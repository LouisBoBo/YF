package com.yssj.ui.activity.shopdetails;

import android.app.Activity;
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
import android.text.TextUtils;
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
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.WithdrawalLimitActivity;
import com.yssj.ui.activity.infos.SetMyPayPassActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.PayErrorDialog;
import com.yssj.ui.fragment.orderinfo.OrderDetailsActivity;
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
import java.util.Date;
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
 *
 */
public class OrderPaymentActivity extends BasicActivity implements OnClickListener, OnWxpayFinish {
	private LinearLayout llWalletPay;
	private LinearLayout llQuickPay;
	private LinearLayout llWXinPay;
	private LinearLayout llAliPay;
	private LinearLayout llUnionPay, btnPay;
	private LinearLayout llMydeayPay;
	public static MealSubmitOrderActivity instance;
	private ImageView ivWalletPay, ivQuickPay, ivWXinPay, ivAliPay, ivUnionPay, ivMydearPay;
	private TextView tvTotalAccount;
	private final int SDK_PAY_FLAG = 1;
	private ImageView imgCancle;
	private HashMap<Integer, Boolean> payTypeMap;
	private ArrayList<ImageView> imgSelectList;
	private IWXAPI msgApi;
	private String orderNo;
	// private List<ShopCart> listGoods;
	private HashMap<String, Object> resultMap;
	private double totalAccount;
	private PayPasswordCustomDialog customDialog;
	private InputDialogListener inputDialogListener;
	// private YJApplication yjapp;
	private boolean isMulti;
	private List<Order> listOrder;
	private Order order;
	private String signShopDetail;
	private int signType;// 代表 几元包邮
	private boolean isDuobao;
	private List<Order> shareOrders;
	private RelativeLayout root;
	private TimeCount timeCount;
	private RelativeLayout rel_pay_success, rel_nomal;
	private TextView tv_price;
	private String CanYunumber;// 夺宝商品的参与号码
	// private int id;
	// private int nextID;
	private int now_type_id;
	private int now_type_id_value;
	private int next_type_id;
	private int next_type_id_value;

	private String mSingle;
	private String order_common;
	private String order_special;
	private String mPayCount;
	private boolean mIsQulification;// 是否具有余额翻倍的资格
	private int mTwofoldness;// 余额翻倍的倍数
	private long mEndDate;// 余额翻倍的结束时间值
	private int mIsOpen;// 0,没有开启;1，已经开启

	private TextView tshengyuTime;
	private LinearLayout mLlFailture;// 支付失败的显示
	private TextView mTvPayTimes;// 支付倒计时
	private TextView mTvUsefulMoney;// 可使用的余额
	private RelativeLayout mRlNeedPay;// 还需支付
	private TextView mTvNeedMoney;
	private RelativeLayout ll_zhifujine;// 余额支付的显示
	private double mMyMoney = 0;// 我的金钱
	private double tfn_money;// 余额翻倍使用的金额
	private int is_wx = -1;
	private boolean pay_wx = false;// true代表不可以用微信支付，false代表可以用
	private long deadLine = 0;// 订单过期时间
	private long deadLine_guid=0;//引导过期时间
	private Date date;
	private long timeout = 0;// 支付倒计时
	private long nowTime = 0;
	private boolean flagSpecial = false;
	private boolean mealflag = false;// true时代表包含特卖
	private boolean isSecondClick;//限制立即支付按钮的点击
	private boolean mIsTwoGroup=false;//判断是否是自己发起的拼团
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
//		aBar.hide();
		setContentView(R.layout.activity_payment);
		context = this;

		WXPayEntryActivity.setOnWxpayFinish(this);// 实现微信支付成功之后调分享界面接口
		msgApi = WXAPIFactory.createWXAPI(this, null);
		deadLine_guid=Long.parseLong(SharedPreferencesUtil.getStringData(this,YConstance.Pref.YIDOU_HALVE_END_TIMES,"0"));
		mIsQulification = SharedPreferencesUtil.getBooleanData(this, Pref.IS_QULIFICATION, false);
		mIsOpen = Integer.parseInt(SharedPreferencesUtil.getStringData(this, Pref.IS_OPEN, "0"));
		mTwofoldness = Integer.parseInt(SharedPreferencesUtil.getStringData(this, Pref.TWOFOLDNESS, "0"));
		msgApi.registerApp(WxPayUtil.APP_ID);
		resultMap = (HashMap<String, Object>) getIntent().getSerializableExtra("result");
		// listGoods = (List<ShopCart>) getIntent().getSerializableExtra(
		// "listGoods");
		totalAccount = getIntent().getDoubleExtra("totlaAccount", 0);
		mSingle = getIntent().getStringExtra("single");
		order_common = getIntent().getStringExtra("order_common");
		order_special = getIntent().getStringExtra("order_special");
		mPayCount = getIntent().getStringExtra("paycount");
		if (null != resultMap) {
			orderNo = (String) resultMap.get("order_code");
		} else {
			orderNo = getIntent().getStringExtra("order_code");
		}
		timeCount = new TimeCount(1500, 500);
		// 此处从未付款订单跳转
		isMulti = getIntent().getBooleanExtra("isMulti", false);
		listOrder = (List<Order>) getIntent().getSerializableExtra("listOrder");
		for (int i = 0; i < listOrder.size(); i++) {
			Order order2 = listOrder.get(i);
			date = order2.getLast_time();
			timeout = date.getTime();
			if (i == 0) {
				deadLine = timeout;
			}
			if (timeout < deadLine) {
				deadLine = timeout;
			}
		}
		order = (Order) getIntent().getSerializableExtra("order");
		shareOrders = new ArrayList<Order>();
		for (int i = 0; i < listOrder.size(); i++) {
			if (listOrder.get(i).getShop_from() != 1) {
				shareOrders.add(listOrder.get(i));
			}
		}
		isDuobao = getIntent().getBooleanExtra("isDuobao", false);
		signShopDetail = getIntent().getStringExtra("signShopDetail");
		signType = getIntent().getIntExtra("signType", 0);
		// id = Integer.valueOf(SharedPreferencesUtil.getStringData(context,
		// "Signidd", "0"));
		// nextID = Integer.valueOf(SharedPreferencesUtil.getStringData(context,
		// "SignnextID", "0"));
		now_type_id = Integer.valueOf(SharedPreferencesUtil.getStringData(context, "now_type_id", "0"));
		now_type_id_value = Integer.valueOf(SharedPreferencesUtil.getStringData(context, "now_type_id_value", "0"));
		next_type_id = Integer.valueOf(SharedPreferencesUtil.getStringData(context, "next_type_id", "0"));
		next_type_id_value = Integer.valueOf(SharedPreferencesUtil.getStringData(context, "next_type_id_value", "0"));

		initview();
		getPayMap();
		getSystemTime();
		// 查询我的余额
		// queryMyMomeny();
		for (int i = 0; i < listOrder.size(); i++) {
			Order order = listOrder.get(i);
			if (order.getShop_from() == 1||order.getShop_from() == 3||order.getShop_from() == 4||order.getShop_from() == 5) {
				mealflag = true;
				break;
			}
		}

		for (int i = 0; i < listOrder.size(); i++) {
			Order order = listOrder.get(i);
			if (order.getIs_roll()==1) {
				mIsTwoGroup = true;
				break;
			}
		}
//		if (mealflag) {// 包含特卖，夺宝或包邮的付款只能用第三方
//			llWXinPay.setOnClickListener(this);
//			llWalletPay.setVisibility(View.GONE);
//			for (int i = 0; i < 6; i++) {
//				payTypeMap.put(i, false);
//			}
//			ivWalletPay.setSelected(false);
//			ivWXinPay.setSelected(true);
//			payTypeMap.put(2, true);
//
//		}
		llWXinPay.setOnClickListener(this);
		llWalletPay.setVisibility(View.GONE);
		for (int i = 0; i < 6; i++) {
			payTypeMap.put(i, false);
		}
		ivWalletPay.setSelected(false);
		ivWXinPay.setSelected(true);
		payTypeMap.put(2, true);
//		getUserGradle();
		if (mTask2 != null) {
			mTask2.cancel();
			mTask2 = new MyTimerTask2();
		} else {
			mTask2 = new MyTimerTask2();
		}
//		timer2.schedule(mTask2, 0, 1000); // timeTask


		//3.7.2暂时保留支付宝支付
		llWXinPay.setVisibility(View.VISIBLE);

		ivWXinPay.setSelected(true);
		setSelectStatus(2);
//		ivAliPay.setSelected(true);
//		setSelectStatus(3);

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

	@Override
	protected void onResume() {
		super.onResume();
		isSecondClick=false;
		// MobclickAgent.onPageStart("PaymentActivity");
		// MobclickAgent.onResume(this);
		HomeWatcherReceiver.registerHomeKeyReceiver(this);
		SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1055");
		TongJiUtils.TongJi(this, 12+"");
		LogYiFu.e("TongJiNew", 12+"");
	}

	@Override
	protected void onPause() {
		super.onPause();
		// MobclickAgent.onPageEnd("PaymentActivity");
		// MobclickAgent.onPause(this);
		HomeWatcherReceiver.unregisterHomeKeyReceiver(this);
		TongJiUtils.TongJi(this, 112+"");
		LogYiFu.e("TongJiNew", 112+"");

	}

	private void initview() {
		ll_zhifujine = (RelativeLayout) findViewById(R.id.ll_zhifujine);
		mLlFailture = (LinearLayout) findViewById(R.id.payment_ll_pay_failture);
		mTvPayTimes = (TextView) findViewById(R.id.tv_pay_times);
		mTvUsefulMoney = (TextView) findViewById(R.id.tv_useful_money);
		mRlNeedPay = (RelativeLayout) findViewById(R.id.rl_need_pay);
		mTvNeedMoney = (TextView) findViewById(R.id.tv_need_money);
		tshengyuTime = (TextView) findViewById(R.id.tshengyuTime);

		tv_price = (TextView) findViewById(R.id.tv_price);
		rel_pay_success = (RelativeLayout) findViewById(R.id.rel_pay_success);
		rel_nomal = (RelativeLayout) findViewById(R.id.rel_nomal);
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

		ivQuickPay = (ImageView) findViewById(R.id.iv_quick_pay);
		ivWXinPay = (ImageView) findViewById(R.id.iv_wxin_pay);
		ivAliPay = (ImageView) findViewById(R.id.iv_ali_pay);
		ivUnionPay = (ImageView) findViewById(R.id.iv_union_pay);
		ivMydearPay = (ImageView) findViewById(R.id.iv_mydear_pay);
		if ("SignShopDetail".equals(signShopDetail) || isDuobao) {
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
		String price = new java.text.DecimalFormat("#0.00").format(totalAccount);
		tv_price.setText("¥" + price);
		tvTotalAccount.setText("共计:¥" + price);
		btnPay = (LinearLayout) findViewById(R.id.btn_pay); // 付款
		imgCancle = (ImageView) findViewById(R.id.iv_cancle);// 取消返回

		setOnclick();
	}

	// 设置点击监听
	private void setOnclick() {
		// llWalletPay.setOnClickListener(this);
		llQuickPay.setOnClickListener(this);
		// llWXinPay.setOnClickListener(this);
		llAliPay.setOnClickListener(this);
		llUnionPay.setOnClickListener(this);
		llMydeayPay.setOnClickListener(this);
		// ivWalletPay.setOnClickListener(this);
		ivQuickPay.setOnClickListener(this);
		// ivWXinPay.setOnClickListener(this);
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
			// 如果单订单单商品直接分享
			// if (!isMulti) {
			// ShareUtil.getPicPath(listOrder.get(0).getList().get(0)
			// .getShop_code(), null, this);
			// }
			for (int i = 0; i < listOrder.size(); i++) {
				Order order = listOrder.get(i);
				if (order.getShop_from() == 0) {// 只要含有正价的就调统计接口
					YunYingTongJi.yunYingTongJi(this, 113);
					break;
				}
			}
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
			noticeDialog();
			break;
		default:
			break;
		}
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
				Intent intent = new Intent(OrderPaymentActivity.this, SetMyPayPassActivity.class);
				startActivity(intent);
				dialog.dismiss();
			}
		});

		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}

	private void checkMyWalletPayPass(View v) {
		new SAsyncTask<Void, Void, CheckPwdInfo>(OrderPaymentActivity.this, v, R.string.wait) {

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
						// Toast.makeText(this, "钱包支付 ",
						// Toast.LENGTH_LONG).show();
						customDialog = new PayPasswordCustomDialog(OrderPaymentActivity.this, R.style.mystyle,
								R.layout.pay_customdialog, "请输入支付密码",
								new java.text.DecimalFormat("#0.00").format(totalAccount));
						inputDialogListener = new InputDialogListener() {

							@Override
							public void onOK(String pwd) {
								// 给密码文本框设置密码
								// ToastUtil.showLongText(context, "你的密码是：" +
								// text);
								isSecondClick = true;
								walletPayOrder(orderNo, pwd, isMulti);
							}

							@Override
							public void onCancel() {
								OrderPaymentActivity.this.finish();
							}

						};
						// TextView tvAccount = (TextView) customDialog
						// .findViewById(R.id.tv_account);
						// tvAccount.setText(totalAccount + "元");.
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
			if(isSecondClick){
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
			ToastUtil.showShortText(OrderPaymentActivity.this, "加载中...");
			if (isMulti) {
				getPrepayId(YUrl.WX_PAY_MULTI);
			} else {
//				getPrepayId(YUrl.WX_PAY_SINGLE);
				getPrepayId(YUrl.WX_PAY_MULTI);

			}
			// }
			break;
		case 3: // 支付宝支付
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

	/** 获取支付宝所需参数 */
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
		// TODO:
		// &&!isDuobao&&!("signShopDetail".equals("signShopDetail"))
		if (tfn_money > 0 && "single".equals(mSingle) && !isDuobao && !("signShopDetail".equals(signShopDetail))
				|| ((grade != 1&&grade!=0) && flagSpecial)) {// 使用了余额翻倍
			imgSelectList.get(0).setSelected(true);
		}
	}

	/**
	 * 支付宝支付
	 *
	 * @param v
	 * @param order_code
	 * @param partner
	 *            :商户id
	 * @param seller
	 *            :商户账号
	 * @param sign_type
	 *            :加密类型
	 * @param private_key
	 *            :私钥
	 */
	private void aliPay(View v, String notify_url, String order_code, String partner, String seller, String sign_type,
			String private_key, String totalMoney) {

		StringBuffer sb = new StringBuffer();
		/*
		 * if (null != listGoods) { for (int i = 0; i < listGoods.size(); i++) {
		 * ShopCart sc = listGoods.get(i); sb.append(sc.getShop_name()).append(
		 * " "); } } else {
		 */
		for (int i = 0; i < listOrder.size(); i++) {
			Order order = listOrder.get(i);
			sb.append(order.getOrder_name()).append(" ");
		}
		// }
		String subject = Config.pay_subject;
		String content = sb.toString();
		// String price = sum + "";
		// shop.getShop_se_price() + "";
		// TODO:
		// 订单
		String orderInfo = PayUtil.getOrderInfo(subject, content, order_code, totalMoney + "", notify_url, partner,
				seller);

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
				PayTask alipay = new PayTask(OrderPaymentActivity.this);
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
					SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
					// Toast.makeText(PaymentActivity.this, "支付成功",
					// Toast.LENGTH_SHORT).show();
					// showShareDialog();
					updatePayStatusList(orderNo, 1 + "");
					if (shareOrders.size() == 0) {// 判断是否只有
													// 0元购的订单，直接cut掉
						OrderPaymentActivity.this.finish();
						return;
					}
					// if("SignShopDetail".equals(signShopDetail)){
					// for (int i = 0; i < listOrder.size(); i++) {
					// Order order = listOrder.get(i);
					// if((order.getStatus()==1&&order.getShop_from()==3)){
					// //未付款的签到订单或者已经完成签到商品支付
					// order.setShop_from(0);//改变为非签到订单
					// }
					// }
					// }

					if ("SignShopDetail".equals(signShopDetail) || isDuobao) {

					} else {
						timeCount.start();
					}
					rel_pay_success.setVisibility(View.VISIBLE);
					TongJiUtils.TongJi(context, 13+"");//支付成功 统计到达次数
					LogYiFu.e("TongJiNew", 13+"");
					for (int i = 0; i < listOrder.size(); i++) {
						Order order = listOrder.get(i);
						if (order.getShop_from() == 0) {// 只要含有正价的就调统计接口
							YunYingTongJi.yunYingTongJi(OrderPaymentActivity.this, 114);
							break;
						}
					}
//					YunYingTongJi.yunYingTongJi(OrderPaymentActivity.this, 114);
					SharedPreferencesUtil.saveStringData(OrderPaymentActivity.this, Pref.TONGJI_TYPE, "1056");
					rel_nomal.setVisibility(View.GONE);

					if (("SignShopDetail".equals(signShopDetail) || isDuobao) && !Order.signCompleteFlag) {
						if (isDuobao) {
							if (isMulti) {
								getMultiDuobaoNumber(orderNo, "1");
							} else {
								getDuobaoNumber(orderNo, "1");
							}
						} else {
							SignComplete();
						}

					} else if (isDuobao && Order.signCompleteFlag) {
						if (isMulti) {
							getMultiDuobaoNumber(orderNo, "1");
						} else {
							getDuobaoNumber(orderNo, "1");
						}
					}

					// if (!isMulti) {
					// getShopLink();
					// } else {
					// Intent intent = new Intent(OrderPaymentActivity.this,
					// OrderPaymentSuccessActivity.class);
					// intent.putExtra("listOrder", (Serializable) shareOrders);
					// intent.putExtra("order_code", orderNo);
					// startActivity(intent);
					// OrderPaymentActivity.this.finish();
					// }

					if (null != OrderDetailsActivity.instance) {
						OrderDetailsActivity.instance.finish();
					}
					// OrderPaymentActivity.this.setResult(1);
					// finish();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(OrderPaymentActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

					} else {

						mLlFailture.setVisibility(View.VISIBLE);
						mTvPayTimes.setVisibility(View.GONE);
						// noticeDialog();
						// if (mTask2 != null) {
						// mTask2.cancel();
						// mTask2 = new MyTimerTask2();
						// } else {
						// mTask2 = new MyTimerTask2();
						// }
						// timer.schedule(mTask, 0, 1000); // timeTask
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						// Toast.makeText(OrderPaymentActivity.this, "支付失败",
						// Toast.LENGTH_SHORT).show();
						// OrderPaymentActivity.this.finish();
					}
				}
				break;
			}
			}
		};
	};

	/****
	 * 通过我的钱包来付款
	 *
	 * @param order_code
	 * @param pwd
	 */
	private void walletPayOrder(String order_code, String pwd, final boolean isMulti) {
		new SAsyncTask<String, Void, ReturnInfo>(OrderPaymentActivity.this, null, R.string.wait) {
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
				} else {// 查询商品详情成功，刷新界面

					int pwdflag = r.getPwdflag();

					if (pwdflag == 0) { // 支付成功
						SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
						ToastUtil.showShortText(OrderPaymentActivity.this, "支付成功");

						if (shareOrders.size() == 0) {// 判断是否只有 0元购的订单，直接cut掉
							OrderPaymentActivity.this.finish();
							return;
						}
						timeCount.start();
						rel_pay_success.setVisibility(View.VISIBLE);
//						TongJiUtils.TongJi(context, 13+"");//支付成功 统计到达次数
						LogYiFu.e("TongJiNew", 13+"");
						for (int i = 0; i < listOrder.size(); i++) {
							Order order = listOrder.get(i);
							if (order.getShop_from() == 0) {// 只要含有正价的就调统计接口
								YunYingTongJi.yunYingTongJi(OrderPaymentActivity.this, 114);
								break;
							}
						}
//						YunYingTongJi.yunYingTongJi(OrderPaymentActivity.this, 114);
						SharedPreferencesUtil.saveStringData(OrderPaymentActivity.this, Pref.TONGJI_TYPE, "1056");
						rel_nomal.setVisibility(View.GONE);
						// if (!isMulti) {
						// if (listOrder.get(0).getList().size() == 1) {
						//
						// getShopLink();
						// } else {
						// Intent intent = new Intent(
						// OrderPaymentActivity.this,
						// OrderPaymentSuccessActivity.class);
						// intent.putExtra("listOrder",
						// (Serializable) shareOrders);
						// intent.putExtra("order_code", orderNo);
						// startActivity(intent);
						// OrderPaymentActivity.this.finish();
						// }
						// } else {
						// Intent intent = new Intent(OrderPaymentActivity.this,
						// OrderPaymentSuccessActivity.class);
						// intent.putExtra("listOrder", (Serializable)
						// shareOrders);
						// intent.putExtra("order_code", orderNo);
						// startActivity(intent);
						// OrderPaymentActivity.this.finish();
						// }
						// OrderPaymentActivity.this.setResult(1);
						// finish();
						if (null != OrderDetailsActivity.instance) {
							OrderDetailsActivity.instance.finish();
						}

						// MealPaymentActivity.this.finish();
						// paySuccessTo();
						// timeCount.start();
						// rel_pay_success.setVisibility(View.VISIBLE);
						// rel_nomal.setVisibility(View.GONE);
					} else if (pwdflag == 1 || pwdflag == 2 || pwdflag == 3) { // 支付密码错误
						isSecondClick = false;
						String message = r.getMessage();
						customDialog.dismiss();
						PayErrorDialog dialog = new PayErrorDialog(context, R.style.DialogStyle1, pwdflag, message);
						dialog.show();

					}

					// if(shareOrders.size() == 0){//判断是否只有 0元购的订单，直接cut掉
					// OrderPaymentActivity.this.finish();
					// return;
					// }
					//
					// if (!isMulti) {
					// if (listOrder.get(0).getList().size() == 1) {
					//
					// getShopLink();
					// } else {
					// Intent intent = new Intent(
					// OrderPaymentActivity.this,
					// OrderPaymentSuccessActivity.class);
					// intent.putExtra("listOrder",
					// (Serializable) shareOrders);
					// intent.putExtra("order_code", orderNo);
					// startActivity(intent);
					// OrderPaymentActivity.this.finish();
					// }
					// } else {
					// Intent intent = new Intent(OrderPaymentActivity.this,
					// OrderPaymentSuccessActivity.class);
					// intent.putExtra("listOrder", (Serializable) shareOrders);
					// intent.putExtra("order_code", orderNo);
					// startActivity(intent);
					// OrderPaymentActivity.this.finish();
					// }
					// // OrderPaymentActivity.this.setResult(1);
					// // finish();
					// if(null != OrderDetailsActivity.instance){
					// OrderDetailsActivity.instance.finish();
					// }
				}

			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute(order_code, pwd);
	}

	// 得到商品链接
	private void getShopLink() {
		new SAsyncTask<String, Void, HashMap<String, String>>(OrderPaymentActivity.this, R.string.wait) {

			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
					throws Exception {
				return ComModel2.getShopLink(params[0], context, "true");
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result, e);

				if (null == e) {
					if (result.get("status").equals("1")) {
						String[] picList = result.get("shop_pic").split(",");
						if (!TextUtils.isEmpty(result.get("four_pic")) && !result.get("four_pic").equals("null")) {
							download(null, picList, listOrder.get(0).getList().get(0).getShop_code(), result);
						} else {
							ToastUtil.showShortText(context, "支付完成");
							OrderPaymentActivity.this.finish();
						}
					} else if (result.get("status").equals("1050")) {// 表明
						startActivity(new Intent(OrderPaymentActivity.this, NoShareActivity.class)); // 分享已经超过了
						// ToastUtil.showShortText(context, "您已达到了分享次数上限"); //
						// 4次限制
						OrderPaymentActivity.this.finish();
					}
				}

			}

		}.execute(listOrder.get(0).getList().get(0).getShop_code());// 第一个订单的第一个商品
	}

	private void download(View v, final String[] picList, final String shop_code,
			final HashMap<String, String> mapInfos) {
		new SAsyncTask<Void, Void, Void>(OrderPaymentActivity.this, v, R.string.wait) {

			@Override
			protected Void doInBackground(Void... params) {
				File fileDirec = new File(YConstance.savePicPath);
				if (!fileDirec.exists()) {
					fileDirec.mkdir();
				}
				File[] listFiles = new File(YConstance.savePicPath).listFiles();
				if (listFiles.length != 0) {
					LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
					for (File file : listFiles) {
						file.delete();
					}
				}
				// LogYiFu.i("TAG", "piclist=" + picList.length);
				List<String> pics = new ArrayList<String>();
				for (int j = 0; j < picList.length; j++) {
					if (!picList[j].contains("reveal_") && !picList[j].contains("detail_")
							&& !picList[j].contains("real_")) {
						pics.add(picList[j]);
					}
				}
				int j = pics.size() + 1;
				if (pics.size() > 8) {
					j = 9;
				}
				for (int i = 0; i < j; i++) {
					if (i == j - 1) {
						/*
						 * ComModel2.saveQRCode(PaymentSuccessActivity.this,
						 * shop_code);
						 */
						// downloadPic(mapInfos.get("qr_pic"), 9);
						Bitmap bm = QRCreateUtil.createImage(mapInfos.get("QrLink"), 500, 700,
								mapInfos.get("shop_se_price") + "", OrderPaymentActivity.this);// 得到二维码图片
						QRCreateUtil.saveBitmap(bm, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
						break;
					}
					downloadPic(shop_code.substring(1, 4) + "/" + shop_code + "/" + pics.get(i) + "!450", i);
				}
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result) {
				// showShareDialog();
				super.onPostExecute(context, result);
				// getShopLink();
				Intent intent = new Intent(OrderPaymentActivity.this, ShowShareActivity.class);
				intent.putExtra("shop_link", mapInfos.get("link"));
				intent.putExtra("content", mapInfos.get("content"));
				intent.putExtra("four_pic", mapInfos.get("four_pic"));
				intent.putExtra("order_code", orderNo);
				Bundle bundle = new Bundle();
				// bundle.putSerializable("listGoods", (Serializable)
				// listGoods);
				intent.putExtras(bundle);
				bundle = new Bundle();
				bundle.putSerializable("listOrder", (Serializable) listOrder);
				intent.putExtras(bundle);

				startActivity(intent);
				OrderPaymentActivity.this.finish();
			}

		}.execute();
	}

	private void downloadPic(String picPath, int i) {
		try {
			URL url = new URL(YUrl.imgurl + picPath);
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			int contentLength = con.getContentLength();
			// System.out.println("长度 :" + contentLength);
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[8192];
			// 读取到的数据长度
			int len;
			// 输出的文件流 /sdcard/yssj/
			File file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(i)) + ".jpg");
			if (file.exists()) {
				file.delete();
			}
			LogYiFu.e("TAG", "多分享选择下载的图片。。。。");
			OutputStream os = new FileOutputStream(file);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);

			}
			LogYiFu.i("TAG", "下载完毕。。。file=" + file.toString());
			// 完毕，关闭所有链接
			os.close();
			is.close();
		} catch (Exception e) {
			LogYiFu.e("TAG", "下载失败");
			e.printStackTrace();
		}
	}

	/***
	 * 微信支付
	 *
	 * @author Administrator
	 *
	 */
	private void getPrepayId(String wxPayUrl) {
		new SAsyncTask<String, Void, Map<String, String>>(OrderPaymentActivity.this, R.string.wait) {

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
		updatePayStatusList(orderNo, 2 + "");
		if (shareOrders.size() == 0) {// 判断是否只有 0元购的订单，直接cut掉
			OrderPaymentActivity.this.finish();
			return;
		}
		// if("SignShopDetail".equals(signShopDetail)){
		// for (int i = 0; i < listOrder.size(); i++) {
		// Order order = listOrder.get(i);
		// if((order.getStatus()==1&&order.getShop_from()==3)){
		// //未付款的签到订单或者已经完成签到商品支付
		// order.setShop_from(0);//改变为非签到订单
		// }
		// }
		// }
		if ("SignShopDetail".equals(signShopDetail) || isDuobao) {

		} else {
			timeCount.start();
		}
		rel_pay_success.setVisibility(View.VISIBLE);
		TongJiUtils.TongJi(context, 13+"");//支付成功 统计到达次数
		LogYiFu.e("TongJiNew", 13+"");
		for (int i = 0; i < listOrder.size(); i++) {
			Order order = listOrder.get(i);
			if (order.getShop_from() == 0) {// 只要含有正价的就调统计接口
				YunYingTongJi.yunYingTongJi(OrderPaymentActivity.this, 114);
				break;
			}
		}
//		YunYingTongJi.yunYingTongJi(OrderPaymentActivity.this, 114);
		SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
		SharedPreferencesUtil.saveStringData(OrderPaymentActivity.this, Pref.TONGJI_TYPE, "1056");
		rel_nomal.setVisibility(View.GONE);
		LogYiFu.e("12121", isDuobao + "");
		if (("SignShopDetail".equals(signShopDetail) || isDuobao) && !Order.signCompleteFlag) {
			if (isDuobao) {
				if (isMulti) {
					getMultiDuobaoNumber(orderNo, "2");
				} else {
					getDuobaoNumber(orderNo, "2");
				}
			} else {
				SignComplete();
			}
		} else if (isDuobao && Order.signCompleteFlag) {
			if (isMulti) {
				getMultiDuobaoNumber(orderNo, "2");
			} else {
				getDuobaoNumber(orderNo, "2");
			}
		}

		// if (!isMulti) {
		// getShopLink();
		// } else {
		// Intent intent = new Intent(OrderPaymentActivity.this,
		// OrderPaymentSuccessActivity.class);
		// intent.putExtra("listOrder", (Serializable) shareOrders);
		// intent.putExtra("order_code", orderNo);
		// startActivity(intent);
		// OrderPaymentActivity.this.finish();
		// }
		// if(null != OrderDetailsActivity.instance){
		// OrderDetailsActivity.instance.finish();
		// }
		// OrderPaymentActivity.this.setResult(1);
		// finish();
	}

	@Override
	public void onWxpayFailed() {
		if (WXPayEntryActivity.instance != null)
			WXPayEntryActivity.instance.finish();
		// OrderPaymentActivity.this.finish();
		mLlFailture.setVisibility(View.VISIBLE);
		mTvPayTimes.setVisibility(View.GONE);
	}

	private void updatePayStatusList(final String gcode, final String buy_type) {
		new SAsyncTask<Void, Void, ReturnInfo>(OrderPaymentActivity.this) {

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

	private void SignComplete() {
//		Intent intent = new Intent(OrderPaymentActivity.this, MainMenuActivity.class);
//		intent.putExtra("signType", signType + "");
//		intent.putExtra("signShopDetail", signShopDetail);
//		intent.putExtra("isDuobao", isDuobao);
//		intent.putExtra("CanYunumber", CanYunumber);
//		// intent.putExtra("Signidd", id);
//		// intent.putExtra("SignnextID", nextID);
//		intent.putExtra("now_type_id", now_type_id);
//		intent.putExtra("now_type_id_value", now_type_id_value);
//		intent.putExtra("next_type_id", next_type_id);
//		intent.putExtra("next_type_id_value", next_type_id_value);



		if(null !=CommonActivity.instance){
			CommonActivity.instance.finish();
		}
		if(null !=ShopDetailsIndianaActivity.instance){
			ShopDetailsIndianaActivity.instance.finish();
		}



			// 跳至赚钱

			SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
			Intent intent = new Intent(OrderPaymentActivity.this,
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



//		startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);






		finish();

//		Order.signCompleteFlag = true;
	}

	/**
	 * 单个订单 生成参与号码
	 *
	 * @return
	 */
	private void getDuobaoNumber(String order_code, String pay_type) {
		new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) OrderPaymentActivity.this, 0) {
			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
					throws Exception {
				return ComModel2.getDuobaoNumber(OrderPaymentActivity.this, params[0], Integer.valueOf(params[1]));
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null && result != null) {
					CanYunumber = result.get("data");
					LogYiFu.e("OrderPaymentActivity", "单个订单参与号码：" + CanYunumber);
					SignComplete();
				}
			}
		}.execute(order_code, pay_type);
	}

	/**
	 * 多个订单生成 参与号码
	 *
	 * @return
	 */
	private void getMultiDuobaoNumber(String g_code, String pay_type) {
		new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) OrderPaymentActivity.this, 0) {
			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
					throws Exception {
				return ComModel2.getMultiDuobaoNumber(OrderPaymentActivity.this, params[0], Integer.valueOf(params[1]));
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null && result != null) {
					CanYunumber = result.get("data");
					LogYiFu.e("OrderPaymentActivity", "多个订单参与号码：" + CanYunumber);
					SignComplete();
				}
			}
		}.execute(g_code, pay_type);
	}

	// 计时器
	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔

		}

		@Override
		public void onFinish() {// 计时完毕时触发
			TongJiUtils.TongJi(context, 113+"");//支付成功 统计跳出次数
			LogYiFu.e("TongJiNew", 113+"");
			// if (ShopCartNewNewActivity.instance != null) {
			// ShopCartNewNewActivity.instance = null;
			// }
			// Intent intent = new Intent(PaymentActivity.this,
			// ShopCartNewNewActivity.class);
			// startActivity(intent);
			if (null != ShopDetailsActivity.instance) {
				ShopDetailsActivity.instance.finish();
			}

			boolean isMad = SharedPreferencesUtil.getBooleanData(context,Pref.ISMADMONDAY, false);
			if(isMad){
				//疯狂星期一 支付成功后跳转到 抽奖
				int chouJiangNum = 0 ;
				//多个订单单独计算获得抽奖次数 再相加
				if(isMulti){
					for (int i = 0; i <listOrder.size() ; i++) {
						double remainMoney = listOrder.get(i).getRemain_money();
						chouJiangNum += (int)(remainMoney%5==0?remainMoney/5:remainMoney/5+1);
					}
				}else{
					chouJiangNum = (int)(totalAccount%5==0?totalAccount/5:totalAccount/5+1);
				}
				SharedPreferencesUtil.saveStringData(context,Pref.PAYSUCCESSDIALOG,""+totalAccount);
				Intent intent = new Intent(context, WithdrawalLimitActivity.class);
				intent.putExtra("isFromPaySuccess", true);
				intent.putExtra("payLotteryNumber", chouJiangNum);//通过订单金额  算的获得的疯狂抽奖次数
				startActivity(intent);
				OrderPaymentActivity.this.finish();

			}else{
//				if (MainMenuActivity.instances != null)
//					MainMenuActivity.instances.finish();
//				Intent intent = new Intent(context, MainMenuActivity.class);
//				intent.putExtra("toYf", "toYf");
//				intent.putExtra("mIsTwoGroup", mIsTwoGroup);
//				startActivity(intent);
				// 支付成功后跳转到 抽奖 弹框提示获得衣豆
//                int yiDouNum = 0 ;
//                //多个订单单独计算获得衣豆再相加
//				if(isMulti){
//					for (int i = 0; i <listOrder.size() ; i++) {
//						double remainMoney = listOrder.get(i).getRemain_money();
//						yiDouNum += (int)(Math.ceil(remainMoney));
//					}
//				}else{
//					yiDouNum = (int)(Math.ceil(totalAccount));
//				}
//				SharedPreferencesUtil.saveStringData(context,Pref.PAYSUCCESSDIALOG,""+totalAccount);
//				Intent intent = new Intent(context, WithdrawalLimitActivity.class);
//				if(recLen_guide>0){
//					intent.putExtra("is_guidPay",true);
//				}else{
//					intent.putExtra("is_guidPay",false);
//				}
//				intent.putExtra("isFromPaySuccess", true);
//				intent.putExtra("payYiDouNumber", yiDouNum);//通过订单金额  算的获得的衣豆
//				startActivity(intent);
				//普通商品单独购买全部跳订单详情
				goToOrderDetail();


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
					intent.putExtra("paySuccess",true);
					startActivity(intent);
					OrderPaymentActivity.this.finish();


				}
			}

		}.execute();


	}


	// TODO:NEW JOIN

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
	private long recLen_normal=24 * 60 * 60 * 1000;//正常支付剩余时间
	private long recLen_guide=0;//引导页面使用的支付
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
//					recLen2 -=1000;
					recLen_normal-=1000;
					if(recLen_guide>0){
						recLen_guide-=1000;
					}
					if(recLen_guide>0&&mTvPayTimes.getVisibility()==View.VISIBLE){
						recLen2=recLen_guide;
					}else{
						recLen2=recLen_normal;
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
					if (recLen2 <= 0&&recLen_normal<=0) {
						timer2.cancel();
						OrderPaymentActivity.this.finish();
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
//		View view = View.inflate(context, R.layout.payment_notice__dialog, null);
		View view = View.inflate(context, R.layout.dialog_pay_back, null);
		TextView mConfrim = (TextView) view.findViewById(R.id.confrim);
		TextView mDismiss = (TextView) view.findViewById(R.id.dismiss);

		mDismiss.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 把这个对话框取消掉
				dialog.dismiss();
				for (int i = 0; i < listOrder.size(); i++) {
					Order order = listOrder.get(i);
					if (order.getShop_from() == 0) {// 只要含有正价的就调统计接口
						YunYingTongJi.yunYingTongJi(OrderPaymentActivity.this, 113);
						break;
					}
				}
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
		mConfrim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				OrderPaymentActivity.this.finish();

			}
		});

		// 创建自定义样式dialog
		dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		dialog.setCancelable(false);
		dialog.show();
	}

	/**
	 * 调用此接口查询我的余额 TODO:
	 */

	private void queryMyMomeny() {

		new SAsyncTask<Void, Void, String[]>(OrderPaymentActivity.this, R.string.wait) {

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
						LogYiFu.e("payment", "测试！！！！");
						if(mealflag){
							llWXinPay.setOnClickListener(OrderPaymentActivity.this);
							llWalletPay.setVisibility(View.GONE);
							for (int i = 0; i < 6; i++) {
								payTypeMap.put(i, false);
							}
							ivWalletPay.setSelected(false);
							ivWXinPay.setSelected(true);
							payTypeMap.put(2, true);
						}else
						if (("order_special".equals(order_special) && !("paycount".equals(mPayCount)))
								|| (listOrder.size() == 1 && listOrder.get(0).getShop_from() == 1)) {// 特卖，单个付款
							llWalletPay.setVisibility(View.VISIBLE);
							llWXinPay.setOnClickListener(OrderPaymentActivity.this);
							if (mIsQulification && mIsOpen == 1) {
								mTvUsefulMoney.setText(
										"可用金额" + new java.text.DecimalFormat("#0.00").format(mMyMoney * mTwofoldness));
								mTvUsefulMoney.setTextColor(Color.parseColor("#c5c5c5"));
								llWalletPay.setClickable(false);
								ivWalletPay.setSelected(false);
								ivWXinPay.setSelected(true);
								for (int i = 0; i < 6; i++) {
									payTypeMap.put(i, false);
								}
								payTypeMap.put(2, true);
							} else {
								if (mMyMoney >= totalAccount) {// 正常期间，余额充足；可用余额支付
									mRlNeedPay.setVisibility(View.GONE);
									mTvUsefulMoney.setText(
											"可用金额" + new java.text.DecimalFormat("#0.00").format(mMyMoney) + "元");
									llWalletPay.setClickable(true);
									ivWalletPay.setSelected(true);
									llWalletPay.setOnClickListener(OrderPaymentActivity.this);
								} else {// 正常期间，余额不足；不能使用余额支付

									mRlNeedPay.setVisibility(View.GONE);
									if (mMyMoney == 0) {
										mTvUsefulMoney.setText("可用金额" + 0.00 + "元");
										mTvUsefulMoney.setTextColor(Color.parseColor("#c5c5c5"));
										llWalletPay.setClickable(false);
										ivWalletPay.setSelected(false);
										ivWXinPay.setSelected(true);
									} else {
										mTvUsefulMoney.setText("可用金额"
												+ new java.text.DecimalFormat("#0.00").format(mMyMoney) + "元(余额不足)");
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
								// mTvUsefulMoney.setText("可用金额" + balance +
								// "元");
								// if(mMyMoney<totalAccount){
								// mTvUsefulMoney.setTextColor(Color.parseColor("#c5c5c5"));
								// mTvUsefulMoney.setText("可用金额" + balance +
								// "元(余额不足)");
								// }
							}
							if (mealflag) {// 包含特卖的付款只能用第三方
								mRlNeedPay.setVisibility(View.GONE);
								llWXinPay.setOnClickListener(OrderPaymentActivity.this);
								llWalletPay.setVisibility(View.GONE);
								for (int i = 0; i < 6; i++) {
									payTypeMap.put(i, false);
								}
								ivWalletPay.setSelected(false);
								ivWXinPay.setSelected(true);
								payTypeMap.put(2, true);

							}
						} else {// 非特卖单个付款的其他付款方式
								// && "order_common".equals(order_common)
							if ("single".equals(mSingle) && !isDuobao && !("signShopDetail".equals(signShopDetail))) {
								llWalletPay.setVisibility(View.VISIBLE);
								// 单个付款与合并付款跳过来的
								LogYiFu.e("payment", "测试！！！！");
								double mHaveMoney = mMyMoney * mTwofoldness;
								for (int i = 0; i < listOrder.size(); i++) {
									Order order = listOrder.get(i);
									if (order.getTfn_money() <= 0) {
										if (mIsQulification && mIsOpen == 1) {// 余额翻倍期间，>=6;可以使用余额
											double mMoney = 0;
											if (grade == 1||grade==0) {
												if (order.getShop_from() == 0) {
													if (auseTwofold * order.getShop_num() <= mHaveMoney) {
														mMoney = auseTwofold * order.getShop_num();

													} else {
														mMoney = mHaveMoney;
													}
													mHaveMoney -= mMoney;// 計算還剩多少錢
													tfn_money += mMoney;
												}
											} else {
												if (order.getShop_from() == 0) {
													if (buseTwofold * order.getShop_num() <= mHaveMoney) {
														mMoney = buseTwofold * order.getShop_num();

													} else {
														mMoney = mHaveMoney;
													}
													mHaveMoney -= mMoney;// 計算還剩多少錢
													tfn_money += mMoney;
												}
											}
										}
									} else {
										tfn_money += order.getTfn_money();
										if (listOrder.size() >= 2) {
											totalAccount += order.getTfn_money();
										}

									}

									if (tfn_money > 0) {
										if (order.getIs_wx() == 1 && order.getWx_price() != order.getRemain_money()) {// 跳过微信支付
											pay_wx = true;
										}
									}
								}
								// tvTotalAccount.setText("共计:¥" + new
								// java.text.DecimalFormat("#0.0").format(totalAccount));
								// Order orderSingle = listOrder.get(0);
								// tfn_money = orderSingle.getTfn_money();//
								// 翻倍使用的金额
								// is_wx=orderSingle.getIs_wx();
								if (tfn_money > 0) {// 使用了余额翻倍
									mTvUsefulMoney.setText(
											"可用金额" + new java.text.DecimalFormat("#0.00").format(tfn_money) + "元");
									// mRlNeedPay.setVisibility(View.VISIBLE);
									mTvNeedMoney.setText(
											"" + new java.text.DecimalFormat("#0.00").format(totalAccount - tfn_money));
									llWalletPay.setClickable(false);
									ivWalletPay.setSelected(true);
									ivWXinPay.setSelected(true);
									for (int i = 0; i < 6; i++) {
										payTypeMap.put(i, false);
									}
									payTypeMap.put(2, true);
									if ("paycount".equals(mPayCount)) {// 合并付款
																		// ,可以微信支付
										llWXinPay.setClickable(true);
										llWXinPay.setOnClickListener(OrderPaymentActivity.this);
									} else if (pay_wx) {// 单个支付，跳过微信支付
										llWXinPay.setClickable(false);
									} else {
										llWXinPay.setClickable(true);
										llWXinPay.setOnClickListener(OrderPaymentActivity.this);
									}
									llWalletPay.setVisibility(View.GONE);
									tvTotalAccount.setText("共计:¥"
											+ new java.text.DecimalFormat("#0.00").format(totalAccount - tfn_money));
								} else if (mIsQulification && mIsOpen == 1) {// 余额翻倍期间，特卖不能使用余额支付
									boolean flag = false;
									for (int i = 0; i < listOrder.size(); i++) {
										if (listOrder.get(i).getShop_from() != 0) {
											flag = false;
										} else {
											flag = true;
											return;
										}
									}
									if (!flag) {
										llWXinPay.setClickable(true);
										llWXinPay.setOnClickListener(OrderPaymentActivity.this);
										llWalletPay.setVisibility(View.GONE);
										for (int i = 0; i < 6; i++) {
											payTypeMap.put(i, false);
										}
										payTypeMap.put(2, true);
									}
								} else if (grade != 1&&grade!=0) {// 非A类用户，正常期间支付

									for (int i = 0; i < listOrder.size(); i++) {
										if (listOrder.get(i).getShop_from() != 1) {
											flagSpecial = true;
											break;
										}
									}
									llWXinPay.setOnClickListener(OrderPaymentActivity.this);
									if (flagSpecial) {
										double mMoney = 0;
										for (int i = 0; i < listOrder.size(); i++) {
											double mTempMoney = 0;
											Order order = listOrder.get(i);
											if (order.getShop_from() == 0) {

												if (buseTwofold * order.getShop_num() <= mMyMoney) {
													mTempMoney = buseTwofold * order.getShop_num();
												} else {
													mTempMoney = mMyMoney;
												}

											} else if (order.getShop_from() == 1 || order.getShop_from() == 2) {
												mTempMoney = order.getOrder_price();
											}
											mMoney += mTempMoney;
										}
										mTvUsefulMoney.setText(
												"可用金额" + new java.text.DecimalFormat("#0.00").format(mMoney) + "元");
										// mRlNeedPay.setVisibility(View.VISIBLE);
										llWalletPay.setClickable(false);
										ivWalletPay.setSelected(false);
										ivWXinPay.setSelected(true);

										// mTvNeedMoney.setText(
										// "" + new
										// java.text.DecimalFormat("#0.0").format(totalAccount
										// - mMoney));
										// llWalletPay.setClickable(false);
										// if (mMoney > 0) {
										// ivWalletPay.setSelected(true);
										// } else {
										// ivWalletPay.setSelected(false);
										// }
										llWalletPay.setVisibility(View.GONE);
										ivWXinPay.setSelected(true);
										for (int i = 0; i < 6; i++) {
											payTypeMap.put(i, false);
										}
										payTypeMap.put(2, true);
										// llWalletPay.setVisibility(View.GONE);
										tvTotalAccount
												.setText("" + new DecimalFormat("#0.00").format(totalAccount) + "元");
									} else {
										llWalletPay.setClickable(true);
										ivWalletPay.setSelected(true);
										llWalletPay.setOnClickListener(OrderPaymentActivity.this);
										mTvUsefulMoney.setText(
												"可用金额" + new java.text.DecimalFormat("#0.00").format(mMyMoney) + "元");
										tvTotalAccount
												.setText("" + new DecimalFormat("#0.00").format(totalAccount) + "元");
									}

								} else if (mMyMoney >= totalAccount) {// A类用户
																		// 正常期间，余额充足；可用余额支付
									LogYiFu.e("payment", "余额充足");
									mRlNeedPay.setVisibility(View.GONE);
									mTvUsefulMoney.setText(
											"可用金额" + new java.text.DecimalFormat("#0.00").format(mMyMoney) + "元");
									llWalletPay.setClickable(true);
									ivWalletPay.setSelected(true);
									llWalletPay.setOnClickListener(OrderPaymentActivity.this);
									llWXinPay.setClickable(true);
									llWXinPay.setOnClickListener(OrderPaymentActivity.this);
								} else {// A类用户 正常期间，余额不足；不能使用余额支付
									LogYiFu.e("payment", "余额不足");
									llWXinPay.setClickable(true);
									llWXinPay.setOnClickListener(OrderPaymentActivity.this);
									mRlNeedPay.setVisibility(View.GONE);
									if (mMyMoney == 0) {
										mTvUsefulMoney.setText("可用金额" + 0.00 + "元");
										mTvUsefulMoney.setTextColor(Color.parseColor("#c5c5c5"));
										llWalletPay.setClickable(false);
										ivWalletPay.setSelected(false);
										ivWXinPay.setSelected(true);
									} else {
										mTvUsefulMoney.setText("可用金额"
												+ new java.text.DecimalFormat("#0.00").format(mMyMoney) + "元(余额不足)");
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
								if (mealflag) {// 包含特卖的付款只能用第三方
									mRlNeedPay.setVisibility(View.GONE);
									llWXinPay.setOnClickListener(OrderPaymentActivity.this);
									llWalletPay.setVisibility(View.GONE);
									for (int i = 0; i < 6; i++) {
										payTypeMap.put(i, false);
									}
									ivWalletPay.setSelected(false);
									ivWXinPay.setSelected(true);
									payTypeMap.put(2, true);

								}
							} else {// 不是单个付款与合并付款跳出来（保持以前的，没操作）
								llWalletPay.setVisibility(View.VISIBLE);
								llWXinPay.setClickable(true);
								llWalletPay.setClickable(true);
								llWalletPay.setOnClickListener(OrderPaymentActivity.this);
								llWXinPay.setOnClickListener(OrderPaymentActivity.this);
							}
						}
					}
				}
			}

		}.execute((Void[]) null);

	}

	private int grade = 1;
	private int buseTwofold = 6;
	private int auseTwofold = 6;

	/**
	 * 获取用户等级
	 */
	private void getUserGradle() {
		new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) OrderPaymentActivity.this,
				R.string.wait) {

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
	 * 获取系统时间
	 */
	private void getSystemTime() {
		new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) OrderPaymentActivity.this,
				R.string.wait) {

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
					nowTime = (Long) result.get("now");

					recLen_normal = deadLine - nowTime;
					recLen_guide=deadLine_guid-nowTime;
//					recLen_guide=1*60*1000;
					mTvPayTimes.setVisibility(View.VISIBLE);
				}else{
					recLen_normal=0;
					recLen_guide=0;
				}
				timer2.schedule(mTask2, 0, 1000); // timeTask
			}

		}.execute();
	}
}
