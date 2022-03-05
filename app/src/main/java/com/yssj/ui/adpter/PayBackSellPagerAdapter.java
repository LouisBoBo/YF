package com.yssj.ui.adpter;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.Order;
import com.yssj.entity.OrderShop;
import com.yssj.ui.base.BaseObjectAdapter;
import com.yssj.ui.fragment.orderinfo.MySellOrderDetailsActivity;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;

public class PayBackSellPagerAdapter extends BaseObjectAdapter {
	private String flag = "";    // 标记从哪个Fragment过来的，以便处理状态
	public PayBackSellPagerAdapter(Context context,String flag) {
		super(context);
		this.flag = flag;
		inflater=LayoutInflater.from(context);
	}
	private LayoutInflater inflater;
	private int status;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context,R.layout.apply_payback_list_item, null);
//			holder.iv_goods_icon = (ImageView) convertView.findViewById(R.id.iv_goods_icon);
//			holder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
//			holder.tv_payback_status = (TextView) convertView.findViewById(R.id.tv_payback_status);
//			holder.tv_color = (TextView) convertView.findViewById(R.id.tv_color);
//			holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
//			holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
//			holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
			holder.tv_payback_money = (TextView) convertView.findViewById(R.id.tv_payback_money);
			holder.tv_count_goods = (TextView) convertView.findViewById(R.id.tv_count_goods);
			holder.continer = (LinearLayout) convertView.findViewById(R.id.container);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final Order order = (Order) getItem(position);
		if(order != null){
			
			status = Integer.parseInt(order.getStatus().toString());
			if(order.getList()!=null&&order.getList().size()>0){
					double itemAccount = addView(order.getList(), holder.continer,  order);

					// holder.container_item.addView(layout);
					String totalAccount = new java.text.DecimalFormat("#0.00")
							.format(itemAccount);
					holder.tv_payback_money.setText("实付 :¥" + order.getPay_money());
					holder.tv_count_goods.setText("共"+order.getList().size()+"件商品");
				}
			
			
			
			/**
			final OrderShop shop = (OrderShop) order.getList().get(0);
			SetImageLoader.initImageLoader(context, holder.iv_goods_icon,shop.getShop_pic(),"");
			holder.tv_goods_name.setText(shop.getShop_name());
			
			if("buyedGoodsListPage".equals(flag) || "selledGoodsListPage".equals(flag)){		// BuyedGoodsListPage，已买商品
				if(order.getChange() == 1 || order.getChange() == 2 || order.getChange() == 3){
					holder.tv_payback_status.setText("卖家处理中");
				}else if(order.getChange() == 4 || order.getChange() == 5 || order.getChange() == 6){
					holder.tv_payback_status.setText("已撤销申请");
				}else if(order.getChange() == 7 || order.getChange() == 8 || order.getChange() == 9){
					holder.tv_payback_status.setText("售后成功");
				}else{
					holder.tv_payback_status.setText(order.getChange() + "");
				}
			}else if("iWantApplyFragment".equals(flag)){		// IWantApplyFragment,我要申请
				if("1".equals(order.getStatus().toString())){
					holder.tv_payback_status.setText("待付款");
				}else if("2".equals(order.getStatus().toString())){
					holder.tv_payback_status.setText("买家已付款");
				}else if("3".equals(order.getStatus().toString())){
					holder.tv_payback_status.setText("卖家已发货");
				}else if("4".equals(order.getStatus().toString())){
					holder.tv_payback_status.setText("待评价");
				}else if("5".equals(order.getStatus().toString())){
					holder.tv_payback_status.setText("追加评论");
				}else if("6".equals(order.getStatus().toString())){
					holder.tv_payback_status.setText("交易成功");
				}else if("7".equals(order.getStatus().toString())){
					holder.tv_payback_status.setText("延长收货");
				}else if("9".equals(order.getStatus().toString())){
					holder.tv_payback_status.setText("取消订单");
				}else{
					holder.tv_payback_status.setText(order.getStatus().toString());
				}
			}
			holder.tv_color.setText("颜色:" + shop.getColor());
			holder.tv_size.setText("尺寸:" + shop.getSize());
			holder.tv_count.setText("X" + order.getShop_num());
			holder.tv_money.setText("￥ " + shop.getShop_price());
			holder.tv_payback_money.setText("实付:￥" + order.getOrder_price());
			holder.tv_count_goods.setText("共" + order.getShop_num() + "件商品");*/
		}
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,MySellOrderDetailsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("order", order);
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});
		

		
		return convertView;
	}

	private double addView(List<OrderShop> list, LinearLayout container,
			 Order order) {
		container.removeAllViews();
		double itemAccount = 0;
		for (OrderShop orderShop : list) {
			View view = inflater.inflate(R.layout.listview_orderlist_son, null);
			ImageView img_product = (ImageView) view
					.findViewById(R.id.img_product1);
			TextView tv_product_name = (TextView) view
					.findViewById(R.id.tv_product_name);
			TextView tv_shop_num = (TextView) view
					.findViewById(R.id.tv_shop_num);
			TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
			TextView tvColor = (TextView) view
					.findViewById(R.id.tv_product_color);
			TextView tvSize = (TextView) view
					.findViewById(R.id.tv_product_size);
			TextView tvStatus = (TextView) view.findViewById(R.id.tv_status);
			// TextView tvOriginalPrice = (TextView) view
			// .findViewById(R.id.tv_original_price);

			String pic = orderShop.getShop_code().substring(1, 4)+"/"+orderShop.getShop_code()+"/"+orderShop.getShop_pic();
			img_product.setTag(pic);
			if (!TextUtils.isEmpty(pic)) {
//				SetImageLoader.initImageLoader(context, img_product, pic,"");
				PicassoUtils.initImage(context, pic, img_product);
			}
			String shop_name = orderShop.getShop_name(0);
			if (!TextUtils.isEmpty(shop_name)) {
				tv_product_name.setText(shop_name);
			}

			if (null == orderShop.getColor()) {
				tvColor.setVisibility(View.GONE);
			}

			if (null == orderShop.getSize()) {
				tvSize.setVisibility(View.GONE);
			}

			tvColor.setText("颜色 : " + orderShop.getColor());
			tvSize.setText("尺寸 : " + orderShop.getSize());
			String price = new java.text.DecimalFormat("#0.00")
					.format(orderShop.getShop_price());
			tv_price.setText("¥" + price);
			int num = orderShop.getShop_num();
			tv_shop_num.setText("x" + num);
			itemAccount += orderShop.getShop_price() * orderShop.getShop_num();
			switch (status) {
			case 1:
				if (order.getLast_time().before(new Date())) {
					tvStatus.setText("已过期");
				} else {
					tvStatus.setText("待付款");
				}
				break;
			case 2:
				tvStatus.setText("待发货");
				break;
			case 3:
				tvStatus.setText("待收货");
				break;
			case 4:
				tvStatus.setText("待评价");
				break;
			case 5:
				tvStatus.setText("已评价");
				break;
			case 6:
				tvStatus.setText("交易成功");
				break;
			case 7:
				tvStatus.setText("延长收货");
				break;
			case 9:
				tvStatus.setText("取消订单");
				break;

			default:
				break;
			}
			container.addView(view);
		}
		return itemAccount;
	}
	
	
	class ViewHolder {
		ImageView iv_goods_icon;
		TextView tv_goods_name,tv_payback_status,tv_color,tv_size,tv_count,tv_money,tv_payback_money,tv_count_goods;
		LinearLayout continer;
	}
}
