package com.yssj.ui.fragment.setting;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.activity.setting.AccountSecureActivity;
import com.yssj.ui.base.BaseFragment;

public class SuccessBindEmailFragment extends BaseFragment implements OnClickListener {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private Button btn_close;
	

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_setting_success_bind_email, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("绑定邮箱");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		
		btn_close = (Button) view.findViewById(R.id.btn_close);
		btn_close.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void initData() {
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			startActivity(new Intent(getActivity(), AccountSecureActivity.class));
			getActivity().finish();
		case R.id.btn_close:
			startActivity(new Intent(getActivity(), AccountSecureActivity.class));
			getActivity().finish();
			break;

		}
	}


}
