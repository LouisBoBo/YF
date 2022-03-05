package com.yssj.utils;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import java.util.HashMap;

public class TongJiUtils {
	public static void TongJi(final Context context, final String type) {
		new Thread() {
			public void run() {
				try {
					ComModel.yunYingshujuTongjiNew(context,type);
				} catch (Exception e) {

				}
			};
		}.start();

	}
	public static void TongJiDuration(final Context context, final String type,final String timer) {
		new Thread() {
			public void run() {
				try {
					ComModel.yunYingshujuTongjiNewDuration(context,type,timer);
				} catch (Exception e) {
					
				}
			};
		}.start();
		
	}


	public static void yunYunTongJi(final String key, final int type, final int tab_type,Context context) {
		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) context, R.string.wait) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
					throws Exception {

				return ComModel2.getOperator(context, key, type, tab_type);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				// System.out.println("搭配调用成功");
			}

		}.execute();
	}

}
