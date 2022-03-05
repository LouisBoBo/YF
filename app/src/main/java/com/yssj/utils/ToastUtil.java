package com.yssj.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.yssj.YJApplication;
import com.yssj.activity.R;

import java.util.Timer;
import java.util.TimerTask;

public class ToastUtil {
    private static final String TAG = "ToastUtil";

//    public static void showShortText(Context context, int resId) {
//        showCustomToast(context, context.getString(resId), Toast.LENGTH_SHORT);
//    }


//    public static void showLongText(Context context, int resId) {
//        showCustomToast(context, context.getString(resId), Toast.LENGTH_LONG);
//    }

    //短Toast
    public static void showShortText(Context context, CharSequence text) {

        if((text+"").indexOf("再分享") != -1){
            showMyToast(context,text+"",4000);
            return;
        }

        CenterToast.centerToast(context, (String) text);
    }

    //短Toast
    public static void showShortText2(CharSequence text) {
        CenterToast.centerToast(YJApplication.mContext, (String) text);
    }


    //长Toast
    public static void showLongText(Context context, CharSequence text) {
        CenterToast.centerLongToast(context, (String) text);
    }

    private static void showCustomToast(Context context, CharSequence text,
                                        int duration) {
        if (text != null && text.length() > 0) {
            Toast.makeText(context, text, duration).show();
        } else {
            LogYiFu.e(TAG, "Toast内容为空。�?��??");
        }
    }

    //自定义停留时间的的Toast
    public static void showMyToast(Context context, String msg, long l) {
        final Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.toast_custom, null);
        LinearLayout toastLayout = (LinearLayout) view;


        TextView txtToast = (TextView) toastLayout.findViewById(R.id.txt_toast);
        txtToast.getBackground().setAlpha(204);
        txtToast.setText(msg);
        toast.setView(toastLayout);


        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, l);

    }

    public static void showMyToastProgress(Context context, String msg, long l) {

        showMyToast(context,msg,l);





//        final Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.toast_custom_progress, null);
//        LinearLayout toastLayout = (LinearLayout) view;
//        toastLayout.findViewById(R.id.container).getBackground().setAlpha(204);
//
//        TextView text_progress = (TextView) toastLayout.findViewById(R.id.text_progress);
//        text_progress.setText(msg);
//        toast.setView(toastLayout);
////
//
//
//        ProgressBar progressBar =  toastLayout.findViewById(R.id.spin_kit);
//        Sprite fadingCircle = new FadingCircle();
//        progressBar.setIndeterminateDrawable(fadingCircle);
//
//        final Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                toast.show();
//            }
//        }, 0, 3000);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                toast.cancel();
//                timer.cancel();
//            }
//        }, l);

    }

//    public Dialog getLoadDialog(Context context, String msg) {
//
//        LayoutInflater mInflater = LayoutInflater.from(context);
//        final Dialog loadDialog = new Dialog(context, R.style.invate_dialog_style);
//        View view = mInflater.inflate(R.layout.toast_custom_progress, null);
//        LinearLayout toastLayout = (LinearLayout) view;
//        toastLayout.findViewById(R.id.container).getBackground().setAlpha(204);
//        TextView text_progress = (TextView) toastLayout.findViewById(R.id.text_progress);
//        text_progress.setText(msg);
//        ProgressBar progressBar = (ProgressBar) toastLayout.findViewById(R.id.spin_kit);
//        Sprite fadingCircle = new FadingCircle();
//        progressBar.setIndeterminateDrawable(fadingCircle);
//
//
//        loadDialog.setCanceledOnTouchOutside(true);
//        loadDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));
//
//        return loadDialog;
//
//
//    }


    //本APP通用dialog弹出
    public static void showDialog(Dialog diolog) {
        //在dialog show之前判断一下
        if (!getActivity(diolog.getContext()).isFinishing()) {
            diolog.getWindow().setWindowAnimations(R.style.common_dialog_style);
            diolog.show();
        }
    }


    private static Activity getActivity(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof Activity) {
            return (Activity) context;
        } else
            return null;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 文字颜色
     */
    public static void addForeColorSpan(TextView tv, int color, String str) {
        SpannableString spanString = new SpannableString(str);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        spanString.setSpan(span, 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(spanString);
    }


    /**
     * 粗体，斜体
     */
    public static void addStyleSpan(TextView tv, String str) {
        SpannableString spanString = new SpannableString(str);
        StyleSpan span = new StyleSpan(Typeface.BOLD_ITALIC);
        spanString.setSpan(span, 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(spanString);
    }


    /**
     * 删除线
     */
    public static void addStrikeSpan(TextView tv, String str) {
        SpannableString spanString = new SpannableString(str);
        StrikethroughSpan span = new StrikethroughSpan();
        spanString.setSpan(span, 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//	    tv.append(spanString);  
        tv.setText(spanString);
    }

    /**
     * 下划线
     */
    public static void addUnderLineSpan(TextView tv, String str) {
        SpannableString spanString = new SpannableString("下划线");
        UnderlineSpan span = new UnderlineSpan();
        spanString.setSpan(span, 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(spanString);
    }


}
