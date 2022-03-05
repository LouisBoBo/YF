package com.yssj.ui.adpter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.MyBankCard;

public class ChoiceMyBankCardListAdapter extends BaseAdapter {
	private Context context;
	private List<MyBankCard> list = new ArrayList<MyBankCard>();
	
	private int bankIcons[] = {R.drawable.zg_bank_icon,R.drawable.ny_bank_icon,R.drawable.gs_bank_icon,R.drawable.js_bank_icon,R.drawable.jt_bank_icon,R.drawable.yz_bank_icon,
			R.drawable.zs_bank_icon,R.drawable.zx_bank_icon,R.drawable.pf_bank_icon,R.drawable.gd_bank_icon};
	
	
	
	public ChoiceMyBankCardListAdapter(Context context,List<MyBankCard> list) {
		this.context = context;
		this.list = list;
	}
	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder ;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.mywallet_bank_list_item, null);
			holder.iv_bank_icon = (ImageView) convertView.findViewById(R.id.iv_bank_icon);
			holder.tv_bank_name = (TextView) convertView.findViewById(R.id.tv_bank_name);
			holder.tv_bank_num = (TextView) convertView.findViewById(R.id.tv_bank_num);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		String bank_name = list.get(position).getBank_name();
		if("中国银行".equals(bank_name)){
			holder.iv_bank_icon.setImageResource(bankIcons[0]);
		}else if("农业银行".equals(bank_name)){
			holder.iv_bank_icon.setImageResource(bankIcons[1]);
		}else if("工商银行".equals(bank_name)){
			holder.iv_bank_icon.setImageResource(bankIcons[2]);
		}else if("建设银行".equals(bank_name)){
			holder.iv_bank_icon.setImageResource(bankIcons[3]);
		}else if("交通银行".equals(bank_name)){
			holder.iv_bank_icon.setImageResource(bankIcons[4]);
		}else if("邮政银行".equals(bank_name)){
			holder.iv_bank_icon.setImageResource(bankIcons[5]);
		}else if("招商银行".equals(bank_name)){
			holder.iv_bank_icon.setImageResource(bankIcons[6]);
		}
		else if("中信银行".equals(bank_name)){
			holder.iv_bank_icon.setImageResource(bankIcons[7]);
		}else if("浦发银行".equals(bank_name)){
			holder.iv_bank_icon.setImageResource(bankIcons[8]);
		}else if("光大银行".equals(bank_name)){
			holder.iv_bank_icon.setImageResource(bankIcons[9]);
		}
		
		holder.tv_bank_name.setText(list.get(position).getBank_name());
		holder.tv_bank_num.setText("**" + list.get(position).getBank_no());
		
		
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView iv_bank_icon;
		TextView tv_bank_name;
		TextView tv_bank_num;
	}

	@Override
	public int getCount() {
		return list.size();
	}


	@Override
	public Object getItem(int position) {
		return list.get(position);
	}


	@Override
	public long getItemId(int position) {
		return position;
	}
	
	

}
