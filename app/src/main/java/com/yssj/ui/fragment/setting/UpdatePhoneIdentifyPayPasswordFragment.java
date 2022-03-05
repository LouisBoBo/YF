package com.yssj.ui.fragment.setting;

import android.graphics.Color;
import android.os.Bundle;
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

public class UpdatePhoneIdentifyPayPasswordFragment extends BaseFragment implements OnClickListener {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private EditText et_pay_pass,et_pay_pass_confir;
	private Button btn_submit;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_setting_update_paypassword, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("手机验证");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		et_pay_pass = (EditText) view.findViewById(R.id.et_pay_pass);
		et_pay_pass_confir = (EditText) view.findViewById(R.id.et_pay_pass_confir);
		
		btn_submit = (Button) view.findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void initData() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			PhoneIdentityFragment mFragment = new PhoneIdentityFragment();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			break;
			
		case R.id.btn_submit:	// 保存
			upwalletpwdbysms();
			break;
			

		}
	}
	
	private void upwalletpwdbysms(){
		
		final String pass = et_pay_pass.getText().toString();
		String rePass = et_pay_pass_confir.getText().toString();
		if(TextUtils.isEmpty(pass) || TextUtils.isEmpty(pass)){
			ToastUtil.showLongText(context, "请输入修改的支付密码");
			return;
		}
		
		if(!pass.equals(rePass)){
			ToastUtil.showLongText(context, "两次密码不一致");
			return;
		}
		
		if(pass.length() != 6 || rePass.length() != 6){
			ToastUtil.showLongText(context, "支付密码只能是6位数字哦");
			return ;
		}
		
		Bundle bundle = getArguments();
		final String code = bundle.getString("code");
		
		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				
				return ComModel.upwalletpwdbysms(context, code, pass);
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
					ToastUtil.showShortText(context, result.getMessage());
					// 往下一步跳转
					getActivity().finish();
					
				}else if (result != null && "2".equals(result.getStatus())) {
					ToastUtil.showShortText(context, result.getMessage());
				}else if (result != null && "3".equals(result.getStatus())) {
					ToastUtil.showShortText(context, result.getMessage());
				}
			}
			}
		}.execute();
	}


}
