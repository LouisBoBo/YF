package com.yssj.ui.activity.picselect;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.yssj.activity.R;
import com.yssj.ui.activity.picselect.ImageLoader;
import com.yssj.ui.activity.picselect.ImageLoader.Type;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

public class ImagePublishAdapter extends BaseAdapter {
	private List<String> mDataList = new ArrayList<String>();
	private Context mContext;

	public ImagePublishAdapter(Context context, List<String> dataList) {
		this.mContext = context;
		this.mDataList = dataList;
	}

	public int getCount() {
		// 多返回一个用于展示添加图标
		if (mDataList == null) {
			return 1;
		} else if (mDataList.size() == 9) {
			return 9;
		} else {
			return mDataList.size() + 1;
		}
	}

	public Object getItem(int position) {
		if (mDataList != null && mDataList.size() == 9) {
			return mDataList.get(position);
		}

		else if (mDataList == null || position - 1 < 0 || position > mDataList.size()) {
			return null;
		} else {
			return mDataList.get(position - 1);
		}
	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("ViewHolder")
	public View getView(final int position, View convertView, ViewGroup parent) {
		// 所有Item展示不满一页，就不进行ViewHolder重用了，避免了一个拍照以后添加图片按钮被覆盖的奇怪问题
		convertView = View.inflate(mContext, R.layout.item_publish, null);
		ImageView imageIv = (ImageView) convertView.findViewById(R.id.item_grid_image);
		TextView tvDelete = (TextView) convertView.findViewById(R.id.item_grid_delete);
		if (mDataList == null || mDataList.size() == 0) {
			tvDelete.setBackgroundDrawable(null);
		} else if (position == 0) {
			tvDelete.setBackgroundDrawable(null);
		} else if (mDataList.size() < 9 && position == mDataList.size()) {
			tvDelete.setBackgroundDrawable(null);
		} else {
			tvDelete.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.icon_img_del));
		}
		tvDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mDataList.size() == 1) {
					removeImgs();
				} else {
					removeImg(position);
					// pager.removeAllViews();
					// adapter.removeView(currentPosition);
					// adapter.notifyDataSetChanged();
				}

			}
		});
		if (isShowAddItem(position)) {
			imageIv.setImageResource(R.drawable.btn_add_pic);
			// imageIv.setBackgroundResource(R.color.bg_gray);
		} else {
			// final ImageItem item = mDataList.get(position);
			// ImageDisplayer.getInstance(mContext).displayBmp(imageIv, "",
			// mDataList.get(position));
//			ImageLoader.getInstance(3, Type.LIFO).loadImage(mDataList.get(position), imageIv);
			File file = new File(mDataList.get(position));
//		Picasso.with(mContext).load(file).into(mIvPic);
			Picasso.get()
					.load(file)
					.memoryPolicy(NO_CACHE, NO_STORE)
					.placeholder(R.drawable.image_default)
					.error(R.drawable.image_default)
					.into(imageIv);
		}

		return convertView;
	}

	private void removeImgs() {
		mDataList.clear();
		notifyDataSetChanged();
	}

	private void removeImg(int location) {
		if (location + 1 <= mDataList.size()) {
			mDataList.remove(location);
			notifyDataSetChanged();
		}
	}

	private boolean isShowAddItem(int position) {
		int size = mDataList == null ? 0 : mDataList.size();
		return position == size;
	}

}
