package com.yssj.ui.activity.logins;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ansen.http.net.HTTPCaller;
import com.ansen.http.net.RequestDataCallback;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.UMSsoHandler;
import com.yssj.Constants;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.CommonLoadingView;
import com.yssj.custom.view.NewPDialog;
import com.yssj.entity.UserIfoThird;
import com.yssj.entity.UserInfo;
import com.yssj.huanxin.PublicUtil;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;
import com.yssj.network.YException;
import com.yssj.service.ApkDownloadManager;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.setting.FeedBackActivity;
import com.yssj.ui.activity.setting.SettingCommonFragmentActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.CheckStrUtil;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DeviceUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.ModUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;
import com.yssj.wxpay.WxPayUtil;
import com.yssj.wxpay.bean.WeiXin;
import com.yssj.wxpay.bean.WeiXinInfo;
import com.yssj.wxpay.bean.WeiXinToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.yssj.ShopBeautyActivity;

/***
 * ????????????
 *
 * @author Administrator
 *
 */
@SuppressWarnings("WrongConstant")
public class LoginActivity extends BasicActivity
        implements OnClickListener, RegisterFragment.OnThirdClickListener, LoginFragment.OnThirdClickListener {

    // ??????????????????
    public static boolean noUserRegist = MainMenuActivity.noUserRegist; // ?????????
    // public static Boolean noUserRegist = true; //??????
    public static boolean isOldLogin = false;// ???????????????????????????

    public static boolean sanFangShow = false;// ??????????????????????????????

    private AppManager appManager;
    public static LoginActivity instances;
    public static String kefu;
    private TextView tvlogin, tvregster;
    private LoginFragment lgFragment;
    private RegisterFragment rsFragment;
    private FragmentTransaction ft;
    private FragmentManager fm;
    private String login_register;
    private TextView tv_chreg;
    private FrameLayout container_login;
    public static Boolean phoneboolll;

    private TextView tvTitle_base, tv_zhanghaodenglu;
    private ImageButton imgbtn_left_icon;
    private LinearLayout imgBack;
    public Context mContext;
    // private TextView tv_third;

    // private ImageView img_wx, img_qq, img_wb;
    // ???????????????Controller, ??????????????????SDK???????????????????????????
    private UMSocialService mController;

    private UserIfoThird user;
    private YJApplication appctx;
    private String isPushOut;

    private String deviceToken = "";
    public static String mShopCart = "";
    public static Boolean isSign = false;
    private int chan;
    private IWXAPI wxAPI;
    private CommonLoadingView loading;

    private int homePageSwitchLoginTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        fm = getSupportFragmentManager();

        super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
        EventBus.getDefault().register(this);//??????
        mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);
        setContentView(R.layout.activity_login);
        mContext = this;

        loading = new CommonLoadingView(this);


        wxAPI = WXAPIFactory.createWXAPI(this, WxPayUtil.APP_ID, true);
        wxAPI.registerApp(WxPayUtil.APP_ID);

        login_register = getIntent().getStringExtra("login_register");
        if (null == login_register) {
            login_register = "login";
        }
        isPushOut = getIntent().getStringExtra("10030");
        mShopCart = getIntent().getStringExtra("shopcart");
        homePageSwitchLoginTo = getIntent().getIntExtra("homePageSwitchLoginTo",0);
        isSign = getIntent().getBooleanExtra("isSign", false);
        LogYiFu.e("zzqyi", "333" + mShopCart);
        initFragment();
        initViews();
        instances = this;

        chan = Integer.parseInt(DeviceUtils.getChannelCode(LoginActivity.this));

        appctx = (YJApplication) getApplicationContext();
        LogYiFu.e("deviceToken", deviceToken);
        addQZoneQQPlatform();
        // addWxPlatform();
        addWX();
        // ????????????SSO handler
//        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        appManager = AppManager.getAppManager();
        appManager.addActivity(this);
        // login_register

        if (savedInstanceState != null) {
            // ToastUtil.showShortText(getApplicationContext(),
            // "??????????????????????????????~");//10 11???
            login_register = savedInstanceState.getString("login_register");
            lgFragment = (LoginFragment) fm.findFragmentByTag("login");
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
        // PublicUtil2.openId(SHARE_MEDIA.WEIXIN, 0, this);//(??????????????????)
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

	/*
     * @Override protected void onResume() { super.onResume();
	 * JPushInterface.onResume(this); }
	 *
	 * @Override protected void onPause() { super.onPause();
	 * JPushInterface.onPause(this); }
	 */

    private void initFragment() {
        ft = fm.beginTransaction();

        // lgFragment = LoginFragment.newInstance("login");
        // rsFragment = RegisterFragment.newInstance("register");
        //
        // lgFragment.setThirdClickListener(this);
        // rsFragment.setThirdClickListener(this);
        // ft.add(R.id.container_login, lgFragment, "login");
        // ft.add(R.id.container_login, rsFragment, "register");

        if (login_register.equals("login")) {
            // ft.replace(R.id.container_login, lgFragment);
            if (lgFragment == null) {
                lgFragment = LoginFragment.newInstance("login");
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


    private void addQZoneQQPlatform() {
        String appId = "1104724623";
        String appKey = "VpAQVytFGidSRx6l";
        // ??????QQ??????, ????????????QQ???????????????target url
//        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, appId, appKey);
//        qqSsoHandler.setTargetUrl("http://www.umeng.com");
//
//        qqSsoHandler.addToSocialSDK();
//        // ??????QZone??????
//        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, appId, appKey);
//        qZoneSsoHandler.addToSocialSDK();
        // isQQInstall = qqSsoHandler.isClientInstalled();
    }

    private boolean isWxInstall;

    // private void addWxPlatform() {
    // // ??????????????????
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

        if (noUserRegist) {
            if (!isOldLogin) {

                if (sanFangShow) {
                    container_login.setBackgroundColor(Color.parseColor("#f0f0f0"));
                } else {
                    container_login.setBackgroundColor(Color.WHITE);

                }

                findViewById(R.id.lin_bottom).setVisibility(View.GONE);
                findViewById(R.id.v_v).setVisibility(View.VISIBLE);
            } else {
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
        // ???????????????????????????
        View view = View.inflate(this, R.layout.show_relogin_dialog, null);
        TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
        tv_des.setText("??????????????????????????????????????????");

        Button ok = (Button) view.findViewById(R.id.ok);
        ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);

        ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // ???????????????????????????
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
     * ??????????????????
     */
    private void setTextTitleColor() {

        if (login_register.equals("login")) {
            tvlogin.setTextColor(getResources().getColor(R.color.white));
            tvregster.setTextColor(getResources().getColor(R.color.common_red));

            // tvlogin.setBackgroundColor(getResources().getColor(R.color.actionbar_bottom));
            // tvregster.setBackgroundColor(getResources().getColor(R.color.white));
            tvlogin.setBackgroundResource(R.drawable.btn_back_red);
            tvregster.setBackgroundResource(R.drawable.btn_back_white);

            boolean san = LoginActivity.sanFangShow;

            if (san) {
                findViewById(R.id.lin_bottom).setVisibility(View.VISIBLE);
                tvTitle_base.setText("??????");
                tv_zhanghaodenglu.setVisibility(View.GONE);
            }

            // tv_third.setVisibility(View.VISIBLE);
            if (noUserRegist && !LoginActivity.sanFangShow) {
                if (!isOldLogin) {
                    tvTitle_base.setText("????????????");
                    tv_zhanghaodenglu.setVisibility(View.VISIBLE);
                } else {
                    tv_zhanghaodenglu.setVisibility(View.VISIBLE);
                    tvTitle_base.setText("????????????");
                }

            } else {
                tvTitle_base.setText("??????");
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
            if ("????????????".equals(tv_chreg.getText().toString())) {
                tvTitle_base.setText("????????????");
            } else if ("????????????".equals(tv_chreg.getText().toString())) {
                tvTitle_base.setText("????????????");
            } else {
                tvTitle_base.setText("??????");
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
                    lgFragment = LoginFragment.newInstance("login");
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

                if (null != OldUserLoginActivity.instances) {
                    OldUserLoginActivity.instances.finish();
                }

                Intent intent = new Intent(LoginActivity.this, OldUserLoginActivity.class);
                intent.putExtra("isSign", true);
                intent.putExtra("login_register", "login");
                // ((FragmentActivity) LoginActivity.this).startActivity(intent);
                // ((FragmentActivity)
                // LoginActivity.this).overridePendingTransition(R.anim.slide_left_in,
                // R.anim.slide_match);
                //
                startActivity(intent);



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
     * ?????? type = 0 ???????????? 1 QQ?????? 2 ?????? ????????????
     */
    private void login(final SHARE_MEDIA platform, final int type, final View v) {


        if (type == 0) {//???????????????????????????
            loading.show();

            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = String.valueOf(System.currentTimeMillis());
            wxAPI.sendReq(req);


            return;
        }


        mController.doOauthVerify(LoginActivity.this, platform, new UMAuthListener() {

            @Override
            public void onStart(SHARE_MEDIA platform) {
                Toast.makeText(LoginActivity.this, "????????????", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
                Toast.makeText(LoginActivity.this, "????????????" + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {

                // showMessage("?????????????????????");
                String unionid = value.containsKey("unionid") ? value.getString("unionid") : "";
                LogYiFu.e("??????????????????", value.toString());
                // MyLogYiFu.e("uid", uid);
                // if (!TextUtils.isEmpty(uid)) {
                user = new UserIfoThird();
                if (type == 0) {// ??????

                    // ??????uid
                    user.setUnionid(unionid);
                    user.setToken(value.getString("access_token"));
//                    user.setUid(value.getString("openid"));
                    user.setOpenid(value.getString("openid"));

                } else if (type == 1) {// QQ
                    user.setUid(value.getString("openid"));
                    user.setToken(value.getString("access_token"));
                } else if (type == 2) {// ????????????
                    user.setUid(value.getString("uid"));
                    // user.setNickname(value.getString("userName"));
                    // user.setToken(value.getString("access_key"));
                    user.setToken(value.getString("access_token"));

                }

                getUserInfo(platform, type, v);

                // ??????IMEI

                // } else {
                // Toast.makeText(LoginActivity.this, "????????????...",
                // Toast.LENGTH_SHORT).show();
                // }
                SharedPreferencesUtil.saveBooleanData(LoginActivity.this, "ISCHUCHIDNEGLU", true); // ?????????????????????
                SharedPreferencesUtil.saveBooleanData(LoginActivity.this, "isLoginLogin", true);
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                ToastUtil.showShortText(LoginActivity.this, "????????????");
            }
        });

    }

    private int sex = 0;

    /**
     * ?????????????????????????????????</br>
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
				 * StatusCode.ST_CODE_SUCCESSED) { showText = "????????????" +
				 * info.get("screen_name").toString(); LogYiFu.d("#########",
				 * "##########" + info.toString()); } else { showText =
				 * "????????????????????????"; }
				 */
                if (info != null && user != null) {

                    if (type == 0) {// ??????
                        if (status == 200 && info != null) {
                            // StringBuilder sb = new StringBuilder();
                            // Set<String> keys = info.keySet();
                            user.setUnionid(info.get("unionid").toString());
                            user.setNickname(info.get("nickname").toString());
                            user.setPic(info.get("headimgurl").toString());
                            user.setUsertype(2 + 3);

                            // ????????????????????????1?????????????????????2?????????????????????0????????????
                            sex = Integer.parseInt(info.get("sex").toString());

                            if (sex != 1 && sex != 2) {
                                sex = 0;
                            }

                            int channel = 9;
                            String ch = DeviceUtils.getChannelCode(instances);
                            try {
                                channel = Integer.parseInt(ch);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            //??????????????????
//                            if (noUserRegist && channel != 17 && channel != 45 && !MainMenuActivity.isNewQudao) {
//                                if (sex == 1) { // ?????????????????????17 45?????????????????????
//                                    customDialog(LoginActivity.this);
//                                    return;
//                                }
//                            }

                        } else {
                            LogYiFu.d("TestData", "???????????????" + status);
                        }

                    } else if (type == 1) {// QQ
                        user.setNickname(info.get("screen_name").toString());
                        user.setPic(info.get("profile_image_url").toString());
                        user.setUsertype(1 + 3);

                    } else if (type == 2) {// ????????????
                        user.setPic(info.get("profile_image_url").toString());

                        user.setNickname(info.get("screen_name").toString());
                        user.setToken(info.get("access_token").toString());

                        user.setUsertype(3 + 3);

                    }
//                    if (GuideActivity.needFengKong) {
                        LongThirdNeedBindPhone(user, v, sex);

//                    } else {
//                        LongThird(user, v, sex);

//                    }
                }
            }
        });
    }


    /****
     * ???????????????????????????
     *
     * @param v
     */
    private void LongThird(final UserIfoThird userr, View v, final int sex) {
        // dissMessage();
        final String channel = DeviceUtils.getChannelCode(this);
        new SAsyncTask<String, Void, UserInfo>(this, v, R.string.wait) {
            @Override
            protected UserInfo doInBackground(FragmentActivity context, String... params) throws Exception {
                UserInfo user = ComModel.LoginThird(LoginActivity.this, userr.getOpenid(), userr.getUnionid(),
                        userr.getNickname(), userr.getPic(), userr.getToken(), userr.getUsertype() + "", deviceToken,
                        sex + "");

                // public static UserInfo LoginThird(Context context, String
                // uid, String unionid, String nickename, String pic, String
                // token, String usertype, String type, String deviceToken,
                // String gender) throws Exception {
                //
                // }

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

                if (e != null) {// ????????????
                    loading.dismiss();

                    if (e instanceof YException && ((YException) e).getErrorCode() == 4) {
                        // // ???????????????????????????????????????
                    } else if (e instanceof YException && ((YException) e).getErrorCode() == 2) {
                    } else if (e instanceof YException && ((YException) e).getErrorCode() == 101) {
                        user = YCache.getCacheUserSafe(context);
                        // ??????sdk????????????
                        PublicUtil.registerHuanxin1(LoginActivity.this, user, "123456");
                        // getHuanXinPassword(user);
                        LogYiFu.e("getUser_id", user.getUser_id() + "");
                        LogYiFu.e("getUser_id", MD5Tools.MD5("123456"));
                    }
                } else {// ????????????
                    loading.dismiss();

                    SharedPreferencesUtil.saveBooleanData(context, "isrelogin", true);

                    if (SettingCommonFragmentActivity.instanes != null) {

                        SettingCommonFragmentActivity.instanes.finish();
                    }

                    SharedPreferencesUtil.saveBooleanData(context, Pref.SHOWSIGNUPDATA, true);
                    SharedPreferencesUtil.saveBooleanData(LoginActivity.this, "ISCHUCHIDNEGLU", true); // ?????????????????????
                    SharedPreferencesUtil.saveBooleanData(LoginActivity.this, "isLoginLogin", true);


                    if (user != null) {
                        YJApplication.isLogined = true;


                        getHuanXinPassword(user, true, noUserRegist);

                    }


                }

            }

            @Override
            protected boolean isHandleException() {
                return true;
            }


        }.execute(user.getUid(), user.getUnionid(), user.getNickname(), user.getPic(), user.getToken(),
                "" + user.getUsertype(), sex + "");

    }

    /****
     * ???????????????????????????
     *
     * @param v
     */
    private void LongThirdNeedBindPhone(final UserIfoThird userr, View v, final int sex) {
        // dissMessage();
        final String channel = DeviceUtils.getChannelCode(this);
        new SAsyncTask<String, Void, UserInfo>(this, v, R.string.wait) {
            @Override
            protected UserInfo doInBackground(FragmentActivity context, String... params) throws Exception {
                UserInfo user = ComModel.LoginThird(LoginActivity.this, userr.getOpenid(), userr.getUnionid(),
                        userr.getNickname(), userr.getPic(), userr.getToken(), userr.getUsertype() + "", deviceToken,
                        sex + "");

                // public static UserInfo LoginThird(Context context, String
                // uid, String unionid, String nickename, String pic, String
                // token, String usertype, String type, String deviceToken,
                // String gender) throws Exception {
                //
                // }

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

                if (e != null) {// ????????????
                    loading.dismiss();

                    if (e instanceof YException && ((YException) e).getErrorCode() == 4) {
                        // // ???????????????????????????????????????
                    } else if (e instanceof YException && ((YException) e).getErrorCode() == 2) {
                    } else if (e instanceof YException && ((YException) e).getErrorCode() == 101) {
                        user = YCache.getCacheUserSafe(context);
                        // ??????sdk????????????
                        PublicUtil.registerHuanxin1(LoginActivity.this, user, "123456");
                        // getHuanXinPassword(user);
                        LogYiFu.e("getUser_id", user.getUser_id() + "");
                        LogYiFu.e("getUser_id", MD5Tools.MD5("123456"));
                    }
                } else {// ????????????
                    loading.dismiss();
                    SharedPreferencesUtil.saveStringData(mContext, Pref.USER_OPEN_ID,userr.getOpenid()+"");
                    SharedPreferencesUtil.saveBooleanData(context, "isrelogin", true);

                    if (SettingCommonFragmentActivity.instanes != null) {

                        SettingCommonFragmentActivity.instanes.finish();
                    }

                    SharedPreferencesUtil.saveBooleanData(context, Pref.SHOWSIGNUPDATA, true);
                    SharedPreferencesUtil.saveBooleanData(LoginActivity.this, "ISCHUCHIDNEGLU", true); // ?????????????????????
                    SharedPreferencesUtil.saveBooleanData(LoginActivity.this, "isLoginLogin", true);

                    // if (isSign) {
                    // CenterToast.centerToast(context, "????????????????????????????????????");
                    //
                    // isSign = false;
                    // SharedPreferencesUtil.saveBooleanData(context,
                    // Pref.ISSHOWEDSIGNUP, false);
                    //
                    // } else {
                    // SharedPreferencesUtil.saveBooleanData(context,
                    // Pref.ISSHOWEDSIGNUP, true);
                    // }

                    // ???????????????????????????????????????????????????
//                    GetShopCart.querShopCart(LoginActivity.this, 1);

//					if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
//						// ??????????????????????????????
//						GetJinBiJinQuanUtils.getJinBi(context);
//						GetJinBiJinQuanUtils.getJinQuan(context);
//					}

                    // ???????????????????????? v3.3.1?????????
                    SharedPreferencesUtil.saveStringData(context, "HUNXIAO", 1 + "");

                    CommonUtils.clearClipboardContent(mContext);
                    SharedPreferencesUtil.saveStringData(mContext,YCache.FENGKONG_CLIPBOARDCONTENT,"");

                    if(!GuideActivity.needFengKong || YUrl.debug){
                        if (user != null) {
                            YJApplication.isLogined = true;


                            getHuanXinPassword(user, true, noUserRegist);

                        }
                        return;
                    }

                    if (user != null) {
                        YJApplication.isLogined = true;
                        LoginFragment.needCheckDuobaoZhongjiang = true;
                        ModUtil.hasMond(LoginActivity.this, false);

                        getMineLike();
                        getIntegral();
                        String phone = YCache.getCacheUser(context).getPhone();
                        LogYiFu.e("hehe", "11111");
                        if ("null".equals(phone) || "".equals(phone) || phone.length() < 11) {
                            getHuanXinPassword(user, false, noUserRegist);
                            LogYiFu.e("hehe", "hehh" + phone);
                            Intent intent = new Intent(LoginActivity.this, SettingCommonFragmentActivity.class);
                            intent.putExtra("flag", "bindPhoneFragment");
                            intent.putExtra("sanFangFirstBind", true);//??????????????????????????? ??????????????????????????????
                            intent.putExtra("bool", false);
                            intent.putExtra("isChanal", noUserRegist); // ????????????true,?????????false
                            // ???????????????????????????true
                            intent.putExtra("phoneNum", "");
                            intent.putExtra("wallet", "account");
                            intent.putExtra("thirdparty", "thirdparty");
                            startActivity(intent);
                        } else if ("shopcart".equals(mShopCart)) {
                            getHuanXinPassword(user, true, noUserRegist);
                            Intent intent2 = new Intent(LoginActivity.this, MainMenuActivity.class);
                            intent2.putExtra("shopcart", "shopcart");
                            startActivity(intent2);
                        } else if (isSign) {

//							Intent intent = new Intent(context, MainMenuActivity.class);
//							intent.putExtra("Exit30", true);
//							context.startActivity(intent);

                            //????????????
                            getHuanXinPassword(user, true, noUserRegist);


                            // ????????????
                            SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
                            context.startActivity(new Intent(context, CommonActivity.class));


                        } else {

                            // if (isSign) {
                            // CenterToast.centerToast(context, "????????????????????????????????????");
                            //
                            // isSign = false;
                            // SharedPreferencesUtil.saveBooleanData(context,
                            // Pref.ISSHOWEDSIGNUP, false);
                            //
                            // } else {
                            // SharedPreferencesUtil.saveBooleanData(context,
                            // Pref.ISSHOWEDSIGNUP, true);
                            // }

                            getHuanXinPassword(user, true, noUserRegist);

//							GetUserABClass.getUserABclass(context);
                        }
                    }
                }

            }

            @Override
            protected boolean isHandleException() {
                return true;
            }


        }.execute(user.getUid(), user.getUnionid(), user.getNickname(), user.getPic(), user.getToken(),
                "" + user.getUsertype(), sex + "");

    }

    private void getPayRedBackage() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!SharedPreferencesUtil.getBooleanData(mContext, Pref.ISMADMONDAY, false)) {
                        //??????????????????????????????
                        boolean hasNoPayOrder = ModQingfeng.getNotFUoder(mContext);
                        if (hasNoPayOrder) {//??????????????????
                            HashMap<String, String> map = ComModel2.getYiDouHalve(mContext);
                            if (null == map || map.size() == 0 || "0".equals(map.get("end_date"))) {//????????????????????????????????????-----????????????????????????????????????????????????map
                                YJApplication.startFukuanYndao();
                            }
                        } else {//?????????????????????---??????????????????----????????????????????????????????????????????????????????????????????????


                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
//        setResult(RESULT_OK);

//        appManager.finishActivity(this);

        if(homePageSwitchLoginTo > 0
                || getIntent().getIntExtra("reviewers",0) > 0
                || getIntent().getBooleanExtra("mustLogin",false)
        ){
            ToastUtil.showShortText2("?????????????????????~");
            return;
        }


        instances.finish();
        overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** ??????SSO?????????????????????????????? */
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
                    HashMap<String, Object> map = ComModel2.queryMyIntegral(LoginActivity.this);
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

//		 if (chan == 47||chan == 48) {
//		if(GuideActivity.needShouquan) {
        //????????????????????????????????? ??????????????????????????????
//			final UMSocialService mController =
//					UMServiceFactory.getUMSocialService("");
//			UMWXHandler wxHandler = new UMWXHandler(LoginActivity.this,
//					WxPayUtil.APP_ID, WxPayUtil.APP_SECRET);
//			wxHandler.addToSocialSDK();
//			wxHandler.setRefreshTokenAvailable(false);
//			mController.deleteOauth(LoginActivity.this, null, null);
//		}
//		 }

        login(SHARE_MEDIA.WEIXIN, 0, v);

    }

    @Override
    public void sinaClick(View v) {
        // TODO Auto-generated method stub
        login(SHARE_MEDIA.SINA, 2, v);
    }

    private void addWX() {


        // ??????????????????
        if (null == MainMenuActivity.APP_ID || "".equals(MainMenuActivity.APP_ID)) {
            //?????????????????????
            boolean flag = SharedPreferencesUtil.getBooleanData(YJApplication.instance, "change_change", true);
            if (flag) {
                MainMenuActivity.APP_ID = "wxbb9728502635a425";
            } else {
                MainMenuActivity.APP_ID = "wx8c5fe3e40669c535";
            }
        }


        if (null == MainMenuActivity.APP_SECRET || "".equals(MainMenuActivity.APP_SECRET)) {
            //?????????????????????
            boolean flag = SharedPreferencesUtil.getBooleanData(YJApplication.instance, "change_change", true);
            if (flag) {
                MainMenuActivity.APP_SECRET = "d4624c36b6795d1d99dcf0547af5443d";
            } else {
                MainMenuActivity.APP_SECRET = "10d080a714d768427242e9b091d33959";
            }
        }


//        if (MainMenuActivity.APP_ID != null && !MainMenuActivity.APP_ID.equals("")) {
//            UMWXHandler wxHandler = new UMWXHandler(LoginActivity.this, MainMenuActivity.APP_ID,
//                    MainMenuActivity.APP_SECRET);
//            wxHandler.addToSocialSDK();
//
//            // if (chan == 47||chan == 48) {
//            wxHandler.setRefreshTokenAvailable(true);// true??????????????????false???????????????
//            // } else {
//            // wxHandler.setRefreshTokenAvailable(false);
//            // }
//
//        }

        // isWxInstall = wxHandler.isClientInstalled();
    }

    /**
     * ??????????????????
     */
    private void getMineLike() {
        new SAsyncTask<Void, Void, String>((FragmentActivity) LoginActivity.this, R.string.wait) {

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

    private void customDialog(final Activity activity) {
        AlertDialog.Builder builder = new Builder(activity);
        // ???????????????????????????
        View view = View.inflate(activity, R.layout.dialog_huodong_finish, null);
        TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
        tv_des.setText("??????????????????????????????????????????????????????????????????????????????????????????");
//        dialog.setCancelable(false);
        Button ok = (Button) view.findViewById(R.id.ok);
        ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setVisibility(View.GONE);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // ???????????????????????????
                dialog.dismiss();


                activity.finish();
                // AppManager.getAppManager().finishAllActivity();

            }
        });

        ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ToastUtil.showShortText(activity, "????????????????????????????????????,?????????~");
                checkVersion2();
                dialog.dismiss();
//				activity.finish();
                // AppManager.getAppManager().finishAllActivity();
            }
        });

        builder.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                // ????????????
                Intent intent2 = new Intent((Activity) instances, MainMenuActivity.class);
                intent2.putExtra("toHome", "toHome");
                instances.startActivity(intent2);
                ((Activity) instances).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                activity.finish();

            }
        });

        dialog = builder.create();
        dialog.setView(view, 0, 0, 0, 0);
        dialog.setCanceledOnTouchOutside(false);

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ????????????password
     */
    private void getHuanXinPassword(final UserInfo user, final boolean bindPhoneFlag, final boolean isQudao) {
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) LoginActivity.this, R.string.wait) {

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
                        PublicUtil.registerHuanxinForLogin(LoginActivity.this, user, "" + MD5Tools.MD5(code),
                                bindPhoneFlag, isQudao);
                        SharedPreferencesUtil.saveStringData(context, Pref.pd, MD5Tools.MD5(code));
                    } else {
                        PublicUtil.registerHuanxinForLogin(LoginActivity.this, user, "" + code, bindPhoneFlag, isQudao);
                        SharedPreferencesUtil.saveStringData(context, Pref.pd, code);
                    }

                    // context.getSharedPreferences(Pref.pd,
                    // Context.MODE_PRIVATE).edit().putString(Pref.pd, code)
                    // .commit();
                }
            }

        }.execute();
    }


    public static final String path = "https://yssj668.b0.upaiyun.com/down/YJApp.apk";

    /**
     * ??????B??? ????????????????????? ?????????????????????
     *
     * @date 2017???4???28?????????10:52:05
     */
    private void checkVersion2() {

        ApkDownloadManager UpgradeApk = new ApkDownloadManager((FragmentActivity) instances);
        UpgradeApk.downloadUpgradeApk(path);

        String imei = CheckStrUtil.getImei(instances);
        if (imei != null && ComModel2.flag == 0) {
            new Thread() {
                public void run() {

                    try {
                        Thread.sleep(5000);// 5???
                    } catch (InterruptedException e) {
                        // block
                        e.printStackTrace();
                    }
                    // mContext.sendBroadcast(new
                    // Intent(TaskReceiver.newMemberTask_1));
                    handlerCheckVersion.sendEmptyMessage(0);
                }

                ;
            }.start();

        } else if (YJApplication.instance.isLoginSucess() == false) {
            new Thread() {
                public void run() {

                    try {
                        Thread.sleep(5000);// 5???
                    } catch (InterruptedException e) {
                        // block
                        e.printStackTrace();
                    }
                    // mContext.sendBroadcast(new
                    // Intent(TaskReceiver.newMemberTask_1));
                    handlerCheckVersion.sendEmptyMessage(1);
                }

                ;
            }.start();
        } else {
            UserInfo userInfo = YCache.getCacheUserSafe(instances);
            if (null == userInfo) {
                return;
            }
            if (null == userInfo.getHobby() || userInfo.getHobby().equals("0")) {

                new Thread() {
                    public void run() {

                        try {
                            Thread.sleep(5000);// 5???
                        } catch (InterruptedException e) {
                            // catch block
                            e.printStackTrace();
                        }
                        // mContext.sendBroadcast(new
                        // Intent(TaskReceiver.newMemberTask_1));
                        handlerCheckVersion.sendEmptyMessage(3);
                    }

                    ;
                }.start();

            } else {
                new Thread() {
                    public void run() {

                        try {
                            Thread.sleep(5000);// 30???
                        } catch (InterruptedException e) {
                            // catch block
                            e.printStackTrace();
                        }
                        // mContext.sendBroadcast(new
                        // Intent(TaskReceiver.newMemberTask_1));
                        handlerCheckVersion.sendEmptyMessage(2);
                    }

                    ;
                }.start();
            }
        }

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);


    }

    private int isPause = 0;
    private Boolean isShow = false;
    private Handler handlerCheckVersion = new Handler() {
        public void handleMessage(android.os.Message msg) {

            if (isPause == 1) {
                return;
            }
            switch (msg.what) {
                case 0: {
                    if (isShow) {
                        return;
                    }
                    if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == instances
                            .getSharedPreferences("shareApp", Context.MODE_PRIVATE).getInt("day", 0)) {
                        return;
                    }
                    NewPDialog dialog = new NewPDialog(instances, R.layout.task_dialog1);
                    dialog.setF(new NewPDialog.FinishLintener() {

                        @Override
                        public void onFinishClickLintener() {
                            isShow = false;
                            instances.getSharedPreferences("shareApp", Context.MODE_PRIVATE).edit()
                                    .putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
                        }
                    });
                    dialog.setL(new NewPDialog.TaskLintener() {

                        @Override
                        public void onOKClickLintener() {
                        }

                        @Override
                        public void onShouZhiClickLintener() {

                        }
                    });
                    // dialog.show();
                    // isShow = true;
                }
                break;
                case 1: {

                    {
                        if (isShow) {
                            return;
                        }
                        if (!YJApplication.instance.isLoginSucess()) {
                            return;
                        }
                        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == instances
                                .getSharedPreferences("EverydayTaskMondayFriday", Context.MODE_PRIVATE).getInt("day", 0)) {
                            return;
                        }

                        UserInfo userInfo;
                        userInfo = YCache.getCacheUserSafe(instances);
                        if (null == userInfo) {
                            return;
                        }
                        if (null == userInfo.getHobby() || userInfo.getHobby().equals("0")) {
                            return;
                        }

                        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                        if (day == 3) {
                        } else if (day == 2) {
                        }
                    }
                }
                case 2: {

                }
                break;
                case 3: {
                    if (isShow) {
                        return;
                    }
//				isNewShop = 2;
                    if (!YJApplication.instance.isLoginSucess()) {
                        return;
                    }
                    if (instances.getSharedPreferences("dian", Context.MODE_PRIVATE).getInt("dian", 0) == Calendar
                            .getInstance().get(Calendar.DAY_OF_MONTH)) {
                        return;
                    }
                    UserInfo userInfo = YCache.getCacheUserSafe(instances);
                    if (null == userInfo) {
                        return;
                    }
                }
                break;
                case 6: {

                    if (instances.getSharedPreferences("tocao_isupdate", Context.MODE_PRIVATE).getBoolean("tocao_isupdate",
                            false)) {
                        return;
                    }
                    if (instances.getSharedPreferences("tocao_isshow", Context.MODE_PRIVATE).getBoolean("tocao_isshow",
                            false)) {
                        return;
                    }
                    if (isPause == 1) {
                        return;
                    }
                    if (isShow) {
                        return;
                    }
                    final NewPDialog mDialog = new NewPDialog(instances, R.layout.task_dialog9);

                    mDialog.setL(new NewPDialog.TaskLintener() {

                        @Override
                        public void onOKClickLintener() {
                            // ??????????????????
                            isShow = false;

                            Intent intent = new Intent();

                            intent.setClass(instances, FeedBackActivity.class);

                            instances.startActivity(intent);

                        }

                        @Override
                        public void onShouZhiClickLintener() {

                        }

                    });
                    mDialog.setF(new NewPDialog.FinishLintener() {

                        @Override
                        public void onFinishClickLintener() {
                            isShow = false;
                        }
                    });
                    mDialog.show();
                    instances.getSharedPreferences("tocao_isshow", Context.MODE_PRIVATE).edit()
                            .putBoolean("tocao_isshow", true).commit();
                    isShow = true;

                }
                break;
                case 4: {
                    if (isShow) {
                        return;
                    }
                }
                break;
                default:
                    break;
            }

        }

        ;
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//????????????
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(WeiXin weiXin) {
        Log.i("ansen", "??????eventbus?????? type:" + weiXin.getType());
        if (weiXin.getType() == 1) {//??????
            getAccessToken(weiXin.getCode());
        } else if (weiXin.getType() == 2) {//??????
            switch (weiXin.getErrCode()) {
                case BaseResp.ErrCode.ERR_OK:
                    Log.i("ansen", "??????????????????.....");
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://????????????
                    Log.i("ansen", "??????????????????.....");
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://???????????????
                    Log.i("ansen", "?????????????????????.....");
                    break;
            }
        } else if (weiXin.getType() == 3) {//????????????
            if (weiXin.getErrCode() == BaseResp.ErrCode.ERR_OK) {//??????
                Log.i("ansen", "??????????????????.....");
            }
        }
    }

    public void getAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + WxPayUtil.APP_ID + "&secret=" + WxPayUtil.APP_SECRET +
                "&code=" + code + "&grant_type=authorization_code";


        HTTPCaller.getInstance().get(WeiXinToken.class, url, null, new RequestDataCallback<WeiXinToken>() {
            @Override
            public void dataCallback(WeiXinToken weiXinToken) {
                if (weiXinToken.getErrcode() == 0) {//????????????
                    getWeiXinUserInfo(weiXinToken);
                } else {//????????????
                    loading.dismiss();
                    showToast(weiXinToken.getErrmsg());
                }

            }
        });


    }

    private void getWeiXinUserInfo(final WeiXinToken weiXinToken) {

        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" +
                weiXinToken.getAccess_token() + "&openid=" + weiXinToken.getOpenid();

        //??????????????????
        HTTPCaller.getInstance().get(WeiXinInfo.class, url, null, new RequestDataCallback<WeiXinInfo>() {
            @Override
            public void dataCallback(WeiXinInfo weiXinInfo) {


                user = new UserIfoThird();
                // ??????uid
                user.setUnionid(weiXinToken.getUnionid());
                user.setToken(weiXinToken.getAccess_token());
                user.setOpenid(weiXinToken.getOpenid());

                user.setNickname(weiXinInfo.getNickname());
                user.setPic(weiXinInfo.getHeadimgurl());
                user.setUsertype(2 + 3);
                sex = weiXinInfo.getSex();//0?????????1??????2???


//                if (GuideActivity.needFengKong) {
                    LongThirdNeedBindPhone(user, null, sex);

//                } else {
//                    LongThird(user, null, sex);
//
//                }



            }
        });
    }


}
