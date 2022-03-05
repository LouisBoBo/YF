package com.yssj.app;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.yssj.YConstance;
import com.yssj.custom.view.LoadMoreView;
import com.yssj.network.YConn;
import com.yssj.utils.SharedPreferencesUtil;


/**
 * <p>加载�?</p>
 * 使用方法:</br>
 * 1.在点击LoaderMoreView的时候，调用load()方法</br>
 * 2.在重置数据的时�?�，调用reset()方法
 * 
 * @author hosolo
 * 
 */
public abstract class LoadMoreExecuter<D> {
	
	private LoadMoreView loadMoreView;
	private Context context;
	private Integer pageIndex = YConstance.FirstPageIndex;
	private Exception exception;

	public LoadMoreExecuter(Context ctx, LoadMoreView lm) {
		context = ctx;
		loadMoreView = lm;
	}
	
	public void reset(int count) {
		pageIndex = YConstance.FirstPageIndex;
		loadMoreView.setState(count > 0
				? LoadMoreView.STATE_IDLE
				: LoadMoreView.STATE_NO_MORE);
	}
	
	public void load() {
		if (!loadMoreView.isReady()) {
			return;
		}
		new AsyncTask<Integer, Void, List<D>>() {
			
			@Override
			protected void onPreExecute() {
				loadMoreView.setState(LoadMoreView.STATE_LOADING);
			}
			
			@Override
			protected List<D> doInBackground(Integer... params) {
				Integer index = params[0];
				return loadInBackground(context, index);
			}
			
			@Override
			protected void onPostExecute(List<D> result) {
				if (exception != null) {
					YConn.showErrorToast(context, exception,"您的账号已在另外一台设备上登录，请重新登录...");
					SharedPreferencesUtil.saveBooleanData(context, "isLoginLogin", false);
					
					exception = null;
					loadMoreView.setState(LoadMoreView.STATE_IDLE);
					return;
				}
				if (result == null || result.isEmpty()) {
					loadMoreView.setState(LoadMoreView.STATE_NO_MORE);
				} else {
					onLoadMorePostExecute(result);
					loadMoreView.setState(LoadMoreView.STATE_IDLE);
					pageIndex++;
				}
			}
		}.execute(pageIndex + 1);
	}
	
	abstract protected List<D> loadInBackground(Context context, Integer index);
	
	abstract protected void onLoadMorePostExecute(List<D> result);
	
	public void setException(Exception e) {
		exception = e;
	}
	
}