package com.yssj.service;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.yssj.activity.R;
import com.yssj.ui.dialog.SignShuomingDialog;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ToastUtil;

/**
 * Created by lifeng on 2017/6/19.
 */

public class OrderDlertService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {


//        AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
//        builder.setTitle("Title");
//        builder.setMessage("This is message");
//        builder.setNegativeButton("OK", null);
//        Dialog dialog=builder.create();
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        dialog.show();

        ToastUtil.showShortText(this,"Aservice");
        LogYiFu.e("Aservice","弹出");
//        SignShuomingDialog shuomingDialog = new SignShuomingDialog(this, R.style.DialogStyle1);
        ToastUtil.showMyToast(this,"完成赚钱小任务可再去提现10次，保底提现5-50元秒到微信零钱！",3000);
//		shuomingDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//		shuomingDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//		shuomingDialog.show();




//        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//        AlertDialog dialog=builder.setMessage("pc端断开连接，请及时保存编辑文档!")
//                .setPositiveButton("确定",new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                    }
//                }).create();
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
//        dialog.setCanceledOnTouchOutside(false);//点击屏幕不消失
//        if (!dialog.isShowing()){//此时提示框未显示
//            dialog.show();
//        }

        return super.onStartCommand(intent, flags, startId);
    }
}
