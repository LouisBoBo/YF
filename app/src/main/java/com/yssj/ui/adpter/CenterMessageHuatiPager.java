package com.yssj.ui.adpter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.time.DateFormatUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.ui.activity.ShowMyPicActivity;
import com.yssj.ui.activity.circles.SweetFriendsDetails;
import com.yssj.ui.base.BaseMainAdapter;
import com.yssj.ui.fragment.IntergralDetailListFragment;
import com.yssj.ui.pager.IntergralIncomePage;
import com.yssj.utils.DateUtil;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;

public class CenterMessageHuatiPager extends BaseMainAdapter {

	private Context context;

	public CenterMessageHuatiPager(Context context) {
		super(context);
		this.context = context;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			convertView = View.inflate(context, R.layout.item_centermessage_huati, null);

			holder = new ViewHolder();
			holder.iv_head_pic = (RoundImageButton) convertView.findViewById(R.id.iv_head_pic);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final HashMap<String, Object> mapObj = result.get(position);
		// "user_id": 1,
		// "nickname": “1”,
		// "head_pic":“1”,
		// "theme_id": 1,
		// "type": 1,
		// "content":“1”,
		// "num": 1,
		// "date": 1,
		// "id": 1

		// PicassoUtils.initImage(context, mapObj.get("head_pic") + "",
		// holder.iv_head_pic);

		// SetImageLoader.initImageLoader3(context, holder.iv_head_pic,
		// mapObj.get("head_pic") + "", "");

		PicassoUtils.initImage(context, mapObj.get("head_pic") + "", holder.iv_head_pic);
		String message = "";
		// 消息类型 1为点赞帖子，2为点赞评论，3为评论，
		int num = Integer.parseInt(mapObj.get("num") + "");
		if ((mapObj.get("type") + "").equals("1")) {// 点赞--话题

			message = mapObj.get("nickname") + "";
			if (message.length() > 8) {
				message = message.substring(0, 8)+"...";
			}

			if (num > 1) {
				message = message + "等" + num + "人赞了你的话题";
			} else {
				message = message + "赞了你的话题";
			}

		} else if ((mapObj.get("type") + "").equals("2")) {// 点赞--评论

			message = mapObj.get("nickname") + "";
			if (message.length() > 8) {
				message = message.substring(0, 8)+"...";
			}

			if (num > 1) {
				message = message + "等" + num + "人赞了你的评论";
			} else {
				message = message + "赞了你的评论";
			}

		} else if ((mapObj.get("type") + "").equals("3")) {// 评论
			
			message = mapObj.get("nickname") + "";
			if (message.length() > 8) {
				message = message.substring(0, 8)+"...";
			}
		}
		holder.tv_name.setText(message);

		holder.tv_content.setText(mapObj.get("content") + "");
		long time = Long.parseLong("" + mapObj.get("date"));
		long now = System.currentTimeMillis();
		if ((now - time) > 24 * 60 * 60 * 1000) {
			// 24小时之前
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String riqi = sdf.format(time);
			holder.tv_time.setText(riqi.split("-")[0] + "年" + riqi.split("-")[1] + "月" + riqi.split("-")[2] + "日");
		} else {
			// 当天
			holder.tv_time.setText(DateUtil.formatTimeDangTian(time));
		}
		convertView.setOnClickListener(new MyAdapterListener(position, mapObj));

		return convertView;
	}

	class ViewHolder {

		public TextView tv_content;
		public TextView tv_time;
		public TextView tv_name;
		public RoundImageButton iv_head_pic;

	}

	class MyAdapterListener implements OnClickListener {
		private int position;
		HashMap<String, Object> mapObj;

		public MyAdapterListener(int pos, HashMap<String, Object> mapObj) {
			position = pos;
			this.mapObj = mapObj;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, SweetFriendsDetails.class);
			intent.putExtra("theme_id", mapObj.get("theme_id") + "");
			((FragmentActivity) context).startActivity(intent);
			((FragmentActivity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

		}
	}

	// private String formatTime(long time) {
	// String reTime = "";
	//
	// // 当天
	// Integer ss = 1000;
	// Integer mi = ss * 60;
	// Integer hh = mi * 60;
	// Integer dd = hh * 24;
	//
	// Long day = time / dd;
	// Long hour = (time - day * dd) / hh;
	// Long minute = (time - day * dd - hour * hh) / mi;
	//
	// // 上午
	// if (hour < 12) {
	//
	// // 小于10分
	// if (minute < 10) {
	// reTime = "上午" + hour + ":0" + minute;
	// } else {
	// // 大于10分
	// reTime = "上午" + hour + ":" + minute;
	// }
	//
	// } else {// 下午
	// long hourPM = hour - 12;
	//
	// // 小于10分
	// if (minute < 10) {
	// reTime = "下午" + hourPM + ":0" + minute;
	// } else {
	// // 大于10分
	// reTime = "下午" + hourPM + ":" + minute;
	// }
	//
	// }
	//
	// return reTime;
	// }

}
