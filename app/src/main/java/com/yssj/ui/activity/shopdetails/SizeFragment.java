//package com.yssj.ui.activity.shopdetails;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.AbsListView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.yssj.YConstance.SizeIndex;
//import com.yssj.activity.R;
//import com.yssj.custom.view.MyScrollView;
//import com.yssj.custom.view.MyScrollView.OnScrollListener;
//import com.yssj.data.YDBHelper;
//import com.yssj.entity.Shop;
//
//public class SizeFragment extends Fragment {
//	private View view;
//	private YDBHelper helper;
//
//	private TextView tv_shopconnect;
//	private static Shop shop;
//
//	private static MyScrollView myScrollView;
//	
//	private LinearLayout viewContainer;
//
//	public static SizeFragment newInstance(Shop mshop, MyScrollView myscrollView) {
//		 SizeFragment sizeFragment = new SizeFragment();
//		 shop = mshop;
//			myScrollView = myscrollView;
//		 return sizeFragment;
//	}
//	
//	private void initScrollView(){
//		myScrollView.getView();
//		myScrollView.setOnScrollListener(new OnScrollListener() {
//			@Override
//			public void onTop() {}
//			@Override
//			public void onScroll() {}
//			@Override
//			public void onBottom() {}
//			@Override
//			public void onAutoScroll(int l, int t, int oldl, int oldt) {}
//			@Override
//			public void onScrollStop() {
//				
//			}
//		});
//	}
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		helper = new YDBHelper(getActivity());
//	}
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
////		Bundle bundle = getArguments();
////		shop = (Shop) bundle.getSerializable("shop");
////		myScrollView = (MyScrollView) bundle.getSerializable("myScrollview");
//		
//		view = inflater.inflate(R.layout.fragment_size, container, false);
//		viewContainer = (LinearLayout) view.findViewById(R.id.viewContainer);
//		initScrollView();
//		tv_shopconnect = (TextView) view.findViewById(R.id.tv_shopconnect);
//
//		if (shop != null && !TextUtils.isEmpty(shop.getContent())) {
//			tv_shopconnect.setText(shop.getContent());
//		}
//		refreshSFView(shop);
//		return view;
//	}
//
//	private List<String[]> toList(int lSize, List<String[]> list) {
//		int p = 1;
//		String strFenGe[] = { "分割" };
//		List<String[]> arrayList = new ArrayList<String[]>();
//		for (int i = 0; i < lSize; i++) {
//			for (int j = 0; j < list.size(); j++) {
//				String strY[] = list.get(j);
//				String str[] = new String[4];
//				str[0] = strY[1];
//				int l = strY.length;
//
//				if ((3 * p - 1) < l) {
//					str[1] = strY[(3 * p - 1)];
//				}
//				if ((3 * p) < l) {
//					str[2] = strY[(3 * p)];
//				}
//				if ((3 * p + 1) < l) {
//					str[3] = strY[3 * p + 1];
//				}
//				arrayList.add(str);
//
//			}
//			p++;
//			if (i != lSize - 1) {
//				arrayList.add(strFenGe);
//			}
//		}
//
//		return arrayList;
//
//	}
//
//	private void refreshSFView(Shop shop) {
//		int l = 0;
//
//		List<String[]> list = shop.getShop_attr();
//		if (list == null || list.size() == 0) {
//			return;
//		}
//		List<String[]> listSize = new ArrayList<String[]>();
//		for (int i = 0; i < list.size(); i++) {// 选择尺码
//			String str[] = list.get(i);
//			if (str[0].equals(SizeIndex.id)) {
//				int length = str.length;
//				if (length > l) {
//					l = length;
//				}
//				listSize.add(str);
//			}
//		}
//		l = l - 2;
//		int lSize = l / 3;
//		int mond = l % 3;
//		if (mond > 0) {
//			lSize++;
//		}
//		List<String[]> listAll = toList(lSize, listSize);
//		addView(viewContainer, listAll);
//	}
//
//	private void addView(LinearLayout container, List<String[]> listAll){
//		container.removeAllViews();
//		LayoutInflater inflater = LayoutInflater.from(getActivity());
//		for(int i = 0; i < listAll.size(); i++){
//			View convertView = inflater.inflate(R.layout.listview_size, null);
//			
//			TextView tv_name = (TextView) convertView
//					.findViewById(R.id.tv_name);
//			TextView tv_size1 = (TextView) convertView
//					.findViewById(R.id.tv_size1);
//			TextView tv_size2 = (TextView) convertView
//					.findViewById(R.id.tv_size2);
//			TextView tv_size3 = (TextView) convertView
//					.findViewById(R.id.tv_size3);
//
//			String[] str = listAll.get(i);
//			if (!TextUtils.isEmpty(str[0])) {
//				if (str[0].equals("分割")) {
//					convertView.setLayoutParams(new AbsListView.LayoutParams(
//							LayoutParams.MATCH_PARENT, 30));
//				} else {
//					for (int j = 0; j < str.length; j++) {
//						if (!TextUtils.isEmpty(str[j])) {
//							String attr_name = helper.queryAttr_name(str[j]);
//							if (!TextUtils.isEmpty(attr_name)) {
//								if (j == 0) {
//									tv_name.setText(attr_name);
//								}
//								if (j == 1) {
//									tv_size1.setText(attr_name);
//								}
//								if (j == 2) {
//									tv_size2.setText(attr_name);
//								}
//								if (j == 3) {
//									tv_size3.setText(attr_name);
//								}
//							}
//						}
//
//					}
//				}
//			}
//			container.addView(convertView);
//		}
//	}
//	
//
//}
