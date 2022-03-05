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

public class JingxuanTuijianMySelfGridViewAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<HashMap<String, String>> mList;
	private boolean isDuoxuan;
	private CheckCallbackChoice checkCallback;
	private CheckChoiceCallback checkstylecallback;
	public JingxuanTuijianMySelfGridViewAdapter(Context mContext, ArrayList<HashMap<String, String>> mList,
			boolean isDuoxuan, boolean isStyle) {
		super();
		this.mContext = mContext;
		this.mList = mList;
		this.isDuoxuan = isDuoxuan;
	}

	public ArrayList<HashMap<String, String>> getData() {
		return mList;
	}

	public void setListener(Activity mActivity) {
		this.checkCallback = (CheckCallbackChoice) mActivity;
	}

	public interface CheckCallbackChoice {
		void onCheckCallback(int positon, boolean isChecked, HashMap<String, String> map,
				JingxuanTuijianMySelfGridViewAdapter mAdapter, boolean isDuoxuan,CheckBox box);
	}

	// 风格
	public void setStyleListener(Activity mActivity) {
		this.checkstylecallback = (CheckChoiceCallback) mActivity;
	}

	public interface CheckChoiceCallback {
		void onCheckFGCallback(int positon, boolean isChecked, HashMap<String, String> map,
				JingxuanTuijianMySelfGridViewAdapter mAdapter, boolean isDuoxuan,CheckBox box);
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

		final ViewHolderStyle holder ;
		if (convertView == null) {
			holder = new ViewHolderStyle();
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.my_like_style_item, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.iv);
			holder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
			holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					checkCallback.onCheckCallback(position, arg1, mList.get(position),
							JingxuanTuijianMySelfGridViewAdapter.this, isDuoxuan,holder.cb);

					if (checkstylecallback != null) {
						checkstylecallback.onCheckFGCallback(position, arg1, mList.get(position),
								JingxuanTuijianMySelfGridViewAdapter.this, isDuoxuan,holder.cb);
					}

				}
			});

			convertView.setTag(holder);
		} else {
			holder = (ViewHolderStyle) convertView.getTag();
		}

		if (this.mList != null) {
			HashMap<String, String> hashMap = this.mList.get(position);
			
			String piccc = hashMap.get("show_pic").split(",")[0];
			piccc = hashMap.get("shop_code").substring(1, 4)+"/"+hashMap.get("shop_code").toString()+"/"+piccc;
			PicassoUtils.initImage(mContext, piccc+"!382", holder.iv);
//			SetImageLoader.initImageLoader3(mContext, holder.iv, piccc, "");
			if (holder.cb != null) {
				if (hashMap.get("isChecked").equals("1")) {
					holder.cb.setChecked(true);
				} else {
					holder.cb.setChecked(false);
				}

			}
		}

		return convertView;
	}

	public static class ViewHolderStyle {
		ImageView iv;
		public CheckBox cb;
	}
}
