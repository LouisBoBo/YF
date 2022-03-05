//package com.yssj.ui.activity.circles;
//
//import java.util.HashMap;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//
//import com.yssj.activity.R;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.ui.fragment.circles.CircleEssenceListFragment;
//
//public class CircleEssenceActivity extends BasicActivity{
//	
////	private FragmentManager fm;
////	private FragmentTransaction ft;
////	
////	private CircleEssenceListFragment felf;
////	private HashMap<String, Object> map;
////
////	@Override
////	protected void onCreate(Bundle savedInstanceState) {
////		super.onCreate(savedInstanceState);
////		map = (HashMap<String, Object>) getIntent().getSerializableExtra("item");
////		fm = this.getSupportFragmentManager();
////		ft = fm.beginTransaction();
//////		initView();
////	}
////	private void initView(){
////		setContentView(R.layout.circle_essence);
////		felf = new CircleEssenceListFragment((String) map.get("circle_id"));
////		ft.add(R.id.list_container, felf);
////		ft.commitAllowingStateLoss();
////	}
//}
