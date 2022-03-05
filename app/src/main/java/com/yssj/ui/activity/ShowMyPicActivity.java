package com.yssj.ui.activity;

import com.yssj.activity.R;
import com.yssj.huanxin.activity.BaseActivity;
import com.yssj.ui.adpter.ShowMyPicPageAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.circles.SignFragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class ShowMyPicActivity extends BasicActivity {
	private PagerAdapter adapter;
	
	private ViewPager vp;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_mypic);;
		
//		android.view.WindowManager.LayoutParams lay = getWindow().getAttributes();
//
//		setParams(lay);
		int position = getIntent().getIntExtra("position", 0);
		vp = (ViewPager) findViewById(R.id.vp);
		adapter = new ShowMyPicPageAdapter(this, MyPicActivity.list);
		vp.setAdapter(adapter);
		vp.setCurrentItem(position);

	}
//	protected void setParams(android.view.WindowManager.LayoutParams lay) {
//
//		DisplayMetrics dm = new DisplayMetrics();
//
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//		Rect rect = new Rect();
//
//		View view = getWindow().getDecorView();
//
//		view.getWindowVisibleDisplayFrame(rect);
//
//		lay.height = dm.heightPixels/* - rect.top */;
//
//		lay.width = dm.widthPixels;
//
//	}

}
