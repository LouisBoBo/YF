package com.yssj.ui.adpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.ui.activity.classfication.ManufactureActivity;
import com.yssj.ui.activity.main.MoreSubjectActivity;
import com.yssj.ui.activity.shopdetails.SpecialTopicDeatilsActivity;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
//import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MoreSubAdapter extends BaseAdapter {
	private List<HashMap<String, String>> mInfos;
	// private ImageLoadingListener animateFirstListener = new
	// AnimateFirstDisplayListener();
	private int imageWidth;
	private int picHeight;
	private Context context;
	private LayoutInflater mInflater;
	private boolean isforcelookMatch;

	public static int width;
	private boolean isManufacture;

	public MoreSubAdapter(Context context, boolean isforcelookMatch, boolean isManufacture) {
		mInfos = new ArrayList<HashMap<String, String>>();
		this.context = context;
		this.isforcelookMatch = isforcelookMatch;
		this.isManufacture = isManufacture;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_morsub, null);
			holder.subject_main_image_iv = (ImageView) convertView.findViewById(R.id.subject_main_image_iv);
			holder.subject_main_tv1 = (TextView) convertView.findViewById(R.id.subject_main_tv1);
			holder.subject_main_tv2 = (TextView) convertView.findViewById(R.id.subject_main_tv2);
			holder.rl_end = (RelativeLayout) convertView.findViewById(R.id.rl_end);
			holder.subject_main_image_rl = (RelativeLayout) convertView.findViewById(R.id.subject_main_image_rl);
			holder.v1 = (View) convertView.findViewById(R.id.v1);
			holder.v2 = (View) convertView.findViewById(R.id.v2);
			holder.end = (TextView) convertView.findViewById(R.id.end);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 填充

		if (isManufacture) {
			holder.subject_main_tv1.setVisibility(View.GONE);
			holder.subject_main_tv2.setVisibility(View.GONE);
			holder.v1.setBackgroundColor(Color.parseColor("#ff3f8b"));
			holder.v2.setBackgroundColor(Color.parseColor("#ff3f8b"));
			holder.end.setTextColor(Color.parseColor("#ff3f8b"));
			holder.end.setText("更多优质供应商正在洽谈中");
		} else {
			holder.subject_main_tv1.setText(mInfos.get(position).get("subName").toString());
			holder.subject_main_tv2.setText(mInfos.get(position).get("subName2").toString());

		}

		// 设置图片的宽高比 加载图片
		ViewGroup.LayoutParams lp = holder.subject_main_image_iv.getLayoutParams();
		lp.width = MoreSubjectActivity.width;
		lp.height = LayoutParams.WRAP_CONTENT;
		holder.subject_main_image_iv.setLayoutParams(lp);
		holder.subject_main_image_iv.setMaxWidth(MoreSubjectActivity.width);
		holder.subject_main_image_iv.setMaxHeight(MoreSubjectActivity.width * 2 / 3); // 宽高比3:2

		if (isManufacture) {
			 PicassoUtils.initImage(context,
			 mInfos.get(position).get("pic").toString(),
			 holder.subject_main_image_iv);
//			SetImageLoader.initImageLoader3(context, holder.subject_main_image_iv,
//					mInfos.get(position).get("pic").toString(), "");

		} else {
//			SetImageLoader.initImageLoader(context, holder.subject_main_image_iv,
//					mInfos.get(position).get("subPIC").toString(), "");
			
			PicassoUtils.initImage(context, mInfos.get(position).get("subPIC").toString(), holder.subject_main_image_iv);
		}

		if (position == mInfos.size() - 1) { // 最后一个条目
			if (MoreSubjectActivity.isEnd) {
				holder.rl_end.setVisibility(View.VISIBLE);
			} else {
				holder.rl_end.setVisibility(View.GONE);
			}
			holder.subject_main_image_rl.setPadding(0, 0, 0, 0);
		} else {
			holder.subject_main_image_rl.setPadding(0, 0, 0, DP2SPUtil.dip2px(context, 4));
			holder.rl_end.setVisibility(View.GONE);
		}

		if (isManufacture) {
			if (position == mInfos.size() - 1) { // 最后一个条目
				holder.rl_end.setVisibility(View.VISIBLE);
			}
		}
		//
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent;
				if (isManufacture) {
					// 跳至品牌详情
					intent = new Intent(context, ManufactureActivity.class);
					intent.putExtra("supple_data", mInfos.get(position));
					context.startActivity(intent);
					((FragmentActivity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
				} else {
					// 跳至专题详情
					intent = new Intent(context, SpecialTopicDeatilsActivity.class);
					intent.putExtra("collocation_code", mInfos.get(position).get("subID").toString());
					intent.putExtra("collocation_pic", mInfos.get(position).get("subPIC").toString());
					if (SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete) {// 强制浏览个数并且是没有完成的任务
						intent.putExtra("isforcelookMatch", isforcelookMatch);
					}
					context.startActivity(intent);
					((FragmentActivity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
				}

			}
		});

		return convertView;
	}

	class ViewHolder {
		ImageView subject_main_image_iv;
		TextView subject_main_tv2;
		TextView subject_main_tv1;
		RelativeLayout rl_end;
		RelativeLayout subject_main_image_rl;
		View v1;
		View v2;
		TextView end;
	}

	@Override
	public int getCount() {
		// ToastUtil.showShortText(context, mInfos + "");
		return mInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public void addItemLast(List<HashMap<String, String>> datas) {

		mInfos.addAll(datas);
		this.notifyDataSetChanged();
	}

	public void addItemTop(List<HashMap<String, String>> datas) {
		mInfos.clear();
		mInfos.addAll(datas);
		this.notifyDataSetChanged();
	}

	public void clearData() {
		mInfos.clear();
		this.notifyDataSetChanged();
	}

	public void setData(List<HashMap<String, String>> result) {
		clearData();
		mInfos.addAll(result);
		// LogYiFu.e("转盘", mInfos + "");
		// LogYiFu.e("转盘", mInfos.get(0).get("subPIC").toString());
		this.notifyDataSetChanged();
	}
}