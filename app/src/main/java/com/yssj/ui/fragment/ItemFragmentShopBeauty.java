//package com.yssj.ui.fragment;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//
//import com.yssj.YJApplication;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.ItemView;
//import com.yssj.custom.view.XListView;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.model.ComModel;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
//import com.yssj.utils.ToastUtil;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//public class ItemFragmentShopBeauty extends Fragment {
//
//	private static Context mContext;
//
//	private LinkedList<HashMap<String, Object>> dataList;
//	
//	private String id,title;
//	
//	
//	private XListView mList;
//	
//	
//	private DateAdapter mAdapter;
//	
//	private int index=1;
//
//	
//	
//	public XListView getmList() {
//		return mList;
//	}
//
//	public static ItemFragmentShopBeauty newInstances(int position, String title,
//			String id, Context context) {
//		ItemFragmentShopBeauty instance = new ItemFragmentShopBeauty();
//		Bundle args = new Bundle();
//		args.putString("title", title);
//		args.putString("id", id);
//		mContext = context;
//		instance.setArguments(args);
//
//		return instance;
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		View v = inflater
//				.inflate(R.layout.item_fragment_view, container, false);
//		
//		title=getArguments().getString("title");
//		id=getArguments().getString("id");
//		mList=(XListView) v.findViewById(R.id.dataList);
////		mList.setPullRefreshEnable(false);
//		mList.setPullLoadEnable(true);
//		mList.setXListViewListener(new XListView.IXListViewListener() {
//			
//			@Override
//			public void onRefresh() {
//				
//			}
//			
//			@Override
//			public void onLoadMore() {
//				index++;
//				initData(String.valueOf(index));
//			}
//		});
//		dataList = new LinkedList<HashMap<String, Object>>();
//		mAdapter = new DateAdapter(mContext);
//		mList.setAdapter(mAdapter);
//		
//		return v;
//	}
//
//	@Override
//	public void onActivityCreated( Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		initData(String.valueOf(index));
//	}
//	
////	@Override
////	public void setUserVisibleHint(boolean isVisibleToUser) {
////		// TODO Auto-generated method stub
////		super.setUserVisibleHint(isVisibleToUser);
////		if(isVisibleToUser&&isVisible()){
////			if(dataList.isEmpty()){
////				
////			}
////		}
////	}
//	
//	public void refresh(){
//		index=1;
//		initData("1");
//	}
//	
//	
//	public void setSelecttion(){
//		if(mAdapter.getCount()>0&&mList.getFirstVisiblePosition()!=0){
//			mList.setSelection(0);
//		}
//	}
//	
//	private void initData(final String index) {
//		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(
//				getActivity(), 0) {
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<HashMap<String, Object>> result,Exception e) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result);
//				if(e!=null){
//					return ;
//				}
//				if(result!=null){
//					if(ItemFragmentShopBeauty.this.index==1){
//						dataList.clear();
//					}
//					if(result.size() == 0){
//						ToastUtil.showShortText(context, "已没有更多商品了");
//					}
//					dataList.addAll(result);
//				}else{
//					if(ItemFragmentShopBeauty.this.index==1){
//						dataList.clear();
//					}else{
//						ToastUtil.showShortText(context, "已没有更多商品了");
//					}
//					
//				}
//				mAdapter.notifyDataSetChanged();
//				mList.stopLoadMore();
//			}
//
//					@Override
//					protected boolean isHandleException() {
//						// TODO Auto-generated method stub
//						return true;
//					}
//			
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//				return ComModel2.getProductListBeautyShop(context, index, id,
//						String.valueOf(1), title, Integer.parseInt("30"));
//			}
//
//		}.execute();
//	}
//
//	private int position=0;
//	
//	public void setLikeStatus(int liked){
//		if((liked+"").equals(dataList.get(position).get("isLike")+"")){
//			return;
//		}
//		dataList.get(position).put("isLike", liked);
//		mAdapter.notifyDataSetChanged();
//	}
//	
//	private class DateAdapter extends BaseAdapter {
//
//		private Context context;
//		
//		private int picHeight;
//		
//		public DateAdapter(Context context) {
//			super();
//			this.context = context;
//			picHeight=context.getResources().getDisplayMetrics().widthPixels/2*900/600;
//		}
//
//		@Override
//		public int getCount() {
//			int count = 0;
//			count += dataList.size() % 2 == 0 ? dataList.size() / 2 : dataList
//					.size() / 2 + 1;
//			if(count==0){
//				count=1;
//			}
//			return count;
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public long getItemId(int arg0) {
//			// TODO Auto-generated method stub
//			return arg0;
//		}
//
//		@Override
//		public View getView(int position, View view, ViewGroup arg2) {
//			ItemViews items;
//			if (view == null) {
//				view = LayoutInflater.from(context).inflate(
//						R.layout.item_fragment_adapter, null);
//				items = new ItemViews();
//				items.left = (com.yssj.custom.view.ItemView) view
//						.findViewById(R.id.left);
//				items.left.setHeight(picHeight);
//				items.right = (com.yssj.custom.view.ItemView) view
//						.findViewById(R.id.right);
//				items.right.setHeight(picHeight);
//				items.noData=(TextView) view.findViewById(R.id.noData);
//				items.noData.getLayoutParams().height=MyShopFragment.height;
//				items.data=view.findViewById(R.id.data);
//				view.setTag(items);
//			} else {
//				items = (ItemViews) view.getTag();
//			}
//			if(dataList.isEmpty()){
//				items.noData.setVisibility(view.VISIBLE);
//				items.data.setVisibility(view.GONE);
//			}else{
//				items.noData.setVisibility(view.GONE);
//				items.data.setVisibility(view.VISIBLE);
//			}
//			
//			position = position * 2;
//
//			if (dataList.size() > position) {
//				//String url = (String) dataList.get(position).get("def_pic");
//				items.left.iniView(dataList.get(position));
//				items.left.setTag(position);
//				items.left.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View arg0) {
//						int position=(Integer) arg0.getTag();
//						mContext.getSharedPreferences("YSSJ_yf",
//								Context.MODE_PRIVATE).edit()
//								.putBoolean("isGoDetail", true).commit();
//						addScanDataTo((String) dataList.get(position).get("shop_code"));
//						ItemFragmentShopBeauty.this.position=position;
//						Intent intent = new Intent(mContext, ShopDetailsActivity.class);
//						intent.putExtra("code", (String) dataList.get(position).get("shop_code"));
//						// context.startActivity(intent);
//						intent.putExtra("shopCarFragment", "shopCarFragment");
//						FragmentActivity activity = (FragmentActivity) mContext;
//						activity.startActivityForResult(intent, 101);
//						activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);;
//					}
//				});
//			}
//			if (dataList.size() > position + 1) {
//				items.right.setVisibility(view.VISIBLE);
//				//String url = (String) dataList.get(position + 1).get("def_pic");
//				items.right.iniView(dataList.get(position + 1));
//				items.right.setTag(position+1);
//				items.right.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View arg0) {
//						int position=(Integer) arg0.getTag();
//						mContext.getSharedPreferences("YSSJ_yf",
//								Context.MODE_PRIVATE).edit()
//								.putBoolean("isGoDetail", true).commit();
//						addScanDataTo((String) dataList.get(position).get("shop_code"));
//						ItemFragmentShopBeauty.this.position=position;
//						Intent intent = new Intent(mContext, ShopDetailsActivity.class);
//						intent.putExtra("code", (String) dataList.get(position).get("shop_code"));
//						// context.startActivity(intent);
//						intent.putExtra("shopCarFragment", "shopCarFragment");
//						FragmentActivity activity = (FragmentActivity) mContext;
//						activity.startActivityForResult(intent, 101);
//						activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);;
//					}
//				});
//			} else {
//				items.right.setVisibility(View.INVISIBLE);
//			}
//
//			return view;
//		}
//
//	}
//	
//	/*
//	 * 把浏览过的数据添加进数据库
//	 */
//	private void addScanDataTo(final String shop_code) {
//		new SAsyncTask<Void, Void, ReturnInfo>(getActivity()) {
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context,
//					Void... params) throws Exception {
//				return ComModel.addMySteps(context, shop_code);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					ReturnInfo result, Exception e) {
//				super.onPostExecute(context, result, e);
//			}
//
//		}.execute();
//	}
//	private static class ItemViews {
//
//		private ItemView left;
//		private ItemView right;
//		private TextView noData;
//		private View data;
//	}
//	
//}
