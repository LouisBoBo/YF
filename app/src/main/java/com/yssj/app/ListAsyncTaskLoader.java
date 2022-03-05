package com.yssj.app;

import java.util.List;

import com.yssj.utils.InterestingConfigChanges;

import android.content.Context;

public abstract class ListAsyncTaskLoader<T> extends ContextAysncTaskLoader<List<T>> {

	private final InterestingConfigChanges mLastConfig = new InterestingConfigChanges();
	private List<T> list;
	private Context context;
	
	public ListAsyncTaskLoader(Context context) {
		super(context);
		this.context = context;
	}
	
	@Override
	public List<T> loadInBackground() {
		return loadInBackground(context);
	}
	
	// 复写这个方法就可以了
	protected List<T> onLoadInBackground(Context context) {
		return null;
	};
	
	@Override
	public void deliverResult(List<T> ls) {
		list = ls;
        if (isStarted()) {
            super.deliverResult(ls);
        }
	}

	@Override
	protected void onStartLoading() {
		super.onStartLoading();
		
		if (list != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(list);
        }
		
		boolean configChange = mLastConfig.applyNewConfig(getContext().getResources());
		if (takeContentChanged() || list == null || configChange) {
			forceLoad();
		}
	}
	
	@Override
	protected void onStopLoading() {
		super.onStopLoading();
		cancelLoad();
	}

	@Override
	protected void onReset() {
		super.onReset();
		stopLoading();
		
		if (list != null) {
			list = null;
        }
	}

}
