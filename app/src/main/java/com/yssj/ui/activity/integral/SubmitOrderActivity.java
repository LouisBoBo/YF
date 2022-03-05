package com.yssj.ui.activity.integral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.AddAndSubView;
import com.yssj.custom.view.AddAndSubView.OnNumChangeListener;
import com.yssj.data.DBService;
import com.yssj.entity.DeliveryAddress;
import com.yssj.entity.IntegralShop;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.Store;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.setting.SetDeliverAddressActivity;
import com.yssj.ui.activity.shopdetails.ChooseMyDeliverAddr;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.orderinfo.WalletDialog;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;
import com.yssj.wxpay.WxPayUtil;

/**
 * 提交订单
 * */
public class SubmitOrderActivity extends BasicActivity implements
		OnNumChangeListener {

	private TextView tv_name, tv_phone, tv_receiver_addr;

	private LinearLayout lin_receiver_addr;

	private DBService db = new DBService(this);

	private TextView tv_sum, tv_pro_name, tv_pro_descri;
	private ImageView img_pro_pic;

	private IntegralShop shop;

	private String size, color;
	private int shop_num, stock_type_id, stock;

	private Button btn_pay;
	private AddAndSubView pro_sum;
	private TextView tv_settle_account;
	private TextView total_account;

	private static final int SDK_PAY_FLAG = 1;
	private AlertDialog dialog2 = null, dialogPay = null;
	private DeliveryAddress dAddress;
	private HashMap<String, String> addResult;
	private String message = "";
	private Store store;
	private String orderNo;

	private double price;

	Map<String, String> resultunifiedorder;

	IWXAPI msgApi;// 微信api

	private int addressId = 0;

	private Intent intent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil
			.getImage());
	private LinearLayout lin_set_addr;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context= this;
		msgApi = WXAPIFactory.createWXAPI(this, null);
		msgApi.registerApp(WxPayUtil.APP_ID);
		initData();
	}

	private void initData() {
		new SAsyncTask<Void, Void, HashMap<String, String>>(this, R.string.wait) {

			@Override
			protected HashMap<String, String> doInBackground(
					FragmentActivity context, Void... params) {
				// TODO Auto-generated method stub
				try {
					return ComModel2.getDefaultDeliverAddr(context);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, String> result) {
				// TODO Auto-generated method stub
				// if (null != result) {
				initView(result);
				// }
				super.onPostExecute(context, result);
			}

		}.execute();

		shop = (IntegralShop) getIntent().getSerializableExtra("shop");
		size = getIntent().getStringExtra("size");
		color = getIntent().getStringExtra("color");
		shop_num = getIntent().getIntExtra("shop_num", 0);
		stock_type_id = getIntent().getIntExtra("stock_type_id", 0);
		stock = getIntent().getIntExtra("stock", 0);
		store = YCache.getCacheStoreSafe(SubmitOrderActivity.this);
		price = getIntent().getDoubleExtra("price", 0);

	}

	@SuppressLint("StringFormatMatches")
	private void initView(HashMap<String, String> result) {
		setContentView(R.layout.submit_order);
		tv_name = (TextView) findViewById(R.id.tv_name);// 收件人
		tv_phone = (TextView) findViewById(R.id.tv_phone);// 收件人电话
		tv_receiver_addr = (TextView) findViewById(R.id.tv_receiver_addr);// 收件地址
		lin_receiver_addr = (LinearLayout) findViewById(R.id.lin_receiver_addr);
		lin_receiver_addr.setOnClickListener(this);
		lin_set_addr = (LinearLayout) findViewById(R.id.lin_set_addr);
		lin_set_addr.setOnClickListener(this);
		this.addResult = result;
		// dAddress = result.get(0);
		setDeliverAddress(result, null);

		tv_sum = (TextView) findViewById(R.id.tv_sum);// 商品单价x商品个数
		tv_pro_name = (TextView) findViewById(R.id.tv_pro_name);// 商品名称
		tv_pro_descri = (TextView) findViewById(R.id.tv_pro_descri);// 商品描述
		img_pro_pic = (ImageView) findViewById(R.id.img_pro_pic);// 商品图片
		btn_pay = (Button) findViewById(R.id.btn_pay);// 支付按钮
		btn_pay.setOnClickListener(this);

		tv_settle_account = (TextView) findViewById(R.id.tv_settle_account);// 合计多少钱
		total_account = (TextView) findViewById(R.id.total_account);// 合计多少货物多少钱

		// pro_sum = (AddAndSubView) findViewById(R.id.pro_sum);
		// pro_sum = (AddAndSubView) findViewById(R.id.pro_sum);
		pro_sum.setOnNumChangeListener(this);

		pro_sum.setNum(shop_num);
		LogYiFu.e("pro_sum.getNum()", pro_sum.getNum() + "");

		double sum = price * shop_num;
		// shop.getShop_se_price() * shop_num;
		total_account.setText(Html.fromHtml(getString(R.string.total_account,
				shop_num, sum)));
		tv_settle_account.setText("合计：" + sum);
		tv_sum.setText(shop.getShop_price() + "\nx" + shop_num);
		tv_pro_name.setText(shop.getShop_name());
		tv_pro_descri.setText(shop.getContent() + size + color);
//		SetImageLoader.initImageLoader(this, img_pro_pic, shop.getDef_pic(),"");
		PicassoUtils.initImage(this,  shop.getDef_pic(),  img_pro_pic);
	}

	private void setDeliverAddress(HashMap<String, String> mapRet,
			DeliveryAddress dAddress) {
		if (null == mapRet && dAddress != null) {
			tv_name.setText("收件人：" + dAddress.getConsignee());
			tv_phone.setText(dAddress.getPhone());
			String province = db.queryAreaNameById(dAddress.getProvince());
			String city = db.queryAreaNameById(dAddress.getCity());
			String county = db.queryAreaNameById(dAddress.getArea());
			String street = "";
			if (null != dAddress.getStreet() && 0 != dAddress.getStreet()) {
				street = db.queryAreaNameById(dAddress.getStreet());
			}
			tv_receiver_addr.setText(province + city + county + street
					+ dAddress.getAddress());// 收货地址
		} else if (null != mapRet && dAddress == null) {
			tv_name.setText("收件人：" + mapRet.get("consignee"));
			tv_phone.setText(mapRet.get("phone"));
			tv_receiver_addr.setText(mapRet.get("address"));// 收货地址
		} else if (null == mapRet && null == dAddress) {
			lin_receiver_addr.setVisibility(View.GONE);
			lin_set_addr.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 提交订单
	 * */
	private void submitOrder(final View v) {

		UserInfo user = YCache.getCacheUser(context);
		if(user.getGender() == 1){
			ToastUtil.showShortText2("系统维护中，暂不支持支付");
			return;
		}




		final List<HashMap<String, String>> OrderShop = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("stocktypeid", stock_type_id + "");
		map.put("message", "");
		map.put("color", color);
		map.put("size", size);
		map.put("Shop_num", shop_num + "");
		map.put("shop_code", shop.getShop_code() + "");
		OrderShop.add(map);
		new SAsyncTask<Void, Void, HashMap<String, Object>>(this, v,
				R.string.wait) {

			@Override
			protected HashMap<String, Object> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				return ComModel2.submitInteOrder(context, message,
						store.getS_code(), store.getS_name(), addressId,
						OrderShop);
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, Object> result) {
				super.onPostExecute(context, result);
				orderNo = (String) result.get("order_code");
				// payMoney(v, (String) result.get("order_code"));
				// showPayDialog();
				SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
				WalletDialog dlgDialog = new WalletDialog(context,
						R.style.DialogStyle);
				dlgDialog.show();
				dlgDialog.listener = new WalletDialog.OnCallBackPayListener() {

					@Override
					public void selectPwd(String pwd) {
						exchange(orderNo, pwd);

					}
				};
			}

		}.execute((Void[]) null);
	}

	private void exchange(final String orderNo, final String pwd) {
		new SAsyncTask<Void, Void, ReturnInfo>(this, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.exchangeGood(context, orderNo, pwd);
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result);
				ToastUtil.showShortText(context, result.getMessage());
			}

		}.execute();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_pay:
			// payMoney(v);
			submitOrder(v);
			break;
		case R.id.lin_receiver_addr:
			intent = new Intent(this, ChooseMyDeliverAddr.class);
			startActivityForResult(intent, 1001);
			break;
		case R.id.lin_set_addr:
			intent = new Intent(this, SetDeliverAddressActivity.class);
			startActivityForResult(intent, 1002);
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 1001) {
			if (null != intent) {
				dAddress = (DeliveryAddress) intent
						.getSerializableExtra("item");
				addressId = dAddress.getId();
				setDeliverAddress(null, dAddress);
			}
		} else if (requestCode == 1002) {
			initData();
		}
	}

	@SuppressLint("StringFormatMatches")
	@Override
	public void onNumChange(View view, int num) {
		// TODO Auto-generated method stub
		shop_num = num;
		tv_sum.setText(shop.getShop_price() + "\nx" + num);
		double sum = price * shop_num;
		// shop.getShop_se_price() * shop_num;
		tv_settle_account.setText("合计：" + sum);
		total_account.setText(Html.fromHtml(getString(R.string.total_account,
				shop_num, sum)));
		if (num > stock) {
			// num = stock;
			pro_sum.setNum(stock);

		}
	}

	// 计时器
	class TimeCount extends CountDownTimer {
		private Button btn = null;

		public TimeCount(long millisInFuture, long countDownInterval, Button btn) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
			this.btn = btn;

		}

		@Override
		public void onFinish() {// 计时完毕时触发
			try {
				// btn.setText("重发(30)");
				// btn.setEnabled(true);
				dialog2.dismiss();
				startActivityForResult(intent, 101);
				// btn.setBackgroundResource(R.color.actionbar_bn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			try {
				// btn.setEnabled(false);
				// btn.setBackgroundResource(R.color.time_count);
				btn.setText("" + millisUntilFinished / 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
