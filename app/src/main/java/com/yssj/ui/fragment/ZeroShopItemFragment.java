package com.yssj.ui.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yssj.activity.R;
import com.yssj.activity.R.drawable;
import com.yssj.app.SAsyncTask;
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

public class ZeroShopItemFragment extends Fragment {

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

	public static ZeroShopItemFragment newInstances(int position,  Context context) {
		ZeroShopItemFragment instance = new ZeroShopItemFragment();
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
		choice = SharedPreferencesUtil.getStringData(mContext, "choice","1" );

//		MainFragment.rl_rootButton.setBackgroundColor(Color.WHITE);

	//	single = SharedPreferencesUtil.getStringData(mContext, "choice", "0");

		View v = inflater
				.inflate(R.layout.item_fragment_view_meal_single, container, false);
		pos = getArguments().getString("position");
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
//		shopList= new ArrayList<HashMap<String, Object>>();
		pList   = new ArrayList<HashMap<String, Object>>();

		/*supermeal   = new ArrayList<HashMap<String, Object>>();  //超值套餐集合
		supersinger   = new ArrayList<HashMap<String, Object>>();//超值单品集合
*/

	//	System.out.println("!!!!"+pList.size());



		mAdapter = new DateAdapter(mContext);
		mList.setAdapter(mAdapter);
//		setOnClickListener();
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
			protected void onPostExecute(FragmentActivity context,
					Map<String, List<HashMap<String, Object>>> result,Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result);
				if(e!=null){
					mList.stopLoadMore();
					return ;
				}
				if(result!=null){
					if(ZeroShopItemFragment.this.index==1){
						pList.clear();
//						shopList.clear();
					}

					if(result.size() == 0){
						ToastUtil.showShortText(context, "已没有更多商品了");
					}

//				shopList.addAll(result.get("shopList"));
				pList.addAll(result.get("pList"));
				}else{
					if(ZeroShopItemFragment.this.index==1){
						pList.clear();
//						shopList.clear();
					}else{
						ToastUtil.showShortText(context, "已没有更多商品了");
					}

				}
				mAdapter.notifyDataSetChanged();
				mList.stopLoadMore();
				zeroShopRefresh.onZeroShopRefresh();
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


				if (choice.equals("1")) {

					return ComModel2.getZeroShopProductList_1(context, index/*,pos*/);
				}else {
//					System.out.println("321");
					return ComModel2.getZeroShopProductList_0(context, index/*,pos*/);
				}
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
			final ViewHolder holder;
			final ViewHolder_single holder_single;
//			 HashMap<String, Object> mapObj = pList.get(position);
//			 String code = mapObj.get("code")+"";
//			String choice=mapObj.get("name")+"";
//
//			for (int i = 0; i < pList.size(); i++) { //拿出所有超值套餐
//				HashMap<String, Object> map = pList.get(i);
//				String choicename=map.get("name")+"";
//				if (choicename.equals("超值套餐")) {
//					supermeal.add(mapObj);
//					System.out.println("***"+choicename+i+"套餐");
//				}
//				if (choicename.equals("超值单品")) {		//拿出所有超值单品
//					supersinger.add(mapObj);
//					System.out.println("******"+choicename+i+"单品");
//				}
//			}
//			System.out.println("!!!!"+pList.size());
//			System.out.println("!!!!"+supermeal.size());
//			System.out.println("!!!!"+supersinger.size());
			if (choice.equals("1")) {//套餐


			if (view == null ) {
				view = LayoutInflater.from(context).inflate(
						R.layout.zero_shop_item_fragment_adapter_meal, null);
//				System.out.println("***///***"+choice);
				holder = new ViewHolder();

				holder.bt_zero_shop=(Button)view.findViewById(R.id.bt_zero_shop);
				holder.bt_zero_shop_gone=(Button)view.findViewById(R.id.bt_zero_shop_gone);


				holder.tv_meal_name=(TextView)view.findViewById(R.id.tv_meal_name);
				//三个覆盖物
				 holder.zero_shop_item_soldall1=(RelativeLayout)view.findViewById(R.id.zero_shop_item_soldall1);
				 holder.zero_shop_item_soldall2=(RelativeLayout)view.findViewById(R.id.zero_shop_item_soldall2);
				 holder.zero_shop_item_soldall3=(RelativeLayout)view.findViewById(R.id.zero_shop_item_soldall3);

				 //2个加号
				holder.img_zero_shop_add1= (ImageView)view.findViewById(R.id.img_zero_shop_add1);
				holder.img_zero_shop_add2= (ImageView)view.findViewById(R.id.img_zero_shop_add2);

	/*	这个拿到zero_shop_fragment		//选择套餐，单品
				holder.zero_shop_item_meal=(LinearLayout)view.findViewById(R.id.zero_shop_item_meal);
				holder.zero_shop_item_single=(TextView)view.findViewById(R.id.zero_shop_item_single);
				holder.tv_zero_shop_item_meal=(TextView)view.findViewById(R.id.tv_zero_shop_item_meal);*/

	//			 holder.zero_shop_choice =(LinearLayout)view.findViewById(R.id.zero_shop_choice);

			//	holder.tv_zero_product_name = (TextView) view.findViewById(R.id.tv_zero_product_name);

				holder.container_item =(LinearLayout) view.findViewById(R.id.container_item);
				holder.rl_zero_shop_top = (RelativeLayout) view.findViewById(R.id.rl_zero_shop_top);
	//33			holder.v_line           = view.findViewById(R.id.v_line);
				holder.v_huisequyu      = view.findViewById(R.id.v_huisequyu);
	//33			holder.rl_pb_product = (RelativeLayout) view.findViewById(R.id.rl_pb_product);
	//33			holder.pb_allowance_count =(ProgressBar) view.findViewById(R.id.pb_allowance_count);
	//33			holder.rl_zero_shop_product1 = (RelativeLayout) view.findViewById(R.id.rl_zero_shop_product1);
	//33			holder.rl_zero_shop_product2 = (RelativeLayout) view.findViewById(R.id.rl_zero_shop_product2);
	//33			holder.rl_zero_shop_product3 = (RelativeLayout) view.findViewById(R.id.rl_zero_shop_product3);
	//33			holder.tv_free_postage   = (TextView) view.findViewById(R.id.tv_free_postage);
				holder.tv_zero_price_outdate1 = (TextView) view.findViewById(R.id.tv_zero_price_outdate);
	//33			holder.tv_zero_price_outdate2 = (TextView) view.findViewById(R.id.tv_zero_price_outdate2);
	//33			holder.tv_zero_price_outdate3 = (TextView) view.findViewById(R.id.tv_zero_price_outdate3);

	//33			holder.tv_package_kind = (TextView) view.findViewById(R.id.tv_package_kind);
				holder.tv_allowance_count = (TextView) view.findViewById(R.id.tv_allowance_count);

				holder.tv_zero_shop_postage =(TextView) view.findViewById(R.id.tv_zero_shop_postage);
				holder.tv_zero_shop_postage_gone =(TextView) view.findViewById(R.id.tv_zero_shop_postage_gone);

				holder.pb_allowance_count_zong = (ProgressBar) view.findViewById(R.id.pb_allowance_count_zong);
				holder.pb_allowance_count_zong_gone = (ProgressBar) view.findViewById(R.id.pb_allowance_count_zong_gone);

				holder.tv_purchase_num1    = (TextView) view.findViewById(R.id.tv_purchase_num);
	//33			holder.tv_purchase_num2    = (TextView) view.findViewById(R.id.tv_purchase_num2);
	//33			holder.tv_purchase_num3    = (TextView) view.findViewById(R.id.tv_purchase_num3);


				//商品图片
				holder.img_zero_shop_product1 = (ImageView) view.findViewById(R.id.img_zero_shop_product1);
				holder.img_zero_shop_product2 = (ImageView) view.findViewById(R.id.img_zero_shop_product2);
				holder.img_zero_shop_product3 = (ImageView) view.findViewById(R.id.img_zero_shop_product3);

				holder.rl_zero_product_name  = (RelativeLayout) view.findViewById(R.id.rl_zero_product_name);
				holder.rl_zero_product_name2  = (RelativeLayout) view.findViewById(R.id.rl_zero_product_name2);
				holder.rl_zero_product_name3  = (RelativeLayout) view.findViewById(R.id.rl_zero_product_name3);

				//商品名称
				holder.tv_zero_product_name1  = (TextView) view.findViewById(R.id.tv_zero_product_name);
				holder.tv_zero_product_name2  = (TextView) view.findViewById(R.id.tv_zero_product_name2);
				holder.tv_zero_product_name3  = (TextView) view.findViewById(R.id.tv_zero_product_name3);
				//商品价格
				holder.tv_zero_product_price1  = (TextView) view.findViewById(R.id.tv_zero_product_price);
		//33		holder.tv_zero_product_price2  = (TextView) view.findViewById(R.id.tv_zero_product_price2);
		//33		holder.tv_zero_product_price3  = (TextView) view.findViewById(R.id.tv_zero_product_price3);

		//33		holder.tv_29_allowance_count   = (TextView) view.findViewById(R.id.tv_29_allowance_count);

		//33		holder.tv_zero_shop_postage_tou = (TextView) view.findViewById(R.id.tv_zero_shop_postage_tou);
		//33		holder.tv_zero_shop_words      = (TextView) view.findViewById(R.id.tv_zero_shop_words);

		//33		holder.tv_package_kind_tou = (TextView) view.findViewById(R.id.tv_package_kind_tou);
				holder.tv_zero_product_price_RMB = (TextView) view.findViewById(R.id.tv_zero_product_price_RMB);


				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
		/*	holder.zero_shop_item_single.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					holder.zero_shop_item_single.setTextColor(Color.parseColor("red"));
					holder.tv_zero_shop_item_meal.setTextColor(Color.parseColor("grey"));
				}
			});

			holder.zero_shop_item_meal.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					holder.zero_shop_item_single.setTextColor(Color.parseColor("grey"));
					holder.tv_zero_shop_item_meal.setTextColor(Color.parseColor("red"));

				}
			});*/

			//点击按钮"立即抢购"跳转到购物详情页面
			//没抢完
			holder.bt_zero_shop.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(mContext, ShopDetailsActivity.class);
					intent.putExtra("isMeal", true);
					intent.putExtra("code",pList.get(position).get("code")+"");
					intent.putExtra("pos", pos);
					intent.putExtra("number_sold", "have");
					FragmentActivity activity = (FragmentActivity) mContext;
					activity.startActivity(intent);
					activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);



				}
			});
			//抢完了
			holder.bt_zero_shop_gone.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(mContext, ShopDetailsActivity.class);
					intent.putExtra("isMeal", true);
					intent.putExtra("code",pList.get(position).get("code")+"");
					intent.putExtra("pos", pos);
					intent.putExtra("number_sold", "none");
					FragmentActivity activity = (FragmentActivity) mContext;
					activity.startActivity(intent);
					activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
				}
			});

			LayoutInflater inflater = LayoutInflater.from(context);
	//999		holder.container_item.removeAllViews();
			HashMap<String, Object> mapObj = pList.get(position);
			 String code = mapObj.get("code")+"";

	/*				if(position==0&&("0".equals(pos))){
//						holder.tv_zero_shop_words.setVisibility(View.VISIBLE);
//
//						holder.tv_zero_shop_words.setText("|  购买需抵扣100积分 首次购买免积分");

						holder.zero_shop_choice.setVisibility(View.VISIBLE);
					}else{
		//				holder.tv_zero_shop_words.setVisibility(View.GONE);
						holder.zero_shop_choice.setVisibility(View.GONE);
					}
					*/

	//33				holder.tv_free_postage.setText(mapObj.get("name")+"");
	//33				holder.rl_zero_shop_top.setVisibility(View.VISIBLE);
	//33				holder.v_line.setVisibility(View.VISIBLE);
	//33				holder.pb_allowance_count.setVisibility(View.GONE);
	//33				holder.rl_pb_product.setVisibility(View.GONE);
	//33				holder.tv_package_kind.setText(mapObj.get("name")+"");
		/*	66		holder.tv_allowance_count.setText("限量"+kc+"仅剩"+mapObj.get("r_num")+"套");88*/





					String r_num = mapObj.get("r_num")+"";
					num_meal = Integer.valueOf(r_num).intValue();


					holder.pb_allowance_count_zong.setProgress(
							(int) (((Double.parseDouble((String) mapObj.get("r_num")))/(Double.parseDouble((mapObj.get("num")+""))))*100));

					int count =0;
					String name = null ;
					List<HashMap<String, Object>> shopList = new ArrayList<HashMap<String, Object>>();
					shopList = (List<HashMap<String, Object>>) mapObj.get("shop_list");

					jj = mapObj.get("content")+"";
					if(jj!=null&&!jj.equals("")){
//					System.out.println("___eee___"+jj);
					}
					for(int i=0;i<shopList.size();i++){
//						if(shopList.get(i).get("p_code").equals(code)){
//							count++;
					/*66	number_single = shopList.get(i).get("virtual_sales")+"";*/


						String kc = mapObj.get("num")+"";//套餐总数
//						System.out.println("//__"+kc);


						holder.tv_allowance_count.setText("限量"+kc+"件 , 还剩"+mapObj.get("r_num")+"套");
							if(i==0){
								count=1;

								if(ZeroShopFragment.width>720){
									SetImageLoader.initImageLoader(context, holder.img_zero_shop_product1,
											shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!382");
								}else{
									SetImageLoader.initImageLoader(context, holder.img_zero_shop_product1,
											shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!280");
								}

								holder.tv_zero_product_name1.setText(shopList.get(i).get("shop_name")+"");

								holder.rl_zero_product_name.getBackground().setAlpha(178);


						/*		if(pos.equals(1+"")||pos.equals(2+"")||pos.equals(3+"")){
									holder.tv_zero_product_price1.setVisibility(View.INVISIBLE);

								}else{
									holder.tv_zero_product_price1.setText("¥0.00");
								}*/






								holder.tv_zero_product_price1.setText(shopList.get(i).get("shop_se_price")+".0");

								//取划掉价格平均数
								String  addStrikeSpan1 = shopList.get(i).get("shop_price")+"";
								addStrikeSpan_num_1 = Double.parseDouble(addStrikeSpan1);
								Double avergage1=addStrikeSpan_num_1;

//								System.out.println("............"+avergage1);
								if (!TextUtils.isEmpty(shopList.get(i).get("shop_price")+"")) {
									ToastUtil.addStrikeSpan( holder.tv_zero_price_outdate1
											,  "¥"+avergage1);
								}


								holder.tv_zero_price_outdate1.setTextColor(getResources().getColor(R.color.price));
//								holder.tv_zero_price_outdate1.setText("￥"+shopList.get(i).get("shop_price"));
//								holder.tv_zero_price_outdate1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
						/*	这里不适合了	holder.tv_purchase_num1.setText("有"+shopList.get(i).get("virtual_sales")+"人在抢");*/
							//	设置价格颜色
								String number=shopList.get(i).get("virtual_sales")+"";
								str_meal="有"+shopList.get(i).get("virtual_sales")+"人在抢";
								SpannableStringBuilder style_meal=new SpannableStringBuilder(str_meal);
								 style_meal.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.zero_shop_choice)),1,number.length()+1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
								 holder.tv_purchase_num1.setTextColor(getResources().getColor(R.color.huise));
								 holder.tv_purchase_num1.setText(style_meal);
							}else if(i==1){
								count=2;
								if((shopList.get(i).get("shop_name")+"").equals(name)){
									count--;
									continue;
								}
								if(ZeroShopFragment.width>720){
									SetImageLoader.initImageLoader(context, holder.img_zero_shop_product2,
											shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!382");
								}else{
									SetImageLoader.initImageLoader(context, holder.img_zero_shop_product2,
											shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!280");
								}
								if(pos.equals(1+"")||pos.equals(2+"")||pos.equals(3+"")){
			//33						holder.tv_zero_product_price2.setVisibility(View.INVISIBLE);



								}
								if(pos.equals(0+"")){
				//33					holder.tv_zero_product_price2.setText("￥0.00");
								}





					/*			Object object2 = shopList.get(i).get("shop_name");
								System.out.println("_____"+object2);*/
								holder.tv_zero_product_name2.setText(shopList.get(i).get("shop_name")+"");
								holder.rl_zero_product_name2.getBackground().setAlpha(178);

								String addStrikeSpan2=shopList.get(i).get("shop_price")+"";
								addStrikeSpan_num_2 = Double.parseDouble(addStrikeSpan2);
								Double avergage2=(addStrikeSpan_num_1+addStrikeSpan_num_2)/2;
								//保留小数点后一位 同时四舍五入
								DecimalFormat formater = new DecimalFormat("#0.0");
								 String format = formater.format(avergage2);
							if (!TextUtils.isEmpty(shopList.get(i).get("shop_price")+"")) {
									/*ToastUtil.addStrikeSpan( holder.tv_zero_price_outdate2*/
								ToastUtil.addStrikeSpan( holder.tv_zero_price_outdate1
											,  "¥"+/*shopList.get(i).get("shop_price")*/format);
								}
//								holder.tv_zero_price_outdate2.setText("￥"+shopList.get(i).get("shop_price"));
//								holder.tv_zero_price_outdate2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
				//33				holder.tv_purchase_num2.setText(shopList.get(i).get("virtual_sales")+"人在抢");


//								holder.tv_zero_product_price2.setText("￥"+shopList.get(i).get("shop_se_price"));
							}else if(i==2){
								count=3;
								if(ZeroShopFragment.width>720){
									SetImageLoader.initImageLoader(context, holder.img_zero_shop_product3,
											shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!382");
								}else{
									SetImageLoader.initImageLoader(context, holder.img_zero_shop_product3,
											shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!280");
								}

								holder.tv_zero_product_name3.setText(shopList.get(i).get("shop_name")+"");
								holder.rl_zero_product_name3.getBackground().setAlpha(178);

								String addStrikeSpan3=shopList.get(i).get("shop_price")+"";
								addStrikeSpan_num_3 = Double.parseDouble(addStrikeSpan3);
								Double avergage3=(addStrikeSpan_num_1+addStrikeSpan_num_2+addStrikeSpan_num_3)/3;

								DecimalFormat formater = new DecimalFormat("#0.0");
								 String format = formater.format(avergage3);
								if (!TextUtils.isEmpty(shopList.get(i).get("shop_price")+"")) {
									/*ToastUtil.addStrikeSpan( holder.tv_zero_price_outdate3*/
									ToastUtil.addStrikeSpan( holder.tv_zero_price_outdate1
											,  "¥"+/*shopList.get(i).get("shop_price")*/format);
								}
//								holder.tv_zero_price_outdate3.setText("￥"+shopList.get(i).get("shop_price"));
//								holder.tv_zero_price_outdate3.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

			/*	33				if(pos.equals(1+"")||pos.equals(2+"")||pos.equals(3+"")){
									holder.tv_zero_product_price3.setVisibility(View.INVISIBLE);

								}
								if(pos.equals(0+"")){
									holder.tv_zero_product_price3.setText("￥0.00");
								}*/


//								holder.tv_zero_product_price3.setText("￥"+shopList.get(i).get("shop_se_price"));
				//33				holder.tv_purchase_num3.setText(shopList.get(i).get("virtual_sales")+"人在抢");
							}
						/*	999else if(i>2){
								addView(shopList.get(i).get("shop_name")+"", (String) shopList.get(i).get("four_pic"), shopList.get(i).get("shop_se_price")+"", shopList.get(i).get("shop_price")+"", shopList.get(i).get("virtual_sales")+"", holder.container_item, inflater);
							}*/
						}

					if(count==2){
			//33			holder.rl_zero_shop_product1.setVisibility(View.VISIBLE);
			//33			holder.rl_zero_shop_product2.setVisibility(View.VISIBLE);
			//33			holder.rl_zero_shop_product3.setVisibility(View.GONE);
						holder.img_zero_shop_product1.setVisibility(View.VISIBLE);
						holder.img_zero_shop_product2.setVisibility(View.VISIBLE);
						holder.img_zero_shop_product3.setVisibility(View.INVISIBLE);

						holder.img_zero_shop_add1.setVisibility(View.VISIBLE);
						holder.img_zero_shop_add2.setVisibility(View.INVISIBLE);

						//商品名称
						holder.tv_zero_product_name1.setVisibility(View.VISIBLE);
						holder.tv_zero_product_name2.setVisibility(View.VISIBLE);
						holder.tv_zero_product_name3.setVisibility(View.INVISIBLE);


						holder.tv_meal_name.setTextColor(getResources().getColor(R.color.zero_shop_choice_other));

						if (num_meal>0) {
							//遮挡物
							holder.zero_shop_item_soldall1.setVisibility(View.INVISIBLE);
							holder.zero_shop_item_soldall2.setVisibility(View.INVISIBLE);
							holder.zero_shop_item_soldall3.setVisibility(View.INVISIBLE);

							holder.tv_zero_shop_postage.setVisibility(View.VISIBLE);
							holder.tv_zero_shop_postage_gone.setVisibility(View.GONE);

							holder.pb_allowance_count_zong.setVisibility(View.VISIBLE);
							holder.pb_allowance_count_zong_gone.setVisibility(View.INVISIBLE);

							holder.tv_meal_name.setText("超值2件套餐 : "+jj); //TODO
							holder.tv_meal_name.setTextColor(getResources().getColor(R.color.zero_shop_choice_other));

							holder.bt_zero_shop.setVisibility(View.VISIBLE);
							holder.bt_zero_shop_gone.setVisibility(View.GONE);

							holder.tv_zero_product_price_RMB.setTextColor(getResources().getColor(R.color.zero_shop_choice));
							 holder.tv_zero_product_price1.setTextColor(getResources().getColor(R.color.zero_shop_choice));
						}else {
							//遮挡物
							holder.zero_shop_item_soldall1.setVisibility(View.VISIBLE);
							holder.zero_shop_item_soldall2.setVisibility(View.VISIBLE);
							holder.zero_shop_item_soldall3.setVisibility(View.INVISIBLE);

							holder.pb_allowance_count_zong.setVisibility(View.INVISIBLE);
							holder.pb_allowance_count_zong_gone.setVisibility(View.VISIBLE);

							holder.bt_zero_shop.setVisibility(View.GONE);
							holder.bt_zero_shop_gone.setVisibility(View.VISIBLE);

							holder.tv_zero_shop_postage.setVisibility(View.GONE);
							holder.tv_zero_shop_postage_gone.setVisibility(View.VISIBLE);

							holder.tv_meal_name.setText("超值2件套餐 : "+jj);
							holder.tv_meal_name.setTextColor(getResources().getColor(R.color.gray_white));

							SpannableStringBuilder style=new SpannableStringBuilder(str_meal);
							 style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gray_white)),1,str_meal.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							 holder.tv_purchase_num1.setTextColor(getResources().getColor(R.color.gray_white));
							 holder.tv_purchase_num1.setText(style);
						/*	SpannableStringBuilder style=new SpannableStringBuilder(str_single);
							 style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.price)),1,number_single.length()+1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							 holder_single.tv_purchase_num1.setText(style);*/

							 holder.tv_zero_product_price_RMB.setTextColor(getResources().getColor(R.color.gray_white));
							 holder.tv_zero_product_price1.setTextColor(getResources().getColor(R.color.gray_white));
						}


			//33			holder.tv_free_postage.setVisibility(View.VISIBLE);
					}else if(count==1){
			//33			holder.rl_zero_shop_product1.setVisibility(View.VISIBLE);
			//33			holder.rl_zero_shop_product2.setVisibility(View.GONE);
			//33			holder.rl_zero_shop_product3.setVisibility(View.GONE);
			//33			holder.tv_free_postage.setVisibility(View.GONE);
					}else if(count==3){
			//33			holder.rl_zero_shop_product1.setVisibility(View.VISIBLE);
			//33			holder.rl_zero_shop_product2.setVisibility(View.VISIBLE);
			//33			holder.rl_zero_shop_product3.setVisibility(View.VISIBLE);

						holder.img_zero_shop_product1.setVisibility(View.VISIBLE);
						holder.img_zero_shop_product2.setVisibility(View.VISIBLE);
						holder.img_zero_shop_product3.setVisibility(View.VISIBLE);

						holder.img_zero_shop_add1.setVisibility(View.VISIBLE);
						holder.img_zero_shop_add2.setVisibility(View.VISIBLE);

						//商品名称
						holder.tv_zero_product_name1.setVisibility(View.VISIBLE);
						holder.tv_zero_product_name2.setVisibility(View.VISIBLE);
						holder.tv_zero_product_name3.setVisibility(View.VISIBLE);

						holder.tv_meal_name.setText("超值3件套餐"); //TODO
						holder.tv_meal_name.setTextColor(getResources().getColor(R.color.zero_shop_choice_other));
						if (num_meal>0) {
							holder.zero_shop_item_soldall1.setVisibility(View.INVISIBLE);
							holder.zero_shop_item_soldall2.setVisibility(View.INVISIBLE);
							holder.zero_shop_item_soldall3.setVisibility(View.INVISIBLE);

							holder.tv_zero_shop_postage.setVisibility(View.VISIBLE);
							holder.tv_zero_shop_postage_gone.setVisibility(View.GONE);

							holder.pb_allowance_count_zong.setVisibility(View.VISIBLE);
							holder.pb_allowance_count_zong_gone.setVisibility(View.INVISIBLE);

							holder.bt_zero_shop.setVisibility(View.VISIBLE);
							holder.bt_zero_shop_gone.setVisibility(View.INVISIBLE);


							holder.tv_meal_name.setText("超值3件套餐 : "+jj);
							holder.tv_meal_name.setTextColor(getResources().getColor(R.color.zero_shop_choice_other));

							holder.tv_zero_product_price_RMB.setTextColor(getResources().getColor(R.color.zero_shop_choice));
							 holder.tv_zero_product_price1.setTextColor(getResources().getColor(R.color.zero_shop_choice));
						}else {
							holder.zero_shop_item_soldall1.setVisibility(View.VISIBLE);
							holder.zero_shop_item_soldall2.setVisibility(View.VISIBLE);
							holder.zero_shop_item_soldall3.setVisibility(View.VISIBLE);


							holder.pb_allowance_count_zong.setVisibility(View.INVISIBLE);
							holder.pb_allowance_count_zong_gone.setVisibility(View.VISIBLE);

							holder.bt_zero_shop.setVisibility(View.GONE);
							holder.bt_zero_shop_gone.setVisibility(View.VISIBLE);

							holder.tv_zero_shop_postage.setVisibility(View.GONE);
							holder.tv_zero_shop_postage_gone.setVisibility(View.VISIBLE);

							holder.tv_meal_name.setText("超值3件套餐 : "+jj);
							holder.tv_meal_name.setTextColor(getResources().getColor(R.color.gray_white));

							SpannableStringBuilder style=new SpannableStringBuilder(str_meal);
							 style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gray_white)),1,str_meal.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							 holder.tv_purchase_num1.setTextColor(getResources().getColor(R.color.gray_white));
							 holder.tv_purchase_num1.setText(style);


							 holder.tv_zero_product_price_RMB.setTextColor(getResources().getColor(R.color.gray_white));
							 holder.tv_zero_product_price1.setTextColor(getResources().getColor(R.color.gray_white));
						}

			//33			holder.tv_free_postage.setVisibility(View.VISIBLE);
					}else if(count==0){//没有这情况
			//33				holder.rl_zero_shop_product1.setVisibility(View.GONE);
			//33			holder.rl_zero_shop_product2.setVisibility(View.GONE);
			//33			holder.rl_zero_shop_product3.setVisibility(View.GONE);
		//33				holder.rl_zero_shop_top.setVisibility(View.GONE);
		//33				holder.v_line.setVisibility(View.GONE);

						holder.img_zero_shop_product1.setVisibility(View.INVISIBLE);
						holder.img_zero_shop_product2.setVisibility(View.INVISIBLE);
						holder.img_zero_shop_product3.setVisibility(View.INVISIBLE);

						holder.v_huisequyu.setVisibility(View.GONE);
						holder.container_item.setVisibility(View.GONE);
//						return view;
					}
		//33			holder.tv_zero_shop_postage_tou.setText(str[count]);
					String postage = new DecimalFormat("#").format(Double
							.parseDouble(mapObj.get("postage")
									.toString()));

					/*holder.tv_zero_shop_postage.setText(Html.fromHtml(String.format(getString(R.string.tv_free_postage_price),postage)));*/
					String postage_meal=mapObj.get("postage")+"";
//					System.out.println("^^^^^^"+postage_meal);
					int postage_meal_int = Integer.valueOf(postage_meal).intValue();
					if (postage_meal_int!=0) {
						holder.tv_zero_shop_postage.setText("邮费"+postage_meal+".0");
						holder.tv_zero_shop_postage_gone.setText("邮费"+postage_meal+".0");

					}else {
						holder.tv_zero_shop_postage.setText("包邮");
						holder.tv_zero_shop_postage_gone.setText("包邮");
					}
//
//					holder.tv_package_kind_tou.setText(str[count]);
//					String postage2 = new DecimalFormat("#").format(Double
//							.parseDouble(mapObj.get("postage")
//									.toString()));


//					pos = getArguments().getString("position");


	/*	33			if (pos.equals(1+"")){

						holder.tv_package_kind_tou.setTextSize(16);
						holder.tv_package_kind_tou.setText("9元");
					}
					if (pos.equals(2+"")){
						holder.tv_package_kind_tou.setTextSize(16);
						holder.tv_package_kind_tou.setText("19元");
					}
					if (pos.equals(3+"")){
						holder.tv_package_kind_tou.setTextSize(16);
						holder.tv_package_kind_tou.setText("29元");
					}*/


//					holder.tv_package_kind_tou.setText(Html.fromHtml(String.format(getString(R.string.tv_package_kind_price),postage2)));


//				}




/*			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
   56465//					mContext.getSharedPreferences("YSSJ_yf",
//							mContext.MODE_PRIVATE).edit()
//							.putBoolean("isGoDetail", true).commit();
//					addScanDataTo((String) dataList.get(position).get("shop_code"));
					Intent intent = new Intent(mContext, ShopDetailsActivity.class);
//					if(!"3".equals(pos)){
					intent.putExtra("isMeal", true);
//					}else{
//						intent.putExtra("isMeal", false);
//					}
					intent.putExtra("code",pList.get(position).get("code")+"");
					intent.putExtra("pos", pos);
					if (num_meal>0) {
					intent.putExtra("number_sold", "have");
					}
					if (num_meal==0) {
						intent.putExtra("number_sold", "none");
					}
					// context.startActivity(intent);
//					intent.putExtra("shopCarFragment", "shopCarFragment");
					FragmentActivity activity = (FragmentActivity) mContext;
					activity.startActivity(intent);
					activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);;

					if (num_meal>0) {
						holder.bt_zero_shop.performClick();
					}
					if (num_meal==0) {
						holder.bt_zero_shop_gone.performClick();
					}
				}55
			});*/

					if (num_meal>0) {
						view.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Intent intent = new Intent(mContext, ShopDetailsActivity.class);
								intent.putExtra("isMeal", true);
								intent.putExtra("code",pList.get(position).get("code")+"");
								intent.putExtra("pos", pos);
								intent.putExtra("number_sold", "have");
								FragmentActivity activity = (FragmentActivity) mContext;
								activity.startActivity(intent);
								activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

							}
						});
					}else {
						view.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Intent intent = new Intent(mContext, ShopDetailsActivity.class);
								intent.putExtra("isMeal", true);
								intent.putExtra("code",pList.get(position).get("code")+"");
								intent.putExtra("pos", pos);
								intent.putExtra("number_sold", "none");
								FragmentActivity activity = (FragmentActivity) mContext;
								activity.startActivity(intent);
								activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

							}
						});
					}



			}/*d单品范围*/else if(choice.equals("0")){



//				System.out.println("***///***");

				if (view == null ) {
					view = LayoutInflater.from(context).inflate(
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
				LayoutInflater inflater = LayoutInflater.from(context);
		//999		holder_single.container_item.removeAllViews();
				HashMap<String, Object> mapObj = pList.get(position);
				 String code = mapObj.get("code")+"";

				 //点击单品按钮"立即抢购"跳转到详情页面
				 //还没抢完的按钮
				 holder_single.bt_zero_shop.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(mContext, ShopDetailsActivity.class);
						intent.putExtra("isMeal", true);
						intent.putExtra("code",pList.get(position).get("code")+"");
						intent.putExtra("pos", pos);
						intent.putExtra("number_sold", "have");
						FragmentActivity activity = (FragmentActivity) mContext;
						activity.startActivity(intent);
						activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
					}
				});

				 //抢完了的按钮
				 holder_single.bt_zero_shop_gone.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mContext, ShopDetailsActivity.class);
						intent.putExtra("isMeal", true);
						intent.putExtra("code",pList.get(position).get("code")+"");
						intent.putExtra("pos", pos);
						intent.putExtra("number_sold", "none");
						FragmentActivity activity = (FragmentActivity) mContext;
						activity.startActivity(intent);
						activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

					}
				});
			/*	 holder_single.tv_allowance_count.setText("仅剩"+mapObj.get("r_num")+"套");*/

			/*	66		String r_num_single = mapObj.get("r_num")+"";
						int num_single = Integer.valueOf(r_num_single).intValue();*/
						holder_single.pb_allowance_count_zong.setProgress(
								(int) ((Double.parseDouble((String) mapObj.get("r_num"))/Double.parseDouble((mapObj.get("num")+"")))*100));

						int count =0;
						String name = null ;
						List<HashMap<String, Object>> shopList = new ArrayList<HashMap<String, Object>>();
						shopList = (List<HashMap<String, Object>>) mapObj.get("shop_list");
						for(int i=0;i<shopList.size();i++){
							String kc_single = mapObj.get("num")+"";//库存
//							System.out.println("//__"+kc_single);

							String r_num_single = mapObj.get("r_num")+"";
							num_single = Integer.valueOf(r_num_single).intValue();

							holder_single.tv_allowance_count.setText("限量"+kc_single+"件,还剩"+mapObj.get("r_num")+"件");
								if(i==0){
									count=1;

									if(ZeroShopFragment.width>720){
										SetImageLoader.initImageLoader(context, holder_single.img_zero_shop_product1,
												shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!382");
									}else{
										SetImageLoader.initImageLoader(context, holder_single.img_zero_shop_product1,
												shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!280");
									}

									holder_single.tv_zero_product_name1.setText(shopList.get(i).get("shop_name")+"");


									holder_single.tv_zero_product_price1.setText(shopList.get(i).get("shop_se_price")+".0");


									String addStrikeSpan_single=shopList.get(i).get("shop_price")+"";
									addStrikeSpan_num_single = Double.parseDouble(addStrikeSpan_single);
								/*	Double avergage3=(addStrikeSpan_num_single)/1;*/

									DecimalFormat formater = new DecimalFormat("#0.0");
									 String format = formater.format(addStrikeSpan_num_single);

									if (!TextUtils.isEmpty(shopList.get(i).get("shop_price")+"")) {

										ToastUtil.addStrikeSpan( holder_single.tv_zero_price_outdate1
												,  "¥"+format);
									}

									number_single = shopList.get(i).get("virtual_sales")+"";
									str_single = "有"+shopList.get(i).get("virtual_sales")+"人在抢";
									SpannableStringBuilder style=new SpannableStringBuilder(str_single);
									 style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.zero_shop_choice)),1,number_single.length()+1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
									 holder_single.tv_purchase_num1.setTextColor(getResources().getColor(R.color.huise));
									 holder_single.tv_purchase_num1.setText(style);

								}/*else if(i==1){
									count=2;
									if((shopList.get(i).get("shop_name")+"").equals(name)){
										count--;
										continue;
									}
									if(ZeroShopFragment.width>720){
							 55			SetImageLoader.initImageLoader(context, holder_single.img_zero_shop_product2,
												shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!382");
									}else{
							55			SetImageLoader.initImageLoader(context, holder_single.img_zero_shop_product2,
												shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!280");
									}
									if(pos.equals(1+"")||pos.equals(2+"")||pos.equals(3+"")){



									}
									if(pos.equals(0+"")){
									}


							//55		holder_single.tv_zero_product_name2.setText(shopList.get(i).get("shop_name")+"");
								}else if(i==2){
									count=3;
									if(ZeroShopFragment.width>720){
								55		SetImageLoader.initImageLoader(context, holder_single.img_zero_shop_product3,
												shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!382");
									}else{
							55			SetImageLoader.initImageLoader(context, holder_single.img_zero_shop_product3,
												shopList.get(i).get("shop_code").toString().substring(1, 4)+"/"+shopList.get(i).get("shop_code").toString()+"/"+(String) shopList.get(i).get("four_pic"),"!280");
									}

							//55		holder_single.tv_zero_product_name3.setText(shopList.get(i).get("shop_name")+"");
								}*/
				/*	999			else if(i>2){
									addView(shopList.get(i).get("shop_name")+"", (String) shopList.get(i).get("four_pic"), shopList.get(i).get("shop_se_price")+"", shopList.get(i).get("shop_price")+"", shopList.get(i).get("virtual_sales")+"", holder_single.container_item, inflater);
								}*/
							}

			/*	55	单品没有这情况	if(count==2){
				//33			holder.rl_zero_shop_product1.setVisibility(View.VISIBLE);
				//33			holder.rl_zero_shop_product2.setVisibility(View.VISIBLE);
				//33			holder.rl_zero_shop_product3.setVisibility(View.GONE);
							holder_single.img_zero_shop_product1.setVisibility(View.VISIBLE);
							holder_single.img_zero_shop_product2.setVisibility(View.VISIBLE);
							holder_single.img_zero_shop_product3.setVisibility(View.INVISIBLE);

							holder_single.img_zero_shop_add1.setVisibility(View.VISIBLE);
							holder_single.img_zero_shop_add2.setVisibility(View.INVISIBLE);

							//商品名称
							holder_single.tv_zero_product_name1.setVisibility(View.VISIBLE);
							holder_single.tv_zero_product_name2.setVisibility(View.VISIBLE);
							holder_single.tv_zero_product_name3.setVisibility(View.INVISIBLE);

							if (num>0) {
								//遮挡物
								holder_single.zero_shop_item_soldall1.setVisibility(View.INVISIBLE);
								holder_single.zero_shop_item_soldall2.setVisibility(View.INVISIBLE);
								holder_single.zero_shop_item_soldall3.setVisibility(View.INVISIBLE);



							}else {
								//遮挡物
								holder_single.zero_shop_item_soldall1.setVisibility(View.VISIBLE);
								holder_single.zero_shop_item_soldall2.setVisibility(View.VISIBLE);
								holder_single.zero_shop_item_soldall3.setVisibility(View.VISIBLE);

							}


				//33			holder.tv_free_postage.setVisibility(View.VISIBLE);
						}else*/ if(count!=0){
							if (num_single>0) {
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
							if(num_single==0){
								//遮挡物
								holder_single.zero_shop_item_soldall1.setVisibility(View.VISIBLE);
								holder_single.tv_zero_product_name1.setTextColor(getResources().getColor(R.color.gray_white));
								holder_single.tv_zero_product_price1.setTextColor(getResources().getColor(R.color.gray_white));
								holder_single.tv_zero_product_price_RMB.setTextColor(getResources().getColor(R.color.gray_white));

								holder_single.tv_zero_shop_postage.setVisibility(View.GONE);
						//		holder_single.tv_zero_shop_postage.setTextColor(getResources().getColor(R.color.price));
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


							}/*else {
								holder_single.tv_zero_shop_postage.setTextColor(getResources().getColor(R.color.zero_shop_choice));
							}*/

				//33			holder.rl_zero_shop_product1.setVisibility(View.VISIBLE);
				//33			holder.rl_zero_shop_product2.setVisibility(View.GONE);
				//33			holder.rl_zero_shop_product3.setVisibility(View.GONE);
				//44			holder.tv_free_postage.setVisibility(View.GONE);
						}/*else if(count==3){ //55	单品没有这情况
				//33			holder.rl_zero_shop_product1.setVisibility(View.VISIBLE);
				//33			holder.rl_zero_shop_product2.setVisibility(View.VISIBLE);
				//33			holder.rl_zero_shop_product3.setVisibility(View.VISIBLE);

							holder_single.img_zero_shop_product1.setVisibility(View.VISIBLE);
							holder_single.img_zero_shop_product2.setVisibility(View.VISIBLE);
							holder_single.img_zero_shop_product3.setVisibility(View.VISIBLE);

							holder_single.img_zero_shop_add1.setVisibility(View.VISIBLE);
							holder_single.img_zero_shop_add2.setVisibility(View.VISIBLE);

							//商品名称
							holder_single.tv_zero_product_name1.setVisibility(View.VISIBLE);
							holder_single.tv_zero_product_name2.setVisibility(View.VISIBLE);
							holder_single.tv_zero_product_name3.setVisibility(View.VISIBLE);

							if (num>0) {
								holder_single.zero_shop_item_soldall1.setVisibility(View.INVISIBLE);
								holder_single.zero_shop_item_soldall2.setVisibility(View.INVISIBLE);
								holder_single.zero_shop_item_soldall3.setVisibility(View.INVISIBLE);
							}else {
								holder_single.zero_shop_item_soldall1.setVisibility(View.VISIBLE);
								holder_single.zero_shop_item_soldall2.setVisibility(View.VISIBLE);
								holder_single.zero_shop_item_soldall3.setVisibility(View.VISIBLE);
							}

				//33			holder.tv_free_postage.setVisibility(View.VISIBLE);
						}*//*else if(count==0){//没有这情况
				//33				holder.rl_zero_shop_product1.setVisibility(View.GONE);
				//33			holder.rl_zero_shop_product2.setVisibility(View.GONE);
				//33			holder.rl_zero_shop_product3.setVisibility(View.GONE);
			//33				holder.rl_zero_shop_top.setVisibility(View.GONE);
			//33				holder.v_line.setVisibility(View.GONE);

							holder_single.img_zero_shop_product1.setVisibility(View.INVISIBLE);
							holder_single.img_zero_shop_product2.setVisibility(View.INVISIBLE);
							holder_single.img_zero_shop_product3.setVisibility(View.INVISIBLE);

							holder_single.v_huisequyu.setVisibility(View.GONE);
							holder_single.container_item.setVisibility(View.GONE);
//							return view;
						}*/
			//33			holder.tv_zero_shop_postage_tou.setText(str[count]);
						String postage = new DecimalFormat("#").format(Double
								.parseDouble(mapObj.get("postage")
										.toString()));
						holder_single.tv_zero_shop_postage.setText(Html.fromHtml(String.format(getString(R.string.tv_free_postage_price),postage)));
						holder_single.tv_zero_shop_postage_gone.setText(Html.fromHtml(String.format(getString(R.string.tv_free_postage_price),postage)));

						String postage_single=mapObj.get("postage")+"";
//						System.out.println("^^^^^^"+postage_single);
						 int postage_single_int = Integer.valueOf(postage_single).intValue();
						if (postage_single_int!=0) {
							holder_single.tv_zero_shop_postage.setText("邮费"+postage_single+".0");
							holder_single.tv_zero_shop_postage_gone.setText("邮费"+postage_single+".0");
						}else {
							holder_single.tv_zero_shop_postage.setText("包邮");
							holder_single.tv_zero_shop_postage_gone.setText("包邮");
						}



//
//						holder.tv_package_kind_tou.setText(str[count]);
//						String postage2 = new DecimalFormat("#").format(Double
//								.parseDouble(mapObj.get("postage")
//										.toString()));


//						pos = getArguments().getString("position");


		/*	33			if (pos.equals(1+"")){

							holder.tv_package_kind_tou.setTextSize(16);
							holder.tv_package_kind_tou.setText("9元");
						}
						if (pos.equals(2+"")){
							holder.tv_package_kind_tou.setTextSize(16);
							holder.tv_package_kind_tou.setText("19元");
						}
						if (pos.equals(3+"")){
							holder.tv_package_kind_tou.setTextSize(16);
							holder.tv_package_kind_tou.setText("29元");
						}*/


//						holder.tv_package_kind_tou.setText(Html.fromHtml(String.format(getString(R.string.tv_package_kind_price),postage2)));


//					}




	/*			view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
 545646   //						mContext.getSharedPreferences("YSSJ_yf",
//								mContext.MODE_PRIVATE).edit()
//								.putBoolean("isGoDetail", true).commit();
//						addScanDataTo((String) dataList.get(position).get("shop_code"));

						Intent intent = new Intent(mContext, ShopDetailsActivity.class);
//						if(!"3".equals(pos)){
						intent.putExtra("isMeal", true);
//						}else{
//							intent.putExtra("isMeal", false);
//						}
						intent.putExtra("code",pList.get(position).get("code")+"");
						intent.putExtra("pos", pos);


						// context.startActivity(intent);
//						intent.putExtra("shopCarFragment", "shopCarFragment");
						FragmentActivity activity = (FragmentActivity) mContext;
						activity.startActivity(intent);
						activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);;

						Intent intent = new Intent(mContext, ShopDetailsActivity.class);
						intent.putExtra("isMeal", true);
						intent.putExtra("code",pList.get(position).get("code")+"");
						intent.putExtra("pos", pos);

						if (num_single>0) {
							intent.putExtra("number_sold", "have");
						}else {
							intent.putExtra("number_sold", "none");
						}
						FragmentActivity activity = (FragmentActivity) mContext;
						activity.startActivity(intent);
						activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);


					}
				});*/
						if (num_single>0) {
							view.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									Intent intent = new Intent(mContext, ShopDetailsActivity.class);
									intent.putExtra("isMeal", true);
									intent.putExtra("code",pList.get(position).get("code")+"");
									intent.putExtra("pos", pos);
									intent.putExtra("number_sold", "have");
									FragmentActivity activity = (FragmentActivity) mContext;
									activity.startActivity(intent);
									activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
								}
							});

						}else {
							view.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									Intent intent = new Intent(mContext, ShopDetailsActivity.class);
									intent.putExtra("isMeal", true);
									intent.putExtra("code",pList.get(position).get("code")+"");
									intent.putExtra("pos", pos);
									intent.putExtra("number_sold", "none");
									FragmentActivity activity = (FragmentActivity) mContext;
									activity.startActivity(intent);
									activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

								}
							});
						}

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

	private static class ViewHolder {
		public TextView tv_zero_product_name;
		private TextView tv_zero_shop_postage,tv_package_kind,tv_allowance_count,tv_free_postage,tv_zero_product_price_RMB,
		tv_zero_price_outdate1,tv_zero_price_outdate2,tv_zero_price_outdate3,tv_29_allowance_count,tv_zero_shop_words,zero_shop_item_single,tv_zero_shop_item_meal;
		private ProgressBar pb_allowance_count_zong,pb_allowance_count,pb_allowance_count_zong_gone;
		private View v_line,v_huisequyu,zero_shop_item_soldall1,zero_shop_item_soldall2,zero_shop_item_soldall3;
		private RelativeLayout rl_zero_shop_top,rl_pb_product,rl_zero_shop_product1,rl_zero_shop_product2,rl_zero_shop_product3,rl_zero_product_name,rl_zero_product_name2,rl_zero_product_name3;

		//商品列表
		private ImageView img_zero_shop_product1,img_zero_shop_product2,img_zero_shop_product3,img_zero_shop_add1,img_zero_shop_add2;
		private TextView tv_zero_product_name1,tv_zero_product_name2,tv_zero_product_name3,tv_zero_shop_postage_gone
		,tv_zero_product_price1,tv_zero_product_price2,tv_zero_product_price3,tv_meal_name
		,tv_purchase_num1,tv_purchase_num2,tv_purchase_num3,tv_zero_shop_postage_tou,tv_package_kind_tou,tv_package_kind_tou2;
		private LinearLayout container_item,zero_shop_choice,zero_shop_item_meal;
		private Button bt_zero_shop,bt_zero_shop_gone;
	}

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
//			TextView tvStatus = (TextView) view.findViewById(R.id.tv_status);
			// TextView tvOriginalPrice = (TextView) view
			// .findViewById(R.id.tv_original_price);

//			String pic = orderShop.getShop_pic();
//			img_product.setTag(pic);
			if (!TextUtils.isEmpty(url)) {
				if(ZeroShopFragment.width>720){
					SetImageLoader.initImageLoader(mContext, img_product, url,"!382");
				}else{
					SetImageLoader.initImageLoader(mContext, img_product, url,"!280");
				}

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
