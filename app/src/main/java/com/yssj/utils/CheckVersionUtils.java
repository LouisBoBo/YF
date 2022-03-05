package com.yssj.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.AppUpdateBean;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel2;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.service.ApkDownloadManager;

import java.util.HashMap;

import constant.UiType;
import listener.OnBtnClickListener;
import listener.OnInitUiListener;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

/**
 * Created by Administrator on 2017/6/15.
 */

public class CheckVersionUtils {

    public static boolean updateDialogShowEd = false;

    public static void checkVersion(final Context mContext) {

        if (updateDialogShowEd) {
            return;
        }

        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
            final String versionName = "v" + pi.versionName;


            HashMap<String, String> map = new HashMap<>();
            map.put("type", "1");

            YConn.httpPost(mContext, YUrl.GET_VERSION, map, new HttpListener<AppUpdateBean>() {
                @Override
                public void onSuccess(final AppUpdateBean result) {
                    if (StringUtils.isDownload(result.getVersion_no() + "", versionName)) {//有更新
//                        String apkUrl = "https://fga1.market.xiaomi.com/download/AppStore/0da6d85b8f9b7428737cdc5bb4f80436c187b92ea/com.tencent.mm.apk";
                        String apkUrl = YUrl.imgurl + result.getPath() + "";

                        UpdateConfig updateConfig = new UpdateConfig();
                        updateConfig.setAlwaysShowDownLoadDialog(true);
                        updateConfig.setForce((result.getIs_update() + "").equals("1"));
//                        updateConfig.setForce(true);

                        updateConfig.setShowNotification(true);
                        updateConfig.setNotifyImgRes(R.drawable.ic_launcher);

                        UiConfig uiConfig = new UiConfig();
                        uiConfig.setUiType(UiType.CUSTOM);
                        uiConfig.setCustomLayoutId(R.layout.view_update_dialog_custom);

                        UpdateAppUtils
                                .getInstance()
                                .apkUrl(apkUrl)
                                .updateTitle("衣蝠发新版啦~")
                                .updateContent(result.getMsg() + "")
                                .updateConfig(updateConfig)
                                .uiConfig(uiConfig)
                                .setOnInitUiListener(new OnInitUiListener() {
                                    @Override
                                    public void onInitUpdateUi(View view, UpdateConfig updateConfig, UiConfig uiConfig) {
                                        TextView tv_title1 = view.findViewById(R.id.tv_title1);
                                        tv_title1.setText(result.getVersion_no() + "更新了以下内容：");
                                    }
                                })
                                .update();
                        updateDialogShowEd = true;

                    }
                }

                @Override
                public void onError() {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void checkVersionAuout(final Context mContext) {


        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
            final String versionName = "v" + pi.versionName;


            HashMap<String, String> map = new HashMap<>();
            map.put("type", "1");

            YConn.httpPost(mContext, YUrl.GET_VERSION, map, new HttpListener<AppUpdateBean>() {
                @Override
                public void onSuccess(final AppUpdateBean result) {
                    if (StringUtils.isDownload(result.getVersion_no() + "", versionName)) {//有更新
//                        String apkUrl = "https://fga1.market.xiaomi.com/download/AppStore/0da6d85b8f9b7428737cdc5bb4f80436c187b92ea/com.tencent.mm.apk";
                        String apkUrl = YUrl.imgurl + result.getPath() + "";
                        UpdateConfig updateConfig = new UpdateConfig();
                        updateConfig.setAlwaysShowDownLoadDialog(true);
                        updateConfig.setForce((result.getIs_update() + "").equals("1"));
//                        updateConfig.setForce(true);

                        updateConfig.setShowNotification(true);
                        updateConfig.setNotifyImgRes(R.drawable.ic_launcher);

                        UiConfig uiConfig = new UiConfig();
                        uiConfig.setUiType(UiType.CUSTOM);
                        uiConfig.setCustomLayoutId(R.layout.view_update_dialog_custom);

                        UpdateAppUtils
                                .getInstance()
                                .apkUrl(apkUrl)
                                .updateTitle("衣蝠发新版啦~")
                                .updateContent(result.getMsg() + "")
                                .updateConfig(updateConfig)
                                .uiConfig(uiConfig)
                                .setOnInitUiListener(new OnInitUiListener() {
                                    @Override
                                    public void onInitUpdateUi(View view, UpdateConfig updateConfig, UiConfig uiConfig) {
                                        TextView tv_title1 = view.findViewById(R.id.tv_title1);
                                        tv_title1.setText(result.getVersion_no() + "更新了以下内容：");
                                    }
                                })
                                .update();
                        updateDialogShowEd = true;

                    } else {
                        ToastUtil.showShortText2("已经是最新版本了哦~");
                    }
                }

                @Override
                public void onError() {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public interface OnNoUpdateListener {
        void cancel();
    }

    public static void checkVersionAppStart(final Context mContext, final OnNoUpdateListener onNoUpdateListener) {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
            final String versionName = "v" + pi.versionName;


            HashMap<String, String> map = new HashMap<>();
            map.put("type", "1");

            YConn.httpPost(mContext, YUrl.GET_VERSION, map, new HttpListener<AppUpdateBean>() {
                @Override
                public void onSuccess(final AppUpdateBean result) {
                    if (StringUtils.isDownload(result.getVersion_no() + "", versionName)) {//有更新
//                        String apkUrl = "https://fga1.market.xiaomi.com/download/AppStore/0da6d85b8f9b7428737cdc5bb4f80436c187b92ea/com.tencent.mm.apk";
                        String apkUrl = YUrl.imgurl + result.getPath() + "";
                        UpdateConfig updateConfig = new UpdateConfig();
                        updateConfig.setAlwaysShowDownLoadDialog(true);
                        updateConfig.setForce((result.getIs_update() + "").equals("1"));
//                        updateConfig.setForce(true);

                        updateConfig.setShowNotification(true);
                        updateConfig.setNotifyImgRes(R.drawable.ic_launcher);

                        UiConfig uiConfig = new UiConfig();
                        uiConfig.setUiType(UiType.CUSTOM);
                        uiConfig.setCustomLayoutId(R.layout.view_update_dialog_custom);

                        UpdateAppUtils
                                .getInstance()
                                .apkUrl(apkUrl)
                                .updateTitle("衣蝠发新版啦~")
                                .updateContent(result.getMsg() + "")
                                .updateConfig(updateConfig)
                                .uiConfig(uiConfig)
                                .setOnInitUiListener(new OnInitUiListener() {
                                    @Override
                                    public void onInitUpdateUi(View view, UpdateConfig updateConfig, UiConfig uiConfig) {
                                        TextView tv_title1 = view.findViewById(R.id.tv_title1);
                                        tv_title1.setText(result.getVersion_no() + "更新了以下内容：");
                                    }
                                })
                                .setCancelBtnClickListener(new OnBtnClickListener() {
                                    @Override
                                    public boolean onClick() {
                                        onNoUpdateListener.cancel();
                                        return false;
                                    }
                                })
                                .update();
                        updateDialogShowEd = true;

                    } else {
                        onNoUpdateListener.cancel();
                    }
                }

                @Override
                public void onError() {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}