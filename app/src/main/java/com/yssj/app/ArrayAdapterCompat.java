package com.yssj.app;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ArrayAdapterCompat<T> extends ArrayAdapter<T> {
	
	public ArrayAdapterCompat(Context context) {
		super(context, android.R.layout.simple_list_item_1);
	}

	public ArrayAdapterCompat(Context context, int resource,
			int textViewResourceId, List<T> objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public ArrayAdapterCompat(Context context, int resource,
			int textViewResourceId, T[] objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public ArrayAdapterCompat(Context context, int resource,
			int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}

	public ArrayAdapterCompat(Context context, int textViewResourceId,
			List<T> objects) {
		super(context, textViewResourceId, objects);
	}

	public ArrayAdapterCompat(Context context, int textViewResourceId,
			T[] objects) {
		super(context, textViewResourceId, objects);
	}

	public ArrayAdapterCompat(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public void setData(List<T> collection) {
		ArrayAdapterUtils.setData(this, collection);
	}
	
	public void addAll(List<T> collection) {
		ArrayAdapterUtils.addAll(this, collection);
	}
}
