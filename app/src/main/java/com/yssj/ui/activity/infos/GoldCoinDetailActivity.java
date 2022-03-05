package com.yssj.ui.activity.infos;


import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.base.BasicActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


/***
 * 金币
 */
public class GoldCoinDetailActivity extends BasicActivity implements OnClickListener{

	private View imgBack;
	private Button intergralDet;//积分明细
	private TextView GoldCountTv;
	private boolean isOpen;//是否开启金币
	private long recLen;//剩余时间
	private Timer timer ;
	private TextView mTvTime;
	private Button btnUse;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_gold_coin_detial);
		AppManager.getAppManager().addActivity(this);
		initView();
	}
	/**
	 * 初始化
	 */
	private void initView() {
		imgBack = findViewById(R.id.img_back);
		intergralDet = (Button) findViewById(R.id.img_right_icon);
		imgBack.setOnClickListener(this);
		intergralDet.setOnClickListener(this);
		GoldCountTv = (TextView) findViewById(R.id.gold_coin_count_tv);
		mTvTime = (TextView) findViewById(R.id.gold_coin_time_tv);
		btnUse = (Button) findViewById(R.id.tv_ln);
		btnUse.setOnClickListener(this);
		getIntegral();
		getTwoFoldnessGold();
	}
	
	@Override
	public void onClick(View v) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_back:
			onBackPressed();
			break;
		case R.id.img_right_icon://积分明细
			Intent intent = new Intent(context, IntergralDetailActivity.class);	
			intent.putExtra("page", 0);
			startActivity(intent);
			break;
		case R.id.tv_ln://立即使用金币
			Intent intent2 = new Intent(context, MainMenuActivity.class);
			intent2.putExtra("toYf", "toYf");
			startActivity(intent2);
			break;

		default:
			break;
		}
	}
	
	/**
	 * 获取当前金币数量
	 */
	private void getIntegral() {
		new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) context, 0) {
			@Override
			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				return ComModel2.getSignData(context);
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null && result != null) {
					String intergral = (String) result.get("iCount");
					GoldCountTv.setText(intergral);
				}
			}
		}.execute();
		
	}
	/**
	 * 获取当前积分/金币其他参数
	 */
	private void getTwoFoldnessGold() {
		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) context, 0) {
			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				return ComModel2.getTwoFoldnessGold(context);
			}
			
			protected boolean isHandleException() {
				return true;
			};
			
			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null && result != null) {
					String str = result.get("twofoldnessGold");
					if(!"".equals(str)){
						isOpen = true;
					}
					if(isOpen){
						String end_date = result.get("end_date");
						long endDate = Long.parseLong(end_date);
						getSystemTime(endDate);
//						recLen = endDate - System.currentTimeMillis();
//						timer.schedule(task, 0, 1000);
					}else{
						mTvTime.setText("距离金币失效还剩:00时00分00秒");	
					}
				}
			}
		}.execute();
		
	}
	/**
	 * 获取系统时间
	 */
	private void getSystemTime(final long endDate) {
		
		new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) context, R.string.wait) {

			@Override
			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				return ComModel2.getSystemTime(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(final FragmentActivity context, HashMap<String, Object> result, Exception e) {
				super.onPostExecute(context, result, e);
					long nowTime = (Long) result.get("now");
					if(timer!=null){
						timer.cancel();
					}
					timer = new Timer();
					recLen = endDate - nowTime;
					timer.schedule(task, 0, 1000);
			}
		}.execute();
	}
	
	// 倒计时
		private TimerTask task = new TimerTask() {
			@Override
			public void run() {

				runOnUiThread(new Runnable() { // UI thread
					@Override
					public void run() {
						recLen -= 1000;
						String hours;
						String minutes;
						String seconds;
						long minute = recLen / 60000;
						long second = (recLen % 60000) / 1000;
						
						long hour = minute / 60;
						minute = minute % 60;
						
						if (hour < 10) {
							hours = "0" + hour;
						} else {
							hours = "" + hour;
						}
						if (minute < 10) {
							minutes = "0" + minute;
						} else {
							minutes = "" + minute;
						}
						if (second < 10) {
							seconds = "0" + second;
						} else {
							seconds = "" + second;
						}
						mTvTime.setText("距离金币失效还剩:" + hours + "时" + minutes + "分" + seconds + "秒");
						
						// mTvTime.setText("" + recLen);
						if (recLen <= 0) {
							timer.cancel();
							mTvTime.setText("距离金币失效还剩:00时00分00秒");
						}
					}
					
				});
			}
		};
}
