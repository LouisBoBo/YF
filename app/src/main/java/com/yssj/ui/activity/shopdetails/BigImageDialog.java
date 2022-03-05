package com.yssj.ui.activity.shopdetails;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.utils.PicassoUtils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


//public class BigImageDialog extends Dialog {
//	private ViewPager viewpager;
//	private String[] url ;
//	private int item ; 
//	private Context mContext;
////	public ImageLoader imageLoader;
////	private DisplayImageOptions options;
////	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//	public BigImageDialog(Context context , int style , String[] url ,int item) {
//		super(context , style);
//		this.url = url;
//		this.mContext = context;
//		this.item = item ; 
//	}
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.dialog_big_image);
//		viewpager = (ViewPager) findViewById(R.id.viewpager);
//		ImagePagerAdpter adpter = new ImagePagerAdpter();
//		viewpager.setAdapter(adpter);
//		viewpager.setCurrentItem(item);
//	
//		
//		
//	}
//	
//	
//	class ImagePagerAdpter extends PagerAdapter{
//		 
//		public ImagePagerAdpter() {
////			imageLoader = ImageLoader.getInstance();
////			options = new DisplayImageOptions.Builder()
////					.showImageOnLoading(R.drawable.ic_stub)
////					.showImageForEmptyUri(R.drawable.ic_empty)
////					.cacheInMemory(true)
////					.cacheOnDisk(true).considerExifParams(true)
////					.displayer(new FadeInBitmapDisplayer(35)).build();
//		}
//
//		@Override
//		public int getCount() {
//			if (url != null) {
//				return url.length;
//			}
//			
//			return 0;
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			
//			return arg0 == arg1;
//		}
//		
//		@Override
//		public Object instantiateItem(ViewGroup container, int position) {
//			View view = LayoutInflater.from(mContext).inflate(R.layout.pager_big_imgview, null);
//			ImageView img = (ImageView) view.findViewById(R.id.big_img);
////			YJApplication.getLoader().displayImage(url[position],img, options, animateFirstListener);
//			
//			
//			PicassoUtils.initImage(mContext, url[position], img);
//			
//			
//			img.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					dismiss();
//					
//				}
//			});
//			((ViewPager)container).addView(view);
//			return view;
//		}
//		@Override
//		public void destroyItem (ViewGroup container, int position, Object object) {
//			container.removeView((View)object);
//		}
//		
//	}
////	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
////
////		static final List<String> displayedImages = Collections
////				.synchronizedList(new LinkedList<String>());
////
////		@Override
////		public void onLoadingComplete(String imageUri, View view,
////				Bitmap loadedImage) {
////			if (loadedImage != null) {
////				ImageView imageView = (ImageView) view;
////				boolean firstDisplay = !displayedImages.contains(imageUri);
////				if (firstDisplay) {
////					FadeInBitmapDisplayer.animate(imageView, 500);
////					displayedImages.add(imageUri);
////				}
////			}
////		}
////}
//
//}
