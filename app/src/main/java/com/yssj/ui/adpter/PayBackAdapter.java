package com.yssj.ui.adpter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.custom.view.MyListView;
import com.yssj.entity.Order;
import com.yssj.entity.OrderShop;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;

public class PayBackAdapter extends BaseAdapter{
	private List<Order> listData;
	private Context context;
	private LayoutInflater inflater;
	
	private static HashMap<Integer, String> mapR;
	
	public PayBackAdapter(Context context,List<Order> listData) {
		this.context = context;
		this.listData = listData;
		this.inflater = LayoutInflater.from(context);
	}
	
	public List<Order> getData(){
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

		ViewHolder holder;
		if (null == convertView) {
			convertView = View.inflate(context, R.layout.payback_list_item, null);

			holder = new ViewHolder();
			holder.tv_shop_name = (TextView) convertView
					.findViewById(R.id.tv_shop_name);
			holder.tv_status = (TextView) convertView
					.findViewById(R.id.tv_status);
			holder.tv_sum = (TextView) convertView.findViewById(R.id.tv_sum);
			holder.tv_zprice = (TextView) convertView
					.findViewById(R.id.tv_zprice);
			holder.container_item = (LinearLayout) convertView
					.findViewById(R.id.container_item);// 加载子布局listview

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Order order = listData.get(position);
		holder.tv_shop_name.setText(order.getOrder_code());// 店铺名称
		int status = Integer.parseInt(order.getStatus().toString());

		String price = "¥" + order.getPay_money();
		int num = order.getShop_num();
		holder.tv_sum.setText("共" + num + "件商品");
		holder.tv_zprice.setText("实付：" + price);
		List<OrderShop> list = order.getList();

		holder.container_item.removeAllViews();
		LayoutInflater inflater = LayoutInflater.from(context);

		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.order_item_list, null);
		MyListView listView = (MyListView) layout
				.findViewById(R.id.listview_son);
		if (list != null) {
			
			addView(list, holder.container_item, inflater);

		}

		holder.tv_status.setText(mapR.get(order.getChange()));
		return convertView;
	}
	static{
		mapR = new HashMap<Integer, String>();
		mapR.put(0, "默认");
		mapR.put(1, "换货");
		mapR.put(2, "退货");
		mapR.put(3, "退款");
		mapR.put(4, "用户撤销换货申请");
		mapR.put(5, "撤销退货申请");
		mapR.put(6, "撤销退款申请");
		mapR.put(7, "换货成功");
		mapR.put(8, "退货成功");
		mapR.put(9, "退款成功");
	}
	
	
	/****
	 * 添加 子布局
	 * @param list
	 * @param container
	 * @param inflater
	 */
	private void addView(List<OrderShop> list, LinearLayout container, LayoutInflater inflater){
		container.removeAllViews();
		for(OrderShop orderShop : list){
			View view = inflater.inflate(R.layout.listview_orderlist_son, null);
			ImageView img_product = (ImageView) view
					.findViewById(R.id.img_product1);
			TextView tv_product_name = (TextView) view
					.findViewById(R.id.tv_product_name);
			TextView tv_shop_num = (TextView) view
					.findViewById(R.id.tv_shop_num);
			TextView tv_price = (TextView) view
					.findViewById(R.id.tv_price);
			
			String pic = orderShop.getShop_pic();
			if (!TextUtils.isEmpty(pic)) {
//				SetImageLoader
//						.initImageLoader(context, img_product, pic,"");
//				
				
				
				PicassoUtils.initImage(context, pic, img_product);
				
			}
			String shop_name = orderShop.getShop_name(0);
			if (!TextUtils.isEmpty(shop_name)) {
				tv_product_name.setText(shop_name);
			}
			double price = orderShop.getShop_price();

			tv_price.setText("¥" + price);
			double num = orderShop.getShop_num();
			tv_shop_num.setText("x" + num);
			// 1待付款2代发货3待收货4待评价5退款中6已完结7延长收货9取消订单
			
			container.addView(view);
		}
	}
	class ViewHolder {
		TextView tv_shop_name, tv_status, tv_sum, tv_zprice;
		LinearLayout container_item;
	}
	
}
