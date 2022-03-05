package com.yssj.ui.base;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseMainAdapter extends BaseAdapter {
	public Context context;
	public LinkedList<HashMap<String, Object>> result = new LinkedList<HashMap<String, Object>>() ;
	
	public BaseMainAdapter(Context context) {
		this.context = context;
	}
	
	public BaseMainAdapter(Context context,List<?> list) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return result == null ? 0:result.size();
	}

	@Override
	public Object getItem(int position) {
		return result == null ? null : result.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
	
	/**下拉刷新时把集合数据清空再添加到集合中*/
	public void addItemFirst(List<HashMap<String, Object>> orgResult) {
		if(orgResult != null){
			result.clear();
			result.addAll(orgResult);
		}
	}

	/**加载时直接把数据加到集合末尾*/
	public void addItemLast(List<HashMap<String, Object>> orgResult) {
		if(orgResult != null){
			for (HashMap<String, Object> hashMap : orgResult) {
				result.addLast(hashMap);
			}
			
		}
	}
	
	public LinkedList<HashMap<String, Object>> getList(){
		return result;
	}
	
	
	public void initIndicator(PullToRefreshListView lv){
		ILoadingLayout startLabels = lv.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在刷新...");// 刷新时
		startLabels.setReleaseLabel("释放刷新...");// 下来达到一定距离时，显示的提示

		ILoadingLayout endLabels = lv.getLoadingLayoutProxy(false, true);
		endLabels.setPullLabel("加载更多");
		endLabels.setRefreshingLabel("正在加载...");
		endLabels.setReleaseLabel("释放加载");
	}

}
