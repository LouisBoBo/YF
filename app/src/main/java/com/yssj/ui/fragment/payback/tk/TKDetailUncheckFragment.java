package com.yssj.ui.fragment.payback.tk;

import java.text.DecimalFormat;

import org.apache.commons.lang.time.DateFormatUtils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ReturnShop;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.infos.FundDetailsActivity;
import com.yssj.ui.activity.payback.PaybackCommonFragmentActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.DateUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;


/**
 * 退款---等待商家审核
 * @author Administrator
 *
 */
public class TKDetailUncheckFragment extends BaseFragment {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private ImageView img_right_icon;
	
	private TextView tv_order_je,tv_order_code,tv_apply_date,tv_end_time,tv_apply_time,tv_hh_reason;
	private Button btn_payback_cancle,btn_view_record;
	private ReturnShop returnShop;
	
	private AlertDialog dialog;
	private String isIndiana;
	
	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_payback_tk_detail_uncheck, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("退款详情");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);
		
		tv_order_je = (TextView) view.findViewById(R.id.tv_order_je);	// 订单金额
		tv_order_code = (TextView) view.findViewById(R.id.tv_order_code);	// 订单号码
		tv_apply_date = (TextView) view.findViewById(R.id.tv_apply_date);	// 申请时间
		tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);	// 结束时间
		tv_apply_time = (TextView) view.findViewById(R.id.tv_apply_time);	// 申请时间
		tv_hh_reason = (TextView) view.findViewById(R.id.tv_hh_reason);	// 退款原因
			
		btn_payback_cancle = (Button) view.findViewById(R.id.btn_payback_cancle);	// 取消按钮
		btn_payback_cancle.setOnClickListener(this);
		
		btn_view_record = (Button) view.findViewById(R.id.btn_view_record);	// 查看支付宝记录（商家过期没处理的）
		btn_view_record.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void initData() {
		Bundle bundle = getArguments();
		if(bundle != null){
			returnShop = (ReturnShop) bundle.getSerializable("returnShop");
//			isIndiana=bundle.getString("isIndiana");
			if(returnShop.getOrder_shop_id()==-2){
				btn_payback_cancle.setVisibility(View.GONE);
			}
			if(returnShop != null){
				tv_order_je.setText("订单金额（包邮):¥" + new DecimalFormat("#0.00").format(returnShop.getMoney()));
				tv_order_code.setText("订单号:" + returnShop.getOrder_code());
				tv_apply_date.setText("申请退款时间:" + DateFormatUtils.format(returnShop.getAdd_time(), "yyyy-MM-dd HH:mm:ss"));
				new CountDownTimer(returnShop.getLast_time().getTime()-System.currentTimeMillis(),1000) {
					
					@Override
					public void onTick(long millisUntilFinished) {
						
						tv_end_time.setText("商家在" + DateUtil.FormatMilliseondToEndTime(millisUntilFinished) + "内未处理，将自动同意退款。");
					}
					
					@Override
					public void onFinish() {
						tv_end_time.setText("商家在指定时间内没有处理，系统已自动同意退款。");
						
						btn_view_record.setVisibility(View.VISIBLE);
						btn_payback_cancle.setVisibility(View.GONE);
					}
				}.start();
				
				tv_apply_time.setText(DateFormatUtils.format(returnShop.getAdd_time(), "yyyy-MM-dd HH:mm:ss"));
				tv_hh_reason.setText(returnShop.getCause());
				
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();
			break;
		case R.id.btn_view_record:
			intent = new Intent(context, FundDetailsActivity.class);
			intent.putExtra("index", 2);
			startActivity(intent);
			break;
		case R.id.btn_payback_cancle:
			customDialog();
			break;
		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(getActivity());

			break;
		}
	}
	
	private void customDialog() {
		AlertDialog.Builder builder = new Builder(context);
		// 自定义一个布局文件
		View view = View.inflate(context, R.layout.payback_esc_apply_dialog, null);
		TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
		tv_des.setText("是否确认取消申请退款");
		
		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
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
					ReturnInfo result, Exception e) {
				super.onPostExecute(context, result, e);
				if(null == e){
				if(result != null && "1".equals(result.getStatus())){
					ToastUtil.showLongText(context, result.getMessage());
					getActivity().finish();
					
//					Fragment mFragment = new PayBackChoiceServiceFragment();
//					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
					Intent intent = new Intent(getActivity(),PaybackCommonFragmentActivity.class);
					intent.putExtra("flag", "payBackListFragment");
					getActivity().startActivityForResult(intent, 10002);
				}else{
					ToastUtil.showLongText(context, "糟糕，出错了~~~");
				}
				}
			}
			
		}.execute();
	}


}
