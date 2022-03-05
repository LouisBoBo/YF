package com.yssj.ui.fragment.funddetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.entity.FundDetail;
import com.yssj.model.ComModel2;

/***
 * 返现
 * @author Administrator
 *
 */
public class RebateFragment extends Fragment {
	
	private PullToRefreshListView listView;
	private FundDetailsAdapter mAdapter;

	private int index = 1, type = 1;
	
	private boolean isAdd = false;
	
	private LinearLayout account_nodata;
	private Button btn_view_allcircle;
	private TextView tv_qin,tv_no_join;

	private List<FundDetail> fundDetails = new ArrayList<FundDetail>();


//	private List<HashMap<String, Object>> fundDetails = new ArrayList<HashMap<String,Object>>();
	
	private void getData(int index){
//		new SAsyncTask<Integer, Void, List<FundDetail>>(getActivity(), 0){
//
//			@Override
//			protected List<FundDetail> doInBackground(FragmentActivity context,
//					Integer... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.getRebateRecord(context, params[0]);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			};
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//										 List<FundDetail> result,Exception e) {
//
//				super.onPostExecute(context, result);
//				if (e != null) {// 查询异常
//					account_nodata.setVisibility(View.VISIBLE);
//					listView.setVisibility(View.GONE);
//					listView.onRefreshComplete();
//				} else {// 查询商品详情成功，刷新界面
//					listView.onRefreshComplete();
//					if (isAdd) {
//						fundDetails.addAll(result);
//					} else {
//						if (result != null && result.size() == 0) {
//
//							account_nodata.setVisibility(View.VISIBLE);
//							listView.setVisibility(View.GONE);
//						}
//
//						fundDetails.clear();
//						fundDetails.addAll(result);
//					}
//					mAdapter.notifyDataSetChanged();
//				}
//			}
//
//		}.execute(index);



		new SAsyncTask<Integer, Void, List<FundDetail>>(getActivity(), 0) {

			protected void onPreExecute() {
				super.onPreExecute();
				LoadingDialog.show(getActivity());
			}

			@Override
			protected List<FundDetail> doInBackground(FragmentActivity context,
													  Integer... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getRebateRecord(context, params[0]);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context,
										 List<FundDetail> result,Exception e) {
				super.onPostExecute(context, result);
				if (e != null) {// 查询异常
					account_nodata.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
					listView.onRefreshComplete();
				} else {// 查询商品详情成功，刷新界面
					listView.onRefreshComplete();
					if (isAdd) {
						fundDetails.addAll(result);
					} else {
						if (result != null && result.size() == 0) {

							account_nodata.setVisibility(View.VISIBLE);
							listView.setVisibility(View.GONE);
						}

						fundDetails.clear();
						fundDetails.addAll(result);
					}
					mAdapter.notifyDataSetChanged();
				}

			}

		}.execute(index);


	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.trade_fragment, container,
				false);
		initView(v);
		getData(index);
		return v;
	}

	private void initView(View v){
		listView = (PullToRefreshListView) v.findViewById(R.id.trade_listview);
		mAdapter = new FundDetailsAdapter(getActivity(), fundDetails, type);


		listView.setAdapter(mAdapter);
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				index = 1;
				isAdd = false;
				getData(index);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				isAdd = true;
				getData(++index);
			}
		});
		
		account_nodata = (LinearLayout) v.findViewById(R.id.account_nodata);
		
		btn_view_allcircle = (Button) v.findViewById(R.id.btn_view_allcircle);
		btn_view_allcircle.setVisibility(View.GONE);
		
		tv_qin = (TextView) v.findViewById(R.id.tv_qin);
		tv_qin.setText("O(∩_∩)O~亲~");
		tv_no_join = (TextView) v.findViewById(R.id.tv_no_join);
		tv_no_join.setText("近期无记录");
	}


}
