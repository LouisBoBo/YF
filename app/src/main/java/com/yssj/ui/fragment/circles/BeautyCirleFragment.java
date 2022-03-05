//package com.yssj.ui.fragment.circles;
//
//import java.util.HashMap;
//import java.util.List;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.yssj.YConstance;
//import com.yssj.activity.R;
//import com.yssj.app.ArrayAdapterCompat;
//import com.yssj.app.SPullLoadListFragment;
//import com.yssj.app.SPullLoadListFragmentCompat;
//import com.yssj.entity.FundDetail;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.adpter.FundListAdapter;
//import com.yssj.utils.SetImageLoader.AnimateFirstDisplayListener;
//
///***
// * 美圈
// * 
// * @author Administrator
// * 
// */
//
//public abstract class BeautyCirleFragment<T> extends
//		SPullLoadListFragment<T> {
//	/** 改用onLoadData */
//	@Override
//	protected List<T> onInitData(Context context) throws Exception {
//		return onLoadData(context, YConstance.FirstPageIndex);
//	}
//
//	/** 改用onLoadData */
//	@Override
//	protected List<T> onLoadMore(Context context, Integer index)
//			throws Exception {
//		return onLoadData(context, index);
//	}
//
//	abstract protected List<T> onLoadData(Context context, Integer index)
//			throws Exception;
//
//	@Override
//	public void onHiddenChanged(boolean hidden) {
//		// TODO Auto-generated method stub
//		super.onHiddenChanged(hidden);
//	}
//}
