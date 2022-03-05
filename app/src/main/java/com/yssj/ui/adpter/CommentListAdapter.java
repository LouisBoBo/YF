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
//public class CommentListAdapter extends
//		ArrayAdapterCompat<HashMap<String, Object>> {
//	private Context context;
//	private LayoutInflater inflater;
//
//	public CommentListAdapter(Context context) {
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
//			convertView = inflater.inflate(R.layout.comment_list_item, null);
//
//			holder = new ViewHolder();
//			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
//			holder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
//			holder.tv_post_time = (TextView) convertView.findViewById(R.id.tv_post_time);
//
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//
//		HashMap<String, Object> mapObj = getItem(position);
//		
//		holder.tv_post_time.setText(DateUtil.twoDateDistance(
//				new Date((Long) mapObj.get("ren_time")),
//				new Date(System.currentTimeMillis())));
//		holder.tv_username.setText((CharSequence) mapObj.get("nickname"));
//		holder.tv_content.setText((CharSequence) mapObj.get("content"));
//		return convertView;
//	}
//
//	class ViewHolder {
//
//		TextView tv_content, tv_username, tv_post_time;
//	}
//
//}
