//package com.yssj.huanxin.activity;
//
//import com.yssj.ui.base.BasicActivity;
//
//import android.app.ActionBar;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
//
//public class ImageGridActivity extends BasicActivity {
//
//	private ActionBar aBar;
//	private static final String TAG = "ImageGridActivity";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
////        if (BuildConfig.DEBUG) {
////            Utils.enableStrictMode();
////        }
//        super.onCreate(savedInstanceState);
////        aBar = getActionBar();
////		aBar.setDisplayHomeAsUpEnabled(true);
//        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
//            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.add(android.R.id.content, new ImageGridFragment(), TAG);
//            ft.commit();
//        }
//    }
//
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//
//	}
//	/*@Override
//	protected void onResume() {
//		super.onResume();
//		JPushInterface.onResume(this);
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		JPushInterface.onPause(this);
//
//	}*/
//
//}
