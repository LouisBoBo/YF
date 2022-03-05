package com.yssj.ui.fragment.payback;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.fragment.BackHandledFragment;
import com.yssj.ui.fragment.payback.hh.HHFragment;
import com.yssj.ui.fragment.payback.thtk.THFragment;
import com.yssj.ui.fragment.payback.tk.TKFragment;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.WXminiAppUtil;

/**
 * 选择服务
 * @author roger
 *
 */
public class PayBackChoiceServiceFragment extends BackHandledFragment {
	
	private boolean hadIntercept;
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private ImageView img_right_icon;
	
	private LinearLayout ll_thtk,ll_tk,ll_hh;
//	private String order_code = "";		// 订单号
//	private String order_price;
	private boolean isHH=false;//仅换货

    private int issue_status;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_payback_choice_service, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("服务详情");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);
		
		
		
		ll_thtk = (LinearLayout) view.findViewById(R.id.ll_thtk);	// 退货退款
		ll_thtk.setOnClickListener(this);
		
		ll_tk = (LinearLayout) view.findViewById(R.id.ll_tk);		// 仅退款
		ll_tk.setOnClickListener(this);
		
		ll_hh = (LinearLayout) view.findViewById(R.id.ll_hh);		// 换货
		ll_hh.setOnClickListener(this);
		Bundle bundle = getArguments();
		String isDuobao=bundle.getString("isDuobao");


        issue_status = bundle.getInt("issue_status");


        if(issue_status == 21){ //显示 退款退货和换货 隐藏：仅退款

            ll_tk.setVisibility(View.GONE);
            view.findViewById(R.id.line2).setVisibility(View.GONE);


        }else if(issue_status == 22){//显示 换货   隐藏：退货退款和仅退款
            ll_thtk.setVisibility(View.GONE);
            ll_tk.setVisibility(View.GONE);
            view.findViewById(R.id.line1).setVisibility(View.GONE);
            view.findViewById(R.id.line0).setVisibility(View.GONE);



        }else{
            if(SharedPreferencesUtil.getBooleanData(context, "daishouhuo", false)||isDuobao!=null){
                ll_tk.setVisibility(View.GONE);
                view.findViewById(R.id.line1).setVisibility(View.GONE);
            }else{
                ll_tk.setVisibility(View.VISIBLE);
                view.findViewById(R.id.line1).setVisibility(View.VISIBLE);
            }

            if(bundle ==null){
                isHH=false;
            }else{
                isHH=bundle.getBoolean("isHH", false);
            }
            if(isHH){
                ll_thtk.setVisibility(View.GONE);
                ll_tk.setVisibility(View.GONE);
                view.findViewById(R.id.line1).setVisibility(View.GONE);
                view.findViewById(R.id.line2).setVisibility(View.GONE);
            }



        }




		
		return view;
	}

	@Override
	public void initData() {
		Bundle bundle = getArguments();
		if(bundle != null){
//			order_code  = bundle.getString("order_code");	// 获取订单号
//			order_price = bundle.getString("order_price");
		}
	}
	
	
	@Override
	public void onClick(View v) {
		Intent intent = null;
		Fragment mFragment ;
		Bundle bundle = getArguments();
		
		switch (v.getId()) {
		case R.id.img_back:	// 返回
			getActivity().onBackPressed();
			break;
		case R.id.ll_thtk:	// 退货退款
			mFragment = new THFragment();
			mFragment.setArguments(bundle);
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			break;
		case R.id.ll_tk:	// 仅退款
			mFragment = new TKFragment();
			mFragment.setArguments(bundle);
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			break;
		case R.id.ll_hh:	// 换货
			mFragment = new HHFragment();
			mFragment.setArguments(bundle);
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			break;
			
		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(getActivity());

			break;	

		}
	}

	@Override
	public boolean onBackPressed() {
		if(hadIntercept){
            return false;
        }else{
//            Toast.makeText(getActivity(), "Click MyFragment", Toast.LENGTH_SHORT).show();
       	 Fragment mFragment = new IWantApplyFragment();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).addToBackStack(null).commit();
       	 hadIntercept = true;
            return true;
        }
	}


}
