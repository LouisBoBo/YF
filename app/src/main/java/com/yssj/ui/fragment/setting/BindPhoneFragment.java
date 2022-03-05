package com.yssj.ui.fragment.setting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.QueryPhoneInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.setting.AccountSecureActivity;
import com.yssj.ui.activity.setting.VerifyQuondamPhoneFragment;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.ToastUtil;

public class BindPhoneFragment extends BaseFragment implements OnClickListener {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private TextView tv_phone_num,tv_bind_state;
	private String exe;
	private String phone = "";

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_setting_secure_bindphone, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("绑定手机");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		
		tv_phone_num = (TextView) view.findViewById(R.id.tv_phone_num);
		tv_bind_state = (TextView) view.findViewById(R.id.tv_bind_state);
		tv_bind_state.setOnClickListener(this);
		exe = getActivity().getIntent().getStringExtra("wallet");
		return view;
	}

	@Override
	public void initData() {
		
	/*	这么接收不知道为啥有时会报空指针异常
	 * Bundle bundle = getArguments();
		String phoneNum = bundle.getString("phoneNum");
		if(!TextUtils.isEmpty(phoneNum)){
			tv_phone_num.setText(phoneNum.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
			
		}*/
		bindPhone();
	}
	
	
	// 查询绑定手机信息
		private void bindPhone(){
			
			new SAsyncTask<Void, Void, QueryPhoneInfo>((FragmentActivity)context){

				@Override
				protected QueryPhoneInfo doInBackground(FragmentActivity context,
						Void... params) throws Exception {
					
					return ComModel.bindPhone(context);
				}

				@Override
				protected boolean isHandleException() {
					return true;
				}
				
				@Override
				protected void onPostExecute(FragmentActivity context,
						QueryPhoneInfo result, Exception e) {
					super.onPostExecute(context, result, e);
					if(null == e){
					if(result != null && "1".equals(result.getStatus())){
						tv_phone_num.setText(result.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
						phone = result.getPhone();
					}else{
						ToastUtil.showLongText(context, "糟糕，出错了~~~");
					}
					}
				}
				
			}.execute();
		}

	@Override
	public void onClick(View v) {
		Fragment mFragment;
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();
			if("account".equals(exe)){
			Intent intent = new Intent(getActivity(), AccountSecureActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.activity_from_right_publish,R.anim.activity_from_right_publish_close);
			}
			break;
			
		case R.id.tv_bind_state:	// 更换绑定手机
//			Intent intent = new Intent(getActivity(),VerifyQuondamPhoneActivity.class);
//			intent.putExtra("phone", phone);
//			startActivity(intent);
			mFragment = new VerifyQuondamPhoneFragment();
			Bundle bundle = new Bundle();
			bundle.putString("phone", phone);
			mFragment.setArguments(bundle);
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			
			break;

		}
	}
	

}
