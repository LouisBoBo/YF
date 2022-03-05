//package com.yssj.ui.adpter;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import android.content.Context;
//import android.content.Intent;
//import android.text.TextUtils;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.custom.view.RoundImageButton;
//import com.yssj.ui.adpter.PostListAdapter.ViewHolder;
//import com.yssj.ui.base.BaseMainAdapter;
//import com.yssj.utils.DateUtil;
//import com.yssj.utils.SetImageLoader;
//
//public class AllPostListPagerAdapter extends BaseMainAdapter {
//	private String circle_id;
//	public AllPostListPagerAdapter(Context context,String circle_id) {
//		super(context);
//		this.circle_id = circle_id;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		ViewHolder holder;
//		if (null == convertView) {
//			convertView = View.inflate(context,R.layout.post_list_item, null);
//
//			holder = new ViewHolder();
//			holder.tv_zd = (TextView) convertView.findViewById(R.id.tv_zd);
//			holder.tv_jp = (TextView) convertView.findViewById(R.id.tv_jp);
//			holder.tv_tp = (TextView) convertView.findViewById(R.id.tv_tp);
//			holder.tv_hot = (TextView) convertView.findViewById(R.id.tv_hot);
//			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
//			holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
//			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
//			holder.tv_comment_sum = (TextView) convertView.findViewById(R.id.tv_comment_sum);
//
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//
//		final HashMap<String, Object> mapObj = result.get(position);
//		holder.tv_title.setText((CharSequence) mapObj.get("title"));
//		
//		if (mapObj.get("top").equals("0")) {
//			holder.tv_zd.setVisibility(View.GONE);
//		} else {
//			holder.tv_zd.setVisibility(View.VISIBLE);
//		}
//		
//		if (mapObj.get("fine").equals("0")) {
//			holder.tv_jp.setVisibility(View.GONE);
//		} else {
//			holder.tv_jp.setVisibility(View.VISIBLE);
//		}
//		
//		if (mapObj.get("hot").equals("0")) {
//			holder.tv_hot.setVisibility(View.GONE);
//		} else {
//			holder.tv_hot.setVisibility(View.VISIBLE);
//		}
//		
//		if (TextUtils.isEmpty((CharSequence)mapObj.get("pic_list"))) {
//			holder.tv_tp.setVisibility(View.GONE);
//		} else {
//			holder.tv_tp.setVisibility(View.VISIBLE);
//		}
//		
//		if(TextUtils.isEmpty((CharSequence)mapObj.get("nickname")) ){
//			holder.tv_user_name.setVisibility(View.VISIBLE);
//			holder.tv_user_name.setText("匿名发表");
//		}else{
//			holder.tv_user_name.setVisibility(View.VISIBLE);
//			holder.tv_user_name.setText((CharSequence) mapObj.get("nickname"));
//		}
//		
//		if(TextUtils.isEmpty(mapObj.get("send_time").toString()) ){
//			holder.tv_time.setVisibility(View.INVISIBLE);
//		}else{
//			holder.tv_time.setVisibility(View.VISIBLE);
//			holder.tv_time.setText(DateUtil.twoDateDistance(new Date((Long) mapObj.get("send_time")),new Date(System.currentTimeMillis())));
//		}
//		if(TextUtils.isEmpty((CharSequence)mapObj.get("r_count")) ){
//			holder.tv_comment_sum.setVisibility(View.VISIBLE);
//			holder.tv_comment_sum.setText("0");
//		}else{
//			holder.tv_comment_sum.setVisibility(View.VISIBLE);
//			holder.tv_comment_sum.setText((CharSequence) mapObj.get("r_count"));
//		}
//		
//		convertView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(context, PostDetailNewActivity.class);
//				intent.putExtra("news_id", (CharSequence) mapObj.get("news_id"));
//				intent.putExtra("user_id", (CharSequence) mapObj.get("user_id"));
//				intent.putExtra("circle_id", circle_id);
//				context.startActivity(intent);
//			}
//		});
//		if(position == result.size() -1)
//			convertView.setPadding(0, 0, 0, 20);
//		return convertView;
//	}
//
//	class ViewHolder {
//
//		TextView tv_zd, tv_jp, tv_tp,tv_hot, tv_title, tv_user_name, tv_time,
//				tv_comment_sum;
//	}
//}
