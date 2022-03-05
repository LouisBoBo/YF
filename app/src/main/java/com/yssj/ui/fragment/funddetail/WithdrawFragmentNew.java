package com.yssj.ui.fragment.funddetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel;
import com.yssj.ui.adpter.WithdrawAdapter;
import com.yssj.ui.base.BaseFragment;

/***
 * 提现
 * @author Administrator
 *
 */
public class WithdrawFragmentNew extends BaseFragment implements OnItemClickListener{
	
	private PullToRefreshListView lv_common;
	private WithdrawAdapter mAdapter;
	
	private int currPage = 1;
	
	private boolean flag = true;		// 判断上拉、下拉的标志
	
	private LinearLayout account_nodata;
	private Button btn_view_allcircle;
	private TextView tv_qin,tv_no_join;
	
	
	@Override
	public View initView() {
		view = View.inflate(context, R.layout.pulltorefreshlistview_common, null);
		lv_common = (PullToRefreshListView) view.findViewById(R.id.lv_common);
		
		account_nodata = (LinearLayout) view.findViewById(R.id.account_nodata);
		
		btn_view_allcircle = (Button) view.findViewById(R.id.btn_view_allcircle);
		btn_view_allcircle.setVisibility(View.GONE);
		
		tv_qin = (TextView) view.findViewById(R.id.tv_qin);
		tv_qin.setText("O(∩_∩)O~亲~");
		tv_no_join = (TextView) view.findViewById(R.id.tv_no_join);
		tv_no_join.setText("近期无记录");
		
		
		return view;
	}

	@Override
	public void initData() {
		queryData();
		
		mAdapter = new WithdrawAdapter(context);
		
		lv_common.setAdapter(mAdapter);
		
		mAdapter.initIndicator(lv_common);
		
		lv_common.setMode(Mode.BOTH);
		lv_common.setEnabled(false);
		
		lv_common.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
					flag = true;
					
					currPage = 1 ;
					queryData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
					flag = false;
					currPage ++;
					queryData();
			}
		});
	}
	
	private void queryData() {
		
		new SAsyncTask<Integer, Void, List<HashMap<String, Object>> >((FragmentActivity) context,null, R.string.wait){

			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Integer... params) throws Exception {
				
				return ComModel.selDeposit(context, currPage);
			}
			
			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					List<HashMap<String, Object>> result, Exception e) {
				super.onPostExecute(context, result, e);
				
				if (e != null) {// 查询异常
					account_nodata.setVisibility(View.VISIBLE);
					lv_common.setVisibility(View.GONE);
					lv_common.onRefreshComplete();
				} else {
					
					if(flag){
						
						if(result != null && (result.size() == 0 || result.isEmpty())){
							account_nodata.setVisibility(View.VISIBLE);
							lv_common.setVisibility(View.GONE);
						}
						
						mAdapter.addItemFirst(result!=null?result:null);
					}else{
						mAdapter.addItemLast(result!=null?result:null);
					}
					
					mAdapter.notifyDataSetChanged();
					lv_common.onRefreshComplete();
					
				}
			}

			
		}.execute(currPage);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}

	@Override
	public void onClick(View v) {
		
	}



}
