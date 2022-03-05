package com.yssj.ui.dialog;

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
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.model.ComModelL;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.infos.MyWalletActivity;
import com.yssj.ui.activity.main.SignGroupShopActivity;
import com.yssj.utils.SharedPreferencesUtil;

import java.util.HashMap;

public class IndianaMoneyResultDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private View tvClose;
    private int status;
    private double money;
    private TextView resuleTitleTv, resuleContentTv;
    private ImageView resuleImg;
    private View groups_gift_ll;
    private ImageView indiana_win_pic;
    private TextView content_num, content_yuan, groups_gift_btn;
    private RelativeLayout group_rl_message;
    private LinearLayout group_ll_winner_message;
    private String issue;
    private String winNumber;
    private String winName;
    private  TextView tv_title;
    private TextView tv_win_number;
    private TextView tv_name;

    public IndianaMoneyResultDialog(Context context, int status, double money,String issue,String winNumber,String winName) {
        super(context);
        setCanceledOnTouchOutside(false);
        this.context = context;
        this.status = status;
        this.money = money;
        this.issue=issue;
        this.winNumber=winNumber;
        this.winName=winName;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_indiana_money_result);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //在0.0f和1.0f之间，0.0f完全不暗，1.0f全暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.75f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        initView();
        initData();
    }

    private void initData() {
        if (status == 0) {//马上开奖
            resuleImg.setImageResource(R.drawable.icon_groups_timeout);
            resuleTitleTv.setText("马上开奖！");
            resuleContentTv.setText("请勿离开！你参加的一元抽奖马上开奖~");
            groups_gift_ll.setVisibility(View.GONE);
            indiana_win_pic.setVisibility(View.GONE);
            group_rl_message.setVisibility(View.VISIBLE);
            group_ll_winner_message.setVisibility(View.GONE);
        } else if (status == 1) {//未中奖
//            resuleImg.setImageResource(R.drawable.icon_groups_timeout);
            resuleImg.setVisibility(View.GONE);
            resuleTitleTv.setText("开奖啦！");
//            resuleContentTv.setText(money + "元现金与你擦身而过，下次再接再厉吧！");
//            groups_gift_ll.setVisibility(View.GONE);
            groups_gift_ll.setVisibility(View.GONE);
            indiana_win_pic.setVisibility(View.VISIBLE);
            group_rl_message.setVisibility(View.GONE);
            group_ll_winner_message.setVisibility(View.VISIBLE);
            tv_title.setText("你参与的第"+issue+"期提现抽奖开奖啦！");
            tv_win_number.setText("中奖号码为:"+winNumber);
            tv_name.setText("中奖者:"+winName);
        } else if (status == 2) {//中奖了
//            resuleImg.setImageResource(R.drawable.icon_groups_success);
            resuleImg.setVisibility(View.GONE);
            resuleTitleTv.setText("恭喜你，中奖啦！");
            resuleContentTv.setText(money + "元提现额度已发放至你的账户，快到钱包查看吧！");
            groups_gift_ll.setVisibility(View.VISIBLE);
            indiana_win_pic.setVisibility(View.VISIBLE);
            group_rl_message.setVisibility(View.VISIBLE);
            group_ll_winner_message.setVisibility(View.GONE);
        }


    }

    private void initView() {
        tvClose = findViewById(R.id.icon_close);
        tvClose.setOnClickListener(this);
        resuleContentTv = (TextView) findViewById(R.id.groups_resule_content);
        resuleTitleTv = (TextView) findViewById(R.id.result_title);
        resuleImg = (ImageView) findViewById(R.id.result_img);
        groups_gift_ll = findViewById(R.id.groups_gift_ll);
        groups_gift_ll.setVisibility(View.GONE);
        indiana_win_pic = (ImageView) findViewById(R.id.indiana_win_pic);
        content_num = (TextView) findViewById(R.id.groups_gift_content_num);
        content_yuan = (TextView) findViewById(R.id.groups_gift_content_yuan);
        groups_gift_btn = (TextView) findViewById(R.id.groups_gift_btn);
        groups_gift_btn.setOnClickListener(this);
        group_rl_message= (RelativeLayout) findViewById(R.id.group_rl_message);
        group_ll_winner_message= (LinearLayout) findViewById(R.id.group_ll_winner_message);
         tv_title = (TextView) findViewById(R.id.tv_title);
         tv_win_number = (TextView) findViewById(R.id.tv_number);
         tv_name = (TextView) findViewById(R.id.tv_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_close:
                this.dismiss();
                break;
            case R.id.groups_gift_btn:
                Intent intent = new Intent(context, MyWalletActivity.class);
                context.startActivity(intent);
                ((FragmentActivity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                if (context != null) {
                    ((Activity) context).finish();
                }
                this.dismiss();
                break;

            default:
                break;
        }

    }




}
