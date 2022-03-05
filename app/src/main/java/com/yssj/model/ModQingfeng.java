package com.yssj.model;

import android.content.Context;

import com.yssj.YUrl;
import com.yssj.YUrlQingfeng;
import com.yssj.entity.ReturnInfo;
import com.yssj.network.YConn;
import com.yssj.utils.EntityFactory;
import com.yssj.utils.EntityFactoryL;
import com.yssj.utils.EntityFactoryQingfeng;
import com.yssj.utils.ReadJsonFileUtils;
import com.yssj.utils.YCache;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModQingfeng {

    /****
     * 我的-小中心-系统
     */
    public static HashMap<String, String> getMessageCenterSystem(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlQingfeng.MYCENTERMESSAGESYSTEM, nameValuePairs);
        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null
                : EntityFactoryQingfeng.createMessageSystem(context, result);

    }

    /**
     * 精选推荐-分享美衣-商品
     */
    public static ArrayList<HashMap<String, String>> getJinPintuijianList(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlQingfeng.QUERYPRODUCTSRECOMMENDEDSHOP, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.createJingxuanList(context, result);
    }

    /**
     * 个人中心---照片
     *
     * @return
     */
    public static List<HashMap<String, Object>> getMyPic(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        String result = YConn.basedPost(context, YUrlQingfeng.CIRCLEIMAGEBROWSING, nameValuePairs);

        return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.createMyPic(context, result);
    }

    /**
     * 消息中心---话题
     *
     * @param context
     * @param page
     * @param history ：是否是历史消息
     * @return
     * @throws Exception
     */
    public static List<HashMap<String, Object>> getMessageHuaTi(Context context, Integer page, boolean history)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("curPage", page + ""));
        nameValuePairs.add(new BasicNameValuePair("history", history + ""));
        String result = YConn.basedPost(context, YUrlQingfeng.CENTERMESSAGEHUATI, nameValuePairs);
        // result =
        // "{\"message\":\"操作成功.\",\"count\":2,\"status\":\"1\",\"data\":[{\"_id\":{\"machine\":-1559199847,\"timeSecond\":1487830380,\"inc\":251895311,\"time\":1487830380000,\"new\":false},\"user_id\":174157,\"nickname\":\"1515@qq\",\"head_pic\":\"userinfo/head_pic/default.jpg\",\"theme_id\":76,\"num\":1,\"type\":1,\"date\":1487830380993,\"id\":2},{\"_id\":{\"machine\":-1559216981,\"timeSecond\":1487840000,\"inc\":254182094,\"time\":1487840000000,\"new\":false},\"user_id\":174553,\"nickname\":\"王炸\",\"head_pic\":\"/userinfo/head_pic/1469810588323.jpg\",\"theme_id\":159,\"num\":11,\"type\":1,\"date\":1487840000670,\"id\":9}]}";
        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactory.createCenterMessageHuaTi(context, result);

    }

    /**
     * 我的----帖子收藏列表
     */
    public static List<HashMap<String, Object>> getMyShouCangTiezi(Context context, String curPage) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("curPage", curPage + ""));
        nameValuePairs.add(new BasicNameValuePair("pageSize", "10"));
        String result = YConn.basedPost(context, YUrlQingfeng.MYCHOUCHANGLIET, nameValuePairs);
        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactory.createIntimateCollectList(context, result);

    }

    /**
     * 是否有疯狂星期一的任务
     */
    public static boolean getCrazyMod(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlQingfeng.HASCRAZYMOD, nameValuePairs);
        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.createHasMon(context, result);

    }


    /**
     * 是否有零元购
     */
    public static boolean getLingyuangou(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlQingfeng.HASCRAZYMOD, nameValuePairs);
        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.createLingyuangou(context, result);

    }

    /**
     * 点赞
     */
//    public static boolean getDianZan(Context context, boolean isFirst) throws Exception {
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
//        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
//        nameValuePairs.add(new BasicNameValuePair("isFirst", isFirst + ""));
//        String result = YConn.basedPost(context, YUrlQingfeng.DIANZAN, nameValuePairs);
//        // LogYiFu.e("TAG", result.toString());
//        return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.createDianZan(context, result);
//
//    }

    /**
     * 获取当前是否有未付款的订单 true:有  false没有
     */
    public static boolean getNotFUoder(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlQingfeng.GETNOTFUKUANORDER, nameValuePairs);
        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.ceaterNotfuOrder(context, result);

    }

    /**
     * 重置拼团提示
     */
    public static boolean restPintuanTishi(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlQingfeng.RESTPTTISHI, nameValuePairs);


        return true;
//		// LogYiFu.e("TAG", result.toString());
//		return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.ceaterNotfuOrder(context, result);

    }

    /**
     * 重置集赞
     */
    public static boolean getResJizan(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlQingfeng.RESJIZAN, nameValuePairs);
        // LogYiFu.e("TAG", result.toString());
        return true;

    }

    public static String getRandRoll(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlQingfeng.GETRANDROLL, nameValuePairs);
        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.createRandRoll(context, result);


    }

    public static String getZhongJiangCount(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlQingfeng.GETZHONGJIANGCOUNT, nameValuePairs);
        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.createRandRoll(context, result);


    }


    /**
     * 我的----话题和搭配
     * <p>
     * theme_type：穿搭 :2 话题:3
     */
    public static List<HashMap<String, Object>> getMyHuaTiAndChuanDa(Context context, int theme_type, String curPage)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("theme_type", theme_type + ""));
        nameValuePairs.add(new BasicNameValuePair("curPage", curPage + ""));
        nameValuePairs.add(new BasicNameValuePair("pageSize", "10"));
        String result = YConn.basedPost(context, YUrlQingfeng.MYHUAIANDCHUANDA, nameValuePairs);
        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null : EntityFactory.createIntimateList(context, result, "", "");

    }

    /**
     * 好友密友列表
     *
     * @param context
     * @return
     * @throws Exception
     */

    public static List<HashMap<String, Object>> getMyFridensList(Context context, String curPage, String type)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("curPage", curPage + ""));
        nameValuePairs.add(new BasicNameValuePair("pageSize", "10"));
        nameValuePairs.add(new BasicNameValuePair("type", type));
        String url = YUrlQingfeng.MYFRIENDSLIST;
        String result = YConn.basedPost(context, url, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.createMyFrendsList(context, result);
    }

    /**
     * 密友圈详情--更多相关推荐
     */
    public static List<HashMap<String, Object>> getFridendDetislMore(Context context, String theme_id, String curPage)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("theme_id", theme_id));
        nameValuePairs.add(new BasicNameValuePair("curPage", curPage + ""));
        nameValuePairs.add(new BasicNameValuePair("pageSize", "10"));
        String url = YUrlQingfeng.XIANGGUANTUIJIAN;
        String result = YConn.basedPost(context, url, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactory.createIntimateList(context, result, "", "");
    }


    /**
     * 文案统一在又拍云获取 带有颜色变化的文案------------分享赢提现额度专用
     *
     * @return
     * @throws Exception
     */
    public static List<String> getColorText() throws Exception {
        JSONObject jsonObject = ReadJsonFileUtils.ReadHJsonFile(YUrl.PAPERWORK_TEXT_CONTENT);
        return (jsonObject == null || "".equals(jsonObject)) ? null : EntityFactoryQingfeng.createColorTextContent(jsonObject);
    }


    /**
     * 往期揭晓——往期揭晓 就是往期揭晓的左边部分----拼团夺宝往期揭晓
     */
    public static List<HashMap<String, Object>> getPintuanDuobaoWQJX(Context context, String index) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));

        nameValuePairs.add(new BasicNameValuePair("sort", "btime"));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));

        nameValuePairs.add(new BasicNameValuePair("page", index + ""));

        String result = YConn.basedPost(context, YUrlQingfeng.PINTUANDUOBAOWANGQIJIEXIAO, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.createWqjx_left(context, result);
    }


    /**
     * 夺宝记录——我的参与记录----拼团夺宝
     */

    public static List<HashMap<String, Object>> SnatchJoin(Context context,
                                                           String index) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token",
                YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", index + ""));

        nameValuePairs.add(new BasicNameValuePair("sort", "add_time"));
        nameValuePairs.add(new BasicNameValuePair("order", "desc"));

        String result = YConn.basedPost(context, YUrlQingfeng.SNATCHJOIN,
                nameValuePairs);
        return (result == null || "".equals(result)) ? null :
                EntityFactoryQingfeng.createSnatchJoin(context, result);
    }


    /**
     * 微信登录绑定手机之后调用查询拼团夺宝任务有没有被引导
     */

    public static HashMap<String, String> getDuoBaoh5(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token",
                YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlQingfeng.PINTUANDUOBAOh5,
                nameValuePairs);
        return (result == null || "".equals(result)) ? null :
                EntityFactoryQingfeng.createDuoBaoh5(context, result);
    }


    /**
     * 微信登录绑定手机之后调用查询拼团夺宝任务有没有被引导
     */

    public static ReturnInfo updatePINtuanDUObao(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token",
                YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlQingfeng.PITUANDUOBAOUPDATEISG,
                nameValuePairs);

        return (result == null || "".equals(result)) ? null :
                EntityFactoryL.createRetInfo(context, result);

    }



    /**
     * app落地页切换 后台新增控制开关
     */
    public static boolean getHomePage(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlQingfeng.GEIHOMEPAGE, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.createDianZan(context, result);

    }

    /**
     * 是否显示赚钱   data //      0不隐藏 1隐藏
     */
    public static boolean getSign(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlQingfeng.GETHASSIGN, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.createHasSign(context, result);

    }


    /**
     * 一元购是否可以中奖   data //     0中1不中奖
     */
    public static boolean getOneBuyWhetherZhongjiang(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlQingfeng.ONEBUYGETZHONGJIANGSTATUS, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.createOneBuyzhongjiangStatus(context, result);

    }

    /**
     * 购物节标签列表
     */
    public static List<HashMap<String, String>> getCrazyTagList(Context context, String type) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        if (type != null) {
            nameValuePairs.add(new BasicNameValuePair("type", type));
        }
        String result = YConn.basedPost(context, YUrl.GET_TUIJIAN_DATA, nameValuePairs);
        return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.createCrazyShopTagList(context, result);
    }

    public static List<HashMap<String, Object>> getTixianedumingxi(Context context, Integer page, String type,
                                                                   String order, String sort) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        nameValuePairs.add(new BasicNameValuePair("page", page + ""));
        nameValuePairs.add(new BasicNameValuePair("type", type)); // 不给为查询所有
        // 1,新增额度2
        // 使用额度
        nameValuePairs.add(new BasicNameValuePair("order", order));
        nameValuePairs.add(new BasicNameValuePair("sort", sort));
        String result = YConn.basedPost(context, YUrlQingfeng.TIXIANZHONGSIGN, nameValuePairs);
        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null
                : EntityFactoryQingfeng.createTiXianEduLiebiaoDongjie(context, result);

    }
    /*
	 * 获取是否有抽奖转盘的任务
	 */
    public static boolean getHasHongbaoTask(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel2.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));


        String result = YConn.basedPost(context, YUrl.SIGN_LIST_LOGIN, nameValuePairs);


        return (result == null || "".equals(result)) ? null : EntityFactoryQingfeng.createHasHongbao(context, result);
    }

    /**
     * 次日任务预告弹窗  上的数量
     * @param context
     * @return
     * @throws Exception
     */
    public static HashMap<String, String> getTomoyugaoCount(Context context) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("version", ComModel.versionCode));
        nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
        String result = YConn.basedPost(context, YUrlQingfeng.CIRIYUGAOCOUNT, nameValuePairs);
        // LogYiFu.e("TAG", result.toString());
        return (result == null || "".equals(result)) ? null
                : EntityFactoryQingfeng.createCiriCount(context, result);

    }

}
