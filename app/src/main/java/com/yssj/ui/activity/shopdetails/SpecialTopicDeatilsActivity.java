package com.yssj.ui.activity.shopdetails;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.ItemView;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.MyListView;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.main.MoreSubjectActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.YunYingTongJi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 专题详情
 * 
 * @author lifeng
 *
 */
public class SpecialTopicDeatilsActivity extends BasicActivity implements OnClickListener {
	private TextView mTvTopTime, mTvTopTitleFrist, mTvTopTitleSecond, mTvBottomTitleFrist1, mTvBottomTitleSecond1,
			mTvBottomTitleFrist2, mTvBottomTitleFrist3, mTvBottomTitleSecond2, mTvBottomTitleSecond3;// 图片上几个文字的控件
	private TextView mTVDescribe;// 中间专题描述
	private MyListView mListView;// 相关商品
	private MyListView mHotListView;// 热门推荐商品
	private ImageView mIvTop, mIvBottom1, mIvBottom2, mIvBottom3;// 几张图片（图片比例3:2）
	private int width;
	private TextView mBtnMoreTopic;// 更多专题
	private LinearLayout mBack;
	private Context mContext;
	private List<HashMap<String, Object>> dataList;// 商品列表
	private List<HashMap<String, Object>> dataListHot;// 商品列表
	private List<HashMap<String, String>> moreTopicList;// 更多专题列表
	private DateAdapter mAdapter;// 专题相关商品用
	private DateAdapterHot mHotAdapter;// 热门推荐用
	private String code = "";// 专题编号
	private String more_collection_code = "";
	private boolean isforcelookMatch;// 判断是否是浏览搭配赚钱任务

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_special_topic_details);
		dataList = new LinkedList<HashMap<String, Object>>();
		dataListHot = new LinkedList<HashMap<String, Object>>();
		moreTopicList = new ArrayList<HashMap<String, String>>();
		width = this.getResources().getDisplayMetrics().widthPixels;
		mContext = this;
		code = getIntent().getStringExtra("collocation_code");
		isforcelookMatch = getIntent().getBooleanExtra("isforcelookMatch", false);
		initView();
		initData();
	}

	private void initView() {
		mBack = (LinearLayout) findViewById(R.id.img_back);
		mBack.setOnClickListener(this);
		mBtnMoreTopic = (TextView) findViewById(R.id.topic_btn_more);
		mBtnMoreTopic.setOnClickListener(this);
		mTvTopTime = (TextView) findViewById(R.id.topic_tv_time);
		mTvTopTitleFrist = (TextView) findViewById(R.id.topic_tv_title_frist);
		mTvBottomTitleFrist1 = (TextView) findViewById(R.id.topic_tv_title_frist1);
		mTvBottomTitleFrist2 = (TextView) findViewById(R.id.topic_tv_title_frist2);
		mTvBottomTitleFrist3 = (TextView) findViewById(R.id.topic_tv_title_frist3);
		mTvTopTitleSecond = (TextView) findViewById(R.id.topic_tv_title_second);
		mTvBottomTitleSecond1 = (TextView) findViewById(R.id.topic_tv_title_second1);
		mTvBottomTitleSecond2 = (TextView) findViewById(R.id.topic_tv_title_second2);
		mTvBottomTitleSecond3 = (TextView) findViewById(R.id.topic_tv_title_second3);
		mTVDescribe = (TextView) findViewById(R.id.topic_tv_describe);
		mListView = (MyListView) findViewById(R.id.topic_listview);
		mListView.setFocusable(false);
		mHotListView = (MyListView) findViewById(R.id.hot_listview);
		mHotListView.setFocusable(false);
		mIvTop = (ImageView) findViewById(R.id.topic_iv_top);
		mIvBottom1 = (ImageView) findViewById(R.id.topic_iv_bottom1);
		mIvBottom2 = (ImageView) findViewById(R.id.topic_iv_bottom2);
		mIvBottom3 = (ImageView) findViewById(R.id.topic_iv_bottom3);
		mIvTop.getLayoutParams().height = width * 2 / 3;
		mIvBottom1.getLayoutParams().height = width * 2 / 3;
		mIvBottom2.getLayoutParams().height = width * 2 / 3;
		mIvBottom3.getLayoutParams().height = width * 2 / 3;
	}

	private void initData() {
		// mIvTop.setImageResource(R.drawable.guid_01);
		// mIvBottom1.setImageResource(R.drawable.guid_02);
		// mIvBottom2.setImageResource(R.drawable.guid_03);
		// mIvBottom3.setImageResource(R.drawable.guid_04);
		// 查询相关商品
		getTopicDetails();

		/// 查询热门推荐
		getHotSale();
	}

	private void getHotSale() {
		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e) {

					
					
					// 填充热门推荐商品
					dataListHot =  result;
					mHotAdapter = new DateAdapterHot(mContext);
					mHotListView.setAdapter(mHotAdapter);
					
					
					
				}
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
					throws Exception {

				if (YJApplication.instance.isLoginSucess()) {
					return ComModel2.getProductList1(context, "", "", "", "", 30, true);
				} else {
					return ComModel2.getFilterProductList2("", "", context, "", null,"",0,"","",true);
					
//					ublic static List<HashMap<String, Object>> getFilterProductList2(String title, String id, Context context,
//							String index, HashMap<String, Object> mapRequest, String notType, int pageSize, String is_new,
//							String order_by_price,boolean isHotSale)
				}

			}
			
			

		}.execute();

	}

	public void getTopicDetails() {
		new SAsyncTask<String, Void, HashMap<String, Object>>((FragmentActivity) mContext, 0) {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				LoadingDialog.show((FragmentActivity) mContext);
			}

			@Override
			protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
					throws Exception {
				return ComModel2.getSpecialTopicDetils(mContext, code + "");
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
				super.onPostExecute(context, result);
				if (e != null) {
					return;
				}
				if (result != null && result.size() > 0) {
//					SetImageLoader.initImageLoader(context, mIvTop, "" + (String) result.get("collocation_pic"),
//							"");
					
					PicassoUtils.initImage(context, "" + (String) result.get("collocation_pic"), mIvTop);
					
					
					mTvTopTitleFrist.setText("" + (String) result.get("collocation_name"));
					mTvTopTitleSecond.setText("" + (String) result.get("collocation_name2"));
					long time = Long.parseLong("" + (String) result.get("add_time"));
					Date date = new Date(time);
					SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
					String tv_time = format.format(date);
					mTvTopTime.setText("" + tv_time);
					mTVDescribe.setText("" + (String) result.get("collocation_remark"));
					// 填充专题相关商品
					dataList = (LinkedList<HashMap<String, Object>>) result.get("shopList");
					mAdapter = new DateAdapter(mContext);
					mListView.setAdapter(mAdapter);

//					// 填充热门推荐商品
//					dataList = (LinkedList<HashMap<String, Object>>) result.get("shopList");
//					mHotAdapter = new DateAdapter(mContext);
//					mHotListView.setAdapter(mHotAdapter);

					moreTopicList = (List<HashMap<String, String>>) result.get("collocationList");
					if (moreTopicList.size() == 1) {
						findViewById(R.id.rl_bottom1).setVisibility(View.VISIBLE);
					} else if (moreTopicList.size() == 2) {
						findViewById(R.id.rl_bottom1).setVisibility(View.VISIBLE);
						findViewById(R.id.rl_bottom2).setVisibility(View.VISIBLE);
					} else if (moreTopicList.size() == 3) {
						findViewById(R.id.rl_bottom1).setVisibility(View.VISIBLE);
						findViewById(R.id.rl_bottom2).setVisibility(View.VISIBLE);
						findViewById(R.id.rl_bottom3).setVisibility(View.VISIBLE);
					}
					for (int i = 0; i < moreTopicList.size(); i++) {// 更多专题
						HashMap<String, String> moreList = new HashMap<String, String>();
						moreList = moreTopicList.get(i);
						more_collection_code = moreList.get("collocation_code");
						if (i == 0) {
//							SetImageLoader.initImageLoader(context, mIvBottom1, "" + moreList.get("collocation_pic"),
//									"!560");
							
							
							PicassoUtils.initImage(mContext, "" + moreList.get("collocation_pic")+"!560", mIvBottom1);
							
							
							mTvBottomTitleFrist1.setText("" + moreList.get("collocation_name"));
							mTvBottomTitleSecond1.setText("" + moreList.get("collocation_name2"));
							mIvBottom1.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// SpecialTopicDeatilsActivity.this.finish();
									Intent intent = new Intent(mContext, SpecialTopicDeatilsActivity.class);
									intent.putExtra("collocation_code",
											"" + moreTopicList.get(0).get("collocation_code"));
									if (SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete) {// 强制浏览个数并且是没有完成的任务
										intent.putExtra("isforcelookMatch", isforcelookMatch);
									}
									startActivity(intent);
									SpecialTopicDeatilsActivity.this.finish();

								}
							});
						} else if (i == 1) {
//							SetImageLoader.initImageLoader(context, mIvBottom2, "" + moreList.get("collocation_pic"),
//									"!560");
//							
							PicassoUtils.initImage(context,moreList.get("collocation_pic")+"!560", mIvBottom2);
							
							
							mTvBottomTitleFrist2.setText("" + moreList.get("collocation_name"));
							mTvBottomTitleSecond2.setText("" + moreList.get("collocation_name2"));
							mIvBottom2.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// SpecialTopicDeatilsActivity.this.finish();
									Intent intent = new Intent(mContext, SpecialTopicDeatilsActivity.class);
									intent.putExtra("collocation_code",
											"" + moreTopicList.get(1).get("collocation_code"));
									if (SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete) {// 强制浏览个数并且是没有完成的任务
										intent.putExtra("isforcelookMatch", isforcelookMatch);
									}
									startActivity(intent);
									SpecialTopicDeatilsActivity.this.finish();

								}
							});
						} else if (i == 2) {
//							SetImageLoader.initImageLoader(context, mIvBottom3, "" + moreList.get("collocation_pic"),
//									"!560");
							
							PicassoUtils.initImage(context,  moreList.get("collocation_pic")+"!560",mIvBottom3);
							
							
							mTvBottomTitleFrist3.setText("" + moreList.get("collocation_name"));
							mTvBottomTitleSecond3.setText("" + moreList.get("collocation_name2"));
							mIvBottom3.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// SpecialTopicDeatilsActivity.this.finish();
									Intent intent = new Intent(mContext, SpecialTopicDeatilsActivity.class);
									intent.putExtra("collocation_code",
											"" + moreTopicList.get(2).get("collocation_code"));
									if (SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete) {// 强制浏览个数并且是没有完成的任务
										intent.putExtra("isforcelookMatch", isforcelookMatch);
									}
									startActivity(intent);
									SpecialTopicDeatilsActivity.this.finish();

								}
							});
						}
					}
				}
				// mAdapter.notifyDataSetChanged();
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

		}.execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.topic_btn_more:
			Intent intent = new Intent(mContext, MoreSubjectActivity.class);
			if (SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete) {// 强制浏览个数并且是没有完成的任务
				intent.putExtra("isforcelookMatch", isforcelookMatch);
			}
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	// 这个地方可以让专题相关商品和热门推荐商品共用
	private class DateAdapter extends BaseAdapter {

		private Context context;

		private int picHeight;

		public DateAdapter(Context context) {
			super();
			this.context = context;
			int dp = DP2SPUtil.dp2px(context, 24);
			picHeight = (context.getResources().getDisplayMetrics().widthPixels - dp) / 2 * 900 / 600;
		}

		@Override
		public int getCount() {
			int count = 0;
			count += dataList.size() % 2 == 0 ? dataList.size() / 2 : dataList.size() / 2 + 1;
			if (count == 0) {
				count = 1;
			}
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

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			ItemViews items;
			if (view == null) {
				view = LayoutInflater.from(context).inflate(R.layout.item_fragment_adapter, null);
				items = new ItemViews();
				items.left = (com.yssj.custom.view.ItemView) view.findViewById(R.id.left);
				items.left.setHeight(picHeight);
				items.right = (com.yssj.custom.view.ItemView) view.findViewById(R.id.right);
				items.right.setHeight(picHeight);
				items.noData = (TextView) view.findViewById(R.id.noData);
				items.noData.getLayoutParams().height = MatchDetailsActivity.heigth;
				items.data = view.findViewById(R.id.data);
				view.setTag(items);
			} else {
				items = (ItemViews) view.getTag();
			}
			if (dataList.isEmpty()) {
				items.noData.setVisibility(view.VISIBLE);
				items.data.setVisibility(view.GONE);
			} else {
				items.noData.setVisibility(view.GONE);
				items.data.setVisibility(view.VISIBLE);
			}

			position = position * 2;

			if (dataList.size() > position) {
				// String url = (String) dataList.get(position).get("def_pic");
				items.left.iniView(dataList.get(position));
				items.left.setTag(position);
				items.left.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// YunYingTongJi.yunYingTongJi(context,
						// 66);//搭配详情页列表下商品图片
						int position = (Integer) arg0.getTag();
						addScanDataTo((String) dataList.get(position).get("shop_code"));
						// ItemMatchDetailsFragment.this.position = position;
						Intent intent = new Intent(mContext, ShopDetailsActivity.class);
						intent.putExtra("code", (String) dataList.get(position).get("shop_code"));
						// context.startActivity(intent);
						intent.putExtra("shopCarFragment", "shopCarFragment");

						if (SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete) {// 强制浏览个数并且是没有完成的任务
							intent.putExtra("isforcelookMatch", isforcelookMatch);
						}

						FragmentActivity activity = (FragmentActivity) mContext;
						activity.startActivity(intent);
						activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
					}
				});
			}
			if (dataList.size() > position + 1) {
				items.right.setVisibility(view.VISIBLE);
				// String url = (String) dataList.get(position +
				// 1).get("def_pic");
				items.right.iniView(dataList.get(position + 1));
				items.right.setTag(position + 1);
				items.right.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// YunYingTongJi.yunYingTongJi(context, 66);//
						// 搭配详情页列表下商品图片
						int position = (Integer) arg0.getTag();
						// mContext.getSharedPreferences("YSSJ_yf",
						// Context.MODE_PRIVATE).edit()
						// .putBoolean("isGoDetail", true).commit();
						addScanDataTo((String) dataList.get(position).get("shop_code"));
						// ItemMatchDetailsFragment.this.position = position;
						Intent intent = new Intent(mContext, ShopDetailsActivity.class);
						intent.putExtra("code", (String) dataList.get(position).get("shop_code"));
						// context.startActivity(intent);
						intent.putExtra("shopCarFragment", "shopCarFragment");

						if (SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete) {// 强制浏览个数并且是没有完成的任务
							intent.putExtra("isforcelookMatch", isforcelookMatch);
						}

						FragmentActivity activity = (FragmentActivity) mContext;
						activity.startActivity(intent);
						activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
					}
				});
			} else {
				items.right.setVisibility(View.INVISIBLE);
			}

			return view;
		}

	}

	
	// 这个地方可以让专题相关商品和热门推荐商品共用
		private class DateAdapterHot extends BaseAdapter {

			private Context context;

			private int picHeight;

			public DateAdapterHot(Context context) {
				super();
				this.context = context;
				int dp = DP2SPUtil.dp2px(context, 24);
				picHeight = (context.getResources().getDisplayMetrics().widthPixels - dp) / 2 * 900 / 600;
			}

			@Override
			public int getCount() {
				int count = 0;
				count += dataListHot.size() % 2 == 0 ? dataListHot.size() / 2 : dataListHot.size() / 2 + 1;
				if (count == 0) {
					count = 1;
				}
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

			@Override
			public View getView(int position, View view, ViewGroup arg2) {
				ItemViews items;
				if (view == null) {
					view = LayoutInflater.from(context).inflate(R.layout.item_fragment_adapter, null);
					items = new ItemViews();
					items.left = (com.yssj.custom.view.ItemView) view.findViewById(R.id.left);
					items.left.setHeight(picHeight);
					items.right = (com.yssj.custom.view.ItemView) view.findViewById(R.id.right);
					items.right.setHeight(picHeight);
					items.noData = (TextView) view.findViewById(R.id.noData);
					items.noData.getLayoutParams().height = MatchDetailsActivity.heigth;
					items.data = view.findViewById(R.id.data);
					view.setTag(items);
				} else {
					items = (ItemViews) view.getTag();
				}
				if (dataListHot.isEmpty()) {
					items.noData.setVisibility(view.VISIBLE);
					items.data.setVisibility(view.GONE);
				} else {
					items.noData.setVisibility(view.GONE);
					items.data.setVisibility(view.VISIBLE);
				}

				position = position * 2;

				if (dataListHot.size() > position) {
					// String url = (String) dataList.get(position).get("def_pic");
					items.left.iniView(dataListHot.get(position));
					items.left.setTag(position);
					items.left.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// YunYingTongJi.yunYingTongJi(context,
							// 66);//搭配详情页列表下商品图片
							int position = (Integer) arg0.getTag();
							addScanDataTo((String) dataListHot.get(position).get("shop_code"));
							// ItemMatchDetailsFragment.this.position = position;
							Intent intent = new Intent(mContext, ShopDetailsActivity.class);
							intent.putExtra("code", (String) dataListHot.get(position).get("shop_code"));
							// context.startActivity(intent);
							intent.putExtra("shopCarFragment", "shopCarFragment");

							if (SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete) {// 强制浏览个数并且是没有完成的任务
								intent.putExtra("isforcelookMatch", isforcelookMatch);
							}

							FragmentActivity activity = (FragmentActivity) mContext;
							activity.startActivity(intent);
							activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
						}
					});
				}
				if (dataListHot.size() > position + 1) {
					items.right.setVisibility(view.VISIBLE);
					// String url = (String) dataList.get(position +
					// 1).get("def_pic");
					items.right.iniView(dataListHot.get(position + 1));
					items.right.setTag(position + 1);
					items.right.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// YunYingTongJi.yunYingTongJi(context, 66);//
							// 搭配详情页列表下商品图片
							int position = (Integer) arg0.getTag();
							// mContext.getSharedPreferences("YSSJ_yf",
							// Context.MODE_PRIVATE).edit()
							// .putBoolean("isGoDetail", true).commit();
							addScanDataTo((String) dataListHot.get(position).get("shop_code"));
							// ItemMatchDetailsFragment.this.position = position;
							Intent intent = new Intent(mContext, ShopDetailsActivity.class);
							intent.putExtra("code", (String) dataListHot.get(position).get("shop_code"));
							// context.startActivity(intent);
							intent.putExtra("shopCarFragment", "shopCarFragment");

							if (SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete) {// 强制浏览个数并且是没有完成的任务
								intent.putExtra("isforcelookMatch", isforcelookMatch);
							}

							FragmentActivity activity = (FragmentActivity) mContext;
							activity.startActivity(intent);
							activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
						}
					});
				} else {
					items.right.setVisibility(View.INVISIBLE);
				}

				return view;
			}

		}
	/*
	 * 把浏览过的数据添加进数据库
	 */
	private void addScanDataTo(final String shop_code) {
		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) mContext) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
				return ComModel.addMySteps(context, shop_code);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
				super.onPostExecute(context, result, e);
			}

		}.execute();
	}

	private static class ItemViews {

		private ItemView left;
		private ItemView right;
		private TextView noData;
		private View data;
	}

}
