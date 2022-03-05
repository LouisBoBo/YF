package com.yssj.ui.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.CustImageGalleryMatch;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.MatchNavLeft;
import com.yssj.custom.view.MatchNavRigth;
import com.yssj.custom.view.PointAlarmView;
import com.yssj.custom.view.XListViewMatch;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.Shop;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainFragment;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.shopdetails.MatchDetailsActivity;
import com.yssj.ui.activity.shopdetails.SpecialTopicDeatilsActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;
import com.yssj.utils.YunYingTongJi;

public class MatchFragment extends Fragment implements View.OnClickListener {
	public static ArrayList<String> hashSet = new ArrayList<String>();

	private View root;
	private Context mContext;
	private XListViewMatch mListViewMatch;
//	private SnatchScrollList mView;
	private List<HashMap<String, Object>> mListDatas;
	private MatchAdapter matchAdater;
	private int curPager;
	private int pagerSize = 10;
	public static int width;
	public static int height;
	private java.text.DecimalFormat pFormate;
	private String mType;//增加参数1 返回全部是搭配商品 2返回专题
	
	
	/**
	 * @param title
	 * @param context
	 * @param type //增加参数1 返回全部是搭配商品 2 专题
	 */
	public static MatchFragment newInstances(String title, Context context,String type) {
		MatchFragment fragment = new MatchFragment();
		Bundle args = new Bundle();
		args.putString("tag", title);
		args.putString("type", type);
//		args.put("context", context);
//		mContext = context;
		fragment.setArguments(args);
		return fragment;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mType = (String) getArguments().get("type");
		mContext = getActivity();
		return inflater.inflate(R.layout.match_fragment, container, false);
		
		
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		root = getView().findViewById(R.id.match_fragment_root);
		root.setBackgroundColor(Color.WHITE);
		pFormate=new DecimalFormat("#0.0");
//		imageCart = (ImageButton) getView().findViewById(R.id.img_shop_cart_common);
//		imageCart.setOnClickListener(this);
//		imageCartCount = (TextView) getView().findViewById(R.id.zero_shop_count_common);
//		mView = (SnatchScrollList) getView().findViewById(R.id.matchView);
//		mView.setMatchOnRefreshLintener(this);
		mListViewMatch = (XListViewMatch) getView().findViewById(R.id.dataList);
		mListViewMatch.setPullLoadEnable(true);
		curPager = 1;
		
		/**
		 * 	  解决混淆错误问题    -----  v3.3.1已经解决  ,
		 *   这里如果拿到"0" 说明是v3.2.2版  没有混淆的代码，退出登录即可，
		 *   3.2.2之后的版本在登录成功之后都已经保存标志"1"
		 */
		
// 		 String HUNXIAO =  SharedPreferencesUtil.getStringData(mContext, "HUNXIAO", 0+"");
////		 
//		 
//		 if(HUNXIAO.equals(0 +"")){
//			 Boolean isLogin = YJApplication.instance.isLoginSucess();
//			 
//			 if (isLogin){
//					loginExit();
//				}
//		 }
		
		

		DisplayMetrics dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);

		width = dm.widthPixels;
		height = dm.heightPixels;

		mListViewMatch.setXListViewListener(new XListViewMatch.IXListViewListener() {

			@Override
			public void onRefresh() {
				LogYiFu.e("MatchFragment", "onRefresh");
			}

			@Override
			public void onLoadMore() {
				LogYiFu.e("MatchFragment", "onLoadMore");
				curPager++;
				queryMatch();
			}
		});
		mListDatas = new ArrayList<HashMap<String, Object>>();
		matchAdater = new MatchAdapter(getActivity(), mListDatas);
		mListViewMatch.setAdapter(matchAdater);
		queryMatch();
		
		
		
//		//下面的是红包的弹窗
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd");
//		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
//		String systime_now = formatter.format(curDate);
//		String time_pass = SharedPreferencesUtil.getStringData(getActivity(), "first_match_time", "-1");
//		
//		
//		String is_first = SharedPreferencesUtil.getStringData(getActivity(), "is_first", "-2");
//		
//		if (!is_first.equals(systime_now)) {
//			SharedPreferencesUtil.saveBooleanData(getActivity(), "first_the_day", true);
//		}else {
//			SharedPreferencesUtil.saveBooleanData(getActivity(), "first_the_day", false);
//		}
//		first_the_day = SharedPreferencesUtil.getBooleanData(getActivity(), "first_the_day", false);
//				if (first_the_day == true) {
//					EightyDialog eightydialog = new EightyDialog(MatchFragment.this.getActivity());
//					eightydialog.show();
//					SharedPreferencesUtil.saveStringData(getActivity(), "is_first", systime_now);
//					SharedPreferencesUtil.saveBooleanData(getActivity(), "appear", true);
//				}else {
//					SharedPreferencesUtil.saveBooleanData(getActivity(), "appear", false);
//				}
//		
//		
//		if (YJApplication.instance.isLoginSucess() ) {
////			String time_pass = SharedPreferencesUtil.getStringData(getActivity(), "first_match_time", "-1");
//
//			String user_string = SharedPreferencesUtil.getStringData(getActivity(), "user_string", "");
//			Boolean appear = SharedPreferencesUtil.getBooleanData(getActivity(), "appear", false);
//
//			hashSet.clear();
//			if (!user_string.equals("") || user_string != null) {
//				String[] split = user_string.split(",");
//				for (int i = split.length-1; i >=0 ; i--) {
//					hashSet.add(split[i]);
//				}
//			}
//			UserInfo userInfo = YCache.getCacheUser(mContext);
//			String user_id = userInfo.getUser_id() + "";
//
//			
//			if (!time_pass.equals(systime_now)) {
//				hashSet.clear();
//			}
//
//
//			if (!hashSet.contains(user_id) && appear == true) {
//				diyongquan();
//				hashSet.add(user_id);
//				SharedPreferencesUtil.saveStringData(getActivity(), "first_match_time", systime_now);
//				
//
//				StringBuffer stringBuffer = new StringBuffer();
//
//				for (int i = hashSet.size()-1; i >=0 ; i--) {
//					String integer = hashSet.get(i);
//
//					if (i != 0) {
//						stringBuffer.append(integer + ",");
//					} else {
//						stringBuffer.append(integer);
//					}
//
//				}
//				String string_last = stringBuffer.toString();
//				SharedPreferencesUtil.saveStringData(getActivity(), "user_string", string_last);
//
//			}
//
//		}
	}
	public void refresh() {
		curPager = 1;
		queryMatch();
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	public void onResume() {
		super.onResume();
		
	}
	
	// 查询购物车数量
//	private void queryCartCount() {
//
//		new SAsyncTask<Void, Void, String>(getActivity(), R.string.wait) {
//
//			@Override
//			protected String doInBackground(FragmentActivity context, Void... params) throws Exception {
//				return ComModel2.getShopCartCount(context);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, String count, Exception e) {
//				super.onPostExecute(context, count, e);
//				if (e != null || count == null) {
//					return;
//				}
//				if (Integer.parseInt(count) > 0) {
//					imageCartCount.setVisibility(View.VISIBLE);
//					imageCartCount.setText(count);
//				} else {
//					imageCartCount.setVisibility(View.GONE);
//				}
//
//			}
//		}.execute();
//
//	}

	/**
	 * 搭配
	 */
	private void queryMatch() {
		new SAsyncTask<Void, Void, List<HashMap<String, Object>>>((FragmentActivity) mContext, R.string.wait) {
			

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				LoadingDialog.show((FragmentActivity)mContext);
			}
			
			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Void... params)
					throws Exception {

				return ComModel2.getMatch(context, curPager + "", pagerSize + "",mType);//增加参数1 返回全部是搭配商品 2返回专题
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
				if (e != null) {// 查询异常
					mListViewMatch.stopLoadMore();
					return;
				}
				List<HashMap<String, Object>> dataList = result;
				if (dataList != null) {
					if (dataList.size() == 0 && curPager > 1) {
						ToastUtil.showShortText(context, "已没有更多商品了");
					} else {
						if (curPager == 1) {
							mListDatas.clear();
						}
						mListDatas.addAll(dataList);
						matchAdater.notifyDataSetChanged();
					}
				} else {
					if (curPager == 1) {
						mListDatas.clear();
						matchAdater.notifyDataSetChanged();
					} else {
						ToastUtil.showShortText(context, "已没有更多商品了");
					}
				}
				mListViewMatch.stopLoadMore();
				LogYiFu.e("MatchFragment", "MatchResult " + result.size() + "curPager" + curPager);
				super.onPostExecute(context, result, e);
			}

		}.execute();
	}


	public static MatchFragment newInstances(String title, Context context) {
		MatchFragment fragment = new MatchFragment();
		Bundle args = new Bundle();
		args.putString("tag", title);
//		mContext = context;
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
	}

	private class MatchAdapter extends BaseAdapter {
		private Context context;
		private List<HashMap<String, Object>> listData;
		private LayoutInflater mInflater;

		public MatchAdapter(Context context, List<HashMap<String, Object>> listData) {
			this.context = context;
			this.listData = listData;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				holder = new Holder();
				convertView = mInflater.inflate(R.layout.match_fragment_list, null);
				holder.mainImageIv = (ImageView) convertView.findViewById(R.id.main_image_iv);
				holder.mainTitleTv = (TextView) convertView.findViewById(R.id.main_title_tv);
				holder.custMatch = (CustImageGalleryMatch) convertView.findViewById(R.id.match_custom_images);
				holder.mainImageRl = (RelativeLayout) convertView.findViewById(R.id.main_image_rl);
				holder.containsRl = (RelativeLayout) convertView.findViewById(R.id.Match_contains_rl);
				
				holder.subjectImageRl = (RelativeLayout) convertView.findViewById(R.id.subject_main_image_rl);
				holder.subjectImageIv = (ImageView) convertView.findViewById(R.id.subject_main_image_iv);
				holder.subjectTv1 = (TextView) convertView.findViewById(R.id.subject_main_tv1);
				holder.subjectTv2 = (TextView) convertView.findViewById(R.id.subject_main_tv2);
				convertView.setTag(holder);

			} else {
				holder = (Holder) convertView.getTag();
			}
			final HashMap<String, Object> datas = listData.get(position);
			final String collocation_code = (String) datas.get("collocation_code");
			final String collocation_pic = (String) datas.get("collocation_pic");
			String collocation_name = (String) datas.get("collocation_name");
			String collocation_name2 = (String) datas.get("collocation_name2");
			String type = (String) datas.get("type");//1或者空 为搭配购，2为专题
			
			if("2".equals(type)){//专题
				holder.subjectImageRl.setVisibility(View.VISIBLE);
				holder.mainImageRl.setVisibility(View.GONE);
				
				holder.subjectTv1.setText(collocation_name);
				holder.subjectTv2.setText(collocation_name2);
				holder.subjectTv1.getPaint().setFakeBoldText(true);//设置中文字体加粗
				// 设置图片的宽高比 加载图片
				ViewGroup.LayoutParams lp = holder.subjectImageIv.getLayoutParams();
				lp.width = width;
				lp.height = LayoutParams.WRAP_CONTENT;
				holder.subjectImageIv.setLayoutParams(lp);
				holder.subjectImageIv.setMaxWidth(width);
				holder.subjectImageIv.setMaxHeight(width*2/3); // 宽高比3:2
				
//				SetImageLoader.initImageLoader(mContext, holder.subjectImageIv, collocation_pic, "!450");//主题图片
				PicassoUtils.initImage(mContext, collocation_pic, holder.subjectImageIv);
				List<HashMap<String, Object>> shop_type_list = (List<HashMap<String, Object>>) datas.get("shop_type_list");
				holder.custMatch.setData(shop_type_list,type);
				
				holder.subjectImageIv.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						YunYingTongJi.yunYingTongJi(context, 19);
						Intent intent = new Intent(context, SpecialTopicDeatilsActivity.class);
						intent.putExtra("collocation_code", collocation_code);
						intent.putExtra("collocation_pic", collocation_pic);
						mContext.startActivity(intent);
						((FragmentActivity)mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
					}
				});
				
			}else if("1".equals(type)||"".equals(type)){//搭配
				holder.subjectImageRl.setVisibility(View.GONE);
				holder.mainImageRl.setVisibility(View.VISIBLE);
				
				holder.containsRl.removeAllViews();
	//			final HashMap<String, Object> datas = listData.get(position);
	//			String collocation_name = (String) datas.get("collocation_name");
				holder.mainTitleTv.setText(collocation_name);
	
	//			final String collocation_code = (String) datas.get("collocation_code");
				// 设置图片的宽高比 加载图片
				ViewGroup.LayoutParams lp = holder.mainImageIv.getLayoutParams();
				lp.width = width;
				lp.height = LayoutParams.WRAP_CONTENT;
				holder.mainImageIv.setLayoutParams(lp);
				holder.mainImageIv.setMaxWidth(width);
				holder.mainImageIv.setMaxHeight(width); // 图片高度设置为屏幕的宽度
	
	//			final String collocation_pic = (String) datas.get("collocation_pic");
//				SetImageLoader.initImageLoader(mContext, holder.mainImageIv, collocation_pic, "!450");
				
				
				PicassoUtils.initImage(mContext, collocation_pic+"!450", holder.mainImageIv);
				
				List<HashMap<String, Object>> shop_type_list = (List<HashMap<String, Object>>) datas.get("shop_type_list");
				holder.custMatch.setData(shop_type_list,type);
	
				List<HashMap<String, Object>> collocation_shop_list = (List<HashMap<String, Object>>) datas
						.get("collocation_shop");
	
				for (int i = 0; i < collocation_shop_list.size(); i++) {
	
					RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);
	
					RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(
							ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	
					String shop_code = (String) collocation_shop_list.get(i).get("shop_code");
					String shop_name = (String) collocation_shop_list.get(i).get("shop_name");
					String shop_se_price = (String) collocation_shop_list.get(i).get("shop_se_price");
					String kickback = (String) collocation_shop_list.get(i).get("kickback");
					String option_flag = (String) collocation_shop_list.get(i).get("option_flag");
					String shop_x = (String) collocation_shop_list.get(i).get("shop_x");
					String shop_y = (String) collocation_shop_list.get(i).get("shop_y");
					double X = 0.0;
					double Y = 0.0;
					if (!TextUtils.isEmpty(shop_y) && !TextUtils.isEmpty(shop_x)) {
						X = Double.valueOf(shop_x);
						Y = Double.valueOf(shop_y);
					}
	
					if (i == 0) {
						X = X == 0 ? 0.45 : X;
						Y = Y == 0 ? 0.35 : Y;
	
	//					Y = Y < 0.2 ? 0.2 : Y;
	//					Y = Y > 0.75 ? 0.75 : Y;
						setLeftView(shop_code, option_flag, i, holder, param, param2, shop_name, shop_se_price,kickback, X, Y);
					} else if (i == 1) {
						X = X == 0 ? 0.6 : X;
						Y = Y == 0 ? 0.58 : Y;
	
	//					Y = Y < 0.2 ? 0.2 : Y;
	//					Y = Y > 0.75 ? 0.75 : Y;
						setRigthView(shop_code, option_flag, i, holder, param, param2, shop_name, shop_se_price,kickback, X, Y);
					}
				}
	
				holder.mainImageRl.setOnClickListener(new View.OnClickListener() {
	
					@Override
					public void onClick(View v) {
						YunYingTongJi.yunYingTongJi(context, 19);//搭配主图
	//					if (YJApplication.instance.isLoginSucess()) {
	//						Intent intent = new Intent(context, MatchDetailsActivity.class);
	//						intent.putExtra("collocation_code", collocation_code);
	//						((FragmentActivity) context).startActivity(intent);
	//					} else {
	//						Intent intent = new Intent(context, LoginActivity.class);
	//						intent.putExtra("login_register", "login");
	//						((FragmentActivity) context).startActivity(intent);
	//					}
						Intent intent = new Intent(context, MatchDetailsActivity.class);
						intent.putExtra("collocation_code", collocation_code);
						intent.putExtra("collocation_pic", collocation_pic);
						mContext.startActivity(intent);
						((FragmentActivity)mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
					}
				});
			}
			return convertView;
		}

		private void setLeftView(String shop_code, String option_flag, int i, Holder holder,
				RelativeLayout.LayoutParams param, RelativeLayout.LayoutParams param2, String shop_name,
				String shop_se_price,String kickback, Double X, Double Y) {
			MatchNavLeft matchNavLeft = new MatchNavLeft(mContext, shop_code,1,false);
			matchNavLeft.setTextView(Shop.getShopNameStrNew(shop_name));
			matchNavLeft.measure(0, 0);
			param2.leftMargin = (int) (width * X - matchNavLeft.getMeasuredWidth() - 8);
//			param2.topMargin = (int) (width / 2 + (Y - 0.5) * width * 1.5 - matchNavLeft.getMeasuredHeight() / 2 + 8);
			//图片宽高比为1
			param2.topMargin = (int) (width / 2 + (Y - 0.5) * width  - matchNavLeft.getMeasuredHeight() / 2 + 8);
			matchNavLeft.setLayoutParams(param2);// 设置布局参数
			holder.containsRl.addView(matchNavLeft);// RelativeLayout添加子View
			setAlarmPoint(holder, X, Y);
			if ("0".equals(option_flag)) {
				// TODO 有搭配
				TextView tv = new TextView(mContext);
				tv.setBackgroundResource(R.drawable.pricetag);
				tv.setId(i);
				double sPrice = Double.valueOf(shop_se_price);
				double sKickback =Double.valueOf(kickback);
				tv.setText("¥" + pFormate.format(sPrice*0.9));//显示九折价格
				tv.setTextColor(Color.WHITE);
				tv.setTextSize(9);
				tv.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
				tv.setPadding(2, 0, 2, 8);
				tv.measure(0, 0);
				param.leftMargin = (int) (width * X - tv.getMeasuredWidth() / 2);
				//图片宽高比为1
				param.topMargin = (int) (width / 2 + (Y - 0.5) * width );
				tv.setLayoutParams(param);// 设置布局参数
				holder.containsRl.addView(tv);// RelativeLayout添加子View
				matchNavLeft.getImgCart().setVisibility(View.VISIBLE);
				tv.setVisibility(View.VISIBLE);

			} else if ("1".equals(option_flag)) {
				// TODO 无搭配
				matchNavLeft.getImgCart().setVisibility(View.GONE);
				ImageView iv = new ImageView(mContext);
				iv.setBackgroundResource(R.drawable.red_point);
				iv.measure(0, 0);
				param.leftMargin = (int) (width * X - iv.getMeasuredWidth() / 2);
				//图片宽高比为1
				param.topMargin = (int) (width / 2 + (Y - 0.5) * width );
				iv.setLayoutParams(param);// 设置布局参数
				holder.containsRl.addView(iv);// RelativeLayout添加子View
			}
		}

		/**
		 * 圆点波形闪烁
		 * 
		 * @param holder
		 * @param X
		 * @param Y
		 */
		private void setAlarmPoint(Holder holder, Double X, Double Y) {
			ImageView iv = new ImageView(mContext);
			iv.setBackgroundResource(R.drawable.red_point);
			iv.measure(0, 0);

			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(80, 80);
			PointAlarmView pointView = new PointAlarmView(mContext);
			lp.leftMargin = (int) (width * X - 40);// pointView 1/2 位置
			//图片宽高比为1
			lp.topMargin = (int) (width / 2 + (Y - 0.5) * width - 40 + iv.getMeasuredHeight() / 2);
			pointView.setLayoutParams(lp);
			holder.containsRl.addView(pointView);
		}

		private void setRigthView(String shop_code, String option_flag, int i, Holder holder,
				RelativeLayout.LayoutParams param, RelativeLayout.LayoutParams param2, String shop_name,
				String shop_se_price, String kickback ,Double X, Double Y) {

			MatchNavRigth matchNavRigth = new MatchNavRigth(mContext, shop_code,1,false);
			matchNavRigth.setTextView(Shop.getShopNameStrNew(shop_name));
			matchNavRigth.measure(0, 0);
			param2.rightMargin = 10;
			param2.leftMargin = (int) (width * X + 8);
//			param2.topMargin = (int) (width / 2 + (Y - 0.5) * width * 1.5 - matchNavRigth.getMeasuredHeight() / 2 + 8);
			//图片宽高比为1
			param2.topMargin = (int) (width / 2 + (Y - 0.5) * width - matchNavRigth.getMeasuredHeight() / 2 + 8);
			matchNavRigth.setLayoutParams(param2);// 设置布局参数
			holder.containsRl.addView(matchNavRigth);// RelativeLayout添加子View
			setAlarmPoint(holder, X, Y);
			if ("0".equals(option_flag)) {
				// TODO 有搭配
				TextView tv = new TextView(mContext);
				tv.setBackgroundResource(R.drawable.pricetag);
				tv.setId(i + 500);
//				tv.setText("¥" + shop_se_price);
				double sPrice = Double.valueOf(shop_se_price);
//				double sKickback =Double.valueOf(kickback);
				tv.setText("¥" + pFormate.format(sPrice*0.9));//显示九折价格
				tv.setTextColor(Color.WHITE);
				tv.setTextSize(9);
				tv.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
				tv.setPadding(2, 0, 2, 8);
				tv.measure(0, 0);
				param.leftMargin = (int) (width * X - tv.getMeasuredWidth() / 2);
				//图片宽高比为1
				param.topMargin = (int) (width / 2 + (Y - 0.5) * width);
				tv.setLayoutParams(param);// 设置布局参数
				holder.containsRl.addView(tv);// RelativeLayout添加子View
				matchNavRigth.getImgCart().setVisibility(View.VISIBLE);
				tv.setVisibility(View.VISIBLE);

			} else if ("1".equals(option_flag)) {
				// TODO 无搭配
				matchNavRigth.getImgCart().setVisibility(View.GONE);
				ImageView iv = new ImageView(mContext);
				iv.setBackgroundResource(R.drawable.red_point);
				iv.measure(0, 0);
				param.leftMargin = (int) (width * X - iv.getMeasuredWidth() / 2);
				//图片宽高比为1
				param.topMargin = (int) (width / 2 + (Y - 0.5) * width);
				iv.setLayoutParams(param);// 设置布局参数
				holder.containsRl.addView(iv);// RelativeLayout添加子View
			}

		}

		class Holder {
			CustImageGalleryMatch custMatch;//搭配和专题显示的共同部分 推荐列表
			ImageView mainImageIv;
			TextView mainTitleTv;
			RelativeLayout mainImageRl,containsRl;
			/**
			 * 专题显示的View
			 */
			ImageView subjectImageIv;
			TextView subjectTv1,subjectTv2;
			RelativeLayout subjectImageRl;
		}
	}

}
