package com.yssj.ui.fragment.orderinfo;

import com.yssj.activity.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WalletDialog extends Dialog{
	
	private EditText et_paypwd ; 
	private TextView tv_pay_ok; 
	private String pay_pwd;
	
	private Context context ; 
	public OnCallBackPayListener listener;
	
	public interface OnCallBackPayListener{
		void selectPwd(String pwd);
	}

	public WalletDialog(Context context, int theme) {
		super(context, theme);
		this.context = context; 
		this.setCanceledOnTouchOutside(true);
	
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_wallet);
		et_paypwd = (EditText) findViewById(R.id.et_paypwd);
		tv_pay_ok = (TextView) findViewById(R.id.tv_pay_ok);
		
		tv_pay_ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pay_pwd = et_paypwd.getText().toString().trim();
				if (!TextUtils.isEmpty(pay_pwd)) {
					if (listener != null) {
						listener.selectPwd(pay_pwd);
					}
					
					dismiss();
				}else {
					Toast .makeText(context, "请输入支付密码", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
	}

}
