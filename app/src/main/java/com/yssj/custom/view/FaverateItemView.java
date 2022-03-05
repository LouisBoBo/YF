package com.yssj.custom.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.data.YDBHelper;
import com.yssj.entity.Like;
import com.yssj.entity.Shop;
import com.yssj.entity.VipDikouData;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.adpter.MyFavorStaggeredAdapter;
import com.yssj.utils.PicassoUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FaverateItemView extends LinearLayout{
	
	private Context context;
	
	private TextView name,new_sub;
	
	private CheckBox box;
	
	private TextView price,tv_save,tv_no_discount_price;
	
	private TextView returnMoney;
	
	private ImageView image;
	
	private int width;

	private ImageView ivShopCart;

	private YDBHelper dbHelp;
	
	private java.text.DecimalFormat pFormate;

	private ImageView ivLike;
	public FaverateItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		pFormate=new DecimalFormat("#0.00");
		LayoutInflater.from(context).inflate(R.layout.myfave_infos_list, this);

		dbHelp = new YDBHelper(context);


		name=(TextView) findViewById(R.id.news_title);
		
		price=(TextView) findViewById(R.id.tv_price);
		
		box=(CheckBox) findViewById(R.id.cb_favor);
		
		returnMoney=(TextView) findViewById(R.id.nickback);
		
		tv_save = (TextView) findViewById(R.id.tv_save);
		new_sub = (TextView) findViewById(R.id.new_sub);
		tv_no_discount_price = (TextView) findViewById(R.id.tv_no_discount_price);
		
//		ivShopCart = (ImageView) findViewById(R.id.iv_shopcar);
////		
//		ivLike = (ImageView) findViewById(R.id.iv_love_star);
		
		image=(ImageView) findViewById(R.id.news_pic);
		
		 width = context.getResources().getDisplayMetrics().widthPixels;
	}
	
	public void initView(boolean bl,final Like like){
		/**这里赋值*/
		
//		image.getLayoutParams().height=(width-DP2SPUtil.dp2px(context, 18))/2*900/600;
		
		image.setTag(like.getShop_code().substring(1, 4)+"/"+like.getShop_code()+"/"+like.getShow_pic());
		
		if(width>720){
			PicassoUtils.initImage(context, like.getShop_code().substring(1, 4)+"/"+like.getShop_code()+"/"+like.getShow_pic()+"!382", image);
//			SetImageLoader.initImageLoader(null, image, like.getShop_code().substring(1, 4)+"/"+like.getShop_code()+"/"+like.getShow_pic(),"!382");
		}else{
//			SetImageLoader.initImageLoader(null, image, like.getShop_code().substring(1, 4)+"/"+like.getShop_code()+"/"+like.getShow_pic(),"!280");
			PicassoUtils.initImage(context, like.getShop_code().substring(1, 4)+"/"+like.getShop_code()+"/"+like.getShow_pic()+"!280", image);
		}
		
		
		String url  =  YUrl.imgurl +  like.getShop_code().substring(1, 4)+"/"+like.getShop_code()+"/"+like.getShow_pic();
		
		
		name.setText(like.getShop_name());
//		Double prices=like.getShop_se_price();
		Double prices = like.getShop_se_price();

		final double kickBack=like.getKickback();




//		price.setText("¥" +new java.text.DecimalFormat("#0.0").format(prices-(int)kickBack));
		price.setText("¥" +new java.text.DecimalFormat("#0.0").format(prices));



		
//		returnMoney.setText(new java.text.DecimalFormat("#0").format(kickBack)+"元");
		returnMoney.setText((int)kickBack+"元");
		
		double shop_price=like.getShop_price();//原价
//		double save = shop_price - prices;
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
		tv_save.setTextSize(13);
		tv_save.setTextColor(Color.parseColor("#A8A8A8"));

		tv_no_discount_price.setText("返"+ new java.text.DecimalFormat("#0.0").format(prices)+"元=0元");
		tv_no_discount_price.setVisibility(View.VISIBLE);
		tv_no_discount_price.setTextSize(11);
		tv_no_discount_price.setTextColor(Color.parseColor("#FB3B3B"));




			tv_no_discount_price.setVisibility(GONE);
			tv_save.setVisibility(VISIBLE);
			returnMoney.setVisibility(GONE);

//
//			if (shop_price > 0) {
//				String no_discount_price = "原价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
////			ToastUtil.addStrikeSpan(tv_no_discount_price,no_discount_price);
//				tv_save.setText(no_discount_price);
//				tv_save.setVisibility(View.VISIBLE);
//			} else {
//				tv_save.setVisibility(View.GONE);
//			}


		tv_save.setText("原价¥" + new java.text.DecimalFormat("#0.0").format(prices));




		String onPrice = like.getApp_shop_group_price()+"";


			onPrice =  new java.text.DecimalFormat("#0.0")
					.format(Double.parseDouble(onPrice));
			price.setText(onPrice+"元");





			String sql2 = "select * from supp_label where _id = " + like.getSupp_label_id()
					+ " order by _id";
			List<HashMap<String, String>> listSupp = new ArrayList<HashMap<String, String>>();// 品牌
			try {
				listSupp = dbHelp.query(sql2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (listSupp != null && listSupp.size() > 0) {
				String label_name = listSupp.get(0).get("name");
				new_sub.setText(label_name);
				new_sub.setVisibility(VISIBLE);
			}



		if(!GuideActivity.show1yuan){ //非1元购处理
			price.setText("¥" + new java.text.DecimalFormat("#0.0").format(prices));
			String no_discount_price = "原价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
			tv_save.setText(no_discount_price);
		}




//		if ("0".equals(like.getIsLike())) {
//			ivLike.setImageResource(R.drawable.img_love_star_default);
//		} else {
//			ivLike.setImageResource(R.drawable.img_love_star_selected);
//
//		}
//		if ("0".equals(like.getIsCart())) {
//			ivShopCart.setImageResource(R.drawable.img_shopcar_default);
//		} else {
//			ivShopCart.setImageResource(R.drawable.img_shopcar_selected);
//
//		}
		
		if(bl){//编辑状态
			
			box.setVisibility(View.VISIBLE);
			
			if(MyFavorStaggeredAdapter.getCheckList().contains(like)){
				//设置选中
				box.setChecked(true);
			}else{
				//未选中
				box.setChecked(false);
			}
			
			this.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					if(FaverateItemView.this.getVisibility()!=View.VISIBLE){
						return;
					}
					
					if(MyFavorStaggeredAdapter.getCheckList().contains(like)){
						MyFavorStaggeredAdapter.getCheckList().remove(like);
						box.setChecked(false);
					}else{
						MyFavorStaggeredAdapter.getCheckList().add(like);
						box.setChecked(true);
					}
				}
			});
			
		}else{
			
			box.setVisibility(View.GONE);
			
			this.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if(FaverateItemView.this.getVisibility()!=View.VISIBLE){
						return;
					}
					Intent intent = new Intent(context, ShopDetailsActivity.class);
					intent.putExtra("code", like.getShop_code());
//					if(kickBack==0){//活动商品返佣 抵扣为0
//						intent.putExtra("isSignActiveShop", true);
//					}
					intent.putExtra("shopCarFragment", "shopCarFragment");
					FragmentActivity activity = (FragmentActivity) context;
					activity.startActivityForResult(intent, 101);
					activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);;
				}
			});
		}
	}
	private double vipPrice;

	public void initViewVip(final Like like, final VipDikouData vipDikouData) {


		//		image.getLayoutParams().height=(width-DP2SPUtil.dp2px(context, 18))/2*900/600;

		image.setTag(like.getShop_code().substring(1, 4)+"/"+like.getShop_code()+"/"+like.getShow_pic());

		if(width>720){
			PicassoUtils.initImage(context, like.getShop_code().substring(1, 4)+"/"+like.getShop_code()+"/"+like.getShow_pic()+"!382", image);
//			SetImageLoader.initImageLoader(null, image, like.getShop_code().substring(1, 4)+"/"+like.getShop_code()+"/"+like.getShow_pic(),"!382");
		}else{
//			SetImageLoader.initImageLoader(null, image, like.getShop_code().substring(1, 4)+"/"+like.getShop_code()+"/"+like.getShow_pic(),"!280");
			PicassoUtils.initImage(context, like.getShop_code().substring(1, 4)+"/"+like.getShop_code()+"/"+like.getShow_pic()+"!280", image);
		}


		Double prices = (double) 0;

		name.setText(Shop.getShopNameStrNew((String) like.getShop_name()));

		String sql2 = "select * from supp_label where _id = " + like.getSupp_label_id()
				+ " order by _id";
		List<HashMap<String, String>> listSupp = new ArrayList<HashMap<String, String>>();// 品牌
		try {
			listSupp = dbHelp.query(sql2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (listSupp != null && listSupp.size() > 0) {
			String label_name = listSupp.get(0).get("name");
			new_sub.setText(label_name);
			new_sub.setVisibility(VISIBLE);
		}


		String a =  like.getShop_se_price()+"";
		if (a.equals("null")) {
			prices = (double) 0;
		} else {

			prices = Double.parseDouble( like.getShop_se_price()+"");

		}




//		returnMoney.setText("" + new java.text.DecimalFormat("#0").format(kickBack) + "元");

		double shop_price = like.getShop_price();//原价
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


		tv_no_discount_price.setVisibility(GONE);
		tv_save.setVisibility(VISIBLE);







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

		tv_save.setText("件");
		tv_save.setVisibility(INVISIBLE);
		if (!GuideActivity.show1yuan) { //非1元购处理
			price.setText("¥" + new java.text.DecimalFormat("#0.0").format(prices));
			String no_discount_price = "原价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
			tv_save.setText(no_discount_price);
		}


		this.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (FaverateItemView.this.getVisibility() != View.VISIBLE) {
					return;
				}
				Intent intent = new Intent(context, ShopDetailsActivity.class);
				intent.putExtra("code", like.getShop_code());
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
