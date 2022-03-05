//package com.yssj.ui.adpter;
//
//import java.util.List;
//
//import com.yssj.activity.R;
//import com.yssj.custom.view.BizCircleItemView;
//import com.yssj.custom.view.BizCircleTitleView;
//import com.yssj.custom.view.SlideShowView;
//import com.yssj.custom.view.SubTimeView;
//import com.yssj.entity.BizCircleEntity;
//import com.yssj.ui.activity.BizDetailActivity;
//import com.yssj.ui.activity.BizTitleCheckActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//public class BizCircleAdapter extends BaseAdapter {
//	
//	private Context context;
//	
//	private List<BizCircleEntity> list;
//	
//	private boolean isHome;//是否是主页
//	
//	private int width;
//	
//	public BizCircleAdapter(Context context, List<BizCircleEntity> list,boolean isHome) {
//		super();
//		this.context = context;
//		this.list = list;
//		this.isHome=isHome;
//		width=context.getResources().getDisplayMetrics().widthPixels;
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
////		return list.size()%2==0?list.size()/2:list.size()/2+1;
//		return 20;
//	}
//	/**
//	 * 添加轮播图数据
//	 */
//	public void setPagersData(){
//		
//		
//		
//	}
//	
//	/**
//	 * 限时购买数据
//	 */
//	public void setXianshiData(){
//		
//	}
//	/**
//	 * 设置导航栏数据
//	 */
//	public void setTitleData(){
//		
//		
//	}
//	
//	@Override
//	public Object getItem(int arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public long getItemId(int arg0) {
//		// TODO Auto-generated method stub
//		return arg0;
//	}
//
//	@Override
//	public View getView(int position, View view, ViewGroup arg2) {
//		ViewHolder vh;
//		if(view==null){
//			vh=new ViewHolder();
//			view=LayoutInflater.from(context).inflate(R.layout.biz_circle_adapter_item, arg2,false);
//			vh.left=(BizCircleItemView) view.findViewById(R.id.left);
//			vh.right=(BizCircleItemView) view.findViewById(R.id.right);
//			vh.pagers=(SlideShowView) view.findViewById(R.id.pagers);
//			vh.pagers.getLayoutParams().height=width/2;
//			vh.data=view.findViewById(R.id.item_data);
//			vh.title=(BizCircleTitleView) view.findViewById(R.id.biz_title);
//			vh.subTime=(SubTimeView) view.findViewById(R.id.sub_time);
//			view.setTag(vh);
//		}else{
//			vh=(ViewHolder) view.getTag();
//		}
//		
//		if(isHome){//如果是主页
//			if(position==0){//轮播图
//				vh.pagers.setVisibility(view.VISIBLE);
//				vh.data.setVisibility(view.GONE);
//				vh.title.setVisibility(view.GONE);
//				vh.subTime.setVisibility(view.GONE);
//				return view;
//			}
//			if(position==1){//限时抢购
//				vh.pagers.setVisibility(view.GONE);
//				vh.data.setVisibility(view.GONE);
//				vh.title.setVisibility(view.GONE);
//				vh.subTime.setVisibility(view.VISIBLE);
//				vh.subTime.setData();
//				return view;
//			}
//			
//			if(position==2){//导航
//				vh.pagers.setVisibility(view.GONE);
//				vh.data.setVisibility(view.GONE);
//				vh.title.setVisibility(view.VISIBLE);
//				vh.subTime.setVisibility(view.GONE);
//				
//				return view;
//			}
//			
//			position=position-3;
//		}
//		
//		vh.pagers.setVisibility(view.GONE);
//		vh.data.setVisibility(view.VISIBLE);
//		vh.title.setVisibility(view.GONE);
//		vh.subTime.setVisibility(view.GONE);
//		
//		position=position*2;
//		
////		vh.left.setData(list.get(position));	
////		if(position+1<list.size()){
////			vh.right.setData(list.get(position+1));
////		}else{
////			vh.right.setVisibility(view.INVISIBLE);
////		}	
//		
//		vh.left.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				context.startActivity(new Intent(context, BizDetailActivity.class));
//			}
//		});
//		vh.right.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				context.startActivity(new Intent(context, BizDetailActivity.class));
//			}
//		});
//		return view;
//	}
//	
//	
//	private static class ViewHolder{
//		SlideShowView pagers;
//		BizCircleItemView left;
//		BizCircleItemView right;
//		View data;
//		BizCircleTitleView title;
//		SubTimeView subTime;
//	}
//	
//}
