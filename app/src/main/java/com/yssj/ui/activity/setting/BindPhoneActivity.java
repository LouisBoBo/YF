package com.yssj.ui.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.QueryPhoneInfo;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.infos.SetMyPayPassActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

public class BindPhoneActivity extends BasicActivity {

	private EditText et_identify_code;
	private Button btn_get_identify_code, btn_next_step;
	private EditText et_phone_num;
	private LinearLayout img_back, root;
	private boolean isjingxuan;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_sign_bind_phone);
		context = this;
		root = (LinearLayout) findViewById(R.id.root);
		root.setBackgroundColor(Color.WHITE);
		isjingxuan = getIntent().getBooleanExtra("isjingxuan", false);

		TextView tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("绑定手机");
		tvTitle_base.setTextSize(18.0f);

		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);

		et_identify_code = (EditText) findViewById(R.id.et_identify_code);
		btn_get_identify_code = (Button) findViewById(R.id.btn_get_identify_code);
		btn_get_identify_code.setOnClickListener(this);

		et_phone_num = (EditText) findViewById(R.id.et_phone_num);

		btn_next_step = (Button) findViewById(R.id.btn_next_step);
		btn_next_step.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		switch (arg0.getId()) {

		case R.id.img_back:
			finish();
			break;
		case R.id.btn_get_identify_code: // 获取验证码
			getCode();
			break;
		case R.id.btn_next_step:
			bindPhone();
			break;
		}
	}

	private void bindPhone() {

		final String num = et_phone_num.getText().toString();
		if (TextUtils.isEmpty(num)) {
			ToastUtil.showShortText(context, "输入您要绑定的手机号");
			return;
		}
		new SAsyncTask<Void, Void, QueryPhoneInfo>((FragmentActivity) context, R.string.wait) {

			@Override
			protected QueryPhoneInfo doInBackground(FragmentActivity context, Void... params) throws Exception {

				return ComModel.checkPhoneIsBind(context, num);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, QueryPhoneInfo result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e) {
					if (result != null && "1".equals(result.getStatus()) && result.isBool() == false) {
						// 往下一步跳转

						checkCode();

					} else if (result != null && "1".equals(result.getStatus()) && result.isBool() == true) {

						ToastUtil.showLongText(context, "该手机号已绑定其他账号，请更换其他号码");

					} else if (result != null && "50".equals(result.getStatus())) {
						ToastUtil.showLongText(context, "支付密码未初始化，请先设置支付密码才能绑定手机");
						Intent intent = new Intent(BindPhoneActivity.this, SetMyPayPassActivity.class);
						startActivity(intent);
					} else {
						ToastUtil.showLongText(context, result.getMessage());
					}
				}
			}
		}.execute();
	}

	private void getCode() {

		final String phone = et_phone_num.getText().toString();
		if (TextUtils.isEmpty(phone)) {
			ToastUtil.showLongText(context, "手机号不能为空");
		}
		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {

				return ComModel.sendPhoneVerifyCode(context, phone, 7);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e) {
					if (result != null && "1".equals(result.getStatus())) {
						new CountDownTimer(Long.parseLong(getResources().getString(R.string.identify_code)), 1000) {

							@Override
							public void onTick(long millisUntilFinished) {
								btn_get_identify_code.setText(String.valueOf(millisUntilFinished / 1000) + "秒重发");
								btn_get_identify_code.setEnabled(false);
							}

							@Override
							public void onFinish() {
								btn_get_identify_code.setText("重新发送");
								btn_get_identify_code.setEnabled(true);
							}
						}.start();

					} else {
						btn_get_identify_code.setText("重新发送");
						btn_get_identify_code.setEnabled(true);
					}
					ToastUtil.showShortText(context, result.getMessage());
				}
			}

		}.execute();
	}

	private void checkCode() {
		final String code = et_identify_code.getText().toString();
		if (TextUtils.isEmpty(code)) {
			ToastUtil.showShortText(context, "请输入验证码");
			return;
		}

		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {

				return ComModel.checkCode(context, code, "", "");
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e) {
					if (result != null && "1".equals(result.getStatus())) {
						SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
						context.getSharedPreferences("sign_bind", Context.MODE_PRIVATE).edit()
								.putBoolean("sign_tag2", true).commit();
						SharedPreferencesUtil.saveStringData(context, Pref.PHONE,
								"" + et_phone_num.getText().toString());
						YCache.getCacheUser(context).setPhone(et_phone_num.getText().toString());
						if (isjingxuan) {
							setResult(-10002);
						}

						finish();
						if (!isjingxuan) {
							ToastUtil.showLongText(context, result.getMessage());
						}

					} else {
						ToastUtil.showLongText(context, result.getMessage());
					}
				}
			}

		}.execute();
	}

}
