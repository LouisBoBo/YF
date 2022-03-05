package com.yssj.ui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yssj.activity.R;
import com.yssj.custom.view.IndianaListItemView;
import com.yssj.custom.view.IndianaListOneItemView;
import com.yssj.utils.DP2SPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class IndianaListOneAdapter extends BaseAdapter {
	private List<HashMap<String, Object>> mInfos;

	private int imageWidth;

	private int picHeight;
	private Context mContext;

	public IndianaListOneAdapter(Context context) {
		mInfos = new ArrayList<HashMap<String, Object>>();

		int width = context.getResources().getDisplayMetrics().widthPixels;
//		imageWidth = width / 2;
//		picHeight=(imageWidth-DP2SPUtil.dp2px(context, 18))*900/600;
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent
	) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater layoutInflator = LayoutInflater.from(parent
					.getContext());
			convertView = layoutInflator.inflate(R.layout.indiana_shop_infos_one_list, null);
			
			holder.left=(IndianaListOneItemView) convertView.findViewById(R.id.left);
			holder.right=(IndianaListOneItemView) convertView.findViewById(R.id.right);
			
//			holder.left.getLayoutParams().height=picHeight;
//			holder.right.getLayoutParams().height=picHeight;
//			holder.right.getLayoutParams().height=picHeight+DP2SPUtil.dp2px(mContext, 18);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		int index=position*2;
		
		holder.left.initView(mInfos.get(index),index);
		
		if(mInfos.size()>index+1){
			holder.right.setVisibility(View.VISIBLE);
			holder.right.initView(mInfos.get(index+1),index+1);
		}else{
			holder.right.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}
	class ViewHolder {

		IndianaListOneItemView left;
		IndianaListOneItemView right;
	}

	@Override
	public int getCount() {
		return mInfos.size()%2==0?mInfos.size()/2:mInfos.size()/2+1;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public void addItemLast(List<HashMap<String, Object>> datas) {
		mInfos.addAll(datas);
		this.notifyDataSetChanged();
	}

	public void addItemTop(List<HashMap<String, Object>> datas) {
		mInfos.clear();
		mInfos.addAll(datas);
		this.notifyDataSetChanged();
	}

	public void clearData() {
		mInfos.clear();
		this.notifyDataSetChanged();
	}

	public void setData(List<HashMap<String, Object>> result) {
		clearData();
		mInfos.addAll(result);
		this.notifyDataSetChanged();
	}
}
