//package com.yssj.ui.fragment.circles;
//
//import com.yssj.activity.R;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
///**
// * 圈圈界面
// * @author lbp
// *
// */
//public class CircleMainFragment
////extends Fragment implements OnClickListener
//{
//	
////	private TextView bizCircle,myCircle;
////	
////	private static Context mContext;
////	
////	public static  CircleMainFragment getIntance(Context context){
////		
////		CircleMainFragment fragment=new CircleMainFragment();
////		mContext=context;
////		return fragment;
////	}
////	
////	
////	@Override
////	public View onCreateView(LayoutInflater inflater,
////			 ViewGroup container,  Bundle savedInstanceState) {
////		
////		View view =inflater.inflate(R.layout.circle_main_fagment, container,false);
////		bizCircle=(TextView) view.findViewById(R.id.biz_circle);
////		bizCircle.setOnClickListener(this);
////		myCircle=(TextView) view.findViewById(R.id.my_circle);
////		myCircle.setOnClickListener(this);
////		
////		bizCircle.setSelected(true);
////		myCircle.setSelected(false);
////		
////		return view;
////	}
////	
////	@Override
////	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
////		// TODO Auto-generated method stub
////		super.onActivityCreated(savedInstanceState);
////		
////		FragmentTransaction ft=getChildFragmentManager().beginTransaction();
////		
////		if(getChildFragmentManager().findFragmentByTag("biz")!=null){
////			ft.show(getChildFragmentManager().findFragmentByTag("biz"));
////		}else{
////			ft.add(R.id.container, BizCircleFragment.getIntances(mContext),"biz")
////		;
////		}
////		if(getChildFragmentManager().findFragmentByTag("my")!=null){
////			ft.hide(getChildFragmentManager().findFragmentByTag("my"));
////		}
////			ft.commitAllowingStateLoss();
////	}
////
////	@Override
////	public void onClick(View arg0) {
////		FragmentTransaction ft=getChildFragmentManager().beginTransaction();
////		switch (arg0.getId()) {
////		case R.id.biz_circle:
////		{
////			bizCircle.setSelected(true);
////			myCircle.setSelected(false);
////			if(getChildFragmentManager().findFragmentByTag("biz")!=null){
////				ft.show(getChildFragmentManager().findFragmentByTag("biz"));
////			}else{
////				ft.
////				add(R.id.container, BizCircleFragment.getIntances(mContext),"biz");
////				
////			}
////			if(getChildFragmentManager().findFragmentByTag("my")!=null){
////				ft.hide(getChildFragmentManager().findFragmentByTag("my"));
////			}
////			ft.commitAllowingStateLoss();
////		}
////			break;
////		case R.id.my_circle:
////		{
////			bizCircle.setSelected(false);
////			myCircle.setSelected(true);
////			
////			if(getChildFragmentManager().findFragmentByTag("my")!=null){
////				ft.show(getChildFragmentManager().findFragmentByTag("my"));
////			}else{
////				ft.
////				add(R.id.container, SignFragment.newInstance(mContext),"my")
////				;
////			}
////			if(getChildFragmentManager().findFragmentByTag("biz")!=null){
////				ft.hide(getChildFragmentManager().findFragmentByTag("biz"));
////			}
////			ft.commitAllowingStateLoss();
////		}
////			break;	
////			
////			
////		default:
////			break;
////		}
////	}
////	
////	
//	
//<<<<<<< .mine
//=======
//	private static Context mContext;
//	
//	public static  CircleMainFragment getIntance(Context context){
//		
//		CircleMainFragment fragment=new CircleMainFragment();
//		mContext=context;
//		return fragment;
//	}
//	
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater,
//			 ViewGroup container,  Bundle savedInstanceState) {
//		
//		View view =inflater.inflate(R.layout.circle_main_fagment, container,false);
//		bizCircle=(TextView) view.findViewById(R.id.biz_circle);
//		bizCircle.setOnClickListener(this);
//		myCircle=(TextView) view.findViewById(R.id.my_circle);
//		myCircle.setOnClickListener(this);
//		
//		bizCircle.setSelected(true);
//		myCircle.setSelected(false);
//		
//		return view;
//	}
//
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		
//	}
//	
////	@Override
//	/*public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onActivityCreated(savedInstanceState);
//		
//		FragmentTransaction ft=getChildFragmentManager().beginTransaction();
//		
//		if(getChildFragmentManager().findFragmentByTag("biz")!=null){
//			ft.show(getChildFragmentManager().findFragmentByTag("biz"));
//		}else{
//			ft.add(R.id.container, BizCircleFragment.getIntances(mContext),"biz")
//		;
//		}
//		if(getChildFragmentManager().findFragmentByTag("my")!=null){
//			ft.hide(getChildFragmentManager().findFragmentByTag("my"));
//		}
//			ft.commitAllowingStateLoss();
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		FragmentTransaction ft=getChildFragmentManager().beginTransaction();
//		switch (arg0.getId()) {
//		case R.id.biz_circle:
//		{
//			bizCircle.setSelected(true);
//			myCircle.setSelected(false);
//			if(getChildFragmentManager().findFragmentByTag("biz")!=null){
//				ft.show(getChildFragmentManager().findFragmentByTag("biz"));
//			}else{
//				ft.
//				add(R.id.container, BizCircleFragment.getIntances(mContext),"biz");
//				
//			}
//			if(getChildFragmentManager().findFragmentByTag("my")!=null){
//				ft.hide(getChildFragmentManager().findFragmentByTag("my"));
//			}
//			ft.commitAllowingStateLoss();
//		}
//			break;
//		case R.id.my_circle:
//		{
//			bizCircle.setSelected(false);
//			myCircle.setSelected(true);
//			
//			if(getChildFragmentManager().findFragmentByTag("my")!=null){
//				ft.show(getChildFragmentManager().findFragmentByTag("my"));
//			}else{
//				ft.
//				add(R.id.container, SignFragment.newInstance(mContext),"my")
//				;
//			}
//			if(getChildFragmentManager().findFragmentByTag("biz")!=null){
//				ft.hide(getChildFragmentManager().findFragmentByTag("biz"));
//			}
//			ft.commitAllowingStateLoss();
//		}
//			break;	
//			
//			
//		default:
//			break;
//		}
//	}
//	*/
//	
//	
//>>>>>>> .r26813
//}
