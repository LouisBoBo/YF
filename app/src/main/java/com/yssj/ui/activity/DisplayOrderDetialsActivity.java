package com.yssj.ui.activity;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.RoundImageButton;
//import com.yssj.custom.view.SnatchScrollShaiDan;
//import com.yssj.custom.view.SnatchScrollShaiDan.ZeroOnRefreshLintener;
//import com.yssj.custom.view.XListViewDuoBao;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.shopdetails.ShopDetailsIndianaActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 晒单详情页面
 *
 * @author Administrator
 *
 */
public class DisplayOrderDetialsActivity extends BasicActivity implements
		View.OnClickListener/*,ZeroOnRefreshLintener */{
//	private List<HashMap<String, Object>> mCommentsDatasList; // 数据
	private LinearLayout ll_container;//去掉评论列表 headerView头部布局换到LinearLayout容器中
	private View backView;
//	private XListViewDuoBao mCommentLv;// 评论的列表
//
//	private EditText mCommentEt;// 评论输入
//	private Button mSendCommentBtn;// 发送评论

	private RoundImageButton mUserHeadRib;// 用户头像
	private TextView mName;// 用户名
//	private LinearLayout mHuiyuanLl,root;// 至尊会员图标
	private TextView mTimeTv;// 晒单时间
	private TextView mGoodsTv, mQiHaoTv, mLuckNumberTv, mPeopleCountTv,
			mOverTimeTv;
	private LinearLayout mLookDetLl;// 查看详情

	private TextView mContentTv;// 内容文字
	private ImageView mImageIv1, mImageIv2, mImageIv3;// 内容图片

	private ImageView mDianZanIv;// 点赞图标
	private TextView mDianZanCountTv;// 点赞数
	private View mDianZanView;// 点赞数父布局(点赞图标 和 点赞数)

//	private ImageView mCommentIv;// 评论图标
//	private TextView mCommentCountTv;// 评论数

	private SAsyncTask<String, Void, HashMap<String, Object>> STask;
//	private SAsyncTask<String, Void, HashMap<String, List<HashMap<String, Object>>>> replyList;

	private View headerView;
	private String shop_code;// 商品编号
//	private OrderCommentAdapter orderCommentAdapter;
	private boolean dianzanFlag = false;// 默认没有点赞
//	private TextView mNoCommentTv;
//	private int curPage = 1;
//	private boolean isHuiFu = false;// 回复还是评论
//	private String toUserId;// 要回复的用户ID
//	private InputMethodManager imm;
//	private SnatchScrollShaiDan mView;

	private String luckNumber;
	private String otime;
	private String issueCode;//期号
	private String userName;//中奖者昵称
	private String userHead;//中奖者头像url
	private String userId;//中奖者id

	protected String clickSize;
//	protected String commentSize;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detils_shaidan);
		AppManager.getAppManager().addActivity(this);
//		aBar.hide();
		Intent intent = getIntent();
		if (intent != null) {
			shop_code = intent.getStringExtra("shop_code");
		}

		initView();
		queryDisplayOrder();
//		setCommentDatas(curPage);
//		setEvent();

	}

	private void initView() {
		backView = this.findViewById(R.id.img_back);
//		mCommentLv = (XListViewDuoBao) this
//				.findViewById(R.id.detials_shaidan_lv);
//		mCommentLv.setPullLoadEnable(true);
//		root= (LinearLayout) findViewById(R.id.root);
//		root.setBackgroundColor(Color.WHITE);

//		mNoCommentTv = (TextView) this.findViewById(R.id.detials_shaidan_nocomment_tv);
		ll_container = (LinearLayout) findViewById(R.id.ll_container);
		headerView = LayoutInflater.from(this).inflate(
				R.layout.detils_shaidan_head, null);

		mUserHeadRib = (RoundImageButton) headerView
				.findViewById(R.id.img_user_button);
		mName = (TextView) headerView.findViewById(R.id.name);
//		mHuiyuanLl = (LinearLayout) headerView.findViewById(R.id.huiyuan);
		mTimeTv = (TextView) headerView.findViewById(R.id.shaidan_time);
		mGoodsTv = (TextView) headerView.findViewById(R.id.goods_name_tv);
		mQiHaoTv = (TextView) headerView.findViewById(R.id.qihao_tv);
		mLuckNumberTv = (TextView) headerView.findViewById(R.id.luck_number_tv);
		mPeopleCountTv = (TextView) headerView
				.findViewById(R.id.people_counts_tv);
		mOverTimeTv = (TextView) headerView.findViewById(R.id.over_time_tv);
		mLookDetLl = (LinearLayout) headerView
				.findViewById(R.id.shaidan_lookdet_ll);
		mContentTv = (TextView) headerView
				.findViewById(R.id.detials_shaidan_content_tv);
		mImageIv1 = (ImageView) headerView
				.findViewById(R.id.detials_shaidan_content_iv1);
		mImageIv2 = (ImageView) headerView
				.findViewById(R.id.detials_shaidan_content_iv2);
		mImageIv3 = (ImageView) headerView
				.findViewById(R.id.detials_shaidan_content_iv3);
		mDianZanIv = (ImageView) headerView
				.findViewById(R.id.shaidan_dianzan_iv);
		mDianZanCountTv = (TextView) headerView
				.findViewById(R.id.shaidan_dianzan_count_tv);
		mDianZanView = headerView
				.findViewById(R.id.shaidan_dianzan_view);
//		mCommentIv = (ImageView) headerView
//				.findViewById(R.id.shaidan_comment_iv);
//		mCommentCountTv = (TextView) headerView
//				.findViewById(R.id.shaidan_comment_count_tv);

//		mCommentEt = (EditText) this
//				.findViewById(R.id.detail_shaidan_pinglun_et);
//		mSendCommentBtn = (Button) this
//				.findViewById(R.id.detail_shaidan_fasong_btn);

		mLookDetLl.setOnClickListener(this);
		backView.setOnClickListener(this);
		mDianZanView.setOnClickListener(this);
 		mDianZanIv.setClickable(false);
		mDianZanCountTv.setClickable(false);
//		mCommentIv.setOnClickListener(this);
//		mCommentCountTv.setOnClickListener(this);
//		mSendCommentBtn.setOnClickListener(this);

//		mView = (SnatchScrollShaiDan) findViewById(R.id.zeroView);
//		mView.setZeroOnRefreshLintener(this);
//
//		mCommentsDatasList = new ArrayList<HashMap<String, Object>>();
//		orderCommentAdapter = new OrderCommentAdapter(
//				DisplayOrderDetialsActivity.this, mCommentsDatasList);
//		mCommentLv.setAdapter(orderCommentAdapter);
//
//		mCommentLv.addHeaderView(headerView);

//		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		ll_container.addView(headerView);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
		case R.id.img_back:
			onBackPressed();
			break;
//		case R.id.shaidan_dianzan_iv:
//		case R.id.shaidan_dianzan_count_tv:
		case R.id.shaidan_dianzan_view:
			if (!dianzanFlag) {
				addDianZan();
			} else if (dianzanFlag) {
				ToastUtil.showLongText(this, "您已经点过赞了");
			}
			LogYiFu.e("DisplayOrder", "点赞图标被点击");
			break;
//		case R.id.shaidan_comment_iv:
//		case R.id.shaidan_comment_count_tv:
//			isHuiFu = false;
//			mCommentEt.setHint("评论");
//			mCommentEt.requestFocus();
//			mCommentEt.performClick();
//			imm.showSoftInput(mCommentEt, InputMethodManager.SHOW_IMPLICIT);
//			LogYiFu.e("DisplayOrder", "评论图标被点击");
//			break;
//		case R.id.detail_shaidan_fasong_btn:
//			if (!isHuiFu) {
//				toUserId = "";
//			}
//			String commentText = mCommentEt.getText().toString();
//			mCommentEt.setText("");
//			addComment(commentText);
//			break;
		case R.id.shaidan_lookdet_ll:// 查看详情
			Intent intent = new Intent(DisplayOrderDetialsActivity.this,
					ShopDetailsIndianaActivity.class);
			intent.putExtra("shop_code", shop_code);
			intent.putExtra("in_code",luckNumber);
			intent.putExtra("otime", otime);
			intent.putExtra("in_name", userName);
			intent.putExtra("in_head", userHead);
			intent.putExtra("in_uid", userId);
			intent.putExtra("issue_code", issueCode);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

//	/**
//	 * 事件
//	 */
//	private void setEvent() {
//		mCommentLv
//				.setXListViewListener(new XListViewDuoBao.IXListViewListener() {
//
//					@Override
//					public void onRefresh() {
//						refresh();
//					}
//
//					@Override
//					public void onLoadMore() {
//						curPage++;
//						setCommentDatas(curPage);
//					}
//				});
//	}
//
//	@Override
//	public void onRefreshlintener() {
//		mView.refreshDone();
//		this.refresh();
//	}
//
//	public void refresh(){
//		curPage=1;
//		queryDisplayOrder();
//		setCommentDatas(1);
//	}
//
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//			View v = getCurrentFocus();
//			if (isShouldHideInput(v, ev)) {
//				if (imm != null) {
//					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//				}
//			}
//			return super.dispatchTouchEvent(ev);
//		}
//		// 必不可少，否则所有的组件都不会有TouchEvent了
//		if (getWindow().superDispatchTouchEvent(ev)) {
//			return true;
//		}
//		return onTouchEvent(ev);
//	}
//
//	public boolean isShouldHideInput(View v, MotionEvent event) {
//		if (v != null && (v instanceof EditText)) {
//			int[] leftTop = { 0, 0 };
//			// 获取输入框当前的location位置
//			v.getLocationInWindow(leftTop);
//			int left = leftTop[0];
//			int top = leftTop[1];
//			int bottom = top + v.getHeight();
//			int right = left + v.getWidth();
//			if (event.getX() > left
//					&& event.getY() > top && event.getY() < bottom) {
//				// 点击的是输入框区域和发送按钮，保留点击EditText的事件
//				return false;
//			} else {
//				return true;
//			}
//		}
//		return false;
//	}

	/**
	 * 点赞
	 */
	private void addDianZan() {
		new SAsyncTask<String, Void, HashMap<String, Object>>(this, null,
				R.string.wait) {

			@Override
			protected HashMap<String, Object> doInBackground(
					FragmentActivity context, String... params)
					throws Exception {
				HashMap<String, Object> map = new HashMap<String, Object>();
				if (YJApplication.instance.isLoginSucess()) {
					map = ComModel.addShaiDanDianZan(
							DisplayOrderDetialsActivity.this, params[0]);
				}
				return map;
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, Object> result, Exception e) {
				if (e == null) {// 查询异常
					ToastUtil.showLongText(DisplayOrderDetialsActivity.this,
							"点赞成功");
					dianzanFlag=true;
//					queryDisplayOrder();
					mDianZanIv.setImageResource(R.drawable.icon_dianzan_press);
					int addClickSize = Integer.valueOf(clickSize).intValue()+1;
					clickSize = addClickSize+"";
					mDianZanCountTv.setText(clickSize);
				}
			}
		}.execute(shop_code);
	}

//	/**
//	 * 发表评论
//	 */
//	private void addComment(final String commentText) {
//		if (TextUtils.isEmpty(commentText)) {
//			if (isHuiFu) {
//				ToastUtil.showShortText(DisplayOrderDetialsActivity.this,
//						"回复不能为空");
//			} else {
//				ToastUtil.showShortText(DisplayOrderDetialsActivity.this,
//						"评论不能为空");
//			}
//			return;
//		}
//		new SAsyncTask<String, Void, HashMap<String, Object>>(this, null,
//				R.string.wait) {
//
//
//
//			@Override
//			protected HashMap<String, Object> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//				HashMap<String, Object> map = new HashMap<String, Object>();
//				if (YJApplication.instance.isLoginSucess()) {
//					map = ComModel.addShaiDanComment(
//							DisplayOrderDetialsActivity.this, params[0],
//							params[1], commentText);
//				}
//				return map;
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			};
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, Object> result, Exception e) {
//				if (e == null) {
//					refresh();
////					int addCommentSize = Integer.valueOf(commentSize).intValue()+1;
////					commentSize = addCommentSize+"";
////					mCommentCountTv.setText(commentSize);
////					DisplayOrderDetialsActivity.this.curPage = 1;
////					setCommentDatas(1);
//					if (isHuiFu) {
//						ToastUtil.showLongText(
//								DisplayOrderDetialsActivity.this, "回复成功");
//					} else {
//						ToastUtil.showLongText(
//								DisplayOrderDetialsActivity.this, "评论成功");
//					}
//					isHuiFu = false;
//					mCommentEt.setHint("评论");
//				} else {
//					ToastUtil.showLongText(DisplayOrderDetialsActivity.this,
//							"评论失败");
//				}
//
//			}
//		}.execute(shop_code, toUserId);
//	}

	/**
	 * 返回
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (STask != null && !STask.isCancelled()) {
			STask.cancel(true);
		}
//		if (replyList != null && !replyList.isCancelled()) {
//			replyList.cancel(true);
//		}
	}

	private void queryDisplayOrder() {
		STask = new SAsyncTask<String, Void, HashMap<String, Object>>(this,
				null, R.string.wait) {
			@Override
			protected HashMap<String, Object> doInBackground(
					FragmentActivity context, String... params)
					throws Exception {
				HashMap<String, Object> map = new HashMap<String, Object>();
				if (YJApplication.instance.isLoginSucess()) {
					map = ComModel
							.queryDisplayOrderDetials(
									DisplayOrderDetialsActivity.this,
									YCache.getCacheToken(DisplayOrderDetialsActivity.this),
									params[0]);
				}
				return map;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, Object> result, Exception e) {
				if (e != null) {// 查询异常
					return;
				}
				if (result != null) {
					LogYiFu.e("DisplayOrder", result.toString());
					headerView.findViewById(R.id.left).setVisibility(View.VISIBLE);
					mLookDetLl.setVisibility(View.VISIBLE);
					userId = (String) result.get("user_id");
					userHead= (String) result.get("user_url");
					if (!TextUtils.isEmpty(userHead)) {
//						SetImageLoader.initImageLoader(
//								DisplayOrderDetialsActivity.this, mUserHeadRib,
//								userHead, "!450");
						PicassoUtils.initImage(DisplayOrderDetialsActivity.this, userHead+"!450", mUserHeadRib);
					}
					userName = (String) result.get("user_name");
					mName.setText(userName);

					dianzanFlag = (result.get("shop_code"))
							.equals(result.get("click"));
					if (dianzanFlag) {
						mDianZanIv
								.setImageResource(R.drawable.icon_dianzan_press);
					}else {
						mDianZanIv
						.setImageResource(R.drawable.icon_dianzan_gray);
					}

					String addDate = (String) result.get("add_date");
					String showTime = showAddTime(addDate);
					mTimeTv.setText(showTime);
					mContentTv.setText((String) result.get("content"));

					String shopName = (String) result.get("shop_name");
					shopName = String.format(
							getResources().getString(R.string.shaidan_goods),
							shopName);
					mGoodsTv.setText(shopName);

					issueCode = (String) result.get("issue_code");
					issueCode = String.format(
							getResources().getString(R.string.shaidan_qihao),
							issueCode);
					SpannableString ssIssueCode = new SpannableString(issueCode);
					ssIssueCode.setSpan(new ForegroundColorSpan(
							Color.TRANSPARENT), 0, 2,
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					mQiHaoTv.setText(ssIssueCode);

					luckNumber= (String) result.get("lucky_number");
					String strNumber = String
							.format(getResources().getString(
									R.string.shaidan_luck_number), luckNumber);
					mLuckNumberTv.setText(strNumber);

					String canYuCount = String.format(
							getResources().getString(
									R.string.shaidan_canyu_count),
							(String) result.get("count"));
					SpannableString ssCanYuCount = new SpannableString(
							canYuCount);
					ssCanYuCount.setSpan(
							new ForegroundColorSpan(Color.argb(0Xff, 0Xff,
									0X44, 0X8E)), 5, canYuCount.length() - 2,
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					mPeopleCountTv.setText(ssCanYuCount);

					String overTime = "";
					otime = (String) result.get("otime");
					if (!"".equals(otime)) {
						Long longOverTime = Long.valueOf(otime);
						Date overDate = new Date(longOverTime);
						SimpleDateFormat sdfOverDate = new SimpleDateFormat(
								"yyyy年MM月dd日");
						overTime = String.format(
								getResources()
										.getString(R.string.shaidan_otime),
								sdfOverDate.format(overDate));
					} else {
						overTime = String.format(
								getResources()
										.getString(R.string.shaidan_otime), "");
					}
					mOverTimeTv.setText(overTime);

					String pics = (String) result.get("pic");
					LogYiFu.e("DisplayOrder", pics.toString());
					if ("".equals(pics)) {
					} else {
						mImageIv1.setVisibility(View.VISIBLE);
						String[] imgs = pics.split(",");
						if (imgs.length > 1) {
							mImageIv2.setVisibility(View.VISIBLE);
							if (imgs.length > 2) {
								mImageIv3.setVisibility(View.VISIBLE);
							}
						}

						for (int i = 0; i < imgs.length; i++) {
							if(imgs[i].startsWith(File.separator)){
								imgs[i] = "shareOrder"+imgs[i];
							}else if(imgs[i].startsWith("shop")){

							}else{
								imgs[i] = "shareOrder"+File.separator+imgs[i];
							}
							LogYiFu.e("DisplayOrder", imgs[i].toString());
						}

//						SetImageLoader.initImageLoader(null, mImageIv1,
//								imgs[0], "!450");


						PicassoUtils.initImage(DisplayOrderDetialsActivity.this, imgs[0]+"!450", mImageIv1);





						if (imgs.length > 1) {
//							SetImageLoader.initImageLoader(null, mImageIv2,
//									imgs[1], "!450");

							PicassoUtils.initImage(DisplayOrderDetialsActivity.this, imgs[1]+"!450", mImageIv2);




							if (imgs.length > 2) {
//								SetImageLoader.initImageLoader(null, mImageIv3,
//										imgs[2], "!450");

								PicassoUtils.initImage(DisplayOrderDetialsActivity.this, imgs[2]+"!450", mImageIv3);

							}
						}
					}


//					commentSize = (String) result.get("comment_size");
					clickSize = (String) result.get("click_size");
					mDianZanCountTv.setText(clickSize);
//					mCommentCountTv.setText(commentSize);
				}

			}

			@Override
			protected boolean isHandleException() {
				return true;
			};

		};
		STask.execute(shop_code);
	}

//	private void setCommentDatas(final int curPage) {
//		replyList = new SAsyncTask<String, Void, HashMap<String, List<HashMap<String, Object>>>>(
//				this, null, R.string.wait) {
//
//			@Override
//			protected HashMap<String, List<HashMap<String, Object>>> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//				HashMap<String, List<HashMap<String, Object>>> map = new HashMap<String, List<HashMap<String, Object>>>();
//				if (YJApplication.instance.isLoginSucess()) {
//					map = ComModel
//							.queryOrderDetialsComment(
//									DisplayOrderDetialsActivity.this,
//									YCache.getCacheToken(DisplayOrderDetialsActivity.this),
//									params[0], curPage);
//				}
//				return map;
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			};
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, List<HashMap<String, Object>>> result,
//					Exception e) {
//				if (e != null) {// 查询异常
//					mCommentLv.stopLoadMore();
//					mCommentLv.stopRefresh();
//					return;
//				}
//				List<HashMap<String, Object>> dataList = result
//						.get("commentsDataList");
//
//				if (dataList != null) {
//
//					if (curPage == 1) {
//						mCommentsDatasList.clear();
//					}else if (dataList.size() == 0) {
//						ToastUtil.showShortText(context, "已没有更多评论了");
//					}
//					mCommentsDatasList.addAll(dataList);
//				} else {
//					if (curPage == 1) {
//						mCommentsDatasList.clear();
//					} else {
//						ToastUtil.showShortText(context, "已没有更多评论了");
//					}
//				}
//				orderCommentAdapter.notifyDataSetChanged();
//				mCommentLv.stopLoadMore();
//			}
//		};
//		replyList.execute(shop_code);
//	}

	/**
	 * 将毫秒数转换为 显示时间
	 *
	 * @param addDate
	 *            添加时间的毫秒数
	 * @return 返回显示在界面的字符串
	 */
	private String showAddTime(String addDate) {
		long long_add_date = Long.valueOf(addDate);

		long timeMillis = new Date().getTime() - long_add_date;
		if (timeMillis < 3600000) {// 一个小时之内
			long showTimeMin = timeMillis / 60000;
			if (showTimeMin < 1) {
				return "刚刚";
			} else
				return showTimeMin + "分钟前";
		} else if (timeMillis >= 3600000 && timeMillis <= 3600000 * 12) {
			long showTimeHour = timeMillis / 3600000;
			return showTimeHour + "小时前";
		} else {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date(long_add_date);
				date = sdf.parse(sdf.format(date));
				Date now = new Date();
				now = sdf.parse(sdf.format(now));
				long sl = date.getTime();
				long el = now.getTime();
				long ei = sl - el;
				int value = (int) (ei / (1000 * 60 * 60 * 24));
				SimpleDateFormat sdfDay = new SimpleDateFormat("HH:mm");
				String dayTime = sdfDay.format(new Date(long_add_date));
				SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
				SimpleDateFormat sdfThisYear = new SimpleDateFormat("MM-dd HH:mm");
				if (value == 0) {
					return "今天" +dayTime;
				} else if (value == -1) {
					return "昨天" +dayTime;
				} else if(value == -2){
					return "前天" +dayTime;
				}else{
					//是否是否是今年
					boolean isThisYear = sdfYear.format(new Date()).equals(sdfYear.format(new Date(long_add_date)));
					if(isThisYear){
						return sdfThisYear.format(new Date(long_add_date));
					}else {
						return sdf.format(new Date(long_add_date));
					}
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		return "";
	}

//	private class OrderCommentAdapter extends BaseAdapter{
//		private Context context;
//		private List<HashMap<String, Object>> listData;
//		private LayoutInflater mInflater;
//
//		public OrderCommentAdapter(Context context,
//				List<HashMap<String, Object>> listData) {
//			this.context = context;
//			this.listData = listData;
//			mInflater = LayoutInflater.from(context);
//		}
//
//		@Override
//		public int getCount() {
//			return listData.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return listData.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			Holder holder = null;
//			if (convertView == null) {
//				holder = new Holder();
//				convertView = mInflater.inflate(
//						R.layout.detils_shaidan_comment_list, null);
//				holder.userHead = (RoundImageButton) convertView
//						.findViewById(R.id.img_user_button);
//				holder.userName = (TextView) convertView
//						.findViewById(R.id.shaidan_comment_username1);
//				holder.toUserName = (TextView) convertView
//						.findViewById(R.id.shaidan_comment_username2);
//				holder.commentTime = (TextView) convertView
//						.findViewById(R.id.shaidan_comment_time);
//				holder.huifuIv = (ImageView) convertView
//						.findViewById(R.id.shaidan_huifu_iv);
//				holder.commentContent = (TextView) convertView
//						.findViewById(R.id.shaidan_comment_content_tv);
//
//				holder.huifu = (TextView) convertView
//						.findViewById(R.id.shaidan_comment_huifu);
//
//				convertView.setTag(holder);
//
//			} else {
//				holder = (Holder) convertView.getTag();
//			}
//			final HashMap<String, Object> datas = listData.get(position);
//			String userUrl = (String) datas.get("user_url");
////			SetImageLoader.initImageLoader(context, holder.userHead, userUrl,
////					"");
//
//			PicassoUtils.initImage(context, userUrl, holder.userHead);
//			holder.userName.setText((String) datas.get("user_name"));
//			holder.commentTime.setText(showAddTime((String) datas
//					.get("add_date")));
//			holder.commentContent.setText((String) datas.get("content"));
//
//			String to_user_name = (String) datas.get("to_user_name");
//			String to_user_id = (String) datas.get("to_user_id");
//			if ("".equals(to_user_id)) {
//				holder.huifu.setVisibility(View.GONE);
//				holder.toUserName.setVisibility(View.GONE);
//			} else {
//				holder.huifu.setVisibility(View.VISIBLE);
//				holder.toUserName.setVisibility(View.VISIBLE);
//				holder.toUserName.setText(to_user_name);
//			}
//
//			holder.commentTime.setOnClickListener(new View.OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					toUserId = "";
//					toUserId = (String) datas.get("reuser_id");
//					String toUserName = (String) datas.get("user_name");
//					mCommentEt.requestFocus();
//					mCommentEt.performClick();
//					imm.showSoftInput(mCommentEt,
//							InputMethodManager.SHOW_IMPLICIT);
//					mCommentEt.setHint("回复" + toUserName);
//					isHuiFu = true;
//				}
//			});
//			holder.huifuIv.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					toUserId = "";
//					toUserId = (String) datas.get("reuser_id");
//					String toUserName = (String) datas.get("user_name");
//					mCommentEt.requestFocus();
//					mCommentEt.performClick();
//					imm.showSoftInput(mCommentEt,
//							InputMethodManager.SHOW_IMPLICIT);
//					mCommentEt.setHint("回复" + toUserName);
//					isHuiFu = true;
//				}
//			});
//
//			return convertView;
//		}
//
//		class Holder {
//			RoundImageButton userHead;
//			TextView userName, toUserName, commentTime, commentContent, huifu;
//			ImageView huifuIv;
//		}
//
//	}
}
