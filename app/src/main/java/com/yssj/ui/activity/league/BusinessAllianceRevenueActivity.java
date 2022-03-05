//package com.yssj.ui.activity.league;
//
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.Html;
//import android.text.TextPaint;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.infos.MyWalletCommonFragmentActivity;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.SetImageLoader;
//
//
//
///***
// * 收益统计
// * 
// * @author Administrator
// * 
// */
//public class BusinessAllianceRevenueActivity extends BasicActivity  {
//	
//	private LinearLayout img_back;
//	private TextView tvTitle_base,tv_today_income,
//	tv_week_income,tv_month_income,tv_total_income,tv_member_add_time,tv_member_name,
//	tv_freeze_commission_count,tv_withdraw_commission_count,tv_widraw_cash_count
//	;
//	private Button bt_alliance_withdraw;
//	private ImageView iv_tongji_header;
//	DecimalFormat df = new DecimalFormat("#0.00");
//	String balance = "0.00";
//	private ImageView iv_to_next_page;
//	private String name,time,allMoney,dMoney,pic,two_balance;
//	private TextView alreadyCount;
//private String getMoney;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.biz_alliance_revenue_statistics);
//		aBar.hide();
//		name = getIntent().getStringExtra("name");
//		time = getIntent().getStringExtra("time");
//		allMoney = getIntent().getStringExtra("allMoney");
//		dMoney = getIntent().getStringExtra("dMoney");
//		getMoney=getIntent().getStringExtra("alreadyMoney");
////		System.out.println(getMoney);
//		pic = getIntent().getStringExtra("pic");
//		two_balance = getIntent().getStringExtra("two_balance");
//		
//		
//	
//		
//		initData();
//		initView();
//		
//		
//	}
//
//	
//	
//
//	private void initView() {
//		alreadyCount=(TextView) findViewById(R.id.tv_already_count);
//		alreadyCount.setText(":"+getMoney);
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("收益统计");
//		
//		tv_total_income = (TextView) findViewById(R.id.tv_total_income);
//		tv_today_income = (TextView) findViewById(R.id.tv_today_income);
//		tv_week_income = (TextView) findViewById(R.id.tv_week_income);
//		tv_month_income = (TextView) findViewById(R.id.tv_month_income);
//		
//		iv_tongji_header = (ImageView) findViewById(R.id.iv_tongji_header);
//		tv_member_add_time = (TextView) findViewById(R.id.tv_member_add_time);
//		tv_member_name = (TextView) findViewById(R.id.tv_member_name);
////		tv_freeze_commission_count = (TextView) findViewById(R.id.tv_freeze_commission_count);
//		tv_withdraw_commission_count = (TextView) findViewById(R.id.tv_withdraw_commission_count);
////		tv_widraw_cash_count = (TextView) findViewById(R.id.tv_widraw_cash_count);
//		
//		
//		SetImageLoader.initImageLoader(null, iv_tongji_header, getIntent().getStringExtra("pic"),"");
//		tv_member_add_time.setText("加入时间:"+new SimpleDateFormat("yyyy-MM-dd    HH:mm:ss").format(new Date(Long.parseLong(getIntent().getStringExtra("time")))));
//		tv_member_name.setText(getIntent().getStringExtra("name"));
//		balance =getIntent().getStringExtra("two_balance");
//		tv_withdraw_commission_count.setText(df.format(Double.parseDouble(balance)));
//		TextPaint tp = tv_withdraw_commission_count.getPaint(); 
//		tp.setFakeBoldText(true); 
////		tv_freeze_commission_count.setText(df.format(Double.parseDouble(getIntent().getStringExtra("dMoney"))));
////		tv_widraw_cash_count.setText(df.format(Double.parseDouble(getIntent().getStringExtra("twbAll"))));
//		
//		bt_alliance_withdraw= (Button)findViewById(R.id.bt_alliance_withdraw);
//		bt_alliance_withdraw.setOnClickListener(this);
//		
////		iv_to_next_page = (ImageView) findViewById(R.id.iv_to_next_page);
////		iv_to_next_page.setOnClickListener(this);
//		tv_total_income.setText(df.format(Double.parseDouble(getIntent().getStringExtra("twbAll"))));
//		
//	}
//
//
//
//
//	private void initData() {
//		new SAsyncTask<Void, Void, HashMap<String, Object>>(
//				BusinessAllianceRevenueActivity.this, R.string.wait) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				return ComModel2.getRevenueStatistics(context);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, Object> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if(null == e){
//				if(result != null && ! result.isEmpty()){
//					
//
//					tv_month_income.setText(String.valueOf(df.format(result.get("monthMoney"))));
//					tv_week_income.setText(String.valueOf(df.format(result.get("monthMoney"))));
//					tv_today_income.setText(String.valueOf(df.format(result.get("todayMoney"))));
//					
//					
//				}else{
//					showToast("糟糕，出错了~~~");
//				}
//				}
//			}
//
//		}.execute();
//	}
//
//
//
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		
//		case R.id.img_back:
//			finish();
//			break;
//		case R.id.bt_alliance_withdraw:
//			Intent intent = new Intent(this, MyWalletCommonFragmentActivity.class);
//			intent.putExtra("flag", "withDrawalFragment");
//			intent.putExtra("balance", balance);
//			intent.putExtra("alliance", "alliance");
//			startActivity(intent);
//			break;
//		/*case R.id.iv_to_next_page:
//			Intent intent2 = new Intent(this, LeagueLogActivity.class);
//
//			intent2.putExtra("name", name);
//
//			intent2.putExtra("time", time);
//
//			intent2.putExtra("allMoney", allMoney);
//
//			intent2.putExtra("dMoney", dMoney);
//
//			intent2.putExtra("pic",pic);
//			intent2.putExtra("two_balance", two_balance);
//			startActivity(intent2);
//			break;*/
//		default:
//			break;
//		}
//
//	}
//
//
//
//
//
//
//
//
//}
