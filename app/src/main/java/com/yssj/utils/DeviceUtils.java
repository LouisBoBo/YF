package com.yssj.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class DeviceUtils {

	public static String getChannelCode(Context context) {
		String code = getMetaData(context, "UMENG_CHANNEL");
		if (code != null) {
			return code;
		}
		return "1";
	}

	public static String getMetaData(Context context, String key) {
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			Object value = ai.metaData.get(key);
			if (value != null) {
				return value.toString();
			}
		} catch (Exception e) {
			//
		}
		return null;
	}

	public static final boolean isApkInstalled(Context context, String packageName) {
		try {
			context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}
	
	/** 获取厂商 */
	public static String getManufacturer() {
		return Build.MANUFACTURER;
	}

	/** 获取手机型号 */
	public static String getModel() {
		return Build.MODEL;
	}

	/** 获取sdk版本�? */
	public static String getSDKVersion() {
		return String.valueOf(Build.VERSION.SDK_INT);
	}

	/** 获取系统版本�? */
	public static String getRelease() {
		return Build.VERSION.RELEASE;
	}

	/** 获取屏幕分辨�? */
	public static String getScreen(Context context) {
		if (context instanceof Activity) {
			DisplayMetrics metrics = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(metrics);
			return "" + metrics.widthPixels + "*" + metrics.heightPixels;
		}
		return "0*0";
	}

	/** 获取屏幕宽度 */
	public static int getScreenWidth(Context context) {
		if (context instanceof Activity) {
			DisplayMetrics metrics = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(metrics);
			int width = metrics.widthPixels;
			return width;
		}
		return 0;
	}

	/** 获取屏幕高度 */
	public static int getScreenHeight(Context context) {
		if (context instanceof Activity) {
			DisplayMetrics metrics = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(metrics);
			int height = metrics.heightPixels;
			return height;
		}
		return 0;
	}

	public static String getVersionName(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 获取设备�? */
	public static String getDeviceId(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

}
