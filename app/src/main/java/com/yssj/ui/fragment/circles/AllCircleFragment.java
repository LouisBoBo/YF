//package com.yssj.ui.fragment.circles;
//
//import java.io.Serializable;
//import java.util.HashMap;
//import java.util.List;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.app.ArrayAdapterCompat;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.circles.CircleDetailActivity;
//import com.yssj.ui.adpter.AllCircleAdapter;
//
///***
// * 美圈
// * 
// * @author Administrator
// * 
// */
//
//public class AllCircleFragment
////extends BeautyCirleFragment<HashMap<String, Object>>
//{
//
////
////	@Override
////	protected List<HashMap<String, Object>> onLoadData(Context context,
////			Integer index) throws Exception {
////		// TODO Auto-generated method stub
////		return ComModel2.getAllCircle(context, index);
////	}
////
////	@Override
////	protected int getLoaderId() {
////		// TODO Auto-generated method stub
////		return 0;
////	}
////
////	@Override
////	protected ArrayAdapterCompat<HashMap<String, Object>> onCreateListAdapter() {
////		// TODO Auto-generated method stub
////		return new AllCircleAdapter(getActivity());
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
////		intent.putExtra("flag", "AllCircleFragment");
////		startActivity(intent);
////	}
//
//}
