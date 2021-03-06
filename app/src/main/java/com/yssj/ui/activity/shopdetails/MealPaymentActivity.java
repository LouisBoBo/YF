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
 * ???????????????
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
    private int signType;// ?????? ????????????
    private Order order;
    private TimeCount timeCount;
    private long nowTime;
    private Bundle bundle;
    private String CanYunumber;//??????????????? ????????????
    //	private int id;
//	private int nextID;
    private int now_type_id;
    private int now_type_id_value;
    private int next_type_id;
    private int next_type_id_value;
    //???????????????????????????-------????????????
    private boolean moneyIndiana;

    private TextView tshengyuTime;
    private TextView mTvPayTimes;// ???????????????
    private LinearLayout mLlFailture;// ?????????????????????
    private String shop_code = "";//????????????????????????
    private boolean isSecondClick;//?????????????????????????????????
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
        WXPayEntryActivity.setOnWxpayFinish(this);// ???????????????????????????????????????????????????
        msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp(WxPayUtil.APP_ID);
        resultMap = (HashMap<String, Object>) getIntent().getSerializableExtra(
                "result");
        totalAccount = getIntent().getDoubleExtra("totlaAccount", 0);
        LogYiFu.e("?????????", resultMap + "");
        pos = getIntent().getStringExtra("pos");
        moneyIndiana = getIntent().getBooleanExtra("moneyIndiana", false);
        shop_code = getIntent().getStringExtra("shop_code");
        if (null != resultMap) {
            orderNo = (String) resultMap.get("order_code");
        } else {
            orderNo = getIntent().getStringExtra("order_code");
        }

        timeCount = new TimeCount(1000, 1000);
        // ??????????????????????????????
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

    // ????????????map?????? ????????????????????????
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
        // ??????????????????
        imgSelectList = new ArrayList<ImageView>();
        imgSelectList.add(ivWalletPay);
        imgSelectList.add(ivQuickPay);
        imgSelectList.add(ivWXinPay);
        imgSelectList.add(ivAliPay);
        imgSelectList.add(ivUnionPay);
        imgSelectList.add(ivMydearPay);

        tvTotalAccount = (TextView) findViewById(R.id.tv_total_account);// ????????????
        String price = new java.text.DecimalFormat("#0.00")
                .format(totalAccount);
        tvTotalAccount.setText("??????:??" + price);
        btnPay = (LinearLayout) findViewById(R.id.btn_pay); // ??????
        imgCancle = (ImageView) findViewById(R.id.iv_cancle);// ????????????

        rel_pay_success = (RelativeLayout) findViewById(R.id.rel_pay_success);
        rel_nomal = (RelativeLayout) findViewById(R.id.rel_nomal);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_price.setText("??" + price);
        setOnclick();
    }

    // ??????????????????
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
            case R.id.btn_pay:// ?????????
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
            case R.id.iv_cancle:// ???????????? ???????????????

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
        // ???????????????????????????
        View view = View.inflate(this, R.layout.payback_esc_apply_dialog, null);
        TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
        tv_des.setText("???????????????????????????????????????????????????");

        Button ok = (Button) view.findViewById(R.id.ok);
        ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
        Button cancel = (Button) view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // ???????????????????????????
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
                                R.layout.pay_customdialog, "?????????????????????",
                                new java.text.DecimalFormat("#0.00")
                                        .format(totalAccount));
                        inputDialogListener = new InputDialogListener() {

                            @Override
                            public void onOK(String pwd) {
                                // ??????????????????????????????
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
                        ToastUtil.showLongText(context, "??????????????????~~~");
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
                    order = result;// ????????????
                    if ("SignShopDetail".equals(signShopDetail)) {
                        order.setShop_from(3);//??????????????????
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

    // ??????
    private void gotoPay(Integer position, View v) {
        switch (position) {
            case 0: // ???????????? ????????????
                if (isSecondClick) {
                    break;
                }
                isSecondClick = true;
                checkMyWalletPayPass(v);
                break;
            case 1: // ????????????

                break;
            case 2: // ????????????
            /*
			 * if (null != listGoods) { if (listGoods.size() > 1) {
			 * getPrepayId(YUrl.WX_PAY_MULTI); } else {
			 * getPrepayId(YUrl.WX_PAY_SINGLE); } } else {
			 */
                ToastUtil.showShortText(MealPaymentActivity.this, "?????????...");
                if (isMulti) {
                    getPrepayId(YUrl.WX_PAY_MULTI);
                } else {
                    getPrepayId(YUrl.WX_PAY_SINGLE);
                }
                // }
                break;
            case 3: // ???????????????
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
            case 4: // ????????????

                break;
            case 5: // ??????????????????

                break;

            default:
                break;
        }
    }

    /**
     * ???????????????????????????
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

    // ??????????????????
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
     * ???????????????
     *
     * @param v
     * @param order_code
     */
    private void aliPay(View v, String notify_url, String order_code,
                        String partner, String seller, String sign_type, String private_key) {

        StringBuffer sb = new StringBuffer();
        String subject = Config.pay_subject;
        String content = "??????1";
        // String price = sum + "";
        // shop.getShop_se_price() + "";

        // ??????
        String orderInfo = PayUtil.getOrderInfo(subject, content, order_code,
                totalAccount + "", notify_url, partner, seller);

        // ????????????RSA ??????
        String sign = PayUtil.sign(orderInfo, private_key);
        try {
            // ?????????sign ???URL??????
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // ???????????????????????????????????????????????????
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + PayUtil.getSignType(sign_type);

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // ??????PayTask ??????
                PayTask alipay = new PayTask(MealPaymentActivity.this);
                // ???????????????????????????????????????
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // ??????????????????
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // ??????resultStatus ??????9000???????????????????????????????????????????????????????????????????????????
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // Toast.makeText(PaymentActivity.this, "????????????",
                        // Toast.LENGTH_SHORT).show();
                        // showShareDialog();
                        updatePayStatus(orderNo, 1);
                        ToastUtil.showShortText(MealPaymentActivity.this, "????????????");
                        if (MealSubmitOrderActivity.instance != null) {
                            MealSubmitOrderActivity.instance.finish();
                        }
                        // MealPaymentActivity.this.finish();
                        if ("SignShopDetail".equals(signShopDetail) || isDuobao) {

                        } else {
                            timeCount.start();
                        }
                        rel_pay_success.setVisibility(View.VISIBLE);
                        TongJiUtils.TongJi(context, 13 + "");//???????????? ??????????????????
                        LogYiFu.e("TongJiNew", 13 + "");
                        if (ShopCartNewNewActivity.instance != null) {
                            ShopCartNewNewActivity.instance.finish();
                            ShopCartNewNewActivity.instance = null;
                        }
//					YunYingTongJi.yunYingTongJi(MealPaymentActivity.this, 114);
                        SharedPreferencesUtil.saveStringData(MealPaymentActivity.this, Pref.TONGJI_TYPE, "1056");
                        rel_nomal.setVisibility(View.GONE);
                        SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
                        SignComplete(orderNo, "1");// ?????? ???????????? ??????????????????
                    } else {
                        // ??????resultStatus ?????????9000??????????????????????????????
                        // ???8000???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(MealPaymentActivity.this, "?????????????????????",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // ??????????????????????????????????????????????????????????????????????????????????????????????????????
                            Toast.makeText(MealPaymentActivity.this, "????????????",
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
     * ???????????????????????????
     *
     * @param order_code
     * @param pwd
     */
    private void walletPayOrder(String order_code, String pwd,
                                final boolean isMulti) { // true ????????? false ?????????
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

                if (e != null) {// ????????????
                    isSecondClick = false;
                    LogYiFu.e("?????? -----", context.getString(R.string.ss));
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

                } else {// ???????????????????????????????????????

                    int pwdflag = r.getPwdflag();

                    if (pwdflag == 0) { // ????????????
                        ToastUtil.showShortText(MealPaymentActivity.this,
                                "????????????");

                        SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
                        // MealPaymentActivity.instance.finish();
                        // MealPaymentActivity.this.finish();
                        // paySuccessTo();
                        timeCount.start();
                        rel_pay_success.setVisibility(View.VISIBLE);
                        TongJiUtils.TongJi(context, 13 + "");//???????????? ??????????????????
                        LogYiFu.e("TongJiNew", 13 + "");
                        if (ShopCartNewNewActivity.instance != null) {
                            ShopCartNewNewActivity.instance.finish();
                            ShopCartNewNewActivity.instance = null;
                        }
//						YunYingTongJi.yunYingTongJi(MealPaymentActivity.this, 114);
                        SharedPreferencesUtil.saveStringData(MealPaymentActivity.this, Pref.TONGJI_TYPE, "1056");
                        rel_nomal.setVisibility(View.GONE);
//						SignComplete();
                    } else if (pwdflag == 1 || pwdflag == 2 || pwdflag == 3) { // ??????????????????
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
     * ????????????
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
            bundle.putString("title", "??????");
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
        ToastUtil.showShortText(MealPaymentActivity.this, "????????????");
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
        TongJiUtils.TongJi(context, 13 + "");//???????????? ??????????????????
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
                            LogYiFu.e("MealPaymentActivity", "???????????????????????????" + CanYunumber);
                            signStatue();
                        } else {
                            LogYiFu.e("MealPaymentActivity", "???????????????????????????" + e);
                        }
                    }
                }.execute(order_code, pay_type);
            } else {
                signStatue();
            }
        }
    }

    //????????????????????????
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


        if (moneyIndiana) {//??????????????????????????????????????????????????????
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

    // ?????????
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// ????????????????????????,????????????????????????

        }

        @Override
        public void onFinish() {// ?????????????????????
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
                    //??????????????? ???????????????????????? ??????
                    SharedPreferencesUtil.saveStringData(context,Pref.PAYSUCCESSDIALOG,""+totalAccount);
                    Intent intent = new Intent(context, WithdrawalLimitActivity.class);
                    intent.putExtra("isFromPaySuccess", true);
                    intent.putExtra("payLotteryNumber", (int) (totalAccount % 5 == 0 ? totalAccount / 5 : totalAccount / 5 + 1));//??????????????????  ?????????????????????????????????
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
                    intent2.putExtra("payYiDouNumber", (int) (Math.ceil(totalAccount)));//??????????????????  ?????????????????????
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
        public void onTick(long millisUntilFinished) {// ??????????????????

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

    // ?????????
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
                            tshengyuTime.setText("" + days + "???" + hours + ":" + minutes + ":" + seconds);
                            mTvPayTimes.setText("" + days + "???" + hours + ":" + minutes + ":" + seconds);
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
                        // tv_time.setText("???????????????");
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
                // ???????????????????????????
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

        // ?????????????????????dialog
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.setCancelable(false);
        dialog.show();
    }
}
