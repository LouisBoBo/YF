package com.yssj.ui.activity.payback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.base.BasicActivity2;
import com.yssj.ui.fragment.BackHandledFragment;
import com.yssj.ui.fragment.BackHandledInterface;
import com.yssj.ui.fragment.payback.PayBackChoiceServiceFragment;
import com.yssj.ui.fragment.payback.PayBackListFragment;
import com.yssj.ui.fragment.payback.tk.TKFragment;

public class PaybackCommonFragmentActivity extends BasicActivity2 implements BackHandledInterface{
	private String flag;
	private FragmentTransaction tr;
	 private BackHandledFragment mBackHandedFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		aBar.hide();
		setContentView(R.layout.activity_circle_common);
		flag = getIntent().getStringExtra("flag");
		tr = getSupportFragmentManager().beginTransaction();
		Fragment mFragment;
		if("payBackListFragment".equals(flag)){
			mFragment = new PayBackListFragment();
			tr.replace(R.id.fl_content, mFragment).commit();
		}else if("payBackChoiceServiceFragment".equals(flag)){
			String order_code = getIntent().getStringExtra("order_code");
			String order_price = getIntent().getStringExtra("order_price");
			boolean isHH=getIntent().getBooleanExtra("isHH",false);
			Bundle bundle = new Bundle();
			bundle.putString("order_code", order_code);
			bundle.putString("order_price", order_price);
			bundle.putBoolean("isHH", isHH);
			mFragment = new PayBackChoiceServiceFragment();
			mFragment.setArguments(bundle);
			tr.replace(R.id.fl_content, mFragment).commit();
		}else if("tKFragment".equals(flag)){	// 仅退款 
			String order_code = getIntent().getStringExtra("order_code");
			String order_price = getIntent().getStringExtra("order_price");
			Bundle bundle = new Bundle();
			bundle.putString("order_code", order_code);
			bundle.putString("order_price", order_price);
			bundle.putString("flag", "orderDetailsActivity");
			mFragment = new TKFragment();
			mFragment.setArguments(bundle);
			tr.replace(R.id.fl_content, mFragment).commit();
		}
	}
	
	public  void setResult(){
		setResult(10001);
		finish();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void setSelectedFragment(BackHandledFragment selectedFragment) {
		// TODO Auto-generated method stub
		this.mBackHandedFragment = selectedFragment;
	}
	
	  @Override
	       public void onBackPressed() {
	           if(mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()){
	               if(getSupportFragmentManager().getBackStackEntryCount() == 1){
	                  super.onBackPressed();
	               }else{
	                   getSupportFragmentManager().popBackStack();
	               }
	           }
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
	

	
}
