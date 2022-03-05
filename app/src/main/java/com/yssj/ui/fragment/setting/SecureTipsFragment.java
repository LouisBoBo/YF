package com.yssj.ui.fragment.setting;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BaseFragment;

public class SecureTipsFragment extends BaseFragment implements OnClickListener {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private RelativeLayout rel_enhance_secure,rel_what_bind_num,rel_set_login_pass,rel_find_pass,rel_login_info_tip;
	
	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_setting_secure_tips, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("安全贴士");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		rel_enhance_secure = (RelativeLayout) view.findViewById(R.id.rel_enhance_secure);
		rel_enhance_secure.setOnClickListener(this);
		rel_what_bind_num = (RelativeLayout) view.findViewById(R.id.rel_what_bind_num);
		rel_what_bind_num.setOnClickListener(this);
		rel_set_login_pass = (RelativeLayout) view.findViewById(R.id.rel_set_login_pass);
		rel_set_login_pass.setOnClickListener(this);
		rel_find_pass = (RelativeLayout) view.findViewById(R.id.rel_find_pass);
		rel_find_pass.setOnClickListener(this);
		rel_login_info_tip = (RelativeLayout) view.findViewById(R.id.rel_login_info_tip);
		rel_login_info_tip.setOnClickListener(this);
		
		
		return view;
	}

	@Override
	public void initData() {
		
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction bt = getActivity().getSupportFragmentManager().beginTransaction();
		Fragment mFragment;
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();
			break;
		case R.id.rel_enhance_secure:
			mFragment= new EnhanceSecureFragment();
			bt.replace(R.id.fl_content, mFragment).commit();
			break;
		case R.id.rel_what_bind_num:
			mFragment= new PhoneNumBindAndModifyFragment();
			bt.replace(R.id.fl_content, mFragment).commit();
			break;
		case R.id.rel_set_login_pass:
			mFragment= new SetLoginPassFragment();
			bt.replace(R.id.fl_content, mFragment).commit();
			break;
		case R.id.rel_find_pass:
			mFragment= new FindPasswordFragment();
			bt.replace(R.id.fl_content, mFragment).commit();
			break;
		case R.id.rel_login_info_tip:
			mFragment= new SecureInfoTipFragment();
			bt.replace(R.id.fl_content, mFragment).commit();
			break;

		default:
			break;
		}
	}

}
