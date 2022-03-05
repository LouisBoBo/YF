package com.yssj.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.main.FilterResultActivity;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.main.ForceLookMatchActivity;
import com.yssj.ui.activity.main.SearchResultActivity;
import com.yssj.ui.activity.main.SignActiveShopActivity;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.SignUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ShareMeiyiChuandaCompleteDiaolg extends Dialog implements View.OnClickListener {
    private TextView tv1, tv2, tv3, tv4, title, tv_fenzhong, tv_miao;
    private Button gobuy1, gobuy2, liebiao;
    private RelativeLayout rl_twobt;
    private Context mContext;
    private String jumpFrom;
    private ImageView icon_close;
    private String awards;

    SignUtil.ShareCompleteCallBack shareCompleteCallBack;


    private LinearLayout ll_dojishi;


    public ShareMeiyiChuandaCompleteDiaolg(Context context, int style, String jumpFrom, SignUtil.ShareCompleteCallBack shareCompleteCallBack) {
        super(context, style);
        this.mContext = context;
        this.jumpFrom = jumpFrom;
        this.shareCompleteCallBack = shareCompleteCallBack;


    }

    public ShareMeiyiChuandaCompleteDiaolg(Context context, int style, String jumpFrom, String awards, SignUtil.ShareCompleteCallBack shareCompleteCallBack) {
        super(context, style);
        this.mContext = context;
        this.jumpFrom = jumpFrom;
        this.awards = awards;

        this.shareCompleteCallBack = shareCompleteCallBack;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign_common);


        // overtimer.schedule(overtask, 0, 1000); // timeTask

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);

        ll_dojishi = (LinearLayout) findViewById(R.id.ll_dojishi);
        tv_fenzhong = (TextView) findViewById(R.id.tv_fenzhong);
        tv_miao = (TextView) findViewById(R.id.tv_miao);

        title = (TextView) findViewById(R.id.title);

        gobuy1 = (Button) findViewById(R.id.gobuy1); // 一个按钮时的按钮
        gobuy2 = (Button) findViewById(R.id.gobuy2);
        liebiao = (Button) findViewById(R.id.liebiao);
        icon_close = (ImageView) findViewById(R.id.icon_close);

        rl_twobt = (RelativeLayout) findViewById(R.id.rl_twobt); // 两个按钮

        gobuy1.setOnClickListener(this);
        gobuy2.setOnClickListener(this);
        liebiao.setOnClickListener(this);
        icon_close.setOnClickListener(this);

        SharedPreferencesUtil.saveBooleanData(mContext, "sharemeiyichuandaback", false);


        initData();

    }

    private void initData() {


        if (jumpFrom.equals("share_sign_finish")) {


            title.setText("任务完成！");

            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);

            tv1.setText("分享成功~");

            tv1.setTextSize(16);
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            tv1.setVisibility(View.VISIBLE);

            String textAward = awards + "奖励已经存入账户，赶紧去买买买吧~";
            SpannableString ssTextAward = new SpannableString(textAward);
            ssTextAward.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0, awards.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv2.setText(ssTextAward);
            tv2.post(new Runnable() {
                @Override
                public void run() {
                    if (tv2.getLineCount() == 1) {
                        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            tv2.setVisibility(View.VISIBLE);

        }


        if (jumpFrom.equals("share_sign_fanbei_finish")) { // 余额翻倍

            title.setText("任务提示");
            tv1.setTextSize(16);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText("你的余额已经翻倍了哦~");
            tv2.setVisibility(View.VISIBLE);
            tv2.setText("余额翻倍购买衣服更实惠啦！");
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.VISIBLE);
            tv4.setText("余额翻倍有效期只有24小时喔，快来买买买吧~");


        }
        if (jumpFrom.equals("share_sign_jinbi_finish")) { // 升级金币

            title.setText("任务完成！");
            tv1.setTextSize(16);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText("积分已经升级为金币了哦~");
            tv2.setVisibility(View.VISIBLE);
            tv2.setText("金币购买衣服更实惠啦！");
            tv3.setVisibility(View.VISIBLE);
            tv3.setText("例如：500积分可用于衣服满5.01元的订单消费");
            tv4.setVisibility(View.VISIBLE);
            tv4.setText("金币有效期只有24小时喔，快来买买买吧~");

        }
        if (jumpFrom.equals("share_sign_jinquan_finish")) { // 升级金券

            title.setText("任务完成！");
            tv1.setTextSize(16);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText("优惠券已经升级为金券了哦~");
            tv2.setVisibility(View.VISIBLE);
            tv2.setText("金券购买衣服更实惠啦！");
            tv3.setVisibility(View.VISIBLE);
            tv3.setText("例如：5元金券可用于衣服满5.01元的订单消费");
            tv4.setVisibility(View.VISIBLE);
            tv4.setText("金券有效期只有24小时喔，快来买买买吧~");


        }


        rl_twobt.setVisibility(View.GONE);
        gobuy1.setVisibility(View.VISIBLE);
        gobuy1.setText("一键做下个任务");

    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.gobuy1: // 去购物----一键做下个任务
                shareCompleteCallBack.clickNext();
                dismiss();
                break;
            case R.id.gobuy2:


                dismiss();
                break;
            case R.id.liebiao:


                dismiss();


                break;
            case R.id.icon_close:
                dismiss();
                break;

            default:
                break;
        }
    }



}
