package com.yssj.ui.fragment.payback.tk;

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
import com.yssj.ui.fragment.payback.SelledHelpActivity;
import com.yssj.utils.WXminiAppUtil;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.DecimalFormat;

/**
 * 退款，商家不同意退款
 * @author Administrator
 *
 */
public class UserRefuseFragment extends BaseFragment {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private ImageView img_right_icon;
	
	private TextView tv_order_je,tv_order_code,tv_tk_date,tv_cancel_type, tv_cancle_date;
	
	private TextView tv_status;
	
	private ReturnShop returnShop;
	
	private String status;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.user_refuse, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		view.setBackgroundColor(Color.WHITE);
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);
		
		tv_order_je = (TextView) view.findViewById(R.id.tv_order_je);	// 订单金额
		tv_order_code = (TextView) view.findViewById(R.id.tv_order_code);	// 订单号码
		tv_tk_date = (TextView) view.findViewById(R.id.tv_tk_date);	// 申请退款时间
		
		tv_status = (TextView) view.findViewById(R.id.tv_status);
		
		tv_cancel_type = (TextView) view.findViewById(R.id.tv_cancel_type);
		tv_cancle_date = (TextView) view.findViewById(R.id.tv_cancle_date);
		
		
		return view;
	}

	@Override
	public void initData() {
		Bundle bundle = getArguments();
		if(bundle != null){
			returnShop = (ReturnShop) bundle.getSerializable("returnShop");
			if(returnShop != null){
				switch (returnShop.getReturn_type()) {
				case 1:
					tvTitle_base.setText("换货详情");
					tv_status.setText("申请换货");
					status = "申请换货时间:";
					break;
				case 2:
					tvTitle_base.setText("退货详情");
					tv_status.setText("申请退货");
					status = "申请退货时间:";
					break;
				case 3:
					tvTitle_base.setText("退款详情");
					tv_status.setText("申请退款");
					status = "申请退款时间:";
					break;

				default:
					break;
				}
				
				tv_order_je.setText("订单金额（包邮):¥" + new DecimalFormat("#0.00").format(returnShop.getMoney()));
				tv_order_code.setText("订单号:" + returnShop.getOrder_code());
				tv_tk_date.setText(status + DateFormatUtils.format(returnShop.getAdd_time(), "yyyy-MM-dd HH:mm:ss"));
				tv_cancle_date.setText("撤销时间"+DateFormatUtils.format(returnShop.getEnd_time(), "yyyy-MM-dd HH:mm:ss"));
				
				
			}
		}
		
		
	}
	
	
	@Override
	public void onClick(View v) {
		Intent intent = null;
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
