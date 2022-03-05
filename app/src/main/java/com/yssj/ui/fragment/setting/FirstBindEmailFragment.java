package com.yssj.ui.fragment.setting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.QueryEmailInfo;
import com.yssj.entity.QueryPhoneInfo;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.infos.MyPayPassActivity;
import com.yssj.ui.activity.infos.SetMyPayPassActivity;
import com.yssj.ui.activity.setting.AccountSecureActivity;
import com.yssj.ui.activity.setting.BindEmailActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ToastUtil;

public class FirstBindEmailFragment extends BaseFragment implements OnClickListener {

	private TextView tvTitle_base;
	private LinearLayout img_back;

	private EditText et_email_num;
	private Button btn_send_email;
	private String email = "";
	private String exe;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_setting_bind_email, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("绑定邮箱");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);

		et_email_num = (EditText) view.findViewById(R.id.et_email);

		btn_send_email = (Button) view.findViewById(R.id.btn_send_email);
		btn_send_email.setOnClickListener(this);
		exe = getActivity().getIntent().getStringExtra("wallet");
		return view;
	}

	@Override
	public void initData() {

	}

	private void bindEmail() {
		email = et_email_num.getText().toString();

		if (TextUtils.isEmpty(email)) {
			ToastUtil.showLongText(context, "email不能为空");
			return;
		}

		if (!email.matches("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")) {
			ToastUtil.showLongText(context, "email格式有误");
			return;
		}

		// new SAsyncTask<Void, Void, ReturnInfo>(getActivity(), R.string.wait){
		//
		// @Override
		// protected ReturnInfo doInBackground(FragmentActivity context,
		// Void... params) throws Exception {
		// return ComModel.get_EmailActivate_Code(context, email);
		// }
		//
		// @Override
		// protected void onPostExecute(FragmentActivity context,
		// ReturnInfo result) {
		// if (result != null && "1".equals(result.getStatus())) {
		// ToastUtil.showShortText(getActivity(), result.getMessage());
		// getActivity().finish();
		// }else{
		// ToastUtil.showShortText(getActivity(), "糟糕，出错了~~~");
		// }
		// }
		//
		// }.execute();
		new SAsyncTask<Void, Void, QueryEmailInfo>((FragmentActivity) context, R.string.wait) {

			@Override
			protected QueryEmailInfo doInBackground(FragmentActivity context, Void... params) throws Exception {

				return ComModel.checkEmailIsBind(context, email);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, QueryEmailInfo result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e) {
					if (result != null && "1".equals(result.getStatus()) && result.isBool() == false) {
						// 往下一步跳转
						Fragment mFragment = new FirstBindEmailIdentityCodeFragment();

						if (null != mFragment && getActivity() != null) {
							Bundle bundle = new Bundle();
							LogYiFu.e("tag", email.toString());
							bundle.putString("email", email);
							mFragment.setArguments(bundle);
							getActivity().getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment).commit();
						}

					} else if (result != null && "1".equals(result.getStatus()) && result.isBool() == true) {

						ToastUtil.showLongText(context, "该邮箱已绑定其他账号，请更换其他邮箱");

					} else {
						ToastUtil.showLongText(context, result.getMessage());
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

			getActivity().finish();
			if ("account".equals(exe)) {
				Intent intent = new Intent(getActivity(), AccountSecureActivity.class);
				startActivity(intent);

				getActivity().overridePendingTransition(R.anim.activity_from_right_publish,
						R.anim.activity_from_right_publish_close);
			}
			break;

		case R.id.btn_send_email:
			bindEmail();
			// mFragment = new SecureIdentifyFragment();
			// getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,
			// mFragment).commit();

			break;

		}
	}

}
