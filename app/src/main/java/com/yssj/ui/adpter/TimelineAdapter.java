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
import android.widget.ImageView;
import android.widget.TextView;

/***
 * 时间轴适配器
 * @author Administrator
 * 
 */
public class TimelineAdapter extends BaseAdapter {
	private Context context;
	private List<HashMap<String, Object>> listData;

	public TimelineAdapter(Context context, List<HashMap<String, Object>> listData) {
		this.context = context;
		this.listData = listData;

	}

	@Override
	public int getCount() {
		return listData!=null?listData.size():0;
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
			convertView = mInflater.inflate(R.layout.timeline_item, null);
			holder.tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
			holder.tv_context = (TextView) convertView
					.findViewById(R.id.tv_context);
			holder.img_circle = (ImageView) convertView
					.findViewById(R.id.img_circle);
			holder.line = (View) convertView
					.findViewById(R.id.line);
			convertView.setTag(holder);

		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.tv_context.setText((CharSequence) listData.get(position).get("context"));
		holder.tv_time.setText((CharSequence) listData.get(position).get("ftime"));
		if(position == 0){
			holder.tv_context.setTextColor(context.getResources().getColor(R.color.green_color));
			holder.tv_time.setTextColor(context.getResources().getColor(R.color.green_color));
			holder.img_circle.setBackgroundResource(R.drawable.logistic_follow_green);
		}else{
			holder.tv_context.setTextColor(context.getResources().getColor(R.color.timeline_time_color));
			holder.tv_time.setTextColor(context.getResources().getColor(R.color.timeline_time_color));
			holder.img_circle.setBackgroundResource(R.drawable.logistic_follow_grey);
		}

		return convertView;
	}

	class Holder {
		TextView tv_time, tv_context;
		ImageView img_circle;
		View line;

	}

}
