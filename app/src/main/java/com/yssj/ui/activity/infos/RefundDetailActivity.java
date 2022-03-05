package com.yssj.ui.activity.infos;

import android.os.Bundle;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.FundDetail;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DateUtil;

public class RefundDetailActivity extends BasicActivity{

	private TextView tv_order_code, tv_amount, tv_time, tv_describe;
	private FundDetail fundDetail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fundDetail = (FundDetail) getIntent().getSerializableExtra("item");
		initView();
	}
	
	private void initView(){
		setContentView(R.layout.fund_detail);
		tv_order_code = (TextView) findViewById(R.id.tv_order_code);
		tv_amount = (TextView) findViewById(R.id.tv_amount);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_describe = (TextView) findViewById(R.id.tv_describe);
		tv_order_code.setText(""+fundDetail.getOrder_code());
		tv_amount.setText("¥"+fundDetail.getMoney());
		tv_time.setText(DateUtil.FormatMillisecond(fundDetail.getAdd_time()));
		tv_describe.setText(""+fundDetail.getMoney());
	}
	
}
