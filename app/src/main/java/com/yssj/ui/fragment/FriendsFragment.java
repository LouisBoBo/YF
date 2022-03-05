package com.yssj.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//import com.easemob.chat.EMChatManager;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.MyMatchTitleView;
import com.yssj.custom.view.MyMatchTitleView.OnCheckTitleLentener;
import com.yssj.custom.view.MyMatchTitleView.OnCheckTitleLentener2;
import com.yssj.custom.view.ScrollPagerIntimateList;
import com.yssj.custom.view.ScrollPagerIntimateList.MyOnRefreshLintener;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.H5Activity2;
import com.yssj.ui.activity.MessageCenterActivity;
import com.yssj.ui.activity.circles.SweetFriendsDetails;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.picselect.PicSelectActivity;
import com.yssj.ui.activity.shopdetails.MatchDetailsActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.activity.shopdetails.SpecialTopicDeatilsActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.YCache;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FriendsFragment extends Fragment implements OnClickListener, MyOnRefreshLintener, OnCheckTitleLentener
				,OnCheckTitleLentener2{
	
	private boolean tongJiFirst = true;
	public static ImageView mIntimateFabuIcon;
	private View newsTipsView,newsTipsDot;
	private View newsAlertView,newsAlertCloseView;
	private TextView newsAlertTv;
	private int newsTips;//收到的消息（铃铛）（话题）
	private int newsTipsCount;//收到的消息条数（话题）
	private int mMessageCount;//收到新的聊天消息数（聊天）
	private int mMessageCountUnRead;//收到新的聊天消息数（聊天未读消息）
	private int mMessageNumCount;//收到新的聊天消息数（聊天）
	private Timer mNewsTimer;//接收消息定时器

	private View rl_sign;
	private LinearLayout redShare;
	private ImageView moneyShare;
	private boolean  mIsSingLiulan;//浏览分钟任务 单独界面 不是首页
	public static FriendsFragment newInstance(Context context) {
		FriendsFragment fragment = new FriendsFragment();
//		mContext = context;
		Bundle args = new Bundle();
		args.putBoolean("isSingLiulan", false);
		fragment.setArguments(args);
		return fragment;
	}


	public static FriendsFragment newInstance(Context context,boolean isSingLiulan) {
		FriendsFragment fragment = new FriendsFragment();
//		mContext = context;
		Bundle args = new Bundle();
		args.putBoolean("isSingLiulan", isSingLiulan);
		fragment.setArguments(args);
		return fragment;
	}

	// 主界面----密友圈
	private Context mContext;

	private List<HashMap<String, String>> listTitle = new ArrayList<HashMap<String, String>>();
	private ViewPager mViewPager;

	private MyMatchTitleView title;
	private IntimateCircleFragment intimateHuatiFragment;// 话题广场
	private IntimateCircleFragment intimateCircleFragment;// 蜜友

//	public static int width;
//
//	public static int height;


//	private IntimateSlideShowView imageViewPager;
	private LinearLayout dot_layout;
	private ViewPager viewPager;
	private RelativeLayout friends_rl;

	private List<HashMap<String, String>> pagerList;


	public ScrollPagerIntimateList mView;

	public Timer timer;

	private int currentPosition = 0;


	private PagerAdapterMy mAdapter;


	public static String liulan = "";


	public interface onSearchListener {
		void onSearch();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mIsSingLiulan = (Boolean) getArguments().get("isSingLiulan");
		mContext = getActivity();
		return inflater.inflate(R.layout.fragment_friends, container, false);

	}



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mIntimateFabuIcon = (ImageView) getView().findViewById(R.id.intimate_fabu_btn_iv);
		mIntimateFabuIcon.setOnClickListener(this);
		newsTipsView = getView().findViewById(R.id.news_tips_rl);
		newsTipsDot = getView().findViewById(R.id.news_tips_dot);
		newsAlertView = getView().findViewById(R.id.friends_news_alert);
		newsAlertCloseView = getView().findViewById(R.id.friends_news_alert_close);
		newsAlertTv = (TextView) getView().findViewById(R.id.friends_news_alert_tv);
		rl_sign = getView().findViewById(R.id.rl_sign);
		rl_sign.setOnClickListener(this);
		friends_rl = (RelativeLayout) getView().findViewById(R.id.friends_rl);

		if(!GuideActivity.hasSign){
			rl_sign.setVisibility(View.GONE);
		}


		if(mIsSingLiulan){
			friends_rl.setVisibility(View.GONE);
		}else{
			friends_rl.setVisibility(View.VISIBLE);
		}



		redShare = (LinearLayout) getView().findViewById(R.id.red_share_ll);
		moneyShare = (ImageView) getView().findViewById(R.id.money_share_iv);
		newsAlertView.setVisibility(View.GONE);
		newsTipsDot.setVisibility(View.GONE);
		newsAlertTv.setOnClickListener(this);
		newsAlertCloseView.setOnClickListener(this);
		newsTipsView.setOnClickListener(this);
		getView().setBackgroundColor(Color.WHITE);
		
		setZhuanIconAnim();
		
//		DisplayMetrics dm = new DisplayMetrics();
//		((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//		width = dm.widthPixels;
//		height = dm.heightPixels;

		mView = (ScrollPagerIntimateList) getView().findViewById(R.id.myView);
		mView.setOnRefreshLintener(this);


		HashMap<String, String> topicHashMap = new HashMap<String, String>();
		topicHashMap.put("is_show", "1");
		topicHashMap.put("sort_name", "话题广场");
		listTitle.add(0, topicHashMap);

		HashMap<String, String> intimateHashMap = new HashMap<String, String>();
		intimateHashMap.put("is_show", "1");
		intimateHashMap.put("sort_name", "蜜友");
		listTitle.add(1, intimateHashMap);


		mViewPager = (ViewPager) getView().findViewById(R.id.content_viewpager);

		title = (com.yssj.custom.view.MyMatchTitleView) getView().findViewById(R.id.title);
//		imageViewPager = (IntimateSlideShowView) getView().findViewById(R.id.image_view_pager);
		// ----------------------新轮播图-------------------------------------------------
		dot_layout = (LinearLayout) getView().findViewById(R.id.dot_layout);
		viewPager = (ViewPager) getView().findViewById(R.id.viewPager);
//		getTuijianData();
		getBanner(false);
		
		title.setIntimateTitle(true);
		title.setData(listTitle);
		title.setCheckLintener(this);
		title.setCheckLintener2(this);
		intimateHuatiFragment = IntimateCircleFragment.newInstances(listTitle.get(0).get("sort_name"), mContext, "1");
		intimateCircleFragment = IntimateCircleFragment.newInstances(listTitle.get(1).get("sort_name"), mContext, "2");
		mAdapter = new PagerAdapterMy(getChildFragmentManager());

		mViewPager.setAdapter(mAdapter);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				
				String id = listTitle.get(arg0).get("_id");
				String sort_name = listTitle.get(arg0).get("sort_name");


				title.setPosition(arg0);
				currentPosition = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		
		updateUnreadLabel();
//		EMChatManager.getInstance().registerEventListener(new EMEventListener() {
//
//			@Override
//			public void onEvent(EMNotifierEvent event) {
//
//				switch (event.getEvent()) {
//				case EventNewMessage: // 接收新消息
//				{
//					event.getData();
//					// 提示新消息
//					updateUnreadLabel();
//					break;
//				}
//				case EventDeliveryAck: {// 接收已发送回执
//					// 提示新消息
//
//					updateUnreadLabel();
//					break;
//				}
//
//				case EventNewCMDMessage: {// 接收透传消息
//
//					updateUnreadLabel();
//					break;
//				}
//
//				case EventReadAck: {// 接收已读回执
//
//					updateUnreadLabel();
//					break;
//				}
//
//				case EventOfflineMessage: {// 接收离线消息
//					event.getData();
//
//					updateUnreadLabel();
//					break;
//				}
//
//				case EventConversationListChanged: {// 通知会话列表通知event注册（在某些特殊情况，SDK去删除会话的时候会收到回调监听）
//
//					updateUnreadLabel();
//					break;
//				}
//
//				default:
//					break;
//				}
//			}
//
//		});
	}
	
	private Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 让ViewPager选中下一页
			if(options.size() >1 ){
				viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
				handler2.sendEmptyMessageDelayed(0, 6000);
			}
//			LogYiFu.e("tag", "sendEmptyMessageDelayed");
		};
	};

	/**
	 * 更新文本和点
	 */
	protected void updateTextAndDot(boolean isRefresh) {
		int currentItem = viewPager.getCurrentItem() % options.size();// 获取当前选中的page

		// 如果当前的currentItem和点的位置相同，点设置为白色，否则是黑色
		for (int i = 0; i < dot_layout.getChildCount(); i++) {
			View childView = dot_layout.getChildAt(i);
			childView.setBackgroundResource(
					currentItem == i ? R.drawable.img_round_checked : R.drawable.img_round_default);

		}
	}

	/**
	 * 添加所有的点
	 */
	private void initDotLayout(boolean isRefresh) {

		if (isRefresh) {// 下拉刷新
			dot_layout.removeAllViews();
		}

		for (int i = 0; i < options.size(); i++) {
			View dotView = new View(mContext);
			LayoutParams params = new LayoutParams(15, 15);
			if (i > 0) {
				params.leftMargin = 15;
			}
			dotView.setLayoutParams(params);// 设置宽高参数

			// 将点加入到dot_layout中
			dot_layout.addView(dotView);
		}

	}
	@Override
	public void onPause() {
		super.onPause();
		if("108".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))){
			TongJiUtils.TongJi(mContext, 108+"");
			LogYiFu.e("TongJiNew", 108+"");
			
			Long nowTimesEnd = System.currentTimeMillis();
			Long nowTimesStart = Long.parseLong(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_TIMES_INTIMATE, nowTimesEnd+""));
			Long duration = (nowTimesEnd - nowTimesStart)/1000;//以 秒 为单位
			TongJiUtils.TongJiDuration(mContext, 1008+"",duration+"");
			LogYiFu.e("TongJiNew", duration+"秒"+1008);
		}
		if(mNewsTimer!=null){
			mNewsTimer.cancel();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if("108".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))
				&&!tongJiFirst ){
			SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "108");
			TongJiUtils.TongJi(mContext, 8+"");
			LogYiFu.e("TongJiNew", 8+"");
			Long nowTimes  = System.currentTimeMillis();
			SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TIMES_INTIMATE, ""+nowTimes);
		}
		tongJiFirst = false;//首次进来时候 在点击首页下面五个按钮时候统计 所以首次这里不统计避免重复
		String userId="";	
		if(YJApplication.instance.isLoginSucess()){
			userId = YCache.getCacheUser(mContext).getUser_id()+"";
			if(!userId.equals(SharedPreferencesUtil.getStringData(mContext, "IntimateUserId", ""))){//切换账号之后 密友列表数据需要刷新
				IntimateCircleFragment inCF = (IntimateCircleFragment) mAdapter.getItem(1);
				inCF.refresh();
				IntimateCircleFragment inCF0 = (IntimateCircleFragment) mAdapter.getItem(0);
				inCF0.refresh();
				newsTipsDot.setVisibility(View.GONE);
				newsAlertView.setVisibility(View.GONE);
				SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE, "0");
				SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE_COUNT, "0");
				SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE_CHAT, "0");
				SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE_COUNT_CHAT, "0");
			}
			SharedPreferencesUtil.saveStringData(mContext, "IntimateUserId", userId);
		}
		
		if(!YJApplication.instance.isLoginSucess()){
			if(!"".equals(SharedPreferencesUtil.getStringData(mContext, "IntimateUserId", ""))){//从登录状态 转换为非登陆状态
				IntimateCircleFragment inCF0 = (IntimateCircleFragment) mAdapter.getItem(0);
				inCF0.refresh();
				IntimateCircleFragment inCF = (IntimateCircleFragment) mAdapter.getItem(1);
				inCF.setListVisibility();
			}
			newsTipsDot.setVisibility(View.GONE);
			newsAlertView.setVisibility(View.GONE);
			SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE, "0");
			SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE_COUNT, "0");
			SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE_CHAT, "0");
			SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE_COUNT_CHAT, "0");
			SharedPreferencesUtil.saveStringData(mContext, "IntimateUserId", userId);
			return;//用户  没有登录 直接返回
		}
		if(newsAlertView.getVisibility()==View.VISIBLE){
			setTopViewHeigth(true);
		}else{
			setTopViewHeigth(false);
		}
		//计时器监听 新消息  没有登录 直接返回
		if(mNewsTimer!=null){
			mNewsTimer.cancel();
		}
		mNewsTimer = new Timer();
		mNewsTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				mMessageCount = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, Pref.ALL_MESSAGE_CHAT, "0"));
				mMessageNumCount = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, Pref.ALL_MESSAGE_COUNT_CHAT, "0"));
				newsTips = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, Pref.ALL_MESSAGE, "0"));
				newsTipsCount = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, Pref.ALL_MESSAGE_COUNT, "0"));
				((Activity) mContext).runOnUiThread(new Runnable() {
					public void run() {
						if(newsTips>0||mMessageCount>0){
							newsTipsDot.setVisibility(View.VISIBLE);
						}else{
							newsTipsDot.setVisibility(View.GONE);
							newsAlertView.setVisibility(View.GONE);
							setTopViewHeigth(false);
						}
						if(newsTipsCount>0||mMessageNumCount>0){
							newsAlertTv.setText("收到"+(mMessageNumCount+newsTipsCount)+"条新消息");
							newsAlertView.setVisibility(View.VISIBLE);
							setTopViewHeigth(true);
						}else{
							newsAlertView.setVisibility(View.GONE);
							setTopViewHeigth(false);
						}
					};
				});
			}
		}, 1000, 1000);
	
	}
	//DP2SPUtil.dp2px(mContext, 180) ViewPager的高度  DP2SPUtil.dp2px(mContext, 38) 收到一条消息的 View 的高度  有偏差+1
	private void setTopViewHeigth(boolean isVisibile){
		if(isVisibile){
			ScrollPagerIntimateList.mTopViewHeight = DP2SPUtil.dp2px(mContext, 180)-DP2SPUtil.dp2px(mContext, 38)+DP2SPUtil.dp2px(mContext, 1);
		}else{
			ScrollPagerIntimateList.mTopViewHeight =DP2SPUtil.dp2px(mContext, 180)+DP2SPUtil.dp2px(mContext, 1);
		}
	}
	
	private MyAdapter adapter;
	private List<HashMap<String, String>> options = null;
	/**
	 * 获取密友圈首页 轮播图
	 *
	 */
	private void getBanner(final boolean isRefresh) {
		new SAsyncTask<Void, Void, List<HashMap<String, String>>>((FragmentActivity) mContext, R.string.wait) {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				LoadingDialog.show(getActivity());
			}

			@Override
			protected List<HashMap<String, String>> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getIntimateBanner(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, String>> result, Exception e) {
				if (null == e && result != null) {
//					pagerList = result;
//					gallList = (List<ShopOption>) result.get("centShops");
//					imageViewPager.setRefresh(true);
//					imageViewPager.setData(pagerList, mContext);

					// mGallery.setData(gallList);
					//
					// mGallery.getScroll().smoothScrollTo(0, 0);// 复位
					options = result;
					mView.refreshDone();
					if(options.size()>0){
						adapter = new MyAdapter(options);
	
						// 初始化所有的点
						initDotLayout(isRefresh);
						// 3.给ViewPager填充数据
						viewPager.setAdapter(adapter);
						// 4.设置ViewPager的page改变的监听器
						viewPager.setOnPageChangeListener(new OnPageChangeListener() {
							/**
							 * 当page的页数改变的时候会调用该方法
							 */
							@Override
							public void onPageSelected(int position) {
								// Log.e("tag", "position: "+position);
								updateTextAndDot(isRefresh);
							}
	
							/**
							 * 只要一直滑动page，就会执行
							 */
							@Override
							public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
								// Log.e("tag", "onPageScrolled: ");
							}
	
							@Override
							public void onPageScrollStateChanged(int state) {
							}
						});
	
						adapter.notifyDataSetChanged();
						// 由于一开始不会回调OnPageChangeListener是onPageSelected方法，所以需要手动初始化
						updateTextAndDot(isRefresh);
						viewPager.setCurrentItem(options.size() * 100000);// 设置ViewPager默认选中的页数
	
						if (isRefresh) {
							handler2.removeCallbacksAndMessages(null);
							handler2 = new Handler() {
								@Override
								public void handleMessage(Message msg) {
									if(options.size() >1 ){
										viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
										handler2.sendEmptyMessageDelayed(0, 6000);
									}
								}
	
							};
	
						}
						// 延时发送消息
						handler2.sendEmptyMessageDelayed(0, 6000);
					}
				}
				super.onPostExecute(context, result, e);
			}

		}.execute();
	}

	/**
	 * 此处应当继承FragmentStatePagerAdapter
	 * 在处理数据量较大的页面应当使用FragmentStatePagerAdapter，而不是FragmentPagerAdapter
	 */
	public class PagerAdapterMy extends FragmentStatePagerAdapter {
		public PagerAdapterMy(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return listTitle.get(position).get("sort_name");
		}

		@Override
		public int getCount() {
			return listTitle.size();
		}

		@Override
		public Fragment getItem(int position) {

			if (position ==1) {
				if (intimateCircleFragment == null) {
					intimateCircleFragment = IntimateCircleFragment.newInstances(listTitle.get(0).get("sort_name"), mContext, "2");
				}
				return intimateCircleFragment;
			} else {
				if (intimateHuatiFragment == null) {
					intimateHuatiFragment = IntimateCircleFragment.newInstances(listTitle.get(0).get("sort_name"), mContext, "1");
				}
				return intimateHuatiFragment;

				// ItemFragment fragment = fragments[position - 1];
				// if (fragment == null) {
				// fragment = ItemFragment.newInstances(position,
				// listTitle.get(position).get("sort_name"),
				// listTitle.get(position).get("_id"), mContext);
				// fragments[position - 1] = fragment;
				// }
				// return fragment;
			}

		}

	}

	
	class MyAdapter extends PagerAdapter {
		List<HashMap<String, String>> options;

		public MyAdapter(List<HashMap<String, String>> options) {
			this.options = options;
		}

		/**
		 * 返回多少页数
		 */
		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
			// return list.size();
		}

		/**
		 * 用来判断instantiateItem方法返回的Object是否是View对象
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		/**
		 * 相当于BaseAdapter的getView，主要用来加载View，并且给VIew填充数据
		 * 
		 * @param container
		 * @param position
		 * @return
		 */
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			// 1.加载View对象
			View view = View.inflate(mContext, R.layout.adapter_list, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.image);
			// 2.设置数据
			String url = options.get(position % options.size()).get("pic") + "!560";

			PicassoUtils.initImage(mContext, url, imageView);
			container.addView(view);

			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					HashMap<String, String> option = options.get(position % options.size());
						if (option.get("type") == null) {
						
						return;
					}
					//type(1话题，2H5链接，3商品详情，4搭配购，5专题详情),pic(图片),link(话题ID/H5链接)
					String type = option.get("type");
					
					if ("2".equals(type)) {//2跳至H5活动页
						Intent intent=new Intent(mContext,H5Activity2.class);
						intent.putExtra("h5_code", option.get("link"));
						mContext.startActivity(intent);
						((FragmentActivity)mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
					} else if ("1".equals(type)) {//1话题
						Intent intent = new Intent(mContext,SweetFriendsDetails.class);
						intent.putExtra("theme_id", option.get("link"));
						((FragmentActivity)mContext).startActivity(intent);
						((FragmentActivity)mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
					}else if("3".equals(type)){ //3商品详情
						Intent intent = new Intent(mContext,ShopDetailsActivity.class);
						intent.putExtra("code", option.get("link"));
						mContext.startActivity(intent);
						((FragmentActivity)mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
					}else if("4".equals(type)){//4搭配购
						Intent intent = new Intent(mContext, MatchDetailsActivity.class);
						intent.putExtra("collocation_code", option.get("link"));
						mContext.startActivity(intent);
						((FragmentActivity)mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
					}else if("5".equals(type)){//5专题详情
						Intent intent = new Intent(mContext, SpecialTopicDeatilsActivity.class);
						intent.putExtra("collocation_code", option.get("link"));
						mContext.startActivity(intent);
						((FragmentActivity)mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
					}
				}
			});

			return view;
		}

		/**
		 * 销毁每个page的方法，具体实现很简单：将view从ViewPager中移除
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// super.destroyItem(container, position, object);
			container.removeView((View) object);
		}
	}
	
	@Override
	public void checkTitle(View v) {
		mViewPager.setCurrentItem(v.getId());
		currentPosition = v.getId();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.intimate_fabu_btn_iv:
			if(!YJApplication.instance.isLoginSucess()){
				toLogin();
			}else{
//				MainFragment.mIntimateFaBuIcon.performClick();
				Intent intnetTopic = new Intent(mContext,PicSelectActivity.class);
				intnetTopic.putExtra("sweet_friends_issue", true);
				((FragmentActivity) mContext).startActivity(intnetTopic);
				((FragmentActivity)mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
			}
			break;
		case R.id.news_tips_rl://消息中心
		case R.id.friends_news_alert_tv://消息中心
			if(!YJApplication.instance.isLoginSucess()){
				toLogin();
				break;
			}
			Intent intent = new Intent(mContext,MessageCenterActivity.class);
			mContext.startActivity(intent);
			((FragmentActivity)mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
			if(newsTipsDot.getVisibility()==View.VISIBLE){
				newsTipsDot.setVisibility(View.GONE);
			}
			if(newsAlertView.getVisibility()==View.VISIBLE){
				newsAlertView.setVisibility(View.GONE);
				setTopViewHeigth(false);
				mView.isTopHidden = false;
			}
			SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE, "0");
			SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE_COUNT, "0");
			SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE_CHAT, "0");
			SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE_COUNT_CHAT, "0");
			break;
		case R.id.friends_news_alert_close://消息中心提示 关闭
			if(newsAlertView.getVisibility()==View.VISIBLE){
				newsAlertView.setVisibility(View.GONE);
				setTopViewHeigth(false);
				mView.isTopHidden = false;
				SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE_COUNT, "0");
				SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE_COUNT_CHAT, "0");
			}
			break;
		case R.id.rl_sign:
			// 跳至赚钱
			SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
			startActivity(new Intent(mContext, CommonActivity.class));
			((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
			redShare.clearAnimation();
			moneyShare.clearAnimation();
			SharedPreferencesUtil.saveStringData(mContext, Pref.SHAREANIMZHUNA, System.currentTimeMillis() + "");
			break;
		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		handler2.removeCallbacksAndMessages(null);
	}
	@Override
	public void onRefreshlintener() {
//		getTuijianData();
		getBanner(true);
//		if (currentPosition == 0||!YJApplication.instance.isLoginSucess()) {
//			if(!YJApplication.instance.isLoginSucess()&&currentPosition != 0){
//				mViewPager.setCurrentItem(0);
//				title.setPosition(0);
//				currentPosition = 0	;
//				toLogin();
//			}
		if (currentPosition == 0) {
			IntimateCircleFragment inCF = (IntimateCircleFragment) mAdapter.getItem(0);
			inCF.refresh();
		} else {
			IntimateCircleFragment inCF = (IntimateCircleFragment) mAdapter.getItem(1);
			inCF.refresh();
		}
	}
//	/**
//	  * 得到根Fragment
//	  * 
//	  * @return
//	  */
//	 private Fragment getRootFragment() {
//	  Fragment fragment = getParentFragment();
//	  while (fragment.getParentFragment() != null) {
//	   fragment = fragment.getParentFragment();
//	  }
//	  return fragment;
//
//	 }
//	 
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if(requestCode == 10127){
//			if(resultCode == 10128){
//				if(YJApplication.instance.isLoginSucess()){
//					IntimateCircleFragment inCF = (IntimateCircleFragment) mAdapter.getItem(1);
//					inCF.refresh();
//				}else{
//					title.setPosition(0);
//					currentPosition = 0;
//				}
//			}
//			
//		}
//	}


	@Override
	public void checkTitle2(View v) {
//			mViewPager.setCurrentItem(0);
//			title.setPosition(0);
//			currentPosition = 0;
//			toLogin();
			
	}
	/**
	 * 去登陆
	 */
	private void toLogin(){
		Intent intent = new Intent(mContext, LoginActivity.class);
		intent.putExtra("login_register", "login");
		((FragmentActivity)mContext).startActivity(intent);
		((FragmentActivity)mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
	}
	
	/**
	 * 刷新未读消息数
	 */
	private void updateUnreadLabel() {

		((Activity) mContext).runOnUiThread(new Runnable() {
			public void run() {
				// 刷新bottom bar消息未读数
				mMessageCount = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, Pref.ALL_MESSAGE_CHAT, "0"));
				mMessageCountUnRead = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, Pref.ALL_MESSAGE_CHAT_UNREAD, "0"));
				mMessageNumCount = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, Pref.ALL_MESSAGE_COUNT_CHAT, "0"));
				int numTotal = getUnreadMsgCountTotal();
				if(numTotal==0||numTotal<mMessageNumCount||numTotal<mMessageCount){
					mMessageCount = 0;
					mMessageNumCount = 0;
				}
				if(numTotal>mMessageCountUnRead){
					mMessageNumCount += numTotal-mMessageCountUnRead;
					mMessageCount += numTotal-mMessageCountUnRead;	
				}else{
					mMessageNumCount = numTotal;
					mMessageCount = numTotal;
				}
				
				mMessageCountUnRead = numTotal;
				SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE_CHAT, mMessageCount+"");
				SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE_COUNT_CHAT, mMessageNumCount+"");
				SharedPreferencesUtil.saveStringData(mContext, Pref.ALL_MESSAGE_COUNT_CHAT, mMessageCountUnRead+"");
				
				newsTips = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, Pref.ALL_MESSAGE, "0"));
				newsTipsCount = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, Pref.ALL_MESSAGE_COUNT, "0"));
				if(newsTips>0||mMessageCount>0){
					newsTipsDot.setVisibility(View.VISIBLE);
				}else{
					newsTipsDot.setVisibility(View.GONE);
					newsAlertView.setVisibility(View.GONE);
					setTopViewHeigth(false);
				}
				if(newsTipsCount>0||mMessageNumCount>0){
					newsAlertTv.setText("收到"+(mMessageNumCount+newsTipsCount)+"条新消息");
					newsAlertView.setVisibility(View.VISIBLE);
					setTopViewHeigth(true);
				}else{
					newsAlertView.setVisibility(View.GONE);
					setTopViewHeigth(false);
				}

			}
		});

	}
	/**
	 * 获取未读消息数
	 * 
	 * @return
	 */
	public int getUnreadMsgCountTotal() {
		int unreadMsgCountTotal = 0;
		int chatroomUnreadMsgCount = 0;
//		unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
		/*
		 * for(EMConversation
		 * conversation:EMChatManager.getInstance().getAllConversations
		 * ().values()){ if(conversation.getType() ==
		 * EMConversationType.ChatRoom)
		 * chatroomUnreadMsgCount=chatroomUnreadMsgCount
		 * +conversation.getUnreadMsgCount(); }
		 */
		return unreadMsgCountTotal - chatroomUnreadMsgCount;
	}
	
	/**
	 * 赚钱图标动画效果
	 */
	private void setZhuanIconAnim() {
		
		SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String shareAnim = SharedPreferencesUtil.getStringData(mContext, Pref.SHAREANIMZHUNA, "0");
		long shareAnimTime = Long.valueOf(shareAnim);
		boolean isRoate = "0".equals(shareAnim) || !df.format(new Date()).equals(df.format(new Date(shareAnimTime)));
		if (!isRoate) {
			return;
		}
		RotateAnimation ani1 = new RotateAnimation(0f,35f,Animation.RELATIVE_TO_SELF, 1.0f,Animation.RELATIVE_TO_SELF,1.0f);
		ScaleAnimation ani2 = new  ScaleAnimation(1.0f, 0.85f, 1.0f, 0.85f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		final AnimationSet set = new AnimationSet(mContext, null);
		ani1.setDuration(270);
		ani1.setRepeatMode(Animation.REVERSE);
//		ani1.setRepeatCount(1);
		ani1.setFillAfter(false);
//		ani1.setStartOffset(1500);
		ani2.setDuration(270);
		ani2.setRepeatMode(Animation.RESTART);
//		ani2.setRepeatCount(Integer.MAX_VALUE);
		ani2.setFillAfter(false);
//		ani2.setStartOffset(1500);
		
		set.addAnimation(ani1);
		set.addAnimation(ani2);
		set.setStartOffset(600);
//		redShare.setAnimation(set);
		redShare.startAnimation(set);
		
		final RotateAnimation ani3 = new RotateAnimation(-12f,10f,Animation.RELATIVE_TO_SELF, 1.0f,Animation.RELATIVE_TO_SELF,1.0f);
		ani3.setDuration(55);
		ani3.setRepeatMode(Animation.REVERSE);
		ani3.setRepeatCount(2);
		ani3.setFillAfter(true);
		final RotateAnimation ani4 = new RotateAnimation(-6f,6f,Animation.RELATIVE_TO_SELF, 1.0f,Animation.RELATIVE_TO_SELF,1.0f);
		ani4.setDuration(45);
		ani4.setRepeatMode(Animation.REVERSE);
		ani4.setRepeatCount(1);
		ani4.setFillAfter(false);
		
		ani1.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				moneyShare.startAnimation(ani3);
				set.setStartOffset(1300);
			}
		});
		ani3.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				moneyShare.startAnimation(ani4);
			}
		});
		ani4.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				redShare.startAnimation(set);
			}
		});
	
	}

}
