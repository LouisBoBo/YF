//package com.yssj.ui.adpter;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
//import com.yssj.YJApplication;
//import com.yssj.YUrl;
//import com.yssj.activity.R;
//import com.yssj.ui.activity.GuideActivity;
//import com.yssj.ui.activity.integral.AddIntegralProdActivity;
//
//public class IntegralMarketAdapter extends BaseAdapter {
//	private LinkedList<HashMap<String, Object>> mInfos;
//	private DisplayImageOptions options;
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//	private Context context;
//
//	public IntegralMarketAdapter(Context context) {
//		this.context = context;
//		mInfos = new LinkedList<HashMap<String, Object>>();
//		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.ic_stub)
//				.showImageForEmptyUri(R.drawable.ic_empty)
//				.cacheInMemory(true)
//				.cacheOnDisk(true).considerExifParams(true)
//				.displayer(new FadeInBitmapDisplayer(35)).build();
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//
//		ViewHolder holder;
//		// DuitangInfo duitangInfo = mInfos.get(position);
//		final HashMap<String, Object> map = mInfos.get(position);
//
//		if (convertView == null) {
//			LayoutInflater layoutInflator = LayoutInflater.from(parent
//					.getContext());
//			convertView = layoutInflator.inflate(R.layout.integral_infos_list,
//					null);
//			holder = new ViewHolder();
//			holder.imageView = (ImageView) convertView
//					.findViewById(R.id.product_pic);
//			holder.contentView = (TextView) convertView
//					.findViewById(R.id.product_name);
//			holder.btn_exchange = (Button) convertView
//					.findViewById(R.id.btn_exchange);
//			convertView.setTag(holder);
//		}
//		holder = (ViewHolder) convertView.getTag();
//
//		// float iHeight = ((float) 200 / 183 * duitangInfo.getHeight());
//
//		// holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(
//		// LinearLayout.LayoutParams.MATCH_PARENT, (int) duitangInfo
//		// .getHeight()));
//
//		holder.contentView.setText((CharSequence) map.get("shop_se_price"));
//		YJApplication.getLoader().displayImage(
//				YUrl.imgurl + (String) map.get("def_pic"), holder.imageView,
//				options, animateFirstListener);
//
//		holder.btn_exchange.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(context,
//						AddIntegralProdActivity.class);
//				Bundle bundle = new Bundle();
//				bundle.putSerializable("item", map);
//				intent.putExtras(bundle);
//				context.startActivity(intent);
//			}
//		});
//		return convertView;
//	}
//
//	class ViewHolder {
//		ImageView imageView;
//		TextView contentView;
//		TextView timeView;
//		Button btn_exchange;
//	}
//
//	@Override
//	public int getCount() {
//		return mInfos.size();
//	}
//
//	@Override
//	public Object getItem(int arg0) {
//		return mInfos.get(arg0);
//	}
//
//	@Override
//	public long getItemId(int arg0) {
//		return 0;
//	}
//
//	public void addItemLast(List<HashMap<String, Object>> datas) {
//		mInfos.addAll(datas);
//		this.notifyDataSetChanged();
//	}
//
//	public void addItemTop(List<HashMap<String, Object>> datas) {
//		for (HashMap<String, Object> info : datas) {
//			mInfos.addFirst(info);
//		}
//		this.notifyDataSetChanged();
//	}
//
//	public void clearData() {
//		mInfos.clear();
//		// this.notifyDataSetChanged();
//	}
//
//	private static class AnimateFirstDisplayListener extends
//			SimpleImageLoadingListener {
//
//		static final List<String> displayedImages = Collections
//				.synchronizedList(new LinkedList<String>());
//
//		@Override
//		public void onLoadingComplete(String imageUri, View view,
//				Bitmap loadedImage) {
//			if (loadedImage != null) {
//				ImageView imageView = (ImageView) view;
//				boolean firstDisplay = !displayedImages.contains(imageUri);
//				if (firstDisplay) {
//					FadeInBitmapDisplayer.animate(imageView, 500);
//					displayedImages.add(imageUri);
//				}
//			}
//		}
//	}
//}
