package com.yssj.ui.activity.shopdetails;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;


public class NoShareActivity extends BasicActivity {

	private TimeCount timeCount;

	private boolean isNomal = false;
	private TextView tv_showtext;
	private RelativeLayout root;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		aBar.hide();
		setContentView(R.layout.activity_noshare);
		root = (RelativeLayout) findViewById(R.id.root);
		root.setBackgroundColor(Color.WHITE);
		isNomal = getIntent().getBooleanExtra("isNomal", false);
		findViewById(R.id.img_back).setOnClickListener(this);
		((TextView)findViewById(R.id.tvTitle_base)).setText("分享购买");
		tv_showtext = (TextView) findViewById(R.id.tv_showtext);
		Date curDates = new Date(System.currentTimeMillis());// 获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String strs = sdf.format(curDates);
		// 分取系统时间 小时分
		String[] dds = strs.split(":");
        int dhs = Integer.parseInt(dds[0]);
		if(isNomal){
			if(dhs >= 14)
				tv_showtext.setText("亲爱哒，今天的分享次数已经使用完了哦，明天再分享吧。接下来购物不分享也能得到现金红包哦！");
			else
				tv_showtext.setText("亲爱哒，今天上午的分享次数已经使用完了哦，下午再分享吧。接下来购物不分享也能得到现金红包哦！");
		}
		timeCount = new TimeCount(5000, 1000);
		timeCount.start();
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.img_back:  //返回
			timeCount.cancel();
			finish();
			break;

		default:
			break;
		}
	}

	private class TimeCount extends CountDownTimer{

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			
		}

		@Override
		public void onFinish() {
			NoShareActivity.this.finish();
		}

		@Override
		public void onTick(long arg0) {
			
		}
		
	}
	
}
