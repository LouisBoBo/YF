package com.yssj.ui.fragment.payback.tk;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ReturnShop;
import com.yssj.entity.UserInfo;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.model.ComModel;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.DateUtil;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YCache;


public class TKDetailOnlyFragment extends BaseFragment {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private ImageView img_right_icon;
	
	private TextView tv_buy_nick_name,tv_sell_nick_name;
	private RoundImageButton img_user_img_buy,img_user_img_seller;
	
	private TextView tv_send_goods_status,tv_reason,tv_apply_time,tv_remain_time,tv_reply_time_sell;
	private Button btn_update_apply,btn_esc_apply,btn_message;
	private ReturnShop returnShop;
	
	private AlertDialog dialog;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_payback_tk_detail_only, null);
		
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("协商仅退款");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);
		
		tv_buy_nick_name = (TextView) view.findViewById(R.id.tv_buy_nick_name);
		tv_sell_nick_name = (TextView) view.findViewById(R.id.tv_sell_nick_name);
		
		tv_send_goods_status = (TextView) view.findViewById(R.id.tv_send_goods_status);
		tv_reason = (TextView) view.findViewById(R.id.tv_reason);
		tv_apply_time = (TextView) view.findViewById(R.id.tv_apply_time);
		tv_remain_time = (TextView) view.findViewById(R.id.tv_remain_time);
		tv_reply_time_sell = (TextView) view.findViewById(R.id.tv_reply_time_sell);
		
		img_user_img_buy = (RoundImageButton) view.findViewById(R.id.img_user_img_buy);
		img_user_img_seller = (RoundImageButton) view.findViewById(R.id.img_user_img_seller);
		
		btn_update_apply = (Button) view.findViewById(R.id.btn_update_apply);
		btn_update_apply.setOnClickListener(this);
		btn_esc_apply = (Button) view.findViewById(R.id.btn_esc_apply);
		btn_esc_apply.setOnClickListener(this);
		btn_message = (Button) view.findViewById(R.id.btn_message);
		btn_message.setOnClickListener(this);
		
		
		return view;
	}

	@Override
	public void initData() {
		UserInfo userInfo = YCache.getCacheUser(context);
		tv_buy_nick_name.setText(userInfo.getNickname());
//		SetImageLoader.initImageLoader(context, img_user_img_buy, userInfo.getPic(),"");
		PicassoUtils.initImage(context, userInfo.getPic(), img_user_img_buy);
		
		Bundle bundle = getArguments();
		if(bundle != null){
			returnShop = (ReturnShop) bundle.getSerializable("returnShop");
			if(returnShop != null){
				Integer order_shop_status = returnShop.getOrder_shop_status();
				if(order_shop_status == 1){
					tv_send_goods_status.setText("未发货");
				}else if(order_shop_status == 2){
					tv_send_goods_status.setText("已发货");
				}else if(order_shop_status == 3){
					tv_send_goods_status.setText("已签收");
				}
				
				tv_reason.setText("原因 : " + returnShop.getCause() + "，金额 : " + returnShop.getMoney() + "，说明 : " + returnShop.getExplain());
				
				tv_apply_time.setText(DateUtil.FormatMillisecond(returnShop.getAdd_time().getTime()));
				
				tv_reply_time_sell.setText(DateUtil.FormatMillisecond(returnShop.getAdd_time().getTime()));
				
				new CountDownTimer(returnShop.getLast_time().getTime()-System.currentTimeMillis(),1000) {
					
					@Override
					public void onTick(long millisUntilFinished) {
						
						tv_remain_time.setText("卖家同意或在" + DateUtil.FormatMilliseondToEndTime(millisUntilFinished) + "内未处理，系统将退款给您。");
					}
					
					@Override
					public void onFinish() {
						tv_remain_time.setText("卖家在指定时间内没有处理，系统已自动同意退款。");
					}
				}.start();
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();
			break;
		case R.id.btn_update_apply:		// 修改申请
			btn_update_apply.setBackgroundResource(R.drawable.payback_tk_bottom_black_bg);
			btn_esc_apply.setBackgroundResource(R.drawable.payback_tk_bottom_white_bg);
			btn_message.setBackgroundResource(R.drawable.payback_tk_bottom_white_bg);
			
			btn_update_apply.setTextColor(getResources().getColor(R.color.white));
			btn_esc_apply.setTextColor(getResources().getColor(R.color.text1_color));
			btn_message.setTextColor(getResources().getColor(R.color.text1_color));
			
			Fragment mFragment = new UpdateTKFragment();
			Bundle bundle = new Bundle();
			bundle.putSerializable("returnShop", returnShop);
			mFragment.setArguments(bundle);
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			
			break;
		case R.id.btn_esc_apply:		// 撤销申请
			btn_esc_apply.setBackgroundResource(R.drawable.payback_tk_bottom_black_bg);
			btn_update_apply.setBackgroundResource(R.drawable.payback_tk_bottom_white_bg);
			btn_message.setBackgroundResource(R.drawable.payback_tk_bottom_white_bg);
			
			btn_esc_apply.setTextColor(getResources().getColor(R.color.white));
			btn_update_apply.setTextColor(getResources().getColor(R.color.text1_color));
			btn_message.setTextColor(getResources().getColor(R.color.text1_color));
			
			customDialog();
			break;
		case R.id.btn_message:		// 留言
			btn_message.setBackgroundResource(R.drawable.payback_tk_bottom_black_bg);
			btn_update_apply.setBackgroundResource(R.drawable.payback_tk_bottom_white_bg);
			btn_esc_apply.setBackgroundResource(R.drawable.payback_tk_bottom_white_bg);
			
			btn_message.setTextColor(getResources().getColor(R.color.white));
			btn_update_apply.setTextColor(getResources().getColor(R.color.text1_color));
			btn_esc_apply.setTextColor(getResources().getColor(R.color.text1_color));
			
			break;
		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(getActivity());

			break;

		}
	}
	
	/**
	 * 撤销申请
	 */
	private void cancle(){
		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.escReturn(context, String.valueOf(returnShop.getId()));
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e	) {
				super.onPostExecute(context, result, e);
				if(null == e){
				if(result != null && "1".equals(result.getStatus())){
					ToastUtil.showLongText(context, result.getMessage());
					dialog.dismiss();
				}else{
					ToastUtil.showLongText(context, "糟糕，出错了~~~");
				}
				}
			}
			
		}.execute();
	}
	
	
	
	private void customDialog() {
		AlertDialog.Builder builder = new Builder(context);
		// 自定义一个布局文件
		View view = View.inflate(context, R.layout.payback_esc_apply_dialog, null);
		Button ok = (Button) view.findViewById(R.id.ok);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 把这个对话框取消掉
				dialog.dismiss();
			}
		});
		
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cancle();
			}
		});
		
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}


}
