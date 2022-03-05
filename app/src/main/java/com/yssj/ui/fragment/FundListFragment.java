package com.yssj.ui.fragment;

import java.util.List;

import com.yssj.app.ArrayAdapterCompat;
import com.yssj.app.SPullLoadListFragmentCompat;
import com.yssj.entity.FundDetail;
import com.yssj.model.ComModel2;
import com.yssj.ui.adpter.FundListAdapter;
import com.yssj.utils.LogYiFu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class FundListFragment extends SPullLoadListFragmentCompat<FundDetail> {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LogYiFu.e("nearbyFragment onCreate", "nearbyFragment onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	protected List<FundDetail> onLoadData(Context context, Integer index)
			throws Exception {
		// return ComModel.getParkLotByList(context, lat, lng,
		// 3,index).getParkLots();

		return ComModel2.getFundList(context, index);
	}

	@Override
	protected int getLoaderId() {
		return 0;
	}

	@Override
	protected ArrayAdapterCompat<FundDetail> onCreateListAdapter() {
		getListView().setDividerHeight(10);
		return new FundListAdapter(getActivity());
		// return null;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getListView().setDividerHeight(0);
	}

	// @Override
	// public void onItemClick(AdapterView<?> parent, View view, int position,
	// long id) {
	// super.onItemClick(parent, view, position, id);
	// position -= getListView().getHeaderViewsCount();
	//
	// if (position >= getAdapter().getCount()) {
	// return;
	// }
	// Intent intent = new Intent(getActivity(), UserDetailActivity.class);
	// intent.putExtra("item", (Serializable) getAdapter().getItem(position));
	// startActivity(intent);
	//
	// }

}
