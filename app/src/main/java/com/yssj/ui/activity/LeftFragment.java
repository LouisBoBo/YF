//package com.yssj.ui.activity;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.text.Html;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnFocusChangeListener;
//import android.view.ViewGroup;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.TextView.OnEditorActionListener;
//
//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
//import com.yssj.activity.R;
//import com.yssj.custom.view.EditTextWithDel;
//import com.yssj.data.YDBHelper;
//import com.yssj.ui.activity.main.SearchResultActivity;
//import com.yssj.ui.activity.main.WordSearchResultActivity;
//import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
//import com.yssj.utils.DP2SPUtil;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.StringUtils;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YunYingTongJi;
//
//public class LeftFragment extends Fragment implements OnCloseListener, OnClosedListener {
//
//	private EditTextWithDel et_search;
//	private LinearLayout data_container;
//
//	private YDBHelper dbHelp;
//	private List<HashMap<String, String>> listFLevel;
//	// 存放于 所查询的所有数据
//	private LinkedHashMap<String, List<HashMap<String, String>>> mapAll = new LinkedHashMap<String, List<HashMap<String, String>>>();
//
//	private static Context mContext;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		SlidingMenu sm = MainMenuActivity.instances.getSlidingMenu();
//
//		sm.setOnCloseListener(this);
//		sm.setOnClosedListener(this);
//	}
//
//	public static LeftFragment newInstance(Context context) {
//		LeftFragment mainFragment = new LeftFragment();
//		mContext = context;
//		return mainFragment;
//	}
//
//	@Override
//	public void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		// MobclickAgent.onPageEnd("LeftFragment");
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		// MobclickAgent.onPageStart("LeftFragment"); //统计页面
//		if (null != mContext)
//			dbHelp = new YDBHelper(mContext);
//		else
//			dbHelp = new YDBHelper(getActivity());
//		String sql = "select * from sort_info where p_id = 0 and is_show = 1 and sort_name <> '热卖' order by sequence ";
//		listFLevel = dbHelp.query(sql);
//		for (int i = 0; i < listFLevel.size(); i++) {
//			HashMap<String, String> item = listFLevel.get(i);
//			sql = "select * from sort_info where p_id = " + item.get("_id") + " order by _id";
//			List<HashMap<String, String>> listSLevel = dbHelp.query(sql);
//			mapAll.put(item.get("sort_name"), listSLevel);
//		}
//		addView(data_container, mapAll);
//	}
//
//	@Override
//	public void onHiddenChanged(boolean hidden) {
//		super.onHiddenChanged(hidden);
//		if (!hidden) {
//			if (null != mContext)
//				dbHelp = new YDBHelper(mContext);
//			else
//				dbHelp = new YDBHelper(getActivity());
//			String sql = "select * from sort_info where p_id = 0 and is_show = 1 and sort_name <> '热卖' order by sequence ";
//			listFLevel = dbHelp.query(sql);
//			for (int i = 0; i < listFLevel.size(); i++) {
//				HashMap<String, String> item = listFLevel.get(i);
//				sql = "select * from sort_info where p_id = " + item.get("_id") + " order by _id";
//				List<HashMap<String, String>> listSLevel = dbHelp.query(sql);
//				mapAll.put(item.get("sort_name"), listSLevel);
//			}
//
//			addView(data_container, mapAll);
//		} else {
//			InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//			imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
//		}
//	}
//
//	private void addView(LinearLayout container, HashMap<String, List<HashMap<String, String>>> mapAll) {
//		container.removeAllViews();
//
//		LayoutInflater inflater = LayoutInflater.from(getActivity());
//		Iterator<Entry<String, List<HashMap<String, String>>>> iterator = mapAll.entrySet().iterator();
//		List<HashMap<String, String>> list_temp1 = mapAll.get("上衣");
//		List<HashMap<String, String>> list_temp2 = mapAll.get("外套");
//		boolean flag1=false;
//		boolean flag2=false;//flag1和2是用来判断上衣和外套那个在前面
//		// while (iterator.hasNext()) {
//		for (int j = 0; j < mapAll.size(); j++) {
//			Map.Entry<String, List<HashMap<String, String>>> entry = iterator.next();
//			List<HashMap<String, String>> listData = entry.getValue();
//			String fLevel = entry.getKey();
//			if("上衣".equals(fLevel)&&!flag2){
//				flag1=true;
//				fLevel="外套";
//				listData=list_temp2;
//			}else if("外套".equals(fLevel)){
//				if(!flag1){
//					flag2=true;
//				}else{
//				fLevel="上衣";
//				listData=list_temp1;
//				}
//			}
//			View view = inflater.inflate(R.layout.first_level_item, null);
//			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
//			tv_name.setText(fLevel);
//			LinearLayout lin_second_container = (LinearLayout) view.findViewById(R.id.lin_second_container);
//			for (int i = 0; i < listData.size(); i++) {
//				final HashMap<String, String> item = listData.get(i);
//				View v = inflater.inflate(R.layout.second_level_item, null);
//				TextView tv_second_name = (TextView) v.findViewById(R.id.tv_second_name);
//				ImageView img_icon = (ImageView) v.findViewById(R.id.img_icon);
//				SetImageLoader.initImageLoader(getActivity(), img_icon, listData.get(i).get("icon"), "");
//				tv_second_name.setText(listData.get(i).get("sort_name"));
//				v.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View arg0) {
//						// TODO Auto-generated method stub
//
//						// intent = new Intent();
//						// MainMenuActivity cxt = (MainMenuActivity)
//						// this.getActivity();
//						// intent.setClassName(cxt,"com.yssj.ui.activity.main.FilterConditionActivity");//打开一个activity
//						// cxt.startActivity(intent);
//						// cxt.overridePendingTransition(R.anim.activity_filter_open,R.anim.activity_filter_close);
//
//						int id = Integer.parseInt(item.get("_id"));
//
//						switch (id) {
//						case 15:
//							YunYingTongJi.yunYingTongJi(mContext, 21);
//
//							break;
//						case 16:
//							YunYingTongJi.yunYingTongJi(mContext, 22);
//
//							break;
//						case 17:
//							YunYingTongJi.yunYingTongJi(mContext, 23);
//
//							break;
//						case 18:
//							YunYingTongJi.yunYingTongJi(mContext, 24);
//
//							break;
//
//						case 19:
//							YunYingTongJi.yunYingTongJi(mContext, 25);
//
//							break;
//						case 20:
//							YunYingTongJi.yunYingTongJi(mContext, 26);
//							break;
//
//						case 21:
//							YunYingTongJi.yunYingTongJi(mContext, 27);
//							break;
//
//						case 22:
//							YunYingTongJi.yunYingTongJi(mContext, 28);
//
//							break;
//						case 23:
//							YunYingTongJi.yunYingTongJi(mContext, 29);
//
//							break;
//						case 24:
//							YunYingTongJi.yunYingTongJi(mContext, 30);
//
//							break;
//
//						case 25:
//							YunYingTongJi.yunYingTongJi(mContext, 31);
//
//							break;
//
//						case 26:
//							YunYingTongJi.yunYingTongJi(mContext, 32);
//							break;
//							
//						case 27:
//							YunYingTongJi.yunYingTongJi(mContext, 33);
//							break;
//							
//						case 28:
//							YunYingTongJi.yunYingTongJi(mContext, 34);
//							
//							break;
//						case 29:
//							YunYingTongJi.yunYingTongJi(mContext, 35);
//							break;
//							
//						case 30:
//							YunYingTongJi.yunYingTongJi(mContext, 36);
//							break;
//
//						case 31:
//							YunYingTongJi.yunYingTongJi(mContext, 37);
//
//							break;
//						case 32:
//							YunYingTongJi.yunYingTongJi(mContext, 38);
//
//							break;
//						case 33:
//							YunYingTongJi.yunYingTongJi(mContext, 39);
//
//							break;
//
//						case 37:
//							YunYingTongJi.yunYingTongJi(mContext, 40);
//
//							break;
//
//						case 38:
//							YunYingTongJi.yunYingTongJi(mContext, 41);
//
//							break;
//						case 10:
//							YunYingTongJi.yunYingTongJi(mContext, 42);
//
//							break;
//
//						case 11:
//							YunYingTongJi.yunYingTongJi(mContext, 43);
//
//							break;
//
//						case 12:
//							YunYingTongJi.yunYingTongJi(mContext, 44);
//
//							break;
//
//						case 13:
//							YunYingTongJi.yunYingTongJi(mContext, 45);
//
//							break;
//
//						case 14:
//							YunYingTongJi.yunYingTongJi(mContext, 46);
//
//							break;
//
//						case 34:
//							YunYingTongJi.yunYingTongJi(mContext, 47);
//
//							break;
//
//						case 35:
//							YunYingTongJi.yunYingTongJi(mContext, 48);
//
//							break;
//
//						case 36:
//							YunYingTongJi.yunYingTongJi(mContext, 49);
//
//							break;
//
//						default:
//							break;
//						}
//
//						Intent intent = new Intent();
//
//						intent.setClass(getActivity(), SearchResultActivity.class);
//
//						Bundle bundle = new Bundle();
//						bundle.putSerializable("item", item);
//						intent.putExtras(bundle);
//						getActivity().startActivity(intent);
//						getActivity().overridePendingTransition(R.anim.activity_from_right,
//								R.anim.activity_search_close);
//					}
//				});
//				lin_second_container.addView(v);
//			}
//
//			container.addView(view);
//		}
//	}
//
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		View v = inflater.inflate(R.layout.left_fragment, container, false);
//		data_container = (LinearLayout) v.findViewById(R.id.container);
//		// addView(data_container, mapAll);
//		et_search = (EditTextWithDel) v.findViewById(R.id.et_search);
//		initEdittext();
//		et_search.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View arg0, boolean arg1) {
//				// TODO Auto-generated method stub
//				if (arg1) {
//					et_search.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
//					et_search.setTag(et_search.getHint());
//					et_search.setHint("");
//				} else {
//					et_search.setGravity(Gravity.CENTER);
//					et_search.setText("");
//					et_search.setHint((CharSequence) et_search.getTag());
//				}
//			}
//		});
//		et_search.setOnEditorActionListener(new OnEditorActionListener() {
//
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//					// 先隐藏键盘
//					((InputMethodManager) et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
//							.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
//									InputMethodManager.HIDE_NOT_ALWAYS);
//
//					if (et_search.getText().toString().trim().equals("")) {
//						ToastUtil.showShortText(getActivity(), "请输入搜索条件");
//						return false;
//					}
//					if (StringUtils.containsEmoji(et_search.getText().toString().trim())) {
//						ToastUtil.showShortText(getActivity(), "搜索条件不能含特殊字符");
//					}
//
//					// String id = dbHelp.queryAttr_id(et_search.getText()
//					// .toString().trim());
//					// 跳转activity
//
//					YunYingTongJi.yunYingTongJi(mContext, 20);
//
//					Intent intent = new Intent();
//					intent = new Intent(getActivity(), WordSearchResultActivity.class);
//					// if (null == id)
//					intent.putExtra("words", et_search.getText().toString().trim());
//					// else
//					// intent.putExtra("_id", id);
//					startActivity(intent);
//
//					return true;
//				}
//				return false;
//			}
//
//		});
//		return v;
//	}
//
//	private void initEdittext() {
//		final String hintText = "<img src=\"" + R.drawable.et_search + "\" /> 搜索";
//		et_search.setHint(Html.fromHtml(hintText, imageGetter, null));
//		et_search.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
//		et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
//
//	}
//
//	Html.ImageGetter imageGetter = new Html.ImageGetter() {
//
//		@Override
//		public Drawable getDrawable(String source) {
//			Drawable drawable = null;
//			int rId = Integer.parseInt(source);
//			drawable = getResources().getDrawable(rId);
//			// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//			// drawable.getIntrinsicHeight());
//			drawable.setBounds(DP2SPUtil.dp2px(getActivity(), 0), DP2SPUtil.dp2px(getActivity(), 0),
//					DP2SPUtil.dp2px(getActivity(), 17), DP2SPUtil.dp2px(getActivity(), 17));
//			return drawable;
//		}
//	};
//
//	@Override
//	public void onClose() {
//		// TODO Auto-generated method stub
//		InputMethodManager imm = (InputMethodManager) et_search.getContext()
//				.getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
//	}
//
//	@Override
//	public void onClosed() {
//		// TODO Auto-generated method stub
//		InputMethodManager imm = (InputMethodManager) et_search.getContext()
//				.getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
//	}
//
//}
