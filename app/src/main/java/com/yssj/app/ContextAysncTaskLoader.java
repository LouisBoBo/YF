package com.yssj.app;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;


public abstract class ContextAysncTaskLoader<D> extends AsyncTaskLoader<D> {

	private WeakReference<Context> mWeakContext;
	
	public ContextAysncTaskLoader(Context context) {
		super(context);
		mWeakContext = new WeakReference<Context>(context);
	}
	
	public Context getComponentContext() {
		return mWeakContext.get();
	}

	@Override
	public D loadInBackground() {
		// 如果context被回收了，我们就不再进行加载�?
		Context context = getComponentContext();
		if (context != null) {
			return loadInBackground(context);
		}
		return null;
	}
	
	abstract protected D loadInBackground(Context context);
}
