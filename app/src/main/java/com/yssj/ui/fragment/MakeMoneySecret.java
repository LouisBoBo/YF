package com.yssj.ui.fragment;

import com.yssj.activity.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class MakeMoneySecret extends Activity{
	
	
	private RelativeLayout rl_three;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.make_money_secret);
		
		initView();
	}

	private void initView() {
		ImageView img_back = (ImageView) findViewById(R.id.img_back);
		
		LinearLayout ll_container = (LinearLayout) findViewById(R.id.ll_container);
		ll_container.setBackgroundColor(getResources().getColor(R.color.white));
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
			}
		});
	}
}
