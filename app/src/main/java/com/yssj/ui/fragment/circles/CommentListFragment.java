//package com.yssj.ui.fragment.circles;
//
//import java.util.HashMap;
//import java.util.List;
//
//import android.content.Context;
//import android.view.View;
//import android.widget.AdapterView;
//
//import com.yssj.app.ArrayAdapterCompat;
//
//
//import com.yssj.ui.adpter.CommentListAdapter;
//
///***
// * 美圈
// * 
// * @author Administrator
// * 
// */
//
//public class CommentListFragment
////extends BeautyCirleFragment<HashMap<String, Object>>
//{
//
////
////	private String news_id;
////	
////	public CommentListFragment(String news_id){
////		this.news_id = news_id;
////	}
////	
////	@Override
////	protected List<HashMap<String, Object>> onLoadData(Context context,
////			Integer index) throws Exception {
////		// TODO Auto-generated method stub
//////		return ComModel2.getCommentList(context,news_id, index);
////		return null;
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
////		return new CommentListAdapter(getActivity());
////	}
////	
////	@Override
////	public void onItemClick(AdapterView<?> parent, View view, int position,
////			long id) {
////		// TODO Auto-generated method stub
//////		if (position >= getAdapter().getCount()) {
//////			return;
//////		}
//////		Intent intent = new Intent(getActivity(), PostDetailActivity.class);
//////		intent.putExtra("item", (Serializable) getAdapter().getItem(position));
//////		startActivity(intent);
////		
////		super.onItemClick(parent, view, position, id);
////	}
//
//}
