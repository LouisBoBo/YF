//package com.yssj.ui.adpter;
//
//import java.util.Date;
//import java.util.HashMap;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.app.ArrayAdapterCompat;
//import com.yssj.utils.DateUtil;
//import com.yssj.utils.SetImageLoader;
//
//public class CircleMemAdapter extends
//		ArrayAdapterCompat<HashMap<String, Object>> {
//	private Context context;
//	private LayoutInflater inflater;
//
//	public CircleMemAdapter(Context context) {
//		super(context);
//		this.context = context;
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
//			convertView = inflater.inflate(R.layout.circle_mem_list_item, null);
//
//			holder = new ViewHolder();
//			holder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
//			holder.tv_ismana = (TextView) convertView.findViewById(R.id.tv_ismana);
//			holder.img_user = (ImageView) convertView.findViewById(R.id.img_user);
//
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//
//		HashMap<String, Object> mapObj = getItem(position);
//		holder.tv_username.setText((CharSequence) mapObj.get("nickname"));
//		if (mapObj.get("admin").equals("0")) {
//			holder.tv_ismana.setBackgroundResource(R.drawable.tvchooseno);
//		} else {
//			holder.tv_ismana.setBackgroundResource(R.drawable.tvchoose);
//		}
//		SetImageLoader.initImageLoader(context, holder.img_user, (String) mapObj.get("pic"),"");
//		return convertView;
//	}
//
//	class ViewHolder {
//
//		TextView tv_username, tv_ismana;
//		ImageView img_user;
//	}
//
//}
