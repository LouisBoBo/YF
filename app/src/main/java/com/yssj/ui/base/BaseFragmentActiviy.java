package com.yssj.ui.base;

import java.util.List;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class BaseFragmentActiviy extends FragmentActivity {
	 private static final String TAG = "BaseActivity";

	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  FragmentManager fm = getSupportFragmentManager();
	  int index = requestCode >> 16;
	  if (index != 0) {
	   index--;
	   if (fm.getFragments() == null || index < 0
	     || index >= fm.getFragments().size()) {
	  
	    return;
	   }
	   Fragment frag = fm.getFragments().get(index);
	   if (frag == null) {
	 
	   } else {
	    handleResult(frag, requestCode, resultCode, data);
	   }
	   return;
	  }
	 }


	 /**
	  * 递归调用，对所有子Fragement生效
	  * 
	  * @param frag
	  * @param requestCode
	  * @param resultCode
	  * @param data
	  */
	 private void handleResult(Fragment frag, int requestCode, int resultCode,
	   Intent data) {
	  frag.onActivityResult(requestCode & 0xffff, resultCode, data);
	  List<Fragment> frags = frag.getChildFragmentManager().getFragments();
	  if (frags != null) {
	   for (Fragment f : frags) {
	    if (f != null)
	     handleResult(f, requestCode, resultCode, data);
	   }
	  }
	 }
}
