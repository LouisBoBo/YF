/*package com.yssj.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.yssj.activity.R;
import com.yssj.custom.view.LoginOrRegisterDialog;
import com.yssj.custom.view.LoginOrRegisterDialog.OnLoginOrRegisterListener;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.ImageUtils;

public class LoginOrRegisterActivity extends BasicActivity {

	private LinearLayout myLl;
	private ScrollView myScroll;
	private Handler mHandler = new Handler();
	private int[] images = { R.drawable.image_1, R.drawable.image_2,
			R.drawable.image_3, R.drawable.image_4 };
	private LoginOrRegisterDialog loginOrRegisterDialog;
	protected boolean isEnd;
	private MyRunnable myRun;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		aBar.hide();
		setContentView(R.layout.activity_loginorregister);
		myScroll = (ScrollView) findViewById(R.id.myslv);
		myRun = new MyRunnable();
		// myScroll.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View arg0, MotionEvent arg1) {
		// return true;
		// }
		// });
		myLl = (LinearLayout) findViewById(R.id.myll);
		loginOrRegisterDialog = new LoginOrRegisterDialog(this,
				new OnLoginOrRegisterListener() {

					@Override
					public void register() {
						// Toast.makeText(LoginOrRegisterActivity.this,
						// "注册,销毁activity", Toast.LENGTH_LONG).show();
						startActivity(new Intent(LoginOrRegisterActivity.this,
								LoginActivity.class).putExtra("login_register",
								"register"));
						LoginOrRegisterActivity.this.finish();
						
						mHandler.removeCallbacks(myRun);
					}

					@Override
					public void login() {
						startActivity(new Intent(LoginOrRegisterActivity.this,
								LoginActivity.class).putExtra("login_register",
								"login"));
						
						LoginOrRegisterActivity.this.finish();
						
						mHandler.removeCallbacks(myRun);
					}
				});
		loginOrRegisterDialog.setCancelable(false);
		
		loginOrRegisterDialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface arg0) {
				// TODO Auto-generated method stub
				LoginOrRegisterActivity.this.finish();
				mHandler.removeCallbacks(myRun);
			}
		});
		loginOrRegisterDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				if (arg1 == KeyEvent.KEYCODE_BACK) {
					LoginOrRegisterActivity.this.finish();
					mHandler.removeCallbacks(myRun);
					return true;
				}
				return false;
			}
		});
		loginOrRegisterDialog.show();

	}

	@Override
	protected void onResume() {
		super.onResume();
		addview();
		setAutoScroll();
	}
	
	private List<ImageView> imgList = new ArrayList<ImageView>();

	private void addview() {
		LogYiFu.i("TAG", "添加图片");
		imgList.clear();
		for (int i = 0; i < images.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			imageView.setAdjustViewBounds(true);
			imageView.setScaleType(ScaleType.FIT_XY);
			Drawable d = getResources().getDrawable(images[i]);
			imageView.setImageDrawable(d);
			imgList.add(imageView);
			myLl.addView(imageView);
		}
	}

	private void setAutoScroll() {
		mHandler.postDelayed(new Runnable() {
			public void run() {
				myScroll.post(myRun);
			}

		}, 800);
	}

	public class MyRunnable implements Runnable {

		@Override
		public void run() {
			int off = myLl.getMeasuredHeight() - myScroll.getHeight();// 判断高度,ScrollView的最大高度，就是屏幕的高度
			if (off > 0) {

				myScroll.scrollBy(0, 1);
				if (myScroll.getScrollY() == off) {
					// MyLogYiFu.e("TAG", "滚动到底部，加入图片");
					addview();
				}
				mHandler.postDelayed(this, 60);
			}
		}

	}

}
*/