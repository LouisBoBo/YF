//package com.yssj.ui.activity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.yssj.activity.R;
//import com.yssj.app.AppManager;
//import com.yssj.custom.view.FixedViewPager;
//import com.yssj.huanxin.widget.photoview.PhotoView;
//import com.yssj.huanxin.widget.photoview.PhotoViewAttacher.OnPhotoTapListener;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.PicassoUtils;
//import com.yssj.utils.SetImageLoader;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//public class ShowIntimateImageActivity extends BasicActivity{
//	
//	private List<View> imageViewLists;
//	private List<String> imageDatas;
//	private FixedViewPager imagePager;
//	private int indext;
//	private Context mContext;
//	private TextView tvCurrent;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		AppManager.getAppManager().addActivity(this);
//		setContentView(R.layout.activity_show_intimate_image_vp);
//		imagePager = (FixedViewPager) findViewById(R.id.show_intimate_image_pager);
//		tvCurrent = (TextView) findViewById(R.id.tv_count);
//		indext = getIntent().getIntExtra("indext", 0);
//		imageDatas = getIntent().getStringArrayListExtra("imageList");
//		imageViewLists = new ArrayList<View>();
//		mContext = this;
//		initViewPager(indext);
//	}
//	
//	/** 初始化ViewPager */
//	private void initViewPager(int index) {
//		tvCurrent.setText((index+1)+"/"+imageDatas.size());
//		for (int i = 0; i < imageDatas.size(); i++) {
//			View v = LayoutInflater.from(mContext).inflate(R.layout.activity_show_intimate_image,null);
//			PhotoView iv = (PhotoView) v.findViewById(R.id.img_main);
////			SetImageLoader.initImageLoader(mContext, iv ,imageDatas.get(i), "");
//			PicassoUtils.initImage(mContext, imageDatas.get(i), iv);
//			imageViewLists.add(v);
//			iv.setOnPhotoTapListener(new OnPhotoTapListener() {
//
//				@Override
//				public void onPhotoTap(View view, float x, float y) {
//					onBackPressed();
//				}
//			});
//		}
//		MyPagerAdapter myPagerAdapter = new MyPagerAdapter(imageViewLists);
//		imagePager.setAdapter(myPagerAdapter);
//		imagePager.setCurrentItem(index);
//		imagePager.setOnPageChangeListener(new OnPageChangeListener() {
//			
//			@Override
//			public void onPageSelected(int arg0) {
//				tvCurrent.setText((arg0+1)+"/"+imageDatas.size());
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
//	}
//	@Override
//	public void onBackPressed() {
//		finish();
//	}
//	
//	class MyPagerAdapter extends PagerAdapter {
//		private List<View> pageLists;
//
//		public MyPagerAdapter(List<View> pageLists) {
//			this.pageLists = pageLists;
//		}
//
//		@Override
//		public int getCount() {
//			return pageLists.size();
//		}
//
//		@Override
//		public Object instantiateItem(ViewGroup container, int position) {
//			container.addView(pageLists.get(position));
//			return pageLists.get(position);
//		}
//
//		@Override
//		public void destroyItem(ViewGroup container, int position, Object object) {
//			container.removeView((View) object);
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			return arg0 == arg1;
//		}
//	}
//	
//}
