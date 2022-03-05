package com.yssj;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.newcaoguo.easyrollingnumber.view.ScrollingDigitalAnimation;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.service.LocationService;
import com.yssj.ui.activity.setting.UserProtocolActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.DateUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.SimpleCountDownTimer;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import java.util.ArrayList;

/**
 * Created by Administrator on 2020/6/16.
 */

public class TestActivity extends BasicActivity {


    private ScrollingDigitalAnimation money;    // 显示金钱的自定义控件
    private ScrollingDigitalAnimation number;  // 显示数字的自定义控件
    private ScrollingDigitalAnimation percentage; // 显示百分比的自定义控件

    private Context mContext;
    private double redPacketValue = 6.85;
    private final int SDK_PERMISSION_REQUEST = 127;
    private LocationService locationService;
    private Dialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext = this;
        money = findViewById(R.id.text);
        number = findViewById(R.id.text1);
        percentage = findViewById(R.id.text2);
        showRefusedDialog();


    }


    /**
     * 启动按钮单击事件
     */
//    public void start(View view) {
//        getPersimmions();
//    }
    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
//            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
//            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            } else {
                ToastUtil.showShortText2("已经拿到定位权限！");

                startGetLocation();

            }
        }
    }

//    @TargetApi(23)
//    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
//        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
//        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
//            if (shouldShowRequestPermissionRationale(permission)) {
//                return true;
//            } else {
//                permissionsList.add(permission);
//                return false;
//            }
//        } else {
//            return true;
//        }
//    }


    private void startGetLocation() {
        // -----------location config ------------
        locationService = ((YJApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mGetLocationListener);
        //注册监听
        LocationClientOption locationClientOption = locationService.getDefaultLocationClientOption();
        locationService.setLocationOption(locationClientOption);
        locationService.start();// 定位SDK


    }


    private BDAbstractLocationListener mGetLocationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            showDialog(bdLocation.getCity() + bdLocation.getDistrict());


        }
    };

    private void showDialog(String locationStr) {

        locationService.unregisterListener(mGetLocationListener); //注销掉监听
        locationService.stop(); //停止定位服务

        if (null != mDialog) {
            mDialog.dismiss();
        }

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        mDialog = new Dialog(mContext, R.style.dialogFullscreen);
        View dialogView = mInflater.inflate(R.layout.dialog_one_text_one_button_quanpin, null);
        TextView tv1 = dialogView.findViewById(R.id.tv1);
        TextView btn_ok = dialogView.findViewById(R.id.btn_ok);
        btn_ok.setText("我知道了");
        tv1.setText("当前位置是：" + locationStr);

        dialogView.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();

            }
        });
        dialogView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();

            }
        });

        mDialog.setCanceledOnTouchOutside(false);
        mDialog.addContentView(dialogView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));


        ToastUtil.showDialog(mDialog);


    }


    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == 0 && grantResults[1] == 0) {
            ToastUtil.showShortText2("获取定位权限成功！");
            startGetLocation();
        } else {


            Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            String pkg = "com.android.settings";
            String cls = "com.android.settings.applications.InstalledAppDetails";
            i.setComponent(new ComponentName(pkg, cls));
            i.setData(Uri.parse("package:" + getPackageName()));
            startActivity(i);

            ToastUtil.showShortText2("请开启定位权限哦~");

        }
    }

    private void showRefusedDialog() {
        mDialog = new Dialog(mContext, R.style.dialogFullscreen);

        View view = View.inflate(mContext, R.layout.dialog_get_location_permission, null);


        view.findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPersimmions();

            }
        });


        // 创建自定义样式dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        if (null != locationService) {
            locationService.unregisterListener(mGetLocationListener); //注销掉监听
            locationService.stop(); //停止定位服务
        }
        super.onStop();
    }


}
