package com.yssj.ui.adpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
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

import com.yssj.activity.R;
import com.yssj.entity.MyToggleButton;
import com.yssj.ui.adpter.MyLikeStyleAdapter.ViewHolder;
//import com.yssj.ui.activity.main.FilterConditionActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;

public class ChoicenessGridViewAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<HashMap<String, String>> mList;
	private CheckCallback checkCallback;

	public ChoicenessGridViewAdapter(Context mContext, ArrayList<HashMap<String, String>> mList) {
		super();
		this.mContext = mContext;
		this.mList = mList;

	}

	public ArrayList<HashMap<String, String>> getData() {
		return mList;
	}

	public void setListener(FragmentActivity mActivity) {
		this.checkCallback = (CheckCallback) mActivity;
	}

	public interface CheckCallback {
		void onCheckCallback(int positon, HashMap<String, String> map, ChoicenessGridViewAdapter mAdapter);
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
		ViewHolderStyle holder = null;
		if (convertView == null) {
			holder = new ViewHolderStyle();
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_choiceness, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.iv);
			holder.iv_close = (ImageView) convertView.findViewById(R.id.iv_close);

//			holder.iv_close.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					checkCallback.onCheckCallback(position, mList.get(position), ChoicenessGridViewAdapter.this);
//				}
//			});

			convertView.setTag(holder);
		} else {
			holder = (ViewHolderStyle) convertView.getTag();
		}

		HashMap<String, String> hashMap = this.mList.get(position);
//		PicassoUtils.initImage(mContext, hashMap.get("like_pic"), holder.iv);
		
//		SetImageLoader.initImageLoader3(mContext, holder.iv, hashMap.get("like_pic"), "");
		
		PicassoUtils.initImage(mContext, hashMap.get("like_pic"), holder.iv);

		return convertView;
	}

	public static class ViewHolderStyle {
		ImageView iv;
		public ImageView iv_close;
	}
}
