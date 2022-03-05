//package com.yssj.ui.fragment.integral;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.yssj.YConstance.SizeIndex;
//import com.yssj.activity.R;
//import com.yssj.custom.view.NestedListView;
//import com.yssj.data.YDBHelper;
//import com.yssj.entity.IntegralShop;
//import com.yssj.entity.Shop;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.AbsListView;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//public class IntegralSizeFragment extends Fragment{
//	private NestedListView listView;
//	private ListViewAdpter adpter;
//	private View view ; 
//	private YDBHelper helper;
//
//	private TextView tv_shopconnect;
//	private IntegralShop shop ;
//	
//	@Override
//	public void onAttach(Activity activity) {
//		
//		super.onAttach(activity);
//	}
//	
//	public IntegralSizeFragment(IntegralShop shop){
//		this.shop =shop;
//	}
//	
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		view = inflater.inflate(R.layout.fragment_size, container, false);
////		listView = (NestedListView) view.findViewById(R.id.nListview);
//		helper = new YDBHelper(getActivity());
//		tv_shopconnect = (TextView) view.findViewById(R.id.tv_shopconnect);
//		
//		if (shop!= null && !TextUtils.isEmpty(shop.getContent())) {
//			tv_shopconnect.setText(shop.getContent());	
//		}
//		List<String[]> lists = refreshSFView(shop);
//		refeshListview(lists);
//		return view;
//	}
//	
//	public void refeshListview(List<String[]> list){
//		
//		if (adpter == null) {
//			adpter = new ListViewAdpter(list);
//			listView.setAdapter(adpter);
//		}else {
//			adpter.notifyDataSetChanged();
//		}
//	}
//	
//	private List<String[]> refreshSFView(IntegralShop shop){
//		    int l = 0 ;
//		  
//		    List<String[]> list = new ArrayList<String[]>();
//			String strs[] = shop.getShop_attr().split("_");
//			for (int i = 0; i < strs.length; i++) {
//				String strson[] = strs[i].split(",");
//				list.add(strson);
//			}
////			List<String[]> list = shop.getShop_attr();
//			if (list == null || list.size() == 0) {
//				return null;
//			}
//			List<String[]> listSize = new ArrayList<String[]>();
//			for (int i = 0; i < list.size(); i++) {//选择尺码
//				String str[] = list.get(i);
//				String sTest = str[0];
//				if (str[0].equals(SizeIndex.id)) {
//					int length = str.length;
//					if (length > l) {
//						l = length;
//					}
//					listSize.add(str);
//				}
//			}
//			l = l - 2;
//			int lSize = l/3;
//			int mond = l%3;
//			if (mond >0) {
//				lSize++;
//			}
//			List<String[]> listAll = toList(lSize, listSize);
//			return listAll;
//	}
//	
//	private  List<String[]> toList(int lSize ,List<String[]> list){
//		int p = 1;
//		String strFenGe[] = {"分割"};
//		List<String[]> arrayList = new ArrayList<String[]>();
//		for (int i = 0; i < lSize; i++) {
//			for (int j = 0; j < list.size(); j++) {	
//				String strY[] = list.get(j);
//				String str[] = new String [4];
//				str[0] = strY[1];
//				int l = strY.length;
//
//				if ((3*p - 1) < l ) {
//					str[1] = strY[(3*p - 1)];
//				}
//				if ((3*p ) < l ) {
//					str[2] = strY[(3*p)];
//				}
//				if ((3*p +1) < l ) {
//					str[3] = strY[3*p + 1 ];
//				}
//				arrayList.add(str);
//				
//			}
//			p++;
//			if (i!=lSize-1) {
//				arrayList.add(strFenGe);
//			}
//		}
//		
//		return arrayList;
//		
//	}
//	
//	class ListViewAdpter extends BaseAdapter{
//		private List<String[]> list ;
//		
//		
//		
//		public  ListViewAdpter(List<String[]> list){
//			this.list = list;
//		
//		}
//
//		@Override
//		public int getCount() {
//			
//	
//			return list.size() ; 
//			
//		}
//
//		@Override
//		public Object getItem(int position) {
//			
//			return list.get(position);
//			
//		}
//
//		@Override
//		public long getItemId(int position) {
//	
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			if (convertView == null) {
//				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.listview_size,null);
//				
//			}
//			TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
//			TextView tv_size1 = (TextView) convertView.findViewById(R.id.tv_size1);
//			TextView tv_size2 = (TextView) convertView.findViewById(R.id.tv_size2);
//			TextView tv_size3 = (TextView) convertView.findViewById(R.id.tv_size3);
//			
//			String[] str = list.get(position);
//			if (!TextUtils.isEmpty(str[0])) {
//				if (str[0].equals("分割")) {
//					convertView.setLayoutParams(new AbsListView.LayoutParams(
//							LayoutParams.MATCH_PARENT, 30));
//				}else {
//					for (int i = 0; i < str.length; i++) {
//						if (!TextUtils.isEmpty(str[i])) {
//							String attr_name = helper.queryAttr_name(str[i]);
//							if (!TextUtils.isEmpty(attr_name)) {
//								if (i == 0) {
//									tv_name.setText(attr_name);
//								} 
//								if (i == 1) {
//									tv_size1.setText(attr_name);
//								}
//								if (i == 2) {
//									tv_size2.setText(attr_name);
//								} 
//								if (i == 3) {
//									tv_size3.setText(attr_name);
//								}
//							}
//						}
//		
//					}
//				}
//			}
//			
//
//			return convertView;
//		}
//	
//	}
//
//}
