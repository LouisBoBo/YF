//package com.yssj.ui.activity.league;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import com.yssj.activity.R;
//import com.yssj.app.AppManager;
//import com.yssj.custom.view.NoScrollGridView;
//import com.yssj.data.YDBHelper;
//import com.yssj.ui.adpter.UnionGridViewAdapter;
//import com.yssj.ui.adpter.UnionGridViewAdapter.CheckCallback;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.ImageFileCache;
//import com.yssj.utils.ImageMemoryCache;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ToastUtil;
//
//public class UnionCategoryActivity extends BasicActivity implements
//		CheckCallback, OnClickListener {
//
//	private AppManager appManager;
//	private LinearLayout container, container_item;
//
//	private YDBHelper helper;
//
//	private List<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();
//
//	private List<ArrayList<HashMap<String, String>>> listDatas = new ArrayList<ArrayList<HashMap<String, String>>>();
//
//	private LinkedList<HashMap<String, String>> linkList = new LinkedList<HashMap<String, String>>();
//
//	private List<HashMap<String, String>> listMap = new ArrayList<HashMap<String, String>>();
//	private List<HashMap<String, String>> listMapChecked = new ArrayList<HashMap<String, String>>();
//
//	private LayoutInflater mInflater;
//
//	private int indicatorWidth;
//
//	private ImageMemoryCache memoryCache;
//	private ImageFileCache fileCache;
//
//	private String checkId;
//	
//	private Handler handler;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		aBar.hide();
//		setContentView(R.layout.union_category_activity);
//		helper = new YDBHelper(this);
//		appManager = AppManager.getAppManager();
//		container = (LinearLayout) findViewById(R.id.container);
//		container_item = (LinearLayout) findViewById(R.id.container_item);
//
//		((TextView) findViewById(R.id.tvTitle_base)).setText("类目选择");
//		findViewById(R.id.imgbtn_left_icon).setOnClickListener(this);
//
//		findViewById(R.id.btn_commit).setOnClickListener(this);
//
//		initView();
//		addItemView();
//
//		
//		
//	}
//
//	private void initView() {
//		categoryList = helper
//				.query("select * from bus_tag where p_id=0 and is_show=1 order by sequence");
//		ArrayList<HashMap<String, String>> listChild;
//		for (int i = 0; i < categoryList.size(); i++) {
//			listChild = (ArrayList<HashMap<String, String>>) helper
//					.query("select * from bus_tag where p_id = "
//							+ categoryList.get(i).get("_id")
//							+ " order by sequence");
//			for (HashMap<String, String> map : listChild) {
//				map.put("isChecked", "0");
//			}
//			listDatas.add(listChild);
//		}
//
//	}
//
//	@Override
//	public void onBackPressed() {
//		appManager.finishActivity();
//	}
//
//	private HashMap<String, String> mapChecked = new HashMap<String, String>();
//
//
//	@Override
//	public void onCheckCallback(int positon, boolean isChecked,
//			HashMap<String, String> map, UnionGridViewAdapter mAdapter) {
//		// TODO Auto-generated method stub
//		if (isChecked) {
//			List<HashMap<String, String>> list = mAdapter.getData();
//			for (int i = 0; i < list.size(); i++) {
//				list.get(i).put("isChecked", "0");
//			}
//
//			for (int i = 0; i < listDatas.size(); i++) {
//				List<HashMap<String, String>> data = adapterList.get(i)
//						.getData();
//				for (int j = 0; j < data.size(); j++) {
//					if (data.get(j) == mapChecked) {
//						data.get(j).put("isChecked", "0");
//						adapterList.get(i).notifyDataSetChanged();
//					}
//
//				}
//
//			}
//
//			list.get(positon).put("isChecked", "1");
//			mAdapter.notifyDataSetChanged();
//
//			mapChecked = map;
//			// listMap.add(list.get(positon));
//			// listMapChecked.add(list.get(positon));
//			
//			
//		
//
//		} else {
//			List<HashMap<String, String>> list = mAdapter.getData();
//			list.get(positon).put("isChecked", "0");
//			mAdapter.notifyDataSetChanged();
//			// mapChecked = null;
//			// listMap.remove(list.get(positon));
//			// listMapChecked.remove(list.get(positon));
//		}
//	}
//
//	private List<UnionGridViewAdapter> adapterList = new ArrayList<UnionGridViewAdapter>();
//
//	private void addItemView() {
//		container_item.removeAllViews();
//		LayoutInflater inflater = LayoutInflater.from(this);
//		for (int i = 0; i < listDatas.size(); i++) {
//			LinearLayout layout = (LinearLayout) inflater.inflate(
//					R.layout.listview_item, null);
//			TextView listview_item_textview = (TextView) layout
//					.findViewById(R.id.listview_item_textview);
//			listview_item_textview.setTextColor(Color.rgb(255, 63, 140));
//			ImageView img_icon = (ImageView) layout.findViewById(R.id.img_icon);
//			NoScrollGridView listview_item_gridview = (NoScrollGridView) layout
//					.findViewById(R.id.listview_item_gridview);
//			listview_item_gridview.setId(i);
//			listview_item_textview
//					.setText(categoryList.get(i).get("attr_name"));
//			SetImageLoader.initImageLoader(null, img_icon, categoryList.get(i)
//					.get("icon"),"");
//			ArrayList<HashMap<String, String>> arrayListForEveryGridView = this.listDatas
//					.get(i);
//			final UnionGridViewAdapter gridViewAdapter = new UnionGridViewAdapter(
//					this, arrayListForEveryGridView);
//			gridViewAdapter.setListener(this);
//			adapterList.add(gridViewAdapter);
//			listview_item_gridview.setAdapter(gridViewAdapter);
//			container_item.addView(layout);
//			
//			listview_item_gridview.setTag(gridViewAdapter);
//		}
//	}
//
//	@Override
//	public void onClick(View v) {
//		super.onClick(v);
//		switch (v.getId()) {
//		case R.id.btn_commit:
//			if (null == mapChecked.get("isChecked")
//					|| mapChecked.get("isChecked").equals("0")) {
//				ToastUtil.showShortText(this, "请选择商家所属类目");
//				return;
//			}
//			
//			Intent intent = getIntent();
//			Bundle bundle = new Bundle();
//			bundle.putSerializable("mapChecked", (Serializable) mapChecked);
//			intent.putExtras(bundle);
//			setResult(RESULT_OK, intent);
//			this.finish();
//			
//			break;
//		case R.id.imgbtn_left_icon:
//			this.finish();
//			break;
//		default:
//			break;
//		}
//	}
//}
