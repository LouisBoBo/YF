package com.yssj.ui.base;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.activity.logins.LoginFragment;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.ToastUtil;

import java.util.List;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class BasicActivity2 extends FragmentActivity implements OnClickListener {

    public ActionBar aBar;
    public Context context;
    // public SlidingMenu slidingMenu;

    public boolean isForeground = false; // 是否在前台运行

    // private TaskReceiver receiver;


//    public  static IWXAPI wXapi;//微信分享api


    /**
     * 弹出对话框
     */
    private AlertDialog mShowConfirmDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aBar = getActionBar();

        if (null != aBar) {
            aBar.setDisplayHomeAsUpEnabled(true);
            aBar.hide();
        }

//        if(!YUrl.debug){
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
//        }
        ImmersionBar.with(this).navigationBarColor(R.color.white)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .navigationBarDarkIcon(true)
                .statusBarDarkFont(true)
                .init();
        this.context = this;
        AppManager.getAppManager().addActivity(this);


//            wxShareApi = WXAPIFactory.createWXAPI(this, WxPayUtil.APP_ID,true);
//            wxShareApi.registerApp(WxPayUtil.APP_ID);


//        wXapi = WXAPIFactory.createWXAPI(this, WxPayUtil.APP_ID);
//        wXapi.registerApp(WxPayUtil.APP_ID);


        // receiver=new TaskReceiver(this);
        // isNetworkAvailable(this);
        // slidingMenu = ((MainMenuActivity)context).getSlidingMenu();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        // super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onResume() {
        YJApplication.setDialogContext(this);
        // TaskReceiver.regiserReceiver(this, receiver);
        super.onResume();
//		ToastUtil.showShortText(this, "onResume");
        MobclickAgent.onResume(this);
        HomeWatcherReceiver.unregisterHomeKeyReceiver(this);

//        if(!"-1".equals(SharedPreferencesUtil.getStringData(this, YConstance.Pref.PAYSUCCESSDIALOG_SHOW_DIALOG,"-1"))){
//            ToastUtil.showDialog(new ZeroBuyFinishDialog(this, R.style.DialogStyle1));
//            SharedPreferencesUtil.saveStringData(this, YConstance.Pref.PAYSUCCESSDIALOG,"-1");
//            SharedPreferencesUtil.saveStringData(this, YConstance.Pref.PAYSUCCESSDIALOG_SHOW_DIALOG,"-1");
//        }


        if (LoginFragment.needCheckDuobaoZhongjiang) {
            //夺宝结果弹窗
            DialogUtils.IndianaResultDialog(context);
            LoginFragment.needCheckDuobaoZhongjiang = false;
        }


        /**
         * 在这里本来可以使用if (!isAppOnForeground()) {//to do
         * sth}，但为了避免再次调用isAppOnForeground()而造成费时且增大系统的开销，故在这里我应用了一个标志位来判断
         */
        if (isForeground == false) { //
            isForeground = true;

            // if (SharedPreferencesUtil.getStringData(context,
            // "APPstartTimetime", "").endsWith("")) {
            // SharedPreferencesUtil.saveStringData(context, "APPstartTimetime",
            // YJApplication.startTiem + "");
            // }
            //
            // if (isAppOnForeground()) {
            // YJApplication.startTiem = Long
            // .parseLong(SharedPreferencesUtil.getStringData(context,
            // "APPstartTimetime", ""));
            // }else{
            // YJApplication.startTiem = System.currentTimeMillis();
            //// ToastUtil.showShortText(BasicActivity.this, "前台运行");
            // }

        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        // if (!isAppOnForeground()) { // 切到了后台
        // YJApplication.endtTiem = System.currentTimeMillis();
        // isForeground = false;
        // long useTime = YJApplication.endtTiem - YJApplication.startTiem;
        //// ToastUtil.showShortText(BasicActivity.this, "APP本次使用时长" + useTime /
        // 1000 + "秒");
        //
        // } else { // 切到了另外一个activity
        // isForeground = true;
        // YJApplication.startTiem = Long
        // .parseLong(SharedPreferencesUtil.getStringData(context,
        // "APPstartTimetime", ""));
        // }
    }

    @Override
    protected void onPause() {
        super.onPause();
//		ToastUtil.showShortText(this, "onPause");
        MobclickAgent.onPause(this);
        // unregisterReceiver(receiver);

    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        /**
         * 获取Android设备中所有正在运行的App
         */
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onMenuItemSelected(featureId, item);
    }

    public boolean setETNull(EditText editText, String str) {
        if (str == null || str.equals("")) {
            editText.setError("输入不能为空");
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * 确认提示框 <BR>
     * 标题栏显示“提示”，显示内容由msgResId提供，左边显示“确定”按钮，右边显示“取消”按钮
     */
    protected void showConfirmDialog(String msg, DialogInterface.OnClickListener onConfirmListener, int confirmResId,
                                     int cancelResId) {

        mShowConfirmDialog = getBuilder().setTitle(R.string.prompt).setMessage(msg)
                .setPositiveButton(confirmResId, onConfirmListener).setNegativeButton(cancelResId, null).create();
        mShowConfirmDialog.show();
    }

    /**
     * 构建弹出提示框 <BR>
     */
    protected Builder getBuilder() {
        Builder builder = null;
        if (getParent() != null) {
            builder = new Builder(getParent());
        } else {
            builder = new Builder(this);
        }
        return builder;
    }

    /**
     * 根据字符串 show toast<BR>
     *
     * @param message 字符串
     */
    public void showToast(CharSequence message) {
        ToastUtil.showShortText(this, message);
    }

	/*
     * @Override protected void onResume() { super.onResume();
	 * JPushInterface.onResume(this); }
	 * 
	 * @Override protected void onPause() { JPushInterface.onPause(this);
	 * super.onPause();
	 * 
	 * }
	 */

    @Override
    protected void onStart() {
        super.onStart();
        // umeng

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
    }

    // public static boolean isNetworkAvailable(Context context) {
    // ConnectivityManager connectivity =
    // (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    // if (connectivity == null) {
    // LogYiFu.i("NetWorkState", "Unavailabel");
    // return false;
    // } else {
    // NetworkInfo[] info = connectivity.getAllNetworkInfo();
    // if (info != null) {
    // for (int i = 0; i < info.length; i++) {
    // if (info[i].getState() == NetworkInfo.State.CONNECTED) {
    // LogYiFu.i("NetWorkState", "Availabel");
    // return true;
    // }
    // }
    // }
    // }
    // return false;
    // }

    public void initIndicator(PullToRefreshListView lv) {
        ILoadingLayout startLabels = lv.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("释放刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = lv.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("加载更多");
        endLabels.setRefreshingLabel("正在加载...");
        endLabels.setReleaseLabel("释放加载");
    }



}
