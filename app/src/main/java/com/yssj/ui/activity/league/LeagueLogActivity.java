//package com.yssj.ui.activity.league;
//
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.XListView;
//import com.yssj.custom.view.XListView.IXListViewListener;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.infos.MyWalletCommonFragmentActivity;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.SetImageLoader;
///**
// * 佣金记录
// * @author lbp
// *
// */
//public class LeagueLogActivity extends BasicActivity {
//	
//	private XListView mListView;
//	
//	private View header;
//	
//	private Button bt_withdraw;
//	
//	
//	private List<HashMap<String, String>> txList;
//
//	
//	private List<HashMap<String, String>> yjList;
//	
//	private MyLogAdapter mAdapter;
//	
//	private TextView mTX,mYJ;
//	private RelativeLayout jilu;
//	
//	DecimalFormat df=new DecimalFormat("#0.00");
//	
//	String balance = "0.00";
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		aBar.hide();
//		setContentView(R.layout.league_log_header);
//		TextView title=(TextView) findViewById(R.id.tvTitle_base);
//		title.setText("超级合伙人");
//		findViewById(R.id.img_back).setOnClickListener(this);
//		jilu =(RelativeLayout) findViewById(R.id.jilu);
//		jilu.setBackgroundColor(Color.WHITE);
//		
//		mListView=(XListView) findViewById(R.id.log_data);
////		
//		txList=new ArrayList<HashMap<String,String>>();
//		
//		yjList=new ArrayList<HashMap<String,String>>();
//		
//		header=LayoutInflater.from(this).inflate(R.layout.league_log, null);
//		mListView.addHeaderView(header);
//		
//		bt_withdraw = (Button) findViewById(R.id.bt_withdraw);
//		bt_withdraw.setOnClickListener(this);
//		
//		balance = getIntent().getStringExtra("two_balance");
//		initData();
//	}
//	
//	
//	private void initHeader(HashMap<String, Object> map){
//		ImageView pic=(ImageView) header.findViewById(R.id.user_pic);
//		TextView joimTime=(TextView) header.findViewById(R.id.jion_time);
//		TextView name=(TextView) header.findViewById(R.id.name);
//		TextView hint=(TextView) header.findViewById(R.id.hint);
//		hint.setText(Html.fromHtml(getString(R.string.league_log_hint)));
//		TextView sucessMoney=(TextView) header.findViewById(R.id.s_money);
//		TextView lMoney=(TextView) header.findViewById(R.id.l_money);
//		TextView dMoney=(TextView) header.findViewById(R.id.d_money);
//		TextView ljMoney=(TextView) header.findViewById(R.id.lj_money);
//		
//		mTX=(TextView) header.findViewById(R.id.tx);
//		mTX.setSelected(true);
//		mYJ=(TextView) header.findViewById(R.id.yj);
//		
//		mTX.setOnClickListener(this);
//		mYJ.setOnClickListener(this);
//		SetImageLoader.initImageLoader(null, pic, getIntent().getStringExtra("pic"),"");
//		joimTime.setText("加入时间:"+new SimpleDateFormat("yyyy-MM-dd    HH:mm:ss").format(new Date(Long.parseLong(getIntent().getStringExtra("time")))));
//		name.setText(getIntent().getStringExtra("name"));
//		ljMoney.setText(df.format(Double.parseDouble(getIntent().getStringExtra("twbAll"))));
//		dMoney.setText(df.format(Double.parseDouble(getIntent().getStringExtra("dMoney"))));
//		lMoney.setText(df.format(Double.parseDouble(getIntent().getStringExtra("twbAll"))));
//		sucessMoney.setText(Html.fromHtml(String.format(getString(R.string.s_money), getIntent().getStringExtra("alreadyMoney"))));//成功提现的金额
//		if(null!=map.get("data")){
//			txList.addAll((List<HashMap<String, String>>) map.get("data"));
//		}
//		
//		mAdapter=new MyLogAdapter();
//		
//		mListView.setAdapter(mAdapter);
//		
//		mListView.setPullLoadEnable(true);
//		
//		mListView.setXListViewListener(new IXListViewListener() {
//			
//			@Override
//			public void onRefresh() {
//				
//			}
//			
//			@Override
//			public void onLoadMore() {
//				
//				if(isSelectTX){
//					TXPage++;
//					initTXData();
//				}else{
//					YJPage++;
//					initYJData();
//				}
//			}
//		});
//	}
//	
//	private boolean isSelectTX=true;
//	
//	@Override
//	public void onClick(View arg0) {
//		switch (arg0.getId()) {
//		case R.id.img_back:
//			onBackPressed();
//			break;
//		case R.id.bt_withdraw:
//			Intent intent = new Intent(this, MyWalletCommonFragmentActivity.class);
//			intent.putExtra("flag", "withDrawalFragment");
//			intent.putExtra("balance", balance);
//			intent.putExtra("alliance", "alliance");
//			startActivity(intent);
//			break;
//		case R.id.tx:
//		{
//			mAdapter.isTx(true);
//			mTX.setSelected(true);
//			mYJ.setSelected(false);
//			isSelectTX=true;
//		}
//			break;
//		case R.id.yj:
//		{
//			mAdapter.isTx(false);
//			mTX.setSelected(false);
//			mYJ.setSelected(true);
//			isSelectTX=false;
//			if(yjList.isEmpty()){
//				//加载佣金记录
//				initYJData();
//			}
//			
//		}
//			break;
//			
//		default:
//			break;
//		}
//	}
//	
//	
//	private void initData(){
//		
//		new SAsyncTask<Void, Void, HashMap<String, Object>>(LeagueLogActivity.this, R.string.wait){
//			@Override
//			protected HashMap<String, Object> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				return ComModel2.earningsDetail(context);
//			}
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, Object> result, Exception e) {
//				if(e==null){
//					initHeader(result);
//				}
//			}
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//		}.execute();
//	}
//	
//	private int YJPage = 1;
//	private int TXPage = 2;
//	
//	private void initYJData(){
//		
//		new SAsyncTask<Void, Void, HashMap<String, Object>>(LeagueLogActivity.this, R.string.wait){
//			@Override
//			protected HashMap<String, Object> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				return ComModel2.YJLog(context,YJPage+"");
//			}
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, Object> result, Exception e) {
//				if(e==null){
//				List<HashMap<String, String>> list=result.get("data")==null?null:(List<HashMap<String, String>>) result.get("data");
//				if(list!=null&&!list.isEmpty()){
//					yjList.addAll(list);
//				}
//				mListView.stopLoadMore();
//				mAdapter.notifyDataSetChanged();
//				}else{
//					if(YJPage>1){
//						YJPage--;
//					}
//				}
//			}
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//		}.execute();
//	}
//	
//	private void initTXData(){
//		
//		new SAsyncTask<Void, Void, HashMap<String, Object>>(LeagueLogActivity.this, R.string.wait){
//			@Override
//			protected HashMap<String, Object> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				return ComModel2.TXLog(context, TXPage+"");
//			}
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, Object> result, Exception e) {
//				if(e==null){
//					List<HashMap<String, String>> list=result.get("data")==null?null:(List<HashMap<String, String>>) result.get("data");
//					if(list!=null&&list.isEmpty()){
//						txList.addAll(list);
//					}
//					mListView.stopLoadMore();
//					mAdapter.notifyDataSetChanged();
//				}else{
//					if(TXPage>1){
//						TXPage--;
//					}
//				}
//			}
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//		}.execute();
//	}
//	
//	
//	private class MyLogAdapter extends BaseAdapter{
//		
//		private boolean isTx=true;//提现记录或者佣金记录
//		
//		@Override
//		public int getCount() {
//			return isTx?txList.size():(yjList.size()==0?(txList.size()==0?0:1):yjList.size());
//		}
//		
//		public void isTx(boolean isTx){
//			this.isTx=isTx;
//			notifyDataSetChanged();
//		}
//		
//		@Override
//		public Object getItem(int arg0) {
//			// TODO Auto-generated method stub
//			return isTx?txList.get(arg0):yjList.get(arg0);
//		}
//
//		@Override
//		public long getItemId(int arg0) {
//			// TODO Auto-generated method stub
//			return arg0;
//		}
//
//		@Override
//		public View getView(int position, View v, ViewGroup arg2) {
//			
//			Holder h;
//			if(v==null){
//				v=LayoutInflater.from(LeagueLogActivity.this).inflate(R.layout.league_adapter_item_view, arg2,false);
//				h=new Holder();
//				h.title=(TextView) v.findViewById(R.id.title);
//				h.time=(TextView) v.findViewById(R.id.time);
//				h.sucessStatuss=(TextView) v.findViewById(R.id.sucess_status);
//				h.money=(TextView) v.findViewById(R.id.money);
//				h.bai=(ImageView) v.findViewById(R.id.bai);
//				h.bai.getLayoutParams().height=LeagueLogActivity.this.getResources().getDisplayMetrics().heightPixels;
//				h.content=v.findViewById(R.id.content);
//				v.setTag(h);
//			}else{
//				h=(Holder) v.getTag();
//			}
//			
//			if(isTx){
//				h.bai.setVisibility(View.GONE);
//				h.content.setVisibility(View.VISIBLE);
//				Map<String, String> map=txList.get(position);
//				h.title.setText(map.get("collect_bank_name")+"****"+map.get("collect_bank_code"));
//				h.time.setText(new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss").format(new Date(Long.parseLong(map.get("add_date")))));
//				String txt=null;
//				switch (Integer.parseInt(map.get("check"))) {
//				case 0:
//				{
//					txt="待审核";
//				}
//					break;
//				case 1:
//				{
//					txt="通过";
//				}
//				break;
//				case 2:
//				{
//					txt="不通过";
//				}
//				break;
//				case 3:
//				{
//					txt="成功到账";
//				}
//				break;
//
//				default:
//					break;
//				}
//				h.sucessStatuss.setText(txt);
//				h.money.setText(df.format(Double.parseDouble(map.get("money"))));
//			}else{
//				if(yjList.isEmpty()){
//					h.bai.setVisibility(View.VISIBLE);
//					h.content.setVisibility(View.GONE);
//					return v;
//				}
//				h.bai.setVisibility(View.GONE);
//				h.content.setVisibility(View.VISIBLE);
//				Map<String, String> map=yjList.get(position);
//				h.title.setText(map.get("NICKNAME"));
//				h.time.setText(new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss").format(new Date(Long.parseLong(map.get("add_date")))));
//				if(map.get("is_free").equals("1")){
//					h.sucessStatuss.setText("成功");
//				}else if(map.get("status").equals("1")){
//					h.sucessStatuss.setText("失败");
//				}else{
//					h.sucessStatuss.setText("冻结");
//				}
//				h.money.setText(df.format(Double.parseDouble(map.get("money"))));
//			}
//			
//			return v;
//		}
//		
//	}
//	
//	private class Holder{
//		TextView title,time,money,sucessStatuss;
//		ImageView bai;
//		View content;
//	}
//	
//}
