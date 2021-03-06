package com.yssj.ui.activity.circles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.custom.view.ShowHoriontalView.onClickLintener;
import com.yssj.data.DBService;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.base.BaseFragmentActiviy;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.GlideUtils;
import com.yssj.utils.LimitDoubleClicked;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.TimeUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class AllEvaluateActivity extends BasicActivity implements TextWatcher, OnLayoutChangeListener {
	private int mClick = 0;// 0???????????????????????????1????????????????????????
	private boolean mIsNoneFlag = false;// ????????????????????? true?????????????????????false?????????
	private boolean mHostCommentBottomFlag = false;// true???????????????????????????
	private String mTheme_id = "";// ??????id
	private String mUser_id = "";// ????????????id
	private String applaud_status = "";// ????????????
	private String applaud_num = "";// ????????????
	private String mUser_id1;// ????????????id
	private String mUser_id2;// ???????????????id
	private boolean isHotComment = false;// true??????????????????
	private boolean mLoveFlag = false;// true ??????????????????
	private int mLoveCount = 0;// ???????????????
	private String comment_count = "0";// ???????????????
	private int screenHeight;// ???????????????
	private EditText mEtContent;
	private TextView mSendComment;
	private HashMap<String, List<HashMap<String, Object>>> mMapHotRecomment;// ???????????????????????????
	private List<HashMap<String, Object>> mListHotComment = new ArrayList<HashMap<String, Object>>();// ????????????
	private List<HashMap<String, Object>> mListNewComment = new ArrayList<HashMap<String, Object>>();// ????????????
	private List<HashMap<String, Object>> mListAllComment = new ArrayList<HashMap<String, Object>>();// ????????????
	private List<HashMap<String, Object>> mListHostComment = new ArrayList<HashMap<String, Object>>();// ????????????????????????
	private List<HashMap<String, Object>> mListHostHotComment = new ArrayList<HashMap<String, Object>>();// ????????????????????????
	private List<HashMap<String, Object>> mListHostNewComment = new ArrayList<HashMap<String, Object>>();// ????????????????????????
	private PullToRefreshListView mListView;
	private TextView allComment;
	private TextView hostComment;
	private TextView tvIconLove;
	private TextView tvLoveCount;
	private View line1, line2;
	private Context mContext;
	private EvaluateAdapter mAdapter;
	private LinearLayout mBack;
	private boolean isClick = false;// ??????????????????

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		AppManager.getAppManager().addActivity(this);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_all_evaluate);
		mContext = this;
		screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
		mTheme_id = getIntent().getStringExtra("theme_id");
		mUser_id = getIntent().getStringExtra("user_id");
		applaud_status = getIntent().getStringExtra("applaud_status");
		applaud_num = getIntent().getStringExtra("applaud_num");
		comment_count = getIntent().getStringExtra("comment_count");
		mListHotComment = (List<HashMap<String, Object>>) getIntent().getExtras().getSerializable("hot_comment");
		initView();
		flush();
		mAdapter = new EvaluateAdapter();
		mListView.setAdapter(mAdapter);
		initData();
	}

	private void initData() {
		queryNewComment();
	}

	private void initView() {
		mEtContent = (EditText) findViewById(R.id.sweet_et_content);
		mEtContent.addTextChangedListener(this);
		LinearLayout mRoot_view = (LinearLayout) findViewById(R.id.root_view);
		mRoot_view.addOnLayoutChangeListener(this);
		mSendComment = (TextView) findViewById(R.id.sweet_tv_send);
		mSendComment.setOnClickListener(this);
		mBack = (LinearLayout) findViewById(R.id.sweet_img_back);
		mBack.setOnClickListener(this);
		mListView = (PullToRefreshListView) findViewById(R.id.top_listview);
		allComment = (TextView) findViewById(R.id.top_tv_all);
		hostComment = (TextView) findViewById(R.id.top_tv_host);
		tvIconLove = (TextView) findViewById(R.id.top_tv_icon_love);
		tvLoveCount = (TextView) findViewById(R.id.top_tv_love_count);
		line1 = findViewById(R.id.top_line1);
		line2 = findViewById(R.id.top_line2);
		if (mClick == 1) {
			line1.setVisibility(View.INVISIBLE);
			line2.setVisibility(View.VISIBLE);
			allComment.setTextColor(Color.parseColor("#7d7d7d"));
			hostComment.setTextColor(Color.parseColor("#3e3e3e"));
		} else {
			line1.setVisibility(View.VISIBLE);
			line2.setVisibility(View.INVISIBLE);
			allComment.setTextColor(Color.parseColor("#3e3e3e"));
			hostComment.setTextColor(Color.parseColor("#7d7d7d"));
		}
		if ("1".equals(applaud_status)) {// ???????????????
			mLoveFlag = true;
		} else {
			mLoveFlag = false;
		}
		if (mLoveFlag) {
			tvIconLove.setBackgroundResource(R.drawable.sweet_icon_xihuan_pre);
		} else {
			tvIconLove.setBackgroundResource(R.drawable.sweet_icon_xihuan);
		}
		try {
			mLoveCount = Integer.parseInt(applaud_num);
		} catch (Exception e) {
			// TODO: handle exception
		}
		tvLoveCount.setText("" + mLoveCount);
		tvIconLove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!YJApplication.instance.isLoginSucess()) {// ????????????????????????????????????
					toLogin();
					return;
				}
				if (!mLoveFlag) {
					dianZan(mTheme_id, "1", mTheme_id, tvIconLove, tvLoveCount, 0);
				} else {
					removeZan(mTheme_id, "1", mTheme_id, tvIconLove, tvLoveCount, 0);
				}
				// mListView.smoothScrollBy(1, 1);
				// mListView.smoothScrollBy(-1, 1);
			}
		});
		allComment.setText("???????????? (" + comment_count + ")");
		allComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mClick == 0) {
					return;
				}
				mClick = 0;
				mAdapter.notifyDataSetChanged();
				// mListView.removeFooterView(mFootView);
				// if (mAllCommentBottomFlag) {
				// mListView.addFooterView(mFootView);
				// }
				allComment.setTextColor(Color.parseColor("#3e3e3e"));
				hostComment.setTextColor(Color.parseColor("#7d7d7d"));
				line1.setVisibility(View.VISIBLE);
				line2.setVisibility(View.INVISIBLE);

			}
		});
		hostComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mClick == 1) {
					return;
				}
				mClick = 1;
				mAdapter.notifyDataSetChanged();
				// mListView.removeFooterView(mFootView);
				if (mHostCommentBottomFlag) {// ????????????
					// mListView.addFooterView(mFootView);
				} else if (mListHostComment.size() == 0) {
					queryHostCommentByShop();
				}
				// mFistClickHost = true;
				allComment.setTextColor(Color.parseColor("#7d7d7d"));
				hostComment.setTextColor(Color.parseColor("#3e3e3e"));
				line1.setVisibility(View.INVISIBLE);
				line2.setVisibility(View.VISIBLE);

			}
		});

	}

	private int rows = 10, page = 1;
	private boolean isCheck = false;

	/**
	 * ??????????????????
	 */
	private void queryNewComment() {
		// if (page == 1) {
		// rows = 5;
		// } else {
		rows = 10;
		// }
		isCheck = true;
		new SAsyncTask<Void, Void, List<HashMap<String, Object>>>((FragmentActivity) mContext, null, R.string.wait) {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				LoadingDialog.show(AllEvaluateActivity.this);
			}

			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				List<HashMap<String, Object>> list = ComModel2.getNewCommentList((FragmentActivity) context, mTheme_id,
						"" + page, "" + rows);
				return list;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> list, Exception e) {
				isCheck = false;
				if (e != null) {// ????????????
					ToastUtil.showShortText(mContext, "????????????????????????");
					page--;
				} else {// ???????????????????????????????????????
					// rrr.setBackgroundColor(Color.WHITE);
					if (list != null && list.size() > 0) {
						// if (mListNewComment == null) {
						// mListNewComment = new ArrayList<ShopComment>();
						// }
						if (page == 1) {
							// mListView.removeFooterView(mFootView);
							mListAllComment.clear();
							mListNewComment.clear();
							mListAllComment.addAll(mListHotComment);
						}
						if(list!=null&&list.size()>0){
							for (int i = 0; i <list.size() ; i++) {
								if("0".equals(list.get(i).get("status"))){
									mListNewComment.add(list.get(i));
									mListAllComment.add(list.get(i));
								}else if("" + list.get(i).get("user_id")!=null&&YCache.getCacheUser(mContext)!=null&&("" + list.get(i).get("user_id")).equals("" + YCache.getCacheUser(mContext).getUser_id())){
									mListNewComment.add(list.get(i));
									mListAllComment.add(list.get(i));
								}
							}
						}
//						mListNewComment.addAll(list);
//						mListAllComment.addAll(list);
					} else {
						// mAllCommentBottomFlag = true;
						// mListView.addFooterView(mFootView);
						if (page > 1) {
							ToastUtil.showShortText(mContext, "???????????????");
						}
						isCheck = true;
					}
					if (mAdapter != null) {
						mAdapter.notifyDataSetChanged();
						ListView listTemp = mListView.getRefreshableView();
						listTemp.smoothScrollBy(1, 10);
					}
				}

			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute();

	}

	private int rows2 = 10, page2 = 1;
	private boolean isCheck2 = false;

	/**
	 * ????????????????????????
	 */
	private void queryHostCommentByShop() {
		// if (page == 1) {
		// rows = 5;
		// } else {
		rows2 = 10;
		// }
		isCheck2 = true;
		new SAsyncTask<Void, Void, HashMap<String, List<HashMap<String, Object>>>>((FragmentActivity) mContext, null,
				R.string.wait) {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				LoadingDialog.show(AllEvaluateActivity.this);
			}

			@Override
			protected HashMap<String, List<HashMap<String, Object>>> doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				HashMap<String, List<HashMap<String, Object>>> map = ComModel2
						.getHostCommentList((FragmentActivity) context, mTheme_id, mUser_id, "" + page2, "" + rows2);
				return map;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, List<HashMap<String, Object>>> map,
					Exception e) {
				isCheck2 = false;
				if (e != null) {// ????????????
					Toast.makeText(context, "????????????????????????", Toast.LENGTH_LONG).show();
					page2--;
				} else {// ???????????????????????????????????????
					// rrr.setBackgroundColor(Color.WHITE);
					if ((map != null && map.size() > 0 && page2 == 1
							&& (map.get("list_hot").size() > 0 || map.get("list_new").size() > 0))
							|| (map != null && map.size() > 0 && page2 > 1 && map.get("list_new").size() > 0)) {
						if (page2 == 1) {
							// mListView.removeFooterView(mFootView);
							mListHostComment.clear();
							mListHostHotComment.clear();
							mListHostNewComment.clear();
						}
						if (mListHostComment == null) {
							mListHostComment = new ArrayList<HashMap<String, Object>>();
						}
						if (page2 == 1) {
							mListHostHotComment.addAll(map.get("list_hot"));// ????????????
							mListHostComment.addAll(mListHostHotComment);
						}
						mListHostNewComment.addAll(map.get("list_new"));// ????????????
						mListHostComment.addAll(map.get("list_new"));
					} else {
						mHostCommentBottomFlag = true;
						// mListView.addFooterView(mFootView);
						if (page2 > 1) {
							Toast.makeText(mContext, "???????????????", Toast.LENGTH_SHORT).show();
						}
						isCheck2 = true;
					}
					if (mAdapter != null) {
						mAdapter.notifyDataSetChanged();
						ListView listTemp = mListView.getRefreshableView();
						listTemp.smoothScrollBy(1, 10);
					}
				}

			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute();

	}

	/**
	 * ?????????????????????????????????
	 */
	private void queryHotComment(final boolean isFristFlag) {
		// if (page == 1) {
		// rows = 5;
		// } else {
		// }
		new SAsyncTask<Void, Void, HashMap<String, List<HashMap<String, Object>>>>((FragmentActivity) mContext, null,
				R.string.wait) {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}

			@Override
			protected HashMap<String, List<HashMap<String, Object>>> doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				mMapHotRecomment = ComModel2.getHotCommentList((FragmentActivity) context, mTheme_id, "" + mUser_id);
				return mMapHotRecomment;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, List<HashMap<String, Object>>> map,
					Exception e) {
				// isCheck = false;
				if (e != null) {// ????????????
					Toast.makeText(context, "????????????????????????", Toast.LENGTH_LONG).show();
					// page--;
				} else {// ???????????????????????????????????????
					// rrr.setBackgroundColor(Color.WHITE);
					if (map != null && map.size() > 0) {
						mListAllComment.clear();
						mListHotComment.clear();
						mListHotComment = map.get("hot_comments");
						mListAllComment.addAll(mListHotComment);
						mListAllComment.addAll(mListNewComment);
						if (mAdapter != null) {
							mAdapter.notifyDataSetChanged();
						}
					}
				}

			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute();

	}

	public class EvaluateAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			if (mClick == 1) {
				if (mListHostComment.size() == 0) {
					mIsNoneFlag = true;
					return 1;
				}
				mIsNoneFlag = false;
				return mListHostComment.size();
			}
			if (mListAllComment.size() == 0) {
				mIsNoneFlag = true;
				return 1;
			}
			mIsNoneFlag = false;
			return mListAllComment.size();

		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			// TODO Auto-generated method stub

			final ViewHolder holder;
			if (convertView == null) {
				// convertView=View.inflate(mContext, R.layout.sweet_item,
				// null);
				convertView = LayoutInflater.from(mContext).inflate(R.layout.sweet_item, parent, false);
				holder = new ViewHolder();
				holder.mLlHot = (LinearLayout) convertView.findViewById(R.id.item_ll_hot);
				holder.mLlNew = (LinearLayout) convertView.findViewById(R.id.item_ll_new);
				holder.mNoneComment = (RelativeLayout) convertView.findViewById(R.id.item_rl_none_comment);
				holder.mHaveComment = (LinearLayout) convertView.findViewById(R.id.item_ll__comment);
				holder.mHeadPic = (ImageView) convertView.findViewById(R.id.item_head_img);
				holder.mGradePic = (ImageView) convertView.findViewById(R.id.item_iv_grade);
				holder.mName = (TextView) convertView.findViewById(R.id.item_tv_name);
				holder.mLocation = (TextView) convertView.findViewById(R.id.item_tv_location);
				holder.mTime = (TextView) convertView.findViewById(R.id.item_tv_time);
				holder.mContent = (TextView) convertView.findViewById(R.id.item_content);
				holder.mZanCount = (TextView) convertView.findViewById(R.id.item_zan_count);
				holder.mContain = (LinearLayout) convertView.findViewById(R.id.item_ll_contain);
				holder.mZanIcon = (TextView) convertView.findViewById(R.id.item_zan_icon);
				// if (mClick == 1) {
				// holder.mNoneComment.setVisibility(View.VISIBLE);
				// holder.mHaveComment.setVisibility(View.GONE);
				// } else {
				// holder.mNoneComment.setVisibility(View.GONE);
				// holder.mHaveComment.setVisibility(View.VISIBLE);
				// }
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			{
				Rect frame = new Rect();
				getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
				int statusBarHeight = frame.top;
				if (mClick == 0) {
					if (mListAllComment.size() == 0) {
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								LinearLayout.LayoutParams.MATCH_PARENT,
								screenHeight - DP2SPUtil.dp2px(mContext, 150) - statusBarHeight);
						holder.mNoneComment.setLayoutParams(params);
						holder.mNoneComment.setVisibility(View.VISIBLE);
						holder.mHaveComment.setVisibility(View.GONE);
					} else {
						holder.mNoneComment.setVisibility(View.GONE);
						holder.mHaveComment.setVisibility(View.VISIBLE);
					}
				} else {
					if (mListHostComment.size() == 0) {
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								LinearLayout.LayoutParams.MATCH_PARENT,
								screenHeight - DP2SPUtil.dp2px(mContext, 150) - statusBarHeight);
						holder.mNoneComment.setLayoutParams(params);
						holder.mNoneComment.setVisibility(View.VISIBLE);
						holder.mHaveComment.setVisibility(View.GONE);
					} else {
						holder.mNoneComment.setVisibility(View.GONE);
						holder.mHaveComment.setVisibility(View.VISIBLE);
					}
				}
				if (!mIsNoneFlag) {
					String outPic = "";// ??????
					String outGrade = "";// ??????
					final String outName;// ??????
					String outLocation = "";// ??????
					String outTime = "";// ??????
					final String outZanCount;// ????????????
					String outContent = "";// ??????
					final String outUserId;// ??????id
					final String outCommentId;// ??????id
					final String outHostUserId;// ????????????id
					// List<String> outZanUserId;// ????????????id??????
					List<HashMap<String, Object>> outSonList = new ArrayList<HashMap<String, Object>>();// ?????????
					String outZanStatus;
					if (mClick == 0) {

						// shopComment = listAllComments.get(position);
						outPic = (String) mListAllComment.get(position).get("head_pic");
						outGrade = (String) mListAllComment.get(position).get("v_ident");
						outName = (String) mListAllComment.get(position).get("nickname");
						outLocation = (String) mListAllComment.get(position).get("location");
						outTime = (String) mListAllComment.get(position).get("send_time");
						outZanCount = (String) mListAllComment.get(position).get("applaud_num");
						outContent = (String) mListAllComment.get(position).get("content");
						outUserId = (String) mListAllComment.get(position).get("user_id");
						outCommentId = (String) mListAllComment.get(position).get("comment_id");
						outHostUserId = (String) mListAllComment.get(position).get("base_user_id");
						// outZanUserId = (List<String>)
						// mListAllComment.get(position).get("applaud_user_list");
						List<HashMap<String, Object>>	outSonListTemp = (List<HashMap<String, Object>>) mListAllComment.get(position).get("replies_list");
						if(outSonListTemp!=null&&outSonListTemp.size()>0){
							for (int i = 0; i <outSonListTemp.size() ; i++) {
								if("0".equals(outSonListTemp.get(i).get("status"))){
									outSonList.add(outSonListTemp.get(i));
								}else if(("" + outSonListTemp.get(i))!=null&&YCache.getCacheUser(mContext)!=null&&("" + outSonListTemp.get(i).get("send_user_id")).equals("" + YCache.getCacheUser(mContext).getUser_id())){
									outSonList.add(outSonListTemp.get(i));
								}
							}

						}
						outZanStatus = (String) mListAllComment.get(position).get("comments_applaud_status");
					} else {
						outPic = (String) mListHostComment.get(position).get("head_pic");
						outGrade = (String) mListHostComment.get(position).get("v_ident");
						outName = (String) mListHostComment.get(position).get("nickname");
						outLocation = (String) mListHostComment.get(position).get("location");
						outTime = (String) mListHostComment.get(position).get("send_time");
						outZanCount = (String) mListHostComment.get(position).get("applaud_num");
						outContent = (String) mListHostComment.get(position).get("content");
						outUserId = (String) mListHostComment.get(position).get("user_id");
						outCommentId = (String) mListHostComment.get(position).get("comment_id");
						outHostUserId = (String) mListHostComment.get(position).get("base_user_id");
						// outZanUserId = (List<String>)
						// mListHostComment.get(position).get("applaud_user_list");
//						outSonList = (List<HashMap<String, Object>>) mListHostComment.get(position).get("replies_list");
						List<HashMap<String, Object>>	outSonListTemp = (List<HashMap<String, Object>>) mListAllComment.get(position).get("replies_list");
						if(outSonListTemp!=null&&outSonListTemp.size()>0){
							for (int i = 0; i <outSonListTemp.size() ; i++) {
								if("0".equals(outSonListTemp.get(i).get("status"))){
									outSonList.add(outSonListTemp.get(i));
								}else if(("" + outSonListTemp.get(i))!=null&&YCache.getCacheUser(mContext)!=null&&("" + outSonListTemp.get(i).get("send_user_id")).equals("" + YCache.getCacheUser(mContext).getUser_id())){
                                    outSonList.add(outSonListTemp.get(i));
                                }
							}

						}
						outZanStatus = (String) mListHostComment.get(position).get("comments_applaud_status");
					}

					if (mClick == 0 && position == 0 && mListHotComment.size() > 0) {
						holder.mLlHot.setVisibility(View.VISIBLE);
						holder.mLlNew.setVisibility(View.GONE);
					} else if (mClick == 0 && mListHotComment.size() > 0 && position == mListHotComment.size()) {
						holder.mLlHot.setVisibility(View.GONE);
						holder.mLlNew.setVisibility(View.VISIBLE);
					} else if (mClick == 1 && position == 0 && mListHostHotComment.size() > 0) {
						holder.mLlHot.setVisibility(View.VISIBLE);
						holder.mLlNew.setVisibility(View.GONE);
					} else if (mClick == 1 && mListHostHotComment.size() > 0
							&& position == mListHostHotComment.size()) {
						holder.mLlHot.setVisibility(View.GONE);
						holder.mLlNew.setVisibility(View.VISIBLE);
					} else {
						holder.mLlHot.setVisibility(View.GONE);
						holder.mLlNew.setVisibility(View.GONE);
					}
					holder.mContain.removeAllViews();
					// int count = position + 1;
					// count = count > 10 ? 10 : count;
					for (int i = 0; i < outSonList.size(); i++) {// ???????????????
						final String inCommentId;// ???????????????id
						final String inName;// ??????????????????
						final String inUserId;// ????????????id
						String inOtherName;// ?????????????????????
						final String inOtherUserId;// ???????????????id
						String inTime;// ??????
						String inContent;// ??????
						// if(mClick==0){
						inCommentId = (String) outSonList.get(i).get("replies_id");
						inName = (String) outSonList.get(i).get("send_nickname");
						inUserId = (String) outSonList.get(i).get("send_user_id");
						inOtherName = (String) outSonList.get(i).get("receive_nickname");
						inOtherUserId = (String) outSonList.get(i).get("receive_user_id");
						inTime = (String) outSonList.get(i).get("send_time");
						inContent = (String) outSonList.get(i).get("content");
						// }

						TextView textView = new TextView(mContext);
						LinearLayout.LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
								LinearLayout.LayoutParams.WRAP_CONTENT);
						params.setMargins(DP2SPUtil.dp2px(mContext, 10), 0, 0, 0);
						if (i == 0 && outSonList.size() > 1) {
							textView.setPadding(0, DP2SPUtil.dp2px(mContext, 10), 0, DP2SPUtil.dp2px(mContext, 5));
						} else if (i == 0) {
							textView.setPadding(0, DP2SPUtil.dp2px(mContext, 10), 0, DP2SPUtil.dp2px(mContext, 10));
						} else if (i == outSonList.size() - 1) {
							textView.setPadding(0, DP2SPUtil.dp2px(mContext, 5), 0, DP2SPUtil.dp2px(mContext, 10));
						} else {
							textView.setPadding(0, DP2SPUtil.dp2px(mContext, 5), 0, DP2SPUtil.dp2px(mContext, 5));
						}
						// ds.setColor()????????????span?????????????????????????????????????????????????????????
						// ????????????????????????(HighLightColor)??????TextView????????????
						// Android4.0???????????????????????????????????????????????????????????????????????????????????????????????????????????????
						textView.setHighlightColor(getResources().getColor(android.R.color.transparent));
						SpannableString spanableInfo;
						String inName2 = inName;
						String inOtherName2 = inOtherName;
						if (inUserId.equals(outHostUserId)) {
							inName2 = "??????";
						}
						if (inOtherUserId.equals(outHostUserId)) {
							inOtherName2 = "??????";
						}
						if (!"".equals(inOtherName2)) {
							spanableInfo = new SpannableString(inName2 + "??????" + inOtherName2 + ": " + inContent);
							spanableInfo.setSpan(new Clickable(clickListener2), inName2.length() + 2,
									inName2.length() + 2 + inOtherName2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
						} else {
							spanableInfo = new SpannableString(inName2 + ": " + inContent);
						}
						spanableInfo.setSpan(new Clickable(clickListener), 0, inName2.length(),
								Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
						textView.setText(spanableInfo);
						textView.setTextColor(Color.parseColor("#7d7d7d"));
						textView.setTextSize(14);
						textView.setMovementMethod(LinkMovementMethod.getInstance());
						holder.mContain.addView(textView, params);
						textView.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								if (!YJApplication.instance.isLoginSucess()) {
									return;
								}
								if (!("" + YCache.getCacheUser(mContext).getUser_id()).equals(outHostUserId)
										&& !("" + YCache.getCacheUser(mContext).getUser_id()).equals(inUserId)
										&& !("" + YCache.getCacheUser(mContext).getUser_id()).equals("" + outUserId)) {// ?????????????????????????????????????????????????????????
									return;
								}
								if (position < mListHotComment.size()) {
									isHotComment = true;
								} else {
									isHotComment = false;
								}
								// if (clickFlag) {
								// clickFlag = false;
								// return;
								// }
								mUser_id1 = outCommentId;
								mUser_id2 = inUserId;
								InputMethodManager imm = (InputMethodManager) getSystemService(
										Context.INPUT_METHOD_SERVICE);
								mEtContent.requestFocus();
								// mEtContent.performClick();
								imm.showSoftInput(mEtContent, InputMethodManager.SHOW_IMPLICIT);
								String inName3 = inName;
								if (inUserId.equals(outHostUserId)) {
									inName3 = "??????";
								}
								mEtContent.setHint("??????" + inName3);

							}
						});
					}

					// SetImageLoader.initImageLoader(mContext, holder.mHeadPic,
					// outPic, "");
//					PicassoUtils.initImage(mContext, outPic, holder.mHeadPic);

					GlideUtils.initRoundImage(Glide.with(mContext),mContext, outPic, holder.mHeadPic);



					if ("1".equals(outGrade)) {
						holder.mGradePic.setVisibility(View.VISIBLE);
						holder.mGradePic.setImageResource(R.drawable.v_red);
					} else if ("2".equals(outGrade)) {
						holder.mGradePic.setVisibility(View.VISIBLE);
						holder.mGradePic.setImageResource(R.drawable.v_blue);
					} else {
						holder.mGradePic.setVisibility(View.GONE);
					}
					if (!TextUtils.isEmpty(outName)) {

						// if (outName.length() == 1) {
						// outName = outName + "****";
						// }
						if (outUserId.equals(outHostUserId)) {
							holder.mName.setText("??????");
						} else {
							holder.mName.setText(outName);
						}
					}
					if (!TextUtils.isEmpty(outTime)) {
						long longSendTime = 0;
						try {
							longSendTime = Long.parseLong(outTime);
						} catch (NumberFormatException e) {
							longSendTime = 0;
						}
						if (longSendTime != 0) {
							String showSendTime = TimeUtils.showSendTime(longSendTime);
							holder.mTime.setText(showSendTime);
						}
					}
					// ??????
					if (!TextUtils.isEmpty(outLocation)) {
						if (outLocation.length() > 6) {
							outLocation = outLocation.substring(0, 6) + "...";
						}
						holder.mLocation.setText(outLocation);
					} else {
						holder.mLocation.setText("????????????");
					}
					if (!TextUtils.isEmpty(outContent)) {
						holder.mContent.setText(outContent);
					}
					// boolean zan_Flag = false;
					// for (int i = 0; i < outZanUserId.size(); i++) {
					// if (("" +
					// YCache.getCacheUser(mContext).getUser_id()).equals(outZanUserId.get(i)))
					// {
					// zan_Flag = true;
					// break;
					// }
					// }
					if ("1".equals(outZanStatus)) {
						holder.mZanIcon.setBackgroundResource(R.drawable.sweet_icon_zan_pre);
					} else {
						holder.mZanIcon.setBackgroundResource(R.drawable.sweet_icon_zan);
					}
					// ????????????
					holder.mZanCount.setText(outZanCount);
					holder.mZanIcon.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (!YJApplication.instance.isLoginSucess()) {// ????????????????????????????????????
								toLogin();
								return;
							}
							if (LimitDoubleClicked.isFastDoubleClick500())
								return;
							// final List<String> outZanUser_Id;// ????????????id??????
							final String inZanStatus;
							boolean zanFlag = false;
							if (mClick == 0) {
								// outZanUser_Id = (List<String>)
								// mListAllComment.get(position).get("applaud_user_list");
								inZanStatus = (String) mListAllComment.get(position).get("comments_applaud_status");
							} else {
								// outZanUser_Id = (List<String>)
								// mListHostComment.get(position).get("applaud_user_list");
								inZanStatus = (String) mListHostComment.get(position).get("comments_applaud_status");
							}
							if ("0".equals(inZanStatus)) {
								dianZan(outCommentId, "2", mTheme_id, holder.mZanIcon, holder.mZanCount, position);

							} else {
								removeZan(outCommentId, "2", mTheme_id, holder.mZanIcon, holder.mZanCount, position);

							}
						}
					});
					convertView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (YJApplication.instance.isLoginSucess()) {
								if (!("" + YCache.getCacheUser(mContext).getUser_id()).equals(outUserId)
										&& !("" + YCache.getCacheUser(mContext).getUser_id()).equals(outHostUserId)) {
									return;
								}
							}
							InputMethodManager imm = (InputMethodManager) getSystemService(
									Context.INPUT_METHOD_SERVICE);
							mEtContent.requestFocus();
							// mEtContent.performClick();
							imm.showSoftInput(mEtContent, InputMethodManager.SHOW_IMPLICIT);
							mEtContent.setHint("??????" + outName);
							mUser_id1 = outCommentId;
							mUser_id2 = outUserId;
						}

					});
				}
			}

			return convertView;

		}

	}

	class ViewHolder {
		private RelativeLayout mNoneComment;// ?????????????????????????????????
		private LinearLayout mHaveComment;// ??????????????????
		private ImageView mHeadPic;// ??????
		private ImageView mGradePic;
		private TextView mName;// ???????????????
		private TextView mLocation;// ???????????????
		private TextView mTime;// ????????????
		private TextView mContent;// ????????????
		private TextView mZanCount;// ????????????
		private LinearLayout mContain;// ???????????????
		private TextView mZanIcon;// ????????????
		private LinearLayout mLlHot;// ????????????
		private LinearLayout mLlNew;// ????????????
	}

	public void flush() {
		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// if (!mEditFlag) {
				// curPage = 1;
				// } else {
				// Toast.makeText(mContext, "???????????????????????????",
				// Toast.LENGTH_SHORT).show();
				// }
				if (mClick == 0) {
					page = 1;
					mListView.onRefreshComplete();
					queryNewComment();
				} else {
					page2 = 1;
					mListView.onRefreshComplete();
					queryHostCommentByShop();
				}

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				if (mClick == 0) {
					page++;
					mListView.onRefreshComplete();
					queryNewComment();
				} else {
					page2++;
					mListView.onRefreshComplete();
					queryHostCommentByShop();
				}
			}

		});

		mListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {

				LogYiFu.e("End of List! == ", "End of List!");
			}
		});
		// listView.setOnItemClickListener(this);
	}

	/**
	 * ?????????
	 */
	private void toLogin() {
		Intent intent = new Intent(mContext, LoginActivity.class);
		intent.putExtra("login_register", "login");
		intent.putExtra("sweet_friend", "sweet_friend");
		((FragmentActivity) mContext).startActivity(intent);
		((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
	}

	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// clickFlag = true;
			// Toast.makeText(SweetFriendsDetails.this, "????????????....??????",
			// Toast.LENGTH_SHORT).show();
		}
	};
	private OnClickListener clickListener2 = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// clickFlag = true;
			// Toast.makeText(SweetFriendsDetails.this, "????????????....??????",
			// Toast.LENGTH_SHORT).show();
		}
	};

	class Clickable extends ClickableSpan {
		private final View.OnClickListener mListener;

		public Clickable(View.OnClickListener l) {
			mListener = l;
		}

		/**
		 * ????????????????????????
		 */
		@Override
		public void onClick(View v) {
			mListener.onClick(v);
		}

		/**
		 * ????????????updateDrawState?????? ???????????????TextView??????????????????,??????????????????...
		 */
		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setColor(Color.parseColor("#ff3f8b"));
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (!"".equals(mEtContent.getText().toString().trim())) {
			mSendComment.setBackgroundResource(R.drawable.indiana_shape_shaidan);
		} else {
			mSendComment.setBackgroundResource(R.drawable.sweet_shape_send);
		}

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight,
			int oldBottom) {
		if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > screenHeight / 4)) {
			// Toast.makeText(mContext, "????????????????????????...",
			// Toast.LENGTH_SHORT).show();

		} else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > screenHeight / 4)) {
			if ("".equals(mEtContent.getText().toString().trim())) {
				mEtContent.setHint("????????????????????????~");
			}
			// Toast.makeText(mContext, "????????????????????????...",
			// Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getId()) {
		case R.id.sweet_tv_send:
			if (!isClick) {
				isClick=true;
				if (!YJApplication.instance.isLoginSucess()) {// ????????????????????????????????????
					toLogin();
					isClick=false;
					return;
				}
				if ("????????????????????????~".equals(mEtContent.getHint().toString())) {// ???????????????
					if (mEtContent.getText().toString() == null || "".equals(mEtContent.getText().toString().trim())) {
						isClick=false;
						return;
					}
					mAdapter.notifyDataSetChanged();
					getCity();
				} else {// ???????????????
					commentIn(mUser_id1, mUser_id2, mEtContent.getText().toString());
				}
			}
			break;
		case R.id.sweet_img_back:
			finish();
			break;
		default:
			break;
		}

	}

	/**
	 * ????????????
	 * 
	 * @param this_id
	 *            ??????id?????????id
	 * @param type:1
	 *            ?????????2 ?????? ?????? theme_id ????????????id
	 */
	public void dianZan(final String this_id, final String type, final String theme_id, final TextView tvIconLove,
			final TextView tvLoveCount, final int position) {
		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, 0) {

			protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {

				return ComModel2.sweetZan(mContext, this_id, type, theme_id);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void onPostExecute(FragmentActivity mContext, ReturnInfo result, Exception e) {
				super.onPostExecute(mContext, result, e);
				if (null == e && result != null && "1".equals("" + result.getStatus())) {
					if ("1".equals(type)) {// ??????
						mLoveFlag = true;
						mLoveCount++;
						tvLoveCount.setText("" + mLoveCount);
						tvIconLove.setBackgroundResource(R.drawable.sweet_icon_xihuan_pre);
						// mapDetails.put("applaud_status", "1");
						// mapDetails.put("applaud_num", "" + mLoveCount);
					} else if ("2".equals(type)) {// ??????
						int count;
						if (mClick == 0) {
							count = Integer.parseInt((String) mListAllComment.get(position).get("applaud_num")) + 1;
						} else {
							count = Integer.parseInt((String) mListHostComment.get(position).get("applaud_num")) + 1;
						}
						// String user_ids = "" +
						// YCache.getCacheUser(mContext).getUser_id();
						if (mClick == 0) {
							// ((List<String>)
							// mListAllComment.get(position).get("applaud_user_list")).add(user_ids);
							mListAllComment.get(position).put("comments_applaud_status", "1");
						} else {
							// ((List<String>)
							// mListHostComment.get(position).get("applaud_user_list")).add(user_ids);
							mListHostComment.get(position).put("comments_applaud_status", "1");
						}
						tvIconLove.setBackgroundResource(R.drawable.sweet_icon_zan_pre);
						// zanFlag = true;
						tvLoveCount.setText("" + count);
						if (mClick == 0) {
							mListAllComment.get(position).put("applaud_num", "" + count);
						} else {
							mListHostComment.get(position).put("applaud_num", "" + count);
						}
					}
				}
			}

		}.execute();
	}

	/**
	 * ???????????????
	 * 
	 * @param this_id
	 *            ??????id?????????id
	 * @param type:1
	 *            ?????????2 ?????? ?????? theme_id ????????????id
	 */
	public void removeZan(final String this_id, final String type, final String theme_id, final TextView tvIconLove,
			final TextView tvLoveCount, final int position) {
		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, 0) {

			protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {

				return ComModel2.sweetRemoveZan(mContext, this_id, type, theme_id);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void onPostExecute(FragmentActivity mContext, ReturnInfo result, Exception e) {
				super.onPostExecute(mContext, result, e);
				if (null == e && result != null && "1".equals(result.getStatus())) {
					if ("1".equals(type)) {// ??????
						mLoveFlag = false;
						mLoveCount--;
						tvLoveCount.setText("" + mLoveCount);
						tvIconLove.setBackgroundResource(R.drawable.sweet_icon_xihuan);
						// mapDetails.put("applaud_status", "0");
						// mapDetails.put("applaud_num", "" + mLoveCount);
					} else if ("2".equals(type)) {
						int count;
						if (mClick == 0) {
							count = Integer.parseInt((String) mListAllComment.get(position).get("applaud_num")) - 1;
						} else {
							count = Integer.parseInt((String) mListHostComment.get(position).get("applaud_num")) - 1;
						}
						if (mClick == 0) {
							// ((List<String>)
							// mListAllComment.get(position).get("applaud_user_list"))
							// .remove("" +
							// YCache.getCacheUser(mContext).getUser_id());
							mListAllComment.get(position).put("comments_applaud_status", "0");
						} else {
							// ((List<String>)
							// mListHostComment.get(position).get("applaud_user_list"))
							// .remove("" +
							// YCache.getCacheUser(mContext).getUser_id());
							mListHostComment.get(position).put("comments_applaud_status", "0");
						}
						// outZanUserId.remove("" +
						// YCache.getCacheUser(mContext).getUser_id());
						tvIconLove.setBackgroundResource(R.drawable.sweet_icon_zan);
						// zanFlag = false;
						tvLoveCount.setText("" + count);
						if (mClick == 0) {
							mListAllComment.get(position).put("applaud_num", "" + count);
						} else {
							mListHostComment.get(position).put("applaud_num", "" + count);
						}
					}
				}
			}

		}.execute();
	}

	public void getCity() {
		// ?????????????????????
		new SAsyncTask<Void, Void, UserInfo>(this, R.string.wait) {

			@Override
			protected UserInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
				return ComModel.queryUserInfo(context);
				// return YCache.getCacheUser(context);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, UserInfo result) {
				super.onPostExecute(context, result);
				String city = "";
				try {
					city = DBService.getIntance().queryAreaNameById(Integer.parseInt(result.getProvince()))
							+ DBService.getIntance().queryAreaNameById(Integer.parseInt(result.getCity()));
				} catch (NumberFormatException e) {

				}
				commentTieZi(mTheme_id, mUser_id, mEtContent.getText().toString(), city);
			}

		}.execute();
	}

	/**
	 * 
	 * @param comment_id
	 *            ??????id
	 * @param receive_user_id
	 *            ???????????????id?????????????????????????????????????????????
	 * @param content
	 *            ??????
	 */
	public void commentIn(final String comment_id, final String receive_user_id, final String content) {
		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, 0) {

			protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {

				return ComModel2.sweetCommentIn(mContext, comment_id, receive_user_id, content);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity mContext, ReturnInfo result, Exception e) {
				super.onPostExecute(mContext, result, e);
				isClick=false;
				if (null == e && result != null && "1".equals(result.getStatus())) {
					ToastUtil.showLongText(mContext, "????????????");
					mEtContent.setText("");
					if (mClick == 0) {
						if (!isHotComment) {
							page = 1;
							queryNewComment();
						} else {
							queryHotComment(false);
						}
					} else {
						page2 = 1;
						queryHostCommentByShop();
					}
				}
			}

		}.execute();
	}

	/**
	 * 
	 * @param theme_id
	 *            ??????id
	 * @param base_user_id
	 *            ??????id
	 * @param content
	 *            ??????
	 * @param location
	 *            ??????
	 */
	public void commentTieZi(final String theme_id, final String base_user_id, final String content,
			final String location) {
		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, 0) {

			protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {

				return ComModel2.sweetCommentTieZi(mContext, theme_id, base_user_id, content, location);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity mContext, ReturnInfo result, Exception e) {
				super.onPostExecute(mContext, result, e);
				isClick=false;
				if (null == e && result != null && "1".equals(result.getStatus())) {
					ToastUtil.showLongText(mContext, "????????????");
					mEtContent.setText("");
					if (mClick == 0) {
						page = 1;
						queryNewComment();
					} else {
						page2 = 1;
						queryHostCommentByShop();
					}
				}
			}

		}.execute();
	}
}
