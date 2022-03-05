package com.yssj.ui.pager;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.model.ComModel;

import com.yssj.ui.base.BaseMainAdapter;
import com.yssj.ui.base.BasePager;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ShenHeZhongPage extends BasePager {
	private HashMap<String, Object> map;



	public ShenHeZhongPage(Context context) {
		super(context);
		
	}

	private PullToRefreshListView lv_common;
	private int currPage = 1;
	private WithdrawSHAdapter mAdapter;
	
	private boolean flag = true;		// 判断上拉、下拉的标志
	
	private LinearLayout account_nodata;
	private Button btn_view_allcircle;
	private TextView tv_qin,tv_no_join;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.pulltorefreshlistview_coupons, null);
		lv_common = (PullToRefreshListView) view.findViewById(R.id.lv_common);
		
		account_nodata = (LinearLayout) view.findViewById(R.id.account_nodata);
		
		btn_view_allcircle = (Button) view.findViewById(R.id.btn_view_allcircle);
		btn_view_allcircle.setVisibility(View.GONE);
		
		tv_qin = (TextView) view.findViewById(R.id.tv_qin);
		tv_qin.setText("O(∩_∩)O~亲~");
		tv_no_join = (TextView) view.findViewById(R.id.tv_no_join);
		tv_no_join.setText("暂无审核中明细");
		
		
		return view;
	}
	
	@Override
	public void initData() {

		currPage = 1;
		queryData();
		mAdapter = new WithdrawSHAdapter(context);
		lv_common.setAdapter(mAdapter);
		
		super.initIndicator(lv_common);
		
		lv_common.setMode(Mode.BOTH);
		
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
				// check   -1 为查询审核中的用户的提现
				//t_type  2 为查询非自动提现的记录
				return ComModel.selDepositFilter(context, currPage,"-1","2");
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				LoadingDialog.show((FragmentActivity) context);
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


	class WithdrawSHAdapter extends BaseMainAdapter {


		public WithdrawSHAdapter(Context context) {
			super(context);

		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			if (null == convertView) {
				convertView = View.inflate(context, R.layout.intergral_detail_item, null);

				holder =new ViewHolder();

				holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
				holder.tv_add_time = (TextView) convertView.findViewById(R.id.tv_add_time);
				holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}

			final HashMap<String, Object> map = (HashMap<String, Object>) getItem(position);
			if (map != null && !map.isEmpty()) {
				holder.tv_type.setText("审核中");
//			holder.tv_money.setText("-" + Float.parseFloat(map.get("money").toString()));
				holder.tv_num.setText("" + new DecimalFormat("#0.00").format(Double.parseDouble(map.get("money").toString()))+"元");
//				holder.tv_add_time.setText(DateFormatUtils.format(new Date(Long.parseLong(map.get("add_date").toString())), "yyyy-MM-dd HH:mm:ss"));
                holder.tv_add_time.setText(map.get("add_date")+"");


//				String checkCode = map.get("check").toString();


			}

//			convertView.setOnClickListener(new View.OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					Intent intent = new Intent(context, MyWalletCommonFragmentActivity.class);
//					intent.putExtra("item", (Serializable) map);
//					intent.putExtra("flag", "withDrawaDetailsFragment");
//					context.startActivity(intent);
//				}
//			});

			return convertView;
		}


		class ViewHolder {

			TextView tv_add_time, tv_type, tv_num;
		}
	}
}
