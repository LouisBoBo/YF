//package com.yssj.ui.fragment;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
//import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
//import com.yssj.YJApplication;
//import com.yssj.YUrl;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.MyScrollView;
//import com.yssj.custom.view.MyScrollView.OnScrollListener;
//import com.yssj.data.YDBHelper;
//import com.yssj.entity.FlowView;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.model.ComModel;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.GuideActivity;
//import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.SetImageLoader.AnimateFirstDisplayListener;
//import com.yssj.utils.ToastUtil;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.view.Display;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
////import me.maxwin.view.XListView;
////import me.maxwin.view.XListView.IXListViewListener;
//
////import com.yssj.ui.adpter.StaggeredAdapter;
//
//public class ProductListFragment extends Fragment implements OnClickListener {
//
//	private String id = -1 + "";
//
//	private int index = 1;
//
//	private int mType = 1;// 1：初始化数据；2：加载更多数据
//
//	// private Button btn_search, btn_filter;
//
//	private List<HashMap<String, String>> mapSecond = new ArrayList<HashMap<String, String>>();
//	private YDBHelper dbHelper;
//
//	private String level = 1 + "";
//
//	private Display display;
//	private int item_width;
//
//	private int column_count = 2;// 显示列数
//
//	private int[] column_height;// 每列的高度
//
//	private int loaded_count = 0;// 已加载数量
//
//	private Context context;
//
//	private HashMap<Integer, FlowView> iviews;
//
//	private MyScrollView waterfall_scroll;
//	private LinearLayout waterfall_container;
//	private ArrayList<LinearLayout> waterfall_items;
//
//	private Handler handler;
//
//	private LayoutInflater inflater;
//
//	private int pageSize = 10;
//	private String sort_name;
//
//	private ImageLoader imageLoader;
//	private DisplayImageOptions options;
//	private ImageLoaderConfiguration config;
//
//	public ProductListFragment(String id, String sort_name,
//			MyScrollView waterfall_scroll) {
//		this.id = id;
//		this.waterfall_scroll = waterfall_scroll;
//		this.sort_name = sort_name;
//		setRetainInstance(true);
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		if (savedInstanceState != null)
//			id = savedInstanceState.getString("id");
//		View v = inflater.inflate(R.layout.product_fragment, container, false);
//		initView(v);
//		return v;
//	}
//
//	private void initView(View v) {
//
//		// btn_search = (Button) v.findViewById(R.id.btn_search);
//		// btn_search.setOnClickListener(this);
//		// btn_filter = (Button) v.findViewById(R.id.btn_filter);
//		// btn_filter.setOnClickListener(this);
//
//		waterfall_scroll.getView();
//		waterfall_scroll.setOnScrollListener(new OnScrollListener() {
//
//			@Override
//			public void onTop() {
//				// 滚动到最顶端
//			}
//
//			@Override
//			public void onScroll() {
//				new PauseOnScrollListener(GuideActivity.getLoader(), true, true);
//			}
//
//			@Override
//			public void onBottom() {
//				// 滚动到最低端
//				// AddItemToContainer(++current_page, page_count);
//				mType = 2;
//				initData(++index + "", id, level, sort_name);
//			}
//
//			@Override
//			public void onAutoScroll(int l, int t, int oldl, int oldt) {
//			}
//
//
//			@Override
//			public void onScrollStop() {
//				// TODO Auto-generated method stub
//
//			}
//		});
//
//		waterfall_container = (LinearLayout) v
//				.findViewById(R.id.waterfall_container);
//
//		waterfall_items = new ArrayList<LinearLayout>();
//
//		for (int i = 0; i < column_count; i++) {
//			LinearLayout itemLayout = new LinearLayout(getActivity());
//			LinearLayout.LayoutParams itemParam = new LinearLayout.LayoutParams(
//					item_width, LayoutParams.WRAP_CONTENT);
//
//			itemLayout.setPadding(2, 2, 2, 2);
//			itemLayout.setOrientation(LinearLayout.VERTICAL);
//
//			itemLayout.setLayoutParams(itemParam);
//			waterfall_items.add(itemLayout);
//			waterfall_container.addView(itemLayout);
//		}
//		initData(index + "", id, level, sort_name);
//
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		dbHelper = new YDBHelper(getActivity());
//		mapSecond = dbHelper.query("select * from sort_info where p_id =" + id);
//		LogYiFu.e("二级目录大小", mapSecond.size() + "");
//
//		display = getActivity().getWindowManager().getDefaultDisplay();
//		item_width = display.getWidth() / column_count;// 根据屏幕大小计算每列大小
//		inflater = LayoutInflater.from(getActivity());
//
//		column_height = new int[column_count];
//		context = getActivity();
//		iviews = new HashMap<Integer, FlowView>();
//
//		initImageLoader();
//
//	}
//
//	private void initImageLoader() {
//		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.image_default)
//				.showImageForEmptyUri(R.drawable.ic_empty)
//				.bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(false)
//				// 设置下载的图片是否缓存在SD卡中
//				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true)
//				.considerExifParams(true)
//				// .displayer(new FadeInBitmapDisplayer(35))
//				.build();
//
//		config = new ImageLoaderConfiguration.Builder(context)
//				.threadPoolSize(1)
//				.threadPriority(Thread.NORM_PRIORITY - 2)
//				.denyCacheImageMultipleSizesInMemory()
//				.memoryCache(new WeakMemoryCache())
//				// .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 *
//				// 1024))
//				// You can pass your own memory cache
//				// implementation/你可以通过自己的内存缓存实现
//				.memoryCacheSize(6 * 1024 * 1024)
//				.diskCacheSize((int) Runtime.getRuntime().maxMemory() / 8)
//				.tasksProcessingOrder(QueueProcessingType.LIFO)
//				.diskCacheFileCount(100)
//				.memoryCacheExtraOptions(480, 800)
//
//				.diskCacheSize(100 * 1024 * 1024)
//				.threadPriority(Thread.NORM_PRIORITY - 2)
//				// 设置线程的优先级
//				// 缓存的文件数量
//				.imageDownloader(
//						new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
//				.writeDebugLogs() // Remove for releaseapp
//				.build();// 开始构建
//
//		imageLoader = ImageLoader.getInstance();
//		imageLoader.init(config);
//	}
//
//	private static ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//
//	private void addView(final HashMap<String, Object> map, int height) {
//		View view = inflater.inflate(R.layout.pro_item, null);
//		ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
//		TextView tv_pro_name = (TextView) view.findViewById(R.id.tv_pro_name);
//		TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
//		TextView returnback = (TextView) view.findViewById(R.id.returnback);
////		ImageView iv_star = (ImageView) view.findViewById(R.id.iv_star);
////		ImageView iv_love_star = (ImageView) view
////				.findViewById(R.id.iv_love_star);
////		ImageView iv_shopcar = (ImageView) view.findViewById(R.id.iv_shopcar);
//		view.setLayoutParams(new LayoutParams(item_width, height));
//		view.setPadding(0, 0, 0, 3);
//		img_title.setTag((String) map.get("def_pic") + "!280");
//		imageLoader.displayImage(YUrl.imgurl + (String) map.get("def_pic")
//				+ "!280", img_title, options, animateFirstListener);
//		// SetImageLoader.loadImage(getActivity(), img_title, (String)
//		// map.get("def_pic")+"!280");
//
//		if (YJApplication.getbigimagelimit() > YJApplication.getbigimagemax()) {
//			imageLoader.clearMemoryCache();
//			YJApplication.setbigimagelimit(0);
//			LogYiFu.e("", "getbigimagelimit():" + YJApplication.getbigimagelimit()
//					+ " getsdimage():" + YJApplication.getsdimage());
//		} else {
//			LogYiFu.e("", "getbigimagelimit():" + YJApplication.getbigimagelimit()
//					+ " getsdimage():" + YJApplication.getsdimage());
//			YJApplication
//					.setbigimagelimit(YJApplication.getbigimagelimit() + 1);
//		}
//
//		// SetImageLoader.initImageLoader(context, img_title,
//		// (String) map.get("def_pic") + "!280");
//		tv_pro_name.setText("【" + (CharSequence) map.get("shop_name") + "】");
//
//		double price = Double.parseDouble((String) map.get("shop_se_price"));
//		tv_price.setText("￥"
//				+ new java.text.DecimalFormat("#0.00").format(price));
//		double kickBack = Double.parseDouble((String) map.get("kickback"));
//		returnback.setText(new java.text.DecimalFormat("#0.00").format(price
//				* kickBack)
//				+ "\n返佣");
//		int isLike = (Integer) map.get("isLike");
////		if (isLike > 0) {
////			iv_love_star.setImageResource(R.drawable.img_love_star_selected);
////		}
////		int isCart = (Integer) map.get("isCart");
////		if (isCart > 0) {
////			iv_shopcar.setImageResource(R.drawable.img_shopcar_selected);
////		}
//		view.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				context.getSharedPreferences("YSSJ_yf", context.MODE_PRIVATE)
//						.edit().putBoolean("isGoDetail", true).commit();
//				addScanDataTo((String) map.get("shop_code"));
//				Intent intent = new Intent(context, ShopDetailsActivity.class);
//				intent.putExtra("code", (String) map.get("shop_code"));
//				// context.startActivity(intent);
//				intent.putExtra("shopCarFragment", "shopCarFragment");
//				FragmentActivity activity = (FragmentActivity) context;
//				activity.startActivityForResult(intent, 101);
//				activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);;
//			}
//		});
//		// 此处计算列值
//		int columnIndex = GetMinValue(column_height);
//
//		column_height[columnIndex] += height;
//		LogYiFu.e("height:", height + " " + item_width);
//		LogYiFu.e("column_height:" + columnIndex, column_height[columnIndex] + "");
//
//		waterfall_items.get(columnIndex).addView(view);
//
//	}
//
//	@Override
//	public void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		if (imageLoader.isInited()) {
//			imageLoader.clearMemoryCache();
//			imageLoader.clearDiskCache();
//			imageLoader.stop();
////			imageLoader.destroy();
//		}
//	}
//
//	private void initData(String index, String id, String level, String typename) {
//		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(
//				getActivity(), 0) {
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<HashMap<String, Object>> result) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result);
//				// listResult.addAll(result);
//				if (null != result && result.size() > 0) {
//					for (int i = 0; i < result.size(); i++) {
//						HashMap<String, Object> map = result.get(i);
//						String imageUrl = (String) map.get("def_pic");
//						LogYiFu.e("", imageUrl);
//						String[] sss = imageUrl.split("_");
//						int imageHeight = 0;
//						if (sss.length > 0) {
//							int picWidth = Integer.parseInt(sss[1]);
//							int picHeight = Integer.parseInt(sss[2]
//									.split("\\.")[0]);
//							imageHeight = item_width * picHeight / picWidth;
//						}
//						addView(map, imageHeight);
//					}
//				} else {
//					ToastUtil.showShortText(context, "已经到底了");
//					ProductListFragment.this.index--;
//				}
//
//			}
//
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//				return ComModel2.getProductList1(context, params[0], params[1],
//						params[2], params[3], pageSize);
//
//			}
//
//		}.execute(index, id, level, typename);
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
//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		outState.putString("id", id);
//	}
//
//	@Override
//	public void onHiddenChanged(boolean hidden) {
//		// TODO Auto-generated method stub
//		if (hidden) {
//			AnimateFirstDisplayListener.displayedImages.clear();
//			if (imageLoader.isInited()) {
//			imageLoader.clearMemoryCache();
//			imageLoader.clearDiskCache();
//			imageLoader.stop();
////			imageLoader.destroy();
//			}
//		}
//		super.onHiddenChanged(hidden);
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		// TODO Auto-generated method stub
//		Intent intent = null;
//		switch (arg0.getId()) {
//		// case R.id.btn_filter:
//		// intent = new Intent(getActivity(),FilterConditionActivity.class);
//		// startActivity(intent);
//		// break;
//		// case R.id.btn_search:
//		// intent = new Intent(getActivity(), SeachNoticeActivity.class);
//		// intent.putExtra("id", id);
//		// startActivity(intent);
//		// break;
//
//		default:
//			break;
//		}
//	}
//
//	private int GetMinValue(int[] array) {
//		int m = 0;
//		int length = array.length;
//		for (int i = 0; i < length; ++i) {
//
//			if (array[i] < array[m]) {
//				m = i;
//			}
//		}
//		return m;
//	}
//
//}
