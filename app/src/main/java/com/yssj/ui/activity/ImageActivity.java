//package com.yssj.ui.activity;
//
//import android.os.Bundle;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.custom.view.OnlyImageView;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.SetImageLoader;
//
//public class ImageActivity extends BasicActivity {
//	private String urls[];
//	private ViewPager viewPager;
//	private TextView pageText;
//	
//	private OnlyImageView onlyImageView;
//
//	private int imagePosition = 0;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_image);
//
////		configImageLoader();
//
//		Bundle bundle = getIntent().getExtras();
//		urls = bundle.getStringArray("urls");
//
////		imagePosition    = bundle.getInt("index");
//		
//
//		imagePosition=bundle.getInt("index");
//
//		aBar.hide();
//		
//		pageText = (TextView) findViewById(R.id.page_text);
//		viewPager = (ViewPager) findViewById(R.id.view_pager);
//		ViewPagerAdapter adapter = new ViewPagerAdapter();
//		viewPager.setAdapter(adapter);
//		viewPager.setCurrentItem(imagePosition);	
//		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
//
//			@Override
//			public void onPageSelected(int arg0) {
//				// TODO Auto-generated method stub
//				pageText.setText((arg0 + 1) + "/" + urls.length);
//			}
//
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//		viewPager.setEnabled(false);
//		// 设定当前的页数和总页数
//		
//		pageText.setText((imagePosition+1) + "/" + urls.length);
//		
//	}
//	
//
//	class ViewPagerAdapter extends PagerAdapter {
//		 
//
//		@Override
//		public Object instantiateItem(ViewGroup container, int position) {
//			// String imagePath = getImagePath(urls[position]);
//			// Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//			// if (bitmap == null) {
//			// bitmap = BitmapFactory.decodeResource(getResources(),
//			// R.drawable.ic_launcher);
//			// }
//
//			View view = LayoutInflater.from(ImageActivity.this).inflate(
//					R.layout.image_layout, null);
//			OnlyImageView imageView = (OnlyImageView) view
//					.findViewById(R.id.image_view);
//			imageView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					
//				}
//			});
//
//			
//
//			SetImageLoader.initImageLoader(null, imageView, urls[position],"");
//			
//			container.addView(view);
//			
//			
//			return view;
//		}
//
//		@Override
//		public int getCount() {
//			return urls.length;
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			return arg0 == arg1;
//		}
//
//		@Override
//		public void destroyItem(ViewGroup container, int position, Object object) {
//			View view = (View) object;
//			container.removeView(view);
//		}
//
//	}
//
//	/**
//	 * 配置ImageLoder
//	 */
////	private void configImageLoader() {
////		// 初始化ImageLoader
////		@SuppressWarnings("deprecation")
////		DisplayImageOptions options = new DisplayImageOptions.Builder()
////				.showStubImage(R.drawable.ic_launcher)
////				.showImageForEmptyUri(R.drawable.ic_launcher)
////				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
////				.cacheOnDisc(true).build();
////
////		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
////				getApplicationContext()).defaultDisplayImageOptions(options)
////				.threadPriority(Thread.NORM_PRIORITY - 2)
////				.denyCacheImageMultipleSizesInMemory()
////				.discCacheFileNameGenerator(new Md5FileNameGenerator())
////				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
////		ImageLoader.getInstance().init(config);
////	}
//
//}
