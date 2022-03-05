package com.yssj.ui.activity.setting;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.ToastUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PayPasswordActivity extends BasicActivity implements OnClickListener {
	private LinearLayout img_back;
	private EditText et_pay_pass,et_pay_pass_confir,et_identify_code;
	private Button btn_get_identify_code,btn_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
//		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		YJApplication.getLoader().stop();
//		MobclickAgent.onPause(this);
	}
	
	private void initView() {
		setContentView(R.layout.activity_setting_paypassword);
		
		TextView tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("支付密码");
		
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		et_pay_pass = (EditText) findViewById(R.id.et_pay_pass);
		et_pay_pass_confir = (EditText) findViewById(R.id.et_pay_pass_confir);
		et_identify_code = (EditText) findViewById(R.id.et_identify_code);
		
		btn_time = (Button) findViewById(R.id.btn_time);
		
		btn_get_identify_code = (Button) findViewById(R.id.btn_get_identify_code);
		btn_get_identify_code.setOnClickListener(this);
		
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.btn_get_identify_code:
			ToastUtil.showShortText(PayPasswordActivity.this, "获取验证码");
			break;

		default:
			break;
		}
	}
}
