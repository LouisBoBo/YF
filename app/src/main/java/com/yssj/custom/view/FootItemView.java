package com.yssj.custom.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.data.YDBHelper;
import com.yssj.entity.Shop;
import com.yssj.entity.VipDikouData;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.adpter.MyFootPrintStaggeredAdapter;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ToastUtil;

public class FootItemView extends LinearLayout {

	private Context context;

	private TextView name;

	private CheckBox box;

	private TextView price,tv_save,tv_no_discount_price;

	private TextView returnMoney;

	private ImageView image;

	private int width;

	private ImageView ivShopCart;

	private ImageView ivLike;

	private java.text.DecimalFormat pFormate;
	private TextView tv_supply_name;

	public FootItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		pFormate = new DecimalFormat("#0.00");
		LayoutInflater.from(context).inflate(R.layout.myfave_infos_list, this);

		name = (TextView) findViewById(R.id.news_title);

		price = (TextView) findViewById(R.id.tv_price);

		box = (CheckBox) findViewById(R.id.cb_favor);

		returnMoney = (TextView) findViewById(R.id.nickback);

		image = (ImageView) findViewById(R.id.news_pic);
		tv_supply_name = (TextView) findViewById(R.id.tv_supply_name);
		tv_save = (TextView) findViewById(R.id.tv_save);
		tv_no_discount_price = (TextView) findViewById(R.id.tv_no_discount_price);

		// ivShopCart = (ImageView) findViewById(R.id.iv_shopcar);
		// ivLike = (ImageView) findViewById(R.id.iv_love_star);

		width = context.getResources().getDisplayMetrics().widthPixels;
		dbHelp = new YDBHelper(context);

	}

	public void initView(boolean bl, final HashMap<String, Object> map, final boolean isShopCart) {// isShopCart=true
																									// 时，代表购物车进来，false,我的足迹
		/** 这里赋值 */

//		image.getLayoutParams().height = (width - DP2SPUtil.dp2px(context, 18)) / 2 * 900 / 600;

		image.setTag(map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/"
				+ (String) map.get("def_pic"));

		if (width > 720) {

			PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/"
					+ map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!382", image);

			// SetImageLoader.initImageLoader(null, image,
			// map.get("shop_code").toString().substring(1, 4) + "/"
			// + map.get("shop_code").toString() + "/" + (String)
			// map.get("def_pic"), "!382");
		} else {
			// SetImageLoader.initImageLoader(null, image,
			// map.get("shop_code").toString().substring(1, 4) + "/"
			// + map.get("shop_code").toString() + "/" + (String)
			// map.get("def_pic"), "!280");

			PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/"
					+ map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!280", image);
		}

		name.setText(Shop.getShopNameStrNew((String) map.get("shop_name")));
		Double prices;

		if (null != map.get("shop_se_price") || "".equals(map.get("shop_se_price"))
				|| "null".equals(map.get("shop_se_price"))) {


//			prices = Double.parseDouble((String) map.get("shop_se_price"));
			prices = Double.parseDouble((String) map.get("shop_se_price")) ;




		} else {
			prices = 0.0;
		}

		if (map.containsKey("supp_label")) {
			if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
				tv_supply_name.setVisibility(View.VISIBLE);
				tv_supply_name.setText("" + map.get("supp_label") + "制造商出品");
			} else {
				tv_supply_name.setVisibility(View.GONE);
			}
		}

		final double kickBack = Double.parseDouble("" + map.get("kickback"));

//		price.setText("¥" + new java.text.DecimalFormat("#0.0").format(prices - (int) kickBack));
		price.setText("¥" + new java.text.DecimalFormat("#0.0").format(prices));
		// returnMoney.setText(""+new
		// java.text.DecimalFormat("#0").format(kickBack)+"元");
		if (kickBack == 0) {
			returnMoney.setText("¥" + new java.text.DecimalFormat("#0.0").format(prices * 0.1));
		} else {
			returnMoney.setText((int) kickBack + "元");
		}
		
		double shop_price=Double.parseDouble((String) map.get("shop_price"));//原价
		double save = shop_price - prices;
//		if(save>0){
//			tv_save.setText("立省" + new java.text.DecimalFormat("#0.0").format(save)+"元");
//			tv_save.setVisibility(View.VISIBLE);
//		}else{
//			tv_save.setVisibility(View.GONE);
//		}
		if(shop_price>0){
			String no_discount_price = "专柜价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
//			ToastUtil.addStrikeSpan(tv_no_discount_price,no_discount_price);
			tv_save.setText(no_discount_price);
			tv_save.setVisibility(View.VISIBLE);
		}else{
			tv_save.setVisibility(View.GONE);
		}
		tv_save.setTextSize(10);
		tv_save.setTextColor(Color.parseColor("#A8A8A8"));

		tv_no_discount_price.setText("返"+ new java.text.DecimalFormat("#0.0").format(prices)+"元=0元");
		tv_no_discount_price.setVisibility(View.VISIBLE);
		tv_no_discount_price.setTextSize(11);
		tv_no_discount_price.setTextColor(Color.parseColor("#FB3B3B"));



			tv_no_discount_price.setVisibility(GONE);
			tv_save.setVisibility(VISIBLE);
			returnMoney.setVisibility(GONE);


//			if (shop_price > 0) {
//				String no_discount_price = "原价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
////			ToastUtil.addStrikeSpan(tv_no_discount_price,no_discount_price);
//				tv_save.setText(no_discount_price);
//				tv_save.setVisibility(View.VISIBLE);
//			} else {
//				tv_save.setVisibility(View.GONE);
//			}


		tv_save.setText("原价¥" + new java.text.DecimalFormat("#0.0").format(prices));



//			price.setText("¥"+GuideActivity.oneShopPrice);




			String onPrice = map.get("app_shop_group_price")+"";
			onPrice =  new java.text.DecimalFormat("#0.0")
					.format(Double.parseDouble(onPrice));
			price.setText(onPrice+"元");




		if(!GuideActivity.show1yuan){ //非1元购处理
			price.setText("¥" + new java.text.DecimalFormat("#0.0").format(prices));
			String no_discount_price = "原价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
			tv_save.setText(no_discount_price);
		}






		// if ("0".equals(map.get("isLike").toString())) {
		// ivLike.setImageResource(R.drawable.img_love_star_default);
		// } else {
		// ivLike.setImageResource(R.drawable.img_love_star_selected);
		//
		// }
		// if ("0".equals(map.get("isCart").toString())) {
		// ivShopCart.setImageResource(R.drawable.img_shopcar_default);
		// } else {
		// ivShopCart.setImageResource(R.drawable.img_shopcar_selected);
		// }
		if (bl) {// 编辑状态

			box.setVisibility(View.VISIBLE);

			if (MyFootPrintStaggeredAdapter.getCheckList().contains(map)) {
				// 设置选中
				box.setChecked(true);
			} else {
				// 未选中
				box.setChecked(false);
			}

			this.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (FootItemView.this.getVisibility() != View.VISIBLE) {
						return;
					}
					if (MyFootPrintStaggeredAdapter.getCheckList().contains(map)) {
						MyFootPrintStaggeredAdapter.getCheckList().remove(map);
						box.setChecked(false);
					} else {
						MyFootPrintStaggeredAdapter.getCheckList().add(map);
						box.setChecked(true);
					}
				}
			});

		} else {

			box.setVisibility(View.GONE);

			this.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (FootItemView.this.getVisibility() != View.VISIBLE) {
						return;
					}
					Intent intent = new Intent(context, ShopDetailsActivity.class);
					intent.putExtra("code", (String) map.get("shop_code"));
					intent.putExtra("shopCarFragment", "shopCarFragment");
//					if (!isShopCart) {// 购物车进来的都是普通商品
//						if (kickBack == 0) {// 活动商品返佣 抵扣为0
//							intent.putExtra("isSignActiveShop", true);
//						}
//					}
					FragmentActivity activity = (FragmentActivity) context;
					activity.startActivityForResult(intent, 101);
					activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
				}
			});
		}
	}

	private double vipPrice;
	private YDBHelper dbHelp;

	public void initViewVip(final HashMap<String, Object> map, final VipDikouData vipDikouData) {


		image.setTag(map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic"));
		if (width > 720) {
			PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!382", image);
		} else {
			PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!280", image);

		}
		Double prices = (double) 0;

		name.setText(Shop.getShopNameStrNew((String) map.get("shop_name")));
		if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
			tv_supply_name.setVisibility(View.VISIBLE);
			tv_supply_name.setText("" + map.get("supp_label") + "制造商出品");
		} else {
			if (null != map.get("supp_label_id") && !"".equals(map.get("supp_label_id"))) {
				List<HashMap<String, String>> listDataSupple = new ArrayList<HashMap<String, String>>();
				String sql = "select * from supp_label  where _id = " + map.get("supp_label_id");
				listDataSupple = dbHelp.query(sql);
				if (listDataSupple.size() > 0) {
					tv_supply_name.setVisibility(View.VISIBLE);
					tv_supply_name.setText(listDataSupple.get(0).get("name") + "制造商出品");
				} else {
					tv_supply_name.setVisibility(View.GONE);
				}
			} else {
				tv_supply_name.setVisibility(View.GONE);
			}
		}
		String a = (String) map.get("shop_se_price");
		if (a.equals("null")) {
			prices = (double) 0;
		} else {

			prices = Double.parseDouble((String) map.get("shop_se_price"));

		}

		double kickBack = Double.parseDouble((String) map.get("kickback"));
		price.setText("月销" + map.get("virtual_sales") + "件");


		returnMoney.setText("" + new java.text.DecimalFormat("#0").format(kickBack) + "元");

		double shop_price = Double.parseDouble((String) map.get("shop_price"));//原价
//        double save = shop_price - prices;
//        if (save > 0) {
//            tv_save.setText("立省" + new java.text.DecimalFormat("#0.0").format(save) + "元");
//            tv_save.setVisibility(View.VISIBLE);
//        } else {
//            tv_save.setVisibility(View.GONE);
//        }
		if (shop_price > 0) {
			String no_discount_price = "专柜价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
//			ToastUtil.addStrikeSpan(tv_no_discount_price,no_discount_price);
			tv_save.setText(no_discount_price);
			tv_save.setVisibility(View.VISIBLE);
		} else {
			tv_save.setVisibility(View.GONE);
		}
		tv_save.setTextSize(13);
		tv_save.setTextColor(Color.parseColor("#A8A8A8"));

		tv_no_discount_price.setText("返" + new java.text.DecimalFormat("#0.0").format(prices) + "元=0元");
		tv_no_discount_price.setVisibility(View.VISIBLE);
		tv_no_discount_price.setTextSize(11);
		tv_no_discount_price.setTextColor(Color.parseColor("#FB3B3B"));


		box.setVisibility(View.GONE);
		tv_supply_name.setVisibility(GONE);


		tv_no_discount_price.setVisibility(GONE);
		tv_save.setVisibility(VISIBLE);




		String onPrice = map.get("app_shop_group_price") + "";
		try {
			onPrice = new java.text.DecimalFormat("#0.0")
					.format(Double.parseDouble(onPrice));
		} catch (Exception e) {
			e.printStackTrace();
		}


		price.setText("¥" + onPrice + "元");

		if (vipDikouData != null && vipDikouData.getIsVip() > 0) { //会员的处理
			price.setText("会员价元");


			double sePrice = prices * 0.95;
			double dikou = prices * 0.9;

			if (vipDikouData.getOne_not_use_price() >= dikou) {
				if (vipDikouData.getMaxType() == 6) {
					vipPrice = sePrice - dikou;
				} else {
					vipPrice = prices - dikou;

				}
			} else {

				if (vipDikouData.getMaxType() == 6) {
					vipPrice = sePrice - vipDikouData.getOne_not_use_price();
				} else {
					vipPrice = prices - vipDikouData.getOne_not_use_price();

				}

			}

			price.setText("¥" + new java.text.DecimalFormat("#0.0").format(vipPrice));

		}


//        tv_save.setText("原价¥" + new java.text.DecimalFormat("#0.0").format(prices) + "元");

		tv_save.setText("月销" + map.get("virtual_sales") + "件");
		tv_save.setVisibility(INVISIBLE);
		if (!GuideActivity.show1yuan) { //非1元购处理
			price.setText("¥" + new java.text.DecimalFormat("#0.0").format(prices));
			String no_discount_price = "原价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
			tv_save.setText(no_discount_price);
		}


		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (FootItemView.this.getVisibility() != View.VISIBLE) {
					return;
				}
				Intent intent = new Intent(context, ShopDetailsActivity.class);
				intent.putExtra("code", (String) map.get("shop_code"));
				intent.putExtra("shopCarFragment", "shopCarFragment");
//					if (!isShopCart) {// 购物车进来的都是普通商品
//						if (kickBack == 0) {// 活动商品返佣 抵扣为0
//							intent.putExtra("isSignActiveShop", true);
//						}
//					}
				FragmentActivity activity = (FragmentActivity) context;
				activity.startActivityForResult(intent, 101);
				activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
			}
		});
	}



}
