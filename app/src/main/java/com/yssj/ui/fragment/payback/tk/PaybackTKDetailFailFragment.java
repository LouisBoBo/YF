package com.yssj.ui.fragment.payback.tk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.ReturnShop;
import com.yssj.entity.Store;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.DateUtil;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YCache;


public class PaybackTKDetailFailFragment extends BaseFragment {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private ImageView img_right_icon;
	
	private TextView tv_close_reason,tv_close_time,tv_shop_name,tv_tk_category,tv_tk_je,tv_tk_reason;
	
	private ReturnShop returnShop;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.payback_detail_close_fail, null);
		
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("退款详情");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setVisibility(View.GONE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		
		tv_close_reason = (TextView) view.findViewById(R.id.tv_close_reason);
		tv_close_time = (TextView) view.findViewById(R.id.tv_close_time);
		tv_shop_name = (TextView) view.findViewById(R.id.tv_shop_name);
		tv_tk_category = (TextView) view.findViewById(R.id.tv_tk_category);
		tv_tk_je = (TextView) view.findViewById(R.id.tv_tk_je);
		tv_tk_reason = (TextView) view.findViewById(R.id.tv_tk_reason);
		
		
		return view;
	}

	@Override
	public void initData() {
		
		Store store = YCache.getCacheStoreSafe(context);
		tv_shop_name.setText(store.getS_name());
		
		
		Bundle bundle = getArguments();
		if(bundle != null){
			returnShop = (ReturnShop) bundle.getSerializable("returnShop");
			if(returnShop != null){
				//0正常完结1超时自动关闭2买家手动关闭3商家超时未处理,自动完结.4换货,买家未及时确认收货,系统自动确认
				if("0".equals(returnShop.getEnd_explain())){
					tv_close_reason.setText("正常完结");
				}else if("1".equals(returnShop.getEnd_explain())){
					tv_close_reason.setText("超时自动关闭");
				}else if("2".equals(returnShop.getEnd_explain())){
					tv_close_reason.setText("买家手动关闭");
				}else if("3".equals(returnShop.getEnd_explain())){
					tv_close_reason.setText("商家超时未处理，自动完结");
				}else if("4".equals(returnShop.getEnd_explain())){
					tv_close_reason.setText("换货，买家未及时确认收货，系统自动确认");
				}
				
				tv_close_time.setText(DateUtil.FormatMillisecond(returnShop.getEnd_time().getTime()));
				
				Integer return_type = returnShop.getReturn_type();
				if(return_type == 1){
					tv_tk_category.setText("换货");
				}else if(return_type == 2){
					tv_tk_category.setText("退货退款");
				}else if(return_type == 3){
					tv_tk_category.setText("仅退款");
				}
				tv_tk_je.setText(returnShop.getMoney() +  "元");
				tv_tk_reason.setText(returnShop.getExplain());
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
		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(getActivity());

			break;
		}
	}
	


}
