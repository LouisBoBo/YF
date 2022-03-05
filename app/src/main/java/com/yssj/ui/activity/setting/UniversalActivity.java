package com.yssj.ui.activity.setting;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.custom.view.SwitchButton;
import com.yssj.entity.MyDialog;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.service.GPSService;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.MineLikeActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.CleanCacheUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;

public class UniversalActivity extends BasicActivity implements OnClickListener {
	
	private LinearLayout img_back;
	private RelativeLayout rel_start_location_service,rel_clear_cache,tongyong;
	private ToggleButton sb_start_location;
	private String key = "location_service_state";
	private ImageView img_right_icon;

	private AlertDialog dialog;
	
	private TextView tv_cleaning,tv_clear_detail,tv_clear;
	private ProgressBar pb_cleaning;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initView();
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
		setContentView(R.layout.activity_setting_universal);
		
		TextView tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("通用");
		tongyong = (RelativeLayout) findViewById(R.id.tongyong);
		tongyong.setBackgroundColor(Color.WHITE);
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.GONE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);
		
		rel_start_location_service = (RelativeLayout) findViewById(R.id.rel_start_location_service);
		rel_start_location_service.setOnClickListener(this);
		rel_clear_cache = (RelativeLayout) findViewById(R.id.rel_clear_cache);
		rel_clear_cache.setOnClickListener(this);
		
		tv_clear       = (TextView) findViewById(R.id.tv_clear);
		tv_clear_detail= (TextView) findViewById(R.id.tv_clear_detail);
		tv_cleaning    = (TextView) findViewById(R.id.tv_cleaning);
		pb_cleaning    = (ProgressBar) findViewById(R.id.pb_cleaning);
		
		sb_start_location = (ToggleButton) findViewById(R.id.sb_start_location);
		boolean icChecked = SharedPreferencesUtil.getBooleanData(this, key, false);
		sb_start_location.setChecked(icChecked);
		
		if(icChecked){
//			ToastUtil.showLongText(this, "开启定位服务了");
			startService(new Intent(getApplicationContext(), GPSService.class));
		}else{
//			ToastUtil.showLongText(this, "关闭定位服务了");
			stopService(new Intent(getApplicationContext(), GPSService.class));
		}
		
		
		sb_start_location.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferencesUtil.saveBooleanData(UniversalActivity.this, key, isChecked);
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.rel_clear_cache:		// 清理图片缓存
			customDialog();
//			customTextView();
			break;
		case R.id.img_right_icon:		// 消息中心
			WXminiAppUtil.jumpToWXmini(this);

			break;

		default:
			break;
		}
	}
	
	private void customDialog() {
		AlertDialog.Builder builder = new Builder(this);
		// 自定义一个布局文件
		View view = View.inflate(this, R.layout.payback_esc_apply_dialog, null);
		TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
		tv_des.setText("您是否要清除所有缓存数据？");
		
		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 把这个对话框取消掉
				dialog.dismiss();
				
			}
		});
		
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CleanCacheUtils.cleanInternalCache(UniversalActivity.this);
				CleanCacheUtils.cleanSharedPreference(UniversalActivity.this);
				CleanCacheUtils.cleanFiles(UniversalActivity.this);
				CleanCacheUtils.cleanExternalCache(UniversalActivity.this);

				CleanCacheUtils.deleteFilesByDirectory(new File(getCacheDir()+"/uil-images/"));
				CleanCacheUtils.deleteFilesByDirectory(new File(getFilesDir() + "/yssjsz#yssjchat"));
				
				CleanCacheUtils.cleanDatabases(UniversalActivity.this);
				dialog.dismiss();
				ToastUtil.showShortText(UniversalActivity.this, "清理完成");
//				//弹出清除完成对话框
//				final MyDialog dialog = new MyDialog(UniversalActivity.this, 300, 200,
//						  R.layout.dialog_clean_successful, R.style.Theme_dialog);
//			     dialog.show();//显示Dialog
//				//清除对话框停留3秒
//				Handler handler = new Handler();
//				handler.postDelayed(new Runnable(){
//				    public void run(){
//					dialog.dismiss();			        			      
//				    }				  
//				  },3000);
			}
		});
		
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}
	
	private void customTextView(){
		Animation translateAnimation=new TranslateAnimation(Animation.RELATIVE_TO_SELF,-1.0f, 
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		translateAnimation.setDuration(1000);
		translateAnimation.setFillAfter(true);
		tv_clear.setVisibility(View.GONE);
		tv_clear_detail.setVisibility(View.GONE);
		tv_cleaning.setVisibility(View.VISIBLE);
		pb_cleaning.setVisibility(View.VISIBLE);
		rel_clear_cache.startAnimation(translateAnimation); 
		Handler handler = new Handler();
		handler.postDelayed(new Runnable(){
		    public void run(){
		    	Animation translateAnimation2=new TranslateAnimation(Animation.RELATIVE_TO_SELF,0, 
						Animation.RELATIVE_TO_SELF, 3.0f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
				translateAnimation2.setDuration(1000);
				translateAnimation2.setFillAfter(false);

				tv_cleaning.startAnimation(translateAnimation2); 
				pb_cleaning.startAnimation(translateAnimation2);
				translateAnimation2.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						rel_clear_cache.setClickable(false);
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						tv_cleaning.setVisibility(View.GONE);
						pb_cleaning.setVisibility(View.GONE);
						tv_clear.setVisibility(View.VISIBLE);
						tv_clear_detail.setVisibility(View.VISIBLE);
						rel_clear_cache.setClickable(true);
					}
				});
				
		    }				  
		  },3000);
		
	}
	
	
}
