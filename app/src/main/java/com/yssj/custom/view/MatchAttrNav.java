package com.yssj.custom.view;

import java.io.File;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.Shop;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;

public class MatchAttrNav extends LinearLayout{
	private ImageView selectIv,headIv,addIv,subIv;
	private TextView nameTv,priceTv,sePriceTv,numTv;
	private Context mContext;
	
	
	public ImageView getSelectIv() {
		return selectIv;
	}

	public void setSelectIv(ImageView selectIv) {
		this.selectIv = selectIv;
	}

	public ImageView getHeadIv() {
		return headIv;
	}

	public void setHeadIv(ImageView headIv) {
		this.headIv = headIv;
	}

	public ImageView getAddIv() {
		return addIv;
	}

	public void setAddIv(ImageView addIv) {
		this.addIv = addIv;
	}

	public ImageView getSubIv() {
		return subIv;
	}

	public void setSubIv(ImageView subIv) {
		this.subIv = subIv;
	}

	public TextView getSePriceTv() {
		return sePriceTv;
	}

	public void setSePriceTv(TextView sePriceTv) {
		this.sePriceTv = sePriceTv;
	}

	public TextView getNumTv() {
		return numTv;
	}

	public void setNumTv(TextView numTv) {
		this.numTv = numTv;
	}

	public MatchAttrNav(Context context) {
		super(context);
		mContext = context;
		initView(context);
	}

	public MatchAttrNav(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView(context);
	}

	public MatchAttrNav(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initView(context);
	}

	private void initView(Context context) {
		LayoutInflater.from(context).inflate(R.layout.activity_match_attr_nav, this,
				true);
		nameTv = (TextView) this.findViewById(R.id.match_main_name_tv);
		sePriceTv = (TextView) this.findViewById(R.id.match_main_price_tv1);
		priceTv = (TextView) this.findViewById(R.id.match_main_price_tv2);
		numTv = (TextView) this.findViewById(R.id.match_number_tv);
		
		selectIv = (ImageView) this.findViewById(R.id.activity_match_select_iv);
		selectIv.setVisibility(View.GONE);
		headIv = (ImageView) this.findViewById(R.id.activity_match_main_img_iv);
		addIv = (ImageView) this.findViewById(R.id.img_add);
		subIv = (ImageView) this.findViewById(R.id.img_reduce);
		priceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
	}

	public void setTextView(String name,String price) {
		nameTv.setText(Shop.getShopNameStrNew(name));
		priceTv.setText("¥"+price);
	}
	public void setSePrice(String sePrice){
		double sPrice = Double.valueOf(sePrice);
		double num = Double.valueOf(numTv.getText().toString());
		sePriceTv.setText("¥"+String.valueOf(num*sPrice));
	}
	/**
	 * 需要比setSePrice先设置 先确定数量 
	 * @param num
	 */
	public void setNumText(String num){
		numTv.setText(num);
	}
	/**
	 * 获取购买数量
	 * @return
	 */
	public String getNumText(){
		return numTv.getText().toString();
	}
	
	public void setImage(String headPic,String shop_code){
		String headUrl = shop_code.substring(1,4)+File.separator+shop_code+File.separator+headPic;
//		SetImageLoader.initImageLoader(mContext, headIv,
//				headUrl, "");
//		
		PicassoUtils.initImage(mContext, headUrl, headIv);
		
		
	}
	public void setSelectIv(boolean flag){
		if(flag){
			selectIv.setImageResource(R.drawable.icon_dapeigou_celect);
		}else{
			selectIv.setImageResource(R.drawable.icon_dapeigou_normal);
		}
	}

}
