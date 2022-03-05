package com.yssj.ui.fragment.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.ToastUtil;

public class SecureIdentifyFragment extends BaseFragment implements OnClickListener {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private EditText et_pay_pass;
	private Button btn_next_step;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_setting_secure_identify, null);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("安全验证");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		et_pay_pass = (EditText) view.findViewById(R.id.et_pay_pass);
		
		btn_next_step = (Button) view.findViewById(R.id.btn_next_step);
		btn_next_step.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void initData() {
		
	}
	
	private void checkPaymentPassword(){
		
		final String password = et_pay_pass.getText().toString();
		if(TextUtils.isEmpty(password)){
			ToastUtil.showShortText(context, "请输入支付密码");
			return;
		}
		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				
				return ComModel.checkPaymentPassword(context, password);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {
				super.onPostExecute(context, result, e);
				if(null == e){
				if (result != null && "1".equals(result.getStatus())) {
					// 往下一步跳转
					InputMethodManager inputMethodManager =
					        (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
					        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//					Fragment mFragment = new SuccessBindPhoneActivity();
//					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();


					Intent in = new Intent(getActivity(),SuccessBindPhoneActivity.class);

//					in.putExtra("buy0","buy0");
					getActivity().startActivity(in);
					((Activity) getActivity()).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

					
				}else{
					
					ToastUtil.showShortText(context, result.getMessage());
					
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
			 Bundle bundle = getArguments();
			 String flag = "";
			 if(bundle != null){
				 flag = bundle.getString("firstBind");
			 }
			if("firstBindPhone".equals(flag)){
				mFragment = new FirstBindPhoneIdentityCodeFragment();
				getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			}else{
				mFragment = new UpdateBindNumCodeFragment();
				getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			}
			break;
			
		case R.id.btn_next_step:
			checkPaymentPassword();
			
			break;

		}
	}


}
