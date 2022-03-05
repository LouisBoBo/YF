package com.yssj.ui.activity.setting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.logins.LoginFragment;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.MineIfoFragment;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

public class ResetPassActivity extends BasicActivity {

	private Button  btn_submit;

	private EditText et_old_pass, et_new_pass, et_confirm_new_pass;
	private String oldPass, newPass, confirmPass;
	
	private LinearLayout img_back,denglumima;
	
	private AppManager appManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		appManager = AppManager.getAppManager();
		setContentView(R.layout.reset_pass);
		initView();
	}

	private void initView() {

		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);

		et_old_pass = (EditText) findViewById(R.id.et_old_pass);
		et_new_pass = (EditText) findViewById(R.id.et_new_pass);
		et_confirm_new_pass = (EditText) findViewById(R.id.et_confirm_new_pass);

		denglumima= (LinearLayout) findViewById(R.id.denglumima);
		denglumima.setBackgroundColor(Color.WHITE);
		TextView tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("修改密码");
		
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		//限制密码内容
		astrictPWD(et_old_pass);
		astrictPWD(et_new_pass);
		astrictPWD(et_confirm_new_pass);
	}

	private void astrictPWD(final EditText ed) {
		
	ed.addTextChangedListener( new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
				String editable = ed.getText().toString();  
		          String str = stringFilter(editable.toString());
		          if(!editable.equals(str)){
		        	  ed.setText(str);
		              //设置新的光标所在位置  
		        	  	ToastUtil.showLongText(getApplicationContext(), "不能输入汉字");
		        	  	ed.setSelection(str.length());
		          }
				
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	
	
	

	public static String stringFilter(String str) throws PatternSyntaxException {
		//不允许输入
		String regEx = "[\u4E00-\u9FA5]"; // "[\u4e00-\u9fa5]"
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	private void submit(View v) {
		oldPass = et_old_pass.getText().toString().trim();
		newPass = et_new_pass.getText().toString().trim();
		confirmPass = et_confirm_new_pass.getText().toString().trim();
		
		if(TextUtils.isEmpty(oldPass)){
			showToast("原始密码不能为空！ ");
			return;
		}
		if(TextUtils.isEmpty(newPass)){
			showToast("新密码不能为空！ ");
			return;
		}
		if(TextUtils.isEmpty(confirmPass)){
			showToast("确认密码不能为空！ ");
			return;
		}
		
		
		if(!( (newPass.length() > 5 && newPass.length() < 17))){
			ToastUtil.showShortText(this, "密码只能6-16个字符");
			return;
		}
		
		if (!newPass.equals(confirmPass)) {
			showToast("两次输入的新密码不一致，请确认！ ");
			return;
		}
		new SAsyncTask<String, Void, ReturnInfo>(ResetPassActivity.this, v,
				R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				
				return ComModel2.ResetPass4Setting(ResetPassActivity.this,
						params[1], params[0],
						YCache.getCacheToken(ResetPassActivity.this));
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result,Exception e) {
				if(null == e){
				showToast(result.getMessage());
				if(result.getStatus().equals("1")){
					ToastUtil.showShortText(context, "您已成功修改密码，请重新登陆！");
					appManager.finishAllActivityOfEveryDayTask();
					
					if (LoginActivity.instances != null){
						LoginActivity.instances .finish();
					}
					
					startActivity(new Intent(YJApplication.instance, LoginActivity.class).putExtra("login_register", "login"));
					
				}
				}
				super.onPostExecute(context, result,e);
			}

		}.execute(oldPass, newPass);
	}
	
/*	private LoginActivityFinish mFinish;
	public interface LoginActivityFinish{
		void finish();
	}
	
	public void setOnFinish(Activity mActivity){
		this.mFinish = (LoginActivityFinish) mActivity;
	}
	*/
	
	@Override
	public void onBackPressed() {
		appManager.finishActivity();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_submit:
			submit(v);
			break;
		case R.id.img_back:
			finish();
			break;
		default:
			break;
		}
	}

}
