//package com.yssj.ui.adpter;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.ToggleButton;
//
//import com.yssj.activity.R;
//import com.yssj.entity.MyToggleButton;
//import com.yssj.ui.activity.main.FilterConditionActivity;
//import com.yssj.ui.base.BasicActivity;
//
//public class UnionGridViewAdapter extends BaseAdapter {
//	private Context mContext;
//	private ArrayList<HashMap<String, String>> mList;
//
//	private CheckCallback checkCallback;
//	private List<ToggleButton> buttonList = new ArrayList<ToggleButton>();
//	
//	public UnionGridViewAdapter(Context mContext,
//			ArrayList<HashMap<String, String>> mList) {
//		super();
//		this.mContext = mContext;
//		this.mList = mList;
//	}
//	public ArrayList<HashMap<String, String>> getData(){
//		return mList;
//	}
//	
//	public void setListener(Activity mActivity) {
//		this.checkCallback = (CheckCallback) mActivity;
//	}
//
//	public interface CheckCallback {
//		void onCheckCallback(int positon, boolean isChecked,
//				HashMap<String, String> map, UnionGridViewAdapter mAdapter);
//	}
//	
//	
//
//	@Override
//	public int getCount() {
//		if (mList == null) {
//			return 0;
//		} else {
//			return this.mList.size();
//		}
//	}
//
//	@Override
//	public Object getItem(int position) {
//		if (mList == null) {
//			return null;
//		} else {
//			return this.mList.get(position);
//		}
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		 final ViewHolder holder;
//		if (convertView == null) {
//			holder = new ViewHolder();
//			convertView = LayoutInflater.from(this.mContext).inflate(
//					R.layout.union_gridview_item, null, false);
//			holder.button = (MyToggleButton) convertView
//					.findViewById(R.id.gridview_item_button);
//			buttonList.add(holder.button);
//			holder.button
//					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//						@Override
//						public void onCheckedChanged(CompoundButton arg0,
//								boolean arg1) {
////							 TODO Auto-generated method stub
////						       if(arg1){
////									holder.button.setFocusableInTouchMode(true);
////									holder.button.setFocusable(true);
////									
////								}
////								else{
////									holder.button.setFocusableInTouchMode(false);
////									holder.button.setFocusable(false);
////								}
//							checkCallback.onCheckCallback(position, arg1,
//									mList.get(position), UnionGridViewAdapter.this);
//
//							}
//						});
////			holder.button.setEllipsize(TruncateAt.MARQUEE);
//			convertView.setTag(holder);
//
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//
//		if (this.mList != null) {
//			HashMap<String, String> hashMap = this.mList.get(position);
//			if (holder.button != null) {
//				holder.button.setText(hashMap.get("attr_name").toString());
//				holder.button.setTextOff(hashMap.get("attr_name").toString());
//				holder.button.setTextOn(hashMap.get("attr_name").toString());
//				if(hashMap.get("isChecked").equals("1")){
//					holder.button.setChecked(true);
//				}else
//					holder.button.setChecked(false);
//			}
//		}
//		return convertView;
//	}
//
//	private class ViewHolder {
//		MyToggleButton button;
//	}
//}
