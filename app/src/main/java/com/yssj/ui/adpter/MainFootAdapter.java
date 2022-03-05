//package com.yssj.ui.adpter;
//
//import java.util.HashMap;
//import java.util.List;
//
//import com.yssj.activity.R;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.PicassoUtils;
//import com.yssj.utils.SetImageLoader;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//
//public class MainFootAdapter extends BaseAdapter {
//	private Context context;
//	private List<HashMap<String, Object>> listData;
//
//	public MainFootAdapter(Context context) {
//		this.context = context;
//	
//	}
//	
//	public void setData(List<HashMap<String, Object>>listData){
//		LogYiFu.e("TAG", "listdata ="+listData.toString());
//		this.listData = listData;
//		notifyDataSetChanged();
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return listData==null?0:listData.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return listData.get(position);
//	}
//
//	@Override
//	public long getItemId(int arg0) {
//		// TODO Auto-generated method stub
//		return arg0;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		Holder holder;
//		if (convertView == null) {
//			holder = new Holder();
//			LayoutInflater mInflater = LayoutInflater.from(context);
//			convertView = mInflater.inflate(R.layout.main_foot_item, null);
//			holder.img_foot = (ImageView) convertView
//					.findViewById(R.id.img_foot);
//			convertView.setTag(holder);
//
//		} else {
//			holder = (Holder) convertView.getTag();
//		}
//		String img_url = (String) listData.get(position).get("def_pic");
//		holder.img_foot.setTag(img_url);
////		SetImageLoader.initImageLoader(context, holder.img_foot, img_url,"");
//		PicassoUtils.initImage(context, img_url, holder.img_foot);
//
//		return convertView;
//	}
//	
//	class Holder{
//		ImageView img_foot;
//	}
//
//}