package com.yssj.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yssj.activity.R;

/***
 * 我的最爱
 * 
 * @author Administrator
 * 
 */
public class FavourListFragment extends Fragment {

	private static final String TAB = "tab2";

	private ViewPager mViewPager;
	private MyFavorProductListFragment mAdapter;

	public static FavourListFragment newInstance(String title) {
		FavourListFragment fragment = new FavourListFragment();
		Bundle args = new Bundle();
		args.putString(TAB, title);
		fragment.setArguments(args);
		return fragment;

	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.my_footprint_fragment, container, false);
		
		mViewPager = (ViewPager) v.findViewById(R.id.mViewPager);
		initView();
		return v;
	}

	private void initView() {
		mAdapter = new MyFavorProductListFragment();
//		mViewPager.setAdapter(mAdapter);
	}



}
