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
public class MatchNavLeftWhite extends LinearLayout{
	private TextView tvTitle;
	private ImageView imgCart;
	private Context mContext;
//	private int from;//1搭配首页，2 搭配详情
	public ImageView getImgCart() {
		return imgCart;
	}
	public void setImgCart(ImageView imgCart) {
		this.imgCart = imgCart;
	}
	
	public MatchNavLeftWhite(Context context) {
		super(context);
		mContext = context;
//		this.from = from;
		initView(context);
	}
	public MatchNavLeftWhite(Context context, AttributeSet attrs,String shop_code) {
		super(context, attrs);
		mContext = context;
		initView(context);
	}

	public MatchNavLeftWhite(Context context, AttributeSet attrs, int defStyle,String shop_code) {
		super(context, attrs, defStyle);
		mContext = context;
		initView(context);
	}
	
	
	private void initView(Context context) {
		LayoutInflater.from(context).inflate(
				R.layout.match_nav_left_white, this, true);
		tvTitle = (TextView) this.findViewById(R.id.match_nav_tv);
		imgCart = (ImageView) this.findViewById(R.id.icon_img_cart);
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
