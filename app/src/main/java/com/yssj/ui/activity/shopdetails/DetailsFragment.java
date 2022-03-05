//package com.yssj.ui.activity.shopdetails;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.List;
//
//import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
////import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.FailReason;
//import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
//import com.yssj.YConstance;
//import com.yssj.YJApplication;
//import com.yssj.YUrl;
//import com.yssj.activity.R;
//import com.yssj.custom.view.MyScrollView;
//import com.yssj.custom.view.MyScrollView.OnScrollListener;
//import com.yssj.entity.Shop;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.GuideActivity;
//import com.yssj.utils.FileUtils;
//import com.yssj.utils.MD5Tools;
//import com.yssj.utils.LogYiFu;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
///***
// * 详情
// * 
// * @author Administrator
// * 
// */
//public class DetailsFragment extends Fragment {
//	private View view;
//	private TextView tv_shopconnect;
//	private static Shop shop;
//
//	private LinearLayout viewContainer;
//
//	private static MyScrollView myScrollView;
//	private int pos;
//	
//	private ImageLoader imageLoader;
//	private ImageLoaderConfiguration config;
//	private static Context context;
//
//	// public DetailsFragment() {
//	// super();
//	// }
//
//	public static DetailsFragment newInstance(Shop mshop, MyScrollView myscrollView) {
//		 DetailsFragment detailsFragment = new DetailsFragment();
//		shop = mshop;
//		myScrollView = myscrollView;
//		 return detailsFragment;
//	}
//
//	private void initScrollView() {
//		myScrollView.getView();
//		myScrollView.setOnScrollListener(new OnScrollListener() {
//			@Override
//			public void onTop() {
//			}
//
//			@Override
//			public void onScroll() {
//				new PauseOnScrollListener(YJApplication.getLoader(), true, true);
//			}
//
//			@Override
//			public void onBottom() {
//			}
//
//			@Override
//			public void onAutoScroll(int l, int t, int oldl, int oldt) {
//			}
//
//			@Override
//			public void onScrollStop() {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		initImageLoader();
//		cacheDir = FileUtils.getAppFile();
//		// cacheDir.delete();
//
//		//imageLoader = ImageLoader.getInstance();
//	}
//
//	@Override
//	public void onDestroy() {
//		// TODO Auto-generated method stub
//		if (imageLoader.isInited()) {
//		imageLoader.clearMemoryCache();
//		imageLoader.stop();
////		imageLoader.destroy();
//		}
//		super.onDestroy();
//	}
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		view = inflater.inflate(R.layout.fragment_details, container, false);
//		viewContainer = (LinearLayout) view.findViewById(R.id.viewContainer);
//
//		initScrollView();
//
//		String[] shop_pic = shop.getShop_pic().split(",");
//		addView(viewContainer, shop_pic);
//		return view;
//	}
//
//	private void addView(LinearLayout container, String[] shop_pic) {
//		container.removeAllViews();
//		LayoutInflater inflater = LayoutInflater.from(getActivity());
//		LogYiFu.e("TAG", "保存前删除文件夹");
//		File fileDirec = new File(YConstance.savePicPath);
//		if(!fileDirec.exists()){
//			fileDirec.mkdir();
//		}
//		File file = new File(YConstance.savePicPath);
//		if (file.listFiles().length > 0) {
//			for (File file2 : file.listFiles()) {
//				file2.delete();
//			}
//		}
//		for (int i = 0; i < shop_pic.length; i++) {
//			View convertView = inflater.inflate(R.layout.listview_details_img,
//					null);
//			ImageView img_details = (ImageView) convertView
//					.findViewById(R.id.img_details);
//			convertView.setPadding(0, 0, 0, 10);
//			final String picName = String.valueOf(i);
//
//			YJApplication.getLoader().displayImage(YUrl.imgurl + shop_pic[i]+"!450", img_details,
//					options, new ImageLoadingListener() {
//
//						@Override
//						public void onLoadingCancelled(String arg0, View arg1) {
//
//						}
//
//						@Override
//						public void onLoadingComplete(String s, View v,
//								final Bitmap b) {
//							saveBitmap(picName, b);
//						}
//
//						@Override
//						public void onLoadingFailed(String arg0, View arg1,
//								FailReason arg2) {
//						}
//
//						@Override
//						public void onLoadingStarted(String arg0, View arg1) {
//
//						}
//
//					});
//
//			container.addView(convertView);
//		}
//	}
//	
//	private void initImageLoader(){
//
//		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.image_default)
//				.showImageForEmptyUri(R.drawable.ic_empty)
//				.cacheInMemory(true)
//				.bitmapConfig(Bitmap.Config.RGB_565)
//				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true)
//				.considerExifParams(true)
//				// .displayer(new FadeInBitmapDisplayer(35))
//				.build();
//
//		config = new ImageLoaderConfiguration.Builder(getActivity())
//				.threadPoolSize(1)
//				.threadPriority(Thread.NORM_PRIORITY - 2)
//				.denyCacheImageMultipleSizesInMemory()
//				.memoryCache(new WeakMemoryCache())
//				// .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 *
//				// 1024))
//				// You can pass your own memory cache
//				// implementation/你可以通过自己的内存缓存实现
//				.memoryCacheSize(6 * 1024 * 1024)
//				.discCacheSize((int) Runtime.getRuntime().maxMemory() / 8)
//				.tasksProcessingOrder(QueueProcessingType.LIFO)
//				.discCacheFileCount(100)
//				.memoryCacheExtraOptions(480, 800)
//
//				.discCacheSize(50 * 1024 * 1024)
//				.threadPriority(Thread.NORM_PRIORITY - 2)
//				// 设置线程的优先级
//				// 缓存的文件数量
//				.imageDownloader(
//						new BaseImageDownloader(getActivity(), 5 * 1000, 30 * 1000))
//				.writeDebugLogs() // Remove for releaseapp
//				.build();// 开始构建
//
//		imageLoader = ImageLoader.getInstance();
//		imageLoader.init(config);
//	
//	}
//
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//	private DisplayImageOptions options;
////	public ImageLoader imageLoader;
//	private File cacheDir;
//
//	/** 保存图片到硬盘中 */
//	public void saveBitmap(final String picName, final Bitmap bm) {
//
//		if (null != bm) {
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					File fileDirec = new File(YConstance.savePicPath);
//					if(!fileDirec.exists()){
//						fileDirec.mkdir();
//					}
//					File file = new File(YConstance.savePicPath);
//					if(file.listFiles().length==8){
//						ComModel2.saveQRCode(getActivity(), shop.getShop_code());
//						return ;
//					}
//					File f = new File(YConstance.savePicPath,
//							MD5Tools.md5(picName) + ".jpg");
//					if (f.exists()) {
//						f.delete();
//					}
//					// File file = new File(YConstance.savePicPath);
//					// if (file.exists()) {
//					// file.delete();
//					// }
//					// File f = new File(file, picName);
//					try {
//						FileOutputStream out = new FileOutputStream(f);
//						bm.compress(Bitmap.CompressFormat.PNG, 90, out);
//						out.flush();
//						out.close();
//						LogYiFu.i("TAG", "已经保存");
//						// bm.recycle();
//					} catch (FileNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}).start();
//
//		}
//	}
//
//	@Override
//	public void onHiddenChanged(boolean hidden) {
//		// TODO Auto-generated method stub
//		AnimateFirstDisplayListener.displayedImages.clear();
//		if(hidden == true){
//			if (imageLoader.isInited()) {
//			imageLoader.clearMemoryCache();
//			imageLoader.stop();
////			imageLoader.destroy();
//			}
//		}
//		super.onHiddenChanged(hidden);
//	}
//
//	static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
//
//		static List<String> displayedImages = Collections
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
