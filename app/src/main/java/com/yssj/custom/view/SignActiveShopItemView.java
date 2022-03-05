package com.yssj.custom.view;

import java.util.HashMap;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.Shop;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SignActiveShopItemView extends LinearLayout{
	
	private Context context;
	
	private TextView name;
	
//	private CheckBox box;
	private TextView price;
	private TextView mOriginalPrice;
	
	private TextView returnMoney;
	
	private ImageView image;
	
	private int width;

	private ImageView ivShopCart;
	
	private ImageView ivLike;
	private TextView tv_discount,tv_sold,tv_manufacturers;

	private boolean isNotScan;

	public void setNotScan(boolean isNotScan) {
		isNotScan = isNotScan;
	}

	public SignActiveShopItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		LayoutInflater.from(context).inflate(R.layout.sign_active_shop_list, this);
		
		name=(TextView) findViewById(R.id.news_title);
		
		price=(TextView) findViewById(R.id.tv_price);
		mOriginalPrice= (TextView) findViewById(R.id.tv_shoppe_price);

//		box=(CheckBox) findViewById(R.id.cb_favor);
		tv_manufacturers = (TextView) findViewById(R.id.tv_manufacturers);
		
		returnMoney=(TextView) findViewById(R.id.nickback);
		
		image=(ImageView) findViewById(R.id.news_pic);
		tv_discount = (TextView) findViewById(R.id.tv_discount);
		tv_sold = (TextView) findViewById(R.id.tv_sold);
		
//		ivShopCart = (ImageView) findViewById(R.id.iv_shopcar);
//		ivLike = (ImageView) findViewById(R.id.iv_love_star);
		
		 width = context.getResources().getDisplayMetrics().widthPixels;
	}
	
	public void initView(final HashMap<String, Object> map){
		/**这里赋值*/
		
		image.getLayoutParams().height=width/2*900/600;
		
		image.setTag(map.get("shop_code").toString().substring(1, 4)+"/"+map.get("shop_code").toString()+"/"+(String) map.get("def_pic"));
		if(width>720){
			PicassoUtils.initImage(context,  map.get("shop_code").toString().substring(1, 4)+"/"+map.get("shop_code").toString()+"/"+(String) map.get("def_pic")+"!382", image);
//			SetImageLoader.initImageLoader(null, image, map.get("shop_code").toString().substring(1, 4)+"/"+map.get("shop_code").toString()+"/"+(String) map.get("def_pic"),"!382");
		}else{
			PicassoUtils.initImage(context,  map.get("shop_code").toString().substring(1, 4)+"/"+map.get("shop_code").toString()+"/"+(String) map.get("def_pic")+"!280", image);

//			SetImageLoader.initImageLoader(null, image, map.get("shop_code").toString().substring(1, 4)+"/"+map.get("shop_code").toString()+"/"+(String) map.get("def_pic"),"!280");
		}
		Double prices=(double) 0;
		
		name.setText(Shop.getShopNameStrNew((String) map.get("shop_name")));
//		Double prices=Double.parseDouble((String) map.get("shop_se_price"));
		String a=(String)map.get("shop_se_price");
		if (a.equals("null")) {
			prices=(double) 0;
		}else {
			prices=Double.parseDouble((String) map.get("shop_se_price"));
		}
		
		
//		Double prices=(double) 1;

		price.setText("¥" +new java.text.DecimalFormat("#0.0").format(prices));
		Double originalPrices = Double.parseDouble((String) map.get("shop_price"));
		mOriginalPrice.setText("¥" + new java.text.DecimalFormat("#0.0").format(originalPrices));

//		Double kickBack=Double.parseDouble((String) map.get("kickback"));
//		returnMoney.setText("立省"+new java.text.DecimalFormat("#0").format(kickBack)+"元");

		Double savePrices=Double.parseDouble((String) map.get("shop_price"));

//		returnMoney.setText("立省"+new java.text.DecimalFormat("#0.0").format(savePrices-prices));

		returnMoney.setText("返" +new java.text.DecimalFormat("#0.0").format(prices)+"元=0元");
		tv_discount.setText(new java.text.DecimalFormat("#0.0").format((prices/savePrices)*10)+"折");

//		returnMoney.setText(Html.fromHtml(context.getString(R.string.tv_kick_back,new java.text.DecimalFormat("#0.00").format(kickBack))));
//		if ("0".equals((map.get("isLike").toString()))) {
//			ivLike.setImageResource(R.drawable.img_love_star_default);
//		} else {
//			ivLike.setImageResource(R.drawable.img_love_star_selected);
//
//		}
//		if ("0".equals((map.get("isCart").toString()))) {
//			ivShopCart.setImageResource(R.drawable.img_shopcar_default);
//		} else {
//			ivShopCart.setImageResource(R.drawable.img_shopcar_selected);
//		}
			
//		box.setVisibility(View.GONE);
		if(map.get("virtual_sales")!=null){
			tv_sold.setText("已售"+(String)map.get("virtual_sales")+"件");
		}
		String manufacturers = (String)map.get("supp_label");
		if(!TextUtils.isEmpty(manufacturers)){
			tv_manufacturers.setText(manufacturers+"制造商出品");
			tv_manufacturers.setVisibility(View.VISIBLE);
		}else{
			tv_manufacturers.setVisibility(View.GONE);
		}
		this.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
//					ForceLookActivity forceLookActivity = new ForceLookActivity();
//					forceLookActivity.click_num=forceLookActivity.click_num+1;
//					forceLookActivity.num_tongji=forceLookActivity.num_tongji+1;
					
					if(SignActiveShopItemView.this.getVisibility()!=View.VISIBLE){
						return;
					}
					addScanDataTo((String) map.get("shop_code"));
					Intent intent = new Intent(context, ShopDetailsActivity.class);
					intent.putExtra("code", (String) map.get("shop_code"));
					intent.putExtra("shopCarFragment", "shopCarFragment");
					
//					intent.putExtra("isSignActiveShop", true);
					if(SignListAdapter.doType==4&&!SignListAdapter.isSignComplete&&!isNotScan){//强制浏览个数并且任务是没有完成的
						intent.putExtra("isSignActiveShopScan", true);
					}
					if (SignListAdapter.doType == 19 && !SignListAdapter.isSignComplete && !isNotScan) {//新增强制浏览个数 奖励额度 并且浏览任务没有完成的
						intent.putExtra(YConstance.Pref.ISFORCELOOKLIMIT, true);
					}
//					intent.putExtra("click_num", forceLookActivity.click_num);//把点击次数传过去 判断是否点击了足够次数 再决定是否弹框
//					intent.putExtra("now_type_id_value", forceLookActivity.now_type_id_value);  
//					intent.putExtra("now_type_id", forceLookActivity.now_type_id);  
//					intent.putExtra("next_type_id", forceLookActivity.next_type_id);  
//					intent.putExtra("next_type_id_value", forceLookActivity.next_type_id_value);  
//					intent.putExtra("virtual_sales", (String)map.get("virtual_sales"));//虚拟销售
					
					FragmentActivity activity = (FragmentActivity) context;
					activity.startActivityForResult(intent, 101);
					activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);;
				}
		});
	}
	
	/*
	 * 把浏览过的数据添加进数据库
	 */
	private void addScanDataTo(final String shop_code) {
		new SAsyncTask<Void, Void, ReturnInfo>(((FragmentActivity) context)) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.addMySteps(context, shop_code);
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result) {
				super.onPostExecute(context, result);
			}

		}.execute();
	}
	
}
