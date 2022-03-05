package com.yssj.ui.adpter;

import java.util.HashMap;

import com.yssj.activity.R;
import com.yssj.ui.activity.ChoiceMyselfActivity;
import com.yssj.ui.dialog.ChoicenessDialog;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;

import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ChoicenessAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;

	public ChoicenessAdapter(Context context) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return ChoicenessDialog.arrayListForEveryGridView2.size();
	}

	@Override
	public Object getItem(int position) {
		return ChoicenessDialog.arrayListForEveryGridView2.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolderStyle holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_choiceness, null);
			holder = new ViewHolderStyle();
			holder.iv = (ImageView) convertView.findViewById(R.id.iv);
			holder.iv_close = (ImageView) convertView.findViewById(R.id.iv_close);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolderStyle) convertView.getTag();
		}
		holder.iv.setOnClickListener(new MyAdapterListener(position));
		HashMap<String, String> hashMap = ChoicenessDialog.arrayListForEveryGridView2.get(position);
		if (hashMap.get("show_pic").equals("0")) {
//			Picasso.with(context).load(R.drawable.zijixuan).into(holder.iv);
			holder.iv.setImageDrawable(context.getResources().getDrawable(R.drawable.zijixuan));
			holder.iv_close.setVisibility(View.GONE);
		} else {
			holder.iv_close.setVisibility(View.VISIBLE);
			try {
				String piccc = hashMap.get("show_pic").split(",")[0];
				piccc = hashMap.get("shop_code").substring(1, 4)+"/"+hashMap.get("shop_code").toString()+"/"+piccc;
				PicassoUtils.initImage(context, piccc+"!382", holder.iv);
				
//				SetImageLoader.initImageLoader3(context, holder.iv, piccc, "");
				
				holder.iv_close.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						ChoicenessDialog.arrayListForEveryGridView2.remove(position);
						notifyDataSetChanged();
					}
				});
				holder.iv_close.setVisibility(View.VISIBLE);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return convertView;
	}

	class ViewHolderStyle {
		ImageView iv;
		public ImageView iv_close;
	}

	class MyAdapterListener implements OnClickListener {

		private int position;

		public MyAdapterListener(int pos) {
			position = pos;
		}

		@Override
		public void onClick(View v) {
			if (position == 0) {
				//自己选
				context.startActivity(new Intent(context, ChoiceMyselfActivity.class));
			}
		}
	}
}
