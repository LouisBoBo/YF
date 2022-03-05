package com.yssj.ui.fragment.payback.hh;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ReturnShop;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.adpter.TimelineAdapter;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;

/**
 * 退换货，商家确认收货并发货
 * @author Administrator
 *
 */
public class HHDetailSellerConfirmReceiveFragment extends BaseFragment {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private ImageView img_right_icon;
	
	private TextView tv_order_je,tv_order_code,tv_confirm_sh_date,tv_sh_wl_num,tv_sh_date;
	
	
	private ReturnShop returnShop;
	
	private ListView list;

	private TimelineAdapter mAdapter;
	
	private Button btn_confirm_goods;
	
	

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_payback_hh_detail_seller_confirm_receive, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("换货详情");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);
		
		tv_order_je = (TextView) view.findViewById(R.id.tv_order_je);	// 订单金额
		tv_order_code = (TextView) view.findViewById(R.id.tv_order_code);	// 订单号码
		tv_confirm_sh_date = (TextView) view.findViewById(R.id.tv_confirm_sh_date);	// 确认收货时间
		tv_sh_wl_num = (TextView) view.findViewById(R.id.tv_sh_wl_num);	// 商家收货物流单号
		
		tv_sh_date = (TextView) view.findViewById(R.id.tv_sh_date);	// 收货时间
		
		list = (ListView) view.findViewById(R.id.list);
		
		btn_confirm_goods = (Button) view.findViewById(R.id.btn_confirm_goods);
		btn_confirm_goods.setOnClickListener(this);
		
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
				tv_confirm_sh_date.setText("确认收货时间:" + DateFormatUtils.format(returnShop.getAdd_time(), "yyyy-MM-dd HH:mm:ss"));
				tv_sh_date.setText(DateFormatUtils.format(returnShop.getAdd_time(), "yyyy-MM-dd HH:mm:ss"));
				
				tv_sh_wl_num.setText("收货物流单号:" + returnShop.getExpress_id().split(",")[1].split(":")[0]);
				
			}
		}
		
		getLogistics();
		
	}
	
	// 去快递100中得到数据
	private void getLogistics() {
		new SAsyncTask<Void, Void, List<HashMap<String, Object>>>((FragmentActivity)context,
				R.string.wait) {

			@Override
			protected List<HashMap<String, Object>> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				return ComModel2.getLogistics(context,
						returnShop.getExpress_id().split(",")[1].split(":")[0]);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					List<HashMap<String, Object>> result, Exception e) {
				super.onPostExecute(context, result, e);
				if(null == e){
				mAdapter = new TimelineAdapter(context , result);
				list.setAdapter(mAdapter);
				}
			}

		}.execute();
	}
		
	
	/**确认换货*/
	public void confirmHH(){
		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.affirmShop(context, String.valueOf(returnShop.getId()));
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
					ToastUtil.showShortText(context, result.getMessage());
					getActivity().finish();
					
				}else{
					ToastUtil.showShortText(context, "糟糕，出错了~~~");
				}
				}
			}
			
			
		}.execute();
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();
			break;
		case R.id.btn_confirm_goods:	// 确认收货
			confirmHH();
			break;
		case R.id.img_right_icon:// 消息盒子

			WXminiAppUtil.jumpToWXmini(getActivity());

			break;
		}

	}
	


}
