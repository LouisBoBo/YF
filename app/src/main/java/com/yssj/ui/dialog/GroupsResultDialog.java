package com.yssj.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.model.ComModelL;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.main.SignGroupShopActivity;
import com.yssj.utils.SharedPreferencesUtil;

import java.util.HashMap;

public class GroupsResultDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private View tvClose;
    private int status, needs;
    private TextView resuleTitleTv, resuleContentTv;
    private ImageView resuleImg;
    private int endType;//1或2 未参与时候过期1或者人满2   0（默认） 表示其他
    private View groups_gift_ll;
    private TextView content_num, content_yuan, groups_gift_btn;
    private Activity activity;
    private int is_pay;//判断有没有付款

    public GroupsResultDialog(Context context, Activity activity, int status, int needs,int is_pay) {
        super(context);
        setCanceledOnTouchOutside(false);
        this.context = context;
        this.activity = activity;
        this.status = status;
        this.needs = needs;
        this.is_pay = is_pay;
    }

    public GroupsResultDialog(Context context, int endType) {
        super(context);
        setCanceledOnTouchOutside(false);
        this.context = context;
        this.endType = endType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_groups_result);
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
        if (endType == 0) {
            if (status == 6) {
                resuleImg.setImageResource(R.drawable.icon_groups_fail);
                resuleTitleTv.setText("很遗憾，拼团未成功！");
                if(is_pay==0){
                    //自己未付款
                    resuleContentTv.setText("很遗憾，该团你并未在指定时间内付款，本次拼团已失效。谢谢您的参与。");
                }else{
                    resuleContentTv.setText("很遗憾，你的拼团未能在时效内达到付款人数，本次拼团未能成功。拼团费会在5-7个工作日内原路退回至支付账号。谢谢您的参与");
                }

                groups_gift_ll.setVisibility(View.VISIBLE);
                setGiftContent();
            } else if (status == 5) {
                resuleImg.setImageResource(R.drawable.icon_groups_fail);
                resuleTitleTv.setText("很遗憾，拼团未成功！");
                resuleContentTv.setText("很遗憾，你的拼团未能在时效内达到拼团人数，本次拼团未能成功。谢谢您的参与。");
                groups_gift_ll.setVisibility(View.VISIBLE);
                setGiftContent();
            } else if (status == 1) {
                resuleImg.setImageResource(R.drawable.icon_groups_success);
                resuleTitleTv.setText("恭喜你，拼团成功！");
                resuleContentTv.setText("商家正在努力发货，请耐心等待！");
                resuleContentTv.setTextSize(15);

            } else if (status == 0) {//待成团 但是拼团时间时间过期了 拼团失败
                if (needs > 0) {
                    resuleImg.setImageResource(R.drawable.icon_groups_fail);
                    resuleTitleTv.setText("很遗憾，拼团未成功！");
                    resuleContentTv.setText("很遗憾，你的拼团未能在时效内达到拼团人数，本次拼团未能成功。谢谢您的参与。");
                    groups_gift_ll.setVisibility(View.VISIBLE);
                    setGiftContent();
                } else {
                    resuleImg.setImageResource(R.drawable.icon_groups_fail);
                    resuleTitleTv.setText("很遗憾，拼团未成功！");
                    if(is_pay==0){
                        //自己未付款
                        resuleContentTv.setText("很遗憾，该团你并未在指定时间内付款，本次拼团已失效。谢谢您的参与。");
                    }else{
                        resuleContentTv.setText("很遗憾，你的拼团未能在时效内达到付款人数，本次拼团未能成功。拼团费会在5-7个工作日内原路退回至支付账号。谢谢您的参与");
                    }
                    groups_gift_ll.setVisibility(View.VISIBLE);
                    setGiftContent();
                }
            }
        } else {
            if (endType == 1) {
                resuleImg.setImageResource(R.drawable.icon_groups_timeout);
                resuleTitleTv.setText("很遗憾，已过期！");
                resuleContentTv.setText("你参与的拼团已过期，去尝试自己开个团吧。");
                resuleContentTv.setTextSize(15);
            } else if (endType == 2) {
                resuleImg.setImageResource(R.drawable.icon_groups_timeout);
                resuleTitleTv.setText("很遗憾，人数已满！");
                resuleContentTv.setText("你参与的拼团已达到拼团人数哦，去尝试自己开个团吧。");
                resuleContentTv.setTextSize(15);
            }
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
        content_num = (TextView) findViewById(R.id.groups_gift_content_num);
        content_yuan = (TextView) findViewById(R.id.groups_gift_content_yuan);
        groups_gift_btn = (TextView) findViewById(R.id.groups_gift_btn);
        groups_gift_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_close:
                this.dismiss();
                break;
            case R.id.groups_gift_btn:


                // 跳至赚钱
                SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
                Intent intent = new Intent(context, CommonActivity.class);
                intent.putExtra("isTXListScroll", true);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                if (activity != null) {
                    activity.finish();
                }
                if (SignGroupShopActivity.instance != null) {
                    SignGroupShopActivity.instance.finish();
                }
                if (CommonActivity.instance != null) {
                    CommonActivity.instance.finish();
                }
                this.dismiss();
                break;

            default:
                break;
        }

    }

    /**
     * 设置未成功时候 赚钱提醒文案
     */
    private void setGiftContent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_QDRWYD);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HashMap<String, Object> m = new HashMap<String, Object>();
                            if (map != null) {
                                m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_QDRWYD);
                            }
                            String text_num = "今日又更新了16个任务，";
                            String text_yuan = "共计200元现金等你拿哦。";
                            if (m != null && m.size() > 0) {
                                String text = (String) m.get("text");
                                String[] texts = text.split(",");
                                try {
                                    text_num = "今日又更新了" + texts[1] + "个任务，";
                                    text_yuan = "共计" + texts[2] + "元现金等你拿哦。";
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            SpannableString textSpan_num = new SpannableString(text_num);
                            SpannableString textSpan_yuan = new SpannableString(text_yuan);
                            textSpan_num.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFF00")),
                                    6, text_num.length() - 3,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textSpan_num.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                                    6, text_num.length() - 3,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            textSpan_yuan.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFF00")),
                                    2, text_yuan.length() - 7,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textSpan_yuan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                                    2, text_yuan.length() - 7,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            content_num.setText(textSpan_num);
                            content_yuan.setText(textSpan_yuan);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
