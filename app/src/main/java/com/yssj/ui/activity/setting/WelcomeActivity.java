package com.yssj.ui.activity.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;

public class WelcomeActivity extends BasicActivity{

	private RelativeLayout rel_bg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		
		rel_bg = (RelativeLayout) findViewById(R.id.rel_bg);
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
	public boolean onTouchEvent(MotionEvent event) {
		rel_bg.setVisibility(View.GONE);
		finish();
		return super.onTouchEvent(event);
	}
	
	@Override
	public void onBackPressed() {
		rel_bg.setVisibility(View.GONE);
		super.onBackPressed();
	}
}
