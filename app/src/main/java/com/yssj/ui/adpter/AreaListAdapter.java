package com.yssj.ui.adpter;

import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.entity.Help;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/***
 * 抽屉适配器
 * 
 * @author Administrator
 * 
 */
public class AreaListAdapter extends BaseAdapter {
	private Context context;
	private List<HashMap<String, String>> listData;

	public AreaListAdapter(Context context, List<HashMap<String, String>> listData) {
		this.context = context;
		this.listData = listData;

	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {

		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.listview_drawer, null);
			holder.tv_drawer = (TextView) convertView
					.findViewById(R.id.tv_drawer);
			convertView.setTag(holder);

		} else {
			holder = (Holder) convertView.getTag();
		}
		
		holder.tv_drawer.setText(listData.get(position).get("AreaName"));

		return convertView;
	}

	class Holder {
		TextView tv_drawer;

	}

}
