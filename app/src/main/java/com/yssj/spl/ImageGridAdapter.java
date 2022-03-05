package com.yssj.spl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.yssj.activity.R;
import com.yssj.utils.PicassoUtils;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageGridAdapter extends BaseAdapter {
	private static final String TAG = "ImageGridAdapter";
	private static final boolean DEBUG = true;
	private LayoutInflater mLayoutInflater;
	private Context context;
	private List<HashMap<String, Object>> listData;

	public ImageGridAdapter(Context context, List<HashMap<String, Object>> listData) {
		this.listData = listData;
		this.context = context;
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		mLayoutInflater = LayoutInflater.from(context);

	}

	public int getCount() {
		return listData.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (DEBUG)
			Log.i(TAG, "position = " + position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_image, null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		final HashMap<String, Object> datas = listData.get(position);
		final String theme_type = (String) datas.get("theme_type");
		final String user_id = (String) datas.get("user_id");// 发帖用户ID
		String pics = (String) datas.get("pics");
		String picImg[];
		if (TextUtils.isEmpty(pics)) {
			picImg = new String[0];
		} else {
			picImg = pics.split(",");
		}

		// 填充图片
		if ("1".equals(theme_type)) {// 精选推荐
			List<HashMap<String, Object>> shop_list = (List<HashMap<String, Object>>) datas.get("shop_list");
			if (shop_list.size() > 0) {

				String shop_code = shop_list.get(0).get("shop_code").toString();
				String url = shop_code.substring(1, 4) + File.separator + shop_code + File.separator
						+ shop_list.get(0).get("def_pic").toString();
				PicassoUtils.initImage(context, url + "!450", holder.imageView);
			}

		} else {
			if(picImg.length >0){
				PicassoUtils.initImage(context, "myq/theme" + "/" + user_id + "/" + picImg[0].split(":")[0] + "!450",
						holder.imageView);
			}
			
		
		}

		return convertView;
	}

	static class ViewHolder {
		ImageView imageView;
	}
}
