package com.yssj.ui.fragment.setting;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.QueryEmailInfo;
import com.yssj.entity.QueryPhoneInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.setting.AccountSecureActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.ToastUtil;

public class BindEmailFragment extends BaseFragment implements OnClickListener {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private RelativeLayout rel_email_num,rel_update_emailNum;
	private TextView tv_email_num,tv_update_emailNum;
	
	private String exe;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_setting_secure_bindemail, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("绑定邮箱");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		rel_email_num = (RelativeLayout) view.findViewById(R.id.rel_update_emailNum);
		
		rel_update_emailNum = (RelativeLayout) view.findViewById(R.id.rel_update_emailNum); // 更换邮箱账号
		rel_update_emailNum.setOnClickListener(this);
		
		tv_email_num = (TextView) view.findViewById(R.id.tv_email_num);
		tv_update_emailNum = (TextView) view.findViewById(R.id.tv_update_emailNum);
		
		exe = getActivity().getIntent().getStringExtra("wallet");
		return view;
	}

	@Override
	public void initData() {
		
	/*	这么接收不知道为啥有时会报空指针异常
	 * Bundle bundle = getArguments();
		String phoneNum = bundle.getString("phoneNum");
		if(!TextUtils.isEmpty(phoneNum)){
			tv_phone_num.setText(phoneNum.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
			
		}*/
		bindEmail();
	}
	
	
	// 查询绑定手机信息
		private void bindEmail(){
			
			new SAsyncTask<Void, Void, QueryEmailInfo>((FragmentActivity)context){

				@Override
				protected QueryEmailInfo doInBackground(FragmentActivity context,
						Void... params) throws Exception {
					
					return ComModel.bindEmail(context);
				}

				@Override
				protected boolean isHandleException() {
					return true;
				}
				
				@Override
				protected void onPostExecute(FragmentActivity context,
						QueryEmailInfo result, Exception e) {
					super.onPostExecute(context, result, e);
					if(null == e){
					if(result != null && "1".equals(result.getStatus())){
						tv_email_num.setText(xx(result.getEmail())
//								.replaceAll("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", "$1****$2")
								);
					}else{
						ToastUtil.showLongText(context, "糟糕，出错了~~~");
					}
					}
				}
				
			}.execute();
		}

	@Override
	public void onClick(View v) {
		Fragment mFragment;
		switch (v.getId()) {
		case R.id.img_back:
			if("account".equals(exe)){
			getActivity().finish();
			Intent intent = new Intent(getActivity(), AccountSecureActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.activity_from_right_publish,R.anim.activity_from_right_publish_close);
			}else{
				getActivity().finish();	
			}
			break;
			
		case R.id.rel_update_emailNum:	// 更换绑定邮箱
			
			mFragment = new UpdateBindEmailFragment();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			
			break;

		}
	}
	public String xx(String ss) {
		String s = "";
		s = ss.substring(0, 3) + "****"
				+ ss.substring(ss.lastIndexOf(".") - 3, ss.length());
		return s;
	}


}
