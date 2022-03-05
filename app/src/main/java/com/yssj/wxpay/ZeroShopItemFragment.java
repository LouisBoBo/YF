//package com.yssj.ui.fragment;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.XListView;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ToastUtil;
//
//import android.R.layout;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Paint;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.text.Html;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//public class ZeroShopItemFragment extends Fragment {
//
//	private static Context mContext;
//
//	
////	private List<HashMap<String, Object>> shopList;
//	private List<HashMap<String, Object>> pList;
//	
//	private String pos;
//	
//	
//	private XListView mList;
//	
//	
//	private DateAdapter mAdapter;
//	private String[] str = { "", "", "二件", "三件", "四件", "五件", "六件", "七件", "八件", "九件" };
//	
//	private int index=1;
//	public onZeroShopRefreshListener zeroShopRefresh;
//	public interface onZeroShopRefreshListener{
//		void onZeroShopRefresh();
//	}
//	public  void setOnZeroRefreshListener(Fragment f){
//		this.zeroShopRefresh = (onZeroShopRefreshListener) f;
//	}
//	
//	
//	public XListView getmList() {
//		return mList;
//	}
//
//	public static ZeroShopItemFragment newInstances(int position,  Context context) {
//		ZeroShopItemFragment instance = new ZeroShopItemFragment();
//		Bundle args = new Bundle();
//		args.putString("position", position+"");
////		args.putString("title", title);
////		args.putString("id", id);
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
//		pos = getArguments().getString("position");
////		title=getArguments().getString("title");
////		id=getArguments().getString("id");
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
////		shopList= new ArrayList<HashMap<String, Object>>();
//		pList   = new ArrayList<HashMap<String, Object>>();
//		mAdapter = new DateAdapter(mContext);
//		mList.setAdapter(mAdapter);
//		
//		return v;
//	}
//
//	@Override
//	public void onActivityCreated( Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		index=1;
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
//		new SAsyncTask<String, Void, Map<String, List<HashMap<String, Object>>>>(
//				getActivity(), 0) {
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					Map<String, List<HashMap<String, Object>>> result,Exception e) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result);
//				if(e!=null){
//					mList.stopLoadMore();
//					return ;
//				}
//				if(result!=null){
//					if(ZeroShopItemFragment.this.index==1){
//						pList.clear();
////						shopList.clear();
//					}
//					
//					if(result.size() == 0){
//						ToastUtil.showShortText(context, "已没有更多商品了");
//					}
//					
////				shopList.addAll(result.get("shopList"));
//				pList.addAll(result.get("pList"));
//				}else{
//					if(ZeroShopItemFragment.this.index==1){
//						pList.clear();
////						shopList.clear();
//					}else{
//						ToastUtil.showShortText(context, "已没有更多商品了");
//					}
//					
//				}
//				mAdapter.notifyDataSetChanged();
//				mList.stopLoadMore();
//				zeroShopRefresh.onZeroShopRefresh();
//			}
//
//					@Override
//					protected boolean isHandleException() {
//						// TODO Auto-generated method stub
//						return true;
//					}
//			
//			@Override
//			protected Map<String, List<HashMap<String, Object>>> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//				return ComModel2.getZeroShopProductList(context, index,pos);
//			}
//
//		}.execute();
//	}
//
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
//			picHeight=ZeroShopFragment.width/2*900/600;
//		}
//
//		@Override
//		public int getCount() {
//			int count = 0;
//			count = pList.size();
//			
//			
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
//		@SuppressWarnings("unchecked")
//		@Override
//		public View getView(final int position, View view, ViewGroup arg2) {
//			ViewHolder holder;
//			
//			if (view == null) {
//				view = LayoutInflater.from(context).inflate(
//						R.layout.zero_shop_item_fragment_adapter, null);
//				holder = new ViewHolder();
//				
//				
//				
//				
//				
//				holder.tv_zero_product_name = (TextView) view.findViewById(R.id.tv_zero_product_name);
//				
//				holder.container_item =(LinearLayout) view.findViewById(R.id.container_item);
//				holder.rl_zero_shop_top = (RelativeLayout) view.findViewById(R.id.rl_zero_shop_top);
//				holder.v_line           = view.findViewById(R.id.v_line);
//				holder.v_huisequyu      = view.findViewById(R.id.v_huisequyu);
//				holder.rl_pb_product = (RelativeLayout) view.findViewById(R.id.rl_pb_product);
//				holder.pb_allowance_count =(ProgressBar) view.findViewById(R.id.pb_allowance_count);
//				holder.rl_zero_shop_product1 = (RelativeLayout) view.findViewById(R.id.rl_zero_shop_product1);
//				holder.rl_zero_shop_product2 = (RelativeLayout) view.findViewById(R.id.rl_zero_shop_product2);
//				holder.rl_zero_shop_product3 = (RelativeLayout) view.findViewById(R.id.rl_zero_shop_product3);
//				holder.tv_free_postage   = (TextView) view.findViewById(R.id.tv_free_postage);
//				holder.tv_zero_price_outdate1 = (TextView) view.findViewById(R.id.tv_zero_price_outdate);
//				holder.tv_zero_price_outdate2 = (TextView) view.findViewById(R.id.tv_zero_price_outdate2);
//				holder.tv_zero_price_outdate3 = (TextView) view.findViewById(R.id.tv_zero_price_outdate3);
//				
//				holder.tv_package_kind = (TextView) view.findViewById(R.id.tv_package_kind);
//				holder.tv_allowance_count = (TextView) view.findViewById(R.id.tv_allowance_count);
//				holder.tv_zero_shop_postage =(TextView) view.findViewById(R.id.tv_zero_shop_postage);
//				holder.pb_allowance_count_zong = (ProgressBar) view.findViewById(R.id.pb_allowance_count_zong);
//				
//				holder.tv_purchase_num1    = (TextView) view.findViewById(R.id.tv_purchase_num);
//				holder.tv_purchase_num2    = (TextView) view.findViewById(R.id.tv_purchase_num2);
//				holder.tv_purchase_num3    = (TextView) view.findViewById(R.id.tv_purchase_num3);
//				
//				
//				//商品图片
//				holder.img_zero_shop_product1 = (ImageView) view.findViewById(R.id.img_zero_shop_product1);
//				holder.img_zero_shop_product2 = (ImageView) view.findViewById(R.id.img_zero_shop_product2);
//				holder.img_zero_shop_product3 = (ImageView) view.findViewById(R.id.img_zero_shop_product3);
//				//商品名称
//				holder.tv_zero_product_name1  = (TextView) view.findViewById(R.id.tv_zero_product_name);
//				holder.tv_zero_product_name2  = (TextView) view.findViewById(R.id.tv_zero_product_name2);
//				holder.tv_zero_product_name3  = (TextView) view.findViewById(R.id.tv_zero_product_name3);
//				//商品价格
//				holder.tv_zero_product_price1  = (TextView) view.findViewById(R.id.tv_zero_product_price);
//				holder.tv_zero_product_price2  = (TextView) view.findViewById(R.id.tv_zero_product_price2);
//				holder.tv_zero_product_price3  = (TextView) view.findViewById(R.id.tv_zero_product_price3);
//				
//				holder.tv_29_allowance_count   = (TextView) view.findViewById(R.id.tv_29_allowance_count);
//				
//				holder.tv_zero_shop_postage_tou = (TextView) view.findViewById(R.id.tv_zero_shop_postage_tou);
//				holder.tv_zero_shop_words      = (TextView) view.findViewById(R.id.tv_zero_shop_words);
//				
//				holder.tv_package_kind_tou = (TextView) view.findViewById(R.id.tv_package_kind_tou);
//				
//				
//				
//				view.setTag(holder);
//			} else {
//				holder = (ViewHolder) view.getTag();
//			}
//			LayoutInflater inflater = LayoutInflater.from(context);
//			holder.container_item.removeAllViews();
//			 HashMap<String, Object> mapObj = pList.get(position);
//			 String code = mapObj.get("code")+"";
//					if(position==0&&("0".equals(pos))){
////						holder.tv_zero_shop_words.setVisibility(View.VISIBLE);
////					
////						holder.tv_zero_shop_words.setText("|  购买需抵扣100积分 首次购买免积分");
//						
//					}else{
//						holder.tv_zero_shop_words.setVisibility(View.GONE);
//					}
//					holder.tv_free_postage.setText(mapObj.get("name")+"");
//					holder.rl_zero_shop_top.setVisibility(View.VISIBLE); 
//					holder.v_line.setVisibility(View.VISIBLE);
//					holder.pb_allowance_count.setVisibility(View.GONE);
//					holder.rl_pb_product.setVisibility(View.GONE);
//					holder.tv_package_kind.setText(mapObj.get("name")+"");
//					holder.tv_allowance_count.setText("仅剩"+mapObj.get("r_num")+"套");
//					holder.pb_allowance_count_zong.setProgress(
//							(int) ((Double.parseDouble((String) mapObj.get("r_num"))/Double.parseDouble((mapObj.get("num")+"")))*100));
//					
//					int count =0;
//					String name = null ;
//					List<HashMap<String, Object>> shopList = new ArrayList<HashMap<String, Object>>();
//					shopList = (List<HashMap<String, Object>>) mapObj.get("shop_list");
//					for(int i=0;i<shopList.size();i++){
////						if(shopList.get(i).get("p_code").equals(code)){
////							count++;
//							
//							if(i==0){
//								count=1;
//								if(ZeroShopFragment.width>720){
//									SetImageLoader.initImageLoader(context, holder.img_zero_shop_product1,
//											shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!382");
//								}else{
//									SetImageLoader.initImageLoader(context, holder.img_zero_shop_product1,
//											shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!280");
//								}
//								
//								holder.tv_zero_product_name1.setText(shopList.get(i).get("shop_name")+"");
//								name = shopList.get(i).get("shop_name")+"";
//								
//								
//								if(pos.equals(1+"")||pos.equals(2+"")||pos.equals(3+"")){
//									holder.tv_zero_product_price1.setVisibility(View.INVISIBLE);
//									
//								}else{
//									holder.tv_zero_product_price1.setText("￥0.00");
//								}
//								
//								
//								
//								
//								
//								
////								holder.tv_zero_product_price1.setText("￥"+shopList.get(i).get("shop_se_price"));
//								
//								if (!TextUtils.isEmpty(shopList.get(i).get("shop_price")+"")) {
//									ToastUtil.addStrikeSpan( holder.tv_zero_price_outdate1
//											,  "￥ "+shopList.get(i).get("shop_price"));
//								}
////								holder.tv_zero_price_outdate1.setText("￥"+shopList.get(i).get("shop_price"));
////								holder.tv_zero_price_outdate1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//								holder.tv_purchase_num1.setText(shopList.get(i).get("virtual_sales")+"人在抢");
//								
//							}else if(i==1){
//								count=2;
//								if((shopList.get(i).get("shop_name")+"").equals(name)){
//									count--;
//									continue;
//								}
//								if(ZeroShopFragment.width>720){
//									SetImageLoader.initImageLoader(context, holder.img_zero_shop_product2,
//											shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!382");
//								}else{
//									SetImageLoader.initImageLoader(context, holder.img_zero_shop_product2,
//											shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!280");
//								}
//								if(pos.equals(1+"")||pos.equals(2+"")||pos.equals(3+"")){
//									holder.tv_zero_product_price2.setVisibility(View.INVISIBLE);
//									
//									
//									
//								} 
//								if(pos.equals(0+"")){
//									holder.tv_zero_product_price2.setText("￥0.00");
//								}
//								
//								
//								
//								holder.tv_zero_product_name2.setText(shopList.get(i).get("shop_name")+"");
//								if (!TextUtils.isEmpty(shopList.get(i).get("shop_price")+"")) {
//									ToastUtil.addStrikeSpan( holder.tv_zero_price_outdate2
//											,  "￥ "+shopList.get(i).get("shop_price"));
//								}
////								holder.tv_zero_price_outdate2.setText("￥"+shopList.get(i).get("shop_price"));
////								holder.tv_zero_price_outdate2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//								holder.tv_purchase_num2.setText(shopList.get(i).get("virtual_sales")+"人在抢");
//								
//								
////								holder.tv_zero_product_price2.setText("￥"+shopList.get(i).get("shop_se_price"));
//							}else if(i==2){
//								count=3;
//								if(ZeroShopFragment.width>720){
//									SetImageLoader.initImageLoader(context, holder.img_zero_shop_product3,
//											shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!382");
//								}else{
//									SetImageLoader.initImageLoader(context, holder.img_zero_shop_product3,
//											shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!280");
//								}
//								
//								holder.tv_zero_product_name3.setText(shopList.get(i).get("shop_name")+"");
//								if (!TextUtils.isEmpty(shopList.get(i).get("shop_price")+"")) {
//									ToastUtil.addStrikeSpan( holder.tv_zero_price_outdate3
//											,  "￥ "+shopList.get(i).get("shop_price"));
//								}
////								holder.tv_zero_price_outdate3.setText("￥"+shopList.get(i).get("shop_price"));
////								holder.tv_zero_price_outdate3.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//								
//								if(pos.equals(1+"")||pos.equals(2+"")||pos.equals(3+"")){
//									holder.tv_zero_product_price3.setVisibility(View.INVISIBLE);
//									
//								}
//								if(pos.equals(0+"")){
//									holder.tv_zero_product_price3.setText("￥0.00");
//								}
//								
//								
////								holder.tv_zero_product_price3.setText("￥"+shopList.get(i).get("shop_se_price"));
//								holder.tv_purchase_num3.setText(shopList.get(i).get("virtual_sales")+"人在抢");
//							}
//							else if(i>2){
//								addView(shopList.get(i).get("shop_name")+"", (String) shopList.get(i).get("four_pic"), shopList.get(i).get("shop_se_price")+"", shopList.get(i).get("shop_price")+"", shopList.get(i).get("virtual_sales")+"", holder.container_item, inflater);
//							}
//						}
//					
//					if(count==2){
//						holder.rl_zero_shop_product1.setVisibility(View.VISIBLE);
//						holder.rl_zero_shop_product2.setVisibility(View.VISIBLE);
//						holder.rl_zero_shop_product3.setVisibility(View.GONE);	
//						holder.tv_free_postage.setVisibility(View.VISIBLE);
//					}else if(count==1){
//						holder.rl_zero_shop_product1.setVisibility(View.VISIBLE);
//						holder.rl_zero_shop_product2.setVisibility(View.GONE);
//						holder.rl_zero_shop_product3.setVisibility(View.GONE);
//						holder.tv_free_postage.setVisibility(View.GONE);
//					}else if(count==3){
//						holder.rl_zero_shop_product1.setVisibility(View.VISIBLE);
//						holder.rl_zero_shop_product2.setVisibility(View.VISIBLE);
//						holder.rl_zero_shop_product3.setVisibility(View.VISIBLE);
//						holder.tv_free_postage.setVisibility(View.VISIBLE);
//					}else if(count==0){
//						holder.rl_zero_shop_product1.setVisibility(View.GONE);
//						holder.rl_zero_shop_product2.setVisibility(View.GONE);
//						holder.rl_zero_shop_product3.setVisibility(View.GONE);
//						holder.rl_zero_shop_top.setVisibility(View.GONE);
//						holder.v_line.setVisibility(View.GONE);
//						holder.v_huisequyu.setVisibility(View.GONE);
//						holder.container_item.setVisibility(View.GONE);
////						return view;
//					}
//					holder.tv_zero_shop_postage_tou.setText(str[count]);
//					String postage = new DecimalFormat("#").format(Double
//							.parseDouble(mapObj.get("postage")
//									.toString()));
//					holder.tv_zero_shop_postage.setText(Html.fromHtml(String.format(getString(R.string.tv_free_postage_price),postage)));
//					
//					
//					
//					
////					
////					holder.tv_package_kind_tou.setText(str[count]);
////					String postage2 = new DecimalFormat("#").format(Double
////							.parseDouble(mapObj.get("postage")
////									.toString()));
//					
//					
////					pos = getArguments().getString("position");
//					
//					
//					if (pos.equals(1+"")){
//						
//						holder.tv_package_kind_tou.setTextSize(16);
//						holder.tv_package_kind_tou.setText("9元");
//					}
//					if (pos.equals(2+"")){
//						holder.tv_package_kind_tou.setTextSize(16);
//						holder.tv_package_kind_tou.setText("19元");
//					}
//					if (pos.equals(3+"")){
//						holder.tv_package_kind_tou.setTextSize(16);
//						holder.tv_package_kind_tou.setText("29元");
//					}
//				
//					
////					holder.tv_package_kind_tou.setText(Html.fromHtml(String.format(getString(R.string.tv_package_kind_price),postage2)));
//					
//					
////				}
//				
//				
//				
//			
//			view.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
////					mContext.getSharedPreferences("YSSJ_yf",
////							mContext.MODE_PRIVATE).edit()
////							.putBoolean("isGoDetail", true).commit();
////					addScanDataTo((String) dataList.get(position).get("shop_code"));
//					Intent intent = new Intent(mContext, ShopDetailsActivity.class);
////					if(!"3".equals(pos)){
//					intent.putExtra("isMeal", true);
////					}else{
////						intent.putExtra("isMeal", false);	
////					}
//					intent.putExtra("code",pList.get(position).get("code")+"");
//					intent.putExtra("pos", pos);
//					// context.startActivity(intent);
////					intent.putExtra("shopCarFragment", "shopCarFragment");
//					FragmentActivity activity = (FragmentActivity) mContext;
//					activity.startActivity(intent);
//					activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);;
//				}
//			});
//			
//			return view;
//		}
//
//	}
//	
//	/*
//	 * 把浏览过的数据添加进数据库
//	 */
////	private void addScanDataTo(final String shop_code) {
////		new SAsyncTask<Void, Void, ReturnInfo>(getActivity()) {
////
////			@Override
////			protected ReturnInfo doInBackground(FragmentActivity context,
////					Void... params) throws Exception {
////				return ComModel.addMySteps(context, shop_code);
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////			
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					ReturnInfo result, Exception e) {
////				super.onPostExecute(context, result, e);
////			}
////
////		}.execute();
////	}
//	private static class ViewHolder {
//		public TextView tv_zero_product_name;
//		private TextView tv_zero_shop_postage,tv_package_kind,tv_allowance_count,tv_free_postage,
//		tv_zero_price_outdate1,tv_zero_price_outdate2,tv_zero_price_outdate3,tv_29_allowance_count,tv_zero_shop_words;
//		private ProgressBar pb_allowance_count_zong,pb_allowance_count;
//		private View v_line,v_huisequyu;
//		private RelativeLayout rl_zero_shop_top,rl_pb_product,rl_zero_shop_product1,rl_zero_shop_product2,rl_zero_shop_product3;
//
//		//商品列表
//		private ImageView img_zero_shop_product1,img_zero_shop_product2,img_zero_shop_product3;
//		private TextView tv_zero_product_name1,tv_zero_product_name2,tv_zero_product_name3
//		,tv_zero_product_price1,tv_zero_product_price2,tv_zero_product_price3
//		,tv_purchase_num1,tv_purchase_num2,tv_purchase_num3,tv_zero_shop_postage_tou,tv_package_kind_tou,tv_package_kind_tou2;
//		private LinearLayout container_item;
//	
//	}
//	
//	private double addView(String name,String url,String price,String price_outdate,String people, LinearLayout container,
//			LayoutInflater inflater) {
////		container.removeAllViews();
//		double itemAccount = 0;
//		
//			View view = inflater.inflate(R.layout.listview_zero_shop_son, null);
//			ImageView img_product = (ImageView) view
//					.findViewById(R.id.img_zero_shop_product_son);
//			TextView tv_product_name = (TextView) view
//					.findViewById(R.id.tv_zero_product_name);
//			TextView tv_price_outdate = (TextView) view
//					.findViewById(R.id.tv_zero_price_outdate);
//			TextView tv_price = (TextView) view.findViewById(R.id.tv_zero_product_price);
//			TextView tv_purchase_num = (TextView) view
//					.findViewById(R.id.tv_purchase_num);
////			TextView tvStatus = (TextView) view.findViewById(R.id.tv_status);
//			// TextView tvOriginalPrice = (TextView) view
//			// .findViewById(R.id.tv_original_price);
//
////			String pic = orderShop.getShop_pic();
////			img_product.setTag(pic);
//			if (!TextUtils.isEmpty(url)) {
//				if(ZeroShopFragment.width>720){
//					SetImageLoader.initImageLoader(mContext, img_product, url,"!382");
//				}else{
//					SetImageLoader.initImageLoader(mContext, img_product, url,"!280");
//				}
//				
//			}
//			if (!TextUtils.isEmpty(name)) {
//				tv_product_name.setText(name);
//			}
//			tv_price.setText("￥" +price );
//			if (!TextUtils.isEmpty(price_outdate+"")) {
//				ToastUtil.addStrikeSpan(tv_price_outdate
//						,  "￥ "+price_outdate);
//			}
//			tv_purchase_num.setText(people+"人在抢");
//			
//			container.addView(view);
//		return itemAccount;
//	}
//	
//}
