package com.yssj.utils;

import android.util.Log;

import com.elvishew.xlog.XLog;

import static com.yssj.YUrl.debug;

public class LogYiFu {

//	public static Boolean debug = false;//正式
//	public static Boolean debug = true;//测试debug模式 打印日志

	public static void i(String s1, String s2) {
		if (debug&& null != s2)
			Log.i(s1, s2);
	}

	public static void v(String s1, String s2) {
		if (debug&& null != s2)
			Log.v(s1, s2);
	}

	public static void w(String s1, String s2) {
		if (debug&& null != s2)
			Log.w(s1, s2);
	}

	public static void d(String s1, String s2) {
		if (debug&& null != s2)
			Log.d(s1, s2);
	}

	public static void e(String s1, String s2) {
		if (debug && null != s2)
			Log.e(s1, s2);

		XLog.e(s1+":"+s2);
	}

	/**
	 * @param logTag
	 * @param string
	 * @param e
	 */
	public static void w(String logTag, String string, Exception e) {
		// TODO Auto-generated method stub
		if (debug)
			Log.e(logTag, string, e);
	}

	public static void SystemOut(String s) {
		if (debug)
			System.out.println(s);

	}

}
