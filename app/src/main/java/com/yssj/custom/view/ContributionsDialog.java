package com.yssj.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.yssj.activity.R;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.utils.ToastUtil;

public class ContributionsDialog extends Dialog implements android.view.View.OnClickListener{

    private Context context;

    private int requestCode;

    public ContributionsDialog(Context context) {
        super(context, R.style.my_invate_dialog);
        this.context=context;
        setContentView(R.layout.contributions_dialog);
        this.getWindow().setWindowAnimations(R.style.my_dialog_anim_style);
        findViewById(R.id.donwload).setOnClickListener(this);
    }


    public void setRequestCode(int code){
        this.requestCode=code;
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.donwload:
            {
                ToastUtil.showShortText(context,"该功能近期上线，敬请期待。");
            }
            break;

            default:
                break;
        }
    }

}
