//package com.yssj.ui.fragment;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.MyScrollView;
//import com.yssj.data.YDBHelper;
//import com.yssj.entity.FlowView;
//import com.yssj.entity.Like;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.model.ComModel;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ToastUtil;
////import me.maxwin.view.XListView;
////import me.maxwin.view.XListView.IXListViewListener;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.view.Display;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
////import com.yssj.ui.adpter.StaggeredAdapter;
//
//public class MyFavorProductListNewFragment extends Fragment implements OnClickListener{
//
//
//	private int index = 1;
//
//	private int mType = 1;// 1：初始化数据；2：加载更多数据
//
//	private List<HashMap<String, String>> mapSecond = new ArrayList<HashMap<String, String>>();
//	private YDBHelper dbHelper;
//
//	private Display display;
//	private int item_width;
//	
//	private int column_count = 2;// 显示列数
//	
//	private int[] topIndex;
//	private int[] bottomIndex;
//	private int[] lineIndex;
//	private int[] column_height;// 每列的高度
//	
//	private HashMap<Integer, String> pins;
//
//	private int loaded_count = 0;// 已加载数量
//
//	private HashMap<Integer, Integer>[] pin_mark = null;
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
//	private boolean flag = false;
//	
//
//	public MyFavorProductListNewFragment(MyScrollView waterfall_scroll) {
//		this.waterfall_scroll = waterfall_scroll;
//		setRetainInstance(true);
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//
//		View v = inflater.inflate(R.layout.product_fragment, container, false);
//		initView(v);
//		return v;
//	}
//
//	private void initView(View v) {/*
//
////		btn_search = (Button) v.findViewById(R.id.btn_search);
////		btn_search.setOnClickListener(this);
////		btn_filter = (Button) v.findViewById(R.id.btn_filter);
////		btn_filter.setOnClickListener(this);
//
//		waterfall_scroll.getView();
//		waterfall_scroll.setOnScrollListener(new OnScrollListener() {
//
//			@Override
//			public void onTop() {
//				// 滚动到最顶端
//				LogYiFu.d("LazyScroll", "Scroll to top");
////				mType = 1;
////				index = 1;
////				waterfall_container.removeAllViews();
////				initData(index+"", id, level);
//			}
//
//			@Override
//			public void onScroll() {
//
//			}
//
//			@Override
//			public void onBottom() {
//				// 滚动到最低端
////				AddItemToContainer(++current_page, page_count);
//				mType = 2;
//				initData(++index+"");
//			}
//
//			@Override
//			public void onAutoScroll(int l, int t, int oldl, int oldt) {}
//
//			@Override
//			public void onScrollStop() {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//
////		waterfall_container = (LinearLayout) v
////				.findViewById(R.id.waterfall_container);
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
//		
//		initData(index + "");
//
//	*/}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		
//		display = getActivity().getWindowManager().getDefaultDisplay();
//		item_width = display.getWidth() / column_count;// 根据屏幕大小计算每列大小
//		inflater = LayoutInflater.from(getActivity());
//		
//		column_height = new int[column_count];
//		context = getActivity();
//		iviews = new HashMap<Integer, FlowView>();
//		pins = new HashMap<Integer, String>();
//		pin_mark = new HashMap[column_count];
//
//		this.lineIndex = new int[column_count];
//		this.bottomIndex = new int[column_count];
//		this.topIndex = new int[column_count];
//
//		for (int i = 0; i < column_count; i++) {
//			lineIndex[i] = -1;
//			bottomIndex[i] = -1;
//			pin_mark[i] = new HashMap();
//		}
//	}
//
//	
//	/**
//	 * 点击编辑显示是否需要置顶标志
//	 * @param flag
//	 */
//	public void setTop(boolean flag){
//		this.flag = flag;
//	}
//	
//	/**
//	 * 设置是否置顶
//	 * @param shop_code
//	 * @param is_show
//	 */
//	private void isSetTop(final String shop_code,final String is_show){
//		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context,
//					Void... params) throws Exception {
//				return ComModel.getMyFavorIsSetTop(context, shop_code, is_show);
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
//				if(result != null && "1".equals(result.getStatus())){
//					ToastUtil.showShortText(context, result.getMessage());
//				}else{
//					ToastUtil.showShortText(context, "糟糕，出错了。。。");
//				}
//				}
//			}
//			
//		}.execute();
//	}
//	
//	public void addView(final Like like, int rowIndex, int id, int width, int height){
//		View view = inflater.inflate(R.layout.myfave_infos_list, null);
//		ImageView imageView = (ImageView) view.findViewById(R.id.news_pic);
//		
//		ImageButton iv_set_top = (ImageButton) view.findViewById(R.id.iv_set_top);
//		
////		ImageView iv_shopcar = (ImageView) view.findViewById(R.id.iv_shopcar);
////		ImageView iv_love_star = (ImageView) view.findViewById(R.id.iv_love_star);
////		ImageView iv_star = (ImageView) view.findViewById(R.id.iv_star);
//		
//		TextView nickback = (TextView) view.findViewById(R.id.nickback);
//		TextView contentView = (TextView) view.findViewById(R.id.news_title);
//		TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
//		CheckBox cb_favor = (CheckBox) view.findViewById(R.id.cb_favor);
//		
//		view.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//	//			addScanDataToDB(map);
//				Intent intent = new Intent(context, ShopDetailsActivity.class);
//				intent.putExtra("code", like.getShop_code());
//				intent.putExtra("shopCarFragment", "shopCarFragment");
//				FragmentActivity activity  = (FragmentActivity) context;
//				activity.startActivityForResult(intent, 101);
//				activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
//			}
//		});
//		String imageUrl = like.getShow_pic();
//		SetImageLoader.initImageLoader(context, imageView, imageUrl,"");
//		contentView.setText("【" + like.getShop_name().toString() + "】");
//		tv_price.setText("￥" + like.getShop_price().toString());
//		
//		if (like.getShop_price().toString().equals("1")) {
//			cb_favor.setChecked(true);
//		} else {
//			cb_favor.setChecked(false);
//		}
//		
//		nickback.setText(like.getKickback() == null ?"0.0":like.getKickback() + "\n返佣");
//	
////		if("0".equals(like.getIsCart())){
////			iv_shopcar.setImageResource(android.R.drawable.btn_star_big_off);
////		}else{
////			iv_shopcar.setImageResource(android.R.drawable.btn_star_big_on);
////		}
//		
//		if(flag){
//			
//			iv_set_top.setVisibility(View.VISIBLE);
//		}else{
//			iv_set_top.setVisibility(View.GONE);
//		}
//		
//		iv_set_top.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if("0".equals(like.getIs_show())){
//					
//					isSetTop(like.getShop_code(),"1");
//				}else{
//					isSetTop(like.getShop_code(),"0");
//				}
//			}
//		});
//		
//		// 此处计算列值
//		int columnIndex = GetMinValue(column_height);
//
//		column_height[columnIndex] += height;
//
//		waterfall_items.get(columnIndex).addView(view);
//
//		lineIndex[columnIndex]++;
//
//		pin_mark[columnIndex].put(lineIndex[columnIndex],
//				column_height[columnIndex]);
//		bottomIndex[columnIndex] = lineIndex[columnIndex];
//	}
//	
//	public void initData(String index) {
//		new SAsyncTask<String, Void, List<Like>>(
//				getActivity(), 0) {
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<Like> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if(null == e){
//				if (null != result && result.size() >0) {
//					for(int i = 0; i< result.size(); i++){
//						Like like = result.get(i);
//						String imageUrl = like.getShow_pic();
//						LogYiFu.e("", imageUrl);
//						String[] sss = imageUrl.split("_");
//						int imageHeight = 0;
//						if (sss.length > 0) {
//							int picWidth = Integer.parseInt(sss[1]);
//							int picHeight = Integer.parseInt(sss[2].split("\\.")[0]);
//							imageHeight = item_width * picWidth / picHeight;
//						}
//						addView(like, (int) Math.ceil(loaded_count / (double) column_count), loaded_count, item_width, imageHeight);
//					}
//				}else{
//					ToastUtil.showShortText(getActivity(), "已经到底了");
//					MyFavorProductListNewFragment.this.index--;
//				}
//				}
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected List<Like> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//					return ComModel2.getMyFavourList(context, Integer.parseInt(params[0]));
//				
//			}
//
//		}.execute(index);
//	}
//
//	/*
//	 * 把浏览过的数据添加进数据库
//	 */
//	@SuppressWarnings("unchecked")
//	private void addScanDataToDB(final HashMap<String, Object> map) {
//		new AsyncTask<HashMap<String, Object>, Void, Void>(){
//
//			@Override
//			protected Void doInBackground(HashMap<String, Object>... params) {
//				
//				return null;
//			}
//
//			@Override
//			protected void onPostExecute(Void result) {
//				super.onPostExecute(result);
//			}
//			
//			
//			
//		}.execute(map);
//	}
//	
//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		Intent intent = null;
//		switch (arg0.getId()) {
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
