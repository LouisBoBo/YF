package com.yssj.ui.activity.setting;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.QueryEmailInfo;
import com.yssj.entity.QueryPhoneInfo;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.infos.SetMyPayPassActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DateUtil;
import com.yssj.utils.LimitDoubleClicked;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AccountSecureActivity extends BasicActivity implements
		OnClickListener {

	private TextView tv_account;
	private TextView tv_last_log_time;
	private TextView tvTitle_base;
	private String token;
	private RelativeLayout rel_log_pass, rel_bind_phone, rel_pay_password,
			rel_login_device, rel_secure_notice, rel_bind_email;
	private LinearLayout img_back,root;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private QueryPhoneInfo phoneInfo = new QueryPhoneInfo();

	private QueryEmailInfo emailInfo = new QueryEmailInfo();

	private AppManager appManager;

	private UserInfo userInfo;

	private ImageView iv_img_pass, iv_img_bind_phone, iv_img_bind_email,
			iv_img_pay_pass;
	
	private int status_pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();

		appManager = AppManager.getAppManager();
		appManager.addActivity(this);

		userInfo = YCache.getCacheUser(this);

		setContentView(R.layout.account_secure);
		initView();
		initData();

		bindEmail(); // 查询绑定邮箱信息
		bindPhone(); // 查询绑定手机信息

	}

	/*
	 * @Override protected void onResume() { super.onResume();
	 * JPushInterface.onResume(this); }
	 * 
	 * @Override protected void onPause() { super.onPause();
	 * JPushInterface.onPause(this);
	 * 
	 * }
	 */
	@Override
	protected void onResume() {
		super.onResume();
//		MobclickAgent.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		YJApplication.getLoader().stop();
//		MobclickAgent.onPause(this);
	}

	private void initView() {
		tv_account = (TextView) findViewById(R.id.tv_account);
		tv_account.setText(userInfo.getNickname());
		tv_last_log_time = (TextView) findViewById(R.id.tv_last_log_time);

		rel_log_pass = (RelativeLayout) findViewById(R.id.rel_log_pass); // 登陆密码
		rel_log_pass.setOnClickListener(this);

		rel_bind_phone = (RelativeLayout) findViewById(R.id.rel_bind_phone); // 绑定手机
		rel_bind_phone.setOnClickListener(this);

		rel_bind_email = (RelativeLayout) findViewById(R.id.rel_bind_email); // 绑定邮箱
		rel_bind_email.setOnClickListener(this);

		rel_pay_password = (RelativeLayout) findViewById(R.id.rel_pay_password); // 支付密码
		rel_pay_password.setOnClickListener(this);

		rel_login_device = (RelativeLayout) findViewById(R.id.rel_login_device); // 登陆设备
		rel_login_device.setOnClickListener(this);

		rel_secure_notice = (RelativeLayout) findViewById(R.id.rel_secure_notice); // 安全贴士
		rel_secure_notice.setOnClickListener(this);

		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("账号与安全");

		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		root = (LinearLayout) findViewById(R.id.root);
		root.setBackgroundColor(Color.WHITE);
		iv_img_pass = (ImageView) findViewById(R.id.iv_img_pass);
		iv_img_bind_phone = (ImageView) findViewById(R.id.iv_img_bind_phone);
		iv_img_bind_email = (ImageView) findViewById(R.id.iv_img_bind_email);
		iv_img_pay_pass = (ImageView) findViewById(R.id.iv_img_pay_pass);
	}

	private void initData() {
		new SAsyncTask<Void, Void, HashMap<String, Object>>(this, R.string.wait) {

			@Override
			protected HashMap<String, Object> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				return ComModel2.getLoginsInfo(context);
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, Object> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e) {
					LogYiFu.e("状态",result.toString());
					if (null != result.get("loginTime")) {
						tv_last_log_time.setText(DateUtil.twoDateDistance(
								new Date(Long.parseLong(result.get("loginTime")
										.toString())),
								new Date(System.currentTimeMillis())));
					}
					if (2 == (Integer) result.get("phone_status")) {
						iv_img_bind_phone
								.setBackgroundResource(R.drawable.setted);
					} else {
						iv_img_bind_phone
								.setBackgroundResource(R.drawable.un_setting);
					}

					if (2 == (Integer) result.get("email_status")) {
						iv_img_bind_email
								.setBackgroundResource(R.drawable.setted);
					} else {
						iv_img_bind_email
								.setBackgroundResource(R.drawable.un_setting);
					}
					status_pwd = (Integer) result.get("paypwd_status");
					if (2 == (Integer) result.get("paypwd_status")) {
						iv_img_pay_pass
								.setBackgroundResource(R.drawable.setted);
					} else {
						iv_img_pay_pass
								.setBackgroundResource(R.drawable.un_setting);
					}
				}
			}

		}.execute((Void[]) null);

	}

	// 查询绑定邮箱信息
	private void bindEmail() {

		new SAsyncTask<Void, Void, QueryEmailInfo>(this) {

			@Override
			protected QueryEmailInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {

				return ComModel.bindEmail(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					QueryEmailInfo result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if(null == e){
				if (result != null && "1".equals(result.getStatus())) {
					emailInfo = result;

				} else {
					ToastUtil.showLongText(context, "糟糕，出错了~~~");
				}
				}
			}
		}.execute();
	}

	// 查询绑定手机信息
	private void bindPhone() {

		new SAsyncTask<Void, Void, QueryPhoneInfo>(this) {

			@Override
			protected QueryPhoneInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {

				return ComModel.bindPhone(context);
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
				if (result != null && "1".equals(result.getStatus())) {
					phoneInfo = result;
					LogYiFu.e("tag", phoneInfo.toString());
				} else {
					ToastUtil.showLongText(context, "糟糕，出错了~~~");
				}
				}
			}

		}.execute();
	}

	@Override
	public void onClick(View v) {
		if (LimitDoubleClicked.isFastDoubleClick())
			return;
		Intent intent = null;
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;

		case R.id.rel_log_pass: // 登陆密码、修改密码
			intent = new Intent(this, ResetPassActivity.class);
			startActivity(intent);
			break;
		case R.id.rel_bind_phone: // 绑定手机


			if(!phoneInfo.isBool() && MainMenuActivity.noUserRegist){

				intent = new Intent(this, SettingCommonFragmentActivity.class);
				intent.putExtra("flag", "bindPhoneFragment");
				intent.putExtra("bool", false);
				intent.putExtra("isChanal", MainMenuActivity.noUserRegist); // 渠道包用true,其他用false
				// 为了测试方便暂时用true
				intent.putExtra("phoneNum", "");

				intent.putExtra("tishiBind", true);
				intent.putExtra("bindSelf", true);


				intent.putExtra("wallet", "account");
				intent.putExtra("thirdparty", "thirdparty");
				startActivity(intent);



			}else{
				intent = new Intent(this, SettingCommonFragmentActivity.class);
				intent.putExtra("flag", "bindPhoneFragment");
				intent.putExtra("bool", phoneInfo.isBool());
				intent.putExtra("phoneNum", phoneInfo.getPhone());
				intent.putExtra("wallet", "account");
				startActivity(intent);

			}



			((FragmentActivity) this).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);







			finish();
			break;
		case R.id.rel_bind_email: // 绑定邮箱
			// ToastUtil.showShortText(this, "绑定邮箱加载H5地址。。。");
			// intent = new Intent(this, BindEmailActivity.class);
			// startActivity(intent);
			
			intent = new Intent(this, SettingCommonFragmentActivity.class);
			intent.putExtra("flag", "bindEmailFragment");
			intent.putExtra("bool", emailInfo.isBool());
			intent.putExtra("wallet", "account");
			intent.putExtra("emailNum", emailInfo.getEmail());

			startActivity(intent);
			
			finish();
			break;
		case R.id.rel_pay_password: // 支付密码
			// intent = new Intent(this, PayPasswordActivity.class);
			// startActivity(intent);
			if (2 == status_pwd){
			intent = new Intent(this, SettingCommonFragmentActivity.class);
			intent.putExtra("flag", "payPasswordFragment");
			intent.putExtra("wallet", "account");
			startActivity(intent);
			finish();
			}else{
				intent = new Intent(this, SetMyPayPassActivity.class);
				startActivity(intent);
				finish();
			}
			
			break;
		case R.id.rel_login_device:
			bindEmail();// 登陆设备
			intent = new Intent(this, SettingCommonFragmentActivity.class);
			intent.putExtra("flag", "loginDevicesFragment");
			intent.putExtra("wallet", "wallet");
			startActivity(intent);
//			finish();
			break;
		case R.id.rel_secure_notice: // 安全贴士
			intent = new Intent(this, SettingCommonFragmentActivity.class);
			intent.putExtra("flag", "secureTipsFragment");
			intent.putExtra("wallet", "wallet");
			startActivity(intent);
//			finish();
			break;

		}
	}

}
