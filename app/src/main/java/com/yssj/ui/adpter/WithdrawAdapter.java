package com.yssj.ui.adpter;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.time.DateFormatUtils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.activity.infos.MyWalletCommonFragmentActivity;
import com.yssj.ui.base.BaseMainAdapter;
import com.yssj.utils.DateUtil;


public class WithdrawAdapter extends BaseMainAdapter {
	
	
	public WithdrawAdapter(Context context ) {
		super(context);

		
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	
		ViewHolder holder;
		if (null == convertView) {
			convertView = View.inflate(context,R.layout.withdraw_list_item, null);
			
			holder = new ViewHolder();
			
			holder.tv_bank_name = (TextView) convertView.findViewById(R.id.tv_bank_name);
			holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
			holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
			holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final HashMap<String, Object> map = (HashMap<String, Object>) getItem(position);
		if(map != null && !map.isEmpty()){
			holder.tv_bank_name.setText(map.get("collect_bank_name").toString() + "**" + map.get("collect_bank_code").toString());
			holder.tv_money.setText("-" + Float.parseFloat(map.get("money").toString()));
//			holder.tv_money.setText(new DecimalFormat("#0.00").format(Double.parseDouble(map.get("money").toString())) +"元");
//			holder.tv_date.setText(DateFormatUtils.format(new Date(Long.parseLong(map.get("add_date").toString())), "yyyy-MM-dd HH:mm:ss"));
            holder.tv_date.setText(map.get("add_date")+"");


            String checkCode = map.get("check").toString();
			if("0".equals(checkCode)){
				holder.tv_status.setText("待审核");
			}else if("1".equals(checkCode)){
				holder.tv_status.setText("通过");
			}else if("2".equals(checkCode)){
				holder.tv_status.setText("不通过");
			}else if("3".equals(checkCode)){
				holder.tv_status.setText("提现成功");
			}else if("4".equals(checkCode)){
				holder.tv_status.setText("审核已通过");
			}else if("6".equals(checkCode)){
				holder.tv_status.setText("提现已发起");
			}else if("7".equals(checkCode)){
				holder.tv_status.setText("提现已提交至开户行");
			}else if("8".equals(checkCode)){
				holder.tv_status.setText("开户行发放中，预计1个工作日内到账");
			}else if("9".equals(checkCode)){
				holder.tv_status.setText("开户行发放中，预计1个工作日内到账");
			}else if("10".equals(checkCode)){
				holder.tv_status.setText("提现成功");
			}else if("11".equals(checkCode)){
				holder.tv_status.setText("转账失败");
			}else if("12".equals(checkCode)){
				holder.tv_status.setText("已重新申请");
			}else{
				holder.tv_status.setText("未知的状态");
			}
		
		}
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, MyWalletCommonFragmentActivity.class);
				intent.putExtra("item", (Serializable)map);
				intent.putExtra("flag", "withDrawaDetailsFragment");
				context.startActivity(intent);
			}
		});
		
		return convertView;
	}
	
	


	class ViewHolder {
		TextView tv_bank_name, tv_money,tv_date,tv_status;
	}

}
