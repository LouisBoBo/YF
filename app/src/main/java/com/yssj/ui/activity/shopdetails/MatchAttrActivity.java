package com.yssj.ui.activity.shopdetails;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.time.DateFormatUtils;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.bouncing_entrances.BounceInAnimator;
import com.yssj.YConstance;
import com.yssj.YUrl;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.activity.R.drawable;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.MatchAttrNav;
import com.yssj.custom.view.MyGridView;
import com.yssj.custom.view.MyListView;
import com.yssj.entity.MatchAttr;
import com.yssj.entity.MatchAttr.ShopAttrBean;
import com.yssj.entity.MatchAttr.StocktypeBean;
import com.yssj.entity.MatchShop.AttrList;
import com.yssj.entity.MatchShop.CollocationShop;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ShopCart;
import com.yssj.entity.StockType;
import com.yssj.huanxin.activity.BaseActivity;
import com.yssj.model.ComModel2;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.activity.ShopCartNewNewActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;
import com.yssj.utils.YunYingTongJi;

public class MatchAttrActivity extends BaseActivity implements OnClickListener {
	private ActionBar aBar;
	private LinearLayout mBack;
	private Context mContext;
	// private ImageView mCartIv;// 购物车
	// private TextView tv_cart_count;// 购物车数量
	// private TextView tv_time_count_down;
	private TextView mAddCartTv;// 加入购物车
	private RelativeLayout img_cart_cart;
	private ImageView mSelectIv;
	private TextView mSaveTv, mSaveTv2;// 节省
	private LinearLayout mSaveLl;
	// private LinearLayout mLJJS;// 立即结算
	private RelativeLayout rl_retain;// 显示或消失 保留30分钟提醒 rl_retain
	private Timer timer;
	private TimerTask task;
	private long recLen;
	// private int cartCount;
	private String collocation_code;
	private String shop_code;
	private String parent_ids;
	private DecimalFormat df;
	private MatchAttr mMatchAttr;
	private List<CollocationShop> collocationShopsList;// 搭配的商品集合
	private HashMap<String, String> shopAttrHashMap;// 搭配商品 shop_attr
	private List<AttrList> attrList;// 搭配的商品属性集合
	private SAsyncTask<String, Void, MatchAttr> STask;
	// private ListView attrListView;
	private LinearLayout attrLL;

	private boolean[] selectFlag;
	private boolean allSelect = true;
	private boolean allNotSelect;
	private double totlePrice;
	private double savePrice;
	// private List<View> listItem;// Adapter item的集合
	private HashMap<Integer, View> listItem;
	private int allNum;// 选择的总件数
	private int[] shop_num;// 每件商品选组的数量的数组

	private int userId;
	private String store_code;
	private String[] color;
	private String[] size;
	private String[] pics;// 选中图片的集合
	private String[] cores;// 可用积分集合
	// private Drawable[] ds;
	// private ImageView [] imgs;
	private String[] supp_id;// 供应商
	private String[] shop_name;// 商品名称
	private String[] kickbacks;//

	private List<StocktypeBean> stocktypeBeans;
	// private List<MatchStockBean> mList;

	private String headPic;// 保存选中属性对应的商品 图片URL
	private String matchDiscount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_attr);
		AppManager.getAppManager().addActivity(this);
		aBar = getActionBar();
		aBar.setDisplayHomeAsUpEnabled(true);
		aBar.hide();
		mContext = this;
		Intent intent = getIntent();
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			collocation_code = bundle.getString("collocation_code");
			shop_code = bundle.getString("shop_code");
			// collocationShopsList = (List<CollocationShop>) bundle
			// .getSerializable("colShopsList");
			attrList = (List<AttrList>) bundle.getSerializable("attrList");
		}

		matchDiscount = SharedPreferencesUtil.getStringData(mContext, Pref.DPZHEKOU,"0.95");
		if("0".equals(matchDiscount)){
			matchDiscount = "0.95";
		}

		initView();
		// queryCartCount();
		querryAttr();
		setEvent();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeWatcherReceiver.registerHomeKeyReceiver(mContext);
		SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TYPE, "1051");
		if (mMatchAttr == null) {
			mAddCartTv.setClickable(false);
			mAddCartTv.setBackgroundColor(Color.rgb(197, 197, 197));
		} else {
			mAddCartTv.setClickable(true);
			mAddCartTv.setBackgroundColor(Color.rgb(255, 63, 139));
		}
		// queryCartCount();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		HomeWatcherReceiver.unregisterHomeKeyReceiver(mContext);
	}
	
	private void initView() {
		mBack = (LinearLayout) findViewById(R.id.img_back);
		mBack.setOnClickListener(this);
		img_cart_cart = (RelativeLayout) findViewById(R.id.img_cart_cart);
		// mCartIv = (ImageView) findViewById(R.id.img_cart_new);
		// tv_cart_count = (TextView) findViewById(R.id.tv_cart_count);
		// tv_time_count_down = (TextView)
		// findViewById(R.id.tv_time_count_down);// 购物车数量旁的倒计时
		// tv_time_count_down.setVisibility(View.GONE);
		mAddCartTv = (TextView) findViewById(R.id.tv_add_shop_car);
		mSelectIv = (ImageView) findViewById(R.id.match_attr_save_select);
		mSaveTv = (TextView) findViewById(R.id.match_attr_save_tv);
		mSaveTv2 = (TextView) findViewById(R.id.match_attr_save_tv2);
		mSaveLl = (LinearLayout) findViewById(R.id.match_attr_save_ll);
		// mLJJS = (LinearLayout) findViewById(R.id.match_ljjs_ll);
		rl_retain = (RelativeLayout) findViewById(R.id.match_attr_ljjs_rl);
		// mCartIv.setOnClickListener(this);
		// findViewById(R.id.img_cart).setOnClickListener(this);//购物车父布局点击监听
		mAddCartTv.setOnClickListener(this);
		mSelectIv.setOnClickListener(this);
		// mLJJS.setOnClickListener(this);
		mSelectIv.setVisibility(View.GONE);

		// attrListView = (ListView) findViewById(R.id.match_attr_lv);
		attrLL = (LinearLayout) findViewById(R.id.match_attr_ll_contains);
		TextView ray_bottom_left_tv = ((TextView) findViewById(R.id.ray_bottom_left_tv));

		String discountText =  "任意两件商品\n可享受搭配购"+new DecimalFormat("#0.#").format(Double.parseDouble(matchDiscount)*10)+"折优惠";
		SpannableString ssDiscountText = new SpannableString(discountText);
		ssDiscountText.setSpan(new ForegroundColorSpan(Color.argb(0Xff, 0Xff, 0X3f, 0X8b)), 13,
				discountText.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ray_bottom_left_tv.setText(ssDiscountText);

		df = new DecimalFormat("#0.00");

		userId = YCache.getCacheUser(mContext).getUser_id();
		store_code = YCache.getCacheStore(mContext).getS_code();
		stocktypeBeans = new ArrayList<StocktypeBean>();
		collocationShopsList = new ArrayList<CollocationShop>();
		// rootView = (RelativeLayout) findViewById(R.id.match_attr_root);

	}

	private void setEvent() {

	}

	/**
	 * 搭配 库存及属性
	 */
	private void querryAttr() {
		STask = new SAsyncTask<String, Void, MatchAttr>(this, null, R.string.wait) {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				LoadingDialog.show(MatchAttrActivity.this);
			}

			@Override
			protected MatchAttr doInBackground(FragmentActivity context, String... params) throws Exception {
				return ComModel2.getMatchAttr(mContext, params[0], true);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, MatchAttr result, Exception e) {
				 if (e != null || result == null) {// 查询异常
					return;
				}
				mMatchAttr = result;
				ShopAttrBean shopAttrBean = mMatchAttr.getShop_attr();
				shopAttrHashMap = shopAttrBean.getShopAttrHashMap();//

				List<StocktypeBean> stocktypeBeanList = mMatchAttr.getStocktype();

				// List<MatchStockBean> listStocks = new
				// ArrayList<MatchStockBean>();
				for (int j = 0; j < stocktypeBeanList.size(); j++) {
					boolean flag = false;
					for (int m = 0; m < stocktypeBeans.size(); m++) {
						if (stocktypeBeans.get(m).getShop_code().equals((stocktypeBeanList.get(j).getShop_code()))) {
							flag = true;
							break;
						}
					}
					if (!flag) {
						stocktypeBeans.add(stocktypeBeanList.get(j));
					}

				}

				// 相同shop_Code 只存放一件
				for (int j = 0; j < stocktypeBeanList.size(); j++) {
					boolean flag = false;
					for (int m = 0; m < collocationShopsList.size(); m++) {
						if (collocationShopsList.get(m).getShop_code()
								.equals((stocktypeBeanList.get(j).getShop_code()))) {
							flag = true;
							break;
						}
					}
					if (!flag) {
						CollocationShop shop = new CollocationShop();
						shop.setShop_code(stocktypeBeanList.get(j).getShop_code());
						shop.setShop_name(stocktypeBeanList.get(j).getShop_name());
						shop.setShop_se_price(Double.valueOf(stocktypeBeanList.get(j).getPrice()));
						collocationShopsList.add(shop);
					}
				}

				stockIds = new Integer[collocationShopsList.size()];
				prices = new Double[collocationShopsList.size()];
				stocks = new Integer[collocationShopsList.size()];
				color = new String[collocationShopsList.size()];
				size = new String[collocationShopsList.size()];
				supp_id = new String[collocationShopsList.size()];
				shop_name = new String[collocationShopsList.size()];
				cores = new String[collocationShopsList.size()];
				pics = new String[collocationShopsList.size()];
				// ds = new Drawable[collocationShopsList.size()];
				// imgs = new ImageView[collocationShopsList.size()];
				// anim_mask_layouts = new
				// ViewGroup[collocationShopsList.size()];
				shopPrices = new Double[collocationShopsList.size()];
				originalPrice = new Double[collocationShopsList.size()];
				kickbacks = new String[collocationShopsList.size()];

				if (stocktypeBeans.size() == 0) {
					mAddCartTv.setClickable(false);
					mSelectIv.setClickable(false);
					mSelectIv.setImageResource(R.drawable.icon_dapeigou_normal);
					mAddCartTv.setBackgroundColor(Color.rgb(197, 197, 197));
				} else {
					mAddCartTv.setBackgroundColor(Color.rgb(255, 63, 139));
					mAddCartTv.setClickable(true);
				}
				// 初始化
				selectFlag = new boolean[collocationShopsList.size()];
				for (int i = 0; i < selectFlag.length; i++) {
					selectFlag[i] = true;// 默认全部选中
				}

				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				for (int i = 0; i < result.getStocktype().size(); i++) {
					hashMap.put(result.getStocktype().get(i).getShop_code(), result.getStocktype().get(i).getPrice());
				}
				for (Object keyName : hashMap.keySet()) {
					totlePrice += Double.valueOf((String) hashMap.get(keyName));
				}
				shop_num = new int[collocationShopsList.size()];
				for (int i = 0; i < shop_num.length; i++) {
					shop_num[i] = 1;
				}
				allNum = collocationShopsList.size();// 默认每样选中一件
				setSaveSpan(totlePrice, selectFlag, allNum);
				mSaveLl.setVisibility(View.VISIBLE);

				/**
				 * 将每一个独立商品 包含的属性 放在CollocationShop类的List_stock_type集合中
				 */
				for (int s = 0; s < collocationShopsList.size(); s++) {
					List<StocktypeBean> listStock = new ArrayList<StocktypeBean>();
					for (int k = 0; k < mMatchAttr.getStocktype().size(); k++) {
						if (collocationShopsList.get(s).getShop_code()
								.equals(mMatchAttr.getStocktype().get(k).getShop_code())) {
							listStock.add(mMatchAttr.getStocktype().get(k));
						}
					}
					collocationShopsList.get(s).setList_stock_type(listStock);
				}

				// if (collocationShopsList != null
				// && collocationShopsList.size() > 0) {
				// MatchAttrListAdapter attrAdapter = new MatchAttrListAdapter(
				// mContext, collocationShopsList);
				// attrListView.setAdapter(attrAdapter);
				// }
				// MatchAttrListAdapter attrAdapter = new MatchAttrListAdapter(
				// mContext, stocktypeBeans);
				// attrListView.setAdapter(attrAdapter);
				addAttrView(mContext, stocktypeBeans);

			}

			@Override
			protected boolean isHandleException() {
				return true;
			};

		};
		STask.execute(shop_code);
	}

	/**
	 * 节省 字符串处理
	 */
	private void setSaveSpan(double price, boolean[] selectFlag, int allNum) {

		if (stocktypeBeans.size() == 0) {
			allNum = 0;
		}
		boolean allNumsFlag = true; // 所有的选中数量都大于0
		for (int i = 0; i < selectFlag.length; i++) {
			if (shop_num[i] == 0) {
				allNumsFlag = false;
				break;
			}
		}
		boolean allNumsFlag2 = true; // 所有的选中数量都等于0
		for (int i = 0; i < selectFlag.length; i++) {
			if (shop_num[i] > 0) {
				allNumsFlag2 = false;
				break;
			}
		}
		// 商品数量大于三件的时候 选中两件以上 可以9折优惠
		int selectNum = 0;
		; // 所有的选中数量都等于0
		if (selectFlag.length > 2) {
			for (int i = 0; i < selectFlag.length; i++) {
				if (selectFlag[i] == true) {
					selectNum++;
				}
			}
		}

		// N件商品 搭配购为你节省0
		if (!allSelect && !allNotSelect && allNumsFlag && selectFlag.length == 2 || selectFlag.length == 1 && allNum > 0
				|| selectFlag.length > 2 && selectNum < 2) {
			String showText = String.format(mContext.getResources().getString(R.string.match_save1),
					String.valueOf(allNum), String.valueOf(df.format(price)));
			SpannableString SshowText = new SpannableString(showText);
			SshowText.setSpan(new ForegroundColorSpan(Color.argb(0Xff, 0Xff, 0X3f, 0X8b)),
					String.valueOf(allNum).length() + 4, showText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			mSaveTv.setText(SshowText);

			String showText2 = String.format(mContext.getResources().getString(R.string.match_save2), "0.0");
			SpannableString SshowText2 = new SpannableString(showText2);
			SshowText2.setSpan(new ForegroundColorSpan(Color.argb(0Xff, 0Xff, 0X3f, 0X8b)), 8, showText2.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			mSaveTv2.setText(SshowText2);

		} else
		// 2件商品 95折
		if (allSelect && allNumsFlag || selectFlag.length > 2 && selectNum >= 2) {
			String totlePrice = df.format(price * Double.parseDouble(matchDiscount));
			String savePrice = df.format(price * (1-Double.parseDouble(matchDiscount)));
			if (price == 0) {
				totlePrice = "0.0";
				savePrice = "0.0";
			}
			String showText = String.format(mContext.getResources().getString(R.string.match_save1),
					String.valueOf(allNum), totlePrice);
			SpannableString SshowText1 = new SpannableString(showText);
			SshowText1.setSpan(new ForegroundColorSpan(Color.argb(0Xff, 0Xff, 0X3f, 0X8b)),
					String.valueOf(allNum).length() + 4, SshowText1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			mSaveTv.setText(SshowText1);

			String showText2 = String.format(mContext.getResources().getString(R.string.match_save2), savePrice);
			SpannableString SshowText2 = new SpannableString(showText2);
			SshowText2.setSpan(new ForegroundColorSpan(Color.argb(0Xff, 0Xff, 0X3f, 0X8b)), 8, showText2.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			mSaveTv2.setText(SshowText2);
		} else
		// 0件商品 搭配购为你节省0
		if (allNotSelect || allNumsFlag2) {
			String showText = String.format(mContext.getResources().getString(R.string.match_save1), "0", "0.0");
			SpannableString SshowText = new SpannableString(showText);
			SshowText.setSpan(new ForegroundColorSpan(Color.argb(0Xff, 0Xff, 0X3f, 0X8b)), "0".length() + 4,
					showText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			mSaveTv.setText(SshowText);

			String showText2 = String.format(mContext.getResources().getString(R.string.match_save2), "0.0");
			SpannableString SshowText2 = new SpannableString(showText2);
			SshowText2.setSpan(new ForegroundColorSpan(Color.argb(0Xff, 0Xff, 0X3f, 0X8b)), 8, showText2.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			mSaveTv2.setText(SshowText2);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			onBackPressed();
			break;
		case R.id.match_attr_save_select:
			totlePrice = 0;
			allNum = 0;
			if (allSelect) {
				allSelect = false;
				for (int i = 0; i < selectFlag.length; i++) {
					selectFlag[i] = false;

				}
				allNotSelect = true;
				mSelectIv.setImageResource(R.drawable.icon_dapeigou_normal);
			} else {
				allSelect = true;
				for (int i = 0; i < selectFlag.length; i++) {
					selectFlag[i] = true;
				}
				allNotSelect = false;
				mSelectIv.setImageResource(R.drawable.icon_dapeigou_celect);

			}

			for (int i = 0; i < listItem.size(); i++) {
				MatchAttrNav matchAttrNav = (MatchAttrNav) listItem.get(i).findViewById(R.id.match_attr_list_nav);
				shop_num[i] = Integer.valueOf(matchAttrNav.getNumTv().getText().toString());
				if (allSelect) {
					matchAttrNav.getSelectIv().setImageResource(R.drawable.icon_dapeigou_celect);
				} else {
					matchAttrNav.getSelectIv().setImageResource(R.drawable.icon_dapeigou_normal);
				}
				if (selectFlag[i]) {
					addTotalPrice(matchAttrNav);
				}

				if (selectFlag[i]) {// 只加选中的
					String numStr = matchAttrNav.getNumTv().getText().toString();
					allNum += Integer.valueOf(numStr);
				}
			}

			setSaveSpan(totlePrice, selectFlag, allNum);

			break;
		case R.id.tv_add_shop_car://结算
			YunYingTongJi.yunYingTongJi(mContext, 110);
			// for (int i = 0; i < selectFlag.length; i++) {
			// if(selectFlag[i]){
			//// anim_mask_layouts[i] = createAnimLayout();
			// mAddCartTv.setClickable(false);
			//// addAnimLayout();
			// break;
			// }
			// }
			addShop();
			break;
		// case R.id.match_ljjs_ll://立即结算
		//// case R.id.img_cart_new://购物车
		// case R.id.img_cart:
		// Intent intent = new Intent(mContext, ShopCartNewNewActivity.class);
		// startActivity(intent);
		// break;
		default:
			break;
		}
	}

	/**
	 * 添加购物车 之前的准备数据
	 */
	private void addShop() {

		if (allNotSelect) {
			ToastUtil.showShortText(mContext, "请选择商品！");
			return;
		}
		// for (int i = 0; i < mList.size(); i++) {
		// mList.remove(i);
		// }
		// for (int i = 0; i < mLists.length; i++) {
		// if (mLists[i] != null && mLists[i].getSupp_id() != null) {
		// mList.add(mLists[i]);
		// }
		// }

		// double shop_price = 0.00;
		// double shop_se_price = 0.00;
		// double original_price = 0.00;
		// List<HashMap<String, String>> catJson = new ArrayList<HashMap<String,
		// String>>();
		List<ShopCart> listGoods = new ArrayList<ShopCart>();
		for (int j = 0; j < collocationShopsList.size(); j++) {
			MatchAttrNav matchAttrNav = (MatchAttrNav) listItem.get(j).findViewById(R.id.match_attr_list_nav);
			CollocationShop shop = collocationShopsList.get(j);
			// HashMap<String, String> map = new HashMap<String, String>();

			// map.put("shop_name", shop.getShop_name());
			// map.put("shop_price", prices[j]+"");

			if (selectFlag[j]) {
				int ss = j + 1;
				String[] str = mapChoose.get(j);
				for (int k = 0; k < str.length; k++) {
					if (null == str[k]) {
						ToastUtil.showShortText(mContext, "请选择商品" + ss + mapChoosedName.get(j)[k]);
						return;
					}
				}

				if (null == stockIds[j]) {
					ToastUtil.showShortText(mContext, "商品" + ss + "无库存");
					return;
				}
				// map.put("shop_code", shop.getShop_code());
				// map.put("shop_num", matchAttrNav.getNumTv().getText()
				// .toString());
				// map.put("size", size[j]);
				// map.put("color", color[j]);
				// map.put("shop_price", df.format(shopPrices[j]) + "");
				// map.put("shop_se_price", df.format(prices[j]) + "");
				// map.put("original_price", df.format(originalPrice[j]) + "");
				// map.put("def_pic", pics[j]);
				// map.put("stock_type_id", stockIds[j] + "");
				// map.put("supp_id", supp_id[j]);
				// map.put("shop_name", shop_name[j]);
				// map.put("kickback", kickbacks[j]);
				//
				// map.put("user_id", userId + "");
				// map.put("store_code", store_code);
				// original_price += originalPrice[j];
				// shop_price += shopPrices[j];
				// shop_se_price += prices[j];

				// if (map != null) {
				// catJson.add(map);
				// }

				ShopCart shopCart = new ShopCart();
				shopCart.setShop_code(shop.getShop_code());
				shopCart.setShop_num(Integer.valueOf(matchAttrNav.getNumTv().getText().toString()));
				shopCart.setSize(size[j]);
				shopCart.setColor(color[j]);
				shopCart.setShop_price(shopPrices[j]);
				shopCart.setShop_se_price(prices[j]);
				shopCart.setOriginal_price(Double.parseDouble(originalPrice[j].intValue() + ""));
				shopCart.setDef_pic(pics[j]);
				shopCart.setCore(cores[j]);
				shopCart.setStock_type_id(stockIds[j]);
				shopCart.setSupp_id(Integer.valueOf(supp_id[j]));
				shopCart.setShop_name(shop_name[j]);
				shopCart.setKickback(Double.valueOf(kickbacks[j]));
				shopCart.setUser_id(userId);
				shopCart.setStore_code(store_code);
				listGoods.add(shopCart);
			}
		}
		// addShopCart(catJson);//加入购物车
		getOrderNo(listGoods);// 结算确认订单
	}

	// private Drawable d;
	// private ImageView img;
	/**
	 * 加入购物车
	 */
	// private void addShopCart(final List<HashMap<String, String>> catJson) {
	//
	// new SAsyncTask<Void, Void, ReturnInfo>(this, R.string.wait) {
	// @Override
	// protected boolean isHandleException() {
	// return true;
	// }
	//
	// @Override
	// protected ReturnInfo doInBackground(FragmentActivity context,
	// Void... params) throws Exception {
	//// try {
	////// String def_pic = "";
	//// for (int i = 0; i < collocationShopsList.size(); i++) {
	//// if (selectFlag[i]) {
	//// if(!TextUtils.isEmpty(pics[i])){
	//// String shopCode =stocktypeBeans.get(i).getShop_code();
	//// String pic =
	// shopCode.substring(1,4)+File.separator+shopCode+File.separator+pics[i];
	//// URL url = new URL(YUrl.imgurl + pic + "!180");
	//// URLConnection conn = url.openConnection();
	//// InputStream is = conn.getInputStream();
	//// ds[i]= BitmapDrawable.createFromStream(is, null);
	//// }else{
	//// ds[i] = null;
	//// }
	//// }else {
	//// ds[i] = null;
	//// }
	//// }
	////
	//// } catch (Exception e) {
	//// }
	//
	// return ComModel2.addMatch(context, catJson, collocation_code);
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	//
	// };
	//
	// @Override
	// protected void onPostExecute(FragmentActivity context,
	// ReturnInfo result, Exception e) {
	// if (null == e) {
	//// for(int i = 0; i < ds.length; i++) {
	//// if(ds[i]!=null&&imgs[i] == null){
	//// imgs[i] = new ImageView(mContext);
	//// imgs[i].setLayoutParams(new LayoutParams(100, 100));
	//// imgs[i].setImageDrawable(ds[i]);
	//// MatchAttrNav matchAttrNav= (MatchAttrNav)
	// listItem.get(i).findViewById(R.id.match_attr_list_nav);
	//// int[] location = new int[2];
	//// matchAttrNav.getHeadIv().getLocationOnScreen(location);
	//// setAnim(null,location,i);
	//// }else{
	//// imgs[i] = null;
	//// anim_mask_layouts[i]=null;
	//// }
	//// }
	// setAnim();
	// }
	// super.onPostExecute(context, result, e);
	// }
	//
	// }.execute();
	// }

	/**
	 * 返回
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// overridePendingTransition(R.anim.slide_left_in,
		// R.anim.slide_left_out);
		if (STask != null && !STask.isCancelled()) {
			STask.cancel(true);
		}
	}

	private void subTotalPrice(MatchAttrNav matchAttrNav) {
		String numStr = matchAttrNav.getNumTv().getText().toString();
		String sePriceStr = matchAttrNav.getSePriceTv().getText().toString().substring(1);
		double price = Double.valueOf(sePriceStr);
		int num = Integer.valueOf(numStr);
		totlePrice -= price * num;
	}

	private void addTotalPrice(MatchAttrNav matchAttrNav) {
		String numStr = matchAttrNav.getNumTv().getText().toString();
		String sePriceStr = matchAttrNav.getSePriceTv().getText().toString().substring(1);
		double price = Double.valueOf(sePriceStr);
		int num = Integer.valueOf(numStr);
		totlePrice += price * num;
	}

	private Integer[] stockIds;
	private Double[] prices;// 售价
	private Double[] shopPrices;//
	private Double[] originalPrice;//
	// private MatchStockBean[] mLists;

	private Integer[] stocks;// 搭配商品的库存数组

	private HashMap<Integer, String[]> mapChoose = new HashMap<Integer, String[]>();
	private HashMap<Integer, String[]> mapChoosedName = new HashMap<Integer, String[]>();
	private int buyStock;// 每一个Item下面的商品需要购买的数量

	private void addAttrView(Context context, List<StocktypeBean> listData) {
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		listItem = new HashMap<Integer, View>();
		for (int pos = 0; pos < listData.size(); pos++) {
			final int position = pos;
			if (listItem.containsKey(position)) {
				continue;
			}
			View view = mInflater.inflate(R.layout.activity_match_attr_list, null);
			LinearLayout containsLl = (LinearLayout) view.findViewById(R.id.match_attr_list_contains);
			MatchAttrNav matchAttrNav = (MatchAttrNav) view.findViewById(R.id.match_attr_list_nav);

			containsLl.removeAllViews();
			// CollocationShop cShop = listData.get(position);// 搭配商品中的一件商品
			StocktypeBean cShop = listData.get(position);
			// final List<StocktypeBean> listsTypes =
			// cShop.getList_stock_type();// 商品中的库存属性集合
			final List<StocktypeBean> listsTypes = collocationShopsList.get(position).getList_stock_type();// 商品中的库存属性集合
			String name = cShop.getShop_name();
			String shop_code = cShop.getShop_code();
			// String price = listsTypes.get(0).getShop_price();
			// String sePrice = listsTypes.get(0).getPrice();
			// String headPic = listsTypes.get(0).getPic();
			String price = cShop.getShop_price();
			String sePrice = cShop.getPrice();
			headPic = cShop.getPic();
			matchAttrNav.setTextView(name, price);
			matchAttrNav.setSePrice(sePrice);
			matchAttrNav.setImage(headPic, shop_code);

			ImageView addIv = (ImageView) matchAttrNav.findViewById(R.id.img_add);
			ImageView subIv = (ImageView) matchAttrNav.findViewById(R.id.img_reduce);
			ImageView selectIv = (ImageView) matchAttrNav.findViewById(R.id.activity_match_select_iv);
			// listItem.add(view);
			listItem.put(position, view);

			/***
			 * 保存Attr数据
			 **/
			final HashMap<Integer, List<String>> listMapAttrName = new HashMap<Integer, List<String>>();
			final HashMap<Integer, List<String>> listMapAttrIds = new HashMap<Integer, List<String>>();

			for (int k = 0; k < listsTypes.size(); k++) {
				StocktypeBean sType = listsTypes.get(k);
				String[] st = sType.getColor_size().split(":");
				for (int j = 0; j < st.length; j++) {

					/**
					 * 在attrList中比对shop_attr的前面两个 数值 找出 属性的名称
					 */
					String attrName = "";
					// 通过属性ID找出属性名称 并保存
					for (int i = 0; i < attrList.size(); i++) {
						String attrId = attrList.get(i).getId();
						if (st[j].equals(attrId)) {
							attrName = attrList.get(i).getAttr_name();
						}
					}

					List<String> listAttrNames = listMapAttrName.get(j);

					if (listAttrNames != null) {
						if (!listAttrNames.contains(attrName)) {
							listAttrNames.add(attrName);
						}
					} else {
						List<String> lista = new ArrayList<String>();
						lista.add(attrName);
						listMapAttrName.put(j, lista);
					}

					// 保存属性ID
					List<String> listAttrIds = listMapAttrIds.get(j);
					if (listAttrIds != null) {
						if (!listAttrIds.contains(st[j])) {
							listAttrIds.add(st[j]);
						}
					} else {
						List<String> lista = new ArrayList<String>();
						lista.add(st[j]);
						listMapAttrIds.put(j, lista);
					}

				}

			}

			String[] st = listsTypes.get(0).getColor_size().split(":");// 判断当前页面几个属性
			final String[] itemClicked = new String[st.length];
			final String[] itemClickedName = new String[st.length];
			for (int j = 0; j < st.length; j++) {
				View itemView = mInflater.inflate(R.layout.match_attrs_container, null);

				TextView tv_shop_attr = (TextView) itemView.findViewById(R.id.tv_shop_attr);
				final MyGridView gridview_shop_attr = (MyGridView) itemView.findViewById(R.id.gridview_shop_attr);

				String sCode = listsTypes.get(0).getShop_code();
				String shop_attrs = shopAttrHashMap.get(sCode);
				String[] shop_attr = shop_attrs.split("_");

				String attrParentName = "";
				for (int i = 0; i < attrList.size(); i++) {
					String[] ids = shop_attr[j].split(",");
					String attrParent_id = attrList.get(i).getParent_id();
					String attrId = attrList.get(i).getId();
					if (ids[0].equals(attrParent_id) && ids[1].equals(attrId)) {
						attrParentName = attrList.get(i).getAttr_name();
					}
				}

				tv_shop_attr.setText(attrParentName);// 查询出商品属性的父类
														// 显示
				itemClickedName[j] = attrParentName;
				final int currentPos = j;// 每个item的第几个gridview

				final GridViewAdpater mAdapter = new GridViewAdpater(listMapAttrName.get(j), position, currentPos,
						itemClicked, listMapAttrIds, listsTypes);
				gridview_shop_attr.setAdapter(mAdapter);
				containsLl.addView(itemView);

				gridview_shop_attr.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						mAdapter.setPosition(arg2);
						mAdapter.notifyDataSetChanged();
						itemClicked[currentPos] = listMapAttrIds.get(currentPos).get(arg2);
						showStock(itemClicked, listsTypes, position, currentPos, listItem);
					}
				});
			}

			mapChoose.put(position, itemClicked);
			mapChoosedName.put(position, itemClickedName);

			addIv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					buyNumersClothes(0, itemClicked, position);
				}
			});
			subIv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					buyNumersClothes(1, itemClicked, position);
				}
			});

			selectIv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					selectClothes(position, listItem);
				}
			});
			attrLL.addView(view);
		}
	}

	// class Holder {
	// LinearLayout containsLl;
	// MatchAttrNav matchAttrNav;
	// }

	/**
	 * @param position
	 * @param listItem
	 */
	private void selectClothes(int position, HashMap<Integer, View> listItem) {
		MatchAttrNav matchAttrNav = (MatchAttrNav) listItem.get(position).findViewById(R.id.match_attr_list_nav);
		// shop_num[position] = Integer.valueOf(matchAttrNav.getNumTv()
		// .getText().toString());
		if (selectFlag[position] == true) {
			selectFlag[position] = false;

		} else {
			selectFlag[position] = true;
		}

		matchAttrNav.setSelectIv(selectFlag[position]);
		/**
		 * 是否全部选中
		 */
		for (int i = 0; i < selectFlag.length; i++) {
			if (!selectFlag[i]) {
				allSelect = false;
				break;
			}
			allSelect = true;
		}

		/**
		 * 是否全部没有选中
		 */
		for (int i = 0; i < selectFlag.length; i++) {
			if (selectFlag[i]) {
				allNotSelect = false;
				break;
			}
			allNotSelect = true;
		}

		if (!allSelect) {
			mSelectIv.setImageResource(R.drawable.icon_dapeigou_normal);
		} else {
			mSelectIv.setImageResource(R.drawable.icon_dapeigou_celect);
		}

		if (!selectFlag[position]) {
			subTotalPrice(matchAttrNav);
		} else {
			addTotalPrice(matchAttrNav);
		}

		String numStr = matchAttrNav.getNumTv().getText().toString();
		if (selectFlag[position]) {// 只加选中的
			allNum += Integer.valueOf(numStr);
		} else {
			allNum -= Integer.valueOf(numStr);
		}

		setSaveSpan(totlePrice, selectFlag, allNum);
	}

	/***
	 * 设置要买的数量
	 * 
	 * @param type
	 */
	private void buyNumersClothes(int type, String[] itemClick, int position) {
		MatchAttrNav matchAttrNav = (MatchAttrNav) listItem.get(position).findViewById(R.id.match_attr_list_nav);
		// 当前商品没有选中 则不能设置购买数量
		if (!selectFlag[position]) {
			return;
		}
		for (int i = 0; i < itemClick.length; i++) {
			if (itemClick[i] == null) {
				ToastUtil.showShortText(mContext, "请选择颜色和尺码");
				return;
			} else if (itemClick[0] == null) {
				ToastUtil.showShortText(mContext, "请选择颜色");
				return;
			} else if (itemClick[1] == null) {
				ToastUtil.showShortText(mContext, "请选择尺码");
				return;
			}
		}

		if (stocks[position] <= 0) {
			return;
		}

		buyStock = Integer.parseInt(matchAttrNav.getNumText());
		if (type == 0 && buyStock < stocks[position]) {
			buyStock++;
			if (buyStock > 1) {
				matchAttrNav.getSubIv().setImageResource(R.drawable.icon_jian);
			}
			matchAttrNav.setNumText(String.valueOf(buyStock));
			shop_num[position] = buyStock;
			totlePrice += prices[position];
			allNum++;
		} else if (type == 1 && buyStock > 1) {
			buyStock--;
			if (buyStock <= 1) {
				matchAttrNav.getSubIv().setImageResource(R.drawable.icon_jian_disable);
			}
			matchAttrNav.setNumText(String.valueOf(buyStock));
			shop_num[position] = buyStock;
			totlePrice -= prices[position];
			allNum--;
		}
		// selectClothes(position, listItem);
		// allNum = 0;
		// for (int i = 0; i < selectFlag.length; i++) {
		// MatchAttrNav matchAttrNav2 = (MatchAttrNav) (listItem.get(i)
		// .findViewById(R.id.match_attr_list_nav));
		// shop_num[i] = Integer.valueOf(matchAttrNav2.getNumText()
		// .toString());
		// if (selectFlag[i]) {
		// allNum += Integer.valueOf(matchAttrNav2.getNumText()
		// .toString());
		// }
		//
		// }
		setSaveSpan(totlePrice, selectFlag, allNum);
	}

	/**
	 * 选择属性
	 */
	private void showStock(String[] strs, List<StocktypeBean> listsTypes, int position, int curPos,
			HashMap<Integer, View> listItem) {

		MatchAttrNav matchAttrNav = (MatchAttrNav) listItem.get(position).findViewById(R.id.match_attr_list_nav);

		for (int i = 0; i < listsTypes.size(); i++) {
			if ((listsTypes.get(i).getColor_size().split(":")[0]).equals(strs[0]) && curPos == 0) {
				if (headPic.equals(listsTypes.get(i).getPic())) {
					// 点击的是同一种颜色 相同图片
				} else {
					headPic = listsTypes.get(i).getPic();
					matchAttrNav.setImage(listsTypes.get(i).getPic(), listsTypes.get(i).getShop_code());
				}
			}
		}
		for (int i = 0; i < strs.length; i++) {
			LogYiFu.e("itemClicked", "itemClicked[]" + i + "  " + position + "   " + strs[i]);
		}
		for (int i = 0; i < strs.length; i++) {
			if (strs[i] == null) {
				return;
			}
		}

		/**
		 * 把选中的商品属性的ID 用“：”拼接
		 */
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < strs.length; j++) {
			sb.append(strs[j]);
			if (j != strs.length - 1) {
				sb.append(":");
			}
		}

		/**
		 * 获取颜色和尺码的集合
		 */
		for (int i = 0; i < attrList.size(); i++) {
			if (attrList.get(i).getId().equals(strs[0])) {
				color[position] = attrList.get(i).getAttr_name();
			}
			if (attrList.get(i).getId().equals(strs[1])) {
				size[position] = attrList.get(i).getAttr_name();
			}
		}

		for (int i = 0; i < listsTypes.size(); i++) {
			if (listsTypes.get(i).getColor_size().equals(sb.toString())) {
				supp_id[position] = listsTypes.get(i).getSupp_id();
				shop_name[position] = listsTypes.get(i).getShop_name();
				kickbacks[position] = listsTypes.get(i).getKickback();

				int stock = Integer.valueOf(listsTypes.get(i).getStock());
				stocks[position] = stock;
				int stock_type_id = Integer.valueOf(listsTypes.get(i).getId());
				stockIds[position] = stock_type_id;

				prices[position] = Double.valueOf(listsTypes.get(i).getPrice());
				shopPrices[position] = Double.valueOf(listsTypes.get(i).getShop_price());
				originalPrice[position] = Double.valueOf(listsTypes.get(i).getOriginal_price());
				pics[position] = listsTypes.get(i).getPic();
				cores[position] = listsTypes.get(i).getCores();
				break;
			} else {
				stockIds[position] = null;
				prices[position] = null;
				pics[position] = null;
			}
		}
	}

	/**
	 * 颜色尺码
	 *
	 */
	class GridViewAdpater extends BaseAdapter {
		private List<String> list;
		private int p = -1;
		// private int type = -1;
		private int i;
		private int j;
		private String[] itemClicked;
		private HashMap<Integer, List<String>> listMapAttrIds;
		private List<StocktypeBean> listsTypes;

		public GridViewAdpater(List<String> list, int i, int j, String[] itemClicked,
				HashMap<Integer, List<String>> listMapAttrIds, List<StocktypeBean> listsTypes) {

			this.list = list;
			// this.type = type;
			this.i = i;
			this.j = j;
			this.itemClicked = itemClicked;
			this.listMapAttrIds = listMapAttrIds;
			this.listsTypes = listsTypes;

		}

		public void setPosition(int p) {
			this.p = p;

		}

		@Override
		public int getCount() {

			return list.size();
		}

		@Override
		public Object getItem(int position) {

			return list.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_shop_details, null);
			}
			ImageView img_clothes_property = (ImageView) convertView.findViewById(R.id.img_clothes_property);
			TextView tv_clothes_property = (TextView) convertView.findViewById(R.id.tv_clothes_property);
			tv_clothes_property.setVisibility(View.GONE);

			String str = list.get(position);
			tv_clothes_property.setText(str);
			if (position == p || list.size() == 1) {
				itemClicked[j] = listMapAttrIds.get(j).get(position);
				showStock(itemClicked, listsTypes, i, j, listItem);
				tv_clothes_property.setBackgroundResource(R.drawable.shape_match_attr);
				tv_clothes_property.setTextColor(mContext.getResources().getColor(R.color.white));
			} else {
				if (position == 0 && p == -1) {
					itemClicked[j] = listMapAttrIds.get(j).get(0);
					showStock(itemClicked, listsTypes, i, j, listItem);
					tv_clothes_property.setBackgroundResource(R.drawable.shape_match_attr);
					tv_clothes_property.setTextColor(mContext.getResources().getColor(R.color.white));
				} else {
					tv_clothes_property.setBackgroundResource(R.drawable.grid_bg_match);
					tv_clothes_property.setTextColor(Color.parseColor("#7D7D7D"));
				}
			}
			img_clothes_property.setVisibility(View.GONE);
			tv_clothes_property.setVisibility(View.VISIBLE);

			return convertView;
		}
	}

	// 添加购物车的时候 查询购物车数量并显示倒计时
	// private void queryCartCountAdd() {
	//
	// new SAsyncTask<Void, Void, HashMap<String, Object>>(this, R.string.wait)
	// {
	//
	//
	// @Override
	// protected HashMap<String, Object> doInBackground(FragmentActivity
	// context,
	// Void... params) throws Exception {
	// return ComModel2.getMatchShopCartCount(context);
	// }
	//
	// @Override
	// protected boolean isHandleException() {
	// return true;
	// }
	//
	// @Override
	// protected void onPostExecute(FragmentActivity context,
	// HashMap<String, Object> result, Exception e) {
	// super.onPostExecute(context, result, e);
	// mAddCartTv.setClickable(true);
	// if (e != null || result == null) {
	// return;
	// }
	// String count = (String) result.get("cart_count");
	// cartCount = Integer.valueOf(count);
	// if (cartCount > 0) {
	//// tv_cart_count.setVisibility(View.VISIBLE);
	// tv_cart_count.setText(count);
	//// tv_time_count_down.setVisibility(View.VISIBLE);
	// } else {
	//// tv_cart_count.setVisibility(View.INVISIBLE);
	//// tv_time_count_down.setVisibility(View.GONE);
	// }
	//
	// Long sTime = (Long)result.get("s_time");// 系统当前时间
	// Long sDeadline = (Long)result.get("s_deadline");//商品过期时间
	//
	// //购物车倒计时
	// if (sDeadline>0&&cartCount>0) {//正数代表加入了购物车 显示倒计时
	// tv_time_count_down.setVisibility(View.VISIBLE);
	// String c_time_cart = DateFormatUtils.format(sDeadline,"yyyy-MM-dd
	// HH:mm:ss");
	// String s_time = DateFormatUtils.format(sTime,"yyyy-MM-dd HH:mm:ss");
	//
	// try {
	// if (timer!=null) {
	// timer.cancel();
	// }
	// task = new TimerTask() {
	// @Override
	// public void run() {
	//
	// runOnUiThread(new Runnable() { // UI thread
	// @Override
	// public void run() {
	// takeTime();
	// }
	// });
	// }
	// };
	// timer = new Timer();
	// timer.schedule(task, 0, 1000); // 显示倒计时
	//
	// SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// Date d1 = df1.parse(c_time_cart);
	// Date d2 = df1.parse(s_time);
	// long diff = d1.getTime() - d2.getTime();
	// recLen = diff;
	// } catch (ParseException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// }else {//负数代表或0没有加入购物车
	// tv_time_count_down.setVisibility(View.GONE);
	// }
	// }
	// }.execute();
	// }
	// 查询购物车数量并显示倒计时
	// private void queryCartCount() {
	//
	// new SAsyncTask<Void, Void, HashMap<String, Object>>(this, R.string.wait)
	// {
	//
	//
	// @Override
	// protected HashMap<String, Object> doInBackground(FragmentActivity
	// context,
	// Void... params) throws Exception {
	// return ComModel2.getMatchShopCartCount(context);
	// }
	//
	// @Override
	// protected boolean isHandleException() {
	// return true;
	// }
	//
	// @Override
	// protected void onPostExecute(FragmentActivity context,
	// HashMap<String, Object> result, Exception e) {
	// super.onPostExecute(context, result, e);
	// if (e != null || result == null) {
	// return;
	// }
	// String count = (String) result.get("cart_count");
	// cartCount = Integer.valueOf(count);
	// if (cartCount > 0) {
	// tv_cart_count.setVisibility(View.VISIBLE);
	// tv_cart_count.setText(count);
	// tv_time_count_down.setVisibility(View.VISIBLE);
	// } else {
	// tv_cart_count.setVisibility(View.INVISIBLE);
	// tv_time_count_down.setVisibility(View.GONE);
	// }
	//
	// Long sTime = (Long)result.get("s_time");// 系统当前时间
	// Long sDeadline = (Long)result.get("s_deadline");//商品过期时间
	//
	// //购物车倒计时
	// if (sDeadline>0&&cartCount>0) {//正数代表加入了购物车 显示倒计时
	// tv_time_count_down.setVisibility(View.VISIBLE);
	// String c_time_cart = DateFormatUtils.format(sDeadline,"yyyy-MM-dd
	// HH:mm:ss");
	// String s_time = DateFormatUtils.format(sTime,"yyyy-MM-dd HH:mm:ss");
	//
	// try {
	// if (timer!=null) {
	// timer.cancel();
	// }
	// task = new TimerTask() {
	// @Override
	// public void run() {
	//
	// runOnUiThread(new Runnable() { // UI thread
	// @Override
	// public void run() {
	// takeTime();
	// }
	// });
	// }
	// };
	// timer = new Timer();
	// timer.schedule(task, 0, 1000); // 显示倒计时
	//
	// SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// Date d1 = df1.parse(c_time_cart);
	// Date d2 = df1.parse(s_time);
	// long diff = d1.getTime() - d2.getTime();
	// recLen = diff;
	// } catch (ParseException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// }else {//负数代表或0没有加入购物车
	// tv_time_count_down.setVisibility(View.GONE);
	// }
	// }
	// }.execute();
	// }
	// 购物车用的
	// private void takeTime(){
	// recLen -= 1000;
	// String days;
	// String hours;
	// String minutes;
	// String seconds;
	// long minute = recLen / 60000;
	// long second = (recLen % 60000) / 1000;
	// if (minute >= 60) {
	// long hour = minute / 60;
	// minute = minute % 60;
	// if (hour >= 24) {
	// long day = hour / 24;
	// hour = hour % 24;
	// if (day < 10) {
	// days = "0" + day;
	// } else {
	// days = "" + day;
	// }
	// if (hour < 10) {
	// hours = "0" + hour;
	// } else {
	// hours = "" + hour;
	// }
	// if (minute < 10) {
	// minutes = "0" + minute;
	// } else {
	// minutes = "" + minute;
	// }
	// if (second < 10) {
	// seconds = "0" + second;
	// } else {
	// seconds = "" + second;
	// }
	// tv_time_count_down.setText("" + days + "天" + hours
	// + "时" + minutes + "分" + seconds + "秒");
	// } else {
	// if (hour < 10) {
	// hours = "0" + hour;
	// } else {
	// hours = "" + hour;
	// }
	// if (minute < 10) {
	// minutes = "0" + minute;
	// } else {
	// minutes = "" + minute;
	// }
	// if (second < 10) {
	// seconds = "0" + second;
	// } else {
	// seconds = "" + second;
	// }
	// tv_time_count_down.setText("" + hours + "时" + minutes
	// + "分" + seconds + "秒");
	// }
	// } else if (minute >= 10 && second >= 10) {
	// tv_time_count_down
	// .setText("" + minute + ":" + second + "");
	// } else if (minute >= 10 && second < 10) {
	// tv_time_count_down.setText("" + minute + ":0" + second
	// + "");
	// } else if (minute < 10 && second >= 10) {
	// tv_time_count_down.setText("0" + minute + ":" + second
	// + "");
	// } else {
	// tv_time_count_down.setText("0" + minute + ":0" + second
	// + "");
	// }
	// if (recLen < 0) {
	// tv_time_count_down.setText("00:00");
	// tv_time_count_down.setVisibility(View.GONE);
	// tv_cart_count.setVisibility(View.INVISIBLE);//数量小圆点消失
	// task.cancel();
	// }
	// }
	// /**
	// * @Description: 创建动画层
	// * @param
	// * @return void
	// * @throws
	// */
	// private ViewGroup createAnimLayout() {
	// ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
	// LinearLayout animLayout = new LinearLayout(this);
	// LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
	// LinearLayout.LayoutParams.MATCH_PARENT,
	// LinearLayout.LayoutParams.MATCH_PARENT);
	// animLayout.setLayoutParams(lp);
	// // animLayout.setId(R.id.age);
	// animLayout.setBackgroundResource(android.R.color.transparent);
	// rootView.addView(animLayout);
	// return animLayout;
	// }
	//
	//
	//
	// private View addViewToAnimLayout(final ViewGroup vg, final View view,
	// int[] location) {
	// // vg.removeAllViews();
	// int x = location[0];
	// int y = location[1];
	// ViewGroup parent = (ViewGroup) view.getParent();
	// if (parent != null) {
	// parent.removeAllViews();
	// }
	// vg.addView(view);
	// LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
	// LinearLayout.LayoutParams.WRAP_CONTENT,
	// LinearLayout.LayoutParams.WRAP_CONTENT);
	// lp.leftMargin = x;
	// lp.topMargin = y;
	// view.setLayoutParams(lp);
	// return view;
	// }
	//
	//// private ViewGroup anim_mask_layout;
	// private ViewGroup[]anim_mask_layouts;

	private int width;
	private int height;

	/***
	 * 得到屏幕宽度和高度 像素
	 */
	private void getWindownPixes() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
	}
	// private void setAnim(View v,int[]location,final int i) {
	// getWindownPixes();
	// Animation mScaleAnimation = new ScaleAnimation(1.5f, 0.1f, 1.5f, 0.1f,
	// Animation.RELATIVE_TO_SELF, 0.1f, Animation.RELATIVE_TO_SELF,
	// 0.1f);
	// mScaleAnimation.setDuration(1000);
	// mScaleAnimation.setFillAfter(true);
	//
	// int[] start_location = new int[] {location[0], location[1]};
	// // rlBottom.getLocationInWindow(start_location);
	// // ViewGroup vg = (ViewGroup) imgIcon.getParent();
	// // vg.removeView(imgIcon);
	//
	// // 将组件添加到我们的动画层上
	// View view = addViewToAnimLayout(anim_mask_layouts[i], imgs[i],
	// start_location);
	// int[] end_location = new int[2];
	// tv_cart_count.getLocationOnScreen(end_location);
	// // 计算位移
	// int endX = end_location[0]-start_location[0];
	// int endY = end_location[1] - start_location[1];
	//
	// Animation mTranslateAnimation = new TranslateAnimation(0, endX, 0,
	// endY);// 移动
	// mTranslateAnimation.setDuration(1000);
	//
	// AnimationSet mAnimationSet = new AnimationSet(false);
	// // 这块要注意，必须设为false,不然组件动画结束后，不会归位。
	// mAnimationSet.setFillAfter(false);
	// mAnimationSet.addAnimation(mScaleAnimation);
	// mAnimationSet.addAnimation(mTranslateAnimation);
	// view.startAnimation(mAnimationSet);
	//
	// mTranslateAnimation.setAnimationListener(new AnimationListener() {
	//
	// @Override
	// public void onAnimationStart(Animation animation) {
	//
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation animation) {
	//
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation animation) {
	// // tvNumber.setText(goodsNumber+"");
	// anim_mask_layouts[i].removeAllViews();
	// imgs[i]=null;
	// ds[i] = null;
	// if(i<imgs.length-1){
	// return;
	// }
	// queryCartCount();
	// tv_time_count_down.setVisibility(View.VISIBLE); // 显示倒计时
	// tv_cart_count.setVisibility(View.VISIBLE);
	// //显示保留30分钟
	// rl_retain.setVisibility(View.VISIBLE);
	// rl_retain.getBackground().setAlpha(205);
	// final Handler handler = new Handler() {
	// public void handleMessage(Message msg) {
	// if (msg.what == 1) {
	// rl_retain.setVisibility(View.GONE);
	// }
	// };
	// };
	//
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// try {
	// Thread.sleep(4000);
	// Message message = new Message();
	// message.what = 1;
	// handler.sendMessage(message);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	//
	// }
	// }).start();
	// }
	// });
	// }
	// private void setAnim(){
	// //获取起点坐标
	// int[] location1 = new int[2];
	// mCartPoint.getLocationInWindow(location1);
	// int x1 = location1[0];
	// int y1 = location1[1];
	// //获取终点坐标，最近拍摄的坐标
	// int[] location2 = new int[2];
	// tv_cart_count.getLocationInWindow(location2);
	// int x2 = location2[0];
	// int y2 = location2[1];
	//// int[] location3 = new int[2];
	// int x3 = (x1+x2)/2;
	// int y3 = y2-90;
	// //两个位移动画
	// TranslateAnimation translateAnimationX = new TranslateAnimation(0,x3-x1,
	// 0, 0); //横向动画
	// final TranslateAnimation translateAnimationX2 = new
	// TranslateAnimation(x3-x1,x2-x1, 0, 0); //横向动画
	// TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0,
	// y3-y1); //竖向动画
	// final TranslateAnimation translateAnimationY2 = new TranslateAnimation(0,
	// 0, y3-y1, y2-y1); //竖向动画
	// translateAnimationX.setInterpolator(new LinearInterpolator());
	// //横向动画设为匀速运动
	// translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
	// translateAnimationX2.setInterpolator(new LinearInterpolator());
	// //横向动画设为匀速运动
	// translateAnimationX2.setRepeatCount(0);// 动画重复执行的次数
	// translateAnimationY.setInterpolator(new
	// AccelerateDecelerateInterpolator());
	// translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
	//// translateAnimationY.setRepeatMode(Animation.REVERSE);
	// translateAnimationY2.setInterpolator(new AccelerateInterpolator());
	// //竖向动画设为开始结尾处加速，中间迅速
	// translateAnimationY2.setRepeatCount(0);// 动画重复执行的次数
	// // 组合动画
	// final AnimationSet anim = new AnimationSet(false);
	// final AnimationSet anim2 = new AnimationSet(false);
	// anim.setFillAfter(false); //动画结束bu停留在最后一帧
	// anim2.setFillAfter(false); //动画结束不停留在最后一帧
	// anim.addAnimation(translateAnimationX);
	// anim.addAnimation(translateAnimationY);
	//
	// anim2.addAnimation(translateAnimationX2);
	// anim2.addAnimation(translateAnimationY2);
	// anim2.setDuration(400);// 动画的执行时间
	// anim2.setStartOffset(0);
	//
	// anim.setAnimationListener(new Animation.AnimationListener(){
	//
	// @Override
	// public void onAnimationStart(Animation animation) {
	//
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation animation) {
	// mCartPoint.startAnimation(anim2);
	//
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation animation) {
	//
	// }
	//
	// } );
	// anim2.setAnimationListener(new Animation.AnimationListener() { //抛物线动画结束后
	// @Override
	// public void onAnimationStart(Animation animation) {
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation animation) {
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation animation) {
	// pointRoot.removeAllViews();
	// mCartPoint.setVisibility(View.INVISIBLE);
	// tv_cart_count.setVisibility(View.VISIBLE);
	//// queryCartCount();
	// //如果倒计时显示
	// if(tv_time_count_down.getVisibility()!=View.VISIBLE){
	// setAnim1();
	// }else{
	// setAnim2();
	// }
	// }
	// });
	//
	//
	// // 播放
	// mCartPoint.setVisibility(View.VISIBLE);
	//// refreshCouldIndicator(activity, null, null);
	//// translateAnimationX.setDuration(800);
	//// translateAnimationY.setDuration(400);
	// anim.setDuration(400);// 动画的执行时间
	// anim.setStartOffset(400);
	// mCartPoint.startAnimation(anim);
	// }
	//
	// /**
	// * 购物车左移动画
	// */
	// protected void setAnim1() {
	// RelativeLayout rel = (RelativeLayout) findViewById(R.id.img_cart);
	// int [] location1 = new int[2];
	// int [] location2 = new int[2];
	// rel.getLocationInWindow(location1);
	// img_cart_cart.getLocationInWindow(location2);
	// TranslateAnimation animation = new
	// TranslateAnimation(0,location1[0]-location2[0]+4,0,0);
	// animation.setRepeatCount(0);// 动画重复执行的次数
	// animation.setDuration(400);// 动画的执行时间
	// animation.setFillAfter(false); //动画结束不停留在最后一帧
	// animation.setStartOffset(0);
	//// animation.setInterpolator(new BounceInterpolator());
	// img_cart_cart.startAnimation(animation);
	// animation.setAnimationListener(new AnimationListener() {
	//
	// @Override
	// public void onAnimationStart(Animation animation) {
	//
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation animation) {
	//
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation animation) {
	// img_cart_cart.clearAnimation();
	//// mAddCartTv.setClickable(true);
	// tv_time_count_down.setVisibility(View.VISIBLE);
	// queryCartCountAdd();
	// showRetain();
	// }
	//
	// });
	// }
	// /**
	// * 购物车圆点缩放动画
	// */
	// protected void setAnim2() {
	// ScaleAnimation animation = new ScaleAnimation(1.0f, 1.4f, 1.0f, 1.4f);
	// animation.setRepeatCount(0);// 动画重复执行的次数
	// animation.setDuration(400);// 动画的执行时间
	// animation.setFillAfter(false); //动画结束不停留在最后一帧
	// animation.setStartOffset(0);
	// tv_cart_count.startAnimation(animation);
	// animation.setAnimationListener(new AnimationListener() {
	//
	// @Override
	// public void onAnimationStart(Animation animation) {
	//
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation animation) {
	//
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation animation) {
	// img_cart_cart.clearAnimation();
	// queryCartCountAdd();
	// showRetain();
	// }
	//
	// });
	// }
	//
	// private RelativeLayout rootView;
	// private ImageView mCartPoint;
	// private RelativeLayout pointRoot;
	// /**
	// * 添加动画布局
	// */
	// private void addAnimLayout() {
	// pointRoot = new RelativeLayout(mContext);
	// RelativeLayout.LayoutParams params = new
	// RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
	// RelativeLayout.LayoutParams.MATCH_PARENT);
	// pointRoot.setBackgroundColor(Color.TRANSPARENT);
	// pointRoot.setLayoutParams(params);
	// rootView.addView(pointRoot);
	//
	// mCartPoint = new ImageView(mContext);
	// RelativeLayout.LayoutParams params2 =new
	// RelativeLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 12),
	// DP2SPUtil.dp2px(mContext, 12));
	// params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	// params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	// params2.bottomMargin=DP2SPUtil.dp2px(mContext, 36);
	// params2.rightMargin = DP2SPUtil.dp2px(mContext, 40);
	// mCartPoint.setLayoutParams(params2);
	// mCartPoint.setBackgroundResource(R.drawable.red_point_bg);
	// pointRoot.addView(mCartPoint);
	// mCartPoint.setVisibility(View.VISIBLE);
	//
	// }
	// private Handler handler = new Handler() {
	// public void handleMessage(Message msg) {
	// if (msg.what == 1) {
	// rl_retain.setVisibility(View.GONE);
	// }
	// };
	// };
	// /**
	// * 立即结算提醒
	// */
	// private void showRetain() {
	// //显示保留30分钟
	// rl_retain.setVisibility(View.VISIBLE);
	// rl_retain.getBackground().setAlpha(205);
	//// while(!thread.isInterrupted()){
	//// thread.interrupt();
	//// }
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// try {
	// Thread.sleep(60*1000);
	// Message message = new Message();
	// message.what = 1;
	// handler.sendMessage(message);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	//
	// }
	// }).start();
	// }

	private void getOrderNo(List<ShopCart> listGoods) {
		// 运营统计拼接shop_code
		StringBuffer bf = new StringBuffer();
		for (int i = listGoods.size() - 1; i >= 0; i--) {
			String shop_code = listGoods.get(i).getShop_code();

			if (i != 0) {
				bf.append(shop_code + ",");
			} else {
				bf.append(shop_code);
			}
		}
		yunYunTongJi(bf.toString(), 200, 2);

		// 如果数量为0 不跳转
		if (listGoods.size() == 0) {
			Toast.makeText(mContext, "没有选中物品", Toast.LENGTH_SHORT).show();
			return;
		}
		double discount = listGoods.size() < 2 ? 0 : totlePrice *(1-Double.parseDouble(matchDiscount));

		Intent intent = new Intent(mContext, SubmitMultiShopActivty.class);

		Bundle bundle = new Bundle();
		bundle.putSerializable("listGoods", (Serializable) listGoods);
		bundle.putBoolean("isDapei", true);
		intent.putExtras(bundle);

		intent.putExtra("mYouhuiMoneyCount", discount);
		long time = 30 * 60 * 1000;
		intent.putExtra("Time", time);
		((Activity) mContext).startActivityForResult(intent, 101);
		// getActivity().finish();
	}

	private void yunYunTongJi(final String shop_code, final int type, final int tab_type) {
		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

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
				// System.out.println("购物车这块调用成功");
			}

		}.execute();
	}

}
