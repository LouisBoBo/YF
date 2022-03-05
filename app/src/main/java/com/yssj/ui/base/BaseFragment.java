package com.yssj.ui.base;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.ui.activity.MainMenuActivity.GetSlidingMenu;
import com.yssj.utils.LogYiFu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;



public abstract class BaseFragment extends Fragment implements OnClickListener{
	public View view;
	public Context context;
//	public SlidingMenu slidingMenu;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getActivity().getActionBar().hide();
		this.context = getActivity();
//		new MainMenuActivity().setGetSlidingMenu(this);
		
		LogYiFu.e("签到测试", "onCreateView");
		
//		slidingMenu = ((MainMenuActivity)context).getSlidingMenu();
	}
	
	// 设置界面对应的view对象
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		view = initView();
		return view;
	}
	
//	@Override
//	public void getSliding(SlidingMenu menu) {
//		this.slidingMenu = menu;
//		
//	}
	// 填充数据的操作
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// 拿数据去填充UI
		initData();
		super.onActivityCreated(savedInstanceState);
	}
	

	// 预设UI的方法
	public abstract View initView() ;
	// 数据填充UI的方法
	public abstract void initData();
	
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
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
	}
}
