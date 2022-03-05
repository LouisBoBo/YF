package com.yssj.custom.view;

import com.yssj.activity.R;
import com.yssj.entity.ShopOption;
import com.yssj.ui.activity.main.FilterResultActivity;
import com.yssj.ui.activity.main.SearchResultActivity;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
/**
 * 套餐热门推荐
 * @author lbp
 *
 */
public class MealRecomenView extends LinearLayout {
	
	
	private ImageView left,right;
	
	private Context context;
	
	private View v;
	
	public MealRecomenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		LayoutInflater.from(context).inflate(R.layout.meal_recomend, this);
		int width = context.getResources().getDisplayMetrics().widthPixels;
		left=(ImageView) findViewById(R.id.m_left);
		right=(ImageView) findViewById(R.id.m_right);
		left.getLayoutParams().height=width/2;
		right.getLayoutParams().height=width/2;
		v=findViewById(R.id.zw);
	}
	
	
	public void setData(final ShopOption shop1,final ShopOption shop2,boolean zw){
		if(zw){
			v.setVisibility(View.VISIBLE);
		}else{
			v.setVisibility(View.GONE);
		}
		left.setTag(shop1.getUrl());
		
//		SetImageLoader.initImageLoader(null, left, shop1.getUrl(), "");
		PicassoUtils.initImage(context, shop1.getUrl(), left);
		left.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent intent=new Intent(context, FilterResultActivity.class);
//				intent.putExtra("isTuijian", true);
//				intent.putExtra("title", shop1.getShop_code());
//				context.startActivity(intent);
				
				if(shop1.getShop_code().contains("type2")){
					Intent intent = new Intent(context, SearchResultActivity.class);
					intent.putExtra("id", shop1.getShop_code().split("=")[1]);
					context.startActivity(intent);
				}else{
					Intent intent=new Intent(context, FilterResultActivity.class);
					intent.putExtra("isTuijian", true);
					intent.putExtra("title", shop1.getShop_code());
					intent.putExtra("shop_name", shop1.getShop_name());
					context.startActivity(intent);
				}
				
			}
		});
		
		if(shop2!=null){
			right.setTag(shop2.getUrl());
//			SetImageLoader.initImageLoader(null, right, shop2.getUrl(), "");
			PicassoUtils.initImage(context, shop2.getUrl(), right);
			right.setVisibility(View.VISIBLE);
			right.setOnClickListener(new  OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(shop2.getShop_code().contains("type2")){
						Intent intent = new Intent(context, SearchResultActivity.class);
						intent.putExtra("id", shop2.getShop_code().split("=")[1]);
						context.startActivity(intent);
					}else{
						Intent intent=new Intent(context, FilterResultActivity.class);
						intent.putExtra("isTuijian", true);
						intent.putExtra("title", shop2.getShop_code());
						intent.putExtra("shop_name", shop2.getShop_name());
						context.startActivity(intent);
					}
				}
			});
		}else{
			right.setVisibility(View.INVISIBLE);
		}
		
	}
	
}
