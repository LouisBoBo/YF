//package com.yssj.ui.activity;
//
//import java.util.List;
//import java.util.Map;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.SlideShowView;
//import com.yssj.entity.Shop;
//import com.yssj.entity.ShopOption;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.SetImageLoader;
///**
// *
// * 会员商品列表
// * 
// * @author lbp
// *
// */
//public class MembersGoodsListActivity extends BasicActivity {
//	
//	private PullToRefreshListView membersListView;
//	
//	private List<Map<String, String>> list;
//	
//	private MembersAdapter mAdapter;
//	
//	private List<ShopOption> pagerList;
//	
//	private int index=1;
//	
//	private int width;
//	private LinearLayout jiuba;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		
//		setContentView(R.layout.members_goods_list_activity);
//		
//		jiuba = (LinearLayout)findViewById(R.id.jiuba);
//		jiuba.setBackgroundColor(Color.WHITE);
//		findViewById(R.id.img_back).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				onBackPressed();
//			}
//		});
//		
//		membersListView=(PullToRefreshListView) findViewById(R.id.members_list);
//		
//		mAdapter=new MembersAdapter();
//		
//		membersListView.setAdapter(mAdapter);
//		
//		membersListView.setMode(Mode.BOTH);//上拉下拉
//		
//		width=getResources().getDisplayMetrics().widthPixels;
//		
//		membersListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2() {
//
//			@Override
//			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
//				index=1;
//				initData();
//				initImgData();
//			}
//
//			@Override
//			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
//				index++;
//				initData();
//			}
//		});
//		
//		initData();
//		initImgData();
//	}
//	
//	private void initImgData(){
//		
//		new SAsyncTask<Void, Void, List<ShopOption>>((FragmentActivity)context, R.string.wait){
//			@Override
//			protected List<ShopOption> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.getMembersImgList(context);
//			}
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<ShopOption> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if(e==null&&result!=null){
//					pagerList=result;
//					mAdapter.notifyDataSetChanged();
//				}
//				membersListView.onRefreshComplete();
//			}
//		}.execute();
//	}
//	
//	
//	/**
//	 * 添加列表数据
//	 */
//	private void initData(){
//		new SAsyncTask<Void, Void, List<Map<String, String>>>((FragmentActivity)context, R.string.wait){
//			@Override
//			protected List<Map<String, String>> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.getMembersShopList(context,String.valueOf(index));
//			}
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<Map<String, String>> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if(e==null&&result!=null){
//					if(index==1){
//						list=result;
//					}else{
//						list.addAll(result);
//					}
//					mAdapter.notifyDataSetChanged();
//				}
//				membersListView.onRefreshComplete();
//			}
//		}.execute();
//	}
//	
//	
//	private class MembersAdapter extends BaseAdapter{
//		
//		@Override
//		public int getCount() {
//			int count=0;
//			if(pagerList!=null&&pagerList.size()>0){
//				count++;
//			}
//			if(list!=null&&list.size()>0){
//				count=count+list.size()%2==0?list.size()/2:list.size()/2+1;
//			}
//			return count;
//		}
//
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View v, ViewGroup parent) {
//			ViewHolder vh;
//			if(v==null){
//				v=LayoutInflater.from(context).inflate(R.layout.members_item, parent,false);
//				vh=new ViewHolder();
//				vh.left=v.findViewById(R.id.left);
//				vh.right=v.findViewById(R.id.right);
//				vh.leftImage=(ImageView) v.findViewById(R.id.left_img);
//				vh.rightImage=(ImageView) v.findViewById(R.id.right_img);
//				vh.leftName=(TextView) v.findViewById(R.id.left_name);
//				vh.RightName=(TextView) v.findViewById(R.id.right_name);
//				vh.leftPrice=(TextView) v.findViewById(R.id.left_price);
//				vh.rightPrice=(TextView) v.findViewById(R.id.right_price);
//				vh.pager=(SlideShowView) v.findViewById(R.id.pager);
//				vh.pager.getLayoutParams().height=width/2;
//				vh.leftImage.getLayoutParams().height=width*3/4;
//				vh.rightImage.getLayoutParams().height=width*3/4;
//				v.setTag(vh);
//			}else{
//				vh=(ViewHolder) v.getTag();
//			}
//			if(position==0&&pagerList!=null&&pagerList.size()>0){//轮播图
//				vh.left.setVisibility(View.GONE);
//				vh.right.setVisibility(View.GONE);
//				vh.pager.setVisibility(View.VISIBLE);
//				vh.pager.setData(pagerList, context);
//				return v;
//			}
//			
//			vh.left.setVisibility(View.VISIBLE);
//			vh.right.setVisibility(View.VISIBLE);
//			vh.pager.setVisibility(View.GONE);
//			
//			if(pagerList!=null&&pagerList.size()>0){
//				position=(position-1)*2;
//			}else{
//				position=position*2;
//			}
//			
//			Map<String, String> leftMap=list.get(position);
//			
//			vh.leftName.setText(Shop.getShopNameStr(leftMap.get("shop_name")));
//			
//			vh.leftPrice.setText("￥"+leftMap.get("shop_se_price"));
//			if(width>720){
//				SetImageLoader.initImageLoader(null, vh.leftImage, leftMap.get("shop_code").substring(1,4)+"/"+leftMap.get("shop_code")+"/"+leftMap.get("def_pic"),"!382");
//			}else{
//				SetImageLoader.initImageLoader(null, vh.leftImage, leftMap.get("shop_code").substring(1,4)+"/"+leftMap.get("shop_code")+"/"+leftMap.get("def_pic"),"!280");
//			}
//			
//			
//			vh.left.setTag(leftMap.get("shop_code"));
//			
//			if(list.size()>position+1){
//				vh.right.setVisibility(View.VISIBLE);
//				
//				Map<String, String> rightMap=list.get(position+1);
//				
//				vh.RightName.setText(Shop.getShopNameStr(rightMap.get("shop_name")));
//				
//				vh.rightPrice.setText("￥"+rightMap.get("shop_se_price"));
//				if(width>720){
//					SetImageLoader.initImageLoader(null, vh.rightImage, rightMap.get("shop_code").substring(1,4)+"/"+rightMap.get("shop_code")+"/"+rightMap.get("def_pic"),"!382");
//				}else{
//					SetImageLoader.initImageLoader(null, vh.rightImage, rightMap.get("shop_code").substring(1,4)+"/"+rightMap.get("shop_code")+"/"+rightMap.get("def_pic"),"!280");
//				}
//				
//				
//				vh.right.setTag(rightMap.get("shop_code"));
//				
//				vh.right.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						
//						Intent intent = new Intent(context, ShopDetailsActivity.class);
//						intent.putExtra("code", v.getTag().toString());
//						intent.putExtra("isMembers", true);
//						startActivityForResult(intent, 101);
//						overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);;
//						
//					}
//				});
//				
//			}else{
//				vh.right.setVisibility(View.INVISIBLE);
//			}
//			
//			vh.left.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					
//					Intent intent = new Intent(context, ShopDetailsActivity.class);
//					intent.putExtra("code", v.getTag().toString());
//					intent.putExtra("isMembers", true);
//					startActivityForResult(intent, 101);
//					overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);;
//				}
//			});
//			
//			
//			return v;
//		}
//		
//	}
//	
//	private class ViewHolder{
//		TextView leftName,RightName,leftPrice,rightPrice;
//		ImageView leftImage,rightImage;
//		View left,right;
//		SlideShowView pager;
//	}
//}
