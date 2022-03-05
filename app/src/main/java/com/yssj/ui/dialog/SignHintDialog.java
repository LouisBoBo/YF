package com.yssj.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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
import com.yssj.YUrlQingfeng;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelL;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.fragment.MyCanyuFragment;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.utils.CheckStrUtil;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.yssj.Json.retInfo;
import static com.yssj.activity.R.id.imageView;

/**
 * 赚钱提示
 *
 * @author qingfeng
 */
public class SignHintDialog extends Dialog implements View.OnClickListener {
    private TextView tv1, tv2, tv3, tv4, title;
    private Button gobuy2, liebiao;
    private Context context;
    private ImageView icon_close;
    ImageView im_iv;
    double h5Moneny;


    public SignHintDialog(Context context, int style, double h5Moneny) {
        super(context, style);
        this.context = context;
        this.h5Moneny = h5Moneny;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign_commom_sign_hint);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        gobuy2 = (Button) findViewById(R.id.gobuy2);
        liebiao = (Button) findViewById(R.id.liebiao);
        icon_close = (ImageView) findViewById(R.id.icon_close);
        im_iv = (ImageView) findViewById(R.id.im_iv);

        tv1.setText("你完成"+h5Moneny+"元的任务奖金已放入余额。");
        if(h5Moneny > 0 ){
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.GONE);
        }else{
            tv1.setVisibility(View.GONE);
            tv2.setVisibility(View.VISIBLE);

        }


//        String gifUrl = YUrlQingfeng.GETCHECKGIF;
//        String phone = "13333333333";
//        String url = "";
//        try {
//            url = ComModelL.getPhoneCode(context, phone, CheckStrUtil.getImei(context));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        int rd = new Random().nextInt(90000000) + 1;
//
//
//        im_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getPicture();
//            }
//        });


        gobuy2.setOnClickListener(this);
        liebiao.setOnClickListener(this);
        icon_close.setOnClickListener(this);

//        setGiftContent();
        getZeroCount();
    }


    // 这个方法其实只为获取网络图片（夺宝规则）的宽高，以达到屏幕适配(有待优化代码)
    private byte[] picBytes;

    public void getPicture() {
        new Thread(runnables).start();
    }

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 99) {
                if (picBytes != null) {
                    int rd = new Random().nextInt(90000000) + 1;
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
//                    mIvRule.getLayoutParams().height = width * bitmap.getHeight() / bitmap.getWidth();
//                    mIvRule.setImageBitmap(bitmap);
                    if ("image/gif".equals(type)) {
                        Glide.with(context)
                                .load(picBytes)
                                .asGif()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE).signature(new StringSignature(rd + "")).into(im_iv);
                    } else if ("image/png".equals(type)) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(picBytes, 0, picBytes.length);
                        im_iv.setImageBitmap(bitmap);
                    } else {
                        String str = picBytes.toString();
                        JSONObject j = null;
                        try {
                            j = new JSONObject(new String(picBytes));
                            String message3 = j.has("message") ? j.getString(Json.RetInfo.message) : "";
                            ToastUtil.showMyToast(context, message3, 1000);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }
            }
        }
    };
    private String type;
    Runnable runnables = new Runnable() {
        @Override
        public void run() {
            try {


                String phone = "13333333333";
                String mUrl = "";
                try {
                    mUrl = ComModelL.getPhoneCode(context, phone, CheckStrUtil.getImei(context));
                } catch (Exception e) {
                    e.printStackTrace();
                }


                URL url = new URL(mUrl);
//                URL url = new URL("http://www.52yifu.wang/cloud-api/vcode/getVcode?version=V1.31&phone=13333333333&imei=866479024413139");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(10000);

                if (conn.getResponseCode() == 200) {
                    InputStream fis = conn.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int length = -1;
                    while ((length = fis.read(bytes)) != -1) {
                        bos.write(bytes, 0, length);
                    }
                    picBytes = bos.toByteArray();
                    bos.close();
                    fis.close();

                    Message message = new Message();
                    message.what = 99;
                    handle.sendMessage(message);
                    type = conn.getContentType();

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    /**
     * 赚钱任务弹窗0元购文案返现金额
     */
    private void getZeroCount(){
        new SAsyncTask<String, Void, HashMap<String, Object>>((FragmentActivity) context, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ComModelL.getZeroCount(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e&&result!=null&&"1".equals(result.get("status"))) {
                    setGiftContent((Integer) result.get("data"));
                }else{
                    setGiftContent(0);
                }
            }

        }.execute();
    }

    /**
     * 设置未成功时候 赚钱提醒文案
     */
    private void setGiftContent(final int moneyZeroCount) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_QDRWYD);
//                    ((Activity) context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            String text_num = "今天又更新了16个任务，";
//                            String text_yuan = "共计200元现金等你拿哦。";
//
//                            try {
//                                HashMap<String, Object> m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_QDRWYD);
//                                if (m != null && m.size() > 0) {
//                                    String text = (String) m.get("text");
//                                    String[] texts = text.split(",");
//                                    try {
//                                        text_num = "今日又更新了" + texts[0] + "个任务，";
//                                        text_yuan = "共计" + texts[1] + "元现金等你拿哦。";
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            tv2.setText(text_num);
//                            tv3.setText(text_yuan);
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_QDRWYD);
                    final HashMap<String, Object> m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_QDRWYD);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String title1 = "今日更新了X个任务，共计XX元奖励。";
                            String title = "XX元购衣款已返，完成任务可提现。";
                            if (m != null && m.size() > 0) {
                                String text = (String) m.get("text");
                                title1 = (String) m.get("title1");
                                title = (String) m.get("title");
                                String[] texts = text.split(",");
                                try {
//                                    textContent = "今日又更新了" + texts[0] + "个任务，\n共计" + texts[1] + "元现金等你拿哦。";
//                                    title = title.replaceFirst("\\$\\{replace\\}", "" + texts[0]);
//                                    title = title.replaceFirst("\\$\\{replace\\}", ""+texts[1]);
                                    title = title.replaceFirst("\\$\\{replace\\}", "" + moneyZeroCount);
                                    title1 = title1.replaceFirst("\\$\\{replace\\}", "" + texts[1]);
                                    title1 = title1.replaceFirst("\\$\\{replace\\}", "" + texts[2]);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if(moneyZeroCount>0){
                                tv2.setVisibility(View.VISIBLE);
                                tv2.setText(title);
                            }else{
                                tv2.setVisibility(View.GONE);
                            }

                            tv3.setText(title1);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }


    public static int zidongGundongY = 0;

    @Override
    public void onClick(View v) {
        Intent intent2;
        switch (v.getId()) {
            case R.id.liebiao: //我去逛逛（(改为去做任务）

//                intent2 = new Intent((Activity) context, MainMenuActivity.class);
//                intent2.putExtra("toYf", "toYf");
//                context.startActivity(intent2);
                dismiss();
                // 跳至赚钱
//                SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
//                context.startActivity(new Intent(context, CommonActivity.class));
//                ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                break;
            case R.id.gobuy2: //知道了(改为去去0元购衣 跳转首页)
                dismiss();


                if (!SharedPreferencesUtil.getBooleanData(context, YCache.getCacheUser(context) + "zhuanqianyindaogoto0yuantanchuangishow", false)) {
                    ToastUtil.showDialog(new LingYUANGOUTishiDialog(context, R.style.DialogStyle1));
                    SharedPreferencesUtil.saveBooleanData(context, YCache.getCacheUser(context) + "zhuanqianyindaogoto0yuantanchuangishow", true);
                } else {
                    CommonUtils.finishActivity(MainMenuActivity.instances);

                    intent2 = new Intent((Activity) context, MainMenuActivity.class);
                    intent2.putExtra("toYf", "toYf");
                    context.startActivity(intent2);
                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                }

//                if (!SignFragment.hasTXtask) {
//                    //没有惊喜提现任务的话，直接关掉
//                    dismiss();
//                    return;
//                }
//                //获取状态栏高度
//                int statusBarHeight2 = -1;
//                try {
//                    Class<?> clazz = Class.forName("com.android.internal.R$dimen");
//                    Object object = clazz.newInstance();
//                    int height = Integer.parseInt(clazz.getField("status_bar_height")
//                            .get(object).toString());
//                    statusBarHeight2 = context.getResources().getDimensionPixelSize(height);
//                } catch (Exception ee) {
//                    ee.printStackTrace();
//                }
//
//                //将惊喜提现任务自动滚动到顶步
//                int finalStatusBarHeight = statusBarHeight2;
//
//
//                int[] location = new int[2];
//                SignFragment.rlEwai.getLocationOnScreen(location);
//                int x = location[0];
//                int y = location[1];
//
//
//
//                if(zidongGundongY == 0){
//                    zidongGundongY = y;
//                }
//
//                int mY ;
//
//                if (YJApplication.instance.isLoginSucess()) {
//                    mY = zidongGundongY - DP2SPUtil.dp2px(context, 80) - finalStatusBarHeight;
//                } else {
//                    mY = zidongGundongY - DP2SPUtil.dp2px(context, 50) - finalStatusBarHeight;
//                }
//                SignFragment.scollView.getRefreshableView().smoothScrollTo(x, Math.abs(mY));


                break;
            case R.id.icon_close:
                dismiss();
                break;
        }
    }

}
