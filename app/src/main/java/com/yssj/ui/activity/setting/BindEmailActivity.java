package com.yssj.ui.activity.setting;

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
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.ToastUtil;

public class BindEmailActivity extends BasicActivity{
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private EditText et_email;
	private Button btn_send_email;
	
	private String email = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aBar.hide();
		setContentView(R.layout.activity_setting_bind_email);
		initView();
	}
	
	private void initView() {
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("绑定邮箱");
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		et_email = (EditText) findViewById(R.id.et_email);
		
		btn_send_email = (Button) findViewById(R.id.btn_send_email);
		btn_send_email.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.btn_send_email:	// 保存
			bindEmail();
			break;
		}
	}
	
	private void bindEmail(){
		email = et_email.getText().toString();
		if(TextUtils.isEmpty(email)){
			ToastUtil.showLongText(this, "email不能为空");
			return ;
		}
		
		if(!email.matches("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")){
			ToastUtil.showLongText(this, "email格式有误");
			return ;
		}
		
		
		new SAsyncTask<Void, Void, ReturnInfo>(this, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.get_EmailActivate_Code(context, email);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {
				if(null == e){
				if (result != null && "1".equals(result.getStatus())) {
					ToastUtil.showShortText(BindEmailActivity.this, result.getMessage());
					finish();
				}else{
					ToastUtil.showShortText(BindEmailActivity.this, "糟糕，出错了~~~");
				}
				}
			}
			
		}.execute();
	}
}
