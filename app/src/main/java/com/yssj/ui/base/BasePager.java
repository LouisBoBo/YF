package com.yssj.ui.base;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yssj.ui.activity.MainMenuActivity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class BasePager{
	public Context context ;
	public View view;
	public TextView txt_title;
	public ImageView imgbtn_text;
//	public SlidingMenu slidingMenu;
	
	public BasePager(Context context) {
		this.context = context;
		view = initView();
		
//		slidingMenu = ((MainMenuActivity)context).getSlidingMenu();
	}
	
	
	
	// 构建UI的方法
	public abstract View initView();
	// 构建数据填充的方法
	public abstract void initData();
	// 返回当前界面显示效果的方法
	public View getRootView(){
		return view;
	}
	
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
	
}
