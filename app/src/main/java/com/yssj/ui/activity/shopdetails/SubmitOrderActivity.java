package com.yssj.ui.activity.shopdetails;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.yssj.Constants;
import com.yssj.YConstance;
import com.yssj.YUrl;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.data.DBService;
import com.yssj.entity.DeliveryAddress;
import com.yssj.entity.Shop;
import com.yssj.entity.ShopCart;
import com.yssj.entity.Store;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.activity.infos.UsefulCouponsActivity;
import com.yssj.ui.activity.logins.RegisterFragment;
import com.yssj.ui.activity.setting.ManMyDeliverAddr;
import com.yssj.ui.activity.setting.SetDeliverAddressActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;
import com.yssj.utils.YunYingTongJi;
import com.yssj.wxpay.WxPayUtil;

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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * 提交订单
 * */
public class SubmitOrderActivity extends BasicActivity implements
		OnCheckedChangeListener {
	
	

	private TextView tv_name, tv_phone, tv_receiver_addr;
	private ToggleButton mTgb;// 优惠券按钮开关
	private ToggleButton tgb_diyongquan; //抵用券开关
	
	private LinearLayout lin_receiver_addr;
	private RelativeLayout rel_show_share;
	private TimeCount time;
	private DBService db = new DBService(this);
	private int m=0;
	private TextView tv_sum, tv_pro_name, tv_pro_descri;
	private ImageView img_pro_pic;
	private TextView mShareVoucachers;
	private Shop shop;
	private Boolean flag = false;
	private String size, color,mDef_pic;
	private int shop_num, stock_type_id, stock;
	
	private String mShop_code;
	private Context context;
	
	private Button btn_pay;
	private TextView tv_settle_account;
	private TextView total_account;

	private static final int SDK_PAY_FLAG = 1;
	private AlertDialog dialog2 = null, dialogPay = null;
	private DeliveryAddress dAddress;
	private HashMap<String, String> addResult;
	private String message = "";
	private Store store;
	private String orderNo;
	private double a;
	private int original_price;
	private double price;
	private String pic;// 默认图片
	private double mTest;
	Map<String, String> resultunifiedorder;
	private int[] countDownBg = { R.drawable.countdown1, R.drawable.countdown2,
			R.drawable.countdown3, R.drawable.countdown3 };

	IWXAPI msgApi;// 微信api

	private int addressId = 0;

	private Intent intent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil
			.getImage());
	private LinearLayout rel_set_addr;

	// title 部分
	private TextView tvTitle_base;
	private LinearLayout img_back;

	private ImageButton imgbtn_left_icon;

	private TextView tv_pro_price, tv_pro_former_price;

	private TextView tv_ProDiscount;

	private ImageView ivBack;

	private LinearLayout lin_set_addr;

	private TextView tvTotalAccount;
	
	private TextView tv_total_account_diyongquan;
	private TextView tv_discount_coupon_count_diyongquan;
	private TextView tv_yunfei;
	private TextView tv_price_youhui;
	private TextView youhuiquan;
	private TextView tv_jifen;
	private TextView tv_diyong;
	private RelativeLayout	rl_youhuiquan;
	private RelativeLayout	rl_jifen;
	
	
	private RelativeLayout rel_name_phone;

	private RelativeLayout rl_discount_coupon;// 优惠卷

	private HashMap<String, Object> mapCoupon = new HashMap<String, Object>();

	private TextView tv_discount_coupon_count;

	// private TextView tv_useable_integral, tv_notice;
	// private EditText et_input_integral;
	// private String inputIntegral;

	private TextView tv_goods_num;

	public static SubmitOrderActivity instance;

	private String orderToken;
	private ImageView img_count_down;

	private double discount;
	private double b = 0;

	private TextView tv_integral_notice;
	// private SwitchButton sbt;

	private int myIntegCount = 0;// 我的积分值

	// private int discountInte;// 计算 本单可用的 积分值
	private int mDiscountInte,countVoucachers2;
	private double inteDiscount;
	private int integral = 0;

	private boolean isAddInteral = true;
	private int mDiscountInteSingle;
	
	//查询抵用券时
	private int mOne;
	private int mTwo;
	private int mFive;
	private int mTen;
	
	private int mTenUse = 0;
	private int mFiveUse = 0;
	private int mTwoUse = 0;
	private int mOneUse = 0;
	
	private	int q = 0;
	private	int w = 0;
	private	int e = 0;
	private	int r = 0;
	
	private Double price_youhui_int;
	
	private boolean isChecked_tgb_diyongquan=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		flag = false;
		context = this;
//		aBar.hide();
		instance = this;
		orderToken = YCache.getOrderToken(this);
		setContentView(R.layout.submit_order);
		msgApi = WXAPIFactory.createWXAPI(this, null);
		msgApi.registerApp(WxPayUtil.APP_ID);
		aBar.hide();
		rel_show_share = (RelativeLayout) findViewById(R.id.rel_show_share);
		img_count_down = (ImageView) findViewById(R.id.img_count_down);
		mShareVoucachers = (TextView) findViewById(R.id.submit_voucahers_tv);
		initData();
		initEdit();
	}

	private void initData() {
		
		// 查询卡券数量情况
//		new SAsyncTask<Integer, Void, List<HashMap<String, Object>>>(
//				(FragmentActivity) context, R.string.wait) {
//			
//
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context,
//					Integer... params) throws Exception {
//				return ComModel2.DiYongQuanDialog(context);
//			}
//			
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<HashMap<String, Object>> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				
//				if (e != null) {
//					
//				} else {
//						//拿到键值并设置到对应位置
//						for (int i = 0; i < result.size(); i++) {
//							String price = result.get(i).get("price").toString();
//							String snum = result.get(i).get("snum").toString();
//							int num = Integer.valueOf(snum).intValue();
//							
//							if (price.equals("1")) {
//								mOne = num;
//							}
//							
//							if (price.equals("2")) {
//								mTwo = num;
//							}
//							
//							if (price.equals("5")) {
//								mFive = num;
//							}
//							
//							if (price.equals("10")) { 
//								mTen = num;
//							}
//						}
//				}
//			}
//		}.execute();
		////////
		final RelativeLayout myView = (RelativeLayout) findViewById(R.id.myview);
		myView.setBackgroundColor(Color.WHITE);

		myView.setVisibility(View.GONE);
		new SAsyncTask<Void, Void, HashMap<String, String>>(this, myView,
				R.string.wait) {

			@Override
			protected HashMap<String, String> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getDefaultDeliverAddr(context);
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result);
				// TODO Auto-generated method stub
				// if (null != result) {
				// if (e == null) {
				myView.setVisibility(View.VISIBLE);
				initView(result);
				// }
				// }
			}

		}.execute();

		shop = (Shop) getIntent().getSerializableExtra("shop");
		size = getIntent().getStringExtra("size");
		color = getIntent().getStringExtra("color");
		shop_num = getIntent().getIntExtra("shop_num", 0);
		stock_type_id = getIntent().getIntExtra("stock_type_id", 0);
		stock = getIntent().getIntExtra("stock", 0);
		store = YCache.getCacheStoreSafe(SubmitOrderActivity.this);
		price = getIntent().getDoubleExtra("price", 0);

		// original_price = getIntent().getDoubleExtra("original_price", 0);
		original_price = shop.getCore();
		mDiscountInteSingle = original_price;
		pic = getIntent().getStringExtra("pic");

		ShopCart shopCart = new ShopCart();
		shopCart.setColor(color);

		shopCart.setShop_name(shop.getShop_name());
		shopCart.setShop_num(shop_num);
		shopCart.setShop_price(price);
		shopCart.setKickback(shop.getKickback());
		shopCart.setShop_se_price(shop.getShop_price());
		shopCart.setSize(size);
		shopCart.setShop_code(shop.getShop_code());
		listGoods = new ArrayList<ShopCart>();
		listGoods.add(shopCart);
	}

	// 获取 最优优惠券
	private void getProxCoup() {

		new SAsyncTask<Void, Void, HashMap<String, Object>>(
				(FragmentActivity) context, 0) {

			

			@Override
			protected HashMap<String, Object> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				/*
				 * List<HashMap<String, Object>> coupList = ComModel2
				 * .getMyCouponList(context, 1, ">", "1", sum + "");
				 */

				HashMap<String, Object> obj = ComModel2
						.multiOrderGetProxCoupon(context, shop.getSupp_id()
								+ ":" + sum);
				HashMap<String, Object> mapRet = null;
				if (null != obj) {
					mapRet = JSON.parseObject(obj.get(shop.getSupp_id() + "")
							+ "", new TypeReference<HashMap<String, Object>>() {
					}); // obj.get(shop.getSupp_id());
				}

				return mapRet;
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, Object> result, Exception e) {
				super.onPostExecute(context, result, e);

				if (e != null) {// 查询异常
					ToastUtil.showShortText(context, "查询异常");
				} else {
					if (null != result) {
						mapCoupon = result;
						tv_discount_coupon_count.setVisibility(View.VISIBLE);
						tv_discount_coupon_count.setText("可使用1张优惠券"
								/*+ mapCoupon.get("c_price") + "元)"*/);
						
						//显示优惠券优惠的价格		
						String price_youhui=mapCoupon.get("c_price")+"";
						
						//一堆转换 就为了去掉小数点
						Double valueOf = Double.valueOf(price_youhui);
						DecimalFormat df = new DecimalFormat("#.0");
						String format = df.format(valueOf);
					price_youhui_int = Double.valueOf(format).doubleValue();
					
						tv_price_youhui.setText("-¥"+price_youhui);		
						youhuiquan.setText("-¥"+price_youhui);
						
						
						discount = ((BigDecimal) mapCoupon.get("c_price"))
								.doubleValue();
						sum = sum - discount;

						amount = sum;
						afterCoupon = sum;

						total_account.setText("¥"
								+ new java.text.DecimalFormat("#0.0")
										.format(jiage*shop_num));
					tvTotalAccount.setText("总计：¥"
								+ new java.text.DecimalFormat("#0.0")
										.format(sum));
					} else {
						mapCoupon = new HashMap<String, Object>();
						tv_discount_coupon_count.setVisibility(View.VISIBLE);
//5.31						tv_discount_coupon_count.setText("(已选择0张)");
						tv_discount_coupon_count.setText("无可用");
						rl_youhuiquan.setVisibility(View.GONE);
						
						total_account.setText("¥"
								+ new java.text.DecimalFormat("#0.0")
										.format(jiage*shop_num));
						tvTotalAccount.setText("总计：¥"
								+ new java.text.DecimalFormat("#0.0")
										.format(sum));
						
						//显示优惠券优惠的价格		
						String price_youhui="0";
						
						//一堆转换 就为了去掉小数点
						Double valueOf = Double.valueOf(price_youhui);
						DecimalFormat df = new DecimalFormat("#.0");
						String format = df.format(valueOf);
						price_youhui_int = Double.valueOf(format).doubleValue();
						
						tv_price_youhui.setText("-¥"+price_youhui_int);		
						youhuiquan.setText("-¥"+price_youhui_int);
						
					}

				}
			}

		}.execute();
	}

	// 得到账号可用的积分
	private void getIntegralSum() {
		new SAsyncTask<Void, Void, Integer>((FragmentActivity) context, 0) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected Integer doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getMyIntegral(context);
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					Integer result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if (e == null) {
					// tv_useable_integral.setText("(可用积分：" + result + ")");
					// afterCoupon = sum;
					// myInte = result;
					/*
					 * if(result < 500){ et_input_integral.setEnabled(false); }
					 */

					// inteEdit();

					myIntegCount = result;
					if (shop_num * mDiscountInteSingle >= myIntegCount) {// 本单可用积分大于
																			// 我所持有积分
						// 故按照我的积分计算

						if (myIntegCount >= 500) {// 如果我的积分小于500，则不抵扣
							// mTgb.setEnabled(true);
							inteDiscount = 0.002 * myIntegCount;
							tv_integral_notice
									.setText(Html
											.fromHtml(getString(
													R.string.tv_useable_integ,
													myIntegCount + "",
													new java.text.DecimalFormat(
															"#0.0")
															.format(inteDiscount)
															+ "")));
							
							//积分
							tv_jifen.setText("-¥"+
											new java.text.DecimalFormat(
													"#0.0")
													.format(inteDiscount)
													+ "");

							integral = myIntegCount;
							sum -= inteDiscount;
							b = inteDiscount;
						} else {
							mTgb.setChecked(false);
							// mTgb.setEnabled(false);
						}

					} else {// 本单可用积分小于我所持有的积分，按照本单积分计算
						mDiscountInte = shop_num * mDiscountInteSingle;
						inteDiscount = 0.002 * mDiscountInte;
						tv_integral_notice.setText(Html.fromHtml(getString(
								R.string.tv_useable_integ,
								mDiscountInte + "",
								new java.text.DecimalFormat("#0.0")
										.format(inteDiscount) + "")));
						
						//积分
						tv_jifen.setText("-¥"+
								new java.text.DecimalFormat("#0.0")
										.format(inteDiscount) + "");

						integral = mDiscountInte;
						b = inteDiscount;
						sum -= inteDiscount;
					}

					total_account.setText("¥"
							+ new java.text.DecimalFormat("#0.0").format(jiage*shop_num));
					tvTotalAccount.setText("总计：¥"
							+ new java.text.DecimalFormat("#0.0").format(sum));

				}
			}

		}.execute();
	}

	/*
	 * private void inteEdit() {
	 * 
	 * et_input_integral.setOnEditorActionListener(new
	 * TextView.OnEditorActionListener() {
	 * 
	 * @Override public boolean onEditorAction(TextView v, int actionId,
	 * KeyEvent event) { sum = afterCoupon;
	 * 
	 * MyLogYiFu.e("aaaa", actionId+"");
	 * 
	 * 判断是否是“GO”键 if (actionId == KeyEvent.ACTION_DOWN || actionId ==
	 * EditorInfo.IME_ACTION_DONE ) { // 先隐藏键盘 ((InputMethodManager)
	 * et_input_integral.getContext()
	 * .getSystemService(Context.INPUT_METHOD_SERVICE))
	 * .hideSoftInputFromWindow(SubmitOrderActivity.this
	 * .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	 * 
	 * if (!TextUtils.isEmpty(et_input_integral.getText().toString())) { int
	 * integral = Integer.valueOf(et_input_integral.getText() .toString());
	 * 
	 * if (integral > myInte) {
	 * ToastUtil.showShortText(SubmitOrderActivity.this, "输入积分大于可用积分");
	 * 
	 * integral = myInte; et_input_integral.setText(myInte + "");
	 * 
	 * double inteDiscount = 0.002 * integral; sum -= inteDiscount;
	 * total_account.setText("￥" + new java.text.DecimalFormat("#0.0")
	 * .format(sum)); tvTotalAccount.setText("总计：￥" + new
	 * java.text.DecimalFormat("#0.0") .format(sum)); return true; }
	 * 
	 * if (integral > discountInte) {
	 * ToastUtil.showShortText(SubmitOrderActivity.this, "输入积分大于商品可用积分");
	 * integral = discountInte; et_input_integral.setText(discountInte + "");
	 * 
	 * double inteDiscount = 0.002 * integral; sum -= inteDiscount;
	 * total_account.setText("￥" + new java.text.DecimalFormat("#0.0")
	 * .format(sum)); tvTotalAccount.setText("总计：￥" + new
	 * java.text.DecimalFormat("#0.0") .format(sum)); return true; }
	 * 
	 * double inteDiscount = 0.002 * integral; sum -= inteDiscount;
	 * total_account.setText("￥" + new
	 * java.text.DecimalFormat("#0.0").format(sum));
	 * tvTotalAccount.setText("总计：￥" + new
	 * java.text.DecimalFormat("#0.0").format(sum));
	 *//**
	 * if(integral < 500){ ToastUtil.showShortText(SubmitOrderActivity.this,
	 * "最低输入500积分"); integral = 500; et_input_integral.setText(integral + "");
	 * return; }
	 */
	/*
	 * 
	 * }else{ double inteDiscount = 0.002 * 0; sum -= inteDiscount;
	 * total_account.setText("￥" + new
	 * java.text.DecimalFormat("#0.0").format(sum));
	 * tvTotalAccount.setText("总计：￥" + new
	 * java.text.DecimalFormat("#0.0").format(sum)); }
	 * 
	 * return true; } return false; } });
	 */

	/*
	 * et_input_integral.addTextChangedListener(new TextWatcher() {
	 * 
	 * @Override public void onTextChanged(CharSequence s, int start, int
	 * before, int count) {
	 * 
	 * }
	 * 
	 * @Override public void beforeTextChanged(CharSequence str, int start, int
	 * count, int after) { // TODO Auto-generated method stub sum = afterCoupon;
	 * MyLogYiFu.e("afterCoupon", afterCoupon+""); total_account.setText("￥" + new
	 * java.text.DecimalFormat("#0.0").format(sum));
	 * tvTotalAccount.setText("总计：￥" + new
	 * java.text.DecimalFormat("#0.0").format(sum)); }
	 * 
	 * @Override public void afterTextChanged(Editable arg0) { // TODO
	 * Auto-generated method stub sum = afterCoupon; if
	 * (!TextUtils.isEmpty(et_input_integral.getText().toString())) { int
	 * integral = Integer.valueOf(et_input_integral.getText() .toString());
	 * 
	 * if (integral > myInte) {
	 * ToastUtil.showShortText(SubmitOrderActivity.this, "输入积分大于可用积分");
	 * 
	 * integral = myInte; et_input_integral.setText(myInte + "");
	 * 
	 * double inteDiscount = 0.002 * integral; sum -= inteDiscount;
	 * total_account.setText("￥" + new java.text.DecimalFormat("#0.0")
	 * .format(sum)); tvTotalAccount.setText("总计：￥" + new
	 * java.text.DecimalFormat("#0.0") .format(sum)); return; }
	 * 
	 * if (integral > discountInte) {
	 * ToastUtil.showShortText(SubmitOrderActivity.this, "输入积分大于商品可用积分");
	 * integral = discountInte; et_input_integral.setText(discountInte + "");
	 * 
	 * double inteDiscount = 0.002 * integral; sum -= inteDiscount;
	 * total_account.setText("￥" + new java.text.DecimalFormat("#0.0")
	 * .format(sum)); tvTotalAccount.setText("总计：￥" + new
	 * java.text.DecimalFormat("#0.0") .format(sum)); return; }
	 * 
	 * double inteDiscount = 0.002 * integral; sum -= inteDiscount;
	 * total_account.setText("￥" + new
	 * java.text.DecimalFormat("#0.0").format(sum));
	 * tvTotalAccount.setText("总计：￥" + new
	 * java.text.DecimalFormat("#0.0").format(sum));
	 *//**
	 * if(integral < 500){ ToastUtil.showShortText(SubmitOrderActivity.this,
	 * "最低输入500积分"); integral = 500; et_input_integral.setText(integral + "");
	 * return; }
	 */
	/*
	 * 
	 * }
	 * 
	 * } });
	 */
	// }

	private void initView(HashMap<String, String> result) {

		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("确认订单");
		findViewById(R.id.rl_containt).setOnClickListener(this);

		tv_pro_former_price = (TextView) findViewById(R.id.tv_pro_former_price);
		tv_ProDiscount = (TextView) findViewById(R.id.tv_pro_price);// 折扣价
		// 折扣价格
		tv_ProDiscount.setText("¥"
				+ new java.text.DecimalFormat("#0.0").format(price));
		
		price_youhuijia = new java.text.DecimalFormat("#0.0").format(price);
		jiage = Double.valueOf(price_youhuijia).doubleValue();
		tv_total_account_diyongquan.setText("¥"
				+ new java.text.DecimalFormat("#0.0").format(price));
		
		String shop_price = "" + shop.getShop_price();
		
		if (!(price + "").equals(shop_price)) {
			// ToastUtil.addStrikeSpan(tv_pro_former_price, "￥:" + shop_price);
			tv_pro_former_price.setVisibility(View.VISIBLE);
			tv_pro_former_price.setText("¥"
					+ new java.text.DecimalFormat("#0.0").format(shop
							.getShop_price()));
			tv_pro_former_price.getPaint()
					.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		}

		tv_name = (TextView) findViewById(R.id.tv_name);// 收件人
		tv_phone = (TextView) findViewById(R.id.tv_phone);// 收件人电话
		tv_receiver_addr = (TextView) findViewById(R.id.tv_receiver_addr);// 收件地址
		lin_receiver_addr = (LinearLayout) findViewById(R.id.lin_receiver_addr);
		lin_receiver_addr.setOnClickListener(this);
		lin_set_addr = (LinearLayout) findViewById(R.id.lin_set_addr);
		lin_set_addr.setOnClickListener(this);

		rel_name_phone = (RelativeLayout) findViewById(R.id.rel_name_phone);

		tv_integral_notice = (TextView) findViewById(R.id.tv_integral_notice);
		tv_integral_notice.setText(Html.fromHtml(getString(
				R.string.tv_useable_integ, 0 + "", 0.0 + "")));
		//积分
		tv_jifen.setText("-¥"+Html.fromHtml(getString(
				R.string.tv_useable_integ_new, 0 + "", 0.0 + "")));
		
		
		tgb_diyongquan = (ToggleButton) findViewById(R.id.tgb_diyongquan);
//		tgb_diyongquan.setChecked(true);
		tgb_diyongquan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isChecked_tgb_diyongquan==true) {
					isChecked_tgb_diyongquan=false;
				}else {
					isChecked_tgb_diyongquan=true;
				}
				
			}
		});
		tgb_diyongquan.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked_tgb_diyongquan) {
				if (isChecked_tgb_diyongquan) {//开
//					if ((shop_num+"").equals("1")) {
						
						sum_diyongquan=kickback*shop_num;
						int userful=(int) sum_diyongquan;
	//					System.out.println("+++++userful="+userful);
						countVoucachers = countVoucachers(userful);
	//					System.out.println("+++++countVoucachers="+countVoucachers);
						tv_discount_coupon_count_diyongquan.setText("可抵用¥"+countVoucachers+"");
						//总结算抵用券
						tv_diyong.setText("-¥"+new java.text.DecimalFormat("#0").format(countVoucachers)+"");
						
						
//						tv_discount_coupon_count_diyongquan.setText("可抵用¥"+new java.text.DecimalFormat("#0").format(kickback)+"");
						//总结算抵用券
						tv_diyong.setText("-¥"+new java.text.DecimalFormat("#0").format(countVoucachers)+"");
						
						
//					}else {
//						tv_discount_coupon_count_diyongquan.setText("可抵用¥"+new java.text.DecimalFormat("#0").format(sum_diyongquan)+"");
//						//总结算抵用券
//						tv_diyong.setText("-¥"+new java.text.DecimalFormat("#0").format(sum_diyongquan)+"");
//						
//						
//					}
					
//	8
						sum -= countVoucachers;

						total_account.setText("¥"
								+ new java.text.DecimalFormat("#0.0").format(jiage*shop_num));
						tvTotalAccount.setText("总计：¥"
									+ new java.text.DecimalFormat("#0.0").format(sum));				
						
						isChecked_tgb_diyongquan=false;
				}else {//关
			
					
					sum_diyongquan=kickback*shop_num;
					int userful=(int) sum_diyongquan;
//					System.out.println("+++++userful="+userful);
					countVoucachers = countVoucachers(userful);
//					System.out.println("+++++countVoucachers="+countVoucachers);
					
					tv_discount_coupon_count_diyongquan.setText("可抵用¥0");
					
					//总结算抵用券
					tv_diyong.setText("-¥"+new java.text.DecimalFormat("#0").format(0)+"");
					
					sum += countVoucachers;

					total_account.setText("¥"
							+ new java.text.DecimalFormat("#0.0").format(jiage*shop_num));
					tvTotalAccount.setText("总计：¥"
								+ new java.text.DecimalFormat("#0.0").format(sum));		
					isChecked_tgb_diyongquan=true;
				}
				
			}
		});
		
		
		// sbt = (SwitchButton) findViewById(R.id.sbt);
		// sbt.setChecked(true);
		// sbt.setOnCheckedChangeListener(this);
		mTgb = (ToggleButton) findViewById(R.id.tgb);
		mTgb.setChecked(true);
		mTgb.setOnCheckedChangeListener(this);
		this.addResult = result;
		// dAddress = result.get(0);
		setDeliverAddress(result, null);

	}

	private void initEdit() {
		rl_youhuiquan=(RelativeLayout) findViewById(R.id.rl_youhuiquan);
		rl_jifen=(RelativeLayout) findViewById(R.id.rl_jifen);
		tv_discount_coupon_count_diyongquan = (TextView) findViewById(R.id.tv_discount_coupon_count_diyongquan);
		tv_total_account_diyongquan = (TextView) findViewById(R.id.tv_total_account_diyongquan); //商品金额价格
		tv_yunfei = (TextView) findViewById(R.id.tv_yunfei); //运费价格
		tv_price_youhui = (TextView) findViewById(R.id.tv_price_youhui);
		youhuiquan = (TextView) findViewById(R.id.youhuiquan);
		tv_jifen = (TextView) findViewById(R.id.tv_jifen);
		tv_diyong = (TextView) findViewById(R.id.tv_diyong); //结算时抵用券减免
		
//		tv_diyong.setText(text);
		
		
		tv_sum = (TextView) findViewById(R.id.tv_sum);// 商品单价x商品个数
		tv_pro_name = (TextView) findViewById(R.id.tv_pro_name);// 商品名称
		tv_pro_descri = (TextView) findViewById(R.id.tv_pro_descri);// 商品描述
		img_pro_pic = (ImageView) findViewById(R.id.img_pro_pic);// 商品图片
		btn_pay = (Button) findViewById(R.id.btn_pay);// 支付按钮
		btn_pay.setOnClickListener(this);

		// tv_settle_account = (TextView)
		// findViewById(R.id.tv_settle_account);// 合计多少钱
		tvTotalAccount = (TextView) findViewById(R.id.tv_total_account); //最后结算
		total_account = (TextView) findViewById(R.id.total_account);// 合计多少货物多少钱

		// pro_sum.setButtonBgResource(R.drawable.bg_btn_number_choose,
		// R.drawable.bg_btn_number_choose);
		// pro_sum = (AddAndSubView) findViewById(R.id.pro_sum);
		// pro_sum.setOnNumChangeListener(this);

		// pro_sum.setNum(shop_num);
		// MyLogYiFu.e("pro_sum.getNum()", pro_sum.getNum() + "");
		
		
//		new SAsyncTask<Integer, Void, List<HashMap<String, Object>>>(
//				(FragmentActivity) context, R.string.wait) {
//			
//
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context,
//					Integer... params) throws Exception {
//				return ComModel2.DiYongQuanDialog(context);
//			}
//			
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<HashMap<String, Object>> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				
//				if (e != null) {
//					
//				} else {
//						//拿到键值并设置到对应位置
//						for (int i = 0; i < result.size(); i++) {
//							String price = result.get(i).get("price").toString();
//							String snum = result.get(i).get("snum").toString();
//							int num = Integer.valueOf(snum).intValue();
//							
//							if (price.equals("1")) {
//								mOne = num;
//							}
//							
//							if (price.equals("2")) {
//								mTwo = num;
//							}
//							
//							if (price.equals("5")) {
//								mFive = num;
//							}
//							
//							if (price.equals("10")) { 
//								mTen = num;
//							}
//						}
//						
//						
//						
//						
//						kickback = shop.getKickback();
//						//显示可抵用价格
//						sum_diyongquan=kickback*shop_num;
//						int userful=(int) sum_diyongquan;
//						countVoucachers2 = countVoucachers(userful);   
//						tv_discount_coupon_count_diyongquan.setText("可抵用¥"+countVoucachers2+"");
//						if(  0 == countVoucachers2){
//							tgb_diyongquan.setChecked(false);
//						}
//						mShareVoucachers.setText(countVoucachers2 + "元抵用券");
//						//总结算抵用券
//						tv_diyong.setText("-¥"+new java.text.DecimalFormat("#0").format(countVoucachers2)+"");
//						
//						sum = price * shop_num-countVoucachers2;
//						
//						amount = sum;
//						originalPriceSum = original_price * shop_num;
//						// 价格合并？？？？
//						total_account.setText("¥:"
//								+ new java.text.DecimalFormat("#0.0").format(jiage*shop_num));
//						tvTotalAccount.setText("总计：¥"
//								+ new java.text.DecimalFormat("#0.0").format(sum));
//						// tv_sum.setText("X" + shop_num);
//
//					
//						tv_goods_num = (TextView) findViewById(R.id.tv_goods_num);// 显示当前多少个商品
//						tv_goods_num.setText(shop_num + "");
//		
//
//				//8		kickback = shop.getKickback();
////					7	tv_discount_coupon_count_diyongquan.setText("可抵用¥"+new java.text.DecimalFormat("#0").format(kickback)+"");
//				//7		tv_diyong.setText("-¥"+new java.text.DecimalFormat("#0").format(kickback)+"");
//						// 优惠券文字显示
//						tv_discount_coupon_count = (TextView) findViewById(R.id.tv_discount_coupon_count);
//						
//						// 返回上一层
//						// ivBack = (ImageView) findViewById(R.id.iv_back);
//						// ivBack.setOnClickListener(this);
//
//						/*
//						 * tv_useable_integral = (TextView)
//						 * findViewById(R.id.tv_useable_integral); tv_notice = (TextView)
//						 * findViewById(R.id.tv_notice); et_input_integral = (EditText)
//						 * findViewById(R.id.et_input_integral);
//						 */
//
//						double discount = originalPriceSum * 0.1;// 通过供应商 给的价格计算可用积分
//						// discountInte = (int) (discount * 100 * 5);
//
//						/*
//						 * tv_notice.setText("本单最多可用积分" + discountInte + ",抵用" + new
//						 * java.text.DecimalFormat("#0.0").format(discount) + "元");
//						 */
//
//						getProxCoup();
//						getIntegralSum();
//				}
//			}
//		}.execute();
		
		tv_goods_num = (TextView) findViewById(R.id.tv_goods_num);// 显示当前多少个商品
		tv_goods_num.setText(shop_num + "");
		
		tv_sum.setText("x" + shop_num);
		tv_pro_name.setText(shop.getShop_name());
		// tv_pro_descri.setText(shop.getContent() + "颜色-" + color + "   尺寸-"
		// + size);
		tv_pro_descri.setText("颜色-" + color + "   尺寸-" + size);

		String pic = shop.getShop_code().substring(1, 4) + "/"
				+ shop.getShop_code() + "/" + shop.getDef_pic();
		
		amount = sum;
		originalPriceSum = original_price * shop_num;
		
		if (null != YCache.getCacheUserSafe(this)) {
			if (YCache.getCacheUserSafe(this).getIs_member().equals("2")) {
				sum = sum * 0.95;// 当是会员的时候 打95折
			}
		}
		
		rl_discount_coupon = (RelativeLayout) findViewById(R.id.rl_discount_coupon);
		rl_discount_coupon.setOnClickListener(this);
		
//		SetImageLoader.initImageLoader(this, img_pro_pic, pic, "");
		
		PicassoUtils.initImage(this, pic, img_pro_pic);

		findViewById(R.id.btn_reduce).setOnClickListener(this);// 减少商品
		findViewById(R.id.btn_add).setOnClickListener(this);// 添加商品
		
		rl_discount_coupon.setOnClickListener(this);
/*		kickback = shop.getKickback();
		System.out.println("%%%%%%%kickback"+kickback);
		//显示可抵用价格
		sum_diyongquan=kickback*shop_num;
		System.out.println("%%%%%%%shop_num"+shop_num);
		System.out.println("%%%%%%%sum_diyongquan"+sum_diyongquan);
		int userful=(int) sum_diyongquan;
		System.out.println("%%%%%%%userful"+userful); //userful拿到了
		int countVoucachers2 = countVoucachers(userful);   //TODO  countVoucachers=0？
		System.out.println("%%%%%%%countVoucachers2"+countVoucachers2);
		tv_discount_coupon_count_diyongquan.setText("可抵用¥"+countVoucachers+"");
		//总结算抵用券
		tv_diyong.setText("-¥"+new java.text.DecimalFormat("#0").format(countVoucachers)+"");*/

		

/*		sum = price * shop_num-countVoucachers;
		if (null != YCache.getCacheUserSafe(this)) {
			if (YCache.getCacheUserSafe(this).getIs_member().equals("2")) {
				sum = sum * 0.95;// 当是会员的时候 打95折
			}
		}
		amount = sum;
		originalPriceSum = original_price * shop_num;
		// 价格合并？？？？
		total_account.setText("¥:"
				+ new java.text.DecimalFormat("#0.0").format(sum));
		tvTotalAccount.setText("总计：¥"
				+ new java.text.DecimalFormat("#0.0").format(sum));
		// tv_sum.setText("X" + shop_num);

		tv_sum.setText("x" + shop_num);
		tv_pro_name.setText(shop.getShop_name());
		// tv_pro_descri.setText(shop.getContent() + "颜色-" + color + "   尺寸-"
		// + size);
		tv_pro_descri.setText("颜色-" + color + "   尺寸-" + size);

		String pic = shop.getShop_code().substring(1, 4) + "/"
				+ shop.getShop_code() + "/" + shop.getDef_pic();

		SetImageLoader.initImageLoader(this, img_pro_pic, pic, "");

		findViewById(R.id.btn_reduce).setOnClickListener(this);// 减少商品
		findViewById(R.id.btn_add).setOnClickListener(this);// 添加商品
		tv_goods_num = (TextView) findViewById(R.id.tv_goods_num);// 显示当前多少个商品
		tv_goods_num.setText(shop_num + "");
		// 优惠券点击事件
		rl_discount_coupon = (RelativeLayout) findViewById(R.id.rl_discount_coupon);
		rl_discount_coupon.setOnClickListener(this);

//8		kickback = shop.getKickback();
//	7	tv_discount_coupon_count_diyongquan.setText("可抵用¥"+new java.text.DecimalFormat("#0").format(kickback)+"");
//7		tv_diyong.setText("-¥"+new java.text.DecimalFormat("#0").format(kickback)+"");
		// 优惠券文字显示
		tv_discount_coupon_count = (TextView) findViewById(R.id.tv_discount_coupon_count);
		
		// 返回上一层
		// ivBack = (ImageView) findViewById(R.id.iv_back);
		// ivBack.setOnClickListener(this);

		
		 * tv_useable_integral = (TextView)
		 * findViewById(R.id.tv_useable_integral); tv_notice = (TextView)
		 * findViewById(R.id.tv_notice); et_input_integral = (EditText)
		 * findViewById(R.id.et_input_integral);
		 

		double discount = originalPriceSum * 0.1;// 通过供应商 给的价格计算可用积分
		// discountInte = (int) (discount * 100 * 5);

		
		 * tv_notice.setText("本单最多可用积分" + discountInte + ",抵用" + new
		 * java.text.DecimalFormat("#0.0").format(discount) + "元");
		 

		getProxCoup();
		getIntegralSum();*/
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
			tv_receiver_addr.setText("收货地址：" + province + city + county
					+ street + dAddress.getAddress());// 收货地址
			lin_receiver_addr.setVisibility(View.VISIBLE);
			rel_name_phone.setVisibility(View.VISIBLE);
			lin_set_addr.setVisibility(View.GONE);
		} else if (null != mapRet && dAddress == null) {
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
	 * 提交订单 //没有使用抵用券，无需分享
	 * */
	private void submitOrder(final View v,final int a) {    //a=1  使用抵用券      a=2 不使用抵用券

		UserInfo user = YCache.getCacheUser(context);
		if(user.getGender() == 1){
			ToastUtil.showShortText2("系统维护中，暂不支持支付");
			return;
		}

		message = ((EditText) findViewById(R.id.edit_submit_message)).getText()
				.toString();
//		if (!TextUtils.isEmpty(message)) {
//			if (StringUtils.containsEmoji(message)) {
//				Toast.makeText(context, "不得含有特殊字符", Toast.LENGTH_SHORT).show();
//				return;
//			}
//			if (RegisterFragment.getWordCount(message) < 5) {
//				Toast.makeText(context, "订单说明不得少于五个字符", Toast.LENGTH_SHORT)
//						.show();
//				return;
//			}
//			if (RegisterFragment.getWordCount(message) > 500) {
//				Toast.makeText(context, "订单说明不得多于五百个字符", Toast.LENGTH_SHORT)
//						.show();
//				return;
//			}
//		}

		// inputIntegral = et_input_integral.getText().toString();

		final List<HashMap<String, String>> OrderShop = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("stocktypeid", stock_type_id + "");
		map.put("color", color);
		map.put("size", size);
		map.put("Shop_num", shop_num + "");
		map.put("shop_code", shop.getShop_code() + "");
		map.put("shop_pic", pic);
		map.put("message", message);
		//////////////////
		map.put("mTenUse", mTenUse+"");
		map.put("mFiveUse", mFiveUse+"");
		map.put("mTwoUse", mTwoUse+"");
		map.put("mOneUse", mOneUse+"");
		OrderShop.add(map);
		new SAsyncTask<Void, Void, HashMap<String, Object>>(this, null,
				R.string.wait) {

			private String voucherRes;
			private String mTenUse_append;
			private String mFiveUse_append;
			private String mTwoUse_append;
			private String mOneUse_append;

			@Override
			protected HashMap<String, Object> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				if (mTenUse!=0) {
					mTenUse_append = "10"+":"+mTenUse+"-";
				}else {
					mTenUse_append = "";
				}
				
				if (mFiveUse!=0) {
					mFiveUse_append = "5"+":"+mFiveUse+"-";
				}else {
					mFiveUse_append = "";
				}
				
				if (mTwoUse!=0) {
					mTwoUse_append = "2"+":"+mTwoUse+"-";
				}else {
					mTwoUse_append = "";
				}
				
				if (mOneUse!=0) {
					mOneUse_append = "1"+":"+mOneUse;
				}else {
					mOneUse_append = "";
				}
				
				String hengxian="-";
//				String voucherRes="10"+":"+mTenUse+"-"+"5"+":"+mFiveUse+"-"+"2"+":"+mTwoUse+"-"+"1"+":"+mOneUse;
				String voucherRes=mTenUse_append+mFiveUse_append+mTwoUse_append+mOneUse_append;
				
				
				String myInte = "0";
				if (isAddInteral) {
					myInte = integral + "";
				} else {
					myInte = 0 + "";
				}
				
				if (null == mapCoupon) {
					
					if( a == 0){
						return ComModel2.submitOrder(context, message,
								store.getS_code(), addressId, null, myInte + "",
								OrderShop, orderToken,0+"");
					}else if (a == 1){
						return ComModel2.submitOrder(context, message,
								store.getS_code(), addressId, null, myInte + "",
								OrderShop, orderToken,voucherRes);
					}
					
					
				} else {
					
					if(a == 0){
						return ComModel2.submitOrder(context, message,
								store.getS_code(), addressId,
								(Integer) mapCoupon.get("id"), myInte + "",
								OrderShop, orderToken,0+"");
					}else if(a == 1){
						return ComModel2.submitOrder(context, message,
								store.getS_code(), addressId,
								(Integer) mapCoupon.get("id"), myInte + "",
								OrderShop, orderToken,voucherRes);
					}
				
				}
				return null;
				
//				if (null == mapCoupon) {
//					return ComModel2.submitOrder(context, message,
//							store.getS_code(), addressId, null, myInte + "",
//							OrderShop, orderToken);
//				} else {
//					return ComModel2.submitOrder(context, message,
//							store.getS_code(), addressId,
//							(Integer) mapCoupon.get("id"), myInte + "",
//							OrderShop, orderToken);
//
//				}



			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, Object> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e) {
					orderNo = (String) result.get("order_code");
					// payMoney(v, (String) result.get("order_code"));

					/* showPayDialog(); */

					// 保存图片
					// getPicPath(shop.getShop_code(), null, result);
					SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
					// 跳转到收银台界面
					Intent intent = new Intent(SubmitOrderActivity.this,
							PaymentActivity.class);
					LogYiFu.e("TAG", "点击提交订单");
					int url = (Integer) result.get("url");
					intent.putExtra("listGoods", (Serializable) listGoods);
					intent.putExtra("result", (Serializable) result);
					intent.putExtra("totlaAccount",
							(Double) result.get("price"));
					intent.putExtra("isMulti", false);
					if (url > 1) {
						intent.putExtra("isMulti", true);
					}
					startActivityForResult(intent, 1003);
					finish();
				}
			}

		}.execute((Void[]) null);
	}

	private List<ShopCart> listGoods;

	private double sum;
	private double sum_diyongquan;
	private double amount;
	private double afterCoupon;// 使用优惠券后的价格

	private double originalPriceSum;

	private int myInte;

	private View view;
	private String jg;
	private double jiage;
	private String price_youhuijia;
	private double kickback;
	private int countVoucachers;
	

	private void addPro() {
		//显示可抵用价格
				sum_diyongquan=kickback*(shop_num+1);
				int userful=(int) sum_diyongquan;
//				System.out.println("+++++userful="+userful);
				countVoucachers = countVoucachers(userful);
//				System.out.println("+++++countVoucachers="+countVoucachers);
				if (isChecked_tgb_diyongquan==true) {
					tv_discount_coupon_count_diyongquan.setText("可抵用¥"+countVoucachers+"");
				}else {
					tv_discount_coupon_count_diyongquan.setText("可抵用¥0");
				}
//				tv_discount_coupon_count_diyongquan.setText("可抵用¥"+countVoucachers+"");
				//总结算抵用券
				tv_diyong.setText("-¥"+new java.text.DecimalFormat("#0").format(countVoucachers)+"");

		shop_num += 1;
		tv_goods_num.setText(shop_num + "");

		tv_sum.setText("x" + shop_num);
		
	
	//	price_youhuijia=jiage*shop_num+"";
		sum = price * shop_num-countVoucachers;
		if (null != YCache.getCacheUserSafe(this)) {
			if (YCache.getCacheUserSafe(this).getIs_member().equals("2")) {
				sum = sum * 0.95;// 当是会员的时候 打95折
			}
		}

		originalPriceSum = original_price * shop_num;
		total_account.setText("¥"
				+ new java.text.DecimalFormat("#0.0").format(jiage*shop_num));
		tvTotalAccount.setText("总计：¥"
				+ new java.text.DecimalFormat("#0.0").format(sum));
		
		//商品金额价格
		tv_total_account_diyongquan.setText("¥"
				+ new java.text.DecimalFormat("#0.0").format(jiage*shop_num));
		
		
		// 重新匹配 优惠券，重新输入 积分
		getProxCoup();
		amount = sum;
		// 积分重新计算最大可用积分
		// double discount = originalPriceSum * 0.1;
		// discountInte = (int) (discount * 100 * 5);
		if (isAddInteral) {
			sum = sum - b;
		}
		if (shop_num * mDiscountInteSingle < myIntegCount) {
			mDiscountInte = shop_num * mDiscountInteSingle;
			inteDiscount = 0.002 * mDiscountInte;
			sum -= inteDiscount;
			tv_integral_notice.setText(Html.fromHtml(getString(
					R.string.tv_useable_integ, mDiscountInte + "",
					new java.text.DecimalFormat("#0.0").format(inteDiscount)
							+ "")));
			
			//积分
			tv_jifen.setText("-¥"+
					new java.text.DecimalFormat("#0.0").format(inteDiscount)
							+ "");
			
			
/* bug			tgb_diyongquan.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						//显示可抵用价格
						sum_diyongquan=kickback*shop_num;
						tv_discount_coupon_count_diyongquan.setText("可抵用¥"+new java.text.DecimalFormat("#0").format(sum_diyongquan)+"");
					}else {
						tv_discount_coupon_count_diyongquan.setText("可抵用¥0");
					}
					
				}
			});*/
			

		}
		// MyLogYiFu.e("afterCoupon 11", afterCoupon+"  "+sum);
		// et_input_integral.setText("");
		// inteEdit();
		
		
		
/*33		//显示可抵用价格
		sum_diyongquan=kickback*shop_num;
		int userful=(int) sum_diyongquan;
//		System.out.println("+++++userful="+userful);
		countVoucachers = countVoucachers(userful);
//		System.out.println("+++++countVoucachers="+countVoucachers);
		tv_discount_coupon_count_diyongquan.setText("可抵用¥"+countVoucachers+"");
		//总结算抵用券
		tv_diyong.setText("-¥"+new java.text.DecimalFormat("#0").format(countVoucachers)+"");*/
		
		
		
//		mTen -= q;  
//		mFive -= w;
//		mTwo -= e;
//		mOne -= r;
	}

	private void reducePro() {
		//显示可抵用价格
		if (shop_num>1) {
			sum_diyongquan=kickback*(shop_num-1);
		}
		
		int userful=(int) sum_diyongquan;
		countVoucachers = countVoucachers(userful);
//		System.out.println("+++++countVoucachers="+countVoucachers);
		if (isChecked_tgb_diyongquan==true) {
			tv_discount_coupon_count_diyongquan.setText("可抵用¥"+countVoucachers+"");
			
		}else {
			tv_discount_coupon_count_diyongquan.setText("可抵用¥0");
		
		}
		
//		tv_discount_coupon_count_diyongquan.setText("可抵用¥"+countVoucachers+"");
		//总结算抵用券
		tv_diyong.setText("-¥"+new java.text.DecimalFormat("#0").format(countVoucachers)+"");

		if (shop_num < 2) {
			return;
		}
		shop_num -= 1;

		tv_goods_num.setText(shop_num + "");

		tv_sum.setText("x" + shop_num);

		
		price_youhuijia=jiage*shop_num+"";
		sum = price * shop_num-countVoucachers;
		if (null != YCache.getCacheUserSafe(this)) {
			if (YCache.getCacheUserSafe(this).getIs_member().equals("2")) {
				sum = sum * 0.95;// 当是会员的时候 打95折
			}
		}
		

		originalPriceSum = original_price * shop_num;
		if (isAddInteral) {
			sum = sum - b;
		}
		System.out.println("*********************" + mTest);
		total_account.setText("¥"
				+ new java.text.DecimalFormat("#0.0").format(jiage*shop_num));
	tvTotalAccount.setText("总计：¥"
				+ new java.text.DecimalFormat("#0.0").format(sum));
		
		//商品金额价格
		tv_total_account_diyongquan.setText("¥"
				+ new java.text.DecimalFormat("#0.0").format(jiage*shop_num));
		// 重新匹配 优惠券，重新输入 积分
		getProxCoup();

		amount = sum;
		afterCoupon = sum;
		// 积分重新计算最大可用积分
		// double discount = originalPriceSum * 0.1;
		// discountInte = (int) (discount * 100 * 5);
		if (shop_num * mDiscountInteSingle < myIntegCount) {
			mDiscountInte = shop_num * mDiscountInteSingle;
			inteDiscount = 0.002 * mDiscountInte;
			sum -= inteDiscount;
			tv_integral_notice.setText(Html.fromHtml(getString(
					R.string.tv_useable_integ, mDiscountInte + "",
					new java.text.DecimalFormat("#0.0").format(inteDiscount)
							+ "")));
			//积分
			tv_jifen.setText("-¥"+
					new java.text.DecimalFormat("#0.0").format(inteDiscount)
							+ "");
			

		}
		// tv_notice.setText("本单最多可用积分" + discountInte + ",抵用"
		// + new java.text.DecimalFormat("#0.0").format(discount) + "元");
		// MyLogYiFu.e("afterCoupon 11", afterCoupon+"  "+sum);
		// et_input_integral.setText("");
		// inteEdit();
	
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_reduce:// 减少
			reducePro();
			break;
		case R.id.btn_add:// 增加
			addPro();
			break;
		case R.id.btn_pay:
			// payMoney(v);
			YunYingTongJi.yunYingTongJi(SubmitOrderActivity.this, 111);
			if (addResult == null && dAddress == null) {
				ToastUtil.showShortText(this, "请设置收货地址");
				return;
			}
			
			if (tgb_diyongquan.isChecked()) {//使用抵用券，需要分享
			
				mShop_code = shop.getShop_code();
				mDef_pic = shop.getDef_pic();
				getShopLink(null);
			
			}else {		//不使用抵用券，无需分享
				submitOrder(v,0);
				System.out.println("else****isChecked_tgb_diyongquan="+isChecked_tgb_diyongquan);
			}
			
//			submitOrder(v);
			break;
		case R.id.lin_receiver_addr:
			// intent = new Intent(this, ChooseMyDeliverAddr.class);
			intent = new Intent(this, ManMyDeliverAddr.class);
			intent.putExtra("flag", "submitmultishop");
			startActivityForResult(intent, 1001);
			break;
		case R.id.lin_set_addr:
			intent = new Intent(this, SetDeliverAddressActivity.class);
			startActivityForResult(intent, 1002);
			break;
		case R.id.img_back:
			customDialog();

			break;
		case R.id.imgbtn_left_icon:
			this.finish();
			break;
		case R.id.rl_discount_coupon:

			intent = new Intent(this, UsefulCouponsActivity.class);
			intent.putExtra("amount", sum);
			startActivityForResult(intent, 1005);
			break;
		case R.id.rl_containt:

			intent = new Intent(context, ShopDetailsActivity.class);
			intent.putExtra("code", shop.getShop_code());
			startActivity(intent);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			;
			break;

		default:
			break;
		}
		super.onClick(v);
	}

	private void getShopLink(final View v) {
		/*Intent intent2 = new Intent(SubmitOrderActivity.this,ShowShareActivity.class);
		startActivity(intent2);
		*/
		
		
		new SAsyncTask<String, Void, HashMap<String, String>>(this,
				R.string.wait) {

			@Override
			protected HashMap<String, String> doInBackground(
					FragmentActivity context, String... params)
					throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getShopLinkSpecial(params[0], context, "true");
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result, e);
				/*
				 * if (e == null) { if (result.get("status").equals("1")) {
				 * MyLogYiFu.e("pic", result.get("shop_pic")); String[] picList =
				 * result.get("shop_pic").split(","); if
				 * (!TextUtils.isEmpty(result.get("four_pic")) &&
				 * !result.get("four_pic").equals("null")) { download(null,
				 * picList, listGoods.get(0) .getShop_code(), result); } }
				 * 
				 * }
				 */
				if (null == e) {
					if (result.get("status").equals("1")) {
						// 大图是900 X 900 二维码

						createSharePic(
								result.get("link"),
								(String) result.get("four_pic"),
								(String) String.valueOf((Double
										.parseDouble((String) result
												.get("shop_se_price")) - countVoucachers2)),
								mShop_code, v);
					}
					// submitZeroOrder(v);
				}
			}

		}.execute(mShop_code);
		
	}
	
	private void createSharePic(final String link, final String picPath,
			final String price, final String shop_code, final View v) {
		new SAsyncTask<Void, Void, Void>(this, R.string.wait) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected Void doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				// TODO Auto-generated method stub
				Bitmap bmQr = QRCreateUtil.createQrImage(link, 160, 160);// 得到二维码图片
//				String pic = shop_code.substring(1, 4) + "/" + shop_code + "/"
//						+ mDef_pic;
				
				String[] strs = picPath.split(",");

				String pic;
				if (strs[2] != null) {
					pic = shop_code.substring(1, 4) + "/" + shop_code + "/"
							+ strs[2];
				} else {
					pic = shop_code.substring(1, 4) + "/" + shop_code + "/"
							+ mDef_pic;
				}
				
				
				
				
				Bitmap bmBg = downloadPic(pic);

				Bitmap bmNew = QRCreateUtil.drawNewBitmap1(context, bmBg, bmQr,
						price, "");

				QRCreateUtil.saveBitmap(bmNew, YConstance.savePicPath,
						MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
				return super.doInBackground(context, params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result,
					Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if (null == e) {
					/*
					 * File file = new File(YConstance.savePicPath,
					 * MD5Tools.md5(String.valueOf(9)) + ".jpg"); share(file,
					 * v);
					 */
					rel_show_share.setVisibility(View.VISIBLE);
					time = new TimeCount(4000, 1000);
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

	private void customDialog() {
		final Dialog dialog = new Dialog(context, R.style.invate_dialog_style);
		View view = View.inflate(context, R.layout.cancle_order_dialog, null);
		TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
		tv_content.setText("这么好的宝贝，确定不要了吗？");
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 把这个对话框取消掉
				dialog.dismiss();
			}
		});
		Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				dialog.dismiss();
			}
		});

		// 创建自定义样式dialog
		dialog.setContentView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));
		dialog.setCancelable(false);
		dialog.show();
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
		} else if (requestCode == 1003 && resultCode == 1) {
			finish();
		} else if (requestCode == 1005 && resultCode == 2001) {
			sum = sum + discount;

			// sum = amount;
			mapCoupon = (HashMap<String, Object>) intent
					.getSerializableExtra("selectUseful");
			tv_discount_coupon_count.setVisibility(View.VISIBLE);
			tv_discount_coupon_count.setText("可使用1张优惠券"
					/*+ mapCoupon.get("c_price") + "元)"*/);
			
			//显示优惠券优惠的价格		
			String price_youhui=mapCoupon.get("c_price")+"";
			tv_price_youhui.setText("-¥"+price_youhui);		
			youhuiquan.setText("-¥"+price_youhui);
					
			discount = (Double) mapCoupon.get("c_price");
			sum = sum - discount;
			afterCoupon = sum;
			total_account.setText("¥"
					+ new java.text.DecimalFormat("#0.0").format(jiage*shop_num));
		tvTotalAccount.setText("总计：¥"
					+ new java.text.DecimalFormat("#0.0").format(sum));
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		isAddInteral = isChecked;

		if (isChecked) {
			
			//积分开关打开，积分和优惠券都显示出来
//			rl_youhuiquan.setVisibility(View.VISIBLE);
			rl_jifen.setVisibility(View.VISIBLE);
			
			if (shop_num * mDiscountInteSingle >= myIntegCount) {// 本单可用积分大于
																	// 我所持有积分
																	// 故按照我的积分计算

				if (myIntegCount < 500) {// 如果我的积分小于500，则不抵扣
					ToastUtil.showShortText(context, "至少使用500积分");
					mTgb.setChecked(false);
					return;
				}
				double inteDiscount = 0.002 * myIntegCount;
				a = inteDiscount;
				mTest = inteDiscount;
				String inte = new java.text.DecimalFormat("#0.0")
						.format(inteDiscount);
				tv_integral_notice.setText(Html
						.fromHtml(getString(R.string.tv_useable_integ,
								myIntegCount + "", inte + "")));
				//积分
				tv_jifen.setText("-¥"+inte + "");
				
				sum -= inteDiscount;

				total_account.setText("¥"
						+ new java.text.DecimalFormat("#0.0").format(jiage*shop_num));
			tvTotalAccount.setText("总计：¥"
						+ new java.text.DecimalFormat("#0.0").format(sum));
				return;
			} else {
				double inteDiscount = 0.002 * shop_num * mDiscountInteSingle;
				String inte = new java.text.DecimalFormat("#0.0")
						.format(inteDiscount);
				a = inteDiscount;
				mTest = inteDiscount;
				tv_integral_notice.setText(Html.fromHtml(getString(
						R.string.tv_useable_integ, shop_num
								* mDiscountInteSingle + "", inte + "")));
				//积分
				tv_jifen.setText("-¥"+ inte + "");
				
				sum -= inteDiscount;

				total_account.setText("¥"
						+ new java.text.DecimalFormat("#0.0").format(jiage*shop_num));
			tvTotalAccount.setText("总计：¥"
						+ new java.text.DecimalFormat("#0.0").format(sum));
			}

		} else {
			//积分开关关闭，积分和优惠券都消失
//			rl_youhuiquan.setVisibility(View.GONE);
			rl_jifen.setVisibility(View.GONE);
			// double inteDiscount = a;
			tv_integral_notice.setText(Html.fromHtml(getString(
					R.string.tv_useable_integ, 0 + "", 0.0 + "")));
			//积分
			tv_jifen.setText("-¥"+Html.fromHtml(getString(
					R.string.tv_useable_integ_new, 0 + "", 0.0 + "")));
			if (a == 0) {
				a = b;
			}
			sum += a;

			// integral = 0;
			total_account.setText("¥"
					+ new java.text.DecimalFormat("#0.0").format(jiage*shop_num));
		tvTotalAccount.setText("总计：¥"
					+ new java.text.DecimalFormat("#0.0").format(sum));
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回键

			customDialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		MobclickAgent.onPageStart("SubmitOrderActivity");
//		MobclickAgent.onResume(this);
		HomeWatcherReceiver.registerHomeKeyReceiver(this);
		SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1053");
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		MobclickAgent.onPageEnd("SubmitOrderActivity");
//		MobclickAgent.onPause(this);
		HomeWatcherReceiver.unregisterHomeKeyReceiver(this);
	}
	
	public int countVoucachers(int userful) {
//		int a = 0;
//		int b = 0;
//		int c = 0;
//		int d = 0;
		int mIntUse = userful;
		cicle: for (int i = 0; i <= mTen; i++) {
			if (10 * i > mIntUse) {
				q = i - 1;
				mIntUse -= (i - 1) * 10;
				for (int j = 0; j <= mFive; j++) {
					if (5 * j > mIntUse) {
						w = j - 1;
						mIntUse -= (j - 1) * 5;
						for (int k = 0; k <= mTwo; k++) {
							if (2 * k > mIntUse) {
								e = k - 1;
								mIntUse -= (k - 1) * 2;
								for (int l = 0; l <= mOne; l++) {
									if (1 * l > mIntUse) {
										r = l - 1;
										break cicle;
									} else {
										//
										if (l == mOne) {
											r = l;
											mIntUse -= l;
											userful -= mIntUse;
											break cicle;
										}

									}
								}
							} else {
								//
								if (k == mTwo) {
									e = k;
									mIntUse -= 2 * k;
									for (int l = 0; l <= mOne; l++) {
										if (1 * l > mIntUse) {
											r = l - 1;
											break cicle;
										} else {
											//
											if (l == mOne) {
												r = l;
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
							w = j;
							mIntUse -= w * 5;
							for (int k = 0; k <= mTwo; k++) {
								if (2 * k > mIntUse) {
									e = k - 1;
									mIntUse -= (k - 1) * 2;
									for (int l = 0; l <= mOne; l++) {
										if (1 * l > mIntUse) {
											r = l - 1;
											break cicle;
										} else {
											//
											if (l == mOne) {
												r = l;
												mIntUse -= l;
												userful -= mIntUse;
												break cicle;
											}

										}
									}
								} else {
									//
									if (k == mTwo) {
										e = k;
										mIntUse -= 2 * k;
										for (int l = 0; l <= mOne; l++) {
											if (1 * l > mIntUse) {
												r = l - 1;
												break cicle;
											} else {
												//
												if (l == mOne) {
													r = l;
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
					q = i;
					mIntUse -= q * 10;
					for (int j = 0; j <= mFive; j++) {
						if (5 * j > mIntUse) {
							w = j - 1;
							mIntUse -= (j - 1) * 5;
							for (int k = 0; k <= mTwo; k++) {
								if (2 * k > mIntUse) {
									e = k - 1;
									mIntUse -= (k - 1) * 2;
									for (int l = 0; l <= mOne; l++) {
										if (1 * l > mIntUse) {
											r = l - 1;
											break cicle;
										} else {
											//
											if (l == mOne) {
												r = l;
												mIntUse -= l;
												userful -= mIntUse;
												break cicle;
											}

										}
									}
								} else {
									//
									if (k == mTwo) {
										e = k;
										mIntUse -= 2 * k;
										for (int l = 0; l <= mOne; l++) {
											if (1 * l > mIntUse) {
												r = l - 1;
												break cicle;
											} else {
												//
												if (l == mOne) {
													r = l;
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
								w = j;
								mIntUse -= w * 5;
								for (int k = 0; k <= mTwo; k++) {
									if (2 * k > mIntUse) {
										e = k - 1;
										mIntUse -= (k - 1) * 2;
										for (int l = 0; l <= mOne; l++) {
											if (1 * l > mIntUse) {
												r = l - 1;
												break cicle;
											} else {
												//
												if (l == mOne) {
													r = l;
													mIntUse -= l;
													userful -= mIntUse;
													break cicle;
												}

											}
										}
									} else {
										//
										if (k == mTwo) {
											e = k;
											mIntUse -= 2 * k;
											for (int l = 0; l <= mOne; l++) {
												if (1 * l > mIntUse) {
													r = l - 1;
													break cicle;
												} else {
													//
													if (l == mOne) {
														r = l;
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
		mTenUse = q;
		mFiveUse = w;
		mTwoUse = e;
		mOneUse = r;
//		mTen -= q;  
//		mFive -= w;
//		mTwo -= e;
//		mOne -= r;
		return userful;
	}
	// 计时器
		class TimeCount extends CountDownTimer {

			public TimeCount(long millisInFuture, long countDownInterval) {
				super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔

			}

			@Override
			public void onFinish() {// 计时完毕时触发
				try {
					File file = new File(YConstance.savePicPath,
							MD5Tools.md5(String.valueOf(9)) + ".jpg");
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
		private void share(File file, final View v) {
//			MyLogYiFu.e(SUBMIT, "//1111111");
			 
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
			UMSocialService mController = UMServiceFactory
					.getUMSocialService(Constants.DESCRIPTOR_SHARE);
			// }

			mController.postShare(instance, SHARE_MEDIA.WEIXIN_CIRCLE,
					new SnsPostListener() {
						Dialog dialogShare;
						
						@Override
						public void onStart() {

						}

						@Override
						public void onComplete(SHARE_MEDIA platform, int eCode,
								SocializeEntity entity) {
							submitOrder(null,1);
						}
//							m++;
////							MyLogYiFu.e(SUBMIT, "!!!!!!"+m);
////							MyLogYiFu.e(SUBMIT, "//mController");
//							if (dialogShare == null) {
//								dialogShare = new Dialog(instance,
//										R.style.invate_dialog_style);
//							}
//							String showText = platform.toString();
//							if (eCode == StatusCode.ST_CODE_SUCCESSED) {
////								MyLogYiFu.e(SUBMIT, "//Success");
//								submitOrder(null,1);
//							} else {
//								View view = View.inflate(instance,
//										R.layout.vouchers_queen_dialog, null);
//								TextView tv = (TextView) view
//										.findViewById(R.id.voucachers_dialog_tv);
//								tv.setText("分享就能使用" + countVoucachers2 + "元抵用券哦，");
//								Button btn_cancel = (Button) view
//										.findViewById(R.id.share_cancle);
//
//								btn_cancel
//										.setOnClickListener(new OnClickListener() {
//
//											@Override
//											public void onClick(View v) {
//												flag = true;
////												for (int i = 0; i < mListTB.size(); i++) {
////													mListTB.get(i)
////															.setChecked(false);
////												}
//												tgb_diyongquan.setChecked(false);
//												dialogShare.dismiss();
//												if (dialogShare != null) {
//													dialogShare = null;
//												}
//											}
//										});
//								Button btn_ok = (Button) view
//										.findViewById(R.id.share_goon);
//								btn_ok.setOnClickListener(new OnClickListener() {
//
//									@Override
//									public void onClick(View v) {
//										flag = true;
//										dialogShare.dismiss();
//										getShopLink(v);
//
//									}
//								});
//
//								// 创建自定义样式dialog
//								dialogShare
//										.setContentView(
//												view,
//												new LinearLayout.LayoutParams(
//														LinearLayout.LayoutParams.FILL_PARENT,
//														LinearLayout.LayoutParams.FILL_PARENT));
//								dialogShare.setCancelable(false);
//								/*
//								 * showText += "平台分享失败";
//								 * Toast.makeText(MealSubmitOrderActivity.this,
//								 * showText, Toast.LENGTH_SHORT).show();
//								 */
////								MyLogYiFu.e(SUBMIT, "//show");
//								if (!flag) {
//									dialogShare.show();
//								}
////								for (int i = 0; i < mListTB.size(); i++) {
////									mListTB.get(i).setChecked(false);
////
////								}
//								tgb_diyongquan.setChecked(false);
//								// mController=null;
//							}
//						}

					});

		}

}
