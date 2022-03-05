package com.yssj.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.yssj.activity.R;
import com.yssj.ui.activity.setting.SettingCommonFragmentActivity;

/****
 * 支付失败
 * 
 * @author Administrator
 * 
 */
public class PayErrorDialog extends Dialog implements OnClickListener {
	private Context context;
	private int pwdflag;
	private TextView errorContent;
	private String message;
	private Button rePut,findPWD;
	
	
	


	public PayErrorDialog(Context context, int style, int pwdflag,String message) {
		super(context, style);
		setCanceledOnTouchOutside(true);
		this.context = context;
		this.pwdflag = pwdflag;
		this.message = message;
		

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_pay_error);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		
		findPWD=(Button) findViewById(R.id.findPWD);
		findPWD.setOnClickListener(this);
		rePut=(Button) findViewById(R.id.rePut);
		rePut.setOnClickListener(this);
		errorContent=(TextView) findViewById(R.id.errorContent);
		errorContent.setOnClickListener(this);
		//errorContent.setText(message);
		
		if(pwdflag == 1){
			errorContent.setText("您的密码错误，还有2次输入的机会");
			
		}
		if(pwdflag == 2){
			errorContent.setText("您的密码错误，还有1次输入的机会");
		}
		
		if(pwdflag == 3){
			errorContent.setText("输入错误的次数已达上限，请通过其他方式修改支付密码");
		}
		
		
		
		
		
	}


		
		
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rePut:
			dismiss();
			break;
		case R.id.findPWD:
			Intent intent ;
			intent = new Intent(context, SettingCommonFragmentActivity.class);
			intent.putExtra("flag", "payPasswordFragment");
//			intent.putExtra("wallet", "account");
			getContext().startActivity(intent);
			
//			Intent intent;
//			intent = new Intent(context, PayPasswordActivity01.class);
//			context.startActivity(intent);
			
			dismiss();
			break;
		default:
			break;
		}

	}

}