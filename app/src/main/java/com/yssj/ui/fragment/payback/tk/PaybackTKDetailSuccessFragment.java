package com.yssj.ui.fragment.payback.tk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.ReturnShop;
import com.yssj.entity.Store;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.ui.activity.infos.FundDetailsActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.DateUtil;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YCache;


public class PaybackTKDetailSuccessFragment extends BaseFragment {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private ImageView img_right_icon;
	
	private TextView tv_tk_je,tv_tk_time,tv_tk_category,tv_tk_status,tv_tk_je2,tv_tk_reason,tv_tk_explain,tv_tk_num,tv_apply_date;
	private Button btn_view_record;
	
	private ReturnShop returnShop;
	

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.payback_detail_close_success, null);
		
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("退款成功");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);
		
		tv_tk_je = (TextView) view.findViewById(R.id.tv_tk_je);
		tv_tk_time = (TextView) view.findViewById(R.id.tv_tk_time);
		tv_tk_category = (TextView) view.findViewById(R.id.tv_tk_category);
		tv_tk_status = (TextView) view.findViewById(R.id.tv_tk_status);
		tv_tk_je2 = (TextView) view.findViewById(R.id.tv_tk_je2);
		tv_tk_reason = (TextView) view.findViewById(R.id.tv_tk_reason);
		tv_tk_explain = (TextView) view.findViewById(R.id.tv_tk_explain);
		tv_tk_num = (TextView) view.findViewById(R.id.tv_tk_num);
		tv_apply_date = (TextView) view.findViewById(R.id.tv_apply_date);
		
		btn_view_record = (Button) view.findViewById(R.id.btn_view_record);
		btn_view_record.setOnClickListener(this);
		
		
		return view;
	}

	@Override
	public void initData() {
		
		Bundle bundle = getArguments();
		if(bundle != null){
			returnShop = (ReturnShop) bundle.getSerializable("returnShop");
			if(returnShop != null){
				tv_tk_je.setText(returnShop.getMoney() + "元");
				tv_tk_time.setText(DateUtil.FormatMillisecond(returnShop.getEnd_time().getTime()));
				if(returnShop.getReturn_type() == 1){
					tv_tk_category.setText("换货");
				}else if(returnShop.getReturn_type() == 2){
					tv_tk_category.setText("退货退款");
				}else if(returnShop.getReturn_type() == 3){
					tv_tk_category.setText("仅退款");
				}
				
				if(returnShop.getStatus() == 1){
					tv_tk_status.setText("待审核");
				}else if(returnShop.getStatus() == 2){
					tv_tk_status.setText("审核通过");
				}else if(returnShop.getStatus() == 3){
					tv_tk_status.setText("审核未通过");
				}else if(returnShop.getStatus() == 4){
					tv_tk_status.setText("供应商已收到货");
				}else if(returnShop.getStatus() == 5){
					tv_tk_status.setText("买家取消");
				}else if(returnShop.getStatus() == 6){
					tv_tk_status.setText("退款成功");
				}else if(returnShop.getStatus() == 7){
					tv_tk_status.setText("退款关闭");
				}else if(returnShop.getStatus() == 8){
					tv_tk_status.setText("换货成功");
				}else if(returnShop.getStatus() == 9){
					tv_tk_status.setText("平台审核退款");
				}
				
				tv_tk_je2.setText(returnShop.getMoney() + "元");
				tv_tk_reason.setText(returnShop.getExplain());
				tv_tk_explain.setText(returnShop.getCause());
				tv_tk_num.setText(returnShop.getReturn_code());
				tv_apply_date.setText(DateUtil.FormatMillisecond(returnShop.getAdd_time().getTime()));
				
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
		case R.id.btn_view_record:		// 查看退款记录
			intent = new Intent(context, FundDetailsActivity.class);
			startActivity(intent);
			break;
		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(getActivity());

			break;
		}
	}
	


}
