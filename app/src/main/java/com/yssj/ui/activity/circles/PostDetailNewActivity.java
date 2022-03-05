//<<<<<<< .mine
////package com.yssj.ui.activity.circles;
////
////import java.io.File;
////import java.util.ArrayList;
////import java.util.Date;
////import java.util.HashMap;
////import java.util.LinkedList;
////import java.util.List;
////import java.util.Map;
////
////import android.annotation.SuppressLint;
////import android.content.Context;
////import android.content.Intent;
////import android.graphics.drawable.ColorDrawable;
////import android.net.Uri;
////import android.os.Bundle;
////import android.support.v4.app.FragmentActivity;
////import android.text.TextUtils;
////import android.util.Log;
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.View.OnClickListener;
////import android.view.ViewGroup;
////import android.view.Window;
////import android.view.WindowManager;
////import android.widget.BaseAdapter;
////import android.widget.Button;
////import android.widget.EditText;
////import android.widget.ImageView;
////import android.widget.LinearLayout;
////import android.widget.LinearLayout.LayoutParams;
////import android.widget.PopupWindow;
////import android.widget.TextView;
////
////import com.handmark.pulltorefresh.library.PullToRefreshBase;
////import com.handmark.pulltorefresh.library.PullToRefreshListView;
////import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
////import com.umeng.analytics.MobclickAgent;
////import com.yssj.YJApplication;
////import com.yssj.activity.R;
////import com.yssj.app.SAsyncTask;
////import com.yssj.custom.view.RoundImageButton;
////import com.yssj.custom.view.ToLoginDialog;
////import com.yssj.entity.ReturnInfo;
////import com.yssj.model.ComModel2;
////import com.yssj.ui.activity.setting.InformCircleActivity;
////import com.yssj.ui.fragment.circles.CirclePostListFragment;
////import com.yssj.utils.DateUtil;
////import com.yssj.utils.SetImageLoader;
////import com.yssj.utils.ShareUtil;
////import com.yssj.utils.StringUtils;
////import com.yssj.utils.ToastUtil;
////
////@SuppressLint("NewApi")
////public class PostDetailNewActivity extends FragmentActivity implements
////		OnClickListener {
////	private String news_id;
////	private String user_id;
////	private String circle_id;
////
////	private LinearLayout img_back;
////	private TextView tvTitle_base;
////	private Button btn_right;
////	private int currPage = 1;
////
////	private TextView tv_advice, tv_msg_count, tv_rm_count, tv_post_nickname,
////			tv_post_time, tv_title, tv_content;
////	private Button btn_title, btn_reply, btn_comment;
////	private RoundImageButton img_post_user;
////	private EditText et_comment;
////	private LinearLayout ll_imglist_contain, ll_comment_contain;
////
////	private View ll_comment;
////	private Button btn_more; // 查看更多评论
////	private ImageView img_right_icon, img_most_right_icon;
////
////	private List<Map<String, Object>> newResult = new ArrayList<Map<String, Object>>();
////
////	private LinearLayout create_louzhu_layout, create_collect_post_layout;
////
////	private PullToRefreshListView mListView;
////
////	private String[] images;
////
////	private MyAdapter adapter;
////
////	private Map<String, Object> map;
////
////	private ToLoginDialog loginDialog;
////	private boolean islouRefresh=false;
////
////	@Override
////	protected void onCreate(Bundle saveInstanceState) {
////		super.onCreate(saveInstanceState);
////		requestWindowFeature(Window.FEATURE_NO_TITLE);
////		getWindow().setSoftInputMode(
////				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
////
////		setContentView(R.layout.activity_circle_post_detail);
////		news_id = getIntent().getStringExtra("news_id");
////		user_id = getIntent().getStringExtra("user_id");
////		circle_id = getIntent().getStringExtra("circle_id");
////
////		// initView();
////		initPostDetailData();
////		initPostCommentListData("");
////		mListView = (PullToRefreshListView) findViewById(R.id.postList);
////		mListView.setMode(Mode.PULL_FROM_END);
////
////		mListView
////				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2() {
////
////					@Override
////					public void onPullDownToRefresh(
////							PullToRefreshBase refreshView) {
////						// currPage=1;
////						// initPostCommentListData("");
////					}
////
////					@Override
////					public void onPullUpToRefresh(PullToRefreshBase refreshView) {
////						currPage++;
////						if(isLou){
////						initPostCommentListData("");}else{
////							initPostCommentListData(user_id);
////						}
////					}
////				});
////
////		et_comment = (EditText) findViewById(R.id.et_comment);
////		img_back = (LinearLayout) findViewById(R.id.img_back);
////		img_back.setOnClickListener(this);
////		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
////		tvTitle_base.setText("帖子详情");
////		btn_reply = (Button) findViewById(R.id.btn_reply); // 回复
////		btn_reply.setOnClickListener(this);
////		btn_reply.setVisibility(View.GONE);
////		btn_comment = (Button) findViewById(R.id.btn_comment); // 发表评论
////		btn_comment.setOnClickListener(this);
////
////		img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
////		img_right_icon.setVisibility(View.GONE);
////		img_right_icon.setImageResource(R.drawable.postdetail_share);
////		img_right_icon.setOnClickListener(this);
////
////		img_most_right_icon = (ImageView) findViewById(R.id.img_most_right_icon);
////		img_most_right_icon.setVisibility(View.VISIBLE);
////		img_most_right_icon.setImageResource(R.drawable.mine_message_center);
////		img_most_right_icon.setOnClickListener(this);
////
////		ll_comment = findViewById(R.id.ll_comment);
////
////	}
////
////	/*
////	 * @Override protected void onResume() { super.onResume();
////	 * JPushInterface.onResume(this); }
////	 * 
////	 * @Override protected void onPause() { super.onPause();
////	 * JPushInterface.onPause(this);
////	 * 
////	 * }
////	 */
////	@Override
////	protected void onResume() {
////		super.onResume();
////		MobclickAgent.onResume(this);
////	}
////
////	@Override
////	protected void onPause() {
////		// TODO Auto-generated method stub
////		super.onPause();
////		YJApplication.getLoader().stop();
////		MobclickAgent.onPause(this);
////	}
////
////	private class MyAdapter extends BaseAdapter {
////
////		private int width;
////
////		public MyAdapter() {
////			super();
////			this.width = PostDetailNewActivity.this.getResources()
////					.getDisplayMetrics().widthPixels;
////			// height=width;
////		}
////
////		@Override
////		public int getCount() {
////			int count = 0;
////			count++;// 标题
////			count++;// 楼主层
////			count += images == null ? 0 : images.length;// 图片集合
////			count += newResult.size();// 评论集合
////			return count;
////		}
////
////		@Override
////		public Object getItem(int arg0) {
////			// TODO Auto-generated method stub
////			return null;
////		}
////
////		@Override
////		public long getItemId(int arg0) {
////			// TODO Auto-generated method stub
////			return arg0;
////		}
////
////		@Override
////		public View getView(int position, View view, ViewGroup arg2) {
////			PostItem items;
////			if (view == null) {
////				view = LayoutInflater.from(PostDetailNewActivity.this).inflate(
////						R.layout.comment_item, null);
////				items = new PostItem();
////				items.title = view.findViewById(R.id.title);
////				items.contentView = view.findViewById(R.id.louzhu);
////				items.iamge = (ImageView) view.findViewById(R.id.image);
////				// items.iamge.setMinimumHeight(height);
////				// items.iamge.getLayoutParams().height=height;
////				items.allTitle = (TextView) view.findViewById(R.id.all_title);
////				items.lCouny = (TextView) view.findViewById(R.id.look_count);
////				items.cCount = (TextView) view.findViewById(R.id.comment_count);
////				items.name = (TextView) view.findViewById(R.id.name);
////				items.ceng = (TextView) view.findViewById(R.id.ceng_name);
////				items.content = (TextView) view.findViewById(R.id.content);
////				items.time = (TextView) view.findViewById(R.id.time);
////				items.btTitle = (TextView) view.findViewById(R.id.bt_title);
////				items.user = (RoundImageButton) view
////						.findViewById(R.id.img_louzhu);
////				items.imageView = view.findViewById(R.id.imageView);
////				// items.imageRep=(TextView) view.findViewById(R.id.image_rep);
////				// items.rep=(TextView) view.findViewById(R.id.rep);
////				items.diver = view.findViewById(R.id.diver);
////				view.setTag(items);
////			} else {
////				items = (PostItem) view.getTag();
////			}
////			if (position == 0) {// 主標題
////				items.diver.setVisibility(view.VISIBLE);
////				items.title.setVisibility(view.VISIBLE);
////				items.imageView.setVisibility(view.GONE);
////				items.contentView.setVisibility(view.GONE);
////				items.allTitle.setText(map.get("title").toString());
////				items.btTitle.setText(map.get("circleTitle").toString());// 圈圈名称
////				if (map.get("circleTitle").toString().equals("衣蝠官方论坛")) {
////					items.btTitle.setPadding(40, 0, 60, 0);
////				}
////				items.lCouny.setText(map.get("skim_count").toString());
////				items.cCount.setText(map.get("rn_count").toString());
////				return view;
////			}
////			items.diver.setVisibility(view.GONE);
////			if (position == 1) {
////				items.title.setVisibility(view.GONE);
////				items.imageView.setVisibility(view.GONE);
////				items.contentView.setVisibility(view.VISIBLE);
////				if (!TextUtils.isEmpty(map.get("upic").toString())) {
////					items.user.setVisibility(view.VISIBLE);
////					items.user.setTag(map.get("upic").toString());
////					SetImageLoader.initImageLoader(null, items.user,
////							map.get("upic").toString(), "");
////					items.user.setOnClickListener(new OnClickListener() {
////
////						@Override
////						public void onClick(View arg0) {
////							Intent intent = new Intent(getApplicationContext(),
////									CircleCommonFragmentActivity.class);
////							intent.putExtra("user_id",
////									(String) map.get("user_id"));
////							intent.putExtra("flag", "circleHomePage");
////							startActivity(intent);
////						}
////					});
////				} else {
////					items.user.setVisibility(view.INVISIBLE);
////				}
////
////				items.ceng.setText("楼主");
////
////				// if(images==null){
////				// // items.rep.setVisibility(view.VISIBLE);
////				// // items.rep.setOnClickListener(new OnClickListener() {
////				// //
////				// // @Override
////				// // public void onClick(View arg0) {
////				// // ll_comment.setVisibility(View.VISIBLE);
////				// //
////				// // }
////				// // });
////				//
////				//
////				// }else{
////				// items.rep.setVisibility(view.GONE);
////				// }
////
////				items.name.setText(map.get("nickname").toString());
////				items.time.setText(DateUtil.twoDateDistance(
////						new Date((Long) map.get("send_time")),
////						new Date(System.currentTimeMillis())));
////				items.content.setText(map.get("content").toString());
////				return view;
////			}
////
////			if (images != null && position < images.length + 2) {
////				items.title.setVisibility(view.GONE);
////				items.imageView.setVisibility(view.VISIBLE);
////				items.contentView.setVisibility(view.GONE);
////				String url = images[position - 2].split(":")[0];
////				float o = Float.parseFloat(images[position - 2].split(":")[1]);
////				items.iamge.getLayoutParams().height = (int) (width / o);
////				items.iamge.setTag(url);
////				SetImageLoader.initImageLoader(null, items.iamge, url, "!560");
////
////				// if(position-1==images.length){
////				// items.imageRep.setVisibility(view.VISIBLE);
////				//
////				// items.imageRep.setOnClickListener(new OnClickListener() {
////				//
////				// @Override
////				// public void onClick(View arg0) {
////				// ll_comment.setVisibility(View.VISIBLE);
////				// }
////				// });
////				//
////				// }else{
////				// items.imageRep.setVisibility(view.GONE);
////				// }
////
////				return view;
////			}
////
////			// 剩下的都是評論內容
////
////			if (images == null) {
////				position = position - 2;
////			} else {
////				position = position - 2 - images.length;
////			}
////
////			items.title.setVisibility(view.GONE);
////			items.imageView.setVisibility(view.GONE);
////			items.contentView.setVisibility(view.VISIBLE);
////			// items.rep.setVisibility(view.GONE);
////
////			Map<String, Object> maps = newResult.get(position);
////
////			items.name.setText(maps.get("nickname").toString());
////
////			items.content.setText(maps.get("content").toString());
////
////			items.user.setTag(maps.get("pic").toString());
////			SetImageLoader.initImageLoader(null, items.user, maps.get("pic")
////					.toString(), "");
////
////			items.user.setOnClickListener(new OnClickLintener(position));
////
////			if (user_id.equals(maps.get("user_id").toString())) {
////				items.ceng.setText("楼主");
////			} else {
////				if (position == 0) {
////					items.ceng.setText("沙发");
////				} else if (position == 1) {
////					items.ceng.setText("板凳");
////				} else {
////					int lou = position + 1;
////					items.ceng.setText(lou + "楼");
////				}
////
////			}
////
////			items.time.setText(DateUtil.twoDateDistance(
////					new Date((Long) maps.get("ren_time")),
////					new Date(System.currentTimeMillis())));
////
////			return view;
////		}
////
////	}
////
////	private static class PostItem {
////		View title;
////
////		View contentView;
////
////		View imageView;
////
////		ImageView iamge;
////
////		TextView allTitle, btTitle, lCouny, cCount, name, ceng, content, time;
////
////		RoundImageButton user;
////
////		View diver;
////
////	}
////
////	private class OnClickLintener implements OnClickListener {
////
////		private int positon;
////
////		public OnClickLintener(int positon) {
////			super();
////			this.positon = positon;
////		}
////
////		@Override
////		public void onClick(View arg0) {
////			Intent intent = new Intent(getApplicationContext(),
////					CircleCommonFragmentActivity.class);
////			intent.putExtra("user_id",
////					newResult.get(this.positon).get("user_id").toString());
////			intent.putExtra("flag", "circleHomePage");
////			startActivity(intent);
////		}
////	}
////
////	private void initView() {
////
////		img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
////		img_right_icon.setVisibility(View.VISIBLE);
////		img_right_icon.setImageResource(R.drawable.postdetail_share);
////		img_right_icon.setOnClickListener(this);
////
////		img_most_right_icon = (ImageView) findViewById(R.id.img_most_right_icon);
////		img_most_right_icon.setVisibility(View.VISIBLE);
////		img_most_right_icon.setImageResource(R.drawable.mine_message_center);
////		img_most_right_icon.setOnClickListener(this);
////		/*
////		 * btn_right = (Button) findViewById(R.id.btn_right);
////		 * btn_right.setVisibility(View.VISIBLE); btn_right.setText("...");
////		 * btn_right.setOnClickListener(this);
////		 */
////
////		tv_advice = (TextView) findViewById(R.id.tv_advice); // 提示
////		tv_msg_count = (TextView) findViewById(R.id.tv_msg_count); // 回复数
////		tv_rm_count = (TextView) findViewById(R.id.tv_rm_count); // 浏览数
////		tv_post_nickname = (TextView) findViewById(R.id.tv_post_nickname); // 昵称
////		tv_post_time = (TextView) findViewById(R.id.tv_post_time); // 发帖时间
////		tv_title = (TextView) findViewById(R.id.tv_title); // 标题
////		tv_content = (TextView) findViewById(R.id.tv_content); // 内容
////
////		btn_title = (Button) findViewById(R.id.btn_title); // 显示标题
////
////		img_back = (LinearLayout) findViewById(R.id.img_back);
////		img_back.setOnClickListener(this);
////		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
////		tvTitle_base.setText("帖子详情");
////		btn_reply = (Button) findViewById(R.id.btn_reply); // 回复
////		btn_reply.setOnClickListener(this);
////		btn_comment = (Button) findViewById(R.id.btn_comment); // 发表评论
////		btn_comment.setOnClickListener(this);
////
////		img_post_user = (RoundImageButton) findViewById(R.id.img_post_user); // 用户头像
////
////		// lv_imglist = (ListView) findViewById(R.id.lv_imglist); // 图片列表
////		// lv_comment = (ListView) findViewById(R.id.lv_comment); // 评论列表
////		ll_imglist_contain = (LinearLayout) findViewById(R.id.ll_imglist_contain);
////		ll_comment_contain = (LinearLayout) findViewById(R.id.ll_comment_contain);
////		// ll_comment = (LinearLayout) findViewById(R.id.ll_comment);
////
////		et_comment = (EditText) findViewById(R.id.et_comment); // 填写评论
////		et_comment.setFocusable(false);
////		btn_more = new Button(this);
////		btn_more.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
////				LayoutParams.WRAP_CONTENT));
////		btn_more.setText("查看更多");
////		btn_more.setOnClickListener(this);
////
////	}
////
////	/** 请求帖子详情列表 */
////	private void initPostDetailData() {
////		new SAsyncTask<Void, Void, Map<String, Object>>(this) {
////
////			@Override
////			protected Map<String, Object> doInBackground(
////					FragmentActivity context, Void... params) throws Exception {
////				if (YJApplication.instance.isLoginSucess()) {
////					return ComModel2.getPostInfo(context, news_id);
////				} else {
////					return ComModel2.getPostInfo2(context, news_id);
////				}
////
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					Map<String, Object> result, Exception e) {
////				super.onPostExecute(context, result, e);
////				if (null == e) {
////					if (result != null) {
////						map = result;
////						if (TextUtils
////								.isEmpty(result.get("pic_list").toString()) == false) {
////							images = ((String) result.get("pic_list"))
////									.split(",");
////						}
////
////						adapter = new MyAdapter();
////						mListView.setAdapter(adapter);
////						/**
////						 * tv_rm_count.setText((CharSequence)result.get(
////						 * "rn_count")); tv_msg_count.setText("0");
////						 * tv_post_nickname
////						 * .setText((CharSequence)result.get("nickname"));
////						 * tv_post_time.setText(DateUtil.twoDateDistance(new
////						 * Date((Long) result.get("send_time")),new
////						 * Date(System.currentTimeMillis())));
////						 * tv_title.setText((CharSequence)result.get("title"));
////						 * tv_content
////						 * .setText((CharSequence)result.get("tv_content"));
////						 * btn_title.setText((CharSequence)result.get("title"));
////						 * String url=(String)result.get("upic");
////						 * if(TextUtils.isEmpty(url)==false){
////						 * SetImageLoader.initImageLoader(context,
////						 * img_post_user, (String)result.get("upic")); }else{
////						 * img_post_user.setVisibility(View.GONE); }
////						 * 
////						 * if(result.get("pic_list") != null){ String str[] =
////						 * ((String)result.get("pic_list")).split(","); for (int
////						 * i = 0; i < str.length; i++) { ImageView imgView = new
////						 * ImageView(context); imgView.setLayoutParams(new
////						 * LayoutParams(LayoutParams.MATCH_PARENT,600));
////						 * imgView.setScaleType(ScaleType.FIT_XY);
////						 * SetImageLoader.initImageLoader(null, imgView,
////						 * str[i]); ll_imglist_contain.addView(imgView); } }
////						 */
////
////					}
////				}
////			}
////
////		}.execute();
////	}
////
////	/** 请求帖子评论列表 */
////	private void initPostCommentListData(final String user_id2) {
////		new SAsyncTask<Void, Void, List<Map<String, Object>>>(this) {
////
////			@Override
////			protected List<Map<String, Object>> doInBackground(
////					FragmentActivity context, Void... params) throws Exception {
////				if (YJApplication.instance.isLoginSucess()) {
////					return ComModel2.getCommentList(context, news_id, currPage,
////							user_id2);
////				} else {
////					return ComModel2.getCommentList2(context, news_id,
////							currPage, user_id2);
////				}
////
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					List<Map<String, Object>> result, Exception e) {
////				super.onPostExecute(context, result, e);
////				if (null == e) {
////					newResult.addAll(result);
////					if (adapter != null) {
////						adapter.notifyDataSetChanged();
////					}
////
////					mListView.onRefreshComplete();
////
////					/**
////					 * if(newResult != null){ for (int i = 0; i <
////					 * newResult.size(); i++) { View view =
////					 * View.inflate(context,
////					 * R.layout.activity_post_detail_comment_item, null); //
////					 * TextView tv_comment_num = (TextView)
////					 * view.findViewById(R.id.tv_comment_num); TextView
////					 * tv_comment_nickname = (TextView)
////					 * view.findViewById(R.id.tv_comment_nickname); TextView
////					 * tv_comment_content = (TextView)
////					 * view.findViewById(R.id.tv_comment_content); TextView
////					 * tv_comment_position = (TextView)
////					 * view.findViewById(R.id.tv_comment_position); TextView
////					 * tv_comment_date = (TextView)
////					 * view.findViewById(R.id.tv_comment_date); RoundImageButton
////					 * img_post_user = (RoundImageButton)
////					 * view.findViewById(R.id.img_post_user);
////					 * 
////					 * // tv_comment_num.setText(i+1 + "");
////					 * tv_comment_nickname.setText
////					 * ((String)newResult.get(i).get("nickname"));
////					 * tv_comment_content
////					 * .setText((String)newResult.get(i).get("content"));
////					 * 
////					 * String uri=(String)newResult.get(i).get("pic");
////					 * 
////					 * if(TextUtils.isEmpty(uri)==false){
////					 * SetImageLoader.initImageLoader(null, img_post_user, uri);
////					 * }else{ img_post_user.setVisibility(View.GONE); }
////					 * 
////					 * if(user_id.equals((String)newResult.get(i).get("user_id")
////					 * )){ tv_comment_position.setText("楼主"); }else{
////					 * tv_comment_position.setText("板凳"); }
////					 * tv_comment_date.setText(DateUtil.twoDateDistance(new
////					 * Date((Long) newResult.get(i).get("ren_time")),new
////					 * Date(System.currentTimeMillis())));
////					 * view.setLayoutParams(new
////					 * LayoutParams(LayoutParams.MATCH_PARENT,
////					 * LayoutParams.WRAP_CONTENT));
////					 * ll_comment_contain.addView(view); }
////					 * 
////					 * if(newResult.size() == 0){
////					 * 
////					 * }else{ ll_comment_contain.addView(btn_more); }
////					 * 
////					 * }
////					 */
////				}
////			}
////
////		}.execute();
////	}
////
////	/** 添加评论 */
////	private void addPostComment(final String content) {
////		new SAsyncTask<Void, Void, ReturnInfo>(this, R.string.wait) {
////
////			@Override
////			protected ReturnInfo doInBackground(FragmentActivity context,
////					Void... params) throws Exception {
////				return ComModel2.postComment(context, news_id, content,
////						circle_id);
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					ReturnInfo returnInfo, Exception e) {
////				super.onPostExecute(context, returnInfo, e);
////				if (null == e) {
////					if (returnInfo != null
////							&& returnInfo.getStatus().equals("1")) {
////						et_comment.setText("");
////						// ll_comment.setVisibility(View.GONE);
////
////						newResult.clear();
////						// ll_comment_contain.removeAllViews();
////						initPostCommentListData("");
////						currPage = 1;
////						ToastUtil.showShortText(getApplication(),
////								returnInfo.getMessage());
////					}
////				}
////			}
////		}.execute();
////	}
////
////	/** 收藏帖子 */
////	private void CollectPost() {
////		new SAsyncTask<Void, Void, ReturnInfo>(this, R.string.wait) {
////
////			@Override
////			protected ReturnInfo doInBackground(FragmentActivity context,
////					Void... params) throws Exception {
////				return ComModel2.postCollect(context, news_id, circle_id);
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					ReturnInfo returnInfo, Exception e) {
////				super.onPostExecute(context, returnInfo, e);
////				if (null == e) {
////					if (returnInfo != null
////							&& returnInfo.getStatus().equals("1")) {
////						ToastUtil.showShortText(getApplication(),
////								returnInfo.getMessage());
////					}
////				}
////			}
////		}.execute();
////	}
////
////	@Override
////	public void onClick(View v) {
////		switch (v.getId()) {
////		case R.id.img_back: // 返回
////			finish();
////			break;
////		case R.id.img_right_icon: // 分享
////			// ToastUtil.showShortText(getApplication(), "努力为您开发分享功能中！");
////			oneKeyShare();
////			break;
////		case R.id.img_most_right_icon: // 。。。
////			FilterPopupWindow popupWindow = new FilterPopupWindow(this);
////			popupWindow.showAsDropDown(img_most_right_icon, 0, 10);
////			break;
////		case R.id.btn_comment:
////			if (!YJApplication.instance.isLoginSucess()) {
////
////				if (loginDialog == null) {
////					loginDialog = new ToLoginDialog(this);
////				}
////				loginDialog.show();
////				loginDialog.setRequestCode(3212);
////				return;
////			}
////			if (TextUtils.isEmpty(et_comment.getText().toString())) {
////				ToastUtil.showShortText(getApplication(), "回复内容不能为空");
////				return;
////			}
////			// if (StringUtils.containsEmoji(et_comment.getText().toString())) {
////			// ToastUtil.showShortText(PostDetailNewActivity.this, "不能输入特殊字符");
////			// return;
////			// }
////			String content = et_comment.getText().toString();
////			addPostComment(content);//提交内容
////
////			break;
////		case R.id.btn_reply: // 回复
////			et_comment.requestFocus();
////			getWindow().setSoftInputMode(
////					WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
////			break;
////
////		}
////
////		if (v == btn_more) {
////			currPage++;
////			ll_comment_contain.removeAllViews();
////			initPostCommentListData("");
////		}
////	}
////
////	// 一键分享
////	private void oneKeyShare() {
////		Intent intent = new Intent();
////		File[] imageFiles = ShareUtil.getImage();
////		ArrayList<Uri> images = new ArrayList<Uri>();
////		for (int i = 0; i < imageFiles.length; i++) {
////			images.add(Uri.fromFile(imageFiles[i]));
////		}
////		MyLogYiFu.e("TAG", "images.size=" + images.size() + "files.length="
////				+ imageFiles.length);
////		intent.setAction(Intent.ACTION_SEND_MULTIPLE);
////		intent.setType("image/*");
////		// intent.setType("text/plain");
////		// intent.setData(Uri.parse(YUrl.QUERY_SHOP_DETAILS));
////		// intent.putExtra(Intent.EXTRA_SUBJECT, "分享到");
////		// intent.putExtra(Intent.EXTRA_TEXT, "值得来看哦！");
////		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, images);
////		startActivity(Intent.createChooser(intent, "分享到"));
////
////	}
////
////	private boolean isLou = true;
////
////	/** 只看楼主和收藏帖子的popupwindow */
////	private class FilterPopupWindow extends PopupWindow implements
////			OnClickListener {
////		private Context mContext;
////		private LinearLayout create_louzhu_layout, create_collect_post_layout,
////				create_inform_layout;
////		private String[] names;
////
////		private TextView is_lou;
////
////		public FilterPopupWindow(Context context) {
////			super(context);
////			mContext = context;
////			init();
////		}
////
////		public FilterPopupWindow(Context context, String[] names) {
////			super(context);
////			mContext = context;
////			this.names = names;
////			init();
////		}
////
////		private void init() {
////			setWidth(LayoutParams.WRAP_CONTENT);
////			setHeight(LayoutParams.WRAP_CONTENT);
////			ColorDrawable dw = new ColorDrawable(0x00);
////			setBackgroundDrawable(dw);
////			View view = LayoutInflater.from(mContext).inflate(
////					R.layout.popup_more, null);
////			create_louzhu_layout = (LinearLayout) view
////					.findViewById(R.id.create_louzhu_layout);
////			create_collect_post_layout = (LinearLayout) view
////					.findViewById(R.id.create_collect_post_layout);
////			create_inform_layout = (LinearLayout) view
////					.findViewById(R.id.create_inform_layout);
////			is_lou = (TextView) view.findViewById(R.id.is_lou);
////			if (isLou) {
////				is_lou.setText("只看楼主");
////			} else {
////				is_lou.setText("全部");
////			}
////			create_louzhu_layout.setOnClickListener(this);
////			create_collect_post_layout.setOnClickListener(this);
////			create_inform_layout.setOnClickListener(this);
////			setContentView(view);
////			setAnimationStyle(R.style.PopupWindowAnimation);
////			setOutsideTouchable(true);
////			setFocusable(true);
////		}
////
////		@Override
////		public void onClick(View v) {
////			// Intent intent = new Intent();
////			// int enterAnimID = R.anim.slide_right_in;
////			// int exitAnimID = R.anim.slide_left_out;
////			int id = v.getId();
////			if (id == R.id.create_louzhu_layout) {
////				if (isLou) {
////					newResult.clear();
////					currPage = 1;
////					// ll_comment_contain.removeAllViews();
////					initPostCommentListData(user_id);
////					isLou = false;
////					
////				} else {
////					newResult.clear();
////					currPage = 1;
////					// ll_comment_contain.removeAllViews();
////					initPostCommentListData("");
////					isLou = true;
////				}
////
////			} else if (id == R.id.create_collect_post_layout) {
////				if (!YJApplication.instance.isLoginSucess()) {
////
////					if (loginDialog == null) {
////						loginDialog = new ToLoginDialog(
////								PostDetailNewActivity.this);
////					}
////					loginDialog.setRequestCode(3212);
////					loginDialog.show();
////
////					return;
////				}
////				CollectPost();
////
////			} else if (id == R.id.create_inform_layout) {
////				if (!YJApplication.instance.isLoginSucess()) {
////
////					if (loginDialog == null) {
////						loginDialog = new ToLoginDialog(
////								PostDetailNewActivity.this);
////					}
////					loginDialog.setRequestCode(3212);
////					loginDialog.show();
////
////					return;
////				}
////				Intent intent = new Intent(PostDetailNewActivity.this,
////						InformCircleActivity.class);
////				startActivity(intent);
////			}
////			// enterAnimID = R.anim.slide_down_in;
////			// exitAnimID = R.anim.slide_top_in;
////			dismiss();
////		}
////
////	}
////
////	@Override
////	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
////		// TODO Auto-generated method stub
////		super.onActivityResult(arg0, arg1, arg2);
////		if (arg0 == 3212) {
////			if (arg1 == -1) {
////				initPostDetailData();
////				// initPostCommentListData("");
////			}
////		}
////	}
////
////}
//=======
//package com.yssj.ui.activity.circles;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
//import com.umeng.analytics.MobclickAgent;
//import com.yssj.YJApplication;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.RoundImageButton;
//import com.yssj.custom.view.ToLoginDialog;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.setting.InformCircleActivity;
//import com.yssj.ui.fragment.circles.CirclePostListFragment;
//import com.yssj.utils.DateUtil;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ShareUtil;
//import com.yssj.utils.StringUtils;
//import com.yssj.utils.ToastUtil;
//
//@SuppressLint("NewApi")
//public class PostDetailNewActivity extends FragmentActivity 
//		 {
//	private String news_id;
//	private String user_id;
//	private String circle_id;
//
//	private LinearLayout img_back;
//	private TextView tvTitle_base;
//	private Button btn_right;
//	private int currPage = 1;
//
//	private TextView tv_advice, tv_msg_count, tv_rm_count, tv_post_nickname,
//			tv_post_time, tv_title, tv_content;
//	private Button btn_title, btn_reply, btn_comment;
//	private RoundImageButton img_post_user;
//	private EditText et_comment;
//	private LinearLayout ll_imglist_contain, ll_comment_contain;
//
//	private View ll_comment;
//	private Button btn_more; // 查看更多评论
//	private ImageView img_right_icon, img_most_right_icon;
//
//	private List<Map<String, Object>> newResult = new ArrayList<Map<String, Object>>();
//
//	private LinearLayout create_louzhu_layout, create_collect_post_layout;
//
//	private PullToRefreshListView mListView;
//
//	private String[] images;
//
////	private MyAdapter adapter;
//
//	private Map<String, Object> map;
//
//	private ToLoginDialog loginDialog;
//	private boolean islouRefresh=false;
//
//	@Override
//	protected void onCreate(Bundle saveInstanceState) {
//		super.onCreate(saveInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setSoftInputMode(
//				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//		setContentView(R.layout.activity_circle_post_detail);
//		news_id = getIntent().getStringExtra("news_id");
//		user_id = getIntent().getStringExtra("user_id");
//		circle_id = getIntent().getStringExtra("circle_id");
//
//		// initView();
////		initPostDetailData();
////		initPostCommentListData("");
////		mListView = (PullToRefreshListView) findViewById(R.id.postList);
////		mListView.setMode(Mode.PULL_FROM_END);
////
////		mListView
////				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2() {
////
////					@Override
////					public void onPullDownToRefresh(
////							PullToRefreshBase refreshView) {
////						// currPage=1;
////						// initPostCommentListData("");
////					}
////
////					@Override
////					public void onPullUpToRefresh(PullToRefreshBase refreshView) {
////						currPage++;
////						if(isLou){
////						initPostCommentListData("");}else{
////							initPostCommentListData(user_id);
////						}
////					}
////				});
////
////		et_comment = (EditText) findViewById(R.id.et_comment);
////		img_back = (LinearLayout) findViewById(R.id.img_back);
////		img_back.setOnClickListener(this);
////		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
////		tvTitle_base.setText("帖子详情");
////		btn_reply = (Button) findViewById(R.id.btn_reply); // 回复
////		btn_reply.setOnClickListener(this);
////		btn_reply.setVisibility(View.GONE);
////		btn_comment = (Button) findViewById(R.id.btn_comment); // 发表评论
////		btn_comment.setOnClickListener(this);
////
////		img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
////		img_right_icon.setVisibility(View.GONE);
////		img_right_icon.setImageResource(R.drawable.postdetail_share);
////		img_right_icon.setOnClickListener(this);
////
////		img_most_right_icon = (ImageView) findViewById(R.id.img_most_right_icon);
////		img_most_right_icon.setVisibility(View.VISIBLE);
////		img_most_right_icon.setImageResource(R.drawable.mine_message_center);
////		img_most_right_icon.setOnClickListener(this);
////
////		ll_comment = findViewById(R.id.ll_comment);
//
//	}
//
//	/*
//	 * @Override protected void onResume() { super.onResume();
//	 * JPushInterface.onResume(this); }
//	 * 
//	 * @Override protected void onPause() { super.onPause();
//	 * JPushInterface.onPause(this);
//	 * 
//	 * }
//	 */
////	@Override
////	protected void onResume() {
////		super.onResume();
////		MobclickAgent.onResume(this);
////	}
////
////	@Override
////	protected void onPause() {
////		// TODO Auto-generated method stub
////		super.onPause();
////		YJApplication.getLoader().stop();
////		MobclickAgent.onPause(this);
////	}
////
////	private class MyAdapter extends BaseAdapter {
////
////		private int width;
////
////		public MyAdapter() {
////			super();
////			this.width = PostDetailNewActivity.this.getResources()
////					.getDisplayMetrics().widthPixels;
////			// height=width;
////		}
////
////		@Override
////		public int getCount() {
////			int count = 0;
////			count++;// 标题
////			count++;// 楼主层
////			count += images == null ? 0 : images.length;// 图片集合
////			count += newResult.size();// 评论集合
////			return count;
////		}
////
////		@Override
////		public Object getItem(int arg0) {
////			// TODO Auto-generated method stub
////			return null;
////		}
////
////		@Override
////		public long getItemId(int arg0) {
////			// TODO Auto-generated method stub
////			return arg0;
////		}
////
////		@Override
////		public View getView(int position, View view, ViewGroup arg2) {
////			PostItem items;
////			if (view == null) {
////				view = LayoutInflater.from(PostDetailNewActivity.this).inflate(
////						R.layout.comment_item, null);
////				items = new PostItem();
////				items.title = view.findViewById(R.id.title);
////				items.contentView = view.findViewById(R.id.louzhu);
////				items.iamge = (ImageView) view.findViewById(R.id.image);
////				// items.iamge.setMinimumHeight(height);
////				// items.iamge.getLayoutParams().height=height;
////				items.allTitle = (TextView) view.findViewById(R.id.all_title);
////				items.lCouny = (TextView) view.findViewById(R.id.look_count);
////				items.cCount = (TextView) view.findViewById(R.id.comment_count);
////				items.name = (TextView) view.findViewById(R.id.name);
////				items.ceng = (TextView) view.findViewById(R.id.ceng_name);
////				items.content = (TextView) view.findViewById(R.id.content);
////				items.time = (TextView) view.findViewById(R.id.time);
////				items.btTitle = (TextView) view.findViewById(R.id.bt_title);
////				items.user = (RoundImageButton) view
////						.findViewById(R.id.img_louzhu);
////				items.imageView = view.findViewById(R.id.imageView);
////				// items.imageRep=(TextView) view.findViewById(R.id.image_rep);
////				// items.rep=(TextView) view.findViewById(R.id.rep);
////				items.diver = view.findViewById(R.id.diver);
////				view.setTag(items);
////			} else {
////				items = (PostItem) view.getTag();
////			}
////			if (position == 0) {// 主標題
////				items.diver.setVisibility(view.VISIBLE);
////				items.title.setVisibility(view.VISIBLE);
////				items.imageView.setVisibility(view.GONE);
////				items.contentView.setVisibility(view.GONE);
////				items.allTitle.setText(map.get("title").toString());
////				items.btTitle.setText(map.get("circleTitle").toString());// 圈圈名称
////				if (map.get("circleTitle").toString().equals("衣蝠官方论坛")) {
////					items.btTitle.setPadding(40, 0, 60, 0);
////				}
////				items.lCouny.setText(map.get("skim_count").toString());
////				items.cCount.setText(map.get("rn_count").toString());
////				return view;
////			}
////			items.diver.setVisibility(view.GONE);
////			if (position == 1) {
////				items.title.setVisibility(view.GONE);
////				items.imageView.setVisibility(view.GONE);
////				items.contentView.setVisibility(view.VISIBLE);
////				if (!TextUtils.isEmpty(map.get("upic").toString())) {
////					items.user.setVisibility(view.VISIBLE);
////					items.user.setTag(map.get("upic").toString());
////					SetImageLoader.initImageLoader(null, items.user,
////							map.get("upic").toString(), "");
////					items.user.setOnClickListener(new OnClickListener() {
////
////						@Override
////						public void onClick(View arg0) {
////							Intent intent = new Intent(getApplicationContext(),
////									CircleCommonFragmentActivity.class);
////							intent.putExtra("user_id",
////									(String) map.get("user_id"));
////							intent.putExtra("flag", "circleHomePage");
////							startActivity(intent);
////						}
////					});
////				} else {
////					items.user.setVisibility(view.INVISIBLE);
////				}
////
////				items.ceng.setText("楼主");
////
////				// if(images==null){
////				// // items.rep.setVisibility(view.VISIBLE);
////				// // items.rep.setOnClickListener(new OnClickListener() {
////				// //
////				// // @Override
////				// // public void onClick(View arg0) {
////				// // ll_comment.setVisibility(View.VISIBLE);
////				// //
////				// // }
////				// // });
////				//
////				//
////				// }else{
////				// items.rep.setVisibility(view.GONE);
////				// }
////
////				items.name.setText(map.get("nickname").toString());
////				items.time.setText(DateUtil.twoDateDistance(
////						new Date((Long) map.get("send_time")),
////						new Date(System.currentTimeMillis())));
////				items.content.setText(map.get("content").toString());
////				return view;
////			}
////
////			if (images != null && position < images.length + 2) {
////				items.title.setVisibility(view.GONE);
////				items.imageView.setVisibility(view.VISIBLE);
////				items.contentView.setVisibility(view.GONE);
////				String url = images[position - 2].split(":")[0];
////				float o = Float.parseFloat(images[position - 2].split(":")[1]);
////				items.iamge.getLayoutParams().height = (int) (width / o);
////				items.iamge.setTag(url);
////				SetImageLoader.initImageLoader(null, items.iamge, url, "!560");
////
////				// if(position-1==images.length){
////				// items.imageRep.setVisibility(view.VISIBLE);
////				//
////				// items.imageRep.setOnClickListener(new OnClickListener() {
////				//
////				// @Override
////				// public void onClick(View arg0) {
////				// ll_comment.setVisibility(View.VISIBLE);
////				// }
////				// });
////				//
////				// }else{
////				// items.imageRep.setVisibility(view.GONE);
////				// }
////
////				return view;
////			}
////
////			// 剩下的都是評論內容
////
////			if (images == null) {
////				position = position - 2;
////			} else {
////				position = position - 2 - images.length;
////			}
////
////			items.title.setVisibility(view.GONE);
////			items.imageView.setVisibility(view.GONE);
////			items.contentView.setVisibility(view.VISIBLE);
////			// items.rep.setVisibility(view.GONE);
////
////			Map<String, Object> maps = newResult.get(position);
////
////			items.name.setText(maps.get("nickname").toString());
////
////			items.content.setText(maps.get("content").toString());
////
////			items.user.setTag(maps.get("pic").toString());
////			SetImageLoader.initImageLoader(null, items.user, maps.get("pic")
////					.toString(), "");
////
////			items.user.setOnClickListener(new OnClickLintener(position));
////
////			if (user_id.equals(maps.get("user_id").toString())) {
////				items.ceng.setText("楼主");
////			} else {
////				if (position == 0) {
////					items.ceng.setText("沙发");
////				} else if (position == 1) {
////					items.ceng.setText("板凳");
////				} else {
////					int lou = position + 1;
////					items.ceng.setText(lou + "楼");
////				}
////
////			}
////
////			items.time.setText(DateUtil.twoDateDistance(
////					new Date((Long) maps.get("ren_time")),
////					new Date(System.currentTimeMillis())));
////
////			return view;
////		}
////
////	}
////
////	private static class PostItem {
////		View title;
////
////		View contentView;
////
////		View imageView;
////
////		ImageView iamge;
////
////		TextView allTitle, btTitle, lCouny, cCount, name, ceng, content, time;
////
////		RoundImageButton user;
////
////		View diver;
////
////	}
////
////	private class OnClickLintener implements OnClickListener {
////
////		private int positon;
////
////		public OnClickLintener(int positon) {
////			super();
////			this.positon = positon;
////		}
////
////		@Override
////		public void onClick(View arg0) {
////			Intent intent = new Intent(getApplicationContext(),
////					CircleCommonFragmentActivity.class);
////			intent.putExtra("user_id",
////					newResult.get(this.positon).get("user_id").toString());
////			intent.putExtra("flag", "circleHomePage");
////			startActivity(intent);
////		}
////	}
////
////	private void initView() {
////
////		img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
////		img_right_icon.setVisibility(View.VISIBLE);
////		img_right_icon.setImageResource(R.drawable.postdetail_share);
////		img_right_icon.setOnClickListener(this);
////
////		img_most_right_icon = (ImageView) findViewById(R.id.img_most_right_icon);
////		img_most_right_icon.setVisibility(View.VISIBLE);
////		img_most_right_icon.setImageResource(R.drawable.mine_message_center);
////		img_most_right_icon.setOnClickListener(this);
////		/*
////		 * btn_right = (Button) findViewById(R.id.btn_right);
////		 * btn_right.setVisibility(View.VISIBLE); btn_right.setText("...");
////		 * btn_right.setOnClickListener(this);
////		 */
////
////		tv_advice = (TextView) findViewById(R.id.tv_advice); // 提示
////		tv_msg_count = (TextView) findViewById(R.id.tv_msg_count); // 回复数
////		tv_rm_count = (TextView) findViewById(R.id.tv_rm_count); // 浏览数
////		tv_post_nickname = (TextView) findViewById(R.id.tv_post_nickname); // 昵称
////		tv_post_time = (TextView) findViewById(R.id.tv_post_time); // 发帖时间
////		tv_title = (TextView) findViewById(R.id.tv_title); // 标题
////		tv_content = (TextView) findViewById(R.id.tv_content); // 内容
////
////		btn_title = (Button) findViewById(R.id.btn_title); // 显示标题
////
////		img_back = (LinearLayout) findViewById(R.id.img_back);
////		img_back.setOnClickListener(this);
////		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
////		tvTitle_base.setText("帖子详情");
////		btn_reply = (Button) findViewById(R.id.btn_reply); // 回复
////		btn_reply.setOnClickListener(this);
////		btn_comment = (Button) findViewById(R.id.btn_comment); // 发表评论
////		btn_comment.setOnClickListener(this);
////
////		img_post_user = (RoundImageButton) findViewById(R.id.img_post_user); // 用户头像
////
////		// lv_imglist = (ListView) findViewById(R.id.lv_imglist); // 图片列表
////		// lv_comment = (ListView) findViewById(R.id.lv_comment); // 评论列表
////		ll_imglist_contain = (LinearLayout) findViewById(R.id.ll_imglist_contain);
////		ll_comment_contain = (LinearLayout) findViewById(R.id.ll_comment_contain);
////		// ll_comment = (LinearLayout) findViewById(R.id.ll_comment);
////
////		et_comment = (EditText) findViewById(R.id.et_comment); // 填写评论
////		et_comment.setFocusable(false);
////		btn_more = new Button(this);
////		btn_more.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
////				LayoutParams.WRAP_CONTENT));
////		btn_more.setText("查看更多");
////		btn_more.setOnClickListener(this);
////
////	}
////
////	/** 请求帖子详情列表 */
////	private void initPostDetailData() {
////		new SAsyncTask<Void, Void, Map<String, Object>>(this) {
////
////			@Override
////			protected Map<String, Object> doInBackground(
////					FragmentActivity context, Void... params) throws Exception {
////				if (YJApplication.instance.isLoginSucess()) {
////					return ComModel2.getPostInfo(context, news_id);
////				} else {
////					return ComModel2.getPostInfo2(context, news_id);
////				}
////
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					Map<String, Object> result, Exception e) {
////				super.onPostExecute(context, result, e);
////				if (null == e) {
////					if (result != null) {
////						map = result;
////						if (TextUtils
////								.isEmpty(result.get("pic_list").toString()) == false) {
////							images = ((String) result.get("pic_list"))
////									.split(",");
////						}
////
////						adapter = new MyAdapter();
////						mListView.setAdapter(adapter);
////						/**
////						 * tv_rm_count.setText((CharSequence)result.get(
////						 * "rn_count")); tv_msg_count.setText("0");
////						 * tv_post_nickname
////						 * .setText((CharSequence)result.get("nickname"));
////						 * tv_post_time.setText(DateUtil.twoDateDistance(new
////						 * Date((Long) result.get("send_time")),new
////						 * Date(System.currentTimeMillis())));
////						 * tv_title.setText((CharSequence)result.get("title"));
////						 * tv_content
////						 * .setText((CharSequence)result.get("tv_content"));
////						 * btn_title.setText((CharSequence)result.get("title"));
////						 * String url=(String)result.get("upic");
////						 * if(TextUtils.isEmpty(url)==false){
////						 * SetImageLoader.initImageLoader(context,
////						 * img_post_user, (String)result.get("upic")); }else{
////						 * img_post_user.setVisibility(View.GONE); }
////						 * 
////						 * if(result.get("pic_list") != null){ String str[] =
////						 * ((String)result.get("pic_list")).split(","); for (int
////						 * i = 0; i < str.length; i++) { ImageView imgView = new
////						 * ImageView(context); imgView.setLayoutParams(new
////						 * LayoutParams(LayoutParams.MATCH_PARENT,600));
////						 * imgView.setScaleType(ScaleType.FIT_XY);
////						 * SetImageLoader.initImageLoader(null, imgView,
////						 * str[i]); ll_imglist_contain.addView(imgView); } }
////						 */
////
////					}
////				}
////			}
////
////		}.execute();
////	}
////
////	/** 请求帖子评论列表 */
////	private void initPostCommentListData(final String user_id2) {
////		new SAsyncTask<Void, Void, List<Map<String, Object>>>(this) {
////
////			@Override
////			protected List<Map<String, Object>> doInBackground(
////					FragmentActivity context, Void... params) throws Exception {
////				if (YJApplication.instance.isLoginSucess()) {
////					return ComModel2.getCommentList(context, news_id, currPage,
////							user_id2);
////				} else {
////					return ComModel2.getCommentList2(context, news_id,
////							currPage, user_id2);
////				}
////
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					List<Map<String, Object>> result, Exception e) {
////				super.onPostExecute(context, result, e);
////				if (null == e) {
////					newResult.addAll(result);
////					if (adapter != null) {
////						adapter.notifyDataSetChanged();
////					}
////
////					mListView.onRefreshComplete();
////
////					/**
////					 * if(newResult != null){ for (int i = 0; i <
////					 * newResult.size(); i++) { View view =
////					 * View.inflate(context,
////					 * R.layout.activity_post_detail_comment_item, null); //
////					 * TextView tv_comment_num = (TextView)
////					 * view.findViewById(R.id.tv_comment_num); TextView
////					 * tv_comment_nickname = (TextView)
////					 * view.findViewById(R.id.tv_comment_nickname); TextView
////					 * tv_comment_content = (TextView)
////					 * view.findViewById(R.id.tv_comment_content); TextView
////					 * tv_comment_position = (TextView)
////					 * view.findViewById(R.id.tv_comment_position); TextView
////					 * tv_comment_date = (TextView)
////					 * view.findViewById(R.id.tv_comment_date); RoundImageButton
////					 * img_post_user = (RoundImageButton)
////					 * view.findViewById(R.id.img_post_user);
////					 * 
////					 * // tv_comment_num.setText(i+1 + "");
////					 * tv_comment_nickname.setText
////					 * ((String)newResult.get(i).get("nickname"));
////					 * tv_comment_content
////					 * .setText((String)newResult.get(i).get("content"));
////					 * 
////					 * String uri=(String)newResult.get(i).get("pic");
////					 * 
////					 * if(TextUtils.isEmpty(uri)==false){
////					 * SetImageLoader.initImageLoader(null, img_post_user, uri);
////					 * }else{ img_post_user.setVisibility(View.GONE); }
////					 * 
////					 * if(user_id.equals((String)newResult.get(i).get("user_id")
////					 * )){ tv_comment_position.setText("楼主"); }else{
////					 * tv_comment_position.setText("板凳"); }
////					 * tv_comment_date.setText(DateUtil.twoDateDistance(new
////					 * Date((Long) newResult.get(i).get("ren_time")),new
////					 * Date(System.currentTimeMillis())));
////					 * view.setLayoutParams(new
////					 * LayoutParams(LayoutParams.MATCH_PARENT,
////					 * LayoutParams.WRAP_CONTENT));
////					 * ll_comment_contain.addView(view); }
////					 * 
////					 * if(newResult.size() == 0){
////					 * 
////					 * }else{ ll_comment_contain.addView(btn_more); }
////					 * 
////					 * }
////					 */
////				}
////			}
////
////		}.execute();
////	}
////
////	/** 添加评论 */
////	private void addPostComment(final String content) {
////		new SAsyncTask<Void, Void, ReturnInfo>(this, R.string.wait) {
////
////			@Override
////			protected ReturnInfo doInBackground(FragmentActivity context,
////					Void... params) throws Exception {
////				return ComModel2.postComment(context, news_id, content,
////						circle_id);
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					ReturnInfo returnInfo, Exception e) {
////				super.onPostExecute(context, returnInfo, e);
////				if (null == e) {
////					if (returnInfo != null
////							&& returnInfo.getStatus().equals("1")) {
////						et_comment.setText("");
////						// ll_comment.setVisibility(View.GONE);
////
////						newResult.clear();
////						// ll_comment_contain.removeAllViews();
////						initPostCommentListData("");
////						currPage = 1;
////						ToastUtil.showShortText(getApplication(),
////								returnInfo.getMessage());
////					}
////				}
////			}
////		}.execute();
////	}
////
////	/** 收藏帖子 */
////	private void CollectPost() {
////		new SAsyncTask<Void, Void, ReturnInfo>(this, R.string.wait) {
////
////			@Override
////			protected ReturnInfo doInBackground(FragmentActivity context,
////					Void... params) throws Exception {
////				return ComModel2.postCollect(context, news_id, circle_id);
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					ReturnInfo returnInfo, Exception e) {
////				super.onPostExecute(context, returnInfo, e);
////				if (null == e) {
////					if (returnInfo != null
////							&& returnInfo.getStatus().equals("1")) {
////						ToastUtil.showShortText(getApplication(),
////								returnInfo.getMessage());
////					}
////				}
////			}
////		}.execute();
////	}
////
////	@Override
////	public void onClick(View v) {
////		switch (v.getId()) {
////		case R.id.img_back: // 返回
////			finish();
////			break;
////		case R.id.img_right_icon: // 分享
////			// ToastUtil.showShortText(getApplication(), "努力为您开发分享功能中！");
////			oneKeyShare();
////			break;
////		case R.id.img_most_right_icon: // 。。。
////			FilterPopupWindow popupWindow = new FilterPopupWindow(this);
////			popupWindow.showAsDropDown(img_most_right_icon, 0, 10);
////			break;
////		case R.id.btn_comment:
////			if (!YJApplication.instance.isLoginSucess()) {
////
////				if (loginDialog == null) {
////					loginDialog = new ToLoginDialog(this);
////				}
////				loginDialog.show();
////				loginDialog.setRequestCode(3212);
////				return;
////			}
////			if (TextUtils.isEmpty(et_comment.getText().toString())) {
////				ToastUtil.showShortText(getApplication(), "回复内容不能为空");
////				return;
////			}
////			// if (StringUtils.containsEmoji(et_comment.getText().toString())) {
////			// ToastUtil.showShortText(PostDetailNewActivity.this, "不能输入特殊字符");
////			// return;
////			// }
////			String content = et_comment.getText().toString();
////			addPostComment(content);//提交内容
////
////			break;
////		case R.id.btn_reply: // 回复
////			et_comment.requestFocus();
////			getWindow().setSoftInputMode(
////					WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
////			break;
////
////		}
////
////		if (v == btn_more) {
////			currPage++;
////			ll_comment_contain.removeAllViews();
////			initPostCommentListData("");
////		}
////	}
////
////	// 一键分享
////	private void oneKeyShare() {
////		Intent intent = new Intent();
////		File[] imageFiles = ShareUtil.getImage();
////		ArrayList<Uri> images = new ArrayList<Uri>();
////		for (int i = 0; i < imageFiles.length; i++) {
////			images.add(Uri.fromFile(imageFiles[i]));
////		}
////		MyLogYiFu.e("TAG", "images.size=" + images.size() + "files.length="
////				+ imageFiles.length);
////		intent.setAction(Intent.ACTION_SEND_MULTIPLE);
////		intent.setType("image/*");
////		// intent.setType("text/plain");
////		// intent.setData(Uri.parse(YUrl.QUERY_SHOP_DETAILS));
////		// intent.putExtra(Intent.EXTRA_SUBJECT, "分享到");
////		// intent.putExtra(Intent.EXTRA_TEXT, "值得来看哦！");
////		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, images);
////		startActivity(Intent.createChooser(intent, "分享到"));
////
////	}
////
////	private boolean isLou = true;
////
////	/** 只看楼主和收藏帖子的popupwindow */
////	private class FilterPopupWindow extends PopupWindow implements
////			OnClickListener {
////		private Context mContext;
////		private LinearLayout create_louzhu_layout, create_collect_post_layout,
////				create_inform_layout;
////		private String[] names;
////
////		private TextView is_lou;
////
////		public FilterPopupWindow(Context context) {
////			super(context);
////			mContext = context;
////			init();
////		}
////
////		public FilterPopupWindow(Context context, String[] names) {
////			super(context);
////			mContext = context;
////			this.names = names;
////			init();
////		}
////
////		private void init() {
////			setWidth(LayoutParams.WRAP_CONTENT);
////			setHeight(LayoutParams.WRAP_CONTENT);
////			ColorDrawable dw = new ColorDrawable(0x00);
////			setBackgroundDrawable(dw);
////			View view = LayoutInflater.from(mContext).inflate(
////					R.layout.popup_more, null);
////			create_louzhu_layout = (LinearLayout) view
////					.findViewById(R.id.create_louzhu_layout);
////			create_collect_post_layout = (LinearLayout) view
////					.findViewById(R.id.create_collect_post_layout);
////			create_inform_layout = (LinearLayout) view
////					.findViewById(R.id.create_inform_layout);
////			is_lou = (TextView) view.findViewById(R.id.is_lou);
////			if (isLou) {
////				is_lou.setText("只看楼主");
////			} else {
////				is_lou.setText("全部");
////			}
////			create_louzhu_layout.setOnClickListener(this);
////			create_collect_post_layout.setOnClickListener(this);
////			create_inform_layout.setOnClickListener(this);
////			setContentView(view);
////			setAnimationStyle(R.style.PopupWindowAnimation);
////			setOutsideTouchable(true);
////			setFocusable(true);
////		}
////
////		@Override
////		public void onClick(View v) {
////			// Intent intent = new Intent();
////			// int enterAnimID = R.anim.slide_right_in;
////			// int exitAnimID = R.anim.slide_left_out;
////			int id = v.getId();
////			if (id == R.id.create_louzhu_layout) {
////				if (isLou) {
////					newResult.clear();
////					currPage = 1;
////					// ll_comment_contain.removeAllViews();
////					initPostCommentListData(user_id);
////					isLou = false;
////					
////				} else {
////					newResult.clear();
////					currPage = 1;
////					// ll_comment_contain.removeAllViews();
////					initPostCommentListData("");
////					isLou = true;
////				}
////
////			} else if (id == R.id.create_collect_post_layout) {
////				if (!YJApplication.instance.isLoginSucess()) {
////
////					if (loginDialog == null) {
////						loginDialog = new ToLoginDialog(
////								PostDetailNewActivity.this);
////					}
////					loginDialog.setRequestCode(3212);
////					loginDialog.show();
////
////					return;
////				}
////				CollectPost();
////
////			} else if (id == R.id.create_inform_layout) {
////				if (!YJApplication.instance.isLoginSucess()) {
////
////					if (loginDialog == null) {
////						loginDialog = new ToLoginDialog(
////								PostDetailNewActivity.this);
////					}
////					loginDialog.setRequestCode(3212);
////					loginDialog.show();
////
////					return;
////				}
////				Intent intent = new Intent(PostDetailNewActivity.this,
////						InformCircleActivity.class);
////				startActivity(intent);
////			}
////			// enterAnimID = R.anim.slide_down_in;
////			// exitAnimID = R.anim.slide_top_in;
////			dismiss();
////		}
////
////	}
////
////	@Override
////	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
////		// TODO Auto-generated method stub
////		super.onActivityResult(arg0, arg1, arg2);
////		if (arg0 == 3212) {
////			if (arg1 == -1) {
////				initPostDetailData();
////				// initPostCommentListData("");
////			}
////		}
////	}
//
//}
//>>>>>>> .r26813
