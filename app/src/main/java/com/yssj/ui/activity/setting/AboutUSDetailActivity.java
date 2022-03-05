package com.yssj.ui.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.base.BasicActivity;

/**
 * @author Administrator
 *
 */
public class AboutUSDetailActivity extends BasicActivity {
	private RelativeLayout rel_welcome_page,rel_version_info;
	private LinearLayout img_back,root;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_about_us_detail);
		
		TextView tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("关于我们");
		
		root = (LinearLayout) findViewById(R.id.root);
		root.setBackgroundColor(Color.WHITE);
		img_back = (LinearLayout) findViewById(R.id.img_back);
		
		rel_welcome_page = (RelativeLayout) findViewById(R.id.rel_welcome_page);
		rel_version_info = (RelativeLayout) findViewById(R.id.rel_version_info);
		
		img_back.setOnClickListener(this);
		rel_welcome_page.setOnClickListener(this);
		rel_version_info.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View view) {
		Intent intent;
		switch (view.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.rel_welcome_page:    // 进入欢迎页
//			intent = new Intent(AboutUSDetailActivity.this, WelcomeActivity.class);
//			startActivity(intent);
//			getSharedPreferences(Pref.is_show_guide, Context.MODE_PRIVATE).edit().putBoolean(Pref.is_show_guide, true).commit();
			intent = new Intent(this,GuideActivity.class);
			intent.putExtra("isFirst", true);
//			intent = new Intent(AboutUSActivity.this, UserProtocolActivity.class);
			startActivity(intent);
			
			break;
		case R.id.rel_version_info:		// 版本信息
			intent = new Intent(AboutUSDetailActivity.this, VersionInfoActivity.class);
			startActivity(intent);
			break;
		}
	}
	
}
