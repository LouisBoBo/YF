//package com.yssj.ui.fragment;
//
//import java.util.HashMap;
//import java.util.List;
//
//import android.content.Context;
//import android.view.View;
//import android.widget.AdapterView;
//
//import com.yssj.app.ArrayAdapterCompat;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.adpter.MyCircleAdapter;
//
///***
// * 美圈
// * 
// * @author Administrator
// * 
// */
//
//public class MyCircleFragment extends BeautyCirleFragment<HashMap<String, Object>> {
//
////	private static final String TAB = "tab3";
////	private ListView list_my_circles;
////
////	public static MyCircleFragment newInstance(String title) {
////		MyCircleFragment fragment = new MyCircleFragment();
////		Bundle args = new Bundle();
////		args.putString(TAB, title);
////		fragment.setArguments(args);
////		return fragment;
////
////	}
//	
//	@Override
//	protected List<HashMap<String, Object>> onLoadData(Context context, Integer index)
//			throws Exception {
//
//		return ComModel2.getMyCircleList(context, index,"");
//	}
//
//	@Override
//	protected int getLoaderId() {
//		return 0;
//	}
//
//	@Override
//	protected ArrayAdapterCompat<HashMap<String, Object>> onCreateListAdapter() {
//		getListView().setDividerHeight(10);
//		return new MyCircleAdapter(getActivity());
////		 return null;
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position,
//			long id) {
//		// TODO Auto-generated method stub
//		super.onItemClick(parent, view, position, id);
//	}
//	
//	
//
//
//}
