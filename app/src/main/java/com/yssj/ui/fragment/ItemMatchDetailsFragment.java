package com.yssj.ui.fragment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.ItemView;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.XListViewMyShop;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.shopdetails.MatchDetailsActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.YunYingTongJi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemMatchDetailsFragment extends Fragment {

	private static Context mContext;

	private LinkedList<HashMap<String, Object>> dataList;

	private String type_id,code;

	private XListViewMyShop mList;

	private DateAdapter mAdapter;

	public XListViewMyShop getmList() {
		return mList;
	}

	public static ItemMatchDetailsFragment newInstances(int position, String type_id,String collocation_code,Context context) {
		ItemMatchDetailsFragment instance = new ItemMatchDetailsFragment();
		Bundle args = new Bundle();
		args.putString("code", collocation_code);
		args.putString("type_id",type_id);
		mContext = context;
		instance.setArguments(args);

		return instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.item_fragment_view_match, container, false);

		type_id = getArguments().getString("type_id");
		code = getArguments().getString("code");
		
		mList = (XListViewMyShop) v.findViewById(R.id.dataList);
		mList.setBackgroundColor(Color.parseColor("#f0f0f0"));
//		mList.setPullRefreshEnable(false);
		mList.setPullLoadEnable(false);
		dataList = new LinkedList<HashMap<String, Object>>();
		mAdapter = new DateAdapter(mContext);
		mList.setAdapter(mAdapter);
		return v;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}
	
	public void refresh() {
//		initData();
		
	}


	public void setSelecttion() {
		if (mAdapter.getCount() > 0 && mList.getFirstVisiblePosition() != 0) {
			mList.setSelection(0);
		}
	}
	
	private void initData() {
		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(getActivity(), 0) {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				LoadingDialog.show(getActivity());
			}
			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
				super.onPostExecute(context, result);
				if (e != null) {
					return;
				}
				if (result != null) {
					dataList.addAll(result);
				}
				mAdapter.notifyDataSetChanged();
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
					throws Exception {
				return  ComModel2.getMatchDetailsRec(mContext,code,type_id);
			}

		}.execute();
	}

	private int position = 0;

	public void setLikeStatus(int liked) {
		if ((liked + "").equals(dataList.get(position).get("isLike") + "")) {
			return;
		}
		dataList.get(position).put("isLike", liked);
		mAdapter.notifyDataSetChanged();
	}

	private class DateAdapter extends BaseAdapter {

		private Context context;

		private int picHeight;

		public DateAdapter(Context context) {
			super();
			this.context = context;
			int dp = DP2SPUtil.dp2px(context, 24);
			picHeight = (context.getResources().getDisplayMetrics().widthPixels - dp) / 2 * 900 / 600;
		}

		@Override
		public int getCount() {
			int count = 0;
			count += dataList.size() % 2 == 0 ? dataList.size() / 2 : dataList.size() / 2 + 1;
			if (count == 0) {
				count = 1;
			}
			return count;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			ItemViews items;
			if (view == null) {
				view = LayoutInflater.from(context).inflate(R.layout.item_fragment_adapter, null);
				items = new ItemViews();
				items.left = (com.yssj.custom.view.ItemView) view.findViewById(R.id.left);
				items.left.setHeight(picHeight);
				items.right = (com.yssj.custom.view.ItemView) view.findViewById(R.id.right);
				items.right.setHeight(picHeight);
				items.noData = (TextView) view.findViewById(R.id.noData);
				items.noData.getLayoutParams().height = MatchDetailsActivity.heigth;
				items.data = view.findViewById(R.id.data);
				view.setTag(items);
			} else {
				items = (ItemViews) view.getTag();
			}
			if (dataList.isEmpty()) {
				items.noData.setVisibility(view.VISIBLE);
				items.data.setVisibility(view.GONE);
			} else {
				items.noData.setVisibility(view.GONE);
				items.data.setVisibility(view.VISIBLE);
			}

			position = position * 2;

			if (dataList.size() > position) {
				// String url = (String) dataList.get(position).get("def_pic");
				items.left.iniView(dataList.get(position));
				items.left.setTag(position);
				items.left.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						YunYingTongJi.yunYingTongJi(context, 66);//搭配详情页列表下商品图片
						int position = (Integer) arg0.getTag();
						mContext.getSharedPreferences("YSSJ_yf", Context.MODE_PRIVATE).edit()
								.putBoolean("isGoDetail", true).commit();
						addScanDataTo((String) dataList.get(position).get("shop_code"));
						ItemMatchDetailsFragment.this.position = position;
						Intent intent = new Intent(mContext, ShopDetailsActivity.class);
						intent.putExtra("code", (String) dataList.get(position).get("shop_code"));
						// context.startActivity(intent);
						intent.putExtra("shopCarFragment", "shopCarFragment");
						FragmentActivity activity = (FragmentActivity) mContext;
						activity.startActivityForResult(intent, 101);
						activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
					}
				});
			}
			if (dataList.size() > position + 1) {
				items.right.setVisibility(view.VISIBLE);
				// String url = (String) dataList.get(position +
				// 1).get("def_pic");
				items.right.iniView(dataList.get(position + 1));
				items.right.setTag(position + 1);
				items.right.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						YunYingTongJi.yunYingTongJi(context, 66);//搭配详情页列表下商品图片
						int position = (Integer) arg0.getTag();
						mContext.getSharedPreferences("YSSJ_yf", Context.MODE_PRIVATE).edit()
								.putBoolean("isGoDetail", true).commit();
						addScanDataTo((String) dataList.get(position).get("shop_code"));
						ItemMatchDetailsFragment.this.position = position;
						Intent intent = new Intent(mContext, ShopDetailsActivity.class);
						intent.putExtra("code", (String) dataList.get(position).get("shop_code"));
						// context.startActivity(intent);
						intent.putExtra("shopCarFragment", "shopCarFragment");
						FragmentActivity activity = (FragmentActivity) mContext;
						activity.startActivityForResult(intent, 101);
						activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
					}
				});
			} else {
				items.right.setVisibility(View.INVISIBLE);
			}

			return view;
		}

	}

	/*
	 * 把浏览过的数据添加进数据库
	 */
	private void addScanDataTo(final String shop_code) {
		new SAsyncTask<Void, Void, ReturnInfo>(getActivity()) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
				return ComModel.addMySteps(context, shop_code);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
				super.onPostExecute(context, result, e);
			}

		}.execute();
	}

	private static class ItemViews {

		private ItemView left;
		private ItemView right;
		private TextView noData;
		private View data;
	}
}
