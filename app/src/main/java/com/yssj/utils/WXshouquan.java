//package com.yssj.utils;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.model.ComModel2;
//
//import android.content.Context;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//
//public class WXshouquan {
//
//	// 0：手机   -----------无需提交微信OPENID（微信授权）
//	// 1：微信------------需要提交微信OPENID（微信授权）
//	
//	
//	
//	private static String data = "";
//
//	public static String tongjifenxiangwho(Context context) {
//
//		new SAsyncTask<Integer, Void, String>((FragmentActivity) context, R.string.wait) {
//
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//			@Override
//			protected String doInBackground(FragmentActivity context, Integer... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.getFirstShouquan(context);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, String result, Exception e) {
//				super.onPostExecute(context, result, e);
//
//				if (e == null) {
//
//					data = result;
//
//				}
//
//			}
//
//		}.execute();
//
//		return data;
//
//	}
//
//}
