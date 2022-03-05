package com.yssj.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
//import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
//import com.nostra13.universalimageloader.core.imageaware.ImageAware;
//import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.ui.activity.GuideActivity;

public class SetImageLoader {


    public static void initImageLoader(Context context, ImageView imageView, String url, String endUrl) {

        PicassoUtils.initImage(context, url + endUrl, imageView);

    }


//	private static DisplayImageOptions options;
//	private static ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//	private static ImageLoaderConfiguration config ;
//	private static ImageLoader imageLoader = ImageLoader.getInstance();
//
//
//	public static ImageLoader getInstance(){
//
//		return imageLoader;
//	}

//	public static void initImageLoader(Context context,final ImageView view,
//			String url,String para) {
//		url = url + para;
//		options = getImageOptions();
//		config = getConfig(context);
//		imageLoader.init(config);
//		ImageAware imageAware = new ImageViewAware(view, true);
    // ImageLoader.getInstance().displayImage(YUrl.imgurl + url,
    // imageAware);
//			if (!TextUtils.isEmpty(url) && url.contains("http://"))
//				YJApplication.getLoader().displayImage(url, imageAware, options,
//						animateFirstListener);
//			else if(!TextUtils.isEmpty(url) &&url.contains("https://")){
//				YJApplication.getLoader().displayImage(url, imageAware, options,
//						animateFirstListener);
//			}else
//				YJApplication.getLoader().displayImage(YUrl.imgurl + url, imageAware,
//						options, animateFirstListener);
    // imageLoader.loadImage(YUrl.imgurl + url, options,
    // new SimpleImageLoadingListener() {
    // @Override
    // public void onLoadingComplete(String imageUri, View v,
    // Bitmap loadedImage) {
    // super.onLoadingComplete(imageUri, view, loadedImage);
    // view.setImageBitmap(loadedImage);
    // }
    // });


//	}
//	public static void initImageLoader2(Context context,final ImageView view,
//			String url,String para) {
//		url = url + para;
//		options = getImageOptions2();
////		config = getConfig(context);
////		imageLoader.init(config);
//		ImageAware imageAware = new ImageViewAware(view, true);
//		// ImageLoader.getInstance().displayImage(YUrl.imgurl + url,
//		// imageAware);
//		if (!TextUtils.isEmpty(url) && url.contains("http://"))
//			YJApplication.getLoader().displayImage(url, imageAware, options,
//					animateFirstListener);
//		else if(!TextUtils.isEmpty(url) &&url.contains("https://")){
//			YJApplication.getLoader().displayImage(url, imageAware, options,
//					animateFirstListener);
//		}else
//			YJApplication.getLoader().displayImage(YUrl.imgurl + url, imageAware,
//					options, animateFirstListener);
//		// imageLoader.loadImage(YUrl.imgurl + url, options,
//		// new SimpleImageLoadingListener() {
//		// @Override
//		// public void onLoadingComplete(String imageUri, View v,
//		// Bitmap loadedImage) {
//		// super.onLoadingComplete(imageUri, view, loadedImage);
//		// view.setImageBitmap(loadedImage);
//		// }
//		// });
//
//
//	}
//	public static void initImageLoader3(Context context,final ImageView view,
//			String url,String para) {
//		url = url + para;
//		options = getImageOptions3();
////		config = getConfig(context);
////		imageLoader.init(config);
//		ImageAware imageAware = new ImageViewAware(view, true);
//		// ImageLoader.getInstance().displayImage(YUrl.imgurl + url,
//		// imageAware);
//		if (!TextUtils.isEmpty(url) && url.contains("http://"))
//			YJApplication.getLoader().displayImage(url, imageAware, options,
//					animateFirstListener);
//		else if(!TextUtils.isEmpty(url) &&url.contains("https://")){
//			YJApplication.getLoader().displayImage(url, imageAware, options,
//					animateFirstListener);
//		}else
//			YJApplication.getLoader().displayImage(YUrl.imgurl + url, imageAware,
//					options, animateFirstListener);
//		// imageLoader.loadImage(YUrl.imgurl + url, options,
//		// new SimpleImageLoadingListener() {
//		// @Override
//		// public void onLoadingComplete(String imageUri, View v,
//		// Bitmap loadedImage) {
//		// super.onLoadingComplete(imageUri, view, loadedImage);
//		// view.setImageBitmap(loadedImage);
//		// }
//		// });
//
//
//	}
/**
 private static ImageLoaderConfiguration getConfig(Context context) {
 // 线程池内加载的数量 .memoryCacheExtraOptions(480, 800)
 // maxwidth, maxheight，即保存的每个缓存文件的最大长宽
 // .discCacheFileNameGenerator(newMd5FileNameGenerator())
 // 将保存的时候的URI名称用MD5 加密
 // .discCache(new UnlimitedDiscCache(cacheDir))
 // 自定义缓存路径
 // connectTimeout(5s), readTimeout(30s)超时时间
 //		.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
 //		.writeDebugLogs() // Remove for releaseapp
 //		.memoryCache(new UsingFreqLimitedMemoryCache(5 * 1024 * 1024))
 // You can pass your own memory cache
 // implementation/你可以通过自己的内存缓存实现
 ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
 context)
 .threadPoolSize(3)
 .threadPriority(Thread.NORM_PRIORITY - 2)
 .denyCacheImageMultipleSizesInMemory()
 .memoryCacheSize((int)Runtime.getRuntime().maxMemory()/4)
 .diskCacheSize(5*1024*1024)
 .tasksProcessingOrder(QueueProcessingType.LIFO)
 .diskCacheFileCount(100)// 缓存的文件数量
 .imageDownloader(
 new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
 .build();// 开始构建
 return config;
 }*/

//	public static void initImageLoader(Context context, RoundImageButton view,
//			String url) {
//		options = getImageOptions();
//		//imageLoader.init(getConfig(context));
//		if (!TextUtils.isEmpty(url) && url.contains("http://"))
//			YJApplication.getLoader().displayImage(url, view, options,
//					animateFirstListener);
//		else
//			YJApplication.getLoader().displayImage(YUrl.imgurl + url, view,
//					options, animateFirstListener);
//	}

//
//	public static DisplayImageOptions getImageOptions() {
//		return new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.image_default)
//				.showImageForEmptyUri(R.drawable.ic_empty)
//				.showImageOnFail(R.drawable.image_default).cacheInMemory(true)
//				.bitmapConfig(Bitmap.Config.RGB_565)
//				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true)
//				.considerExifParams(true)
//				.displayer(new FadeInBitmapDisplayer(35)).build();
//	}
//	public static DisplayImageOptions getImageOptions2() {
//		return new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.image_default)
//				.showImageForEmptyUri(R.drawable.ic_empty)
//				.showImageOnFail(R.drawable.image_default).cacheInMemory(true)
//				.bitmapConfig(Bitmap.Config.RGB_565)
//				.imageScaleType(ImageScaleType.NONE).cacheOnDisk(true)
//				.considerExifParams(true)
//				.displayer(new FadeInBitmapDisplayer(35)).build();
//	}
//	public static DisplayImageOptions getImageOptions3() {
//		return new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.image_default)
//				.showImageForEmptyUri(R.drawable.ic_empty)
//				.showImageOnFail(R.drawable.image_default).cacheInMemory(true)
//				.bitmapConfig(Bitmap.Config.RGB_565)
//				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true)
//				.considerExifParams(true)
//				.build();
//	}

//	public static void initRoundImageLoader(Context context, ImageView view,
//			String url) {
//		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.ic_stub)
//				.showImageForEmptyUri(R.drawable.ic_empty)
//				.showImageOnFail(R.drawable.image_default).cacheInMemory(false)
//				.bitmapConfig(Bitmap.Config.RGB_565)
//				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true)
//				.considerExifParams(true)
//				.displayer(new RoundedBitmapDisplayer(35)).build();
//		if(!TextUtils.isEmpty(url) &&url.contains("https://")){
//			YJApplication.getLoader().displayImage(YUrl.imgurl + url, view,
//					options, animateFirstListener);
//		}else
//			YJApplication.getLoader().displayImage(YUrl.imgurl + url, view,
//				options, animateFirstListener);
//	}
/**
 public static void loadImage(Context context, final ImageView view,
 String url){
 options = getImageOptions();
 //config = getConfig(context);
 //imageLoader.init(config);
 // ImageLoader.getInstance().displayImage(YUrl.imgurl + url,
 // imageAware);
 if (!TextUtils.isEmpty(url) && url.contains("http://")){
 YJApplication.getLoader().loadImage(url, options,
 new SimpleListener(view, url));}
 else{
 YJApplication.getLoader().loadImage(YUrl.imgurl+url, options, new SimpleListener(view, url));}
 }*/

//	public static class AnimateFirstDisplayListener extends
//			SimpleImageLoadingListener {
//
//		public static final List<String> displayedImages = Collections
//				.synchronizedList(new LinkedList<String>());
//
//		@Override
//		public void onLoadingComplete(String imageUri, View view,
//				Bitmap loadedImage) {
//			if (loadedImage != null) {
//				ImageView imageView = (ImageView) view;
//				boolean firstDisplay = !displayedImages.contains(imageUri);
//				if (firstDisplay) {
//					FadeInBitmapDisplayer.animate(imageView, 800);
//					displayedImages.add(imageUri);
//				}
//			}
//		}
//	}

//	public static class SimpleListener extends SimpleImageLoadingListener{
//		private ImageView imgView;
//		private String imgUrl;
//		public SimpleListener(ImageView iView,String imgUrl) {
//			// TODO Auto-generated constructor stub
//			this.imgView = iView;
//			this.imgUrl = imgUrl;
//		}
//		@Override
//		public void onLoadingComplete(String imageUri, View view,
//				Bitmap loadedImage) {
//			super.onLoadingComplete(imageUri, view, loadedImage);
//			if(imgUrl.equals(imgView.getTag())){
//				imgView.setImageBitmap(loadedImage);
//			}
//		}
//
//		@Override
//		public void onLoadingStarted(String imageUri, View view) {
//			super.onLoadingStarted(imageUri, view);
//			imgView.setImageResource(R.drawable.image_default);
//		}
//	}
}

