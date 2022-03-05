package com.yssj.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.activity.GroupsDetailsActivity;
import com.yssj.ui.activity.shopdetails.MealPaymentActivity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class GroupsPayDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private View tvClose;
//    private View groups_pay_v,groups_no_pay_v;
    private TextView groups_pay_btn;
    private Activity activity;
    private HashMap<String, String> orderMap;



    public GroupsPayDialog(Context context,Activity activity,long recLenPay,HashMap<String, String> orderMap) {
        super(context);
        setCanceledOnTouchOutside(false);
        this.context = context;
        this.activity = activity;
        recLen = recLenPay;
        this.orderMap = orderMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_groups_pay);
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

        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new MyTimerTask(), 0, 1000);

    }

    private void initView() {
        tvClose = findViewById(R.id.icon_close);
        tvClose.setOnClickListener(this);
        mTvTimeHours = (TextView) findViewById(R.id.time_tv_hours);
        mTvTimeHoursHour = (TextView) findViewById(R.id.time_tv_hours_hour);
        mTvTimeHours.setVisibility(View.GONE);
        mTvTimeHoursHour.setVisibility(View.GONE);
        mTvTimeMinutes = (TextView) findViewById(R.id.time_tv_minutes);
        mTvTimeSeconds = (TextView) findViewById(R.id.time_tv_seconds);
        groups_pay_btn = (TextView) findViewById(R.id.groups_pay_btn);
        groups_pay_btn.setOnClickListener(this);
//        groups_pay_v = findViewById(R.id.groups_pay_v);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_close:
                this.dismiss();
                break;
            case R.id.groups_pay_btn:
                //去付款

                if(orderMap!=null&&orderMap.size()>0){
                    // 跳转到收银台界面
                    Intent intent = new Intent(context, MealPaymentActivity.class);
                    Bundle bundle = new Bundle();
                    intent.putExtra("order_code", orderMap.get("order_code"));
//                    bundle.putSerializable("result", result);
                    intent.putExtras(bundle);
                    intent.putExtra("isMulti", false);
                    // intent.putExtra("totlaAccount", sum);
                    intent.putExtra("totlaAccount", Double.parseDouble(orderMap.get("shop_roll")));
                    intent.putExtra("sign_huodong", "sign_huodong");
                    intent.putExtra("groupFlag",1);
                    context.startActivity(intent);

//                if (activity != null) {
//                    activity.finish();
//                }
//                if (SignGroupShopActivity.instance != null) {
//                    SignGroupShopActivity.instance.finish();
//                }
//                if (CommonActivity.instance != null) {
//                    CommonActivity.instance.finish();
//                }
                }

                this.dismiss();
                break;

            default:
                break;
        }

    }


    private TextView mTvTimeHours,mTvTimeHoursHour, mTvTimeMinutes, mTvTimeSeconds;// 倒计时TextView
    private long recLen = 0;// 拼团人满后 付款剩余时间
    private Timer timer;
    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            activity.runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen -= 1000;
                    String days;
                    String hours;
                    String minutes;
                    String seconds;
                    long minute = recLen / 60000;
                    long second = (recLen % 60000) / 1000;
                    if (minute >= 60) {
                        long hour = minute / 60;
                        minute = minute % 60;
//						if (hour >= 24) {
//							long day = hour / 24;
//							hour = hour % 24;
//							if (day < 10) {
//								days = "0" + day;
//							} else {
//								days = "" + day;
//							}
//							if (hour < 10) {
//								hours = "0" + hour;
//							} else {
//								hours = "" + hour;
//							}
//							if (minute < 10) {
//								minutes = "0" + minute;
//							} else {
//								minutes = "" + minute;
//							}
//							if (second < 10) {
//								seconds = "0" + second;
//							} else {
//								seconds = "" + second;
//							}
//							// mTvTime.setText("" + days + "天" + hours + "时" +
//							// minutes + "分" + seconds+"秒");
////							mTvTimeDays.setText(days);
////							mTvTimeHours.setText(hours);
////							mTvTimeMinutes.setText(minutes);
////							mTvTimeSeconds.setText(seconds);
//						} else {
                        if (hour < 10) {
                            hours = "0" + hour;
                        } else {
                            hours = "" + hour;
                        }
                        if (minute < 10) {
                            minutes = "0" + minute;
                        } else {
                            minutes = "" + minute;
                        }
                        if (second < 10) {
                            seconds = "0" + second;
                        } else {
                            seconds = "" + second;
                        }
                        mTvTimeHours.setText(hours);
                        mTvTimeMinutes.setText(minutes);
                        mTvTimeSeconds.setText(seconds);
                        mTvTimeHours.setVisibility(View.VISIBLE);
                        mTvTimeHoursHour.setVisibility(View.VISIBLE);
//						}
                    } else if (minute >= 10 && second >= 10) {
                        mTvTimeHours.setText("00");
                        mTvTimeMinutes.setText(minute + "");
                        mTvTimeSeconds.setText(second + "");
                        mTvTimeHours.setVisibility(View.GONE);
                        mTvTimeHoursHour.setVisibility(View.GONE);
                    } else if (minute >= 10 && second < 10) {
                        mTvTimeHours.setText("00");
                        mTvTimeMinutes.setText(minute + "");
                        mTvTimeSeconds.setText("0" + second);
                        mTvTimeHours.setVisibility(View.GONE);
                        mTvTimeHoursHour.setVisibility(View.GONE);
                    } else if (minute < 10 && second >= 10) {
                        mTvTimeHours.setText("00");
                        mTvTimeMinutes.setText("0" + minute);
                        mTvTimeSeconds.setText(second + "");
                        mTvTimeHours.setVisibility(View.GONE);
                        mTvTimeHoursHour.setVisibility(View.GONE);
                    } else if (minute < 10 && second < 10) {
                        mTvTimeHours.setText("00");
                        mTvTimeMinutes.setText("0" + minute);
                        mTvTimeSeconds.setText("0" + second);
                        mTvTimeHours.setVisibility(View.GONE);
                        mTvTimeHoursHour.setVisibility(View.GONE);
                    }

                    if (recLen <= 0) {
                        timer.cancel();
                        mTvTimeHours.setText("00");
                        mTvTimeMinutes.setText("00");
                        mTvTimeSeconds.setText("00");
                        mTvTimeHours.setVisibility(View.GONE);
                        mTvTimeHoursHour.setVisibility(View.GONE);
                        groups_pay_btn.setBackgroundResource(R.drawable.btn_back);
                        groups_pay_btn.setClickable(false);
                        GroupsPayDialog.this.dismiss();
                        //去付款弹窗消失 拼团结果弹窗弹出 当前拼团人满 并且当前用户未付款
                        new GroupsResultDialog(context,activity,6,0,0).show();
                        ((GroupsDetailsActivity)activity).setBottomFinishTv();

                    }
                }
            });
        }
    }




}
