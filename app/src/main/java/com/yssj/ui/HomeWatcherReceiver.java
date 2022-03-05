
package com.yssj.ui;

import com.yssj.YConstance.Pref;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.YJApplication;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.YunYingTongJi;

/**
* @author lifeng
* @date 2016年8月15日下午3:30:59
* 
* 
* 监听Home键的广播
* 
* 
*/
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class HomeWatcherReceiver extends BroadcastReceiver {
	private static final String LOG_TAG = "主页键监听";
	private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
	private static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
	private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
	private static final String SYSTEM_DIALOG_REASON_LOCK = "lock";
	private static final String SYSTEM_DIALOG_REASON_ASSIST = "assist";
	private String tongji_page = "";

	private int type; // 点击Home时当前页面的位置

	@Override
	public void onReceive(final Context context, Intent intent) {
		String action = intent.getAction();

		if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
			// android.intent.action.CLOSE_SYSTEM_DIALOGS
			String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);

			// 短按Home键
			if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {

//				// APP使用時長統計
//				if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
//					new Thread(new Runnable() {
//						@Override
//						public void run() {
//
//							long endUseTime = System.currentTimeMillis();
//							try {
//								ComModel2.APPuseTime(context, (endUseTime - GuideActivity.startUserTime) + "");
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//					}).start();
//
//				}

				type = Integer.parseInt(SharedPreferencesUtil.getStringData(context, Pref.TONGJI_TYPE, "-1"));
				if (type == -1) {
					return;
				}

				// 当前页面
				tongji_page = SharedPreferencesUtil.getStringData(context, Pref.TONGJI_PAGE, "-1");

				if (type == 1012 && tongji_page.equals("热卖")) {
					YunYingTongJi.yunYingTongJi(context, type);
				} else if (type == 1013 && tongji_page.equals("上衣")) {
					YunYingTongJi.yunYingTongJi(context, type);
				} else if (type == 1014 && tongji_page.equals("裙子")) {
					YunYingTongJi.yunYingTongJi(context, type);
				} else if (type == 1015 && tongji_page.equals("裤子")) {
					YunYingTongJi.yunYingTongJi(context, type);
				} else if (type == 1016 && tongji_page.equals("外套")) {
					YunYingTongJi.yunYingTongJi(context, type);
				} else if (type == 1017 && tongji_page.equals("套装")) {
					YunYingTongJi.yunYingTongJi(context, type);
				} else if (type == 1052) {
					if ("1".equals(SharedPreferencesUtil.getStringData(context, "TONGJI_SHOPCART", "" + 0)) && "1"
							.equals(SharedPreferencesUtil.getStringData(context, "TONGJI_SHOPCART_PAGE", "" + 0))) {
						YunYingTongJi.yunYingTongJi(context, type);
					}
				} else {
					YunYingTongJi.yunYingTongJi(context, type);
				}

				// switch (type) {
				// case 1: // 小店
				// LogYiFu.e(LOG_TAG, "小店主页Home键被点击了");
				// break;
				//
				// case 2: // 购物
				// LogYiFu.e(LOG_TAG, "购物主页Home键被点击了");
				// break;
				//
				// case 3: // 签到
				// LogYiFu.e(LOG_TAG, "签到主页Home键被点击了");
				// break;
				//
				// case 4: // 购物车
				// LogYiFu.e(LOG_TAG, "购物车主页Home键被点击了");
				// break;
				// case 5: // 我的
				// LogYiFu.e(LOG_TAG, "我的主页Home键被点击了");
				// break;
				// default:
				// break;
				// }

			} else if (SYSTEM_DIALOG_REASON_RECENT_APPS.equals(reason)) {
				// 长按Home键 或者 activity切换键
				LogYiFu.e(LOG_TAG, "长按Home键 或者 activity切换键");

			} else if (SYSTEM_DIALOG_REASON_LOCK.equals(reason)) {
				// 锁屏
				LogYiFu.e(LOG_TAG, "锁屏");
			} else if (SYSTEM_DIALOG_REASON_ASSIST.equals(reason)) {
				// samsung 长按Home键
				LogYiFu.e(LOG_TAG, "长按Home键");
			}

		}
	}

	// 动态注册Home键监听广播(主界面)

	public static HomeWatcherReceiver mHomeKeyReceiver = null;

	public static void registerHomeKeyReceiver(Context context) {

//		try {
//
//			mHomeKeyReceiver = new HomeWatcherReceiver();
//			final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//			SharedPreferencesUtil.saveBooleanData(context, Pref.HOME_BROADCAST_SIGN, true);
//			context.registerReceiver(mHomeKeyReceiver, homeFilter);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	public static void unregisterHomeKeyReceiver(Context context) {

//		if (SharedPreferencesUtil.getBooleanData(context, Pref.HOME_BROADCAST_SIGN, false)) {
//			if (null != mHomeKeyReceiver) {
//
//				try {
//					context.unregisterReceiver(mHomeKeyReceiver);
//					SharedPreferencesUtil.saveBooleanData(context, Pref.HOME_BROADCAST_SIGN, false);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//		}

	}

}