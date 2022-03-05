package com.yssj.ui.activity.logins;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.ui.base.BasicActivity;

/**
 * 立即登录
 * 
 * @author Administrator
 * 
 */
public class LoginRightNowActivity extends BasicActivity {
	private AppManager appManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_rightnow);

		appManager = AppManager.getAppManager();
		appManager.addActivity(this);

		ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
		ivBack.setVisibility(View.GONE);
		Button btnNow = (Button) findViewById(R.id.btn_login_rightnow);
		btnNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// appManager.finishActivity();
				startActivity(new Intent(LoginRightNowActivity.this, LoginActivity.class).putExtra("login_register",
						"login"));
				
				
				timer.cancel();
				
				finish();
			}
		});

		
		
		//3秒后自动跳到登录界面
		task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		};

		timer.schedule(task, 3000);

	}

	private final Timer timer = new Timer();
	private TimerTask task;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			startActivity(
					new Intent(LoginRightNowActivity.this, LoginActivity.class).putExtra("login_register", "login"));
			finish();

			super.handleMessage(msg);
		}
	};

	// 跳转登录界面
	private void finish(int position) {
		for (int i = 0; i < position; i++) {
			appManager.finishActivity();
		}

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			appManager.finishActivity();
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// JPushInterface.onResume(this);
		// MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// JPushInterface.onPause(this);
		// MobclickAgent.onPause(this);

	}

	@Override
	public void onBackPressed() {
		appManager.finishActivity();

	}
}
