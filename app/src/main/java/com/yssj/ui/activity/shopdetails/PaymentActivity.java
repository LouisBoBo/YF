package com.yssj.ui.activity.shopdetails;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yssj.YConstance;
import com.yssj.YConstance.Config;
import com.yssj.YConstance.Pref;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXPayEntryActivity;
import com.yssj.activity.wxapi.WXPayEntryActivity.OnWxpayFinish;
import com.yssj.alipay.PayResult;
import com.yssj.alipay.PayUtil;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.PayPasswordCustomDialog;
import com.yssj.custom.view.PayPasswordCustomDialog.InputDialogListener;
import com.yssj.entity.CheckPwdInfo;
import com.yssj.entity.Order;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ShopCart;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.activity.OneBuyChouJiangActivity;
import com.yssj.ui.activity.OneBuyGroupsDetailsActivity;
import com.yssj.ui.activity.ShopCartNewNewActivity;
import com.yssj.ui.activity.WithdrawalLimitActivity;
import com.yssj.ui.activity.infos.SetMyPayPassActivity;
import com.yssj.ui.activity.infos.StatusInfoActivity;
import com.yssj.ui.activity.vip.MyVipListActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.PayErrorDialog;
import com.yssj.ui.dialog.WaitDialog;
import com.yssj.ui.fragment.orderinfo.OrderDetailsActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.YunYingTongJi;
import com.yssj.wxpay.WxPayUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 收银台支付
 *
 * @author Administrator
 */
public class PaymentActivity extends BasicActivity implements OnClickListener, OnWxpayFinish {

    private String shop_code;

    private LinearLayout llWalletPay;
    private LinearLayout llQuickPay;
    private LinearLayout llWXinPay;
    private LinearLayout llAliPay;
    private LinearLayout llUnionPay;
    private LinearLayout llMydeayPay;
    private RelativeLayout root;
    private ImageView ivWalletPay, ivQuickPay, ivWXinPay, ivAliPay, ivUnionPay, ivMydearPay;
    private TextView tvTotalAccount, tshengyuTime;
    private LinearLayout btnPay;
    private final int SDK_PAY_FLAG = 1;
    private ImageView imgCancle;
    private HashMap<Integer, Boolean> payTypeMap;
    private ArrayList<ImageView> imgSelectList;
    private IWXAPI msgApi;
    private String orderNo;
    private List<ShopCart> listGoods;
    private HashMap<String, Object> resultMap;
    private double totalAccount;
    private PayPasswordCustomDialog customDialog;
    private InputDialogListener inputDialogListener;
    private boolean isMulti = true;
    private List<Order> listOrder;

    private boolean isMeal;//是否是特卖一元拼团
    private boolean isOneBuy;//是否是一元购
    private boolean isOneBuyOnce;//是否是一元购再抽一次下单

    private Long shengyuTime;
    private boolean isGcode;
    private RelativeLayout rel_pay_success, rel_nomal;
    private TextView tv_price;
    // public static PaymentActivity instance;

    public static PaymentActivity instance;
    private TimeCount timeCount;
    private long nowTime;
    private Bundle bundle;

    private String zhoujiangShowOnePrice;

    private LinearLayout mLlFailture;// 支付失败的显示
    private View mViewLine, mView1, mView2, mView3;
    private TextView mTvMoney;// 余额二字
    private TextView mTvUsefulMoney;// 可使用的余额
    private RelativeLayout mRlNeedPay;// 还需支付
    private TextView mTvNeedMoney;
    private TextView tv_three_yuan;
    private RelativeLayout ll_zhifujine;// 余额支付的显示
    private TextView mTvPayTimes;// 支付倒计时
    private double mMyMoney = 0;// 我的金钱
    private double mUsefulMoney;// 可使用的余额
    private boolean mIsQulification;// 是否具有余额翻倍的资格
    private int mTwofoldness;// 余额翻倍的倍数
    private long mEndDate;// 余额翻倍的结束时间值
    private int mIsOpen;// 0,没有开启;1，已经开启
    private long mSysTime = -1;// 系统当前时间
    private int shopNum = 0;// 商品件数

    private boolean isSecondClick;//限制立即支付按钮的点击
    //	private boolean mIsTwoGroup=false;
    private long deadline = 0;//引导过期时间

    private boolean wxInstall;

    private String shop_name = "";

    private boolean isVIPpay;
    private boolean isBuyTXK;
    private Context mContext;
    private boolean showZFBthishi;
    private double vipZFBjian = 3.0;

    private String price;
    private String vipPrice = "0.0";
    private double vipDiscount;
    private boolean isBuyCoujiangCount;
    private WaitDialog waitDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (null != aBar) {
            aBar.hide();
        }
        mContext = this;
        setContentView(R.layout.activity_payment);
        waitDialog = new WaitDialog(this, R.style.DialogStyle1);

        WXPayEntryActivity.setOnWxpayFinish(this);// 实现微信支付成功之后调分享界面接口
        wxInstall = WXcheckUtil.isWeChatAppInstalled(this);
//		mIsTwoGroup=getIntent().getBooleanExtra("mIsTwoGroup", false);
        mIsQulification = SharedPreferencesUtil.getBooleanData(this, Pref.IS_QULIFICATION, false);
        mIsOpen = Integer.parseInt(SharedPreferencesUtil.getStringData(this, Pref.IS_OPEN, "0"));
        mTwofoldness = Integer.parseInt(SharedPreferencesUtil.getStringData(this, Pref.TWOFOLDNESS, "0"));
        msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp(WxPayUtil.APP_ID);
        resultMap = (HashMap<String, Object>) getIntent().getSerializableExtra("result");
        listGoods = (List<ShopCart>) getIntent().getSerializableExtra("listGoods");
        isVIPpay = getIntent().getBooleanExtra("isVIPpay", false);
        isBuyTXK = getIntent().getBooleanExtra("isBuyTXK", false);

        if (null == listGoods) {
            shopNum = 1;
        } else {
            for (int i = 0; i < listGoods.size(); i++) {
                ShopCart cart = listGoods.get(i);
                shopNum = shopNum + cart.getShop_num();
                // sum = sum + (cart.getShop_num() * cart.getShop_se_price());
            }
        }

        totalAccount = getIntent().getDoubleExtra("totlaAccount", 0);
        isGcode = getIntent().getBooleanExtra("is_g_code", false);
        isOneBuy = getIntent().getBooleanExtra("isOneBuy", false);
        isMeal = getIntent().getBooleanExtra("isMeal", false);
        isOneBuyOnce = getIntent().getBooleanExtra("isOneBuyOnce", false);
        isBuyCoujiangCount = getIntent().getBooleanExtra("isBuyCoujiangCount", false);
        vipDiscount = getIntent().getDoubleExtra("vipDiscount", 0);
        vipZFBjian = vipDiscount;

        if (isOneBuyOnce) {
            shop_name = getIntent().getStringExtra("shop_name");
        }

        shengyuTime = getIntent().getLongExtra("shengyuTime", 0);
        zhoujiangShowOnePrice = getIntent().getStringExtra("zhoujiangShowOnePrice");

        recLen = shengyuTime;
        deadline = Long.parseLong(SharedPreferencesUtil.getStringData(this, YConstance.Pref.YIDOU_HALVE_END_TIMES, "0"));
        getSystemTime(1);

        if (null != resultMap) {
            orderNo = (String) resultMap.get("order_code");
        } else {
            orderNo = getIntent().getStringExtra("order_code");
        }

        LogYiFu.e("orderNo", orderNo + "");
        // 此处从未付款订单跳转
//        isMulti = getIntent().getBooleanExtra("isMulti", false);


        listOrder = (List<Order>) getIntent().getSerializableExtra("listOrder");


        timeCount = new TimeCount(1500, 500);
        rel_pay_success = (RelativeLayout) findViewById(R.id.rel_pay_success);
        rel_nomal = (RelativeLayout) findViewById(R.id.rel_nomal);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_three_yuan = (TextView) findViewById(R.id.tv_three_yuan);
        //支付宝减3元（默认支付宝）
        price = new java.text.DecimalFormat("#0.00").format(totalAccount);

        if (isVIPpay && !isBuyTXK) {
            vipPrice = new java.text.DecimalFormat("#0.00").format(totalAccount - vipZFBjian);
            tv_price.setText("¥" + vipPrice);
        } else {
            tv_price.setText("¥" + price);
        }


        tv_three_yuan.setText("（立减" + vipZFBjian + "元）");


        initview();
        getPayMap();


        if (isVIPpay && !isBuyTXK) {
            tv_three_yuan.setVisibility(View.VISIBLE);
        } else {
            tv_three_yuan.setVisibility(View.GONE);
        }

        // 查询我的余额
        // queryMyMomeny();
        // 查询用户等级
//		getUserGradle();
        // 付款下单计时
        if (mTask2 != null) {
            mTask2.cancel();
            mTask2 = new MyTimerTask2();
        } else {
            mTask2 = new MyTimerTask2();
        }
//		timer2.schedule(mTask2, 0, 1000); // timeTask

        // TODO:页面显示的一系列操作
        showView();
    }

    private void showView() {
        if (mIsOpen == 1) {
            // mRlNeedPay.setVisibility(View.VISIBLE);
            // mView3.setVisibility(View.VISIBLE);
        }
    }

    // 得到一个map集合 存放选择支付类型
    private void getPayMap() {
        payTypeMap = new HashMap<Integer, Boolean>();
        for (int i = 0; i < 6; i++) {
            payTypeMap.put(i, false);
        }


        ivWalletPay.setSelected(false);

        //主推支付宝
        ivAliPay.setSelected(true);
        ivWXinPay.setSelected(false);
        payTypeMap.put(3, true); //主推支付宝


//        if ((isOneBuy || isOneBuyOnce || isVIPpay) && wxInstall) { //1元购自动唤起微信支付
//
//
//            //直接调起支付（默认微信支付）
//            Iterator<Entry<Integer, Boolean>> iterator = payTypeMap.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Entry<Integer, Boolean> entry = iterator.next();
//                if (entry.getValue()) {
//                    ToastUtil.showShortText(PaymentActivity.this, "加载中...");
//                    getPrepayId(YUrl.WX_PAY_MULTI);
//                    break;
//                }
//            }
//        }


    }

    private void initview() {
        mLlFailture = (LinearLayout) findViewById(R.id.payment_ll_pay_failture);
        mViewLine = findViewById(R.id.view_id);
        mView1 = findViewById(R.id.view1);
        mView2 = findViewById(R.id.view2);
        mView3 = findViewById(R.id.view3);
        mTvMoney = (TextView) findViewById(R.id.tv_wallet);
        mTvUsefulMoney = (TextView) findViewById(R.id.tv_useful_money);
        mRlNeedPay = (RelativeLayout) findViewById(R.id.rl_need_pay);
        mTvNeedMoney = (TextView) findViewById(R.id.tv_need_money);
        ll_zhifujine = (RelativeLayout) findViewById(R.id.ll_zhifujine);
        root = (RelativeLayout) findViewById(R.id.root);
        root.setBackgroundColor(Color.WHITE);
        llWalletPay = (LinearLayout) findViewById(R.id.ll_wallet);
        llWalletPay.setVisibility(View.GONE);
        llQuickPay = (LinearLayout) findViewById(R.id.ll_quick_pay);
        llWXinPay = (LinearLayout) findViewById(R.id.ll_wxin_pay);
        llAliPay = (LinearLayout) findViewById(R.id.ll_ali_pay);
        llUnionPay = (LinearLayout) findViewById(R.id.ll_union_pay);
        llMydeayPay = (LinearLayout) findViewById(R.id.ll_mydear_pay);
        ivWalletPay = (ImageView) findViewById(R.id.iv_wallet);
        ivWalletPay.setSelected(true);

        ivQuickPay = (ImageView) findViewById(R.id.iv_quick_pay);
        ivWXinPay = (ImageView) findViewById(R.id.iv_wxin_pay);
        ivAliPay = (ImageView) findViewById(R.id.iv_ali_pay);
        ivUnionPay = (ImageView) findViewById(R.id.iv_union_pay);
        ivMydearPay = (ImageView) findViewById(R.id.iv_mydear_pay);

        // 勾选按钮集合
        imgSelectList = new ArrayList<ImageView>();
        imgSelectList.add(ivWalletPay);
        imgSelectList.add(ivQuickPay);
        imgSelectList.add(ivWXinPay);
        imgSelectList.add(ivAliPay);
        imgSelectList.add(ivUnionPay);
        imgSelectList.add(ivMydearPay);

        tvTotalAccount = (TextView) findViewById(R.id.tv_total_account);// 共计支付

        tshengyuTime = (TextView) findViewById(R.id.tshengyuTime);

//        String price = new java.text.DecimalFormat("#0.00").format(totalAccount);
//        tvTotalAccount.setText(price + "元");


        if (isVIPpay && !isBuyTXK) {
            tvTotalAccount.setText(vipPrice + "元");
        } else {
            tvTotalAccount.setText(price + "元");
        }


        btnPay = (LinearLayout) findViewById(R.id.btn_pay); // 付款
        imgCancle = (ImageView) findViewById(R.id.iv_cancle);// 取消返回
        mTvPayTimes = (TextView) findViewById(R.id.tv_pay_times);
        setOnclick();
//
//        if(isVIPpay){
//            llAliPay.setVisibility(View.GONE);
//        }
    }

    // 设置点击监听
    private void setOnclick() {

        llQuickPay.setOnClickListener(this);
        llWXinPay.setOnClickListener(this);
        llAliPay.setOnClickListener(this);
        llUnionPay.setOnClickListener(this);
        llMydeayPay.setOnClickListener(this);
        // ivWalletPay.setOnClickListener(this);
        ivQuickPay.setOnClickListener(this);
        ivWXinPay.setOnClickListener(this);
        ivAliPay.setOnClickListener(this);
        ivUnionPay.setOnClickListener(this);
        ivMydearPay.setOnClickListener(this);
        btnPay.setOnClickListener(this);
        imgCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_wallet:// 余额支付
            case R.id.iv_wallet:
                setSelectStatus(0);
                break;
            case R.id.ll_quick_pay:
            case R.id.iv_quick_pay:
                // setSelectStatus(1);
                break;
            case R.id.ll_wxin_pay:// 微信支付
            case R.id.iv_wxin_pay:

                if (isVIPpay && !isBuyTXK) { //购买会员不减3元（除提现卡）
                    if (!showZFBthishi) {
                        tv_price.setText(vipPrice + "元");
                        tvTotalAccount.setText(vipPrice + "元");
                        showZFBtishiDialog();
                        return;
                    }
                }

                tv_price.setText(price + "元");
                tvTotalAccount.setText(price + "元");
                setSelectStatus(2);


                break;
            case R.id.ll_ali_pay:// 支付宝支付
            case R.id.iv_ali_pay:
                if (isVIPpay && !isBuyTXK) {
                    tv_price.setText(vipPrice + "元");
                    tvTotalAccount.setText(vipPrice + "元");
                } else {
                    tv_price.setText(price + "元");
                    tvTotalAccount.setText(price + "元");
                }
                setSelectStatus(3);
                break;
            case R.id.ll_union_pay:
            case R.id.iv_union_pay:
                setSelectStatus(4);
                break;
            case R.id.ll_mydear_pay:
            case R.id.iv_mydear_pay:
                setSelectStatus(5);
                break;
            case R.id.btn_pay:// 去支付
                waitDialog.show();
                YunYingTongJi.yunYingTongJi(this, 113);
                Iterator<Entry<Integer, Boolean>> iterator = payTypeMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<Integer, Boolean> entry = iterator.next();
                    if (entry.getValue()) {
                        gotoPay(entry.getKey(), view);
                        break;
                    }
                }
                break;
            case R.id.iv_cancle:// 取消支付 返回上一层
                // getOrderInfo();
                // if (!isMulti) {
                // getOrderInfo();
                // } else {
                // // TODO:
                // // noticeDialog();
                //
                // Intent intent = new Intent(PaymentActivity.this,
                // StatusInfoActivity.class);
                // intent.putExtra("index", 1);
                // startActivity(intent);
                //
                // PaymentActivity.this.finish();
                // if (null != SubmitMultiShopActivty.instance) {
                // SubmitMultiShopActivty.instance.finish();
                // }
                // if (null != SubmitOrderActivity.instance) {
                // SubmitOrderActivity.instance.finish();
                // }
                // }
//			noticeDialog();


//                if(isVIPpay){
//                    finish();
//                    return;
//                }

                if (isBuyCoujiangCount) {
                    PaymentActivity.this.finish();
                    return;
                }


                if (isOneBuyOnce) {

                    setResult(11);
                    PaymentActivity.this.finish();

                } else if (isOneBuy) {
                    finish();

                } else if (isVIPpay) {
                    PaymentActivity.this.finish();

                } else {

                    Intent intent = new Intent(PaymentActivity.this, StatusInfoActivity.class);
                    intent.putExtra("index", 1);
                    startActivity(intent);

                    PaymentActivity.this.finish();
                    if (null != SubmitMultiShopActivty.instance) {
                        SubmitMultiShopActivty.instance.finish();
                    }
                    if (null != SubmitOrderActivity.instance) {
                        SubmitOrderActivity.instance.finish();
                    }
                    if (null != OneBuySubmitShopActivty.instance) {
                        OneBuySubmitShopActivty.instance.finish();
                    }
                }

                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // getOrderInfo();
//			noticeDialog();

//            if(isVIPpay){
//                finish();
//                return false;
//            }

            if (isBuyCoujiangCount) {
                PaymentActivity.this.finish();

            } else if (isOneBuyOnce) {

                setResult(11);
                PaymentActivity.this.finish();

            } else if (isOneBuy) {
                finish();

            } else if (isVIPpay) {
                PaymentActivity.this.finish();

            } else {

                Intent intent = new Intent(PaymentActivity.this, StatusInfoActivity.class);
                intent.putExtra("index", 1);
                startActivity(intent);

                PaymentActivity.this.finish();
                if (null != SubmitMultiShopActivty.instance) {
                    SubmitMultiShopActivty.instance.finish();
                }
                if (null != SubmitOrderActivity.instance) {
                    SubmitOrderActivity.instance.finish();
                }
                if (null != OneBuySubmitShopActivty.instance) {
                    OneBuySubmitShopActivty.instance.finish();
                }
            }


//            finish();
            // if (!isMulti) {
            // getOrderInfo();
            // } else {
            // Intent intent = new Intent(PaymentActivity.this,
            // StatusInfoActivity.class);
            // intent.putExtra("index", 1);
            // startActivity(intent);
            //
            // PaymentActivity.this.finish();
            // if (null != SubmitMultiShopActivty.instance) {
            // SubmitMultiShopActivty.instance.finish();
            // }
            // if (null != SubmitOrderActivity.instance) {
            // SubmitOrderActivity.instance.finish();
            // }
            // }
        }
        return false;
    }

    private AlertDialog dialog;

    private void customDialog() {
        AlertDialog.Builder builder = new Builder(this);
        // 自定义一个布局文件
        View view = View.inflate(this, R.layout.payback_esc_apply_dialog, null);
        TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
        tv_des.setText("您还没有设置支付密码，立即去设置？");

        Button ok = (Button) view.findViewById(R.id.ok);
        ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
        Button cancel = (Button) view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 把这个对话框取消掉
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, SetMyPayPassActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }

    private void checkMyWalletPayPass(View v) {
        new SAsyncTask<Void, Void, CheckPwdInfo>(PaymentActivity.this, v, R.string.wait) {

            @Override
            protected CheckPwdInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel.checkPWD(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, CheckPwdInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                isSecondClick = false;
                if (null == e) {
                    if (result != null && "1".equals(result.getStatus()) && "1".equals(result.getFlag())) {
                        customDialog();
                    } else if (result != null && "1".equals(result.getStatus()) && "2".equals(result.getFlag())) {
                        customDialog = new PayPasswordCustomDialog(PaymentActivity.this, R.style.mystyle,
                                R.layout.pay_customdialog, "请输入支付密码",
                                new java.text.DecimalFormat("#0.00").format(totalAccount));
                        inputDialogListener = new InputDialogListener() {

                            @Override
                            public void onOK(String pwd) {
                                // 给密码文本框设置密码
                                isSecondClick = true;
                                walletPayOrder(orderNo, pwd, isMulti);
                            }

                            @Override
                            public void onCancel() {

                                // getOrderInfo();
                                if (!isMulti) {
                                    getOrderInfo();
                                } else {
                                    Intent intent = new Intent(PaymentActivity.this, StatusInfoActivity.class);
                                    intent.putExtra("index", 1);
                                    startActivity(intent);

                                    PaymentActivity.this.finish();
                                    if (null != SubmitMultiShopActivty.instance) {
                                        SubmitMultiShopActivty.instance.finish();
                                    }
                                    if (null != SubmitOrderActivity.instance) {
                                        SubmitOrderActivity.instance.finish();
                                    }
                                }
                            }

                        };
                        customDialog.setListener(inputDialogListener);
                        customDialog.show();
                    } else {
                        ToastUtil.showLongText(context, "糟糕，出错了~~~");
                    }
                }
            }

        }.execute((Void[]) null);
    }

    // 付款
    private void gotoPay(Integer position, View v) {
        switch (position) {
            case 0: // 余额支付 钱包支付
                if (isSecondClick) {
                    break;
                }
                isSecondClick = true;
                checkMyWalletPayPass(v);
                break;
            case 1: // 快捷支付

                break;
            case 2: // 微信支付

                if (!wxInstall) {
                    ToastUtil.showShortText2("您还未安装微信哦~");
                    return;
                }
                /*
                 * if (null != listGoods) { if (listGoods.size() > 1) {
                 * getPrepayId(YUrl.WX_PAY_MULTI); } else {
                 * getPrepayId(YUrl.WX_PAY_SINGLE); } } else {
                 */
                ToastUtil.showShortText(PaymentActivity.this, "加载中...");
                if (isMulti) {
                    getPrepayId(YUrl.WX_PAY_MULTI);
                } else {
                    getPrepayId(YUrl.WX_PAY_SINGLE);
                }
                // }
                break;
            case 3: // 支付宝支付
                /*
                 * if (null != listGoods) { if (listGoods.size() > 1) { aliPay(null,
                 * YUrl.ALI_NOTIFY_URL_MULTI, orderNo); } else { aliPay(null,
                 * YUrl.ALI_NOTIFY_URL_SINGLE, orderNo); } } else {
                 */
                getAliParam();
                /*
                 * if (isMulti) { aliPay(null, YUrl.ALI_NOTIFY_URL_MULTI, orderNo);
                 * } else { aliPay(null, YUrl.ALI_NOTIFY_URL_SINGLE, orderNo); }
                 */
                // }
                break;
            case 4: // 银联支付

                break;
            case 5: // 找亲爱的支付

                break;

            default:
                break;
        }
    }

    /**
     * 获取支付宝所需参数
     */
    private void getAliParam() {
        new SAsyncTask<Void, Void, HashMap<String, String>>(this, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getAliParam(context, orderNo);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                if (e == null) {
                    if (result.get("status").equals("1")) {
                        if (isMulti) {
                            aliPay(null, result.get("pay_url") + YUrl.ALI_NOTIFY_URL_MULTI, orderNo,
                                    result.get("partner"), result.get("seller"), result.get("sign_type"),
                                    result.get("private_key"), result.get("price"));
                        } else {
                            aliPay(null, result.get("pay_url") + YUrl.ALI_NOTIFY_URL_SINGLE, orderNo,
                                    result.get("partner"), result.get("seller"), result.get("sign_type"),
                                    result.get("private_key"), result.get("price"));
                        }
                    }
                }
                super.onPostExecute(context, result, e);
            }

        }.execute();
    }

    // 设置选择状态
    private void setSelectStatus(int i) {
        for (int j = 0; j < 6; j++) {
            if (j == i) {
                payTypeMap.put(j, true);
                imgSelectList.get(j).setSelected(true);
            } else {
                payTypeMap.put(j, false);
                imgSelectList.get(j).setSelected(false);
            }
        }
        // TODO:使用余额和其他方式同时支付
        if ((mIsQulification && mIsOpen == 1 && mMyMoney > 0) || (grade != 1 && grade != 0)) {// 余额大于6，并且已经开启余额翻倍
            imgSelectList.get(0).setSelected(true);
        }
    }

    /**
     * 支付宝支付
     *
     * @param v
     * @param order_code
     */
    private void aliPay(View v, String notify_url, String order_code, String partner, String seller, String sign_type,
                        String private_key, String totalMoney) {


        StringBuffer sb = new StringBuffer();

        if (isOneBuyOnce) {
            sb.append(shop_name);
        } else {
            if (null != listGoods) {
                for (int i = 0; i < listGoods.size(); i++) {
                    ShopCart sc = listGoods.get(i);
                    sb.append(sc.getShop_name()).append(" ");
                }
            } else {
                if (!isVIPpay && !isBuyCoujiangCount && !isBuyTXK) {
                    for (int i = 0; i < listOrder.size(); i++) {
                        Order order = listOrder.get(i);
                        sb.append(order.getOrder_name()).append(" ");
                    }
                }

            }
        }


        String subject = Config.pay_subject;
        String content = sb.toString();
        // String price = sum + "";
        // shop.getShop_se_price() + "";

        // 订单
        String orderInfo = PayUtil.getOrderInfo(subject, content, order_code, totalMoney + "", notify_url, partner,
                seller);

        LogYiFu.e("totalMoney", totalMoney + "");
        // 对订单做RSA 签名
        String sign = PayUtil.sign(orderInfo, private_key);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + PayUtil.getSignType(sign_type);

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(PaymentActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {

                    waitDialog.dismiss();

                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {

                        if (isVIPpay) {
                            setResult(MyVipListActivity.PAY_SUCCESS);
                            finish();
                            return;
                        }

                        if (isBuyCoujiangCount) {
                            setResult(OneBuyChouJiangActivity.BUY_COUNT_SUCCESS);
                            finish();
                            return;
                        }


                        SharedPreferencesUtil.saveBooleanData(mContext, "signDATAneedRefresh", true);
                        // Toast.makeText(PaymentActivity.this, "支付成功",
                        // Toast.LENGTH_SHORT).show();
                        // showShareDialog();

                        // yunYunTongJi(shop_code, 200, 2); // 统计购物车结算次数

                        // updatePayStatus(orderNo, 1);

                        updatePayStatusList(orderNo, 1 + "");
//                        ToastUtil.showShortText(PaymentActivity.this, "支付成功");
                        if (MealSubmitOrderActivity.instance != null) {
                            MealSubmitOrderActivity.instance.finish();
                        }
                        // MealPaymentActivity.this.finish();
                        if (timeCount == null) {
                            timeCount = new TimeCount(1500, 500);
                        }
                        timeCount.start();
                        rel_pay_success.setVisibility(View.VISIBLE);
                        TongJiUtils.TongJi(mContext, 13 + "");//支付成功 统计到达次数
                        LogYiFu.e("TongJiNew", 13 + "");
//					if(ShopCartNewNewActivity.instance!=null){
//						ShopCartNewNewActivity.instance.finish();
//						ShopCartNewNewActivity.instance=null;
//					}
                        YunYingTongJi.yunYingTongJi(PaymentActivity.this, 114);
                        SharedPreferencesUtil.saveStringData(PaymentActivity.this, Pref.TONGJI_TYPE, "1056");
                        rel_nomal.setVisibility(View.GONE);

                        // updatePayStatus(orderNo);
                        //
                        // if ((listGoods != null && listGoods.size() == 1)
                        // || (listOrder != null && listOrder.size() == 1)) {
                        // getShopLink();
                        //
                        // } else {
                        // Intent intent = new Intent(PaymentActivity.this,
                        // PaymentSuccessActivity.class);
                        // intent.putExtra("listGoods", (Serializable) listGoods);
                        // intent.putExtra("listOrder", (Serializable) listOrder);
                        // intent.putExtra("order_code", orderNo);
                        // intent.putExtra("isMulti", isMulti);
                        // startActivity(intent);
                        // PaymentActivity.this.finish();
                        // if (null != SubmitMultiShopActivty.instance) {
                        // SubmitMultiShopActivty.instance.finish();
                        // }
                        // }
                        // PaymentActivity.this.setResult(1);
                        // finish();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PaymentActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {

                            if (isVIPpay) {
                                setResult(MyVipListActivity.PAY_FAIL);
                                finish();
                                return;
                            }

                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            // TODO:支付失败时的显示
                            mLlFailture.setVisibility(View.VISIBLE);
                            mTvPayTimes.setVisibility(View.GONE);
                            // noticeDialog();
                            // if (mTask != null) {
                            // mTask.cancel();
                            // mTask = new MyTimerTask();
                            // } else {
                            // mTask = new MyTimerTask();
                            // }
                            // timer.schedule(mTask, 0, 1000); // timeTask
                            // Toast.makeText(PaymentActivity.this, "支付失败",
                            // Toast.LENGTH_SHORT).show();

                            // getOrderInfo();
                            // if (!isMulti) {
                            // getOrderInfo();
                            // } else {
                            // Intent intent = new Intent(PaymentActivity.this,
                            // StatusInfoActivity.class);
                            // intent.putExtra("index", 1);
                            // startActivity(intent);
                            //
                            // PaymentActivity.this.finish();
                            // if (null != SubmitMultiShopActivty.instance) {
                            // SubmitMultiShopActivty.instance.finish();
                            // }
                            // if (null != SubmitOrderActivity.instance) {
                            // SubmitOrderActivity.instance.finish();
                            // }
                            // }
                        }
                    }
                    break;
                }
            }
        }

        ;
    };

    private void getOrderInfo() {
        new SAsyncTask<Void, Void, Order>(PaymentActivity.this, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected Order doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel2.getMyOrder(context, orderNo);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Order result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    bundle = new Bundle();
                    bundle.putSerializable("order", result);
                    getSystemTime(2);
                    // Intent intent = new Intent(PaymentActivity.this,
                    // OrderDetailsActivity.class);
                    // Bundle bundle = new Bundle();
                    // bundle.putSerializable("order", result);
                    // intent.putExtras(bundle);
                    // startActivity(intent);
                    //
                    // PaymentActivity.this.finish();
                    // if (null != SubmitMultiShopActivty.instance) {
                    // SubmitMultiShopActivty.instance.finish();
                    // }
                    // if (null != SubmitOrderActivity.instance) {
                    // SubmitOrderActivity.instance.finish();
                    // }
                }
            }

        }.execute();
    }

    private void getSystemTime(final int a) {//a==1，一进来调用支付剩余时间，a==2,为了订单详情页面使用
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) PaymentActivity.this, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getSystemTime(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);

                if (null == e && result != null) {
                    if (a == 2) {
                        nowTime = (Long) result.get("now");

                        Intent intent = new Intent(PaymentActivity.this, OrderDetailsActivity.class);
                        intent.putExtras(bundle);
                        intent.putExtra("nowTime", nowTime);
                        startActivity(intent);

                        PaymentActivity.this.finish();
                        if (null != SubmitMultiShopActivty.instance) {
                            SubmitMultiShopActivty.instance.finish();
                        }
                        if (null != SubmitOrderActivity.instance) {
                            SubmitOrderActivity.instance.finish();
                        }
                    } else if (a == 1) {
                        long nowTime = (Long) result.get("now");
                        recLen_guide = deadline - nowTime;
//                        recLen_guide=1*60*1000;
                    }
                } else if (a == 1) {
                    recLen_guide = 0;
                }
                if (a == 1) {
                    timer2.schedule(mTask2, 0, 1000); // timeTask
                }
            }

        }.execute();
    }

    /****
     * 通过我的钱包来付款
     *
     * @param order_code
     * @param pwd
     */
    private void walletPayOrder(String order_code, String pwd, final boolean isMulti) {
        new SAsyncTask<String, Void, ReturnInfo>(PaymentActivity.this, null, R.string.wait) {
            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                if (!isMulti) {
                    return ComModel.walletPayOrder(context, params[0], params[1]);
                } else {
                    return ComModel.walletPayMultiOrder(context, params[0], params[1]);
                }
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo r, Exception e) {

                if (e != null) {// 查询异常
                    isSecondClick = false;
                    LogYiFu.e("异常 -----", context.getString(R.string.ss));
                    // Toast.makeText(context, "" + e,
                    // Toast.LENGTH_SHORT).show();
                    /*
                     * if (!isMulti) { getOrderInfo(); } else { Intent intent =
                     * new Intent(PaymentActivity.this,
                     * StatusInfoActivity.class); intent.putExtra("index", 1);
                     * startActivity(intent);
                     *
                     * PaymentActivity.this.finish(); if (null !=
                     * SubmitMultiShopActivty.instance) {
                     * SubmitMultiShopActivty.instance.finish(); } if (null !=
                     * SubmitOrderActivity.instance) {
                     * SubmitOrderActivity.instance.finish(); } }
                     */
                } else {// 查询商品详情成功，刷新界面

                    int pwdflag = r.getPwdflag();

                    if (pwdflag == 0) { // 支付成功
//                        ToastUtil.showShortText(PaymentActivity.this, "支付成功");
                        SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
                        // this.finish();
                        // MealPaymentActivity.this.finish();
                        // paySuccessTo();
                        if (timeCount == null) {
                            timeCount = new TimeCount(1500, 500);
                        }
                        timeCount.start();
                        rel_pay_success.setVisibility(View.VISIBLE);
                        TongJiUtils.TongJi(context, 13 + "");//支付成功 统计到达次数
                        LogYiFu.e("TongJiNew", 13 + "");
                        if (ShopCartNewNewActivity.instance != null) {
                            ShopCartNewNewActivity.instance.finish();
                            ShopCartNewNewActivity.instance = null;
                        }
                        YunYingTongJi.yunYingTongJi(PaymentActivity.this, 114);
                        SharedPreferencesUtil.saveStringData(PaymentActivity.this, Pref.TONGJI_TYPE, "1056");
                        rel_nomal.setVisibility(View.GONE);

                        // yunYunTongJi(shop_code, 200, 2); // 统计购物车结算次数
                        // if ((listGoods != null && listGoods.size() == 1)
                        // || (listOrder != null && listOrder.size() == 1)) {
                        //
                        // getShopLink();
                        // } else {
                        // Intent intent = new Intent(PaymentActivity.this,
                        // PaymentSuccessActivity.class);
                        // intent.putExtra("listGoods", (Serializable)
                        // listGoods);
                        // intent.putExtra("listOrder", (Serializable)
                        // listOrder);
                        // intent.putExtra("order_code", orderNo);
                        // intent.putExtra("isMulti", isMulti);
                        // startActivity(intent);
                        // PaymentActivity.this.finish();
                        // if (null != SubmitMultiShopActivty.instance) {
                        // SubmitMultiShopActivty.instance.finish();
                        // }
                        // }

                        // PaymentActivity.this.setResult(1);
                        // finish();

                    } else if (pwdflag == 1 || pwdflag == 2 || pwdflag == 3) { // 支付密码错误
                        isSecondClick = false;
                        String message = r.getMessage();
                        // customDialog.dismiss();
                        PayErrorDialog dialog = new PayErrorDialog(context, R.style.DialogStyle1, pwdflag, message);
                        dialog.show();

                    }

                    // if ((listGoods != null && listGoods.size() == 1)
                    // || (listOrder != null && listOrder.size() == 1)) {
                    //
                    // getShopLink();
                    // } else {
                    // Intent intent = new Intent(PaymentActivity.this,
                    // PaymentSuccessActivity.class);
                    // intent.putExtra("listGoods", (Serializable) listGoods);
                    // intent.putExtra("listOrder", (Serializable) listOrder);
                    // intent.putExtra("order_code", orderNo);
                    // intent.putExtra("isMulti", isMulti);
                    // startActivity(intent);
                    // PaymentActivity.this.finish();
                    // if (null != SubmitMultiShopActivty.instance) {
                    // SubmitMultiShopActivty.instance.finish();
                    // }
                    // }
                    // PaymentActivity.this.setResult(1);
                    // finish();
                }

            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;
        }.execute(order_code, pwd);

    }


    /***
     * 微信支付
     *
     * @author Administrator
     *
     */
    private void getPrepayId(String wxPayUrl) {
        new SAsyncTask<String, Void, Map<String, String>>(PaymentActivity.this, R.string.wait) {

            @Override
            protected Map<String, String> doInBackground(FragmentActivity context, String... params) throws Exception {
                Map<String, String> mapReturn = ComModel2.getPrepayId(context, orderNo, "test", params[0]);
                return mapReturn;
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Map<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);

                if (null == e) {
                    WxPayUtil.sendPayReq(msgApi, WxPayUtil.genPayReq(result), result.get("appid"));
                }
                // finish();
            }

        }.execute(wxPayUrl);
    }

    @Override
    public void onWxpaySuccess() {
        waitDialog.dismiss();

        if (isVIPpay) {
            setResult(MyVipListActivity.PAY_SUCCESS);
            finish();
        }

        if (isBuyCoujiangCount) {
            setResult(OneBuyChouJiangActivity.BUY_COUNT_SUCCESS);
            finish();
            return;
        }

        // yunYunTongJi(shop_code, 200, 2); // 统计购物车结算次数

        // updatePayStatus(orderNo, 2);
        updatePayStatusList(orderNo, 2 + "");
//        ToastUtil.showShortText(PaymentActivity.this, "支付成功");
        SharedPreferencesUtil.saveBooleanData(mContext, "signDATAneedRefresh", true);

        // yunYunTongJi(shop_code, 200, 2);

        // MealSubmitOrderActivity.instance.finish();
        // MealPaymentActivity.this.finish();
        if (timeCount == null) {
            timeCount = new TimeCount(1500, 500);
        }
        timeCount.start();
        rel_pay_success.setVisibility(View.VISIBLE);
        TongJiUtils.TongJi(mContext, 13 + "");//支付成功 统计到达次数
        LogYiFu.e("TongJiNew", 13 + "");
//		if(ShopCartNewNewActivity.instance!=null){
//			ShopCartNewNewActivity.instance.finish();
//			ShopCartNewNewActivity.instance=null;
//		}
        YunYingTongJi.yunYingTongJi(PaymentActivity.this, 114);
        SharedPreferencesUtil.saveStringData(PaymentActivity.this, Pref.TONGJI_TYPE, "1056");

        // updatePayStatus(orderNo);
        // if ((listGoods != null && listGoods.size() == 1)
        // || (listOrder != null && listOrder.size() == 1)) {
        // getShopLink();
        // } else {
        // Intent intent = new Intent(PaymentActivity.this,
        // PaymentSuccessActivity.class);
        // intent.putExtra("listGoods", (Serializable) listGoods);
        // intent.putExtra("listOrder", (Serializable) listOrder);
        // intent.putExtra("order_code", orderNo);
        // intent.putExtra("isMulti", isMulti);
        // startActivity(intent);
        // PaymentActivity.this.finish();
        // if (null != SubmitMultiShopActivty.instance) {
        // SubmitMultiShopActivty.instance.finish();
        // }
        // }
        // PaymentActivity.this.setResult(1);
        // finish();
    }

    @Override
    public void onWxpayFailed() {

        waitDialog.dismiss();
        if (isVIPpay) {
            setResult(MyVipListActivity.PAY_FAIL);
            finish();
            instance.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
            return;
        }


        if (null != WXPayEntryActivity.instance) {
            WXPayEntryActivity.instance.finish();
        }
        // getOrderInfo();
        mLlFailture.setVisibility(View.VISIBLE);
        mTvPayTimes.setVisibility(View.GONE);
        // noticeDialog();
        // if (!isMulti) {
        // getOrderInfo();
        // } else {
        // Intent intent = new Intent(PaymentActivity.this,
        // StatusInfoActivity.class);
        // intent.putExtra("index", 1);
        // startActivity(intent);
        //
        // PaymentActivity.this.finish();
        // if (null != SubmitMultiShopActivty.instance) {
        // SubmitMultiShopActivty.instance.finish();
        // }
        // if (null != SubmitOrderActivity.instance) {
        // SubmitOrderActivity.instance.finish();
        // }
        // }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // MobclickAgent.onPageStart("PaymentActivity");
        // MobclickAgent.onResume(this);
        isSecondClick = false;
        HomeWatcherReceiver.registerHomeKeyReceiver(this);
        SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1055");
        TongJiUtils.TongJi(this, 12 + "");
        LogYiFu.e("TongJiNew", 12 + "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // MobclickAgent.onPageEnd("PaymentActivity");
        // MobclickAgent.onPause(this);
        HomeWatcherReceiver.unregisterHomeKeyReceiver(this);
        TongJiUtils.TongJi(this, 112 + "");
        LogYiFu.e("TongJiNew", 112 + "");

    }

    // 计时器
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔

        }

        @Override
        public void onFinish() {// 计时完毕时触发
            TongJiUtils.TongJi(mContext, 113 + "");//支付成功 统计跳出次数
            LogYiFu.e("TongJiNew", 113 + "");
            // if (ShopCartNewNewActivity.instance != null) {
            // ShopCartNewNewActivity.instance = null;
            // }
            // Intent intent = new Intent(PaymentActivity.this,
            // ShopCartNewNewActivity.class);
            // startActivity(intent);
            boolean isMad = SharedPreferencesUtil.getBooleanData(mContext, Pref.ISMADMONDAY, false);


//            if(isOneBuy || isOneBuyOnce){ //如果是1元购支付，更改抽奖的order_code
//                OneBuyChouJiangActivity.choujiangOderCode = orderNo;
//
//            }


            if (isOneBuyOnce || isBuyCoujiangCount) { //再抽一次下单

                setResult(10);
                PaymentActivity.this.finish();


            } else {


                if (isOneBuy) { //新拼团支付成功
//                    Intent OneBuyintent = new Intent(PaymentActivity.this, OneBuyGroupsDetailsActivity.class);
//                    OneBuyintent.putExtra("isKaituan", true);
//                    OneBuyintent.putExtra("isMeal", isMeal);
//                    startActivity(OneBuyintent);
//                    PaymentActivity.this.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    setResult(OneBuyGroupsDetailsActivity.PAY_SUCCESS);

                    PaymentActivity.this.finish();

                } else {


                    if (isMad) {
                        //疯狂星期一 支付成功后跳转到 抽奖
                        SharedPreferencesUtil.saveStringData(mContext, Pref.PAYSUCCESSDIALOG, "" + totalAccount);
                        Intent intent = new Intent(mContext, WithdrawalLimitActivity.class);
                        intent.putExtra("isFromPaySuccess", true);
                        intent.putExtra("payLotteryNumber", (int) (totalAccount % 5 == 0 ? totalAccount / 5 : totalAccount / 5 + 1));//通过订单金额  算的获得的疯狂抽奖次数
                        startActivity(intent);
                        PaymentActivity.this.finish();

                    } else {
//				if (MainMenuActivity.instances != null)
//					MainMenuActivity.instances.finish();
//				//支付成功之后跳转到购物页面
//				Intent intent = new Intent(context, MainMenuActivity.class);
//				intent.putExtra("toYf", "toYf");
////				intent.putExtra("mIsTwoGroup", mIsTwoGroup);
//				startActivity(intent);


//                        // 支付成功后跳转到 抽奖 弹框提示获得衣豆
//                        SharedPreferencesUtil.saveStringData(context, Pref.PAYSUCCESSDIALOG, "" + totalAccount);
//                        Intent intent = new Intent(context, WithdrawalLimitActivity.class);
//                        if (recLen_guide > 0) {
//                            intent.putExtra("is_guidPay", true);
//                        } else {
//                            intent.putExtra("is_guidPay", false);
//                        }
//                        intent.putExtra("isFromPaySuccess", true);
//                        intent.putExtra("payYiDouNumber", (int) (Math.ceil(totalAccount)));//通过订单金额  算的获得的衣豆
//                        startActivity(intent);

                        //普通商品单独购买全部跳订单详情
                        goToOrderDetail();


                    }
                }

            }


        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示

        }
    }

    private void goToOrderDetail() {

        new SAsyncTask<Void, Void, Order>(this, R.string.wait) {

            @Override
            protected Order doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.getMyOrderPaysuccss(context, orderNo);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(final FragmentActivity context, final Order result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {


                    Intent intent = new Intent(context,
                            OrderDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order", result);
                    intent.putExtras(bundle);
                    intent.putExtra("paySuccess", true);
                    startActivity(intent);
                    PaymentActivity.this.finish();


                }
            }

        }.execute();


    }

    // private long recLen = 30 * 1000 * 60;
    private long recLen = 10000;
    Timer timer = new Timer();
    private MyTimerTask mTask;

    // 倒计时
    public class MyTimerTask extends TimerTask {

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
                            tshengyuTime.setText("" + days + "天" + hours + ":" + minutes + ":" + seconds);
                            // mTvPayTimes.setText("" + days + "天" + hours + ":"
                            // + minutes + ":" + seconds);
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
                            tshengyuTime.setText("" + hours + ":" + minutes + ":" + seconds);
                        }
                    } else if (minute >= 10 && second >= 10) {
                        tshengyuTime.setText("" + minute + ":" + second);
                        // mTvPayTimes.setText("" + minute + ":" + second);
                    } else if (minute >= 10 && second < 10) {
                        tshengyuTime.setText("" + minute + ":0" + second);
                        // mTvPayTimes.setText("" + minute + ":0" + second);
                    } else if (minute < 10 && second >= 10) {
                        tshengyuTime.setText("0" + minute + ":" + second);
                        // mTvPayTimes.setText("0" + minute + ":" + second);
                    } else {
                        tshengyuTime.setText("0" + minute + ":0" + second);
                        // mTvPayTimes.setText("0" + minute + ":0" + second);
                    }
                    // tv_time.setText("" + recLen);
                    if (recLen == 0) {
                        timer.cancel();
                        // tv_time.setText("商品已过期");
                        btnPay.setBackgroundColor(Color.parseColor("#a8a8a8"));
                        btnPay.setClickable(false);

                    }
                }
            });
        }

    }

    private long recLen_normal = 24 * 60 * 60 * 1000;//正常支付剩余时间
    private long recLen_guide = 0;//引导页面使用的支付

    private long recLen2 = 0;
    Timer timer2 = new Timer();
    private MyTimerTask2 mTask2;

    // 倒计时
    public class MyTimerTask2 extends TimerTask {

        @Override
        public void run() {

            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
//					recLen2 -= 1000;
                    recLen_normal -= 1000;
                    if (recLen_guide > 0) {
                        recLen_guide -= 1000;
                    }
                    if (recLen_guide > 0 && mTvPayTimes.getVisibility() == View.VISIBLE) {
                        recLen2 = recLen_guide;
                    } else {
                        recLen2 = recLen_normal;
                    }
                    String days;
                    String hours;
                    String minutes;
                    String seconds;
                    long minute = recLen2 / 60000;
                    long second = (recLen2 % 60000) / 1000;
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
                            tshengyuTime.setText("" + days + "天" + hours + ":" + minutes + ":" + seconds);
                            mTvPayTimes.setText("" + days + "天" + hours + ":" + minutes + ":" + seconds);
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
                            tshengyuTime.setText("" + hours + ":" + minutes + ":" + seconds);
                            mTvPayTimes.setText("" + hours + ":" + minutes + ":" + seconds);
                        }
                    } else if (minute >= 10 && second >= 10) {
                        tshengyuTime.setText("" + minute + ":" + second);
                        mTvPayTimes.setText("" + minute + ":" + second);
                    } else if (minute >= 10 && second < 10) {
                        tshengyuTime.setText("" + minute + ":0" + second);
                        mTvPayTimes.setText("" + minute + ":0" + second);
                    } else if (minute < 10 && second >= 10) {
                        tshengyuTime.setText("0" + minute + ":" + second);
                        mTvPayTimes.setText("0" + minute + ":" + second);
                    } else {
                        tshengyuTime.setText("0" + minute + ":0" + second);
                        mTvPayTimes.setText("0" + minute + ":0" + second);
                    }
                    // tv_time.setText("" + recLen);
                    if (recLen2 <= 0 && recLen_normal <= 0) {
                        timer2.cancel();

                        Intent intent = new Intent(PaymentActivity.this, ShopCartNewNewActivity.class);
                        startActivity(intent);
                        PaymentActivity.this.finish();
                        // tv_time.setText("商品已过期");
                        // btn_pay.setBackgroundColor(Color.parseColor("#a8a8a8"));
                        // btn_pay.setClickable(false);

                    }
                }
            });
        }

    }

    // TODO:
    public void noticeDialog() {
        final Dialog dialog = new Dialog(mContext, R.style.invate_dialog_style);

//		Calendar c = Calendar.getInstance();
//		int day = c.get(Calendar.DAY_OF_MONTH);
        View view;
//		if (!("" + YCache.getCacheToken(context) + day).equals(SharedPreferencesUtil.getStringData(context, YConstance.Pref.PAY_HOME_BACK, ""))) {
//			SharedPreferencesUtil.saveStringData(context, YConstance.Pref.PAY_HOME_BACK, "" + YCache.getCacheToken(context) + day);
        view = View.inflate(mContext, R.layout.dialog_pay_back, null);
//		}else{
//			view = View.inflate(context, R.layout.payment_notice__dialog, null);
//		}

        TextView mConfrim = (TextView) view.findViewById(R.id.confrim);
        TextView mDismiss = (TextView) view.findViewById(R.id.dismiss);

        mDismiss.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 把这个对话框取消掉
                Iterator<Entry<Integer, Boolean>> iterator = payTypeMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<Integer, Boolean> entry = iterator.next();
                    if (entry.getValue()) {
                        gotoPay(entry.getKey(), null);
                        break;
                    }
                }
                dialog.dismiss();
            }
        });
        mConfrim.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO:
                if (!isMulti) {
                    getOrderInfo();
                } else {
                    Intent intent = new Intent(PaymentActivity.this, StatusInfoActivity.class);
                    intent.putExtra("index", 1);
                    startActivity(intent);

                    PaymentActivity.this.finish();
                    if (null != SubmitMultiShopActivty.instance) {
                        SubmitMultiShopActivty.instance.finish();
                    }
                    if (null != SubmitOrderActivity.instance) {
                        SubmitOrderActivity.instance.finish();
                    }
                }
            }
        });

        // 创建自定义样式dialog
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    private int grade = 1;
    private int buseTwofold = 6;
    private int auseTwofold = 6;

    /**
     * 获取用户等级
     */
    private void getUserGradle() {
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) PaymentActivity.this, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getUserGradle(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);

                if (null == e && result != null) {
                    grade = (Integer) result.get("grade");
                    buseTwofold = (Integer) result.get("buseTwofold");
                    auseTwofold = (Integer) result.get("auseTwofold");
                    queryMyMomeny();
                }
            }

        }.execute();
    }

    /**
     * 调用此接口查询我的余额 TODO:
     */

    private void queryMyMomeny() {

        new SAsyncTask<Void, Void, String[]>(PaymentActivity.this, R.string.wait) {

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
                        if (grade == 1 || grade == 0) {//A类用户
                            if (mIsQulification && mIsOpen == 1) {// 余额翻倍期间，>=6;可以使用余额
                                double mMoney = 0;
                                if (auseTwofold * shopNum <= mMyMoney * mTwofoldness) {
                                    mMoney = auseTwofold * shopNum;
                                } else {
                                    mMoney = mMyMoney * mTwofoldness;
                                }
                                mTvUsefulMoney
                                        .setText("可用金额" + new java.text.DecimalFormat("#0.00").format(mMoney) + "元");
                                // mRlNeedPay.setVisibility(View.VISIBLE);
                                mTvNeedMoney.setText(
                                        "" + new java.text.DecimalFormat("#0.00").format(totalAccount - mMoney));
                                llWalletPay.setClickable(false);
                                if (mMoney > 0) {
                                    ivWalletPay.setSelected(true);
                                } else {
                                    ivWalletPay.setSelected(false);
                                }
                                ivWXinPay.setSelected(true);
                                for (int i = 0; i < 6; i++) {
                                    payTypeMap.put(i, false);
                                }
                                payTypeMap.put(2, true);
                                llWalletPay.setVisibility(View.GONE);
                                tvTotalAccount
                                        .setText("" + new DecimalFormat("#0.00").format(totalAccount - mMoney) + "元");

                            }
                            // else if (mIsQulification && mIsOpen == 1) {//
                            // 余额翻倍期间,<6;不能使用余额
                            // mRlNeedPay.setVisibility(View.GONE);
                            // if (mMyMoney == 0) {
                            // mTvUsefulMoney.setText("可用金额" + mMyMoney *
                            // mTwofoldness + "元");
                            // mTvUsefulMoney.setTextColor(Color.parseColor("#c5c5c5"));
                            // llWalletPay.setClickable(false);
                            // ivWalletPay.setSelected(false);
                            // } else {
                            // mTvUsefulMoney.setText("可用金额" + mMyMoney *
                            // mTwofoldness + "元(余额不足)");
                            // mTvUsefulMoney.setTextColor(Color.parseColor("#c5c5c5"));
                            // llWalletPay.setClickable(false);
                            // ivWalletPay.setSelected(false);
                            // }
                            // ivAliPay.setSelected(true);
                            // for (int i = 0; i < 6; i++) {
                            // payTypeMap.put(i, false);
                            // }
                            // payTypeMap.put(3, true);
                            //
                            // }
                            else if (mMyMoney >= totalAccount) {// 正常期间，余额充足；可用余额支付
                                mRlNeedPay.setVisibility(View.GONE);
                                mTvUsefulMoney
                                        .setText("可用金额" + new java.text.DecimalFormat("#0.00").format(mMyMoney) + "元");
                                llWalletPay.setClickable(true);
                                ivWalletPay.setSelected(true);
                                llWalletPay.setVisibility(View.VISIBLE);
                                llWalletPay.setOnClickListener(PaymentActivity.this);
                            } else {// 正常期间，余额不足；不能使用余额支付
                                llWalletPay.setVisibility(View.VISIBLE);
                                mRlNeedPay.setVisibility(View.GONE);
                                if (mMyMoney == 0) {
                                    mTvUsefulMoney.setText("可用金额" + 0.00 + "元");
                                    mTvUsefulMoney.setTextColor(Color.parseColor("#c5c5c5"));
                                    llWalletPay.setClickable(false);
                                    ivWalletPay.setSelected(false);
                                    ivWXinPay.setSelected(true);
                                } else {
                                    mTvUsefulMoney.setText(
                                            "可用金额" + new java.text.DecimalFormat("#0.00").format(mMyMoney) + "元(余额不足)");
                                    mTvUsefulMoney.setTextColor(Color.parseColor("#c5c5c5"));
                                    llWalletPay.setClickable(false);
                                    ivWalletPay.setSelected(false);
                                    ivWXinPay.setSelected(true);
                                }
                                for (int i = 0; i < 6; i++) {
                                    payTypeMap.put(i, false);
                                }
                                payTypeMap.put(2, true);
                            }
                            // mTvUsefulMoney.setText("可用金额" + balance + "元");
                            // if(mMyMoney<totalAccount){
                            // mTvUsefulMoney.setTextColor(Color.parseColor("#c5c5c5"));
                            // mTvUsefulMoney.setText("可用金额" + balance +
                            // "元(余额不足)");
                            // }
                        } else {//B类用户
                            double mMoney = 0;
                            if (mIsQulification && mIsOpen == 1) {
                                llWalletPay.setVisibility(View.GONE);
                            } else {
                                llWalletPay.setVisibility(View.VISIBLE);
                            }
                            if (buseTwofold * shopNum <= mMyMoney) {
                                mMoney = buseTwofold * shopNum;
                            } else {
                                mMoney = mMyMoney;
                            }

                            LogYiFu.e("zzqyifu", mMoney + "xx");

                            mTvUsefulMoney.setText("可用金额" + new java.text.DecimalFormat("#0.00").format(mMoney) + "元");
                            // mRlNeedPay.setVisibility(View.VISIBLE);
                            llWalletPay.setClickable(false);
                            ivWalletPay.setSelected(false);
                            ivWXinPay.setSelected(true);

                            // mTvNeedMoney.setText(
                            // "" + new
                            // java.text.DecimalFormat("#0.0").format(totalAccount
                            // - mMoney));
//							if (mMoney > 0) {
//								ivWalletPay.setSelected(true);
//							} else {
//								ivWalletPay.setSelected(false);
//							}
                            llWalletPay.setVisibility(View.GONE);
                            ivWXinPay.setSelected(true);
                            for (int i = 0; i < 6; i++) {
                                payTypeMap.put(i, false);
                            }
                            payTypeMap.put(2, true);
                            // llWalletPay.setVisibility(View.GONE);
                            tvTotalAccount.setText("" + new DecimalFormat("#0.00").format(totalAccount - mMoney) + "元");
                        }

                    }
                }
            }

        }.execute((Void[]) null);

    }

    private void yunYunTongJi(final String shop_code, final int type, final int tab_type) {
        new SAsyncTask<Void, Void, HashMap<String, String>>(this, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.getOperator(context, shop_code, type, tab_type);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);
                // System.out.println("购物车调用成功");
            }

        }.execute();
    }

    private void updatePayStatusList(final String gcode, final String buy_type) {
        new SAsyncTask<Void, Void, ReturnInfo>(PaymentActivity.this) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel.updatePayStatusList(context, gcode, buy_type);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
            }

        }.execute();
    }


    private void showZFBtishiDialog() {


        LayoutInflater mInflater = LayoutInflater.from(mContext);
        final Dialog mDialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_common_one_button1, null);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tag_name = view.findViewById(R.id.tag_name);
        TextView btn_ok = view.findViewById(R.id.btn_ok);

        tv_title.setText("温馨提示");
        Spanned tv2Str = Html.fromHtml("用支付宝支付立减<font color='#ff0000'><strong><big>"
                + 3 +
                "</big></strong></font>元哦~");
        tag_name.setText(tv2Str);
        btn_ok.setText("使用支付宝付款");


        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                setSelectStatus(3);
                Iterator<Entry<Integer, Boolean>> iterator = payTypeMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<Integer, Boolean> entry = iterator.next();
                    if (entry.getValue()) {
                        gotoPay(entry.getKey(), null);
                        break;
                    }
                }

            }
        });
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });


        mDialog.setCanceledOnTouchOutside(false);
        mDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(mDialog);
        showZFBthishi = true;

    }

}
