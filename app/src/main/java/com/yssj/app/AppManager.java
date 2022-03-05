package com.yssj.app;

import java.util.Stack;

import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.logins.LoginActivity;

import android.app.Activity;
import android.content.Context;

public class AppManager {
	public  static Stack<Activity> activityStack;
	public  static Stack<Activity> shopactivityStack;
	private static AppManager instance;
	public  static Stack<Activity> activityDetails;//夺宝，普通商品详情
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	public void addDetailsActivity(Activity activity) {
		if (activityDetails == null) {
			activityDetails = new Stack<Activity>();
		}
		activityDetails.add(activity);
	}
	public void finishDetailsActivity(Activity activity) {
		if(activityDetails!=null&&activity!=null) {
			int size = activityDetails.size();
			for (int i = size - 1; i >= 0; i--) {
				if (activity == activityDetails.get(i)) {
					activityDetails.get(i).finish();
					activityDetails.remove(activityDetails.get(i));
					return;
				}
			}
		}
	}
	public void removeDetailsActivity(Activity activity) {
		if(activityDetails!=null&&activity!=null) {
			int size = activityDetails.size();
			for (int i = size - 1; i >= 0; i--) {
				if (activity == activityDetails.get(i)) {
					activityDetails.remove(activityDetails.get(i));
					return;
				}
			}
		}
	}


	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}


	public void finishAllActivity() {
		int size = activityStack.size();
		for (int i = size - 1;  i >= 0; i--) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
		if(null!=activityDetails){
			activityDetails.clear();
		}
	}
	/**
	public void finishAllActivityOfLogin() {
		int size = activityStack.size();
		for (int i = size - 1;  i >= 0; i--) {
			if (null != activityStack.get(i)) {
				if(activityStack.get(i) instanceof LoginActivity){
					continue;
				}
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}*/
	
	public void finishAllActivityOfEveryDayTask() {
		int size = activityStack.size();
		for (int i = size - 1;  i >= 0; i--) {
			if (null != activityStack.get(i)) {
				if(activityStack.get(i) instanceof MainMenuActivity){
					continue;
				}
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
		if(MainMenuActivity.instances!=null){
			activityStack.add(MainMenuActivity.instances);
		}
		if(null!=activityDetails){
			activityDetails.clear();
		}
	}
	
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

}
