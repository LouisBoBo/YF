package com.yssj.utils;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

public class ScreenManager {

	private static Stack<Activity> activityStack;
	private static ScreenManager instance;
	private boolean flag = true;

	private ScreenManager() {
	}

	public static ScreenManager getScreenManager() {
		if (instance == null) {
			instance = new ScreenManager();
		}
		return instance;
	}

	// 退出栈顶Activity
	public void popActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			activityStack.remove(activity);
			if (activityStack.size() == 0) {
				flag = false;
			}
			activity = null;
		}
	}

	// 获得当前栈顶Activity
	public Activity currentActivity() {
		Activity activity = null;
		if(activityStack!=null){
			 activity = activityStack.lastElement();
		}
		return activity;
	}

	// 将当前Activity推入栈中
	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	// 退出栈中所有Activity
	public void popAllActivityExceptOne(Class cls) {
		flag = true;
		while (flag) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				break;
			}
			popActivity(activity);
		}
	}

	/**
	 * 75 结束所有Activity 76
	 */

	public void finishAllActivity() {

		for (int i = 0, size = activityStack.size(); i < size; i++) {

			if (null != activityStack.get(i)) {

				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}
	/**
	 * 86 退出应用程序 87
	 */

	public void AppExit(Context context) {

		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
