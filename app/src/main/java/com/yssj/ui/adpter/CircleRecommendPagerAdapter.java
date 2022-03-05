//package com.yssj.ui.adpter;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.HashMap;
//
//import android.content.Context;
//import android.content.Intent;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.ui.activity.circles.CircleCommonFragmentActivity;
//import com.yssj.ui.activity.circles.PostDetailNewActivity;
//import com.yssj.ui.base.BaseMainAdapter;
//import com.yssj.utils.DateUtil;
//import com.yssj.utils.SetImageLoader;
//
//public class CircleRecommendPagerAdapter extends BaseMainAdapter {
//
//	public CircleRecommendPagerAdapter(Context context) {
//		super(context);
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//
//		ViewHolder holder;
//		if (null == convertView) {
//			holder = new ViewHolder();
//			convertView = View.inflate(context,R.layout.recom_circle_item, null);
//
//			holder.img_circle = (ImageView) convertView.findViewById(R.id.img_circle);
////			holder.img_content_pic = (ImageView) convertView.findViewById(R.id.img_content_pic);
//			holder.tv_circle_name = (TextView) convertView.findViewById(R.id.tv_circle_name);
//			holder.tv_comment_count = (TextView) convertView.findViewById(R.id.tv_comment_count);
//			holder.tv_content_content = (TextView) convertView.findViewById(R.id.tv_content_content);
//			holder.tv_content_title = (TextView) convertView.findViewById(R.id.tv_content_title);
//			holder.tv_pub_time = (TextView) convertView.findViewById(R.id.tv_pub_time);
//			holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
//			
//			holder.ll_contain = (LinearLayout) convertView.findViewById(R.id.ll_contain);
//			
//			holder.linContent=convertView.findViewById(R.id.lin_content);
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//		
//		final HashMap<String, Object> mapObj = result.get(position);
//		if(mapObj.get("pic") == null){
//			holder.img_circle.setImageResource(R.drawable.circle_default_img);
//		}else{
//			holder.img_circle.setTag(mapObj.get("pic"));
//			SetImageLoader.initImageLoader(context, holder.img_circle, (String) mapObj.get("pic"),"");
//		}
//		
//		holder.ll_contain.removeAllViews();
//		
//		if(TextUtils.isEmpty(mapObj.get("pic_list").toString())==false){
//			String[] pics = ((String) mapObj.get("pic_list")).split(",");
//			for (int i = 0; i < pics.length; i++) {
//				View view = View.inflate(context, R.layout.circle_recom_list_img_item, null);
//				ImageView imageView = (ImageView) view.findViewById(R.id.img_content_pic);
//				imageView.setTag(pics[i].split(":")[0]);
//				SetImageLoader.initImageLoader(context, imageView,pics[i].split(":")[0],"!560");
//				holder.ll_contain.addView(view);
//				break;
//			}
//		}
//		
//		holder.tv_circle_name.setText((CharSequence) mapObj.get("ctitle"));
//		holder.tv_comment_count.setText((CharSequence) mapObj.get("r_count"));
//		
//		holder.tv_content_content.setText(toDBC(mapObj.get("content").toString()));
//		holder.tv_content_title.setText((CharSequence) mapObj.get("ftitle"));
//		holder.tv_pub_time.setText(DateUtil.twoDateDistance(new Date((Long) mapObj.get("send_time")), new Date(System.currentTimeMillis())));
//		holder.tv_user_name.setText((CharSequence) mapObj.get("nickname"));
//		
//		
//		holder.tv_circle_name.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(context, CircleCommonFragmentActivity.class);
//				intent.putExtra("item", (Serializable)mapObj);
//				intent.putExtra("flag", "recommendPager");
//				context.startActivity(intent);
//			}
//		});
//		holder.img_circle.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(context, CircleCommonFragmentActivity.class);
//				intent.putExtra("item", (Serializable)mapObj);
//				intent.putExtra("flag", "recommendPager");
//				context.startActivity(intent);
//			}
//		});
//		
//		holder.linContent.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(context, PostDetailNewActivity.class);
//				intent.putExtra("news_id", mapObj.get("news_id").toString());
//				intent.putExtra("user_id", mapObj.get("user_id").toString());
//				intent.putExtra("circle_id", mapObj.get("circle_id").toString());
//				context.startActivity(intent);
//			}
//		});
//		
//		return convertView;
//	}
//
//	class ViewHolder {
//		LinearLayout ll_contain;
//		ImageView img_circle;
////		ImageView img_content_pic;
//		TextView tv_circle_name, tv_pub_time, tv_content_title,
//				tv_content_content, tv_user_name, tv_comment_count;
//		View linContent;
//		
//	}
//	/**
//	  * 针对TextView显示中文中出现的排版错乱问题，通过调用此方法得以解决
//	  * @param str
//	  * @return 返回全部为全角字符的字符串
//	  */
//	public static String toDBC(String str) {
//	    char[] c = str.toCharArray();
//	    for (int i = 0; i < c.length; i++) {
//	        if (c[i] == 12288) {
//	            c[i] = (char) 32;
//	            continue;
//	        }
//	        if (c[i] > 65280 && c[i] < 65375) {
//	            c[i] = (char) (c[i] - 65248);
//	        }
//	            
//	    }
//	    return new String(c);
//	}
//
//}
