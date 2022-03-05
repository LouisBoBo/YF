//package com.yssj.ui.dialog;
//
//import com.yssj.activity.R;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class SignExplain extends Dialog implements android.view.View.OnClickListener {
//	
//	private int id;
//	private Context context;
//	private TextView bt;
//	private TextView tv_one;
//	private TextView tv_two;
//	public SignExplain(Context context, int id_new) {
//		super(context);
//		setCanceledOnTouchOutside(false);
//		this.context = context;
//		this.id=id_new;
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.sign_explain);
//		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//		initView();
//	}
//
//	private void initView() {
//		bt = (TextView) findViewById(R.id.bt);
//		tv_one = (TextView) findViewById(R.id.tv_one);
//		tv_two = (TextView) findViewById(R.id.tv_two);
//		
//		if (id==11) {
//			tv_one.setText(2+"元现金随机藏在本周精选商品列表的商品中，打开商品详情页浏览至底部，就有机会找到哦~");
//			tv_two.setText("在这些推荐商品中购买下单，即可以直接完成签到任务获得"+2+"元现金奖励。");
//		}
//		if (id==12) {
//			tv_one.setText(5+"元现金随机藏在本周精选商品列表的商品中，打开商品详情页浏览至底部，就有机会找到哦~");
//			tv_two.setText("在这些推荐商品中购买下单，即可以直接完成签到任务获得"+5+"元现金奖励。");
//		}
//		if (id==15) {
//			tv_one.setText(1+"元现金随机藏在本周精选商品列表的商品中，打开商品详情页浏览至底部，就有机会找到哦~");
//			tv_two.setText("在这些推荐商品中购买下单，即可以直接完成签到任务获得"+1+"元现金奖励。");
//		}
//		if (id==20) {
//			tv_one.setText(3+"元现金随机藏在本周精选商品列表的商品中，打开商品详情页浏览至底部，就有机会找到哦~");
//			tv_two.setText("在这些推荐商品中购买下单，即可以直接完成签到任务获得"+3+"元现金奖励。");
//		}
//		
//		bt.setOnClickListener(this);
//		
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.bt:
//			this.dismiss();
//			break;
//
//		default:
//			break;
//		}
//		
//	}
//	
//}
