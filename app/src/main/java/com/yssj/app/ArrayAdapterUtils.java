package com.yssj.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.annotation.TargetApi;
import android.os.Build;
import android.widget.ArrayAdapter;

public class ArrayAdapterUtils {
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static<T> void setData(ArrayAdapter<T> adapter, Collection<? extends T> items) {
		if (adapter == null) {
			throw new NullPointerException("adapter is null");
		}
		adapter.clear();
		if (items == null || items.isEmpty()) {
			return;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			adapter.addAll(items);
		} else {
			adapter.setNotifyOnChange(false);
		    if (items != null) {
		        for (T item : items)
		        	adapter.add(item);
		    }
		    adapter.setNotifyOnChange(true);
		    adapter.notifyDataSetChanged();
		}
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static<T> void addAll(ArrayAdapter<T> adapter, Collection<? extends T> items) {
		if (adapter == null) {
			throw new NullPointerException("adapter is null");
		}
		if (items == null || items.isEmpty()) {
			return;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			adapter.addAll(items);
		} else {
			adapter.setNotifyOnChange(false);
		    if (items != null) {
		        for (T item : items)
		        	adapter.add(item);
		    }
		    adapter.setNotifyOnChange(true);
		    adapter.notifyDataSetChanged();
		}
	}

	/** 
	 * 帮助List改成Gird形式
	 * 分组，将List，改成你想要的多少个�?�?
	 */
	public static <T> List<Group<T>> split(int num, List<T> lists) {
		if (lists == null) return null;
		List<Group<T>> newList = new ArrayList<Group<T>>();
		
		Group<T> group = null;
		int count = 0;
		for (int i = 0, n = lists.size(); i < n; i += num) {
			if (i % num == 0 || i == 0) {
				group = new Group<T>();
				group.array = new ArrayList<T>(num);
				newList.add(group);
			}
			for (int y = 0; y < num; y++) {
				final int index = num * count + y;
				if (index > lists.size() - 1) break;
				group.array.add(lists.get(index));
			}
			count ++;
		}
		return newList;
	}
	
	public static <T> T getRealItem(ArrayAdapter<Group<T>> adapter, int num, int position) {
		int row = position / num;
		int which = position % num;
		return adapter.getItem(row).array.get(which);
	}
	
	public static class Group<T> {
		public List<T> array;
	}
	
}
