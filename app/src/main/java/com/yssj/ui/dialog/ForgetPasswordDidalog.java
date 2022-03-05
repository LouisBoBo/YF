package com.yssj.ui.dialog;

import com.yssj.activity.R;
import com.yssj.ui.activity.logins.ForgetPassStepActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.setting.SettingCommonFragmentActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ForgetPasswordDidalog extends Dialog implements android.view.View.OnClickListener {

	private Context context;
	private TextView tv1, tv2;

	public ForgetPasswordDidalog(Context context) {
		super(context);
		setCanceledOnTouchOutside(false);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forget_password_dialog);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		initView();
	}

	private void initView() {
		tv1 = (TextView) findViewById(R.id.go_upload);
		tv2 = (TextView) findViewById(R.id.go_password);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.go_upload:

			if (LoginActivity.instances != null) {
				LoginActivity.instances.finish();
			}

//			if (SettingCommonFragmentActivity.instanes != null) {
//
//				SettingCommonFragmentActivity.instanes.finish();
//			}

			Intent intent = new Intent(context, LoginActivity.class);
			intent.putExtra("login_register", "login");
			context.startActivity(intent);

			dismiss();

			break;
		case R.id.go_password:

			Intent intent2 = new Intent(context, ForgetPassStepActivity.class);
			context.startActivity(intent2);

			dismiss();
			break;

		default:
			break;
		}

	}

}
