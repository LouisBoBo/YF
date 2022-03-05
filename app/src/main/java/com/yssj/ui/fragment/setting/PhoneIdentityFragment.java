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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.huanxin.PublicUtil;
import com.yssj.model.ComModel;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.CenterToast;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

public class PhoneIdentityFragment extends BaseFragment implements OnClickListener {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private EditText et_identify_code;
	private Button btn_next_step,btn_get_identify_code;
	private String code;
	private EditText et_auto;
	private ImageView ck_auto;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_setting_phone_identity, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("手机验证");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		et_identify_code = (EditText) view.findViewById(R.id.et_identify_code);
		
		btn_get_identify_code = (Button) view.findViewById(R.id.btn_get_identify_code);
		btn_get_identify_code.setOnClickListener(this);
		
		btn_next_step = (Button) view.findViewById(R.id.btn_next_step);
		btn_next_step.setOnClickListener(this);
		et_auto = (EditText) view.findViewById(R.id.et_auto);
		ck_auto = (ImageView) view.findViewById(R.id.ck_auto);
		ck_auto.setOnClickListener(this);
		return view;
	}

	@Override
	public void initData() {
		PublicUtil.setVCodePwd(getActivity(),ck_auto);
	}
	private boolean isClick=true;
	@Override
	public void onClick(View v) {
		Fragment mFragment;
		switch (v.getId()) {
		case R.id.img_back:
			mFragment = new PayPasswordFragment();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			break;
			
		case R.id.btn_get_identify_code:	// 获取手机验证码
			if(TextUtils.isEmpty(et_auto.getText().toString().trim())){
				CenterToast.centerToast(context, "请先输入图形验证码");
				return;
			}
			String vcode = et_auto.getText().toString().trim();
			if(isClick){
				isClick=false;
				sendIdentityCode(vcode);
			}
			
			break;
		case R.id.btn_next_step:
			ckPhoneCode();
			break;
		case R.id.ck_auto:
			PublicUtil.setVCodePwd(getActivity(),ck_auto);
			break;
		default:
			break;
		}
	}
	
	
	private void sendIdentityCode(final String vcode){
		code = et_identify_code.getText().toString();
		new SAsyncTask<String, Void, ReturnInfo>(getActivity()){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				return ComModel.get_Phone_Code_To_Uppaypas(context,vcode);
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
						ToastUtil.showShortText(context, result.getMessage());
						et_identify_code.setFocusable(true);
						et_identify_code.requestFocus();

						new CountDownTimer(Long.parseLong(getResources().getString(R.string.identify_code)),1000) {

							@Override
							public void onTick(long millisUntilFinished) {
								btn_get_identify_code.setBackgroundResource(R.drawable.bg_square_bind_btn_default);
								btn_get_identify_code.setText(String.valueOf(millisUntilFinished/1000) + "秒");
							}

							@Override
							public void onFinish() {
								PublicUtil.setVCodePwd(getActivity(),ck_auto);//发送手机验证码之后 重新从后台获取图形验证码并显示
								et_auto.getText().clear();
								btn_get_identify_code.setText("重新发送");
								btn_get_identify_code.setBackgroundResource(R.drawable.bg_square_choice_btn_checked);
								isClick=true;
							}
						}.start();
					}else{
						PublicUtil.setVCodePwd(getActivity(),ck_auto);//发送手机验证码之后 重新从后台获取图形验证码并显示
						et_auto.getText().clear();
						btn_get_identify_code.setText("重新发送");
						btn_get_identify_code.setBackgroundResource(R.drawable.bg_square_choice_btn_checked);
						isClick=true;
					}
				}else{
					isClick=true;
				}

			}
		}.execute();
	}
	
	/**
	 * 验证手机短信验证码是否正确
	 */
	private void ckPhoneCode(){
		code = et_identify_code.getText().toString();
		if(TextUtils.isEmpty(code)){
			ToastUtil.showLongText(context, "请输入验证码");
			return;
		}
		new SAsyncTask<String, Void, ReturnInfo>(getActivity()){
			
			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				return ComModel.ckPhoneCode(context,code);
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
					
					Fragment mFragment = new UpdatePhoneIdentifyPayPasswordFragment();
					Bundle bundle = new Bundle();
					bundle.putString("code", code);
					mFragment.setArguments(bundle);
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
					
				}else{
					ToastUtil.showLongText(context, result.getMessage());
				}
				
				}else{
					et_identify_code.setText("");
				}
			}
			
		}.execute();
	}
	

}
