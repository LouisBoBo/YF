//<<<<<<< .mine
////package com.yssj.ui.fragment.circles;
////
////import java.io.Serializable;
////import java.util.ArrayList;
////import java.util.HashMap;
////import java.util.List;
////import java.util.Map;
////
////import android.annotation.SuppressLint;
////import android.app.Activity;
////import android.content.Intent;
////import android.graphics.Color;
////import android.os.Bundle;
////import android.support.v4.app.FragmentActivity;
////import android.support.v4.view.PagerAdapter;
////import android.support.v4.view.ViewPager;
////import android.support.v4.view.ViewPager.OnPageChangeListener;
////import android.view.View;
////import android.view.View.OnClickListener;
////import android.view.ViewGroup;
////import android.widget.ImageButton;
////import android.widget.ImageView;
////import android.widget.LinearLayout;
////import android.widget.RelativeLayout;
////import android.widget.TextView;
////
////import com.yssj.YJApplication;
////import com.yssj.activity.R;
////import com.yssj.app.SAsyncTask;
////import com.yssj.custom.view.RoundImageButton;
////import com.yssj.custom.view.ToLoginDialog;
////import com.yssj.entity.ReturnInfo;
////import com.yssj.model.ComModel2;
////import com.yssj.ui.activity.circles.CircleDetailNewActivity;
////import com.yssj.ui.activity.circles.PublishTopicActivity;
////import com.yssj.ui.base.BaseFragment;
////import com.yssj.ui.base.BasePager;
////import com.yssj.ui.pager.AllPostListPage;
////import com.yssj.ui.pager.FinePostListPager;
////import com.yssj.ui.pager.HotPostListPager;
////import com.yssj.utils.SetImageLoader;
////import com.yssj.utils.ToastUtil;
////
/////**
//// * 帖子列表
//// * 
//// * @author Administrator
//// * 
//// */
////@SuppressLint("NewApi")
////public class CirclePostListFragment 
//////extends BaseFragment implements
//////		OnClickListener 
////		
////		{
//////	private String tags;
//////	private HashMap<String, Object> map;
//////	private ViewPager content_pager;
//////	private LinearLayout ll;
//////	private List<BasePager> pageLists;
//////	private TextView textView1, textView2, textView3;
//////	private int currIndex = 0;// 当前页卡编号
//////
//////	private LinearLayout ll_head;
//////	private TextView tvTitle_base;
//////	private LinearLayout img_back;
//////
//////	private RelativeLayout rl_head_bg;
//////	private ImageView img_bg;
//////	private RoundImageButton img_user;
//////	private TextView tv_title, tv_admin, tv_ncount, tv_ucount;
//////	private ImageButton imgbtn_join;
//////	private ImageView img_right_icon;
//////	private ImageButton imgbtn_left_icon;
//////	
//////	private ToLoginDialog loginDialog;
//////	
//////	private int isNo=0;//是否加入圈子0表示没加入
//////	
//////	private Map<String, List<HashMap<String, Object>>> saveResult;
//////
//////	public CirclePostListFragment(HashMap<String, Object> map) {
//////		this.map = map;
//////		System.out.println("map数据集：" + map.toString());
//////	}
//////
//////	@Override
//////	public void onCreate(Bundle savedInstanceState) {
//////		super.onCreate(savedInstanceState);
//////	}
//////
//////	public CirclePostListFragment() {
//////	}
//////
//////	private CirClePostFinish mFinish;
//////
//////	public interface CirClePostFinish {
//////		void finish();
//////	}
//////
//////	public void setOnFinish(Activity mActivity) {
//////		this.mFinish = (CirClePostFinish) mActivity;
//////	}
//////
//////	@Override
//////	public View initView() {
//////		view = View.inflate(context, R.layout.activity_circle_post_list, null);
//////		ll_head = (LinearLayout) view.findViewById(R.id.ll_head);
//////		ll_head.setBackgroundColor(Color.TRANSPARENT);
//////		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
//////		tvTitle_base.setText("帖子列表");
//////		tvTitle_base.setTextColor(getResources().getColor(R.color.white));
//////		img_back = (LinearLayout) view.findViewById(R.id.img_back);
//////		img_back.setOnClickListener(this);
//////
//////		imgbtn_left_icon = (ImageButton) view
//////				.findViewById(R.id.imgbtn_left_icon);
//////		
//////		imgbtn_left_icon.setBackgroundResource(R.drawable.white_return_back_icon);
//////
//////		ll = (LinearLayout) view.findViewById(R.id.ll);
//////		content_pager = (ViewPager) view.findViewById(R.id.content_pager);
//////
//////		textView1 = (TextView) view.findViewById(R.id.textView1);
//////		textView2 = (TextView) view.findViewById(R.id.textView2);
//////		textView3 = (TextView) view.findViewById(R.id.textView3);
//////
//////		rl_head_bg = (RelativeLayout) view.findViewById(R.id.rl_head_bg);
//////		rl_head_bg.setOnClickListener(this);
//////		img_bg = (ImageView) view.findViewById(R.id.img_bg);
//////		img_user = (RoundImageButton) view.findViewById(R.id.img_user);
//////		img_user.setOnClickListener(this);
//////		tv_title = (TextView) view.findViewById(R.id.tv_title);
//////		tv_admin = (TextView) view.findViewById(R.id.tv_admin);
//////		tv_ncount = (TextView) view.findViewById(R.id.tv_ncount);
//////		tv_ucount = (TextView) view.findViewById(R.id.tv_ucount);
//////
//////		/*
//////		 * 右上角发表话题的按钮
//////		 */
//////		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
//////		img_right_icon.setVisibility(View.VISIBLE);
//////		img_right_icon.setImageResource(R.drawable.circle_public_post_icon);
//////		img_right_icon.setOnClickListener(this);
//////
//////		// 加入圈子
//////		imgbtn_join = (ImageButton) view.findViewById(R.id.imgbtn_join);
//////		imgbtn_join.setOnClickListener(this);
//////
//////		return view;
//////		
//////	}
//////
//////	@Override
//////	public void initData() {
//////		initViewPager();
//////		initTextView();
//////
//////		new SAsyncTask<Integer, Void, Map<String, List<HashMap<String, Object>>>>(
//////				(FragmentActivity) context, null) {
//////
//////			@Override
//////			protected Map<String, List<HashMap<String, Object>>> doInBackground(
//////					FragmentActivity context, Integer... params)
//////					throws Exception {
//////				if(YJApplication.instance.isLoginSucess()){
//////					return ComModel2.getPostList(context,
//////							(String) map.get("circle_id"), 0, "true", 0, 0);
//////				}else{
//////					return ComModel2.getPostList2(context,
//////							(String) map.get("circle_id"), 0, "true", 0, 0);
//////				}
//////				
//////			}
//////
//////			@Override
//////			protected void onPostExecute(
//////					Map<String, List<HashMap<String, Object>>> result) {
//////				super.onPostExecute(result);
//////				saveResult = result;
//////				
//////				if (result != null) {
//////					tags = (String) result.get("circlesData").get(0).get("tag");
//////					SetImageLoader.initImageLoader(
//////							context,
//////							img_bg,
//////							(String) result.get("circlesData").get(0)
//////									.get("bg_pic"),"");
//////					SetImageLoader.initImageLoader(context, img_user,
//////							(String) result.get("circlesData").get(0)
//////									.get("pic"),"");
//////					
//////					tv_title.setText((String) result.get("circlesData").get(0)
//////							.get("title"));
//////					
//////					tv_admin.setText("管理员:"
//////							+ (String) result.get("circlesData").get(0)
//////									.get("admin"));
//////					
//////					isNo=Integer.parseInt( result.get("circlesData").get(0).get("isNo").toString());
//////					
//////					imgbtn_join.setImageResource(isNo==0?R.drawable.circle_postlist_join:R.drawable.circle_postlist_exit);
//////					
//////					if(result.get("nCountData").size() != 0){
//////						tv_ncount.setText((String) result.get("nCountData").get(0)
//////								.get("count"));
//////					}
//////					if(result.get("uCountData").size() != 0){
//////						tv_ucount.setText((String) result.get("uCountData").get(0)
//////								.get("count"));
//////					}
//////				}
//////			}
//////
//////		}.execute(0);
//////	};
//////
//////	/** 初始化ViewPager */
//////	private void initViewPager() {
//////		pageLists = new ArrayList<BasePager>();
//////		pageLists.add(new AllPostListPage(getActivity(), map));
//////		pageLists.add(new FinePostListPager(getActivity(), map));
//////		pageLists.add(new HotPostListPager(getActivity(), map));
//////
//////		pageLists.get(0).initData(); // 第一次进来时加载数据
//////
//////		content_pager.setOffscreenPageLimit(2); // 设置预加载页面
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
//////		textView1.setOnClickListener(new MyOnClickListener(0));
//////		textView2.setOnClickListener(new MyOnClickListener(1));
//////		textView3.setOnClickListener(new MyOnClickListener(2));
//////	}
//////
//////	/* 页卡切换监听 */
//////	public class MyOnPageChangeListener implements OnPageChangeListener {
//////
//////		@Override
//////		public void onPageSelected(int arg0) {
//////
//////			pageLists.get(arg0).initData();
//////			setTextTitleSelectedColor(arg0);
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
//////		for (int i = 0; i < 5; i++) {
//////			TextView tv = (TextView) ll.getChildAt(i);
//////			if (((arg0 == 0) && (i == 0)) || ((arg0 == 1) && (i == 2))
//////					|| ((arg0 == 2) && (i == 4))) {
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
//////		}
//////	}
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
//////			container.removeView((View) object);
//////		}
//////
//////		@Override
//////		public boolean isViewFromObject(View arg0, Object arg1) {
//////			return arg0 == arg1;
//////		}
//////	}
//////
//////	private void addCircle() {
//////		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) context,
//////				R.string.wait) {
//////
//////			@Override
//////			protected ReturnInfo doInBackground(FragmentActivity context,
//////					String... params) throws Exception {
//////				return ComModel2.addCircle(context,
//////						(String) map.get("circle_id"));
//////			}
//////
//////			@Override
//////			protected boolean isHandleException() {
//////				return true;
//////			}
//////			
//////			@Override
//////			protected void onPostExecute(FragmentActivity context,
//////					ReturnInfo result, Exception e) {
//////				super.onPostExecute(context, result, e);
//////				if(null == e){
//////				if ("1".equals(result.getStatus())) {
//////					ToastUtil.showShortText(context, "成功加入圈子");
//////					imgbtn_join.setImageResource(R.drawable.circle_postlist_exit);
//////					tv_ucount.setText(Integer.parseInt(tv_ucount.getText().toString())+1+"");
//////					isNo=1;
//////				}
//////				}
//////			}
//////
//////		}.execute((String) map.get("circle_id"));
//////	}
//////
//////	@Override
//////	public void onClick(View v) {
//////		Intent intent;
//////		switch (v.getId()) {
//////		case R.id.img_back: // 返回
//////			mFinish.finish();
//////			break;
//////
//////		case R.id.imgbtn_join: // 加入圈子
//////			//
//////			if(!YJApplication.instance.isLoginSucess()){
//////				
//////				if(loginDialog==null){
//////					loginDialog=new ToLoginDialog(context);
//////				}
//////				loginDialog.show();
//////				loginDialog.setRequestCode(3211);
//////				return;
//////			}
//////			
//////			if(isNo==0){
//////				addCircle();
//////			}else{
//////				exitCircle();
//////			}
//////			
//////			break;
//////		case R.id.img_user: // 进入圈子详情
//////			intent = new Intent(context, CircleDetailNewActivity.class);
//////			intent.putExtra("item", (Serializable) saveResult);
//////			startActivity(intent);
//////			break;
//////		case R.id.img_right_icon: // 发表话题 即发帖
//////			if(!YJApplication.instance.isLoginSucess()){
//////				
//////				if(loginDialog==null){
//////					loginDialog=new ToLoginDialog(context);
//////				}
//////				loginDialog.show();
//////				loginDialog.setRequestCode(3211);
//////				return;
//////			}
//////			intent = new Intent(context, PublishTopicActivity.class);
//////			intent.putExtra("circle_id", (String) map.get("circle_id"));
//////			intent.putExtra("tags",tags);
//////			startActivity(intent);
//////			getActivity().finish();
//////			break;
//////
//////		default:
//////			break;
//////		}
//////	}
//////	
//////	
//////	private void exitCircle(){
//////		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){
//////
//////			@Override
//////			protected ReturnInfo doInBackground(FragmentActivity context,
//////					String... params) throws Exception {
//////				return ComModel2.exitCircle(context, (String)map.get("circle_id"));
//////			}
//////
//////			@Override
//////			protected boolean isHandleException() {
//////				return true;
//////			}
//////			
//////			@Override
//////			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
//////				super.onPostExecute(context, result, e);
//////				if(null == e){
//////				if("1".equals(result.getStatus())){
//////					ToastUtil.showShortText(context, "成功退出圈子");
//////					imgbtn_join.setImageResource(R.drawable.circle_postlist_join);
//////					tv_ucount.setText(Integer.parseInt(tv_ucount.getText().toString())-1+"");
//////					isNo=0;
//////				}
//////				}
//////			}
//////			
//////		}.execute((String)map.get("circle_id"));
//////	}
//////	
////}
//=======
//package com.yssj.ui.fragment.circles;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.yssj.YJApplication;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.RoundImageButton;
//import com.yssj.custom.view.ToLoginDialog;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.circles.CircleDetailNewActivity;
//import com.yssj.ui.activity.circles.PublishTopicActivity;
//import com.yssj.ui.base.BaseFragment;
//import com.yssj.ui.base.BasePager;
//import com.yssj.ui.pager.AllPostListPage;
//import com.yssj.ui.pager.FinePostListPager;
//import com.yssj.ui.pager.HotPostListPager;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ToastUtil;
//
///**
// * 帖子列表
// * 
// * @author Administrator
// * 
// */
//@SuppressLint("NewApi")
//public class CirclePostListFragment extends BaseFragment implements
//		OnClickListener {
//	private String tags;
//	private HashMap<String, Object> map;
//	private ViewPager content_pager;
//	private LinearLayout ll;
//	private List<BasePager> pageLists;
//	private TextView textView1, textView2, textView3;
//	private int currIndex = 0;// 当前页卡编号
//
//	private LinearLayout ll_head;
//	private TextView tvTitle_base;
//	private LinearLayout img_back;
//
//	private RelativeLayout rl_head_bg;
//	private ImageView img_bg;
//	private RoundImageButton img_user;
//	private TextView tv_title, tv_admin, tv_ncount, tv_ucount;
//	private ImageButton imgbtn_join;
//	private ImageView img_right_icon;
//	private ImageButton imgbtn_left_icon;
//	
//	private ToLoginDialog loginDialog;
//	
//	private int isNo=0;//是否加入圈子0表示没加入
//	
//	private Map<String, List<HashMap<String, Object>>> saveResult;
//
//	public CirclePostListFragment(HashMap<String, Object> map) {
//		this.map = map;
//		System.out.println("map数据集：" + map.toString());
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//	}
//
//	public CirclePostListFragment() {
//	}
//
//	private CirClePostFinish mFinish;
//
//	public interface CirClePostFinish {
//		void finish();
//	}
//
//	public void setOnFinish(Activity mActivity) {
//		this.mFinish = (CirClePostFinish) mActivity;
//	}
//
//	@Override
//	public View initView() {
//		view = View.inflate(context, R.layout.activity_circle_post_list, null);
//		ll_head = (LinearLayout) view.findViewById(R.id.ll_head);
//		ll_head.setBackgroundColor(Color.TRANSPARENT);
//		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("帖子列表");
//		tvTitle_base.setTextColor(getResources().getColor(R.color.white));
//		img_back = (LinearLayout) view.findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//
//		imgbtn_left_icon = (ImageButton) view
//				.findViewById(R.id.imgbtn_left_icon);
//		
//		imgbtn_left_icon.setBackgroundResource(R.drawable.white_return_back_icon);
//
//		ll = (LinearLayout) view.findViewById(R.id.ll);
//		content_pager = (ViewPager) view.findViewById(R.id.content_pager);
//
//		textView1 = (TextView) view.findViewById(R.id.textView1);
//		textView2 = (TextView) view.findViewById(R.id.textView2);
//		textView3 = (TextView) view.findViewById(R.id.textView3);
//
//		rl_head_bg = (RelativeLayout) view.findViewById(R.id.rl_head_bg);
//		rl_head_bg.setOnClickListener(this);
//		img_bg = (ImageView) view.findViewById(R.id.img_bg);
//		img_user = (RoundImageButton) view.findViewById(R.id.img_user);
//		img_user.setOnClickListener(this);
//		tv_title = (TextView) view.findViewById(R.id.tv_title);
//		tv_admin = (TextView) view.findViewById(R.id.tv_admin);
//		tv_ncount = (TextView) view.findViewById(R.id.tv_ncount);
//		tv_ucount = (TextView) view.findViewById(R.id.tv_ucount);
//
//		/*
//		 * 右上角发表话题的按钮
//		 */
//		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
//		img_right_icon.setVisibility(View.VISIBLE);
//		img_right_icon.setImageResource(R.drawable.circle_public_post_icon);
//		img_right_icon.setOnClickListener(this);
//
//		// 加入圈子
//		imgbtn_join = (ImageButton) view.findViewById(R.id.imgbtn_join);
//		imgbtn_join.setOnClickListener(this);
//
//		return view;
//		
//	}
//
//	@Override
//	public void initData() {
//		initViewPager();
//		initTextView();
//
//		new SAsyncTask<Integer, Void, Map<String, List<HashMap<String, Object>>>>(
//				(FragmentActivity) context, null) {
//
//			@Override
//			protected Map<String, List<HashMap<String, Object>>> doInBackground(
//					FragmentActivity context, Integer... params)
//					throws Exception {
//				if(YJApplication.instance.isLoginSucess()){
//					return ComModel2.getPostList(context,
//							(String) map.get("circle_id"), 0, "true", 0, 0);
//				}else{
//					return ComModel2.getPostList2(context,
//							(String) map.get("circle_id"), 0, "true", 0, 0);
//				}
//				
//			}
//
//			@Override
//			protected void onPostExecute(
//					Map<String, List<HashMap<String, Object>>> result) {
//				super.onPostExecute(result);
//				saveResult = result;
//				
//				if (result != null) {
//					tags = (String) result.get("circlesData").get(0).get("tag");
//					SetImageLoader.initImageLoader(
//							context,
//							img_bg,
//							(String) result.get("circlesData").get(0)
//									.get("bg_pic"),"");
//					SetImageLoader.initImageLoader(context, img_user,
//							(String) result.get("circlesData").get(0)
//									.get("pic"),"");
//					
//					tv_title.setText((String) result.get("circlesData").get(0)
//							.get("title"));
//					
//					tv_admin.setText("管理员:"
//							+ (String) result.get("circlesData").get(0)
//									.get("admin"));
//					
//					isNo=Integer.parseInt( result.get("circlesData").get(0).get("isNo").toString());
//					
//					imgbtn_join.setImageResource(isNo==0?R.drawable.circle_postlist_join:R.drawable.circle_postlist_exit);
//					
//					if(result.get("nCountData").size() != 0){
//						tv_ncount.setText((String) result.get("nCountData").get(0)
//								.get("count"));
//					}
//					if(result.get("uCountData").size() != 0){
//						tv_ucount.setText((String) result.get("uCountData").get(0)
//								.get("count"));
//					}
//				}
//			}
//
//		}.execute(0);
//	};
//
//	/** 初始化ViewPager */
//	private void initViewPager() {
//		pageLists = new ArrayList<BasePager>();
//		pageLists.add(new AllPostListPage(getActivity(), map));
//		pageLists.add(new FinePostListPager(getActivity(), map));
//		pageLists.add(new HotPostListPager(getActivity(), map));
//
//		pageLists.get(0).initData(); // 第一次进来时加载数据
//
//		content_pager.setOffscreenPageLimit(2); // 设置预加载页面
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
//		textView1.setOnClickListener(new MyOnClickListener(0));
//		textView2.setOnClickListener(new MyOnClickListener(1));
//		textView3.setOnClickListener(new MyOnClickListener(2));
//	}
//
//	/* 页卡切换监听 */
//	public class MyOnPageChangeListener implements OnPageChangeListener {
//
//		@Override
//		public void onPageSelected(int arg0) {
//
//			pageLists.get(arg0).initData();
//			setTextTitleSelectedColor(arg0);
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
//	/** 设置标题文本的颜色 **/
//	private void setTextTitleSelectedColor(int arg0) {
//		for (int i = 0; i < 5; i++) {
//			TextView tv = (TextView) ll.getChildAt(i);
//			if (((arg0 == 0) && (i == 0)) || ((arg0 == 1) && (i == 2))
//					|| ((arg0 == 2) && (i == 4))) {
//				tv.setTextColor(getResources().getColor(R.color.pink_color));
//			} else {
//				tv.setTextColor(Color.BLACK);
//			}
//		}
//	}
//
//	/* 标题点击监听 */
//	private class MyOnClickListener implements OnClickListener {
//		private int index = 0;
//
//		public MyOnClickListener(int i) {
//			index = i;
//		}
//
//		public void onClick(View v) {
//			content_pager.setCurrentItem(index);
//		}
//	}
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
//			container.removeView((View) object);
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			return arg0 == arg1;
//		}
//	}
//
//	private void addCircle() {
//		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) context,
//				R.string.wait) {
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context,
//					String... params) throws Exception {
//				return ComModel2.addCircle(context,
//						(String) map.get("circle_id"));
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					ReturnInfo result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if(null == e){
//				if ("1".equals(result.getStatus())) {
//					ToastUtil.showShortText(context, "成功加入圈子");
//					imgbtn_join.setImageResource(R.drawable.circle_postlist_exit);
//					tv_ucount.setText(Integer.parseInt(tv_ucount.getText().toString())+1+"");
//					isNo=1;
//				}
//				}
//			}
//
//		}.execute((String) map.get("circle_id"));
//	}
//
//	@Override
//	public void onClick(View v) {
//		Intent intent;
//		switch (v.getId()) {
//		case R.id.img_back: // 返回
//			mFinish.finish();
//			break;
//
//		case R.id.imgbtn_join: // 加入圈子
//			//
//			if(!YJApplication.instance.isLoginSucess()){
//				
//				if(loginDialog==null){
//					loginDialog=new ToLoginDialog(context);
//				}
//				loginDialog.show();
//				loginDialog.setRequestCode(3211);
//				return;
//			}
//			
//			if(isNo==0){
//				addCircle();
//			}else{
////				exitCircle();
//			}
//			
//			break;
//		case R.id.img_user: // 进入圈子详情
//			intent = new Intent(context, CircleDetailNewActivity.class);
//			intent.putExtra("item", (Serializable) saveResult);
//			startActivity(intent);
//			break;
//		case R.id.img_right_icon: // 发表话题 即发帖
//			if(!YJApplication.instance.isLoginSucess()){
//				
//				if(loginDialog==null){
//					loginDialog=new ToLoginDialog(context);
//				}
//				loginDialog.show();
//				loginDialog.setRequestCode(3211);
//				return;
//			}
//			intent = new Intent(context, PublishTopicActivity.class);
//			intent.putExtra("circle_id", (String) map.get("circle_id"));
//			intent.putExtra("tags",tags);
//			startActivity(intent);
//			getActivity().finish();
//			break;
//
//		default:
//			break;
//		}
//	}
//	
//	
////	private void exitCircle(){
////		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){
////
////			@Override
////			protected ReturnInfo doInBackground(FragmentActivity context,
////					String... params) throws Exception {
////				return ComModel2.exitCircle(context, (String)map.get("circle_id"));
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////			
////			@Override
////			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
////				super.onPostExecute(context, result, e);
////				if(null == e){
////				if("1".equals(result.getStatus())){
////					ToastUtil.showShortText(context, "成功退出圈子");
////					imgbtn_join.setImageResource(R.drawable.circle_postlist_join);
////					tv_ucount.setText(Integer.parseInt(tv_ucount.getText().toString())-1+"");
////					isNo=0;
////				}
////				}
////			}
////			
////		}.execute((String)map.get("circle_id"));
////	}
//	
//}
//>>>>>>> .r26813
