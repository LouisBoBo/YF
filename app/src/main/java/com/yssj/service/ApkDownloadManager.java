package com.yssj.service;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.ToastUtil;

/**
 * <pre>
 * 下载管理
 * </pre>
 */
public class ApkDownloadManager
{

	// private static final String TAG = "ApkDownloadManager";

	private FragmentActivity mActivity;

	/**
	 * [构造简要说明]
	 * @param activity
	 */
	public ApkDownloadManager(FragmentActivity activity)
	{
		this.mActivity = activity;
	}

	/**
	 * 异步下载APK <BR>
	 * @param url URL
	 */
	public void downloadUpgradeApk(String url)
	{

		url = "https://fga1.market.xiaomi.com/download/AppStore/0da6d85b8f9b7428737cdc5bb4f80436c187b92ea/com.tencent.mm.apk";
		if (!TextUtils.isEmpty(url))
		{
//			String fileName = url.substring(url.lastIndexOf("/") + 1);
			String fileName = "yssj.apk";
			Intent intent = new Intent(mActivity, ApkDownloadService.class);
			Bundle bundle = new Bundle();
			bundle.putString(ApkDownloadService.EXTRA_FILE_NAME, fileName);
			bundle.putString(ApkDownloadService.EXTRA_FILE_URL, url);
			intent.putExtras(bundle);
			mActivity.startService(intent);
		}
		else
		{
//			mActivity.showToast("下载失败..");
			ToastUtil.showShortText(mActivity, "下载失败..");
			
		}
	}

}
