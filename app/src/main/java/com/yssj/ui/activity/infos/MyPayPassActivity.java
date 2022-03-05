package com.yssj.ui.activity.infos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.base.BasicActivity;

/***
 * @author Administrator 密码
 */
public class MyPayPassActivity extends BasicActivity implements OnCheckedChangeListener{

	private RadioGroup radio_group;
	private RadioButton one, two, three;
	private EditText et_login_pass, et_pay_pass, et_confirm_pay_pass;

	private LinearLayout lin_one, lin_two, lin_three;
	private Button btn_submit;
	
	//手机处
	private EditText et_phone_no,et_v_code, et_new_pay_pass;
	private Button btn_phone_submit, btn_get_code;
	//邮箱处
	private EditText et_email_addr, et_v_email_code, et_new_pay_email_pass;
	private Button btn_email_submit, btn_get_email_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_pay_pass);
		initView();
	}

	private void initView() {
		one = (RadioButton) findViewById(R.id.one);
		one.setOnClickListener(this);
		two = (RadioButton) findViewById(R.id.two);
		two.setOnClickListener(this);
		three = (RadioButton) findViewById(R.id.three);
		three.setOnClickListener(this);
		radio_group = (RadioGroup) findViewById(R.id.radio_group);
		radio_group.setOnCheckedChangeListener(this);
		
		lin_one = (LinearLayout) findViewById(R.id.lin_one); 
		lin_two = (LinearLayout) findViewById(R.id.lin_two); 
		lin_three = (LinearLayout) findViewById(R.id.lin_three);
		
		et_login_pass = (EditText) findViewById(R.id.et_login_pass);
		et_pay_pass = (EditText) findViewById(R.id.et_pay_pass);
		et_confirm_pay_pass = (EditText) findViewById(R.id.et_confirm_pay_pass);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);
		
		et_phone_no = (EditText) findViewById(R.id.et_phone_no);
		et_v_code = (EditText) findViewById(R.id.et_v_code);
		et_new_pay_pass = (EditText) findViewById(R.id.et_new_pay_pass);
		
		btn_get_code = (Button) findViewById(R.id.btn_get_code);
		btn_get_code.setOnClickListener(this);
		btn_phone_submit = (Button) findViewById(R.id.btn_phone_submit);
		btn_phone_submit.setOnClickListener(this);
		
		//邮箱验证
		
		et_email_addr = (EditText) findViewById(R.id.et_email_addr);
		et_v_email_code = (EditText) findViewById(R.id.et_v_email_code);
		et_new_pay_email_pass = (EditText) findViewById(R.id.et_new_pay_email_pass);
		
		btn_email_submit = (Button) findViewById(R.id.btn_email_submit);
		btn_email_submit.setOnClickListener(this);
		btn_get_email_code = (Button) findViewById(R.id.btn_get_email_code);
		btn_get_email_code.setOnClickListener(this);
		
	}

	private void submitByPass(View v){
		String loginPass = et_login_pass.getText().toString().trim();
		String payPass = et_pay_pass.getText().toString().trim();
		new SAsyncTask<String, Void, ReturnInfo>(MyPayPassActivity.this, v, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.setPayPassByLoginPass(context, params[0], params[1]);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {
				super.onPostExecute(context, result,e);
				if(null == e){
				showToast(result.getMessage());
				}
			}
			
		}.execute(loginPass,payPass);
	}
	
	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkId) {
		// TODO Auto-generated method stub
		switch (checkId) {
		case R.id.one:
			lin_one.setVisibility(View.VISIBLE);
			lin_two.setVisibility(View.GONE);
			lin_three.setVisibility(View.GONE);
			break;
		case R.id.two:
			lin_one.setVisibility(View.GONE);
			lin_two.setVisibility(View.VISIBLE);
			lin_three.setVisibility(View.GONE);
			break;
		case R.id.three:
			lin_one.setVisibility(View.GONE);
			lin_two.setVisibility(View.GONE);
			lin_three.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}
	
	private void getCode(View v){
		String phoneNo = et_phone_no.getText().toString().trim();
		new SAsyncTask<String, Void, ReturnInfo>(MyPayPassActivity.this, v, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel.sendPhoneVerifyCode(context,params[0],2);
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
				showToast(result.getMessage());
				}
			}
			
		}.execute(phoneNo);
	}
	
	private void submitByPhone(View v){
		String phoneNo = et_phone_no.getText().toString().trim();
		String code = et_v_code.getText().toString().trim();
		String pay_pass = et_new_pay_pass.getText().toString().trim();
		new SAsyncTask<String, Void, ReturnInfo>(MyPayPassActivity.this, v, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.setPayPassByPhone(context, params[0], params[1], params[2]);
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
				showToast(result.getMessage());
				}
			}
			
		}.execute(phoneNo,code, pay_pass);
	}
	
	private void getEmailCode(View v){
		String emailAddr = et_email_addr.getText().toString().trim();
		new SAsyncTask<String, Void, ReturnInfo>(MyPayPassActivity.this, v, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel.sendEmailVerifyCode(context,params[0],2);
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
				showToast(result.getMessage());
				}
			}
			
		}.execute(emailAddr);
	}
	private void submitByEmail(View v){
		String emialAddr = et_email_addr.getText().toString().trim();
		String code = et_v_email_code.getText().toString().trim();
		String pay_pass = et_new_pay_email_pass.getText().toString().trim();
		new SAsyncTask<String, Void, ReturnInfo>(MyPayPassActivity.this, v, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.setPayPassByEmail(context, params[0], params[1], params[2]);
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
				showToast(result.getMessage());
				}
			}
			
		}.execute(emialAddr,code, pay_pass);
	}
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.one:
			break;
		case R.id.two:
			break;
		case R.id.btn_submit://用原密码提交
			submitByPass(v);
			break;
		case R.id.btn_get_code://手机获取验证码
			getCode(v);
			break;
		case R.id.btn_phone_submit://通过手机获取验证码提交 
			submitByPhone(v);
			break;
		case R.id.btn_get_email_code://邮箱获取验证码
			getEmailCode(v);
			break;
		case R.id.btn_email_submit://通过邮箱获取验证码提交
			submitByEmail(v);
			break;
		}

	}

}
