package com.yssj.utils;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class TongjiShareCount {
	
	private static Context context;
	
	public static void tongjifenxiangwho(final String shopCode) {

		new SAsyncTask<Integer, Void, String>((FragmentActivity) context, R.string.wait) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected String doInBackground(FragmentActivity context, Integer... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.tongjifenxiang(context, shopCode);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, String result, Exception e) {
				super.onPostExecute(context, result, e);

				if (e == null) {
					LogYiFu.e("谁分享了", result + "");
				}

			}

		}.execute();

	}
	
	public static void tongjifenxiangCount() {

		new SAsyncTask<Integer, Void, String>((FragmentActivity) context, R.string.wait) {

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected String doInBackground(FragmentActivity context, Integer... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.tongjifenxiangshu(context);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, String result, Exception e) {
				super.onPostExecute(context, result, e);

				if (e == null) {
					LogYiFu.e("统计用户分享次数", result + "");
				}

			}

		}.execute();

	}
	

}
