package com.yssj.ui.activity.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.data.YDBHelper;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.adpter.DataAdapter;
import com.yssj.ui.base.BasicActivity;

public class SeachNoticeActivity extends BasicActivity implements OnClickListener{

	private EditText et_search;
	private ListView lv;
	
	private List<HashMap<String, String>> listData = new ArrayList<HashMap<String,String>>();
	
	private YDBHelper helper;
	private String id = -1+"";
	
	private DataAdapter mAdapter;
	private String searchWord;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		helper = new YDBHelper(this);
		id = getIntent().getStringExtra("id");
		et_search = (EditText) findViewById(R.id.et_search);
		et_search.requestFocus();
		lv = (ListView) findViewById(R.id.lv);
		setData1();
		findViewById(R.id.btn_seach).setOnClickListener(this);

		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SeachNoticeActivity.this, SearchResultActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("item", listData.get(arg2));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	private void setData1() {
		listData.clear();
		listData = helper.query("select * from sort_info where p_id ="+id);
		// 创建ArrayAdapter
		mAdapter = new DataAdapter(this, listData);
		// 绑定适配器
		lv.setAdapter(mAdapter);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		YJApplication.getLoader().stop();
//		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
//		JPushInterface.onResume(this);   
//		MobclickAgent.onResume(this);
	}

	/*@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
		
	}*/
	
	@Override
	public void onClick(View arg0) {
		Intent intent = null;
		switch (arg0.getId()) {
		case R.id.btn_seach:
			searchWord = et_search.getText().toString().trim();
			if(searchWord.length() == 0 || searchWord.equals("")){
				Toast.makeText(this, "请输入要查询的商品", Toast.LENGTH_SHORT).show();
				et_search.requestFocus();
				return;
			}
			intent = new Intent(this, WordSearchResultActivity.class);
			intent.putExtra("words", searchWord);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

//	private void setData2() {
//		data.clear();
//		for (int i = 16; i < 30; i++) {
//			data.add("当前列" + i);
//		}
//		ArrayAdapter arrayAdapter = new ArrayAdapter(SeachNoticeActivity.this,
//				android.R.layout.simple_list_item_1, data);
//		// 绑定适配器
//		lv.setAdapter(arrayAdapter);
//	}
	
	
}
