package com.yssj.custom.view;


import com.yssj.ui.activity.MainMenuActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class LoadingDialog  {
	
	private static CustomProgressDialog dialog=null;
	
	
	public static void show(FragmentActivity context){
		
		if(dialog!=null){
			hide(context);
		}
		dialog=CustomProgressDialog.createDialog(context);
		
		dialog.show();
		
		
	}
	
	
	public static void hide(Context context){
		if(dialog!=null){
			if(dialog.isShowing()){
				
				try {
					dialog.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			dialog=null;
		}
	};
	
	
	
	
	
	/**
	private static final String EXTRA_MESSAGE = "message";
	private static final String TAG = "loading_dialog";
	
	
	public static LoadingDialog newInstance(String message) {
		LoadingDialog f = new LoadingDialog();
		Bundle args = new Bundle();
		args.putString(EXTRA_MESSAGE, message);
		f.setArguments(args);
		return f;
	}
	
	public static void show(String message, FragmentManager fm) {
		if (fm == null) {
			return;
		}
		hide(fm);
		LoadingDialog f = LoadingDialog.newInstance(message);
//		if(f.getActivity()==null||f.getActivity().isFinishing()){
//			MyLogYiFu.e("TAG", "==="+f.getActivity().toString()+",>>>"+f.getActivity().isFinishing());
//			return ;
//		}
		
		FragmentTransaction ft = fm.beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		f.show(ft, TAG);
	}
	
	
	
	@Override
	public int show(FragmentTransaction transaction, String tag) {
		// TODO Auto-generated method stub
		
		    //修改commit方法为commitAllowingStateLoss          
		    transaction.add(this, tag);  
		   transaction.commitAllowingStateLoss();  
		return 0;
	}

	public static void hide(FragmentManager fm) {
		if (fm == null) {
			return;
		}
		LoadingDialog f = (LoadingDialog) fm.findFragmentByTag(TAG);
		if (f != null) {
			f.dismissAllowingStateLoss();
		}
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String message = getArguments().getString(EXTRA_MESSAGE);
		
		CustomProgressDialog dialog = CustomProgressDialog.createDialog(getActivity());
//		dialog.setIndeterminate(true);
		dialog.setCanceledOnTouchOutside(false);
		//dialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_medium_holo));
		dialog.setMessage(message);
		
		return dialog;
	}
	
	 @Override
	public void onSaveInstanceState(Bundle arg0) {
		// TODO Auto-generated method stub
		//super.onSaveInstanceState(arg0);
		 Log.i("TAG", "保存状态");
	}*/
}
