//package com.yssj.ui.activity.shopdetails;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.Serializable;
//import java.io.UnsupportedEncodingException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.alipay.sdk.app.PayTask;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//import com.yssj.YConstance;
//import com.yssj.YConstance.Config;
//import com.yssj.YUrl;
//import com.yssj.activity.R;
//import com.yssj.activity.wxapi.WXPayEntryActivity;
//import com.yssj.activity.wxapi.WXPayEntryActivity.OnWxpayFinish;
//import com.yssj.alipay.PayResult;
//import com.yssj.alipay.PayUtil;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.PayPasswordCustomDialog;
//import com.yssj.custom.view.PayPasswordCustomDialog.InputDialogListener;
//import com.yssj.entity.CheckPwdInfo;
//import com.yssj.entity.Order;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.entity.ShopCart;
//import com.yssj.model.ComModel;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.infos.RedPacketsActivity;
//import com.yssj.ui.activity.infos.SetMyPayPassActivity;
//import com.yssj.ui.activity.infos.StatusInfoActivity;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.ui.fragment.orderinfo.OrderDetailsActivity;
//import com.yssj.utils.MD5Tools;
//import com.yssj.utils.QRCreateUtil;
//import com.yssj.utils.ShareUtil;
//import com.yssj.utils.ToastUtil;
//import com.yssj.wxpay.WxPayUtil;
//
///**
// * 收银台支付
// * 
// * @author Administrator
// * 
// */
//public class RedPacketsPaymentActivity extends BasicActivity implements OnClickListener,
//		OnWxpayFinish {
//	private LinearLayout llWalletPay;
//	private LinearLayout llQuickPay;
//	private LinearLayout llWXinPay;
//	private LinearLayout llAliPay;
//	private LinearLayout llUnionPay;
//	private LinearLayout llMydeayPay;
//	private ImageView ivWalletPay, ivQuickPay, ivWXinPay, ivAliPay, ivUnionPay,
//			ivMydearPay;
//	private TextView tvTotalAccount;
//	private Button btnPay;
//	private final int SDK_PAY_FLAG = 1;
//	private ImageView imgCancle;
//	private HashMap<Integer, Boolean> payTypeMap;
//	private ArrayList<ImageView> imgSelectList;
//	private IWXAPI msgApi;
//	private String totalAccount;
//	private PayPasswordCustomDialog customDialog;
//	private InputDialogListener inputDialogListener;
//	private RelativeLayout root;
//	private String rp_id;//红包订单号
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		// requestWindowFeature(Window.FEATURE_NO_TITLE);
//		aBar.hide();
//		setContentView(R.layout.activity_payment);
//		WXPayEntryActivity.setOnWxpayFinish(this);// 实现微信支付成功之后调分享界面接口
//		msgApi = WXAPIFactory.createWXAPI(this, null);
//		msgApi.registerApp(WxPayUtil.APP_ID);
//		totalAccount = getIntent().getStringExtra("totlaAccount");
//		
//		rp_id = getIntent().getStringExtra("rp_id");
//
//		// 此处从未付款订单跳转
//		initview();
//		getPayMap();
//	}
//
//	// 得到一个map集合 存放选择支付类型
//	private void getPayMap() {
//		payTypeMap = new HashMap<Integer, Boolean>();
//		payTypeMap.put(0, true);
//		for (int i = 1; i < 6; i++) {
//			payTypeMap.put(i, false);
//		}
//	}
//
//	private void initview() {
//		llWalletPay = (LinearLayout) findViewById(R.id.ll_wallet);
//		llQuickPay = (LinearLayout) findViewById(R.id.ll_quick_pay);
//		llWXinPay = (LinearLayout) findViewById(R.id.ll_wxin_pay);
//		llAliPay = (LinearLayout) findViewById(R.id.ll_ali_pay);
//		llUnionPay = (LinearLayout) findViewById(R.id.ll_union_pay);
//		llMydeayPay = (LinearLayout) findViewById(R.id.ll_mydear_pay);
//		ivWalletPay = (ImageView) findViewById(R.id.iv_wallet);
//		ivWalletPay.setSelected(true);
//
////		ivQuickPay = (ImageView) findViewById(R.id.iv_quick_pay);
//		ivWXinPay = (ImageView) findViewById(R.id.iv_wxin_pay);
//		ivAliPay = (ImageView) findViewById(R.id.iv_ali_pay);
//		ivUnionPay = (ImageView) findViewById(R.id.iv_union_pay);
//		ivMydearPay = (ImageView) findViewById(R.id.iv_mydear_pay);
//		root = (RelativeLayout) findViewById(R.id.root);
//		root.setBackgroundColor(Color.WHITE);
//		// 勾选按钮集合
//		imgSelectList = new ArrayList<ImageView>();
//		imgSelectList.add(ivWalletPay);
//		imgSelectList.add(ivQuickPay);
//		imgSelectList.add(ivWXinPay);
//		imgSelectList.add(ivAliPay);
//		imgSelectList.add(ivUnionPay);
//		imgSelectList.add(ivMydearPay);
//
//		tvTotalAccount = (TextView) findViewById(R.id.tv_total_account);// 共计支付
////		String price = new java.text.DecimalFormat("#0.00")
////				.format(totalAccount);
//		tvTotalAccount.setText("共计:￥" + totalAccount);
//		btnPay = (Button) findViewById(R.id.btn_pay); // 付款
//		imgCancle = (ImageView) findViewById(R.id.iv_cancle);// 取消返回
//
//		setOnclick();
//	}
//
//	// 设置点击监听
//	private void setOnclick() {
//		llWalletPay.setOnClickListener(this);
//		llQuickPay.setOnClickListener(this);
//		llWXinPay.setOnClickListener(this);
//		llAliPay.setOnClickListener(this);
//		llUnionPay.setOnClickListener(this);
//		llMydeayPay.setOnClickListener(this);
//		ivWalletPay.setOnClickListener(this);
//		ivQuickPay.setOnClickListener(this);
//		ivWXinPay.setOnClickListener(this);
//		ivAliPay.setOnClickListener(this);
//		ivUnionPay.setOnClickListener(this);
//		ivMydearPay.setOnClickListener(this);
//		btnPay.setOnClickListener(this);
//		imgCancle.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View view) {
//		switch (view.getId()) {
//		case R.id.ll_wallet:
//		case R.id.iv_wallet:
//			setSelectStatus(0);
//			break;
//		case R.id.ll_quick_pay:
//		case R.id.iv_quick_pay:
//			setSelectStatus(1);
//			break;
//		case R.id.ll_wxin_pay:
//		case R.id.iv_wxin_pay:
//			setSelectStatus(2);
//			break;
//		case R.id.ll_ali_pay:
//		case R.id.iv_ali_pay:
//			setSelectStatus(3);
//			break;
//		case R.id.ll_union_pay:
//		case R.id.iv_union_pay:
//			setSelectStatus(4);
//			break;
//		case R.id.ll_mydear_pay:
//		case R.id.iv_mydear_pay:
//			setSelectStatus(5);
//			break;
//		case R.id.btn_pay:// 去支付
//			Iterator<Entry<Integer, Boolean>> iterator = payTypeMap.entrySet()
//					.iterator();
//			while (iterator.hasNext()) {
//				Entry<Integer, Boolean> entry = iterator.next();
//				if (entry.getValue()) {
//					gotoPay(entry.getKey(), view);
//					break;
//				}
//			}
//			break;
//		case R.id.iv_cancle:// 取消支付 返回上一层
//				finish();
//			break;
//		default:
//			break;
//		}
//	}
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			finish();
//		}
//		return false;
//	}
//
//	private AlertDialog dialog;
//
//	private void customDialog() {
//		AlertDialog.Builder builder = new Builder(this);
//		// 自定义一个布局文件
//		View view = View.inflate(this, R.layout.payback_esc_apply_dialog, null);
//		TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
//		tv_des.setText("您还没有设置支付密码，立即去设置？");
//
//		Button ok = (Button) view.findViewById(R.id.ok);
//		ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
//		Button cancel = (Button) view.findViewById(R.id.cancel);
//
//		cancel.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// 把这个对话框取消掉
//				dialog.dismiss();
//			}
//		});
//
//		ok.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(RedPacketsPaymentActivity.this,
//						SetMyPayPassActivity.class);
//				startActivity(intent);
//				dialog.dismiss();
//			}
//		});
//
//		dialog = builder.create();
//		dialog.setView(view, 0, 0, 0, 0);
//		dialog.show();
//	}
//
//	private void checkMyWalletPayPass(View v) {
//		// TODO Auto-generated method stub
//		new SAsyncTask<Void, Void, CheckPwdInfo>(RedPacketsPaymentActivity.this, v,
//				R.string.wait) {
//
//			@Override
//			protected CheckPwdInfo doInBackground(FragmentActivity context,
//					Void... params) throws Exception {
//				return ComModel.checkPWD(context);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					CheckPwdInfo result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (null == e) {
//					if (result != null && "1".equals(result.getStatus())
//							&& "1".equals(result.getFlag())) {
//						customDialog();
//					} else if (result != null && "1".equals(result.getStatus())
//							&& "2".equals(result.getFlag())) {
//						customDialog = new PayPasswordCustomDialog(
//								RedPacketsPaymentActivity.this, R.style.mystyle,
//								R.layout.pay_customdialog, "请输入支付密码",
////								new java.text.DecimalFormat("#0.00")
////										.format(totalAccount)
//								
//								totalAccount);
//						inputDialogListener = new InputDialogListener() {
//
//							@Override
//							public void onOK(String pwd) {
//								// 给密码文本框设置密码
//								walletPayOrder(rp_id, pwd,true);
//							}
//
//							@Override
//							public void onCancel() {
//							}
//
//						};
//						customDialog.setListener(inputDialogListener);
//						customDialog.show();
//					} else {
//						ToastUtil.showLongText(context, "糟糕，出错了~~~");
//					}
//				}
//			}
//
//		}.execute((Void[]) null);
//	}
//
//	// 付款
//	private void gotoPay(Integer position, View v) {
//		switch (position) {
//		case 0: // 余额支付 钱包支付
//			checkMyWalletPayPass(v);
//			break;
//		case 1: // 快捷支付
//
//			break;
//		case 2: // 微信支付
//			/*
//			 * if (null != listGoods) { if (listGoods.size() > 1) {
//			 * getPrepayId(YUrl.WX_PAY_MULTI); } else {
//			 * getPrepayId(YUrl.WX_PAY_SINGLE); } } else {
//			 */
////			if (isMulti) {
////				getPrepayId(YUrl.WX_PAY_MULTI);
////			} else {
////				getPrepayId(YUrl.WX_PAY_SINGLE);
////			}
//			getPrepayId(YUrl.WX_PAY_RED);
//			// }
//			break;
//		case 3: // 支付宝支付
//			/*
//			 * if (null != listGoods) { if (listGoods.size() > 1) { aliPay(null,
//			 * YUrl.ALI_NOTIFY_URL_MULTI, orderNo); } else { aliPay(null,
//			 * YUrl.ALI_NOTIFY_URL_SINGLE, orderNo); } } else {
//			 */
//			getAliParam();
//			/*
//			 * if (isMulti) { aliPay(null, YUrl.ALI_NOTIFY_URL_MULTI, orderNo);
//			 * } else { aliPay(null, YUrl.ALI_NOTIFY_URL_SINGLE, orderNo); }
//			 */
//			// }
//			break;
//		case 4: // 银联支付
//
//			break;
//		case 5: // 找亲爱的支付
//
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	/** 获取支付宝所需参数 */
//	private void getAliParam() {
//		new SAsyncTask<Void, Void, HashMap<String, String>>(this, R.string.wait) {
//
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//			@Override
//			protected HashMap<String, String> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.getAliParam(context, rp_id);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, String> result, Exception e) {
//				// TODO Auto-generated method stub
//				if (e == null) {
//					if (result.get("status").equals("1")) {
//						if (true) {
//							aliPay(null, result.get("pay_url")
//									+ YUrl.ALI_NOTIFY_URL_RED, rp_id,
//									result.get("partner"),
//									result.get("seller"),
//									result.get("sign_type"),
//									result.get("private_key"));
//						} else {
//							aliPay(null, result.get("pay_url")
//									+ YUrl.ALI_NOTIFY_URL_SINGLE, rp_id,
//									result.get("partner"),
//									result.get("seller"),
//									result.get("sign_type"),
//									result.get("private_key"));
//						}
//					}
//				}
//				super.onPostExecute(context, result, e);
//			}
//
//		}.execute();
//	}
//
//	// 设置选择状态
//	private void setSelectStatus(int i) {
//		for (int j = 0; j < 6; j++) {
//			if (j == i) {
//				payTypeMap.put(j, true);
//				imgSelectList.get(j).setSelected(true);
//			} else {
//				payTypeMap.put(j, false);
//				imgSelectList.get(j).setSelected(false);
//			}
//		}
//	}
//
//	/**
//	 * 支付宝支付
//	 * 
//	 * @param v
//	 * @param order_code
//	 */
//	private void aliPay(View v, String notify_url, String order_code,
//			String partner, String seller, String sign_type, String private_key) {
//
//		StringBuffer sb = new StringBuffer();
//		String subject = Config.pay_subject;
//		String content = sb.toString();
//		// String price = sum + "";
//		// shop.getShop_se_price() + "";
//
//		// 订单
//		String orderInfo = PayUtil.getOrderInfo(subject, "hongbao", order_code,
//				totalAccount + "", notify_url, partner, seller);
//
//		MyLogYiFu.e("totalAccount", totalAccount+"");
//		// 对订单做RSA 签名
//		String sign = PayUtil.sign(orderInfo, private_key);
//		try {
//			// 仅需对sign 做URL编码
//			sign = URLEncoder.encode(sign, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//
//		// 完整的符合支付宝参数规范的订单信息
//		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
//				+ PayUtil.getSignType(sign_type);
//
//		Runnable payRunnable = new Runnable() {
//
//			@Override
//			public void run() {
//				// 构造PayTask 对象
//				PayTask alipay = new PayTask(RedPacketsPaymentActivity.this);
//				// 调用支付接口，获取支付结果
//				String result = alipay.pay(payInfo);
//
//				Message msg = new Message();
//				msg.what = SDK_PAY_FLAG;
//				msg.obj = result;
//				mHandler.sendMessage(msg);
//			}
//		};
//
//		// 必须异步调用
//		Thread payThread = new Thread(payRunnable);
//		payThread.start();
//
//	}
//
//	private Handler mHandler = new Handler() {
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case SDK_PAY_FLAG: {
//				PayResult payResult = new PayResult((String) msg.obj);
//
//				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
//				String resultInfo = payResult.getResult();
//
//				String resultStatus = payResult.getResultStatus();
//
//				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//				if (TextUtils.equals(resultStatus, "9000")) {
//					// Toast.makeText(PaymentActivity.this, "支付成功",
//					// Toast.LENGTH_SHORT).show();
//					// showShareDialog();
//					
//					queryRedPackets(rp_id);
//					
//				} else {
//					// 判断resultStatus 为非“9000”则代表可能支付失败
//					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//					if (TextUtils.equals(resultStatus, "8000")) {
//						Toast.makeText(RedPacketsPaymentActivity.this, "支付结果确认中",
//								Toast.LENGTH_SHORT).show();
//
//					} else {
//						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//						Toast.makeText(RedPacketsPaymentActivity.this, "支付失败",
//								Toast.LENGTH_SHORT).show();
//						// getOrderInfo();
//						if (true) {
////							getOrderInfo();
//						} else {
//						}
//					}
//				}
//				break;
//			}
//			}
//		};
//	};
//
//	private void getOrderInfo() {
//		new SAsyncTask<Void, Void, Order>(RedPacketsPaymentActivity.this, R.string.wait) {
//
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//			@Override
//			protected Order doInBackground(FragmentActivity context,
//					Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.getMyOrder(context, rp_id);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					Order result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (null == e) {
//				}
//			}
//
//		}.execute();
//	}
//
//	/****
//	 * 通过我的钱包来付款
//	 * 
//	 * @param order_code
//	 * @param pwd
//	 */
//	private void walletPayOrder(final String rp_id, String pwd,
//			final boolean isMulti) {
//		new SAsyncTask<String, Void, ReturnInfo>(RedPacketsPaymentActivity.this, null,
//				R.string.wait) {
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context,
//					String... params) throws Exception {
//
//					return ComModel.walletPayRed(context,params[0],
//							params[1]);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					ReturnInfo r, Exception e) {
//
//				if (e != null) {// 查询异常
//					MyLogYiFu.e("异常 -----", context.getString(R.string.ss));
//				} else {// 查询商品详情成功，刷新界面
//					if(r.getStatus().equals("108")){
//						ToastUtil.showShortText(context, r.getMessage());
//					}else{
//					queryRedPackets(rp_id);
//					}
//				}
//
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			};
//		}.execute(rp_id, pwd);
//
//	}
//
//	/***
//	 * 微信支付
//	 * 
//	 * @author Administrator
//	 * 
//	 */
//	private void getPrepayId(String wxPayUrl) {
//		new SAsyncTask<String, Void, Map<String, String>>(RedPacketsPaymentActivity.this,
//				R.string.wait) {
//
//			@Override
//			protected Map<String, String> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//				// TODO Auto-generated method stub
//				Map<String, String> mapReturn = ComModel2.getPrepayIdRed(context,
//						rp_id, params[0]);
//				return mapReturn;
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					Map<String, String> result, Exception e) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result, e);
//
//				if (null == e) {
//					WxPayUtil.sendPayReq(msgApi, WxPayUtil.genPayReq(result),
//							result.get("appid"));
//				}
//				// finish();
//			}
//
//		}.execute(wxPayUrl);
//	}
//
//	@Override
//	public void onWxpaySuccess() {
//		queryRedPackets(rp_id);
//	}
//
//	@Override
//	public void onWxpayFailed() {
//		// TODO Auto-generated method stub
//		if(null != WXPayEntryActivity.instance)
//			WXPayEntryActivity.instance.finish();
//	}
//	
//	
//	private void queryRedPackets(final String rp_id){
//		new SAsyncTask<Void, Void, HashMap<String, Object>>(RedPacketsPaymentActivity.this) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(FragmentActivity context,
//					Void... params) throws Exception {
//				return ComModel.getShareRedPacketLink(context, rp_id);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, Object> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if(null==e){
//					ShareUtil.shareRedPackets(context, result.get("link")+"",result.get("name")+"",result.get("content")+"");
//					finish();
//				}
//			}
//
//		}.execute();
//	}
//}
