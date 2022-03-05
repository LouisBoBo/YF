package com.yssj.ui.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yssj.activity.R;
import com.yssj.activity.R.drawable;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.XListView;
import com.yssj.custom.view.XListViewMealSingle;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainFragment;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.fragment.orderinfo.OrderListAdapter.notifyDatas;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;

import android.R.layout;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ZeroShopItemFragmentSingle extends Fragment {

	private static Context mContext;


//	private List<HashMap<String, Object>> shopList;
	private List<HashMap<String, Object>> pList;

	private List<HashMap<String, Object>> supermeal;  //超值
	private List<HashMap<String, Object>> supersinger;

	private String pos;


	private XListViewMealSingle mList;


	private DateAdapter mAdapter;
	private String[] str = { "", "", "二件", "三件", "四件", "五件", "六件", "七件", "八件", "九件" };

	private int index=1;
	public onZeroShopRefreshListener zeroShopRefresh;


	private String choice;


	private String single;
	public interface onZeroShopRefreshListener{
		void onZeroShopRefresh();
	}
	public  void setOnZeroRefreshListener(Fragment f){
		this.zeroShopRefresh = (onZeroShopRefreshListener) f;
	}


	public XListViewMealSingle getmList() {
		return mList;
	}

	public static ZeroShopItemFragmentSingle newInstances(int position,  Context context) {
		ZeroShopItemFragmentSingle instance = new ZeroShopItemFragmentSingle();
		Bundle args = new Bundle();
		args.putString("position", position+"");
//		args.putString("title", title);
//		args.putString("id", id);
		mContext = context;
		instance.setArguments(args);

		return instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		choice = SharedPreferencesUtil.getStringData(getActivity(), "choice","1" );

//		MainFragment.rl_rootButton.setBackgroundColor(Color.WHITE);

	//	single = SharedPreferencesUtil.getStringData(mContext, "choice", "0");

		View v = inflater
				.inflate(R.layout.item_fragment_view_meal_single, container, false);
//		pos = getArguments().getString("position");
//		title=getArguments().getString("title");
//		id=getArguments().getString("id");
		mList=(XListViewMealSingle) v.findViewById(R.id.dataList);
//		mList.setPullRefreshEnable(false);
		mList.setPullLoadEnable(true);
		mList.setXListViewListener(new XListViewMealSingle.IXListViewListener() {


			@Override
			public void onRefresh() {

			}

			@Override
			public void onLoadMore() {
				index++;
				initData(String.valueOf(index));
			}
		});
		pList   = new ArrayList<HashMap<String, Object>>();





		mAdapter = new DateAdapter(getActivity());
		mList.setAdapter(mAdapter);
		return v;
	}
//	private void setOnClickListener() {
//
//		mList.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//					break;
//				case MotionEvent.ACTION_MOVE:
//					mGestureDetector.onTouchEvent(event);
//					break;
//				case MotionEvent.ACTION_UP:
//					break;
//				}
//				return false;
//			}
//		});
//	}
//
//
//	private GestureDetector mGestureDetector = new GestureDetector(getActivity(),
//			new GestureDetector.OnGestureListener() {
//
//				@Override
//				public boolean onSingleTapUp(MotionEvent e) {
//					return false;
//				}
//
//				@Override
//				public void onShowPress(MotionEvent e) {
//
//				}
//
//				@Override
//				public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//
//
//					if(Math.abs(distanceY) > 15)return false;
//					if(distanceY > 0){
//						if(MainFragment.rl_rootButton.getVisibility() == View.GONE)return false;
//						MainFragment.rl_rootButton.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_out));
//						MainFragment.rl_rootButton.setVisibility(View.GONE);
//					}else if(distanceY < 0){
//						if(MainFragment.rl_rootButton.getVisibility() == View.VISIBLE)return false;
//						MainFragment.rl_rootButton.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_in));
//						MainFragment.rl_rootButton.setVisibility(View.VISIBLE);
//					}
//					return false;
//				}
//
//				@Override
//				public void onLongPress(MotionEvent e) {
//
//				}
//
//				@Override
//				public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//					return false;
//				}
//
//				@Override
//				public boolean onDown(MotionEvent e) {
//					return false;
//				}
//			});

	@Override
	public void onActivityCreated( Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		index=1;

		initData(String.valueOf(index));
	}

//	@Override
//	public void setUserVisibleHint(boolean isVisibleToUser) {
//		// TODO Auto-generated method stub
//		super.setUserVisibleHint(isVisibleToUser);
//		if(isVisibleToUser&&isVisible()){
//			if(dataList.isEmpty()){
//
//			}
//		}
//	}

	public void refresh(){
		index=1;
		initData("1");

		mList.setSelection(0);
		mList.setSelected(true);

	}


	public void setSelecttion(){
		if(mAdapter.getCount()>0&&mList.getFirstVisiblePosition()!=0){
			mList.setSelection(0);
		}
	}

	private void initData(final String index) {
		new SAsyncTask<String, Void, Map<String, List<HashMap<String, Object>>>>(
				getActivity(), 0) {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				LoadingDialog.show(getActivity());
			}
			@Override
			protected void onPostExecute(FragmentActivity context,
					Map<String, List<HashMap<String, Object>>> result,Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result);
				if(e!=null){
					mList.stopLoadMore();
					return ;
				}
				if(result!=null){
					if(ZeroShopItemFragmentSingle.this.index==1){
						pList.clear();
//						shopList.clear();
					}

					if(result.size() == 0){
						ToastUtil.showShortText(context, "已没有更多商品了");
					}

//				shopList.addAll(result.get("shopList"));
				pList.addAll(result.get("pList"));
				}else{
					if(ZeroShopItemFragmentSingle.this.index==1){
						pList.clear();
//						shopList.clear();
					}else{
						ToastUtil.showShortText(context, "已没有更多商品了");
					}

				}
				mAdapter.notifyDataSetChanged();
				mList.stopLoadMore();
//				zeroShopRefresh.onZeroShopRefresh();
			}

					@Override
					protected boolean isHandleException() {
						// TODO Auto-generated method stub
						return true;
					}

			@Override
			protected Map<String, List<HashMap<String, Object>>> doInBackground(
					FragmentActivity context, String... params)
					throws Exception {


//				if (choice.equals("1")) {
//
//					return ComModel2.getZeroShopProductList_1(context, index);
//				}else {
//					System.out.println("321");
					return ComModel2.getZeroShopProductList_0(context, index);
//				}
		//		return ComModel2.getZeroShopProductList_0(context, index,pos);
			}

		}.execute();
	}


	private class DateAdapter extends BaseAdapter {

		private Context context;

		private int picHeight;

		private String number_single;

		private String str_single;
		private String str_meal;

		private int num_single;

		private String jj;

		private double addStrikeSpan_num_1;

		private Double addStrikeSpan_num_2;
		private Double addStrikeSpan_num_3;
		private Double addStrikeSpan_num_single;
		private int num_meal;

		private String kc_single;





		public DateAdapter(Context context) {
			super();
			this.context = context;
			picHeight=ZeroShopFragment.width/2*900/600;
		}


		@Override
		public int getCount() {
			int count = 0;
			count = pList.size();
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

		@SuppressWarnings("unchecked")
		@Override
		public View getView(final int position, View view, ViewGroup arg2) {
			final ViewHolder_single holder_single;
			String p_status;

				if (view == null ) {
					view = LayoutInflater.from(getActivity()).inflate(
							R.layout.zero_shop_item_fragment_adapter_singer, null);
					holder_single = new ViewHolder_single();

					holder_single.zero_shop_item_soldall1=(RelativeLayout)view.findViewById(R.id.zero_shop_item_soldall1);

					holder_single.container_item =(LinearLayout) view.findViewById(R.id.container_item);
				//	holder.rl_zero_shop_top = (RelativeLayout) view.findViewById(R.id.rl_zero_shop_top);
					holder_single.v_huisequyu      = view.findViewById(R.id.v_huisequyu);
					holder_single.tv_zero_price_outdate1 = (TextView) view.findViewById(R.id.tv_zero_price_outdate);
					holder_single.tv_allowance_count = (TextView) view.findViewById(R.id.tv_allowance_count);
					holder_single.tv_zero_shop_postage =(TextView) view.findViewById(R.id.tv_zero_shop_postage);
					holder_single.tv_zero_shop_postage_gone =(TextView) view.findViewById(R.id.tv_zero_shop_postage_gone);

					holder_single.pb_allowance_count_zong = (ProgressBar) view.findViewById(R.id.pb_allowance_count_zong);
					holder_single.pb_allowance_count_zong_gone = (ProgressBar) view.findViewById(R.id.pb_allowance_count_zong_gone);

					holder_single.tv_purchase_num1    = (TextView) view.findViewById(R.id.tv_purchase_num);

					holder_single.tv_zero_product_price_RMB = (TextView) view.findViewById(R.id.tv_zero_product_price_RMB);
					//商品图片
					holder_single.img_zero_shop_product1 = (ImageView) view.findViewById(R.id.img_zero_shop_product1);
				//	holder.img_zero_shop_product2 = (ImageView) view.findViewById(R.id.img_zero_shop_product2);
				//	holder.img_zero_shop_product3 = (ImageView) view.findViewById(R.id.img_zero_shop_product3);
					//商品名称
					holder_single.tv_zero_product_name1  = (TextView) view.findViewById(R.id.tv_zero_product_name);
				//	holder.tv_zero_product_name2  = (TextView) view.findViewById(R.id.tv_zero_product_name2);
				//	holder.tv_zero_product_name3  = (TextView) view.findViewById(R.id.tv_zero_product_name3);
					//商品价格
					holder_single.tv_zero_product_price1  = (TextView) view.findViewById(R.id.tv_zero_product_price);


					holder_single.bt_zero_shop=(Button) view.findViewById(R.id.bt_zero_shop);
					holder_single.bt_zero_shop_gone=(Button) view.findViewById(R.id.bt_zero_shop_gone);


					view.setTag(holder_single);
				} else {
					holder_single = (ViewHolder_single) view.getTag();
				}
				LayoutInflater inflater = LayoutInflater.from(getActivity());

				HashMap<String, Object> mapObj = pList.get(position);
				 String code = mapObj.get("code")+"";

				 p_status = mapObj.get("p_status").toString();

				 //点击单品按钮"立即抢购"跳转到详情页面
				 //还没抢完的按钮
				 holder_single.bt_zero_shop.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getActivity(), ShopDetailsActivity.class);
						intent.putExtra("isMeal", true);
						intent.putExtra("code",pList.get(position).get("code")+"");
//						intent.putExtra("pos", pos);
						intent.putExtra("number_sold", "have");
						FragmentActivity activity = (FragmentActivity) getActivity();
						activity.startActivity(intent);
						activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
					}
				});

				 //抢完了的按钮
				 holder_single.bt_zero_shop_gone.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(), ShopDetailsActivity.class);
						intent.putExtra("isMeal", true);
						intent.putExtra("code",pList.get(position).get("code")+"");
//						intent.putExtra("pos", pos);
						intent.putExtra("number_sold", "none");
						FragmentActivity activity = (FragmentActivity) getActivity();
						activity.startActivity(intent);
						activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

					}
				});

//						holder_single.pb_allowance_count_zong.setProgress(
//								(int) ((Double.parseDouble((String) mapObj.get("r_num"))/Double.parseDouble((mapObj.get("num")+"")))*100));

				 		String add_date_str =  (String)(mapObj.get("add_date"));
				 		if("null".equals(add_date_str)||TextUtils.isEmpty(add_date_str)){
				 			add_date_str = "0";
				 		}
						long add_date =  Long.valueOf(add_date_str);
						long recLen = add_date-System.currentTimeMillis()+48*60*60*1000;
						if(recLen>0){
							int hour = (int) (recLen / (3600*1000));
							int minute = (int) ((recLen - hour * 3600*1000) / (60*1000));
							holder_single.tv_allowance_count.setText("限时48小时，还剩"+hour+"时"+minute+"分");
							holder_single.pb_allowance_count_zong.setProgress(hour*100/48);
						}else{
							holder_single.tv_allowance_count.setText("限时48小时，还剩0时0分");
							holder_single.pb_allowance_count_zong.setProgress(0);
						}


						int count =0;
						String name = null ;
						List<HashMap<String, Object>> shopList = new ArrayList<HashMap<String, Object>>();
						shopList = (List<HashMap<String, Object>>) mapObj.get("shop_list");
						for(int i=0;i<shopList.size();i++){
//							kc_single = mapObj.get("num")+"";

							String r_num_single = mapObj.get("r_num")+"";
							num_single = Integer.valueOf(r_num_single).intValue();

//							holder_single.tv_allowance_count.setText("限量"+kc_single+"件,还剩"+mapObj.get("r_num")+"件");
								if(i==0){
									count=1;

									if(ZeroShopFragment.width>720){



//										SetImageLoader.initImageLoader(context, holder_single.img_zero_shop_product1,
//												shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!382");

                                        PicassoUtils.initImage(context,shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic")+"!382",holder_single.img_zero_shop_product1);


									}else{
//										SetImageLoader.initImageLoader(context, holder_single.img_zero_shop_product1,
//												shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!280");

                                        PicassoUtils.initImage(context,shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic")+"!382",holder_single.img_zero_shop_product1);

                                    }

									holder_single.tv_zero_product_name1.setText(shopList.get(i).get("shop_name")+"");


//									holder_single.tv_zero_product_price1.setText(shopList.get(i).get("shop_se_price")+""/*+".0"*/);
									String string = shopList.get(i).get("shop_se_price").toString();
									Double num1 = Double.valueOf(string);
									holder_single.tv_zero_product_price1.setText(new DecimalFormat("#0.0").format(num1)+""/*+".0"*/);

//									new DecimalFormat("#0.0").format(shop.getShop_se_price() - dikou_int)

									String addStrikeSpan_single=shopList.get(i).get("shop_price")+"";
									addStrikeSpan_num_single = Double.parseDouble(addStrikeSpan_single);

									DecimalFormat formater = new DecimalFormat("#0.0");
									 String format = formater.format(addStrikeSpan_num_single);

									if (!TextUtils.isEmpty(shopList.get(i).get("shop_price")+"")) {

										ToastUtil.addStrikeSpan( holder_single.tv_zero_price_outdate1
												,  "¥"+format);
									}

//									number_single = shopList.get(i).get("virtual_sales")+"";
//									str_single = "有"+shopList.get(i).get("virtual_sales")+"人在抢";

									number_single = mapObj.get("virtual_sales")+"";
									str_single = "有"+mapObj.get("virtual_sales")+"人在抢";

									SpannableStringBuilder style=new SpannableStringBuilder(str_single);
									 style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.zero_shop_choice)),1,number_single.length()+1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
									 holder_single.tv_purchase_num1.setTextColor(getResources().getColor(R.color.huise));
									 holder_single.tv_purchase_num1.setText(style);

								}

							}

						if(count!=0){
							if (recLen>0 || p_status.equals("0") || num_single>0) {
								//遮挡物
								holder_single.zero_shop_item_soldall1.setVisibility(View.INVISIBLE);

								holder_single.tv_zero_shop_postage.setVisibility(View.VISIBLE);
								holder_single.tv_zero_shop_postage.setTextColor(getResources().getColor(R.color.zero_shop_choice));
								holder_single.tv_zero_shop_postage_gone.setVisibility(View.GONE);

								holder_single.bt_zero_shop.setVisibility(View.VISIBLE);
								holder_single.bt_zero_shop_gone.setVisibility(View.INVISIBLE);

								holder_single.pb_allowance_count_zong.setVisibility(View.VISIBLE);
								holder_single.pb_allowance_count_zong_gone.setVisibility(View.GONE);

								holder_single.tv_zero_product_price1.setTextColor(getResources().getColor(R.color.zero_shop_choice));
								holder_single.tv_zero_product_price_RMB.setTextColor(getResources().getColor(R.color.zero_shop_choice));

								holder_single.tv_zero_product_name1.setTextColor(getResources().getColor(R.color.zero_shop_choice_other));



							}
							if(recLen<=0 || p_status.equals("1") || num_single==0){
//								holder_single.tv_allowance_count.setText("限量"+kc_single+"件,还剩0件");
								holder_single.tv_allowance_count.setText("限时48小时，还剩0时0分");

								//遮挡物
								holder_single.zero_shop_item_soldall1.setVisibility(View.VISIBLE);
								holder_single.tv_zero_product_name1.setTextColor(getResources().getColor(R.color.gray_white));
								holder_single.tv_zero_product_price1.setTextColor(getResources().getColor(R.color.gray_white));
								holder_single.tv_zero_product_price_RMB.setTextColor(getResources().getColor(R.color.gray_white));

								holder_single.tv_zero_shop_postage.setVisibility(View.GONE);

								holder_single.tv_zero_shop_postage_gone.setVisibility(View.VISIBLE);

								holder_single.bt_zero_shop.setVisibility(View.INVISIBLE);
								holder_single.bt_zero_shop_gone.setVisibility(View.VISIBLE);

								holder_single.pb_allowance_count_zong.setVisibility(View.GONE);
								holder_single.pb_allowance_count_zong_gone.setVisibility(View.VISIBLE);//TODO




								str_single="有0人在抢";
								SpannableStringBuilder style=new SpannableStringBuilder(str_single);
								 style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gray_white)),1,number_single.length()+1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
								 holder_single.tv_purchase_num1.setTextColor(getResources().getColor(R.color.gray_white));
								 holder_single.tv_purchase_num1.setText(style);


							}

						}

						String postage = new DecimalFormat("#").format(Double
								.parseDouble(mapObj.get("postage")
										.toString()));
						holder_single.tv_zero_shop_postage.setText(Html.fromHtml(String.format(getString(R.string.tv_free_postage_price),postage)));
						holder_single.tv_zero_shop_postage_gone.setText(Html.fromHtml(String.format(getString(R.string.tv_free_postage_price),postage)));

						String postage_single=mapObj.get("postage")+"";

						 double postage_single_int = Double.valueOf(postage_single).doubleValue();
						if (postage_single_int!=0) {
							holder_single.tv_zero_shop_postage.setText("邮费"+postage_single+".0");
							holder_single.tv_zero_shop_postage_gone.setText("邮费"+postage_single+".0");
						}else {
							holder_single.tv_zero_shop_postage.setText("包邮");
							holder_single.tv_zero_shop_postage_gone.setText("包邮");
						}


						if (holder_single.zero_shop_item_soldall1.getVisibility() ==View.INVISIBLE || holder_single.zero_shop_item_soldall1.getVisibility() ==View.GONE) {
							view.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									Intent intent = new Intent(getActivity(), ShopDetailsActivity.class);
									intent.putExtra("isMeal", true);
									intent.putExtra("code",pList.get(position).get("code")+"");
//									intent.putExtra("pos", pos);
									intent.putExtra("number_sold", "have");
									FragmentActivity activity = (FragmentActivity) getActivity();
									activity.startActivity(intent);
									activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
								}
							});

						}else  {
							view.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									Intent intent = new Intent(getActivity(), ShopDetailsActivity.class);
									intent.putExtra("isMeal", true);
									intent.putExtra("code",pList.get(position).get("code")+"");
//									intent.putExtra("pos", pos);
									intent.putExtra("number_sold", "none");
									FragmentActivity activity = (FragmentActivity) getActivity();
									activity.startActivity(intent);
									activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

								}
							});
						}



			return view;
		}

	}

	/*
	 * 把浏览过的数据添加进数据库
	 */
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



	private static class ViewHolder_single{
		public TextView tv_zero_product_name;
		private TextView tv_zero_shop_postage,tv_package_kind,tv_allowance_count,tv_free_postage,tv_zero_product_price_RMB,
		tv_zero_price_outdate1,tv_zero_price_outdate2,tv_zero_price_outdate3,tv_29_allowance_count,tv_zero_shop_words,zero_shop_item_single,tv_zero_shop_item_meal;
		private ProgressBar pb_allowance_count_zong,pb_allowance_count,pb_allowance_count_zong_gone;
		private View v_line,v_huisequyu,zero_shop_item_soldall1,zero_shop_item_soldall2,zero_shop_item_soldall3;
		private RelativeLayout rl_zero_shop_top,rl_pb_product,rl_zero_shop_product1,rl_zero_shop_product2,rl_zero_shop_product3;

		//商品列表
		private ImageView img_zero_shop_product1,img_zero_shop_product2,img_zero_shop_product3,img_zero_shop_add1,img_zero_shop_add2;
		private TextView tv_zero_product_name1,tv_zero_product_name2,tv_zero_product_name3,tv_zero_shop_postage_gone
		,tv_zero_product_price1,tv_zero_product_price2,tv_zero_product_price3
		,tv_purchase_num1,tv_purchase_num2,tv_purchase_num3,tv_zero_shop_postage_tou,tv_package_kind_tou,tv_package_kind_tou2;
		private LinearLayout container_item,zero_shop_choice,zero_shop_item_meal;
		private Button bt_zero_shop,bt_zero_shop_gone;

	}

	private double addView(String name,String url,String price,String price_outdate,String people, LinearLayout container,
			LayoutInflater inflater) {
//		container.removeAllViews();
		double itemAccount = 0;

			View view = inflater.inflate(R.layout.listview_zero_shop_son, null);
			ImageView img_product = (ImageView) view
					.findViewById(R.id.img_zero_shop_product_son);
			TextView tv_product_name = (TextView) view
					.findViewById(R.id.tv_zero_product_name);
			TextView tv_price_outdate = (TextView) view
					.findViewById(R.id.tv_zero_price_outdate);
			TextView tv_price = (TextView) view.findViewById(R.id.tv_zero_product_price);
			TextView tv_purchase_num = (TextView) view
					.findViewById(R.id.tv_purchase_num);
			if (!TextUtils.isEmpty(url)) {

			    PicassoUtils.initImage(getActivity(),url,img_product);
//				if(ZeroShopFragment.width>720){
//					SetImageLoader.initImageLoader(getActivity(), img_product, url,"!382");
//				}else{
//					SetImageLoader.initImageLoader(getActivity(), img_product, url,"!280");
//				}

			}
			if (!TextUtils.isEmpty(name)) {
				tv_product_name.setText(name);
			}
			tv_price.setText("¥" +price );
			if (!TextUtils.isEmpty(price_outdate+"")) {
				ToastUtil.addStrikeSpan(tv_price_outdate
						,  "¥ "+price_outdate);
			}
			tv_purchase_num.setText(people+"人在抢");

			container.addView(view);
		return itemAccount;
	}

}
