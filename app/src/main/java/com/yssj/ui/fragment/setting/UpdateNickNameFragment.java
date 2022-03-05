package com.yssj.ui.fragment.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.infos.MyInfoActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.ToastUtil;

/**
 * 已废弃
 * @author Administrator
 *
 */
public class UpdateNickNameFragment extends BaseFragment implements OnClickListener {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private EditText et_nickname;
	private Button btn_save;
	
	private String nickName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		nickName = bundle.getString("nickName");
	}
	
	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_setting_update_nickname, null);
		
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("昵称");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		et_nickname = (EditText) view.findViewById(R.id.et_nickname);
		et_nickname.setText(nickName);
		
		btn_save = (Button) view.findViewById(R.id.btn_save);
		btn_save.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void initData() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			Intent intent = new Intent(context, MyInfoActivity.class);
			startActivity(intent);
			getActivity().finish();
			break;
		case R.id.btn_save:	// 保存
			updateNickName();
			
			break;
		}
	}
	
	private void updateNickName(){
		nickName = et_nickname.getText().toString();
		if(TextUtils.isEmpty(nickName)){
			ToastUtil.showLongText(context, "昵称不能为空");
			return ;
		}
		new SAsyncTask<Void, Void, UserInfo>((FragmentActivity)context, R.string.wait){

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
					ToastUtil.showShortText(context, "操作成功");
					Intent intent = new Intent(context, MyInfoActivity.class);
					startActivity(intent);
					getActivity().finish();
				}
			}
			}
		}.execute();
	}
	
	

}
