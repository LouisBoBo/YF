package com.yssj.ui.fragment.mywallet;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.logins.RegisterFragment;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.GetUserABClass;
import com.yssj.utils.IDCardUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.widget.LocationSelectorDialogBuilder;
import com.yssj.widget.LocationSelectorDialogBuilder.OnSaveLocationLister;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddMyBankCardFragment extends BaseFragment implements OnClickListener,OnSaveLocationLister {

	private TextView tvTitle_base,et_province;
	private LinearLayout img_back;
	
	private EditText et_bank_num,et_person_name,et__phone_num/*,et_email,et_branch_bank_name*/;
	private Button btn_next_step;
	private TextView tv_scan_support_bank,tv_identify_info;
	private LocationSelectorDialogBuilder locationBuilder;
//	private String province,city,province_id,city_id;
	
	
	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_mywallet_add_mycard, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("身份验证");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		et_bank_num = (EditText) view.findViewById(R.id.et_bank_num);
		et_person_name = (EditText) view.findViewById(R.id.et_person_name);
//		et_identity = (EditText) view.findViewById(R.id.et_identity);
		et__phone_num = (EditText) view.findViewById(R.id.et__phone_num);
		et_province =  (TextView) view.findViewById(R.id.et_province);
		et_province.setOnClickListener(this);
	//	et_email =  (EditText) view.findViewById(R.id.et_email);
	//	et_branch_bank_name =  (EditText) view.findViewById(R.id.et_branch_bank_name);
		
		btn_next_step = (Button) view.findViewById(R.id.btn_next_step);	
		btn_next_step.setOnClickListener(this);
		
		tv_scan_support_bank = (TextView) view.findViewById(R.id.tv_scan_support_bank);
		tv_scan_support_bank.setOnClickListener(this);
		tv_identify_info = (TextView) view.findViewById(R.id.tv_identify_info);
		String text = "为了您的账户信息安全，身份证号必须正确填写，平台将与银行预留信息进行比对，错误将直接导致提现失败，身份信息严格加密处理，仅用于银行验证。";
		SpannableString spanText = new SpannableString(text);
		spanText.setSpan(new ForegroundColorSpan(
				Color.parseColor("#ff3f8b")), 11, 21,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv_identify_info.setText(spanText);
		return view;
	}

	@Override
	public void initData() {
		
	}
	
	private void addBankNum(){
//		LogYiFu.e("shichiejif^^^^^^^^",city+"*****"+province);
		final String bank_no = et_bank_num.getText().toString().trim();
//		final String identity = et_identity.getText().toString().trim();
		final String name = et_person_name.getText().toString().trim();
		final String phone = et__phone_num.getText().toString().trim();
	//	final String email   = et_email.getText().toString().trim();
	//	final String branch_bank_name   = et_branch_bank_name.getText().toString().trim();
		
		
		if(TextUtils.isEmpty(bank_no)){
			et_bank_num.requestFocus();
			ToastUtil.showLongText(context, "银行卡号不能为空");
			return ;
		} 
//		if(TextUtils.isEmpty(identity)){
//			et_identity.requestFocus();
//			ToastUtil.showLongText(context, "身份证号不能为空");
//			return ;
//		}else{
////			String info = IDCardUtil.IDCardValidate(identity);
////			if(!info.equals("YES")){
////				ToastUtil.showLongText(context, "身份证号有误");
////				return;
////			}
//		}
		if(TextUtils.isEmpty(name)){
			et_person_name.requestFocus();
			ToastUtil.showLongText(context, "持卡人姓名不能为空");
			return ;
		} 
		
		if(RegisterFragment.getWordCount(name)>8){
			et_person_name.requestFocus();
			ToastUtil.showLongText(context, "持卡人姓名不能超过8个字符");
			return;
		}
		
		if(TextUtils.isEmpty(phone)){
			et__phone_num.requestFocus();
			ToastUtil.showLongText(context, "电话号码不能为空");
			return ;
		}else{
			if (!StringUtils.isPhoneNumberValid(phone)) {
				ToastUtil.showLongText(context,"请输入正确的银行预留手机号!");
				return;
			}
		}
//		if(TextUtils.isEmpty(province)||TextUtils.isEmpty(city)||province.equals("")||city.equals("")){
//			ToastUtil.showShortText(context, "请完善收款人省市");
//			return;
//		}
//		if(!TextUtils.isEmpty(email)){
//			if (!StringUtils.isEmail(email)) {
//				ToastUtil.showLongText(context,"你的邮箱格式不正确，请重新输入");
//				return;
//			}
//		}
	/*	if(TextUtils.isEmpty(branch_bank_name)){
			et_branch_bank_name.requestFocus();
			ToastUtil.showLongText(context, "支行名称不能为空");
			return ;
		}*/
//		LogYiFu.e("shichiejif^^^^^^^^",city+"*****"+province);
		
		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){
			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.addMyBankCard(context, bank_no, "", name, phone, /*branch_bank_name,*/ "",
						"","", ""/*, email*/);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {
				super.onPostExecute(context, result, e);
				if(null == e){
				if(result != null && "1".equals(result.getStatus())){
					GetUserABClass.getUserABclass(context);//绑定身份证后重新获取AB类
					Bundle bundle2 = getArguments();
					String flag = "";
					if(bundle2 != null){
						flag = bundle2.getString("flag");
					}
					Fragment mFragment;
					if("choiceMyBankCardFragment".equals(flag)){
						mFragment = new ChoiceMyBankCardFragment();
						bundle2.putString("money", bundle2.getString("money"));
						mFragment.setArguments(bundle2);
						getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
					}else{
						mFragment = new MyBankCardFragment();
						getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
					}
					
				}else if(result != null && !"1".equals(result.getStatus())){
					ToastUtil.showLongText(context, result.getMessage());
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
			Bundle bundle2 = getArguments();
			String flag = "" ;
			if(bundle2 != null){
				flag = bundle2.getString("flag");
			}
			if("choiceMyBankCardFragment".equals(flag)){
				mFragment = new ChoiceMyBankCardFragment();
				bundle2.putString("money", bundle2.getString("money"));
				mFragment.setArguments(bundle2);
				getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			}else{
				mFragment = new MyBankCardFragment();
				getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			}
			break;
			
		case R.id.tv_scan_support_bank:	// 查看所支持的银行
			
			Intent intent= new Intent(context, ViewSupportBankActivity.class);
			
			startActivity(intent);
//			mFragment = new ViewSupportBankActivity();
//			Bundle bundle = new Bundle();
//			bundle.putString("flag", "addMyBankCardFragment");
//			mFragment.setArguments(bundle);
//			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			break;
		case R.id.btn_next_step:	// 下一步
			addBankNum();
			break;
		case R.id.et_province:	// 下一步
//			if (locationBuilder == null) {
				locationBuilder = LocationSelectorDialogBuilder
						.getInstance(getActivity());
				locationBuilder.setOnSaveLocationLister(this);
//			}
			locationBuilder.show();
			//
			break;	
		}
	}

	@Override
	public void onSaveLocation(String location, String provinceId, String cityId) {
		// TODO Auto-generated method stub
		et_province.setText(location);
		String[] str = location.trim().split(" ");
//		this.province_id = provinceId;
//		this.city_id  = cityId;
//		this.province =str[0];
//		this.city     =str[1];
	}


}
