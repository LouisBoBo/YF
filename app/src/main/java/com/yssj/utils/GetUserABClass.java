package com.yssj.utils;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.logins.OldUserLoginActivity;

import java.util.HashMap;
//获取用户是不是A类 并保存在本地  ---- 在用户登录成功、APP启动和用户绑定完身份证号的时候调用

public class GetUserABClass {

	public static void getUserABclass(Context context) {

		new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) context, R.string.wait) {

			@Override
			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				return ComModel2.getUserGradle(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
				super.onPostExecute(context, result, e);

				if (null == e && result != null) {

					try {
						int grade = Integer.parseInt(result.get("grade") + ""); // 1==A类
						// 2==B类…以此类推,目前只有两类
						// 0//
						// 还未区分a,b类用户

						if (grade == 1) {
							SharedPreferencesUtil.saveBooleanData(context, Pref.ISACLASS, true); // 是A类
						} else {
							SharedPreferencesUtil.saveBooleanData(context, Pref.ISACLASS, false);// 不是A类
						}

						// ToastUtil.showShortText(context, "用户AB类确定");

						if (null != OldUserLoginActivity.instances) {
							OldUserLoginActivity.instances.finish();
						}

						if (null != LoginActivity.instances) {
							LoginActivity.instances.finish();
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}

				}
			}

		}.execute();

	}
}