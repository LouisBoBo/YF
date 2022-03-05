//package com.yssj.custom.view;
//
//import java.util.List;
//
//import com.yssj.activity.R;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
///**
// * 自由搭配
// * @author lbp
// *
// */
//public class ShopDetailView extends LinearLayout {
//	
//	private int height;
//	
//	private ImageView one,two,three,four,five,six,seven,eight,nine;
//	
//	public ShopDetailView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		LayoutInflater.from(context).inflate(R.layout.shoo_detail_view, this);
//		height=context.getResources().getDisplayMetrics().widthPixels/3;
//		one=(ImageView) findViewById(R.id.one);
//		two=(ImageView) findViewById(R.id.two);
//		three=(ImageView) findViewById(R.id.three);
//		four=(ImageView) findViewById(R.id.four);
//		five=(ImageView) findViewById(R.id.five);
//		six=(ImageView) findViewById(R.id.six);
//		seven=(ImageView) findViewById(R.id.seven);
//		eight=(ImageView) findViewById(R.id.eight);
//		nine=(ImageView) findViewById(R.id.nine);
//		
//		one.getLayoutParams().height=height;
//		two.getLayoutParams().height=height;
//		three.getLayoutParams().height=height;
//		four.getLayoutParams().height=height;
//		five.getLayoutParams().height=height;
//		six.getLayoutParams().height=height;
//		seven.getLayoutParams().height=height;
//		eight.getLayoutParams().height=height;
//		nine.getLayoutParams().height=height;
//	}
//	
//	public void setDate(List<String> list){
//		
//	}
//}
