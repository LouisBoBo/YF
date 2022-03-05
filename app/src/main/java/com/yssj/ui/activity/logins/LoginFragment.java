package com.yssj.ui.activity.logins;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.android.volley.Response;
//import com.android.volley.VolleyError;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.utils.DeviceConfig;
import com.yssj.Constants;
import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LineEditText;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;
import com.yssj.network.YException;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.MineLikeActivity;
import com.yssj.ui.activity.SetRefereeActivity;
import com.yssj.ui.activity.setting.SettingCommonFragmentActivity;
import com.yssj.utils.CheckStrUtil;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.GetShopCart;
import com.yssj.utils.GetUserABClass;
import com.yssj.utils.LimitDoubleClicked;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.ModUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
//import com.yssj.utils.VolleyListenerInterface;
//import com.yssj.utils.VolleyRequestUtil;
import com.yssj.utils.YCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****
 * 登陆
 *
 * @author Administrator
 *
 */
public class LoginFragment extends Fragment implements OnClickListener {

    private static final String TITLE = "login";
    private LineEditText et_account;
    private LineEditText et_pwd;
    private ImageView et_account_xx, et_pwd_xx;
    private ImageView yanjingIv;
    private boolean isChecked;
    private TextView tv_ln;
    private YJApplication appctx;
    private LinearLayout ll_qudao;
    public static LoginActivity instance;

    private boolean wxInstall;

    private String deviceToken;
    public static boolean needCheckDuobaoZhongjiang = false;

    // private boolean isOldLogin;//老用户登录
    public static LoginFragment newInstance(String title) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = (LoginActivity) getActivity();
        appctx = (YJApplication) getActivity().getApplicationContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v;

        wxInstall = WXcheckUtil.isWeChatAppInstalled(getActivity());

        if (LoginActivity.noUserRegist) {

            if (LoginActivity.sanFangShow) { // 是渠道但是显示正常登录
                v = inflater.inflate(R.layout.fragment_login, container, false);
                v.findViewById(R.id.tv_third_rl).setVisibility(View.VISIBLE);
            } else { // 是渠道----显示根据判断
                if (!LoginActivity.isOldLogin) {
                    v = inflater.inflate(R.layout.activity_login_qudao, container, false);
                } else {

                    if (LoginActivity.sanFangShow) {
                        v = inflater.inflate(R.layout.fragment_login, container, false);
                        // 老用户登录
                        v.findViewById(R.id.tv_third_rl).setVisibility(View.VISIBLE);
                    } else {

                        v = inflater.inflate(R.layout.activity_login_qudao, container, false);
                        // 老用户登录
                        // v.findViewById(R.id.tv_third_rl).setVisibility(View.VISIBLE);
                    }

                }
            }

        } else {
            v = inflater.inflate(R.layout.fragment_login, container, false);
        }

        initViews(v);

        return v;
    }

    private void getPayRedBackage() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!SharedPreferencesUtil.getBooleanData(instance, Pref.ISMADMONDAY, false)) {
                        //当天是否有未支付订单
                        boolean hasNoPayOrder = ModQingfeng.getNotFUoder(instance);
                        if (hasNoPayOrder) {//有未支付订单
                            HashMap<String, String> map = ComModel2.getYiDouHalve(instance);
                            if (null == map || map.size() == 0 || "0".equals(map.get("end_date"))) {//说明没有参与过红包的任务-----用户走完流程，支持成功依旧会返回map
                                YJApplication.startFukuanYndao();
                            }
                        } else {//没有未支付订单---无需任何操作----不考虑任何情况，只要没有未付款订单就不用引导红包,过期时间直接存0

                            SharedPreferencesUtil.saveStringData(instance, YConstance.Pref.YIDOU_HALVE_END_TIMES, "0");
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void setThirdClickListener(FragmentActivity activity) {
        this.onThirdClickListener = (OnThirdClickListener) activity;
    }

    private OnThirdClickListener onThirdClickListener;

    public interface OnThirdClickListener {
        void qqClick(View v);

        void wxClick(View v);

        void sinaClick(View v);
    }

    private void initViews(View v) {

        if (!LoginActivity.noUserRegist || LoginActivity.isOldLogin || LoginActivity.sanFangShow) {

            instance.findViewById(R.id.tv_chreg).setVisibility(View.INVISIBLE);


            try {
                v.findViewById(R.id.login_wx_btn).setOnClickListener(this);
            } catch (Exception e) {
                // TODO: handle exception
            }


            if (LoginActivity.sanFangShow) {

                TextView tv_fpwd = (TextView) v.findViewById(R.id.tv_fpwd);
                tv_fpwd.setOnClickListener(this);
                et_account = (LineEditText) v.findViewById(R.id.et_account);
                et_pwd = (LineEditText) v.findViewById(R.id.et_pwd);
                et_account_xx = (ImageView) v.findViewById(R.id.et_account_xx);
                et_pwd_xx = (ImageView) v.findViewById(R.id.et_pwd_xx);
                tv_ln = (TextView) v.findViewById(R.id.tv_ln);
                yanjingIv = (ImageView) v.findViewById(R.id.et_pwd_yanjing);
                yanjingIv.setOnClickListener(this);
                tv_ln.setOnClickListener(this);
                v.findViewById(R.id.loginn).setBackgroundColor(Color.WHITE);
                v.findViewById(R.id.img_wx).setOnClickListener(this);
                v.findViewById(R.id.img_qq).setOnClickListener(this);
                v.findViewById(R.id.img_wb).setOnClickListener(this);
                et_account_xx.setOnClickListener(this);
                et_pwd_xx.setOnClickListener(this);

                try {
                    if (!DeviceConfig.isAppInstalled("com.tencent.mobileqq", getActivity())) {
                        v.findViewById(R.id.img_qq_ll).setVisibility(View.GONE);
                    }

                    if (!WXcheckUtil.isWeChatAppInstalled(getActivity())) {
                        v.findViewById(R.id.img_wx_ll).setVisibility(View.GONE);
                        v.findViewById(R.id.tv_third_rl).setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }


                et_account.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String et_account_text = et_account.getText().toString();
                        if (et_account_text.length() > 0) {
                            et_account_xx.setVisibility(View.VISIBLE);
                        } else {
                            et_account_xx.setVisibility(View.GONE);
                        }
                        if (et_account_text.length() > 0 && et_pwd.getText().toString().length() > 0) {
                            tv_ln.setBackgroundResource(R.drawable.btn_back_red);
                        } else {
                            tv_ln.setBackgroundResource(R.drawable.btn_back);
                        }

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub

                    }
                });
                et_pwd.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String et_pwd_text = et_pwd.getText().toString();
                        if (et_pwd_text.length() > 0) {
                            et_pwd_xx.setVisibility(View.VISIBLE);
                        } else {
                            et_pwd_xx.setVisibility(View.GONE);
                        }
                        if (et_pwd_text.length() > 0 && et_account.getText().toString().length() > 0) {
                            tv_ln.setBackgroundResource(R.drawable.btn_back_red);
                        } else {
                            tv_ln.setBackgroundResource(R.drawable.btn_back);
                        }

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub

                    }
                });

            }

        } else {

            if (!LoginActivity.sanFangShow) {
                v.findViewById(R.id.login_wx_btn).setOnClickListener(this);
            }

        }

        // loginPhone(null, "15139688628", "123");
        // loginPhone(null, "13797802070", "654321");
        // loginPhone(null, "15675365752", "123456");

        // loginPhone(null, "15813741828", "123456");

        // loginPhone(null, "18829575129", "llllll");

    }

    @Override
    public void onClick(View v) {
        if (LimitDoubleClicked.isFastDoubleClick())
            return;
        switch (v.getId()) {
            case R.id.img_wx:
                if(!wxInstall){
                    ToastUtil.showShortText2("您还未安装微信哦！");
                    return;
                }
                onThirdClickListener.wxClick(v);
                break;
            case R.id.img_qq:
                onThirdClickListener.qqClick(v);
                break;
            case R.id.img_wb:
                onThirdClickListener.sinaClick(v);
                break;

            case R.id.login_wx_btn:

                if(!wxInstall){
                    ToastUtil.showShortText2("您还未安装微信哦！");
                    return;
                }
                onThirdClickListener.wxClick(v);
                break;

            case R.id.tv_fpwd:// 忘记密码
                Intent intent = new Intent(getActivity(), FillCodeActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                break;
            case R.id.et_pwd_yanjing:
                isChecked = !isChecked;
                if (isChecked) {
                    yanjingIv.setImageResource(R.drawable.yanjing_icon_selected);
                    et_pwd.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    yanjingIv.setImageResource(R.drawable.yanjing_icon_default);
                    et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.tv_ln:// 登陆
                login(null);

                break;
            case R.id.et_account_xx:
                et_account.getText().clear();
                break;
            case R.id.et_pwd_xx:
                et_pwd.getText().clear();
                break;

            default:
                break;
        }

    }

    /***
     * 登陆
     *
     * @param v
     */
    private void login(View v) {
        String str_account = et_account.getText().toString().trim();
        // if (!setETNull(et_account, str_account))
        // return;

        if (str_account.length() == 0) {
            ToastUtil.showShortText(getActivity(), "请输入手机号或邮箱");
            return;
        }

        String pwd = et_pwd.getText().toString().trim();
        if (et_pwd.length() == 0) {
            ToastUtil.showShortText(getActivity(), "请输入密码");
            return;
        }
//		if(GuideActivity.needShouquan) {
//			//每次登陆都强制微信授权 清除上次微信授权信息
//			final UMSocialService mController =
//					UMServiceFactory.getUMSocialService("");
//			UMWXHandler wxHandler = new UMWXHandler(getActivity(),
//					WxPayUtil.APP_ID, WxPayUtil.APP_SECRET);
//			wxHandler.addToSocialSDK();
//			wxHandler.setRefreshTokenAvailable(false);
//			mController.deleteOauth(getActivity(), null, null);
//		}
        if (StringUtils.isPhoneNumberValid(str_account) && !str_account.contains("@")) {// 手机账号登陆
            loginPhone(v, str_account, pwd);

        } else if (StringUtils.isEmail(str_account)) {// 邮箱账号登陆
            loginEmail(v, str_account, pwd);
//            LoginEmailVolley(str_account, pwd);


        } else {
            // Toast.makeText(getActivity(), "你输入的账号格式不正确，请重新输入",
            // Toast.LENGTH_SHORT).show();

            ToastUtil.showShortText(getActivity(), "你输入的账号格式不正确，请重新输入");
        }

    }

//    private void LoginEmailVolley(String str_account, String pwd) {
//
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("account",str_account);
//        map.put("pwd",MD5Tools.MD5(pwd));
//        map.put("device","1");
//        map.put("imei",CheckStrUtil.getImei(instance));
//        map.put("mac",CheckStrUtil.getMac(instance));
//
//
//        VolleyListenerInterface Listener = new VolleyListenerInterface(instance,
//
//                new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String response) {
//
//                        ToastUtil.showShortText(instance,"onResponse-----"+response);
//
//                    }
//                },
//
//                new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        ToastUtil.showShortText(instance,"onErrorResponse"+error);
//
//                    }
//                }
//
//
//        ) {
//
//            @Override
//            public void onMySuccess(String result) {
//
//                ToastUtil.showShortText(instance,"onMySuccess"+result);
//
//            }
//
//            @Override
//            public void onMyError(VolleyError error) {
//                ToastUtil.showShortText(instance,"VolleyError"+error);
//            }
//        };
//
//        try {
//
//            VolleyRequestUtil.RequestPost(getActivity(), YUrl.ACCOUNT_LOGIN, "LoginEmail", map, Listener);
//        } catch (Exception e) {
//
//            ToastUtil.showShortText(instance,"请求异常"+e.getMessage());
//            e.printStackTrace();
//        }
//
//
//    }

    /****
     * 手机登陆
     *
     * @param v
     * @param str_account
     * @param pwd
     */
    private void loginPhone(View v, final String str_account, final String pwd) {
        new SAsyncTask<String, Void, UserInfo>(getActivity(), v, R.string.wait) {


            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                LoadingDialog.show((FragmentActivity) instance);
            }


            @Override
            protected UserInfo doInBackground(FragmentActivity context, String... params) throws Exception {
                UserInfo user = ComModel.loginPhone(context, params[0], params[1], deviceToken);

                return user;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, UserInfo user, Exception e) {

                if (e != null) {// 登陆异常

                    if (e instanceof YException && ((YException) e).getErrorCode() == 4) {
                        // // 帐号未验证。跳转到验证页面
                    } else if (e instanceof YException && ((YException) e).getErrorCode() == 2) {
                    } else if (e instanceof YException && ((YException) e).getErrorCode() == 1051) {
                        Intent intent = new Intent(getActivity(), SetRefereeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userinfo", YCache.getCacheUserSafe(context));
                        intent.putExtras(bundle);
                        startActivity(intent);

                    } else if (e instanceof YException && ((YException) e).getErrorCode() == 101) {

                        Intent intent = new Intent(getActivity(), MineLikeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userinfo", YCache.getCacheUserSafe(context));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } else {// 登陆成功
                    SharedPreferencesUtil.saveBooleanData(context, "isrelogin", true);
                    YJApplication.isLogined = true;
                    if (SettingCommonFragmentActivity.instanes != null) {

                        SettingCommonFragmentActivity.instanes.finish();
                    }
                    needCheckDuobaoZhongjiang = true;
                    ModUtil.hasMond(context,false);

                    getPayRedBackage();

                    //
                    // SharedPreferencesUtil.saveBooleanData(context,
                    // Pref.ISSHOWEDSIGNUP, false);
                    //
                    // if (LoginActivity.isSign) {
                    // CenterToast.centerToast(context, "签到表已经更新啦~");
                    // LoginActivity.isSign = false ;
                    // SharedPreferencesUtil.saveBooleanData(context,
                    // Pref.ISSHOWEDSIGNUP, false);
                    // }else{
                    // SharedPreferencesUtil.saveBooleanData(context,
                    // Pref.ISSHOWEDSIGNUP, true);
                    // }
                    //
//					if(!GuideActivity.needShouquan) {
                    ToastUtil.showShortText(getActivity(), "登录成功");
//					}
                    // Toast.makeText(context, "登录成功",
                    // Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtil.saveStringData(context, "HUNXIAO", 1 + "");
                    // loginHuanxin();
                    context.getSharedPreferences(Pref.pd, Context.MODE_PRIVATE).edit()
                            .putString(Pref.pd, MD5Tools.MD5(pwd)).commit();
                    SharedPreferencesUtil.saveBooleanData(context, "ISCHUCHIDNEGLU", true); // 标志改为已登录

                    // 获取购物车数据并添加到购物车数据库
                    GetShopCart.querShopCart(getActivity(), 1);
//					if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
//						// 获取金币金券相关数据
//						GetJinBiJinQuanUtils.getJinBi(context);
//						GetJinBiJinQuanUtils.getJinQuan(context);
//					}


                    // 登陆环信
                    // loginHxServer(user, pwd);
                    /**
                     * Intent intent = new Intent(getActivity(),
                     * MainMenuActivity.class); startActivity(intent);
                     */

                    // 后台设置初次登录是否跳转到微信授权（3.3.8）

                    // getWXshouquan(context, getActivity());

                    SharedPreferencesUtil.saveStringData(context, Pref.pd, MD5Tools.MD5(pwd));
                    YCache.setCacheUser(context, user);// 登陆成功后设置缓存
                    ComModel.saveLoginFlag(context);
                    context.setResult(Activity.RESULT_OK);
                    submitIMEI();
                    submitMAC();
                    if (!GuideActivity.needShouquan) {
                        getWXshouquan(context);
                    }
                    // 添加我的喜好
                    getMineLike();
                    getIntegral();


                    boolean unionidIsEmpty = TextUtils.isEmpty(user.getUuid()) || "null".equals(user.getUuid());
                    if (GuideActivity.needShouquan && unionidIsEmpty) {
                        // 微信授权拿到openID  开关打开 并且用户没有授权过
                        openId(SHARE_MEDIA.WEIXIN, context, "登录成功");
                    }

                    SharedPreferencesUtil.saveBooleanData(context, "isLoginLogin", true);
                    LogYiFu.e("zzqyi", "shouji" + LoginActivity.mShopCart);

                    if (LoginActivity.isSign) {

//						Intent intent = new Intent(context, MainMenuActivity.class);
//						intent.putExtra("Exit30", true);
//						context.startActivity(intent);

                        // 跳至赚钱
//                        SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
//                        context.startActivity(new Intent(context, CommonActivity.class));


                        // 跳至首页
                        Intent intent2 = new Intent((Activity) context, MainMenuActivity.class);
                        intent2.putExtra("toHome", "toHome");
                        context.startActivity(intent2);
                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                    } else if ("shopcart".equals(LoginActivity.mShopCart)) {
                        Intent intent2 = new Intent(getActivity(), MainMenuActivity.class);
                        intent2.putExtra("shopcart", "shopcart");
                        startActivity(intent2);
                    }

//					if(!GuideActivity.needShouquan) {
//                    GetUserABClass.getUserABclass(context);
//					}


//					if (null != OldUserLoginActivity.instances) {
//						OldUserLoginActivity.instances.finish();
//					}
//
//
//					if (null != LoginActivity.instances) {
//						LoginActivity.instances.finish();
//					}

                    //					getActivity().finish();
                }

            }

            ;

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;
        }.execute(str_account, pwd);

    }

    protected void getWXshouquan(final Context context) {

        // TODO Auto-generated method stub
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    String data = ComModel2.getFirstShouquan(context);
                    LogYiFu.e("getWXshouquan", data);
                    if (data.equals("1")) {

                        // 微信授权拿到openID
                        openId(SHARE_MEDIA.WEIXIN, context, "登录成功");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // new SAsyncTask<Integer, Void, String>((FragmentActivity) context,
        // R.string.wait) {
        //
        // @Override
        // protected boolean isHandleException() {
        // // TODO Auto-generated method stub
        // return true;
        // }
        //
        // @Override
        // protected String doInBackground(FragmentActivity context, Integer...
        // params) throws Exception {
        // // TODO Auto-generated method stub
        // return ComModel2.getFirstShouquan(context);
        // }
        //
        // @Override
        // protected void onPostExecute(FragmentActivity context, String result,
        // Exception e) {
        // super.onPostExecute(context, result, e);
        //
        // if (e == null) {
        //
        // LogYiFu.e("getWXshouquan", result);
        //
        //
        // // 0：手机 -----------无需提交微信OPENID（无需微信授权）
        // // 1：微信------------需要提交微信OPENID（需要微信授权）
        // if (result.equals("1")) {
        //
        // // 微信授权拿到openID
        // openId(SHARE_MEDIA.WEIXIN, activity);
        //
        // }
        //
        // }
        //
        // }
        //
        // }.execute();

    }

    protected void openId(SHARE_MEDIA weixin, final Context context, final String loginMessage) {

        final UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);

        mController.doOauthVerify(context, weixin, new UMAuthListener() {

            @Override
            public void onStart(SHARE_MEDIA platform) {

            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {

                final String openid = value.getString("openid");


                mController.getPlatformInfo(context, platform, new UMDataListener() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onComplete(int status, Map<String, Object> info) {
                        if (info != null) {

//							//需要强制微信授权  这一步就在微信授权之后 否则是在登录成功之后
//							if(GuideActivity.needShouquan) {
//								//改为微信授权成功后再弹框
//								ToastUtil.showShortText(getActivity(), loginMessage);
//								GetUserABClass.getUserABclass(context);
//							}

                            final String unionid = info.get("unionid").toString();
                            if (TextUtils.isEmpty(unionid)) {
                                return;
                            }

                            new Thread() {
                                public void run() {
                                    try {
                                        ComModel2.updateUuid(context, unionid,openid);
                                    } catch (Exception e) {
//                                        ToastUtil.showShortText(context, e.getMessage());
                                    }
                                }

                                ;
                            }.start();
                        }
                    }
                });
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
//				exitLogin();
            }
        });

    }

    protected void submitMAC() {
        // 上传mac，如果没有就上传
        final String mac = CheckStrUtil.getMac(getActivity());

        String Fmac = YCache.getCacheUser(getActivity()).getMac();

        Boolean b = (Fmac == null || Fmac.equals("null") || Fmac.equals("")) && (mac != null);

        if (b) {

            new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) getActivity(), R.string.wait) {

                @Override
                protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                    return ComModel2.updateMac(context, mac);
                }

                @Override
                protected boolean isHandleException() {
                    return true;
                }

                @Override
                protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                    super.onPostExecute(context, result, e);
                    if (null == e) {
                        if (result != null) {

                            // 提交mac成功
                            LogYiFu.e("网卡号", mac);
                        }
                    }
                }

            }.execute();

        }

    }

    private void submitIMEI() {
        // 上传IEMI码(如果没有) -------------

        final String imei = CheckStrUtil.getImei(getActivity());

        String sss = YCache.getCacheUser(getActivity()).getImei();

        if ((sss.equals("null") || sss == null || sss.equals("")) && (sss != null)) {

            new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) getActivity(), R.string.wait) {

                @Override
                protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                    return ComModel2.updateImei(context, imei);
                }

                @Override
                protected boolean isHandleException() {
                    return true;
                }

                @Override
                protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                    super.onPostExecute(context, result, e);
                    if (null == e) {
                        if (result != null) {

                            // 提交IMEI成功
                            LogYiFu.e("串号", imei);
                        }
                    }
                }

            }.execute();

        }
    }

    /***
     * 邮箱登陆
     *
     * @param v
     * @param str_account
     * @param pwd
     */
    private void loginEmail(View v, final String str_account, final String pwd) {
        new SAsyncTask<String, Void, UserInfo>(getActivity(), v, R.string.wait) {


            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                LoadingDialog.show((FragmentActivity) instance);
            }


            @Override
            protected UserInfo doInBackground(FragmentActivity context, String... params) throws Exception {
                UserInfo user = ComModel.loginEmail(context, params[0], params[1], deviceToken);
                /*
                 * HashMap<String, Object>
				 * map=ComModel2.queryMyIntegral(context); List<String>
				 * list=(List<String>) map.get("fulfill");
				 * if(list!=null&&map.get("everyDayFulfill")!=null){
				 * list.addAll((List<String>)map.get("everyDayFulfill")); }
				 * YJApplication.instance.setTaskMap(list);
				 */
                return user;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, UserInfo user, Exception e) {

                if (e != null) {// 登陆异常

                    if (e instanceof YException && ((YException) e).getErrorCode() == 4) {
                        // // 帐号未验证。跳转到验证页面
                    } else if (e instanceof YException && ((YException) e).getErrorCode() == 2) {
                    } else if (e instanceof YException && ((YException) e).getErrorCode() == 101) {
                        Intent intent = new Intent(getActivity(), MineLikeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userinfo", YCache.getCacheUserSafe(context));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } else {// 登陆成功
                    YJApplication.isLogined = true;
                    SharedPreferencesUtil.saveBooleanData(context, "isrelogin", true);
                    if (SettingCommonFragmentActivity.instanes != null) {

                        SettingCommonFragmentActivity.instanes.finish();
                    }

                    needCheckDuobaoZhongjiang = true;

                    ModUtil.hasMond(context,false);


                    getPayRedBackage();

                    // if (LoginActivity.isSign) {
                    // CenterToast.centerToast(context, "签到表已经更新啦~");
                    // LoginActivity.isSign = false ;
                    // SharedPreferencesUtil.saveBooleanData(context,
                    // Pref.ISSHOWEDSIGNUP, false);
                    // }else{
                    // SharedPreferencesUtil.saveBooleanData(context,
                    // Pref.ISSHOWEDSIGNUP, true);
                    // }

                    SharedPreferencesUtil.saveBooleanData(context, Pref.SHOWSIGNUPDATA, true);
//					if(!GuideActivity.needShouquan){
//						ToastUtil.showShortText(getActivity(), "邮箱登录成功");
//					}
                    ToastUtil.showShortText(getActivity(), "邮箱登录成功");

                    SharedPreferencesUtil.saveStringData(context, Pref.pd, MD5Tools.MD5(pwd));

                    SharedPreferencesUtil.saveBooleanData(context, "ISCHUCHIDNEGLU", true); // 标志改为已登录
                    // 为了解决混淆问题 v3.3.1已解决
                    SharedPreferencesUtil.saveStringData(context, "HUNXIAO", 1 + "");
                    // 获取购物车数据并添加到购物车数据库


                    // 登陆环信服务器
                    // loginHxServer(user, pwd);
                    // Intent intent = new Intent(getActivity(),
                    // MainMenuActivity.class);
                    // startActivity(intent);
                    // AppManager.getAppManager().finishAllActivity();
                    // AppManager.getAppManager().finishActivity(getActivity());

                    SharedPreferencesUtil.saveStringData(context, Pref.pd, MD5Tools.MD5(pwd));
                    YCache.setCacheUser(context, user);// 登陆成功后设置缓存
                    ComModel.saveLoginFlag(context);


                    GetShopCart.querShopCart(getActivity(), 1);
//					if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
//						// 获取金币金券相关数据
//						GetJinBiJinQuanUtils.getJinBi(context);
//						GetJinBiJinQuanUtils.getJinQuan(context);
//					}


                    context.setResult(-1);
                    submitMAC();
                    getIntegral();
                    // 获取我的喜好
                    getMineLike();


                    boolean unionidIsEmpty = TextUtils.isEmpty(user.getUuid()) || "null".equals(user.getUuid());
                    if (GuideActivity.needShouquan && unionidIsEmpty) {
                        // 微信授权拿到openID 开关开启 并且 用户没有授权过
                        openId(SHARE_MEDIA.WEIXIN, context, "邮箱登录成功");
                    }

                    SharedPreferencesUtil.saveBooleanData(context, "isLoginLogin", true);

                    if (LoginActivity.isSign) {

//						Intent intent = new Intent(context, MainMenuActivity.class);
//						intent.putExtra("Exit30", true);
//						context.startActivity(intent);

//                        // 跳至赚钱
//                        SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
//                        context.startActivity(new Intent(context, CommonActivity.class));


                        // 跳至赚钱
                        SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
                        context.startActivity(new Intent(context, CommonActivity.class));


                    } else if ("shopcart".equals(LoginActivity.mShopCart)) {
                        Intent intent2 = new Intent(getActivity(), MainMenuActivity.class);
                        intent2.putExtra("shopcart", "shopcart");
                        startActivity(intent2);
                    }

//					if (null != OldUserLoginActivity.instances) {
//						OldUserLoginActivity.instances.finish();
//					}
//					if (null != LoginActivity.instances) {
//						LoginActivity.instances.finish();
//					}
//					if(!GuideActivity.needShouquan){
                    GetUserABClass.getUserABclass(context);
//					}


//					getActivity().finish();
                }
            }

            ;

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;
        }.execute(str_account, pwd);

    }

    private void getIntegral() {
        // TODO Auto-generated method stub
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    HashMap<String, Object> map = ComModel2.queryMyIntegral(getActivity());
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

    /**
     * 获取我的喜好
     */
    private void getMineLike() {
        new SAsyncTask<Void, Void, String>((FragmentActivity) getActivity(), R.string.wait) {

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

    // 登录成功之后登录环信

    // private void loginHuanxin() {
    // if (null != YCache.getCacheUserSafe(getActivity())) {
    //
    // LogYiFu.e("环信000", "登录聊天服务器成功！"+
    // getActivity().getSharedPreferences(Pref.pd,
    // Context.MODE_PRIVATE).getString(Pref.pd, "") +" "+
    // YCache.getCacheUserSafe(getActivity()).getUser_id() + "" + "");
    //
    //
    // loginHxServer(YCache.getCacheUserSafe(getActivity()),
    // getActivity().getSharedPreferences(Pref.pd,
    // Context.MODE_PRIVATE).getString(Pref.pd, ""));
    // }
    // }

    // private void loginHxServer(final UserInfo result, final String password)
    // {
    //
    // LogYiFu.e("getActivity()", getActivity()+"");
    //
    // EMChatManager.getInstance().login( result.getUser_id() + "", password,
    // new EMCallBack() {// 回调
    // @Override
    // public void onSuccess() {
    // getActivity().runOnUiThread(new Runnable() {
    // public void run() {
    // EMGroupManager.getInstance().loadAllGroups();
    // EMChatManager.getInstance().loadAllConversations();
    // Log.d("信信信UIUUUmain", "登录聊天服务器成功！");
    // // 成功之后 退出当前页面
    // }
    // });
    // }
    //
    // @Override
    // public void onProgress(int progress, String status) {
    //
    // }
    //
    // @Override
    // public void onError(int code, String message) {
    // // Log.d("main", "登录聊天服务器失败！");
    //// ToastUtil.showShortText(getActivity(), "登陆聊天服务器失败！");
    //
    // LogYiFu.e("环信000", "失败"+ message + password +" "+result.getUser_id() +
    // "");
    //
    // if (YJApplication.instance.isLoginSucess()) {
    // loginHxServer(result, password);
    // }
    // }
    // });
    // }


}