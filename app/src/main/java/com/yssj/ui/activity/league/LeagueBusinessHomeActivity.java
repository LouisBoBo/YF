//package com.yssj.ui.activity.league;
//
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.Html;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.SetImageLoader;
//
///**
// * 超级合伙人主页
// * 
// * @author zly
// * 
// */
//public class LeagueBusinessHomeActivity extends BasicActivity {
//
//	// 主页信息
//	private HashMap<String, String> map;
//
//	DecimalFormat df = new DecimalFormat("#0.00");
//	DecimalFormat dfo = new DecimalFormat("#");
//
//	// private HashMap<String, Object> mapObj;
//	private LinearLayout ll_biz_alliance_revenue_income,
//			ll_biz_alliance_my_member, ll_biz_alliance_sale_history,
//			ll_biz_alliance_revenue_my_card_num;
//
//	public static LeagueBusinessHomeActivity instance;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		aBar.hide();
//
//		instance = this;
//
//		setContentView(R.layout.league_biz_home);
//
//		TextView title = (TextView) findViewById(R.id.tvTitle_base);
//
//		title.setText("超级合伙人");
//
//		findViewById(R.id.img_back).setOnClickListener(this);// 返回
//
//		findViewById(R.id.right_arrow).setOnClickListener(this);
//
//		ll_biz_alliance_revenue_income = (LinearLayout) findViewById(R.id.ll_biz_alliance_revenue_income);
//		ll_biz_alliance_my_member = (LinearLayout) findViewById(R.id.ll_biz_alliance_my_member);
//		ll_biz_alliance_sale_history = (LinearLayout) findViewById(R.id.ll_biz_alliance_sale_history);
//		ll_biz_alliance_revenue_my_card_num = (LinearLayout) findViewById(R.id.ll_biz_alliance_revenue_my_card_num);
//		ll_biz_alliance_revenue_income.setOnClickListener(this);
//		ll_biz_alliance_my_member.setOnClickListener(this);
//		ll_biz_alliance_sale_history.setOnClickListener(this);
//		ll_biz_alliance_revenue_my_card_num.setOnClickListener(this);
//		// mapObj = (HashMap<String, Object>)
//		// getIntent().getSerializableExtra("mapObj");
//
//		initData();
//
//	}
//
//	private void initView() {
//		TextView name = (TextView) findViewById(R.id.name);
//		TextView addTime = (TextView) findViewById(R.id.jion_time);
//		TextView dMoney = (TextView) findViewById(R.id.d_money);
//		TextView tMoney = (TextView) findViewById(R.id.t_money);
//		TextView lMoney = (TextView) findViewById(R.id.l_money);
//		name.setText(map.get("user_name"));
//		addTime.setText("加入时间:"
//				+ new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss")
//						.format(new Date(Long.parseLong(map
//								.get("user_add_date")))));// 加入时间
//		dMoney.setText(df.format(Double.parseDouble(map.get("two_balance"))));// 可提现余额
//		// dMoney.setText(Html.fromHtml(String.format(getString(R.string.d_money),
//		// df.format(Double.parseDouble(map.get("two_freeze_balance"))))));//
//		// 冻结资金
//		// dMoney.setText(Html.fromHtml(String.format(getString(R.string.t_money),
//		// df.format(Double.parseDouble(map.get("two_balance"))))));// 可提现余额
//		// lMoney.setText(df.format(Double.parseDouble(map.get("depositMoneySuccessSum"))));//
//		// 历史佣金总数
//		// lMoney.setText(map.get("depositMoneySuccessSum").toString());
//		ImageView pic = (ImageView) findViewById(R.id.user_pic);
//		SetImageLoader.initImageLoader(null, pic, map.get("user_pic"), "");
//
//		TextView sMoney = (TextView) findViewById(R.id.s_money);// 收益统计
//		TextView hCount = (TextView) findViewById(R.id.h_count);// 伙伴
//		TextView dCount = (TextView) findViewById(R.id.d_count);// 订单
//		TextView card_num = (TextView) findViewById(R.id.card_num);// 卡号
//
//		lMoney.setText(Html.fromHtml(String
//				.format(getString(R.string.s_money), df.format(Double
//						.parseDouble(map.get("depositMoneySuccessSum"))))));
//		hCount.setText(Html.fromHtml(String.format(getString(R.string.h_count),
//				dfo.format(Double.parseDouble(map.get("juniorUserCount"))))));
//		dCount.setText(Html.fromHtml(String.format(getString(R.string.d_count),
//				dfo.format(Double.parseDouble(map.get("orderCount"))))));
//
//		sMoney.setText(df.format(Double.parseDouble(map.get("two_balance")))
//				+ "元");
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		Intent intent;
//		switch (arg0.getId()) {
//		case R.id.img_back:
//			onBackPressed();
//			break;
//		case R.id.right_arrow:
//			intent = new Intent(this, LeagueLogActivity.class);
//
//			intent.putExtra("name", map.get("user_name"));
//
//			intent.putExtra("time", map.get("user_add_date"));
//
//			intent.putExtra("allMoney", map.get("depositMoneySuccessSum"));
//			intent.putExtra("alreadyMoney", df.format(Double
//					.parseDouble(map.get("depositMoneySuccessSum"))));
//			intent.putExtra("dMoney", map.get("two_freeze_balance"));
//			intent.putExtra("twbAll", map.get("two_balance"));
//			intent.putExtra("pic", map.get("user_pic"));
//			intent.putExtra("two_balance", map.get("two_balance"));
//			startActivity(intent);
//
//			break;
//		case R.id.ll_biz_alliance_revenue_income:// 收益统计
//			intent = new Intent(this, BusinessAllianceRevenueActivity.class);
//			intent.putExtra("name", map.get("user_name"));
//
//			intent.putExtra("time", map.get("user_add_date"));
//			intent.putExtra("alreadyMoney", df.format(Double
//							.parseDouble(map.get("depositMoneySuccessSum"))));
//			intent.putExtra("allMoney", map.get("twbAll"));
//
//			intent.putExtra("dMoney", map.get("two_freeze_balance"));
//
//			intent.putExtra("pic", map.get("user_pic"));
//			intent.putExtra("twbAll", map.get("two_balance"));
//			intent.putExtra("two_balance", map.get("two_balance"));
//			startActivity(intent);
//			break;
//		case R.id.ll_biz_alliance_my_member:// 我的会员
//			intent = new Intent(this, BizMemberActivity.class);
//			intent.putExtra("tag", "huiyuan");
//			startActivity(intent);
//			break;
//		case R.id.ll_biz_alliance_sale_history:// 分销订单
//			intent = new Intent(this, BizMemberActivity.class);
//			intent.putExtra("tag", "dingdan");
//			startActivity(intent);
//			break;
//		case R.id.ll_biz_alliance_revenue_my_card_num:// 我的卡号
//			intent = new Intent(this, MyCardNumActivity.class);
//			intent.putExtra("tag", "kahao");
//			startActivity(intent);
//			break;
//
//		default:
//			break;
//		}
//
//	}
//
//	// public void onCheck(View v) {
//	// switch (v.getId()) {
//	// case R.id.biz_tongji: {
//	// Intent intent = new Intent(this,
//	// BusinessAllianceRevenueActivity.class);
//	// intent.putExtra("name", map.get("user_name"));
//	//
//	// intent.putExtra("time", map.get("user_add_date"));
//	//
//	// intent.putExtra("allMoney", map.get("twbAll"));
//	//
//	// intent.putExtra("dMoney", map.get("two_freeze_balance"));
//	//
//	// intent.putExtra("pic", map.get("user_pic"));
//	// intent.putExtra("twbAll", map.get("twbAll"));
//	// intent.putExtra("two_balance", map.get("two_balance"));
//	// startActivity(intent);
//	// }
//	// break;
//	// case R.id.biz_huiyuan: {
//	// Intent intent = new Intent(this,
//	// BizMemberActivity.class);
//	// intent.putExtra("tag", "huiyuan");
//	// startActivity(intent);
//	// }
//	// break;
//	// case R.id.biz_yaoqing: {
//	// if (invateNumber != null) {
//	// if (dialog == null) {
//	// dialog = new MyDilog(LeagueBusinessHomeActivity.this,
//	// invateNumber);
//	// }
//	// dialog.show();
//	// } else {
//	// initInvateData();
//	// }
//	// }
//	// break;
//	// case R.id.biz_dingdan: {
//	// Intent intent = new Intent(this,
//	// BizMemberActivity.class);
//	// intent.putExtra("tag", "dingdan");
//	// startActivity(intent);
//	//
//	// }
//	// break;
//	// case R.id.biz_bianji: {
//	//
//	// Intent intent = new Intent(this,
//	// UnionRegisterActivity.class);
//	// Bundle bundle = new Bundle();
//	// bundle.putSerializable("mapObj", mapObj);
//	// intent.putExtras(bundle);
//	// startActivity(intent);
//	// // finish();
//	// }
//	// break;
//	//
//	// default:
//	// break;
//	// }
//	// }
//
//	private void initData() {
//		new SAsyncTask<Void, Void, HashMap<String, String>>(
//				LeagueBusinessHomeActivity.this, R.string.wait) {
//			@Override
//			protected HashMap<String, String> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				return ComModel2.leagueHomeData(context);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, String> result, Exception e) {
//				if (e == null) {
//					map = result;
//					initView();
//				}
//			}
//		}.execute();
//	}
//
//}
