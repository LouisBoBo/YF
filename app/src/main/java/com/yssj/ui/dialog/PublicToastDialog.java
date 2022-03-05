package com.yssj.ui.dialog;

import java.util.Timer;
import java.util.TimerTask;

import com.yssj.activity.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PublicToastDialog extends Dialog {
	private LinearLayout root;
	private String where;
	private TextView id_tv_loadingmsg;
	private ImageView loadingImageView;

	private Timer timer;

	private Context context;

	public PublicToastDialog(Context context, int theme, String where) {
		super(context, theme);
		this.where = where;
		this.context = context;
	}

	public PublicToastDialog(Context context) {
		super(context);
	}

	@SuppressLint("Range")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog_share_wait);
		root = (LinearLayout) findViewById(R.id.root);
		root.setAlpha(180);

		getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		AnimationDrawable anim = (AnimationDrawable) findViewById(R.id.loadingImageView).getBackground();
		anim.start();
		getWindow().setBackgroundDrawableResource(R.drawable.bg_toast_share);
		getWindow().setDimAmount(0f);
		setCancelable(false);

		id_tv_loadingmsg = (TextView) findViewById(R.id.id_tv_loadingmsg);
		loadingImageView = (ImageView) findViewById(R.id.loadingImageView);

		if (where.equals("tuichuAPP")) {
			loadingImageView.setVisibility(View.GONE);
			id_tv_loadingmsg.setText("再按一次退出程序");
			setCancelable(true);

			timer = new Timer();
			timer.schedule(task, 3000);  //2秒后关闭

		}

		// if (!where.equals("")) {
		// timer.schedule(task, 3000); // 1s后执行task
		// }
		//
		// if (where.equals("signToast")) {
		// setCancelable(true);
		// loadingImageView.setVisibility(View.GONE);
		// id_tv_loadingmsg.setText("签到表已经更新啦~");
		// }

	}
	
	
	

	
	
	//
	// Handler handler = new Handler() {
	// public void handleMessage(Message msg) {
	// if (msg.what == 1) {
	// dismiss();
	// }
	// super.handleMessage(msg);
	// };
	// };
	//
	// Timer timer = new Timer();
	// TimerTask task = new TimerTask() {
	//
	// @Override
	// public void run() {
	// // 需要做的事:发送消息
	// Message message = new Message();
	// message.what = 1;
	// handler.sendMessage(message);
	// }
	// };

	// 倒计时
	TimerTask task = new TimerTask() {
		@Override
		public void run() {

			((Activity) context).runOnUiThread(new Runnable() { // UI thread
				@Override
				public void run() {

					try {

						dismiss();

					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			});
		}
	};

}
