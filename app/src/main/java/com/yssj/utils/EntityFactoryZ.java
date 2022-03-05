package com.yssj.utils;

import android.content.Context;
import android.view.View;

import com.yssj.data.YDBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityFactoryZ {


    /**
     * 获取正常夺宝规则文案
     *
     * @return
     * @throws JSONException
     */
    public static List<String> createIndianaTextContent(JSONObject jj, String key) throws JSONException {
        List<String> list = new ArrayList<String>();
        if (null == jj || "".equals(jj) || "null".equals(jj)) {
            return null;
        }

        if (!jj.has(key) || null == jj.getString(key) || "".equals(jj.getString(key))
                || "null".equals(jj.getString(key))) {
            return null;
        }

        JSONObject joj = jj.getJSONObject(key);

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

    /**
     * 得到拼团夺宝参与记录
     */
    public static final List<HashMap<String, Object>> createGroupIndianaTakeRecord(String result)
            throws JSONException {
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (!j.has("status") || !"1".equals(j.getString("status"))) {
            return null;
        }
        if (!j.has("data") || "".equals(j.getString("data")) || "null".equals(j.getString("data")) || null == j.getString("data")) {
            return null;
        }
        JSONArray jsonArray = j.has("data") ? new JSONArray(j.getString("data")) : null;
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }
//                "id":24,
//                "u_code":"幸运号",
//                "shop_code":商品编号,
//                "issue_code":期号,
//                "user_id":团长id,
//                "user_head":团长头像,
//                "user_name":团长昵称,
//                "add_time":成团时间,
//                "join_time":参与时间,
//                "num":数量

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.opt(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();
            if (!jo.has("id") || null == jo.getString("id") || "null".equals(jo.getString("id"))
                    || "".equals(jo.getString("id"))) {
                mapObject.put("id", "0");
            } else {
                mapObject.put("id", jo.getString("id"));
            }
            if (!jo.has("u_code") || null == jo.getString("u_code") || "null".equals(jo.getString("u_code"))
                    || "".equals(jo.getString("u_code"))) {
                mapObject.put("u_code", "0");
            } else {
                mapObject.put("u_code", jo.getString("u_code"));
            }
            if (!jo.has("shop_code") || null == jo.getString("shop_code") || "null".equals(jo.getString("shop_code"))
                    || "".equals(jo.getString("shop_code"))) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.getString("shop_code"));
            }
            if (!jo.has("issue_code") || null == jo.getString("issue_code") || "".equals(jo.getString("issue_code"))) {
                mapObject.put("issue_code", "");
            } else {
                mapObject.put("issue_code", jo.getString("issue_code"));
            }
            if (!jo.has("user_id") || null == jo.getString("user_id") || "null".equals(jo.getString("user_id"))
                    || "".equals(jo.getString("user_id"))) {
                mapObject.put("user_id", "");
            } else {
                mapObject.put("user_id", jo.getString("user_id"));
            }
            if (!jo.has("user_head") || null == jo.getString("user_head") || "".equals(jo.getString("user_head"))) {
                mapObject.put("user_head", "");
            } else {
                mapObject.put("user_head", jo.getString("user_head"));
            }
            if (!jo.has("user_name") || null == jo.getString("user_name") || "".equals(jo.getString("user_name"))) {
                mapObject.put("user_name", "");
            } else {
                mapObject.put("user_name", jo.getString("user_name"));
            }
            if (!jo.has("add_time") || null == jo.getString("add_time") || "".equals(jo.getString("add_time")) || "null".equals(jo.getString("add_time"))) {
                mapObject.put("add_time", "0");
            } else {
                mapObject.put("add_time", jo.getString("add_time"));
            }
            if (!jo.has("join_time") || null == jo.getString("join_time") || "".equals(jo.getString("join_time")) || "null".equals(jo.getString("join_time"))) {
                mapObject.put("join_time", "0");
            } else {
                mapObject.put("join_time", jo.getString("join_time"));
            }
            if (!jo.has("num") || null == jo.getString("num") || "".equals(jo.getString("num"))) {
                mapObject.put("num", "");
            } else {
                mapObject.put("num", jo.getString("num"));
            }
            retInfo.add(mapObject);
        }
        return retInfo;
    }

    /**
     * 得到拼团夺宝拼团列表
     */
    public static final List<HashMap<String, Object>> createGroupList(Context context, String result)
            throws JSONException {
        HashMap<String, Object> tempMap1 = new HashMap<String, Object>();//开团未满
        HashMap<String, Object> tempMap2 = new HashMap<String, Object>();//参团未满
        List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (!j.has("status") || !"1".equals(j.getString("status"))) {
            return null;
        }
        if (!j.has("data") || "".equals(j.getString("data")) || "null".equals(j.getString("data")) || null == j.getString("data")) {
            return null;
        }
        JSONArray jsonArray = j.has("data") ? new JSONArray(j.getString("data")) : null;
        if (null == jsonArray || "".equals(jsonArray)) {
            return null;
        }
//                "issue_code":期号( == 0 时表示没满足人数)
//                 "tuserId":团长id,
//                "btime":开团时间,
//                "shop_code":商品编号,
//                "thead":团长头像,
//                "num":当前参与人数,
//                "nickname":团长昵称,
//                "user": [// 所有的团员都在这里面了
//                {
//                 "head":团员头像,
//                "uid":团员id,
//                "btime":团员参与时间,
//                "nickname":团员昵称 "
//                }]

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = (JSONObject) jsonArray.opt(i);
            HashMap<String, Object> mapObject = new HashMap<String, Object>();
            if (!jo.has("issue_code") || null == jo.getString("issue_code") || "null".equals(jo.getString("issue_code"))
                    || "".equals(jo.getString("issue_code"))) {
                mapObject.put("issue_code", "");
            } else {
                mapObject.put("issue_code", jo.getString("issue_code"));
            }
            if (!jo.has("tuserId") || null == jo.getString("tuserId") || "null".equals(jo.getString("tuserId"))
                    || "".equals(jo.getString("tuserId"))) {
                mapObject.put("tuserId", "");
            } else {
                mapObject.put("tuserId", jo.getString("tuserId"));
            }
            if (!jo.has("btime") || null == jo.getString("btime") || "null".equals(jo.getString("btime"))
                    || "".equals(jo.getString("btime"))) {
                mapObject.put("btime", "0");
            } else {
                mapObject.put("btime", jo.getString("btime"));
            }
            if (!jo.has("shop_code") || null == jo.getString("shop_code") || "".equals(jo.getString("shop_code"))) {
                mapObject.put("shop_code", "");
            } else {
                mapObject.put("shop_code", jo.getString("shop_code"));
            }
            if (!jo.has("thead") || null == jo.getString("thead") || "null".equals(jo.getString("thead"))
                    || "".equals(jo.getString("thead"))) {
                mapObject.put("thead", "");
            } else {
                mapObject.put("thead", jo.getString("thead"));
            }
            if (!jo.has("num") || null == jo.getString("num") || "".equals(jo.getString("num"))) {
                mapObject.put("num", "");
            } else {
                mapObject.put("num", jo.getString("num"));
            }
            if (!jo.has("nickname") || null == jo.getString("nickname") || "".equals(jo.getString("nickname"))) {
                mapObject.put("nickname", "");
            } else {
                mapObject.put("nickname", jo.getString("nickname"));
            }
            if (!jo.has("u_code") || null == jo.getString("u_code") || "".equals(jo.getString("u_code"))) {
                mapObject.put("u_code", "");
            } else {
                mapObject.put("u_code", jo.getString("u_code"));
            }
            List<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> userMapHost = new HashMap<String, String>();
            userMapHost.put("head", mapObject.get("thead").toString());
            userMapHost.put("uid", mapObject.get("tuserId").toString());
            userMapHost.put("btime", mapObject.get("btime").toString());
            userMapHost.put("nickname", mapObject.get("nickname").toString());
            userList.add(userMapHost);
            if (!jo.has("user") || null == jo.getString("user") || "".equals(jo.getString("user"))) {
                mapObject.put("user", userList);
            } else {
                JSONArray userArray = new JSONArray(jo.getString("user"));
                if (null == jsonArray || "".equals(jsonArray) || "null".equals(jsonArray)) {
                    mapObject.put("user", userList);
                } else {
                    for (int k = 0; k < userArray.length(); k++) {
                        JSONObject joUser = (JSONObject) userArray.opt(k);
                        HashMap<String, String> userMap = new HashMap<String, String>();
                        if (!joUser.has("head") || null == joUser.getString("head") || "".equals(joUser.getString("head"))) {
                            userMap.put("head", "");
                        } else {
                            userMap.put("head", joUser.getString("head"));
                        }
                        if (!joUser.has("uid") || null == joUser.getString("uid") || "".equals(joUser.getString("uid"))) {
                            userMap.put("uid", "");
                        } else {
                            userMap.put("uid", joUser.getString("uid"));
                        }
                        if (!joUser.has("btime") || null == joUser.getString("btime") || "".equals(joUser.getString("btime")) || "null".equals(joUser.getString("btime"))) {
                            userMap.put("btime", "0");
                        } else {
                            userMap.put("btime", joUser.getString("btime"));
                        }
                        if (!joUser.has("nickname") || null == joUser.getString("nickname") || "".equals(joUser.getString("nickname"))) {
                            userMap.put("nickname", "");
                        } else {
                            userMap.put("nickname", joUser.getString("nickname"));
                        }
                        userList.add(userMap);
                    }
                    mapObject.put("user", userList);
                }
            }

            String user_id = YCache.getCacheUser(context).getUser_id() + "";
            if (user_id.equals(mapObject.get("tuserId")) &&
                    "0".equals(mapObject.get("issue_code"))) {
                tempMap1 = mapObject;
            } else if (!user_id.equals(mapObject.get("tuserId")) &&
                    "0".equals(mapObject.get("issue_code"))) {
                tempMap2 = mapObject;
            } else {
                retInfo.add(mapObject);
            }

        }
        if (tempMap2.size() > 0) {
            retInfo.add(0, tempMap2);
        }
        if (tempMap1.size() > 0) {
            retInfo.add(0, tempMap1);
        }
        return retInfo;
    }


    /**
     * 拼团文案
     */
    public static HashMap<String, String> createGroupContent(JSONObject jj) throws JSONException {
        if (null == jj || "".equals(jj) || "null".equals(jj)) {
            return null;
        }

        if (!jj.has("ptgwn") || null == jj.getString("ptgwn") || "".equals(jj.getString("ptgwn"))
                || "null".equals(jj.getString("ptgwn"))) {
            return null;
        }

        JSONObject jo = jj.getJSONObject("ptgwn");

        if (null == jo || "".equals(jo)) {
            return null;
        }

        HashMap<String, String> map = new HashMap<String, String>();

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
     * 分享文案
     */
    public static HashMap<String, String> createShareTitleContent(JSONObject jj) throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        if (null == jj || "".equals(jj) || "null".equals(jj)) {
            map.put("title", "${replace}品牌${replace}，仅售${replace}元，次月返${replace}元，等于0元！");//
            map.put("text", "");
            return map;
        }

        if (!jj.has("wxdddfx") || null == jj.getString("wxdddfx") || "".equals(jj.getString("wxdddfx"))
                || "null".equals(jj.getString("wxdddfx"))) {
            map.put("title", "${replace}品牌${replace}，仅售${replace}元，次月返${replace}元，等于0元！");//
            map.put("text", "");
            return map;
        }

        JSONObject jo = jj.getJSONObject("wxdddfx");

        if (null == jo || "".equals(jo)) {
            map.put("title", "${replace}品牌${replace}，仅售${replace}元，次月返${replace}元，等于0元！");//
            map.put("text", "");
            return map;
        }


        if (!jo.has("title") || null == jo.getString("title") || "null".equals(jo.getString("title")) || "".equals(jo.getString("title"))) {
            map.put("title", "${replace}品牌${replace}，仅售${replace}元，次月返${replace}元，等于0元！");//
        } else {
            map.put("title", jo.optString("title", "${replace}品牌${replace}，仅售${replace}元，次月返${replace}元，等于0元！"));
        }
        if (!jo.has("text") || null == jo.getString("text") || "null".equals(jo.getString("text")) || "".equals(jo.getString("text"))) {
            map.put("text", "");//
        } else {
            map.put("text", jo.optString("text"));
        }
        return map;
    }

    /**
     * 分享文案
     */
    public static HashMap<String, String> createShareTitleContentNew(JSONObject jj) throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        if (null == jj || "".equals(jj) || "null".equals(jj)) {
            map.put("title", "${replace}元！【${replace}品牌${replace}】次月返还${replace}元，等于0元！");//
            map.put("text", "");
            return map;
        }

        if (!jj.has("wxcx_wxdddfx") || null == jj.getString("wxcx_wxdddfx") || "".equals(jj.getString("wxcx_wxdddfx"))
                || "null".equals(jj.getString("wxdddfx"))) {
            map.put("title", "${replace}元！【${replace}品牌${replace}】次月返还${replace}元，等于0元！");//
            map.put("text", "");
            return map;
        }

        JSONObject jo = jj.getJSONObject("wxcx_wxdddfx");

        if (null == jo || "".equals(jo)) {
            map.put("title", "${replace}元！【${replace}品牌${replace}】次月返还${replace}元，等于0元！");//
            map.put("text", "");
            return map;
        }


        if (!jo.has("title") || null == jo.getString("title") || "null".equals(jo.getString("title")) || "".equals(jo.getString("title"))) {
            map.put("title", "${replace}元！【${replace}品牌${replace}】次月返还${replace}元，等于0元！");//
        } else {
            map.put("title", jo.optString("title", "${replace}元！【${replace}品牌${replace}】次月返还${replace}元，等于0元！"));
        }
        if (!jo.has("text") || null == jo.getString("text") || "null".equals(jo.getString("text")) || "".equals(jo.getString("text"))) {
            map.put("text", "");//
        } else {
            map.put("text", jo.optString("text"));
        }
        return map;
    }

    /**
     * 一分钱夺宝分享文案
     */
    public static HashMap<String, HashMap<String, Object>> createIndianaTextContent(JSONObject jsonObject) throws JSONException {

        HashMap<String, HashMap<String, Object>> allMap = new HashMap<String, HashMap<String, Object>>();

        if (null == jsonObject || "".equals(jsonObject)) {
            return allMap;
        }

        getText(jsonObject, allMap, "spellGroup_yf");
        getText(jsonObject, allMap, "indiana_yf");
        getText(jsonObject, allMap, "h5money_yf");
        getText(jsonObject, allMap, "link");


        return allMap;
    }

    private static void getText(JSONObject jsonObject, HashMap<String, HashMap<String, Object>> allMap, String ssss) throws JSONException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (jsonObject.has(ssss)) {
            JSONObject jo = jsonObject.getJSONObject(ssss);
            if (!jo.has("title") || null == jo.getString("title") || "null".equals(jo.getString("title"))
                    || "".equals(jo.getString("title"))) {
                map.put("title", "");
            } else {
                map.put("title", jo.optString("title", ""));
            }

            if (!jo.has("text") || null == jo.getString("text") || "null".equals(jo.getString("text"))
                    || "".equals(jo.getString("text"))) {
                map.put("text", "");
            } else {
                map.put("text", jo.optString("text", ""));
            }

            if (!jo.has("icon") || null == jo.getString("icon") || "null".equals(jo.getString("icon"))
                    || "".equals(jo.getString("icon"))) {
                map.put("icon", "");
            } else {
                map.put("icon", jo.optString("icon", ""));
            }

            if (!jo.has("remark") || null == jo.getString("remark") || "null".equals(jo.getString("remark"))
                    || "".equals(jo.getString("remark"))) {
                map.put("remark", "");
            } else {
                map.put("remark", jo.optString("remark", ""));
            }

        } else {
//            map.put("title", "不存在");
        }

        allMap.put(ssss, map);
    }

    /**
     * 得到拼团夺宝参与记录
     */
    public static final HashMap<String, String> createAutoWithDraw(String result)
            throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            return null;
        }
        if (!j.has("status") || "".equals(j.getString("status")) || "null".equals(j.getString("status")) || null == j.getString("status")) {
            map.put("status", "2");
        } else {
            map.put("status", j.getString("status"));
        }
        if (!j.has("message") || "".equals(j.getString("message")) || null == j.getString("message")) {
            map.put("message", "");
        } else {
            map.put("message", j.getString("message"));
        }
        return map;
    }

    /**
     * 分享文案
     */
    public static HashMap<String, String> createShareGroupTitleContent(JSONObject jj) throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        if (null == jj || "".equals(jj) || "null".equals(jj)) {
            map.put("title", "仅剩${replace}人，快来，我免费领到了${replace}");//
            map.put("text", "${replace}已经开团，仅剩${replace}人，全场0元，成团即发。马上来参团吧。");
            return map;
        }

        if (!jj.has("ptdbfx") || null == jj.getString("ptdbfx") || "".equals(jj.getString("ptdbfx"))
                || "null".equals(jj.getString("ptdbfx"))) {
            map.put("title", "仅剩${replace}人，快来，我免费领到了${replace}");//
            map.put("text", "${replace}已经开团，仅剩${replace}人，全场0元，成团即发。马上来参团吧。");
            return map;
        }

        JSONObject jo = jj.getJSONObject("ptdbfx");

        if (null == jo || "".equals(jo)) {
            map.put("title", "仅剩${replace}人，快来，我免费领到了${replace}");//
            map.put("text", "${replace}已经开团，仅剩${replace}人，全场0元，成团即发。马上来参团吧。");
            return map;
        }


        if (!jo.has("title") || null == jo.getString("title") || "null".equals(jo.getString("title")) || "".equals(jo.getString("title"))) {
            map.put("title", "仅剩${replace}人，快来，我免费领到了${replace}");//
        } else {
            map.put("title", jo.optString("title"));
        }
        if (!jo.has("text") || null == jo.getString("text") || "null".equals(jo.getString("text")) || "".equals(jo.getString("text"))) {
            map.put("text", "${replace}已经开团，仅剩${replace}人，全场0元，成团即发。马上来参团吧。");
        } else {
            map.put("text", jo.optString("text"));
        }
        return map;
    }


    /**
     * 获取二级类目和品牌
     */
    public static final HashMap<String, String> createType2SuppLabel(String result, Context context)
            throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject j = new JSONObject(result);
        if (null == j || "".equals(j)) {
            map.put("type2", "");
            map.put("supp_label_id", "衣蝠");
            return map;
        }
        if (!j.has("status") || "".equals(j.getString("status")) || "null".equals(j.getString("status")) || null == j.getString("status") || !"1".equals(j.getString("status"))) {
            map.put("type2", "");
            map.put("supp_label_id", "衣蝠");
            return map;
        }
        YDBHelper dbHelp = new YDBHelper(context);
        if (!j.has("type2") || "".equals(j.getString("type2")) || null == j.getString("type2") || "null".equals(j.getString("type2"))) {
            map.put("type2", "");
        } else {
//            String sql2 = "select * from supp_label where _id = " + j.getString("type2")
//                    + " order by _id";
//            List<HashMap<String, String>> listSupp = new ArrayList<HashMap<String, String>>();
//            try {
//                listSupp = dbHelp.query(sql2);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (listSupp != null && listSupp.size() > 0) {
//                String label_name = listSupp.get(0).get("class_name");
//                map.put("type2", label_name);
//            } else {
//                map.put("type2", "");
//            }
            map.put("type2", j.getString("type2"));
        }

        if (!j.has("supp_label_id") || "".equals(j.getString("supp_label_id")) || null == j.getString("supp_label_id")) {
            map.put("supp_label_id", "衣蝠");
        } else {
            String sql2 = "select * from supp_label where _id = " + j.getString("supp_label_id")
                    + " order by _id";
            List<HashMap<String, String>> listSupp = new ArrayList<HashMap<String, String>>();// 品牌
            try {
                listSupp = dbHelp.query(sql2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (listSupp != null && listSupp.size() > 0) {
                String label_name = listSupp.get(0).get("name");
                map.put("supp_label_id", label_name);
            } else {
                map.put("supp_label_id", "衣蝠");
            }
        }


        return map;
    }


    /**
     * 分享文案
     */
    public static HashMap<String, String> createNextDayTakContent(JSONObject jj) throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        if (null == jj || "".equals(jj) || "null".equals(jj)) {
            return null;
        }

        if (!jj.has("crrwyg") || null == jj.getString("crrwyg") || "".equals(jj.getString("crrwyg"))
                || "null".equals(jj.getString("crrwyg"))) {
            return null;
        }

        JSONObject jo = jj.getJSONObject("crrwyg");

        if (null == jo || "".equals(jo)) {
            return null;
        }


        if (!jo.has("title") || null == jo.getString("title") || "null".equals(jo.getString("title")) || "".equals(jo.getString("title"))) {
            map.put("title", "");//
        } else {
            map.put("title", jo.optString("title"));
        }
        return map;
    }
}
