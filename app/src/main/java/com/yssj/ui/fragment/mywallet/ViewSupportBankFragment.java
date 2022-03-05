package com.yssj.ui.fragment.mywallet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BaseFragment;

public class ViewSupportBankFragment extends BaseFragment implements OnClickListener {

	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private ListView lv_support_bank;
	
	private int bankIcons[] = {R.drawable.zg_bank_icon,R.drawable.ny_bank_icon,R.drawable.gs_bank_icon,R.drawable.js_bank_icon,R.drawable.jt_bank_icon,R.drawable.yz_bank_icon,
			R.drawable.zs_bank_icon,R.drawable.zx_bank_icon,R.drawable.pf_bank_icon,R.drawable.gd_bank_icon};
	
	private String[] bankNames = {"中国银行","农业银行","工商银行","建设银行","交通银行","邮政银行","招商银行","中信银行","浦发银行","光大银行"};
	
	private ViewSupportBankAdapter adapter;
	
	
	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_mywallet_view_support_bank, null);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("银行列表");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		lv_support_bank = (ListView) view.findViewById(R.id.lv_support_bank);
		
		
		
		return view;
	}

	@Override
	public void initData() {
		
		adapter = new ViewSupportBankAdapter();
		lv_support_bank.setAdapter(adapter);
	}
	
	private class ViewSupportBankAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return bankNames.length;
		}

		@Override
		public Object getItem(int position) {
			return bankNames[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder ;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.mywallet_bank_list_item, null);
				holder.iv_bank_icon = (ImageView) convertView.findViewById(R.id.iv_bank_icon);
				holder.tv_bank_name = (TextView) convertView.findViewById(R.id.tv_bank_name);
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.iv_bank_icon.setImageResource(bankIcons[position]);
			holder.tv_bank_name.setText(bankNames[position]);
			
			return convertView;
		}
	}
	
	class ViewHolder{
		ImageView iv_bank_icon;
		TextView tv_bank_name;
	}
	

	@Override
	public void onClick(View v) {
		Fragment mFragment;
		switch (v.getId()) {
		case R.id.img_back:
			Bundle bundle = getArguments();
			String flag = bundle.getString("flag");
			if("myBankCardFragment".equals(flag)){
				mFragment = new MyBankCardFragment();
				getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			}else{
				mFragment = new AddMyBankCardFragment();
				getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			}
			break;
			
		}
	}


}
