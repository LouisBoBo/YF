package com.yssj.ui.activity.logins;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.UMSsoHandler;
import com.yssj.Constants;
//import com.yssj.ShopBeautyActivity;
import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.UserIfoThird;
import com.yssj.entity.UserInfo;
import com.yssj.huanxin.PublicUtil;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;
import com.yssj.network.YException;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.setting.SettingCommonFragmentActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DeviceUtils;
import com.yssj.utils.GetJinBiJinQuanUtils;
import com.yssj.utils.GetShopCart;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ModUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

/***
 * 登陆注册
 * 
 * @author Administrator
 * 
 */
public class OldUserLoginActivity extends BasicActivity
		implements OnClickListener, RegisterFragment.OnThirdClickListener, OldLoginFragment.OnThirdClickListener {

	// 渠道切换开关
//	public static Boolean noUserRegist = false; // 非渠道
	 public static Boolean isQudao = true; //渠道
	public static boolean isOldLogin = false;//渠道包的老用户登录
	private AppManager appManager;
	public static OldUserLoginActivity instances;
	public static String kefu;
	private TextView tvlogin, tvregster;
	private OldLoginFragment lgFragment;
	private RegisterFragment rsFragment;
	private FragmentTransaction ft;
	private FragmentManager fm;
	private String login_register;
	private TextView tv_chreg;
	private FrameLayout container_login;
	public static Boolean phoneboolll;

	private TextView tvTitle_base,tv_zhanghaodenglu;
	private ImageButton imgbtn_left_icon;
	private LinearLayout imgBack;
	public static Context context;
	// private TextView tv_third;

	// private ImageView img_wx, img_qq, img_wb;
	// 整个平台的Controller, 负责管理整个SDK的配置、操作等处理
	private UMSocialService mController;

	private UserIfoThird user;
	private YJApplication appctx;
	private String isPushOut;

	private String deviceToken = "";
	public static String mShopCart = "";
	public static Boolean isSign = false;
	private int chan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		fm = getSupportFragmentManager();

		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);
		setContentView(R.layout.activity_login);
		login_register = getIntent().getStringExtra("login_register");
		isPushOut = getIntent().getStringExtra("10030");
		mShopCart = getIntent().getStringExtra("shopcart");
		isSign = getIntent().getBooleanExtra("isSign", false);
		LogYiFu.e("zzqyi", "333" + mShopCart);
		initFragment();
		initViews();
		instances = this;

		chan = Integer.parseInt(DeviceUtils.getChannelCode(OldUserLoginActivity.this));

		appctx = (YJApplication) getApplicationContext();
		LogYiFu.e("deviceToken", deviceToken);
		addQZoneQQPlatform();
		// addWxPlatform();
		addWX();
		// 设置新浪SSO handler
//		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		appManager = AppManager.getAppManager();
		appManager.addActivity(this);
		// login_register

		if (savedInstanceState != null) {
			// ToastUtil.showShortText(getApplicationContext(),
			// "亲，请先完成微信授权~");//10 11号
			login_register = savedInstanceState.getString("login_register");
			lgFragment = (OldLoginFragment) fm.findFragmentByTag("login");
			rsFragment = (RegisterFragment) fm.findFragmentByTag("register");
			initFragment();
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putString("login_register", login_register);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// PublicUtil2.openId(SHARE_MEDIA.WEIXIN, 0, this);//(判断微信性别)
		isPasuse = 0;
		// MobclickAgent.onResume(this);

	}

	private int isPasuse = 0;

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isPasuse = 1;
//		YJApplication.getLoader().stop();
		// MobclickAgent.onPause(this);
	}



	private void getPayRedBackage() {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (!SharedPreferencesUtil.getBooleanData(OldUserLoginActivity.this, Pref.ISMADMONDAY,false)) {
						//当天是否有未支付订单
						boolean hasNoPayOrder = ModQingfeng.getNotFUoder(OldUserLoginActivity.this);
						if (hasNoPayOrder) {//有未支付订单
							HashMap<String, String> map = ComModel2.getYiDouHalve(OldUserLoginActivity.this);
							if (null == map || map.size() == 0 || "0".equals(map.get("end_date"))) {//说明没有参与过红包的任务-----用户走完流程，支持成功依旧会返回map
								YJApplication.startFukuanYndao();
							}
						} else {//没有未支付订单---无需任何操作----不考虑任何情况，只要没有未付款订单就不用引导红包

							SharedPreferencesUtil.saveStringData(OldUserLoginActivity.this, YConstance.Pref.YIDOU_HALVE_END_TIMES,"0");
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}



	/*
	 * @Override protected void onResume() { super.onResume();
	 * JPushInterface.onResume(this); }
	 * 
	 * @Override protected void onPause() { super.onPause();
	 * JPushInterface.onPause(this); }
	 */

	private void initFragment() {
		ft = fm.beginTransaction();

		// lgFragment = OldLoginFragment.newInstance("login");
		// rsFragment = RegisterFragment.newInstance("register");
		//
		// lgFragment.setThirdClickListener(this);
		// rsFragment.setThirdClickListener(this);
		// ft.add(R.id.container_login, lgFragment, "login");
		// ft.add(R.id.container_login, rsFragment, "register");

		if (login_register.equals("login")) {
			// ft.replace(R.id.container_login, lgFragment);
			if (lgFragment == null) {
				lgFragment = OldLoginFragment.newInstance("login");
				ft.add(R.id.container_login, lgFragment, "login");
			}
			lgFragment.setThirdClickListener(this);
			ft.show(lgFragment);
			if (rsFragment != null) {
				ft.hide(rsFragment);
			}

		} else {
			// ft.replace(R.id.container_login, rsFragment);
			if (rsFragment == null) {
				rsFragment = RegisterFragment.newInstance("register");
				ft.add(R.id.container_login, rsFragment, "register");
			}
			rsFragment.setThirdClickListener(this);
			// ft.hide(lgFragment);
			ft.show(rsFragment);
			if (lgFragment != null) {
				ft.hide(lgFragment);
			}
		}
		ft.commit();
	}

	private boolean isQQInstall;

	private void addQZoneQQPlatform() {
//		String appId = "1104724623";
//		String appKey = "VpAQVytFGidSRx6l";
//		// 添加QQ支持, 并且设置QQ分享内容的target url
//		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, appId, appKey);
//		qqSsoHandler.setTargetUrl("http://www.umeng.com");
//
//		qqSsoHandler.addToSocialSDK();
//		// 添加QZone平台
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, appId, appKey);
//		qZoneSsoHandler.addToSocialSDK();
		// isQQInstall = qqSsoHandler.isClientInstalled();
	}

	private boolean isWxInstall;

	// private void addWxPlatform() {
	// // 添加微信平台
	// UMWXHandler wxHandler = new UMWXHandler(LoginActivity.this,
	// WxPayUtil.APP_ID, WxPayUtil.APP_SECRET);
	// wxHandler.addToSocialSDK();
	// wxHandler.setRefreshTokenAvailable(true);
	//
	// // isWxInstall = wxHandler.isClientInstalled();
	// }

	private void initViews() {
		tvlogin = (TextView) findViewById(R.id.tvlogin);
		tvlogin.setOnClickListener(this);
		tvregster = (TextView) findViewById(R.id.tvregster);
		tvregster.setOnClickListener(this);
		// img_wx = (ImageView) findViewById(R.id.img_wx);
		// img_wx.setOnClickListener(this);
		// img_qq = (ImageView) findViewById(R.id.img_qq);
		// img_qq.setOnClickListener(this);
		// img_wb = (ImageView) findViewById(R.id.img_wb);
		// img_wb.setOnClickListener(this);

		// tv_third = (TextView) findViewById(R.id.tv_third);
		findViewById(R.id.lll).setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tv_zhanghaodenglu = (TextView) findViewById(R.id.tv_zhanghaodenglu);
		tv_zhanghaodenglu.setOnClickListener(this);
		imgbtn_left_icon = (ImageButton) findViewById(R.id.imgbtn_left_icon);
		imgBack = (LinearLayout) findViewById(R.id.img_back);
		// imgbtn_left_icon.setOnClickListener(this);
		imgBack.setOnClickListener(this);
		imgbtn_left_icon.setVisibility(View.VISIBLE);
		tv_chreg = (TextView) findViewById(R.id.tv_chreg);
		tv_chreg.setOnClickListener(this);
		container_login = (FrameLayout) findViewById(R.id.container_login);

		if (isQudao) {
			if(!isOldLogin){
				container_login.setBackgroundColor(Color.WHITE);
				findViewById(R.id.lin_bottom).setVisibility(View.GONE);
				findViewById(R.id.v_v).setVisibility(View.VISIBLE);	
			}else{
				findViewById(R.id.tvlogin).setVisibility(View.GONE);
				findViewById(R.id.tvregster).setVisibility(View.GONE);
				findViewById(R.id.lin_bottom).setVisibility(View.GONE);
				findViewById(R.id.lin_bottom).setBackgroundColor(Color.parseColor("#f0f0f0"));
				findViewById(R.id.lll).setBackgroundColor(Color.parseColor("#f0f0f0"));
			}
		}

		if (null != isPushOut) {
			customDialog();
		}
		setTextTitleColor();
	}

	private static AlertDialog dialog;

	private void customDialog() {
		AlertDialog.Builder builder = new Builder(this);
		// 自定义一个布局文件
		View view = View.inflate(this, R.layout.show_relogin_dialog, null);
		TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
		tv_des.setText("您已在别处登录～～请重新登录");

		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 把这个对话框取消掉
				dialog.dismiss();
			}
		});

		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	/***
	 * 设置标题颜色
	 */
	private void setTextTitleColor() {

		if (login_register.equals("login")) {
			tvlogin.setTextColor(getResources().getColor(R.color.white));
			tvregster.setTextColor(getResources().getColor(R.color.common_red));

			// tvlogin.setBackgroundColor(getResources().getColor(R.color.actionbar_bottom));
			// tvregster.setBackgroundColor(getResources().getColor(R.color.white));
			tvlogin.setBackgroundResource(R.drawable.btn_back_red);
			tvregster.setBackgroundResource(R.drawable.btn_back_white);

			// tv_third.setVisibility(View.VISIBLE);
			if (isQudao) {
				if(!isOldLogin){
					tvTitle_base.setText("微信授权");	
					tv_zhanghaodenglu.setVisibility(View.VISIBLE);
					
					
				}else{
					tvTitle_base.setText("老用户登录");	
				}
				
			} else {
				tvTitle_base.setText("登录");
			}

			tv_chreg.setVisibility(View.INVISIBLE);
		} else {
			// tvlogin.setTextColor(getResources().getColor(R.color.login_register_normal_color));
			// tvregster.setTextColor(getResources().getColor(R.color.login_register_select_color));
			//
			// tvlogin.setBackgroundColor(getResources().getColor(R.color.white));
			// tvregster.setBackgroundColor(getResources().getColor(R.color.actionbar_bottom));
			tvlogin.setTextColor(getResources().getColor(R.color.common_red));
			tvregster.setTextColor(getResources().getColor(R.color.white));

			tvlogin.setBackgroundResource(R.drawable.btn_back_white);
			tvregster.setBackgroundResource(R.drawable.btn_back_red);

			// tv_third.setVisibility(View.GONE);

			tv_chreg.setVisibility(View.VISIBLE);
			if ("邮箱注册".equals(tv_chreg.getText().toString())) {
				tvTitle_base.setText("手机注册");
			} else if ("手机注册".equals(tv_chreg.getText().toString())) {
				tvTitle_base.setText("邮箱注册");
			} else {
				tvTitle_base.setText("注册");
			}

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvlogin:
			login_register = "login";
			setTextTitleColor();
			ft = fm.beginTransaction();
			if (lgFragment == null) {
				lgFragment = OldLoginFragment.newInstance("login");
				ft.add(R.id.container_login, lgFragment, "login");
				lgFragment.setThirdClickListener(this);
			}
			ft.show(lgFragment);
			if (rsFragment != null) {
				ft.hide(rsFragment);
			}
			ft.commit();
			break;
		case R.id.tvregster:
			login_register = "register";
			setTextTitleColor();
			ft = fm.beginTransaction();
			if (rsFragment == null) {
				rsFragment = RegisterFragment.newInstance("register");
				ft.add(R.id.container_login, rsFragment, "register");
				rsFragment.setThirdClickListener(this);
			}
			ft.show(rsFragment);
			if (lgFragment != null) {
				ft.hide(lgFragment);
			}
			// ft.replace(R.id.container_login, rsFragment);
			ft.commit();
			break;
		// case R.id.imgbtn_left_icon:
		case R.id.img_back:
			// appManager.finishActivity();`
			onBackPressed();
			break;
		case R.id.tv_zhanghaodenglu:
			OldUserLoginActivity.isOldLogin = true;
			Intent intent = new Intent(OldUserLoginActivity.this, OldUserLoginActivity.class);
			intent.putExtra("isSign", true);
			intent.putExtra("login_register", "login");
			((FragmentActivity) context).startActivity(intent);
			((FragmentActivity) context).overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_match);
			break;
	
		default:
			break;
		}

	}

	public ProgressDialog dlg;

	public void showMessage(String strMsg) {
		dlg = new ProgressDialog(this);
		dlg.setMessage(strMsg);
		dlg.setProgress(ProgressDialog.STYLE_HORIZONTAL);
		// dlg.setProgress(ProgressDialog.STYLE_SPINNER);
		dlg.setCancelable(false);
		if (isPasuse == 0) {
			dlg.show();
		}

	}

	public void dissMessage() {
		if (dlg != null || dlg.isShowing()) {
			dlg.dismiss();
		}

	}

	/***
	 * 登录 type = 0 微信登录 1 QQ登陆 2 新浪 微博登录
	 */
	private void login(final SHARE_MEDIA platform, final int type, final View v) {

		mController.doOauthVerify(OldUserLoginActivity.this, platform, new UMAuthListener() {

			@Override
			public void onStart(SHARE_MEDIA platform) {
				Toast.makeText(OldUserLoginActivity.this, "授权开始", Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onError(SocializeException e, SHARE_MEDIA platform) {
				Toast.makeText(OldUserLoginActivity.this, "授权失败" + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onComplete(Bundle value, SHARE_MEDIA platform) {

				// showMessage("请稍候。。。。");
				String unionid = value.containsKey("unionid") ? value.getString("unionid") : "";
				LogYiFu.e("微信授权三方", value.toString());
				// MyLogYiFu.e("uid", uid);
				// if (!TextUtils.isEmpty(uid)) {
				user = new UserIfoThird();
				if (type == 0) {// 微信

					// 获取uid
					user.setUnionid(unionid);
					user.setToken(value.getString("access_token"));
					user.setUid(value.getString("openid"));

				} else if (type == 1) {// QQ
					user.setUid(value.getString("openid"));
					user.setToken(value.getString("access_token"));
				} else if (type == 2) {// 新浪微博
					user.setUid(value.getString("uid"));
					// user.setNickname(value.getString("userName"));
					// user.setToken(value.getString("access_key"));
					user.setToken(value.getString("access_token"));

				}

				getUserInfo(platform, type, v);

				// 上传IMEI

				// } else {
				// Toast.makeText(LoginActivity.this, "授权失败...",
				// Toast.LENGTH_SHORT).show();
				// }
				SharedPreferencesUtil.saveBooleanData(OldUserLoginActivity.this, "ISCHUCHIDNEGLU", true); // 标志改为已登录
				SharedPreferencesUtil.saveBooleanData(OldUserLoginActivity.this, "isLoginLogin", true);
			}

			@Override
			public void onCancel(SHARE_MEDIA platform) {
				ToastUtil.showShortText(OldUserLoginActivity.this, "授权取消");
			}
		});

	}

	private int sex = 0;

	/**
	 * 获取授权平台的用户信息</br>
	 */
	private void getUserInfo(SHARE_MEDIA platform, final int type, final View v) {

		mController.getPlatformInfo(this, platform, new UMDataListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(int status, Map<String, Object> info) {
				/*
				 * String showText = ""; if (status ==
				 * StatusCode.ST_CODE_SUCCESSED) { showText = "用户名：" +
				 * info.get("screen_name").toString(); LogYiFu.d("#########",
				 * "##########" + info.toString()); } else { showText =
				 * "获取用户信息失败"; }
				 */
				if (info != null && user != null) {

					if (type == 0) {// 微信
						if (status == 200 && info != null) {
							// StringBuilder sb = new StringBuilder();
							// Set<String> keys = info.keySet();
							user.setUnionid(info.get("unionid").toString());
							user.setNickname(info.get("nickname").toString());
							user.setPic(info.get("headimgurl").toString());
							user.setUsertype(2 + 3);

							// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
							sex = Integer.parseInt(info.get("sex").toString());

							if (sex != 1 && sex != 2) {
								sex = 0;
							}

							if (isQudao && !MainMenuActivity.isNewQudao) {
								if (sex == 1) { // 渠道包用
									customDialog(OldUserLoginActivity.this);
									return;
								}
							}

						} else {
							LogYiFu.d("TestData", "发生错误：" + status);
						}

					} else if (type == 1) {// QQ
						user.setNickname(info.get("screen_name").toString());
						user.setPic(info.get("profile_image_url").toString());
						user.setUsertype(1 + 3);

					} else if (type == 2) {// 新浪微博
						user.setPic(info.get("profile_image_url").toString());

						user.setNickname(info.get("screen_name").toString());
						user.setToken(info.get("access_token").toString());

						user.setUsertype(3 + 3);

					}
					LongThird(user, v, sex);
				}
			}
		});
	}

	/****
	 * 第三方登陆本地后台
	 * 

	 * @param v
	 */
	private void LongThird(final UserIfoThird userr, View v, final int sex) {
		// dissMessage();
		final String channel = DeviceUtils.getChannelCode(this);
		new SAsyncTask<String, Void, UserInfo>(this, v, R.string.wait) {
			@Override
			protected UserInfo doInBackground(FragmentActivity context, String... params) throws Exception {
				UserInfo user = ComModel.LoginThird(OldUserLoginActivity.this, userr.getUid(), userr.getUnionid(), userr.getNickname(),
						userr.getPic(), userr.getToken(),userr.getUsertype()+"", deviceToken,sex+"");

				
				 
				 
//				 public static UserInfo LoginThird(Context context, String
//				 uid, String unionid, String nickename, String pic, String
//				  token, String usertype, String type, String deviceToken,
//				 String gender) throws Exception {
//				  
//				 }

				/**
				 * 
				 * user.getUid(), user.getUnionid(), user.getNickname(),
				 * user.getPic(), user.getToken(), "" + user.getUsertype(), sex
				 * + ""
				 * 
				 * 
				 */

				/*
				 * HashMap<String, Object> map = ComModel2
				 * .queryMyIntegral(context); List<String> list = (List<String>)
				 * map.get("fulfill"); if (list != null &&
				 * map.get("everyDayFulfill") != null) {
				 * list.addAll((List<String>) map.get("everyDayFulfill")); }
				 * YJApplication.instance.setTaskMap(list);
				 */
				return user;
			}

			@SuppressWarnings("static-access")
			@Override
			protected void onPostExecute(FragmentActivity context, UserInfo user, Exception e) {

				if (e != null) {// 登陆异常

					if (e instanceof YException && ((YException) e).getErrorCode() == 4) {
						// // 帐号未验证。跳转到验证页面
					} else if (e instanceof YException && ((YException) e).getErrorCode() == 2) {
					} else if (e instanceof YException && ((YException) e).getErrorCode() == 101) {
						user = YCache.getCacheUserSafe(context);
						// 调用sdk注册方法
						PublicUtil.registerHuanxin1(OldUserLoginActivity.this, user, "123456");
						// getHuanXinPassword(user);
						LogYiFu.e("getUser_id", user.getUser_id() + "");
						LogYiFu.e("getUser_id", MD5Tools.MD5("123456"));
					}
				} else {// 登陆成功

					if (SettingCommonFragmentActivity.instanes != null) {

						SettingCommonFragmentActivity.instanes.finish();
					}

					SharedPreferencesUtil.saveBooleanData(context, Pref.SHOWSIGNUPDATA, true);
					ModUtil.hasMond(context,false);


					getPayRedBackage();

					// if (isSign) {
					// CenterToast.centerToast(context, "更新啦！！！！！！！！！");
					//
					// isSign = false;
					// SharedPreferencesUtil.saveBooleanData(context,
					// Pref.ISSHOWEDSIGNUP, false);
					//
					// } else {
					// SharedPreferencesUtil.saveBooleanData(context,
					// Pref.ISSHOWEDSIGNUP, true);
					// }

					// 获取购物车数据并添加到购物车数据库
					GetShopCart.querShopCart(OldUserLoginActivity.this, 1);
					
					if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
						// 获取金币金券相关数据
						GetJinBiJinQuanUtils.getJinBi(context);
						GetJinBiJinQuanUtils.getJinQuan(context);
					}

					// 为了解决混淆问题 v3.3.1已解决
					SharedPreferencesUtil.saveStringData(context, "HUNXIAO", 1 + "");

					if (user != null) {

						// if (chan == 47||chan == 48) {
						//
						// int status = Integer.parseInt(
						// SharedPreferencesUtil.getStringData(context,
						// Pref.thrid_login_status, ""));
						//
						// if (status == 2){
						//
						// YCache.cleanToken(context);
						// YCache.cleanUserInfo(context);
						// ComModel.clearLoginFlag(context);
						// AppManager.getAppManager().finishAllActivityOfEveryDayTask();
						// YJApplication.isLogined = false;
						//
						// return;
						// }
						//
						// }

						YJApplication.isLogined = true;
						// Toast.makeText(LoginActivity.this, "第三方登录成功...",
						// Toast.LENGTH_SHORT).show();
						getMineLike();
						LogYiFu.e("getUser_id", user.getUser_id() + "");
						LogYiFu.e("getUser_id", MD5Tools.MD5("123456"));
						// getHuanXinPassword(user);
						// PublicUtil.registerHuanxin(LoginActivity.this, user,
						// "123456");
						// appManager.finishActivity();
						getIntegral();
						String phone = YCache.getCacheUser(context).getPhone();
						LogYiFu.e("hehe", "11111");
						if ("null".equals(phone) || "".equals(phone) || phone.length() < 11) {
							getHuanXinPassword(user, false, isQudao);
							LogYiFu.e("hehe", "hehh" + phone);
							Intent intent = new Intent(OldUserLoginActivity.this, SettingCommonFragmentActivity.class);
							intent.putExtra("flag", "bindPhoneFragment");
							intent.putExtra("bool", false);
							intent.putExtra("isChanal", isQudao); // 渠道包用true,其他用false
																	// 为了测试方便暂时用true
							intent.putExtra("phoneNum", "");
							intent.putExtra("wallet", "account");
							intent.putExtra("thirdparty", "thirdparty");
							startActivity(intent);
						} else if ("shopcart".equals(mShopCart)) {
							getHuanXinPassword(user, true, isQudao);
							CommonUtils.finishActivity(MainMenuActivity.instances);

							Intent intent2 = new Intent(OldUserLoginActivity.this, MainMenuActivity.class);
							intent2.putExtra("shopcart", "shopcart");
							startActivity(intent2);
						} else if(isSign){
							
							
//							Intent intent = new Intent(context, MainMenuActivity.class);
//							intent.putExtra("Exit30", true);
//							context.startActivity(intent);
							
							// 跳至赚钱
							SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
							context.startActivity(new Intent(context, CommonActivity.class));




							
							
						}else {
						

							// if (isSign) {
							// CenterToast.centerToast(context, "更新啦！！！！！！！！！");
							//
							// isSign = false;
							// SharedPreferencesUtil.saveBooleanData(context,
							// Pref.ISSHOWEDSIGNUP, false);
							//
							// } else {
							// SharedPreferencesUtil.saveBooleanData(context,
							// Pref.ISSHOWEDSIGNUP, true);
							// }

							getHuanXinPassword(user, true, isQudao);
						}
					}
				}

			};

	@Override
	protected boolean isHandleException() {
		return true;
	};

	}.execute(user.getUid(),user.getUnionid(),user.getNickname(),user.getPic(),user.getToken(),""+user.getUsertype(),sex+"");

	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_OK);

		appManager.finishActivity(this);
		overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	private void getIntegral() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					HashMap<String, Object> map = ComModel2.queryMyIntegral(OldUserLoginActivity.this);
					List<String> list = (List<String>) map.get("fulfill");
					if (list != null && map.get("everyDayFulfill") != null) {
						list.addAll((List<String>) map.get("everyDayFulfill"));
					}
					YJApplication.instance.setTaskMap(list);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void qqClick(View v) {
		// TODO Auto-generated method stub
		login(SHARE_MEDIA.QQ, 1, v);
	}

	@Override
	public void wxClick(View v) {
		// TODO Auto-generated method stub

		// if (chan == 47||chan == 48) {
//		if(GuideActivity.needShouquan) {
//			//每次登陆都强制微信授权 清除上次微信授权信息
//			final UMSocialService mController =
//					UMServiceFactory.getUMSocialService("");
//			UMWXHandler wxHandler = new UMWXHandler(OldUserLoginActivity.this,
//					WxPayUtil.APP_ID, WxPayUtil.APP_SECRET);
//			wxHandler.addToSocialSDK();
//			wxHandler.setRefreshTokenAvailable(false);
//			mController.deleteOauth(OldUserLoginActivity.this, null, null);
//		}
		// }

		login(SHARE_MEDIA.WEIXIN, 0, v);

	}

	@Override
	public void sinaClick(View v) {
		// TODO Auto-generated method stub
		login(SHARE_MEDIA.SINA, 2, v);
	}

	private void addWX() {
		// 添加微信平台

//		if (MainMenuActivity.APP_ID != null &&!MainMenuActivity.APP_ID.equals("")) {
//			UMWXHandler wxHandler = new UMWXHandler(OldUserLoginActivity.this, MainMenuActivity.APP_ID,
//					MainMenuActivity.APP_SECRET);
//			wxHandler.addToSocialSDK();
//
//			// if (chan == 47||chan == 48) {
//			wxHandler.setRefreshTokenAvailable(true);
//			// } else {
//			// wxHandler.setRefreshTokenAvailable(false);
//			// }
//
//		}

		// isWxInstall = wxHandler.isClientInstalled();
	}

	/**
	 * 获取我的喜好
	 */
	private void getMineLike() {
		new SAsyncTask<Void, Void, String>((FragmentActivity) OldUserLoginActivity.this, R.string.wait) {

			@Override
			protected String doInBackground(FragmentActivity context, Void... params) throws Exception {
				return ComModel2.getMineLike(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, String result, Exception e) {
				super.onPostExecute(context, result, e);

				if (null == e && result != null) {
					// SharedPreferences sp =
					// context.getSharedPreferences("data",
					// Context.MODE_PRIVATE);
					// Editor et = sp.edit();
					// et.putStringSet(""+YCache.getCacheUser(context).getUser_id(),
					// result);
					SharedPreferencesUtil.saveStringData(context, "" + YCache.getCacheUser(context).getUser_id(),
							result);
				}
			}

		}.execute();
	}

	private static void customDialog(final Activity activity) {
		AlertDialog.Builder builder = new Builder(activity);
		// 自定义一个布局文件
		View view = View.inflate(activity, R.layout.payback_esc_apply_dialog, null);
		TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
		tv_des.setText("活动已结束，谢谢关注");
		// dialog.setCancelable(false);
		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 把这个对话框取消掉
				dialog.dismiss();
				activity.finish();
				// AppManager.getAppManager().finishAllActivity();

			}
		});

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				activity.finish();
				// AppManager.getAppManager().finishAllActivity();
			}
		});

		builder.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				activity.finish();

			}
		});

		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	/**
	 * 获取环信password
	 */
	private void getHuanXinPassword(final UserInfo user, final boolean bindPhoneFlag, final boolean isQudao) {
		new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) OldUserLoginActivity.this, R.string.wait) {

			@Override
			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				return ComModel2.getHuanXinPassword(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
				super.onPostExecute(context, result, e);

				LogYiFu.e("zzz", result + "");

				if (null == e && result != null) {
					String code = (String) result.get("code");

					if ("".equals(code) || null == code) {
						code = "123456";
						PublicUtil.registerHuanxinForLogin(OldUserLoginActivity.this, user, "" + MD5Tools.MD5(code),
								bindPhoneFlag, isQudao);
						SharedPreferencesUtil.saveStringData(context, Pref.pd, MD5Tools.MD5(code));
					} else {
						PublicUtil.registerHuanxinForLogin(OldUserLoginActivity.this, user, "" + code, bindPhoneFlag, isQudao);
						SharedPreferencesUtil.saveStringData(context, Pref.pd, code);
					}

					// context.getSharedPreferences(Pref.pd,
					// Context.MODE_PRIVATE).edit().putString(Pref.pd, code)
					// .commit();
				}
			}

		}.execute();
	}
}
