//package com.yssj.ui.adpter;
//
//import java.util.HashMap;
//import java.util.List;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.yssj.activity.R;
//import com.yssj.app.ArrayAdapterCompat;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.MyListView;
//import com.yssj.custom.view.RoundImageButton;
//import com.yssj.entity.Order;
//import com.yssj.entity.OrderShop;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.entity.ShopCart;
//import com.yssj.model.ComModel;
//import com.yssj.ui.fragment.orderinfo.OrderDetailsActivity;
//import com.yssj.ui.fragment.orderinfo.OrderListSonAdapter;
//import com.yssj.ui.fragment.orderinfo.WalletDialog;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.YCache;
//
//
//public class MyCircleAdapter extends ArrayAdapterCompat<HashMap<String, Object>> {
//	private Context context;
//	private LayoutInflater inflater;
//	private FragmentActivity activity;
//	
//
//	
//	public MyCircleAdapter(Context context ) {
//		super(context);
//		this.context = context;
//		this.activity = (FragmentActivity) context;
//
//		inflater = LayoutInflater.from(context);
//		
//	}
//
//	
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//	
//		ViewHolder holder;
//		if (null == convertView) {
//			convertView = inflater.inflate(R.layout.my_circle_item, null);
//			
//			holder = new ViewHolder();
//			holder.img_title =(ImageView) convertView.findViewById(R.id.img_title);
//			holder.tv_title =(TextView) convertView.findViewById(R.id.tv_title);
//			holder.tv_sum =(TextView) convertView.findViewById(R.id.tv_sum);
//			holder.tv_desc =(TextView) convertView.findViewById(R.id.tv_desc);
//			
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//		
//		HashMap<String, Object> mapObj = getItem(position);
//		SetImageLoader.initImageLoader(context, holder.img_title, (String) mapObj.get("pic"),"");
//		holder.tv_title.setText((CharSequence) mapObj.get("title"));
//		holder.tv_sum.setText((CharSequence) mapObj.get("u_count")+"  "+(CharSequence) mapObj.get("n_count"));
//		holder.tv_desc.setText((CharSequence) mapObj.get("content"));
//		return convertView;
//	}
//	
//	
//
//	
//
//	class ViewHolder {
//		ImageView img_title;
//		TextView tv_title, tv_sum, tv_desc;
//	}
//
//}
