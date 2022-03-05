package com.yssj.ui.pager;

import java.util.HashMap;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.ForceLookItemView;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.model.ComModel2;
import com.yssj.ui.adpter.MyCouponsAllPagerAdapter;
import com.yssj.ui.base.BasePager;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AllCouponsPage extends BasePager {
	private HashMap<String, Object> map;
	public AllCouponsPage(Context context) {
		super(context);
	}

	private PullToRefreshListView lv_common;
	private int currPage = 1;
	private MyCouponsAllPagerAdapter mAdapter;

	private boolean flag = true; // 判断上拉、下拉的标志

	private LinearLayout account_nodata;
	private Button btn_view_allcircle;
	private TextView tv_qin, tv_no_join;
	private TextView diyong_one;
	private TextView diyong_two;
	private TextView diyong_five;
	private TextView diyong_ten;

//	private List<HashMap<String, Object>> diyongqianresult;

	private int num_one = 0;
	private int num_two = 0;
	private int num_five = 0;
	private int num_ten = 0;
	private View inflate;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.pulltorefreshlistview_coupons,
				null);
		view.setBackgroundColor(Color.WHITE);
		lv_common = (PullToRefreshListView) view.findViewById(R.id.lv_common);
		inflate = View.inflate(context, R.layout.weishiyong, null);
		ListView refreshableView = lv_common.getRefreshableView();
		refreshableView.addFooterView(inflate);
		inflate.setVisibility(View.INVISIBLE);
		diyong_one = (TextView) view.findViewById(R.id.diyong_one);
		diyong_two = (TextView) view.findViewById(R.id.diyong_two);
		diyong_five = (TextView) view.findViewById(R.id.diyong_five);
		diyong_ten = (TextView) view.findViewById(R.id.diyong_ten);

		/*
		 * diyong_one.setText(num_one); diyong_two.setText(num_two);
		 * diyong_five.setText(num_five); diyong_ten.setText(num_ten);
		 */

		account_nodata = (LinearLayout) view.findViewById(R.id.account_nodata);

		btn_view_allcircle = (Button) view
				.findViewById(R.id.btn_view_allcircle);
		btn_view_allcircle.setVisibility(View.GONE);

		tv_qin = (TextView) view.findViewById(R.id.tv_qin);
		tv_qin.setText("亲，暂时没有相关数据哦~");
		tv_no_join = (TextView) view.findViewById(R.id.tv_no_join);
		tv_no_join.setText("暂无优惠券");
		tv_no_join.setVisibility(View.GONE);

		return view; // 暂时直接显示我加的头
		// return inflate;
	}

	@Override
	public void initData() {
		currPage = 1;
//		diYongQuan();
		queryAllConponsData();
		mAdapter = new MyCouponsAllPagerAdapter(context);
		lv_common.setAdapter(mAdapter);

		super.initIndicator(lv_common);

		lv_common.setMode(Mode.BOTH);

		lv_common.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				flag = true;

				currPage = 1;
				queryAllConponsData();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				flag = false;
				currPage++;
				queryAllConponsData();
			}
		});
	}

//	private void diYongQuan() {
//		new SAsyncTask<Integer, Void, List<HashMap<String, Object>>>(
//				(FragmentActivity) context, R.string.wait) {
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(
//					FragmentActivity context, Integer... params)
//					throws Exception {
//				return ComModel2.DiYongQuanDialog(context);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<HashMap<String, Object>> result, Exception e) {
//				super.onPostExecute(context, result, e);
//
//				// mAdapter.addItemFirst(result!=null?result:null);
//
//				/*
//				 * String ss = result.get(0).get("price").toString();
//				 * System.out.println("0.0.0.0"+ss);
//				 */
//
//				if (null != result && null == e) {
//					diyongqianresult = result;
//					// 拿到键值并设置到对应位置
//					for (int i = 0; i < result.size(); i++) {
//						String price = result.get(i).get("price").toString();
//						String snum = result.get(i).get("snum").toString();
//
//						if (null != price && null != snum) {
//							int num = Integer.valueOf(snum).intValue();
//							if (price.equals("1")) {
//								num_one = num;
//								String s = String.valueOf(num_one);
//								view.findViewById(R.id.fl_one).setVisibility(View.VISIBLE);
//								diyong_one.setText("x" + s);
//							}
//
//							if (price.equals("2")) {
//								num_two = num;
//								String s = String.valueOf(num_two);
//								view.findViewById(R.id.fl_two).setVisibility(View.VISIBLE);
//								diyong_two.setText("x" + s);
//							}
//
//							if (price.equals("5")) {
//								num_five = num;
//								String s = String.valueOf(num_five);
//								view.findViewById(R.id.fl_five).setVisibility(View.VISIBLE);
//								diyong_five.setText("x" + s);
//							}
//							// 除了首次登陆都没有10元
//							if (price.equals("10")) {
//								num_ten = num;
//								LogYiFu.e("啊是", num + "");
//								String s = String.valueOf(num_ten);
//								view.findViewById(R.id.fl_ten).setVisibility(View.VISIBLE);
//								diyong_ten.setText("x" + s);
//							}
//						}
//					}
//
//				}
//
//			}
//
//		}.execute();
//
//	}

	private void queryAllConponsData() {

		new SAsyncTask<Integer, Void, List<HashMap<String, Object>>>(
				(FragmentActivity) context, R.string.wait) {
			
			
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				LoadingDialog.show((FragmentActivity) context);
			}
			
			
			@Override
			protected List<HashMap<String, Object>> doInBackground(
					FragmentActivity context, Integer... params)
					throws Exception {
				return ComModel2.getMyCouponList(context, currPage, ">", "1", "");
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

					if (flag) {
						String is_open =  SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_IS_OPEN, "");
						String is_use =  SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_IS_USE, "");
						String c_price =  SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_C_PRICE, "0.0");
						String c_id =  SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_C_ID, "");
						String c_last_time =  SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_C_LAST_TIME, "0");
						String end_date =  SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_END_DATE, "0");
						boolean isHave = false;
						
						for (int i = 0; i < result.size(); i++) {
							if(((Integer)result.get(i).get("id")+"").equals(c_id)){
								isHave = true;
								break;
							}
						} 
						//金券保持放在最上面   手动添加可以用的金券    解析时候 是金券就不放在集合中  字段必须和解析类 中 createMyCouponListNew 一致
						if("1".equals(is_open)&&"0".equals(is_use)&&!isHave){
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("end_date", end_date);
							map.put("c_last_time", c_last_time);
							map.put("id", Integer.valueOf(c_id));
							map.put("is_use", "1");//优惠券未使用的 is_use =1
							map.put("c_price", Double.valueOf(c_price));
							map.put("is_open", is_open);
							result.add(0, map);
						}
						if (result != null
								&& (result.size() == 0 || result.isEmpty())) {

//							if (diyongqianresult.size() == 0 || diyongqianresult == null) {
//								account_nodata.setVisibility(View.VISIBLE);
//								lv_common.setVisibility(View.GONE);
//							}


							if(result != null && (result.size() == 0 || result.isEmpty())){
								account_nodata.setVisibility(View.VISIBLE);
								lv_common.setVisibility(View.GONE);
							}

						}
//						if (diyongqianresult.size() == 0 || diyongqianresult == null) {
//							
//						}else{
							inflate.setVisibility(View.GONE);//显示抵用券
//						}
						
						mAdapter.addItemFirst(result != null ? result : null);
					} else {
						mAdapter.addItemLast(result != null ? result : null);
					}

					mAdapter.notifyDataSetChanged();
					lv_common.onRefreshComplete();

				}
			}

		}.execute(currPage);
	}

}
