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
//import com.yssj.ui.activity.circles.PostDetailActivity;
//import com.yssj.ui.adpter.RecomCircleAdapter;
///***
// * 美圈
// * @author Administrator
// *
// */
//
////public class RecommendCircleFragment extends BeautyCirleFragment<HashMap<String, Object>>{
////	
////
////	@Override
////	protected List<HashMap<String, Object>> onLoadData(Context context,
////			Integer index) throws Exception {
////		// TODO Auto-generated method stub
////		return ComModel2.getRecommendCircle(context, index,"false");
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
////		return new RecomCircleAdapter(getActivity());
////	}
////
////	@Override
////	public void onItemClick(AdapterView<?> parent, View view, int position,
////			long id) {
////		super.onItemClick(parent, view, position, id);
////		
////		if (position >= getAdapter().getCount()) {
////			return;
////		}
////		Intent intent = new Intent(getActivity(), PostDetailActivity.class);
////		intent.putExtra("item", (Serializable) getAdapter().getItem(position-1));
////		startActivity(intent);
////		
////	}
////	
////	
////
////}
