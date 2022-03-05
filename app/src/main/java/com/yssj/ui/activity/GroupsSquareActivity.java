//package com.yssj.ui.activity;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
//import com.yssj.activity.R;
//import com.yssj.app.AppManager;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.RoundImageButton;
////import com.yssj.custom.view.SignActiveShopItemView;
//import com.yssj.entity.UserInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.main.HotSaleActivity;
//import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.ui.fragment.circles.SignFragment;
//import com.yssj.utils.DP2SPUtil;
//import com.yssj.utils.PicassoUtils;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YCache;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Paint;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.util.DisplayMetrics;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//
///***
// * 拼团广场 列表
// */
//public class GroupsSquareActivity extends BasicActivity implements OnClickListener {
//
//	private View img_back;
//	private int mType = 1;// 1：初始化数据；2：加载更多数据
//	private int index = 1;
//	private GroupsSquareAdapter mAdapter;
//	private PullToRefreshListView r_list_view;
//	public  int width;
//	public  int height;
//	private View nodataView;
//	private TextView nodataTv;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_groups_square);
//		AppManager.getAppManager().addActivity(this);
//		initView();
//	}
//
//	/**
//	 * 初始化View
//	 */
//	private void initView() {
//		img_back = findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//		r_list_view = (PullToRefreshListView) findViewById(R.id.r_list_view);
//		r_list_view.setMode(Mode.BOTH);
//		DisplayMetrics dm = new DisplayMetrics();
//		this.getWindowManager().getDefaultDisplay()
//				.getMetrics(dm);
//		width = dm.widthPixels;
//		height = dm.heightPixels;
//		mAdapter = new GroupsSquareAdapter(this);
//		r_list_view.setAdapter(mAdapter);
//		setListViewRefresh();
//		nodataView =findViewById(R.id.groups_nodata);
//		findViewById(R.id.btn_view_allcircle).setVisibility(View.GONE);
//		nodataTv = (TextView) findViewById(R.id.tv_qin);
//		nodataTv.setText("亲，暂时没有相关数据哦~");
//		findViewById(R.id.tv_no_join).setVisibility(View.GONE);
//		initData();
//
//	}
//
//	/**
//	 * 获取拼团广场列表
//	 *
//	 */
//	private void initData(){
//		final int pageSize = 10;
//		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
//					throws Exception {
//
//				return ComModel2.queryGroupSquareShopList(context,index+"",pageSize+"");
//			}
//
//			@Override
//			protected boolean isHandleException() {
//
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (e == null) {
//					if (mType == 1) {
//						if (result == null || result.size() == 0) {
//							r_list_view.setVisibility(View.GONE);
//							nodataView.setVisibility(View.VISIBLE);
//						} else {
//							r_list_view.setVisibility(View.VISIBLE);
//							nodataView.setVisibility(View.GONE);
//							mAdapter.setData(result);
//						}
//
//					} else if (mType == 2) {
//						if(result == null ||result.size()==0){
//							ToastUtil.showShortText(context, "没有更多商品了哦~");
//						}else{
//							mAdapter.addItemLast(result);
//							r_list_view.getRefreshableView().smoothScrollBy(100, 10);
//						}
//					}
//				}else{
//					r_list_view.setVisibility(View.GONE);
//					nodataView.setVisibility(View.VISIBLE);
//				}
//				r_list_view.onRefreshComplete();
//			}
//
//		}.execute();
//	}
//	private void setListViewRefresh(){
//		r_list_view.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//
//			@Override
//			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//				index = 1;
//				mType=1;
//				initData();
//			}
//
//			@Override
//			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//				index++;
//				mType = 2;
//				initData();
//			}
//
//		});
//
//	}
//
//	@Override
//	public void onClick(View v) {
//		super.onClick(v);
//		switch (v.getId()) {
//		case R.id.img_back:
//			onBackPressed();
//			break;
//		default:
//			break;
//		}
//	}
//
//
//	class GroupsSquareAdapter extends BaseAdapter {
//		private List<HashMap<String, Object>> mInfos;
//		private LayoutInflater layoutInflator;
//		private Context context;
//
//		public GroupsSquareAdapter(Context context) {
//			this.context = context;
//			mInfos = new ArrayList<HashMap<String, Object>>();
//			layoutInflator = LayoutInflater.from(context);
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHolder holder;
//			if (convertView == null) {
//				holder = new ViewHolder();
//				convertView = layoutInflator.inflate(R.layout.groups_square_list, null);
//				holder.imgMain = (ImageView) convertView.findViewById(R.id.groups_list_img_main);
//				holder.imgUserHead = (RoundImageButton) convertView.findViewById(R.id.user_img_head);
//				holder.tvUserName = (TextView) convertView.findViewById(R.id.user_name);
//				holder.tvExpText = (TextView) convertView.findViewById(R.id.groups_exp_text);
//				holder.tvPrice = (TextView) convertView.findViewById(R.id.groups_list_groupsprice);
//				holder.tvSignlePrice = (TextView) convertView.findViewById(R.id.groups_list_signleprice);
//				holder.groups_ljct_ll = convertView.findViewById(R.id.groups_ljct_ll);
//				convertView.setTag(holder);
//			} else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//
//			String shop_roll = (String) mInfos.get(position).get("shop_roll");
//			String shop_original = (String) mInfos.get(position).get("shop_original");
//			String shop_name = (String) mInfos.get(position).get("shop_name");
//			final String  shop_code =(String) mInfos.get(position).get("shop_code");
//			final String r_code = (String) mInfos.get(position).get("r_code");//拼团编号
//			String shop_url = (String) mInfos.get(position).get("shop_url");
//			holder.tvPrice.setText("¥"+new java.text.DecimalFormat("#0.0#").format(Double.parseDouble(shop_roll)));//商品拼团价
//			ToastUtil.addStrikeSpan(holder.tvSignlePrice, "¥"+new java.text.DecimalFormat("#0.0#").format(Double.parseDouble(shop_original)));//商品原价格 单独购买价格
//
////			UserInfo info = YCache.getCacheUserSafe(context);
////			int usetId = info.getUser_id();
//			String userName = (String) mInfos.get(position).get("user_name");
//			if(!TextUtils.isEmpty(userName)){
//				userName = userName.substring(0,1)+"***"+userName.substring(userName.length()-1);
//			}
//			holder.tvUserName.setText(userName);
//			String user_portrait = (String) mInfos.get(position).get("user_portrait");
////			SetImageLoader.initImageLoader(context, holder.imgUserHead, user_portrait, "");
//
//
//			PicassoUtils.initImage(context, user_portrait, holder.imgUserHead);
//
//
//			holder.tvExpText.setText(shop_name);
//
//			if(width>720){
////				SetImageLoader.initImageLoader(context, holder.imgMain,shop_code.substring(1, 4).toString()+"/"+shop_code+"/"+
////						shop_url,"!382");
//
//
//				PicassoUtils.initImage(context, shop_code.substring(1, 4).toString()+"/"+shop_code+"/"+shop_url+"!382", holder.imgMain);
//
//
//			}else{
////				SetImageLoader.initImageLoader(context, holder.imgMain,shop_code.substring(1, 4).toString()+"/"+shop_code+"/"+
////						shop_url,"!280");
//
//
//				PicassoUtils.initImage(context, shop_code.substring(1, 4).toString()+"/"+shop_code+"/"+shop_url+"!280", holder.imgMain);
//			}
////			holder.groups_ljct_ll.setOnClickListener(new OnClickListener() {
////
////				@Override
////				public void onClick(View v) {
////					goToCanTuan(shop_code,r_code);
////				}
////			});
////			holder.imgMain.setOnClickListener(new OnClickListener() {
////
////				@Override
////				public void onClick(View v) {
////					goToCanTuan(shop_code,r_code);
////				}
////			});
//			holder.imgUserHead.setClickable(false);
//			convertView.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					goToCanTuan(shop_code,r_code);
//				}
//			});
//
//			return convertView;
//		}
//		class ViewHolder {
//			ImageView imgMain;
//			RoundImageButton imgUserHead;
//			TextView tvUserName,tvExpText,tvPrice,tvSignlePrice;
//			View groups_ljct_ll;//立即参团按钮
//
//		}
//		private void goToCanTuan(String shopCode,String r_code){
//			Intent intent = new Intent(context, ShopDetailsActivity.class);
//			intent.putExtra("code", shopCode);//商品编号
//			intent.putExtra("r_code", r_code);//拼团编号
//			intent.putExtra("isSignActiveShop", true);//是活动商品 传true
//			intent.putExtra("isSignActiveShopScan", false);//不是浏览活动商品个数的赚钱任务 传false
//			intent.putExtra("mIsGroup", true);//去拼团
//			FragmentActivity activity = (FragmentActivity) context;
//			activity.startActivity(intent);
//			activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
//		}
//		@Override
//		public int getCount() {
//			return mInfos.size();
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			return null;
//		}
//
//		@Override
//		public long getItemId(int arg0) {
//			return arg0;
//		}
//
//		public void addItemLast(List<HashMap<String, Object>> datas) {
//			mInfos.addAll(datas);
//			this.notifyDataSetChanged();
//		}
//
//		public void addItemTop(List<HashMap<String, Object>> datas) {
//			mInfos.clear();
//			mInfos.addAll(datas);
//			this.notifyDataSetChanged();
//		}
//
//		public void clearData() {
//			mInfos.clear();
//			this.notifyDataSetChanged();
//		}
//
//		public void setData(List<HashMap<String, Object>> result) {
//			clearData();
//			mInfos.addAll(result);
//			this.notifyDataSetChanged();
//		}
//	}
//}
