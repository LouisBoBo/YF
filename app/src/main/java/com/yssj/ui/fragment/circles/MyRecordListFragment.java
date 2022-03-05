//<<<<<<< .mine
////package com.yssj.ui.fragment.circles;
//=======
//package com.yssj.ui.fragment.circles;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Color;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.text.TextUtils;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.adpter.CircleCollectionAdapter;
//import com.yssj.ui.adpter.CircleMyRecordAdapter;
//import com.yssj.ui.base.BaseFragment;
//import com.yssj.ui.base.BasePager;
//import com.yssj.ui.pager.CollectionRecordPager;
//import com.yssj.ui.pager.MyRecordPager;
//import com.yssj.utils.ToastUtil;
//
//@SuppressLint("NewApi") 
//public class MyRecordListFragment 
////extends BaseFragment implements OnClickListener 
//{
///*	private ViewPager content_pager;
//	private LinearLayout ll;  
//	private List<BasePager> pageLists;
//	private TextView textView1,textView2;
//	private int currIndex = 0;// 当前页卡编号
//	
//	private LinearLayout ll_head;
//	private TextView tvTitle_base,tv_ischoose_text;
//	private LinearLayout img_back;
//	private Button btn_right;
//	private RelativeLayout rl_bottom;   // 底部全选、删除
//	private ImageButton imgbtn_choice_all;   // 全选
//	private Button btn_delete;   // 删除
//	private RelativeLayout rl_choice_all;
//	
//	private CircleMyRecordAdapter mMyRecordAdapter;
//	private CircleCollectionAdapter mCollectionAdapter;
//	
//	
//	private MyRecordFinish mFinish;
//	public interface MyRecordFinish{
//		void finish();
//	}
//	
//	public void setOnFinish(Activity mActivity){
//		this.mFinish = (MyRecordFinish) mActivity;
//	}
//	
//	
//	public MyRecordListFragment() {
//	}
//
//	@Override
//	public View initView() {
//		view = View.inflate(context, R.layout.activity_circle_myrecord_list, null);
//		ll_head = (LinearLayout) view.findViewById(R.id.ll_head);
//		ll_head.setBackgroundColor(Color.TRANSPARENT);
//		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("我的记录");
//		img_back = (LinearLayout) view.findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//		
//		ll = (LinearLayout) view.findViewById(R.id.ll);
//		content_pager = (ViewPager) view.findViewById(R.id.content_pager);
//		
//		textView1 = (TextView) view.findViewById(R.id.textView1);
//		textView2 = (TextView) view.findViewById(R.id.textView2);
////		rl_head_bg = (RelativeLayout) view.findViewById(R.id.rl_head_bg);
////		rl_head_bg.setOnClickListener(this);
//		btn_right = (Button) view.findViewById(R.id.btn_right);
//		btn_right.setVisibility(View.VISIBLE);
//		btn_right.setText("编辑");
//		btn_right.setOnClickListener(this);
//		
//		
//		rl_bottom = (RelativeLayout) view.findViewById(R.id.rl_bottom);
//		imgbtn_choice_all = (ImageButton) view.findViewById(R.id.imgbtn_choice_all);
//		imgbtn_choice_all.setSelected(true);
//		btn_delete = (Button) view.findViewById(R.id.btn_delete);
//		btn_delete.setOnClickListener(this);
//		rl_choice_all = (RelativeLayout) view.findViewById(R.id.rl_choice_all);
//		rl_choice_all.setOnClickListener(this);
//		
//		tv_ischoose_text = (TextView) view.findViewById(R.id.tv_ischoose_text);
//		
//		return view;
//	}
//
//	@Override
//	public void initData() {
//        initViewPager();
//        initTextView();
//        
//	};
//	
//	
//	*//**初始化ViewPager*//*
//	private void initViewPager() {
//		pageLists = new ArrayList<BasePager>();
//		
//		pageLists.add(new MyRecordPager(getActivity()));
//		pageLists.add(new CollectionRecordPager(getActivity()));
//		
//		pageLists.get(0).initData();   // 第一次进来时加载数据
//		
//		// 获取adapter
//		mMyRecordAdapter = ((MyRecordPager)pageLists.get(0)).getAdapter();
//		mCollectionAdapter = ((CollectionRecordPager)pageLists.get(1)).getAdapter();
//		
//		content_pager.setOffscreenPageLimit(1);   // 设置预加载页面
//		content_pager.setAdapter(new MyPagerAdapter(pageLists));
//		
//		content_pager.setCurrentItem(currIndex);
//		content_pager.setOnPageChangeListener(new MyOnPageChangeListener());
//		
//	}
//	
//	private void initTextView() {
//		
//		textView1.setTextColor(getResources().getColor(R.color.pink_color));
//		if("取消".equals(btn_right.getText())){
//			ToastUtil.showShortText(context, "亲，编辑状态下不能被切换。。。");
//		}else{
//			textView1.setOnClickListener(new MyOnClickListener(0));
//			textView2.setOnClickListener(new MyOnClickListener(1));
//		}
//	}
//	
//	 页卡切换监听 
//	public class MyOnPageChangeListener implements OnPageChangeListener {
//
//		@Override
//		public void onPageSelected(int arg0) {
////			if("取消".equals(btn_right.getText())){
////				ToastUtil.showShortText(context, "亲，编辑状态下不能被切换。。。");
////			}else{
//				pageLists.get(arg0).initData();
//				mMyRecordAdapter = ((MyRecordPager)pageLists.get(0)).getAdapter();
//				mCollectionAdapter = ((CollectionRecordPager)pageLists.get(1)).getAdapter();
//				setTextTitleSelectedColor(arg0);
//				
//				if("编辑".equals(btn_right.getText())){
//					
//					if(content_pager.getCurrentItem() == 0){
//						mMyRecordAdapter.configCheckMap(false);
//						mMyRecordAdapter.setFlag(false);
//						mMyRecordAdapter.notifyDataSetChanged();
//					}else if(content_pager.getCurrentItem() == 1){
//						mCollectionAdapter.configCheckMap(false,false);
//						mCollectionAdapter.notifyDataSetChanged();
//					}
//					
//				}else{
//					if(content_pager.getCurrentItem() == 0){
//						mMyRecordAdapter.configCheckMap(false);
//						mMyRecordAdapter.setFlag(true);
//						mMyRecordAdapter.notifyDataSetChanged();
//					}else if(content_pager.getCurrentItem() == 1){
//						mCollectionAdapter.configCheckMap(false,true);
//						mCollectionAdapter.notifyDataSetChanged();
//					}
//					
//				}
////			}
//
//		}
//
//		@Override
//		public void onPageScrolled(int arg0, float arg1, int arg2) {
//		}
//
//		@Override
//		public void onPageScrollStateChanged(int arg0) {
//		}
//	}
//	
//	*//** 设置标题文本的颜色 **//*
//	private void setTextTitleSelectedColor(int arg0) {
//		for (int i = 0; i < 3; i++) {
//			TextView tv = (TextView) ll.getChildAt(i);
//			if (((arg0 == 0) && (i == 0)) || ((arg0 == 1) && (i == 2))) {
//				tv.setTextColor(getResources().getColor(R.color.pink_color));
//			} else {
//				tv.setTextColor(Color.BLACK);
//			}
//		}
//	}
//	
//	 标题点击监听 
//	private class MyOnClickListener implements OnClickListener {
//		private int index = 0;
//
//		public MyOnClickListener(int i) {
//			index = i;
//		}
//
//		public void onClick(View v) {
//			content_pager.setCurrentItem(index);
//				rl_bottom.setVisibility(View.GONE);
//				btn_right.setText("编辑");
//				
//				if(content_pager.getCurrentItem() == 0){
//					mMyRecordAdapter.configCheckMap(false);
//					mMyRecordAdapter.setFlag(false);
//					mMyRecordAdapter.notifyDataSetChanged();
//				}else if(content_pager.getCurrentItem() == 1){
//					mCollectionAdapter.configCheckMap(false,false);
//					mCollectionAdapter.notifyDataSetChanged();
//				}
//				
//			}
//	}
//
//	
//	class MyPagerAdapter extends PagerAdapter {
//		private List<BasePager> pageLists;
//
//		public MyPagerAdapter(List<BasePager> pageLists) {
//			this.pageLists = pageLists;
//		}
//
//		@Override
//		public int getCount() {
//			return pageLists.size();
//		}
//		
//		@Override
//		public Object instantiateItem(ViewGroup container, int position) {
//			container.addView(pageLists.get(position).getRootView());
//			return pageLists.get(position).getRootView();
//		}
//
//		@Override
//		public void destroyItem(ViewGroup container, int position, Object object) {
//			container.removeView((View)object);
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			return arg0 == arg1;
//		}
//	}
//	
//	
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.img_back:   // 返回
//			mFinish.finish();
//			break;
//			
//		case R.id.btn_right:   // 编辑
//			
//			if("编辑".equals(btn_right.getText())){
//				rl_bottom.setVisibility(View.VISIBLE);
//				btn_right.setText("取消");
//				if(content_pager.getCurrentItem() == 0){
//					mMyRecordAdapter.configCheckMap(false);
//					mMyRecordAdapter.setFlag(true);
//					mMyRecordAdapter.notifyDataSetChanged();
//				}else if(content_pager.getCurrentItem() == 1){
//					mCollectionAdapter.configCheckMap(false,true);
//					mCollectionAdapter.notifyDataSetChanged();
//				}
//			}else{
//				rl_bottom.setVisibility(View.GONE);
//				btn_right.setText("编辑");
//				
//				if(content_pager.getCurrentItem() == 0){
//					mMyRecordAdapter.configCheckMap(false);
//					mMyRecordAdapter.setFlag(false);
//					mMyRecordAdapter.notifyDataSetChanged();
//				}else if(content_pager.getCurrentItem() == 1){
//					mCollectionAdapter.configCheckMap(false,false);
//					mCollectionAdapter.notifyDataSetChanged();
//				}
//				
//			}
//			break;
//		case R.id.rl_choice_all:   // 全选
//			if(content_pager.getCurrentItem() == 0){
////				handlerMyRecordChoice();
//			}else if(content_pager.getCurrentItem() == 1){
////				handlerCollectRecordChoice();
//			}
//			break;
//		case R.id.btn_delete:   // 删除
//			if(content_pager.getCurrentItem() == 0){
//				handlerMyRecordDelete();
//			}else if(content_pager.getCurrentItem() == 1){
//				handlerCollectRecordDelete();
//			}
//			break;
//			
//		}
//	}
//	
//	*//**
//	 * 当在我的记录页面点击删除的时候
//	 *//*
//	private void handlerMyRecordDelete(){
//		
//		
//		 * 删除算法最复杂,拿到checkBox选择寄存map
//		 
//		Map<Integer, Boolean> map = mMyRecordAdapter.getCheckMap();
//
//		// 获取当前的数据数量
//		myRecordCount = mMyRecordAdapter.getCount();
//
//		
//		// 进行遍历
//		for (int i = 0; i < myRecordCount; i++) {
//
//			// 因为List的特性,删除了2个item,则3变成2,所以这里要进行这样的换算,才能拿到删除后真正的position
//			int position = i - (myRecordCount - mMyRecordAdapter.getCount());
//
//			if (map.get(i) != null && map.get(i)) {
//
//				Map<String, Object> mapData = (Map<String, Object>) mMyRecordAdapter.getItem(position);
//				news_ids += (String)mapData.get("news_id") + ",";
//				
//				mMyRecordAdapter.getCheckMap().remove(i);
//				mMyRecordAdapter.remove(position);
//				
//				selectMyRecordCount ++ ;
//
//			}
//		}
//		if(!TextUtils.isEmpty(news_ids)){
//			if(news_ids.substring(0,news_ids.lastIndexOf(",")).contains(",")){
//				news_ids = news_ids.substring(0,news_ids.lastIndexOf(","));
//				news_id = "";
//			}else{
//				news_id = news_ids.substring(0,news_ids.lastIndexOf(","));
//				news_ids = "";
//			}
//			
////			deleteMyRecordData();
//			mMyRecordAdapter.notifyDataSetChanged();
//		}
//		
//	}
//	
//	*//**
//	 * 当在收藏记录点击删除的时候
//	 *//*
//	private void handlerCollectRecordDelete(){
//		
//		
//		 * 删除算法最复杂,拿到checkBox选择寄存map
//		 
//		Map<Integer, Boolean> map = mCollectionAdapter.getCheckMap();
//		
//		// 获取当前的数据数量
//		collectCount = mCollectionAdapter.getCount();
//		
//		
//		// 进行遍历
//		for (int i = 0; i < collectCount; i++) {
//			
//			// 因为List的特性,删除了2个item,则3变成2,所以这里要进行这样的换算,才能拿到删除后真正的position
//			int position = i - (collectCount - mCollectionAdapter.getCount());
//			
//			if (map.get(i) != null && map.get(i)) {
//				
//				Map<String, Object> mapData = (Map<String, Object>) mCollectionAdapter.getItem(position);
//				news_ids += (String)mapData.get("news_id") + ",";
//				
//				mCollectionAdapter.getCheckMap().remove(i);
//				mCollectionAdapter.remove(position);
//				
//				SelectCollectCount ++ ;
//				
//			}
//		}
//		if(!TextUtils.isEmpty(news_ids)){
//			if(news_ids.substring(0,news_ids.lastIndexOf(",")).contains(",")){
//				news_ids = news_ids.substring(0,news_ids.lastIndexOf(","));
//				news_id = "";
//			}else{
//				news_id = news_ids.substring(0,news_ids.lastIndexOf(","));
//				news_ids = "";
//			}
//			
////			deleteCollectRecordData();
//			mCollectionAdapter.notifyDataSetChanged();
//		}
//		*/
//		
//		
////	}
//	
//	private String news_id="",news_ids="";
//	
//	private int myRecordCount,collectCount;
//	private int selectMyRecordCount,SelectCollectCount;
////	/**
////	 * 删除所选中的帖子
////	 */
////	private void deleteMyRecordData() {
////		new SAsyncTask<Integer, Void, ReturnInfo>((FragmentActivity) context,null, R.string.wait){
//>>>>>>> .r26813
////
////import java.util.ArrayList;
////import java.util.List;
////import java.util.Map;
////
////import android.annotation.SuppressLint;
////import android.app.Activity;
////import android.content.Intent;
////import android.graphics.Color;
////import android.support.v4.app.FragmentActivity;
////import android.support.v4.view.PagerAdapter;
////import android.support.v4.view.ViewPager;
////import android.support.v4.view.ViewPager.OnPageChangeListener;
////import android.text.TextUtils;
////import android.view.View;
////import android.view.View.OnClickListener;
////import android.view.ViewGroup;
////import android.widget.Button;
////import android.widget.ImageButton;
////import android.widget.LinearLayout;
////import android.widget.RelativeLayout;
////import android.widget.TextView;
////
////import com.yssj.activity.R;
////import com.yssj.app.SAsyncTask;
////import com.yssj.entity.ReturnInfo;
////import com.yssj.model.ComModel2;
////import com.yssj.ui.adpter.CircleCollectionAdapter;
////import com.yssj.ui.adpter.CircleMyRecordAdapter;
////import com.yssj.ui.base.BaseFragment;
////import com.yssj.ui.base.BasePager;
////import com.yssj.ui.pager.CollectionRecordPager;
////import com.yssj.ui.pager.MyRecordPager;
////import com.yssj.utils.ToastUtil;
////
//////@SuppressLint("NewApi") 
//////public class MyRecordListFragment extends BaseFragment implements OnClickListener {
//////	private ViewPager content_pager;
//////	private LinearLayout ll;  
//////	private List<BasePager> pageLists;
//////	private TextView textView1,textView2;
//////	private int currIndex = 0;// 当前页卡编号
//////	
//////	private LinearLayout ll_head;
//////	private TextView tvTitle_base,tv_ischoose_text;
//////	private LinearLayout img_back;
//////	private Button btn_right;
//////	private RelativeLayout rl_bottom;   // 底部全选、删除
//////	private ImageButton imgbtn_choice_all;   // 全选
//////	private Button btn_delete;   // 删除
//////	private RelativeLayout rl_choice_all;
//////	
//////	private CircleMyRecordAdapter mMyRecordAdapter;
//////	private CircleCollectionAdapter mCollectionAdapter;
//////	
//////	
//////	private MyRecordFinish mFinish;
//////	public interface MyRecordFinish{
//////		void finish();
//////	}
//////	
//////	public void setOnFinish(Activity mActivity){
//////		this.mFinish = (MyRecordFinish) mActivity;
//////	}
//////	
//////	
//////	public MyRecordListFragment() {
//////	}
//////
//////	@Override
//////	public View initView() {
//////		view = View.inflate(context, R.layout.activity_circle_myrecord_list, null);
//////		ll_head = (LinearLayout) view.findViewById(R.id.ll_head);
//////		ll_head.setBackgroundColor(Color.TRANSPARENT);
//////		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
//////		tvTitle_base.setText("我的记录");
//////		img_back = (LinearLayout) view.findViewById(R.id.img_back);
//////		img_back.setOnClickListener(this);
//////		
//////		ll = (LinearLayout) view.findViewById(R.id.ll);
//////		content_pager = (ViewPager) view.findViewById(R.id.content_pager);
//////		
//////		textView1 = (TextView) view.findViewById(R.id.textView1);
//////		textView2 = (TextView) view.findViewById(R.id.textView2);
////////		rl_head_bg = (RelativeLayout) view.findViewById(R.id.rl_head_bg);
////////		rl_head_bg.setOnClickListener(this);
//////		btn_right = (Button) view.findViewById(R.id.btn_right);
//////		btn_right.setVisibility(View.VISIBLE);
//////		btn_right.setText("编辑");
//////		btn_right.setOnClickListener(this);
//////		
//////		
//////		rl_bottom = (RelativeLayout) view.findViewById(R.id.rl_bottom);
//////		imgbtn_choice_all = (ImageButton) view.findViewById(R.id.imgbtn_choice_all);
//////		imgbtn_choice_all.setSelected(true);
//////		btn_delete = (Button) view.findViewById(R.id.btn_delete);
//////		btn_delete.setOnClickListener(this);
//////		rl_choice_all = (RelativeLayout) view.findViewById(R.id.rl_choice_all);
//////		rl_choice_all.setOnClickListener(this);
//////		
//////		tv_ischoose_text = (TextView) view.findViewById(R.id.tv_ischoose_text);
//////		
//////		return view;
//////	}
//////
//////	@Override
//////	public void initData() {
//////        initViewPager();
//////        initTextView();
//////        
//////	};
//////	
//////	
//////	/**初始化ViewPager*/
//////	private void initViewPager() {
//////		pageLists = new ArrayList<BasePager>();
//////		
//////		pageLists.add(new MyRecordPager(getActivity()));
//////		pageLists.add(new CollectionRecordPager(getActivity()));
//////		
//////		pageLists.get(0).initData();   // 第一次进来时加载数据
//////		
//////		// 获取adapter
//////		mMyRecordAdapter = ((MyRecordPager)pageLists.get(0)).getAdapter();
//////		mCollectionAdapter = ((CollectionRecordPager)pageLists.get(1)).getAdapter();
//////		
//////		content_pager.setOffscreenPageLimit(1);   // 设置预加载页面
//////		content_pager.setAdapter(new MyPagerAdapter(pageLists));
//////		
//////		content_pager.setCurrentItem(currIndex);
//////		content_pager.setOnPageChangeListener(new MyOnPageChangeListener());
//////		
//////	}
//////	
//////	private void initTextView() {
//////		
//////		textView1.setTextColor(getResources().getColor(R.color.pink_color));
//////		if("取消".equals(btn_right.getText())){
//////			ToastUtil.showShortText(context, "亲，编辑状态下不能被切换。。。");
//////		}else{
//////			textView1.setOnClickListener(new MyOnClickListener(0));
//////			textView2.setOnClickListener(new MyOnClickListener(1));
//////		}
//////	}
//////	
//////	/* 页卡切换监听 */
//////	public class MyOnPageChangeListener implements OnPageChangeListener {
//////
//////		@Override
//////		public void onPageSelected(int arg0) {
////////			if("取消".equals(btn_right.getText())){
////////				ToastUtil.showShortText(context, "亲，编辑状态下不能被切换。。。");
////////			}else{
//////				pageLists.get(arg0).initData();
//////				mMyRecordAdapter = ((MyRecordPager)pageLists.get(0)).getAdapter();
//////				mCollectionAdapter = ((CollectionRecordPager)pageLists.get(1)).getAdapter();
//////				setTextTitleSelectedColor(arg0);
//////				
//////				if("编辑".equals(btn_right.getText())){
//////					
//////					if(content_pager.getCurrentItem() == 0){
//////						mMyRecordAdapter.configCheckMap(false);
//////						mMyRecordAdapter.setFlag(false);
//////						mMyRecordAdapter.notifyDataSetChanged();
//////					}else if(content_pager.getCurrentItem() == 1){
//////						mCollectionAdapter.configCheckMap(false,false);
//////						mCollectionAdapter.notifyDataSetChanged();
//////					}
//////					
//////				}else{
//////					if(content_pager.getCurrentItem() == 0){
//////						mMyRecordAdapter.configCheckMap(false);
//////						mMyRecordAdapter.setFlag(true);
//////						mMyRecordAdapter.notifyDataSetChanged();
//////					}else if(content_pager.getCurrentItem() == 1){
//////						mCollectionAdapter.configCheckMap(false,true);
//////						mCollectionAdapter.notifyDataSetChanged();
//////					}
//////					
//////				}
////////			}
//////
//////		}
//////
//////		@Override
//////		public void onPageScrolled(int arg0, float arg1, int arg2) {
//////		}
//////
//////		@Override
//////		public void onPageScrollStateChanged(int arg0) {
//////		}
//////	}
//////	
//////	/** 设置标题文本的颜色 **/
//////	private void setTextTitleSelectedColor(int arg0) {
//////		for (int i = 0; i < 3; i++) {
//////			TextView tv = (TextView) ll.getChildAt(i);
//////			if (((arg0 == 0) && (i == 0)) || ((arg0 == 1) && (i == 2))) {
//////				tv.setTextColor(getResources().getColor(R.color.pink_color));
//////			} else {
//////				tv.setTextColor(Color.BLACK);
//////			}
//////		}
//////	}
//////	
//////	/* 标题点击监听 */
//////	private class MyOnClickListener implements OnClickListener {
//////		private int index = 0;
//////
//////		public MyOnClickListener(int i) {
//////			index = i;
//////		}
//////
//////		public void onClick(View v) {
//////			content_pager.setCurrentItem(index);
//////				rl_bottom.setVisibility(View.GONE);
//////				btn_right.setText("编辑");
//////				
//////				if(content_pager.getCurrentItem() == 0){
//////					mMyRecordAdapter.configCheckMap(false);
//////					mMyRecordAdapter.setFlag(false);
//////					mMyRecordAdapter.notifyDataSetChanged();
//////				}else if(content_pager.getCurrentItem() == 1){
//////					mCollectionAdapter.configCheckMap(false,false);
//////					mCollectionAdapter.notifyDataSetChanged();
//////				}
//////				
//////			}
//////	}
//////
//////	
//////	class MyPagerAdapter extends PagerAdapter {
//////		private List<BasePager> pageLists;
//////
//////		public MyPagerAdapter(List<BasePager> pageLists) {
//////			this.pageLists = pageLists;
//////		}
//////
//////		@Override
//////		public int getCount() {
//////			return pageLists.size();
//////		}
//////		
//////		@Override
//////		public Object instantiateItem(ViewGroup container, int position) {
//////			container.addView(pageLists.get(position).getRootView());
//////			return pageLists.get(position).getRootView();
//////		}
//////
//////		@Override
//////		public void destroyItem(ViewGroup container, int position, Object object) {
//////			container.removeView((View)object);
//////		}
//////
//////		@Override
//////		public boolean isViewFromObject(View arg0, Object arg1) {
//////			return arg0 == arg1;
//////		}
//////	}
//////	
//////	
//////
//////	@Override
//////	public void onClick(View v) {
//////		switch (v.getId()) {
//////		case R.id.img_back:   // 返回
//////			mFinish.finish();
//////			break;
//////			
//////		case R.id.btn_right:   // 编辑
//////			
//////			if("编辑".equals(btn_right.getText())){
//////				rl_bottom.setVisibility(View.VISIBLE);
//////				btn_right.setText("取消");
//////				if(content_pager.getCurrentItem() == 0){
//////					mMyRecordAdapter.configCheckMap(false);
//////					mMyRecordAdapter.setFlag(true);
//////					mMyRecordAdapter.notifyDataSetChanged();
//////				}else if(content_pager.getCurrentItem() == 1){
//////					mCollectionAdapter.configCheckMap(false,true);
//////					mCollectionAdapter.notifyDataSetChanged();
//////				}
//////			}else{
//////				rl_bottom.setVisibility(View.GONE);
//////				btn_right.setText("编辑");
//////				
//////				if(content_pager.getCurrentItem() == 0){
//////					mMyRecordAdapter.configCheckMap(false);
//////					mMyRecordAdapter.setFlag(false);
//////					mMyRecordAdapter.notifyDataSetChanged();
//////				}else if(content_pager.getCurrentItem() == 1){
//////					mCollectionAdapter.configCheckMap(false,false);
//////					mCollectionAdapter.notifyDataSetChanged();
//////				}
//////				
//////			}
//////			break;
//////		case R.id.rl_choice_all:   // 全选
//////			if(content_pager.getCurrentItem() == 0){
////////				handlerMyRecordChoice();
//////			}else if(content_pager.getCurrentItem() == 1){
////////				handlerCollectRecordChoice();
//////			}
//////			break;
//////		case R.id.btn_delete:   // 删除
//////			if(content_pager.getCurrentItem() == 0){
//////				handlerMyRecordDelete();
//////			}else if(content_pager.getCurrentItem() == 1){
//////				handlerCollectRecordDelete();
//////			}
//////			break;
//////			
//////		}
//////	}
//////	
//////	/**
//////	 * 当在我的记录页面点击删除的时候
//////	 */
//////	private void handlerMyRecordDelete(){
//////		
//////		/*
//////		 * 删除算法最复杂,拿到checkBox选择寄存map
//////		 */
//////		Map<Integer, Boolean> map = mMyRecordAdapter.getCheckMap();
//////
//////		// 获取当前的数据数量
//////		myRecordCount = mMyRecordAdapter.getCount();
//////
//////		
//////		// 进行遍历
//////		for (int i = 0; i < myRecordCount; i++) {
//////
//////			// 因为List的特性,删除了2个item,则3变成2,所以这里要进行这样的换算,才能拿到删除后真正的position
//////			int position = i - (myRecordCount - mMyRecordAdapter.getCount());
//////
//////			if (map.get(i) != null && map.get(i)) {
//////
//////				Map<String, Object> mapData = (Map<String, Object>) mMyRecordAdapter.getItem(position);
//////				news_ids += (String)mapData.get("news_id") + ",";
//////				
//////				mMyRecordAdapter.getCheckMap().remove(i);
//////				mMyRecordAdapter.remove(position);
//////				
//////				selectMyRecordCount ++ ;
//////
//////			}
//////		}
//////		if(!TextUtils.isEmpty(news_ids)){
//////			if(news_ids.substring(0,news_ids.lastIndexOf(",")).contains(",")){
//////				news_ids = news_ids.substring(0,news_ids.lastIndexOf(","));
//////				news_id = "";
//////			}else{
//////				news_id = news_ids.substring(0,news_ids.lastIndexOf(","));
//////				news_ids = "";
//////			}
//////			
////////			deleteMyRecordData();
//////			mMyRecordAdapter.notifyDataSetChanged();
//////		}
//////		
//////	}
//////	
//////	/**
//////	 * 当在收藏记录点击删除的时候
//////	 */
//////	private void handlerCollectRecordDelete(){
//////		
//////		/*
//////		 * 删除算法最复杂,拿到checkBox选择寄存map
//////		 */
//////		Map<Integer, Boolean> map = mCollectionAdapter.getCheckMap();
//////		
//////		// 获取当前的数据数量
//////		collectCount = mCollectionAdapter.getCount();
//////		
//////		
//////		// 进行遍历
//////		for (int i = 0; i < collectCount; i++) {
//////			
//////			// 因为List的特性,删除了2个item,则3变成2,所以这里要进行这样的换算,才能拿到删除后真正的position
//////			int position = i - (collectCount - mCollectionAdapter.getCount());
//////			
//////			if (map.get(i) != null && map.get(i)) {
//////				
//////				Map<String, Object> mapData = (Map<String, Object>) mCollectionAdapter.getItem(position);
//////				news_ids += (String)mapData.get("news_id") + ",";
//////				
//////				mCollectionAdapter.getCheckMap().remove(i);
//////				mCollectionAdapter.remove(position);
//////				
//////				SelectCollectCount ++ ;
//////				
//////			}
//////		}
//////		if(!TextUtils.isEmpty(news_ids)){
//////			if(news_ids.substring(0,news_ids.lastIndexOf(",")).contains(",")){
//////				news_ids = news_ids.substring(0,news_ids.lastIndexOf(","));
//////				news_id = "";
//////			}else{
//////				news_id = news_ids.substring(0,news_ids.lastIndexOf(","));
//////				news_ids = "";
//////			}
//////			
////////			deleteCollectRecordData();
//////			mCollectionAdapter.notifyDataSetChanged();
//////		}
//////		
//////		
//////		
//////	}
//////	
//////	private String news_id="",news_ids="";
//////	
//////	private int myRecordCount,collectCount;
//////	private int selectMyRecordCount,SelectCollectCount;
////////	/**
////////	 * 删除所选中的帖子
////////	 */
////////	private void deleteMyRecordData() {
////////		new SAsyncTask<Integer, Void, ReturnInfo>((FragmentActivity) context,null, R.string.wait){
////////
////////			@Override
////////			protected ReturnInfo doInBackground(FragmentActivity context, Integer... params) throws Exception {
////////				
////////				return ComModel2.getDeleteMyRecord(context,news_id,news_ids);
////////			}
////////
////////			@Override
////////			protected void onPostExecute(ReturnInfo result) {
////////				super.onPostExecute(result);
////////				if(result!= null && "1".equals(result.getStatus())){
////////					ToastUtil.showShortText(context, result.getMessage());
////////					
////////					int count = myRecordCount - selectMyRecordCount;
////////					selectMyRecordCount = 0;
////////					if(count == 0){
////////						((MyRecordPager)pageLists.get(0)).showData();
////////					}
////////					
////////				}else{
////////					ToastUtil.showShortText(context, "糟糕，出错了~~~");
////////					return;
////////				}
////////				news_id="";
////////				news_ids="";
////////				
////////				rl_bottom.setVisibility(View.GONE);
////////				btn_right.setText("编辑");
////////				
////////
////////				mMyRecordAdapter.configCheckMap(false);
////////				mMyRecordAdapter.setFlag(false);
////////;				mMyRecordAdapter.notifyDataSetChanged();
////////				
////////			}
////////			
////////		}.execute();
////////	}
//////	
////////	/**
////////	 * 删除所选中的收藏记录
////////	 */
////////	private void deleteCollectRecordData() {
////////		new SAsyncTask<Integer, Void, ReturnInfo>((FragmentActivity) context,null, R.string.wait){
////////			
////////			@Override
////////			protected ReturnInfo doInBackground(FragmentActivity context, Integer... params) throws Exception {
////////				
////////				return ComModel2.getDeleteCollectRecord(context,news_id,news_ids);
////////			}
//////		
//////		/*	@Override
//////			protected void onPostExecute(ReturnInfo result) {
//////				super.onPostExecute(result);
//////				if("1".equals(result.getStatus())){
//////					ToastUtil.showShortText(context, result.getMessage());
//////					
//////					int count = collectCount - SelectCollectCount;
//////					SelectCollectCount = 0;
//////					if(count == 0){
//////						((CollectionRecordPager)pageLists.get(1)).showData();
//////					}
//////				}else{
//////					ToastUtil.showShortText(context, "糟糕，出错了~~~");
//////					return;
//////				}
//////				news_id="";
//////				news_ids="";
//////				
//////				rl_bottom.setVisibility(View.GONE);
//////				btn_right.setText("编辑");
//////				
//////				
//////				mCollectionAdapter.configCheckMap(false,false);
//////				mCollectionAdapter.notifyDataSetChanged();
//////				
//////			}
//////			
//////		}.execute();
//////	}
//////
//////	*//**
//////	 * 处理我的记录全选和反选
//////	 *//*
//////	private void handlerMyRecordChoice() {
//////		if(imgbtn_choice_all.isSelected()){
//////			imgbtn_choice_all.setImageResource(R.drawable.tvchooseno_selected);
////////			tv_ischoose_text.setText("全不选");
//////			imgbtn_choice_all.setSelected(false);
//////			mMyRecordAdapter.configCheckMap(true);
//////			mMyRecordAdapter.notifyDataSetChanged();
//////		}else{
//////			imgbtn_choice_all.setImageResource(R.drawable.tvchooseno_normal);
////////			tv_ischoose_text.setText("全选");
//////			mMyRecordAdapter.configCheckMap(false);
//////			imgbtn_choice_all.setSelected(true);
//////			mMyRecordAdapter.notifyDataSetChanged();
//////		}*/
//////		
////////	}
////////	/**
////////	 * 处理收藏记录全选和反选
////////	 */
////////	private void handlerCollectRecordChoice() {
////////		if(imgbtn_choice_all.isSelected()){
////////			imgbtn_choice_all.setImageResource(R.drawable.tvchooseno_selected);
//////////			tv_ischoose_text.setText("全选");
////////			imgbtn_choice_all.setSelected(false);
////////			mCollectionAdapter.configCheckMap(true,true);
////////			mCollectionAdapter.notifyDataSetChanged();
////////		}else{
////////			imgbtn_choice_all.setImageResource(R.drawable.tvchooseno_normal);
//////////			tv_ischoose_text.setText("全选");
////////			imgbtn_choice_all.setSelected(true);
////////			mCollectionAdapter.configCheckMap(false,true);
////////			mCollectionAdapter.notifyDataSetChanged();
////////		}
////////	}
//////	
//////}
