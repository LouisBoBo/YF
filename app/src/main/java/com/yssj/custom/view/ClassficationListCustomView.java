package com.yssj.custom.view;

import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.ui.activity.main.WordSearchResultActivity;
import com.yssj.utils.PicassoUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClassficationListCustomView extends LinearLayout {
	private Context mContext;
	private TextView tvTitle;
	private MyGridView mMyGridView;
//	private RecordsDao recordsDao;
	
	public ClassficationListCustomView(Context context) {
		super(context);
		mContext = context;
//		this.recordsDao = recordsDao;
		initView(context);
	}

	public ClassficationListCustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView(context);
	}

	public ClassficationListCustomView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initView(context);
	}

	private void initView(Context context) {
		LayoutInflater.from(context).inflate(
				R.layout.classfication_list, this, true);
		tvTitle = (TextView) findViewById(R.id.classfication_title_category_a);
		mMyGridView = (MyGridView) findViewById(R.id.gv_classfication);
	}
	
	public void setTextView(String title){
		tvTitle.setText(title);
	}
	public void setGridView(List<HashMap<String, String>> mDataList){
		GridViewAdapter adapter = new GridViewAdapter(mContext, mDataList);
		mMyGridView.setAdapter(adapter);
	}
	public void hideTopLine(){
		findViewById(R.id.top_divider_line).setVisibility(View.GONE);
	}
	
	
	private class GridViewAdapter extends BaseAdapter {
		private Context context;
		private List<HashMap<String, String>> listData;
		private LayoutInflater mInflater;

		public GridViewAdapter(Context context, List<HashMap<String, String>> listData) {
			this.context = context;
			this.listData = listData;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				holder = new Holder();
				convertView = mInflater.inflate(R.layout.classfication_gridview_list_item, null);
				holder.ivIcon = (ImageView) convertView.findViewById(R.id.classfication_gv_item_iv_icon);
				holder.ivImage = (ImageView) convertView.findViewById(R.id.classfication_gv_item_iv);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.classfication_title_category_b);
				convertView.setTag(holder);

			} else {
				holder = (Holder) convertView.getTag();
			}
			final String class_name = listData.get(position).get("class_name");
			final String class_id = listData.get(position).get("_id");
			String isHot = listData.get(position).get("is_hot");
			String isNew = listData.get(position).get("is_new");
			holder.tvTitle.setText(class_name);
			if("1".equals(isNew)){
				holder.ivIcon.setVisibility(View.VISIBLE);
				holder.ivIcon.setImageResource(R.drawable.new_classfication);
			}else if("1".equals(isHot)){
				holder.ivIcon.setVisibility(View.VISIBLE);
				holder.ivIcon.setImageResource(R.drawable.hot_classfication);
			}else{
				holder.ivIcon.setVisibility(View.GONE);
			}
//			SetImageLoader.initImageLoader(mContext, holder.ivImage, listData.get(position).get("pic"), "!450");
			PicassoUtils.initImage2(mContext, listData.get(position).get("pic")+"!450", holder.ivImage);
			convertView.setOnClickListener( new OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					HashMap<String, String> recordsMap = new HashMap<String, String>();
//					recordsMap.put("class_name", class_name);
//					recordsMap.put("class_id", class_id);
//					recordsDao.addRecords(recordsMap);//添加搜索记录
					Intent intent = new Intent();
					intent = new Intent(mContext, WordSearchResultActivity.class);
					intent.putExtra("words", class_name.trim());
					intent.putExtra("class_id", class_id.trim());
					context.startActivity(intent);
					((Activity) context).overridePendingTransition(R.anim.activity_from_right,
							R.anim.activity_search_close);
				}
			});
			
		return convertView;
		
		}

		class Holder {
			ImageView ivImage,ivIcon;
			TextView tvTitle;
			
		}
	}

}
