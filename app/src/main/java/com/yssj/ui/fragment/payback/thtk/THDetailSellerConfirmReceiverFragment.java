package com.yssj.ui.fragment.payback.thtk;

import java.text.DecimalFormat;

import org.apache.commons.lang.time.DateFormatUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.ReturnShop;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.WXminiAppUtil;

/**
 * 退货退款，商家确认收货
 * @author Administrator
 *
 */
public class THDetailSellerConfirmReceiverFragment extends BaseFragment {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private ImageView img_right_icon;
	
	private TextView tv_order_je,tv_order_code,tv_end_date,tv_apply_date,tv_return_money;
	
	
	private ReturnShop returnShop;
	
	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_payback_th_detail_seller_confirm_receiver, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("退货详情");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		
		tv_order_je = (TextView) view.findViewById(R.id.tv_order_je);	// 订单金额
		tv_order_code = (TextView) view.findViewById(R.id.tv_order_code);	// 订单号码
		tv_end_date = (TextView) view.findViewById(R.id.tv_end_date);	// 完成时间
		tv_apply_date = (TextView) view.findViewById(R.id.tv_apply_date);	// 完成时间
		
		tv_return_money = (TextView) view.findViewById(R.id.tv_return_money);	// 退货金额
		
		
		return view;
	}

	@Override
	public void initData() {
		Bundle bundle = getArguments();
		if(bundle != null){
			returnShop = (ReturnShop) bundle.getSerializable("returnShop");
			if(returnShop != null){
				tv_order_je.setText("订单金额（包邮):¥" + new DecimalFormat("#0.00").format(returnShop.getMoney()));
				tv_order_code.setText("订单号:" + returnShop.getOrder_code());
				tv_end_date.setText("完成时间:" + DateFormatUtils.format(returnShop.getEnd_time(), "yyyy-MM-dd HH:mm:ss"));
				tv_apply_date.setText(DateFormatUtils.format(returnShop.getEnd_time(), "yyyy-MM-dd HH:mm:ss"));
				
				tv_return_money.setText("商家已收到你退的商品，交易款项" + returnShop.getMoney() + "已归还至您的账户");
				
			}
		}
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();
			break;
		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(getActivity());

			break;
		}

	}
	


}
