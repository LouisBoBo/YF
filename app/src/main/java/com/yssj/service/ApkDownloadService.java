package com.yssj.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.yssj.activity.R;
import com.yssj.utils.FileUtils;
import com.yssj.utils.LogYiFu;


/**
 * <pre>
 * apk下载服务
 * </pre>
 */
@SuppressLint("HandlerLeak")
public class ApkDownloadService extends Service
{
	public static final String EXTRA_FILE_NAME = "fileName";

	public static final String EXTRA_FILE_URL = "fileURL";

	private static final String TAG = "ApkDownloadService";

	private static final int WHAT_UPDATE_PROGRESS = 1;

	private static final int WHAT_CANCEL_NOTIFY = 2;

	private static final int WHAT_DOWNLOAD_FAIL = 3;

	private static final int NOTIFY_ID = 0;

	private static final String SAVE_DIR_PATH = FileUtils.getEnvironmentPath();

	private Context mContext = this;

	private NotificationManager mNotificationManager;

	private Notification mNotification;

	private boolean mIsCancelled;

	private String fileName;

	private String fileUrl;

	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
				case WHAT_UPDATE_PROGRESS:
				{
					int rate = msg.arg1;

					if (rate >= 100)
					{
						// 下载完毕，提示，并自动安装
						RemoteViews contentView = mNotification.contentView;
						contentView.setTextViewText(R.id.nd_rate, "100%");
						contentView.setProgressBar(R.id.nd_progress, 100, 100, false);
						contentView.setTextViewText(R.id.nd_filename,"下载完毕");
						mNotification.flags = Notification.FLAG_AUTO_CANCEL;
						mNotificationManager.notify(NOTIFY_ID, mNotification);

						Intent intent = new Intent();
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setAction(Intent.ACTION_VIEW);
						intent.setDataAndType(Uri.fromFile(new File(SAVE_DIR_PATH, fileName)),
								"application/vnd.android.package-archive");
						mContext.startActivity(intent);

						stopSelf();
					}
					else
					{
						// LogUtil.d(TAG,
						// "更新进度： " + rate + "%");
						RemoteViews contentView = mNotification.contentView;
						contentView.setTextViewText(R.id.nd_rate, rate + "%");
						contentView.setProgressBar(R.id.nd_progress, 100, rate, false);
						mNotificationManager.notify(NOTIFY_ID, mNotification);
					}

					break;
				}

				case WHAT_CANCEL_NOTIFY:
				{
					mNotificationManager.cancel(NOTIFY_ID);
					break;
				}

				case WHAT_DOWNLOAD_FAIL:
				{
					RemoteViews contentView = mNotification.contentView;
					contentView.setTextViewText(R.id.nd_filename, "网络连接出错");
					mNotification.flags = Notification.FLAG_AUTO_CANCEL;
					mNotificationManager.notify(NOTIFY_ID, mNotification);
					break;
				}

				default:
					break;
			}
		}
	};

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		mIsCancelled = true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Bundle bundle = intent.getExtras();
		fileName = bundle.getString(EXTRA_FILE_NAME);
		fileUrl = bundle.getString(EXTRA_FILE_URL);

		LogYiFu.d(TAG, String.format("fileName: %s, fileURL: %s", fileName, fileUrl));

		if (!TextUtils.isEmpty(fileName) || !TextUtils.isEmpty(fileUrl))
		{

			mNotification = new Notification(R.drawable.ic_launcher, "开始下载",
					System.currentTimeMillis());
			mNotification.flags = Notification.FLAG_ONGOING_EVENT;

			RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.download_notification_layout);
			contentView.setTextViewText(R.id.nd_filename, fileName);
			mNotification.contentView = contentView;

			Intent it = new Intent();
			PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
			mNotification.contentIntent = contentIntent;

			mNotificationManager.cancel(NOTIFY_ID);
			mNotificationManager.notify(NOTIFY_ID, mNotification);
		}

		// 下载
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				startDownload();
			}
		}).start();

		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 开始下载 <BR>
	 */
	private void startDownload()
	{
		mIsCancelled = false;

		HttpGet httpRequest = new HttpGet(fileUrl);
		InputStream in = null;
		FileOutputStream out = null;
		try
		{
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				HttpEntity entity = httpResponse.getEntity();
				double contentLen = entity.getContentLength();

//				LogYiFu.d(TAG, String.format("Content-Length: %d", contentLen));

				if (contentLen <= 0)
				{
					return;
				}

				in = entity.getContent();

				File dir = new File(SAVE_DIR_PATH);
				if (!dir.exists())
				{
					dir.mkdirs();
				}

				out = new FileOutputStream(new File(SAVE_DIR_PATH, fileName), false);

				byte[] buffer = new byte[4096];
				int len = 0;
				double tempSize = 0;
				int rate = 0;
				Message msg = null;
				
				double rateF = 0.00;
				while ((len = in.read(buffer)) > -1)
				{
					if (mIsCancelled)
					{
						mHandler.sendEmptyMessage(WHAT_CANCEL_NOTIFY);
						break;
					}

					out.write(buffer, 0, len);
					out.flush();
					tempSize = tempSize + len;
					
					rateF = tempSize / contentLen;
					
					rate =  (int) (rateF * 100);

					LogYiFu.e("rate", ""+rate+"  "+tempSize+"  "+len+"  "+contentLen);
					if (rate % 5 == 0 || rate == 100)
					{
						mHandler.removeMessages(WHAT_UPDATE_PROGRESS);
						msg = mHandler.obtainMessage();
						msg.what = WHAT_UPDATE_PROGRESS;
						msg.arg1 = rate;
						mHandler.sendMessageDelayed(msg, 50);
					}
				}
				
			}
			else
			{
				// 下载出错
				mHandler.sendEmptyMessage(WHAT_DOWNLOAD_FAIL);
			}
		}
		catch (Exception e)
		{
			LogYiFu.e(TAG, e+"");
			// 下载出错
			mHandler.sendEmptyMessage(WHAT_DOWNLOAD_FAIL);
		}
		finally
		{
			FileUtils.close(out);
			FileUtils.close(in);
		}
	}

}
