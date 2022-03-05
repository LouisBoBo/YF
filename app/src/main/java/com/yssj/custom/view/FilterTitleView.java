package com.yssj.custom.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
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
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class FilterTitleView extends LinearLayout implements OnClickListener {

	private LinearLayout mGroup;

	private Context context;

	private ImageMemoryCache memoryCache;
	private ImageFileCache fileCache;

	private HorizontalScrollView myScrollView;

	private List<String> urlList;

	private List<HashMap<String, String>> list;
	
	public static int index=0;
	

	public FilterTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		initView(context);
	}
	
	private void initView(Context context) {
		LayoutInflater.from(context).inflate(R.layout.my_horizontal_view, this);
		mGroup = (LinearLayout) findViewById(R.id.hor_group);
		myScrollView = (HorizontalScrollView) findViewById(R.id.my_h_scroll);
		urlList = new ArrayList<String>();
	}
	
	public void setPosition(int position){
		onClick(mGroup.getChildAt(position));
	}
	
	
	public void setData(List<HashMap<String, String>> list) {
		this.list = list;
		mGroup.removeAllViews();
		memoryCache = new ImageMemoryCache(context);
		fileCache = new ImageFileCache();
		for (int i = 0; i < list.size(); i++) {
			TextView rb = new TextView(context);
			rb.setId(i);
			rb.setBackgroundColor(Color.WHITE);
			getIcon(list.get(i).get("icon").split(",")[0], rb);
			rb.setTextSize(16);
			rb.setTextColor(Color.parseColor("#989898"));
			rb.setPadding(20, 0, 0, 0);
			rb.setText(list.get(i).get("sort_name"));
			rb.setLayoutParams(new LayoutParams(MyShopFragment.width / 4,
					LayoutParams.MATCH_PARENT));
			rb.setGravity(Gravity.CENTER);
			rb.setOnClickListener(this);
			mGroup.addView(rb);
		}
		TextView rb = (TextView) mGroup.getChildAt(index);
		getIcon(list.get(index).get("icon").split(",")[1], rb);
		rb.setTextColor(Color.parseColor("#222222"));
	}

	private class msgObject {
		public String url;
		public Bitmap bm;
		public TextView rb;
	}

	private void getIcon(String url, TextView rb){
		Drawable d = getResources().getDrawable(YJApplication.mapIcon.get(url));
		rb.setCompoundDrawablesWithIntrinsicBounds(d, null, null,null);
	}
	
	final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1) {
				msgObject mMsg = (msgObject) msg.obj;
				Drawable d = new BitmapDrawable(mMsg.bm);
				mMsg.rb.setCompoundDrawablesWithIntrinsicBounds(d, null, null,
						null);
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
				rb.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(
						result), null, null, null);
			}
		} else {
			rb.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(
					result), null, null, null);
		}
	}
	
	
	
	@Override
	public void onClick(View arg0) {
		if(index==arg0.getId()){
			return;
		}
		index=arg0.getId();
		TextView rb = (TextView) mGroup.getChildAt(arg0.getId());
		getIcon(list.get(arg0.getId()).get("icon").split(",")[1], rb);
		rb.setTextColor(Color.parseColor("#222222"));
		myScrollView
				.smoothScrollTo(
						(arg0.getId() > 1 ? ((TextView) mGroup.getChildAt(arg0
								.getId())).getLeft() : 0)
								- ((TextView) mGroup.getChildAt(2)).getLeft(),
						0);

		for (int i = 0; i < mGroup.getChildCount(); i++) {
			if (i != arg0.getId()) {
				TextView rbs = (TextView) mGroup.getChildAt(i);
				getIcon(((List<HashMap<String, String>>) list).get(i)
										.get("icon").split(",")[0], rbs);
				rbs.setTextColor(Color.parseColor("#989898"));
			}
		}
	}
}
