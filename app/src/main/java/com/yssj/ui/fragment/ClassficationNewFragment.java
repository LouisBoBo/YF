package com.yssj.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.custom.view.MyMatchTitleView;
import com.yssj.custom.view.MyMatchTitleView.OnCheckTitleLentener;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/***
 * 分类 
 */
public class ClassficationNewFragment extends Fragment implements OnCheckTitleLentener,View.OnClickListener {
	private static final String TAB = "ClassficationNewFragment";
	private static Context mContext;
	private View ll_title,imageBack;
//	private YDBHelper dbHelp;
//	private LayoutInflater mInflater;
	private String [] titles;
	private int [] types;
//	private List<HashMap<String, String>> listSLevel;
	private MyMatchTitleView myMatchTitleView;
	private List<HashMap<String, String>> listTitle;
	private PagerAdapter mAdapter;
	private ViewPager mViewPager;
	private ItemClassficationFragment[] fragments;
	private int currentPosition;
	
	public static ClassficationNewFragment newInstance(String title, Context context) {
		ClassficationNewFragment fragment = new ClassficationNewFragment();
		Bundle args = new Bundle();
		args.putString(TAB, title);
		fragment.setArguments(args);
		mContext = context;
		return fragment;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_new_classfication, container, false);
//		mInflater=LayoutInflater.from(mContext);
		
		String title = (String) getArguments().get(TAB);
		ll_title = v.findViewById(R.id.ll_title);
		imageBack = v.findViewById(R.id.img_back);
		imageBack.setOnClickListener(this);
		ll_title.setVisibility(View.VISIBLE);
		
		myMatchTitleView = (MyMatchTitleView) v.findViewById(R.id.title);
		mViewPager = (ViewPager) v.findViewById(R.id.content_viewpager);
		listTitle = new ArrayList<HashMap<String, String>>();
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		titles = new String[]{"上衣","裤子","裙子","套装"};//一级类目名称
		types = new int[]{2,4,3,7};//一级类目ID
		
		myMatchTitleView.setCheckLintener(this);
//		if (null != mContext){
//			dbHelp = new YDBHelper(mContext);	
//		}else{
//			dbHelp = new YDBHelper(getActivity());
//		}
//		for (int i = 0; i < titles.length; i++) {
//			String sql = "select * from type_tag where type = " + types[i] + " order by _id";
//			listSLevel = dbHelp.query(sql);
//		}
		
		
		for (int i = 0; i < titles.length; i++) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("sort_name", titles[i]);
			hashMap.put("icon", ",");
			hashMap.put("type_id", types[i]+"");
			listTitle.add(hashMap);
		}
		myMatchTitleView.setData(listTitle);
		fragments = new ItemClassficationFragment[listTitle.size()];
		mAdapter = new PagerAdapter(getActivity().getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);
		
		setEvent();
		
	}
	
	public class PagerAdapter extends FragmentStatePagerAdapter {
		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) { 
			return listTitle.get(position).get("sort_name");
		}

		@Override
		public int getCount() {
			return listTitle.size();
		}

		@Override
		public Fragment getItem(int position) {
			ItemClassficationFragment fragment = fragments[position];
			if (fragment == null) {
				fragment = ItemClassficationFragment.newInstances(position, listTitle.get(position).get("type_id"), mContext);
				fragments[position] = fragment;
			}
			return fragment;
		}

	}
	
	private void setEvent() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				myMatchTitleView.setPosition(arg0);
				currentPosition = arg0;
				if (null != ((ItemClassficationFragment) mAdapter.getItem(arg0)).getmList()
						&& ((ItemClassficationFragment) mAdapter.getItem(arg0)).getmList().getCount() != 0)
					((ItemClassficationFragment) mAdapter.getItem(arg0)).getmList().setSelection(0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}


	


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().onBackPressed();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void checkTitle(View v) {
		mViewPager.setCurrentItem(v.getId());
		currentPosition = v.getId();
	}
	

}
