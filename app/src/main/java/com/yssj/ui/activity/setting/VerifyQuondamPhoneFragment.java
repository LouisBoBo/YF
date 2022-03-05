package com.yssj.ui.activity.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.huanxin.PublicUtil;
import com.yssj.model.ComModel;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.fragment.setting.BindPhoneFragment;
import com.yssj.ui.fragment.setting.UpdateBindNumCodeFragment;
import com.yssj.utils.CenterToast;
import com.yssj.utils.ToastUtil;

import org.w3c.dom.Text;

public class VerifyQuondamPhoneFragment extends BaseFragment implements OnClickListener{

	private TextView tvTitle_base, tv_current_phone, tv_phone_num, tv_send;
	private LinearLayout img_back;

	private EditText ev_quondam_phone_num;
	private Button btn_send_code;

	private String phone = "";
	private String code;
	private EditText et_auto;
	private ImageView ck_auto;
	
	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_verify_quondam_phone, null);
		view.setBackgroundColor(Color.WHITE);
		phone=getArguments().getString("phone");
		tvTitle_base = (TextView) view. findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("验证原手机号");
		img_back = (LinearLayout)view. findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		tv_current_phone = (TextView)view. findViewById(R.id.tv_current_phone);
		tv_current_phone.setText("当前手机号："
				+ phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
		tv_phone_num = (TextView) view.findViewById(R.id.tv_phone_num);
		tv_phone_num.setText(phone.replaceAll("(\\d{3})\\d{4}(\\d{4})",
				"$1****$2"));

		ev_quondam_phone_num = (EditText)view. findViewById(R.id.ev_quondam_phone_num);
		btn_send_code = (Button) view.findViewById(R.id.btn_send_code);
		btn_send_code.setOnClickListener(this);

		tv_send = (TextView) view.findViewById(R.id.tv_send);
		tv_send.setOnClickListener(this);

		et_auto = (EditText) view.findViewById(R.id.et_auto);
		ck_auto = (ImageView) view.findViewById(R.id.ck_auto);
		ck_auto.setOnClickListener(this);
		if(!TextUtils.isEmpty(phone)){
			PublicUtil.setVCode(getActivity(),ck_auto,phone);
		}
		return view;
	}


	@Override
	public void onClick(View v) {
		Fragment mFragment;
		switch (v.getId()) {
		case R.id.img_back:
			InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(ev_quondam_phone_num.getWindowToken(), 0);
			mFragment = new BindPhoneFragment();
			Bundle bundle = new Bundle();
			bundle.putString("phone", phone);
			mFragment.setArguments(bundle);
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			break;
		case R.id.btn_send_code: // 保存
			submitCode();
			break;
		case R.id.ck_auto: //重新获取图片验证码
			if(!TextUtils.isEmpty(phone)){
				PublicUtil.setVCode(getActivity(),ck_auto,phone);
			}
			break;
		case R.id.tv_send:
			if(TextUtils.isEmpty(et_auto.getText().toString().trim())){
				CenterToast.centerToast(context, "请先输入图形验证码");
				return;
			}
			if(TextUtils.isEmpty(phone)){
				return;
			}
			String vCode = et_auto.getText().toString().trim();
//			PublicUtil.setVCode(getActivity(),ck_auto,phone);//发送手机验证码之后 重新从后台获取图形验证码并显示
//			et_auto.setText("");

			tv_send.setEnabled(false);
			getCode(vCode);
		}
	}

	private void submitCode() {
		code = ev_quondam_phone_num.getText().toString();
		if (TextUtils.isEmpty(code)) {
			ToastUtil.showLongText(context, "验证码不能为空");
			return;
		}

		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity)context, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.checkOldCode(context, code);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {
				if (null == e) {
					if (result != null && "1".equals(result.getStatus())) {
						Fragment mFragment = new ChangePhoneBindFragment();
						Bundle bundle = new Bundle();
						bundle.putString("phone", phone);
						mFragment.setArguments(bundle);
						getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
					} else {
						ToastUtil.showShortText(
								context, "糟糕，出错了~~~");
					}
				}
			}

		}.execute();
	}

	private void getCode(final String vCode) {

		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context,
				R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {

				return ComModel.sendOldPhoneVerifyCode(context,vCode);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e) {
					if (result != null && "1".equals(result.getStatus())) {
						ev_quondam_phone_num.setFocusable(true);
						ev_quondam_phone_num.requestFocus();
						new CountDownTimer(Long.parseLong(getResources()
								.getString(R.string.identify_code)), 1000) {

							@Override
							public void onTick(long millisUntilFinished) {
								tv_send.setText(String
										.valueOf(millisUntilFinished / 1000)
										+ "秒重发");
								tv_send.setBackgroundResource(R.drawable.bg_square_bind_btn_default);
								
							}

							@Override
							public void onFinish() {
								PublicUtil.setVCode(getActivity(),ck_auto,phone);//发送手机验证码之后 重新从后台获取图形验证码并显示
								et_auto.getText().clear();
								tv_send.setText("重新发送");
								tv_send.setEnabled(true);
								tv_send.setBackgroundResource(R.drawable.bg_square_choice_btn_checked);
							}
						}.start();

					} else {
						PublicUtil.setVCode(getActivity(),ck_auto,phone);//发送手机验证码之后 重新从后台获取图形验证码并显示
						et_auto.getText().clear();

						tv_send.setText("重新发送");
						tv_send.setEnabled(true);
						tv_send.setBackgroundResource(R.drawable.bg_square_choice_btn_checked);
					}
					ToastUtil.showShortText(context, result.getMessage());
				}else{
					tv_send.setEnabled(true);
				}
			}

		}.execute();
	}


	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}
}
