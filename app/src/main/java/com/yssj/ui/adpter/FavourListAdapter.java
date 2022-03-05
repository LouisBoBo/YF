package com.yssj.ui.adpter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.ArrayAdapterCompat;
import com.yssj.entity.Like;
import com.yssj.entity.Order;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.utils.PicassoUtils;

public class FavourListAdapter extends ArrayAdapterCompat<Like> {

	private Context context;
	private LayoutInflater inflater;

//	public ImageLoader imageLoader;

//	private DisplayImageOptions options;
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public FavourListAdapter(Context context) {
		super(context);
		this.context = context;
		inflater = LayoutInflater.from(context);
//		imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.ic_stub)
//				.showImageForEmptyUri(R.drawable.ic_empty)
//				.cacheInMemory(true)
//				.cacheOnDisk(true).considerExifParams(true)
//				.displayer(new FadeInBitmapDisplayer(35)).build();
	}

	class ViewHolder {
		TextView tv_product_name, tv_price, tv_add_time;
		ImageView img_product;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.favour_item, null);
			holder = new ViewHolder();
			holder.tv_product_name = (TextView) convertView
					.findViewById(R.id.tv_product_name);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.tv_add_time = (TextView) convertView
					.findViewById(R.id.tv_add_time);
			holder.img_product = (ImageView) convertView
					.findViewById(R.id.img_product);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Like like = getItem(position);
		holder.tv_product_name.setText(like.getShop_name());
		holder.tv_price.setText(like.getShop_price() + "");
//		holder.tv_add_time.setText(like.getAdd_time() + "");

//		YJApplication.getLoader().displayImage(like.getShow_pic(),
//				holder.img_product, options, animateFirstListener);
//		
		
		PicassoUtils.initImage(context, like.getShow_pic(), holder.img_product);
		
		
		
		
		
		
		return convertView;
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
//				boolean firstDisplay = !displayedImages.contains(imageUri);
//				if (firstDisplay) {
//					FadeInBitmapDisplayer.animate(imageView, 500);
//					displayedImages.add(imageUri);
//				}
//			}
//		}
//	}
}
