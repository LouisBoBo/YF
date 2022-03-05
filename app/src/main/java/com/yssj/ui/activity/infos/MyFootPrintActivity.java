package com.yssj.ui.activity.infos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.adpter.MyFootPrintStaggeredAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.MineIfoFragment;
import com.yssj.ui.fragment.MyFootPrintProductListFragment;
import com.yssj.utils.ToastUtil;

/***
 * @author Administrator 我的足迹
 */
public class MyFootPrintActivity extends BasicActivity implements
		OnClickListener {

	private FragmentManager fm;
	private FragmentTransaction ft;

	private MyFootPrintProductListFragment fFragment;

	private Button btn_eidt;
	private LinearLayout img_back;

	private Button btn_sx;

	private TextView tv_all_count;

	private LinearLayout ll_handler_bottom,myfoot;
	private Button btn_cancle, btn_delete;

	private MyFootPrintStaggeredAdapter mAdapter;

	private String isDel = "2"; // 标志是否失效，1表示失效，空表示未全部,1表示没有失效

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏头部标题
//		aBar.hide();

		setContentView(R.layout.my_footprint_list_fragment);
		myfoot = (LinearLayout) findViewById(R.id.myfoot);
		myfoot.setBackgroundColor(Color.WHITE);
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		btn_eidt = (Button) findViewById(R.id.btn_eidt);
		btn_eidt.setOnClickListener(this);

		btn_sx = (Button) findViewById(R.id.btn_sx);
		btn_sx.setOnClickListener(this);
		
		fm = this.getSupportFragmentManager();
		ft = fm.beginTransaction();
		fFragment = new MyFootPrintProductListFragment();
		ft.add(R.id.myfootprint_list_fragment, fFragment);
		ft.commit();

		// 设置我的足迹数量
		tv_all_count = (TextView) findViewById(R.id.tv_all_count);
		countSum = getIntent().getIntExtra("myStepCount", 0);
		tv_all_count.setText("全部分类（" + countSum + "）");

		ll_handler_bottom = (LinearLayout) findViewById(R.id.ll_handler_bottom);

		btn_cancle = (Button) findViewById(R.id.btn_cancle);
		btn_cancle.setOnClickListener(this);

		btn_delete = (Button) findViewById(R.id.btn_delete);
		btn_delete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		mAdapter = fFragment.getAdapter();
		switch (v.getId()) {
		case R.id.img_back:
			onBackPressed();
			
			break;
		case R.id.btn_eidt:
			if (ll_handler_bottom.getVisibility() == View.GONE) {
				ll_handler_bottom.setVisibility(View.VISIBLE);
				btn_eidt.setText("取消");
				mAdapter.setEdit(true);

			} else {
				ll_handler_bottom.setVisibility(View.GONE);
				btn_eidt.setText("编辑");
				mAdapter.setEdit(false);
			}

			break;

		case R.id.btn_cancle:
			ll_handler_bottom.setVisibility(View.GONE);
//			mAdapter.configCheckMap(false, false);
			mAdapter.setEdit(false);
			btn_eidt.setText("编辑");
			break;

		case R.id.btn_delete:
//			handlerMyStepsDelete();
			List<HashMap<String, Object>> list=mAdapter.getCheckList();
			if(list.isEmpty()){
				mAdapter.setEdit(false);
				ll_handler_bottom.setVisibility(View.GONE);
				btn_eidt.setText("编辑");
				return;
			}
			selectCount=list.size();
			if(list.size()==1){
				Shop_code=(String) list.get(0).get("shop_code");
				deleteMyStepsData();
				return;
			}
			for (int i = 0; i < list.size(); i++) {
				Shop_codes+=(String) list.get(i).get("shop_code")+",";
			}
			Shop_codes=Shop_codes.substring(0, Shop_codes.length()-1);
			deleteMyStepsData();
			break;

		case R.id.btn_sx:
			if ("1".equals(isDel)) {
//				mAdapter.filterSX(isDel);
				isDel = "2";
				fFragment.setDel(isDel);
				btn_sx.setText("失效");
			} else {
//				mAdapter.filterSX(isDel);
				isDel = "1";
				fFragment.setDel(isDel);
				btn_sx.setText("全部");
			}
			break;

		default:
			break;
		}
	}
	
//	/**
//	 * 当在我的记录页面点击删除的时候
//	 */
//	private void handlerMyStepsDelete() {
//
//		/*
//		 * 删除算法最复杂,拿到checkBox选择寄存map
//		 */
//		Map<Integer, Boolean> map = mAdapter.getCheckMap();
//
//		// 获取当前的数据数量
//		int count = mAdapter.getCount();
//
//		// 进行遍历
//		for (int i = 0; i < count; i++) {
//
//			// 因为List的特性,删除了2个item,则3变成2,所以这里要进行这样的换算,才能拿到删除后真正的position
//			int position = i - (count - mAdapter.getCount());
//
//			if (map.get(i) != null && map.get(i)) {
//
//				Map<String, Object> mapData = (Map<String, Object>) mAdapter
//						.getItem(position);
//				Shop_codes += (String) mapData.get("shop_code") + ",";
//
//				mAdapter.getCheckMap().remove(i);
//				mAdapter.remove(position);
//				
//				selectCount++;
//
//			}
//		}
//		if (!TextUtils.isEmpty(Shop_codes)) {
//			if (Shop_codes.substring(0, Shop_codes.lastIndexOf(",")).contains(
//					",")) {
//				Shop_codes = Shop_codes.substring(0,
//						Shop_codes.lastIndexOf(","));
//				Shop_code = "";
//			} else {
//				Shop_code = Shop_codes
//						.substring(0, Shop_codes.lastIndexOf(","));
//				Shop_codes = "";
//			}
//
//			deleteMyStepsData();
//			mAdapter.notifyDataSetChanged();
//		}
//
//	}

	private String Shop_code = "", Shop_codes = "";
	private Integer countSum;	// 总数
	private int selectCount ;	// 所选中要删除的数量
	
	
	/**
	 * 删除所选中的足迹
	 */
	private void deleteMyStepsData() {
		new SAsyncTask<Integer, Void, ReturnInfo>(this, null, 0) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Integer... params) throws Exception {
				//ComModel.deleteMyFavor(context, Shop_code);
				return ComModel.deleteMyStep(context, Shop_code, Shop_codes);
			}

			@Override
			protected void onPostExecute(ReturnInfo result) {
				super.onPostExecute(result);
				if (result != null && "1".equals(result.getStatus())) {
					ToastUtil.showShortText(MyFootPrintActivity.this,result.getMessage());
					btn_eidt.setText("编辑");
					if("2".equals(isDel)){
						countSum = countSum - selectCount;
					}
					selectCount = 0;
					
					tv_all_count.setText("全部分类（" + countSum + "）");
					if(countSum == 0&&"2".equals(isDel)){
						fFragment.showNoData();
					}
				} else {
					ToastUtil.showShortText(MyFootPrintActivity.this,
							"糟糕，出错了~~~");
					return;
				}
				Shop_code = "";
				Shop_codes = "";

				ll_handler_bottom.setVisibility(View.GONE);
//				isDel = "";
				fFragment.setDel(isDel);
				mAdapter.setEdit(false);
			}

		}.execute();
	}

}
