//package com.yssj.ui.adpter;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//
//import com.yssj.activity.R;
//import com.yssj.utils.PicassoUtils;
//import com.yssj.utils.SetImageLoader;
//
//public class PostDetailImageAdapter extends BaseAdapter {
//	private Context context;
//	private String[] datas;
//
//	public PostDetailImageAdapter(Context context,String[] datas) {
//		this.context = context;
//		this.datas = datas;
//	}
//	
//	@Override
//	public int getCount() {
//		return datas.length;
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return datas[position];
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
//			convertView = View.inflate(context, R.layout.activity_post_detail_img_item, null);
//			holder.img_goods = (ImageView) convertView.findViewById(R.id.img_goods);
//			convertView.setTag(holder);
//		}else{
//			holder = (ViewHolder) convertView.getTag();
//		}
////		SetImageLoader.initImageLoader(context, holder.img_goods, datas[position],"");
//		PicassoUtils.initImage(context, datas[position], holder.img_goods);
//		
//		return convertView;
//	}
//	
//	class ViewHolder{
//		ImageView img_goods;
//	}
//}
