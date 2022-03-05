package com.yssj.ui.fragment.funddetail;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.FundDetail;
import com.yssj.ui.base.BaseFragment;

public class ReFundDetailsFragment extends BaseFragment {
	
	private TextView tvTitle_base;
	private LinearLayout img_back,tuikuan;

	private TextView tv_order_code,tv_refund_sum,tv_refund_time,tv_title,tv_get_sum;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_mywalet_refunddetails, null);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("退款详情");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		tv_order_code = (TextView) view.findViewById(R.id.tv_order_code);
		tv_refund_sum = (TextView) view.findViewById(R.id.tv_refund_sum);
		tv_refund_time = (TextView) view.findViewById(R.id.tv_refund_time);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_get_sum = (TextView) view.findViewById(R.id.tv_get_sum);
		tuikuan= (LinearLayout)view.findViewById(R.id.tuikuan);
		tuikuan.setBackgroundColor(Color.WHITE);
		return view;
	}

	@Override
	public void initData() {
		Bundle bundle = getArguments();
		FundDetail detail = (FundDetail)bundle.getSerializable("detail");
		tv_order_code.setText(detail.getOrder_code());
		tv_refund_sum.setText("¥" + detail.getMoney());
		tv_refund_time.setText(DateFormatUtils.format(new Date(detail.getAdd_time()), "yyyy-MM-dd HH:mm:ss"));
		if("0".equals(detail.getPay_type().toString())){
			tv_title.setText("退至钱包金额:");
		}else if("1".equals(detail.getPay_type().toString())){
			tv_title.setText("退至支付宝金额:");
		}if("2".equals(detail.getPay_type().toString())){
			tv_title.setText("退至微信金额:");
		}if("3".equals(detail.getPay_type().toString())){
			tv_title.setText("退至银行卡金额:");
		}
		
		tv_get_sum.setText("¥" + detail.getMoney());
		
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();
			break;

		default:
			break;
		}
	}

}
