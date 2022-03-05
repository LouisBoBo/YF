//package com.yssj.ui.activity.integral;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.huanxin.activity.ChatAllHistoryActivity;
//import com.yssj.ui.base.BasicActivity;
//
//public class MyIntegralExplainActivity extends BasicActivity {
//
//	private TextView tvTitle_base;
//	private LinearLayout img_back;
//	private ImageView img_right_icon;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		getActionBar().hide();
//		setContentView(R.layout.my_integral_explain);
//		initView();
//	}
//
//	private void initView() {
//		
//		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("我的积分");
//		
//		img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
//		img_right_icon.setVisibility(View.VISIBLE);
//		img_right_icon.setImageResource(R.drawable.mine_message_center);
//		img_right_icon.setOnClickListener(this);
//		
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//		
//		
//	}
//
//	
//	
//
//
//	@Override
//	public void onClick(View v) {
//		super.onClick(v);
//		switch (v.getId()) {
//		case R.id.img_back:
//			finish();
//			break;
//		case R.id.img_right_icon:
//			Intent intent = new Intent(this, ChatAllHistoryActivity.class);
//			startActivity(intent);
//			break;
//		}
//	}
//}
