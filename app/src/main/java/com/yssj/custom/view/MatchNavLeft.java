package com.yssj.custom.view;

import com.yssj.activity.R;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.utils.YunYingTongJi;

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
/**
 * 
 * @author Administrator
 *
 */
public class MatchNavLeft extends LinearLayout{
	private TextView tvTitle;
	private ImageView imgCart;
	private Context mContext;
	private int from;//1搭配首页，2 搭配详情
	private boolean isforcelook;//判断是否是强制浏览是强制浏览传true  搭配列表和搭配详情 不是强制浏览 传false 
	public ImageView getImgCart() {
		return imgCart;
	}
	public void setImgCart(ImageView imgCart) {
		this.imgCart = imgCart;
	}
	
	public MatchNavLeft(Context context,String shop_code,int from,boolean isforcelook) {
		super(context);
		mContext = context;
		this.from = from;
		this.isforcelook = isforcelook;
		initView(context,shop_code);
	}
	public MatchNavLeft(Context context, AttributeSet attrs,String shop_code) {
		super(context, attrs);
		mContext = context;
		initView(context,shop_code);
	}

	public MatchNavLeft(Context context, AttributeSet attrs, int defStyle,String shop_code) {
		super(context, attrs, defStyle);
		mContext = context;
		initView(context,shop_code);
	}
	
	
	private void initView(Context context,final String shop_code) {
		LayoutInflater.from(context).inflate(
				R.layout.match_nav_left, this, true);
		tvTitle = (TextView) this.findViewById(R.id.match_nav_tv);
		imgCart = (ImageView) this.findViewById(R.id.icon_img_cart);
		this.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(from == 1){
					YunYingTongJi.yunYingTongJi(mContext, 56);//首页搭配图坐标
				}else if(from == 2){
					YunYingTongJi.yunYingTongJi(mContext, 57);//搭配详情主图坐标  
				}
				if(!TextUtils.isEmpty(shop_code)){
					
					Intent intent = new Intent(mContext,
							ShopDetailsActivity.class);
					intent.putExtra("code", shop_code);
					if(isforcelook){
						intent.putExtra("shopCarFragment", "shopCarFragment");
						intent.putExtra("isforcelookMatch", isforcelook);
					}
					mContext.startActivity(intent);
					((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
				}
			}
		});
	}
	
	public void setTextView(String title){
		//控制文字长度最多七个字
		if(title.length()>7){
			title = title.substring(0, 7);
		}
		if(!TextUtils.isEmpty(title)&&!"null".equals(title)){
			tvTitle.setText(title);	
		}else{
			tvTitle.setText("");	
		}
		
	}
	
}
