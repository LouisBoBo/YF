package com.yssj.ui.adpter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yssj.activity.R;

public class DataAdapter extends BaseAdapter {
	private Context context;
	private List<HashMap<String, String>> listData;

	public DataAdapter(Context context, List<HashMap<String, String>> listData) {
		this.context = context;
		this.listData = listData;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listData.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.list_guide, null);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			convertView.setTag(holder);

		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.tv_name.setText(listData.get(position).get("sort_name"));

		return convertView;
	}
	
	class Holder{
		TextView tv_name;
	}

}