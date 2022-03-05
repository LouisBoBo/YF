package com.yssj.ui.fragment.payback.thtk;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ReturnShop;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.payback.PaybackCommonFragmentActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.fragment.payback.PayBackChoiceServiceFragment;
import com.yssj.utils.DateUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;

/**
 * 退货退款，审核通过
 * @author Administrator
 *
 */
public class THDetailAuditPassFragment extends BaseFragment {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private ImageView img_right_icon;
	
	private TextView tv_order_je,tv_order_code,tv_apply_date,tv_end_time,tv_receive_name,tv_phone,tv_post_code,tv_address;
	
	private EditText et_wl_name,et_wl_num;
	
	private Button btn_submit;
	private ReturnShop returnShop;
	
	private PopupWindow popupWindow;
	private ListView listView;
	private List<String> listWlSimpleName = new ArrayList<String>();
	private List<String> listWlName = new ArrayList<String>();
	private MyAdapter mAdapter;
	
	private String express_id = "" ;	// 物流单号
	
	private TextView tv_notice;
	private TextView lin_call_phone;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_payback_th_detail_auditpass, null);
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
		tv_apply_date = (TextView) view.findViewById(R.id.tv_apply_date);	// 换货时间
		tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);	// 结束时间
		
		tv_receive_name = (TextView) view.findViewById(R.id.tv_receive_name);	// 收件人
		tv_phone = (TextView) view.findViewById(R.id.tv_phone);	// 电话
		tv_post_code = (TextView) view.findViewById(R.id.tv_post_code);	// 邮编
		tv_address = (TextView) view.findViewById(R.id.tv_address);	// 地址
		
		et_wl_name = (EditText) view.findViewById(R.id.et_wl_name);	// 选择物流名称
		et_wl_name.setOnClickListener(this);
		
		et_wl_num = (EditText) view.findViewById(R.id.et_wl_num);	// 物流单号
		et_wl_num.setOnClickListener(this);
		
			
		btn_submit = (Button) view.findViewById(R.id.btn_submit);	// 提交按钮
		btn_submit.setOnClickListener(this);
		
		tv_notice = (TextView) view.findViewById(R.id.tv_notice);
		lin_call_phone = (TextView) view.findViewById(R.id.tv_notice);
		lin_call_phone.setOnClickListener(this);
		tv_notice.setText(Html.fromHtml(context.getString(R.string.tv_th_notice)));
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
				tv_apply_date.setText("退货时间:" + DateFormatUtils.format(returnShop.getAdd_time(), "yyyy-MM-dd HH:mm:ss"));
				new CountDownTimer(returnShop.getLast_time().getTime()-System.currentTimeMillis(),1000) {
					
					@Override
					public void onTick(long millisUntilFinished) {
						
						tv_end_time.setText("请您在" + DateUtil.FormatMilliseondToEndTime(millisUntilFinished) + "内填写，逾期将自动取消退货。");
					}
					
					@Override
					public void onFinish() {
						tv_end_time.setText("由于您在指定时间内没有处理，系统已自动取消换货。");
					}
				}.start();
				
				if(null == returnShop.getSupp_consignee() || null == returnShop.getSupp_phone() || null == returnShop.getSupp_postcode() || null == returnShop.getSupp_address()){
					tv_receive_name.setText("空");
					tv_phone.setVisibility(View.GONE);
					tv_post_code.setVisibility(View.GONE);
					tv_address.setVisibility(View.GONE);
					
					tv_notice.setVisibility(View.VISIBLE);
					lin_call_phone.setVisibility(View.VISIBLE);
				}else{
					tv_receive_name.setText("收件人:" + returnShop.getSupp_consignee());
					tv_phone.setText("电话:" + returnShop.getSupp_phone());
					tv_post_code.setText("邮编:" + returnShop.getSupp_postcode());
					tv_address.setText("地址:" + returnShop.getSupp_address());
					
					tv_notice.setVisibility(View.GONE);
					lin_call_phone.setVisibility(View.GONE);
				}
			}
		}
		
		getKuaiDiCompany();
		
		initListView();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();
			break;
		case R.id.btn_submit:	// 提交
			addLogistics();
			break;
		case R.id.et_wl_name:	// 下拉选择物流名称
			showSelectWlNameDown();
			break;
		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(getActivity());

			break;
		case R.id.lin_call_phone://拨打电话
			Intent intent1 = new Intent(Intent.ACTION_CALL,Uri.parse("tel:4008884224"));  
            startActivity(intent1);  
			break;
		}
		

	}
	
	
	private void initListView(){
		listView = new ListView(context);
		
		listView.setBackgroundResource(R.drawable.payback_hh_selectdown_bg);
		listView.setVerticalScrollBarEnabled(false);  // 隐藏ListView滚动条
		listView.setDivider(null);
		
	}
	
	private void showSelectWlNameDown(){
		
		mAdapter = new MyAdapter(listWlSimpleName, et_wl_name);
		listView.setAdapter(mAdapter);
		
		if(popupWindow == null){
			popupWindow = new PopupWindow(listView, et_wl_name.getWidth(),215);
		}
		
		// 要让其子view获取焦点，必须设置为true
		popupWindow.setFocusable(true);
		// 还必须设置一个背景图片，可以是空的
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击消失
		popupWindow.setOutsideTouchable(true);
		
		popupWindow.showAsDropDown(et_wl_name,0,0);
	}
	
	
	class MyAdapter extends BaseAdapter{

		private List<String> list;
		private EditText et;
		public MyAdapter(List<String> list, EditText et){
			this.list = list;
			this.et = et;
		}
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final View view = View.inflate(context, R.layout.payback_hh_selectdown_item, null);
			TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
			tv_item.setText(list.get(position));
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					et.setText(list.get(position));
					popupWindow.dismiss();
					
				}
			});
			return view;
		}
		
	}
	
	
	/**获取各大公司物流*/
	private void getKuaiDiCompany(){
		new SAsyncTask<Void, Void, List<String>>((FragmentActivity)context){

			@Override
			protected List<String> doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.getKuaiDi(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					List<String> result, Exception e) {
				super.onPostExecute(context, result, e);
				if(null == e){
				if(result != null ){
					listWlName = result ;
					for (int i = 0; i < result.size(); i++) {
						listWlSimpleName.add(result.get(i).split("==")[1]);
					}
				}else{
					ToastUtil.showLongText(context, "糟糕，获取物流公司出错了~~~");
				}
				}
			}
			
		}.execute();
	}
	
	
	/**提交物流信息*/
	private void addLogistics(){
		String wlName = et_wl_name.getText().toString();
		String wlNum = et_wl_num.getText().toString();
//		String receiveRaddress = et_receive_address.getText().toString();
		if(TextUtils.isEmpty(wlName)){
			ToastUtil.showShortText(context, "请选择物流信息");
			return ;
		}
		
		if(TextUtils.isEmpty(wlNum)){
			ToastUtil.showShortText(context, "请填写物流单号");
			return ;
		}
		
		for (int i = 0; i < listWlName.size(); i++) {
			if(wlName.equals(listWlName.get(i).split("==")[1])){
				express_id = wlNum + ":" + listWlName.get(i).split("==")[0] ;
			}
		}
		
		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){
			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.addLogistics(context, returnShop.getId()+"", "0", express_id);
			}
			
			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result,  Exception e) {
				super.onPostExecute(context, result, e);
				if(null == e){
				if(result != null && "1".equals(result.getStatus())){
					ToastUtil.showLongText(context, result.getMessage());
					getActivity().finish();
//					Fragment mFragment = new PayBackChoiceServiceFragment();
//					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
					Intent intent=new Intent(getActivity(),PaybackCommonFragmentActivity.class);
					intent.putExtra("flag", "payBackListFragment");
					startActivity(intent);
				}else{
					ToastUtil.showLongText(context, "糟糕，出错了~~~");
				}
				}
			}
			
		}.execute();
	}


}
