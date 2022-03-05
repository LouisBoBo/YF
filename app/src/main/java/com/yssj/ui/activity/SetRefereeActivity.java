package com.yssj.ui.activity;

import android.content.Intent;
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
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.network.YException;

import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

public class SetRefereeActivity extends BasicActivity{
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private EditText et_referee;
	private Button btn_set_referee,btn_next_step_setreferee;
	
	private String referee = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aBar.hide();
		setContentView(R.layout.activity_setting_referee);
		initView();
	}
	
	private void initView() {
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("推荐人");
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		et_referee = (EditText) findViewById(R.id.et_referee);
		
		btn_set_referee = (Button) findViewById(R.id.btn_set_referee);
		btn_set_referee.setOnClickListener(this);
		btn_next_step_setreferee = (Button) findViewById(R.id.btn_next_step_setreferee);
		btn_next_step_setreferee.setOnClickListener(this);
		
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.btn_set_referee:	// 下一步
			setReferee();
			break;
		case R.id.btn_next_step_setreferee:	// 进入喜好选择
			Intent intent = new Intent(getApplicationContext(),
					MineLikeActivity.class);
			startActivity(intent);
			break;
		}
	}
	
	private void setReferee(){
		referee = et_referee.getText().toString();
		if(TextUtils.isEmpty(referee)){
			ToastUtil.showLongText(this, "推荐人不能为空");
			return ;
		}else if(!StringUtils.isRefereeValid(referee)){
			ToastUtil.showLongText(this, "请填写正确的推荐人id");
			return ;
		}
		
		new SAsyncTask<Void, Void, ReturnInfo>(this, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.set_Referee(context, referee);
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result,Exception e) {
				if(e!=null){
				if (e instanceof YException
						&& ((YException) e).getErrorCode() == 101) {
					
					Intent intent = new Intent(context,
							MineLikeActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("userinfo", YCache.getCacheUserSafe(context));
					intent.putExtras(bundle);
					startActivity(intent);
//				}
//				if (result != null && "101".equals(result.getStatus())) {
//					Intent intent = new Intent(getApplicationContext(),
//							MineLikeActivity.class);
//					startActivity(intent);
//					ToastUtil.showShortText(SetRefereeActivity.this, result.getMessage());
//					finish();
				}else{
					ToastUtil.showShortText(SetRefereeActivity.this, "糟糕，出错了~~~");
				}
				}
			}
			@Override
			protected boolean isHandleException() {
				return true;
			};
			
		}.execute();
	}

}
