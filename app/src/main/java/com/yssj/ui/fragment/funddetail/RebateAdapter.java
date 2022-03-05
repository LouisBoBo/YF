package com.yssj.ui.fragment.funddetail;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.utils.DateUtil;

public class RebateAdapter extends BaseAdapter{
	private List<HashMap<String, Object>> listData;
	private Context context;
	
	public RebateAdapter(Context context,List<HashMap<String, Object>> listData) {
		this.context = context;
		this.listData = listData;
	}
	
	public List<HashMap<String, Object>> getData(){
		return listData;
	}
	
	@Override
	public int getCount() {
		return listData.size();
	}
	
	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.rebate_list_item, null);
			holder.status = (TextView) convertView.findViewById(R.id.status);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);
			holder.order_code = (TextView) convertView.findViewById(R.id.order_code);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.ll_rebate_bg = (LinearLayout) convertView.findViewById(R.id.ll_rebate_bg);
			holder.tv_order_user_name = (TextView) convertView.findViewById(R.id.tv_order_user_name);
			holder.tv_order_price  = (TextView) convertView.findViewById(R.id.tv_order_price);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		HashMap<String, Object> map = listData.get(position);
		String status = ((Integer)(map.get("is_free")))==1?"成功":"冻结";
		if("4".equals(map.get("status"))){//0正常,1该商品已退款,2退款中,3还未收到货,4无效
			status = "已失效";
		}else if("1".equals(map.get("status"))){
			status = "该商品已退款";
		}else if("3".equals(map.get("status"))){
			status = "还未收到货";
		}else if("2".equals(map.get("status"))){
			status = "退款中";
		}
		holder.status.setText(status);
		if(((Integer)(map.get("is_free")))==1){
			holder.ll_rebate_bg.setBackgroundResource(R.drawable.rebate_list_item_sucess_bg);
			if((map.get("money")+"").length()>3){
				holder.amount.setTextSize(25.0f);
			}else{
				holder.amount.setTextSize(30.0f);
			}
//			TODO:
//			holder.amount.setText("+" + map.get("money")+"");
			holder.amount.setText("+"+new DecimalFormat("#0.00").format(Double
					.parseDouble( map.get("money").toString())) + "");
		}else{
			holder.ll_rebate_bg.setBackgroundResource(R.drawable.rebate_list_item_fail_bg);
			if((map.get("money")+"").length()>3){
				holder.amount.setTextSize(25.0f);
			}else{
				holder.amount.setTextSize(30.0f);
			}
			holder.amount.setText("¥" + new DecimalFormat("#0.00").format(Double
					.parseDouble( map.get("money").toString()))+"");
		}
		holder.order_code.setText("订单号"+map.get("order_code"));
		holder.tv_order_user_name.setText(map.get("user_name")+"");
//		holder.tv_order_price.setText(map.get("order_price")+"");
//		TODO:
		holder.tv_order_price.setText(new DecimalFormat("#0.0").format(Double
				.parseDouble(map.get("order_price").toString())) + "");
		
		holder.date.setText(DateUtil.FormatMillisecond((Long) map.get("add_date")));
		
		
		if(map.get("type").equals("8")){ //签到任务奖励翻倍
			holder.tv_order_user_name.setText("惊喜任务签到翻倍");
			holder.order_code.setVisibility(View.GONE);
			holder.tv_order_price.setVisibility(View.GONE);
		}else if(map.get("type").equals("9")){//上级用户奖励
			holder.tv_order_user_name.setText("邀请好友——好友未提现奖励");
			holder.order_code.setVisibility(View.GONE);
			holder.tv_order_price.setVisibility(View.GONE);
		}else if(map.get("type").equals("10")){//免付返现
			if(map.get("is_buy").equals("0")){
				holder.tv_order_user_name.setText("免单第一次返现(签收商品后)");
				holder.order_code.setVisibility(View.GONE);
				holder.tv_order_price.setVisibility(View.GONE);
			}else if(map.get("is_buy").equals("1")){
				holder.tv_order_user_name.setText("免单第二次返现(签收后一个月)");
				holder.order_code.setVisibility(View.GONE);
				holder.tv_order_price.setVisibility(View.GONE);
			}else if(map.get("is_buy").equals("2")){
				holder.tv_order_user_name.setText("免单第三次返现(签收后两个月)");
				holder.order_code.setVisibility(View.GONE);
				holder.tv_order_price.setVisibility(View.GONE);
			}else if(map.get("is_buy").equals("3")){
				holder.tv_order_user_name.setText("免单第四次返现(签收后三个月)");
				holder.order_code.setVisibility(View.GONE);
				holder.tv_order_price.setVisibility(View.GONE);
			}else if(map.get("is_buy").equals("4")){
				holder.tv_order_user_name.setText("免单第五次返现(签收后四个月)");
				holder.order_code.setVisibility(View.GONE);
				holder.tv_order_price.setVisibility(View.GONE);
			}
		}else if(map.get("type").equals("11")){
			String status2 = ((Integer)(map.get("is_free")))==1?"解冻":"冻结";
			holder.status.setText("0元购返现"+status2);
			holder.tv_order_user_name.setText("订单号"+map.get("order_code"));
			holder.order_code.setVisibility(View.GONE);
			holder.tv_order_price.setVisibility(View.GONE);
		}
		
		
		
		
		return convertView;
	}
	
	
	class ViewHolder{
		LinearLayout ll_rebate_bg;
		TextView status, order_code, date, amount,tv_order_user_name,tv_order_price;
	}
	
	
}
