package com.yssj.ui.pager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.ui.adpter.MyCouponsPagerAdapter;
import com.yssj.ui.adpter.MyCouponsSelectPagerAdapter;
import com.yssj.ui.base.BasePager;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;

public class UsefulCouponsPage extends BasePager implements OnClickListener,OnItemClickListener{
	private HashMap<String, Object> map;
	private Activity fatherActivity;
	private List<HashMap<String, Object>> usefulList;
	private double amount;
	private double jinquan;
	private String is_open ;
	private String c_price ;
	private String is_use ;//金券是否使用过了
	public UsefulCouponsPage(Context context, double amount,double jinquan) {
		super(context);
		this.fatherActivity = (Activity) context;
		this.amount = amount;
		this.jinquan = jinquan;
		is_open =  SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_IS_OPEN, "");
		is_use =  SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_IS_USE, "");
		c_price =  SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_C_PRICE, "0.0");
	}

	private PullToRefreshListView lv_common;
	private int currPage = 1;
	private MyCouponsSelectPagerAdapter mAdapter;
	
	private boolean flag = true;		// 判断上拉、下拉的标志
	
	private LinearLayout account_nodata;
	private Button btn_view_allcircle;
	private Button bt_confirm;
	private TextView tv_qin,tv_no_join;
	private int selectPosition;
	
	private HashMap<String, Object> selectUsefulMap;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.pulltorefreshlistview_selectcoupons, null);
		lv_common = (PullToRefreshListView) view.findViewById(R.id.lv_common);
		lv_common.setOnItemClickListener(this);
		account_nodata = (LinearLayout) view.findViewById(R.id.account_nodata);
		
		btn_view_allcircle = (Button) view.findViewById(R.id.btn_view_allcircle);
		btn_view_allcircle.setVisibility(View.GONE);
		
		tv_qin = (TextView) view.findViewById(R.id.tv_qin);
		tv_qin.setText("O(∩_∩)O~亲~");
		tv_no_join = (TextView) view.findViewById(R.id.tv_no_join);
		tv_no_join.setText("暂无优惠券");
		
		//确定按钮和点击事件
		bt_confirm = (Button) view.findViewById(R.id.bt_confirm);
		bt_confirm.setOnClickListener(this);
		
		return view;
	}
	
	@Override
	public void initData() {
		currPage = 1 ;
		queryUsefulConponsData();
		mAdapter = new MyCouponsSelectPagerAdapter(context);
		lv_common.setAdapter(mAdapter);
		
		super.initIndicator(lv_common);
		
		lv_common.setMode(Mode.BOTH);
		
		lv_common.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
					flag = true;
					
					currPage = 1 ;
					queryUsefulConponsData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
					flag = false;
					currPage ++;
					queryUsefulConponsData();
			}
		});
	}
	
	
	
	private void queryUsefulConponsData() {
		
		new SAsyncTask<Integer, Void, List<HashMap<String, Object>>>(
				(FragmentActivity) context, R.string.wait) {

			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context,
					Integer... params) throws Exception {
				usefulList = ComModel2.getMyCouponList(context, currPage,">","1",amount+"");
				return usefulList;
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
//					String is_use =  SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_IS_USE, "");
					String c_id =  SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_C_ID, "");
					String c_last_time =  SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_C_LAST_TIME, "0");
					String end_date =  SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_END_DATE, "0");
					boolean isHave = false;
					if(flag){
					
						for (int i = 0; i < result.size(); i++) {
							if(((Integer)result.get(i).get("id")+"").equals(c_id)){
								isHave = true;
								break;
							}
						} 
						//因为金券可使用条件降低    手动添加可以用的金券      字段必须和解析类 中 createMyCouponListNew 一致
						if("1".equals(is_open)&&"0".equals(is_use)&&!isHave&&(Double.valueOf(c_price)+0.01)<=jinquan){
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("end_date", end_date);
							map.put("c_last_time", c_last_time);
							map.put("id", Integer.valueOf(c_id));
							map.put("is_use", "1");//优惠券未使用的 is_use =1
							map.put("c_price", Double.valueOf(c_price));
							map.put("is_open", is_open);
							result.add(0, map);
						}
						
						if(result != null && (result.size() == 0 || result.isEmpty())){
							account_nodata.setVisibility(View.VISIBLE);
							lv_common.setVisibility(View.GONE);
						}
						mAdapter.addItemFirst(result!=null?result:null);
					}else{
						mAdapter.addItemLast(result!=null?result:null);
					}
					
					mAdapter.notifyDataSetChanged();
					lv_common.onRefreshComplete();
				}
			}

		}.execute(currPage);
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.bt_confirm){
			// TODO Auto-generated method stub
			if(null == selectUsefulMap){
				ToastUtil.showShortText(fatherActivity, "请选择优惠券");
				return;
			}
			Intent intent = new Intent();
			intent.putExtra("selectUseful", selectUsefulMap);
			fatherActivity.setResult(2001, intent);
			fatherActivity.finish();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		/*selectPosition = position;
		// TODO Auto-generated method stub
		int childCount = parent.getChildCount();
		for(int i=0; i<=childCount ; i++){
			if(i==position){
				parent.getChildAt(i).setBackgroundColor(220000);
			}else{
				parent.getChildAt(i).setBackgroundColor(Color.WHITE);
			}
		}*/
		ImageView imgSelect = (ImageView) view.findViewById(R.id.img_select);
		imgSelect.setImageResource(R.drawable.youhuiquan_selected);
		ListView listView = (ListView)parent;
		HashMap<String, Object> map = (HashMap<String, Object>) listView.getItemAtPosition(position);
		if("1".equals(map.get("is_use").toString()) && (Long.parseLong(map.get("c_last_time").toString()) > System.currentTimeMillis())){
			selectUsefulMap = (HashMap<String, Object>) listView.getItemAtPosition(position);
		}
		
		if(null == selectUsefulMap){
			ToastUtil.showShortText(fatherActivity, "请选择优惠券");
			return;
		}
		Intent intent = new Intent();
		intent.putExtra("selectUseful", selectUsefulMap);
		if("1".equals(is_open)&&"0".equals(is_use)&&position == 0&&(Double.valueOf(c_price)+0.01)<=jinquan){
			intent.putExtra("isJinQuan", true);
		}
		fatherActivity.setResult(2001, intent);
		fatherActivity.finish();
		
//		mAdapter.notifyDataSetChanged();
		
	}


}
