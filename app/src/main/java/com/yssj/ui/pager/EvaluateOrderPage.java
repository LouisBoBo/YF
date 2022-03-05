package com.yssj.ui.pager;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.OrderShop;
import com.yssj.ui.base.BasePager;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;

public class EvaluateOrderPage extends BasePager{
	
	private ImageView iv_goods_icon;
	private TextView tv_goods_name,tv_color,tv_size,tv_money;

	private OrderShop orderShop ;
	
	public EvaluateOrderPage(Context context,OrderShop shop) {
		super(context);
		this.orderShop = shop;
	}

	@Override
	public View initView() {
		view = View.inflate(context,R.layout.evaluate_order_list_item, null);
		view.setBackgroundColor(Color.WHITE);
		iv_goods_icon = (ImageView) view.findViewById(R.id.iv_goods_icon);
		tv_goods_name = (TextView) view.findViewById(R.id.tv_goods_name);
		tv_color = (TextView) view.findViewById(R.id.tv_color);
		tv_size = (TextView) view.findViewById(R.id.tv_size);
		tv_money = (TextView) view.findViewById(R.id.tv_money);
			
		
		return view;
	}

	@Override
	public void initData() {
		
		String pic = orderShop.getShop_code().substring(1, 4)+"/"+orderShop.getShop_code()+"/"+orderShop.getShop_pic();
		
//		SetImageLoader.initImageLoader(context, iv_goods_icon,pic,"");
		PicassoUtils.initImage(context, pic, iv_goods_icon);
		tv_goods_name.setText(orderShop.getShop_name(0));
		
		tv_color.setText("颜色:" + orderShop.getColor());
		tv_size.setText("尺寸:" + orderShop.getSize());
		tv_money.setText("¥ " + new DecimalFormat("#0.00").format(orderShop.getShop_price()));
		
	}
	
}
