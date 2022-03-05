//package com.yssj.ui.fragment.circles;
//
//import java.io.Serializable;
//import java.util.HashMap;
//import java.util.List;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.View;
//import android.widget.AdapterView;
//
//import com.yssj.app.ArrayAdapterCompat;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.circles.CircleDetailActivity;
//import com.yssj.ui.adpter.MyCircleAdapter;
//
///***
// * 美圈
// * 
// * @author Administrator
// * 
// */
//
////public class MyCircleFragment extends BeautyCirleFragment<HashMap<String, Object>> {
////
////	
////	@Override
////	protected List<HashMap<String, Object>> onLoadData(Context context, Integer index)
////			throws Exception {
////
////		return ComModel2.getMyCircleList(context, index,"");
////	}
////
////	@Override
////	protected int getLoaderId() {
////		return 0;
////	}
////
////	@Override
////	protected ArrayAdapterCompat<HashMap<String, Object>> onCreateListAdapter() {
////		getListView().setDividerHeight(10);
////		return new MyCircleAdapter(getActivity());
//////		 return null;
////	}
////
////	@Override
////	public void onItemClick(AdapterView<?> parent, View view, int position,
////			long id) {
////		// TODO Auto-generated method stub
////		
////		
////		super.onItemClick(parent, view, position, id);
////		
////		if (position >= getAdapter().getCount()) {
////			return;
////		}
////		Intent intent = new Intent(getActivity(), CircleDetailActivity.class);
////		intent.putExtra("item", (Serializable) getAdapter().getItem(position-1));
////		intent.putExtra("flag", "MyCircleFragment");
////		startActivity(intent);
////	}
////	
////	
////
////
////}
