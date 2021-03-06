package com.yssj.ui.activity.shopdetails;

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
import com.yssj.huanxin.PublicUtil;
import com.yssj.model.ComModel2;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.activity.infos.UsefulCouponsActivity;
import com.yssj.ui.activity.setting.ManMyDeliverAddr;
import com.yssj.ui.activity.setting.SetDeliverAddressActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.YCache;
import com.yssj.utils.YunYingTongJi;
import com.yssj.utils.sqlite.ShopCartDao;
import com.yssj.wxpay.WxPayUtil;

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

/**
 * ???????????????????????????
 */
@SuppressLint("StringFormatMatches")
public class NewMealSubmitOrderActivity extends BasicActivity implements OnCheckedChangeListener {
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
    private Context context;

    private static final int SDK_PAY_FLAG = 1;
    private AlertDialog dialog2 = null, dialogPay = null;
    private DeliveryAddress dAddress;
    private HashMap<String, String> addResult;
    private String message = "";
    private Store store;
    private String orderNo;
    private String mDef_pic;
    Map<String, String> resultunifiedorder;

    IWXAPI msgApi;// ??????api

    private int addressId = 0;

    private Intent intent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
    private LinearLayout lin_set_addr;

    private List<ShopCart> listGoods;

    private LinearLayout container;
    // TODO:
    // private static Dialog dialogShare;
    private List<ToggleButton> mListTB = new ArrayList<ToggleButton>();
    private boolean mIsChecked = true; // ????????????????????????
    private int mVouchersCount = 0;// ????????????????????????
    private List<ShopCart> mListShopCart = new ArrayList<ShopCart>();// ??????????????????????????????????????????
    private RelativeLayout rel_show_share;
    private ImageView img_count_down;
    String SUBMIT = "SubmitMultiShopActivty";
    private TimeCount time;
    private int[] countDownBg = {R.drawable.count_down_1, R.drawable.count_down_2, R.drawable.count_down_3,
            R.drawable.count_down_3};
    private TextView mSubmitTotal;// ??????
    private TextView mPockage;// ??????
    private TextView mVoucachers, mCoupon, mIntegral, mCouponMoney, tv_gold_voucaher;// ??????????????????????????????,???????????????
    private TextView mShareVoucachers;
    private RelativeLayout mRlCoupon, mRlIntegral;// ??????????????????????????????????????????
    private double mTotal = 0;// ??????
    private int mTen = 0, mFive = 0, mTwo = 0, mOne = 0;// 10???5???2???1??????????????????
    private int mTenUse = 0, mFiveUse = 0, mTwoUse = 0, mOneUse = 0;// ????????????????????????????????????
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

    private HashMap<Integer, HashMap<String, Object>> mapCoups = new HashMap<Integer, HashMap<String, Object>>();// ??????????????????

    // private HashMap<Integer, String> mapInteg = new HashMap<Integer,
    // String>();

    private ImageView ivBack;

    private RelativeLayout rel_name_phone;
    private int CODE_PAY;

    private RelativeLayout rlTotal;

    private RelativeLayout rlData, rl_dapeigou;

    int shopNum = 0;

    private int myIntegCount = 0;// ???????????????

    private HashMap<String, Object> mapCoupon;

    public static NewMealSubmitOrderActivity instance;

    private List<ShopCart> shopCartMeal = new ArrayList<ShopCart>();

    private String orderToken;

    private RelativeLayout rl_discount_coupon, rel_havedapei;
    private RelativeLayout submit_rl_group_buy, submit_rl_need_pay;//???????????????????????????????????????????????????
    private TextView submit_tv_buy;
    private TextView submit_tv_group_price, submit_tv_need_pay_price;
    private TextView tv_discount_coupon_count, tv_integral_notice, tv_time, youhui_jine, submit_dapeigou,
            jiesuan_jiesheng, mTvItegrals, mTvBottomItegrals, tv_discount_coupon, tv_special_price;
    private LinearLayout mShareBack;
    private TextView tv_money_notice_new;
    private int mDiscountInte = 0;
    private double mPriceCount = 0.0;// ??????????????????
    private double inteDiscount = 0.0;// ??????????????????????????????
    private double discount = 0.0;// ??????????????????????????????
    private Double cartmYouhuiMoneyCount;
    private Double mYouhuiMoneyCount;
    private int integral;
    private Boolean isDapei;
    private Boolean useFlag = false;// ??????????????????
    // private SwitchButton sbt;
    private ToggleButton mTgbs, mMoneyTgb;
    private boolean isAddIntegral = true;
    // private UMSocialService mController;
    boolean flag = false;

    private MyTimerTask mTask;
    private RelativeLayout submit_rl_money;// ?????? ??????????????????
    private TextView submit_money;// ???????????????????????????????????????
    private double mMyMoney;
    private boolean mIsGold = false;// ??????????????????????????????

    private boolean mCoreFlag = false;// true???????????????????????????????????????????????????
    private boolean mHuoDongFlag = false;// ????????????????????????????????????????????????true????????????false????????????
    private boolean mIsTwoGroup = false;// ?????????????????????????????????????????????2???????????????
    private String rollCode = "0";// ???????????????????????????
    private double mLimitMoney = 0;// ??????????????????????????????
    private double mUseMoney = 0;// ?????????????????????
    String str_jinbi_endDate = "-1";
    private Double mGroupPrice = 9.90;
    private int groupFlag = 0;//????????????mHuoDongFlag=true,groupFlag!=0;?????????????????????

    private ImageView ivBalanceLottory;

    private int shop_num;
    private String buy_shop_code = "";
    private String shop_pic;
    private String color_size;

    String shopAttr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        aBar.hide();
        instance = this;
        context = this;
        flag = false;
        groupFlag = getIntent().getIntExtra("groupFlag", 0);

        shop_num = getIntent().getIntExtra("shop_num", 1);
        buy_shop_code = getIntent().getStringExtra("buy_shop_code");
        shop_pic = getIntent().getStringExtra("shop_pic");
        color_size = getIntent().getStringExtra("color_size");

        mHuoDongFlag = getIntent().getBooleanExtra("isSignActiveShop", false);
        mIsTwoGroup = getIntent().getBooleanExtra("mIsTwoGroup", false);
        rollCode = getIntent().getStringExtra("rollCode");
        mGroupPrice = getIntent().getDoubleExtra("mSignGroupsPrice", 9.90);
        // // ???????????????
        // if (!mHuoDongFlag) {
        // getVoucachersCount();
        // }
        orderToken = YCache.getOrderToken(this);
        setContentView(R.layout.submit_multi_goods);

        submit_rl_money = (RelativeLayout) findViewById(R.id.submit_rl_money);
        submit_money = (TextView) findViewById(R.id.submit_money);
        img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        jiesuan_jiesheng = (TextView) findViewById(R.id.jiesuan_jiesheng);
        tv_special_price = (TextView) findViewById(R.id.tv_special_price);
        mShareBack = (LinearLayout) findViewById(R.id.img_back_share);
        mShareBack.setOnClickListener(this);
        rl_dapeigou = (RelativeLayout) findViewById(R.id.rl_dapeigou);
        submit_dapeigou = (TextView) findViewById(R.id.submit_dapeigou);
        btn_pay = (LinearLayout) findViewById(R.id.btn_pay);// ????????????
        recLen = getIntent().getLongExtra("Time", 0);

        youhui_jine = (TextView) findViewById(R.id.youhui_jine);

        tv_time = (TextView) findViewById(R.id.tv_time);
        tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("????????????");
        // MyLogYiFu.e("TAG", "????????????");

        ivBalanceLottory = (ImageView) findViewById(R.id.img_balance_lottery);

        listGoods = (List<ShopCart>) getIntent().getExtras().getSerializable("listGoods");


        initShopAttr();


        isDapei = getIntent().getBooleanExtra("isDapei", false);

        // bundle.putBoolean("isDapei", true);

        cartmYouhuiMoneyCount = getIntent().getDoubleExtra("mYouhuiMoneyCount", 0.0);
        mYouhuiMoneyCount = Double.parseDouble(new DecimalFormat("#0.00").format(cartmYouhuiMoneyCount));//

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

            sum += shopCart.getShop_se_price() * shopCart.getShop_num();// ????????????????????????
            // mLimitMoney = sum * 0.1;
            mPriceCount += shopCart.getShop_se_price() * shopCart.getShop_num();// ????????????

        }
        // if (isDapei) {
        // mDiscountInte = (int) (mPriceCount * 50 * 0.9);
        // } else {
        // mDiscountInte = (int) (mPriceCount * 50);
        // }
        if (null != YCache.getCacheUserSafe(context)) {
            if (YCache.getCacheUserSafe(context).getIs_member().equals("2")) {// ?????????????????????
                sum = sum * 0.95;// ????????????????????? ???95???
            }
        }

        rlData = (RelativeLayout) findViewById(R.id.rl_data);

        rel_havedapei = (RelativeLayout) findViewById(R.id.rel_havedapei);

        msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp(WxPayUtil.APP_ID);
        initData(0);

        // ??????????????????
        if (mTask != null) {
            mTask.cancel();
            mTask = new MyTimerTask();
        } else {
            mTask = new MyTimerTask();
        }
        timer.schedule(mTask, 0, 1000); // timeTask

        store = YCache.getCacheStoreSafe(NewMealSubmitOrderActivity.this);


        //??????????????????
        PublicUtil.getBalanceNum(this, ivBalanceLottory, false);

    }

    private void initShopAttr() {

        //????????????????????????????????????
        for (int j = 0; j < MealShopDetailsActivity.mealAttrList.size(); j++) {
            if (null == MealShopDetailsActivity.mealAttrList.get(j).getId()) {
                MealShopDetailsActivity.mealAttrList.remove(j);
                j--;
            }

        }

        HashMap<String, String> tempMap = new HashMap<>();

        LogYiFu.e("shopAttr-color-size",color_size);
        String[] attr = color_size.split(":");
        if (attr.length > 0) {

            for (int i = 0; i < attr.length; i++) {

                int attrTemp = Integer.parseInt(attr[i]);

                for (int j = 0; j < MealShopDetailsActivity.mealAttrList.size(); j++) {
                    if (attrTemp == MealShopDetailsActivity.mealAttrList.get(j).getId()) { //??????????????????;
                        LogYiFu.e("Testmeal", attr[i]);

                        String tempName = MealShopDetailsActivity.mealAttrList.get(j).getAttr_name();
                        int pid = MealShopDetailsActivity.mealAttrList.get(j).getParent_id();
                        //???????????????
                        for (int x = 0; x < MealShopDetailsActivity.mealAttrList.size(); x++) {
                            if (pid == MealShopDetailsActivity.mealAttrList.get(x).getId()) {
                                String attrName = MealShopDetailsActivity.mealAttrList.get(x).getAttr_name();
                                tempMap.put(tempName, attrName);
                            }
                        }
                    }
                }
            }
        }

        Iterator iter = tempMap.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            String tempStr = val+":"+key +" ";
            shopAttr += tempStr;
        }

        LogYiFu.e("shopAttr",shopAttr);

    }

    @Override
    protected void onResume() {
        super.onResume();
        isClick = false;
        HomeWatcherReceiver.registerHomeKeyReceiver(this);
        SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1053");
        TongJiUtils.TongJi(this, 11 + "");
        LogYiFu.e("TongJiNew", 11 + "");//????????????????????????
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        HomeWatcherReceiver.unregisterHomeKeyReceiver(this);
        TongJiUtils.TongJi(this, 111 + "");
        LogYiFu.e("TongJiNew", 111 + "");//????????????????????????

    }

    private void initData(final int requestCode) {
        rlData.setVisibility(View.GONE);
        LogYiFu.i("TAG", "?????????");
        new SAsyncTask<Void, Void, HashMap<String, String>>(this, R.string.wait) {// ????????????

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
                if (requestCode == 1002) {// ????????????
                    NewMealSubmitOrderActivity.this.addResult = result;
                    // ????????????
                    setDeliverAddress(result, null);
                    return;
                } else {
                    initView(result);
                }

            }

        }.execute();

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
        tv_name = (TextView) findViewById(R.id.tv_name);// ?????????
        tv_phone = (TextView) findViewById(R.id.tv_phone);// ???????????????
        tv_receiver_addr = (TextView) findViewById(R.id.tv_receiver_addr);// ????????????
        lin_receiver_addr = (LinearLayout) findViewById(R.id.lin_receiver_addr);
        lin_receiver_addr.setOnClickListener(this);
        lin_set_addr = (LinearLayout) findViewById(R.id.lin_set_addr);
        lin_set_addr.setOnClickListener(this);
        this.addResult = result;
        // dAddress = result.get(0);
        if (mHuoDongFlag) {
            btn_pay.setOnClickListener(this);
        }

        tv_settle_account = (TextView) findViewById(R.id.tv_settle_account);// ???????????????
        total_account = (TextView) findViewById(R.id.total_account);// ???????????????????????????

        rel_name_phone = (RelativeLayout) findViewById(R.id.rel_name_phone);

        tv_discount_coupon_count = (TextView) findViewById(R.id.tv_discount_coupon_count);
        tv_discount_coupon = (TextView) findViewById(R.id.tv_discount_coupon);
        tv_integral_notice = (TextView) findViewById(R.id.tv_integral_notice);
        mTvItegrals = (TextView) findViewById(R.id.tv_integrals);
        mTvBottomItegrals = (TextView) findViewById(R.id.tv_bottom_itegrals);
        tv_integral_notice.setText("????????????:" + 0 + "   ???????????" + 0.0);
        mIntegral.setText("-??" + 0.0);
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
        // ????????????
        setDeliverAddress(result, null);
        initOther();
        if (mHuoDongFlag) {// ?????????????????????????????????????????????????????????...?????????
            findViewById(R.id.rl_integral).setVisibility(View.GONE);// ??????
            findViewById(R.id.rl_coucahers).setVisibility(View.GONE);// ?????????
            findViewById(R.id.rl_money_new).setVisibility(View.GONE);// ????????????
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
                submit_tv_buy.setText("?????????????????????");
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
            dapeigou();// ?????????????????????????????????
            // queryMyMomeny();
            getOrderMoney();
            submit_rl_money.setVisibility(View.VISIBLE);
        }
    }

    private void dapeigou() {
        if (mYouhuiMoneyCount > 0) {// ???????????????
            String matchDiscount = SharedPreferencesUtil.getStringData(context, Pref.DPZHEKOU, "0.95");
            if ("0".equals(matchDiscount)) {
                matchDiscount = "0.95";
            }
            rel_havedapei.setVisibility(View.VISIBLE);
            youhui_jine.setText("?????????" + new DecimalFormat("#0.#").format(Double.parseDouble(matchDiscount) * 10)
                    + "??????????????????????????" + mYouhuiMoneyCount + "???");
            rl_dapeigou.setVisibility(View.VISIBLE);
            submit_dapeigou.setText("-??" + mYouhuiMoneyCount);
            jiesuan_jiesheng.setVisibility(View.VISIBLE);
            jiesuan_jiesheng.setText("??????????????" + mYouhuiMoneyCount);

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
     * ??????????????????
     */
    private void getMyIntegral() {

        new SAsyncTask<Void, Void, Integer>((FragmentActivity) context, 0) {

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected Integer doInBackground(FragmentActivity context, Void... params) throws Exception {
                mSystemTimeMap = ComModel2.getSystemTime(NewMealSubmitOrderActivity.this);
                return ComModel2.getMyIntegral(context);
            }

            @Override
            protected void onPostExecute(final FragmentActivity context, final Integer result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null) {
                    btn_pay.setOnClickListener(NewMealSubmitOrderActivity.this);
                    getJiFen(result);
                }
                // getProxCoup(inteDiscount + money);// ???????????????
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
        str_jinbi_endDate = SharedPreferencesUtil.getStringData(NewMealSubmitOrderActivity.this, Pref.JINBI_END_DATE, "-1");
        if (null == str_jinbi_endDate || "".equals(str_jinbi_endDate)) {
            str_jinbi_endDate = "-1";
        }
        long jinbi_endDate = Long.parseLong(str_jinbi_endDate);
        myIntegCount = myJifen;
        if (mSystemTimeMap != null && mSystemTimeMap.size() > 0 && (Long) mSystemTimeMap.get("now") < jinbi_endDate) {
            mIsGold = true;
        }
        if (mIsGold) {
            mTvItegrals.setText("??????");
            mTvBottomItegrals.setText("??????");
        } else {
            mTvBottomItegrals.setText("??????");
            mTvItegrals.setText("??????");
        }
        if (myIntegCount < 1) {
            if (mIsGold && myIntegCount > 0) {// ????????????????????????(?????????????????????,??????????????????????????????)
                inteDiscount = 0.01 * myIntegCount;
                if (inteDiscount > sum) {
                    inteDiscount = sum - 0.01;
                    integral = (int) (inteDiscount * 100);
                } else {
                    integral = myIntegCount;
                }
                tv_integral_notice.setText(
                        "????????????:" + integral + "   ???????????" + new DecimalFormat("#0.00").format(inteDiscount));
                mRlIntegral.setVisibility(View.VISIBLE);
                mIntegral.setText("-??" + new DecimalFormat("#0.00").format(inteDiscount) + "");
                sum -= inteDiscount;

            } else {// ???????????????????????????500??????
                mTgbs.setChecked(false);
                mTgbs.setEnabled(false);
                mRlIntegral.setVisibility(View.GONE);
            }

        } else {

            // // ????????????
            // if (isDapei) {
            // mDiscountInte = core;
            // }

            if (mDiscountInte >= myIntegCount) {// ???????????????????????? ??????????????????
                // ???????????????????????????
                if (mIsGold) {// ????????????????????????
                    if (myIntegCount > 600) {// ??????????????????600?????????100??????==1??????
                        inteDiscount = 0.01 * 600;
                        if (inteDiscount > sum) {
                            inteDiscount = sum;
                            // myIntegCount=(int)
                            // (inteDiscount*100);111
                            integral = (int) (inteDiscount * 100);
                            tv_integral_notice.setText("????????????:" + integral + "   ???????????"
                                    + new DecimalFormat("#0.00").format(inteDiscount));
                        } else {
                            tv_integral_notice.setText("????????????:" + 600 + "   ???????????"
                                    + new DecimalFormat("#0.00").format(inteDiscount));
                            integral = 600;
                        }

                        mRlIntegral.setVisibility(View.VISIBLE);
                        mIntegral.setText("-??" + new DecimalFormat("#0.00").format(inteDiscount) + "");
                        sum -= inteDiscount;
                    } else {
                        inteDiscount = 0.01 * myIntegCount;
                        if (inteDiscount > sum) {
                            inteDiscount = sum;
                            integral = (int) (inteDiscount * 100);
                        } else {
                            integral = myIntegCount;
                        }
                        tv_integral_notice.setText("????????????:" + integral + "   ???????????"
                                + new DecimalFormat("#0.00").format(inteDiscount));
                        mRlIntegral.setVisibility(View.VISIBLE);
                        mIntegral.setText("-??" + new DecimalFormat("#0.00").format(inteDiscount) + "");
                        sum -= inteDiscount;
                    }
                } else {// ????????????????????????

                    inteDiscount = 0.01 * myIntegCount;

                    tv_integral_notice.setText("????????????:" + myIntegCount + "   ???????????"
                            + new DecimalFormat("#0.00").format(inteDiscount));
                    mRlIntegral.setVisibility(View.VISIBLE);
                    mIntegral.setText("-??" + new DecimalFormat("#0.00").format(inteDiscount) + "");
                    integral = myIntegCount;
                    sum -= inteDiscount;

                }

            } else {// ????????????????????????????????????????????????????????????????????????
                if (mIsGold) {// ??????????????????????????????????????????????????????
                    if (myIntegCount > 600) {// ??????????????????600?????????100??????==1??????
                        inteDiscount = 0.01 * 600;
                        if (inteDiscount > sum) {
                            inteDiscount = sum;
                            integral = (int) (inteDiscount * 100);
                            tv_integral_notice.setText("????????????:" + integral + "   ???????????"
                                    + new DecimalFormat("#0.00").format(inteDiscount));
                        } else {
                            integral = 600;
                            tv_integral_notice.setText("????????????:" + 600 + "   ???????????"
                                    + new DecimalFormat("#0.00").format(inteDiscount));
                        }
                        mRlIntegral.setVisibility(View.VISIBLE);
                        mIntegral.setText("-??" + new DecimalFormat("#0.00").format(inteDiscount) + "");
                        sum -= inteDiscount;
                    } else {
                        inteDiscount = 0.01 * myIntegCount;
                        if (inteDiscount > sum) {
                            inteDiscount = sum;
                            integral = (int) (inteDiscount * 100);
                        } else {
                            integral = myIntegCount;
                        }
                        tv_integral_notice.setText("????????????:" + integral + "   ???????????"
                                + new DecimalFormat("#0.00").format(inteDiscount));
                        mRlIntegral.setVisibility(View.VISIBLE);
                        mIntegral.setText("-??" + new DecimalFormat("#0.00").format(inteDiscount) + "");
                        sum -= inteDiscount;
                    }

                } else {// ????????????????????????
                    if (mDiscountInte < 1) {
                        mTgbs.setChecked(false);
                        mTgbs.setEnabled(false);
                        mRlIntegral.setVisibility(View.GONE);
                    } else {
                        inteDiscount = 0.01 * mDiscountInte;
                        tv_integral_notice.setText("????????????:" + mDiscountInte + "   ???????????"
                                + new DecimalFormat("#0.00").format(inteDiscount));
                        mRlIntegral.setVisibility(View.VISIBLE);
                        mIntegral.setText("-??" + new DecimalFormat("#0.00").format(inteDiscount) + "");
                        integral = mDiscountInte;
                        sum -= inteDiscount;
                    }
                }
            }

            // total_account.setText(Html.fromHtml(
            // getString(R.string.total_account, shopNum, new
            // java.text.DecimalFormat("#0.00").format(sum))));
            // double sum2 = sum - mVouchersCount;
            // tv_settle_account.setText("?????????:??" + new
            // java.text.DecimalFormat("#0.00").format(sum2 -
            // mYouhuiMoneyCount));
            setMoney(sum - mYouhuiMoneyCount - mVouchersCount, true);
        }

    }

    private HashMap<String, String> mGoldVoucherMap;
    private HashMap<String, Object> mSystemTimeMap;

    // ?????? ???????????????
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

                if (e != null) {// ????????????
                    ToastUtil.showShortText(context, "????????????");
                } else {
                    // ?????????????????????????????????
                    String end_date = SharedPreferencesUtil.getStringData(NewMealSubmitOrderActivity.this,
                            Pref.JINQUAN_END_DATE, "-1");
                    if (null == end_date || "".equals(end_date)) {
                        end_date = "-1";
                    }
                    String is_open = SharedPreferencesUtil.getStringData(NewMealSubmitOrderActivity.this,
                            Pref.JINQUAN_IS_OPEN, "0");
                    if (null == is_open || "".equals(is_open)) {
                        is_open = "0";
                    }
                    String c_last_time = SharedPreferencesUtil.getStringData(NewMealSubmitOrderActivity.this,
                            Pref.JINQUAN_C_LAST_TIME, "0");
                    if (null == c_last_time || "".equals(c_last_time)) {
                        c_last_time = "0";
                    }
                    String c_price = SharedPreferencesUtil.getStringData(NewMealSubmitOrderActivity.this,
                            Pref.JINQUAN_C_PRICE, "0");
                    if (null == c_price || "".equals(c_price)) {
                        c_price = "0";
                    }
                    String is_use = SharedPreferencesUtil.getStringData(NewMealSubmitOrderActivity.this,
                            Pref.JINQUAN_IS_USE, "0");
                    if (null == is_use || "".equals(is_use)) {
                        is_use = "0";
                    }
                    String c_id = SharedPreferencesUtil.getStringData(NewMealSubmitOrderActivity.this, Pref.JINQUAN_C_ID,
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
                                .parseInt("" + mGoldVoucherMap.get("c_id")) == (Integer) mapCoupon.get("id")) {// ??????????????????????????????
                            tv_discount_coupon.setText("??????");
                            tv_discount_coupon_count.setText("    ?????????1?????????");
                            tv_gold_voucaher.setText("??????");
                            useFlag = true;
                        } else {
                            tv_discount_coupon_count.setText("?????????1????????????");
                        }
                        mCouponMoney.setVisibility(View.VISIBLE);
                        mCouponMoney.setText("-??" + mapCoupon.get("c_price"));
                        discount = ((BigDecimal) mapCoupon.get("c_price")).doubleValue();

                        double discount = ((BigDecimal) mapCoupon.get("c_price")).doubleValue();
                        sum = sum - discount;

                        // amount = sum;
                        // afterCoupon = sum;

                        total_account.setText(Html.fromHtml(getString(R.string.total_account, shopNum,
                                new DecimalFormat("#0.00").format(sum))));
                        mCoupon.setVisibility(View.VISIBLE);
                        mCoupon.setText("-??" + new DecimalFormat("#0.00").format(discount));
                        // tv_settle_account.setText("?????????:??" + new
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
                                tv_discount_coupon.setText("??????");
                                tv_discount_coupon_count.setText("     ?????????1?????????");
                                tv_gold_voucaher.setText("??????");
                                mCouponMoney.setVisibility(View.VISIBLE);
                                mCouponMoney.setText("-??" + mGoldVoucherMap.get("c_price"));
                                discount = Double.parseDouble(mGoldVoucherMap.get("c_price"));

                                double discount = Double.parseDouble(mGoldVoucherMap.get("c_price"));
                                sum = sum - discount;

                                // amount = sum;
                                // afterCoupon = sum;
                                total_account.setText(Html.fromHtml(getString(R.string.total_account, shopNum,
                                        new DecimalFormat("#0.00").format(sum))));
                                mCoupon.setVisibility(View.VISIBLE);
                                mCoupon.setText("-??" + new DecimalFormat("#0.00").format(discount));
                                useFlag = true;
                                // tv_settle_account.setText("?????????:??" + new
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
                                tv_discount_coupon_count.setText("?????????");
                                mCouponMoney.setVisibility(View.GONE);
                                total_account.setText(Html.fromHtml(getString(R.string.total_account, shopNum,
                                        new DecimalFormat("#0.00").format(sum))));

                                // tv_settle_account.setText("?????????:??" + new
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
                            tv_discount_coupon_count.setText("?????????");
                            mCouponMoney.setVisibility(View.GONE);
                            total_account.setText(Html.fromHtml(getString(R.string.total_account, shopNum,
                                    new DecimalFormat("#0.00").format(sum))));
                            LogYiFu.e("test", "mVouchersCount" + mVouchersCount);
                            // tv_settle_account.setText("?????????:??" + new
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
     *

     *            :?????????????????????
     * @param container
     *            :?????????????????????
     */
    private void addView(HashMap<Integer, List<ShopCart>> applyList, LinearLayout container,
                         HashMap<String, Object> mapCoupon) {
        container.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        Iterator<Entry<Integer, List<ShopCart>>> iterator = applyList.entrySet().iterator();
        int position = 0;

        while (iterator.hasNext()) {
            Entry<Integer, List<ShopCart>> entry = iterator.next();
            final List<ShopCart> shopCarts = (List<ShopCart>) entry.getValue();

            View view = inflater.inflate(R.layout.goods_item, null);
            view.setBackgroundColor(Color.WHITE);


            // ??????????????? ??????????????????
            position++;
            View line = view.findViewById(R.id.v_bottom_line);
            if (position == 1) {
                line.setVisibility(View.GONE);
            }

            LinearLayout good_container = (LinearLayout) view.findViewById(R.id.good_container);
            EditText edit_message = (EditText) view.findViewById(R.id.edit_message);
            mapEdit.put(entry.getKey(), edit_message);
            // ??????????????????????????????????????????????????????
            // TextView tv_total_account = (TextView)
            // view.findViewById(R.id.total_account);
            double sumAccount = 0.0;

            // double original_price = 0;
            // ??????????????????????????????
            LogYiFu.e("TAG", "????????????????????????---" + shopCarts.size());
            int userful = 0;//
            double specilaPriceCount = 0;//???????????????
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
                // ???????????? ????????????
                TextView tvProPrice = (TextView) v.findViewById(R.id.tv_pro_price);
                TextView tv_item_supply = (TextView) v.findViewById(R.id.tv_item_supply);

                // TextView tv_sumname = (TextView)
                // v.findViewById(R.id.tv_sumname);ho
                // SetImageLoader.initImageLoader(this, img_pro_pic,
                // good.getShop_code().substring(1, 4) + "/" +
                // good.getShop_code() + "/" + good.getDef_pic(), "");


                PicassoUtils.initImage(this,
                        good.getShop_code().substring(1, 4) + "/" + good.getShop_code() + "/" + shop_pic,
                        img_pro_pic);


                LogYiFu.e("TAG", "==good=" + good.getColor() + ",size=" + good.getSize());
                tv_sum.setText("x" + good.getShop_num());
                tv_pro_name.setText(good.getShop_name());
//                tv_pro_descri.setText("??????-" + good.getColor() + "    ??????:" + good.getSize());
                tv_pro_descri.setText(shopAttr);



                tv_discout.setText("??" + new DecimalFormat("#0.0").format(good.getShop_se_price()));


                tv_zero_kickback.setText("???" + new DecimalFormat("#0.00").format(good.getShop_se_price()) + "???=0??????");
                tv_zero_kickback.setVisibility(View.GONE); //???1???????????????0??????


                // ???????????????????????????
                tvProPrice.setText("??" + new DecimalFormat("#0.00").format(good.getShop_price()));
                tvProPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                String supp_label = good.getSupp_label();
                if (!TextUtils.isEmpty(supp_label)) {
//                    tv_item_supply.setText(supp_label + "?????????");
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
                LogYiFu.e("TAG", "???????????????????????????");
                mListShopCart.add(good);
                v.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        Intent intent = new Intent(context, MealShopDetailsActivity.class);
                        intent.putExtra("code", good.getShop_code());
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                    }
                });
            }

            tv_special_price.setVisibility(View.GONE);

            if (userful <= 0) {

            } else {

                double sum2 = sum - mVouchersCount;
                tv_settle_account
                        .setText("?????????:??" + new DecimalFormat("#0.00").format(sum2 - mYouhuiMoneyCount));
                int m = (int) (mVouchersCount);
                mVoucachers.setText("-??" + new DecimalFormat("#0.00").format(m) + "");
            }


            container.addView(view);
        }

        total_account.setText(Html.fromHtml(
                getString(R.string.total_account, shopNum, new DecimalFormat("#0.00").format(sum))));
        mTotal = sum;
        double sum2 = sum - mVouchersCount;
        if (mHuoDongFlag && groupFlag != 0) {
//            tv_settle_account.setText("?????????:??"+new java.text.DecimalFormat("#0.00").format(mGroupPrice)+"??????");
            tv_settle_account.setText("?????????????????0.00???");
            submit_tv_group_price.setText("-??" + new DecimalFormat("#0.00").format(mTotal - mGroupPrice));
            submit_tv_need_pay_price.setText("??" + new DecimalFormat("#0.00").format(mGroupPrice));
        } else {
            tv_settle_account.setText("?????????:??" + new DecimalFormat("#0.00").format(sum2 - mYouhuiMoneyCount));
        }
        mSubmitTotal.setText("??" + new DecimalFormat("#0.00").format(mTotal));
        mPockage.setText("-??" + new DecimalFormat("#0.00").format(0.0));
    }

    // ????????????
    private void setDeliverAddress(HashMap<String, String> mapRet, DeliveryAddress dAddress) {
        if (null == mapRet && dAddress != null) {
            tv_name.setText("????????????" + dAddress.getConsignee());
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

            tv_receiver_addr.setText("???????????????" + province + city + county + street + dAddress.getAddress());// ????????????
            lin_receiver_addr.setVisibility(View.VISIBLE);
            rel_name_phone.setVisibility(View.VISIBLE);
            lin_set_addr.setVisibility(View.GONE);
        } else if (null != mapRet && dAddress == null) {
            lin_receiver_addr.setVisibility(View.VISIBLE);
            rel_name_phone.setVisibility(View.VISIBLE);
            lin_set_addr.setVisibility(View.GONE);
            tv_name.setText("????????????" + mapRet.get("consignee"));
            tv_phone.setText(mapRet.get("phone"));
            tv_receiver_addr.setText("???????????????" + mapRet.get("address"));// ????????????
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
     * ????????????
     */
    private void submitOrder(final View v) {
//		Log.e("hello", "000000");
        Iterator<Entry<Integer, EditText>> iterator = mapEdit.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Integer, EditText> entry = iterator.next();
            String s = entry.getValue().getText().toString().trim();
//            if (!TextUtils.isEmpty(s)) {
//                if (StringUtils.containsEmoji(s)) {
//                    Toast.makeText(context, "????????????????????????", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (RegisterFragment.getWordCount(s) < 5) {
//                    Toast.makeText(context, "????????????????????????????????????", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (RegisterFragment.getWordCount(s) > 500) {
//                    Toast.makeText(context, "???????????????????????????????????????", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
            mapMsg.put(entry.getKey(), s);
        }


        final StringBuffer sb = new StringBuffer();

        new SAsyncTask<Void, Void, HashMap<String, Object>>(this, v, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                LogYiFu.e("TAG", "mapListGood=" + mapListGood.toString());

                int myInte = 0;
                if (isAddIntegral) {
                    myInte = integral;
                } else {
                    myInte = 0;
                }
                int is_be;// 0??????????????????????????????1???????????????????????????
                if (mMoneyTgb.isChecked() && mUseMoney > 0) {
                    is_be = 1;
                } else {
                    is_be = 0;
                }


                String sbResult = "";
                sbResult = shop_num + "^" + buy_shop_code + "^" + listGoods.get(0).getStock_type_id();

                if (null == mapCoupon || !mapCoupon.containsKey("id") || null == mapCoupon.get("id")) {

                    return ComModel2.submitNewMealtOrders(sbResult, context, mapMsg, listGoods, myInte, addressId, 0,
                            mMapTen, mMapFive, mMapTwo, mMapOne, is_be);
                } else {


                    return ComModel2.submitNewMealtOrders(sbResult, context, mapMsg, listGoods, myInte, addressId,
                            (Integer) mapCoupon.get("id"), mMapTen, mMapFive, mMapTwo, mMapOne, is_be);
                }
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
                    SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);

                    // ??????????????????????????????
                    Intent intent = new Intent(NewMealSubmitOrderActivity.this, PaymentActivity.class);
                    LogYiFu.e("TAG", "??????????????????");
                    intent.putExtra("is_g_code", true);
                    intent.putExtra("listGoods", (Serializable) listGoods);
                    intent.putExtra("result", (Serializable) result);
                    intent.putExtra("order_code", orderNo);
                    // intent.putExtra("mIsTwoGroup", mIsTwoGroup);
                    ShopCartDao dao = new ShopCartDao(context);
                    for (int i = 0; i < listGoods.size(); i++) {
                        dao.delete("" + listGoods.get(i).getStock_type_id());
                    }

                    Long shengyuTime = (long) (1 * 1000 * 60 * 60 * 24); // ????????????

                    intent.putExtra("shengyuTime", shengyuTime);

                    intent.putExtra("totlaAccount", (Double) result.get("price"));
                    if (url > 1) {
                        intent.putExtra("isMulti", true);
                    }
                    /*
                     * if (listGoods.size() == 1) {
					 * getPicPath(listGoods.get(0).getShop_code(), null); }
					 */


                    //????????????---????????????????????????????????????2???????????????
                    //?????????????????????
                    String fuchuanyindaodialog = SharedPreferencesUtil.getStringData(context, "FUCHUANYINDAODIALOG", "");
                    SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
                    // ????????????
                    String datee = sdff.format(new Date());
                    //?????????????????????------?????????????????????
//                    if (!datee.equals(fuchuanyindaodialog) && !SharedPreferencesUtil.getBooleanData(context, Pref.ISMADMONDAY, false)) {
                    YJApplication.startFukuanYndao();//?????????????????????
//                    }


                    NewMealSubmitOrderActivity.this.setResult(1);
                    NewMealSubmitOrderActivity.this.startActivityForResult(intent, 1003);
                    NewMealSubmitOrderActivity.this.finish();
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
                YunYingTongJi.yunYingTongJi(NewMealSubmitOrderActivity.this, 111);
                // mList
                // payMoney(v);
                if (addResult == null && dAddress == null) {
                    ToastUtil.showShortText(this, "?????????????????????");
                    return;
                }

                if (!isDapei) {
                    mShareVoucachers.setText(mVouchersCount + "");
                }
                if (!isClick) {
                    if (mHuoDongFlag) {

                    } else {//????????????????????????
                        if (mListShopCart.size() > 0) {
                            Random random = new Random();
                            int nextInt = random.nextInt(mListShopCart.size());
                            mShop_code = mListShopCart.get(nextInt).getShop_code();
                            mKickback = mListShopCart.get(nextInt).getKickback();

                            mDef_pic = mListShopCart.get(nextInt).getDef_pic();
                            if (isDapei) {
                                mShareVoucachers.setText(new DecimalFormat("#0")
                                        .format(mKickback * mListShopCart.get(nextInt).getShop_num()) + "");
                            }
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
            case R.id.img_back:// ???????????????
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
    // ??????????????????
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

                if (null == e) {
                    if (result.get("status").equals("1")) {
                        // ?????????900 X 900 ?????????

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
                Bitmap bmQr = QRCreateUtil.createQrImage(link, 160, 160);// ?????????????????????
                String[] strs = picPath.split(",");
                String pic;
                if (strs[2] != null) {
                    pic = shop_code.substring(1, 4) + "/" + shop_code + "/" + strs[2];
                } else {
                    pic = shop_code.substring(1, 4) + "/" + shop_code + "/" + mDef_pic;
                }

                Bitmap bmBg = downloadPic(pic);

                Bitmap bmNew = QRCreateUtil.drawNewBitmap1(context, bmBg, bmQr, price, "");

                QRCreateUtil.saveBitmap(bmNew, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// ?????????????????????
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
                    ToastUtil.showShortText(context, "????????????????????????~");
                    time.start();
                }
            }

        }.execute();
    }

    private Bitmap downloadPic(String picPath) {
        try {
            URL url = new URL(YUrl.imgurl + picPath);
            // ????????????
            URLConnection con = url.openConnection();
            // ?????????????????????
            int contentLength = con.getContentLength();
            System.out.println("?????? :" + contentLength);
            // ?????????
            InputStream is = con.getInputStream();
            // 1K???????????????
            byte[] bs = new byte[8192];
            // ????????????????????????
            int len;
            BitmapDrawable bmpDraw = new BitmapDrawable(is);

            // ???????????????????????????
            is.close();
            return bmpDraw.getBitmap();
        } catch (Exception e) {
            LogYiFu.e("TAG", "????????????");

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

    // ?????????
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// ????????????????????????,????????????????????????

        }

        @Override
        public void onFinish() {// ?????????????????????
            try {
                File file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");
                share(file, null);
                // onceShare(intent, "??????");

                rel_show_share.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {// ??????????????????
            try {
                // btn.setEnabled(false);
                // btn.setBackgroundResource(R.color.time_count);
                // text.setText(millisUntilFinished / 1000 + "???????????????????????????");
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
//        tv_content.setText("??????????????????????????????????????????");
        View view = View.inflate(context, R.layout.dialog_order_back, null);
        TextView btn_cancel = (TextView) view.findViewById(R.id.btn_cancel);

        ((TextView) view.findViewById(R.id.balance_dialog_tv1)).setText("??????????????????????????????????????????");


        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // ???????????????????????????
                dialog.dismiss();
            }
        });
        TextView btn_ok = (TextView) view.findViewById(R.id.btn_ok);
        btn_ok.setText("?????????");
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

        // ?????????????????????dialog
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1001) { // ????????????
            if (null != intent) {
                dAddress = (DeliveryAddress) intent.getSerializableExtra("item");
                addressId = dAddress.getId();
                setDeliverAddress(null, dAddress);
            } else {
                initData(1002);
            }
        } else if (requestCode == 1002) { // ????????????
            initData(requestCode);
        } else if (requestCode == 1003 && resultCode == 1) {
            NewMealSubmitOrderActivity.this.setResult(1); // 1????????????????????????
            LogYiFu.e("TAG", "????????????????????????????????????");
            finish();
        } else if (requestCode == 1005 && resultCode == 2001) {
            sum = sum + discount;
            boolean flag = intent.getBooleanExtra("isJinQuan", false);
            if (flag) {
                tv_discount_coupon.setText("??????");
                tv_discount_coupon_count.setText("    ?????????1?????????");
                tv_gold_voucaher.setText("??????");
            } else {
                tv_discount_coupon.setText("?????????");
                tv_discount_coupon_count.setText("?????????1????????????");
                tv_gold_voucaher.setText("?????????");
            }
            mCouponMoney.setVisibility(View.VISIBLE);
            mRlCoupon.setVisibility(View.VISIBLE);
            // sum = amount;
            mapCoupon = (HashMap<String, Object>) intent.getSerializableExtra("selectUseful");
            tv_discount_coupon_count.setVisibility(View.VISIBLE);
            // tv_discount_coupon_count.setText("?????????1????????????");
            mCouponMoney.setText("-??" + mapCoupon.get("c_price"));
            // if(mapCoupon.get("c_price")>0)
            mCoupon.setText("-??" + mapCoupon.get("c_price"));
            discount = (Double) mapCoupon.get("c_price");
            sum = sum - discount;
            total_account.setText(Html.fromHtml(
                    getString(R.string.total_account, shopNum, new DecimalFormat("#0.00").format(sum))));

            // tv_settle_account.setText(
            // "?????????:??" + new java.text.DecimalFormat("#0.00").format(sum -
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
            case R.id.tgb:// ????????????
                isAddIntegral = isChecked;
                if (isChecked) {
                    double inteDiscount = 0;
                    if (mIsGold) {// ????????????????????????
                        inteDiscount = 0.01 * integral;
                        tv_integral_notice.setText(
                                "????????????:" + integral + "   ???????????" + new DecimalFormat("#0.00").format(inteDiscount));
                    } else {
                        inteDiscount = 0.01 * integral;
                        tv_integral_notice.setText(
                                "????????????:" + integral + "   ???????????" + new DecimalFormat("#0.00").format(inteDiscount));
                    }

                    mRlIntegral.setVisibility(View.VISIBLE);
                    mIntegral.setText("-??" + new DecimalFormat("#0.00").format(inteDiscount) + "");
                    sum -= inteDiscount;

                    total_account.setText(Html.fromHtml(
                            getString(R.string.total_account, shopNum, new DecimalFormat("#0.00").format(sum))));
                    double sum2 = sum - mVouchersCount;
                    // tv_settle_account
                    // .setText("?????????:??" + new
                    // java.text.DecimalFormat("#0.00").format(sum2 -
                    // mYouhuiMoneyCount));

                    // TODO:55555555555
                    setMoney(sum2 - mYouhuiMoneyCount, true);
                } else {
                    double inteDiscount = 0;
                    if (mIsGold) {// ????????????????????????
                        inteDiscount = 0.01 * integral;
                        tv_integral_notice
                                .setText("????????????:" + 0 + "   ???????????" + new DecimalFormat("#0.00").format(0.0));
                    } else {
                        inteDiscount = 0.01 * integral;
                        tv_integral_notice
                                .setText("????????????:" + 0 + "   ???????????" + new DecimalFormat("#0.00").format(0.0));
                    }
                    mRlIntegral.setVisibility(View.GONE);
                    mIntegral.setText(0.0 + "");
                    sum += inteDiscount;

                    total_account.setText(Html.fromHtml(
                            getString(R.string.total_account, shopNum, new DecimalFormat("#0.00").format(sum))));
                    double sum2 = sum - mVouchersCount;
                    // tv_settle_account
                    // .setText("?????????:??" + new
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
                    tv_discount_coupon.setText("??????");
                    tv_discount_coupon_count.setText("     ?????????1?????????");
                    tv_gold_voucaher.setText("??????");
                    mCouponMoney.setVisibility(View.VISIBLE);
                    mCouponMoney.setText("-??" + mGoldVoucherMap.get("c_price"));
                    discount = Double.parseDouble(mGoldVoucherMap.get("c_price"));

                    double discount = Double.parseDouble(mGoldVoucherMap.get("c_price"));
                    sum = sum - discount;

                    // amount = sum;
                    // afterCoupon = sum;

                    total_account.setText(Html.fromHtml(
                            getString(R.string.total_account, shopNum, new DecimalFormat("#0.00").format(sum))));
                    mCoupon.setVisibility(View.VISIBLE);
                    mCoupon.setText("-??" + new DecimalFormat("#0.00").format(discount));
                    // tv_settle_account.setText("?????????:??"
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
                    tv_discount_coupon.setText("?????????");
                    tv_discount_coupon_count.setText("?????????");
                    tv_gold_voucaher.setText("?????????");
                    mCouponMoney.setVisibility(View.GONE);
                    sum = sum + discount;
                    discount = 0;
                    total_account.setText(Html.fromHtml(
                            getString(R.string.total_account, shopNum, new DecimalFormat("#0.00").format(sum))));
                    mCoupon.setVisibility(View.GONE);
                    // tv_settle_account.setText("?????????:??"
                    // + new java.text.DecimalFormat("#0.00").format(sum -
                    // mYouhuiMoneyCount - mVouchersCount));
                    // TODO:888888888888888888
                    setMoney(sum - mYouhuiMoneyCount - mVouchersCount, true);
                }
                break;
            case R.id.tgb_money:// ??????????????????
                // if (isChecked) {// ??????
//                submit_money.setText("-??" + new DecimalFormat("#0.00").format(mUseMoney));
                // sum -= mUseMoney;
                tv_settle_account.setText("?????????:??" + new DecimalFormat("#0.00")
                        .format(sum - mYouhuiMoneyCount - mVouchersCount - mUseMoney));

                tv_money_notice_new.setText("?????????" + new DecimalFormat("#0.00").format(mUseMoney) + "(????????????10%)");
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // ?????????????????????
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
     * ?????????????????????????????????????????????
     */
    private void getOrderMoney() {
        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) NewMealSubmitOrderActivity.this,
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
     * ????????????????????????????????? TODO:
     */
    private void queryMyMomeny() {

        new SAsyncTask<Void, Void, String[]>(NewMealSubmitOrderActivity.this, R.string.wait) {

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
                        String balance = result[0];// ????????????
                        mMyMoney = Double.parseDouble(balance);
                        tv_settle_account.setText("?????????:??" + new DecimalFormat("#0.00")
                                .format(sum - mYouhuiMoneyCount - mVouchersCount));
                        // getMyIntegral(mMyMoney);
                        double price;
                        if (isDapei) {
                            price = mPriceCount * 0.9;
                        } else {
                            price = mPriceCount;
                        }
                        getProxCoup(price);// ???????????????

                    }

                }
            }

        }.execute((Void[]) null);

    }


    // ?????????
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
                            tv_time.setText("" + days + "???" + hours + ":" + minutes + ":" + seconds);
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
            Toast.makeText(this, "??????????????????????????????~~", Toast.LENGTH_SHORT).show();
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
        tv.setText("??????????????????" + mVouchersCount + "??????????????????");
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

        // ?????????????????????dialog
        dialogShare.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        dialogShare.setCancelable(false);
        dialogShare.show();

    }

    public void setMoney(double sum, boolean flag) {
        mLimitMoney = (sum) * maxRate;
        double mMoney = 0;// ???????????????
        if (mLimitMoney > mMyMoney) {// ????????????????????????
            mMoney = mMyMoney;
        } else {
            mMoney = mLimitMoney;
        }
        if (mMoney > maxMoney) {// ????????????????????????
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
                mMoneyTgb.setEnabled(false);// ??????????????????
            }
        }

        // sum -= mMoney;
        if (mMoneyTgb.isChecked()) {
            String strPrice = new DecimalFormat("#0.00").format(mMoney);
            Double price = Double.parseDouble(strPrice);// ????????????
            tv_money_notice_new.setText("?????????" + new DecimalFormat("#0.00").format(mMoney) + "(????????????10%)");
            tv_settle_account.setText("?????????:??" + new DecimalFormat("#0.00").format(sum - price));

//            submit_money.setText("-??" + new DecimalFormat("#0.00").format(mMoney));
        } else {
            tv_settle_account.setText("?????????:??" + new DecimalFormat("#0.00").format(sum));
            tv_money_notice_new.setText("?????????" + new DecimalFormat("#0.00").format(0) + "(????????????10%)");
//            submit_money.setText("-??" + new DecimalFormat("#0.00").format(0));

        }

        //1???????????????
        getdiKOU(sum, submit_money, tv_settle_account);


        if (!mTgbs.isChecked()) {
            tv_integral_notice.setText("????????????:" + 0 + "   ???????????" + 0.0);
        }
    }

    private void getdiKOU(final double sum, final TextView submit_money, final TextView tv_settle_account) {


        new SAsyncTask<Void, Void, String>(NewMealSubmitOrderActivity.this, R.string.wait) {

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

                    if (Double.parseDouble(result) <= sum) {//????????????????????????????????????????????????
                        submit_money.setText("-??" + result);
                        tv_settle_account.setText("?????????:??" + new DecimalFormat("#0.00").format(sum - Double.parseDouble(result)));
                    } else { //??????????????????????????????
                        submit_money.setText("-??" + sum);
                        tv_settle_account.setText("?????????:??0.01");

                    }

                    if (Double.parseDouble(result) == 0) {
                        submit_money.setText("??0.0");
                    }

                }

                super.onPostExecute(context, result, e);
            }

        }.execute();


    }


}
