package com.yssj.ui.adpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.ui.activity.MineLikeActivity;
import com.yssj.ui.adpter.GridViewAdapter.CheckCallback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter implements CheckCallback {
	private List<ArrayList<HashMap<String, String>>> mList;
	private Context mContext;
	private List<HashMap<String, String>> categoryList;

	public ListViewAdapter(List<HashMap<String, String>> categoryList,
			List<ArrayList<HashMap<String, String>>> mList, Context mContext) {
		super();
		this.categoryList = categoryList;
		this.mList = mList;
		this.mContext = mContext;
	}

	public interface checkedCallBack {
		void onCheckCallBack(int positon, boolean isChecked,
				HashMap<String, String> map);
	}

	public checkedCallBack callback;

	public void setlistener(MineLikeActivity mActivity) {
		callback = (checkedCallBack) mActivity;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(this.mContext).inflate(
					R.layout.listview_item, null, false);
			holder.textView = (TextView) convertView
					.findViewById(R.id.listview_item_textview);
			holder.gridView = (GridView) convertView
					.findViewById(R.id.listview_item_gridview);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (this.mList != null) {
			if (holder.textView != null) {
				holder.textView.setText(categoryList.get(position).get(
						"attr_name"));
			}
			if (holder.gridView != null) {
				ArrayList<HashMap<String, String>> arrayListForEveryGridView = this.mList
						.get(position);
				GridViewAdapter gridViewAdapter = new GridViewAdapter(mContext,
						arrayListForEveryGridView);
//				gridViewAdapter.setListener(this);
				holder.gridView.setAdapter(gridViewAdapter);
			}
		}
		return convertView;
	}

	private class ViewHolder {
		TextView textView;
		GridView gridView;
	}

	@Override
	public void onCheckCallback(int positon, boolean isChecked,
			HashMap<String, String> map) {
		callback.onCheckCallBack(positon, isChecked, map);
	}
}
