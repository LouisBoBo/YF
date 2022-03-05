package com.yssj.ui.activity.logins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.CheckView;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.LimitDoubleClicked;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.StringUtils;

/****
 * 找回密码(第一步)
 * 
 * @author Administrator
 * 
 */
public class ForgetPassStepActivity extends BasicActivity implements
		OnClickListener {
	private AppManager appManager;

	private CheckView ck_autos;
	private EditText et_autos, et_input_auto;
	private String[] res = new String[4];
	private TextView tv_getcode;
	private boolean flagAuto = false;
	
	private LinearLayout img_back;

	private String account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_pass_step);
		appManager = AppManager.getAppManager();
		// aBar.setTitle("找回密码");
//		aBar.hide();
		initViews();
		res = ck_autos.getValidataAndSetImage();
		addTextChange();
	}

	private void initViews() {
		ck_autos = (CheckView) findViewById(R.id.ck_autos);
		ck_autos.setOnClickListener(this);
		et_autos = (EditText) findViewById(R.id.et_autos);   //验证码
		et_input_auto = (EditText) findViewById(R.id.et_input_auto);  //手机号
		tv_getcode = (TextView) findViewById(R.id.tv_getcode);
		tv_getcode.setOnClickListener(this);
		
		TextView tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("找回密码");
		
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);

	}

	private void addTextChange() {

		et_autos.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String code = s.toString();
				if (code != null && code.length() == 4) {
					String inputCode = "";
					for (int i = 0; i < res.length; i++) {
						inputCode += res[i];
					}
					LogYiFu.e("inputCode", "" + inputCode);
					if (code.toLowerCase().equals(inputCode.toLowerCase())) {
						// 重新初始化验证码
						flagAuto = true;
					} else {
						flagAuto = false;
					}
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.forget_pass_step, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			appManager.finishActivity();
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onBackPressed() {
		appManager.finishActivity();

	}

	@Override
	public void onClick(View v) {
		if (LimitDoubleClicked.isFastDoubleClick())
			return;

		switch (v.getId()) {
		case R.id.ck_autos:
			// 重新初始化验证码
			res = ck_autos.getValidataAndSetImage();
			et_autos.setText("");
			break;
		case R.id.tv_getcode:// 下一步
			getCode(v);

			break;
		case R.id.img_back:// 返回上一级
			finish();

			break;
		default:
			break;
		}

	}

	/***
	 * 获取验证码 （下一步）
	 * 
	 * @param v
	 */
	private void getCode(View v) {
		String phone_email = et_input_auto.getText().toString().trim();
		if (!setETNull(et_input_auto, phone_email))
			return;
		String et_autod = et_autos.getText().toString().trim();
		if (!setETNull(et_autos, et_autod))
			return;

		account = phone_email;
		if (StringUtils.isEmail(phone_email)) {
			if (!flagAuto) {
				et_autos.setError("请输入正确的验证码");
				return;
			}
			getEmailCode(v, phone_email);

		} else if (StringUtils.isPhoneNumberValid(phone_email)) {
			if (!flagAuto) {
				et_autos.setError("请输入正确的验证码");
				return;
			}
			getPhoneCode(v, phone_email);
		} else {
			et_input_auto.setError("输入账号不正确，请重输");
		}

	}

	/***
	 * 手机获取验证码
	 * 
	 * @param v
	 */
	private void getPhoneCode(View v, String phone_email) {

		new SAsyncTask<String, Void, ReturnInfo>(this, v, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {

				return ComModel.sendPhoneVerifyCode(
						ForgetPassStepActivity.this, params[0], 2);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {
				LogYiFu.e("TAG", "result====");
				if(null == e){
				if (null != result) {// 获取验证码成功
					Intent intent = new Intent(ForgetPassStepActivity.this,
							FillCodeActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("type", "phone");
					bundle.putString("account", account);
					intent.putExtras(bundle);
					startActivity(intent);
				}
				}
				super.onPostExecute(context, result, e);
			}

		}.execute(phone_email);

	}

	/****
	 * 邮箱获取验证码
	 * 
	 * @param v
	 */
	private void getEmailCode(View v, String phone_email) {
		new SAsyncTask<String, Void, ReturnInfo>(this, v, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {

				return ComModel.sendEmailVerifyCode(
						ForgetPassStepActivity.this, params[0], 2);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {

				if(null == e){
				if (null != result) {
					Intent intent = new Intent(ForgetPassStepActivity.this,
							FillCodeActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("type", "email");
					bundle.putString("account", account);
					intent.putExtras(bundle);
					startActivity(intent);
				}
				}
				super.onPostExecute(context, result, e);
			}

		}.execute(phone_email);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
	}

}
