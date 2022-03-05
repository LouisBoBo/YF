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
//import com.yssj.ui.base.BaseMainAdapter;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ToastUtil;
//
//public class CircleAllPagerAdapter extends BaseMainAdapter {
//
//	public CircleAllPagerAdapter(Context context) {
//		super(context);
//	}
//	private ToLoginDialog loginDialog;
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		final ViewHolder holder;
//		if(convertView == null){
//			holder = new ViewHolder();
//			convertView = View.inflate(context, R.layout.circle_allpage_list_item, null);
//			holder.rib_img = (RoundImageButton) convertView.findViewById(R.id.rib_img);
//			holder.ibtn_join = (ImageButton) convertView.findViewById(R.id.ibtn_join);
//			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
//			holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
//			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
//			
//			convertView.setTag(holder);
//		}else{
//			holder = (ViewHolder) convertView.getTag();
//		}
//		
//		final Map<String, Object> map  = result.get(position);
//		
//		SetImageLoader.initImageLoader(context, holder.rib_img, (String) map.get("pic"),"!180");
//		holder.tv_title.setText((CharSequence) map.get("title"));
//		holder.tv_price.setText("今日:" + (CharSequence) map.get("day_count"));
//		holder.tv_content.setText((CharSequence) map.get("content"));
//		
//		
//		if(map.get("isNo") != null && "1".equals((String)map.get("isNo"))){
//			holder.ibtn_join.setBackgroundResource(R.drawable.circle_join_select);
//		}else{
//			holder.ibtn_join.setBackgroundResource(R.drawable.circle_join_default);
//		}
//		// 加入圈子
//		holder.ibtn_join.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(!YJApplication.instance.isLoginSucess()){
//					
//					if(loginDialog==null){
//						loginDialog=new ToLoginDialog(context);
//					}
//					loginDialog.show();
//					return;
//				}else{
//				if("1".equals((String)map.get("isNo"))){
//					exitCircle(map, holder, position);
//				}else{
//					addCircle(map,holder,position);
//				}
//				}
//			}
//		});
//		
//		
//		// 点击进入帖子列表
//		convertView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(context, CircleCommonFragmentActivity.class);
//				intent.putExtra("item", (Serializable)map);
//				intent.putExtra("flag", "allPager");
//				context.startActivity(intent);
//			}
//		});
//		
//		
//		return convertView;
//	}
//	
//	class ViewHolder{
//		RoundImageButton rib_img;
//		ImageButton ibtn_join;
//		TextView tv_title,tv_price,tv_content;
//	}
//	
//	private void addCircle(final Map<String, Object> map,final ViewHolder holder,final int position){
//		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context,
//					String... params) throws Exception {
//				return ComModel2.addCircle(context, (String)map.get("circle_id"));
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
//					ToastUtil.showShortText(context, "成功加入圈子");
//					CircleAllPagerAdapter.this.result.get(position).put("isNo", "1");
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
//	private void exitCircle(final Map<String, Object> map,final ViewHolder holder,final int position){
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
//					CircleAllPagerAdapter.this.result.get(position).put("isNo", "0");
//					
//					notifyDataSetChanged();
//				}
//				}
//			}
//			
//		}.execute((String)map.get("circle_id"));
//	}
//	
//}
