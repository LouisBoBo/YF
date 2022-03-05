package com.yssj.ui.fragment.setting;

import android.graphics.Color;
import android.os.Bundle;
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
import com.yssj.entity.QueryEmailInfo;
import com.yssj.entity.QueryPhoneInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.ToastUtil;

public class UpdateBindEmailFragment extends BaseFragment implements OnClickListener {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private EditText et_new_email;
	private Button btn_next_step;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_setting_update_email, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("更换绑定邮箱");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		et_new_email = (EditText) view.findViewById(R.id.et_new_email);
		
		btn_next_step = (Button) view.findViewById(R.id.btn_next_step);
		btn_next_step.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void initData() {
		
	}
	
	private void nextStep(){
		
		final String email = et_new_email.getText().toString();
		if(TextUtils.isEmpty(email)){
			ToastUtil.showShortText(context, "请输入您需要绑定的新邮箱账号");
			return;
		}
		
		new SAsyncTask<Void, Void, QueryEmailInfo>((FragmentActivity)context, R.string.wait){

			@Override
			protected QueryEmailInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				
				return ComModel.checkEmailIsBind(context, email);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					QueryEmailInfo result, Exception e) {
				super.onPostExecute(context, result, e);
				if(null == e){
				if (result != null && "1".equals(result.getStatus()) && result.isBool() == false) {
					// 往下一步跳转
					Fragment mFragment = new UpdateBindEmailCodeFragment();
					Bundle bundle = new Bundle();
					bundle.putString("email", email);
					mFragment.setArguments(bundle);
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
					
				}else if (result != null && "1".equals(result.getStatus()) && result.isBool() == true) {
					
					ToastUtil.showLongText(context, "该邮箱已绑定其他账号，请更换");
					
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
			mFragment = new BindEmailFragment();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			break;
			
		case R.id.btn_next_step:
			nextStep();
			
			break;

		}
	}


}
