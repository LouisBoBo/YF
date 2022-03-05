package com.yssj.utils;

import android.content.Context;

import com.yssj.YConstance;
import com.yssj.entity.DuoBaoJiLu_user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityFactoryQingfeng {

    public static HashMap<String, String> createMessageSystem(Context context, String result) throws JSONException {
        /**
         * 1.订单消息 2.好友消息 3.账户消息 4.衣豆消息
         */
        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONObject jData = new JSONObject(j.optString("data"));
        if (null == jData || "".equals(jData)) {
            return null;
        }
        if (!jData.has("1") || null == jData.getString("1") || jData.getString("1").equals("null")) {
            map.put("1", "");
        } else {

            map.put("1", jData.has("1") ? jData.optString("1") : "");
        }

        if (!jData.has("2") || null == jData.getString("2") || jData.getString("2").equals("null")) {
            map.put("2", "");
        } else {

            map.put("2", jData.has("2") ? jData.optString("2") : "");
        }

        if (!jData.has("3") || null == jData.getString("3") || jData.getString("3").equals("null")) {
            map.put("3", "");
        } else {

            map.put("3", jData.has("3") ? jData.optString("3") : "");
        }

        if (!jData.has("4") || null == jData.getString("4") || jData.getString("4").equals("null")) {
            map.put("4", "");
        } else {

            map.put("4", jData.has("4") ? jData.optString("4") : "");
        }
        if (!jData.has("5") || null == jData.getString("5") || jData.getString("5").equals("null")) {
            map.put("5", "");
        } else {

            map.put("5", jData.has("5") ? jData.optString("5") : "");
        }
        return map;

    }

    /**
     * 精选推荐--分享列表
     */
    public static final ArrayList<HashMap<String, String>> createJingxuanList(Context context, String result)
            throws JSONException {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(j.getString("list"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.get(i);
            HashMap<String, String> mapObject = new HashMap<String, String>();
            if (!jo.has("shop_code") || null == jo.getString("shop_code") || "null".equals(jo.getString("shop_code"))
                    || "".equals(jo.getString("shop_code"))) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");
            }

            if (!jo.has("show_pic") || null == jo.getString("show_pic") || "null".equals(jo.getString("show_pic"))
                    || "".equals(jo.getString("show_pic"))) {
                mapObject.put("show_pic", "0");
            } else {
                mapObject.put("show_pic", jo.has("show_pic") ? jo.getString("show_pic") : "0");
            }

            list.add(mapObject);
        }
        return list;
    }

    /**
     * 好友密友列表
     */
    public static final List<HashMap<String, Object>> createMyFrendsList(Context context, String result)
            throws JSONException {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(j.getString("list"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.get(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();
            // 用户ID
            if (!jo.has("user_id") || null == jo.getString("user_id") || "null".equals(jo.getString("user_id"))
                    || "".equals(jo.getString("user_id"))) {
                mapObject.put("user_id", "");
            } else {
                mapObject.put("user_id", jo.has("user_id") ? jo.getString("user_id") : "");
            }
            // 用户昵称
            if (!jo.has("nickname") || null == jo.getString("nickname") || "null".equals(jo.getString("nickname"))
                    || "".equals(jo.getString("nickname"))) {
                mapObject.put("nickname", "0");
            } else {
                mapObject.put("nickname", jo.has("nickname") ? jo.getString("nickname") : "0");
            }
            // 用户头像
            if (!jo.has("pic") || null == jo.getString("pic") || "null".equals(jo.getString("pic"))
                    || "".equals(jo.getString("nickname"))) {
                mapObject.put("pic", "0");
            } else {
                mapObject.put("pic", jo.has("pic") ? jo.getString("pic") : "0");
            }

            list.add(mapObject);

        }
        return list;
    }

    /***
     * 个人中心----照片
     */
    public static final List<HashMap<String, Object>> createMyPic(Context context, String result) throws Exception {
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
        LogYiFu.e("Kr", result + "");
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        JSONArray ja = jsonObject.getJSONArray("data");
        for (int i = 0; i < ja.length(); i++) {
            String js = ja.getString(i);
            JSONObject jo = new JSONObject(js);
            HashMap<String, Object> map = new HashMap<String, Object>();

            if (!jo.has("pic") || null == jo.getString("pic")) {
                map.put("pic", "");
            } else {
                map.put("pic", jo.optString("pic"));
            }
            if (!jo.has("theme_id") || null == jo.getString("theme_id")) {
                map.put("theme_id", "");
            } else {
                map.put("theme_id", jo.optString("theme_id"));
            }
            if (!jo.has("title") || null == jo.getString("title")) {
                map.put("title", "");
            } else {
                map.put("title", jo.optString("title"));
            }
            if (!jo.has("content") || null == jo.getString("content")) {
                map.put("content", "");
            } else {
                map.put("content", jo.optString("content"));
            }
            if (!jo.has("user_id") || null == jo.getString("user_id")) {
                map.put("user_id", "");
            } else {
                map.put("user_id", jo.optString("user_id"));
            }
            // 1 精选推荐，2 穿搭，3 普通话题
            if (!jo.has("theme_type") || null == jo.getString("theme_type")) {
                map.put("theme_type", "");
            } else {
                map.put("theme_type", jo.optString("theme_type"));
            }
            if (jo.optString("theme_type").equals("1")) {
                List<HashMap<String, Object>> shop_list = new ArrayList<HashMap<String, Object>>();
                HashMap<String, Object> mapObjectShopList = new HashMap<String, Object>();
                JSONObject jk = new JSONObject(jo.getString("shop_list"));
                if (!jk.has("shop_code") || null == jk.getString("shop_code") || jk.getString("shop_code").equals("")) {
                    mapObjectShopList.put("shop_code", "");
                } else {
                    mapObjectShopList.put("shop_code", jk.has("shop_code") ? jk.optString("shop_code") : "");
                }
                if (!jk.has("def_pic") || null == jk.getString("def_pic") || jk.getString("def_pic").equals("")) {
                    mapObjectShopList.put("def_pic", "");
                } else {
                    mapObjectShopList.put("def_pic", jk.has("def_pic") ? jk.optString("def_pic") : "");
                }
                if (!jk.has("shop_se_price") || null == jk.getString("shop_se_price")
                        || "null".equals(jk.getString("shop_se_price")) || jk.getString("shop_se_price").equals("")) {
                    mapObjectShopList.put("shop_se_price", "0");
                } else {
                    mapObjectShopList.put("shop_se_price",
                            jk.has("shop_se_price") ? jk.optString("shop_se_price") : "0");
                }
                if (!jk.has("shop_status") || null == jk.getString("shop_status")
                        || "null".equals(jk.getString("shop_status")) || jk.getString("shop_status").equals("")) {
                    mapObjectShopList.put("shop_status", "100");
                } else {
                    mapObjectShopList.put("shop_status", jk.has("shop_status") ? jk.optString("shop_status") : "100");
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
                if (!jk.has("supp_label") || null == jk.getString("supp_label")
                        || jk.getString("supp_label").equals("")) {
                    mapObjectShopList.put("supp_label", "");
                } else {
                    mapObjectShopList.put("supp_label", jk.has("supp_label") ? jk.optString("supp_label") : "");
                }
                shop_list.add(mapObjectShopList);
                map.put("shop_list", shop_list);
            }

            retInfo.add(map);
        }
        return retInfo;
    }

    public static final List<HashMap<String, Object>> createTiXianEduLiebiao(Context context, String result)
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
             * type:分类 1抽红包增加 2 夺宝退款 3粉丝购物 4官方赠送 5余额提现 6官方减少
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

    public static boolean createHasMon(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return false;
        }

//---------------------------------------------------------------------------------------------------------------
        //是否有每月惊喜任务

        if (!j.has("monthly") || null == j.getString("monthly") || "null".equals(j.getString("monthly"))
                || "".equals(j.getString("monthly"))) {
            SharedPreferencesUtil.saveBooleanData(context, YConstance.Pref.HASMEIYUEJINGXI, false);

        } else {
            boolean is0 = false;
            try {
                if ("1".equals(j.getString("monthly"))) {
                    is0 = true;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            SharedPreferencesUtil.saveBooleanData(context, YConstance.Pref.HASMEIYUEJINGXI, is0);
        }

//---------------------------------------------------------------------------------------------------------------
        //是否有千元红包
        if (!j.has("red_rain") || null == j.getString("red_rain") || "null".equals(j.getString("red_rain"))
                || "".equals(j.getString("red_rain"))) {
            SharedPreferencesUtil.saveBooleanData(context, YConstance.Pref.HAISQIANYAUNHBONGBAO_SIGN, false);

        } else {
            boolean is0 = false;
            try {
                if ("1".equals(j.getString("red_rain"))) {
                    is0 = true;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            SharedPreferencesUtil.saveBooleanData(context, YConstance.Pref.HAISQIANYAUNHBONGBAO_SIGN, is0);
        }


//---------------------------------------------------------------------------------------------------------------
        //是否有提成任务
        SharedPreferencesUtil.saveBooleanData(context, YConstance.Pref.FRIENDTICHENGPAGE, false);
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
            SharedPreferencesUtil.saveBooleanData(context, YConstance.Pref.FRIENDTICHENGPAGE, true);

        } else {
            if (!j.has("fri_win") || null == j.getString("fri_win") || "null".equals(j.getString("fri_win"))
                    || "".equals(j.getString("fri_win"))) {

            } else {
                try {
                    if ("1".equals(j.getString("fri_win"))) {
                        SharedPreferencesUtil.saveBooleanData(context, YConstance.Pref.FRIENDTICHENGPAGE, true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
//---------------------------------------------------------------------------------------------------------------
        //是否有星期一

        if (!j.has("isMonday") || null == j.getString("isMonday") || j.getString("isMonday").equals("isMonday")) {
            return false;
        } else {
            String isMonday = j.getString("isMonday");
            if (isMonday.equals("true")) {
                return true;
            } else {
                return false;
            }

        }

    }


    public static boolean createLingyuangou(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return false;
        }
        if (!j.has("zero_buy") || null == j.getString("zero_buy") || j.getString("zero_buy").equals("zero_buy")) {
            return false;
        } else {
            String isMonday = j.getString("zero_buy");
            if (isMonday.equals("1")) {
                return true;
            } else {
                return false;
            }

        }

    }

    /**
     * 点赞
     * data 1为首页 2为赚钱
     *
     * @param context
     * @param result
     * @return 返回 true:首页；false:赚钱
     * @throws JSONException
     */
    public static boolean createDianZan(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return true;
        }
        if (!j.has("data") || null == j.getString("data") || j.getString("data").equals("data")) {
            return true;
        } else {
            String data = j.getString("data");
            if (data.equals("1")) { //首页
                return false;
            } else if (data.equals("2")) {
                return true;
            }
            return true;

        }


    }


    /**
     * 是否隐藏赚钱   data //      0不隐藏 1隐藏
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static boolean createHasSign(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return true;
        }
        if (!j.has("data") || null == j.getString("data") || j.getString("data").equals("data")) {
            return true;
        } else {
            String data = j.getString("data");

            if (data.equals("1")) { //隐藏
                return false;
            } else if (data.equals("0")) {//不隐藏
                return true;
            }
            return true;

        }


    }

    public static boolean createOneBuyzhongjiangStatus(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return false;
        }
        if (!j.has("OrNotPrize") || null == j.getString("OrNotPrize") || j.getString("OrNotPrize").equals("OrNotPrize")) {
            return false;
        } else {
            String data = j.getString("OrNotPrize");

            if (data.equals("0")) {
                return true;
            } else if (data.equals("0")) {
                return false;
            }
            return false;

        }


    }


    /**
     * 获取当前是否有未付款的订单
     *
     * @param context
     * @param result
     * @return
     * @throws JSONException
     */
    public static boolean ceaterNotfuOrder(Context context, String result) throws JSONException {

        JSONObject jo = new JSONObject(result);
        if (null == jo || "".equals(jo)) {
            return false;
        }

        if (!jo.has("status") || null == jo.getString("status") || jo.getString("status").equals("status")) {
            return false;
        } else {
            if (null == jo.getString("status") || !"1".equals(jo.getString("status"))) {
                return false;
            }

            if (!jo.has("s") || null == jo.getString("s") || jo.getString("s").equals("null") || "0".equals(jo.getString("s"))) {
                return false;
            } else {
                return true;
            }
        }


    }


    /**
     * 获取分享赢提现额度说明文案
     *
     * @return
     * @throws JSONException
     */
    public static List<String> createColorTextContent(JSONObject jj) throws JSONException {
        List<String> list = new ArrayList<String>();
        if (null == jj || "".equals(jj)) {
            return null;
        }


        if (!jj.has("fxtx") || null == jj.getString("fxtx") || "".equals(jj.getString("fxtx"))
                || "null".equals(jj.getString("fxtx"))) {
            return null;
        }

        JSONObject joj = jj.getJSONObject("fxtx");

        if (null == joj || "".equals(joj)) {
            return null;
        }


        if (!joj.has("text") || null == joj.getString("text") || "".equals(joj.getString("text"))
                || "null".equals(joj.getString("text"))) {
            return null;
        }

        String joString = joj.getString("text");
        JSONObject jo = new JSONObject(joString);


        for (int i = 0; i < jo.length(); i++) {
            if (!jo.has("t" + i) || null == jo.getString("t" + i) || "null".equals(jo.getString("t" + i)) || "".equals(jo.getString("t" + i))) {
            } else {
                list.add(jo.getString("t" + i));
            }
        }
        return list;
    }


    public static String createRandRoll(Context context, String result) throws JSONException {


        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }


        if (!j.has("data") || null == j.getString("data") || "".equals(j.getString("data"))
                || "null".equals(j.getString("data"))) {
            return "";
        } else {

            return j.get("data") + "";
        }


    }


    /**
     * 夺宝 往期揭晓——往期揭晓(往期揭晓左边的)  -----拼团夺宝往期揭晓
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

            if (!jo.has("id") || null == jo.getString("id") || "null".equals(jo.getString("id"))
                    || "".equals(jo.getString("id"))) {
                mapObject.put("id", "");
            } else {
                mapObject.put("id", jo.has("id") ? jo.getString("id") : "");
            }
            if (!jo.has("in_sum") || null == jo.getString("in_sum") || "null".equals(jo.getString("in_sum"))
                    || "".equals(jo.getString("in_sum"))) {
                mapObject.put("in_sum", "");
            } else {
                mapObject.put("in_sum", jo.has("in_sum") ? jo.getString("in_sum") : "");
            }

            if (!jo.has("in_rollUserName") || null == jo.getString("in_rollUserName") || "null".equals(jo.getString("in_rollUserName"))
                    || "".equals(jo.getString("in_rollUserName"))) {
                mapObject.put("in_rollUserName", "");
            } else {
                mapObject.put("in_rollUserName", jo.has("in_rollUserName") ? jo.getString("in_rollUserName") : "");
            }


            if (!jo.has("r_code") || null == jo.getString("r_code") || "null".equals(jo.getString("r_code"))
                    || "".equals(jo.getString("r_code"))) {
                mapObject.put("r_code", "");
            } else {
                mapObject.put("r_code", jo.has("r_code") ? jo.getString("r_code") : "");
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
//            if (!jo.has("otime") || null == jo.getString("otime") || "null".equals(jo.getString("otime"))
//                    || "".equals(jo.getString("otime"))) {
//                mapObject.put("otime", 0L);
//            } else {
//                mapObject.put("otime", jo.has("otime") ? jo.getLong("otime") : 0L);
//            }
//            if (!jo.has("btime") || null == jo.getString("btime") || "null".equals(jo.getString("btime"))
//                    || "".equals(jo.getString("btime"))) {
//                mapObject.put("btime", 0L);
//            } else {
//                mapObject.put("btime", jo.has("btime") ? jo.getLong("btime") : 0L);
//            }
            if (!jo.has("etime") || null == jo.getString("etime") || "null".equals(jo.getString("etime"))
                    || "".equals(jo.getString("etime"))) {
                mapObject.put("etime", 0L);
            } else {
                mapObject.put("etime", jo.has("etime") ? jo.getLong("etime") : 0L);
            }
//            if (!jo.has("num") || null == jo.getString("num") || "null".equals(jo.getString("num"))
//                    || "".equals(jo.getString("num"))) {
//                mapObject.put("num", "0");
//            } else {
//                mapObject.put("num", jo.has("num") ? jo.getInt("num") : "0");
//            }
//            if (!jo.has("virtual_num") || null == jo.getString("virtual_num")
//                    || "null".equals(jo.getString("virtual_num")) || "".equals(jo.getString("virtual_num"))) {
//                mapObject.put("virtual_num", "0");
//            } else {
//                mapObject.put("virtual_num", jo.has("virtual_num") ? jo.getInt("virtual_num") : "0");
//            }
//            if (!jo.has("in_name") || null == jo.getString("in_name") || "".equals(jo.getString("in_name"))) {
//                mapObject.put("in_name", "");
//            } else {
//                mapObject.put("in_name", jo.has("in_name") ? jo.getString("in_name") : "");
//            }
//            if (!jo.has("in_head") || null == jo.getString("in_head") || "null".equals(jo.getString("in_head"))
//                    || "".equals(jo.getString("in_head"))) {
//                mapObject.put("in_head", "");
//            } else {
//                mapObject.put("in_head", jo.has("in_head") ? jo.getString("in_head") : "");
//            }
            if (!jo.has("issue_code") || null == jo.getString("issue_code") || "null".equals(jo.getString("issue_code"))
                    || "".equals(jo.getString("issue_code"))) {
                mapObject.put("issue_code", "");
            } else {
                mapObject.put("issue_code", jo.has("issue_code") ? jo.getString("issue_code") : "");
            }
//            if (!jo.has("in_uid") || null == jo.getString("in_uid") || "null".equals(jo.getString("in_uid"))
//                    || "".equals(jo.getString("in_uid"))) {
//                mapObject.put("in_uid", "");
//            } else {
//                mapObject.put("in_uid", jo.has("in_uid") ? jo.getInt("in_uid") : "");
//            }


            if (!jo.has("in_rollHead") || null == jo.getString("in_rollHead") || "null".equals(jo.getString("in_rollHead"))
                    || "".equals(jo.getString("in_rollHead"))) {
                mapObject.put("in_rollHead", "");
            } else {


                mapObject.put("in_rollHead", jo.optString("in_rollHead"));
            }


            if (!jo.has("in_userId") || null == jo.getString("in_userId") || "null".equals(jo.getString("in_userId"))
                    || "".equals(jo.getString("in_userId"))) {
                mapObject.put("in_userId", "");
            } else {
                mapObject.put("in_userId", jo.has("in_userId") ? jo.getInt("in_userId") : "");
            }
            mapRet.add(mapObject);
        }
        return mapRet;

    }


    /**
     * 夺宝记录——我的参与记录----拼团夺宝
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

            if (!jo.has("id") || null == jo.getString("id") ||
                    "null".equals(jo.getString("id"))
                    || "".equals(jo.getString("id"))) {
                mapObject.put("id", "");
            } else {
                mapObject.put("id", jo.has("id") ?
                        jo.getString("id") : "");
            }


            if (!jo.has("shop_code") || null == jo.getString("shop_code") ||
                    "null".equals(jo.getString("shop_code"))
                    || "".equals(jo.getString("shop_code"))) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.has("shop_code") ?
                        jo.getString("shop_code") : "");
            }


            if (!jo.has("r_code") || null == jo.getString("r_code") || "null".equals(jo.getString("r_code"))
                    || "".equals(jo.getString("r_code"))) {
                mapObject.put("r_code", "");
            } else {
                mapObject.put("r_code", jo.has("r_code") ? jo.getString("r_code") : "");
            }


            if (!jo.has("issue_code") || null == jo.getString("issue_code") ||
                    "null".equals(jo.getString("issue_code"))
                    || "".equals(jo.getString("issue_code"))) {
                mapObject.put("issue_code", "");
            } else {
                mapObject.put("issue_code", jo.has("issue_code") ?
                        jo.getString("issue_code") : "");
            }

            if (!jo.has("shop_name") || null == jo.getString("shop_name") ||
                    "null".equals(jo.getString("shop_name"))
                    || "".equals(jo.getString("shop_name"))) {
                mapObject.put("shop_name", "");
            } else {
                mapObject.put("shop_name", jo.has("shop_name") ?
                        jo.getString("shop_name") : "");
            }

            //开奖时间
            if (!jo.has("etime") || null == jo.getString("etime") ||
                    "null".equals(jo.getString("etime"))
                    || "".equals(jo.getString("etime"))) {
                mapObject.put("etime", 0L);
            } else {
                mapObject.put("etime", jo.has("etime") ? jo.getLong("etime") : 0L);
            }

            //开始时间
            if (!jo.has("btime") || null == jo.getString("btime") ||
                    "null".equals(jo.getString("btime"))
                    || "".equals(jo.getString("btime"))) {
                mapObject.put("btime", 0L);
            } else {
                mapObject.put("btime", jo.has("btime") ? jo.getLong("btime") : 0L);
            }

            //"status": 当前状态 0正常参与 1等待开奖 2已经开奖,
            if (!jo.has("status") || null == jo.getString("status") ||
                    "null".equals(jo.getString("status"))
                    || "".equals(jo.getString("status"))) {
                mapObject.put("status", "");
            } else {
                mapObject.put("status", jo.has("status") ? jo.getInt("status") : "");
            }


            if (!jo.has("in_code") || null == jo.getString("in_code") ||
                    "null".equals(jo.getString("in_code"))
                    || "".equals(jo.getString("in_code"))) {
                mapObject.put("in_code", "");
            } else {
                mapObject.put("in_code", jo.has("in_code") ? jo.getString("in_code") :
                        "");
            }

            if (!jo.has("in_sum") || null == jo.getString("in_sum") || "null".equals(jo.getString("in_sum"))
                    || "".equals(jo.getString("in_sum"))) {
                mapObject.put("in_sum", "");
            } else {
                mapObject.put("in_sum", jo.has("in_sum") ? jo.getString("in_sum") : "");
            }


            if (!jo.has("num") || null == jo.getString("num") || "null".equals(jo.getString("num"))
                    || "".equals(jo.getString("num"))) {
                mapObject.put("num", "0");
            } else {
                mapObject.put("num", jo.has("num") ? jo.getString("num") : "0");
            }


            if (!jo.has("v_num") || null == jo.getString("v_num") || "null".equals(jo.getString("v_num"))
                    || "".equals(jo.getString("v_num"))) {
                mapObject.put("v_num", "0");
            } else {
                mapObject.put("v_num", jo.has("v_num") ? jo.getString("v_num") : "0");
            }


            if (!jo.has("in_rollUserName") || null == jo.getString("in_rollUserName") || "null".equals(jo.getString("in_rollUserName"))
                    || "".equals(jo.getString("in_rollUserName"))) {
                mapObject.put("in_rollUserName", "");
            } else {
                mapObject.put("in_rollUserName", jo.has("in_rollUserName") ? jo.getString("in_rollUserName") : "");
            }


            if (!jo.has("in_rollHead") || null == jo.getString("in_rollHead") || "null".equals(jo.getString("in_rollHead"))
                    || "".equals(jo.getString("in_rollHead"))) {
                mapObject.put("in_rollHead", "");
            } else {
                mapObject.put("in_rollHead", jo.has("in_rollHead") ? jo.getString("in_rollHead") : "");
            }


            if (!jo.has("in_userId") || null == jo.getString("in_userId") || "null".equals(jo.getString("in_userId"))
                    || "".equals(jo.getString("in_userId"))) {
                mapObject.put("in_userId", "");
            } else {
                mapObject.put("in_userId", jo.has("in_userId") ? jo.getInt("in_userId") : "");
            }

            if (!jo.has("shop_pic") || null == jo.getString("shop_pic") ||
                    "null".equals(jo.getString("shop_pic"))
                    || "".equals(jo.getString("shop_pic"))) {
                mapObject.put("shop_pic", "");
            } else {
                mapObject.put("shop_pic", jo.has("shop_pic") ? jo.getString("shop_pic") :
                        "");
            }

//---------------------------------------------------------------------------------------------

//            if (!jo.has("in_name") || null == jo.getString("in_name") ||
//                    "".equals(jo.getString("in_name"))) {
//                mapObject.put("in_name", "");
//            } else {
//                mapObject.put("in_name", jo.has("in_name") ? jo.getString("in_name") :
//                        "");
//            }
//            if (!jo.has("in_head") || null == jo.getString("in_head") ||
//                    "null".equals(jo.getString("in_head"))
//                    || "".equals(jo.getString("in_head"))) {
//                mapObject.put("in_head", "");
//            } else {
//                mapObject.put("in_head", jo.has("in_head") ? jo.getString("in_head") :
//                        "");
//            }
//            // mapObject.put("in_head", jo.getString("in_head"));
//            if (!jo.has("in_uid") || null == jo.getString("in_uid") ||
//                    "null".equals(jo.getString("in_uid"))
//                    || "".equals(jo.getString("in_uid"))) {
//                mapObject.put("in_uid", "0");
//            } else {
//                mapObject.put("in_uid", jo.has("in_uid") ? jo.getInt("in_uid") : "0");
//            }
//
//            if (!jo.has("shop_pic") || null == jo.getString("shop_pic") ||
//                    "null".equals(jo.getString("shop_pic"))
//                    || "".equals(jo.getString("shop_pic"))) {
//                mapObject.put("shop_pic", "");
//            } else {
//                mapObject.put("shop_pic", jo.has("shop_pic") ? jo.getString("shop_pic") :
//                        "");
//            }
//
//            if (!jo.has("num") || null == jo.getString("num") ||
//                    "null".equals(jo.getString("num"))
//                    || "".equals(jo.getString("num"))) {
//                mapObject.put("num", "0");
//            } else {
//                mapObject.put("num", jo.has("num") ? jo.getInt("num") : "0");
//            }
//            if (!jo.has("virtual_num") || null == jo.getString("virtual_num")
//                    || "null".equals(jo.getString("virtual_num")) ||
//                    "".equals(jo.getString("virtual_num"))) {
//                mapObject.put("virtual_num", "0");
//            } else {
//                mapObject.put("virtual_num", jo.has("virtual_num") ?
//                        jo.getInt("virtual_num") : "0");
//            }
//            if (!jo.has("otime") || null == jo.getString("otime") ||
//                    "null".equals(jo.getString("otime"))
//                    || "".equals(jo.getString("otime"))) {
//                mapObject.put("otime", 0L);
//            } else {
//                mapObject.put("otime", jo.has("otime") ? jo.getLong("otime") : 0L); //
//
//            }
//            if (!jo.has("btime") || null == jo.getString("btime") ||
//                    "null".equals(jo.getString("btime"))
//                    || "".equals(jo.getString("btime"))) {
//                mapObject.put("btime", 0L);
//            } else {
//                mapObject.put("btime", jo.has("btime") ? jo.getLong("btime") : 0L);
//            }


            list.add(mapObject);
        }
        return list;

    }


    public static HashMap<String, String> createDuoBaoh5(Context context, String result) throws JSONException {

        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject jo = new JSONObject(result);
        if (null == jo || "".equals(jo)) {
            return null;
        }


        if (!jo.has("status") || null == jo.getString("status") || jo.getString("status").equals("null")) {
            map.put("statuss", "-1");
        } else {
            map.put("statuss", jo.has("status") ? jo.optString("status") : "-1");
        }

        String shareData = jo.optString("shareData");

        if (null == shareData || "null" == shareData) {
            map.put("status", "-1");
            return map;
        }

        JSONObject j = new JSONObject(jo.optString("shareData"));
        if (null == j || "".equals(j) || "null".equals(j) || j.length() == 0) {
            map.put("status", "-1");
            return map;
        }


        if (!j.has("shop_code") || null == j.getString("shop_code") || j.getString("shop_code").equals("null")) {
            map.put("shop_code", "-1");
        } else {

            map.put("shop_code", j.has("shop_code") ? j.optString("shop_code") : "-1");
        }


        if (!j.has("user_id") || null == j.getString("user_id") || j.getString("user_id").equals("null")) {
            map.put("user_id", "");
        } else {

            map.put("user_id", j.has("user_id") ? j.optString("user_id") : "-1");
        }


        if (!j.has("sup_user_id") || null == j.getString("sup_user_id") || j.getString("sup_user_id").equals("null")) {
            map.put("sup_user_id", "");
        } else {

            map.put("sup_user_id", j.has("sup_user_id") ? j.optString("sup_user_id") : "-1");
        }

        //当status不是-1代表被引导过
        if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")) {
            map.put("status", "-1");
        } else {
            map.put("status", j.optString("status"));

        }


        return map;

    }


    public static final List<HashMap<String, String>> createCrazyShopTagList(Context context, String result)
            throws Exception {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String,
                String>>();
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(jsonObject.getString("crazyMonday"));
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.opt(i);
            HashMap<String, String> mapObject = new HashMap<String, String>();

            if (!jo.has("shop_code") || null == jo.getString("shop_code") || "null".equals(jo.getString("shop_code")) || "".equals(jo.getString("shop_code"))) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.has("shop_code") ?
                        jo.getString("shop_code") : "");
            }


            if (!jo.has("url") || null == jo.getString("url") || "null".equals(jo.getString("url")) || "".equals(jo.getString("url"))) {
                mapObject.put("url", "");
            } else {
                mapObject.put("url", jo.has("url") ?
                        jo.getString("url") : "");
            }

            if (!jo.has("option_type") || null == jo.getString("option_type") || "null".equals(jo.getString("option_type")) || "".equals(jo.getString("option_type"))) {
                mapObject.put("option_type", "");
            } else {
                mapObject.put("option_type", jo.has("option_type") ?
                        jo.getString("option_type") : "");
            }


            list.add(mapObject);
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

            if (!jo.has("status") || null == jo.getString("status") || jo.getString("status").equals("null")) {
                map.put("status", 0);
            } else {

                map.put("status", jo.has("status") ? jo.optInt("status") : 0);
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
            // order_code:订单编号
            if (!jo.has("order_code") || null == jo.getString("order_code") || jo.getString("order_code").equals("null")) {
                map.put("r_code", 0);
            } else {

                map.put("r_code", jo.has("order_code") ? jo.optString("order_code") : 0);
            }


            if (!jo.has("is_deduct_money") || null == jo.getString("is_deduct_money") || jo.getString("is_deduct_money").equals("null")) {
                map.put("is_deduct_money", "");
            } else {

                map.put("is_deduct_money", jo.has("is_deduct_money") ? jo.optString("is_deduct_money") : "");
            }

            if (!jo.has("add_time") || null == jo.getString("add_time") || jo.getString("add_time").equals("null")) {
                map.put("add_time", "0");
            } else {

                map.put("add_time", jo.has("add_time") ? jo.optString("add_time") : "0");
            }


            if (!jo.has("money") || null == jo.getString("money") || jo.getString("money").equals("null")) {
                map.put("money", "0");
            } else {

                map.put("money", jo.has("money") ? jo.optString("money") : "0");
            }


            if (!jo.has("cfrom") || null == jo.getString("cfrom") || jo.getString("cfrom").equals("null")) {
                map.put("cfrom", "0");
            } else {

                map.put("cfrom", jo.has("cfrom") ? jo.optString("cfrom") : "0");
            }


            lists.add(map);
        }
        // LogYiFu.e("TAG", lists.toString());
        return lists;
    }


    public static boolean createHasHongbao(Context context, String result) throws JSONException {
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return false;
        }
        if (1 == j.getInt("status")) {
            JSONArray jsondaytaskList = new JSONArray(j.getString("daytaskList"));
            for (int i = 0; i < jsondaytaskList.length(); i++) {
                JSONObject jo = (JSONObject) jsondaytaskList.opt(i);
//                if (!jo.has("task_type") || null == jo.getString("task_type")
//                        || "null".equals(jo.getString("task_type")) || "".equals(jo.getString("task_type"))) {
//                    return false;
//                } else {


                if ("26".equals(jo.getString("task_type") + "")) {
                    return true;
                }


//                    else {
//                        return false;
//                    }


//                }

            }

        }
        return false;
    }


    public static HashMap<String, String> createCiriCount(Context context, String result) throws JSONException {

        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject jData = new JSONObject(result);
        if (null == jData || "".equals(jData)) {
            return null;
        }
//        JSONObject jData = new JSONObject(j.optString("data"));
//        if (null == jData || "".equals(jData)) {
//            return null;
//        }
        if (!jData.has("task_count") || null == jData.getString("task_count") || jData.getString("task_count").equals("null")) {
            map.put("task_count", "17");
        } else {

            map.put("task_count", jData.has("task_count") ? jData.optString("task_count") : "16");
        }

        if (!jData.has("money") || null == jData.getString("money") || jData.getString("money").equals("null")) {
            map.put("money", "51");
        } else {

            map.put("money", jData.has("money") ? jData.optString("money") : "50");
        }


        return map;

    }


}
