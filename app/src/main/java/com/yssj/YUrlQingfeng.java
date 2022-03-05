
package com.yssj;

public interface YUrlQingfeng {

    /****
     * 我的-小中心-系统
     */
    String MYCENTERMESSAGESYSTEM = YUrl.YSS_URL_ANDROID + "push/sysMsg";
    /****
     * 精选推荐
     */
    String QUERYPRODUCTSRECOMMENDEDSHOP = YUrl.YSS_URL_ANDROID + "like/queryProductsRecommendedShopAll";
    /****
     * 我的---照片
     */
    String CIRCLEIMAGEBROWSING = YUrl.YSS_URL_ANDROID + "fc/circleImageBrowsing";
    /****
     * 我的---消息中心--话题
     */
    String CENTERMESSAGEHUATI = YUrl.YSS_URL_ANDROID + "fc/getMessage";
    /****
     * 我的---话题和搭配
     */
    String MYHUAIANDCHUANDA = YUrl.YSS_URL_ANDROID + "fc/wearTopic";
    /****
     * 密友圈详情--更多相关推荐
     */
    String XIANGGUANTUIJIAN = YUrl.YSS_URL_ANDROID + "fc/subjectRecommend";
    /****
     * 密友圈-----好友密友列表接口
     */
    String MYFRIENDSLIST = YUrl.YSS_URL_ANDROID + "fc/queryFriendList";
    /****
     * 密友圈-----我的收藏
     */
    String MYCHOUCHANGLIET = YUrl.YSS_URL_ANDROID + "fc/queryCollectList";
    /**
     * 当天是否有疯狂星期一的任务
     */
    String HASCRAZYMOD = YUrl.YSS_URL_ANDROID + "signIn2_0/isMonday";
    /**
     * 集赞---点赞
     */
    String DIANZAN = YUrl.YSS_URL_ANDROID + "point/pointOfPraise";

    /**
     * 集赞---重置集赞
     */
    String RESJIZAN = YUrl.YSS_URL_ANDROID + "point/changePopup";
    /**
     * 获取当前是否有未付款的订单
     */
    String GETNOTFUKUANORDER = YUrl.YSS_URL_ANDROID + "order/getWaitPayCount";
    /**
     * 重置拼团提示
     */
    String RESTPTTISHI = YUrl.YSS_URL_ANDROID + "fightTeam/changeStatus";


    /**
     * 获取随机拼团编号
     */
    String GETRANDROLL = YUrl.YSS_URL_ANDROID + "order/getRandRoll";


    /**
     * 获取图片验证码
     */
    String GETCHECKGIF = YUrl.YSS_URL_ANDROID + "vcode/getVcode";

    /**
     * 获取图片验证码（使用手机修改支付密码）
     */
    String GETCHECKGIFPWD = YUrl.YSS_URL_ANDROID + "vcode/getVcodePwd";


    /**
     * 获取用户夺宝中奖额度
     */
    String GETZHONGJIANGCOUNT = YUrl.YSS_URL_ANDROID + "treasures/getTreaExtract";

    /**
     * 拼团夺宝-往期揭晓
     */
    String PINTUANDUOBAOWANGQIJIEXIAO = YUrl.YSS_URL_ANDROID + "rollTrea/getPast";


    /**
     * 夺宝记录——参与记录----拼团夺宝
     */
    String SNATCHJOIN = YUrl.YSS_URL_ANDROID + "rollTrea/getMyPation";




    /**
     *  微信登录绑定手机之后调用查询拼团夺宝任务有没有被引导
     */
    String PINTUANDUOBAOh5 = YUrl.YSS_URL_ANDROID + "rollTrea/queryISG";
    /**
     * 拼团夺宝修改参与状态。 掉完参团接口再掉这个
     */
    String PITUANDUOBAOUPDATEISG = YUrl.YSS_URL_ANDROID + "rollTrea/updateISG";


    /**
     *     控制app落地页开关返回data 1为首页 2为赚钱任务页 默认为2,需登录
     */
    String GEIHOMEPAGE = YUrl.YSS_URL_ANDROID + "cfg/landingPage";


    /**
     * 赚钱是否隐藏赚钱     data //      0不隐藏 1隐藏
      */
    String GETHASSIGN = YUrl.YSS_URL_ANDROID + "cfg/getlandingPage8778";

    /**
     * 提现中..
     */
    String TIXIANZHONGSIGN = YUrl.YSS_URL_ANDROID + "wallet/queryWaitDeposit";

    /**
     * 次日任务预告弹窗
     */
    String CIRIYUGAOCOUNT = YUrl.YSS_URL_ANDROID + "signIn2_0/queryNextTask";
    /**
     * 1元购获取是否能中奖
     */
    String ONEBUYGETZHONGJIANGSTATUS = YUrl.YSS_URL_ANDROID + "order/getOrderRaffleOrNotPrize";

}
