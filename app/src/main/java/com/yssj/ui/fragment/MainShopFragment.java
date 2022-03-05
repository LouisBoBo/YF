//package com.yssj.ui.fragment;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.yssj.YUrl;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.MyHorizontalScrollView;
//import com.yssj.custom.view.MyHorizontalScrollView.onClickLintener;
//import com.yssj.custom.view.MyScrollView;
//import com.yssj.custom.view.SlideShowView;
//import com.yssj.data.YDBHelper;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.entity.ShopOption;
//import com.yssj.model.ComModel;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.ui.activity.main.FilterConditionActivity;
//import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
//import com.yssj.ui.adpter.ColorPagerAdapter;
//import com.yssj.utils.ImageFileCache;
//import com.yssj.utils.ImageGetFromHttp;
//import com.yssj.utils.ImageMemoryCache;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.SetImageLoader.AnimateFirstDisplayListener;
//import com.yssj.utils.YCache;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.util.DisplayMetrics;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
//import android.view.animation.LinearInterpolator;
//import android.view.animation.TranslateAnimation;
//import android.widget.AbsListView;
//import android.widget.AbsListView.OnScrollListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.HorizontalScrollView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//
///***
// * 主店
// * 
// * @author Administrator
// * 
// */
//public class MainShopFragment extends Fragment implements OnClickListener,
//		OnItemClickListener ,OnScrollListener,onClickLintener{
//
//	private static final String TAB = "tab2";
//
//	private HorizontalScrollView horizotalScrollView;
//	private RadioGroup rg_nav_content;
//	private ImageView iv_nav_indicator;
//	// private AutoHeightViewPager mViewPager;
//
//	public static  int indicatorWidth;
//
//	private LayoutInflater mInflater;
//
//	private ColorPagerAdapter mAdapter;
//
//	private int currentIndicatorLeft = 0;
//
//	// private NavigationDrawerFragment mNavigationDrawerFragment;
//
//	private YDBHelper helper;
//	private List<HashMap<String, String>> listTitle;
//
//	private MyScrollView lazy_scroll;
//
//	private FragmentManager fm;
//	private FragmentTransaction ft;
//
//	private SlideShowView ad_pager;
//
//	private ImageView img_1, img_2, img_3;
//
//	private View v;
//
//	private List<ShopOption> topOptions;
//	private List<ShopOption> centerOptions;
//
//	// 下载缓存 将用
//	private ImageMemoryCache memoryCache;
//	private ImageFileCache fileCache;
//
//	private ListView mLv;
//	private List<String> imagePaths;
//
//	//private MainFootAdapter mFAdapter;
//
//	private Bitmap bm;
//
//	protected final String PAGESIZE ="10";
//
//	private LinearLayout llMainFoot;
//
//	private float footX;
//
//	private float footY;
//	private int mType=1;
//	private int index = 1;
//	private int currentPagers = 0;
//	private boolean isEnd =false;
//	private LinkedList<HashMap<String, Object>> dataList;
//	
//	private HorizontalScrollView sc_image;
//	private LinearLayout img_container;
//	private static Context mContext;
//	
//	private PullToRefreshListView myListView;
//	
//	private DateAdapter mDataAdapter;
//	
//	private int position=0;
//	
//	private MyHorizontalScrollView title;
//	
//	public static int mIndex=0;//当前位置
//	
//	public static int leftX=0;//导航栏滑动左坐标
//
//	public static MainShopFragment newInstance(String title, Context context) {
//		MainShopFragment fragment = new MainShopFragment();
//		Bundle args = new Bundle();
//		args.putString(TAB, title);
//		mContext = context;
//		fragment.setArguments(args);
//		return fragment;
//
//	}
//
//	@Override
//	public void onAttach(Activity activity) {
//
//		super.onAttach(activity);
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		helper = new YDBHelper(mContext);
//		String sql = "select * from sort_info where p_id = 0 and is_show =1 order by sequence ";
//		listTitle = helper.query(sql);
//
//		memoryCache = new ImageMemoryCache(mContext);
//		fileCache = new ImageFileCache();
//		imagePaths = new ArrayList<String>();
//		sp = mContext.getSharedPreferences("YSSJ_yf", 0);
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		v = inflater.inflate(R.layout.main_shop_fragment, container, false);
//		TextView tv = (TextView) v.findViewById(R.id.tv_title);
//		tv.setText("主店");
//
//		v.findViewById(R.id.img_btn_search).setOnClickListener(this);
//		v.findViewById(R.id.img_btn_filter).setOnClickListener(this);
//
//		horizotalScrollView = (HorizontalScrollView) v.findViewById(R.id.mHsv);
//		rg_nav_content = (RadioGroup) v.findViewById(R.id.rg_nav_content);
//		iv_nav_indicator = (ImageView) v.findViewById(R.id.iv_nav_indicator);
//		// mViewPager = (AutoHeightViewPager) v.findViewById(R.id.mViewPager);
//
//		//ad_pager = (SlideShowView) v.findViewById(R.id.ad_pager);
//		lazy_scroll = (MyScrollView) v.findViewById(R.id.lazy_scroll);
//		
//		sc_image = (HorizontalScrollView) v.findViewById(R.id.sc_image);
//		
//		img_container = (LinearLayout) v.findViewById(R.id.img_container);
//		
//		myListView=(PullToRefreshListView) v.findViewById(R.id.myDataList);
//		
//		myListView.setMode(Mode.BOTH);
//		myListView.getLoadingLayoutProxy(false, true).setReleaseLabel("正在加载....");  
//		myListView.getRefreshableView().setSelector(R.color.transparenct);
//		
//		myListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//
//			@Override
//			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//				mType=1;
//				index=0;
//				initData(position,index+"");
//			}
//
//			@Override
//			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//				mType=2;
//				index++;
//				initData(position,index+"");
//			}
//		});
//		
//		/*img_1 = (ImageView) v.findViewById(R.id.img_1);
//		img_1.setOnClickListener(this);
//		img_2 = (ImageView) v.findViewById(R.id.img_2);
//		img_2.setOnClickListener(this);
//		img_3 = (ImageView) v.findViewById(R.id.img_3);
//		img_3.setOnClickListener(this);*/
//		v.setVisibility(View.GONE);
//		//getFootListview();
//		initView();
//		//setListener();
//		getTuijianData();
//		
//		mIndex=0;
//		leftX=0;
//		
//		title=(MyHorizontalScrollView) v.findViewById(R.id.image_title);
//		title.setList(listTitle, false);
//		title.setOnClickLintener(this);
//		myListView.getRefreshableView().setOnScrollListener(new OnScrollListener() {
//			
//			@Override
//			public void onScrollStateChanged(AbsListView arg0, int arg1) {
//				
//			}
//			
//			@Override
//			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
//				if(arg1>=2){
//					title.setVisibility(View.VISIBLE);
//					title.setIndex(mIndex);
//				}else{
//					title.setVisibility(View.INVISIBLE);
//				}
//			}
//		});
//		return v;
//	}
//	
//	private int getScrollY(AbsListView view) throws Exception {
//		View c = view.getChildAt(0);
//		if (c == null) {
//			throw new Exception();
//		}
//
//		int firstVisiblePosition = view.getFirstVisiblePosition();
//		int top = c.getTop();
//
//		int headerHeight = 0;
//		if (firstVisiblePosition == 2) {
//			headerHeight = c.getHeight();
//		}
//
//		return -top + firstVisiblePosition * c.getHeight() + headerHeight;
//	}
//	
//	
//	// 足迹列表
//	private void getFootListview() {
//		footData = new LinkedList<HashMap<String, Bitmap>>();
//		llMainFoot = (LinearLayout) v.findViewById(R.id.ll_main_foot);
//		footX = llMainFoot.getX();
//		footY = llMainFoot.getY();
//		mLv = (ListView) v.findViewById(R.id.mlv);
////		mFAdapter = new MainFootAdapter(mContext);
////		mLv.setAdapter(mFAdapter);
//		v.findViewById(R.id.ibtn_foot).setOnClickListener(this);
//		mLv.setOnItemClickListener(this);
//		mLv.setOnScrollListener(this);
//	}
//
//	// 得到足迹数据
//	private void initData(String index) {
//
//		
//		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(
//				(FragmentActivity)mContext, 0) {
//
//	
//
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//
//				return ComModel.queryMyStepsList(context, params[0], YCache
//						.getCacheStore(mContext).getS_code(), "", PAGESIZE );
//			}
//
//			@Override
//			protected void onPostExecute(List<HashMap<String, Object>> result) {
//				super.onPostExecute(result);
//				imagePaths.clear();
//				if(dataList==null){
//					dataList =  new LinkedList<HashMap<String,Object>>();
//				}
//				if (result != null) {
//					/*
//					 * for (int i = 0; i < result.size(); i++) { HashMap<String,
//					 * Object> map = result.get(i); imagePaths.add(YUrl.imgurl +
//					 * map.get("def_pic")); } MyLogYiFu.e("TAG",
//					 * "imagePaths = "+imagePaths.toString()); getData();
//					 */
//					if(mType==1){
//						dataList.clear();
//						dataList.addAll(result);
//					}else {
//						dataList.addAll(result);
//					}
//					if(result.size()<10||currentPagers==50){
//						isEnd = true;
//					}
//					currentPagers+=result.size();
//					LogYiFu.i("TAG", "dataList="+dataList.toString());
//					//mFAdapter.setData(dataList);
//				}
//			}
//
//		}.execute(index);
//	}
//
////	public static void setOnSearchListener(Activity mActivity) {
////		searchListener = (onSearchListener) mActivity;
////	}
//
////	private static onSearchListener searchListener;
//
//	private LinkedList<HashMap<String, Bitmap>> footData;
//
//	private SimpleAdapter footAdapter;
//
//	private boolean isFootShow = false;
//
//	private boolean isBottom;
//
//	private SharedPreferences sp;
//	public static  int img_width;
//	
//	public static int width;
//
//	
//
//	private void initView() {
//
//		DisplayMetrics dm = new DisplayMetrics();
//		((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//		width=dm.widthPixels;
//		img_width = dm.widthPixels / 3;
//		indicatorWidth = dm.widthPixels / 4;
//		// 设置主页的轮播图的高宽
////		ad_pager.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
////				dm.widthPixels, dm.widthPixels / 2));
//		LayoutParams cursor_Params = iv_nav_indicator.getLayoutParams();
//		cursor_Params.width = indicatorWidth;// 初始化滑动下标的宽
//		iv_nav_indicator.setLayoutParams(cursor_Params);
//
//		// mHsv.setSomeParam(rl_nav, iv_nav_left, iv_nav_right, this);
//
//		// 获取布局填充器
//		// mInflater = (LayoutInflater) getActivity()
//		// .getSystemService(LAYOUT_INFLATER_SERVICE);
//
//		// 另一种方式获取
//		mInflater = LayoutInflater.from(mContext);
//
//		//initNavigationHSV();
//
////		fm = getChildFragmentManager();
////		ft = fm.beginTransaction();
////		ProductListFragment product = new ProductListFragment(
////				listTitle.isEmpty() ? null : listTitle.get(0).get("_id"),
////				listTitle.isEmpty() ? null : listTitle.get(0).get("sort_name"),
////				lazy_scroll);
////		ft.add(R.id.fragment, product);
////		ft.commitAllowingStateLoss();
//
//		// mAdapter = new ColorPagerAdapter(getChildFragmentManager(),
//		// listTitle,
//		// lazy_scroll);
//		// mViewPager.setAdapter(mAdapter);
//	}
//	
//
//	private void initNavigationHSV() {
//
//		rg_nav_content.removeAllViews();
//
//		for (int i = 0; i < listTitle.size(); i++) {
//
//			RadioButton rb = (RadioButton) mInflater.inflate(
//					R.layout.nav_radiogroup_item, null);
//			rb.setId(i);
//			// setDrawable(rb, listTitle.get(i).get("icon").split(",")[0]);
//			// rb.setText(tabTitle[i]);
//			getBitmap(YUrl.imgurl + listTitle.get(i).get("icon").split(",")[0],
//					rb);
//			rb.setText(listTitle.get(i).get("sort_name"));
//			rb.setLayoutParams(new LayoutParams(indicatorWidth,
//					LayoutParams.MATCH_PARENT));
//			rb.setGravity(Gravity.CENTER);
//
//			rg_nav_content.addView(rb);
//		}
//		rg_nav_content.check(0);
//		RadioButton rb = (RadioButton) rg_nav_content.getChildAt(0);
//
//		getBitmap(YUrl.imgurl + listTitle.get(0).get("icon").split(",")[0], rb);// 设置第一个图标为选中状态
//	}
//
//	/** 主店轮播图 以及 */
//	private void getTuijianData() {
//		new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity)mContext, 0) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.getMainTuijianData(context,null);
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
//				if(null == e){
//				// TODO Auto-generated method stub
////				ad_pager.setData((List<ShopOption>) result.get("topShops"),
////						context);
//				topOptions = (List<ShopOption>) result.get("topShops");
//				centerOptions = (List<ShopOption>) result.get("centShops");
////				addCenterImages();
////				SetImageLoader.initImageLoader(context, img_1,
////						((List<ShopOption>) (result.get("centShops"))).get(0)
////								.getUrl());
////				SetImageLoader.initImageLoader(context, img_2,
////						((List<ShopOption>) (result.get("centShops"))).get(1)
////								.getUrl());
////				SetImageLoader.initImageLoader(context, img_3,
////						((List<ShopOption>) (result.get("centShops"))).get(2)
////								.getUrl());
//				
//				v.setVisibility(View.VISIBLE);
//				mDataAdapter=new DateAdapter(mContext);
//				myListView.setAdapter(mDataAdapter);
//				initData(position,index+"");
//				}
//				super.onPostExecute(context, result, e);
//			}
//
//		}.execute();
//	}
//	
//	//添加中间图片
//	private void addCenterImages(){
//		img_container.removeAllViews();
//		for(int i = 0; i < centerOptions.size(); i++){
//			ImageView imageView = new ImageView(mContext);
//			imageView.setLayoutParams(new LayoutParams(img_width, img_width));
//			imageView.setPadding(2, 0, 2, 0);
//			imageView.setAdjustViewBounds(true);
//			SetImageLoader.initImageLoader(null,imageView,
//					centerOptions.get(i).getUrl(),"!180");
//			img_container.addView(imageView);
//			imageView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					sp.edit().putBoolean("isGoDetail", true).commit();
//					Intent intent = new Intent(mContext, ShopDetailsActivity.class);
//					intent.putExtra("code", centerOptions.get(0).getShop_code());
//					startActivityForResult(intent, 102);
//					getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);;
//				}
//			});
//		}
//	}
//
//	private void setListener() {
//		// mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
//		//
//		// @Override
//		// public void onPageSelected(int position) {
//		//
//		// if (rg_nav_content != null
//		// && rg_nav_content.getChildCount() > position) {
//		// ((RadioButton) rg_nav_content.getChildAt(position))
//		// .performClick();
//		// }
//		//
//		// }
//		//
//		// @Override
//		// public void onPageScrolled(int arg0, float arg1, int arg2) {
//		//
//		// }
//		//
//		// @Override
//		// public void onPageScrollStateChanged(int arg0) {
//		//
//		// }
//		// });
//
//		rg_nav_content
//				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//					@Override
//					public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//						if (rg_nav_content.getChildAt(checkedId) != null) {
//
//							TranslateAnimation animation = new TranslateAnimation(
//									currentIndicatorLeft,
//									((RadioButton) rg_nav_content
//											.getChildAt(checkedId)).getLeft(),
//									0f, 0f);
//							animation.setInterpolator(new LinearInterpolator());
//							animation.setDuration(100);
//							animation.setFillAfter(true);
//							RadioButton rb = (RadioButton) rg_nav_content
//									.getChildAt(checkedId);
//							getBitmap(YUrl.imgurl
//									+ listTitle.get(checkedId).get("icon")
//											.split(",")[0], rb);// 设置当前的图标为选中状态
//							// 其他图标设为未选中状态
//							for (int i = 0; i < listTitle.size(); i++) {
//								if (i != checkedId) {
//									rb = (RadioButton) rg_nav_content
//											.getChildAt(i);
//									getBitmap(YUrl.imgurl
//											+ listTitle.get(i).get("icon")
//													.split(",")[0], rb);
//								}
//							}
//							// 执行位移动画
//							iv_nav_indicator.startAnimation(animation);
//
//							// mViewPager.setCurrentItem(checkedId); //
//							// ViewPager
//							// 跟随一起 切换
//
//							// 记录当前 下标的距最左侧的 距离
//							currentIndicatorLeft = ((RadioButton) rg_nav_content
//									.getChildAt(checkedId)).getLeft();
//
//							horizotalScrollView
//									.smoothScrollTo(
//											(checkedId > 1 ? ((RadioButton) rg_nav_content
//													.getChildAt(checkedId))
//													.getLeft() : 0)
//													- ((RadioButton) rg_nav_content
//															.getChildAt(2))
//															.getLeft(), 0);
//
//							ft = fm.beginTransaction();
//							fm.popBackStack();
//							ft.replace(
//									R.id.fragment,
//									new ProductListFragment(listTitle.get(
//											checkedId).get("_id"), listTitle
//											.isEmpty() ? null : listTitle.get(
//											checkedId).get("sort_name"),
//											lazy_scroll));
//							ft.commitAllowingStateLoss();
//						}
//					}
//				});
//	}
//
//	public void getBitmap(final String url, final RadioButton rb) {
//		final Handler mHandler = new Handler() {
//			@Override
//			public void handleMessage(Message msg) {
//				// TODO Auto-generated method stub
//				super.handleMessage(msg);
//				if (msg.what == 1) {
//					Bitmap bitmap = (Bitmap) msg.obj;
//					Drawable d = new BitmapDrawable(bitmap);
//					// rb.setButtonDrawable(d);
//					rb.setCompoundDrawablesWithIntrinsicBounds(d, null, null,
//							null);
//					fileCache.saveBitmap(bitmap, url);
//					memoryCache.addBitmapToCache(url, bitmap);
//				}
//			}
//		};
//
//		// 从内存缓存中获取图片
//		Bitmap result = memoryCache.getBitmapFromCache(url);
//		if (result == null) {
//			// 文件缓存中获取
//			result = fileCache.getImage(url);
//			if (result == null) {
//				// 从网络获取
//				new Thread(new Runnable() {
//
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						Bitmap result = ImageGetFromHttp.downloadBitmap(url);
//						Message msg = new Message();
//						msg.obj = result;
//						msg.what = 1;
//						mHandler.sendMessage(msg);
//					}
//				}).start();
//
//			} else {
//				// 添加到内存缓存
//				memoryCache.addBitmapToCache(url, result);
//				rb.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(
//						result), null, null, null);
//			}
//		} else {
//			rb.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(
//					result), null, null, null);
//		}
//	}
//
//	@Override
//	public void onHiddenChanged(boolean hidden) {
//		// TODO Auto-generated method stub
//		if (hidden) {
//			AnimateFirstDisplayListener.displayedImages.clear();
//		}
//		super.onHiddenChanged(hidden);
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		// TODO Auto-generated method stub
//		Intent intent = null;
//		switch (arg0.getId()) {
//		case R.id.img_btn_search:
////			searchListener.onSearch();
//			break;
//		case R.id.img_btn_filter:
////			intent = new Intent(); 
//			MainMenuActivity cxt = (MainMenuActivity) this.getActivity();
//			intent = new Intent(cxt,FilterConditionActivity.class); 
////			intent.setClassName(cxt,"com.yssj.ui.activity.main.FilterConditionActivity");//打开一个activity  
//			startActivity(intent);                    
//			cxt.overridePendingTransition(R.anim.activity_filter_open,R.anim.activity_filter_close); 
//			break;
//		/*case R.id.img_1:
//			sp.edit().putBoolean("isGoDetail", true).commit();
//			intent = new Intent(getActivity(), ShopDetailsActivity.class);
//			intent.putExtra("code", centerOptions.get(0).getShop_code());
//			startActivityForResult(intent, 102);
//			break;
//		case R.id.img_2:
//			sp.edit().putBoolean("isGoDetail", true).commit();
//			intent = new Intent(getActivity(), ShopDetailsActivity.class);
//			intent.putExtra("code", centerOptions.get(1).getShop_code());
//			startActivityForResult(intent, 102);
//			break;
//		case R.id.img_3:
//			sp.edit().putBoolean("isGoDetail", true).commit();
//			intent = new Intent(getActivity(), ShopDetailsActivity.class);
//			intent.putExtra("code", centerOptions.get(2).getShop_code());
//			startActivityForResult(intent, 102);
//			break;*/
//		case R.id.ibtn_foot:
//			if (isFootShow) {
//				mLv.setVisibility(View.GONE);
//				isFootShow = !isFootShow;
//
//			} else {
//				mLv.setVisibility(View.VISIBLE);
//				isFootShow = !isFootShow;
//				mType=1;
//				index=1;
//				if(sp.getBoolean("isGoDetail", false)||dataList==null){
//				//initData(index + "");
//				}
//			}
//			break;
//		default:
//			break;
//		}
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		HashMap<String, Object> posMap = (HashMap<String, Object>) arg0
//				.getItemAtPosition(arg2);
//		startActivity(new Intent(mContext, ShopDetailsActivity.class)
//				.putExtra("code", (String) posMap.get("shop_code")));
//		getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);;
//		mLv.setVisibility(View.GONE);
//	}
//
//
//	@Override
//	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
//		if(arg1+arg2==arg3){
//			isBottom = true;
//		}
//	}
//
//	@Override
//	public void onScrollStateChanged(AbsListView arg0, int arg1) {
////		if(arg1==SCROLL_STATE_IDLE&&isBottom&&!isEnd){
////			mType=2;
////			index++;
////			initData(index+"");
////			isBottom = false;
////			Toast.makeText(mContext, "加载更多", Toast.LENGTH_LONG).show()	;
////		}
//	}
//
//
//
//	private class DateAdapter extends BaseAdapter{
//		
//		private Context context;
//		
//		private int picHeight;//900
//		
//		public DateAdapter( Context context) {
//			super();
//			this.context = context;
//			this.picHeight=width/2*900/600;
//		}
//
//		@Override
//		public int getCount() {
//			int count=0;
//			if(topOptions!=null&&!topOptions.isEmpty()){
//				count++;
//			}
//			if(centerOptions!=null&&!centerOptions.isEmpty()){
//				count++;
//			}
//			
//			if(listTitle!=null&&!listTitle.isEmpty()){//导航栏
//				count++;
//			}
//			
//			if(dataList!=null&&!dataList.isEmpty()){
//				count+=dataList.size()%2==0?dataList.size()/2:dataList.size()/2+1;
//			}
//			
//			return count;
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
//		public View getView(int position, View view, ViewGroup arg2) {
//			ItemView items;
//			if(view==null){
//				view=LayoutInflater.from(context).inflate(R.layout.my_item_layout, null);
//				items=new ItemView();
//				items.pager=(SlideShowView) view.findViewById(R.id.pager);
//				items.title=(MyHorizontalScrollView) view.findViewById(R.id.title);
//				items.imageTitle=(MyHorizontalScrollView) view.findViewById(R.id.image_title);
//				items.left=(com.yssj.custom.view.ItemView) view.findViewById(R.id.left);
//				items.left.setHeight(picHeight);
//				items.right=(com.yssj.custom.view.ItemView) view.findViewById(R.id.right);
//				items.right.setHeight(picHeight);
//				items.mData=view.findViewById(R.id.data);
//				view.setTag(items);
//			}else{
//				items=(ItemView) view.getTag();
//			}
//			
//			
//			
//			if(position==0&&topOptions!=null&&topOptions.size()>0){
//				items.pager.setVisibility(view.VISIBLE);
//				items.title.setVisibility(view.GONE);
//				items.imageTitle.setVisibility(view.GONE);
//				items.mData.setVisibility(view.GONE);
//				items.pager.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
//						width, width / 2));
//				items.pager.setData(topOptions, context);
//				return view;
//			}
//			
//			if(position==1&&centerOptions!=null&&centerOptions.size()>0){
//				items.imageTitle.setVisibility(view.VISIBLE);
//				items.pager.setVisibility(view.GONE);
//				items.title.setVisibility(view.GONE);
//				items.mData.setVisibility(view.GONE);
//				items.imageTitle.setList(centerOptions, true);
//				return view;
//			}
//			
//			if(position==2&&listTitle!=null&&listTitle.size()>0){
//				items.title.setVisibility(view.VISIBLE);
//				items.pager.setVisibility(view.GONE);
//				items.imageTitle.setVisibility(view.GONE);
//				items.mData.setVisibility(view.GONE);
//				items.title.setOnClickLintener(MainShopFragment.this);
//				items.title.setList(listTitle, false);
//				return view;
//			}
//			
//			if(position>2){
//				position=position-3;
//			}
//			position=position*2;
//			
//			if(items.mData.getVisibility()==View.GONE){
//				items.mData.setVisibility(view.VISIBLE);
//				items.pager.setVisibility(view.GONE);
//				items.title.setVisibility(view.GONE);
//				items.imageTitle.setVisibility(view.GONE);
//			}
//			
//			if(dataList.size()>position){
//				String url=(String) dataList.get(position).get("def_pic");
////				String[] sss = url.split("_");
////				int imageHeight = 0;
////				if (sss.length > 0) {
////					int picWidth = Integer.parseInt(sss[1]);
////					int picHeight = Integer.parseInt(sss[2]
////							.split("\\.")[0]);
////					imageHeight = width/2 * picHeight / picWidth;
////				}
//				items.left.iniView(dataList.get(position));
//				items.left.setOnClickListener(new MyOnClick(position));
//			}
//			if(dataList.size()>position+1){
//				items.right.setVisibility(view.VISIBLE);
//				String url=(String) dataList.get(position+1).get("def_pic");
////				String[] sss = url.split("_");
////				int imageHeight = 0;
////				if (sss.length > 0) {
////					int picWidth = Integer.parseInt(sss[1]);
////					int picHeight = Integer.parseInt(sss[2]
////							.split("\\.")[0]);
////					imageHeight = width/2 * picHeight / picWidth;
////				}
//				items.right.iniView(dataList.get(position+1));
//				items.right.setOnClickListener(new MyOnClick(position+1));
//			}else{
//				items.right.setVisibility(view.INVISIBLE);
//			}
//			
//			return view;
//		}
//		
//	}
//	
//	private class MyOnClick implements OnClickListener{
//		
//		private int position;
//		
//		
//		public MyOnClick(int position) {
//			super();
//			this.position = position;
//		}
//
//		@Override
//		public void onClick(View arg0) {
//			HashMap<String, Object> posMap = dataList.get(position);
//			
//			mContext.getSharedPreferences("YSSJ_yf",
//					mContext.MODE_PRIVATE).edit()
//					.putBoolean("isGoDetail", true).commit();
//			addScanDataTo((String) posMap.get("shop_code"));
//			Intent intent = new Intent(mContext, ShopDetailsActivity.class);
//			intent.putExtra("code", (String) posMap.get("shop_code"));
//			// context.startActivity(intent);
//			intent.putExtra("shopCarFragment", "shopCarFragment");
//			FragmentActivity activity = (FragmentActivity) mContext;
//			activity.startActivityForResult(intent, 101);
//			activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);;
//		}
//	}
//	
//	
//	private static class ItemView{
//		
//		private  SlideShowView pager;
//		private  MyHorizontalScrollView title;
//		private  MyHorizontalScrollView imageTitle;
//		private  com.yssj.custom.view.ItemView left;
//		private  com.yssj.custom.view.ItemView right;
//		private  View mData;
//	}
//	
//
//	private void initData(final int position,final String index) {
//		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(
//				getActivity(), 0) {
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<HashMap<String, Object>> result) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result);
//				if(dataList==null){
//					dataList =  new LinkedList<HashMap<String,Object>>();
//				}
//				if(mType==1){
//					dataList.clear();
//					if(myListView.getRefreshableView().getFirstVisiblePosition()>3){
//						myListView.getRefreshableView().setSelection(3);
//					}
//				}
//				if(mType==2&&(result==null||result.isEmpty())){
//					Toast.makeText(mContext, "已经到底了", Toast.LENGTH_SHORT).show();
//					myListView.onRefreshComplete();
//					return;
//				}
//				dataList.addAll(result);
//				mDataAdapter.notifyDataSetChanged();
//				myListView.onRefreshComplete();
//			}
//
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//				return ComModel2.getProductList1(context,index, listTitle.get(position).get("_id"),String.valueOf(1),listTitle
//						.isEmpty() ? null : listTitle.get(
//								position).get("sort_name"),
//						 Integer.parseInt("30"));
//			}
//
//		}.execute();
//	}
//	
//	/*
//	 * 把浏览过的数据添加进数据库
//	 */
//	private void addScanDataTo(final String shop_code) {
//		new SAsyncTask<Void, Void, ReturnInfo>(getActivity()) {
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context,
//					Void... params) throws Exception {
//				return ComModel.addMySteps(context, shop_code);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					ReturnInfo result) {
//				super.onPostExecute(context, result);
//			}
//
//		}.execute();
//	}
//
//	
//	
//	@Override
//	public void myOnClick(View v) {
//		mType=1;
//		index=1;
//		mIndex=v.getId();
//		position=v.getId();
//		initData(v.getId(),index+"");
//		
//	}
//	
//	/**
//	 * 滑动选择
//	 */
//	
//	
//	
//	
//	
//	
//	
//}
