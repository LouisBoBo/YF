package com.yssj.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.SignUtil;

import java.util.HashMap;
import java.util.List;

import static com.yssj.ui.fragment.circles.SignFragment.zidongGundongYEW;

/**
 * 赚钱提示
 *
 * @author qingfeng
 */
public class TiXianWanchengTishiDialog extends Dialog implements View.OnClickListener {
    private TextView tv2, tv3, tv4, title;
    private Button gobuy2, liebiao;
    private Context context;
    private ImageView icon_close;
    ImageView im_iv;

    SignUtil.ShareCompleteCallBack shareCompleteCallBack;



    public TiXianWanchengTishiDialog(Context context, int style, SignUtil.ShareCompleteCallBack shareCompleteCallBack) {
        super(context, style);
        this.context = context;
        this.shareCompleteCallBack = shareCompleteCallBack;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tixianwancheng_tishi);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        gobuy2 = (Button) findViewById(R.id.gobuy2);//一键做下个任务
        liebiao = (Button) findViewById(R.id.liebiao);//赚赚赚
        icon_close = (ImageView) findViewById(R.id.icon_close);
        im_iv = (ImageView) findViewById(R.id.im_iv);





        gobuy2.setOnClickListener(this);
        liebiao.setOnClickListener(this);
        icon_close.setOnClickListener(this);






    }




    /**
     * 设置未成功时候 赚钱提醒文案
     */



    public static int zidongGundongY = 0;

    @Override
    public void onClick(View v) {
        Intent intent2;
        switch (v.getId()) {
            case R.id.liebiao: //赚赚赚

                //获取状态栏高度
                int statusBarHeight2 = -1;
                try {
                    Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                    Object object = clazz.newInstance();
                    int height = Integer.parseInt(clazz.getField("status_bar_height")
                            .get(object).toString());
                    statusBarHeight2 = context.getResources().getDimensionPixelSize(height);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }

                int finalStatusBarHeight = statusBarHeight2;

                int mY;

                if (YJApplication.instance.isLoginSucess()) {
                    mY = zidongGundongYEW - DP2SPUtil.dp2px(context, 80) - finalStatusBarHeight;
                } else {
                    mY = zidongGundongYEW - DP2SPUtil.dp2px(context, 50) - finalStatusBarHeight;
                }
//                SignFragment.scollView.getRefreshableView().smoothScrollTo(0, mY);

                dismiss();


                break;
            case R.id.gobuy2: //一键做下个任务


                shareCompleteCallBack.clickNext();



                dismiss();
                break;
            case R.id.icon_close:
                dismiss();
                break;
        }
    }

}
