package com.yssj.ui.activity.shopdetails;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
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
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.activity.GroupsDetailsActivity;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.ShopCartNewNewActivity;
import com.yssj.ui.activity.WithdrawalLimitActivity;
import com.yssj.ui.activity.infos.SetMyPayPassActivity;
import com.yssj.ui.activity.infos.StatusInfoActivity;
import com.yssj.ui.activity.main.FilterResultActivity;
import com.yssj.ui.activity.main.IndianaListActivity;
import com.yssj.ui.activity.main.SignGroupShopActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.PayErrorDialog;
import com.yssj.ui.fragment.orderinfo.OrderDetailsActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.YunYingTongJi;
import com.yssj.wxpay.WxPayUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 收银台支付
 *
 * @author Administrator
 */
public class MealPaymentActivity extends BasicActivity implements
        OnClickListener, OnWxpayFinish {
    private LinearLayout llWalletPay;
    private LinearLayout llQuickPay;
    private LinearLayout llWXinPay;
    private LinearLayout llAliPay, btnPay;
    private LinearLayout llUnionPay;
    private LinearLayout llMydeayPay;
    private ImageView ivWalletPay, ivQuickPay, ivWXinPay, ivAliPay, ivUnionPay,
            ivMydearPay;
    private TextView tvTotalAccount;
    private final int SDK_PAY_FLAG = 1;
    private ImageView imgCancle;
    private HashMap<Integer, Boolean> payTypeMap;
    private ArrayList<ImageView> imgSelectList;
    private IWXAPI msgApi;
    private String orderNo;
    private HashMap<String, Object> resultMap;
    private double totalAccount;
    private PayPasswordCustomDialog customDialog;
    private InputDialogListener inputDialogListener;
    private boolean isMulti;

    public static MealPaymentActivity instance;

    private RelativeLayout rel_pay_success, rel_nomal, root;
    private TextView tv_price;

    private String pos;
    private boolean isDuobao;

    private String signShopDetail;
    private String sign_huodong;
    private int signType;// 代表 几元包邮
    private Order order;
    private TimeCount timeCount;
    private long nowTime;
    private Bundle bundle;
    private String CanYunumber;//夺宝商品的 参与号码
    //	private int id;
//	private int nextID;
    private int now_type_id;
    private int now_type_id_value;
    private int next_type_id;
    private int next_type_id_value;
    //是否是提现一元夺宝-------一元夺宝
    private boolean moneyIndiana;

    private TextView tshengyuTime;
    private TextView mTvPayTimes;// 支付倒计时
    private LinearLayout mLlFailture;// 支付失败的显示
    private String shop_code = "";//活动商品商品编号
    private boolean isSecondClick;//限制立即支付按钮的点击
    private boolean mIsTwoGroup = false;
    private int groupFlag = 0;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
//        aBar.hide();
        setContentView(R.layout.activity_payment);
        context = this;
        groupFlag = getIntent().getIntExtra("groupFlag", 0);
        mIsTwoGroup = getIntent().getBooleanExtra("mIsTwoGroup", false);
        WXPayEntryActivity.setOnWxpayFinish(this);// 实现微信支付成功之后调分享界面接口
        msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp(WxPayUtil.APP_ID);
        resultMap = (HashMap<String, Object>) getIntent().getSerializableExtra(
                "result");
        totalAccount = getIntent().getDoubleExtra("totlaAccount", 0);
        LogYiFu.e("的的的", resultMap + "");
        pos = getIntent().getStringExtra("pos");
        moneyIndiana = getIntent().getBooleanExtra("moneyIndiana", false);
        shop_code = getIntent().getStringExtra("shop_code");
        if (null != resultMap) {
            orderNo = (String) resultMap.get("order_code");
        } else {
            orderNo = getIntent().getStringExtra("order_code");
        }

        timeCount = new TimeCount(1000, 1000);
        // 此处从未付款订单跳转
        isMulti = getIntent().getBooleanExtra("isMulti", false);
        isDuobao = getIntent().getBooleanExtra("isDuobao", false);
        signShopDetail = getIntent().getStringExtra("signShopDetail");
        sign_huodong = getIntent().getStringExtra("sign_huodong");
//		if(isDuobao){
////			signType =  Integer.parseInt(String.valueOf(resultMap.get("price")));
//			String[] str  = (String.valueOf(resultMap.get("price"))).split("\\.");
//			String type  = str[0];
//			signType = Integer.parseInt(type);
//		}else {
//			signType = getIntent().getIntExtra("signType", 0);
//		}
        signType = getIntent().getIntExtra("signType", 0);


        now_type_id = Integer.valueOf(SharedPreferencesUtil.getStringData(context, "now_type_id", "0"));
        now_type_id_value = Integer.valueOf(SharedPreferencesUtil.getStringData(context, "now_type_id_value", "0"));
        next_type_id = Integer.valueOf(SharedPreferencesUtil.getStringData(context, "next_type_id", "0"));
        next_type_id_value = Integer.valueOf(SharedPreferencesUtil.getStringData(context, "next_type_id_value", "0"));

        initview();
        getPayMap();
        if (mTask2 != null) {
            mTask2.cancel();
            mTask2 = new MyTimerTask2();
        } else {
            mTask2 = new MyTimerTask2();
        }
        timer2.schedule(mTask2, 0, 1000); // timeTask
    }

    // 得到一个map集合 存放选择支付类型
    private void getPayMap() {
        payTypeMap = new HashMap<Integer, Boolean>();
        int select = 0;
        if (ivWalletPay.isSelected()) {

        } else if (ivWXinPay.isSelected()) {
            select = 2;
        }
        payTypeMap.put(select, true);
        for (int i = 0; i < 6; i++) {
            if (i != select) {
                payTypeMap.put(i, false);
            }
        }

    }

    private void initview() {
        mLlFailture = (LinearLayout) findViewById(R.id.payment_ll_pay_failture);
        mTvPayTimes = (TextView) findViewById(R.id.tv_pay_times);
        tshengyuTime = (TextView) findViewById(R.id.tshengyuTime);
        root = (RelativeLayout) findViewById(R.id.root);
        root.setBackgroundColor(Color.WHITE);
        llWalletPay = (LinearLayout) findViewById(R.id.ll_wallet);
        llQuickPay = (LinearLayout) findViewById(R.id.ll_quick_pay);
        llWXinPay = (LinearLayout) findViewById(R.id.ll_wxin_pay);
        llAliPay = (LinearLayout) findViewById(R.id.ll_ali_pay);
        llUnionPay = (LinearLayout) findViewById(R.id.ll_union_pay);
        llMydeayPay = (LinearLayout) findViewById(R.id.ll_mydear_pay);
        ivWalletPay = (ImageView) findViewById(R.id.iv_wallet);

        ivQuickPay = (ImageView) findViewById(R.id.iv_quick_pay);
        ivWXinPay = (ImageView) findViewById(R.id.iv_wxin_pay);
        ivAliPay = (ImageView) findViewById(R.id.iv_ali_pay);
        ivUnionPay = (ImageView) findViewById(R.id.iv_union_pay);
        ivMydearPay = (ImageView) findViewById(R.id.iv_mydear_pay);
        if ("SignShopDetail".equals(signShopDetail) || isDuobao || "sign_huodong".equals(sign_huodong)) {
            llWalletPay.setVisibility(View.GONE);
            ivWXinPay.setSelected(true);
        } else {
            ivWalletPay.setSelected(true);
        }
        // 勾选按钮集合
        imgSelectList = new ArrayList<ImageView>();
        imgSelectList.add(ivWalletPay);
        imgSelectList.add(ivQuickPay);
        imgSelectList.add(ivWXinPay);
        imgSelectList.add(ivAliPay);
        imgSelectList.add(ivUnionPay);
        imgSelectList.add(ivMydearPay);

        tvTotalAccount = (TextView) findViewById(R.id.tv_total_account);// 共计支付
        String price = new java.text.DecimalFormat("#0.00")
                .format(totalAccount);
        tvTotalAccount.setText("共计:¥" + price);
        btnPay = (LinearLayout) findViewById(R.id.btn_pay); // 付款
        imgCancle = (ImageView) findViewById(R.id.iv_cancle);// 取消返回

        rel_pay_success = (RelativeLayout) findViewById(R.id.rel_pay_success);
        rel_nomal = (RelativeLayout) findViewById(R.id.rel_nomal);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_price.setText("¥" + price);
        setOnclick();
    }

    // 设置点击监听
    private void setOnclick() {
        llWalletPay.setOnClickListener(this);
        llQuickPay.setOnClickListener(this);
        llWXinPay.setOnClickListener(this);
        llAliPay.setOnClickListener(this);
        llUnionPay.setOnClickListener(this);
        llMydeayPay.setOnClickListener(this);
        ivWalletPay.setOnClickListener(this);
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
            case R.id.ll_wallet:
            case R.id.iv_wallet:
                setSelectStatus(0);
                break;
            case R.id.ll_quick_pay:
            case R.id.iv_quick_pay:
                setSelectStatus(1);
                break;
            case R.id.ll_wxin_pay:
            case R.id.iv_wxin_pay:
                setSelectStatus(2);
                break;
            case R.id.ll_ali_pay:
            case R.id.iv_ali_pay:
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
//			YunYingTongJi.yunYingTongJi(this, 113);
                Iterator<Entry<Integer, Boolean>> iterator = payTypeMap.entrySet()
                        .iterator();
                while (iterator.hasNext()) {
                    Entry<Integer, Boolean> entry = iterator.next();
                    if (entry.getValue()) {
                        gotoPay(entry.getKey(), view);
                        break;
                    }
                }
                break;
            case R.id.iv_cancle:// 取消支付 返回上一层

//			if (isDuobao) {
////				Intent intent = new Intent(MealPaymentActivity.this,
////						StatusInfoActivity.class);
////				startActivity(intent);
//				getOrderInfo();
//
//				MealPaymentActivity.this.finish();
//				return;
//			}
//
//			if (!isMulti) {
//				getOrderInfo();
//			} else {
//				Intent intent = new Intent(MealPaymentActivity.this,
//						StatusInfoActivity.class);
//				intent.putExtra("index", 1);
//				startActivity(intent);
//
//				MealPaymentActivity.this.finish();
//				if (null != SubmitMultiShopActivty.instance) {
//					SubmitMultiShopActivty.instance.finish();
//				}
//				if (null != SubmitOrderActivity.instance) {
//					SubmitOrderActivity.instance.finish();
//				}
//			}

                if(moneyIndiana){
                    finish();
                }else{
                    noticeDialog();

                }


                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isSecondClick = false;
        // MobclickAgent.onPageStart("PaymentActivity");
        // MobclickAgent.onResume(this);
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
                Intent intent = new Intent(MealPaymentActivity.this,
                        SetMyPayPassActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();

    }

    private void checkMyWalletPayPass(View v) {
        // TODO Auto-generated method stub
        new SAsyncTask<Void, Void, CheckPwdInfo>(MealPaymentActivity.this, v,
                R.string.wait) {

            @Override
            protected CheckPwdInfo doInBackground(FragmentActivity context,
                                                  Void... params) throws Exception {
                return ComModel.checkPWD(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         CheckPwdInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                isSecondClick = false;
                if (null == e) {
                    if (result != null && "1".equals(result.getStatus())
                            && "1".equals(result.getFlag())) {
                        customDialog();
                    } else if (result != null && "1".equals(result.getStatus())
                            && "2".equals(result.getFlag())) {
                        customDialog = new PayPasswordCustomDialog(
                                MealPaymentActivity.this, R.style.mystyle,
                                R.layout.pay_customdialog, "请输入支付密码",
                                new java.text.DecimalFormat("#0.00")
                                        .format(totalAccount));
                        inputDialogListener = new InputDialogListener() {

                            @Override
                            public void onOK(String pwd) {
                                // 给密码文本框设置密码
                                isSecondClick = true;
                                walletPayOrder(orderNo, pwd, isMulti);
                            }

                            @Override
                            public void onCancel() {
                                // TODO Auto-generated method stub

                                // getOrderInfo();
                                if (!isMulti) {
                                    getOrderInfo();
                                } else {
                                    Intent intent = new Intent(
                                            MealPaymentActivity.this,
                                            StatusInfoActivity.class);
                                    intent.putExtra("index", 1);
                                    startActivity(intent);

                                    MealPaymentActivity.this.finish();
                                    if (null != MealSubmitOrderActivity.instance) {
                                        MealSubmitOrderActivity.instance
                                                .finish();
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

    private void getOrderInfo() {
        new SAsyncTask<Void, Void, Order>(MealPaymentActivity.this,
                R.string.wait) {

            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected Order doInBackground(FragmentActivity context,
                                           Void... params) throws Exception {
                // TODO Auto-generated method stub
                LogYiFu.e("ywerwer", ComModel2.getMyOrder(context, orderNo) + "");
                return ComModel2.getMyOrder(context, orderNo);
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         Order result, Exception e) {
                super.onPostExecute(context, result, e);

                LogYiFu.e("werwer", result + "");
                if (null == e) {
                    order = result;// 获取订单
                    if ("SignShopDetail".equals(signShopDetail)) {
                        order.setShop_from(3);//签到抢购订单
                        order.setSignType(signType);
                    }
                    if (isDuobao) {
                        order.setShop_from(4);
                        order.setSignType(signType);
                    }
                    Intent intent = new Intent(MealPaymentActivity.this,
                            OrderDetailsActivity.class);
                    bundle = new Bundle();
                    bundle.putSerializable("order", result);
//					getSystemTime();
                    if ("sign_huodong".equals(sign_huodong)) {
                        intent.putExtra("sign_huodong", sign_huodong);
                        intent.putExtra("shop_code", "" + shop_code);
                    }
                    intent.putExtra("nowTime", System.currentTimeMillis());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    MealPaymentActivity.this.finish();
                    if (null != MealSubmitOrderActivity.instance) {
                        MealSubmitOrderActivity.instance.finish();
                    }
                } else {
                    Intent intent = new Intent(MealPaymentActivity.this, StatusInfoActivity.class);
                    intent.putExtra("index", 1);
                    startActivity(intent);

                    MealPaymentActivity.this.finish();
                    if (null != SubmitMultiShopActivty.instance) {
                        SubmitMultiShopActivty.instance.finish();
                    }
                    if (null != SubmitOrderActivity.instance) {
                        SubmitOrderActivity.instance.finish();
                    }

                }
            }

        }.execute();
    }

    private void getSystemTime() {
        new SAsyncTask<Void, Void, HashMap<String, Object>>(
                (FragmentActivity) MealPaymentActivity.this, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(
                    FragmentActivity context, Void... params) throws Exception {
                return ComModel2.getSystemTime(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);

                if (null == e && result != null) {
                    nowTime = (Long) result.get("now");

                    Intent intent = new Intent(MealPaymentActivity.this,
                            OrderDetailsActivity.class);
                    if ("sign_huodong".equals(sign_huodong)) {
                        intent.putExtra("sign_huodong", sign_huodong);
                    }
                    intent.putExtras(bundle);
                    intent.putExtra("nowTime", nowTime);
                    startActivity(intent);
                    MealPaymentActivity.this.finish();
                    if (null != MealSubmitOrderActivity.instance) {
                        MealSubmitOrderActivity.instance.finish();
                    }

                }
            }

        }.execute();
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
            /*
			 * if (null != listGoods) { if (listGoods.size() > 1) {
			 * getPrepayId(YUrl.WX_PAY_MULTI); } else {
			 * getPrepayId(YUrl.WX_PAY_SINGLE); } } else {
			 */
                ToastUtil.showShortText(MealPaymentActivity.this, "加载中...");
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
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected HashMap<String, String> doInBackground(
                    FragmentActivity context, Void... params) throws Exception {
                // TODO Auto-generated method stub
                return ComModel2.getAliParam(context, orderNo);
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         HashMap<String, String> result, Exception e) {
                // TODO Auto-generated method stub
                if (e == null) {
                    if (result.get("status").equals("1")) {
                        if (isMulti) {
                            aliPay(null, result.get("pay_url")
                                            + YUrl.ALI_NOTIFY_URL_MULTI, orderNo,
                                    result.get("partner"),
                                    result.get("seller"),
                                    result.get("sign_type"),
                                    result.get("private_key"));
                        } else {
                            aliPay(null, result.get("pay_url")
                                            + YUrl.ALI_NOTIFY_URL_SINGLE, orderNo,
                                    result.get("partner"),
                                    result.get("seller"),
                                    result.get("sign_type"),
                                    result.get("private_key"));
                        }
                    }
                }
                super.onPostExecute(context, result, e);
            }

        }.execute();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(moneyIndiana){
                finish();
            }else{
                noticeDialog();

            }

			/*
												 * // getOrderInfo(); if
												 * (!isMulti) { getOrderInfo();
												 * } else { Intent intent = new
												 * Intent
												 * (MealPaymentActivity.this,
												 * StatusInfoActivity.class);
												 * intent.putExtra("index", 1);
												 * startActivity(intent);
												 * 
												 * MealPaymentActivity.this.finish
												 * (); if (null !=
												 * MealSubmitOrderActivity
												 * .instance) {
												 * MealSubmitOrderActivity
												 * .instance.finish(); } if
												 * (null !=
												 * SubmitOrderActivity.instance)
												 * {
												 * SubmitOrderActivity.instance
												 * .finish(); } }
												 */
        }
        return false;
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
    }

    /**
     * 支付宝支付
     *
     * @param v
     * @param order_code
     */
    private void aliPay(View v, String notify_url, String order_code,
                        String partner, String seller, String sign_type, String private_key) {

        StringBuffer sb = new StringBuffer();
        String subject = Config.pay_subject;
        String content = "套餐1";
        // String price = sum + "";
        // shop.getShop_se_price() + "";

        // 订单
        String orderInfo = PayUtil.getOrderInfo(subject, content, order_code,
                totalAccount + "", notify_url, partner, seller);

        // 对订单做RSA 签名
        String sign = PayUtil.sign(orderInfo, private_key);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + PayUtil.getSignType(sign_type);

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(MealPaymentActivity.this);
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
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // Toast.makeText(PaymentActivity.this, "支付成功",
                        // Toast.LENGTH_SHORT).show();
                        // showShareDialog();
                        updatePayStatus(orderNo, 1);
                        ToastUtil.showShortText(MealPaymentActivity.this, "支付成功");
                        if (MealSubmitOrderActivity.instance != null) {
                            MealSubmitOrderActivity.instance.finish();
                        }
                        // MealPaymentActivity.this.finish();
                        if ("SignShopDetail".equals(signShopDetail) || isDuobao) {

                        } else {
                            timeCount.start();
                        }
                        rel_pay_success.setVisibility(View.VISIBLE);
                        TongJiUtils.TongJi(context, 13 + "");//支付成功 统计到达次数
                        LogYiFu.e("TongJiNew", 13 + "");
                        if (ShopCartNewNewActivity.instance != null) {
                            ShopCartNewNewActivity.instance.finish();
                            ShopCartNewNewActivity.instance = null;
                        }
//					YunYingTongJi.yunYingTongJi(MealPaymentActivity.this, 114);
                        SharedPreferencesUtil.saveStringData(MealPaymentActivity.this, Pref.TONGJI_TYPE, "1056");
                        rel_nomal.setVisibility(View.GONE);
                        SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
                        SignComplete(orderNo, "1");// 签到 支付成功 返回签到页面
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(MealPaymentActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(MealPaymentActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();
                            mLlFailture.setVisibility(View.VISIBLE);
                            mTvPayTimes.setVisibility(View.GONE);

//						if (!isMulti) {
//							getOrderInfo();
//						} else {
//							
//							
//							Intent intent = new Intent(
//									MealPaymentActivity.this,
//									StatusInfoActivity.class);
//							intent.putExtra("index", 1);
//							startActivity(intent);
//
//							MealPaymentActivity.this.finish();
//							if (null != MealSubmitOrderActivity.instance) {
//								MealSubmitOrderActivity.instance.finish();
//							}
//						}

                        }
                    }
                    break;
                }
            }
        }

        ;
    };


    /****
     * 通过我的钱包来付款
     *
     * @param order_code
     * @param pwd
     */
    private void walletPayOrder(String order_code, String pwd,
                                final boolean isMulti) { // true 多订单 false 单订单
        new SAsyncTask<String, Void, ReturnInfo>(MealPaymentActivity.this,
                null, R.string.wait) {
            @Override
            protected ReturnInfo doInBackground(FragmentActivity context,
                                                String... params) throws Exception {

                if (!isMulti) {
                    return ComModel.walletPayOrder(context, params[0],
                            params[1]);
                } else {
                    return ComModel.walletPayMultiOrder(context, params[0],
                            params[1]);
                }
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         ReturnInfo r, Exception e) {

                if (e != null) {// 查询异常
                    isSecondClick = false;
                    LogYiFu.e("异常 -----", context.getString(R.string.ss));
                    // Toast.makeText(context, "" + e,
                    // Toast.LENGTH_SHORT).show();

					/*
					 * if (!isMulti) { getOrderInfo(); } else { Intent intent =
					 * new Intent(MealPaymentActivity.this,
					 * StatusInfoActivity.class); intent.putExtra("index", 1);
					 * startActivity(intent);
					 * 
					 * MealPaymentActivity.this.finish(); if (null !=
					 * MealSubmitOrderActivity.instance) {
					 * MealSubmitOrderActivity.instance.finish(); }
					 * 
					 * }
					 */

                } else {// 查询商品详情成功，刷新界面

                    int pwdflag = r.getPwdflag();

                    if (pwdflag == 0) { // 支付成功
                        ToastUtil.showShortText(MealPaymentActivity.this,
                                "支付成功");

                        SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
                        // MealPaymentActivity.instance.finish();
                        // MealPaymentActivity.this.finish();
                        // paySuccessTo();
                        timeCount.start();
                        rel_pay_success.setVisibility(View.VISIBLE);
                        TongJiUtils.TongJi(context, 13 + "");//支付成功 统计到达次数
                        LogYiFu.e("TongJiNew", 13 + "");
                        if (ShopCartNewNewActivity.instance != null) {
                            ShopCartNewNewActivity.instance.finish();
                            ShopCartNewNewActivity.instance = null;
                        }
//						YunYingTongJi.yunYingTongJi(MealPaymentActivity.this, 114);
                        SharedPreferencesUtil.saveStringData(MealPaymentActivity.this, Pref.TONGJI_TYPE, "1056");
                        rel_nomal.setVisibility(View.GONE);
//						SignComplete();
                    } else if (pwdflag == 1 || pwdflag == 2 || pwdflag == 3) { // 支付密码错误
                        // customDialog.dismiss();
                        isSecondClick = false;
                        String message = r.getMessage();
                        PayErrorDialog dialog = new PayErrorDialog(context,
                                R.style.DialogStyle1, pwdflag, message);
                        dialog.show();
                    }
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
        new SAsyncTask<String, Void, Map<String, String>>(
                MealPaymentActivity.this, R.string.wait) {

            @Override
            protected Map<String, String> doInBackground(
                    FragmentActivity context, String... params)
                    throws Exception {
                // TODO Auto-generated method stub
                Map<String, String> mapReturn = ComModel2.getPrepayId(context,
                        orderNo, "test", params[0]);
                return mapReturn;
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         Map<String, String> result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);

                if (null == e) {
                    WxPayUtil.sendPayReq(msgApi, WxPayUtil.genPayReq(result),
                            result.get("appid"));
                }
                // finish();
            }

        }.execute(wxPayUrl);
    }

    private void paySuccessTo() {
        // new VersionChangDialog(context, R.style.DialogStyle1).show();

        if (null != ShopDetailsActivity.instance) {
            ShopDetailsActivity.instance.finish();
        }

        if (pos == null) {

        } else if (pos.equals("0") || pos.equals("1")) {
            Intent intent = new Intent(this, FilterResultActivity.class);
            Bundle bundle = new Bundle();
            HashMap<String, Object> map = new HashMap<String, Object>();
            HashMap<String, String> mapString = new HashMap<String, String>();
            mapString.put("_id", "20");
            map.put("fix_price", mapString);
            bundle.putSerializable("condition", map);
            bundle.putString("id", "6");
            bundle.putString("title", "热卖");
            intent.putExtras(bundle);
            // intent.putExtra("checkId", checkId);
            startActivity(intent);
            this.finish();
        } else {
            if (MainMenuActivity.instances != null)
                MainMenuActivity.instances.finish();
            Intent intent = new Intent(this, MainMenuActivity.class);
            intent.putExtra("toYf", "toYf");
            startActivity(intent);
            finish();
            // MealPaymentActivity.instance.finish();
        }
    }

    @Override
    public void onWxpaySuccess() {
        // TODO Auto-generated method stub
        updatePayStatus(orderNo, 2);
        ToastUtil.showShortText(MealPaymentActivity.this, "支付成功");
        if (MealSubmitOrderActivity.instance != null) {
            MealSubmitOrderActivity.instance.finish();
        }
//		else if(SubmitDuobaoOrderActivity.instance!=null){
//			SubmitDuobaoOrderActivity.instance.finish();
//		}else if(SubmitDuobaoOrderActivity.instance!=null){
//			SubmitDuobaoOrderActivity.instance.finish();
//		}
        // MealPaymentActivity.this.finish();
        if ("SignShopDetail".equals(signShopDetail) || isDuobao) {

        } else {
            timeCount.start();
        }
        rel_pay_success.setVisibility(View.VISIBLE);
        TongJiUtils.TongJi(context, 13 + "");//支付成功 统计到达次数
        LogYiFu.e("TongJiNew", 13 + "");
//        if (ShopCartNewNewActivity.instance != null) {
//            ShopCartNewNewActivity.instance.finish();
//            ShopCartNewNewActivity.instance = null;
//        }
		YunYingTongJi.yunYingTongJi(MealPaymentActivity.this, 114);
        SharedPreferencesUtil.saveStringData(MealPaymentActivity.this, Pref.TONGJI_TYPE, "1056");
        rel_nomal.setVisibility(View.GONE);
        SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
        SignComplete(orderNo, "2");
    }

    private void SignComplete(String order_code, String pay_type) {

        if (("SignShopDetail".equals(signShopDetail) || isDuobao) && !Order.signCompleteFlag) {

            if (isDuobao) {
                new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) MealPaymentActivity.this, 0) {
                    @Override
                    protected HashMap<String, String> doInBackground(
                            FragmentActivity context, String... params)
                            throws Exception {
                        return ComModel2.getDuobaoNumber(MealPaymentActivity.this, params[0], Integer.valueOf(params[1]));
                    }

                    protected boolean isHandleException() {
                        return true;
                    }

                    ;

                    @Override
                    protected void onPostExecute(FragmentActivity context,
                                                 HashMap<String, String> result, Exception e) {
                        super.onPostExecute(context, result, e);
                        if (e == null && result != null) {
                            CanYunumber = result.get("data");
                            LogYiFu.e("MealPaymentActivity", "单个订单参与号码：" + CanYunumber);
                            signStatue();
                        } else {
                            LogYiFu.e("MealPaymentActivity", "单个订单参与号码：" + e);
                        }
                    }
                }.execute(order_code, pay_type);
            } else {
                signStatue();
            }
        }
    }

    //改变后台签到状态
    private void signStatue() {
//		Intent intent = new Intent(MealPaymentActivity.this,
//				MainMenuActivity.class);
//		intent.putExtra("signType", signType + "");
//		intent.putExtra("signShopDetail", signShopDetail);
//		intent.putExtra("isDuobao", isDuobao);
//		intent.putExtra("CanYunumber", CanYunumber);
////		intent.putExtra("Signidd", id);
////		intent.putExtra("SignnextID", nextID);
//		intent.putExtra("now_type_id", now_type_id);
//		intent.putExtra("now_type_id_value", now_type_id_value);
//		intent.putExtra("next_type_id", next_type_id);
//		intent.putExtra("next_type_id_value", next_type_id_value);

//		Intent intent = new Intent(MealPaymentActivity.this,
//		MainMenuActivity.class);


        if (null != CommonActivity.instance) {
            CommonActivity.instance.finish();
        }
        if (null != ShopDetailsIndianaActivity.instance) {
            ShopDetailsIndianaActivity.instance.finish();
        }
        if (null != IndianaListActivity.instance) {
            IndianaListActivity.instance.finish();
        }


        if (moneyIndiana) {//如果是一元夺宝就跳到一元夺宝商品详情
            Intent intent = new Intent(MealPaymentActivity.this,
                    ShopDetailsMoneyIndianaActivity.class);
            intent.putExtra("CanYunumber", CanYunumber);
            intent.putExtra("shop_code", shop_code);
            intent.putExtra("moneyIndiana", true);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in,
                    android.R.anim.fade_out);

            finish();
            return;
        }


            SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
            Intent intent = new Intent(MealPaymentActivity.this,
                    CommonActivity.class);
            intent.putExtra("signType", signType + "");
            intent.putExtra("signShopDetail", signShopDetail);
            intent.putExtra("isDuobao", isDuobao);
            intent.putExtra("CanYunumber", CanYunumber);
//		intent.putExtra("Signidd", id);
//		intent.putExtra("SignnextID", nextID);
            intent.putExtra("now_type_id", now_type_id);
            intent.putExtra("now_type_id_value", now_type_id_value);
            intent.putExtra("next_type_id", next_type_id);
            intent.putExtra("next_type_id_value", next_type_id_value);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in,
                    android.R.anim.fade_out);




        finish();
//		Order.signCompleteFlag = true;
    }

    @Override
    public void onWxpayFailed() {
        // TODO Auto-generated method stub
        if (null != WXPayEntryActivity.instance)
            WXPayEntryActivity.instance.finish();
        mLlFailture.setVisibility(View.VISIBLE);
        mTvPayTimes.setVisibility(View.GONE);
        // getOrderInfo();
//		if (!isMulti) {
//			getOrderInfo();
//		} else {
//			Intent intent = new Intent(MealPaymentActivity.this,
//					StatusInfoActivity.class);
//			intent.putExtra("index", 1);
//			startActivity(intent);
//
//			MealPaymentActivity.this.finish();
//			if (null != MealSubmitOrderActivity.instance) {
//				MealSubmitOrderActivity.instance.finish();
//			}
//		}
    }

    // 计时器
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔

        }

        @Override
        public void onFinish() {// 计时完毕时触发
            TongJiUtils.TongJi(context, 113 + "");
            LogYiFu.e("TongJiNew", 113 + "");
//			paySuccessTo();
            if (null != ShopDetailsActivity.instance) {
                ShopDetailsActivity.instance.finish();
            }
            if (groupFlag == 0) {
//				if (MainMenuActivity.instances != null)
//					MainMenuActivity.instances.finish();
//				Intent intent = new Intent(context, MainMenuActivity.class);
//				intent.putExtra("toYf", "toYf");
//				intent.putExtra("mIsTwoGroup", mIsTwoGroup);
//				startActivity(intent);

                boolean isMad = SharedPreferencesUtil.getBooleanData(context, Pref.ISMADMONDAY, false);
                if (isMad) {
                    //疯狂星期一 支付成功后跳转到 抽奖
                    SharedPreferencesUtil.saveStringData(context,Pref.PAYSUCCESSDIALOG,""+totalAccount);
                    Intent intent = new Intent(context, WithdrawalLimitActivity.class);
                    intent.putExtra("isFromPaySuccess", true);
                    intent.putExtra("payLotteryNumber", (int) (totalAccount % 5 == 0 ? totalAccount / 5 : totalAccount / 5 + 1));//通过订单金额  算的获得的疯狂抽奖次数
                    startActivity(intent);
                } else {
                    SharedPreferencesUtil.saveStringData(context,Pref.PAYSUCCESSDIALOG,""+totalAccount);
                    Intent intent2 = new Intent(context, WithdrawalLimitActivity.class);
//					if(recLen_guide>0){
//						intent.putExtra("is_guidPay",true);
//					}else{
//						intent.putExtra("is_guidPay",false);
//					}
                    intent2.putExtra("isFromPaySuccess", true);
                    intent2.putExtra("payYiDouNumber", (int) (Math.ceil(totalAccount)));//通过订单金额  算的获得的衣豆
                    startActivity(intent2);
                }
                if (SignGroupShopActivity.instance != null) {
                    SignGroupShopActivity.instance.finish();
                }
                MealPaymentActivity.this.finish();
            } else {
                if (SignGroupShopActivity.instance != null) {
                    SignGroupShopActivity.instance.finish();
                }
                MealPaymentActivity.this.finish();
                Intent intent = new Intent(MealPaymentActivity.this, GroupsDetailsActivity.class);
                intent.putExtra("completeStatus", 5);
                startActivity(intent);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示

        }
    }

    private void updatePayStatus(final String order_code, final int buy_type) {
        new SAsyncTask<Void, Void, ReturnInfo>(MealPaymentActivity.this) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context,
                                                Void... params) throws Exception {
                return ComModel.updatePayStatus2(context, order_code, buy_type);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
            }

        }.execute();
    }


    private long recLen2 = 24 * 60 * 60 * 1000;
    Timer timer2 = new Timer();
    private MyTimerTask2 mTask2;

    // 倒计时
    public class MyTimerTask2 extends TimerTask {

        @Override
        public void run() {

            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen2 -= 1000;
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
                    if (recLen2 <= 0) {
                        timer2.cancel();

                        Intent intent = new Intent(MealPaymentActivity.this, ShopCartNewNewActivity.class);
                        startActivity(intent);
                        MealPaymentActivity.this.finish();
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
        final Dialog dialog = new Dialog(context, R.style.invate_dialog_style);
//        View view = View.inflate(context, R.layout.payment_notice__dialog, null);
        View view = View.inflate(context, R.layout.dialog_pay_back, null);
        TextView mConfrim = (TextView) view.findViewById(R.id.confrim);
        TextView mDismiss = (TextView) view.findViewById(R.id.dismiss);

        mDismiss.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 把这个对话框取消掉
                Iterator<Entry<Integer, Boolean>> iterator = payTypeMap.entrySet()
                        .iterator();
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
//				if (isDuobao) {
////					Intent intent = new Intent(MealPaymentActivity.this,
////							StatusInfoActivity.class);
////					startActivity(intent);
//					getOrderInfo();
//
//					MealPaymentActivity.this.finish();
//					return;
//				}

                if (!isMulti) {
                    getOrderInfo();
                } else {
                    Intent intent = new Intent(MealPaymentActivity.this,
                            StatusInfoActivity.class);
                    intent.putExtra("index", 1);
                    startActivity(intent);

                    MealPaymentActivity.this.finish();
                    if (null != SubmitMultiShopActivty.instance) {
                        SubmitMultiShopActivty.instance.finish();
                    }
                    if (null != SubmitOrderActivity.instance) {
                        SubmitOrderActivity.instance.finish();
                    }
                }
                dialog.dismiss();
            }
        });

        // 创建自定义样式dialog
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.setCancelable(false);
        dialog.show();
    }
}
