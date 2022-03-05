package com.yssj.ui.adpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.yssj.activity.R;
import com.yssj.ui.activity.circles.ChooseTagActivity;

public class ChooseTagAdapter extends BaseAdapter{
	private List<Map<String, Object>> listData;
	private CheckCallback checkCallback;
	private Context context;
	
	public ChooseTagAdapter(Context context,List<Map<String, Object>> listData) {
		this.context = context;
		this.listData = listData;
	}
	
	public List<Map<String, Object>> getData(){
		return listData;
	}
	
	@Override
	public int getCount() {
		return listData.size();
	}
	
	public void setListener(ChooseTagActivity mActivity) {
		this.checkCallback = (CheckCallback) mActivity;
	}

	public interface CheckCallback {
		void onCheckCallback(int positon, boolean isChecked,
				Map<String, Object> map, ChooseTagAdapter mAdapter);
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.activit_tag_itme, null);
			holder.tv_tag = (ToggleButton) convertView.findViewById(R.id.tv_tag);
			
			
			holder.tv_tag.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					checkCallback.onCheckCallback(position, isChecked,
							listData.get(position), ChooseTagAdapter.this);
					if(holder.tv_tag.isChecked()){
						holder.tv_tag.setBackgroundColor(context.getResources().getColor(R.color.pink_color));
						holder.tv_tag.setTextColor(context.getResources().getColor(R.color.white));
					}else {
						holder.tv_tag.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.et_resetpassword_bn));
						holder.tv_tag.setTextColor(context.getResources().getColor(R.color.text1_color));
					}
				}
			});
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		final Map<String, Object> map = listData.get(position);
		
		holder.tv_tag.setText(map.get("tag_name").toString());
		holder.tv_tag.setTextOff(map.get("tag_name").toString());
		holder.tv_tag.setTextOn(map.get("tag_name").toString());
		
		
		if(map.get("isChecked").equals("1")){
			holder.tv_tag.setChecked(true);
			holder.tv_tag.setBackgroundColor(context.getResources().getColor(R.color.pink_color));
			holder.tv_tag.setTextColor(context.getResources().getColor(R.color.white));
			
		}else{
			holder.tv_tag.setChecked(false);
			holder.tv_tag.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.et_resetpassword_bn));
			holder.tv_tag.setTextColor(context.getResources().getColor(R.color.text1_color));
		}
		
		return convertView;
	}
	
	
	class ViewHolder{
		ToggleButton tv_tag;
	}
	
	
}
