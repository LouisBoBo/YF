package com.yssj.ui.fragment.orderinfo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;
/****
 * 退换货
 * @author Administrator
 *
 */
public class ReturnShopMoneyActivity extends BasicActivity implements OnClickListener{
	
	private TextView tv_return_shopmoney;
	private TextView tv_return_money; 
	private RelativeLayout rayLayout0 , raLayout1 , raLayout2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_return_shopmoney);
		tv_return_money = (TextView) findViewById(R.id.tv_return_money);
		tv_return_money.setOnClickListener(this);
		tv_return_shopmoney = (TextView) findViewById(R.id.tv_return_shopmoney);
		tv_return_shopmoney.setOnClickListener(this);
		rayLayout0 = (RelativeLayout) findViewById(R.id.ray0);
		rayLayout0.setOnClickListener(this);
		raLayout1 = (RelativeLayout) findViewById(R.id.ray1);
		raLayout1.setOnClickListener(this);
		raLayout2 = (RelativeLayout) findViewById(R.id.ray2);
		raLayout2.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tv_return_shopmoney://退货退款
		case R.id.ray0:
			
			break;

		
		case R.id.tv_return_money://仅退款
		case R.id.ray1:
			
			
			
			break;
		case R.id.ray2://换货
			
			break;

		default:
			break;
		}
	}

}
