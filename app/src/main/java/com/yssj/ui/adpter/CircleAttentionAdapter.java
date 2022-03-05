//package com.yssj.ui.adpter;
//
//import java.io.Serializable;
//import java.util.Map;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.app.FragmentActivity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import com.yssj.YJApplication;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.RoundImageButton;
//import com.yssj.custom.view.ToLoginDialog;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.circles.CircleCommonFragmentActivity;
//import com.yssj.ui.adpter.CircleAllPagerAdapter.ViewHolder;
//import com.yssj.ui.base.BaseMainAdapter;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ToastUtil;
//
//public class CircleAttentionAdapter extends BaseMainAdapter {
//	ViewHolder holder;
//	ToLoginDialog loginDialog;
//	public CircleAttentionAdapter(Context context) {
//		super(context);
//	}
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		
//		if(convertView == null){
//			holder = new ViewHolder();
//			convertView = View.inflate(context, R.layout.circle_homepage_attention_list_item, null);
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
//		
//		if(map != null){
//			holder.rib_img.setTag((String) map.get("pic"));
//			SetImageLoader.initImageLoader(context, holder.rib_img, (String) map.get("pic"),"");
//			holder.tv_nickname.setText((CharSequence) map.get("title"));
//			holder.tv_content.setText((CharSequence) map.get("content"));
//			
//			holder.tv_dynamic_count.setText("动态:" + (CharSequence) map.get("n_count"));
//			holder.tv_fans_count.setText("粉丝:" + (CharSequence) map.get("u_count"));
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
//						loginDialog.setRequestCode(3214);
//						loginDialog.show();
//						return;
//					}else{
//						if("1".equals((String)map.get("isNo"))){
//							exitCircle(map, position);
//						}else{
//							addCircle(map,position);
//						}
//						}
//					
//				}
//			});
//			convertView.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Intent intent = new Intent(context, CircleCommonFragmentActivity.class);
//					intent.putExtra("item", (Serializable)map);
//					intent.putExtra("flag", "allPager");
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
//	private void exitCircle(final Map<String, Object> map,final int position){
//		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context,
//					String... params) throws Exception {
//				return ComModel2.exitCircle(context, (String)map.get("circle_id"));
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if(null == e){
//				if("1".equals(result.getStatus())){
//					ToastUtil.showShortText(context, "成功退出圈子");
//					CircleAttentionAdapter.this.result.get(position).put("isNo", "0");
//					
//					notifyDataSetChanged();
//				}
//				}
//			}
//			
//		}.execute((String)map.get("circle_id"));
//	}
//	
//	
//	private void addCircle(final Map<String, Object> map,final int position){
//		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context,
//					String... params) throws Exception {
//				return ComModel2.addCircle(context, (String)map.get("circle_id"));
//				/*if("0".equals((String)map.get("isNo"))){
//				}else{
//					return null;
//				}*/
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if(null == e){
//				if(result != null && "1".equals(result.getStatus())){
//					ToastUtil.showShortText(context, "成功加入圈子");
//					CircleAttentionAdapter.this.result.get(position).put("isNo", "1");
//					
//					notifyDataSetChanged();
//				}
//				
//				}
//				
//			}
//			
//		}.execute((String)map.get("circle_id"));
//	}
//	
//
//}
