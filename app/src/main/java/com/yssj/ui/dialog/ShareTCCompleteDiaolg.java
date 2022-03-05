package com.yssj.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.utils.SharedPreferencesUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/****
 * 分享提成分享完成提示
 *
 * @author Administrator
 *
 */
public class ShareTCCompleteDiaolg extends Dialog {


    @Bind(R.id.bt_right)
    Button btRight;
    @Bind(R.id.icon_close)
    ImageView iconClose;
    @Bind(R.id.bt_zhuanqian)
    Button btZhuanqian;
    private Context mContext;
    private View ivWx;


    public ShareTCCompleteDiaolg(Context context, int style,View ivWx) {
        super(context, style);
        setCanceledOnTouchOutside(true);
        this.mContext = context;
        this.ivWx = ivWx;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tichengshare_complete);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);


    }


    @OnClick({R.id.bt_right, R.id.icon_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_right:
                //继续分享
                dismiss();
                if(ivWx!=null){
                    ivWx.performClick();
                }
                break;
            case R.id.icon_close:
                dismiss();
                break;
        }
    }
    @OnClick(R.id.bt_zhuanqian)
    public void onViewClicked() {

        if(null != CommonActivity.instance){
            CommonActivity.instance.finish();
        }

        // 跳至赚钱
        SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
        Intent intent = new Intent(mContext, CommonActivity.class);
        intent.putExtra("isTastComplete", true);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


        ((Activity) mContext).finish();


    }




}