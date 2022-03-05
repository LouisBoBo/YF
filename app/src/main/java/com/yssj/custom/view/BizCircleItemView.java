//package com.yssj.custom.view;
//
//import com.yssj.activity.R;
//import com.yssj.entity.BizCircleEntity;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//public class BizCircleItemView extends LinearLayout {
//
//	public BizCircleItemView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		initView(context);
//	}
//	
//	private ImageView img;
//	
//	private void initView(Context context){
//		
//		LayoutInflater.from(context).inflate(R.layout.biz_circle_item, this);
//		img=(ImageView) findViewById(R.id.img);
//		img.getLayoutParams().height=context.getResources().getDisplayMetrics().widthPixels/2*9/6;
//	}
//	
//	public void setData(BizCircleEntity entity){
//		
//	}
//	
//}
