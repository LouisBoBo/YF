package com.yssj.ui.dialog;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.main.SignGroupShopActivity;
import com.yssj.utils.SharedPreferencesUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OneBuyGroupsResultDialog extends Dialog implements View.OnClickListener {

    @Bind(R.id.iv_ling)
    ImageView ivLing;
    private Context context;
    private int needPopCount;
    private View tvClose;
    private TextView resuleTitleTv, resuleContentTv;
    private ImageView resuleImg;


    public OneBuyGroupsResultDialog(Context context, int needPopCount) {
        super(context);
        setCanceledOnTouchOutside(false);
        this.context = context;
        this.needPopCount = needPopCount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_groups_result_one_buy);

        ButterKnife.bind(this);

        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //在0.0f和1.0f之间，0.0f完全不暗，1.0f全暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.75f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        initView();
        initData();


        initAnim();

    }

    private void initAnim() {


        ObjectAnimator animatorX = ObjectAnimator.ofFloat(ivLing, "scaleX", 1, 1.2f, 1);

        animatorX.setRepeatCount(-1);
//        animatorX.setRepeatMode(ValueAnimator.RESTART);


        ObjectAnimator animatorY = ObjectAnimator.ofFloat(ivLing, "scaleY", 1, 1.2f, 1);
        animatorY.setRepeatCount(-1);
//        animatorY.setRepeatMode(ValueAnimator.RESTART);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.setDuration(1100);
        animatorSet.start();





    }

    private void initData() {
        resuleImg.setImageResource(R.drawable.icon_groups_fail);
        resuleTitleTv.setText("拼团未成功！");


        if (needPopCount > 0) {//人未满，时间过了
            resuleContentTv.setText("很遗憾，你的拼团未能在时效内达到人数，本次拼团未成功。如您已支付拼团费，将会在24小时内原路返回您的付款账户。请注意查收。");

        } else {//人满了时间过了
            resuleContentTv.setText("很遗憾，有团友未能在时效内支付，本次拼团未成功。如您已支付，将会在24小时内原路返回您的付款账户。请注意查收。");

        }


    }

    private void initView() {
        tvClose = findViewById(R.id.icon_close);
        tvClose.setOnClickListener(this);
        resuleContentTv = findViewById(R.id.groups_resule_content);
        resuleTitleTv = findViewById(R.id.result_title);
        resuleImg = findViewById(R.id.result_img);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_close:
                this.dismiss();
                break;
//            case R.id.groups_gift_btn:
//
//                // 跳至赚钱
//                SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
//                Intent intent = new Intent(context, CommonActivity.class);
//                intent.putExtra("isTXListScroll", true);
//                context.startActivity(intent);
//                ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//
//                if (SignGroupShopActivity.instance != null) {
//                    SignGroupShopActivity.instance.finish();
//                }
//                if (CommonActivity.instance != null) {
//                    CommonActivity.instance.finish();
//                }
//                this.dismiss();
//                break;


            default:
                break;
        }

    }

    @OnClick(R.id.iv_ling)
    public void onViewClicked() {


        //                // 跳至赚钱
        SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
        Intent intent = new Intent(context, CommonActivity.class);
        intent.putExtra("isTXListScroll", true);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


        if (SignGroupShopActivity.instance != null) {
            SignGroupShopActivity.instance.finish();
        }
        if (CommonActivity.instance != null) {
            CommonActivity.instance.finish();
        }
        this.dismiss();
    }

    /**
     * 设置未成功时候 赚钱提醒文案
     */
//    private void setGiftContent() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_QDRWYD);
//                    ((Activity) context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            HashMap<String, Object> m = new HashMap<String, Object>();
//                            if (map != null) {
//                                m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_QDRWYD);
//                            }
//                            String text_num = "今日又更新了16个任务，";
//                            String text_yuan = "共计200元现金等你拿哦。";
//                            if (m != null && m.size() > 0) {
//                                String text = (String) m.get("text");
//                                String[] texts = text.split(",");
//                                try {
//                                    text_num = "今日又更新了" + texts[1] + "个任务，";
//                                    text_yuan = "共计" + texts[2] + "元现金等你拿哦。";
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            SpannableString textSpan_num = new SpannableString(text_num);
//                            SpannableString textSpan_yuan = new SpannableString(text_yuan);
//                            textSpan_num.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFF00")),
//                                    6, text_num.length() - 3,
//                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                            textSpan_num.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
//                                    6, text_num.length() - 3,
//                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                            textSpan_yuan.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFF00")),
//                                    2, text_yuan.length() - 7,
//                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                            textSpan_yuan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
//                                    2, text_yuan.length() - 7,
//                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }


}
