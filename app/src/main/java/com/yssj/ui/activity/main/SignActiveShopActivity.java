package com.yssj.ui.activity.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.data.YDBHelper;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.adpter.SignAcitveShopAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.LeaveDialog;
import com.yssj.ui.dialog.SignActiveShopExplainDialog;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
/**
 * 签到活动商品
* @author Administrator
* @date 2016年10月12日下午3:28:17
 */
public class SignActiveShopActivity extends BasicActivity implements OnClickListener {	
	private PullToRefreshListView r_list_view;
	private SignAcitveShopAdapter mAdapter;
	private View img_back;
	private TextView tv_shuoming;
	private int mType = 1;// 1：初始化数据；2：加载更多数据
	private int index = 1;
	private String singvalue;
	private ImageView fight_groups_icon;
	public static SignActiveShopActivity instance;

	private TextView tvNew, tvHot, tvDesc, tvAsc;
	private View horizontal_title_ll;
	private String is_hot = null, is_new = "is_new", order_by_price = null;

	private boolean isNotScan;//疯狂新衣节（不是浏览任务）
    private String doIconId;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		ActionBar aBar = getActionBar();
//		aBar.setDisplayHomeAsUpEnabled(true);
//		aBar.hide();
		context = this;
		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.sign_active_shop);
        isNotScan = getIntent().getBooleanExtra("isCrazy",false);
		if(isNotScan){
			doIconId = getIntent().getStringExtra("doIconId");//分类类目ID
		}else if(SignListAdapter.doType == 4||SignListAdapter.doType == 19){//件数数浏览
			doIconId = getIntent().getStringExtra("doIconId");//分类类目ID
		}else if(SignListAdapter.doType == 5){//分钟数浏览
			doIconId =SignListAdapter.fenzhongIconID.get(YConstance.SCAN_SHOP_TIME);//分类类目ID
		}
		initView();
	}
	
	private void initView() {
		instance = this;
		img_back = findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		r_list_view = (PullToRefreshListView) findViewById(R.id.r_list_view);
		r_list_view.setMode(Mode.BOTH);
		tv_shuoming = (TextView) findViewById(R.id.task_explain_tv);
		tv_shuoming.setOnClickListener(this);
		fight_groups_icon = (ImageView) findViewById(R.id.fight_groups_icon);
		fight_groups_icon.setOnClickListener(this);

        mAdapter = new SignAcitveShopAdapter(this,isNotScan);
		r_list_view.setAdapter(mAdapter);

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

		tvHot.setVisibility(View.GONE);

		if(!SignListAdapter.isSignComplete&&(SignListAdapter.doType == 4||SignListAdapter.doType == 19)&&!isNotScan){
			showDialog();
		}
		if(!SignListAdapter.isSignComplete&&(SignListAdapter.doType == 5&&!isNotScan)){
			showTimesDialog();
		}
		if((SignListAdapter.doType == 4||SignListAdapter.doType == 19||SignListAdapter.doType == 5)&&!isNotScan){//浏览任务时候才有浏览任务说明
			tv_shuoming.setVisibility(View.VISIBLE);
		}else{
			tv_shuoming.setVisibility(View.GONE);
		}
		if(SignListAdapter.doType==4&&!isNotScan){//强制浏览个数
			//获取当前任务的需要浏览的次数
			String value = SignListAdapter.doValue;
			String values [] = value.split(",");
			if(values.length>1){
				singvalue = values[1];
				if(!Pattern.compile("^\\+?[1-9][0-9]*$").matcher(singvalue).find()){
					singvalue=""+SignListAdapter.doNum;
				}
				
			}else{
				singvalue=""+SignListAdapter.doNum;
			}
		}
		if (SignListAdapter.doType == 19&&!isNotScan) {// 新增强制浏览个数 奖励提现额度

			// 获取当前任务的需要浏览的次数
			String value = SignListAdapter.doValue;
			String values[] = value.split(",");
			if (values.length > 1) {
				singvalue = values[1];
				if (!Pattern.compile("^\\+?[1-9][0-9]*$").matcher(singvalue).find()) {
					singvalue = "10";
				}
			}
			else {
				singvalue = "10";
			}
//			haisheng.setText("每浏览"+singvalue+"件衣服，即得"+SignListAdapter.jiangliValue+"元提现额度哦，快去领取吧~");

			if(TextUtils.isEmpty(SignListAdapter.doNeedCount)||"-1".equals(SignListAdapter.doNeedCount)
					||"null".equals(SignListAdapter.doNeedCount)){
				//没有完成过一次浏览 没有调用过签到接口
			}else{
				int forcelookLimitNum = Integer
						.parseInt(
								SharedPreferencesUtil
										.getStringData(SignActiveShopActivity.this,
												SignListAdapter.signIndex + Pref.ISFORCELOOKLIMITNUM
														+ YCache.getCacheUser(context).getUser_id(),
												"0"));
				int forceLookLimitNumTemp = ((SignListAdapter.doNum-Integer.parseInt(SignListAdapter.doNeedCount))*Integer.parseInt(singvalue)
						+forcelookLimitNum%Integer.parseInt(singvalue));//根据还剩任务完成次数（调用接口次数 也就是 领取奖励次数）来判断并覆盖还剩浏览次数
				SharedPreferencesUtil.saveStringData(SignActiveShopActivity.this,
						SignListAdapter.signIndex + Pref.ISFORCELOOKLIMITNUM
								+ YCache.getCacheUser(context).getUser_id(),
						"" + forceLookLimitNumTemp);
			}
		}
		setListViewRefresh();
		initData(index + "",  is_new, order_by_price);
		
	}
	
	private void setListViewRefresh(){
		r_list_view.setMode(Mode.BOTH);
		r_list_view.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				index = 1;
				mType=1;
				initData(index + "",  is_new, order_by_price);
//				r_list_view.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//				r_list_view.onRefreshComplete();
				index++;
				mType = 2;
				initData(index + "",  is_new, order_by_price);
			}

		});
	
	}
	
	/**
	 *每天只显示一次
	 */
	private void showDialog() {
		SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String taskExplain = SharedPreferencesUtil.getStringData(context, Pref.SIGN_ACTIVE_SHOP_TASK_EXPLAIN, "0");
		long forceLookTime = Long.parseLong(taskExplain);
		if ("0".equals(taskExplain) || !df.format(new Date()).equals(df.format(new Date(forceLookTime)))) {
			new SignActiveShopExplainDialog(context).show();
			SharedPreferencesUtil.saveStringData(context, Pref.SIGN_ACTIVE_SHOP_TASK_EXPLAIN, System.currentTimeMillis() + "");
		}

	}

	/**
	 *每天只显示一次 浏览分钟
	 */
	private void showTimesDialog() {
		SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String taskExplain = SharedPreferencesUtil.getStringData(context, Pref.SIGN_ACTIVE_SHOP_TASK_EXPLAIN_TIME, "0");
		long forceLookTime = Long.parseLong(taskExplain);
		if ("0".equals(taskExplain) || !df.format(new Date()).equals(df.format(new Date(forceLookTime)))) {
			SignActiveShopExplainDialog dialog = new SignActiveShopExplainDialog(context);
			dialog.show();
			dialog.setText("浏览美衣达到指定时间即可完成任务~");
			SharedPreferencesUtil.saveStringData(context, Pref.SIGN_ACTIVE_SHOP_TASK_EXPLAIN_TIME, System.currentTimeMillis() + "");
		}

	}
	private void initData(final String index,  final String is_new,
						  final String order_by_price){
		final int pageSize = 10;
		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {
			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
					throws Exception {

				return ComModel2.getSignActiveShopListSort(context,index,pageSize+"",is_new,is_hot,order_by_price);
			}

			@Override
			protected boolean isHandleException() {

				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
//				if(result==null){
//					result = new ArrayList<HashMap<String,Object>>();
//				}
//				for (int i = 0; i < 10; i++) {
//				HashMap<String, Object> map = new HashMap<String, Object>();
//				map.put("shop_code",  "MAOQ15423130");
//				map.put("def_pi",  "LIEnDjTm_600_900.jpg");
//				map.put("virtual_sales",  "106");
//				map.put("shop_name",  "简约百搭绣花拉链刺绣短裙");
//				map.put("shop_se_price",  "68");
//				map.put("shop_price",  "100");
//				map.put("supp_label_id",  "12");
//				map.put("supp_label",  "ZARA制造商出品de");
//				result.add(map);
//				}
				if (e == null) {
					if (mType == 1) {
						if (result == null || result.size() == 0) {
							r_list_view.setVisibility(View.GONE);
						} else {
							r_list_view.setVisibility(View.VISIBLE);
						}
						mAdapter.setData(result);

					} else if (mType == 2) {

						if(result == null ||result.size()==0){
							ToastUtil.showShortText(context, "已没有更多商品了哦~");
						}else{
							mAdapter.addItemLast(result);
							r_list_view.getRefreshableView().smoothScrollBy(100, 20);
						}
					}
				}
				r_list_view.onRefreshComplete();
			}

		}.execute();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_back:
//			if(SignListAdapter.doType == 4){
//				countTaskBack();
//			}else if(SignListAdapter.doType ==5){
//				timeTaskBack();	
//			}else{
//				onBackPressed();
//			}
			onBackPressed();
			
			break;
		case R.id.task_explain_tv:
			if((SignListAdapter.doType==4||SignListAdapter.doType==19)&&!isNotScan){
				new SignActiveShopExplainDialog(this).show();
			}else if(SignListAdapter.doType==5&&!isNotScan){
				SignActiveShopExplainDialog dialog = new SignActiveShopExplainDialog(this);
				dialog.show();
				dialog.setText("浏览美衣达到指定时间即可完成任务~");
			}
			break;
//		case R.id.fight_groups_icon://拼团广场
////			Intent intent = new Intent(context, GroupsSquareActivity.class);
////			context.startActivity(intent);
////			overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//			break;
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
	
	@Override
	public void onBackPressed() {
		if((SignListAdapter.doType == 4||SignListAdapter.doType == 19)&&!SignListAdapter.isSignComplete&&!isNotScan){
			countTaskBack();
		}else if(SignListAdapter.doType ==5&&!SignListAdapter.isSignComplete&&!isNotScan){
			timeTaskBack();	
		}else{
			super.onBackPressed();
			overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
		}
	}
//	// 修改系统返回键
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		if(SignListAdapter.doType == 4){
//			countTaskBack();
//		}else if(SignListAdapter.doType ==5){
//			timeTaskBack();	
//		}else{
//			onBackPressed();
//		}
//		return true;
//
//	}
	
	/**
	 * 浏览个数任务返回
	 */
	private void countTaskBack(){
		int signActiveShopNum=0;
		int forcelookLimitNum=0;
		SimpleDateFormat dfNum = new SimpleDateFormat("yyyy-MM-dd");
		if (SignListAdapter.doType ==4) {
			String nowTimeSignActiveShop =  SharedPreferencesUtil.getStringData(SignActiveShopActivity.this, "signActiveShopNowTime"+YCache.getCacheUser(context).getUser_id(), "");
			signActiveShopNum=  Integer.valueOf(SharedPreferencesUtil.getStringData(SignActiveShopActivity.this, SignListAdapter.signIndex+"signActiveShopNum"+YCache.getCacheUser(context).getUser_id(), "0"));
			if(!dfNum.format(new Date()).equals(nowTimeSignActiveShop)){
				signActiveShopNum = 0;//不是同一天点击分享任务    或者不是同一个用户 就  或者取出的数据大于浏览次数  计数分享重置
			}
		} else if (SignListAdapter.doType ==19){
			String nowTimeForcelookLimit = SharedPreferencesUtil.getStringData(
					SignActiveShopActivity.this,
					"nowTimeForcelookLimit" + YCache.getCacheUser(context).getUser_id(), "");
			forcelookLimitNum = Integer
					.parseInt(
							SharedPreferencesUtil
									.getStringData(SignActiveShopActivity.this,
											SignListAdapter.signIndex + Pref.ISFORCELOOKLIMITNUM
													+ YCache.getCacheUser(context).getUser_id(),
											"0"));
			if (!dfNum.format(new Date()).equals(nowTimeForcelookLimit)) {
				forcelookLimitNum = 0;// 不是同一天点击分享任务
				// 或者不是同一个用户 就
				// 或者取出的数据大于浏览次数
				// 重新开始计数分享的次数
			}
		}

		if (signActiveShopNum >=Integer.parseInt(singvalue)
				||(SignListAdapter.doType ==19 && forcelookLimitNum/Integer.parseInt(singvalue) +1 > SignListAdapter.doNum)) {
			finish();
			overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
		} else {
			// Toast.makeText(context, "亲，在逛一会才能领取现金哦",
			// Toast.LENGTH_SHORT).show();
			final LeaveDialog leaveDialog = new LeaveDialog(this);
			leaveDialog.show();

			View btn_left = leaveDialog.findViewById(R.id.btn_left);
			btn_left.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(leaveDialog!=null){
						leaveDialog.dismiss();
					}
					finish();
					overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
				}
			});
		}
	}
	
	
	/**
	 * 浏览分钟数返回时候的弹窗提示
	 */
	private void timeTaskBack(){
		if(SignListAdapter.isForceLookTimeOut){//分钟数时间到了
			finish();
			overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
		}else{
			final LeaveDialog leaveDialog = new LeaveDialog(this);
			leaveDialog.show();
			leaveDialog.setContentText("你正在进行浏览商品任务，浏览时长还未完成，你可以选择再逛逛当前页面，或者去浏览其它商品，浏览时长达到任务要求即可完成任务喔~");
			leaveDialog.setButtonText("不了，谢谢", "其他商品");
			View btn_left = leaveDialog.findViewById(R.id.btn_left);
			btn_left.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(leaveDialog!=null){
						leaveDialog.dismiss();
					}
					finish();
					overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
				}
			});
			View btn_right = leaveDialog.findViewById(R.id.btn_right);
			btn_right.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					CommonUtils.finishActivity(MainMenuActivity.instances);

					Intent intent = new Intent((Activity) context, MainMenuActivity.class);
					intent.putExtra("toYf", "toYf");
					context.startActivity(intent);
				}
			});
		}
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
		dbHelp = new YDBHelper(context);
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
			String banner = listData.get(0).get("banner");
			if("null".equals(banner)||TextUtils.isEmpty(banner)) {
				imgBanner.setVisibility(View.GONE);
			}else{
				PicassoUtils.initImage(this,banner+"!560",imgBanner);
				imgBanner.setVisibility(View.VISIBLE);
			}
		}
    }

}
