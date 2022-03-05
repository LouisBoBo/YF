//package com.yssj.ui.fragment;
//
//import java.util.ArrayList;
//import java.util.List;
//import com.yssj.activity.R;
//import com.yssj.ui.adpter.DrawerAdpter;
//
//import android.app.ActionBar;
//import android.app.Activity;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.ActionBarDrawerToggle;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ListView;
//import android.widget.Toast;
//
//public class NavigationDrawerFragment extends Fragment implements
//		OnItemClickListener {
//
//	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
//	private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
//	private NavigationDrawerCallbacks mCallbacks;
//	private ActionBarDrawerToggle mDrawerToggle;
//	private DrawerLayout mDrawerLayout;
//	private ListView mDrawerListView;
//	private DrawerAdpter mDrawerAdpter;
//	private List<String> lUri;
//	private View mFragmentContainerView;
//	private int mCurrentSelectedPosition = 0;
//	private boolean mFromSavedInstanceState;
//	private boolean mUserLearnedDrawer;
//
//	public NavigationDrawerFragment() {
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		SharedPreferences sp = PreferenceManager
//				.getDefaultSharedPreferences(getActivity());
//		mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);
//
//		if (savedInstanceState != null) {
//			mCurrentSelectedPosition = savedInstanceState
//					.getInt(STATE_SELECTED_POSITION);
//			mFromSavedInstanceState = true;
//		}
//		selectItem(mCurrentSelectedPosition);
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		setHasOptionsMenu(true);
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		mDrawerListView = (ListView) inflater.inflate(
//				R.layout.fragment_navigation_drawer, container, false);
//		lUri = new ArrayList<String>();
//		for (int i = 0; i < 5; i++) {
//			lUri.add("抽屉：" + i);
//		}
//
//		mDrawerAdpter = new DrawerAdpter(getActivity(), lUri);
//		mDrawerListView.setAdapter(mDrawerAdpter);
//		mDrawerListView.setOnItemClickListener(this);
//		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
//		return mDrawerListView;
//	}
//
//	public void setData(List<String> data) {
//		this.lUri = data;
//	}
//
//	public boolean isDrawerOpen() {
//		return mDrawerLayout != null
//				&& mDrawerLayout.isDrawerOpen(mFragmentContainerView);
//	}
//
//	public void setUp(View view, DrawerLayout drawerLayout) {
//		mFragmentContainerView = view;
//		mDrawerLayout = drawerLayout;
//		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
//				GravityCompat.START);
//		ActionBar actionBar = getActivity().getActionBar();
//		actionBar.setDisplayHomeAsUpEnabled(true);
//		actionBar.setHomeButtonEnabled(true);
//		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,
//				R.drawable.ic_drawer, R.string.navigation_drawer_open,
//				R.string.navigation_drawer_close) {
//			@Override
//			public void onDrawerClosed(View drawerView) {
//				super.onDrawerClosed(drawerView);
//				if (!isAdded()) {
//					return;
//				}
//
//				getActivity().supportInvalidateOptionsMenu();
//			}
//			
//
//			@Override
//			public void onDrawerOpened(View drawerView) {
//				super.onDrawerOpened(drawerView);
//				if (!isAdded()) {
//					return;
//				}
//				if (!mUserLearnedDrawer) {
//					mUserLearnedDrawer = true;
//					SharedPreferences sp = PreferenceManager
//							.getDefaultSharedPreferences(getActivity());
//					sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true)
//							.commit();
//				}
//				getActivity().supportInvalidateOptionsMenu(); // 调用
//																// onPrepareOptionsMenu()
//			}
//		};
//		
//		// 如果是第一次进入应用，显示抽屉
//		if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
//			mDrawerLayout.openDrawer(mFragmentContainerView);
//		}
//
//		mDrawerLayout.post(new Runnable() {
//			@Override
//			public void run() {
//				mDrawerToggle.syncState();
//			}
//		});
//
//		mDrawerLayout.setDrawerListener(mDrawerToggle);
//	}
//
//	public void setUp(int fragmentId, DrawerLayout drawerLayout) {
//		mFragmentContainerView = getActivity().findViewById(fragmentId);
//		mDrawerLayout = drawerLayout;
//		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
//				GravityCompat.START);
//		ActionBar actionBar = getActivity().getActionBar();
//		actionBar.setDisplayHomeAsUpEnabled(true);
//		actionBar.setHomeButtonEnabled(true);
//		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,
//				R.drawable.ic_drawer, R.string.navigation_drawer_open,
//				R.string.navigation_drawer_close) {
//			@Override
//			public void onDrawerClosed(View drawerView) {
//				super.onDrawerClosed(drawerView);
//				if (!isAdded()) {
//					return;
//				}
//
//				getActivity().supportInvalidateOptionsMenu();
//			}
//
//			@Override
//			public void onDrawerOpened(View drawerView) {
//				super.onDrawerOpened(drawerView);
//				if (!isAdded()) {
//					return;
//				}
//				if (!mUserLearnedDrawer) {
//					mUserLearnedDrawer = true;
//					SharedPreferences sp = PreferenceManager
//							.getDefaultSharedPreferences(getActivity());
//					sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true)
//							.commit();
//				}
//				getActivity().supportInvalidateOptionsMenu(); // 调用
//																// onPrepareOptionsMenu()
//			}
//		};
//		// 如果是第一次进入应用，显示抽屉
//		if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
//			mDrawerLayout.openDrawer(mFragmentContainerView);
//		}
//
//		mDrawerLayout.post(new Runnable() {
//			@Override
//			public void run() {
//				mDrawerToggle.syncState();
//			}
//		});
//
//		mDrawerLayout.setDrawerListener(mDrawerToggle);
//	}
//
//	/***
//	 * 抽屉条目点击事件
//	 * 
//	 * @param position
//	 */
//	private void selectItem(int position) {
//		mCurrentSelectedPosition = position;
//		if (mDrawerListView != null) {
//			mDrawerListView.setItemChecked(position, true);
//		}
//
//		if (mDrawerLayout != null) {
//			mDrawerLayout.closeDrawer(mFragmentContainerView);
//		}
//		if (mCallbacks != null) {
//			mCallbacks.onNavigationDrawerItemSelected(position);
//		}
//	}
//
//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//		try {
//			mCallbacks = (NavigationDrawerCallbacks) activity;
//		} catch (ClassCastException e) {
//			throw new ClassCastException(
//					"Activity must implement NavigationDrawerCallbacks.");
//		}
//	}
//
//	@Override
//	public void onDetach() {
//		super.onDetach();
//		mCallbacks = null;
//	}
//
//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
//	}
//
//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//
//		if (mDrawerLayout != null && isDrawerOpen()) {
//			inflater.inflate(R.menu.global, menu);
//			showGlobalContextActionBar();
//		}
//		super.onCreateOptionsMenu(menu, inflater);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		if (mDrawerToggle.onOptionsItemSelected(item)) {
//			return true;
//		}
//
////		if (item.getItemId() == R.id.action_example) {
////			Toast.makeText(getActivity(), "点的购物车", Toast.LENGTH_SHORT).show();
////			return true;
////		}
//
//		return super.onOptionsItemSelected(item);
//	}
//
//	private void showGlobalContextActionBar() {
//		ActionBar actionBar = getActivity().getActionBar();
//		actionBar.setDisplayShowTitleEnabled(true);
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//		actionBar.setTitle(R.string.app_name);
//	}
//
//	/*private ActionBar getActionBar() {
//		return  getActivity().();
//	}*/
//
//	public static interface NavigationDrawerCallbacks {
//
//		void onNavigationDrawerItemSelected(int position);
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//
//		selectItem(arg2);
//	}
//}
