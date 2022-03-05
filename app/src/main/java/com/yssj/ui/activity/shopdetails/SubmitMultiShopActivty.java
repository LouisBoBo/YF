package com.yssj.ui.activity.shopdetails;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.yssj.Constants;
import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.data.DBService;
import com.yssj.entity.DeliveryAddress;
import com.yssj.entity.ShopCart;
import com.yssj.entity.Store;
import com.yssj.entity.UserInfo;
import com.yssj.huanxin.PublicUtil;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelL;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.activity.GroupsDetailsActivity;
import com.yssj.ui.activity.WithdrawalLimitActivity;
import com.yssj.ui.activity.infos.UsefulCouponsActivity;
import com.yssj.ui.activity.logins.RegisterFragment;
import com.yssj.ui.activity.main.SignGroupShopActivity;
import com.yssj.ui.activity.setting.ManMyDeliverAddr;
import com.yssj.ui.activity.setting.SetDeliverAddressActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.YCache;
import com.yssj.utils.YunYingTongJi;
import com.yssj.utils.sqlite.ShopCartDao;
import com.yssj.wxpay.WxPayUtil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * 提交多订单
 */
@SuppressLint("StringFormatMatches")
public class SubmitMultiShopActivty extends BasicActivity implements OnCheckedChangeListener {
    private TextView tv_name, tv_phone, tv_receiver_addr;

    private LinearLayout lin_receiver_addr;
    int m = 0;
    private DBService db = new DBService(this);
    private RelativeLayout mTouBg;
    private TextView tv_sum, tv_pro_name, tv_pro_descri;
    private ImageView img_pro_pic;
    private String mShop_code;
    private Double mKickback;
    private LinearLayout btn_pay;
    private TextView tv_settle_account;
    private TextView total_account;

    private TextView tvTitle_base;
    private LinearLayout img_back;

    private static final int SDK_PAY_FLAG = 1;
    private AlertDialog dialog2 = null, dialogPay = null;
    private DeliveryAddress dAddress;
    private HashMap<String, String> addResult;
    private String message = "";
    private Store store;
    private String orderNo;
    private String mDef_pic;
    Map<String, String> resultunifiedorder;

    IWXAPI msgApi;// 微信api

    private int addressId = 0;

    private Intent intent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
    private LinearLayout lin_set_addr;

    private List<ShopCart> listGoods;

    private LinearLayout container;
    // TODO:
    // private static Dialog dialogShare;
    private List<ToggleButton> mListTB = new ArrayList<ToggleButton>();
    private boolean mIsChecked = true; // 抵用券开关的状态
    private int mVouchersCount = 0;// 总共使用的抵用券
    private List<ShopCart> mListShopCart = new ArrayList<ShopCart>();// 用来存储使用抵用券的商品集合
    private RelativeLayout rel_show_share;
    private ImageView img_count_down;
    String SUBMIT = "SubmitMultiShopActivty";
    private TimeCount time;
    private int[] countDownBg = {R.drawable.count_down_1, R.drawable.count_down_2, R.drawable.count_down_3,
            R.drawable.count_down_3};
    private TextView mSubmitTotal;// 总价
    private TextView mPockage;// 邮费
    private TextView mVoucachers, mCoupon, mIntegral, mCouponMoney, tv_gold_voucaher;// 抵用券，优惠券，积分,优惠券价钱
    private TextView mShareVoucachers;
    private RelativeLayout mRlCoupon, mRlIntegral;// 控制优惠券与积分的显示与隐藏
    private double mTotal = 0;// 总价
    private int mTen = 0, mFive = 0, mTwo = 0, mOne = 0;// 10，5，2，1元优惠券数量
    private int mTenUse = 0, mFiveUse = 0, mTwoUse = 0, mOneUse = 0;// 每个商品使用的优惠券数量
    private HashMap<String, Integer> mMapTen = new HashMap<String, Integer>();
    private HashMap<String, Integer> mMapFive = new HashMap<String, Integer>();
    private HashMap<String, Integer> mMapTwo = new HashMap<String, Integer>();
    private HashMap<String, Integer> mMapOne = new HashMap<String, Integer>();
    // private RelativeLayout rl_discount_coupons;

    double sum = 0.00;
    private HashMap<Integer, List<ShopCart>> mapListGood = new HashMap<Integer, List<ShopCart>>();

    private HashMap<Integer, String> mapMsg = new HashMap<Integer, String>();

    private HashMap<Integer, EditText> mapEdit = new HashMap<Integer, EditText>();

    // private HashMap<Integer, EditText> mapEditInte = new HashMap<Integer,
    // EditText>();

    private HashMap<Integer, HashMap<String, Object>> mapCoups = new HashMap<Integer, HashMap<String, Object>>();// 提交的优惠券

    // private HashMap<Integer, String> mapInteg = new HashMap<Integer,
    // String>();

    private ImageView ivBack;

    private RelativeLayout rel_name_phone;
    private int CODE_PAY;

    private RelativeLayout rlTotal;

    private RelativeLayout rlData, rl_dapeigou;

    int shopNum = 0;

    private int myIntegCount = 0;// 我的积分值

    private HashMap<String, Object> mapCoupon;

    public static SubmitMultiShopActivty instance;

    private List<ShopCart> shopCartMeal = new ArrayList<ShopCart>();

    private String orderToken;

    private RelativeLayout rl_discount_coupon, rel_havedapei;
    private RelativeLayout submit_rl_group_buy, submit_rl_need_pay;//活动商品拼团减价，活动商品还需支付
    private TextView submit_tv_buy;
    private TextView submit_tv_group_price, submit_tv_need_pay_price;
    private TextView tv_discount_coupon_count, tv_integral_notice, tv_time, youhui_jine, submit_dapeigou,
            jiesuan_jiesheng, mTvItegrals, mTvBottomItegrals, tv_discount_coupon, tv_special_price;
    private LinearLayout mShareBack;
    private TextView tv_money_notice_new;
    private int mDiscountInte = 0;
    private double mPriceCount = 0.0;// 本单价格之和
    private double inteDiscount = 0.0;// 使用的积分抵扣的钱数
    private double discount = 0.0;// 获取到的优惠券的价格
    private Double cartmYouhuiMoneyCount;
    private Double mYouhuiMoneyCount;
    private int integral;
    private Boolean isDapei;
    private Boolean useFlag = false;// 金券使用标记
    // private SwitchButton sbt;
    private ToggleButton mTgbs, mMoneyTgb;
    private boolean isAddIntegral = true;
    // private UMSocialService mController;
    boolean flag = false;

    private MyTimerTask mTask;
    private RelativeLayout rl_new_money;// 上面余额支付显示
    private TextView tv_new_money_notice;// 可抵用余额
    private RelativeLayout submit_rl_money;// 下面 余额支付显示
    private TextView submit_money;// 余额翻倍期间使用了多少余额
    private boolean mIsQulification;// 是否具有余额翻倍的资格
    private int mTwofoldness;// 余额翻倍的倍数
    private long mEndDate;// 余额翻倍的结束时间值
    private int mIsOpen;// 0,没有开启;1，已经开启
    private double mMyMoney;
    private boolean mIsGold = false;// 是否积分转换成了金币

    private int core;
    private boolean mCoreFlag = false;// true代表通过关闭积分按钮可以使用了金券
    private boolean mDiyongFlag = false;// true代表通过关闭抵用券可以使用金券
    private boolean isSignActiveShop = false;
    private boolean mHuoDongFlag = false;// 用来区分是否是活动商品跳过来的，true代表是，false代表其他
    private boolean mIsTwoGroup = false;// 用来判断是否是活动商品自己发起2人团购买的
    private String rollCode = "0";// 活动商品购买的方式
    private double mLimitMoney = 0;// 此单限制可使用的余额
    private double mUseMoney = 0;// 此单使用的余额
    String str_jinbi_endDate = "-1";
    private Double mGroupPrice = 9.90;
    private int groupFlag = 0;//同时满足mHuoDongFlag=true,groupFlag!=0;代表拼团购购买

    private ImageView ivBalanceLottory;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null !=aBar){
            aBar.hide();
        }
        context = this;
        instance = this;
        flag = false;
        groupFlag = getIntent().getIntExtra("groupFlag", 0);
        mHuoDongFlag = getIntent().getBooleanExtra("isSignActiveShop", false);
        mIsTwoGroup = getIntent().getBooleanExtra("mIsTwoGroup", false);
        rollCode = getIntent().getStringExtra("rollCode");
        mGroupPrice = getIntent().getDoubleExtra("mSignGroupsPrice", 9.90);
        // // 获取抵用券
        // if (!mHuoDongFlag) {
        // getVoucachersCount();
        // }
        orderToken = YCache.getOrderToken(this);
        setContentView(R.layout.submit_multi_goods);
        mIsQulification = SharedPreferencesUtil.getBooleanData(context, Pref.IS_QULIFICATION, false);
        mTwofoldness = Integer.parseInt(SharedPreferencesUtil.getStringData(context, Pref.TWOFOLDNESS, -1 + ""));
        mEndDate = Long.parseLong(SharedPreferencesUtil.getStringData(context, Pref.END_DATE, -1 + ""));
        mIsOpen = Integer.parseInt(SharedPreferencesUtil.getStringData(context, Pref.IS_OPEN, -1 + ""));
        submit_rl_money = (RelativeLayout) findViewById(R.id.submit_rl_money);
        tv_new_money_notice = (TextView) findViewById(R.id.tv_new_money_notice);
        submit_money = (TextView) findViewById(R.id.submit_money);
        rl_new_money = (RelativeLayout) findViewById(R.id.rl_new_money);
        img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        jiesuan_jiesheng = (TextView) findViewById(R.id.jiesuan_jiesheng);
        tv_special_price = (TextView) findViewById(R.id.tv_special_price);
        mShareBack = (LinearLayout) findViewById(R.id.img_back_share);
        mShareBack.setOnClickListener(this);
        rl_dapeigou = (RelativeLayout) findViewById(R.id.rl_dapeigou);
        submit_dapeigou = (TextView) findViewById(R.id.submit_dapeigou);
        btn_pay = (LinearLayout) findViewById(R.id.btn_pay);// 支付按钮
        recLen = getIntent().getLongExtra("Time", 0);

        youhui_jine = (TextView) findViewById(R.id.youhui_jine);

        tv_time = (TextView) findViewById(R.id.tv_time);
        tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("确认订单");
        // MyLogYiFu.e("TAG", "确认下单");

        ivBalanceLottory = (ImageView) findViewById(R.id.img_balance_lottery);

        listGoods = (List<ShopCart>) getIntent().getExtras().getSerializable("listGoods");

        isDapei = getIntent().getBooleanExtra("isDapei", false);

        // bundle.putBoolean("isDapei", true);

        cartmYouhuiMoneyCount = getIntent().getDoubleExtra("mYouhuiMoneyCount", 0.0);
        mYouhuiMoneyCount = Double.parseDouble(new java.text.DecimalFormat("#0.00").format(cartmYouhuiMoneyCount));//

        for (ShopCart shopCart : listGoods) {
            if (TextUtils.isEmpty(shopCart.getP_code())) {
                List<ShopCart> list = mapListGood.get(shopCart.getSupp_id());
                if (list != null) {
                    list.add(shopCart);
                } else {
                    List<ShopCart> lista = new ArrayList<ShopCart>();
                    lista.add(shopCart);
                    mapListGood.put(shopCart.getSupp_id(), lista);
                }
            } else {
                shopCartMeal.add(shopCart);
            }

            // if (isDapei) {
            // core += Integer.parseInt(shopCart.getCore());
            //
            // }

            sum += shopCart.getShop_se_price() * shopCart.getShop_num();// 计算显示的总金额
            // mLimitMoney = sum * 0.1;
            mPriceCount += shopCart.getShop_se_price() * shopCart.getShop_num();// 商品总价

        }
        // if (isDapei) {
        // mDiscountInte = (int) (mPriceCount * 50 * 0.9);
        // } else {
        // mDiscountInte = (int) (mPriceCount * 50);
        // }
        if (null != YCache.getCacheUserSafe(context)) {
            if (YCache.getCacheUserSafe(context).getIs_member().equals("2")) {// 判断是否是会员
                sum = sum * 0.95;// 当是会员的时候 打95折
            }
        }

        rlData = (RelativeLayout) findViewById(R.id.rl_data);

        rel_havedapei = (RelativeLayout) findViewById(R.id.rel_havedapei);

        msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp(WxPayUtil.APP_ID);
        initData(0);

        // 付款下单计时
        if (mTask != null) {
            mTask.cancel();
            mTask = new MyTimerTask();
        } else {
            mTask = new MyTimerTask();
        }
        timer.schedule(mTask, 0, 1000); // timeTask

        store = YCache.getCacheStoreSafe(SubmitMultiShopActivty.this);

        if (mHuoDongFlag && groupFlag != 0) {
            //拼团商品下单不显示悬浮红包
        } else {
            //显示悬浮红包
            PublicUtil.getBalanceNum(this, ivBalanceLottory, false);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        isClick = false;
        HomeWatcherReceiver.registerHomeKeyReceiver(this);
        SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1053");
        TongJiUtils.TongJi(this, 11 + "");
        LogYiFu.e("TongJiNew", 11 + "");//到达提交订单界面
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        HomeWatcherReceiver.unregisterHomeKeyReceiver(this);
        TongJiUtils.TongJi(this, 111 + "");
        LogYiFu.e("TongJiNew", 111 + "");//跳出提交订单界面

    }

    private void initData(final int requestCode) {
        rlData.setVisibility(View.GONE);
        LogYiFu.i("TAG", "多订单");
        new SAsyncTask<Void, Void, HashMap<String, String>>(this, R.string.wait) {// 获取地址

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getDefaultDeliverAddr(context);
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                rlData.setVisibility(View.VISIBLE);
                if (requestCode == 1002) {// 设置地址
                    SubmitMultiShopActivty.this.addResult = result;
                    // 设置地址
                    setDeliverAddress(result, null);
                    return;
                } else {
                    initView(result);
                }

            }

        }.execute();

        // 对要付款的物品进行分类 同一家发货，不同家发货
        // listGoods = (List<ShopCart>) getIntent().getSerializableExtra(
        // "listGoods");

    }

    private void initView(HashMap<String, String> result) {
        // TODO:
        tv_money_notice_new = (TextView) findViewById(R.id.tv_money_notice_new);
        mTouBg = (RelativeLayout) findViewById(R.id.submit_bg);
        mTouBg.setBackgroundColor(Color.WHITE);
        mShareVoucachers = (TextView) findViewById(R.id.submit_voucahers_tv);
        mRlIntegral = (RelativeLayout) findViewById(R.id.submit_rl_integral);
        mRlCoupon = (RelativeLayout) findViewById(R.id.submit_rl_voucahers);
        mCouponMoney = (TextView) findViewById(R.id.tv_discount_coupon_count_money);
        rel_show_share = (RelativeLayout) findViewById(R.id.rel_show_share);
        img_count_down = (ImageView) findViewById(R.id.img_count_down);
        mSubmitTotal = (TextView) findViewById(R.id.submit_total);
        mPockage = (TextView) findViewById(R.id.submit_pockage);
        mVoucachers = (TextView) findViewById(R.id.submit_voucahers);
        mCoupon = (TextView) findViewById(R.id.submit_coupon);
        tv_gold_voucaher = (TextView) findViewById(R.id.tv_gold_voucaher);
        mIntegral = (TextView) findViewById(R.id.submit_integral);
        container = (LinearLayout) findViewById(R.id.container);
        tv_name = (TextView) findViewById(R.id.tv_name);// 收件人
        tv_phone = (TextView) findViewById(R.id.tv_phone);// 收件人电话
        tv_receiver_addr = (TextView) findViewById(R.id.tv_receiver_addr);// 收件地址
        lin_receiver_addr = (LinearLayout) findViewById(R.id.lin_receiver_addr);
        lin_receiver_addr.setOnClickListener(this);
        lin_set_addr = (LinearLayout) findViewById(R.id.lin_set_addr);
        lin_set_addr.setOnClickListener(this);
        this.addResult = result;
        // dAddress = result.get(0);
        //TODO:_MODIFY_不知道是否还区分活动
        /*if (mHuoDongFlag) {
            btn_pay.setOnClickListener(this);
        }*/
        btn_pay.setOnClickListener(this);

        tv_settle_account = (TextView) findViewById(R.id.tv_settle_account);// 合计多少钱
        total_account = (TextView) findViewById(R.id.total_account);// 合计多少货物多少钱

        rel_name_phone = (RelativeLayout) findViewById(R.id.rel_name_phone);

        tv_discount_coupon_count = (TextView) findViewById(R.id.tv_discount_coupon_count);
        tv_discount_coupon = (TextView) findViewById(R.id.tv_discount_coupon);
        tv_integral_notice = (TextView) findViewById(R.id.tv_integral_notice);
        mTvItegrals = (TextView) findViewById(R.id.tv_integrals);
        mTvBottomItegrals = (TextView) findViewById(R.id.tv_bottom_itegrals);
        tv_integral_notice.setText("可用积分:" + 0 + "   可抵用¥" + 0.0);
        mIntegral.setText("-¥" + 0.0);
        rl_discount_coupon = (RelativeLayout) findViewById(R.id.rl_discount_coupon);
        rl_discount_coupon.setOnClickListener(this);
        submit_rl_group_buy = (RelativeLayout) findViewById(R.id.submit_rl_group_buy);
        submit_rl_need_pay = (RelativeLayout) findViewById(R.id.submit_rl_need_pay);
        submit_tv_group_price = (TextView) findViewById(R.id.submit_tv_group_price);
        submit_tv_need_pay_price = (TextView) findViewById(R.id.submit_tv_need_pay_price);
        submit_tv_buy = (TextView) findViewById(R.id.submit_tv_buy);
        mTgbs = (ToggleButton) findViewById(R.id.tgb);
        mMoneyTgb = (ToggleButton) findViewById(R.id.tgb_money);
        mMoneyTgb.setOnCheckedChangeListener(this);
        mTgbs.setOnCheckedChangeListener(this);
        // mTgbs.setChecked(true);
        // 设置地址
        setDeliverAddress(result, null);
        initOther();
        if (mHuoDongFlag) {// 活动商品不能使用任何优惠（积分，抵用券...）方式
            findViewById(R.id.rl_integral).setVisibility(View.GONE);// 积分
            findViewById(R.id.rl_coucahers).setVisibility(View.GONE);// 优惠券
            findViewById(R.id.rl_money_new).setVisibility(View.GONE);// 余额支付
            findViewById(R.id.v_line1).setVisibility(View.GONE);
            findViewById(R.id.v_line2).setVisibility(View.GONE);
            findViewById(R.id.v_line_money).setVisibility(View.GONE);
            mRlIntegral.setVisibility(View.GONE);
            mRlCoupon.setVisibility(View.GONE);
            rl_discount_coupon.setVisibility(View.GONE);
            rl_dapeigou.setVisibility(View.GONE);
            rel_havedapei.setVisibility(View.GONE);
            jiesuan_jiesheng.setVisibility(View.GONE);

            if (groupFlag != 0) {
                tv_special_price.setVisibility(View.GONE);
                submit_rl_group_buy.setVisibility(View.VISIBLE);
                submit_rl_need_pay.setVisibility(View.VISIBLE);
                submit_tv_buy.setText("立即参与拼团购");
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                int width = dm.widthPixels;
                btn_pay.getLayoutParams().width = width / 2;
            }
            submit_rl_money.setVisibility(View.GONE);

//            tv_settle_account.setGravity(Gravity.RIGHT);
//            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//            params.setMargins(0,0, DP2SPUtil.dp2px(SubmitMultiShopActivty.this,10),0);
        } else {
            dapeigou();// 搭配购立即下单时的显示
            // queryMyMomeny();
            getOrderMoney();
            submit_rl_money.setVisibility(View.VISIBLE);
        }
    }

    private void dapeigou() {
        if (mYouhuiMoneyCount > 0) {// 存在搭配购
            String matchDiscount = SharedPreferencesUtil.getStringData(context, Pref.DPZHEKOU, "0.95");
            if ("0".equals(matchDiscount)) {
                matchDiscount = "0.95";
            }
            rel_havedapei.setVisibility(View.VISIBLE);
            youhui_jine.setText("已享受" + new DecimalFormat("#0.#").format(Double.parseDouble(matchDiscount) * 10)
                    + "折优惠，为你节省¥" + mYouhuiMoneyCount + "元");
            rl_dapeigou.setVisibility(View.VISIBLE);
            submit_dapeigou.setText("-¥" + mYouhuiMoneyCount);
            jiesuan_jiesheng.setVisibility(View.VISIBLE);
            jiesuan_jiesheng.setText("为你节省¥" + mYouhuiMoneyCount);

        } else {
            rel_havedapei.setVisibility(View.GONE);
            rl_dapeigou.setVisibility(View.GONE);
            submit_dapeigou.setVisibility(View.GONE);
            jiesuan_jiesheng.setVisibility(View.GONE);
        }

    }

    private void initOther() {
        for (int i = 0; i < listGoods.size(); i++) {
            ShopCart cart = listGoods.get(i);
            shopNum = shopNum + cart.getShop_num();
            // sum = sum + (cart.getShop_num() * cart.getShop_se_price());
        }

        addView(mapListGood, container, null);
    }

    private HashMap<String, String> mapGold;

    /**
     * 得到我的积分
     */
    private void getMyIntegral() {

        new SAsyncTask<Void, Void, Integer>((FragmentActivity) context, 0) {

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected Integer doInBackground(FragmentActivity context, Void... params) throws Exception {
                mSystemTimeMap = ComModel2.getSystemTime(SubmitMultiShopActivty.this);
                return ComModel2.getMyIntegral(context);
            }

            @Override
            protected void onPostExecute(final FragmentActivity context, final Integer result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null) {
                    btn_pay.setOnClickListener(SubmitMultiShopActivty.this);
                    getJiFen(result);
                }
                // getProxCoup(inteDiscount + money);// 匹配优惠券
            }

        }.execute();

    }

    public void getJiFen(int myJifen) {
        if (isDapei) {
            mDiscountInte = (int) ((mPriceCount * 0.9 - discount) * 50);
        } else {
            mDiscountInte = (int) ((mPriceCount - discount) * 50);
        }
        // TODO:
        str_jinbi_endDate = SharedPreferencesUtil.getStringData(SubmitMultiShopActivty.this, Pref.JINBI_END_DATE, "-1");
        if (null == str_jinbi_endDate || "".equals(str_jinbi_endDate)) {
            str_jinbi_endDate = "-1";
        }
        long jinbi_endDate = Long.parseLong(str_jinbi_endDate);
        myIntegCount = myJifen;
        if (mSystemTimeMap != null && mSystemTimeMap.size() > 0 && (Long) mSystemTimeMap.get("now") < jinbi_endDate) {
            mIsGold = true;
        }
        if (mIsGold) {
            mTvItegrals.setText("金币");
            mTvBottomItegrals.setText("金币");
        } else {
            mTvBottomItegrals.setText("积分");
            mTvItegrals.setText("积分");
        }
        if (myIntegCount < 1) {
            if (mIsGold && myIntegCount > 0) {// 开启了积分变金币(金币使用无下限,全部按照我的积分来算)
                inteDiscount = 0.01 * myIntegCount;
                if (inteDiscount > sum) {
                    inteDiscount = sum - 0.01;
                    integral = (int) (inteDiscount * 100);
                } else {
                    integral = myIntegCount;
                }
                tv_integral_notice.setText(
                        "可用金币:" + integral + "   可抵用¥" + new java.text.DecimalFormat("#0.00").format(inteDiscount));
                mRlIntegral.setVisibility(View.VISIBLE);
                mIntegral.setText("-¥" + new java.text.DecimalFormat("#0.00").format(inteDiscount) + "");
                sum -= inteDiscount;

            } else {// 正常情况下至少使用500积分
                mTgbs.setChecked(false);
                mTgbs.setEnabled(false);
                mRlIntegral.setVisibility(View.GONE);
            }

        } else {

            // // 测试搭配
            // if (isDapei) {
            // mDiscountInte = core;
            // }

            if (mDiscountInte >= myIntegCount) {// 本单可用积分大于 我所持有积分
                // 故按照我的积分计算
                if (mIsGold) {// 积分已转换成金币
                    if (myIntegCount > 600) {// 最多只能使用600金币（100金币==1元）
                        inteDiscount = 0.01 * 600;
                        if (inteDiscount > sum) {
                            inteDiscount = sum;
                            // myIntegCount=(int)
                            // (inteDiscount*100);111
                            integral = (int) (inteDiscount * 100);
                            tv_integral_notice.setText("可用金币:" + integral + "   可抵用¥"
                                    + new java.text.DecimalFormat("#0.00").format(inteDiscount));
                        } else {
                            tv_integral_notice.setText("可用金币:" + 600 + "   可抵用¥"
                                    + new java.text.DecimalFormat("#0.00").format(inteDiscount));
                            integral = 600;
                        }

                        mRlIntegral.setVisibility(View.VISIBLE);
                        mIntegral.setText("-¥" + new java.text.DecimalFormat("#0.00").format(inteDiscount) + "");
                        sum -= inteDiscount;
                    } else {
                        inteDiscount = 0.01 * myIntegCount;
                        if (inteDiscount > sum) {
                            inteDiscount = sum;
                            integral = (int) (inteDiscount * 100);
                        } else {
                            integral = myIntegCount;
                        }
                        tv_integral_notice.setText("可用金币:" + integral + "   可抵用¥"
                                + new java.text.DecimalFormat("#0.00").format(inteDiscount));
                        mRlIntegral.setVisibility(View.VISIBLE);
                        mIntegral.setText("-¥" + new java.text.DecimalFormat("#0.00").format(inteDiscount) + "");
                        sum -= inteDiscount;
                    }
                } else {// 积分未转变为金币

                    inteDiscount = 0.01 * myIntegCount;

                    tv_integral_notice.setText("可用积分:" + myIntegCount + "   可抵用¥"
                            + new java.text.DecimalFormat("#0.00").format(inteDiscount));
                    mRlIntegral.setVisibility(View.VISIBLE);
                    mIntegral.setText("-¥" + new java.text.DecimalFormat("#0.00").format(inteDiscount) + "");
                    integral = myIntegCount;
                    sum -= inteDiscount;

                }

            } else {// 本单可用积分小于我所持有的积分，按照本单积分计算
                if (mIsGold) {// 积分已转换成金币（按照我的积分来算）
                    if (myIntegCount > 600) {// 最多只能使用600金币（100金币==1元）
                        inteDiscount = 0.01 * 600;
                        if (inteDiscount > sum) {
                            inteDiscount = sum;
                            integral = (int) (inteDiscount * 100);
                            tv_integral_notice.setText("可用金币:" + integral + "   可抵用¥"
                                    + new java.text.DecimalFormat("#0.00").format(inteDiscount));
                        } else {
                            integral = 600;
                            tv_integral_notice.setText("可用金币:" + 600 + "   可抵用¥"
                                    + new java.text.DecimalFormat("#0.00").format(inteDiscount));
                        }
                        mRlIntegral.setVisibility(View.VISIBLE);
                        mIntegral.setText("-¥" + new java.text.DecimalFormat("#0.00").format(inteDiscount) + "");
                        sum -= inteDiscount;
                    } else {
                        inteDiscount = 0.01 * myIntegCount;
                        if (inteDiscount > sum) {
                            inteDiscount = sum;
                            integral = (int) (inteDiscount * 100);
                        } else {
                            integral = myIntegCount;
                        }
                        tv_integral_notice.setText("可用金币:" + integral + "   可抵用¥"
                                + new java.text.DecimalFormat("#0.00").format(inteDiscount));
                        mRlIntegral.setVisibility(View.VISIBLE);
                        mIntegral.setText("-¥" + new java.text.DecimalFormat("#0.00").format(inteDiscount) + "");
                        sum -= inteDiscount;
                    }

                } else {// 积分未转换成金币
                    if (mDiscountInte < 1) {
                        mTgbs.setChecked(false);
                        mTgbs.setEnabled(false);
                        mRlIntegral.setVisibility(View.GONE);
                    } else {
                        inteDiscount = 0.01 * mDiscountInte;
                        tv_integral_notice.setText("可用积分:" + mDiscountInte + "   可抵用¥"
                                + new java.text.DecimalFormat("#0.00").format(inteDiscount));
                        mRlIntegral.setVisibility(View.VISIBLE);
                        mIntegral.setText("-¥" + new java.text.DecimalFormat("#0.00").format(inteDiscount) + "");
                        integral = mDiscountInte;
                        sum -= inteDiscount;
                    }
                }
            }

            // total_account.setText(Html.fromHtml(
            // getString(R.string.total_account, shopNum, new
            // java.text.DecimalFormat("#0.00").format(sum))));
            // double sum2 = sum - mVouchersCount;
            // tv_settle_account.setText("实付款:¥" + new
            // java.text.DecimalFormat("#0.00").format(sum2 -
            // mYouhuiMoneyCount));
            setMoney(sum - mYouhuiMoneyCount - mVouchersCount, true);
        }

    }
    // 获取抵用券

    // private void getVoucachersCount() {
    // new SAsyncTask<Integer, Void, List<HashMap<String,
    // Object>>>((FragmentActivity) context, R.string.wait) {
    // @Override
    // protected List<HashMap<String, Object>> doInBackground(FragmentActivity
    // context, Integer... params)
    // throws Exception {
    // return ComModel2.DiYongQuanDialog(context);
    // }
    //
    // @Override
    // protected boolean isHandleException() {
    // return true;
    // }
    //
    // @Override
    // protected void onPostExecute(FragmentActivity context,
    // List<HashMap<String, Object>> result, Exception e) {
    // super.onPostExecute(context, result, e);
    //
    // if (e != null) {// 查询异常
    // } else {
    //
    // if (result != null) {
    // for (int i = 0; i < result.size(); i++) {
    // String price = result.get(i).get("price").toString();
    // String snum = result.get(i).get("snum").toString();
    // int num = Integer.valueOf(snum).intValue();
    //
    // if (price.equals("1")) {
    // mOne = num;
    // }
    //
    // if (price.equals("2")) {
    // mTwo = num;
    // }
    //
    // if (price.equals("5")) {
    // mFive = num;
    // }
    // // 除了首次登陆都没有10元
    // if (price.equals("10")) {
    // mTen = num;
    // }
    // }
    // }
    //
    // }
    //
    // }
    //
    // }.execute();
    //
    // }

    private HashMap<String, String> mGoldVoucherMap;
    private HashMap<String, Object> mSystemTimeMap;

    // 获取 最优优惠券
    private void getProxCoup(final double mGoldMoney) {

        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) context, 0) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                // mGoldVoucherMap =
                // ComModel2.getCpgold(SubmitMultiShopActivty.this);
                // mSystemTimeMap =
                // ComModel2.getSystemTime(SubmitMultiShopActivty.this);
                // HashMap<String, Object> obj =
                // ComModel2.multiOrderGetProxCoupon(context,
                // 0 + ":" + (sum - mYouhuiMoneyCount));
                HashMap<String, Object> obj = ComModel2.multiOrderGetProxCoupon(context,
                        0 + ":" + (mPriceCount - mYouhuiMoneyCount));
                HashMap<String, Object> mapRet = null;
                if (null != obj) {
                    mapRet = JSON.parseObject(obj.get(0 + "") + "", new TypeReference<HashMap<String, Object>>() {
                    }); // obj.get(shop.getSupp_id());
                }

                return mapRet;
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @SuppressLint("StringFormatMatches")
            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);

                if (e != null) {// 查询异常
                    ToastUtil.showShortText(context, "查询异常");
                } else {
                    // 使用本地存储的金券信息
                    String end_date = SharedPreferencesUtil.getStringData(SubmitMultiShopActivty.this,
                            Pref.JINQUAN_END_DATE, "-1");
                    if (null == end_date || "".equals(end_date)) {
                        end_date = "-1";
                    }
                    String is_open = SharedPreferencesUtil.getStringData(SubmitMultiShopActivty.this,
                            Pref.JINQUAN_IS_OPEN, "0");
                    if (null == is_open || "".equals(is_open)) {
                        is_open = "0";
                    }
                    String c_last_time = SharedPreferencesUtil.getStringData(SubmitMultiShopActivty.this,
                            Pref.JINQUAN_C_LAST_TIME, "0");
                    if (null == c_last_time || "".equals(c_last_time)) {
                        c_last_time = "0";
                    }
                    String c_price = SharedPreferencesUtil.getStringData(SubmitMultiShopActivty.this,
                            Pref.JINQUAN_C_PRICE, "0");
                    if (null == c_price || "".equals(c_price)) {
                        c_price = "0";
                    }
                    String is_use = SharedPreferencesUtil.getStringData(SubmitMultiShopActivty.this,
                            Pref.JINQUAN_IS_USE, "0");
                    if (null == is_use || "".equals(is_use)) {
                        is_use = "0";
                    }
                    String c_id = SharedPreferencesUtil.getStringData(SubmitMultiShopActivty.this, Pref.JINQUAN_C_ID,
                            "0");
                    if (null == c_id || "".equals(c_id)) {
                        c_id = "0";
                    }
                    if (mSystemTimeMap != null && mSystemTimeMap.size() > 0
                            && (Long) mSystemTimeMap.get("now") < Long.parseLong(end_date)) {
                        mGoldVoucherMap = new HashMap<String, String>();
                        mGoldVoucherMap.put("end_date", end_date);
                        mGoldVoucherMap.put("is_open", is_open);
                        mGoldVoucherMap.put("c_last_time", c_last_time);
                        mGoldVoucherMap.put("c_price", c_price);
                        mGoldVoucherMap.put("is_use", is_use);
                        mGoldVoucherMap.put("c_id", c_id);
                    } else {
                        mGoldVoucherMap = null;
                    }

                    if (null != result) {
                        mapCoupon = result;
                        mRlCoupon.setVisibility(View.VISIBLE);
                        tv_discount_coupon_count.setVisibility(View.VISIBLE);
                        if (mGoldVoucherMap != null && mGoldVoucherMap.size() > 0 && Integer
                                .parseInt("" + mGoldVoucherMap.get("c_id")) == (Integer) mapCoupon.get("id")) {// 匹配的优惠券就是金券
                            tv_discount_coupon.setText("金券");
                            tv_discount_coupon_count.setText("    可使用1张金券");
                            tv_gold_voucaher.setText("金券");
                            useFlag = true;
                        } else {
                            tv_discount_coupon_count.setText("可使用1张优惠券");
                        }
                        mCouponMoney.setVisibility(View.VISIBLE);
                        mCouponMoney.setText("-¥" + mapCoupon.get("c_price"));
                        discount = ((BigDecimal) mapCoupon.get("c_price")).doubleValue();

                        double discount = ((BigDecimal) mapCoupon.get("c_price")).doubleValue();
                        sum = sum - discount;

                        // amount = sum;
                        // afterCoupon = sum;

                        total_account.setText(Html.fromHtml(getString(R.string.total_account, shopNum,
                                new java.text.DecimalFormat("#0.00").format(sum))));
                        mCoupon.setVisibility(View.VISIBLE);
                        mCoupon.setText("-¥" + new java.text.DecimalFormat("#0.00").format(discount));
                        // tv_settle_account.setText("实付款:¥" + new
                        // java.text.DecimalFormat("#0.00")
                        // .format(sum - mYouhuiMoneyCount - mVouchersCount));
                        // TODO:11111111111111111
                        // setMoney(sum - mYouhuiMoneyCount - mVouchersCount,
                        // false);

                    } else {
                        if (mSystemTimeMap != null && mSystemTimeMap.size() > 0 && mGoldVoucherMap != null
                                && mGoldVoucherMap.size() > 0) {
                            LogYiFu.e("zzqTest", "***" + mGoldMoney);
                            LogYiFu.e("zzqTest", "___" + Double.parseDouble(mGoldVoucherMap.get("c_price")));
                            LogYiFu.e("zzqTest", "***" + (sum - mYouhuiMoneyCount));
                            if (Integer.parseInt(mGoldVoucherMap.get("is_open")) == 1
                                    && (Long) mSystemTimeMap.get("now") < Long
                                    .parseLong(mGoldVoucherMap.get("end_date"))
                                    && (Long) mSystemTimeMap.get("now") < Long
                                    .parseLong(mGoldVoucherMap.get("c_last_time"))
                                    && Integer.parseInt(mGoldVoucherMap.get("is_use")) == 0 && sum - mYouhuiMoneyCount
                                    - Double.parseDouble(mGoldVoucherMap.get("c_price")) - mVouchersCount > 0) {
                                mRlCoupon.setVisibility(View.VISIBLE);
                                tv_discount_coupon_count.setVisibility(View.VISIBLE);
                                tv_discount_coupon.setText("金券");
                                tv_discount_coupon_count.setText("     可使用1张金券");
                                tv_gold_voucaher.setText("金券");
                                mCouponMoney.setVisibility(View.VISIBLE);
                                mCouponMoney.setText("-¥" + mGoldVoucherMap.get("c_price"));
                                discount = Double.parseDouble(mGoldVoucherMap.get("c_price"));

                                double discount = Double.parseDouble(mGoldVoucherMap.get("c_price"));
                                sum = sum - discount;

                                // amount = sum;
                                // afterCoupon = sum;
                                total_account.setText(Html.fromHtml(getString(R.string.total_account, shopNum,
                                        new java.text.DecimalFormat("#0.00").format(sum))));
                                mCoupon.setVisibility(View.VISIBLE);
                                mCoupon.setText("-¥" + new java.text.DecimalFormat("#0.00").format(discount));
                                useFlag = true;
                                // tv_settle_account.setText("实付款:¥" + new
                                // java.text.DecimalFormat("#0.00")
                                // .format(sum - mYouhuiMoneyCount -
                                // mVouchersCount));
                                // mapCoupon.put("id",
                                // Integer.parseInt(mGoldVoucherMap.get("c_id")));

                                // TODO:222222222222222
                                // setMoney(sum - mYouhuiMoneyCount -
                                // mVouchersCount, false);

                            } else {
                                mLimitMoney = sum * maxRate;
                                mRlCoupon.setVisibility(View.GONE);
                                mapCoupon = new HashMap<String, Object>();
                                tv_discount_coupon_count.setVisibility(View.VISIBLE);
                                tv_discount_coupon_count.setText("无可用");
                                mCouponMoney.setVisibility(View.GONE);
                                total_account.setText(Html.fromHtml(getString(R.string.total_account, shopNum,
                                        new java.text.DecimalFormat("#0.00").format(sum))));

                                // tv_settle_account.setText("实付款:¥" + new
                                // java.text.DecimalFormat("#0.00")
                                // .format(sum - mYouhuiMoneyCount -
                                // mVouchersCount));
                                // TODO:33333333333333333333
                                // setMoney(sum - mYouhuiMoneyCount -
                                // mVouchersCount, false);
                            }
                        } else {
                            mRlCoupon.setVisibility(View.GONE);
                            mapCoupon = new HashMap<String, Object>();
                            tv_discount_coupon_count.setVisibility(View.VISIBLE);
                            tv_discount_coupon_count.setText("无可用");
                            mCouponMoney.setVisibility(View.GONE);
                            total_account.setText(Html.fromHtml(getString(R.string.total_account, shopNum,
                                    new java.text.DecimalFormat("#0.00").format(sum))));
                            LogYiFu.e("test", "mVouchersCount" + mVouchersCount);
                            // tv_settle_account.setText("实付款:¥" + new
                            // java.text.DecimalFormat("#0.00")
                            // .format(sum - mYouhuiMoneyCount -
                            // mVouchersCount));
                            // TODO:4444444444444444444
                            // if(mTgbs.isChecked()){
                            // setMoney(sum - mYouhuiMoneyCount -
                            // mVouchersCount, false);
                            // }
                        }
                    }
                    // if (isDapei) {
                    // mDiscountInte = (int) (mPriceCount * 50 * 0.9-discount);
                    // } else {
                    // mDiscountInte = (int) (mPriceCount * 50-discount);
                    // }
                    getMyIntegral();
                }
            }

        }.execute();
    }

    /***
     * 得到匹配的优惠券
     *
     * @param applyList
     */
    private void getProxCoupon(HashMap<Integer, List<ShopCart>> applyList) {
        StringBuffer sb = new StringBuffer();
        Iterator<Entry<Integer, List<ShopCart>>> iterator = applyList.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Integer, List<ShopCart>> entry = iterator.next();
            List<ShopCart> shopCarts = (List<ShopCart>) entry.getValue();
            int supp_id = entry.getKey();
            sb.append(supp_id).append(":");
            int sumAccount = 0;
            for (int i = 0; i < shopCarts.size(); i++) {
                ShopCart good = shopCarts.get(i);
                sumAccount += good.getShop_se_price() * good.getShop_num();
            }
            sb.append(sumAccount);
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }

        new SAsyncTask<String, Void, HashMap<String, Object>>(SubmitMultiShopActivty.this, 0) {

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ComModel2.multiOrderGetProxCoupon(context, params[0]);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null) {
                    SubmitMultiShopActivty.this.mapCoupon = result;
                    isProxCouponComplete = true;
                    if (isIntegralComplete) {
                        addView(mapListGood, container, result);
                    }

                }
            }

        }.execute(sb.toString());
    }

    boolean isProxCouponComplete = false;
    boolean isIntegralComplete = false;

    /***
     *

     *            :需要付款的商品
     * @param container
     *            :显示商品的容器
     */
    private void addView(HashMap<Integer, List<ShopCart>> applyList, LinearLayout container,
                         HashMap<String, Object> mapCoupon) {
        container.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        Iterator<Entry<Integer, List<ShopCart>>> iterator = applyList.entrySet().iterator();
        int position = 0;

        /*** 套餐的商品 */
        // mealMessage = new String[shopCartMeal.size()];

		/*
         * for (int j = 0; j < shopCartMeal.size(); j++) { ShopCart sc =
		 * shopCartMeal.get(j); View view =
		 * inflater.inflate(R.layout.meal_order, null); ImageView img_pro_pic =
		 * (ImageView) view .findViewById(R.id.img_pro_pic); TextView
		 * tv_pro_name = (TextView) view .findViewById(R.id.tv_pro_name);
		 * TextView tv_pro_price = (TextView) view
		 * .findViewById(R.id.tv_pro_price); TextView tv_pro_former_price =
		 * (TextView) view .findViewById(R.id.tv_pro_former_price); TextView
		 * express_money = (TextView) view .findViewById(R.id.express_money);
		 * TextView total_account = (TextView) view
		 * .findViewById(R.id.total_account); EditText edit_message = (EditText)
		 * view .findViewById(R.id.edit_message);
		 * 
		 * mealMessage[j] = edit_message.getText().toString().trim();
		 * 
		 * String pic = sc.getShop_code().substring(1,
		 * 4)+"/"+sc.getShop_code()+"/"+sc.getDef_pic();
		 * SetImageLoader.initImageLoader(this, img_pro_pic, pic, "");
		 * tv_pro_name.setText(sc.getShop_name());
		 * tv_pro_price.setText(sc.getShop_se_price() + "");
		 * tv_pro_former_price.setText(sc.getShop_price() + "");
		 * express_money.setText(Html.fromHtml(getString(R.string.tv_mail_fee,
		 * sc.getPostage() + "")));// 邮费 if
		 * (!TextUtils.isEmpty(sc.getPostage())) { double count =
		 * Double.valueOf(sc.getPostage()) + sc.getShop_se_price();
		 * total_account.setText(count + ""); } container.addView(view); }
		 */
        while (iterator.hasNext()) {
            Entry<Integer, List<ShopCart>> entry = iterator.next();
            final List<ShopCart> shopCarts = (List<ShopCart>) entry.getValue();
            /*
             * String obj = null; if (null != mapCoupon.get(entry.getKey() +
			 * "")) { obj = mapCoupon.get(entry.getKey() + "").toString(); }
			 * HashMap<String, Object> map = null; if (null != obj) { map =
			 * JSON.parseObject(obj, new TypeReference<HashMap<String,
			 * Object>>() { }); mapCoups.put(entry.getKey(), map);// 如果存在优惠券
			 * 将优惠券加上去 添加到map集合 // 请求服务器的时候需要用 }
			 */
            View view = inflater.inflate(R.layout.goods_item, null);
            view.setBackgroundColor(Color.WHITE);
            // if (mHuoDongFlag) {// 隐藏抵用券条目
            // ((RelativeLayout)
            // view.findViewById(R.id.item_rl_discount_coupon)).setVisibility(View.GONE);
            // ((RelativeLayout)
            // view.findViewById(R.id.item_rl_huodong)).setVisibility(View.VISIBLE);
            // }
            // rl_discount_coupons = (RelativeLayout) view
            // .findViewById(R.id.rl_discount_coupon);
            // TextView tv_discount_coupon_count = (TextView) view
            // .findViewById(R.id.tv_discount_coupon_count);
            // TextView tv_useable_integral = (TextView) view
            // .findViewById(R.id.tv_useable_integral);
            // TextView tv_notice = (TextView)
            // view.findViewById(R.id.tv_notice);
            // EditText et_input_integral = (EditText) view
            // .findViewById(R.id.et_input_integral);

			/*
             * String inputInteral =
			 * et_input_integral.getText().toString().trim(); if
			 * (!TextUtils.isEmpty(inputInteral)) { mapInteg.put(entry.getKey(),
			 * inputInteral); }
			 * 
			 * listInteEdit.add(et_input_integral);
			 * 
			 * mapEditInte.put(entry.getKey(), et_input_integral);
			 */

            // 设置分割线 第一条的隐藏
            position++;
            View line = view.findViewById(R.id.v_bottom_line);
            if (position == 1) {
                line.setVisibility(View.GONE);
            }
            // TODO:

            // final TextView mUserful = (TextView)
            // view.findViewById(R.id.submit_shopcart_userful);
            // final ToggleButton mTgbVoucachers = (ToggleButton)
            // view.findViewById(R.id.submit_shopcart_tgb);
            // mListTB.add(mTgbVoucachers);
            // mTgbVoucachers.setOnCheckedChangeListener(new
            // OnCheckedChangeListener() {
            //
            // @Override
            // public void onCheckedChanged(CompoundButton buttonView, boolean
            // isChecked) {
            // mIsChecked = isChecked;
            // int userfulChange = 0;
            // // for (int i = 0; i < shopCarts.size(); i++) {
            // // final ShopCart good = shopCarts.get(i);
            // // userfulChange +=
            // // countVoucachers(good.getKickback()
            // // * good.getShop_num());
            // // }
            // if (mIsChecked) {
            // if (mTen == 0 && mFive == 0 && mTwo == 0 && mOne == 0) {
            // mTgbVoucachers.setChecked(false);
            // ToastUtil.showShortText(context, "抵用券已经用完");
            // return;
            // }
            // userfulChange = 0;
            // for (int i = 0; i < shopCarts.size(); i++) {
            // final ShopCart good = shopCarts.get(i);
            // userfulChange += countVoucachers((int) (good.getKickback() *
            // good.getShop_num()));
            //
            // mMapTen.put("" + good.getStock_type_id(), mTenUse);
            // mMapFive.put("" + good.getStock_type_id(), mFiveUse);
            // mMapTwo.put("" + good.getStock_type_id(), mTwoUse);
            // mMapOne.put("" + good.getStock_type_id(), mOneUse);
            // mListShopCart.add(good);
            // }
            // mUserful.setVisibility(View.VISIBLE);
            // mUserful.setText("抵用¥" + new
            // java.text.DecimalFormat("#0").format(userfulChange));
            // mVouchersCount += userfulChange;
            // int m = (int) (mVouchersCount);
            // double sum2 = sum - mVouchersCount;
            //// tv_settle_account.setText(
            //// "实付款:¥" + new java.text.DecimalFormat("#0.00").format(sum2 -
            // mYouhuiMoneyCount));
            // setMoney(sum2 - mYouhuiMoneyCount,true);
            // mVoucachers.setText("-¥" + new
            // java.text.DecimalFormat("#0.00").format(m) + "");
            // LogYiFu.e(SUBMIT, new
            // java.text.DecimalFormat("#0").format(mVouchersCount) + "");
            // LogYiFu.e(SUBMIT, "//" + mListShopCart.size() + "");
            // } else {
            //
            // for (int i = 0; i < shopCarts.size(); i++) {
            // final ShopCart good = shopCarts.get(i);
            // mListShopCart.remove(good);
            // int a = mMapTen.get("" + good.getStock_type_id());
            // int b = mMapFive.get("" + good.getStock_type_id());
            // int c = mMapTwo.get("" + good.getStock_type_id());
            // int d = mMapOne.get("" + good.getStock_type_id());
            // mTen += a;
            // mFive += b;
            // mTwo += c;
            // mOne += d;
            // userfulChange += a * 10 + b * 5 + c * 2 + d * 1;
            // mMapTen.put("" + good.getStock_type_id(), 0);
            // mMapFive.put("" + good.getStock_type_id(), 0);
            // mMapTwo.put("" + good.getStock_type_id(), 0);
            // mMapOne.put("" + good.getStock_type_id(), 0);
            // }
            // mUserful.setVisibility(View.GONE);
            // mVouchersCount -= userfulChange;
            // double sum2 = sum - mVouchersCount;
            //// tv_settle_account.setText(
            //// "实付款:¥" + new java.text.DecimalFormat("#0.00").format(sum2 -
            // mYouhuiMoneyCount));
            // setMoney(sum2 - mYouhuiMoneyCount,true);
            // int m = (int) (mVouchersCount);
            // mVoucachers.setText("-¥" + new
            // java.text.DecimalFormat("#0.00").format(m) + "");
            // LogYiFu.e(SUBMIT, new
            // java.text.DecimalFormat("#0").format(mVouchersCount) + "");
            // LogYiFu.e(SUBMIT, "//" + mListShopCart.size() + "");
            // }
            // // 111111111111111
            // if (discount == 0 && mSystemTimeMap != null &&
            // mSystemTimeMap.size() > 0 && mGoldVoucherMap != null
            // && mGoldVoucherMap.size() > 0 &&
            // Integer.parseInt(mGoldVoucherMap.get("is_open")) == 1
            // && (Long) mSystemTimeMap.get("now") <
            // Long.parseLong(mGoldVoucherMap.get("end_date"))
            // && (Long) mSystemTimeMap.get("now") <
            // Long.parseLong(mGoldVoucherMap.get("c_last_time"))
            // && Integer.parseInt(mGoldVoucherMap.get("is_use")) == 0 && sum -
            // mYouhuiMoneyCount
            // - Double.parseDouble(mGoldVoucherMap.get("c_price")) -
            // mVouchersCount > 0) {
            // mRlCoupon.setVisibility(View.VISIBLE);
            // tv_discount_coupon_count.setVisibility(View.VISIBLE);
            // tv_discount_coupon.setText("金券");
            // tv_discount_coupon_count.setText(" 可使用1张金券");
            // tv_gold_voucaher.setText("金券");
            // mCouponMoney.setVisibility(View.VISIBLE);
            // mCouponMoney.setText("-¥" + mGoldVoucherMap.get("c_price"));
            // discount = Double.parseDouble(mGoldVoucherMap.get("c_price"));
            //
            // double discount =
            // Double.parseDouble(mGoldVoucherMap.get("c_price"));
            // sum = sum - discount;
            //
            // // amount = sum;
            // // afterCoupon = sum;
            //
            // total_account.setText(Html.fromHtml(getString(R.string.total_account,
            // shopNum,
            // new java.text.DecimalFormat("#0.00").format(sum))));
            // mCoupon.setVisibility(View.VISIBLE);
            // mCoupon.setText("-¥" + new
            // java.text.DecimalFormat("#0.00").format(discount));
            //// tv_settle_account.setText("实付款:¥" + new
            // java.text.DecimalFormat("#0.00")
            //// .format(sum - mYouhuiMoneyCount - mVouchersCount));
            // setMoney(sum - mYouhuiMoneyCount - mVouchersCount,true);
            // // mapCoupon.put("id",
            // // Integer.parseInt(mGoldVoucherMap.get("c_id")));
            // useFlag = true;
            // mDiyongFlag = true;
            // } else if (mDiyongFlag) {
            // mDiyongFlag = false;
            // mRlCoupon.setVisibility(View.GONE);
            // tv_discount_coupon_count.setVisibility(View.VISIBLE);
            // tv_discount_coupon.setText("优惠券");
            // tv_discount_coupon_count.setText("无可用");
            // tv_gold_voucaher.setText("优惠券");
            // mCouponMoney.setVisibility(View.GONE);
            // sum = sum + discount;
            // discount = 0;
            // total_account.setText(Html.fromHtml(getString(R.string.total_account,
            // shopNum,
            // new java.text.DecimalFormat("#0.00").format(sum))));
            // mCoupon.setVisibility(View.GONE);
            //// tv_settle_account.setText("实付款:¥" + new
            // java.text.DecimalFormat("#0.00")
            //// .format(sum - mYouhuiMoneyCount - mVouchersCount));
            // setMoney(sum - mYouhuiMoneyCount - mVouchersCount,true);
            // }
            // }
            // });
            LinearLayout good_container = (LinearLayout) view.findViewById(R.id.good_container);
            EditText edit_message = (EditText) view.findViewById(R.id.edit_message);
            mapEdit.put(entry.getKey(), edit_message);
            // 总共的价格？？？同一个商家的价格合并
            // TextView tv_total_account = (TextView)
            // view.findViewById(R.id.total_account);
            double sumAccount = 0.0;

            // double original_price = 0;
            // 获取同一商家的购物车
            LogYiFu.e("TAG", "同一家商品的数量---" + shopCarts.size());
            int userful = 0;//
            double specilaPriceCount = 0;//专柜价总和
            for (int i = 0; i < shopCarts.size(); i++) {
                final ShopCart good = shopCarts.get(i);
                specilaPriceCount = specilaPriceCount + (good.getShop_se_price() / good.getShop_price()) * 10;
                View v = inflater.inflate(R.layout.good_item, null);
                ImageView img_pro_pic = (ImageView) v.findViewById(R.id.img_pro_pic);
                TextView tv_sum = (TextView) v.findViewById(R.id.tv_sum);
                TextView tv_pro_name = (TextView) v.findViewById(R.id.tv_pro_name);
                TextView tv_pro_descri = (TextView) v.findViewById(R.id.tv_pro_descri);
                TextView tv_discout = (TextView) v.findViewById(R.id.tv_pro_discount);
                TextView tv_zero_kickback = (TextView) v.findViewById(R.id.item_tv_zero_kickback);
                if (mHuoDongFlag && groupFlag != 0) {
                    tv_zero_kickback.setVisibility(View.GONE);
                } else {
                    tv_zero_kickback.setVisibility(View.VISIBLE);
                }
                // 没有打折 钱的价格
                TextView tvProPrice = (TextView) v.findViewById(R.id.tv_pro_price);
                TextView tv_item_supply = (TextView) v.findViewById(R.id.tv_item_supply);

                // TextView tv_sumname = (TextView)
                // v.findViewById(R.id.tv_sumname);ho
                // SetImageLoader.initImageLoader(this, img_pro_pic,
                // good.getShop_code().substring(1, 4) + "/" +
                // good.getShop_code() + "/" + good.getDef_pic(), "");

                PicassoUtils.initImage(this,
                        good.getShop_code().substring(1, 4) + "/" + good.getShop_code() + "/" + good.getDef_pic(),
                        img_pro_pic);

                LogYiFu.e("TAG", "==good=" + good.getColor() + ",size=" + good.getSize());
                tv_sum.setText("x" + good.getShop_num());
                tv_pro_name.setText(good.getShop_name());
                tv_pro_descri.setText("颜色-" + good.getColor() + "    尺寸:" + good.getSize());
                tv_discout.setText("¥" + new java.text.DecimalFormat("#0.00").format(good.getShop_se_price()));


                tv_zero_kickback.setText("返" + new java.text.DecimalFormat("#0.00").format(good.getShop_se_price()) + "元=0元购");
                tv_zero_kickback.setVisibility(View.GONE); //有1元购后去掉0元购


                // 设置没有打折的价格
                tvProPrice.setText("¥" + new java.text.DecimalFormat("#0.00").format(good.getShop_price()));
                tvProPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                String supp_label = good.getSupp_label();
                if (!TextUtils.isEmpty(supp_label)) {
//                    tv_item_supply.setText(supp_label + "制造商");
                    tv_item_supply.setText(supp_label + "");

                }

                // holder.tv_item_nprice.getPaint().setFlags(
                // Paint.STRIKE_THRU_TEXT_FLAG);
                sumAccount += good.getShop_se_price() * good.getShop_num();

                int useful2 = countVoucachers((int) (good.getKickback() * good.getShop_num()));
                mMapTen.put("" + good.getStock_type_id(), mTenUse);
                mMapFive.put("" + good.getStock_type_id(), mFiveUse);
                mMapTwo.put("" + good.getStock_type_id(), mTwoUse);
                mMapOne.put("" + good.getStock_type_id(), mOneUse);
                userful += useful2;
                // original_price += good.getOriginal_price() *
                // good.getShop_num();
                good_container.addView(v);
                LogYiFu.e("TAG", "添加空间（同一家）");
                mListShopCart.add(good);
                v.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (mHuoDongFlag) {
                            Intent intent = new Intent(context, ShopDetailsActivity.class);
                            intent.putExtra("code", good.getShop_code());
//                            intent.putExtra("isSignActiveShop", true);
                            if (groupFlag != 0) {
                                intent.putExtra("group_click_flag", true);//用来表示拼团购修改商品属性的
                            }
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);                            ;
                        } else {
                            Intent intent = new Intent(context, ShopDetailsActivity.class);
                            intent.putExtra("code", good.getShop_code());
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                            ;
                        }
                    }
                });
            }

//            tv_special_price.setText("专柜" + new DecimalFormat("#0.0").format(specilaPriceCount / shopCarts.size()) + "折");
            tv_special_price.setVisibility(View.GONE);
            // tv_total_account.setText("¥" + new
            // java.text.DecimalFormat("#0.00").format(sumAccount));
            if (userful <= 0) {
                // mUserful.setVisibility(View.GONE);
                // mTgbVoucachers.setChecked(false);
            } else {
                // double userful2 = countVoucachers(userful);

                // mUserful.setText("抵用¥" + new
                // java.text.DecimalFormat("#0").format(userful));
                mVouchersCount += userful;
                double sum2 = sum - mVouchersCount;
                tv_settle_account
                        .setText("实付款:¥" + new java.text.DecimalFormat("#0.00").format(sum2 - mYouhuiMoneyCount));
                int m = (int) (mVouchersCount);
                mVoucachers.setText("-¥" + new java.text.DecimalFormat("#0.00").format(m) + "");
            }
            // double discount = original_price * 0.1;
            // int discountInte = (int) (discount * 100 * 5);
            // tv_notice.setText("本单最多可用积分" + discountInte + ",抵用"
            // + new java.text.DecimalFormat("#0.00").format(discount)
            // + "元,最低500抵换");
            // tv_useable_integral.setText("(可用积分：" + myIntegCount + ")");
            // 优惠券
            /*
             * if (null != map) {
			 * 
			 * tv_discount_coupon_count.setText("(已选择1张优惠券，优惠" +
			 * map.get("c_price") + "元)");
			 * 
			 * sumAccount = sumAccount -
			 * Double.valueOf(map.get("c_price").toString()); } sum +=
			 * sumAccount; tv_total_account.setText("¥:" + new
			 * java.text.DecimalFormat("#0.00").format(sumAccount));
			 * 
			 * // 积分 double afterCoupon = sum;
			 */
            // addOnTextChange(et_input_integral, myIntegCount, discountInte,
            // afterCoupon, sumAccount, tv_total_account);

            container.addView(view);
        }

        total_account.setText(Html.fromHtml(
                getString(R.string.total_account, shopNum, new java.text.DecimalFormat("#0.00").format(sum))));
        mTotal = sum;
        double sum2 = sum - mVouchersCount;
        if (mHuoDongFlag && groupFlag != 0) {
//            tv_settle_account.setText("实付款:¥"+new java.text.DecimalFormat("#0.00").format(mGroupPrice)+"包邮");
            tv_settle_account.setText("参与实付：¥0.00元");
            submit_tv_group_price.setText("-¥" + new java.text.DecimalFormat("#0.00").format(mTotal - mGroupPrice));
            submit_tv_need_pay_price.setText("¥" + new java.text.DecimalFormat("#0.00").format(mGroupPrice));
        } else {
            tv_settle_account.setText("实付款:¥" + new java.text.DecimalFormat("#0.00").format(sum2 - mYouhuiMoneyCount));
        }
        mSubmitTotal.setText("¥" + new java.text.DecimalFormat("#0.00").format(mTotal));
        mPockage.setText("-¥" + new java.text.DecimalFormat("#0.00").format(0.0));
    }

    // 设置地址
    private void setDeliverAddress(HashMap<String, String> mapRet, DeliveryAddress dAddress) {
        if (null == mapRet && dAddress != null) {
            tv_name.setText("收件人：" + dAddress.getConsignee());
            tv_phone.setText(dAddress.getPhone());


            String province = db.queryAreaNameById(dAddress.getProvince()) != null && "0".equals(db.queryAreaNameById(dAddress.getProvince()))
                    ? db.queryAreaNameById(dAddress.getProvince()) : "";


//            String city = db.queryAreaNameById(dAddress.getCity());


            String city = db.queryAreaNameById(dAddress.getCity()) != null && "0".equals(db.queryAreaNameById(dAddress.getCity()))
                    ? db.queryAreaNameById(dAddress.getCity()) : "";


            String county = dAddress.getArea() != null && 0 != dAddress.getArea()
                    ? db.queryAreaNameById(dAddress.getArea()) : "";
            String street = "";


            if (null != dAddress.getStreet() && 0 != dAddress.getStreet()) {
                street = db.queryAreaNameById(dAddress.getStreet());
            }

            tv_receiver_addr.setText("收货地址：" + province + city + county + street + dAddress.getAddress());// 收货地址
            lin_receiver_addr.setVisibility(View.VISIBLE);
            rel_name_phone.setVisibility(View.VISIBLE);
            lin_set_addr.setVisibility(View.GONE);
        } else if (null != mapRet && dAddress == null) {
            lin_receiver_addr.setVisibility(View.VISIBLE);
            rel_name_phone.setVisibility(View.VISIBLE);
            lin_set_addr.setVisibility(View.GONE);
            tv_name.setText("收件人：" + mapRet.get("consignee"));
            tv_phone.setText(mapRet.get("phone"));
            tv_receiver_addr.setText("收货地址：" + mapRet.get("address"));// 收货地址
            lin_receiver_addr.setVisibility(View.VISIBLE);
            rel_name_phone.setVisibility(View.VISIBLE);
            lin_set_addr.setVisibility(View.GONE);
        } else if (null == mapRet && null == dAddress) {
            lin_receiver_addr.setVisibility(View.GONE);
            rel_name_phone.setVisibility(View.GONE);
            lin_set_addr.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 提交订单
     */
    private void submitOrder(final View v) {
        UserInfo user = YCache.getCacheUser(context);
        //TODO:_MODIFY_这个判断不知道干嘛的，先注释掉，不然不能继续执行
        /*if(user.getGender() == 1){
            ToastUtil.showShortText2("系统维护中，暂不支持支付");
            return;
        }*/
//		Log.e("hello", "000000");
        Iterator<Entry<Integer, EditText>> iterator = mapEdit.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Integer, EditText> entry = iterator.next();
            String s = entry.getValue().getText().toString().trim();
//            if (!TextUtils.isEmpty(s)) {
//                if (StringUtils.containsEmoji(s)) {
//                    Toast.makeText(context, "不得含有特殊字符", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (RegisterFragment.getWordCount(s) < 5) {
//                    Toast.makeText(context, "订单说明不得少于五个字符", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (RegisterFragment.getWordCount(s) > 500) {
//                    Toast.makeText(context, "订单说明不得多于五百个字符", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
            mapMsg.put(entry.getKey(), s);
        }

        double inteMoney = 0.0;

        final StringBuffer sb = new StringBuffer();

        new SAsyncTask<Void, Void, HashMap<String, Object>>(this, v, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                LogYiFu.e("TAG", "mapListGood=" + mapListGood.toString());
                // return ComModel2.submitMultiOrder(context, store.getS_code(),
                // store.getS_name(), addressId, mapListGood, mapMsg,
                // mapCoups, mapInteg, sb.toString(), orderToken);
                int myInte = 0;
                if (isAddIntegral) {
                    myInte = integral;
                } else {
                    myInte = 0;
                }
                int is_be;// 0代表未使用余额抵扣，1代表使用了余额抵扣
                if (mMoneyTgb.isChecked() && mUseMoney > 0) {
                    is_be = 1;
                } else {
                    is_be = 0;
                }
                if (isDapei) {

                    if (null == mapCoupon || !mapCoupon.containsKey("id") || null == mapCoupon.get("id")) {
                        if (useFlag) {
                            return ComModel2.submitShopcartOrdersDaPei(context, mapMsg, listGoods, myInte, addressId,
                                    Integer.parseInt(mGoldVoucherMap.get("c_id")), mMapTen, mMapFive, mMapTwo, mMapOne,
                                    is_be);
                        } else {
                            return ComModel2.submitShopcartOrdersDaPei(context, mapMsg, listGoods, myInte, addressId, 0,
                                    mMapTen, mMapFive, mMapTwo, mMapOne, is_be);
                        }

                    } else {
                        if (useFlag) {
                            return ComModel2.submitShopcartOrdersDaPei(context, mapMsg, listGoods, myInte, addressId,
                                    Integer.parseInt(mGoldVoucherMap.get("c_id")), mMapTen, mMapFive, mMapTwo, mMapOne,
                                    is_be);
                        } else {
                            return ComModel2.submitShopcartOrdersDaPei(context, mapMsg, listGoods, myInte, addressId,
                                    (Integer) mapCoupon.get("id"), mMapTen, mMapFive, mMapTwo, mMapOne, is_be);
                        }
                    }

                } else {
                    if (null == mapCoupon || !mapCoupon.containsKey("id") || null == mapCoupon.get("id")) {
//						Log.e("hello", "55555");
                        if (useFlag) {// 把使用的金券ID转给后台
                            return ComModel2.submitShopcartOrders(context, mapMsg, listGoods, myInte, addressId,
                                    Integer.parseInt(mGoldVoucherMap.get("c_id")), mMapTen, mMapFive, mMapTwo, mMapOne,
                                    is_be);
                        } else {
                            return ComModel2.submitShopcartOrders(context, mapMsg, listGoods, myInte, addressId, 0,
                                    mMapTen, mMapFive, mMapTwo, mMapOne, is_be);
                        }
                    } else {

                        if (useFlag) {
                            return ComModel2.submitShopcartOrders(context, mapMsg, listGoods, myInte, addressId,
                                    Integer.parseInt(mGoldVoucherMap.get("c_id")), mMapTen, mMapFive, mMapTwo, mMapOne,
                                    is_be);
                        } else {
                            return ComModel2.submitShopcartOrders(context, mapMsg, listGoods, myInte, addressId,
                                    (Integer) mapCoupon.get("id"), mMapTen, mMapFive, mMapTwo, mMapOne, is_be);
                        }
                    }
                }
                // return null;
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    orderNo = (String) result.get("order_code");
                    // payMoney(v, (String) result.get("order_code"));
                    // showPayDialog(aliNotifyUrl, wxPayUrl, isSingle);

                    int url = (Integer) result.get("url");
                    if (useFlag) {// 使用了金券
                        SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_IS_USE, "1");
                    }

                    SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);

                    // 跳转到收银台支付界面
                    Intent intent = new Intent(SubmitMultiShopActivty.this, PaymentActivity.class);
                    LogYiFu.e("TAG", "点击提交订单");
                    intent.putExtra("is_g_code", true);
                    intent.putExtra("listGoods", (Serializable) listGoods);
                    intent.putExtra("result", (Serializable) result);
                    intent.putExtra("order_code", orderNo);
                    // intent.putExtra("mIsTwoGroup", mIsTwoGroup);
                    ShopCartDao dao = new ShopCartDao(context);
                    for (int i = 0; i < listGoods.size(); i++) {
                        dao.delete("" + listGoods.get(i).getStock_type_id());
                    }

                    Long shengyuTime = (long) (1 * 1000 * 60 * 60 * 24); // 一天计时

                    intent.putExtra("shengyuTime", shengyuTime);

                    intent.putExtra("totlaAccount", (Double) result.get("price"));
                    if (url > 1) {
                        intent.putExtra("isMulti", true);
                    }
					/*
					 * if (listGoods.size() == 1) {
					 * getPicPath(listGoods.get(0).getShop_code(), null); }
					 */


                    //支付引导---如果当然没有引导过就开始2分钟的计时
                    //上次弹出的日期
                    String fuchuanyindaodialog = SharedPreferencesUtil.getStringData(context, "FUCHUANYINDAODIALOG", "");
                    SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
                    // 当前日期
                    String datee = sdff.format(new Date());
                    //当天只弹出一次------避开疯狂星期一
//                    if (!datee.equals(fuchuanyindaodialog) && !SharedPreferencesUtil.getBooleanData(context, Pref.ISMADMONDAY, false)) {
                    YJApplication.startFukuanYndao();//开始两分钟计时
//                    }


                    SubmitMultiShopActivty.this.setResult(1);
                    SubmitMultiShopActivty.this.startActivityForResult(intent, 1003);
                    SubmitMultiShopActivty.this.finish();
                    // finish();
                    // SubmitMultiShopActivty.this.startActivityForResult(intent,
                    // CODE_PAY);
                }
            }

        }.execute((Void[]) null);
    }

    boolean isClick = false;

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_pay:
                YunYingTongJi.yunYingTongJi(SubmitMultiShopActivty.this, 111);
                // mList
                // payMoney(v);
                if (addResult == null && dAddress == null) {
                    ToastUtil.showShortText(this, "请设置收货地址");
                    return;
                }
                // Iterator<Entry<Integer, EditText>> iterator1 =
                // mapEditInte.entrySet()
                // .iterator();
                // int inputIntegral = 0;
                // while (iterator1.hasNext()) {
                // Entry<Integer, EditText> entry = iterator1.next();
                // if (!TextUtils.isEmpty(entry.getValue().getText().toString())) {
                // int value =
                // Integer.valueOf(entry.getValue().getText().toString());
                // if (value < 500) {
                // ToastUtil.showShortText(this, "积分最低输入500");
                // entry.getValue().setText("");
                // return;
                // }
                // inputIntegral += value;
                // }
                // }
                //
                // if(inputIntegral > myIntegCount){
                // ToastUtil.showShortText(this, "积分输入有误，已超过可用积分");
                // return;
                // }
                // mShareVoucachers.setText(mVouchersCount + "");
                if (!isDapei) {
                    mShareVoucachers.setText(mVouchersCount + "");
                }
                if (!isClick) {
                    if (mHuoDongFlag) {
//                        submitSignOrder(null, shopNum);
                        if (groupFlag == 0) {
                            submitSignOrder(null, shopNum);//活动商品提交订单
                        } else {
                            submitSignGroupShop(null);//拼团购商品提交订单
                        }
                    } else {//其他商品提交订单
                        if (mListShopCart.size() > 0) {
                            Random random = new Random();
                            int nextInt = random.nextInt(mListShopCart.size());
                            mShop_code = mListShopCart.get(nextInt).getShop_code();
                            mKickback = mListShopCart.get(nextInt).getKickback();

                            mDef_pic = mListShopCart.get(nextInt).getDef_pic();
                            if (isDapei) {
                                mShareVoucachers.setText(new java.text.DecimalFormat("#0")
                                        .format(mKickback * mListShopCart.get(nextInt).getShop_num()) + "");
                            }
                            // if(!isClick){
                            // ToastUtil.showShortText(context, "正在加载分享图，请稍后~");
                            // getShopLink(v); // 需要分享
                            submitOrder(null);
                            // isClick=true;
                            // }
                        } else {
                            submitOrder(null);
                        }
                    }
                    isClick = true;
                }
                // submitOrder(null);

                break;
            case R.id.lin_receiver_addr:
                intent = new Intent(this, ManMyDeliverAddr.class);
                intent.putExtra("flag", "submitmultishop");
                startActivityForResult(intent, 1001);
                break;
            case R.id.lin_set_addr:
                intent = new Intent(this, SetDeliverAddressActivity.class);
                startActivityForResult(intent, 1002);
                break;
            case R.id.img_back:// 返回上一级
                customDialog();
                break;
            case R.id.rl_discount_coupon:
                intent = new Intent(this, UsefulCouponsActivity.class);
                // intent.putExtra("amount", sum + discount);
                // intent.putExtra("jinquan", sum + discount - mVouchersCount);
                intent.putExtra("amount", mPriceCount);
                intent.putExtra("jinquan", mPriceCount - mVouchersCount);
                startActivityForResult(intent, 1005);
                break;
            case R.id.img_back_share:
                customDialog();
                break;
            default:
                break;
        }
        super.onClick(v);
    }

    // TODO:
    // 得到商品链接
    private void getShopLink(final View v) {
        new SAsyncTask<String, Void, HashMap<String, String>>(this, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                // TODO Auto-generated method stub
                return ComModel2.getShopLinkSpecial(params[0], context, "true");
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
				/*
				 * if (e == null) { if (result.get("status").equals("1")) {
				 * MyLogYiFu.e("pic", result.get("shop_pic")); String[] picList
				 * = result.get("shop_pic").split(","); if
				 * (!TextUtils.isEmpty(result.get("four_pic")) &&
				 * !result.get("four_pic").equals("null")) { download(null,
				 * picList, listGoods.get(0) .getShop_code(), result); } }
				 * 
				 * }
				 */
                if (null == e) {
                    if (result.get("status").equals("1")) {
                        // 大图是900 X 900 二维码

                        String shop_se_price = (String) result.get("shop_se_price");

                        Double shop_se_price_se = Double.parseDouble(shop_se_price);

                        // Double shop_se_price_se_se = shop_se_price_se -
                        // mKickback;
                        Double shop_se_price_se_se = shop_se_price_se - Math.floor(mKickback);
                        // MyLogYiFu.e("shop_se_price_se_se",
                        // shop_se_price_se_se+" "+mKickback+"
                        // "+Math.round(mKickback)+" "+shop_se_price_se);

                        createSharePic(result.get("link"), (String) result.get("four_pic"),

                                shop_se_price_se_se + "",

                                mShop_code, v);
                    }
                    // submitZeroOrder(v);
                }
            }

        }.execute(mShop_code);
    }

    private void createSharePic(final String link, final String picPath, final String price, final String shop_code,
                                final View v) {
        new SAsyncTask<Void, Void, Void>(this, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected Void doInBackground(FragmentActivity context, Void... params) throws Exception {
                // TODO Auto-generated method stub
                Bitmap bmQr = QRCreateUtil.createQrImage(link, 160, 160);// 得到二维码图片
                String[] strs = picPath.split(",");
                String pic;
                if (strs[2] != null) {
                    pic = shop_code.substring(1, 4) + "/" + shop_code + "/" + strs[2];
                } else {
                    pic = shop_code.substring(1, 4) + "/" + shop_code + "/" + mDef_pic;
                }

                Bitmap bmBg = downloadPic(pic);

                Bitmap bmNew = QRCreateUtil.drawNewBitmap1(context, bmBg, bmQr, price, "");

                QRCreateUtil.saveBitmap(bmNew, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
                return super.doInBackground(context, params);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Void result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);
                if (null == e) {
					/*
					 * File file = new File(YConstance.savePicPath,
					 * MD5Tools.md5(String.valueOf(9)) + ".jpg"); share(file,
					 * v);
					 */
                    rel_show_share.setVisibility(View.VISIBLE);
                    if (null != time) {
                        time.cancel();
                        time = null;
                    }
                    time = new TimeCount(4000, 1000);
                    ToastUtil.showShortText(context, "分享中，请稍等哦~");
                    time.start();
                }
            }

        }.execute();
    }

    private Bitmap downloadPic(String picPath) {
        try {
            URL url = new URL(YUrl.imgurl + picPath);
            // 打开连接
            URLConnection con = url.openConnection();
            // 获得文件的长度
            int contentLength = con.getContentLength();
            System.out.println("长度 :" + contentLength);
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[8192];
            // 读取到的数据长度
            int len;
            BitmapDrawable bmpDraw = new BitmapDrawable(is);

            // 完毕，关闭所有链接
            is.close();
            return bmpDraw.getBitmap();
        } catch (Exception e) {
            LogYiFu.e("TAG", "下载失败");

            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        if (null == time) {
            super.onBackPressed();
        } else {
            time.cancel();
            time = null;
            rel_show_share.setVisibility(View.GONE);
        }
    }

    // 计时器
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔

        }

        @Override
        public void onFinish() {// 计时完毕时触发
            try {
                File file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");
                share(file, null);
                // onceShare(intent, "微信");

                rel_show_share.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            try {
                // btn.setEnabled(false);
                // btn.setBackgroundResource(R.color.time_count);
                // text.setText(millisUntilFinished / 1000 + "秒后将自动微信分享");
                int i = Integer.valueOf(millisUntilFinished / 1000 + "") - 1;
                img_count_down.setImageResource(countDownBg[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void customDialog() {
        final Dialog dialog = new Dialog(context, R.style.invate_dialog_style);
//        View view = View.inflate(context, R.layout.cancle_order_dialog, null);
//        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
//        tv_content.setText("这么好的宝贝，确定不要了吗？");
        View view = View.inflate(context, R.layout.dialog_order_back, null);
        TextView btn_cancel = (TextView) view.findViewById(R.id.btn_cancel);

        ((TextView) view.findViewById(R.id.balance_dialog_tv1)).setText("这么好的宝贝，确定不要了吗？");


        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 把这个对话框取消掉
                dialog.dismiss();
            }
        });
        TextView btn_ok = (TextView) view.findViewById(R.id.btn_ok);
        btn_ok.setText("不要了");
        btn_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // mController = null;

                // Intent intent=new

                dialog.dismiss();
                if (time != null) {
                    time.cancel();
                    time = null;
                    rel_show_share.setVisibility(View.GONE);
                    isClick = false;
                } else {
                    // Intent intent = new Intent(SubmitMultiShopActivty.this,
                    // ShopCartNewNewActivity.class);
                    // startActivity(intent);
                    finish();
                }
            }
        });

        // 创建自定义样式dialog
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1001) { // 修改地址
            if (null != intent) {
                dAddress = (DeliveryAddress) intent.getSerializableExtra("item");
                addressId = dAddress.getId();
                setDeliverAddress(null, dAddress);
            } else {
                initData(1002);
            }
        } else if (requestCode == 1002) { // 设置地址
            initData(requestCode);
        } else if (requestCode == 1003 && resultCode == 1) {
            SubmitMultiShopActivty.this.setResult(1); // 1，代表支付成功；
            LogYiFu.e("TAG", "提交订单，刷新购物车。。");
            finish();
        } else if (requestCode == 1005 && resultCode == 2001) {
            sum = sum + discount;
            boolean flag = intent.getBooleanExtra("isJinQuan", false);
            if (flag) {
                tv_discount_coupon.setText("金券");
                tv_discount_coupon_count.setText("    可使用1张金券");
                tv_gold_voucaher.setText("金券");
            } else {
                tv_discount_coupon.setText("优惠券");
                tv_discount_coupon_count.setText("可使用1张优惠券");
                tv_gold_voucaher.setText("优惠券");
            }
            mCouponMoney.setVisibility(View.VISIBLE);
            mRlCoupon.setVisibility(View.VISIBLE);
            // sum = amount;
            mapCoupon = (HashMap<String, Object>) intent.getSerializableExtra("selectUseful");
            tv_discount_coupon_count.setVisibility(View.VISIBLE);
            // tv_discount_coupon_count.setText("可使用1张优惠券");
            mCouponMoney.setText("-¥" + mapCoupon.get("c_price"));
            // if(mapCoupon.get("c_price")>0)
            mCoupon.setText("-¥" + mapCoupon.get("c_price"));
            discount = (Double) mapCoupon.get("c_price");
            sum = sum - discount;
            total_account.setText(Html.fromHtml(
                    getString(R.string.total_account, shopNum, new java.text.DecimalFormat("#0.00").format(sum))));

            // tv_settle_account.setText(
            // "实付款:¥" + new java.text.DecimalFormat("#0.00").format(sum -
            // mYouhuiMoneyCount - mVouchersCount));
            // TODO:999999999999999999999
            if (mTgbs.isChecked()) {
                sum = sum + inteDiscount;
                getJiFen(myIntegCount);
            }
            setMoney(sum - mYouhuiMoneyCount - mVouchersCount, true);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.tgb:// 积分按钮
                isAddIntegral = isChecked;
                if (isChecked) {
                    double inteDiscount = 0;
                    if (mIsGold) {// 开启了积分变金币
                        inteDiscount = 0.01 * integral;
                        tv_integral_notice.setText(
                                "可用金币:" + integral + "   可抵用¥" + new java.text.DecimalFormat("#0.00").format(inteDiscount));
                    } else {
                        inteDiscount = 0.01 * integral;
                        tv_integral_notice.setText(
                                "可用积分:" + integral + "   可抵用¥" + new java.text.DecimalFormat("#0.00").format(inteDiscount));
                    }

                    mRlIntegral.setVisibility(View.VISIBLE);
                    mIntegral.setText("-¥" + new java.text.DecimalFormat("#0.00").format(inteDiscount) + "");
                    sum -= inteDiscount;

                    total_account.setText(Html.fromHtml(
                            getString(R.string.total_account, shopNum, new java.text.DecimalFormat("#0.00").format(sum))));
                    double sum2 = sum - mVouchersCount;
                    // tv_settle_account
                    // .setText("实付款:¥" + new
                    // java.text.DecimalFormat("#0.00").format(sum2 -
                    // mYouhuiMoneyCount));

                    // TODO:55555555555
                    setMoney(sum2 - mYouhuiMoneyCount, true);
                } else {
                    double inteDiscount = 0;
                    if (mIsGold) {// 开启了积分变金币
                        inteDiscount = 0.01 * integral;
                        tv_integral_notice
                                .setText("可用金币:" + 0 + "   可抵用¥" + new java.text.DecimalFormat("#0.00").format(0.0));
                    } else {
                        inteDiscount = 0.01 * integral;
                        tv_integral_notice
                                .setText("可用积分:" + 0 + "   可抵用¥" + new java.text.DecimalFormat("#0.00").format(0.0));
                    }
                    mRlIntegral.setVisibility(View.GONE);
                    mIntegral.setText(0.0 + "");
                    sum += inteDiscount;

                    total_account.setText(Html.fromHtml(
                            getString(R.string.total_account, shopNum, new java.text.DecimalFormat("#0.00").format(sum))));
                    double sum2 = sum - mVouchersCount;
                    // tv_settle_account
                    // .setText("实付款:¥" + new
                    // java.text.DecimalFormat("#0.00").format(sum2 -
                    // mYouhuiMoneyCount));
                    // TODO:666666666666666666
                    setMoney(sum2 - mYouhuiMoneyCount, true);
                }
                // 111111111111111111
                if (discount == 0 && mSystemTimeMap != null && mSystemTimeMap.size() > 0 && mGoldVoucherMap != null
                        && mGoldVoucherMap.size() > 0 && Integer.parseInt(mGoldVoucherMap.get("is_open")) == 1
                        && (Long) mSystemTimeMap.get("now") < Long.parseLong(mGoldVoucherMap.get("end_date"))
                        && (Long) mSystemTimeMap.get("now") < Long.parseLong(mGoldVoucherMap.get("c_last_time"))
                        && Integer.parseInt(mGoldVoucherMap.get("is_use")) == 0 && sum - mYouhuiMoneyCount
                        - Double.parseDouble(mGoldVoucherMap.get("c_price")) - mVouchersCount > 0) {
                    mRlCoupon.setVisibility(View.VISIBLE);
                    tv_discount_coupon_count.setVisibility(View.VISIBLE);
                    tv_discount_coupon.setText("金券");
                    tv_discount_coupon_count.setText("     可使用1张金券");
                    tv_gold_voucaher.setText("金券");
                    mCouponMoney.setVisibility(View.VISIBLE);
                    mCouponMoney.setText("-¥" + mGoldVoucherMap.get("c_price"));
                    discount = Double.parseDouble(mGoldVoucherMap.get("c_price"));

                    double discount = Double.parseDouble(mGoldVoucherMap.get("c_price"));
                    sum = sum - discount;

                    // amount = sum;
                    // afterCoupon = sum;

                    total_account.setText(Html.fromHtml(
                            getString(R.string.total_account, shopNum, new java.text.DecimalFormat("#0.00").format(sum))));
                    mCoupon.setVisibility(View.VISIBLE);
                    mCoupon.setText("-¥" + new java.text.DecimalFormat("#0.00").format(discount));
                    // tv_settle_account.setText("实付款:¥"
                    // + new java.text.DecimalFormat("#0.00").format(sum -
                    // mYouhuiMoneyCount - mVouchersCount));
                    // mapCoupon.put("id",
                    // Integer.parseInt(mGoldVoucherMap.get("c_id")));
                    useFlag = true;
                    mCoreFlag = true;

                    // TODO:7777777777777777777777
                    setMoney(sum - mYouhuiMoneyCount - mVouchersCount, true);
                } else if (mCoreFlag) {
                    mCoreFlag = false;
                    mRlCoupon.setVisibility(View.GONE);
                    tv_discount_coupon_count.setVisibility(View.VISIBLE);
                    tv_discount_coupon.setText("优惠券");
                    tv_discount_coupon_count.setText("无可用");
                    tv_gold_voucaher.setText("优惠券");
                    mCouponMoney.setVisibility(View.GONE);
                    sum = sum + discount;
                    discount = 0;
                    total_account.setText(Html.fromHtml(
                            getString(R.string.total_account, shopNum, new java.text.DecimalFormat("#0.00").format(sum))));
                    mCoupon.setVisibility(View.GONE);
                    // tv_settle_account.setText("实付款:¥"
                    // + new java.text.DecimalFormat("#0.00").format(sum -
                    // mYouhuiMoneyCount - mVouchersCount));
                    // TODO:888888888888888888
                    setMoney(sum - mYouhuiMoneyCount - mVouchersCount, true);
                }
                break;
            case R.id.tgb_money:// 余额支付按钮
                // if (isChecked) {// 打开
//                submit_money.setText("-¥" + new DecimalFormat("#0.00").format(mUseMoney));
                // sum -= mUseMoney;
                tv_settle_account.setText("实付款:¥" + new java.text.DecimalFormat("#0.00")
                        .format(sum - mYouhuiMoneyCount - mVouchersCount - mUseMoney));

                tv_money_notice_new.setText("可抵￥" + new DecimalFormat("#0.00").format(mUseMoney) + "(最高可抵10%)");
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 这里重写返回键
            customDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public int countVoucachers(int userful) {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int mIntUse = userful;
        cicle:
        for (int i = 0; i <= mTen; i++) {
            if (10 * i > mIntUse) {
                a = i - 1;
                mIntUse -= (i - 1) * 10;
                for (int j = 0; j <= mFive; j++) {
                    if (5 * j > mIntUse) {
                        b = j - 1;
                        mIntUse -= (j - 1) * 5;
                        for (int k = 0; k <= mTwo; k++) {
                            if (2 * k > mIntUse) {
                                c = k - 1;
                                mIntUse -= (k - 1) * 2;
                                for (int l = 0; l <= mOne; l++) {
                                    if (1 * l > mIntUse) {
                                        d = l - 1;
                                        break cicle;
                                    } else {
                                        //
                                        if (l == mOne) {
                                            d = l;
                                            mIntUse -= l;
                                            userful -= mIntUse;
                                            break cicle;
                                        }

                                    }
                                }
                            } else {
                                //
                                if (k == mTwo) {
                                    c = k;
                                    mIntUse -= 2 * k;
                                    for (int l = 0; l <= mOne; l++) {
                                        if (1 * l > mIntUse) {
                                            d = l - 1;
                                            break cicle;
                                        } else {
                                            //
                                            if (l == mOne) {
                                                d = l;
                                                mIntUse -= l;
                                                userful -= mIntUse;
                                                break cicle;
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        //
                        if (j == mFive) {
                            b = j;
                            mIntUse -= b * 5;
                            for (int k = 0; k <= mTwo; k++) {
                                if (2 * k > mIntUse) {
                                    c = k - 1;
                                    mIntUse -= (k - 1) * 2;
                                    for (int l = 0; l <= mOne; l++) {
                                        if (1 * l > mIntUse) {
                                            d = l - 1;
                                            break cicle;
                                        } else {
                                            //
                                            if (l == mOne) {
                                                d = l;
                                                mIntUse -= l;
                                                userful -= mIntUse;
                                                break cicle;
                                            }

                                        }
                                    }
                                } else {
                                    //
                                    if (k == mTwo) {
                                        c = k;
                                        mIntUse -= 2 * k;
                                        for (int l = 0; l <= mOne; l++) {
                                            if (1 * l > mIntUse) {
                                                d = l - 1;
                                                break cicle;
                                            } else {
                                                //
                                                if (l == mOne) {
                                                    d = l;
                                                    mIntUse -= l;
                                                    userful -= mIntUse;
                                                    break cicle;
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                //
                if (i == mTen) {
                    a = i;
                    mIntUse -= a * 10;
                    for (int j = 0; j <= mFive; j++) {
                        if (5 * j > mIntUse) {
                            b = j - 1;
                            mIntUse -= (j - 1) * 5;
                            for (int k = 0; k <= mTwo; k++) {
                                if (2 * k > mIntUse) {
                                    c = k - 1;
                                    mIntUse -= (k - 1) * 2;
                                    for (int l = 0; l <= mOne; l++) {
                                        if (1 * l > mIntUse) {
                                            d = l - 1;
                                            break cicle;
                                        } else {
                                            //
                                            if (l == mOne) {
                                                d = l;
                                                mIntUse -= l;
                                                userful -= mIntUse;
                                                break cicle;
                                            }

                                        }
                                    }
                                } else {
                                    //
                                    if (k == mTwo) {
                                        c = k;
                                        mIntUse -= 2 * k;
                                        for (int l = 0; l <= mOne; l++) {
                                            if (1 * l > mIntUse) {
                                                d = l - 1;
                                                break cicle;
                                            } else {
                                                //
                                                if (l == mOne) {
                                                    d = l;
                                                    mIntUse -= l;
                                                    userful -= mIntUse;
                                                    break cicle;
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            //
                            if (j == mFive) {
                                b = j;
                                mIntUse -= b * 5;
                                for (int k = 0; k <= mTwo; k++) {
                                    if (2 * k > mIntUse) {
                                        c = k - 1;
                                        mIntUse -= (k - 1) * 2;
                                        for (int l = 0; l <= mOne; l++) {
                                            if (1 * l > mIntUse) {
                                                d = l - 1;
                                                break cicle;
                                            } else {
                                                //
                                                if (l == mOne) {
                                                    d = l;
                                                    mIntUse -= l;
                                                    userful -= mIntUse;
                                                    break cicle;
                                                }

                                            }
                                        }
                                    } else {
                                        //
                                        if (k == mTwo) {
                                            c = k;
                                            mIntUse -= 2 * k;
                                            for (int l = 0; l <= mOne; l++) {
                                                if (1 * l > mIntUse) {
                                                    d = l - 1;
                                                    break cicle;
                                                } else {
                                                    //
                                                    if (l == mOne) {
                                                        d = l;
                                                        mIntUse -= l;
                                                        userful -= mIntUse;
                                                        break cicle;
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        mTenUse = a;
        mFiveUse = b;
        mTwoUse = c;
        mOneUse = d;
        mTen -= a;
        mFive -= b;
        mTwo -= c;
        mOne -= d;
        return userful;
    }

    // private long recLen = 30 * 1000 * 60;
    private long recLen = 10000;
    Timer timer = new Timer();

    private double maxRate = 0.1;
    private double maxMoney = 0;

    /**
     * 获取下单前可使用余额比例和额度
     */
    private void getOrderMoney() {
        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) SubmitMultiShopActivty.this,
                R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getOrderMoney(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);

                if (null == e && result != null) {
                    maxRate = Double.parseDouble((String) result.get("maxRate"));
                    maxMoney = Double.parseDouble((String) result.get("maxMoney"));
                    // mLimitMoney=sum*maxRate;
                    queryMyMomeny();
                }
            }

        }.execute();
    }

    /**
     * 调用此接口查询我的余额 TODO:
     */
    private void queryMyMomeny() {

        new SAsyncTask<Void, Void, String[]>(SubmitMultiShopActivty.this, R.string.wait) {

            @Override
            protected String[] doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel2.myWalletInfo(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, String[] result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result != null && result.length > 0) {
                        String balance = result[0];// 我的余额
                        mMyMoney = Double.parseDouble(balance);
                        if (mIsQulification && mIsOpen == 1) {// 余额翻倍期间，>=6;可以使用余额
                            mMyMoney = mMyMoney * mTwofoldness;
                        }
                        // double mMoney = 0;// 可使用余额
                        // if (mLimitMoney > mMyMoney) {// 最多抵扣我的余额
                        // mMoney = mMyMoney;
                        // } else {
                        // mMoney = mLimitMoney;
                        // }
                        // if(mMoney>maxMoney){//不能超过最大额度
                        // mMoney=maxMoney;
                        // }
                        // mUseMoney = mMoney;
                        // if (mMoney > 0) {
                        // mMoneyTgb.setChecked(true);
                        // mMoneyTgb.setEnabled(true);
                        // } else {
                        // mMoneyTgb.setChecked(false);
                        // mMoneyTgb.setEnabled(false);// 让其不可点击
                        // }
                        // tv_money_notice_new.setText("可抵￥" + new
                        // DecimalFormat("#0.00").format(mMoney) + "(最高可抵10%)");
                        // tv_new_money_notice.setText(
                        // "可用余额¥" + new
                        // java.text.DecimalFormat("#0.00").format(mMyMoney *
                        // mTwofoldness)
                        // + " 可抵用¥" + new
                        // java.text.DecimalFormat("#0.00").format(mMoney));
                        // submit_money.setText("-¥" + new
                        // DecimalFormat("#0.00").format(mMoney));
                        // sum -= mMoney;
                        tv_settle_account.setText("实付款:¥" + new java.text.DecimalFormat("#0.00")
                                .format(sum - mYouhuiMoneyCount - mVouchersCount));
                        // getMyIntegral(mMyMoney);
                        double price;
                        if (isDapei) {
                            price = mPriceCount * 0.9;
                        } else {
                            price = mPriceCount;
                        }
                        getProxCoup(price);// 匹配优惠券

                    }

                }
            }

        }.execute((Void[]) null);

    }

    /**
     * 活动商品单个订单
     */
    private void submitSignOrder(final View v, final int shop_num) {
        new SAsyncTask<Void, Void, HashMap<String, Object>>(this, null, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                String packageCode = listGoods.get(0).getShop_code();// 签到 提交订单
                // 商品编号
                return ComModel2.submitHuoDongSign(context, message, "" + listGoods.get(0).getShop_code(),
                        "" + listGoods.get(0).getStock_type_id(), addressId, shop_num, rollCode);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    orderNo = (String) result.get("order_code");
					/*
					 * ToastUtil.showShortText(context, (String)
					 * result.get("message"));
					 */

                    SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);

                    // 跳转到收银台界面
                    Intent intent = new Intent(SubmitMultiShopActivty.this, MealPaymentActivity.class);
                    YJApplication.startFukuanYndao();//开始两分钟计时
                    LogYiFu.e("TAG", "点击提交订单");
                    int url = (Integer) result.get("url");
                    Bundle bundle = new Bundle();
                    intent.putExtra("order_code", orderNo);
                    bundle.putSerializable("result", result);
                    intent.putExtras(bundle);
                    intent.putExtra("shop_code", "" + listGoods.get(0).getShop_code());
                    intent.putExtra("isMulti", false);
                    // intent.putExtra("totlaAccount", sum);
                    intent.putExtra("totlaAccount", Double.parseDouble((String) result.get("price")));
                    intent.putExtra("sign_huodong", "sign_huodong");
                    intent.putExtra("mIsTwoGroup", mIsTwoGroup);
                    intent.putExtra("groupFlag", groupFlag);
                    // intent.putExtra("signType", "3");
                    // intent.putExtra("signValue", signValue);
                    // intent.putExtra("pos", pos);
                    SubmitMultiShopActivty.this.finish();
                    if (url > 1) {
                        intent.putExtra("isMulti", true);
                    }
                    startActivityForResult(intent, 1003);
                }
            }

        }.execute((Void[]) null);
    }


    /**
     * 拼团商品提交订单
     */
    private void submitSignGroupShop(final View v) {

        Iterator<Entry<Integer, EditText>> iterator = mapEdit.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Integer, EditText> entry = iterator.next();
            String s = entry.getValue().getText().toString().trim();
//            if (!TextUtils.isEmpty(s)) {
//                if (StringUtils.containsEmoji(s)) {
//                    Toast.makeText(context, "不得含有特殊字符", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (RegisterFragment.getWordCount(s) < 5) {
//                    Toast.makeText(context, "订单说明不得少于五个字符", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (RegisterFragment.getWordCount(s) > 500) {
//                    Toast.makeText(context, "订单说明不得多于五百个字符", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
            mapMsg.put(entry.getKey(), s);
        }
        new SAsyncTask<Void, Void, HashMap<String, Object>>(this, null, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.submitSignGroupShop(context, addressId, listGoods, mapMsg);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    orderNo = (String) result.get("order_code");

//                    SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);

//                    // 跳转到收银台界面
//                    Intent intent = new Intent(SubmitMultiShopActivty.this, MealPaymentActivity.class);
////                    int url = (Integer) result.get("url");
//                    Bundle bundle = new Bundle();
//                    intent.putExtra("order_code", orderNo);
//                    bundle.putSerializable("result", result);
//                    intent.putExtras(bundle);
//                    intent.putExtra("isMulti", false);
//                    // intent.putExtra("totlaAccount", sum);
//                    intent.putExtra("totlaAccount", Double.parseDouble((String) result.get("price")));
//                    intent.putExtra("sign_huodong", "sign_huodong");
//                    intent.putExtra("mIsTwoGroup", mIsTwoGroup);
//                    intent.putExtra("groupFlag",groupFlag);
//                    SubmitMultiShopActivty.this.finish();
////                    if (url > 1) {
////                        intent.putExtra("isMulti", true);
////                    }
//                    startActivityForResult(intent, 1003);


                    if (SignGroupShopActivity.instance != null) {
                        SignGroupShopActivity.instance.finish();
                    }
                    Intent intent = new Intent(SubmitMultiShopActivty.this, GroupsDetailsActivity.class);//跳转到拼团详情
                    intent.putExtra("completeStatus", 4);
                    SubmitMultiShopActivty.this.finish();
                    startActivity(intent);


                }
            }

        }.execute((Void[]) null);
    }


    // 倒计时
    class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen -= 1000;
                    String days;
                    String hours;
                    String minutes;
                    String seconds;
                    long minute = recLen / 60000;
                    long second = (recLen % 60000) / 1000;
                    if (minute >= 60) {
                        long hour = minute / 60;
                        minute = minute % 60;
                        if (hour >= 24) {
                            long day = hour / 24;
                            hour = hour % 24;
                            if (day < 10) {
                                days = "0" + day;
                            } else {
                                days = "" + day;
                            }
                            if (hour < 10) {
                                hours = "0" + hour;
                            } else {
                                hours = "" + hour;
                            }
                            if (minute < 10) {
                                minutes = "0" + minute;
                            } else {
                                minutes = "" + minute;
                            }
                            if (second < 10) {
                                seconds = "0" + second;
                            } else {
                                seconds = "" + second;
                            }
                            tv_time.setText("" + days + "天" + hours + ":" + minutes + ":" + seconds);
                        } else {
                            if (hour < 10) {
                                hours = "0" + hour;
                            } else {
                                hours = "" + hour;
                            }
                            if (minute < 10) {
                                minutes = "0" + minute;
                            } else {
                                minutes = "" + minute;
                            }
                            if (second < 10) {
                                seconds = "0" + second;
                            } else {
                                seconds = "" + second;
                            }
                            tv_time.setText("" + hours + ":" + minutes + ":" + seconds);
                        }
                    } else if (minute >= 10 && second >= 10) {
                        tv_time.setText("" + minute + ":" + second);
                    } else if (minute >= 10 && second < 10) {
                        tv_time.setText("" + minute + ":0" + second);
                    } else if (minute < 10 && second >= 10) {
                        tv_time.setText("0" + minute + ":" + second);
                    } else {
                        tv_time.setText("0" + minute + ":0" + second);
                    }
                    // tv_time.setText("" + recLen);
                    if (recLen < 0) {
                        timer.cancel();
                        // tv_time.setText("00:00");
                        tv_time.setVisibility(View.GONE);
                        // btn_pay.setBackgroundColor(Color.parseColor("#a8a8a8"));
                        // btn_pay.setClickable(false);

                    }
                }
            });
        }

    }

    private void share(File file, final View v) {
        LogYiFu.e(SUBMIT, "//1111111");

        if (file == null) {
            Toast.makeText(this, "您的网络状态不太好哦~~", Toast.LENGTH_SHORT).show();
            return;
        }

        UMImage umImage = new UMImage(this, file);
        // UMImage umImage = new UMImage(this, R.drawable.huo_dong);
        // UMImage umImage = new UMImage(this, R.drawable.huodong_new);
        ShareUtil.configPlatforms(this);
        ShareUtil.shareShop(this, umImage);
        // if (mController == null) {
        UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);
        // }

        mController.postShare(instance, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {
            // Dialog dialogShare;

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                m++;
                LogYiFu.e(SUBMIT, "!!!!!!" + m);
                LogYiFu.e(SUBMIT, "//mController");
                // if (dialogShare == null) {
                // dialogShare = new Dialog(instance,
                // R.style.invate_dialog_style);
                // }
                String showText = platform.toString();
                if (eCode == StatusCode.ST_CODE_SUCCESSED) {
                    LogYiFu.e(SUBMIT, "//Success");
                    submitOrder(null);
                } else {
                    submitOrder(null);
                }
            }

        });

    }

    private void showShareDialog() {
        // Activity activity = SubmitMultiShopActivty.this;
        // while (activity.getParent() != null) {
        // activity = activity.getParent();
        // }
        final Dialog dialogShare = new Dialog(instance, R.style.invate_dialog_style);
        View view = View.inflate(context, R.layout.vouchers_queen_dialog, null);
        TextView tv = (TextView) view.findViewById(R.id.voucachers_dialog_tv);
        tv.setText("分享就能使用" + mVouchersCount + "元抵用券哦，");
        Button btn_cancel = (Button) view.findViewById(R.id.share_cancle);

        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                for (int i = 0; i < mListTB.size(); i++) {
                    mListTB.get(i).setChecked(false);
                }
                dialogShare.dismiss();
                // dialogShare=null;
            }
        });
        Button btn_ok = (Button) view.findViewById(R.id.share_goon);
        btn_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogShare.dismiss();
                getShopLink(v);
            }
        });

        // 创建自定义样式dialog
        dialogShare.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        dialogShare.setCancelable(false);
        dialogShare.show();

    }

    public void setMoney(double sum, boolean flag) {
        mLimitMoney = (sum) * maxRate;
        double mMoney = 0;// 可使用余额
        if (mLimitMoney > mMyMoney) {// 最多抵扣我的余额
            mMoney = mMyMoney;
        } else {
            mMoney = mLimitMoney;
        }
        if (mMoney > maxMoney) {// 不能超过最大额度
            mMoney = maxMoney;
        }
        mUseMoney = mMoney;
        if (!flag) {
            if (mMoney > 0) {
                if (!mMoneyTgb.isChecked()) {
                    mMoneyTgb.setChecked(true);
                }
                mMoneyTgb.setEnabled(true);
            } else {
                if (mMoneyTgb.isChecked()) {
                    mMoneyTgb.setChecked(false);
                }
                mMoneyTgb.setEnabled(false);// 让其不可点击
            }
        }

        // sum -= mMoney;
        if (mMoneyTgb.isChecked()) {
            String strPrice = new DecimalFormat("#0.00").format(mMoney);
            Double price = Double.parseDouble(strPrice);// 为了精确
            tv_money_notice_new.setText("可抵￥" + new DecimalFormat("#0.00").format(mMoney) + "(最高可抵10%)");
            tv_settle_account.setText("实付款:¥" + new java.text.DecimalFormat("#0.00").format(sum - price));

//            submit_money.setText("-¥" + new DecimalFormat("#0.00").format(mMoney));
        } else {
            tv_settle_account.setText("实付款:¥" + new java.text.DecimalFormat("#0.00").format(sum));
            tv_money_notice_new.setText("可抵￥" + new DecimalFormat("#0.00").format(0) + "(最高可抵10%)");
//            submit_money.setText("-¥" + new DecimalFormat("#0.00").format(0));

        }

        //1元购抵相关
        getdiKOU(sum, submit_money, tv_settle_account);


        if (!mTgbs.isChecked()) {
            tv_integral_notice.setText("可用积分:" + 0 + "   可抵用¥" + 0.0);
        }
    }

    private void getdiKOU(final double sum, final TextView submit_money, final TextView tv_settle_account) {


        new SAsyncTask<Void, Void, String>(SubmitMultiShopActivty.this, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected String doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel2.getALLDikouKeyong(context);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, String result, Exception e) {
                if (null == e) {

                    if (Double.parseDouble(result) <= sum) {//可抵扣的金额不大于需要付的总金额
                        submit_money.setText("-¥" + result);
                        tv_settle_account.setText("实付款:¥" + new DecimalFormat("#0.00").format(sum - Double.parseDouble(result)));
                    } else { //大于需要支付了总金额
                        submit_money.setText("-¥" + sum);
                        tv_settle_account.setText("实付款:¥0.01");

                    }

                    if(Double.parseDouble(result) == 0){
                        submit_money.setText("¥0.0");
                    }

                }

                super.onPostExecute(context, result, e);
            }

        }.execute();


    }


}
