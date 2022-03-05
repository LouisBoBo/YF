package com.yssj.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ListAdapter;

public class NoScrollGridView extends GridView {
	
	public ListAdapter adapter;
	public NoScrollGridView(Context context) {
		super(context);

	}

	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
	@Override
	public void setAdapter(ListAdapter adapter) {
		// TODO Auto-generated method stub
		
		super.setAdapter(adapter);
		
		this.adapter=adapter;
	}
	@Override
	public ListAdapter getAdapter() {
		// TODO Auto-generated method stub
		return adapter;
	}
}
