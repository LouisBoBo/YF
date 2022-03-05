package com.yssj.ui.activity.setting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.data.DBService;
import com.yssj.ui.adpter.AreaListAdapter;
import com.yssj.ui.base.BasicActivity;

public class UpdateAreaActivity extends BasicActivity {

	private LinearLayout lin_container;
	private ListView lv;
	private DBService db = new DBService(this);

	private List<HashMap<String, String>> listData;
	private AreaListAdapter mAdapter;

	private String id;
	private Button btn_save;
	private List<HashMap<String, String>> savedMap = new ArrayList<HashMap<String, String>>();
	private List<TextView> savedTv = new ArrayList<TextView>();
	private TextView tvTitle_base;
	private LinearLayout img_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.select_area);
		listData = db.query("select * from areatbl where NoteType =1 and id not in(32,33,34,35)");
		mAdapter = new AreaListAdapter(this, listData);
		initView();
	}
	
	private void initView() {
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("选择地区");
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		lin_container = (LinearLayout) findViewById(R.id.lin_container);
		lv = (ListView) findViewById(R.id.lv);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_save.setOnClickListener(this);
		lv.setAdapter(mAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				final HashMap<String, String> map = listData.get(arg2);
				id = map.get("id");
				savedMap.add(map);
				final TextView tv = new TextView(UpdateAreaActivity.this);
				tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.MATCH_PARENT));
				tv.setPadding(10, 10, 10, 10);
				tv.setText(map.get("AreaName"));
				tv.setTextSize(18);
				savedTv.add(tv);
				tv.setTextColor(getResources().getColor(R.color.title_color));
				tv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
//						lin_container.removeView(tv);
//						savedMap.remove(map);
						int j = savedMap.indexOf(map);
						int k = savedMap.size();
						for(int i = k-1; i>=j;i--){
							savedMap.remove(i);
							lin_container.removeView(savedTv.get(i));
							savedTv.remove(savedTv.get(i));
						}
						listData = db.query("select * from areatbl where AreaKey ="+map.get("AreaKey"));
						if(listData.size()!=0 && "3".equals(map.get("NoteType"))&&!"36".equals(map.get("AreaKey"))){
							HashMap<String, String> mapAdd = new HashMap<String, String>();
							mapAdd.put("AreaName", "其他");
							mapAdd.put("NoteType", "3");
							mapAdd.put("AreaKey", "36");
							mapAdd.put("id", "566");
							listData.add(mapAdd);
						}
						if(listData.size()!=0 && "4".equals(map.get("NoteType"))&&!"36".equals(map.get("AreaKey"))){
							HashMap<String, String> mapAdd = new HashMap<String, String>();
							mapAdd.put("AreaName", "其他");
							mapAdd.put("NoteType", "4");
							mapAdd.put("AreaKey", "36");
							mapAdd.put("id", "566");
							listData.add(mapAdd);
						}
						mAdapter = new AreaListAdapter(UpdateAreaActivity.this,
								listData);
						lv.setAdapter(mAdapter);
						return;
					}
				});
				lin_container.addView(tv);

				listData = db.query("select * from areatbl where AreaKey = '"
						+ id + "'");
				if(listData.size()!=0 && "2".equals(map.get("NoteType"))&&!"36".equals(id)){
					HashMap<String, String> mapAdd = new HashMap<String, String>();
					mapAdd.put("AreaName", "其他");
					mapAdd.put("NoteType", "3");
					mapAdd.put("AreaKey", "36");
					mapAdd.put("id", "566");
					listData.add(mapAdd);
				}
				if(listData.size()!=0 && "3".equals(map.get("NoteType"))&&!"36".equals(id)){
					HashMap<String, String> mapAdd = new HashMap<String, String>();
					mapAdd.put("AreaName", "其他");
					mapAdd.put("NoteType", "4");
					mapAdd.put("AreaKey", "36");
					mapAdd.put("id", "566");
					listData.add(mapAdd);
				}
				if (listData.size() == 0) {
					btn_save.setVisibility(View.VISIBLE);
				} else {
					btn_save.setVisibility(View.GONE);
				}
				mAdapter = new AreaListAdapter(UpdateAreaActivity.this,
						listData);
				lv.setAdapter(mAdapter);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_save:
			Intent intent = getIntent();
			Bundle bundle = new Bundle();
			bundle.putSerializable("list", (Serializable) savedMap);
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);
			this.finish();
			break;
		case R.id.img_back:
			this.finish();
			break;

		default:
			break;
		}
	}

}
