package com.yssj.ui.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;



import com.yssj.activity.R;
import com.yssj.entity.MyToggleButton;
import com.yssj.ui.activity.MineLikeActivity;


public class GridViewAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<HashMap<String, String>> mList;
	
	private CheckCallback checkCallback;

	public GridViewAdapter(Context mContext,
			ArrayList<HashMap<String, String>> mList) {
		super();
		this.mContext = mContext;
		this.mList = mList;
	}

//	public void setListener(ListViewAdapter mAdapter){
//		this.checkCallback = (CheckCallback) mAdapter;
//	}
	
	public void setListener(MineLikeActivity mActivity){
		this.checkCallback = (CheckCallback) mActivity;
	}
	
	public interface CheckCallback{
		void onCheckCallback(int positon, boolean isChecked, HashMap<String, String> map);
	}
	
	@Override
	public int getCount() {
		if (mList == null) {
			return 0;
		} else {
			return this.mList.size();
		}
	}

	@Override
	public Object getItem(int position) {
		if (mList == null) {
			return null;
		} else {
			return this.mList.get(position);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(this.mContext).inflate(
					R.layout.gridview_item, null, false);
			holder.button = (MyToggleButton) convertView
					.findViewById(R.id.gridview_item_button);
			holder.button
			.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton arg0,
						boolean arg1) {
					// TODO Auto-generated method stub
					checkCallback.onCheckCallback(position, arg1,mList.get(position));
				}
			});
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (this.mList != null) {
			HashMap<String, String> hashMap = this.mList.get(position);
			if (holder.button != null) {
				holder.button.setText(hashMap.get("attr_name").toString());
				holder.button.setTextOff(hashMap.get("attr_name").toString());
				holder.button.setTextOn(hashMap.get("attr_name").toString());
				// holder.button.setOnClickListener(new OnClickListener() {
				// @Override
				// public void onClick(View v) {
				// Toast.makeText(mContext, "第" + (position + 1) + "个",
				// Toast.LENGTH_SHORT).show();
				// }
				// });
				
			}
		}
		return convertView;
	}

	private class ViewHolder {
		MyToggleButton button;
	}
}
