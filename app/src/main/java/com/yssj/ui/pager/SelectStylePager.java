package com.yssj.ui.pager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.NoScrollViewPager;
import com.yssj.model.ComModel2;
import com.yssj.ui.base.BasePager;
import com.yssj.ui.dialog.SelectSuppleDialog.SuppleListener;
import com.yssj.utils.PicassoUtils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SelectStylePager extends BasePager {
	private Context mContext;
	private GridView gvStyle;
	private NoScrollViewPager mViewPager;
	private TextView rightTitleTv;

	public SelectStylePager(Context context,NoScrollViewPager mViewPager,TextView rightTitleTv) {
		super(context);
		mContext = context;
		this.mViewPager = mViewPager;
		this.rightTitleTv = rightTitleTv;
	}

	private View view;

	@Override
	public View initView() {
		view = ((Activity) context).getLayoutInflater().inflate(R.layout.select_style_viewpager, null);
		gvStyle = (GridView) view.findViewById(R.id.gv_style);
		return view;
	}

	private HashMap<String, ArrayList<HashMap<String, String>>> allList = new HashMap<String, ArrayList<HashMap<String, String>>>();

	@Override
	public void initData() {
		new SAsyncTask<Void, Void, HashMap<String, ArrayList<HashMap<String, String>>>>((FragmentActivity) context, 0) {

			@Override
			protected HashMap<String, ArrayList<HashMap<String, String>>> doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel2.getHobbyList(context);
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, ArrayList<HashMap<String, String>>> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null && result != null) {
					if (result.size() != 0) {
						allList = result;
						// 风格
						mList = allList.get("2");
						for (HashMap<String, String> hashMap : mList) {
							hashMap.put("isChecked", "0");
						}
						final StyleGridViewAdapter gridViewAdapter2 = new StyleGridViewAdapter(mContext);

						// 监听风格选中的个数
						gvStyle.setAdapter(gridViewAdapter2);

					}

				}

			}

		}.execute();
	}
	private ArrayList<HashMap<String, String>> mList=new ArrayList<HashMap<String,String>>();
	class StyleGridViewAdapter extends BaseAdapter {
		private Context mContext;
		private List<ToggleButton> buttonList = new ArrayList<ToggleButton>();

		public StyleGridViewAdapter(Context mContext) {
			super();
			this.mContext = mContext;
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
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
			final ViewHolderStyle holder ;
			if (convertView == null) {
				holder = new ViewHolderStyle();
				convertView = LayoutInflater.from(this.mContext).inflate(R.layout.select_style_item, null);
				holder.iv = (ImageView) convertView.findViewById(R.id.iv);
				holder.cb = (ImageView) convertView.findViewById(R.id.item_cb);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolderStyle) convertView.getTag();
			}
			if (mList != null) {
				final HashMap<String, String> hashMap = mList.get(position);
				PicassoUtils.initImage(mContext, hashMap.get("like_pic"), holder.iv);
				if("1".equals(hashMap.get("isChecked"))){
					holder.cb.setImageResource(R.drawable.wodexihao_fengge_icon_xuanzhong);
				}else{
					holder.cb.setImageResource(R.drawable.wodexihao_fengge_icon_weixuanzhong);
				}
			}
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					for (HashMap<String, String> hashMap : mList) {
						hashMap.put("isChecked", "0");
					}
					mList.get(position).put("isChecked", "1");
					notifyData();
					mViewPager.setNoScroll(false);
					rightTitleTv.setClickable(true);
					mViewPager.setCurrentItem(1, true);
					if(listener!=null){
						listener.setStyleListener(mList.get(position));
					}
				}
			});
			return convertView;
		}
	}

	public static class ViewHolderStyle {
		ImageView iv;
	    ImageView cb;
	}
	
	
	public interface  StyleListener{
		void setStyleListener(HashMap<String, String> hashMap);
	}
	private StyleListener listener;
	public void setStyleListener(StyleListener listener){
		this.listener = listener;
	}
}
