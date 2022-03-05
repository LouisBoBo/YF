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
//import com.yssj.ui.fragment.circles.CircleMemFragment;
//
//public class CircleUserActivity extends BasicActivity{
//	
////	private HashMap<String, Object> map;
////	private FragmentManager fm;
////	private FragmentTransaction ft;
////	private CircleMemFragment cmf;
////	
////	private int admin;
////
////	@Override
////	protected void onCreate(Bundle savedInstanceState) {
////		super.onCreate(savedInstanceState);
////		map = (HashMap<String, Object>) getIntent().getSerializableExtra("item");
////		admin = getIntent().getIntExtra("admin", 0);
////		fm = this.getSupportFragmentManager();
////		ft = fm.beginTransaction();
////		initView();
////		
////	}
////	
////	private void initView(){
////		setContentView(R.layout.circle_mem);
////		cmf = new CircleMemFragment((String) map.get("circle_id"),admin);
////		ft.add(R.id.list_container, cmf);
////		ft.commitAllowingStateLoss();
////	}
////	
//}
