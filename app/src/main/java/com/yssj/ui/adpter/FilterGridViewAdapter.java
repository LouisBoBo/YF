package com.yssj.ui.adpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

//import com.squareup.picasso.Picasso;
import com.yssj.activity.R;
import com.yssj.entity.MyToggleButton;
import com.yssj.ui.adpter.MyLikeStyleAdapter.ViewHolder;
//import com.yssj.ui.activity.main.FilterConditionActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.PicassoUtils;
//import com.yssj.utils.CropSquareTransformation;
//import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;

public class FilterGridViewAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<HashMap<String, String>> mList;
	private boolean isDuoxuan;
	private CheckCallback checkCallback;

	private CheckXiaofeixiguanCallback checkxiaofeixiguancallback;
	private CheckStyleCallback checkstylecallback;
	private CheckAgeCallback checkagecallback;
	private boolean isStyle;
	private List<ToggleButton> buttonList = new ArrayList<ToggleButton>();

	public FilterGridViewAdapter(Context mContext, ArrayList<HashMap<String, String>> mList, boolean isDuoxuan,
			boolean isStyle) {
		super();
		this.mContext = mContext;
		this.mList = mList;
		this.isDuoxuan = isDuoxuan;
		this.isStyle = isStyle;
	}

	public ArrayList<HashMap<String, String>> getData() {
		return mList;
	}

	public void setListener(Activity mActivity) {
		this.checkCallback = (CheckCallback) mActivity;
	}

	public interface CheckCallback {
		void onCheckCallback(int positon, boolean isChecked, HashMap<String, String> map,
				FilterGridViewAdapter mAdapter, boolean isDuoxuan);
	}

	/**
	 * 另外写三个接口用于监听用户分别选中的每个类---提交时判断是否有选项没有勾选
	 */

	// 消费习惯--定价
	public void setXiaofeixiguanListener(Activity mActivity) {
		this.checkxiaofeixiguancallback = (CheckXiaofeixiguanCallback) mActivity;
	}

	public interface CheckXiaofeixiguanCallback {
		void onCheckXFCallback(int positon, boolean isChecked, HashMap<String, String> map,
				FilterGridViewAdapter mAdapter, boolean isDuoxuan);
	}

	// 风格
	public void setStyleListener(Activity mActivity) {
		this.checkstylecallback = (CheckStyleCallback) mActivity;
	}

	public interface CheckStyleCallback {
		void onCheckFGCallback(int positon, boolean isChecked, HashMap<String, String> map,
				FilterGridViewAdapter mAdapter, boolean isDuoxuan);
	}

	// 年龄段
	public void setAgeListener(Activity mActivity) {
		this.checkagecallback = (CheckAgeCallback) mActivity;
	}

	public interface CheckAgeCallback {
		void onCheckNLCallback(int positon, boolean isChecked, HashMap<String, String> map,
				FilterGridViewAdapter mAdapter, boolean isDuoxuan);
	}

	@Override
	public int getCount() {
		if (mList == null) {
			return 0;
		} else {
			return this.mList.size();
		}
	}

	@Override
	public Object getItem(int position) {
		if (mList == null) {
			return null;
		} else {
			return this.mList.get(position);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (!isStyle) {
			final ViewHolder holder;
			if (convertView == null) {

				holder = new ViewHolder();
				convertView = LayoutInflater.from(this.mContext).inflate(R.layout.filter_gridview_item, null, false);
				holder.button = (MyToggleButton) convertView.findViewById(R.id.gridview_item_button);
				buttonList.add(holder.button);
				holder.button.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
						checkCallback.onCheckCallback(position, arg1, mList.get(position), FilterGridViewAdapter.this,
								isDuoxuan);

						if (checkagecallback != null) {
							checkagecallback.onCheckNLCallback(position, arg1, mList.get(position),
									FilterGridViewAdapter.this, isDuoxuan);
						}

						if (checkxiaofeixiguancallback != null) {

							checkxiaofeixiguancallback.onCheckXFCallback(position, arg1, mList.get(position),
									FilterGridViewAdapter.this, isDuoxuan);
						}

					}
				});
				// holder.button.setEllipsize(TruncateAt.MARQUEE);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (this.mList != null) {
				HashMap<String, String> hashMap = this.mList.get(position);
				if (holder.button != null) {
					holder.button.setText(hashMap.get("attr_name").toString());
					holder.button.setTextOff(hashMap.get("attr_name").toString());
					holder.button.setTextOn(hashMap.get("attr_name").toString());
					if (hashMap.get("isChecked").equals("1")) {
						holder.button.setChecked(true);
					} else {
						holder.button.setChecked(false);
					}

				}
			}
		} else { // 风格------------布局跟其他的不同
			ViewHolderStyle holder = null;
			if (convertView == null) {
				holder = new ViewHolderStyle();
				convertView = LayoutInflater.from(this.mContext).inflate(R.layout.my_like_style_item2, null);
				holder.iv = (ImageView) convertView.findViewById(R.id.iv);
				holder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);

				holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
						checkCallback.onCheckCallback(position, arg1, mList.get(position), FilterGridViewAdapter.this,
								isDuoxuan);

						if (checkstylecallback != null) {
							checkstylecallback.onCheckFGCallback(position, arg1, mList.get(position),
									FilterGridViewAdapter.this, isDuoxuan);
						}

					}
				});

				convertView.setTag(holder);
			} else {
				holder = (ViewHolderStyle) convertView.getTag();
			}

			if (this.mList != null) {
				HashMap<String, String> hashMap = this.mList.get(position);
//				PicassoUtils.initImage(mContext, hashMap.get("like_pic"), holder.iv);
				
//				SetImageLoader.initImageLoader3(mContext, holder.iv, hashMap.get("like_pic"), "");
				PicassoUtils.initImage(mContext,  hashMap.get("like_pic"), holder.iv);
//				Picasso.with(mContext).load("").toString()
				if (holder.cb != null) {
					if (hashMap.get("isChecked").equals("1")) {
						holder.cb.setChecked(true);
					} else {
						holder.cb.setChecked(false);
					}

				}
			}

		}

		return convertView;
	}

	private class ViewHolder {
		MyToggleButton button;
	}

	public static class ViewHolderStyle {
		ImageView iv;
		public CheckBox cb;
	}
}
