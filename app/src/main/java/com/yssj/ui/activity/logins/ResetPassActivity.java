package com.yssj.ui.activity.logins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.base.BasicActivity;

/***
 * 修改密码
 * 
 * @author Administrator
 * 
 */
public class ResetPassActivity extends BasicActivity implements OnClickListener {
	private AppManager appManager;

	private EditText et_input_pwd, et_reset_pwd;
	private ImageView et_input_pwd_xx,et_reset_pwd_xx;
	private String pwd, rpwd;
	private TextView tv_finish_set;
	private String account, code, type;
	
	private LinearLayout img_back;
	private TextView tvTitle_base;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_pass);
		appManager = AppManager.getAppManager();
		// aBar.setTitle("输入新密码");
//		aBar.hide();
		Bundle bundle = getIntent().getExtras();
		account = bundle.getString("account");
		code = bundle.getString("code");
		type = bundle.getString("type");
		initViews();
		addTextChange();
	}

	private void addTextChange() {
		et_input_pwd.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String et_input_pwd_text = et_input_pwd.getText().toString();
				if (et_input_pwd_text.length() > 0) {
					et_input_pwd_xx.setVisibility(View.VISIBLE);
				} else {
					et_input_pwd_xx.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		et_reset_pwd.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String et_reset_pwd_text = et_reset_pwd.getText().toString();
				if (et_reset_pwd_text.length() > 0) {
					et_reset_pwd_xx.setVisibility(View.VISIBLE);
				} else {
					et_reset_pwd_xx.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	}

	private void initViews() {
		et_input_pwd = (EditText) findViewById(R.id.et_input_pwd);
		et_reset_pwd = (EditText) findViewById(R.id.et_reset_pwd);
		tv_finish_set = (TextView) findViewById(R.id.tv_finish_set);
		et_input_pwd_xx = (ImageView) findViewById(R.id.et_input_pwd_xx);
		et_reset_pwd_xx = (ImageView) findViewById(R.id.et_reset_pwd_xx);
		et_input_pwd_xx.setOnClickListener(this);
		et_reset_pwd_xx.setOnClickListener(this);
		tv_finish_set.setOnClickListener(this);
		
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("找回密码");

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_finish_set:// 重置密码成功
			summit(tv_finish_set);

			break;
		case R.id.img_back:// 返回上一层
			finish();

			break;
		case R.id.et_input_pwd_xx:
			et_input_pwd.getText().clear();
			break;
		case R.id.et_reset_pwd_xx:
			et_reset_pwd.getText().clear();
			break;

		default:
			break;
		}

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

	/***
	 * 完成修改
	 * 
	 * @param v
	 */
	private void summit(View v) {
		pwd = et_input_pwd.getText().toString().trim();
		if (!setETNull(et_input_pwd, pwd))
			return;
		rpwd = et_reset_pwd.getText().toString().trim();
		
		if (!setETNull(et_reset_pwd, rpwd))
			return;
		if (!pwd.equals(rpwd)) {
			Toast.makeText(this, "你两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if(pwd.length()<6||pwd.length()>16){
			Toast.makeText(this, "密码长度为6-16位", Toast.LENGTH_SHORT)
			.show();
	return;
		}

		if (type.equals("phone")) {// 手机找回
			findPwdPhone(v);
		} else {// 邮箱找回
			findPwdEmail(v);
		}
	}

	/***
	 * 邮箱找回
	 * 
	 * @param v
	 */
	private void findPwdEmail(View v) {
		new SAsyncTask<String, Void, ReturnInfo>(ResetPassActivity.this, v,
				R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				return ComModel.ResetPass4ForgetEmail(ResetPassActivity.this,
						params[0], params[1], params[2]);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {

				if(null == e){
				if (result.getStatus().equals("1")) {
					Toast.makeText(ResetPassActivity.this, "邮箱重置密码成功",
							Toast.LENGTH_LONG).show();

					ResetPassActivity.this
							.startActivity(new Intent(ResetPassActivity.this,
									LoginRightNowActivity.class));
					finish(3);
				}
				}
				super.onPostExecute(context, result, e);
			}

		}.execute(account, pwd, code);

	}

	/***
	 * 手机找回
	 * 
	 * @param v
	 */
	private void findPwdPhone(View v) {
		new SAsyncTask<String, Void, ReturnInfo>(ResetPassActivity.this, v,
				R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				return ComModel.ResetPass4ForgetFhone(ResetPassActivity.this,
						params[0], params[1], params[2]);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {

				if(null == e){
				if (result.getStatus().equals("1")) {
					Toast.makeText(ResetPassActivity.this, "手机重置密码成功",
							Toast.LENGTH_LONG).show();
					ResetPassActivity.this
							.startActivity(new Intent(ResetPassActivity.this,
									LoginRightNowActivity.class));
					finish(3);
				}
				}
				super.onPostExecute(context, result, e);
			}

		}.execute(account, pwd, code);

	}

	private void finish(int position) {
		for (int i = 0; i < position; i++) {
			appManager.finishActivity();
		}

	}

}
