package com.yssj.ui.fragment.mywallet;

import java.util.List;

import android.content.Intent;
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
import com.yssj.entity.MyBankCard;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.logins.RegisterFragment;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.IDCardUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.widget.LocationSelectorDialogBuilder;
import com.yssj.widget.LocationSelectorDialogBuilder.OnSaveLocationLister;

public class ModifyMyBankCardFragment extends BaseFragment implements OnClickListener,OnSaveLocationLister {

	private TextView tvTitle_base,et_province,et_bank_num;
	private LinearLayout img_back;
	
	private EditText et_person_name,et_identity,et__phone_num,et_email,et_branch_bank_name;
	private Button btn_next_step;
	private TextView tv_scan_support_bank;
	private LocationSelectorDialogBuilder locationBuilder;
	private String province,city,province_id,city_id;
	private String bank_id;
	private MyBankCard bankCard;
	public ModifyMyBankCardFragment(){
	}
	public ModifyMyBankCardFragment(MyBankCard bankCard){
		this.bankCard = bankCard;
	}
	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_mywallet_modify_mycard, null);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("完善银行卡信息");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		et_bank_num =  (TextView) view.findViewById(R.id.et_bank_num);
		et_bank_num.setText("***"+bankCard.getBank_no());
		et_person_name = (EditText) view.findViewById(R.id.et_person_name);
		et_person_name.setText(bankCard.getName());
		et_identity = (EditText) view.findViewById(R.id.et_identity);
		et_identity.setText(bankCard.getIdentity());
		et__phone_num = (EditText) view.findViewById(R.id.et__phone_num);
		et__phone_num.setText(bankCard.getPhone());
		et_province =  (TextView) view.findViewById(R.id.et_province);
		et_province.setOnClickListener(this);
		et_email =  (EditText) view.findViewById(R.id.et_email);
	//	et_branch_bank_name =  (EditText) view.findViewById(R.id.et_branch_bank_name);
		
		btn_next_step = (Button) view.findViewById(R.id.btn_next_step);	
		btn_next_step.setOnClickListener(this);
		
		tv_scan_support_bank = (TextView) view.findViewById(R.id.tv_scan_support_bank);
		tv_scan_support_bank.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void initData() {
		
	}
	
	private void addBankNum(){
		LogYiFu.e("shichiejif^^^^^^^^",city+"*****"+province);
		final String identity = et_identity.getText().toString().trim();
		final String name = et_person_name.getText().toString().trim();
		final String phone = et__phone_num.getText().toString().trim();
		final String email   = et_email.getText().toString().trim();
		final String branch_bank_name   = et_branch_bank_name.getText().toString().trim();
		
		
//		if(TextUtils.isEmpty(bank_no)){
//			et_bank_num.requestFocus();
//			ToastUtil.showLongText(context, "银行卡号不能为空");
//			return ;
//		} 
		if(TextUtils.isEmpty(identity)){
			et_identity.requestFocus();
			ToastUtil.showLongText(context, "身份证号不能为空");
			return ;
		}else{
			String info = IDCardUtil.IDCardValidate(identity);
			if(!info.equals("YES")){
				ToastUtil.showLongText(context, "身份证号有误");
				return;
			}
		} 
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
		if(TextUtils.isEmpty(province)||TextUtils.isEmpty(city)){
			province    ="广东省";
			city        ="深圳市";
			province_id ="19";
			city_id     ="291";
		}
		if(!TextUtils.isEmpty(email)){
			if (!StringUtils.isEmail(email)) {
				ToastUtil.showLongText(context,"你的邮箱格式不正确，请重新输入");
				return;
			}
		}
		if(TextUtils.isEmpty(branch_bank_name)){
			et_branch_bank_name.requestFocus();
			ToastUtil.showLongText(context, "支行名称不能为空");
			return ;
		}
		LogYiFu.e("shichiejif^^^^^^^^",city+"*****"+province);
		
		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){
			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.updateMyBankCard(context, "", identity, name, phone, branch_bank_name, province_id,
						province,city, city_id, email,bankCard.getId());
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
//			mFragment = new ViewSupportBankActivity();
//			Bundle bundle = new Bundle();
//			bundle.putString("flag", "addMyBankCardFragment");
//			mFragment.setArguments(bundle);
//			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();

			Intent intent= new Intent(context, ViewSupportBankActivity.class);
			
			startActivity(intent);
			
			break;
		case R.id.btn_next_step:	
			addBankNum();
			break;
		case R.id.et_province:	
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
		this.province_id = provinceId;
		this.city_id  = cityId;
		this.province =str[0];
		this.city     =str[1]; 
	}


}
