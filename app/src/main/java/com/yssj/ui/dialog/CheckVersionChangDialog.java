//package com.yssj.ui.dialog;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.TextView;
//import com.yssj.activity.R;
//import com.yssj.ui.activity.setting.SettingCommonFragmentActivity;
//import com.yssj.utils.SharedPreferencesUtil;
//
///****
// * 
// * 
// * @author Administrator
// * 
// */
//public class CheckVersionChangDialog extends Dialog implements OnClickListener {
//	private Context context;
//	private int pwdflag;
//	private TextView errorContent;
//	private String message;
//	private Button rePut,findPWD;
//	private Activity activity;
//	
//	
//
//
//	public CheckVersionChangDialog(Context context, int style,Activity activity) {
//		super(context, style);
//		setCanceledOnTouchOutside(true);
//		this.context = context;
//		this.activity=activity;
//		
//		
//
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.dialog_check_version_error);
//		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//		this.setCancelable(false);
//		
//		findPWD=(Button) findViewById(R.id.findPWD);
//		findPWD.setOnClickListener(this);
//		rePut=(Button) findViewById(R.id.rePut);
//		rePut.setOnClickListener(this);
////		errorContent=(TextView) findViewById(R.id.errorContent);
////		errorContent.setOnClickListener(this);
//		//errorContent.setText(message);
//	
//		
//		
//		
//		
//	}
//
//
//		
//		
//	
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.rePut://取消
//			dismiss();
//			activity.finish();
//			break;
//		case R.id.findPWD://确定
//			
//			SharedPreferencesUtil.saveStringData(getContext(), "tags", "");
//			
//			
//			
//			
//			
//			
//			
////			Intent intent ;
////			intent = new Intent(context, SettingCommonFragmentActivity.class);
////			intent.putExtra("flag", "payPasswordFragment");
//////			intent.putExtra("wallet", "account");
////			getContext().startActivity(intent);
//			
////			Intent intent;
////			intent = new Intent(context, PayPasswordActivity01.class);
////			context.startActivity(intent);
//			
//			dismiss();
//			break;
//		default:
//			break;
//		}
//
//	}
//
//}