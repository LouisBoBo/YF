package com.yssj.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.WindowManager;

import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.entity.VipInfo;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.base.BasicActivity;

import java.text.ParseException;
import java.util.HashMap;

/**
 * Created by Administrator on 2020/5/18.
 */

public class CommonUtils {


    public static final int DELAY = 2000;
    private static long lastClickTime = 0;

    //防止多次点击（比如下单按钮，默认2秒内只能点击1次）
    public static boolean isNotFastClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > DELAY) {
            lastClickTime = currentTime;
            return true;
        } else {
            return false;
        }
    }

    public static void finishActivity(Activity activity) {
        if (null != activity) {
            activity.finish();
        }

    }

    //判断是否是会员（不能仅仅用isVip）
    public static boolean isVip(int isVip, int maxType) {
        return (isVip > 0 && isVip != 3)
                ||
                (isVip == 3 && maxType == 4);
    }

    /**
     * 禁用截屏,先查询vip，是会员就吧这个activity禁用截屏
     *
     * @param activity
     */
    public static void disableScreenshots(final Activity activity) {
//        if (YJApplication.instance.isLoginSucess()) {
//            HashMap<String, String> pairsMap = new HashMap<>();
//            YConn.httpPost(activity, YUrl.QUERY_VIP_INFO_FL, pairsMap
//                    , new HttpListener<VipInfo>() {
//                        @Override
//                        public void onSuccess(VipInfo vipInfo) {
//                            if (isVip(vipInfo.getIsVip(), vipInfo.getMaxType())) {
//                                if (!isActivityDestroy(activity)) {
                                    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

//                                    if (YUrl.debug) {
//                                        ToastUtil.showShortText2("此界面截屏已被禁用");
//                                    }
//
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onError() {
//
//                        }
//                    });
//        }
    }


    public static void disableScreenshotsSign(final Activity activity) throws ParseException {
        if (YJApplication.instance.isLoginSucess()) {

            boolean zcTimeOver1hour = false;
            long addData = Long.parseLong(DateUtil.dateToStamp(YCache.getCacheUser(activity).getAdd_date()));
//            if (YUrl.debug) {
//                ToastUtil.showShortText2("注册距现在：" + DateUtil.FormatMilliseondToEndTime(System.currentTimeMillis() - addData));
//
//            }

            int minutes = 60;
            if (YUrl.debug) {
                minutes = 5;
            }

            if (System.currentTimeMillis() - addData > 1000 * 60 * minutes) { //如果注册时间少于1小时就先不禁用截图
                zcTimeOver1hour = true;
            }


            HashMap<String, String> pairsMap = new HashMap<>();
            final boolean finalZcTimeOver1hour = zcTimeOver1hour;
            YConn.httpPost(activity, YUrl.QUERY_VIP_INFO_FL, pairsMap
                    , new HttpListener<VipInfo>() {
                        @Override
                        public void onSuccess(VipInfo vipInfo) {
                            if (isVip(vipInfo.getIsVip(), vipInfo.getMaxType()) && finalZcTimeOver1hour) {
                                if (!isActivityDestroy(activity)) {
                                    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
//                                    if (YUrl.debug) {
//                                        ToastUtil.showShortText2("此界面截屏已被禁用");
//                                    }
                                }
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }
    }

    public interface GetVipListener {
        void callBack(boolean isVip, int maxType);
    }
//    public GetVipListener getVipListener;
    public static void getVip(final Context mContext, final GetVipListener getVipListener) {
        YConn.httpPost(mContext, YUrl.QUERY_VIP_INFO_FL, new HashMap<String, String>()
                , new HttpListener<VipInfo>() {
                    @Override
                    public void onSuccess(VipInfo vipInfo) {
                        getVipListener.callBack(isVip(vipInfo.getIsVip(), vipInfo.getMaxType()), vipInfo.getMaxType());
                    }

                    @Override
                    public void onError() {
                    }
                });
    }


    /**
     * 获取剪切板内容
     *
     * @return
     */
    public static String getClipboardContent(Context context) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {
                CharSequence addedText = manager.getPrimaryClip().getItemAt(0).getText();
                String addedTextString = String.valueOf(addedText);
                if (!TextUtils.isEmpty(addedTextString)) {
                    return addedTextString;
                }
            }
        }
        return "";
    }

    /**
     * 清空剪切板
     */
    public static void clearClipboardContent(Context context) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            try {
                manager.setPrimaryClip(manager.getPrimaryClip());
                manager.setPrimaryClip(ClipData.newPlainText("", ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 判断Activity是否Destroy
     *
     * @return
     */
    public static boolean isActivityDestroy(Activity mActivity) {
        if (mActivity == null || mActivity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }

}
