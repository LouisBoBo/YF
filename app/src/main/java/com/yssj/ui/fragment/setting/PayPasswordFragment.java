package com.yssj.ui.fragment.setting;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.activity.setting.AccountSecureActivity;
import com.yssj.ui.base.BaseFragment;

public class PayPasswordFragment extends BaseFragment implements OnClickListener {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private RelativeLayout rel_old_paypass,rel_phone_identity;
	

	private String exe;
	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_setting_secure_reset_paypassword, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("重置支付密码");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		rel_old_paypass = (RelativeLayout) view.findViewById(R.id.rel_old_paypass);
		rel_old_paypass.setOnClickListener(this);
		
		rel_phone_identity = (RelativeLayout) view.findViewById(R.id.rel_phone_identity);
		rel_phone_identity.setOnClickListener(this);
		
		exe = getActivity().getIntent().getStringExtra("wallet");
		
		return view;
	}

	@Override
	public void initData() {
	}

	@Override
	public void onClick(View v) {
		Fragment mFragment;
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();
			if("account".equals(exe)){
			Intent intent = new Intent(getActivity(), AccountSecureActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.activity_from_right_publish,R.anim.activity_from_right_publish_close);
			}
			break;
		case R.id.rel_old_paypass:	// 原始密码支付
			mFragment = new OldPayPasswordFragment();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			break;
		case R.id.rel_phone_identity:	// 手机验证
			mFragment = new PhoneIdentityFragment();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			break;

		}
	}


}
