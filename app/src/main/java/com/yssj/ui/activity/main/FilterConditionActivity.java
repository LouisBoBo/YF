//package com.yssj.ui.activity.main;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.opengl.Visibility;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.FragmentTransaction;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.view.animation.LinearInterpolator;
//import android.view.animation.TranslateAnimation;
//import android.widget.HorizontalScrollView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
//import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
//import com.yssj.YJApplication;
//import com.yssj.YUrl;
//import com.yssj.activity.R;
//import com.yssj.app.AppManager;
//import com.yssj.custom.view.FilterTitleView;
//import com.yssj.custom.view.LoadingDialog;
//import com.yssj.custom.view.MyTitleView;
//import com.yssj.custom.view.NoScrollGridView;
//import com.yssj.data.YDBHelper;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.GuideActivity;
//import com.yssj.ui.activity.LeftFragment;
//import com.yssj.ui.adpter.FilterGridViewAdapter;
//import com.yssj.ui.adpter.FilterGridViewAdapter.CheckCallback;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.ImageFileCache;
//import com.yssj.utils.ImageGetFromHttp;
//import com.yssj.utils.ImageMemoryCache;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YunYingTongJi;
//
///***
// * 我喜欢
// * 
// * @author Administrator
// * 
// */
//public class FilterConditionActivity extends SlidingFragmentActivity implements
//		CheckCallback,OnClickListener{
//
//	private AppManager appManager;
//	
//
//	private YDBHelper helper;
//
//	private List<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();
//
//	private List<ArrayList<HashMap<String, String>>> listDatas = new ArrayList<ArrayList<HashMap<String, String>>>();
//
//	private LinkedList<HashMap<String, String>> linkList = new LinkedList<HashMap<String, String>>();
//
//	private LinearLayout container, container_item;
//
//	private RadioGroup rg_nav_content;
//	
//	private HorizontalScrollView horizotalScrollView;
//
////	private List<HashMap<String, String>> listTitle;
//
//	private List<HashMap<String, String>> listMap = new ArrayList<HashMap<String, String>>();
//	private List<HashMap<String, String>> listMapChecked = new ArrayList<HashMap<String, String>>();
//
//	private LayoutInflater mInflater;
//
//	private int indicatorWidth;
//	
//	private int currentIndicatorLeft = 0;
//
//	// 下载缓存 将用
//	private ImageMemoryCache memoryCache;
//	private ImageFileCache fileCache;
//	
//	private String checkId;
//
//	private RelativeLayout img_most_right_icon;
//	
//	protected LeftFragment mFrag;
//	
//	private SlidingMenu sm;
//	
////	private FilterTitleView filter_title;
//	private TextView tv_fc_word;
//	
//	private List<HashMap<String, String>> listTitle; 
//	private String sort_name,_id;
//	
//	
//	
//	private CanvasTransformer mTransformer = new CanvasTransformer() {
//		@Override
//		public void transformCanvas(Canvas canvas, float percentOpen) {
//			float scale = (float) (percentOpen * 0.25 + 0.75);
//			canvas.scale(scale, scale, canvas.getWidth() / 2,
//					canvas.getHeight() / 2);
//		}
//	};
//
//
//	private String isWhere;
//
//
//	
//	
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		YJApplication.getLoader().stop();
////		MobclickAgent.onPageEnd("FilterConditionActivity"); 
////		MobclickAgent.onPause(this);
//	}
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
////		getActionBar().hide();
//		
//		setBehindContentView(R.layout.menu_frame);
//		if (savedInstanceState == null) {
//			FragmentTransaction t = this.getSupportFragmentManager()
//					.beginTransaction();
//			mFrag = new LeftFragment();
//			t.replace(R.id.menu_frame, mFrag);
//			t.commit();
//		} else {
//			mFrag = (LeftFragment) this.getSupportFragmentManager()
//					.findFragmentById(R.id.menu_frame);
//		}
//		sm = getSlidingMenu();
//		sm.setShadowWidthRes(R.dimen.shadow_width);
//		sm.setShadowDrawable(R.drawable.shadow);
//		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//		sm.setFadeDegree(0.35f);
//		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
////		sm.setActivated(true);
//		
//		setContentView(R.layout.filter_condition);
//		
//		sm = getSlidingMenu();
//		setSlidingActionBarEnabled(true);
//		sm.setBehindScrollScale(0.0f);
//		sm.setBehindCanvasTransformer(mTransformer);
//		helper = new YDBHelper(this);
//		
//		
//		
//		appManager = AppManager.getAppManager();
//		appManager.addActivity(this);
//		findViewById(R.id.tv_commit).setOnClickListener(this);
//		// listView = (ListView) findViewById(R.id.listView);
//		container = (LinearLayout) findViewById(R.id.container);
//		container_item = (LinearLayout) findViewById(R.id.container_item);
//		
////		filter_title  =(FilterTitleView) findViewById(R.id.filter_title);
//		
////		YDBHelper helper = new YDBHelper(this);
////		String sql = "select * from sort_info where p_id = 0 and is_show = 1 order by sequence";
////		listTitle = helper.query(sql);
////		filter_title.setData(listTitle);
//		mInflater = LayoutInflater.from(this);
////		listTitle = helper.query(sql);
//		
//		isWhere = getIntent().getStringExtra("isWhere");
//		
//		sort_name= getIntent().getStringExtra("sort_name");
//		_id      = getIntent().getStringExtra("_id");
//		tv_fc_word = (TextView) findViewById(R.id.tv_fc_word);
//		tv_fc_word.setText("以下每个选项为单选哦，可同时选多个选项。如喜欢韩版女装，就单选\"韩系\"点提交，喜欢韩版修身，同时选\"韩系\"和\"修身款\"，再点提交就可以啦！");
//		((TextView) findViewById(R.id.tvTitle_base)).setText("筛  选");
//		findViewById(R.id.img_back).setOnClickListener(this);
//		img_most_right_icon = (RelativeLayout) findViewById(R.id.img_most_right_icon);
//		img_most_right_icon.setOnClickListener(this);
//		
//		rg_nav_content = (RadioGroup) findViewById(R.id.rg_nav_content);
//		
//		horizotalScrollView = (HorizontalScrollView) findViewById(R.id.mHsv);
////		LoadingDialog.show(this);
//		new AsyncTask<Void, Void, Void>() {
//			
//			@Override
//			protected Void doInBackground(Void... params) {
//				// TODO Auto-generated method stub
//				initView();
//				return null;
//			}
//			@Override
//			protected void onPostExecute(Void result) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(result);
//				new Handler().postDelayed(new Runnable(){
//				    public void run(){
//				    	addItemView();
////				    	LoadingDialog.hide();
//				    }
//				  
//				  },100);
//				
//			}
//		}.execute();
////		memoryCache = new ImageMemoryCache(this);
////		fileCache = new ImageFileCache();
//		
////		initNavigationHSV();
////		setRadioGroupListener();
//		
//	}
//	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
////		MobclickAgent.onPageStart("FilterConditionActivity"); 
////	    MobclickAgent.onResume(this);    
//	}
//	
//	
//	private void initView() {
//
//		
//		categoryList = helper
//				.query("select * from tag_info where p_id=0 and is_show=1 order by sequence");
//		ArrayList<HashMap<String, String>> listChild;
//		for (int i = 0; i < categoryList.size(); i++) {
//			listChild = (ArrayList<HashMap<String, String>>) helper
//					.query("select * from tag_info where p_id = "
//							+ categoryList.get(i).get("_id") + " order by sequence");
//			for (HashMap<String, String> map : listChild) {
//				map.put("isChecked", "0");
//				map.put("aa", categoryList.get(i).get("e_name"));
//			}
//			listDatas.add(listChild);
//		}
//		
//		//addItemView();
//	}
//
////	@Override
////	public boolean onMenuItemSelected(int featureId, MenuItem item) {
////		if (item.getItemId() == android.R.id.home) {
////			appManager.finishActivity();
////		}
////		return super.onMenuItemSelected(featureId, item);
////	}
//
//	@Override
//	public void onBackPressed() {
//		appManager.finishActivity();
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_commit:
//			YunYingTongJi.yunYingTongJi(FilterConditionActivity.this, 18);
//			if(listMapChecked.size() == 0){
//				ToastUtil.showShortText(FilterConditionActivity.this, "必须选择一项");
//				return ;
//			}
//			isWhere="isFilter";
//			submitCondition(v);
//			break;
//		case R.id.img_back:
//			toggle();
//			break;
//		case R.id.img_most_right_icon:
//			finish();
//			break;
//		default:
//			break;
//		}
//
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
//	public void finish() {
//		// TODO Auto-generated method stub
//		super.finish();
//		this.overridePendingTransition(R.anim.activity_filter_open_tui,R.anim.activity_filter_close_tui);
//	}
//
//	/***
//	 * 提交数据到筛选界面
//	 */
//	private void submitCondition(View v) {
//		LogYiFu.e("TAG", "listMap=" + listMap.toString());
//		// for (int i = 0; i < listMap.size(); i++) {
//		// HashMap<String, String> map = listMap.get(i);
//		// if (map.get("isChecked").equals("1")) {
//		// listMapChecked.add(map);
//		// }
//		// }
////		MobclickAgent.onEvent(this,"FilterConditionActivitySubmit");
//		HashMap<String, Object> mapRequest = new HashMap<String, Object>();
//		Boolean isFilterConditionActivity =true;
//		for (int i = 0; i < listMapChecked.size(); i++) {
//			LogYiFu.e("TAG", "listmapchecked=" + listMapChecked.toString());
//			HashMap<String, String> map = listMapChecked.get(i);
//			int p_id = Integer.valueOf(map.get("p_id"));
////			HashMap<String, String> mapP = 
//			mapRequest.put(map.get("aa"), map);
//			/*switch (p_id) {
//			case 1:
//				mapRequest.put("age", map);
//				break;
//			case 2:
//				mapRequest.put("size", map);
//				break;
//			case 3:
//				mapRequest.put("fix_price", map);
//				break;
//			case 4:
//				mapRequest.put("occasion", map);
//				break;
//			case 5:
//				mapRequest.put("favorite", map);
//				break;
//			case 6:
//				mapRequest.put("sys_color", map);
//				break;
//			case 7:
//				mapRequest.put("pattern", map);
//				break;
//			case 49:
//				mapRequest.put("stuff", map);
//				break;
//			case 50:
//				mapRequest.put("stuff2", map);
//				break;
//			case 51:
//				mapRequest.put("stuff3", map);
//				break;
//			case 52:
//				mapRequest.put("stuff4", map);
//				break;
//			case 9:
//				mapRequest.put("trait", map);
//				break;
//			case 101:
//				mapRequest.put("style", map);
//				break;
//			case 1045:
//				mapRequest.put("trait2", map);
//				break;
//			case 1047:
//				mapRequest.put("trait3", map);
//				break;
//
//			}*/
//		}
//		LogYiFu.i("TAG", "map=" + mapRequest.toString());
//		Intent intent = new Intent(this, FilterResultActivity.class);
//		intent.putExtra("isFilterConditionActivity", isFilterConditionActivity);
//		intent.putExtra("isWhere", isWhere);
//		
//		Bundle bundle = new Bundle();
//		bundle.putSerializable("condition", mapRequest);
//		bundle.putString("id", _id);
//		bundle.putString("title", sort_name);
//		intent.putExtras(bundle);
////		intent.putExtra("checkId", checkId);
//		startActivity(intent);
//
//	}
//
//	private void addItemView() {
//		container_item.removeAllViews();
//		LayoutInflater inflater = LayoutInflater.from(this);
//		for (int i = 0; i < listDatas.size(); i++) {
//			LinearLayout layout = (LinearLayout) inflater.inflate(
//					R.layout.listview_item, null);
//			TextView listview_item_textview = (TextView) layout
//					.findViewById(R.id.listview_item_textview);
//			ImageView img_icon = (ImageView) layout.findViewById(R.id.img_icon);
//			NoScrollGridView listview_item_gridview = (NoScrollGridView) layout
//					.findViewById(R.id.listview_item_gridview);
//			listview_item_gridview.setId(i);
//			listview_item_textview
//					.setText(categoryList.get(i).get("attr_name"));
//			SetImageLoader.initImageLoader(null, img_icon,
//					categoryList.get(i).get("icon"),"");
//			ArrayList<HashMap<String, String>> arrayListForEveryGridView = this.listDatas
//				.get(i);
//			final FilterGridViewAdapter gridViewAdapter = new FilterGridViewAdapter(
//					this, arrayListForEveryGridView);
//			gridViewAdapter.setListener(this);
//			listview_item_gridview.setAdapter(gridViewAdapter);
//			container_item.addView(layout);
//
//		}
//	}
//
//	private void addSelectView() {
//		container.removeAllViews();
//		for (int i = 0; i < linkList.size(); i++) {
//			HashMap<String, String> map = linkList.get(i);
//			TextView tv = new TextView(this);
//			tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//					LayoutParams.WRAP_CONTENT));
//			tv.setText(map.get("attr_name"));
//			container.addView(tv);
//		}
//	}
//
//	@Override
//	public void onCheckCallback(int positon, boolean isChecked,
//			HashMap<String, String> map, FilterGridViewAdapter mAdapter) {
//		// TODO Auto-generated method stub
//		if (isChecked) {
//			List<HashMap<String, String>> list = mAdapter.getData();
//			for (int i = 0; i < list.size(); i++) {
//				list.get(i).put("isChecked", "0");
//			}
//			list.get(positon).put("isChecked", "1");
//			mAdapter.notifyDataSetChanged();
//			// listMap.add(list.get(positon));
//			if(!listMapChecked.contains(list.get(positon))){
//				listMapChecked.add(list.get(positon));
//			}
//
//		} else {
//			List<HashMap<String, String>> list = mAdapter.getData();
//			list.get(positon).put("isChecked", "0");
//			mAdapter.notifyDataSetChanged();
//			// listMap.remove(list.get(positon));
//			listMapChecked.remove(list.get(positon));
//		}
//	}
//
//	// @Override
//	// public void onCheckCallback(int positon, boolean isChecked,
//	// HashMap<String, String> map) {
//	// // TODO Auto-generated method stub
//	// if (isChecked) {
//	// MyLogYiFu.e("_id", map.get("_id"));
//	// linkList.add(map);
//	// addSelectView();
//	// } else {
//	// MyLogYiFu.e("unChecked_id", map.get("_id"));
//	// linkList.remove(map);
//	// addSelectView();
//	// }
//	// }
//
//}
