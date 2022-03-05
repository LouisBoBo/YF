//package com.yssj.custom.view;
//
//
//import com.yssj.activity.R;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.widget.LinearLayout;
///**
// * 限时抢购
// * @author lbp
// *
// */
//public class SubTimeView extends LinearLayout {
//	
//	private LinearLayout group;
//	
//	private Context context;
//	
//	
//	private int width;
//	
//	private boolean isSet;
//	
//	public SubTimeView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		LayoutInflater.from(context).inflate(R.layout.sub_time_view, this);
//		width=context.getResources().getDisplayMetrics().widthPixels;
//		group=(LinearLayout) findViewById(R.id.container);
//		this.context=context;
//		isSet=false;
//	}
//	public void setData(){
//		if(isSet==true){
//			return;
//		}
//		isSet=true;
//		group.removeAllViews();
//		for (int i = 0; i < 5; i++) {
//			SubTimeItemView item=new SubTimeItemView(context, null);
//			item.setLayoutParams(new LayoutParams(width/3, width/3));
//			group.addView(item);
//		}
//	}
//}
