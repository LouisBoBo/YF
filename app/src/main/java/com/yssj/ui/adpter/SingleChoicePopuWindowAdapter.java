package com.yssj.ui.adpter;

import java.util.List;

import com.yssj.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class SingleChoicePopuWindowAdapter extends BaseAdapter implements OnItemClickListener {
	
	private List<String> lists;
	private Context context;
	
	private int mCheckBoxResourceID = 0;
	private int mSelectItem = 0;
	private LayoutInflater mInflater;

	public SingleChoicePopuWindowAdapter(Context context,List<String> lists , int checkBoResourceId) {
		this.context = context;
		this.lists = lists;
		init(checkBoResourceId);
	}

	private void init(int checkBoResourceId) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mCheckBoxResourceID = checkBoResourceId;
	}

	public void refreshData(List<String> objects) {
		if (objects != null) {
			lists = objects;
			setSelectItem(0);
		}
	}

	public void setSelectItem(int selectItem) {
		if (selectItem >= 0 && selectItem < lists.size()) {
			mSelectItem = selectItem;
			notifyDataSetChanged();
		}

	}

	/**
	 * 获取返回值
	 * @return
	 */
	public String getSelectItem() {
		return lists.get(mSelectItem);
	}

	public void clear() {
		lists.clear();
		notifyDataSetChanged();
	}

	public int getCount() {
		return lists.size();
	}

	public Object getItem(int position) {
		return lists.get(position);
	}
	
	public int getPosition(String item) {
		return lists.indexOf(item);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.popu_list_item, null);
			holder = new ViewHolder();
			holder.img_select_icon = (CheckBox) convertView
					.findViewById(R.id.img_select_icon);
			holder.tv_reason = (TextView) convertView
					.findViewById(R.id.tv_reason);
			convertView.setTag(holder);

			if (mCheckBoxResourceID != 0) {
				holder.img_select_icon.setButtonDrawable(mCheckBoxResourceID);
			}

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.img_select_icon.setChecked(mSelectItem == position);
		holder.tv_reason.setText(lists.get(position));

		convertView.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (position != mSelectItem) {
					mSelectItem = position;
					notifyDataSetChanged();
				}
			}
		});
		


		return convertView;
	}



	public static class ViewHolder {
		CheckBox img_select_icon;
		TextView tv_reason;
	}



	public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
		if (position != mSelectItem) {
			mSelectItem = position;
			notifyDataSetChanged();
		}
	}
}
