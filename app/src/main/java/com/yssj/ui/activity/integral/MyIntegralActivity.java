//package com.yssj.ui.activity.integral;
//
//import java.util.HashMap;
//import java.util.List;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.infos.DailySignActivity;
//import com.yssj.ui.activity.infos.IntergralDetailActivity;
//import com.yssj.ui.base.BasicActivity;
//
//public class MyIntegralActivity extends BasicActivity implements OnItemClickListener{
//
//	private TextView tv_my_integral,tv_my_intergral_background, tv_month_income, tv_month_expense;
//	private LinearLayout btn_sign_daily;
//	private LinearLayout ll_month_income;
//	private LinearLayout ll_month_expense;
//	private TextView tvTitle_base;
//	private Button btn_right,btn_itegral_market;
//	private LinearLayout img_back,ll_head;
//	private ListView lv_info;
//	private String mapFulfill = "";
//	private String is_sign = ""; 
//	private String signDays,intergrals;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		getActionBar().hide();
//		setContentView(R.layout.my_integral);
//		initView();
//		initData();
//	}
//
//	private void initView() {
//		tv_my_integral = (TextView) findViewById(R.id.tv_my_integral);
//		tv_my_intergral_background =(TextView) findViewById(R.id.tv_my_intergral_background);
//		tv_month_income = (TextView) findViewById(R.id.tv_month_income);
//		tv_month_expense = (TextView) findViewById(R.id.tv_month_expense);
//		btn_sign_daily = (LinearLayout) findViewById(R.id.btn_sign_daily);
//		btn_sign_daily.setOnClickListener(this);
//		ll_month_income = (LinearLayout) findViewById(R.id.ll_month_income);
//		ll_month_income.setOnClickListener(this);
//		ll_month_expense = (LinearLayout) findViewById(R.id.ll_month_expense);
//		ll_month_expense.setOnClickListener(this);
//		btn_itegral_market = (Button) findViewById(R.id.btn_itegral_market);
//		btn_itegral_market.setOnClickListener(this);
//		btn_itegral_market.setVisibility(View.GONE);		// 暂时把积分商城隐藏
//		
//		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("我的积分");
//		
//		ll_head = (LinearLayout) findViewById(R.id.ll_head);
//		ll_head.setBackgroundColor(Color.TRANSPARENT);
//		
//		btn_right = (Button) findViewById(R.id.btn_right);
//		btn_right.setVisibility(View.VISIBLE);
//		btn_right.setText("说明");
//		btn_right.setOnClickListener(this);
//		
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//		
//		lv_info = (ListView) findViewById(R.id.lv_info);
//		lv_info.setOnItemClickListener(this);
//		
//	}
//
//	private void initData() {
//		new SAsyncTask<Void, Void, HashMap<String,List<HashMap<String, String>>>>(
//				MyIntegralActivity.this, R.string.wait) {
//
//			@Override
//			protected HashMap<String,List<HashMap<String, String>>> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				return ComModel2.getMyIntegralInfo(context);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String,List<HashMap<String, String>>> result) {
//				super.onPostExecute(context, result);
//				if(result != null && ! result.isEmpty()){
//					signDays =result.get("info").get(0).get("signDay").equals("")?"0":result.get("info").get(0).get("signDay");
//					tv_month_income.setText(result.get("info").get(0).get("income").equals("null") ? "0":result.get("info").get(0).get("income"));
//					tv_month_expense.setText(result.get("info").get(0).get("expense").equals("null") ? "0":result.get("info").get(0).get("expense"));
//					intergrals = result.get("info").get(0).get("integral")+"";
//					if(intergrals.equals("null") || intergrals.equals("0")){
//						tv_my_integral.setText("0.00");
//						tv_my_intergral_background.setBackgroundResource(R.drawable.myinteger_black_sum);
//						
//					}else{
//						int intergral = Integer.parseInt(intergrals);
//						if(intergral>9999 && intergral<100000){
//							tv_my_integral.setTextSize(35.0f);
//						}else if(intergral>99999 && intergral<1000000){
//							tv_my_integral.setTextSize(30.0f);
//						}else if(intergral>999999 && intergral<10000000){
//							tv_my_integral.setTextSize(25.0f);
//						}
//						tv_my_integral.setText(""+(result.get("info").get(0).get("integral").equals("null") ? "0":result.get("info").get(0).get("integral")));
//						tv_my_intergral_background.setBackgroundResource(R.drawable.myinteger_pink_sum);
//					}
//					
//					is_sign = result.get("info").get(0).get("is_sign").equals("null") ? "":result.get("info").get(0).get("is_sign");
//					
//					mapFulfill = result.get("info").get(0).get("mapFulfill");
//					
//					MyAdapter mAdapter = new MyAdapter(context, result.get("mission"));
//					lv_info.setAdapter(mAdapter);
//					
//				}else{
//					showToast("糟糕，出错了~~~");
//				}
//			}
//
//		}.execute();
//	}
//	
//	
//	private class MyAdapter extends BaseAdapter{
//		private List<HashMap<String, String>> list;
//		private Context context;
//
//		public MyAdapter(Context context, List<?> list) {
//			this.list = (List<HashMap<String, String>>) list;
//			this.context = context;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHolder holder;
//			if(convertView == null){
//				holder = new ViewHolder();
//				convertView = View.inflate(context, R.layout.myintegral_list_item, null);
//				holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
//				holder.tv_info = (TextView) convertView.findViewById(R.id.tv_info);
//				
//				convertView.setTag(holder);
//			}else{
//				holder = (ViewHolder) convertView.getTag();
//			}
//			
//			HashMap<String, String> map = (HashMap<String, String>) getItem(position);
//			if(map != null && !map.isEmpty()){
//				if(mapFulfill.contains(map.get("id"))){
//					holder.img_icon.setImageResource(R.drawable.integral_use_icon);
//				}else{
//					holder.img_icon.setImageResource(R.drawable.integral_unuse_icon);
//				}
//				holder.tv_info.setText(map.get("m_name"));
//			}
//			return convertView;
//		}
//
//		@Override
//		public int getCount() {
//			return list.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return list.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//	}
//	
//	class ViewHolder{
//		ImageView img_icon;
//		TextView tv_info;
//	}
//
//
//	@Override
//	public void onClick(View v) {
//		super.onClick(v);
//		Intent intent = null;
//		switch (v.getId()) {
//		case R.id.btn_right:	// 积分说明
//			intent = new Intent(context, MyIntegralExplainActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.img_back:
//			finish();
//			break;
//		case R.id.btn_sign_daily: 
//			// 签到
//			intent = new Intent(context,DailySignActivity.class);
//			intent.putExtra("signDays", signDays);
//			intent.putExtra("intergrals", intergrals);
//			intent.putExtra("isSign", is_sign);
//			startActivityForResult(intent, 2341);
//
////			dailySign(v);
//			break;
//		case R.id.ll_month_income:			//近一月收入明细
//			intent =  new Intent(context,IntergralDetailActivity.class);
//			intent.putExtra("page", 0);
//			startActivity(intent);
//			check();
//			break;
//		case R.id.ll_month_expense:
//			intent = new Intent(context,IntergralDetailActivity.class);
//			intent.putExtra("page", 1);
//			startActivity(intent);
//			break;
//		case R.id.btn_itegral_market:	// 积分商城
//			/*intent = new Intent(this, IntegralMarketActivity.class);
//			startActivity(intent);*/
//			Toast.makeText(context, "亲，该模块我们正在全力以赴通宵达旦的开发中。。。", 1).show();
//			break;
//		default:
//			break;
//		}
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		// TODO Auto-generated method stub
//		// 签到
//		Intent intent = new Intent(context,DailySignActivity.class);
//		intent.putExtra("signDays", signDays);
//		intent.putExtra("intergrals", intergrals);
//		intent.putExtra("isSign", is_sign);
//		startActivity(intent);
//	}
//	
//	@Override
//	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(arg0, arg1, arg2);
//		if(arg0==2341){
//			initData();
//		}
//	}
//	private void check(){
//		
//	}
//
//}
