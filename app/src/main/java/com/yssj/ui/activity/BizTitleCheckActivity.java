//package com.yssj.ui.activity;
//
//import java.util.ArrayList;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.BaseAdapter;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.yssj.activity.R;
//import com.yssj.entity.BizCircleEntity;
//import com.yssj.ui.adpter.BizCircleAdapter;
//import com.yssj.ui.base.BasicActivity;
//
///**
// * 二级类目界面
// * @author lbp
// *
// */
//
//public class BizTitleCheckActivity extends BasicActivity {
//	
//	private ListView list1,list2;
//	
//	private PullToRefreshListView dataList;
//	
//	private TextView title,address,asc;
//	
//	private BizCircleAdapter adapter;
//	
//	private View mTitleList;
//	
//	
//	private ImageButton mUpBt;
//	
//	private View bt;
//	
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		aBar.hide();
//		setContentView(R.layout.biz_title_check_activity);
//		title=(TextView) findViewById(R.id.two_title);
//		address=(TextView) findViewById(R.id.address);
//		asc=(TextView) findViewById(R.id.asc);
//		
//		mTitleList=findViewById(R.id.title_list);
//		
//		findViewById(R.id.back).setOnClickListener(this);
//		
//		findViewById(R.id.search).setOnClickListener(this);
//		
//		bt=findViewById(R.id.bt);
//		bt.getBackground().setAlpha(100);
//		title.setOnClickListener(this);
//		title.setSelected(true);
//		address.setOnClickListener(this);
//		asc.setOnClickListener(this);
//		
//		mUpBt=(ImageButton) findViewById(R.id.up_button);
//		
//		mUpBt.setOnClickListener(this);
//		
//		list1=(ListView) findViewById(R.id.list1);
//		
//		list2=(ListView) findViewById(R.id.list2);
//		
//		dataList=(PullToRefreshListView) findViewById(R.id.data);
//		
//		adapter=new BizCircleAdapter(this, new ArrayList<BizCircleEntity>(), false);
//		
//		dataList.setAdapter(adapter);
//		
//		list1.setAdapter(new TwoTitleAdapter());
//		
//		list2.setAdapter(new TitleItemAdapter());
//		
//	}
//	
//	@Override
//	public void onClick(View arg0) {
//		switch (arg0.getId()) {
//		case R.id.two_title:
//		{	
//			title.setSelected(true);
//			address.setSelected(false);
//			asc.setSelected(false);
//			if(mTitleList.getVisibility()==View.GONE){
//				bt.setVisibility(View.VISIBLE);
//				mTitleList.setVisibility(View.VISIBLE);
//				Animation animation = AnimationUtils
//						.loadAnimation(
//								this,
//								R.anim.biz_title_show);
//				mTitleList.startAnimation(animation);
//			}else{
//				bt.setVisibility(View.GONE);
//				mTitleList.setVisibility(View.GONE);
//				Animation animation = AnimationUtils
//						.loadAnimation(
//								this,
//								R.anim.biz_title_gone);
//				mTitleList.startAnimation(animation);
//			}
//			
//		}
//			break;
//			
//		case R.id.address:
//		{	
//			title.setSelected(false);
//			address.setSelected(true);
//			asc.setSelected(false);
//			if(mTitleList.getVisibility()==View.GONE){
//				bt.setVisibility(View.VISIBLE);
//				mTitleList.setVisibility(View.VISIBLE);
//				Animation animation = AnimationUtils
//						.loadAnimation(
//								this,
//								R.anim.biz_title_show);
//				mTitleList.startAnimation(animation);
//			}else{
//				bt.setVisibility(View.GONE);
//				mTitleList.setVisibility(View.GONE);
//				Animation animation = AnimationUtils
//						.loadAnimation(
//								this,
//								R.anim.biz_title_gone);
//				mTitleList.startAnimation(animation);
//			}
//			
//		}
//			break;
//		
//		case R.id.asc:	
//		{
//			title.setSelected(false);
//			address.setSelected(false);
//			asc.setSelected(true);
//		}
//		break;
//		case R.id.up_button:
//		{
//			bt.setVisibility(View.GONE);
//			mTitleList.setVisibility(View.GONE);
//			Animation animation = AnimationUtils
//					.loadAnimation(
//							this,
//							R.anim.biz_title_gone);
//			mTitleList.startAnimation(animation);
//		}
//		break;
//		
//		case R.id.back:
//		{
//			finish();
//			 
//		}
//			break;
//		case R.id.search:
//		{
//			showToast("呵呵呵");
//		}
//			break;
//		
//		default:
//			break;
//		}
//		
//	}
//	
//	
//	
//	private class TwoTitleAdapter extends BaseAdapter{
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return 10;
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			// TODO Auto-generated method stub
//			return null;
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
//			TwoTitleHolder vh;
//			if(v==null){
//				vh=new TwoTitleHolder();
//				v=LayoutInflater.from(context).inflate(R.layout.two_title_adapter_item, arg2,false);
//				v.setTag(vh);
//			}else{
//				vh=(TwoTitleHolder) v.getTag();
//			}
//			
//			
//			return v;
//		}
//		
//	}
//	
//	
//	private class TitleItemAdapter extends BaseAdapter{
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return 15;
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public long getItemId(int arg0) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public View getView(int position, View v, ViewGroup arg2) {
//			TwoTitleHolder vh;
//			if(v==null){
//				vh=new TwoTitleHolder();
//				v=LayoutInflater.from(context).inflate(R.layout.two_title_adapter_item, arg2,false);
//				v.setTag(vh);
//			}else{
//				vh=(TwoTitleHolder) v.getTag();
//			}
//			
//			
//			return v;
//		}
//		
//	}
//	
//	private  class TwoTitleHolder{
//		TextView title;
//		TextView titleCount;
//	}
//}
