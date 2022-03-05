
package com.yssj;

public class YUrl {


// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓正式专用↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    public static boolean debug = true;//测试debug模式 打印日志（上线改成false）
    public static boolean wxMiniDedug = false;//客服专用
//    public static String YSS_URL_ANDROID = "http://52yifu.com/cloud-api/";
//    public static String YSS_URL_ANDROID_NEW = "http://52yifu.com/cloud-pay/";
//    public static String YSS_URL_ANDROID_H5 = "http://www.52yifu.com/";

    public static String YSS_URL_ANDROID = "http://123.58.33.33/cloud-api/";
    public static String YSS_URL_ANDROID_NEW = "http://123.58.33.33/cloud-pay/";
    public static String YSS_URL_ANDROID_H5 = "http://123.58.33.33/";

//    public static String imgurl = "https://www.incursion.wang/";
    public static String imgurl = "http://img.yiline.com/";
    public static String TEST_API_KEY = "M3s2N+dRDbjzzRATbBP5IbYIThc=";
    public static String BUCKET = "yssj668";
    public static final String APP_SECRET = "10d080a714d768427242e9b091d33959";
    public static final String API_KEY = "B9CB71EC2BAE087D0B9A37BDFABD328D";
    public static final String MCH_ID = "1265692601";
    public static String APP_ID = "wx8c5fe3e40669c535";
    public static final String WX_MINIAPP_ORIGINAL_ID = "gh_05d342ef9932";//小程序原始id（写死）
    public static final String WX_MINIAPP_ID = "wxc211367f634ba3e9";//小程序app_id（写死）


// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑正式专用↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
/**
 * ===================================================分割线=======================================================================
 */

// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓测试专用↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

//    public static boolean debug = true;//测试debug模式 打印日志
//    public static boolean wxMiniDedug = true;//
//    public static String TEST_API_KEY = "T8CDRaESN017je6QcRjYqmjxXkw="; // 正式使用的表单api验证密钥
//    public static String BUCKET = "yssj-real-test"; // 存储空间
////    public static String YSS_URL_ANDROID = "http://www.52yifu.wang/cloud-app/";
////    public static String YSS_URL_ANDROID_NEW = "http://www.52yifu.wang/cloud-pay/";
//
//    public static String YSS_URL_ANDROID = "http://123.58.33.33/cloud-app/";
//    public static String YSS_URL_ANDROID_NEW = "http://123.58.33.33/cloud-pay/";
//    public static String YSS_URL_ANDROID_H5 = "http://www.52yifu.wang/";
//    public static String imgurl = "https://www.measures.wang/";
//    public static final String WX_MINIAPP_ORIGINAL_ID = "gh_01f3abb24f0b";//小程序原始id（写死）
//    public static final String WX_MINIAPP_ID = "wx79b8d262f2563c52";//小程序app_id（写死）
//    public static String APP_ID = "wxbb9728502635a425";
//    public static String MCH_ID = "1265692601";
//    public static String API_KEY = "B9CB71EC2BAE087D0B9A37BDFABD328D";
//    public static String APP_SECRET = "d4624c36b6795d1d99dcf0547af5443d";


// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑测试专用↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


    /***
     * 外网测试
     */
    public static String ALI_NOTIFY_URL_MULTI = "alipay/appNotifyList";
    public static String ALI_NOTIFY_URL_SINGLE = "alipay/appNotify";
    public static String ALI_NOTIFY_URL_RED = "alipay/appNotifyHb";

    /***
     * apk 下载地址
     */
//    public static String apkUrl = "http://yssj-app.b0.upaiyun.com/";

    /***
     * 下载文件安装
     */
    public static String GET_FILE = YSS_URL_ANDROID + "/updateApp";
    /***
     * 邮箱注册
     */
    public static String ACCOUNT_EMAIL_SIGNUP = YSS_URL_ANDROID + "user/register";

    /***
     * 发送手机验证码
     */
    public static String GET_PHONE_CODE = YSS_URL_ANDROID + "user/get_phone_code";
    /***
     * 更换绑定手机获取验证码
     */
    public static String GET_OLD_PHONE_CODE = YSS_URL_ANDROID + "user/old_code";
    /****
     * 发送email验证码
     */
    public static String GET_EMAIL_CODE = YSS_URL_ANDROID + "user/get_email_code";

    /***
     * 手机 注册
     */
    public static String ACCOUNT_SIGNUP = YSS_URL_ANDROID + "user/register";

    /***
     * 邮箱忘记密码处重置密码
     */
    public static String RESET_PASS_4_FORGET_EMAIL = YSS_URL_ANDROID + "user/getRetrievePwdEmail";

    /***
     * 手机忘记密码处重置密码
     */
    public static String RESET_PASS_4_FORGET_PHONE = YSS_URL_ANDROID + "user/getRetrievePwd";

    /****
     * 个人中心统计
     */
    public static String PERSON_CENTER_COUNT = YSS_URL_ANDROID + "user/count";


    /****
     *首张钻石卡购买成功后转盘的弹窗
     */
    public static String FIRST_ZUANSHI_ZHUANPAIN_TISHI = YSS_URL_ANDROID + "userVipCard/selVipIsPopup";


    /**
     * 会员卡列表
     */
    public static String VIP_LIST = YSS_URL_ANDROID + "userVipType/queryByVipList";

    /**
     * 会员卡开通或补卡下单
     */
    public static String VIP_PAY_URL = YSS_URL_ANDROID + "userVipCard/addUserVipCard";
    /**
     * 拼手速抽奖花钱买次数查询可以买的次数
     */
    public static String QUERY_CJ_BUY_COUNT = YSS_URL_ANDROID + "order/vipFreeDrawData";
    /**
     * 拼手速抽奖购买抽奖次数下单
     */
    public static String SUBMIT_CJ_BUY_COUNT = YSS_URL_ANDROID + "order/buyVipFreeLuckyDraw";
    /***
     * 手机登陆
     */
    public static String ACCOUNT_LOGIN_PHONE = YSS_URL_ANDROID + "user/phoneLogin";// 作废

    /***
     * 邮箱登陆
     */
    public static String ACCOUNT_LOGIN_EMAIL = YSS_URL_ANDROID + "user/emailLogin";// 作废

    /***
     * 各种开关
     */
    public static String CONFIG_SWITCH = YSS_URL_ANDROID + "cfg/config_switch";

    /**
     * 审核地理位置验证
     */
    public static String CHECK_SHENHE_LOCATION= YSS_URL_ANDROID + "cfg/locationCheck";
    /***
     * 登陆接口
     */
    public static String ACCOUNT_LOGIN = YSS_URL_ANDROID + "user/login";

    /****
     * 第三方登陆
     */
    public static String ACCOUNT_LOGIN_THIRD = YSS_URL_ANDROID + "user/userLogin";
    /***
     * 得到帮助列表
     */
    public static String GET_HELP_LIST = YSS_URL_ANDROID + "help/goHelp";

    /**
     * ]客服系统自动提醒热门问题
     */
    public static String GET_HELP_CENTRE = YSS_URL_ANDROID + "help/queryHotQuestion";

    /***
     * \\\ 得到帮助详情
     */
    public static String GET_HELP_QUESTION = YSS_URL_ANDROID + "help/questionOne";
    /***
     * 得到订单列表
     */
    public static String GET_ORDER_LIST = YSS_URL_ANDROID + "order/getOrder";
    /****
     * 得到我的店铺订单管理
     */
    public static String GET_ORDER_MYSHOP_LIST = YSS_URL_ANDROID + "shopStore/orderManage";
    /***
     * 得到资金流水列表
     */
    public static String GET_FUND_LIST = YSS_URL_ANDROID + "wallet/findFundDetail";

    /***
     * 得到卡券列表--似乎已废弃
     */
    public static String GET_COUPON_LIST = YSS_URL_ANDROID + "wallet/findMyCoupon";
    /***

     */
    public static String QUERYBYPAGE = YSS_URL_ANDROID + "coupon/queryByPage";

    /**
     * 查询用户打卡进度奖励信息
     */
    public static String QUERY_NEW_WALLET_DATA = YSS_URL_ANDROID + "clockInProgress/userProgressData";
    /**
     * 用户领取打卡进度奖励
     */
    public static String LINGQU_NEW_WALLET_DATA = YSS_URL_ANDROID + "clockInProgress/grantProgress";
    /***

     */
    public static String GET_MY_FAVOUR_LIST = YSS_URL_ANDROID + "like/selLike";

    public static String GET_SHARE_IMG = YSS_URL_ANDROID + "sysimg/getShareImg";
    /***
     * 我的最爱列表数据是否置顶
     */
    public static String GET_MY_FAVOUR_IS_SET_TOP = YSS_URL_ANDROID_H5 + "like/upLike";
    /***
     * 得到我的银行卡列表
     */
    public static String GET_MY_CARD_LIST = YSS_URL_ANDROID + "wallet/findMyBankCard";
    /***
     * 检测更新
     */
    public static String GET_VERSION = YSS_URL_ANDROID + "getVersion";
    /***
     * 用户反馈
     */
    public static String FEEDBACK = YSS_URL_ANDROID + "user/addUserFeedBackInfo";
    /***
     * 登陆设备列表
     */
    public static String LOGINDEVICE_LIST = YSS_URL_ANDROID + "loginRecord/loginList";
    /***
     * 历史登陆
     */
    public static String LOGIN_RECORD = YSS_URL_ANDROID + "loginRecord/lastLogin";
    /***
     * 注销登录
     */

    public static String LOGOUT = YSS_URL_ANDROID + "user/loginout";

    /****
     * 第三方注销登陆
     */
    public static String LOGOUT_THIRD = YSS_URL_ANDROID + "user/userLoginout";

    /****
     * 设置处重置密码
     */
    public static String RESET_PASS_4_SETTING = YSS_URL_ANDROID + "user/updatePwd";

    /****
     * 我的钱包
     */
    public static String MY_WALLET_INFO = YSS_URL_ANDROID + "wallet/myWallet";

    /****
     * 同步商品属性
     */
    public static String SYNC_DATAS = YSS_URL_ANDROID + "shop/queryTA";

    /****
     * 获取我的积分信息wallet/getIntegral
     */
    public static String GET_MY_INTEGRAL_INFO = YSS_URL_ANDROID + "wallet/getIntegral";

    /****
     * 获取积分使用记录
     */
    public static String GET_MY_INTEGRAL_LIST = YSS_URL_ANDROID + "wallet/queryIntegralList";

    /****
     * 每日签到
     */
    public static String DAILY_SIGN = YSS_URL_ANDROID + "wallet/everydaySign";
    /****
     * 通过登录密码设置支付密码
     */
    public static String RESET_PAY_PAYPASS_BY_LOGINPASS = YSS_URL_ANDROID + "wallet/upWalletPwd";
    /****
     * 通过手机设置支付密码
     */
    public static String RESET_PAY_PAYPASS_BY_PHONE = YSS_URL_ANDROID + "wallet/upWalletPwdBySms";
    /****
     * 通过手机设置支付密码
     */
    public static String RESET_PAY_PAYPASS_BY_EMAIL = YSS_URL_ANDROID + "wallet/upWalletPwdByEmail";
    /****
     * 积分商城通过积分兑换
     */
    public static String EXCHANGE_GOOD = YSS_URL_ANDROID + "inteOrder/integralPayOrder";
    /****
     * 主界面获取商品列表
     */
    public static String GET_PRODUCT_LIST = YSS_URL_ANDROID + "shop/queryCondition";
    /**
     * 获取店铺美衣商品列表
     */
    public static String GET_SHOP_BEAUTY = YSS_URL_ANDROID + "shop/queryShop";
    /****
     * 主界面获取商品列表 未登录状态
     */
    public static String GET_PRODUCT_LIST2 = YSS_URL_ANDROID + "shop/queryConUnLogin";

    /**
     * 个人中心邀请好友
     */
    public static String GET_INVITE_FRIEND = YSS_URL_ANDROID + "user/inviteFriend";

    /****
     * 主界面获取商品列表
     */
    public static String GET_QRCODE = YSS_URL_ANDROID + "shop/birth_qr";
    /****
     * 查询商品详情
     */
    public static String QUERY_SHOP_DETAILS = YSS_URL_ANDROID + "shop/query";
    /**
     * 夺宝详情
     */
    public static String IADIANA_SHOP_DETAILS = YSS_URL_ANDROID + "shop/queryIndiana";
    /****
     * 查询参与记录
     */
    public static String QUERY_TAKE_RECORD = YSS_URL_ANDROID + "treasures/getParticipationMan";
    /****
     * 查询参与记录 new
     */
    public static String QUERY_TAKE_RECORD_NEW = YSS_URL_ANDROID + "treasures/getParticipationManPic";
    /****
     * 查询商品详情 未登录状态
     */
    public static String QUERY_SHOP_DETAILS2 = YSS_URL_ANDROID + "shop/queryUnLogin";

    /**
     * 查询晒单详情
     */
    public static String QUERY_SHAIDAN_DETIALS = YSS_URL_ANDROID + "shareOrder/selComment";

    /**
     * 查询晒单评论列表
     */
    public static String QUERY_SHAIDAN_DETIALS_COMMENT = YSS_URL_ANDROID + "shareOrder/replyList";

    /**
     * 发表晒单评论
     */
    public static String ADD_SHAIDAN_COMMENT = YSS_URL_ANDROID + "shareOrder/replyComment";

    /**
     * 晒单详情点赞
     */
    public static String ADD_SHAIDAN_CLICK = YSS_URL_ANDROID + "shareOrder/addClick";
    /****
     * 查看商品所有评论
     */
    public static String QUERY_SHOP_EVALUATE = YSS_URL_ANDROID + "shopComment/selCommentByShop";
    /****
     * 添加商品到购物车
     */
    public static String JOIN_SHOP_SHOPCART = YSS_URL_ANDROID + "shopCart/add";

    /****
     * 查询购物车商品列表信息
     */
    public static String QUERY_SHOP_SHOPCART = YSS_URL_ANDROID + "shopCart/queryList";

    /****
     * 查询购物车商品列表信息（包括特卖和普通）
     */
    public static String QUERY_SHOP_SHOPCART_ALL = YSS_URL_ANDROID + "/shopCart/queryCart";

    /**
     * 重新加入
     */
    public static String NEW_JOIN_SHOP_SHOPCART = YSS_URL_ANDROID + "shopCart/reenter";
    /**
     * 获取购物车下架列表
     */
    public static String GET_SHOPCART_INVALID_LIST = YSS_URL_ANDROID + "/shopCart/checkShopList";
    /**
     * 余额翻倍
     */
    public static String DOUBLE_BALANCE = YSS_URL_ANDROID + "wallet/openTfn";

    /****
     * 特卖查询购物车商品列表信息
     */
    public static String QUERY_SHOP_SHOPCART_SPECIAL = YSS_URL_ANDROID + "shopCart/queryPList";

    /****
     * 删除购物车商品列表信息
     */
    public static String DELETE_SHOP_SHOPCART = YSS_URL_ANDROID + "shopCart/del";

    /****
     * 特卖删除购物车商品列表信息
     */
    public static String DELETE_SHOP_SHOPCART_SPECIAL = YSS_URL_ANDROID + "shopCart/delP";

    /****
     * 修改商品到购物车
     */
    public static String UPDATE_SHOP_SHOPCART = YSS_URL_ANDROID + "shopCart/update";
    /****
     * 特卖修改商品到购物车
     */
    public static String UPDATE_SHOP_SHOPCART_SPECIAL = YSS_URL_ANDROID + "shopCart/updatepackage";

    /****
     * 查询商品属性库存
     */
    public static String QUERY_SHOP_ATTR = YSS_URL_ANDROID + "shop/queryAttr";

    /****
     * 查询商品属性库存
     */
    public static String QUERY_INTESHOP_ATTR = YSS_URL_ANDROID + "inteShop/queryStock";

    /****
     * 积分商城列表
     */
    public static String GET_INTE_PROD_LIST = YSS_URL_ANDROID + "inteShop/queryCondition";

    /****
     * 获取地址列表
     */
    public static String GET_DELIVERY_ADDR = YSS_URL_ANDROID + "address/queryall";
    /****
     * 获取默认的收货地址
     */
    public static String GET_DEFAULT_DELIVERY_ADDR = YSS_URL_ANDROID + "address/queryDefault";
    /****
     * 添加地址
     */
    public static String ADD_DELIVERY_ADDR = YSS_URL_ANDROID + "address/insert";
    /****
     * 修改地址
     */
    public static String UPDATE_DELIVERY_ADDR = YSS_URL_ANDROID + "address/update";
    /****
     * 删除收货地址
     */
    public static String DELETE_DELIVERY_ADDR = YSS_URL_ANDROID + "address/delete";

    /****
     * 添加商品到我的最爱
     */
    public static String ADD_MY_LIKE = YSS_URL_ANDROID + "like/addLike";
    /*****
     * 删除我最爱的商品
     *
     */
    public static String DELETE_MY_LIKE = YSS_URL_ANDROID + "like/delLike";

    /****
     * 设置我的喜好
     */
    public static String SET_MY_HOBBY = YSS_URL_ANDROID + "user/update_userinfo";

    /****
     * 获取积分商城的商品详情
     */
    public static String GET_INTEGRAL_GOOD_DETAIL = YSS_URL_ANDROID + "inteShop/query";

    /****
     * 申请退换货
     */
    public static String RETURN_SHOP_ADD = YSS_URL_ANDROID + "returnShop/add";

    /****
     * 申请套餐退换货
     */
    public static String RETURN_MEAL_SHOP_ADD = YSS_URL_ANDROID + "returnShop/addZero";
    /****
     * 夺宝申请退款
     */
    public static String RETURN_DUOBAO_SHOP_ADD = YSS_URL_ANDROID + "treasures/addReturn";
    /****
     * 查看退换货
     */
    public static String RETURN_SHOP_REQUERY = YSS_URL_ANDROID + "/returnShop/queryById";
    /****
     * 撤销售后，取消退货/退款、换货等
     */
    public static String ESCRETURN = YSS_URL_ANDROID + "returnShop/escReturn";
    /****
     * 删除订单
     */

    public static String DELETE_ORDER = YSS_URL_ANDROID + "order/delOrder";

    /****
     * 取消订单
     */

    public static String CANCLE_ESCORDER = YSS_URL_ANDROID + "order/escOrder";
    /****
     * 夺宝 取消订单
     */

    public static String CANCLE_ESCORDER_DUO = YSS_URL_ANDROID + "treasures/escOrder";

    /****
     * 根据订单编号来确认收货
     */
    public static String AFFIRMORDER_ORDER = YSS_URL_ANDROID + "order/affirmOrder";

    /****
     * 根据订单商品来确认收货
     */
    public static String AFFIRMORDER_ORDER_SHOP = YSS_URL_ANDROID + "order/affirmOrdershop";
    /****
     * 延长收货
     */
    public static String EXTENSIOON_ORDER = YSS_URL_ANDROID + "order/extension";

    /****
     * 提交评论
     */
    public static String ADD_SHOP_CONMMENT = YSS_URL_ANDROID + "shopComment/addShopComment";
    /****
     * 追加评论评论
     */
    public static String APPEND_SHOP_CONMMENT = YSS_URL_ANDROID + "shopComment/appendCommentList";
    /**
     * 平台介入
     */
    public static String APPLY_PLATFORM = YSS_URL_ANDROID + "returnShop/intervene";
    /**
     * 夺宝评价订单
     */
    public static String INDIANA_COMMENT = YSS_URL_ANDROID + "shareOrder/addComment";
    /****
     * 多订单提交评论
     */
    public static String ADD_SHOP_CONMMENT_LIST = YSS_URL_ANDROID + "shopComment/addShopCommentList";
    /****
     * 追加评论
     */

    public static String APPEND_COMMENT = YSS_URL_ANDROID + "shopComment/appendComment";

    /***
     * 审核追加的评论
     */

    public static String VERIFY_APPEND_SHOPCOMMENT = YSS_URL_ANDROID + "shopComment/verifyAppend";
    /***
     * 修改退换货
     */

    public static String UPADTE_RETURNSHOP = YSS_URL_ANDROID + "returnShop/update";
    /***
     * 退货货时添加物流信息
     */
    public static String ADDLOGISTICS_RETURNSHOP = YSS_URL_ANDROID + "returnShop/addLogistics";
    /****
     * 查看退货货
     */

    public static String QUERYBID_RETURNSHOP = YSS_URL_ANDROID + "returnShop/queryById";

    /****
     * 通过我的钱包来付款单个订单
     */
    public static String WALLET_PAYORDER = YSS_URL_ANDROID + "order/walletPayOrder";

    /****
     * 通过我的钱包来付款红包
     */
    public static String WALLET_PAYRED = YSS_URL_ANDROID + "redPacket/walletPay";
    /****
     * 通过我的钱包来付款多个订单
     */
    public static String WALLET_PAY_MULTI_ORDER = YSS_URL_ANDROID + "order/walletPayOrderList";
    /****
     * 查询该订单的详情
     */
    public static String QUERY_ORDERSHOP_DETAILS = YSS_URL_ANDROID + "order/getOrderShop";
    /****
     * 获取店铺信息
     */
    public static String GET_MY_SHOP_INFO = YSS_URL_ANDROID + "store/queryStore";
    /****
     * 提交订单获取订单编号
     */
    public static String SUBMIT_ORDER = YSS_URL_ANDROID + "order/addOrder";
    /****
     * 提交多个订单获取订单编号
     */
    public static String SUBMIT_MULTI_ORDER = YSS_URL_ANDROID + "order/addOrderList";
    /****
     * 提交多个订单获取订单编号
     */
    public static String SUBMIT_MULTI_CART_ORDER = YSS_URL_ANDROID + "order/addOrderListV160302";


    /****
     * 免费领提交订单
     */
    public static String SUBMIT_FREE_BUY_ORDER = YSS_URL_ANDROID + "order/addOrderVipFreeBuy";

    /****
     * 会员免费领提交订单
     */
    public static String VIP_SUBMIT_SHARE_FREE_BUY_ORDER = YSS_URL_ANDROID + "order/addOrderFriendsShare";


    /****
     * 1元购提交订单
     */
    public static String SUBMIT_ONE_BUY_ORDER = YSS_URL_ANDROID + "order/addOrderListV180306";

    /**
     * 一元购再抽一次下单
     */
    public static String ONEBUY_SUBMIT_AG = YSS_URL_ANDROID + "order/addOrderAg";

    /****
     * 提交多个订单获取订单编号(搭配)
     */
    public static String SUBMIT_MULTI_CART_ORDER_DAPEI = YSS_URL_ANDROID + "order/addOrderListDpV160806";

    /**
     * 特卖提交多订单获取订单编号
     */
    public static String SUBMIT_MULTI_CART_ORDER_SPECIAL = YSS_URL_ANDROID + "order/addOrderListZero";

    /****
     * 积分商城提交订单获取订单编号
     */
    public static String SUBMIT_INTE_ORDER = YSS_URL_ANDROID + "inteOrder/addOrder";

    /****
     * 获取我的圈子列表
     */
    public static String GET_MY_CIRCLE_LIST = YSS_URL_ANDROID + "circle/queryUserCircleList";
    /****
     * 获取个人主页关注 未登录状态
     */
    public static String GET_MY_CIRCLE_LIST2 = YSS_URL_ANDROID + "circle/queryUCLUnLogin";
    /****
     * 获取粉丝列表
     */
    public static String GET_CIRCLE_FANS_LIST = YSS_URL_ANDROID + "userFans/queryFansist";
    /****
     * 获取粉丝列表 未登录状态
     */
    public static String GET_CIRCLE_FANS_LIST2 = YSS_URL_ANDROID + "userFans/queryFLUnLogin";
    /****
     * 获取所有圈子列表
     */
    public static String GET_ALL_CIRCLE_LIST = YSS_URL_ANDROID + "circle/queryAll";

    /****
     * 获取所有圈子列表 未登录状态
     */
    public static String GET_ALL_CIRCLE_LIST2 = YSS_URL_ANDROID + "circle/queryAllUnLogin";

    /**
     * 未登录状态下获取所有圈子列表
     */
    public static String GET_ALL_CIRCLE_LIST_UNLOGIN = YSS_URL_ANDROID + "circle/queryAllUnLogin";
    /****
     * 获取推荐圈子列表
     */
    public static String GET_RECOMMEND_CIRCLE_LIST = YSS_URL_ANDROID + "circle/fine";

    /****
     * 获取推荐圈子列表 未登录
     */
    public static String GET_RECOMMEND_CIRCLE_LIST2 = YSS_URL_ANDROID + "circle/fineUnLogin";
    /****
     * 微信支付得到prepayID
     */
    public static String GET_PREPAY_ID = YSS_URL_ANDROID + "wxpay/uinifiedOrder";

    /****
     * 她她圈--帖子列表
     */
    public static String GET_POST_LIST = YSS_URL_ANDROID + "circle/queryList";

    /****
     * 她她圈--帖子列表 未登录状态
     */
    public static String GET_POST_LIST2 = YSS_URL_ANDROID + "circle/queryLUnLogin";
    /****
     * 她她圈--我的记录和动态列表
     */
    public static String GET_USER_NEWS_LIST = YSS_URL_ANDROID + "circle/queryUserNewsList";
    /****
     * 她她圈--未登录状态下查询个人主页动态列表
     */
    public static String GET_USER_NEWS_LIST2 = YSS_URL_ANDROID + "circle/qDynamicUnLogin";
    /****
     * 她她圈--收藏列表
     */
    public static String GET_COLLECT_LIST = YSS_URL_ANDROID + "circle/queryCollectList";

    /***
     * 她她圈--加入圈子
     */
    public static String ADD_CIRCLE = YSS_URL_ANDROID + "circle/add";
    /***
     * 她她圈--退出圈子
     */
    public static String EXIT_CIRCLE = YSS_URL_ANDROID + "circle/del";
    /****
     * 她她圈--圈子成员（含管理员列表）列表
     */
    public static String GET_CIRCLE_MEM = YSS_URL_ANDROID + "circle/queryUserList";
    /****
     * 她她圈--圈子成员（含管理员列表）列表 未登录状态
     */
    public static String GET_CIRCLE_MEM2 = YSS_URL_ANDROID + "circle/queryUListUnlogin";
    /****
     * 她她圈--帖子评论列表
     */
    public static String GET_COMMENT_LIST = YSS_URL_ANDROID + "circleReNews/queryList";
    /****
     * 她她圈--帖子评论列表 未登录状态
     */
    public static String GET_COMMENT_LIST2 = YSS_URL_ANDROID + "circleReNews/queryLUnlogin";
    /****
     * 她她圈--添加评论
     */
    public static String POST_COMMENT = YSS_URL_ANDROID + "circleReNews/add";
    /****
     * 她她圈--收藏帖子
     */
    public static String POST_COLLECT_NEWS = YSS_URL_ANDROID + "circleNews/collectNews";
    /****
     * 她她圈--帖子查询
     */
    public static String POST_QUERY = YSS_URL_ANDROID + "circleNews/queryNews";
    /****
     * 她她圈--帖子查询 未登录状态
     */
    public static String POST_QUERY2 = YSS_URL_ANDROID + "circleNews/queryNewsUnLogin";
    /****
     * 她她圈--查询个人主页
     */
    public static String GET_CIRCLE_HOME_PAGE = YSS_URL_ANDROID + "userFans/home";
    /****
     * 她她圈--查询个人主页 未登录状态
     */
    public static String GET_CIRCLE_HOME_PAGE2 = YSS_URL_ANDROID + "userFans/homeUnLogin";
    /****
     * 她她圈--查询个人主页--关注
     */
    public static String GET_CIRCLE_HOME_PAGE_ATTENTION = YSS_URL_ANDROID + "userFans/add";
    /****
     * 她她圈--查询个人主页--取消关注
     */
    public static String GET_CIRCLE_HOME_PAGE_UNATTENTION = YSS_URL_ANDROID + "userFans/del";
    /****
     * 她她圈--发帖
     */
    public static String GET_CIRCLE_PUBLISH_TOPIC = YSS_URL_ANDROID + "circleNews/insert";
    /****
     * 她她圈--查询标签
     */
    public static String GET_CIRCLE_CHOOSE_TAGS = YSS_URL_ANDROID + "circleNews/queryTag";
    /****
     * 她她圈--删除帖子（我的记录）
     */
    public static String GET_CIRCLE_DELETE_POST = YSS_URL_ANDROID + "circleNews/delNews";
    /****
     * 她她圈--删除帖子（我的记录）
     */
    public static String GET_CIRCLE_DELETE_COLLECT_POST = YSS_URL_ANDROID + "circleNews/deleteCollectNews";

    /****
     * 申请退换货
     */
    public static String APPLY_THH = YSS_URL_ANDROID + "returnShop/add";
    /****
     * 添加商品评论
     */
    public static String ADD_COMMENT = YSS_URL_ANDROID + "shopComment/addShopComment";

    /****
     * 根据订单编号来确认收货
     */
    public static String AFFIRM_PROD_RECEIVED = YSS_URL_ANDROID + "order/affirmOrder";
    /***
     * 查看物流
     */
    public static String CHAKAN_LOGISTICS = YSS_URL_ANDROID + "order/selLogistics";

    /****
     * 获取商品的详情图片
     */
    public static String GET_PRODUCT_PIC_LIST = YSS_URL_ANDROID + "shop/queryPicList";
    /***
     * 得到首页轮播图数据
     */
    public static String GET_TUIJIAN_DATA = YSS_URL_ANDROID + "shop/queryOption";
    /***
     * 得到每日金额
     */
    public static String GET_DAILY_AMOUNT = YSS_URL_ANDROID + "wallet/sellMoney";
    /***
     * 得到每日回佣
     */
    public static String GET_DAILY_REBATE = YSS_URL_ANDROID + "wallet/sellKickback";
    /***
     * 得到每日访客
     */
    public static String GET_DAILY_VISITOR = YSS_URL_ANDROID + "wallet/sellStoreVisitor";
    /***
     * 得到交易列表
     */
    public static String GET_TRADE_LIST = YSS_URL_ANDROID + "wallet/findFundDetail";
    /***
     * 账户明细-余额
     */
    public static String GET_REBATE_LIST = YSS_URL_ANDROID + "wallet/findFundDetail";

    /****
     * 得到我的卖单订单列表
     */
    public static String GET_MY_SELL_ORDER_LIST = YSS_URL_ANDROID + "order/sellOrderManage";

    /***
     * 得到买单列表
     */
    public static String GET_BUY_ORDER_LIST = YSS_URL_ANDROID + "order/getBuyOrder";

    /**
     * 得到卡券说明
     */
    public static String GET_KAQUAN_SHUOMING = YSS_URL_ANDROID + "help/couponRule";
    /**
     * 得到夺宝参入号码
     */
    public static String GET_DUOBAO_NUMBER = YSS_URL_ANDROID_NEW + "treasures/getPayCode";
    /**
     * 得到夺宝参入号码 多个订单
     */
    public static String GET_MULTI_DUOBAO_NUMBER = YSS_URL_ANDROID_NEW + "treasures/getPayCodeList";
    /****
     * 查询用户信息
     */
    public static String GET_USER_INFO = YSS_URL_ANDROID + "user/query_userinfo";
    /****
     * 修改用户信息
     */
    public static String UPDATE_USER_INFO = YSS_URL_ANDROID + "user/update_userinfo";

    /****
     * 验证原支付密码是否正确
     */
    public static String CHECK_PWD = YSS_URL_ANDROID + "wallet/ckPwd";

    /****
     * 通过原支付密码修改支付密码
     */
    public static String UPWALLETPWD = YSS_URL_ANDROID + "wallet/upWalletPwd";

    /****
     * 通过手机修改支付密码获取验证码
     */
    public static String GET_PHONE_CODE_TO_UPPAYPASS = YSS_URL_ANDROID + "wallet/get_phone_code";
    /****
     * 通过手机修改支付密码验证验证码是否正确
     */
    public static String CKPHONECODE = YSS_URL_ANDROID + "wallet/ckPhoneCode";
    /****
     * 通过手机修改支付密码
     */
    public static String UPWALLETPWDBYSMS = YSS_URL_ANDROID + "wallet/upWalletPwdBySms";
    /****
     * 查询是否绑定手机号
     */
    public static String QUERYPHONE = YSS_URL_ANDROID + "user/queryPhone";
    /****
     * 查询是否绑定邮箱
     */
    public static String QUERYEMAIL = YSS_URL_ANDROID + "user/queryEmail";
    /****
     * 检测手机是否已经绑定其他账号
     */
    public static String CHECKPHONE = YSS_URL_ANDROID + "user/checkPhone";
    /****
     * 检测邮箱是否已经绑定其他账号
     */
    public static String CHECKEMAIL = YSS_URL_ANDROID + "user/checkEmail";
    /****
     * 绑定邮箱-验证短信验证码
     */
    public static String CHECKEMAILCODE = YSS_URL_ANDROID + "user/checkEmailCode";

    /****
     * 绑定手机-验证短信验证码
     */
    public static String CHECKCODE = YSS_URL_ANDROID + "user/checkCode";
    /***
     * 更改绑定手机-验证短信验证码
     */
    public static String CHECKOLDCODE = YSS_URL_ANDROID + "user/checkOldCode";

    /****
     * 绑定手机-验证短信验证码
     */
    public static String CHECKPAYMENTPASSWORD = YSS_URL_ANDROID + "user/checkPaymentPassword";
    /****
     * 我的钱包-检测是否设置了支付密码
     */
    public static String CKSETPWD = YSS_URL_ANDROID + "wallet/ckSetPwd";

    /****
     * 我的钱包-设置支付密码
     */
    public static String SETWALLETPWD = YSS_URL_ANDROID + "wallet/setWalletPwd";
    /****
     * 我的钱包-我的银行卡
     */
    public static String FINDMYBANKCARD = YSS_URL_ANDROID + "wallet/findMyBankCard";
    /****
     * 我的钱包-添加银行卡
     */
    public static String ADDMYBANKCARD = YSS_URL_ANDROID + "wallet/addMyBankCard";
    /****
     * 我的钱包-完善银行卡信息
     */
    public static String UPDATEMYBANKCARD = YSS_URL_ANDROID + "wallet/updateMyBankCard";

    /****
     * 我的钱包-提现，获取最大最小值
     */
    public static String GETDEPOSITRANGE = YSS_URL_ANDROID + "wallet/getDepositRange";
    /****
     * 我的钱包-提现
     */
    public static String BANKDEPOSITADD = YSS_URL_ANDROID + "wallet/bankDepositAdd";
    /****
     * 我的钱包-微信提现
     */
    public static String WEIXIN_DEPOSITADD = YSS_URL_ANDROID + "wallet/wxDepositAdd";

    /****
     * 联盟商家提现
     */
    public static String BANKDEPOSITADD2 = YSS_URL_ANDROID + "merchantAlliance/bankDepositAdd";
    /****
     * 我的钱包-账户明细-提现
     */
    public static String SELDEPOSIT = YSS_URL_ANDROID + "wallet/selDeposit";

    /****
     * 添加我的足迹
     */
    public static String GET_ADD_MY_STEPS = YSS_URL_ANDROID + "mySteps/addSteps";
    /****
     * 查询我的足迹
     */
    public static String QUERY_STEPS_LIST = YSS_URL_ANDROID + "mySteps/queryStepsList";
    /****
     * 删除我的足迹
     */
    public static String DELETE_MY_STEPS = YSS_URL_ANDROID + "mySteps/delSteps";
    /***
     * 获取用户信息
     */
    public static String GET_BUYER_INFO = YSS_URL_ANDROID + "order/getOrderBuyers";

    /***
     * 微信支付单个订单
     */
    // public static String WX_PAY_SINGLE = "https://www.52yifu.wang/cloud-pay/" +
    // "wxpay/appUinifiedOrder";
    // WX_PAY_MULTI
    // WX_PAY_MULTIOLD
    public static String WX_PAY_SINGLE = YSS_URL_ANDROID_NEW + "wxpay/appUinifiedOrder";

    /***
     * 微信支付多个订单
     */
    public static String WX_PAY_MULTI = YSS_URL_ANDROID_NEW + "wxpay/appUinifiedOrderList";
    // WX_PAY_MULTI
    // WX_PAY_MULTIOLD
    // public static String WX_PAY_MULTI = YSS_URL_ANDROID + "wxpay/appUinifiedOrderList";
    /***
     * 微信支付红包
     */
    public static String WX_PAY_RED = YSS_URL_ANDROID_NEW + "wxpay/uinifiedHd";
    // public static String WX_PAY_RED = YSS_URL_ANDROID +"wxpay/uinifiedHd";

    /***
     * 待付款--选择多个订单一起支付
     */
    public static String PAY_ORDERS = YSS_URL_ANDROID + "order/payOrders";
    /***
     * 查看退换货
     */
    public static String CHECK_PAYBACK = YSS_URL_ANDROID + "returnShop/findOne";

    /****
     * 修改用户头像
     */
    public static String UPDATE_USER_IMG = YSS_URL_ANDROID + "user/update_userinfo";
    /****
     * 修改店铺头像
     */
    public static String UPDATE_STORE_IMG = YSS_URL_ANDROID + "store/update";

    /****
     * 查询各大公司物流
     */
    public static String QUERY_GETKUAIDI = YSS_URL_ANDROID + "returnShop/getKuaidi";

    /****
     * 退换货时添加物流信息
     */
    public static String ADDLOGISTICS = YSS_URL_ANDROID + "returnShop/addLogistics";

    /****
     * 退换货时 --- 确认收到换货
     */
    public static String AFFIRMSHOP = YSS_URL_ANDROID + "returnShop/affirmShop";

    /****
     * 美圈，申请管理员
     */
    public static String APPLYADMIN = YSS_URL_ANDROID + "circle/apply";

    /****
     * 获取商品链接
     */
    public static String GET_SHOP_LINK = YSS_URL_ANDROID + "shop/getShopLink";


    /****
     * 获取商品链接-----随机夺宝商品的链接
     */
    public static String GET_SHOP_LINK_DUOBAO_SUIJI = YSS_URL_ANDROID + "shop/getIndianaLink";

    /****
     * 获取商品链接
     */
    public static String GET_P1_SHOP_LINK = YSS_URL_ANDROID + "shop/getpShopLink";

    /****
     * 获取分享商品链接
     */
    public static String GET_SHARE_SHOP_LINK = YSS_URL_ANDROID + "shop/shareShop";

    /****
     * 获取分享正价特惠商品链接
     */
    public static String GET_SHARE_SHOP_LINK_HOBBY = YSS_URL_ANDROID + "shop/shareShop";

    /****
     * 获取搭配购商品链接 或者搭配购商品分享图片的URL
     */
    public static String GET_SHARE_SHOP_LINK_MATCH = YSS_URL_ANDROID + "collocationShop/querypic";

    /****
     * 提醒发货
     */
    public static String URGESUPPSHIPMENTS = YSS_URL_ANDROID + "order/urgeSuppShipments";
    /****
     * 检测邮箱验证码
     */
    public static String CHECK_EMAIL_AUTHCODE = YSS_URL_ANDROID + "user/checkEmailAuthCode";
    /****
     * 忘记密码检测手机验证码
     */
    public static String CHECK_PHONE_AUTHCODE = YSS_URL_ANDROID + "user/checkAuthCode";

    /****
     * 删除银行卡
     */
    public static String DEL_MYBANK_CARD = YSS_URL_ANDROID + "wallet/delMyBankCard";

    /****
     * 获取邮箱激活码接口
     */
    public static String GET_EMAILACTIVATE_CODE = YSS_URL_ANDROID + "user/get_emailactivate_code";

    /****
     * 设置推荐人
     */
    public static String SET_REFEREE = YSS_URL_ANDROID + "user/setReferee";
    /****
     * 获取我的二维码
     */
    public static String GET_MY_QRCODE = YSS_URL_ANDROID + "wxPush/createQRCode";
    /***
     * 待付款--选择多个订单一起支付
     */
    public static String GET_PROX_COUPON = YSS_URL_ANDROID + "coupon/appMatchCoupon";

    /****
     * 获取积分使用记录
     */
    public static String GET_MY_INTEGRAL = YSS_URL_ANDROID + "wallet/getIntegralNum";

    /****
     * 通知后台 得回佣
     */
    public static String GET_KICKBACK = YSS_URL_ANDROID + "order/shareGetkb";
    /****
     * 通知后台 得回佣
     */
    public static String GET_KICKBACK_LIST = YSS_URL_ANDROID + "order/shareGetkbToList";

    /****
     * 通知后台 得积分
     */
    public static String GET_INTEGRAL = YSS_URL_ANDROID + "order/integralDoShare";

    /***
     * 得到阿里参数
     */
    public static String GET_ALI_PARAM = YSS_URL_ANDROID_NEW + "alipay/getAppKey";
    // WX_PAY_MULTI
    // WX_PAY_MULTIOLD

    /***
     * 得到微信支付参数参数
     */
    public static String GET_WX_PARAM = YSS_URL_ANDROID_NEW + "wxpay/getData";
    // WX_PAY_MULTI
    // WX_PAY_MULTIOLD

    // public static String GET_ALI_PARAM = YSS_URL_ANDROID + "alipay/getAppKey";

    /***
     * 得到邀请码
     */
    public static String GET_INVITE_CODE = YSS_URL_ANDROID + "inviteCode/getInviteCode";

    /**
     * 分享邀请码之后调用此接口.以便记录数据,后期好统计
     */
    public static String SHARE_NUMBER = YSS_URL_ANDROID + "addDepositCount";

    /**
     * 3293 【AND】 运营数据统计
     */
    public static String YUNYINGTONGJI = YSS_URL_ANDROID + "record/add";

    /***
     * 分享拿红包
     */
    public static String GET_COUPON = YSS_URL_ANDROID + "coupon/regisiterInviteGetCoupon";

    /***
     * 记录分享
     */
    public static String RECORD_SHARE = YSS_URL_ANDROID + "activaShare/activaShare";

    /***
     * 主页检查是否已经分享
     */
    public static String MAIN_CK_IS_SHOW = YSS_URL_ANDROID + "phoneShare/ckIsShare";

    /***
     * 兑换邀请码
     */
    public static String EXCHANGE_INVITE_CODE = YSS_URL_ANDROID + "inviteCode/inviteCodeGetCoupon";

    /***
     * 商家联盟收益统计
     *
     */
    public static String GET_REVENUE_STATISTICS = YSS_URL_ANDROID + "merchantAlliance/earningsCount";

    // 线上聊天地址
    public static String CHAT_HOST = "message4.yeamo.com";
    // 聊天端口，测试&正式
    int CHAT_PORT = 6004;

    /**
     * 联盟商家主页
     */
    public static String LEAGUE_BIZ = YSS_URL_ANDROID + "merchantAlliance/merchanMain";
    /**
     * 联盟商家记录详情
     */
    public static String LEAGUE_BIZ_DETAIL = YSS_URL_ANDROID + "merchantAlliance/earningsDetail";
    /**
     * 佣金记录详情
     */
    public static String LEAGUE_BIZ_YJ_LOG = YSS_URL_ANDROID + "merchantAlliance/selKickBack";
    /**
     * 提现记录详情
     */
    public static String LEAGUE_BIZ_TX_LOG = YSS_URL_ANDROID + "merchantAlliance/selBankDeposit";

    /**
     * 添加商户信息
     */
    public static String ADD_BUSINESS = YSS_URL_ANDROID + "merchantAlliance/addBusiness";

    /**
     * 分享后调用，用于统计谁分享了
     */
    public static String TONGJI_FENXIANG = YSS_URL_ANDROID + "shareShop/add";
    /**
     * 修改商户信息
     */
    public static String UPDATE_BUSINESS = YSS_URL_ANDROID + "merchantAlliance/updateBusiness";

    /**
     * 超级合伙人会员信息
     */
    public static String ALLIANCE_MEMBER = YSS_URL_ANDROID + "superMan/myVipList";
    /**
     * 超级合伙人会员的会员
     */
    public static String ALLIANCE_MEMBERH5 = YSS_URL_ANDROID + "superMan/myMemberList";

    /****
     * 检测商家是否填写资料
     */
    public static String CHECK_MERCHASE_COMMIT_INFO = YSS_URL_ANDROID + "merchantAlliance/checkIsBus";
    /***
     * 得到玩的下级买单列表
     */
    public static String GET_MERCHANT_BUY_ORDER_LIST = YSS_URL_ANDROID + "merchantAlliance/seljuniorUserOrder";

    /****
     * 得到我的卖单订单列表
     */
    public static String GET_MERCHANT_MY_SELL_ORDER_LIST = YSS_URL_ANDROID + "order/sellOrderManage";

    /****
     * 开始激活
     */
    public static String START_ACTIVE = YSS_URL_ANDROID + "startActiva/add";

    /****
     * APP使用时长统计
     */
    public static String USE_TIME = YSS_URL_ANDROID + "apptimeStartistice/getCount";
    /****
     * APP使用次数统计
     */
    public static String USE_COUNT = YSS_URL_ANDROID + "apptimeStartistice/countActivation";

    /****
     * 统计用户每日分享次数
     */
    public static String TONGJI_SHARE_COUNT = YSS_URL_ANDROID + "user/afterShare";

    /****
     * 获取供应商号码
     */
    public static String GET_SUPP_PHONE = YSS_URL_ANDROID + "order/getSuppPhone";

    /****
     * 0元购套餐条件查询接口
     */
    public static String GET_PACKAGE_LIST = YSS_URL_ANDROID + "shop/queryPackageList";

    /**
     * 查询商品详情(登录)
     */

    public static String GET_SHOP_MEAL = YSS_URL_ANDROID + "shop/queryPackage";

    /**
     * 查询商品详情(未登录)
     */

    public static String GET_SHOP_MEAL_UNLOGIN = YSS_URL_ANDROID + "shop/queryPUnLogin";

    /**
     * 购物车统计(登录)
     */
    public static String GET_SHOP_CART_COUNT = YSS_URL_ANDROID + "shopCart/shopCartCount";
    /***
     * 申请售后前,需请求后台,获取可退金额,优惠券抵扣多少.积分抵扣金额.
     */
    public static String GET_TK_TH_INFO = YSS_URL_ANDROID + "returnShop/addAgo";

    /***
     * 单独添加0元购单
     */
    public static String ADD_ZERO_ORDER = YSS_URL_ANDROID + "order/addOrderZero";
    /***
     * 夺宝下单
     */
    public static String ADD_DUOBAO_DRDER = YSS_URL_ANDROID + "treasures/addTreasures";

    /***
     * 单独添加0元购单(签到跳过来的商品)
     */
    public static String ADD_ZERO_ORDER_SIGN = YSS_URL_ANDROID + "order/addOrder/active";
    /***
     * 活动商品提交订单
     */
    public static String ADD_ZERO_ORDER_SIGN_HUODONG = YSS_URL_ANDROID + "order/addOrder/activity";

    /***
     * 得到售后列表
     */
    public static String GET_MY_PAYBACK_LIST = YSS_URL_ANDROID + "returnShop/queryByPage";

    /***
     * 0元购商品属性列表
     */
    public static String GET_SHOPS_ATTRS = YSS_URL_ANDROID + "shop/queryPAttr";
    /***
     * 0元购 加入购物车
     */
    public static String MEAL_ADD_SHOPCART = YSS_URL_ANDROID + "shopCart/addList";

    /***
     * 1.13 获取套餐链接
     */
    public static String GET_P_SHOP_LINK = YSS_URL_ANDROID + "shop/getpShopLink";

    /***
     * 签到分享特价商品
     */
    public static String GET_SIGN_SHARE_ZERO = YSS_URL_ANDROID + "shop/getPSShareLink";

    /***
     * 0元购下单之前
     */
    public static String PRE_SUBMIT_ORDER = YSS_URL_ANDROID + "order/isOneBuy";
    /***
     * 我的积分信息
     */
    public static String QUERY_MY_INTEGRAL_INFO = YSS_URL_ANDROID + "wallet/getIntegral";
    /**
     * 任务
     */
    public static String DOMISSION = YSS_URL_ANDROID + "mission/doMission";
    /**
     * 获取套餐链接
     */
    public static String P_SHOP_LINK = YSS_URL_ANDROID + "shop/getpShopLink";

    /***
     * 得到是否还有任务（慢）
     */
    public static String GET_FIN_COUNT = YSS_URL_ANDROID + "user/finCount";

    /***
     * 得到我刚下的订单
     */
    public static String GET_MY_ORDER = YSS_URL_ANDROID + "order/getOrderDetial";
    public static String GET_PAYSUCCESS_ORDER = YSS_URL_ANDROID + "order/getOrderDetialByGcodeOrOcode";

    /***
     * 单个订单支付完成状态
     */
    public static String UPDATA_PAYSTATUS = YSS_URL_ANDROID_NEW + "order/updatePayStatus";
    /***
     * 多个订单支付完成状态
     */
    public static String UPDATA_PAYSTATUS_LIST = YSS_URL_ANDROID_NEW + "order/updatePayStatusList";
    /***
     * 会员验证
     */
    public static String MEMBER_VERIFY = YSS_URL_ANDROID + "vip/submitting";
    /****
     * 会员商品提交订单获取订单编号
     */
    public static String SUBMIT_MEMBER_ORDER = YSS_URL_ANDROID + "order/addOrder98";

    /****
     * 会员商品提交订单获取订单编号
     */
    public static String QUERY_VIP_CARD = YSS_URL_ANDROID + "vip/queryMyVipCard";
    /****
     * 通过用户id获取用户头像
     */
    public static String QUERY_PIC_BY_UID = YSS_URL_ANDROID + "user/queryUserPic";

    /****
     * 超级合伙人卡号列表
     */
    public static String QUERY_SUPERMAN_CARDLIST = YSS_URL_ANDROID + "superMan/cardList";

    /**
     * 强制浏览(热门推手)
     */
    public static String FORCE_LOOK = YSS_URL_ANDROID + "shop/queryBrowseShopList";
    /**
     * 发布--选择商品
     */
    public static String QUERYPURCHASE = YSS_URL_ANDROID + "shop/queryPurchase";
    /**
     * 穿搭详情--更多推荐商品
     */
    public static String QUERYTRSLIST = YSS_URL_ANDROID + "fc/QueryTRSList";
    /**
     * 活动商品列表
     */
    public static String SIGN_ACTIVE_SHOP = YSS_URL_ANDROID + "shop/queryShopActivity";
    /**
     * 夺宝商品列表
     */
    public static String INDIANA_SHOP_LIST = YSS_URL_ANDROID + "shop/queryIndianaList";

    /**
     * 获取红包详情
     */

    public static String QUERY_RED = YSS_URL_ANDROID + "redPacket/queryRedPacket";

    /****
     * 后台获取物流信息
     */
    public static String EXEPQUERY = YSS_URL_ANDROID + "order/expQuery";

    /****
     * 检测用户是否有微信授权
     */
    public static String HAVEUID = YSS_URL_ANDROID + "redPacket/haveUid";

    /****
     * 塞进红包
     */
    public static String SEND_REDPACKETS = YSS_URL_ANDROID + "redPacket/sendRedPacket";

    /****
     * 得到红包链接
     */
    public static String GET_SHARERED_PACKETLINK = YSS_URL_ANDROID + "redPacket/getShareRedPacketLink";

    /**
     * 搭配 首页
     */
    public static String GET_MATCH = YSS_URL_ANDROID + "collocationShop/queryShopCondition";
    /**
     * 搭配 详情
     */
    public static String GET_MATCH_DETAILS = YSS_URL_ANDROID + "collocationShop/query";
    /**
     * 搭配 详情(未登录)
     */
    public static String GET_MATCH_DETAILS_UNLOGIN = YSS_URL_ANDROID + "collocationShop/queryUnLogin";
    /**
     * 搭配 详情 相关商品推荐
     */
    public static String GET_MATCH_DETAILS_REC = YSS_URL_ANDROID + "collocationShop/queryShopList";

    /**
     * 搭配 商品属性选择
     */
    public static String GET_MATCH_ATTR = YSS_URL_ANDROID + "shop/queryStockAttr";
    /**
     * 搭配 商品加入购物车
     */
    public static String ADD_MATCH = YSS_URL_ANDROID + "shopCart/add";

    /**
     * 判断用户是否下过包邮商品的订单
     */

    public static String CHECK_ISBAOYOUDINGDAN = YSS_URL_ANDROID + "order/ckOneActivity";

    /****
     * 签到任务列表(登录前)
     */
    public static String SIGN_LIST = YSS_URL_ANDROID + "signIn2_0/siTaskList";

    /****
     * 签到任务列表(登录后)
     */
    public static String SIGN_LIST_LOGIN = YSS_URL_ANDROID + "signIn2_0/siLogTaskList";

    /****
     *
     *
     *
     *
     * 检查是否有补签卡
     */
    public static String SIGN_CHECK_BUQIANKA = YSS_URL_ANDROID + "signIn/ckeckCard";

    /****
     * 签到
     */
    public static String SIGN_QIANDAO = YSS_URL_ANDROID + "signIn2_0/signIning";

    /****
     * 用户已完成的签到列表
     */
    public static String SIGN_USER_YET = YSS_URL_ANDROID + "signIn2_0/userTaskList";
    /**
     * 获取签到信息
     */

    public static String SIGN_DATA = YSS_URL_ANDROID + "signIn2_0/getCount";
    /**
     * 检测是否开店
     */

    public static String CHECKSTORE = YSS_URL_ANDROID + "user/checkStore";
    /**
     * 更换客服
     */

    public static String CHOICE_KEFU = YSS_URL_ANDROID + "user/getCustomerService";
    /**
     * 获取用户等级表
     */

    // public static String GETUSERDENGJI = YUrl.imgurl + "vitality/v.json";
    public static String GETUSERDENGJI = YUrl.imgurl + "vitality/v.json";

    /**
     * 获取店铺二维码链接
     */
    public static String GETMYSHOPLINK = YSS_URL_ANDROID + "store/getQRUrl";

    /****
     * 记录微信授权信息
     */
    public static String WEIXINTOKEN = YSS_URL_ANDROID + "redPacket/weiXinRedPacketToken";
    /**
     * 记录手机是否开过店
     */
    public static String isOpenShop = YSS_URL_ANDROID + "ad/isOpen";

    /**
     * 获取抵用券
     */
    public static String Voucher = YSS_URL_ANDROID + "coupon/addVoucher";
    /**
     * 查询抵用券
     */
    public static String HaveVoucher = YSS_URL_ANDROID + "coupon/queryVoucher";

    /**
     * 夺宝记录——参与记录
     */
    public static String SnatchJoin = YSS_URL_ANDROID + "treasures/getMyParticipationList";

    /**
     * 夺宝中的往期揭晓——往期揭晓
     */
    public static String Wqjx_left = YSS_URL_ANDROID + "treasures/getWinParticipationList";

    /**
     * 夺宝记录——我的晒单
     */
    public static String DuoBaoShaiDan = YSS_URL_ANDROID + "shareOrder/selCommList";

    /**
     * 晒单点赞
     */
    public static String AddClick = YSS_URL_ANDROID + "shareOrder/addClick";

    /**
     * 特卖商品提交订单确认
     */
    public static String SHOPCART_SUBMIT_CONFRIM = YSS_URL_ANDROID + "order/isOneListBuy";

    public static String GETNOWTIME = YSS_URL_ANDROID + "order/getNow"; // 获取当前系统时间 具体参考任务2779

    /**
     * 提现统计
     */
    public static String GET_MONEY = YSS_URL_ANDROID + "wallet/addDepositCount";

    /**
     * 启动获取pic
     */
    public static String STRAT_GET_PIC = YSS_URL_ANDROID + "initiateApp/queryStartPage";

    /**
     * 获取分享的生活状态图
     */
    public static String STRAT_GET_SHARE_LIFE_PIC = YSS_URL_ANDROID + "initiateApp/queryShareLifePic";

    /**
     * 钱包红点状态
     */
    public static String WALLET_RED_STATUE = YSS_URL_ANDROID + "wallet/delRedDot";
    /**
     * 卡券红点状态
     */
    public static String COUPONS_RED_STATUE = YSS_URL_ANDROID + "coupon/delRedDot";
    /**
     * 我的喜欢
     */
    public static String ADD_MINE_LIKE = YSS_URL_ANDROID + "like/selLikeShopCodes";

    /**
     * 获取购物车数据
     */
    public static String GET_CART_DATA = YSS_URL_ANDROID + "shopCart/getCartData";
    /**
     * 获取密码
     */
    public static String GET_HUANXIN_PASSWORD = YSS_URL_ANDROID + "user/getUser";
    /**
     * 运营数据统计
     */
    public static String YUNYINGSHUJUTONGJI = YSS_URL_ANDROID + "dataRecord/addDataRecord";
    /**
     * 运营数据统计（新版）
     */
    public static String YUNYINGSHUJUTONGJINEW = YSS_URL_ANDROID + "dataRecord/addDataRecord/170412";
    /**
     * 运营数据统计时长（新版）
     */
    public static String YUNYINGSHUJUTONGJINEWDURATION = YSS_URL_ANDROID + "dataRecord/insertDataRecordTime";
    /**
     * 获取用户等级
     */
    public static String GET_USER_GRALDE = YSS_URL_ANDROID + "wallet/getGrade";

    /**
     * 获取提现前信息
     */
    public static String GET_IS_DIALOG = YSS_URL_ANDROID + "wallet/doDepositAgo";


    public static String UM_PUSH_COUNT = YSS_URL_ANDROID + "initiateApp/pushMsgDataCount";
    /**
     * 获取九宫图的二维码背景图片
     */
    public static String GET_SHARE_BG = YSS_URL_ANDROID + "initiateApp/queryShareNineBackPic";

    /**
     * 后台设置初次登录是否跳转到微信授权（3.3.8）
     */
    public static String FIRST_SHOUQUAN = YSS_URL_ANDROID + "user/getSwitch";

    /**
     * 签到分享搭配购获取搭配编号
     */
    public static String GETDAIPEIBIANHAO = YSS_URL_ANDROID + "collocationShop/getLink";

    /**
     * 显示用户新增粉丝和用户领取奖励弹窗
     */
    public static String CREATESHAREAWARDS = YSS_URL_ANDROID + "slb/queryBarr";

    /**
     * * 分享额外奖励详情
     */
    public static String QUERY_SHARE_DETIALS = YSS_URL_ANDROID + "slb/query";

    /**
     * 最新获奖弹窗通知
     */
    public static String QUERYMONEY = YSS_URL_ANDROID + "slb/queryMoney";

    /**
     * 获取是否开启优惠券升级金券
     */
    public static String QUERYCPGOLD = YSS_URL_ANDROID + "wallet/CpGold";

    /**
     * 获取是否开启积分升级金币
     */
    public static String QUERYTWOFOLDNESSGOLD = YSS_URL_ANDROID + "wallet/twofoldnessGold";

    /**
     * 获取粉丝数和金额
     */
    public static String QUERY_FANS_MONEY = YSS_URL_ANDROID + "user/getFansMap";

    /**
     * 1号渠道专用接口 切换登录形式
     */
    public static String LOGINSTYLE1 = YSS_URL_ANDROID + "user/getChannelStatus";

    /**
     * 获取会员活力值
     */
    // public static String GETVITALITY = YSS_URL_ANDROID + "wallet/getVitality"; // 获取会员活力值
    /**
     * 获取 拼团广场商品列表
     */
    public static String GROUPSQUARESHOPLIST = YSS_URL_ANDROID + "shop/queryGroupSquareShopList"; // 获取
    // 拼团广场商品列表

    /**
     * 获取免单使用情况
     */
    public static String FREEUSE = YSS_URL_ANDROID + "order/freeUse";
    /**
     * 获取专题--更多专题
     */
    public static String MORESUB = YSS_URL_ANDROID + "collocationShop/moreProject";
    /**
     * 专题详情
     */
    public static String SPECIALTOPICDETAILS = YSS_URL_ANDROID + "collocationShop/query2";
    /**
     * 获取余额抵扣比例
     */
    public static String GET_ORDER_MONEY = YSS_URL_ANDROID + "order/addOrderAgo";
    /**
     * 赌博抽奖 -- 拿到抽奖结果
     */
    public static String GET_LUCK_DRAW = YSS_URL_ANDROID + "wallet/doRaffle";
    /**
     * 获取衣豆明细列表
     */
    public static String GET_PEARS_LIST = YSS_URL_ANDROID + "wallet/getPeasDetail";
    /**
     * 引导提交身份证
     */
    public static String SUBMIT_CARD_GUID = YSS_URL_ANDROID + "wallet/addCardDoType";
    /**
     * 提现额度明细列表
     */
    public static String TIXIANEDUMINGXI = YSS_URL_ANDROID + "wallet/getExtractDetail";
    /**
     * 冻结提现额度明细列表
     */
    public static String TIXIANEDUMINGXIDONGJIE = YSS_URL_ANDROID + "wallet/getExtractUnDetail";

    /**
     * 抽奖页面 衣豆奖励列表 展示的25条
     */
    public static String GETYIDOUNEWDATA = YSS_URL_ANDROID + "wallet/getNewData";
    /**
     * 体现额度列表
     */
    public static String GETTIXINEDULIEBIAO = YSS_URL_ANDROID + "wallet/fkNewData";
    /**
     * 抽奖页面 额度奖励列表 展示的25条
     */
    public static String EXTRACTLIMITNEWDATA = YSS_URL_ANDROID + "wallet/extractNewData";
    /**
     * 喜好列表
     */
    public static String HOBBYLIE = YSS_URL_ANDROID + "shop/getUserHobbyData";
    /**
     * 获得热门标签
     */
    public static String GETHOTTAG = YSS_URL_ANDROID + "shop/getHotTag";
    /**
     * 购物车里获得我的喜欢列表
     */
    public static String GET_MY_LOVE_LIST = YSS_URL_ANDROID + "like/queryGuessLikeShop";
    /**
     * 购物车里修改颜色尺寸
     */
    public static String MODIFY_COLOR_SIZE = YSS_URL_ANDROID + "shopCart/updatePrefer";
    /**
     * 精品推荐列表
     */
    public static String LIKE_QUA = YSS_URL_ANDROID + "like/qua";
    /*****
     * 删除精品推荐
     */
    public static String DELUACC = YSS_URL_ANDROID + "like/delUAcc";
    /*****
     * 密友圈
     */
    public static String CIRCLEHOMEPAGE = YSS_URL_ANDROID + "fc/circleHomepage";
    /*****
     * 话题广场
     */
    public static String CIRCLETOPICSQUARE = YSS_URL_ANDROID + "fc/circleTopicSquare";
    /*****
     * 发帖接口
     */
    public static String ISSUE_TOPIC = YSS_URL_ANDROID + "fc/send";
    /*****
     * 评论接口
     */
    public static String SWEET_ISSUE_COMMENT_TIEZI = YSS_URL_ANDROID + "/fc/sendReply";
    /*****
     * 评论内回复接口
     */
    public static String SWEET_ISSUE_COMMENT_IN = YSS_URL_ANDROID + "/fc/addReply";
    /*****
     * 点赞接口
     */
    public static String SWEET_ZAN = YSS_URL_ANDROID + "/fc/addApplaud";
    /*****
     * 取消点赞接口
     */
    public static String SWEET_REMOVE_ZAN = YSS_URL_ANDROID + "/fc/removeApplaud";
    /*****
     * 帖子收藏接口
     */
    public static String ADD_COLLECT = YSS_URL_ANDROID + "fc/addCollect";
    /*****
     * 取消帖子收藏接口
     */
    public static String DEL_COLLECT = YSS_URL_ANDROID + "fc/delCollect";
    /*****
     * 密友圈内关注与取消关注接口
     */
    public static String SWEET_ATTENTION = YSS_URL_ANDROID + "/fc/addOrDelMiYou";
    /*****
     * 删除帖子
     */
    public static String DELETE_THEME = YSS_URL_ANDROID + "fc/deleteTheme";
    /****
     * 精选推荐 获取分享资格
     */
    public static String GET_SHARE_QUA = YSS_URL_ANDROID + "fc/getSA";
    /****
     * 密友圈首页 轮播图
     */
    public static String GET_INTIMATE_BANNER = YSS_URL_ANDROID + "fc/banner";
    /****
     * 举报帖子
     */
    public static String SWEET_REPORT = YSS_URL_ANDROID + "/fc/report";
    /****
     * 密友圈详情评论-热门评论
     */
    public static String SWEET_DETAIL_COMMENT_LIST_HOT = YSS_URL_ANDROID + "/fc/topicDetails";
    /****
     * 密友圈详情评论-最新评论
     */
    public static String SWEET_DETAIL_COMMENT_LIST_NEW = YSS_URL_ANDROID + "fc/latestComments";
    /****
     * 密友圈详情评论-只看楼主
     */
    public static String SWEET_DETAIL_COMMENT_LIST_HOST = YSS_URL_ANDROID + "fc/onlyLookBuilding";
    /**
     * 商品详情获取图片压缩比
     */
    public static String SHOP_DETAILS_IMAGE_RADIO = YSS_URL_ANDROID + "shop/getImgRate";

    /**
     * 集赞奖品
     */
    public static String QUERYCOLLECTIONPRAISE = YSS_URL_ANDROID + "signIn2_0/queryCollectionPraise";
    /**
     * 集赞排名
     */
    public static String QUERY_PRAISE_RANKING = YSS_URL_ANDROID + "point/praise_List";
    /**
     * 集赞额度奖励
     */
    public static String QUERY_PRAISE_EXTRACT_LIST = YSS_URL_ANDROID + "point/popup_List";
    /**
     * 集赞奖励弹窗（点赞数，获得奖励）
     */
    public static String QUERY_PRAISE_MONEY = YSS_URL_ANDROID + "point/dailyRewards";
//    /*
//     * 集赞分享 获取文案
//     */
//    public static String POINT_SHARE_CONTENT = YSS_URL_ANDROID + "signIn2_0/queryText";

    /*
     * 获取衣豆抽奖减半信息 并获取 减半过期时间
     */
    public static String YI_DOU_HALVE = YSS_URL_ANDROID + "wallet/yiDouHalve";

    /*
     * 衣豆减半获取资格
     */
    public static String YI_DOU_HALVE_AGO = YSS_URL_ANDROID + "wallet/yiDouHalveAgo";

    /*
     * 夺宝分享成功记录
     */
    public static String INDIANA_SHARE_RECORD = YSS_URL_ANDROID + "treasures/shareAdd";

    /*
     * 夺宝分享相关参数
     */
    public static String INDIANA_SHARE_DATA = YSS_URL_ANDROID + "treasures/shareQuery";
    /*
     *获奖感言列表
     */
    public static String PRAISE_VOICE_LIST = YSS_URL_ANDROID + "point/praise_voice_list";

    /***
     * 活动商品提交订单
     */
    public static String SIGN_GROUP_SHOP_SUBMIT = YSS_URL_ANDROID + "order/addOrderDp";
    /**
     * 一元拼团提交订单
     */
    public static String ONEBU_SUBNIT_ORDER = YSS_URL_ANDROID + "order/addOrderListPT";

    /**
     * 获取 新的拼团详情拼团列表
     */
    public static String GROUPSQUERYBYROLL = YSS_URL_ANDROID + "order/queryByRoll";

    /**
     * 获取 新的拼团详情拼团列表(参团 通过拼团编号查询)
     */
    public static String GROUPSQUERYBYROLLTOCODE = YSS_URL_ANDROID + "order/queryByRollToCode";
    /**
     * 获取 拼团初始化数据
     */
    public static String GROUP_INIT_DATA = YSS_URL_ANDROID + "order/getRollInit";
    /**
     * 获取 夺宝中奖信息
     */
    public static String INDIANA_DIALOG_MESSAGE = YSS_URL_ANDROID + "treasures/getMsg";
    /**
     * 未登录获取第一次抽奖情况
     */
    public static String REALRAFFLECHANNEL = YSS_URL_ANDROID + "wallet/realRaffleChannel";

    /**
     * 文案地址 文案统一在又拍云获取
     */
    public static String PAPERWORK_TEXT_CONTENT = YUrl.imgurl + "paperwork/paperwork.json";

    /**
     * 获取分享链接H5域名
     */
    public static String GETDOMAIN = YSS_URL_ANDROID + "cfg/getdomain";

    /**
     * 一元购开启状态（-1为未开启）
     */
    public static String GETONYUANSTATUS = YSS_URL_ANDROID + "cfg/on_off_3_7";
    /**
     * 获取一元购可抵扣的全部余额
     */
    public static String GETALLDIKOU = YSS_URL_ANDROID + "order/getZeroOrderDeductible";
    /**
     * 1元购抽奖通知
     */
    public static String UPDATE_ONEBUYCHOUJIANG = YSS_URL_ANDROID + "order/updateOrderOneFrom";

    /**
     * 1元购抽奖通知(第一次专用)
     */
    public static String UPDATE_ONEBUYCHOUJIANG_FIRST = YSS_URL_ANDROID + "order/updateOrderOneFromCZ";


    /**
     * 一元拼团订单初始化
     */
    public static String INIT_ONEBUY_ORDER = YSS_URL_ANDROID + "order/lotteryDraw";


    /**
     * 购买转盘次数后初始化和查次数
     */
    public static String INIT_ONEBUY_ORDER_BUG_COUNT = YSS_URL_ANDROID + "order/selFreeDrawNum";
    public static String INIT_ONEBUY_ORDER2 = YSS_URL_ANDROID + "order/lotteryDraw2";

    /**
     * 反馈后台抽奖结果
     */
    public static String FEEDBACK_ONEBUY_CHOUJIANG = YSS_URL_ANDROID + "order/luckDraw";
    public static String FEEDBACK_ONEBUY_CHOUJIANG2 = YSS_URL_ANDROID + "order/luckDraw";


    /**
     * 拼团通知 order/getOrderStatus
     */
    public static String PINGTUAN_TONGZHI = YSS_URL_ANDROID + "order/getOrderStatus";

    /**
     * 拼团通知 召唤机器人参团
     */
    public static String CALL_MATCHINE_CANTUAN = YSS_URL_ANDROID + "order/addOrderFake";
    /**
     * 查询是否有交易记录
     */
    public static String QUERY_HAS_JYJL = YSS_URL_ANDROID + "order/getNewUserOrder";


    /**
     * 首页3商品列表
     */
    public static String HOMEPAGE3_SHOPLIST = YSS_URL_ANDROID + "homePage3shop/dataShopList";
    public static String HOMEPAGE3_SHOPLIST_FIRST__DIAMOND = YSS_URL_ANDROID + "homePage2FreeShop/dataShopList";

    /**
     * 获取拼团一元购优惠券
     */
    public static String GET_YHQ_PT = YSS_URL_ANDROID + "coupon/getRollCoupon";

    /**
     * 查询提现详情
     */
    public static String QUERY_TIXIAN_DETAIL = YSS_URL_ANDROID + "wallet/selDepositOrder";
    /**
     * 根据商品查询会员相关信息
     */
    public static String QUERY_VIP_INFO = YSS_URL_ANDROID + "userVipCard/vipBuyShopInfo";

    public static String QUERY_VIP_INFO_FL = YSS_URL_ANDROID + "userVipCard/userIsVip2";


    /**
     * 查询是否是会员
     */
    public static String QUERY_VIP_INFO2 = YSS_URL_ANDROID + "userVipCard/userIsVip";


    /**
     * 是否要去免费领 （会员）
     */
    public static String NEED_JUM_FREE_LING = YSS_URL_ANDROID +"userVipCard/forcedJumpFreePage";//是否需要调过去免费领

    /**
     * 查询是否是会员
     */
    public static String QUERY_DAKA = YSS_URL_ANDROID + "clockIn/clockInTodayByUserId";


    /**
     * 查询打卡
     */
    public static String QUERY_SIGN_DAKA = YSS_URL_ANDROID + "clockIn/queryList";

    /**
     * 会员分享免费领  分享完成之后 跳转盘之前调用
     */
    public static String UPDATE_ORDER_FRIENDS_SHARE = YSS_URL_ANDROID + "order/updateOrderFriendsShare";


    /**
     * 好友奖励
     */
    public static String FRIEND_JIANGLI_LIST = YSS_URL_ANDROID + "wallet/getExtremeTiChengInfo";

    /**
     * 好友奖励头部数据
     */
    public static String FRIEND_JIANGLI_DAY_AND_COUNT = YSS_URL_ANDROID + "wallet/getExtremeToDayCount";


    /**
     * 查询是否要提示奖励金
     */
    public static String GET_REWARD_DRAW_POP = YSS_URL_ANDROID + "userVipCard/rewardDrawPop";

    /**
     * 获取客服微信号
     */
    public static String GET_WXH = YSS_URL_ANDROID + "order/queryWxhNumber";
    /**
     * 佣金明细顶部数据
     */
    public static String YJMX_HEADER_DATA = YSS_URL_ANDROID + "wallet/getCommissionTips";

    /**
     * 查询当前用户有效的团
     */
    public static String QUERY_GOOD_TUAN = YSS_URL_ANDROID + "order/getUserRollOrder";

    /**
     * 虚拟抽奖同步数据
     */
    public static String SYNC_CJ_DATA = YSS_URL_ANDROID + "wallet/synchronizationUnLoginUserRaffle";
    /**
     * 查询抽奖提现次数和相关数据
     */
    public static String QUERY_NEW_CJTX_COUNT = YSS_URL_ANDROID + "wallet/queryUserLotteryQualification";

    /**
     * 查询提前抽奖是否抽完
     */
    public static String QUERY_TIQIAN_TXCJ = YSS_URL_ANDROID + "wallet/judgeUserFirstIntoRaffle";
    /**
     * 查询所有用户提前抽奖是否存在
     */
    public static String QUERY_TIQIAN_TXCJ_EXIST = YSS_URL_ANDROID + "wallet/newRaffleIsExist";


    /**
     * 查询抽奖（提现）中奖情况
     */
    public static String QUERY_TIQIAN_TXCJ_MONEY = YSS_URL_ANDROID + "wallet/getnewUserRaffleMoney";

    /**
     * 提现抽奖中奖后发放
     */
    public static String QUERY_TIQIAN_TXCJ_MONEY_FAFANG = YSS_URL_ANDROID + "wallet/newUserDoRaffle";

    /**
     * 查询道具卡升级钻石卡数据
     */
    public static String QUERY_DAOJUKASHENGJI_ZUANSHI_DATA = YSS_URL_ANDROID + "userVipCard/selCardPurchSucc";


    /**
     * 查询当前会员卡价格
     */
    public static String QUERY_CURRENT_VIP_PRICE_DATA = YSS_URL_ANDROID + "userVipCard/selActualPrice";

    /**
     * 刷新赚钱任务
     */
    public static String REFRESH_SIGN_LIST = YSS_URL_ANDROID + "wallet/noviceNewbieTask";


    /**
     * 查询最新的拼团情况
     */
    public static String QUERY_LAST_PT = YSS_URL_ANDROID + "order/getUserLatelyRoll";

    /**
     * 查询任务列表是否刷新
     */
    public static String QUERY_SIGN_LIST_REFRESH = YSS_URL_ANDROID + "signIn2_0/taskteFreshFlag";
    /**
     * 查道具卡情况
     */
    public static String QUERY_DJK_DETAIL = YSS_URL_ANDROID + "userVipCard/selPropCardNum";
    /**
     * 查询是否有拼团失败的订单
     */
    public static String QUERY_PT_INFO = YSS_URL_ANDROID + "order/queryRollFail";

    /**
     * 申请供款
     */
    public static String CLOUD_API_WAR_SUPPLYMATERIAL_SUPPLY = YSS_URL_ANDROID + "cloud_api_war/supplyMaterial/supply";
}
