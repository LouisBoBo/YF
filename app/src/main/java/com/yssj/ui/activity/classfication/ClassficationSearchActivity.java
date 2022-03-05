
package com.yssj.ui.activity.classfication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.FlowLayout;
import com.yssj.data.YDBHelper;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.main.WordSearchResultActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;
import com.yssj.utils.sqlite.RecordsDao;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * 分类搜索界面
 */
public class ClassficationSearchActivity extends BasicActivity implements View.OnClickListener{
	private Bitmap bitmap_bg;
	private EditText et_search;
	private ImageView et_search_xx;
	private Context mContext;
	private RecordsDao recordsDao;//搜索历史的数据库
	private YDBHelper dbHelp;
	private LayoutInflater mInflater;
	private List<HashMap<String, String>> mDatasHis, mDatasHot;
	private View historical_search,imageBack;
	private FlowLayout historical_search_flowlayout, hot_search_flowlayout;// 历史搜索  热门搜索
	private View historical_search_rootview;
	private ListView lvTips;//搜索提示列表
	private List<HashMap<String, String>> mTipsList;
	private SimpleAdapter tipsAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.activity_search_classfication);
	    setViewBg();
	    mContext = this;
	    recordsDao = new RecordsDao(mContext);
		dbHelp = new YDBHelper(mContext);
	    et_search = (EditText) findViewById(R.id.et_search);
		et_search_xx = (ImageView) findViewById(R.id.et_search_xx);
		et_search_xx.setOnClickListener(this);
		imageBack =findViewById(R.id.img_back);
		imageBack.setOnClickListener(this);
		lvTips = (ListView) findViewById(R.id.activity_search_classfication_tips_lv);
		historical_search_rootview = findViewById(R.id.historical_search_rootview);
		mTipsList =new ArrayList<HashMap<String,String>>();
		
		tipsAdapter = new SimpleAdapter(mContext, mTipsList, R.layout.activity_search_tips_list_item,
				new String[]{"attr_name"}, new int[]{R.id.tips_list_item_tv});
		lvTips.setAdapter(tipsAdapter);
		setTipsClickListener();
		
		mInflater = LayoutInflater.from(mContext);
		mDatasHis = new ArrayList<HashMap<String, String>>();
		mDatasHot = new ArrayList<HashMap<String, String>>();
		
		historical_search = findViewById(R.id.historical_search);
		findViewById(R.id.historical_search_delete_icon).setOnClickListener(this);
		historical_search_flowlayout = (FlowLayout) findViewById(R.id.historical_search_flowlayout);
		historical_search_flowlayout.setMaxLine(3);
		hot_search_flowlayout = (FlowLayout)findViewById(R.id.hot_search_flowlayout);
	    setEditextLstener();
	    setEditextChangedListener();
	    getHotTag();
	}

	/**
	 * 设置模糊背景 不可滚动
	 */
	private void setViewBg() {
		ImageView root_iv = (ImageView) findViewById(R.id.root_view_iv);
		if(getIntent()!=null){
			byte [] bis=getIntent().getByteArrayExtra("bitmapDatas");  
	        bitmap_bg=BitmapFactory.decodeByteArray(bis, 0, bis.length);  
		}
	    if(bitmap_bg!=null){
	    	root_iv.setImageBitmap(bitmap_bg);
	    }
	    ScrollView bgScroll = (ScrollView) findViewById(R.id.bg_scroll);
	    bgScroll.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
	}
	
	/**
	 * 获取热门搜索标签
	 */
	private void getHotTag() {

		new SAsyncTask<Void, Void, List<HashMap<String, String>>>((FragmentActivity) mContext, R.string.wait) {

			@Override
			protected List<HashMap<String, String>> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				return ComModel2.getHotTag(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, String>> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null && result != null && result.size() > 0) {

					for (int i = 0; i < result.size(); i++) {
						String tag_id = result.get(i).get("tag_id");
						String hotSql = "select * from tag_info where _id = " + tag_id;
						List<HashMap<String, String>> datasHot = dbHelp.query(hotSql);
						if (i == 0 && datasHot.size() > 0) {
							et_search.setHint(datasHot.get(0).get("attr_name"));
						}
						mDatasHot.addAll(dbHelp.query(hotSql));
					}
					initHotChildViews(mDatasHot, 1);// type_tag 和 tag_info
													// name键值 不同
				} else {
					String hotSql = "select * from type_tag where type = " + 0 + " order by _id";// 没有数据时候 使用流行趋势填充
					mDatasHot = dbHelp.query(hotSql);
					initHotChildViews(mDatasHot, 0);// type_tag 和 tag_info name键值 不同
				}

			}
		}.execute();
	}
	
	
	/**
	 * 填充热门搜索
	 */
	public void initHotChildViews(List<HashMap<String, String>> mDatasHot, int type) {
		for (int i = 0; i < mDatasHot.size(); i++) {
			TextView tv = (TextView) mInflater.inflate(R.layout.search_label_tv, hot_search_flowlayout, false);
			String name = "";// type_tag 和 tag_info name键值 不同
			if (type == 1) {
				name = mDatasHot.get(i).get("attr_name");
			} else {
				name = mDatasHot.get(i).get("class_name");
			}
			tv.setText(name);
			final String words = name;
			// 点击事件
			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent = new Intent(mContext, WordSearchResultActivity.class);
					intent.putExtra("words", words.trim());
					intent.putExtra("notType", true);
					startActivity(intent);
				}
			});
			hot_search_flowlayout.addView(tv);// 添加到父View
		}
	}

	/**
	 * 填充历史搜索
	 * 
	 * @date 2016年12月29日下午3:32:37
	 */
	public void initHistoryChildViews() {
		/**
		 * 找到搜索标签的控件
		 */
		historical_search_flowlayout.removeAllViews();
		for (int i = 0; i < mDatasHis.size(); i++) {
			TextView tv = (TextView) mInflater.inflate(R.layout.search_label_tv, historical_search_flowlayout, false);
			// final String class_id = mDatasHis.get(i).get("class_id");
			final String words = mDatasHis.get(i).get("class_name");
			tv.setText(words);
			// 点击事件
			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent = new Intent(mContext, WordSearchResultActivity.class);
					intent.putExtra("words", words.trim());
					intent.putExtra("notType", true);
					startActivity(intent);
				}
			});
			historical_search_flowlayout.addView(tv);// 添加到父View
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.historical_search_delete_icon:
			deleteHositorySearch();
			break;
		case R.id.img_back:
			onBackPressed();
			et_search.clearFocus();// 输入框失去焦点
			break;
		case R.id.et_search_xx:
			et_search.getText().clear();
			break;
		default:
			break;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		et_search.getText().clear();
		mDatasHis.clear();
		mDatasHis = recordsDao.getRecordsList();
		if (mDatasHis.size() == 0) {
			historical_search.setVisibility(View.GONE);
		} else {
			historical_search.setVisibility(View.VISIBLE);
			initHistoryChildViews();
		}
		
		mTipsList.clear();
		historical_search_rootview.setVisibility(View.VISIBLE);
		lvTips.setVisibility(View.GONE);
	}
	
	/**
	 * 设置编辑框的监听
	 */
	private void setEditextLstener() {

		et_search.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					String words = et_search.getText().toString().trim();
					// 先隐藏键盘
					((InputMethodManager) et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(ClassficationSearchActivity.this.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);

					if (words.equals("")) {
						ToastUtil.showShortText(mContext, "请输入搜索条件");
						return false;
					}
					if (StringUtils.containsEmoji(words)) {
						ToastUtil.showShortText(mContext, "搜索条件不能含特殊字符");
						return false;
					}
//					et_search.getText().clear();
					HashMap<String, String> recordsMap = new HashMap<String, String>();
					recordsMap.put("class_name",words);
					recordsMap.put("class_id", "");
					recordsMap.put("user_id", YJApplication.instance.isLoginSucess()
							? YCache.getCacheUserSafe(mContext).getUser_id() + "" : "-1");// 区分用户
																							// 未登录时候默认id
																							// ：-1
					recordsDao.addRecords(recordsMap);// 添加搜索记录
					Intent intent = new Intent();
					intent = new Intent(mContext, WordSearchResultActivity.class);
					// if (null == id)
					intent.putExtra("words", words);
					intent.putExtra("notType", true);
					// else
					// intent.putExtra("_id", id);
					startActivity(intent);
					return true;
				}
				return false;
			}

		});
	}
	
	/**
	 * 删除历史记录
	 */
	private void deleteHositorySearch() {
		final Dialog dialog = new Dialog(mContext, R.style.invate_dialog_style);
		View view = View.inflate(mContext, R.layout.delete_hository_search_dialog, null);
		view.findViewById(R.id.btn_left).setOnClickListener(new OnClickListener() {
			// 关闭 取消
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		view.findViewById(R.id.btn_right).setOnClickListener(new OnClickListener() {
			// 确定删除
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				historical_search.setVisibility(View.GONE);
				mDatasHis.clear();
				recordsDao.deleteAllRecords();
			}
		});

		// // 创建自定义样式dialog
		dialog.setContentView(view,
				new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270), LinearLayout.LayoutParams.MATCH_PARENT));
		dialog.show();
	}
	/**
	 * 输入提醒 输入删除
	 * @date 2017年1月16日下午5:48:46
	 */
	private void setEditextChangedListener() {
		et_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String et_search_text = et_search.getText().toString();
				if (et_search_text.length() > 0) {

//					String sql_tips_cla = "SELECT * FROM type_tag WHERE class_name LIKE '%"+ et_search_text
//							+"%'  order by case when class_name LIKE '"+ et_search_text +"%' then 1 else 0 end desc";
//					List<HashMap<String, String>> tipsListCla_tem = dbHelp.query(sql_tips_cla);
//					List<HashMap<String, String>> tipsListCla =new ArrayList<HashMap<String, String>>();
//
//					for (int i = 0; i <tipsListCla_tem.size() ; i++) {
//						HashMap<String, String> tipsMapCla_tem = new HashMap<String, String>();
//						tipsMapCla_tem.put("attr_name",tipsListCla_tem.get(i).get("class_name"));
//						tipsListCla.add(tipsMapCla_tem);
//					}

					String sql_tips = "SELECT * FROM tag_info WHERE attr_name LIKE '%"+ et_search_text
							+"%'  order by case when attr_name LIKE '"+ et_search_text +"%' then 1 else 0 end desc";
					List<HashMap<String, String>> tipsList = dbHelp.query(sql_tips);

					if(/*tipsListCla!=null&&tipsListCla.size()>0||*/tipsList!=null&&tipsList.size()>0){
						mTipsList.clear();
//						mTipsList.addAll(tipsListCla);
						mTipsList.addAll(tipsList);
						tipsAdapter.notifyDataSetChanged();
					}

					if(mTipsList.size()==0){
						et_search_xx.setVisibility(View.GONE);
						historical_search_rootview.setVisibility(View.VISIBLE);
						lvTips.setVisibility(View.GONE);
					}else{
						et_search_xx.setVisibility(View.VISIBLE);
						historical_search_rootview.setVisibility(View.GONE);
						lvTips.setVisibility(View.VISIBLE);
					}
					
				} else {
					et_search_xx.setVisibility(View.GONE);
					mTipsList.clear();
					historical_search_rootview.setVisibility(View.VISIBLE);
					lvTips.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}
	/**
	 * 提醒列表点击事件
	 */
	private void setTipsClickListener() {
		lvTips.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView tv_tips = (TextView) view.findViewById(R.id.tips_list_item_tv);
				String words = tv_tips.getText().toString().trim();
//				et_search.setText(tv_tips.getText().toString());
				HashMap<String, String> recordsMap = new HashMap<String, String>();
				recordsMap.put("class_name",words);
				recordsMap.put("class_id", "");
				recordsMap.put("user_id", YJApplication.instance.isLoginSucess()
						? YCache.getCacheUserSafe(mContext).getUser_id() + "" : "-1");// 区分用户
																						// ：-1
				recordsDao.addRecords(recordsMap);// 添加搜索记录
				Intent intent = new Intent();
				intent = new Intent(mContext, WordSearchResultActivity.class);
				intent.putExtra("words", words);
				intent.putExtra("notType", true);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(bitmap_bg!=null){
			bitmap_bg.recycle();
		}
	}
		
}
