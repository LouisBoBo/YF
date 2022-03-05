//package com.yssj.ui.activity;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.custom.view.XListView;
//import com.yssj.custom.view.XListView.IXListViewListener;
//import com.yssj.ui.base.BasicActivity;
///**
// * 
// * 商家底层页
// * @author lbp
// *
// */
////public class BizDetailActivity extends BasicActivity implements IXListViewListener{
////	
////	private XListView listView;
////	
////	@Override
////	protected void onCreate(Bundle savedInstanceState) {
////		// TODO Auto-generated method stub
////		super.onCreate(savedInstanceState);
////		aBar.hide();
////		setContentView(R.layout.biz_detail_activity);
////		listView=(XListView) findViewById(R.id.data);
////		listView.setPullLoadEnable(false);
////		listView.setXListViewListener(this);
////		findViewById(R.id.back).setOnClickListener(this);
////		findViewById(R.id.search).setOnClickListener(this);
////		
////		
////		View header=LayoutInflater.from(this).inflate(R.layout.biz_detail_header, null);
////		listView.addHeaderView(header);
////		listView.setAdapter(new MyAdapter());
////		header.findViewById(R.id.biz_phone).setOnClickListener(this);
////	}
////	
////	@Override
////	public void onClick(View arg0) {
////		// TODO Auto-generated method stub
////		switch (arg0.getId()) {
////		case R.id.back:
////		{
////			finish();
////		}
////			break;
////		case R.id.search:
////		{
////			showToast("搜索");
////		}
////			break;
////		case R.id.biz_phone:
////		{
////			Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"18829575129"));
////			startActivity(intent);
////		}
////			break;
////			
////		default:
////			break;
////		}
////		
////		
////	}
////	
////	
////	private class MyAdapter extends BaseAdapter{
////
////		@Override
////		public int getCount() {
////			// TODO Auto-generated method stub
////			return 50;
////		}
////
////		@Override
////		public Object getItem(int arg0) {
////			// TODO Auto-generated method stub
////			return null;
////		}
////
////		@Override
////		public long getItemId(int arg0) {
////			// TODO Auto-generated method stub
////			return arg0;
////		}
////
////		@Override
////		public View getView(int position, View v, ViewGroup arg2) {
////			ViewHolder vh;
////			if(v==null){
////				v=LayoutInflater.from(context).inflate(R.layout.biz_detail_adapter_item_view, arg2,false);
////				vh=new ViewHolder();
////				vh.content=(TextView) v.findViewById(R.id.content);
////				v.setTag(vh);
////			}else{
////				vh=(ViewHolder) v.getTag();
////			}
////			
////			if(position%2==0){
////				vh.content.setText("这个是");
////			}else{
////				vh.content.setText("呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼这个是评论呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼这个是评论呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼这个是评论" +
////						"呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼这个是评论呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼这个是评论" +
////						"呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼这个是评论" +
////						"呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼这个是评论");
////			}
////			
////			return v;
////		}
////		
////	}
////
////	
////	private static class ViewHolder{
////		TextView content;
////	}
////	
////	
////	@Override
////	public void onRefresh() {
////		// TODO Auto-generated method stub
////		
////	}
////
////	@Override
////	public void onLoadMore() {
////		listView.stopLoadMore();
////	}
////	
//}
