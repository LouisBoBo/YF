package com.yssj.model;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.app.SAsyncTask;
import com.yssj.data.YDBHelper;
import com.yssj.entity.DeliveryAddress;
import com.yssj.entity.FundDetail;
import com.yssj.entity.Like;
import com.yssj.entity.MatchAttr;
import com.yssj.entity.MatchShop;
import com.yssj.entity.MealShopList;
import com.yssj.entity.MyBankCard;
import com.yssj.entity.OneBuyStaus;
import com.yssj.entity.Order;
import com.yssj.entity.OrderShop;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ReturnShop;
import com.yssj.entity.ShopCart;
import com.yssj.entity.ShopOption;
import com.yssj.entity.StockType;
import com.yssj.entity.Store;
import com.yssj.entity.UserInfo;
import com.yssj.entity.VipInfo;
import com.yssj.network.YConn;
import com.yssj.ui.activity.MainFragment;
import com.yssj.ui.activity.shopdetails.StockBean;
import com.yssj.ui.activity.shopdetails.SubmitFreeBuyShopActivty;
import com.yssj.ui.fragment.HomePage3Fragment;
import com.yssj.ui.fragment.cardselect.CardDataItem;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.ui.fragment.orderinfo.OrderInfoFragment;
import com.yssj.utils.CheckStrUtil;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.EntityFactory;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.YCache;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class ComModel2 {
    public static final String versionCode = "V1.32";
    private static YDBHelper dbHelper;
    public static String channel = "0";

    /** 邮箱注册 */
    // public static UserInfo sigup4Email(Context context, String email, String
    // pwd, String nick_name) throws Exception {
    //
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("email", email));
    // nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));
    // // nameValuePairs.add(new BasicNameValuePair("code", code));
    //
    // nameValuePairs.add(new BasicNameValuePair("nickname", nick_name));
    //
    // String result = YConn.basedPost(context, YUrl.ACCOUNT_EMAIL_SIGNUP,
    // nameValuePairs);
    //
    // // 保存token
    // // String logStringoken = JSONUtils.getString(result, Json.logintoken,
    // // "");
    // JSONObject jsonUser = JSONUtils.getJSONObject(result, Json.userinfo,
    // null);
    //
    // if (jsonUser == null) {
    // return null;
    // }
    //
    // UserInfo user = EntityFactory.createYUser(context, jsonUser);
    // // YCache.cacheUserInfo(context, logStringoken, user);
    // return user;
    // }

    /**
     * 得到帮助列表
     *
     * @return
     */
    public static HashMap<String, Object> getHelpList(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("order", "asc"));

        String result = YConn.basedPost(context, YUrl.GET_HELP_LIST, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createHelpList(context, result);
    }

    /**
     * 客服系统自动提醒热门问题
     */
    public static HashMap<String, Object> getHelpCentre(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.GET_HELP_CENTRE, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createHelpCentre(context, result);
    }

    /**
     * 检测更新
     */
    public static HashMap<String, String> checkVersion(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("type", 1 + ""));
        String result = YConn.basedPost(context, YUrl.GET_VERSION, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createVersionInfo(context, result);
    }

    public static int h5GetCoupon1(Context context) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_COUPON, nameValuePairs);
        int status = result != null ? new JSONObject(result).optInt("status") : 0;
        return status;
    }

    public static int h5GetCoupon(Context context) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("imei", CheckStrUtil.getImei(context)));
        String token = YCache.getCacheToken(context);
        if (!TextUtils.isEmpty(token)) {
            nameValuePairs.add(new BasicNameValuePair("token", token));
        }
        String result = YConn.basedPost(context, YUrl.RECORD_SHARE, nameValuePairs);
        int status = result != null ? new JSONObject(result).optInt("status") : 0;
        return status;
    }

    public static int checkIsShow(Context context) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.MAIN_CK_IS_SHOW, nameValuePairs);
        int status = result != null ? new JSONObject(result).optInt("status") : 0;
        return status;
    }

    /**
     * 用户反馈
     */
    public static ReturnInfo feedback(Context context, HashMap<String, String> mapRequest) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Iterator<Entry<String, String>> iterator = mapRequest.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));

        String result = YConn.basedPost(context, YUrl.FEEDBACK, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 得到我的买单 订单列表
     *
     * @return
     */
    public static List<Order> getOrderList(Context context, int index, int status, String change) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));
        nameValuePairs.add(new BasicNameValuePair("status", status + ""));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));
        if (!TextUtils.isEmpty(change)) {
            nameValuePairs.add(new BasicNameValuePair("change", "-1"));
        }

        String result = YConn.basedPost(context, YUrl.GET_BUY_ORDER_LIST, nameValuePairs);


        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
        } else {
            if (index == 1) {
                OrderInfoFragment.send_num = 0;
                OrderInfoFragment.free_num = 0;
                OrderInfoFragment.dayEndTime = 0L;
                OrderInfoFragment.now = 0L;
                OrderInfoFragment.current_date = "";
                OrderInfoFragment.send_num = jsonObject.optInt("send_num");
                OrderInfoFragment.free_num = jsonObject.optInt("free_num");
                OrderInfoFragment.isVip = jsonObject.optInt("isVip");
                OrderInfoFragment.maxType = jsonObject.optInt("maxType");


                OrderInfoFragment.dayEndTime = jsonObject.optLong("dayEndTime");
                OrderInfoFragment.now = jsonObject.optLong("now");
                OrderInfoFragment.current_date = jsonObject.optString("current_date");
            }


        }


        return (result == null || "".equals(result)) ? null : EntityFactory.createOrderList(context, result);
    }

    /**
     * 得卡券说明
     *
     * @return
     */
    public static HashMap<String, String> getKaquanShuoming(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.GET_KAQUAN_SHUOMING, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createKaquan(context, result);
    }

    /**
     * 获取夺宝参与号
     *
     * @return
     */
    public static HashMap<String, String> getDuobaoNumber(Context context, String order_code, int pay_type)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));// 订单编号
        nameValuePairs.add(new BasicNameValuePair("pay_type", pay_type + ""));// 支付方式
        // 1支付宝
        // 2微信

        String result = YConn.basedPost(context, YUrl.GET_DUOBAO_NUMBER, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createDuoBaoNumber(context, result);
    }

    /**
     * 获取夺宝参与号 多个订单
     *
     * @return
     */
    public static HashMap<String, String> getMultiDuobaoNumber(Context context, String g_code, int pay_type)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        nameValuePairs.add(new BasicNameValuePair("g_code", g_code));// 组合订单编号
        nameValuePairs.add(new BasicNameValuePair("pay_type", pay_type + ""));// 支付方式
        // 1支付宝
        // 2微信

        String result = YConn.basedPost(context, YUrl.GET_MULTI_DUOBAO_NUMBER, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createDuoBaoNumber(context, result);
    }

    /**
     * 得到我的买单 订单列表Modify(待用)
     *
     * @return
     */
    public static HashMap<String, Object> getOrderListModify(Context context, Integer index, int status, String change)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));
        nameValuePairs.add(new BasicNameValuePair("status", status + ""));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));
        if (!TextUtils.isEmpty(change)) {
            nameValuePairs.add(new BasicNameValuePair("change", "-1"));
        }

        String result = YConn.basedPost(context, YUrl.GET_BUY_ORDER_LIST, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createOrderListmodify(context, result);
    }

    /**
     * 得到我的售后的订单
     *
     * @return
     */
    public static List<ReturnShop> getPaybackOrderList(Context context, Integer index, int is_buy) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));
        nameValuePairs.add(new BasicNameValuePair("sort", "id"));
        nameValuePairs.add(new BasicNameValuePair("is_buy", is_buy + ""));

        String result = YConn.basedPost(context, YUrl.GET_MY_PAYBACK_LIST, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createReturnShopList(context, result);
    }

    /**
     * 得到我的买单 订单列表
     *
     * @return
     */
    public static List<Order> getMerchantBuyOrderList(Context context, Integer index, int status, String change,
                                                      String user_id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));
        nameValuePairs.add(new BasicNameValuePair("status", status + ""));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        if (!TextUtils.isEmpty(change)) {
            nameValuePairs.add(new BasicNameValuePair("change", "-1"));
        }
        String result = YConn.basedPost(context, YUrl.GET_MERCHANT_BUY_ORDER_LIST, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createOrderList(context, result);
    }

    /**
     * 得到我的卖单列表 --退换货--by luojie
     *
     * @return
     */
    public static List<Order> getSellOrderList(Context context, Integer index, int status, String change,
                                               String store_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));
        nameValuePairs.add(new BasicNameValuePair("status", status + ""));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));
        nameValuePairs.add(new BasicNameValuePair("store_code", store_code));

        if (!TextUtils.isEmpty(change)) {
            nameValuePairs.add(new BasicNameValuePair("change", "-1"));
        }

        String result = YConn.basedPost(context, YUrl.GET_MY_SELL_ORDER_LIST, nameValuePairs);

        // System.out.println("result:" + result);

        return (result == null || "".equals(result)) ? null : EntityFactory.createOrderList(context, result);
    }

    /**
     * 得到我的卖单 订单列表
     *
     * @return
     */
    public static List<Order> getOrderList(Context context, Integer index, String shop_code, int status)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
        if (shop_code != null) {
            nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        }
        nameValuePairs.add(new BasicNameValuePair("status", status + ""));
        String result = YConn.basedPost(context, YUrl.GET_ORDER_MYSHOP_LIST, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createOrderList(context, result);
    }

    /**
     * 得到我的买单 订单列表
     *
     * @return
     */
    // public static List<Order> getMyUserOrderList(Context context, Integer
    // index, int status, String change)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("page", index + ""));
    // nameValuePairs.add(new BasicNameValuePair("status", status + ""));
    // nameValuePairs.add(new BasicNameValuePair("order", "desc"));
    // if (!TextUtils.isEmpty(change)) {
    // nameValuePairs.add(new BasicNameValuePair("change", "-1"));
    // }
    //
    // String result = YConn.basedPost(context, YUrl.GET_BUY_ORDER_LIST,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createOrderList(context, result);
    // }

    /**
     * 用户反馈
     */
    public static HashMap<String, Object> getLoginsInfo(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));

        String result = YConn.basedPost(context, YUrl.LOGIN_RECORD, nameValuePairs);
        // MyLogYiFu.e("状态",result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactory.createLoginsInfos(context, result);
    }

    /**
     * 设置处重置密码
     */
    public static ReturnInfo ResetPass4Setting(Context context, String newPwd, String pwd, String token)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", token));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));
        nameValuePairs.add(new BasicNameValuePair("newPwd", MD5Tools.MD5(newPwd)));
        String result = YConn.basedPost(context, YUrl.RESET_PASS_4_SETTING, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /***
     * 同步全部数据
     *
     * @param context
     */
    public static ReturnInfo syncDatas(Context context, String sortDateStr, String tagDateStr, String typeTagDateStr,
                                       String suppLabelDataStr, String friendCircleTagDataStr, boolean bool) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("type_data", sortDateStr));
        nameValuePairs.add(new BasicNameValuePair("bool", bool + ""));
        nameValuePairs.add(new BasicNameValuePair("tag_data", tagDateStr));
        nameValuePairs.add(new BasicNameValuePair("type_tag_data", typeTagDateStr));
        nameValuePairs.add(new BasicNameValuePair("supp_label_data", suppLabelDataStr));
        nameValuePairs.add(new BasicNameValuePair("friend_circle_tag_data", friendCircleTagDataStr));

        String result = YConn.basedPost(context, YUrl.SYNC_DATAS, nameValuePairs);
        LogYiFu.e("时间戳kkkkk", result.toString());
        EntityFactory.updateSyncDatas(context, result);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /***
     * 同步数据 (发布密友圈成功 只是同步品牌表)
     *
     * @param context
     */
    public static ReturnInfo syncDatasSweet(Context context, String suppLabelDataStr, boolean bool) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("bool", bool + ""));
        nameValuePairs.add(new BasicNameValuePair("supp_label_data", suppLabelDataStr));

        String result = YConn.basedPost(context, YUrl.SYNC_DATAS, nameValuePairs);
        LogYiFu.e("时间戳kkkkk", result.toString());
        EntityFactory.updateSyncDatasSweet(context, result);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 我的钱包
     */
    public static String[] myWalletInfo(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.MY_WALLET_INFO, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");

        return (result == null || "".equals(result)) ? null : EntityFactory.createMyWalletInfo(context, result);
    }

    /**
     * 得到明细列表
     *
     * @return
     */
    public static List<FundDetail> getFundList(Context context, Integer index) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));

        String result = YConn.basedPost(context, YUrl.GET_FUND_LIST, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createFundList(context, result);
    }

    /**
     * 得到我的银行卡列表
     *
     * @return
     */
    public static List<MyBankCard> getMyCardList(Context context, Integer index) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));

        String result = YConn.basedPost(context, YUrl.GET_MY_CARD_LIST, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createCardList(context, result);
    }

    /**
     * 得到我的卡券列表-----似乎已废弃
     *
     * @return
     */
    public static List<HashMap<String, Object>> getMyCouponList(Context context, Integer index) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));

        String result = YConn.basedPost(context, YUrl.GET_COUPON_LIST, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMyCouponList(context, result);
    }

    /**
     * 得到我的卡券列表
     *
     * @return
     */
    public static List<HashMap<String, Object>> getMyCouponList(Context context, Integer index, String maxormin,
                                                                String is_use, String c_cond) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));
        nameValuePairs.add(new BasicNameValuePair("order", "asc"));
        nameValuePairs.add(new BasicNameValuePair("sort", "is_use ASC,C_LAST_TIME ASC,c_price"));
        if (!TextUtils.isEmpty(maxormin)) {
            nameValuePairs.add(new BasicNameValuePair("maxormin", maxormin)); // 符号----不选为查询全部的卡券,给”>”为查询未过期的,给”<”为查询过期的
        }
        if (!TextUtils.isEmpty(is_use)) {
            nameValuePairs.add(new BasicNameValuePair("is_use", is_use)); // 是否使用------不给为查询全部,1是未使用的2是已使用的
            // (如果要查询过期的,这个条件和前一个字段必给)
        }
        if (!TextUtils.isEmpty(c_cond)) {
            nameValuePairs.add(new BasicNameValuePair("c_cond", c_cond)); // 最低使用条件
            // ----可以用这个条件来筛选可以使用的优惠券,给订单的总金额,来筛选这个订单可以使用的优惠券,(在订单使用这里,带上了这个就必须给maxormin=”>”和is_user=1这2个参数)这里查询我后台就不做判断了
        }

        String result = YConn.basedPost(context, YUrl.QUERYBYPAGE, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMyCouponListNew(context, result);
    }

    /**
     * 得到我的卡券总数-----似乎已废弃
     *
     * @return
     */
    public static int getMyCoupontCount(Context context, Integer index) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));

        String result = YConn.basedPost(context, YUrl.GET_COUPON_LIST, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMyCouponCount(context, result);
    }

    /**
     * 得到我的最爱列表
     *
     * @return
     */
    public static List<Like> getMyFavourList(Context context, Integer index, String del) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
        if (!TextUtils.isEmpty(del)) {
            nameValuePairs.add(new BasicNameValuePair("is_del", del));
        }
        String result = YConn.basedPost(context, YUrl.GET_MY_FAVOUR_LIST, nameValuePairs);

        // System.out.println("reslut:" + result);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMyFavourList(context, result);
    }

    /**
     * 得到我的最爱列表---发布用
     *
     * @return
     */
    public static List<HashMap<String, Object>> getMyFavourListFabu(Context context, Integer index, String del) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
        if (!TextUtils.isEmpty(del)) {
            nameValuePairs.add(new BasicNameValuePair("is_del", del));
        }
        String result = YConn.basedPost(context, YUrl.GET_MY_FAVOUR_LIST, nameValuePairs);

        // System.out.println("reslut:" + result);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMyLikeFabu(context, result);
    }

    /**
     * 个人中心统计
     *
     * @return
     */
    public static HashMap<String, Object> getPerosnCenterCount(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.PERSON_CENTER_COUNT, nameValuePairs);
        LogYiFu.e("个人中心数据", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactory.createPersonCenterCount(context, result);
    }

    /**
     * 我的积分
     *
     * @return
     */
    public static HashMap<String, List<HashMap<String, String>>> getMyIntegralInfo(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.GET_MY_INTEGRAL_INFO, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createIntegralInfo(context, result);
    }

    /**
     * 每日积分签到
     */
    public static HashMap<String, Object> dailySign(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.DAILY_SIGN, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSignRetInfo(context, result);
    }

    /**
     * 用原密码修改支付密码
     */
    public static ReturnInfo setPayPassByLoginPass(Context context, String loginPass, String payPass) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("old_pwd", MD5Tools.MD5(loginPass)));
        nameValuePairs.add(new BasicNameValuePair("payment_pwd", MD5Tools.MD5(payPass)));
        String result = YConn.basedPost(context, YUrl.RESET_PAY_PAYPASS_BY_LOGINPASS, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 用手机修改支付密码
     */
    public static ReturnInfo setPayPassByPhone(Context context, String phoneNo, String code, String payPass)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("phone", phoneNo));
        nameValuePairs.add(new BasicNameValuePair("code", code));
        nameValuePairs.add(new BasicNameValuePair("payment_pwd", MD5Tools.MD5(payPass)));
        String result = YConn.basedPost(context, YUrl.RESET_PAY_PAYPASS_BY_PHONE, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 用邮箱修改支付密码
     */
    public static ReturnInfo setPayPassByEmail(Context context, String email, String code, String payPass)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("code", code));
        nameValuePairs.add(new BasicNameValuePair("payment_pwd", MD5Tools.MD5(payPass)));
        String result = YConn.basedPost(context, YUrl.RESET_PAY_PAYPASS_BY_EMAIL, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /** 通过ID获取商品列表 */
    /**
     * @param context
     * @param index
     * @param id
     * @param level
     * @param typename
     * @param pageSize
     * @param isHotSale 专题详情下面的热门推荐专用
     * @return
     */
    public static List<HashMap<String, Object>> getProductList1(Context context, String index, String id, String level,
                                                                String typename, int pageSize, boolean isHotSale) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        if (isHotSale) {
            nameValuePairs.add(new BasicNameValuePair("notType", "true"));
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "actual_sales"));
        } else {

            nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
            nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize + ""));
            nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

            // nameValuePairs.add(new BasicNameValuePair("shop_type_id",
            // id+""));

            if (id.equals("8")) { // 上新
                nameValuePairs.add(new BasicNameValuePair("type1", "8"));
                nameValuePairs.add(new BasicNameValuePair("type_name", "上新"));
            } else {

                if (level.equals("1")) {
                    nameValuePairs.add(new BasicNameValuePair("type1", id + ""));
                    nameValuePairs.add(new BasicNameValuePair("type_name", typename + ""));
                } else if (level.equals("2")) {
                    nameValuePairs.add(new BasicNameValuePair("type2", id + ""));
                } else if (level.equals("3")) {
                    nameValuePairs.add(new BasicNameValuePair("type3s", id + ""));
                    nameValuePairs.add(new BasicNameValuePair("notType", "true"));

                } else if (level.equals("4")) {
                    nameValuePairs.add(new BasicNameValuePair("type4", id + ""));

                }

            }
        }

        String result = YConn.basedPost(context, YUrl.GET_PRODUCT_LIST, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createProductList(context, result);
    }

    public static List<HashMap<String, Object>> getSignShop(Context context, String index
            , String pinJievalue, String is_new, String is_hot, String order_by_price)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        if ((YJApplication.isLogined || YJApplication.instance.isLoginSucess())) {
            nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        }
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pager.pageSize", "30"));

        if (null != is_new) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "audit_time"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
        }
        if (null != is_hot) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "virtual_sales"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
        }
        if (null != order_by_price) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "shop_se_price"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", order_by_price));
        }

        if (pinJievalue == null || pinJievalue.length() == 0) {

        } else {
            try {
                String[] valueAll = pinJievalue.split("&");
                for (int i = 0; i < valueAll.length; i++) {
                    String[] valuePin = valueAll[i].split("=");
                    nameValuePairs.add(new BasicNameValuePair(valuePin[0], valuePin[1]));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        String url = (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) ?
                YUrl.GET_PRODUCT_LIST : YUrl.GET_PRODUCT_LIST2;
        String result = YConn.basedPost(context, url, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createProductList(context, result);
    }


    public static List<HashMap<String, Object>> getHomePage3ShopList(Context context, String index
            , String pinJievalue, String is_new, String is_hot)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        if ((YJApplication.isLogined || YJApplication.instance.isLoginSucess())) {
            nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        }
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pager.pageSize", "30"));

//        if (null != is_new) {
//            nameValuePairs.add(new BasicNameValuePair("pager.sort", "audit_time"));
//            nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
//        }
//        if (null != is_hot) {
//            nameValuePairs.add(new BasicNameValuePair("pager.sort", "virtual_sales"));
//            nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
//        }
//        if (null != order_by_price) {
//            nameValuePairs.add(new BasicNameValuePair("pager.sort", "shop_se_price"));
//            nameValuePairs.add(new BasicNameValuePair("pager.order", order_by_price));
//        }

//        if (pinJievalue == null || pinJievalue.length() == 0) {
//
//        } else {
//            try {
//                String[] valueAll = pinJievalue.split("&");
//                for (int i = 0; i < valueAll.length; i++) {
//                    String[] valuePin = valueAll[i].split("=");
//                    nameValuePairs.add(new BasicNameValuePair(valuePin[0], valuePin[1]));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }

        String url = YUrl.HOMEPAGE3_SHOPLIST;
        switch (HomePage3Fragment.freeOrderPage) {
            case 1:
                url = YUrl.HOMEPAGE3_SHOPLIST_FIRST__DIAMOND;
                break;
            case 2:
                url = YUrl.HOMEPAGE3_SHOPLIST;
                break;
            case 3:
                url = YUrl.HOMEPAGE3_SHOPLIST;
                break;
        }
        String result = YConn.basedPost(context, url, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createProductList(context, result);
    }

    /**
     * 通过ID获取店铺美衣商品列表
     */
    public static List<HashMap<String, Object>> getProductListBeautyShop(Context context, String index, String id,
                                                                         String level, String typename, int pageSize) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize + ""));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        // nameValuePairs.add(new BasicNameValuePair("shop_type_id", id+""));
        if (level.equals("1")) {
            nameValuePairs.add(new BasicNameValuePair("type1", id));
            nameValuePairs.add(new BasicNameValuePair("type_name", typename + ""));
        }
        String result = YConn.basedPost(context, YUrl.GET_SHOP_BEAUTY, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createProductList(context, result);
    }

    /**
     * 个人中心邀请好友
     */
    public static HashMap<String, Object> getInviteFriend(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.GET_INVITE_FRIEND, nameValuePairs);

        return (result == (String) null || "".equals(result)) ? null
                : EntityFactory.createInviteFriend(context, result);
    }

    /**
     * 通过ID获取商品列表
     */
    public static List<HashMap<String, Object>> getProductListUnLogin(Context context, String index, String id,
                                                                      String level, String typename, int pageSize, boolean siHotSale) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));

        if (siHotSale) {
            nameValuePairs.add(new BasicNameValuePair("notType", "true"));
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "actual_sales"));
        } else {
            nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
            nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize + ""));
            // nameValuePairs.add(new BasicNameValuePair("token", YCache
            // .getCacheToken(context)));
            // nameValuePairs.add(new BasicNameValuePair("shop_type_id",
            // id+""));
            if (level.equals("1")) {
                nameValuePairs.add(new BasicNameValuePair("type1", id + ""));
                nameValuePairs.add(new BasicNameValuePair("type_name", typename + ""));
            } else if (level.equals("2")) {
                nameValuePairs.add(new BasicNameValuePair("type2", id + ""));
            } else if (level.equals("3")) {
                nameValuePairs.add(new BasicNameValuePair("type3s", id + ""));
                nameValuePairs.add(new BasicNameValuePair("notType", "true"));

            } else if (level.equals("4")) {
                nameValuePairs.add(new BasicNameValuePair("type4", id + ""));
            }
        }

        String result = YConn.basedPost(context, YUrl.GET_PRODUCT_LIST2, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createProductList(context, result);
    }

    /**
     * 通过ID获取商品列表
     */
    public static List<HashMap<String, Object>> getProductListBySearch(Context context, String index, String id,
                                                                       String level, int pageSize, String is_new, String order_by_price, boolean notType, String level2Id)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize + ""));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("notType", notType + ""));
        // nameValuePairs.add(new BasicNameValuePair("shop_type_id", id+""));
        if (level.equals("1")) {
            nameValuePairs.add(new BasicNameValuePair("type1", id + ""));
        } else if (level.equals("2")) {
            nameValuePairs.add(new BasicNameValuePair("type2", id + ""));

        } else if (level.equals("3")) {
            nameValuePairs.add(new BasicNameValuePair("type3s", id + ""));
            nameValuePairs.add(new BasicNameValuePair("notType", "true"));

        } else if (level.equals("4")) {
            nameValuePairs.add(new BasicNameValuePair("type4", id + ""));
        }

        if (null != is_new) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "audit_time"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
        }
        if (null != order_by_price) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "shop_se_price"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", order_by_price));
        }
        nameValuePairs.add(new BasicNameValuePair("type2", level2Id + ""));

        String result = YConn.basedPost(context, YUrl.GET_PRODUCT_LIST, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createProductList(context, result);
    }

    /**
     * 通过ID获取商品列表
     */
    public static List<HashMap<String, Object>> getProductList(Context context, String index, String id, String level,
                                                               int pageSize, String is_new, String order_by_price, boolean notType) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize + ""));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("notType", notType + ""));
        // nameValuePairs.add(new BasicNameValuePair("shop_type_id", id+""));
        if (level.equals("1")) {
            nameValuePairs.add(new BasicNameValuePair("type1", id + ""));
        } else if (level.equals("2")) {
            nameValuePairs.add(new BasicNameValuePair("type2", id + ""));

        } else if (level.equals("3")) {
            nameValuePairs.add(new BasicNameValuePair("type3s", id + ""));
            nameValuePairs.add(new BasicNameValuePair("notType", "true"));

        } else if (level.equals("4")) {
            nameValuePairs.add(new BasicNameValuePair("type4", id + ""));
        }

        if (null != is_new) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "audit_time"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
        }
        if (null != order_by_price) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "shop_se_price"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", order_by_price));
        }
        String result = YConn.basedPost(context, YUrl.GET_PRODUCT_LIST, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createProductList(context, result);
    }

    /**
     * 通过ID获取商品列表
     */
    public static List<HashMap<String, Object>> getProductList2UnLoginBySearch(Context context, String index, String id,
                                                                               String level, int pageSize, String is_new, String order_by_price, boolean notType, String level2Id)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize + ""));
        nameValuePairs.add(new BasicNameValuePair("notType", notType + ""));
        // nameValuePairs.add(new BasicNameValuePair("token", YCache
        // .getCacheToken(context)));
        // nameValuePairs.add(new BasicNameValuePair("shop_type_id", id+""));
        if (level.equals("1")) {
            nameValuePairs.add(new BasicNameValuePair("type1", id + ""));
        } else if (level.equals("2")) {
            nameValuePairs.add(new BasicNameValuePair("type2", id + ""));
        } else if (level.equals("3")) {
            nameValuePairs.add(new BasicNameValuePair("type3s", id + ""));
            nameValuePairs.add(new BasicNameValuePair("notType", "true"));
        } else if (level.equals("4")) {
            nameValuePairs.add(new BasicNameValuePair("type4", id + ""));
        }
        if (null != is_new) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "audit_time"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
        }
        if (null != order_by_price) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "shop_se_price"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", order_by_price));
        }
        nameValuePairs.add(new BasicNameValuePair("type2", level2Id + ""));
        String result = YConn.basedPost(context, YUrl.GET_PRODUCT_LIST2, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createProductList(context, result);
    }

    /**
     * 通过ID获取商品列表
     */
    public static List<HashMap<String, Object>> getProductList2UnLogin(Context context, String index, String id,
                                                                       String level, int pageSize, String is_new, String order_by_price, boolean notType) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize + ""));
        nameValuePairs.add(new BasicNameValuePair("notType", notType + ""));
        // nameValuePairs.add(new BasicNameValuePair("token", YCache
        // .getCacheToken(context)));
        // nameValuePairs.add(new BasicNameValuePair("shop_type_id", id+""));
        if (level.equals("1")) {
            nameValuePairs.add(new BasicNameValuePair("type1", id + ""));
        } else if (level.equals("2")) {
            nameValuePairs.add(new BasicNameValuePair("type2", id + ""));
        } else if (level.equals("3")) {
            nameValuePairs.add(new BasicNameValuePair("type3s", id + ""));
            nameValuePairs.add(new BasicNameValuePair("notType", "true"));
        } else if (level.equals("4")) {
            nameValuePairs.add(new BasicNameValuePair("type4", id + ""));
        }
        if (null != is_new) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "audit_time"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
        }
        if (null != order_by_price) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "shop_se_price"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", order_by_price));
        }
        String result = YConn.basedPost(context, YUrl.GET_PRODUCT_LIST2, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createProductList(context, result);
    }

    public static String getQRCode(Context context, String shop_code, String store_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("store_code", store_code));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_QRCODE, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        } else {
            JSONObject jsonObj = new JSONObject(result);
            // Log.i("TAG", jsonObj.toString());
            JSONObject object = jsonObj.getJSONObject("store_shop");
            if (object == null) {
                return null;
            } else if (object.has("qr_pic")) {
                // LogYiFu.e("TAG", object.toString());
                return object.getString("qr_pic");
            } else {
                return null;
            }
        }

    }

    public static void saveQRCode(final Context context, final String shop_code) {
        new SAsyncTask<Void, Void, String>((FragmentActivity) context) {

            @Override
            protected String doInBackground(Void... params) {
                try {
                    String qrCodeStr = getQRCode(context, shop_code, YCache.getCacheStoreSafe(context).getS_code());
                    URL url = new URL(YUrl.imgurl + qrCodeStr);
                    // LogYiFu.e("TAG", "==" + (YUrl.imgurl + qrCodeStr));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(30 * 1000);
                    connection.setDoInput(true);
                    connection.setRequestMethod("GET");
                    connection.connect();
                    if (connection.getResponseCode() == 200) {
                        InputStream is = connection.getInputStream();
                        File file = new File(YConstance.savePicPath, MD5Tools.md5("qrcode") + ".jpg");
                        if (file.exists()) {
                            file.delete();
                        }
                        FileOutputStream fos = new FileOutputStream(file);
                        byte[] data = new byte[1024];
                        int len = 0;
                        while ((len = is.read(data)) != -1) {
                            fos.write(data, 0, len);
                        }
                        is.close();
                        fos.close();
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, String result) {
                super.onPostExecute(context, result);

            }

        }.execute();
    }

    /** 查询数据库获取商品列表，我的足迹 */
    /*
     * public static List<HashMap<String, Object>> getProductListFromdb( Context
     * context, String index, String id) throws Exception { dbHelper = new
     * YDBHelper(context); int currentPage = Integer.parseInt(index);
     * currentPage--; System.out.println("currentPage值：" + currentPage); String
     * sql =
     * "select id , def_pic, shop_code, shop_se_price from foot_print limit 10 offset "
     * + currentPage; return dbHelper.queryToObject(sql); }
     */

    /*
     * public static String getMineInfoData(Context context) { YDBHelper help =
     * new YDBHelper(context); String sql =
     * "select count(*) as counts from foot_print"; List<String> list =
     * help.queryToSimpleList(sql); return list.isEmpty() ? "0" : list.get(0); }
     */

    // /** 通过关键字获取商品列表 */

    /**
     * 新的分类搜索 （登录）
     */
    public static List<HashMap<String, Object>> getProductListByWord(Context context, String index, String words,
                                                                     int pageSize, String is_hot, String is_new, String order_by_price, boolean notType, String class_id)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize + ""));
        nameValuePairs.add(new BasicNameValuePair("notType", notType + ""));
        if (notType) {// 输入框搜索时候 才有shop_name 否则不传 shop_name和 class_id 不能同时传参
            nameValuePairs.add(new BasicNameValuePair("shop_name", words));
        }
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        if (class_id != null) {// 类目标签搜索时候 才有class_id 否则不传 shop_name和 class_id
            // 不能同时传参
            nameValuePairs.add(new BasicNameValuePair("class_id", class_id + ""));
        }
        if (null != is_new) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "audit_time"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
        }
        if (null != is_hot) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "virtual_sales"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
        }
        if (null != order_by_price) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "shop_se_price"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", order_by_price));
        }
        String result = YConn.basedPost(context, YUrl.GET_PRODUCT_LIST, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createProductList(context, result);
    }

    /**
     * 新的分类搜索 （未登录）
     */
    public static List<HashMap<String, Object>> getProductListByWord2(Context context, String index, String words,
                                                                      int pageSize, String is_hot, String is_new, String order_by_price, boolean notType, String class_id)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));

        nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize + ""));
        nameValuePairs.add(new BasicNameValuePair("notType", notType + ""));
        if (notType) {// 输入框搜索时候 才有shop_name 否则不传 shop_name和 class_id 不能同时传参
            nameValuePairs.add(new BasicNameValuePair("shop_name", words));
        }
        if (class_id != null) {// 类目标签搜索时候 才有class_id 否则不传 shop_name和 class_id
            // 不能同时传参
            nameValuePairs.add(new BasicNameValuePair("class_id", class_id + ""));
        }

        if (null != is_new) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "audit_time"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
        }
        if (null != is_hot) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "virtual_sales"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
        }
        if (null != order_by_price) {
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "shop_se_price"));
            nameValuePairs.add(new BasicNameValuePair("pager.order", order_by_price));
        }
        // nameValuePairs.add(new BasicNameValuePair("token", YCache
        // .getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_PRODUCT_LIST2, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createProductList(context, result);
    }

    /**
     * 获取积分商城商品列表
     */
    public static List<HashMap<String, Object>> getInteProdList(Context context, String index) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
        String result = YConn.basedPost(context, YUrl.GET_INTE_PROD_LIST, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createIntegralProd(context, result);
    }

    /**
     * 获取地址列表
     */
    public static List<DeliveryAddress> getDeliverAddr(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_DELIVERY_ADDR, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createDeliverAddrList(context, result);
    }

    /**
     * 获取默认地址
     */
    public static HashMap<String, String> getDefaultDeliverAddr(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_DEFAULT_DELIVERY_ADDR, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null
                : EntityFactory.createDefaultDeliverAddrList(context, result);
    }

    /**
     * 添加收货地址
     */
    public static ReturnInfo addReceiverAddr(Context context, String province, String city, String area, String street,
                                             String receiverName, String receiverPhone, String zipCode, String dtaddress, int is_default)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("province", province));
        nameValuePairs.add(new BasicNameValuePair("city", city));
        nameValuePairs.add(new BasicNameValuePair("area", area));
        nameValuePairs.add(new BasicNameValuePair("street", street));

        nameValuePairs.add(new BasicNameValuePair("address", dtaddress));
        nameValuePairs.add(new BasicNameValuePair("consignee", receiverName));
        nameValuePairs.add(new BasicNameValuePair("phone", receiverPhone));
        nameValuePairs.add(new BasicNameValuePair("postcode", zipCode));
        nameValuePairs.add(new BasicNameValuePair("is_default", is_default + ""));
        String result = YConn.basedPost(context, YUrl.ADD_DELIVERY_ADDR, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 修改收货地址
     */
    public static ReturnInfo updateReceiverAddr(Context context, String province, String city, String area,
                                                String street, String receiverName, String receiverPhone, String zipCode, String dtaddress, int id,
                                                int is_default) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("province", province));
        nameValuePairs.add(new BasicNameValuePair("city", city));
        nameValuePairs.add(new BasicNameValuePair("area", area));

        if (null == street)
            nameValuePairs.add(new BasicNameValuePair("street", 0 + ""));
        else
            nameValuePairs.add(new BasicNameValuePair("street", street));

        nameValuePairs.add(new BasicNameValuePair("address", dtaddress));
        nameValuePairs.add(new BasicNameValuePair("consignee", receiverName));
        nameValuePairs.add(new BasicNameValuePair("phone", receiverPhone));
        nameValuePairs.add(new BasicNameValuePair("postcode", zipCode));
        nameValuePairs.add(new BasicNameValuePair("id", id + ""));
        nameValuePairs.add(new BasicNameValuePair("is_default", is_default + ""));

        String result = YConn.basedPost(context, YUrl.UPDATE_DELIVERY_ADDR, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 删除我的收货地址
     */
    public static ReturnInfo deleteReceiverAddr(Context context, int id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("id", id + ""));

        String result = YConn.basedPost(context, YUrl.DELETE_DELIVERY_ADDR, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 设置我的喜好
     */
    public static ReturnInfo setMyHobby(Context context, String hobby) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("hobby", hobby));
        if (null == YCache.getCacheUser(context).getImei() || "".equals(YCache.getCacheUser(context).getImei())) {
            nameValuePairs.add(new BasicNameValuePair("imei", CheckStrUtil.getImei(context)));
        }
        String result = YConn.basedPost(context, YUrl.SET_MY_HOBBY, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");
        LogYiFu.e("店铺数据", result.toString());
        EntityFactory.createStore(context, result);// 保存店铺数据
        EntityFactory.createUserInfo(context, result);// 保存用户信息
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 设置我的喜好
     */
    public static ReturnInfo setMyHobbyKaiDian(Context context, String hobby) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("hobby", hobby));
        if (null == YCache.getCacheUser(context).getImei() || "".equals(YCache.getCacheUser(context).getImei())) {
            nameValuePairs.add(new BasicNameValuePair("imei", CheckStrUtil.getImei(context)));
        }
        String result = YConn.basedPost(context, YUrl.SET_MY_HOBBY, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");
        LogYiFu.e("店铺数据", result.toString());
        EntityFactory.createStore(context, result);// 保存店铺数据
        EntityFactory.createUserInfo(context, result);// 保存用户信息
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfoKaiDian(context, result);
    }

    /**
     * 通过关键字获取商品列表
     */
    public static HashMap<String, Object> getIntegralGoodById(Context context, String code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("code", code));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_INTEGRAL_GOOD_DETAIL, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);
        // return null;
        return (result == null || "".equals(result)) ? null : EntityFactory.createIntegralGood(context, result);
    }

    /**
     * 店铺管理 获取店铺信息
     */
    public static HashMap<String, Object> getMyShpInfo(Context context, String s_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("s_code", s_code));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_MY_SHOP_INFO, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);
        // return null;
        return (result == null || "".equals(result)) ? null : EntityFactory.createMyShopInfo(context, result);
    }

    /**
     * 提交订单
     */
    public static HashMap<String, Object> submitOrder(Context context, String message, String store_code,
                                                      int address_id, Integer coupon_id, String integral, List<HashMap<String, String>> OrderShop,
                                                      String orderToken, String voucherRes) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("message", message));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("store_code", store_code));
        nameValuePairs.add(new BasicNameValuePair("address_id", address_id + ""));

        // channel=SharedPreferencesUtil.getStringData(context, "tags","");
        LogYiFu.e("abc", channel);
        // nameValuePairs.add(new BasicNameValuePair("channel", channel));

        nameValuePairs.add(new BasicNameValuePair("orderToken", orderToken));
        // //
        nameValuePairs.add(new BasicNameValuePair("voucherRes", voucherRes));
        // /
        if (null != coupon_id) {
            nameValuePairs.add(new BasicNameValuePair("coupon_id", coupon_id + ""));
        }

        if (!TextUtils.isEmpty(integral)) {
            nameValuePairs.add(new BasicNameValuePair("integral_num", integral));
        }
        String orderString = JSONArray.toJSONString(OrderShop);
        nameValuePairs.add(new BasicNameValuePair("result", JSONArray.toJSONString(OrderShop).toString()));
        String result = YConn.basedPost(context, YUrl.SUBMIT_ORDER, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);
        // return null;
        return (result == null || "".equals(result)) ? null : EntityFactory.addOrder(context, result);
    }

    public static HashMap<String, Object> submitShopcartOrders(Context context, HashMap<Integer, String> mapMsg,
                                                               List<ShopCart> carts, Integer integral_num, int address_id, int couponid, HashMap<String, Integer> mMapTen,
                                                               HashMap<String, Integer> mMapFive, HashMap<String, Integer> mMapTwo, HashMap<String, Integer> mMapOne,
                                                               int is_be) throws Exception {
        Iterator<Entry<Integer, String>> iterator = mapMsg.entrySet().iterator();
        StringBuffer sbMsg = new StringBuffer();
        StringBuffer sbCartId = new StringBuffer();
        StringBuffer sbResult = new StringBuffer();
        while (iterator.hasNext()) {

            Entry<Integer, String> entry = iterator.next();
            sbMsg.append(entry.getKey()).append("^").append(entry.getValue());
            if (iterator.hasNext()) {
                sbMsg.append(",");
            }

        }
        for (int i = 0; i < carts.size(); i++) {
            ShopCart cart = carts.get(i);
            sbCartId.append(cart.getId());
            if (i != carts.size() - 1) {
                sbCartId.append(",");
            }
            sbResult.append(cart.getShop_num()).append("^").append(cart.getShop_code()).append("^")
                    .append(cart.getStock_type_id());
            if (mMapTen.get("" + cart.getStock_type_id()) > 0) {
                sbResult.append("^").append("10:").append(mMapTen.get("" + cart.getStock_type_id()));
            }
            if (mMapFive.get("" + cart.getStock_type_id()) > 0) {
                if (mMapTen.get("" + cart.getStock_type_id()) > 0) {
                    sbResult.append("-").append("5:").append(mMapFive.get("" + cart.getStock_type_id()));
                } else {
                    sbResult.append("^").append("5:").append(mMapFive.get("" + cart.getStock_type_id()));
                }
            }
            if (mMapTwo.get("" + cart.getStock_type_id()) > 0) {
                if (mMapTen.get("" + cart.getStock_type_id()) > 0 || mMapFive.get("" + cart.getStock_type_id()) > 0) {
                    sbResult.append("-").append("2:").append(mMapTwo.get("" + cart.getStock_type_id()));
                } else {
                    sbResult.append("^").append("2:").append(mMapTwo.get("" + cart.getStock_type_id()));
                }
            }
            if (mMapOne.get("" + cart.getStock_type_id()) > 0) {
                if (mMapTen.get("" + cart.getStock_type_id()) > 0 || mMapFive.get("" + cart.getStock_type_id()) > 0
                        || mMapTwo.get("" + cart.getStock_type_id()) > 0) {
                    sbResult.append("-").append("1:").append(mMapOne.get("" + cart.getStock_type_id()));
                } else {
                    sbResult.append("^").append("1:").append(mMapOne.get("" + cart.getStock_type_id()));
                }
            }

            if (mMapTen.get("" + cart.getStock_type_id()) <= 0 && mMapFive.get("" + cart.getStock_type_id()) <= 0
                    && mMapTwo.get("" + cart.getStock_type_id()) <= 0
                    && mMapOne.get("" + cart.getStock_type_id()) <= 0) {
                sbResult.append("^").append("0");
            }
            if (i != carts.size() - 1) {
                sbResult.append(",");
            }
        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("message", sbMsg.toString()));
        nameValuePairs.add(new BasicNameValuePair("cartIds", sbCartId.toString()));
        nameValuePairs.add(new BasicNameValuePair("integral_num", integral_num + ""));
        nameValuePairs.add(new BasicNameValuePair("result", sbResult.toString()));
        nameValuePairs.add(new BasicNameValuePair("address_id", address_id + ""));
        nameValuePairs.add(new BasicNameValuePair("couponid", couponid + ""));
        nameValuePairs.add(new BasicNameValuePair("is_be", is_be + ""));
        // channel=SharedPreferencesUtil.getStringData(context, "tags","");
        LogYiFu.e("abc", channel);
        // nameValuePairs.add(new BasicNameValuePair("channel", channel));

        String result = YConn.basedPost(context, YUrl.SUBMIT_MULTI_CART_ORDER, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.addOrder(context, result);
    }


    public static HashMap<String, Object> submitShopcartOrdersFreeBuy(VipInfo vipInfo, Context context, HashMap<Integer, String> mapMsg,
                                                                      List<ShopCart> carts, Integer integral_num, int address_id, int couponid, HashMap<String, Integer> mMapTen,
                                                                      HashMap<String, Integer> mMapFive, HashMap<String, Integer> mMapTwo, HashMap<String, Integer> mMapOne,
                                                                      int freeOrderPage, int freeBuyType) throws Exception {


        Iterator<Entry<Integer, String>> iterator = mapMsg.entrySet().iterator();
        StringBuffer sbMsg = new StringBuffer();
        StringBuffer sbCartId = new StringBuffer();
        StringBuffer sbResult = new StringBuffer();
        String shop_code = "";
        while (iterator.hasNext()) {

            Entry<Integer, String> entry = iterator.next();
            sbMsg.append(entry.getKey()).append("^").append(entry.getValue());
            if (iterator.hasNext()) {
                sbMsg.append(",");
            }

        }
        for (int i = 0; i < carts.size(); i++) {
            ShopCart cart = carts.get(i);
            sbCartId.append(cart.getId());
            if (i != carts.size() - 1) {
                sbCartId.append(",");
            }
            sbResult.append(cart.getShop_num()).append("^").append(cart.getShop_code()).append("^")
                    .append(cart.getStock_type_id());

            if (i == 0) {
                shop_code = cart.getShop_code();
            }


            if (mMapTen.get("" + cart.getStock_type_id()) > 0) {
                sbResult.append("^").append("10:").append(mMapTen.get("" + cart.getStock_type_id()));
            }
            if (mMapFive.get("" + cart.getStock_type_id()) > 0) {
                if (mMapTen.get("" + cart.getStock_type_id()) > 0) {
                    sbResult.append("-").append("5:").append(mMapFive.get("" + cart.getStock_type_id()));
                } else {
                    sbResult.append("^").append("5:").append(mMapFive.get("" + cart.getStock_type_id()));
                }
            }
            if (mMapTwo.get("" + cart.getStock_type_id()) > 0) {
                if (mMapTen.get("" + cart.getStock_type_id()) > 0 || mMapFive.get("" + cart.getStock_type_id()) > 0) {
                    sbResult.append("-").append("2:").append(mMapTwo.get("" + cart.getStock_type_id()));
                } else {
                    sbResult.append("^").append("2:").append(mMapTwo.get("" + cart.getStock_type_id()));
                }
            }
            if (mMapOne.get("" + cart.getStock_type_id()) > 0) {
                if (mMapTen.get("" + cart.getStock_type_id()) > 0 || mMapFive.get("" + cart.getStock_type_id()) > 0
                        || mMapTwo.get("" + cart.getStock_type_id()) > 0) {
                    sbResult.append("-").append("1:").append(mMapOne.get("" + cart.getStock_type_id()));
                } else {
                    sbResult.append("^").append("1:").append(mMapOne.get("" + cart.getStock_type_id()));
                }
            }

            if (mMapTen.get("" + cart.getStock_type_id()) <= 0 && mMapFive.get("" + cart.getStock_type_id()) <= 0
                    && mMapTwo.get("" + cart.getStock_type_id()) <= 0
                    && mMapOne.get("" + cart.getStock_type_id()) <= 0) {
                sbResult.append("^").append("0");
            }
            if (i != carts.size() - 1) {
                sbResult.append(",");
            }
        }
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("message", sbMsg.toString()));
        nameValuePairs.add(new BasicNameValuePair("cartIds", sbCartId.toString()));
        nameValuePairs.add(new BasicNameValuePair("integral_num", integral_num + ""));
        nameValuePairs.add(new BasicNameValuePair("result", sbResult.toString()));
        nameValuePairs.add(new BasicNameValuePair("freeOrderPage", freeOrderPage + ""));
        nameValuePairs.add(new BasicNameValuePair("freeBuyType", freeBuyType + ""));


        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));

        nameValuePairs.add(new BasicNameValuePair("address_id", address_id + ""));


        nameValuePairs.add(new BasicNameValuePair("t", "1"));
        nameValuePairs.add(new BasicNameValuePair("vip_type", vipInfo.getVip_type() + ""));
        nameValuePairs.add(new BasicNameValuePair("page3", SubmitFreeBuyShopActivty.page3 + ""));

        String httpUrl = YUrl.SUBMIT_FREE_BUY_ORDER;


//        if(freeBuyType == 1 ){
//            httpUrl = YUrl.VIP_SUBMIT_SHARE_FREE_BUY_ORDER;
//        }


        String result = YConn.basedPost(context, httpUrl, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.addOrder(context, result);


    }


    public static HashMap<String, Object> submitNewMealtOrders(String sbResult, Context context, HashMap<Integer, String> mapMsg,
                                                               List<ShopCart> carts, Integer integral_num, int address_id, int couponid, HashMap<String, Integer> mMapTen,
                                                               HashMap<String, Integer> mMapFive, HashMap<String, Integer> mMapTwo, HashMap<String, Integer> mMapOne,
                                                               int is_be) throws Exception {
        Iterator<Entry<Integer, String>> iterator = mapMsg.entrySet().iterator();
        StringBuffer sbMsg = new StringBuffer();
        StringBuffer sbCartId = new StringBuffer();
//        StringBuffer sbResult = new StringBuffer();
        while (iterator.hasNext()) {

            Entry<Integer, String> entry = iterator.next();
            sbMsg.append(entry.getKey()).append("^").append(entry.getValue());
            if (iterator.hasNext()) {
                sbMsg.append(",");
            }

        }
//        for (int i = 0; i < carts.size(); i++) {
//            ShopCart cart = carts.get(i);
//            sbCartId.append(cart.getId());
//            if (i != carts.size() - 1) {
//                sbCartId.append(",");
//            }
//            sbResult.append(cart.getShop_num()).append("^").append(cart.getShop_code()).append("^")
//                    .append(cart.getStock_type_id());
//            if (mMapTen.get("" + cart.getStock_type_id()) > 0) {
//                sbResult.append("^").append("10:").append(mMapTen.get("" + cart.getStock_type_id()));
//            }
//            if (mMapFive.get("" + cart.getStock_type_id()) > 0) {
//                if (mMapTen.get("" + cart.getStock_type_id()) > 0) {
//                    sbResult.append("-").append("5:").append(mMapFive.get("" + cart.getStock_type_id()));
//                } else {
//                    sbResult.append("^").append("5:").append(mMapFive.get("" + cart.getStock_type_id()));
//                }
//            }
//            if (mMapTwo.get("" + cart.getStock_type_id()) > 0) {
//                if (mMapTen.get("" + cart.getStock_type_id()) > 0 || mMapFive.get("" + cart.getStock_type_id()) > 0) {
//                    sbResult.append("-").append("2:").append(mMapTwo.get("" + cart.getStock_type_id()));
//                } else {
//                    sbResult.append("^").append("2:").append(mMapTwo.get("" + cart.getStock_type_id()));
//                }
//            }
//            if (mMapOne.get("" + cart.getStock_type_id()) > 0) {
//                if (mMapTen.get("" + cart.getStock_type_id()) > 0 || mMapFive.get("" + cart.getStock_type_id()) > 0
//                        || mMapTwo.get("" + cart.getStock_type_id()) > 0) {
//                    sbResult.append("-").append("1:").append(mMapOne.get("" + cart.getStock_type_id()));
//                } else {
//                    sbResult.append("^").append("1:").append(mMapOne.get("" + cart.getStock_type_id()));
//                }
//            }
//
//            if (mMapTen.get("" + cart.getStock_type_id()) <= 0 && mMapFive.get("" + cart.getStock_type_id()) <= 0
//                    && mMapTwo.get("" + cart.getStock_type_id()) <= 0
//                    && mMapOne.get("" + cart.getStock_type_id()) <= 0) {
//                sbResult.append("^").append("0");
//            }
//            if (i != carts.size() - 1) {
//                sbResult.append(",");
//            }
//        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("message", sbMsg.toString()));
        nameValuePairs.add(new BasicNameValuePair("cartIds", "666666"));
        nameValuePairs.add(new BasicNameValuePair("integral_num", integral_num + ""));
        nameValuePairs.add(new BasicNameValuePair("result", sbResult.toString()));
        nameValuePairs.add(new BasicNameValuePair("address_id", address_id + ""));
        nameValuePairs.add(new BasicNameValuePair("couponid", couponid + ""));
        nameValuePairs.add(new BasicNameValuePair("is_be", is_be + ""));
        // channel=SharedPreferencesUtil.getStringData(context, "tags","");
        LogYiFu.e("abc", channel);
        // nameValuePairs.add(new BasicNameValuePair("channel", channel));

        String result = YConn.basedPost(context, YUrl.SUBMIT_MULTI_CART_ORDER, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.addOrder(context, result);
    }


    public static HashMap<String, Object> newMealSubmitShopOneYuan(String sbResult, Context context, HashMap<Integer, String> mapMsg,
                                                                   List<ShopCart> carts, Integer integral_num, int address_id, int couponid, HashMap<String, Integer> mMapTen,
                                                                   HashMap<String, Integer> mMapFive, HashMap<String, Integer> mMapTwo, HashMap<String, Integer> mMapOne,
                                                                   int is_be) throws Exception {
        Iterator<Entry<Integer, String>> iterator = mapMsg.entrySet().iterator();
        StringBuffer sbMsg = new StringBuffer();
//        StringBuffer sbResult = new StringBuffer();
//        while (iterator.hasNext()) {
//
//            Entry<Integer, String> entry = iterator.next();
//            sbMsg.append(entry.getKey()).append("^").append(entry.getValue());
//            if (iterator.hasNext()) {
//                sbMsg.append(",");
//            }
//
//        }
//        for (int i = 0; i < carts.size(); i++) {
//            ShopCart cart = carts.get(i);
//
//            sbResult.append(cart.getShop_num()).append("^").append(cart.getShop_code()).append("^")
//                    .append(cart.getStock_type_id());
//            if (mMapTen.get("" + cart.getStock_type_id()) > 0) {
//                sbResult.append("^").append("10:").append(mMapTen.get("" + cart.getStock_type_id()));
//            }
//            if (mMapFive.get("" + cart.getStock_type_id()) > 0) {
//                if (mMapTen.get("" + cart.getStock_type_id()) > 0) {
//                    sbResult.append("-").append("5:").append(mMapFive.get("" + cart.getStock_type_id()));
//                } else {
//                    sbResult.append("^").append("5:").append(mMapFive.get("" + cart.getStock_type_id()));
//                }
//            }
//            if (mMapTwo.get("" + cart.getStock_type_id()) > 0) {
//                if (mMapTen.get("" + cart.getStock_type_id()) > 0 || mMapFive.get("" + cart.getStock_type_id()) > 0) {
//                    sbResult.append("-").append("2:").append(mMapTwo.get("" + cart.getStock_type_id()));
//                } else {
//                    sbResult.append("^").append("2:").append(mMapTwo.get("" + cart.getStock_type_id()));
//                }
//            }
//            if (mMapOne.get("" + cart.getStock_type_id()) > 0) {
//                if (mMapTen.get("" + cart.getStock_type_id()) > 0 || mMapFive.get("" + cart.getStock_type_id()) > 0
//                        || mMapTwo.get("" + cart.getStock_type_id()) > 0) {
//                    sbResult.append("-").append("1:").append(mMapOne.get("" + cart.getStock_type_id()));
//                } else {
//                    sbResult.append("^").append("1:").append(mMapOne.get("" + cart.getStock_type_id()));
//                }
//            }
//
//            if (mMapTen.get("" + cart.getStock_type_id()) <= 0 && mMapFive.get("" + cart.getStock_type_id()) <= 0
//                    && mMapTwo.get("" + cart.getStock_type_id()) <= 0
//                    && mMapOne.get("" + cart.getStock_type_id()) <= 0) {
//                sbResult.append("^").append("0");
//            }
//            if (i != carts.size() - 1) {
//                sbResult.append(",");
//            }
//        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("message", sbMsg.toString()));
        nameValuePairs.add(new BasicNameValuePair("integral_num", integral_num + ""));
        nameValuePairs.add(new BasicNameValuePair("result", sbResult + ""));
        nameValuePairs.add(new BasicNameValuePair("address_id", address_id + ""));
        nameValuePairs.add(new BasicNameValuePair("couponid", couponid + ""));
        nameValuePairs.add(new BasicNameValuePair("is_be", is_be + ""));


        //保存一元购下单信息
        SharedPreferencesUtil.saveStringData(context, "ONEBUY_message", sbMsg.toString());
        SharedPreferencesUtil.saveStringData(context, "ONEBUY_integral_num", integral_num + "");
        SharedPreferencesUtil.saveStringData(context, "ONEBUY_result", sbResult.toString());
        SharedPreferencesUtil.saveStringData(context, "ONEBUY_address_id", address_id + "");
        SharedPreferencesUtil.saveStringData(context, "ONEBUY_couponid", couponid + "");
        SharedPreferencesUtil.saveStringData(context, "ONEBUY_is_be", is_be + "");


        // channel=SharedPreferencesUtil.getStringData(context, "tags","");
        LogYiFu.e("abc", channel);
        // nameValuePairs.add(new BasicNameValuePair("channel", channel));

        String result = YConn.basedPost(context, YUrl.SUBMIT_ONE_BUY_ORDER, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.addOrder(context, result);
    }


    public static HashMap<String, Object> submitShopOneYuan(Context context, HashMap<Integer, String> mapMsg,
                                                            List<ShopCart> carts, Integer integral_num, int address_id, int couponid, HashMap<String, Integer> mMapTen,
                                                            HashMap<String, Integer> mMapFive, HashMap<String, Integer> mMapTwo, HashMap<String, Integer> mMapOne,
                                                            int is_be) throws Exception {
        Iterator<Entry<Integer, String>> iterator = mapMsg.entrySet().iterator();
        StringBuffer sbMsg = new StringBuffer();
        StringBuffer sbResult = new StringBuffer();
        while (iterator.hasNext()) {

            Entry<Integer, String> entry = iterator.next();
            sbMsg.append(entry.getKey()).append("^").append(entry.getValue());
            if (iterator.hasNext()) {
                sbMsg.append(",");
            }

        }
        for (int i = 0; i < carts.size(); i++) {
            ShopCart cart = carts.get(i);

            sbResult.append(cart.getShop_num()).append("^").append(cart.getShop_code()).append("^")
                    .append(cart.getStock_type_id());
            if (mMapTen.get("" + cart.getStock_type_id()) > 0) {
                sbResult.append("^").append("10:").append(mMapTen.get("" + cart.getStock_type_id()));
            }
            if (mMapFive.get("" + cart.getStock_type_id()) > 0) {
                if (mMapTen.get("" + cart.getStock_type_id()) > 0) {
                    sbResult.append("-").append("5:").append(mMapFive.get("" + cart.getStock_type_id()));
                } else {
                    sbResult.append("^").append("5:").append(mMapFive.get("" + cart.getStock_type_id()));
                }
            }
            if (mMapTwo.get("" + cart.getStock_type_id()) > 0) {
                if (mMapTen.get("" + cart.getStock_type_id()) > 0 || mMapFive.get("" + cart.getStock_type_id()) > 0) {
                    sbResult.append("-").append("2:").append(mMapTwo.get("" + cart.getStock_type_id()));
                } else {
                    sbResult.append("^").append("2:").append(mMapTwo.get("" + cart.getStock_type_id()));
                }
            }
            if (mMapOne.get("" + cart.getStock_type_id()) > 0) {
                if (mMapTen.get("" + cart.getStock_type_id()) > 0 || mMapFive.get("" + cart.getStock_type_id()) > 0
                        || mMapTwo.get("" + cart.getStock_type_id()) > 0) {
                    sbResult.append("-").append("1:").append(mMapOne.get("" + cart.getStock_type_id()));
                } else {
                    sbResult.append("^").append("1:").append(mMapOne.get("" + cart.getStock_type_id()));
                }
            }

            if (mMapTen.get("" + cart.getStock_type_id()) <= 0 && mMapFive.get("" + cart.getStock_type_id()) <= 0
                    && mMapTwo.get("" + cart.getStock_type_id()) <= 0
                    && mMapOne.get("" + cart.getStock_type_id()) <= 0) {
                sbResult.append("^").append("0");
            }
            if (i != carts.size() - 1) {
                sbResult.append(",");
            }
        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("message", sbMsg.toString()));
        nameValuePairs.add(new BasicNameValuePair("integral_num", integral_num + ""));
        nameValuePairs.add(new BasicNameValuePair("result", sbResult.toString()));
        nameValuePairs.add(new BasicNameValuePair("address_id", address_id + ""));
        nameValuePairs.add(new BasicNameValuePair("couponid", couponid + ""));
        nameValuePairs.add(new BasicNameValuePair("t", "1"));


        //保存一元购下单信息
        SharedPreferencesUtil.saveStringData(context, "ONEBUY_message", sbMsg.toString());
        SharedPreferencesUtil.saveStringData(context, "ONEBUY_integral_num", integral_num + "");
        SharedPreferencesUtil.saveStringData(context, "ONEBUY_result", sbResult.toString());
        SharedPreferencesUtil.saveStringData(context, "ONEBUY_address_id", address_id + "");
        SharedPreferencesUtil.saveStringData(context, "ONEBUY_couponid", couponid + "");
        SharedPreferencesUtil.saveStringData(context, "ONEBUY_is_be", is_be + "");


        // channel=SharedPreferencesUtil.getStringData(context, "tags","");
        LogYiFu.e("abc", channel);
        // nameValuePairs.add(new BasicNameValuePair("channel", channel));

        String result = YConn.basedPost(context, YUrl.SUBMIT_ONE_BUY_ORDER, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.addOrder(context, result);
    }

    public static int initChoujiangOrder(Context context, String order_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.INIT_ONEBUY_ORDER, nameValuePairs);

        if (result == null || "".equals(result)) {
            return -1;
        }

        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {

            return -1;
        }

        if ("1".equals(j.optString("status"))) {
            return j.optInt("remainder");
        }
        return -1;

    }


    public static boolean callMachineCanTuan(Context context, String order_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.CALL_MATCHINE_CANTUAN, nameValuePairs);

        if (result == null || "".equals(result)) {
            return false;
        }

        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return false;
        }

        if ("1".equals(j.optString("status"))) {
            DialogUtils.macthineName = j.optString("userName");

            return true;
        }
        return false;

    }


    public static Boolean feedBackOneBuyChoujiang(Context context, String order_code, int firstGroup) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("whether_prize", 1 + ""));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, firstGroup > 0 ? YUrl.FEEDBACK_ONEBUY_CHOUJIANG2 : YUrl.FEEDBACK_ONEBUY_CHOUJIANG, nameValuePairs);


        if (result == null || "".equals(result)) {
            return false;
        }

        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return false;
        }

        if ("1".equals(j.optString("status"))) {
            return true;
        }
        return false;


    }


    public static Order feedBackOneBuyChoujiangZJ(Context context, String order_code, int firstGroup) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("whether_prize", 0 + ""));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, firstGroup > 0 ? YUrl.FEEDBACK_ONEBUY_CHOUJIANG2 : YUrl.FEEDBACK_ONEBUY_CHOUJIANG, nameValuePairs);


        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        String orderStr = jsonObject.optString("order");

        JSONObject jos = new JSONObject(orderStr);

        String text = "";

        if (!jos.has("orderShops") || null == jos.getString("orderShops") || jos.getString("orderShops").equals("")) {
        } else {
            text = jos.optString("orderShops");
        }
        List<OrderShop> list = JSON.parseArray(text, OrderShop.class);


        Order order = JSON.parseObject(orderStr, Order.class);

        order.setList(list);

        return order;


    }


    //1元购再抽一次下单
    public static HashMap<String, Object> submitShopOneYuanTWO(Context context, String order_code
    ) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));


        String result = YConn.basedPost(context, YUrl.ONEBUY_SUBMIT_AG, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.addOrder(context, result);
    }


    public static HashMap<String, Object> submitShopcartOrdersDaPei(Context context, HashMap<Integer, String> mapMsg,
                                                                    List<ShopCart> carts, Integer integral_num, int address_id, int couponid, HashMap<String, Integer> mMapTen,
                                                                    HashMap<String, Integer> mMapFive, HashMap<String, Integer> mMapTwo, HashMap<String, Integer> mMapOne,
                                                                    int is_be) throws Exception {
        Iterator<Entry<Integer, String>> iterator = mapMsg.entrySet().iterator();
        StringBuffer sbMsg = new StringBuffer();
        StringBuffer sbCartId = new StringBuffer();
        StringBuffer sbResult = new StringBuffer();
        while (iterator.hasNext()) {

            Entry<Integer, String> entry = iterator.next();
            sbMsg.append(entry.getKey()).append("^").append(entry.getValue());
            if (iterator.hasNext()) {
                sbMsg.append(",");
            }

        }
        for (int i = 0; i < carts.size(); i++) {
            ShopCart cart = carts.get(i);
            sbCartId.append(cart.getId());
            if (i != carts.size() - 1) {
                sbCartId.append(",");
            }
            sbResult.append(cart.getShop_num()).append("^").append(cart.getShop_code()).append("^")
                    .append(cart.getStock_type_id());
            if (mMapTen.get("" + cart.getStock_type_id()) > 0) {
                sbResult.append("^").append("10:").append(mMapTen.get("" + cart.getStock_type_id()));
            }
            if (mMapFive.get("" + cart.getStock_type_id()) > 0) {
                if (mMapTen.get("" + cart.getStock_type_id()) > 0) {
                    sbResult.append("-").append("5:").append(mMapFive.get("" + cart.getStock_type_id()));
                } else {
                    sbResult.append("^").append("5:").append(mMapFive.get("" + cart.getStock_type_id()));
                }
            }
            if (mMapTwo.get("" + cart.getStock_type_id()) > 0) {
                if (mMapTen.get("" + cart.getStock_type_id()) > 0 || mMapFive.get("" + cart.getStock_type_id()) > 0) {
                    sbResult.append("-").append("2:").append(mMapTwo.get("" + cart.getStock_type_id()));
                } else {
                    sbResult.append("^").append("2:").append(mMapTwo.get("" + cart.getStock_type_id()));
                }
            }
            if (mMapOne.get("" + cart.getStock_type_id()) > 0) {
                if (mMapTen.get("" + cart.getStock_type_id()) > 0 || mMapFive.get("" + cart.getStock_type_id()) > 0
                        || mMapTwo.get("" + cart.getStock_type_id()) > 0) {
                    sbResult.append("-").append("1:").append(mMapOne.get("" + cart.getStock_type_id()));
                } else {
                    sbResult.append("^").append("1:").append(mMapOne.get("" + cart.getStock_type_id()));
                }
            }

            if (mMapTen.get("" + cart.getStock_type_id()) <= 0 && mMapFive.get("" + cart.getStock_type_id()) <= 0
                    && mMapTwo.get("" + cart.getStock_type_id()) <= 0
                    && mMapOne.get("" + cart.getStock_type_id()) <= 0) {
                sbResult.append("^").append("0");
            }
            if (i != carts.size() - 1) {
                sbResult.append(",");
            }
        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("message", sbMsg.toString()));
        nameValuePairs.add(new BasicNameValuePair("cartIds", sbCartId.toString()));
        nameValuePairs.add(new BasicNameValuePair("integral_num", integral_num + ""));
        nameValuePairs.add(new BasicNameValuePair("result", sbResult.toString()));
        nameValuePairs.add(new BasicNameValuePair("address_id", address_id + ""));
        nameValuePairs.add(new BasicNameValuePair("couponid", couponid + ""));
        nameValuePairs.add(new BasicNameValuePair("is_be", is_be + ""));

        // channel=SharedPreferencesUtil.getStringData(context, "tags","");
        LogYiFu.e("abc", channel);
        // nameValuePairs.add(new BasicNameValuePair("channel", channel));

        String result = YConn.basedPost(context, YUrl.SUBMIT_MULTI_CART_ORDER_DAPEI, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.addOrder(context, result);
    }

    // TODO:
    /**
     * 特卖商品购物车提交订单
     */
    // public static HashMap<String, Object> submitShopcartSpecial(Context
    // context, HashMap<Integer, String> mapMsg,
    // List<ShopCart> carts, int address_id, String cartIds) throws Exception {
    // StringBuffer packageResult = new StringBuffer();
    //
    // Iterator<Entry<Integer, String>> iterator = mapMsg.entrySet().iterator();
    // List<String> list = new ArrayList<String>();
    // while (iterator.hasNext()) {
    //
    // Entry<Integer, String> entry = iterator.next();
    // list.add(entry.getValue());
    //
    // }
    // for (int i = 0; i < carts.size(); i++) {
    //
    // ShopCart cart = carts.get(i);
    // if (list.get(i) == null || list.get(i) == "" ||
    // TextUtils.isEmpty(list.get(i))) {
    // packageResult.append(cart.getP_code()).append("^").append(cart.getP_s_t_id()).append("^")
    // .append(cart.getShop_num()).append("^").append("*****");
    // } else {
    // packageResult.append(cart.getP_code()).append("^").append(cart.getP_s_t_id()).append("^")
    // .append(cart.getShop_num()).append("^").append(list.get(i));
    // }
    //
    // if (i != carts.size() - 1) {
    // packageResult.append("#");
    // }
    // }
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("cartIds", cartIds));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // // nameValuePairs.add(new BasicNameValuePair("message",
    // // sbMsg.toString()));
    // // nameValuePairs.add(new BasicNameValuePair("cartIds",
    // // sbCartId.toString()));
    // // nameValuePairs.add(new BasicNameValuePair("integral_num",
    // // integral_num+""));
    // // nameValuePairs.add(new BasicNameValuePair("result",
    // // sbResult.toString()));
    // nameValuePairs.add(new BasicNameValuePair("packageResult",
    // packageResult.toString()));
    //
    // // channel=SharedPreferencesUtil.getStringData(context, "tags","");
    // LogYiFu.e("abc", channel);
    // // nameValuePairs.add(new BasicNameValuePair("channel", channel));
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("address_id", address_id +
    // ""));
    // // nameValuePairs.add(new BasicNameValuePair("couponid", couponid+""));
    //
    // String result = YConn.basedPost(context,
    // YUrl.SUBMIT_MULTI_CART_ORDER_SPECIAL, nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.addOrderSpecial(context, result);
    // }

    /** 会员商品提交订单 */
    // public static HashMap<String, Object> submitMemberOrder(Context context,
    // String message, String shop_code,
    // String stocktype_id, String shop_num, String address_id) throws Exception
    // {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("message", message));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
    //
    // // channel=SharedPreferencesUtil.getStringData(context, "tags","");
    // LogYiFu.e("abc", channel);
    // // nameValuePairs.add(new BasicNameValuePair("channel", channel));
    //
    // nameValuePairs.add(new BasicNameValuePair("address_id", address_id));
    // nameValuePairs.add(new BasicNameValuePair("stocktype_id", stocktype_id));
    // nameValuePairs.add(new BasicNameValuePair("shop_num", shop_num));
    //
    // String result = YConn.basedPost(context, YUrl.SUBMIT_MEMBER_ORDER,
    // nameValuePairs);
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.addMemberOrder(context, result);
    // }

    /**
     * 提交多个订单
     */
    public static HashMap<String, Object> submitMultiOrder(Context context, String store_code, String store_name,
                                                           int address_id, HashMap<Integer, List<ShopCart>> OrderShop, HashMap<Integer, String> mapMsg,
                                                           HashMap<Integer, HashMap<String, Object>> mapCoups, HashMap<Integer, String> mapInteg, String packageResult,
                                                           String orderToken) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("store_code", store_code));
        nameValuePairs.add(new BasicNameValuePair("address_id", address_id + ""));

        nameValuePairs.add(new BasicNameValuePair("packageResult", packageResult));
        nameValuePairs.add(new BasicNameValuePair("orderToken", orderToken));
        String orderString = JSONArray.toJSONString(OrderShop);

        List<HashMap<String, Object>> listOrder = new ArrayList<HashMap<String, Object>>();
        Iterator<Entry<Integer, List<ShopCart>>> iterator = OrderShop.entrySet().iterator();
        StringBuffer sb = new StringBuffer();
        while (iterator.hasNext()) {
            HashMap<String, Object> mapRequest = new HashMap<String, Object>();
            Entry<Integer, List<ShopCart>> entry = iterator.next();
            String msg = mapMsg.get(entry.getKey());// 得到保存的 留言信息
            mapRequest.put("message", msg);
            if (null != mapInteg) {
                mapRequest.put("integral_num", mapInteg.get(entry.getKey()));
            }
            HashMap<String, Object> mapC = mapCoups.get(entry.getKey());// 得到优惠券的
            // map集合
            if (null != mapC) {
                mapRequest.put("coupon_id", mapC.get("id"));
            }

            List<HashMap<String, String>> mapOrder = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < entry.getValue().size(); i++) {
                ShopCart sc = entry.getValue().get(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("shop_code", sc.getShop_code());
                map.put("shop_num", sc.getShop_num() + "");
                map.put("size", sc.getSize());
                map.put("color", sc.getColor());
                map.put("stocktypeid", sc.getStock_type_id() + "");
                map.put("shop_pic", sc.getDef_pic());
                mapOrder.add(map);
                // Log.i("TAG", "====" + sc.getId());
                sb.append(sc.getId() + ",");
            }
            mapRequest.put("orderShopList", mapOrder);
            listOrder.add(mapRequest);
        }
        sb.deleteCharAt(sb.length() - 1);
        // LogYiFu.e("TAG", "listorder=" + listOrder.toString() + ",sb=" +
        // sb.toString());
        nameValuePairs.add(new BasicNameValuePair("result", JSONArray.toJSONString(listOrder).toString()));
        nameValuePairs.add(new BasicNameValuePair("cartIds", sb.toString()));

        /*
         * if (null != mapInteg) { Iterator<Entry<Integer, String>>
         * iteratorInteg = mapInteg .entrySet().iterator(); int sum = 0; while
         * (iteratorInteg.hasNext()) { Entry<Integer, String> entryInte =
         * iteratorInteg.next(); int integ = 0; if
         * (!TextUtils.isEmpty(entryInte.getValue())) { integ =
         * Integer.valueOf(entryInte.getValue()); } sum += integ; }
         * nameValuePairs .add(new BasicNameValuePair("integral_num", sum +
         * "")); }
         */
        String result = YConn.basedPost(context, YUrl.SUBMIT_MULTI_ORDER, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);
        // return null;
        return (result == null || "".equals(result)) ? null : EntityFactory.addOrder(context, result);
    }

    /**
     * 积分商城提交订单
     */
    public static HashMap<String, Object> submitInteOrder(Context context, String message, String store_code,
                                                          String store_name, int address_id, List<HashMap<String, String>> OrderShop) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("message", message));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("store_code", store_code));
        nameValuePairs.add(new BasicNameValuePair("store_name", store_name));
        nameValuePairs.add(new BasicNameValuePair("address_id", address_id + ""));
        String orderString = JSONArray.toJSONString(OrderShop);
        nameValuePairs.add(new BasicNameValuePair("order_shop", JSONArray.toJSONString(OrderShop).toString()));
        String result = YConn.basedPost(context, YUrl.SUBMIT_INTE_ORDER, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);
        // return null;
        return (result == null || "".equals(result)) ? null : EntityFactory.addOrder(context, result);
    }

    /** 获取我的圈子列表 */
    // public static List<HashMap<String, Object>> getMyCircleList(Context
    // context, int index, String user_id)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
    // nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
    // String result = YConn.basedPost(context, YUrl.GET_MY_CIRCLE_LIST,
    // nameValuePairs);
    //
    // // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
    // // null);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createMyCircle(context, result);
    // }

    /** 获取个人主页关注 未登录状态 */
    // public static List<HashMap<String, Object>> getMyCircleList2(Context
    // context, int index, String user_id)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
    // nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
    // String result = YConn.basedPost(context, YUrl.GET_MY_CIRCLE_LIST2,
    // nameValuePairs);
    //
    // // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
    // // null);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createMyCircle(context, result);
    // }

    /** 获取我的圈子---个人主页粉丝列表 */
    // public static List<HashMap<String, Object>> getCircleFansList(Context
    // context, int index, String fol_user_id)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
    // nameValuePairs.add(new BasicNameValuePair("fol_user_id", fol_user_id));
    // String result = YConn.basedPost(context, YUrl.GET_CIRCLE_FANS_LIST,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createMyFansList(context, result);
    // }

    /** 获取我的圈子---个人主页粉丝列表 未登录状态 */
    // public static List<HashMap<String, Object>> getCircleFansList2(Context
    // context, int index, String fol_user_id)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // // nameValuePairs.add(new BasicNameValuePair("token", YCache
    // // .getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
    // nameValuePairs.add(new BasicNameValuePair("fol_user_id", fol_user_id));
    // String result = YConn.basedPost(context, YUrl.GET_CIRCLE_FANS_LIST2,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createMyFansList(context, result);
    // }

    /** 获取圈子推荐列表 */
    // public static List<HashMap<String, Object>> getRecommendCircle(Context
    // context, int index, String recom)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
    // nameValuePairs.add(new BasicNameValuePair("recom", recom));
    // // nameValuePairs.add(new BasicNameValuePair("shop_type_id", id+""));
    // String result = YConn.basedPost(context, YUrl.GET_RECOMMEND_CIRCLE_LIST,
    // nameValuePairs);
    // MyLogYiFu.e("推荐", result.toString());
    //
    // // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
    // // null);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createRecomCircle(context, result);
    // }

    // public static List<HashMap<String, Object>> getRecommendCircle2(Context
    // context, int index, String recom)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // // nameValuePairs.add(new BasicNameValuePair("token", YCache
    // // .getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
    // nameValuePairs.add(new BasicNameValuePair("recom", recom));
    // // nameValuePairs.add(new BasicNameValuePair("shop_type_id", id+""));
    // String result = YConn.basedPost(context, YUrl.GET_RECOMMEND_CIRCLE_LIST2,
    // nameValuePairs);
    // MyLogYiFu.e("未登录推荐", result.toString());
    //
    // // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
    // // null);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createRecomCircle(context, result);
    // }

    /** 获取所有圈子 */
    // public static List<HashMap<String, Object>> getAllCircle(Context context,
    // int index) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // // if(false == YJApplication.isLoginSucess())
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
    // // nameValuePairs.add(new BasicNameValuePair("shop_type_id", id+""));
    // String result = YConn.basedPost(context, YUrl.GET_ALL_CIRCLE_LIST,
    // nameValuePairs);
    //
    // // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
    // // null);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createAllCircle(context, result);
    // }

    /** 获取所有圈子 未登录状态 */
    // public static List<HashMap<String, Object>> getAllCircle2(Context
    // context, int index) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // // if(false == YJApplication.isLoginSucess())
    // // nameValuePairs.add(new BasicNameValuePair("token", YCache
    // // .getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
    // // nameValuePairs.add(new BasicNameValuePair("shop_type_id", id+""));
    // String result = YConn.basedPost(context, YUrl.GET_ALL_CIRCLE_LIST2,
    // nameValuePairs);
    //
    // // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
    // // null);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createAllCircle(context, result);
    // }

    /** 获取圈子个人主页 */
    // public static Map<String, Object> getCircleHomePager(Context context,
    // String fol_user_id) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("user_id", fol_user_id));
    // String result = YConn.basedPost(context, YUrl.GET_CIRCLE_HOME_PAGE,
    // nameValuePairs);
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createCircleHomePager(context, result);
    // }

    /** 获取圈子个人主页 未登录状态 */
    // public static Map<String, Object> getCircleHomePager2(Context context,
    // String fol_user_id) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // // nameValuePairs.add(new BasicNameValuePair("token", YCache
    // // .getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("user_id", fol_user_id));
    // String result = YConn.basedPost(context, YUrl.GET_CIRCLE_HOME_PAGE2,
    // nameValuePairs);
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createCircleHomePager(context, result);
    // }

    /** 获取圈子个人主页-关注 */
    // public static ReturnInfo getCircleHomePagerAttention(Context context,
    // String fol_user_id) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("fol_user_id", fol_user_id));
    // String result = YConn.basedPost(context,
    // YUrl.GET_CIRCLE_HOME_PAGE_ATTENTION, nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createRetInfo(context, result);
    // }

    /** 获取圈子个人主页-取消关注 */
    // public static ReturnInfo getCircleHomePagerUnAttention(Context context,
    // String fol_user_id) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("fol_user_id", fol_user_id));
    // String result = YConn.basedPost(context,
    // YUrl.GET_CIRCLE_HOME_PAGE_UNATTENTION, nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createRetInfo(context, result);
    // }

    /**
     * 微信支付
     */
    public static Map<String, String> getPrepayId(Context context, String order_code, String order_name,
                                                  String wxPayUrl) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("order_name", order_name));
        // nameValuePairs.add(new BasicNameValuePair("shop_type_id", id+""));
        String result = YConn.basedPost(context, wxPayUrl, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMapWxPrepay(context, result);
    }

    /** 微信支付红包 */
    // public static Map<String, String> getPrepayIdRed(Context context, String
    // hcode, String wxPayUrl) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    //
    // nameValuePairs.add(new BasicNameValuePair("hcode", hcode));
    // String result = YConn.basedPost(context, wxPayUrl, nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createMapWxPrepay(context, result);
    // }

    /** 获取所有圈子---帖子列表 */
    // public static Map<String, List<HashMap<String, Object>>>
    // getPostList(Context context, String circle_id, int pager,
    // String queryCircle, int fine, int hot) throws Exception {
    //
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("circle_id", circle_id));
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", pager + ""));
    //
    // if (!"null".equals(queryCircle)) {
    // nameValuePairs.add(new BasicNameValuePair("queryCircle", queryCircle));
    // }
    // if (!"false".equals(queryCircle)) {
    //
    // nameValuePairs.add(new BasicNameValuePair("fine", fine + ""));
    // nameValuePairs.add(new BasicNameValuePair("hot", hot + ""));
    // }
    // String result = YConn.basedPost(context, YUrl.GET_POST_LIST,
    // nameValuePairs);
    //
    // if (!"null".equals(queryCircle)) {
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createPostList(context, result);
    // } else {
    // return (result == null || "".equals(result)) ? null
    // : EntityFactory.createPostListForJPAndHot(context, result);
    // }
    // }

    /** 获取所有圈子---帖子列表 未登录状态 */
    // public static Map<String, List<HashMap<String, Object>>>
    // getPostList2(Context context, String circle_id, int pager,
    // String queryCircle, int fine, int hot) throws Exception {
    //
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // // nameValuePairs.add(new BasicNameValuePair("token", YCache
    // // .getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("circle_id", circle_id));
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", pager + ""));
    //
    // if (!"null".equals(queryCircle)) {
    // nameValuePairs.add(new BasicNameValuePair("queryCircle", queryCircle));
    // }
    // if (!"false".equals(queryCircle)) {
    //
    // nameValuePairs.add(new BasicNameValuePair("fine", fine + ""));
    // nameValuePairs.add(new BasicNameValuePair("hot", hot + ""));
    // }
    // String result = YConn.basedPost(context, YUrl.GET_POST_LIST2,
    // nameValuePairs);
    //
    // if (!"null".equals(queryCircle)) {
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createPostList(context, result);
    // } else {
    // return (result == null || "".equals(result)) ? null
    // : EntityFactory.createPostListForJPAndHot(context, result);
    // }
    // }

    /** 获取所有圈子---我的记录和动态 */
    // public static List<HashMap<String, Object>> getMyRecordList(Context
    // context, int pager, String bool, String user_id)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    //
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", pager + ""));
    // nameValuePairs.add(new BasicNameValuePair("bool", bool));
    //
    // if (!"true".equals(bool)) {
    // nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
    // }
    // String result = YConn.basedPost(context, YUrl.GET_USER_NEWS_LIST,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createMyRecordAndDynamic(context, result);
    // }

    /** 获取所有圈子---未登录状态下查询个人主页动态 */
    // public static List<HashMap<String, Object>> getMyRecordList2(Context
    // context, int pager, String bool,
    // String user_id) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // // nameValuePairs.add(new BasicNameValuePair("token", YCache
    // // .getCacheToken(context)));
    //
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", pager + ""));
    // nameValuePairs.add(new BasicNameValuePair("bool", bool));
    //
    // if (!"true".equals(bool)) {
    // nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
    // }
    // String result = YConn.basedPost(context, YUrl.GET_USER_NEWS_LIST2,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createMyRecordAndDynamic(context, result);
    // }

    // /** 获取所有圈子---删除我的记录（帖子） */
    // public static ReturnInfo getDeleteMyRecord(Context context, String
    // news_id, String news_ids) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    //
    // if (!TextUtils.isEmpty(news_id)) {
    // nameValuePairs.add(new BasicNameValuePair("news_id", news_id));
    // }
    //
    // if (!TextUtils.isEmpty(news_ids)) {
    // nameValuePairs.add(new BasicNameValuePair("news_ids", news_ids));
    // }
    //
    // String result = YConn.basedPost(context, YUrl.GET_CIRCLE_DELETE_POST,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createRetInfo(context, result);
    // }

    /** 获取所有圈子---删除收藏记录 */
    // public static ReturnInfo getDeleteCollectRecord(Context context, String
    // news_id, String news_ids) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    //
    // if (!TextUtils.isEmpty(news_id)) {
    // nameValuePairs.add(new BasicNameValuePair("news_id", news_id));
    // }
    //
    // if (!TextUtils.isEmpty(news_ids)) {
    // nameValuePairs.add(new BasicNameValuePair("news_ids", news_ids));
    // }
    //
    // String result = YConn.basedPost(context,
    // YUrl.GET_CIRCLE_DELETE_COLLECT_POST, nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createRetInfo(context, result);
    // }

    /** 获取所有圈子---收藏的帖子记录 */
    // public static List<HashMap<String, Object>> getCollectList(Context
    // context, int pager) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    //
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", pager + ""));
    //
    // String result = YConn.basedPost(context, YUrl.GET_COLLECT_LIST,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createCollectList(context, result);
    // }

    /** 加入圈子 */
    // public static ReturnInfo addCircle(Context context, String circle_id)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("circle_id", circle_id));
    // String result = YConn.basedPost(context, YUrl.ADD_CIRCLE,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createRetInfo(context, result);
    // }

    /**
     * 退出圈子
     *
     * @param context
     * @param circle_id
     */
    // public static ReturnInfo exitCircle(Context context, String circle_id)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("circle_id", circle_id));
    // String result = YConn.basedPost(context, YUrl.EXIT_CIRCLE,
    // nameValuePairs);
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createRetInfo(context, result);
    // }

    /***
     * 查询圈成员列表
     *
     * @param context
     * @param circle_id
     * @param admin
     *            是否查询管理员
     * @param pager
     * @return
     * @throws Exception
     */
    // public static List<HashMap<String, Object>> getCircleMem(Context context,
    // String circle_id, int admin, int pager)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("circle_id", circle_id));
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", pager + ""));
    // nameValuePairs.add(new BasicNameValuePair("admin", admin + ""));
    // String result = YConn.basedPost(context, YUrl.GET_CIRCLE_MEM,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createCircleMem(context, result);
    // }

    /***
     * 查询圈成员列表 未登录状态
     *
     * @param context
     * @param circle_id
     * @param admin
     *            是否查询管理员
     * @param pager
     * @return
     * @throws Exception
     */
    // public static List<HashMap<String, Object>> getCircleMem2(Context
    // context, String circle_id, int admin, int pager)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("circle_id", circle_id));
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", pager + ""));
    // nameValuePairs.add(new BasicNameValuePair("admin", admin + ""));
    // String result = YConn.basedPost(context, YUrl.GET_CIRCLE_MEM2,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createCircleMem(context, result);
    // }

    /***
     * 帖子评论列表
     *
     * @param context
     * @param circle_id
     * @param admin
     *            是否查询管理员
     * @param pager
     * @return
     * @throws Exception
     */
    // public static List<Map<String, Object>> getCommentList(Context context,
    // String news_id, int pager, String user_id)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("news_id", news_id));
    //
    // if (!TextUtils.isEmpty(user_id)) {
    // nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
    // }
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", pager + ""));
    // String result = YConn.basedPost(context, YUrl.GET_COMMENT_LIST,
    // nameValuePairs);
    // // System.out.println(result);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createCommentList(context, result);
    // }

    /***
     * 帖子评论列表 未登录状态
     *
     * @param context
     * @param circle_id
     * @param admin
     *            是否查询管理员
     * @param pager
     * @return
     * @throws Exception
     */
    // public static List<Map<String, Object>> getCommentList2(Context context,
    // String news_id, int pager, String user_id)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // // nameValuePairs.add(new BasicNameValuePair("token", YCache
    // // .getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("news_id", news_id));
    //
    // if (!TextUtils.isEmpty(user_id)) {
    // nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
    // }
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", pager + ""));
    // String result = YConn.basedPost(context, YUrl.GET_COMMENT_LIST2,
    // nameValuePairs);
    // // System.out.println(result);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createCommentList(context, result);
    // }

    /***
     * 她她圈--添加评论
     *
     * @param context
     * @param news_id
     * @return
     */
    public static ReturnInfo postComment(Context context, String news_id, String content, String circle_id)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("content", content));
        nameValuePairs.add(new BasicNameValuePair("news_id", news_id));
        nameValuePairs.add(new BasicNameValuePair("circle_id", circle_id));
        String result = YConn.basedPost(context, YUrl.POST_COMMENT, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /***
     * 她她圈--收藏帖子
     *
     * @param context
     * @param news_id
     * @param comment
     * @return
     * @throws Exception
     */
    // public static ReturnInfo postCollect(Context context, String news_id,
    // String circle_id) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("news_id", news_id));
    // nameValuePairs.add(new BasicNameValuePair("circle_id", circle_id));
    // String result = YConn.basedPost(context, YUrl.POST_COLLECT_NEWS,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createRetInfo(context, result);
    // }

    /** 获取圈子推荐列表 */
    // public static List<HashMap<String, Object>> getEssenceCircle(Context
    // context, int index, String circle_id,
    // String recom) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
    // nameValuePairs.add(new BasicNameValuePair("recom", recom + ""));
    // nameValuePairs.add(new BasicNameValuePair("circle_id", circle_id + ""));
    // // nameValuePairs.add(new BasicNameValuePair("shop_type_id", id+""));
    // String result = YConn.basedPost(context, YUrl.GET_RECOMMEND_CIRCLE_LIST,
    // nameValuePairs);
    //
    // // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
    // // null);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createRecomCircle(context, result);
    // }

    /** 获取圈子发帖 */
    // public static ReturnInfo getCirclePublishTopic(Context context, String
    // circle_id, String title, String content,
    // String pic_list, String tag) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("circle_id", circle_id));
    // nameValuePairs.add(new BasicNameValuePair("title", title));
    // nameValuePairs.add(new BasicNameValuePair("content", content));
    // nameValuePairs.add(new BasicNameValuePair("pic_list", pic_list));
    // nameValuePairs.add(new BasicNameValuePair("tag", tag));
    // String result = YConn.basedPost(context, YUrl.GET_CIRCLE_PUBLISH_TOPIC,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createRetInfo(context, result);
    // }

    /** 获取圈子发帖 --选择标签 */
    // public static List<Map<String, Object>> getCircleTags(Context context,
    // String tag_data) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("tag_data", tag_data));
    //
    // String result = YConn.basedPost(context, YUrl.GET_CIRCLE_CHOOSE_TAGS,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createTags(context, result);
    // }

    /***
     * 帖子详情
     *
     * @param context
     * @param news_id
     * @return
     * @throws Exception
     */
    // public static HashMap<String, Object> getPostInfo(Context context, String
    // news_id) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("news_id", news_id));
    //
    // String result = YConn.basedPost(context, YUrl.POST_QUERY,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createPostInfo(context, result);
    // }

    /***
     * 帖子详情 未登录状态
     *
     * @param context
     * @param news_id
     * @return
     * @throws Exception
     */
    // public static HashMap<String, Object> getPostInfo2(Context context,
    // String news_id) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // // nameValuePairs.add(new BasicNameValuePair("token", YCache
    // // .getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("news_id", news_id));
    //
    // String result = YConn.basedPost(context, YUrl.POST_QUERY2,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createPostInfo(context, result);
    // }

    /**
     * 申请退换货
     */
    public static ReturnInfo applyThh(Context context, String order_code, String explain, String pic, int return_type,
                                      String cause) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("explain", explain));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));

        nameValuePairs.add(new BasicNameValuePair("return_type", return_type + ""));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("cause", cause));

        String result = YConn.basedPost(context, YUrl.APPLY_THH, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 添加商品评论，单商品评论
     */
    public static ReturnInfo addComment(Context context, int order_shop_id, String content, String pic,
                                        String ordercode, int comment_type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("order_shop_id", order_shop_id + ""));
        nameValuePairs.add(new BasicNameValuePair("content", content));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));

        nameValuePairs.add(new BasicNameValuePair("ordercode", ordercode));
        nameValuePairs.add(new BasicNameValuePair("comment_type", comment_type + ""));

        String result = YConn.basedPost(context, YUrl.ADD_COMMENT, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 添加商品评论，多商品评论
     */
    public static ReturnInfo addCommentList(Context context, String ordercode, String jsonComment) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("ordercode", ordercode));
        nameValuePairs.add(new BasicNameValuePair("jsonComment", jsonComment));

        String result = YConn.basedPost(context, YUrl.ADD_SHOP_CONMMENT_LIST, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 追加商品评论，多商品评论
     */
    public static ReturnInfo appendCommentList(Context context, String ordercode, String jsonComment) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("order_code", ordercode));
        nameValuePairs.add(new BasicNameValuePair("jsonComment", jsonComment));

        String result = YConn.basedPost(context, YUrl.APPEND_SHOP_CONMMENT, nameValuePairs);
        LogYiFu.e("dajijfe几分几家分解法", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 平台介入
     */
    public static ReturnInfo applyPlatform(Context context, Integer id, String user_cert_msg, String user_certificate)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("id", id + ""));
        nameValuePairs.add(new BasicNameValuePair("user_cert_msg", user_cert_msg));
        nameValuePairs.add(new BasicNameValuePair("user_certificate", user_certificate));
        String result = YConn.basedPost(context, YUrl.APPLY_PLATFORM, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 夺宝中我要晒单
     */
    public static ReturnInfo addIndianaShaidan(Context context, String content, String pic, String shop_code,
                                               String shop_name, String in_code, String issue_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("content", content + ""));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("shop_name", shop_name));
        nameValuePairs.add(new BasicNameValuePair("issue_code", issue_code + ""));
        nameValuePairs.add(new BasicNameValuePair("lucky_number", in_code + ""));
        String result = YConn.basedPost(context, YUrl.INDIANA_COMMENT, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 5元红包弹窗
     */
    public static HashMap<String, Object> fiveYuanDialog(Context context, String imei) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("imei", imei));
        String result = YConn.basedPost(context, YUrl.isOpenShop, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createfiveYuanDialog(context, result);
    }

    /**
     * 获取系统当前时间
     */
    public static HashMap<String, Object> getSystemTime(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GETNOWTIME, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSystemTime(context, result);
    }

    /**
     * 获取用户等级
     */
    public static HashMap<String, Object> getUserGradle(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_USER_GRALDE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createUserGralde(context, result);
    }

    /**
     * 获取是否弹余额翻倍的弹窗
     */
    public static HashMap<String, Object> getIsDialog(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_IS_DIALOG, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createIsDialog(context, result);
    }

    /**
     * 获取环信密码
     */
    public static HashMap<String, Object> getHuanXinPassword(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_HUANXIN_PASSWORD, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createHuanXinPassword(context, result);
    }

    /**
     * 获取我的喜好
     *
     * @param context
     * @return
     */
    public static String getMineLike(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.ADD_MINE_LIKE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createMineLike(context, result);
    }

    /**
     * 往期揭晓——晒单
     */

    public static List<HashMap<String, Object>> WangQIShaiDan(Context context, String index) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));

        String result = YConn.basedPost(context, YUrl.DuoBaoShaiDan, nameValuePairs);
        // System.out.println("看这个result=" + result);
        return (result == null || "".equals(result)) ? null : EntityFactory.createDuoBao_ShaiDan(context, result);
    }

    /**
     * 晒单点赞
     */
    public static HashMap<String, Object> AddClick(Context context, String shop_code) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.AddClick, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createAddClick(context, result);
    }

    /**
     * 夺宝记录——我的晒单
     */
    public static List<HashMap<String, Object>> DuoBaoShaiDan(Context context, String user_id, String index)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));

        nameValuePairs.add(new BasicNameValuePair("user_id", user_id + ""));

        String result = YConn.basedPost(context, YUrl.DuoBaoShaiDan, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createDuoBao_ShaiDan(context, result);
    }

    /**
     * 往期揭晓——往期揭晓 就是往期揭晓的左边部分
     */
    public static List<HashMap<String, Object>> Wqjx_Left(Context context, String index) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        nameValuePairs.add(new BasicNameValuePair("sort", "otime"));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));

        nameValuePairs.add(new BasicNameValuePair("page", index + ""));

        String result = YConn.basedPost(context, YUrl.Wqjx_left, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createWqjx_left(context, result);
    }

    /**
     * 夺宝记录——我的参与记录
     */

    public static List<HashMap<String, Object>> SnatchJoin(Context context,
                                                           String index) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token",
                YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));

        nameValuePairs.add(new BasicNameValuePair("sort", "btime"));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));

        String result = YConn.basedPost(context, YUrl.SnatchJoin,
                nameValuePairs);
        return (result == null || "".equals(result)) ? null :
                EntityFactory.createSnatchJoin(context, result);
    }

    /**
     * 查询抵用券
     */
    // public static List<HashMap<String, Object>> DiYongQuanDialog(Context
    // context) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // // nameValuePairs.add(new BasicNameValuePair("imei", imei2));
    // String result = YConn.basedPost(context, YUrl.HaveVoucher,
    // nameValuePairs);
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createDiYongQuan(context, result);
    // }

    // /**
    // * 得到我的抵用券列表
    // */
    // public static List<HashMap<String, Object>> getMyDiYongQuan(
    // Context context) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token", YCache
    // .getCacheToken(context)));
    //
    // String result = YConn.basedPost(context, YUrl.HaveVoucher,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null : EntityFactory
    // .createDiYongQuan(context, result);
    // }

    /**
     * 第几次拿抵用券
     *
     * @param context
     * @return
     */
    public static HashMap<String, Object> DiYongQuan(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.Voucher, nameValuePairs);
        // System.out.println("comModel1111" + result);
        return (result == null || "".equals(result)) ? null : EntityFactory.createDiYong(context, result);
    }

    /**
     * 特卖订单提交确认
     */
    // public static HashMap<String, Object> shopCartSubmitConfrim(Context
    // context) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // String result = YConn.basedPost(context, YUrl.SHOPCART_SUBMIT_CONFRIM,
    // nameValuePairs);
    // return (result == null || "".equals(result)) ? null
    // : EntityFactory.createShopCartSubmitConfrim(context, result);
    // }

    /****
     * 根据单个订单商品确认收货
     *
     * @param context
     * @param order_code
     * @param order_shop_id
     * @param pwd
     * @return
     */

    public static ReturnInfo affirmOrder(Context context, String order_code, String pwd, int order_shop_id)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));

        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));
        String result = YConn.basedPost(context, YUrl.AFFIRM_PROD_RECEIVED, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 查看物流
     */
    public static HashMap<String, Object> getLogisticsInfo(Context context, String order_code, String shop_code)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));

        String result = YConn.basedPost(context, YUrl.CHAKAN_LOGISTICS, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createLogisticsInfo(context, result);
    }

    /** 从快递100中查询物流信息 */
    // public static List<HashMap<String, Object>> getLogistics(Context context,
    // String logi_name, String logi_code) throws Exception {
    // String url = "http://www.kuaidi100.com/query";
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("type", logi_name));
    // nameValuePairs.add(new BasicNameValuePair("postid", logi_code));
    // String result = YConn.postKuaidi100(context, url, nameValuePairs);
    //
    // // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
    // // null);
    // // return null;
    // return (result == null || "".equals(result)) ? null : EntityFactory
    // .createLogistics(context, result);
    // }

    /**
     * 从后台获取物流信息
     */
    public static List<HashMap<String, Object>> getLogistics(Context context, String nu) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("nu", nu));
        String result = YConn.basedPost(context, YUrl.EXEPQUERY, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createLogistics(context, result);
    }

    /**
     * 得到退货款列表界面
     */
    public static List<Order> getPaybackList(Context context, int index, int change) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));
        nameValuePairs.add(new BasicNameValuePair("status", "0"));
        nameValuePairs.add(new BasicNameValuePair("change", change + ""));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));
        String result = YConn.basedPost(context, YUrl.GET_BUY_ORDER_LIST, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createOrderList(context, result);
    }

    /**
     * 通过筛选获取商品列表
     */
    public static List<HashMap<String, Object>> getFilterProductList(String id, Context context, String index,
                                                                     HashMap<String, Object> mapRequest, int pageSize) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize + ""));
        nameValuePairs.add(new BasicNameValuePair("type1", id));
        // nameValuePairs.add(new BasicNameValuePair("type1", checkId + ""));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        // nameValuePairs.add(new BasicNameValuePair("shop_type_id", id+""));

        Iterator<Entry<String, Object>> iterator = mapRequest.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            HashMap<String, String> map = (HashMap<String, String>) entry.getValue();
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), map.get("_id")));
        }
        String result = YConn.basedPost(context, YUrl.GET_PRODUCT_LIST, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createProductList(context, result);
    }

    /** 通过筛选获取商品列表 未登录状态 */
    /**
     * @param title
     * @param id
     * @param context
     * @param index
     * @param mapRequest
     * @param notType
     * @param pageSize
     * @param is_new
     * @param order_by_price
     * @param isHotSale      专题详情下面的热门推荐专用
     * @return
     */
    public static List<HashMap<String, Object>> getFilterProductList2(String title, String id, Context context,
                                                                      String index, HashMap<String, Object> mapRequest, String notType, int pageSize, String is_new,
                                                                      String order_by_price, boolean isHotSale) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));

        if (isHotSale) {
            nameValuePairs.add(new BasicNameValuePair("notType", "true"));
            nameValuePairs.add(new BasicNameValuePair("pager.sort", "actual_sales"));
        } else {
            nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
            nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize + ""));
            nameValuePairs.add(new BasicNameValuePair("notType", notType + ""));
            if (!TextUtils.isEmpty(id)) {
                nameValuePairs.add(new BasicNameValuePair("type1", id));
            }
            if (!TextUtils.isEmpty(title)) {
                nameValuePairs.add(new BasicNameValuePair("type_name", title));
            }

            if (null != is_new) {
                nameValuePairs.add(new BasicNameValuePair("pager.sort", "audit_time"));
                nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
            }
            if (null != order_by_price) {
                nameValuePairs.add(new BasicNameValuePair("pager.sort", "shop_se_price"));
                nameValuePairs.add(new BasicNameValuePair("pager.order", order_by_price));
            }
            // nameValuePairs.add(new BasicNameValuePair("type1", checkId +
            // ""));
            // nameValuePairs.add(new BasicNameValuePair("token", YCache
            // .getCacheToken(context)));
            // nameValuePairs.add(new BasicNameValuePair("shop_type_id",
            // id+""));

            Iterator<Entry<String, Object>> iterator = mapRequest.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                HashMap<String, String> map = (HashMap<String, String>) entry.getValue();
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), map.get("_id")));
            }
        }

        String result = YConn.basedPost(context, YUrl.GET_PRODUCT_LIST2, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createProductList(context, result);
    }

    /**
     * 热卖推荐
     *
     * @param context
     * @param index
     * @param pageSize
     * @return
     */
    public static List<HashMap<String, Object>> getTuiJianFilterProductList2(Context context, String index,
                                                                             int pageSize, String code, String is_new, String order_by_price) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String url = YUrl.GET_PRODUCT_LIST2 + "?version=" + versionCode + "&pager.curPage=" + index + "&pager.pageSize="
                + pageSize + "&notType=" + true + "&" + code;
        if (null != is_new) {
            url = url + "&pager.sort=audit_time&pager.order=desc";
        }
        if (null != order_by_price) {
            url = url + "&pager.sort=shop_se_price&pager.order=" + order_by_price;
        }
        String result = YConn.basedPost(context, url, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createProductList(context, result);
    }

    /**
     * 积分商城通过积分兑换
     */
    public static ReturnInfo exchangeGood(Context context, String order_code, String pwd) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        nameValuePairs.add(new BasicNameValuePair("pwd", MD5Tools.MD5(pwd)));
        String result = YConn.basedPost(context, YUrl.EXCHANGE_GOOD, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 获取商品的详情图片
     */
    public static HashMap<String, Object> getDetailsPic(Context context, String shop_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_PRODUCT_PIC_LIST, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createProductPicList(context, result);
    }

    /**
     * 搭配 首页
     */
    public static List<HashMap<String, Object>> getMatch(Context context, String curPager, String pagerSize,
                                                         String type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", curPager));
        nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pagerSize));
        nameValuePairs.add(new BasicNameValuePair("type", type));
        if (YJApplication.instance.isLoginSucess()) {
            String token = YCache.getCacheToken(context);
            nameValuePairs.add(new BasicNameValuePair("token", token));
        }
        String result = YConn.basedPost(context, YUrl.GET_MATCH, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createMatchList(context, result);
    }

    /**
     * 搭配 详情
     */
    public static MatchShop getMatchDetails(Context context, String code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("code", code));
        String result = "";
        if (YJApplication.instance.isLoginSucess()) {
            nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
            result = YConn.basedPost(context, YUrl.GET_MATCH_DETAILS, nameValuePairs);
        } else {
            result = YConn.basedPost(context, YUrl.GET_MATCH_DETAILS_UNLOGIN, nameValuePairs);
        }
        return (result == null || "".equals(result)) ? null : EntityFactory.createMatchDetails(context, result);
    }

    /**
     * 搭配 详情 相关商品推荐
     */
    public static List<HashMap<String, Object>> getMatchDetailsRec(Context context, String code, String type_id)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("code", code));
        nameValuePairs.add(new BasicNameValuePair("type_id", type_id));

        String result = YConn.basedPost(context, YUrl.GET_MATCH_DETAILS_REC, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMatchDetailsRec(context, result);
    }

    /**
     * 搭配 商品属性选择
     */
    public static MatchAttr getMatchAttr(Context context, String shop_code, boolean find) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("find", find + ""));

        String result = YConn.basedPost(context, YUrl.GET_MATCH_ATTR, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMatchAttr(context, result);
    }

    /**
     * 搭配 商品添加购物车
     */
    public static ReturnInfo addMatch(Context context, List<HashMap<String, String>> cartJson, String paired_code)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("cartJson", JSONArray.toJSONString(cartJson).toString()));
        LogYiFu.e("cartJson", JSONArray.toJSONString(cartJson).toString());
        nameValuePairs.add(new BasicNameValuePair("paired_code", paired_code));

        String result = YConn.basedPost(context, YUrl.ADD_MATCH, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 得到我的卖单订单列表
     *
     * @return
     */
    public static List<Order> getMySellOrderList(Context context, Integer index, int status) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
        nameValuePairs.add(new BasicNameValuePair("status", status + ""));
        nameValuePairs.add(new BasicNameValuePair("store_code", YCache.getCacheStore(context).getS_code()));
        String result = YConn.basedPost(context, YUrl.GET_MY_SELL_ORDER_LIST, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createOrderList(context, result);
    }

    /**
     * 得到我的会员卖单订单列表
     *
     * @return
     */
    public static List<Order> getMerchantMySellOrderList(Context context, Integer index, int status, String user_id)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
        nameValuePairs.add(new BasicNameValuePair("status", status + ""));
        nameValuePairs.add(new BasicNameValuePair("store_code", user_id + ""));
        // nameValuePairs.add(new BasicNameValuePair("user_id", user_id + ""));
        String result = YConn.basedPost(context, YUrl.GET_MERCHANT_MY_SELL_ORDER_LIST, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createOrderList(context, result);
    }

    /**
     * 得到首页轮播图数据
     */
    public static HashMap<String, Object> getMainTuijianData(Context context, String type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        if (type != null) {
            nameValuePairs.add(new BasicNameValuePair("type", type));
        }
        // nameValuePairs.add(new BasicNameValuePair("token", YCache
        // .getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_TUIJIAN_DATA, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMainTuijianData(context, result);
    }

    /**
     * 得到首页轮播图数据
     */
    public static HashMap<String, Object> getZeroTuijianData(Context context, String type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        if (type != null) {
            nameValuePairs.add(new BasicNameValuePair("type", type));
        }
        // nameValuePairs.add(new BasicNameValuePair("token", YCache
        // .getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_TUIJIAN_DATA, nameValuePairs);
        LogYiFu.e("零元购轮播图数据", result.toString());
        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createZeroShopData(context, result);
    }

    /**
     * 得到每日金额
     */
    public static List<HashMap<String, Object>> getDailyAmount(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("store_code", YCache.getCacheStore(context).getS_code()));
        String result = YConn.basedPost(context, YUrl.GET_DAILY_AMOUNT, nameValuePairs);
        LogYiFu.e("每日金额数据", result.toString());
        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createDailyAmountData(context, result);
    }

    /**
     * 得到每日回佣
     */
    public static List<HashMap<String, Object>> getDailyRebate(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("store_code", YCache.getCacheStore(context).getS_code()));
        String result = YConn.basedPost(context, YUrl.GET_DAILY_REBATE, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createDailyAmountData(context, result);
    }

    /**
     * 得到每日访客
     */
    public static List<HashMap<String, Object>> getDailyVisitor(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("store_code", YCache.getCacheStore(context).getS_code()));
        String result = YConn.basedPost(context, YUrl.GET_DAILY_VISITOR, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createDailyVisitorData(context, result);
    }

    /**
     * 得到交易记录
     */
    public static List<FundDetail> getTradeRecord(Context context, int index, int type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));
        if (type != 0) {
            nameValuePairs.add(new BasicNameValuePair("type", type + ""));
        }
        String result = YConn.basedPost(context, YUrl.GET_TRADE_LIST, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createFundDetailsData(context, result);
    }

    /**
     * 账户明细余额
     */
    public static List<FundDetail> getRebateRecord(Context context, int index) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));
        nameValuePairs.add(new BasicNameValuePair("type", 2 + ""));

        String result = YConn.basedPost(context, YUrl.GET_REBATE_LIST, nameValuePairs);
        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createFundDetailsData(context, result);
    }

    /**
     * 买家信息
     */
    public static HashMap<String, String> getBuyerInfo(Context context, String order_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        String result = YConn.basedPost(context, YUrl.GET_BUYER_INFO, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createBuyerInfo(context, result);
    }

    /**
     * 待付款--选择多个订单一起支付
     */
    public static HashMap<String, String> payOrders(Context context, String order_codes) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("order_codes", order_codes));
        String result = YConn.basedPost(context, YUrl.PAY_ORDERS, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createOrders(context, result);
    }

    /**
     * 查看退换货
     */
    public static ReturnShop checkPayback(Context context, String order_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        String result = YConn.basedPost(context, YUrl.CHECK_PAYBACK, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createReturnShop(context, result);
    }

    /**
     * 修改头像
     */
    public static UserInfo resetUserPic(Context context, String picPath) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("pic", picPath));
        String result = YConn.basedPost(context, YUrl.UPDATE_USER_IMG, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");

        UserInfo userInfo = (result == null || "".equals(result)) ? null
                : EntityFactory.createUserInfo(context, result);
        YCache.setCacheUser(context, userInfo);// 登陆成功后设置缓存
        return userInfo;
    }

    /**
     * 修改店铺头像
     */

    public static void resetStorePic(Context context, String picPath) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Store store = YCache.getCacheStoreSafe(context);
        nameValuePairs.add(new BasicNameValuePair("s_sign", picPath));
        nameValuePairs.add(new BasicNameValuePair("s_code", store.getS_code()));
        nameValuePairs.add(new BasicNameValuePair("realm", store.getRealm(context)));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        String result = YConn.basedPost(context, YUrl.UPDATE_STORE_IMG, nameValuePairs);
    }

    /*** 得到商品链接 */
    public static String getShopLinks(String shop_code, Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        Store s = YCache.getCacheStoreSafe(context);
        nameValuePairs.add(new BasicNameValuePair("realm", "" + YCache.getCacheUser(context).getUser_id()));
        String result = YConn.basedPost(context, YUrl.GET_SHOP_LINK, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");
        return (result == null || "".equals(result)) ? null : EntityFactory.createShopLink(context, result);
    }

    /*** 得到商品链接 */
    public static String getShopLinksIndiana(String shop_code, Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("getShop", "true"));
        nameValuePairs.add(new BasicNameValuePair("indiana", "" + 1));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        Store s = YCache.getCacheStoreSafe(context);
        nameValuePairs.add(new BasicNameValuePair("realm", s.getRealm(context)));
        String result = YConn.basedPost(context, YUrl.GET_SHOP_LINK, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");
        return (result == null || "".equals(result)) ? null : EntityFactory.createShopLink(context, result);
    }

    /*** 得到套餐商品链接 */
    public static String getPShopLinks(String shop_code, Context context, Boolean isMeal) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("p_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("share", "2"));

        if (!isMeal) {
            nameValuePairs.add(new BasicNameValuePair("p_s", "1"));
        }

        Store s = YCache.getCacheStoreSafe(context);
        nameValuePairs.add(new BasicNameValuePair("realm", s.getRealm(context)));

        // nameValuePairs.add(new BasicNameValuePair("realm", s.getRealm()));
        String result = YConn.basedPost(context, YUrl.P_SHOP_LINK, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");
        return (result == null || "".equals(result)) ? null : EntityFactory.createShopLink(context, result);
    }

    /*** 得到商品链接 */
    public static HashMap<String, String> getShopLink(String shop_code, Context context, String isTrue)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("getShopMessage", isTrue));
        Store s = YCache.getCacheStoreSafe(context);
        nameValuePairs.add(new BasicNameValuePair("realm", "" + YCache.getCacheUser(context).getUser_id()));
        String result = YConn.basedPost(context, YUrl.GET_SHOP_LINK, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");
        return (result == null || "".equals(result)) ? null : EntityFactory.createShareContent(context, result);
    }

    /*** 得到活动商品链接 */
    public static HashMap<String, String> getActiveShopLink(String shop_code, Context context, String isTrue)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        // nameValuePairs.add(new BasicNameValuePair("getShopMessage", isTrue));
        nameValuePairs.add(new BasicNameValuePair("activity", "1"));
        Store s = YCache.getCacheStoreSafe(context);
        nameValuePairs.add(new BasicNameValuePair("realm", s.getRealm(context)));
        String result = YConn.basedPost(context, YUrl.GET_SHOP_LINK, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");
        return (result == null || "".equals(result)) ? null : EntityFactory.createShareContent(context, result);
    }

    /***
     * 搭配购商品 得到商品链接 isPic=true返回图片和链接（用于分享） =false只返回链接（用于联系客服发送宝贝链接）
     */
    public static HashMap<String, String> getMatchShopLinkOrPic(String code, Context context, boolean isPic)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("code", code));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("isPic", isPic + ""));
        // Store s = YCache.getCacheStoreSafe(context);
        // nameValuePairs.add(new BasicNameValuePair("realm", s.getRealm()));
        nameValuePairs.add(new BasicNameValuePair("realm", "" + YCache.getCacheUser(context).getUser_id()));
        String result = YConn.basedPost(context, YUrl.GET_SHARE_SHOP_LINK_MATCH, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMatchShopLinkOrPic(context, result);
    }

    /*** 普通 得到商品链接 */
    public static HashMap<String, String> getShopLinkSpecial(String shop_code, Context context, String isTrue)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("getShop", isTrue));
        Store s = YCache.getCacheStoreSafe(context);
        nameValuePairs.add(new BasicNameValuePair("realm", s.getRealm(context)));
        String result = YConn.basedPost(context, YUrl.GET_SHOP_LINK, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");
        return (result == null || "".equals(result)) ? null : EntityFactory.createShareContent(context, result);
    }

    /*** 得到商品链接 */
    public static HashMap<String, Object> getPshopLink(String p_code, Context context, String isTrue) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("p_code", p_code));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("getPShop", isTrue));
        nameValuePairs.add(new BasicNameValuePair("share", "2"));

        Store s = YCache.getCacheStoreSafe(context);
        nameValuePairs.add(new BasicNameValuePair("realm", s.getRealm(context)));
        // nameValuePairs.add(new BasicNameValuePair("p_s", "1"));
        String result = YConn.basedPost(context, YUrl.GET_P1_SHOP_LINK, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");
        return (result == null || "".equals(result)) ? null : EntityFactory.createpShareContent(context, result);
    }

    /*** 夺宝 得到商品链接 */

    public static HashMap<String, String> getIndianashopLink(String shop_code, Context context, String isTrue)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("indiana", 1 + ""));
        nameValuePairs.add(new BasicNameValuePair("getShop", "" + true));
        Store s = YCache.getCacheStoreSafe(context);
        nameValuePairs.add(new BasicNameValuePair("realm", s.getRealm(context)));
        String result = YConn.basedPost(context, YUrl.GET_SHOP_LINK, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");
        return (result == null || "".equals(result)) ? null : EntityFactory.createShareContent(context, result);
    }


    public static HashMap<String, String> getsuijishopLink(Context context)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        Store s = YCache.getCacheStoreSafe(context);
        String result = YConn.basedPost(context, YUrl.GET_SHOP_LINK_DUOBAO_SUIJI, nameValuePairs);


        return (result == null || "".equals(result)) ? null : EntityFactory.createSuiJiDuoBaoLink(context, result);
    }


    /*** 分享商品的链接 */
    public static HashMap<String, String> getShareShopLink(Context context, String getShop) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        if (!"".equals(getShop))
            nameValuePairs.add(new BasicNameValuePair("getShop", getShop));
        // nameValuePairs.add(new BasicNameValuePair("getShopMessage", isTrue));
        Store s = YCache.getCacheStoreSafe(context);
        nameValuePairs.add(new BasicNameValuePair("realm", s.getRealm(context)));
        String result = YConn.basedPost(context, YUrl.GET_SHARE_SHOP_LINK, nameValuePairs);
        LogYiFu.e("分享链接数据", result.toString());
        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");
        return (result == null || "".equals(result)) ? null : EntityFactory.createShareContent(context, result);
    }

    /*** 分享正价特惠商品的链接 */
    public static HashMap<String, String> getShareShopLinkHobby(Context context, String getShop) throws Exception {


        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("getShop", true + ""));
        nameValuePairs.add(new BasicNameValuePair("realm", YCache.getCacheUserSafe(context).getUser_id() + ""));
        String result = YConn.basedPost(context, YUrl.GET_SHARE_SHOP_LINK_HOBBY, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createShareContent(context, result);
    }


    /*** 分享正价特惠商品的链接 ---签到新分享赢提现专用*/
    public static HashMap<String, String> getShareShopLinkHobbyTX(Context context, String getShop) throws Exception {


        List<NameValuePair> nameValuePairs = new ArrayList<>();


        String sss = "0";

        try {
            sss = SignListAdapter.doValue.split(",")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("getShop", true + ""));
        nameValuePairs.add(new BasicNameValuePair("realm", YCache.getCacheUserSafe(context).getUser_id() + ""));


//        String url = YUrl.GET_SHARE_SHOP_LINK_HOBBY + "?version=" + versionCode + "&token="
//                + YCache.getCacheToken(context) + "&getShop=true" + "&realm=" + YCache.getCacheUserSafe(context).getUser_id() + "&"
//                + sss;


        HashMap<String, String> valueMap = splitValue(sss);


        Iterator<String> iter = valueMap.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = valueMap.get(key);
            nameValuePairs.add(new BasicNameValuePair(key, value));

        }


        String result = YConn.basedPost(context, YUrl.GET_SHARE_SHOP_LINK_HOBBY, nameValuePairs);


        return (result == null || "".equals(result)) ? null : EntityFactory.createShareContent(context, result);

    }


    /*** 分享正价特惠商品的链接 ---签到分享X件商品专用*/
    public static HashMap<String, String> getShareShopLinkHobbySign(Context context) throws Exception {


        List<NameValuePair> nameValuePairs = new ArrayList<>();


        String sss = "0";

        try {
            sss = SignListAdapter.doValue.split(",")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("getShop", true + ""));
        nameValuePairs.add(new BasicNameValuePair("realm", YCache.getCacheUserSafe(context).getUser_id() + ""));


//        String url = YUrl.GET_SHARE_SHOP_LINK_HOBBY + "?version=" + versionCode + "&token="
//                + YCache.getCacheToken(context) + "&getShop=true" + "&realm=" + YCache.getCacheUserSafe(context).getUser_id() + "&"
//                + sss;


        HashMap<String, String> valueMap = splitValue(sss);


        Iterator<String> iter = valueMap.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = valueMap.get(key);
            nameValuePairs.add(new BasicNameValuePair(key, value));

        }


        String result = YConn.basedPost(context, YUrl.GET_SHARE_SHOP_LINK_HOBBY, nameValuePairs);


        return (result == null || "".equals(result)) ? null : EntityFactory.createShareContent(context, result);
    }

    public static HashMap<String, String> splitValue(String urlparam) {
        HashMap<String, String> map = new HashMap<String, String>();
        String[] param = urlparam.split("&");
        for (String keyvalue : param) {
            String[] pair = keyvalue.split("=");
            if (pair.length == 2) {
                map.put(pair[0], pair[1]);
            }
        }
        return map;
    }


    /**
     * 多订单选择最优的优惠券
     */
    public static HashMap<String, Object> multiOrderGetProxCoupon(Context context, String conp) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("result", conp));
        String result = YConn.basedPost(context, YUrl.GET_PROX_COUPON, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);
        return (result == null || "".equals(result)) ? null : EntityFactory.createProxCoupon(context, result);
    }

    /**
     * 单独购买查询优惠券
     *
     * @param context
     * @param conp
     * @return
     */
    public static HashMap<String, Object> onlyBuyGetProxCoupon(Context context, String conp) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("result", conp));
        nameValuePairs.add(new BasicNameValuePair("is_roll", "1"));

        String result = YConn.basedPost(context, YUrl.GET_PROX_COUPON, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);
        return (result == null || "".equals(result)) ? null : EntityFactory.createProxCoupon(context, result);
    }

    /**
     * 得到积分明细
     */
    public static List<HashMap<String, Object>> getIntergralDetail(Context context, Integer index, String expenses,
                                                                   String order, String sort) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));
        // if (!TextUtils.isEmpty(expenses)) {
        nameValuePairs.add(new BasicNameValuePair("expenses", expenses)); // 符号----不选为查询所有的记录.给>为查询收入,给<为查询支出
        // }
        nameValuePairs.add(new BasicNameValuePair("order", order));
        nameValuePairs.add(new BasicNameValuePair("sort", sort));
        String result = YConn.basedPost(context, YUrl.GET_MY_INTEGRAL_LIST, nameValuePairs);

        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactory.createIntergralDetail(context, result);

    }

    /**
     * 得到衣豆明细
     */
    public static List<HashMap<String, Object>> getPearsDetail(Context context, Integer index, String order,
                                                               String sort, int num) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));
        nameValuePairs.add(new BasicNameValuePair("rows", 10 + ""));
        // if (!TextUtils.isEmpty(expenses)) {
        // nameValuePairs.add(new BasicNameValuePair("expenses", expenses)); //
        // 符号----不选为查询所有的记录.给>为查询收入,给<为查询支出
        // }
        nameValuePairs.add(new BasicNameValuePair("order", order));
        nameValuePairs.add(new BasicNameValuePair("sort", sort));
        nameValuePairs.add(new BasicNameValuePair("num", num + ""));
        String result = YConn.basedPost(context, YUrl.GET_PEARS_LIST, nameValuePairs);

        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactory.createPearsDetail(context, result);

    }

    /**
     * 得到积分总额
     */
    public static Integer getMyIntegral(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_MY_INTEGRAL, nameValuePairs);

        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactory.createMyIntergral(context, result);

    }

    /**
     * 修改地址
     *
     * @param context
     * @return
     */
    public static UserInfo updateLocation(Context context, String provinceId, String cityId) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("province", provinceId));
        nameValuePairs.add(new BasicNameValuePair("city", cityId));
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
     * 修改IMEI
     *
     * @param context
     * @return
     */
    public static ReturnInfo updateImei(Context context, String imei) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("imei", imei));
        String result = YConn.basedPost(context, YUrl.UPDATE_USER_INFO, nameValuePairs);

        if (result == null || "".equals(result)) {
            return null;
        }
        // UserInfo user = EntityFactory.createYUser(context, result);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);

        // EntityFactory.createStore(context, result);// 保存店铺数据
        // user.setUsertype(-1);
        // YCache.setCacheUser(context, user);// 登陆成功后设置缓存
        // return user;
    }

    /**
     * 修改MAC
     *
     * @param context
     * @return
     */
    public static ReturnInfo updateMac(Context context, String mac) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("mac", mac));
        String result = YConn.basedPost(context, YUrl.UPDATE_USER_INFO, nameValuePairs);

        if (result == null || "".equals(result)) {
            return null;
        }
        // UserInfo user = EntityFactory.createYUser(context, result);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);

        // EntityFactory.createStore(context, result);// 保存店铺数据
        // user.setUsertype(-1);
        // YCache.setCacheUser(context, user);// 登陆成功后设置缓存
        // return user;
    }

    /**
     * 修改喜好标签
     *
     * @param context
     * @return
     */
    public static ReturnInfo updateMyLike(Context context, String hobby) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("hobby", hobby));

        String result = YConn.basedPost(context, YUrl.UPDATE_USER_INFO, nameValuePairs);

        if (result == null || "".equals(result)) {
            return null;
        }
        // UserInfo user = EntityFactory.createYUser(context, result);
        // EntityFactory.createStore(context, result);// 保存店铺数据
        // user.setUsertype(-1);
        // YCache.setCacheUser(context, user);// 登陆成功后设置缓存
        LogYiFu.e("获取喜好数据", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 修改生日
     *
     * @param context
     * @return
     */
    public static UserInfo updateBirthday(Context context, String selectedDate) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("birthday", selectedDate));
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
     * 个性签名
     *
     * @param context
     * @return
     */
    public static UserInfo updateSign(Context context, String text) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("person_sign", text));
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
     * 得到回佣
     */
    public static void getKickback(Context context, String order_code, boolean isGcode) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String url;
        if (isGcode) {
            nameValuePairs.add(new BasicNameValuePair("g_code", order_code));
            url = YUrl.GET_KICKBACK_LIST;
        } else {
            nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
            url = YUrl.GET_KICKBACK;
        }
        String result = YConn.basedPost(context, url, nameValuePairs);

    }

    /**
     * 普通分享得到积分
     *
     * @return
     */
    public static Void getIntegral(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_INTEGRAL, nameValuePairs);
        return null;

    }

    /**
     * 阿里参数获取
     */
    public static HashMap<String, String> getAliParam(Context context, String order_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));
        String result = YConn.basedPost(context, YUrl.GET_ALI_PARAM, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);
        return (result == null || "".equals(result)) ? null : EntityFactory.createAliParam(context, result);
    }

    /**
     * 微信支付参数获取
     */
    public static HashMap<String, String> getWXParam(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_WX_PARAM, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);
        return (result == null || "".equals(result)) ? null : EntityFactory.createWXParam(context, result);
    }

    /**
     * 阿里参数获取
     */
    public static HashMap<String, String> getInviteCode(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_INVITE_CODE, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);
        return (result == null || "".equals(result)) ? null : EntityFactory.createInviteCode(context, result);
    }

    /**
     * 分享邀请码之后调用此接口.以便记录数据,后期好统计
     */
    public static HashMap<String, String> getShareNumber(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.SHARE_NUMBER, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createShareNumber(context, result);
    }

    /**
     * 兑换验证码
     */
    public static ReturnInfo exchangeInviteCode(Context context, String inviteCode) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("inviteCode", inviteCode));
        String result = YConn.basedPost(context, YUrl.EXCHANGE_INVITE_CODE, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 获取联盟商家主页信息
     *
     * @param context
     * @return
     */
    // public static HashMap<String, String> leagueHomeData(Context context)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // String result = YConn.basedPost(context, YUrl.LEAGUE_BIZ,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.leagueHome(context, result);
    // }

    /**
     * 佣金记录抬头
     *
     * @param context
     * @return
     */
    // public static HashMap<String, Object> earningsDetail(Context context)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // String result = YConn.basedPost(context, YUrl.LEAGUE_BIZ_DETAIL,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.leagueLogDetail(context, result);
    // }

    /** 商家联盟收益统计 */
    // public static HashMap<String, Object> getRevenueStatistics(Context
    // context) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // String result = YConn.basedPost(context, YUrl.GET_REVENUE_STATISTICS,
    // nameValuePairs);
    //
    // // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
    // // null);
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createRevenueStatistics(context, result);
    // }

    /**
     * 佣金记录数据
     *
     * @param context
     * @return
     */
    // public static HashMap<String, Object> YJLog(Context context, String page)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("page", page));
    //
    // nameValuePairs.add(new BasicNameValuePair("order", "desc"));
    //
    // nameValuePairs.add(new BasicNameValuePair("sort", "add_date"));
    //
    // String result = YConn.basedPost(context, YUrl.LEAGUE_BIZ_YJ_LOG,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.leagueYJLog(context, result);
    // }

    /**
     * 佣金记录数据
     *
     * @param context
     * @return
     * @throws Exception
     */
    // public static HashMap<String, Object> TXLog(Context context, String page)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("page", page));
    //
    // nameValuePairs.add(new BasicNameValuePair("order", "desc"));
    //
    // nameValuePairs.add(new BasicNameValuePair("sort", "add_date"));
    //
    // String result = YConn.basedPost(context, YUrl.LEAGUE_BIZ_TX_LOG,
    // nameValuePairs);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.leagueTXLog(context, result);
    // }

    /** 添加商户信息 */
    // public static ReturnInfo bizInfoCommit(Context context, Business biz,
    // String url) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // // nameValuePairs.add(new BasicNameValuePair("business", biz.));
    // Field[] field = biz.getClass().getDeclaredFields();
    // for (int i = 0; i < field.length; i++) {
    // String name = field[i].getName();
    // String invokeName = name.substring(0, 1).toUpperCase() +
    // name.substring(1);
    // String type = field[i].getGenericType().toString();
    // if (type.equals("class java.lang.String")) {
    // Method m = biz.getClass().getMethod("get" + invokeName);
    // String value = (String) m.invoke(biz);
    // if (null != value) {
    // nameValuePairs.add(new BasicNameValuePair(name, value));
    // }
    // }
    //
    // if (type.equals("class java.lang.Integer")) {
    // Method m = biz.getClass().getMethod("get" + invokeName);
    // Integer value = (Integer) m.invoke(biz);
    // if (value != null) {
    // nameValuePairs.add(new BasicNameValuePair(name, value + ""));
    // }
    // }
    //
    // if (type.equals("class java.lang.Double")) {
    // Method m = biz.getClass().getMethod("get" + invokeName);
    // Double value = (Double) m.invoke(biz);
    // if (value != null) {
    // nameValuePairs.add(new BasicNameValuePair(name, value + ""));
    // }
    // }
    //
    // if (type.equals("class java.lang.Long")) {
    // Method m = biz.getClass().getMethod("get" + invokeName);
    // Long value = (Long) m.invoke(biz);
    // if (value != null) {
    // nameValuePairs.add(new BasicNameValuePair(name, value + ""));
    // }
    // }
    //
    // if (type.equals("class java.lang.Character")) {
    // Method m = biz.getClass().getMethod("get" + invokeName);
    // Character value = (Character) m.invoke(biz);
    // if (value != null) {
    // nameValuePairs.add(new BasicNameValuePair(name, value + ""));
    // }
    // }
    // }
    //
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    //
    // String result = YConn.basedPost(context, url, nameValuePairs);
    //
    // // 保存token
    // // String logStringoken = JSONUtils.getString(result, Json.logintoken,
    // // "");
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createRetInfo(context, result);
    // }

    /** 超级合伙人获取会员 */
    // public static List<HashMap<String, Object>> getAllianceMember(Context
    // context, int index) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("curPage", index + ""));
    // // nameValuePairs.add(new BasicNameValuePair("shop_type_id", id+""));
    // String result = YConn.basedPost(context, YUrl.ALLIANCE_MEMBER,
    // nameValuePairs);
    // LogYiFu.e("会员", result.toString());
    // // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
    // // null);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createAllianceMember(context, result);
    // }

    /** 超级合伙人家获取会员的h5会员 */
    // public static List<HashMap<String, Object>> getAllianceH5Member(Context
    // context, int index, String uid)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // nameValuePairs.add(new BasicNameValuePair("curPage", index + ""));
    // nameValuePairs.add(new BasicNameValuePair("uid", uid));
    // // nameValuePairs.add(new BasicNameValuePair("shop_type_id", id+""));
    // String result = YConn.basedPost(context, YUrl.ALLIANCE_MEMBERH5,
    // nameValuePairs);
    // LogYiFu.e("H5会员", result.toString());
    // // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
    // // null);
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createAllianceH5Member(context, result);
    // }

    /** 商家联盟 是否填写资料 */
    // public static HashMap<String, Object> checkMerchaCommit(Context context)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // String result = YConn.basedPost(context, YUrl.CHECK_MERCHASE_COMMIT_INFO,
    // nameValuePairs);
    //
    // // 保存token
    // // String logStringoken = JSONUtils.getString(result, Json.logintoken,
    // // "");
    //
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.creatInfos(context, result);
    // }

    /**
     * 开始激活
     */
    public static ReturnInfo startActive(Context context, String imei, String type, int num) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("imei", imei));
        nameValuePairs.add(new BasicNameValuePair("type", type));

        Boolean psp = SharedPreferencesUtil.getBooleanData(context, "isLoginLogin", false);

        LogYiFu.e("psp", psp + "");

        if (psp) {
            nameValuePairs.add(new BasicNameValuePair("num", num + "")); // 统计用户启动APP的次数
            nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        } else {
            nameValuePairs.add(new BasicNameValuePair("num", 0 + ""));
            nameValuePairs.add(new BasicNameValuePair("token", null));
        }

        String result = YConn.basedPost(context, YUrl.START_ACTIVE, nameValuePairs);


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
        long serviceTime = System.currentTimeMillis();
        if (!j.has("h5money") || null == j.getString("h5money") || j.getString("h5money").equals("null")
                || j.getString("h5money").equals("")) {
        } else {
            try {
                serviceTime = Long.parseLong(j.optString("now"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        YJApplication.serviceDifferenceTime =  serviceTime - System.currentTimeMillis();


        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");
        EntityFactory.saveOrderToken(context, result);
        String aa = YCache.getOrderToken(context);
        LogYiFu.e("order_token", YCache.getOrderToken(context));
        flag = TextUtils.isEmpty(result) ? 1 : new JSONObject(result).optInt("flag");

        return (result == null || "".equals(result)) ? null : EntityFactory.createInfos(context, result);
    }

    /**
     * APP使用时长统计
     */
    public static void APPuseTime(Context context, String value) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("type", "1"));
        nameValuePairs.add(new BasicNameValuePair("value", value));

        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        YConn.basedPost(context, YUrl.USE_TIME, nameValuePairs);

//		Log.e("APPTIEM", value);
    }

    /**
     * APP使用时长统计
     */
    public static void APPuseCount(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("imei", "" + CheckStrUtil.getImei(context)));
        YJApplication.tongJiYet = true;
        YConn.basedPost(context, YUrl.USE_COUNT, nameValuePairs);
    }

    // 统计分享次数
    public static String tongjifenxiangshu(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.TONGJI_SHARE_COUNT, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createShareCount(context, result);
    }

    // 统计谁分享了分享次数
    public static String tongjifenxiang(Context context, String shopCode) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("code", shopCode));
        String result = YConn.basedPost(context, YUrl.TONGJI_FENXIANG, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createShareCount(context, result);
    }

    public static int flag = 1;

    /**
     * 获取供应商号码
     */
    public static String getSuppPhone(Context context, int supp_id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("supp_id", supp_id + ""));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.GET_SUPP_PHONE, nameValuePairs);

        // 保存token
        // String logStringoken = JSONUtils.getString(result, Json.logintoken,
        // "");

        return (result == null || "".equals(result)) ? null : EntityFactory.getSuppPhone(context, result);
    }


    /**
     * 新特卖商品列表
     */
    public static MealShopList getNewMealShopList(Context context,
                                                  String index/*
     * , String type
     */) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));


        nameValuePairs.add(new BasicNameValuePair("p_type", "" + 0));
        // nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize
        // + ""));
        // int typeNew = Integer.parseInt(type) + 1;
        // nameValuePairs.add(new BasicNameValuePair("type", typeNew + ""));
        // //合并分区后不需要改字段
        String result = YConn.basedPost(context, YUrl.GET_PACKAGE_LIST, nameValuePairs);
        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);
        return (result == null || "".equals(result)) ? null : EntityFactory.createNewMealShopList(result);
    }


    /**
     * 0元购获取单品商品品列表
     */
    public static Map<String, List<HashMap<String, Object>>> getZeroShopProductList_0(Context context,
                                                                                      String index/*
     * , String type
     */) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));


        nameValuePairs.add(new BasicNameValuePair("p_type", "" + 0));
        // nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize
        // + ""));
        // int typeNew = Integer.parseInt(type) + 1;
        // nameValuePairs.add(new BasicNameValuePair("type", typeNew + ""));
        // //合并分区后不需要改字段
        String result = YConn.basedPost(context, YUrl.GET_PACKAGE_LIST, nameValuePairs);
        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);
        return (result == null || "".equals(result)) ? null : EntityFactory.createZeroShopProductList(context, result);
    }

    /**
     * 强制浏览
     */
    public static List<HashMap<String, Object>> getForceLook(Context context, int pageSize, String index
            , String is_new, String is_hot, String order_by_price)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("curPage", index + ""));
        // System.out.println("强制浏览走到这了吧");
        nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize + ""));

        if (null != is_new) {
            nameValuePairs.add(new BasicNameValuePair("sort", "add_time"));
            nameValuePairs.add(new BasicNameValuePair("order", "desc"));
        }
//        if (null != is_hot) {
//            nameValuePairs.add(new BasicNameValuePair("pager.sort", "virtual_sales"));
//            nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
//        }
        if (null != order_by_price) {
            nameValuePairs.add(new BasicNameValuePair("sort", "shop_se_price"));
            nameValuePairs.add(new BasicNameValuePair("order", order_by_price));
        }
        String result = YConn.basedPost(context, YUrl.FORCE_LOOK, nameValuePairs);
        // MyLogYiFu.e("卡号列表", result.toString());

        return (result == null || "".equals(result)) ? null : EntityFactory.createForceLook(context, result, false);
    }

    /**
     * 发布--商品
     */
    public static List<HashMap<String, Object>> getFauShop(Context context, int pageSize, String index)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("curPage", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize + ""));
        String result = YConn.basedPost(context, YUrl.QUERYPURCHASE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createMyBugShop(context, result);
    }

    /**
     * 穿搭详情--更多推荐商品
     */
    public static List<HashMap<String, Object>> getMoreShop(Context context, int pageSize, String index,
                                                            String theme_id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("curPage", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize + ""));
        nameValuePairs.add(new BasicNameValuePair("theme_id", theme_id));
//		if (null != is_new) {
//			nameValuePairs.add(new BasicNameValuePair("pager.sort", "audit_time"));
//			nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
//		}
//		if (null != is_hot) {
//			nameValuePairs.add(new BasicNameValuePair("pager.sort", "virtual_sales"));
//			nameValuePairs.add(new BasicNameValuePair("pager.order", "desc"));
//		}
//		if (null != order_by_price) {
//			nameValuePairs.add(new BasicNameValuePair("pager.sort", "shop_se_price"));
//			nameValuePairs.add(new BasicNameValuePair("pager.order", order_by_price));
//		}
        String result = YConn.basedPost(context, YUrl.QUERYTRSLIST, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createForceLook(context, result, true);
    }

    /**
     * 自定义品牌推荐商品
     */
    public static List<HashMap<String, Object>> getCusmtomLeaableShaop(Context context, int pageSize, String index,
                                                                       String theme_id, String only_id, String is_hot, String is_new, String order_by_price) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("curPage", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize + ""));
        nameValuePairs.add(new BasicNameValuePair("theme_id", theme_id));
        nameValuePairs.add(new BasicNameValuePair("only_id", only_id));

        // sort=virtual_sales&order=desc

        if (null != is_new) {
            nameValuePairs.add(new BasicNameValuePair("sort", "audit_time"));
            nameValuePairs.add(new BasicNameValuePair("order", "desc"));
        }
        if (null != is_hot) {
            nameValuePairs.add(new BasicNameValuePair("sort", "virtual_sales"));
            nameValuePairs.add(new BasicNameValuePair("order", "desc"));
        }
        if (null != order_by_price) {
            nameValuePairs.add(new BasicNameValuePair("sort", "shop_se_price"));
            nameValuePairs.add(new BasicNameValuePair("order", order_by_price));
        }

        String result = YConn.basedPost(context, YUrl.QUERYTRSLIST, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createForceLook(context, result, true);
    }

    /**
     * 3293 【AND】 运营数据统计
     */
    public static HashMap<String, String> getOperator(Context context, String key, int type, int tab_type)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        nameValuePairs.add(new BasicNameValuePair("key", key + ""));
        nameValuePairs.add(new BasicNameValuePair("type", type + ""));
        nameValuePairs.add(new BasicNameValuePair("tab_type", tab_type + ""));

        String result = YConn.basedPost(context, YUrl.YUNYINGTONGJI, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createYunYingTongJi(context, result);
    }

    /**
     * 0元购获取套餐商品品列表
     */
    public static Map<String, List<HashMap<String, Object>>> getZeroShopProductList_1(Context context, String index
            /* String type */) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
        nameValuePairs.add(new BasicNameValuePair("p_type", "" + 1));
        // nameValuePairs.add(new BasicNameValuePair("content", ""));
        // nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize
        // + ""));
        // int typeNew = Integer.parseInt(type) + 1;
        // nameValuePairs.add(new BasicNameValuePair("type", typeNew + ""));
        // //合并分区后不需要改字段
        String result = YConn.basedPost(context, YUrl.GET_PACKAGE_LIST, nameValuePairs);
        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);
        return (result == null || "".equals(result)) ? null : EntityFactory.createZeroShopProductList(context, result);
    }

    /**
     * 购物车统计
     */
    public static String getShopCartCount(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_SHOP_CART_COUNT, nameValuePairs);
        return (result == null || "".equals(result)) ? "0" : EntityFactory.createShopCartCount(context, result);
    }

    /**
     * 搭配购 购物车统计 和 购物车倒计时
     */
    public static HashMap<String, Object> getMatchShopCartCount(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_SHOP_CART_COUNT, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createMatchShopCartCount(context, result);
    }

    /**
     * 退款退货信息
     */
    public static HashMap<String, String> getThTkInfo(Context context, int order_shop_id, String return_type)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("order_shop_id", order_shop_id + ""));
        nameValuePairs.add(new BasicNameValuePair("return_type", return_type));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_TK_TH_INFO, nameValuePairs);

        // JSONObject jsonRet = JSONUtils.getJSONObject(result, Json.user,
        // null);

        return (result == null || "".equals(result)) ? null : EntityFactory.createThTkInfo(context, result);
    }

    /**
     * 得到0元购商品属性列表
     *
     * @return
     */
    public static List<StockType> getShopListAttrs(Context context, String shopCodes) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("shop_codes", shopCodes));

        String result = YConn.basedPost(context, YUrl.GET_SHOPS_ATTRS, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createShopsAttrs(context, result);
    }

    /**
     * 0元购 加入购物车
     */
    public static ReturnInfo mealJoinShopCart(Context context, String p_code, int shop_num, String p_seq,
                                              List<StockBean> catJson, String p_type, String postage, double shop_price, double shop_se_price,
                                              int supp_id, int id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        DecimalFormat df = new DecimalFormat("###0.00");
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("p_code", p_code));
        nameValuePairs.add(new BasicNameValuePair("supp_id", supp_id + ""));
        nameValuePairs.add(new BasicNameValuePair("shop_num", shop_num + ""));
        // nameValuePairs.add(new BasicNameValuePair("p_type", p_type + ""));
        // nameValuePairs.add(new BasicNameValuePair("p_seq", p_seq));
        nameValuePairs.add(new BasicNameValuePair("shop_price", df.format(shop_price) + ""));
        nameValuePairs.add(new BasicNameValuePair("shop_se_price", df.format(shop_se_price) + ""));
        nameValuePairs.add(new BasicNameValuePair("store_code", YCache.getCacheStoreSafe(context).getS_code()));
        nameValuePairs.add(new BasicNameValuePair("postage", postage));
        nameValuePairs.add(new BasicNameValuePair("p_s_t_id", p_seq));
        nameValuePairs.add(new BasicNameValuePair("id", id + ""));
        // nameValuePairs.add(new BasicNameValuePair("shop_name", shop_name));
        nameValuePairs.add(new BasicNameValuePair("cartJson", JSONArray.toJSONString(catJson).toString()));

        String result = YConn.basedPost(context, YUrl.MEAL_ADD_SHOPCART, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 0元购 单独下单
     *
     * @return
     */
    public static HashMap<String, Object> submitZeroOrder(Context context, String message, String packageCode,
                                                          String stockType, int package_num, int address_id, String orderToken) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("message", message));
        nameValuePairs.add(new BasicNameValuePair("packageCode", packageCode));
        nameValuePairs.add(new BasicNameValuePair("stockType", stockType));

        // channel=SharedPreferencesUtil.getStringData(context, "tags","");
        LogYiFu.e("abc", channel);
        // nameValuePairs.add(new BasicNameValuePair("channel", channel));

        nameValuePairs.add(new BasicNameValuePair("package_num", package_num + ""));
        nameValuePairs.add(new BasicNameValuePair("address_id", address_id + ""));
        nameValuePairs.add(new BasicNameValuePair("orderToken", orderToken));

        String result = YConn.basedPost(context, YUrl.ADD_ZERO_ORDER, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createZeroOrder(context, result);
    }

    /**
     * 夺宝下单
     *
     * @return
     */
    public static HashMap<String, Object> submitDuobaoOrder(Context context, String message, int address_id, String num, String t, String shop_code)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("message", message));

        // channel=SharedPreferencesUtil.getStringData(context, "tags","");
        LogYiFu.e("abc", channel);
        // nameValuePairs.add(new BasicNameValuePair("channel", channel));

        nameValuePairs.add(new BasicNameValuePair("address_id", address_id + ""));
        nameValuePairs.add(new BasicNameValuePair("num", num + ""));
        nameValuePairs.add(new BasicNameValuePair("t", t + ""));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code + ""));

        String result = YConn.basedPost(context, YUrl.ADD_DUOBAO_DRDER, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createDuobaoOrder(context, result);
    }

    /**
     * 0元购 单独下单(签到)
     *
     * @return
     */
    public static HashMap<String, Object> submitZeroOrderSign(Context context, String message, String shop_code,
                                                              String stockType, int address_id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("message", message));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("stocktype_id", stockType));

        // channel=SharedPreferencesUtil.getStringData(context, "tags","");
        LogYiFu.e("abc", channel);
        // nameValuePairs.add(new BasicNameValuePair("channel", channel));

        nameValuePairs.add(new BasicNameValuePair("address_id", address_id + ""));

        String result = YConn.basedPost(context, YUrl.ADD_ZERO_ORDER_SIGN, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createZeroOrderSign(context, result);
    }

    /**
     * 活动商品提交订单
     *
     * @return
     */
    public static HashMap<String, Object> submitHuoDongSign(Context context, String message, String shop_code,
                                                            String stockType, int address_id, int shop_num, String rollCode) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("message", message));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("stocktype_id", stockType));
        nameValuePairs.add(new BasicNameValuePair("rollCode", "" + rollCode));
        // channel=SharedPreferencesUtil.getStringData(context, "tags","");
        LogYiFu.e("abc", channel);
        // nameValuePairs.add(new BasicNameValuePair("channel", channel));

        nameValuePairs.add(new BasicNameValuePair("address_id", address_id + ""));
        nameValuePairs.add(new BasicNameValuePair("shop_num", shop_num + ""));

        String result = YConn.basedPost(context, YUrl.ADD_ZERO_ORDER_SIGN_HUODONG, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createZeroOrderSign(context, result);
    }

    /**
     * 0元购 分享请求
     *
     * @return
     */
    public static HashMap<String, Object> getSharePShopInfo(Context context, String p_code, boolean getPShop)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("p_code", p_code));
        nameValuePairs.add(new BasicNameValuePair("getPShop", getPShop + ""));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.GET_P_SHOP_LINK, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createP_ShopLink(context, result);
    }

    /**
     * 签到包邮商品请求
     *
     * @return
     */
    public static HashMap<String, Object> getSharePShopInfoDduobao(Context context, String p_code, boolean getPShop)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("p_code", p_code));
        nameValuePairs.add(new BasicNameValuePair("getPShop", getPShop + ""));
        nameValuePairs.add(new BasicNameValuePair("p_s", "1"));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.GET_P_SHOP_LINK, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createP_ShopLink(context, result);
    }

    /**
     * 签到分享特价商品
     *
     * @return
     */
    public static HashMap<String, String> getSignShareZero(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        // nameValuePairs.add(new BasicNameValuePair("version", versionCode));

        String url = YUrl.GET_SIGN_SHARE_ZERO + "?version=" + versionCode + "&token=" + YCache.getCacheToken(context)
                + "&" + SignListAdapter.doValue;

        // urlStr = [NSString
        // stringWithFormat:@"%@shop/getPSShareLink?token=%@&version=%@&%@",URLHTTP,
        // token,VERSION,sharevalue];

        // nameValuePairs.add(new BasicNameValuePair("type", type+""));
        // nameValuePairs.add(new BasicNameValuePair("token",
        // YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, url, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createSignShareZero(context, result);
    }

    /**
     * 下单前要得到的参数
     *
     * @return
     */
    public static HashMap<String, Object> getPreSubmit(Context context, String packageCode) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("packageCode", packageCode));

        String result = YConn.basedPost(context, YUrl.PRE_SUBMIT_ORDER, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.preSubmitOrder(context, result);
    }

    /**
     * 我的积分界面
     *
     * @return
     */
    public static HashMap<String, Object> queryMyIntegral(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.QUERY_MY_INTEGRAL_INFO, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createMyIntegral(context, result);
    }

    /**
     * 做任务得积分
     *
     * @param context
     * @return
     */
    public static Integer doMission(Context context, String id) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("m_id", id));

        String result = YConn.basedPost(context, YUrl.DOMISSION, nameValuePairs);

        if (new JSONObject(result).optInt("status") == 1) {
            HashMap<String, Integer> map = getFinCount(context);
            if (MainFragment.mfragment != null)
                MainFragment.mfragment.setUndo(map.get("H5Count"), map.get("finCount"));
        }
        if (!TextUtils.isEmpty(result)) {
            JSONObject json = new JSONObject(result);
            int num = json.optInt("num", 0);
            int newNum = json.optInt("newNum", 0);
            return num + newNum;
        }

        return null;
    }

    /**
     * 得到是否还有任务
     *
     * @return
     */
    public static HashMap<String, Integer> getFinCount(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrl.GET_FIN_COUNT, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createFinCount(context, result);
    }

    /**
     * 得到我刚下的单
     *
     * @return
     */
    public static Order getMyOrder(Context context, String order_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));

        String result = YConn.basedPost(context, YUrl.GET_MY_ORDER, nameValuePairs);
        LogYiFu.e("werwer", "1" + result);
        return (result == null || "".equals(result)) ? null : EntityFactory.createOrder(context, result);
    }

    public static Order getMyOrderPaysuccss(Context context, String order_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));

        String result = YConn.basedPost(context, YUrl.GET_PAYSUCCESS_ORDER, nameValuePairs);


        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        String orderStr = jsonObject.optString("order");

        JSONObject jos = new JSONObject(orderStr);

        String text = "";

        if (!jos.has("orderShops") || null == jos.getString("orderShops") || jos.getString("orderShops").equals("")) {
        } else {
            text = jos.optString("orderShops");
        }
        List<OrderShop> list = JSON.parseArray(text, OrderShop.class);


        Order order = JSON.parseObject(orderStr, Order.class);

        order.setList(list);

        return order;


    }


    public static void registerOpenId(String unionid, Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        nameValuePairs.add(new BasicNameValuePair("unionid", unionid));

        YConn.basedPost(context, YUrl.YSS_URL_ANDROID + "user/appDownLog", nameValuePairs);
    }

    public static ReturnInfo register2OpenId(String uid, Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        nameValuePairs.add(new BasicNameValuePair("uid", uid));

        String result = YConn.basedPost(context, YUrl.YSS_URL_ANDROID + "user/appDownLog", nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    public static ReturnInfo registerWeiXinToken(String unionid, String uid, String pic, String nickname,
                                                 Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        nameValuePairs.add(new BasicNameValuePair("uid", uid));
        nameValuePairs.add(new BasicNameValuePair("unionid", unionid));
        nameValuePairs.add(new BasicNameValuePair("pic", pic));
        nameValuePairs.add(new BasicNameValuePair("nickname", nickname));

        String result = YConn.basedPost(context, YUrl.WEIXINTOKEN, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 获取会员商品列表
     */

    public static List<Map<String, String>> getMembersShopList(Context context, String index) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index));

        String result = YConn.basedPost(context, YUrl.YSS_URL_ANDROID + "vip/queryShopList", nameValuePairs);
        return TextUtils.isEmpty(result) ? null : EntityFactory.createMembersShopList(context, result);

    }

    /**
     * 获取会员轮播
     *
     * @param context
     * @return
     */
    public static List<ShopOption> getMembersImgList(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.YSS_URL_ANDROID + "vip/queryOption", nameValuePairs);
        return TextUtils.isEmpty(result) ? null : EntityFactory.createMembersImgList(context, result);

    }

    /**
     * 获取会员卡
     *
     * @param context
     * @return
     */
    public static HashMap<String, Object> queryVipCard(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.QUERY_VIP_CARD, nameValuePairs);
        return TextUtils.isEmpty(result) ? null : EntityFactory.createQueryVipCard(context, result);

    }

    /**
     * 获取用户头像
     *
     * @param context
     * @return
     */
    public static String queryUserPic(Context context, String uid) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("uid", uid));
        String result = YConn.basedPost(context, YUrl.QUERY_PIC_BY_UID, nameValuePairs);
        return TextUtils.isEmpty(result) ? null : EntityFactory.createQueryUserPic(context, result);

    }

    /**
     * 超级合伙人卡号列表
     */
    public static List<HashMap<String, Object>> getSupermanCardList(Context context, int index, String search)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("curPage", index + ""));
        if (!"".equals(search) || null == search) {
            nameValuePairs.add(new BasicNameValuePair("seach", search));
        }
        String result = YConn.basedPost(context, YUrl.QUERY_SUPERMAN_CARDLIST, nameValuePairs);
        // MyLogYiFu.e("卡号列表", result.toString());

        return (result == null || "".equals(result)) ? null : EntityFactory.createSupermanCardList(context, result);
    }

    /**
     * 检测微信uid
     */
    public static HashMap<String, Object> checkUid(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.HAVEUID, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createUID(context, result);
    }

    /**
     * 塞进红包
     */
    public static HashMap<String, Object> sendPackets(Context context, String uid, String difficulty, String content,
                                                      String amount, String count) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("uid", uid));
        Double d = Double.parseDouble(difficulty);
        nameValuePairs.add(new BasicNameValuePair("difficulty", d / 100 + ""));
        nameValuePairs.add(new BasicNameValuePair("content", content));
        nameValuePairs.add(new BasicNameValuePair("amount", amount));
        nameValuePairs.add(new BasicNameValuePair("count", count));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.SEND_REDPACKETS, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.creatRedPackets(context, result);
    }

    /**
     * 获取签到页数据
     *
     * @param context
     * @return
     */
    public static HashMap<String, Object> getSignData(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        final long statrtTime = System.currentTimeMillis();

        String result = "";

        // // 是否重新登录过
        // boolean isReLogin = SharedPreferencesUtil.getBooleanData(context,
        // "isrelogin", true);
        // // 完成过签到任务：调签到接口 ---支付成功 ----开店成功
        // boolean signDATAneedRefresh =
        // SharedPreferencesUtil.getBooleanData(context, "signDATAneedRefresh",
        // true);
        // if (signDATAneedRefresh || isReLogin) {
        // result = YConn.basedPost(context, YUrl.SIGN_DATA, nameValuePairs);
        // SharedPreferencesUtil.saveStringData(context, "SignDataDATA",
        // result);
        // } else { // 无需刷新
        // result = SharedPreferencesUtil.getStringData(context, "SignDataDATA",
        // "");
        // }

        result = YConn.basedPost(context, YUrl.SIGN_DATA, nameValuePairs);

        long endTime = System.currentTimeMillis();
        LogYiFu.e("签到界面获取时间", (endTime - statrtTime) + "---signIn2_0/getCount");

        LogYiFu.e("状态", result.toString());
        return TextUtils.isEmpty(result) ? null : EntityFactory.createSignData(context, result);

    }

    /**
     * 最新获奖弹窗通知
     *
     * @param context
     * @return
     */
    public static HashMap<String, Object> querySignNewMoney(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.QUERYMONEY, nameValuePairs);
        LogYiFu.e("状态", result.toString());
        return TextUtils.isEmpty(result) ? null : EntityFactory.createSignNewMoney(context, result);

    }

    /*
     * 获取已完成任务列表
     */
    public static HashMap<String, List<HashMap<String, Object>>> getSignYetList(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        final long statrtTime = System.currentTimeMillis();
        // String result = "";
        //
        // // 当前日期
        // Date nowDate = new Date();
        // SimpleDateFormat nowSDF = new SimpleDateFormat("yyyy-MM-dd");
        // String nowRiqi = nowSDF.format(nowDate);
        //
        // // 拿到保存的日期
        // String compltesigliebiaotime =
        // SharedPreferencesUtil.getStringData(context, "compltesigliebiaotime",
        // "");
        //
        // // 是否重新登录过
        // boolean isReLogin = SharedPreferencesUtil.getBooleanData(context,
        // "isrelogin", true);
        // // 完成过签到任务：调签到接口 ---支付成功 ----开店成功
        // boolean signDATAneedRefresh =
        // SharedPreferencesUtil.getBooleanData(context, "signDATAneedRefresh",
        // true);
        // if (signDATAneedRefresh || isReLogin ||
        // !nowRiqi.equals(compltesigliebiaotime)) { //
        // 如果是签到任务完成-重新登录过-日期不是同一天都得重新获取完成任务列表
        // result = YConn.basedPost(context, YUrl.SIGN_USER_YET,
        // nameValuePairs);
        // SharedPreferencesUtil.saveStringData(context, "compltesigliebiao",
        // result);
        // // 保存拿到列表时的日期
        // Date dtt = new Date();
        // SimpleDateFormat matter11 = new SimpleDateFormat("yyyy-MM-dd");
        // String riqii = matter11.format(dtt);
        // SharedPreferencesUtil.saveStringData(context,
        // "compltesigliebiaotime", riqii);
        //
        // } else {
        // result = SharedPreferencesUtil.getStringData(context,
        // "compltesigliebiao", "");
        // }
        //
        // long endTime = System.currentTimeMillis();
        // LogYiFu.e("签到界面获取时间", (endTime - statrtTime) +
        // "---signIn2_0/userTaskList");
        // LogYiFu.e("任务列表", result.toString());
        String result = YConn.basedPost(context, YUrl.SIGN_USER_YET, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSignYetList(context, result);
    }

    /*
     * 获取店铺二维码链接
     */
    public static HashMap<String, Object> getMyShopLink(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));

        nameValuePairs.add(new BasicNameValuePair("realm", YCache.getCacheStore(context).getRealm(context)));

        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GETMYSHOPLINK, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createMyShopLink(context, result);
    }

    /*
     * 获取签到任务列表(未登录)
     */
    public static HashMap<String, List<HashMap<String, Object>>> getSignList(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
//		final long statrtTime = System.currentTimeMillis();
//
//		String result = "";
//
//		// 未登录列表每天只获取一次
//
//		// 当前日期
//		Date nowDate = new Date();
//		SimpleDateFormat nowSDF = new SimpleDateFormat("yyyy-MM-dd");
//		String nowRiqi = nowSDF.format(nowDate);
//
//		// 拿到保存的日期
//		String unLoginTableTime = SharedPreferencesUtil.getStringData(context, "unlogintabletime", "");
//
//		String rr = result = SharedPreferencesUtil.getStringData(context, "unlogintable", "");
//		if (rr.length() == 0 || rr.equals("") || null == rr) { // 如果本地存的数据不存在就直接去获取
//
//			result = YConn.basedPost(context, YUrl.SIGN_LIST, nameValuePairs);
//			SharedPreferencesUtil.saveStringData(context, "unlogintable", result);
//			// 保存拿到列表时的日期
//			Date dtt = new Date();
//			SimpleDateFormat matter11 = new SimpleDateFormat("yyyy-MM-dd");
//			String riqii = matter11.format(dtt);
//			SharedPreferencesUtil.saveStringData(context, "unlogintabletime", riqii);
//			LogYiFu.e("拿的是", "新数据---未登录列表");
//		} else {
//
//			if (!nowRiqi.equals(unLoginTableTime)) {
        String result = YConn.basedPost(context, YUrl.SIGN_LIST, nameValuePairs);
//				SharedPreferencesUtil.saveStringData(context, "unlogintable", result);
//				// 保存拿到列表时的日期
//				Date dtt = new Date();
//				SimpleDateFormat matter11 = new SimpleDateFormat("yyyy-MM-dd");
//				String riqii = matter11.format(dtt);
//				SharedPreferencesUtil.saveStringData(context, "unlogintabletime", riqii);
//				LogYiFu.e("拿的是", "新数据---未登录列表");
//
//			} else {
//				result = SharedPreferencesUtil.getStringData(context, "unlogintable", "");
//				LogYiFu.e("拿的是", "老数据---未登录列表");
//			}
//		}
//
//		long endTime = System.currentTimeMillis();
//		LogYiFu.e("签到界面获取时间", (endTime - statrtTime) + "---signIn2_0/siTaskList");
        return (result == null || "".equals(result)) ? null : EntityFactory.createSignList(context, result);
    }

    /*
     * 获取签到任务列表(登录后) 解析跟登录后的用的是同一个
     */
    public static HashMap<String, List<HashMap<String, Object>>> getLoginSignList(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
//		final long statrtTime = System.currentTimeMillis();
//
//		String result = "";
//
//		// 登录之后列表每天都得获取一次，但如果用户重新登录也要重新获取
//
//		// 当前日期
//		Date nowDate = new Date();
//		SimpleDateFormat nowSDF = new SimpleDateFormat("yyyy-MM-dd");
//		String nowRiqi = nowSDF.format(nowDate);
//
//		// 拿到保存的日期
//		String unLoginTableTime = SharedPreferencesUtil.getStringData(context, "logintabletime", "");
//
//		// 是否重新登录过
//		boolean isReLogin = SharedPreferencesUtil.getBooleanData(context, "isrelogin", true);
//
//		String rr = result = SharedPreferencesUtil.getStringData(context, "logintable", "");
//		if (rr.length() == 0 || rr.equals("") || null == rr) { // 如果本地存的数据不存在就直接去获取
//			result = YConn.basedPost(context, YUrl.SIGN_LIST_LOGIN, nameValuePairs);
//			SharedPreferencesUtil.saveStringData(context, "logintablesignlist", result);
//			// 保存拿到列表时的日期
//			Date dtt = new Date();
//			SimpleDateFormat matter11 = new SimpleDateFormat("yyyy-MM-dd");
//			String riqii = matter11.format(dtt);
//			SharedPreferencesUtil.saveStringData(context, "logintabletime", riqii);
//			LogYiFu.e("拿的是", "新数据---已登录登录列表");
//
//			SharedPreferencesUtil.saveBooleanData(context, "isrelogin", false);
//		} else {
//
//			if ((!nowRiqi.equals(unLoginTableTime)) || isReLogin) { // 如果重新登录过或者隔天了也重新获取
//				result = YConn.basedPost(context, YUrl.SIGN_LIST_LOGIN, nameValuePairs);
//				SharedPreferencesUtil.saveStringData(context, "logintablesignlist", result);
//				// 保存拿到列表时的日期
//				Date dtt = new Date();
//				SimpleDateFormat matter11 = new SimpleDateFormat("yyyy-MM-dd");
//				String riqii = matter11.format(dtt);
//				SharedPreferencesUtil.saveStringData(context, "logintabletime", riqii);
//				LogYiFu.e("拿的是", "新数据---已登录登录列表");
//
//				SharedPreferencesUtil.saveBooleanData(context, "isrelogin", false);
//
//			} else {
//				result = SharedPreferencesUtil.getStringData(context, "logintablesignlist", "");
//				LogYiFu.e("拿的是", "老数据---已登录登录列表");
//			}
//
//		}
//		long endTime = System.currentTimeMillis();
//		LogYiFu.e("签到界面获取时间", (endTime - statrtTime) + "---signIn2_0/siLogTaskList");


        String result = YConn.basedPost(context, YUrl.SIGN_LIST_LOGIN, nameValuePairs);


        return (result == null || "".equals(result)) ? null : EntityFactory.createSignList(context, result);
    }

    /*
     * 检查是否有补签卡
     */
    // public static HashMap<String, Object> getBuqianka(Context context) throws
    // Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // String result = YConn.basedPost(context, YUrl.SIGN_CHECK_BUQIANKA,
    // nameValuePairs);
    // MyLogYiFu.e("检查", result.toString());
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createCheckbuqianka(context, result);
    // }

    /**
     * 签到
     *
     * @param context
     * @param retime     是否获取余额翻倍过期时间（如果是余额翻倍的奖励需要传true,其他传false）
     * @param share      是否是分享(如果是分享的任务传true，其他传false)
     * @param task_class 大任务类型，必做1 额外2 惊喜3
     * @return
     */
    public static HashMap<String, Object> getSignIn(Context context, boolean retime, boolean share, String signIndex,
                                                    int task_class) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        nameValuePairs.add(new BasicNameValuePair("share", share + ""));
        nameValuePairs.add(new BasicNameValuePair("mac", CheckStrUtil.getMac(context)));

        nameValuePairs.add(new BasicNameValuePair("index_id", signIndex));

        if (task_class == 3) {
            nameValuePairs.add(new BasicNameValuePair("day", "0"));
        } else {
            nameValuePairs.add(new BasicNameValuePair("day", EntityFactory.signDay));
        }

        if (retime) {
            nameValuePairs.add(new BasicNameValuePair("retime", retime + ""));
        }

        SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);

        String result = YConn.basedPost(context, YUrl.SIGN_QIANDAO, nameValuePairs);
        LogYiFu.e("签到结果", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactory.createQiandao(context, result);
    }

    public static ReturnInfo getMoney(Context context) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_MONEY, nameValuePairs);
        return null;
    }

    /*
     * 检测是否开店
     */
    public static HashMap<String, Object> checkStore(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.CHECKSTORE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createCheckStore(context, result);
    }


    /**
     * 微信unionid
     *
     * @param context
     * @return
     */
    public static UserInfo updateUuid(Context context, String unionid, String openid) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("wx_openid", openid));
        nameValuePairs.add(new BasicNameValuePair("unionid", unionid));
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

    public static boolean queryHasJYJL(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.QUERY_HAS_JYJL, nameValuePairs);

        if (result == null || "".equals(result)) {
            return false;
        }

        JSONObject jo = new JSONObject(result);
        return !"0".equals(jo.optString("count"));

    }

    /*
     * 选择客服
     */
    public static HashMap<String, String> choiceKefu(Context context, int type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("type", type + ""));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.CHOICE_KEFU, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createChoiceKefu(context, result);
    }
    /*
     * 获取用户等级表
     */
    // public static HashMap<String, String> getUserGradeTable(Context context)
    // throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // String result = YConn.basedPost(context, YUrl.GETUSERDENGJI,
    // nameValuePairs);
    // return (result == null || "".equals(result)) ? null :
    // EntityFactory.createUserGradeTable(context, result);
    // }

    // TODO:
    /*
     * 卡券消除红点
     */
    public static ReturnInfo deleteCouponsRed(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.COUPONS_RED_STATUE, nameValuePairs);
        return null;
    }

    /*
     * 钱包消除红点
     */
    public static ReturnInfo deleteWalletRed(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.WALLET_RED_STATUE, nameValuePairs);
        return null;
    }

    /*
     * 检查是否参加过签到包邮活动
     */
    public static HashMap<String, Object> checkBaoyou(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.CHECK_ISBAOYOUDINGDAN, nameValuePairs);
        LogYiFu.e("检查", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactory.checkBaoyou(context, result);
    }

    /*
     * 获取购物车数据
     */
    public static HashMap<String, Object> getShopCartData(Context context, int num) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("num ", "1"));
        String result = YConn.basedPost(context, YUrl.GET_CART_DATA, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.shopCartData(context, result, num);
    }

    /**
     * 友盟推送统计
     *
     * @param context
     * @param code_type  统计类型
     * @param message_id 推送消息的ID
     * @return
     */
    public static ReturnInfo UMPushCount(Context context, int code_type, String message_id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        // nameValuePairs.add(new BasicNameValuePair("token",
        // YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("code_type", "" + code_type));
        nameValuePairs.add(new BasicNameValuePair("message_id", "" + message_id));
        String result = YConn.basedPost(context, YUrl.UM_PUSH_COUNT, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.creatUMPushCount(context, result);
    }

    /**
     * 获取九宫图的二维码背景图片
     *
     * @param context
     * @return
     */
    public static String getShareBg(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        String result = YConn.basedPost(context, YUrl.GET_SHARE_BG, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createShareBg(context, result);
    }

    /**
     * 后台设置初次登录是否跳转到微信授权（3.3.8）
     */
    public static String getFirstShouquan(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        String result = YConn.basedPost(context, YUrl.FIRST_SHOUQUAN, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createFirstShouquan(context, result);
    }

    /**
     * 签到分享搭配购获取搭配编号
     */
    public static HashMap<String, Object> getCollectionCode(Context context) throws Exception {

        Store store = YCache.getCacheStoreSafe(context);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("realm", store.getRealm(context)));
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        String result = YConn.basedPost(context, YUrl.GETDAIPEIBIANHAO, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createCollectionCode(context, result);
    }

    /**
     * 获取用户新增粉丝和用户领取奖励弹窗
     */
    public static List<String> getShareAwardsData(Context context) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.CREATESHAREAWARDS, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createShareAwardsData(context, result);
    }

    /**
     * 获取是否开启优惠券升级金券(可随时调用)
     */
    public static HashMap<String, String> getCpgold(Context context) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        final long statrtTime = System.currentTimeMillis();
        String result = YConn.basedPost(context, YUrl.QUERYCPGOLD, nameValuePairs);
        long endTime = System.currentTimeMillis();
        LogYiFu.e("商品详情", (endTime - statrtTime) + "---wallet/CpGold");

        return (result == null || "".equals(result)) ? null : EntityFactory.createCpgold(context, result);
    }

    /**
     * 获取是否开启积分升级金币(可随时调用)
     */
    public static HashMap<String, String> getTwoFoldnessGold(Context context) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        final long statrtTime = System.currentTimeMillis();
        String result = YConn.basedPost(context, YUrl.QUERYTWOFOLDNESSGOLD, nameValuePairs);

        long endTime = System.currentTimeMillis();
        LogYiFu.e("商品详情", (endTime - statrtTime) + "---wallet/twofoldnessGold");

        return (result == null || "".equals(result)) ? null : EntityFactory.createTwoFoldnessGold(context, result);
    }

    /**
     * // * 升级到金币（测试用） //
     */
    // public static void updateJinBI(Context context) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("type", "" + "1"));
    // nameValuePairs.add(new BasicNameValuePair("user_id",
    // YCache.getCacheUser(context).getUser_id() + ""));
    // YConn.basedPost(context, YUrl.YSS_URL_ANDROID_NEW + "test/addtow",
    // nameValuePairs);
    // }

    /**
     * 升级到金券（测试用）
     */
    // public static void updateJinQUAN(Context context) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("type", "" + "1"));
    // nameValuePairs.add(new BasicNameValuePair("user_id",
    // YCache.getCacheUser(context).getUser_id() + ""));
    //
    // YConn.basedPost(context, YUrl.YSS_URL_ANDROID_NEW + "test/addc",
    // nameValuePairs);
    // }

    /**
     *
     */
    public static void delUserType(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        YConn.basedPost(context, YUrl.YSS_URL_ANDROID + "user/delUserType", nameValuePairs);
    }

    /**
     * 活动商品
     */
    public static List<HashMap<String, Object>> getSignActiveShopList(Context context, String index, String pageSize)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("curPage", index));
        nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));
        String result = YConn.basedPost(context, YUrl.SIGN_ACTIVE_SHOP, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSignActiveShopList(context, result);
    }

    /**
     * 活动商品 (带排序)
     */
    public static List<HashMap<String, Object>> getSignActiveShopListSort(Context context, String index, String pageSize
            , String is_new, String is_hot, String order_by_price)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("curPage", index));
        nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));

        if (null != is_new) {
            nameValuePairs.add(new BasicNameValuePair("sort", "add_time"));
            nameValuePairs.add(new BasicNameValuePair("order", "desc"));
        }

        if (null != order_by_price) {
            nameValuePairs.add(new BasicNameValuePair("sort", "shop_se_price"));
            nameValuePairs.add(new BasicNameValuePair("order", order_by_price));
        }

        String result = YConn.basedPost(context, YUrl.SIGN_ACTIVE_SHOP, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSignActiveShopList(context, result);
    }

//    /**
//     * 获取拼团广场列表
//     */
//    public static List<HashMap<String, Object>> queryGroupSquareShopList(Context context, String index, String pageSize)
//            throws Exception {
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
//        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
//        nameValuePairs.add(new BasicNameValuePair("curPage", index));
//        nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));
//        String result = YConn.basedPost(context, YUrl.GROUPSQUARESHOPLIST, nameValuePairs);
//        return (result == null || "".equals(result)) ? null : EntityFactory.createGroupSquareShopList(context, result);
//    }

    /**
     * 获取粉丝数和奖励
     */
    public static HashMap<String, Object> getFansMoney(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.QUERY_FANS_MONEY, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createFansMoney(context, result);
    }

    /*
     * 1号渠道包切换登录界面
     */
    public static HashMap<String, String> get1QudaoLoginStyle(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
//		nameValuePairs.add(new BasicNameValuePair("type", FirstBindPhoneFragmentChanal.lei));
        String result = YConn.basedPost(context, YUrl.LOGINSTYLE1, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createLoginStyle(context, result);
    }

    /**
     * 获取会员活力值
     */
    // public static int getVitality(Context context) throws Exception {
    // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    // nameValuePairs.add(new BasicNameValuePair("version", versionCode));
    // nameValuePairs.add(new BasicNameValuePair("token",
    // YCache.getCacheToken(context)));
    // String result = YConn.basedPost(context, YUrl.GETVITALITY,
    // nameValuePairs);
    // return (result == null || "".equals(result)) ? 0 :
    // EntityFactory.createVitality(context, result);
    // }

    /**
     * 获得免单情况
     */
    public static HashMap<String, String> getFreeUse(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.FREEUSE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createFreeUse(context, result);
    }

    /**
     * 获得更多专题
     */
    public static List<HashMap<String, String>> getMoreSub(Context context, int Page) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("curPage", Page + ""));
        nameValuePairs.add(new BasicNameValuePair("pageSize", "10"));

        String result = YConn.basedPost(context, YUrl.MORESUB, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createMoreSub(context, result);
    }

    /**
     * 获取专题详情
     */
    public static HashMap<String, Object> getSpecialTopicDetils(Context context, String code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("code", code));
        String result = YConn.basedPost(context, YUrl.SPECIALTOPICDETAILS, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSpecialTopicDetails(context, result);
    }

    /**
     * 下单时获取订单可使用余额比例
     */
    public static HashMap<String, String> getOrderMoney(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_ORDER_MONEY, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createOrderAgo(context, result);
    }

    /**
     * 获取抽奖结果
     */
    public static HashMap<String, String> getLuckResult(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GET_LUCK_DRAW, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createLuckResult(context, result);
    }

    /**
     * 提交身份证
     */
    public static HashMap<String, String> submit_card_guid(Context context, String idcard) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", "" + YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("imei", "" + CheckStrUtil.getImei(context)));
        nameValuePairs.add(new BasicNameValuePair("idcard", "" + idcard));
        nameValuePairs.add(new BasicNameValuePair("mac", "" + CheckStrUtil.getMac(context)));
        String result = YConn.basedPost(context, YUrl.SUBMIT_CARD_GUID, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createGuidCard(context, result);
    }

    /** 得到提额度明细 */

    /**
     * @param context
     * @param page    当前页数
     * @param type    不给为查询所有的 1,新增额度2 使用额度
     * @param order   排序,倒序为desc,正须为asc
     * @param sort    排序的字段
     * @return
     */
    public static List<HashMap<String, Object>> getTixianedumingxi(Context context, Integer page, String type,
                                                                   String order, String sort) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", page + ""));
        nameValuePairs.add(new BasicNameValuePair("type", type)); // 不给为查询所有
        // 1,新增额度2
        // 使用额度
        nameValuePairs.add(new BasicNameValuePair("order", order));
        nameValuePairs.add(new BasicNameValuePair("sort", sort));
        String result = YConn.basedPost(context, YUrl.TIXIANEDUMINGXI, nameValuePairs);
        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null
                : EntityFactory.createTiXianEduLiebiaoDongjie(context, result);

    }

    /**
     * 获取额度最新25条奖励
     */
    public static List<HashMap<String, String>> getExtractNewData(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        String result = YConn.basedPost(context, YUrl.EXTRACTLIMITNEWDATA, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createExtractNewData(context, result);
    }

    /**
     * 获取集赞排名
     */
    public static List<HashMap<String, String>> getPraiseRanking(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        String result = YConn.basedPost(context, YUrl.QUERY_PRAISE_RANKING, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createPraiseRanking(context, result);
    }

    /**
     * 获取集赞额度奖励
     */
    public static List<HashMap<String, String>> getPraiseEctractList(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        String result = YConn.basedPost(context, YUrl.QUERY_PRAISE_EXTRACT_LIST, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createPraiseList(context, result);
    }

    /**
     * 获取衣豆最新25条奖励
     */
    public static List<HashMap<String, String>> getYiDouNewData(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        String result = YConn.basedPost(context, YUrl.GETYIDOUNEWDATA, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createYiDouNewData(context, result);
    }

    /*
     * 获取喜好列表
     */
    public static HashMap<String, ArrayList<HashMap<String, String>>> getHobbyList(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.HOBBYLIE, nameValuePairs);
        LogYiFu.e("喜好列表", "调用------" + result);
        return (result == null || "".equals(result)) ? null : EntityFactory.createHobbyList(context, result);
    }

    /**
     * 获取分类热门搜索标签
     */
    public static List<HashMap<String, String>> getHotTag(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        String result = YConn.basedPost(context, YUrl.GETHOTTAG, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createHotTag(context, result);
    }

    /** 得到冻结提现额度明细 */

    /**
     * @param context
     * @param page    当前页数
     * @param order   排序,倒序为desc,正须为asc
     * @param sort    排序的字段
     * @return
     */
    public static List<HashMap<String, Object>> getTixianedumingxiDongjie(Context context, Integer page, String order,
                                                                          String sort) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", page + ""));
        nameValuePairs.add(new BasicNameValuePair("order", order));
        nameValuePairs.add(new BasicNameValuePair("sort", sort));
        String result = YConn.basedPost(context, YUrl.TIXIANEDUMINGXIDONGJIE, nameValuePairs);
        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null
                : EntityFactory.createTiXianEduLiebiaoDongjie(context, result);

    }

    /**
     * 体现额列表
     */
    public static List<HashMap<String, String>> getTixianEduList(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        String result = YConn.basedPost(context, YUrl.GETTIXINEDULIEBIAO, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createTixienEduList(context, result);
    }

    /**
     * 购物车获得我的喜欢列表
     */
    public static List<HashMap<String, Object>> getMyLovewList(Context context, String curPage, String pageSize)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("curPage", curPage));
        nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));
        String result = YConn.basedPost(context, YUrl.GET_MY_LOVE_LIST, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createMyLoveList(context, result);
    }

    /**
     * 购物车修改颜色和尺寸
     */
    public static HashMap<String, String> modifyColorSize(Context context, String id, String color, String size,
                                                          String shop_num, String stock_type_id, String def_pic) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("id", id));
        nameValuePairs.add(new BasicNameValuePair("color", color));
        nameValuePairs.add(new BasicNameValuePair("size", size));
        nameValuePairs.add(new BasicNameValuePair("shop_num", shop_num));
        nameValuePairs.add(new BasicNameValuePair("stock_type_id", stock_type_id));
        nameValuePairs.add(new BasicNameValuePair("def_pic", def_pic));
        String result = YConn.basedPost(context, YUrl.MODIFY_COLOR_SIZE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.modifyColorSize(context, result);
    }

    /**
     * 精品推荐列表
     */
    public static List<CardDataItem> getLikeQua(Context context, int pageSize) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize + ""));
        String result = YConn.basedPost(context, YUrl.LIKE_QUA, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createLikeQua(context, result);
    }

    /**
     * 精品推荐列表 不喜欢 删除
     */
    public static ReturnInfo deleteDisLikeShop(Context context, String shop_codes) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("shop_codes", shop_codes));

        String result = YConn.basedPost(context, YUrl.DELUACC, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createRetInfo(context, result);
    }

    /**
     * 话题广场type = 1 或 密友圈 type = 2
     */
    public static List<HashMap<String, Object>> getIntimateList(Context context, String type, int curPage, String tag)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        if (YJApplication.instance.isLoginSucess()) {
            nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        }
        nameValuePairs.add(new BasicNameValuePair("curPage", curPage + ""));
        nameValuePairs.add(new BasicNameValuePair("pageSize", "30"));
        if (!"".equals(tag)) {
            nameValuePairs.add(new BasicNameValuePair("tag", tag));// 标签
            // ""表示选择全部，不传
        }
        String url = "1".equals(type) ? YUrl.CIRCLETOPICSQUARE : YUrl.CIRCLEHOMEPAGE;
        String result = YConn.basedPost(context, url, nameValuePairs);
        return (result == null || "".equals(result)) ? null
                : EntityFactory.createIntimateList(context, result, type, curPage + "");
    }

    /**
     * 直接评论帖子接口
     */
    // theme_id 帖子id base_user_id 楼主id content 文本 location 位置
    public static ReturnInfo sweetCommentTieZi(Context context, String theme_id, String base_user_id, String content,
                                               String location) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("theme_id", "" + theme_id));
        nameValuePairs.add(new BasicNameValuePair("base_user_id", base_user_id));
        nameValuePairs.add(new BasicNameValuePair("content", content));
        nameValuePairs.add(new BasicNameValuePair("location", location));

        String result = YConn.basedPost(context, YUrl.SWEET_ISSUE_COMMENT_TIEZI, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSweetRetInfo(context, result);
    }

    /**
     * 密友圈详情评论内回复接口
     */
    // * comment_id 评论id receive_user_id 被评论用户id（为空则对为直接对当前楼用户） content 文本
    public static ReturnInfo sweetCommentIn(Context context, String comment_id, String receive_user_id, String content)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("comment_id", "" + comment_id));
        nameValuePairs.add(new BasicNameValuePair("receive_user_id", receive_user_id));
        nameValuePairs.add(new BasicNameValuePair("content", content));

        String result = YConn.basedPost(context, YUrl.SWEET_ISSUE_COMMENT_IN, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSweetRetInfo(context, result);
    }

    /**
     * 密友圈详情点赞接口
     */
    // * this_id 帖子id或评论id * type :1 帖子，2 评论*新增 theme_id 当前帖子id
    public static ReturnInfo sweetZan(Context context, String this_id, String type, String theme_id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("this_id", "" + this_id));
        nameValuePairs.add(new BasicNameValuePair("type", type));
        nameValuePairs.add(new BasicNameValuePair("theme_id", "" + theme_id));
        String result = YConn.basedPost(context, YUrl.SWEET_ZAN, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSweetRetInfo(context, result);
    }

    /**
     * 密友圈详情取消赞接口
     */
    // * this_id 帖子id或评论id * type :1 帖子，2 评论*新增 theme_id 当前帖子id
    public static ReturnInfo sweetRemoveZan(Context context, String this_id, String type, String theme_id)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("this_id", "" + this_id));
        nameValuePairs.add(new BasicNameValuePair("type", type));
        nameValuePairs.add(new BasicNameValuePair("theme_id", "" + theme_id));
        String result = YConn.basedPost(context, YUrl.SWEET_REMOVE_ZAN, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSweetRetInfo(context, result);
    }

    /**
     * 密友圈详情内关注与取消关注接口
     */
    // * friend_user_id 关注的用户id* type 1为添加，2为删除 ，其他直接返回
    public static ReturnInfo sweetAttention(Context context, String friend_user_id, String type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("friend_user_id", "" + friend_user_id));
        nameValuePairs.add(new BasicNameValuePair("type", type));

        String result = YConn.basedPost(context, YUrl.SWEET_ATTENTION, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSweetRetInfo(context, result);
    }

    /**
     * 密友圈删除自己的帖子
     */
    public static ReturnInfo deleteTheme(Context context, String theme_id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("theme_id", "" + theme_id));
        String result = YConn.basedPost(context, YUrl.DELETE_THEME, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSweetRetInfo(context, result);
    }

    /**
     * 密友圈 收藏帖子
     */
    public static ReturnInfo addCollect(Context context, String theme_id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("theme_id", theme_id));
        String result = YConn.basedPost(context, YUrl.ADD_COLLECT, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSweetRetInfo(context, result);
    }

    /**
     * 密友圈取消收藏帖子
     *
     * @param context
     */
    public static ReturnInfo delCollect(Context context, String theme_id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("theme_id", theme_id));
        String result = YConn.basedPost(context, YUrl.DEL_COLLECT, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSweetRetInfo(context, result);
    }

    // 发帖
    // * title 标题
    // * content 文本
    // * pics 图片
    // * tags 标签
    // * location 位置
    // * theme_type 类型 1 精选推荐，2 穿搭，3 普通话题
    // * shop_codes 商品编号
    // * 当theme_type=1时shop_codes ,
    // * 当theme_type=2时type1,type2,supp_label_id,tag_info
    // *customTags 自定义标签，逗号隔开
    public static HashMap<String, String> createFaTie(Context context, String title, String content, String pics,
                                                      String tags, String location, String theme_type, String shop_codes, String customTags, String type1,
                                                      String type2, String style, String tag_info, String jsonSuppLabel) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        nameValuePairs.add(new BasicNameValuePair("title", title));
        nameValuePairs.add(new BasicNameValuePair("content", content));
        nameValuePairs.add(new BasicNameValuePair("pics", pics));
        nameValuePairs.add(new BasicNameValuePair("location", location));
        nameValuePairs.add(new BasicNameValuePair("theme_type", theme_type));
        if ("3".equals(theme_type)) {
            nameValuePairs.add(new BasicNameValuePair("tags", tags));
            // nameValuePairs.add(new BasicNameValuePair("shop_codes",
            // shop_codes));
        }
        nameValuePairs.add(new BasicNameValuePair("customTags", customTags));
        if ("2".equals(theme_type)) {
            nameValuePairs.add(new BasicNameValuePair("type1", type1));
            nameValuePairs.add(new BasicNameValuePair("type2", type2));
            nameValuePairs.add(new BasicNameValuePair("supp_label_id", tag_info));// 品牌id
            nameValuePairs.add(new BasicNameValuePair("tag_info", ""));// 商品标签
            nameValuePairs.add(new BasicNameValuePair("style", style));// 风格
        }
        if ("4".equals(theme_type)) {
            nameValuePairs.add(new BasicNameValuePair("tags", tags));
            nameValuePairs.add(new BasicNameValuePair("jsonSuppLabel", jsonSuppLabel));// 风格
        }
        String result = YConn.basedPost(context, YUrl.ISSUE_TOPIC, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createFatie(context, result);
    }

    // 精选推荐--发帖
    public static HashMap<String, String> createFaTieJingxuan(Context context, String title, String content,
                                                              String pics, String tags, String location, String theme_type, String shop_codes) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("customTags", ""));
        nameValuePairs.add(new BasicNameValuePair("title", ""));
        nameValuePairs.add(new BasicNameValuePair("content", content));
        nameValuePairs.add(new BasicNameValuePair("pics", pics));
        nameValuePairs.add(new BasicNameValuePair("tags", ""));
        nameValuePairs.add(new BasicNameValuePair("location", location));
        nameValuePairs.add(new BasicNameValuePair("theme_type", theme_type));
        nameValuePairs.add(new BasicNameValuePair("shop_codes", shop_codes));

        String result = YConn.basedPost(context, YUrl.ISSUE_TOPIC, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createFatie(context, result);
    }

    /**
     * 密友圈首页轮播图
     */
    public static List<HashMap<String, String>> getIntimateBanner(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        if (YJApplication.instance.isLoginSucess()) {
            nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        }
        String result = YConn.basedPost(context, YUrl.GET_INTIMATE_BANNER, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createIntimateBanner(context, result);
    }

    /**
     * 品牌详情 推荐列表
     *
     * @param context
     */
    public static List<HashMap<String, Object>> getProductListSuppLabel(Context context, String id, String index,
                                                                        int pageSize) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));

        nameValuePairs.add(new BasicNameValuePair("pager.curPage", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pager.pageSize", pageSize + ""));
        nameValuePairs.add(new BasicNameValuePair("supp_label_id", id));

        String url = "";
        if (YJApplication.instance.isLoginSucess()) {
            nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
            url = YUrl.GET_PRODUCT_LIST;
        } else {
            url = YUrl.GET_PRODUCT_LIST2;
        }
        String result = YConn.basedPost(context, url, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createProductList(context, result);
    }

    /**
     * 密友圈详情举报接口
     */
    // * content 文本* theme_id 帖子id
    public static ReturnInfo sweetReport(Context context, String content, String theme_id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("content", "" + content));
        nameValuePairs.add(new BasicNameValuePair("theme_id", theme_id));

        String result = YConn.basedPost(context, YUrl.SWEET_REPORT, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSweetRetInfo(context, result);
    }

    /**
     * 密友圈详情获取热门评论和推荐
     */
    // theme_id 帖子id
    public static HashMap<String, List<HashMap<String, Object>>> getHotCommentList(Context context, String theme_id,
                                                                                   String user_id) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("theme_id", theme_id));
        // nameValuePairs.add(new BasicNameValuePair("user_id", user_id));

        String result = YConn.basedPost(context, YUrl.SWEET_DETAIL_COMMENT_LIST_HOT, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSweetHotCommentList(context, result);
    }

    /**
     * 密友圈详情获取最新评论
     */
    // theme_id 帖子id
    public static List<HashMap<String, Object>> getNewCommentList(Context context, String theme_id, String page,
                                                                  String rows) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("theme_id", theme_id));
        nameValuePairs.add(new BasicNameValuePair("curPage", page));
        nameValuePairs.add(new BasicNameValuePair("pageSize", rows));

        String result = YConn.basedPost(context, YUrl.SWEET_DETAIL_COMMENT_LIST_NEW, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSweetNewCommentList(context, result);
    }

    /**
     * 密友圈详情获取评论-只看楼主
     */
    // theme_id 帖子id
    public static HashMap<String, List<HashMap<String, Object>>> getHostCommentList(Context context, String theme_id,
                                                                                    String theme_user_id, String page, String rows) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("theme_id", theme_id));
        nameValuePairs.add(new BasicNameValuePair("theme_user_id", theme_user_id));
        nameValuePairs.add(new BasicNameValuePair("curPage", page));
        nameValuePairs.add(new BasicNameValuePair("pageSize", rows));

        String result = YConn.basedPost(context, YUrl.SWEET_DETAIL_COMMENT_LIST_HOST, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createSweetHostCommentList(context, result);
    }

    /**
     * 商品详情获取图片压缩比
     */

    public static HashMap<String, String> getImageRadio(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.SHOP_DETAILS_IMAGE_RADIO, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createImageRadio(context, result);
    }


    /**
     * 积攒奖品
     */
    public static List<HashMap<String, String>> praiseRankingList(Context context)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.QUERYCOLLECTIONPRAISE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.praiseRankingList(context, result);
    }

    /**
     * 集赞奖励弹窗（点赞数，获得奖励）
     */
    public static HashMap<String, String> getPraiseMoney(Context context)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.QUERY_PRAISE_MONEY, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createPraiseMoney(context, result);
    }

//    /**
//     * 集赞分享 获取文案
//     */
//    public static List<HashMap<String, String>> getPointShareContent(Context context)
//            throws Exception {
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
//        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
//        String result = YConn.basedPost(context, YUrl.POINT_SHARE_CONTENT, nameValuePairs);
//        return (result == null || "".equals(result)) ? null : EntityFactory.createPointShareContent(context, result);
//    }
//
//    /**
//     * he 集赞奖励规则 文案获取(getPointShareContent 同一个接口取不同字段)
//     */
//    public static HashMap<String, String> getPointGuiZeContent(Context context)
//            throws Exception {
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
//        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
//        String result = YConn.basedPost(context, YUrl.POINT_SHARE_CONTENT, nameValuePairs);
//        return (result == null || "".equals(result)) ? null : EntityFactory.createPointGuiZeContent(context, result);
//    }

    /*
     * 获取衣豆抽奖减半信息 并获取 减半过期时间
     */
    public static HashMap<String, String> getYiDouHalve(Context context)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.YI_DOU_HALVE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createYiDouHalve(context, result);
    }

    /*
     *衣豆减半获取资格(一天只调用一次)
     */
    public static HashMap<String, String> getYiDouHalveAgo(Context context)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.YI_DOU_HALVE_AGO, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createYiDouHalveAgo(context, result);
    }


    /**
     * 夺宝分享成功记录
     */
    public static HashMap<String, Object> IndianashareRecordCount(Context context, String shop_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("shop_code", "" + shop_code));
        String result = YConn.basedPost(context, YUrl.INDIANA_SHARE_RECORD, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createIndianaShareRecord(context, result);
    }

    /**
     * 夺宝分享相关参数
     */
    public static HashMap<String, Object> IndianaShareData(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.INDIANA_SHARE_DATA, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createIndianaShareData(context, result);
    }


    /**
     * 获取获奖感言
     */
    public static HashMap<String, Object> getPraiseVoiceList(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        String result = YConn.basedPost(context, YUrl.PRAISE_VOICE_LIST, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createPraiseVoiceList(context, result);
    }

    /**
     * 夺宝规则 获取文案
     */
//    public static List<String> getIndianaRuleContent(Context context)
//            throws Exception {
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
//        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
//        String result = YConn.basedPost(context, YUrl.POINT_SHARE_CONTENT, nameValuePairs);
//        return (result == null || "".equals(result)) ? null : EntityFactory.createIndinaRuleContent(context, result);
//    }


    /**
     * 拼团一元购下单--开团
     *
     * @return
     */
    public static HashMap<String, Object> submitOneBuyTuan(Context context,
                                                           int address_id, List<ShopCart> carts, HashMap<Integer, String> mapMsg, int vip_type) throws Exception {

        Iterator<Entry<Integer, String>> iterator = mapMsg.entrySet().iterator();
        StringBuffer sbMsg = new StringBuffer();
        StringBuffer sbResult = new StringBuffer();
        while (iterator.hasNext()) {
            Entry<Integer, String> entry = iterator.next();
            sbMsg.append(entry.getKey()).append("^").append(entry.getValue());
            if (iterator.hasNext()) {
                sbMsg.append(",");
            }
        }
        for (int i = 0; i < carts.size(); i++) {
            ShopCart cart = carts.get(i);
            sbResult.append(cart.getShop_num()).append("^").append(cart.getShop_code()).append("^")
                    .append(cart.getStock_type_id());
            if (i != carts.size() - 1) {
                sbResult.append(",");
            }
        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("message", "" + sbMsg));
        nameValuePairs.add(new BasicNameValuePair("address_id", address_id + ""));
        nameValuePairs.add(new BasicNameValuePair("result", "" + sbResult));
        nameValuePairs.add(new BasicNameValuePair("vip_type", "" + vip_type));

        nameValuePairs.add(new BasicNameValuePair("t", "1"));


//        nameValuePairs.add(new BasicNameValuePair("day", "" + EntityFactory.signDay));
//        nameValuePairs.add(new BasicNameValuePair("t", "" + SignListAdapter.tuanClass));
//        if ("2".equals("" + SignListAdapter.tuanClass)) {
//            nameValuePairs.add(new BasicNameValuePair("roll_code", "" + SignListAdapter.pingTuanNum));
//            nameValuePairs.add(new BasicNameValuePair("index_day", "" + SignListAdapter.canTuanIndex));
//        } else {
//            nameValuePairs.add(new BasicNameValuePair("index_day", "" + SignListAdapter.signIndex));
//        }
//


        String result = YConn.basedPost(context, YUrl.ONEBU_SUBNIT_ORDER, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createZeroOrderSign(context, result);
    }


    /**
     * 活动商品提交订单
     *
     * @return
     */
    public static HashMap<String, Object> submitSignGroupShop(Context context,
                                                              int address_id, List<ShopCart> carts, HashMap<Integer, String> mapMsg) throws Exception {

        Iterator<Entry<Integer, String>> iterator = mapMsg.entrySet().iterator();
        StringBuffer sbMsg = new StringBuffer();
        StringBuffer sbResult = new StringBuffer();
        while (iterator.hasNext()) {
            Entry<Integer, String> entry = iterator.next();
            sbMsg.append(entry.getKey()).append("^").append(entry.getValue());
            if (iterator.hasNext()) {
                sbMsg.append(",");
            }
        }
        for (int i = 0; i < carts.size(); i++) {
            ShopCart cart = carts.get(i);
            sbResult.append(cart.getShop_num()).append("^").append(cart.getShop_code()).append("^")
                    .append(cart.getStock_type_id());
            if (i != carts.size() - 1) {
                sbResult.append(",");
            }
        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("message", "" + sbMsg));
        nameValuePairs.add(new BasicNameValuePair("address_id", address_id + ""));
        nameValuePairs.add(new BasicNameValuePair("day", "" + EntityFactory.signDay));
        nameValuePairs.add(new BasicNameValuePair("result", "" + sbResult));
        nameValuePairs.add(new BasicNameValuePair("t", "" + SignListAdapter.tuanClass));
        if ("2".equals("" + SignListAdapter.tuanClass)) {
            nameValuePairs.add(new BasicNameValuePair("roll_code", "" + SignListAdapter.pingTuanNum));
            nameValuePairs.add(new BasicNameValuePair("index_day", "" + SignListAdapter.canTuanIndex));
        } else {
            nameValuePairs.add(new BasicNameValuePair("index_day", "" + SignListAdapter.signIndex));
        }
        String result = YConn.basedPost(context, YUrl.SIGN_GROUP_SHOP_SUBMIT, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactory.createZeroOrderSign(context, result);
    }

    /**
     * 获取拼团新的拼团详情
     */
    public static List<HashMap<String, String>> queryGroupDetailsList(Context context, int type)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("type", type + ""));
        String result = YConn.basedPost(context, YUrl.GROUPSQUERYBYROLL, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createGroupDetaisList(context, result);
    }

    /**
     * 获取拼团新的拼团详情(参团 通过拼团编号查询)
     */
    public static List<HashMap<String, String>> queryGroupDetailsListToCode(Context context, String code)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("code", code + ""));
        String result = YConn.basedPost(context, YUrl.GROUPSQUERYBYROLLTOCODE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createGroupDetaisList(context, result);
    }

    /**
     *
     *
     */
//    public static HashMap<String, String> getGroupContent(Context context)
//            throws Exception {
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
//        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
//        String result = YConn.basedPost(context, YUrl.POINT_SHARE_CONTENT, nameValuePairs);
//        return (result == null || "".equals(result)) ? null : EntityFactory.createGroupContent(context, result);
//    }


    /**
     * 获取拼团初始化数据
     */
    public static HashMap<String, String> queryInitData(Context context)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.GROUP_INIT_DATA, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createGroupinitData(context, result);
    }

    /**
     * 获取拼团初始化数据
     */
    public static HashMap<String, String> queryInitDataOneBuy(Context context, String roll_code)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("roll_code", roll_code));

        String result = YConn.basedPost(context, YUrl.GROUP_INIT_DATA, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createGroupinitData(context, result);
    }


    /**
     * 夺宝商品列表
     */
    public static List<HashMap<String, Object>> getIndianaShopList(Context context, String index, String pageSize, String ShopTypeEnum)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("curPage", index));
        nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));
        nameValuePairs.add(new BasicNameValuePair("ShopTypeEnum", ShopTypeEnum));
        String result = YConn.basedPost(context, YUrl.INDIANA_SHOP_LIST, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createIndianaShopList(context, result);
    }


    /**
     * 夺宝中奖后的弹窗
     */
    public static List<String> getIndianaDialogMessage(Context context)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrl.INDIANA_DIALOG_MESSAGE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createIndianaDialogMessage(context, result);
    }


    /**
     * 夺宝记录——我的参与记录
     */

    public static List<HashMap<String, Object>> yiyuanDuobaoJilu(Context context,
                                                                 String index, String my_win) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token",
                YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));

        nameValuePairs.add(new BasicNameValuePair("sort", "btime"));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));
        nameValuePairs.add(new BasicNameValuePair("pt", "1"));
        nameValuePairs.add(new BasicNameValuePair("my_win", my_win));

        String result = YConn.basedPost(context, YUrl.SnatchJoin,
                nameValuePairs);
        return (result == null || "".equals(result)) ? null :
                EntityFactory.createSnatchJoin(context, result);
    }

    public static OneBuyStaus getOneYuanStatus(Context context) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
//        nameValuePairs.add(new BasicNameValuePair("token",
//                YCache.getCacheToken(context)));


        String result = YConn.basedPost(context, YUrl.GETONYUANSTATUS,
                nameValuePairs);


        return (result == null || "".equals(result)) ? null :
                EntityFactory.creatOnYuanStatus(result);


    }


    public static String getALLDikou(Context context) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token",
                YCache.getCacheToken(context)));


        String result = YConn.basedPost(context, YUrl.GETALLDIKOU,
                nameValuePairs);


        return (result == null || "".equals(result)) ? null :
                EntityFactory.createAllDikou(result);


    }

    public static HashMap<String, String> getALLDikouTongzhi(Context context) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token",
                YCache.getCacheToken(context)));


        String result = YConn.basedPost(context, YUrl.GETALLDIKOU,
                nameValuePairs);


        return (result == null || "".equals(result)) ? null :
                EntityFactory.createAllDikouTongzhi(result);


    }

    public static String getALLDikouKeyong(Context context) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token",
                YCache.getCacheToken(context)));


        String result = YConn.basedPost(context, YUrl.GETALLDIKOU,
                nameValuePairs);


        return (result == null || "".equals(result)) ? null :
                EntityFactory.createAllDikouKeyong(result);


    }

//    public static String getOneBuyZhongjiang(Context context) throws Exception {
//
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
//        nameValuePairs.add(new BasicNameValuePair("token",
//                YCache.getCacheToken(context)));
//
//
//        String result = YConn.basedPost(context, YUrl.GETONEBUYZHONGJIANGSTATUS,
//                nameValuePairs);
//
//
//        return (result == null || "".equals(result)) ? null :
//                EntityFactory.createAllDikouKeyong(result);
//
//
//    }

    /**
     * 通知后台更新1元购抽奖
     *
     * @param context
     * @param order_code
     */

    public static void updateOneBuyChoujiang(Context context, String order_code, boolean chouzhong) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token",
                YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("order_code", order_code));

        if (chouzhong) {
            String result = YConn.basedPost(context, YUrl.UPDATE_ONEBUYCHOUJIANG_FIRST,
                    nameValuePairs);

        } else {
            String result = YConn.basedPost(context, YUrl.UPDATE_ONEBUYCHOUJIANG,
                    nameValuePairs);

        }


//        if (ShopDetailsActivity.isNewUser) {
//            //通知更新商品信息
//            Intent intent = new Intent();
//            intent.setAction(TaskReceiver.onebuysubmitoderend);
//            context.sendBroadcast(intent);
//        }

    }


    public static HashMap<String, String> getOneBuyYHQ(Context context) throws Exception {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token",
                YCache.getCacheToken(context)));


        String result = YConn.basedPost(context, YUrl.GET_YHQ_PT,
                nameValuePairs);

        HashMap<String, String> map = new HashMap<>();

        JSONObject jo = new JSONObject(result);

        if (null == jo) {
            return null;
        }

        if (!"1".equals(jo.optString("status"))) {
            return null;
        }
        map.put("cond", jo.optString("cond"));
        map.put("price", jo.optString("price"));
        return map;
    }


    public static HashMap<String, Object> queryTiXianDetail(Context context, String business_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("business_code", business_code));
        String result = YConn.basedPost(context, YUrl.QUERY_TIXIAN_DETAIL, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createTXdetail(context, result);
    }
}