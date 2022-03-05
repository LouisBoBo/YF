package com.yssj.ui.activity.setting;

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
import com.yssj.model.ComModel;
import com.yssj.ui.activity.infos.MyInfoActivity;
import com.yssj.ui.activity.logins.RegisterFragment;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;

public class UpdateNickNameActivity extends BasicActivity{
	
	private TextView tvTitle_base;
	private LinearLayout img_back,nichen;
	
	private EditText et_nickname;
	private Button btn_save;
	
	private String nickName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		nickName = getIntent().getStringExtra("nickName");
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.activity_setting_update_nickname);
		nichen= (LinearLayout) findViewById(R.id.nichen);
		nichen.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("昵称");
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		et_nickname = (EditText) findViewById(R.id.et_nickname);
		et_nickname.setText(nickName);
		
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_save.setOnClickListener(this);
		
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.btn_save:	// 保存
			updateNickName();
			
			break;
		}
	}
	
	private void updateNickName(){
		nickName = et_nickname.getText().toString();
		if(TextUtils.isEmpty(nickName)){
			ToastUtil.showLongText(this, "昵称不能为空");
			return ;
		}
		
		if(RegisterFragment.getWordCount(nickName) > 8 || RegisterFragment.getWordCount(nickName) < 2){
			ToastUtil.showShortText(this, "请输入2-8个字符的昵称");
			return;
		}
		new SAsyncTask<Void, Void, UserInfo>(this, R.string.wait){

			@Override
			protected UserInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.updateNickName(context, nickName);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					UserInfo result, Exception e) {
				super.onPostExecute(context, result, e);
				if(null == e){
				if (result != null ) {
					ToastUtil.showShortText(UpdateNickNameActivity.this, "操作成功");
//					Intent intent = new Intent(UpdateNickNameActivity.this, MyInfoActivity.class);
//					startActivity(intent);
					Intent intent = getIntent();
					intent.putExtra("nickName", nickName);
					setResult(10001, intent);
					finish();
				}
				}
			}
			
		}.execute();
	}
	
}
