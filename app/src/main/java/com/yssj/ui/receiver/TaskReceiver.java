package com.yssj.ui.receiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.umeng.socialize.media.UMImage;
import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.MyPopupwindow;
import com.yssj.custom.view.NewPDialog;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainFragment;
import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.ui.activity.infos.DailySignActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.shopdetails.NoShareActivity;
import com.yssj.ui.fragment.MineIfoFragment;
import com.yssj.ui.fragment.ClassficationFragment;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.utils.CheckStrUtil;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.YCache;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

/**
 * 弹出对话框接收器
 * 
 * @author lbp
 * 
 */

public class TaskReceiver extends BroadcastReceiver {

	public static String newMemberTask_1 = "newMemberTask_1";
	public static String newMemberTask_2 = "newMemberTask_2";
	public static String newMemberTask_3 = "newMemberTask_3";
	public static String newMemberTask_4 = "newMemberTask_4";
	public static String newMemberTask_5 = "newMemberTask_5";
	public static String newMemberTask_6 = "newMemberTask_6";
	public static String newMemberTask_7 = "newMemberTask_7";
	public static String newMemberTask_8 = "newMemberTask_8";

	public static String everyDayTask_1 = "everyDayTask_1";
	public static String everyDayTask_2 = "everyDayTask_2";
	public static String everyDayTask_3 = "everyDayTask_3";
	public static String everyDayTask_4 = "everyDayTask_4";
	public static String everyDayTask_5 = "everyDayTask_5";
	public static String everyDayTask_6 = "everyDayTask_6";
	public static String everyDayTask_7 = "everyDayTask_7";
	public static String everyDayTask_8 = "everyDayTask_8";

	public static String onebuysubmitoderend = "onebuysubmitoderend";


	public static String toMineInfo = "toMineInfo";

	private Activity activity;

	// private Shop shop;
	public TaskReceiver(Activity activity) {
		super();
		this.activity = activity;
	}

	public static void regiserReceiver(Activity activity, TaskReceiver receiver) {
		IntentFilter filter = new IntentFilter();
		filter.addAction(newMemberTask_1);
		filter.addAction(newMemberTask_2);
		filter.addAction(newMemberTask_3);
		filter.addAction(newMemberTask_4);
		filter.addAction(newMemberTask_5);
		filter.addAction(newMemberTask_6);
		filter.addAction(newMemberTask_7);
		filter.addAction(newMemberTask_8);
		filter.addAction(everyDayTask_1);
		filter.addAction(everyDayTask_2);
		filter.addAction(everyDayTask_3);
		filter.addAction(everyDayTask_4);
		filter.addAction(everyDayTask_5);
		filter.addAction(everyDayTask_6);
		filter.addAction(everyDayTask_7);
		filter.addAction(everyDayTask_8);
		filter.addAction(toMineInfo);
		filter.addAction(onebuysubmitoderend);

		activity.registerReceiver(receiver, filter);
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		String action = intent.getAction();
		if (action.equals(toMineInfo)) {

			if (MainMenuActivity.instances != null) {
				MainMenuActivity.instances.getFragment().setIndex(4);
			}

			return;
		}

		if (action.equals(newMemberTask_1)) {
			String imei = CheckStrUtil.getImei(activity);
			if (imei == null || ComModel2.flag != 0) {
				newThread(1000 * 60l, activity, TaskReceiver.newMemberTask_4);
				return;
			}
			NewPDialog dialog = new NewPDialog(activity, R.layout.task_dialog1);
			dialog.setF(new NewPDialog.FinishLintener() {

				@Override
				public void onFinishClickLintener() {
					newThread(1000 * 60l, activity, TaskReceiver.newMemberTask_4);
				}
			});
			dialog.setL(new NewPDialog.TaskLintener() {

				@Override
				public void onOKClickLintener() {
					// 点击分享
					newThread(1000 * 60l, activity, TaskReceiver.newMemberTask_4);
					LoadingDialog.show((FragmentActivity) activity);
					new AsyncTask<Void, Void, Void>() {
						@Override
						protected Void doInBackground(Void... arg0) {

							dPic("http://yssj668.b0.upaiyun.com/share/android/900_900_1_android.jpg");
							return null;
						}

						@Override
						protected void onPostExecute(Void result) {
							super.onPostExecute(result);
							LoadingDialog.hide(activity);
							ShareUtil.shareH5(activity, file);
						};

					}.execute();

				}

				@Override
				public void onShouZhiClickLintener() {
					// TODO Auto-generated method stub

				}
			});
			dialog.show();

		} else if (action.equals(newMemberTask_2)) {

//		} else if (action.equals(newMemberTask_3)) {
//			if (YJApplication.instance.isLoginSucess()) {
//				return;
//			}
//			NewPDialog dialog = new NewPDialog(activity, R.layout.task_dialog3);
//			dialog.setL(new NewPDialog.TaskLintener() {
//
//				@Override
//				public void onOKClickLintener() {
//					// 注册
//
//					if (LoginActivity.instances != null) {
//						LoginActivity.instances.finish();
//					}
//					Intent i = new Intent(activity, LoginActivity.class);
//					i.putExtra("login_register", "login");
//					activity.startActivity(i);
//				}
//
//				@Override
//				public void onShouZhiClickLintener() {
//					// TODO Auto-generated method stub
//
//				}
//			});
//			dialog.show();
//
//		} else if (action.equals(newMemberTask_4)) {
			if (!YJApplication.instance.isLoginSucess()) {
				newThread(55 * 1000l, activity, TaskReceiver.newMemberTask_3);
				return;
			}

			UserInfo userInfo = YCache.getCacheUserSafe(activity);
			if (null == userInfo.getHobby() || userInfo.getHobby().equals("0")) {
//				NewPDialog dialog = new NewPDialog(activity, R.layout.task_dialog4);
//				dialog.setF(new NewPDialog.FinishLintener() {
//
//					@Override
//					public void onFinishClickLintener() {
//						newThread(55 * 1000l, activity, TaskReceiver.newMemberTask_3);
//					}
//				});
//				dialog.setL(new NewPDialog.TaskLintener() {
//
//					@Override
//					public void onOKClickLintener() {
//						// 开店
//						newThread(55 * 1000l, activity, TaskReceiver.newMemberTask_3);
//						AppManager.getAppManager().finishAllActivityOfEveryDayTask();
//						if (MainMenuActivity.instances != null)
//							((MainFragment) MainMenuActivity.instances.getSupportFragmentManager()
//									.findFragmentByTag("tag")).setIndex(0);
//
//					}
//
//					@Override
//					public void onShouZhiClickLintener() {
//						// TODO Auto-generated method stub
//
//					}
//				});
//				dialog.show();
			} else {
				newThread(55 * 1000l, activity, TaskReceiver.newMemberTask_3);
			}

		} else if (action.equals(newMemberTask_5)) {

//			NewPDialog dialog = new NewPDialog(activity, R.layout.task_dialog5_2);
//			dialog.setL(new NewPDialog.TaskLintener() {
//
//				@Override
//				public void onOKClickLintener() {
//					//
//					NewPDialog dialog = new NewPDialog(activity, R.layout.task_dialog5_1);
//					dialog.setL(new NewPDialog.TaskLintener() {
//
//						@Override
//						public void onOKClickLintener() {
//
//						}
//
//						@Override
//						public void onShouZhiClickLintener() {
//							// TODO Auto-generated method stub
//
//						}
//					});
//				}
//
//				@Override
//				public void onShouZhiClickLintener() {
//					// TODO Auto-generated method stub
//
//				}
//			});
		} else if (action.equals(newMemberTask_6)) {
//			NewPDialog dialog = new NewPDialog(activity, R.layout.task_dialog6_1);
//			dialog.setL(new NewPDialog.TaskLintener() {
//
//				@Override
//				public void onOKClickLintener() {
//					//
//					NewPDialog dialogs = new NewPDialog(activity, R.layout.task_dialog6_2);
//					dialogs.setL(new NewPDialog.TaskLintener() {
//
//						@Override
//						public void onOKClickLintener() {
//							// 注册
//							if (LoginActivity.instances != null) {
//								LoginActivity.instances.finish();
//							}
//							Intent i = new Intent(activity, LoginActivity.class);
//							i.putExtra("login_register", "register");
//							activity.startActivityForResult(i, 15502);
//						}
//
//						@Override
//						public void onShouZhiClickLintener() {
//							// TODO Auto-generated method stub
//
//						}
//					});
//					dialogs.show();
//				}
//
//				@Override
//				public void onShouZhiClickLintener() {
//					// TODO Auto-generated method stub
//
//				}
//			});
//			dialog.show();

		} else if (action.equals(newMemberTask_7)) {

		} else if (action.equals(newMemberTask_8)) {
			int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			if (hour < 14) {
				if (activity.getSharedPreferences("EveryDayShareAm", Context.MODE_PRIVATE).getInt("day", 0) != Calendar
						.getInstance().get(Calendar.DAY_OF_MONTH)) {
					NewPDialog dialog = new NewPDialog(activity, R.layout.jifen_dialog);
					dialog.setL(new NewPDialog.TaskLintener() {

						@Override
						public void onOKClickLintener() {
							share();
						}

						@Override
						public void onShouZhiClickLintener() {
							// TODO Auto-generated method stub

						}
					});
					dialog.show();
					return;
				}
				;
			} else {
				if (activity.getSharedPreferences("EveryDaySharePm", Context.MODE_PRIVATE).getInt("day", 0) != Calendar
						.getInstance().get(Calendar.DAY_OF_MONTH)) {
					NewPDialog dialog = new NewPDialog(activity, R.layout.jifen_dialog);
					dialog.setL(new NewPDialog.TaskLintener() {

						@Override
						public void onOKClickLintener() {
							share();
						}

						@Override
						public void onShouZhiClickLintener() {
							// TODO Auto-generated method stub

						}
					});
					dialog.show();
					return;
				}
				;
			}
			NewPDialog dialog = new NewPDialog(activity, R.layout.task_dialog8);
			dialog.setL(new NewPDialog.TaskLintener() {

				@Override
				public void onOKClickLintener() {
//					activity.startActivity(new Intent(activity, DailySignActivity.class));
				}

				@Override
				public void onShouZhiClickLintener() {
					// TODO Auto-generated method stub

				}
			});
			dialog.show();

		} else if (action.equals(everyDayTask_1)) {
			UserInfo userInfo;
			userInfo = YCache.getCacheUserSafe(activity);
			if (userInfo.getHobby().equals("null") || userInfo.getHobby() == null || userInfo.getHobby().equals("")
					|| userInfo.getHobby().equals("0")) {
				return;
			}
			if (!YJApplication.instance.isLoginSucess()) {
				return;
			}
			if (activity instanceof MainMenuActivity) {

				ClassficationFragment fragment0 = (ClassficationFragment) ((MainFragment) ((MainMenuActivity) activity)
						.getSupportFragmentManager().findFragmentByTag("tag")).getChildFragmentManager()
								.findFragmentByTag("1");
				SignFragment fragment2 = (SignFragment) ((MainFragment) ((MainMenuActivity) activity)
						.getSupportFragmentManager().findFragmentByTag("tag")).getChildFragmentManager()
								.findFragmentByTag("3");
				MineIfoFragment fragment4 = (MineIfoFragment) ((MainFragment) ((MainMenuActivity) activity)
						.getSupportFragmentManager().findFragmentByTag("tag")).getChildFragmentManager()
								.findFragmentByTag("5");
				if (fragment0 != null) {
					if (fragment0.isVisible()) {
						return;
					}
				}
				if (fragment2 != null) {
					if (fragment2.isVisible()) {
						return;
					}
				}
				if (fragment4 != null) {
					if (fragment4.isVisible()) {
						return;
					}
				}
			}
			// if(((MainFragment)((MainMenuActivity)activity).getSupportFragmentManager()
			// .findFragmentByTag("tag")).getChildFragmentManager().findFragmentByTag("0").isVisible()
			// &&((MainFragment)((MainMenuActivity)activity).getSupportFragmentManager()
			// .findFragmentByTag("tag")).getChildFragmentManager().findFragmentByTag("4").isVisible()
			// &&((MainFragment)((MainMenuActivity)activity).getSupportFragmentManager()
			// .findFragmentByTag("tag")).getChildFragmentManager().findFragmentByTag("2").isVisible())
			// {
			// return;
			// }
			if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == activity
					.getSharedPreferences("Calendar", Context.MODE_PRIVATE).getInt("week", 0)) {
				return;
			}
			NewPDialog dialog = new NewPDialog(activity, R.layout.every_day_task_dialog1);
			dialog.setL(new NewPDialog.TaskLintener() {

				@Override
				public void onOKClickLintener() {
					activity.getSharedPreferences("Calendar", Context.MODE_PRIVATE).edit()
							.putInt("week", Calendar.getInstance().get(Calendar.DAY_OF_WEEK)).commit();
					// 跳往我的店
					AppManager.getAppManager().finishAllActivityOfEveryDayTask();
					if (MainMenuActivity.instances != null)
						((MainFragment) MainMenuActivity.instances.getSupportFragmentManager().findFragmentByTag("tag"))
								.setIndex(0);
				}

				@Override
				public void onShouZhiClickLintener() {
					// TODO Auto-generated method stub

				}
			});
			dialog.show();

		} else if (action.equals(everyDayTask_2)) {
			if (!YJApplication.instance.isLoginSucess()) {
				return;
			}

			if (activity instanceof MainMenuActivity) {
				ClassficationFragment fragment0 = (ClassficationFragment) ((MainFragment) ((MainMenuActivity) activity)
						.getSupportFragmentManager().findFragmentByTag("tag")).getChildFragmentManager()
								.findFragmentByTag("1");
				SignFragment fragment2 = (SignFragment) ((MainFragment) ((MainMenuActivity) activity)
						.getSupportFragmentManager().findFragmentByTag("tag")).getChildFragmentManager()
								.findFragmentByTag("3");
				MineIfoFragment fragment4 = (MineIfoFragment) ((MainFragment) ((MainMenuActivity) activity)
						.getSupportFragmentManager().findFragmentByTag("tag")).getChildFragmentManager()
								.findFragmentByTag("5");
				if (fragment0 != null) {
					if (fragment0.isVisible()) {
						return;
					}
				}
				if (fragment2 != null) {
					if (fragment2.isVisible()) {
						return;
					}
				}
				if (fragment4 != null) {
					if (fragment4.isVisible()) {
						return;
					}
				}
			}

			// 获取当前时间
//			String currentTime = DateFormat.format("HH", new Date()).toString();
//			int hour = Integer.parseInt(currentTime);
			int  hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			// 每日上午分享一次
//			if ((hour > 7 || hour == 7) && hour < 14) {
//
//				if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == activity
//						.getSharedPreferences("EveryDayShareAm", Context.MODE_PRIVATE).getInt("day", 0)) {
//					return;
//				}
//				NewPDialog dialog = new NewPDialog(activity, R.layout.every_day_task_dialog2);
//				dialog.setL(new NewPDialog.TaskLintener() {
//
//					@Override
//					public void onOKClickLintener() {
//						activity.getSharedPreferences("EveryDayShareAm", Context.MODE_PRIVATE).edit()
//								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//						// 分享商品
//						share();
//					}
//
//					@Override
//					public void onShouZhiClickLintener() {
//						// TODO Auto-generated method stub
//
//					}
//				});
//				dialog.show();
//			}

			// 每日下午分享一次
//			if ((hour > 14 || hour == 14) && hour < 20) {
//				if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == activity
//						.getSharedPreferences("EveryDaySharePm", Context.MODE_PRIVATE).getInt("day", 0)) {
//					return;
//				}
//				NewPDialog dialog = new NewPDialog(activity, R.layout.every_day_task_dialog3);
//				dialog.setL(new NewPDialog.TaskLintener() {
//
//					@Override
//					public void onOKClickLintener() {
//						activity.getSharedPreferences("EveryDaySharePm", Context.MODE_PRIVATE).edit()
//								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//						// 分享商品
//						share();
//					}
//
//					@Override
//					public void onShouZhiClickLintener() {
//						// TODO Auto-generated method stub
//
//					}
//				});
//				dialog.show();
//			}

		} else if (action.equals(everyDayTask_3)) {

		} else if (action.equals(everyDayTask_4)) {
			// if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == activity
			// .getSharedPreferences("EverydayTaskMondayFriday",
			// Context.MODE_PRIVATE).getInt("day", 0)) {
			// return;
			// }
			// int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
			// if(activity instanceof MainMenuActi)
			// if(day==6){
			//
			//
			// MyShopFragment fragment0 =(MyShopFragment)
			// ((MainFragment)((MainMenuActivity)activity).getSupportFragmentManager()
			// .findFragmentByTag("tag")).getChildFragmentManager().findFragmentByTag("2");
			// CircleFragment fragment2 =
			// (CircleFragment)((MainFragment)((MainMenuActivity)activity).getSupportFragmentManager()
			// .findFragmentByTag("tag")).getChildFragmentManager().findFragmentByTag("3");
			// MineIfoFragment fragment4 =(MineIfoFragment)
			// ((MainFragment)((MainMenuActivity)activity).getSupportFragmentManager()
			// .findFragmentByTag("tag")).getChildFragmentManager().findFragmentByTag("5");
			// if (fragment0!= null){
			// if(fragment0.isVisible()){
			// return;
			// }
			// }
			// if (fragment2!= null){
			// if(fragment2.isVisible()){
			// return;
			// }
			// }
			// if (fragment4!= null){
			// if(fragment4.isVisible()){
			// return;
			// }
			// }
			//
			// }
			// else{
			//
			//
			// MineShopFragment fragment0 =(MineShopFragment)
			// ((MainFragment)((MainMenuActivity)activity).getSupportFragmentManager()
			// .findFragmentByTag("tag")).getChildFragmentManager().findFragmentByTag("1");
			// CircleFragment fragment2 =
			// (CircleFragment)((MainFragment)((MainMenuActivity)activity).getSupportFragmentManager()
			// .findFragmentByTag("tag")).getChildFragmentManager().findFragmentByTag("3");
			// MineIfoFragment fragment4 =(MineIfoFragment)
			// ((MainFragment)((MainMenuActivity)activity).getSupportFragmentManager()
			// .findFragmentByTag("tag")).getChildFragmentManager().findFragmentByTag("5");
			// if (fragment0!= null){
			// if(fragment0.isVisible()){
			// return;
			// }
			// }
			// if (fragment2!= null){
			// if(fragment2.isVisible()){
			// return;
			// }
			// }
			// if (fragment4!= null){
			// if(fragment4.isVisible()){
			// return;
			// }
			// }
			//
			// }
			// NewPDialog dialog = null;
			// if (day == 2) {
			// dialog = new NewPDialog(activity,
			// R.layout.every_day_task_dialog4_1);
			// dialog.setL(new NewPDialog.TaskLintener() {
			//
			// @Override
			// public void onOKClickLintener() {
			// activity.getSharedPreferences(
			// "EverydayTaskMondayFriday",
			// Context.MODE_PRIVATE)
			// .edit()
			// .putInt("day",
			// Calendar.getInstance().get(
			// Calendar.DAY_OF_MONTH))
			// .commit();
			// // 跳往我的店
			// AppManager.getAppManager()
			// .finishAllActivityOfEveryDayTask();
			// if (MainMenuActivity.instances != null)
			// ((MainFragment) MainMenuActivity.instances
			// .getSupportFragmentManager()
			// .findFragmentByTag("tag")).setIndex(0);
			// if (((MineShopFragment) ((MainFragment)
			// MainMenuActivity.instances
			// .getSupportFragmentManager().findFragmentByTag(
			// "tag")).getChildFragmentManager()
			// .findFragmentByTag("1")) == null) {
			// MineShopFragment.url = YUrl.YSS_URL_ANDROID_H5
			// + "view/store/selectTheme.html";
			// } else {
			// ((MineShopFragment) ((MainFragment) MainMenuActivity.instances
			// .getSupportFragmentManager()
			// .findFragmentByTag("tag"))
			// .getChildFragmentManager()
			// .findFragmentByTag("1"))
			// .loadUrlEveryDayTask(YUrl.YSS_URL_ANDROID_H5
			// + "view/store/selectTheme.html");
			// }
			// }
			// });
			// dialog.show();
			// } else if (day == 3) {
			// dialog = new NewPDialog(activity,
			// R.layout.every_day_task_dialog5_1);
			// dialog.setL(new NewPDialog.TaskLintener() {
			//
			// @Override
			// public void onOKClickLintener() {
			// activity.getSharedPreferences(
			// "EverydayTaskMondayFriday",
			// Context.MODE_PRIVATE)
			// .edit()
			// .putInt("day",
			// Calendar.getInstance().get(
			// Calendar.DAY_OF_MONTH))
			// .commit();
			// // 跳往我的店
			// AppManager.getAppManager()
			// .finishAllActivityOfEveryDayTask();
			// if (MainMenuActivity.instances != null)
			// ((MainFragment) MainMenuActivity.instances
			// .getSupportFragmentManager()
			// .findFragmentByTag("tag")).setIndex(0);
			// if (((MineShopFragment) ((MainFragment)
			// MainMenuActivity.instances
			// .getSupportFragmentManager().findFragmentByTag(
			// "tag")).getChildFragmentManager()
			// .findFragmentByTag("1")) == null) {
			// MineShopFragment.url = YUrl.YSS_URL_ANDROID_H5
			// + "view/store/theme/editNotice.html";
			// } else {
			// ((MineShopFragment) ((MainFragment) MainMenuActivity.instances
			// .getSupportFragmentManager()
			// .findFragmentByTag("tag"))
			// .getChildFragmentManager()
			// .findFragmentByTag("1"))
			// .loadUrlEveryDayTask(YUrl.YSS_URL_ANDROID_H5
			// + "view/store/theme/editNotice.html");
			// }
			// }
			// });
			// dialog.show();
			// } else if (day == 4) {
			// dialog = new NewPDialog(activity,
			// R.layout.every_day_task_dialog6_1);
			// dialog.setL(new NewPDialog.TaskLintener() {
			//
			// @Override
			// public void onOKClickLintener() {
			// activity.getSharedPreferences(
			// "EverydayTaskMondayFriday",
			// Context.MODE_PRIVATE)
			// .edit()
			// .putInt("day",
			// Calendar.getInstance().get(
			// Calendar.DAY_OF_MONTH))
			// .commit();
			// // 跳往我的店
			// AppManager.getAppManager()
			// .finishAllActivityOfEveryDayTask();
			// if (MainMenuActivity.instances != null)
			// ((MainFragment) MainMenuActivity.instances
			// .getSupportFragmentManager()
			// .findFragmentByTag("tag")).setIndex(0);
			// if (((MineShopFragment) ((MainFragment)
			// MainMenuActivity.instances
			// .getSupportFragmentManager().findFragmentByTag(
			// "tag")).getChildFragmentManager()
			// .findFragmentByTag("1")) == null) {
			// MineShopFragment.url = YUrl.YSS_URL_ANDROID_H5
			// + "view/store/theme/editBanner.html";
			// } else {
			// ((MineShopFragment) ((MainFragment) MainMenuActivity.instances
			// .getSupportFragmentManager()
			// .findFragmentByTag("tag"))
			// .getChildFragmentManager()
			// .findFragmentByTag("1"))
			// .loadUrlEveryDayTask(YUrl.YSS_URL_ANDROID_H5
			// + "view/store/theme/editBanner.html");
			// }
			// //
			// }
			// });
			// dialog.show();
			// } else if (day == 5) {
			// dialog = new NewPDialog(activity,
			// R.layout.every_day_task_dialog7_1);
			// dialog.setL(new NewPDialog.TaskLintener() {
			//
			// @Override
			// public void onOKClickLintener() {
			// activity.getSharedPreferences(
			// "EverydayTaskMondayFriday",
			// Context.MODE_PRIVATE)
			// .edit()
			// .putInt("day",
			// Calendar.getInstance().get(
			// Calendar.DAY_OF_MONTH))
			// .commit();
			// // 跳往我的店
			// AppManager.getAppManager()
			// .finishAllActivityOfEveryDayTask();
			// if (MainMenuActivity.instances != null)
			// ((MainFragment) MainMenuActivity.instances
			// .getSupportFragmentManager()
			// .findFragmentByTag("tag")).setIndex(0);
			// if (((MineShopFragment) ((MainFragment)
			// MainMenuActivity.instances
			// .getSupportFragmentManager().findFragmentByTag(
			// "tag")).getChildFragmentManager()
			// .findFragmentByTag("1")) == null) {
			// MineShopFragment.url = YUrl.YSS_URL_ANDROID_H5
			// + "view/store/theme/setRecommend.html";
			// } else {
			// ((MineShopFragment) ((MainFragment) MainMenuActivity.instances
			// .getSupportFragmentManager()
			// .findFragmentByTag("tag"))
			// .getChildFragmentManager()
			// .findFragmentByTag("1"))
			// .loadUrlEveryDayTask(YUrl.YSS_URL_ANDROID_H5
			// + "view/store/theme/setRecommend.html");
			// }
			// }
			// });
			// dialog.show();
			// } else if (day == 6) {
			// dialog = new NewPDialog(activity,
			// R.layout.every_day_task_dialog8_1);
			// dialog.setL(new NewPDialog.TaskLintener() {
			//
			// @Override
			// public void onOKClickLintener() {
			// activity.getSharedPreferences(
			// "EverydayTaskMondayFriday",
			// Context.MODE_PRIVATE)
			// .edit()
			// .putInt("day",
			// Calendar.getInstance().get(
			// Calendar.DAY_OF_MONTH))
			// .commit();
			// // 跳往我的店
			// AppManager.getAppManager()
			// .finishAllActivityOfEveryDayTask();
			// if (MainMenuActivity.instances != null)
			// ((MainFragment) MainMenuActivity.instances
			// .getSupportFragmentManager()
			// .findFragmentByTag("tag")).setIndex(1);
			// // ((MineShopFragment) ((MainFragment)
			// // MainMenuActivity.instances
			// // .getSupportFragmentManager().findFragmentByTag(
			// // "tag")).getChildFragmentManager()
			// //
			// .findFragmentByTag("1")).setUrl("view/store/theme/setRecommend.html");
			// // .loadUrlEveryDayTask("view/store/theme/setRecommend.html");
			//
			// }
			// });
			// dialog.show();
			//
			// }

		} else if (action.equals(everyDayTask_5)) {

		} else if (action.equals(everyDayTask_6)) {

		} else if (action.equals(everyDayTask_7)) {

		} else if (action.equals(everyDayTask_8)) {

		}

	}

	private File file;

	private void dPic(String picPath) {
		try {
			URL url = new URL(picPath);
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			int contentLength = con.getContentLength();
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[8192];
			// 读取到的数据长度
			int len;
			File fileDirec = new File(YConstance.savePicPath);
			if (!fileDirec.exists()) {
				fileDirec.mkdir();
			}
			// 输出的文件流 /sdcard/yssj/
			file = new File(YConstance.savePicPath, "yssjH5Share.jpg");
			if (file.exists()) {
				file.delete();
			}
			OutputStream os = new FileOutputStream(file);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			os.close();
			is.close();
		} catch (Exception e) {
			LogYiFu.e("TAG", "下载失败");
			e.printStackTrace();
		}
	}

	/** 得到分享的链接 */

	private void share() {

		new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) activity, R.string.wait) {

			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
					throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getShareShopLink(context, "");
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if (null == e) {
					if (result.get("status").equals("1")) {
						LogYiFu.e("pic", result.get("shop_pic"));
						String[] picList = result.get("shop_pic").split(",");
						String link = result.get("link");
						download(null, picList, result.get("shop_code"), result.get("shop_name"), result, link);
					} else if (result.get("status").equals("1050")) {// 表明
						Intent intent = new Intent(context, NoShareActivity.class);
						intent.putExtra("isNomal", true);
						context.startActivity(intent); // 分享已经超过了
					}
				}
			}

		}.execute();
	}

	private void download(View v, final String[] picList, final String shop_code, final String shop_name,
			final HashMap<String, String> mapInfos, final String link) {
		new SAsyncTask<Void, Void, Void>((FragmentActivity) activity, v, R.string.wait) {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				File fileDirec = new File(YConstance.savePicPath);
				if (!fileDirec.exists()) {
					fileDirec.mkdir();
				}
				File[] listFiles = new File(YConstance.savePicPath).listFiles();
				if (listFiles.length != 0) {
					LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
					for (File file : listFiles) {
						file.delete();
					}
				}
				// LogYiFu.i("TAG", "piclist=" + picList.length);
				List<String> pics = new ArrayList<String>();
				for (int j = 0; j < picList.length; j++) {
					if (!picList[j].contains("reveal_") && !picList[j].contains("detail_")
							&& !picList[j].contains("real_")) {
						pics.add(picList[j]);
					}
				}
				int j = pics.size() + 1;
				if (pics.size() > 8) {
					j = 9;
				}
				for (int i = 0; i < j; i++) {
					if (i == j - 1) {
						/*
						 * ComModel2.saveQRCode(PaymentSuccessActivity.this,
						 * shop_code);
						 */
						Bitmap bm = QRCreateUtil.createImage(mapInfos.get("QrLink"), 500, 700,
								mapInfos.get("shop_se_price"), activity);// 得到二维码图片
						QRCreateUtil.saveBitmap(bm, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
						// downloadPic(mapInfos.get("qr_pic"), 9);
						break;
					}
					downloadPic(pics.get(i) + "!450", i);
				}
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result) {
				// TODO Auto-generated method stub
				// showShareDialog();
				// MyLogYiFu.e("TAG", "宝贝内容=" + shop.getShop_name() + ",宝贝链接=" +
				// result);
				ShareUtil.configPlatforms(context);
				UMImage umImage = new UMImage(context, R.drawable.ic_launcher);
				ShareUtil.setShareContent(context, umImage, shop_name, link);
				// ShareUtil.share(ShopDetailsActivity.this);
				MyPopupwindow myPopupwindow = new MyPopupwindow(context, 0, null, null);
				myPopupwindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
				super.onPostExecute(context, result);

			}

		}.execute();
	}

	private void downloadPic(String picPath, int i) {
		try {
			URL url = new URL(YUrl.imgurl + picPath);
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			int contentLength = con.getContentLength();
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[8192];
			// 读取到的数据长度
			int len;
			// 输出的文件流 /sdcard/yssj/
			File file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(i)) + ".jpg");
			if (file.exists()) {
				file.delete();
			}
			LogYiFu.e("TAG", "多分享选择下载的图片。。。。");
			OutputStream os = new FileOutputStream(file);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			LogYiFu.i("TAG", "下载完毕。。。file=" + file.toString());
			// 完毕，关闭所有链接
			os.close();
			is.close();
		} catch (Exception e) {
			LogYiFu.e("TAG", "下载失败");
			e.printStackTrace();
		}
	}

	public static void newThread(final long time, final Activity activity, final String action) {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				activity.sendBroadcast(new Intent(action));
			};
		}.start();
	}

}
