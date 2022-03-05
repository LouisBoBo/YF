//package com.yssj.ui.activity.shopdetails;
//
//import java.io.File;
//import java.io.InputStream;
//import java.io.Serializable;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Random;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.bean.SocializeEntity;
//import com.umeng.socialize.bean.StatusCode;
//import com.umeng.socialize.controller.UMServiceFactory;
//import com.umeng.socialize.controller.UMSocialService;
//import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
//import com.umeng.socialize.media.UMImage;
//import com.yssj.Constants;
//import com.yssj.YConstance;
//import com.yssj.YUrl;
//import com.yssj.YConstance.Pref;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.SwitchButton;
//import com.yssj.data.DBService;
//import com.yssj.entity.DeliveryAddress;
//import com.yssj.entity.ShopCart;
//import com.yssj.entity.Store;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.HomeWatcherReceiver;
//import com.yssj.ui.activity.infos.UsefulCouponsActivity;
//import com.yssj.ui.activity.logins.RegisterFragment;
//import com.yssj.ui.activity.setting.ManMyDeliverAddr;
//import com.yssj.ui.activity.setting.SetDeliverAddressActivity;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.ui.receiver.TaskReceiver;
//import com.yssj.utils.MD5Tools;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.QRCreateUtil;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ShareUtil;
//import com.yssj.utils.SharedPreferencesUtil;
//import com.yssj.utils.StringUtils;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YCache;
//import com.yssj.utils.YunYingTongJi;
//import com.yssj.utils.sqlite.ShopCartDao;
//import com.yssj.wxpay.WxPayUtil;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.support.v4.app.FragmentActivity;
//import android.text.Html;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
///**
// * 特卖 提交多订单
// * */
//public class SubmitMultiShopSpecialActivty extends BasicActivity implements
//		OnCheckedChangeListener {
//	private int mIntegral;
//	private TextView tv_name, tv_phone, tv_receiver_addr, mTv_zero, mTv_need;
//	private boolean flag_confrim = true;
//	private LinearLayout lin_receiver_addr;
//
//	private DBService db = new DBService(this);
//
//	private TextView tv_sum, tv_pro_name, tv_pro_descri;
//	private ImageView img_pro_pic;
//	private RelativeLayout rel_show_share;
//	private LinearLayout btn_pay;
//	private TextView tv_settle_account;
//	private TextView total_account;
//	private int mIsZeroBuy = 1;
//	private int mZeroLocation = 1000;
//	private TextView tvTitle_base;
//	private LinearLayout img_back; 
//	private TextView mPostage;
//	private TextView mTv_integral;
//	private static final int SDK_PAY_FLAG = 1;
//	private AlertDialog dialog2 = null, dialogPay = null;
//	private DeliveryAddress dAddress;
//	private HashMap<String, String> addResult;
//	private String message = "";
//	private Store store;
//	private String orderNo;
//	private TaskReceiver receiver;
//	private int[] countDownBg = { R.drawable.count_down_1,
//			R.drawable.count_down_2, R.drawable.count_down_3,
//			R.drawable.count_down_3 };
//
//	private ImageView img_count_down;
//
//	Map<String, String> resultunifiedorder;
//
//	IWXAPI msgApi;// 微信api
//
//	private int addressId = 0;
//
//	private Intent intent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil
//			.getImage());
//	private LinearLayout lin_set_addr;
//
//	private List<ShopCart> listGoods;
//	private LinearLayout container;
//	private String packageCode;
//
//	// private RelativeLayout rl_discount_coupons;
//
//	double sum = 0.00;
//	private HashMap<Integer, List<ShopCart>> mapListGood = new HashMap<Integer, List<ShopCart>>();
//
//	private HashMap<Integer, String> mapMsg = new HashMap<Integer, String>();
//
//	private HashMap<Integer, EditText> mapEdit = new HashMap<Integer, EditText>();
//
//	// private HashMap<Integer, EditText> mapEditInte = new HashMap<Integer,
//	// EditText>();
//
//	private HashMap<Integer, HashMap<String, Object>> mapCoups = new HashMap<Integer, HashMap<String, Object>>();// 提交的优惠券
//
//	// private HashMap<Integer, String> mapInteg = new HashMap<Integer,
//	// String>();
//
//	private ImageView ivBack;
//
//	private RelativeLayout rel_name_phone;
//	private int CODE_PAY;
//
//	private RelativeLayout rlTotal;
//
//	private RelativeLayout rlData;
//
//	int shopNum = 0;
//
//	private int myIntegCount = 0;// 我的积分值
//
//	private HashMap<String, Object> mapCoupon;
//
//	public static SubmitMultiShopSpecialActivty instance;
//
//	private List<ShopCart> shopCartMeal = new ArrayList<ShopCart>();
//
//	private String orderToken;
//
//	private RelativeLayout rl_discount_coupon;
//	private TextView tv_discount_coupon_count, tv_integral_notice,tv_time;
//
//	private double original_price;
//
//	private int discountInte;// 计算 本单可用的 积分值
//	private double inteDiscount;
//	private int integral;
//	private String cartIds;
//	private SwitchButton sbt;
//	private double discount = 0.0;
//	private boolean isAddIntegral = true;
//	private MyTimerTask mTask;
//private boolean isClick=false;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		// shopCartsubmitConfrim();
//		aBar.hide();
//		instance = this;
//		orderToken = YCache.getOrderToken(this);
//		setContentView(R.layout.submit_multi_goods_special);
//		RelativeLayout mTouBg = (RelativeLayout) findViewById(R.id.submit_special_bg);
//		mTouBg.setBackgroundColor(Color.WHITE);
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("确认订单");
//
//		btn_pay = (LinearLayout) findViewById(R.id.btn_pay);// 支付按钮
//		btn_pay.setOnClickListener(this);
//		
//		tv_time = (TextView) findViewById(R.id.tv_time);
//		// MyLogYiFu.e("TAG", "确认下单");
//
//		listGoods = (List<ShopCart>) getIntent().getExtras().getSerializable(
//				"listGoods");
//		cartIds = getIntent().getStringExtra("mCardIds");
//		
//		recLen  =	getIntent().getLongExtra("mTime", 0);
//
//		int i = 0;
//		for (ShopCart shopCart : listGoods) {
//			// TODO:
//			i++;
//			if (!TextUtils.isEmpty(shopCart.getP_code())) {
//				List<ShopCart> list = mapListGood.get(shopCart.getSupp_id());
//				List<ShopCart> lista = new ArrayList<ShopCart>();
//				lista.add(shopCart);
//				mapListGood.put(i, lista);
//			} else {
//				shopCartMeal.add(shopCart);
//			}
//
//			sum += shopCart.getShop_se_price() * shopCart.getShop_num()
//					+ shopCart.getPostage() * shopCart.getShop_num();// 计算显示的总金额
//
//			// original_price += shopCart.getOriginal_price()
//			// * shopCart.getShop_num();// 计算原始金额，用于计算优惠券
//		}
//
//		if (null != YCache.getCacheUserSafe(context)) {
//			// if (YCache.getCacheUserSafe(context).getIs_member().equals("2"))
//			// {// 判断是否是会员
//			// sum = sum * 0.95;// 当是会员的时候 打95折
//			// }
//		}
//		// TODO:
//		// double discount = original_price * 0.1;
//		// discountInte = (int) (discount * 100 * 5);
//
//		rlData = (RelativeLayout) findViewById(R.id.rl_data);
//		msgApi = WXAPIFactory.createWXAPI(this, null);
//		msgApi.registerApp(WxPayUtil.APP_ID);
//		initData();
//		
//		//付款下单计时
//		if (mTask != null) {
//			mTask.cancel();
//			mTask = new MyTimerTask();
//		} else {
//			mTask = new MyTimerTask();
//		}
//		timer.schedule(mTask, 0, 1000); // timeTask
//		
//		store = YCache.getCacheStoreSafe(SubmitMultiShopSpecialActivty.this);
//
//	}
//
//	private void initData() {
//		rlData.setVisibility(View.GONE);
//		LogYiFu.i("TAG", "多订单");
//		new SAsyncTask<Void, Void, HashMap<String, String>>(this, R.string.wait) {
//
//			@Override
//			protected HashMap<String, String> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.getDefaultDeliverAddr(context);
//			}
//
//			protected boolean isHandleException() {
//				return true;
//			};
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, String> result, Exception e) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result, e);
//				rlData.setVisibility(View.VISIBLE);
//				initView(result);
//			}
//
//		}.execute();
//
//		// 对要付款的物品进行分类 同一家发货，不同家发货
//		// listGoods = (List<ShopCart>) getIntent().getSerializableExtra(
//		// "listGoods");
//
//	}
//
//	TimeCount time;
//
//	private void initView(HashMap<String, String> result) {
//		rel_show_share = (RelativeLayout) findViewById(R.id.rel_show_share);
//		container = (LinearLayout) findViewById(R.id.container);
//		tv_name = (TextView) findViewById(R.id.tv_name);// 收件人
//		tv_phone = (TextView) findViewById(R.id.tv_phone);// 收件人电话
//		tv_receiver_addr = (TextView) findViewById(R.id.tv_receiver_addr);// 收件地址
//		lin_receiver_addr = (LinearLayout) findViewById(R.id.lin_receiver_addr);
//		lin_receiver_addr.setOnClickListener(this);
//		lin_set_addr = (LinearLayout) findViewById(R.id.lin_set_addr);
//		lin_set_addr.setOnClickListener(this);
//		this.addResult = result;
//		// dAddress = result.get(0);
//
//		img_count_down = (ImageView) findViewById(R.id.img_count_down);
//
//
//		tv_settle_account = (TextView) findViewById(R.id.tv_settle_account);// 合计多少钱
//		total_account = (TextView) findViewById(R.id.total_account);// 合计多少货物多少钱
//
//		rel_name_phone = (RelativeLayout) findViewById(R.id.rel_name_phone);
//
//		tv_discount_coupon_count = (TextView) findViewById(R.id.tv_discount_coupon_count);
//		tv_integral_notice = (TextView) findViewById(R.id.tv_integral_notice);
//		tv_integral_notice.setText(Html.fromHtml(getString(
//				R.string.tv_useable_integ, 0 + "", 0.0 + "")));
//		rl_discount_coupon = (RelativeLayout) findViewById(R.id.rl_discount_coupon);
//		rl_discount_coupon.setOnClickListener(this);
//		rl_discount_coupon.setVisibility(View.GONE);
//		sbt = (SwitchButton) findViewById(R.id.sbt);
//		sbt.setOnCheckedChangeListener(this);
//		sbt.setChecked(true);
//		// 设置地址
//		setDeliverAddress(result, null);
//		// TODO:
//		initOther();
//		// 添加物品item
//		// addView(mapListGood, container);
//		// shop.getShop_se_price() * shop_num;
//		// sum = 0.00;
//
//		// 返回
//		// ivBack = (ImageView) findViewById(R.id.iv_back);
//		// ivBack.setOnClickListener(this);
//	}
//
//	private void initOther() {
//		for (int i = 0; i < listGoods.size(); i++) {
//			ShopCart cart = listGoods.get(i);
//			shopNum = shopNum + cart.getShop_num();
//			// sum = sum + (cart.getShop_num() * cart.getShop_se_price());
//		}
//
//		// getProxCoupon(mapListGood);
//		// TODO:
//		// getProxCoup();
//		getMyIntegral();
//		addView(mapListGood, container, null);
//	}
//
//	/** 得到我的积分 */
//	private void getMyIntegral() {
//
//		new SAsyncTask<Void, Void, Integer>((FragmentActivity) context, 0) {
//
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//			@Override
//			protected Integer doInBackground(FragmentActivity context,
//					Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.getMyIntegral(context);
//			}
//
//			@Override
//			protected void onPostExecute(final FragmentActivity context,
//					final Integer result, Exception e) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result, e);
//				if (e == null) {
//					myIntegCount = result;
//					// if(discountInte >= myIntegCount){//本单可用积分大于 我所持有积分
//					// 故按照我的积分计算
//					//
//					// if(myIntegCount >= 500){//如果我的积分小于500，则不抵扣
//					// inteDiscount = 0.002 * myIntegCount;
//					// tv_integral_notice.setText(Html.fromHtml(getString(R.string.tv_useable_integ,
//					// myIntegCount+"",inteDiscount+"")));
//					//
//					// integral = myIntegCount;
//					// sum -= inteDiscount;
//					//
//					// }else{
//					// sbt.setChecked(false);
//					// sbt.setEnabled(false);
//					// }
//					//
//					// }else{//本单可用积分小于我所持有的积分，按照本单积分计算
//					// inteDiscount = 0.002 * discountInte;
//					// tv_integral_notice.setText(Html.fromHtml(getString(R.string.tv_useable_integ,
//					// discountInte+"",inteDiscount+"")));
//					//
//					// integral = discountInte;
//					// sum -= inteDiscount;
//					// }
//					//
//					// total_account.setText(Html.fromHtml(getString(R.string.total_account,
//					// shopNum, new
//					// java.text.DecimalFormat("#0.00").format(sum))));
//					//
//					// tv_settle_account.setText("总计:￥"
//					// + new java.text.DecimalFormat("#0.00").format(sum));
//				}
//			}
//
//		}.execute();
//
//	}
//
//	boolean isProxCouponComplete = false;
//	boolean isIntegralComplete = false;
//
//	/***
//	 * 
//	 * @param et
//	 *            输入积分框
//	 * @param useableInteSum
//	 *            用户可用积分
//	 * @param inteSum
//	 *            本商品可用积分
//	 * @param sum
//	 *            商品总金额
//	 * @param afterCoupon
//	 *            商品减去优惠券的金额
//	 * @param tv
//	 *            金额显示
//	 */
//	/*
//	 * private void addOnTextChange(final EditText et, final int useableInteSum,
//	 * final int inteSum, final double afterCoupon, final double itemSum, final
//	 * TextView tv) {
//	 * 
//	 * et.addTextChangedListener(new TextWatcher() {
//	 * 
//	 * @Override public void onTextChanged(CharSequence str, int start, int
//	 * before, int after) {
//	 * 
//	 * }
//	 * 
//	 * @Override public void beforeTextChanged(CharSequence str, int start, int
//	 * count, int after) { // TODO Auto-generated method stub // sum =
//	 * afterCoupon;
//	 * 
//	 * double inteDiscount = 0.002 * 0; // sum = sum - inteDiscount;
//	 * 
//	 * double price = itemSum;
//	 * 
//	 * price -= inteDiscount; // total_account.setText("￥:" // + new
//	 * java.text.DecimalFormat("#0.00").format(sum)); //
//	 * tv_settle_account.setText("总计:￥" // + new
//	 * java.text.DecimalFormat("#0.00").format(sum)); tv.setText("￥:" + new
//	 * java.text.DecimalFormat("#0.00").format(price)); }
//	 * 
//	 * @Override public void afterTextChanged(Editable arg0) { // TODO
//	 * Auto-generated method stub
//	 * 
//	 * String str = et.getText().toString().trim(); if (!TextUtils.isEmpty(str))
//	 * { int integral = Integer.valueOf(str.toString()); if(myIntegCount <
//	 * integral){
//	 * 
//	 * ToastUtil.showShortText(context, "输入积分大于可用积分"); integral = myIntegCount;
//	 * 
//	 * // myIntegCount -= integral; double inteDiscount = 0.002 * integral; //
//	 * sum = sum - inteDiscount;
//	 * 
//	 * double price = itemSum;
//	 * 
//	 * price -= inteDiscount; // total_account.setText("￥:" // + new
//	 * java.text.DecimalFormat("#0.00").format(sum)); //
//	 * tv_settle_account.setText("总计:￥" // + new
//	 * java.text.DecimalFormat("#0.00").format(sum)); tv.setText("￥:" + new
//	 * java.text.DecimalFormat("#0.00") .format(price));
//	 * 
//	 * et.setText(integral + ""); return;
//	 * 
//	 * } if (integral > inteSum) { ToastUtil.showShortText(context,
//	 * "输入积分大于商品可用积分"); integral = inteSum;
//	 * 
//	 * // myIntegCount -= integral; double inteDiscount = 0.002 * integral; //
//	 * sum = sum - inteDiscount;
//	 * 
//	 * double price = itemSum;
//	 * 
//	 * price -= inteDiscount; // total_account.setText("￥:" // + new
//	 * java.text.DecimalFormat("#0.00").format(sum)); //
//	 * tv_settle_account.setText("总计:￥" // + new
//	 * java.text.DecimalFormat("#0.00").format(sum)); tv.setText("￥:" + new
//	 * java.text.DecimalFormat("#0.00") .format(price));
//	 * 
//	 * et.setText(integral + ""); return; }
//	 * 
//	 * if (integral > useableInteSum) { ToastUtil.showShortText(context,
//	 * "输入积分大于可用积分"); integral = useableInteSum;
//	 * 
//	 * myIntegCount -= integral; double inteDiscount = 0.002 * integral; // sum
//	 * = sum - inteDiscount;
//	 * 
//	 * double price = itemSum;
//	 * 
//	 * price -= inteDiscount; // total_account.setText("￥:" // + new
//	 * java.text.DecimalFormat("#0.00").format(sum)); //
//	 * tv_settle_account.setText("总计:￥" // + new
//	 * java.text.DecimalFormat("#0.00").format(sum)); tv.setText("￥:" + new
//	 * java.text.DecimalFormat("#0.00") .format(price));
//	 * 
//	 * et.setText(integral + ""); return; }
//	 * 
//	 * if(integral < 500){ ToastUtil.showShortText(context, "最低500抵换"); integral
//	 * = 500; }
//	 * 
//	 * 
//	 * double inteDiscount = 0.002 * integral; // sum = sum - inteDiscount;
//	 * 
//	 * double price = itemSum;
//	 * 
//	 * price -= inteDiscount; // total_account.setText("￥:" // + new
//	 * java.text.DecimalFormat("#0.00").format(sum)); //
//	 * tv_settle_account.setText("总计:￥" // + new
//	 * java.text.DecimalFormat("#0.00").format(sum)); tv.setText("￥:" + new
//	 * java.text.DecimalFormat("#0.00") .format(price));
//	 * 
//	 * // tvTotalAccount.setText("合计:￥" // + new
//	 * java.text.DecimalFormat("#0.00").format(sum)); }
//	 * 
//	 * } }); }
//	 */
//
//	// private List<EditText> listInteEdit = new ArrayList<EditText>();
//
//	// private String[] mealMessage;
//
//	/***
//	 * 
//	 * @param listGoods
//	 *            :需要付款的商品
//	 * @param container
//	 *            :显示商品的容器
//	 */
//	private void addView(HashMap<Integer, List<ShopCart>> applyList,
//			LinearLayout container, HashMap<String, Object> mapCoupon) {
//		container.removeAllViews();
//		LayoutInflater inflater = LayoutInflater.from(this);
//		Iterator<Entry<Integer, List<ShopCart>>> iterator = applyList
//				.entrySet().iterator();
//		int position = 0;
//
//		/*** 套餐的商品 */
//		// mealMessage = new String[shopCartMeal.size()];
//
//		/*
//		 * for (int j = 0; j < shopCartMeal.size(); j++) { ShopCart sc =
//		 * shopCartMeal.get(j); View view =
//		 * inflater.inflate(R.layout.meal_order, null); ImageView img_pro_pic =
//		 * (ImageView) view .findViewById(R.id.img_pro_pic); TextView
//		 * tv_pro_name = (TextView) view .findViewById(R.id.tv_pro_name);
//		 * TextView tv_pro_price = (TextView) view
//		 * .findViewById(R.id.tv_pro_price); TextView tv_pro_former_price =
//		 * (TextView) view .findViewById(R.id.tv_pro_former_price); TextView
//		 * express_money = (TextView) view .findViewById(R.id.express_money);
//		 * TextView total_account = (TextView) view
//		 * .findViewById(R.id.total_account); EditText edit_message = (EditText)
//		 * view .findViewById(R.id.edit_message);
//		 * 
//		 * mealMessage[j] = edit_message.getText().toString().trim();
//		 * 
//		 * String pic = sc.getShop_code().substring(1,
//		 * 4)+"/"+sc.getShop_code()+"/"+sc.getDef_pic();
//		 * SetImageLoader.initImageLoader(this, img_pro_pic, pic, "");
//		 * tv_pro_name.setText(sc.getShop_name());
//		 * tv_pro_price.setText(sc.getShop_se_price() + "");
//		 * tv_pro_former_price.setText(sc.getShop_price() + "");
//		 * express_money.setText(Html.fromHtml(getString(R.string.tv_mail_fee,
//		 * sc.getPostage() + "")));// 邮费 if
//		 * (!TextUtils.isEmpty(sc.getPostage())) { double count =
//		 * Double.valueOf(sc.getPostage()) + sc.getShop_se_price();
//		 * total_account.setText(count + ""); } container.addView(view); }
//		 */
//		while (iterator.hasNext()) {
//			Entry<Integer, List<ShopCart>> entry = iterator.next();
//			List<ShopCart> shopCarts = (List<ShopCart>) entry.getValue();
//			/*
//			 * String obj = null; if (null != mapCoupon.get(entry.getKey() +
//			 * "")) { obj = mapCoupon.get(entry.getKey() + "").toString(); }
//			 * HashMap<String, Object> map = null; if (null != obj) { map =
//			 * JSON.parseObject(obj, new TypeReference<HashMap<String,
//			 * Object>>() { }); mapCoups.put(entry.getKey(), map);// 如果存在优惠券
//			 * 将优惠券加上去 添加到map集合 // 请求服务器的时候需要用 }
//			 */
//			View view = inflater.inflate(R.layout.goods_item_special, null);
//			view.setBackgroundColor(Color.WHITE);
//			mTv_integral = (TextView) view
//					.findViewById(R.id.tv_useable_integral11);
//
//			mPostage = (TextView) view.findViewById(R.id.express_way);
//			mTv_zero = (TextView) view.findViewById(R.id.tv_notice_zero);
//			mTv_need = (TextView) view
//					.findViewById(R.id.et_input_integral_need);
//			// rl_discount_coupons = (RelativeLayout) view
//			// .findViewById(R.id.rl_discount_coupon);
//			// TextView tv_discount_coupon_count = (TextView) view
//			// .findViewById(R.id.tv_discount_coupon_count);
//			// TextView tv_useable_integral = (TextView) view
//			// .findViewById(R.id.tv_useable_integral);
//			// TextView tv_notice = (TextView)
//			// view.findViewById(R.id.tv_notice);
//			// EditText et_input_integral = (EditText) view
//			// .findViewById(R.id.et_input_integral);
//
//			/*
//			 * String inputInteral =
//			 * et_input_integral.getText().toString().trim(); if
//			 * (!TextUtils.isEmpty(inputInteral)) { mapInteg.put(entry.getKey(),
//			 * inputInteral); }
//			 * 
//			 * listInteEdit.add(et_input_integral);
//			 * 
//			 * mapEditInte.put(entry.getKey(), et_input_integral);
//			 */
//
//			// 设置分割线 第一条的隐藏
//			position++;
//			View line = view.findViewById(R.id.v_bottom_line);
//			if (position == 1) {
//				line.setVisibility(View.GONE);
//			}
//
//			LinearLayout good_container = (LinearLayout) view
//					.findViewById(R.id.good_container);
//			EditText edit_message = (EditText) view
//					.findViewById(R.id.edit_message);
//			mapEdit.put(entry.getKey(), edit_message);
//			// 总共的价格？？？同一个商家的价格合并
//			TextView tv_total_account = (TextView) view
//					.findViewById(R.id.total_account);
//			double sumAccount = 0;
//			double original_price = 0;
//			// 获取同一商家的购物车
//			LogYiFu.e("TAG", "同一家商品的数量---" + shopCarts.size());
//			for (int i = 0; i < shopCarts.size(); i++) {
//				// if(i==mZeroLocation&&mIsZeroBuy==0){
//				// mTv_need.setText("0");
//				// mTv_need.setTextColor(Color.parseColor("#cecece"));
//				// mTv_zero.setText("首次下单0积分");
//				// }else
//
//				if (shopCarts.get(i).getP_type() == 1) {
//					if (mIsZeroBuy == 0) {
//						mTv_need.setText("0");
//						mTv_need.setTextColor(Color.parseColor("#cecece"));
//						// mTv_zero.setText("首次下单0积分");
//						mIsZeroBuy += 1;
//					} else {
//						mTv_need.setText(shopCarts.get(i).getShop_num() * 100
//								+ "");
//						mTv_need.setTextColor(Color.parseColor("#FF3F8C"));
//						// mTv_zero.setText("本次下单抵扣"
//						// + shopCarts.get(i).getShop_num() * 100 + ""
//						// + "积分");
//					}
//				} else {
//					mTv_need.setText("0");
//					mTv_need.setTextColor(Color.parseColor("#cecece"));
//					// mTv_zero.setText("本次下单抵扣0积分");
//				}
//				final ShopCart good = shopCarts.get(i);
//				View v = inflater.inflate(R.layout.good_item_special, null);
//				ImageView img_pro_pic = (ImageView) v
//						.findViewById(R.id.img_pro_pic);
//				TextView tv_sum = (TextView) v.findViewById(R.id.tv_sum);
//				TextView tv_pro_name = (TextView) v
//						.findViewById(R.id.tv_pro_name);
//				TextView tv_meal = (TextView) v.findViewById(R.id.meal);
//				TextView tv_pro_descri = (TextView) v
//						.findViewById(R.id.tv_pro_descri);
//				TextView tv_discout = (TextView) v
//						.findViewById(R.id.tv_pro_discount);
//				// 没有打折 钱的价格
//				TextView tvProPrice = (TextView) v
//						.findViewById(R.id.tv_pro_price);
//				mPostage.setText("邮费¥"
//						+ new java.text.DecimalFormat("#0.00").format(good
//								.getPostage()));
//				// mTv_integral.setText("(账户积分余额：" + mIntegral + ")");
//				// TextView tv_sumname = (TextView)
//				// v.findViewById(R.id.tv_sumname);
//				SetImageLoader.initImageLoader(this, img_pro_pic,
//						good.getDef_pic(), "");
//				LogYiFu.e("TAG",
//						"==good=" + good.getColor() + ",size=" + good.getSize());
//				tv_sum.setText("x" + good.getShop_num());
//				tv_pro_name.setText(good.getP_name());
//				tv_meal.setText(good.getP_name());
//				tv_discout.setText("¥"
//						+ new java.text.DecimalFormat("#0.00").format(good
//								.getShop_se_price()));
//
//				// 设置没有打折的价格
//				tvProPrice.setText("¥"
//						+ new java.text.DecimalFormat("#0.00").format(good
//								.getShop_price()));
//				tvProPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//				// tvProPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//
//				// holder.tv_item_nprice.getPaint().setFlags(
//				// Paint.STRIKE_THRU_TEXT_FLAG);
//				sumAccount += good.getShop_se_price() * good.getShop_num()
//						+ good.getPostage() * good.getShop_num();
//				// original_price += good.getOriginal_price() *
//				// good.getShop_num();
//				good_container.addView(v);
//				LogYiFu.e("TAG", "添加空间（同一家）");
//				v.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View arg0) {
//						Intent intent = new Intent(context,ShopDetailsActivity.class);
//						intent.putExtra("code", good.getP_code());
//						intent.putExtra("isMeal", true);
//						startActivity(intent);
//						overridePendingTransition(R.anim.slide_left_in,
//								R.anim.slide_left_out);
//					}
//				});
//			}
//
//			tv_total_account.setText("¥"
//					+ new java.text.DecimalFormat("#0.00").format(sumAccount));
//			// double discount = original_price * 0.1;
//			// int discountInte = (int) (discount * 100 * 5);
//			// tv_notice.setText("本单最多可用积分" + discountInte + ",抵用"
//			// + new java.text.DecimalFormat("#0.00").format(discount)
//			// + "元,最低500抵换");
//			// tv_useable_integral.setText("(可用积分：" + myIntegCount + ")");
//			// 优惠券
//			/*
//			 * if (null != map) {
//			 * 
//			 * tv_discount_coupon_count.setText("(已选择1张优惠券，优惠" +
//			 * map.get("c_price") + "元)");
//			 * 
//			 * sumAccount = sumAccount -
//			 * Double.valueOf(map.get("c_price").toString()); } sum +=
//			 * sumAccount; tv_total_account.setText("¥:" + new
//			 * java.text.DecimalFormat("#0.00").format(sumAccount));
//			 * 
//			 * // 积分 double afterCoupon = sum;
//			 */
//			// addOnTextChange(et_input_integral, myIntegCount, discountInte,
//			// afterCoupon, sumAccount, tv_total_account);
//
//			container.addView(view);
//		}
//
//		total_account.setText(Html.fromHtml(getString(R.string.total_account,
//				shopNum, new java.text.DecimalFormat("#0.00").format(sum))));
//
//		tv_settle_account.setText("总计:¥"
//				+ new java.text.DecimalFormat("#0.00").format(sum));
//
//	}
//
//	// 设置地址
//	private void setDeliverAddress(HashMap<String, String> mapRet,
//			DeliveryAddress dAddress) {
//		if (null == mapRet && dAddress != null) {
//			tv_name.setText("收件人：" + dAddress.getConsignee());
//			tv_phone.setText(dAddress.getPhone());
//			String province = db.queryAreaNameById(dAddress.getProvince());
//			String city = db.queryAreaNameById(dAddress.getCity());
////			String county = db.queryAreaNameById(dAddress.getArea());
//			String county = dAddress.getArea()!=null && 0 != dAddress.getArea() ?db.queryAreaNameById(dAddress.getArea()):"";
//			String street = "";
//			if (null != dAddress.getStreet() && 0 != dAddress.getStreet()) {
//				street = db.queryAreaNameById(dAddress.getStreet());
//			}
//			tv_receiver_addr.setText("收货地址：" + province + city + county
//					+ street + dAddress.getAddress());// 收货地址
//			lin_receiver_addr.setVisibility(View.VISIBLE);
//			rel_name_phone.setVisibility(View.VISIBLE);
//			lin_set_addr.setVisibility(View.GONE);
//		} else if (null != mapRet && dAddress == null) {
//			lin_receiver_addr.setVisibility(View.VISIBLE);
//			rel_name_phone.setVisibility(View.VISIBLE);
//			lin_set_addr.setVisibility(View.GONE);
//			tv_name.setText("收件人：" + mapRet.get("consignee"));
//			tv_phone.setText(mapRet.get("phone"));
//			tv_receiver_addr.setText("收货地址：" + mapRet.get("address"));// 收货地址
//			lin_receiver_addr.setVisibility(View.VISIBLE);
//			rel_name_phone.setVisibility(View.VISIBLE);
//			lin_set_addr.setVisibility(View.GONE);
//		} else if (null == mapRet && null == dAddress) {
//			lin_receiver_addr.setVisibility(View.GONE);
//			rel_name_phone.setVisibility(View.GONE);
//			lin_set_addr.setVisibility(View.VISIBLE);
//		}
//
//	}
//
//	/**
//	 * 提交订单
//	 * */
//	private void submitOrder(final View v) {
//
//		Iterator<Entry<Integer, EditText>> iterator = mapEdit.entrySet()
//				.iterator();
//		while (iterator.hasNext()) {
//			Entry<Integer, EditText> entry = iterator.next();
//			String s = entry.getValue().getText().toString().trim();
//			if (!TextUtils.isEmpty(s)) {
//				if (StringUtils.containsEmoji(s)) {
//					Toast.makeText(context, "不得含有特殊字符", Toast.LENGTH_SHORT)
//							.show();
//					return;
//				}
//				if (RegisterFragment.getWordCount(s) < 5) {
//					Toast.makeText(context, "订单说明不得少于五个字符", Toast.LENGTH_SHORT)
//							.show();
//					return;
//				}
//				if (RegisterFragment.getWordCount(s) > 500) {
//					Toast.makeText(context, "订单说明不得多于五百个字符", Toast.LENGTH_SHORT)
//							.show();
//					return;
//				}
//				// if(s.contains("#")||s.contains("^")){
//				s.replace("#", "");
//				s.replace("^", "");
//				// }
//			}
//			mapMsg.put(entry.getKey(), s);
//		}
//
//		double inteMoney = 0.0;
//
//		final StringBuffer sb = new StringBuffer();
//
//		new SAsyncTask<Void, Void, HashMap<String, Object>>(this, v,
//				R.string.wait) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				LogYiFu.e("TAG", "mapListGood=" + mapListGood.toString());
//				// int myInte = 0;
//				// if (isAddIntegral) {
//				// myInte = integral;
//				// } else {
//				// myInte = 0;
//				// }
//				// if (null == mapCoupon.get("id")) {
//				// return ComModel2.submitShopcartSpecial(context, listGoods,
//				// addressId);
//				// }
//				return ComModel2.submitShopcartSpecial(context, mapMsg,
//						listGoods, addressId, cartIds);
//
//				// return ComModel2.submitShopcartOrders(context, mapMsg,
//				// listGoods, myInte, addressId,
//				// (Integer) mapCoupon.get("id"));
//				// return null;
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
//				if (null == e) {
//					orderNo = (String) result.get("g_code");
//					// payMoney(v, (String) result.get("order_code"));
//					// showPayDialog(aliNotifyUrl, wxPayUrl, isSingle);
//
//					int url = (Integer) result.get("url");
//
//					// 跳转到收银台支付界面
//					Intent intent = new Intent(
//							SubmitMultiShopSpecialActivty.this,
//							MealPaymentMultiActivity.class);
//					LogYiFu.e("TAG", "点击提交订单");
//					intent.putExtra("listGoods", (Serializable) listGoods);
//					intent.putExtra("result", (Serializable) result);
//					intent.putExtra("order_code", orderNo);
//					intent.putExtra("is_g_code", true);
//					intent.putExtra("totlaAccount",
//							(Double) result.get("price"));
//					ShopCartDao dao=new ShopCartDao(context);
//					for (int i = 0; i < listGoods.size(); i++) {
//						dao.p_delete(""+listGoods.get(i).getP_s_t_id());
//					}
//					// intent.putExtra("pos", value);
//					if (url > 1) {
//						intent.putExtra("isMulti", true);
//					}
//					/*
//					 * if (listGoods.size() == 1) {
//					 * getPicPath(listGoods.get(0).getShop_code(), null); }
//					 */
//					SubmitMultiShopSpecialActivty.this.setResult(1);
//					SubmitMultiShopSpecialActivty.this.startActivityForResult(
//							intent, 1003);
//					SubmitMultiShopSpecialActivty.this.finish();
//					// finish();
//					// SubmitMultiShopActivty.this.startActivityForResult(intent,
//					// CODE_PAY);
//				}
//			}
//
//		}.execute((Void[]) null);
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		Intent intent = null;
//		switch (v.getId()) {
//		case R.id.btn_pay:
////			YunYingTongJi.yunYingTongJi(SubmitMultiShopSpecialActivty.this, 111);
//			// payMoney(v);
//			if (addResult == null && dAddress == null) {
//				ToastUtil.showShortText(this, "请设置收货地址");
//				return;
//			}
//			// if (!shopCartsubmitConfrim()) {
//			// return;
//			// }
//			// submitOrder(null);
//			// // ArrayList<Integer> tpyeList= ShopCartSpecialFragment.tpyeList;
//			// MyLogYiFu.e("tpyeList",tpyeList+"");
//			//
//
//			// if(tpyeList.contains(1)){
//			// getShareShop(v);
//			// }else{
//			// submitOrder(null);
//			// }
//
//			ArrayList<ShopCart> tpyeOneList = new ArrayList<ShopCart>();
//
//			ArrayList<Integer> tpyeList = new ArrayList<Integer>();
//
//			for (int i = 0; i < listGoods.size(); i++) {
//				tpyeList.add(listGoods.get(i).getP_type());
//
//				for (int j = 0; j < tpyeList.size(); j++) {
//					if (listGoods.get(j).getP_type() == 1) {
//						tpyeOneList.add(listGoods.get(j));
//
//						// int index = (int)Math.round(tpyeOneList.size());
//
//						int index = new Random().nextInt(tpyeOneList.size());
//
//						packageCode = tpyeOneList.get(index).getP_code();
//					}
//				}
//
//			}
//			LogYiFu.e("tpyeList", tpyeList + "");
//			LogYiFu.e("listGoods", listGoods + "");
////			if (tpyeList.contains(1)) {
////				getShareShop(v);
////			} else {
////				submitOrder(null);
////			}
//			if(!isClick){
//			submitOrder(null); //特卖无需分享
//			isClick=true;
//			}
//			break;
//		case R.id.lin_receiver_addr:
//			intent = new Intent(this, ManMyDeliverAddr.class);
//			intent.putExtra("flag", "submitmultishop");
//			startActivityForResult(intent, 1001);
//			break;
//		case R.id.lin_set_addr:
//			intent = new Intent(this, SetDeliverAddressActivity.class);
//			startActivityForResult(intent, 1002);
//			break;
//		case R.id.img_back:// 返回上一级
//			customDialog();
//			break;
//		case R.id.rl_discount_coupon:
//			intent = new Intent(this, UsefulCouponsActivity.class);
//			intent.putExtra("amount", sum);
//			startActivityForResult(intent, 1005);
//			break;
//		default:
//			break;
//		}
//		super.onClick(v);
//	}
//
//	private boolean shopCartsubmitConfrim() {
//
//		new SAsyncTask<Void, Void, HashMap<String, Object>>(
//				(FragmentActivity) SubmitMultiShopSpecialActivty.this,
//				R.string.wait) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2
//						.shopCartSubmitConfrim(SubmitMultiShopSpecialActivty.this);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			// TODO:
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, Object> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (null == e && result != null) {
//					int buyCount = (Integer) result.get("buyCount");
//					int isZeroBuy = (Integer) result.get("isZeroBuy");
//					int integral = (Integer) result.get("integral");
//					mIntegral = integral;
//					mIsZeroBuy = isZeroBuy;
//
//					int status = (Integer) result.get("status");
//					int needIntegral = (Integer) result.get("needIntegral");
//					// if (status == 4) {
//					// flag_confrim = false;
//					// ToastUtil.showLongText(context, "积分不足");
//					// lackOfIntegral();
//					// return;
//					// }
//					int m = 0;// 用来记录0元区的数量
//					int count = 0;
//					List<ShopCart> listZero = new ArrayList<ShopCart>();
//					for (int i = 0; i < listGoods.size(); i++) {
//						if (listGoods.get(i).getP_type() == 1) {
//							listZero.add(listGoods.get(i));
//						}
//					}
//					for (int j = 0; j < listZero.size(); j++) {
//						if (isZeroBuy == 0) {
//							isZeroBuy++;
//							flag_confrim = true;
//						} else if (buyCount > 0) {
//							buyCount--;
//							flag_confrim = true;
//						} else {
//							count += listZero.get(j).getShop_num();
//						}
//					}
//					if (count * needIntegral > integral) {
//						flag_confrim = false;
//						ToastUtil.showLongText(context, "积分不足");
//						lackOfIntegral();
//					} else {
//						flag_confrim = true;
//					}
//
//					/*
//					 * for (int i = 0; i < listGoods.size(); i++) { if
//					 * (listGoods.get(i).getP_type() == 1) { m++; mZeroLocation
//					 * = i; if (isZeroBuy == 0) { isZeroBuy++; flag_confrim =
//					 * true; } else if (buyCount > 0) { buyCount--; flag_confrim
//					 * = true; } else if (m == 1) { if (integral -
//					 * (listGoods.get(i).getShop_num() * 100) < 0) {
//					 * flag_confrim = false; ToastUtil.showLongText(context,
//					 * "积分不足"); lackOfIntegral(); return; } else { integral -=
//					 * listGoods.get(i).getShop_num() * 100; } } else if
//					 * (listGoods.size() != 1) { if (i != listGoods.size() - 1)
//					 * { if ((integral - ((listGoods.get(i) .getShop_num()) *
//					 * 100)) >= (listGoods .get(i + 1).getShop_num()) * 100) {
//					 * integral -= (listGoods.get(i) .getShop_num()) * 100;
//					 * flag_confrim = true; } else { flag_confrim = false;
//					 * ToastUtil.showLongText(context, "积分不足");
//					 * lackOfIntegral(); return; } }
//					 * 
//					 * } else { if (integral < (listGoods.get(i).getShop_num())
//					 * * 100) { flag_confrim = false;
//					 * ToastUtil.showLongText(context, "积分不足");
//					 * lackOfIntegral(); return; } else { flag_confrim = true; }
//					 * 
//					 * } } }
//					 */
//				}
//
//			}
//		}.execute();
//		return flag_confrim;
//	}
//
//	private void lackOfIntegral() {// 积分不足，发送广播做任务
//		sendBroadcast(new Intent(TaskReceiver.newMemberTask_8));
//	}
//
//	private void customDialog() {
//		final Dialog dialog = new Dialog(context, R.style.invate_dialog_style);
//		View view = View.inflate(context, R.layout.cancle_order_dialog, null);
//		TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
//		tv_content.setText("这么好的宝贝，确定不要了吗？");
//		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
//
//		btn_cancel.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// 把这个对话框取消掉
//				dialog.dismiss();
//			}
//		});
//		Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
//		btn_ok.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
////				Intent intent = new Intent(SubmitMultiShopSpecialActivty.this,
////						ShopCartNewNewActivity.class);
////				intent.putExtra("submit", "special");
////				startActivity(intent);
//				finish();
//			}
//		});
//
//		// 创建自定义样式dialog
//		dialog.setContentView(view, new LinearLayout.LayoutParams(
//				LinearLayout.LayoutParams.FILL_PARENT,
//				LinearLayout.LayoutParams.FILL_PARENT));
//		dialog.setCancelable(false);
//		dialog.show();
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode,
//			Intent intent) {
//		super.onActivityResult(requestCode, resultCode, intent);
//		if (requestCode == 1001) { // 修改地址
//			if (null != intent) {
//				dAddress = (DeliveryAddress) intent
//						.getSerializableExtra("item");
//				addressId = dAddress.getId();
//				setDeliverAddress(null, dAddress);
//			}
//		} else if (requestCode == 1002) { // 设置地址
//			initData();
//		} else if (requestCode == 1003 && resultCode == 1) {
//			SubmitMultiShopSpecialActivty.this.setResult(1); // 1，代表支付成功；
//			LogYiFu.e("TAG", "提交订单，刷新购物车。。");
//			finish();
//		} else if (requestCode == 1005 && resultCode == 2001) {
//			// sum = sum + discount;
//
//			// sum = amount;
//			mapCoupon = (HashMap<String, Object>) intent
//					.getSerializableExtra("selectUseful");
//			// tv_discount_coupon_count.setVisibility(View.VISIBLE);
//			// tv_discount_coupon_count.setText("(已选择1张优惠券，优惠"
//			// + mapCoupon.get("c_price") + "元)");
//			// discount = (Double) mapCoupon.get("c_price");
//			// sum = sum - discount;
//			total_account.setText(Html.fromHtml(getString(
//					R.string.total_account, shopNum,
//					new java.text.DecimalFormat("#0.00").format(sum))));
//
//			tv_settle_account.setText("总计:¥"
//					+ new java.text.DecimalFormat("#0.00").format(sum));
//		}
//	}
//
//	@Override
//	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//		// TODO Auto-generated method stub
//		isAddIntegral = isChecked;
//		if (isChecked) {
//			// double inteDiscount = 0.002 * integral;
//			tv_integral_notice.setText(Html
//					.fromHtml(getString(R.string.tv_useable_integ, integral
//							+ "", inteDiscount + "")));
//
//			// sum -= inteDiscount;
//
//			total_account.setText(Html.fromHtml(getString(
//					R.string.total_account, shopNum,
//					new java.text.DecimalFormat("#0.00").format(sum))));
//
//			tv_settle_account.setText("总计:¥"
//					+ new java.text.DecimalFormat("#0.00").format(sum));
//
//		} else {
//			// double inteDiscount = 0.002 * integral;
//			tv_integral_notice.setText(Html.fromHtml(getString(
//					R.string.tv_useable_integ, 0 + "", 0.0 + "")));
//
//			// sum += inteDiscount;
//
//			total_account.setText(Html.fromHtml(getString(
//					R.string.total_account, shopNum,
//					new java.text.DecimalFormat("#0.00").format(sum))));
//
//			tv_settle_account.setText("总计:¥"
//					+ new java.text.DecimalFormat("#0.00").format(sum));
//		}
//	}
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//			// 这里重写返回键
//
//			customDialog();
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//
//	/**
//	 * 得到分享的商品
//	 * **/
//	private void getShareShop(final View v) {
//		new SAsyncTask<Void, Void, HashMap<String, Object>>(this, v,
//				R.string.wait) {
//
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//			@Override
//			protected HashMap<String, Object> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.getSharePShopInfo(context, packageCode, true);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, Object> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (null == e) {
//					if ((Integer) result.get("status") == 1) {
//						// 大图是900 X 900 二维码
//
//						createSharePic((String) result.get("link"),
//								(String) result.get("def_pic"),
//								(String) result.get("price"),
//								(String) result.get("shop_code"), v);
//					}
//					// submitZeroOrder(v);
//				}
//			}
//
//		}.execute();
//	}
//
//	private void createSharePic(final String link, final String picPath,
//			final String price, final String shop_code, final View v) {
//		new SAsyncTask<Void, Void, Void>(this, R.string.wait) {
//
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//			@Override
//			protected Void doInBackground(FragmentActivity context,
//					Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				Bitmap bmQr = QRCreateUtil.createQrImage(link, 160, 160);// 得到二维码图片
//				String pic = shop_code.substring(1, 4) + "/" + shop_code + "/"
//						+ picPath;
//				Bitmap bmBg = downloadPic(pic);
//
//				Bitmap bmNew = QRCreateUtil.drawNewBitmap1(context, bmBg, bmQr,
//						price, "");
//
//				QRCreateUtil.saveBitmap(bmNew, YConstance.savePicPath,
//						MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
//				return super.doInBackground(context, params);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, Void result,
//					Exception e) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result, e);
//				if (null == e) {
//					File file = new File(YConstance.savePicPath,
//							MD5Tools.md5(String.valueOf(9)) + ".jpg");
//					rel_show_share.setVisibility(View.VISIBLE);
//					time = new TimeCount(4000, 1000);
//					time.start();
//				}
//			}
//
//		}.execute();
//	}
//
//	@Override
//	public void onBackPressed() {
//		super.onBackPressed();
//		if (null != time) {
//			time.cancel();
//		}
//	}
//
//	private Bitmap downloadPic(String picPath) {
//		try {
//			URL url = new URL(YUrl.imgurl + picPath);
//			// 打开连接
//			URLConnection con = url.openConnection();
//			// 获得文件的长度
//			int contentLength = con.getContentLength();
//			System.out.println("长度 :" + contentLength);
//			// 输入流
//			InputStream is = con.getInputStream();
//			// 1K的数据缓冲
//			byte[] bs = new byte[8192];
//			// 读取到的数据长度
//			int len;
//			BitmapDrawable bmpDraw = new BitmapDrawable(is);
//
//			// 完毕，关闭所有链接
//			is.close();
//			return bmpDraw.getBitmap();
//		} catch (Exception e) {
//			LogYiFu.e("TAG", "下载失败");
//
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	// 计时器
//	class TimeCount extends CountDownTimer {
//
//		public TimeCount(long millisInFuture, long countDownInterval) {
//			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
//
//		}
//
//		@Override
//		public void onFinish() {// 计时完毕时触发
//			try {
//				File file = new File(YConstance.savePicPath,
//						MD5Tools.md5(String.valueOf(9)) + ".jpg");
//				share(file, null);
//				// onceShare(intent, "微信");
//
//				rel_show_share.setVisibility(View.GONE);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		@Override
//		public void onTick(long millisUntilFinished) {// 计时过程显示
//			try {
//				// btn.setEnabled(false);
//				// btn.setBackgroundResource(R.color.time_count);
//				// text.setText(millisUntilFinished / 1000 + "秒后将自动微信分享");
//				int i = Integer.valueOf(millisUntilFinished / 1000 + "") - 1;
//				img_count_down.setImageResource(countDownBg[i]);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public UMSocialService mController = UMServiceFactory
//			.getUMSocialService(Constants.DESCRIPTOR_SHARE);
//
//	private void share(File file, final View v) {
//
//		if (file == null) {
//			Toast.makeText(this, "您的网络状态不太好哦~~", Toast.LENGTH_SHORT).show();
//			return;
//		}
//
//		UMImage umImage = new UMImage(this, file);
//		ShareUtil.configPlatforms(this);
//		ShareUtil.shareShop(this, umImage);
//
//		mController.postShare(this, SHARE_MEDIA.WEIXIN_CIRCLE,
//				new SnsPostListener() {
//
//					@Override
//					public void onStart() {
//
//					}
//
//					@Override
//					public void onComplete(SHARE_MEDIA platform, int eCode,
//							SocializeEntity entity) {
//						String showText = platform.toString();
//						if (eCode == StatusCode.ST_CODE_SUCCESSED) {
//							submitOrder(null);
//						} else {
//							/*
//							 * showText += "平台分享失败";
//							 * Toast.makeText(MealSubmitOrderActivity.this,
//							 * showText, Toast.LENGTH_SHORT).show();
//							 */
//						}
//					}
//				});
//
//	}
//
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		// MobclickAgent.onPageStart("MealSubmitOrderActivity");
//		// MobclickAgent.onResume(this);
//		receiver = new TaskReceiver(this);
//		TaskReceiver.regiserReceiver(this, receiver);
//		isClick=false;
//		HomeWatcherReceiver.registerHomeKeyReceiver(this);
//		SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1053");
//	
//	}
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		HomeWatcherReceiver.unregisterHomeKeyReceiver(this);
//	}
////	private long recLen = 30 * 1000 * 60;
//	private long recLen = 0;
//	Timer timer=new Timer();
//	//倒计时
//		class MyTimerTask extends TimerTask {
//
//			@Override
//			public void run() {
//				runOnUiThread(new Runnable() { // UI thread
//							@Override
//							public void run() {
//								recLen -= 1000;
//								String days;
//								String hours;
//								String minutes;
//								String seconds;
//								long minute = recLen / 60000;
//								long second = (recLen % 60000) / 1000;
//								if (minute >= 60) {
//									long hour = minute / 60;
//									minute = minute % 60;
//									if (hour >= 24) {
//										long day = hour / 24;
//										hour = hour % 24;
//										if (day < 10) {
//											days = "0" + day;
//										} else {
//											days = "" + day;
//										}
//										if (hour < 10) {
//											hours = "0" + hour;
//										} else {
//											hours = "" + hour;
//										}
//										if (minute < 10) {
//											minutes = "0" + minute;
//										} else {
//											minutes = "" + minute;
//										}
//										if (second < 10) {
//											seconds = "0" + second;
//										} else {
//											seconds = "" + second;
//										}
//										tv_time.setText("" + days + "天" + hours
//												+ ":" + minutes + ":" + seconds
//												);
//									} else {
//										if (hour < 10) {
//											hours = "0" + hour;
//										} else {
//											hours = "" + hour;
//										}
//										if (minute < 10) {
//											minutes = "0" + minute;
//										} else {
//											minutes = "" + minute;
//										}
//										if (second < 10) {
//											seconds = "0" + second;
//										} else {
//											seconds = "" + second;
//										}
//										tv_time.setText("" + hours + ":" + minutes
//												+ ":" + seconds );
//									}
//								} else if (minute >= 10 && second >= 10) {
//									tv_time.setText("" + minute + ":" + second
//											 );
//								} else if (minute >= 10 && second < 10) {
//									tv_time.setText("" + minute + ":0" + second
//											);
//								} else if (minute < 10 && second >= 10) {
//									tv_time.setText("0" + minute + ":" + second
//											);
//								} else {
//									tv_time.setText("0" + minute + ":0" + second
//											);
//								}
//								// tv_time.setText("" + recLen);
//								if (recLen <  0) {
//									timer.cancel();
////									tv_time.setText("00:00");
//									tv_time.setVisibility(View.GONE);
////									btn_pay.setBackgroundColor(Color.parseColor("#a8a8a8"));
////									btn_pay.setClickable(false);
//									
//								}
//							}
//						});
//			}
//
//		}
//
//}
