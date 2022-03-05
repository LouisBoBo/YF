package com.yssj.ui.activity.infos;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
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

/***
 * @author Administrator 密码
 */
public class SetMyPayPassActivity extends BasicActivity {
	private TextView tvTitle_base;
	private LinearLayout img_back,setmima;

	private EditText et_pay_pass, et_pay_pass_confir;
	private Button btn_submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getActionBar().hide();
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_wallet_set_paypassword);
		setmima = (LinearLayout) findViewById(R.id.setmima);
		setmima.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("支付密码设置");
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);

		et_pay_pass = (EditText) findViewById(R.id.et_pay_pass);
		et_pay_pass_confir = (EditText) findViewById(R.id.et_pay_pass_confir);

		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);

	}

	private void initData(){
		final String pwd = et_pay_pass.getText().toString();
		final String confir_pwd = et_pay_pass_confir.getText().toString();
		
		if(pwd.length() < 6 || pwd.length() > 6){
			ToastUtil.showLongText(this, "密码长度只能为6个数字");
			return ;
		}
		if(confir_pwd.length() < 6 || confir_pwd.length() > 6){
			ToastUtil.showLongText(this, "确认密码长度只能为6个数字");
			return ;
		}
		
		if(TextUtils.isEmpty(pwd) || TextUtils.isEmpty(confir_pwd)){
			ToastUtil.showLongText(this, "密码不能为空");
			return ;
		}
		if(!pwd.trim().equals(confir_pwd.trim())){
			ToastUtil.showLongText(this, "两次密码不一致");
			return ;
		}
		
		new SAsyncTask<Void, Void, ReturnInfo>(this, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.setWalletPwd(context, pwd);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
				super.onPostExecute(context, result, e);
				if(null == e){
				if(result != null){
					ToastUtil.showLongText(SetMyPayPassActivity.this, result.getMessage());
					finish();
				}else{
					ToastUtil.showLongText(SetMyPayPassActivity.this, "糟糕，出错了~~~");
				}
				}
			}
			
		}.execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.btn_submit:
			initData();
			break;
		}
	}

}
