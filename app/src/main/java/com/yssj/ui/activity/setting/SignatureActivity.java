package com.yssj.ui.activity.setting;

import java.util.HashMap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.logins.RegisterFragment;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DeviceUtils;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

public class SignatureActivity extends BasicActivity {
	
	private LinearLayout img_back,qianming;
	private Button btn_right;
	private EditText comment_edit_signature;
	private String submitSignatureContent;
	private String versionName;
	private HashMap<String, String> mapSignature = new HashMap<String, String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.signature_activity);
		
		TextView tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("个性签名");
		tvTitle_base.setTextSize(18.0f);
		qianming = (LinearLayout) findViewById(R.id.qianming);
		qianming.setBackgroundColor(Color.WHITE);
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		btn_right=(Button) findViewById(R.id.btn_right);
		btn_right.setText("完成");
		btn_right.setTextColor(Color.parseColor("#222222"));
		btn_right.setTextSize(16.0f);
		btn_right.setPadding(15, 2, 0, 2);
		btn_right.setVisibility(View.VISIBLE);
		btn_right.setOnClickListener(this);
		
		comment_edit_signature = (EditText) findViewById(R.id.comment_edit_signature);
		String sign=YCache.getCacheUser(this).getUserSign();
		comment_edit_signature.setText(TextUtils.isEmpty(sign)||sign.equals("null")?"":sign);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		switch (arg0.getId()) {
		
		case R.id.img_back:
			finish();
			break;
		case R.id.btn_right:
			submit(arg0);
			break;
		}
	}
	
	private void submit(View v) {
		submitSignatureContent = comment_edit_signature.getText().toString().trim();
		if(TextUtils.isEmpty(submitSignatureContent)){
			ToastUtil.showShortText(SignatureActivity.this, "请输入签名");
			return;
		}
//		if(StringUtils.containsEmoji(submitSignatureContent)){
//			ToastUtil.showShortText(SignatureActivity.this, "不能输入特殊字符");
//			return;
//		}	
		if(RegisterFragment.getWordCount(submitSignatureContent) > 50){
			ToastUtil.showShortText(SignatureActivity.this, "请输入不超过50个字符");
			return;
		}
		
		versionName = DeviceUtils.getVersionName(this);
		mapSignature.put("content",submitSignatureContent);
		mapSignature.put("version_no", versionName);
		new SAsyncTask<Void, Void, UserInfo>(SignatureActivity.this, v,
				R.string.wait) {

			@Override
			protected UserInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.updateSign(SignatureActivity.this, submitSignatureContent);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					UserInfo result, Exception e) {
				if(null == e){
				if (result != null ) {
					ToastUtil.showShortText(SignatureActivity.this, "操作成功");
//					Intent intent = new Intent(UpdateNickNameActivity.this, MyInfoActivity.class);
//					startActivity(intent);
//					Intent intent = getIntent();
//					intent.putExtra("sign", submitSignatureContent);
//					setResult(10005, intent);
					finish();
				}
				}
			}

		}.execute((Void[]) null);
	}

}
