package com.yssj.ui.adpter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yssj.activity.R;
import com.yssj.custom.view.FootItemView;
import com.yssj.data.YDBHelper;
import com.yssj.entity.VipDikouData;

public class MyFootPrintStaggeredAdapter extends BaseAdapter {
	private LinkedList<HashMap<String, Object>> mInfos;
//	private DisplayImageOptions options;
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private Context context;
	private YDBHelper dbHelper;

	private int imageWidth;
	/**是否是编辑状态*/
	private boolean flag = false;
	private boolean isVip; //是否是会员
	private VipDikouData vipDikouData;
	private static List<HashMap<String, Object>> checkList;

	public MyFootPrintStaggeredAdapter(Context context) {
		this.context = context;
		mInfos = new LinkedList<HashMap<String, Object>>();

//		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.ic_stub)
//				.showImageForEmptyUri(R.drawable.ic_empty)
//				.cacheInMemory(true)
//				.cacheOnDisk(true).considerExifParams(true)
//				.displayer(new FadeInBitmapDisplayer(35)).build();

		dbHelper = new YDBHelper(context);
		checkList=new ArrayList<HashMap<String,Object>>();
//		configCheckMap(false, false);
	}
	
	
	
	public static List<HashMap<String, Object>> getCheckList() {
		return checkList;
	}



	public void setEdit(boolean bl){
		this.flag=bl;
		if(bl==false){
			checkList.clear();
		}
		notifyDataSetChanged();
	}
	
//	/**
//	 * CheckBox 是否选择的存储集合,key 是 position , value 是该position是否选中
//	 */
//	private Map<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();
//
//	/**
//	 * 首先,默认情况下,所有项目都是没有选中的.这里进行初始化
//	 */
//	public void configCheckMap(boolean bool, boolean flag) {
//
//		for (int i = 0; i < mInfos.size(); i++) {
//			isCheckMap.put(i, bool);
//		}
//
//		this.flag = flag;
//	}

//	// 移除一个项目的时候
//	public void remove(int position) {
//		this.mInfos.remove(position);
//	}
//
//	public Map<Integer, Boolean> getCheckMap() {
//		return this.isCheckMap;
//	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		// DuitangInfo duitangInfo = mInfos.get(position);
		//final HashMap<String, Object> map = mInfos.get(position);

		if (convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(parent
					.getContext());
			convertView = layoutInflator.inflate(R.layout.foot_item_view,
					null);
			holder = new ViewHolder();
			
			holder.left=(FootItemView) convertView.findViewById(R.id.left);
			holder.right=(FootItemView) convertView.findViewById(R.id.right);
//			holder.imageView = (ImageView) convertView
//					.findViewById(R.id.news_pic);
//			
//			holder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth,imageWidth*900/600));
//			
//			holder.iv_set_top = (ImageButton) convertView
//					.findViewById(R.id.iv_set_top);
//
//			holder.iv_shopcar = (ImageView) convertView
//					.findViewById(R.id.iv_shopcar);
//			holder.iv_love_star = (ImageView) convertView
//					.findViewById(R.id.iv_love_star);
//			holder.iv_star = (ImageView) convertView.findViewById(R.id.iv_star);
//
//			holder.nickback = (TextView) convertView
//					.findViewById(R.id.nickback);
//			holder.contentView = (TextView) convertView
//					.findViewById(R.id.news_title);
//			holder.tv_price = (TextView) convertView
//					.findViewById(R.id.tv_price);
//			holder.cb_favor = (CheckBox) convertView
//					.findViewById(R.id.cb_favor);
//			holder.rl_content = (RelativeLayout) convertView
//					.findViewById(R.id.rl_content);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		position=position*2;
		
		if(isVip){
			holder.left.initViewVip(mInfos.get(position),vipDikouData);
		}else{
			holder.left.initView(flag,mInfos.get(position),false);
		}

		if(mInfos.size()>position+1){
			holder.right.setVisibility(View.VISIBLE);

			if(isVip){
				holder.right.initViewVip(mInfos.get(position+1),vipDikouData);
			}else{
				holder.right.initView(flag,mInfos.get(position+1),false);
			}



		}else{
			holder.right.setVisibility(View.INVISIBLE);
		}
//		convertView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(context, ShopDetailsActivity.class);
//				intent.putExtra("code", (String) map.get("shop_code"));
//				intent.putExtra("shopCarFragment", "shopCarFragment");
//				FragmentActivity activity = (FragmentActivity) context;
//				activity.startActivityForResult(intent, 101);
//
//			}
//		});
		
//		holder.rl_content.setTag(position);
//
//		holder.contentView.setText("【"
//				+ (String) mInfos.get(position).get("shop_name") + "】");
//		
//		double price = Double.parseDouble((String) mInfos.get(position).get("shop_price"));
//	
//		holder.tv_price
//				.setText("￥" +new java.text.DecimalFormat("#0.00").format(price));
//
//		String imgUrl = (String) mInfos.get(position).get("def_pic");
//		holder.imageView.setTag(imgUrl);
//		SetImageLoader.loadImage(null, holder.imageView, imgUrl);
//
//		holder.iv_set_top.setVisibility(View.GONE);
//
//		if (flag) {
//			// holder.ll_mystep_list.setBackgroundColor(Color.RED);
//			holder.cb_favor.setVisibility(View.VISIBLE);
//
//			if (isCheckMap.get(position) == null) {
//				isCheckMap.put(position, false);
//			}
//
//			holder.cb_favor.setChecked(isCheckMap.get(position));
//		} else {
//			// holder.ll_mystep_list.setBackgroundColor(Color.TRANSPARENT);
//			holder.cb_favor.setVisibility(View.GONE);
//		}
//
//		/*
//		 * 设置单选按钮的选中
//		 */
//		holder.cb_favor
//				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//					@Override
//					public void onCheckedChanged(CompoundButton buttonView,
//							boolean isChecked) {
//						isCheckMap.put(position, isChecked);
//					}
//				});
//		double kickBack = Double.parseDouble((String) mInfos.get(position).get("kickback"));
//		
//		holder.nickback.setText(new java.text.DecimalFormat("#0.00").format(kickBack*price)+ "\n返佣");
//
//		if ("0".equals((String) mInfos.get(position).get("isCart"))) {
//			// holder.iv_shopcar.setImageResource(android.R.drawable.btn_star_big_off);
//			holder.iv_shopcar.setVisibility(View.VISIBLE);
//		} else {
//			// holder.iv_shopcar.setImageResource(android.R.drawable.btn_star_big_on);
//			holder.iv_shopcar.setVisibility(View.GONE);
//		}
//
//		holder.iv_love_star.setVisibility(View.VISIBLE);
//		if ("0".equals((String) mInfos.get(position).get("isLike"))) {
//			// holder.iv_love_star.setImageResource(R.drawable.img_love_star);
//			holder.iv_love_star.setVisibility(View.GONE);
//		} else {
//			holder.iv_love_star
//					.setImageResource(R.drawable.img_love_star_default);
//		}
//		
//		holder.rl_content.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				HashMap<String, Object> map=mInfos.get((Integer) arg0.getTag());
//				Intent intent = new Intent(context, ShopDetailsActivity.class);
//				intent.putExtra("code", (String) map.get("shop_code"));
//				intent.putExtra("shopCarFragment", "shopCarFragment");
//				FragmentActivity activity = (FragmentActivity) context;
//				activity.startActivityForResult(intent, 101);
//			}
//		});
		
		
		
		return convertView;
	}

	class ViewHolder {
//		ImageView imageView, iv_shopcar, iv_love_star, iv_star;
//		ImageButton iv_set_top;
//		TextView contentView;
//		TextView tv_price;
//		TextView nickback; // 返佣
//		CheckBox cb_favor;
//		RelativeLayout rl_content;
		
		FootItemView left;
		
		FootItemView right;
		
	}

//	class MySimpleImageLoadingListener extends SimpleImageLoadingListener {
//		private ImageView igView;
//		String url ;
//		public MySimpleImageLoadingListener(ImageView igView, String imgUrl) {
//			// TODO Auto-generated constructor stub
//			this.igView = igView;
//			this.url = imgUrl;
//		}
//
//		@Override
//		public void onLoadingComplete(String imageUri, View view,
//				Bitmap loadedImage) {
//			// TODO Auto-generated method stub
//			super.onLoadingComplete(imageUri, view, loadedImage);
//			if(url.equals(igView.getTag())){
//			igView.setImageBitmap(loadedImage);}
//		}
//		@Override
//		public void onLoadingStarted(String imageUri, View view) {
//			super.onLoadingStarted(imageUri, view);
//			igView.setImageResource(R.drawable.image_default);
//		}
//		
//	}

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

		// for (HashMap<String, Object> data : datas) {
		// mInfos.addFirst(data);
		// }
		mInfos.addAll(datas);
		this.notifyDataSetChanged();
	}

	public void addItemTop(List<HashMap<String, Object>> datas) {
		mInfos.clear();
		for (HashMap<String, Object> info : datas) {
//			mInfos.addFirst(info);
			mInfos.addLast(info);
		}
		
		this.notifyDataSetChanged();
	}

	public void setDataVip(List<HashMap<String, Object>> result, boolean isVip, VipDikouData vipDikouData) {
		this.isVip = isVip;
		this.vipDikouData = vipDikouData;
		clearData();
		mInfos.addAll(result);
		this.notifyDataSetChanged();
	}

	private LinkedList<HashMap<String, Object>> mIsSxInfos = new LinkedList<HashMap<String, Object>>();

	private LinkedList<HashMap<String, Object>> mIsUnSxInfos = new LinkedList<HashMap<String, Object>>();

	// 过滤是否是失效
	public void filterSX(String is_del) {
		for (int i = 0; i < mInfos.size(); i++) {
			if ("1".equals(mInfos.get(i).get("is_del").toString())) {
				mIsSxInfos.addLast(mInfos.get(i));
			}
		}
		if ("1".equals(is_del)) {
			mIsUnSxInfos = mInfos;
			mInfos = mIsSxInfos;
		} else {
			mInfos = mIsUnSxInfos;
		}

		this.notifyDataSetChanged();
	}

	public void clearData() {
		mInfos.clear();
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
