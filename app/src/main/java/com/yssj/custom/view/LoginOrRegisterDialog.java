//package com.yssj.custom.view;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//
//import com.yssj.activity.R;
//
//public class LoginOrRegisterDialog extends Dialog implements android.view.View.OnClickListener {
//	public OnLoginOrRegisterListener onLoginOrRegisterListener;
//	private Context context;
//	
//	public interface OnLoginOrRegisterListener{
//		public void login();
//		public void register();
//	}
//
//	public LoginOrRegisterDialog(Context context,OnLoginOrRegisterListener onLoginOrRegisterListener) {
//		super(context,R.style.log_style);
//		this.context = context;
//		this.onLoginOrRegisterListener = onLoginOrRegisterListener;
//	}
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.dialog_log_or_reg);
//		findViewById(R.id.btn_log).setOnClickListener(this);
//		findViewById(R.id.btn_reg).setOnClickListener(this);
//	}
//	
//	
//	@Override
//	public void onClick(View arg0) {
//		switch (arg0.getId()) {
//		case R.id.btn_log:
//			onLoginOrRegisterListener.login();
//			break;
//		case R.id.btn_reg:
//			onLoginOrRegisterListener.register();
//			break;
//
//		default:
//			break;
//		}
//	}
//
//}
