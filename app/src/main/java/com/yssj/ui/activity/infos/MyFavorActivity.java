package com.yssj.ui.activity.infos;

import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.MyScrollView;
import com.yssj.entity.Like;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.adpter.MyFavorStaggeredAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.MineIfoFragment;
import com.yssj.ui.fragment.MyFavorProductListFragment;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

/***
 * 我的最爱
 * 
 * @author Administrator
 */
public class MyFavorActivity extends BasicActivity {

	private FragmentManager fm;
	private FragmentTransaction ft;

	private MyFavorProductListFragment fFragment;

	private TextView tv_all_count;
	private MyScrollView lazy_scroll;
	
	private TextView tv_edit;

	private MyFavorStaggeredAdapter mAdapter;
	
	private LinearLayout ll_handler_bottom,mylike;
	private Button btn_cancle, btn_delete;

	// 标志是否失效，1表示失效、0表示未失效
	private String isDel = "0";

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		// 隐藏头部标题
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();

		setContentView(R.layout.my_favour_list_fragment);
		// lazy_scroll = (MyScrollView) findViewById(R.id.lazy_scroll);

		fm = this.getSupportFragmentManager();
		ft = fm.beginTransaction();
		fFragment = new MyFavorProductListFragment();
		ft.add(R.id.myfavour_list_fragment, fFragment);
		ft.commit();

		mylike = (LinearLayout) (findViewById(R.id.mylike));
		mylike.setBackgroundColor(Color.WHITE);
		// 设置我的最爱数量
		tv_all_count = (TextView) findViewById(R.id.tv_all_count);
		countSum = getIntent().getIntExtra("count", 0);
		tv_all_count.setText("全部分类（" + countSum + "）");

		findViewById(R.id.img_back).setOnClickListener(this);
		findViewById(R.id.tv_edit).setOnClickListener(this);
		findViewById(R.id.tv_out_time).setOnClickListener(this);
		
		tv_edit = (TextView) findViewById(R.id.tv_edit);
		
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
//			intent = new Intent(getApplicationContext(), MineIfoFragment.class);
//			finish();
			onBackPressed();
			break;
	/*	case R.id.tv_edit:
			// Toast.makeText(getApplicationContext(), "点我编辑",
			// Toast.LENGTH_LONG).show();
			mAdapter = fFragment.getAdapter();
			if (flag) {
				mAdapter.setTop(true);
				flag = false;
			} else {
				mAdapter.setTop(false);
				flag = true;
			}
			// fFragment.initData("1");
			mAdapter.notifyDataSetChanged();
			break;*/
			
		case R.id.tv_edit:
			if (ll_handler_bottom.getVisibility() == View.GONE) {
				ll_handler_bottom.setVisibility(View.VISIBLE);
				tv_edit.setText("取消");
				mAdapter.setEdit(true);
			} else {
				ll_handler_bottom.setVisibility(View.GONE);
				tv_edit.setText("编辑");
				mAdapter.setEdit(false);
			}

			break;

		case R.id.btn_cancle:
			ll_handler_bottom.setVisibility(View.GONE);
			
			tv_edit.setText("编辑");
			mAdapter.setEdit(false);
			
			break;

		case R.id.btn_delete:
			
			List<Like> list=mAdapter.getCheckList();
			if(list.isEmpty()){
				tv_edit.setText("编辑");
				mAdapter.setEdit(false);
				ll_handler_bottom.setVisibility(View.GONE);
				return;
			}
			selectCount=list.size();
			for (int i = 0; i < list.size(); i++) {
				Shop_code+=list.get(i).getShop_code()+",";
			}
			Shop_code.substring(0, Shop_code.length()-1);
			deleteMyFavorData();
			break;
			
		case R.id.tv_out_time: // 失效
			mAdapter = fFragment.getAdapter();
			if ("1".equals(isDel)) {
				isDel = "0";
				mAdapter.filterSX(isDel);
				fFragment.setDel(isDel);
				((TextView)findViewById(R.id.tv_out_time)).setText("失效");
			} else {
				isDel = "1";
				mAdapter.filterSX(isDel);
				fFragment.setDel(isDel);
				((TextView)findViewById(R.id.tv_out_time)).setText("全部");
			}
			if(ll_handler_bottom.getVisibility()==View.VISIBLE){
				ll_handler_bottom.setVisibility(View.GONE);
				
				tv_edit.setText("编辑");
				mAdapter.setEdit(false);
			} 
			//mAdapter.notifyDataSetChanged();
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
//				Like like = (Like) mAdapter.getItem(position);
//				
//				Shop_code += like.getShop_code() + ",";
//
//				mAdapter.getCheckMap().remove(i);
//				mAdapter.remove(position);
//				
//				selectCount ++ ;
//			}
//		}
//		if (!TextUtils.isEmpty(Shop_code)) {
//			Shop_code = Shop_code.substring(0, Shop_code.lastIndexOf(","));
//			deleteMyFavorData();
//			mAdapter.notifyDataSetChanged();
//		}
//
//	}

	private String Shop_code = "" ;
	private Integer countSum;	// 总数
	private int selectCount ;	// 所选中要删除的数量

	/**
	 * 删除所选中的最爱
	 */
	private void deleteMyFavorData() {
		new SAsyncTask<Integer, Void, ReturnInfo>(this, null, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Integer... params) throws Exception {

				return ComModel.deleteMyFavor(context, Shop_code);
			}

			@Override
			protected void onPostExecute(ReturnInfo result) {
				super.onPostExecute(result);
				if (result != null && "1".equals(result.getStatus())) {
					ToastUtil.showShortText(MyFavorActivity.this,result.getMessage());
					tv_edit.setText("编辑");
					if("0".equals(isDel)){
						countSum = countSum - selectCount;	
					}
					
					selectCount = 0;
					
					tv_all_count.setText("全部分类（" + countSum + "）");
					
					if(countSum == 0&&"0".equals(isDel)){
						fFragment.showNoData();
					}
					String str=SharedPreferencesUtil.getStringData(context, ""+YCache.getCacheUser(context).getUser_id(), "");
					String strs[]=Shop_code.split(",");
					for (int i = 0; i < strs.length; i++) {
						str=str.replace(strs[i], "");
					}
					SharedPreferencesUtil.saveStringData(context, ""+YCache.getCacheUser(context).getUser_id(), str);
				} else {
					ToastUtil.showShortText(MyFavorActivity.this,
							"糟糕，出错了~~~");
					return;
				}
				Shop_code = "";

				ll_handler_bottom.setVisibility(View.GONE);
				fFragment.setType(1);
				fFragment.initData("1");
				mAdapter.setEdit(false);
			}

		}.execute();
	}

}
