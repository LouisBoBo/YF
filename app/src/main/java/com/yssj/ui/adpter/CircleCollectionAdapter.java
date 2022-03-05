//package com.yssj.ui.adpter;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.custom.view.RoundImageButton;
//import com.yssj.ui.activity.circles.PostDetailNewActivity;
//import com.yssj.ui.base.BaseMainAdapter;
//import com.yssj.utils.SetImageLoader;
//
//public class CircleCollectionAdapter extends BaseMainAdapter {
//
//	ViewHolder holder;
//	private boolean flag = false;
//	
//	public CircleCollectionAdapter(Context context) {
//		super(context);
//		
//		configCheckMap(false,false);
//	}
//	
//	/**
//	 * CheckBox 是否选择的存储集合,key 是 position , value 是该position是否选中
//	 */
//	private Map<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();
//	
//	/**
//	 * 首先,默认情况下,所有项目都是没有选中的.这里进行初始化
//	 */
//	public void configCheckMap(boolean bool,boolean flag) {
//
//		for (int i = 0; i < result.size(); i++) {
//			isCheckMap.put(i, bool);
//		}
//		
//		if(this.flag==flag){
//			return;
//		}
//		this.flag = flag;
//		
//	}
//	
//	// 移除一个项目的时候
//	public void remove(int position) {
//		this.result.remove(position);
//	}
//
//	public Map<Integer, Boolean> getCheckMap() {
//		return this.isCheckMap;
//	}
//
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		ViewHolder holder;
//		if(convertView == null){
//			holder = new ViewHolder();
//			convertView = View.inflate(context, R.layout.circle_myrecord_list_item, null);
//			holder.rib_img = (RoundImageButton) convertView.findViewById(R.id.rib_img);
//			holder.img_goods = (ImageView) convertView.findViewById(R.id.img_goods);
//			holder.imgbtn_choose = (CheckBox) convertView.findViewById(R.id.imgbtn_choose);
//			holder.tv_nickname = (TextView) convertView.findViewById(R.id.tv_nickname);
//			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
//			
//			convertView.setTag(holder);
//		}else{
//			holder = (ViewHolder) convertView.getTag();
//		}
//		
//		if(flag){
//			holder.rib_img.setVisibility(View.INVISIBLE);
//			holder.imgbtn_choose.setVisibility(View.VISIBLE);
//			if (isCheckMap.get(position) == null) {
//				isCheckMap.put(position, false);
//			}
//
//			holder.imgbtn_choose.setChecked(isCheckMap.get(position));
//		}else{
//			holder.rib_img.setVisibility(View.VISIBLE);
//			holder.rib_img.setTag((String) result.get(position).get("upic"));
//			SetImageLoader.initImageLoader(context, holder.rib_img, (String) result.get(position).get("upic"),"");
//			holder.imgbtn_choose.setVisibility(View.INVISIBLE);
//		}
//		holder.img_goods.setTag(((String) result.get(position).get("npic")));
////		MyLogYiFu.e("收藏记录",((String) result.get(position).get("npic")).split(":")[0]);
//		
//		String url =((String) result.get(position).get("npic")).split(":")[0] ;
//		if(url==""||url.length()==0||url==null){
//			holder.img_goods.setVisibility(View.GONE);
//		}else{
//			holder.img_goods.setVisibility(View.VISIBLE);
//			SetImageLoader.initImageLoader(null, holder.img_goods, url,"");
//		}
//		
////		SetImageLoader.loadImage(context, holder.img_goods, ((String) result.get(position).get("npic")).split(":")[0]);
//		holder.tv_nickname.setText((CharSequence) result.get(position).get("nickname"));
//		holder.tv_title.setText((CharSequence) result.get(position).get("title"));
//		
//		convertView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(context, PostDetailNewActivity.class);
//				intent.putExtra("news_id", (CharSequence) result.get(position).get("news_id"));
//				intent.putExtra("user_id", (CharSequence) result.get(position).get("user_id"));
//				intent.putExtra("circle_id", (CharSequence) result.get(position).get("circle_id"));
//				context.startActivity(intent);
//			}
//		});
//		
//		/*
//		 * 设置单选按钮的选中
//		 */
//		holder.imgbtn_choose.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView,
//					boolean isChecked) {
//				/*
//				 * 将选择项加载到map里面寄存
//				 */
//				isCheckMap.put(position, isChecked);
//			}
//		});
//		
//		
//		return convertView;
//	}
//	
//	class ViewHolder{
//		RoundImageButton rib_img;
//		CheckBox imgbtn_choose;
//		ImageView img_goods;
//		TextView tv_nickname,tv_title;
//	}
//	
//
//}
