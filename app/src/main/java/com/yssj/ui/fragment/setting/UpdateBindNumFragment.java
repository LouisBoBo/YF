package com.yssj.ui.fragment.setting;

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
import com.yssj.entity.QueryPhoneInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.ToastUtil;

public class UpdateBindNumFragment extends BaseFragment implements OnClickListener {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private EditText et_new_num;
	private Button btn_next_step;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_setting_update_num, null);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("更换绑定手机");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		et_new_num = (EditText) view.findViewById(R.id.et_new_num);
		
		btn_next_step = (Button) view.findViewById(R.id.btn_next_step);
		btn_next_step.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void initData() {
		
	}
	
	private void nextStep(){
		
		final String phoneNum = et_new_num.getText().toString();
		if(TextUtils.isEmpty(phoneNum)){
			ToastUtil.showShortText(context, "请输入您需要绑定的新手机号码");
			return;
		}
		
		new SAsyncTask<Void, Void, QueryPhoneInfo>((FragmentActivity)context, R.string.wait){

			@Override
			protected QueryPhoneInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				
				return ComModel.checkPhoneIsBind(context, phoneNum);
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
				if (result != null && "1".equals(result.getStatus()) && result.isBool() == false) {
					// 往下一步跳转
					Fragment mFragment = new UpdateBindNumCodeFragment();
					Bundle bundle = new Bundle();
					bundle.putString("num", phoneNum);
					mFragment.setArguments(bundle);
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
					
				}else if (result != null && "1".equals(result.getStatus()) && result.isBool() == true) {
					
					ToastUtil.showLongText(context, "该手机号已绑定其他账号，请更换其他号码");
					
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
			mFragment = new BindPhoneFragment();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			break;
			
		case R.id.btn_next_step:
			nextStep();
			
			break;

		}
	}


}
