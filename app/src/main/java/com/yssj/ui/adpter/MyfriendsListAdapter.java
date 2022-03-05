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

public class MyfriendsListAdapter extends BaseMainAdapter {

	private Context context;

	public MyfriendsListAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			convertView = View.inflate(context, R.layout.item_friends, null);

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
		PicassoUtils.initImage(context, mapObj.get("head_pic") + "", holder.iv_head_pic);
		String message = "";
		int num = Integer.parseInt(mapObj.get("num") + "");
		if ((mapObj.get("type") + "").equals("1")) {// 点赞--话题

			if (num > 1) {
				message = mapObj.get("nickname") + "等" + num + "人赞了你的话题";
			} else {
				message = mapObj.get("nickname") + "赞了你的话题";
			}

		} else if ((mapObj.get("type") + "").equals("2")) {// 点赞--评论

			if (num > 1) {
				message = mapObj.get("nickname") + "等" + num + "人赞了你的评论";
			} else {
				message = mapObj.get("nickname") + "赞了你的评论";
			}

		} else if ((mapObj.get("type") + "").equals("3")) {// 评论
			message = mapObj.get("nickname") + "";
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

}
