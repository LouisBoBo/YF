//package com.yssj.ui.fragment.circles;
//
//import java.util.HashMap;
//import java.util.List;
//
//import android.content.Context;
//
//import com.yssj.YJApplication;
//import com.yssj.app.ArrayAdapterCompat;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.adpter.CircleMemAdapter;
//
///***
// * 美圈
// * 
// * @author Administrator
// * 
// */
//
//public class CircleMemFragment
////extends BeautyCirleFragment<HashMap<String, Object>>
//{
//
////
////	private String circle_id;
////	private int admin;
////	
////	public CircleMemFragment(String circle_id, int admin){
////		this.circle_id = circle_id;
////		this.admin = admin;
////	}
////	
////	@Override
////	protected List<HashMap<String, Object>> onLoadData(Context context,
////			Integer index) throws Exception {
////		// TODO Auto-generated method stub
////		if(YJApplication.instance.isLoginSucess()){
////			return ComModel2.getCircleMem(context, circle_id, admin, index);
////		}else{
////			return ComModel2.getCircleMem2(context, circle_id, admin, index);
////		}
////		
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
////		return new CircleMemAdapter(getActivity());
////	}
//
//}
