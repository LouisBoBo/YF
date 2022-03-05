package com.yssj.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.yssj.YJApplication;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.base.BasePager;
//import com.yssj.ui.pager.CenterMessageHuaTi;
import com.yssj.ui.pager.CenterMessageHuaTiNew;
import com.yssj.ui.pager.MessageLiaotianPage;
import com.yssj.ui.pager.MyMessageSystemPager;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.YCache;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class MessageCenterDetailListFragment extends BaseFragment implements OnClickListener {
	private ViewPager content_pager;
	private LinearLayout ll, ll_v3;
	private List<BasePager> pageLists; // 总余额--------可提现-----冻结额度
	private TextView textView1, textView2, textView3, tv_view;
	private int currIndex;// 当前页卡编号

	private TextView tvTitle_base;
	private LinearLayout img_back;
	// private RelativeLayout rl_edu;
	// private Button bt_tixian;

	private View v1, v2, v3;
	private View redPoint1, redPoint2, redPoint3;
	private MyTimerTask mTask;
	Timer timer = new Timer();

	@SuppressLint("ValidFragment")
	public MessageCenterDetailListFragment(int currIndex) {
		this.currIndex = currIndex;

	}

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.messagecentliebao, null);
		// 红点
		redPoint1 = view.findViewById(R.id.view_point1);
		redPoint2 = view.findViewById(R.id.view_point2);
		redPoint3 = view.findViewById(R.id.view_point3);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);

		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		ll_v3 = (LinearLayout) view.findViewById(R.id.ll_v3);

		ll = (LinearLayout) view.findViewById(R.id.ll);
		content_pager = (ViewPager) view.findViewById(R.id.content_pager);
		textView1 = (TextView) view.findViewById(R.id.textView1);
		textView2 = (TextView) view.findViewById(R.id.textView2);
		textView3 = (TextView) view.findViewById(R.id.textView3);
		tv_view = (TextView) view.findViewById(R.id.tv_view);

		// 红线
		v1 = (View) view.findViewById(R.id.v1); // 左边
		v2 = (View) view.findViewById(R.id.v2); // 中间边
		v3 = (View) view.findViewById(R.id.v3); // 右边

		textView3.setVisibility(View.VISIBLE);
		tv_view.setVisibility(View.VISIBLE);
		textView3.setText("系统");
		tvTitle_base.setText("消息中心");
		textView1.setText("聊天");
		textView2.setText("话题");
		ll_v3.setVisibility(View.VISIBLE);

		return view;
	}

	@Override
	public void initData() {
		initViewPager(currIndex);
		initTextView();

	}

	@Override
	public void onResume() {
		super.onResume();
		if (mTask != null) {
			mTask.cancel();
			mTask = new MyTimerTask();
		} else {
			mTask = new MyTimerTask();
		}
		updateUnreadLabel();
//		EMChatManager.getInstance().registerEventListener(new EMEventListener() {
//
//			@Override
//			public void onEvent(EMNotifierEvent event) {
//
//				switch (event.getEvent()) {
//				case EventNewMessage: // 接收新消息
//				{
//					event.getData();
//					// 提示新消息
//					updateUnreadLabel();
//					break;
//				}
//				case EventDeliveryAck: {// 接收已发送回执
//					// 提示新消息
//
//					updateUnreadLabel();
//					break;
//				}
//
//				case EventNewCMDMessage: {// 接收透传消息
//
//					updateUnreadLabel();
//					break;
//				}
//
//				case EventReadAck: {// 接收已读回执
//
//					updateUnreadLabel();
//					break;
//				}
//
//				case EventOfflineMessage: {// 接收离线消息
//					event.getData();
//
//					updateUnreadLabel();
//					break;
//				}
//
//				case EventConversationListChanged: {// 通知会话列表通知event注册（在某些特殊情况，SDK去删除会话的时候会收到回调监听）
//
//					updateUnreadLabel();
//					break;
//				}
//
//				default:
//					break;
//				}
//			}
//
//		});
		timer.schedule(mTask, 0, 1000); // 1s后执行task,经过1s再次执行

	}

	/** 初始化ViewPager */
	private void initViewPager(int index) {
		pageLists = new ArrayList<BasePager>();
		pageLists.add(new MessageLiaotianPage(getActivity())); // 聊天
//		pageLists.add(new CenterMessageHuaTi(getActivity()));// 话题
		pageLists.add(new CenterMessageHuaTiNew(getActivity()));// 话题
		pageLists.add(new MyMessageSystemPager(getActivity()));// 系统
		pageLists.get(index).initData(); // 第一次进来时加载数据
		content_pager.setOffscreenPageLimit(1); // 设置预加载页面
		content_pager.setAdapter(new MyPagerAdapter(pageLists));
		content_pager.setCurrentItem(index);
		content_pager.setOnPageChangeListener(new MyOnPageChangeListener());

	}

	private void initTextView() {

		// 红色红线的显示
		if (currIndex == 0) {
			textView1.setTextColor(getResources().getColor(R.color.pink_color));
			v1.setVisibility(View.VISIBLE);
			v2.setVisibility(View.INVISIBLE);
			v3.setVisibility(View.INVISIBLE);

		} else if (currIndex == 1) {
			textView2.setTextColor(getResources().getColor(R.color.pink_color));
			v1.setVisibility(View.INVISIBLE);
			v2.setVisibility(View.VISIBLE);
			v3.setVisibility(View.INVISIBLE);
		} else {
			textView3.setTextColor(getResources().getColor(R.color.pink_color));
			v1.setVisibility(View.INVISIBLE);
			v2.setVisibility(View.INVISIBLE);
			v3.setVisibility(View.VISIBLE);

		}
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
		textView3.setOnClickListener(new MyOnClickListener(2));
	}

	/* 页卡切换监听 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			pageLists.get(arg0).initData();
			setTextTitleSelectedColor(arg0);

			if (arg0 == 0) {
				v1.setVisibility(View.VISIBLE);
				v2.setVisibility(View.INVISIBLE);
				v3.setVisibility(View.INVISIBLE);
			}

			if (arg0 == 1) {
				v1.setVisibility(View.INVISIBLE);
				v2.setVisibility(View.VISIBLE);
				v3.setVisibility(View.INVISIBLE);
			}
			if (arg0 == 2) {
				v1.setVisibility(View.INVISIBLE);
				v2.setVisibility(View.INVISIBLE);
				v3.setVisibility(View.VISIBLE);
			}

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	/** 设置标题文本的颜色 **/
	private void setTextTitleSelectedColor(int arg0) {
		for (int i = 0; i < 3; i++) {
			// TextView tv = (TextView) ll.getChildAt(i);

			if (arg0 == 0) {
				textView1.setTextColor(getResources().getColor(R.color.pink_color));
				textView2.setTextColor(getResources().getColor(R.color.black));
				textView3.setTextColor(getResources().getColor(R.color.black));
			} else if (arg0 == 1) {
				textView1.setTextColor(getResources().getColor(R.color.black));
				textView2.setTextColor(getResources().getColor(R.color.pink_color));
				textView3.setTextColor(getResources().getColor(R.color.black));
			} else {
				textView1.setTextColor(getResources().getColor(R.color.black));
				textView2.setTextColor(getResources().getColor(R.color.black));
				textView3.setTextColor(getResources().getColor(R.color.pink_color));
			}

		}

	}

	/* 标题点击监听 */
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			content_pager.setCurrentItem(index);
			if (index == 1) {// 话题
				if (YJApplication.instance.isLoginSucess()) {
					SharedPreferencesUtil.saveStringData(context,
							Pref.TOPIC_MESSAGE + YCache.getCacheUser(context).getUser_id(), "0");
				}
			}
//			else if(index==2){//系统
//				if (YJApplication.instance.isLoginSucess()) {
//					SharedPreferencesUtil.saveStringData(context,
//							Pref.SYSTEM_MESSAGE + YCache.getCacheUser(context).getUser_id(), "0");
//				}
//			}
		}
	}

	class MyPagerAdapter extends PagerAdapter {
		private List<BasePager> pageLists;

		public MyPagerAdapter(List<BasePager> pageLists) {
			this.pageLists = pageLists;
		}

		@Override
		public int getCount() {
			return pageLists.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(pageLists.get(position).getRootView());
			return pageLists.get(position).getRootView();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();
			getActivity().overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);

			break;

		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				if (YJApplication.instance.isLoginSucess()) {
					String str2 = SharedPreferencesUtil.getStringData(context,
							Pref.TOPIC_MESSAGE + YCache.getCacheUser(context).getUser_id(), "0");
					if (!"0".equals(str2) && 1 != content_pager.getCurrentItem()) {// 话题无新消息
						redPoint2.setVisibility(View.VISIBLE);
					} else {
						redPoint2.setVisibility(View.GONE);
					}
					
//					String str3 = SharedPreferencesUtil.getStringData(context,
//							Pref.SYSTEM_MESSAGE + YCache.getCacheUser(context).getUser_id(), "0");
//					if (!"0".equals(str3) && 2 != content_pager.getCurrentItem()) {// 系统无新消息
//						redPoint3.setVisibility(View.VISIBLE);
//					} else {
//						redPoint3.setVisibility(View.GONE);
//					}
				}
			}
			super.handleMessage(msg);
		};
	};

	public class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			// 需要做的事:发送消息
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};

	/**
	 * 刷新未读消息数
	 */
	private void updateUnreadLabel() {

		((Activity) context).runOnUiThread(new Runnable() {
			public void run() {

				// 刷新bottom bar消息未读数
//				int count = getUnreadMsgCountTotal();
//				if (count > 0) {
//					redPoint1.setVisibility(View.VISIBLE);
//				} else {
					redPoint1.setVisibility(View.GONE);
				}

//			}
		});

	}

	/**
	 * 获取未读消息数
	 * 
	 * @return
	 */
//	public int getUnreadMsgCountTotal() {
//		int unreadMsgCountTotal = 0;
//		int chatroomUnreadMsgCount = 0;
//		unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
//		/*
//		 * for(EMConversation
//		 * conversation:EMChatManager.getInstance().getAllConversations
//		 * ().values()){ if(conversation.getType() ==
//		 * EMConversationType.ChatRoom)
//		 * chatroomUnreadMsgCount=chatroomUnreadMsgCount
//		 * +conversation.getUnreadMsgCount(); }
//		 */
//		return unreadMsgCountTotal - chatroomUnreadMsgCount;
//	}
}
