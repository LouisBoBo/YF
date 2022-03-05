package com.yssj.model;


import android.content.Context;

import com.yssj.YUrl;
import com.yssj.YUrlZ;
import com.yssj.network.YConn;
import com.yssj.utils.EntityFactory;
import com.yssj.utils.EntityFactoryL;
import com.yssj.utils.EntityFactoryZ;
import com.yssj.utils.ReadJsonFileUtils;
import com.yssj.utils.YCache;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

public class ComModelZ {
    public static final String versionCode = ComModel.versionCode;


    /**
     * 文案统一在又拍云获取 ------------正常夺宝夺宝规则
     *
     * @return
     * @throws Exception
     */
    public static List<String> getIndianaRuleText(String key) throws Exception {
        JSONObject jsonObject = ReadJsonFileUtils.ReadHJsonFile(YUrl.PAPERWORK_TEXT_CONTENT);
        return (jsonObject == null || "".equals(jsonObject)) ? null : EntityFactoryZ.createIndianaTextContent(jsonObject, key);
    }

    /**
     * 额度夺宝中的往期幸运星
     */
    public static List<HashMap<String, Object>> getIndianaOldWinName(Context context, String index, String pt) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        nameValuePairs.add(new BasicNameValuePair("sort", "otime"));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));

        nameValuePairs.add(new BasicNameValuePair("page", index + ""));
        nameValuePairs.add(new BasicNameValuePair("pt", pt));
        String result = YConn.basedPost(context, YUrl.Wqjx_left, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createWqjx_left(context, result);
    }

    /****
     * 拼团夺宝详情------参与记录列表
     */
    public static List<HashMap<String, Object>> queryGroupIndianaTakeRecord(Context context, String shop_code,
                                                                            String issue_code, String page, String rows) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("issue_code", issue_code));
        nameValuePairs.add(new BasicNameValuePair("page", page));
        nameValuePairs.add(new BasicNameValuePair("rows", rows));
        String result = YConn.basedPost(context, YUrlZ.GROUP_DETAILS_TAKE_RECORD, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        List<HashMap<String, Object>> list = EntityFactoryZ.createGroupIndianaTakeRecord(result);

        return list;
    }


    /****
     * 拼团夺宝详情------拼团详情列表
     */
    public static List<HashMap<String, Object>> queryGroupIndianaGroupList(Context context, String shop_code,
                                                                           String issue_code, String page, String rows, int is_ago) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("issue_code", issue_code));
        nameValuePairs.add(new BasicNameValuePair("page", page));
        nameValuePairs.add(new BasicNameValuePair("rows", rows));
        nameValuePairs.add(new BasicNameValuePair("is_ago", "" + is_ago));
        String result = YConn.basedPost(context, YUrlZ.GROUP_DETAILS_GROUP_LIST, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        List<HashMap<String, Object>> list = EntityFactoryZ.createGroupList(context, result);

        return list;
    }

    /**
     * 文案统一在又拍云获取 ------------拼团购文案
     *
     * @return
     * @throws Exception
     */
    public static HashMap<String, String> getGroupContent() throws Exception {
        JSONObject jsonObject = ReadJsonFileUtils.ReadHJsonFile(YUrl.PAPERWORK_TEXT_CONTENT);
        return (jsonObject == null || "".equals(jsonObject)) ? null : EntityFactoryZ.createGroupContent(jsonObject);
    }

    /**
     * 文案统一在又拍云获取 ------------分享随机标题文案
     *
     * @return
     * @throws Exception
     */
    public static HashMap<String, String> getShareTitleContent() throws Exception {
        JSONObject jsonObject = ReadJsonFileUtils.ReadHJsonFile(YUrl.PAPERWORK_TEXT_CONTENT);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("title", "${replace}品牌商出品，拼团仅售${replace}元，快来一起拼");//${replace}品牌${replace}，仅售${replace}元，次月返${replace}元，等于0元！
        map.put("text", "");
        return (jsonObject == null || "".equals(jsonObject)) ? map : EntityFactoryZ.createShareTitleContent(jsonObject);
    }

    /**
     * 文案统一在又拍云获取 ------------分享随机标题文案
     *
     * @return
     * @throws Exception
     */
    public static HashMap<String, String> getNewShareTitleContent() throws Exception {
        JSONObject jsonObject = ReadJsonFileUtils.ReadHJsonFile(YUrl.PAPERWORK_TEXT_CONTENT);
        HashMap<String, String> map = new HashMap<String, String>();
        return (jsonObject == null || "".equals(jsonObject)) ? map : EntityFactoryZ.createShareTitleContentNew(jsonObject);
    }

    /**
     * 文案统一在又拍云获取 ------------1分钱夺宝分享标题文案
     *
     * @return
     * @throws Exception
     */
    public static HashMap<String, HashMap<String, Object>> getIndianaShareText() throws Exception {
        JSONObject jsonObject = ReadJsonFileUtils.ReadHJsonFile(YUrl.PAPERWORK_TEXT_CONTENT);
        return (jsonObject == null || "".equals(jsonObject)) ? null : EntityFactoryZ.createIndianaTextContent(jsonObject);
    }

    /****
     * 拼团夺宝详情------拼团详情列表
     */
    public static HashMap<String, String> autoWithDrawUUID(Context context, String name,
                                                           String openid) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("u_name", name));
        nameValuePairs.add(new BasicNameValuePair("openid", openid));
        String result = YConn.basedPost(context, YUrlZ.AURO_WITHDRAW, nameValuePairs);
        if (result == null || "".equals(result)) {
            return null;
        }
        HashMap<String, String> map = EntityFactoryZ.createAutoWithDraw(result);

        return map;
    }


    /**
     * 文案统一在又拍云获取 ------------分享拼团夺宝
     *
     * @return
     * @throws Exception
     */
    public static HashMap<String, String> getShareGroupTitleContent() throws Exception {
        JSONObject jsonObject = ReadJsonFileUtils.ReadHJsonFile(YUrl.PAPERWORK_TEXT_CONTENT);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("title", "仅剩${replace}人，快来，我免费领到了${replace}");//
        map.put("text", "${replace}已经开团，仅剩${replace}人，全场0元，成团即发。马上来参团吧。");
        return (jsonObject == null || "".equals(jsonObject)) ? map : EntityFactoryZ.createShareGroupTitleContent(jsonObject);
    }

    /****
     * 根据商品编号获取2级类目id和品牌id
     */
    public static HashMap<String, String> geType2SuppLabe(Context context, String shop_code) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        String result = YConn.basedPost(context, YUrlZ.getType2Supplabel, nameValuePairs);
        if (result == null || "".equals(result)) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("type2", "");
            map.put("supp_label_id", "衣蝠");
            return map;
        }
        HashMap<String, String> map = EntityFactoryZ.createType2SuppLabel(result,context);

        return map;
    }


    /**
     * 文案统一在又拍云获取 ------------次日任务预告
     *
     * @return
     * @throws Exception
     */
    public static HashMap<String, String> getNextDayTaskContent() throws Exception {
        JSONObject jsonObject = ReadJsonFileUtils.ReadHJsonFile(YUrl.PAPERWORK_TEXT_CONTENT);
        return (jsonObject == null || "".equals(jsonObject)) ? null : EntityFactoryZ.createNextDayTakContent(jsonObject);
    }
}
