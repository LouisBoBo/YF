//package com.yssj.ui.activity.circles;
//
//import java.util.HashMap;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
//import android.view.Window;
//
//import com.umeng.analytics.MobclickAgent;
//import com.yssj.YJApplication;
//import com.yssj.activity.R;
//import com.yssj.ui.fragment.circles.CirclePostListFragment;
////import com.yssj.ui.fragment.circles.CirclePostListFragment.CirClePostFinish;
////import com.yssj.ui.fragment.circles.HomePageFragment;
////import com.yssj.ui.fragment.circles.HomePageFragment.HomePageFinish;
////import com.yssj.ui.fragment.circles.MyRecordListFragment;
////import com.yssj.ui.fragment.circles.MyRecordListFragment.MyRecordFinish;
//
////public class CircleCommonFragmentActivity extends FragmentActivity implements
////		CirClePostFinish, HomePageFinish, MyRecordFinish {
////	private HashMap<String, Object> map;
////	private String flag;
////	private String user_id;
////	private FragmentTransaction tr;
////
////	@Override
////	protected void onCreate(Bundle savedInstanceState) {
////		super.onCreate(savedInstanceState);
////		requestWindowFeature(Window.FEATURE_NO_TITLE);
////		setContentView(R.layout.activity_circle_common);
////		flag = getIntent().getStringExtra("flag");
////
//////		tr = getSupportFragmentManager().beginTransaction();
//////		if ("minePager".equals(flag) || "recommendPager".equals(flag)
//////				|| "allPager".equals(flag)) {
//////			map = (HashMap<String, Object>) getIntent().getSerializableExtra(
//////					"item");
//////			CirclePostListFragment fragment = new CirclePostListFragment(map);
//////			tr.replace(R.id.fl_content, fragment).commit();
//////			fragment.setOnFinish(this);
//////
//////		} else if ("myRecord".equals(flag)) {
//////			MyRecordListFragment fragment = new MyRecordListFragment();
//////			tr.replace(R.id.fl_content, fragment).commit();
//////			fragment.setOnFinish(this);
//////
//////		} else if ("circleHomePage".equals(flag)) {
//////			user_id = getIntent().getStringExtra("user_id");
//////			HomePageFragment fragment = new HomePageFragment(user_id);
//////			tr.replace(R.id.fl_content, fragment).commit();
//////			fragment.setOnFinish(this);
//////		}
////	}
////	
//////	@Override
//////	protected void onPause() {
//////		// TODO Auto-generated method stub
//////		super.onPause();
//////		YJApplication.getLoader().stop();
//////		MobclickAgent.onPause(this);
//////	}
////	
//////	@Override
//////	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
//////		// TODO Auto-generated method stub
//////		super.onActivityResult(arg0, arg1, arg2);
//////		if(arg1==3210){
//////			if(arg0==-1){
//////				user_id = getIntent().getStringExtra("user_id");
//////				HomePageFragment fragment = new HomePageFragment(user_id);
//////				tr.replace(R.id.fl_content, fragment).commit();
//////				fragment.setOnFinish(this);
//////			}
//////		}else if(arg1==3211){
//////			if(arg0==-1){
//////				map = (HashMap<String, Object>) getIntent().getSerializableExtra(
//////						"item");
//////				CirclePostListFragment fragment = new CirclePostListFragment(map);
//////				tr.replace(R.id.fl_content, fragment).commit();
//////				fragment.setOnFinish(this);	
//////			}
//////		}
//////	}
//////
//////	@Override
//////	protected void onResume() {
//////		super.onResume();
////////		JPushInterface.onResume(this);   
//////		// onresume时，取消notification显示
//////		
////////		 * HXSDKHelper.getInstance().getNotifier().reset();
//////		 MobclickAgent.onResume(this);
//////		 
//////	}
////
////	/*@Override
////	protected void onPause() {
////		super.onPause();
//////		JPushInterface.onPause(this);
////		MobclickAgent.onPause(this);
////	}*/
////	
////	@Override
////	public void finish() {
////		super.finish();
////	}
////
////}
