package com.yssj.ui.fragment;

import java.util.HashMap;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.entity.VipDikouData;
import com.yssj.model.ComModel2;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.pubu.PubuFragment;
import com.yssj.ui.adpter.StaggeredAdapter;
import com.yssj.utils.ToastUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class InviteHotFragment extends Fragment {
	private static Context mContext;
	private static boolean mIsFabu;
	private PullToRefreshListView r_list_view;
	private StaggeredAdapter mAdapter;
	private LinearLayout ll_nodata;
	private int index = 1;
	private int mType = 1;// 1：初始化数据；2：加载更多数据
	private static int mI; // 1:已购买的，2：我的最爱
	private TextView tv_nodata_img, tv_nodata_text;
	private VipDikouData vipDikouData;

	public static InviteHotFragment newInstances(Context context, boolean isFabu, int i) {
		mContext = context;
		mIsFabu = isFabu;
		mI = i;
		InviteHotFragment fragment = new InviteHotFragment();
		return fragment;
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(mContext, R.layout.fragment_invite_hot, null);
		r_list_view = (PullToRefreshListView) view.findViewById(R.id.r_list_view);
		ll_nodata = (LinearLayout) view.findViewById(R.id.ll_nodata);
		tv_nodata_img = (TextView) view.findViewById(R.id.tv_nodata_img);
		tv_nodata_text = (TextView) view.findViewById(R.id.tv_nodata_text);

		if (mIsFabu) {
			if (mI == 1) {
				tv_nodata_img.setBackground(mContext.getResources().getDrawable(R.drawable.icon_no_buy));
				tv_nodata_text.setText("你还木有购买过商品哦~");
			} else {
				tv_nodata_img.setBackground(mContext.getResources().getDrawable(R.drawable.icon_no_like));
				tv_nodata_text.setText("你还木有喜欢的商品哦~");
			}

		}

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListViewRefresh();
		r_list_view.setMode(Mode.BOTH);
		mAdapter = new StaggeredAdapter(mContext, true, mIsFabu);
		r_list_view.setAdapter(mAdapter);

		//查询会员情况
		if (YJApplication.instance.isLoginSucess()) {
			//查询抵扣
			HashMap<String, String> pairsMap = new HashMap<>();
			YConn.httpPost(mContext, YUrl.GETALLDIKOU, pairsMap, new HttpListener<VipDikouData>() {
				@Override
				public void onSuccess(VipDikouData result) {
					vipDikouData = result;
					initData(index + "");

				}

				@Override
				public void onError() {
					initData(index + "");

				}
			});

		}else{
			initData(index + "");

		}


	}

	private void setListViewRefresh() {
		r_list_view.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				mType = 1;
				index = 1;
				YaoQingFrendsActivity.shopCode = "";
				YaoQingFrendsActivity.shopPIC = "";
				initData(index + "");
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				// if (!isComplete) {// 当数据没加载完成 上拉可以加载更多
				// if (SignFragment.doType!=4) {
				index++;
				mType = 2;
				initData(index + "");
				// }else{
				// r_list_view.onRefreshComplete();//强制浏览个数 只获取最前面十件商品
				// }
			}
		});

	}

	private void initData(final String index) {
		// 下面是真数据
		final int pageSize = 10;

		new SAsyncTask<String, Void, List<HashMap<String, Object>>>((FragmentActivity) mContext, 0) {
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				LoadingDialog.show((FragmentActivity) mContext);
			}

			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
					throws Exception {

				// return ComModel2.getForceLook(context, pageSize, index);

				if (mIsFabu) {

					if (mI == 1) {// 已购买的
						return ComModel2.getFauShop(context, pageSize, index);

					} else {// 我的最爱
						return ComModel2.getMyFavourListFabu(context, Integer.parseInt(index), "1");
					}

				} else {
					return ComModel2.getProductList1(context, index, 6 + "", "1", "", pageSize, false);

				}

			}

			@Override
			protected boolean isHandleException() {

				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				LoadingDialog.hide((FragmentActivity) mContext);
				if (e == null) {

					if (mIsFabu && mType == 2 && mI ==1) {//发布中的--已购买的没有上拉加载更多
						r_list_view.onRefreshComplete();
						return;
					}

					if (result != null) {
						for (int i = 0; i < result.size(); i++) {

							result.get(i).put("isSeletorFlag", "0");
							result.get(i).put("mIsFabu", mIsFabu);
						}
					}
					if (mType == 1) {
						if (result == null || result.size() == 0) {
							ll_nodata.setVisibility(View.VISIBLE);
							r_list_view.setVisibility(View.GONE);
						} else {
							ll_nodata.setVisibility(View.GONE);
							r_list_view.setVisibility(View.VISIBLE);
						}
						mAdapter.setData(result,vipDikouData);

					} else if (mType == 2) {
						mAdapter.addItemLast(result);
						if (result.size() == 0) {
							ToastUtil.showShortText(context, "已没有更多商品了哦~");
						}
					}
				}
				r_list_view.onRefreshComplete();
			}

		}.execute(index);
	}
}
