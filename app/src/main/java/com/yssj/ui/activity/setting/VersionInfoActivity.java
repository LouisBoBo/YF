package com.yssj.ui.activity.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DeviceUtils;

public class VersionInfoActivity extends BasicActivity implements OnClickListener {
	private LinearLayout img_back;
	
	private TextView tv_version;
	private RelativeLayout root;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initView();
		initData();
		
	}

	/*@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);   
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
		
	}*/
	
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
		setContentView(R.layout.activity_version_info);
		root =(RelativeLayout)findViewById(R.id.root);
		root.setBackgroundColor(Color.WHITE);
		TextView tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("版本信息");
		
		img_back = (LinearLayout) findViewById(R.id.img_back);
		
		img_back.setOnClickListener(this);
		
		tv_version = (TextView) findViewById(R.id.tv_version);
		
	}
	
	private void initData() {
		tv_version.setText("当前版本：V" + DeviceUtils.getVersionName(this));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;

		}
	}


}
