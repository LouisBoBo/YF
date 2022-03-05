package com.yssj.ui.fragment.payback;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.WXminiAppUtil;

/**
 * 售后帮助
 * @author roger
 *
 */
public class SelledHelpActivity extends BasicActivity implements OnClickListener {
	
	private boolean hadIntercept;
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private ScrollView root;
	
	private Button btn_unsell_customer,btn_selled_customer,btn_complaint_advice,btn_service_self;
	private RelativeLayout rl_call_phone;
	
	private Button btn_call; // 拨打电话
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		ActionBar aBar = getActionBar();
//		aBar.setDisplayHomeAsUpEnabled(true);
//		aBar.hide();
		setContentView(R.layout.activity_payback_selled_help);
		initView();
	}
	private void initView() {
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("联系客服");
		img_back = (LinearLayout)findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		root = (ScrollView) findViewById(R.id.root);
		root.setBackgroundColor(Color.WHITE);
		btn_unsell_customer = (Button)findViewById(R.id.btn_unsell_customer);		// 售前服务
		btn_unsell_customer.setOnClickListener(this);
		
		btn_selled_customer = (Button)findViewById(R.id.btn_selled_customer);		// 售后服务
		btn_selled_customer.setOnClickListener(this);
		
		btn_complaint_advice = (Button)findViewById(R.id.btn_complaint_advice);	// 投诉建议
		btn_complaint_advice.setOnClickListener(this);
		
		btn_service_self = (Button)findViewById(R.id.btn_service_self);		// 自助服务
		btn_service_self.setOnClickListener(this);
		
		rl_call_phone = (RelativeLayout)findViewById(R.id.rl_call_phone);		// 客服电话
		rl_call_phone.setOnClickListener(this);
		
		btn_call = (Button)findViewById(R.id.btn_call);
		
		
		
	}
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
			
		case R.id.btn_unsell_customer:	// 售前服务
//			intent = new Intent(this, ChatActivity.class);
//			intent.putExtra("userId", SharedPreferencesUtil.getStringData(getApplicationContext(), "kefuNB", "0"));
//			startActivity(intent);
			WXminiAppUtil.jumpToWXmini(this);

			break;
		case R.id.btn_selled_customer:	// 售后服务
//			intent = new Intent(this, ChatActivity.class);
//			intent.putExtra("userId", SharedPreferencesUtil.getStringData(getApplicationContext(), "kefuNB", "0"));
//			startActivity(intent);
			WXminiAppUtil.jumpToWXmini(this);

			break;
		case R.id.btn_complaint_advice:	// 投诉建议
//			intent = new Intent(this, ChatActivity.class);
//			intent.putExtra("userId", SharedPreferencesUtil.getStringData(getApplicationContext(), "kefuNB", "0"));
//			startActivity(intent);
			WXminiAppUtil.jumpToWXmini(this);

			break;
		case R.id.btn_service_self:		// 自助服务
//			intent = new Intent(this, ChatActivity.class);
//			intent.putExtra("userId", SharedPreferencesUtil.getStringData(getApplicationContext(), "kefuNB", "0"));
//			startActivity(intent);
			WXminiAppUtil.jumpToWXmini(this);

			break;
		case R.id.rl_call_phone:		// 客服电话
			intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + btn_call.getText().toString().split(":")[1].replace("-", "")));
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		}
	}
	@Override
	public void onBackPressed() {
	finish();
}

}
