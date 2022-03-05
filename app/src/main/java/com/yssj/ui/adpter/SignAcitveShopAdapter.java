package com.yssj.ui.adpter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yssj.activity.R;
import com.yssj.custom.view.ForceLookItemView;
import com.yssj.custom.view.SignActiveShopItemView;
import com.yssj.utils.DP2SPUtil;

public class SignAcitveShopAdapter extends BaseAdapter {
	private List<HashMap<String, Object>> mInfos;
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	private int imageWidth;
	
	private int picHeight;

	private boolean isNotScan;//不是浏览任务

	public SignAcitveShopAdapter(Context context,boolean isNotScan) {
		mInfos = new ArrayList<HashMap<String, Object>>();

		int width = context.getResources().getDisplayMetrics().widthPixels;
		imageWidth = width / 2;
		picHeight=(imageWidth-DP2SPUtil.dp2px(context, 18))*900/600;
		this.isNotScan = isNotScan;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater layoutInflator = LayoutInflater.from(parent
					.getContext());
			convertView = layoutInflator.inflate(R.layout.sign_active_shop_infos_list, null);
			
			holder.left=(SignActiveShopItemView) convertView.findViewById(R.id.left);
			holder.right=(SignActiveShopItemView) convertView.findViewById(R.id.right);
			
			holder.left.getLayoutParams().height=picHeight;
			
			holder.right.getLayoutParams().height=picHeight;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		int index=position*2;
		
		holder.left.initView(mInfos.get(index));
		
		if(mInfos.size()>index+1){
			holder.right.setVisibility(View.VISIBLE);
			holder.right.initView(mInfos.get(index+1));
		}else{
			holder.right.setVisibility(View.INVISIBLE);
		}
		holder.left.setNotScan(isNotScan);
		holder.right.setNotScan(isNotScan);
		return convertView;
	}
	class ViewHolder {
		
		SignActiveShopItemView left;
		SignActiveShopItemView right;
	}

	@Override
	public int getCount() {
		return mInfos.size()%2==0?mInfos.size()/2:mInfos.size()/2+1;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public void addItemLast(List<HashMap<String, Object>> datas) {
		mInfos.addAll(datas);
		this.notifyDataSetChanged();
	}

	public void addItemTop(List<HashMap<String, Object>> datas) {
		mInfos.clear();
		mInfos.addAll(datas);
		this.notifyDataSetChanged();
	}

	public void clearData() {
		mInfos.clear();
		this.notifyDataSetChanged();
	}

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
//				imageView.setImageBitmap(loadedImage);
//				boolean firstDisplay = !displayedImages.contains(imageUri);
//				if (firstDisplay) {
//					FadeInBitmapDisplayer.animate(imageView, 500);
//					displayedImages.add(imageUri);
//				}
//			}
//		}
//	}
//
	public void setData(List<HashMap<String, Object>> result) {
		clearData();
		mInfos.addAll(result);
		this.notifyDataSetChanged();
	}
}
