package com.yssj.ui.fragment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.data.YDBHelper;
import com.yssj.ui.activity.main.WordSearchResultActivity;
import com.yssj.utils.PicassoUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemClassficationFragment extends Fragment {

	private static Context mContext;

	private List<HashMap<String, String>> dataList;

	private String type_id;

	private GridView mList;
	private GridViewAdapter mAdapter;
	private YDBHelper dbHelp;
	public GridView getmList() {
		return mList;
	}

	public static ItemClassficationFragment newInstances(int position, String type_id,Context context) {
		ItemClassficationFragment instance = new ItemClassficationFragment();
		Bundle args = new Bundle();
		args.putString("type_id",type_id);
		mContext = context;
		instance.setArguments(args);

		return instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.item_classfication_fragment, container, false);

		type_id = getArguments().getString("type_id");
		
		mList = (GridView) v.findViewById(R.id.gv_item_classfication);
		dataList = new LinkedList<HashMap<String, String>>();
		return v;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}
	

	
	private void initData() {
		if (null != mContext){
			dbHelp = new YDBHelper(mContext);	
		}else{
			dbHelp = new YDBHelper(getActivity());
		}
		String sql = "select * from type_tag where type = " + type_id + " and class_type = 2 order by _id";
		dataList = dbHelp.query(sql);
		mAdapter = new GridViewAdapter(mContext,dataList);
		mList.setAdapter(mAdapter);
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
