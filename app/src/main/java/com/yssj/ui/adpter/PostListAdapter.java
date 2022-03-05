//package com.yssj.ui.adpter;
//
//import java.util.Date;
//import java.util.HashMap;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.app.ArrayAdapterCompat;
//import com.yssj.utils.DateUtil;
//
//public class PostListAdapter extends
//		ArrayAdapterCompat<HashMap<String, Object>> {
//	private Context context;
//	private LayoutInflater inflater;
//
//	public PostListAdapter(Context context) {
//		super(context);
//		this.context = context;
//
//		inflater = LayoutInflater.from(context);
//
//	}
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//
//		ViewHolder holder;
//		if (null == convertView) {
//			convertView = inflater.inflate(R.layout.post_list_item, null);
//
//			holder = new ViewHolder();
//			holder.tv_zd = (TextView) convertView.findViewById(R.id.tv_zd);
//			holder.tv_jp = (TextView) convertView.findViewById(R.id.tv_jp);
//			holder.tv_tp = (TextView) convertView.findViewById(R.id.tv_tp);
//			holder.tv_title = (TextView) convertView
//					.findViewById(R.id.tv_title);
//			holder.tv_user_name = (TextView) convertView
//					.findViewById(R.id.tv_user_name);
//			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
//			holder.tv_comment_sum = (TextView) convertView
//					.findViewById(R.id.tv_comment_sum);
//
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//
//		HashMap<String, Object> mapObj = getItem(position);
//		holder.tv_title.setText((CharSequence) mapObj.get("title"));
//		if (mapObj.get("top").equals("0")) {
//			holder.tv_zd.setVisibility(View.GONE);
//		} else {
//			holder.tv_zd.setVisibility(View.VISIBLE);
//		}
//		if (mapObj.get("fine").equals("0")) {
//			holder.tv_jp.setVisibility(View.GONE);
//		} else {
//			holder.tv_jp.setVisibility(View.VISIBLE);
//		}
//		holder.tv_user_name.setText((CharSequence) mapObj.get("nickname"));
//		holder.tv_time.setText(DateUtil.twoDateDistance(
//				new Date((Long) mapObj.get("send_time")),
//				new Date(System.currentTimeMillis())));
//		holder.tv_comment_sum.setText((CharSequence) mapObj.get("r_count"));
//		return convertView;
//	}
//
//	class ViewHolder {
//
//		TextView tv_zd, tv_jp, tv_tp, tv_title, tv_user_name, tv_time,
//				tv_comment_sum;
//	}
//
//}
