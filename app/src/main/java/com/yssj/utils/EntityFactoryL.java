package com.yssj.utils;

import android.content.Context;

import com.yssj.Json;
import com.yssj.entity.ReturnInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityFactoryL {


//    /**
//     * 新的拼团详情 邀请好友参团分享  获取文案
//     */
//    public static List<HashMap<String, String>> createGroupsShareContent(Context context, String result) throws JSONException {
//        JSONObject jsonObject = new JSONObject(result);
//        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//        if (null == jsonObject || "".equals(jsonObject)) {
//            return null;
//        }
//
//        if (!"1".equals(jsonObject.getString("status"))) {
//            return null;
//        }
//        if (!jsonObject.has("ptgfx") || null == jsonObject.getString("ptgfx") || "null".equals(jsonObject.getString("ptgfx"))
//                || "".equals(jsonObject.getString("ptgfx"))) {
//            return null;
//        }
//
//        JSONArray ja = new JSONArray(jsonObject.getString("ptgfx"));
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


    public static HashMap<String, HashMap<String, Object>> createSpellGroupsTextContent(JSONObject jsonObject) throws JSONException {

        HashMap<String, HashMap<String, Object>> allMap = new HashMap<String, HashMap<String, Object>>();

        if (null == jsonObject || "".equals(jsonObject)) {
            return allMap;
        }


//            HashMap<String, Object> map = new HashMap<String, Object>();
//            JSONObject jo = jsonObject.getJSONObject("spellGroup");
//
//            if (!jo.has("title") || null == jo.getString("title") || "null".equals(jo.getString("title"))
//                    || "".equals(jo.getString("title"))) {
//                map.put("title", "");
//            } else {
//                map.put("title", jo.optString("title", ""));
//            }
//
//            if (!jo.has("text") || null == jo.getString("text") || "null".equals(jo.getString("text"))
//                    || "".equals(jo.getString("text"))) {
//                map.put("text", "");
//            } else {
//                map.put("text", jo.optString("text", ""));
//            }
//
//            if (!jo.has("icon") || null == jo.getString("icon") || "null".equals(jo.getString("icon"))
//                    || "".equals(jo.getString("icon"))) {
//                map.put("icon", "");
//            } else {
//                map.put("icon", jo.optString("icon", ""));
//            }
//
//            if (!jo.has("remark") || null == jo.getString("remark") || "null".equals(jo.getString("remark"))
//                    || "".equals(jo.getString("remark"))) {
//                map.put("remark", "");
//            } else {
//                map.put("remark", jo.optString("remark", ""));
//            }
//
//            allMap.put("spellGroup", map);

        getText(jsonObject, allMap, "spellGroup");
        getText(jsonObject, allMap, "indiana");
        getText(jsonObject, allMap, "h5money");
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


    public static HashMap<String, Object> createTextContent(JSONObject jsonObject, String key) throws JSONException {

        HashMap<String, Object> allMap = new HashMap<String, Object>();

        if (null == jsonObject || "".equals(jsonObject)) {
            return allMap;
        }

        getTextContent(jsonObject, allMap, key);


        return allMap;
    }

    private static void getTextContent(JSONObject jsonObject, HashMap<String, Object> allMap, String key) throws JSONException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (jsonObject.has(key)) {
            JSONObject jo = jsonObject.getJSONObject(key);
            if (!jo.has("title") || null == jo.getString("title") || "null".equals(jo.getString("title"))
                    || "".equals(jo.getString("title"))) {
                map.put("title", "");
            } else {
                map.put("title", jo.optString("title", ""));
            }
            //首页赚钱任务提醒文案 加一个title1 字段
            if (!jo.has("title1") || null == jo.getString("title1") || "null".equals(jo.getString("title1"))
                    || "".equals(jo.getString("title1"))) {
                map.put("title1", "");
            } else {
                map.put("title1", jo.optString("title1", ""));
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


            //一元夺宝，分享页面文案专用
            if (!jo.has("text1") || null == jo.getString("text1") || "null".equals(jo.getString("text1"))
                    || "".equals(jo.getString("text1"))) {
                map.put("text1", "");
            } else {
                map.put("text1", jo.optString("text1", ""));
            }

            if (!jo.has("text2") || null == jo.getString("text2") || "null".equals(jo.getString("text2"))
                    || "".equals(jo.getString("text2"))) {
                map.put("text2", "");
            } else {
                map.put("text2", jo.optString("text2", ""));
            }

            if (!jo.has("t0") || null == jo.getString("t0") || "null".equals(jo.getString("t0"))
                    || "".equals(jo.getString("t0"))) {
                map.put("t0", "");
            } else {
                map.put("t0", jo.optString("t0", ""));
            }


            if (!jo.has("t1") || null == jo.getString("t1") || "null".equals(jo.getString("t1"))
                    || "".equals(jo.getString("t1"))) {
                map.put("t1", "");
            } else {
                map.put("t1", jo.optString("t1", ""));
            }

        } else {

        }

        allMap.put(key, map);
    }

    /**
     * 集赞奖励活动规则文案
     *
     * @param jsonObject
     * @param key
     * @return
     * @throws JSONException
     */
    public static HashMap<String, String> createTextContentJZJL(JSONObject jsonObject, String key) throws JSONException {

        HashMap<String, String> map = new HashMap<String, String>();

        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }
        if (!jsonObject.has(key) || null == jsonObject.getString(key) || "".equals(jsonObject.getString(key))
                || "null".equals(jsonObject.getString(key))) {
            return null;
        }

        String joString = jsonObject.getString(key);
        JSONObject jo = new JSONObject(joString);
        if (null == jo || "".equals(jo)) {
            return null;
        }

        if (!jo.has("n1") || null == jo.getString("n1") || "null".equals(jo.getString("n1"))) {
            map.put("n1", "");
        } else {
            map.put("n1", jo.optString("n1", ""));
        }
        if (!jo.has("n2") || null == jo.getString("n2") || "null".equals(jo.getString("n2"))
                || "".equals(jo.getString("n2"))) {
            map.put("n2", "");
        } else {
            map.put("n2", jo.optString("n2", ""));
        }

        if (!jo.has("n3") || null == jo.getString("n3") || "null".equals(jo.getString("n3"))
                || "".equals(jo.getString("n3"))) {
            map.put("n3", "");
        } else {
            map.put("n3", jo.optString("n3", ""));
        }

        if (!jo.has("n4-1") || null == jo.getString("n4-1") || "null".equals(jo.getString("n4-1"))
                || "".equals(jo.getString("n4-1"))) {
            map.put("n4-1", "");
        } else {
            map.put("n4-1", jo.optString("n4-1", ""));
        }

        if (!jo.has("n4-2") || null == jo.getString("n4-2") || "null".equals(jo.getString("n4-2"))
                || "".equals(jo.getString("n4-2"))) {
            map.put("n4-2", "");
        } else {
            map.put("n4-2", jo.optString("n4-2", ""));
        }

        if (!jo.has("n6") || null == jo.getString("n6") || "null".equals(jo.getString("n6"))
                || "".equals(jo.getString("n6"))) {
            map.put("n6", "");
        } else {
            map.put("n6", jo.optString("n6", ""));
        }

        return map;

    }

    /**
     * 获取 带有颜色的文案
     *
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    public static HashMap<String, Object> createColorTextContent(JSONObject jsonObject, String key) throws JSONException {
        HashMap<String, Object> allMap = new HashMap<String, Object>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return allMap;
        }
        getColorText(jsonObject, allMap, key);
        return allMap;
    }

    private static void getColorText(JSONObject jsonObject, HashMap<String, Object> allMap, String key) throws JSONException {

        HashMap<String, Object> map = new HashMap<String, Object>();
        if (jsonObject.has(key)) {
            JSONObject jo = jsonObject.getJSONObject(key);
            if (!jo.has("remark") || null == jo.getString("remark") || "null".equals(jo.getString("remark"))
                    || "".equals(jo.getString("remark"))) {
                map.put("remark", "");
            } else {
                map.put("remark", jo.optString("remark", ""));
            }

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

            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            if (!jo.has("list") || null == jo.getString("list") || "null".equals(jo.getString("list"))
                    || "".equals(jo.getString("list"))) {
                map.put("list", list);
            } else {
                JSONArray ja = jo.getJSONArray("list");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject joo = ja.getJSONObject(i);
                    HashMap<String, String> mapp = new HashMap<String, String>();
                    if (!joo.has("index") || null == joo.getString("index") || "null".equals(joo.getString("index"))
                            || "".equals(joo.getString("index"))) {
                        mapp.put("index", "");
                    } else {
                        mapp.put("index", joo.optString("index", ""));
                    }
                    if (!joo.has("value") || null == joo.getString("value") || "null".equals(joo.getString("value"))
                            || "".equals(joo.getString("value"))) {
                        mapp.put("value", "");
                    } else {
                        mapp.put("value", joo.optString("value", ""));
                    }

                    if (!joo.has("color") || null == joo.getString("color") || "null".equals(joo.getString("color"))
                            || "".equals(joo.getString("color"))) {
                        mapp.put("color", "");
                    } else {
                        mapp.put("color", joo.optString("color", ""));
                    }
                    list.add(mapp);
                }
                map.put("list", list);
            }
        } else {
//            map.put("title", "不存在");
        }
        allMap.put(key, map);
    }


    /**
     * 分享H5域名动态获取
     */
    public static String createDomain(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!"1".equals(jsonObject.getString("status"))) {
            return null;
        }
        if (!jsonObject.has("domain") || null == jsonObject.getString("domain")) {
            return null;
        } else {
            return jsonObject.optString("domain", null);
        }

    }


    public static final ReturnInfo createRetInfo(Context context, String result) throws JSONException {

        ReturnInfo retInfo = new ReturnInfo();
        JSONObject j = new JSONObject(result);
        retInfo.setStatus(j.has("status") ? j.getString(Json.RetInfo.status) : "");
        retInfo.setMessage(j.has("message") ? j.getString(Json.RetInfo.message) : "");

        return retInfo;
    }


    /**
     * 获取抽奖结果（抽奖 余额红包）
     */
    public static final HashMap<String, String> createBalanceResult(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        HashMap<String, String> map = new HashMap<String, String>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!jsonObject.has("data") || null == jsonObject.getString("data")
                || "null".equals(jsonObject.getString("data")) || "".equals(jsonObject.getString("data"))) {
            map.put("data", "0");
        } else {
            map.put("data", jsonObject.optString("data", "0"));// 获得到余额红包金额 余额抽奖剩余次数
        }

        if (!jsonObject.has("n") || null == jsonObject.getString("n")
                || "null".equals(jsonObject.getString("n")) || "".equals(jsonObject.getString("n"))) {
            map.put("n", "0");
        } else {
            map.put("n", jsonObject.optString("n", "0"));// 余额抽奖总次数
        }

        if (!jsonObject.has("money") || null == jsonObject.getString("money")
                || "null".equals(jsonObject.getString("money")) || "".equals(jsonObject.getString("money"))) {
            map.put("money", "0");
        } else {
            map.put("money", jsonObject.optString("money", "0"));// 余额抽奖总目前抽中的总金额
        }

        if (!jsonObject.has("status") || null == jsonObject.getString("status")
                || "null".equals(jsonObject.getString("status")) || "".equals(jsonObject.getString("status"))) {
            map.put("status", "1");
        } else {
            map.put("status", jsonObject.optString("status", "1"));// 1


        }

        return map;

    }


    /**
     * 获取用户是否绑定提现微信
     */
    public static final HashMap<String, String> createWxIsBind(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        HashMap<String, String> map = new HashMap<String, String>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!jsonObject.has("data") || null == jsonObject.getString("data")
                || "null".equals(jsonObject.getString("data")) || "".equals(jsonObject.getString("data"))) {
            map.put("data", "");
        } else {
            map.put("data", jsonObject.optString("data", "")); //1已经绑定 0 未绑定
        }

        if (!jsonObject.has("wxOpenId") || null == jsonObject.getString("wxOpenId")
                || "null".equals(jsonObject.getString("wxOpenId")) || "".equals(jsonObject.getString("wxOpenId"))) {
            map.put("wxOpenId", "");
        } else {
            map.put("wxOpenId", jsonObject.optString("wxOpenId", ""));
        }



        if (!jsonObject.has("status") || null == jsonObject.getString("status")
                || "null".equals(jsonObject.getString("status")) || "".equals(jsonObject.getString("status"))) {
            map.put("status", "");
        } else {
            map.put("status", jsonObject.optString("status", ""));// 1


        }

        return map;

    }


    /**
     * 转盘 抽提现额度获取抽奖总次数
     */
    public static final HashMap<String, String> createRaffleNumResult(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        HashMap<String, String> map = new HashMap<String, String>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!jsonObject.has("data") || null == jsonObject.getString("data")
                || "null".equals(jsonObject.getString("data")) || "".equals(jsonObject.getString("data"))) {
            map.put("data", "0");
        } else {
            map.put("data", jsonObject.optString("data", "0"));
        }

        if (!jsonObject.has("status") || null == jsonObject.getString("status")
                || "null".equals(jsonObject.getString("status")) || "".equals(jsonObject.getString("status"))) {
            map.put("status", "0");
        } else {
            map.put("status", jsonObject.optString("status", "0"));

        }

        return map;

    }

    /**
     * 好友提成系统 今日奖励弹窗
     */
    public static HashMap<String, Object> createTodayPraiseMoney(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!"1".equals(jsonObject.getString("status"))) {
            return null;
        }
        if (!jsonObject.has("data") || null == jsonObject.getString("data") || "null".equals(jsonObject.getString("data"))
                || "".equals(jsonObject.getString("data"))) {
            return null;
        }
        JSONObject jo = jsonObject.getJSONObject("data");

        HashMap<String, Object> map = new HashMap<>();

        if (!jo.has("ed_num") || null == jo.getString("ed_num") || "null".equals(jo.getString("ed_num"))
                || "".equals(jo.getString("ed_num"))) {
            map.put("ed_num", "0");
        } else {
            map.put("ed_num", jo.optString("ed_num", "0"));//xx人获得额度,
        }
        if (!jo.has("xj_num") || null == jo.getString("xj_num") || "null".equals(jo.getString("xj_num"))
                || "".equals(jo.getString("xj_num"))) {
            map.put("xj_num", "0");
        } else {
            map.put("xj_num", jo.optString("xj_num", "0"));//xx人获得现金,
        }

        if (!jo.has("f_money") || null == jo.getString("f_money") || "null".equals(jo.getString("f_money"))
                || "".equals(jo.getString("f_money"))) {
            map.put("f_money", "0.00");
        } else {
            map.put("f_money", jo.optString("f_money", "0.00"));//下级获得的现金,
        }

        if (!jo.has("f_extra") || null == jo.getString("f_extra") || "null".equals(jo.getString("f_extra"))
                || "".equals(jo.getString("f_extra"))) {
            map.put("f_extra", "0.00");
        } else {
            map.put("f_extra", jo.optString("f_extra", "0.00"));//下级获得的额度
        }


        if (!jo.has("money") || null == jo.getString("money") || "null".equals(jo.getString("money"))
                || "".equals(jo.getString("money"))) {
            map.put("money", "0.00");
        } else {
            map.put("money", jo.optString("money", "0.00"));//我获得现金,
        }

        if (!jo.has("extra") || null == jo.getString("extra") || "null".equals(jo.getString("extra"))
                || "".equals(jo.getString("extra"))) {
            map.put("extra", "0.00");
        } else {
            map.put("extra", jo.optString("extra", "0.00"));//我获得的额度
        }


        List<HashMap<String, String>> listUser = new ArrayList<HashMap<String, String>>();
        if (!jo.has("listUser") || null == jo.getString("listUser") || "null".equals(jo.getString("listUser"))
                || "".equals(jo.getString("listUser"))) {
            map.put("listUser", listUser);
        } else {
            JSONArray ja = jo.getJSONArray("listUser");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject joo = ja.getJSONObject(i);
                HashMap<String, String> mapp = new HashMap<String, String>();
                if (!joo.has("add_date") || null == joo.getString("add_date") || "null".equals(joo.getString("add_date"))
                        || "".equals(joo.getString("add_date"))) {
                    mapp.put("add_date", "0");
                } else {
                    mapp.put("add_date", joo.optString("add_date", "0"));
                }
                if (!joo.has("nickname") || null == joo.getString("nickname") || "null".equals(joo.getString("nickname"))
                        || "".equals(joo.getString("nickname"))) {
                    mapp.put("nickname", "");
                } else {
                    mapp.put("nickname", joo.optString("nickname", ""));
                }

                if (!joo.has("f_extra") || null == joo.getString("f_extra") || "null".equals(joo.getString("f_extra"))
                        || "".equals(joo.getString("f_extra"))) {
                    mapp.put("f_extra", "0");
                } else {
                    mapp.put("f_extra", joo.optString("f_extra", "0")); //提现额度
                }

                if (!joo.has("pic") || null == joo.getString("pic") || "null".equals(joo.getString("pic"))
                        || "".equals(joo.getString("pic"))) {
                    mapp.put("pic", "");
                } else {
                    mapp.put("pic", joo.optString("pic", "")); //图像
                }

                if (!joo.has("f_money") || null == joo.getString("f_money") || "null".equals(joo.getString("f_money"))
                        || "".equals(joo.getString("f_money"))) {
                    mapp.put("f_money", "0");
                } else {
                    mapp.put("f_money", joo.optString("f_money", "0")); //余额
                }


                listUser.add(mapp);
            }
            map.put("listUser", listUser);
        }


        return map;
    }



    /**
     * 赚钱任务弹窗0元购文案返现金额
     */
    public static final HashMap<String, Object> createZeroCount(Context context, String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (null == jsonObject || "".equals(jsonObject)) {
            return null;
        }

        if (!jsonObject.has("data") || null == jsonObject.getString("data")
                || "null".equals(jsonObject.getString("data")) || "".equals(jsonObject.getString("data"))) {
            map.put("data", 0);
        } else {
            map.put("data", jsonObject.optInt("data", 0));
        }

        if (!jsonObject.has("status") || null == jsonObject.getString("status")
                || "null".equals(jsonObject.getString("status")) || "".equals(jsonObject.getString("status"))) {
            map.put("status", "");
        } else {
            map.put("status", jsonObject.optString("status", ""));
        }

        return map;

    }

}
