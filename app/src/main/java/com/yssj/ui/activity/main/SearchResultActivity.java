package com.yssj.ui.activity.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.CustImageGallery;
import com.yssj.data.YDBHelper;
import com.yssj.model.ComModel2;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.adpter.StaggeredAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.LeaveDialog;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchResultActivity extends BasicActivity 
		{
	private int a=-4;
	private HashMap<String, String> mapId;
	private String id = -1 + "";
	private PullToRefreshListView r_list_view;

	private StaggeredAdapter mAdapter;
	
	private boolean isSign;

	private int index = 1;

	private int mType = 1;// 1：初始化数据；2：加载更多数据

	private RadioGroup rd_g;

	private List<HashMap<String, String>> mapThird;// 第三级

	private YDBHelper helper;
	// private CategoryView cat_view;

	private String level = 2 + "";

	private int pageSize = 10;

	private boolean isComplete = false;// 当数据少时，下拉刷新 既调用刷新 又调用 加载跟多，
										// 所以当每次返回的数据<pageSize的时候
										// isComplete为true

	private HorizontalScrollView hsv, title_hsv;
	private LinearLayout lin_bar;

	private LayoutInflater mInflater;

	private LinearLayout title_lin,root;
	private TextView tvTitle_base;
	
	private TextView tv_no_data;
	
	private ImageButton btn_right;
	
	private String is_new = null, order_by_price = null;
	
	private boolean notType = false;
	
	private String sort_name;
	private boolean singliulan = false;
	private String oldId;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
//		aBar.hide();
		helper = new YDBHelper(this);
		mapId = (HashMap<String, String>) getIntent().getSerializableExtra(
				"item");
		
		singliulan =  getIntent().getBooleanExtra("singliulan", false);
			
		if(null != mapId){
			id = mapId.get("_id");
			oldId = id;
			sort_name = mapId.get("sort_name");
		}else{
			id = getIntent().getStringExtra("id");
			sort_name = helper.querySort_name(id);
			oldId = id;
		}
		
		isWhere = getIntent().getStringExtra("isWhere");
		isSign = getIntent().getBooleanExtra("isSign", false);
		
		mapThird = helper.query("select * from sort_info where p_id =" + id+" order by group_flag");
		
		setContentView(R.layout.list_result);
		//
		// TextView tvResult = (TextView) findViewById(R.id.tv_result);
		// tvResult.setText(result);
		// tvResult.setOnClickListener(this);
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		String shop_name=getIntent().getStringExtra("shop_name");
//		if(null!=shop_name&&!("".equals(shop_name))){
//			tvTitle_base.setText(shop_name);
//		}else{
		tvTitle_base.setText(sort_name);
//		}
		root = (LinearLayout) findViewById(R.id.root);
		root.setBackgroundColor(Color.WHITE);
		findViewById(R.id.img_back).setOnClickListener(this);
		r_list_view = (PullToRefreshListView) findViewById(R.id.r_list_view);
		tv_no_data = (TextView) findViewById(R.id.tv_no_data);
		btn_right = (ImageButton) findViewById(R.id.btn_right);
		btn_right.setOnClickListener(this);
		initRBView();
//		if (isWhere.equals("isCustImageGallery")) {
//			id="isCustImageGallery";
//		}
		mAdapter = new StaggeredAdapter(this,id,isWhere);
		r_list_view.setAdapter(mAdapter);
		r_list_view.setMode(Mode.BOTH);
		r_list_view.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				mType = 1;
				index = 1;
				initData(index + "", id, level,is_new,order_by_price, notType);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				//if (!isComplete) {
					index++;
					mType = 2;
					initData(index + "", id, level,is_new,order_by_price,notType);
				//}
			}
		});
		
		initData(index + "", id, level,is_new,order_by_price,notType);
	}

	private List<ToggleButton> tbs = new ArrayList<ToggleButton>();
	
	private void initRBView() {
		title_lin = (LinearLayout) findViewById(R.id.title_lin);
		hsv = (HorizontalScrollView) findViewById(R.id.hsv);
		lin_bar = (LinearLayout) findViewById(R.id.lin_bar);
		title_hsv = (HorizontalScrollView) findViewById(R.id.title_hsv);
		mInflater = LayoutInflater.from(this);
		for (int i = 0; i < mapThird.size(); i++) {

			final HashMap<String, String> map = mapThird.get(i);
			final HashMap<String, Object> mapObj = new HashMap<String, Object>();
			View view = mInflater.inflate(R.layout.nav_toggle_button, null);
			final ToggleButton tb = (ToggleButton) view
					.findViewById(R.id.gridview_item_button);
			tb.setId(i);
			tb.setText(mapThird.get(i).get("sort_name"));
			tb.setTextOff(mapThird.get(i).get("sort_name"));
			tb.setTextOn(mapThird.get(i).get("sort_name"));
			tb.setGravity(Gravity.CENTER);
			tbs.add(tb);
			lin_bar.addView(view);
			tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					mapObj.put("toggle", tb);
					mapObj.put("map", map);
//					map.put("isCheck", ""+arg1);
//					listChecked.add(map);
//					setToggled(map);
					setCheckData(mapObj, arg1);
				}
			});
		}

		
	}
	@Override
	protected void onResume() {
		super.onResume();
		HomeWatcherReceiver.registerHomeKeyReceiver(this);
		
		a=CustImageGallery.fg;
		
		Pattern p = Pattern.compile("[0-9]*");
		Matcher m = p.matcher("" + id);
		
		if (a==4 ) {
			SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1005");
//			SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_PAGE, "风格4");
			HomeWatcherReceiver.registerHomeKeyReceiver(this);
			CustImageGallery.fg=-6;
		}else if (a==8) {
			SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1009");
//			SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_PAGE, "风格8");
			HomeWatcherReceiver.registerHomeKeyReceiver(this);
			CustImageGallery.fg=-6;
		}
		else if (m.matches()) {

			if (id != null && !"".equals(id) && !"null".equals(id)) {
				switch (Integer.parseInt(id)) {
				case 15:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1021");
					break;
				case 16:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1022");
					break;
				case 17:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1023");
					break;
				case 18:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1024");
					break;

				case 19:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1025");
					break;
				case 20:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1026");
					break;

				case 21:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1027");
					break;

				case 22:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1028");
					break;
				case 23:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1029");
					break;
				case 24:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1030");
					break;

				case 25:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1031");
					break;

				case 26:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1032");
					break;
					
				case 27:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1033");
					break;
					
				case 28:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1034");
					
					break;
				case 29:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1035");
					break;
					
				case 30:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1036");
					break;

				case 31:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1037");
					break;
				case 32:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1038");
					break;
				case 33:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1039");
					break;

				case 37:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1040");
					break;

				case 38:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1041");
					break;
				case 10:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1042");
					break;

				case 11:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1043");
					break;

				case 12:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1044");
					break;

				case 13:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1045");
					break;

				case 14:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1046");
					break;

				case 34:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1047");
					break;

				case 35:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1048");
					break;

				case 36:
					SharedPreferencesUtil.saveStringData(SearchResultActivity.this, Pref.TONGJI_TYPE, "1049");
					break;

				default:
					break;
				}
			}
		}

	}
	@Override
	protected void onPause() {
		super.onPause();
		HomeWatcherReceiver.unregisterHomeKeyReceiver(this);

	}
	private List<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();//被选中的map

	private void setCheckData(HashMap<String, Object> map, boolean isChecked) {
		if (isChecked) {
			listData.add(map);
			HashMap<String, String > mapAA = (HashMap<String, String>) map.get("map");
			for(int j=0; j<mapThird.size(); j++){
				if(mapThird.get(j).equals(mapAA)){
//					ToggleButton tb = tbs.get(j);
//					tb.setTextColor(getResources().getColor(R.color.selector_category_text));
					ToggleButton tb = tbs.get(j);
					tb.setClickable(true);
					tb.setBackgroundResource(R.drawable.bg_choice_btn_checked);
					tb.setTextColor(getResources().getColor(R.color.white));
					continue;
				}else if(mapThird.get(j).get("group_flag").equals(mapAA.get("group_flag"))){
					ToggleButton tb = tbs.get(j);
					tb.setClickable(false);
					tb.setBackgroundResource(R.drawable.bg_choice_btn_unclickable);
					tb.setTextColor(getResources().getColor(R.color.f1));
				}
			}
		} else {
			listData.remove(map);
			HashMap<String, String > mapAA = (HashMap<String, String>) map.get("map");
			for(int i = 0; i < mapThird.size(); i++){
				if(mapThird.get(i).get("group_flag").equals(mapAA.get("group_flag"))){
					ToggleButton tb = tbs.get(i);
					tb.setClickable(true);
					tb.setBackgroundResource(R.drawable.selector_category_bg);
					tb.setTextColor(getResources().getColor(R.color.selector_category_text));
				}
			}
		}
		StringBuffer _ids = new StringBuffer();
		for (int i = 0; i < listData.size(); i++) {
			HashMap<String, Object> mapObj = listData.get(i);
			HashMap<String, String> mapChecked = (HashMap<String, String>) mapObj.get("map");
			_ids.append(mapChecked.get("_id"));
			
			if (i != listData.size() - 1) {
				_ids.append(",");
			}
			
			/*for(int j=0; j<mapThird.size(); j++){
				if(mapThird.get(j).equals(mapChecked)){
//					ToggleButton tb = tbs.get(j);
//					tb.setTextColor(getResources().getColor(R.color.selector_category_text));
					continue;
				}else if(mapThird.get(j).get("group_flag").equals(mapChecked.get("group_flag"))){
					ToggleButton tb = tbs.get(j);
					tb.setClickable(false);
					tb.setBackgroundResource(R.drawable.bg_choice_btn_unclickable);
//					tb.setTextColor(getResources().getColor(R.color.white));
				}
//				else{
//					ToggleButton tb = tbs.get(j);
//					tb.setClickable(true);
//					tb.setBackgroundResource(R.drawable.selector_category_bg);
////					tb.setTextColor(getResources().getColor(R.color.selector_category_text));
//				}
			}*/
			
		}

		
		
		mType = 1;
		index = 1;
		
		mAdapter.clearData();
		if (listData.size() != 0) {
			level = 3 + "";
			title_hsv.setVisibility(View.VISIBLE);
			tvTitle_base.setVisibility(View.GONE);
			showTitleView(listData);
			id=_ids.toString();
			notType = true;
			initData(index + "", id , level,is_new,order_by_price, notType);
		} else {
			title_hsv.setVisibility(View.GONE);
			tvTitle_base.setVisibility(View.VISIBLE);
			level=2+"";
//			id = mapId.get("_id");
			id = oldId;
			notType = false;
			initData(index + "", id, level,is_new,order_by_price, notType);
			
		}
	}

	private void showTitleView(final List<HashMap<String, Object>> listData) {
		title_lin.removeAllViews();
		for (int i = 0; i < listData.size(); i++) {
			final HashMap<String, Object> mapObj = listData.get(i);
			final HashMap<String, String> map = (HashMap<String, String>) mapObj.get("map");
			View v = mInflater.inflate(
					R.layout.textview_search, null);
			TextView tv = (TextView) v.findViewById(R.id.tv_title);
			tv.setText(map.get("sort_name"));
			title_lin.addView(v);
			final ToggleButton tb = (ToggleButton) mapObj.get("toggle");
			tv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
//					listData.remove(map);
					tb.setChecked(false);
					setCheckData(mapObj, false);
				}
			});
		}
	}

	private void initData(String index, String id, String level, final String is_new, final String order_by_price, final boolean notType) {
		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					List<HashMap<String, Object>> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if(e==null&&result!=null){
				if (mType == 1) {
					mAdapter.addItemTop(result);
					isComplete = false;
					if(result.size() == 0){
						tv_no_data.setVisibility(View.VISIBLE);
						r_list_view.setVisibility(View.GONE);
					}else{
						tv_no_data.setVisibility(View.GONE);
						r_list_view.setVisibility(View.VISIBLE);
					}
				} else if (mType == 2) {
					mAdapter.addItemLast(result);
				}
				}
				mAdapter.notifyDataSetChanged();
				r_list_view.onRefreshComplete();
			}

			@Override
			protected boolean isHandleException() {
				
				return true;
			}



			@Override
			protected List<HashMap<String, Object>> doInBackground(
					FragmentActivity context, String... params)
					throws Exception {
				// TODO Auto-generated method stub
				return YJApplication.instance.isLoginSucess()?ComModel2.getProductListBySearch(context, params[0], params[1],
						params[2], pageSize,is_new,order_by_price, notType, oldId):
							ComModel2.getProductList2UnLoginBySearch(context, params[0], params[1],
									params[2], pageSize,is_new,order_by_price, notType, oldId);
			}

		}.execute(index, id, level);
	}
	@Override
	public void onBackPressed() {
	
		if(isSign&&!SignListAdapter.isForceLookTimeOut&&!SignListAdapter.isSignComplete) {//赚钱任务浏览分钟数 并且时间没有到 并且是没有完成的任务
			LeaveDialog leaveDialog = new LeaveDialog(this);
			leaveDialog.show();
			leaveDialog.setContentText("你正在进行浏览商品任务，浏览时长还未完成，你可以选择去浏览其它商品，浏览时长达到任务要求即可完成任务喔~");
			leaveDialog.setButtonText("不了谢谢", "其他商品");
			View btn_left = leaveDialog.findViewById(R.id.btn_left);
			View btn_right = leaveDialog.findViewById(R.id.btn_right);
			btn_right.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					CommonUtils.finishActivity(MainMenuActivity.instances);

					Intent intent = new Intent((Activity) context, MainMenuActivity.class);
					intent.putExtra("toYf", "toYf");
					context.startActivity(intent);
				}
			});
			
			btn_left.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
					overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
				}
			});
			
			

		}else{
			finish();
			overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.img_back:// 返回上一层
			onBackPressed();
			break;
		case R.id.btn_right:
			OrderByPopupWindow popupWindow = new OrderByPopupWindow(this);
			popupWindow.showAsDropDown(btn_right, 0, 10);
			break;
		default:
			break;
		}
	}
	
private boolean isLou = true;
private String isWhere="";
	
	/** 只看楼主和收藏帖子的popupwindow */
	private class OrderByPopupWindow extends PopupWindow implements
			OnClickListener {
		private Context mContext;
		private LinearLayout create_is_new, create_price_desc, create_price_asc;
		private String[] names;

		private TextView is_lou;

		public OrderByPopupWindow(Context context) {
			super(context);
			mContext = context;
			init();
		}

		public OrderByPopupWindow(Context context, String[] names) {
			super(context);
			mContext = context;
			this.names = names;
			init();
		}

		private void init() {
			setWidth(LayoutParams.WRAP_CONTENT);
			setHeight(LayoutParams.WRAP_CONTENT);
			ColorDrawable dw = new ColorDrawable(0x00);
			setBackgroundDrawable(dw);
			View view = LayoutInflater.from(mContext).inflate(
					R.layout.popup_order, null);
			create_is_new = (LinearLayout) view
					.findViewById(R.id.create_is_new);
			create_price_desc = (LinearLayout) view
					.findViewById(R.id.create_price_desc);
			create_price_asc = (LinearLayout) view
					.findViewById(R.id.create_price_asc);
			
			create_is_new.setOnClickListener(this);
			create_price_desc.setOnClickListener(this);
			create_price_asc.setOnClickListener(this);
			setContentView(view);
			setAnimationStyle(R.style.PopupWindowAnimation);
			setOutsideTouchable(true);
			setFocusable(true);
		}

		@Override
		public void onClick(View v) {
			// Intent intent = new Intent();
			// int enterAnimID = R.anim.slide_right_in;
			// int exitAnimID = R.anim.slide_left_out;
			int id = v.getId();
			if (id == R.id.create_is_new) {
				is_new = "is_new";
				order_by_price = null;
			} else if (id == R.id.create_price_desc) {
				is_new = null;
				order_by_price = "desc";
			}else if(id == R.id.create_price_asc){
				is_new = null;
				order_by_price = "asc";
			}
			notType = true;
			mType = 1;
			index = 1;
			initData(index + "", SearchResultActivity.this.id, level,is_new,order_by_price, notType);
			// enterAnimID = R.anim.slide_down_in;
			// exitAnimID = R.anim.slide_top_in;
			dismiss();
		}

	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0==101&&arg1==102){
			CommonUtils.finishActivity(MainMenuActivity.instances);

			Intent intent = new Intent(this, MainMenuActivity.class);
			intent.putExtra("index", 3);
			startActivity(intent);
			finish();
		}
	}

}
