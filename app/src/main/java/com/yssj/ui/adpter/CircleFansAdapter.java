//package com.yssj.ui.adpter;
//
//import java.io.Serializable;
//import java.util.Map;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.yssj.YJApplication;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.RoundImageButton;
//import com.yssj.custom.view.ToLoginDialog;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.circles.CircleCommonFragmentActivity;
//import com.yssj.ui.base.BaseMainAdapter;
//import com.yssj.utils.SetImageLoader;
//
//public class CircleFansAdapter extends BaseMainAdapter {
//	ViewHolder holder;
//	ToLoginDialog loginDialog;
//	public CircleFansAdapter(Context context) {
//		super(context);
//	}
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		
//		if(convertView == null){
//			holder = new ViewHolder();
//			convertView = View.inflate(context, R.layout.circle_homepage_fans_list_item, null);
//			holder.rib_img = (RoundImageButton) convertView.findViewById(R.id.rib_img);
//			holder.ibtn_attention = (ImageButton) convertView.findViewById(R.id.ibtn_attention);
//			holder.tv_nickname = (TextView) convertView.findViewById(R.id.tv_nickname);
//			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
//			holder.tv_dynamic_count = (TextView) convertView.findViewById(R.id.tv_dynamic_count);
//			holder.tv_fans_count = (TextView) convertView.findViewById(R.id.tv_fans_count);
//			
//			convertView.setTag(holder);
//		}else{
//			holder = (ViewHolder) convertView.getTag();
//		}
//		
//		final Map<String, Object> map = result.get(position);	
////		MyLogYiFu.e("fensi粉丝",result.toString());
//		if(map != null){
//			holder.rib_img.setTag((String) map.get("pic"));
//			SetImageLoader.initImageLoader(context, holder.rib_img, (String) map.get("pic"),"");
//			holder.tv_nickname.setText((CharSequence) map.get("nickname"));
//			holder.tv_content.setText((CharSequence) map.get("person_sign"));
//			
//			holder.tv_dynamic_count.setText("动态:" + (CharSequence) map.get("news_count"));
//			holder.tv_fans_count.setText("粉丝:" + (CharSequence) map.get("fans_count"));
//			
//			if("1".equals((String) map.get("isNo")) || "2".equals((String) map.get("isNo"))){
//				holder.ibtn_attention.setImageResource(R.drawable.circle_join_select);
//			}else{
//				holder.ibtn_attention.setImageResource(R.drawable.circle_join_default);
//			}
//			
//			holder.ibtn_attention.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					if(!YJApplication.instance.isLoginSucess()){
//						
//						if(loginDialog==null){
//							loginDialog=new ToLoginDialog(context);
//						}
//						loginDialog.setRequestCode(3213);
//						loginDialog.show();
//						
//						return;
//					}
//					attention(map,position);
//				}
//			});
//			convertView.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Intent intent = new Intent(context, CircleCommonFragmentActivity.class);
//					intent.putExtra("user_id", (Serializable)map.get("user_id"));
//					intent.putExtra("flag", "circleHomePage");
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
//		ImageButton ibtn_attention;
//		TextView tv_nickname,tv_content,tv_dynamic_count,tv_fans_count;
//	}
//	
//	
//	/**关注与取消关注*/
//	private void attention(final Map<String, Object> map,final int position) {
//		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){
//			@Override
//			protected ReturnInfo doInBackground(
//					FragmentActivity context, String... params)
//							throws Exception {
//				if("0".equals((String)map.get("isNo"))){
//					return ComModel2.getCircleHomePagerAttention(context, (String)map.get("user_id"));
//				}else if("1".equals((String)map.get("isNo"))){
//					return ComModel2.getCircleHomePagerUnAttention(context, (String)map.get("user_id"));
//				}else{
//					return null;
//				}
//			}
//			
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					ReturnInfo returnInfo, Exception e) {
//				super.onPostExecute(context, returnInfo, e);
//				if(null == e){
//				if(returnInfo != null && "1".equals(returnInfo.getStatus())){
//						
//						if("0".equals((String)map.get("isNo"))){
//							
//							Toast.makeText(context, "关注成功", 0).show();
//							result.get(position).put("isNo", "1");
//							
//						}else if("1".equals((String)map.get("isNo"))){
//							
//							Toast.makeText(context, "取消关注成功", 0).show();
//							result.get(position).put("isNo", "0");
//						}
//						
//						notifyDataSetChanged();
//						
//				}else{
//					Toast.makeText(context, "糟糕，出错了~~~", 0).show();
//				}
//				}
//				
//			}
//			
//		}.execute((String)map.get("user_id"));
//	}
//
//}
