//package com.yssj.ui.fragment;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URL;
//import java.net.URLConnection;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import com.umeng.analytics.MobclickAgent;
//import com.umeng.socialize.media.UMImage;
//import com.yssj.YConstance;
//import com.yssj.YJApplication;
//import com.yssj.YUrl;
//import com.yssj.activity.R;
//import com.yssj.app.AppManager;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.CustImageGallery;
//import com.yssj.custom.view.LoadingDialog;
//import com.yssj.custom.view.MyPopupwindow;
//import com.yssj.custom.view.MyTitleView;
//import com.yssj.custom.view.NewPDialog;
//import com.yssj.custom.view.ScrollPagerList;
//import com.yssj.custom.view.ToLoginDialog;
//import com.yssj.custom.view.MyTitleView.OnCheckTitleLentener;
//import com.yssj.custom.view.ScrollPagerList.MyOnRefreshLintener;
//import com.yssj.custom.view.SlideShowView;
//import com.yssj.custom.view.ZeroShopScrollPagerList;
//import com.yssj.custom.view.ZeroShopScrollPagerList.ZeroOnRefreshLintener;
//import com.yssj.custom.view.ZeroShopSlideShowView;
//import com.yssj.data.YDBHelper;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.entity.ShopOption;
//import com.yssj.entity.UserInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.service.ApkDownloadManager;
//import com.yssj.ui.activity.MainFragment;
//import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.ui.activity.ShopCartActivity;
//import com.yssj.ui.activity.logins.LoginActivity;
//import com.yssj.ui.activity.setting.FeedBackActivity;
//import com.yssj.ui.activity.shopdetails.NoShareActivity;
//import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
//import com.yssj.ui.dialog.CheckVersionChangDialog;
//import com.yssj.ui.fragment.ZeroShopItemFragment.onZeroShopRefreshListener;
//import com.yssj.ui.fragment.orderinfo.OrderDetailsActivity;
//import com.yssj.ui.receiver.TaskReceiver;
//import com.yssj.utils.CheckStrUtil;
//import com.yssj.utils.MD5Tools;
//import com.yssj.utils.QRCreateUtil;
//import com.yssj.utils.ShareUtil;
//import com.yssj.utils.SharedPreferencesUtil;
//import com.yssj.utils.StringUtils;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YCache;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.AlertDialog.Builder;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.DialogInterface.OnCancelListener;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.content.pm.PackageManager.NameNotFoundException;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
///**
// * 零元购页面
// * 
// */
//public class ZeroShopFragment extends Fragment implements OnClickListener,
//		ZeroOnRefreshLintener, onZeroShopRefreshListener {
//
//	private static Context mContext;
//
//	private List<HashMap<String, String>> listTitle;
//
//	private ViewPager mViewPager;
//
//	private LinearLayout title;
//
//	private ZeroShopItemFragment[] fragments;
//
//	public static int width;
//
//	public static int height;
//
//	private List<ShopOption> pagerList;
//	private Boolean isShow = false;
//
//	// private CustImageGallery mGallery;
//
//	// private ImageView imageViewPager;
//
//	private ZeroShopSlideShowView imageViewPager;
//
//	private List<ShopOption> gallList;
//
//	// private List<ShopOption> pagerList;
//
//	private ZeroShopScrollPagerList mView;
//
//	private int currentPosition = 0;
//
//	private PagerAdapter mAdapter;
//
//	private LinearLayout ll_title_one, ll_title_two, ll_title_three,
//			ll_title_four;
//
//	private ToLoginDialog loginDialog;
//private Boolean isOpen;
//	private TextView tv_shop_cart_count;
//	private int num = 0;
//private String imei2;
//	public ZeroShopFragment() {
//		super();
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		return inflater.inflate(R.layout.zero_shop_fragment, container, false);
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onActivityCreated(savedInstanceState);
//		imei2 = CheckStrUtil
//				.getImei(mContext);
//		fiveYuanDialog();
//	
//		DisplayMetrics dm = new DisplayMetrics();
//		((Activity) mContext).getWindowManager().getDefaultDisplay()
//				.getMetrics(dm);
//		width = dm.widthPixels;
//		height = dm.heightPixels;
//		float s = (float) (9.6 * (float) 3);
//		Log.e("值", s + "");
//		TextView tv_title = (TextView) getView().findViewById(R.id.tv_title);
//		tv_title.setOnClickListener(this);
//		ll_title_one = (LinearLayout) getView().findViewById(R.id.ll_title_one);
//		ll_title_two = (LinearLayout) getView().findViewById(R.id.ll_title_two);
//		ll_title_three = (LinearLayout) getView().findViewById(
//				R.id.ll_title_three);
//		ll_title_four = (LinearLayout) getView().findViewById(
//				R.id.ll_title_four);
//		mView = (ZeroShopScrollPagerList) getView().findViewById(R.id.zeroView);
//
//		mView.setZeroOnRefreshLintener(this);
//		getView().findViewById(R.id.img_shop_cart).setOnClickListener(this);
//		tv_shop_cart_count = (TextView) getView().findViewById(
//				R.id.tv_shop_cart_count);
//
//		// YDBHelper helper = new YDBHelper(mContext);
//		// String sql =
//		// "select * from sort_info where p_id = 0 and is_show = 1 order by sequence";
//		// listTitle = helper.query(sql);
//		mViewPager = (ViewPager) getView().findViewById(
//				R.id.zero_shop_content_viewpager);
//		title = (LinearLayout) getView().findViewById(R.id.zero_shop_title);
//		imageViewPager = (ZeroShopSlideShowView) getView().findViewById(
//				R.id.image_view_pager);
//		imageViewPager.getLayoutParams().height = width / 2;
//		// mGallery=(CustImageGallery) getView().findViewById(R.id.images);
//		checkVersion(null);
//		getTuijianData();
//		initTextView();
//		// getShopCartCount();
//		fragments = new ZeroShopItemFragment[4];
//
//		mAdapter = new PagerAdapter(getChildFragmentManager());
//		mViewPager.setAdapter(mAdapter);
//		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
//
//			@Override
//			public void onPageSelected(int arg0) {
//				setTextTitleSelectedColor(arg0);
//				currentPosition = arg0;
//				if (null != ((ZeroShopItemFragment) mAdapter.getItem(arg0))
//						.getmList()
//						&& ((ZeroShopItemFragment) mAdapter.getItem(arg0))
//								.getmList().getCount() != 0)
//					((ZeroShopItemFragment) mAdapter.getItem(arg0)).getmList()
//							.setSelection(0);
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
//
//		});
//
//		startThread();
////		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
////		String date = sDateFormat.format(new java.util.Date());
////
////		String nowData = SharedPreferencesUtil.getStringData(mContext, "dates", "");
////		String chan =SharedPreferencesUtil.getStringData(getActivity(), "tags","");
////		
////		if(chan.equals("10")||chan.equals("11")){
////			if (!nowData.equals(date)) {
////				new CheckVersionChangDialog(mContext, R.style.DialogStyle1,getActivity()).show();
////
////			}	
////			
////		}
//		
//		
//	}
//
//	private void fiveYuanDialog() {
//		new SAsyncTask<Void, Void, HashMap<String, Object>>(
//				(FragmentActivity) mContext, R.string.wait) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.fiveYuanDialog(context,imei2);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, Object> result, Exception e) {
//				if (null == e && result != null) {
//					String isOpen = (String) result.get("isOpen");
//					System.out.println("*************************************************"+result.get("isOpen"));
//					if (isOpen.equals("false")) {
//						if (!(mContext.getSharedPreferences("hongbao_tian",
//								Context.MODE_PRIVATE).getInt("hongbao_tian", 0) == Calendar
//								.getInstance().get(Calendar.DAY_OF_MONTH))) {
//							final NewPDialog mDialog = new NewPDialog(getActivity(),
//									R.layout.task_dialog11);
//
//							mDialog.setL(new NewPDialog.TaskLintener() {
//								@Override
//								public void onOKClickLintener() {
//									isShow = false;
//									if (MainMenuActivity.instances != null)
//										// 跳到签到页面
//										((MainFragment) MainMenuActivity.instances
//												.getSupportFragmentManager()
//												.findFragmentByTag("tag")).setIndex(2);
//
//								}
//
//								@Override
//								public void onShouZhiClickLintener() {
//									// TODO Auto-generated method stub
//
//								}
//							});
//							mDialog.setF(new NewPDialog.FinishLintener() {
//
//								@Override
//								public void onFinishClickLintener() {
//									isShow = false;
//									// TODO Auto-generated method stub
//								}
//							});
//							Window window = mDialog.getWindow();
//							window.setWindowAnimations(R.style.dlg_top_to_down_some);
//							mDialog.show();
//							getActivity()
//									.getSharedPreferences("hongbao_tian",
//											Context.MODE_PRIVATE)
//									.edit()
//									.putInt("hongbao_tian",
//											Calendar.getInstance().get(
//													Calendar.DAY_OF_MONTH)).commit();
//							isShow = true;
//						}
//					} else {
//						if ((mContext.getSharedPreferences("cishu", Context.MODE_PRIVATE)
//								.getInt("cishu", 0) < 3)&&(!(mContext.getSharedPreferences("hongbao_tian",
//										Context.MODE_PRIVATE).getInt("hongbao_tian", 0) == Calendar
//										.getInstance().get(Calendar.DAY_OF_MONTH)))) {
//							if (!(mContext.getSharedPreferences("hongbao_tian2",
//									Context.MODE_PRIVATE).getInt("hongbao_tian2", 0) == Calendar
//									.getInstance().get(Calendar.DAY_OF_MONTH))) {
//								final NewPDialog mDialog = new NewPDialog(getActivity(),
//										R.layout.task_dialog10);
//
//								mDialog.setL(new NewPDialog.TaskLintener() {
//									@Override
//									public void onOKClickLintener() {
//										isShow = false;
//										if (MainMenuActivity.instances != null)
//											// 跳到签到页面
//											((MainFragment) MainMenuActivity.instances
//													.getSupportFragmentManager()
//													.findFragmentByTag("tag")).setIndex(2);
//									}
//
//									@Override
//									public void onShouZhiClickLintener() {
//										// TODO Auto-generated method stub
//									}
//								});
//								mDialog.setF(new NewPDialog.FinishLintener() {
//									@Override
//									public void onFinishClickLintener() {
//										isShow = false;
//										// TODO Auto-generated method stub
//									}
//								});
//								Window window = mDialog.getWindow();
//								window.setWindowAnimations(R.style.dlg_top_to_down_some);
//								mDialog.show();
//								getActivity()
//										.getSharedPreferences("hongbao_tian2",
//												Context.MODE_PRIVATE)
//										.edit()
//										.putInt("hongbao_tian2",
//												Calendar.getInstance().get(
//														Calendar.DAY_OF_MONTH)).commit();
//								num = mContext.getSharedPreferences("cishu",
//										Context.MODE_PRIVATE).getInt("cishu", 0);
//								num++;
//								getActivity()
//										.getSharedPreferences("cishu", Context.MODE_PRIVATE)
//										.edit().putInt("cishu", num).commit();
//								isShow = true;
//							}
//						}
//					}
//				}
//				super.onPostExecute(context, result, e);
//			}
//
//		}.execute();
//	}
//
//	// 弹出吐槽dialog9
//	private void startThread() {
//
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
//
//	}
//
//	private int isNewMemberTask_1, isEveryDayTask3_4, isLoginNewTask,
//			isNewShop, isEverydayShareTask, tucaoTask;
//
//	// 检测更新
//	private void checkVersion(View v) {
//		try {
//			PackageManager pm = mContext.getPackageManager();
//			PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
//			int versionCode = pi.versionCode;
//			final String versionName = "v" + pi.versionName;
//			new SAsyncTask<Void, Void, HashMap<String, String>>(
//					(FragmentActivity) mContext, v, 0) {
//
//				@Override
//				protected HashMap<String, String> doInBackground(
//						FragmentActivity context, Void... params)
//						throws Exception {
//					// TODO Auto-generated method stub
//					HashMap<String, String> mapRet = ComModel2
//							.checkVersion(mContext);
//					return mapRet;
//				}
//
//				protected boolean isHandleException() {
//					return true;
//				};
//
//				protected void onPostExecute(FragmentActivity context,
//						final java.util.HashMap<String, String> result,
//						Exception e) {
//					if (null == e) {
//						if (StringUtils.isDownload(result.get("version_no"),
//								versionName)) {
//							mContext.getSharedPreferences("tocao_isupdate",
//									Context.MODE_PRIVATE).edit()
//									.putBoolean("tocao_isupdate", true)
//									.commit();
//							AlertDialog.Builder builder = new Builder(mContext);
//							final Dialog dialog = new Dialog(mContext,
//									R.style.invate_dialog_style);
//							View view = View.inflate(mContext,
//									R.layout.is_force_update_dialog, null);
//							TextView tv_content = (TextView) view
//									.findViewById(R.id.tv_content);
//							tv_content.setText(result.get("msg"));
//							Button btn_cancel = (Button) view
//									.findViewById(R.id.btn_cancel);
//
//							btn_cancel
//									.setOnClickListener(new OnClickListener() {
//
//										@Override
//										public void onClick(View arg0) {
//											// TODO Auto-generated method stub
//											dialog.dismiss();
//											if (result.get("is_update").equals(
//													"1")) {// 强制更新
//												AppManager.getAppManager()
//														.finishAllActivity();
//											} else {
//												String imei = CheckStrUtil
//														.getImei(mContext);
//												if (imei != null
//														&& ComModel2.flag == 0) {
//													new Thread() {
//														public void run() {
//
//															try {
//																Thread.sleep(5000);// 5秒
//															} catch (InterruptedException e) {
//																// TODO
//																// Auto-generated
//																// catch block
//																e.printStackTrace();
//															}
//															// mContext.sendBroadcast(new
//															// Intent(TaskReceiver.newMemberTask_1));
//															isNewMemberTask_1 = 1;
//															handler.sendEmptyMessage(0);
//														};
//													}.start();
//
//												} else if (YJApplication.instance
//														.isLoginSucess() == false) {
//													new Thread() {
//														public void run() {
//
//															try {
//																Thread.sleep(5000);// 5秒
//															} catch (InterruptedException e) {
//																// TODO
//																// Auto-generated
//																// catch block
//																e.printStackTrace();
//															}
//															// mContext.sendBroadcast(new
//															// Intent(TaskReceiver.newMemberTask_1));
//															isLoginNewTask = 1;
//															handler.sendEmptyMessage(2);
//														};
//													}.start();
//												} else {
//													UserInfo userInfo = YCache
//															.getCacheUserSafe(mContext);
//													if(null == userInfo){
//														return;
//													}
//													if (null == userInfo
//															.getHobby()
//															|| userInfo
//																	.getHobby()
//																	.equals("0")) {
//
//														new Thread() {
//															public void run() {
//
//																try {
//																	Thread.sleep(5000);// 5秒
//																} catch (InterruptedException e) {
//																	// TODO
//																	// Auto-generated
//																	// catch
//																	// block
//																	e.printStackTrace();
//																}
//																// mContext.sendBroadcast(new
//																// Intent(TaskReceiver.newMemberTask_1));
//																isNewShop = 1;
//																handler.sendEmptyMessage(3);
//															};
//														}.start();
//
//													} else {
//														new Thread() {
//															public void run() {
//
//																try {
//																	Thread.sleep(5000);// 5秒
//																} catch (InterruptedException e) {
//																	// TODO
//																	// Auto-generated
//																	// catch
//																	// block
//																	e.printStackTrace();
//																}
//																// mContext.sendBroadcast(new
//																// Intent(TaskReceiver.newMemberTask_1));
//																isEverydayShareTask = 1;
//																handler.sendEmptyMessage(2);
//															};
//														}.start();
//													}
//												}
//											}
//										}
//									});
//
//							Button btn_ok = (Button) view
//									.findViewById(R.id.btn_ok);
//							btn_ok.setOnClickListener(new OnClickListener() {
//
//								@Override
//								public void onClick(View arg0) {
//									// TODO Auto-generated method stub
//									dialog.dismiss();
//									ApkDownloadManager UpgradeApk = new ApkDownloadManager(
//											(FragmentActivity) mContext);
//									UpgradeApk.downloadUpgradeApk(YUrl.apkUrl
//											+ result.get("path"));
//
//									String imei = CheckStrUtil
//											.getImei(mContext);
//									if (imei != null && ComModel2.flag == 0) {
//										new Thread() {
//											public void run() {
//
//												try {
//													Thread.sleep(5000);// 5秒
//												} catch (InterruptedException e) {
//													// TODO Auto-generated catch
//													// block
//													e.printStackTrace();
//												}
//												// mContext.sendBroadcast(new
//												// Intent(TaskReceiver.newMemberTask_1));
//												isNewMemberTask_1 = 1;
//												handler.sendEmptyMessage(0);
//											};
//										}.start();
//
//									} else if (YJApplication.instance
//											.isLoginSucess() == false) {
//										new Thread() {
//											public void run() {
//
//												try {
//													Thread.sleep(5000);// 5秒
//												} catch (InterruptedException e) {
//													// TODO Auto-generated catch
//													// block
//													e.printStackTrace();
//												}
//												// mContext.sendBroadcast(new
//												// Intent(TaskReceiver.newMemberTask_1));
//												isLoginNewTask = 1;
//												handler.sendEmptyMessage(1);
//											};
//										}.start();
//									} else {
//										UserInfo userInfo = YCache
//												.getCacheUserSafe(mContext);
//										if(null == userInfo){
//											return;
//										}
//										if (null == userInfo.getHobby()
//												|| userInfo.getHobby().equals(
//														"0")) {
//
//											new Thread() {
//												public void run() {
//
//													try {
//														Thread.sleep(5000);// 5秒
//													} catch (InterruptedException e) {
//														// TODO Auto-generated
//														// catch block
//														e.printStackTrace();
//													}
//													// mContext.sendBroadcast(new
//													// Intent(TaskReceiver.newMemberTask_1));
//													isNewShop = 1;
//													handler.sendEmptyMessage(3);
//												};
//											}.start();
//
//										} else {
//											new Thread() {
//												public void run() {
//
//													try {
//														Thread.sleep(5000);// 30秒
//													} catch (InterruptedException e) {
//														// TODO Auto-generated
//														// catch block
//														e.printStackTrace();
//													}
//													// mContext.sendBroadcast(new
//													// Intent(TaskReceiver.newMemberTask_1));
//													isEverydayShareTask = 1;
//													handler.sendEmptyMessage(2);
//												};
//											}.start();
//										}
//									}
//									
//									
//									if (result.get("is_update").equals(
//											"1")) {// 强制更新
//										AppManager.getAppManager()
//												.finishAllActivity();
//									}
//								}
//							});
//
//							// 创建自定义样式dialog
//							dialog.setContentView(
//									view,
//									new LinearLayout.LayoutParams(
//											LinearLayout.LayoutParams.FILL_PARENT,
//											LinearLayout.LayoutParams.FILL_PARENT));
//							dialog.setCancelable(false);
//							if (result.get("is_update").equals("1")) {// 强制更新
//								btn_cancel.setVisibility(View.GONE);
//								dialog.setOnCancelListener(new OnCancelListener() {
//									@Override
//									public void onCancel(DialogInterface arg0) {
//
//										// TODO Auto-generated method stub
//										// mContext.finish();
//										AppManager.getAppManager()
//												.finishAllActivity();
//									}
//								});
//							}
//							if (!YJApplication.instance.getIsShowUpdateDialog()) {
//								dialog.show();
//								YJApplication.instance.isShowUpdateDialog(true);
//							}
//						} else {
//							String imei = CheckStrUtil.getImei(mContext);
//							if (imei != null && ComModel2.flag == 0) {
//								new Thread() {
//									public void run() {
//
//										try {
//											Thread.sleep(5000);// 5秒
//										} catch (InterruptedException e) {
//											// TODO Auto-generated catch block
//											e.printStackTrace();
//										}
//										// mContext.sendBroadcast(new
//										// Intent(TaskReceiver.newMemberTask_1));
//										isNewMemberTask_1 = 1;
//										handler.sendEmptyMessage(0);
//									};
//								}.start();
//
//							} else if (YJApplication.instance.isLoginSucess() == false) {
//								new Thread() {
//									public void run() {
//
//										try {
//											Thread.sleep(5000);// 5秒
//										} catch (InterruptedException e) {
//											// TODO Auto-generated catch block
//											e.printStackTrace();
//										}
//										// mContext.sendBroadcast(new
//										// Intent(TaskReceiver.newMemberTask_1));
//										isLoginNewTask = 1;
//										handler.sendEmptyMessage(1);
//									};
//								}.start();
//							} else {
//								UserInfo userInfo = YCache
//										.getCacheUserSafe(mContext);
//								if(null == userInfo){
//									return;
//								}
//								if (null == userInfo.getHobby()
//										|| userInfo.getHobby().equals("0")) {
//
//									new Thread() {
//										public void run() {
//
//											try {
//												Thread.sleep(5000);// 5秒
//											} catch (InterruptedException e) {
//												// TODO Auto-generated catch
//												// block
//												e.printStackTrace();
//											}
//											// mContext.sendBroadcast(new
//											// Intent(TaskReceiver.newMemberTask_1));
//											isNewShop = 1;
//											handler.sendEmptyMessage(3);
//										};
//									}.start();
//
//								} else {
//									new Thread() {
//										public void run() {
//
//											try {
//												Thread.sleep(5000);// 30秒
//											} catch (InterruptedException e) {
//												// TODO Auto-generated catch
//												// block
//												e.printStackTrace();
//											}
//											// mContext.sendBroadcast(new
//											// Intent(TaskReceiver.newMemberTask_1));
//											isEverydayShareTask = 1;
//											handler.sendEmptyMessage(2);
//										};
//									}.start();
//								}
//							}
//						}
//					}
//				};
//
//			}.execute((Void[]) null);
//		} catch (NameNotFoundException e) {
//			// e.printStackTrace();
//			String imei = CheckStrUtil.getImei(mContext);
//			if (imei != null && ComModel2.flag == 0) {
//				new Thread() {
//					public void run() {
//
//						try {
//							Thread.sleep(5000);// 5秒
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						// mContext.sendBroadcast(new
//						// Intent(TaskReceiver.newMemberTask_1));
//						isNewMemberTask_1 = 1;
//						handler.sendEmptyMessage(0);
//					};
//				}.start();
//
//			} else {
//				new Thread() {
//					public void run() {
//
//						try {
//							Thread.sleep(5000);// 5秒
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						// mContext.sendBroadcast(new
//						// Intent(TaskReceiver.newMemberTask_1));
//						isEverydayShareTask = 1;
//						handler.sendEmptyMessage(2);
//					};
//				}.start();
//			}
//		}
//	}
//
//	private File file;
//
//	private void dPic(String picPath) {
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
//			Log.e("TAG", "下载失败");
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void onHiddenChanged(boolean hidden) {
//		// TODO Auto-generated method stub
//		super.onHiddenChanged(hidden);
//		if (!hidden) {
//
//			if (isNewMemberTask_1 == 1) {
//				handler.sendEmptyMessage(0);
//			}
//			if (isLoginNewTask == 1) {
//				handler.sendEmptyMessage(2);
//			}
//			if (isNewShop == 1) {
//				handler.sendEmptyMessage(3);
//			}
//			if (isEveryDayTask3_4 == 1) {
//				handler.sendEmptyMessage(1);
//			}
//			if (isEverydayShareTask == 1) {
//				handler.sendEmptyMessage(4);
//			}
//			if (tucaoTask == 1) {
//				handler.sendEmptyMessage(6);
//				tucaoTask = 2;
//			}
//		}
//	}
//
//	private int isPause = 0;
//	private Handler handler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//
//			if (isHidden() || isPause == 1) {
//				return;
//			}
//			switch (msg.what) {
//			case 0: {
//				if (isShow) {
//					return;
//				}
//				if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == mContext
//						.getSharedPreferences("shareApp", Context.MODE_PRIVATE)
//						.getInt("day", 0)) {
//					return;
//				}
//				isNewMemberTask_1 = 2;
//				NewPDialog dialog = new NewPDialog(mContext,
//						R.layout.task_dialog1);
//				dialog.setF(new NewPDialog.FinishLintener() {
//
//					@Override
//					public void onFinishClickLintener() {
//						isShow = false;
//						mContext.getSharedPreferences("shareApp",
//								Context.MODE_PRIVATE)
//								.edit()
//								.putInt("day",
//										Calendar.getInstance().get(
//												Calendar.DAY_OF_MONTH))
//								.commit();
//					}
//				});
//				dialog.setL(new NewPDialog.TaskLintener() {
//
//					@Override
//					public void onOKClickLintener() {
//						isShow = false;
//						mContext.getSharedPreferences("shareApp",
//								Context.MODE_PRIVATE)
//								.edit()
//								.putInt("day",
//										Calendar.getInstance().get(
//												Calendar.DAY_OF_MONTH))
//								.commit();
//
//						if (!YJApplication.instance.isLoginSucess()) {
//							if (loginDialog == null) {
//								loginDialog = new ToLoginDialog(mContext);
//								loginDialog.setRequestCode(56);
//							}
//							loginDialog.show();
//							isShow = true;
//							// NewPDialog dialog = new NewPDialog(mContext,
//							// R.layout.task_dialog3);
//							// dialog.setL(new NewPDialog.TaskLintener() {
//							//
//							// @Override
//							// public void onOKClickLintener() {
//							// // 注册
//							//
//							// Intent i = new Intent(mContext,
//							// LoginActivity.class);
//							// i.putExtra("login_register", "login");
//							// ((FragmentActivity) mContext)
//							// .startActivityForResult(i, 56);
//							// }
//							//
//							// @Override
//							// public void onShouZhiClickLintener() {
//							// // TODO Auto-generated method stub
//							//
//							// }
//							// });
//							// dialog.show();
//						} else {
//							// 点击分享
//							LoadingDialog.show((FragmentActivity) mContext);
//							isShow = true;
//							new AsyncTask<Void, Void, Void>() {
//								@Override
//								protected Void doInBackground(Void... arg0) {
//
//									dPic("http://yssj668.b0.upaiyun.com/share/android/900_900_1_android.jpg");
//									return null;
//								}
//
//								@Override
//								protected void onPostExecute(Void result) {
//									super.onPostExecute(result);
//									LoadingDialog.hide(getActivity());
//									isShow = false;
//									ShareUtil.shareH5(mContext, file);
//								};
//
//							}.execute();
//						}
//					}
//
//					@Override
//					public void onShouZhiClickLintener() {
//						// TODO Auto-generated method stub
//
//					}
//				});
////				dialog.show();
////				isShow = true;
//			}
//				break;
//			case 1: {
//
//				{
//					if (isShow) {
//						return;
//					}
//					if (!YJApplication.instance.isLoginSucess()) {
//						return;
//					}
//					if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == mContext
//							.getSharedPreferences("EverydayTaskMondayFriday",
//									Context.MODE_PRIVATE).getInt("day", 0)) {
//						return;
//					}
//
//					UserInfo userInfo;
//					userInfo = YCache.getCacheUserSafe(mContext);
//					if(null==userInfo){
//						return;
//					}
//					if (null == userInfo.getHobby()
//							|| userInfo.getHobby().equals("0")) {
//						return;
//					}
//
//					isEveryDayTask3_4 = 2;
//					int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
//					if (day == 3) {
//					} else if (day == 2) {
//					}
//				}
//			}
//			case 2: {
//
//				/*
//				 * isLoginNewTask=2;
//				 * 
//				 * if (YJApplication.instance.isLoginSucess()) { return; }
//				 * NewPDialog dialog = new NewPDialog(mContext,
//				 * R.layout.task_dialog3); dialog.setF(new
//				 * NewPDialog.FinishLintener() {
//				 * 
//				 * @Override public void onFinishClickLintener() { // TODO
//				 * Auto-generated method stub } }); dialog.setL(new
//				 * NewPDialog.TaskLintener() {
//				 * 
//				 * @Override public void onOKClickLintener() { // 注册 Intent i =
//				 * new Intent(mContext, LoginActivity.class);
//				 * i.putExtra("login_register", "login");
//				 * mContext.startActivity(i); }
//				 * 
//				 * @Override public void onShouZhiClickLintener() { // TODO
//				 * Auto-generated method stub
//				 * 
//				 * } });
//				 */
//				// dialog.show();
//				/**
//				 * NewPDialog dialog = new NewPDialog(mContext,
//				 * R.layout.task_dialog1); dialog.setF(new
//				 * NewPDialog.FinishLintener() {
//				 * 
//				 * @Override public void onFinishClickLintener() { // TODO
//				 *           Auto-generated method stub new Thread(){ public
//				 *           void run() {
//				 * 
//				 *           try { Thread.sleep(25000);//5秒 } catch
//				 *           (InterruptedException e) { // TODO Auto-generated
//				 *           catch block e.printStackTrace(); }
//				 *           //mContext.sendBroadcast(new
//				 *           Intent(TaskReceiver.newMemberTask_1));
//				 *           isEveryDayTask3_4=1; handler.sendEmptyMessage(1);
//				 *           }; }.start(); } }); dialog.setL(new
//				 *           NewPDialog.TaskLintener() {
//				 * @Override public void onOKClickLintener() {
//				 * 
//				 * 
//				 *           new Thread(){ public void run() {
//				 * 
//				 *           try { Thread.sleep(25000);//5秒 } catch
//				 *           (InterruptedException e) { // TODO Auto-generated
//				 *           catch block e.printStackTrace(); }
//				 *           //mContext.sendBroadcast(new
//				 *           Intent(TaskReceiver.newMemberTask_1));
//				 *           isEveryDayTask3_4=1; handler.sendEmptyMessage(1);
//				 *           }; }.start(); if
//				 *           (!YJApplication.instance.isLoginSucess()) {
//				 *           NewPDialog dialog = new NewPDialog(mContext,
//				 *           R.layout.task_dialog3); dialog.setL(new
//				 *           NewPDialog.TaskLintener() {
//				 * @Override public void onOKClickLintener() { // 注册 Intent i =
//				 *           new Intent(mContext, LoginActivity.class);
//				 *           i.putExtra("login_register", "login");
//				 *           ((FragmentActivity
//				 *           )mContext).startActivityForResult(i,56); }
//				 * @Override public void onShouZhiClickLintener() { // TODO
//				 *           Auto-generated method stub
//				 * 
//				 *           } }); dialog.show(); }else{ // 点击分享
//				 *           LoadingDialog.show((FragmentActivity) mContext);
//				 *           new AsyncTask<Void, Void, Void>() {
//				 * @Override protected Void doInBackground(Void... arg0) {
//				 * 
//				 *           dPic(
//				 *           "http://yssj668.b0.upaiyun.com/share/android/900_900_1_android.jpg"
//				 *           ); return null; }
//				 * @Override protected void onPostExecute(Void result) {
//				 *           super.onPostExecute(result); LoadingDialog.hide();
//				 *           ShareUtil.shareH5(mContext, file); };
//				 * 
//				 *           }.execute(); } }
//				 * @Override public void onShouZhiClickLintener() { // TODO
//				 *           Auto-generated method stub
//				 * 
//				 *           } }); dialog.show();
//				 */
//			}
//				break;
//			case 3: {
//				if (isShow) {
//					return;
//				}
//				isNewShop = 2;
//				if (!YJApplication.instance.isLoginSucess()) {
//					return;
//				}
//				if (mContext.getSharedPreferences("dian", Context.MODE_PRIVATE)
//						.getInt("dian", 0) == Calendar.getInstance().get(
//						Calendar.DAY_OF_MONTH)) {
//					return;
//				}
//				UserInfo  userInfo= YCache.getCacheUserSafe(mContext);
//				if(null == userInfo){
//					return;
//				}
//				if (null == userInfo.getHobby()
//						|| userInfo.getHobby().equals("0")) {
//					mContext.getSharedPreferences("dian", Context.MODE_PRIVATE)
//							.edit()
//							.putInt("dian",
//									Calendar.getInstance().get(
//											Calendar.DAY_OF_MONTH)).commit();
//					int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
//					NewPDialog dialog;
//					if (day == 2) {
//						dialog = new NewPDialog(getActivity(),
//								R.layout.task_dialog4_monday);
//					} else if (day == 3) {
//						dialog = new NewPDialog(getActivity(),
//								R.layout.task_dialog4_tuesday);
//					} else if (day == 4) {
//						dialog = new NewPDialog(getActivity(),
//								R.layout.task_dialog4_wednesday);
//					} else if (day == 5) {
//						dialog = new NewPDialog(getActivity(),
//								R.layout.task_dialog4_thursday);
//					} else if (day == 6) {
//						dialog = new NewPDialog(getActivity(),
//								R.layout.task_dialog4_friday);
//					} else {
//						dialog = new NewPDialog(getActivity(),
//								R.layout.task_dialog4);
//					}
//
//					dialog.setF(new NewPDialog.FinishLintener() {
//
//						@Override
//						public void onFinishClickLintener() {
//							isShow = false;
//							// TODO Auto-generated method stub
//
//						}
//					});
//
//					dialog.setL(new NewPDialog.TaskLintener() {
//
//						@Override
//						public void onOKClickLintener() {
//							isShow = false;
//							// 开店
//							AppManager.getAppManager()
//									.finishAllActivityOfEveryDayTask();
//							if (MainMenuActivity.instances != null)
//								((MainFragment) MainMenuActivity.instances
//										.getSupportFragmentManager()
//										.findFragmentByTag("tag")).setIndex(0);
//						}
//
//						@Override
//						public void onShouZhiClickLintener() {
//
//						}
//
//					});
//					try {
//						dialog.show();
//						isShow = true;
//					} catch (Exception e) {
//
//					}
//				}
//			}
//				break;
//			case 6: {
//
//				if (mContext.getSharedPreferences("tocao_isupdate",
//						Context.MODE_PRIVATE).getBoolean("tocao_isupdate",
//						false)) {
//					return;
//				}
//				if (mContext.getSharedPreferences("tocao_isshow",
//						Context.MODE_PRIVATE).getBoolean("tocao_isshow", false)) {
//					return;
//				}
//				if (isPause == 1) {
//					return;
//				}
//				if (isShow) {
//					return;
//				}
//				final NewPDialog mDialog = new NewPDialog(getActivity(),
//						R.layout.task_dialog9);
//
//				mDialog.setL(new NewPDialog.TaskLintener() {
//
//					@Override
//					public void onOKClickLintener() {
//						// 跳到意见反馈
//						isShow = false;
//
//						Intent intent = new Intent();
//
//						intent.setClass(mContext, FeedBackActivity.class);
//
//						mContext.startActivity(intent);
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
//						isShow = false;
//						// TODO Auto-generated method stub
//					}
//				});
//				mDialog.show();
//				mContext.getSharedPreferences("tocao_isshow",
//						Context.MODE_PRIVATE).edit()
//						.putBoolean("tocao_isshow", true).commit();
//				isShow = true;
//
//			}
//				break;
//			case 4: {
//				if (isShow) {
//					return;
//				}
//				if (isEverydayShareTask == 1) {
//					if (!YJApplication.instance.isLoginSucess()) {
//						return;
//					}
//					if (isPause == 1) {
//						return;
//					}
//					List<String> taskMap = YJApplication.instance.getTaskMap();
//					if (taskMap == null) {
//						taskMap = new ArrayList<String>();
//					}
//					if (taskMap.contains("7") || taskMap.contains("8")) {
//						return;
//					}
//					if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == mContext
//							.getSharedPreferences("EveryDayShareAm",
//									Context.MODE_PRIVATE).getInt("day", 0)) {
//						return;
//					}
//					if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == mContext
//							.getSharedPreferences("EveryDaySharePm",
//									Context.MODE_PRIVATE).getInt("day", 0)) {
//						return;
//					}
//					isEverydayShareTask = 2;
//					// 获取当前时间
//					// String currentTime = DateFormat.format("HH", new Date())
//					// .toString();
//					// int hour = Integer.parseInt(currentTime);
//					int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//					// 每日上午分享一次
//					if ((hour > 7 || hour == 7) && hour < 14) {
//
//						NewPDialog dialog = new NewPDialog(mContext,
//								R.layout.every_day_task_dialog2);
//
//						dialog.setF(new NewPDialog.FinishLintener() {
//
//							@Override
//							public void onFinishClickLintener() {
//								isShow = false;
//								// TODO Auto-generated method stub
//								mContext.getSharedPreferences(
//										"EveryDaySharePause",
//										Context.MODE_PRIVATE).edit()
//										.putInt("day", 0).commit();
//								mContext.getSharedPreferences(
//										"EveryDayShareAm", Context.MODE_PRIVATE)
//										.edit()
//										.putInt("day",
//												Calendar.getInstance().get(
//														Calendar.DAY_OF_MONTH))
//										.commit();
//							}
//						});
//
//						dialog.setL(new NewPDialog.TaskLintener() {
//
//							@Override
//							public void onOKClickLintener() {
//								isShow = false;
//								mContext.getSharedPreferences(
//										"EveryDaySharePause",
//										Context.MODE_PRIVATE).edit()
//										.putInt("day", 0).commit();
//								mContext.getSharedPreferences(
//										"EveryDayShareAm", Context.MODE_PRIVATE)
//										.edit()
//										.putInt("day",
//												Calendar.getInstance().get(
//														Calendar.DAY_OF_MONTH))
//										.commit();
//								// 分享商品
//								shareRandom();
//
//							}
//
//							@Override
//							public void onShouZhiClickLintener() {
//								// TODO Auto-generated method stub
//
//							}
//						});
//						try {
//							dialog.show();
//							isShow = true;
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//						}
//					}
//
//					// 每日下午分享一次
//					if ((hour > 14 || hour == 14) && hour < 20) {
//
//						NewPDialog dialog = new NewPDialog(mContext,
//								R.layout.every_day_task_dialog3);
//						dialog.setF(new NewPDialog.FinishLintener() {
//
//							@Override
//							public void onFinishClickLintener() {
//								isShow = false;
//								// TODO Auto-generated method stub
//								mContext.getSharedPreferences(
//										"EveryDaySharePm", Context.MODE_PRIVATE)
//										.edit()
//										.putInt("day",
//												Calendar.getInstance().get(
//														Calendar.DAY_OF_MONTH))
//										.commit();
//							}
//						});
//						dialog.setL(new NewPDialog.TaskLintener() {
//
//							@Override
//							public void onOKClickLintener() {
//								isShow = false;
//								mContext.getSharedPreferences(
//										"EveryDaySharePm", Context.MODE_PRIVATE)
//										.edit()
//										.putInt("day",
//												Calendar.getInstance().get(
//														Calendar.DAY_OF_MONTH))
//										.commit();
//								// 分享商品
//								shareRandom();
//							}
//
//							@Override
//							public void onShouZhiClickLintener() {
//								// TODO Auto-generated method stub
//
//							}
//						});
//						try {
//							dialog.show();
//							isShow = true;
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//						}
//					}
//				}
//			}
//				break;
//			default:
//				break;
//			}
//
//		};
//	};
//
//	/** 主店轮播图 以及 */
//	private void getTuijianData() {
//		new SAsyncTask<Void, Void, HashMap<String, Object>>(
//				(FragmentActivity) mContext, R.string.wait) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.getZeroTuijianData(context, "3");
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, Object> result, Exception e) {
//				if (null == e && result != null) {
//					pagerList = (List<ShopOption>) result.get("topShops");
//					// gallList = (List<ShopOption>) result.get("centShops");
//					imageViewPager.setRefresh(true);
//					imageViewPager.setData(pagerList, mContext);
//
//					// mGallery.setData(gallList);
//					//
//					// mGallery.getScroll().smoothScrollTo(0, 0);//复位
//
//					mView.refreshDone();
//				}
//				super.onPostExecute(context, result, e);
//			}
//
//		}.execute();
//	}
//
//	/** 购物车统计 */
//	// private void getShopCartCount() {
//	// new SAsyncTask<Void, Void, String>((FragmentActivity)mContext, 0) {
//	//
//	// @Override
//	// protected String doInBackground(
//	// FragmentActivity context, Void... params) throws Exception {
//	// // TODO Auto-generated method stub
//	// if(YJApplication.instance.isLoginSucess()){
//	// return ComModel2.getShopCartCount(context);
//	// }else{
//	// return null;
//	// }
//	// }
//	//
//	// @Override
//	// protected boolean isHandleException() {
//	// return true;
//	// }
//	//
//	// @Override
//	// protected void onPostExecute(FragmentActivity context,
//	// String result, Exception e) {
//	// if(null == e&&result!=null){
//	// if(result.equals("0")){
//	// tv_shop_cart_count.setVisibility(View.GONE);
//	// }else{
//	// tv_shop_cart_count.setVisibility(View.VISIBLE);
//	// tv_shop_cart_count.setText(result);
//	// }
//	// // pagerList = (List<ShopOption>) result.get("topShops");
//	// // gallList = (List<ShopOption>) result.get("centShops");
//	// // imageViewPager.setRefresh(true);
//	// // imageViewPager.setData(pagerList, mContext);
//	//
//	// // mGallery.setData(gallList);
//	//
//	// // mGallery.getScroll().smoothScrollTo(0, 0);//复位
//	//
//	// // mView.refreshDone();
//	// }
//	// super.onPostExecute(context, result, e);
//	// }
//	//
//	// }.execute();
//	// }
//
//	public static ZeroShopFragment newInstance(String title, Context context) {
//		ZeroShopFragment fragment = new ZeroShopFragment();
//		Bundle args = new Bundle();
//		args.putString("tag", title);
//		mContext = context;
//		fragment.setArguments(args);
//		return fragment;
//	}
//
//	/**
//	 * 此处应当继承FragmentStatePagerAdapter
//	 * 在处理数据量较大的页面应当使用FragmentStatePagerAdapter，而不是FragmentPagerAdapter
//	 */
//	public class PagerAdapter extends FragmentStatePagerAdapter {
//		public PagerAdapter(FragmentManager fm) {
//			super(fm);
//		}
//
//		@Override
//		public int getCount() {
//			return 4;
//		}
//
//		@Override
//		public Fragment getItem(int position) {
//			ZeroShopItemFragment fragment = fragments[position];
//			if (fragment == null) {
//				fragment = ZeroShopItemFragment
//						.newInstances(position, mContext);
//				fragments[position] = fragment;
//				fragment.setOnZeroRefreshListener(ZeroShopFragment.this);
//			}
//			return fragment;
//		}
//
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		switch (arg0.getId()) {
//		case R.id.img_shop_cart:
//			if (YJApplication.instance.isLoginSucess() == false) {
//
//				if (loginDialog == null) {
//					loginDialog = new ToLoginDialog(getActivity());
//				}
//				// loginDialog.setRequestCode(235);
//				loginDialog.show();
//				return;
//			}
//			Intent intent = new Intent(mContext, ShopCartActivity.class);
//			startActivity(intent);
//			// ((FragmentActivity)mContext).overridePendingTransition(R.anim.activity_filter_open,R.anim.activity_filter_close);
//			break;
//		case R.id.tv_title:
//			recoverBy2Click();
//		default:
//			break;
//		}
//	}
//
//	@Override
//	public void onRefreshlintener() {
//		// getShopCartCount();
//		getTuijianData();
//		ZeroShopItemFragment fragment = (ZeroShopItemFragment) mAdapter
//				.getItem(currentPosition);
//		fragment.refresh();
//	}
//
//	private static Boolean isExit = false;
//
//	private void recoverBy2Click() {
//		Timer tExit = null;
//		if (isExit == false) {
//			isExit = true;
//
//			tExit = new Timer();
//			tExit.schedule(new TimerTask() {
//				@Override
//				public void run() {
//					isExit = false;
//				}
//			}, 1000); // 如果1秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
//
//		} else {
//
//			mView.scrollTo(0, 0);
//			((ZeroShopItemFragment) mAdapter.getItem(currentPosition))
//					.getmList().setSelection(0);
//
//		}
//	}
//
//	private void initTextView() {
//		ll_title_one.setBackgroundColor(Color.rgb(217, 87, 87));
//		ll_title_one.setOnClickListener(new MyOnClickListener(0));
//		ll_title_two.setOnClickListener(new MyOnClickListener(1));
//		ll_title_three.setOnClickListener(new MyOnClickListener(2));
//		ll_title_four.setOnClickListener(new MyOnClickListener(3));
//	}
//
//	/* 标题点击监听 */
//	private class MyOnClickListener implements OnClickListener {
//		private int index = 0;
//
//		public MyOnClickListener(int i) {
//			index = i;
//		}
//
//		public void onClick(View v) {
//			mViewPager.setCurrentItem(index);
//		}
//	}
//
//	/** 设置标题文本的颜色 **/
//	private void setTextTitleSelectedColor(int arg0) {
//		// int count = mViewPager.getChildCount();
//		switch (arg0) {
//		case 0:
//			MobclickAgent.onEvent(mContext,"0yuanareclick");
//			break;
//		case 1:
//			MobclickAgent.onEvent(mContext,"9yuanareclick");
//			break;
//		case 2:
//			MobclickAgent.onEvent(mContext,"19yuanareclick");
//			break;
//		case 3:
//			MobclickAgent.onEvent(mContext,"29yuanareclick");
//			break;
//
//		default:
//			break;
//		}
//		for (int i = 0; i < 4; i++) {
//			LinearLayout mLinear = (LinearLayout) title.getChildAt(i);
//			if (arg0 == i) {
//				mLinear.setBackgroundColor(Color.rgb(217, 87, 87));
//			} else {
//				mLinear.setBackgroundColor(Color.rgb(255, 102, 102));
//			}
//		}
//	}
//
//	@Override
//	public void onZeroShopRefresh() {
//		// TODO Auto-generated method stub
//		mView.refreshDone();
//	}
//
//	@Override
//	public void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		// getShopCartCount();
//		MobclickAgent.onPageStart("ZeroShopFragment"); 
//		isPause = 0;
//	}
//
//	@Override
//	public void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		MobclickAgent.onPageEnd("ZeroShopFragment"); 
//		isPause = 1;
//	}
//
//	/** 得到随机分享的链接 */
//
//	private void shareRandom() {
//
//		new SAsyncTask<String, Void, HashMap<String, String>>(
//				(FragmentActivity) mContext, R.string.wait) {
//
//			@Override
//			protected HashMap<String, String> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.getShareShopLink(context,"");
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, String> result, Exception e) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result, e);
//				if (null == e) {
//					if (result.get("status").equals("1")) {
//						Log.e("pic", result.get("shop_pic"));
//						String[] picList = result.get("shop_pic").split(",");
//						String link = result.get("link");
//						downloadRandom(null, picList, result.get("shop_code"),
//								result.get("shop_name"), result, link);
//					} else if (result.get("status").equals("1050")) {// 表明
//						Intent intent = new Intent(context,
//								NoShareActivity.class);
//						intent.putExtra("isNomal", true);
//						context.startActivity(intent); // 分享已经超过了
//					}
//				}
//			}
//
//		}.execute();
//	}
//
//	private void downloadRandom(View v, final String[] picList,
//			final String shop_code, final String shop_name,
//			final HashMap<String, String> mapInfos, final String link) {
//		new SAsyncTask<Void, Void, Void>((FragmentActivity) mContext, v,
//				R.string.wait) {
//
//			@Override
//			protected Void doInBackground(Void... params) {
//				// TODO Auto-generated method stub
//				File fileDirec = new File(YConstance.savePicPath);
//				if (!fileDirec.exists()) {
//					fileDirec.mkdir();
//				}
//				File[] listFiles = new File(YConstance.savePicPath).listFiles();
//				if (listFiles.length != 0) {
//					Log.e("TAG", "存在文件夹 删除中。。。。");
//					for (File file : listFiles) {
//						file.delete();
//					}
//				}
//				// Log.i("TAG", "piclist=" + picList.length);
//				List<String> pics = new ArrayList<String>();
//				for (int j = 0; j < picList.length; j++) {
//					if (!picList[j].contains("reveal_")
//							&& !picList[j].contains("detail_")
//							&& !picList[j].contains("real_")) {
//						picList[j] = shop_code.substring(1, 4) + "/"
//								+ shop_code + "/" + picList[j];
//						pics.add(picList[j]);
//					}
//				}
//				int j = pics.size() + 1;
//				if (pics.size() > 8) {
//					j = 9;
//				}
//				for (int i = 0; i < j; i++) {
//					if (i == j - 1) {
//						/*
//						 * ComModel2.saveQRCode(PaymentSuccessActivity.this,
//						 * shop_code);
//						 */
//						Bitmap bm = QRCreateUtil.createImage(
//								mapInfos.get("QrLink"), 500, 700,
//								mapInfos.get("shop_se_price"), mContext);// 得到二维码图片
//						QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
//								MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
//						// downloadPic(mapInfos.get("qr_pic"), 9);
//						break;
//					}
//					downloadPic(pics.get(i) + "!450", i);
//				}
//				return super.doInBackground(params);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, Void result) {
//				// TODO Auto-generated method stub
//				// showShareDialog();
//				// Log.e("TAG", "宝贝内容=" + shop.getShop_name() + ",宝贝链接=" +
//				// result);
//				if (null != context
//						&& null != context.getWindow().getDecorView()) {
//					ShareUtil.configPlatforms(context);
//					UMImage umImage = new UMImage(context,
//							R.drawable.ic_launcher);
//					ShareUtil
//							.setShareContent(context, umImage, shop_name, link);
//					// ShareUtil.share(ShopDetailsActivity.this);
//					MyPopupwindow myPopupwindow = new MyPopupwindow(context, 0,
//							1);
//					myPopupwindow.showAtLocation(context.getWindow()
//							.getDecorView(), Gravity.BOTTOM, 0, 0);
//				}
//				super.onPostExecute(context, result);
//
//			}
//
//		}.execute();
//	}
//
//	private void downloadPic(String picPath, int i) {
//		try {
//			URL url = new URL(YUrl.imgurl + picPath);
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
//			// 输出的文件流 /sdcard/yssj/
//			File file = new File(YConstance.savePicPath, MD5Tools.md5(String
//					.valueOf(i)) + ".jpg");
//			if (file.exists()) {
//				file.delete();
//			}
//			Log.e("TAG", "多分享选择下载的图片。。。。");
//			OutputStream os = new FileOutputStream(file);
//			// 开始读取
//			while ((len = is.read(bs)) != -1) {
//				os.write(bs, 0, len);
//			}
//			Log.i("TAG", "下载完毕。。。file=" + file.toString());
//			// 完毕，关闭所有链接
//			os.close();
//			is.close();
//		} catch (Exception e) {
//			Log.e("TAG", "下载失败");
//			e.printStackTrace();
//		}
//	}
//}
