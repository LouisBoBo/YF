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
import android.widget.TextView;

import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ShopCart;
import com.yssj.model.ComModel;
import com.yssj.model.ComModelL;
import com.yssj.model.ComModelZ;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LogYiFu;

import java.util.HashMap;
import java.util.List;

import static com.yssj.ui.fragment.circles.SignFragment.zidongGundongYEW;

/**
 * 赚钱提示
 *
 * @author qingfeng
 */
public class BizuoEwaiWanchengTishiDialog extends Dialog implements View.OnClickListener {
    private TextView tv2;
    private Button gobuy2, liebiao;
    private Context context;
    private ImageView icon_close;
    ImageView im_iv;

    String mTitle = " ";


//    private String count = "";
//    private String money = "";

    public BizuoEwaiWanchengTishiDialog(Context context, int style,String mTitle) {
        super(context, style);
        this.context = context;
        this.mTitle = mTitle;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bizuoewanwancheng_tishi);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        tv2 = (TextView) findViewById(R.id.tv2);
        gobuy2 = (Button) findViewById(R.id.gobuy2);
        liebiao = (Button) findViewById(R.id.liebiao);
        icon_close = (ImageView) findViewById(R.id.icon_close);
        im_iv = (ImageView) findViewById(R.id.im_iv);


        gobuy2.setOnClickListener(this);
        liebiao.setOnClickListener(this);
        icon_close.setOnClickListener(this);

        tv2.setText(mTitle);

//
//        new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) context, R.string.wait) {
//
//            @Override
//            protected HashMap<String, String> doInBackground(FragmentActivity mContext, String... params)
//                    throws Exception {
//
//                return ModQingfeng.getTomoyugaoCount(context);
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity mContext, HashMap<String, String> result, Exception e) {
//
//                if (null != result) {
//                    count = result.get("task_count") + "";
//                    money = result.get("money") + "";
//
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                final HashMap<String, String> m = ComModelZ.getNextDayTaskContent();
//                                ((Activity) context).runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        mTitle = " 恭喜你今天完成了全部任务，明天会更新200个任务，共计200元奖励。记得明天继续来哦。";
//
//                                        if (m != null && m.size() > 0) {
//                                            mTitle = m.get("title")+"";
//
//                                            try {
//                                                mTitle = mTitle.replaceFirst("\\$\\{replace\\}", "" + count);
//                                                mTitle = mTitle.replaceFirst("\\$\\{replace\\}", "" + money);
//
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//
//                                        tv2.setText(mTitle);
//
//
//                                    }
//                                });
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }).start();
//
//
//                }
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//        }.execute();


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


                break;
            case R.id.gobuy2: //赚赚赚


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
//                int finalStatusBarHeight = statusBarHeight2;
//
//                int mY;
//
//                if (YJApplication.instance.isLoginSucess()) {
//                    mY = zidongGundongYEW - DP2SPUtil.dp2px(context, 80) - finalStatusBarHeight;
//                } else {
//                    mY = zidongGundongYEW - DP2SPUtil.dp2px(context, 50) - finalStatusBarHeight;
//                }
//                SignFragment.scollView.getRefreshableView().smoothScrollTo(0, mY);


                dismiss();
                break;
            case R.id.icon_close:
                dismiss();
                break;
        }
    }

}
