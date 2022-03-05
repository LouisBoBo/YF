package com.yssj.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;

import com.yssj.activity.R;
import com.yssj.ui.activity.logins.LoginActivity;
/**
 * 未登录时提示登录
 * @author lbp
 *
 */
public class ToLoginDialog extends Dialog implements android.view.View.OnClickListener{
	
	private Context context;
	
	private int requestCode;
	
	public ToLoginDialog(Context context) {
		super(context,R.style.my_invate_dialog);
		this.context=context;
		setContentView(R.layout.to_login_dialog);
		this.getWindow().setWindowAnimations(R.style.my_dialog_anim_style);
		findViewById(R.id.login).setOnClickListener(this);
		findViewById(R.id.register).setOnClickListener(this);
		findViewById(R.id.back).getBackground().setAlpha(210);
	}
	
	
	public void setRequestCode(int code){
		this.requestCode=code;
	}
	
	@Override
	public void onClick(View v) {
		dismiss();
		switch (v.getId()) {
		case R.id.login: 
		{
			
			if (LoginActivity.instances != null){
				LoginActivity.instances .finish();
			}
			Intent intent =new Intent(context, LoginActivity.class);
			intent.putExtra("login_register", "login");
			((FragmentActivity)context).startActivityForResult(intent, requestCode);
		}
			break;
		case R.id.register:
		{
			
			if (LoginActivity.instances != null){
				LoginActivity.instances .finish();
			}
			Intent intent =new Intent(context, LoginActivity.class);
			intent.putExtra("login_register", "register");
			((FragmentActivity)context).startActivityForResult(intent,requestCode);
		}
		break;

		default:
			break;
		}
	}

}
