package com.yssj.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.yssj.activity.R;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.utils.SharedPreferencesUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qingfeng on 2018/5/16.
 */

public class NoTixianEduDialog extends Dialog {

    private Context mContext;

    public NoTixianEduDialog(@NonNull Context context) {
        super(context, R.style.DialogStyle1);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_show_notixianedu);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(true);


    }

    @OnClick({R.id.iv_tosign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_tosign:

                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                mContext.startActivity(new Intent(mContext, CommonActivity.class));
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                dismiss();

                break;
//            case R.id.bt_to_share_day:
//                mContext.startActivity(new Intent(mContext, FriendCommissionActivity.class));
//                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//                dismiss();
//
//                break;
//            case R.id.bt_to_sign:
//                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
//                mContext.startActivity(new Intent(mContext, CommonActivity.class));
//                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//                dismiss();
//
//                break;
//            case R.id.bt_to_homepage:
//                // 跳至首页
//                mContext.startActivity(new Intent(mContext, MainMenuActivity.class)
//                        .putExtra("toHome", "toHome"));
//                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//                dismiss();
//
//                break;
//            case R.id.bt_to_sale://跳至新特卖列表
//
//
//                mContext.startActivity(new Intent(mContext, LifeSaleActivity.class));
//                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//                dismiss();
//                break;
        }


    }
}
