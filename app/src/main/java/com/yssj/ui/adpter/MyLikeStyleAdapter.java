
package com.yssj.ui.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import com.yssj.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MyLikeStyleAdapter extends BaseAdapter {
	// 填充数据的list
	private ArrayList<String> list;
	// 用来控制CheckBox的选中状况
	private static HashMap<Integer, Boolean> isSelected;
	// 上下文
	private Context context;
	// 用来导入布局
	private LayoutInflater inflater = null;
	private ArrayList<Integer> isCheckedLit = null;

	// 构造器
	public MyLikeStyleAdapter(ArrayList<String> list, ArrayList<Integer> isCheckedLit, Context context) {
		this.context = context;
		this.list = list;
		this.isCheckedLit = isCheckedLit;
		inflater = LayoutInflater.from(context);
		isSelected = new HashMap<Integer, Boolean>();
		// ��ʼ������
		initDate();
	}

	// ��ʼ��isSelected������
	private void initDate() {
		for (int i = 0; i < list.size(); i++) {
			getIsSelected().put(i, false);
		}

		// ����ѡ�е���Ŀ�Ž�ȥ
		for (int i = 0; i < isCheckedLit.size(); i++) {
			getIsSelected().put(isCheckedLit.get(i), true);
		}

	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
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
			convertView = inflater.inflate(R.layout.my_like_style_item, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.iv);
			holder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.cb.setChecked(getIsSelected().get(position));

		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {

		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		MyLikeStyleAdapter.isSelected = isSelected;
	}

	public static class ViewHolder {
		ImageView iv;
		public CheckBox cb;
	}
}