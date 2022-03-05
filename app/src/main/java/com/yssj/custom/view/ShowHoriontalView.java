package com.yssj.custom.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.fragment.MyShopFragment;
import com.yssj.utils.ImageFileCache;
import com.yssj.utils.ImageGetFromHttp;
import com.yssj.utils.ImageMemoryCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 商品详情后接列表
 * 
 * @author lbp
 * 
 */
public class ShowHoriontalView extends LinearLayout implements OnClickListener {
	private LinearLayout mGroup;

	private Context context;

	private boolean isRefresh = false;

	private onClickLintener onClickLintener;

	// 下载缓存 将用
	private ImageMemoryCache memoryCache;
	private ImageFileCache fileCache;
	// private ImageView iv_nav_indicator;

	private List list;

	private static int index = 0;

	private HorizontalScrollView myScrollView;

	private List<String> urlList;

	private Handler touchHandler;

	// private mRunnable run;

	public void setOnClickLintener(onClickLintener onClickLintener) {
		this.onClickLintener = onClickLintener;
	}

	public void setRefresh(boolean isRefresh) {
		this.isRefresh = isRefresh;
	}

	public ShowHoriontalView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView(context);
	}

	private void getIcon(String url, TextView rb) {
		if (null != url && null != YJApplication.mapIcon.get(url)) {
			Drawable d = context.getResources().getDrawable(YJApplication.mapIcon.get(url));
			rb.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
		}
	}

	private void initView(Context context) {
		LayoutInflater.from(context).inflate(R.layout.my_horizontal_view, this);
		mGroup = (LinearLayout) findViewById(R.id.hor_group);
		// iv_nav_indicator = (ImageView) findViewById(R.id.iv_nav_indicator);
		isRefresh = true;
		myScrollView = (HorizontalScrollView) findViewById(R.id.my_h_scroll);
		urlList = new ArrayList<String>();
		width = context.getResources().getDisplayMetrics().widthPixels;
	}

	private int width;

	// private class mRunnable implements Runnable {
	//
	// @Override
	// public void run() {
	// if (ShopDetailsActivity.mLeftX != myScrollView.getScrollX()) {
	// ShopDetailsActivity.mLeftX = myScrollView.getScrollX();
	// touchHandler.postDelayed(run, 100);
	// } else {
	// touchHandler.removeCallbacks(run);
	// }
	// }
	// }

	public void setList(List list) {
		// myScrollView.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View arg0, MotionEvent arg1) {
		// if (arg1.getAction() == MotionEvent.ACTION_UP) {// 抬起手指
		// if (touchHandler == null) {
		// touchHandler = new Handler();
		// }
		// if (run == null) {
		// run = new mRunnable();
		// }
		// touchHandler.postDelayed(run, 100);
		// }
		// return false;
		// }
		// });
		if (isRefresh == false) {
			// myScrollView.setScrollX(ShopDetailsActivity.mLeftX);

			for (int i = 0; i < mGroup.getChildCount(); i++) {
				if (i != index) {
					TextView rbs = (TextView) mGroup.getChildAt(i);
					getIcon(((List<HashMap<String, String>>) list).get(i).get("icon"), rbs);
					rbs.setTextColor(Color.parseColor("#989898"));
				}
			}
			TextView rb = (TextView) mGroup.getChildAt(index);
			getIcon(((List<HashMap<String, String>>) list).get(index).get("icon").split(",")[1], rb);
			rb.setTextColor(Color.parseColor("#222222"));
			return;
		}
		this.list = list;
		isRefresh = false;
		mGroup.removeAllViews();
		memoryCache = new ImageMemoryCache(context);
		fileCache = new ImageFileCache();
		for (int i = 0; i < ((List<HashMap<String, String>>) list).size(); i++) {

			TextView rb = new TextView(context);
			rb.setId(i);
			rb.setBackgroundColor(Color.WHITE);
			// setDrawable(rb, listTitle.get(i).get("icon").split(",")[0]);
			// rb.setText(tabTitle[i]);
			getIcon(((List<HashMap<String, String>>) list).get(i).get("icon").split(",")[0], rb);
			rb.setTextSize(16);
			rb.setTextColor(Color.parseColor("#989898"));
			rb.setPadding(20, 0, 0, 0);
			rb.setText(((List<HashMap<String, String>>) list).get(i).get("sort_name"));
			rb.setLayoutParams(new LayoutParams(width / 4, LayoutParams.MATCH_PARENT));
			rb.setGravity(Gravity.CENTER);
			rb.setOnClickListener(this);
			mGroup.addView(rb);
		}
//		TextView rb = (TextView) mGroup.getChildAt(index);
//		getIcon(((List<HashMap<String, String>>) list).get(index).get("icon").split(",")[1], rb);
//		rb.setTextColor(Color.parseColor("#222222"));
		// this.index = ShopDetailsActivity.mIndex;
		// myScrollView.setScrollX(ShopDetailsActivity.mLeftX);
	}

	public interface onClickLintener {
		void myOnClick(View v);
	}

	private boolean isScroll = false;

	public void setIndex(final int index) {
		// if (myScrollView.getScrollX() != ShopDetailsActivity.mLeftX) {
		// myScrollView.setScrollX(ShopDetailsActivity.mLeftX);
		// }
		if (isScroll == false) {
			myScrollView.postDelayed(new Runnable() {

				@Override
				public void run() {
					myScrollView.smoothScrollTo((index > 1 ? width / 4 * index : 0) - width / 4 * 2, 0);
					isScroll = true;
				}
			}, 800);
		} else {
			myScrollView.smoothScrollTo((index > 1 ? width / 4 * index : 0) - width / 4 * 2, 0);
		}

		TextView rb = (TextView) mGroup.getChildAt(index);
		for (int i = 0; i < mGroup.getChildCount(); i++) {
			if (i != index) {
				TextView rbs = (TextView) mGroup.getChildAt(i);
				getIcon(((List<HashMap<String, String>>) list).get(i).get("icon").split(",")[0], rbs);
				rbs.setTextColor(Color.parseColor("#989898"));
			}
		}
		if (index > 0) {
			if (((List<HashMap<String, String>>) list).get(index).get("icon").split(",").length <= 1) {
				return;
			}else{
				getIcon(((List<HashMap<String, String>>) list).get(index).get("icon").split(",")[1], rb);

			}
		}
		if (rb != null) {
			rb.setTextColor(Color.parseColor("#222222"));

		}
	}

	@Override
	public void onClick(View arg0) {
		if (this.getVisibility() == View.INVISIBLE) {
			return;
		}
		if (onClickLintener != null) {
			onClickLintener.myOnClick(arg0);
		}
		// ShopDetailsActivity.mIndex = arg0.getId();
		TextView rb = (TextView) mGroup.getChildAt(arg0.getId());

		if (((List<HashMap<String, String>>) list).get(arg0.getId()).get("icon").split(",").length <= 1) {
			return;
		} else {
			getIcon(((List<HashMap<String, String>>) list).get(arg0.getId()).get("icon").split(",")[1], rb);

		}

		rb.setTextColor(Color.parseColor("#222222"));
		// TranslateAnimation animation = new TranslateAnimation(
		// currentIndicatorLeft,
		// ((TextView) mGroup
		// .getChildAt(arg0.getId())).getLeft(),
		// 0f, 0f);
		// animation.setInterpolator(new LinearInterpolator());
		// animation.setDuration(100);
		// animation.setFillAfter(true);
		//
		// currentIndicatorLeft = ((TextView) mGroup
		// .getChildAt(arg0.getId())).getLeft();
		//
		myScrollView.smoothScrollTo((arg0.getId() > 1 ? ((TextView) mGroup.getChildAt(arg0.getId())).getLeft() : 0)
				- ((TextView) mGroup.getChildAt(2)).getLeft(), 0);
		// ShopDetailsActivity.mLeftX = (arg0.getId() > 1 ? ((TextView) mGroup
		// .getChildAt(arg0.getId())).getLeft() : 0)
		// - ((TextView) mGroup.getChildAt(2)).getLeft();

		for (int i = 0; i < mGroup.getChildCount(); i++) {
			if (i != arg0.getId()) {
				TextView rbs = (TextView) mGroup.getChildAt(i);
				getIcon(((List<HashMap<String, String>>) list).get(i).get("icon").split(",")[0], rbs);
				rbs.setTextColor(Color.parseColor("#989898"));
			}
		}

	}

	private class msgObject {
		public String url;
		public Bitmap bm;
		public TextView rb;
	}

	final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1) {
				msgObject mMsg = (msgObject) msg.obj;
				Drawable d = new BitmapDrawable(mMsg.bm);
				mMsg.rb.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
				fileCache.saveBitmap(mMsg.bm, mMsg.url);
				memoryCache.addBitmapToCache(mMsg.url, mMsg.bm);
			}
		}
	};

	public void getBitmap(final String url, final TextView rb) {

		// 从内存缓存中获取图片
		Bitmap result = memoryCache.getBitmapFromCache(url);
		if (result == null) {
			// 文件缓存中获取
			result = fileCache.getImage(url);
			if (result == null) {
				if (urlList.contains(url)) {
					return;
				}
				urlList.add(url);
				// 从网络获取
				new Thread(new Runnable() {

					@Override
					public void run() {
						Bitmap result = ImageGetFromHttp.downloadBitmap(url);
						Message msg = new Message();
						msgObject mMsg = new msgObject();
						mMsg.bm = result;
						mMsg.rb = rb;
						mMsg.url = url;
						msg.what = 1;
						msg.obj = mMsg;
						mHandler.sendMessage(msg);
					}
				}).start();

			} else {
				// 添加到内存缓存
				memoryCache.addBitmapToCache(url, result);
				rb.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(result), null, null, null);
			}
		} else {
			rb.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(result), null, null, null);
		}
	}

}
