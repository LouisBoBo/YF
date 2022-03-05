//package com.yssj.ui.fragment.integral;
//
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.List;
//
//import android.app.Activity;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
//import com.yssj.Constants;
//import com.yssj.YConstance;
//import com.yssj.YJApplication;
//import com.yssj.activity.R;
//import com.yssj.custom.view.NestedListView;
//import com.yssj.entity.IntegralShop;
//import com.yssj.entity.Shop;
//import com.yssj.ui.activity.GuideActivity;
//
///***
// * 详情
// * 
// * @author Administrator
// * 
// */
//public class IntegralDetailsFragment extends Fragment {
//	private View view;
//	private NestedListView listView;
//	private ListViewAdpter adpter;
//	private TextView tv_shopconnect;
//	private IntegralShop shop;
//
//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//	}
//
//	public IntegralDetailsFragment(IntegralShop shop) {
//		this.shop = shop;
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		view = inflater.inflate(R.layout.fragment_details, container, false);
////		listView = (NestedListView) view.findViewById(R.id.nListview);
//		tv_shopconnect = (TextView) view.findViewById(R.id.tv_shopconnect);
//
//		if (shop != null) {
//
//			String str_connect = shop.getContent();
//			if (!TextUtils.isEmpty(str_connect)) {
//				tv_shopconnect.setText(str_connect);
//			}
//		}
//		refeshImagView();
//		return view;
//	}
//
//	public void refeshImagView() {
//		if (adpter == null) {
//			if (shop != null) {
//				String[] shop_pic = shop.getShop_pic().split(",");
//				adpter = new ListViewAdpter(shop_pic);
//				listView.setAdapter(adpter);
//			}
//		} else {
//			adpter.notifyDataSetChanged();
//		}
//	}
//
//	class ListViewAdpter extends BaseAdapter {
//
//		private DisplayImageOptions options;
//		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//
//		private String[] shop_pic;
//
//		public ListViewAdpter(String[] shop_pic) {
//			this.shop_pic = shop_pic;
//			options = new DisplayImageOptions.Builder()
//					.showImageOnLoading(R.drawable.ic_stub)
//					.showImageForEmptyUri(R.drawable.ic_empty)
//					.cacheInMemory(true)
//					.cacheOnDisk(true).considerExifParams(true)
//					.displayer(new FadeInBitmapDisplayer(35)).build();
//		}
//
//		@Override
//		public int getCount() {
//			if (shop_pic != null && shop_pic.length > 0) {
//				return shop_pic.length;
//			}
//			return 0;
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			if (shop_pic != null && shop_pic.length > 0) {
//				return shop_pic[arg0];
//			}
//			return 0;
//		}
//
//		@Override
//		public long getItemId(int position) {
//
//			return position;
//		}
//
//		@Override
//		public int getViewTypeCount() {
//
//			return super.getViewTypeCount();
//		}
//
//		@Override
//		public int getItemViewType(int position) {
//
//			return super.getItemViewType(position);
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			Holder_Image holder = null;
//			if (convertView == null) {
//				holder = new Holder_Image();
//				convertView = LayoutInflater.from(getActivity()).inflate(
//						R.layout.listview_details_img, null);
//				holder.img_details = (ImageView) convertView
//						.findViewById(R.id.img_details);
//				convertView.setTag(holder);
//
//			} else {
//				holder = (Holder_Image) convertView.getTag();
//			}
//			if (shop_pic != null && shop_pic.length > 0) {
//
//				YJApplication.getLoader().displayImage(shop_pic[position],
//						holder.img_details, options, animateFirstListener);
//			}
//
//			return convertView;
//		}
//
//		class Holder_Image {
//			ImageView img_details;
//
//		}
//
//		private class AnimateFirstDisplayListener extends
//				SimpleImageLoadingListener {
//
//			List<String> displayedImages = Collections
//					.synchronizedList(new LinkedList<String>());
//
//			@Override
//			public void onLoadingComplete(String imageUri, View view,
//					Bitmap loadedImage) {
//				if (loadedImage != null) {
//					ImageView imageView = (ImageView) view;
//					boolean firstDisplay = !displayedImages.contains(imageUri);
//					if (firstDisplay) {
//						FadeInBitmapDisplayer.animate(imageView, 500);
//						displayedImages.add(imageUri);
//					}
//				}
//			}
//		}
//
//	}
//}
