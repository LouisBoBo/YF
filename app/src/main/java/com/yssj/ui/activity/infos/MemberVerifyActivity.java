//package com.yssj.ui.activity.infos;
//
//import java.util.HashMap;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.entity.UserInfo;
//import com.yssj.model.ComModel;
//import com.yssj.ui.activity.MembersGoodsListActivity;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YCache;
//
//public class MemberVerifyActivity extends BasicActivity {
//	private LinearLayout img_back,menb;
//	private TextView tvTitle_base,tv_member_verify_submit;
//	
//	private EditText et_member_verify_number,et_member_verify_pwd;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		getActionBar().hide();
//		setContentView(R.layout.activity_member_verify);
//		initView();
//	}
//	
//	private void initView(){
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("会员验证");
//		menb = (LinearLayout)findViewById(R.id.menb);
//		menb.setBackgroundColor(Color.WHITE);
//		et_member_verify_number = (EditText) findViewById(R.id.et_member_verify_number);
//		et_member_verify_pwd    = (EditText) findViewById(R.id.et_member_verify_pwd);
//		
//		tv_member_verify_submit = (TextView) findViewById(R.id.tv_member_verify_submit);
//		tv_member_verify_submit.setOnClickListener(this);
//	}
//	
//	@Override
//	public void onClick(View arg0) {
//		// TODO Auto-generated method stub
//		super.onClick(arg0);
//		Intent intent = null;
//		switch (arg0.getId()) {
//		case R.id.img_back:// 返回按钮
//			finish();
//			break;
//		case R.id.tv_member_verify_submit://提交
//			verifyMemberNumber();
//			break;
//		}
//	}
//	
//	private void verifyMemberNumber(){
//		final String number = et_member_verify_number.getText().toString().trim();
//		final String pwd    = et_member_verify_pwd.getText().toString().trim();
//		if(TextUtils.isEmpty(number)){
//			ToastUtil.showLongText(context, "会员卡号不能为空");
//			return ;
//		}
//		if(TextUtils.isEmpty(number)){
//			ToastUtil.showLongText(context, "密码不能为空");
//			return ;
//		}
//		new SAsyncTask<Void, Void, HashMap<String, Object>>(this, R.string.wait){
//
//			@Override
//			protected HashMap<String, Object> doInBackground(FragmentActivity context,
//					Void... params) throws Exception {
//				return ComModel.memberVerify(context, number,pwd);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, Object> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if(null == e){
//				if (result != null ) {
//					if(result.get("status").equals("1")){
//					ToastUtil.showShortText(MemberVerifyActivity.this, result.get("message").toString());
//					UserInfo user = YCache.getCacheUserSafe(context);
//					user.setIs_member(result.get("is_member").toString());
//					YCache.setCacheUser(context, user);
//					Intent intent = new Intent(context,MembersGoodsListActivity.class);
//					startActivity(intent);
//					finish();
//					}else{
//						ToastUtil.showShortText(MemberVerifyActivity.this, result.get("message").toString());
//					}
//				}
//				}
//			}
//			
//		}.execute();
//	}
//	
//	
//}
