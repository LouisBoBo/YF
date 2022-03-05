//package com.yssj.ui.adpter;
//
//import java.util.Date;
//import java.util.HashMap;
//
//import android.content.Context;
//import android.support.v4.app.FragmentActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.app.ArrayAdapterCompat;
//import com.yssj.utils.DateUtil;
//import com.yssj.utils.PicassoUtils;
//import com.yssj.utils.SetImageLoader;
//
//public class RecomCircleAdapter extends
//		ArrayAdapterCompat<HashMap<String, Object>> {
//	private Context context;
//	private LayoutInflater inflater;
//	private FragmentActivity activity;
//
//	public RecomCircleAdapter(Context context) {
//		super(context);
//		this.context = context;
//		this.activity = (FragmentActivity) context;
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
//			convertView = inflater.inflate(R.layout.recom_circle_item, null);
//
//			holder = new ViewHolder();
//			holder.img_circle = (ImageView) convertView.findViewById(R.id.img_circle);
//			holder.img_content_pic = (ImageView) convertView.findViewById(R.id.img_content_pic);
//			holder.tv_circle_name = (TextView) convertView.findViewById(R.id.tv_circle_name);
//			holder.tv_comment_count = (TextView) convertView.findViewById(R.id.tv_comment_count);
//			holder.tv_content_content = (TextView) convertView.findViewById(R.id.tv_content_content);
//			holder.tv_content_title = (TextView) convertView.findViewById(R.id.tv_content_title);
//			holder.tv_pub_time = (TextView) convertView.findViewById(R.id.tv_pub_time);
//			holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
//
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//
//		HashMap<String, Object> mapObj = getItem(position);
////		SetImageLoader.initImageLoader(context, holder.img_circle,
////				(String) mapObj.get("pic"),"");
//		
//		
//		PicassoUtils.initImage(context, (String) mapObj.get("pic"), holder.img_circle);
//		
////		SetImageLoader.initImageLoader(context, holder.img_content_pic,
////				((String) mapObj.get("pic_list")).split(",")[0],"!280");
////		
//		
//		
//		PicassoUtils.initImage(context, ((String) mapObj.get("pic_list")).split(",")[0]+"!280", holder.img_content_pic);
//		
//		holder.tv_circle_name.setText((CharSequence) mapObj.get("ctitle"));
//		holder.tv_comment_count.setText((CharSequence) mapObj.get("r_count"));
//		holder.tv_content_content.setText((CharSequence) mapObj.get("content"));
//		holder.tv_content_title.setText((CharSequence) mapObj.get("ftitle"));
//		holder.tv_pub_time.setText(DateUtil.twoDateDistance(new Date((Long) mapObj.get("send_time")), new Date(System.currentTimeMillis())));
//		holder.tv_user_name.setText((CharSequence) mapObj.get("nickname"));
//		return convertView;
//	}
//
//	class ViewHolder {
//		ImageView img_circle, img_content_pic;
//		TextView tv_circle_name, tv_pub_time, tv_content_title,
//				tv_content_content, tv_user_name, tv_comment_count;
//	}
//
//}
