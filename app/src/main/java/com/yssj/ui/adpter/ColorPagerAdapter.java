package com.yssj.ui.adpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yssj.activity.R;
import com.yssj.custom.view.LazyScrollView;
import com.yssj.custom.view.MyScrollView;

public class ColorPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> mFragments;

	private List<HashMap<String, String>> data;

	public ColorPagerAdapter(FragmentManager fm, List<HashMap<String, String>> data,MyScrollView scroll) {
		super(fm);
		this.data = data;
		mFragments = new ArrayList<Fragment>();
		for(int i = 0; i<data.size(); i++){
//			mFragments.add(new ProductListFragment(data.get(i).get("_id"),data.get(i).get("sort_name"), scroll));
		}
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}

}