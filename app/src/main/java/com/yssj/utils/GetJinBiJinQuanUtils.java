package com.yssj.utils;

import java.util.HashMap;

import com.yssj.YConstance.Pref;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.infos.GoldCoinDetailActivity;
import com.yssj.ui.activity.infos.IntergralDetailActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

public class GetJinBiJinQuanUtils {

	// 获取金币相关数据
	public static void getJinBi(Context context) {

		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) context, 0) {
			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				return ComModel2.getTwoFoldnessGold(context);
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null && result != null) {
					String str = result.get("twofoldnessGold");
					if (!"".equals(str)) {
						
						SharedPreferencesUtil.saveStringData(context, Pref.JINBI_END_DATE, result.get("end_date"));
						SharedPreferencesUtil.saveStringData(context, Pref.JINBU_ID, result.get("id"));
						
						

					} else {
						SharedPreferencesUtil.saveStringData(context, Pref.JINBI_END_DATE, "-1");
						SharedPreferencesUtil.saveStringData(context, Pref.JINBU_ID, "");
					}
					
//					ToastUtil.showShortText(context, result+"");

				}
			}
		}.execute();

	}

	// 获取金券相关数据
	public static void getJinQuan(Context context) {

		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) context, 0) {
			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				return ComModel2.getCpgold(context);
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null && result != null) {
					
					
					
					try {

						if (result.get("is_open").equals("1")){
							SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_END_DATE, result.get("end_date"));
							SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_IS_OPEN, result.get("is_open"));
							SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_C_LAST_TIME, result.get("c_last_time"));
							SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_C_PRICE, result.get("c_price"));
							SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_IS_USE, result.get("is_use"));
							SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_C_ID, result.get("c_id"));
						}else{
							SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_END_DATE, "-1");
							SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_IS_OPEN, "0");
							SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_C_LAST_TIME, "0");
							SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_C_PRICE, "0.0");
							SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_IS_USE, "");
							SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_C_ID, "0");
						}
						
					} catch (Exception e2) {
						// TODO: handle exception
					}
					
					
					
					
				

				}
			}
		}.execute();

	}

}
