package com.yssj.ui.fragment.orderinfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.OrderShop;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.infos.EvaluateOrderActivity;
import com.yssj.ui.activity.infos.LogisticsInfoActivity;
import com.yssj.ui.activity.infos.ThhActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ToastUtil;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MySellOrderListSonAdapter extends BaseAdapter {
	private Context context;
	private List<OrderShop> list;
//	private DisplayImageOptions options;

	private boolean flag;
	private FragmentActivity activity;

	public MySellOrderListSonAdapter(Context context, List<OrderShop> list,
			boolean flag) {
		this.context = context;
		this.list = list;
		this.flag = flag;// 判断待评价页面是否可以退款
		this.activity = (BasicActivity) context;

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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.listview_mysell_orderlist_son, null);
			holder.img_product = (ImageView) convertView
					.findViewById(R.id.img_product1);
			holder.tv_product_name = (TextView) convertView
					.findViewById(R.id.tv_product_name);
			holder.tv_shop_num = (TextView) convertView
					.findViewById(R.id.tv_shop_num);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);


			convertView.setTag(holder);

		} else {
			holder = (Holder) convertView.getTag();
		}
		final OrderShop orderShop = list.get(position);
		
		if (orderShop != null) {
			String pic = orderShop.getShop_pic();
			if (!TextUtils.isEmpty(pic)) {
//				SetImageLoader
//						.initImageLoader(context, holder.img_product, pic,"");
//				
				PicassoUtils.initImage(context, pic,  holder.img_product);
			}
			String shop_name = orderShop.getShop_name(1);
			if (!TextUtils.isEmpty(shop_name)) {
				holder.tv_product_name.setText(shop_name);
			}
			double price = orderShop.getShop_price();

			holder.tv_price.setText("¥" + price);
			double num = orderShop.getShop_num();
			holder.tv_shop_num.setText("x" + num);
			// 1待付款2代发货3待收货4待评价5退款中6已完结7延长收货9取消订单

		}
		holder.img_product.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "" + position, Toast.LENGTH_SHORT)
						.show();

			}
		});


		return convertView;
	}

	class Holder {
		ImageView img_product; // 商品图片
		TextView tv_product_name, tv_price, tv_shop_num; // 商品名称，商品价格，商品个数

	}

}
