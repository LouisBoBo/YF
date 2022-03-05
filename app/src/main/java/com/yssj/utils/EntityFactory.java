package com.yssj.utils;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yssj.Json;
import com.yssj.Json.JUserInfo;
import com.yssj.Json.RetInfo;
import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.data.YDBHelper;
import com.yssj.entity.CheckPwdInfo;
import com.yssj.entity.Comment;
import com.yssj.entity.DeliveryAddress;
import com.yssj.entity.DuoBaoJiLu_user;
import com.yssj.entity.FriendCircleTag;
import com.yssj.entity.FundDetail;
import com.yssj.entity.Help;
import com.yssj.entity.IntegralShop;
import com.yssj.entity.Like;
import com.yssj.entity.MatchAttr;
import com.yssj.entity.MatchAttr.ShopAttrBean;
import com.yssj.entity.MatchAttr.StocktypeBean;
import com.yssj.entity.MatchShop;
import com.yssj.entity.MatchShop.AttrList;
import com.yssj.entity.MatchShop.CollocationShop;
import com.yssj.entity.MealShopList;
import com.yssj.entity.MyBankCard;
import com.yssj.entity.OneBuyStaus;
import com.yssj.entity.Order;
import com.yssj.entity.OrderShop;
import com.yssj.entity.QueryEmailInfo;
import com.yssj.entity.QueryPhoneInfo;
import com.yssj.entity.RemainShipInfo;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ReturnShop;
import com.yssj.entity.ShareShop;
import com.yssj.entity.Shop;
import com.yssj.entity.ShopAttr;
import com.yssj.entity.ShopCart;
import com.yssj.entity.ShopComment;
import com.yssj.entity.ShopGroupList;
import com.yssj.entity.ShopOption;
import com.yssj.entity.ShopTag;
import com.yssj.entity.ShopType;
import com.yssj.entity.StockType;
import com.yssj.entity.Store;
import com.yssj.entity.SuppComment;
import com.yssj.entity.SuppLabel;
import com.yssj.entity.TypeTag;
import com.yssj.entity.UserInfo;
import com.yssj.ui.activity.shopdetails.MealShopDetailsActivity;
import com.yssj.ui.fragment.cardselect.CardDataItem;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.utils.sqlite.ShopCartDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntityFactory {

    // ---------------自强-------------------------------------------

    /***
     * 注册返回用户信息
     *
     * @param context
     * @param j
     * @return
     * @throws JSONException
     *             \ ]
     */

    public static final UserInfo createYUser(Context context, JSONObject j) throws JSONException {

        UserInfo user = new UserInfo();
        user.setUser_id(j.getInt(JUserInfo.user_id));
        user.setPhone(j.optString(JUserInfo.phone));
        user.setNickname(j.optString(JUserInfo.nickname));
        user.setEmail(j.optString(JUserInfo.email));
        user.setUser_type(j.optInt("user_type"));

        return user;
    }

    public static final HashMap<String, Object> createLoginsInfos(Context context, String result) throws JSONException {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
                || j.getString("status").equals("")) {
            mapRet.put("status", "-1000");
        } else {
            mapRet.put("status", j.getString(RetInfo.status));
        }

        if (!j.has("message") || null == j.getString("message") || j.getString("message").equals("")) {
            mapRet.put("message", "");
        } else {
            mapRet.put("message", j.getString(RetInfo.message));
        }
        // mapRet.put("device", j.optString("device"));
        if (!j.has("loginTime") || null == j.getString("loginTime") || j.getString("loginTime").equals("null")
                || j.getString("loginTime").equals("")) {
            mapRet.put("loginTime", 0);
        } else {
            mapRet.put("loginTime", j.opt("loginTime"));
        }

        if (!j.has("phone_status") || null == j.getString("phone_status") || j.getString("phone_status").equals("null")
                || j.getString("phone_status").equals("")) {
            mapRet.put("phone_status", 1);// 1为未设置，2为已设置
        } else {
            mapRet.put("phone_status", j.getInt("phone_status"));// 1为未设置，2为已设置
        }

        if (!j.has("email_status") || null == j.getString("email_status") || j.getString("email_status").equals("null")
                || j.getString("email_status").equals("")) {
            mapRet.put("email_status", 1);// 1为未设置，2为已设置
        } else {
            mapRet.put("email_status", j.getInt("email_status"));// 1为未设置，2为已设置
        }

        if (!j.has("paypwd_status") || null == j.getString("paypwd_status")
                || j.getString("paypwd_status").equals("null")) {
            mapRet.put("paypwd_status", -1);
        } else {
            mapRet.put("paypwd_status", j.getInt("paypwd_status"));
        }
        return mapRet;
    }

    public static final ReturnInfo createRetInfo(Context context, String result) throws JSONException {

        ReturnInfo retInfo = new ReturnInfo();
        JSONObject j = new JSONObject(result);
        SharedPreferencesUtil.saveBooleanData(context, "tixianyingdaoisshow", false);
        retInfo.setStatus(j.has("status") ? j.getString(RetInfo.status) : "");
        retInfo.setMessage(j.has("message") ? j.getString(RetInfo.message) : "");
        retInfo.setPwdflag(j.has("pwdflag") ? j.getInt(RetInfo.pwdflag) : 5);

        return retInfo;
    }

    /**
     * 开启余额翻倍
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final HashMap<String, Object> createDoubleBalance(Context context, String result)
            throws JSONException {

        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (1 == jsonObject.getInt("status")) {
            if (!jsonObject.has("now") || null == jsonObject.getString("now")
                    || jsonObject.getString("now").equals("null") || jsonObject.getString("now").equals("")) {
                mapRet.put("now", 0L);
            } else {
                mapRet.put("now", jsonObject.has("now") ? jsonObject.getLong("now") : 0L);
            }
            if (!jsonObject.has("vt") || null == jsonObject.getString("vt") || jsonObject.getString("vt").equals("null")
                    || jsonObject.getString("vt").equals("")) {
                mapRet.put("vt", 0);
            } else {
                mapRet.put("vt", jsonObject.has("vt") ? jsonObject.getInt("vt") : 24);
            }

            return mapRet;
        }
        return null;

    }

    public static final ReturnInfo createRetInfoKaiDian(Context context, String result) throws JSONException {
        LogYiFu.e("开启小店", result + "");

        ReturnInfo retInfo = new ReturnInfo();
        JSONObject j = new JSONObject(result);

        SharedPreferencesUtil.saveStringData(context, Pref.TWOFOLDNESS, "3"); // 余额翻倍倍数

        if (!j.has("t_time") || null == j.getString("t_time") || j.getString("t_time").equals("null")
                || j.getString("t_time").equals("")) {
            SharedPreferencesUtil.saveStringData(context, Pref.END_DATE, "-1"); // 余额翻倍剩余时间
        } else {
            SharedPreferencesUtil.saveStringData(context, Pref.END_DATE,
                    j.has("t_time") ? (j.getLong("t_time") + "") : "-1"); // 余额翻倍剩余时间
        }
        SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, true); // 余额翻倍是否有开启资格
        SharedPreferencesUtil.saveStringData(context, Pref.IS_OPEN, "0");// 是否已开启
        // 0：未开启
        // 1：已开启

        if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
                || j.getString("status").equals("")) {
            retInfo.setStatus("-1000");
        } else {
            retInfo.setStatus(j.has("status") ? j.getString(RetInfo.status) : "");
        }

        if (!j.has("message") || null == j.getString("message") || j.getString("message").equals("")) {
            retInfo.setMessage("");
        } else {
            retInfo.setMessage(j.has("message") ? j.getString(RetInfo.message) : "");
        }

        if (!j.has("pwdflag") || null == j.getString("pwdflag") || j.getString("pwdflag").equals("null")
                || j.getString("pwdflag").equals("")) {
            retInfo.setPwdflag(5);
        } else {
            retInfo.setPwdflag(j.has("pwdflag") ? j.getInt(RetInfo.pwdflag) : 5);
        }
        return retInfo;
    }

    // public static final ReturnInfo createIEMI(Context context, String result)
    // throws JSONException {
    // ReturnInfo retInfo = new ReturnInfo();
    // JSONObject j = new JSONObject(result);
    // retInfo.setStatus(j.has("status") ? j.getString(RetInfo.status) : "");
    // retInfo.setMessage(j.has("message") ? j.getString(RetInfo.message) : "");
    // // retInfo.setIsCart(j.optInt("isCart"));
    //
    // MyLogYiFu.e("返回状态", retInfo.getStatus());
    // MyLogYiFu.e("返回信息", retInfo.getMessage());
    // return retInfo;
    // }

    public static final ReturnInfo createInfos(Context context, String result) throws JSONException {
        ReturnInfo retInfo = new ReturnInfo();
        JSONObject j = new JSONObject(result);
        retInfo.setStatus(j.has("status") ? j.getString(RetInfo.status) : "");
        retInfo.setMessage(j.has("message") ? j.getString(RetInfo.message) : "");


        if (!j.has("halve") || null == j.getString("halve") || j.getString("halve").equals("null")
                || j.getString("halve").equals("")) {
            SharedPreferencesUtil.saveStringData(context, "FUKUANYINDAOTIEMSHIJIAN", "60000");
        } else {
            SharedPreferencesUtil.saveStringData(context, "FUKUANYINDAOTIEMSHIJIAN", j.getString("halve"));
        }

        if (!j.has("shareToNum") || null == j.getString("shareToNum") || j.getString("shareToNum").equals("null")
                || j.getString("shareToNum").equals("") || j.getString("shareToNum").equals("0")) {
            SharedPreferencesUtil.saveStringData(context, Pref.SHARETONUMDUOBAO, "5");//分享满多少次给一次机会
        } else {
            SharedPreferencesUtil.saveStringData(context, Pref.SHARETONUMDUOBAO, j.getString("shareToNum"));
        }

        if (!j.has("shareToDayNum") || null == j.getString("shareToDayNum") || j.getString("shareToDayNum").equals("null")
                || j.getString("shareToDayNum").equals("") || j.getString("shareToDayNum").equals("0")) {
            SharedPreferencesUtil.saveStringData(context, Pref.SHARETODAYNUMDUOBAO, "200");//当天分享次数上限
        } else {
            SharedPreferencesUtil.saveStringData(context, Pref.SHARETODAYNUMDUOBAO, j.getString("shareToDayNum"));
        }
        if (!j.has("dpZheKou") || null == j.getString("dpZheKou") || j.getString("dpZheKou").equals("null")
                || j.getString("dpZheKou").equals("") || j.getString("dpZheKou").equals("0")) {
            SharedPreferencesUtil.saveStringData(context, Pref.DPZHEKOU, "0");
        } else {
            SharedPreferencesUtil.saveStringData(context, Pref.DPZHEKOU, j.getString("dpZheKou"));
        }


        // retInfo.setIsCart(j.optInt("isCart"));
        int isMember = j.has("ismember") ? j.optInt("ismember") : 0;
        UserInfo user = YCache.getCacheUserSafe(context);
        if (null != user) {
            user.setIs_member(isMember + "");
            YCache.setCacheUser(context, user);
        }
        if (j.has("twofoldness")) {
            String s = j.getString("twofoldness");
            LogYiFu.e("helllo", "!!!!!!" + s);
            if (s != null && !("".equals(s)) && !("{}".equals(s))) {
                JSONObject j2 = new JSONObject(s);
                SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, true);
                SharedPreferencesUtil.saveStringData(context, Pref.TWOFOLDNESS,
                        j2.has("twofoldness") ? (j2.getInt("twofoldness") + "") : "-1");
                SharedPreferencesUtil.saveStringData(context, Pref.END_DATE,
                        j2.has("end_date") ? (j2.getLong("end_date") + "") : "-1");
                SharedPreferencesUtil.saveStringData(context, Pref.IS_OPEN,
                        j2.has("is_open") ? (j2.getInt("is_open") + "") : "-1");
            } else {
                SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, false);
            }
        } else {
            SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, false);
            LogYiFu.e("helllo", "ddd");
        }

        LogYiFu.e("返回状态", retInfo.getStatus());
        LogYiFu.e("返回信息", retInfo.getMessage());
        // // 判断是否是星期一 并保存在本地（疯狂星期一）
        // if (!j.has("now") || null == j.getString("now") ||
        // j.getString("now").equals("")) {
        // SharedPreferencesUtil.saveBooleanData(context, Pref.ISMADMONDAY,
        // false);
        // } else {
        // String now = j.optString("now", "0");
        //
        // int dayForWeek = TimeUtils.dayForWeek(now);
        // if (dayForWeek == 1) {
        // SharedPreferencesUtil.saveBooleanData(context, Pref.ISMADMONDAY,
        // true);
        // } else {
        // SharedPreferencesUtil.saveBooleanData(context, Pref.ISMADMONDAY,
        // false);
        // }
        //
        // }

        return retInfo;
    }

    // 银行卡 提现接口返回信息
    public static final HashMap<String, Object> createWithDrawInfo(Context context, String result)
            throws JSONException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);

        if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
                || j.getString("status").equals("")) {
            map.put("status", "-1000");
        } else {
            map.put("status", j.has("status") ? j.getString("status") : "");
        }
        if (!j.has("message") || null == j.getString("message") || j.getString("message").equals("")) {
            map.put("message", "");
        } else {
            map.put("message", j.has("message") ? j.getString("message") : "");
        }

        if (!j.has("flag") || null == j.getString("flag") || j.getString("flag").equals("null")
                || j.getString("flag").equals("")) {
            map.put("flag", "");
        } else {
            map.put("flag", j.has("flag") ? j.optInt("flag") : "");
        }
        if (!j.has("money") || null == j.getString("money") || j.getString("money").equals("null")
                || j.getString("money").equals("")) {
            map.put("money", "0");
        } else {
            map.put("money", j.has("money") ? j.optDouble("money") : "0");
        }
        if (!j.has("pwdflag") || null == j.getString("pwdflag") || j.getString("pwdflag").equals("null")
                || j.getString("pwdflag").equals("")) {
            map.put("pwdflag", 5);
        } else {
            map.put("pwdflag", j.has("pwdflag") ? j.optInt("pwdflag") : 5);
        }

        return map;
    }

    // 微信 提现接口返回信息
    public static final HashMap<String, Object> createWeinXinWithDrawInfo(Context context, String result)
            throws JSONException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);

        if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
                || j.getString("status").equals("")) {
            map.put("status", "-1000");
        } else {
            map.put("status", j.has("status") ? j.getString("status") : "");
        }
        if (!j.has("message") || null == j.getString("message") || j.getString("message").equals("")) {
            map.put("message", "");
        } else {
            map.put("message", j.has("message") ? j.getString("message") : "");
        }



        if (!j.has("business_code") || null == j.getString("business_code") || j.getString("business_code").equals("")) {
            map.put("business_code", "");
        } else {
            map.put("business_code", j.has("business_code") ? j.getString("business_code") : "");
        }

        if (!j.has("flag") || null == j.getString("flag") || j.getString("flag").equals("null")
                || j.getString("flag").equals("")) {
            map.put("flag", "");
        } else {
            map.put("flag", j.has("flag") ? j.optInt("flag") : "");
        }
        if (!j.has("money") || null == j.getString("money") || j.getString("money").equals("null")
                || j.getString("money").equals("")) {
            map.put("money", "0");
        } else {
            map.put("money", j.has("money") ? j.optDouble("money") : "0");
        }
        // if (!j.has("pwdflag") || null == j.getString("pwdflag") ||
        // j.getString("pwdflag").equals("null")
        // || j.getString("pwdflag").equals("")) {
        // map.put("pwdflag", 5);
        // } else {
        // map.put("pwdflag", j.has("pwdflag") ? j.optInt("pwdflag") : 5);
        // }

        return map;
    }

    public static final HashMap<String, Object> createSignRetInfo(Context context, String result) throws JSONException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
                || j.getString("status").equals("")) {
            map.put("status", "-1000");
        } else {
            map.put("status", j.has("status") ? j.getString("status") : "");
        }
        if (!j.has("message") || null == j.getString("message") || j.getString("message").equals("")) {
            map.put("message", "");
        } else {
            map.put("message", j.has("message") ? j.getString("message") : "");
        }
        if (!j.has("integral") || null == j.getString("integral") || j.getString("integral").equals("null")
                || j.getString("integral").equals("")) {
            map.put("integral", "0");
        } else {
            map.put("integral", j.has("integral") ? j.optInt("integral") : "");
        }
        if (!j.has("pwdflag") || null == j.getString("pwdflag") || j.getString("pwdflag").equals("null")
                || j.getString("pwdflag").equals("")) {
            map.put("pwdflag", 5);
        } else {
            map.put("pwdflag", j.has("pwdflag") ? j.optInt("pwdflag") : 5);
        }

        // retInfo.setIsCart(j.optInt("isCart"));
        return map;
    }

    public static final QueryPhoneInfo createQueryPhoneInfo(Context context, String result) throws JSONException {
        QueryPhoneInfo Info = new QueryPhoneInfo();
        JSONObject j = new JSONObject(result);
        if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
                || j.getString("status").equals("")) {
            Info.setStatus("");
        } else {
            Info.setStatus(j.has("status") ? j.getString("status") : "");
        }

        if (!j.has("message") || null == j.getString("message") || j.getString("message").equals("")) {
            Info.setMessage("");
        } else {
            Info.setMessage(j.has("message") ? j.getString("message") : "");
        }

        if (!j.has("bool") || null == j.getString("bool") || j.getString("bool").equals("null")
                || j.getString("bool").equals("")) {
            Info.setBool(false);
        } else {
            Info.setBool(j.has("bool") ? j.getBoolean("bool") : false);
        }

        if (!j.has("phone") || null == j.getString("phone") || j.getString("phone").equals("null")
                || j.getString("phone").equals("")) {
            Info.setPhone("");
        } else {
            Info.setPhone(j.has("phone") ? j.getString("phone") : "");
        }
        return Info;
    }

    public static final QueryEmailInfo createQueryEmailInfo(Context context, String result) throws JSONException {
        QueryEmailInfo emailInfo = new QueryEmailInfo();
        JSONObject j = new JSONObject(result);

        if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
                || j.getString("status").equals("")) {
            emailInfo.setStatus("");
        } else {
            emailInfo.setStatus(j.has("status") ? j.getString("status") : "");
        }

        if (!j.has("message") || null == j.getString("message") || j.getString("message").equals("")) {
            emailInfo.setMessage("");
        } else {
            emailInfo.setMessage(j.has("message") ? j.getString("message") : "");
        }
        if (!j.has("bool") || null == j.getString("bool") || j.getString("bool").equals("null")
                || j.getString("bool").equals("")) {
            emailInfo.setBool(false);
        } else {
            emailInfo.setBool(j.has("bool") ? j.getBoolean("bool") : false);
        }
        if (!j.has("email") || null == j.getString("email") || j.getString("email").equals("null")
                || j.getString("email").equals("")) {
            emailInfo.setEmail("");
        } else {
            emailInfo.setEmail(j.has("email") ? j.getString("email") : "");
        }
        return emailInfo;
    }

    public static final RemainShipInfo createRemainShipInfo(Context context, String result) throws JSONException {
        RemainShipInfo Info = new RemainShipInfo();
        JSONObject j = new JSONObject(result);

        if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
                || j.getString("status").equals("")) {
            Info.setStatus("");
        } else {
            Info.setStatus(j.has("status") ? j.getString("status") : "");
        }
        if (!j.has("message") || null == j.getString("message") || j.getString("message").equals("")) {
            Info.setMessage("");
        } else {
            Info.setMessage(j.has("message") ? j.getString("message") : "");
        }

        if (!j.has("falg") || null == j.getString("falg") || j.getString("falg").equals("null")
                || j.getString("falg").equals("")) {
            Info.setFalg(0);
        } else {
            Info.setFalg(j.has("falg") ? j.getInt("falg") : 0);
        }
        if (!j.has("time") || null == j.getString("time") || j.getString("time").equals("null")
                || j.getString("time").equals("")) {
            Info.setTime(0);
        } else {
            Info.setTime(j.has("time") ? j.getLong("time") : 0);
        }

        return Info;
    }

    // public static final ReturnShop createReturnShop(Context context,
    // String result) throws JSONException {
    // //result = result.replace("null", "\"\"");
    //
    // ReturnShop retShop = new ReturnShop();
    // JSONObject jsonObject = new JSONObject(result);
    //
    // JSONObject jb = new JSONObject(jsonObject.getString("returnShop"));
    //
    // retShop.setAdd_time(jb.optString("add_time"));
    // retShop.setCause(jb.optString("cause"));
    // retShop.setExplain(jb.optString("explain"));
    // retShop.setMoney(jb.optString("money"));
    // retShop.setStatus(jb.optString("status"));
    // retShop.setStore_code(jb.optString("store_code"));
    //
    // return retShop;
    // }

    public static final CheckPwdInfo createCheckPwdInfo(Context context, String result) throws JSONException {
        CheckPwdInfo Info = new CheckPwdInfo();
        JSONObject j = new JSONObject(result);
        if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
                || j.getString("status").equals("")) {
            Info.setStatus("");
        } else {
            Info.setStatus(j.has("status") ? j.getString("status") : "");
        }
        if (!j.has("message") || null == j.getString("message") || j.getString("message").equals("")) {
            Info.setMessage("");
        } else {
            Info.setMessage(j.has("message") ? j.getString("message") : "");
        }

        if (!j.has("flag") || null == j.getString("flag") || j.getString("flag").equals("null")
                || j.getString("flag").equals("")) {
            Info.setFlag("");
        } else {
            Info.setFlag(j.has("flag") ? j.getString("flag") : "");
        }
        return Info;
    }

    // public static final HashMap<String, Object> creatInfos(Context context,
    // String result) throws JSONException {
    // HashMap<String, Object> mapObj = new HashMap<String, Object>();
    // ReturnInfo retInfo = new ReturnInfo();
    // JSONObject j = new JSONObject(result);
    // if (!j.has("status") || null == j.getString("status") ||
    // j.getString("status").equals("null")
    // || j.getString("status").equals("")) {
    // retInfo.setStatus("");
    // } else {
    // retInfo.setStatus(j.has("status") ? j.getString("status") : "");
    // }
    // if (!j.has("message") || null == j.getString("message") ||
    // j.getString("message").equals("")) {
    // retInfo.setMessage("");
    // } else {
    // retInfo.setMessage(j.has("message") ? j.getString("message") : "");
    // }
    // // retInfo.setIsCart(j.optInt("isCart"));
    //
    // if (!j.has("status") || null == j.getString("status") ||
    // j.getString("status").equals("null")
    // || j.getString("status").equals("")) {
    // mapObj.put("status", "");
    // } else {
    // mapObj.put("status", j.has("status") ? j.getString(RetInfo.status) : "");
    // }
    // if (!j.has("message") || null == j.getString("message") ||
    // j.getString("message").equals("")) {
    // mapObj.put("message", "");
    // } else {
    // mapObj.put("message", j.has("message") ? j.getString(RetInfo.message) :
    // "");
    // }
    //
    // if (j.getInt("status") == 1062 || j.getInt("status") == 1) {
    // Business bs = new Business();
    // if (null != j.getString("business")) {
    // bs = JSON.parseObject(j.getString("business"), Business.class);
    // mapObj.put("business", bs);
    // }
    // }
    // LogYiFu.e("获取数据的时候", retInfo.toString());
    // return mapObj;
    // }

    /****
     * 登陆返回用户信息
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static final UserInfo createYUser(Context context, String result) throws Exception {
        UserInfo user = new UserInfo();
        JSONObject jsonObject = new JSONObject(result);

        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        if (jsonObject.has("twofoldness")) {
            String s = jsonObject.getString("twofoldness");
            if (s != null && !("".equals(s)) && !("{}".equals(s))) {
                JSONObject j2 = new JSONObject(s);
                SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, true);
                SharedPreferencesUtil.saveStringData(context, Pref.TWOFOLDNESS,
                        j2.has("twofoldness") ? (j2.getInt("twofoldness") + "") : "-1");
                SharedPreferencesUtil.saveStringData(context, Pref.END_DATE,
                        j2.has("end_date") ? (j2.getLong("end_date") + "") : "-1");
                SharedPreferencesUtil.saveStringData(context, Pref.IS_OPEN,
                        j2.has("is_open") ? (j2.getInt("is_open") + "") : "-1");
                LogYiFu.e("helllo", "!!!!!!***" + j2.getInt("is_open"));
            } else {
                SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, false);
            }
        }
        // JSONObject j2= new JSONObject(jsonObject.getString("status"));
        // user.setStatus(jsonObject.getString("status"));

        // 新用户1元提现引导
        if (jsonObject.has("userType")) { // 有userTypez字段
            // 如果取到的是null ----代表弹出过
            if (null == jsonObject.optString("userType")) {
                SharedPreferencesUtil.saveBooleanData(context, "isYindaoToast", true);
            } else if (jsonObject.optString("userType").equals("null")) {
                // 如果取到的是null字符串也是弹出过
                SharedPreferencesUtil.saveBooleanData(context, "isYindaoToast", true);
            } else if (jsonObject.optString("userType").equals("")) {
                // 如果取到的是""--弹出过
                SharedPreferencesUtil.saveBooleanData(context, "isYindaoToast", true);
            } else {
                // 其他情况---未弹出过
                SharedPreferencesUtil.saveBooleanData(context, "isYindaoToast", false);
            }

        } else {
            // 没有userTypez字段 ----只可能是已经弹出过引导 ----不在弹出
            SharedPreferencesUtil.saveBooleanData(context, "isYindaoToast", true);
        }

        JSONObject j = new JSONObject(jsonObject.getString(Json.userinfo));

        if (!j.has("unionid") || null == j.getString("unionid")) {
            user.setUuid(j.optString(""));
        } else {
            user.setUuid(j.optString("unionid"));
        }
        user.setUser_id(j.getInt(JUserInfo.user_id));

        if (!j.has(JUserInfo.phone) || null == j.getString(JUserInfo.phone)
                || j.getString(JUserInfo.phone).equals("null") || j.getString(JUserInfo.phone).equals("")) {
            user.setPhone("");
        } else {
            user.setPhone(j.optString(JUserInfo.phone));
        }
        if (!j.has(JUserInfo.email) || null == j.getString(JUserInfo.email)
                || j.getString(JUserInfo.email).equals("null") || j.getString(JUserInfo.email).equals("")) {
            user.setEmail("");
        } else {
            user.setEmail(j.optString(JUserInfo.email));
        }
        if (!j.has(JUserInfo.nickname) || null == j.getString(JUserInfo.nickname)
                || j.getString(JUserInfo.nickname).equals("")) {
            user.setNickname("");
        } else {
            user.setNickname(j.optString(JUserInfo.nickname));
        }


        if (!j.has(JUserInfo.add_date) || null == j.getString(JUserInfo.add_date)
                || j.getString(JUserInfo.add_date).equals("")) {
            user.setAdd_date("");
        } else {
            user.setAdd_date(j.optString(JUserInfo.add_date));
        }


        user.setUser_type(j.optInt(JUserInfo.user_type));
        if (!j.has(JUserInfo.account) || null == j.getString(JUserInfo.account)) {
            user.setAccount("");
        } else {
            user.setAccount(j.optString(JUserInfo.account));
        }

        if (!j.has(JUserInfo.user_name) || null == j.getString(JUserInfo.user_name)) {
            user.setUser_name("");
        } else {
            user.setUser_name(j.optString(JUserInfo.user_name));
        }

        if (!j.has(JUserInfo.user_ident) || null == j.getString(JUserInfo.user_ident)) {
            user.setUser_ident("");
        } else {
            user.setUser_ident(j.optString(JUserInfo.user_ident));
        }
        // user.setHome_address(j.optString(JUserInfo.home_address));
        // user.setOccupation(j.optString(JUserInfo.occupation));
        user.setAge(j.optInt(JUserInfo.age));
        user.setGender(j.optInt(JUserInfo.gender));
        if (!j.has(JUserInfo.remarks) || null == j.getString(JUserInfo.remarks)) {
            user.setRemarks("");
        } else {
            user.setRemarks(j.optString(JUserInfo.remarks));
        }

        if (!j.has(JUserInfo.birthday) || null == j.getString(JUserInfo.birthday)) {
            user.setBirthday("");
        } else {
            user.setBirthday(j.optString(JUserInfo.birthday));
        }

        if (!j.has(JUserInfo.pic) || null == j.getString(JUserInfo.pic) || j.getString(JUserInfo.pic).equals("null")) {
            user.setPic("");
        } else {
            user.setPic(j.optString(JUserInfo.pic));
        }

        if (!j.has(JUserInfo.city) || null == j.getString(JUserInfo.city)) {
            user.setCity("");
        } else {
            user.setCity(j.has(JUserInfo.city) ? j.optString(JUserInfo.city) : "");
        }

        if (!j.has(JUserInfo.email_status) || null == j.getString(JUserInfo.email_status)
                || j.getString(JUserInfo.email_status).equals("null")
                || j.getString(JUserInfo.email_status).equals("")) {
            user.setEmail_status(0);
        } else {
            user.setEmail_status(j.has(JUserInfo.email_status) ? j.optInt(JUserInfo.email_status) : 0);
        }

        if (!j.has(JUserInfo.street) || null == j.getString(JUserInfo.street)
                || j.getString(JUserInfo.street).equals("")) {
            user.setStreet("");
        } else {
            user.setStreet(j.has(JUserInfo.street) ? j.optString(JUserInfo.street) : "");
        }

        if (!j.has(JUserInfo.area) || null == j.getString(JUserInfo.area) || j.getString(JUserInfo.area).equals("")) {
            user.setArea("");
        } else {
            user.setArea(j.has(JUserInfo.area) ? j.optString(JUserInfo.area) : "");
        }

        if (!j.has(JUserInfo.province) || null == j.getString(JUserInfo.province)
                || j.getString(JUserInfo.province).equals("null") || j.getString(JUserInfo.province).equals("")) {
            user.setProvince("");
        } else {
            user.setProvince(j.has(JUserInfo.province) ? j.optString(JUserInfo.province) : "");
        }

        if (!j.has("person_sign") || null == j.getString("person_sign") || j.getString("person_sign").equals("null")
                || j.getString("person_sign").equals("")) {
            user.setUserSign("");
        } else {
            user.setUserSign(j.optString("person_sign"));
        }

        if (!j.has("birthday") || null == j.getString("birthday") || j.getString("birthday").equals("null")) {
            user.setBirthday("");
        } else {
            user.setBirthday(j.optString("birthday"));
        }

        if (!j.has("province") || null == j.getString("province") || j.getString("province").equals("null")) {
            user.setProvince("");
        } else {
            user.setProvince(j.optString("province"));
        }

        if (!j.has("city") || null == j.getString("city")) {
            user.setCity("");
        } else {
            user.setCity(j.optString("city"));
        }

        if (!j.has("hobby") || null == j.getString("hobby")) {
            user.setHobby("");
        } else {
            user.setHobby(j.optString("hobby"));
        }

        if (!j.has("code_type") || null == j.getString("code_type") || j.getString("code_type").equals("null")) {
            user.setCode_type("");// 判断是否 是商家预审核
        } else {
            user.setCode_type(j.optString("code_type"));// 判断是否 是商家预审核
        }

        if (!j.has("is_member") || null == j.getString("is_member") || j.getString("is_member").equals("null")) {
            user.setIs_member("");// 判断是否是会员
        } else {
            user.setIs_member(j.optString("is_member"));// 判断是否是会员
        }

        if (!j.has("imei") || null == j.getString("imei") || j.getString("imei").equals("null")) {
            user.setImei("");
        } else {
            user.setImei(j.optString("imei"));
        }
        if (!j.has("mac") || null == j.getString("mac") || j.getString("mac").equals("null")) {
            user.setMac("");
        } else {
            user.setMac(j.optString("mac"));
        }
        // v_ident 用户V 0：为普通用户 ，1：为红V，2：为蓝V
        if (!j.has("v_ident") || null == j.getString("v_ident") || j.getString("v_ident").equals("null")) {
            user.setV_ident("0");
        } else {
            user.setV_ident(j.optString("v_ident"));
        }
        return user;
    }

    /****
     * 登陆返回用户信息
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static final Store createStore(Context context, String result) throws Exception {
        Store store = new Store();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject) || !jsonObject.has("store")) {
            return null;
        }
        String storeStr = jsonObject.has("store") ? jsonObject.getString("store") : "";
        if (null == storeStr || "".equals(storeStr)) {
            return null;
        }
        store = JSON.parseObject(storeStr, Store.class);
        YCache.setCacheStore(context, store);
        return store;
    }

    /****
     * 保存下单令牌
     */
    public static final void saveOrderToken(Context context, String result) throws Exception {
        JSONObject j = new JSONObject(result);

        if (!j.has("orderToken") || null == j.getString("orderToken") || j.getString("orderToken").equals("null")
                || j.getString("orderToken").equals("")) {
            YCache.saveOrderToken(context, "");
        } else {
            YCache.saveOrderToken(context, j.optString("orderToken"));
        }
    }

    /***
     * Help列表
     */
    public static final HashMap<String, Object> createHelpList(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        List<Help> helps = new ArrayList<Help>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(jsonObject.has("helps") ? jsonObject.getString("helps") : "");
        for (int i = 0; i < jsonArray.length(); i++) {
            Help help = new Help();
            JSONObject j = (JSONObject) jsonArray.opt(i);
            help = JSON.parseObject(j.toString(), Help.class);
            helps.add(help);
        }
        mapRet.put("helps", helps);

        String jsonType = jsonObject.has("helpType") ? jsonObject.getString("helpType") : "";
        LinkedHashMap<Integer, String> retInfo = JSON.parseObject(jsonType,
                new TypeReference<LinkedHashMap<Integer, String>>() {
                });
        mapRet.put("helpType", retInfo);
        return mapRet;
    }

    /**
     * 客服系统自动提醒热门问题
     */
    public static final HashMap<String, Object> createHelpCentre(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        List<Help> helps = new ArrayList<Help>();

        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        long time = 0L;
        if (!jsonObject.has("time") || null == jsonObject.getString("time")
                || jsonObject.getString("time").equals("null") || jsonObject.getString("time").equals("")) {
        } else {
            time = jsonObject.getLong("time");
        }
        mapRet.put("time", time);

        JSONArray jsonArray = new JSONArray(jsonObject.getString("helpList"));
        for (int i = 0; i < jsonArray.length(); i++) {
            Help help = new Help();
            JSONObject j = (JSONObject) jsonArray.opt(i);
            help = JSON.parseObject(j.toString(), Help.class);
            helps.add(help);
        }
        mapRet.put("helpList", helps);

        String jsonType = "";
        if (!jsonObject.has("helpType") || null == jsonObject.getString("helpType")
                || jsonObject.getString("helpType").equals("")) {
        } else {
            jsonType = jsonObject.getString("helpType");
        }
        LinkedHashMap<Integer, String> retInfo = JSON.parseObject(jsonType,
                new TypeReference<LinkedHashMap<Integer, String>>() {
                });
        mapRet.put("helpType", retInfo);
        return mapRet;
    }

    /***
     * Help详情
     */
    public static final Help createHelp(Context context, String result) throws Exception {

        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        JSONObject obj = jsonObject.getJSONObject("question");
        Help help = new Help();
        help = JSON.parseObject(obj.toString(), Help.class);
        return help;
    }

    public static final HashMap<String, String> createVersionInfo(Context context, String result) throws JSONException {
        HashMap<String, String> retInfo = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        retInfo.put("status", j.getString("status"));
        retInfo.put("message", j.getString("message"));

        if (!j.has("version_no") || null == j.getString("version_no") || j.getString("version_no").equals("")) {
            retInfo.put("version_no", "");
        } else {
            retInfo.put("version_no", j.getString("version_no"));
        }

        if (!j.has("path") || null == j.getString("path") || j.getString("path").equals("")) {
            retInfo.put("path", "");// 下载路径
        } else {
            retInfo.put("path", j.getString("path"));// 下载路径
        }

        if (!j.has("is_update") || null == j.getString("is_update") || j.getString("is_update").equals("null")
                || j.getString("is_update").equals("")) {
            retInfo.put("is_update", "");// 是否强制更新 0：否 1：是
        } else {
            retInfo.put("is_update", j.getString("is_update"));// 是否强制更新 0：否 1：是
        }

        if (!j.has("msg") || null == j.getString("msg") || j.getString("msg").equals("")) {
            retInfo.put("msg", "");// 更新的内容
        } else {
            retInfo.put("msg", j.getString("msg"));// 更新的内容
        }
        return retInfo;
    }

    /***
     * 订单列表
     */
    public static final List<Order> createOrderList(Context context, String result) throws Exception {

        List<Order> orders = new ArrayList<Order>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        JSONArray jsonArray = new JSONArray(jsonObject.getString("orders"));
        for (int i = 0; i < jsonArray.length(); i++) {

            Order order = new Order();
            JSONObject j = (JSONObject) jsonArray.opt(i);
            String text = "";

            if (!j.has("orderShops") || null == j.getString("orderShops") || j.getString("orderShops").equals("")) {
            } else {
                text = j.optString("orderShops");
            }
            List<OrderShop> list = JSON.parseArray(text, OrderShop.class);
            order = JSON.parseObject(j.toString(), Order.class);
            // if (list != null && list.size() > 0) {
            // order.setList(list);
            // }

            // if (!j.has("pay_money") || null == j.getString("pay_money") ||
            // j.getString("pay_money").equals("")) {
            // order.setPay_money(0.0);
            // } else {
            // order.setPay_money(j.getDouble("pay_money"));
            // }

            order.setList(list);
            orders.add(order);
        }

        return orders;
    }

    /***
     * 卡券说明
     */
    public static final HashMap<String, String> createKaquan(Context context, String result) throws Exception {

        HashMap<String, String> retInfo = new HashMap<String, String>();

        LogYiFu.e("Kr", result + "");
        JSONObject j = new JSONObject(result);
        if (j.getString("status").equals(1 + "")) {
            String data = "";
            if (!j.has("data") || null == j.getString("data") || j.getString("data").equals("")) {
            } else {
                data = j.getString("data");
            }
            retInfo.put("data", data);
        }
        return retInfo;
    }

    /***
     * 获得夺宝参入号码
     */
    public static final HashMap<String, String> createDuoBaoNumber(Context context, String result) throws Exception {

        HashMap<String, String> retInfo = new HashMap<String, String>();

        LogYiFu.e("Kr", result + "");
        JSONObject j = new JSONObject(result);
        if (j.getString("status").equals(1 + "")) {
            String data = "";
            if (!j.has("data") || null == j.getString("data") || j.getString("data").equals("")) {
            } else {
                data = j.getString("data");
            }
            retInfo.put("data", data);
        }
        return retInfo;
    }

    /***
     * 订单列表modify(待用)
     */
    public static final HashMap<String, Object> createOrderListmodify(Context context, String result) throws Exception {
        HashMap<String, Object> retInfo = new HashMap<String, Object>();
        List<Order> orders = new ArrayList<Order>();
        JSONObject jsonObject = new JSONObject(result);

        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!jsonObject.has("now") || null == jsonObject.getString("now") || jsonObject.getString("now").equals("null")
                || jsonObject.getString("now").equals("")) {
            retInfo.put("now", (long) 0L);
        } else {
            retInfo.put("now", jsonObject.getLong("now"));
        }
        JSONArray jsonArray = new JSONArray(jsonObject.getString("orders"));
        for (int i = 0; i < jsonArray.length(); i++) {
            Order order = new Order();
            JSONObject j = (JSONObject) jsonArray.opt(i);
            String text = "";
            if (!j.has("orderShops") || null == j.getString("orderShops") || j.getString("orderShops").equals("")) {
            } else {
                text = j.optString("orderShops");
            }
            List<OrderShop> list = JSON.parseArray(text, OrderShop.class);
            order = JSON.parseObject(j.toString(), Order.class);
            if (list != null && list.size() > 0) {
                order.setList(list);
            }
            orders.add(order);
        }
        retInfo.put("orders", orders);
        return retInfo;
    }

    /***
     * 资金明细列表
     */
    public static final List<FundDetail> createFundList(Context context, String result) throws Exception {

        List<FundDetail> funds = new ArrayList<FundDetail>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(jsonObject.getString("fundDetails"));
        for (int i = 0; i < jsonArray.length(); i++) {
            FundDetail fund = new FundDetail();
            JSONObject j = (JSONObject) jsonArray.opt(i);
            fund = JSON.parseObject(j.toString(), FundDetail.class);
            funds.add(fund);
        }
        return funds;
    }

    /***
     * 我的银行卡列表
     */
    public static final List<MyBankCard> createCardList(Context context, String result) throws Exception {

        List<MyBankCard> bankCards = new ArrayList<MyBankCard>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(jsonObject.getString("bankCards"));
        for (int i = 0; i < jsonArray.length(); i++) {
            MyBankCard bankCard = new MyBankCard();
            JSONObject j = (JSONObject) jsonArray.opt(i);
            bankCard = JSON.parseObject(j.toString(), MyBankCard.class);
            bankCards.add(bankCard);
        }
        return bankCards;
    }

    /***
     * 我的卡券列表
     */
    public static final List<HashMap<String, Object>> createMyCouponListNew(Context context, String result)
            throws Exception {
        String is_open = SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_IS_OPEN, "0");
        String is_use = SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_IS_USE, "");
        String c_id = SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_C_ID, "");// 升级金券的优惠券
        List<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }

        JSONArray ja = new JSONArray(j.getString("data"));
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = (JSONObject) ja.opt(i);

            HashMap<String, Object> map = new HashMap<String, Object>();
            int id = 0;
            if (!jo.has("id") || null == jo.getString("id") || jo.getString("id").equals("null")
                    || jo.getString("id").equals("")) {
                map.put("id", 0);
            } else {
                id = jo.optInt("id");
                map.put("id", jo.has("id") ? jo.optInt("id") : 0);
            }


            if (!jo.has("c_type") || null == jo.getString("c_type") || jo.getString("c_type").equals("null")
                    || jo.getString("c_type").equals("")) {
                map.put("c_type", 0);
            } else {
                map.put("c_type", jo.has("c_type") ? jo.optInt("c_type") : 0);
            }

            if (!jo.has("c_price") || null == jo.getString("c_price") || jo.getString("c_price").equals("null")
                    || jo.getString("c_price").equals("")) {
                map.put("c_price", 0);
            } else {
                map.put("c_price", jo.has("c_price") ? jo.optDouble("c_price") : 0);
            }


            if (!jo.has("c_cond") || null == jo.getString("c_cond") || jo.getString("c_cond").equals("null")
                    || jo.getString("c_cond").equals("")) {
                map.put("c_cond", 0);
            } else {
                map.put("c_cond", jo.has("c_cond") ? jo.optString("c_cond") : 0);
            }


            if (!jo.has("c_overlying") || null == jo.getString("c_overlying")
                    || jo.getString("c_overlying").equals("null") || jo.getString("c_overlying").equals("")) {
                map.put("c_overlying", "");
            } else {
                map.put("c_overlying", jo.has("c_overlying") ? jo.optString("c_overlying") : "");
            }

            if (!jo.has("user_id") || null == jo.getString("user_id") || jo.getString("user_id").equals("null")
                    || jo.getString("user_id").equals("")) {
                map.put("user_id", "");
            } else {
                map.put("user_id", jo.has("user_id") ? jo.optString("user_id") : "");
            }

            if (!jo.has("s_code") || null == jo.getString("s_code") || jo.getString("s_code").equals("null")
                    || jo.getString("s_code").equals("")) {
                map.put("s_code", "");
            } else {
                map.put("s_code", jo.has("s_code") ? jo.optString("s_code") : "");
            }

            if (!jo.has("c_add_time") || null == jo.getString("c_add_time") || jo.getString("c_add_time").equals("null")
                    || jo.getString("c_add_time").equals("")) {
                map.put("c_add_time", "");
            } else {
                map.put("c_add_time", jo.has("c_add_time") ? jo.optString("c_add_time") : "");
            }

            if (!jo.has("c_last_time") || null == jo.getString("c_last_time")
                    || jo.getString("c_last_time").equals("null") || jo.getString("c_last_time").equals("")) {
                map.put("c_last_time", "");
            } else {
                map.put("c_last_time", jo.has("c_last_time") ? jo.optString("c_last_time") : "");
            }

            String isUse = "";
            if (!jo.has("is_use") || null == jo.getString("is_use") || jo.getString("is_use").equals("null")
                    || jo.getString("is_use").equals("")) {
                map.put("is_use", "");
            } else {
                isUse = jo.optString("is_use");
                map.put("is_use", jo.has("is_use") ? jo.optString("is_use") : "");
            }

            if (!jo.has("c_remark") || null == jo.getString("c_remark") || jo.getString("c_remark").equals("null")
                    || jo.getString("c_remark").equals("")) {
                map.put("c_remark", "");
            } else {
                map.put("c_remark", jo.has("c_remark") ? jo.optString("c_remark") : "");
            }
            String is_gold = "0";
            if (!jo.has("is_gold") || null == jo.getString("is_gold") || jo.getString("is_gold").equals("null")
                    || jo.getString("is_gold").equals("")) {
                map.put("is_gold", "0");
            } else {
                is_gold = jo.optString("is_gold");
                map.put("is_gold", jo.has("is_gold") ? jo.optString("is_gold") : "0");
            }
            // ////// 在这加这东西干嘛？
            // map.put("voucher", jo.has("voucher") ? jo.optString("voucher")
            // : "");
            // map.put("snum", jo.has("snum") ? jo.optString("snum")
            // : "");
            // map.put("price", jo.has("price") ? jo.optString("price")
            // : "");

            // 如果是金券 手动添加
            if ((id + "").equals(c_id) && "1".equals(is_open) && "0".equals(is_use)) {
                if (!"1".equals(isUse) && "1".equals(is_gold)) {// isUse ！=1
                    // 已经使用的优惠券
                    // is_gold=1失效列表
                    // 已经使用的金券
                    lists.add(map);
                }
            } else {
                lists.add(map);
            }

        }
        return lists;
    }

    /***
     * 我的卡券列表
     */
    public static final List<HashMap<String, Object>> createMyCouponList(Context context, String result)
            throws Exception {
        List<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }

        JSONArray ja = new JSONArray(j.getString("coupons"));
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = (JSONObject) ja.opt(i);

            HashMap<String, Object> map = new HashMap<String, Object>();

            if (!jo.has("id") || null == jo.getString("id") || jo.getString("id").equals("null")
                    || jo.getString("id").equals("")) {
                map.put("id", 0);
            } else {
                map.put("id", jo.has("id") ? jo.optInt("id") : 0);
            }

            if (!jo.has("user_id") || null == jo.getString("user_id") || jo.getString("user_id").equals("null")
                    || jo.getString("user_id").equals("")) {
                map.put("user_id", 0);
            } else {
                map.put("user_id", jo.has("user_id") ? jo.optInt("user_id") : 0);
            }

            if (!jo.has("c_id") || null == jo.getString("c_id") || jo.getString("c_id").equals("null")
                    || jo.getString("c_id").equals("")) {
                map.put("c_id", 0);
            } else {
                map.put("c_id", jo.has("c_id") ? jo.optInt("c_id") : 0);
            }

            if (!jo.has("num") || null == jo.getString("num") || jo.getString("num").equals("null")
                    || jo.getString("num").equals("")) {
                map.put("num", 0);
            } else {
                map.put("num", jo.has("num") ? jo.optInt("num") : 0);
            }

            if (!jo.has("add_time") || null == jo.getString("add_time") || jo.getString("add_time").equals("null")
                    || jo.getString("add_time").equals("")) {
                map.put("add_time", "");
            } else {
                map.put("add_time", jo.has("add_time") ? jo.optString("add_time") : "");
            }

            if (!jo.has("last_time") || null == jo.getString("last_time") || jo.getString("last_time").equals("null")
                    || jo.getString("last_time").equals("")) {
                map.put("last_time", "");
            } else {
                map.put("last_time", jo.has("last_time") ? jo.optString("last_time") : "");
            }
            lists.add(map);
        }
        return lists;
    }

    /***
     * 我的卡券总数
     */
    public static final int createMyCouponCount(Context context, String result) throws Exception {
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return 0;
        }

        int count = 0;
        if (!j.has("count") || null == j.getString("count") || j.getString("count").equals("null")
                || j.getString("count").equals("")) {
        } else {
            count = j.getInt("count");
        }
        return count;
    }

    /***
     * 我的最爱列表
     */
    public static final List<Like> createMyFavourList(Context context, String result) throws Exception {

        List<Like> favours = new ArrayList<Like>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        String pageCount = jsonObject.has("pageCount") ? jsonObject.getString("pageCount") : "";

        if (!TextUtils.isEmpty(pageCount)) {
            JSONArray jsonArray = new JSONArray(jsonObject.has("likes") ? jsonObject.getString("likes") : null);
            for (int i = 0; i < jsonArray.length(); i++) {
                Like like = new Like();
                JSONObject j = (JSONObject) jsonArray.opt(i);
                like = JSON.parseObject(j.toString(), Like.class);
                like.setPageCount(pageCount);
                favours.add(like);
            }
        }
        return favours;
    }

    /***
     * 个人中心统计
     */
    public static final HashMap<String, Object> createPersonCenterCount(Context context, String result)
            throws Exception {
        // result = result.replace("null", "\"\"");
        // MycenterCount centerCount = null;
        // List<String> lists = new ArrayList<String>();
        HashMap<String, Object> mapCount = new HashMap<String, Object>();
        JSONObject jsonObject;

        jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!jsonObject.has("fans_count") || null == jsonObject.getString("fans_count")
                || jsonObject.getString("fans_count").equals("null") || jsonObject.getString("fans_count").equals("")) {
            mapCount.put("fans_count", "0");
        } else {
            mapCount.put("fans_count", jsonObject.optInt("fans_count") + "");// 我的粉丝数量
        }

        if (!jsonObject.has("like_count") || null == jsonObject.getString("like_count")
                || jsonObject.getString("like_count").equals("null") || jsonObject.getString("like_count").equals("")) {
            mapCount.put("like_count", "0");
        } else {
            mapCount.put("like_count", jsonObject.optInt("like_count") + "");// 我的最爱数
        }

        if (!jsonObject.has("store_shop_count") || null == jsonObject.getString("store_shop_count")
                || jsonObject.getString("store_shop_count").equals("null")
                || jsonObject.getString("store_shop_count").equals("")) {
            mapCount.put("store_shop_count", "0");// 店铺美衣数
        } else {
            mapCount.put("store_shop_count", jsonObject.optInt("store_shop_count") + "");// 店铺美衣数
        }

        if (!jsonObject.has("integral") || null == jsonObject.getString("integral")
                || jsonObject.getString("integral").equals("null") || jsonObject.getString("integral").equals("")) {
            mapCount.put("integral", "0");// 我的积分数
        } else {
            mapCount.put("integral", jsonObject.optInt("integral") + "");// 我的积分数
        }

        if (!jsonObject.has("balance_show") || null == jsonObject.getString("balance_show")
                || jsonObject.getString("balance_show").equals("null")
                || jsonObject.getString("balance_show").equals("")) {
            mapCount.put("balance_show", "0");// 返回钱包是否应该显示红点
            // 1显示,其他不显示
        } else {
            mapCount.put("balance_show",
                    jsonObject.has("balance_show") ? (jsonObject.optString("balance_show") + "") : "0");// 返回钱包是否应该显示红点
            // 1显示,其他不显示
        }

        if (!jsonObject.has("coupon_show") || null == jsonObject.getString("coupon_show")
                || jsonObject.getString("coupon_show").equals("null")
                || jsonObject.getString("coupon_show").equals("")) {
            mapCount.put("coupon_show", "0");// 获取优惠券的红点
        } else {
            mapCount.put("coupon_show",
                    jsonObject.has("coupon_show") ? (jsonObject.optString("coupon_show") + "") : "0");// 获取优惠券的红点
        } // 1显示,其他不显示

        if (!jsonObject.has("balance") || null == jsonObject.getString("balance")
                || jsonObject.getString("balance").equals("null") || jsonObject.getString("balance").equals("")) {
            mapCount.put("balance", "0.0"); // 获取钱包的余额
        } else {
            mapCount.put("balance", jsonObject.has("balance") ? (jsonObject.optDouble("balance") + "") : "0.0"); // 获取钱包的余额
        }

        if (!jsonObject.has("coupon_sum") || null == jsonObject.getString("coupon_sum")
                || jsonObject.getString("coupon_sum").equals("null") || jsonObject.getString("coupon_sum").equals("")) {
            mapCount.put("coupon_sum", "0.0");// 获取优惠券的总额
        } else {
            mapCount.put("coupon_sum",
                    jsonObject.has("coupon_sum") ? (jsonObject.optDouble("coupon_sum") + "") : "0.0");// 获取优惠券的总额
        }

        if (!jsonObject.has("mySteps_count") || null == jsonObject.getString("mySteps_count")
                || jsonObject.getString("mySteps_count").equals("null")
                || jsonObject.getString("mySteps_count").equals("")) {
            mapCount.put("mySteps_count", "0");// 我的足迹
        } else {
            mapCount.put("mySteps_count", jsonObject.optInt("mySteps_count") + "");// 我的足迹
        }

        // 待付款数
        if (!jsonObject.has("pay_count") || null == jsonObject.getString("pay_count")
                || jsonObject.getString("pay_count").equals("null") || jsonObject.getString("pay_count").equals("")) {
            mapCount.put("pay_count", "0");
        } else {
            mapCount.put("pay_count", jsonObject.optInt("pay_count") + "");
        }

        // 待发货数
        if (!jsonObject.has("send_count") || null == jsonObject.getString("send_count")
                || jsonObject.getString("send_count").equals("null") || jsonObject.getString("send_count").equals("")) {
            mapCount.put("send_count", "0");
        } else {
            mapCount.put("send_count", jsonObject.optInt("send_count") + "");
        }

        // 待收货数
        if (!jsonObject.has("furl_count") || null == jsonObject.getString("furl_count")
                || jsonObject.getString("furl_count").equals("null") || jsonObject.getString("furl_count").equals("")) {
            mapCount.put("furl_count", "0");
        } else {
            mapCount.put("furl_count", jsonObject.optInt("furl_count") + "");
        }

        // 待评价数
        if (!jsonObject.has("ass_count") || null == jsonObject.getString("ass_count")
                || jsonObject.getString("ass_count").equals("null") || jsonObject.getString("ass_count").equals("")) {
            mapCount.put("ass_count", "0");
        } else {
            mapCount.put("ass_count", jsonObject.optInt("ass_count") + "");
        }

        if (!jsonObject.has("refund_count") || null == jsonObject.getString("refund_count")
                || jsonObject.getString("refund_count").equals("null")
                || jsonObject.getString("refund_count").equals("")) {
            mapCount.put("refund_count", "0");// 退款中数
        } else {
            mapCount.put("refund_count", jsonObject.optInt("change_count") + "");// 退款中数
        }
        // JSONObject jo = jsonObject.optJSONObject("home_info");
        //
        // if (!jo.has("circle_count") || null == jo.getString("circle_count")
        // || jo.getString("circle_count").equals("null")
        // || jo.getString("circle_count").equals("")) {
        // mapCount.put("circle_count", "0");
        // } else {
        // mapCount.put("circle_count",
        // (jo.optInt("circle_count") + "").equals("") ? "0" :
        // (jo.optInt("circle_count") + ""));
        // }
        //
        // if (!jsonObject.has("finCount") || null ==
        // jsonObject.getString("finCount")
        // || jsonObject.getString("finCount").equals("null") ||
        // jsonObject.getString("finCount").equals("")) {
        // mapCount.put("finCount", "0");
        // } else {
        // mapCount.put("finCount",
        // (jsonObject.optInt("finCount") + "").equals("") ? "0" :
        // (jsonObject.optInt("finCount") + ""));
        // }
        //
        // if (!jo.has("fans_count") || null == jo.getString("fans_count")
        // || jo.getString("fans_count").equals("null") ||
        // jo.getString("fans_count").equals("")) {
        // mapCount.put("fans_count", "0");
        // } else {
        // mapCount.put("fans_count",
        // (jo.optInt("fans_count") + "").equals("") ? "0" :
        // (jo.optInt("fans_count") + ""));
        // }
        // YCache.getCacheUser(context).setFans_count(ja.);

        // centerCount = JSON.parseObject(result, MycenterCount.class);
        // centerCount.setCircle_count(ja.optInt(0, "circle_count"));
        // centerCount.setFans_count(ja.optString(0, "fans_count"));

        return mapCount;
    }

    /***
     * 我的积分信息
     */
    public static final HashMap<String, List<HashMap<String, String>>> createIntegralInfo(Context context,
                                                                                          String result) throws Exception {
        HashMap<String, String> mapRet = new HashMap<String, String>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        HashMap<String, List<HashMap<String, String>>> maplist = new HashMap<String, List<HashMap<String, String>>>();
        List<HashMap<String, String>> listMap = new ArrayList<HashMap<String, String>>();

        JSONArray jsonArray = new JSONArray(jsonObject.getString("mission"));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = (JSONObject) jsonArray.opt(i);
            HashMap<String, String> mapMission = new HashMap<String, String>();

            if (!obj.has("id") || null == obj.getString("id") || obj.getString("id").equals("")) {
                mapMission.put("id", "");
            } else {
                mapMission.put("id", obj.getString("id"));
            }

            if (!obj.has("m_name") || null == obj.getString("m_name") || obj.getString("m_name").equals("")) {
                mapMission.put("m_name", "");
            } else {
                mapMission.put("m_name", obj.getString("m_name"));
            }
            listMap.add(mapMission);
        }
        maplist.put("mission", listMap);

        List<HashMap<String, String>> listMapInfo = new ArrayList<HashMap<String, String>>();

        if (!jsonObject.has("income") || null == jsonObject.getString("income")
                || jsonObject.getString("income").equals("")) {
            mapRet.put("income", "");
        } else {
            mapRet.put("income", jsonObject.getString("income"));
        }

        if (!jsonObject.has("expense") || null == jsonObject.getString("expense")
                || jsonObject.getString("expense").equals("")) {
            mapRet.put("expense", "");
        } else {
            mapRet.put("expense", jsonObject.getString("expense"));
        }

        if (!jsonObject.has("integral") || null == jsonObject.getString("integral")
                || jsonObject.getString("integral").equals("")) {
            mapRet.put("integral", "");
        } else {
            mapRet.put("integral", jsonObject.getString("integral"));
        }

        if (!jsonObject.has("signDay") || null == jsonObject.getString("signDay")
                || jsonObject.getString("signDay").equals("")) {
            mapRet.put("signDay", "");
        } else {
            mapRet.put("signDay", jsonObject.getString("signDay"));
        }

        if (!jsonObject.has("is_sign") || null == jsonObject.getString("is_sign")
                || jsonObject.getString("is_sign").equals("")) {
            mapRet.put("is_sign", "");
        } else {
            mapRet.put("is_sign", jsonObject.getString("is_sign"));
        }
        String fulfill = jsonObject.getString("fulfill").toString();
        mapRet.put("mapFulfill", fulfill);

        listMapInfo.add(mapRet);

        maplist.put("info", listMapInfo);
        return maplist;
    }

    /***
     * 将数据得到的商品属性数据插入数据库
     *
     * @param context
     * @param result
     * @throws Exception
     */
    public static final void updateSyncDatas(Context context, String result) throws Exception {
        YDBHelper helpler = new YDBHelper(context);
        List<String> sqls = new ArrayList<String>();
        JSONObject jo = new JSONObject(result);

        if (jo.getString("status").equals("1")) {
            // 分类数据
            JSONArray jType = new JSONArray(jo.getString("shop_type"));
            LogYiFu.e("获取的数据的长度", jType.length() + "");
            if (null != jType && jType.length() > 0) {
                helpler.delete("delete from sort_info");
                for (int i = 0; i < jType.length(); i++) {
                    ShopType sType = JSON.parseObject(jType.getString(i), ShopType.class);
                    String sql = "insert into sort_info(_id,sort_name,icon,is_show,p_id,group_flag,sequence)values('"
                            + sType.getId() + "','" + sType.getType_name() + "','" + sType.getIco() + "','"
                            + sType.getIs_show() + "','" + sType.getParent_id() + "','" + sType.getGroup_flag() + "','"
                            + sType.getSequence() + "')";
                    // if (!"外套".equals("" + sType.getType_name())) {
                    sqls.add(sql);
                    // }

                }
            }
            // 新的分类数据
            JSONArray type_tag = new JSONArray(jo.getString("type_tag"));
            LogYiFu.e("获取的数据的长度", type_tag.length() + "");
            if (null != type_tag && type_tag.length() > 0) {
                helpler.delete("delete from type_tag");
                for (int i = 0; i < type_tag.length(); i++) {
                    TypeTag sType = JSON.parseObject(type_tag.getString(i), TypeTag.class);
                    String sql = "insert into type_tag(_id,class_name,pic,is_new,is_hot,sort,type,class_type)values('"
                            + sType.getId() + "','" + sType.getClass_name() + "','" + sType.getPic() + "','"
                            + sType.getIs_new() + "','" + sType.getIs_hot() + "','" + sType.getSort() + "','"
                            + sType.getType() + "','" + sType.getClass_type() + "')";
                    sqls.add(sql);

                }
            }

            // 属性数据

            /*
             * JSONArray jAttr = new JSONArray(jo.getString("shop_attr")); if
             * (null != jAttr && jAttr.length() > 0) { helpler.delete(
             * "delete from attr_info"); for (int i = 0; i < jAttr.length();
             * i++) { ShopAttr sAttr = JSON.parseObject(jAttr.getString(i),
             * ShopAttr.class); String sql =
             * "insert into attr_info(_id,attr_name,icon,p_id,is_show)values('"
             * + sAttr.getId() + "','" + sAttr.getAttr_name() + "','" +
             * sAttr.getIco() + "','" + sAttr.getParent_id() + "','" +
             * sAttr.getIs_show() + "')"; sqls.add(sql);
             *
             * } }
             */

            /*
             * JSONArray jAttr = new JSONArray(jo.getString("shop_attr")); if
             * (null != jAttr && jAttr.length() > 0) { helpler.delete(
             * "delete from attr_info"); for (int i = 0; i < jAttr.length();
             * i++) { ShopAttr sAttr = JSON.parseObject(jAttr.getString(i),
             * ShopAttr.class); String sql =
             * "insert into attr_info(_id,attr_name,icon,p_id,is_show)values('"
             * + sAttr.getId() + "','" + sAttr.getAttr_name() + "','" +
             * sAttr.getIco() + "','" + sAttr.getParent_id() + "','" +
             * sAttr.getIs_show() + "')"; sqls.add(sql);
             *
             * } }
             */

            // 商家标签数据
            /*
             * JSONArray jaBusTag = new JSONArray(jo.getString("bus_tag")); if
             * (null != jaBusTag && jaBusTag.length() > 0) { helpler.delete(
             * "delete from bus_tag"); for (int i = 0; i < jaBusTag.length();
             * i++) { BusinessTag sTag = JSON.parseObject(jaBusTag.optString(i),
             * BusinessTag.class); String sql =
             * "insert into bus_tag(_id,attr_name,icon,p_id,sequence,is_show)values('"
             * + sTag.getId() + "','" + sTag.getTag_name() + "','" +
             * sTag.getIco() + "','" + sTag.getParent_id() + "','" +
             * sTag.getSequence() + "','" + sTag.getIs_show() + "')";
             * sqls.add(sql); } }
             */

            // 标签数据
            JSONArray jaTag = new JSONArray(jo.getString("shop_tag"));
            if (null != jaTag && jaTag.length() > 0) {
                helpler.delete("delete from tag_info");
                for (int i = 0; i < jaTag.length(); i++) {
                    ShopTag sTag = JSON.parseObject(jaTag.getString(i), ShopTag.class);
                    String sql = "insert into tag_info(_id,attr_name,icon,p_id,e_name,sequence,is_show)values('"
                            + sTag.getId() + "','" + sTag.getTag_name() + "','" + sTag.getIco() + "','"
                            + sTag.getParent_id() + "','" + sTag.getE_name() + "','" + sTag.getSequence() + "','"
                            + sTag.getIs_show() + "')";
                    sqls.add(sql);

                }
            }
            // 密友圈标签
            JSONArray friend_circle_tag = new JSONArray(jo.getString("friend_circle_tag"));
            LogYiFu.e("获取的数据的长度", friend_circle_tag.length() + "");
            if (null != friend_circle_tag && friend_circle_tag.length() > 0) {
                helpler.delete("delete from friend_circle_tag");
                for (int i = 0; i < friend_circle_tag.length(); i++) {
                    FriendCircleTag sType = JSON.parseObject(friend_circle_tag.getString(i), FriendCircleTag.class);
                    String sql = "insert into friend_circle_tag(_id,name,is_show,sort,type)values('" + sType.getId()
                            + "','" + sType.getName() + "','" + sType.getIs_show() + "','" + sType.getSort() + "','"
                            + sType.getType() + "')";
                    sqls.add(sql);
                }
            }


            JSONArray shop_group_list;
            if (!jo.has("shopGroupList") || null == jo.getString("shopGroupList")
                    || jo.getString("shopGroupList").equals("")) {
                shop_group_list = null;
            } else {
                shop_group_list = new JSONArray(jo.getString("shopGroupList"));
                LogYiFu.e("获取的数据的长度", shop_group_list.length() + "");
            }

            if (null != shop_group_list && shop_group_list.length() > 0) {
                helpler.delete("delete from shop_group_list");
                for (int i = 0; i < shop_group_list.length(); i++) {
                    ShopGroupList shopGroupList = JSON.parseObject(shop_group_list.getString(i), ShopGroupList.class);
                    String sql = "insert into shop_group_list(_id,app_name,icon,value,banner)values('"
                            + shopGroupList.getId() + "','" + shopGroupList.getApp_name() + "','" + shopGroupList.getIcon() + "','"
                            + shopGroupList.getValue() + "','"
                            + shopGroupList.getBanner() + "')";
                    sqls.add(sql);
                    // }

                }
            }

            // 新增 品牌
            JSONArray supp_label = new JSONArray(jo.getString("supp_label"));
            LogYiFu.e("获取的数据的长度", supp_label.length() + "");
            if (null != supp_label && supp_label.length() > 0) {
                helpler.delete("delete from supp_label");
                for (int i = 0; i < supp_label.length(); i++) {
                    SuppLabel sType = JSON.parseObject(supp_label.getString(i), SuppLabel.class);
                    String remark = sType.getRemark();
                    String name = sType.getName();
                    if (remark.contains("'")) {
                        String remarks[] = remark.split("'");
                        remark = "";
                        for (int k = 0; k < remarks.length; k++) {
                            if (k < remarks.length - 1) {
                                remark += remarks[k] + "''";// 在SQL语句中 将' 加上一个'
                                // 在执行SQL语句时候 两个单引号
                                // 标识一个单引号字符
                            } else {
                                remark += remarks[k];
                            }
                        }
                    }
                    if (name.contains("'")) {
                        String names[] = name.split("'");
                        name = "";
                        for (int k = 0; k < names.length; k++) {
                            if (k < names.length - 1) {
                                name += names[k] + "''";
                            } else {
                                name += names[k];
                            }
                        }
                    }
                    String sql = "insert into supp_label(_id,name,pic,icon,sort,remark,add_time,type)values('"
                            + sType.getId() + "','" + name + "','" + sType.getPic() + "','" + sType.getIcon() + "','"
                            + sType.getSort() + "','" + remark + "','" + sType.getAdd_time() + "','" + sType.getType()
                            + "')";
                    sqls.add(sql);
                }
            }
            helpler.update(sqls);
            // helpler.close();
            /*
             * context.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE)
             * .edit() .putString(Pref.sync_attr_date,
             * jo.getString("attr_data")) .commit();
             */
            context.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE).edit()
                    .putString(Pref.sync_sort_date, jo.getString("type_data")).commit();
            context.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE).edit()
                    .putString(Pref.sync_tag_date, jo.getString("tag_data")).commit();
            context.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE).edit()
                    .putString(Pref.sync_type_tag_data, jo.getString("type_tag_data")).commit();

            context.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE).edit()
                    .putString(Pref.sync_supp_label_data, jo.getString("supp_label_data")).commit();

            context.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE).edit()
                    .putString(Pref.sync_friend_circle_tag_data, jo.getString("friend_circle_tag_data")).commit();
            // context.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE)
            // .edit()
            // .putString(Pref.sync_bus_tag_date,
            // jo.optString("bus_tag_data")).commit();
            // context.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE)
            // .edit()
            // .putString(Pref.sync_bus_tag_date,
            // jo.optString("bus_tag_data")).commit();
            /*
             * context.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE)
             * .edit() .putString(Pref.sync_bus_tag_date,
             * jo.optString("bus_tag_data")).commit();
             */
        }

    }

    /***
     * 将数据得到的商品属性数据插入数据库(发布密友圈时候 只更新品牌表)
     *
     * @param context
     * @param result
     * @throws Exception
     */
    public static final void updateSyncDatasSweet(Context context, String result) throws Exception {
        YDBHelper helpler = new YDBHelper(context);
        List<String> sqls = new ArrayList<String>();
        JSONObject jo = new JSONObject(result);

        if (jo.getString("status").equals("1")) {
            // 新增 品牌
            JSONArray supp_label = new JSONArray(jo.getString("supp_label"));
            LogYiFu.e("获取的数据的长度", supp_label.length() + "");
            if (null != supp_label && supp_label.length() > 0) {
                helpler.delete("delete from supp_label");
                for (int i = 0; i < supp_label.length(); i++) {
                    SuppLabel sType = JSON.parseObject(supp_label.getString(i), SuppLabel.class);
                    String remark = sType.getRemark();
                    String name = sType.getName();
                    if (remark.contains("'")) {
                        String remarks[] = remark.split("'");
                        remark = "";
                        for (int k = 0; k < remarks.length; k++) {
                            if (k < remarks.length - 1) {
                                remark += remarks[k] + "''";// 在SQL语句中 将' 加上一个'
                                // 在执行SQL语句时候 两个单引号
                                // 标识一个单引号字符
                            } else {
                                remark += remarks[k];
                            }
                        }
                    }
                    if (name.contains("'")) {
                        String names[] = name.split("'");
                        name = "";
                        for (int k = 0; k < names.length; k++) {
                            if (k < names.length - 1) {
                                name += names[k] + "''";
                            } else {
                                name += names[k];
                            }
                        }
                    }
                    String sql = "insert into supp_label(_id,name,pic,icon,sort,remark,add_time,type)values('"
                            + sType.getId() + "','" + name + "','" + sType.getPic() + "','" + sType.getIcon() + "','"
                            + sType.getSort() + "','" + remark + "','" + sType.getAdd_time() + "','" + sType.getType()
                            + "')";
                    sqls.add(sql);
                }
            }
            helpler.update(sqls);
            context.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE).edit()
                    .putString(Pref.sync_supp_label_data, jo.getString("supp_label")).commit();
        }

    }

    public static final String[] createMyWalletInfo(Context context, String result) throws JSONException {
        JSONObject jo = new JSONObject(result);
        String str[] = new String[10];
        if (jo.getInt("status") == 1) {// 此解析要根据对应的角标查询代表的内容（用起来很不方便，待优化）

            if (!jo.has("balance") || null == jo.getString("balance") || jo.getString("balance").equals("null")
                    || jo.getString("balance").equals("")) {
                str[0] = "0"; // 余额
            } else {
                str[0] = jo.optString("balance"); // 余额
            }

            if (!jo.has("conponCount") || null == jo.getString("conponCount")
                    || jo.getString("conponCount").equals("null") || jo.getString("conponCount").equals("")) {
                str[1] = "0"; // 我的券张数
            } else {
                str[1] = jo.optString("conponCount"); // 我的券张数
            }

            if (!jo.has("two_balance") || null == jo.getString("two_balance")
                    || jo.getString("two_balance").equals("null") || jo.getString("two_balance").equals("")) {
                str[2] = "0";// 二级金额
            } else {
                str[2] = jo.has("two_balance") ? jo.optString("two_balance") : "0";// 二级金额
            }

            if (!jo.has("two_freeze_balance") || null == jo.getString("two_freeze_balance")
                    || jo.getString("two_freeze_balance").equals("null")
                    || jo.getString("two_freeze_balance").equals("")) {
                str[3] = "0";// 二级冻结金额
            } else {
                str[3] = jo.has("two_freeze_balance") ? jo.optString("two_freeze_balance") : "0";// 二级冻结金额
            }

            if (!jo.has("peas") || null == jo.getString("peas") || jo.getString("peas").equals("null")
                    || jo.getString("peas").equals("")) {
                str[4] = "0";// 可用衣豆
            } else {
                str[4] = jo.has("peas") ? jo.optString("peas") : "0";// 可用衣豆
            }
            if (!jo.has("peas_free") || null == jo.getString("peas_free") || jo.getString("peas_free").equals("null")
                    || jo.getString("peas_free").equals("")) {
                str[5] = "0";// 冻结衣豆
            } else {
                str[5] = jo.has("peas_free") ? jo.optString("peas_free") : "0";// 冻结衣豆
            }
            if (!jo.has("extract") || null == jo.getString("extract") || jo.getString("extract").equals("null")
                    || jo.getString("extract").equals("")) {
                str[6] = "0";// 可提现额度
            } else {
                str[6] = jo.has("extract") ? jo.optString("extract") : "0";// 可提现额度
            }
            if (!jo.has("ex_free") || null == jo.getString("ex_free") || jo.getString("ex_free").equals("null")
                    || jo.getString("ex_free").equals("")) {
                str[7] = "0";// 冻结提现额度
            } else {
                str[7] = jo.has("ex_free") ? jo.optString("ex_free") : "0";// 冻结提现额度
            }
            if (!jo.has("point_count") || null == jo.getString("point_count") || jo.getString("point_count").equals("null")
                    || jo.getString("point_count").equals("")) {
                str[8] = "0";// 累计点赞数
            } else {
                str[8] = jo.has("point_count") ? jo.optString("point_count") : "0";// 累计点赞数
            }
            if (!jo.has("freeze_balance") || null == jo.getString("freeze_balance") || jo.getString("freeze_balance").equals("null")
                    || jo.getString("freeze_balance").equals("")) {
                str[9] = "0";// 增加 冻结金额
            } else {
                str[9] = jo.has("freeze_balance") ? jo.optString("freeze_balance") : "0";// 增加 冻结金额
            }



//            if (!jo.has("raffleMoney") || null == jo.getString("raffleMoney") || jo.getString("raffleMoney").equals("null")
//                    || jo.getString("raffleMoney").equals("")) {
//                str[10] = "0";
//            } else {
//                str[10] = jo.has("raffleMoney") ? jo.optString("raffleMoney") : "0";
//            }


        }
        return str;
    }

    /**
     * 个人中心邀请好友
     */
    public static final HashMap<String, Object> createInviteFriend(Context context, String result)
            throws JSONException {

        HashMap<String, Object> mapObject = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONObject pShop = j.optJSONObject("data");
        if (!pShop.has("user_id") || null == pShop.getString("user_id") || pShop.getString("user_id").equals("")) {
            mapObject.put("user_id", "");
        } else {
            mapObject.put("user_id", pShop.has("user_id") ? pShop.getString("user_id") : "");
        }
        if (!pShop.has("link") || null == pShop.getString("link") || pShop.getString("link").equals("")) {
            mapObject.put("link", "");
        } else {
            mapObject.put("link", pShop.has("link") ? pShop.getString("link") : "");
        }
        if (!pShop.has("pic") || null == pShop.getString("pic") || pShop.getString("pic").equals("")) {
            mapObject.put("pic", "");
        } else {
            mapObject.put("pic", pShop.has("pic") ? pShop.getString("pic") : "");
        }
        return mapObject;
    }

    /**
     * 主界面商品列表
     */
    public static final List<HashMap<String, Object>> createProductList(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(j.getString("listShop"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.opt(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();

            if (!jo.has("def_pic") || null == jo.getString("def_pic") || jo.getString("def_pic").equals("")) {
                mapObject.put("def_pic", "");
            } else {
                mapObject.put("def_pic", jo.getString("def_pic"));
            }


//            if (!jo.has("app_shop_group_price") || null == jo.getString("app_shop_group_price") || jo.getString("app_shop_group_price").equals("")) {
//                mapObject.put("app_shop_group_price", "1");
//            } else {
//                mapObject.put("app_shop_group_price", jo.getString("app_shop_group_price"));
//            }


            if (!jo.has("assmble_price") || null == jo.getString("assmble_price") || jo.getString("assmble_price").equals("")) {
                mapObject.put("assmble_price", "1");
                mapObject.put("app_shop_group_price", "1");
            } else {
                mapObject.put("assmble_price", jo.getString("assmble_price"));
                mapObject.put("app_shop_group_price", jo.getString("assmble_price"));
            }


            if (!jo.has("shop_code") || null == jo.getString("shop_code") || jo.getString("shop_code").equals("")) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.getString("shop_code"));
            }

            if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price")
                    || jo.getString("shop_se_price").equals("null") || jo.getString("shop_se_price").equals("")) {
                mapObject.put("shop_se_price", "0");
            } else {
                mapObject.put("shop_se_price", jo.getString("shop_se_price"));
            }
            // TODO:
            // mapObject.put("shop_price", jo.getString("shop_price"));
            // mapObject.put("id", jo.getString("id"));
            if (!jo.has("shop_name") || null == jo.getString("shop_name") || jo.getString("shop_name").equals("")) {
                mapObject.put("shop_name", "");
            } else {
                mapObject.put("shop_name", jo.getString("shop_name"));
            }
            // mapObject.put("love_num", jo.getString("love_num"));

            if (!jo.has("kickback") || null == jo.getString("kickback") || jo.getString("kickback").equals("null")
                    || jo.getString("kickback").equals("")) {
                mapObject.put("kickback", "0");
            } else {
                mapObject.put("kickback", jo.getString("kickback"));
            }
            if (!jo.has("supp_label") || null == jo.getString("supp_label") || jo.getString("supp_label").equals("null")
                    || jo.getString("supp_label").equals("")) {
                mapObject.put("supp_label", "");
            } else {
                mapObject.put("supp_label", jo.getString("supp_label"));
            }

            if (!jo.has("supp_label_id") || null == jo.getString("supp_label_id")
                    || "null".equals(jo.getString("supp_label_id")) || "".equals(jo.getString("supp_label_id"))) {
                mapObject.put("supp_label_id", "");
            } else {
                mapObject.put("supp_label_id", jo.has("supp_label_id") ? jo.getString("supp_label_id") : "");
            }

            // 商品原价
            if (!jo.has("shop_price") || null == jo.getString("shop_price") || jo.getString("shop_price").equals("null")
                    || jo.getString("shop_price").equals("")) {
                mapObject.put("shop_price", "0");
            } else {
                mapObject.put("shop_price", jo.getString("shop_price"));
            }
            // 虚拟销量
            if (!jo.has("virtual_sales") || null == jo.getString("virtual_sales")
                    || jo.getString("virtual_sales").equals("null") || jo.getString("virtual_sales").equals("")) {
                mapObject.put("virtual_sales", "0");
            } else {
                mapObject.put("virtual_sales", jo.getString("virtual_sales"));
            }
            //
            // if (jo.has("isLike")) {
            // mapObject.put("isLike", jo.getInt("isLike"));
            // } else {
            // mapObject.put("isLike", 0);
            // }
            //
            // if (jo.has("isCart")) {
            // mapObject.put("isCart", jo.getInt("isCart"));
            // } else {
            // mapObject.put("isCart", 0);
            // }

            retInfo.add(mapObject);
        }
        return retInfo;
    }

    /**
     * 主界面商品列表
     */
    public static final HashMap<String, Object> createProductPicList(Context context, String result)
            throws JSONException {
        HashMap<String, Object> retInfo = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        String[] picList = j.getString("pic_list").split(",");
        retInfo.put("picList", picList);
        retInfo.put("status", j.getString("status"));
        return retInfo;
    }

    /**
     * 搭配 首页
     */
    public static final List<HashMap<String, Object>> createMatchList(Context context, String result)
            throws JSONException {// TODO:ZZQ
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (!"1".equals(j.getString("status"))) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(j.getString("listShop"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.opt(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();
            if (!jo.has("collocation_name") || null == jo.getString("collocation_name")
                    || jo.getString("collocation_name").equals("")) {
                mapObject.put("collocation_name", "");
            } else {
                mapObject.put("collocation_name", jo.has("collocation_name") ? jo.optString("collocation_name") : "");
            }

            ////////////////////////// 专题新增两个字段////////////////////////////////
            if (!jo.has("collocation_name2") || null == jo.getString("collocation_name2")
                    || jo.getString("collocation_name2").equals("")) {
                mapObject.put("collocation_name2", "");
            } else {
                mapObject.put("collocation_name2",
                        jo.has("collocation_name2") ? jo.optString("collocation_name2") : "");// 专题二级名
            }
            if (!jo.has("type") || null == jo.getString("type") || jo.getString("type").equals("")) {
                mapObject.put("type", "");
            } else {
                mapObject.put("type", jo.has("type") ? jo.optString("type") : "");// 1或者空
                // 为搭配购，2为专题
            }
            ////////////////////////// 专题新增两个字段////////////////////////////////

            if (!jo.has("collocation_code") || null == jo.getString("collocation_code")
                    || jo.getString("collocation_code").equals("")) {
                mapObject.put("collocation_code", "");
            } else {
                mapObject.put("collocation_code", jo.has("collocation_code") ? jo.optString("collocation_code") : "");
            }
            if (!jo.has("collocation_pic") || null == jo.getString("collocation_pic")
                    || jo.getString("collocation_pic").equals("")) {
                mapObject.put("collocation_pic", "");
            } else {
                mapObject.put("collocation_pic", jo.has("collocation_pic") ? jo.optString("collocation_pic") : "");
            }
            List<HashMap<String, Object>> listCollocationShop = new ArrayList<HashMap<String, Object>>();
            JSONArray jsonArrayCollocationShop = jo.getJSONArray("collocation_shop");
            for (int k = 0; k < jsonArrayCollocationShop.length(); k++) {
                JSONObject jk = (JSONObject) jsonArrayCollocationShop.opt(k);
                HashMap<String, Object> mapObjectCollocationShop = new HashMap<String, Object>();
                if (!jk.has("shop_code") || null == jk.getString("shop_code") || jk.getString("shop_code").equals("")) {
                    mapObjectCollocationShop.put("shop_code", "");
                } else {
                    mapObjectCollocationShop.put("shop_code", jk.has("shop_code") ? jk.optString("shop_code") : "");
                }
                if (!jk.has("shop_name") || null == jk.getString("shop_name") || jk.getString("shop_name").equals("")) {
                    mapObjectCollocationShop.put("shop_name", "");
                } else {
                    mapObjectCollocationShop.put("shop_name", jk.has("shop_name") ? jk.optString("shop_name") : "");
                }
                if (!jk.has("shop_se_price") || null == jk.getString("shop_se_price")
                        || "null".equals(jk.getString("shop_se_price")) || jk.getString("shop_se_price").equals("")) {
                    mapObjectCollocationShop.put("shop_se_price", "0");
                } else {
                    mapObjectCollocationShop.put("shop_se_price",
                            jk.has("shop_se_price") ? jk.optString("shop_se_price") : "0");
                }
                if (!jk.has("kickback") || null == jk.getString("kickback") || "null".equals(jk.getString("kickback"))
                        || jk.getString("kickback").equals("")) {
                    mapObjectCollocationShop.put("kickback", "0");
                } else {
                    mapObjectCollocationShop.put("kickback", jk.has("kickback") ? jk.optString("kickback") : "0");
                }
                if (!jk.has("option_flag") || null == jk.getString("option_flag")
                        || jk.getString("option_flag").equals("")) {
                    mapObjectCollocationShop.put("option_flag", "");
                } else {
                    mapObjectCollocationShop.put("option_flag",
                            jk.has("option_flag") ? jk.optString("option_flag") : "");
                }
                if (!jk.has("shop_x") || null == jk.getString("shop_x") || jk.getString("shop_x").equals("")) {
                    mapObjectCollocationShop.put("shop_x", "");
                } else {
                    mapObjectCollocationShop.put("shop_x", jk.has("shop_x") ? jk.optString("shop_x") : "");
                }
                if (!jk.has("shop_y") || null == jk.getString("shop_y") || jk.getString("shop_y").equals("")) {
                    mapObjectCollocationShop.put("shop_y", "");
                } else {
                    mapObjectCollocationShop.put("shop_y", jk.has("shop_y") ? jk.optString("shop_y") : "");
                }
                listCollocationShop.add(mapObjectCollocationShop);
            }
            mapObject.put("collocation_shop", listCollocationShop);

            List<HashMap<String, Object>> listShopType = new ArrayList<HashMap<String, Object>>();
            JSONArray jsonArrayShopType = jo.getJSONArray("shop_type_list");
            for (int m = 0; m < jsonArrayShopType.length(); m++) {

                JSONObject jsonObjectShopType2 = jsonArrayShopType.getJSONObject(m);

                // mapObjectShopType2.put("type_id",
                // jsonObjectShopType2.has("type_id") ?
                // jsonObjectShopType2.optString("type_id"): "");

                JSONArray jsonArrayShopType2 = jsonObjectShopType2.getJSONArray("list");
                for (int k = 0; k < jsonArrayShopType2.length(); k++) {
                    JSONObject jk = (JSONObject) jsonArrayShopType2.opt(k);
                    HashMap<String, Object> mapObjectShopType2 = new HashMap<String, Object>();
                    if (!jk.has("shop_code") || null == jk.getString("shop_code")
                            || jk.getString("shop_code").equals("")) {
                        mapObjectShopType2.put("shop_code", "");
                    } else {
                        mapObjectShopType2.put("shop_code", jk.has("shop_code") ? jk.optString("shop_code") : "");
                    }
                    if (!jk.has("shop_name") || null == jk.getString("shop_name")
                            || jk.getString("shop_name").equals("")) {
                        mapObjectShopType2.put("shop_name", "");
                    } else {
                        mapObjectShopType2.put("shop_name", jk.has("shop_name") ? jk.optString("shop_name") : "");
                    }
                    if (!jk.has("shop_price") || null == jk.getString("shop_price")
                            || "null".equals(jk.getString("shop_price")) || jk.getString("shop_price").equals("")) {
                        mapObjectShopType2.put("shop_price", "0");
                    } else {
                        mapObjectShopType2.put("shop_price", jk.has("shop_price") ? jk.optString("shop_price") : "");
                    }
                    if (!jk.has("shop_se_price") || null == jk.getString("shop_se_price")
                            || "null".equals(jk.getString("shop_se_price"))
                            || jk.getString("shop_se_price").equals("")) {
                        mapObjectShopType2.put("shop_se_price", "0");
                    } else {
                        mapObjectShopType2.put("shop_se_price",
                                jk.has("shop_se_price") ? jk.optString("shop_se_price") : "0");
                    }
                    if (!jk.has("type1") || null == jk.getString("type1") || jk.getString("type1").equals("")) {
                        mapObjectShopType2.put("type1", "");
                    } else {
                        mapObjectShopType2.put("type1", jk.has("type1") ? jk.optString("type1") : "");
                    }
                    if (!jk.has("type2") || null == jk.getString("type2") || jk.getString("type2").equals("")) {
                        mapObjectShopType2.put("type2", "");
                    } else {
                        mapObjectShopType2.put("type2", jk.has("type2") ? jk.optString("type2") : "");
                    }
                    if (!jk.has("def_pic") || null == jk.getString("def_pic") || jk.getString("def_pic").equals("")) {
                        mapObjectShopType2.put("def_pic", "");
                    } else {
                        mapObjectShopType2.put("def_pic", jk.has("def_pic") ? jk.optString("def_pic") : "");
                    }
                    if (!jk.has("type_id") || null == jk.getString("type_id") || jk.getString("type_id").equals("")) {
                        mapObjectShopType2.put("type_id", "");
                    } else {
                        mapObjectShopType2.put("type_id",
                                jsonObjectShopType2.has("type_id") ? jsonObjectShopType2.optString("type_id") : "");
                    }
                    if (!jk.has("kickback") || null == jk.getString("kickback")
                            || "null".equals(jk.getString("kickback")) || jk.getString("kickback").equals("")) {
                        mapObjectShopType2.put("kickback", "0");
                    } else {
                        mapObjectShopType2.put("kickback", jk.has("kickback") ? jk.optString("kickback") : "0");
                    }
                    if (!jk.has("supp_label") || null == jk.getString("supp_label")
                            || "null".equals(jk.getString("supp_label")) || jk.getString("supp_label").equals("")) {
                        mapObjectShopType2.put("supp_label", "");
                    } else {
                        mapObjectShopType2.put("supp_label", jk.has("supp_label") ? jk.optString("supp_label") : "");
                    }
                    if (!jk.has("supp_label_id") || null == jk.getString("supp_label_id")
                            || "null".equals(jk.getString("supp_label_id"))
                            || jk.getString("supp_label_id").equals("")) {
                        mapObjectShopType2.put("supp_label_id", "");
                    } else {
                        mapObjectShopType2.put("supp_label_id",
                                jk.has("supp_label_id") ? jk.optString("supp_label_id") : "");
                    }
                    listShopType.add(mapObjectShopType2);
                }
            }
            mapObject.put("shop_type_list", listShopType);

            retInfo.add(mapObject);

        }
        return retInfo;
    }

    /**
     * 搭配详情
     */
    // public static final List<HashMap<String, Object>> createMatchDetails(
    // Context context, String result) throws JSONException {
    // List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String,
    // Object>>();
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    // if(!"1".equals(j.getString("status"))){
    // return null;
    // }
    //
    // JSONObject jo = new JSONObject("shop");
    // if (null == jo || "".equals(jo)) {
    // return null;
    // }
    // HashMap<String, Object> shopHashMap = new HashMap<String, Object>();
    // shopHashMap.put("collocation_name", jo.has("collocation_name") ?
    // jo.optString("collocation_name"): "");
    // shopHashMap.put("collocation_code", jo.has("collocation_code") ?
    // jo.optString("collocation_code"): "");
    // shopHashMap.put("collocation_pic", jo.has("collocation_pic") ?
    // jo.optString("collocation_pic"): "");
    // shopHashMap.put("add_time", jo.has("add_time") ?
    // jo.optString("add_time"): "");
    // shopHashMap.put("collocation_remark", jo.has("collocation_remark") ?
    // jo.optString("collocation_remark"): "");
    // shopHashMap.put("type_relation_ids", jo.has("type_relation_ids") ?
    // jo.optString("type_relation_ids"): "");
    //
    // JSONArray ja =jo.getJSONArray("collocation_shop");
    // List<HashMap<String, Object>> listCollocationShop = new
    // ArrayList<HashMap<String, Object>>();
    // for (int k = 0; k < ja.length();k++) {
    // JSONObject jk = (JSONObject) ja.opt(k);
    // HashMap<String, Object> mapObjectCollocationShop = new HashMap<String,
    // Object>();
    // mapObjectCollocationShop.put("shop_code", jk.has("shop_code") ?
    // jk.optString("shop_code"): "");
    // mapObjectCollocationShop.put("shop_name", jk.has("shop_name") ?
    // jk.optString("shop_name"): "");
    // mapObjectCollocationShop.put("shop_se_price", jk.has("shop_se_price") ?
    // jk.optString("shop_se_price"): "");
    // mapObjectCollocationShop.put("option_flag", jk.has("option_flag") ?
    // jk.optString("option_flag"): "");
    // mapObjectCollocationShop.put("shop_x", jk.has("shop_x") ?
    // jk.optString("shop_x"): "");
    // mapObjectCollocationShop.put("shop_y", jk.has("shop_y") ?
    // jk.optString("shop_y"): "");
    // mapObjectCollocationShop.put("seq", jk.has("seq") ? jk.optString("seq"):
    // "");
    // listCollocationShop.add(mapObjectCollocationShop);
    // }
    // shopHashMap.put("collocation_shop", listCollocationShop);
    //
    // retInfo.add(shopHashMap);
    // return retInfo;
    // }

    /**
     * 搭配详情
     */
    public static final MatchShop createMatchDetails(Context context, String result) throws JSONException {
        MatchShop mShop = new MatchShop();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (!"1".equals(j.getString("status"))) {
            return null;
        }

        JSONObject jo = j.getJSONObject("shop");
        if (null == jo || "".equals(jo)) {
            return null;
        }
        if (!jo.has("collocation_name") || null == jo.getString("collocation_name")
                || jo.getString("collocation_name").equals("")) {
            mShop.setCollocation_name("");
        } else {
            mShop.setCollocation_name(jo.optString("collocation_name"));
        }
        if (!jo.has("collocation_code") || null == jo.getString("collocation_code")
                || jo.getString("collocation_code").equals("")) {
            mShop.setCollocation_code("");
        } else {
            mShop.setCollocation_code(jo.optString("collocation_code"));
        }
        if (!jo.has("collocation_pic") || null == jo.getString("collocation_pic")
                || jo.getString("collocation_pic").equals("")) {
            mShop.setCollocation_pic("");
        } else {
            mShop.setCollocation_pic(jo.optString("collocation_pic"));
        }
        if (!jo.has("add_time") || null == jo.getString("add_time") || jo.getString("add_time").equals("")) {
            mShop.setAdd_time("0");
        } else {
            mShop.setAdd_time(jo.optString("add_time"));
        }
        if (!jo.has("collocation_remark") || null == jo.getString("collocation_remark")
                || jo.getString("collocation_remark").equals("")) {
            mShop.setCollocation_remark("");
        } else {
            mShop.setCollocation_remark(jo.optString("collocation_remark"));
        }
        if (!jo.has("type_relation_ids") || null == jo.getString("type_relation_ids")
                || jo.getString("type_relation_ids").equals("")) {
            mShop.setType_relation_ids("");
        } else {
            mShop.setType_relation_ids(jo.optString("type_relation_ids"));
        }
        JSONArray ja = jo.getJSONArray("collocation_shop");
        List<CollocationShop> collocation_shop_list = new ArrayList<MatchShop.CollocationShop>();
        for (int k = 0; k < ja.length(); k++) {
            JSONObject jk = (JSONObject) ja.opt(k);
            CollocationShop collocation_shop = new CollocationShop();

            if (!jk.has("shop_code") || null == jk.getString("shop_code") || jk.getString("shop_code").equals("")) {
                collocation_shop.setShop_code("");
            } else {
                collocation_shop.setShop_code(jk.optString("shop_code"));
            }
            if (!jk.has("shop_name") || null == jk.getString("shop_name") || jk.getString("shop_name").equals("")) {
                collocation_shop.setShop_name("");
            } else {
                collocation_shop.setShop_name(jk.optString("shop_name"));
            }
            if (!jk.has("shop_se_price") || null == jk.getString("shop_se_price")
                    || jk.getString("shop_se_price").equals("")) {
                collocation_shop.setShop_se_price(0);
            } else {
                collocation_shop.setShop_se_price(jk.optDouble("shop_se_price", 0));
            }
            if (!jk.has("option_flag") || null == jk.getString("option_flag")
                    || jk.getString("option_flag").equals("")) {
                collocation_shop.setOption_flag(1);
            } else {
                collocation_shop.setOption_flag(jk.optInt("option_flag", 1));
            }
            if (!jk.has("shop_x") || null == jk.getString("shop_x") || jk.getString("shop_x").equals("")) {
                collocation_shop.setShop_x(0);
            } else {
                collocation_shop.setShop_x(jk.optDouble("shop_x", 0));
            }
            if (!jk.has("shop_y") || null == jk.getString("shop_y") || jk.getString("shop_y").equals("")) {
                collocation_shop.setShop_y(0);
            } else {
                collocation_shop.setShop_y(jk.optDouble("shop_y", 0));
            }
            if (!jk.has("seq") || null == jk.getString("seq") || jk.getString("seq").equals("")) {
                collocation_shop.setSeq(0);
            } else {
                collocation_shop.setSeq(jk.has("seq") ? jk.optInt("seq") : 0);
            }
            if (!jk.has("kickback") || null == jk.getString("kickback") || jk.getString("kickback").equals("null")
                    || jk.getString("kickback").equals("")) {
                collocation_shop.setKickback(0);
            } else {
                collocation_shop.setKickback(jk.has("kickback") ? jk.optDouble("kickback", 0) : 0);
            }
            collocation_shop_list.add(collocation_shop);
        }
        mShop.setCollocation_shop(collocation_shop_list);

        JSONArray ja2 = jo.getJSONArray("attrList");
        List<MatchShop.AttrList> attrList_list = new ArrayList<MatchShop.AttrList>();
        for (int m = 0; m < ja2.length(); m++) {
            JSONObject jm = (JSONObject) ja2.opt(m);
            AttrList attrList = new AttrList();

            if (!jm.has("attr_name") || null == jm.getString("attr_name") || jm.getString("attr_name").equals("")) {
                attrList.setAttr_name("");
            } else {
                attrList.setAttr_name(jm.optString("attr_name"));
            }
            if (!jm.has("is_show") || null == jm.getString("is_show") || jm.getString("is_show").equals("")) {
                attrList.setIs_show("");
            } else {
                attrList.setIs_show(jm.optString("is_show"));
            }
            if (!jm.has("ico") || null == jm.getString("ico") || jm.getString("ico").equals("")) {
                attrList.setIco("");
            } else {
                attrList.setIco(jm.optString("ico"));
            }
            if (!jm.has("color_type") || null == jm.getString("color_type") || jm.getString("color_type").equals("")) {
                attrList.setColor_type("");
            } else {
                attrList.setColor_type(jm.optString("color_type"));
            }
            if (!jm.has("id") || null == jm.getString("id") || jm.getString("id").equals("")) {
                attrList.setId("");
            } else {
                attrList.setId(jm.optString("id"));
            }
            if (!jm.has("parent_id") || null == jm.getString("parent_id") || jm.getString("parent_id").equals("")) {
                attrList.setParent_id("");
            } else {
                attrList.setParent_id(jm.optString("parent_id"));
            }
            attrList_list.add(attrList);
        }
        mShop.setAttrList(attrList_list);

        return mShop;
    }

    /**
     * 搭配详情 相关商品
     */
    public static final List<HashMap<String, Object>> createMatchDetailsRec(Context context, String result)
            throws JSONException {
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (!"1".equals(j.getString("status"))) {
            return null;
        }

        JSONArray ja = j.getJSONArray("listShop");

        List<HashMap<String, Object>> listShop = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = (JSONObject) ja.opt(i);
            HashMap<String, Object> mapList = new HashMap<String, Object>();
            if (!jo.has("shop_code") || null == jo.getString("shop_code") || jo.getString("shop_code").equals("")) {
                mapList.put("shop_code", "");
            } else {
                mapList.put("shop_code", jo.has("shop_code") ? jo.optString("shop_code") : "");
            }
            if (!jo.has("shop_name") || null == jo.getString("shop_name") || jo.getString("shop_name").equals("")) {
                mapList.put("shop_name", "");
            } else {
                mapList.put("shop_name", jo.has("shop_name") ? jo.optString("shop_name") : "");
            }

            if (!jo.has("shop_price") || null == jo.getString("shop_price") || jo.getString("shop_price").equals("null")
                    || jo.getString("shop_price").equals("")) {
                mapList.put("shop_price", "0");
            } else {
                mapList.put("shop_price", jo.has("shop_price") ? jo.optString("shop_price") : "0");
            }
            if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price")
                    || jo.getString("shop_se_price").equals("null") || jo.getString("shop_se_price").equals("")) {
                mapList.put("shop_se_price", "0");
            } else {
                mapList.put("shop_se_price", jo.has("shop_se_price") ? jo.optString("shop_se_price") : "0");
            }
            if (!jo.has("kickback") || null == jo.getString("kickback") || jo.getString("kickback").equals("null")
                    || jo.getString("kickback").equals("")) {
                mapList.put("kickback", "0");
            } else {
                mapList.put("kickback", jo.has("kickback") ? jo.optString("kickback") : "0");
            }
            if (!jo.has("def_pic") || null == jo.getString("def_pic") || jo.getString("def_pic").equals("null")
                    || jo.getString("def_pic").equals("")) {
                mapList.put("def_pic", "");
            } else {
                mapList.put("def_pic", jo.has("def_pic") ? jo.optString("def_pic") : "");
            }
            if (!jo.has("supp_label") || null == jo.getString("supp_label") || jo.getString("supp_label").equals("null")
                    || jo.getString("supp_label").equals("")) {
                mapList.put("supp_label", "");
            } else {
                mapList.put("supp_label", jo.has("supp_label") ? jo.optString("supp_label") : "");
            }
            listShop.add(mapList);
        }
        return listShop;
    }

    /**
     * 搭配商品 属性选择
     */
    public static final MatchAttr createMatchAttr(Context context, String result) throws JSONException {// ZZQ
        JSONObject j = new JSONObject(result);
        MatchAttr matchAttr = new MatchAttr();
        if (null == j || "".equals(j)) {
            return null;
        }
        if (!"1".equals(j.getString("status"))) {
            return null;
        }

        JSONObject jo = j.getJSONObject("shop_attr");
        ShopAttrBean shopAttrBean = new ShopAttrBean();
        HashMap<String, String> shopAttrHashMap = new HashMap<String, String>();

        matchAttr.setShop_attr(shopAttrBean);

        JSONArray ja = j.getJSONArray("stocktype");
        List<StocktypeBean> stocktypeList = new ArrayList<MatchAttr.StocktypeBean>();
        for (int i = 0; i < ja.length(); i++) {
            JSONObject ji = (JSONObject) ja.opt(i);
            StocktypeBean stocktypeBean = new StocktypeBean();
            if (!ji.has("shop_code") || null == ji.getString("shop_code") || ji.getString("shop_code").equals("")) {
                stocktypeBean.setShop_code("");
            } else {
                stocktypeBean.setShop_code(ji.getString("shop_code"));
            }
            shopAttrHashMap.put(ji.getString("shop_code"), jo.optString(ji.getString("shop_code")));
            shopAttrBean.setShopAttrHashMap(shopAttrHashMap);
            if (!ji.has("original_price") || null == ji.getString("original_price")
                    || ji.getString("original_price").equals("null") || ji.getString("original_price").equals("")) {
                stocktypeBean.setOriginal_price("0");
            } else {
                stocktypeBean.setOriginal_price(ji.getString("original_price"));
            }
            if (!ji.has("pic") || null == ji.getString("pic") || ji.getString("pic").equals("")) {
                stocktypeBean.setPic("");
            } else {
                stocktypeBean.setPic(ji.getString("pic"));
            }
            if (!ji.has("kickback") || null == ji.getString("kickback") || ji.getString("kickback").equals("null")
                    || ji.getString("kickback").equals("")) {
                stocktypeBean.setKickback("");
            } else {
                stocktypeBean.setKickback(ji.getString("kickback"));
            }
            if (!ji.has("shop_name") || null == ji.getString("shop_name") || ji.getString("shop_name").equals("")) {
                stocktypeBean.setShop_name("");
            } else {
                stocktypeBean.setShop_name(ji.getString("shop_name"));
            }
            if (!ji.has("core") || null == ji.getString("core") || ji.getString("core").equals("")) {
                stocktypeBean.setCores("0");
            } else {
                stocktypeBean.setCores(ji.getString("core"));
            }
            if (!ji.has("id") || null == ji.getString("id") || ji.getString("id").equals("")) {
                stocktypeBean.setId("");
            } else {
                stocktypeBean.setId(ji.getString("id"));
            }
            if (!ji.has("stock") || null == ji.getString("stock") || ji.getString("stock").equals("")) {
                stocktypeBean.setStock("");
            } else {
                stocktypeBean.setStock(ji.getString("stock"));
            }
            if (!ji.has("color_size") || null == ji.getString("color_size") || ji.getString("color_size").equals("")) {
                stocktypeBean.setColor_size("");
            } else {
                stocktypeBean.setColor_size(ji.getString("color_size"));
            }
            if (!ji.has("price") || null == ji.getString("price") || ji.getString("price").equals("null")
                    || ji.getString("price").equals("")) {
                stocktypeBean.setPrice("0");
            } else {
                stocktypeBean.setPrice(ji.getString("price"));
            }
            if (!ji.has("shop_price") || null == ji.getString("shop_price") || ji.getString("shop_price").equals("null")
                    || ji.getString("shop_price").equals("")) {
                stocktypeBean.setShop_price("");
            } else {
                stocktypeBean.setShop_price(ji.getString("shop_price"));
            }
            if (!ji.has("supp_id") || null == ji.getString("supp_id") || ji.getString("supp_id").equals("")) {
                stocktypeBean.setSupp_id("");
            } else {
                stocktypeBean.setSupp_id(ji.getString("supp_id"));
            }
            if (!ji.has("three_kickback") || null == ji.getString("three_kickback")
                    || ji.getString("three_kickback").equals("")) {
                stocktypeBean.setThree_kickback("");
            } else {
                stocktypeBean.setThree_kickback(ji.getString("three_kickback"));
            }
            if (!ji.has("two_kickback") || null == ji.getString("two_kickback")
                    || ji.getString("two_kickback").equals("")) {
                stocktypeBean.setTwo_kickback("");
            } else {
                stocktypeBean.setTwo_kickback(ji.getString("two_kickback"));
            }
            stocktypeList.add(stocktypeBean);
        }
        matchAttr.setStocktype(stocktypeList);

        return matchAttr;
    }

    /**
     * 积分商城商品列表
     */
    public static final List<HashMap<String, Object>> createIntegralProd(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(j.getString("listShop"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.opt(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();
            if (!jo.has("def_pic") || null == jo.getString("def_pic") || jo.getString("def_pic").equals("")) {
                mapObject.put("def_pic", "");
            } else {
                mapObject.put("def_pic", jo.getString("def_pic"));
            }
            if (!jo.has("shop_code") || null == jo.getString("shop_code") || jo.getString("shop_code").equals("")) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.getString("shop_code"));
            }
            if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price")
                    || jo.getString("shop_se_price").equals("null") || jo.getString("shop_se_price").equals("")) {
                mapObject.put("shop_se_price", "0");
            } else {
                mapObject.put("shop_se_price", jo.getString("shop_se_price"));
            }
            if (!jo.has("id") || null == jo.getString("id") || jo.getString("id").equals("")) {
                mapObject.put("id", "");
            } else {
                mapObject.put("id", jo.getString("id"));
            }
            retInfo.add(mapObject);
        }
        return retInfo;
    }

    /***
     * 得到商品详情
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final HashMap<String, Object> createShop(Context context, String result) throws JSONException {
        // result = result.replace("null", "\"\"");

        YDBHelper helper = new YDBHelper(context);
        Shop shop = new Shop();
        HashMap<String, Object> map = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        // //////
        // JSONObject jAttr1 = new JSONObject(j.optString("share_shop"));
        // ShareShop shareShop = new ShareShop();
        // shareShop.setCount(jAttr1.optLong("count"));
        // System.out.println("这里是share_shop="+jAttr1);

        // shop.setCount(jAttr1.optInt("count"));
        // /////
        if (!j.has("returnOneText") || null == j.getString("returnOneText") || j.getString("returnOneText").equals("null")
                || j.getString("returnOneText").equals("")) {
            shop.setReturnOneText("一键参与");
        } else {
            shop.setReturnOneText(j.optString("returnOneText"));

        }


        if (!j.has("id") || null == j.getString("id") || j.getString("id").equals("null")
                || j.getString("id").equals("")) {
            shop.setKickback(0);
        } else {
            shop.setKickback(j.optDouble("kickback"));
        }

        if (!j.has("zeroOrderNum") || null == j.getString("zeroOrderNum") || j.getString("zeroOrderNum").equals("null")
                || j.getString("zeroOrderNum").equals("")) {
            shop.setZeroOrderNum(0);
        } else {
            shop.setZeroOrderNum((float) j.optInt("zeroOrderNum", 0));
        }


        if (!j.has("color_count") || null == j.getString("color_count") || j.getString("color_count").equals("null")
                || j.getString("color_count").equals("")) {
            shop.setColor_count(0);
        } else {
            shop.setColor_count((float) j.optInt("color_count", 0));
        }


        if (!j.has("type_count") || null == j.getString("type_count") || j.getString("type_count").equals("null")
                || j.getString("type_count").equals("")) {
            shop.setType_count(0);
        } else {
            shop.setType_count((float) j.optInt("type_count", 0));
        }
        if (!j.has("work_count") || null == j.getString("work_count") || j.getString("work_count").equals("null")
                || j.getString("work_count").equals("")) {
            shop.setWork_count(0);
        } else {
            shop.setWork_count((float) j.optInt("work_count", 0));
        }
        if (!j.has("cost_count") || null == j.getString("cost_count") || j.getString("cost_count").equals("null")
                || j.getString("cost_count").equals("")) {
            shop.setCost_count(0);
        } else {
            shop.setCost_count((float) j.optInt("cost_count", 0));
        }
        if (!j.has("star_count") || null == j.getString("star_count") || j.getString("star_count").equals("null")
                || j.getString("star_count").equals("")) {
            shop.setStar_count(0);
        } else {
            shop.setStar_count(j.optDouble("star_count"));
        }
        if (!j.has("praise_count") || null == j.getString("praise_count") || j.getString("praise_count").equals("null")
                || j.getString("praise_count").equals("")) {
            shop.setPraise_count(0); // 好评数 Integer
        } else {
            shop.setPraise_count(j.optInt("praise_count")); // 好评数 Integer
        }
        if (!j.has("med_count") || null == j.getString("med_count") || j.getString("med_count").equals("null")
                || j.getString("med_count").equals("")) {
            shop.setMed_count(0); // 中评数 Integer
        } else {
            shop.setMed_count(j.optInt("med_count")); // 中评数 Integer
        }
        if (!j.has("bad_count") || null == j.getString("bad_count") || j.getString("bad_count").equals("null")
                || j.getString("bad_count").equals("")) {
            shop.setBad_count(0); // 差评数 Integer
        } else {
            shop.setBad_count(j.optInt("bad_count")); // 差评数 Integer
        }
        if (!j.has("eva_count") || null == j.getString("eva_count") || j.getString("eva_count").equals("null")
                || j.getString("eva_count").equals("")) {
            shop.setEva_count(0); // 评价总数 Integer
        } else {
            shop.setEva_count((float) j.optInt("eva_count", 0)); // 评价总数 Integer
        }
        // /
        if (YJApplication.instance.isLoginSucess()) {
            // shop.setS_time(j.optLong("s_time"));
            if (!j.has("c_time") || null == j.getString("c_time") || j.getString("c_time").equals("null")
                    || j.getString("c_time").equals("")) {
                shop.setC_time(0L);
            } else {
                shop.setC_time(j.optLong("c_time"));
            }
        }
        if (!j.has("s_time") || null == j.getString("s_time") || j.getString("s_time").equals("null")
                || j.getString("s_time").equals("")) {
            shop.setS_time(0L);
        } else {
            shop.setS_time(j.optLong("s_time"));
        }
        // /

        shop.setLike_id(("-1".equals(j.optString("like_id")) || !j.has("like_id")) ? -1 : 1); // 评价总数
        // Integer
        if (!j.has("cart_count") || null == j.getString("cart_count") || j.getString("cart_count").equals("null")
                || j.getString("cart_count").equals("")) {
            shop.setCart_count(0);// 购物车数量
        } else {
            shop.setCart_count(j.optInt("cart_count"));// 购物车数量
        }
        JSONObject obj = j.getJSONObject("shop");

        // ///
        if (!obj.has("audit_time") || null == obj.getString("audit_time") || obj.getString("audit_time").equals("null")
                || obj.getString("audit_time").equals("")) {
            shop.setAudit_time("0"); // 七天倒计时
        } else {
            shop.setAudit_time(obj.has("audit_time") ? obj.optString("audit_time") : "0"); // 七天倒计时
        }
        // ///
        if (!obj.has("supp_id") || null == obj.getString("supp_id") || obj.getString("supp_id").equals("")) {
            shop.setSupp_id(0);
        } else {
            shop.setSupp_id(obj.optInt("supp_id"));
        }
        if (!obj.has("shop_se_price") || null == obj.getString("shop_se_price")
                || obj.getString("shop_se_price").equals("null") || obj.getString("shop_se_price").equals("")) {
            shop.setShop_se_price(0);
        } else {
            shop.setShop_se_price(obj.optDouble("shop_se_price", 0));
        }

        if (!obj.has("advance_sale_days") || null == obj.getString("advance_sale_days") || obj.getString("advance_sale_days").equals("null")
                || obj.getString("advance_sale_days").equals("")) {
            shop.setAdvance_sale_days(0);
        } else {
            shop.setAdvance_sale_days(obj.optInt("advance_sale_days", 0));
        }


        if (!obj.has("shop_price") || null == obj.getString("shop_price") || obj.getString("shop_price").equals("null")
                || obj.getString("shop_price").equals("")) {
            shop.setShop_price(0);
        } else {
            shop.setShop_price(obj.optDouble("shop_price", 0));
        }


        if (!obj.has("app_shop_group_price") || null == obj.getString("app_shop_group_price") || obj.getString("app_shop_group_price").equals("null")
                || obj.getString("app_shop_group_price").equals("")) {
            shop.setApp_shop_group_price("1");
        } else {
            shop.setApp_shop_group_price(obj.optString("app_shop_group_price", "1"));
        }


        if (!obj.has("assmble_price") || null == obj.getString("assmble_price") || obj.getString("assmble_price").equals("null")
                || obj.getString("assmble_price").equals("")) {
            shop.setAssmble_price("1");
        } else {
            shop.setAssmble_price(obj.optString("assmble_price", "1"));
        }


        if (!obj.has("shop_group_price") || null == obj.getString("shop_group_price")
                || obj.getString("shop_group_price").equals("null") || obj.getString("shop_group_price").equals("")) {
            shop.setShop_group_price(0);
        } else {
            shop.setShop_group_price(obj.optDouble("shop_group_price", 0));
        }
        if (!obj.has("roll_price") || null == obj.getString("roll_price")
                || obj.getString("roll_price").equals("null") || obj.getString("roll_price").equals("")) {
            shop.setRoll_price(0);
        } else {
            shop.setRoll_price(obj.optDouble("roll_price", 0));
        }
        if (!obj.has("shop_code") || null == obj.getString("shop_code") || obj.getString("shop_code").equals("")) {
            shop.setShop_code("wu");
        } else {
            shop.setShop_code(obj.optString("shop_code", "wu"));
        }
        if (!obj.has("collocation_code") || null == obj.getString("collocation_code")
                || obj.getString("collocation_code").equals("")) {
            shop.setCollocation_code("");
        } else {
            shop.setCollocation_code(obj.optString("collocation_code", ""));
        }
        if (!obj.has("core") || null == obj.getString("core") || obj.getString("core").equals("")) {
            shop.setCore(0);
        } else {
            shop.setCore(obj.optInt("core"));
        }
        if (!obj.has("remark") || null == obj.getString("remark") || obj.getString("remark").equals("")) {
            shop.setRemark("wu");
        } else {
            shop.setRemark(obj.optString("remark", "wu"));
        }
        if (!obj.has("def_pic") || null == obj.getString("def_pic") || obj.getString("def_pic").equals("")) {
            shop.setDef_pic("wu");
        } else {
            shop.setDef_pic(obj.optString("def_pic", "wu"));
        }
        if (!obj.has("virtual_sales") || null == obj.getString("virtual_sales")
                || obj.getString("virtual_sales").equals("")) {
            shop.setVirtual_sales(0);
        } else {
            shop.setVirtual_sales(obj.optInt("virtual_sales", 0));
        }
        shop.setRandom(obj.optDouble("random", 0));
        shop.setKickback(obj.optDouble("kickback", 0));
        shop.setShop_up_time(obj.optLong("shop_up_time", 0L));
        shop.setInvertory_num(obj.optInt("invertory_num", 0));
        shop.setShop_name(obj.optString("shop_name", "wu"));
        shop.setId(obj.optInt("id", 0));
        shop.setContent(obj.optString("content", "wu"));
        shop.setActual_sales(obj.optInt("actual_sales", 0));
        shop.setIs_new(obj.optString("is_new", "wu"));
        shop.setShop_pic(obj.optString("shop_pic", "wu"));
        shop.setClicks(obj.optInt("clicks", 0));
        shop.setIs_hot(obj.optString("is_hot", "wu"));
        // shop.setVirtual_sales(obj.optInt("virtual_sales", 0));
        shop.setLove_num(obj.optInt("love_num", 0));
        shop.setShop_tag(obj.optString("shop_tag"));
        shop.setSupp_label(obj.optString("supp_label"));
        shop.setSupp_label_id(obj.optString("supp_label_id"));
        String ss = obj.optString("shop_type_id");
        if (!TextUtils.isEmpty(ss)) {
            ss = ss.replace("[", "");
            ss = ss.replace("]", "");
            String[] shop_type_id = ss.split(",");
            shop.setShop_type_id(shop_type_id);
        }

        shop.setType1(obj.optInt("type1"));

        String text = obj.optString("shop_attr");

        List<String[]> liststr = new ArrayList<String[]>();
        String str[] = text.split("_");
        for (int i = 0; i < str.length; i++) {
            String strson[] = str[i].split(",");
            int length = strson.length;
            String s[] = new String[length];
            for (int d = 0; d < strson.length; d++) {
                s[d] = strson[d];

            }
            liststr.add(strson);
        }
        shop.setShop_attr(liststr);

        // 属性数据
        List<String> sqls = new ArrayList<String>();

        List<ShopAttr> mealAttrLists = new ArrayList<>();

        JSONArray jAttr = new JSONArray(j.optString("attrList"));
        if (null != jAttr && jAttr.length() > 0) {
            helper.delete("delete from attr_info");
            for (int i = 0; i < jAttr.length(); i++) {
                ShopAttr sAttr = JSON.parseObject(jAttr.getString(i), ShopAttr.class);
                String sql = "insert into attr_info(_id,attr_name,icon,p_id,is_show)values('" + sAttr.getId() + "','"
                        + sAttr.getAttr_name() + "','" + sAttr.getIco() + "','" + sAttr.getParent_id() + "','"
                        + sAttr.getIs_show() + "')";
                sqls.add(sql);
                mealAttrLists.add(sAttr);


            }
            helper.update(sqls);
        }

        MealShopDetailsActivity.mealAttrList = mealAttrLists;


        // System.out.println("112233 shop=" + shop);
        // 1111111111111111111
        ShareShop shareshop = new ShareShop();

        String jj = j.optString("share_shop");

        LogYiFu.e("jj", jj + "");

        if (jj.equals("null") || jj == null || jj.equals("")) {
            // return null;
            map.put("shareshop", null);
        } else {

            JSONObject j1 = new JSONObject(j.optString("share_shop"));

            shareshop.setCount(j1.has("count") ? j1.optInt("count") : 0);

            JSONArray jArray = new JSONArray(j1.has("user_list") ? j1.optString("user_list") : null);

            List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

            for (int i = 0; i < jArray.length(); i++) {
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                JSONObject object = (JSONObject) jArray.get(i);
                Object object2 = object.getString("pic");
                // shareshop.setUser_list(null);
                hashMap.put("pic", object2);
                list.add(hashMap);
            }
            shareshop.setUser_list(list);
            map.put("shareshop", shareshop);
        }
        map.put("shop", shop);
        // 1111111111111111111
        HashMap<String, String> mapSupply = new HashMap<String, String>();
        if (!obj.has("supplier_label") || null == obj.getString("supplier_label")
                || obj.getString("supplier_label").equals("null") || obj.getString("supplier_label").equals("")
                || obj.getString("supplier_label").equals("{}")) {
            map.put("supplier_label", null);
        } else {
            JSONObject obj2 = obj.getJSONObject("supplier_label");
            // if (!obj2.has("id") || null == obj2.getString("id") ||
            // obj2.getString("id").equals("null")
            // ) {
            // mapSupply.put("id", "");
            // } else {
            // mapSupply.put("id", ""+obj2.getString("id"));
            // }
            // if (!obj2.has("is_show") || null == obj2.getString("is_show") ||
            // obj2.getString("is_show").equals("null")
            // ) {
            // mapSupply.put("is_show", "");
            // } else {
            // mapSupply.put("is_show", ""+obj2.getString("is_show"));
            // }
            if (!obj2.has("content1") || null == obj2.getString("content1")
                    || obj2.getString("content1").equals("null")) {
                mapSupply.put("content1", "");
            } else {
                mapSupply.put("content1", "" + obj2.getString("content1"));//
            }
            if (!obj2.has("content2") || null == obj2.getString("content2")
                    || obj2.getString("content2").equals("null")) {
                mapSupply.put("content2", "");
            } else {
                mapSupply.put("content2", "" + obj2.getString("content2"));
            }
            if (!obj2.has("name") || null == obj2.getString("name") || obj2.getString("name").equals("null")) {
                mapSupply.put("name", "");
            } else {
                mapSupply.put("name", "" + obj2.getString("name"));
            }
            // if (!obj2.has("remark") || null == obj2.getString("remark") ||
            // obj2.getString("remark").equals("null")
            // ) {
            // mapSupply.put("remark", "");
            // } else {
            // mapSupply.put("remark", ""+obj2.getString("remark"));
            // }
            // if (!obj2.has("add_time") || null == obj2.getString("add_time")
            // || obj2.getString("add_time").equals("null")
            // ) {
            // mapSupply.put("add_time", "");
            // } else {
            // mapSupply.put("add_time", ""+obj2.getString("remark"));
            // }
            map.put("supplier_label", mapSupply);
        }
        return map;
    }

    // TODO ZZQ

    /**
     * shareshop查询的解析
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */

    public static final ShareShop createShopShareshop(Context context, String result) throws JSONException {

        ShareShop shareshop = new ShareShop();
        JSONObject j = new JSONObject(result);
        if (null == j || j.equals("")) {
            return null;
        }

        String jj = j.getString("share_shop");

        LogYiFu.e("jj", jj + "");

        if (jj.equals("null") || jj == null) {
            return null;
        } else {

            JSONObject j1 = new JSONObject(j.optString("share_shop"));

            // shareshop.setCount(j1.has("count") ? j1.optInt("count") : 0);
            if (!j1.has("count") || null == j1.getString("count") || "".equals(j1.getString("count"))) {
                shareshop.setCount(0);
            } else {
                shareshop.setCount(j1.has("count") ? j1.optInt("count") : 0);
            }

            JSONArray jArray = new JSONArray(j1.has("user_list") ? j1.optString("user_list") : null);

            List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

            for (int i = 0; i < jArray.length(); i++) {
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                JSONObject object = (JSONObject) jArray.get(i);
                // Object object2 = object.getString("pic");
                // hashMap.put("pic", object2);
                if (!object.has("pic") || null == object.getString("pic") || "".equals(object.getString("pic"))) {
                    hashMap.put("pic", "");
                } else {
                    hashMap.put("pic", object.getString("pic"));
                }

                list.add(hashMap);
            }
            shareshop.setUser_list(list);
            return shareshop;
        }

    }

    public static HashMap<String, Object> createShopMeal(Context context, String result) throws JSONException {
        HashMap<String, Object> map = new HashMap<String, Object>();

        //
        JSONObject json = new JSONObject(result);

        YDBHelper helper = new YDBHelper(context);

        JSONObject pShop = json.optJSONObject("pShop");
        if (pShop != null) {
            // map.put("name", pShop.optString("name", "套餐"));
            if (!pShop.has("name") || null == pShop.getString("name") || "".equals(pShop.getString("name"))) {
                map.put("name", "");
            } else {
                map.put("name", pShop.optString("name", "套餐"));
            }
            // map.put("code", pShop.optString("code"));
            if (!pShop.has("code") || null == pShop.getString("code") || "".equals(pShop.getString("code"))) {
                map.put("code", "");
            } else {
                map.put("code", pShop.optString("code"));
            }

            // map.put("price", pShop.optString("price", "0"));
            if (!pShop.has("price") || null == pShop.getString("price") || "".equals(pShop.getString("price"))) {
                map.put("price", "0");
            } else {
                map.put("price", pShop.optString("price", "0"));
            }

            // map.put("num", pShop.optString("num", "0"));// 套餐总数
            if (!pShop.has("num") || null == pShop.getString("num") || "".equals(pShop.getString("num"))) {
                map.put("num", "0");
            } else {
                map.put("num", pShop.optString("num", "0"));// 套餐总数
            }

            // map.put("seq", pShop.optString("seq"));
            if (!pShop.has("seq") || null == pShop.getString("seq") || "".equals(pShop.getString("seq"))) {
                map.put("seq", "");
            } else {
                map.put("seq", pShop.optString("seq"));
            }

            // map.put("def_pic", pShop.optString("def_pic", "没有图"));// 主图
            if (!pShop.has("def_pic") || null == pShop.getString("def_pic") || "".equals(pShop.getString("def_pic"))) {
                map.put("def_pic", "");
            } else {
                map.put("def_pic", pShop.optString("def_pic", "没有图"));// 主图
            }

            // map.put("postage", pShop.optString("postage", "0"));
            if (!pShop.has("postage") || null == pShop.getString("postage") || "".equals(pShop.getString("postage"))) {
                map.put("postage", "0");
            } else {
                map.put("postage", pShop.optString("postage", "0"));
            }

            // map.put("p_type", pShop.optString("type"));
            if (!pShop.has("type") || null == pShop.getString("type") || "".equals(pShop.getString("type"))) {
                map.put("p_type", "");
            } else {
                map.put("p_type", pShop.optString("type"));
            }

        }

        // map.put("color_count", json.optString("color_count", "0"));
        if (!json.has("color_count") || null == json.getString("color_count")
                || "".equals(json.getString("color_count"))) {
            map.put("color_count", "0");
        } else {
            map.put("color_count", json.optString("color_count", "0"));
        }

        // map.put("work_count", json.optString("work_count", "0"));
        if (!json.has("work_count") || null == json.getString("work_count")
                || "".equals(json.getString("work_count"))) {
            map.put("work_count", "0");
        } else {
            map.put("work_count", json.optString("work_count", "0"));
        }

        // map.put("star_count", json.optString("star_count", "5.0"));
        if (!json.has("star_count") || null == json.getString("star_count")
                || "".equals(json.getString("star_count"))) {
            map.put("star_count", "0");
        } else {
            map.put("star_count", json.optString("star_count", "0"));
        }

        // map.put("type_count", json.optString("type_count", "0"));
        if (!json.has("type_count") || null == json.getString("type_count")
                || "".equals(json.getString("type_count"))) {
            map.put("type_count", "0");
        } else {
            map.put("type_count", json.optString("type_count", "0"));
        }

        // map.put("eva_count", json.optString("eva_count", "0"));
        if (!json.has("eva_count") || null == json.getString("eva_count") || "".equals(json.getString("eva_count"))) {
            map.put("eva_count", "0");
        } else {
            map.put("eva_count", json.optString("eva_count", "0"));
        }

        // map.put("cost_count", json.optString("cost_count", "0"));
        if (!json.has("cost_count") || null == json.getString("cost_count")
                || "".equals(json.getString("cost_count"))) {
            map.put("cost_count", "0");
        } else {
            map.put("cost_count", json.optString("cost_count", "0"));
        }

        // map.put("cart_count", json.optString("cart_count", "0"));
        if (!json.has("cart_count") || null == json.getString("cart_count")
                || "".equals(json.getString("cart_count"))) {
            map.put("cart_count", "0");
        } else {
            map.put("cart_count", json.optString("cart_count", "0"));
        }

        if (YJApplication.instance.isLoginSucess()) {
            // map.put("c_time", json.has("c_time") ? json.getLong("c_time") :
            // 0);
            if (!json.has("c_time") || null == json.getString("c_time") || "".equals(json.getString("c_time"))) {
                map.put("c_time", 0L);
            } else {
                map.put("c_time", json.has("c_time") ? json.getLong("c_time") : 0);
            }

            // map.put("s_time", json.has("s_time") ? json.getLong("s_time") :
            // 0);
            if (!json.has("s_time") || null == json.getString("s_time") || "".equals(json.getString("s_time"))) {
                map.put("s_time", 0L);
            } else {
                map.put("s_time", json.has("s_time") ? json.getLong("s_time") : 0);
            }
        }

        JSONArray shopList = json.optJSONArray("shopList");

        if (shopList != null && shopList.length() > 0) {

            List<Shop> list = new ArrayList<Shop>();

            for (int i = 0; i < shopList.length(); i++) {
                JSONObject obj = shopList.optJSONObject(i);
                Shop shop = new Shop();
                // shop.setShop_pic(obj.optString("shop_pic"));
                if (!obj.has("shop_pic") || null == obj.getString("shop_pic") || "".equals(obj.getString("shop_pic"))) {
                    shop.setShop_pic("");
                } else {
                    shop.setShop_pic(obj.optString("shop_pic"));
                    ;
                }

                // shop.setShop_code(obj.optString("shop_code"));
                if (!obj.has("shop_code") || null == obj.getString("shop_code")
                        || "".equals(obj.getString("shop_code"))) {
                    shop.setShop_code("");
                } else {
                    shop.setShop_code(obj.optString("shop_code"));
                }

                // shop.setShop_price(obj.optDouble("shop_price", 0));
                if (!obj.has("shop_price") || null == obj.getString("shop_price")
                        || "".equals(obj.getString("shop_price"))) {
                    shop.setShop_price(0);
                } else {
                    shop.setShop_price(obj.optDouble("shop_price", 0));
                }

                // shop.setShop_se_price(obj.optDouble("shop_se_price", 0));
                if (!obj.has("shop_se_price") || null == obj.getString("shop_se_price")
                        || "".equals(obj.getString("shop_se_price"))) {
                    shop.setShop_se_price(0);
                } else {
                    shop.setShop_se_price(obj.optDouble("shop_se_price", 0));
                }

                // shop.setDef_pic(obj.optString("def_pic", ""));
                if (!obj.has("def_pic") || null == obj.getString("def_pic") || "".equals(obj.getString("def_pic"))) {
                    shop.setDef_pic("");
                } else {
                    shop.setDef_pic(obj.optString("def_pic", ""));
                }

                // shop.setSupp_id(obj.optInt("supp_id", 0));
                if (!obj.has("supp_id") || null == obj.getString("supp_id") || "".equals(obj.getString("supp_id"))) {
                    shop.setSupp_id(0);
                } else {
                    shop.setSupp_id(obj.optInt("supp_id", 0));
                }

                // shop.setShop_name(obj.optString("shop_name", ""));
                if (!obj.has("shop_name") || null == obj.getString("shop_name")
                        || "".equals(obj.getString("shop_name"))) {
                    shop.setShop_name("");
                } else {
                    shop.setShop_name(obj.optString("shop_name", ""));
                }

                shop.setTrait(obj.optInt("age", 0) + "," + obj.optInt("stuff", 0) + "," + obj.optInt("stuff2", 0) + ","
                        + obj.optInt("fix_price", 0) + "," + obj.optInt("occasion", 0) + "," + obj.optInt("favorite", 0)
                        + "," + obj.optInt("stuff3", 0) + "," + obj.optInt("stuff4", 0) + ","
                        + obj.optInt("sys_color", 0) + "," + obj.optInt("pattern", 0) + "," + obj.optInt("trait", 0)
                        + "," + obj.optInt("trait2", 0) + "," + obj.optInt("trait3", 0) + "," + obj.optInt("style", 0));

                // String text = obj.getString("shop_attr");
                String text = "";
                if (!obj.has("shop_attr") || null == obj.getString("shop_attr")
                        || "".equals(obj.getString("shop_attr"))) {
                    text = "";
                } else {
                    text = obj.getString("shop_attr");
                }

                List<String[]> liststr = new ArrayList<String[]>();
                String str[] = text.split("_");
                for (int j = 0; j < str.length; j++) {
                    String strson[] = str[j].split(",");
                    int length = strson.length;
                    String s[] = new String[length];
                    for (int d = 0; d < strson.length; d++) {
                        s[d] = strson[d];
                    }
                    liststr.add(strson);
                }
                shop.setShop_attr(liststr);
                list.add(shop);
            }
            map.put("shopList", list);
        }

        JSONArray attrList = json.optJSONArray("attrList");

        if (attrList != null && attrList.length() > 0) {
            List<String> sqls = new ArrayList<String>();
            helper.delete("delete from attr_info");
            for (int i = 0; i < attrList.length(); i++) {
                ShopAttr sAttr = JSON.parseObject(attrList.getString(i), ShopAttr.class);
                String sql = "insert into attr_info(_id,attr_name,icon,p_id,is_show)values('" + sAttr.getId() + "','"
                        + sAttr.getAttr_name() + "','" + sAttr.getIco() + "','" + sAttr.getParent_id() + "','"
                        + sAttr.getIs_show() + "')";
                sqls.add(sql);
            }
            helper.update(sqls);
        }
        return map;

        //
        // JSONObject json = new JSONObject(result);
        //
        // YDBHelper helper = new YDBHelper(context);
        //
        // JSONObject pShop = json.optJSONObject("pShop");
        // if (pShop != null) {
        // map.put("name", pShop.optString("name", "套餐"));
        // map.put("price", pShop.optString("price", "0"));
        // map.put("num", pShop.optString("num", "0"));// 套餐总数
        // map.put("seq", pShop.optString("seq"));
        // map.put("code", pShop.optString("code"));
        // map.put("def_pic", pShop.optString("def_pic", "没有图"));// 主图
        // map.put("postage", pShop.optString("postage", "0"));
        // map.put("p_type", pShop.optString("type"));
        // }
        //
        // map.put("color_count", json.optString("color_count", "0"));
        // map.put("work_count", json.optString("work_count", "0"));
        // map.put("star_count", json.optString("star_count", "5.0"));
        // map.put("type_count", json.optString("type_count", "0"));
        // map.put("eva_count", json.optString("eva_count", "0"));
        // map.put("cost_count", json.optString("cost_count", "0"));
        // map.put("cart_count", json.optString("cart_count", "0"));
        //
        // if (YJApplication.instance.isLoginSucess()) {
        // map.put("c_time", json.has("c_time") ? json.getLong("c_time") : 0);
        // map.put("s_time", json.has("s_time") ? json.getLong("s_time") : 0);
        // }
        //
        // JSONArray shopList = json.optJSONArray("shopList");
        //
        // if (shopList != null && shopList.length() > 0) {
        //
        // List<Shop> list = new ArrayList<Shop>();
        //
        // for (int i = 0; i < shopList.length(); i++) {
        // JSONObject obj = shopList.optJSONObject(i);
        // Shop shop = new Shop();
        // shop.setShop_pic(obj.optString("shop_pic"));
        // shop.setShop_code(obj.optString("shop_code"));
        // shop.setShop_price(obj.optDouble("shop_price", 0));
        // shop.setShop_se_price(obj.optDouble("shop_se_price", 0));
        // shop.setDef_pic(obj.optString("def_pic", ""));
        // shop.setSupp_id(obj.optInt("supp_id", 0));
        // shop.setShop_name(obj.optString("shop_name", ""));
        // /**
        // * shop.setAge(obj.optInt("age",0));
        // * shop.setStuff(obj.optInt("stuff",0));
        // * shop.setStuff2(obj.optInt("stuff2",0));
        // * shop.setFix_price(obj.optInt("fix_price",0));
        // * shop.setOccasion(obj.optInt("occasion",0));
        // * shop.setFavorite(obj.optInt("favorite",0));
        // * shop.setStuff3(obj.optInt("stuff3",0));
        // * shop.setStuff4(obj.optInt("stuff4",0));
        // * shop.setSys_color(obj.optInt("sys_color",0));
        // * shop.setPattern(obj.optInt("pattern",0));
        // * shop.setTrait(obj.optInt("trait",0));
        // * shop.setTrait2(obj.optInt("trait2",0));
        // * shop.setTrait3(obj.optInt("trait3",0));
        // * shop.setStyle(obj.optInt("style",0));
        // */
        //
        // shop.setTrait(obj.optInt("age", 0) + "," + obj.optInt("stuff", 0) +
        // "," + obj.optInt("stuff2", 0) + ","
        // + obj.optInt("fix_price", 0) + "," + obj.optInt("occasion", 0) + ","
        // + obj.optInt("favorite", 0)
        // + "," + obj.optInt("stuff3", 0) + "," + obj.optInt("stuff4", 0) + ","
        // + obj.optInt("sys_color", 0) + "," + obj.optInt("pattern", 0) + "," +
        // obj.optInt("trait", 0)
        // + "," + obj.optInt("trait2", 0) + "," + obj.optInt("trait3", 0) + ","
        // + obj.optInt("style", 0));
        //
        // String text = obj.getString("shop_attr");
        // List<String[]> liststr = new ArrayList<String[]>();
        // String str[] = text.split("_");
        // for (int j = 0; j < str.length; j++) {
        // String strson[] = str[j].split(",");
        // int length = strson.length;
        // String s[] = new String[length];
        // for (int d = 0; d < strson.length; d++) {
        // s[d] = strson[d];
        // }
        // liststr.add(strson);
        // }
        // shop.setShop_attr(liststr);
        // list.add(shop);
        // }
        // map.put("shopList", list);
        // }
        //
        // JSONArray attrList = json.optJSONArray("attrList");
        //
        // if (attrList != null && attrList.length() > 0) {
        // List<String> sqls = new ArrayList<String>();
        // helper.delete("delete from attr_info");
        // for (int i = 0; i < attrList.length(); i++) {
        // ShopAttr sAttr = JSON.parseObject(attrList.getString(i),
        // ShopAttr.class);
        // String sql = "insert into
        // attr_info(_id,attr_name,icon,p_id,is_show)values('" + sAttr.getId() +
        // "','"
        // + sAttr.getAttr_name() + "','" + sAttr.getIco() + "','" +
        // sAttr.getParent_id() + "','"
        // + sAttr.getIs_show() + "')";
        // sqls.add(sql);
        // }
        // helper.update(sqls);
        // }
        // return map;
    }

    /***
     * 得到评论列表
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final List<ShopComment> createListShop_comment(Context context, String result) throws JSONException {

        JSONObject j = new JSONObject(result);
        String text = j.getString("comments");
        // List<ShopComment> list = JSON.parseArray(text, ShopComment.class);
        // List<Object> list = JSON.parseObject(text,new
        // TypeReference<List<Object>>(){});
        JSONArray jsonArray = new JSONArray(j.getString("comments"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }
        List<ShopComment> list = new ArrayList<ShopComment>();
        for (int i = 0; i < jsonArray.length(); i++) {
            // JSONObject jo = (JSONObject) jsonArray.opt(i);
            com.alibaba.fastjson.JSONObject jo = (com.alibaba.fastjson.JSONObject) JSON.parse(jsonArray.getString(i));
            ShopComment sComment = new ShopComment();
            sComment.setColor(jo.getInteger("color"));
            sComment.setAdd_date(jo.getLong("add_date") == null ? 0L : jo.getLong("add_date"));
            sComment.setComment_type(jo.getInteger("comment_type"));
            sComment.setContent(jo.getString("content"));
            sComment.setCost(jo.getInteger("cost"));
            sComment.setId(jo.getInteger("id"));
            // sComment.setIs_del(jo.getInteger("is_del"));
            sComment.setPic(jo.getString("pic"));
            sComment.setShop_code(jo.getString("shop_code"));
            sComment.setShop_color(jo.getString("shop_color"));
            sComment.setShop_name(jo.getString("shop_name"));
            sComment.setShop_price(jo.getString("shop_price"));
            sComment.setShop_size(jo.getString("shop_size"));
            sComment.setStar(jo.getInteger("star"));
            sComment.setStore_code(jo.getString("store_code"));
            // sComment.setSupp_add_date(jo.getLong("supp_add_date"));
            sComment.setSupp_content(jo.getString("supp_content"));
            // sComment.setSupp_id(jo.getInteger("supp_id"));
            sComment.setSupp_pic(jo.getString("supp_pic"));
            // sComment.setSupp_verify_status(supp_verify_status)
            sComment.setType(jo.getInteger("type"));

            sComment.setUser_id(jo.getInteger("user_id"));
            sComment.setUser_name(jo.getString("user_name"));
            sComment.setUser_url(jo.getString("user_url"));
            // sComment.setVerify_info(jo.getString("verify_info"));
            // sComment.setVerify_status(jo.getInteger("verify_status"));
            // sComment.setVerify_time(jo.getLong("verify_time"));
            // sComment.setVerify_user(jo.getInteger("verify_user"));
            sComment.setWork(jo.getInteger("work"));
            // 卖家回复
            if (null != jo.getString("suppComment")) {
                JSONArray jASuppComment = new JSONArray(jo.getString("suppComment"));
                if (null == jASuppComment || "".equals(jASuppComment) || jASuppComment.length() == 0) {
                    sComment.setSuppComment(null);
                } else {
                    List<SuppComment> suppComments = new ArrayList<SuppComment>();
                    for (int k = 0; k < jASuppComment.length(); k++) {
                        JSONObject joSupp = (JSONObject) jASuppComment.opt(k);
                        SuppComment suppComment = new SuppComment();
                        suppComment.setSupp_add_date(joSupp.optLong("supp_add_date", 0L));
                        suppComment.setSupp_content(joSupp.optString("supp_content"));
                        // suppComment.setSupp_pic(joSupp.getString("supp_pic"));
                        // suppComment.setSupp_verify_status(joSupp.getInt("supp_verify_status"));
                        suppComments.add(suppComment);
                    }
                    sComment.setSuppComment(suppComments);
                }
            }
            // 买家追评
            if (null != jo.getString("comment")) {
                JSONArray jAComment = new JSONArray(jo.getString("comment"));
                if (null == jAComment || "".equals(jAComment) || jAComment.length() == 0) {
                    sComment.setComment(null);
                } else {
                    List<Comment> comments = new ArrayList<Comment>();
                    for (int k = 0; k < jAComment.length(); k++) {
                        JSONObject joComment = (JSONObject) jAComment.opt(k);
                        Comment comment = new Comment();
                        comment.setAdd_date(joComment.optLong("add_date", 0L));
                        comment.setContent(joComment.optString("content"));
                        // comment.setId(joComment.getInt("id"));
                        comment.setPic(joComment.optString("pic"));
                        // comment.setVerify_status(joComment.getInt("verify_status"));
                        comments.add(comment);
                    }
                    sComment.setComment(comments);
                }
            }

            // 卖家回复追评
            if (null != jo.getString("suppEndComment")) {
                JSONArray jASuppReply = new JSONArray(jo.getString("suppEndComment"));
                if (null == jASuppReply || "".equals(jASuppReply) || jASuppReply.length() == 0) {
                    sComment.setSuppEndComment(null);
                } else {
                    List<SuppComment> suppReplys = new ArrayList<SuppComment>();
                    for (int k = 0; k < jASuppReply.length(); k++) {
                        JSONObject joSupp = (JSONObject) jASuppReply.opt(k);
                        SuppComment suppComment = new SuppComment();
                        suppComment.setSupp_add_date(joSupp.optLong("supp_add_date", 0L));
                        suppComment.setSupp_content(joSupp.optString("supp_content"));
                        // suppComment.setSupp_pic(joSupp.getString("supp_pic"));
                        // suppComment.setSupp_verify_status(joSupp.getInt("supp_verify_status"));
                        suppReplys.add(suppComment);
                    }
                    sComment.setSuppEndComment(suppReplys);
                }

            }
            list.add(sComment);
        }

        return list;
    }

    /***
     * 特卖得到商品购物车
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final List<ShopCart> createListShop_Cart(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);
        List<ShopCart> list;
        long p_deadline = 0;
        long s_time = 0L;
        int rowCount = 0;
        if (1 == j.getInt("status")) {
            String s = j.getString("pager");
            JSONObject j2 = new JSONObject(s);
            if (j2.has("rowCount")) {
                String ss = j2.getString("rowCount");
                rowCount = Integer.parseInt(ss);
            }
            if (j.has("p_deadline")) {
                String s1 = j.getString("p_deadline");
                p_deadline = Long.parseLong(s1);
            }
            if (j.has("s_time")) {
                String s2 = j.getString("s_time");
                s_time = Long.parseLong(s2);
            }
            String text = j.getString("listcart");
            list = JSON.parseArray(text, ShopCart.class);
            for (int i = 0; i < list.size(); i++) {
                ShopCart shopCart = list.get(i);
                shopCart.setP_deadline(p_deadline);
                shopCart.setS_time(s_time);
                shopCart.setRowCount(rowCount);
            }
        } else
            return null;
        return list;
    }

    /***
     * 正价 得到商品购物车
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final List<List<ShopCart>> createListShop_CartNew(Context context, String result)
            throws JSONException {
        JSONObject j = new JSONObject(result);
        List<List<ShopCart>> list = new ArrayList<List<ShopCart>>();
        List<ShopCart> inList;
        long s_deadline = 0L;
        long s_time = 0L;
        // int p_count=0;
        // int s_count=0;
        if (1 == j.getInt("status")) {
            String text = j.getString("listcart");
            if (j.has("s_deadline")) {
                String s1 = j.getString("s_deadline");
                s_deadline = Long.parseLong(s1);
            }
            if (j.has("s_time")) {
                String s2 = j.getString("s_time");
                s_time = Long.parseLong(s2);
            }
            // if(j.has("p_count")){
            // String s3=j.getString("p_count");
            // p_count=Integer.parseInt(s3);
            // LogYiFu.e("zzqyifu", ""+p_count);
            // }
            // if(j.has("s_count")){
            // String s4=j.getString("s_count");
            // s_count=Integer.parseInt(s4);
            // LogYiFu.e("zzqyifu", ""+s_count);
            // }
            // list = JSON.parseArray(text, ShopCart.class);
            if (null != j.getString("listcart")) {
                JSONArray JShopCartList = new JSONArray(j.getString("listcart"));
                for (int i = 0; i < JShopCartList.length(); i++) {
                    JSONObject joSupp = (JSONObject) JShopCartList.opt(i);
                    if (joSupp.has("shop_list")) {
                        String text2 = joSupp.getString("shop_list");
                        inList = JSON.parseArray(text2, ShopCart.class);
                        for (int k = 0; k < inList.size(); k++) {
                            ShopCart shopCart = inList.get(i);
                            shopCart.setS_deadline(s_deadline);
                            shopCart.setS_time(s_time);
                        }
                        list.add(inList);
                    } else {
                        List<ShopCart> sList = new ArrayList<ShopCart>();
                        String text3 = joSupp.toString();
                        ShopCart shopCart = JSON.parseObject(text3, ShopCart.class);
                        shopCart.setS_deadline(s_deadline);
                        shopCart.setS_time(s_time);
                        // shopCart.setP_count(p_count);
                        // shopCart.setS_count(s_count);
                        sList.add(shopCart);
                        inList = sList;
                        list.add(inList);
                    }
                }
            }
        } else {
            return null;
        }
        return list;
    }

    /**
     * 得到特卖商品购物车
     */
    /*
     * public static final List<ShopCartSpecial>
     * createListShop_CartSpecial(Context context, String result) throws
     * JSONException { JSONObject j = new JSONObject(result);
     * List<ShopCartSpecial> list; if (1 == j.getInt("status")) { String text =
     * j.getString("listcart"); list = JSON.parseArray(text,
     * ShopCartSpecial.class); } else return null; return list; }
     */

    /***
     * 得到邮寄地址
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final List<DeliveryAddress> createDeliverAddrList(Context context, String result)
            throws JSONException {
        JSONObject j = new JSONObject(result);
        String text = j.getString("listdt");
        List<DeliveryAddress> list = JSON.parseArray(text, DeliveryAddress.class);

        return list;
    }

    /***
     * 得到默认邮寄地址
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static HashMap<String, String> createDefaultDeliverAddrList(Context context, String result)
            throws JSONException {
        JSONObject j = new JSONObject(result);
        String text = j.getString("address");
        HashMap<String, String> mapRet = new HashMap<String, String>();
        JSONObject jo = new JSONObject(text);
        if (jo == null || jo.equals("") || text.equals("{}")) {
            return null;
        }
        mapRet.put("address", jo.getString("address"));
        mapRet.put("consignee", jo.getString("consignee"));
        mapRet.put("phone", jo.getString("phone"));
        mapRet.put("postcode", jo.getString("postcode"));

        return mapRet;
    }

    /***
     * 库存属性接口
     *
     * @param context
     * @param result
     * @param shop
     * @throws JSONException
     */
    public static Shop createStock_type(Context context, String result, Shop shop) throws JSONException {
        JSONObject j = new JSONObject(result);
        String text = j.getString("stocktype");
        List<StockType> list = JSON.parseArray(text, StockType.class);
        shop.setList_stock_type(list);

        return shop;
    }

    /***
     * 积分商品库存属性接口
     *
     * @param context
     * @param result
     * @param shop
     * @throws JSONException
     */
    public static IntegralShop createInteGoodStock_type(Context context, String result, IntegralShop shop)
            throws JSONException {
        JSONObject j = new JSONObject(result);
        String text = j.getString("stocktype");
        List<StockType> list = JSON.parseArray(text, StockType.class);
        shop.setList_stock_type(list);

        return shop;
    }

    /***
     * 库存属性接口
     *
     * @param context
     * @param result

     * @throws JSONException
     */
    public static List<StockType> createStock_type(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);
        String text = j.getString("stocktype");
        List<StockType> list = JSON.parseArray(text, StockType.class);
        return list;
    }

    /***
     * 订单详情接口
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static List<OrderShop> createOrderShop_Details(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);
        String text = j.getString("shops");
        List<OrderShop> list = JSON.parseArray(text, OrderShop.class);
        return list;
    }

    /**
     * 积分商品详情
     */
    public static final HashMap<String, Object> createIntegralGood(Context context, String result)
            throws JSONException {
        HashMap<String, Object> retInfo = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        IntegralShop shop = JSON.parseObject(j.getString("shop"), IntegralShop.class);
        retInfo.put("shop", shop);
        retInfo.put("praise_count", j.getString("praise_count"));
        retInfo.put("eva_count", j.getString("eva_count"));
        retInfo.put("med_count", j.getString("med_count"));
        retInfo.put("bad_count", j.getString("bad_count"));
        retInfo.put("status", j.getString("status"));
        return retInfo;
    }

    /**
     * 晒单详情
     */
    public static final HashMap<String, Object> createShaiDanDetials(Context context, String result)
            throws JSONException {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        mapRet.put("status", j.has("status") ? j.getString("status") : "");
        mapRet.put("message", j.has("message") ? j.getString("message") : "");

        JSONObject jo = j.getJSONObject("comment");
        mapRet.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");// 商品编号
        mapRet.put("user_name", jo.has("user_name") ? jo.getString("user_name") : "");
        mapRet.put("user_url", jo.has("user_url") ? jo.getString("user_url") : "");
        mapRet.put("user_id", jo.has("user_id") ? jo.getString("user_id") : "");
        mapRet.put("content", jo.has("content") ? jo.getString("content") : "");
        mapRet.put("pic", jo.has("pic") ? jo.getString("pic") : "");// 评论图片
        mapRet.put("add_date", jo.has("add_date") ? jo.getString("add_date") : "");
        mapRet.put("comment_size", jo.has("comment_size") ? jo.getString("comment_size") : "");
        mapRet.put("click_size", jo.has("click_size") ? jo.getString("click_size") : "");
        mapRet.put("click", jo.has("click") ? jo.getString("click") : "");

        mapRet.put("count", jo.has("count") ? jo.getString("count") : "");// 本期参与次数
        mapRet.put("issue_code", jo.has("issue_code") ? jo.getString("issue_code") : "");// 期数
        mapRet.put("lucky_number", jo.has("lucky_number") ? jo.getString("lucky_number") : "");// 幸运号码
        mapRet.put("otime", jo.has("otime") ? jo.getString("otime") : "");// 揭晓时间
        mapRet.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");// 商品名称
        LogYiFu.e("shaidanMap", mapRet.toString());
        return mapRet;
    }

    /**
     * 晒单评论列表
     */
    public static final HashMap<String, List<HashMap<String, Object>>> createShaiDanDetialsComment(Context context,
                                                                                                   String result) throws JSONException {
        HashMap<String, List<HashMap<String, Object>>> resultMap = new HashMap<String, List<HashMap<String, Object>>>();
        JSONObject j = new JSONObject(result);
        LogYiFu.e("shaidanMap", j.toString());
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray jsonComment = j.getJSONArray("comments");
        List<HashMap<String, Object>> commentsDataList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < jsonComment.length(); i++) {
            JSONObject jo = (JSONObject) jsonComment.opt(i);
            HashMap<String, Object> commentMap = new HashMap<String, Object>();
            commentMap.put("to_user_id", jo.has("to_user_id") ? jo.getString("to_user_id") : "");
            commentMap.put("to_user_name", jo.has("to_user_name") ? jo.getString("to_user_name") : "");
            commentMap.put("reuser_id", jo.has("reuser_id") ? jo.getString("reuser_id") : "");
            commentMap.put("add_date", jo.has("add_date") ? jo.getString("add_date") : "");
            commentMap.put("content", jo.has("content") ? jo.getString("content") : "");
            commentMap.put("user_name", jo.has("user_name") ? jo.getString("user_name") : "");
            commentMap.put("user_url", jo.has("user_url") ? jo.getString("user_url") : "");
            commentsDataList.add(commentMap);
        }
        resultMap.put("commentsDataList", commentsDataList);// 添加晒单评论列表
        return resultMap;
    }

    /***
     * 晒单发表评论
     */
    public static final HashMap<String, Object> createShaiDanComment(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (1 == jsonObject.getInt("status")) {
            mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
            return mapRet;
        }
        return null;

    }

    /***
     * 晒单点赞
     */
    public static final HashMap<String, Object> createShaiDanClick(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (1 == jsonObject.getInt("status")) {
            mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
            return mapRet;
        }
        return null;

    }

    /**
     * 我的店铺信息
     */
    public static final HashMap<String, Object> createMyShopInfo(Context context, String result) throws JSONException {
        HashMap<String, Object> retInfo = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray jaGoods = j.getJSONArray("shops");
        List<HashMap<String, String>> listGoods = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < jaGoods.length(); i++) {
            JSONObject jo = (JSONObject) jaGoods.opt(i);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("def_pic", jo.getString("def_pic"));
            map.put("shop_price", jo.getString("shop_price"));
            map.put("shop_code", jo.getString("shop_code"));
            map.put("shop_se_price", jo.getString("shop_se_price"));
            map.put("shop_name", jo.getString("shop_name"));
            listGoods.add(map);
        }
        retInfo.put("listMyFavor", listGoods);// 店铺商品列表

        Store store = JSON.parseObject(j.getString("store"), Store.class);
        retInfo.put("store", store);// 店铺信息

        JSONArray jaMyFavor = j.getJSONArray("likes");
        List<HashMap<String, String>> listMyFavor = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < jaMyFavor.length(); i++) {
            JSONObject jo = (JSONObject) jaMyFavor.opt(i);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("shop_price", jo.getString("shop_price"));
            map.put("shop_code", jo.getString("shop_code"));
            map.put("shop_se_price", jo.getString("shop_se_price"));
            map.put("shop_name", jo.getString("shop_name"));
            map.put("four_pic", jo.getString("four_pic"));
            listMyFavor.add(map);
        }
        retInfo.put("listMyFavor", listMyFavor);// 我的最爱列表

        return retInfo;
    }

    /**
     * 添加订单
     */
    public static final HashMap<String, Object> addOrder(Context context, String result) throws JSONException {
        HashMap<String, Object> retInfo = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        retInfo.put("order_code", j.getString("order_code"));
        retInfo.put("url", j.getInt("url"));
        retInfo.put("price", j.getDouble("price"));

        retInfo.put("tri", j.optInt("tri"));

        //开团团号
        if (!j.has("roll_code") || null == j.getString("roll_code")
                || j.getString("roll_code").equals("null")) {
            retInfo.put("roll_code", "");
        } else {

            retInfo.put("roll_code", j.optString("roll_code"));
        }


        if (!j.has("jumpDrawPage") || null == j.getString("jumpDrawPage")
                || j.getString("jumpDrawPage").equals("null")) {
            retInfo.put("jumpDrawPage", "1");
        } else {

            retInfo.put("jumpDrawPage", j.optString("jumpDrawPage"));
        }


//        if (j.optString("userIdentity").equals("0")) {
//            retInfo.put("price", 0);
//        }


        String array = j.optString("outShops");
        if (null != array && !"".equals(array)) {
            JSONArray ja = new JSONArray(j.optString("outShops"));
            if (null == ja || "".equals(ja)) {
                return retInfo;
            }
        }
        YCache.saveOrderToken(context, j.optString("orderToken"));

        return retInfo;
    }

    /**
     * 特卖添加订单
     */
    public static final HashMap<String, Object> addOrderSpecial(Context context, String result) throws JSONException {
        HashMap<String, Object> retInfo = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        retInfo.put("g_code", j.getString("g_code"));
        retInfo.put("url", j.getInt("url"));
        retInfo.put("price", j.getDouble("price"));
        String array = j.optString("outShops");
        if (null != array && !"".equals(array)) {
            JSONArray ja = new JSONArray(j.optString("outShops"));
            if (null == ja || "".equals(ja)) {
                return retInfo;
            }
        }
        YCache.saveOrderToken(context, j.optString("orderToken"));

        return retInfo;
    }

    /**
     * 会员商品添加订单
     */
    public static final HashMap<String, Object> addMemberOrder(Context context, String result) throws JSONException {
        HashMap<String, Object> retInfo = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        retInfo.put("order_code", j.getString("order_code"));
        retInfo.put("url", j.getInt("url"));
        retInfo.put("price", j.getDouble("price"));
        // JSONArray ja = new JSONArray(j.getString("outShops"));
        // if (null == ja || "".equals(ja)) {
        // return retInfo;
        // }
        // YCache.saveOrderToken(context, j.optString("orderToken"));

        return retInfo;
    }

    /**
     * 我的圈子数据
     */
    // public static final List<HashMap<String, Object>> createMyCircle(
    // Context context, String result) throws JSONException {
    // List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String,
    // Object>>();
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    // JSONArray jsonArray = new JSONArray(j.getString("circles"));
    // if (null == jsonArray || "".equals(jsonArray)) {
    // return null;
    // }
    // for (int i = 0; i < jsonArray.length(); i++) {
    // JSONObject jo = (JSONObject) jsonArray.opt(i);
    // HashMap<String, Object> mapObject = new HashMap<String, Object>();
    // mapObject.put("circle_id", jo.getString("circle_id"));
    // mapObject.put("pic", jo.getString("pic"));
    // mapObject.put("title", jo.getString("title"));
    // mapObject.put("content", jo.getString("content"));
    // mapObject.put("u_count", jo.getString("u_count"));
    // mapObject.put("n_count", jo.getString("n_count"));
    // mapObject.put("isNo", jo.getString("isNo"));
    // retInfo.add(mapObject);
    // }
    // return retInfo;
    // }

    /**
     * 我的粉丝列表
     */
    // public static final List<HashMap<String, Object>> createMyFansList(
    // Context context, String result) throws JSONException {
    // List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String,
    // Object>>();
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    // JSONArray jsonArray = new JSONArray(j.getString("fansList"));
    // if (null == jsonArray || "".equals(jsonArray)) {
    // return null;
    // }
    // for (int i = 0; i < jsonArray.length(); i++) {
    // JSONObject jo = (JSONObject) jsonArray.opt(i);
    // HashMap<String, Object> mapObject = new HashMap<String, Object>();
    // mapObject.put("user_id",
    // jo.has("user_id") ? jo.getString("user_id") : "");
    // mapObject.put("person_sign",
    // jo.has("person_sign") ? jo.getString("person_sign") : "");
    // mapObject.put("nickname",
    // jo.has("nickname") ? jo.getString("nickname") : "");
    // mapObject.put("isNo", jo.has("isNo") ? jo.getString("isNo") : "");
    // mapObject.put("pic", jo.has("pic") ? jo.getString("pic") : "");
    // mapObject.put("news_count",
    // jo.has("news_count") ? jo.getString("news_count") : "");
    // mapObject.put("fans_count",
    // jo.has("fans_count") ? jo.getString("fans_count") : "");
    // retInfo.add(mapObject);
    // }
    // return retInfo;
    // }

    /**
     * 所有的圈子数据
     */
    // public static final List<HashMap<String, Object>> createAllCircle(
    // Context context, String result) throws JSONException {
    // List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String,
    // Object>>();
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    // JSONArray jsonArray = new JSONArray(j.getString("circles"));
    // if (null == jsonArray || "".equals(jsonArray)) {
    // return null;
    // }
    // for (int i = 0; i < jsonArray.length(); i++) {
    // JSONObject jo = (JSONObject) jsonArray.opt(i);
    // HashMap<String, Object> mapObject = new HashMap<String, Object>();
    // mapObject.put("circle_id", jo.getString("circle_id"));
    // mapObject.put("pic", jo.getString("pic"));
    // mapObject.put("title", jo.getString("title"));
    // mapObject.put("content", jo.getString("content"));
    // mapObject.put("day_count", jo.getString("day_count"));
    // mapObject.put("isNo", jo.getString("isNo"));
    // retInfo.add(mapObject);
    // }
    // return retInfo;
    // }

    /**
     * 查询圈子个人主页
     */
    // public static final Map<String, Object> createCircleHomePager(
    // Context context, String result) throws JSONException {
    // // result = result.replace("null", "\"\"");
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    // HashMap<String, Object> mapObject = new HashMap<String, Object>();
    // mapObject.put("isNo", j.getString("isNo"));
    // JSONObject jsonObject = new JSONObject(j.getString("home_info"));
    // if (null == jsonObject || "".equals(jsonObject)) {
    // return null;
    // }
    // mapObject.put("birthday",
    // jsonObject.has("birthday") ? jsonObject.getString("birthday")
    // : "");
    // mapObject.put(
    // "fans_count",
    // jsonObject.has("fans_count") ? jsonObject
    // .getString("fans_count") : "");
    // mapObject.put(
    // "fol_user_id",
    // jsonObject.has("fans_count") ? jsonObject
    // .getString("fol_user_id") : "");
    // mapObject.put(
    // "circle_count",
    // jsonObject.has("circle_count") ? jsonObject
    // .getString("circle_count") : "");
    // mapObject.put("user_id",
    // jsonObject.has("user_id") ? jsonObject.getString("user_id")
    // : "");
    // mapObject.put(
    // "person_sign",
    // jsonObject.has("person_sign") ? jsonObject
    // .getString("person_sign") : "");
    // mapObject.put("nickname",
    // jsonObject.has("nickname") ? jsonObject.getString("nickname")
    // : "");
    // mapObject.put("id", jsonObject.has("id") ? jsonObject.getString("id")
    // : "");
    // mapObject.put("pic",
    // jsonObject.has("pic") ? jsonObject.getString("pic") : "");
    // mapObject.put("hobby",
    // jsonObject.has("hobby") ? jsonObject.getString("hobby") : "");
    // mapObject.put("city",
    // jsonObject.has("city") ? jsonObject.getString("city") : "");
    // mapObject.put("province",
    // jsonObject.has("province") ? jsonObject.getString("province")
    // : "");
    // MyLogYiFu.e("用户信息个人主页", mapObject.toString());
    // return mapObject;
    // }

    /**
     * 推荐圈子数据
     */
    // public static final List<HashMap<String, Object>> createRecomCircle(
    // Context context, String result) throws JSONException {
    // // result = result.replace("null", "\"\"");
    // List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String,
    // Object>>();
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    // JSONArray jsonArray = new JSONArray(j.getString("circles"));
    // if (null == jsonArray || "".equals(jsonArray)) {
    // return null;
    // }
    // for (int i = 0; i < jsonArray.length(); i++) {
    // JSONObject jo = (JSONObject) jsonArray.opt(i);
    // HashMap<String, Object> mapObject = new HashMap<String, Object>();
    // mapObject.put("ftitle", jo.optString("ftitle"));
    // mapObject.put("send_time", jo.optLong("send_time"));
    // mapObject.put("circle_id", jo.optString("circle_id"));
    // mapObject.put("user_id", jo.optString("user_id"));
    // mapObject.put("ctitle", jo.optString("ctitle"));
    // mapObject.put("nickname", jo.optString("nickname"));
    // mapObject.put("r_count", jo.optString("r_count"));
    // mapObject.put("pic", jo.optString("pic"));
    // mapObject.put("pic_list", jo.optString("pic_list"));
    // mapObject.put("news_id", jo.optString("news_id"));
    // mapObject.put("content", jo.optString("content"));
    //
    // retInfo.add(mapObject);
    //
    // }
    // // JSONArray jsonArray2 = new JSONArray(j.getString("page"));
    // // if (null == jsonArray2 || "".equals(jsonArray2)) {
    // // return null;
    // // }
    // // for( int a = 0; a < jsonArray2.length())
    // return retInfo;
    // }

    // public static final List<HashMap<String, Object>> createRecomCircle2(
    // Context context, String result) throws JSONException {
    // // result = result.replace("null", "\"\"");
    // List<HashMap<String, Object>> retInfo2 = new ArrayList<HashMap<String,
    // Object>>();
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    // JSONArray jsonArray = new JSONArray(j.getString("page"));
    // if (null == jsonArray || "".equals(jsonArray)) {
    // return null;
    // }
    // for (int i = 0; i < jsonArray.length(); i++) {
    // JSONObject jo = (JSONObject) jsonArray.opt(i);
    // HashMap<String, Object> mapObject2 = new HashMap<String, Object>();
    // mapObject2.put("pageCount", jo.optString("pageCount"));
    //
    // retInfo2.add(mapObject2);
    //
    // }
    // // JSONArray jsonArray2 = new JSONArray(j.getString("page"));
    // // if (null == jsonArray2 || "".equals(jsonArray2)) {
    // // return null;
    // // }
    // // for( int a = 0; a < jsonArray2.length())
    // return retInfo2;
    // }

    /**
     * 微信
     */
    public static final Map<String, String> createMapWxPrepay(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        JSONObject jo = new JSONObject(j.getString("xml"));
        map.put("nonce_str", jo.getString("nonce_str"));
        map.put("appid", jo.getString("appid"));
        map.put("sign", jo.getString("sign"));
        map.put("trade_type", jo.getString("trade_type"));
        map.put("return_msg", jo.getString("return_msg"));
        map.put("result_code", jo.getString("result_code"));
        map.put("mch_id", jo.getString("mch_id"));
        map.put("return_code", jo.getString("return_code"));
        map.put("prepay_id", jo.getString("prepay_id"));
        return map;
        // WxPayUtil.decodeXml(j.getString("xml"));
    }

    /**
     * 全部帖子列表数据
     */
    // public static final Map<String, List<HashMap<String, Object>>>
    // createPostList(
    // Context context, String result) throws JSONException {
    // // result = result.replace("null", "\"\"");
    // Map<String, List<HashMap<String, Object>>> allMap = new
    // LinkedHashMap<String, List<HashMap<String, Object>>>();
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    // int isNo = j.optInt("isNo");
    // List<HashMap<String, Object>> listData = new ArrayList<HashMap<String,
    // Object>>();
    // JSONArray ja = new JSONArray(j.getString("news"));
    // for (int i = 0; i < ja.length(); i++) {
    // JSONObject jo = (JSONObject) ja.opt(i);
    // HashMap<String, Object> map = new HashMap<String, Object>();
    // map.put("send_time", jo.optLong("send_time"));
    // map.put("top", jo.optString("top"));
    // map.put("user_id", jo.optString("user_id"));
    // map.put("fine", jo.optString("fine"));
    // map.put("hot", jo.optString("hot"));
    // map.put("tag", jo.optString("tag"));
    // map.put("pic_list", jo.optString("pic_list"));
    //
    // map.put("nickname", jo.optString("nickname"));
    // map.put("r_count", jo.optString("r_count"));
    // map.put("title", jo.optString("title"));
    // map.put("news_id", jo.optString("news_id"));
    //
    // listData.add(map);
    //
    // }
    // allMap.put("listData", listData);
    //
    // List<HashMap<String, Object>> uCountData = new ArrayList<HashMap<String,
    // Object>>();
    // JSONArray jaUCount = new JSONArray(j.getString("u_count"));
    // for (int i = 0; i < jaUCount.length(); i++) {
    // JSONObject jo = (JSONObject) jaUCount.opt(i);
    // HashMap<String, Object> map = new HashMap<String, Object>();
    // map.put("circle_id", jo.optString("circle_id"));
    // map.put("count", jo.optString("count"));
    // uCountData.add(map);
    // }
    // allMap.put("uCountData", uCountData);
    //
    // List<HashMap<String, Object>> nCountData = new ArrayList<HashMap<String,
    // Object>>();
    // JSONArray jaNCount = new JSONArray(j.getString("n_count"));
    // for (int i = 0; i < jaNCount.length(); i++) {
    // JSONObject jo = (JSONObject) jaNCount.opt(i);
    // HashMap<String, Object> map = new HashMap<String, Object>();
    // map.put("circle_id", jo.optString("circle_id"));
    // map.put("count", jo.optString("count"));
    // nCountData.add(map);
    // }
    // allMap.put("nCountData", nCountData);
    //
    // List<HashMap<String, Object>> circlesData = new ArrayList<HashMap<String,
    // Object>>();
    // JSONObject circleObj = new JSONObject(j.getString("circle"));
    // HashMap<String, Object> map = new HashMap<String, Object>();
    // map.put("pager", circleObj.optString("pager"));
    // map.put("circle_id", circleObj.optString("circle_id"));
    // map.put("title", circleObj.optString("title"));
    // map.put("content", circleObj.optString("content"));
    // map.put("pic", circleObj.optString("pic"));
    // map.put("bg_pic", circleObj.optString("bg_pic"));
    // map.put("create_time", circleObj.optString("create_time"));
    // map.put("user_id", circleObj.optString("user_id"));
    // map.put("admin", circleObj.optString("admin"));
    // map.put("circle_ids", circleObj.optString("circle_ids"));
    // map.put("tag", circleObj.optString("tag"));
    // map.put("isNo", isNo);
    // circlesData.add(map);
    // allMap.put("circlesData", circlesData);
    //
    // List<HashMap<String, Object>> adminsData = new ArrayList<HashMap<String,
    // Object>>();
    // JSONArray jaAdmins = new JSONArray(j.getString("admins"));
    // for (int i = 0; i < jaAdmins.length(); i++) {
    // JSONObject jo = (JSONObject) jaAdmins.opt(i);
    // HashMap<String, Object> mapAdmins = new HashMap<String, Object>();
    // mapAdmins.put("user_id",
    // jo.has("user_id") ? jo.optString("user_id") : "");
    // mapAdmins.put("nickname",
    // jo.has("nickname") ? jo.optString("nickname") : "");
    // mapAdmins
    // .put("admin", jo.has("admin") ? jo.optString("admin") : "");
    // mapAdmins.put("pic", jo.has("pic") ? jo.optString("pic") : "");
    // adminsData.add(mapAdmins);
    // }
    // allMap.put("adminsData", adminsData);
    //
    // List<HashMap<String, Object>> rnCountData = new ArrayList<HashMap<String,
    // Object>>();
    // HashMap<String, Object> rnCountMap = new HashMap<String, Object>();
    // String rn_count = j.has("rn_count") ? j.getString("rn_count") : "";
    // rnCountMap.put("rn_count", rn_count);
    // rnCountData.add(rnCountMap);
    // allMap.put("rnCountData", rnCountData);
    //
    // return allMap;
    // }

    /**
     * 精品和热门帖子列表数据
     */
    // public static final Map<String, List<HashMap<String, Object>>>
    // createPostListForJPAndHot(
    // Context context, String result) throws JSONException {
    // // result = result.replace("null", "\"\"");
    // Map<String, List<HashMap<String, Object>>> allMap = new
    // LinkedHashMap<String, List<HashMap<String, Object>>>();
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    //
    // List<HashMap<String, Object>> listData = new ArrayList<HashMap<String,
    // Object>>();
    // JSONArray ja = new JSONArray(j.getString("news"));
    // for (int i = 0; i < ja.length(); i++) {
    // JSONObject jo = (JSONObject) ja.opt(i);
    // HashMap<String, Object> map = new HashMap<String, Object>();
    // map.put("send_time", jo.optLong("send_time"));
    // map.put("top", jo.optString("top"));
    // map.put("user_id", jo.optString("user_id"));
    // map.put("fine", jo.optString("fine"));
    // map.put("hot", jo.optString("hot"));
    // map.put("tag", jo.optString("tag"));
    // map.put("pic_list", jo.optString("pic_list"));
    //
    // map.put("nickname", jo.optString("nickname"));
    // map.put("r_count", jo.optString("r_count"));
    // map.put("title", jo.optString("title"));
    // map.put("news_id", jo.optString("news_id"));
    //
    // listData.add(map);
    // }
    // allMap.put("listData", listData);
    //
    // return allMap;
    // }

    /**
     * 我的记录和动态数据
     */
    // public static final List<HashMap<String, Object>>
    // createMyRecordAndDynamic(
    // Context context, String result) throws JSONException {
    // // result = result.replace("null", "\"\"");
    // List<HashMap<String, Object>> lists = new ArrayList<HashMap<String,
    // Object>>();
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    //
    // JSONArray ja = new JSONArray(j.getString("news"));
    // for (int i = 0; i < ja.length(); i++) {
    // JSONObject jo = (JSONObject) ja.opt(i);
    // HashMap<String, Object> map = new HashMap<String, Object>();
    // map.put("send_time", jo.optLong("send_time"));
    // map.put("top", jo.optString("top"));
    // map.put("user_id", jo.optString("user_id"));
    // map.put("fine", jo.optString("fine"));
    // map.put("hot", jo.optString("hot"));
    // map.put("tag", jo.optString("tag"));
    // map.put("pic_list", jo.optString("pic_list"));
    // map.put("pic", jo.optString("pic"));
    // map.put("circle_id", jo.optString("circle_id"));
    //
    // map.put("nickname", jo.optString("nickname"));
    // map.put("r_count", jo.optString("r_count"));
    // map.put("title", jo.optString("title"));
    // map.put("news_id", jo.optString("news_id"));
    // lists.add(map);
    // }
    //
    // return lists;
    // }

    /**
     * 收藏帖子列表数据
     */
    // public static final List<HashMap<String, Object>> createCollectList(
    // Context context, String result) throws JSONException {
    // // result = result.replace("null", "\"\"");
    // System.out.println("result：" + result);
    // List<HashMap<String, Object>> lists = new ArrayList<HashMap<String,
    // Object>>();
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    //
    // JSONArray ja = new JSONArray(j.getString("collects"));
    // for (int i = 0; i < ja.length(); i++) {
    // JSONObject jo = (JSONObject) ja.opt(i);
    // HashMap<String, Object> map = new HashMap<String, Object>();
    // map.put("upic", jo.optString("upic"));
    // map.put("nickname", jo.optString("nickname"));
    // map.put("pic", jo.optString("pic"));
    // map.put("title", jo.optString("title"));
    // map.put("npic", jo.optString("npic"));
    // map.put("tag", jo.optString("tag"));
    // map.put("news_id", jo.optString("news_id"));
    // map.put("user_id", jo.optString("user_id"));
    // map.put("circle_id", jo.optString("circle_id"));
    //
    // lists.add(map);
    // }
    //
    // return lists;
    // }

    /**
     * 圈子成员列表
     */
    // public static final List<HashMap<String, Object>> createCircleMem(
    // Context context, String result) throws JSONException {
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    // List<HashMap<String, Object>> listData = new ArrayList<HashMap<String,
    // Object>>();
    //
    // JSONArray ja = new JSONArray(j.getString("circleUsers"));
    // for (int i = 0; i < ja.length(); i++) {
    // JSONObject jo = (JSONObject) ja.opt(i);
    // HashMap<String, Object> map = new HashMap<String, Object>();
    // map.put("user_id", jo.getString("user_id"));
    // map.put("nickname", jo.getString("nickname"));
    // map.put("admin", jo.has("admin") ? jo.getString("admin") : "");
    // map.put("pic", jo.has("pic") ? jo.getString("pic") : "");
    // listData.add(map);
    // }
    // return listData;
    // }

    /**
     * 圈子成员列表
     */
    // public static final List<Map<String, Object>> createCommentList(
    // Context context, String result) throws JSONException {
    // // //result = result.replace("null", "\"\"");
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    // List<Map<String, Object>> listData = new ArrayList<Map<String,
    // Object>>();
    //
    // JSONArray ja = new JSONArray(j.getString("rennews"));
    // for (int i = 0; i < ja.length(); i++) {
    // JSONObject jo = (JSONObject) ja.opt(i);
    // Map<String, Object> map = new HashMap<String, Object>();
    // map.put("user_id", jo.getString("user_id"));
    // map.put("nickname", jo.has("nickname") ? jo.getString("nickname")
    // : "");
    // map.put("re_id", jo.getString("re_id"));
    // map.put("news_id", jo.getString("news_id"));
    // map.put("content", jo.getString("content"));
    // map.put("ren_time", jo.getLong("ren_time"));
    // map.put("pic", jo.has("pic") ? jo.getString("pic") : "");
    // listData.add(map);
    // }
    // return listData;
    // }

    /**
     * 圈子发帖选择标签
     */
    // public static final List<Map<String, Object>> createTags(Context context,
    // String result) throws JSONException {
    // // result = result.replace("null", "\"\"");
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    // List<Map<String, Object>> listData = new ArrayList<Map<String,
    // Object>>();
    //
    // JSONArray ja = new JSONArray(j.getString("circle_tag"));
    // for (int i = 0; i < ja.length(); i++) {
    // JSONObject jo = (JSONObject) ja.opt(i);
    // HashMap<String, Object> map = new HashMap<String, Object>();
    // map.put("tag_name", jo.getString("tag_name"));
    // map.put("id", jo.getString("id"));
    //
    // listData.add(map);
    // }
    // return listData;
    // }

    // public static final HashMap<String, Object> createPostInfo(Context
    // context,
    // String result) throws JSONException {
    // // result = result.replace("null", "\"\"");
    // HashMap<String, Object> mapRet = new HashMap<String, Object>();
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    //
    // JSONObject jo = new JSONObject(j.getString("news"));
    // mapRet.put("skim_count", jo.optString("skim_count"));
    // mapRet.put("send_time", jo.optLong("send_time"));
    // mapRet.put("circle_id", jo.optString("circle_id"));
    // mapRet.put("upic", jo.optString("upic"));
    // mapRet.put("top", jo.optString("top"));
    // mapRet.put("hot", jo.optString("hot"));
    // mapRet.put("nickname", jo.optString("nickname"));
    // mapRet.put("rn_count", jo.optString("rn_count"));
    // mapRet.put("tag", jo.optString("tag"));
    //
    // mapRet.put("fine", jo.optString("fine"));
    // mapRet.put("user_id", jo.optString("user_id"));
    // mapRet.put("title", jo.optString("title"));
    // mapRet.put("pic_list", jo.optString("pic_list"));
    // mapRet.put("news_id", jo.optString("news_id"));
    // mapRet.put("content", jo.optString("content"));
    // mapRet.put("status", jo.optString("status"));
    // try {
    // mapRet.put("circleTitle", j.optJSONObject("circle").opt("title"));
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // // e.printStackTrace();
    // mapRet.put("circleTitle", "");
    // }
    // return mapRet;
    // }
    public static final HashMap<String, Object> createLogisticsInfo(Context context, String result)
            throws JSONException {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        mapRet.put("status", j.getString(RetInfo.status));
        mapRet.put("message", j.getString(RetInfo.message));
        JSONObject jo = new JSONObject(j.getString("logistics"));
        if (null == jo || jo.equals("")) {
            return null;
        }
        mapRet.put("logi_name", jo.getString("logi_name"));
        mapRet.put("logi_code", jo.getString("logi_code"));
        return mapRet;
    }

    /**
     * 我的物流数据
     */
    public static final List<HashMap<String, Object>> createLogistics(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(j.optString("data"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }
        JSONObject jj = (JSONObject) jsonArray.opt(0);
        if (null == jj || "".equals(jj)) {
            return null;
        }
        JSONObject jjj = jj.optJSONObject("lastResult");
        // JSONArray jsonArray2 = new JSONArray(jj.optString("lastResult"));
        if (null == jjj || "".equals(jjj)) {
            return null;
        }
        if (!jjj.has("data")) {
            return null;
        }
        JSONArray jsonArray3 = new JSONArray(jjj.has("data") ? jjj.optString("data") : null);
        if (null == jsonArray3 || "".equals(jsonArray3)) {
            return null;
        }

        for (int i = 0; i < jsonArray3.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray3.opt(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();
            mapObject.put("time", jo.optString("time"));
            mapObject.put("context", jo.optString("context"));
            mapObject.put("ftime", jo.optString("ftime"));
            retInfo.add(mapObject);
        }
        return retInfo;
    }

    // ---------------李锋-------------------------------------------

    /**
     * 主界面商品列表    type为6时，shop_code的值为shopGroupList中对应的id
     */
    public static final HashMap<String, Object> createMainTuijianData(Context context, String result)
            throws JSONException {
        HashMap<String, Object> retInfo = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (j.has("topShops")) {
            List<ShopOption> topShops = JSON.parseArray(j.getString("topShops"), ShopOption.class);
            retInfo.put("topShops", topShops);
        }

        List<ShopOption> centShops = JSON.parseArray(j.has("centShops") ? j.getString("centShops") : "",
                ShopOption.class);
        retInfo.put("centShops", centShops);
        return retInfo;
    }

    /**
     * 主界面商品列表
     */
    public static final HashMap<String, Object> createZeroShopData(Context context, String result)
            throws JSONException {
        HashMap<String, Object> retInfo = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (j.has("zeroShops")) {
            List<ShopOption> topShops = JSON.parseArray(j.getString("zeroShops"), ShopOption.class);
            retInfo.put("topShops", topShops);
        }

        return retInfo;
    }

    /**
     * 每日金额
     */
    public static final List<HashMap<String, Object>> createDailyAmountData(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray ja = new JSONArray(j.getString("data"));
        if (null == ja || "".equals(ja) || ja.length() == 0) {
            return null;
        }
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = (JSONObject) ja.opt(i);
            HashMap<String, Object> map = new HashMap<String, Object>();

            if (!jo.has("t") || null == jo.getString("t") || jo.getString("t").equals("null")) {
                map.put("date", "0");
            } else {

                map.put("date", jo.getString("t"));
            }

            if (!jo.has("count") || null == jo.getString("count") || jo.getString("count").equals("null")
                    || jo.getString("count").equals("")) {
                map.put("count", -1000);
            } else {

                map.put("count", jo.getInt("count"));
            }

            if (!jo.has("sum") || null == jo.getString("sum") || jo.getString("sum").equals("null")) {
                map.put("sum", "-1000");
            } else {

                map.put("sum", jo.getString("sum"));
            }

            // map.put("date", jo.getString("t"));
            // map.put("count", jo.getInt("count"));
            // map.put("sum", jo.getString("sum"));
            retInfo.add(map);
        }
        return retInfo;
    }

    /**
     * 每日访客
     */
    public static final List<HashMap<String, Object>> createDailyVisitorData(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (j.getString("data").equals("null")) {
            return null;
        }
        JSONArray ja = new JSONArray(j.getString("data"));
        if (null == ja || "".equals(ja)) {
            return null;
        }
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = (JSONObject) ja.opt(i);
            HashMap<String, Object> map = new HashMap<String, Object>();

            if (!jo.has("t") || null == jo.getString("t") || jo.getString("t").equals("null")) {
                map.put("date", "0");
            } else {

                map.put("date", jo.getString("t"));
            }

            if (!jo.has("sum") || null == jo.getString("sum") || jo.getString("sum").equals("null")) {
                map.put("sum", "0");
            } else {

                map.put("sum", jo.getString("sum"));
            }

            // map.put("date", jo.getString("t"));
            // map.put("sum", jo.getString("sum"));
            retInfo.add(map);

        }
        return retInfo;
    }

    /**
     * 资金明细
     */
    public static final List<FundDetail> createFundDetailsData(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        List<FundDetail> topShops = JSON.parseArray(j.getString("fundDetails"), FundDetail.class);
        return topShops;
    }

    /**
     * 账户明细-余额
     */
    public static final List<HashMap<String, Object>> createRebateList(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }

        JSONArray ja = new JSONArray(j.getString("data"));
        if (null == ja || "".equals(ja)) {
            return null;
        }
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = (JSONObject) ja.opt(i);
            HashMap<String, Object> map = new HashMap<String, Object>();

            if (!jo.has("order_code") || null == jo.getString("order_code")
                    || jo.getString("order_code").equals("null")) {
                map.put("order_code", "0");
            } else {

                map.put("order_code", jo.getString("order_code"));
            }

            if (!jo.has("add_date") || null == jo.getString("add_date") || jo.getString("add_date").equals("null")) {
                map.put("add_date", 0L);
            } else {

                map.put("add_date", jo.getLong("add_date"));
            }

            if (!jo.has("money") || null == jo.getString("money") || jo.getString("money").equals("null")) {
                map.put("money", 0);
            } else {

                map.put("money", jo.getDouble("money"));
            }

            if (!jo.has("is_free") || null == jo.getString("is_free") || jo.getString("is_free").equals("null")) {
                map.put("is_free", 0);
            } else {

                map.put("is_free", jo.getInt("is_free"));
            }

            if (!jo.has("status") || null == jo.getString("status") || jo.getString("status").equals("null")) {
                map.put("status", "");
            } else {

                map.put("status", jo.has("status") ? jo.getString("status") : "0");
            }

            if (!jo.has("user_name") || null == jo.getString("user_name")) {
                map.put("user_name", "");
            } else {

                map.put("user_name", jo.has("user_name") ? jo.getString("user_name") : "0");
            }

            if (!jo.has("order_price") || null == jo.getString("order_price")
                    || jo.getString("order_price").equals("null")) {
                map.put("order_price", 0);
            } else {

                map.put("order_price", jo.has("order_price") ? jo.getString("order_price") : "0");
            }

            /**
             * type:分类 (1一级回佣2二级回佣3供应商应得金额,4h5优惠券.5,三级回佣6四级回佣,7会员卡钱.8任务翻倍)
             */
            if (!jo.has("type") || null == jo.getString("type") || jo.getString("type").equals("null")) {
                map.put("type", "0");
            } else {

                map.put("type", jo.has("type") ? jo.getString("type") : "0");
            }

            if (!jo.has("is_buy") || null == jo.getString("is_buy") || jo.getString("is_buy").equals("null")) {
                map.put("is_buy", "0");
            } else {

                map.put("is_buy", jo.has("is_buy") ? jo.getString("is_buy") : "0");
            }

            // map.put("order_code", jo.getString("order_code"));
            // map.put("add_date", jo.getLong("add_date"));
            // map.put("money", jo.getDouble("money"));
            // map.put("is_free", jo.getInt("is_free"));
            // map.put("status", jo.has("status") ? jo.getString("status") :
            // "0");
            // map.put("user_name", jo.has("user_name") ?
            // jo.getString("user_name") : "");
            // map.put("order_price", jo.has("order_price") ?
            // jo.getString("order_price") : "");
            retInfo.add(map);
        }
        return retInfo;
    }

    /**
     * 我的足迹列表
     */
    public static final List<HashMap<String, Object>> createMyStepsList(Context context, String result)
            throws JSONException {
        // result = result.replace("null", "\"\"");
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }

        JSONArray jsonArray = j.getJSONArray("mysList");
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            // JSONObject jo = (JSONObject) jsonArray.opt(i);
            JSONObject jo = jsonArray.getJSONObject(i);
            if (jo == null || "".equals(jo)) {
                continue;
            }
            if (jo.optString("def_pic").equals("null") || jo.optString("def_pic").equals("")
                    || jo.optString("def_pic") == null) {
                continue;
            }
            HashMap<String, Object> mapObject = new HashMap<String, Object>();

            if (!jo.has("kickback") || null == jo.getString("kickback") || jo.getString("kickback").equals("null")) {
                mapObject.put("kickback", 0);
            } else {

                mapObject.put("kickback", jo.has("kickback") ? jo.getString("kickback") : "0");
            }

            if (!jo.has("app_shop_group_price") || null == jo.getString("app_shop_group_price") || jo.getString("app_shop_group_price").equals("null")) {
                mapObject.put("app_shop_group_price", "1.5");
            } else {

                mapObject.put("app_shop_group_price", jo.has("app_shop_group_price") ? jo.getString("app_shop_group_price") : "1.5");
            }

            if (!jo.has("def_pic") || null == jo.getString("def_pic") || jo.getString("def_pic").equals("null")) {
                mapObject.put("def_pic", 0);
            } else {

                mapObject.put("def_pic", jo.has("def_pic") ? jo.getString("def_pic") : "0");
            }

            if (!jo.has("shop_price") || null == jo.getString("shop_price")
                    || jo.getString("shop_price").equals("null")) {
                mapObject.put("shop_price", jo.optString("shop_price"));
            } else {

                mapObject.put("shop_price", jo.has("shop_price") ? jo.getString("shop_price") : "0");
            }

            if (!jo.has("shop_code") || null == jo.getString("shop_code") || jo.getString("shop_code").equals("null")) {
                mapObject.put("shop_code", "0");
            } else {

                mapObject.put("shop_code", jo.optString("shop_code"));
            }

            if (!jo.has("isLike") || null == jo.getString("isLike") || jo.getString("isLike").equals("null")) {
                mapObject.put("isLike", "0");
            } else {

                mapObject.put("isLike", jo.optString("isLike"));
            }

            if (!jo.has("is_del") || null == jo.getString("is_del") || jo.getString("is_del").equals("null")) {
                mapObject.put("is_del", "0");
            } else {

                mapObject.put("is_del", jo.optString("is_del"));
            }

            if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price")
                    || jo.getString("shop_se_price").equals("null")) {
                mapObject.put("shop_se_price", "0");
            } else {

                mapObject.put("shop_se_price", jo.optString("shop_se_price"));
            }

            if (!jo.has("isCart") || null == jo.getString("isCart") || jo.getString("isCart").equals("null")) {
                mapObject.put("isCart", "0");
            } else {

                mapObject.put("isCart", jo.optString("isCart"));
            }

            if (!jo.has("shop_name") || null == jo.getString("shop_name") || jo.getString("shop_name").equals("null")) {
                mapObject.put("shop_name", "0");
            } else {

                mapObject.put("shop_name", jo.optString("shop_name"));
            }

            // mapObject.put("kickback", jo.optString("kickback"));
            // mapObject.put("def_pic", jo.optString("def_pic"));
            // mapObject.put("shop_price", jo.optString("shop_price"));
            // mapObject.put("shop_code", jo.optString("shop_code"));
            // mapObject.put("isLike", jo.optString("isLike"));
            // mapObject.put("is_del", jo.optString("is_del"));
            // mapObject.put("shop_se_price", jo.optString("shop_se_price"));
            // mapObject.put("isCart", jo.optString("isCart"));
            // mapObject.put("shop_name", jo.optString("shop_name"));
            retInfo.add(mapObject);
        }
        return retInfo;
    }

    /**
     * 登陆设备列表
     */
    public static final List<HashMap<String, Object>> loginDeviceList(Context context, String result)
            throws JSONException {
        // result = result.replace("null", "\"\"");
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(j.getString("loginlist"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.opt(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();

            if (!jo.has("user_id") || null == jo.getString("user_id") || jo.getString("user_id").equals("null")) {
                mapObject.put("user_id", "0");
            } else {

                mapObject.put("user_id", jo.optString("user_id"));
            }

            if (!jo.has("login_time") || null == jo.getString("login_time")
                    || jo.getString("login_time").equals("null")) {
                mapObject.put("login_time", 0L);
            } else {

                mapObject.put("login_time", jo.optLong("login_time"));
            }

            if (!jo.has("ip") || null == jo.getString("ip") || jo.getString("ip").equals("null")) {
                mapObject.put("ip", "0");
            } else {

                mapObject.put("ip", jo.optString("ip"));
            }

            if (!jo.has("id") || null == jo.getString("id") || jo.getString("id").equals("null")) {
                mapObject.put("id", "0");
            } else {

                mapObject.put("id", jo.optString("id"));
            }

            if (!jo.has("device") || null == jo.getString("device")) {
                mapObject.put("device", "0");
            } else {

                mapObject.put("device", jo.optString("device"));
            }

            // mapObject.put("user_id", jo.optString("user_id"));
            // mapObject.put("login_time", jo.optLong("login_time"));
            // mapObject.put("ip", jo.optString("ip"));
            // mapObject.put("id", jo.optString("id"));
            // mapObject.put("device", jo.optString("device"));
            retInfo.add(mapObject);
        }
        return retInfo;
    }

    /***
     * 我的银行卡列表
     */
    public static final List<MyBankCard> createMyBankCardList(Context context, String result) throws Exception {
        List<MyBankCard> myBankCards = new ArrayList<MyBankCard>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(jsonObject.getString("bankCards"));
        for (int i = 0; i < jsonArray.length(); i++) {
            MyBankCard myBankCard = new MyBankCard();
            JSONObject j = (JSONObject) jsonArray.opt(i);
            myBankCard = JSON.parseObject(j.toString(), MyBankCard.class);
            myBankCards.add(myBankCard);
        }
        return myBankCards;
    }

    /***
     * 得到买家信息
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final HashMap<String, String> createBuyerInfo(Context context, String result) throws JSONException {
        HashMap<String, String> retInfo = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);

        if (!j.has("status") || null == j.getString("status")) {
            retInfo.put("status", "-1000");
        } else {

            retInfo.put("status", j.getString("status"));
        }

        if (!j.has("message") || null == j.getString("message")) {
            retInfo.put("message", "");
        } else {

            retInfo.put("message", j.getString("message"));
        }

        if (!j.has("nick_name") || null == j.getString("nick_name")) {
            retInfo.put("nick_name", "");
        } else {

            retInfo.put("nick_name", j.getString("nick_name"));
        }

        if (!j.has("sell_msg") || null == j.getString("sell_msg")) {
            retInfo.put("sell_msg", "");
        } else {

            retInfo.put("sell_msg", j.getString("sell_msg"));
        }

        // retInfo.put("status", j.getString("status"));
        // retInfo.put("message", j.getString("message"));
        // retInfo.put("nick_name", j.getString("nick_name"));
        // retInfo.put("sell_msg", j.getString("sell_msg"));
        return retInfo;
    }

    /***
     * 最大最小值界定
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final HashMap<String, String> createMaxAndMin(Context context, String result) throws JSONException {
        HashMap<String, String> retInfo = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);

        if (!j.has("status") || null == j.getString("status")) {
            retInfo.put("status", "");
        } else {

            retInfo.put("status", j.getString("status"));
        }

        if (!j.has("message") || null == j.getString("message")) {
            retInfo.put("message", "");
        } else {

            retInfo.put("message", j.getString("message"));
        }

        if (!j.has("min") || null == j.getString("min") || j.getString("min").equals("null")) {
            retInfo.put("min", "");
        } else {

            retInfo.put("min", j.has("min") ? j.getString("min") : "5");
        }

        if (!j.has("max") || null == j.getString("max") || j.getString("max").equals("null")) {
            retInfo.put("max", "");
        } else {

            retInfo.put("max", j.has("max") ? j.getString("max") : "2000");
        }
        if (!j.has("lastBname") || null == j.getString("lastBname") || j.getString("lastBname").equals("null")) {
            retInfo.put("lastBname", "");
        } else {

            retInfo.put("lastBname", j.has("lastBname") ? j.getString("lastBname") : "");
        }
        // retInfo.put("status", j.getString("status"));
        // retInfo.put("message", j.getString("message"));
        // retInfo.put("min", j.has("min") ? j.getString("min") : "5");
        // retInfo.put("max", j.has("max") ? j.getString("max") : "2000");
        return retInfo;
    }

    /***
     * 我的钱包-账户明细-提现
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final List<HashMap<String, Object>> createMyBankDepositModel(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }

        JSONArray ja = new JSONArray(j.getString("data"));
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = (JSONObject) ja.opt(i);

            HashMap<String, Object> map = new HashMap<String, Object>();

            if (!jo.has("id") || null == jo.getString("id")) {
                map.put("id", 0);
            } else {

                map.put("id", jo.has("id") ? jo.optInt("id") : 0);
            }

            if (!jo.has("business_code") || null == jo.getString("business_code")) {
                map.put("business_code", 0);
            } else {

                map.put("business_code", jo.has("business_code") ? jo.optString("business_code") : 0);
            }

            if (!jo.has("add_date") || null == jo.getString("add_date") || jo.getString("add_date").equals("null")) {
                map.put("add_date", "0");
            } else {

                map.put("add_date", jo.has("add_date") ? jo.optString("add_date") : "0");
            }

            if (!jo.has("money") || null == jo.getString("money") || jo.getString("money").equals("null")) {
                map.put("money", 0);
            } else {

                map.put("money", jo.has("money") ? jo.optString("money") : 0);
            }

            if (!jo.has("collect_bank_name") || null == jo.getString("collect_bank_name")) {
                map.put("collect_bank_name", "");
            } else {

                map.put("collect_bank_name", jo.has("collect_bank_name") ? jo.optString("collect_bank_name") : "");
            }

            if (!jo.has("collect_name") || null == jo.getString("collect_name")) {
                map.put("collect_name", "");
            } else {

                map.put("collect_name", jo.has("collect_name") ? jo.optString("collect_name") : "");
            }

            if (!jo.has("collect_bank_code") || null == jo.getString("collect_bank_code")) {
                map.put("collect_bank_code", "");
            } else {

                map.put("collect_bank_code", jo.has("collect_bank_code") ? jo.optString("collect_bank_code") : "");
            }

            if (!jo.has("collect_phone") || null == jo.getString("collect_phone")) {
                map.put("collect_phone", "");
            } else {

                map.put("collect_phone", jo.has("collect_phone") ? jo.optString("collect_phone") : "");
            }

            if (!jo.has("user_id") || null == jo.getString("user_id")) {
                map.put("user_id", "");
            } else {

                map.put("user_id", jo.has("user_id") ? jo.optString("user_id") : "");
            }

            if (!jo.has("message") || null == jo.getString("message")) {
                map.put("message", "");
            } else {

                map.put("message", jo.has("message") ? jo.optString("message") : "");
            }

            if (!jo.has("check") || null == jo.getString("check")) {
                map.put("check", "");
            } else {

                map.put("check", jo.has("check") ? jo.optString("check") : "");
            }

            if (!jo.has("check_date") || null == jo.getString("check_date")) {
                map.put("check_date", "");
            } else {

                map.put("check_date", jo.has("check_date") ? jo.optString("check_date") : "");
            }

            if (!jo.has("check_code") || null == jo.getString("check_code")) {
                map.put("check_code", "");
            } else {

                map.put("check_code", jo.has("check_code") ? jo.optString("check_code") : "");
            }

            if (!jo.has("check_message") || null == jo.getString("check_message")) {
                map.put("check_message", "");
            } else {

                map.put("check_message", jo.has("check_message") ? jo.optString("check_message") : "");
            }

            if (!jo.has("fail_details") || null == jo.getString("fail_details")) {
                map.put("fail_details", "");
            } else {

                map.put("fail_details", jo.has("fail_details") ? jo.optString("fail_details") : "");
            }

            if (!jo.has("batch_no") || null == jo.getString("batch_no")) {
                map.put("batch_no", "");
            } else {

                map.put("batch_no", jo.has("batch_no") ? jo.optString("batch_no") : "");
            }

            if (!jo.has("transfer_error") || null == jo.getString("transfer_error")) {
                map.put("transfer_error", "");
            } else {

                map.put("transfer_error", jo.has("transfer_error") ? jo.optString("transfer_error") : "");
            }

            // map.put("id", jo.has("id") ? jo.optInt("id") : 0);
            // map.put("business_code", jo.has("business_code") ?
            // jo.optString("business_code") : 0);
            // map.put("add_date", jo.has("add_date") ? jo.optLong("add_date") :
            // 0);
            // map.put("money", jo.has("money") ? jo.optString("money") : 0);
            // map.put("collect_bank_name", jo.has("collect_bank_name") ?
            // jo.optString("collect_bank_name") : "");
            // map.put("collect_name", jo.has("collect_name") ?
            // jo.optString("collect_name") : "");
            // map.put("identity", jo.has("identity") ? jo.optString("identity")
            // : "");
            // map.put("collect_bank_code", jo.has("collect_bank_code") ?
            // jo.optString("collect_bank_code") : "");
            // map.put("collect_phone", jo.has("collect_phone") ?
            // jo.optString("collect_phone") : "");
            // map.put("user_id", jo.has("user_id") ? jo.optString("user_id") :
            // "");
            // map.put("message", jo.has("message") ? jo.optString("message") :
            // "");
            // map.put("check", jo.has("check") ? jo.optString("check") : "");
            // map.put("check_date", jo.has("check_date") ?
            // jo.optString("check_date") : "");
            // map.put("check_code", jo.has("check_code") ?
            // jo.optString("check_code") : "");
            // map.put("check_message", jo.has("check_message") ?
            // jo.optString("check_message") : "");
            // map.put("fail_details", jo.has("fail_details") ?
            // jo.optString("fail_details") : "");
            // map.put("batch_no", jo.has("batch_no") ? jo.optString("batch_no")
            // : "");
            // map.put("transfer_error", jo.has("transfer_error") ?
            // jo.optString("transfer_error") : "");

            lists.add(map);
        }
        return lists;
    }

    /****
     * 待付款--选择多个订单一起支付
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final HashMap<String, String> createOrders(Context context, String result) throws JSONException {
        HashMap<String, String> retInfo = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);

        if (!j.has("transfer_error") || null == j.getString("transfer_error")) {
            retInfo.put("status", "-1000");
        } else {

            retInfo.put("status", j.getString("status"));
        }

        if (!j.has("message") || null == j.getString("message")) {
            retInfo.put("message", "-1000");
        } else {

            retInfo.put("message", j.getString("message"));
        }

        // retInfo.put("status", j.getString("status"));
        // retInfo.put("message", j.getString("message"));
        if (1 == j.getInt("status")) {

            if (!j.has("g_code") || null == j.getString("g_code")) {
                retInfo.put("g_code", "-1000");
            } else {

                retInfo.put("g_code", j.getString("g_code"));
            }

            if (!j.has("orderCodes") || null == j.getString("orderCodes")) {
                retInfo.put("orderCodes", "0");
            } else {

                retInfo.put("orderCodes", j.getString("orderCodes"));
            }

            if (!j.has("orderPrice") || null == j.getString("orderPrice")) {
                retInfo.put("orderPrice", "0");
            } else {

                retInfo.put("orderPrice", j.getString("orderPrice"));
            }

            // retInfo.put("g_code", j.getString("g_code"));
            // retInfo.put("orderCodes", j.getString("orderCodes"));
            // retInfo.put("orderPrice", j.getString("orderPrice"));
        }
        return retInfo;
    }

    /****
     * 查看退换货
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final ReturnShop createReturnShop(Context context, String result) throws JSONException {
        ReturnShop rShop = null;
        JSONObject j = new JSONObject(result);
        if (1 == j.getInt("status")) {
            rShop = JSON.parseObject(j.getString("returnShop"), ReturnShop.class);
        }
        return rShop;
    }

    /**
     * 修改信息后
     */
    public static final UserInfo createUserInfo(Context context, String result) throws JSONException {
        UserInfo userInfo = null;
        JSONObject j = new JSONObject(result);
        if (j.getInt("status") == 1) {
            userInfo = JSON.parseObject(j.getString("userinfo"), UserInfo.class);
        }
        LogYiFu.e("yong户信息", userInfo.toString());

        YCache.setCacheUser(context, userInfo);
        return userInfo;
    }

    /***
     * 获取各大公司的物流
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final List<String> createGetKuaiDiCompany(Context context, String result) throws JSONException {
        List<String> lists = new ArrayList<String>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }

        JSONObject obj = new JSONObject(j.getString("data"));
        Iterator<String> keys = obj.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            String value = obj.optString(next);
            lists.add(next + "==" + value);
        }
        return lists;
    }

    /**
     * 得到商品链接
     */
    public static final String createShopLink(Context context, String result) throws JSONException {
        String shopLink = null;
        JSONObject j = new JSONObject(result);
        if (j.getInt("status") == 1 || j.getInt("status") == 1050) {

            if (!j.has("link") || null == j.getString("link")) {
                shopLink = "";
            } else {

                shopLink = j.getString("link");
            }

            // shopLink = j.getString("link");
        }
        return shopLink;
    }

    /**
     * 搭配购商品 得到商品链接 isPic=true返回图片（用于分享） =false只返回链接（用于联系客服发送宝贝链接）
     */
    public static final HashMap<String, String> createMatchShopLinkOrPic(Context context, String result)
            throws JSONException {

        HashMap<String, String> resultInfo = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        if (j.getInt("status") == 1 || j.getInt("status") == 1050) {

            if (!j.has("status") || null == j.getString("status")) {
                resultInfo.put("status", "");
            } else {

                resultInfo.put("status", j.optString("status"));
            }

            if (!j.has("link") || null == j.getString("link")) {
                resultInfo.put("link", "");
            } else {

                resultInfo.put("link", j.optString("link"));
            }

            if (!j.has("pic") || null == j.getString("pic")) {
                resultInfo.put("pic", "");
            } else {

                resultInfo.put("pic", j.optString("pic"));
            }

            // resultInfo.put("status", j.optString("status"));
            // resultInfo.put("link", j.optString("link"));
            // resultInfo.put("pic", j.optString("pic"));
        }
        return resultInfo;
    }


    /**
     * 得到商品链接----夺宝随机商品的链接
     */
    public static final HashMap<String, String> createSuiJiDuoBaoLink(Context context, String result)
            throws JSONException {
        HashMap<String, String> resultInfo = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        if (null == j || j.equals("")) {
            return null;
        }
        if (j.getInt("status") == 1) {


            if (!j.has("link") || null == j.getString("link")) {
                resultInfo.put("link", "www.baidu.com");
            } else {
                resultInfo.put("link", j.optString("link"));
            }


            if (!j.has("shop") || null == j.getString("shop") || "".equals(j.getString("shop"))) {
                resultInfo.put("shop_name", "iphone7");
            } else {
                String str = j.getString("shop");
                JSONObject j2 = new JSONObject(str);
                if (null == j2 || j2.equals("")) {
                    resultInfo.put("shop_name", "iphone7");
                } else {
                    if (!j2.has("shop_name") || null == j2.getString("shop_name")) {
                        resultInfo.put("shop_name", "iphone7");
                    } else {
                        resultInfo.put("shop_name", j2.optString("shop_name"));
                    }


                    if (!j2.has("shop_code") || null == j2.getString("shop_code")) {
                        resultInfo.put("shop_code", "12333");
                    } else {
                        resultInfo.put("shop_code", j2.optString("shop_code"));
                    }


                    if (!j2.has("def_pic") || null == j2.getString("def_pic")) {
                        resultInfo.put("def_pic", "https://www.measures.wang/small-iconImages/ad_pic/ic_launcher.png");
                    } else {
                        resultInfo.put("def_pic", j2.optString("def_pic"));
                    }
                }
            }

        }

        return resultInfo;

    }

    /**
     * 得到商品链接
     */
    public static final HashMap<String, String> createShareContent(Context context, String result)
            throws JSONException {
        HashMap<String, String> resultInfo = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        if (null == j || j.equals("")) {
            return null;
        }
        if (j.getInt("status") == 1) {

            if (!j.has("status") || null == j.getString("status")) {
                resultInfo.put("status", "-1000");
            } else {

                resultInfo.put("status", j.optString("status"));
            }

            if (!j.has("link") || null == j.getString("link")) {
                resultInfo.put("link", "");
            } else {

                resultInfo.put("link", j.optString("link"));
            }

            if (!j.has("duobaoTitle") || null == j.getString("duobaoTitle")) {
                resultInfo.put("duobaoTitle", "");
            } else {

                resultInfo.put("duobaoTitle", j.optString("duobaoTitle"));
            }
            if (!j.has("duobaoTxt") || null == j.getString("duobaoTxt")) {
                resultInfo.put("duobaoTxt", "");
            } else {

                resultInfo.put("duobaoTxt", j.optString("duobaoTxt"));
            }


            if (!j.has("QrLink") || null == j.getString("QrLink")) {
                resultInfo.put("QrLink", "");
            } else {

                resultInfo.put("QrLink", j.optString("QrLink"));
            }

            // resultInfo.put("status", j.optString("status"));
            // resultInfo.put("link", j.optString("link"));
            // resultInfo.put("QrLink", j.optString("QrLink"));
            // resultInfo.put("status", j.optString("status"));

            if (j.getString("shop") != null) {

                JSONObject object = new JSONObject(j.getString("shop"));
                if (null == object || object.equals("")) {
                    return resultInfo;
                }

                if (!object.has("content") || null == object.getString("content")) {
                    resultInfo.put("content", "");
                } else {

                    resultInfo.put("content", object.getString("content"));
                }
                if (!object.has("four_pic") || null == object.getString("four_pic")) {
                    resultInfo.put("four_pic", "");
                } else {

                    resultInfo.put("four_pic", object.getString("four_pic"));
                }

                if (!object.has("shop_code") || null == object.getString("shop_code")) {
                    resultInfo.put("shop_code", "");
                } else {

                    resultInfo.put("shop_code", object.getString("shop_code"));
                }


                if (!object.has("assmble_price") || null == object.getString("assmble_price")) {
                    resultInfo.put("app_shop_group_price", "0.0");
                } else {

                    resultInfo.put("app_shop_group_price", object.getString("assmble_price"));
                }


                if (!object.has("shop_name") || null == object.getString("shop_name")) {
                    resultInfo.put("shop_name", "");
                } else {

                    resultInfo.put("shop_name", object.getString("shop_name"));
                }

                if (!object.has("shop_pic") || null == object.getString("shop_pic")) {
                    resultInfo.put("shop_pic", "");
                } else {

                    resultInfo.put("shop_pic", object.getString("shop_pic"));
                }

                if (!object.has("def_pic") || null == object.getString("def_pic")) {
                    resultInfo.put("def_pic", "");
                } else {

                    resultInfo.put("def_pic", object.getString("def_pic"));
                }

                if (!object.has("shop_se_price") || null == object.getString("shop_se_price")) {
                    resultInfo.put("shop_se_price", "0");
                } else {

                    resultInfo.put("shop_se_price", object.getString("shop_se_price"));
                }


                if (!object.has("supp_label") || null == object.getString("supp_label") || "null".equals(object.getString("supp_label"))) {
                    resultInfo.put("supp_label", "");
                } else {

                    resultInfo.put("supp_label", object.getString("supp_label"));
                }

                // resultInfo.put("content", object.getString("content"));
                // resultInfo.put("four_pic", object.getString("four_pic"));
                // resultInfo.put("shop_code", object.getString("shop_code"));
                // resultInfo.put("shop_name", object.getString("shop_name"));

                // resultInfo.put("qr_pic", object.getString("qr_pic"));
                // resultInfo.put("shop_pic", object.getString("shop_pic"));
                // resultInfo.put("def_pic", object.getString("def_pic"));
                // resultInfo.put("shop_se_price",
                // object.getString("shop_se_price"));
            }

        } else if (j.getInt("status") == 1050) {

            if (!j.has("status") || null == j.getString("status")) {
                resultInfo.put("status", "-1000");
            } else {

                resultInfo.put("status", j.optString("status"));
            }

            // resultInfo.put("status", j.optString("status"));
        }

        LogYiFu.e("日日日", resultInfo + "");
        return resultInfo;
    }

    /**
     * 签到分享特价商品
     */
    public static final HashMap<String, String> createSignShareZero(Context context, String result)
            throws JSONException {
        HashMap<String, String> resultInfo = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        if (null == j || j.equals("")) {
            return null;
        }
        if ("1".equals(j.getString("status"))) {
            resultInfo.put("status", j.optString("status"));

            if (!j.has("link") || null == j.getString("link")) {
                resultInfo.put("link", "");
            } else {

                resultInfo.put("link", j.optString("link"));
            }

            if (!j.has("message") || null == j.getString("message")) {
                resultInfo.put("message", "");
            } else {

                resultInfo.put("message", j.getString("message"));
            }

            if (!j.has("price") || null == j.getString("price")) {
                resultInfo.put("price", "");
            } else {

                resultInfo.put("price", j.getString("price"));
            }

            // resultInfo.put("link", j.optString("link"));
            // resultInfo.put("message", j.getString("message"));
            // resultInfo.put("price", j.getString("price"));
            if (j.getString("Pshop") != null) {

                JSONObject object = new JSONObject(j.getString("Pshop"));
                if (null == object || object.equals("")) {
                    return resultInfo;
                }

                JSONArray ja = new JSONArray(object.getString("shop_list"));
                JSONObject shopListArray = (JSONObject) ja.opt(0);
                if (null == shopListArray || shopListArray.equals("")) {
                    return resultInfo;
                }

                if (!j.has("shop_code") || null == j.getString("shop_code")) {
                    resultInfo.put("shop_code", "");
                } else {

                    resultInfo.put("shop_code", shopListArray.getString("shop_code"));
                }

                if (!j.has("four_pic") || null == j.getString("four_pic")) {
                    resultInfo.put("four_pic", "");
                } else {

                    resultInfo.put("four_pic", shopListArray.getString("four_pic"));
                }

                // resultInfo.put("shop_code",
                // shopListArray.getString("shop_code"));
                // resultInfo.put("four_pic",
                // shopListArray.getString("four_pic"));

                // resultInfo.put("qr_pic", object.getString("qr_pic"));
            }

        } else if (j.getInt("status") == 1050) {

            if (!j.has("status") || null == j.getString("status")) {
                resultInfo.put("status", "-1000");
            } else {

                resultInfo.put("status", j.optString("status"));
            }

            // resultInfo.put("status", j.optString("status"));
        }

        return resultInfo;
    }

    /**
     * 得到套餐商品链接
     */
    public static final HashMap<String, Object> createpShareContent(Context context, String result)
            throws JSONException {
        HashMap<String, Object> resultInfo = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (null == j || j.equals("")) {
            return null;
        }
        String four_pic = null;
        if (j.getInt("status") == 1) {
            resultInfo.put("status", j.optString("status"));
            resultInfo.put("link", j.optString("link"));
            resultInfo.put("shop_se_price", j.optString("price"));

            if (j.getString("Pshop") != null) {

                JSONArray jArray = new JSONArray(j.getString("Pshop"));
                if (null == jArray || jArray.equals("")) {
                    return resultInfo;
                }

                StringBuffer sb = new StringBuffer();

                List<String> shopCodes = new ArrayList<String>();
                List<HashMap<String, String>> pics = new ArrayList<HashMap<String, String>>();
                JSONObject jo2 = (JSONObject) jArray.opt(0);
                four_pic = jo2.optString("shop_code").substring(1, 4) + "/" + jo2.optString("shop_code") + "/"
                        + jo2.optString("four_pic");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jo = (JSONObject) jArray.opt(i);

                    if (!jo.has("shop_code") || null == jo.getString("shop_code")) {
                        shopCodes.add("");
                    } else {

                        shopCodes.add(jo.optString("shop_code"));
                    }

                    // shopCodes.add(jo.optString("shop_code"));

                    HashMap<String, String> hashMap = new HashMap<String, String>();

                    if (!jo.has("shop_code") || null == jo.getString("shop_code")) {
                        hashMap.put(jo.optString("shop_code"), "");
                    } else {

                        hashMap.put(jo.optString("shop_code"), jo.optString("shop_pic"));
                    }

                    // hashMap.put(jo.optString("shop_code"),
                    // jo.optString("shop_pic"));
                    pics.add(hashMap);

                    if (!jo.has("shop_pic") || null == jo.getString("shop_pic")) {
                        sb.append("");
                    } else {

                        sb.append(jo.optString("shop_pic"));
                    }

                    // sb.append(jo.optString("shop_pic"));
                    if (i != jArray.length() - 1) {
                        sb.append(",");
                    }
                }
                resultInfo.put("shopCodes", shopCodes);
                resultInfo.put("pics", pics);
                resultInfo.put("four_pic", four_pic);

                resultInfo.put("shop_pic", sb.toString());
            }

        } else if (j.getInt("status") == 1050) {
            resultInfo.put("status", j.optString("status"));
        }
        return resultInfo;
    }

    /****
     * 获取最优优惠卡
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final HashMap<String, Object> createProxCoupon(Context context, String result) throws JSONException {
        HashMap<String, Object> retInfo = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);

        if (!j.has("status") || null == j.getString("status")) {
            retInfo.put("status", "-1000");
        } else {

            retInfo.put("status", j.getString("status"));
        }

        if (!j.has("message") || null == j.getString("message")) {
            retInfo.put("message", "-1000");
        } else {

            retInfo.put("message", j.getString("message"));
        }

        // retInfo.put("status", j.getString("status"));
        // retInfo.put("message", j.getString("message"));
        if (1 == j.getInt("status")) {
            retInfo = JSON.parseObject(result, new TypeReference<HashMap<String, Object>>() {
            });
        }
        return retInfo;
    }

    /****
     * 查询积分记录
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final List<HashMap<String, Object>> createIntergralDetail(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray ja = new JSONArray(j.getString("data"));
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = (JSONObject) ja.opt(i);

            HashMap<String, Object> map = new HashMap<String, Object>();

            if (!jo.has("remark") || null == jo.getString("remark")) {
                map.put("remark", "");
            } else {

                map.put("remark", jo.has("remark") ? jo.optString("remark") : "");
            }

            if (!jo.has("id") || null == jo.getString("id") || jo.getString("id").equals("null")) {
                map.put("id", 0);
            } else {

                map.put("id", jo.has("id") ? jo.optInt("id") : 0);
            }

            if (!jo.has("user_id") || null == jo.getString("user_id") || jo.getString("user_id").equals("null")) {
                map.put("user_id", 0);
            } else {

                map.put("user_id", jo.has("user_id") ? jo.optInt("user_id") : 0);
            }

            if (!jo.has("num") || null == jo.getString("num") || jo.getString("num").equals("null")) {
                map.put("num", 0);
            } else {

                map.put("num", jo.has("num") ? jo.optInt("num") : 0);
            }

            if (!jo.has("type") || null == jo.getString("type") || jo.getString("type").equals("null")) {
                map.put("type", 0);
            } else {

                map.put("type", jo.has("type") ? jo.optInt("type") : 0);
            }

            if (!jo.has("add_time") || null == jo.getString("add_time") || jo.getString("add_time").equals("null")) {
                map.put("add_time", "");
            } else {

                map.put("add_time", jo.has("add_time") ? jo.optString("add_time") : "");
            }

            // map.put("remark", jo.has("remark") ? jo.optString("remark") :
            // "");
            // map.put("id", jo.has("id") ? jo.optInt("id") : 0);
            // map.put("user_id", jo.has("user_id") ? jo.optInt("user_id") : 0);
            // map.put("num", jo.has("num") ? jo.optInt("num") : 0);
            // map.put("type", jo.has("type") ? jo.optInt("type") : 0);
            // map.put("add_time", jo.has("add_time") ? jo.optString("add_time")
            // : "");
            lists.add(map);
        }
        // LogYiFu.e("TAG", lists.toString());
        return lists;
    }

    /****
     * 查询衣豆明细记录
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final List<HashMap<String, Object>> createPearsDetail(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray ja = new JSONArray(j.getString("data"));
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = (JSONObject) ja.opt(i);

            HashMap<String, Object> map = new HashMap<String, Object>();

            if (!jo.has("p_name") || null == jo.getString("p_name")) {
                map.put("p_name", "");
            } else {

                map.put("p_name", jo.has("p_name") ? jo.optString("p_name") : "");
            }

            if (!jo.has("num") || null == jo.getString("num") || jo.getString("num").equals("null")) {
                map.put("num", 0);
            } else {

                map.put("num", jo.has("num") ? jo.optInt("num") : 0);
            }

            if (!jo.has("type") || null == jo.getString("type") || jo.getString("type").equals("null")) {
                map.put("type", 0);
            } else {

                map.put("type", jo.has("type") ? jo.optInt("type") : 0);
            }

            if (!jo.has("add_date") || null == jo.getString("add_date") || jo.getString("add_date").equals("null")) {
                map.put("add_date", "0");
            } else {

                map.put("add_date", jo.has("add_date") ? jo.optString("add_date") : "0");
            }
            if (!jo.has("freeze") || null == jo.getString("freeze") || jo.getString("freeze").equals("null")) {
                map.put("freeze", "0");
            } else {

                map.put("freeze", jo.has("freeze") ? jo.optString("freeze") : "0");
            }
            lists.add(map);
        }
        return lists;
    }

    /****
     * 查询我的积分
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final Integer createMyIntergral(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }

        if (j.getInt("status") != 1) {
            return null;
        }

        if (!j.has("integral") || null == j.getString("integral") || j.getString("integral").equals("null")) {
            return -1000;
        } else {

            return j.getInt("integral");
        }

        // return j.getInt("integral");
    }

    /****
     * 获取阿里参数
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final HashMap<String, String> createAliParam(Context context, String result) throws JSONException {
        HashMap<String, String> retInfo = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        retInfo.put("status", j.getString("status"));
        retInfo.put("message", j.getString("message"));
        if (1 == j.getInt("status")) {

            if (!j.has("private_key") || null == j.getString("private_key")
                    || j.getString("private_key").equals("null")) {
                retInfo.put("private_key", "");
            } else {

                retInfo.put("private_key", j.getString("private_key"));
            }

            if (!j.has("partner") || null == j.getString("partner") || j.getString("partner").equals("null")) {
                retInfo.put("partner", "");
            } else {

                retInfo.put("partner", j.getString("partner"));
            }

            if (!j.has("ali_public_key") || null == j.getString("ali_public_key")
                    || j.getString("ali_public_key").equals("null")) {
                retInfo.put("ali_public_key", "");
            } else {

                retInfo.put("ali_public_key", j.getString("ali_public_key"));
            }

            if (!j.has("sign_type") || null == j.getString("sign_type") || j.getString("sign_type").equals("null")) {
                retInfo.put("sign_type", "");
            } else {

                retInfo.put("sign_type", j.getString("sign_type"));
            }

            if (!j.has("seller") || null == j.getString("seller") || j.getString("seller").equals("null")) {
                retInfo.put("seller", "");
            } else {

                retInfo.put("seller", j.getString("seller"));
            }

            if (!j.has("pay_url") || null == j.getString("pay_url") || j.getString("pay_url").equals("null")) {
                retInfo.put("pay_url", "");
            } else {

                retInfo.put("pay_url", j.optString("pay_url"));
            }

            if (!j.has("price") || null == j.getString("price") || j.getString("price").equals("null")) {
                retInfo.put("price", "0");
            } else {

                retInfo.put("price", j.optString("price"));
            }

            // retInfo.put("private_key", j.getString("private_key"));
            // retInfo.put("partner", j.getString("partner"));
            // retInfo.put("ali_public_key", j.getString("ali_public_key"));
            // retInfo.put("sign_type", j.getString("sign_type"));
            // retInfo.put("seller", j.getString("seller"));
            // retInfo.put("pay_url", j.optString("pay_url"));
            // retInfo.put("price", j.optString("price"));
        }
        return retInfo;
    }

    /****
     * 获取微信支付参数
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final HashMap<String, String> createWXParam(Context context, String result) throws JSONException {
        HashMap<String, String> retInfo = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        retInfo.put("status", j.getString("status"));
        retInfo.put("message", j.getString("message"));
        if (1 == j.getInt("status")) {

            if (!j.has("key") || null == j.getString("key")) {
                retInfo.put("key", YUrl.API_KEY);
            } else {

                retInfo.put("key", j.getString("key"));
            }

            if (!j.has("appID") || null == j.getString("appID")) {
                retInfo.put("appID", YUrl.APP_ID);
            } else {

                retInfo.put("appID", j.getString("appID"));
            }

            if (!j.has("AppSecret") || null == j.getString("AppSecret")) {
                retInfo.put("AppSecret", YUrl.APP_SECRET);
            } else {

                retInfo.put("AppSecret", j.optString("AppSecret"));
            }

            if (!j.has("mchID") || null == j.getString("mchID")) {
                retInfo.put("mchID", YUrl.MCH_ID);
            } else {

                retInfo.put("mchID", j.optString("mchID"));
            }

            // retInfo.put("key", j.getString("key"));
            // retInfo.put("appID", j.getString("appID"));
            // retInfo.put("AppSecret", j.optString("AppSecret"));
            // retInfo.put("mchID", j.optString("mchID"));
        }
        return retInfo;
    }

    /****
     * 获取阿里参数
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final HashMap<String, String> createInviteCode(Context context, String result) throws JSONException {
        HashMap<String, String> retInfo = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        retInfo.put("status", j.getString("status"));
        retInfo.put("message", j.getString("message"));
        if (1 == j.getInt("status")) {

            if (!j.has("link") || null == j.getString("link")) {
                retInfo.put("link", "");
            } else {

                retInfo.put("link", j.getString("link"));
            }

            if (!j.has("inviteCode") || null == j.getString("inviteCode")) {
                retInfo.put("inviteCode", "");
            } else {

                retInfo.put("inviteCode", j.getString("inviteCode"));
            }

            if (!j.has("content") || null == j.getString("content")) {
                retInfo.put("content", "");
            } else {

                retInfo.put("content", j.getString("content"));
            }

            if (!j.has("useCode") || null == j.getString("useCode")) {
                retInfo.put("useCode", "");
            } else {

                retInfo.put("useCode", j.getString("useCode"));
            }

            // retInfo.put("link", j.getString("link"));
            // retInfo.put("inviteCode", j.getString("inviteCode"));
            // retInfo.put("content", j.getString("content"));
            // retInfo.put("useCode", j.getString("useCode"));
        }
        return retInfo;
    }

    /**
     * 分享邀请码之后调用此接口.以便记录数据,后期好统计
     */
    public static final HashMap<String, String> createShareNumber(Context context, String result) throws JSONException {
        HashMap<String, String> retInfo = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        retInfo.put("status", j.getString("status"));
        retInfo.put("message", j.getString("message"));
        // if (1 == j.getInt("status")) {
        //
        // }
        return retInfo;
    }

    /**
     * 3293 【AND】 运营数据统计
     */
    public static final HashMap<String, String> createYunYingTongJi(Context context, String result)
            throws JSONException {
        HashMap<String, String> retInfo = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        retInfo.put("status", j.getString("status"));
        retInfo.put("message", j.getString("message"));
        // if (1 == j.getInt("status")) {
        //
        // }
        return retInfo;
    }

    /****
     * 商家联盟收益统计
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    //
    // public static final HashMap<String, Object>
    // createRevenueStatistics(Context context, String result)
    // throws JSONException {
    //
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    //
    // HashMap<String, Object> map = new HashMap<String, Object>();
    // map.put("monthMoney", j.has("monthMoney") ? j.optDouble("monthMoney") :
    // "");
    // map.put("todayMoney", j.has("todayMoney") ? j.optDouble("todayMoney") :
    // 0);
    // map.put("weekMoney", j.has("weekMoney") ? j.optDouble("weekMoney") : 0);
    // map.put("status", j.has("status") ? j.optString("status") : "");
    // map.put("message", j.has("message") ? j.optString("message") : "");
    //
    // LogYiFu.e("收益统计", map.toString());
    // return map;
    // }

    /**
     * 超级合伙人会员
     */
    // public static final List<HashMap<String, Object>>
    // createAllianceMember(Context context, String result)
    // throws JSONException {
    // List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String,
    // Object>>();
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    // JSONArray jsonArray = new JSONArray(j.getString("list"));
    // if (null == jsonArray || "".equals(jsonArray)) {
    // return null;
    // }
    // for (int i = 0; i < jsonArray.length(); i++) {
    // JSONObject jo = (JSONObject) jsonArray.opt(i);
    // HashMap<String, Object> mapObject = new HashMap<String, Object>();
    // mapObject.put("start_time", jo.has("start_time") ?
    // jo.getString("start_time") : "");
    // mapObject.put("pic", jo.has("pic") ? jo.getString("pic") : "");
    // mapObject.put("nickname", jo.has("nickname") ? jo.getString("nickname") :
    // "");
    // mapObject.put("user_id", jo.has("user_id") ? jo.getString("user_id") :
    // "");
    // mapObject.put("phone", jo.has("phone") ? jo.getString("phone") : "");
    // mapObject.put("city", jo.has("city") ? jo.getString("city") : "");
    // mapObject.put("province", jo.has("province") ? jo.getString("province") :
    // "");
    // mapObject.put("plaintext", jo.has("plaintext") ?
    // jo.getString("plaintext") : "");
    // mapObject.put("buyer_id", jo.has("buyer_id") ? jo.getString("buyer_id") :
    // "");
    // mapObject.put("card_no", jo.has("card_no") ? jo.getString("card_no") :
    // "");
    // mapObject.put("count", jo.has("count") ? jo.getString("count") : "0");
    // retInfo.add(mapObject);
    //
    // }

    // return retInfo;
    // }

    /**
     * 超级合伙人会员下级会员
     */
    // public static final List<HashMap<String, Object>>
    // createAllianceH5Member(Context context, String result)
    // throws JSONException {
    //
    // List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String,
    // Object>>();
    //
    // JSONObject j = new JSONObject(result);
    // if (null == j || "".equals(j)) {
    // return null;
    // }
    // JSONArray jsonArray = new JSONArray(j.getString("list"));
    // if (null == jsonArray || "".equals(jsonArray)) {
    // return null;
    // }
    // for (int i = 0; i < jsonArray.length(); i++) {
    // JSONObject jo = (JSONObject) jsonArray.opt(i);
    // HashMap<String, Object> mapObject = new HashMap<String, Object>();
    // mapObject.put("time", jo.has("time") ? jo.getString("time") : "");
    // mapObject.put("pic", jo.has("pic") ? jo.getString("pic") : "");
    // mapObject.put("nickname", jo.has("nickname") ? jo.getString("nickname") :
    // "");
    // mapObject.put("user_id", jo.has("user_id") ? jo.getString("user_id") :
    // "");
    // mapObject.put("phone", jo.has("phone") ? jo.getString("phone") : "");
    // mapObject.put("city", jo.has("city") ? jo.getString("city") : "");
    // mapObject.put("province", jo.has("province") ? jo.getString("province") :
    // "");
    // mapObject.put("sumMoney", jo.has("sumMoney") ? jo.getString("sumMoney") :
    // "");
    // retInfo.add(mapObject);
    //
    // }

    // return retInfo;
    // }

    /**
     * 联盟商家主页信息获取
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    // public static HashMap<String, String> leagueHome(Context context, String
    // result) throws JSONException {
    // HashMap<String, String> retInfo = new HashMap<String, String>();
    // JSONObject j = new JSONObject(result);
    // retInfo.put("status", j.optString("status"));
    // retInfo.put("message", j.optString("message"));
    // if (1 == j.optInt("status")) {
    // retInfo.put("two_balance", j.optString("two_balance"));
    // retInfo.put("user_add_date", j.optString("user_add_date"));
    // retInfo.put("user_pic", j.getString("user_pic"));
    // retInfo.put("user_name", j.getString("user_name"));
    // retInfo.put("orderCount", j.getString("orderCount"));
    // retInfo.put("two_freeze_balance", j.getString("two_freeze_balance"));
    // retInfo.put("juniorUserCount", j.getString("juniorUserCount"));
    // retInfo.put("depositMoneySuccessSum",
    // j.getString("depositMoneySuccessSum"));
    // }
    // return retInfo;
    // }

    /**
     * 联盟商家提现记录获取
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    // public static HashMap<String, Object> leagueLogDetail(Context context,
    // String result) throws JSONException {
    // HashMap<String, Object> retInfo = new HashMap<String, Object>();
    // JSONObject j = new JSONObject(result);
    // retInfo.put("status", j.optString("status"));
    // retInfo.put("message", j.optString("message"));
    // if (1 == j.optInt("status")) {
    // retInfo.put("depositMoneySuccessSum",
    // j.optString("depositMoneySuccessSum"));
    // JSONArray ja = j.optJSONArray("data");
    // if (ja != null && ja.length() > 0) {
    // List<HashMap<String, String>> list = new ArrayList<HashMap<String,
    // String>>();
    // for (int i = 0; i < ja.length(); i++) {
    // JSONObject js = ja.optJSONObject(i);
    // HashMap<String, String> map = new HashMap<String, String>();
    // map.put("collect_bank_code", js.optString("collect_bank_code"));
    // map.put("collect_bank_name", js.optString("collect_bank_name"));
    // map.put("money", js.optString("money"));
    // map.put("add_date", js.optString("add_date"));
    // map.put("check", js.optString("check"));
    // list.add(map);
    // }
    // retInfo.put("data", list);
    // }
    // }
    // return retInfo;
    // }

    /**
     * 联盟商家佣金记录获取
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    // public static HashMap<String, Object> leagueYJLog(Context context, String
    // result) throws JSONException {
    // HashMap<String, Object> retInfo = new HashMap<String, Object>();
    // JSONObject j = new JSONObject(result);
    // retInfo.put("status", j.optString("status"));
    // retInfo.put("message", j.optString("message"));
    // if (1 == j.optInt("status")) {
    // retInfo.put("pageCount", j.optString("pageCount"));
    // JSONArray ja = j.optJSONArray("data");
    // if (ja != null && ja.length() > 0) {
    // List<HashMap<String, String>> list = new ArrayList<HashMap<String,
    // String>>();
    // for (int i = 0; i < ja.length(); i++) {
    // JSONObject js = ja.optJSONObject(i);
    // HashMap<String, String> map = new HashMap<String, String>();
    // map.put("NICKNAME", js.optString("user_name"));
    // map.put("money", js.optString("money"));
    // map.put("add_date", js.optString("add_date"));
    // map.put("is_free", js.optString("is_free"));
    // map.put("status", js.optString("status"));
    // list.add(map);
    // }
    // retInfo.put("data", list);
    // }
    // }
    // return retInfo;
    // }

    /**
     * 联盟商家提现记录获取
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    // public static HashMap<String, Object> leagueTXLog(Context context, String
    // result) throws JSONException {
    // HashMap<String, Object> retInfo = new HashMap<String, Object>();
    // JSONObject j = new JSONObject(result);
    // retInfo.put("status", j.optString("status"));
    // retInfo.put("message", j.optString("message"));
    // if (1 == j.optInt("status")) {
    // retInfo.put("pageCount", j.optString("pageCount"));
    // JSONArray ja = j.optJSONArray("data");
    // if (ja != null && ja.length() > 0) {
    // List<HashMap<String, String>> list = new ArrayList<HashMap<String,
    // String>>();
    // for (int i = 0; i < ja.length(); i++) {
    // JSONObject js = ja.optJSONObject(i);
    // HashMap<String, String> map = new HashMap<String, String>();
    // map.put("collect_bank_code", js.optString("collect_bank_code"));
    // map.put("collect_bank_name", js.optString("collect_bank_name"));
    // map.put("money", js.optString("money"));
    // map.put("check", js.optString("check"));
    // map.put("add_date", js.optString("add_date"));
    // list.add(map);
    // }
    // retInfo.put("data", list);
    // }
    //
    // }
    // return retInfo;
    // }

    /**
     * 获取供应商号码
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static String getSuppPhone(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);
        if (j.getInt("status") == 1) {

            if (!j.has("cart_count") || null == j.getString("cart_count") || j.getString("cart_count").equals("null")) {
                return j.optString("0");
            } else {

                return j.optString("phone");
            }

            // return j.optString("phone");
        }
        return null;
    }

    // 统计分享次数
    public static String createShareCount(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);
        if (j.getInt("status") == 1) {
            return j.optString("status");
        }
        return null;
    }


    public static MealShopList createNewMealShopList(String shopList) throws JSONException {
        return JSON.parseObject(shopList, MealShopList.class);

    }


    /**
     * 0元购套餐列表
     */
    public static final Map<String, List<HashMap<String, Object>>> createZeroShopProductList(Context context,
                                                                                             String result) throws JSONException {
        // System.out.println("套餐检查=" + result);
        Map<String, List<HashMap<String, Object>>> allMap = new HashMap<String, List<HashMap<String, Object>>>();
        List<HashMap<String, Object>> zeroShopList2 = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }

        JSONArray jsonArray2 = new JSONArray(j.has("pList") ? j.getString("pList") : "");
        for (int i = 0; i < jsonArray2.length(); i++) {
            JSONObject jo2 = (JSONObject) jsonArray2.opt(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();

            if (!jo2.has("p_status") || null == jo2.getString("p_status") || jo2.getString("p_status").equals("null")) {
                mapObject.put("p_status", 0); // 有数据了再看是不是在这个位置
            } else {

                mapObject.put("p_status", jo2.has("p_status") ? jo2.getInt("p_status") : "0"); // 有数据了再看是不是在这个位置
            }

            // mapObject.put("p_status", jo2.has("p_status") ?
            // jo2.getInt("p_status") : "0"); // 有数据了再看是不是在这个位置

            if (!jo2.has("code") || null == jo2.getString("code") || jo2.getString("code").equals("null")) {
                mapObject.put("code", "");
            } else {

                mapObject.put("code", jo2.has("code") ? jo2.getString("code") : "");
            }
            // mapObject.put("code", jo2.has("code") ? jo2.getString("code") :
            // "");

            if (!jo2.has("name") || null == jo2.getString("name") || jo2.getString("name").equals("null")) {
                mapObject.put("name", "");
            } else {

                mapObject.put("name", jo2.has("name") ? jo2.getString("name") : "");
            }
            // mapObject.put("name", jo2.has("name") ? jo2.getString("name") :
            // "");

            if (!jo2.has("postage") || null == jo2.getString("postage") || jo2.getString("postage").equals("null")) {
                mapObject.put("postage", "");
            } else {

                mapObject.put("postage", jo2.has("postage") ? jo2.getString("postage") : "");
            }
            // mapObject.put("postage", jo2.has("postage") ?
            // jo2.getString("postage") : "");

            if (!jo2.has("type") || null == jo2.getString("type") || jo2.getString("type").equals("null")) {
                mapObject.put("type", "");
            } else {

                mapObject.put("type", jo2.has("type") ? jo2.getString("type") : "");
            }
            // mapObject.put("type", jo2.has("type") ? jo2.getString("type") :
            // "");

            if (!jo2.has("price") || null == jo2.getString("price") || jo2.getString("price").equals("null")) {
                mapObject.put("price", "0");
            } else {

                mapObject.put("price", jo2.has("price") ? jo2.getString("price") : "");
            }

            // mapObject.put("price", jo2.has("price") ? jo2.getString("price")
            // : "");

            if (!jo2.has("content") || null == jo2.getString("content")) {
                mapObject.put("content", "");
            } else {

                mapObject.put("content", jo2.has("content") ? jo2.getString("content") : "");
            }

            // mapObject.put("content", jo2.has("content") ?
            // jo2.getString("content") : "");

            if (!jo2.has("seq") || null == jo2.getString("seq")) {
                mapObject.put("seq", "");
            } else {

                mapObject.put("seq", jo2.has("seq") ? jo2.getString("seq") : "");
            }

            // mapObject.put("seq", jo2.has("seq") ? jo2.getString("seq") : "");
            if (!jo2.has("add_date") || null == jo2.getString("add_date")) {
                mapObject.put("add_date", "0");
            } else {

                mapObject.put("add_date", jo2.has("add_date") ? jo2.optString("add_date", "0") : "0");
            }
            // mapObject.put("add_date", jo2.has("add_date") ?
            // jo2.optString("add_date","0") :"0");

            if (!jo2.has("virtual_sales") || null == jo2.getString("virtual_sales")
                    || jo2.getString("virtual_sales").equals("null")) {
                mapObject.put("virtual_sales", "0");
            } else {

                mapObject.put("virtual_sales", jo2.has("virtual_sales") ? jo2.getString("virtual_sales") : "0");
            }

            // mapObject.put("virtual_sales", jo2.has("virtual_sales") ?
            // jo2.getString("virtual_sales") : "0");
            /*
             * mapObject.put("p_type", jo2.has("p_type") ?
             * jo2.getString("p_type") : "999");
             */
            mapObject.put("num", jo2.has("num") ? jo2.getString("num") : "0");
            mapObject.put("r_num", jo2.has("r_num") ? jo2.getString("r_num") : "0");
            JSONArray jsonArray3 = new JSONArray(jo2.has("shop_list") ? jo2.getString("shop_list") : "");
            List<HashMap<String, Object>> zeroShopList1 = new ArrayList<HashMap<String, Object>>();
            for (int p = 0; p < jsonArray3.length(); p++) {
                JSONObject jo = (JSONObject) jsonArray3.opt(p);
                HashMap<String, Object> map = new HashMap<String, Object>();

                if (!jo.has("p_code") || null == jo.getString("p_code") || jo.getString("p_code").equals("null")) {
                    mapObject.put("p_code", "0");
                } else {

                    map.put("p_code", jo.has("p_code") ? jo.getString("p_code") : "");
                }

                // map.put("p_code", jo.has("p_code") ? jo.getString("p_code") :
                // "");
                /*
                 * // TODO: map.put("p_type", jo.has("p_type") ?
                 * jo.getString("p_type") : "");
                 */

                if (!jo.has("shop_name") || null == jo.getString("shop_name")
                        || jo.getString("shop_name").equals("null")) {
                    mapObject.put("shop_name", "");
                } else {

                    map.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");
                }
                // map.put("shop_name", jo.has("shop_name") ?
                // jo.getString("shop_name") : "");

                if (!jo.has("invertory_num") || null == jo.getString("invertory_num")
                        || jo.getString("invertory_num").equals("null")) {
                    mapObject.put("invertory_num", "");
                } else {

                    map.put("invertory_num", jo.has("invertory_num") ? jo.getString("invertory_num") : "");
                }

                // map.put("invertory_num", jo.has("invertory_num") ?
                // jo.getString("invertory_num") : "");
                // map.put("virtual_sales", jo.has("virtual_sales") ?
                // jo.getString("virtual_sales") : "");

                if (!jo.has("four_pic") || null == jo.getString("four_pic")
                        || jo.getString("four_pic").equals("null")) {
                    mapObject.put("four_pic", "");
                } else {

                    map.put("four_pic", jo.has("four_pic") ? jo.getString("four_pic") : "");
                }
                // map.put("four_pic", jo.has("four_pic") ?
                // jo.getString("four_pic") : "");

                if (!jo.has("shop_price") || null == jo.getString("shop_price")
                        || jo.getString("shop_price").equals("null")) {
                    mapObject.put("shop_price", "0");
                } else {

                    map.put("shop_price", jo.has("shop_price") ? jo.getString("shop_price") : "");
                }

                // map.put("shop_price", jo.has("shop_price") ?
                // jo.getString("shop_price") : "");

                if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price")
                        || jo.getString("shop_se_price").equals("null")) {
                    mapObject.put("shop_se_price", "0");
                } else {

                    map.put("shop_se_price", jo.has("shop_se_price") ? jo.getString("shop_se_price") : "");
                }
                // map.put("shop_se_price", jo.has("shop_se_price") ?
                // jo.getString("shop_se_price") : "");

                // map.put("content",
                // jo.has("content") ? jo.getString("content")
                // : "");

                if (!jo.has("shop_code") || null == jo.getString("shop_code")
                        || jo.getString("shop_code").equals("null")) {
                    mapObject.put("shop_code", "");
                } else {

                    map.put("shop_code", jo.optString("shop_code", ""));
                }
                // map.put("shop_code", jo.optString("shop_code", ""));
                zeroShopList1.add(map);
            }

            mapObject.put("shop_list", zeroShopList1);
            zeroShopList2.add(mapObject);
        }
        allMap.put("pList", zeroShopList2);
        if (zeroShopList2.isEmpty()) {
            return null;
        }

        return allMap;
    }

    /**
     * 购物车统计
     */
    public static String createShopCartCount(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);
        if (j.getInt("status") == 1) {

            if (!j.has("cart_count") || null == j.getString("cart_count") || j.getString("cart_count").equals("null")) {
                return j.optString("0");
            } else {

                return j.optString("cart_count");
            }

            // return j.optString("cart_count");
        }
        return "0";
    }

    /**
     * 搭配购 购物车数量统计 和获取倒计时
     */
    public static HashMap<String, Object> createMatchShopCartCount(Context context, String result)
            throws JSONException {
        JSONObject j = new JSONObject(result);
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        if (j.getInt("status") == 1) {

            if (!j.has("cart_count") || null == j.getString("cart_count") || j.getString("cart_count").equals("null")) {
                hashMap.put("cart_count", "0");
            } else {

                hashMap.put("cart_count", j.optString("cart_count"));
            }

            if (!j.has("s_time") || null == j.getString("s_time") || j.getString("s_time").equals("null")) {
                hashMap.put("s_time", 0L);
            } else {

                hashMap.put("s_time", j.optLong("s_time", 0L));
            }

            if (!j.has("s_deadline") || null == j.getString("s_deadline") || j.getString("s_deadline").equals("null")) {
                hashMap.put("s_deadline", 0L);
            } else {

                hashMap.put("s_deadline", j.optLong("s_deadline", 0L));
            }

            // hashMap.put("cart_count", j.optString("cart_count"));
            // hashMap.put("s_time", j.optLong("s_time", 0));
            // hashMap.put("s_deadline", j.optLong("s_deadline", 0));
        }
        return hashMap;
    }

    public static final HashMap<String, String> createThTkInfo(Context context, String result) throws JSONException {
        HashMap<String, String> retInfo = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        retInfo.put("status", j.getString("status"));
        retInfo.put("message", j.getString("message"));
        if (j.optInt("status") == 1) {

            if (!j.has("useCoupon") || null == j.getString("useCoupon") || j.getString("useCoupon").equals("null")) {
                retInfo.put("useCoupon", "0");// 优惠券抵用的钱
            } else {

                retInfo.put("useCoupon", j.getString("useCoupon"));// 优惠券抵用的钱
            }

            if (!j.has("useInegral") || null == j.getString("useInegral") || j.getString("useInegral").equals("null")) {
                retInfo.put("useInegral", "0");// 优惠券抵用的钱
            } else {

                retInfo.put("useInegral", j.getString("useInegral"));// 优惠券抵用的钱
            }

            if (!j.has("money") || null == j.getString("money") || j.getString("money").equals("null")) {
                retInfo.put("money", "0");// 优惠券抵用的钱
            } else {

                retInfo.put("money", j.getString("money"));// 优惠券抵用的钱
            }
            // retInfo.put("useCoupon", j.getString("useCoupon"));// 优惠券抵用的钱
            // retInfo.put("useInegral", j.getString("useInegral"));// 积分抵用的钱
            // retInfo.put("money", j.getString("money"));// 可退金额
        }

        return retInfo;
    }

    public static final HashMap<String, Object> createReturnShopNew(Context context, String result)
            throws JSONException {
        HashMap<String, Object> retInfo = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        retInfo.put("status", j.getString("status"));
        retInfo.put("message", j.getString("message"));
        if (j.optInt("status") == 1) {
            ReturnShop rShop = JSON.parseObject(j.getString("returnShop"), ReturnShop.class);
            retInfo.put("returnShop", rShop);
        }

        return retInfo;
    }

    // 夺宝退款
    public static final HashMap<String, Object> createReturnDuobaoShopNew(Context context, String result)
            throws JSONException {
        HashMap<String, Object> retInfo = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        retInfo.put("status", j.getString("status"));
        retInfo.put("message", j.getString("message"));
        if (j.optInt("status") == 1) {
            ReturnShop rShop = JSON.parseObject(j.getString("returnShop"), ReturnShop.class);
            retInfo.put("returnShop", rShop);
        }

        return retInfo;
    }

    public static final List<ReturnShop> createReturnShopList(Context context, String result) throws JSONException {
        HashMap<String, Object> retInfo = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        retInfo.put("status", j.getString("status"));
        retInfo.put("message", j.getString("message"));
        if (j.optInt("status") == 1) {

            List<ReturnShop> listShop = JSON.parseArray(j.optString("data"), ReturnShop.class);
            return listShop;
        }

        return null;
    }

    /***
     * 0元购商品属性
     */
    public static final List<StockType> createShopsAttrs(Context context, String result) throws Exception {
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        List<StockType> list = null;
        if (j.optInt("status") == 1) {
            list = JSON.parseArray(j.getString("stocktype"), StockType.class);
        }
        return list;
    }

    /***
     * 0元购下单
     */
    public static final HashMap<String, Object> createZeroOrder(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!jsonObject.has("order_code") || null == jsonObject.getString("order_code")
                || jsonObject.getString("order_code").equals("null")) {
            mapRet.put("order_code", "");
        } else {

            mapRet.put("order_code", jsonObject.optString("order_code"));
        }

        if (!jsonObject.has("price") || null == jsonObject.getString("price")
                || jsonObject.getString("price").equals("null")) {
            mapRet.put("price", "0");
        } else {

            mapRet.put("price", jsonObject.optString("price"));
        }

        if (!jsonObject.has("orderToken") || null == jsonObject.getString("orderToken")
                || jsonObject.getString("orderToken").equals("null")) {
            YCache.saveOrderToken(context, "");
        } else {

            YCache.saveOrderToken(context, jsonObject.optString("orderToken"));
        }

        if (!jsonObject.has("url") || null == jsonObject.getString("url")
                || jsonObject.getString("url").equals("null")) {
            mapRet.put("url", 0);
        } else {

            mapRet.put("url", jsonObject.optInt("url"));
        }

        // mapRet.put("order_code", jsonObject.optString("order_code"));
        // mapRet.put("price", jsonObject.optString("price"));
        // YCache.saveOrderToken(context, jsonObject.optString("orderToken"));
        // mapRet.put("url", jsonObject.optInt("url"));
        mapRet.put("status", jsonObject.optString("status"));
        mapRet.put("message", jsonObject.optString("message"));
        return mapRet;
    }

    /***
     * 夺宝下单
     */
    public static final HashMap<String, Object> createDuobaoOrder(Context context, String result) throws Exception {

        // MyLogYiFu.e("是是是", result);
        // HashMap<String, Object> mapRet = new HashMap<String, Object>();
        // JSONObject jsonObject = new JSONObject(result);
        // if (null == jsonObject || "".equals(jsonObject)) {
        // return null;
        // }
        //
        // mapRet.put("status",jsonObject.has("status")?
        // jsonObject.getString("status"):"");
        //
        // JSONArray array = jsonObject.optJSONArray("data");
        // for (int i = 0; i < array.length(); i++) {
        //
        // JSONObject js = array.optJSONObject(i);
        //
        // // mapObject.put("ISODate", jo.has("ISODate") ?
        // // jo.getString("ISODate") : "");
        //
        // mapRet.put("order_code", js.optString("order_code"));
        // mapRet.put("price", js.optString("price"));
        JSONObject j = new JSONObject(result);
        HashMap<String, Object> retInfo = new HashMap<String, Object>();

        if (null == j || "".equals(j)) {
            return null;
        }

        if (1 == j.getInt("status")) {
            JSONObject jj = j.optJSONObject("data");
            if (null == jj || "".equals(jj)) {
                return null;
            }

            if (!jj.has("price") || null == jj.getString("price") || jj.getString("price").equals("null")) {
                retInfo.put("price", "0");
            } else {

                retInfo.put("price", jj.has("price") ? jj.getString("price") : "");
            }

            if (!jj.has("order_code") || null == jj.getString("order_code")
                    || jj.getString("order_code").equals("null")) {
                retInfo.put("order_code", "");
            } else {

                retInfo.put("order_code", jj.has("order_code") ? jj.getString("order_code") : "");
            }

            // retInfo.put("price", jj.has("price") ? jj.getString("price") :
            // "");
            // retInfo.put("order_code", jj.has("order_code") ?
            // jj.getString("order_code") : "");

            return retInfo;
        } else {
            return null;
        }

        /**
         * JSONObject jsonObject = new JSONObject(result); if (null ==
         * jsonObject || "".equals(jsonObject)) { return null; } if (1 ==
         * jsonObject.getInt("status")) { JSONObject j =
         * jsonObject.optJSONObject("data"); if (null == j || "".equals(j)) {
         * return null; }
         *
         * mapRet.put("link", j.has("link") ? j.getString("link") : "");
         * mapRet.put("content", j.has("content") ? j.getString("content") :
         * ""); mapRet.put("name", j.has("name") ? j.getString("name") : "");
         *
         * return mapRet; } else { return null; }
         */

    }

    /***
     * 0元购下单(签到)
     */
    public static final HashMap<String, Object> createZeroOrderSign(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!jsonObject.has("order_code") || null == jsonObject.getString("order_code")
                || jsonObject.getString("order_code").equals("null")) {
            mapRet.put("order_code", "");
        } else {

            mapRet.put("order_code", jsonObject.optString("order_code"));
        }

        if (!jsonObject.has("price") || null == jsonObject.getString("price")
                || jsonObject.getString("price").equals("null")) {
            mapRet.put("price", "0");
        } else {

            mapRet.put("price", jsonObject.optString("price"));
        }
        if (!jsonObject.has("vipBuy") || null == jsonObject.getString("vipBuy")
                || jsonObject.getString("vipBuy").equals("null")) {
            mapRet.put("vipBuy", "0");
        } else {

            mapRet.put("vipBuy", jsonObject.optString("vipBuy"));
        }
        if (!jsonObject.has("orderToken") || null == jsonObject.getString("orderToken")
                || jsonObject.getString("orderToken").equals("null")) {
            YCache.saveOrderToken(context, "");
        } else {

            YCache.saveOrderToken(context, jsonObject.optString("orderToken"));
        }

        if (!jsonObject.has("url") || null == jsonObject.getString("url")
                || jsonObject.getString("url").equals("null")) {
            mapRet.put("url", 0);
        } else {

            mapRet.put("url", jsonObject.optInt("url"));
        }

        //开团团号
        if (!jsonObject.has("roll_code") || null == jsonObject.getString("roll_code")
                || jsonObject.getString("roll_code").equals("null")) {
            mapRet.put("roll_code", "");
        } else {

            mapRet.put("roll_code", jsonObject.optString("roll_code"));
        }


        // mapRet.put("order_code", jsonObject.optString("order_code"));
        // mapRet.put("price", jsonObject.optString("price"));
        // YCache.saveOrderToken(context, jsonObject.optString("orderToken"));
        // mapRet.put("url", jsonObject.optInt("url"));// (单个还是多个) 这里永远返回1
        // ,调用单个订单支付就行.
        mapRet.put("status", jsonObject.optString("status"));
        mapRet.put("message", jsonObject.optString("message"));
        return mapRet;
    }

    /***
     * 分享信息
     */
    public static final HashMap<String, Object> createP_ShopLink(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        LogYiFu.e("JSONObject", jsonObject.toString());
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        mapRet.put("status", jsonObject.optInt("status"));
        mapRet.put("message", jsonObject.optString("message"));

        if (!jsonObject.has("link") || null == jsonObject.getString("link")
                || jsonObject.getString("link").equals("null")) {
            mapRet.put("link", "");
        } else {

            mapRet.put("link", jsonObject.optString("link"));
        }

        // mapRet.put("link", jsonObject.optString("link"));

        if (!jsonObject.has("price") || null == jsonObject.getString("price")
                || jsonObject.getString("price").equals("null")) {
            mapRet.put("price", "");

        } else {

            mapRet.put("price", jsonObject.optString("price"));
        }

        // mapRet.put("price", jsonObject.optString("price"));
        if (!TextUtils.isEmpty(jsonObject.optString("Pshop"))) {
            JSONArray j = new JSONArray(jsonObject.optString("Pshop"));

            if (null == ((JSONObject) j.opt(0)).optString("four_pic")
                    || ((JSONObject) j.opt(0)).optString("four_pic").equals("null")) {
                mapRet.put("four_pic", "");
            } else {
                mapRet.put("four_pic", ((JSONObject) j.opt(0)).optString("four_pic"));
            }

            // mapRet.put("four_pic", ((JSONObject)
            // j.opt(0)).optString("four_pic"));

            if (null == ((JSONObject) j.opt(0)).optString("shop_code")
                    || ((JSONObject) j.opt(0)).optString("shop_code").equals("null")) {
                mapRet.put("shop_code", "");
            } else {
                mapRet.put("shop_code", ((JSONObject) j.opt(0)).optString("shop_code"));
            }

            // mapRet.put("shop_code", ((JSONObject)
            // j.opt(0)).optString("shop_code"));

            if (null == ((JSONObject) j.opt(0)).optString("shop_pic")
                    || ((JSONObject) j.opt(0)).optString("shop_pic").equals("null")) {
                mapRet.put("shop_pic", "");
            } else {
                mapRet.put("shop_pic", ((JSONObject) j.opt(0)).optString("shop_pic"));
            }

            // mapRet.put("shop_pic", ((JSONObject)
            // j.opt(0)).optString("shop_pic"));
        }
        /*
         * "Pshop": { "num": 300, "price": 0, "r_num": 300, "name": "套餐三",
         * "seq": 3, "def_pic": "shop/banner_16.jpg", "code": "AFI125", "type":
         * 1, "postage": 11 },
         */
        LogYiFu.e("mapRet", mapRet.toString());
        return mapRet;
    }

    /***
     * 下单之前
     */
    public static final HashMap<String, Object> preSubmitOrder(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (jsonObject.getInt("status") == 1) {
            mapRet.put("status", jsonObject.optInt("status"));
            mapRet.put("message", jsonObject.optString("message"));

            if (!jsonObject.has("integral") || null == jsonObject.getString("integral")
                    || jsonObject.getString("integral").equals("null")) {
                mapRet.put("integral", 0);
            } else {

                mapRet.put("integral", jsonObject.optInt("integral"));
            }

            // mapRet.put("integral", jsonObject.optInt("integral"));

            if (!jsonObject.has("isOneBuy") || null == jsonObject.getString("isOneBuy")
                    || jsonObject.getString("isOneBuy").equals("null")) {
                mapRet.put("isOneBuy", 0);
            } else {

                mapRet.put("isOneBuy", jsonObject.optInt("isOneBuy"));
            }

            // mapRet.put("isOneBuy", jsonObject.optInt("isOneBuy"));

            if (!jsonObject.has("needIntegral") || null == jsonObject.getString("needIntegral")
                    || jsonObject.getString("needIntegral").equals("null")) {
                mapRet.put("needIntegral", 0);
            } else {

                mapRet.put("needIntegral", jsonObject.optInt("needIntegral"));
            }

            // mapRet.put("needIntegral", jsonObject.optInt("needIntegral"));

        }

        return mapRet;
    }

    /***
     * 获取我的积分信息
     */
    public static final HashMap<String, Object> createMyIntegral(Context context, String result) throws Exception {
        HashMap<String, Object> parseObject = null;
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (jsonObject.getInt("status") == 1) {
            parseObject = JSON.parseObject(result, new TypeReference<HashMap<String, Object>>() {
            });

        }

        return parseObject;
    }

    /***
     * Help列表
     */
    public static final HashMap<String, Integer> createFinCount(Context context, String result) throws Exception {
        HashMap<String, Integer> mapRet = new HashMap<String, Integer>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!jsonObject.has("H5Count") || null == jsonObject.getString("H5Count")
                || jsonObject.getString("H5Count").equals("null")) {
            mapRet.put("H5Count", 0);
        } else {

            mapRet.put("H5Count", jsonObject.optInt("H5Count"));
        }

        // mapRet.put("H5Count", jsonObject.optInt("H5Count"));

        if (!jsonObject.has("finCount") || null == jsonObject.getString("finCount")
                || jsonObject.getString("finCount").equals("null")) {
            mapRet.put("finCount", 0);
        } else {

            mapRet.put("finCount", jsonObject.optInt("finCount"));
        }

        // mapRet.put("finCount", jsonObject.optInt("finCount"));
        return mapRet;

    }

    // ---------------乐山-------------------------------------------

    // ---------------守祥----------------------------------------------

    /***
     * 订单列表
     */
    public static final Order createOrder(Context context, String result) throws Exception {
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        Order order = JSON.parseObject(jsonObject.optString("order"), Order.class);
        // List<OrderShop> list =
        // JSON.parseArray(jsonObject.optString("orderShops"), OrderShop.class);
        List<OrderShop> list = new ArrayList<OrderShop>();
        String str = jsonObject.optString("orderShops");

        if (str != null || !("".equals(str))) {
            JSONArray ja = new JSONArray(str);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject j = (JSONObject) ja.get(i);
                OrderShop shop = new OrderShop();
                if (!j.has("order_code") || null == j.getString("id") || "null".equals(j.getString("id"))
                        || "".equals(j.getString("id"))) {
                    shop.setId(-1);
                } else {
                    shop.setId(j.has("id") ? j.optInt("id") : -1);
                }
                if (!j.has("order_code") || null == j.getString("order_code")
                        || "null".equals(j.getString("order_code")) || "".equals(j.getString("order_code"))) {
                    shop.setOrder_code(-1 + "");
                } else {
                    shop.setOrder_code(j.has("order_code") ? j.optString("order_code") : -1 + "");
                }
                if (!j.has("shop_name") || null == j.getString("shop_name") || "".equals(j.getString("shop_name"))) {
                    shop.setShop_name("");
                } else {
                    shop.setShop_name(j.has("shop_name") ? j.optString("shop_name") : "");
                }
                if (!j.has("shop_code") || null == j.getString("shop_code") || "null".equals(j.getString("shop_code"))
                        || "".equals(j.getString("shop_code"))) {
                    shop.setShop_code(-1 + "");
                } else {
                    shop.setShop_code(j.has("shop_code") ? j.optString("shop_code") : -1 + "");
                }
                if (!j.has("shop_price") || null == j.getString("shop_price")
                        || "null".equals(j.getString("shop_price")) || "".equals(j.getString("shop_price"))) {
                    shop.setShop_price(-1.0);
                } else {
                    shop.setShop_price(j.has("shop_price") ? j.optDouble("shop_price") : -1);
                }
                if (!j.has("shop_pic") || null == j.getString("shop_pic") || "".equals(j.getString("shop_pic"))) {
                    shop.setShop_pic(-1 + "");
                } else {
                    shop.setShop_pic(j.has("shop_pic") ? j.optString("shop_pic") : -1 + "");
                }
                if (!j.has("shop_num") || null == j.getString("shop_num") || "null".equals(j.getString("shop_num"))
                        || "".equals(j.getString("shop_num"))) {
                    shop.setShop_num(-1);
                } else {
                    shop.setShop_num(j.has("shop_num") ? j.optInt("shop_num") : -1);
                }
                if (!j.has("size") || null == j.getString("size") || "null".equals(j.getString("size"))
                        || "".equals(j.getString("size"))) {
                    shop.setSize(-1 + "");
                } else {
                    shop.setSize(j.has("size") ? j.optString("size") : -1 + "");
                }
                if (!j.has("color") || null == j.getString("color") || "null".equals(j.getString("color"))
                        || "".equals(j.getString("color"))) {
                    shop.setColor(-1 + "");
                } else {
                    shop.setColor(j.has("color") ? j.optString("color") : -1 + "");
                }
                if (!j.has("logi_code") || null == j.getString("logi_code") || "null".equals(j.getString("logi_code"))
                        || "".equals(j.getString("logi_code"))) {
                    shop.setLogi_code(-1 + "");
                } else {
                    shop.setLogi_code(j.has("logi_code") ? j.optString("logi_code") : -1 + "");
                }
                if (!j.has("message") || null == j.getString("message") || "".equals(j.getString("message"))) {
                    shop.setMessage(-1 + "");
                } else {
                    shop.setMessage(j.has("message") ? j.optString("message") : -1 + "");
                }
                if (!j.has("bak") || null == j.getString("bak") || "null".equals(j.getString("bak"))
                        || "".equals(j.getString("bak"))) {
                    shop.setBak(-1 + "");
                } else {
                    shop.setBak(j.has("bak") ? j.optString("bak") : -1 + "");
                }

                if (!j.has("stocktypeid") || null == j.getString("stocktypeid")
                        || "null".equals(j.getString("stocktypeid")) || "".equals(j.getString("stocktypeid"))) {
                    shop.setStocktypeid(-1);
                } else {
                    shop.setStocktypeid(j.has("stocktypeid") ? j.optInt("stocktypeid") : -1);
                }
                if (!j.has("change") || null == j.getString("change") || "null".equals(j.getString("change"))
                        || "".equals(j.getString("change"))) {
                    shop.setChange(-1);
                } else {
                    shop.setChange(j.has("change") ? j.optInt("change") : -1);
                }
                if (!j.has("last_time") || null == j.getString("last_time") || "null".equals(j.getString("last_time"))
                        || "".equals(j.getString("last_time"))) {
                    shop.setLast_timess(-1L);
                } else {
                    shop.setLast_timess(j.has("last_time") ? j.optLong("last_time") : -1L);
                }
                list.add(shop);
            }
        }

        if (list != null && list.size() > 0) {
            order.setList(list);
            return order;
        }
        return order;
    }

    /**
     * 获取会员商品列表
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> createMembersShopList(Context context, String result) throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        JSONObject jsonObject = new JSONObject(result);

        JSONArray jsonArray = jsonObject.optJSONArray("data");

        if (jsonArray == null || jsonArray.length() == 0) {
            return null;
        }

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject j = jsonArray.getJSONObject(i);

            Map<String, String> map = new HashMap<String, String>();

            if (!j.has("shop_code") || null == j.getString("shop_code") || "null".equals(j.getString("shop_code"))
                    || "".equals(j.getString("shop_code"))) {
                map.put("shop_code", "");
            } else {
                map.put("shop_code", j.optString("shop_code", ""));
            }
            if (!j.has("shop_name") || null == j.getString("shop_name") || "null".equals(j.getString("shop_name"))
                    || "".equals(j.getString("shop_name"))) {
                map.put("shop_name", "");
            } else {
                map.put("shop_name", j.optString("shop_name", ""));
            }
            if (!j.has("shop_se_price") || null == j.getString("shop_se_price")
                    || "null".equals(j.getString("shop_se_price")) || "".equals(j.getString("shop_se_price"))) {
                map.put("shop_se_price", "0");
            } else {
                map.put("shop_se_price", j.optString("shop_se_price", "0"));
            }
            if (!j.has("def_pic") || null == j.getString("def_pic") || "null".equals(j.getString("def_pic"))
                    || "".equals(j.getString("def_pic"))) {
                map.put("def_pic", "");
            } else {
                map.put("def_pic", j.optString("def_pic", ""));
            }
            list.add(map);
        }

        return list.isEmpty() ? null : list;
    }

    /***
     * 会员验证
     */
    public static final HashMap<String, Object> createMemberVerify(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        if (!jsonObject.has("status") || null == jsonObject.getString("status")
                || "null".equals(jsonObject.getString("status")) || "".equals(jsonObject.getString("status"))) {
            mapRet.put("status", "");
        } else {
            mapRet.put("status", jsonObject.has("status") ? jsonObject.getString("status") : "");
        }
        if (!jsonObject.has("message") || null == jsonObject.getString("message")
                || "".equals(jsonObject.getString("message"))) {
            mapRet.put("message", "");
        } else {
            mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
        }
        if (!jsonObject.has("is_member") || null == jsonObject.getString("is_member")
                || "null".equals(jsonObject.getString("is_member")) || "".equals(jsonObject.getString("is_member"))) {
            mapRet.put("is_member", "");
        } else {
            mapRet.put("is_member", jsonObject.has("is_member") ? jsonObject.optInt("is_member") : "");
        }
        return mapRet;
    }

    /**
     * 获取会员轮播
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */

    public static List<ShopOption> createMembersImgList(Context context, String result) throws Exception {
        List<ShopOption> list = new ArrayList<ShopOption>();

        JSONObject jsonObject = new JSONObject(result);

        JSONArray jsonArray = jsonObject.optJSONArray("topShops");

        if (jsonArray == null || jsonArray.length() == 0) {
            return null;
        }

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject j = jsonArray.getJSONObject(i);

            ShopOption shop = new ShopOption();

            if (!j.has("id") || null == j.getString("id") || "null".equals(j.getString("id"))
                    || "".equals(j.getString("id"))) {
                shop.setId(0);
            } else {
                shop.setId(j.optInt("id", 0));
            }
            if (!j.has("remark") || null == j.getString("remark") || "null".equals(j.getString("remark"))
                    || "".equals(j.getString("remark"))) {
                shop.setRemark("");
            } else {
                shop.setRemark(j.optString("remark", ""));
            }
            if (!j.has("shop_code") || null == j.getString("shop_code") || "null".equals(j.getString("shop_code"))
                    || "".equals(j.getString("shop_code"))) {
                shop.setShop_code("");
            } else {
                shop.setShop_code(j.optString("shop_code", ""));
            }
            if (!j.has("shop_name") || null == j.getString("shop_name") || "null".equals(j.getString("shop_name"))
                    || "".equals(j.getString("shop_name"))) {
                shop.setShop_name("");
            } else {
                shop.setShop_name(j.optString("shop_name", ""));
            }
            if (!j.has("type") || null == j.getString("type") || "null".equals(j.getString("type"))
                    || "".equals(j.getString("type"))) {
                shop.setType(0);
            } else {
                shop.setType(j.optInt("type", 0));
            }
            if (!j.has("url") || null == j.getString("url") || "null".equals(j.getString("url"))
                    || "".equals(j.getString("url"))) {
                shop.setUrl("");
            } else {
                shop.setUrl(j.optString("url", ""));
            }
            list.add(shop);
        }

        return list.isEmpty() ? null : list;
    }

    /***
     * 获取会员卡
     */
    public static final HashMap<String, Object> createQueryVipCard(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        if (!jsonObject.has("status") || null == jsonObject.getString("status")
                || "null".equals(jsonObject.getString("status")) || "".equals(jsonObject.getString("status"))) {
            mapRet.put("status", "");
        } else {
            mapRet.put("status", jsonObject.has("status") ? jsonObject.getString("status") : "");
        }
        if (!jsonObject.has("message") || null == jsonObject.getString("message")
                || "null".equals(jsonObject.getString("message")) || "".equals(jsonObject.getString("message"))) {
            mapRet.put("message", "");
        } else {
            mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
        }
        if (!jsonObject.has("card_no") || null == jsonObject.getString("card_no")
                || "null".equals(jsonObject.getString("card_no")) || "".equals(jsonObject.getString("card_no"))) {
            mapRet.put("card_no", "");
        } else {
            mapRet.put("card_no", jsonObject.has("card_no") ? jsonObject.getString("card_no") : "");
        }
        if (!jsonObject.has("plaintext") || null == jsonObject.getString("plaintext")
                || "null".equals(jsonObject.getString("plaintext")) || "".equals(jsonObject.getString("plaintext"))) {
            mapRet.put("plaintext", "");
        } else {
            mapRet.put("plaintext", jsonObject.has("plaintext") ? jsonObject.getString("plaintext") : "");
        }
        if (!jsonObject.has("time") || null == jsonObject.getString("time")
                || "null".equals(jsonObject.getString("time")) || "".equals(jsonObject.getString("time"))) {
            mapRet.put("time", "0");
        } else {
            mapRet.put("time", jsonObject.has("time") ? jsonObject.getString("time") : "0");
        }
        return mapRet;
    }

    /***
     * 获取用户头像
     */
    public static final String createQueryUserPic(Context context, String result) throws Exception {
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        if (!jsonObject.has("pic") || null == jsonObject.getString("pic") || "null".equals(jsonObject.getString("pic"))
                || "".equals(jsonObject.getString("pic"))) {
            return "";
        } else {
            return jsonObject.has("pic") ? jsonObject.getString("pic") : "";
        }
    }

    /**
     * 超级合伙人卡号列表
     */
    public static final List<HashMap<String, Object>> createSupermanCardList(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (!j.has("status") || null == j.getString("status") || "null".equals(j.getString("status"))
                || "".equals(j.getString("status"))) {
            return null;
        }
        if (1 == j.getInt("status")) {
            JSONArray jsonArray = new JSONArray(j.getString("cardList"));
            if (null == jsonArray || "".equals(jsonArray)) {
                return null;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = (JSONObject) jsonArray.opt(i);
                HashMap<String, Object> mapObject = new HashMap<String, Object>();
                if (!jo.has("card_no") || null == jo.getString("card_no") || "null".equals(jo.getString("card_no"))
                        || "".equals(jo.getString("card_no"))) {
                    mapObject.put("card_no", "");
                } else {
                    mapObject.put("card_no", jo.has("card_no") ? jo.getString("card_no") : "");
                }
                if (!jo.has("id") || null == jo.getString("id") || "null".equals(jo.getString("id"))
                        || "".equals(jo.getString("id"))) {
                    mapObject.put("id", "");
                } else {
                    mapObject.put("id", jo.has("id") ? jo.getString("id") : "");
                }
                if (!jo.has("plaintext") || null == jo.getString("plaintext")
                        || "null".equals(jo.getString("plaintext")) || "".equals(jo.getString("plaintext"))) {
                    mapObject.put("plaintext", "");
                } else {
                    mapObject.put("plaintext", jo.has("plaintext") ? jo.getString("plaintext") : "");
                }
                if (!jo.has("buyer_id") || null == jo.getString("buyer_id") || "null".equals(jo.getString("buyer_id"))
                        || "".equals(jo.getString("buyer_id"))) {
                    mapObject.put("buyer_id", "");
                } else {
                    mapObject.put("buyer_id", jo.has("buyer_id") ? jo.getString("buyer_id") : "");
                }
                if (!jo.has("is_use") || null == jo.getString("is_use") || "null".equals(jo.getString("is_use"))
                        || "".equals(jo.getString("is_use"))) {
                    mapObject.put("is_use", "");
                } else {
                    mapObject.put("is_use", jo.has("is_use") ? jo.getString("is_use") : "");
                }
                if (!jo.has("nickname") || null == jo.getString("nickname") || "null".equals(jo.getString("nickname"))
                        || "".equals(jo.getString("nickname"))) {
                    mapObject.put("nickname", "");
                } else {
                    mapObject.put("nickname", jo.has("nickname") ? jo.getString("nickname") : "");
                }
                list.add(mapObject);
            }
        } else
            return null;

        return list;
    }

    /**
     * 强制浏览
     */
    ////////
    public static final List<HashMap<String, Object>> createForceLook(Context context, String result,
                                                                      boolean isMoreShop) throws JSONException {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        // System.out.println("这是强制浏览的result=" + result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray jsonArray;
        if (isMoreShop) {
            jsonArray = new JSONArray(j.getString("shop_list"));
        } else {
            jsonArray = new JSONArray(j.getString("list"));
        }

        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.get(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();

            if (!jo.has("app_shop_group_price") || null == jo.getString("app_shop_group_price") || "null".equals(jo.getString("app_shop_group_price"))
                    || "".equals(jo.getString("app_shop_group_price"))) {
                mapObject.put("app_shop_group_price", "1.5");
            } else {
                mapObject.put("app_shop_group_price", jo.has("app_shop_group_price") ? jo.getString("app_shop_group_price") : "1.5");
            }


            if (!jo.has("shop_code") || null == jo.getString("shop_code") || "null".equals(jo.getString("shop_code"))
                    || "".equals(jo.getString("shop_code"))) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");
            }

            if (!jo.has("shop_name") || null == jo.getString("shop_name") || "null".equals(jo.getString("shop_name"))
                    || "".equals(jo.getString("shop_name"))) {
                mapObject.put("shop_name", "");
            } else {
                mapObject.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");
            }
            if (!jo.has("shop_price") || null == jo.getString("shop_price") || "null".equals(jo.getString("shop_price"))
                    || "".equals(jo.getString("shop_price"))) {
                mapObject.put("shop_price", "0");
            } else {
                mapObject.put("shop_price", jo.has("shop_price") ? jo.getString("shop_price") : "0");
            }
            if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price")
                    || "null".equals(jo.getString("shop_se_price")) || "".equals(jo.getString("shop_se_price"))) {
                mapObject.put("shop_se_price", "0");
            } else {
                mapObject.put("shop_se_price", jo.has("shop_se_price") ? jo.getString("shop_se_price") : "0");
            }
            if (!jo.has("def_pic") || null == jo.getString("def_pic") || "null".equals(jo.getString("def_pic"))
                    || "".equals(jo.getString("def_pic"))) {
                mapObject.put("def_pic", "");
            } else {
                mapObject.put("def_pic", jo.has("def_pic") ? jo.getString("def_pic") : "");
            }
            if (!jo.has("kickback") || null == jo.getString("kickback") || "null".equals(jo.getString("kickback"))
                    || "".equals(jo.getString("kickback"))) {
                mapObject.put("kickback", "0");
            } else {
                mapObject.put("kickback", jo.has("kickback") ? jo.getString("kickback") : "0");
            }
            if (!jo.has("supp_label") || null == jo.getString("supp_label") || "null".equals(jo.getString("supp_label"))
                    || "".equals(jo.getString("supp_label"))) {
                mapObject.put("supp_label", "");
            } else {
                mapObject.put("supp_label", jo.has("supp_label") ? jo.getString("supp_label") : "");
            }
            if (!jo.has("supp_label_id") || null == jo.getString("supp_label_id")
                    || "null".equals(jo.getString("supp_label_id")) || "".equals(jo.getString("supp_label_id"))) {
                mapObject.put("supp_label_id", "");
            } else {
                mapObject.put("supp_label_id", jo.has("supp_label_id") ? jo.getString("supp_label_id") : "");
            }
            list.add(mapObject);
        }

        // for (int i = 0; i < jsonArray.length(); i++) {
        // JSONObject jo = (JSONObject) jsonArray.opt(i);
        // HashMap<String, Object> mapObject = new HashMap<String, Object>();
        // mapObject.put("card_no", jo.has("card_no") ? jo.getString("card_no")
        // : "");
        // mapObject.put("id", jo.has("id") ? jo.getString("id") : "");
        // mapObject.put("plaintext", jo.has("plaintext") ?
        // jo.getString("plaintext") : "");
        // mapObject.put("buyer_id", jo.has("buyer_id") ?
        // jo.getString("buyer_id") : "");
        // mapObject.put("is_use", jo.has("is_use") ? jo.getString("is_use") :
        // "");
        // mapObject.put("nickname", jo.has("nickname") ?
        // jo.getString("nickname") : "");
        // list.add(mapObject);
        // }

        return list;
    }

    /**
     * 我的最爱---发布用
     */
    ////////
    public static final List<HashMap<String, Object>> createMyLikeFabu(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        // System.out.println("这是强制浏览的result=" + result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray jsonArray;

        jsonArray = new JSONArray(j.getString("likes"));

        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.get(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();

            if (!jo.has("shop_code") || null == jo.getString("shop_code") || "null".equals(jo.getString("shop_code"))
                    || "".equals(jo.getString("shop_code"))) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");
            }
            if (!jo.has("shop_name") || null == jo.getString("shop_name") || "null".equals(jo.getString("shop_name"))
                    || "".equals(jo.getString("shop_name"))) {
                mapObject.put("shop_name", "");
            } else {
                mapObject.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");
            }
            if (!jo.has("shop_price") || null == jo.getString("shop_price") || "null".equals(jo.getString("shop_price"))
                    || "".equals(jo.getString("shop_price"))) {
                mapObject.put("shop_price", "0");
            } else {
                mapObject.put("shop_price", jo.has("shop_price") ? jo.getString("shop_price") : "0");
            }
            if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price")
                    || "null".equals(jo.getString("shop_se_price")) || "".equals(jo.getString("shop_se_price"))) {
                mapObject.put("shop_se_price", "0");
            } else {
                mapObject.put("shop_se_price", jo.has("shop_se_price") ? jo.getString("shop_se_price") : "0");
            }
            if (!jo.has("show_pic") || null == jo.getString("show_pic") || "null".equals(jo.getString("show_pic"))
                    || "".equals(jo.getString("show_pic"))) {
                mapObject.put("def_pic", "");
            } else {
                mapObject.put("def_pic", jo.has("show_pic") ? jo.getString("show_pic") : "");
            }
            if (!jo.has("kickback") || null == jo.getString("kickback") || "null".equals(jo.getString("kickback"))
                    || "".equals(jo.getString("kickback"))) {
                mapObject.put("kickback", "0");
            } else {
                mapObject.put("kickback", jo.has("kickback") ? jo.getString("kickback") : "0");
            }
            if (!jo.has("supp_label") || null == jo.getString("supp_label") || "null".equals(jo.getString("supp_label"))
                    || "".equals(jo.getString("supp_label"))) {
                mapObject.put("supp_label", "");
            } else {
                mapObject.put("supp_label", jo.has("supp_label") ? jo.getString("supp_label") : "");
            }
            if (!jo.has("supp_label_id") || null == jo.getString("supp_label_id")
                    || "null".equals(jo.getString("supp_label_id")) || "".equals(jo.getString("supp_label_id"))) {
                mapObject.put("supp_label_id", "");
            } else {
                mapObject.put("supp_label_id", jo.has("supp_label_id") ? jo.getString("supp_label_id") : "");
            }

            if (!jo.has("app_shop_group_price") || null == jo.getString("app_shop_group_price")
                    || "null".equals(jo.getString("app_shop_group_price")) || "".equals(jo.getString("app_shop_group_price"))) {
                mapObject.put("app_shop_group_price", "1.5");
            } else {
                mapObject.put("app_shop_group_price", jo.has("app_shop_group_price") ? jo.getString("app_shop_group_price") : "1.5");
            }
            list.add(mapObject);
        }

        // for (int i = 0; i < jsonArray.length(); i++) {
        // JSONObject jo = (JSONObject) jsonArray.opt(i);
        // HashMap<String, Object> mapObject = new HashMap<String, Object>();
        // mapObject.put("card_no", jo.has("card_no") ? jo.getString("card_no")
        // : "");
        // mapObject.put("id", jo.has("id") ? jo.getString("id") : "");
        // mapObject.put("plaintext", jo.has("plaintext") ?
        // jo.getString("plaintext") : "");
        // mapObject.put("buyer_id", jo.has("buyer_id") ?
        // jo.getString("buyer_id") : "");
        // mapObject.put("is_use", jo.has("is_use") ? jo.getString("is_use") :
        // "");
        // mapObject.put("nickname", jo.has("nickname") ?
        // jo.getString("nickname") : "");
        // list.add(mapObject);
        // }

        return list;
    }

    /**
     * 发布已经购买的商品列表
     */
    ////////
    public static final List<HashMap<String, Object>> createMyBugShop(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        // System.out.println("这是强制浏览的result=" + result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray jsonArray;

        jsonArray = new JSONArray(j.getString("listShop"));

        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.get(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();

            if (!jo.has("shop_code") || null == jo.getString("shop_code") || "null".equals(jo.getString("shop_code"))
                    || "".equals(jo.getString("shop_code"))) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");
            }
            if (!jo.has("shop_name") || null == jo.getString("shop_name") || "null".equals(jo.getString("shop_name"))
                    || "".equals(jo.getString("shop_name"))) {
                mapObject.put("shop_name", "");
            } else {
                mapObject.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");
            }
            if (!jo.has("shop_price") || null == jo.getString("shop_price") || "null".equals(jo.getString("shop_price"))
                    || "".equals(jo.getString("shop_price"))) {
                mapObject.put("shop_price", "0");
            } else {
                mapObject.put("shop_price", jo.has("shop_price") ? jo.getString("shop_price") : "0");
            }
            if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price")
                    || "null".equals(jo.getString("shop_se_price")) || "".equals(jo.getString("shop_se_price"))) {
                mapObject.put("shop_se_price", "0");
            } else {
                mapObject.put("shop_se_price", jo.has("shop_se_price") ? jo.getString("shop_se_price") : "0");
            }
            if (!jo.has("def_pic") || null == jo.getString("def_pic") || "null".equals(jo.getString("def_pic"))
                    || "".equals(jo.getString("def_pic"))) {
                mapObject.put("def_pic", "");
            } else {
                mapObject.put("def_pic", jo.has("def_pic") ? jo.getString("def_pic") : "");
            }
            if (!jo.has("kickback") || null == jo.getString("kickback") || "null".equals(jo.getString("kickback"))
                    || "".equals(jo.getString("kickback"))) {
                mapObject.put("kickback", "0");
            } else {
                mapObject.put("kickback", jo.has("kickback") ? jo.getString("kickback") : "0");
            }
            if (!jo.has("supp_label") || null == jo.getString("supp_label") || "null".equals(jo.getString("supp_label"))
                    || "".equals(jo.getString("supp_label"))) {
                mapObject.put("supp_label", "");
            } else {
                mapObject.put("supp_label", jo.has("supp_label") ? jo.getString("supp_label") : "");
            }
            if (!jo.has("supp_label_id") || null == jo.getString("supp_label_id")
                    || "null".equals(jo.getString("supp_label_id")) || "".equals(jo.getString("supp_label_id"))) {
                mapObject.put("supp_label_id", "");
            } else {
                mapObject.put("supp_label_id", jo.has("supp_label_id") ? jo.getString("supp_label_id") : "");
            }


            if (!jo.has("app_shop_group_price") || null == jo.getString("app_shop_group_price")
                    || "null".equals(jo.getString("app_shop_group_price")) || "".equals(jo.getString("app_shop_group_price"))) {
                mapObject.put("app_shop_group_price", "1.5");
            } else {
                mapObject.put("app_shop_group_price", jo.has("app_shop_group_price") ? jo.getString("app_shop_group_price") : "1.5");
            }

            list.add(mapObject);
        }

        // for (int i = 0; i < jsonArray.length(); i++) {
        // JSONObject jo = (JSONObject) jsonArray.opt(i);
        // HashMap<String, Object> mapObject = new HashMap<String, Object>();
        // mapObject.put("card_no", jo.has("card_no") ? jo.getString("card_no")
        // : "");
        // mapObject.put("id", jo.has("id") ? jo.getString("id") : "");
        // mapObject.put("plaintext", jo.has("plaintext") ?
        // jo.getString("plaintext") : "");
        // mapObject.put("buyer_id", jo.has("buyer_id") ?
        // jo.getString("buyer_id") : "");
        // mapObject.put("is_use", jo.has("is_use") ? jo.getString("is_use") :
        // "");
        // mapObject.put("nickname", jo.has("nickname") ?
        // jo.getString("nickname") : "");
        // list.add(mapObject);
        // }

        return list;
    }

    ///////

    /***
     * 检测uid
     */
    public static final HashMap<String, Object> createUID(Context context, String result) throws JSONException {
        HashMap<String, Object> mapObject = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (!j.has("flag") || null == j.getString("flag") || "null".equals(j.getString("flag"))
                || "".equals(j.getString("flag"))) {
            mapObject.put("flag", "");
        } else {
            mapObject.put("flag", (j.has("flag") ? j.getString("flag") : ""));
        }
        if (!j.has("uid") || null == j.getString("uid") || "null".equals(j.getString("uid"))
                || "".equals(j.getString("uid"))) {
            mapObject.put("uid", "");
            YCache.getCacheUser(context).setUuid("");
        } else {
            mapObject.put("uid", j.has("uid") ? j.getString("uid") : "");
            YCache.getCacheUser(context).setUuid(j.getString("uid"));
        }

        return mapObject;
    }

    /***
     * 塞进红包
     */
    public static final HashMap<String, Object> creatRedPackets(Context context, String result) throws JSONException {
        HashMap<String, Object> mapObject = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (!j.has("rp_id") || null == j.getString("rp_id") || "null".equals(j.getString("rp_id"))
                || "".equals(j.getString("rp_id"))) {
            mapObject.put("rp_id", "");
        } else {
            mapObject.put("rp_id", (j.has("rp_id") ? j.getString("rp_id") : ""));
        }
        if (!j.has("message") || null == j.getString("message") || "null".equals(j.getString("message"))
                || "".equals(j.getString("message"))) {
            mapObject.put("message", "");
        } else {
            mapObject.put("message", j.has("message") ? j.getString("message") : "");
        }
        if (!j.has("status") || null == j.getString("status") || "null".equals(j.getString("status"))
                || "".equals(j.getString("status"))) {
            mapObject.put("status", "");
        } else {
            mapObject.put("status", j.has("status") ? j.getString("status") : "");
        }

        return mapObject;
    }

    /***
     * 获取签到首页数据
     */
    public static final HashMap<String, Object> createSignData(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        if (!jsonObject.has("status") || null == jsonObject.getString("status")
                || "null".equals(jsonObject.getString("status")) || "".equals(jsonObject.getString("status"))) {
            return null;
        }
        if (1 == jsonObject.getInt("status")) {


            if (!jsonObject.has("whetherTask") || null == jsonObject.getString("whetherTask")
                    || "null".equals(jsonObject.getString("whetherTask")) || "".equals(jsonObject.getString("whetherTask"))) {
                mapRet.put("whetherTask", "");
            } else {
                mapRet.put("whetherTask", jsonObject.has("whetherTask") ? jsonObject.getString("whetherTask") : "");
            }


            if (!jsonObject.has("status") || null == jsonObject.getString("status")
                    || "null".equals(jsonObject.getString("status")) || "".equals(jsonObject.getString("status"))) {
                mapRet.put("status", "");
            } else {
                mapRet.put("status", jsonObject.has("status") ? jsonObject.getString("status") : "");
            }


            if (!jsonObject.has("current_date") || null == jsonObject.getString("current_date")
                    || "null".equals(jsonObject.getString("current_date")) || "".equals(jsonObject.getString("current_date"))) {
                mapRet.put("current_date", "1");
            } else {
                mapRet.put("current_date", jsonObject.has("current_date") ? jsonObject.getString("current_date") : "1");
            }


            if (!jsonObject.has("todayReturnMoney") || null == jsonObject.getString("todayReturnMoney")
                    || "null".equals(jsonObject.getString("todayReturnMoney")) || "".equals(jsonObject.getString("todayReturnMoney"))) {
                mapRet.put("todayReturnMoney", "1");
            } else {
                mapRet.put("todayReturnMoney", jsonObject.has("todayReturnMoney") ? jsonObject.getString("todayReturnMoney") : "1");
            }


            if (!jsonObject.has("cumReturnMoney") || null == jsonObject.getString("cumReturnMoney")
                    || "null".equals(jsonObject.getString("cumReturnMoney")) || "".equals(jsonObject.getString("cumReturnMoney"))) {
                mapRet.put("cumReturnMoney", "1");
            } else {
                mapRet.put("cumReturnMoney", jsonObject.has("cumReturnMoney") ? jsonObject.getString("cumReturnMoney") : "1");
            }


            if (!jsonObject.has("cumWitMoney") || null == jsonObject.getString("cumWitMoney")
                    || "null".equals(jsonObject.getString("cumWitMoney")) || "".equals(jsonObject.getString("cumWitMoney"))) {
                mapRet.put("cumWitMoney", "1");
            } else {
                mapRet.put("cumWitMoney", jsonObject.has("cumWitMoney") ? jsonObject.getString("cumWitMoney") : "1");
            }


            if (!jsonObject.has("message") || null == jsonObject.getString("message")
                    || "null".equals(jsonObject.getString("message")) || "".equals(jsonObject.getString("message"))) {
                mapRet.put("message", "");
            } else {
                mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
            }
            if (!jsonObject.has("sCount") || null == jsonObject.getString("sCount")
                    || "null".equals(jsonObject.getString("sCount")) || "".equals(jsonObject.getString("sCount"))) {
                mapRet.put("sCount", "0");
            } else {
                mapRet.put("sCount", jsonObject.has("sCount") ? jsonObject.getString("sCount") : "0");
            }
            if (!jsonObject.has("iCount") || null == jsonObject.getString("iCount")
                    || "null".equals(jsonObject.getString("iCount")) || "".equals(jsonObject.getString("iCount"))) {
                mapRet.put("iCount", "0");
            } else {
                mapRet.put("iCount", jsonObject.has("iCount") ? jsonObject.getString("iCount") : "0");
            }
            if (!jsonObject.has("bCount") || null == jsonObject.getString("bCount")
                    || "null".equals(jsonObject.getString("bCount")) || "".equals(jsonObject.getString("bCount"))) {
                mapRet.put("bCount", "0");
            } else {
                mapRet.put("bCount", jsonObject.has("bCount") ? jsonObject.getDouble("bCount") : "0");
            }
            if (!jsonObject.has("cCount") || null == jsonObject.getString("cCount")
                    || "null".equals(jsonObject.getString("cCount")) || "".equals(jsonObject.getString("cCount"))) {
                mapRet.put("cCount", "0");
            } else {
                mapRet.put("cCount", jsonObject.has("cCount") ? jsonObject.getString("cCount") : "0");
            }

            //提现中
            if (!jsonObject.has("desing") || null == jsonObject.getString("desing")
                    || "null".equals(jsonObject.getString("desing")) || "".equals(jsonObject.getString("desing"))) {
                mapRet.put("desing", "0");
            } else {
                mapRet.put("desing", jsonObject.has("desing") ? jsonObject.getString("desing") : "0");
            }


            if (!jsonObject.has("fxqd") || null == jsonObject.getString("fxqd")
                    || "null".equals(jsonObject.getString("fxqd")) || "".equals(jsonObject.getString("fxqd"))) {
                mapRet.put("fxqd", "0");
            } else {
                mapRet.put("fxqd", jsonObject.has("fxqd") ? jsonObject.getString("fxqd") : "0");
            }

            // bro_count：浏览数

            if (!jsonObject.has("bro_count") || null == jsonObject.getString("bro_count")
                    || "null".equals(jsonObject.getString("bro_count"))
                    || "".equals(jsonObject.getString("bro_count"))) {
                mapRet.put("bro_count", "0");
            } else {
                mapRet.put("bro_count", jsonObject.has("bro_count") ? jsonObject.getString("bro_count") : "0");
            }
            // count_money：累计奖励金额

            if (!jsonObject.has("count_money") || null == jsonObject.getString("count_money")
                    || "null".equals(jsonObject.getString("count_money"))
                    || "".equals(jsonObject.getString("count_money"))) {
                mapRet.put("count_money", "0");
            } else {
                mapRet.put("count_money", jsonObject.has("count_money") ? jsonObject.getString("count_money") : "0");
            }

            // fans_count：粉丝数
            if (!jsonObject.has("fans_count") || null == jsonObject.getString("fans_count")
                    || "null".equals(jsonObject.getString("fans_count"))
                    || "".equals(jsonObject.getString("fans_count"))) {
                mapRet.put("fans_count", "0");
            } else {
                mapRet.put("fans_count", jsonObject.has("fans_count") ? jsonObject.getString("fans_count") : "0");
            }


            //好友提成系统分享次数
            if (!jsonObject.has("shareCount") || null == jsonObject.getString("shareCount")
                    || "null".equals(jsonObject.getString("shareCount"))
                    || "".equals(jsonObject.getString("shareCount"))) {
                mapRet.put("shareCount", "0");
            } else {
                mapRet.put("shareCount", jsonObject.has("shareCount") ? jsonObject.getString("shareCount") : "0");
            }


            //好友提成系统总奖励数
            if (!jsonObject.has("shareMoneyCount") || null == jsonObject.getString("shareMoneyCount")
                    || "null".equals(jsonObject.getString("shareMoneyCount"))
                    || "".equals(jsonObject.getString("shareMoneyCount"))) {
                mapRet.put("shareMoneyCount", "0");
            } else {
                mapRet.put("shareMoneyCount", jsonObject.has("shareMoneyCount") ? jsonObject.getString("shareMoneyCount") : "0");
            }


            // today_money 每日金额
            if (!jsonObject.has("today_money") || null == jsonObject.getString("today_money")
                    || "null".equals(jsonObject.getString("today_money"))
                    || "".equals(jsonObject.getString("today_money"))) {
                mapRet.put("today_money", "0");
            } else {
                mapRet.put("today_money", jsonObject.has("today_money") ? jsonObject.getString("today_money") : "0");
            }

            // withdrawal_money 可提现额度

            if (!jsonObject.has("withdrawal_money") || null == jsonObject.getString("withdrawal_money")
                    || "null".equals(jsonObject.getString("withdrawal_money"))
                    || "".equals(jsonObject.getString("withdrawal_money"))) {
                mapRet.put("withdrawal_money", "0");
            } else {
                mapRet.put("withdrawal_money",
                        jsonObject.has("withdrawal_money") ? jsonObject.getString("withdrawal_money") : "0");
            }
            // LotteryNumber 抽奖次数

            if (!jsonObject.has("LotteryNumber") || null == jsonObject.getString("LotteryNumber")
                    || "null".equals(jsonObject.getString("LotteryNumber"))
                    || "".equals(jsonObject.getString("LotteryNumber"))) {
                mapRet.put("LotteryNumber", "0");
            } else {
                mapRet.put("LotteryNumber",
                        jsonObject.has("LotteryNumber") ? jsonObject.getString("LotteryNumber") : "0");
            }

            //point_status点赞状态 0默认，1第一次点赞，2多次点赞

            if (!jsonObject.has("point_status") || null == jsonObject.getString("point_status")
                    || "null".equals(jsonObject.getString("point_status"))
                    || "".equals(jsonObject.getString("point_status"))) {
                mapRet.put("point_status", "-1");
            } else {
                mapRet.put("point_status",
                        jsonObject.has("point_status") ? jsonObject.getString("point_status") : "-1");
            }
            //total_rewards点赞获得奖励数
            if (!jsonObject.has("total_rewards") || null == jsonObject.getString("total_rewards")
                    || "null".equals(jsonObject.getString("total_rewards"))
                    || "".equals(jsonObject.getString("total_rewards"))) {
                mapRet.put("total_rewards", "0");
            } else {
                mapRet.put("total_rewards",
                        jsonObject.has("total_rewards") ? jsonObject.getString("total_rewards") : "0");
            }
            //point_count 获得点赞数
            if (!jsonObject.has("point_count") || null == jsonObject.getString("point_count")
                    || "null".equals(jsonObject.getString("point_count"))
                    || "".equals(jsonObject.getString("point_count"))) {
                mapRet.put("point_count", "0");
            } else {
                mapRet.put("point_count",
                        jsonObject.has("point_count") ? jsonObject.getString("point_count") : "0");
            }
            //isGratis 如果用户点击继续点赞使用的是免费的还是收费的（扣衣豆）
            if (!jsonObject.has("isGratis") || null == jsonObject.getString("isGratis")
                    || "null".equals(jsonObject.getString("isGratis"))
                    || "".equals(jsonObject.getString("isGratis"))) {
                mapRet.put("isGratis", "false");
            } else {
                mapRet.put("isGratis",
                        jsonObject.has("isGratis") ? jsonObject.getString("isGratis") : "false");
            }


            //popup //弹窗状态 0不弹窗，1弹窗---免费机会已用完
            if (!jsonObject.has("popup") || null == jsonObject.getString("popup")
                    || "null".equals(jsonObject.getString("popup"))
                    || "".equals(jsonObject.getString("popup"))) {
                mapRet.put("popup", "0");
            } else {
                mapRet.put("popup",
                        jsonObject.has("popup") ? jsonObject.getString("popup") : "0");
            }


//------------------------------------拼团相关------------------------------------------------------
            if (!jsonObject.has("roll") || null == jsonObject.getString("roll")
                    || "null".equals(jsonObject.getString("roll"))
                    || "".equals(jsonObject.getString("roll"))) {
                mapRet.put("roll", "0");
            } else {
                mapRet.put("roll",
                        jsonObject.has("roll") ? jsonObject.getString("roll") : "0");
            }

            if (!jsonObject.has("fighStatus") || null == jsonObject.getString("fighStatus")
                    || "null".equals(jsonObject.getString("fighStatus"))
                    || "".equals(jsonObject.getString("fighStatus"))) {
                mapRet.put("fighStatus", "0");
            } else {
                mapRet.put("fighStatus",
                        jsonObject.has("fighStatus") ? jsonObject.getString("fighStatus") : "0");
            }

            if (!jsonObject.has("orderStatus") || null == jsonObject.getString("orderStatus")
                    || "null".equals(jsonObject.getString("orderStatus"))
                    || "".equals(jsonObject.getString("orderStatus"))) {
                mapRet.put("orderStatus", "0");
            } else {
                mapRet.put("orderStatus",
                        jsonObject.has("orderStatus") ? jsonObject.getString("orderStatus") : "0");
            }
            if (!jsonObject.has("orderCount") || null == jsonObject.getString("orderCount")
                    || "null".equals(jsonObject.getString("orderCount"))
                    || "".equals(jsonObject.getString("orderCount"))) {
                mapRet.put("orderCount", "0");
            } else {
                mapRet.put("orderCount",
                        jsonObject.has("orderCount") ? jsonObject.getString("orderCount") : "0");
            }


            if (!jsonObject.has("offered") || null == jsonObject.getString("offered")
                    || "null".equals(jsonObject.getString("offered"))
                    || "".equals(jsonObject.getString("offered"))) {
                mapRet.put("offered", "0");
            } else {
                mapRet.put("offered",
                        jsonObject.has("offered") ? jsonObject.getString("offered") : "0");
            }

            return mapRet;
        }

        return null;
    }

    /***
     * 获取红包详情
     */
    public static final HashMap<String, Object> creatRedpackets(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        if (1 == jsonObject.getInt("status")) {
            JSONObject j = jsonObject.optJSONObject("data");
            if (null == j || "".equals(j)) {
                return null;
            }
            if (!j.has("link") || null == j.getString("link") || "null".equals(j.getString("link"))
                    || "".equals(j.getString("link"))) {
                mapRet.put("link", "");
            } else {
                mapRet.put("link", j.has("link") ? j.getString("link") : "");
            }
            if (!j.has("content") || null == j.getString("content") || "null".equals(j.getString("content"))
                    || "".equals(j.getString("content"))) {
                mapRet.put("content", "");
            } else {
                mapRet.put("content", j.has("content") ? j.getString("content") : "");
            }
            if (!j.has("name") || null == j.getString("name") || "".equals(j.getString("name"))) {
                mapRet.put("name", "");
            } else {
                mapRet.put("name", j.has("name") ? j.getString("name") : "");
            }

            return mapRet;
        } else {
            return null;
        }
    }

    /**
     * 签到任务列表
     */
    public static final HashMap<String, List<HashMap<String, Object>>> createSignList(Context context, String result)
            throws JSONException {

        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }

        // MyLogYiFu.e("resultA", result + "");
        HashMap<String, List<HashMap<String, Object>>> maplist = new HashMap<String, List<HashMap<String, Object>>>();

        List<HashMap<String, Object>> taskList = new ArrayList<HashMap<String, Object>>();
        if (1 == j.getInt("status")) {


            if (!j.has("today_ref") || null == j.getString("today_ref") || "null".equals(j.getString("today_ref"))
                    || "".equals(j.getString("today_ref"))) {
                SignFragment.today_ref = "0";
            } else {
                SignFragment.today_ref = j.getString("today_ref");
            }
//------------------------------------------------------------------------------------------------------------------------
            //是否有星期一
            if (!j.has("isMonday") || null == j.getString("isMonday") || "null".equals(j.getString("isMonday"))
                    || "".equals(j.getString("isMonday"))) {
                SharedPreferencesUtil.saveBooleanData(context, Pref.ISMADMONDAY, false);

            } else {
                boolean isMon = false;
                try {
                    isMon = Boolean.parseBoolean(j.getString("isMonday"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SharedPreferencesUtil.saveBooleanData(context, Pref.ISMADMONDAY, isMon);
            }

//------------------------------------------------------------------------------------------------------------------------
            //是否有零元购
            if (!j.has("zero_buy") || null == j.getString("zero_buy") || "null".equals(j.getString("zero_buy"))
                    || "".equals(j.getString("zero_buy"))) {
                SharedPreferencesUtil.saveBooleanData(context, Pref.ISHASLINGYUANGOU, false);

            } else {
                boolean is0 = false;
                try {
                    if ("1".equals(j.getString("zero_buy"))) {
                        is0 = true;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                SharedPreferencesUtil.saveBooleanData(context, Pref.ISHASLINGYUANGOU, is0);
            }
//------------------------------------------------------------------------------------------------------------------------
            //是否有每月惊喜任务
            if (!j.has("monthly") || null == j.getString("monthly") || "null".equals(j.getString("monthly"))
                    || "".equals(j.getString("monthly"))) {
                SharedPreferencesUtil.saveBooleanData(context, Pref.HASMEIYUEJINGXI, false);

            } else {
                boolean is0 = false;
                try {
                    if ("1".equals(j.getString("monthly"))) {
                        is0 = true;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                SharedPreferencesUtil.saveBooleanData(context, Pref.HASMEIYUEJINGXI, is0);
            }

//------------------------------------------------------------------------------------------------------------------------


            //是有千元包任务
            if (!j.has("red_rain") || null == j.getString("red_rain") || "null".equals(j.getString("red_rain"))
                    || "".equals(j.getString("red_rain"))) {
                SharedPreferencesUtil.saveBooleanData(context, Pref.HAISQIANYAUNHBONGBAO_SIGN, false);

            } else {
                boolean is0 = false;
                try {
                    if ("1".equals(j.getString("red_rain"))) {
                        is0 = true;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                SharedPreferencesUtil.saveBooleanData(context, Pref.HAISQIANYAUNHBONGBAO_SIGN, is0);
            }


//------------------------------------------------------------------------------------------------------------------------
            SharedPreferencesUtil.saveBooleanData(context, Pref.FRIENDTICHENGPAGE, false);

            boolean hasTicheng = false;//是否有好友提成的任务
            if (!j.has("sup_day") || null == j.getString("sup_day") || "null".equals(j.getString("sup_day"))
                    || "".equals(j.getString("sup_day"))) {
                hasTicheng = false;

            } else {
                try {
                    if ("1".equals(j.getString("sup_day"))) {
                        hasTicheng = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (hasTicheng) {
                SharedPreferencesUtil.saveBooleanData(context, Pref.FRIENDTICHENGPAGE, true);

            } else {
                if (!j.has("fri_win") || null == j.getString("fri_win") || "null".equals(j.getString("fri_win"))
                        || "".equals(j.getString("fri_win"))) {

                } else {
                    try {
                        if ("1".equals(j.getString("fri_win"))) {
                            SharedPreferencesUtil.saveBooleanData(context, Pref.FRIENDTICHENGPAGE, true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }


//------------------------------------------------------------------------------------------------------------------------


            String task_class = ""; // 用于区分必做任务（1）和额外任务（2）

            // 当天必做任务
            List<HashMap<String, Object>> daytaskList = new ArrayList<HashMap<String, Object>>();
            // 当天额外任务
            List<HashMap<String, Object>> othertaskList = new ArrayList<HashMap<String, Object>>();
            // 惊喜任务
            List<HashMap<String, Object>> surpriseTaskList = new ArrayList<HashMap<String, Object>>();
            // 惊喜提现任务
            List<HashMap<String, Object>> TXsurpriseTaskList = new ArrayList<HashMap<String, Object>>();
            // 集赞任务列表
            List<HashMap<String, Object>> jizanTastList = new ArrayList<HashMap<String, Object>>();
            //夺宝任务列表--已经改成超级惊喜任务栏
            List<HashMap<String, Object>> duoBaoTaskList = new ArrayList<HashMap<String, Object>>();

            // 已完成任务填充图标
            List<HashMap<String, Object>> taskiconList = new ArrayList<HashMap<String, Object>>();

            JSONArray jsondaytaskList = new JSONArray(j.getString("daytaskList"));// 包含必做任务和额外任务
            if (null == jsondaytaskList || "".equals(jsondaytaskList)) {
                return null;
            }

            for (int i = 0; i < jsondaytaskList.length(); i++) {
                JSONObject jo = (JSONObject) jsondaytaskList.opt(i);
                HashMap<String, Object> mapObject = new HashMap<String, Object>();

                if (!jo.has("task_class") || null == jo.getString("task_class")
                        || "null".equals(jo.getString("task_class")) || "".equals(jo.getString("task_class"))) {
                    return null;
                } else {
                    task_class = jo.getString("task_class");
                }


                int taskClass = 5;


                try {
                    taskClass = Integer.parseInt(task_class);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


                if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id")) // 奖励ID---默认给"4"---积分
                        || "".equals(jo.getString("t_id"))) {
                    mapObject.put("t_id", "4");
                } else {
                    mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "4");
                }
                if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
                        || "".equals(jo.getString("value"))) {
                    mapObject.put("value", "0"); // 此value指的是去干什么用的value
                } else {
                    mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
                }
                if (!jo.has("task_type") || null == jo.getString("task_type")
                        || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
                    mapObject.put("task_type", "");
                } else {
                    mapObject.put("task_type", jo.has("task_type") ? jo.getString("task_type") : "");
                }
                if (!jo.has("index") || null == jo.getString("index") || "null".equals(jo.getString("index"))
                        || "".equals(jo.getString("index"))) {
                    mapObject.put("index", "");
                } else {
                    mapObject.put("index", jo.has("index") ? jo.getString("index") : "");
                }

                if (!jo.has("num") || null == jo.getString("num") || "null".equals(jo.getString("num"))
                        || "".equals(jo.getString("num"))) {
                    mapObject.put("num", "");
                } else {
                    mapObject.put("num", jo.has("num") ? jo.getString("num") : "");
                }

                if (!jo.has("task_h5") || null == jo.getString("task_h5") || "null".equals(jo.getString("task_h5"))
                        || "".equals(jo.getString("task_h5"))) {
                    mapObject.put("task_h5", "1");
                } else {
                    mapObject.put("task_h5", jo.has("task_h5") ? jo.getString("task_h5") : "1");
                }

                // 新增对应icon图标的id 去 shopGroupList中的id做对比
                if (!jo.has("icon") || null == jo.getString("icon") || "null".equals(jo.getString("icon"))
                        || "".equals(jo.getString("icon"))) {
                    mapObject.put("icon", "");
                } else {
                    mapObject.put("icon", jo.has("icon") ? jo.getString("icon") : "");
                }

                //夺宝任务奖励名称
                if (!jo.has("type_name") || null == jo.getString("type_name") || "null".equals(jo.getString("type_name"))
                        || "".equals(jo.getString("type_name"))) {
                    mapObject.put("type_name", "苹果7");
                } else {
                    mapObject.put("type_name", jo.has("type_name") ? jo.getString("type_name") : "苹果7");
                }
                mapObject.put("task_class", taskClass + "");

                switch (taskClass) {
                    case 1:
                        daytaskList.add(mapObject); // 添加每日必做任务
                        break;
                    case 2:
                        othertaskList.add(mapObject); // 添加每日额外任务
                        break;
                    case 3:
                        surpriseTaskList.add(mapObject); // 添加惊喜任务------已经改成每月惊喜任务
                        break;
                    case 4:
                        TXsurpriseTaskList.add(mapObject); // 添加惊喜任务--提现
                        break;
                    case 5:
                        jizanTastList.add(mapObject); // 添加惊喜任务--提现
                        break;
                    case 6:
                        duoBaoTaskList.add(mapObject); // 已经改成超级惊喜任务栏
                        break;
                    default:
                        break;
                }


//                // 判断task_class填充必做任务和额外任务
//                if (task_class.equals("1")) {// 必做任务（包括已完成和未完成）
//
//                    mapObject.put("task_class", "1");
//
//                    if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id")) // 奖励ID---默认给"4"---积分
//                            || "".equals(jo.getString("t_id"))) {
//                        mapObject.put("t_id", "4");
//                    } else {
//                        mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "4");
//                    }
//                    if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
//                            || "".equals(jo.getString("value"))) {
//                        mapObject.put("value", "0"); // 此value指的是去干什么用的value
//                    } else {
//                        mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
//                    }
//                    if (!jo.has("task_type") || null == jo.getString("task_type")
//                            || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
//                        mapObject.put("task_type", "");
//                    } else {
//                        mapObject.put("task_type", jo.has("task_type") ? jo.getString("task_type") : "");
//                    }
//                    if (!jo.has("index") || null == jo.getString("index") || "null".equals(jo.getString("index"))
//                            || "".equals(jo.getString("index"))) {
//                        mapObject.put("index", "");
//                    } else {
//                        mapObject.put("index", jo.has("index") ? jo.getString("index") : "");
//                    }
//
//                    if (!jo.has("num") || null == jo.getString("num") || "null".equals(jo.getString("num"))
//                            || "".equals(jo.getString("num"))) {
//                        mapObject.put("num", "");
//                    } else {
//                        mapObject.put("num", jo.has("num") ? jo.getString("num") : "");
//                    }
//
//                    // 新增对应icon图标的id 去 shopGroupList中的id做对比
//                    if (!jo.has("icon") || null == jo.getString("icon") || "null".equals(jo.getString("icon"))
//                            || "".equals(jo.getString("icon"))) {
//                        mapObject.put("icon", "");
//                    } else {
//                        mapObject.put("icon", jo.has("icon") ? jo.getString("icon") : "");
//                    }
//
//                    //夺宝任务奖励名称
//                    if (!jo.has("type_name") || null == jo.getString("type_name") || "null".equals(jo.getString("type_name"))
//                            || "".equals(jo.getString("type_name"))) {
//                        mapObject.put("type_name", "苹果7");
//                    } else {
//                        mapObject.put("type_name", jo.has("type_name") ? jo.getString("type_name") : "苹果7");
//                    }
//
//                    daytaskList.add(mapObject); // 添加每日必做任务
//
//                } else if (task_class.equals("2")) { // 额外任务
//
//                    mapObject.put("task_class", "2");
//
//                    if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id"))
//                            || "".equals(jo.getString("t_id"))) {
//                        mapObject.put("t_id", "4");
//                    } else {
//                        mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "4");
//                    }
//
//                    if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
//                            || "".equals(jo.getString("value"))) {
//                        mapObject.put("value", "0");
//                    } else {
//                        mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
//                    }
//                    if (!jo.has("task_type") || null == jo.getString("task_type")
//                            || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
//                        mapObject.put("task_type", "");
//                    } else {
//                        mapObject.put("task_type", jo.has("task_type") ? jo.getString("task_type") : "");
//                    }
//                    if (!jo.has("index") || null == jo.getString("index") || "null".equals(jo.getString("index"))
//                            || "".equals(jo.getString("index"))) {
//                        mapObject.put("index", "");
//                    } else {
//                        mapObject.put("index", jo.has("index") ? jo.getString("index") : "");
//                    }
//
//                    if (!jo.has("num") || null == jo.getString("num") || "null".equals(jo.getString("num"))
//                            || "".equals(jo.getString("num"))) {
//                        mapObject.put("num", "");
//                    } else {
//                        mapObject.put("num", jo.has("num") ? jo.getString("num") : "");
//                    }
//
//                    // 新增对应icon图标的id 去 shopGroupList中的id做对比
//                    if (!jo.has("icon") || null == jo.getString("icon") || "null".equals(jo.getString("icon"))
//                            || "".equals(jo.getString("icon"))) {
//                        mapObject.put("icon", "");
//                    } else {
//                        mapObject.put("icon", jo.has("icon") ? jo.getString("icon") : "");
//                    }
//
//                    //夺宝任务奖励名称
//                    if (!jo.has("type_name") || null == jo.getString("type_name") || "null".equals(jo.getString("type_name"))
//                            || "".equals(jo.getString("type_name"))) {
//                        mapObject.put("type_name", "苹果7");
//                    } else {
//                        mapObject.put("type_name", jo.has("type_name") ? jo.getString("type_name") : "苹果7");
//                    }
//
//                    othertaskList.add(mapObject); // 添加每日额外任务
//
//                } else if (task_class.equals("3")) { // 惊喜任务
//
//                    mapObject.put("task_class", "3");
//
//                    if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id"))
//                            || "".equals(jo.getString("t_id"))) {
//                        mapObject.put("t_id", "4");
//                    } else {
//                        mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "4");
//                    }
//                    if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
//                            || "".equals(jo.getString("value"))) {
//                        mapObject.put("value", "0");
//                    } else {
//                        mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
//                    }
//                    if (!jo.has("task_type") || null == jo.getString("task_type")
//                            || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
//                        mapObject.put("task_type", "");
//                    } else {
//                        mapObject.put("task_type", jo.has("task_type") ? jo.getString("task_type") : "");
//                    }
//                    if (!jo.has("index") || null == jo.getString("index") || "null".equals(jo.getString("index"))
//                            || "".equals(jo.getString("index"))) {
//                        mapObject.put("index", "");
//                    } else {
//                        mapObject.put("index", jo.has("index") ? jo.getString("index") : "");
//                    }
//                    if (!jo.has("num") || null == jo.getString("num") || "null".equals(jo.getString("num"))
//                            || "".equals(jo.getString("num"))) {
//                        mapObject.put("num", "");
//                    } else {
//                        mapObject.put("num", jo.has("num") ? jo.getString("num") : "");
//                    }
//
//                    // 新增对应icon图标的id 去 shopGroupList中的id做对比
//                    if (!jo.has("icon") || null == jo.getString("icon") || "null".equals(jo.getString("icon"))
//                            || "".equals(jo.getString("icon"))) {
//                        mapObject.put("icon", "");
//                    } else {
//                        mapObject.put("icon", jo.has("icon") ? jo.getString("icon") : "");
//                    }
//
//                    //夺宝任务奖励名称
//                    if (!jo.has("type_name") || null == jo.getString("type_name") || "null".equals(jo.getString("type_name"))
//                            || "".equals(jo.getString("type_name"))) {
//                        mapObject.put("type_name", "苹果7");
//                    } else {
//                        mapObject.put("type_name", jo.has("type_name") ? jo.getString("type_name") : "苹果7");
//                    }
//
//                    surpriseTaskList.add(mapObject); // 添加惊喜任务
//
//                } else if (task_class.equals("4")) {// 惊喜提现任务
//
//                    mapObject.put("task_class", "4");
//
//                    if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id"))
//                            || "".equals(jo.getString("t_id"))) {
//                        mapObject.put("t_id", "4");
//                    } else {
//                        mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "4");
//                    }
//                    if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
//                            || "".equals(jo.getString("value"))) {
//                        mapObject.put("value", "0");
//                    } else {
//                        mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
//                    }
//                    if (!jo.has("task_type") || null == jo.getString("task_type")
//                            || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
//                        mapObject.put("task_type", "");
//                    } else {
//                        mapObject.put("task_type", jo.has("task_type") ? jo.getString("task_type") : "");
//                    }
//                    if (!jo.has("index") || null == jo.getString("index") || "null".equals(jo.getString("index"))
//                            || "".equals(jo.getString("index"))) {
//                        mapObject.put("index", "");
//                    } else {
//                        mapObject.put("index", jo.has("index") ? jo.getString("index") : "");
//                    }
//                    if (!jo.has("num") || null == jo.getString("num") || "null".equals(jo.getString("num"))
//                            || "".equals(jo.getString("num"))) {
//                        mapObject.put("num", "");
//                    } else {
//                        mapObject.put("num", jo.has("num") ? jo.getString("num") : "");
//                    }
//
//                    // 新增对应icon图标的id 去 shopGroupList中的id做对比
//                    if (!jo.has("icon") || null == jo.getString("icon") || "null".equals(jo.getString("icon"))
//                            || "".equals(jo.getString("icon"))) {
//                        mapObject.put("icon", "");
//                    } else {
//                        mapObject.put("icon", jo.has("icon") ? jo.getString("icon") : "");
//                    }
//
//                    //夺宝任务奖励名称
//                    if (!jo.has("type_name") || null == jo.getString("type_name") || "null".equals(jo.getString("type_name"))
//                            || "".equals(jo.getString("type_name"))) {
//                        mapObject.put("type_name", "苹果7");
//                    } else {
//                        mapObject.put("type_name", jo.has("type_name") ? jo.getString("type_name") : "苹果7");
//                    }
//
//                    TXsurpriseTaskList.add(mapObject); // 添加惊喜任务--提现
//
//                } else if (task_class.equals("5")) {
//
//                    mapObject.put("task_class", "5");
//
//                    if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id"))
//                            || "".equals(jo.getString("t_id"))) {
//                        mapObject.put("t_id", "4");
//                    } else {
//                        mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "4");
//                    }
//                    if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
//                            || "".equals(jo.getString("value"))) {
//                        mapObject.put("value", "0");
//                    } else {
//                        mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
//                    }
//                    if (!jo.has("task_type") || null == jo.getString("task_type")
//                            || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
//                        mapObject.put("task_type", "");
//                    } else {
//                        mapObject.put("task_type", jo.has("task_type") ? jo.getString("task_type") : "");
//                    }
//                    if (!jo.has("index") || null == jo.getString("index") || "null".equals(jo.getString("index"))
//                            || "".equals(jo.getString("index"))) {
//                        mapObject.put("index", "");
//                    } else {
//                        mapObject.put("index", jo.has("index") ? jo.getString("index") : "");
//                    }
//                    if (!jo.has("num") || null == jo.getString("num") || "null".equals(jo.getString("num"))
//                            || "".equals(jo.getString("num"))) {
//                        mapObject.put("num", "");
//                    } else {
//                        mapObject.put("num", jo.has("num") ? jo.getString("num") : "");
//                    }
//
//                    // 新增对应icon图标的id 去 shopGroupList中的id做对比
//                    if (!jo.has("icon") || null == jo.getString("icon") || "null".equals(jo.getString("icon"))
//                            || "".equals(jo.getString("icon"))) {
//                        mapObject.put("icon", "");
//                    } else {
//                        mapObject.put("icon", jo.has("icon") ? jo.getString("icon") : "");
//                    }
//
//                    //夺宝任务奖励名称
//                    if (!jo.has("type_name") || null == jo.getString("type_name") || "null".equals(jo.getString("type_name"))
//                            || "".equals(jo.getString("type_name"))) {
//                        mapObject.put("type_name", "苹果7");
//                    } else {
//                        mapObject.put("type_name", jo.has("type_name") ? jo.getString("type_name") : "苹果7");
//                    }
//
//
//                    jizanTastList.add(mapObject); // 添加惊喜任务--提现
//
//                } else if (task_class.equals("6")) {//夺宝
//
//
//                    mapObject.put("task_class", "6");
//
//                    if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id"))
//                            || "".equals(jo.getString("t_id"))) {
//                        mapObject.put("t_id", "4");
//                    } else {
//                        mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "4");
//                    }
//                    if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
//                            || "".equals(jo.getString("value"))) {
//                        mapObject.put("value", "0");
//                    } else {
//                        mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
//                    }
//                    if (!jo.has("task_type") || null == jo.getString("task_type")
//                            || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
//                        mapObject.put("task_type", "");
//                    } else {
//                        mapObject.put("task_type", jo.has("task_type") ? jo.getString("task_type") : "");
//                    }
//                    if (!jo.has("index") || null == jo.getString("index") || "null".equals(jo.getString("index"))
//                            || "".equals(jo.getString("index"))) {
//                        mapObject.put("index", "");
//                    } else {
//                        mapObject.put("index", jo.has("index") ? jo.getString("index") : "");
//                    }
//                    if (!jo.has("num") || null == jo.getString("num") || "null".equals(jo.getString("num"))
//                            || "".equals(jo.getString("num"))) {
//                        mapObject.put("num", "");
//                    } else {
//                        mapObject.put("num", jo.has("num") ? jo.getString("num") : "");
//                    }
//
//                    // 新增对应icon图标的id 去 shopGroupList中的id做对比
//                    if (!jo.has("icon") || null == jo.getString("icon") || "null".equals(jo.getString("icon"))
//                            || "".equals(jo.getString("icon"))) {
//                        mapObject.put("icon", "");
//                    } else {
//                        mapObject.put("icon", jo.has("icon") ? jo.getString("icon") : "");
//                    }
//                    //夺宝任务奖励名称
//                    if (!jo.has("type_name") || null == jo.getString("type_name") || "null".equals(jo.getString("type_name"))
//                            || "".equals(jo.getString("type_name"))) {
//                        mapObject.put("type_name", "苹果7");
//                    } else {
//                        mapObject.put("type_name", jo.has("type_name") ? jo.getString("type_name") : "苹果7");
//                    }
//                    duoBaoTaskList.add(mapObject); // 夺宝任务列表
//
//                }

            }
            maplist.put("daytaskList", daytaskList);
            maplist.put("othertaskList", othertaskList);
            maplist.put("surpriseTaskList", surpriseTaskList);
            maplist.put("txsurprisetasklist", TXsurpriseTaskList);
            maplist.put("jizantasklist", jizanTastList);
            maplist.put("duoBaoTaskList", duoBaoTaskList);

            // 明日任务列表 明日列表中有task_class但是只做展示，所以不区分
            List<HashMap<String, Object>> tomorrowTaskList = new ArrayList<HashMap<String, Object>>();

            JSONArray jsontomorrowTaskList = new JSONArray(j.getString("tomorrowTaskList"));

            if (null == jsontomorrowTaskList || "".equals(jsontomorrowTaskList)) {
                return null;
            }

            for (int i = 0; i < jsontomorrowTaskList.length(); i++) {
                JSONObject jo = (JSONObject) jsontomorrowTaskList.opt(i);
                HashMap<String, Object> mapObjectTT = new HashMap<String, Object>();
                if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id"))
                        || "".equals(jo.getString("t_id"))) {
                    mapObjectTT.put("t_id", "4");
                } else {
                    mapObjectTT.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "");
                }
                if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
                        || "".equals(jo.getString("value"))) { // 此value指的是去干什么用的value（每日任务是不能点击的，应该是用不上）
                    mapObjectTT.put("value", "0");
                } else {
                    mapObjectTT.put("value", jo.has("value") ? jo.getString("value") : "0");
                }

                if (!jo.has("task_type") || null == jo.getString("task_type")
                        || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
                    mapObjectTT.put("task_type", "");
                } else {
                    mapObjectTT.put("task_type", jo.has("task_type") ? jo.getString("task_type") : "");
                }

                if (!jo.has("index") || null == jo.getString("index") || "null".equals(jo.getString("index"))
                        || "".equals(jo.getString("index"))) {
                    mapObjectTT.put("index", "");
                } else {
                    mapObjectTT.put("index", jo.has("index") ? jo.getString("index") : "");
                }

                if (!jo.has("num") || null == jo.getString("num") || "null".equals(jo.getString("num"))
                        || "".equals(jo.getString("num"))) {
                    mapObjectTT.put("num", "");
                } else {
                    mapObjectTT.put("num", jo.has("num") ? jo.getString("num") : "");
                }
                if (!jo.has("task_class") || null == jo.getString("task_class")
                        || "null".equals(jo.getString("task_class")) || "".equals(jo.getString("task_class"))) {
                    mapObjectTT.put("task_class", "");
                } else {
                    mapObjectTT.put("task_class", jo.has("task_class") ? jo.getString("task_class") : "");
                }

                tomorrowTaskList.add(mapObjectTT);
            }
            maplist.put("tomorrowTaskList", tomorrowTaskList);

            // 任务列表。。用来取出ID作对比用
            JSONArray jsonArray = new JSONArray(j.getString("taskList"));
            if (null == jsonArray || "".equals(jsonArray)) {
                return null;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = (JSONObject) jsonArray.opt(i);
                HashMap<String, Object> mapObject = new HashMap<String, Object>();
                if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id"))
                        || "".equals(jo.getString("t_id"))) {
                    mapObject.put("t_id", "4");
                } else {
                    mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "");
                }
                if (!jo.has("t_name") || null == jo.getString("t_name") || "null".equals(jo.getString("t_name"))
                        || "".equals(jo.getString("t_name"))) {
                    mapObject.put("t_name", "");
                } else {
                    mapObject.put("t_name", jo.has("t_name") ? jo.getString("t_name") : "");
                }
                if (!jo.has("type_id") || null == jo.getString("type_id") || "null".equals(jo.getString("type_id"))
                        || "".equals(jo.getString("type_id"))) {
                    mapObject.put("type_id", "");
                } else {
                    mapObject.put("type_id", jo.has("type_id") ? jo.getString("type_id") : "");
                }
                if (!jo.has("add_time") || null == jo.getString("add_time") || "null".equals(jo.getString("add_time"))
                        || "".equals(jo.getString("add_time"))) {
                    mapObject.put("add_time", "0");
                } else {
                    mapObject.put("add_time", jo.has("add_time") ? jo.getString("add_time") : "0");
                }
                if (!jo.has("ISODate") || null == jo.getString("ISODate") || "null".equals(jo.getString("ISODate"))
                        || "".equals(jo.getString("ISODate"))) {
                    mapObject.put("ISODate", "");
                } else {
                    mapObject.put("ISODate", jo.has("ISODate") ? jo.getString("ISODate") : "");
                }
                if (!jo.has("is_show") || null == jo.getString("is_show") || "null".equals(jo.getString("is_show"))
                        || "".equals(jo.getString("is_show"))) {
                    mapObject.put("is_show", "");
                } else {
                    mapObject.put("is_show", jo.has("is_show") ? jo.getString("is_show") : "");
                }
                if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
                        || "".equals(jo.getString("value"))) {// 此处value指的是得到奖励数量
                    mapObject.put("value", "0");
                } else {
                    mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
                }
                if (!jo.has("condition") || null == jo.getString("condition")
                        || "null".equals(jo.getString("condition")) || "".equals(jo.getString("condition"))) {
                    mapObject.put("condition", "");
                } else {
                    mapObject.put("condition", jo.has("condition") ? jo.getString("condition") : "");
                }
                taskList.add(mapObject);
            }

            maplist.put("taskList", taskList);

            // 任务类型列表

            List<HashMap<String, Object>> taskTypeList = new ArrayList<HashMap<String, Object>>();

            JSONArray jsonArraytaskTypeList = new JSONArray(j.getString("taskTypeList"));
            if (null == jsonArraytaskTypeList || "".equals(jsonArraytaskTypeList)) {
                return null;
            }
            for (int i = 0; i < jsonArraytaskTypeList.length(); i++) {
                JSONObject jo = (JSONObject) jsonArraytaskTypeList.opt(i);
                HashMap<String, Object> mapObject = new HashMap<String, Object>();
                if (!jo.has("id") || null == jo.getString("id") || "null".equals(jo.getString("id"))
                        || "".equals(jo.getString("id"))) {
                    mapObject.put("id", "4");
                } else {
                    mapObject.put("id", jo.has("id") ? jo.getString("id") : "");
                }
                if (!jo.has("t_name") || null == jo.getString("t_name") || "null".equals(jo.getString("t_name"))
                        || "".equals(jo.getString("t_name"))) {
                    mapObject.put("t_name", "");
                } else {
                    mapObject.put("t_name", jo.has("t_name") ? jo.getString("t_name") : "");
                }
                if (!jo.has("type_id") || null == jo.getString("type_id") || "null".equals(jo.getString("type_id"))
                        || "".equals(jo.getString("type_id"))) {
                    mapObject.put("type_id", "");
                } else {
                    mapObject.put("type_id", jo.has("type_id") ? jo.getString("type_id") : "");
                }
                if (!jo.has("add_time") || null == jo.getString("add_time") || "null".equals(jo.getString("add_time"))
                        || "".equals(jo.getString("add_time"))) {
                    mapObject.put("add_time", "0");
                } else {
                    mapObject.put("add_time", jo.has("add_time") ? jo.getString("add_time") : "0");
                }
                if (!jo.has("ISODate") || null == jo.getString("ISODate") || "null".equals(jo.getString("ISODate"))
                        || "".equals(jo.getString("ISODate"))) {
                    mapObject.put("ISODate", "");
                } else {
                    mapObject.put("ISODate", jo.has("ISODate") ? jo.getString("ISODate") : "");
                }
                if (!jo.has("is_show") || null == jo.getString("is_show") || "null".equals(jo.getString("is_show"))
                        || "".equals(jo.getString("is_show"))) {
                    mapObject.put("is_show", "");
                } else {
                    mapObject.put("is_show", jo.has("is_show") ? jo.getString("is_show") : "");
                }
                taskTypeList.add(mapObject);

            }

            maplist.put("taskTypeList", taskTypeList);

            JSONArray jsondaytaskiconList = new JSONArray(j.getString("shopGroupList"));// 完成任务的图标
            for (int i = 0; i < jsondaytaskiconList.length(); i++) {
                JSONObject jo = (JSONObject) jsondaytaskiconList.opt(i);
                HashMap<String, Object> mapObjectTT = new HashMap<String, Object>();

                if (!jo.has("id") || null == jo.getString("id") || "null".equals(jo.getString("id"))
                        || "".equals(jo.getString("id"))) {
                    mapObjectTT.put("id", "");
                } else {
                    mapObjectTT.put("id", jo.has("id") ? jo.getString("id") : "");
                }
                if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
                        || "".equals(jo.getString("value"))) {
                    mapObjectTT.put("value", "");
                } else {
                    mapObjectTT.put("value", jo.has("value") ? jo.getString("value") : "");
                }
                if (!jo.has("icon") || null == jo.getString("icon") || "null".equals(jo.getString("icon"))
                        || "".equals(jo.getString("icon"))) {
                    mapObjectTT.put("icon", "");
                } else {
                    mapObjectTT.put("icon", jo.has("icon") ? jo.getString("icon") : "");
                }
                // 任务名称
                if (!jo.has("app_name") || null == jo.getString("app_name") || "null".equals(jo.getString("app_name"))
                        || "".equals(jo.getString("app_name"))) {
                    mapObjectTT.put("app_name", "");
                } else {
                    mapObjectTT.put("app_name", jo.has("app_name") ? jo.getString("app_name") : "");
                }

                taskiconList.add(mapObjectTT);
            }

            maplist.put("taskiconList", taskiconList);

            // HashMap<String, List<HashMap<String, Object>>>
            List<HashMap<String, Object>> modayList = new ArrayList<HashMap<String, Object>>();
            HashMap<String, Object> mapModay = new HashMap<String, Object>();
            if (!j.has("monday_info") || null == j.getString("monday_info") || "null".equals(j.getString("monday_info"))
                    || "".equals(j.getString("monday_info"))) {
                mapModay.put("value", "");
            } else {
                JSONObject joModay = (JSONObject) j.optJSONObject("monday_info");
                if (!joModay.has("value") || null == joModay.getString("value")
                        || "null".equals(joModay.getString("value")) || "".equals(joModay.getString("value"))) {
                    mapModay.put("value", "");
                } else {
                    mapModay.put("value", joModay.has("value") ? joModay.getString("value") : "");
                }
            }
            modayList.add(mapModay);
            maplist.put("monday_info", modayList);
            return maplist;

        }

        return null;
    }

    public static String signDay = "";

    // 已完成任务列表
    public static final HashMap<String, List<HashMap<String, Object>>> createSignYetList(Context context, String result)
            throws JSONException {

        /**
         *
         * {"taskList":[{"index_id":0,"t_id":7,"task_type":0,"task_class":1,
         * "status":0}],"message":"操作成功.","status":"1"}
         *
         */
        HashMap<String, List<HashMap<String, Object>>> maplist = new HashMap<String, List<HashMap<String, Object>>>();

        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (1 == j.getInt("status")) {
            signDay = j.getString("day");
            // 当天必做任务
            List<HashMap<String, Object>> daytaskListYet = new ArrayList<HashMap<String, Object>>();
            // 当天额外任务
            List<HashMap<String, Object>> othertaskListYet = new ArrayList<HashMap<String, Object>>();

            // 当天惊喜任务
            List<HashMap<String, Object>> surpriseTaskListYet = new ArrayList<HashMap<String, Object>>();
            // 当天提现惊喜任务
            List<HashMap<String, Object>> TXsurpriseTaskListYet = new ArrayList<HashMap<String, Object>>();
            // 当天集赞任务完成
            List<HashMap<String, Object>> jizanTaltListYet = new ArrayList<HashMap<String, Object>>();
            //当天夺宝任务完成
            List<HashMap<String, Object>> duoBaoTaskListYet = new ArrayList<HashMap<String, Object>>();

            String task_class = "";

            JSONArray jsondaytaskList = new JSONArray(j.getString("taskList"));// 包含必做任务和额外任务
            if (null == jsondaytaskList || "".equals(jsondaytaskList)) {
                return null;
            }

            for (int i = 0; i < jsondaytaskList.length(); i++) {
                JSONObject jo = (JSONObject) jsondaytaskList.opt(i);
                HashMap<String, Object> mapObject = new HashMap<String, Object>();

                if (!jo.has("task_class") || null == jo.getString("task_class")
                        || "null".equals(jo.getString("task_class")) || "".equals(jo.getString("task_class"))) {
                    return null;
                } else {
                    task_class = jo.getString("task_class");
                }


                int taskClass = 5;
                try {
                    taskClass = Integer.parseInt(task_class);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


                // 添加完成状态
                mapObject.put("signStatus", "1"); // 0表示未完成，1表示已完成

                if (!jo.has("status") || null == jo.getString("status") || "null".equals(jo.getString("status")) // 签到完成状态
                        || "".equals(jo.getString("status"))) {
                    mapObject.put("status", "");
                } else {
                    mapObject.put("status", jo.has("status") ? jo.getString("status") : "");
                }


                if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id")) // 奖励ID
                        || "".equals(jo.getString("t_id"))) {
                    mapObject.put("t_id", "4");
                } else {
                    mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "4");
                }
                if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
                        || "".equals(jo.getString("value"))) {
                    mapObject.put("value", "0"); // 此value指的是去干什么用的value
                } else {
                    mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
                }
                if (!jo.has("task_type") || null == jo.getString("task_type")
                        || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
                    mapObject.put("task_type", "");
                } else {
                    mapObject.put("task_type", jo.has("task_type") ? jo.getString("task_type") : "");
                }
                if (!jo.has("index_id") || null == jo.getString("index_id")
                        || "null".equals(jo.getString("index_id")) || "".equals(jo.getString("index_id"))) {
                    mapObject.put("index_id", "");
                } else {
                    mapObject.put("index_id", jo.has("index_id") ? jo.getString("index_id") : "");
                }
                if (!jo.has("task_class") || null == jo.getString("task_class")
                        || "null".equals(jo.getString("task_class")) || "".equals(jo.getString("task_class"))) {
                    mapObject.put("task_class", "");
                } else {
                    mapObject.put("task_class", jo.has("task_class") ? jo.getString("task_class") : "");
                }


                switch (taskClass) {
                    case 1:
                        daytaskListYet.add(mapObject); // 添加每日必做任务
                        break;
                    case 2:
                        othertaskListYet.add(mapObject); // 添加每日额外任务
                        break;
                    case 3:
                        surpriseTaskListYet.add(mapObject); // 添加惊喜任务
                        break;
                    case 4:
                        TXsurpriseTaskListYet.add(mapObject); // 添加惊喜任务--提现
                        break;
                    case 5:
                        jizanTaltListYet.add(mapObject); // 添加惊喜任务--集赞
                        break;
                    case 6:
                        duoBaoTaskListYet.add(mapObject); //夺宝-----现在叫超级惊喜任务
                        break;
                    default:
                        break;
                }


                // 判断task_class填充必做任务和额外任务
//                if (task_class.equals("1")) {
//                    // 必做任务
//
//                    // 添加完成状态
//                    mapObject.put("signStatus", "1"); // 0表示未完成，1表示已完成
//
//                    if (!jo.has("status") || null == jo.getString("status") || "null".equals(jo.getString("status")) // 签到完成状态
//                            || "".equals(jo.getString("status"))) {
//                        mapObject.put("status", "");
//                    } else {
//                        mapObject.put("status", jo.has("status") ? jo.getString("status") : "");
//                    }
//
//                    if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id")) // 奖励ID
//                            || "".equals(jo.getString("t_id"))) {
//                        mapObject.put("t_id", "4");
//                    } else {
//                        mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "4");
//                    }
//                    if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
//                            || "".equals(jo.getString("value"))) {
//                        mapObject.put("value", "0"); // 此value指的是去干什么用的value
//                    } else {
//                        mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
//                    }
//                    if (!jo.has("task_type") || null == jo.getString("task_type")
//                            || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
//                        mapObject.put("task_type", "");
//                    } else {
//                        mapObject.put("task_type", jo.has("task_type") ? jo.getString("task_type") : "");
//                    }
//                    if (!jo.has("index_id") || null == jo.getString("index_id")
//                            || "null".equals(jo.getString("index_id")) || "".equals(jo.getString("index_id"))) {
//                        mapObject.put("index_id", "");
//                    } else {
//                        mapObject.put("index_id", jo.has("index_id") ? jo.getString("index_id") : "");
//                    }
//                    if (!jo.has("task_class") || null == jo.getString("task_class")
//                            || "null".equals(jo.getString("task_class")) || "".equals(jo.getString("task_class"))) {
//                        mapObject.put("task_class", "");
//                    } else {
//                        mapObject.put("task_class", jo.has("task_class") ? jo.getString("task_class") : "");
//                    }
//
//                    // if (!jo.has("num") || null == jo.getString("num") ||
//                    // "null".equals(jo.getString("num"))
//                    // || "".equals(jo.getString("num"))) {
//                    // mapObject.put("num", "");
//                    // } else {
//                    // mapObject.put("num", jo.has("num") ? jo.getString("num")
//                    // : "");
//                    // }
//
//                    daytaskListYet.add(mapObject); // 添加每日必做任务
//
//
//                } else if (task_class.equals("2")) { // 额外任务
//                    // 添加完成状态
//                    mapObject.put("signStatus", "1"); // 0表示未完成，1表示已完成
//
//                    if (!jo.has("status") || null == jo.getString("status") || "null".equals(jo.getString("status")) // 签到完成状态
//                            || "".equals(jo.getString("status"))) {
//                        mapObject.put("status", "");
//                    } else {
//                        mapObject.put("status", jo.has("status") ? jo.getString("status") : "");
//                    }
//
//                    if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id"))
//                            || "".equals(jo.getString("t_id"))) {
//                        mapObject.put("t_id", "4");
//                    } else {
//                        mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "4");
//                    }
//                    if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
//                            || "".equals(jo.getString("value"))) {
//                        mapObject.put("value", "0");
//                    } else {
//                        mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
//                    }
//                    if (!jo.has("task_type") || null == jo.getString("task_type")
//                            || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
//                        mapObject.put("task_type", "");
//                    } else {
//                        mapObject.put("task_type", jo.has("task_type") ? jo.getString("task_type") : "");
//                    }
//                    if (!jo.has("index_id") || null == jo.getString("index_id")
//                            || "null".equals(jo.getString("index_id")) || "".equals(jo.getString("index_id"))) {
//                        mapObject.put("index_id", "");
//                    } else {
//                        mapObject.put("index_id", jo.has("index_id") ? jo.getString("index_id") : "");
//                    }
//
//                    if (!jo.has("task_class") || null == jo.getString("task_class")
//                            || "null".equals(jo.getString("task_class")) || "".equals(jo.getString("task_class"))) {
//                        mapObject.put("task_class", "");
//                    } else {
//                        mapObject.put("task_class", jo.has("task_class") ? jo.getString("task_class") : "");
//                    }
//
//                    othertaskListYet.add(mapObject); // 添加每日额外任务\\
//
//
//                } else if (task_class.equals("3")) { // 惊喜任务
//
//                    // 添加完成状态
//                    mapObject.put("signStatus", "1"); // 0表示未完成，1表示已完成
//
//                    if (!jo.has("status") || null == jo.getString("status") || "null".equals(jo.getString("status")) // 签到完成状态
//                            || "".equals(jo.getString("status"))) {
//                        mapObject.put("status", "");
//                    } else {
//                        mapObject.put("status", jo.has("status") ? jo.getString("status") : "");
//                    }
//
//                    if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id"))
//                            || "".equals(jo.getString("t_id"))) {
//                        mapObject.put("t_id", "4");
//                    } else {
//                        mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "4");
//                    }
//                    if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
//                            || "".equals(jo.getString("value"))) {
//                        mapObject.put("value", "0");
//                    } else {
//                        mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
//                    }
//                    if (!jo.has("task_type") || null == jo.getString("task_type")
//                            || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
//                        mapObject.put("task_type", "");
//                    } else {
//                        mapObject.put("task_type", jo.has("task_type") ? jo.getString("task_type") : "");
//                    }
//                    if (!jo.has("index_id") || null == jo.getString("index_id")
//                            || "null".equals(jo.getString("index_id")) || "".equals(jo.getString("index_id"))) {
//                        mapObject.put("index_id", "");
//                    } else {
//                        mapObject.put("index_id", jo.has("index_id") ? jo.getString("index_id") : "");
//                    }
//
//                    if (!jo.has("task_class") || null == jo.getString("task_class")
//                            || "null".equals(jo.getString("task_class")) || "".equals(jo.getString("task_class"))) {
//                        mapObject.put("task_class", "");
//                    } else {
//                        mapObject.put("task_class", jo.has("task_class") ? jo.getString("task_class") : "");
//                    }
//
//                    surpriseTaskListYet.add(mapObject); // 添加惊喜已完成的任务
//
//
//                } else if (task_class.equals("4")) {
//
//                    // 添加完成状态
//                    mapObject.put("signStatus", "1"); // 0表示未完成，1表示已完成
//
//                    if (!jo.has("status") || null == jo.getString("status") || "null".equals(jo.getString("status")) // 签到完成状态
//                            || "".equals(jo.getString("status"))) {
//                        mapObject.put("status", "");
//                    } else {
//                        mapObject.put("status", jo.has("status") ? jo.getString("status") : "");
//                    }
//
//                    if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id"))
//                            || "".equals(jo.getString("t_id"))) {
//                        mapObject.put("t_id", "4");
//                    } else {
//                        mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "4");
//                    }
//                    if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
//                            || "".equals(jo.getString("value"))) {
//                        mapObject.put("value", "0");
//                    } else {
//                        mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
//                    }
//                    if (!jo.has("task_type") || null == jo.getString("task_type")
//                            || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
//                        mapObject.put("task_type", "");
//                    } else {
//                        mapObject.put("task_type", jo.has("task_type") ? jo.getString("task_type") : "");
//                    }
//                    if (!jo.has("index_id") || null == jo.getString("index_id")
//                            || "null".equals(jo.getString("index_id")) || "".equals(jo.getString("index_id"))) {
//                        mapObject.put("index_id", "");
//                    } else {
//                        mapObject.put("index_id", jo.has("index_id") ? jo.getString("index_id") : "");
//                    }
//
//                    if (!jo.has("task_class") || null == jo.getString("task_class")
//                            || "null".equals(jo.getString("task_class")) || "".equals(jo.getString("task_class"))) {
//                        mapObject.put("task_class", "");
//                    } else {
//                        mapObject.put("task_class", jo.has("task_class") ? jo.getString("task_class") : "");
//                    }
//
//                    TXsurpriseTaskListYet.add(mapObject); // 添加提现任务已完成的任务
//
//
//                } else if (task_class.equals("5")) {
//                    // 添加完成状态
//                    mapObject.put("signStatus", "1"); // 0表示未完成，1表示已完成
//
//                    if (!jo.has("status") || null == jo.getString("status") || "null".equals(jo.getString("status")) // 签到完成状态
//                            || "".equals(jo.getString("status"))) {
//                        mapObject.put("status", "");
//                    } else {
//                        mapObject.put("status", jo.has("status") ? jo.getString("status") : "");
//                    }
//
//                    if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id"))
//                            || "".equals(jo.getString("t_id"))) {
//                        mapObject.put("t_id", "4");
//                    } else {
//                        mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "4");
//                    }
//                    if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
//                            || "".equals(jo.getString("value"))) {
//                        mapObject.put("value", "0");
//                    } else {
//                        mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
//                    }
//                    if (!jo.has("task_type") || null == jo.getString("task_type")
//                            || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
//                        mapObject.put("task_type", "");
//                    } else {
//                        mapObject.put("task_type", jo.has("task_type") ? jo.getString("task_type") : "");
//                    }
//                    if (!jo.has("index_id") || null == jo.getString("index_id")
//                            || "null".equals(jo.getString("index_id")) || "".equals(jo.getString("index_id"))) {
//                        mapObject.put("index_id", "");
//                    } else {
//                        mapObject.put("index_id", jo.has("index_id") ? jo.getString("index_id") : "");
//                    }
//
//                    if (!jo.has("task_class") || null == jo.getString("task_class")
//                            || "null".equals(jo.getString("task_class")) || "".equals(jo.getString("task_class"))) {
//                        mapObject.put("task_class", "");
//                    } else {
//                        mapObject.put("task_class", jo.has("task_class") ? jo.getString("task_class") : "");
//                    }
//
//                    jizanTaltListYet.add(mapObject); // 集赞任务
//
//
//                } else if (task_class.equals("6")) {
//                    // 添加完成状态
//                    mapObject.put("signStatus", "1"); // 0表示未完成，1表示已完成
//
//                    if (!jo.has("status") || null == jo.getString("status") || "null".equals(jo.getString("status")) // 签到完成状态
//                            || "".equals(jo.getString("status"))) {
//                        mapObject.put("status", "");
//                    } else {
//                        mapObject.put("status", jo.has("status") ? jo.getString("status") : "");
//                    }
//
//                    if (!jo.has("t_id") || null == jo.getString("t_id") || "null".equals(jo.getString("t_id"))
//                            || "".equals(jo.getString("t_id"))) {
//                        mapObject.put("t_id", "4");
//                    } else {
//                        mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "4");
//                    }
//                    if (!jo.has("value") || null == jo.getString("value") || "null".equals(jo.getString("value"))
//                            || "".equals(jo.getString("value"))) {
//                        mapObject.put("value", "0");
//                    } else {
//                        mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
//                    }
//                    if (!jo.has("task_type") || null == jo.getString("task_type")
//                            || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
//                        mapObject.put("task_type", "");
//                    } else {
//                        mapObject.put("task_type", jo.has("task_type") ? jo.getString("task_type") : "");
//                    }
//                    if (!jo.has("index_id") || null == jo.getString("index_id")
//                            || "null".equals(jo.getString("index_id")) || "".equals(jo.getString("index_id"))) {
//                        mapObject.put("index_id", "");
//                    } else {
//                        mapObject.put("index_id", jo.has("index_id") ? jo.getString("index_id") : "");
//                    }
//
//                    if (!jo.has("task_class") || null == jo.getString("task_class")
//                            || "null".equals(jo.getString("task_class")) || "".equals(jo.getString("task_class"))) {
//                        mapObject.put("task_class", "");
//                    } else {
//                        mapObject.put("task_class", jo.has("task_class") ? jo.getString("task_class") : "");
//                    }
//
//                    duoBaoTaskListYet.add(mapObject); // 添加夺宝任务完毕
//                }

            }
            maplist.put("daytaskListYet", daytaskListYet);
            maplist.put("othertaskListYet", othertaskListYet);
            maplist.put("surpriseTaskListYet", surpriseTaskListYet);
            maplist.put("txsurprisetasklistyet", TXsurpriseTaskListYet);
            maplist.put("jizantasklistyet", jizanTaltListYet);
            maplist.put("duoBaoTaskListYet", duoBaoTaskListYet);

            return maplist;
        }

        return null;

    }

    public static final HashMap<String, Object> createMyShopLink(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);

        LogYiFu.e("result555", result + "");

        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (1 == jsonObject.getInt("status")) {
            if (!jsonObject.has("message") || null == jsonObject.getString("message")
                    || "null".equals(jsonObject.getString("message")) || "".equals(jsonObject.getString("message"))) {
                mapRet.put("message", "");
            } else {
                mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
            }
            // context.getSharedPreferences("buqianka", Context.MODE_PRIVATE)
            // .edit()
            // .putBoolean(
            // "bool",
            // jsonObject.has("bool") ? jsonObject
            // .getBoolean("bool") : false).commit();
            // mapRet.put("bool",
            // jsonObject.has("bool") ? jsonObject.getString("bool") : "");
            if (!jsonObject.has("url") || null == jsonObject.getString("url")
                    || "null".equals(jsonObject.getString("url")) || "".equals(jsonObject.getString("url"))) {
                mapRet.put("url", "");
            } else {
                mapRet.put("url", jsonObject.has("url") ? jsonObject.getString("url") : "");
            }
            if (!jsonObject.has("pic") || null == jsonObject.getString("pic")
                    || "null".equals(jsonObject.getString("pic")) || "".equals(jsonObject.getString("pic"))) {
                mapRet.put("pic", "");
            } else {
                mapRet.put("pic", jsonObject.has("pic") ? jsonObject.getString("pic") : "");
            }

            return mapRet;
        }

        return null;

    }

    // /***
    // * 检查是否有补签卡
    // */
    // public static final HashMap<String, Object> createCheckbuqianka(
    // Context context, String result) throws Exception {
    // HashMap<String, Object> mapRet = new HashMap<String, Object>();
    // JSONObject jsonObject = new JSONObject(result);
    // if (null == jsonObject || "".equals(jsonObject)) {
    // return null;
    // }
    //
    // if (1 == jsonObject.getInt("status")) {
    // mapRet.put("message",
    // jsonObject.has("message") ? jsonObject.getString("message")
    // : "");
    // context.getSharedPreferences("buqianka", Context.MODE_PRIVATE)
    // .edit()
    // .putBoolean(
    // "bool",
    // jsonObject.has("bool") ? jsonObject
    // .getBoolean("bool") : false).commit();
    // mapRet.put("bool",
    // jsonObject.has("bool") ? jsonObject.getString("bool") : "");
    // return mapRet;
    // }
    //
    // return null;
    //
    // }

    /***
     * 签到
     */
    public static final HashMap<String, Object> createQiandao(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        // System.out.println("强制浏览------result="+result);
        if (1 == jsonObject.getInt("status")) {
            // SharedPreferencesUtil.saveBooleanData(context,
            // "signDATAneedRefresh", true);
            if (!jsonObject.has("message") || null == jsonObject.getString("message")
                    || "null".equals(jsonObject.getString("message")) || "".equals(jsonObject.getString("message"))) {
                mapRet.put("message", "");
            } else {
                mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
            }
            if (!jsonObject.has("changeTable") || null == jsonObject.getString("changeTable")
                    || "null".equals(jsonObject.getString("changeTable"))
                    || "".equals(jsonObject.getString("changeTable"))) {
                mapRet.put("changeTable", "");
            } else {
                mapRet.put("changeTable", jsonObject.has("changeTable") ? jsonObject.getBoolean("changeTable") : "");
            }
            // 签到任务中余额翻倍过期时间
            if (!jsonObject.has("t_time") || null == jsonObject.getString("t_time")
                    || "null".equals(jsonObject.getString("t_time")) || "".equals(jsonObject.getString("t_time"))) {
                mapRet.put("t_time", "-1");
            } else {
                mapRet.put("t_time", jsonObject.has("t_time") ? jsonObject.getString("t_time") : "-1");
            }


            mapRet.put("clock_in_status", jsonObject.optInt("clock_in_status"));
            mapRet.put("isNewbie01", jsonObject.optInt("isNewbie01"));


            return mapRet;
        }
        return null;

    }

    /***
     * 检查是否开店
     */
    public static final HashMap<String, Object> createCheckStore(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (1 == jsonObject.getInt("status")) {
            // false代表已开店
            if (!jsonObject.has("is_store") || null == jsonObject.getString("is_store")
                    || "null".equals(jsonObject.getString("is_store")) || "".equals(jsonObject.getString("is_store"))) {
                mapRet.put("is_store", "");
            } else {
                mapRet.put("is_store", jsonObject.has("is_store") ? jsonObject.getString("is_store") : "");
            }
            return mapRet;
        }
        return null;

    }

    /***
     * 选择客服
     */
    public static final HashMap<String, String> createChoiceKefu(Context context, String result) throws Exception {
        // HashMap<String, String> mapRet = new HashMap<String, String>();
        // JSONObject jsonObject = new JSONObject(result);
        //
        // if (null == jsonObject || "".equals(jsonObject)) {
        // return null;
        // }
        //
        // if (1 == jsonObject.getInt("status")) {
        // mapRet.put("id", jsonObject.has("id") ? jsonObject.getString("id")
        // : "");
        // return mapRet;
        // }

        HashMap<String, String> retInfo = new HashMap<String, String>();

        LogYiFu.e("Kr", result + "");
        JSONObject j = new JSONObject(result);

        if (j.getString("status").equals(1 + "")) {

            if (!j.has("id") || null == j.getString("id") || "null".equals(j.getString("id"))
                    || "".equals(j.getString("id"))) {
                retInfo.put("id", "914");
            } else {
                String data = j.getString("id");
                retInfo.put("id", data);
            }
        }

        SharedPreferencesUtil.saveStringData(context, "kefuNB", retInfo.get("id"));


        return retInfo;

    }

    /***
     * 获取用户等级表
     */
    public static final HashMap<String, String> createUserGradeTable(Context context, String result) throws Exception {
        HashMap<String, String> retInfo = new HashMap<String, String>();

        LogYiFu.e("Kr", result + "");
        JSONArray jsonarray = new JSONArray(result);
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject j = (JSONObject) jsonarray.opt(i);

            if (!j.has("0") || null == j.getString("0") || "null".equals(j.getString("0"))
                    || "".equals(j.getString("0"))) {
                retInfo.put("0", "0");
            } else {
                retInfo.put("0", j.getString("0"));
            }

            // -------------------------------------------------------------
            if (!j.has("1") || null == j.getString("1") || "null".equals(j.getString("1"))
                    || "".equals(j.getString("1"))) {
                retInfo.put("1", "40");
            } else {
                retInfo.put("1", j.getString("1"));
            }

            // -------------------------------------------------------------

            if (!j.has("2") || null == j.getString("2") || "null".equals(j.getString("2"))
                    || "".equals(j.getString("2"))) {
                retInfo.put("2", "100");
            } else {
                retInfo.put("2", j.getString("2"));
            }

            // -------------------------------------------------------------

            if (!j.has("3") || null == j.getString("3") || "null".equals(j.getString("3"))
                    || "".equals(j.getString("1"))) {
                retInfo.put("3", "300");
            } else {
                retInfo.put("3", j.getString("3"));
            }

        }
        return retInfo;

    }

    /***
     * 检查是否开店(用于5元红包弹窗)
     */
    public static final HashMap<String, Object> createfiveYuanDialog(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (1 == jsonObject.getInt("status")) {
            if (!jsonObject.has("isOpen") || null == jsonObject.getString("isOpen")
                    || "null".equals(jsonObject.getString("isOpen")) || "".equals(jsonObject.getString("isOpen"))) {
                mapRet.put("isOpen", "");
            } else {
                mapRet.put("isOpen", jsonObject.has("isOpen") ? jsonObject.getString("isOpen") : "");
            }
            return mapRet;
        }
        return null;

    }

    /**
     * 获取当前系统时间
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static final HashMap<String, Object> createSystemTime(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (1 == jsonObject.getInt("status")) {
            if (!jsonObject.has("now") || null == jsonObject.getString("now")
                    || "null".equals(jsonObject.getString("now")) || "".equals(jsonObject.getString("now"))) {
                mapRet.put("now", 0L);
            } else {
                mapRet.put("now", jsonObject.has("now") ? jsonObject.getLong("now") : "0");
            }
            return mapRet;
        }
        return null;

    }

    public static final HashMap<String, Object> createTXdetail(Context context, String result) throws Exception {
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }

        if (1 == j.getInt("status") && null != j.optString("data")) {
            JSONObject jo = new JSONObject(j.optString("data"));

            HashMap<String, Object> map = new HashMap<>();

            if (!jo.has("id") || null == jo.getString("id")) {
                map.put("id", 0);
            } else {

                map.put("id", jo.has("id") ? jo.optInt("id") : 0);
            }

            if (!jo.has("business_code") || null == jo.getString("business_code")) {
                map.put("business_code", 0);
            } else {

                map.put("business_code", jo.has("business_code") ? jo.optString("business_code") : 0);
            }

            if (!jo.has("add_date") || null == jo.getString("add_date") || jo.getString("add_date").equals("null")) {
                map.put("add_date", "0");
            } else {

                map.put("add_date", jo.has("add_date") ? jo.optString("add_date") : "0");
            }

            if (!jo.has("money") || null == jo.getString("money") || jo.getString("money").equals("null")) {
                map.put("money", 0);
            } else {

                map.put("money", jo.has("money") ? jo.optString("money") : 0);
            }

            if (!jo.has("collect_bank_name") || null == jo.getString("collect_bank_name")) {
                map.put("collect_bank_name", "");
            } else {

                map.put("collect_bank_name", jo.has("collect_bank_name") ? jo.optString("collect_bank_name") : "");
            }

            if (!jo.has("collect_name") || null == jo.getString("collect_name")) {
                map.put("collect_name", "");
            } else {

                map.put("collect_name", jo.has("collect_name") ? jo.optString("collect_name") : "");
            }

            if (!jo.has("collect_bank_code") || null == jo.getString("collect_bank_code")) {
                map.put("collect_bank_code", "");
            } else {

                map.put("collect_bank_code", jo.has("collect_bank_code") ? jo.optString("collect_bank_code") : "");
            }

            if (!jo.has("collect_phone") || null == jo.getString("collect_phone")) {
                map.put("collect_phone", "");
            } else {

                map.put("collect_phone", jo.has("collect_phone") ? jo.optString("collect_phone") : "");
            }

            if (!jo.has("user_id") || null == jo.getString("user_id")) {
                map.put("user_id", "");
            } else {

                map.put("user_id", jo.has("user_id") ? jo.optString("user_id") : "");
            }

            if (!jo.has("message") || null == jo.getString("message")) {
                map.put("message", "");
            } else {

                map.put("message", jo.has("message") ? jo.optString("message") : "");
            }

            if (!jo.has("check") || null == jo.getString("check")) {
                map.put("check", "");
            } else {

                map.put("check", jo.has("check") ? jo.optString("check") : "");
            }

            if (!jo.has("check_date") || null == jo.getString("check_date")) {
                map.put("check_date", "");
            } else {

                map.put("check_date", jo.has("check_date") ? jo.optString("check_date") : "");
            }

            if (!jo.has("check_code") || null == jo.getString("check_code")) {
                map.put("check_code", "");
            } else {

                map.put("check_code", jo.has("check_code") ? jo.optString("check_code") : "");
            }

            if (!jo.has("check_message") || null == jo.getString("check_message")) {
                map.put("check_message", "");
            } else {

                map.put("check_message", jo.has("check_message") ? jo.optString("check_message") : "");
            }

            if (!jo.has("fail_details") || null == jo.getString("fail_details")) {
                map.put("fail_details", "");
            } else {

                map.put("fail_details", jo.has("fail_details") ? jo.optString("fail_details") : "");
            }

            if (!jo.has("batch_no") || null == jo.getString("batch_no")) {
                map.put("batch_no", "");
            } else {

                map.put("batch_no", jo.has("batch_no") ? jo.optString("batch_no") : "");
            }

            if (!jo.has("transfer_error") || null == jo.getString("transfer_error")) {
                map.put("transfer_error", "");
            } else {

                map.put("transfer_error", jo.has("transfer_error") ? jo.optString("transfer_error") : "");
            }

            return map;

        }
        return null;

    }


    /**
     * 获取粉丝数和奖励
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static final HashMap<String, Object> createFansMoney(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (1 == jsonObject.getInt("status")) {
            if (!jsonObject.has("money") || null == jsonObject.getString("money")
                    || "null".equals(jsonObject.getString("money")) || "".equals(jsonObject.getString("money"))) {
                mapRet.put("money", 0.0);
            } else {
                mapRet.put("money", jsonObject.has("money") ? jsonObject.optDouble("money") : 0.0);
            }
            if (!jsonObject.has("fans") || null == jsonObject.getString("fans")
                    || "null".equals(jsonObject.getString("fans")) || "".equals(jsonObject.getString("fans"))) {
                mapRet.put("fans", 0);
            } else {
                mapRet.put("fans", jsonObject.has("fans") ? jsonObject.getInt("fans") : 0);
            }
            if (!jsonObject.has("remark") || null == jsonObject.getString("remark")
                    || "null".equals(jsonObject.getString("remark")) || "".equals(jsonObject.getString("remark"))) {
                mapRet.put("remark", "");
            } else {
                mapRet.put("remark", jsonObject.has("remark") ? jsonObject.getString("remark") : "");
            }
            return mapRet;
        }
        return null;

    }

    /**
     * 获取用户等级
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static final HashMap<String, Object> createUserGralde(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (1 == jsonObject.getInt("status")) {// 1==A类 2==B类…以此类推,目前只有两类 0
            // 还未区分a,b类用户
            if (!jsonObject.has("grade") || null == jsonObject.getString("grade")
                    || "null".equals(jsonObject.getString("grade")) || "".equals(jsonObject.getString("grade"))) {
                mapRet.put("grade", 1);
            } else {
                mapRet.put("grade", jsonObject.has("grade") ? jsonObject.getInt("grade") : 1);
            }
            if (!jsonObject.has("grade") || null == jsonObject.getString("grade")
                    || "null".equals(jsonObject.getString("grade")) || "".equals(jsonObject.getString("grade"))) {
                mapRet.put("grade", 1);
            } else {
                mapRet.put("grade", jsonObject.has("grade") ? jsonObject.getInt("grade") : 1);
            }
            if (!jsonObject.has("vitality") || null == jsonObject.getString("vitality")
                    || "null".equals(jsonObject.getString("vitality")) || "".equals(jsonObject.getString("vitality"))) {
                mapRet.put("vitality", 0);
            } else {
                mapRet.put("vitality", jsonObject.has("vitality") ? jsonObject.getInt("vitality") : 0);
            }
            if (!jsonObject.has("auseTwofold") || null == jsonObject.getString("auseTwofold")
                    || "null".equals(jsonObject.getString("auseTwofold"))
                    || "".equals(jsonObject.getString("auseTwofold"))) {
                mapRet.put("auseTwofold", 6);
            } else {
                mapRet.put("auseTwofold", jsonObject.has("auseTwofold") ? jsonObject.getInt("auseTwofold") : 6);
            }
            if (!jsonObject.has("buseTwofold") || null == jsonObject.getString("buseTwofold")
                    || "null".equals(jsonObject.getString("buseTwofold"))
                    || "".equals(jsonObject.getString("buseTwofold"))) {
                mapRet.put("buseTwofold", 6);
            } else {
                mapRet.put("buseTwofold", jsonObject.has("buseTwofold") ? jsonObject.getInt("buseTwofold") : 6);
            }
            return mapRet;
        }
        return null;

    }

    /**
     * 获取是否弹余额翻倍弹窗
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static final HashMap<String, Object> createIsDialog(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (1 == jsonObject.getInt("status")) {
            if (!jsonObject.has("balance") || null == jsonObject.getString("balance")
                    || "null".equals(jsonObject.getString("balance")) || "".equals(jsonObject.getString("balance"))) {
                mapRet.put("balance", 0.0);
            } else {
                mapRet.put("balance", jsonObject.has("balance") ? jsonObject.getDouble("balance") : 0.0);
            }
            if (!jsonObject.has("firstBlood") || null == jsonObject.getString("firstBlood")
                    || "null".equals(jsonObject.getString("firstBlood")) || "".equals(jsonObject.getString("firstBlood"))) {
                mapRet.put("firstBlood", true);
            } else {
                mapRet.put("firstBlood", jsonObject.has("firstBlood") ? jsonObject.getBoolean("firstBlood") : true);
            }
            if (!jsonObject.has("flag") || null == jsonObject.getString("flag")
                    || "null".equals(jsonObject.getString("flag")) || "".equals(jsonObject.getString("flag"))) {
                mapRet.put("flag", 0);
            } else {
                mapRet.put("flag", jsonObject.has("flag") ? jsonObject.getInt("flag") : 0);
            }
            if (!jsonObject.has("idcardFlag") || null == jsonObject.getString("idcardFlag")
                    || "null".equals(jsonObject.getString("idcardFlag"))
                    || "".equals(jsonObject.getString("idcardFlag"))) {
                mapRet.put("idcardFlag", 0);
            } else {
                mapRet.put("idcardFlag", jsonObject.has("idcardFlag") ? jsonObject.getInt("idcardFlag") : 0);
            }
            if (!jsonObject.has("grade") || null == jsonObject.getString("grade")
                    || "null".equals(jsonObject.getString("grade")) || "".equals(jsonObject.getString("grade"))) {
                mapRet.put("grade", 1);
            } else {
                mapRet.put("grade", jsonObject.has("grade") ? jsonObject.getInt("grade") : 1);
            }

            if (!jsonObject.has("vitality") || null == jsonObject.getString("vitality")
                    || "null".equals(jsonObject.getString("vitality")) || "".equals(jsonObject.getString("vitality"))) {
                mapRet.put("vitality", 0);
            } else {
                mapRet.put("vitality", jsonObject.has("vitality") ? jsonObject.getInt("vitality") : 0);
            }
            if (!jsonObject.has("lastBname") || null == jsonObject.getString("lastBname")
                    || "null".equals(jsonObject.getString("lastBname"))
                    || "".equals(jsonObject.getString("lastBname"))) {
                mapRet.put("lastBname", "");
            } else {
                mapRet.put("lastBname", jsonObject.has("lastBname") ? jsonObject.optString("lastBname") : "");
            }
            if (!jsonObject.has("extract") || null == jsonObject.getString("extract")
                    || "null".equals(jsonObject.getString("extract")) || "".equals(jsonObject.getString("extract"))) {
                mapRet.put("extract", 0.0);
            } else {
                mapRet.put("extract", jsonObject.has("extract") ? jsonObject.optDouble("extract") : 0.0);
            }
            if (!jsonObject.has("freeze_balance") || null == jsonObject.getString("freeze_balance") || jsonObject.getString("freeze_balance").equals("null")
                    || jsonObject.getString("freeze_balance").equals("")) {
                mapRet.put("freeze_balance", 0.0);
            } else {
                mapRet.put("freeze_balance", jsonObject.has("freeze_balance") ? jsonObject.optDouble("freeze_balance") : 0.0);// 增加 冻结金额
            }
            if (!jsonObject.has("ex_free") || null == jsonObject.getString("ex_free")
                    || "null".equals(jsonObject.getString("ex_free")) || "".equals(jsonObject.getString("ex_free"))) {// 冻结金额
                mapRet.put("ex_free", 0.0);
            } else {
                mapRet.put("ex_free", jsonObject.has("ex_free") ? jsonObject.optDouble("ex_free") : 0.0);
            }
            if (!jsonObject.has("kts") || null == jsonObject.getString("kts")
                    || "null".equals(jsonObject.getString("kts")) || "".equals(jsonObject.getString("kts"))) {// 可提现金额限制
                mapRet.put("kts", "30,50,100");
            } else {
                mapRet.put("kts", jsonObject.has("kts") ? jsonObject.optString("kts") : "30,50,100");
            }

            if (!jsonObject.has("minicill") || null == jsonObject.getString("minicill")
                    || "null".equals(jsonObject.getString("minicill")) || "".equals(jsonObject.getString("minicill"))) {// 最低提现
                mapRet.put("minicill", "5.0");
            } else {
                mapRet.put("minicill", jsonObject.has("minicill") ? jsonObject.optString("kts") : "5.0");
            }


            if (!jsonObject.has("isCanTX") || null == jsonObject.getString("isCanTX")
                    || "null".equals(jsonObject.getString("isCanTX")) || "".equals(jsonObject.getString("isCanTX"))) {// 是否可以提现
                mapRet.put("isCanTX", 0);
            } else {
                mapRet.put("isCanTX", jsonObject.has("isCanTX") ? jsonObject.optInt("isCanTX") : 0);
            }


            if (!jsonObject.has("dm") || null == jsonObject.getString("dm") || "null".equals(jsonObject.getString("dm"))
                    || "".equals(jsonObject.getString("dm"))) {
                mapRet.put("max", "0.0");
                mapRet.put("min", "0.0");
            } else {
                String text = jsonObject.getString("dm");
                JSONObject jo2 = new JSONObject(text);
                if (!jo2.has("max") || null == jo2.getString("max") || "null".equals(jo2.getString("max"))
                        || "".equals(jo2.getString("max"))) {
                    mapRet.put("max", "0.0");
                } else {
                    mapRet.put("max", jo2.has("max") ? jo2.optString("max") : "0.0");
                }
                if (!jo2.has("min") || null == jo2.getString("min") || "null".equals(jo2.getString("min"))
                        || "".equals(jo2.getString("min"))) {
                    mapRet.put("min", "0.0");
                } else {
                    mapRet.put("min", jo2.has("min") ? jo2.optString("min") : "0.0");
                }
                if (!jo2.has("oneMin") || null == jo2.getString("oneMin") || "null".equals(jo2.getString("oneMin"))
                        || "".equals(jo2.getString("oneMin"))) {
                    mapRet.put("oneMin", "20.0");
                } else {
                    mapRet.put("oneMin", jo2.has("oneMin") ? jo2.optString("oneMin") : "20.0");
                }
            }
            return mapRet;
        }
        return null;

    }

    /**
     * 获取当环信密码
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static final HashMap<String, Object> createHuanXinPassword(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (1 == jsonObject.getInt("status")) {
            if (!jsonObject.has("code") || null == jsonObject.getString("code")
                    || "".equals(jsonObject.getString("code"))) {
                mapRet.put("code", "");
            } else {
                mapRet.put("code", jsonObject.has("code") ? jsonObject.getString("code") : "");
            }
            return mapRet;
        }
        return null;

    }

    /**
     * 获取我的喜好
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static final String createMineLike(Context context, String result) throws Exception {
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (1 == jsonObject.getInt("status")) {
            String str = jsonObject.getString("codes");
            if (str.length() > 2) {
                str.replace("[", "");
                str.replace("]", "");
            }
            if (!jsonObject.has("codes") || null == jsonObject.getString("codes")
                    || "null".equals(jsonObject.getString("codes")) || "".equals(jsonObject.getString("codes"))) {
                return null;
            } else {
                return str;
            }

            // String[] strs = str.split(",");
            // HashSet<String> set = new HashSet<String>();
            // if (strs != null && strs.length > 0) {
            // for (int i = 0; i < strs.length; i++) {
            // set.add(strs[i]);
            // }
            // return set;
            // } else {
            // return null;
            // }
        } else {
            return null;
        }

    }

    /**
     * 获取PIC与时间
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static final HashMap<String, Object> createPic(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        if (!jsonObject.has("status") || null == jsonObject.getString("status")
                || "null".equals(jsonObject.getString("status")) || "".equals(jsonObject.getString("status"))) {
            return null;
        }
        if (1 == jsonObject.getInt("status")) {
            if (!jsonObject.has("data") || null == jsonObject.getString("data")
                    || "null".equals(jsonObject.getString("data")) || "".equals(jsonObject.getString("data"))) {
                return null;
            }
            String str = jsonObject.getString("data");
            JSONObject jsonObject2 = new JSONObject(str);
            // String pic = jsonObject2.getString("pic");
            if (!jsonObject2.has("pic") || null == jsonObject2.getString("pic")
                    || "null".equals(jsonObject2.getString("pic")) || "".equals(jsonObject2.getString("pic"))) {
                mapRet.put("pic", "");
            } else {
                mapRet.put("pic", jsonObject2.getString("pic"));
            }

            // String time = jsonObject2.getString("time");
            if (!jsonObject2.has("time") || null == jsonObject2.getString("time")
                    || "null".equals(jsonObject2.getString("time")) || "".equals(jsonObject2.getString("time"))) {
                mapRet.put("time", "0");
            } else {
                mapRet.put("time", jsonObject2.getString("time"));
            }

            return mapRet;
        }

        return null;

    }

    /**
     * 晒单点赞
     */
    public static final HashMap<String, Object> createAddClick(Context context, String result) throws Exception {

        // System.out.println("晒单点赞result=" + result);

        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        // mapRet.put("status", jsonObject.has("status") ?
        // jsonObject.getLong("status") : "");
        if (!jsonObject.has("message") || null == jsonObject.getString("message")
                || "null".equals(jsonObject.getString("message")) || "".equals(jsonObject.getString("message"))) {
            mapRet.put("message", "");
        } else {
            mapRet.put("message", jsonObject.getString("message"));
        }
        if (!jsonObject.has("status") || null == jsonObject.getString("status")
                || "null".equals(jsonObject.getString("status")) || "".equals(jsonObject.getString("status"))) {
            mapRet.put("status", "");
        } else {
            mapRet.put("status", jsonObject.getString("status"));
        }

        return mapRet;
    }

    /**
     * 夺宝——晒单
     */
    public static final List<HashMap<String, Object>> createDuoBao_ShaiDan(Context context, String result)
            throws Exception {
        // HashMap<String, Object> mapRet = new HashMap<String, Object>();
        List<HashMap<String, Object>> mapRet = new ArrayList<HashMap<String, Object>>();

        JSONObject jsonObject = new JSONObject(result);

        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(jsonObject.getString("comments"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }

        // //////////
        JSONArray array = new JSONArray(jsonObject.getString("shop_codes"));
        if (null == array || "".equals(array)) {
            return null;
        }
        List<String> list = new ArrayList<String>();

        // ////////////

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.opt(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();
            if (!jo.has("shop_code") || null == jo.getString("shop_code") || "null".equals(jo.getString("shop_code"))
                    || "".equals(jo.getString("shop_code"))) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");
            }
            if (!jo.has("user_id") || null == jo.getString("user_id") || "null".equals(jo.getString("user_id"))
                    || "".equals(jo.getString("user_id"))) {
                mapObject.put("user_id", "");
            } else {
                mapObject.put("user_id", jo.has("user_id") ? jo.getInt("user_id") : "");
            }
            if (!jo.has("user_name") || null == jo.getString("user_name") || "".equals(jo.getString("user_name"))) {
                mapObject.put("user_name", "");
            } else {
                mapObject.put("user_name", jo.has("user_name") ? jo.getString("user_name") : "");
            }
            if (!jo.has("user_url") || null == jo.getString("user_url") || "null".equals(jo.getString("user_url"))
                    || "".equals(jo.getString("user_url"))) {
                mapObject.put("user_url", "");
            } else {
                mapObject.put("user_url", jo.has("user_url") ? jo.getString("user_url") : "");
            }
            if (!jo.has("content") || null == jo.getString("content") || "null".equals(jo.getString("content"))
                    || "".equals(jo.getString("content"))) {
                mapObject.put("content", "");
            } else {
                mapObject.put("content", jo.has("content") ? jo.getString("content") : "");
            }
            if (!jo.has("pic") || null == jo.getString("pic") || "null".equals(jo.getString("pic"))
                    || "".equals(jo.getString("pic"))) {
                mapObject.put("pic", "");
            } else {
                mapObject.put("pic", jo.has("pic") ? jo.getString("pic") : "");
            }
            if (!jo.has("add_date") || null == jo.getString("add_date") || "null".equals(jo.getString("add_date"))
                    || "".equals(jo.getString("add_date"))) {
                mapObject.put("add_date", 0L);
            } else {
                mapObject.put("add_date", jo.has("add_date") ? jo.getLong("add_date") : "0");
            }

            if (!jo.has("comment_size") || null == jo.getString("comment_size")
                    || "null".equals(jo.getString("comment_size")) || "".equals(jo.getString("comment_size"))) {
                mapObject.put("comment_size", "");
            } else {
                mapObject.put("comment_size", jo.has("comment_size") ? jo.getString("comment_size") : "");
            }
            if (!jo.has("click_size") || null == jo.getString("click_size") || "null".equals(jo.getString("click_size"))
                    || "".equals(jo.getString("click_size"))) {
                mapObject.put("click_size", "");
            } else {
                mapObject.put("click_size", jo.has("click_size") ? jo.getString("click_size") : "");
            }

            if (array.length() == 0) { // 位避免空指针
                mapObject.put("shop_codes", "0");
            } else {
                for (int j = 0; j < array.length(); j++) {

                    if (j < array.length() && array.get(j).toString().equals(jo.getString("shop_code") + "")) {
                        mapObject.put("shop_codes", array.get(j).toString());
                        break; // 跳出本次的循环 避免走到最后一次
                    }

                    if (j == array.length() - 1) {
                        if (array.get(j).toString().equals(jo.getString("shop_code") + "")) {
                            mapObject.put("shop_codes", array.get(j).toString());
                        } else {
                            mapObject.put("shop_codes", "0.0");
                        }
                    }
                }
            }

            // mapObject.put("shop_codes",array.get(i).toString());

            mapRet.add(mapObject);
        }

        // ///////////////////////////////

        // JSONArray array = new JSONArray(jsonObject.getString("shop_codes"));
        // if (null == array || "".equals(array)) {
        // return null;
        // }
        // System.out.println("-------array="+array);
        //
        // List<String> list = new ArrayList<String>();
        // for (int i = 0; i < array.length(); i++) {
        // // JSONObject jo = (JSONObject) array.opt(i);
        // list.add(i, (String) array.get(i));
        // Object object = array.get(i);
        // }
        // System.out.println("???????????????list="+list);
        //
        //
        //
        // for (String shop_code : list) {
        // HashMap<String,Object> map = new HashMap<String,Object>();
        // map.put("shop_codes", shop_code);
        //
        // mapRet.add(map);
        // }
        // System.out.println("yes or no--mapRet="+mapRet);

        // //////////////////////////////////////

        // for (int i = 0; i < array.length(); i++) {
        // JSONObject jo = (JSONObject) array.opt(i);
        // List<String> list = new ArrayList<String>();
        //
        //
        // }
        return mapRet;

    }

    /**
     * 夺宝 往期揭晓——往期揭晓(往期揭晓左边的)
     */

    public static final List<HashMap<String, Object>> createWqjx_left(Context context, String result) throws Exception {
        // HashMap<String, Object> mapRet = new HashMap<String, Object>();
        // System.out.println("往期揭晓左边=" + result);
        List<HashMap<String, Object>> mapRet = new ArrayList<HashMap<String, Object>>();
        JSONObject jsonObject = new JSONObject(result);

        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.opt(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();
            if (!jo.has("shop_code") || null == jo.getString("shop_code") || "null".equals(jo.getString("shop_code"))
                    || "".equals(jo.getString("shop_code"))) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");
            }
            if (!jo.has("shop_name") || null == jo.getString("shop_name") || "null".equals(jo.getString("shop_name"))
                    || "".equals(jo.getString("shop_name"))) {
                mapObject.put("shop_name", "");
            } else {
                mapObject.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");
            }
            if (!jo.has("shop_pic") || null == jo.getString("shop_pic") || "null".equals(jo.getString("shop_pic"))
                    || "".equals(jo.getString("shop_pic"))) {
                mapObject.put("shop_pic", "");
            } else {
                mapObject.put("shop_pic", jo.has("shop_pic") ? jo.getString("shop_pic") : "");
            }
            if (!jo.has("in_code") || null == jo.getString("in_code") || "null".equals(jo.getString("in_code"))
                    || "".equals(jo.getString("in_code"))) {
                mapObject.put("in_code", "");
            } else {
                mapObject.put("in_code", jo.has("in_code") ? jo.getString("in_code") : "");
            }
            if (!jo.has("status") || null == jo.getString("status") || "null".equals(jo.getString("status"))
                    || "".equals(jo.getString("status"))) {
                mapObject.put("status", "");
            } else {
                mapObject.put("status", jo.has("status") ? jo.getInt("status") : "");
            }
            if (!jo.has("otime") || null == jo.getString("otime") || "null".equals(jo.getString("otime"))
                    || "".equals(jo.getString("otime"))) {
                mapObject.put("otime", 0L);
            } else {
                mapObject.put("otime", jo.has("otime") ? jo.getLong("otime") : 0L);
            }
            if (!jo.has("btime") || null == jo.getString("btime") || "null".equals(jo.getString("btime"))
                    || "".equals(jo.getString("btime"))) {
                mapObject.put("btime", 0L);
            } else {
                mapObject.put("btime", jo.has("btime") ? jo.getLong("btime") : 0L);
            }
            if (!jo.has("etime") || null == jo.getString("etime") || "null".equals(jo.getString("etime"))
                    || "".equals(jo.getString("etime"))) {
                mapObject.put("etime", 0L);
            } else {
                mapObject.put("etime", jo.has("etime") ? jo.getLong("etime") : 0L);
            }
            if (!jo.has("num") || null == jo.getString("num") || "null".equals(jo.getString("num"))
                    || "".equals(jo.getString("num"))) {
                mapObject.put("num", "0");
            } else {
                mapObject.put("num", jo.has("num") ? jo.getInt("num") : "0");
            }
            if (!jo.has("virtual_num") || null == jo.getString("virtual_num")
                    || "null".equals(jo.getString("virtual_num")) || "".equals(jo.getString("virtual_num"))) {
                mapObject.put("virtual_num", "0");
            } else {
                mapObject.put("virtual_num", jo.has("virtual_num") ? jo.getInt("virtual_num") : "0");
            }
            if (!jo.has("in_name") || null == jo.getString("in_name") || "".equals(jo.getString("in_name"))) {
                mapObject.put("in_name", "");
            } else {
                mapObject.put("in_name", jo.has("in_name") ? jo.getString("in_name") : "");
            }
            if (!jo.has("in_head") || null == jo.getString("in_head") || "null".equals(jo.getString("in_head"))
                    || "".equals(jo.getString("in_head"))) {
                mapObject.put("in_head", "");
            } else {
                mapObject.put("in_head", jo.has("in_head") ? jo.getString("in_head") : "");
            }
            if (!jo.has("issue_code") || null == jo.getString("issue_code") || "null".equals(jo.getString("issue_code"))
                    || "".equals(jo.getString("issue_code"))) {
                mapObject.put("issue_code", "");
            } else {
                mapObject.put("issue_code", jo.has("issue_code") ? jo.getString("issue_code") : "");
            }
            if (!jo.has("in_uid") || null == jo.getString("in_uid") || "null".equals(jo.getString("in_uid"))
                    || "".equals(jo.getString("in_uid"))) {
                mapObject.put("in_uid", "");
            } else {
                mapObject.put("in_uid", jo.has("in_uid") ? jo.getInt("in_uid") : "");
            }
            mapRet.add(mapObject);
        }
        return mapRet;

    }

    /**
     * 夺宝记录——我的参与记录
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static final List<HashMap<String, Object>>
    createSnatchJoin(Context context, String result)
            throws Exception {
        // HashMap<String, Object> mapRet = new HashMap<String, Object>();
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String,
                Object>>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        // System.out.println("aaaaaaaaaaaaaaa+result=" + result);
        JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.opt(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();

            if (!jo.has("shop_code") || null == jo.getString("shop_code") ||
                    "null".equals(jo.getString("shop_code"))
                    || "".equals(jo.getString("shop_code"))) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.has("shop_code") ?
                        jo.getString("shop_code") : "");
            }
            if (!jo.has("issue_code") || null == jo.getString("issue_code") ||
                    "null".equals(jo.getString("issue_code"))
                    || "".equals(jo.getString("issue_code"))) {
                mapObject.put("issue_code", "");
            } else {
                mapObject.put("issue_code", jo.has("issue_code") ?
                        jo.getString("issue_code") : "");
            }
            if (!jo.has("in_name") || null == jo.getString("in_name") ||
                    "".equals(jo.getString("in_name"))) {
                mapObject.put("in_name", "");
            } else {
                mapObject.put("in_name", jo.has("in_name") ? jo.getString("in_name") :
                        "");
            }
            if (!jo.has("in_head") || null == jo.getString("in_head") ||
                    "null".equals(jo.getString("in_head"))
                    || "".equals(jo.getString("in_head"))) {
                mapObject.put("in_head", "");
            } else {
                mapObject.put("in_head", jo.has("in_head") ? jo.getString("in_head") :
                        "");
            }
            // mapObject.put("in_head", jo.getString("in_head"));
            if (!jo.has("in_uid") || null == jo.getString("in_uid") ||
                    "null".equals(jo.getString("in_uid"))
                    || "".equals(jo.getString("in_uid"))) {
                mapObject.put("in_uid", "0");
            } else {
                mapObject.put("in_uid", jo.has("in_uid") ? jo.getInt("in_uid") : "0");
            }
            if (!jo.has("shop_name") || null == jo.getString("shop_name") ||
                    "null".equals(jo.getString("shop_name"))
                    || "".equals(jo.getString("shop_name"))) {
                mapObject.put("shop_name", "");
            } else {
                mapObject.put("shop_name", jo.has("shop_name") ?
                        jo.getString("shop_name") : "");
            }
            if (!jo.has("shop_pic") || null == jo.getString("shop_pic") ||
                    "null".equals(jo.getString("shop_pic"))
                    || "".equals(jo.getString("shop_pic"))) {
                mapObject.put("shop_pic", "");
            } else {
                mapObject.put("shop_pic", jo.has("shop_pic") ? jo.getString("shop_pic") :
                        "");
            }
            if (!jo.has("in_code") || null == jo.getString("in_code") ||
                    "null".equals(jo.getString("in_code"))
                    || "".equals(jo.getString("in_code"))) {
                mapObject.put("in_code", "");
            } else {
                mapObject.put("in_code", jo.has("in_code") ? jo.getString("in_code") :
                        "");
            }
            if (!jo.has("status") || null == jo.getString("status") ||
                    "null".equals(jo.getString("status"))
                    || "".equals(jo.getString("status"))) {
                mapObject.put("status", "");
            } else {
                mapObject.put("status", jo.has("status") ? jo.getInt("status") : "");
            }
            if (!jo.has("num") || null == jo.getString("num") ||
                    "null".equals(jo.getString("num"))
                    || "".equals(jo.getString("num"))) {
                mapObject.put("num", "0");
            } else {
                mapObject.put("num", jo.has("num") ? jo.getInt("num") : "0");
            }
            if (!jo.has("virtual_num") || null == jo.getString("virtual_num")
                    || "null".equals(jo.getString("virtual_num")) ||
                    "".equals(jo.getString("virtual_num"))) {
                mapObject.put("virtual_num", "0");
            } else {
                mapObject.put("virtual_num", jo.has("virtual_num") ?
                        jo.getInt("virtual_num") : "0");
            }
            if (!jo.has("otime") || null == jo.getString("otime") ||
                    "null".equals(jo.getString("otime"))
                    || "".equals(jo.getString("otime"))) {
                mapObject.put("otime", 0L);
            } else {
                mapObject.put("otime", jo.has("otime") ? jo.getLong("otime") : 0L); //

            }
            if (!jo.has("btime") || null == jo.getString("btime") ||
                    "null".equals(jo.getString("btime"))
                    || "".equals(jo.getString("btime"))) {
                mapObject.put("btime", 0L);
            } else {
                mapObject.put("btime", jo.has("btime") ? jo.getLong("btime") : 0L);
            }
            if (!jo.has("etime") || null == jo.getString("etime") ||
                    "null".equals(jo.getString("etime"))
                    || "".equals(jo.getString("etime"))) {
                mapObject.put("etime", 0L);
            } else {
                mapObject.put("etime", jo.has("etime") ? jo.getLong("etime") : 0L);
            }

            JSONArray jsonArray2 = new JSONArray(jo.has("user") ?
                    jo.getString("user") : "");
            DuoBaoJiLu_user user = new DuoBaoJiLu_user();
            for (int p = 0; p < jsonArray2.length(); p++) {
                JSONObject jo2 = (JSONObject) jsonArray2.get(p);
                HashMap<String, Object> map = new HashMap<String, Object>();

                // user.setNickname(jo2.getString("nickname"));
                if (!jo2.has("uid") || null == jo2.getString("uid") ||
                        "null".equals(jo2.getString("uid"))
                        || "".equals(jo2.getString("uid"))) {
                    map.put("uid", "0");
                } else {
                    map.put("uid", jo2.getInt("uid"));
                }
                if (!jo2.has("atime") || null == jo2.getString("atime") ||
                        "null".equals(jo2.getString("atime"))
                        || "".equals(jo2.getString("atime"))) {
                    map.put("atime", 0L);
                } else {
                    map.put("atime", jo2.getLong("atime"));
                }
                if (!jo2.has("uhead") || null == jo2.getString("uhead") ||
                        "null".equals(jo2.getString("uhead"))
                        || "".equals(jo2.getString("uhead"))) {
                    map.put("uhead", "");
                } else {
                    map.put("uhead", jo2.getString("uhead"));
                }
                if (!jo2.has("num") || null == jo2.getString("num") ||
                        "null".equals(jo2.getString("num"))
                        || "".equals(jo2.getString("num"))) {
                    map.put("num", "0");
                } else {
                    map.put("num", jo2.getInt("num"));
                }
                if (!jo2.has("nickname") || null == jo2.getString("nickname")
                        || "null".equals(jo2.getString("nickname")) ||
                        "".equals(jo2.getString("nickname"))) {
                    map.put("nickname", "");
                } else {
                    map.put("nickname", jo2.getString("nickname"));
                }
                if (!jo2.has("uid") || null == jo2.getString("uid") ||
                        "null".equals(jo2.getString("uid"))
                        || "".equals(jo2.getString("uid"))) {
                    user.setUid(0);
                } else {
                    user.setUid(jo2.getInt("uid"));
                }
                if (!jo2.has("atime") || null == jo2.getString("atime") ||
                        "null".equals(jo2.getString("atime"))
                        || "".equals(jo2.getString("atime"))) {
                    user.setAtime(0L);
                } else {
                    user.setAtime(jo2.getLong("atime"));
                }

                if (!jo2.has("uhead") || null == jo2.getString("uhead") ||
                        "null".equals(jo2.getString("uhead"))
                        || "".equals(jo2.getString("uhead"))) {
                    user.setUhead("");
                } else {
                    user.setUhead(jo2.getString("uhead"));
                }
                if (!jo2.has("num") || null == jo2.getString("num") ||
                        "null".equals(jo2.getString("num"))
                        || "".equals(jo2.getString("num"))) {
                    user.setNum(0);
                } else {
                    user.setNum(jo2.getInt("num"));
                }
                if (!jo2.has("nickname") || null == jo2.getString("nickname") ||
                        "".equals(jo2.getString("nickname"))) {
                    user.setNickname("");
                } else {
                    user.setNickname(jo2.getString("nickname"));
                }
            }
            mapObject.put("user", user);
            list.add(mapObject);
        }
        return list;

    }

    /**
     * 查询抵用券
     */
    // public static final List<HashMap<String, Object>>
    // createDiYongQuan(Context context, String result)
    // throws Exception {
    // // HashMap<String, Object> mapRet = new HashMap<String, Object>();
    // List<HashMap<String, Object>> mapRet = new ArrayList<HashMap<String,
    // Object>>();
    // JSONObject jsonObject = new JSONObject(result);
    // // System.out.println("&&&^^^^^^&&&" + result);
    // if (null == jsonObject || "".equals(jsonObject)) {
    // return null;
    // }
    // JSONArray jsonArray = new JSONArray(jsonObject.getString("voucher"));
    // if (null == jsonArray || "".equals(jsonArray)) {
    // return null;
    // }
    //
    // for (int i = 0; i < jsonArray.length(); i++) {
    // JSONObject jo = (JSONObject) jsonArray.opt(i);
    // HashMap<String, Object> mapObject = new HashMap<String, Object>();
    // if (!jo.has("snum") || null == jo.getString("snum") ||
    // "null".equals(jo.getString("snum"))
    // || "".equals(jo.getString("snum"))) {
    // mapObject.put("snum", "0");
    // } else {
    // mapObject.put("snum", jo.getString("snum"));
    // }
    // if (!jo.has("price") || null == jo.getString("price") ||
    // "null".equals(jo.getString("price"))
    // || "".equals(jo.getString("price"))) {
    // mapObject.put("price", "0");
    // } else {
    // mapObject.put("price", jo.getString("price"));
    // }
    //
    // mapRet.add(mapObject);
    // // System.out.println("***--***"+mapRet);
    // }
    // // System.out.println("***------***"+mapRet);
    // return mapRet;
    //
    // }

    /**
     * 获取抵用券
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static final HashMap<String, Object> createDiYong(Context context, String result) throws Exception {
        HashMap<String, Object> mapObj = new HashMap<String, Object>();
        // ReturnInfo retInfo = new ReturnInfo();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        // retInfo.setStatus(j.has("status") ? j.getString(RetInfo.status) :
        // "");
        // retInfo.setMessage(j.has("message") ? j.getString(RetInfo.message) :
        // "");
        if (!j.has("status") || null == j.getString("status") || "null".equals(j.getString("status"))
                || "".equals(j.getString("status"))) {
            mapObj.put("status", "");
        } else {
            mapObj.put("status", j.has("status") ? j.getString(RetInfo.status) : "");
        }
        if (!j.has("message") || null == j.getString("message") || "null".equals(j.getString("message"))
                || "".equals(j.getString("message"))) {
            mapObj.put("message", "");
        } else {
            mapObj.put("message", j.has("message") ? j.getString(RetInfo.message) : "");
        }
        if (!j.has("num") || null == j.getString("num") || "null".equals(j.getString("num"))
                || "".equals(j.getString("num"))) {
            mapObj.put("num", "0");
        } else {
            mapObj.put("num", j.has("num") ? j.getString(RetInfo.num) : "0");
        }

        // System.out.println("?????" + mapObj);
        return mapObj;
    }

    /***
     * 特卖多订单提交确认
     */
    // public static final HashMap<String, Object>
    // createShopCartSubmitConfrim(Context context, String result)
    // throws Exception {
    // HashMap<String, Object> mapRet = new HashMap<String, Object>();
    // JSONObject jsonObject = new JSONObject(result);
    // if (null == jsonObject || "".equals(jsonObject)) {
    // return null;
    // }
    //
    // if (1 == jsonObject.getInt("status")) {
    // if (!jsonObject.has("buyCount") || null ==
    // jsonObject.getString("buyCount")
    // || "null".equals(jsonObject.getString("buyCount")) ||
    // "".equals(jsonObject.getString("buyCount"))) {
    // mapRet.put("buyCount", "0");
    // } else {
    // mapRet.put("buyCount", jsonObject.has("buyCount") ?
    // jsonObject.optInt("buyCount") : "0");
    // }
    // if (!jsonObject.has("isZeroBuy") || null ==
    // jsonObject.getString("isZeroBuy")
    // || "null".equals(jsonObject.getString("isZeroBuy"))
    // || "".equals(jsonObject.getString("isZeroBuy"))) {
    // mapRet.put("isZeroBuy", "-1000");
    // } else {
    // mapRet.put("isZeroBuy", jsonObject.has("isZeroBuy") ?
    // jsonObject.optInt("isZeroBuy") : "-1000");
    // }
    // if (!jsonObject.has("integral") || null ==
    // jsonObject.getString("integral")
    // || "null".equals(jsonObject.getString("integral")) ||
    // "".equals(jsonObject.getString("integral"))) {
    // mapRet.put("integral", "0");
    // } else {
    // mapRet.put("integral", jsonObject.has("integral") ?
    // jsonObject.optInt("integral") : "0");
    // }
    // if (!jsonObject.has("needIntegral") || null ==
    // jsonObject.getString("needIntegral")
    // || "null".equals(jsonObject.getString("needIntegral"))
    // || "".equals(jsonObject.getString("needIntegral"))) {
    // mapRet.put("needIntegral", "0");
    // } else {
    // mapRet.put("needIntegral", jsonObject.has("needIntegral") ?
    // jsonObject.optInt("needIntegral") : "0");
    // }
    // if (!jsonObject.has("status") || null == jsonObject.getString("status")
    // || "null".equals(jsonObject.getString("status")) ||
    // "".equals(jsonObject.getString("status"))) {
    // mapRet.put("status", "");
    // } else {
    // mapRet.put("status", jsonObject.has("status") ?
    // jsonObject.optInt("status") : "");
    // }
    // return mapRet;
    // }
    // return null;
    //
    // }
    public static final int createWeixinInfo(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return 0;
        }
        if (!jsonObject.has("sex") || null == jsonObject.getString("sex") || "null".equals(jsonObject.getString("sex"))
                || "".equals(jsonObject.getString("sex"))) {
            return 0;
        } else {
            return jsonObject.getInt("sex");
        }
    }

    /**
     * 夺宝得到商品详情
     */

    public static final HashMap<String, Object> createIndianaShop(Context context, String result) throws JSONException {
        // result = result.replace("null", "\"\"");
        HashMap<String, Object> map = new HashMap<String, Object>();
        // retInfo.put("now", jsonObject.getLong("now"));

        YDBHelper helper = new YDBHelper(context);
        Shop shop = new Shop();
        JSONObject j = new JSONObject(result);

        //countTrea"// 0表示没有参与,其他表示参与过.
        if (!j.has("countTrea") || null == j.getString("countTrea") || "null".equals(j.getString("countTrea"))
                || "".equals(j.getString("countTrea"))) {
            map.put("countTrea", 0);
        } else {
            map.put("countTrea", j.has("countTrea") ? j.optInt("countTrea") : 0);
        }

        if (!j.has("num") || null == j.getString("num") || "null".equals(j.getString("num"))
                || "".equals(j.getString("num"))) {
            map.put("num", 0);
        } else {
            map.put("num", j.has("num") ? j.optInt("num") : 0);
        }
//        if (!j.has("group_number") || null == j.getString("group_number") || "null".equals(j.getString("group_number"))
//                || "".equals(j.getString("group_number"))) {
//            map.put("group_number", 3);
//        } else {
//            map.put("group_number", j.has("group_number") ? j.optInt("group_number") : 3);
//        }

        if (!j.has("virtual_num") || null == j.getString("virtual_num") || "null".equals(j.getString("virtual_num"))
                || "".equals(j.getString("virtual_num"))) {
            map.put("virtual_num", 0);
        } else {
            map.put("virtual_num", j.has("virtual_num") ? j.optInt("virtual_num") : 0);
        }
        if (!j.has("order_status") || null == j.getString("order_status") || "null".equals(j.getString("order_status"))
                || "".equals(j.getString("order_status"))) {
            map.put("order_status", 0);
        } else {
            map.put("order_status", j.has("order_status") ? j.optInt("order_status") : 0);
        }
        if (!j.has("ostatus") || null == j.getString("ostatus") || "null".equals(j.getString("ostatus"))
                || "".equals(j.getString("ostatus"))) {
            map.put("ostatus", 0);
        } else {
            map.put("ostatus", j.has("ostatus") ? j.optInt("ostatus") : 0);
        }

        String str2 = "";
        List<String> list = new ArrayList<String>();
        if (j.has("codes") && null != j.getString("codes") && !"null".equals(j.getString("codes")) && !"".equals(j.getString("codes"))) {
            JSONArray jarray = new JSONArray(j.getString("codes"));
            if (jarray != null && jarray.length() > 0) {
                for (int i = 0; i < jarray.length(); i++) {
                    str2 = jarray.getString(i);
                    list.add(str2);
                }
            }
        }

        map.put("codes", list);
        if (!j.has("my_num") || null == j.getString("my_num") || "null".equals(j.getString("my_num"))
                || "".equals(j.getString("my_num"))) {
            map.put("my_num", 0);
        } else {
            map.put("my_num", j.has("my_num") ? j.optInt("my_num") : 0);
        }
        if (!j.has("otime") || null == j.getString("otime") || "null".equals(j.getString("otime"))
                || "".equals(j.getString("otime"))) {
            map.put("otime", 0L);
        } else {
            map.put("otime", j.has("otime") ? j.optLong("otime") : 0L);
        }

        if (!j.has("in_name") || null == j.getString("in_name") || "null".equals(j.getString("in_name"))
                || "".equals(j.getString("in_name"))) {
            map.put("in_name", "");
        } else {
            map.put("in_name", j.has("in_name") ? j.optString("in_name") : "");
        }
        if (!j.has("in_head") || null == j.getString("in_head") || "null".equals(j.getString("in_head"))
                || "".equals(j.getString("in_head"))) {
            map.put("in_head", "");
        } else {
            map.put("in_head", j.has("in_head") ? j.optString("in_head") : "");
        }
        if (!j.has("in_sum") || null == j.getString("in_sum") || "null".equals(j.getString("in_sum"))
                || "".equals(j.getString("in_sum"))) {
            map.put("in_sum", "1");
        } else {
            map.put("in_sum", j.has("in_sum") ? j.optString("in_sum") : "1");
        }
        if (!j.has("in_code") || null == j.getString("in_code") || "null".equals(j.getString("in_code"))
                || "".equals(j.getString("in_code"))) {
            map.put("in_code", "");
        } else {
            map.put("in_code", j.has("in_code") ? j.optString("in_code") : "");
        }
        if (!j.has("in_uid") || null == j.getString("in_uid") || "null".equals(j.getString("in_uid"))
                || "".equals(j.getString("in_uid"))) {
            map.put("in_uid", "");
        } else {
            map.put("in_uid", j.has("in_uid") ? j.optString("in_uid") : "");
        }
        // map.put("sys_time", j.getLong("sys_time"));


        if (!j.has("kickback") || null == j.getString("kickback") || "null".equals(j.getString("kickback"))
                || "".equals(j.getString("kickback"))) {
            shop.setKickback(0);
        } else {
            shop.setKickback(j.optDouble("kickback"));
        }
        if (!j.has("color_count") || null == j.getString("color_count") || "null".equals(j.getString("color_count"))
                || "".equals(j.getString("color_count"))) {
            shop.setColor_count(0);
        } else {
            shop.setColor_count((float) j.optInt("color_count", 0));
        }
        if (!j.has("type_count") || null == j.getString("type_count") || "null".equals(j.getString("type_count"))
                || "".equals(j.getString("type_count"))) {
            shop.setType_count(0);
        } else {
            shop.setType_count((float) j.optInt("type_count", 0));
        }
        if (!j.has("work_count") || null == j.getString("work_count") || "null".equals(j.getString("work_count"))
                || "".equals(j.getString("work_count"))) {
            shop.setWork_count(0);
        } else {
            shop.setWork_count((float) j.optInt("work_count", 0));
        }
        if (!j.has("cost_count") || null == j.getString("cost_count") || "null".equals(j.getString("cost_count"))
                || "".equals(j.getString("cost_count"))) {
            shop.setCost_count(0);
        } else {
            shop.setCost_count((float) j.optInt("cost_count", 0));
        }
        if (!j.has("star_count") || null == j.getString("star_count") || "null".equals(j.getString("star_count"))
                || "".equals(j.getString("star_count"))) {
            shop.setStar_count(0);
        } else {
            shop.setStar_count(j.optDouble("star_count"));
        }

        if (!j.has("praise_count") || null == j.getString("praise_count") || "null".equals(j.getString("praise_count"))
                || "".equals(j.getString("praise_count"))) {
            shop.setPraise_count(0);
        } else {
            shop.setPraise_count(j.optInt("praise_count")); // 好评数 Integer
        }
        if (!j.has("med_count") || null == j.getString("med_count") || "null".equals(j.getString("med_count"))
                || "".equals(j.getString("med_count"))) {
            shop.setMed_count(0);
        } else {
            shop.setMed_count(j.optInt("med_count")); // 中评数 Integer
        }
        if (!j.has("bad_count") || null == j.getString("bad_count") || "null".equals(j.getString("bad_count"))
                || "".equals(j.getString("bad_count"))) {
            shop.setBad_count(0);
        } else {
            shop.setBad_count(j.optInt("bad_count")); // 差评数 Integer
        }
        if (!j.has("eva_count") || null == j.getString("eva_count") || "null".equals(j.getString("eva_count"))
                || "".equals(j.getString("eva_count"))) {
            shop.setEva_count(0);
        } else {
            shop.setEva_count((float) j.optInt("eva_count", 0)); // 评价总数 Integer
        }
        if (!j.has("like_id") || null == j.getString("like_id") || "null".equals(j.getString("like_id"))
                || "-1".equals(j.optString("like_id")) || "".equals(j.getString("like_id"))) {
            shop.setLike_id(1);
        } else {
            shop.setLike_id(("-1".equals(j.optString("like_id")) || !j.has("like_id")) ? -1 : 1);
        }
        if (!j.has("cart_count") || null == j.getString("cart_count") || "null".equals(j.getString("cart_count"))
                || "".equals(j.getString("cart_count"))) {
            shop.setCart_count(0);
        } else {
            shop.setCart_count(j.optInt("cart_count"));// 购物车数量
        }

        JSONObject obj = j.getJSONObject("shop");
        if (!obj.has("banner") || null == obj.getString("banner") || "null".equals(obj.getString("banner"))
                || "".equals(obj.getString("banner"))) {
            shop.setBanner("");
        } else {
            shop.setBanner(obj.optString("banner"));
        }
        if (!obj.has("group_number") || null == obj.getString("group_number") || "null".equals(obj.getString("group_number"))
                || "".equals(obj.getString("group_number"))) {
            shop.setGroup_number(3);
        } else {
            shop.setGroup_number(obj.optInt("group_number"));
        }
        if (!obj.has("active_people_num") || null == obj.getString("active_people_num")
                || "null".equals(obj.getString("active_people_num")) || "".equals(obj.getString("active_people_num"))) {
            shop.setActive_people_num(0);
        } else {
            shop.setActive_people_num(obj.optInt("active_people_num"));
        }

        if (!obj.has("involved_people_num") || null == obj.getString("involved_people_num")
                || "null".equals(obj.getString("involved_people_num")) || "".equals(obj.getString("involved_people_num"))) {
            shop.setInvolved_people_num(0);
        } else {
            shop.setInvolved_people_num(obj.optInt("involved_people_num"));
        }
        if (!obj.has("num") || null == obj.getString("num") || "null".equals(obj.getString("num"))
                || "".equals(obj.getString("num"))) {
            shop.setNum(0);
        } else {
            shop.setNum(obj.optInt("num"));
        }

        if (!obj.has("otime") || null == obj.getString("otime") || "null".equals(obj.getString("otime"))
                || "".equals(obj.getString("otime"))) {
            shop.setOtime(0L);
        } else {
            shop.setOtime(obj.optLong("otime"));
        }

        if (!obj.has("sys_time") || null == obj.getString("sys_time") || "null".equals(obj.getString("sys_time"))
                || "".equals(obj.getString("sys_time"))) {
            shop.setSys_time(0L);
        } else {
            shop.setSys_time(obj.optLong("sys_time"));
        }
        if (!obj.has("active_start_time") || null == obj.getString("active_start_time")
                || "null".equals(obj.getString("active_start_time")) || "".equals(obj.getString("active_start_time"))) {
            shop.setActive_start_time(0L);
        } else {
            shop.setActive_start_time(obj.optLong("active_start_time"));
        }
        if (!obj.has("active_end_time") || null == obj.getString("active_end_time")
                || "null".equals(obj.getString("active_end_time")) || "".equals(obj.getString("active_end_time"))) {
            shop.setActive_end_time(0L);
        } else {
            shop.setActive_end_time(obj.optLong("active_end_time"));
        }
        if (!obj.has("shop_batch_num") || null == obj.getString("shop_batch_num")
                || "".equals(obj.getString("shop_batch_num"))) {
            shop.setShop_batch_num("");
        } else {
            shop.setShop_batch_num(obj.optString("shop_batch_num"));
        }
        if (!obj.has("supp_id") || null == obj.getString("supp_id") || "null".equals(obj.getString("supp_id"))
                || "".equals(obj.getString("supp_id"))) {
            shop.setSupp_id(0);
        } else {
            shop.setSupp_id(obj.optInt("supp_id"));
        }
        if (!obj.has("shop_se_price") || null == obj.getString("shop_se_price")
                || "null".equals(obj.getString("shop_se_price")) || "".equals(obj.getString("shop_se_price"))) {
            shop.setShop_se_price(0);
        } else {
            shop.setShop_se_price(obj.optDouble("shop_se_price", 0));
        }
        if (!obj.has("shop_price") || null == obj.getString("shop_price") || "null".equals(obj.getString("shop_price"))
                || "".equals(obj.getString("shop_price"))) {
            shop.setShop_price(0);
        } else {
            shop.setShop_price(obj.optDouble("shop_price", 0));
        }
        if (!obj.has("shop_code") || null == obj.getString("shop_code") || "".equals(obj.getString("shop_code"))) {
            shop.setShop_code("wu");
        } else {
            shop.setShop_code(obj.optString("shop_code", "wu"));
        }
        if (!obj.has("core") || null == obj.getString("core") || "null".equals(obj.getString("core"))
                || "".equals(obj.getString("core"))) {
            shop.setCore(0);
        } else {
            shop.setCore(obj.optInt("core"));
        }
        if (!obj.has("remark") || null == obj.getString("remark") || "".equals(obj.getString("remark"))) {
            shop.setRemark("wu");
        } else {
            shop.setRemark(obj.optString("remark", "wu"));
        }
        if (!obj.has("def_pic") || null == obj.getString("def_pic") || "".equals(obj.getString("def_pic"))) {
            shop.setDef_pic("wu");
        } else {
            shop.setDef_pic(obj.optString("def_pic", "wu"));
        }
        if (!obj.has("kickback") || null == obj.getString("kickback") || "null".equals(obj.getString("kickback"))
                || "".equals(obj.getString("kickback"))) {
            shop.setKickback(0);
        } else {
            shop.setKickback(obj.optDouble("kickback", 0));
        }
        if (!obj.has("shop_up_time") || null == obj.getString("shop_up_time")
                || "null".equals(obj.getString("shop_up_time")) || "".equals(obj.getString("shop_up_time"))) {
            shop.setShop_up_time(0L);
        } else {
            shop.setShop_up_time(obj.optLong("shop_up_time", 0L));
        }
        if (!obj.has("invertory_num") || null == obj.getString("invertory_num")
                || "null".equals(obj.getString("invertory_num")) || "".equals(obj.getString("invertory_num"))) {
            shop.setInvertory_num(0);
        } else {
            shop.setInvertory_num(obj.optInt("invertory_num", 0));
        }
        if (!obj.has("shop_name") || null == obj.getString("shop_name") || "".equals(obj.getString("shop_name"))) {
            shop.setShop_name("wu");
        } else {
            shop.setShop_name(obj.optString("shop_name", "wu"));
        }
        if (!obj.has("id") || null == obj.getString("id") || "null".equals(obj.getString("id"))
                || "".equals(obj.getString("id"))) {
            shop.setId(0);
        } else {
            shop.setId(obj.optInt("id", 0));
        }
        if (!obj.has("content") || null == obj.getString("content") || "".equals(obj.getString("content"))) {
            shop.setContent("wu");
        } else {
            shop.setContent(obj.optString("content", "wu"));
        }
        if (!obj.has("actual_sales") || null == obj.getString("actual_sales")
                || "null".equals(obj.getString("actual_sales")) || "".equals(obj.getString("actual_sales"))) {
            shop.setActual_sales(0);
        } else {
            shop.setActual_sales(obj.optInt("actual_sales", 0));
        }
        if (!obj.has("is_new") || null == obj.getString("is_new") || "".equals(obj.getString("is_new"))) {
            shop.setIs_new("wu");
        } else {
            shop.setIs_new(obj.optString("is_new", "wu"));
        }
        if (!obj.has("shop_pic") || null == obj.getString("shop_pic") || "".equals(obj.getString("shop_pic"))) {
            shop.setShop_pic("wu");
        } else {
            shop.setShop_pic(obj.optString("shop_pic", "wu"));
        }
        if (!obj.has("clicks") || null == obj.getString("clicks") || "null".equals(obj.getString("clicks"))
                || "".equals(obj.getString("clicks"))) {
            shop.setClicks(0);
        } else {
            shop.setClicks(obj.optInt("clicks", 0));
        }
        if (!obj.has("is_hot") || null == obj.getString("is_hot") || "".equals(obj.getString("is_hot"))) {
            shop.setIs_hot("wu");
        } else {
            shop.setIs_hot(obj.optString("is_hot", "wu"));
        }
        if (!obj.has("virtual_sales") || null == obj.getString("virtual_sales")
                || "null".equals(obj.getString("virtual_sales")) || "".equals(obj.getString("virtual_sales"))) {
            shop.setVirtual_sales(0);
        } else {
            shop.setVirtual_sales(obj.optInt("virtual_sales", 0));
        }
        if (!obj.has("love_num") || null == obj.getString("love_num") || "null".equals(obj.getString("love_num"))
                || "".equals(obj.getString("love_num"))) {
            shop.setLove_num(0);
        } else {
            shop.setLove_num(obj.optInt("love_num", 0));
        }
        if (!obj.has("shop_tag") || null == obj.getString("shop_tag") || "".equals(obj.getString("shop_tag"))) {
            shop.setShop_tag("");
        } else {
            shop.setShop_tag(obj.optString("shop_tag"));
        }

        String ss = "";
        if (!obj.has("shop_type_id") || null == obj.getString("shop_type_id")
                || "".equals(obj.getString("shop_type_id"))) {

        } else {
            ss = obj.optString("shop_type_id");
        }
        if (!TextUtils.isEmpty(ss)) {
            ss = ss.replace("[", "");
            ss = ss.replace("]", "");
            String[] shop_type_id = ss.split(",");
            shop.setShop_type_id(shop_type_id);
        }

        if (!obj.has("type1") || null == obj.getString("type1") || "null".equals(obj.getString("type1"))
                || "".equals(obj.getString("type1"))) {
            shop.setType1(0);
        } else {
            shop.setType1(obj.optInt("type1"));
        }

        String text = "";
        if (!obj.has("shop_attr") || null == obj.getString("shop_attr") || "".equals(obj.getString("shop_attr"))) {
        } else {
            text = obj.getString("shop_attr");
        }

        List<String[]> liststr = new ArrayList<String[]>();
        String str[] = text.split("_");
        for (int i = 0; i < str.length; i++) {
            String strson[] = str[i].split(",");
            int length = strson.length;
            String s[] = new String[length];
            for (int d = 0; d < strson.length; d++) {
                s[d] = strson[d];

            }
            liststr.add(strson);
        }
        shop.setShop_attr(liststr);

        // 属性数据
        List<String> sqls = new ArrayList<String>();
        JSONArray jAttr = new JSONArray(j.optString("attrList"));
        if (null != jAttr && jAttr.length() > 0) {
            helper.delete("delete from attr_info");
            for (int i = 0; i < jAttr.length(); i++) {
                ShopAttr sAttr = JSON.parseObject(jAttr.getString(i), ShopAttr.class);
                String sql = "insert into attr_info(_id,attr_name,icon,p_id,is_show)values('" + sAttr.getId() + "','"
                        + sAttr.getAttr_name() + "','" + sAttr.getIco() + "','" + sAttr.getParent_id() + "','"
                        + sAttr.getIs_show() + "')";
                sqls.add(sql);

            }
            helper.update(sqls);
        }
        map.put("shop", shop);
        return map;
    }

    /**
     * 得到参与记录
     */
    public static final List<HashMap<String, Object>> createIndianaTakeRecord(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (!j.has("status") || !"1".equals(j.getString("status"))) {
            return null;
        }
        if (!j.has("list") || "".equals(j.getString("list")) || "null".equals(j.getString("list")) || null == j.getString("list")) {
            return null;
        }
        JSONArray jsonArray = j.has("list") ? new JSONArray(j.getString("list")) : null;
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.opt(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();
            if (!jo.has("uid") || null == jo.getString("uid") || "null".equals(jo.getString("uid"))
                    || "".equals(jo.getString("uid"))) {
                mapObject.put("uid", 0);
            } else {
                mapObject.put("uid", jo.getInt("uid"));
            }
            if (!jo.has("atime") || null == jo.getString("atime") || "null".equals(jo.getString("atime"))
                    || "".equals(jo.getString("atime"))) {
                mapObject.put("atime", 0);
            } else {
                mapObject.put("atime", jo.getLong("atime"));
            }
            if (!jo.has("money") || null == jo.getString("money") || "null".equals(jo.getString("money"))
                    || "".equals(jo.getString("money"))) {
                mapObject.put("money", 0);
            } else {
                mapObject.put("money", jo.getInt("money"));
            }
            if (!jo.has("uhead") || null == jo.getString("uhead") || "".equals(jo.getString("uhead"))) {
                mapObject.put("uhead", "");
            } else {
                mapObject.put("uhead", jo.getString("uhead"));
            }
            if (!jo.has("num") || null == jo.getString("num") || "null".equals(jo.getString("num"))
                    || "".equals(jo.getString("num"))) {
                mapObject.put("num", 0);
            } else {
                mapObject.put("num", jo.getInt("num"));
            }
            if (!jo.has("nickname") || null == jo.getString("nickname") || "".equals(jo.getString("nickname"))) {
                mapObject.put("nickname", "");
            } else {
                mapObject.put("nickname", jo.getString("nickname"));
            }

            retInfo.add(mapObject);
        }
        return retInfo;
    }

    /**
     * 得到参与记录NEW
     */
    public static final HashMap<String, Object> createIndianaTakeRecordNew(Context context, String result)
            throws JSONException {
        HashMap<String, Object> retInfo = new HashMap<String, Object>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONObject jsonArray = new JSONObject(j.getString("data"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }
        if (!jsonArray.has("head_pic") || null == jsonArray.getString("head_pic")
                || "".equals(jsonArray.getString("head_pic"))) {
            retInfo.put("head_pic", "");
        } else {
            retInfo.put("head_pic", jsonArray.has("head_pic") ? jsonArray.get("head_pic") : "");
        }
        if (!jsonArray.has("flag") || null == jsonArray.getString("flag") || "".equals(jsonArray.getString("flag"))) {
            retInfo.put("flag", "");
        } else {
            retInfo.put("flag", jsonArray.has("flag") ? (jsonArray.get("flag") + "") : "");
        }
        return retInfo;
    }

    // /***
    // * 检查是否参加过包邮活动
    // */
    public static final HashMap<String, Object> checkBaoyou(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (1 == jsonObject.getInt("status")) {
            if (!jsonObject.has("flag") || null == jsonObject.getString("flag")
                    || "".equals(jsonObject.getString("flag"))) {
                mapRet.put("flag", "");
            } else {
                mapRet.put("flag", jsonObject.has("flag") ? jsonObject.getString("flag") : "");
            }

            // context.getSharedPreferences("buqianka",
            // Context.MODE_PRIVATE).edit()
            // .putBoolean("bool", jsonObject.has("bool") ?
            // jsonObject.getBoolean("bool") : false).commit();
            // mapRet.put("bool", jsonObject.has("bool") ?
            // jsonObject.getString("bool") : "");
            return mapRet;
        }

        return null;

    }

    /**
     * 获取购物车数据
     */
    public static final HashMap<String, Object> shopCartData(Context context, String result, int num) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (1 == jsonObject.getInt("status")) {

            if (!jsonObject.has("id") || null == jsonObject.getString("id") || "".equals(jsonObject.getString("id"))) {
                mapRet.put("id", "");
            } else {
                mapRet.put("id", jsonObject.has("id") ? jsonObject.getString("id") : "");
            }

            return mapRet;
        }

        return null;

    }

    /***
     * 购物车所有数据
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final List<List<ShopCart>> createListShop_CartAll(Context context, String result, int flagFrist)
            throws JSONException {// flagFrist=0代表第一次进入，flagFrist=1代表切换用户
        JSONObject j = new JSONObject(result);

        List<ShopCart> listCommn = null;
        List<ShopCart> listMeal = null;

        if (j.getInt("status") == 1) {
            if (j.has("shop_list")) {
                String textComm = j.getString("shop_list");
                listCommn = JSON.parseArray(textComm, ShopCart.class);

            }

            if (j.has("p_shop_list")) {

                String textMeal = j.getString("p_shop_list");
                // listMeal = JSON.parseArray(textMeal, ShopCart.class);
            }
            // long p_deadline = 0L;
            // long s_deadline = 0L;
            // long sys_time = 0L;
            // if (j.has("p_deadline") && null != j.getString("p_deadline")) {
            // p_deadline = j.getLong("p_deadline");
            // }
            // if (j.has("s_deadline") && null != j.getString("s_deadline")) {
            // s_deadline = j.getLong("s_deadline");
            //
            // }
            // if (j.has("sys_time") && null != j.getString("sys_time")) {
            // sys_time = j.getLong("sys_time");
            // }
            ShopCartDao dao = new ShopCartDao(context);
            List<ShopCart> list_valid_temp = dao.findAll();
            List<ShopCart> list_invalid_temp = dao.findAll_invalid();
            if (flagFrist == 0 && ((list_valid_temp.size() + list_invalid_temp.size()) > 0)) {// 打开app与数据库有数据时，只同步数据库中没有的
                if (listCommn != null) {// 正价
                    for (int i = 0; i < listCommn.size(); i++) {
                        boolean flag = false;
                        for (int k = 0; k < list_valid_temp.size(); k++) {
                            if (list_valid_temp.get(k).getIs_meal_flag() == 0) {
                                if (("" + listCommn.get(i).getStock_type_id())
                                        .equals("" + list_valid_temp.get(k).getStock_type_id())) {
                                    flag = true;// 有效中存在
                                    break;
                                }
                            }
                        }
                        if (!flag) {// 有效中没有
                            for (int k = 0; k < list_invalid_temp.size(); k++) {
                                if (list_invalid_temp.get(k).getIs_meal_flag() == 0) {
                                    if (("" + listCommn.get(i).getStock_type_id())
                                            .equals("" + list_invalid_temp.get(k).getStock_type_id())) {
                                        flag = true;// 失效中存在
                                        break;
                                    }
                                }
                            }
                        }
                        if (!flag) {// 有效失效都不存在，添加到有效当中
                            ShopCart s = listCommn.get(i);
                            dao.add(s.getShop_code(), s.getSize(), s.getColor(), s.getShop_num(), s.getStock_type_id(),
                                    s.getDef_pic(), 0, s.getShop_name(),
                                    s.getStore_code() != null ? s.getStore_code() : 0 + "", "" + s.getShop_price(),
                                    "" + s.getShop_se_price(), "" + s.getSupp_id(), "" + s.getKickback(),
                                    "" + s.getOriginal_price(), 0, null, null, null, null, null, s.getId(), null, 0,
                                    "" + s.getSupp_label());
                        }
                    }
                }

                // if (listMeal != null) {// 特卖
                // for (int i = 0; i < listMeal.size(); i++) {
                // boolean flag = false;
                // for (int k = 0; k < list_valid_temp.size(); k++) {
                // if (list_valid_temp.get(k).getIs_meal_flag() == 1) {
                // if
                // (listMeal.get(i).getP_s_t_id().equals(list_valid_temp.get(k).getP_s_t_id()))
                // {
                // flag = true;// 有效中存在
                // break;
                // }
                // }
                // }
                // if (!flag) {// 有效中没有
                // for (int k = 0; k < list_invalid_temp.size(); k++) {
                // if (list_invalid_temp.get(k).getIs_meal_flag() == 1) {
                // if
                // (listMeal.get(i).getP_s_t_id().equals(list_invalid_temp.get(k).getP_s_t_id()))
                // {
                // flag = true;// 失效中存在
                // break;
                // }
                // }
                // }
                // }
                // if (!flag) {// 有效失效都不存在，添加到有效当中
                // dao.add(null, null, null, listMeal.get(i).getShop_num(), 0,
                // (String) listMeal.get(i).getDef_pic(), 0, null, null,
                // "" + listMeal.get(i).getShop_price(), "" +
                // listMeal.get(i).getShop_se_price(),
                // listMeal.get(i).getSupp_id() + "", "" + 0, "" + 0, 1,
                // listMeal.get(i).getP_code(),
                // "" + listMeal.get(i).getPostage(),
                // listMeal.get(i).getP_s_t_id(),
                // listMeal.get(i).getP_shop_code(),
                // listMeal.get(i).getP_color(),
                // listMeal.get(i).getId(),
                // listMeal.get(i).getP_s_t_id().split(",").length > 1 ? "超值套餐"
                // : "超值单品", 0);
                // }
                // }
                // }

            } else {
                List<ShopCart> list_invalid = dao.findAll_invalid();
                if (list_invalid != null) {// 同步数据，清空失效购物车数据库
                    for (int i = 0; i < list_invalid.size(); i++) {
                        if (list_invalid.get(i).getIs_meal_flag() == 0) {
                            dao.delete_invalid("" + list_invalid.get(i).getStock_type_id());
                        } else {
                            dao.p_delete_invalid(list_invalid.get(i).getP_s_t_id());
                        }
                    }
                }
                // SharedPreferencesUtil.saveStringData(context,
                // Pref.SHOPCART_COMMON_TIME,
                // "" + (System.currentTimeMillis() + s_deadline - sys_time));
                // SharedPreferencesUtil.saveStringData(context,
                // Pref.SHOPCART_MEAL_TIME,
                // "" + (System.currentTimeMillis() + p_deadline - sys_time));
                dao.common_first_add(context, listCommn, listMeal);// 全部数据先添加到有效购物车数据库中

            }
            // LogYiFu.e("listCommn", listCommn + "");
            // LogYiFu.e("listMeal", listMeal + "");
            // LogYiFu.e("listMeal", listMeal + "");
        }

        return null;
    }

    /***
     * 运营数据统计
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final List<List<ShopCart>> createYunYingshuju(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);

        return null;
    }

    /**
     * 获取PIC与时间
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static final HashMap<String, Object> createPicLife(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (1 == jsonObject.getInt("status")) {

            String str = jsonObject.getString("data");

            JSONObject jsonObject2 = new JSONObject(str);

            // String pic = jsonObject2.getString("pic");
            if (!jsonObject2.has("pic") || null == jsonObject2.getString("pic")
                    || "".equals(jsonObject2.getString("pic"))) {
                mapRet.put("pic", "");
            } else {
                mapRet.put("pic", jsonObject2.getString("pic"));
            }

            return mapRet;
        }

        return null;

    }

    public static final List<String> invalid_shopcart(Context context, String result) throws Exception {
        List<FundDetail> funds = new ArrayList<FundDetail>();
        JSONObject jsonObject = new JSONObject(result);

        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        if (!"1".equals(jsonObject.getString("status"))) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        if (jsonObject.has("list")) {
            JSONArray jsonArray = new JSONArray(jsonObject.getString("list"));
            if (jsonArray != null && !("".equals(jsonArray))) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add("" + jsonArray.get(i));
                }
                return list;
            }
            return null;
        } else {
            return null;
        }
    }

    //////////////////////////////////////////////// 增加的///////////////////////////////////////////////
    public static final ReturnInfo creatUMPushCount(Context context, String result) throws JSONException {

        ReturnInfo retInfo = new ReturnInfo();
        JSONObject j = new JSONObject(result);

        retInfo.setStatus(j.has("status") ? j.getString(RetInfo.status) : "");
        retInfo.setMessage(j.has("message") ? j.getString(RetInfo.message) : "");

        return retInfo;
    }

    /**
     * 获取九宫图的二维码背景图片
     */
    public static final String createShareBg(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        JSONObject jo = jsonObject.getJSONObject("data");
        String pic = "";
        if (!jo.has("pic") || null == jo.getString("pic") || "".equals(jo.getString("pic"))) {

        } else {
            pic = jo.has("pic") ? jo.optString("pic") : "";
        }

        return pic;
    }

    /**
     * 后台设置初次登录是否跳转到微信授权（3.3.8）
     */
    public static final String createFirstShouquan(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        String data = "";
        if (!jsonObject.has("data") || null == jsonObject.getString("data")
                || "".equals(jsonObject.getString("data"))) {
            data = "0";
        } else {
            data = jsonObject.has("data") ? jsonObject.optString("data") : "";
        }
        // 0:手机 1：微信 0时不用获取微信openID 1时需要获取openID(跳转到微信授权)
        return data;
    }

    /**
     * 签到分享搭配购获取搭配编号
     */
    public static final HashMap<String, Object> createCollectionCode(Context context, String result)
            throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (!jsonObject.has("code") || null == jsonObject.getString("code")
                || "".equals(jsonObject.getString("code"))) {
            map.put("code", "");
        } else {
            map.put("code", jsonObject.has("code") ? jsonObject.optString("code") : "");
        }
        if (!jsonObject.has("link") || null == jsonObject.getString("link")
                || "".equals(jsonObject.getString("link"))) {
            map.put("link", "");
        } else {
            map.put("link", jsonObject.has("link") ? jsonObject.optString("link") : "");
        }
        return map;
    }

    /**
     * 显示用户新增粉丝和用户领取奖励弹窗
     */
    public static final List<String> createShareAwardsData(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        List<String> list = new ArrayList<String>();
        String status = jsonObject.optString("status");
        if ("1".equals(status)) {
            JSONArray barr_list = jsonObject.optJSONArray("barr_list");
            for (int i = 0; i < barr_list.length(); i++) {
                String barr_list_str = barr_list.optString(i);
                if ("".equals(barr_list_str) || null == barr_list_str) {

                } else {
                    list.add(barr_list_str);
                }
            }
        }
        return list;
    }

    /***
     * 最新获奖弹窗通知
     */
    public static final HashMap<String, Object> createSignNewMoney(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        if (!jsonObject.has("status") || null == jsonObject.getString("status")
                || "null".equals(jsonObject.getString("status")) || "".equals(jsonObject.getString("status"))) {
            return null;
        }
        if (1 == jsonObject.getInt("status")) {

            if (!jsonObject.has("money") || null == jsonObject.getString("money")
                    || "null".equals(jsonObject.getString("money")) || "".equals(jsonObject.getString("money"))) {
                mapRet.put("money", "0");
            } else {
                mapRet.put("money", jsonObject.has("money") ? jsonObject.getString("money") : "0");
            }

            return mapRet;
        }

        return null;
    }

    /**
     * 额外分享详情页
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final HashMap<String, Object> createShareDetails(Context context, String result)
            throws JSONException {
        JSONObject j = new JSONObject(result);
        HashMap<String, Object> map = new HashMap<String, Object>();
        List<String> listPic = new ArrayList<String>();
        List<String> browList = new ArrayList<String>();
        List<String> fansList = new ArrayList<String>();
        if (j.has("status")) {
            map.put("status", "" + j.getString("status"));
        }
        if (j.has("pic_list") && null != j.getString("pic_list") && !("".equals(j.getString("pic_list")))) {
            JSONArray ja = new JSONArray(j.getString("pic_list"));
            if (null != ja && !("".equals(ja))) {
                for (int i = 0; i < ja.length(); i++) {
                    listPic.add("" + ja.get(i));
                }
            }
        }
        if (j.has("browList") && null != j.getString("browList") && !("".equals(j.getString("browList")))) {
            JSONArray ja2 = new JSONArray(j.getString("browList"));
            if (null != ja2 && !("".equals(ja2))) {
                for (int i = 0; i < ja2.length(); i++) {
                    browList.add("" + ja2.get(i));
                }
            }
        }
        if (j.has("fansList") && null != j.getString("fansList") && !("".equals(j.getString("fansList")))) {
            JSONArray ja2 = new JSONArray(j.getString("fansList"));
            if (null != ja2 && !("".equals(ja2))) {
                for (int i = 0; i < ja2.length(); i++) {
                    fansList.add("" + ja2.get(i));
                }
            }
        }
        map.put("pic_list", listPic);
        map.put("browList", browList);
        map.put("fansList", fansList);
        if (j.has("bro_count") && null != j.getString("bro_count") && !("".equals(j.getString("bro_count")))) {
            map.put("bro_count", j.optInt("bro_count"));
        } else {
            map.put("bro_count", 0);
        }
        if (j.has("fans_count") && null != j.getString("fans_count") && !("".equals(j.getString("fans_count")))) {
            map.put("fans_count", j.optInt("fans_count"));
        } else {
            map.put("fans_count", 0);
        }
        return map;
    }

    /**
     * 获取是否开启优惠券升级金券
     */
    public static final HashMap<String, String> createCpgold(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        HashMap<String, String> map = new HashMap<String, String>();
        String status = jsonObject.optString("status");
        if ("1".equals(status)) {

            String str = jsonObject.getString("CpGold");

            JSONObject jsonObject2 = new JSONObject(str);

            // map.put("end_date", jsonObject2.optString("end_date"));

            // if ("null".equals(jsonObject2.getString("end_date")) &&
            // jsonObject2.has("end_date")
            // && null != jsonObject2.getString("end_date") &&
            // !("".equals(jsonObject2.getString("end_date")))) {
            if (!jsonObject2.has("end_date") || null == jsonObject2.getString("end_date")
                    || "null".equals(jsonObject2.getString("end_date"))
                    || "".equals(jsonObject2.getString("end_date"))) {
                map.put("end_date", "0");
            } else {
                map.put("end_date", jsonObject2.optString("end_date"));
            }

            // map.put("is_open", jsonObject2.optString("is_open"));
            // if ("null".equals(jsonObject2.getString("is_open")) &&
            // jsonObject2.has("is_open")
            // && null != jsonObject2.getString("is_open") &&
            // !("".equals(jsonObject2.getString("is_open")))) {
            // map.put("is_open", jsonObject2.optString("is_open"));
            // } else {
            // map.put("is_open", "-1000");
            // }
            if (!jsonObject2.has("is_open") || null == jsonObject2.getString("is_open")
                    || "null".equals(jsonObject2.getString("is_open")) || "".equals(jsonObject2.getString("is_open"))) {
                map.put("is_open", "-1000");
            } else {
                map.put("is_open", jsonObject2.optString("is_open"));
            }

            // map.put("c_last_time", jsonObject2.optString("c_last_time"));
            // if ("null".equals(jsonObject2.getString("c_last_time")) &&
            // jsonObject2.has("c_last_time")
            // && null != jsonObject2.getString("c_last_time")
            // && !("".equals(jsonObject2.getString("c_last_time")))) {
            // map.put("c_last_time", jsonObject2.optString("c_last_time"));
            // } else {
            // map.put("c_last_time", "0");
            // }
            if (!jsonObject2.has("c_last_time") || null == jsonObject2.getString("c_last_time")
                    || "null".equals(jsonObject2.getString("c_last_time"))
                    || "".equals(jsonObject2.getString("c_last_time"))) {
                map.put("c_last_time", "0");
            } else {
                map.put("c_last_time", jsonObject2.optString("c_last_time"));
            }

            // map.put("c_price", jsonObject2.optString("c_price"));
            // if ("null".equals(jsonObject2.getString("c_price")) &&
            // jsonObject2.has("c_price")
            // && null != jsonObject2.getString("c_price") &&
            // !("".equals(jsonObject2.getString("c_price")))) {
            // map.put("c_price", jsonObject2.optString("c_price"));
            // } else {
            // map.put("c_price", "0");
            // }
            if (!jsonObject2.has("c_price") || null == jsonObject2.getString("c_price")
                    || "null".equals(jsonObject2.getString("c_price")) || "".equals(jsonObject2.getString("c_price"))) {
                map.put("c_price", "0");
            } else {
                map.put("c_price", jsonObject2.optString("c_price"));
            }

            // map.put("c_id ", jsonObject2.optString("c_id "));
            // if ("null".equals(jsonObject2.getString("c_id")) &&
            // jsonObject2.has("c_id")
            // && null != jsonObject2.getString("c_id") &&
            // !("".equals(jsonObject2.getString("c_id")))) {
            // map.put("c_id ", jsonObject2.optString("c_id "));
            // } else {
            // map.put("c_id", "0");
            // }
            if (!jsonObject2.has("c_id") || null == jsonObject2.getString("c_id")
                    || "null".equals(jsonObject2.getString("c_id")) || "".equals(jsonObject2.getString("c_id"))) {
                map.put("c_id", "0");
            } else {
                map.put("c_id", jsonObject2.optString("c_id"));
            }

            // map.put("is_use", jsonObject2.optString("is_use"));
            // if ("null".equals(jsonObject2.getString("is_use")) &&
            // jsonObject2.has("is_use")
            // && null != jsonObject2.getString("is_use") &&
            // !("".equals(jsonObject2.getString("is_use")))) {
            // map.put("is_use", jsonObject2.optString("is_use"));
            // } else {
            // map.put("is_use", "-1");
            // }
            if (!jsonObject2.has("is_use") || null == jsonObject2.getString("is_use")
                    || "null".equals(jsonObject2.getString("is_use")) || "".equals(jsonObject2.getString("is_use"))) {
                map.put("is_use", "-1");
            } else {
                map.put("is_use", jsonObject2.optString("is_use"));
            }

            /**
             *
             *
             * end_date=此次升级的过期时间,
             *
             * is_open=1,(0否1是 ,只有=1时下面的参数才有值)
             *
             * c_last_time=被升级的优惠券的过期时间,
             *
             * c_price=升级的优惠券面值,
             *
             * c_id 升级的优惠券id
             *
             * is_use 升级的优惠券是否使用 (0否1是)
             */

        }
        return map;
    }

    /**
     * 获取是否开启积分升级金币
     */
    public static final HashMap<String, String> createTwoFoldnessGold(Context context, String result)
            throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        HashMap<String, String> map = new HashMap<String, String>();
        String status = jsonObject.optString("status");
        if ("1".equals(status)) {

            String str = jsonObject.getString("twofoldnessGold");
            if (null == str || "".equals(str) || "{}".equals(str)) {
                map.put("twofoldnessGold", "");
            } else {
                map.put("twofoldnessGold", str);
                JSONObject jsonObject2 = new JSONObject(str);

                // map.put("end_date", jsonObject2.optString("end_date"));
                // if ("null".equals(jsonObject2.getString("end_date")) &&
                // jsonObject2.has("end_date")
                // && null != jsonObject2.getString("end_date")
                // && !("".equals(jsonObject2.getString("end_date")))) {
                // map.put("end_date", jsonObject2.optString("end_date"));
                // } else {
                // map.put("end_date", "0");
                // }
                if (!jsonObject2.has("end_date") || null == jsonObject2.getString("end_date")
                        || "null".equals(jsonObject2.getString("end_date"))
                        || "".equals(jsonObject2.getString("end_date"))) {
                    map.put("end_date", "0");
                } else {
                    map.put("end_date", jsonObject2.optString("end_date"));
                }

                // map.put("c_id ", jsonObject2.optString("c_id "));
                // if ("null".equals(jsonObject2.getString("id")) &&
                // jsonObject2.has("id")
                // && null != jsonObject2.getString("id") &&
                // !("".equals(jsonObject2.getString("id")))) {
                // map.put("id ", jsonObject2.optString("id "));
                // } else {
                // map.put("id", "0");
                // }
                if (!jsonObject2.has("id") || null == jsonObject2.getString("id")
                        || "null".equals(jsonObject2.getString("id")) || "".equals(jsonObject2.getString("id"))) {
                    map.put("id", "0");
                } else {
                    map.put("id", jsonObject2.optString("id"));
                }

                /**
                 * 参数: (twofoldnessGold==null 或者空的时候表示没有开启 )
                 *
                 * end_date 升级的过期时间
                 *
                 */

            }
        }
        return map;
    }

    /**
     * 活动商品列表
     */
    ////////
    public static final List<HashMap<String, Object>> createSignActiveShopList(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        // System.out.println("这是强制浏览的result=" + result);
        if (null == j || "".equals(j)) {
            return list;
        }
        if (!"1".equals(j.getString("status"))) {
            return list;
        }
        JSONArray jsonArray = new JSONArray(j.getString("list"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return list;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.get(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();

            if (!jo.has("shop_code") || null == jo.getString("shop_code") || "null".equals(jo.getString("shop_code"))
                    || "".equals(jo.getString("shop_code"))) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");
            }
            if (!jo.has("shop_name") || null == jo.getString("shop_name") || "null".equals(jo.getString("shop_name"))
                    || "".equals(jo.getString("shop_name"))) {
                mapObject.put("shop_name", "");
            } else {
                mapObject.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");
            }
            if (!jo.has("shop_price") || null == jo.getString("shop_price") || "null".equals(jo.getString("shop_price"))
                    || "".equals(jo.getString("shop_price"))) {
                mapObject.put("shop_price", "0");
            } else {
                mapObject.put("shop_price", jo.has("shop_price") ? jo.getString("shop_price") : "0");
            }
            if (!jo.has("roll_price") || null == jo.getString("roll_price") || "null".equals(jo.getString("roll_price"))
                    || "".equals(jo.getString("roll_price"))) {
                mapObject.put("roll_price", "0");
            } else {
                mapObject.put("roll_price", jo.has("roll_price") ? jo.getString("roll_price") : "0");
            }

            if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price")
                    || "null".equals(jo.getString("shop_se_price")) || "".equals(jo.getString("shop_se_price"))) {
                mapObject.put("shop_se_price", "0");
            } else {
                mapObject.put("shop_se_price", jo.has("shop_se_price") ? jo.getString("shop_se_price") : "0");
            }
            if (!jo.has("def_pic") || null == jo.getString("def_pic") || "null".equals(jo.getString("def_pic"))
                    || "".equals(jo.getString("def_pic"))) {
                mapObject.put("def_pic", "");
            } else {
                mapObject.put("def_pic", jo.has("def_pic") ? jo.getString("def_pic") : "");
            }
            if (!jo.has("virtual_sales") || null == jo.getString("virtual_sales")
                    || "null".equals(jo.getString("virtual_sales")) || "".equals(jo.getString("virtual_sales"))) {
                mapObject.put("virtual_sales", "0");
            } else {
                mapObject.put("virtual_sales", jo.has("virtual_sales") ? jo.getString("virtual_sales") : "0");
            }
            if (!jo.has("supp_label_id") || null == jo.getString("supp_label_id")
                    || "null".equals(jo.getString("supp_label_id")) || "".equals(jo.getString("supp_label_id"))) {
                mapObject.put("supp_label_id", "");
            } else {
                mapObject.put("supp_label_id", jo.has("supp_label_id") ? jo.getString("supp_label_id") : "");
            }
            if (!jo.has("supp_label") || null == jo.getString("supp_label") || "null".equals(jo.getString("supp_label"))
                    || "".equals(jo.getString("supp_label"))) {
                mapObject.put("supp_label", "");
            } else {
                mapObject.put("supp_label", jo.has("supp_label") ? jo.getString("supp_label") : "");
            }
            list.add(mapObject);
        }
        return list;
    }

    /**
     * 获取拼团广场列表
     */
    public static final List<HashMap<String, Object>> createGroupSquareShopList(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return list;
        }
        if (!"1".equals(j.getString("status"))) {
            return list;
        }
        JSONArray jsonArray = new JSONArray(j.getString("list"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return list;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.get(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();

            if (!jo.has("user_name") || null == jo.getString("user_name") || "null".equals(jo.getString("user_name"))
                    || "".equals(jo.getString("user_name"))) {
                mapObject.put("user_name", "");
            } else {
                mapObject.put("user_name", jo.has("user_name") ? jo.getString("user_name") : ""); // 用户名称
            }
            if (!jo.has("user_portrait") || null == jo.getString("user_portrait")
                    || "null".equals(jo.getString("user_portrait")) || "".equals(jo.getString("user_portrait"))) {
                mapObject.put("user_portrait", "");
            } else {
                mapObject.put("user_portrait", jo.has("user_portrait") ? jo.getString("user_portrait") : ""); // 用户头像
            }
            if (!jo.has("r_code") || null == jo.getString("r_code") || "null".equals(jo.getString("r_code"))
                    || "".equals(jo.getString("r_code"))) {
                mapObject.put("r_code", "");
            } else {
                mapObject.put("r_code", jo.has("r_code") ? jo.getString("r_code") : ""); // 拼团编号
            }
            if (!jo.has("shop_code") || null == jo.getString("shop_code") || "null".equals(jo.getString("shop_code"))
                    || "".equals(jo.getString("shop_code"))) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : ""); // 商品编号
            }
            if (!jo.has("shop_url") || null == jo.getString("shop_url") || "null".equals(jo.getString("shop_url"))
                    || "".equals(jo.getString("shop_url"))) {
                mapObject.put("shop_url", "");
            } else {
                mapObject.put("shop_url", jo.has("shop_url") ? jo.getString("shop_url") : ""); // 图片
            }
            if (!jo.has("shop_name") || null == jo.getString("shop_name") || "null".equals(jo.getString("shop_name"))
                    || "".equals(jo.getString("shop_name"))) {
                mapObject.put("shop_name", "");
            } else {
                mapObject.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");// 商品名称
            }
            if (!jo.has("shop_original") || null == jo.getString("shop_original")
                    || "null".equals(jo.getString("shop_original")) || "".equals(jo.getString("shop_original"))) {
                mapObject.put("shop_original", "0");
            } else {
                mapObject.put("shop_original", jo.has("shop_original") ? jo.getString("shop_original") : "0");// 商品原价
            }
            if (!jo.has("shop_roll") || null == jo.getString("shop_roll") || "null".equals(jo.getString("shop_roll"))
                    || "".equals(jo.getString("shop_roll"))) {
                mapObject.put("shop_roll", "0");
            } else {
                mapObject.put("shop_roll", jo.has("shop_roll") ? jo.getString("shop_roll") : "0");// 商品拼团价格
            }
            list.add(mapObject);
        }
        return list;
    }

    /***
     * 1号渠道包切换登录界面
     */
    public static final HashMap<String, String> createLoginStyle(Context context, String result) throws Exception {

        HashMap<String, String> retInfo = new HashMap<String, String>();

        LogYiFu.e("Kr", result + "");
        JSONObject j = new JSONObject(result);

        if (j.getString("status").equals(1 + "")) {

            if (!j.has("data") || null == j.getString("data") || "null".equals(j.getString("data"))
                    || "".equals(j.getString("data"))) {
                retInfo.put("data", "");
            } else {
                String data = j.getString("data");
                retInfo.put("data", data);
            }
        }
        return retInfo;

    }

    /**
     * 获取活力值
     */
    // public static final int createVitality(Context context, String result)
    // throws Exception {
    // JSONObject jsonObject = new JSONObject(result);
    // if (null == jsonObject || "".equals(jsonObject)) {
    // return -1;
    // }
    //
    // if (1 == jsonObject.getInt("status")) {
    // if (!jsonObject.has("data") || null == jsonObject.getString("data")
    // || "null".equals(jsonObject.getString("data")) ||
    // "".equals(jsonObject.getString("data"))) {
    //
    // return -1;
    // } else {
    // return jsonObject.has("data") ? jsonObject.optInt("data") : -1;
    // }
    // }
    // return -1;
    //
    // }

    /**
     * 获得免单情况
     */
    public static final HashMap<String, String> createFreeUse(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        HashMap<String, String> map = new HashMap<String, String>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        String status = jsonObject.optString("status");
        if ("1".equals(status)) {
            if (!jsonObject.has("num") || null == jsonObject.getString("num")
                    || "null".equals(jsonObject.getString("num")) || "".equals(jsonObject.getString("num"))) {
                map.put("num", "-1");
            } else {
                map.put("num", jsonObject.optString("num", "-1"));
            }

            if (!jsonObject.has("order_code") || null == jsonObject.getString("order_code")
                    || "null".equals(jsonObject.getString("order_code"))
                    || "".equals(jsonObject.getString("order_code"))) {
                map.put("order_code", "");
            } else {
                map.put("order_code", jsonObject.optString("order_code", ""));
            }
            if (!jsonObject.has("op") || null == jsonObject.getString("op") || "null".equals(jsonObject.getString("op"))
                    || "".equals(jsonObject.getString("op"))) {
                map.put("op", "0");
            } else {
                map.put("op", jsonObject.optString("op", "0"));
            }
            return map;
        }
        return null;
    }

    /***
     * 更多专题
     */
    public static final List<HashMap<String, String>> createMoreSub(Context context, String result) throws Exception {
        List<HashMap<String, String>> lists = new ArrayList<HashMap<String, String>>();

        /**
         * “ collocation_code”: “MAOQ15423130” //专题ID
         * “collocation_pic”:”LIEnDjTm_600_900.jpg” //专题购图片 “collocation_name”:”
         * 简约百搭绣花拉链刺绣短裙”//专题购名称 “collocation_name2”:” 简约百搭绣花拉链刺绣短裙” //名称2 }]
         */

        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }

        JSONArray ja = new JSONArray(j.getString("data"));
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = (JSONObject) ja.opt(i);

            HashMap<String, String> map = new HashMap<String, String>();

            if (!jo.has("collocation_code") || null == jo.getString("collocation_code")
                    || jo.getString("collocation_code").equals("collocation_code")
                    || jo.getString("collocation_code").equals("")) {
                map.put("subID", "");
            } else {

                map.put("subID", jo.has("collocation_code") ? jo.optString("collocation_code") : "");
            }
            //
            //
            if (!jo.has("collocation_pic") || null == jo.getString("collocation_pic")
                    || jo.getString("collocation_pic").equals("collocation_pic")
                    || jo.getString("collocation_pic").equals("")) {
                map.put("subPIC", "");
            } else {

                map.put("subPIC", jo.has("collocation_pic") ? jo.optString("collocation_pic") : "");
            }
            //
            //
            //
            if (!jo.has("collocation_name") || null == jo.getString("collocation_name")
                    || jo.getString("collocation_name").equals("collocation_name")
                    || jo.getString("collocation_name").equals("")) {
                map.put("subName", "");
            } else {
                map.put("subName", jo.has("collocation_name") ? jo.optString("collocation_name") : "");
            }
            //
            //
            //
            //
            if (!jo.has("collocation_name2") || null == jo.getString("collocation_name2")
                    || jo.getString("collocation_name2").equals("collocation_name2")
                    || jo.getString("collocation_name2").equals("")) {
                map.put("subName2", "");
            } else {
                //
                map.put("subName2", jo.has("collocation_name2") ? jo.optString("collocation_name2") : "");
            }
            lists.add(map);

        }
        return lists;
    }

    /**
     * 获取专题详情
     */
    public static final HashMap<String, Object> createSpecialTopicDetails(Context context, String result)
            throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        String status = jsonObject.optString("status");
        if ("1".equals(status)) {
            String data = jsonObject.optString("shop");
            JSONObject j2 = new JSONObject(data);
            if (!j2.has("collocation_code") || null == j2.getString("collocation_code")
                    || "null".equals(j2.getString("collocation_code")) || "".equals(j2.getString("collocation_code"))) {
                map.put("collocation_code", "-1");
            } else {
                map.put("collocation_code", j2.optString("collocation_code", "-1"));
            }
            if (!j2.has("collocation_pic") || null == j2.getString("collocation_pic")
                    || "null".equals(j2.getString("collocation_pic")) || "".equals(j2.getString("collocation_pic"))) {
                map.put("collocation_pic", "-1");
            } else {
                map.put("collocation_pic", j2.optString("collocation_pic"));
            }
            if (!j2.has("collocation_name") || null == j2.getString("collocation_name")
                    || "null".equals(j2.getString("collocation_name")) || "".equals(j2.getString("collocation_name"))) {
                map.put("collocation_name", "-1");
            } else {
                map.put("collocation_name", j2.optString("collocation_name"));
            }
            if (!j2.has("collocation_name2") || null == j2.getString("collocation_name2")
                    || "null".equals(j2.getString("collocation_name2"))
                    || "".equals(j2.getString("collocation_name2"))) {
                map.put("collocation_name2", "-1");
            } else {
                map.put("collocation_name2", j2.optString("collocation_name2"));
            }
            if (!j2.has("collocation_remark") || null == j2.getString("collocation_remark")
                    || "null".equals(j2.getString("collocation_remark"))
                    || "".equals(j2.getString("collocation_remark"))) {
                map.put("collocation_remark", "-1");
            } else {
                map.put("collocation_remark", j2.optString("collocation_remark"));
            }
            if (!j2.has("add_time") || null == j2.getString("add_time") || "null".equals(j2.getString("add_time"))
                    || "".equals(j2.getString("add_time"))) {
                map.put("add_time", "-1");
            } else {
                map.put("add_time", j2.optString("add_time"));
            }

            JSONArray jsonArray = new JSONArray(j2.getString("shopList"));
            if (null == jsonArray || "".equals(jsonArray)) {
                map.put("shopList", null);
            } else {
                List<HashMap<String, Object>> listShop = new LinkedList<HashMap<String, Object>>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = (JSONObject) jsonArray.opt(i);
                    HashMap<String, Object> mapList = new HashMap<String, Object>();
                    if (!jo.has("shop_code") || null == jo.getString("shop_code")
                            || jo.getString("shop_code").equals("")) {
                        mapList.put("shop_code", "");
                    } else {
                        mapList.put("shop_code", jo.has("shop_code") ? jo.optString("shop_code") : "");
                    }
                    if (!jo.has("shop_name") || null == jo.getString("shop_name") + 0
                            || jo.getString("shop_name").equals("")) {
                        mapList.put("shop_name", "");
                    } else {
                        mapList.put("shop_name", jo.has("shop_name") ? jo.optString("shop_name") : "");
                    }

                    if (!jo.has("shop_price") || null == jo.getString("shop_price")
                            || jo.getString("shop_price").equals("null") || jo.getString("shop_price").equals("")) {
                        mapList.put("shop_price", "0");
                    } else {
                        mapList.put("shop_price", jo.has("shop_price") ? jo.optString("shop_price") : "0");
                    }
                    if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price")
                            || jo.getString("shop_se_price").equals("null")
                            || jo.getString("shop_se_price").equals("")) {
                        mapList.put("shop_se_price", "0");
                    } else {
                        mapList.put("shop_se_price", jo.has("shop_se_price") ? jo.optString("shop_se_price") : "0");
                    }
                    if (!jo.has("kickback") || null == jo.getString("kickback")
                            || jo.getString("kickback").equals("null") || jo.getString("kickback").equals("")) {
                        mapList.put("kickback", "0");
                    } else {
                        mapList.put("kickback", jo.has("kickback") ? jo.optString("kickback") : "0");
                    }
                    if (!jo.has("def_pic") || null == jo.getString("def_pic") || jo.getString("def_pic").equals("null")
                            || jo.getString("def_pic").equals("")) {
                        mapList.put("def_pic", "");
                    } else {
                        mapList.put("def_pic", jo.has("def_pic") ? jo.optString("def_pic") : "");
                    }
                    if (!jo.has("supp_label") || null == jo.getString("supp_label")
                            || jo.getString("supp_label").equals("null") || jo.getString("supp_label").equals("")) {
                        mapList.put("supp_label", "");
                    } else {
                        mapList.put("supp_label", jo.has("supp_label") ? jo.optString("supp_label") : "");
                    }
                    listShop.add(mapList);
                }
                map.put("shopList", listShop);
            }

            JSONArray jsonArray2 = new JSONArray(j2.getString("collocationList"));
            if (null == jsonArray2 || "".equals(jsonArray2)) {
                map.put("shopList", null);
            } else {
                List<HashMap<String, String>> collocationList = new ArrayList<HashMap<String, String>>();
                for (int j = 0; j < jsonArray2.length(); j++) {
                    JSONObject jo2 = (JSONObject) jsonArray2.opt(j);
                    HashMap<String, String> moreList = new HashMap<String, String>();
                    if (!jo2.has("collocation_code") || null == jo2.getString("collocation_code")
                            || jo2.getString("collocation_code").equals("null")
                            || jo2.getString("collocation_code").equals("")) {
                        moreList.put("collocation_code", "");
                    } else {
                        moreList.put("collocation_code",
                                jo2.has("collocation_code") ? jo2.optString("collocation_code") : "");
                    }
                    if (!jo2.has("collocation_pic") || null == jo2.getString("collocation_pic")
                            || jo2.getString("collocation_pic").equals("null")
                            || jo2.getString("collocation_pic").equals("")) {
                        moreList.put("collocation_pic", "");
                    } else {
                        moreList.put("collocation_pic",
                                jo2.has("collocation_pic") ? jo2.optString("collocation_pic") : "");
                    }
                    if (!jo2.has("collocation_name") || null == jo2.getString("collocation_name")
                            || jo2.getString("collocation_name").equals("null")
                            || jo2.getString("collocation_name").equals("")) {
                        moreList.put("collocation_name", "");
                    } else {
                        moreList.put("collocation_name",
                                jo2.has("collocation_name") ? jo2.optString("collocation_name") : "");
                    }
                    if (!jo2.has("collocation_name2") || null == jo2.getString("collocation_name2")
                            || jo2.getString("collocation_name2").equals("null")
                            || jo2.getString("collocation_name2").equals("")) {
                        moreList.put("collocation_name2", "");
                    } else {
                        moreList.put("collocation_name2",
                                jo2.has("collocation_name2") ? jo2.optString("collocation_name2") : "");
                    }
                    collocationList.add(moreList);
                }
                map.put("collocationList", collocationList);
            }
            return map;
        }
        return null;
    }

    /**
     * 获取下单前可使用的余额额度
     */
    public static final HashMap<String, String> createOrderAgo(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        HashMap<String, String> map = new HashMap<String, String>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        String status = jsonObject.optString("status");
        if ("1".equals(status)) {
            if (!jsonObject.has("maxRate") || null == jsonObject.getString("maxRate")
                    || "null".equals(jsonObject.getString("maxRate")) || "".equals(jsonObject.getString("maxRate"))) {
                map.put("maxRate", "0.1");
            } else {
                map.put("maxRate", jsonObject.optString("maxRate", "0.1"));// 最大百分之比
            }

            if (!jsonObject.has("maxMoney") || null == jsonObject.getString("maxMoney")
                    || "null".equals(jsonObject.getString("maxMoney")) || "".equals(jsonObject.getString("maxMoney"))) {
                map.put("maxMoney", "0");
            } else {
                map.put("maxMoney", jsonObject.optString("maxMoney", "0"));// 最大金额
            }
            return map;
        }
        return null;
    }

    /**
     * 获取抽奖结果
     */
    public static final HashMap<String, String> createLuckResult(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        HashMap<String, String> map = new HashMap<String, String>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!jsonObject.has("raffle") || null == jsonObject.getString("raffle")
                || "null".equals(jsonObject.getString("raffle")) || "".equals(jsonObject.getString("raffle"))) {
            map.put("raffle", "0");
        } else {
            map.put("raffle", jsonObject.optString("raffle", "0"));// 获得到的提现额度
        }

        if (!jsonObject.has("t") || null == jsonObject.getString("t")
                || "null".equals(jsonObject.getString("t")) || "".equals(jsonObject.getString("t"))) {
            map.put("t", "0");
        } else {
            map.put("t", jsonObject.optString("t", "0"));// t 表示抽中的是额度还是现金 0额度 1现金
        }

        if (!jsonObject.has("status") || null == jsonObject.getString("status")
                || "null".equals(jsonObject.getString("status")) || "".equals(jsonObject.getString("status"))) {
            map.put("status", "1");
        } else {
            map.put("status", jsonObject.optString("status", "1"));// 1
            // 代表正常，201代表衣豆不足

        }

        return map;

    }

    /**
     * 获取下单前可使用的余额额度
     */
    public static final HashMap<String, String> createGuidCard(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        HashMap<String, String> map = new HashMap<String, String>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        String status = jsonObject.optString("status");
        if ("1".equals(status)) {
            if (!jsonObject.has("data")) {
                return null;
            }
            String ja = jsonObject.getString("data");
            if (null == ja || "".equals(ja)) {
                return null;
            }
            JSONObject jo = new JSONObject(ja);
            if (!jo.has("sguidance") || null == jo.getString("sguidance") || jo.getString("sguidance").equals("null")
                    || jo.getString("sguidance").equals("")) {
                map.put("sguidance", "0");
            } else {
                map.put("sguidance", jo.has("sguidance") ? jo.optString("sguidance") : "0");
            }

            if (!jo.has("grade") || null == jo.getString("grade") || jo.getString("grade").equals("null")
                    || jo.getString("grade").equals("")) {
                map.put("grade", "");
            } else {
                map.put("grade", jo.has("grade") ? jo.optString("grade") : "0");
            }

            return map;
        }

        return null;
    }

    /**
     * 消息中心--话题
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final List<HashMap<String, Object>> createCenterMessageHuaTi(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray ja = new JSONArray(j.getString("data"));
        if (null == ja || "".equals(ja)) {
            return null;
        }
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = (JSONObject) ja.opt(i);

            HashMap<String, Object> map = new HashMap<String, Object>();

            if (!jo.has("user_id") || null == jo.getString("user_id") || jo.getString("user_id").equals("null")) {
                map.put("user_id", 0);
            } else {

                map.put("user_id", jo.has("user_id") ? jo.optInt("user_id") : 0);
            }

            if (!jo.has("nickname") || null == jo.getString("nickname")) {
                map.put("nickname", 0);
            } else {

                map.put("nickname", jo.has("nickname") ? jo.optString("nickname") : 0);
            }

            if (!jo.has("head_pic") || null == jo.getString("head_pic") || jo.getString("head_pic").equals("null")) {
                map.put("head_pic", 0);
            } else {

                map.put("head_pic", jo.has("head_pic") ? jo.getString("head_pic") : "0");
            }

            if (!jo.has("theme_id") || null == jo.getString("theme_id") || jo.getString("theme_id").equals("null")) {
                map.put("theme_id", -1);
            } else {

                map.put("theme_id", jo.has("theme_id") ? jo.optInt("theme_id") : -1);
            }

            if (!jo.has("type") || null == jo.getString("type") || jo.getString("type").equals("null")) {
                map.put("type", "-1");
            } else {

                map.put("type", jo.has("type") ? jo.optString("type") : "-1");
            }

            if (!jo.has("content") || null == jo.getString("content") || jo.getString("content").equals("null")) {
                map.put("content", "");
            } else {

                map.put("content", jo.has("content") ? jo.optString("content") : "");
            }

            if (!jo.has("num") || null == jo.getString("num") || jo.getString("num").equals("null")) {
                map.put("num", "-1");
            } else {

                map.put("num", jo.has("num") ? jo.optString("num") : "-1");
            }

            if (!jo.has("date") || null == jo.getString("date") || jo.getString("date").equals("null")) {
                map.put("date", "-1");
            } else {

                map.put("date", jo.has("date") ? jo.optString("date") : "-1");
            }

            if (!jo.has("id") || null == jo.getString("id") || jo.getString("id").equals("null")) {
                map.put("id", "-1");
            } else {

                map.put("id", jo.has("id") ? jo.optString("id") : "-1");
            }

            lists.add(map);
        }
        // LogYiFu.e("TAG", lists.toString());
        return lists;
    }

    /**
     * 获取额度最新25条奖励
     */
    public static final List<HashMap<String, String>> createExtractNewData(Context context, String result)
            throws JSONException {

        JSONObject jsonObject = new JSONObject(result);
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!"1".equals(jsonObject.getString("status"))) {
            return null;
        }
        JSONArray ja = jsonObject.getJSONArray("data");
        for (int i = 0; i < ja.length(); i++) {
            String js = ja.getString(i);

            JSONObject jo = new JSONObject(js);

            HashMap<String, String> map = new HashMap<String, String>();

            if (!jo.has("nname") || null == jo.getString("nname")) {
                map.put("nname", "*****");
            } else {
                map.put("nname", jo.optString("nname", "*****"));
            }
            if (!jo.has("num") || null == jo.getString("num") || "null".equals(jo.getString("num"))
                    || "".equals(jo.getString("num"))) {
                map.put("num", "+1元");
            } else {
                map.put("num", "+" + jo.optString("num", "1") + "元");
            }

            if (!jo.has("type") || null == jo.getString("type") || "null".equals(jo.getString("type"))
                    || "".equals(jo.getString("type"))) {
                map.put("p_name", "拆红包获得提现额度"); // 获取衣豆最新25条奖励 数据一致 使用同一个Adaptec
                // 键值保持一致
            } else {
                String type = jo.optString("type", "");
                String p_name = "拆红包获得提现额度";
//                if ("1".equals(type)) {
//                    p_name = "拆红包获得提现额度";
//                } else if ("2".equals(type)) {
//                    p_name = "夺宝退款获得提现额度";
//                } else if ("3".equals(type)) {
//                    p_name = "粉丝购物获得提现额度";
//                } else if ("4".equals(type)) {
//                    p_name = "官方赠送获得提现额度";
//                }
                map.put("p_name", p_name);
            }
            if (!jo.has("pic") || null == jo.getString("pic") || "null".equals(jo.getString("pic"))
                    || "".equals(jo.getString("pic"))) {
                map.put("pic", "");
            } else {
                map.put("pic", jo.optString("pic", ""));
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 获取衣豆最新25条奖励
     */
    public static final List<HashMap<String, String>> createYiDouNewData(Context context, String result)
            throws JSONException {

        JSONObject jsonObject = new JSONObject(result);
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!"1".equals(jsonObject.getString("status"))) {
            return null;
        }
        JSONArray ja = jsonObject.getJSONArray("data");
        for (int i = 0; i < ja.length(); i++) {
            String js = ja.getString(i);

            JSONObject jo = new JSONObject(js);

            HashMap<String, String> map = new HashMap<String, String>();

            if (!jo.has("nname") || null == jo.getString("nname")) {
                map.put("nname", "*****");
            } else {
                map.put("nname", jo.optString("nname", "*****"));
            }
            if (!jo.has("num") || null == jo.getString("num") || "null".equals(jo.getString("num"))
                    || "".equals(jo.getString("num"))) {
                map.put("num", "+1个");
            } else {
                map.put("num", "+" + jo.optString("num", "1") + "个");
            }

            if (!jo.has("p_name") || null == jo.getString("p_name") || "null".equals(jo.getString("p_name"))
                    || "".equals(jo.getString("p_name"))) {
                map.put("p_name", "");
            } else {
                map.put("p_name", jo.optString("p_name", ""));
            }
            if (!jo.has("pic") || null == jo.getString("pic") || "null".equals(jo.getString("pic"))
                    || "".equals(jo.getString("pic"))) {
                map.put("pic", "");
            } else {
                map.put("pic", jo.optString("pic", ""));
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 设置喜好列表
     */
    public static final HashMap<String, ArrayList<HashMap<String, String>>> createHobbyList(Context context,
                                                                                            String result) throws JSONException {

        JSONObject j = new JSONObject(result);
        LogYiFu.e("喜好列表", "解析-------" + result);
        LogYiFu.e("喜好列表", "解析JJJJJJ" + j);
        if (null == j || "".equals(j)) {
            return null;
        }
        // MyLogYiFu.e("resultA", result + "");
        HashMap<String, ArrayList<HashMap<String, String>>> maplist2 = new HashMap<String, ArrayList<HashMap<String, String>>>();

        if (1 == j.getInt("status")) {

            // 消费习惯
            ArrayList<HashMap<String, String>> xiaofeiList = new ArrayList<HashMap<String, String>>();
            // 喜爱风格
            ArrayList<HashMap<String, String>> loveStyleList = new ArrayList<HashMap<String, String>>();
            // 年龄段
            ArrayList<HashMap<String, String>> ageList = new ArrayList<HashMap<String, String>>();

            // 消费习惯
            JSONArray xiaofeiJ = new JSONArray(j.getString("1"));
            if (null == xiaofeiJ || "".equals(xiaofeiJ)) {
                return null;
            }
            for (int i = 0; i < xiaofeiJ.length(); i++) {

                JSONObject jo = (JSONObject) xiaofeiJ.opt(i);
                HashMap<String, String> xiaofeMap = new HashMap<String, String>();

                xiaofeMap.put("t_name", "1");
                if (!jo.has("like_id") || null == jo.getString("like_id") || "null".equals(jo.getString("like_id"))
                        || "".equals(jo.getString("like_id"))) {
                    xiaofeMap.put("like_id", "");
                } else {
                    xiaofeMap.put("like_id", jo.has("like_id") ? jo.getString("like_id") : "");
                }

                if (!jo.has("like_pic") || null == jo.getString("like_pic") || "null".equals(jo.getString("like_pic"))
                        || "".equals(jo.getString("like_pic"))) {
                    xiaofeMap.put("like_pic", "");
                } else {
                    xiaofeMap.put("like_pic", jo.has("like_pic") ? jo.getString("like_pic") : "");
                }

                if (!jo.has("like_name") || null == jo.getString("like_name")
                        || "null".equals(jo.getString("like_name")) || "".equals(jo.getString("like_id"))) {
                    xiaofeMap.put("attr_name", "");
                } else {
                    xiaofeMap.put("attr_name", jo.has("like_name") ? jo.getString("like_name") : "");
                }
                xiaofeiList.add(xiaofeMap); // 添加消费习惯
            }

            maplist2.put("1", xiaofeiList);

            // 喜爱风格
            JSONArray loveStyleJ = new JSONArray(j.getString("2"));
            if (null == loveStyleJ || "".equals(loveStyleJ)) {
                return null;
            }
            for (int i = 0; i < loveStyleJ.length(); i++) {
                JSONObject jo = (JSONObject) loveStyleJ.opt(i);
                HashMap<String, String> loveStyleMap = new HashMap<String, String>();
                loveStyleMap.put("t_name", "2");
                if (!jo.has("like_id") || null == jo.getString("like_id") || "null".equals(jo.getString("like_id"))
                        || "".equals(jo.getString("like_id"))) {
                    loveStyleMap.put("like_id", "");
                } else {
                    loveStyleMap.put("like_id", jo.has("like_id") ? jo.getString("like_id") : "");
                }
                if (!jo.has("like_pic") || null == jo.getString("like_pic") || "null".equals(jo.getString("like_pic"))
                        || "".equals(jo.getString("like_pic"))) {
                    loveStyleMap.put("like_pic", "");
                } else {
                    loveStyleMap.put("like_pic", jo.has("like_pic") ? jo.getString("like_pic") : "");
                }
                if (!jo.has("like_name") || null == jo.getString("like_name")
                        || "null".equals(jo.getString("like_name")) || "".equals(jo.getString("like_name"))) {
                    loveStyleMap.put("attr_name", "");
                } else {
                    loveStyleMap.put("attr_name", jo.has("like_name") ? jo.getString("like_name") : "");
                }
                loveStyleList.add(loveStyleMap); // 添加喜爱风格
            }
            maplist2.put("2", loveStyleList);
            // 年龄段
            JSONArray ageJ = new JSONArray(j.getString("3"));
            if (null == ageJ || "".equals(ageJ)) {
                return null;
            }
            for (int i = 0; i < ageJ.length(); i++) {
                JSONObject jo = (JSONObject) ageJ.opt(i);
                HashMap<String, String> ageMap = new HashMap<String, String>();
                ageMap.put("t_name", "3");
                if (!jo.has("like_id") || null == jo.getString("like_id") || "null".equals(jo.getString("like_id"))
                        || "".equals(jo.getString("like_id"))) {
                    ageMap.put("like_id", "");
                } else {
                    ageMap.put("like_id", jo.has("like_id") ? jo.getString("like_id") : "");
                }
                if (!jo.has("like_pic") || null == jo.getString("like_pic") || "null".equals(jo.getString("like_pic"))
                        || "".equals(jo.getString("like_pic"))) {
                    ageMap.put("like_pic", "");
                } else {
                    ageMap.put("like_pic", jo.has("like_pic") ? jo.getString("like_pic") : "");
                }
                if (!jo.has("like_name") || null == jo.getString("like_name")
                        || "null".equals(jo.getString("like_name")) || "".equals(jo.getString("like_name"))) {
                    ageMap.put("attr_name", "");
                } else {
                    ageMap.put("attr_name", jo.has("like_name") ? jo.getString("like_name") : "");
                }
                ageList.add(ageMap); // 年龄段
            }
            maplist2.put("3", ageList);

            return maplist2;

        }

        return null;
    }

    /**
     * 获取分类 热门搜索标签
     */
    public static final List<HashMap<String, String>> createHotTag(Context context, String result)
            throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return list;
        }
        String status = jsonObject.optString("status");
        if ("1".equals(status)) {
            if (!jsonObject.has("hotTagList")) {
                return list;
            }
            JSONArray jo = jsonObject.getJSONArray("hotTagList");
            for (int i = 0; i < jo.length(); i++) {
                HashMap<String, String> tagMap = new HashMap<String, String>();
                JSONObject ja = jo.getJSONObject(i);
                if (!ja.has("tag_id") || null == ja.getString("tag_id") || "null".equals(ja.getString("tag_id"))
                        || "".equals(ja.getString("tag_id"))) {
                    tagMap.put("tag_id", "");
                } else {
                    tagMap.put("tag_id", ja.has("tag_id") ? ja.getString("tag_id") : "");// 标签ID
                }
                if (!ja.has("count") || null == ja.getString("count") || "null".equals(ja.getString("count"))
                        || "".equals(ja.getString("count"))) {
                    tagMap.put("count", "0");
                } else {
                    tagMap.put("count", ja.has("count") ? ja.getString("count") : "0");// 标签权重
                }
                list.add(tagMap);
            }
        }

        return list;
    }

    /****
     * 提现额度明细
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static final List<HashMap<String, Object>> createTiXianEduLiebiaoDongjie(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray ja = new JSONArray(j.getString("data"));
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = (JSONObject) ja.opt(i);

            HashMap<String, Object> map = new HashMap<String, Object>();

            if (!jo.has("id") || null == jo.getString("id") || jo.getString("id").equals("null")) {
                map.put("id", 0);
            } else {

                map.put("id", jo.has("id") ? jo.optInt("id") : 0);
            }

            if (!jo.has("user_id") || null == jo.getString("user_id") || jo.getString("user_id").equals("null")) {
                map.put("user_id", 0);
            } else {

                map.put("user_id", jo.has("user_id") ? jo.optInt("user_id") : 0);
            }
            // r_code:订单编号
            if (!jo.has("r_code") || null == jo.getString("r_code") || jo.getString("r_code").equals("null")) {
                map.put("r_code", 0);
            } else {

                map.put("r_code", jo.has("r_code") ? jo.optString("r_code") : 0);
            }

            /**
             * num:数量 （元）
             */

            if (!jo.has("num") || null == jo.getString("num") || jo.getString("num").equals("null")) {
                map.put("num", 0);
            } else {

                map.put("num", jo.has("num") ? jo.getString("num") : "0");
            }

            /**
             *
             * type: 1疯狂星期一
             *
             */
            if (!jo.has("type") || null == jo.getString("type") || jo.getString("type").equals("null")) {
                map.put("type", 0);
            } else {

                map.put("type", jo.has("type") ? jo.optInt("type") : 0);
            }

            if (!jo.has("add_date") || null == jo.getString("add_date") || jo.getString("add_date").equals("null")) {
                map.put("add_date", "");
            } else {

                map.put("add_date", jo.has("add_date") ? jo.optString("add_date") : "");
            }

            if (!jo.has("add_time") || null == jo.getString("add_time") || jo.getString("add_time").equals("null")) {
                map.put("add_time", "");
            } else {

                map.put("add_time", jo.has("add_time") ? jo.optString("add_time") : "");
            }

            // map.put("remark", jo.has("remark") ? jo.optString("remark") :
            // "");
            // map.put("id", jo.has("id") ? jo.optInt("id") : 0);
            // map.put("user_id", jo.has("user_id") ? jo.optInt("user_id") : 0);
            // map.put("num", jo.has("num") ? jo.optInt("num") : 0);
            // map.put("type", jo.has("type") ? jo.optInt("type") : 0);
            // map.put("add_time", jo.has("add_time") ? jo.optString("add_time")
            // : "");
            lists.add(map);
        }
        // LogYiFu.e("TAG", lists.toString());
        return lists;
    }

    /**
     * 获取体现额度奖励列表
     */
    public static final List<HashMap<String, String>> createTixienEduList(Context context, String result)
            throws JSONException {

        JSONObject jsonObject = new JSONObject(result);
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!"1".equals(jsonObject.getString("status"))) {
            return null;
        }
        JSONArray ja = jsonObject.getJSONArray("data");
        for (int i = 0; i < ja.length(); i++) {
            String js = ja.getString(i);

            JSONObject jo = new JSONObject(js);

            HashMap<String, String> map = new HashMap<String, String>();

            if (!jo.has("nname") || null == jo.getString("nname")) {
                map.put("nname", "*****");
            } else {
                map.put("nname", jo.optString("nname", "*****"));
            }
            if (!jo.has("num") || null == jo.getString("num") || "null".equals(jo.getString("num"))
                    || "".equals(jo.getString("num"))) {
                map.put("num", "");
            } else {
                map.put("num", jo.optString("num"));
            }

            if (!jo.has("pic") || null == jo.getString("pic") || "null".equals(jo.getString("pic"))
                    || "".equals(jo.getString("pic"))) {
                map.put("pic", "");
            } else {
                map.put("pic", jo.optString("pic", ""));
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 购物车里我的喜爱
     */
    public static final List<HashMap<String, Object>> createMyLoveList(Context context, String result)
            throws JSONException {
        // result = result.replace("null", "\"\"");
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return retInfo;
        }
        if (!j.has("list") || null == j.getString("list") || "null".equals(j.getString("list"))) {
            return retInfo;
        }
        if (!"1".equals(j.getString("status"))) {
            return retInfo;
        }
        JSONArray jsonArray = j.getJSONArray("list");
        if (null == jsonArray || "".equals(jsonArray)) {
            return retInfo;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            // JSONObject jo = (JSONObject) jsonArray.opt(i);
            JSONObject jo = jsonArray.getJSONObject(i);
            if (jo == null || "".equals(jo)) {
                continue;
            }
            // if (jo.optString("def_pic").equals("null") ||
            // jo.optString("def_pic").equals("")
            // || jo.optString("def_pic") == null) {
            // continue;
            // }
            HashMap<String, Object> mapObject = new HashMap<String, Object>();
            if (!jo.has("shop_code") || null == jo.getString("shop_code") || jo.getString("shop_code").equals("null")) {
                mapObject.put("shop_code", "0");
            } else {

                mapObject.put("shop_code", jo.optString("shop_code"));
            }
            if (!jo.has("shop_name") || null == jo.getString("shop_name") || jo.getString("shop_name").equals("null")) {
                mapObject.put("shop_name", "0");
            } else {

                mapObject.put("shop_name", jo.optString("shop_name"));
            }
            if (!jo.has("shop_price") || null == jo.getString("shop_price")
                    || jo.getString("shop_price").equals("null")) {
                mapObject.put("shop_price", jo.optString("shop_price"));
            } else {

                mapObject.put("shop_price", jo.has("shop_price") ? jo.getString("shop_price") : "0");
            }
            if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price")
                    || jo.getString("shop_se_price").equals("null")) {
                mapObject.put("shop_se_price", "0");
            } else {

                mapObject.put("shop_se_price", jo.optString("shop_se_price"));
            }
            // 把show_pic放进def_pic是因为使用了以前的界面
            if (!jo.has("show_pic") || null == jo.getString("show_pic") || jo.getString("show_pic").equals("null")) {
                mapObject.put("def_pic", "0");
            } else {
                mapObject.put("def_pic", jo.has("show_pic") ? jo.getString("show_pic") : "0");
            }
            if (!jo.has("supp_label") || null == jo.getString("supp_label") || jo.getString("supp_label").equals("null")
                    || jo.getString("supp_label").equals("")) {
                mapObject.put("supp_label", "");
            } else {
                mapObject.put("supp_label", jo.has("supp_label") ? jo.optString("supp_label") : "");
            }

            // 以下全部是本接口没有的，给默认值主要是为了用到的界面不报错
            if (!jo.has("kickback") || null == jo.getString("kickback") || jo.getString("kickback").equals("null")) {
                mapObject.put("kickback", 0);
            } else {
                mapObject.put("kickback", jo.has("kickback") ? jo.getString("kickback") : "0");
            }

            if (!jo.has("isLike") || null == jo.getString("isLike") || jo.getString("isLike").equals("null")) {
                mapObject.put("isLike", "0");
            } else {

                mapObject.put("isLike", jo.optString("isLike"));
            }
            if (!jo.has("is_del") || null == jo.getString("is_del") || jo.getString("is_del").equals("null")) {
                mapObject.put("is_del", "0");
            } else {

                mapObject.put("is_del", jo.optString("is_del"));
            }
            if (!jo.has("isCart") || null == jo.getString("isCart") || jo.getString("isCart").equals("null")) {
                mapObject.put("isCart", "0");
            } else {

                mapObject.put("isCart", jo.optString("isCart"));
            }
            retInfo.add(mapObject);
        }
        return retInfo;
    }

    /**
     * 购物车里修改颜色尺寸
     */
    public static final HashMap<String, String> modifyColorSize(Context context, String result) throws JSONException {
        HashMap<String, String> retInfo = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
                || j.getString("status").equals("")) {
            retInfo.put("status", "-1000");
        } else {
            retInfo.put("status", j.getString("status"));
        }

        if (!j.has("message") || null == j.getString("message") || j.getString("message").equals("")) {
            retInfo.put("message", "");
        } else {
            retInfo.put("message", j.getString("message"));
        }
        return retInfo;
    }

    /**
     * 主界面商品列表
     */
    public static final List<CardDataItem> createLikeQua(Context context, String result) throws JSONException {
        // HashMap<String, Object> retInfo = new HashMap<String, Object>();
        List<CardDataItem> retInfo = new ArrayList<CardDataItem>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (!j.has("pageCount") || null == j.getString("pageCount") || j.getString("pageCount").equals("")
                || j.getString("pageCount").equals("null")) {
            SharedPreferencesUtil.saveStringData(context, Pref.JINGXUAN_PAGECOUNT, "1");
        } else {
            SharedPreferencesUtil.saveStringData(context, Pref.JINGXUAN_PAGECOUNT, j.optString("pageCount", "1"));
        }
        JSONArray ja = j.getJSONArray("likes");
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.getJSONObject(i);
            CardDataItem item = new CardDataItem();
            if (!jo.has("def_pic") || null == jo.getString("def_pic") || jo.getString("def_pic").equals("")
                    || jo.getString("def_pic").equals("null")) {
                item.setDef_pic("");
            } else {
                item.setDef_pic(jo.optString("def_pic"));
            }
            if (!jo.has("shop_name") || null == jo.getString("shop_name") || jo.getString("shop_name").equals("")
                    || jo.getString("shop_name").equals("null")) {
                item.setShop_name("");
            } else {
                item.setShop_name(jo.optString("shop_name"));
            }
            if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price")
                    || jo.getString("shop_se_price").equals("") || jo.getString("shop_se_price").equals("null")) {
                item.setShop_se_price("0");
            } else {
                item.setShop_se_price(jo.optString("shop_se_price"));
            }

            if (!jo.has("supp_label") || null == jo.getString("supp_label") || jo.getString("supp_label").equals("")
                    || jo.getString("supp_label").equals("null")) {
                item.setSupp_label("");
            } else {
                item.setSupp_label(jo.optString("supp_label"));
            }
            if (!jo.has("shop_code") || null == jo.getString("shop_code") || jo.getString("shop_code").equals("")
                    || jo.getString("shop_code").equals("null")) {
                item.setShop_code("");
            } else {
                item.setShop_code(jo.optString("shop_code"));
            }

            if (!jo.has("score") || null == jo.getString("score") || jo.getString("score").equals("")
                    || jo.getString("score").equals("null")) {
                item.setScore("");
            } else {
                item.setScore(jo.optString("score"));
            }
            if (!jo.has("add_time") || null == jo.getString("add_time") || jo.getString("add_time").equals("")
                    || jo.getString("add_time").equals("null")) {
                item.setAdd_time("0");
            } else {
                item.setAdd_time(jo.optString("add_time"));
            }
            retInfo.add(item);
        }
        return retInfo;
    }

    /**
     * 密友圈首页列表
     */
    public static List<HashMap<String, Object>> intimateListSqa = new ArrayList<HashMap<String, Object>>();
    public static List<HashMap<String, Object>> intimateListFri = new ArrayList<HashMap<String, Object>>();

    public static final List<HashMap<String, Object>> createIntimateList(Context context, String result, String type,
                                                                         String curPage) throws JSONException {
        if ("1".equals(curPage)) {
            if ("1".equals(type)) {
                intimateListSqa.clear();
            }
            if ("2".equals(type)) {
                intimateListFri.clear();
            }
        }
        // SharedPreferencesUtil.saveStringData(context,
        // Pref.INTIMATE_LIST_SIZE, "0");
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
        List<HashMap<String, Object>> retInfo2 = new ArrayList<HashMap<String, Object>>();
        // List<HashMap<String, Object>> retInfoTemp = new
        // ArrayList<HashMap<String, Object>>();

        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        if (!"1".equals(jsonObject.getString("status"))) {
            return null;
        }
        JSONArray ja = null;

        for (int d = 0; d < 2; d++) {
            if (d == 0) {// data数据
                if (!jsonObject.has("data") || null == jsonObject.getString("data")
                        || "null".equals(jsonObject.getString("data")) || "".equals(jsonObject.getString("data"))) {
                    continue;
                } else {
                    ja = jsonObject.getJSONArray("data");
                }
            } else if (d == 1) {// data2数据
                if (!jsonObject.has("data2") || null == jsonObject.getString("data2")
                        || "null".equals(jsonObject.getString("data2")) || "".equals(jsonObject.getString("data2"))) {
                    continue;
                } else {
                    ja = jsonObject.getJSONArray("data2");
                }
            }

            if (null != ja && !"".equals(ja)) {
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    HashMap<String, Object> mapRet = new HashMap<String, Object>();
                    String theme_id = "";
                    if (!jo.has("theme_id") || null == jo.getString("theme_id")
                            || "null".equals(jo.getString("theme_id")) || "".equals(jo.getString("theme_id"))) {
                        mapRet.put("theme_id", "");
                    } else {
                        theme_id = jo.has("theme_id") ? jo.getString("theme_id") : "";
                        mapRet.put("theme_id", theme_id);
                    }
                    if (!jo.has("user_id") || null == jo.getString("user_id") || "null".equals(jo.getString("user_id"))
                            || "".equals(jo.getString("user_id"))) {
                        mapRet.put("user_id", "");
                    } else {
                        mapRet.put("user_id", jo.has("user_id") ? jo.getString("user_id") : "");
                    }
                    if (!jo.has("head_pic") || null == jo.getString("head_pic")
                            || "null".equals(jo.getString("head_pic")) || "".equals(jo.getString("head_pic"))) {
                        mapRet.put("head_pic", "");
                    } else {
                        mapRet.put("head_pic", jo.has("head_pic") ? jo.getString("head_pic") : "");
                    }
                    if (!jo.has("title") || null == jo.getString("title") || "null".equals(jo.getString("title"))
                            || "".equals(jo.getString("title"))) {
                        mapRet.put("title", "");
                    } else {
                        mapRet.put("title", jo.has("title") ? jo.getString("title") : "");
                    }
                    if (!jo.has("nickname") || null == jo.getString("nickname")) {
                        mapRet.put("nickname", "");
                    } else {
                        mapRet.put("nickname", jo.has("nickname") ? jo.getString("nickname") : "");
                    }
                    if (!jo.has("date") || null == jo.getString("date") || "null".equals(jo.getString("date"))
                            || "".equals(jo.getString("date"))) {
                        mapRet.put("date", "");
                    } else {
                        mapRet.put("date", jo.has("date") ? jo.getString("date") : "");
                    }
                    if (!jo.has("send_time") || null == jo.getString("send_time")
                            || "null".equals(jo.getString("send_time")) || "".equals(jo.getString("send_time"))) {
                        mapRet.put("send_time", "0");
                    } else {
                        mapRet.put("send_time", jo.has("send_time") ? jo.getString("send_time") : "0");
                    }
                    if (!jo.has("content") || null == jo.getString("content")) {
                        mapRet.put("content", "");
                    } else {
                        mapRet.put("content", jo.has("content") ? jo.getString("content") : "");
                    }
                    if (!jo.has("pics") || null == jo.getString("pics") || "null".equals(jo.getString("pics"))
                            || "".equals(jo.getString("pics"))) {
                        mapRet.put("pics", "");
                    } else {
                        mapRet.put("pics", jo.has("pics") ? jo.getString("pics") : "");
                    }

                    List<String> tagList = new ArrayList<String>();
                    if (!jo.has("tags") || null == jo.getString("tags") || "null".equals(jo.getString("tags"))
                            || "".equals(jo.getString("tags"))) {
                        mapRet.put("tags", tagList);
                    } else {
                        JSONArray jaSonList = jo.getJSONArray("tags");
                        for (int k = 0; k < jaSonList.length(); k++) {
                            String object = jaSonList.getString(k);
                            tagList.add(object);
                        }
                        mapRet.put("tags", tagList);
                    }
                    if (!jo.has("location") || null == jo.getString("location")
                            || "null".equals(jo.getString("location")) || "".equals(jo.getString("location"))) {
                        mapRet.put("location", "");
                    } else {
                        mapRet.put("location", jo.has("location") ? jo.getString("location") : "");
                    }
                    if (!jo.has("theme_type") || null == jo.getString("theme_type")
                            || "null".equals(jo.getString("theme_type")) || "".equals(jo.getString("theme_type"))) {
                        mapRet.put("theme_type", "");
                    } else {
                        mapRet.put("theme_type", jo.has("theme_type") ? jo.getString("theme_type") : "");
                    }
                    if (!jo.has("applaud_num") || null == jo.getString("applaud_num")
                            || "null".equals(jo.getString("applaud_num")) || "".equals(jo.getString("applaud_num"))) {
                        mapRet.put("applaud_num", "0");
                    } else {
                        mapRet.put("applaud_num", jo.has("applaud_num") ? jo.getString("applaud_num") : "0");
                    }
                    if (!jo.has("comment_count") || null == jo.getString("comment_count")
                            || "null".equals(jo.getString("comment_count"))
                            || "".equals(jo.getString("comment_count"))) {
                        mapRet.put("comment_count", "0");
                    } else {
                        mapRet.put("comment_count", jo.has("comment_count") ? jo.getString("comment_count") : "0");
                    }
                    // 关注状态0未关注1，已经关注
                    if (!jo.has("attention_status") || null == jo.getString("attention_status")
                            || "null".equals(jo.getString("attention_status"))
                            || "".equals(jo.getString("attention_status"))) {
                        mapRet.put("attention_status", "");
                    } else {
                        mapRet.put("attention_status",
                                jo.has("attention_status") ? jo.getString("attention_status") : "");
                    }
                    // 点赞状态（ 0 未点赞1 已经点赞）
                    if (!jo.has("applaud_status") || null == jo.getString("applaud_status")
                            || "null".equals(jo.getString("applaud_status"))
                            || "".equals(jo.getString("applaud_status"))) {
                        mapRet.put("applaud_status", "0");
                    } else {
                        mapRet.put("applaud_status", jo.has("applaud_status") ? jo.getString("applaud_status") : "0");
                    }
                    // 穿搭 风格标签
                    // if (!jo.has("style") || null == jo.getString("style") ||
                    // "null".equals(jo.getString("style"))
                    // || "".equals(jo.getString("style"))) {
                    // mapRet.put("style", "");
                    // } else {
                    // mapRet.put("style", jo.has("style") ?
                    // jo.getString("style") : "");
                    // }
                    // // 穿搭 品牌标签
                    // if (!jo.has("supp_label_id") || null ==
                    // jo.getString("supp_label_id")
                    // || "null".equals(jo.getString("supp_label_id"))
                    // || "".equals(jo.getString("supp_label_id"))) {
                    // mapRet.put("supp_label_id", "");
                    // } else {
                    // mapRet.put("supp_label_id", jo.has("supp_label_id") ?
                    // jo.getString("supp_label_id") : "");
                    // }
                    // 收藏状态状态（0：为未收藏，1：为收藏）
                    if (!jo.has("collection_status") || null == jo.getString("collection_status")
                            || "null".equals(jo.getString("collection_status"))
                            || "".equals(jo.getString("collection_status"))) {
                        mapRet.put("collection_status", "0");
                    } else {
                        mapRet.put("collection_status",
                                jo.has("collection_status") ? jo.getString("collection_status") : "0");
                    }
                    // 收藏数量
                    if (!jo.has("collect_num") || null == jo.getString("collect_num")
                            || "null".equals(jo.getString("collect_num")) || "".equals(jo.getString("collect_num"))) {
                        mapRet.put("collect_num", "0");
                    } else {
                        mapRet.put("collect_num", jo.has("collect_num") ? jo.getString("collect_num") : "0");
                    }
                    // 0：为普通用户 ，1：为红V，2：为蓝V
                    if (!jo.has("v_ident") || null == jo.getString("v_ident") || "null".equals(jo.getString("v_ident"))
                            || "".equals(jo.getString("v_ident"))) {
                        mapRet.put("v_ident", "0");
                    } else {
                        mapRet.put("v_ident", jo.has("v_ident") ? jo.getString("v_ident") : "0");
                    }
                    // 品牌和风格
                    List<HashMap<String, Object>> supp_label_list = new ArrayList<HashMap<String, Object>>();
                    if (!jo.has("supp_label_list") || null == jo.getString("supp_label_list")
                            || "null".equals(jo.getString("supp_label_list"))
                            || "".equals(jo.getString("supp_label_list"))) {
                    } else {
                        JSONArray jsonArraySuppLabelList = jo.getJSONArray("supp_label_list");
                        for (int k = 0; k < jsonArraySuppLabelList.length(); k++) {
                            JSONObject jk = (JSONObject) jsonArraySuppLabelList.opt(k);
                            HashMap<String, Object> mapObjectSuppLabelList = new HashMap<String, Object>();
                            if (!jk.has("label_id") || null == jk.getString("label_id")
                                    || "null".equals(jk.getString("label_id")) || jk.getString("label_id").equals("")) {
                                mapObjectSuppLabelList.put("label_id", "");
                            } else {
                                mapObjectSuppLabelList.put("label_id",
                                        jk.has("label_id") ? jk.optString("label_id") : "");
                            }
                            if (!jk.has("only_id") || null == jk.getString("only_id")
                                    || "null".equals(jk.getString("only_id")) || jk.getString("only_id").equals("")) {
                                mapObjectSuppLabelList.put("only_id", "");
                            } else {
                                mapObjectSuppLabelList.put("only_id", jk.has("only_id") ? jk.optString("only_id") : "");
                            }
                            if (!jk.has("type1") || null == jk.getString("type1")
                                    || "null".equals(jk.getString("type1")) || jk.getString("type1").equals("")) {
                                mapObjectSuppLabelList.put("type1", "");
                            } else {
                                mapObjectSuppLabelList.put("type1", jk.has("type1") ? jk.optString("type1") : "");
                            }
                            if (!jk.has("type2") || null == jk.getString("type2")
                                    || "null".equals(jk.getString("type2")) || jk.getString("type2").equals("")) {
                                mapObjectSuppLabelList.put("type2", "");
                            } else {
                                mapObjectSuppLabelList.put("type2", jk.has("type2") ? jk.optString("type2") : "");
                            }
                            if (!jk.has("style") || null == jk.getString("style")
                                    || "null".equals(jk.getString("style")) || jk.getString("style").equals("")) {
                                mapObjectSuppLabelList.put("style", "");
                            } else {
                                mapObjectSuppLabelList.put("style", jk.has("style") ? jk.optString("style") : "");
                            }
                            // 用来区分后台标签和用户自定义标签 1：后台标签 2：用户自定义的标签
                            if (!jk.has("label_type") || null == jk.getString("label_type")
                                    || "null".equals(jk.getString("label_type"))
                                    || jk.getString("label_type").equals("")) {
                                mapObjectSuppLabelList.put("label_type", "");
                            } else {
                                mapObjectSuppLabelList.put("label_type",
                                        jk.has("label_type") ? jk.optString("label_type") : "");
                            }
                            if (!jk.has("label_x") || null == jk.getString("label_x")
                                    || "null".equals(jk.getString("label_x")) || jk.getString("label_x").equals("")) {
                                mapObjectSuppLabelList.put("label_x", "");
                            } else {
                                mapObjectSuppLabelList.put("label_x", jk.has("label_x") ? jk.optString("label_x") : "");
                            }
                            if (!jk.has("label_y") || null == jk.getString("label_y")
                                    || "null".equals(jk.getString("label_y")) || jk.getString("label_y").equals("")) {
                                mapObjectSuppLabelList.put("label_y", "");
                            } else {
                                mapObjectSuppLabelList.put("label_y", jk.has("label_y") ? jk.optString("label_y") : "");
                            }
                            supp_label_list.add(mapObjectSuppLabelList);
                        }
                    }
                    mapRet.put("supp_label_list", supp_label_list);
                    // 评论列表
                    List<HashMap<String, String>> comments_list = new ArrayList<HashMap<String, String>>();
                    if (!jo.has("comments_list") || null == jo.getString("comments_list")
                            || "null".equals(jo.getString("comments_list"))
                            || "".equals(jo.getString("comments_list"))) {
                    } else {
                        JSONArray jsonArrayCommentsList = jo.getJSONArray("comments_list");
                        for (int k = 0; k < jsonArrayCommentsList.length(); k++) {
                            JSONObject jk = (JSONObject) jsonArrayCommentsList.opt(k);
                            HashMap<String, String> mapObjectCommentsList = new HashMap<String, String>();
                            String base_user_id = "";// 帖子的用户ID
                            if (!jk.has("base_user_id") || null == jk.getString("base_user_id")
                                    || "null".equals(jk.getString("base_user_id"))
                                    || jk.getString("base_user_id").equals("")) {
                                mapObjectCommentsList.put("base_user_id", "");
                            } else {
                                base_user_id = jk.has("base_user_id") ? jk.optString("base_user_id") : "";
                                mapObjectCommentsList.put("base_user_id",
                                        jk.has("base_user_id") ? jk.optString("base_user_id") : "");
                            }
                            String user_id = "";// 评论帖子的用户ID
                            if (!jk.has("user_id") || null == jk.getString("user_id")
                                    || "null".equals(jk.getString("user_id")) || jk.getString("user_id").equals("")) {
                                mapObjectCommentsList.put("user_id", "");
                            } else {
                                user_id = jk.has("user_id") ? jk.optString("user_id") : "";
                                mapObjectCommentsList.put("user_id", jk.has("user_id") ? jk.optString("user_id") : "");
                            }
                            if (user_id.equals(base_user_id)) {
                                mapObjectCommentsList.put("nickname", "楼主");
                            } else {
                                if (!jk.has("nickname") || null == jk.getString("nickname")
                                        || jk.getString("nickname").equals("")) {
                                    mapObjectCommentsList.put("nickname", "");
                                } else {
                                    mapObjectCommentsList.put("nickname",
                                            jk.has("nickname") ? jk.optString("nickname") : "");
                                }
                            }
                            if (!jk.has("content") || null == jk.getString("content")
                                    || jk.getString("content").equals("")) {
                                mapObjectCommentsList.put("content", "");
                            } else {
                                mapObjectCommentsList.put("content", jk.has("content") ? jk.optString("content") : "");
                            }

                            String status;//  0为展示  1为仅自己可见
                            if (!jk.has("status") || null == jk.getString("status")
                                    || jk.getString("status").equals("")) {
                                status = "0";
                                mapObjectCommentsList.put("status", "0");
                            } else {
                                status = jk.optString("status");
                                mapObjectCommentsList.put("status", jk.has("status") ? jk.optString("status") : "0");
                            }
                            mapObjectCommentsList.put("comments_type", "1");// 1标识是评论
                            // 2
                            // 回复
                            if (!YJApplication.instance.isLoginSucess()) {//未登录
                                if (!"1".equals(status)) {
                                    comments_list.add(mapObjectCommentsList);
                                }

                            } else if ("1".equals(status) && (!user_id.equals(YCache.getCacheUser(context).getUser_id()))) {
                                //status  0为展示  1为仅自己可见
                            } else {
                                comments_list.add(mapObjectCommentsList);
                            }

                            // 评论里面的回复
                            if (!jk.has("replies_list") || null == jk.getString("replies_list")
                                    || "null".equals(jk.getString("replies_list"))
                                    || "".equals(jk.getString("replies_list"))) {
                            } else {
                                JSONArray jsonArrayCommentsList2 = jk.getJSONArray("replies_list");
                                for (int kk = 0; kk < jsonArrayCommentsList2.length(); kk++) {
                                    if (jsonArrayCommentsList2.getString(kk) == null
                                            || "null".equals(jsonArrayCommentsList2.getString(kk))) {
                                        continue;
                                    }
                                    JSONObject jkk = (JSONObject) jsonArrayCommentsList2.opt(kk);
                                    HashMap<String, String> mapObjectCommentsList2 = new HashMap<String, String>();
                                    String send_user_id = "";// 回复的用户ID
                                    if (!jkk.has("send_user_id") || null == jkk.getString("send_user_id")
                                            || "null".equals(jkk.getString("send_user_id"))
                                            || jkk.getString("send_user_id").equals("")) {
                                        mapObjectCommentsList2.put("send_user_id", "");
                                    } else {
                                        send_user_id = jkk.has("send_user_id") ? jkk.optString("send_user_id") : "";
                                        mapObjectCommentsList2.put("send_user_id",
                                                jkk.has("send_user_id") ? jkk.optString("send_user_id") : "");
                                    }
                                    String receive_user_id = "";// 被回复的用户ID
                                    if (!jkk.has("receive_user_id") || null == jkk.getString("receive_user_id")
                                            || "null".equals(jkk.getString("receive_user_id"))
                                            || jkk.getString("receive_user_id").equals("")) {
                                        mapObjectCommentsList2.put("receive_user_id", "");
                                    } else {
                                        receive_user_id = jkk.has("receive_user_id") ? jkk.optString("receive_user_id")
                                                : "";
                                        mapObjectCommentsList2.put("receive_user_id",
                                                jkk.has("receive_user_id") ? jkk.optString("receive_user_id") : "");
                                    }
                                    if (send_user_id.equals(base_user_id)) {
                                        mapObjectCommentsList2.put("send_nickname", "楼主");
                                    } else {
                                        if (!jkk.has("send_nickname") || null == jkk.getString("send_nickname")
                                                || jkk.getString("send_nickname").equals("")) {
                                            mapObjectCommentsList2.put("send_nickname", "");
                                        } else {
                                            mapObjectCommentsList2.put("send_nickname",
                                                    jkk.has("send_nickname") ? jkk.optString("send_nickname") : "");
                                        }
                                    }
                                    if (receive_user_id.equals(base_user_id)) {
                                        mapObjectCommentsList2.put("receive_nickname", "楼主");
                                    } else {
                                        if (!jkk.has("receive_nickname") || null == jkk.getString("receive_nickname")
                                                || jkk.getString("receive_nickname").equals("")) {
                                            mapObjectCommentsList2.put("receive_nickname", "");
                                        } else {
                                            mapObjectCommentsList2.put("receive_nickname", jkk.has("receive_nickname")
                                                    ? jkk.optString("receive_nickname") : "");
                                        }
                                    }
                                    if (!jkk.has("content") || null == jkk.getString("content")
                                            || jkk.getString("content").equals("")) {
                                        mapObjectCommentsList2.put("content", "");
                                    } else {
                                        mapObjectCommentsList2.put("content",
                                                jkk.has("content") ? jkk.optString("content") : "");
                                    }
                                    String repstatus;//  0为展示  1为仅自己可见
                                    if (!jkk.has("status") || null == jkk.getString("status")
                                            || jkk.getString("status").equals("")) {
                                        repstatus = "0";
                                        mapObjectCommentsList.put("status", "0");
                                    } else {
                                        repstatus = jkk.optString("status");
                                        mapObjectCommentsList.put("status", jkk.has("status") ? jkk.optString("status") : "0");
                                    }
                                    mapObjectCommentsList2.put("comments_type", "2");// 1标识是评论
                                    // 2
                                    // 回复
                                    if (!YJApplication.instance.isLoginSucess()) {//未登录
                                        if (!"1".equals(repstatus)) {
                                            comments_list.add(mapObjectCommentsList2);
                                        }
                                    } else if ("1".equals(repstatus) && !send_user_id.equals(YCache.getCacheUser(context).getUser_id())) {
                                        //status  0为展示  1为仅自己可见
                                    } else {
                                        comments_list.add(mapObjectCommentsList2);
                                    }


                                }
                            }
                        }
                    }
                    mapRet.put("comments_list", comments_list);
                    // 商品列表
                    List<HashMap<String, Object>> shop_list = new ArrayList<HashMap<String, Object>>();
                    if (!jo.has("shop_list") || null == jo.getString("shop_list")
                            || "null".equals(jo.getString("shop_list")) || "".equals(jo.getString("shop_list"))) {
                    } else {
                        JSONArray jsonArrayShopList = jo.getJSONArray("shop_list");
                        for (int k = 0; k < jsonArrayShopList.length(); k++) {
                            JSONObject jk = (JSONObject) jsonArrayShopList.opt(k);
                            HashMap<String, Object> mapObjectShopList = new HashMap<String, Object>();
                            if (!jk.has("shop_code") || null == jk.getString("shop_code")
                                    || jk.getString("shop_code").equals("")) {
                                mapObjectShopList.put("shop_code", "");
                            } else {
                                mapObjectShopList.put("shop_code",
                                        jk.has("shop_code") ? jk.optString("shop_code") : "");
                            }


                            if (!jk.has("app_shop_group_price") || null == jk.getString("app_shop_group_price")
                                    || jk.getString("app_shop_group_price").equals("")) {
                                mapObjectShopList.put("app_shop_group_price", "1.5");
                            } else {
                                mapObjectShopList.put("app_shop_group_price",
                                        jk.has("app_shop_group_price") ? jk.optString("app_shop_group_price") : "1.5");
                            }


                            if (!jk.has("shop_name") || null == jk.getString("shop_name")
                                    || jk.getString("shop_name").equals("")) {
                                mapObjectShopList.put("shop_name", "");
                            } else {
                                mapObjectShopList.put("shop_name",
                                        jk.has("shop_name") ? jk.optString("shop_name") : "");
                            }
                            if (!jk.has("shop_status") || null == jk.getString("shop_status")
                                    || jk.getString("shop_status").equals("")) {
                                mapObjectShopList.put("shop_status", "");
                            } else {
                                mapObjectShopList.put("shop_status",
                                        jk.has("shop_status") ? jk.optString("shop_status") : "");
                            }
                            if (!jk.has("shop_code") || null == jk.getString("shop_code")
                                    || jk.getString("shop_code").equals("")) {
                                mapObjectShopList.put("shop_code", "");
                            } else {
                                mapObjectShopList.put("shop_code",
                                        jk.has("shop_code") ? jk.optString("shop_code") : "");
                            }
                            if (!jk.has("def_pic") || null == jk.getString("def_pic")
                                    || jk.getString("def_pic").equals("")) {
                                mapObjectShopList.put("def_pic", "");
                            } else {
                                mapObjectShopList.put("def_pic", jk.has("def_pic") ? jk.optString("def_pic") : "");
                            }
                            if (!jk.has("shop_se_price") || null == jk.getString("shop_se_price")
                                    || "null".equals(jk.getString("shop_se_price"))
                                    || jk.getString("shop_se_price").equals("")) {
                                mapObjectShopList.put("shop_se_price", "0");
                            } else {
                                mapObjectShopList.put("shop_se_price",
                                        jk.has("shop_se_price") ? jk.optString("shop_se_price") : "0");
                            }
                            if (!jk.has("shop_price") || null == jk.getString("shop_price")
                                    || "null".equals(jk.getString("shop_price"))
                                    || jk.getString("shop_price").equals("")) {
                                mapObjectShopList.put("shop_price", "0");
                            } else {
                                mapObjectShopList.put("shop_price",
                                        jk.has("shop_price") ? jk.optString("shop_price") : "0");
                            }
                            if (!jk.has("supp_label") || null == jk.getString("supp_label")
                                    || jk.getString("supp_label").equals("")) {
                                mapObjectShopList.put("supp_label", "");
                            } else {
                                mapObjectShopList.put("supp_label",
                                        jk.has("supp_label") ? jk.optString("supp_label") : "");
                            }
                            shop_list.add(mapObjectShopList);
                        }

                    }
                    mapRet.put("shop_list", shop_list);
                    // retInfoTemp.add(mapRet);
                    List<HashMap<String, Object>> intimateList = new ArrayList<HashMap<String, Object>>();
                    if ("1".equals(type)) {
                        intimateList = intimateListSqa;
                    } else if ("2".equals(type)) {
                        intimateList = intimateListFri;
                    }
                    boolean isHaving = false;
                    for (int j = 0; j < intimateList.size(); j++) {
                        String theme_id_suq = (String) intimateList.get(j).get("theme_id");
                        if (theme_id.equals(theme_id_suq)) {
                            isHaving = true;
                            break;
                        }
                    }
                    if ("1".equals(type)) {
                        if (!isHaving) {
                            intimateListSqa.add(mapRet);
                        }
                    }
                    if ("2".equals(type)) {
                        if (!isHaving) {
                            intimateListFri.add(mapRet);
                        }
                    }
                    if (d == 0) {
                        if (!isHaving) {// 添加不重复的数据
                            retInfo.add(mapRet);
                        }
                    } else if (d == 1) {
                        if (!isHaving) {
                            retInfo2.add(mapRet);
                        }

                    }
                }
            }
        }

        // data数据中 每间隔五条数据 插入一条data2(精选推荐)数据
        for (int i = 0; i < retInfo.size(); ) {
            i += 5;
            if (retInfo2.size() == 0 || i >= retInfo.size()) {
                break;
            }
            retInfo.add(i, retInfo2.get(0));
            retInfo2.remove(0);
            i += 1;
        }
        retInfo.addAll(retInfo2);
        // SharedPreferencesUtil.saveStringData(context,
        // Pref.INTIMATE_LIST_SIZE, retInfoTemp.size()+"");
        return retInfo;
    }

    /**
     * 帖子收藏列表
     *
     * @param context
     * @param result
     */
    public static final List<HashMap<String, Object>> createIntimateCollectList(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();

        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        if (!"1".equals(jsonObject.getString("status"))) {
            return null;
        }
        JSONArray ja = null;

        if (!jsonObject.has("collect_list") || null == jsonObject.getString("collect_list")
                || "null".equals(jsonObject.getString("collect_list"))
                || "".equals(jsonObject.getString("collect_list"))) {
        } else {
            ja = jsonObject.getJSONArray("collect_list");
        }

        if (null != ja && !"".equals(ja)) {
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                HashMap<String, Object> mapRet = new HashMap<String, Object>();
                String theme_id = "";
                if (!jo.has("theme_id") || null == jo.getString("theme_id") || "null".equals(jo.getString("theme_id"))
                        || "".equals(jo.getString("theme_id"))) {
                    mapRet.put("theme_id", "");
                } else {
                    theme_id = jo.has("theme_id") ? jo.getString("theme_id") : "";
                    mapRet.put("theme_id", theme_id);
                }
                if (!jo.has("user_id") || null == jo.getString("user_id") || "null".equals(jo.getString("user_id"))
                        || "".equals(jo.getString("user_id"))) {
                    mapRet.put("user_id", "");
                } else {
                    mapRet.put("user_id", jo.has("user_id") ? jo.getString("user_id") : "");
                }
                if (!jo.has("head_pic") || null == jo.getString("head_pic") || "null".equals(jo.getString("head_pic"))
                        || "".equals(jo.getString("head_pic"))) {
                    mapRet.put("head_pic", "");
                } else {
                    mapRet.put("head_pic", jo.has("head_pic") ? jo.getString("head_pic") : "");
                }
                if (!jo.has("title") || null == jo.getString("title") || "null".equals(jo.getString("title"))
                        || "".equals(jo.getString("title"))) {
                    mapRet.put("title", "");
                } else {
                    mapRet.put("title", jo.has("title") ? jo.getString("title") : "");
                }
                if (!jo.has("nickname") || null == jo.getString("nickname")) {
                    mapRet.put("nickname", "");
                } else {
                    mapRet.put("nickname", jo.has("nickname") ? jo.getString("nickname") : "");
                }
                if (!jo.has("date") || null == jo.getString("date") || "null".equals(jo.getString("date"))
                        || "".equals(jo.getString("date"))) {
                    mapRet.put("date", "");
                } else {
                    mapRet.put("date", jo.has("date") ? jo.getString("date") : "");
                }
                if (!jo.has("send_time") || null == jo.getString("send_time")
                        || "null".equals(jo.getString("send_time")) || "".equals(jo.getString("send_time"))) {
                    mapRet.put("send_time", "0");
                } else {
                    mapRet.put("send_time", jo.has("send_time") ? jo.getString("send_time") : "0");
                }
                if (!jo.has("content") || null == jo.getString("content")) {
                    mapRet.put("content", "");
                } else {
                    mapRet.put("content", jo.has("content") ? jo.getString("content") : "");
                }
                if (!jo.has("pics") || null == jo.getString("pics") || "null".equals(jo.getString("pics"))
                        || "".equals(jo.getString("pics"))) {
                    mapRet.put("pics", "");
                } else {
                    mapRet.put("pics", jo.has("pics") ? jo.getString("pics") : "");
                }

                List<String> tagList = new ArrayList<String>();
                if (!jo.has("tags") || null == jo.getString("tags") || "null".equals(jo.getString("tags"))
                        || "".equals(jo.getString("tags"))) {
                    mapRet.put("tags", tagList);
                } else {
                    JSONArray jaSonList = jo.getJSONArray("tags");
                    for (int k = 0; k < jaSonList.length(); k++) {
                        String object = jaSonList.getString(k);
                        tagList.add(object);
                    }
                    mapRet.put("tags", tagList);
                }
                if (!jo.has("location") || null == jo.getString("location") || "null".equals(jo.getString("location"))
                        || "".equals(jo.getString("location"))) {
                    mapRet.put("location", "");
                } else {
                    mapRet.put("location", jo.has("location") ? jo.getString("location") : "");
                }
                if (!jo.has("theme_type") || null == jo.getString("theme_type")
                        || "null".equals(jo.getString("theme_type")) || "".equals(jo.getString("theme_type"))) {
                    mapRet.put("theme_type", "");
                } else {
                    mapRet.put("theme_type", jo.has("theme_type") ? jo.getString("theme_type") : "");
                }
                if (!jo.has("applaud_num") || null == jo.getString("applaud_num")
                        || "null".equals(jo.getString("applaud_num")) || "".equals(jo.getString("applaud_num"))) {
                    mapRet.put("applaud_num", "0");
                } else {
                    mapRet.put("applaud_num", jo.has("applaud_num") ? jo.getString("applaud_num") : "0");
                }
                if (!jo.has("comment_count") || null == jo.getString("comment_count")
                        || "null".equals(jo.getString("comment_count")) || "".equals(jo.getString("comment_count"))) {
                    mapRet.put("comment_count", "0");
                } else {
                    mapRet.put("comment_count", jo.has("comment_count") ? jo.getString("comment_count") : "0");
                }
                // 关注状态0未关注1，已经关注
                if (!jo.has("attention_status") || null == jo.getString("attention_status")
                        || "null".equals(jo.getString("attention_status"))
                        || "".equals(jo.getString("attention_status"))) {
                    mapRet.put("attention_status", "");
                } else {
                    mapRet.put("attention_status", jo.has("attention_status") ? jo.getString("attention_status") : "");
                }
                // 点赞状态（ 0 未点赞1 已经点赞）
                if (!jo.has("applaud_status") || null == jo.getString("applaud_status")
                        || "null".equals(jo.getString("applaud_status")) || "".equals(jo.getString("applaud_status"))) {
                    mapRet.put("applaud_status", "0");
                } else {
                    mapRet.put("applaud_status", jo.has("applaud_status") ? jo.getString("applaud_status") : "0");
                }
                // // 穿搭 风格标签
                // if (!jo.has("style") || null == jo.getString("style") ||
                // "null".equals(jo.getString("style"))
                // || "".equals(jo.getString("style"))) {
                // mapRet.put("style", "");
                // } else {
                // mapRet.put("style", jo.has("style") ? jo.getString("style") :
                // "");
                // }
                // // 穿搭 品牌标签
                // if (!jo.has("supp_label_id") || null ==
                // jo.getString("supp_label_id")
                // || "null".equals(jo.getString("supp_label_id"))
                // || "".equals(jo.getString("supp_label_id"))) {
                // mapRet.put("supp_label_id", "");
                // } else {
                // mapRet.put("supp_label_id", jo.has("supp_label_id") ?
                // jo.getString("supp_label_id") : "");
                // }
                // 收藏状态状态（0：为未收藏，1：为收藏）
                if (!jo.has("collection_status") || null == jo.getString("collection_status")
                        || "null".equals(jo.getString("collection_status"))
                        || "".equals(jo.getString("collection_status"))) {
                    mapRet.put("collection_status", "0");
                } else {
                    mapRet.put("collection_status",
                            jo.has("collection_status") ? jo.getString("collection_status") : "0");
                }
                // 收藏数量
                if (!jo.has("collect_num") || null == jo.getString("collect_num")
                        || "null".equals(jo.getString("collect_num")) || "".equals(jo.getString("collect_num"))) {
                    mapRet.put("collect_num", "0");
                } else {
                    mapRet.put("collect_num", jo.has("collect_num") ? jo.getString("collect_num") : "0");
                }
                // 0：为普通用户 ，1：为红V，2：为蓝V
                if (!jo.has("v_ident") || null == jo.getString("v_ident") || "null".equals(jo.getString("v_ident"))
                        || "".equals(jo.getString("v_ident"))) {
                    mapRet.put("v_ident", "0");
                } else {
                    mapRet.put("v_ident", jo.has("v_ident") ? jo.getString("v_ident") : "0");
                }

                // 品牌和风格
                List<HashMap<String, Object>> supp_label_list = new ArrayList<HashMap<String, Object>>();
                if (!jo.has("supp_label_list") || null == jo.getString("supp_label_list")
                        || "null".equals(jo.getString("supp_label_list"))
                        || "".equals(jo.getString("supp_label_list"))) {
                } else {
                    JSONArray jsonArraySuppLabelList = jo.getJSONArray("supp_label_list");
                    for (int k = 0; k < jsonArraySuppLabelList.length(); k++) {
                        JSONObject jk = (JSONObject) jsonArraySuppLabelList.opt(k);
                        HashMap<String, Object> mapObjectSuppLabelList = new HashMap<String, Object>();
                        if (!jk.has("label_id") || null == jk.getString("label_id")
                                || "null".equals(jk.getString("label_id")) || jk.getString("label_id").equals("")) {
                            mapObjectSuppLabelList.put("label_id", "");
                        } else {
                            mapObjectSuppLabelList.put("label_id", jk.has("label_id") ? jk.optString("label_id") : "");
                        }
                        if (!jk.has("only_id") || null == jk.getString("only_id")
                                || "null".equals(jk.getString("only_id")) || jk.getString("only_id").equals("")) {
                            mapObjectSuppLabelList.put("only_id", "");
                        } else {
                            mapObjectSuppLabelList.put("only_id", jk.has("only_id") ? jk.optString("only_id") : "");
                        }
                        if (!jk.has("type1") || null == jk.getString("type1") || "null".equals(jk.getString("type1"))
                                || jk.getString("type1").equals("")) {
                            mapObjectSuppLabelList.put("type1", "");
                        } else {
                            mapObjectSuppLabelList.put("type1", jk.has("type1") ? jk.optString("type1") : "");
                        }
                        if (!jk.has("type2") || null == jk.getString("type2") || "null".equals(jk.getString("type2"))
                                || jk.getString("type2").equals("")) {
                            mapObjectSuppLabelList.put("type2", "");
                        } else {
                            mapObjectSuppLabelList.put("type2", jk.has("type2") ? jk.optString("type2") : "");
                        }
                        if (!jk.has("style") || null == jk.getString("style") || "null".equals(jk.getString("style"))
                                || jk.getString("style").equals("")) {
                            mapObjectSuppLabelList.put("style", "");
                        } else {
                            mapObjectSuppLabelList.put("style", jk.has("style") ? jk.optString("style") : "");
                        }
                        // 用来区分后台标签和用户自定义标签 1：后台标签 2：用户自定义的标签
                        if (!jk.has("label_type") || null == jk.getString("label_type")
                                || "null".equals(jk.getString("label_type")) || jk.getString("label_type").equals("")) {
                            mapObjectSuppLabelList.put("label_type", "");
                        } else {
                            mapObjectSuppLabelList.put("label_type",
                                    jk.has("label_type") ? jk.optString("label_type") : "");
                        }
                        if (!jk.has("label_x") || null == jk.getString("label_x")
                                || "null".equals(jk.getString("label_x")) || jk.getString("label_x").equals("")) {
                            mapObjectSuppLabelList.put("label_x", "");
                        } else {
                            mapObjectSuppLabelList.put("label_x", jk.has("label_x") ? jk.optString("label_x") : "");
                        }
                        if (!jk.has("label_y") || null == jk.getString("label_y")
                                || "null".equals(jk.getString("label_y")) || jk.getString("label_y").equals("")) {
                            mapObjectSuppLabelList.put("label_y", "");
                        } else {
                            mapObjectSuppLabelList.put("label_y", jk.has("label_y") ? jk.optString("label_y") : "");
                        }
                        supp_label_list.add(mapObjectSuppLabelList);
                    }
                }
                mapRet.put("supp_label_list", supp_label_list);
                // 评论列表
                List<HashMap<String, String>> comments_list = new ArrayList<HashMap<String, String>>();
                if (!jo.has("comments_list") || null == jo.getString("comments_list")
                        || "null".equals(jo.getString("comments_list")) || "".equals(jo.getString("comments_list"))) {
                } else {
                    JSONArray jsonArrayCommentsList = jo.getJSONArray("comments_list");
                    for (int k = 0; k < jsonArrayCommentsList.length(); k++) {
                        JSONObject jk = (JSONObject) jsonArrayCommentsList.opt(k);
                        HashMap<String, String> mapObjectCommentsList = new HashMap<String, String>();
                        String base_user_id = "";// 帖子的用户ID
                        if (!jk.has("base_user_id") || null == jk.getString("base_user_id")
                                || "null".equals(jk.getString("base_user_id"))
                                || jk.getString("base_user_id").equals("")) {
                            mapObjectCommentsList.put("base_user_id", "");
                        } else {
                            base_user_id = jk.has("base_user_id") ? jk.optString("base_user_id") : "";
                            mapObjectCommentsList.put("base_user_id",
                                    jk.has("base_user_id") ? jk.optString("base_user_id") : "");
                        }
                        String user_id = "";// 评论帖子的用户ID
                        if (!jk.has("user_id") || null == jk.getString("user_id")
                                || "null".equals(jk.getString("user_id")) || jk.getString("user_id").equals("")) {
                            mapObjectCommentsList.put("user_id", "");
                        } else {
                            user_id = jk.has("user_id") ? jk.optString("user_id") : "";
                            mapObjectCommentsList.put("user_id", jk.has("user_id") ? jk.optString("user_id") : "");
                        }
                        if (user_id.equals(base_user_id)) {
                            mapObjectCommentsList.put("nickname", "楼主");
                        } else {
                            if (!jk.has("nickname") || null == jk.getString("nickname")
                                    || jk.getString("nickname").equals("")) {
                                mapObjectCommentsList.put("nickname", "");
                            } else {
                                mapObjectCommentsList.put("nickname",
                                        jk.has("nickname") ? jk.optString("nickname") : "");
                            }
                        }
                        if (!jk.has("content") || null == jk.getString("content")
                                || jk.getString("content").equals("")) {
                            mapObjectCommentsList.put("content", "");
                        } else {
                            mapObjectCommentsList.put("content", jk.has("content") ? jk.optString("content") : "");
                        }
                        mapObjectCommentsList.put("comments_type", "1");// 1标识是评论
                        // 2 回复
                        comments_list.add(mapObjectCommentsList);
                        // 评论里面的回复
                        if (!jk.has("replies_list") || null == jk.getString("replies_list")
                                || "null".equals(jk.getString("replies_list"))
                                || "".equals(jk.getString("replies_list"))) {
                        } else {
                            JSONArray jsonArrayCommentsList2 = jk.getJSONArray("replies_list");
                            for (int kk = 0; kk < jsonArrayCommentsList2.length(); kk++) {
                                JSONObject jkk = (JSONObject) jsonArrayCommentsList2.opt(kk);
                                HashMap<String, String> mapObjectCommentsList2 = new HashMap<String, String>();
                                String send_user_id = "";// 回复的用户ID
                                if (!jkk.has("send_user_id") || null == jkk.getString("send_user_id")
                                        || "null".equals(jkk.getString("send_user_id"))
                                        || jkk.getString("send_user_id").equals("")) {
                                    mapObjectCommentsList2.put("send_user_id", "");
                                } else {
                                    send_user_id = jkk.has("send_user_id") ? jkk.optString("send_user_id") : "";
                                    mapObjectCommentsList2.put("send_user_id",
                                            jkk.has("send_user_id") ? jkk.optString("send_user_id") : "");
                                }
                                String receive_user_id = "";// 被回复的用户ID
                                if (!jkk.has("receive_user_id") || null == jkk.getString("receive_user_id")
                                        || "null".equals(jkk.getString("receive_user_id"))
                                        || jkk.getString("receive_user_id").equals("")) {
                                    mapObjectCommentsList2.put("receive_user_id", "");
                                } else {
                                    receive_user_id = jkk.has("receive_user_id") ? jkk.optString("receive_user_id")
                                            : "";
                                    mapObjectCommentsList2.put("receive_user_id",
                                            jkk.has("receive_user_id") ? jkk.optString("receive_user_id") : "");
                                }
                                if (send_user_id.equals(base_user_id)) {
                                    mapObjectCommentsList2.put("send_nickname", "楼主");
                                } else {
                                    if (!jkk.has("send_nickname") || null == jkk.getString("send_nickname")
                                            || jkk.getString("send_nickname").equals("")) {
                                        mapObjectCommentsList2.put("send_nickname", "");
                                    } else {
                                        mapObjectCommentsList2.put("send_nickname",
                                                jkk.has("send_nickname") ? jkk.optString("send_nickname") : "");
                                    }
                                }
                                if (receive_user_id.equals(base_user_id)) {
                                    mapObjectCommentsList2.put("receive_nickname", "楼主");
                                } else {
                                    if (!jkk.has("receive_nickname") || null == jkk.getString("receive_nickname")
                                            || jkk.getString("receive_nickname").equals("")) {
                                        mapObjectCommentsList2.put("receive_nickname", "");
                                    } else {
                                        mapObjectCommentsList2.put("receive_nickname",
                                                jkk.has("receive_nickname") ? jkk.optString("receive_nickname") : "");
                                    }
                                }
                                if (!jkk.has("content") || null == jkk.getString("content")
                                        || jkk.getString("content").equals("")) {
                                    mapObjectCommentsList2.put("content", "");
                                } else {
                                    mapObjectCommentsList2.put("content",
                                            jkk.has("content") ? jkk.optString("content") : "");
                                }
                                mapObjectCommentsList2.put("comments_type", "2");// 1标识是评论
                                // 2
                                // 回复
                                comments_list.add(mapObjectCommentsList2);
                            }
                        }
                    }
                }
                mapRet.put("comments_list", comments_list);

                List<HashMap<String, Object>> shop_list = new ArrayList<HashMap<String, Object>>();
                if (!jo.has("shop_list") || null == jo.getString("shop_list")
                        || "null".equals(jo.getString("shop_list")) || "".equals(jo.getString("shop_list"))) {
                } else {
                    JSONArray jsonArrayShopList = jo.getJSONArray("shop_list");
                    for (int k = 0; k < jsonArrayShopList.length(); k++) {
                        JSONObject jk = (JSONObject) jsonArrayShopList.opt(k);
                        HashMap<String, Object> mapObjectShopList = new HashMap<String, Object>();
                        if (!jk.has("shop_code") || null == jk.getString("shop_code")
                                || jk.getString("shop_code").equals("")) {
                            mapObjectShopList.put("shop_code", "");
                        } else {
                            mapObjectShopList.put("shop_code", jk.has("shop_code") ? jk.optString("shop_code") : "");
                        }
                        if (!jk.has("shop_name") || null == jk.getString("shop_name")
                                || jk.getString("shop_name").equals("")) {
                            mapObjectShopList.put("shop_name", "");
                        } else {
                            mapObjectShopList.put("shop_name", jk.has("shop_name") ? jk.optString("shop_name") : "");
                        }
                        if (!jk.has("shop_status") || null == jk.getString("shop_status")
                                || jk.getString("shop_status").equals("")) {
                            mapObjectShopList.put("shop_status", "");
                        } else {
                            mapObjectShopList.put("shop_status",
                                    jk.has("shop_status") ? jk.optString("shop_status") : "");
                        }
                        if (!jk.has("shop_code") || null == jk.getString("shop_code")
                                || jk.getString("shop_code").equals("")) {
                            mapObjectShopList.put("shop_code", "");
                        } else {
                            mapObjectShopList.put("shop_code", jk.has("shop_code") ? jk.optString("shop_code") : "");
                        }
                        if (!jk.has("def_pic") || null == jk.getString("def_pic")
                                || jk.getString("def_pic").equals("")) {
                            mapObjectShopList.put("def_pic", "");
                        } else {
                            mapObjectShopList.put("def_pic", jk.has("def_pic") ? jk.optString("def_pic") : "");
                        }
                        if (!jk.has("shop_se_price") || null == jk.getString("shop_se_price")
                                || "null".equals(jk.getString("shop_se_price"))
                                || jk.getString("shop_se_price").equals("")) {
                            mapObjectShopList.put("shop_se_price", "0");
                        } else {
                            mapObjectShopList.put("shop_se_price",
                                    jk.has("shop_se_price") ? jk.optString("shop_se_price") : "0");
                        }
                        if (!jk.has("shop_price") || null == jk.getString("shop_price")
                                || "null".equals(jk.getString("shop_price")) || jk.getString("shop_price").equals("")) {
                            mapObjectShopList.put("shop_price", "0");
                        } else {
                            mapObjectShopList.put("shop_price",
                                    jk.has("shop_price") ? jk.optString("shop_price") : "0");
                        }
                        if (!jk.has("supp_label") || null == jk.getString("supp_label")
                                || jk.getString("supp_label").equals("")) {
                            mapObjectShopList.put("supp_label", "");
                        } else {
                            mapObjectShopList.put("supp_label", jk.has("supp_label") ? jk.optString("supp_label") : "");
                        }
                        shop_list.add(mapObjectShopList);
                    }

                }
                mapRet.put("shop_list", shop_list);
                retInfo.add(mapRet);
            }
        }
        return retInfo;
    }

    public static final ReturnInfo createSweetRetInfo(Context context, String result) throws JSONException {

        ReturnInfo retInfo = new ReturnInfo();
        JSONObject j = new JSONObject(result);
        retInfo.setStatus(j.has("status") ? j.getString(RetInfo.status) : "");
        retInfo.setMessage(j.has("message") ? j.getString(RetInfo.message) : "");
        // retInfo.setPwdflag(j.has("pwdflag") ? j.getInt(RetInfo.pwdflag) : 5);
        return retInfo;
    }

    /***
     * 发帖
     */
    public static final HashMap<String, String> createFatie(Context context, String result) throws Exception {
        HashMap<String, String> retInfo = new HashMap<String, String>();
        LogYiFu.e("Kr", result + "");
        JSONObject j = new JSONObject(result);
        if (j.getString("status").equals(1 + "")) {
            retInfo.put("status", "1");
            if (!j.has("message") || null == j.getString("message") || j.getString("message").equals("")
                    || j.getString("message").equals("null")) {
                retInfo.put("message", "未知错误");
            } else {
                retInfo.put("message", j.optString("message"));
            }
            if (!j.has("theme_id") || null == j.getString("theme_id") || j.getString("theme_id").equals("")
                    || j.getString("theme_id").equals("null")) {
                retInfo.put("theme_id", "-100");
            } else {
                retInfo.put("theme_id", j.optString("theme_id"));
            }
        }
        return retInfo;
    }

    /**
     * 精选推荐获取分享资格 send_allow //发送资格 0、无发送资格，1、有发送资格
     *
     * @param context
     */
    public static final String getShareQua(Context context, String result) throws JSONException {

        String retInfo = "0";
        JSONObject j = new JSONObject(result);
        if (j.getString("status").equals(1 + "")) {
            if (!j.has("send_allow") || null == j.getString("send_allow") || j.getString("send_allow").equals("")
                    || j.getString("send_allow").equals("null")) {
                retInfo = "0";
            } else {
                retInfo = j.getString("send_allow");
            }
        }
        return retInfo;
    }

    /***
     * 获取密友圈首页轮播图
     */
    public static final List<HashMap<String, String>> createIntimateBanner(Context context, String result)
            throws Exception {
        List<HashMap<String, String>> retList = new ArrayList<HashMap<String, String>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (!"1".equals(j.getString("status"))) {
            return null;
        }
        JSONArray ja = j.getJSONArray("banner_list");
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.getJSONObject(i);
            HashMap<String, String> map = new HashMap<String, String>();
            if (!jo.has("type") || null == jo.getString("type") || "null".equals(jo.getString("type"))
                    || jo.getString("type").equals("")) {
                map.put("type", "");
            } else {
                map.put("type", jo.has("type") ? jo.optString("type") : "");
            }
            if (!jo.has("pic") || null == jo.getString("pic") || "null".equals(jo.getString("pic"))
                    || jo.getString("pic").equals("")) {
                map.put("pic", "");
            } else {
                map.put("pic", jo.has("pic") ? jo.optString("pic") : "");
            }
            if (!jo.has("link") || null == jo.getString("link") || "null".equals(jo.getString("link"))
                    || jo.getString("link").equals("")) {
                map.put("link", "");
            } else {
                map.put("link", jo.has("link") ? jo.optString("link") : "");
            }
            retList.add(map);
        }

        return retList;
    }

    /***
     * 获取密友圈详情热门评论
     */
    public static final HashMap<String, List<HashMap<String, Object>>> createSweetHotCommentList(Context context,
                                                                                                 String result) throws Exception {
        HashMap<String, List<HashMap<String, Object>>> map = new HashMap<String, List<HashMap<String, Object>>>();
        List<HashMap<String, Object>> listHotComments = new ArrayList<HashMap<String, Object>>();// 热门评论
        List<HashMap<String, Object>> listRecommend = new ArrayList<HashMap<String, Object>>();// 相关推荐
        List<HashMap<String, Object>> listDetails = new ArrayList<HashMap<String, Object>>();// 详情主数据
        JSONObject json = new JSONObject(result);
        if (null == json || "".equals(json)) {
            return null;
        }
        if (!"1".equals(json.getString("status"))) {
            return null;
        }
        String str = json.getString("data");
        JSONObject j = new JSONObject(str);
        if (!j.has("post_details") || null == j.getString("post_details") || "null".equals(j.getString("post_details"))
                || j.getString("post_details").equals("")) {
            map.put("post_details", listDetails);
        } else {
            String strDetails = j.optString("post_details");
            JSONObject jo = new JSONObject(strDetails);
            HashMap<String, Object> mapRet = new HashMap<String, Object>();
            if (!jo.has("theme_id") || null == jo.getString("theme_id") || "null".equals(jo.getString("theme_id"))
                    || "".equals(jo.getString("theme_id"))) {
                mapRet.put("theme_id", "");
            } else {
                mapRet.put("theme_id", jo.has("theme_id") ? jo.getString("theme_id") : "");
            }
            if (!jo.has("user_id") || null == jo.getString("user_id") || "null".equals(jo.getString("user_id"))
                    || "".equals(jo.getString("user_id"))) {
                mapRet.put("user_id", "");
            } else {
                mapRet.put("user_id", jo.has("user_id") ? jo.getString("user_id") : "");
            }
            if (!jo.has("head_pic") || null == jo.getString("head_pic") || "null".equals(jo.getString("head_pic"))
                    || "".equals(jo.getString("head_pic"))) {
                mapRet.put("head_pic", "");
            } else {
                mapRet.put("head_pic", jo.has("head_pic") ? jo.getString("head_pic") : "");
            }
            if (!jo.has("v_ident") || null == jo.getString("v_ident") || "null".equals(jo.getString("v_ident"))
                    || "".equals(jo.getString("v_ident"))) {
                mapRet.put("v_ident", "0");
            } else {
                mapRet.put("v_ident", jo.has("v_ident") ? jo.getString("v_ident") : "0");
            }
            if (!jo.has("title") || null == jo.getString("title") || "".equals(jo.getString("title"))) {
                mapRet.put("title", "");
            } else {
                mapRet.put("title", jo.has("title") ? jo.getString("title") : "");
            }
            if (!jo.has("nickname") || null == jo.getString("nickname")) {
                mapRet.put("nickname", "");
            } else {
                mapRet.put("nickname", jo.has("nickname") ? jo.getString("nickname") : "");
            }
            if (!jo.has("date") || null == jo.getString("date") || "null".equals(jo.getString("date"))
                    || "".equals(jo.getString("date"))) {
                mapRet.put("date", "");
            } else {
                mapRet.put("date", jo.has("date") ? jo.getString("date") : "");
            }
            if (!jo.has("send_time") || null == jo.getString("send_time") || "null".equals(jo.getString("send_time"))
                    || "".equals(jo.getString("send_time"))) {
                mapRet.put("send_time", "0");
            } else {
                mapRet.put("send_time", jo.has("send_time") ? jo.getString("send_time") : "0");
            }
            if (!jo.has("content") || null == jo.getString("content")) {
                mapRet.put("content", "");
            } else {
                mapRet.put("content", jo.has("content") ? jo.getString("content") : "");
            }
            if (!jo.has("pics") || null == jo.getString("pics") || "null".equals(jo.getString("pics"))
                    || "".equals(jo.getString("pics"))) {
                mapRet.put("pics", "");
            } else {
                mapRet.put("pics", jo.has("pics") ? jo.getString("pics") : "");
            }
            // if (!jo.has("style") || null == jo.getString("style") ||
            // "null".equals(jo.getString("style"))
            // || "".equals(jo.getString("style"))) {
            // mapRet.put("style", "");
            // } else {
            // mapRet.put("style", jo.has("style") ? jo.getString("style") :
            // "");
            // }
            // if (!jo.has("supp_label_id") || null ==
            // jo.getString("supp_label_id")
            // || "null".equals(jo.getString("supp_label_id")) ||
            // "".equals(jo.getString("supp_label_id"))) {
            // mapRet.put("supp_label_id", "");
            // } else {
            // mapRet.put("supp_label_id", jo.has("supp_label_id") ?
            // jo.getString("supp_label_id") : "");
            // }
            // 品牌和风格
            List<HashMap<String, Object>> supp_label_list = new ArrayList<HashMap<String, Object>>();
            if (!jo.has("supp_label_list") || null == jo.getString("supp_label_list")
                    || "null".equals(jo.getString("supp_label_list")) || "".equals(jo.getString("supp_label_list"))) {
            } else {
                JSONArray jsonArraySuppLabelList = jo.getJSONArray("supp_label_list");
                for (int k = 0; k < jsonArraySuppLabelList.length(); k++) {
                    JSONObject jk = (JSONObject) jsonArraySuppLabelList.opt(k);
                    HashMap<String, Object> mapObjectSuppLabelList = new HashMap<String, Object>();
                    if (!jk.has("label_id") || null == jk.getString("label_id")
                            || "null".equals(jk.getString("label_id")) || jk.getString("label_id").equals("")) {
                        mapObjectSuppLabelList.put("label_id", "");
                    } else {
                        mapObjectSuppLabelList.put("label_id", jk.has("label_id") ? jk.optString("label_id") : "");
                    }
                    if (!jk.has("only_id") || null == jk.getString("only_id") || "null".equals(jk.getString("only_id"))
                            || jk.getString("only_id").equals("")) {
                        mapObjectSuppLabelList.put("only_id", "");
                    } else {
                        mapObjectSuppLabelList.put("only_id", jk.has("only_id") ? jk.optString("only_id") : "");
                    }
                    // 一级类目
                    if (!jk.has("type1") || null == jk.getString("type1") || "null".equals(jk.getString("type1"))
                            || jk.getString("type1").equals("")) {
                        mapObjectSuppLabelList.put("type1", "");
                    } else {
                        mapObjectSuppLabelList.put("type1", jk.has("type1") ? jk.optString("type1") : "");
                    }
                    // 二级类目
                    if (!jk.has("type2") || null == jk.getString("type2") || "null".equals(jk.getString("type2"))
                            || jk.getString("type2").equals("")) {
                        mapObjectSuppLabelList.put("type2", "");
                    } else {
                        mapObjectSuppLabelList.put("type2", jk.has("type2") ? jk.optString("type2") : "");
                    }

                    // 风格
                    if (!jk.has("style") || null == jk.getString("style") || "null".equals(jk.getString("style"))
                            || jk.getString("style").equals("")) {
                        mapObjectSuppLabelList.put("style", "");
                    } else {
                        mapObjectSuppLabelList.put("style", jk.has("style") ? jk.optString("style") : "");
                    }
                    // 用来区分后台标签和用户自定义标签 1：后台标签 2：用户自定义的标签
                    if (!jk.has("label_type") || null == jk.getString("label_type")
                            || "null".equals(jk.getString("label_type")) || jk.getString("label_type").equals("")) {
                        mapObjectSuppLabelList.put("label_type", "");
                    } else {
                        mapObjectSuppLabelList.put("label_type",
                                jk.has("label_type") ? jk.optString("label_type") : "");
                    }
                    if (!jk.has("label_x") || null == jk.getString("label_x") || "null".equals(jk.getString("label_x"))
                            || jk.getString("label_x").equals("")) {
                        mapObjectSuppLabelList.put("label_x", "0");
                    } else {
                        mapObjectSuppLabelList.put("label_x", jk.has("label_x") ? jk.optString("label_x") : "0");
                    }
                    if (!jk.has("label_y") || null == jk.getString("label_y") || "null".equals(jk.getString("label_y"))
                            || jk.getString("label_y").equals("")) {
                        mapObjectSuppLabelList.put("label_y", "0");
                    } else {
                        mapObjectSuppLabelList.put("label_y", jk.has("label_y") ? jk.optString("label_y") : "0");
                    }
                    if (!jk.has("direction") || null == jk.getString("direction")
                            || "null".equals(jk.getString("direction")) || jk.getString("direction").equals("")) {
                        mapObjectSuppLabelList.put("direction", "0");
                    } else {
                        mapObjectSuppLabelList.put("direction", jk.has("direction") ? jk.optString("direction") : "0");
                    }
                    if (!jk.has("label_name") || null == jk.getString("label_name")
                            || "null".equals(jk.getString("label_name")) || jk.getString("label_name").equals("")) {
                        mapObjectSuppLabelList.put("label_name", "");
                    } else {
                        mapObjectSuppLabelList.put("label_name",
                                jk.has("label_name") ? jk.optString("label_name") : "");
                    }
                    if (!jk.has("shop_code") || null == jk.getString("shop_code")
                            || "null".equals(jk.getString("shop_code")) || jk.getString("shop_code").equals("")) {
                        mapObjectSuppLabelList.put("shop_code", "");
                    } else {
                        mapObjectSuppLabelList.put("shop_code", jk.has("shop_code") ? jk.optString("shop_code") : "");
                    }
                    supp_label_list.add(mapObjectSuppLabelList);
                }
            }
            mapRet.put("supp_label_list", supp_label_list);

            List<String> tagList = new ArrayList<String>();
            if (!jo.has("tags") || null == jo.getString("tags") || "null".equals(jo.getString("tags"))
                    || "".equals(jo.getString("tags"))) {
                mapRet.put("tags", tagList);
            } else {
                JSONArray jaSonList = jo.getJSONArray("tags");
                for (int k = 0; k < jaSonList.length(); k++) {
                    String object = jaSonList.getString(k);
                    tagList.add(object);
                }
                mapRet.put("tags", tagList);
            }
            if (!jo.has("location") || null == jo.getString("location") || "null".equals(jo.getString("location"))
                    || "".equals(jo.getString("location"))) {
                mapRet.put("location", "");
            } else {
                mapRet.put("location", jo.has("location") ? jo.getString("location") : "");
            }
            if (!jo.has("theme_type") || null == jo.getString("theme_type") || "null".equals(jo.getString("theme_type"))
                    || "".equals(jo.getString("theme_type"))) {
                mapRet.put("theme_type", "");
            } else {
                mapRet.put("theme_type", jo.has("theme_type") ? jo.getString("theme_type") : "");
            }
            if (!jo.has("applaud_num") || null == jo.getString("applaud_num")
                    || "null".equals(jo.getString("applaud_num")) || "".equals(jo.getString("applaud_num"))) {
                mapRet.put("applaud_num", "0");
            } else {
                mapRet.put("applaud_num", jo.has("applaud_num") ? jo.getString("applaud_num") : "0");
            }
            if (!jo.has("comment_count") || null == jo.getString("comment_count")
                    || "null".equals(jo.getString("comment_count")) || "".equals(jo.getString("comment_count"))) {
                mapRet.put("comment_count", "0");
            } else {
                mapRet.put("comment_count", jo.has("comment_count") ? jo.getString("comment_count") : "0");
            }
            if (!jo.has("attention_status") || null == jo.getString("attention_status")
                    || "null".equals(jo.getString("attention_status")) || "".equals(jo.getString("attention_status"))) {
                mapRet.put("attention_status", "");
            } else {
                mapRet.put("attention_status", jo.has("attention_status") ? jo.getString("attention_status") : "");// 关注状态
                // 0
                // 未关注
                // 1，已经关注
            }
            if (!jo.has("applaud_status") || null == jo.getString("applaud_status")
                    || "null".equals(jo.getString("applaud_status")) || "".equals(jo.getString("applaud_status"))) {
                mapRet.put("applaud_status", "0");
            } else {
                mapRet.put("applaud_status", jo.has("applaud_status") ? jo.getString("applaud_status") : "0");// 点赞状态
                // 0
                // 未点赞
                // 1
                // 已经点赞
            }
            List<HashMap<String, Object>> shop_list = new ArrayList<HashMap<String, Object>>();
            if (!jo.has("shop_list") || null == jo.getString("shop_list") || "null".equals(jo.getString("shop_list"))
                    || "".equals(jo.getString("shop_list"))) {
                mapRet.put("shop_list", shop_list);
            } else {
                JSONArray jsonArrayShopList = jo.getJSONArray("shop_list");
                for (int k = 0; k < jsonArrayShopList.length(); k++) {
                    JSONObject jk = (JSONObject) jsonArrayShopList.opt(k);
                    HashMap<String, Object> mapObjectShopList = new HashMap<String, Object>();
                    if (!jk.has("shop_code") || null == jk.getString("shop_code")
                            || jk.getString("shop_code").equals("")) {
                        mapObjectShopList.put("shop_code", "");
                    } else {
                        mapObjectShopList.put("shop_code", jk.has("shop_code") ? jk.optString("shop_code") : "");
                    }


                    if (!jk.has("app_shop_group_price") || null == jk.getString("app_shop_group_price")
                            || jk.getString("app_shop_group_price").equals("1.5")) {
                        mapObjectShopList.put("app_shop_group_price", "1.5");
                    } else {
                        mapObjectShopList.put("app_shop_group_price", jk.has("app_shop_group_price") ? jk.optString("app_shop_group_price") : "1.5");
                    }


                    if (!jk.has("def_pic") || null == jk.getString("def_pic") || jk.getString("def_pic").equals("")) {
                        mapObjectShopList.put("def_pic", "");
                    } else {
                        mapObjectShopList.put("def_pic", jk.has("def_pic") ? jk.optString("def_pic") : "");
                    }
                    if (!jk.has("shop_se_price") || null == jk.getString("shop_se_price")
                            || "null".equals(jk.getString("shop_se_price"))
                            || jk.getString("shop_se_price").equals("")) {
                        mapObjectShopList.put("shop_se_price", "0");
                    } else {
                        mapObjectShopList.put("shop_se_price",
                                jk.has("shop_se_price") ? jk.optString("shop_se_price") : "0");
                    }
                    if (!jk.has("shop_price") || null == jk.getString("shop_price")
                            || "null".equals(jk.getString("shop_price")) || jk.getString("shop_price").equals("")) {
                        mapObjectShopList.put("shop_price", "0");
                    } else {
                        mapObjectShopList.put("shop_price", jk.has("shop_price") ? jk.optString("shop_price") : "0");
                    }
                    if (!jk.has("shop_name") || null == jk.getString("shop_name")
                            || "null".equals(jk.getString("shop_name")) || jk.getString("shop_name").equals("")) {
                        mapObjectShopList.put("shop_name", "");
                    } else {
                        mapObjectShopList.put("shop_name", jk.has("shop_name") ? jk.optString("shop_name") : "");
                    }
                    if (!jk.has("shop_status") || null == jk.getString("shop_status")
                            || "null".equals(jk.getString("shop_status")) || jk.getString("shop_status").equals("")) {
                        mapObjectShopList.put("shop_status", "");
                    } else {
                        mapObjectShopList.put("shop_status", jk.has("shop_status") ? jk.optString("shop_status") : "");
                    }
                    if (!jk.has("supp_label") || null == jk.getString("supp_label")
                            || jk.getString("supp_label").equals("")) {
                        mapObjectShopList.put("supp_label", "");
                    } else {
                        mapObjectShopList.put("supp_label", jk.has("supp_label") ? jk.optString("supp_label") : "");
                    }
                    if (!jk.has("shop_status") || null == jk.getString("shop_status")
                            || jk.getString("shop_status").equals("")) {
                        mapObjectShopList.put("shop_status", "");
                    } else {
                        mapObjectShopList.put("shop_status", jk.has("shop_status") ? jk.optString("shop_status") : "");
                    }
                    shop_list.add(mapObjectShopList);
                }

            }
            mapRet.put("shop_list", shop_list);

            listDetails.add(mapRet);
            map.put("post_details", listDetails);
        }
        if (!j.has("hot_comments") || null == j.getString("hot_comments") || "null".equals(j.getString("hot_comments"))
                || j.getString("hot_comments").equals("")) {
            map.put("hot_comments", listHotComments);
        } else {
            JSONArray jaHotList = j.getJSONArray("hot_comments");
            for (int i = 0; i < jaHotList.length(); i++) {
                JSONObject jaHot = jaHotList.getJSONObject(i);
                HashMap<String, Object> mapHot = new HashMap<String, Object>();
                if (!jaHot.has("theme_id") || null == jaHot.getString("theme_id")
                        || "null".equals(jaHot.getString("theme_id")) || jaHot.getString("theme_id").equals("")) {
                    mapHot.put("theme_id", "");
                } else {
                    mapHot.put("theme_id", jaHot.optString("theme_id"));// 帖子id
                }
                if (!jaHot.has("comment_id") || null == jaHot.getString("comment_id")
                        || "null".equals(jaHot.getString("comment_id")) || jaHot.getString("comment_id").equals("")) {
                    mapHot.put("comment_id", "");
                } else {
                    mapHot.put("comment_id", jaHot.optString("comment_id"));// 评论id
                }
                if (!jaHot.has("user_id") || null == jaHot.getString("user_id")
                        || "null".equals(jaHot.getString("user_id")) || jaHot.getString("user_id").equals("")) {
                    mapHot.put("user_id", "");
                } else {
                    mapHot.put("user_id", jaHot.optString("user_id"));// 用户id
                }
                if (!jaHot.has("nickname") || null == jaHot.getString("nickname")
                        || jaHot.getString("nickname").equals("")) {
                    mapHot.put("nickname", "");
                } else {
                    mapHot.put("nickname", jaHot.optString("nickname"));// 昵称
                }
                if (!jaHot.has("head_pic") || null == jaHot.getString("head_pic")
                        || "null".equals(jaHot.getString("head_pic")) || jaHot.getString("head_pic").equals("")) {
                    mapHot.put("head_pic", "");
                } else {
                    mapHot.put("head_pic", jaHot.optString("head_pic"));// 头像
                }
                if (!jaHot.has("v_ident") || null == jaHot.getString("v_ident")
                        || "null".equals(jaHot.getString("v_ident")) || jaHot.getString("v_ident").equals("")) {
                    mapHot.put("v_ident", "0");
                } else {
                    mapHot.put("v_ident", jaHot.optString("v_ident"));// 头像
                }
                if (!jaHot.has("location") || null == jaHot.getString("location")
                        || "null".equals(jaHot.getString("location")) || jaHot.getString("location").equals("")) {
                    mapHot.put("location", "");
                } else {
                    mapHot.put("location", jaHot.optString("location"));// 位置
                }
                if (!jaHot.has("applaud_num") || null == jaHot.getString("applaud_num")
                        || "null".equals(jaHot.getString("applaud_num")) || jaHot.getString("applaud_num").equals("")) {
                    mapHot.put("applaud_num", "0");
                } else {
                    mapHot.put("applaud_num", jaHot.optString("applaud_num"));// 点赞数
                }
                if (!jaHot.has("report_num") || null == jaHot.getString("report_num")
                        || "null".equals(jaHot.getString("report_num")) || jaHot.getString("report_num").equals("")) {
                    mapHot.put("report_num", "0");
                } else {
                    mapHot.put("report_num", jaHot.optString("report_num"));// 举报数
                }
                if (!jaHot.has("content") || null == jaHot.getString("content")
                        || "null".equals(jaHot.getString("content")) || jaHot.getString("content").equals("")) {
                    mapHot.put("content", "");
                } else {
                    mapHot.put("content", jaHot.optString("content"));// 文本
                }
                if (!jaHot.has("base_user_id") || null == jaHot.getString("base_user_id")
                        || "null".equals(jaHot.getString("base_user_id"))
                        || jaHot.getString("base_user_id").equals("")) {
                    mapHot.put("base_user_id", "");
                } else {
                    mapHot.put("base_user_id", jaHot.optString("base_user_id"));// 楼主用户id
                }
                if (!jaHot.has("status") || null == jaHot.getString("status")
                        || "null".equals(jaHot.getString("status")) || jaHot.getString("status").equals("")) {
                    mapHot.put("status", "");
                } else {
                    mapHot.put("status", jaHot.optString("status"));// 回复状态0
                }
                if (!jaHot.has("send_time") || null == jaHot.getString("send_time")
                        || "null".equals(jaHot.getString("send_time")) || jaHot.getString("send_time").equals("")) {
                    mapHot.put("send_time", "");
                } else {
                    mapHot.put("send_time", jaHot.optString("send_time"));// 发表时间
                }
                if (!jaHot.has("comments_applaud_status") || null == jaHot.getString("comments_applaud_status")
                        || "null".equals(jaHot.getString("comments_applaud_status"))
                        || jaHot.getString("comments_applaud_status").equals("")) {
                    mapHot.put("comments_applaud_status", "0");
                } else {
                    mapHot.put("comments_applaud_status", jaHot.optString("comments_applaud_status"));// 评论点赞状态
                }
                List<String> zanList = new ArrayList<String>();
                if (!jaHot.has("applaud_user_list") || null == jaHot.getString("applaud_user_list")
                        || "null".equals(jaHot.getString("applaud_user_list"))
                        || jaHot.getString("applaud_user_list").equals("")) {
                    mapHot.put("applaud_user_list", zanList);// 点赞用户id
                } else {
                    JSONArray jaSonList = jaHot.getJSONArray("applaud_user_list");
                    for (int k = 0; k < jaSonList.length(); k++) {
                        String object = (String) jaSonList.getString(k);
                        zanList.add(object);
                    }
                    mapHot.put("applaud_user_list", zanList);// 点赞用户id
                }
                List<HashMap<String, String>> sonList = new ArrayList<HashMap<String, String>>();
                if (!jaHot.has("replies_list") || null == jaHot.getString("replies_list")
                        || "null".equals(jaHot.getString("replies_list"))
                        || jaHot.getString("replies_list").equals("")) {
                    mapHot.put("replies_list", sonList);// 子评论列表
                } else {
                    JSONArray jaSonList = jaHot.getJSONArray("replies_list");
                    for (int k = 0; k < jaSonList.length(); k++) {
                        JSONObject jSon = jaSonList.getJSONObject(k);
                        HashMap<String, String> sonMap = new HashMap<String, String>();
                        if (!jSon.has("replies_id") || null == jSon.getString("replies_id")
                                || "null".equals(jSon.getString("replies_id"))
                                || jSon.getString("replies_id").equals("")) {
                            sonMap.put("replies_id", "");
                        } else {
                            sonMap.put("replies_id", jSon.optString("replies_id"));// 回复内评论id
                        }
                        if (!jSon.has("send_user_id") || null == jSon.getString("send_user_id")
                                || "null".equals(jSon.getString("send_user_id"))
                                || jSon.getString("send_user_id").equals("")) {
                            sonMap.put("send_user_id", "");
                        } else {
                            sonMap.put("send_user_id", jSon.optString("send_user_id"));// 评论用户id
                        }
                        if (!jSon.has("send_nickname") || null == jSon.getString("send_nickname")
                                || jSon.getString("send_nickname").equals("")) {
                            sonMap.put("send_nickname", "");
                        } else {
                            sonMap.put("send_nickname", jSon.optString("send_nickname"));// 评论用户昵称
                        }
                        if (!jSon.has("receive_user_id") || null == jSon.getString("receive_user_id")
                                || "null".equals(jSon.getString("receive_user_id"))
                                || jSon.getString("receive_user_id").equals("")) {
                            sonMap.put("receive_user_id", "");
                        } else {
                            sonMap.put("receive_user_id", jSon.optString("receive_user_id"));// 被评论用户id
                        }
                        if (!jSon.has("receive_nickname") || null == jSon.getString("receive_nickname")
                                || jSon.getString("receive_nickname").equals("")) {
                            sonMap.put("receive_nickname", "");
                        } else {
                            sonMap.put("receive_nickname", jSon.optString("receive_nickname"));// 被评论用户昵称
                        }
                        if (!jSon.has("content") || null == jSon.getString("content")
                                || jSon.getString("content").equals("")) {
                            sonMap.put("content", "");
                        } else {
                            sonMap.put("content", jSon.optString("content"));// 子评论文本
                        }
                        if (!jSon.has("send_time") || null == jSon.getString("send_time")
                                || "null".equals(jSon.getString("send_time"))
                                || jSon.getString("send_time").equals("")) {
                            sonMap.put("send_time", "");
                        } else {
                            sonMap.put("send_time", jSon.optString("send_time"));// 时间
                        }
                        if (!jSon.has("status") || null == jSon.getString("status")
                                || "null".equals(jSon.getString("status")) || jSon.getString("status").equals("")) {
                            sonMap.put("status", "");
                        } else {
                            sonMap.put("status", jSon.optString("status"));// 回复状态
                        }
                        sonList.add(sonMap);
                    }
                    mapHot.put("replies_list", sonList);// 子评论列表
                }
                listHotComments.add(mapHot);
            }
            map.put("hot_comments", listHotComments);
        }
        if (!j.has("related_recommended") || null == j.getString("related_recommended")
                || "null".equals(j.getString("related_recommended")) || j.getString("related_recommended").equals("")) {
            map.put("related_recommended", listRecommend);
        } else {
            JSONArray jaRecommendList = j.getJSONArray("related_recommended");
            for (int i = 0; i < jaRecommendList.length(); i++) {
                HashMap<String, Object> recommendMap = new HashMap<String, Object>();
                JSONObject jo = jaRecommendList.getJSONObject(i);
                if (!jo.has("theme_id") || null == jo.getString("theme_id") || "null".equals(jo.getString("theme_id"))
                        || jo.getString("theme_id").equals("")) {
                    recommendMap.put("theme_id", "");
                } else {
                    recommendMap.put("theme_id", jo.optString("theme_id"));// 帖子id
                }
                if (!jo.has("user_id") || null == jo.getString("user_id") || "null".equals(jo.getString("user_id"))
                        || jo.getString("user_id").equals("")) {
                    recommendMap.put("user_id", "");
                } else {
                    recommendMap.put("user_id", jo.optString("user_id"));// 用户id
                }
                if (!jo.has("head_pic") || null == jo.getString("head_pic") || "null".equals(jo.getString("head_pic"))
                        || jo.getString("head_pic").equals("")) {
                    recommendMap.put("head_pic", "");
                } else {
                    recommendMap.put("head_pic", jo.optString("head_pic"));// 头像
                }
                if (!jo.has("title") || null == jo.getString("title") || "null".equals(jo.getString("title"))
                        || jo.getString("title").equals("")) {
                    recommendMap.put("title", "");
                } else {
                    recommendMap.put("title", jo.optString("title"));// 标题
                }
                if (!jo.has("nickname") || null == jo.getString("nickname") || jo.getString("nickname").equals("")) {
                    recommendMap.put("nickname", "");
                } else {
                    recommendMap.put("nickname", jo.optString("nickname"));// 昵称
                }
                if (!jo.has("date") || null == jo.getString("date") || "null".equals(jo.getString("date"))
                        || jo.getString("date").equals("")) {
                    recommendMap.put("date", "");
                } else {
                    recommendMap.put("date", jo.optString("date"));// 日期
                }
                if (!jo.has("send_time") || null == jo.getString("send_time")
                        || "null".equals(jo.getString("send_time")) || jo.getString("send_time").equals("")) {
                    recommendMap.put("send_time", "");
                } else {
                    recommendMap.put("send_time", jo.optString("send_time"));// 时间
                }
                if (!jo.has("content") || null == jo.getString("content") || jo.getString("content").equals("")) {
                    recommendMap.put("content", "");
                } else {
                    recommendMap.put("content", jo.optString("content"));// 文本
                }
                if (!jo.has("pics") || null == jo.getString("pics") || "null".equals(jo.getString("pics"))
                        || jo.getString("pics").equals("")) {
                    recommendMap.put("pics", "");
                } else {
                    recommendMap.put("pics", jo.optString("pics"));// 图片
                }
                if (!jo.has("location") || null == jo.getString("location") || "null".equals(jo.getString("location"))
                        || jo.getString("location").equals("")) {
                    recommendMap.put("location", "");
                } else {
                    recommendMap.put("location", jo.optString("location"));// 位置
                }
                if (!jo.has("theme_type") || null == jo.getString("theme_type")
                        || "null".equals(jo.getString("theme_type")) || jo.getString("theme_type").equals("")) {
                    recommendMap.put("theme_type", "");
                } else {
                    recommendMap.put("theme_type", jo.optString("theme_type"));// 类型
                }
                listRecommend.add(recommendMap);
            }
            map.put("related_recommended", listRecommend);
        }
        return map;
    }

    /***
     * 获取密友圈详情最新评论
     */
    public static final List<HashMap<String, Object>> createSweetNewCommentList(Context context, String result)
            throws Exception {
        List<HashMap<String, Object>> listNewComments = new ArrayList<HashMap<String, Object>>();// 热门评论
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (!"1".equals(j.getString("status"))) {
            return null;
        }
        if (!j.has("data") || null == j.getString("data") || "null".equals(j.getString("data"))
                || j.getString("data").equals("")) {
            return listNewComments;
        } else {
            JSONArray jaHotList = j.getJSONArray("data");
            for (int i = 0; i < jaHotList.length(); i++) {
                JSONObject jaHot = jaHotList.getJSONObject(i);
                HashMap<String, Object> mapHot = new HashMap<String, Object>();
                if (!jaHot.has("status") || null == jaHot.getString("status")
                        || "null".equals(jaHot.getString("status")) || jaHot.getString("status").equals("")) {
                    mapHot.put("status", "0");
                } else {
                    mapHot.put("status", jaHot.optString("status"));// 帖子id
                }


                if (!jaHot.has("theme_id") || null == jaHot.getString("theme_id")
                        || "null".equals(jaHot.getString("theme_id")) || jaHot.getString("theme_id").equals("")) {
                    mapHot.put("theme_id", "");
                } else {
                    mapHot.put("theme_id", jaHot.optString("theme_id"));// 帖子id
                }
                if (!jaHot.has("comment_id") || null == jaHot.getString("comment_id")
                        || "null".equals(jaHot.getString("comment_id")) || jaHot.getString("comment_id").equals("")) {
                    mapHot.put("comment_id", "");
                } else {
                    mapHot.put("comment_id", jaHot.optString("comment_id"));// 评论id
                }
                if (!jaHot.has("user_id") || null == jaHot.getString("user_id")
                        || "null".equals(jaHot.getString("user_id")) || jaHot.getString("user_id").equals("")) {
                    mapHot.put("user_id", "");
                } else {
                    mapHot.put("user_id", jaHot.optString("user_id"));// 用户id
                }
                if (!jaHot.has("nickname") || null == jaHot.getString("nickname")
                        || jaHot.getString("nickname").equals("")) {
                    mapHot.put("nickname", "");
                } else {
                    mapHot.put("nickname", jaHot.optString("nickname"));// 昵称
                }
                if (!jaHot.has("head_pic") || null == jaHot.getString("head_pic")
                        || "null".equals(jaHot.getString("head_pic")) || jaHot.getString("head_pic").equals("")) {
                    mapHot.put("head_pic", "");
                } else {
                    mapHot.put("head_pic", jaHot.optString("head_pic"));// 头像
                }
                if (!jaHot.has("v_ident") || null == jaHot.getString("v_ident")
                        || "null".equals(jaHot.getString("v_ident")) || jaHot.getString("v_ident").equals("")) {
                    mapHot.put("v_ident", "0");
                } else {
                    mapHot.put("v_ident", jaHot.optString("v_ident"));// 头像
                }
                if (!jaHot.has("location") || null == jaHot.getString("location")
                        || "null".equals(jaHot.getString("location")) || jaHot.getString("location").equals("")) {
                    mapHot.put("location", "");
                } else {
                    mapHot.put("location", jaHot.optString("location"));// 位置
                }
                if (!jaHot.has("applaud_num") || null == jaHot.getString("applaud_num")
                        || "null".equals(jaHot.getString("applaud_num")) || jaHot.getString("applaud_num").equals("")) {
                    mapHot.put("applaud_num", "0");
                } else {
                    mapHot.put("applaud_num", jaHot.optString("applaud_num"));// 点赞数
                }
                if (!jaHot.has("report_num") || null == jaHot.getString("report_num")
                        || "null".equals(jaHot.getString("report_num")) || jaHot.getString("report_num").equals("")) {
                    mapHot.put("report_num", "0");
                } else {
                    mapHot.put("report_num", jaHot.optString("report_num"));// 举报数
                }
                if (!jaHot.has("content") || null == jaHot.getString("content")
                        || jaHot.getString("content").equals("")) {
                    mapHot.put("content", "");
                } else {
                    mapHot.put("content", jaHot.optString("content"));// 文本
                }
                if (!jaHot.has("base_user_id") || null == jaHot.getString("base_user_id")
                        || "null".equals(jaHot.getString("base_user_id"))
                        || jaHot.getString("base_user_id").equals("")) {
                    mapHot.put("base_user_id", "");
                } else {
                    mapHot.put("base_user_id", jaHot.optString("base_user_id"));// 楼主用户id
                }
                if (!jaHot.has("status") || null == jaHot.getString("status")
                        || "null".equals(jaHot.getString("status")) || jaHot.getString("status").equals("")) {
                    mapHot.put("status", "");
                } else {
                    mapHot.put("status", jaHot.optString("status"));// 回复状态0
                }
                if (!jaHot.has("send_time") || null == jaHot.getString("send_time")
                        || "null".equals(jaHot.getString("send_time")) || jaHot.getString("send_time").equals("")) {
                    mapHot.put("send_time", "");
                } else {
                    mapHot.put("send_time", jaHot.optString("send_time"));// 发表时间
                }
                if (!jaHot.has("comments_applaud_status") || null == jaHot.getString("comments_applaud_status")
                        || "null".equals(jaHot.getString("comments_applaud_status"))
                        || jaHot.getString("comments_applaud_status").equals("")) {
                    mapHot.put("comments_applaud_status", "0");
                } else {
                    mapHot.put("comments_applaud_status", jaHot.optString("comments_applaud_status"));// 评论点赞状态
                }
                List<Integer> zanList = new ArrayList<Integer>();
                if (!jaHot.has("applaud_user_list") || null == jaHot.getString("applaud_user_list")
                        || "null".equals(jaHot.getString("applaud_user_list"))
                        || jaHot.getString("applaud_user_list").equals("")) {
                    mapHot.put("applaud_user_list", zanList);// 点赞用户id
                } else {
                    JSONArray jaSonList = jaHot.getJSONArray("applaud_user_list");
                    for (int k = 0; k < jaSonList.length(); k++) {
                        Integer object = (Integer) jaSonList.get(k);
                        zanList.add(object);
                    }
                    mapHot.put("applaud_user_list", zanList);// 点赞用户id
                }
                List<HashMap<String, String>> sonList = new ArrayList<HashMap<String, String>>();
                if (!jaHot.has("replies_list") || null == jaHot.getString("replies_list")
                        || "null".equals(jaHot.getString("replies_list"))
                        || jaHot.getString("replies_list").equals("")) {
                    mapHot.put("replies_list", sonList);// 子评论列表
                } else {
                    JSONArray jaSonList = jaHot.getJSONArray("replies_list");
                    for (int k = 0; k < jaSonList.length(); k++) {
                        JSONObject jSon = jaSonList.getJSONObject(k);
                        HashMap<String, String> sonMap = new HashMap<String, String>();
                        if (!jSon.has("status") || null == jSon.getString("status")
                                || "null".equals(jSon.getString("status"))
                                || jSon.getString("status").equals("")) {
                            sonMap.put("status", "0");
                        } else {
                            sonMap.put("status", jSon.optString("status"));// 用来区分是否展示
                        }


                        if (!jSon.has("replies_id") || null == jSon.getString("replies_id")
                                || "null".equals(jSon.getString("replies_id"))
                                || jSon.getString("replies_id").equals("")) {
                            sonMap.put("replies_id", "");
                        } else {
                            sonMap.put("replies_id", jSon.optString("replies_id"));// 回复内评论id
                        }
                        if (!jSon.has("send_user_id") || null == jSon.getString("send_user_id")
                                || "null".equals(jSon.getString("send_user_id"))
                                || jSon.getString("send_user_id").equals("")) {
                            sonMap.put("send_user_id", "");
                        } else {
                            sonMap.put("send_user_id", jSon.optString("send_user_id"));// 评论用户id
                        }
                        if (!jSon.has("send_nickname") || null == jSon.getString("send_nickname")
                                || jSon.getString("send_nickname").equals("")) {
                            sonMap.put("send_nickname", "");
                        } else {
                            sonMap.put("send_nickname", jSon.optString("send_nickname"));// 评论用户昵称
                        }
                        if (!jSon.has("receive_user_id") || null == jSon.getString("receive_user_id")
                                || "null".equals(jSon.getString("receive_user_id"))
                                || jSon.getString("receive_user_id").equals("")) {
                            sonMap.put("receive_user_id", "");
                        } else {
                            sonMap.put("receive_user_id", jSon.optString("receive_user_id"));// 被评论用户id
                        }
                        if (!jSon.has("receive_nickname") || null == jSon.getString("receive_nickname")
                                || jSon.getString("receive_nickname").equals("")) {
                            sonMap.put("receive_nickname", "");
                        } else {
                            sonMap.put("receive_nickname", jSon.optString("receive_nickname"));// 被评论用户昵称
                        }
                        if (!jSon.has("content") || null == jSon.getString("content")
                                || jSon.getString("content").equals("")) {
                            sonMap.put("content", "");
                        } else {
                            sonMap.put("content", jSon.optString("content"));// 子评论文本
                        }
                        if (!jSon.has("send_time") || null == jSon.getString("send_time")
                                || "null".equals(jSon.getString("send_time"))
                                || jSon.getString("send_time").equals("")) {
                            sonMap.put("send_time", "");
                        } else {
                            sonMap.put("send_time", jSon.optString("send_time"));// 时间
                        }
                        if (!jSon.has("status") || null == jSon.getString("status")
                                || "null".equals(jSon.getString("status")) || jSon.getString("status").equals("")) {
                            sonMap.put("status", "");
                        } else {
                            sonMap.put("status", jSon.optString("status"));// 回复状态
                        }
                        sonList.add(sonMap);
                    }
                    mapHot.put("replies_list", sonList);// 子评论列表
                }
                listNewComments.add(mapHot);
            }
            return listNewComments;
        }
    }

    /***
     * 获取密友圈详情只看楼主评论
     */
    public static final HashMap<String, List<HashMap<String, Object>>> createSweetHostCommentList(Context context,
                                                                                                  String result) throws Exception {
        HashMap<String, List<HashMap<String, Object>>> map = new HashMap<String, List<HashMap<String, Object>>>();// 全部评论
        List<HashMap<String, Object>> listNewComments = new ArrayList<HashMap<String, Object>>();// 最新评论
        List<HashMap<String, Object>> listHotComments = new ArrayList<HashMap<String, Object>>();// 热门评论
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (!"1".equals(j.getString("status"))) {
            return null;
        }
        if (!j.has("data") || null == j.getString("data") || "null".equals(j.getString("data"))
                || j.getString("data").equals("")) {
            // return listNewComments;
        } else {
            JSONArray jaHotList = j.getJSONArray("data");
            for (int i = 0; i < jaHotList.length(); i++) {
                JSONObject jaHot = jaHotList.getJSONObject(i);
                HashMap<String, Object> mapHot = new HashMap<String, Object>();
                if (!jaHot.has("theme_id") || null == jaHot.getString("theme_id")
                        || "null".equals(jaHot.getString("theme_id")) || jaHot.getString("theme_id").equals("")) {
                    mapHot.put("theme_id", "");
                } else {
                    mapHot.put("theme_id", jaHot.optString("theme_id"));// 帖子id
                }
                if (!jaHot.has("comment_id") || null == jaHot.getString("comment_id")
                        || "null".equals(jaHot.getString("comment_id")) || jaHot.getString("comment_id").equals("")) {
                    mapHot.put("comment_id", "");
                } else {
                    mapHot.put("comment_id", jaHot.optString("comment_id"));// 评论id
                }
                if (!jaHot.has("user_id") || null == jaHot.getString("user_id")
                        || "null".equals(jaHot.getString("user_id")) || jaHot.getString("user_id").equals("")) {
                    mapHot.put("user_id", "");
                } else {
                    mapHot.put("user_id", jaHot.optString("user_id"));// 用户id
                }
                if (!jaHot.has("nickname") || null == jaHot.getString("nickname")
                        || jaHot.getString("nickname").equals("")) {
                    mapHot.put("nickname", "");
                } else {
                    mapHot.put("nickname", jaHot.optString("nickname"));// 昵称
                }
                if (!jaHot.has("head_pic") || null == jaHot.getString("head_pic")
                        || "null".equals(jaHot.getString("head_pic")) || jaHot.getString("head_pic").equals("")) {
                    mapHot.put("head_pic", "");
                } else {
                    mapHot.put("head_pic", jaHot.optString("head_pic"));// 头像
                }
                if (!jaHot.has("v_ident") || null == jaHot.getString("v_ident")
                        || "null".equals(jaHot.getString("v_ident")) || jaHot.getString("v_ident").equals("")) {
                    mapHot.put("v_ident", "0");
                } else {
                    mapHot.put("v_ident", jaHot.optString("v_ident"));// 头像
                }
                if (!jaHot.has("location") || null == jaHot.getString("location")
                        || "null".equals(jaHot.getString("location")) || jaHot.getString("location").equals("")) {
                    mapHot.put("location", "");
                } else {
                    mapHot.put("location", jaHot.optString("location"));// 位置
                }
                if (!jaHot.has("applaud_num") || null == jaHot.getString("applaud_num")
                        || "null".equals(jaHot.getString("applaud_num")) || jaHot.getString("applaud_num").equals("")) {
                    mapHot.put("applaud_num", "0");
                } else {
                    mapHot.put("applaud_num", jaHot.optString("applaud_num"));// 点赞数
                }
                if (!jaHot.has("report_num") || null == jaHot.getString("report_num")
                        || "null".equals(jaHot.getString("report_num")) || jaHot.getString("report_num").equals("")) {
                    mapHot.put("report_num", "0");
                } else {
                    mapHot.put("report_num", jaHot.optString("report_num"));// 举报数
                }
                if (!jaHot.has("content") || null == jaHot.getString("content")
                        || jaHot.getString("content").equals("")) {
                    mapHot.put("content", "");
                } else {
                    mapHot.put("content", jaHot.optString("content"));// 文本
                }
                if (!jaHot.has("base_user_id") || null == jaHot.getString("base_user_id")
                        || "null".equals(jaHot.getString("base_user_id"))
                        || jaHot.getString("base_user_id").equals("")) {
                    mapHot.put("base_user_id", "");
                } else {
                    mapHot.put("base_user_id", jaHot.optString("base_user_id"));// 楼主用户id
                }
                if (!jaHot.has("status") || null == jaHot.getString("status")
                        || "null".equals(jaHot.getString("status")) || jaHot.getString("status").equals("")) {
                    mapHot.put("status", "");
                } else {
                    mapHot.put("status", jaHot.optString("status"));// 回复状态0
                }
                if (!jaHot.has("send_time") || null == jaHot.getString("send_time")
                        || "null".equals(jaHot.getString("send_time")) || jaHot.getString("send_time").equals("")) {
                    mapHot.put("send_time", "");
                } else {
                    mapHot.put("send_time", jaHot.optString("send_time"));// 发表时间
                }
                if (!jaHot.has("comments_applaud_status") || null == jaHot.getString("comments_applaud_status")
                        || "null".equals(jaHot.getString("comments_applaud_status"))
                        || jaHot.getString("comments_applaud_status").equals("")) {
                    mapHot.put("comments_applaud_status", "0");
                } else {
                    mapHot.put("comments_applaud_status", jaHot.optString("comments_applaud_status"));// 评论点赞状态
                }
                List<Integer> zanList = new ArrayList<Integer>();
                if (!jaHot.has("applaud_user_list") || null == jaHot.getString("applaud_user_list")
                        || "null".equals(jaHot.getString("applaud_user_list"))
                        || jaHot.getString("applaud_user_list").equals("")) {
                    mapHot.put("applaud_user_list", zanList);// 点赞用户id
                } else {
                    JSONArray jaSonList = jaHot.getJSONArray("applaud_user_list");
                    for (int k = 0; k < jaSonList.length(); k++) {
                        Integer object = (Integer) jaSonList.get(k);
                        zanList.add(object);
                    }
                    mapHot.put("applaud_user_list", zanList);// 点赞用户id
                }
                List<HashMap<String, String>> sonList = new ArrayList<HashMap<String, String>>();
                if (!jaHot.has("replies_list") || null == jaHot.getString("replies_list")
                        || "null".equals(jaHot.getString("replies_list"))
                        || jaHot.getString("replies_list").equals("")) {
                    mapHot.put("replies_list", sonList);// 子评论列表
                } else {
                    JSONArray jaSonList = jaHot.getJSONArray("replies_list");
                    for (int k = 0; k < jaSonList.length(); k++) {
                        JSONObject jSon = jaSonList.getJSONObject(k);
                        HashMap<String, String> sonMap = new HashMap<String, String>();
                        if (!jSon.has("replies_id") || null == jSon.getString("replies_id")
                                || "null".equals(jSon.getString("replies_id"))
                                || jSon.getString("replies_id").equals("")) {
                            sonMap.put("replies_id", "");
                        } else {
                            sonMap.put("replies_id", jSon.optString("replies_id"));// 回复内评论id
                        }
                        if (!jSon.has("send_user_id") || null == jSon.getString("send_user_id")
                                || "null".equals(jSon.getString("send_user_id"))
                                || jSon.getString("send_user_id").equals("")) {
                            sonMap.put("send_user_id", "");
                        } else {
                            sonMap.put("send_user_id", jSon.optString("send_user_id"));// 评论用户id
                        }
                        if (!jSon.has("send_nickname") || null == jSon.getString("send_nickname")
                                || "null".equals(jSon.getString("send_nickname"))
                                || jSon.getString("send_nickname").equals("")) {
                            sonMap.put("send_nickname", "");
                        } else {
                            sonMap.put("send_nickname", jSon.optString("send_nickname"));// 评论用户昵称
                        }
                        if (!jSon.has("receive_user_id") || null == jSon.getString("receive_user_id")
                                || jSon.getString("receive_user_id").equals("")) {
                            sonMap.put("receive_user_id", "");
                        } else {
                            sonMap.put("receive_user_id", jSon.optString("receive_user_id"));// 被评论用户id
                        }
                        if (!jSon.has("receive_nickname") || null == jSon.getString("receive_nickname")
                                || jSon.getString("receive_nickname").equals("")) {
                            sonMap.put("receive_nickname", "");
                        } else {
                            sonMap.put("receive_nickname", jSon.optString("receive_nickname"));// 被评论用户昵称
                        }
                        if (!jSon.has("content") || null == jSon.getString("content")
                                || jSon.getString("content").equals("")) {
                            sonMap.put("content", "");
                        } else {
                            sonMap.put("content", jSon.optString("content"));// 子评论文本
                        }
                        if (!jSon.has("send_time") || null == jSon.getString("send_time")
                                || "null".equals(jSon.getString("send_time"))
                                || jSon.getString("send_time").equals("")) {
                            sonMap.put("send_time", "");
                        } else {
                            sonMap.put("send_time", jSon.optString("send_time"));// 时间
                        }
                        if (!jSon.has("status") || null == jSon.getString("status")
                                || "null".equals(jSon.getString("status")) || jSon.getString("status").equals("")) {
                            sonMap.put("status", "");
                        } else {
                            sonMap.put("status", jSon.optString("status"));// 回复状态
                        }
                        sonList.add(sonMap);
                    }
                    mapHot.put("replies_list", sonList);// 子评论列表
                }
                listNewComments.add(mapHot);
            }
            // return listNewComments;
        }

        if (!j.has("data2") || null == j.getString("data2") || "null".equals(j.getString("data2"))
                || j.getString("data2").equals("")) {
            // return listHotComments;
        } else {
            JSONArray jaHotList = j.getJSONArray("data2");
            for (int i = 0; i < jaHotList.length(); i++) {
                JSONObject jaHot = jaHotList.getJSONObject(i);
                HashMap<String, Object> mapHot = new HashMap<String, Object>();
                if (!jaHot.has("theme_id") || null == jaHot.getString("theme_id")
                        || "null".equals(jaHot.getString("theme_id")) || jaHot.getString("theme_id").equals("")) {
                    mapHot.put("theme_id", "");
                } else {
                    mapHot.put("theme_id", jaHot.optString("theme_id"));// 帖子id
                }
                if (!jaHot.has("comment_id") || null == jaHot.getString("comment_id")
                        || "null".equals(jaHot.getString("comment_id")) || jaHot.getString("comment_id").equals("")) {
                    mapHot.put("comment_id", "");
                } else {
                    mapHot.put("comment_id", jaHot.optString("comment_id"));// 评论id
                }
                if (!jaHot.has("user_id") || null == jaHot.getString("user_id")
                        || "null".equals(jaHot.getString("user_id")) || jaHot.getString("user_id").equals("")) {
                    mapHot.put("user_id", "");
                } else {
                    mapHot.put("user_id", jaHot.optString("user_id"));// 用户id
                }
                if (!jaHot.has("nickname") || null == jaHot.getString("nickname")
                        || jaHot.getString("nickname").equals("")) {
                    mapHot.put("nickname", "");
                } else {
                    mapHot.put("nickname", jaHot.optString("nickname"));// 昵称
                }
                if (!jaHot.has("head_pic") || null == jaHot.getString("head_pic")
                        || "null".equals(jaHot.getString("head_pic")) || jaHot.getString("head_pic").equals("")) {
                    mapHot.put("head_pic", "");
                } else {
                    mapHot.put("head_pic", jaHot.optString("head_pic"));// 头像
                }
                if (!jaHot.has("location") || null == jaHot.getString("location")
                        || "null".equals(jaHot.getString("location")) || jaHot.getString("location").equals("")) {
                    mapHot.put("location", "");
                } else {
                    mapHot.put("location", jaHot.optString("location"));// 位置
                }
                if (!jaHot.has("applaud_num") || null == jaHot.getString("applaud_num")
                        || "null".equals(jaHot.getString("applaud_num")) || jaHot.getString("applaud_num").equals("")) {
                    mapHot.put("applaud_num", "0");
                } else {
                    mapHot.put("applaud_num", jaHot.optString("applaud_num"));// 点赞数
                }
                if (!jaHot.has("report_num") || null == jaHot.getString("report_num")
                        || "null".equals(jaHot.getString("report_num")) || jaHot.getString("report_num").equals("")) {
                    mapHot.put("report_num", "0");
                } else {
                    mapHot.put("report_num", jaHot.optString("report_num"));// 举报数
                }
                if (!jaHot.has("content") || null == jaHot.getString("content")
                        || jaHot.getString("content").equals("")) {
                    mapHot.put("content", "");
                } else {
                    mapHot.put("content", jaHot.optString("content"));// 文本
                }
                if (!jaHot.has("base_user_id") || null == jaHot.getString("base_user_id")
                        || "null".equals(jaHot.getString("base_user_id"))
                        || jaHot.getString("base_user_id").equals("")) {
                    mapHot.put("base_user_id", "");
                } else {
                    mapHot.put("base_user_id", jaHot.optString("base_user_id"));// 楼主用户id
                }
                if (!jaHot.has("status") || null == jaHot.getString("status")
                        || "null".equals(jaHot.getString("status")) || jaHot.getString("status").equals("")) {
                    mapHot.put("status", "");
                } else {
                    mapHot.put("status", jaHot.optString("status"));// 回复状态0
                }
                if (!jaHot.has("send_time") || null == jaHot.getString("send_time")
                        || "null".equals(jaHot.getString("send_time")) || jaHot.getString("send_time").equals("")) {
                    mapHot.put("send_time", "");
                } else {
                    mapHot.put("send_time", jaHot.optString("send_time"));// 发表时间
                }
                if (!jaHot.has("comments_applaud_status") || null == jaHot.getString("comments_applaud_status")
                        || "null".equals(jaHot.getString("comments_applaud_status"))
                        || jaHot.getString("comments_applaud_status").equals("")) {
                    mapHot.put("comments_applaud_status", "0");
                } else {
                    mapHot.put("comments_applaud_status", jaHot.optString("comments_applaud_status"));// 评论点赞状态
                }
                List<Integer> zanList = new ArrayList<Integer>();
                if (!jaHot.has("applaud_user_list") || null == jaHot.getString("applaud_user_list")
                        || "null".equals(jaHot.getString("applaud_user_list"))
                        || jaHot.getString("applaud_user_list").equals("")) {
                    mapHot.put("applaud_user_list", zanList);// 点赞用户id
                } else {
                    JSONArray jaSonList = jaHot.getJSONArray("applaud_user_list");
                    for (int k = 0; k < jaSonList.length(); k++) {
                        Integer object = (Integer) jaSonList.get(k);
                        zanList.add(object);
                    }
                    mapHot.put("applaud_user_list", zanList);// 点赞用户id
                }
                List<HashMap<String, String>> sonList = new ArrayList<HashMap<String, String>>();
                if (!jaHot.has("replies_list") || null == jaHot.getString("replies_list")
                        || "null".equals(jaHot.getString("replies_list"))
                        || jaHot.getString("replies_list").equals("")) {
                    mapHot.put("replies_list", sonList);// 子评论列表
                } else {
                    JSONArray jaSonList = jaHot.getJSONArray("replies_list");
                    for (int k = 0; k < jaSonList.length(); k++) {
                        JSONObject jSon = jaSonList.getJSONObject(k);
                        HashMap<String, String> sonMap = new HashMap<String, String>();
                        if (!jSon.has("replies_id") || null == jSon.getString("replies_id")
                                || "null".equals(jSon.getString("replies_id"))
                                || jSon.getString("replies_id").equals("")) {
                            sonMap.put("replies_id", "");
                        } else {
                            sonMap.put("replies_id", jSon.optString("replies_id"));// 回复内评论id
                        }
                        if (!jSon.has("send_user_id") || null == jSon.getString("send_user_id")
                                || "null".equals(jSon.getString("send_user_id"))
                                || jSon.getString("send_user_id").equals("")) {
                            sonMap.put("send_user_id", "");
                        } else {
                            sonMap.put("send_user_id", jSon.optString("send_user_id"));// 评论用户id
                        }
                        if (!jSon.has("send_nickname") || null == jSon.getString("send_nickname")
                                || "null".equals(jSon.getString("send_nickname"))
                                || jSon.getString("send_nickname").equals("")) {
                            sonMap.put("send_nickname", "");
                        } else {
                            sonMap.put("send_nickname", jSon.optString("send_nickname"));// 评论用户昵称
                        }
                        if (!jSon.has("receive_user_id") || null == jSon.getString("receive_user_id")
                                || jSon.getString("receive_user_id").equals("")) {
                            sonMap.put("receive_user_id", "");
                        } else {
                            sonMap.put("receive_user_id", jSon.optString("receive_user_id"));// 被评论用户id
                        }
                        if (!jSon.has("receive_nickname") || null == jSon.getString("receive_nickname")
                                || jSon.getString("receive_nickname").equals("")) {
                            sonMap.put("receive_nickname", "");
                        } else {
                            sonMap.put("receive_nickname", jSon.optString("receive_nickname"));// 被评论用户昵称
                        }
                        if (!jSon.has("content") || null == jSon.getString("content")
                                || jSon.getString("content").equals("")) {
                            sonMap.put("content", "");
                        } else {
                            sonMap.put("content", jSon.optString("content"));// 子评论文本
                        }
                        if (!jSon.has("send_time") || null == jSon.getString("send_time")
                                || "null".equals(jSon.getString("send_time"))
                                || jSon.getString("send_time").equals("")) {
                            sonMap.put("send_time", "");
                        } else {
                            sonMap.put("send_time", jSon.optString("send_time"));// 时间
                        }
                        if (!jSon.has("status") || null == jSon.getString("status")
                                || "null".equals(jSon.getString("status")) || jSon.getString("status").equals("")) {
                            sonMap.put("status", "");
                        } else {
                            sonMap.put("status", jSon.optString("status"));// 回复状态
                        }
                        sonList.add(sonMap);
                    }
                    mapHot.put("replies_list", sonList);// 子评论列表
                }
                listHotComments.add(mapHot);
            }
            // return listHotComments;
        }
        map.put("list_hot", listHotComments);
        map.put("list_new", listNewComments);
        return map;
    }

    /**
     * 获取图片压缩比
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static final HashMap<String, String> createImageRadio(Context context, String result) throws Exception {
        HashMap<String, String> mapRet = new HashMap<String, String>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (jsonObject.has("status") && "1".equals(jsonObject.getString("status"))) {
            if (!jsonObject.has("img_rate") || null == jsonObject.getString("img_rate")
                    || "null".equals(jsonObject.getString("img_rate")) || "".equals(jsonObject.getString("img_rate"))) {
                mapRet.put("img_rate", "450");
            } else {
                mapRet.put("img_rate", jsonObject.has("img_rate") ? jsonObject.getString("img_rate") : "450");
            }
            return mapRet;
        }
        return null;

    }

    /**
     * 集赞奖品设置
     */
    public static final List<HashMap<String, String>> praiseRankingList(Context context, String result)
            throws Exception {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (jsonObject.has("status") && "1".equals(jsonObject.getString("status"))) {
            JSONArray ja = jsonObject.getJSONArray("data");
            for (int i = 0; i < ja.length(); i++) {
                HashMap<String, String> mapRet = new HashMap<String, String>();
                JSONObject jo = ja.getJSONObject(i);
                if (!jo.has("ranking") || null == jo.getString("ranking")// 一等奖
                        // 二等奖
                        // 三等奖
                        || "null".equals(jo.getString("ranking")) || "".equals(jo.getString("ranking"))) {
                    mapRet.put("ranking", "");
                } else {
                    mapRet.put("ranking", jo.has("ranking") ? jo.getString("ranking") : "");
                }
                if (!jo.has("number") || null == jo.getString("number")// 几等奖设有多少名
                        || "null".equals(jo.getString("number")) || "".equals(jo.getString("number"))) {
                    mapRet.put("number", "");
                } else {
                    mapRet.put("number", jo.has("number") ? jo.getString("number") : "");
                }
                if (!jo.has("content") || null == jo.getString("content") || "null".equals(jo.getString("content"))
                        || "".equals(jo.getString("content"))) {
                    mapRet.put("content", "");
                } else {
                    mapRet.put("content", jo.has("content") ? jo.getString("content") : "");
                }
                if (!jo.has("pic") || null == jo.getString("pic") || "null".equals(jo.getString("pic"))
                        || "".equals(jo.getString("pic"))) {
                    mapRet.put("pic", "");
                } else {
                    mapRet.put("pic", jo.has("pic") ? jo.getString("pic") : "");
                }
                list.add(mapRet);
            }

            return list;
        }
        return null;

    }

    /**
     * 获取额度最新25条奖励
     */
    public static final List<HashMap<String, String>> createPraiseRanking(Context context, String result)
            throws JSONException {

        JSONObject jsonObject = new JSONObject(result);
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!"1".equals(jsonObject.getString("status"))) {
            return null;
        }
        JSONArray ja = jsonObject.getJSONArray("point_list");
        for (int i = 0; i < ja.length(); i++) {
            String js = ja.getString(i);

            JSONObject jo = new JSONObject(js);

            HashMap<String, String> map = new HashMap<String, String>();

            if (!jo.has("user_id") || null == jo.getString("user_id")) {
                map.put("user_id", "");
            } else {
                map.put("user_id", jo.optString("user_id", ""));
            }
            if (!jo.has("nickname") || null == jo.getString("nickname") || "null".equals(jo.getString("nickname"))
                    || "".equals(jo.getString("nickname"))) {
                map.put("nickname", "*****");
            } else {
                map.put("nickname", jo.optString("nickname"));
            }

            if (!jo.has("location") || null == jo.getString("location") || "null".equals(jo.getString("location"))
                    || "".equals(jo.getString("location"))) {
                map.put("location", "来自喵星");
            } else {
                map.put("location", jo.optString("location"));
            }
            if (!jo.has("pic") || null == jo.getString("pic") || "null".equals(jo.getString("pic"))
                    || "".equals(jo.getString("pic"))) {
                map.put("pic", "");
            } else {
                map.put("pic", jo.optString("pic", ""));
            }
            if (!jo.has("point_count") || null == jo.getString("point_count") || "null".equals(jo.getString("point_count"))
                    || "".equals(jo.getString("point_count"))) {
                map.put("point_count", "");
            } else {
                map.put("point_count", jo.optString("point_count", ""));
            }
            list.add(map);
        }
        return list;
    }


    /**
     * 获取集赞额度奖励
     */
    public static final List<HashMap<String, String>> createPraiseList(Context context, String result)
            throws JSONException {

        JSONObject jsonObject = new JSONObject(result);
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!"1".equals(jsonObject.getString("status"))) {
            return null;
        }
        JSONArray ja = jsonObject.getJSONArray("popup_List");
        for (int i = 0; i < ja.length(); i++) {
            String js = ja.getString(i);

            JSONObject jo = new JSONObject(js);

            HashMap<String, String> map = new HashMap<String, String>();

            if (!jo.has("nickname") || null == jo.getString("nickname")) {
                map.put("nickname", "*****");
            } else {
                map.put("nickname", jo.optString("nickname", "*****"));
            }
            if (!jo.has("today_reward") || null == jo.getString("today_reward") || "null".equals(jo.getString("today_reward"))
                    || "".equals(jo.getString("today_reward"))) {
                map.put("today_reward", "1.0");
            } else {
                map.put("today_reward", "" + jo.optString("today_reward", "1.0"));
            }

            if (!jo.has("pic") || null == jo.getString("pic") || "null".equals(jo.getString("pic"))
                    || "".equals(jo.getString("pic"))) {
                map.put("pic", "");
            } else {
                map.put("pic", jo.optString("pic", ""));
            }
            if (!jo.has("user_id") || null == jo.getString("user_id") || "null".equals(jo.getString("user_id"))
                    || "".equals(jo.getString("user_id"))) {
                map.put("user_id", "");
            } else {
                map.put("user_id", jo.optString("user_id", ""));
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 集赞奖励弹窗（点赞数，获得奖励）
     */
    public static HashMap<String, String> createPraiseMoney(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!"1".equals(jsonObject.getString("status"))) {
            return null;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        if (!jsonObject.has("oldFriends") || null == jsonObject.getString("oldFriends") || "null".equals(jsonObject.getString("oldFriends"))
                || "".equals(jsonObject.getString("oldFriends"))) {
            map.put("oldFriends", "0");
        } else {
            map.put("oldFriends", jsonObject.optString("oldFriends", "0"));
        }
        if (!jsonObject.has("newFriends") || null == jsonObject.getString("newFriends") || "null".equals(jsonObject.getString("newFriends"))
                || "".equals(jsonObject.getString("newFriends"))) {
            map.put("newFriends", "0");
        } else {
            map.put("newFriends", jsonObject.optString("newFriends", "0"));
        }
        if (!jsonObject.has("today_rewards") || null == jsonObject.getString("today_rewards") || "null".equals(jsonObject.getString("today_rewards"))
                || "".equals(jsonObject.getString("today_rewards"))) {
            map.put("today_rewards", "0.00");
        } else {
            map.put("today_rewards", jsonObject.optString("today_rewards", "0.00"));
        }
        return map;
    }


//    /**
//     * 集赞分享 获取文案
//     */
//    public static List<HashMap<String, String>> createPointShareContent(Context context, String result) throws JSONException {
//        JSONObject jsonObject = new JSONObject(result);
//        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//        if (null == jsonObject || "".equals(jsonObject)) {
//            return null;
//        }
//
//        if (!"1".equals(jsonObject.getString("status"))) {
//            return null;
//        }
//
//        if (!jsonObject.has("data") || null == jsonObject.getString("data") || "null".equals(jsonObject.getString("data"))
//                || "".equals(jsonObject.getString("data"))) {
//            return null;
//        }
//        JSONArray ja = new JSONArray(jsonObject.getString("data"));
//
//        for (int i = 0; i < ja.length(); i++) {
//            JSONObject jo = ja.getJSONObject(i);
//
//            HashMap<String, String> map = new HashMap<String, String>();
//
//
//            if (!jo.has("title") || null == jo.getString("title")) {
//                map.put("title", "");
//            } else {
//                map.put("title", jo.optString("title", ""));
//            }
//            if (!jo.has("txt") || null == jo.getString("txt") || "null".equals(jo.getString("txt"))
//                    || "".equals(jo.getString("txt"))) {
//                map.put("txt", "");
//            } else {
//                map.put("txt", jo.optString("txt", ""));
//            }
//
//            if (!jo.has("png") || null == jo.getString("png") || "null".equals(jo.getString("png"))
//                    || "".equals(jo.getString("png"))) {
//                map.put("png", "");
//            } else {
//                map.put("png", jo.optString("png", ""));
//            }
//            list.add(map);
//        }
//
//        return list;
//    }


//    /**
//     * he 集赞奖励规则 文案获取(getPointShareContent 同一个接口取不同字段)
//     */
//    public static HashMap<String, String> createPointGuiZeContent(Context context, String result) throws JSONException {
//        JSONObject jsonObject = new JSONObject(result);
//        HashMap<String, String> map = new HashMap<String, String>();
//        if (null == jsonObject || "".equals(jsonObject)) {
//            return null;
//        }
//
//        if (!"1".equals(jsonObject.getString("status"))) {
//            return null;
//        }
//
//
////        if (!jsonObject.has("fxqd") || null == jsonObject.getString("fxqd") || "".equals(jsonObject.getString("fxqd"))
////                || "null".equals(jsonObject.getString("fxqd"))) {
////            SignFragment.fxqd = "0.0";
////        } else {
////            SignFragment.fxqd = jsonObject.getString("fxqd");
////        }
//
////        //新手引导赚钱任务 弹框文案
////        if (!jsonObject.has("syxsyd") || null == jsonObject.getString("syxsyd") || "".equals(jsonObject.getString("syxsyd"))
////                || "null".equals(jsonObject.getString("syxsyd"))) {
////            DialogUtils.yContent = "";
////        } else {
////            DialogUtils.yContent = jsonObject.getString("syxsyd");
////        }
//
//        if (!jsonObject.has("jzjl") || null == jsonObject.getString("jzjl") || "".equals(jsonObject.getString("jzjl"))
//                || "null".equals(jsonObject.getString("jzjl"))) {
//            return null;
//        }
//
//        String joString = jsonObject.getString("jzjl");
//        JSONObject jo = new JSONObject(joString);
//        if (null == jo || "".equals(jo)) {
//            return null;
//        }
//
//        if (!jo.has("n1") || null == jo.getString("n1") || "null".equals(jo.getString("n1"))) {
//            map.put("n1", "");
//        } else {
//            map.put("n1", jo.optString("n1", ""));
//        }
//        if (!jo.has("n2") || null == jo.getString("n2") || "null".equals(jo.getString("n2"))
//                || "".equals(jo.getString("n2"))) {
//            map.put("n2", "");
//        } else {
//            map.put("n2", jo.optString("n2", ""));
//        }
//
//        if (!jo.has("n3") || null == jo.getString("n3") || "null".equals(jo.getString("n3"))
//                || "".equals(jo.getString("n3"))) {
//            map.put("n3", "");
//        } else {
//            map.put("n3", jo.optString("n3", ""));
//        }
//
//        if (!jo.has("n4-1") || null == jo.getString("n4-1") || "null".equals(jo.getString("n4-1"))
//                || "".equals(jo.getString("n4-1"))) {
//            map.put("n4-1", "");
//        } else {
//            map.put("n4-1", jo.optString("n4-1", ""));
//        }
//
//        if (!jo.has("n4-2") || null == jo.getString("n4-2") || "null".equals(jo.getString("n4-2"))
//                || "".equals(jo.getString("n4-2"))) {
//            map.put("n4-2", "");
//        } else {
//            map.put("n4-2", jo.optString("n4-2", ""));
//        }
//
//        if (!jo.has("n6") || null == jo.getString("n6") || "null".equals(jo.getString("n6"))
//                || "".equals(jo.getString("n6"))) {
//            map.put("n6", "");
//        } else {
//            map.put("n6", jo.optString("n6", ""));
//        }
//
//        return map;
//    }


    /*
     * 获取衣豆抽奖减半信息 并获取 减半过期时间
     */
    public static HashMap<String, String> createYiDouHalve(Context context, String result) throws JSONException {
        JSONObject jo = new JSONObject(result);
        if (null == jo || "".equals(jo)) {
            return null;
        }

        if (!"1".equals(jo.getString("status"))) {
            return null;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        if (!jo.has("now") || null == jo.getString("now") || "null".equals(jo.getString("now"))
                || "".equals(jo.getString("now"))) {
            map.put("now", "0");
        } else {
            map.put("now", jo.optString("now", "0"));//获取系统当前时间 直接放到 data数据的同一个Map中
        }
        JSONObject jsonObject = jo.getJSONObject("data");
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        if (!jsonObject.has("end_date") || null == jsonObject.getString("end_date") || "null".equals(jsonObject.getString("end_date"))
                || "".equals(jsonObject.getString("end_date"))) {
            map.put("end_date", "0");
        } else {
            map.put("end_date", jsonObject.optString("end_date", "0"));

            SharedPreferencesUtil.saveStringData(context, YConstance.Pref.YIDOU_HALVE_END_TIMES, jsonObject.optString("end_date"));
        }
        if (!jsonObject.has("is_open") || null == jsonObject.getString("is_open") || "null".equals(jsonObject.getString("is_open"))
                || "".equals(jsonObject.getString("is_open"))) {
            map.put("is_open", "0");
        } else {
            map.put("is_open", jsonObject.optString("is_open", "0"));
        }
        if (!jsonObject.has("twofoldness") || null == jsonObject.getString("twofoldness") || "null".equals(jsonObject.getString("twofoldness"))
                || "".equals(jsonObject.getString("twofoldness"))) {
            map.put("twofoldness", "1");
        } else {
            map.put("twofoldness", jsonObject.optString("twofoldness", "1"));//减半倍数  默认不减半
        }
        if (!jsonObject.has("id") || null == jsonObject.getString("id") || "null".equals(jsonObject.getString("id"))
                || "".equals(jsonObject.getString("id"))) {
            map.put("id", "");
        } else {
            map.put("id", jsonObject.optString("id", ""));
        }
        return map;
    }

    /*
     * 衣豆减半获取资格
     */
    public static HashMap<String, String> createYiDouHalveAgo(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!"1".equals(jsonObject.getString("status"))) {
            return null;
        }
        HashMap<String, String> map = new HashMap<String, String>();

        if (!jsonObject.has("d") || null == jsonObject.getString("d") || "null".equals(jsonObject.getString("d"))
                || "".equals(jsonObject.getString("d"))) {
            map.put("d", "0");
        } else {
            map.put("d", jsonObject.optString("d", "0"));//过期时间
        }
        if (!jsonObject.has("now") || null == jsonObject.getString("now") || "null".equals(jsonObject.getString("now"))
                || "".equals(jsonObject.getString("now"))) {
            map.put("now", "0");
        } else {
            map.put("now", jsonObject.optString("now", "0"));
        }

        return map;
    }


    /**
     * 夺宝分享成功后的记录
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static final HashMap<String, Object> createIndianaShareRecord(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!"1".equals("" + jsonObject.getString("status"))) {
            return null;
        }
        if (!jsonObject.has("message") || null == jsonObject.getString("message")
                || "message".equals(jsonObject.getString("message")) || "".equals(jsonObject.getString("message"))) {
            mapRet.put("message", "");
        } else {
            mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
        }
        return mapRet;

    }

    /**
     * 夺宝分享相关参数
     *
     * @param context
     * @param result
     * @return
     * @throws Exception
     */
    public static final HashMap<String, Object> createIndianaShareData(Context context, String result) throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!"1".equals("" + jsonObject.getString("status"))) {
            return null;
        }
        if (!jsonObject.has("message") || null == jsonObject.getString("message")
                || "message".equals(jsonObject.getString("message")) || "".equals(jsonObject.getString("message"))) {
            mapRet.put("message", "");
        } else {
            mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
        }
        if (!jsonObject.has("sc") || null == jsonObject.getString("sc")
                || "sc".equals(jsonObject.getString("sc")) || "".equals(jsonObject.getString("sc"))) {//分享了多少次
            mapRet.put("sc", "0");
        } else {
            mapRet.put("sc", jsonObject.has("sc") ? jsonObject.getString("sc") : "0");
        }
        if (!jsonObject.has("oc") || null == jsonObject.getString("oc")
                || "oc".equals(jsonObject.getString("oc")) || "".equals(jsonObject.getString("oc"))) {//1分钱机会
            mapRet.put("oc", "0");
        } else {
            mapRet.put("oc", jsonObject.has("oc") ? jsonObject.getString("oc") : "0");
        }
        if (!jsonObject.has("scDay") || null == jsonObject.getString("scDay")
                || "scDay".equals(jsonObject.getString("scDay")) || "".equals(jsonObject.getString("scDay"))) {//当天分享的次数
            mapRet.put("scDay", "0");
        } else {
            mapRet.put("scDay", jsonObject.has("scDay") ? jsonObject.getString("scDay") : "0");
        }
        return mapRet;

    }

    /**
     * 获奖感言 列表
     */
    public static final HashMap<String, Object> createPraiseVoiceList(Context context, String result)
            throws Exception {
        HashMap<String, Object> mapRet = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!"1".equals("" + jsonObject.getString("status"))) {
            return null;
        }


        if (!jsonObject.has("message") || null == jsonObject.getString("message")
                || "".equals(jsonObject.getString("message")) || "".equals(jsonObject.getString("message"))) {
            mapRet.put("message", "");
        } else {
            mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
        }

        List<HashMap<String, String>> voice_list = new ArrayList<HashMap<String, String>>();

        if (!jsonObject.has("voice_list") || null == jsonObject.getString("voice_list")
                || "null".equals(jsonObject.getString("voice_list"))
                || "".equals(jsonObject.getString("voice_list"))) {
        } else {
            JSONArray ja = jsonObject.getJSONArray("voice_list");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
                // user_id用户id
                if (!jo.has("user_id") || null == jo.getString("user_id")
                        || "null".equals(jo.getString("user_id")) || "".equals(jo.getString("user_id"))) {
                    map.put("user_id", "");
                } else {
                    map.put("user_id", jo.has("user_id") ? jo.getString("user_id") : "");
                }

                // nickname 用户昵称
                if (!jo.has("nickname") || null == jo.getString("nickname")
                        || "null".equals(jo.getString("nickname")) || "".equals(jo.getString("nickname"))) {
                    map.put("nickname", "");
                } else {
                    map.put("nickname", jo.has("nickname") ? jo.getString("nickname") : "");
                }

                //location 位置
                if (!jo.has("location") || null == jo.getString("location")
                        || "null".equals(jo.getString("location")) || "".equals(jo.getString("location"))) {
                    map.put("location", "来自喵星");
                } else {
                    map.put("location", jo.has("location") ? jo.getString("location") : "来自喵星");
                }

                //pic头像
                if (!jo.has("pic") || null == jo.getString("pic")
                        || "null".equals(jo.getString("pic")) || "".equals(jo.getString("pic"))) {
                    map.put("pic", "");
                } else {
                    map.put("pic", jo.has("pic") ? jo.getString("pic") : "");
                }

                //audio 语音
                if (!jo.has("audio") || null == jo.getString("audio")
                        || "null".equals(jo.getString("audio")) || "".equals(jo.getString("audio"))) {
                    map.put("audio", "");
                } else {
                    map.put("audio", jo.has("audio") ? jo.getString("audio") : "");
                }

                //audio 语音时长
                if (!jo.has("audioLength") || null == jo.getString("audioLength")
                        || "null".equals(jo.getString("audioLength")) || "".equals(jo.getString("audioLength"))) {
                    map.put("audioLength", "00:00:00");
                } else {
                    map.put("audioLength", jo.has("audioLength") ? jo.getString("audioLength") : "00:00:00");
                }

                //ranking 名次
                if (!jo.has("ranking") || null == jo.getString("ranking")
                        || "null".equals(jo.getString("ranking")) || "".equals(jo.getString("ranking"))) {
                    map.put("ranking", "");
                } else {
                    map.put("ranking", jo.has("ranking") ? jo.getString("ranking") : "");
                }

                //content 内容
                if (!jo.has("content") || null == jo.getString("content")
                        || "null".equals(jo.getString("content")) || "".equals(jo.getString("content"))) {
                    map.put("content", "");
                } else {
                    map.put("content", jo.has("content") ? jo.getString("content") : "");
                }

                //期数
                if (!jo.has("period") || null == jo.getString("period")
                        || "null".equals(jo.getString("period")) || "".equals(jo.getString("period"))) {
                    map.put("period", "01");
                } else {
                    map.put("period", jo.has("period") ? jo.getString("period") : "01");
                }

                voice_list.add(map);
            }
        }
        mapRet.put("voice_list", voice_list);
        return mapRet;

    }


    /**
     * 集赞分享 获取文案
     */
    public static List<String> createIndinaRuleContent(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        List<String> list = new ArrayList<String>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!"1".equals(jsonObject.getString("status"))) {
            return null;
        }
        if (!jsonObject.has("dbgz") || null == jsonObject.getString("dbgz") || "".equals(jsonObject.getString("dbgz"))
                || "null".equals(jsonObject.getString("dbgz"))) {
            return null;
        }

        String joString = jsonObject.getString("dbgz");
        JSONObject jo = new JSONObject(joString);
        if (null == jo || "".equals(jo)) {
            return null;
        }
        for (int i = 0; i < jo.length(); i++) {
            if (!jo.has("t" + i) || null == jo.getString("t" + i) || "null".equals(jo.getString("t" + i)) || "".equals(jo.getString("t" + i))) {
            } else {
                list.add(jo.getString("t" + i));
            }
        }

        return list;
    }

    /**
     * 获取拼团详情
     */
    public static final List<HashMap<String, String>> createGroupDetaisList(Context context, String result)
            throws JSONException {
        List<HashMap<String, String>> list = new ArrayList<>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return list;
        }
        if (!"1".equals(j.getString("status"))) {
            return list;
        }
        JSONArray jsonArray = new JSONArray(j.getString("data"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return list;
        }


        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.get(i);
            HashMap<String, String> mapObject = new HashMap<String, String>();
            if (!j.has("now") || null == j.getString("now") || "null".equals(j.getString("now"))//now在第一层 放在每一条数据里面
                    || "".equals(j.getString("now"))) {
                mapObject.put("now", "0");
            } else {
                mapObject.put("now", j.has("now") ? j.getString("now") : "0");
            }

            //当前登录的用户是否已经支付过该拼团.
            if (!j.has("is_pay") || null == j.getString("is_pay") || "null".equals(j.getString("is_pay"))//is_pay 放在每一条数据里面
                    || "".equals(j.getString("is_pay"))) {
                mapObject.put("is_pay", "0");
            } else {
                mapObject.put("is_pay", j.has("is_pay") ? j.getString("is_pay") : "0");
            }

            if (!jo.has("r_code") || null == jo.getString("r_code")
                    || "null".equals(jo.getString("r_code")) || "".equals(jo.getString("r_code"))) {
                mapObject.put("r_code", "");
            } else {
                mapObject.put("r_code", jo.has("r_code") ? jo.getString("r_code") : "");
            }
            if (!jo.has("add_time") || null == jo.getString("add_time") || "null".equals(jo.getString("add_time"))
                    || "".equals(jo.getString("add_time"))) {
                mapObject.put("add_time", "0");
            } else {
                mapObject.put("add_time", jo.has("add_time") ? jo.getString("add_time") : "0");
            }

            if (!jo.has("elide_price") || null == jo.getString("elide_price")
                    || "null".equals(jo.getString("elide_price")) || "".equals(jo.getString("elide_price"))) {
                mapObject.put("elide_price", "0^0");
            } else {
                mapObject.put("elide_price", jo.has("elide_price") ? jo.getString("elide_price") : "0^0");// 商品原价
            }
            if (!jo.has("shop_roll") || null == jo.getString("shop_roll") || "null".equals(jo.getString("shop_roll"))
                    || "".equals(jo.getString("shop_roll"))) {
                mapObject.put("shop_roll", "0");
            } else {
                mapObject.put("shop_roll", jo.has("shop_roll") ? jo.getString("shop_roll") : "0");
            }

            if (!jo.has("user_name") || null == jo.getString("user_name")
                    || "".equals(jo.getString("user_name"))) {
                mapObject.put("user_name", "");
            } else {
                mapObject.put("user_name", jo.has("user_name") ? jo.getString("user_name") : "");
            }

            String user_id = "";
            if (!jo.has("user_id") || null == jo.getString("user_id") || "null".equals(jo.getString("user_id"))
                    || "".equals(jo.getString("user_id"))) {
                mapObject.put("user_id", "");
            } else {
                user_id = jo.has("user_id") ? jo.getString("user_id") : "";
                mapObject.put("user_id", user_id);
            }

            if (!jo.has("order_code") || null == jo.getString("order_code") || "null".equals(jo.getString("order_code"))
                    || "".equals(jo.getString("order_code"))) {
                mapObject.put("order_code", "");
            } else {
                mapObject.put("order_code", jo.has("order_code") ? jo.getString("order_code") : "");
            }
            if (!jo.has("status") || null == jo.getString("status") || "null".equals(jo.getString("status"))
                    || "".equals(jo.getString("status"))) {
                mapObject.put("status", "");
            } else {
                mapObject.put("status", jo.has("status") ? jo.getString("status") : "");
            }

            if (!jo.has("user_portrait") || null == jo.getString("user_portrait") || "null".equals(jo.getString("user_portrait"))
                    || "".equals(jo.getString("user_portrait"))) {
                mapObject.put("user_portrait", "");
            } else {
                mapObject.put("user_portrait", jo.has("user_portrait") ? jo.getString("user_portrait") : "");
            }

            if (!jo.has("shop_name") || null == jo.getString("shop_name") || "null".equals(jo.getString("shop_name"))
                    || "".equals(jo.getString("shop_name"))) {
                mapObject.put("shop_name", ",");
            } else {
                mapObject.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : ",");// 商品名称
            }

            if (!jo.has("shop_url") || null == jo.getString("shop_url") || "null".equals(jo.getString("shop_url"))
                    || "".equals(jo.getString("shop_url"))) {
                mapObject.put("shop_url", ",");
            } else {
                mapObject.put("shop_url", jo.has("shop_url") ? jo.getString("shop_url") : ",");
            }

            if (!jo.has("shop_code") || null == jo.getString("shop_code") || "null".equals(jo.getString("shop_code"))
                    || "".equals(jo.getString("shop_code"))) {
                mapObject.put("shop_code", ",");
            } else {
                mapObject.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : ",");
            }

            if (!jo.has("type") || null == jo.getString("type") || "null".equals(jo.getString("type"))
                    || "".equals(jo.getString("type"))) {
                mapObject.put("type", "");
            } else {
                mapObject.put("type", jo.has("type") ? jo.getString("type") : "");
            }

            if (!jo.has("color") || null == jo.getString("color") || "null".equals(jo.getString("color"))
                    || "".equals(jo.getString("color"))) {
                mapObject.put("color", "^");
            } else {
                mapObject.put("color", jo.has("color") ? jo.getString("color") : "^");
            }
            if (!jo.has("size") || null == jo.getString("size") || "null".equals(jo.getString("size"))
                    || "".equals(jo.getString("size"))) {
                mapObject.put("size", "^");
            } else {
                mapObject.put("size", jo.has("size") ? jo.getString("size") : "^");
            }
            if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price") || "null".equals(jo.getString("shop_se_price"))
                    || "".equals(jo.getString("shop_se_price"))) {
                mapObject.put("shop_se_price", "0");
            } else {
                mapObject.put("shop_se_price", jo.has("shop_se_price") ? jo.getString("shop_se_price") : "0");
            }

            if (!jo.has("shop_price") || null == jo.getString("shop_price") || "null".equals(jo.getString("shop_price"))
                    || "".equals(jo.getString("shop_price"))) {
                mapObject.put("shop_price", "0");
            } else {
                mapObject.put("shop_price", jo.has("shop_price") ? jo.getString("shop_price") : "0");
            }


            if (!jo.has("p_price") || null == jo.getString("p_price") || "null".equals(jo.getString("p_price"))
                    || "".equals(jo.getString("p_price"))) {
                mapObject.put("p_price", "0^0");
            } else {
                mapObject.put("p_price", jo.has("p_price") ? jo.getString("p_price") : "0^0");
            }
            //0 人数没满 1人满了等付款
            if (!jo.has("n_status") || null == jo.getString("n_status") || "null".equals(jo.getString("n_status"))
                    || "".equals(jo.getString("n_status"))) {
                mapObject.put("n_status", "0");
            } else {
                mapObject.put("n_status", jo.has("n_status") ? jo.getString("n_status") : "0");
            }
            //付款结束时间 n_status=1 才有值.
            if (!jo.has("pay_end_time") || null == jo.getString("pay_end_time") || "null".equals(jo.getString("pay_end_time"))
                    || "".equals(jo.getString("pay_end_time"))) {
                mapObject.put("pay_end_time", "0");
            } else {
                mapObject.put("pay_end_time", jo.has("pay_end_time") ? jo.getString("pay_end_time") : "0");
            }

            //0代表未付款
            if (!jo.has("noPay") || null == jo.getString("noPay") || "null".equals(jo.getString("noPay"))
                    || "".equals(jo.getString("noPay"))) {
                mapObject.put("noPay", "1");
            } else {
                mapObject.put("noPay", jo.has("noPay") ? jo.getString("noPay") : "1");
            }


            list.add(mapObject);
        }
        return list;
    }


    /**
     * 拼团文案
     */
    public static HashMap<String, String> createGroupContent(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        HashMap<String, String> map = new HashMap<String, String>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!"1".equals(jsonObject.getString("status"))) {
            return null;
        }

        if (!jsonObject.has("ptgwn") || "".equals(jsonObject.getString("ptgwn")) || "null".equals(jsonObject.getString("ptgwn"))) {
            return null;
        }
        String joString = jsonObject.getString("ptgwn");
        JSONObject jo = new JSONObject(joString);
        if (null == jo || "".equals(jo)) {
            return null;
        }

        if (!jo.has("n1") || null == jo.getString("n1") || "null".equals(jo.getString("n1"))) {
            map.put("n1", "任选2件");
        } else {
            map.put("n1", jo.optString("n1", "任选2件"));
        }
        if (!jo.has("n2") || null == jo.getString("n2") || "null".equals(jo.getString("n2"))
                || "".equals(jo.getString("n2"))) {
            map.put("n2", "9块9包邮");
        } else {
            map.put("n2", jo.optString("n2", "9块9包邮"));
        }

        if (!jo.has("n3") || null == jo.getString("n3") || "null".equals(jo.getString("n3"))
                || "".equals(jo.getString("n3"))) {
            map.put("n3", "两件9块9包邮");
        } else {
            map.put("n3", jo.optString("n3", "两件9块9包邮"));
        }

        if (!jo.has("n4") || null == jo.getString("n4") || "null".equals(jo.getString("n4"))
                || "".equals(jo.getString("n4"))) {
            map.put("n4", "100款活动商品任选两件");
        } else {
            map.put("n4", jo.optString("n4", "100款活动商品任选两件"));
        }

        return map;
    }


    /**
     * 拼团初始化数据
     */
    public static final HashMap<String, String> createGroupinitData(Context context, String result)
            throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j) || "null".equals(j)) {
            return null;
        }
        if (!"1".equals(j.getString("status"))) {
            return null;
        }
        if (!j.has("data") || "".equals(j.getString("data")) || "null".equals(j.getString("data")) || null == j.getString("data")) {
            return null;
        }
        JSONObject j2 = new JSONObject(j.getString("data"));
        if (!j2.has("rnum") || "".equals(j2.getString("rnum")) || "null".equals(j2.getString("rnum")) || null == j2.getString("rnum")) {
            map.put("rnum", "10.0");
        } else {
            map.put("rnum", j2.getString("rnum"));
        }
        if (!j2.has("DPPAYPRICE") || "".equals(j2.getString("DPPAYPRICE")) || "null".equals(j2.getString("DPPAYPRICE")) || null == j2.getString("DPPAYPRICE")) {
            map.put("DPPAYPRICE", "9.9");
        } else {
            map.put("DPPAYPRICE", j2.getString("DPPAYPRICE"));
        }
        if (!j2.has("validHour") || "".equals(j2.getString("validHour")) || "null".equals(j2.getString("validHour")) || null == j2.getString("validHour")) {
            map.put("validHour", "12");
        } else {
            map.put("validHour", j2.getString("validHour"));
        }
        //剩余时间
        if (!j2.has("validMin") || "".equals(j2.getString("validMin")) || "null".equals(j2.getString("validMin")) || null == j2.getString("validMin")) {
            map.put("validMin", 12 * 60 * 0 * 1000 + "");
        } else {
            map.put("validMin", j2.getString("validMin"));
        }
        return map;
    }

    /**
     * 夺宝商品列表
     */
    ////////
    public static final List<HashMap<String, Object>> createIndianaShopList(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        // System.out.println("这是强制浏览的result=" + result);
        if (null == j || "".equals(j)) {
            return list;
        }
        if (!"1".equals(j.getString("status"))) {
            return list;
        }
        JSONArray jsonArray = new JSONArray(j.getString("data"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return list;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.get(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();

            if (!jo.has("shop_code") || null == jo.getString("shop_code") || "null".equals(jo.getString("shop_code"))
                    || "".equals(jo.getString("shop_code"))) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");
            }
            if (!jo.has("shop_name") || null == jo.getString("shop_name") || "null".equals(jo.getString("shop_name"))
                    || "".equals(jo.getString("shop_name"))) {
                mapObject.put("shop_name", "");
            } else {
                mapObject.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");
            }

            if (!jo.has("def_pic") || null == jo.getString("def_pic") || "null".equals(jo.getString("def_pic"))
                    || "".equals(jo.getString("def_pic"))) {
                mapObject.put("def_pic", "");
            } else {
                mapObject.put("def_pic", jo.has("def_pic") ? jo.getString("def_pic") : "");
            }

            if (!jo.has("active_people_num") || null == jo.getString("active_people_num") || "null".equals(jo.getString("active_people_num"))
                    || "".equals(jo.getString("active_people_num"))) {
                mapObject.put("active_people_num", "1");
            } else {
                mapObject.put("active_people_num", jo.has("active_people_num") ? jo.getString("active_people_num") : "1");
            }
            if (!jo.has("involved_people_num") || null == jo.getString("involved_people_num") || "null".equals(jo.getString("involved_people_num"))
                    || "".equals(jo.getString("involved_people_num"))) {
                mapObject.put("involved_people_num", "1");
            } else {
                mapObject.put("involved_people_num", jo.has("involved_people_num") ? jo.getString("involved_people_num") : "1");
            }
            if (!jo.has("shop_price") || null == jo.getString("shop_price") || "null".equals(jo.getString("shop_price"))
                    || "".equals(jo.getString("shop_price"))) {
                mapObject.put("shop_price", "0.00");
            } else {
                mapObject.put("shop_price", jo.has("shop_price") ? jo.getString("shop_price") : "0.00");
            }
            if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price") || "null".equals(jo.getString("shop_se_price"))
                    || "".equals(jo.getString("shop_se_price"))) {
                mapObject.put("shop_se_price", "0.00");
            } else {
                mapObject.put("shop_se_price", jo.has("shop_se_price") ? jo.getString("shop_se_price") : "0.00");
            }


            list.add(mapObject);
        }
        return list;
    }


    /**
     * 夺宝商品列表
     */
    public static final List<String> createIndianaDialogMessage(Context context, String result)
            throws JSONException {
        List<String> list = new ArrayList<String>();
        JSONObject j = new JSONObject(result);
        // System.out.println("这是强制浏览的result=" + result);
        if (null == j || "".equals(j)) {
            return list;
        }
        if (!"1".equals(j.getString("status"))) {
            return list;
        }
        if (!j.has("data") || j.getString("data") == null || "null".equals(j.getString("data"))) {
            return list;
        }
        JSONArray jsonArray = new JSONArray(j.getString("data"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return list;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            String str = jsonArray.getString(i);

            list.add(str);
        }
        return list;
    }


    /**
     * 1元购开启状态
     */
    public static final OneBuyStaus creatOnYuanStatus(String result)
            throws JSONException {

//        HashMap<String, String> map = new HashMap<>();
//
//        JSONObject j = new JSONObject(result);
//        if (null != j.optString("status") && "1".equals(j.optString("status"))) {
//            JSONArray ja = j.getJSONArray("data");
//            if (null != ja && ja.length() > 0) {
//                for (int i = 0; i < ja.length(); i++) {
//
//                    JSONObject jo = (JSONObject) ja.get(i);
//
//                    if (!jo.has("app_status") || "".equals(jo.getString("app_status")) || "null".equals(jo.getString("app_status")) || null == jo.getString("app_status")) {
//                        map.put("app_status", "");
//                    } else {
//                        map.put("app_status", jo.getString("app_status"));
//                    }
//
//                    if (!jo.has("app_value") || "".equals(jo.getString("app_value")) || "null".equals(jo.getString("app_value")) || null == jo.getString("app_value")) {
//                        map.put("app_value", "1.0");
//                    } else {
//                        map.put("app_value", jo.getString("app_value"));
//                    }
//
//                }
//            }
//        }
//        Log.e("启动检查","1元购解析");
//        Log.e("启动检查","1元购解析-"+JSON.parseObject(result, OneBuyStaus.class).toString());

        return JSON.parseObject(result, OneBuyStaus.class);
    }


    public static final String createAllDikou(String result)
            throws JSONException {
        JSONObject jo = new JSONObject(result);
        if (null != jo.optString("status") && "1".equals(jo.optString("status"))) {
            if (!jo.has("order_price") || "".equals(jo.getString("order_price")) || "null".equals(jo.getString("order_price")) || null == jo.getString("order_price")) {
                return "0.0";
            } else {
                return jo.getString("order_price");
            }

        }
        return "0.0";
    }


    public static final HashMap<String, String> createAllDikouTongzhi(String result)
            throws JSONException {
        HashMap<String, String> map = new HashMap<>();
        JSONObject jo = new JSONObject(result);
        if (null != jo.optString("status") && "1".equals(jo.optString("status"))) {
            if (!jo.has("order_price") || "".equals(jo.getString("order_price")) || "null".equals(jo.getString("order_price")) || null == jo.getString("order_price")) {
                map.put("order_price", "0.0");
            } else {
                map.put("order_price", jo.optString("order_price"));
            }

            if (!jo.has("isFail") || "".equals(jo.getString("isFail")) || "null".equals(jo.getString("isFail")) || null == jo.getString("isFail")) {
                map.put("isFail", "0");
            } else {
                map.put("isFail", jo.optString("isFail"));
            }

        } else {
            return null;
        }
        return map;
    }

    public static final String createAllDikouKeyong(String result)
            throws JSONException {
        JSONObject jo = new JSONObject(result);
        if (null != jo.optString("status") && "1".equals(jo.optString("status"))) {
            if (!jo.has("one_not_use_price") || "".equals(jo.getString("one_not_use_price")) || "null".equals(jo.getString("one_not_use_price")) || null == jo.getString("one_not_use_price")) {
                return "0.0";
            } else {
                return jo.getString("one_not_use_price");
            }

        }
        return "0.0";
    }

}
