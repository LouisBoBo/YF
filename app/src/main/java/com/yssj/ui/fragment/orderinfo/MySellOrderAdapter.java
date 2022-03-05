//package com.yssj.ui.fragment.orderinfo;
//
//import java.io.Serializable;
//import java.util.List;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.MyListView;
//import com.yssj.entity.Order;
//import com.yssj.entity.OrderShop;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.huanxin.activity.ChatActivity;
//import com.yssj.model.ComModel;
//import com.yssj.ui.activity.infos.EvaluateOrderActivity;
//import com.yssj.ui.activity.infos.EvaluateOrderNewActivity;
//import com.yssj.ui.activity.infos.LogisticsInfoActivity;
//import com.yssj.utils.PicassoUtils;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YCache;
//
//public class MySellOrderAdapter extends BaseAdapter{
//	private List<Order> listData;
//	private Context context;
//	private LayoutInflater inflater;
//	private Order order;
//
//	public MySellOrderAdapter(Context context,List<Order> listData) {
//		this.context = context;
//		this.listData = listData;
//		this.inflater = LayoutInflater.from(context);
//	}
//
//	public List<Order> getData(){
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
//			convertView = View.inflate(context, R.layout.my_sell_order, null);
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
//			holder.lay1 = (LinearLayout) convertView.findViewById(R.id.lay1);// 待付款
//			holder.btn_Contact_consumer = (Button) convertView
//					.findViewById(R.id.btn_Contact_consumer);
//			holder.lay2 = (LinearLayout) convertView.findViewById(R.id.lay2);// 待发货
//			holder.btn_Remind_shipments = (Button) convertView
//					.findViewById(R.id.btn_Remind_shipments);
//
//			holder.lay3 = (LinearLayout) convertView.findViewById(R.id.lay3);// 待收货
//			holder.btn_check_logistics_3 = (Button) convertView.findViewById(R.id.btn_check_logistics_3);
//			holder.lay4 = (LinearLayout) convertView.findViewById(R.id.lay4);// 待评价
//			holder.btn_check_logistics_4 = (Button) convertView.findViewById(R.id.btn_check_logistics_4);
//			holder.btn_Evaluation_consumer = (Button) convertView.findViewById(R.id.btn_Evaluation_consumer);
//			holder.lay5 = (LinearLayout) convertView.findViewById(R.id.lay5);
//			holder.lay6 = (LinearLayout) convertView.findViewById(R.id.lay6);
//			holder.lay7 = (LinearLayout) convertView.findViewById(R.id.lay7);
//			holder.lay9 = (LinearLayout) convertView.findViewById(R.id.lay9);
//
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//		order = listData.get(position);
//
//		holder.tv_shop_name.setText(order.getOrder_code());// 店铺名称
//		int status = Integer.parseInt(order.getStatus().toString());
//
//		// /1待付款2代发货3待收货4待评价5退款中6已完结7延长收货9取消订单
//		setVisibility(holder, status, order);
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
////			MySellOrderListSonAdapter adapter = new MySellOrderListSonAdapter(context,
////					list, false);
////			listView.setAdapter(adapter);
////			listView.setOnItemClickListener(new OnItemClickListener() {
////				@Override
////				public void onItemClick(AdapterView<?> arg0, View arg1,
////						int arg2, long arg3) {
////					if (order != null) {
////						Intent intent = new Intent(context,
////								OrderDetailsActivity.class);
////						Bundle bundle = new Bundle();
////						bundle.putSerializable("order", order);
////						intent.putExtras(bundle);
////						context.startActivity(intent);
////					}
////
////				}
////			});
////
////			holder.container_item.addView(layout);
//			addView(list, holder.container_item, inflater);
//		}
//
//		return convertView;
//	}
//
//	private void addView(List<OrderShop> list, LinearLayout container, LayoutInflater inflater){
//		container.removeAllViews();
//		for(OrderShop orderShop : list){
//			View view = inflater.inflate(R.layout.listview_orderlist_son, null);
//			ImageView img_product = (ImageView) view
//					.findViewById(R.id.img_product1);
//			TextView tv_product_name = (TextView) view
//					.findViewById(R.id.tv_product_name);
//			TextView tv_shop_num = (TextView) view
//					.findViewById(R.id.tv_shop_num);
//			TextView tv_price = (TextView) view
//					.findViewById(R.id.tv_price);
//
//			String pic = orderShop.getShop_code().substring(1, 4)+"/"+orderShop.getShop_code()+"/" +orderShop.getShop_pic();
//			if (!TextUtils.isEmpty(pic)) {
////				SetImageLoader
////						.initImageLoader(context, img_product, pic,"");
//
//				PicassoUtils.initImage(context, pic, img_product);
//			}
////			String shop_name = orderShop.getShop_name(1);
//
//			String shop_name = order.getOrder_name();
//
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
//		Button btn_Contact_consumer;//待付款状态
//		Button btn_Remind_shipments;//提醒发货
//		Button btn_check_logistics_3;//待收货状态
//		Button btn_check_logistics_4, btn_Evaluation_consumer;//待评价 ：查看物流，评价买家
//		LinearLayout container_item, lay1, lay2, lay3, lay4, lay5, lay6, lay7,
//				lay9;
//	}
//
//	// /1待付款2待发货3待收货4待评价
//		// 5退款中6已完结7延长收货9取消订单
//		private void setVisibility(ViewHolder holder, int status, final Order order) {
//			switch (status) {
//			case 1:
//				holder.lay1.setVisibility(View.VISIBLE);
//				holder.lay2.setVisibility(View.GONE);
//				holder.lay3.setVisibility(View.GONE);
//				holder.lay4.setVisibility(View.GONE);
//
//				holder.lay5.setVisibility(View.GONE);
//				holder.lay6.setVisibility(View.GONE);
//				holder.lay7.setVisibility(View.GONE);
//				holder.lay9.setVisibility(View.GONE);
//
//				holder.tv_status.setText("待付款");
//
//				holder.btn_Contact_consumer.setOnClickListener(new OnClickListener() {//联系买家
//
//					@Override
//					public void onClick(View arg0) {
//						// TODO Auto-generated method stub
//						context.startActivity(new Intent(context, ChatActivity.class).putExtra("userId", order.getUser_id()+""));
//					}
//				});
//
//				break;
//			case 2:
//				holder.lay1.setVisibility(View.GONE);
//				holder.lay2.setVisibility(View.VISIBLE);
//				holder.lay3.setVisibility(View.GONE);
//				holder.lay4.setVisibility(View.GONE);
//
//				holder.lay5.setVisibility(View.GONE);
//				holder.lay6.setVisibility(View.GONE);
//				holder.lay7.setVisibility(View.GONE);
//				holder.lay9.setVisibility(View.GONE);
//				holder.tv_status.setText("待发货");
//				holder.btn_Remind_shipments
//						.setOnClickListener(new View.OnClickListener() {// 提醒发货(聊天系统)
//
//							@Override
//							public void onClick(View v) {
//
//								ToastUtil.showShortText(context, "提醒发货成功");
//
//
////								// 跳到聊天界面
////								context.startActivity(new Intent(context, ChatActivity.class).putExtra("userId", "914"));
//							}
//						});
//				break;
//			case 3:
//				holder.lay1.setVisibility(View.GONE);
//				holder.lay2.setVisibility(View.GONE);
//				holder.lay3.setVisibility(View.VISIBLE);
//				holder.lay4.setVisibility(View.GONE);
//
//				holder.lay5.setVisibility(View.GONE);
//				holder.lay6.setVisibility(View.GONE);
//				holder.lay7.setVisibility(View.GONE);
//				holder.lay9.setVisibility(View.GONE);
//				holder.tv_status.setText("待收货");
//
//				holder.btn_check_logistics_3.setOnClickListener(new OnClickListener() {//查看物流
//
//					@Override
//					public void onClick(View arg0) {
//						// TODO Auto-generated method stub
//						checkLogistic(order);
//					}
//				});
//
//				break;
//			case 4:
//				holder.lay1.setVisibility(View.GONE);
//				holder.lay2.setVisibility(View.GONE);
//				holder.lay3.setVisibility(View.GONE);
//				holder.lay4.setVisibility(View.VISIBLE);
//
//				holder.lay5.setVisibility(View.GONE);
//				holder.lay6.setVisibility(View.GONE);
//				holder.lay7.setVisibility(View.GONE);
//				holder.lay9.setVisibility(View.GONE);
//				holder.tv_status.setText("待评价");// 交易成功
//
//
//				holder.btn_Evaluation_consumer
//						.setOnClickListener(new View.OnClickListener() {// 评价买家
//
//							@Override
//							public void onClick(View v) {
//								Intent intent = new Intent(context, EvaluateOrderNewActivity.class);
//								Bundle bundle = new Bundle();
//								bundle.putSerializable("order", order);
//								intent.putExtras(bundle);
//								context.startActivity(intent);
//							}
//						});
//				holder.btn_check_logistics_4.setOnClickListener(new OnClickListener() { //查看物流
//
//					@Override
//					public void onClick(View arg0) {
//						// TODO Auto-generated method stub
//						checkLogistic(order);
//					}
//				});
//				break;
//			case 5:// 退款中
//				holder.lay1.setVisibility(View.GONE);
//				holder.lay2.setVisibility(View.GONE);
//				holder.lay3.setVisibility(View.GONE);
//				holder.lay4.setVisibility(View.GONE);
//
//				holder.lay5.setVisibility(View.VISIBLE);
//				holder.lay6.setVisibility(View.GONE);
//				holder.lay7.setVisibility(View.GONE);
//				holder.lay9.setVisibility(View.GONE);
//				holder.tv_status.setText("退款中");
//				break;
//			case 6:// 已完结
//				holder.lay1.setVisibility(View.GONE);
//				holder.lay2.setVisibility(View.GONE);
//				holder.lay3.setVisibility(View.GONE);
//				holder.lay4.setVisibility(View.GONE);
//
//				holder.lay5.setVisibility(View.GONE);
//				holder.lay6.setVisibility(View.VISIBLE);
//				holder.lay7.setVisibility(View.GONE);
//				holder.lay9.setVisibility(View.GONE);
//				holder.tv_status.setText("交易成功");
//				break;
//			case 7:// 延长收货
//				holder.lay1.setVisibility(View.GONE);
//				holder.lay2.setVisibility(View.GONE);
//				holder.lay3.setVisibility(View.GONE);
//				holder.lay4.setVisibility(View.GONE);
//
//				holder.lay5.setVisibility(View.GONE);
//				holder.lay6.setVisibility(View.GONE);
//				holder.lay7.setVisibility(View.VISIBLE);
//				holder.lay9.setVisibility(View.GONE);
//				holder.tv_status.setText("延长收货");
////				holder.btnExtend_Receipt
////						.setOnClickListener(new View.OnClickListener() {// 延长收货
//	//
////							@Override
////							public void onClick(View v) {
////								String order_code = order.getOrder_code();
////								extensionOrdershop(order_code);
////							}
////						});
//
//				break;
//			case 9:// 取消订单
//				holder.lay1.setVisibility(View.GONE);
//				holder.lay2.setVisibility(View.GONE);
//				holder.lay3.setVisibility(View.GONE);
//				holder.lay4.setVisibility(View.GONE);
//
//				holder.lay5.setVisibility(View.GONE);
//				holder.lay6.setVisibility(View.GONE);
//				holder.lay7.setVisibility(View.GONE);
//				holder.lay9.setVisibility(View.VISIBLE);
//				holder.tv_status.setText("取消订单");
//
//				break;
//
//			default:
//				break;
//			}
//
//		}
//
//		private void checkLogistic(Order order){
//			Intent intent = new Intent(context, LogisticsInfoActivity.class);
//			Bundle bundle = new Bundle();
//			bundle.putSerializable("order", order);
//			intent.putExtras(bundle);
//			context.startActivity(intent);
//
//
//
//		}
//}
