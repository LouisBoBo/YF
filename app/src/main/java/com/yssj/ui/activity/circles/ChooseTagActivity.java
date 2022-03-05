package com.yssj.ui.activity.circles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;

import com.yssj.activity.R;
import com.yssj.ui.adpter.ChooseTagAdapter;
import com.yssj.ui.adpter.ChooseTagAdapter.CheckCallback;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.ToastUtil;


public class ChooseTagActivity
//extends FragmentActivity implements OnClickListener,CheckCallback 
{
//	
//	private GridView gv_tags;
//	private Button btn_cancle,btn_sure;
//	private List<Map<String, Object>> listData ;
//	private ChooseTagAdapter mAdapter;
//	
//	private List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
//	
//	private String checkedValueId,checkedValue;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.activity_choose_tag);
//		
//		listData = (List<Map<String, Object>>) getIntent().getSerializableExtra("tagValue");
//		
//		btn_sure = (Button) findViewById(R.id.btn_sure);
//		btn_cancle = (Button) findViewById(R.id.btn_cancle);
//		btn_cancle.setOnClickListener(this);
//		btn_sure.setOnClickListener(this);
//		gv_tags = (GridView) findViewById(R.id.gv_tags);
//		
//		mAdapter = new ChooseTagAdapter(this,listData);
//		mAdapter.setListener(ChooseTagActivity.this);
//		gv_tags.setAdapter(mAdapter);
//		
//	}
//
//	@Override
//	public void onCheckCallback(int positon, boolean isChecked,
//			Map<String, Object> map, ChooseTagAdapter mAdapter) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		
//	}
	
/*	private void initTagData(){
		new SAsyncTask<Void, Void, List<Map<String, Object>>>(this){

			@Override
			protected List<Map<String, Object>> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				return ComModel2.getCircleTags(context, tag_data);
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					List<Map<String, Object>> result) {
				super.onPostExecute(context, result);
				if(result != null){
					for (int i = 0; i < result.size(); i++) {
						result.get(i).put("isChecked", "0");
					}
					listData = result;
					mAdapter = new ChooseTagAdapter(context,result) ;
					mAdapter.setListener(ChooseTagActivity.this);
					gv_tags.setAdapter(mAdapter);
				}
			}
			
		}.execute();
	}*/
	
//	@Override
//	protected void onResume() {
//		super.onResume();
//		MobclickAgent.onResume(this);
//	}
//	
//	@Override
//	protected void onPause() {
//		super.onPause();
//		MobclickAgent.onPause(this);
//	}
//	
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn_cancle:
//			finish();
//			break;
//			
//		case R.id.btn_sure:
//			if(TextUtils.isEmpty(checkedValueId)){
//				ToastUtil.showShortText(getApplication(), "请选择标签或点取消按钮");
//			}else {
//				Intent intent = new Intent();
//				intent.putExtra("id", checkedValueId);
//				intent.putExtra("name", checkedValue);
//				setResult(102, intent);
//				finish();
//			}
//			break;
//
//		}
//	}
//
//	@Override
//	public void onCheckCallback(int positon, boolean isChecked,
//			Map<String, Object> map, ChooseTagAdapter mAdapter) {
//		if (isChecked) {
//			List<Map<String, Object>> list = mAdapter.getData();
//			for (int i = 0; i < list.size(); i++) {
//				list.get(i).put("isChecked", "0");
//			}
//			list.get(positon).put("isChecked", "1");
//			mAdapter.notifyDataSetChanged();
//			checkedValueId = (String)list.get(positon).get("id");
//			checkedValue = (String)list.get(positon).get("tag_name");
//			listMap.add(list.get(positon));
//		}else{
//			List<Map<String, Object>> list = mAdapter.getData();
//			listMap.remove(list.get(positon));
//		}
//	}
//	
	
}
