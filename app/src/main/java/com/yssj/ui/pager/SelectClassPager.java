package com.yssj.ui.pager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.data.YDBHelper;
import com.yssj.ui.base.BasePager;
import com.yssj.utils.DP2SPUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class SelectClassPager extends BasePager {
	private LinearLayout mLlContainFrist;
	private ListView mLvContainSecond;
	private Context mContext;
	private YDBHelper dbHelp;
	private HashMap<String, List<HashMap<String, String>>> secondDatas;//二级类目的数据源集合 0 1 2 3 表示各个集合的键值
	private SelectAdapter mAdapter;
	
	public SelectClassPager(Context context) {
		super(context);
		mContext = context;
	}

	private View view;

	@Override
	public View initView() {
		view = ((Activity) context).getLayoutInflater().inflate(R.layout.select_class_viewpager, null);
		mLlContainFrist = (LinearLayout) view.findViewById(R.id.frist_class_contain);
		mLvContainSecond = (ListView) view.findViewById(R.id.second_class_contain);
		return view;
	}
	@Override
	public void initData() {
		secondDatas = new HashMap<String,List<HashMap<String, String>>>();
		dbHelp = new YDBHelper(mContext);
		String sql = "select * from sort_info where p_id = 0 and is_show = 1 and sort_name <> '热卖' and sort_name <> '特卖' and sort_name <> '上新' order by sequence ";
		final List<HashMap<String, String>> firstListSLevel = dbHelp.query(sql);
		if (firstListSLevel != null) {
			mLlContainFrist.removeAllViews();
			for (int firstIndext = 0; firstIndext< firstListSLevel.size(); firstIndext++) {
				final TextView textView = new TextView(mContext);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						DP2SPUtil.dp2px(mContext, 58));
				textView.setTextSize(16);
				textView.setTextColor(Color.parseColor("#7D7D7D"));
				final String strFirst = firstListSLevel.get(firstIndext).get("sort_name");
				final String typeFirst = ""+firstListSLevel.get(firstIndext).get("_id");
				if(firstIndext==0){
					textView.setBackgroundColor(Color.parseColor("#F0F0F0"));
					getSecondClass(typeFirst,firstListSLevel,firstIndext);
				}
				textView.setText(strFirst);
				textView.setGravity(Gravity.CENTER);
				mLlContainFrist.addView(textView, params);
				View view = new View(mContext);
				LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						DP2SPUtil.dip2px(mContext,0.5f));
				view.setBackgroundColor(Color.parseColor("#e5e5e5"));
				mLlContainFrist.addView(view, params2);
				final int firstPostion = firstIndext;
				textView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						for (int m = 0; m<  firstListSLevel.size(); m++) {
							mLlContainFrist.getChildAt(2*m).setBackgroundColor(Color.parseColor("#FFFFFF"));
						}
						textView.setBackgroundColor(Color.parseColor("#F0F0F0"));
						getSecondClass(typeFirst,firstListSLevel,firstPostion);
					}

				});
			}
		}

	}
	
	/**
	 * 获取二级类目
	 * @param dbHelp
	 * @param typeFirst
	 */
	private void getSecondClass(final String typeFirst,List<HashMap<String, String>> firstListSLevel,int firstIndext) {
		String sql = "select * from sort_info where p_id = " + typeFirst + " and is_show = 1 order by _id";
		final List<HashMap<String, String>> SecondListSLevel = dbHelp.query(sql);
		if(!secondDatas.containsKey(firstIndext+"")){
			for (int i = 0; i < SecondListSLevel.size(); i++) {
				SecondListSLevel.get(i).put("isSelected", "0");
			}
			secondDatas.put(firstIndext+"", SecondListSLevel);	
		}
		HashMap<String, String> hashMapFirst = firstListSLevel.get(firstIndext) ;
		mAdapter = new SelectAdapter(mContext,firstIndext,hashMapFirst,firstListSLevel);
		mLvContainSecond.setAdapter(mAdapter);
//		if (SecondListSLevel != null) {
//			mLlContainSecond.removeAllViews();
//			for (int i = 0; i < SecondListSLevel.size(); i++) {
//				final HashMap<String, String> hashMapSecond = SecondListSLevel.get(i) ;
//				View v = LayoutInflater.from(mContext).inflate(R.layout.select_class_tv, null);
//				TextView textView = (TextView) v.findViewById(R.id.select_class_tv);
//				final ImageView iv = (ImageView) v.findViewById(R.id.select_class_iv);
////				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
////						DP2SPUtil.dp2px(mContext, 58));
////				textView.setTextSize(16);
//				final String strSecond = SecondListSLevel.get(i).get("sort_name");
//				final String typeSecond = ""+SecondListSLevel.get(i).get("_id");
//				textView.setText(strSecond);
//				mLlContainSecond.addView(v);
//				View view = new View(mContext);
//				LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
//						DP2SPUtil.dip2px(mContext, 0.5f));
//				view.setBackgroundColor(Color.parseColor("#e5e5e5"));
//				mLlContainSecond.addView(view, params2);
//				v.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						iv.setVisibility(View.VISIBLE);
//						if(listener != null){
//							listener.setClassListener(hashMapFirst, hashMapSecond);
//						}
//					}
//				});
//			}
//		}
	}
	
	class SelectAdapter extends BaseAdapter {
		private LayoutInflater layoutInflator;
		private Context context;
		private int firstIndext;
		private HashMap<String, String> hashMapFirst;
		private List<HashMap<String, String>> firstListSLevel;

		public SelectAdapter(Context context,int firstIndext,HashMap<String, String> hashMapFirst,List<HashMap<String, String>> firstListSLevel) {
			this.context = context;
			this.firstIndext = firstIndext;
			this.hashMapFirst = hashMapFirst;
			this.firstListSLevel =  firstListSLevel;
			layoutInflator = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			return secondDatas.get(firstIndext+"").size();
		}

		@Override
		public Object getItem(int position) {
			return secondDatas.get(firstIndext+"").get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		private void notifyData(){
			this.notifyDataSetChanged();
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = layoutInflator.inflate(R.layout.select_class_tv, null);
				holder.tv = (TextView) convertView.findViewById(R.id.select_class_tv);
				holder.iv = (ImageView) convertView.findViewById(R.id.select_class_iv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final HashMap<String, String> hashMapSecond = secondDatas.get(firstIndext+"").get(position);
			final String strSecond = hashMapSecond.get("sort_name");
			final String typeSecond = ""+hashMapSecond.get("_id");
			String isSelected = hashMapSecond.get("isSelected");
			holder.tv.setText(strSecond);
			if("1".equals(isSelected)){
				holder.iv.setVisibility(View.VISIBLE);
			}else{
				holder.iv.setVisibility(View.GONE);	
			}
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					for (int i = 0; i < firstListSLevel.size(); i++) {
						List<HashMap<String, String>> secondDataslist = new ArrayList<HashMap<String,String>>();
						if(secondDatas.get(i+"")!=null){
							secondDataslist = secondDatas.get(i+"");
						}
						for (int j = 0; j< secondDataslist.size(); j++) {
							secondDatas.get(i+"").get(j).put("isSelected", "0");
						}
					}
					secondDatas.get(firstIndext+"").get(position).put("isSelected", "1");
					notifyData();
					if(listener != null){
						listener.setClassListener(hashMapFirst, hashMapSecond);
					}
				}
			});
			return convertView;
		}
		
		class ViewHolder {
			TextView tv;
			ImageView iv;
		}
		
	}
	
	
	
	
	public interface  ClassListener{
		void setClassListener(HashMap<String, String> hashMap1,HashMap<String, String> hashMap2);
	}
	private ClassListener listener;
	public void setClassListener(ClassListener listener){
		this.listener = listener;
	}	
	
	
	

}
