package com.yssj.ui.activity.shopdetails;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;

public class ShowPigActivity extends BasicActivity {

	private ImageView img_animation;

	int i = 0;

	private AnimationDrawable animationDrawable;

	private int[] drawableIds = { R.drawable.share0020,
			R.drawable.share0021, R.drawable.share0022, R.drawable.share0023,
			R.drawable.share0024, R.drawable.share0025, R.drawable.share0026,
			R.drawable.share0027, R.drawable.share0028, R.drawable.share0029,
			R.drawable.share0030, R.drawable.share0031, R.drawable.share0032,
			R.drawable.share0033, R.drawable.share0034, R.drawable.share0035,
			R.drawable.share0036, R.drawable.share0037, R.drawable.share0038,
			R.drawable.share0039, R.drawable.share0040, R.drawable.share0041,
			R.drawable.share0042, R.drawable.share0043, R.drawable.share0044,
			R.drawable.share0045 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_pig);
		img_animation = (ImageView) findViewById(R.id.img_animation);
		needSecond = getIntent().getBooleanExtra("needSecond", true);
	}

	private Handler mHandler;

	private int duration;

	private boolean needSecond = true;

	@Override
	protected void onResume() {
		super.onResume();
		if (needSecond) {
			if (i > 0) {
				img_animation.setBackgroundResource(R.drawable.pig_anim);
				animationDrawable = (AnimationDrawable) img_animation
						.getBackground();
				duration = 60 * 60;

				mHandler = new Handler() {

					public void handleMessage(android.os.Message msg) {
						if (msg.what == 1) {
							ShowPigActivity.this.finish();
						} else if (msg.what == 2) {
							animationDrawable.start();// 开始
						}
					};

				};
				mHandler.sendEmptyMessageDelayed(2, 3000);
				mHandler.sendEmptyMessageDelayed(1, 3000 + 60 * 35);
			}

			i++;
		} else {
			img_animation.setBackgroundResource(R.drawable.pig_anim);
			animationDrawable = (AnimationDrawable) img_animation
					.getBackground();
			duration = 60 * 60;

			mHandler = new Handler() {

				public void handleMessage(android.os.Message msg) {
					if (msg.what == 1) {
						ShowPigActivity.this.finish();
					} else if (msg.what == 2) {
						animationDrawable.start();// 开始
					}
				};

			};
			mHandler.sendEmptyMessageDelayed(2, 3000);
			mHandler.sendEmptyMessageDelayed(1, 3000 + 60 * 35);
		}
		
//		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
//		MobclickAgent.onPause(this);
	}
	
	
}
