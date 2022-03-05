//package com.yssj.ui.adpter;
//
//import java.io.Serializable;
//import java.util.Map;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.custom.view.RoundImageButton;
//import com.yssj.ui.activity.circles.CircleCommonFragmentActivity;
//import com.yssj.ui.base.BaseMainAdapter;
//import com.yssj.utils.SetImageLoader;
//
//public class CircleMainPagerAdapter extends BaseMainAdapter {
//
//	public CircleMainPagerAdapter(Context context) {
//		super(context);
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		ViewHolder holder;
//		if(convertView == null){
//			holder = new ViewHolder();
//			convertView = View.inflate(context, R.layout.circle_minepage_list_item, null);
//			holder.rib_img = (RoundImageButton) convertView.findViewById(R.id.rib_img);
//			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
//			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
//			holder.tv_msg_count = (TextView) convertView.findViewById(R.id.tv_msg_count);
//			holder.tv_access_count = (TextView) convertView.findViewById(R.id.tv_access_count);
//			
//			convertView.setTag(holder);
//		}else{
//			holder = (ViewHolder) convertView.getTag();
//		}
//		
//		final Map<String, Object> map  = result.get(position);
//		if(map != null){
//			if(map.get("pic") == null){
//				holder.rib_img.setImageResource(R.drawable.circle_default_img);
//			}else{
//				holder.rib_img.setTag(map.get("pic"));
//				SetImageLoader.initImageLoader(context, holder.rib_img, (String) map.get("pic"),"!180");
//			}
//			holder.tv_title.setText((CharSequence) map.get("title"));
//			holder.tv_content.setText((CharSequence) map.get("content"));
//			holder.tv_msg_count.setText((CharSequence) map.get("n_count"));
//			holder.tv_access_count.setText((CharSequence) map.get("u_count"));
//			
//			convertView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					Intent intent = new Intent(context, CircleCommonFragmentActivity.class);
//					intent.putExtra("item", (Serializable)map);
//					intent.putExtra("flag", "minePager");
//					context.startActivity(intent);
//				}
//			});
//			
//		}
//		
//		return convertView;
//	}
//	
//	class ViewHolder{
//		RoundImageButton rib_img;
//		TextView tv_title,tv_content,tv_msg_count,tv_access_count;
//	}
//
//}
