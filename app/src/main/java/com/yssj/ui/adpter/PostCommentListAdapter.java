//package com.yssj.ui.adpter;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.utils.DateUtil;
//import com.yssj.utils.SetImageLoader;
//
//public class PostCommentListAdapter extends BaseAdapter {
//	private Context context;
//	private List<Map<String,Object>> result;
//
//	public PostCommentListAdapter(Context context,List<Map<String,Object>> result) {
//		this.context = context;
//		this.result = result;
//	}
//	
//	@Override
//	public int getCount() {
//		return result.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return result.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		ViewHolder holder;
//		if(convertView == null){
//			holder = new ViewHolder();
//			convertView = View.inflate(context, R.layout.activity_post_detail_comment_item, null);
//			holder.tv_comment_content = (TextView) convertView.findViewById(R.id.tv_comment_content);
//			holder.tv_comment_date = (TextView) convertView.findViewById(R.id.tv_comment_date);
//			holder.tv_comment_nickname = (TextView) convertView.findViewById(R.id.tv_comment_nickname);
//			convertView.setTag(holder);
//		}else{
//			holder = (ViewHolder) convertView.getTag();
//		}
//		Map<String, Object> map = result.get(position);
//		holder.tv_comment_nickname.setText((String)map.get("nickname"));
//		holder.tv_comment_content.setText((String)map.get("content"));
//		holder.tv_comment_date.setText(DateUtil.twoDateDistance(new Date((Long) map.get("ren_time")),new Date(System.currentTimeMillis())));
//		
//		return convertView;
//	}
//	
//	class ViewHolder{
//		TextView tv_comment_content,tv_comment_date,tv_comment_nickname;
//	}
//}
