//package com.yssj.ui.activity.league;
//
//import java.util.List;
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.custom.view.MyListView;
//import com.yssj.entity.Order;
//import com.yssj.entity.OrderShop;
//import com.yssj.utils.PicassoUtils;
//import com.yssj.utils.SetImageLoader;
//
//public class MySellOrderAdapter extends BaseAdapter {
//	private List<Order> listData;
//	private Context context;
//	private LayoutInflater inflater;
//
//	public MySellOrderAdapter(Context context, List<Order> listData) {
//		this.context = context;
//		this.listData = listData;
//		this.inflater = LayoutInflater.from(context);
//	}
//
//	public List<Order> getData() {
//		return listData;
//	}
//
//	@Override
//	public int getCount() {
//		return listData.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return listData.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//
//		ViewHolder holder;
//		if (null == convertView) {
//			convertView = View.inflate(context, R.layout.league_my_sell_order, null);
//
//			holder = new ViewHolder();
//			holder.tv_shop_name = (TextView) convertView
//					.findViewById(R.id.tv_shop_name);
//			holder.tv_status = (TextView) convertView
//					.findViewById(R.id.tv_status);
//			holder.tv_sum = (TextView) convertView.findViewById(R.id.tv_sum);
//			holder.tv_zprice = (TextView) convertView
//					.findViewById(R.id.tv_zprice);
//			holder.container_item = (LinearLayout) convertView
//					.findViewById(R.id.container_item);// 加载子布局listview
//
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//		final Order order = listData.get(position);
//		holder.tv_shop_name.setText(order.getOrder_code());// 店铺名称
//		int status = Integer.parseInt(order.getStatus().toString());
//
//		String price = "¥" + order.getPay_money();
//		int num = order.getShop_num();
//		holder.tv_sum.setText("共" + num + "件商品");
//		holder.tv_zprice.setText("实付：" + price);
//		List<OrderShop> list = order.getList();
//
//		holder.container_item.removeAllViews();
//		LayoutInflater inflater = LayoutInflater.from(context);
//
//		LinearLayout layout = (LinearLayout) inflater.inflate(
//				R.layout.order_item_list, null);
//		MyListView listView = (MyListView) layout
//				.findViewById(R.id.listview_son);
//		if (list != null) {
//
//			// MySellOrderListSonAdapter adapter = new
//			// MySellOrderListSonAdapter(context,
//			// list, false);
//			// listView.setAdapter(adapter);
//			// listView.setOnItemClickListener(new OnItemClickListener() {
//			// @Override
//			// public void onItemClick(AdapterView<?> arg0, View arg1,
//			// int arg2, long arg3) {
//			// if (order != null) {
//			// Intent intent = new Intent(context,
//			// OrderDetailsActivity.class);
//			// Bundle bundle = new Bundle();
//			// bundle.putSerializable("order", order);
//			// intent.putExtras(bundle);
//			// context.startActivity(intent);
//			// }
//			//
//			// }
//			// });
//			//
//			// holder.container_item.addView(layout);
//			addView(list, holder.container_item, inflater);
//		}
//
//		return convertView;
//	}
//
//	private void addView(List<OrderShop> list, LinearLayout container,
//			LayoutInflater inflater) {
//		container.removeAllViews();
//		for (OrderShop orderShop : list) {
//			View view = inflater.inflate(R.layout.listview_orderlist_son, null);
//			ImageView img_product = (ImageView) view
//					.findViewById(R.id.img_product1);
//			TextView tv_product_name = (TextView) view
//					.findViewById(R.id.tv_product_name);
//			TextView tv_shop_num = (TextView) view
//					.findViewById(R.id.tv_shop_num);
//			TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
//
//			String pic = orderShop.getShop_pic();
//			if (!TextUtils.isEmpty(pic)) {
////				SetImageLoader.initImageLoader(context, img_product, orderShop.getShop_code().substring(1, 4)+"/"+orderShop.getShop_code()+"/"+pic,"");
//				PicassoUtils.initImage(context,  orderShop.getShop_code().substring(1, 4)+"/"+orderShop.getShop_code()+"/"+pic, img_product);
//			}
//			String shop_name = orderShop.getShop_name(1);
//			if (!TextUtils.isEmpty(shop_name)) {
//				tv_product_name.setText(shop_name);
//			}
//			double price = orderShop.getShop_price();
//
//			tv_price.setText("¥" + price);
//			double num = orderShop.getShop_num();
//			tv_shop_num.setText("x" + num);
//			// 1待付款2代发货3待收货4待评价5退款中6已完结7延长收货9取消订单
//
//			container.addView(view);
//		}
//	}
//
//	class ViewHolder {
//		TextView tv_shop_name, tv_status, tv_sum, tv_zprice;
//		LinearLayout container_item;
//	}
//
//}
