package com.yssj.app;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.yssj.YConstance.LoaderID;
import com.yssj.activity.R;
import com.yssj.custom.view.LoadMoreView;
import com.yssj.entity.UserInfo;
import com.yssj.network.YConn;
import com.yssj.utils.YCache;

public abstract class SPullLoadListFragment<T> extends SPullToRefreshListFragment 
	implements LoaderCallbacks<List<T>> {
	
	private Exception exception;
	private ArrayAdapterCompat<T> adapter;
	private LoadMoreExecuter<T> loadMoreExecuter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setEmptyText(getString(R.string.no_data));
		ListView lv = getListView();
		
		LoadMoreView footer = new LoadMoreView(getActivity());
		loadMoreExecuter = new DataLoadMoreExecuter(getActivity(), footer);
		lv.addFooterView(footer);
		lv.setFooterDividersEnabled(false);
		
		adapter = onCreateListAdapter();
		setListAdapter(adapter);
		setListShown(false);
		
		getLoaderManager().initLoader(getLoaderId(), null, this);
	}

	private class DataLoadMoreExecuter extends LoadMoreExecuter<T> {

		public DataLoadMoreExecuter(Context ctx, LoadMoreView lm) {
			super(ctx, lm);
		}
		
		@Override
		protected List<T> loadInBackground(Context context, Integer index) {
			try {
				return onLoadMore(context, index);
			} catch (Exception e) {
				setException(e);
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onLoadMorePostExecute(List<T> result) {
			adapter.addAll(result);
		}
	}
	
	// 重新加载
	public void forceLoad() {
		getLoaderManager().restartLoader(getLoaderId(), null, this);
		//getLoaderManager().getLoader(getLoaderId()).forceLoad();
	}
	
	
	// 重新加载
	public void forceLoadFirend() {
			getLoaderManager().restartLoader(LoaderID.USER_FOOTING, null, this);
			//getLoaderManager().getLoader(getLoaderId()).forceLoad();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// load more
		position -= getListView().getHeaderViewsCount();
		if (position >= adapter.getCount()) {
			loadMoreExecuter.load();
			return;
		}
	}
	
	@Override
	public Loader<List<T>> onCreateLoader(int id, Bundle args) {
		return new ListAsyncTaskLoader<T>(getActivity()) {

			@Override
			protected List<T> loadInBackground(Context context) {
				try {
					UserInfo user=YCache.getCacheUserSafe(context);
//					ComModel.getFriends(context, user);
					return onInitData(context);
				} catch (Exception e) {
					exception = e;
					e.printStackTrace();
				}
				return null;
			}
		};
	}
	
	/** 当你自定义Loader的时候，才需要setException */
	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		
		forceLoad();
	};
	
	@Override
	public void onLoadFinished(Loader<List<T>> loader, List<T> data) {
		adapter.setData(data);
		getPullToRefreshListView().onRefreshComplete();
		loadMoreExecuter.reset(data == null ? 0 : data.size());
		if (isResumed()) {
			setListShown(true);
		} else {
			setListShownNoAnimation(true);
		}
		
		if (exception != null) {
			YConn.showErrorToast(getActivity(), exception, "请重新..");
			exception = null;
		}
	}
	
	@Override
	public void onLoaderReset(Loader<List<T>> loader) {
		adapter.setData(null);
	}
	
	public ArrayAdapter<T> getAdapter() {
		return adapter;
	}

	// LoaderId
	abstract protected int getLoaderId();
	
	// 创建adapter
	abstract protected ArrayAdapterCompat<T> onCreateListAdapter();
	
	// 初始化数�?
	abstract protected List<T> onInitData(Context context) throws Exception;
	
	// 加载更多数据
	abstract protected List<T> onLoadMore(Context context, Integer index)  throws Exception;
}
