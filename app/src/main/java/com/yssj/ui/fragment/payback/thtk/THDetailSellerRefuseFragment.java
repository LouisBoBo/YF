package com.yssj.ui.fragment.payback.thtk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.ReturnShop;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.fragment.payback.SelledHelpActivity;
import com.yssj.utils.WXminiAppUtil;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.DecimalFormat;

/**
 * 退货退款，商家不同意退货
 * @author Administrator
 *
 */
public class THDetailSellerRefuseFragment extends BaseFragment {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private ImageView img_right_icon;
	
	private TextView tv_order_je,tv_order_code,tv_hh_date,tv_apply_date,tv_hh_reason;
	
	private Button btn_connect_seller,btn_payback_help;
	
	
	private ReturnShop returnShop;
	

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_payback_th_detail_seller_refuse, null);
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
		tv_hh_date = (TextView) view.findViewById(R.id.tv_hh_date);	// 换货时间
		tv_apply_date = (TextView) view.findViewById(R.id.tv_apply_date);	// 申请退货时间
		tv_hh_reason = (TextView) view.findViewById(R.id.tv_hh_reason);	// 退货原因
		
		
		btn_connect_seller = (Button) view.findViewById(R.id.btn_connect_seller);
		btn_connect_seller.setOnClickListener(this);
		
		btn_payback_help = (Button) view.findViewById(R.id.btn_payback_help);
		btn_payback_help.setOnClickListener(this);
		
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
				tv_hh_date.setText("申请退货时间:" + DateFormatUtils.format(returnShop.getAdd_time(), "yyyy-MM-dd HH:mm:ss"));
				tv_apply_date.setText(DateFormatUtils.format(returnShop.getAdd_time(), "yyyy-MM-dd HH:mm:ss"));
				tv_hh_reason.setText("退货原因:" + returnShop.getCause());
			}
		}
		
		
	}
	
	
	@Override
	public void onClick(View v) {
		Intent intent ;
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();
			break;
		case R.id.btn_connect_seller:	// 联系卖家
//			intent = new Intent(getActivity(), ChatActivity.class);
//			intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
//			startActivity(intent);

			WXminiAppUtil.jumpToWXmini(getActivity());

			break;
		case R.id.btn_payback_help:	// 售后帮助
			Intent intent2=new Intent(getActivity(),SelledHelpActivity.class);
			startActivity(intent2);
			break;
		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(getActivity());

			break;
		}

	}



}
