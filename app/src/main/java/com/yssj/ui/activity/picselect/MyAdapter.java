package com.yssj.ui.activity.picselect;

import java.util.LinkedList;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.utils.TakePhotoUtil;
import com.yssj.utils.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MyAdapter extends CommonAdapter<String> {

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public List<String> mSelectedImage = new LinkedList<String>();

	/**
	 * 文件夹路径
	 */
	private List<String> mDirPath;
	private int availableSize;
	private Context mContext;
	private SelectPicListener listener;
	private boolean headFlag;
	private boolean mIssueSweetFriends;

	public MyAdapter(Context context, List<String> mDatas, int itemLayoutId, List<String> dirPath, int availableSize,
			SelectPicListener listener, List<String> picPath, boolean headFlag, boolean mIssueSweetFriends) {
		super(context, mDatas, itemLayoutId);
		mContext = context;
		this.mDirPath = dirPath;
		this.availableSize = availableSize;
		this.listener = listener;
		this.headFlag = headFlag;
		this.mIssueSweetFriends = mIssueSweetFriends;
		if (picPath != null) {
			mSelectedImage.addAll(picPath);
		}
	}

	@Override
	public void convert(final ViewHolder helper, final String item, int position) {
		// 设置no_pic
		helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
		// 设置no_selected
		helper.setImageResource(R.id.id_item_select, R.drawable.icon_xuanze_disabled);
		// 设置图片
		// helper.setImageByUrl(R.id.id_item_image, mDirPath.get(position) + "/"
		// + item);
		helper.setImageByUrl(R.id.id_item_image, item);

		final ImageView mImageView = helper.getView(R.id.id_item_image);
		final ImageView mSelect = helper.getView(R.id.id_item_select);
		if (headFlag || mIssueSweetFriends) {
			mSelect.setVisibility(View.GONE);
		}
		final int a = position;
		mImageView.setColorFilter(null);
		// 设置ImageView的点击事件
		mImageView.setOnClickListener(new OnClickListener() {
			// 选择，则将图片变暗，反之则反之
			@Override
			public void onClick(View v) {
				if (headFlag) {
					listener.clickPicHead(item, mSelect);
					return;
				}
				if (mIssueSweetFriends) {
					listener.clickPicIssue(item, mSelect);
				}
				// 已经选择过该图片
				// if (mSelectedImage.contains(mDirPath.get(a) + "/" + item)) {
				// mSelectedImage.remove(mDirPath.get(a) + "/" + item);
				// mSelect.setImageResource(R.drawable.picture_unselected);
				// mImageView.setColorFilter(null);
				// listener.clickPic(mSelectedImage.size());
				// } else
				// // 未选择该图片
				// {
				// if (mSelectedImage.size() + 1 > availableSize) {
				// ToastUtil.showShortText(mContext, "最多选择九张图片");
				// return;
				// }
				// mSelectedImage.add(mDirPath.get(a) + "/" + item);
				// mSelect.setImageResource(R.drawable.pictures_selected);
				// mImageView.setColorFilter(Color.parseColor("#77000000"));
				// listener.clickPic(mSelectedImage.size());
				// }
				if (mSelectedImage.contains(item)) {
					mSelectedImage.remove(item);
					mSelect.setImageResource(R.drawable.icon_xuanze_disabled);
					mImageView.setColorFilter(null);
					listener.clickPic(mSelectedImage.size());
				} else
				// 未选择该图片
				{
					if (mSelectedImage.size() + 1 > availableSize) {
						ToastUtil.showShortText(mContext, "最多只能选择9张图片哦~");
						return;
					}
					mSelectedImage.add(item);
					mSelect.setImageResource(R.drawable.icon_celect);
					if (!mIssueSweetFriends) {
						mImageView.setColorFilter(Color.parseColor("#77000000"));
					}
					listener.clickPic(mSelectedImage.size());
				}

			}
		});

		/**
		 * 已经选择过的图片，显示出选择过的效果
		 */
		if (mSelectedImage.contains(item)) {
			mSelect.setImageResource(R.drawable.icon_celect);
			if (!mIssueSweetFriends) {
				mImageView.setColorFilter(Color.parseColor("#77000000"));
			}
		}

	}

	public List<String> getPicPath() {
		return mSelectedImage;
	}

	interface SelectPicListener {
		void clickPic(int a);// a已经选择的数量

		void clickPicHead(String picPath, ImageView iv);

		void clickPicIssue(String picPath, ImageView iv);
	}
}
