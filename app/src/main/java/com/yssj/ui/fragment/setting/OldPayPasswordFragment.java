package com.yssj.ui.fragment.setting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

public class OldPayPasswordFragment extends BaseFragment implements OnClickListener {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private EditText et_old_pay_pass;
	private Button btn_next_step;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_setting_old_paypassword, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("支付密码");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		et_old_pay_pass = (EditText) view.findViewById(R.id.et_old_pay_pass);
		
		btn_next_step = (Button) view.findViewById(R.id.btn_next_step);
		btn_next_step.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void initData() {
		pwdXianZhi();//限制密码输出内容
		
	}
	
	private void pwdXianZhi() {
		et_old_pay_pass.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String editable = et_old_pay_pass.getText().toString();
				String str = stringFilter(editable);
				if (!editable.equals(str)) {
					et_old_pay_pass.setText(str);
					ToastUtil.showShortText(getActivity(), "只能输入数字~");
					// 设置新的光标所在位置
					et_old_pay_pass.setSelection(str.length());
				}
				
				
			
			}
			
			

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
			
			private String stringFilter(String str) throws PatternSyntaxException{
				// 只允许输入数字
				String regEx = "[^[0-9]*$]"; // "[\u4e00-\u9fa5]"
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(str);
				return m.replaceAll("").trim();
			}
		});
		
	}

	private void ckPwd(){
		
		final String password = et_old_pay_pass.getText().toString();
		if(TextUtils.isEmpty(password)){
			ToastUtil.showShortText(context, "请输入原支付密码");
			return;
		}
		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				
				return ComModel.ckPwd(context, password);
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
					Fragment mFragment = new UpdateOldPayPasswordFragment();
					Bundle bundle = new Bundle();
					bundle.putString("old_pass", password);
					mFragment.setArguments(bundle);
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
					
				}else if (result != null && "2".equals(result.getStatus())) {
					
					ToastUtil.showShortText(context, result.getMessage());
					
				}else if (result != null && "3".equals(result.getStatus())) {
					
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
			mFragment = new PayPasswordFragment();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			break;
			
		case R.id.btn_next_step:
			ckPwd();
			
			break;

		}
	}


}
