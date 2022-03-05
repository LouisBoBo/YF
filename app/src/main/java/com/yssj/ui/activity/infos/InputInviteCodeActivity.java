//package com.yssj.ui.activity.infos;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.ToastUtil;
//
//public class InputInviteCodeActivity extends BasicActivity{
//	private LinearLayout root;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		aBar.hide();
//		setContentView(R.layout.input_invite_code_activity);
//		initView();
//	}
//	
//	private EditText et_input_code;
//	private void initView(){
//		et_input_code = (EditText) findViewById(R.id.et_input_code);
//		findViewById(R.id.btn_exchange).setOnClickListener(this);
//		findViewById(R.id.img_back).setOnClickListener(this);
//		root = (LinearLayout ) findViewById(R.id.root);
//		root.setBackgroundColor(Color.WHITE);
//	}
//	
//	
//	private String inputCode;
//	
//	private void exchangeCode(View v){
//		inputCode = et_input_code.getText().toString().trim();
//		if(TextUtils.isEmpty(inputCode)){
//			ToastUtil.showShortText(this, "请输入邀请码");
//			return;
//		}
//		new SAsyncTask<String, Void, ReturnInfo>(this, v, R.string.wait){
//
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context,
//					String... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.exchangeInviteCode(context, params[0]);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					ReturnInfo result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if(null == e ){
//					if(result.getStatus().equals("1")){
//						Intent intent = new Intent(InputInviteCodeActivity.this, SubmitCodeStatusActivity.class);
//						intent.putExtra("isSuccess", true);
//						startActivity(intent);
//					}else{
//						Intent intent = new Intent(InputInviteCodeActivity.this, SubmitCodeStatusActivity.class);
//						intent.putExtra("isSuccess", false);
//						startActivity(intent);
//					}
//					
//					InputInviteCodeActivity.this.finish();
//				}
//			}
//			
//		}.execute(inputCode);
//	}
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		Intent intent = null;
//		switch (v.getId()) {
//		case R.id.img_back:
//			finish();
//			break;
//		case R.id.btn_exchange:
//			exchangeCode(v);
//			
//			break;
//
//		default:
//			break;
//		}
//		super.onClick(v);
//	}
//}
