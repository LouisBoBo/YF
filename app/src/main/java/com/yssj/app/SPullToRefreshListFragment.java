package com.yssj.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public abstract class SPullToRefreshListFragment extends SPullToRefreshBaseListFragment<PullToRefreshListView> implements OnRefreshListener<ListView> {

	protected PullToRefreshListView onCreatePullToRefreshListView(LayoutInflater inflater, Bundle savedInstanceState) {
		SPullToRefreshListView pullToRefreshlistView = new SPullToRefreshListView(getActivity(), Mode.PULL_FROM_START, AnimationStyle.FLIP);
		pullToRefreshlistView.setOnRefreshListener(this);
		pullToRefreshlistView.setShowIndicator(false);
		return pullToRefreshlistView;
	}
	
	@Override
	public void ensureList() {
		super.ensureList();
		getPullToRefreshListView().getRefreshableView().setOnItemClickListener(onItemClickListener);
	}
	
	private final AdapterView.OnItemClickListener onItemClickListener
		= new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				SPullToRefreshListFragment.this.onItemClick(parent, v, position, id);
		}
	};
	
	@Override
	abstract public void onRefresh(PullToRefreshBase<ListView> refreshView);
	
	@Override
	abstract protected void onItemClick(AdapterView<?> parent, View v, int position, long id);
}
