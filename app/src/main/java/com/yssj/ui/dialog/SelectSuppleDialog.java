package com.yssj.ui.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.custom.view.FlowLayout;
import com.yssj.data.YDBHelper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SelectSuppleDialog extends Dialog implements android.view.View.OnClickListener {
	
	private Context mContext;
	private EditText suppleEt;
	private String mEtTtext;
	private View selectOkView;
	private ListView lvTips;
	private List<HashMap<String, String>> mTipsList;
	private SimpleAdapter tipsAdapter;
	private FlowLayout suppleFl;
	private ScrollView scollView;
	private View noSuppleView;
	private TextView noSuppleTv;
	private YDBHelper dbHelp;
	private LayoutInflater mInflater;
	private List<HashMap<String, String>> listDataSupp;
	public SelectSuppleDialog(Context context) {
		super(context);
		setCanceledOnTouchOutside(false);
		this.mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select_supple_dialog);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		initView();
		initData();
		setEditextChangedListener();
	}

	private void initView() {
		dbHelp = new YDBHelper(mContext);
		mInflater = LayoutInflater.from(mContext);
		listDataSupp = new ArrayList<HashMap<String,String>>();
		suppleEt = (EditText) findViewById(R.id.add_supple_et);
		selectOkView = findViewById(R.id.tv_select_ok);
		lvTips = (ListView) findViewById(R.id.supple_tips_lv);
		suppleFl = (FlowLayout) findViewById(R.id.supple_flowlayout);
		noSuppleView = findViewById(R.id.no_supple_ll);
		noSuppleTv = (TextView) findViewById(R.id.no_supple_tv);
		scollView = (ScrollView) findViewById(R.id.supple_flowlayout_sl);
		selectOkView.setOnClickListener(this);
		noSuppleTv.setOnClickListener(this);
	}
	
	private void initData(){
		String suppSql =  "select * from supp_label where type = 1 order by _id";
		listDataSupp =  dbHelp.query(suppSql);
		for (int i = 0; i < listDataSupp.size(); i++) {
			TextView tv = (TextView) mInflater.inflate(R.layout.search_label_tv, suppleFl, false);
			final String name = listDataSupp.get(i).get("name");
			final HashMap<String, String> SuppleHashMap = listDataSupp.get(i);
			tv.setText(name);
			// 点击事件
			tv.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(listener!=null){
						listener.setSuppleListener(name,SuppleHashMap,"1");
					}
					dismiss();
				}
			});
			suppleFl.addView(tv);// 添加到父View
		}
		mTipsList =new ArrayList<HashMap<String,String>>();
		
		tipsAdapter = new SimpleAdapter(mContext, mTipsList, R.layout.dialog_supple_tips_list_item,
				new String[]{"name"}, new int[]{R.id.tips_list_item_tv});
		lvTips.setAdapter(tipsAdapter);
		lvTips.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String name = mTipsList.get(position).get("name");
				final HashMap<String, String> SuppleHashMap = listDataSupp.get(position);
				if(listener!=null){
					listener.setSuppleListener(name,SuppleHashMap,"1");
				}
				dismiss();
			}
		});
	}
	
	/**
	 * 输入提醒 输入删除
	 */
	private void setEditextChangedListener() {
		suppleEt.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String et_text = suppleEt.getText().toString().trim();
				mEtTtext = et_text;
				if (et_text.length() > 0) {
					scollView.setVisibility(View.INVISIBLE);
					String sql_tips = "SELECT * FROM supp_label WHERE type = 1 and name LIKE '%"+ et_text
							+"%'  order by case when name LIKE '"+ et_text +"%' then 1 else 0 end desc";
					List<HashMap<String, String>> tipsList = dbHelp.query(sql_tips);
					mTipsList.clear();
					mTipsList.addAll(tipsList);
					tipsAdapter.notifyDataSetChanged();
					if(mTipsList.size()==0){
						lvTips.setVisibility(View.GONE);
						noSuppleView.setVisibility(View.VISIBLE);
						noSuppleTv.setText("直接添加品牌\""+et_text+"\"");
					}else{
						lvTips.setVisibility(View.VISIBLE);
						noSuppleView.setVisibility(View.GONE);
					}
					selectOkView.setClickable(true);
					selectOkView.setBackgroundResource(R.drawable.indiana_shape_shaidan);
				} else {
					mTipsList.clear();
					scollView.setVisibility(View.VISIBLE);
					lvTips.setVisibility(View.GONE);
					noSuppleView.setVisibility(View.GONE);
					selectOkView.setClickable(false);
					selectOkView.setBackgroundResource(R.drawable.sweet_shape_send);
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
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_select_ok:
		case R.id.no_supple_tv:
			if(listener!=null){
				listener.setSuppleListener(mEtTtext,null,"2");
			}
			dismiss();
			break;

		default:
			break;
		}
		
	}
	
	public interface  SuppleListener{
		//label_type 1 后台品牌 2 自定义品牌
		void setSuppleListener(String name,HashMap<String, String> SuppleHashMap,String label_type);
	}
	private SuppleListener listener;
	public void setSuppleListener(SuppleListener listener){
		this.listener = listener;
	}
	
}
