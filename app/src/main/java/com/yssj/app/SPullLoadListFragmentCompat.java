package com.yssj.app;

import java.util.List;

import android.content.Context;

import com.yssj.YConstance;

public abstract class SPullLoadListFragmentCompat<T> extends SPullLoadListFragment<T> {
	
	/** 改用onLoadData */
	@Override
	protected List<T> onInitData(Context context) throws Exception {
		return onLoadData(context, YConstance.FirstPageIndex);
	}

	/** 改用onLoadData */
	@Override
	protected List<T> onLoadMore(Context context, Integer index)
			throws Exception {
		return onLoadData(context, index);
	}

	abstract protected List<T> onLoadData(Context context, Integer index)  throws Exception;
}
