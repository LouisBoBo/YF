package com.yssj.ui.fragment.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
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

public class FirstBindEmailIdentityCodeFragment extends BaseFragment implements OnClickListener {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private EditText et_identify_code;
	private Button btn_next_step,btn_get_identify_code;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_setting_first_bind_email_code, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("绑定邮箱");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		et_identify_code = (EditText) view.findViewById(R.id.et_identify_code);
		
		btn_next_step = (Button) view.findViewById(R.id.btn_next_step);
		btn_next_step.setOnClickListener(this);
		
		btn_get_identify_code = (Button) view.findViewById(R.id.btn_get_identify_code);
		btn_get_identify_code.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void initData() {
		
	}
	
	private void getCode(){
		
		final String email = getArguments().getString("email");
		if(TextUtils.isEmpty(email)){
			ToastUtil.showLongText(context, "邮箱不能为空");
		}
		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				
				return ComModel.sendEmailVerifyCode(context, email,7);
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
				if(result != null && "1".equals(result.getStatus())){
					new CountDownTimer(Long.parseLong(getResources().getString(R.string.identify_code)), 1000) {

						@Override
						public void onTick(long millisUntilFinished) {
							btn_get_identify_code.setText(String.valueOf(millisUntilFinished / 1000) + "秒重发");
						}

						@Override
						public void onFinish() {
							btn_get_identify_code.setText("重新发送");
						}
					}.start();
					
				}else{
					btn_get_identify_code.setText("重新发送");
				}
				ToastUtil.showShortText(context, result.getMessage());
			}
			}
			
		}.execute();
	}
	
	private void checkCode(){
		final String code = et_identify_code.getText().toString();
		if(TextUtils.isEmpty(code)){
			ToastUtil.showShortText(context, "请输入验证码");
			return;
		}
		
		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){
			
			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				
				return ComModel.checkEmailCode(context, code);
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
					Fragment mFragment = new SuccessBindEmailFragment();
//					Bundle bundle = new Bundle();
//					bundle.putString("firstBind", "firstBindEmail");
//					mFragment.setArguments(bundle);
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
					
				}else {
					ToastUtil.showLongText(context, result.getMessage());
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
			mFragment = new FirstBindEmailFragment();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			break;
			
		case R.id.btn_next_step:
			checkCode();
			break;
		case R.id.btn_get_identify_code:	// 获取验证码
			getCode();
			break;
			

		}
	}


}
