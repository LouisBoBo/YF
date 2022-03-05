package com.yssj.ui.activity.shopdetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.MatchNavLeft;
import com.yssj.custom.view.MatchNavRigth;
import com.yssj.custom.view.MatchSharePopupwindow;
import com.yssj.custom.view.MyMatchTitleView;
import com.yssj.custom.view.MyMatchTitleView.OnCheckTitleLentener;
import com.yssj.custom.view.PointAlarmView;
import com.yssj.custom.view.ScrollPagerMatchList;
import com.yssj.custom.view.ScrollPagerMatchList.MyOnRefreshLintener;
import com.yssj.data.YDBHelper;
import com.yssj.entity.MatchShop;
import com.yssj.entity.MatchShop.AttrList;
import com.yssj.entity.MatchShop.CollocationShop;
import com.yssj.entity.Shop;
import com.yssj.model.ComModel2;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.activity.ShopCartNewNewActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.XunBaoMatchDialog;
import com.yssj.ui.fragment.ItemMatchDetailsFragment;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YunYingTongJi;
import com.yssj.utils.sqlite.ShopCartDao;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 搭配购 详情页面
 * 
 * @author Administrator
 * 
 */
public class MatchDetailsActivity extends BasicActivity implements OnCheckTitleLentener, View.OnClickListener,
		MyOnRefreshLintener{

	private Context mContext;
	private String collocation_code,collocation_pic;// 搭配商品编号,搭配商品图片URL
	private ViewPager mViewPager;
	private MyMatchTitleView myMatchTitleView;
	private List<HashMap<String, String>> listTitle;
	private List<HashMap<String, String>> listTitleName;
	private MatchShop mMatchShop;// 详情页数据
	private ItemMatchDetailsFragment[] fragments;
	// private ItemFragment[] fragments;
	private PagerAdapter mAdapter;
	private int currentPosition = 0;
	private LinearLayout mSpreadLL;
	private boolean isSpread; // 展开或收起状态 默认false 收起
	private TextView mContentTv, mSpreadTv;
	private ImageView mSpreadIv;
	private LinearLayout mBack;// 返回
	private ImageView mConnect;// 联系客服
//	private ImageView imgCartIv;// 购物车
	private TextView tv_cart_count;// 购物车 数量
	private TextView tv_time_count_down;// 购物车倒计时
	private LinearLayout matchBuyTv;// 搭配购
//	private ImageView mShareIv;// 分享
	private ScrollPagerMatchList mScrollView;
	private SAsyncTask<String, Void, MatchShop> matchShopTask;
	private String[] typeIds;
	private String sort_name[];

	private TextView mAddTimeTv;
	private TextView mNameTv;
	private ImageView mMainImgIv;
	private RelativeLayout containsRl;
	private String shop_code;// 搭配购的商品 每个单件商品的shop_code拼接 用“,”隔开
	private int screenWidth;// 屏幕宽度
	public static int heigth;
	int optionFlag[];// 存放是否存在搭配 0 存在 1 不存在
	private boolean optFlag;// 是否存在搭配购
	private LinearLayout mTop;
	private String collocation_remark;
	private TextView mDiscountText;
	private Timer timer;
	private TimerTask task;
	private long recLen;
	private int cartCount;
//	private double SharePrice;// 分享显示总价
	private boolean isForceLookMatch;
	private LinearLayout redShare;
	private ImageView moneyShare;
	private java.text.DecimalFormat pFormate;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.activity_match_detalis);
		getActionBar().hide();
		mContext = this;
		pFormate=new DecimalFormat("#0.0");
		Intent intent = getIntent();
		if (intent != null) {
			collocation_code = intent.getStringExtra("collocation_code");
			collocation_pic = intent.getStringExtra("collocation_pic");
			isForceLookMatch = intent.getBooleanExtra("isForceLookMatch", false);
		}
		initView();
		String matchDiscount = SharedPreferencesUtil.getStringData(mContext, Pref.DPZHEKOU,"0.95");
		if("0".equals(matchDiscount)){
			matchDiscount = "0.95";
		}
		mDiscountText.setText("下单立享"+new DecimalFormat("#0.#").format(Double.parseDouble(matchDiscount)*10)+"折优惠");
		if (!TextUtils.isEmpty(collocation_code)) {
			queryMatchDetails();
		}

		setEvent();
	}

	private void initView() {
		findViewById(R.id.root_view).setBackgroundColor(Color.WHITE);
		mTop = (LinearLayout) this.findViewById(R.id.top_view_match);
		mAddTimeTv = (TextView) findViewById(R.id.title_date_tv);
		mDiscountText = (TextView) findViewById(R.id.match_discount_text);
		mNameTv = (TextView) findViewById(R.id.name_tv);
		mMainImgIv = (ImageView) findViewById(R.id.image_main_iv);
//		mRel = (RelativeLayout) findViewById(R.id.main_image_rl);
		containsRl = (RelativeLayout)findViewById(R.id.Match_contains_rl);
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		heigth = dm.heightPixels;
		// 设置图片的宽高比 加载图片
		ViewGroup.LayoutParams lp = mMainImgIv.getLayoutParams();
		lp.width = screenWidth;
		lp.height = LayoutParams.WRAP_CONTENT;
		mMainImgIv.setLayoutParams(lp);
		mMainImgIv.setMaxWidth(screenWidth);
		mMainImgIv.setMaxHeight(screenWidth); // 图片高度设置为屏幕的宽度

		mSpreadLL = (LinearLayout) findViewById(R.id.zhankai_ll);
		mSpreadLL.setOnClickListener(this);
		mBack = (LinearLayout) findViewById(R.id.img_back);
		mBack.setOnClickListener(this);
		mConnect = (ImageView) findViewById(R.id.connect_iv);
		mConnect.setOnClickListener(this);
		mContentTv = (TextView) findViewById(R.id.content_tv);
		// int lineCount = mContentTv.getLineCount(); //显示内容小于3行不显示展示按钮
		// if (lineCount<=3) {
		// mSpreadLL.setVisibility(View.GONE);
		// }

		mSpreadTv = (TextView) findViewById(R.id.zhankai_tv);
		mSpreadIv = (ImageView) findViewById(R.id.zhankai_iv);
//		imgCartIv = (ImageView) findViewById(R.id.image_cart_iv);
		findViewById(R.id.image_cart_rl).setOnClickListener(this);
//		imgCartIv.setOnClickListener(this);
		matchBuyTv = (LinearLayout) findViewById(R.id.tv_dapei_buy);
		matchBuyTv.setOnClickListener(this);
		matchBuyTv.setClickable(false);

		tv_cart_count = (TextView) findViewById(R.id.cart_count_tv);
		tv_time_count_down = (TextView) findViewById(R.id.tv_time_count_down);
		tv_time_count_down.setVisibility(View.GONE);
//		mShareIv = (ImageView) findViewById(R.id.img_fenxiang);
		// mShareIv.setOnClickListener(this);
		redShare = (LinearLayout) findViewById(R.id.red_share_ll);
		moneyShare = (ImageView) findViewById(R.id.money_share_iv);
//		redShare.setVisibility(View.GONE);
		findViewById(R.id.fenxiang_rl).setOnClickListener(this);
		findViewById(R.id.ray_bottom).setBackgroundColor(Color.WHITE);
		mScrollView = (ScrollPagerMatchList) findViewById(R.id.myView);
		mScrollView.setOnRefreshLintener(this);

		mViewPager = (ViewPager) findViewById(R.id.content_viewpager);
//		mAdapter = new PagerAdapter(getSupportFragmentManager());
//		mViewPager.setAdapter(mAdapter);
		
		myMatchTitleView = (MyMatchTitleView) findViewById(R.id.title);
		listTitle = new ArrayList<HashMap<String, String>>();
		listTitleName = new ArrayList<HashMap<String, String>>();
		
		YDBHelper helper = new YDBHelper(mContext);
		String sql = "select * from sort_info where p_id = 0 and is_show = 1 order by sequence";
		listTitleName = helper.query(sql);
		myMatchTitleView.setCheckLintener(this);
//		if(mMatchShop!=null){
//			MatchDetailsActivity.this.findViewById(R.id.all).setVisibility(View.VISIBLE);
//		}else{
//			MatchDetailsActivity.this.findViewById(R.id.all).setVisibility(View.INVISIBLE);
//		}
		if(!TextUtils.isEmpty(collocation_pic)){
//			SetImageLoader.initImageLoader(mContext, mMainImgIv, collocation_pic, "!450");
			PicassoUtils.initImage(mContext, collocation_pic+"!450", mMainImgIv);
		}
		
	}

	private List<CollocationShop> mCollocationShopsList;// 搭配购 中的商品的集合
	private List<AttrList> attrList;// 搭配商品的属性集合
	private String mCollocationName;
	/**
	 * 请求搭配购 商品详情
	 */
	private void queryMatchDetails() {
		matchShopTask = new SAsyncTask<String, Void, MatchShop>(this, R.string.wait) {
			

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				LoadingDialog.show(MatchDetailsActivity.this);
			}

			@Override
			protected MatchShop doInBackground(FragmentActivity context, String... params) throws Exception {
				return ComModel2.getMatchDetails(context, collocation_code);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, MatchShop result, Exception e) {
				super.onPostExecute(context, result, e);
				if (e != null || result == null) {
					return;
				}
//				findViewById(R.id.root_view).setBackgroundColor(Color.WHITE);
//				MatchDetailsActivity.this.findViewById(R.id.all).setVisibility(View.VISIBLE);
				mMatchShop = result;
				if(isForceLookMatch){
					showDialog(result);	
				}
//				String collocation_code = result.getCollocation_code();
				if(TextUtils.isEmpty(collocation_pic)){
					collocation_pic = result.getCollocation_pic();	
//					SetImageLoader.initImageLoader(mContext, mMainImgIv, collocation_pic, "!450");
					PicassoUtils.initImage(mContext,  collocation_pic+"!450", mMainImgIv);
				}
				mCollocationName = result.getCollocation_name();
				String add_time = result.getAdd_time();
				collocation_remark = result.getCollocation_remark();
				mContentTv.setText(collocation_remark);
				
//			    //根据高度来控制显示
				mSpreadLL.setVisibility(mContentTv.getLineCount() > 3 ? View.VISIBLE : View.GONE);
//				mContentTv.post(new Runnable() {
//		            @Override
//		            public void run() {
//		            	mSpreadLL.setVisibility(mContentTv.getLineCount() > 3 ? View.VISIBLE : View.GONE);
//		            }
//			     });
				if (!TextUtils.isEmpty(add_time)) {
					long addTimeLong = Long.parseLong(add_time);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
					mAddTimeTv.setText(sdf.format(addTimeLong).toString());
				}
				mNameTv.setText(mCollocationName);

//				SetImageLoader.initImageLoader(mContext, mMainImgIv, collocation_pic, "!450");

				List<CollocationShop> collocationShopList = result.getCollocation_shop();
				mCollocationShopsList = collocationShopList;
				List<AttrList> attrList = result.getAttrList();
				MatchDetailsActivity.this.attrList = attrList;
				optionFlag = new int[collocationShopList.size()];

//				for (int i = 0; i < collocationShopList.size(); i++) {
//					SharePrice += (collocationShopList.get(i).getShop_se_price()-(int)collocationShopList.get(i).getKickback());
//				}
				containsRl.removeAllViews();
				for (int i = 0; i < collocationShopList.size(); i++) {
					RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
							ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

					RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(
							ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

					String shop_code = (String) collocationShopList.get(i).getShop_code();
					String shop_name = (String) collocationShopList.get(i).getShop_name();
					double shop_se_price = collocationShopList.get(i).getShop_se_price();
					double kickback = collocationShopList.get(i).getKickback();
					int option_flag = collocationShopList.get(i).getOption_flag();
					double shop_x = collocationShopList.get(i).getShop_x();
					double shop_y = collocationShopList.get(i).getShop_y();
					optionFlag[i] = option_flag;

					if (i == 0) {
						shop_x = shop_x == 0 ? 0.45 : shop_x;
						shop_y = shop_y == 0 ? 0.35 : shop_y;

//						shop_y = shop_y < 0.2 ? 0.2 : shop_y;
//						shop_y = shop_y > 0.75 ? 0.75 : shop_y;
						setLeftView(shop_code, option_flag, i, param, param2, shop_name, shop_se_price,kickback, shop_x, shop_y);
					} else if (i == 1) {
						shop_x = shop_x == 0 ? 0.6 : shop_x;
						shop_y = shop_y == 0 ? 0.58 : shop_y;

//						shop_y = shop_y < 0.2 ? 0.2 : shop_y;
//						shop_y = shop_y > 0.75 ? 0.75 : shop_y;
						setRigthView(shop_code, option_flag, i, param, param2, shop_name, shop_se_price,kickback, shop_x,
								shop_y);
					}
				}

				for (int i = 0; i < optionFlag.length; i++) {
					if (optionFlag[i] == 0) {
						optFlag = true;// 只要有一件存在搭配
						break;
					}
				}
				// 只要有一件存在搭配 搭配购按钮就可以点击 否则 不可以点击
				if (!optFlag) {
					matchBuyTv.setBackgroundColor(Color.rgb(197, 197, 197));
					matchBuyTv.setClickable(false);
				} else {
					matchBuyTv.setBackgroundColor(Color.rgb(255, 63, 139));
					matchBuyTv.setClickable(true);
				}
				// 拼接搭配的商品的shop_code
				final StringBuffer sb = new StringBuffer();
				for (int j = 0; j < collocationShopList.size(); j++) {
					sb.append(collocationShopList.get(j).getShop_code());
					if (j != collocationShopList.size() - 1) {
						sb.append(",");
					}
				}
				shop_code = new String(sb);
				
				/**
				 * 添加相关商品
				 */
				String typeRelIds = result.getType_relation_ids();
				
				if (!TextUtils.isEmpty(typeRelIds)) {
					typeIds = typeRelIds.split(",");
					sort_name = new String[typeIds.length];
					for (int i = 0; i < sort_name.length; i++) {
						HashMap<String, String> hashMap = new HashMap<String, String>();
						for (int j = 0; j < listTitleName.size(); j++) {
							if(typeIds[i].equals(listTitleName.get(j).get("_id"))){
								sort_name[i] = 	listTitleName.get(j).get("sort_name");
								break;
							}
						}
						hashMap.put("sort_name", sort_name[i]);
						hashMap.put("icon", ",");
						hashMap.put("type_id", typeIds[i]);
						listTitle.add(hashMap);
					}
					myMatchTitleView.setData(listTitle);
					fragments = new ItemMatchDetailsFragment[listTitle.size()];
					// fragments = new ItemFragment[listTitle.size()];
					mAdapter = new PagerAdapter(getSupportFragmentManager());
					mViewPager.setAdapter(mAdapter);
				}

			}
		};
		matchShopTask.execute(collocation_code);
	}

	private void setLeftView(String shop_code, int option_flag, int i, RelativeLayout.LayoutParams param,
			RelativeLayout.LayoutParams param2, String shop_name, double shop_se_price,double kickback,double X, double Y) {
		MatchNavLeft matchNavLeft = new MatchNavLeft(mContext, shop_code,2,isForceLookMatch);
		matchNavLeft.setTextView(Shop.getShopNameStrNew(shop_name));
		matchNavLeft.measure(0, 0);
		param2.leftMargin = (int) (screenWidth * X - matchNavLeft.getMeasuredWidth() - 8);
//		param2.topMargin = (int) (screenWidth / 2 + (Y - 0.5) * screenWidth * 1.5 - matchNavLeft.getMeasuredHeight() / 2
		//图片宽高比为1
		param2.topMargin = (int) (screenWidth / 2 + (Y - 0.5) * screenWidth - matchNavLeft.getMeasuredHeight() / 2
				+ 8);
		matchNavLeft.setLayoutParams(param2);// 设置布局参数
		containsRl.addView(matchNavLeft);// RelativeLayout添加子View

		setAlarmPoint(X, Y);
		
		if (option_flag == 0) {
			matchNavLeft.getImgCart().setVisibility(View.VISIBLE);
			TextView tv = new TextView(mContext);
			tv.setBackgroundResource(R.drawable.pricetag);
			tv.setId(i);
//			tv.setText("¥" + pFormate.format(shop_se_price-(int)kickback));
			tv.setText("¥" + pFormate.format(shop_se_price*0.9));//显示九折价格
			tv.setTextColor(Color.WHITE);
			tv.setTextSize(9);
			tv.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
			tv.setPadding(2, 0, 2, 8);
			tv.measure(0, 0);
			param.leftMargin = (int) (screenWidth * X - tv.getMeasuredWidth() / 2);
//			param.topMargin = (int) (screenWidth / 2 + (Y - 0.5) * screenWidth * 1.5);
			//图片宽高比为1
			param.topMargin = (int) (screenWidth / 2 + (Y - 0.5) * screenWidth);
			tv.setLayoutParams(param);// 设置布局参数
			containsRl.addView(tv);// RelativeLayout添加子View

		} else if (option_flag == 1) {
			matchNavLeft.getImgCart().setVisibility(View.GONE);
			ImageView iv = new ImageView(mContext);
			iv.setBackgroundResource(R.drawable.red_point);
			iv.measure(0, 0);
			param.leftMargin = (int) (screenWidth * X - iv.getMeasuredWidth() / 2);
			//图片宽高比为1
			param.topMargin = (int) (screenWidth / 2 + (Y - 0.5) * screenWidth);
			iv.setLayoutParams(param);// 设置布局参数
			containsRl.addView(iv);// RelativeLayout添加子View
		}
	}

	/**
	 * 圆点波形闪烁
	 * @param X
	 * @param Y
	 */
	private void setAlarmPoint(double X, double Y) {
		ImageView iv = new ImageView(mContext);
		iv.setBackgroundResource(R.drawable.red_point);
		iv.measure(0, 0);
		
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(80,80);
		PointAlarmView pointView = new PointAlarmView(mContext);
		lp.leftMargin = (int) (screenWidth*X-40);
		//图片宽高比为1
		lp.topMargin = (int) (screenWidth/2+(Y-0.5)*screenWidth-40+iv.getMeasuredHeight()/2);
		pointView.setLayoutParams(lp);
		containsRl.addView(pointView);
	}

	private void setRigthView(String shop_code, int option_flag, int i, RelativeLayout.LayoutParams param,
			RelativeLayout.LayoutParams param2, String shop_name, double shop_se_price,double kickback,double X, double Y) {
		MatchNavRigth matchNavRigth = new MatchNavRigth(mContext, shop_code,2,isForceLookMatch);
		matchNavRigth.setTextView(Shop.getShopNameStrNew(shop_name));
		matchNavRigth.measure(0, 0);
		param2.rightMargin = 10;
		param2.leftMargin = (int) (screenWidth * X + 8);
		//图片宽高比为1
		param2.topMargin = (int) (screenWidth / 2 + (Y - 0.5) * screenWidth
				- matchNavRigth.getMeasuredHeight() / 2 + 8);
		matchNavRigth.setLayoutParams(param2);// 设置布局参数
		containsRl.addView(matchNavRigth);// RelativeLayout添加子View
		
		setAlarmPoint(X, Y);
		
		if (option_flag == 0) {
			matchNavRigth.getImgCart().setVisibility(View.VISIBLE);
			TextView tv = new TextView(mContext);
			tv.setBackgroundResource(R.drawable.pricetag);
			tv.setId(i + 500);
			tv.setText("¥" + pFormate.format(shop_se_price*0.9));//显示九折价格
			tv.setTextColor(Color.WHITE);
			tv.setTextSize(9);
			tv.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
			tv.setPadding(2, 0, 2, 8);
			tv.measure(0, 0);
			param.leftMargin = (int) (screenWidth * X - tv.getMeasuredWidth() / 2);
			//图片宽高比为1
			param.topMargin = (int) (screenWidth / 2 + (Y - 0.5) * screenWidth);
			tv.setLayoutParams(param);// 设置布局参数
			containsRl.addView(tv);// RelativeLayout添加子View

		} else if (option_flag == 1) {
			matchNavRigth.getImgCart().setVisibility(View.GONE);
			ImageView iv = new ImageView(mContext);
			iv.setBackgroundResource(R.drawable.red_point);
			iv.measure(0, 0);
			param.leftMargin = (int) (screenWidth * X - iv.getMeasuredWidth() / 2);
			//图片宽高比为1
			param.topMargin = (int) (screenWidth / 2 + (Y - 0.5) * screenWidth);
			iv.setLayoutParams(param);// 设置布局参数
			containsRl.addView(iv);// RelativeLayout添加子View
		}
	}

	/**
	 * 事件
	 */
	private void setEvent() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				myMatchTitleView.setPosition(arg0);
				currentPosition = arg0;
				if (null != ((ItemMatchDetailsFragment) mAdapter.getItem(arg0)).getmList()
						&& ((ItemMatchDetailsFragment) mAdapter.getItem(arg0)).getmList().getCount() != 0)
					((ItemMatchDetailsFragment) mAdapter.getItem(arg0)).getmList().setSelection(0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public void checkTitle(View v) {
		mViewPager.setCurrentItem(v.getId());
		currentPosition = v.getId();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.img_back) {
			onBackPressed();
			return;
		}

		// TextView 展开 收起
		if (v.getId() == R.id.zhankai_ll) {
			int tempHight = 0;//高度差
            int startHight=mContentTv.getHeight();  //起始高度
			if (!isSpread) {//展开
				isSpread = true;
				mSpreadTv.setText(R.string.shouqi);
				mSpreadIv.setImageResource(R.drawable.icon_shouqi);
				tempHight = mContentTv.getLineHeight() * mContentTv.getLineCount() - startHight;  //为正值，长文减去短文的高度差
			} else if (isSpread) {//收起
				isSpread = false;
				mSpreadTv.setText(R.string.zhankai);
				mSpreadIv.setImageResource(R.drawable.icon_zhankai);
				tempHight = mContentTv.getLineHeight() * 3 - startHight;//为负值，即短文减去长文的高度差
			}
			mContentTv.setHeight((int) (startHight + tempHight+mContentTv.getCompoundPaddingTop() + mContentTv.getCompoundPaddingBottom())+2);//原始长度+高度差
			mTop.measure(0, 0);
			LayoutParams params = mTop.getLayoutParams();
			params.height = (int) (mTop.getMeasuredHeight());
			mTop.setLayoutParams(params);	
			return;
		}
		//去登陆
		if (!YJApplication.instance.isLoginSucess()) {
			
			if (LoginActivity.instances != null){
				LoginActivity.instances .finish();
			}
			
			
			
			Intent intent = new Intent(mContext, LoginActivity.class);
			intent.putExtra("login_register", "login");
			((FragmentActivity) mContext).startActivity(intent);
			((FragmentActivity)mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
			return;
		}
		switch (v.getId()) {
		case R.id.connect_iv:// 联系客服
//			Intent intent_connect = new Intent(this, KeFuActivity.class);
//			intent_connect.putExtra("userId", SharedPreferencesUtil.getStringData(mContext, "kefuNB", "0"));
//			Bundle bundle_connect = new Bundle();
//			bundle_connect.putSerializable("mMatchShop", mMatchShop);
//			if (null == mMatchShop) {
//				ToastUtil.showShortText(mContext, "操作无效");
//				return;
//			}
//			intent_connect.putExtras(bundle_connect);
//			startActivity(intent_connect);


			WXminiAppUtil.jumpToWXmini(this);

			break;
//		case R.id.image_cart_iv:// 购物车
		case R.id.image_cart_rl:// 购物车父布局
			YunYingTongJi.yunYingTongJi(mContext, 108);
			Intent intent = new Intent(mContext, ShopCartNewNewActivity.class);
			intent.putExtra("where", "0");
			startActivityForResult(intent, 235);
			break;

		case R.id.tv_dapei_buy:// 搭配购
			if (null == mMatchShop) {
				return;
			}
			Intent intent2 = new Intent(this, MatchAttrActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("collocation_code", collocation_code);
			bundle.putString("shop_code", shop_code);
			bundle.putSerializable("colShopsList", (Serializable) mCollocationShopsList);
			bundle.putSerializable("attrList", (Serializable) attrList);
			intent2.putExtras(bundle);
			mContext.startActivity(intent2);
//			overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
//			MobclickAgent.onEvent(mContext, "joinshopcartclick");
			break;
		case R.id.fenxiang_rl:// 分享
			// case R.id.img_fenxiang:
//			MobclickAgent.onEvent(mContext, "matchdetails");
			if (null == mMatchShop) {
				ToastUtil.showShortText(mContext, "操作无效");
				return;
			}
			redShare.clearAnimation();
			moneyShare.clearAnimation();
			SharedPreferencesUtil.saveStringData(mContext, Pref.SHAREANIM, System.currentTimeMillis() + "");
			showMyPopwindou(this);
			break;
		default:
			break;
		}
	}

	/**
	 * 显示分享的MatchSharePopupwindow
	 * 
	 * @param context
	 */
	private void showMyPopwindou(FragmentActivity context) {
		double feedBack = 0;
		for (int i = 0; i < mMatchShop.getCollocation_shop().size(); i++) {
			String feedBackStr = new DecimalFormat("#0.0").format(mMatchShop.getCollocation_shop().get(i).getShop_se_price()*0.1);
			feedBack += Double.valueOf(feedBackStr);
			LogYiFu.e("feedBack", feedBack+"");
		}
		feedBack = Double.valueOf(new DecimalFormat("#0.0").format(feedBack));
		MatchSharePopupwindow popupwindow = new MatchSharePopupwindow(context, collocation_code, mCollocationName,feedBack,mCollocationShopsList);
		if (MatchDetailsActivity.this != null) {
			popupwindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
		}
	}

	/**
	 * 返回
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
		if (matchShopTask != null && !matchShopTask.isCancelled()) {
			matchShopTask.cancel(true);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		setShareAnim();
		HomeWatcherReceiver.registerHomeKeyReceiver(mContext);
		SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TYPE, "1019");
		if (YJApplication.instance.isLoginSucess()) {
			queryCartCount();
		} else {
			tv_cart_count.setVisibility(View.GONE);
		}
	}
	
	private void setShareAnim() {
		
		SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String shareAnim = SharedPreferencesUtil.getStringData(mContext, Pref.SHAREANIM, "0");
		long shareAnimTime = Long.valueOf(shareAnim);
		boolean isRoate = "0".equals(shareAnim) || !df.format(new Date()).equals(df.format(new Date(shareAnimTime)));
		if (!isRoate) {
			return;
		}
		RotateAnimation ani1 = new RotateAnimation(0f,35f,Animation.RELATIVE_TO_SELF, 1.0f,Animation.RELATIVE_TO_SELF,1.0f);
		ScaleAnimation ani2 = new  ScaleAnimation(1.0f, 0.85f, 1.0f, 0.85f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		final AnimationSet set = new AnimationSet(mContext, null);
		ani1.setDuration(270);
		ani1.setRepeatMode(Animation.REVERSE);
//		ani1.setRepeatCount(1);
		ani1.setFillAfter(false);
//		ani1.setStartOffset(1500);
		ani2.setDuration(270);
		ani2.setRepeatMode(Animation.RESTART);
//		ani2.setRepeatCount(Integer.MAX_VALUE);
		ani2.setFillAfter(false);
//		ani2.setStartOffset(1500);
		
		set.addAnimation(ani1);
		set.addAnimation(ani2);
		set.setStartOffset(600);
//		redShare.setAnimation(set);
		redShare.startAnimation(set);
		
		final RotateAnimation ani3 = new RotateAnimation(-12f,10f,Animation.RELATIVE_TO_SELF, 1.0f,Animation.RELATIVE_TO_SELF,1.0f);
		ani3.setDuration(55);
		ani3.setRepeatMode(Animation.REVERSE);
		ani3.setRepeatCount(2);
		ani3.setFillAfter(true);
		final RotateAnimation ani4 = new RotateAnimation(-6f,6f,Animation.RELATIVE_TO_SELF, 1.0f,Animation.RELATIVE_TO_SELF,1.0f);
		ani4.setDuration(45);
		ani4.setRepeatMode(Animation.REVERSE);
		ani4.setRepeatCount(1);
		ani4.setFillAfter(false);
		
		ani1.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				moneyShare.startAnimation(ani3);
				set.setStartOffset(1300);
			}
		});
		ani3.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				moneyShare.startAnimation(ani4);
			}
		});
		ani4.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				redShare.startAnimation(set);
			}
		});
		
	
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		HomeWatcherReceiver.unregisterHomeKeyReceiver(mContext);
	}

	//
	// // 查询购物车数量
	// private void queryCartCount() {
	//
	// new SAsyncTask<Void, Void, String>(this, R.string.wait) {
	//
	// @Override
	// protected String doInBackground(FragmentActivity context,
	// Void... params) throws Exception {
	// return ComModel2.getShopCartCount(context);
	// }
	//
	// @Override
	// protected boolean isHandleException() {
	// return true;
	// }
	//
	// @Override
	// protected void onPostExecute(FragmentActivity context,
	// String count, Exception e) {
	// super.onPostExecute(context, count, e);
	// if (e != null || count == null) {
	// return;
	// }
	// if (Integer.parseInt(count) > 0) {
	// imgCartCountTv.setVisibility(View.VISIBLE);
	// imgCartCountTv.setText(count);
	// } else {
	// imgCartCountTv.setVisibility(View.GONE);
	// }
	//
	// }
	// }.execute();
	//
	// }

	// 查询购物车数量并显示倒计时
	private void queryCartCount() {

//		new SAsyncTask<Void, Void, HashMap<String, Object>>(this, R.string.wait) {
//
////			@Override
////			protected void onPreExecute() {
////				// TODO Auto-generated method stub
////				super.onPreExecute();
////				LoadingDialog.show(MatchDetailsActivity.this);
////			}
//			@Override
//			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//					throws Exception {
//				return ComModel2.getMatchShopCartCount(context);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (e != null || result == null) {
//					return;
//				}
				ShopCartDao dao=new ShopCartDao(mContext);
//				cartCount =dao.queryCartCount(mContext);
				cartCount = dao.queryCartCommonCount(mContext);
//				String count = (String) result.get("cart_count");
//				cartCount = Integer.valueOf(count);
				if (cartCount > 0) {
					cartCount = cartCount>99?99:cartCount;
					tv_cart_count.setVisibility(View.VISIBLE);
					tv_cart_count.setText(cartCount+"");
//					tv_time_count_down.setVisibility(View.VISIBLE);
				} else {
					tv_cart_count.setVisibility(View.GONE);
//					tv_time_count_down.setVisibility(View.GONE);
				}

				Long sTime = new Date().getTime();// 系统当前时间
				Long sDeadline = Long.valueOf(SharedPreferencesUtil.getStringData(mContext, Pref.SHOPCART_COMMON_TIME, sTime+""));// 商品过期时间
//				Long sTime = (Long) result.get("s_time");// 系统当前时间
//				Long sDeadline = (Long) result.get("s_deadline");// 商品过期时间

				// 购物车倒计时
//				if (sDeadline-sTime>0&&cartCount > 0) {
//					tv_time_count_down.setVisibility(View.VISIBLE);
//					String c_time_cart = DateFormatUtils.format(sDeadline, "yyyy-MM-dd HH:mm:ss");
//					String s_time = DateFormatUtils.format(sTime, "yyyy-MM-dd HH:mm:ss");
//
//					try {
//						if (timer != null) {
//							timer.cancel();
//						}
//						task = new TimerTask() {
//							@Override
//							public void run() {
//
//								runOnUiThread(new Runnable() { // UI thread
//									@Override
//									public void run() {
//										takeTime();
//									}
//								});
//							}
//						};
//						timer = new Timer();
//						timer.schedule(task, 0, 1000); // 显示倒计时
//
//						SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//						Date d1 = df1.parse(c_time_cart);
//						Date d2 = df1.parse(s_time);
//						long diff = d1.getTime() - d2.getTime();
//						recLen = diff;
//					} catch (ParseException e1) {
//						e1.printStackTrace();
//					}
//				} else {// 负数代表或0没有加入购物车
//					tv_time_count_down.setVisibility(View.GONE);
//				}
//			}
//		}.execute();
	}

	// 购物车用的
	private void takeTime() {
		recLen -= 1000;
		String days;
		String hours;
		String minutes;
		String seconds;
		long minute = recLen / 60000;
		long second = (recLen % 60000) / 1000;
		if (minute >= 60) {
			long hour = minute / 60;
			minute = minute % 60;
			if (hour >= 24) {
				long day = hour / 24;
				hour = hour % 24;
				if (day < 10) {
					days = "0" + day;
				} else {
					days = "" + day;
				}
				if (hour < 10) {
					hours = "0" + hour;
				} else {
					hours = "" + hour;
				}
				if (minute < 10) {
					minutes = "0" + minute;
				} else {
					minutes = "" + minute;
				}
				if (second < 10) {
					seconds = "0" + second;
				} else {
					seconds = "" + second;
				}
				tv_time_count_down.setText("" + days + "天" + hours + "时" + minutes + "分" + seconds + "秒");
			} else {
				if (hour < 10) {
					hours = "0" + hour;
				} else {
					hours = "" + hour;
				}
				if (minute < 10) {
					minutes = "0" + minute;
				} else {
					minutes = "" + minute;
				}
				if (second < 10) {
					seconds = "0" + second;
				} else {
					seconds = "" + second;
				}
				tv_time_count_down.setText("" + hours + "时" + minutes + "分" + seconds + "秒");
			}
		} else if (minute >= 10 && second >= 10) {
			tv_time_count_down.setText("" + minute + ":" + second + "");
		} else if (minute >= 10 && second < 10) {
			tv_time_count_down.setText("" + minute + ":0" + second + "");
		} else if (minute < 10 && second >= 10) {
			tv_time_count_down.setText("0" + minute + ":" + second + "");
		} else {
			tv_time_count_down.setText("0" + minute + ":0" + second + "");
		}
		if (recLen < 0) {
			tv_time_count_down.setText("00:00");
			tv_time_count_down.setVisibility(View.GONE);
			tv_cart_count.setVisibility(View.GONE);// 数量小圆点消失
			task.cancel();
		}
	}

	/**
	 * 此处应当继承FragmentStatePagerAdapter
	 * 在处理数据量较大的页面应当使用FragmentStatePagerAdapter，而不是FragmentPagerAdapter
	 */
	public class PagerAdapter extends FragmentStatePagerAdapter {
		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return listTitle.get(position).get("sort_name");
		}

		@Override
		public int getCount() {
			return listTitle.size();
		}

		@Override
		public Fragment getItem(int position) {
			ItemMatchDetailsFragment fragment = fragments[position];
			if (fragment == null) {
				fragment = ItemMatchDetailsFragment.newInstances(position, listTitle.get(position).get("type_id"),
						collocation_code, mContext);
				fragments[position] = fragment;
			}
			return fragment;
		}

	}
	
	/**
	 * 强制浏览签到寻宝提示框 每天只显示一次
	 */
	private void showDialog(MatchShop result) {
		SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String forceLook = SharedPreferencesUtil.getStringData(this, Pref.FORCELOOKMATCHDET, "0");
		long forceLookTime = Long.valueOf(forceLook);
		if ("0".equals(forceLook) || !df.format(new Date()).equals(df.format(new Date(forceLookTime)))) {
			List<CollocationShop> collocationShopList = result.getCollocation_shop();
			String shop_name = (String) collocationShopList.get(0).getShop_name();
			double shop_x = collocationShopList.get(0).getShop_x();
			double shop_y = collocationShopList.get(0).getShop_y(); 
			XunBaoMatchDialog dialog = new XunBaoMatchDialog(this,shop_name,shop_x+"",shop_y+"",-1);
			dialog.show();
			SharedPreferencesUtil.saveStringData(this, Pref.FORCELOOKMATCHDET, System.currentTimeMillis() + "");
		}

	}
	
	@Override
	public void onRefreshlintener() {
//		if (!TextUtils.isEmpty(collocation_code)) {
//			queryMatchDetails();
//		}
		mScrollView.refreshDone();
	}
}
