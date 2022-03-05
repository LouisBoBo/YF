//package com.yssj.ui.activity.infos;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.ui.base.BasicActivity;
//
//public class ManSaleActivity extends BasicActivity {
//	
//	private TextView tvTitle_base;
//	private LinearLayout img_back;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		getActionBar().hide();
//		initView();
//	}
//
//	private void initView() {
//		setContentView(R.layout.man_sale);
//		findViewById(R.id.rel_daily_amount).setOnClickListener(this);
//		findViewById(R.id.rel_daily_rebate).setOnClickListener(this);
//		findViewById(R.id.rel_daily_visitor).setOnClickListener(this);
//		
//		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("销售管理");
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		Intent intent = null;
//		super.onClick(v);
//		switch (v.getId()) {
//		case R.id.img_back://返回
//			finish();
//			break;
//		case R.id.rel_daily_amount://每日金额
//			intent = new Intent(this, ManSaleDetailsActivity.class);
//			intent.putExtra("item", 0);
//			startActivity(intent);
//			break;
//		case R.id.rel_daily_rebate://一周回佣
//			intent = new Intent(this, ManSaleDetailsActivity.class);
//			intent.putExtra("item", 1);
//			startActivity(intent);
//			break;
//		case R.id.rel_daily_visitor://每日访客
//			intent = new Intent(this, ManSaleDetailsActivity.class);
//			intent.putExtra("item", 2);
//			startActivity(intent);
//			break;
//
//		default:
//			break;
//		}
//	}
//
//}
