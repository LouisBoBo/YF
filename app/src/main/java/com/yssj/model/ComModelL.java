package com.yssj.model;

import android.content.Context;

import com.yssj.Json;
import com.yssj.YUrl;
import com.yssj.YUrlL;
import com.yssj.YUrlQingfeng;
import com.yssj.entity.ReturnInfo;
import com.yssj.network.YConn;
import com.yssj.utils.EntityFactory;
import com.yssj.utils.EntityFactoryL;
import com.yssj.utils.ReadJsonFileUtils;
import com.yssj.utils.YCache;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/12.
 */

public class ComModelL {
    public static final String versionCode = ComModel.versionCode;


//    /**
//     * 新的拼团详情 邀请好友参团分享  获取文案
//     *
//     */
//    public static List<HashMap<String,String>> getGroupsShareContent(Context context)
//            throws Exception {
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
//        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
//        String result = YConn.basedPost(context, YUrl.POINT_SHARE_CONTENT, nameValuePairs);
//        return (result == null || "".equals(result)) ? null : EntityFactoryL.createGroupsShareContent(context, result);
//    }

    /**
     * 文案统一在又拍云获取
     * @return
     * @throws Exception
     */
    public static HashMap<String , HashMap<String , Object>> getSpellGroupsText()throws Exception{
        JSONObject jsonObject = ReadJsonFileUtils.ReadHJsonFile(YUrl.PAPERWORK_TEXT_CONTENT);
        return (jsonObject == null || "".equals(jsonObject)) ? null : EntityFactoryL.createSpellGroupsTextContent(jsonObject);
    }

    /**
     * 文案统一在又拍云获取
     * @return
     * @param key json文件中的相关文案 键值
     * @throws Exception
     */
    public static HashMap<String , Object> getContentText(String key)throws Exception{
        JSONObject jsonObject = ReadJsonFileUtils.ReadHJsonFile(YUrl.PAPERWORK_TEXT_CONTENT);
        return (jsonObject == null || "".equals(jsonObject)) ? null : EntityFactoryL.createTextContent(jsonObject,key);
    }

    /**
     * 文案统一在又拍云获取
     * 集赞奖励活动规则文案
     * @return
     * @param
     * @throws Exception
     */
    public static  HashMap<String, String> getContentTextJZJL(String key)throws Exception{
        JSONObject jsonObject = ReadJsonFileUtils.ReadHJsonFile(YUrl.PAPERWORK_TEXT_CONTENT);
        return (jsonObject == null || "".equals(jsonObject)) ? null : EntityFactoryL.createTextContentJZJL(jsonObject,key);
    }
    /**
     * 文案统一在又拍云获取 带有颜色变化的文案
     * @return
     * @param key json文件中的相关文案 键值
     * @throws Exception
     */
    public static HashMap<String , Object> getColorText(String key)throws Exception {
        JSONObject jsonObject = ReadJsonFileUtils.ReadHJsonFile(YUrl.PAPERWORK_TEXT_CONTENT);
        return (jsonObject == null || "".equals(jsonObject)) ? null : EntityFactoryL.createColorTextContent(jsonObject,key);
    }

    /**
     * 动态获取分享链接H5域名
     * @param context
     * @return
     * @throws Exception
     */
    public static String getAndroidH5Url(Context context)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        String result = YConn.basedPost(context, YUrl.GETDOMAIN, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryL.createDomain(context, result);
    }


    /**
     * 获取图形验证码 加密URL
     * @param context
     * @param phone
     * @param imei
     * @return 加密图片验证码的URL
     * @throws Exception
     */
    public static String getPhoneCode(Context context,String phone,String imei)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("phone", phone));
        nameValuePairs.add(new BasicNameValuePair("imei", imei));
        return  YConn.getCheckGifCode(context, YUrlQingfeng.GETCHECKGIF, nameValuePairs);
    }

    /**
     * 获取图形验证码 加密URL（修改支付密码）
     * @param context
     * @param imei
     * @return 加密图片验证码的URL
     * @throws Exception
     */
    public static String getPhoneCodePWD(Context context,String imei)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
//        nameValuePairs.add(new BasicNameValuePair("phone", phone));
        nameValuePairs.add(new BasicNameValuePair("imei", imei));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        return  YConn.getCheckGifCode(context, YUrlQingfeng.GETCHECKGIFPWD, nameValuePairs);
    }


    /**
     * 拼团夺宝 开团和参团
     * @param context
     * @param shop_code 参团商品
     * @param type 1开团2参团 默认1
     * @param tuserId 团长用户id（可选）当type==2 时必选
     * @return
     * @throws Exception
     */
    public static ReturnInfo getRollTrea(Context context,String shop_code,String type,String tuserId)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("shop_code", shop_code));
        nameValuePairs.add(new BasicNameValuePair("type", type));
        if("2".equals(type)){
            nameValuePairs.add(new BasicNameValuePair("tuserId", tuserId));
        }
        String result = YConn.basedPost(context, YUrlL.INDIANAGROUPSROLLTREA, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryL.createRetInfo(context, result);
    }

    /**
     * 获取抽奖结果 免费抽余额红包
     */
    public static HashMap<String, String> getBalanceLuckResult(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlL.DORAFFLE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryL.createBalanceResult(context, result);
    }
    /**
     * 获取抽奖结果 免费抽余额红包
     */
    public static HashMap<String, String> getBalanceLuckNum(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlL.ORDERRAFFLENUM, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryL.createBalanceResult(context, result);
    }

    /**
     *用户是否绑定提现微信
     * @param context
     * @return
     * @throws Exception
     */
    public static HashMap<String, String> getWxIsBind(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlL.GETWXOPENIDISBIND, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryL.createWxIsBind(context, result);
    }


    /**
     * 获取抽奖结果
     */
    public static HashMap<String, String> getRaffleNum (Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlL.GET_RAFFLE_NUM, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryL.createRaffleNumResult(context, result);
    }

    /**
     * 好友提成系统 分享次数统计
     */
    public static ReturnInfo fcAddShareCount(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlL.FC_ADDSHARECOUNT, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryL.createRetInfo(context, result);
    }

    /**
     * 好友提成系统 今日奖励弹窗
     */
    public static HashMap<String, Object> getTadayPraiseMoney(Context context)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlL.GETTCTODAYCOUNT, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryL.createTodayPraiseMoney(context, result);
    }
    /**
     * 赚钱任务弹窗0元购文案返现金额
     */
    public static HashMap<String, Object> getZeroCount(Context context)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlL.GETZEROCOUNT, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryL.createZeroCount(context, result);
    }
}
