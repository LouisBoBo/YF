//package com.yssj.ui.fragment;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.Calendar;
//import java.util.HashMap;
//
////import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.media.UMImage;
//import com.yssj.YConstance;
//import com.yssj.YJApplication;
//import com.yssj.YUrl;
//import com.yssj.YConstance.Pref;
//import com.yssj.activity.R;
//import com.yssj.app.AppManager;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.LoadingDialog;
//import com.yssj.custom.view.MyShopLinkSharePopwindow;
//import com.yssj.custom.view.NewPDialog;
//import com.yssj.entity.QueryPhoneInfo;
//import com.yssj.entity.Store;
//import com.yssj.model.ComModel;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.ui.activity.MineLikeActivity;
//import com.yssj.ui.activity.ShopCartNewNewActivity;
//import com.yssj.ui.activity.logins.LoginActivity;
//import com.yssj.ui.activity.setting.BindPhoneActivity;
//import com.yssj.ui.activity.setting.BlankBackActivity;
//import com.yssj.ui.activity.setting.FeedBackActivity;
//import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
//import com.yssj.ui.receiver.TaskReceiver;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.NetworkUtils;
//import com.yssj.utils.ShareUtil;
//import com.yssj.utils.SharedPreferencesUtil;
//import com.yssj.utils.TakePhotoUtil;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YCache;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.provider.MediaStore;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnTouchListener;
//import android.view.ViewGroup;
//import android.webkit.JavascriptInterface;
//import android.webkit.ValueCallback;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
///***
// * 我的店铺
// * 
// * IP访问：http://183.61.166.16:8080/cloud-h5/view/store/inde.jsp?realm={store.
// * sealm} 域名访问：http://www.{realm}.yssj.com:8080/cloud-h5/view/store/index.jsp
// * 
// * @author Administrator
// * 
// */
//@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
//public class OldMyShopFragment extends Fragment implements OnClickListener {
//	private Integer[] guid = { R.drawable.guid_01, R.drawable.guid_02, R.drawable.guid_03, R.drawable.guid_04 };
//	private static final String TAB = "tab1";
//
//	private WebView webview;
//	private FrameLayout loading_view;
//
//	public static String url = "file:///android_asset/www/edit.html";
//
//	private Store store;
//
//	private com.yssj.entity.UserInfo userInfo;
//
//	public FragmentManager fm;
//
//	private static Context mContext;
//	private boolean phonebool;
//
//	protected ValueCallback<Uri> mUploadMessage;
//	protected int FILECHOOSER_RESULTCODE = 1;
//	private String mCameraFilePath;
//	private Boolean isToShowDialog = false;
//	private int tucaoTask;
//	private String showGuide;
//
//	public FragmentManager getFm() {
//		return fm;
//	}
//
//	private Handler handler = new Handler() {
//
//		public void handleMessage(android.os.Message msg) {
//			if (isHidden()) {
//				return;
//			}
//			if (isPause == 1) {
//				return;
//			}
//			switch (msg.what) {
//
//			// 弹出吐槽
//			case 6: {
//				if (isToShowDialog) {
//					return;
//				}
//				if (mContext.getSharedPreferences("tocao_isupdate", Context.MODE_PRIVATE).getBoolean("tocao_isupdate",
//						false)) {
//					return;
//				}
//				if (mContext.getSharedPreferences("tocao_isshow", Context.MODE_PRIVATE).getBoolean("tocao_isshow",
//						false)) {
//					return;
//				}
//				// if(mContext.getSharedPreferences("isFirstH5Login",
//				// Context.MODE_PRIVATE).getBoolean("isFirstH5Login", true)){
//				// return;
//				// }
//				if (isPause == 1) {
//					return;
//				}
//				final NewPDialog mDialog = new NewPDialog(getActivity(), R.layout.task_dialog9);
//
//				mDialog.setL(new NewPDialog.TaskLintener() {
//					@Override
//					public void onOKClickLintener() {
//						isToShowDialog = false;
//						// 跳到意见反馈
//						// isShow = false;
//
//						Intent intent = new Intent();
//
//						intent.setClass(mContext, FeedBackActivity.class);
//
//						mContext.startActivity(intent);
//
//						((Activity) mContext).finish();
//
//					}
//
//					@Override
//					public void onShouZhiClickLintener() {
//						// TODO Auto-generated method stub
//
//					}
//
//				});
//				mDialog.setF(new NewPDialog.FinishLintener() {
//
//					@Override
//					public void onFinishClickLintener() {
//						isToShowDialog = false;
//						// isShow = false;
//						// TODO Auto-generated method stub
//					}
//				});
//				mDialog.show();
//				isToShowDialog = true;
//				mContext.getSharedPreferences("tocao_isshow", Context.MODE_PRIVATE).edit()
//						.putBoolean("tocao_isshow", true).commit();
//				break;
//			}
//
//			default:
//				break;
//			}
//
//		};
//	};
//
//	public static OldMyShopFragment newInstance(String title, Context context) {
//		OldMyShopFragment fragment = new OldMyShopFragment();
//		Bundle args = new Bundle();
//		args.putString(TAB, title);
//		fragment.setArguments(args);
//
//		mContext = context;
//		return fragment;
//
//	}
//
//	@Override
//	public void onAttach(Activity activity) {
//
//		super.onAttach(activity);
////		((MainMenuActivity) (mContext)).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		// mContext = getActivity();
//		/*
//		 * store = YCache.getCacheStoreSafe(getActivity()); if(null == store){
//		 * Intent intent = new Intent(mContext, MineLikeActivity.class);
//		 * startActivityForResult(intent, requestCode); }
//		 */
//	}
//
//	// private ImageButton img;
//	// private ImageView img_to_mine_like;
//	private FrameLayout fl;
//
//	private Handler mHandler = new Handler();
//	private GuidAdapter adapter;
//	private ViewPager mViewPager;
//	private RelativeLayout img_to_mine_like;
//	private TextView mButton;
//	private LinearLayout mPointContainer;
//	private int mPosition = (Integer.MAX_VALUE / 2) + 1;
//	private MyRunnable runnable = new MyRunnable();
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		View v = inflater.inflate(R.layout.fragment_shop, container, false);
//
//		webview = (WebView) v.findViewById(R.id.webview);
//		loading_view = (FrameLayout) v.findViewById(R.id.loading_view);
//
//		img_to_mine_like = (RelativeLayout) v.findViewById(R.id.img_to_mine_like);
//
//		mViewPager = (ViewPager) v.findViewById(R.id.shop_viewpager);
//		mButton = (TextView) v.findViewById(R.id.shop_button);
//		mPointContainer = (LinearLayout) v.findViewById(R.id.shop_container);
//		for (int i = 0; i < guid.length; i++) {
//			View view = new View(mContext);
//			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(32, 6);
//			view.setBackgroundResource(R.drawable.guid_selected);
//			if (i != 0) {
//				layoutParams.setMargins(14, 0, 0, 0);
//				view.setBackgroundResource(R.drawable.guid_unselected);
//			}
//			mPointContainer.addView(view, layoutParams);
//		}
//		mButton.setOnClickListener(this);
//		adapter = new GuidAdapter();
//		mViewPager.setAdapter(adapter);
//		mViewPager.setCurrentItem(mPosition);
//		mViewPager.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//					mHandler.removeCallbacks(runnable);
//					break;
//				case MotionEvent.ACTION_UP:
//					if (runnable != null) {
//						mHandler.removeCallbacks(runnable);
//					}
//					mHandler.postDelayed(runnable, 3000);
//					break;
//				default:
//					break;
//				}
//				return false;
//			}
//		});
//
//		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
//
//			@Override
//			public void onPageSelected(int position) {
//				int m = position % guid.length;
//				for (int i = 0; i < guid.length; i++) {
//					if (m == i) {
//						mPointContainer.getChildAt(i).setBackgroundResource(R.drawable.guid_selected);
//					} else {
//						mPointContainer.getChildAt(i).setBackgroundResource(R.drawable.guid_unselected);
//					}
//				}
//			}
//
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//
//		// img = (ImageButton) v.findViewById(R.id.img);
//
//		// img=(ImageButton) v.findViewById(R.id.img);
//
//		// img_to_mine_like = (ImageView) v.findViewById(R.id.img_to_mine_like);
//		// SetImageLoader.initImageLoader(null, img_to_mine_like, "drawable://"
//		// + R.drawable.img_to_choose_my_like, "");
//		// img_to_mine_like.setOnClickListener(this);
//		fl = (FrameLayout) v.findViewById(R.id.fl);
//		return v;
//	}
//
//	private boolean isFirst = false;
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		mHandler.postDelayed(runnable, 3000);
//		/**
//		 * WebSettings webseting = webview.getSettings();
//		 * webseting.setDomStorageEnabled(true);
//		 * webseting.setAppCacheMaxSize(1024*1024*16); String appCacheDir =
//		 * mContext.getDir("database", Context.MODE_PRIVATE).getPath();
//		 * webseting.setAppCachePath(appCacheDir);
//		 * webseting.setAllowFileAccess(true);
//		 * webseting.setAllowContentAccess(true);
//		 * webseting.setAppCacheEnabled(true);
//		 * webseting.setLoadWithOverviewMode(true);
//		 * webseting.setUseWideViewPort(true);
//		 * webseting.setCacheMode(WebSettings.LOAD_DEFAULT);
//		 */
//
//		// if(Build.VERSION.SDK_INT==16){
//		// webview.enablecrossdomain41();
//		// }else{
//		// webview.enablecrossdomain();
//		// }
//		webview.getSettings().setJavaScriptEnabled(true);
//		webview.addJavascriptInterface(new upLoadImage(mContext), "android");
//		webview.getSettings().setLoadWithOverviewMode(true);
//		webview.getSettings().setAllowFileAccess(true);
//		isFirst = false;
//		if (NetworkUtils.haveNetworkConnection(getActivity())) {
//			initData();
//		} else {
//			webview.loadUrl("file:///android_asset/error.html");
//		}
//		if (YJApplication.instance.isLoginSucess() == false) {
//			return;
//		}
//		userInfo = YCache.getCacheUser(mContext);
//		if (null == userInfo.getHobby() || userInfo.getHobby().equals("0")) {
//			return;
//		}
//	}
//
//	// 弹出吐槽dialog9
//	private void startThread() {
//		new Thread() {
//			@Override
//			public void run() {
//				tucaoTask = 2;
//				try {
//					Thread.sleep(60 * 1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				tucaoTask = 1;
//				handler.sendEmptyMessage(6);
//			}
//		}.start();
//	}
//
//	class MyRunnable implements Runnable {
//		@Override
//		public void run() {
//			mPosition = mViewPager.getCurrentItem();
//			mPosition++;
//			mViewPager.setCurrentItem(mPosition);
//			adapter.notifyDataSetChanged();
//			mHandler.postDelayed(this, 3000);
//
//		}
//	}
//
//	private int isShow;
//	private int isPause;
//
//	@Override
//	public void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		isPause = 1;
//		// MobclickAgent.onPageEnd("MineShopFragment");
//	}
//
//	private void toMineInfo() {
//		mContext.sendBroadcast(new Intent(TaskReceiver.toMineInfo));
//	}
//
//	@Override
//	public void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		isPause = 0;
//		startThread();
//		// MobclickAgent.onPageStart("MineShopFragment");
//		if (YJApplication.instance.isLoginSucess()) {
//			bindPhone();
//		}
//
//	}
//
//	public void refresh() {
//		// if (NetworkUtils.haveNetworkConnection(getActivity())) {
//		// initData();
//		// } else {
//		// webview.loadUrl("file:///android_asset/error.html");
//		// }
//		if (store != null) {
//			url = "file:///android_asset/www/edit.html";
//		}
//
//		webview.loadUrl(url);
//	}
//
//	private File file;
//
//	private void downloadPic(String picPath) {
//		try {
//			URL url = new URL(picPath);
//			// 打开连接
//			URLConnection con = url.openConnection();
//			// 获得文件的长度
//			int contentLength = con.getContentLength();
//			// 输入流
//			InputStream is = con.getInputStream();
//			// 1K的数据缓冲
//			byte[] bs = new byte[8192];
//			// 读取到的数据长度
//			int len;
//			File fileDirec = new File(YConstance.savePicPath);
//			if (!fileDirec.exists()) {
//				fileDirec.mkdir();
//			}
//			// 输出的文件流 /sdcard/yssj/
//			file = new File(YConstance.savePicPath, "yssjH5Share.jpg");
//			if (file.exists()) {
//				file.delete();
//			}
//			OutputStream os = new FileOutputStream(file);
//			// 开始读取
//			while ((len = is.read(bs)) != -1) {
//				os.write(bs, 0, len);
//			}
//			os.close();
//			is.close();
//		} catch (Exception e) {
//			LogYiFu.e("TAG", "下载失败");
//			e.printStackTrace();
//		}
//	}
//
//	private class upLoadImage {
//		private Context context;
//
//		public upLoadImage(Context context) {
//			super();
//			this.context = context;
//		}
//
//		@JavascriptInterface
//		public void show() {
//			TakePhotoUtil.doUserPic((Activity) context);
//		}
//
//		@JavascriptInterface
//		public void toLogin() {
//			if (YJApplication.isLogined) {
//				YCache.cleanToken(context);
//				YCache.cleanUserInfo(context);
//				ComModel.clearLoginFlag(context);
//				AppManager.getAppManager().finishAllActivityOfEveryDayTask();
//				YJApplication.isLogined = false;
//
//				if (LoginActivity.instances != null) {
//					LoginActivity.instances.finish();
//				}
//
//				Intent intent = new Intent(context, LoginActivity.class);
//				intent.putExtra("login_register", "login");
//				((FragmentActivity) context).startActivityForResult(intent, 239);
//			}
//		}
//
//		/**
//		 * 跳转到登录页
//		 * 
//		 * @param code
//		 */
//		@JavascriptInterface
//		public void shopDetail(String code) {
//			Intent intent = new Intent(context, ShopDetailsActivity.class);
//			intent.putExtra("code", code);
//			startActivity(intent);
//			getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
//			;
//		}
//
//		@JavascriptInterface
//		public void addPic(String code) {
//			TakePhotoUtil.doPickPhotoAction((Activity) context);
//		}
//
//		@JavascriptInterface
//		public String isFirst() {
//			// if (isFirst) {
//			// isFirst = false;
//			// return "true";
//			// }
//			// checkStore();
//			showGuide = context.getSharedPreferences("isFirstGuide", Context.MODE_PRIVATE).getString("showGuide",
//					"false");
//			context.getSharedPreferences("isFirstGuide", Context.MODE_PRIVATE).edit().putString("showGuide", "false")
//					.commit();
//			return showGuide;
//		}
//
//		@JavascriptInterface
//		public String isFirstShare() {
//			int c = context.getSharedPreferences("H5SHARE", Context.MODE_PRIVATE).getInt("H5SHARE", 0);
//			int curr = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//			if (c != curr) {
//				context.getSharedPreferences("H5SHARE", Context.MODE_PRIVATE).edit().putInt("H5SHARE", curr).commit();
//				return "true";
//			}
//			return "false";
//		}
//
//		@JavascriptInterface
//		public void h5ToShopCart() {
//			Intent intent = new Intent(context, ShopCartNewNewActivity.class);
//			startActivityForResult(intent, 10001);
//		}
//
//		@JavascriptInterface
//		public void h5ToMineInfo() {
//			toMineInfo();
//		}
//
//		@JavascriptInterface
//		public void firstLogin() {
//			// if(isLogin){
//			// mContext.getSharedPreferences("isFirstH5Login",
//			// Context.MODE_PRIVATE).edit().putBoolean("isFirstH5Login",
//			// false).commit();
//			// }
//		}
//
//		@JavascriptInterface
//		public String returnRealm() {
//			return YCache.getCacheStore(context).getRealm();
//		}
//
//		@JavascriptInterface
//		public String returnToken() {
//			return YCache.getCacheToken(context);
//		}
//
//		@JavascriptInterface
//		public String returnIsAndroid() {
//			return "true";
//		}
//
//		@JavascriptInterface
//		public String returnParam() {
//			String realm = YCache.getCacheStore(context).getRealm();
//			String token = YCache.getCacheToken(context);
//			return realm + "," + token + ",true";
//		}
//
//		@JavascriptInterface
//		public String returnContent(String style) {
//			StringBuffer sb = new StringBuffer();
//			try {
//				InputStream is = context.getResources().getAssets().open(style);
//				InputStreamReader inputStreamReader = new InputStreamReader(is, "UTF-8");
//				BufferedReader reader = new BufferedReader(inputStreamReader);
//				String line;
//				while ((line = reader.readLine()) != null) {
//					sb.append(line);
//					sb.append("\n");
//				}
//				reader.close();
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return sb.toString();
//		}
//
//		// 每日任务供h5调接口
//		@JavascriptInterface
//		public void h5ShareMondayDoneDialog(int type) {
//
//			mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE).edit()
//					.putBoolean("isDone", true).commit();
//			mContext.getSharedPreferences("TheFirstTask", Context.MODE_PRIVATE).edit().putInt("type", type).commit();
//		}
//
//		@JavascriptInterface
//		// 更换模板成功
//		public void h5ShareMondayDialog() {
//			if (mContext.getSharedPreferences("isFirstH5Login", Context.MODE_PRIVATE).getBoolean("isFirstH5Login",
//					true)) {
//				return;
//			}
//			if (isToShowDialog) {
//				return;
//			}
//			if ((Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 2)
//					&& mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE)
//							.getBoolean("isDone", false)) {
//				if (mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).getInt("day",
//						0) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
//					return;
//				}
//				NewPDialog dialog = new NewPDialog(mContext, R.layout.every_day_task_dialog4_2);
//				dialog.setF(new NewPDialog.FinishLintener() {
//
//					@Override
//					public void onFinishClickLintener() {
//						isToShowDialog = false;
//						// TODO Auto-generated method stub
//						mContext.getSharedPreferences("EverydayTaskMondayFriday", Context.MODE_PRIVATE).edit()
//								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//						mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).edit()
//								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//						mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE).edit()
//								.putBoolean("isDone", false).commit();
//					}
//				});
//				dialog.setL(new NewPDialog.TaskLintener() {
//
//					@Override
//					public void onOKClickLintener() {
//						isToShowDialog = false;
//						mContext.getSharedPreferences("EverydayTaskMondayFriday", Context.MODE_PRIVATE).edit()
//								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//						mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).edit()
//								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//						mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE).edit()
//								.putBoolean("isDone", false).commit();
//					}
//
//					@Override
//					public void onShouZhiClickLintener() {
//						isToShowDialog = false;
//						// TODO Auto-generated method stub
//						shareStoreLink(mContext);
//						mContext.getSharedPreferences("EverydayTaskMondayFriday", Context.MODE_PRIVATE).edit()
//								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//						mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).edit()
//								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//						mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE).edit()
//								.putBoolean("isDone", false).commit();
//					}
//				});
//				dialog.show();
//				isToShowDialog = true;
//				// 发布公告
//			} else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 3
//					&& mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE)
//							.getBoolean("isDone", false)) {
//				if (mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).getInt("day",
//						0) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
//					return;
//				}
//				NewPDialog dialog = new NewPDialog(mContext, R.layout.every_day_task_dialog5_2);
//				dialog.setF(new NewPDialog.FinishLintener() {
//
//					@Override
//					public void onFinishClickLintener() {
//						isToShowDialog = false;
//						// TODO Auto-generated method stub
//						mContext.getSharedPreferences("EverydayTaskMondayFriday", Context.MODE_PRIVATE).edit()
//								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//						mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).edit()
//								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//
//						mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE).edit()
//								.putBoolean("isDone", false).commit();
//					}
//				});
//				dialog.setL(new NewPDialog.TaskLintener() {
//
//					@Override
//					public void onOKClickLintener() {
//						isToShowDialog = false;
//						mContext.getSharedPreferences("EverydayTaskMondayFriday", Context.MODE_PRIVATE).edit()
//								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//						mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).edit()
//								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//
//						mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE).edit()
//								.putBoolean("isDone", false).commit();
//					}
//
//					@Override
//					public void onShouZhiClickLintener() {
//						isToShowDialog = false;
//						// TODO Auto-generated method stub
//						shareStoreLink(mContext);
//						mContext.getSharedPreferences("EverydayTaskMondayFriday", Context.MODE_PRIVATE).edit()
//								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//						mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).edit()
//								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//						mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE).edit()
//								.putBoolean("isDone", false).commit();
//					}
//				});
//				dialog.show();
//				isToShowDialog = true;
//				// 更换轮播图成功
//			} else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 4
//					&& mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE)
//							.getBoolean("isDone", false)) {
//				if (mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).getInt("day",
//						0) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
//					return;
//				}
////				NewPDialog dialog = new NewPDialog(mContext, R.layout.every_day_task_dialog6_2);
////				dialog.setF(new NewPDialog.FinishLintener() {
////
////					@Override
////					public void onFinishClickLintener() {
////						isToShowDialog = false;
////						// TODO Auto-generated method stub
////						mContext.getSharedPreferences("EverydayTaskMondayFriday", Context.MODE_PRIVATE).edit()
////								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
////						mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).edit()
////								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
////
////						mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE).edit()
////								.putBoolean("isDone", false).commit();
////					}
////				});
////				dialog.setL(new NewPDialog.TaskLintener() {
////
////					@Override
////					public void onOKClickLintener() {
////						isToShowDialog = false;
////						mContext.getSharedPreferences("EverydayTaskMondayFriday", Context.MODE_PRIVATE).edit()
////								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
////						mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).edit()
////								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
////
////						mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE).edit()
////								.putBoolean("isDone", false).commit();
////					}
////
////					@Override
////					public void onShouZhiClickLintener() {
////						isToShowDialog = false;
////						// TODO Auto-generated method stub
////						shareStoreLink(mContext);
////						mContext.getSharedPreferences("EverydayTaskMondayFriday", Context.MODE_PRIVATE).edit()
////								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
////						mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).edit()
////								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
////						mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE).edit()
////								.putBoolean("isDone", false).commit();
////					}
////				});
////				dialog.show();
//				isToShowDialog = true;
//				// 更换老板娘最爱成功
//			} else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 5
//					&& mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE)
//							.getBoolean("isDone", false)) {
//				if (mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).getInt("day",
//						0) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
//					return;
//				}
////				NewPDialog dialog = new NewPDialog(mContext, R.layout.every_day_task_dialog6_2);
////				dialog.setF(new NewPDialog.FinishLintener() {
////
////					@Override
////					public void onFinishClickLintener() {
////						isToShowDialog = false;
////						// TODO Auto-generated method stub
////						mContext.getSharedPreferences("EverydayTaskMondayFriday", Context.MODE_PRIVATE).edit()
////								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
////						mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).edit()
////								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
////
////						mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE).edit()
////								.putBoolean("isDone", false).commit();
////					}
////				});
////				dialog.setL(new NewPDialog.TaskLintener() {
////
////					@Override
////					public void onOKClickLintener() {
////						isToShowDialog = false;
////						mContext.getSharedPreferences("EverydayTaskMondayFriday", Context.MODE_PRIVATE).edit()
////								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
////						mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).edit()
////								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
////
////						mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE).edit()
////								.putBoolean("isDone", false).commit();
////					}
////
////					@Override
////					public void onShouZhiClickLintener() {
////						isToShowDialog = false;
////						// TODO Auto-generated method stub
////						shareStoreLink(mContext);
////						mContext.getSharedPreferences("EverydayTaskMondayFriday", Context.MODE_PRIVATE).edit()
////								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
////						mContext.getSharedPreferences("EverydayTaskMondayFridayH5", Context.MODE_PRIVATE).edit()
////								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
////						mContext.getSharedPreferences("EverydayTaskMondayFridayH5Done", Context.MODE_PRIVATE).edit()
////								.putBoolean("isDone", false).commit();
////					}
////				});
////				dialog.show();
//				isToShowDialog = true;
//			}
//		}
//
//		@JavascriptInterface
//		public void h5ShareTuesdayDialog() {
//
//		}
//
//		@JavascriptInterface
//		public void h5ShareWednesdayDialog() {
//
//		}
//
//		@JavascriptInterface
//		public void h5ShareThursdayDialog() {
//
//		}
//
//		// @JavascriptInterface
//		// public String showGuide() {
//		// checkStore();
//		// MyLogYiFu.e("showGuide,,,,,",showGuide);
//		// return showGuide;
//		// }
//
//		@JavascriptInterface
//		public void showGuideAlready() {
//			if (null == showGuide || "".equals(showGuide)) {
//				return;
//			}
//			if (showGuide.equals("true")) {
//
//				Boolean Nosign = SharedPreferencesUtil.getBooleanData(context, "kaidiandialogisshowed", false);
//
//				if (!Nosign) {
//					toSignFragment();
//				}
//
//			}
//
//		}
//
//		@JavascriptInterface
//		public void h5Share() {
//			LoadingDialog.show((FragmentActivity) mContext);
//			mContext.getSharedPreferences("isFirstH5Login", Context.MODE_PRIVATE).edit()
//					.putBoolean("isFirstH5Login", false).commit();
//			new AsyncTask<Void, Void, Void>() {
//				@Override
//				protected Void doInBackground(Void... arg0) {
//
//					downloadPic("http://yssj668.b0.upaiyun.com/share/android/900_900_1_android.jpg");
//					return null;
//				}
//
//				@Override
//				protected void onPostExecute(Void result) {
//					super.onPostExecute(result);
//					LoadingDialog.hide(context);
//					ShareUtil.shareH5(context, file);
//				};
//
//			}.execute();
//		}
//
//		@JavascriptInterface
//		public void h5ShareStoreLink() {
//			SharedPreferencesUtil.saveBooleanData(context, Pref.ISSHOWSTORE, false);
//			yunYunTongJi(store.getRealm(), 1, 1);
//			UMImage umImage = new UMImage(context, R.drawable.is_match);
//			ShareUtil.configPlatforms(context);
//			ShareUtil.setShareContent(context, umImage, "",
//					YUrl.YSS_URL_ANDROID_H5 + "view/store/index.html" + "?realm=" + store.getRealm());
//
//			// if (myPopupwindow == null) {
//			// myPopupwindow = new MyShopLinkSharePopwindow(
//			// (FragmentActivity) context);
//			//
//			// }
//			// myPopupwindow.show();
//			ShareUtil.shareShopLink(SHARE_MEDIA.WEIXIN_CIRCLE, context);
//		}
//
//		private void yunYunTongJi(final String shop_code, final int type, final int tab_type) {
//			new SAsyncTask<Void, Void, HashMap<String, String>>(getActivity(), R.string.wait) {
//
//				@Override
//				protected boolean isHandleException() {
//					// TODO Auto-generated method stub
//					return true;
//				}
//
//				@Override
//				protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
//						throws Exception {
//
//					return ComModel2.getOperator(context, shop_code, type, tab_type);
//				}
//
//				@Override
//				protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
//					// TODO Auto-generated method stub
//					super.onPostExecute(context, result, e);
//
//				}
//
//			}.execute();
//		}
//
//		@JavascriptInterface
//		public void editName(String name) {
//			store.setS_name(name);
//			YCache.setCacheStore(context, store);
//		}
//
//	}
//
//	int[] array = null;
//
//	MyShopLinkSharePopwindow myPopupwindow;
//
//	// boolean isLogin;
//	/**
//	 * Called when the activity is first created.
//	 */
//	int i = 0;
//
//	@Override
//	public void onHiddenChanged(boolean hidden) {
//		// TODO Auto-generated method stub
//		super.onHiddenChanged(hidden);
//		if (!hidden) {
//			if (YJApplication.instance.isLoginSucess()) {
//				bindPhone();
//			}
//			if (tucaoTask == 1) {
//				handler.sendEmptyMessage(6);
//				tucaoTask = 2;
//			}
//			if ((mContext.getSharedPreferences("isShowGuide", Context.MODE_PRIVATE).getString("isAlready", "false"))
//					.equals("already")) {
//				NewPDialog dialog = new NewPDialog(mContext, R.layout.task_dialog5_2);
//				dialog.setL(new NewPDialog.TaskLintener() {
//
//					@Override
//					public void onOKClickLintener() {
//						//
//						NewPDialog dialog = new NewPDialog(mContext, R.layout.task_dialog5_1);
//						dialog.setL(new NewPDialog.TaskLintener() {
//							@Override
//							public void onOKClickLintener() {
//								mContext.getSharedPreferences("isShowGuide", Context.MODE_PRIVATE).edit()
//										.putString("isAlready", "done").commit();
//							}
//
//							@Override
//							public void onShouZhiClickLintener() {
//								// TODO Auto-generated method stub
//								shareStoreLink(mContext);
//								mContext.getSharedPreferences("isShowGuide", Context.MODE_PRIVATE).edit()
//										.putString("isAlready", "done").commit();
//							}
//						});
//						dialog.show();
//						mContext.getSharedPreferences("isShowGuide", Context.MODE_PRIVATE).edit()
//								.putString("isAlready", "done").commit();
//					}
//
//					@Override
//					public void onShouZhiClickLintener() {
//						// TODO Auto-generated method stub
//
//					}
//				});
//				dialog.show();
//				mContext.getSharedPreferences("isShowGuide", Context.MODE_PRIVATE).edit().putString("isAlready", "done")
//						.commit();
//			}
//		}
//		if (hidden) {
//			return;
//		}
//		if (YJApplication.instance.isLoginSucess() == false) {
//			initData();
//			return;
//		}
//		Store s = YCache.getCacheStore(mContext);
//		String token = YCache.getCacheToken(mContext);
//		// MyLogYiFu.e("token", token);
//		// MyLogYiFu.e("this.token", this.token);
//		if ((store != null && store.getId() != s.getId()) || (store == null && s != null)
//				|| !token.equals(this.token)) {
//			url = "file:///android_asset/www/edit.html";
//			initData();
//		}
//	}
//
//	private String token;
//
//	private boolean isShowDialog = false;
//
//	public WebView getWebview() {
//		return webview;
//	}
//
//	@SuppressLint("SetJavaScriptEnabled")
//	public void initData() {
//
//		if (YJApplication.instance.isLoginSucess() == false) {
//			img_to_mine_like.setVisibility(View.VISIBLE);
//			fl.setVisibility(View.GONE);
//			isShow = 0;
//			return;
//		}
//		store = YCache.getCacheStoreSafe(mContext);
//		token = YCache.getCacheToken(mContext);
//		userInfo = YCache.getCacheUserSafe(mContext);
//		LogYiFu.e("djifejid大大大", userInfo.getHobby());
//		if (null == userInfo.getHobby() || userInfo.getHobby().equals("0") || userInfo.getHobby().equals("null")) {
//			img_to_mine_like.setVisibility(View.VISIBLE);
//			fl.setVisibility(View.GONE);
//			return;
//		} else {
//			img_to_mine_like.setVisibility(View.GONE);
//			fl.setVisibility(View.VISIBLE);
//		}
//		if (isToShowDialog) {
//			return;
//		}
//		if (isShowDialog) {
//			isShowDialog = false;
//
//			isToShowDialog = true;
//		}
//		fm = getChildFragmentManager();
//		webview.setWebViewClient(new WebViewClient() {
//
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				if (url.contains("tel:")) {
//					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//					startActivity(intent);
//					return true;
//				}
//				return false;
//
//			}
//
//			@Override
//			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//				super.onReceivedError(view, errorCode, description, failingUrl);
//				if (errorCode == -6) {
//					webview.loadUrl("file:///android_asset/error.html");
//				}
//
//			}
//
//			@Override
//			public void onPageStarted(WebView view, String url, Bitmap favicon) {
//				super.onPageStarted(view, url, favicon);
//
//			}
//
//			@Override
//			public void onPageFinished(WebView view, String url) {
//				super.onPageFinished(view, url);
//			}
//		});
//
//		webview.loadUrl(url);
//		this.url = "file:///android_asset/www/edit.html";
//	}
//
//	private Intent createDefaultOpenableIntent() {
//		// Create and return a chooser with the default OPENABLE
//		// actions including the camera, camcorder and sound
//		// recorder where available.
//		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//		i.addCategory(Intent.CATEGORY_OPENABLE);
//		i.setType("image/*");
//
//		Intent chooser = createChooserIntent(createCameraIntent());
//		chooser.putExtra(Intent.EXTRA_INTENT, i);
//		return chooser;
//	}
//
//	private Intent createChooserIntent(Intent... intents) {
//		Intent chooser = new Intent(Intent.ACTION_CHOOSER);
//		chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
//		chooser.putExtra(Intent.EXTRA_TITLE, "请选择");
//		return chooser;
//	}
//
//	private Intent createCameraIntent() {
//		// 注意：此处代码主要目的是将拍照文件保存在 browser-photos 文件夹下（非系统默认文件夹）
//		// 如不需要这样处理，可以简化代码
//
//		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		File externalDataDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//		File cameraDataDir = new File(externalDataDir.getPath() + "/Images/");
//		cameraDataDir.mkdirs();
//		mCameraFilePath = cameraDataDir.getPath() + System.currentTimeMillis() + ".jpg";
//		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));
//		return cameraIntent;
//	}
//
//	public void onBackPressed() {
//		if (webview.canGoBack()) {
//			webview.goBack();
//		} else {
//			getActivity().onBackPressed();
//		}
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.shop_button:
//			if (YJApplication.instance.isLoginSucess() == false) {
//				// ToLoginDialog loginDialog = new ToLoginDialog(mContext);
//				// loginDialog.setRequestCode(13334);
//				// loginDialog.show();
//
//				if (LoginActivity.instances != null) {
//					LoginActivity.instances.finish();
//				}
//
//				Intent intent = new Intent(getActivity(), LoginActivity.class);
//				intent.putExtra("login_register", "login");
//				((FragmentActivity) getActivity()).startActivityForResult(intent, 13334);
//				return;
//			}
//
//			// 判断是否有绑定手机和微信授权
//			if (!phonebool) {
//				customDialog();
//				return;
//			}
//
//			// if (null == YCache.getCacheUser(mContext).getUuid()
//			// || "".equals(YCache.getCacheUser(mContext)
//			// .getUuid())||
//			// "null".equals(YCache.getCacheUser(mContext)
//			// .getUuid())) {
//			//// openId(SHARE_MEDIA.WEIXIN, 0, getActivity());
//			// PublicUtil.Uuid(SHARE_MEDIA.WEIXIN, 0, getActivity());
//			// return;
//			// }
//
//			Intent intent = new Intent(mContext, MineLikeActivity.class);
//			isFirst = true;
//			isShowDialog = true;
//			((MainMenuActivity) mContext).startActivityForResult(intent, 13334);
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	public static void shareStoreLink(Context context) {
//		Store store = YCache.getCacheStore(context);
//		UMImage umImage = new UMImage(context, R.drawable.is_match);
//		ShareUtil.configPlatforms(context);
//		ShareUtil.setShareContent(context, umImage, "",
//				YUrl.YSS_URL_ANDROID_H5 + "view/store/index.html" + "?realm=" + store.getRealm());
//		MyShopLinkSharePopwindow myPopupwindow = new MyShopLinkSharePopwindow((FragmentActivity) context);
//		myPopupwindow.show();
//	}
//
//	class GuidAdapter extends PagerAdapter {
//
//		@Override
//		public int getCount() {
//			// return guid.length;
//			return Integer.MAX_VALUE;
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			return arg0 == arg1;
//		}
//
//		@Override
//		public Object instantiateItem(ViewGroup container, int position) {
//			position = position % guid.length;
//			ImageView view = new ImageView(mContext);
//			view.setBackgroundResource(guid[position]);
//			container.addView(view);
//			return view;
//		}
//
//		@Override
//		public void destroyItem(ViewGroup container, int position, Object object) {
//			position = position % guid.length;
//			container.removeView((View) object);
//
//		}
//	}
//
//	private void toSignFragment() {
//		mContext.getSharedPreferences("isShowGuide", Context.MODE_PRIVATE).edit().putString("isAlready", "true")
//				.commit();
//		showGuide = "false";
//		AppManager.getAppManager().finishAllActivityOfEveryDayTask();
//		// if (MainMenuActivity.instances != null)
//		// ((MainFragment) MainMenuActivity.instances
//		// .getSupportFragmentManager().findFragmentByTag("tag"))
//		// .setIndex(3);
//		Intent intent = new Intent(mContext, BlankBackActivity.class);
//		startActivity(intent);
//	}
//
//	private AlertDialog dialog;
//
//	private void customDialog() {
//		AlertDialog.Builder builder = new Builder(mContext);
//		// 自定义一个布局文件
//		View view = View.inflate(mContext, R.layout.payback_esc_apply_dialog, null);
//		TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
//		tv_des.setText("为了您的账户安全，请先绑定手机");
//
//		Button ok = (Button) view.findViewById(R.id.ok);
//		ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
//		Button cancel = (Button) view.findViewById(R.id.cancel);
//
//		cancel.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// 把这个对话框取消掉
//				dialog.dismiss();
//
//			}
//		});
//
//		ok.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(mContext, BindPhoneActivity.class);
//				startActivity(intent);
//				dialog.dismiss();
//			}
//		});
//
//		dialog = builder.create();
//		dialog.setView(view, 0, 0, 0, 0);
//		dialog.show();
//	}
//
//	private void bindPhone() {
//
//		new SAsyncTask<Void, Void, QueryPhoneInfo>((FragmentActivity) mContext) {
//
//			@Override
//			protected QueryPhoneInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
//
//				return ComModel.bindPhone(context);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, QueryPhoneInfo result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (null == e) {
//					if (result != null && "1".equals(result.getStatus())) {
//						phonebool = result.isBool();
//					} else {
//						ToastUtil.showLongText(context, "糟糕，出错了~~~");
//					}
//				}
//			}
//
//		}.execute();
//	}
//
//}
