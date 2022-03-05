package com.yssj.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

import com.yssj.Json;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.entity.CheckPwdInfo;
import com.yssj.entity.Help;
import com.yssj.entity.IntegralShop;
import com.yssj.entity.MyBankCard;
import com.yssj.entity.OrderShop;
import com.yssj.entity.QueryEmailInfo;
import com.yssj.entity.QueryPhoneInfo;
import com.yssj.entity.RemainShipInfo;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ShareShop;
import com.yssj.entity.Shop;
import com.yssj.entity.ShopCart;
import com.yssj.entity.ShopComment;
import com.yssj.entity.StockType;
import com.yssj.entity.Store;
import com.yssj.entity.UserInfo;
import com.yssj.eventbus.MessageEvent;
import com.yssj.huanxin.PublicUtil;
import com.yssj.network.YConn;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.dialog.NewSignCommonDiaolg;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.CheckStrUtil;
import com.yssj.utils.DeviceUtils;
import com.yssj.utils.EntityFactory;
import com.yssj.utils.GetJinBiJinQuanUtils;
import com.yssj.utils.JSONUtils;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.StringUtils;
import com.yssj.utils.YCache;

public class ComModel {
    public static final String versionCode = "V1.32";

    /**
     * 邮箱注册
     */
    public static UserInfo sigup4Email(Context context, String email, String pwd, String nick_name, String type,
                                       String deviceToken) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("account", email));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));
        // nameValuePairs.add(new BasicNameValuePair("code", code));
        nameValuePairs.add(new BasicNameValuePair("type", type));
        nameValuePairs.add(new BasicNameValuePair("device", "1"));
//		nameValuePairs.add(new BasicNameValuePair("nickname", nick_name));
        nameValuePairs.add(new BasicNameValuePair("deviceToken", deviceToken));
        nameValuePairs.add(new BasicNameValuePair("imei", CheckStrUtil.getImei(context)));
        nameValuePairs.add(new BasicNameValuePair("mac", CheckStrUtil.getMac(context)));
        nameValuePairs.add(new BasicNameValuePair("user_type", "2"));
        String result = YConn.basedPost(context, YUrl.ACCOUNT_EMAIL_SIGNUP, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");

        JSONObject jsonUser = JSONUtils.getJSONObject(result, Json.userinfo, null);

        if (jsonUser == null) {
            return null;
        }

        UserInfo user = EntityFactory.createYUser(context, result);

        user.setUsertype(-1);
        String logStringoken = JSONUtils.getString(result, Json.token, "");
        EntityFactory.createStore(context, result);// 保存店铺数据
        EntityFactory.saveOrderToken(context, result);// 保存下单令牌
        YCache.setCacheUser(context, user);// 登陆成功后设置缓存
        YCache.cacheUserInfo(context, logStringoken, user);
        // clearLoginFlag(context);
        return user;
    }

    /**
     * 手机注册
     */
    public static UserInfo sigup4Phone(Context context, String phone, String pwd, String code, String nickname,
                                       String type, String deviceToken, String unionid, String openid) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("account", phone));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));
        nameValuePairs.add(new BasicNameValuePair("code", code));
//		nameValuePairs.add(new BasicNameValuePair("nickname", nickname));

        if (!unionid.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("wx_openid", openid));
            nameValuePairs.add(new BasicNameValuePair("unionid", unionid));
        }

        nameValuePairs.add(new BasicNameValuePair("type", type));
        nameValuePairs.add(new BasicNameValuePair("device", "1"));
        nameValuePairs.add(new BasicNameValuePair("deviceToken", deviceToken));
        nameValuePairs.add(new BasicNameValuePair("imei", CheckStrUtil.getImei(context)));
        nameValuePairs.add(new BasicNameValuePair("mac", CheckStrUtil.getMac(context)));
        nameValuePairs.add(new BasicNameValuePair("user_type", "1"));

        String result = YConn.basedPost(context, YUrl.ACCOUNT_SIGNUP, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");

        JSONObject jsonUser = JSONUtils.getJSONObject(result, Json.userinfo, null);

        if (jsonUser == null) {
            return null;
        }

        UserInfo user = EntityFactory.createYUser(context, result);
        EntityFactory.saveOrderToken(context, result);// 保存下单令牌
        user.setUsertype(-1);
        String logStringoken = JSONUtils.getString(result, Json.token, "");
        EntityFactory.createStore(context, result);// 保存店铺数据
        YCache.setCacheUser(context, user);// 登陆成功后设置缓存
        YCache.cacheUserInfo(context, logStringoken, user);
        // clearLoginFlag(context);
        return user;
    }

    /**
     * 发手机验证码接口 type=1 注册 type=2找回密码
     */
    public static ReturnInfo sendPhoneVerifyCode(Context context, String account, int type, String isbin, String vcode)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("phone", account));
        nameValuePairs.add(new BasicNameValuePair("isbin", isbin));
        nameValuePairs.add(new BasicNameValuePair("codetype", String.valueOf(type)));
        nameValuePairs.add(new BasicNameValuePair("vcode", vcode));
        String result = YConn.basedPost(context, YUrl.GET_PHONE_CODE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    public static ReturnInfo sendPhoneVerifyCode(Context context, String account, int type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("phone", account));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("codetype", String.valueOf(type)));
        String result = YConn.basedPost(context, YUrl.GET_PHONE_CODE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * (2017.8.23添加)
     * 获取短信验证码 加字段 vcode（用户输入的从后台获取的图片验证码）
     *
     * @param context
     * @param type
     * @param vcode   图片验证码
     * @return
     * @throws Exception
     */
    public static ReturnInfo sendPhoneVerifyCodeVCode(Context context, String account, int type, String vcode) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("phone", account));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("codetype", String.valueOf(type)));
        nameValuePairs.add(new BasicNameValuePair("vcode", vcode));
        String result = YConn.basedPost(context, YUrl.GET_PHONE_CODE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    public static ReturnInfo sendPhoneVerifyCodeFirst(Context context, String account, int type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("phone", account));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("codetype", String.valueOf(type)));
        nameValuePairs.add(new BasicNameValuePair("merge", "1"));
        String result = YConn.basedPost(context, YUrl.GET_PHONE_CODE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * * (2017.8.23添加)
     * 获取短信验证码 加字段 vcode（用户输入的从后台获取的图片验证码）
     *
     * @param context
     * @param type
     * @param vcode
     * @return
     * @throws Exception
     */
    public static ReturnInfo sendPhoneVerifyCodeFirstVcode(Context context, String account, int type, String vcode) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("phone", account));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("codetype", String.valueOf(type)));
        nameValuePairs.add(new BasicNameValuePair("merge", "1"));
        nameValuePairs.add(new BasicNameValuePair("vcode", vcode));
        String result = YConn.basedPost(context, YUrl.GET_PHONE_CODE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 更换绑定手机
     * 获取短信验证码 加字段 vcode（用户输入的从后台获取的图片验证码）
     */
    public static ReturnInfo sendOldPhoneVerifyCode(Context context, String vcode) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("vcode", vcode));
        String result = YConn.basedPost(context, YUrl.GET_OLD_PHONE_CODE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 发邮箱验证码接口 type=1 注册 type=2找回密码
     */
    public static ReturnInfo sendEmailVerifyCode(Context context, String account, int type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("email", account));
        nameValuePairs.add(new BasicNameValuePair("codetype", String.valueOf(type)));
        String result = YConn.basedPost(context, YUrl.GET_EMAIL_CODE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 手机 忘记密码处重置密码
     */
    public static ReturnInfo ResetPass4ForgetFhone(Context context, String phone, String pwd, String code)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("phone", phone));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));
        nameValuePairs.add(new BasicNameValuePair("code", code));
        String result = YConn.basedPost(context, YUrl.RESET_PASS_4_FORGET_PHONE, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");

        // YCache.cacheUserInfo(context, logStringoken, user);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 邮箱 忘记密码处重置密码
     */
    public static ReturnInfo ResetPass4ForgetEmail(Context context, String email, String pwd, String code)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("user_email", email));
        nameValuePairs.add(new BasicNameValuePair("user_pass", MD5Tools.MD5(pwd)));
        nameValuePairs.add(new BasicNameValuePair("code", code));
        String result = YConn.basedPost(context, YUrl.RESET_PASS_4_FORGET_EMAIL, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");

        // YCache.cacheUserInfo(context, logStringoken, user);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 手机登陆
     *
     * @param context
     *
     *
     * @return
     * @throws Exception
     */
    public static UserInfo loginPhone(Context context, String account, String pwd, String deviceToken)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("account", account));
        nameValuePairs.add(new BasicNameValuePair("device", "1"));
        nameValuePairs.add(new BasicNameValuePair("deviceToken", deviceToken));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));

        nameValuePairs.add(new BasicNameValuePair("imei", CheckStrUtil.getImei(context)));
        nameValuePairs.add(new BasicNameValuePair("mac", CheckStrUtil.getMac(context)));

        String result = YConn.basedPost(context, YUrl.ACCOUNT_LOGIN, nameValuePairs);

        if (result == null || "".equals(result)) {
            return null;
        }


        JSONObject j = new JSONObject(result);
        if (!j.has("h5money") || null == j.getString("h5money") || j.getString("h5money").equals("null")
                || j.getString("h5money").equals("")) {
            SignFragment.h5Moneny = 0;
        } else {
            try {
                SignFragment.h5Moneny = Double.parseDouble(j.optString("h5money"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        UserInfo user = EntityFactory.createYUser(context, result);
        EntityFactory.createStore(context, result);// 保存店铺数据
        EntityFactory.saveOrderToken(context, result);// 保存下单令牌
        user.setUsertype(-1);
        String logStringoken = JSONUtils.getString(result, Json.token, "");
        int userReviewers = j.optInt("reviewers", 0);
        user.setReviewers(userReviewers);
        YCache.setCacheUser(context, user);// 登陆成功后设置缓存
        YCache.cacheUserInfo(context, logStringoken, user);
        saveLoginFlag(context);
        return user;
    }

    public static void saveLoginFlag(Context mContext) {
        if (YJApplication.instance.isLoginSucess()) {
            return;
        }
        mContext.getSharedPreferences(Pref.isLoginFlag, 0).edit().putBoolean(Pref.isLoginFlag, true).commit();

        YJApplication.instance.setLoginSucess(true);

//        EventBus.getDefault().post(new MessageEvent("登录成功"));

//		PublicUtil.initPinTuan(mContext);


//
//		GetJinBiJinQuanUtils.getJinBi(mContext);
//		GetJinBiJinQuanUtils.getJinQuan(mContext);
    }

    public static void clearLoginFlag(Context mContext) {
        mContext.getSharedPreferences(Pref.isLoginFlag, 0).edit().putBoolean(Pref.isLoginFlag, false).commit();
        YJApplication.instance.setLoginSucess(false);
        YJApplication.isLogined = false;
//        EventBus.getDefault().post(new MessageEvent("退出登录成功"));

    }

    /****
     * 邮箱
     *
     * @param context
     *
     *
     * @return
     * @throws Exception
     */
    public static UserInfo loginEmail(Context context, String account, String pwd, String deviceToken)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("account", account));
        nameValuePairs.add(new BasicNameValuePair("device", "1"));
        nameValuePairs.add(new BasicNameValuePair("deviceToken", deviceToken));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));

        nameValuePairs.add(new BasicNameValuePair("imei", CheckStrUtil.getImei(context)));
        nameValuePairs.add(new BasicNameValuePair("mac", CheckStrUtil.getMac(context)));

        String result = YConn.basedPost(context, YUrl.ACCOUNT_LOGIN, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        JSONObject j = new JSONObject(result);
        if (!j.has("h5money") || null == j.getString("h5money") || j.getString("h5money").equals("null")
                || j.getString("h5money").equals("")) {
        } else {
            try {
                SignFragment.h5Moneny = Double.parseDouble(j.optString("h5money"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        UserInfo user = EntityFactory.createYUser(context, result);
        EntityFactory.createStore(context, result);// 保存店铺数据
        EntityFactory.saveOrderToken(context, result);// 保存下单令牌
        user.setUsertype(-1);
        String logStringoken = JSONUtils.getString(result, Json.token, "");
        YCache.setCacheUser(context, user);// 登陆成功后设置缓存
        YCache.cacheUserInfo(context, logStringoken, user);
        saveLoginFlag(context);
        return user;
    }

    /****
     * 第三方登陆
     *
     * @param context
     *
     * @return
     */
    public static UserInfo LoginThird(Context context, String wx_openid, String unionid, String nickename, String pic,
                                      String token, String usertype, String deviceToken, String gender) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("openid", wx_openid));
        if (null == unionid || unionid.equals("null") || "".equals(unionid)) {

        } else {
            nameValuePairs.add(new BasicNameValuePair("unionid", unionid));
        }



//        if (!StringUtils.isEmpty(((FragmentActivity) context).getIntent().getStringExtra("reviewers"))){
//            nameValuePairs.add(new BasicNameValuePair("reviewers", ((FragmentActivity) context).getIntent().getStringExtra("reviewers")));
//        }


        nameValuePairs.add(new BasicNameValuePair("reviewers", ((FragmentActivity) context).getIntent().getIntExtra("reviewers",0)+""));

        nameValuePairs.add(new BasicNameValuePair("nickname", nickename));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));
        nameValuePairs.add(new BasicNameValuePair("gender", gender));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("usertype", usertype));
        nameValuePairs.add(new BasicNameValuePair("device", "1"));
        nameValuePairs.add(new BasicNameValuePair("type", DeviceUtils.getChannelCode(context)));
        nameValuePairs.add(new BasicNameValuePair("deviceToken", deviceToken));
        nameValuePairs.add(new BasicNameValuePair("imei", CheckStrUtil.getImei(context)));
        nameValuePairs.add(new BasicNameValuePair("mac", CheckStrUtil.getMac(context)));
        String  popularize_token = SharedPreferencesUtil.getStringData(context,YCache.FENGKONG_CLIPBOARDCONTENT,"");
        if(!StringUtils.isEmpty(popularize_token)){
            nameValuePairs.add(new BasicNameValuePair("popularize_token", popularize_token));
        }


        String result = YConn.basedPost(context, YUrl.ACCOUNT_LOGIN_THIRD, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }

        JSONObject j = new JSONObject(result);
        int status = j.getInt("status");


        if (!j.has("h5money") || null == j.getString("h5money") || j.getString("h5money").equals("null")
                || j.getString("h5money").equals("")) {

            SignFragment.h5Moneny = 0;

        } else {
            try {
                SignFragment.h5Moneny = Double.parseDouble(j.optString("h5money"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        SharedPreferencesUtil.saveBooleanData(context, YCache.USER_FISRT_LOGIN, (j.optBoolean("firstLogin", false)));
        boolean mNeedFengkong = j.optInt("showSpecialPage", 0) == 0;
        GuideActivity.needFengKong = mNeedFengkong;
        SharedPreferencesUtil.saveBooleanData(context, YCache.NEED_FENG_KONG, mNeedFengkong);


        SharedPreferencesUtil.saveStringData(context, Pref.thrid_login_status, status + "");

        UserInfo user = EntityFactory.createYUser(context, result);
        EntityFactory.createStore(context, result);// 保存店铺数据
        EntityFactory.saveOrderToken(context, result);// 保存下单令牌
        user.setUsertype(Integer.parseInt(usertype));
//        user.setUserth_id(uid);
        String logStringoken = JSONUtils.getString(result, Json.token, "");
        int userReviewers = j.optInt("reviewers", 0);

        user.setReviewers(userReviewers);

        YCache.setCacheUser(context, user);// 登陆成功后设置缓存
        YCache.cacheUserInfo(context, logStringoken, user);
        return user;
    }

    /**
     * 注销自拥有账户登录
     */
    public static ReturnInfo Logout(Context context, String token) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));

        SignListAdapter.minuteMap.clear();
        SignListAdapter.indexMap.clear();

        if (null != NewSignCommonDiaolg.overtimer) {
            NewSignCommonDiaolg.overtimer.cancel();
        }
        if (null != NewSignCommonDiaolg.overtimershow) {
            NewSignCommonDiaolg.overtimershow.cancel();
        }
        if (null != NewSignCommonDiaolg.timer) {
            NewSignCommonDiaolg.timer.cancel();
        }

        String result = YConn.basedPost(context, YUrl.LOGOUT, nameValuePairs);
        //退出登录后风控就用渠道的风控
        GuideActivity.needFengKong = MainMenuActivity.mSwitchData.getMust_risk_management_channel() == 1;

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 注销第 三方账户登录
     */
    public static ReturnInfo Logout_Third(Context context, String uid, String token, String usertype) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("uid", uid));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("usertype", usertype));

        SignListAdapter.minuteMap.clear();
        SignListAdapter.indexMap.clear();

        if (null != NewSignCommonDiaolg.overtimer) {
            NewSignCommonDiaolg.overtimer.cancel();
        }
        if (null != NewSignCommonDiaolg.overtimershow) {
            NewSignCommonDiaolg.overtimershow.cancel();
        }
        if (null != NewSignCommonDiaolg.timer) {
            NewSignCommonDiaolg.timer.cancel();
        }

        String result = YConn.basedPost(context, YUrl.LOGOUT_THIRD, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 查询商品详情
     *
     * @param context
     *
     *
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> queryShopDetails(Context context, String token, String shop_code,
                                                           String attr_data, String sweet_theme_id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("code", shop_code));
        if (sweet_theme_id != null && !sweet_theme_id.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("id", sweet_theme_id));
        }
        // nameValuePairs.add(new BasicNameValuePair("attr_data", attr_data));
        long statrtTime = System.currentTimeMillis();
        String result = YConn.basedPost(context, YUrl.QUERY_SHOP_DETAILS, nameValuePairs);

        long endTime = System.currentTimeMillis();
//		Log.e("商品详情", (endTime - statrtTime) + "---shop/query");
        if (result == null || "".equals(result)) {
            return null;
        }
        HashMap<String, Object> map = EntityFactory.createShop(context, result);
        if (map == null) {
            return null;
        }
        // System.out.println("qqwweerr shop=" + shop);
        return map;
    }

    /**
     * shareshop查询商品详情
     */
    public static ShareShop queryShopDetailsShareshop(Context context, String token, String shop_code, String attr_data)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("code", shop_code));
        // nameValuePairs.add(new BasicNameValuePair("attr_data", attr_data));
        String result = YConn.basedPost(context, YUrl.QUERY_SHOP_DETAILS, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        ShareShop shareshop = EntityFactory.createShopShareshop(context, result);
        if (shareshop == null) {
            return null;
        }
        return shareshop;
    }

    // TODO:

    /****
     * 夺宝查询商品详情
     */
    public static HashMap<String, Object> queryIndianaShopDetails(Context context, String token, String treas_type,
                                                                  String attr_data, String shop_code, String issue_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        if ("".equals(treas_type)) {
            nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        } else {
            nameValuePairs.add(new BasicNameValuePair("treas_type", treas_type));
        }
        if (null != issue_code && !"".equals(issue_code)) {
            nameValuePairs.add(new BasicNameValuePair("issue_code", issue_code));
        }
        // nameValuePairs.add(new BasicNameValuePair("attr_data", attr_data));
        String result = YConn.basedPost(context, YUrl.IADIANA_SHOP_DETAILS, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        HashMap<String, Object> map = EntityFactory.createIndianaShop(context, result);
        if (map == null) {
            return null;
        }

        return map;
    }

    // 查询参与记录
    public static List<HashMap<String, Object>> queryIndianaTakeRecord(Context context, String shop_code,
                                                                       String issue_code, String page, String rows) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("issue_code", issue_code));
        nameValuePairs.add(new BasicNameValuePair("page", page));
        nameValuePairs.add(new BasicNameValuePair("rows", rows));
        String result = YConn.basedPost(context, YUrl.QUERY_TAKE_RECORD, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        List<HashMap<String, Object>> list = EntityFactory.createIndianaTakeRecord(context, result);
        if (list == null) {
            return null;
        }

        return list;
    }

    // 查询参与记录new
    public static HashMap<String, Object> queryIndianaTakeRecordNew(Context context, String shop_code,
                                                                    String issue_code, String page, String rows) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("issue_code", issue_code));
        nameValuePairs.add(new BasicNameValuePair("page", page));
        nameValuePairs.add(new BasicNameValuePair("rows", rows));
        String result = YConn.basedPost(context, YUrl.QUERY_TAKE_RECORD_NEW, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        HashMap<String, Object> list = EntityFactory.createIndianaTakeRecordNew(context, result);
        if (list == null) {
            return null;
        }

        return list;
    }

    /****
     * 查询会员商品详情
     *
     * @param context
     *
     *
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> queryMembersShopDetails(Context context, String token, String shop_code,
                                                                  String attr_data) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("code", shop_code));
        // nameValuePairs.add(new BasicNameValuePair("attr_data", attr_data));
        String result = YConn.basedPost(context, YUrl.YSS_URL_ANDROID + "vip/queryShop", nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        HashMap<String, Object> map = EntityFactory.createShop(context, result);
        if (map == null) {
            return null;
        }

        return map;
    }

    /**
     * shareshop查询
     */
    public static ShareShop queryMembersShopDetailsShareshop(Context context, String token, String shop_code,
                                                             String attr_data) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("code", shop_code));
        // nameValuePairs.add(new BasicNameValuePair("attr_data", attr_data));
        String result = YConn.basedPost(context, YUrl.YSS_URL_ANDROID + "vip/queryShop", nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        ShareShop shareshop = EntityFactory.createShopShareshop(context, result);
        if (shareshop == null) {
            return null;
        }

        return shareshop;
    }

    /****
     * 查询商品详情
     *
     * @param context
     *
     *
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> queryShopDetails2(Context context, String shop_code, String attr_data)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        // nameValuePairs.add(new BasicNameValuePair("token", YCache
        // .getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("code", shop_code));
        // nameValuePairs.add(new BasicNameValuePair("attr_data", attr_data));
        String result = YConn.basedPost(context, YUrl.QUERY_SHOP_DETAILS2, nameValuePairs);

        if (result == null || "".equals(result)) {
            return null;
        }
        HashMap<String, Object> map = EntityFactory.createShop(context, result);
        if (map == null) {
            return null;
        }

        return map;
    }

    public static HashMap<String, Object> queryShareDetails(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.QUERY_SHARE_DETIALS, nameValuePairs);

        if (result == null || "".equals(result)) {
            return null;
        }
        HashMap<String, Object> map = EntityFactory.createShareDetails(context, result);
        if (map == null) {
            return null;
        }

        return map;
    }

    /**
     * shareshop查询商品详情
     */
    public static ShareShop queryShopDetailsShareshop(Context context, String shop_code, String attr_data)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        // nameValuePairs.add(new BasicNameValuePair("token", YCache
        // .getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("code", shop_code));
        // nameValuePairs.add(new BasicNameValuePair("attr_data", attr_data));
        String result = YConn.basedPost(context, YUrl.QUERY_SHOP_DETAILS2, nameValuePairs);

        if (result == null || "".equals(result)) {
            return null;
        }
        ShareShop shareshop = EntityFactory.createShopShareshop(context, result);
        if (shareshop == null) {
            return null;
        }

        return shareshop;
    }

    /**
     * 查询商品套餐详情
     *
     * @param context
     * @param code
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> queryShopMeal(Context context, String code) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));

        nameValuePairs.add(new BasicNameValuePair("code", code));
        String result = null;
        if (YJApplication.instance.isLoginSucess()) {
            nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
            result = YConn.basedPost(context, YUrl.GET_SHOP_MEAL, nameValuePairs);
        } else {
            result = YConn.basedPost(context, YUrl.GET_SHOP_MEAL_UNLOGIN, nameValuePairs);
        }
        LogYiFu.e("套餐数据", result.toString());
        if (result == null || "".equals(result)) {
            return null;
        }
        HashMap<String, Object> map = EntityFactory.createShopMeal(context, result);

        return map;

    }

    /****
     * 查看该商品的评论
     *
     * @param context
     *
     *
     * @return
     * @throws Exception
     */
    public static List<ShopComment> queryShopEvaluate(Context context, String page, String rows, String shop_code,
                                                      boolean isYouXuan) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("page", page));
        nameValuePairs.add(new BasicNameValuePair("rows", rows));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        if (isYouXuan) {
            nameValuePairs.add(new BasicNameValuePair("is_real", "-1"));
        }
        String result = YConn.basedPost(context, YUrl.QUERY_SHOP_EVALUATE, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        List<ShopComment> list = null;
        list = EntityFactory.createListShop_comment(context, result);

        return list;
    }

    /**
     * 添加商品到购物车
     */
    public static ReturnInfo joinShopCart(Context context, String size, String color, String shop_num,
                                          String stock_type_id, String pic, String user_id, String token, double realPrice, Shop shop,
                                          String store_code, String supply_id, double kickback, int original_price, int id, String supp_label)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("size", size));
        nameValuePairs.add(new BasicNameValuePair("color", color));
        nameValuePairs.add(new BasicNameValuePair("stock_type_id", stock_type_id));
        nameValuePairs.add(new BasicNameValuePair("shop_num", shop_num));
        nameValuePairs.add(new BasicNameValuePair("shop_id", "" + shop.getId()));
        nameValuePairs.add(new BasicNameValuePair("shop_name", shop.getShop_name()));
        nameValuePairs.add(new BasicNameValuePair("shop_price", "" + shop.getShop_price()));
        nameValuePairs.add(new BasicNameValuePair("shop_se_price", "" + realPrice));
        nameValuePairs.add(new BasicNameValuePair("def_pic", pic));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop.getShop_code()));
        if (!TextUtils.isEmpty(shop.getCollocation_code())) {// 搭配编号不为空
            nameValuePairs.add(new BasicNameValuePair("paired_code", shop.getCollocation_code()));
        }
        nameValuePairs.add(new BasicNameValuePair("store_code", store_code));
        nameValuePairs.add(new BasicNameValuePair("supp_id", supply_id));

        nameValuePairs.add(new BasicNameValuePair("kickback", kickback + ""));
        nameValuePairs.add(new BasicNameValuePair("original_price", original_price + ""));

        nameValuePairs.add(new BasicNameValuePair("id", id + ""));
        nameValuePairs.add(new BasicNameValuePair("supp_label", supp_label + ""));

        String result = YConn.basedPost(context, YUrl.JOIN_SHOP_SHOPCART, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 查询购物车列表信息
     *
     * @param context
     *
     *
     * @return
     * @throws Exception
     */
    public static List<ShopCart> queryShopCarts(Context context, String token, String curPage, String pageSize)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("curPage", curPage));
        nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));
        String result = YConn.basedPost(context, YUrl.QUERY_SHOP_SHOPCART, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        List<ShopCart> list = null;
        list = EntityFactory.createListShop_Cart(context, result);

        return list;
    }

    /****
     * NEW正价查询购物车列表信息
     *
     * @param context
     *
     *
     * @return
     * @throws Exception
     */
    public static List<List<ShopCart>> queryShopCartsNew(Context context, String token, String curPage, String pageSize,
                                                         String expired) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("curPage", curPage));
        nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));
        nameValuePairs.add(new BasicNameValuePair("expired", expired));
        String result = YConn.basedPost(context, YUrl.QUERY_SHOP_SHOPCART, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        List<List<ShopCart>> list = null;
        list = EntityFactory.createListShop_CartNew(context, result);

        return list;
    }

    /**
     * 重新加入购物车
     */
    public static ReturnInfo newJoinShopCartsCommon(Context context, String token, String code, String isWhat)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        if ("isDaPei".equals(isWhat)) {
            nameValuePairs.add(new BasicNameValuePair("paired_code", code));
        } else if ("isShop".equals(isWhat)) {
            nameValuePairs.add(new BasicNameValuePair("shop_code", code));
        } else if ("isMeal".equals(isWhat)) {
            nameValuePairs.add(new BasicNameValuePair("p_code", code));
        }
        String result = YConn.basedPost(context, YUrl.NEW_JOIN_SHOP_SHOPCART, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 购物车获取下架列表
     */
    public static List<String> getInvalidList(Context context, String token, String codes, String isWhat)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        if ("common".equals(isWhat)) {
            nameValuePairs.add(new BasicNameValuePair("shop_codes", codes));
        } else if ("meal".equals(isWhat)) {
            nameValuePairs.add(new BasicNameValuePair("p_codes", codes));
        }
        String result = YConn.basedPost(context, YUrl.GET_SHOPCART_INVALID_LIST, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.invalid_shopcart(context, result);
    }

    /**
     * 启动时获取pic
     */
    public static HashMap<String, Object> startGetPic(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        String result = YConn.basedPost(context, YUrl.STRAT_GET_PIC, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createPic(context, result);
    }

    /**
     * 分享生活状态图
     */
    public static HashMap<String, Object> ShareLifeGetPic(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        String result = YConn.basedPost(context, YUrl.STRAT_GET_SHARE_LIFE_PIC, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createPicLife(context, result);
    }

    /**
     * 开启余额翻倍
     */
    public static HashMap<String, Object> startDoubleBalance(Context context, String token, int entrance)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("entrance", entrance + ""));
        String result = YConn.basedPost(context, YUrl.DOUBLE_BALANCE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createDoubleBalance(context, result);
    }

    /**
     * 特卖查询购物车信息
     */
    // public static List<ShopCart> queryShopCartsSpecial(Context context,
    // String token, String curPage, String pageSize,
    // String expired) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token", token));
    // nameValuePairs.add(new BasicNameValuePair("curPage", curPage));
    // nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));
    // nameValuePairs.add(new BasicNameValuePair("expired", expired));
    // String result = YConn.basedPost(context,
    // YUrl.QUERY_SHOP_SHOPCART_SPECIAL, nameValuePairs);
    // if (result == null || "".equals(result)) {
    // return null;
    // }
    // List<ShopCart> list = null;
    // list = EntityFactory.createListShop_Cart(context, result);
    //
    // return list;
    // }

    /**
     * 删除购物车的商品 单个删除
     */
    public static ReturnInfo deleteShopCart(Context context, String token, String id, String pCode) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("id", id));
        if (TextUtils.isEmpty(pCode) == false) {
            nameValuePairs.add(new BasicNameValuePair("p_code", pCode));
        }
        String result = YConn.basedPost(context, YUrl.DELETE_SHOP_SHOPCART, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * NEW 正价 删除购物车的商品 单个删除
     */
    public static ReturnInfo deleteShopCartNew(Context context, String token, String id, String pCode, String isSelect,
                                               String paired_code, String shop_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));

        if (TextUtils.isEmpty(pCode) == false) {
            nameValuePairs.add(new BasicNameValuePair("p_code", pCode));
        }
        if ("yes_dapei".equals(isSelect)) {
            nameValuePairs.add(new BasicNameValuePair("paired_code", paired_code));
            nameValuePairs.add(new BasicNameValuePair("id", id));
            nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        } else if ("yes_single".equals(isSelect)) {
            nameValuePairs.add(new BasicNameValuePair("id", id));
        } else if ("no_dapei".equals(isSelect)) {

            nameValuePairs.add(new BasicNameValuePair("paired_code", paired_code));
        }
        String result = YConn.basedPost(context, YUrl.DELETE_SHOP_SHOPCART, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 特卖删除购物车的商品 单个删除
     */
    public static ReturnInfo deleteShopCartSpecial(Context context, String token, String id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("id", id));
        // if(TextUtils.isEmpty(pCode)==false){
        // nameValuePairs.add(new BasicNameValuePair("p_code", pCode));
        // }
        String result = YConn.basedPost(context, YUrl.DELETE_SHOP_SHOPCART_SPECIAL, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 删除购物车的商品 批量删除
     */
    public static ReturnInfo deleteShopCartList(Context context, String token, String ids) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("ids", ids));
        String result = YConn.basedPost(context, YUrl.DELETE_SHOP_SHOPCART, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 修改购物车的商品数量
     */
    public static ReturnInfo updateShopCart(Context context, String token, String shop_num, String id,
                                            String stock_type_id, String p_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("shop_num", shop_num));
        nameValuePairs.add(new BasicNameValuePair("id", id));
        if (TextUtils.isEmpty(p_code) == false) {
            nameValuePairs.add(new BasicNameValuePair("p_code", p_code));
        } else {
            nameValuePairs.add(new BasicNameValuePair("stock_type_id", stock_type_id));
        }
        String result = YConn.basedPost(context, YUrl.UPDATE_SHOP_SHOPCART, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 特卖修改购物车的商品数量
     */
    // public static ReturnInfo updateShopCartSpecial(Context context, String
    // token, String shop_num, String id)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token", token));
    // nameValuePairs.add(new BasicNameValuePair("shop_num", shop_num));
    // nameValuePairs.add(new BasicNameValuePair("id", id));
    // String result = YConn.basedPost(context,
    // YUrl.UPDATE_SHOP_SHOPCART_SPECIAL, nameValuePairs);
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createRetInfo(context, result);
    // }

    /**
     * 修改购物车的商品 价格 数量,属性，尺码等
     */
    public static ReturnInfo updateShopCartType(Context context, String token, String id, String shop_se_price,
                                                String color, String size, String shop_num, String def_pic, String stock_type_id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("id", id));
        // nameValuePairs.add(new BasicNameValuePair("shop_price", shop_price));
        nameValuePairs.add(new BasicNameValuePair("shop_se_price", shop_se_price));
        nameValuePairs.add(new BasicNameValuePair("color", color));
        nameValuePairs.add(new BasicNameValuePair("size", size));
        nameValuePairs.add(new BasicNameValuePair("shop_num", shop_num));
        nameValuePairs.add(new BasicNameValuePair("def_pic", def_pic));
        nameValuePairs.add(new BasicNameValuePair("stock_type_id", stock_type_id));

        String result = YConn.basedPost(context, YUrl.UPDATE_SHOP_SHOPCART, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 查询商品属性
     *
     * @param context
     *
     *
     * @return
     * @throws Exception
     */
    public static Shop queryShopQueryAttr(Context context, Shop shop, String find) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("shop_code", "" + shop.getShop_code()));
        nameValuePairs.add(new BasicNameValuePair("find", find));
        String result = YConn.basedPost(context, YUrl.QUERY_SHOP_ATTR, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        EntityFactory.createStock_type(context, result, shop);
        return shop;
    }

    /****
     * 查询积分商品属性
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static IntegralShop queryInteShopQueryAttr(Context context, IntegralShop shop, String find)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("shop_code", "" + shop.getId()));
        nameValuePairs.add(new BasicNameValuePair("find", find));
        String result = YConn.basedPost(context, YUrl.QUERY_INTESHOP_ATTR, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        EntityFactory.createInteGoodStock_type(context, result, shop);
        return shop;
    }

    /****
     * 查询商品属性
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static List<StockType> queryShop_Stokect(Context context, String shop_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("find", "false"));
        String result = YConn.basedPost(context, YUrl.QUERY_SHOP_ATTR, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        List<StockType> list = EntityFactory.createStock_type(context, result);
        return list;
    }

    /**
     * 晒单详情数据
     *
     * @param context
     * @param shop_code
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> queryDisplayOrderDetials(Context context, String token, String shop_code)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("token", token));

        String result = YConn.basedPost(context, YUrl.QUERY_SHAIDAN_DETIALS, nameValuePairs);

        if (result == null || "".equals(result)) {
            return null;
        }
        HashMap<String, Object> map = EntityFactory.createShaiDanDetials(context, result);
        if (map == null) {
            return null;
        }

        return map;
    }

    /**
     * 晒单详情评论列表
     */
    public static HashMap<String, List<HashMap<String, Object>>> queryOrderDetialsComment(Context context, String token,
                                                                                          String shop_code, int curPage) throws Exception {
        HashMap<String, List<HashMap<String, Object>>> map = new HashMap<String, List<HashMap<String, Object>>>();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", curPage + ""));

        String result = YConn.basedPost(context, YUrl.QUERY_SHAIDAN_DETIALS_COMMENT, nameValuePairs);

        if (result == null || "".equals(result)) {
            return null;
        }
        map = EntityFactory.createShaiDanDetialsComment(context, result);
        if (map == null) {
            return null;
        }
        return map;
    }

    /**
     * 晒单发表评论
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> addShaiDanComment(Context context, String shop_code, String toUserId,
                                                            String content) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("content", content));
        if (!"".equals(toUserId)) {
            nameValuePairs.add(new BasicNameValuePair("to_user_id", toUserId));
        }
        String result = YConn.basedPost(context, YUrl.ADD_SHAIDAN_COMMENT, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createShaiDanComment(context, result);
    }

    /**
     * 晒单 点赞
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> addShaiDanDianZan(Context context, String shop_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        String result = YConn.basedPost(context, YUrl.ADD_SHAIDAN_CLICK, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createShaiDanClick(context, result);
    }

    /**
     * 添加商品到我的喜欢
     */
    public static ReturnInfo addLikeShop(Context context, String token, String shop_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        nameValuePairs.add(new BasicNameValuePair("show", week + ""));
        String result = YConn.basedPost(context, YUrl.ADD_MY_LIKE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    // 加喜好同时置顶
    public static ReturnInfo addTopShop(Context context, String token, String shop_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("is_show", "1"));
        Store s = YCache.getCacheStoreSafe(context);
        nameValuePairs.add(new BasicNameValuePair("realm", s.getRealm(context)));
        nameValuePairs.add(new BasicNameValuePair("s_code", s.getS_code()));
        String result = YConn.basedPost(context, YUrl.YSS_URL_ANDROID_H5 + "storeShop/update", nameValuePairs);
        LogYiFu.e("加置顶", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 删除商品到我的喜欢
     */
    public static ReturnInfo deleteLikeShop(Context context, String token, String shop_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));

        String result = YConn.basedPost(context, YUrl.DELETE_MY_LIKE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 2.28 申请退换货
     *
     * @param context
     *
     *
     * @param money
     * @param explain
     * @param pic
     * @param change
     *
     * @return
     * @throws Exception
     */
    public static ReturnInfo returnShop(Context context, String token, String order_shop_id, String money,
                                        String explain, String pic, String change, String order_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("order_shop_id", order_shop_id));
        nameValuePairs.add(new BasicNameValuePair("money", money));
        nameValuePairs.add(new BasicNameValuePair("explain", explain));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));
        nameValuePairs.add(new BasicNameValuePair("change", change));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        String result = YConn.basedPost(context, YUrl.RETURN_SHOP_ADD, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);

    }

    /****
     * 2.28 申请退换货 by luojie
     *
     */
    public static ReturnInfo returnShop(Context context, String explain, String pic, String return_type,
                                        String order_code, String cause) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("explain", explain));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));
        nameValuePairs.add(new BasicNameValuePair("return_type", return_type)); // 1换货2退货3退款
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("cause", cause));
        String result = YConn.basedPost(context, YUrl.RETURN_SHOP_ADD, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 2.28 新 申请退换货
     *
     */
    public static HashMap<String, Object> returnShopNew(Context context, String explain, String pic, String return_type,
                                                        String cause, String order_shop_id, String order_shop_status) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("explain", explain));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));
        nameValuePairs.add(new BasicNameValuePair("return_type", return_type)); // 1换货2退货3退款
        nameValuePairs.add(new BasicNameValuePair("order_shop_id", order_shop_id));
        nameValuePairs.add(new BasicNameValuePair("order_shop_status", order_shop_status));
        nameValuePairs.add(new BasicNameValuePair("cause", cause));
        String result = YConn.basedPost(context, YUrl.RETURN_SHOP_ADD, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createReturnShopNew(context, result);
    }

    /****
     * 2.28 套餐新 申请退换货
     *
     */
    public static HashMap<String, Object> returnMealShop(Context context, String explain, String cause,
                                                         String order_code, String return_type, String pic) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("explain", explain));
        nameValuePairs.add(new BasicNameValuePair("cause", cause));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("return_type", return_type));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));
        String result = YConn.basedPost(context, YUrl.RETURN_MEAL_SHOP_ADD, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createReturnShopNew(context, result);
    }

    /****
     * 2.28 新 申请退换货
     *
     */
    public static HashMap<String, Object> returnShopNewTh(Context context, String explain, String pic,
                                                          String return_type, String cause, String order_shop_id, String order_shop_status, double money)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("explain", explain));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));
        nameValuePairs.add(new BasicNameValuePair("return_type", return_type)); // 1换货2退货3退款
        nameValuePairs.add(new BasicNameValuePair("order_shop_id", order_shop_id));
        nameValuePairs.add(new BasicNameValuePair("order_shop_status", order_shop_status));
        nameValuePairs.add(new BasicNameValuePair("cause", cause));
        nameValuePairs.add(new BasicNameValuePair("money", "" + money));
        String result = YConn.basedPost(context, YUrl.RETURN_SHOP_ADD, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createReturnShopNew(context, result);
    }

    /****
     * 2.28 套餐新 申请退换货
     *
     */
    public static HashMap<String, Object> returnMealShopTh(Context context, String explain, String cause,
                                                           String order_code, String return_type, String pic, double money) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("explain", explain));
        nameValuePairs.add(new BasicNameValuePair("cause", cause));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("return_type", return_type));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));
        nameValuePairs.add(new BasicNameValuePair("money", "" + money));
        String result = YConn.basedPost(context, YUrl.RETURN_MEAL_SHOP_ADD, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createReturnShopNew(context, result);
    }

    /**
     * 夺宝申请退款表
     */
    public static HashMap<String, Object> returnIndanaShop(Context context, String explain, String cause,
                                                           String order_code, String return_type, String pic) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("explain", explain));
        nameValuePairs.add(new BasicNameValuePair("return_type", return_type));
        nameValuePairs.add(new BasicNameValuePair("cause", cause));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));
        String result = YConn.basedPost(context, YUrl.RETURN_DUOBAO_SHOP_ADD, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createReturnDuobaoShopNew(context, result);
    }

    /****
     * 2.28 套餐新 申请退换货
     *
     */
    public static HashMap<String, Object> returnDuobaoShop(Context context, String explain, String cause,
                                                           String order_code, String return_type, String pic) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("explain", explain));
        nameValuePairs.add(new BasicNameValuePair("cause", cause));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("return_type", return_type));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));
        String result = YConn.basedPost(context, YUrl.RETURN_DUOBAO_SHOP_ADD, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createReturnDuobaoShopNew(context, result);
    }

    /****
     * 2.27 修改退换货
     *
     * @param context
     *
     *
     * @param money
     * @param explain
     * @param pic
     * @param express_code
     * @param change
     *
     * @return
     * @throws Exception
     */
    public static ReturnInfo updateReturnShop(Context context, String token, String order_shop_id, String money,

                                              String explain, String pic, String express_code, String change, String order_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("order_shop_id", order_shop_id));
        nameValuePairs.add(new BasicNameValuePair("money", money));
        nameValuePairs.add(new BasicNameValuePair("explain", explain));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));
        nameValuePairs.add(new BasicNameValuePair("express_code", express_code));
        nameValuePairs.add(new BasicNameValuePair("change", change));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));

        String result = YConn.basedPost(context, YUrl.UPADTE_RETURNSHOP, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 2.27 修改退换货 by luojie
     *
     * @param context
     *
     *
     * @param explain
     * @param pic
     *
     * @return
     * @throws Exception
     */
    public static ReturnInfo updateReturnShop(Context context, String explain, String pic, String express_id,
                                              String return_type, String order_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("explain", explain));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));
        nameValuePairs.add(new BasicNameValuePair("express_id", express_id));
        nameValuePairs.add(new BasicNameValuePair("return_type", return_type));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));

        String result = YConn.basedPost(context, YUrl.UPADTE_RETURNSHOP, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 删除订单
     *
     * @param context
     *
     *
     * @return
     * @throws Exception
     */

    public static ReturnInfo returnShop(Context context, String token, String order_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        String result = YConn.basedPost(context, YUrl.DELETE_ORDER, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 取消订单
     *
     * @param context
     *
     *
     * @return
     * @throws Exception
     */

    public static ReturnInfo escOrder(Context context, String token, String order_code, String end_explain)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("end_explain", end_explain));
        String result = YConn.basedPost(context, YUrl.CANCLE_ESCORDER, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 夺宝 取消订单
     *
     * @param context
     *
     *
     * @return
     * @throws Exception
     */

    public static ReturnInfo escOrderDuo(Context context, String token, String order_code, String end_explain)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("end_explain", end_explain));
        String result = YConn.basedPost(context, YUrl.CANCLE_ESCORDER_DUO, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 根据订单编号确认收货
     *
     * @param context
     *
     *
     *
     *
     * @return
     * @throws Exception
     */

    public static ReturnInfo affirmOrder(Context context, String token, String order_code, String pwd)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));

        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));
        String result = YConn.basedPost(context, YUrl.AFFIRMORDER_ORDER, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 根据商品编号确认收货
     *
     * @param context
     *
     *
     *
     *
     * @return
     * @throws Exception
     */

    public static ReturnInfo affirmOrdershop(Context context, String token, String order_shop_id, String pwd)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("order_shop_id", order_shop_id));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));
        String result = YConn.basedPost(context, YUrl.AFFIRMORDER_ORDER_SHOP, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 延长收货
     *
     * @param context
     *
     *
     * @return
     * @throws Exception
     */
    public static ReturnInfo extensionOrdershop(Context context, String token, String order_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));

        String result = YConn.basedPost(context, YUrl.EXTENSIOON_ORDER, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 提交评论
     *
     * @param context
     *
     *
     * @param content
     * @param pic
     * @param comment_type
     * @return
     * @throws Exception
     */
    public static ReturnInfo addShopComment(Context context, String token, String order_shop_id, String content,
                                            String pic, String comment_type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("order_shop_id", order_shop_id));
        nameValuePairs.add(new BasicNameValuePair("content", content));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));
        nameValuePairs.add(new BasicNameValuePair("comment_type", comment_type));

        String result = YConn.basedPost(context, YUrl.EXTENSIOON_ORDER, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 追加评论
     *
     * @param context
     *
     * @param content
     * @param pic
     * @param mid
     * @return
     * @throws Exception
     */
    public static ReturnInfo appendComment(Context context, String token, String content, String pic, String mid)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("mid", mid));
        nameValuePairs.add(new BasicNameValuePair("content", content));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));

        String result = YConn.basedPost(context, YUrl.EXTENSIOON_ORDER, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 2.21 审核追加的评论
     *
     * @param context
     *
     * @param verify_info
     * @param verify_status
     * @param mid
     * @param commentId
     * @return
     * @throws Exception
     */
    public static ReturnInfo verifyAppend(Context context, String token, String verify_info, String verify_status,
                                          String mid, String commentId) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("mid", mid));
        nameValuePairs.add(new BasicNameValuePair("verify_info", verify_info));
        nameValuePairs.add(new BasicNameValuePair("verify_status", verify_status));
        nameValuePairs.add(new BasicNameValuePair("commentId", commentId));

        String result = YConn.basedPost(context, YUrl.VERIFY_APPEND_SHOPCOMMENT, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /*****
     * 添加物流信息
     *
     * @param context
     *
     *
     * @param logi_code
     * @param logi_name
     * @return
     * @throws Exception
     */
    public static ReturnInfo addLogistics(Context context, String token, String Order_shop_id, String logi_code,
                                          String logi_name) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("order_shop_id", Order_shop_id));
        nameValuePairs.add(new BasicNameValuePair("logi_code", logi_code));
        nameValuePairs.add(new BasicNameValuePair("logi_name", logi_name));
        String result = YConn.basedPost(context, YUrl.ADDLOGISTICS_RETURNSHOP, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 通过我的钱包付款单个订单
     *
     * @param context
     *
     *
     *
     * @return
     * @throws Exception
     */
    public static ReturnInfo walletPayOrder(Context context, String order_code, String pwd) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));
        String result = YConn.basedPost(context, YUrl.WALLET_PAYORDER, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 通过我的钱包红包付款
     *
     * @param context
     *
     *
     *
     * @return
     * @throws Exception
     */
    public static ReturnInfo walletPayRed(Context context, String re_id, String pwd) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("rp_id", re_id));
        nameValuePairs.add(new BasicNameValuePair("uid", "oPQMKvzB8tIFX55w-Gbc6xGiZVdw"));
        nameValuePairs.add(new BasicNameValuePair("amount", "1"));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));
        String result = YConn.basedPost(context, YUrl.WALLET_PAYRED, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /****
     * 通过我的钱包来付款多个订单
     *
     * @param context
     *
     * @param g_code
     *
     * @return
     * @throws Exception
     */
    public static ReturnInfo walletPayMultiOrder(Context context, String g_code, String pwd) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("g_code", g_code));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));
        String result = YConn.basedPost(context, YUrl.WALLET_PAY_MULTI_ORDER, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    public static List<OrderShop> getOrderShop(Context context, String token, String order_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        String result = YConn.basedPost(context, YUrl.QUERY_ORDERSHOP_DETAILS, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        List<OrderShop> list = EntityFactory.createOrderShop_Details(context, result);
        return list;
    }

    /**
     * 我的最爱置顶与否
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo getMyFavorIsSetTop(Context context, String shop_code, String is_show) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("is_show", is_show));
        String result = YConn.basedPost(context, YUrl.GET_MY_FAVOUR_IS_SET_TOP, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 删除我的最爱
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo deleteMyFavor(Context context, String shop_codes) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("shop_codes", shop_codes));
        String result = YConn.basedPost(context, YUrl.DELETE_MY_LIKE, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 删除我的足迹
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo deleteMyStep(Context context, String shop_code, String shop_codes) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("realm", YCache.getCacheStore(context).getRealm(context)));
        if (!TextUtils.isEmpty(shop_code)) {
            nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        }
        if (!TextUtils.isEmpty(shop_codes)) {
            nameValuePairs.add(new BasicNameValuePair("shop_codes", shop_codes));
        }
        String result = YConn.basedPost(context, YUrl.DELETE_MY_STEPS, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 我的足迹---添加
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo addMySteps(Context context, String shop_code) throws Exception {
        if (YJApplication.instance.isLoginSucess() == false) {
            return null;
        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("realm", YCache.getCacheStore(context).getRealm(context)));
        long statrtTime = System.currentTimeMillis();
        String result = YConn.basedPost(context, YUrl.YSS_URL_ANDROID + "mySteps/addSteps", nameValuePairs);

        long endTime = System.currentTimeMillis();
//		Log.e("商品详情", (endTime - statrtTime) + "---mySteps/addSteps");

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 我的足迹---查询
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static List<HashMap<String, Object>> queryMyStepsList(Context context, String currPage, String store_code,
                                                                 String is_del, String pageSize) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("isApp", "true"));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", currPage));
        // nameValuePairs.add(new BasicNameValuePair("pager.pageSize",
        // pageSize));
        // nameValuePairs.add(new BasicNameValuePair("store_code", store_code));
        nameValuePairs.add(new BasicNameValuePair("realm", YCache.getCacheStore(context).getRealm(context)));

        if (!TextUtils.isEmpty(is_del)) {
            nameValuePairs.add(new BasicNameValuePair("is_del", is_del)); // 是否失效参数，1位失效，空的为全部
        }
        String result = YConn.basedPost(context, YUrl.QUERY_STEPS_LIST, nameValuePairs);

        // System.out.println("result:" + result);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMyStepsList(context, result);
    }

    /**
     * 设置---登陆设备
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static List<HashMap<String, Object>> loginDevices(Context context, int currPage) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", currPage + ""));
        String result = YConn.basedPost(context, YUrl.LOGINDEVICE_LIST, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.loginDeviceList(context, result);
    }

    /****
     * 查询用户信息
     *
     * @param context
     *
     *
     * @return
     * @throws Exception
     */
    public static UserInfo queryUserInfo(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_USER_INFO, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        // System.out.println("UserInfo:" + result.toString());
        UserInfo user = EntityFactory.createYUser(context, result);
        return user;
    }

    /**
     * 修改我的昵称
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static UserInfo updateNickName(Context context, String nickName) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("nickname", nickName));
        String result = YConn.basedPost(context, YUrl.UPDATE_USER_INFO, nameValuePairs);

        if (result == null || "".equals(result)) {
            return null;
        }
        UserInfo user = EntityFactory.createYUser(context, result);
        EntityFactory.createStore(context, result);// 保存店铺数据
        user.setUsertype(-1);
        YCache.setCacheUser(context, user);// 登陆成功后设置缓存
        return user;
    }

    /**
     * 修改我的个性签名
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static UserInfo updateSignature(Context context, String signature) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("person_sign", signature));
        String result = YConn.basedPost(context, YUrl.UPDATE_USER_INFO, nameValuePairs);

        if (result == null || "".equals(result)) {
            return null;
        }
        UserInfo user = EntityFactory.createYUser(context, result);
        EntityFactory.createStore(context, result);// 保存店铺数据
        user.setUsertype(-1);
        YCache.setCacheUser(context, user);// 登陆成功后设置缓存
        return user;
    }

    /**
     * 修改我的生日
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static UserInfo updateBirthday(Context context, String birthday) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("birthday", birthday));
        String result = YConn.basedPost(context, YUrl.UPDATE_USER_INFO, nameValuePairs);

        if (result == null || "".equals(result)) {
            return null;
        }
        UserInfo user = EntityFactory.createYUser(context, result);
        EntityFactory.createStore(context, result);// 保存店铺数据
        user.setUsertype(-1);
        YCache.setCacheUser(context, user);// 登陆成功后设置缓存
        return user;
    }

    /**
     * 修改我的省份
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static UserInfo updateProvince(Context context, String province) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("province", province));
        String result = YConn.basedPost(context, YUrl.UPDATE_USER_INFO, nameValuePairs);

        if (result == null || "".equals(result)) {
            return null;
        }
        UserInfo user = EntityFactory.createYUser(context, result);
        EntityFactory.createStore(context, result);// 保存店铺数据
        user.setUsertype(-1);
        YCache.setCacheUser(context, user);// 登陆成功后设置缓存
        return user;
    }

    /**
     * 修改我的省份
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static UserInfo updateCity(Context context, String city) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("city", city));
        String result = YConn.basedPost(context, YUrl.UPDATE_USER_INFO, nameValuePairs);

        if (result == null || "".equals(result)) {
            return null;
        }
        UserInfo user = EntityFactory.createYUser(context, result);
        EntityFactory.createStore(context, result);// 保存店铺数据
        user.setUsertype(-1);
        YCache.setCacheUser(context, user);// 登陆成功后设置缓存
        return user;
    }

    /**
     * 修改密码，检测原支付密码是否正确
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo ckPwd(Context context, String pwd) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));
        String result = YConn.basedPost(context, YUrl.CHECK_PWD, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 通过原密码修改支付密码
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo upwalletpwd(Context context, String old_pwd, String payment_pwd) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("old_pwd", MD5Tools.MD5(old_pwd)));
        nameValuePairs.add(new BasicNameValuePair("payment_pwd", MD5Tools.MD5(payment_pwd)));
        String result = YConn.basedPost(context, YUrl.UPWALLETPWD, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 通过手机修改支付密码-获取验证码
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo get_Phone_Code_To_Uppaypas(Context context, String vcode) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("vcode", vcode));
        String result = YConn.basedPost(context, YUrl.GET_PHONE_CODE_TO_UPPAYPASS, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 通过手机修改支付密码-验证验证码是否正确
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo ckPhoneCode(Context context, String code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("code", code));
        String result = YConn.basedPost(context, YUrl.CKPHONECODE, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 通过手机修改支付密码
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo upwalletpwdbysms(Context context, String code, String payment_pwd) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("code", code));
        nameValuePairs.add(new BasicNameValuePair("payment_pwd", MD5Tools.MD5(payment_pwd)));
        String result = YConn.basedPost(context, YUrl.UPWALLETPWDBYSMS, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 查询绑定手机信息
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static QueryPhoneInfo bindPhone(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.QUERYPHONE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createQueryPhoneInfo(context, result);
    }

    /**
     * 查询绑定邮箱信息
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static QueryEmailInfo bindEmail(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.QUERYEMAIL, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createQueryEmailInfo(context, result);
    }

    /**
     * 检查手机是否已绑定其他账号
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static QueryPhoneInfo checkPhoneIsBind(Context context, String phoneNum) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("phone", phoneNum));

        String result = YConn.basedPost(context, YUrl.CHECKPHONE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createQueryPhoneInfo(context, result);
    }

    /**
     * 检测邮箱是否已绑定其他账号
     */
    public static QueryEmailInfo checkEmailIsBind(Context context, String email) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("email", email));

        String result = YConn.basedPost(context, YUrl.CHECKEMAIL, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createQueryEmailInfo(context, result);
    }

    /**
     * 绑定手机，验证短信验证码
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo checkCode(Context context, String code, String ageGroup, String isChannl)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        if (isChannl.equals("isChannl")) {
            nameValuePairs.add(new BasicNameValuePair("ageGroup", ageGroup));
        }

        nameValuePairs.add(new BasicNameValuePair("code", code));

        String result = YConn.basedPost(context, YUrl.CHECKCODE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 绑定手机，验证短信验证码(自己设置登录密码)
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo checkCodePwd(Context context, String code, String ageGroup, String isChannl, String pwd)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        if (isChannl.equals("isChannl")) {
            nameValuePairs.add(new BasicNameValuePair("ageGroup", ageGroup));
        }

        nameValuePairs.add(new BasicNameValuePair("code", code));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));

        String result = YConn.basedPost(context, YUrl.CHECKCODE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 更改绑定手机，验证原来手机短信验证码
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo checkOldCode(Context context, String code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("code", code));

        String result = YConn.basedPost(context, YUrl.CHECKOLDCODE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 绑定手机，验证短信验证码
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo checkEmailCode(Context context, String code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("code", code));

        String result = YConn.basedPost(context, YUrl.CHECKEMAILCODE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 绑定手机--验证支付密码成功则绑定手机号
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo checkPaymentPassword(Context context, String pwd) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));

        String result = YConn.basedPost(context, YUrl.CHECKPAYMENTPASSWORD, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 帮助中心-问题详情
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static Help getHelpQuestion(Context context, String id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("id", id));

        String result = YConn.basedPost(context, YUrl.GET_HELP_QUESTION, nameValuePairs);
        // System.out.println("result:" + result);
        return (result == null || "".equals(result)) ? null : EntityFactory.createHelp(context, result);
    }

    /**
     * 我的钱包-检测是否设置了支付密码
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static CheckPwdInfo checkPWD(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.CKSETPWD, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createCheckPwdInfo(context, result);
    }

    /**
     * 我的钱包-设置支付密码
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo setWalletPwd(Context context, String pwd) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("payment_pwd", MD5Tools.MD5(pwd)));

        String result = YConn.basedPost(context, YUrl.SETWALLETPWD, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 我的钱包-我的银行卡
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static List<MyBankCard> findMyBankCard(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.FINDMYBANKCARD, nameValuePairs);
        // System.out.println("result:" + result);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMyBankCardList(context, result);
    }

    /**
     * 我的钱包-添加银行卡
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo addMyBankCard(Context context, String bank_no, String identity, String name,
                                           String phone/* ,String branch_name */, String province_id, String province, String city,
                                           String city_id/*
     * , String e_mail
     */
    ) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("bank_no", bank_no));
//        nameValuePairs.add(new BasicNameValuePair("identity", identity));
        nameValuePairs.add(new BasicNameValuePair("name", name));
        nameValuePairs.add(new BasicNameValuePair("phone", phone));
        // nameValuePairs.add(new BasicNameValuePair("branch_name",
        // branch_name)); //支行名称
//        nameValuePairs.add(new BasicNameValuePair("province_id", province_id));// 收款人省id
//        // integer
//        nameValuePairs.add(new BasicNameValuePair("province", province));
//        nameValuePairs.add(new BasicNameValuePair("city_id", city_id));// 收款人市id
        // integer
//        nameValuePairs.add(new BasicNameValuePair("city", city));
        String mac = TextUtils.isEmpty(CheckStrUtil.getMac(context)) ? "0" : CheckStrUtil.getMac(context);// 没有给0
        String imei = TextUtils.isEmpty(CheckStrUtil.getImei(context)) ? "0" : CheckStrUtil.getImei(context);// 没有给0
        nameValuePairs.add(new BasicNameValuePair("mac", mac));
        nameValuePairs.add(new BasicNameValuePair("imei", imei));
        // nameValuePairs.add(new BasicNameValuePair("e_main", e_mail));//邮箱 可选

        String result = YConn.basedPost(context, YUrl.ADDMYBANKCARD, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 我的钱包-完善银行卡信息
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo updateMyBankCard(Context context, String bank_no, String identity, String name,
                                              String phone, String branch_name, String province_id, String province, String city, String city_id,
                                              String e_mail, Integer bank_id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("bank_no", bank_no));
        nameValuePairs.add(new BasicNameValuePair("identity", identity));
        nameValuePairs.add(new BasicNameValuePair("name", name));
        nameValuePairs.add(new BasicNameValuePair("phone", phone));
        nameValuePairs.add(new BasicNameValuePair("branch_name", branch_name)); // 支行名称
        nameValuePairs.add(new BasicNameValuePair("province_id", province_id));// 收款人省id
        // integer
        nameValuePairs.add(new BasicNameValuePair("province", province));
        nameValuePairs.add(new BasicNameValuePair("city_id", city_id));// 收款人市id
        // integer
        nameValuePairs.add(new BasicNameValuePair("city", city));
        nameValuePairs.add(new BasicNameValuePair("e_main", e_mail));// 邮箱 可选
        nameValuePairs.add(new BasicNameValuePair("id", bank_id + ""));// 邮箱 可选
        String result = YConn.basedPost(context, YUrl.UPDATEMYBANKCARD, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 我的钱包-提现，获取最大最小值
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static HashMap<String, String> getMaxAndMin(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.GETDEPOSITRANGE, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMaxAndMin(context, result);
    }

    /**
     * 我的钱包-银行卡提现
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> bankDepositAdd(Context context, String money, String pwd, String bank_id,
                                                         String message) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("money", money));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));
        nameValuePairs.add(new BasicNameValuePair("bank_id", bank_id));
        nameValuePairs.add(new BasicNameValuePair("message", message));

        String result = YConn.basedPost(context, YUrl.BANKDEPOSITADD, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createWithDrawInfo(context, result);
    }

    /**
     * 我的钱包-微信提现
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> WXDepositAdd(Context context, String money, String message,
                                                       String collect_name, String identity, String collect_bank_code, boolean flag) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("money", money));
        // nameValuePairs.add(new BasicNameValuePair("pwd",
        // MD5Tools.MD5("111111")));//微信提现不需要支付密码了
        // nameValuePairs.add(new BasicNameValuePair("bank_id", bank_id));
        nameValuePairs.add(new BasicNameValuePair("message", message));
        nameValuePairs.add(new BasicNameValuePair("collect_name", collect_name));
        nameValuePairs.add(new BasicNameValuePair("collect_bank_code", collect_bank_code));
        if (flag) {// 没有区分AB类的时候必给
            String mac = TextUtils.isEmpty(CheckStrUtil.getMac(context)) ? "0" : CheckStrUtil.getMac(context);// 没有给0
            String imei = TextUtils.isEmpty(CheckStrUtil.getImei(context)) ? "0" : CheckStrUtil.getImei(context);// 没有给0
//            nameValuePairs.add(new BasicNameValuePair("identity", identity));
            nameValuePairs.add(new BasicNameValuePair("imei", imei));
            nameValuePairs.add(new BasicNameValuePair("mac", mac));
        }
        String result = YConn.basedPost(context, YUrl.WEIXIN_DEPOSITADD, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createWeinXinWithDrawInfo(context, result);
    }

    /**
     * 联盟商家-提现
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> bankDepositAdd2(Context context, String money, String pwd, String bank_id,
                                                          String message) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("money", money));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));
        nameValuePairs.add(new BasicNameValuePair("bank_id", bank_id));
        nameValuePairs.add(new BasicNameValuePair("message", message));

        String result = YConn.basedPost(context, YUrl.BANKDEPOSITADD2, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createWithDrawInfo(context, result);
    }

    /**
     * 我的钱包-账户明细-提现
     *
     * @param context
     */
    public static List<HashMap<String, Object>> selDeposit(Context context, int currPage) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", currPage + ""));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));

        String result = YConn.basedPost(context, YUrl.SELDEPOSIT, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMyBankDepositModel(context, result);
    }


    /**
     * 我的钱包-账户明细-提现
     * 增加可选的输入参数 check t_type
     *
     * @param check   -1 为查询审核中的用户的提现
     * @param t_type  2 为查询非自动提现的记录
     * @param context
     */
    public static List<HashMap<String, Object>> selDepositFilter(Context context, int currPage,
                                                                 String check, String t_type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", currPage + ""));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));
        if (!TextUtils.isEmpty(check)) {
            nameValuePairs.add(new BasicNameValuePair("check", check));
        }
        if (!TextUtils.isEmpty(t_type)) {
            nameValuePairs.add(new BasicNameValuePair("t_type", t_type));
        }

        String result = YConn.basedPost(context, YUrl.SELDEPOSIT, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMyBankDepositModel(context, result);
    }

    /**
     * 撤销售后，取消退货/退款、换货等
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo escReturn(Context context, String id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("id", id));

        String result = YConn.basedPost(context, YUrl.ESCRETURN, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 查询各大公司物流
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static List<String> getKuaiDi(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.QUERY_GETKUAIDI, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createGetKuaiDiCompany(context, result);
    }

    /**
     * 退换货时添加物流信息
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo addLogistics(Context context, String order_code, String address_id, String express_id)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("id", order_code));
        nameValuePairs.add(new BasicNameValuePair("address_id", address_id));
        nameValuePairs.add(new BasicNameValuePair("express_id", express_id));

        String result = YConn.basedPost(context, YUrl.ADDLOGISTICS, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 退换货时---确认收到换货
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo affirmShop(Context context, String id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("id", id));

        String result = YConn.basedPost(context, YUrl.AFFIRMSHOP, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 美圈，申请管理员
     *
     * @param context
     *
     * @return
     * @throws Exception
     */
    // public static ReturnInfo applyAdmin(Context context, String circle_id)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token", YCache
    // .getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("circle_id", circle_id));
    //
    // String result = YConn.basedPost(context, YUrl.APPLYADMIN,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null : EntityFactory
    // .createRetInfo(context, result);
    // }

    /**
     * 提醒发货
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static RemainShipInfo urgesuppShipments(Context context, String order_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));

        String result = YConn.basedPost(context, YUrl.URGESUPPSHIPMENTS, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRemainShipInfo(context, result);
    }

    /**
     * 检测邮箱验证码
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo checkEmailCode(Context context, String email, String code, String type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("code", code));
        nameValuePairs.add(new BasicNameValuePair("type", type)); // 类型：1,注册.2,取回密码.3,开通通讯录

        String result = YConn.basedPost(context, YUrl.CHECK_EMAIL_AUTHCODE, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 检测手机验证码
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo checkPhoneCode(Context context, String phone, String code, String type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("phone", phone));
        nameValuePairs.add(new BasicNameValuePair("code", code));
        nameValuePairs.add(new BasicNameValuePair("type", type)); // 类型：1,注册.2,取回密码.3,开通通讯录

        String result = YConn.basedPost(context, YUrl.CHECK_PHONE_AUTHCODE, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 删除银行卡
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo delMybankCard(Context context, String id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("id", id));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.DEL_MYBANK_CARD, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 提供用户邮箱激活验证链接功能
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo get_EmailActivate_Code(Context context, String email) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_EMAILACTIVATE_CODE, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 填写推荐人
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo set_Referee(Context context, String referee) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("parent_id", referee));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.SET_REFEREE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 单个订单支付完成状态
     *
     * @param context
     *
     *
     * @return
     * @throws Exception
     */
    // public static ReturnInfo updatePayStatus(Context context, String
    // order_code)
    // throws Exception {
    //
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token", YCache
    // .getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
    // String result = YConn.basedPost(context, YUrl.UPDATA_PAYSTATUS,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null : EntityFactory
    // .createRetInfo(context, result);
    // }

    /**
     * 单个订单支付完成状态
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo updatePayStatus2(Context context, String order_code, int buy_type) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("buy_type", buy_type + ""));
        String result = YConn.basedPost(context, YUrl.UPDATA_PAYSTATUS, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 多个订单支付完成状态
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ReturnInfo updatePayStatusList(Context context, String gcode, String buy_type) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("gcode", gcode));
        nameValuePairs.add(new BasicNameValuePair("buy_type", buy_type));
        String result = YConn.basedPost(context, YUrl.UPDATA_PAYSTATUS_LIST, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 会员验证
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> memberVerify(Context context, String card_no, String password)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("card_no", card_no));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        String result = YConn.basedPost(context, YUrl.MEMBER_VERIFY, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMemberVerify(context, result);
    }

    /**
     * 查询红包详情
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> queryRedPackets(Context context, String rp_id) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("rp_id", rp_id));
        String result = YConn.basedPost(context, YUrl.QUERY_RED, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.creatRedpackets(context, result);
    }

    /**
     * 得到红包分享链接
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> getShareRedPacketLink(Context context, String rp_id) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("rp_id", rp_id));
        String result = YConn.basedPost(context, YUrl.GET_SHARERED_PACKETLINK, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.creatRedpackets(context, result);
    }

    /**
     * 获取微信性别
     */
    public static int getSex(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String access_token = context.getSharedPreferences("weixin", Context.MODE_PRIVATE).getString("access_token",
                "");
        nameValuePairs.add(new BasicNameValuePair("access_token", access_token));
        String openid = context.getSharedPreferences("weixin", Context.MODE_PRIVATE).getString("openid", "");
        nameValuePairs.add(new BasicNameValuePair("openid", openid));

        String result = YConn.basedPost(context, "https://api.weixin.qq.com/sns/userinfo", nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createWeixinInfo(context, result);
    }

    /****
     * 购物车所有数据
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static List<List<ShopCart>> queryShopCartsAll(Context context, int a) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.QUERY_SHOP_SHOPCART_ALL, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        List<List<ShopCart>> list = null;
        list = EntityFactory.createListShop_CartAll(context, result, a);

        return list;
    }

    /****
     * 运营数据统计
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static List<List<ShopCart>> yunYingshujuTongji(Context context, int type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("type", type + ""));
        long statrtTime = System.currentTimeMillis();
        String result = YConn.basedPost(context, YUrl.YUNYINGSHUJUTONGJI, nameValuePairs);
        long endTime = System.currentTimeMillis();
        if (result == null || "".equals(result)) {
            return null;
        }
        List<List<ShopCart>> list = null;
        list = EntityFactory.createYunYingshuju(context, result);
        return list;
    }

    /****
     * 运营数据统计（新）
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static void yunYingshujuTongjiNew(Context context, String type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("type", type + ""));
        YConn.basedPost(context, YUrl.YUNYINGSHUJUTONGJINEW, nameValuePairs);
    }

    /****
     * 运营数据统计 记录时长（新）
     *
     * @param context

     * @return
     * @throws Exception
     */
    public static void yunYingshujuTongjiNewDuration(Context context, String type, String timer) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("type", type + ""));
        nameValuePairs.add(new BasicNameValuePair("timer", timer));
        YConn.basedPost(context, YUrl.YUNYINGSHUJUTONGJINEWDURATION, nameValuePairs);
    }

    /**
     * 精品推荐获取分享资格
     */
    public static String getShareQua(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.GET_SHARE_QUA, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.getShareQua(context, result);
    }

    public static boolean getPingtuanTongzhi(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.PINGTUAN_TONGZHI, nameValuePairs);
        JSONObject j = new JSONObject(result);
        if (!j.has("roll") || null == j.getString("roll") || j.getString("roll").equals("null")
                || j.getString("roll").equals("")) {
            return false;
        } else {
            return ("1".equals(j.optString("roll")));
        }
    }


    public static boolean getPingtuanFirstFail(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.PINGTUAN_TONGZHI, nameValuePairs);
        JSONObject j = new JSONObject(result);
        if (!j.has("roll_count") || null == j.getString("roll_count") || j.getString("roll_count").equals("roll_count")
                || j.getString("roll_count").equals("")) {
            return false;
        } else {
            return ("1".equals(j.optString("roll_count")));
        }
    }
}
