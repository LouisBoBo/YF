package com.yssj.ui.activity.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.data.YDBHelper;
import com.yssj.model.ComModel2;
import com.yssj.ui.adpter.ForceLookAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 疯狂新衣节列表跳转进来界面
 */
public class ForceLookMadActivity extends BasicActivity {

	private PullToRefreshListView r_list_view;

	private ForceLookAdapter mAdapter;
	public static ForceLookMadActivity instance;

	private int index = 1;


	private int mType = 1;// 1：初始化数据；2：加载更多数据

	private String title;

	private String pinJievalue = "";


	// private String checkId;

	private RelativeLayout rl_yuefanbei;


	private LinearLayout llNodata;
	// 所以当每次返回的数据<pageSize的时候

	private ImageButton img_btn_filter;
	private TextView tv_shuoming;
//	private String is_new = null, order_by_price = null;
	private String is_hot = null, is_new = "is_new", order_by_price = null;
	private TextView tv_cart_count_Force;
	private TextView mTitle;//
	private TextView tvNew, tvHot, tvDesc, tvAsc;
	private View horizontal_title_ll;
	private String doIconId;
    private boolean isNotScan;

	// isComplete为true
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		instance = this;


//		aBar.hide();
		isNotScan = getIntent().getBooleanExtra("isCrazy",false);

		doIconId = getIntent().getStringExtra("doIconId");//分类类目ID
		setContentView(R.layout.force_look);

		r_list_view = (PullToRefreshListView) findViewById(R.id.r_list_view);
		headView = LayoutInflater.from(this).inflate(R.layout.view_scan_header,null);
		addHeadeViewBanner();
		horizontal_title_ll = headView.findViewById(R.id.horizontal_title_ll);
		tvNew = (TextView) headView.findViewById(R.id.create_is_new);
		tvHot = (TextView) headView.findViewById(R.id.create_is_hot);
		tvDesc = (TextView) headView.findViewById(R.id.create_price_desc);
		tvAsc = (TextView) headView.findViewById(R.id.create_price_asc);
		tvHot.setOnClickListener(this);
		tvNew.setOnClickListener(this);
		tvDesc.setOnClickListener(this);
		tvAsc.setOnClickListener(this);
		if ("collection=browse_shop".equals(pinJievalue)){
			tvHot.setVisibility(View.GONE);
		}

		tv_shuoming = (TextView) findViewById(R.id.tv_shuoming);
//		tv_shuoming.setOnClickListener(this);
		mTitle = (TextView) findViewById(R.id.tv_forcelook_title);

		findViewById(R.id.img_back).setOnClickListener(this);
		img_btn_filter = (ImageButton) findViewById(R.id.img_btn_filter);
//		img_btn_filter.setOnClickListener(this);

		rl_yuefanbei = (RelativeLayout) findViewById(R.id.rl_yuefanbei);
		tv_cart_count_Force = (TextView) findViewById(R.id.tv_cart_count_Force);


		llNodata = (LinearLayout) findViewById(R.id.ll_nodata);
		findViewById(R.id.btn_view_allcircle).setVisibility(View.GONE);
		((TextView) findViewById(R.id.tv_no_join)).setText("暂无数据");
		r_list_view.setMode(Mode.BOTH);


//		try {
//			if ("collection=browse_shop".equals(pinJievalue)){
//				horizontal_title_ll.setVisibility(View.GONE);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}

		mAdapter = new ForceLookAdapter(this, isNotScan);
		r_list_view.setAdapter(mAdapter);

		// 如果是签到跳过来的要修改布局

		setListViewRefresh();

		initData(index + "", is_new, order_by_price);




        mTitle.setText(title);
		rl_yuefanbei.setVisibility(View.GONE);
		img_btn_filter.setVisibility(View.GONE);
		tv_cart_count_Force.setVisibility(View.GONE);
		tv_shuoming.setVisibility(View.GONE);

	}

	private void setListViewRefresh() {
		r_list_view.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				mType = 1;
				index = 1;
				initData(index + "", is_new, order_by_price);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				index++;
				mType = 2;
				initData(index + "",  is_new, order_by_price);
			}
		});

	}

	private void initData(final String index,  final String is_new,
			final String order_by_price) {

		// 下面是真数据
		final int pageSize = 10;
		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {
			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
					throws Exception {
					//热门推首拿强制浏览的数据
					if ("collection=browse_shop".equals(pinJievalue)){
						return ComModel2.getForceLook(context, pageSize, index,is_new,is_hot,order_by_price);
					}else{
						//热卖推荐商品
						return ComModel2.getSignShop(context, index, pinJievalue,is_new,is_hot,order_by_price);
					}
			}

			@Override
			protected boolean isHandleException() {

				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);

				if (e == null) {
					if (mType == 1) {
						if (result == null || result.size() == 0) {
							llNodata.setVisibility(View.VISIBLE);
							r_list_view.setVisibility(View.GONE);
						} else {
							llNodata.setVisibility(View.GONE);
							r_list_view.setVisibility(View.VISIBLE);
						}
						mAdapter.setData(result,false);

					} else if (mType == 2) {
						if (result == null || result.size() == 0) {
							ToastUtil.showShortText(context, "已没有更多商品了哦~");
						}else{
							mAdapter.addItemLast(result);
							r_list_view.getRefreshableView().smoothScrollBy(100, 20);
						}
					}
				}
				r_list_view.onRefreshComplete();
			}

		}.execute(index);
	}



	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.img_back: // 返回
			onBackPressed();
			break;


		case R.id.create_is_new:// 上新
			tvNew.setTextColor(Color.parseColor("#FF3F8B"));
			tvHot.setTextColor(Color.parseColor("#3E3E3E"));
			tvDesc.setTextColor(Color.parseColor("#3E3E3E"));
			tvAsc.setTextColor(Color.parseColor("#3E3E3E"));
			is_new = "is_new";
			is_hot = null;
			order_by_price = null;
			mType = 1;
			index = 1;
			initData(index + "", is_new, order_by_price);
			break;
		case R.id.create_is_hot:// 热销
			tvHot.setTextColor(Color.parseColor("#FF3F8B"));
			tvNew.setTextColor(Color.parseColor("#3E3E3E"));
			tvDesc.setTextColor(Color.parseColor("#3E3E3E"));
			tvAsc.setTextColor(Color.parseColor("#3E3E3E"));
			is_hot = "is_hot";
			is_new = null;
			order_by_price = null;
			mType = 1;
			index = 1;
			initData(index + "",  is_new, order_by_price);
			break;
		case R.id.create_price_asc:// 价格
			tvAsc.setTextColor(Color.parseColor("#FF3F8B"));
			tvNew.setTextColor(Color.parseColor("#3E3E3E"));
			tvHot.setTextColor(Color.parseColor("#3E3E3E"));
			tvDesc.setTextColor(Color.parseColor("#3E3E3E"));
			is_new = null;
			is_hot = null;
			order_by_price = "asc";
			mType = 1;
			index = 1;
			initData(index + "",  is_new, order_by_price);
			break;
		case R.id.create_price_desc:// 价格
			tvDesc.setTextColor(Color.parseColor("#FF3F8B"));
			tvNew.setTextColor(Color.parseColor("#3E3E3E"));
			tvHot.setTextColor(Color.parseColor("#3E3E3E"));
			tvAsc.setTextColor(Color.parseColor("#3E3E3E"));
			is_new = null;
			is_hot = null;
			order_by_price = "desc";
			mType = 1;
			index = 1;
			initData(index + "",  is_new, order_by_price);
			break;
		default:
			break;
		}
	}

	// private void explain() {
	// // TODO Auto-generated method stub
	// SignExplain signExplain = new SignExplain(this, id_new);
	// signExplain.show();
	// SharedPreferencesUtil.saveBooleanData(context, "isfirst", false);
	// }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}









	@Override
	public void onBackPressed() {
			super.onBackPressed();
			overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
	}



	private int width;
	private  View  headView;
	private ImageView imgBanner;
	private YDBHelper dbHelp;
	private List<HashMap<String,String>> listData;
	private void  addHeadeViewBanner(){
		r_list_view.getRefreshableView().addHeaderView(headView);
		imgBanner = (ImageView) headView.findViewById(R.id.img_head);
		imgBanner.setVisibility(View.GONE);
		dbHelp = new YDBHelper(this);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		ViewGroup.LayoutParams lp = imgBanner.getLayoutParams();
		lp.width =width;
		lp.height =lp.width*200/340;
		imgBanner.setLayoutParams(lp);
//		imgBanner.setImageResource(R.drawable.bg_group_shop);
		listData= new ArrayList<HashMap<String,String>>();
		String sql = "select * from shop_group_list where _id = "+doIconId;
		listData =  dbHelp.query(sql);
		if(listData!=null&&listData.size()>0){
			title = listData.get(0).get("app_name");
			if(title.isEmpty()){
				title = "热卖";
			}
			pinJievalue = listData.get(0).get("value");
			String banner = listData.get(0).get("banner");
			if("null".equals(banner)||TextUtils.isEmpty(banner)) {
				imgBanner.setVisibility(View.GONE);
			}else{
				PicassoUtils.initImage(this,banner+"!560",imgBanner);
				imgBanner.setVisibility(View.VISIBLE);
			}
		}else{
			title = "热卖";

		}
	}

}
