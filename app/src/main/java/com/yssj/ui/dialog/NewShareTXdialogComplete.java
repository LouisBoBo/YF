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

import com.yssj.activity.R;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SignUtil;
import com.yssj.utils.ToastUtil;

/**
 * 赚钱提示
 *
 * @author qingfeng
 */
public class NewShareTXdialogComplete extends Dialog implements View.OnClickListener {
    private TextView tv2, tv3, tv4, title;
    private Button gobuy2;
    private Context context;
    private ImageView icon_close;
    ImageView im_iv;




    public NewShareTXdialogComplete(Context context, int style ) {
        super(context, style);
        this.context = context;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_share_tx);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        LogYiFu.e("222222222222","22222222222222222222222");


        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        gobuy2 = (Button) findViewById(R.id.gobuy2);
//        liebiao = (Button) findViewById(R.id.liebiao);
        icon_close = (ImageView) findViewById(R.id.icon_close);
        im_iv = (ImageView) findViewById(R.id.im_iv);





        gobuy2.setOnClickListener(this);
//        liebiao.setOnClickListener(this);
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
            case R.id.liebiao: //我去逛逛

                intent2 = new Intent((Activity) context, MainMenuActivity.class);
                intent2.putExtra("toYf", "toYf");
                context.startActivity(intent2);
                dismiss();

                break;
            case R.id.gobuy2: //继续分享

                ToastUtil.showDialog(new NewShareGetTXDialog((Activity)context, context, R.style.DialogStyle1,
                        5));

                dismiss();
                break;
            case R.id.icon_close:
                dismiss();
                break;
        }
    }

}
