package com.yssj.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.yssj.Json;
import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.model.ComModelL;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.utils.CheckStrUtil;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.SignUtil;
import com.yssj.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

/**
 * 赚钱提示
 *
 * @author qingfeng
 */
public class EWtaskTishiDialog extends Dialog implements View.OnClickListener {
    private TextView tv2, tv3, tv4, title;
    private Button gobuy2, liebiao;
    private Context context;
    private ImageView icon_close;
    ImageView im_iv;

    SignUtil.ShareCompleteCallBack shareCompleteCallBack;



    public EWtaskTishiDialog(Context context, int style,SignUtil.ShareCompleteCallBack shareCompleteCallBack) {
        super(context, style);
        this.context = context;
        this.shareCompleteCallBack = shareCompleteCallBack;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ewtask_tishi);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        gobuy2 = (Button) findViewById(R.id.gobuy2);
        liebiao = (Button) findViewById(R.id.liebiao);
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
            case R.id.liebiao: //我去逛逛
                CommonUtils.finishActivity(MainMenuActivity.instances);

                intent2 = new Intent((Activity) context, MainMenuActivity.class);
                intent2.putExtra("toYf", "toYf");
                context.startActivity(intent2);
                dismiss();

                break;
            case R.id.gobuy2: //一键完成下个任务

                shareCompleteCallBack.clickNext();


                dismiss();
                break;
            case R.id.icon_close:
                dismiss();
                break;
        }
    }

}
