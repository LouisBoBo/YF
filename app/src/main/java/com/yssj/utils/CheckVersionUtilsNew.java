package com.yssj.utils;

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
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel2;
import com.yssj.service.ApkDownloadManager;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/15.
 */

public class CheckVersionUtilsNew {
    public static boolean remind = false;

    public static void checkVersion(View v,final Context mContext) {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
//            int versionCode = pi.versionCode;
            final String versionName = "v" + pi.versionName;
            new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mContext, v, 0) {

                @Override
                protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
                        throws Exception {
                    HashMap<String, String> mapRet = ComModel2.checkVersion(mContext);
                    return mapRet;
                }

                protected boolean isHandleException() {
                    return true;
                };

                protected void onPostExecute(FragmentActivity context, final HashMap<String, String> result,
                                             Exception e) {
                    if (null == e) {
                        if (StringUtils.isDownload(result.get("version_no"), versionName)) {
                            mContext.getSharedPreferences("tocao_isupdate", Context.MODE_PRIVATE).edit()
                                    .putBoolean("tocao_isupdate", true).commit();























                            final Dialog dialog = new Dialog(mContext, R.style.invate_dialog_style);
                            View view = View.inflate(mContext, R.layout.is_force_update_dialog, null);
                            TextView tv_version = (TextView) view.findViewById(R.id.tv_version);
                            tv_version.setText("" + result.get("version_no") + "????????????????????????");
                            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
                            String content = result.get("msg");
                            LogYiFu.e("ssss", content);
                            tv_content.setText(content);
                            Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
                            final ImageView iv_remind = (ImageView) view.findViewById(R.id.iv_remind);
                            LinearLayout ll_notice = (LinearLayout) view.findViewById(R.id.ll_notice);
                            iv_remind.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    // TODO Auto-generated method stub
                                    remind = !remind;
                                    if (remind) {
                                        iv_remind.setBackgroundResource(R.drawable.remind_cel);
                                    } else {
                                        iv_remind.setBackgroundResource(R.drawable.remind_nor);
                                    }
                                }
                            });
                            btn_cancel.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    dialog.dismiss();
                                    if (result.get("is_update").equals("1")) {// ????????????
                                        AppManager.getAppManager().finishAllActivity();
                                    } else {
                                        SharedPreferencesUtil.saveBooleanData(mContext, YConstance.Pref.REMIND_IS_SHOW, remind);
                                        SharedPreferencesUtil.saveStringData(mContext, YConstance.Pref.RECORD_VERSION,
                                                "" + result.get("version_no"));
                                        String imei = CheckStrUtil.getImei(mContext);
                                        if (imei != null && ComModel2.flag == 0) {
                                            new Thread() {
                                                public void run() {

                                                    try {
                                                        Thread.sleep(5000);// 5???
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
//                                                    isNewMemberTask_1 = 1;
//                                                    handlerCheckVersion.sendEmptyMessage(0);
                                                };
                                            }.start();

                                        } else if (YJApplication.instance.isLoginSucess() == false) {
                                            new Thread() {
                                                public void run() {

                                                    try {
                                                        Thread.sleep(5000);// 5???
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
//                                                    isLoginNewTask = 1;
//                                                    handlerCheckVersion.sendEmptyMessage(2);
                                                };
                                            }.start();
                                        } else {
                                            UserInfo userInfo = YCache.getCacheUserSafe(mContext);
                                            if (null == userInfo) {
                                                return;
                                            }
                                            if (null == userInfo.getHobby() || userInfo.getHobby().equals("0")) {

                                                new Thread() {
                                                    public void run() {

                                                        try {
                                                            Thread.sleep(5000);// 5???
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
//                                                        isNewShop = 1;
//                                                        handlerCheckVersion.sendEmptyMessage(3);
                                                    };
                                                }.start();

                                            } else {
                                                new Thread() {
                                                    public void run() {

                                                        try {
                                                            Thread.sleep(5000);// 5???
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
//                                                        isEverydayShareTask = 1;
//                                                        handlerCheckVersion.sendEmptyMessage(2);
                                                    };
                                                }.start();
                                            }
                                        }
                                    }
                                }
                            });

                            Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
                            btn_ok.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    dialog.dismiss();
                                    ApkDownloadManager UpgradeApk = new ApkDownloadManager((FragmentActivity) mContext);
                                    String downLodurl = YUrl.imgurl + result.get("path");
                                    UpgradeApk.downloadUpgradeApk(YUrl.imgurl + result.get("path"));

                                    String imei = CheckStrUtil.getImei(mContext);
                                    if (imei != null && ComModel2.flag == 0) {
                                        new Thread() {
                                            public void run() {

                                                try {
                                                    Thread.sleep(5000);// 5???
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
//                                                isNewMemberTask_1 = 1;
//                                                handlerCheckVersion.sendEmptyMessage(0);
                                            };
                                        }.start();

                                    } else if (YJApplication.instance.isLoginSucess() == false) {
                                        new Thread() {
                                            public void run() {

                                                try {
                                                    Thread.sleep(5000);// 5???
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
//                                                isLoginNewTask = 1;
//                                                handlerCheckVersion.sendEmptyMessage(1);
                                            };
                                        }.start();
                                    } else {
                                        UserInfo userInfo = YCache.getCacheUserSafe(mContext);
                                        if (null == userInfo) {
                                            return;
                                        }
                                        if (null == userInfo.getHobby() || userInfo.getHobby().equals("0")) {

                                            new Thread() {
                                                public void run() {

                                                    try {
                                                        Thread.sleep(5000);// 5???
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
//                                                    isNewShop = 1;
//                                                    handlerCheckVersion.sendEmptyMessage(3);
                                                };
                                            }.start();

                                        } else {
                                            new Thread() {
                                                public void run() {

                                                    try {
                                                        Thread.sleep(5000);// 30???
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
//                                                    isEverydayShareTask = 1;
//                                                    handlerCheckVersion.sendEmptyMessage(2);
                                                };
                                            }.start();
                                        }
                                    }

                                    if (result.get("is_update").equals("1")) {// ????????????

                                        Intent intent = new Intent(Intent.ACTION_MAIN);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addCategory(Intent.CATEGORY_HOME);
                                        mContext.startActivity(intent);

                                    }
                                }
                            });

                            // ?????????????????????dialog
                            dialog.setContentView(view, new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
                            dialog.setCancelable(false);

                            if (result.get("is_update").equals("1")) {// ????????????
                                btn_cancel.setVisibility(View.GONE);
                                ll_notice.setVisibility(View.GONE);
                                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface arg0) {

                                        // mContext.finish();
                                        AppManager.getAppManager().finishAllActivity();
                                    }
                                });
                                dialog.show();
                            } else {// ???????????????
                                if (!YJApplication.instance.getIsShowUpdateDialog()) {
                                    if (!(SharedPreferencesUtil.getStringData(mContext, YConstance.Pref.RECORD_VERSION, "")
                                            .equals("" + result.get("version_no")))) {// ?????????????????????????????????????????????????????????????????????????????????
                                        dialog.show();
                                        SharedPreferencesUtil.saveStringData(mContext, YConstance.Pref.UPDATE_TIME,
                                                "" + (System.currentTimeMillis() + 72 * 60 * 60 * 1000));// ?????????????????????
                                        SharedPreferencesUtil.saveBooleanData(mContext, YConstance.Pref.REMIND_IS_SHOW, false);// ?????????????????????????????????
                                    } else {// ???????????????????????????????????????????????????????????????????????????????????????????????????
                                        if (Long.parseLong(SharedPreferencesUtil.getStringData(mContext,
                                                YConstance.Pref.UPDATE_TIME, "" + 0)) < System.currentTimeMillis()
                                                && !SharedPreferencesUtil.getBooleanData(mContext, YConstance.Pref.REMIND_IS_SHOW,
                                                false)) {// ??????????????????????????????????????????????????????????????????
                                            dialog.show();
                                            SharedPreferencesUtil.saveStringData(mContext, YConstance.Pref.UPDATE_TIME,
                                                    "" + (System.currentTimeMillis() + 72 * 60 * 60 * 1000));
                                        }
                                    }
                                    YJApplication.instance.isShowUpdateDialog(true);
                                }
                            }

                        } else {
                            String imei = CheckStrUtil.getImei(mContext);
                            if (imei != null && ComModel2.flag == 0) {
                                new Thread() {
                                    public void run() {

                                        try {
                                            Thread.sleep(5000);// 5???
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
//                                        isNewMemberTask_1 = 1;
//                                        handlerCheckVersion.sendEmptyMessage(0);
                                    };
                                }.start();

                            } else if (YJApplication.instance.isLoginSucess() == false) {
                                new Thread() {
                                    public void run() {

                                        try {
                                            Thread.sleep(5000);// 5???
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
//                                        isLoginNewTask = 1;
//                                        handlerCheckVersion.sendEmptyMessage(1);
                                    };
                                }.start();
                            } else {
                                UserInfo userInfo = YCache.getCacheUserSafe(mContext);
                                if (null == userInfo) {
                                    return;
                                }
                                if (null == userInfo.getHobby() || userInfo.getHobby().equals("0")) {

                                    new Thread() {
                                        public void run() {

                                            try {
                                                Thread.sleep(5000);// 5???
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
//                                            isNewShop = 1;
//                                            handlerCheckVersion.sendEmptyMessage(3);
                                        };
                                    }.start();

                                } else {
                                    new Thread() {
                                        public void run() {

                                            try {
                                                Thread.sleep(5000);// 30???
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
//                                            isEverydayShareTask = 1;
//                                            handlerCheckVersion.sendEmptyMessage(2);
                                        };
                                    }.start();
                                }
                            }
                        }
                    }
                };

            }.execute((Void[]) null);
        } catch (PackageManager.NameNotFoundException e) {
            String imei = CheckStrUtil.getImei(mContext);
            if (imei != null && ComModel2.flag == 0) {
                new Thread() {
                    public void run() {

                        try {
                            Thread.sleep(5000);// 5???
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        isNewMemberTask_1 = 1;
//                        handlerCheckVersion.sendEmptyMessage(0);
                    };
                }.start();

            } else {
                new Thread() {
                    public void run() {

                        try {
                            Thread.sleep(5000);// 5???
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        isEverydayShareTask = 1;
//                        handlerCheckVersion.sendEmptyMessage(2);
                    };
                }.start();
            }
        }

    }


}
