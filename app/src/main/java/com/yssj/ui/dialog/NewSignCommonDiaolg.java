package com.yssj.ui.dialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.ShopPageActivity;
import com.yssj.ui.activity.infos.GoldCoinDetailActivity;
import com.yssj.ui.activity.infos.MyCouponsActivity;
import com.yssj.ui.activity.infos.MyWalletActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.main.FilterResultActivity;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.main.ForceLookMadActivity;
import com.yssj.ui.activity.main.ForceLookMatchActivity;
import com.yssj.ui.activity.main.SearchResultActivity;
import com.yssj.ui.activity.main.SignActiveShopActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
//import android.graphics.PixelXorXfermode;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class NewSignCommonDiaolg extends Dialog implements android.view.View.OnClickListener {
    private TextView tv1, tv2, tv3, tv4, title, tv_fenzhong, tv_miao;
    private Button gobuy1, gobuy2, liebiao;
    private RelativeLayout rl_twobt;
    private Context mContext;
    private String jumpFrom;
    private ImageView icon_close;
    private String awards;
    private String doValue = "1";
    private String duobaoNumber = "";
    public static String app_name;
    public static Timer overtimer;
    public static Timer overtimershow;
    public static Timer timer;

    private String leiTX = "";

    public static long doValuefenzhong = 1 * 60 * 1000; // 浏览分钟数
    public long overTime = 10000L; // 浏览分钟数

    private LinearLayout ll_dojishi;

    public static long minute; // 剩余分钟
    public static long second; // 剩余秒数

    public static long doValuefenzhongOver = 1 * 60 * 1000; // 浏览分钟数

    // 倒计时
    TimerTask overtask = new TimerTask() {
        @Override
        public void run() {

            ((Activity) mContext).runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {

                    // doValuefenzhongOver = doValuefenzhong;

                    doValuefenzhongOver -= 1000;

                    // 分割成分和秒
                    // minute = doValuefenzhong / 60000;
                    // second = (doValuefenzhong % 60000) / 1000;
                    // minute剩余分钟 second剩余秒
                    // ToastUtil.showShortText(context, minute+"分钟"+
                    // second+"秒");

                    if (doValuefenzhong <= 0) {
                        // timer.cancel();
                        // 1：已开启
                    }
                }
            });
        }
    };
    // 倒计时---用于实时显示时间
    TimerTask overtaskShow = new TimerTask() {
        @Override
        public void run() {

            ((Activity) mContext).runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    // doValuefenzhongOver -= 1000;

                    if (doValuefenzhongOver <= 0) {
                        tv_miao.setText("00");
                        tv_fenzhong.setText("00");
                        overtimershow.cancel();
                        dismiss();
                        return;
                    }

                    // 分割成分和秒
                    minute = doValuefenzhongOver / 60000;
                    second = (doValuefenzhongOver % 60000) / 1000;
                    // minute剩余分钟 second剩余秒
                    // ToastUtil.showShortText(context, minute+"分钟"+
                    // second+"秒");

                    if (SignListAdapter.minuteMap.size() != 0) {
                        // title.setText("任务正在进行中...");
                        if (minute >= 10) {
                            tv_fenzhong.setText(minute + "");

                        } else {
                            tv_fenzhong.setText("0" + minute);

                        }

                        if (second >= 10) {
                            tv_miao.setText(second + "");
                        } else {
                            tv_miao.setText("0" + second);
                        }

                    }

                    if (second == 0 && minute == 0) {
                        tv_fenzhong.setText("00");
                        tv_miao.setText("00");
                        overtimershow.cancel();
                        if (overtimer != null) {
                            overtimer.cancel();
                        }
                    }

                }
            });
        }
    };

    public NewSignCommonDiaolg(Context context, int style, String jumpFrom, SignRefreshDataListener dataListener,
                               String app_name) {
        super(context, style);
        this.mContext = context;
        this.jumpFrom = jumpFrom;
        this.app_name = app_name;
        refreshData = dataListener;
    }

    private String doIconId;

    public NewSignCommonDiaolg(Context context, int style, String jumpFrom, SignRefreshDataListener dataListener,
                               String app_name,String doIconId) {
        super(context, style);
        this.mContext = context;
        this.jumpFrom = jumpFrom;
        this.app_name = app_name;
        this.doIconId = doIconId;

        refreshData = dataListener;
    }


    public NewSignCommonDiaolg(Context context, int style, String jumpFrom) {
        super(context, style);
        this.mContext = context;
        this.jumpFrom = jumpFrom;

    }

    public NewSignCommonDiaolg(Context context, int style, String jumpFrom, String awards) {
        super(context, style);
        this.mContext = context;
        this.jumpFrom = jumpFrom;
        this.awards = awards;
    }

    public NewSignCommonDiaolg(Context context, int style, String jumpFrom, String awards, String duobaoNumber) {
        super(context, style);
        this.mContext = context;
        this.jumpFrom = jumpFrom;
        this.awards = awards;
        this.duobaoNumber = duobaoNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign_common);

        //夺宝号只取前两个
        if (null != duobaoNumber && duobaoNumber.length() > 0) {
            String[] duo = duobaoNumber.split(",");
            if (duo.length > 2) {
                duobaoNumber = duo[0] + "," + duo[1];
            }
        }


        int fenzhong = 1;
        try {
            fenzhong = Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        doValuefenzhong = fenzhong * 60 * 1000;

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


        try {
            leiTX = SignListAdapter.doValue.split(",")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (SignListAdapter.minuteMap.size() != 0) { // 第二次点

            if (null != overtimershow) {
                overtimershow.cancel();
            }
            overtimershow = new Timer();
            overtimershow.schedule(overtaskShow, 0, 1000); // timeTask
            doValuefenzhong = doValuefenzhongOver; // 拿到之前剩余的时间

        }

        initData();

    }

    private void initData() {

        try {
            doValue = SignListAdapter.doValue.split(",")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (jumpFrom.equals("jingxiwanchengtishikuang")) {// 惊喜任务完成
            title.setText("任务完成！");
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText("下单成功~");
            tv2.setVisibility(View.VISIBLE);
            tv2.setText("你本月的现金奖励将全部翻倍，记得坚持签到领取更多现金喔~");
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.VISIBLE);
            tv4.setText("此外，订单若发生退货/退款，每月惊喜将会失效，需重新完成任务");
            gobuy1.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.VISIBLE);
            gobuy2.setText("去逛逛");
            liebiao.setText("知道了");

        }

        if (jumpFrom.equals("goumairenwuwancheng")) {// 购买任务完成
            title.setText("购买完成！");
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText("购买成功~");
            tv2.setVisibility(View.VISIBLE);
            tv2.setText("任务奖励的提现额度已经发放到您账户~订单完成后可提现！");
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.VISIBLE);
            tv4.setText("如同时完成多件订单，系统将默认第一单可享有完成任务的奖励！");
            gobuy1.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.VISIBLE);
            gobuy2.setText("去逛逛");
            liebiao.setText("知道了");

        }

        if (jumpFrom.equals("liulanhuodongjishitishi")) {

            // title.setText("任务提示");
            // tv2.setVisibility(View.GONE);
            // tv3.setVisibility(View.GONE);
            // tv4.setVisibility(View.GONE);
            // rl_twobt.setVisibility(View.GONE);
            //
            // tv1.setText("浏览活动商品" + doValue + "分钟，即可完成任务喔~");
            // tv1.setTextSize(14);
            // tv1.setTextColor(Color.parseColor("#7D7D7D"));
            // tv1.post(new Runnable() {
            // @Override
            // public void run() {
            // if (tv1.getLineCount() == 1) {
            // tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            // }
            // }
            // });
            // tv1.setVisibility(View.VISIBLE);
            // gobuy1.setVisibility(View.VISIBLE);
            // gobuy1.setText("去浏览美衣");
            //

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览超值活动商品" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }

        }
        if (jumpFrom.equals("liulanfenzhongtishi")) { // 浏览分钟数，除活动商品和搭配商品外都共用这个

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览" + app_name + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }

        }

        if (jumpFrom.equals("duobaowanchen")) { // 夺宝完成

            title.setText("任务完成！");
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText("参与成功！");
            tv1.setTextSize(16);

            tv2.setText("你的抽奖号为" + duobaoNumber);
            tv2.post(new Runnable() {

                @Override
                public void run() {
                    if (tv2.getLineCount() == 1) {
                        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            tv2.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.VISIBLE);
            tv4.setText("听说买件美衣可以增加运气喔~快来选购吧~");
            rl_twobt.setVisibility(View.GONE);
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("买买买");
        }

        if (jumpFrom.equals("liulandapeitishi")) { // 浏览搭配购分钟

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览时尚搭配" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }

        }
        if (jumpFrom.equals("liulanzhuantitishi")) { // 浏览专题分钟

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览专题" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }

        }

        if (jumpFrom.equals("liulangouwuyemian")) { // 浏览购物页X分钟

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = app_name + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");

            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }

            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }

        }


        if (jumpFrom.equals("shequshouye")) { // 浏览社区首页X分钟

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = app_name + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览社区");

            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }

            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }

        }

        if (jumpFrom.equals("liulanwaitaotishi")) { // 浏览外套商品

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览甜心的外套" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }

        }
        if (jumpFrom.equals("liulanxiaowaitao")) { // 浏览小外套
            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览帅气外套" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }
        if (jumpFrom.equals("liulanlianyiqun")) { // 浏览连衣裙集合

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览气质美裙" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }
        if (jumpFrom.equals("liulanhanxi")) { // 浏览韩系集合

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览甜美韩系" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }
        if (jumpFrom.equals("liulanoouxi")) { // 浏览欧系集合

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览欧美潮范" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }
        if (jumpFrom.equals("liulanjianyuebaida")) { // 浏览简约百搭集合

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览经典百搭商品" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }
        if (jumpFrom.equals("liulanwenyifugu")) { // 浏览文艺复古集合

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览文艺复古商品" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }
        if (jumpFrom.equals("liulanqingtongzhuang")) { // 浏览通勤装集合

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览上班族必备" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }
        if (jumpFrom.equals("liulanshihui")) { // 浏览实惠集合

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览超值特惠" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }
        if (jumpFrom.equals("liullanzhonggaoduan")) { // 浏览中高端集合 （浏览轻奢商品）

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览流行趋势" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }
        if (jumpFrom.equals("liulantianmeikeai")) { // 浏览少女心商品

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览萌系可爱风" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }
        if (jumpFrom.equals("liulantongqingmingyuan")) { // 浏览通勤名媛商品

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览简约通勤" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }
        if (jumpFrom.equals("liulanyundongxiuxian")) { // 浏览运动休闲集合

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览运动休闲商品" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }

        if (jumpFrom.equals("liulanshangyitishi")) { // 浏览上衣板块

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览宝宝的上衣" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }

        if (jumpFrom.equals("liulanqunzitishi")) { // 浏览裙子板块

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览仙女的裙子" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }

        if (jumpFrom.equals("liulankuzitishi")) { // 浏览裤子板块
            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览萌妹的裤子" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }

        if (jumpFrom.equals("liulanremaitishi")) { // 浏览热卖板块

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览最热销单品" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }

        if (jumpFrom.equals("liulanshangxintishi")) { // 浏览上新

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览潮流新品" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }

        if (jumpFrom.equals("liulantaozhuangtishi")) { // 浏览套装板块

            title.setText("任务提示");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "浏览女王的套装" + doValue + "分钟，就能领到bling bling的奖励完成任务哦~";
            SpannableString tttTextjifen = new SpannableString(textortherjifen);
            tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), textortherjifen.length() - 28,
                    textortherjifen.length() - 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv1.setText(tttTextjifen);
            tv1.setTextSize(14);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setVisibility(View.VISIBLE);
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("任务正在进行中...");
            }
        }

        if (jumpFrom.equals("liulan_sign_finish") || jumpFrom.equals("share_sign_finish")
                || jumpFrom.equals("liulan_sign_chuanda_finish")) { // 浏览个数完成任务
            // 或
            // 分享完成任务

            title.setText("任务完成！");

            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            if (jumpFrom.equals("liulan_sign_finish")) {
                finishScanMode(0);
            } else if (jumpFrom.equals("share_sign_finish")) {
                tv1.setText("分享成功~");
            } else if (jumpFrom.equals("liulan_sign_chuanda_finish")) {
                tv1.setText("完成浏览任务~");
            }
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
            if (jumpFrom.equals("liulan_sign_finish")) {
                rl_twobt.setVisibility(View.VISIBLE);
                gobuy1.setVisibility(View.GONE);

                gobuy2.setTextSize(14);
                liebiao.setTextSize(14);

                gobuy2.setText("继续浏览");
                liebiao.setText("一键做下个任务");
            } else if (jumpFrom.equals("share_sign_finish")) {
                rl_twobt.setVisibility(View.GONE);
                gobuy1.setVisibility(View.VISIBLE);
                gobuy1.setText("买买买~");
            } else if (jumpFrom.equals("liulan_sign_chuanda_finish")) {
                rl_twobt.setVisibility(View.VISIBLE);
                gobuy1.setVisibility(View.GONE);

                gobuy2.setTextSize(14);
                liebiao.setTextSize(14);


                gobuy2.setText("继续浏览");
                liebiao.setText("一键做下个任务");
            }

            //统一处理显示
            rl_twobt.setVisibility(View.VISIBLE);
            gobuy1.setVisibility(View.GONE);

            gobuy2.setTextSize(14);
            liebiao.setTextSize(14);


            gobuy2.setText("一键做下个任务");
            liebiao.setText("继续浏览");

        }

        if (jumpFrom.equals(Pref.LIULAN_SIGN_UPPER_LIMIT)) { // 浏览个数 奖励额度 完成任务
            // 或
            // 分享完成任务

            title.setText("任务完成！");

            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            tv1.setText("完成浏览任务~");
            tv1.setTextSize(16);
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            tv1.setVisibility(View.VISIBLE);

            tv2.setText("今日的浏览奖励已达上限，记得明天再来。");
            tv2.post(new Runnable() {
                @Override
                public void run() {
                    if (tv2.getLineCount() == 1) {
                        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            tv2.setVisibility(View.VISIBLE);
            rl_twobt.setVisibility(View.VISIBLE);
            gobuy1.setVisibility(View.GONE);

            gobuy2.setTextSize(14);
            liebiao.setTextSize(14);

//            gobuy2.setText("继续浏览");
//            liebiao.setText("一键做下个任务");

            gobuy2.setText("一键做下个任务");
            liebiao.setText("继续浏览");
        }

        if (jumpFrom.contains("addshopcarttishi")) { // 加入X件商品到购物车提示购物车提示

            title.setText("任务提示");

            tv2.setVisibility(View.GONE);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText("将喜欢的衣服加入购物车，即可完成任务喔！");
            tv1.setTextColor(Color.parseColor("#7D7D7D"));
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    if (tv1.getLineCount() == 1) {
                        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            });
            tv1.setTextSize(14);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);

            rl_twobt.setVisibility(View.GONE);
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("去浏览美衣~~");
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

            rl_twobt.setVisibility(View.VISIBLE);
            gobuy1.setVisibility(View.GONE);
            gobuy2.setText("查看余额");
            liebiao.setText("买买买");
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

            rl_twobt.setVisibility(View.VISIBLE);
            gobuy1.setVisibility(View.GONE);
            gobuy2.setText("查看金币");
            liebiao.setText("买买买");
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

            rl_twobt.setVisibility(View.VISIBLE);
            gobuy1.setVisibility(View.GONE);
            gobuy2.setText("查看金券");
            liebiao.setText("买买买");
        }

        if (jumpFrom.equals("jingxirenwushuoming")) { // 惊喜任务说明

            title.setText("惊喜任务说明");

            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.GONE);
            tv1.setText("惊喜任务每月限做一次，完成惊喜任务后，当月签到现金奖励全部翻倍。");
            tv1.setTextColor(Color.parseColor("#7D7D7D"));
            tv1.setTextSize(14);
            tv3.setVisibility(View.VISIBLE);
            tv3.setText("本月完成全部任务，最高600元现金奖励在等着您哦！");
            tv4.setVisibility(View.GONE);

            rl_twobt.setVisibility(View.GONE);
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("买买买~");
        }
        if (jumpFrom.equals("goumaishuoming")) { // 购买任务说明

            title.setText("购买任务说明");

            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.GONE);
            String miaoshu = "";

            String where = "type_name=热卖&notType=true";
            String buyCount = "1";
            try {

                where = app_name.split("-")[0].split(",")[0];

                // where = app_name.split(",")[0];

            } catch (Exception e) {
            }
            try {

                buyCount = app_name.split("-")[0].split(",")[1];

                // buyCount = app_name.split(",")[1];

            } catch (Exception e) {
            }

            // if (where.equals("collection=shopping_page")) {// 购物
            // miaoshu = "购买购物页" + buyCount + "件商品";
            //
            // } else if (where.equals("collection=collocation_shop")) {// 搭配
            // miaoshu = "购买搭配页" + buyCount + "件商品";
            //
            // } else if (where.equals("collection=shop_activity")) {// 活动
            // miaoshu = "购买活动页" + buyCount + "件商品";
            //
            // } else if (where.equals("collection=csss_shop")) {// 专题
            // miaoshu = "购买专题页" + buyCount + "件商品";
            //
            // }
            //
            // else if (where.equals("type_name=热卖&notType=true")) {// 热卖
            // miaoshu = "购买热卖页" + buyCount + "件商品";
            //
            // }
            //
            // else if (where.equals("collection=shop_home")) {// 首页
            // miaoshu = "购买首页" + buyCount + "件商品";
            //
            // }
            //
            // else { // 找不到的话还是用热卖
            // miaoshu = "购买热卖页" + buyCount + "件商品";
            //
            // }

            String namne = "热卖";
            try {
                namne = app_name.split("-")[1];
            } catch (Exception e) {
            }

            miaoshu = "购买" + buyCount + "件" + namne + "商品";

            tv1.setText("只需" + miaoshu + "即可完成任务，获得提现额度！提现额度在订单完结后（不可退货退款）才能使用哦~若同时购买多件商品，以购买的第一件为完成任务商品！");
            tv1.setTextColor(Color.parseColor("#7D7D7D"));
            tv1.setTextSize(14);
            tv3.setVisibility(View.GONE);
            // tv3.setText("本月完成全部任务，最高600元现金奖励在等着您哦！");
            tv4.setVisibility(View.GONE);

            rl_twobt.setVisibility(View.GONE);
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("买买买~");
        }

    }

    /**
     * 浏览板块 mode = 1 分钟数 mode = 0 个数 --- 没有分钟的了
     */
    private void finishScanMode(int mode) {

        String bankuai = SignListAdapter.doValue.split(",")[0];
        if (bankuai.equals("collection=collocation_shop")) {
            tv1.setText("完成【时尚搭配】浏览~");
        } else if (bankuai.equals("collection=browse_shop")) {
            tv1.setText("完成【最热销单品】浏览~");
        } else if (bankuai.equals("collection=shop_activity")) {
            tv1.setText("完成【超值活动商品】浏览~");
        } else if (bankuai.equals("type2=11")) {
            tv1.setText("完成【帅气外套】浏览~");
        } else if (bankuai.equals("type2=23")) {
            tv1.setText("完成【气质美裙】浏览~");
        } else if (bankuai.equals("favorite=29")) {
            tv1.setText("完成【甜美韩系】浏览~");
        } else if (bankuai.equals("favorite=30")) {
            tv1.setText("完成【欧美潮范】浏览~");
        } else if (bankuai.equals("fix_price=20")) {
            tv1.setText("完成【超值特惠】浏览~");
        } else if (bankuai.equals("fix_price=22")) {
            tv1.setText("完成【流行趋势】浏览~");
        } else if (bankuai.equals("style=105")) {
            tv1.setText("完成【萌系可爱风】浏览~");
        } else if (bankuai.equals("style=103")) {
            tv1.setText("完成【简约通勤】浏览~");
        } else if (bankuai.equals("style=112")) {
            tv1.setText("完成【运动休闲】浏览~");
        } else if (bankuai.equals("style=750")) {
            tv1.setText("完成【经典百搭】浏览~");
        } else if (bankuai.equals("style=102")) {
            tv1.setText("完成【文艺复古】 浏览~");
        } else if (bankuai.equals("occasion=24")) {
            tv1.setText("完成【上班族必备】浏览~");
        } else {
            tv1.setText("完成浏览任务~");
        }

    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.gobuy1: // 去购物

                if (jumpFrom.equals("duobaowanchen")) { // 夺宝完成点击买买买
                    CommonUtils.finishActivity(MainMenuActivity.instances);

                    Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                    intent2.putExtra("toYf", "toYf");
                    mContext.startActivity(intent2);
                }

                if (jumpFrom.equals("jingxitishi")) { // 惊喜任务点击时提醒
                    CommonUtils.finishActivity(MainMenuActivity.instances);

                    Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                    intent2.putExtra("toYf", "toYf");
                    mContext.startActivity(intent2);
                }
//			if (jumpFrom.equals("jingxirenwushuoming")) { // 惊喜任务点击时提醒
//
//				Intent intent = new Intent(mContext, ForceLookActivity.class);
//				intent.putExtra("id", "6");
//				intent.putExtra("title", "热卖");
//				mContext.startActivity(intent);
//				((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//				// Intent intent2 = new Intent((Activity) context,
//				// MainMenuActivity.class);
//				// intent2.putExtra("toYf", "toYf");
//				// context.startActivity(intent2);
//			}

                if (jumpFrom.equals("goumaishuoming") || jumpFrom.equals("jingxirenwushuoming")) { // 购买任务提示





                    String where = "type_name=热卖&notType=true";
                    try {
                        where = app_name.split("-")[0].split(",")[0];
                    } catch (Exception e) {
                    }

                    if (where.equals("collection=shopping_page")) {// 购物
                        // 跳至购物
                        CommonUtils.finishActivity(MainMenuActivity.instances);

                        Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                        intent2.putExtra("toShop", "toShop");
                        mContext.startActivity(intent2);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    } else if (where.equals("collection=collocation_shop")) {// 搭配

                        Intent intent = new Intent(mContext, ForceLookMatchActivity.class);
                        intent.putExtra("type", "1");


                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("isGaoMai", true);


                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    } else if (where.equals("collection=shop_activity")) {// 活动

                        // 跳转到活动商品

                        Intent intent = new Intent(mContext, SignActiveShopActivity.class);

                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("isGaoMai", true);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    } else if (where.equals("collection=csss_shop")) {// 专题
                        Intent intent = new Intent(mContext, ForceLookMatchActivity.class);

                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("isGaoMai", true);
                        intent.putExtra("type", "2");
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    } else if (where.equals("type_name=热卖&notType=true")) {// 热卖

                        Intent intent = new Intent(mContext, ForceLookActivity.class);
                        intent.putExtra("id", "6");
                        intent.putExtra("title", "热卖");

                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("isGaoMai", true);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    } else if (where.equals("collection=shop_home")) {// 首页

                        // 跳至首页
                        CommonUtils.finishActivity(MainMenuActivity.instances);

                        Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                        intent2.putExtra("toHome", "toHome");
                        mContext.startActivity(intent2);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    } else { // 找不到的话还是用热卖

                        Intent intent = new Intent(mContext, ForceLookActivity.class);
                        intent.putExtra("id", "6");
                        intent.putExtra("title", "热卖");

                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("isGaoMai", true);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    }
                }

                if (jumpFrom.equals("liulandapeitishi")) { // 浏览搭配集合
                    Intent intent = new Intent(mContext, ForceLookMatchActivity.class);
                    intent.putExtra("type", "1");
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask
                    // timer.schedule(task, 5*1000); // timeTask

                }


                if (jumpFrom.equals("addshopcarttishi_dapei")) { // 搭配集合---加购物车


                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String now_time = sdf.format(date);
                    if (!SharedPreferencesUtil.getStringData(mContext, "qiandao_time", "").equals(now_time)) {
                        SharedPreferencesUtil.saveStringData(mContext,
                                "qiandao_time" + YCache.getCacheUser(mContext).getUser_id(), now_time);
                        SharedPreferencesUtil.saveStringData(mContext,
                                "qiandao_num" + YCache.getCacheUser(mContext).getUser_id(), "0");
                    }


                    Intent intent = new Intent(mContext, ForceLookMatchActivity.class);
                    intent.putExtra("type", "1");
                    intent.putExtra("isAddShopcart",true);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                }


                if (jumpFrom.equals("liulanzhuantitishi")) { // 浏览专题分钟
                    Intent intent = new Intent(mContext, ForceLookMatchActivity.class);
                    intent.putExtra("type", "2");
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask
                    // timer.schedule(task, 5*1000); // timeTask

                }


                if (jumpFrom.equals("addshopcarttishi_zhuanti")) { // 专题--加购物车


                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String now_time = sdf.format(date);
                    if (!SharedPreferencesUtil.getStringData(mContext, "qiandao_time", "").equals(now_time)) {
                        SharedPreferencesUtil.saveStringData(mContext,
                                "qiandao_time" + YCache.getCacheUser(mContext).getUser_id(), now_time);
                        SharedPreferencesUtil.saveStringData(mContext,
                                "qiandao_num" + YCache.getCacheUser(mContext).getUser_id(), "0");
                    }


                    Intent intent = new Intent(mContext, ForceLookMatchActivity.class);
                    intent.putExtra("type", "2");
                    intent.putExtra("isAddShopcart",true);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                }


                if (jumpFrom.equals("addshopcarttishi_huodong")) { // 活动--加购物车


                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String now_time = sdf.format(date);
                    if (!SharedPreferencesUtil.getStringData(mContext, "qiandao_time", "").equals(now_time)) {
                        SharedPreferencesUtil.saveStringData(mContext,
                                "qiandao_time" + YCache.getCacheUser(mContext).getUser_id(), now_time);
                        SharedPreferencesUtil.saveStringData(mContext,
                                "qiandao_num" + YCache.getCacheUser(mContext).getUser_id(), "0");
                    }



                    Intent intent = new Intent(mContext, SignActiveShopActivity.class);

                    intent.putExtra("doIconId", doIconId);

                    intent.putExtra("isCrazy", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);

                }



                if (jumpFrom.equals("addshopcarttishi_qitajihe")) { // 其他集合--加购物车


                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String now_time = sdf.format(date);
                    if (!SharedPreferencesUtil.getStringData(mContext, "qiandao_time", "").equals(now_time)) {
                        SharedPreferencesUtil.saveStringData(mContext,
                                "qiandao_time" + YCache.getCacheUser(mContext).getUser_id(), now_time);
                        SharedPreferencesUtil.saveStringData(mContext,
                                "qiandao_num" + YCache.getCacheUser(mContext).getUser_id(), "0");
                    }


                    Intent    intent = new Intent(mContext, ForceLookMadActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("title", "热卖");
                    intent.putExtra("pinJievalue", leiTX);

                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isCrazy", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);


                }



                if (jumpFrom.equals("liulangouwuyemian")) { // 浏览购物X分钟

                    // 跳至购物
//                    Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
//                    intent2.putExtra("toShop", "toShop");
//                    mContext.startActivity(intent2);
//                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                    mContext.startActivity(new Intent(mContext, ShopPageActivity.class));
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask
                    // timer.schedule(task, 5*1000); // timeTask

                }


                if (jumpFrom.equals("addshopcarttishi_gowuye")) { // 购物页-----加购物车



                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String now_time = sdf.format(date);
                    if (!SharedPreferencesUtil.getStringData(mContext, "qiandao_time", "").equals(now_time)) {
                        SharedPreferencesUtil.saveStringData(mContext,
                                "qiandao_time" + YCache.getCacheUser(mContext).getUser_id(), now_time);
                        SharedPreferencesUtil.saveStringData(mContext,
                                "qiandao_num" + YCache.getCacheUser(mContext).getUser_id(), "0");
                    }



                    Intent intent = new Intent(mContext, ShopPageActivity.class);
                    intent.putExtra("isAddShopcart",true);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);



                }




                if (jumpFrom.equals("shequshouye")) { // 浏览社区首页X分钟

                    // 跳至购物
//                    Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
//                    intent2.putExtra("toShop", "toShop");
//                    mContext.startActivity(intent2);
//                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    Intent intent = new Intent(mContext, ShopPageActivity.class);
                    intent.putExtra("isMiyouquan",true);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask
                    // timer.schedule(task, 5*1000); // timeTask

                }



                if (jumpFrom.equals("liulanwaitaotishi")) { // 浏览外套集合

                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("title", "甜心的外套");
                    intent.putExtra("id", "1");
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    SignListAdapter.minuteMap.put("jumpFrom", app_name);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }

                if (jumpFrom.equals("liulanfenzhongtishi")) { // 浏览分钟数，除活动商品和搭配商品外都共用这个

                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("title", app_name);
                    intent.putExtra("pinJievalue", SignListAdapter.doValueLiulan.split(",")[0]);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    SignListAdapter.minuteMap.put("jumpFrom", app_name);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }

                if (jumpFrom.equals("liulanshangyitishi")) { // 浏上衣集合
                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("title", "宝宝的上衣");
                    intent.putExtra("id", "2");
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }

                if (jumpFrom.equals("liulanqunzitishi")) { // 浏览裙子集合
                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("title", "仙女的裙子");
                    intent.putExtra("id", "3");
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }

                if (jumpFrom.equals("liulankuzitishi")) { // 浏览裤子集合
                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("title", "萌妹的裤子");
                    intent.putExtra("id", "4");
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }

                if (jumpFrom.equals("liulanremaitishi")) { // 浏热卖集合
                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("title", "最热销单品");
                    intent.putExtra("id", "6");
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }
                if (jumpFrom.equals("liulanshangxintishi")) { // 上新集合
                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("title", "潮流新品");
                    intent.putExtra("id", "8");
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }
                if (jumpFrom.equals("liulantaozhuangtishi")) { // 浏览套装集合
                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("title", "女王的套装");
                    intent.putExtra("id", "7");
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }

                if (jumpFrom.equals("share_sign_finish") || jumpFrom.equals("liulan_sign_chuanda_finish")) {// 浏览个数点击买买买
                    // 只是消失Dialog
                    CommonUtils.finishActivity(MainMenuActivity.instances);

                    Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                    intent2.putExtra("toYf", "toYf");
                    mContext.startActivity(intent2);

                }

                if (jumpFrom.equals("addshopcarttishi")) { // 加入购物车提示

                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String now_time = sdf.format(date);
                    if (!SharedPreferencesUtil.getStringData(mContext, "qiandao_time", "").equals(now_time)) {
                        SharedPreferencesUtil.saveStringData(mContext,
                                "qiandao_time" + YCache.getCacheUser(mContext).getUser_id(), now_time);
                        SharedPreferencesUtil.saveStringData(mContext,
                                "qiandao_num" + YCache.getCacheUser(mContext).getUser_id(), "0");
                    }






                    CommonUtils.finishActivity(MainMenuActivity.instances);

                    Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                    intent2.putExtra("isAddShopCart", true);
                    intent2.putExtra("toYf", "toYf");
                    mContext.startActivity(intent2);
                }

                if (jumpFrom.equals("liulanxiaowaitao")) { // 浏览小外套几分钟

                    // int fenzhong = 1;
                    // try {
                    // fenzhong =
                    // Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                    // long doValue = fenzhong * 60 * 1000;

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    // 跳转到小外套搜索结果

                    HashMap<String, String> item = new HashMap<String, String>();

                    item.put("s_show", "1");
                    item.put("p_id", "1");
                    item.put("icon", "shop/type/xiaowaitao.png");
                    item.put("_id", "11");
                    item.put("group_flag", "");
                    item.put("sequence", "2");
                    item.put("sort_name", "帅气外套");

                    Intent intent = new Intent();
                    intent.setClass(mContext, SearchResultActivity.class);

                    intent.putExtra("isSign", true);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("item", item);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.activity_from_right,
                            R.anim.activity_search_close);
                }
                if (jumpFrom.equals("liulanhuodongjishitishi")) { // 浏览活动商品几分钟
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    // int fenzhong = 1;
                    // try {
                    // fenzhong =
                    // Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                    // long doValue = fenzhong * 60 * 1000;
                    // doValue = 5*1000 ;

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();

                    if (doValuefenzhong > 0) {
                        // 签到计时
                        timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask
                    } else {
                        ToastUtil.showShortText(mContext, "此任务已完成！");
                        return;
                    }

                    // 跳转到活动商品

                    mContext.startActivity(new Intent(mContext, SignActiveShopActivity.class));
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                }

                if (jumpFrom.equals("liulanlianyiqun")) { // 浏览连衣裙几分钟
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    // int fenzhong = 1;
                    // try {
                    // fenzhong =
                    // Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                    // long doValue = fenzhong * 60 * 1000;

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();

                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                    // 跳转到连衣裙搜索结果
                    HashMap<String, String> item = new HashMap<String, String>();
                    item.put("s_show", "1");
                    item.put("p_id", "1");
                    item.put("icon", "shop/type/lianyiqun.png");
                    item.put("_id", "23");
                    item.put("group_flag", "");
                    item.put("sequence", "1");
                    item.put("sort_name", "气质美裙");

                    Intent intent = new Intent();
                    intent.setClass(mContext, SearchResultActivity.class);
                    intent.putExtra("isSign", true);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("item", item);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.activity_from_right,
                            R.anim.activity_search_close);
                }
                if (jumpFrom.equals("liulanhanxi")) { // 浏览韩系几分钟
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    // int fenzhong = 1;
                    // try {
                    // fenzhong =
                    // Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                    // long doValue = fenzhong * 60 * 1000;

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, Object> mapRequest = new HashMap<String, Object>();
                    map.put("aa", "favorite");
                    map.put("is_show", "1");
                    map.put("p_id", "5");
                    map.put("icon", "");
                    map.put("isChecked", "1");
                    map.put("e_name", "");
                    map.put("_id", "29");
                    map.put("sequence", "29");
                    map.put("attr_name", "韩系");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isSign", true);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "甜美韩系");
                    intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// 默认筛选热卖
                    bundle.putString("title", "热卖");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // {favorite={aa=favorite, is_show=1, p_id=5, icon=,
                    // isChecked=1, e_name=, _id=29, sequence=29,
                    // attr_name=韩系}}
                }
                if (jumpFrom.equals("liulanoouxi")) { // 浏览欧系几分钟
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    // int fenzhong = 1;
                    // try {
                    // fenzhong =
                    // Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                    // long doValue = fenzhong * 60 * 1000;

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask
                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, Object> mapRequest = new HashMap<String, Object>();
                    map.put("aa", "favorite");
                    map.put("is_show", "1");
                    map.put("p_id", "5");
                    map.put("icon", "");
                    map.put("isChecked", "1");
                    map.put("e_name", "");
                    map.put("_id", "30");
                    map.put("sequence", "30");
                    map.put("attr_name", "欧系");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "欧美潮范");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// 默认筛选热卖
                    bundle.putString("title", "热卖");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // {favorite={aa=favorite, is_show=1, p_id=5, icon=,
                    // isChecked=1, e_name=, _id=30, sequence=30,
                    // attr_name=欧系}}
                }
                if (jumpFrom.equals("liulanshihui")) { // 浏览实惠几分钟
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    // int fenzhong = 1;
                    // try {
                    // fenzhong =
                    // Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                    // long doValue = fenzhong * 60 * 1000;

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, Object> mapRequest = new HashMap<String, Object>();
                    map.put("aa", "fix_price");
                    map.put("is_show", "1");
                    map.put("p_id", "3");
                    map.put("icon", "");
                    map.put("isChecked", "1");
                    map.put("e_name", "");
                    map.put("_id", "20");
                    map.put("sequence", "20");
                    map.put("attr_name", "实惠");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "超值特惠");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// 默认筛选热卖
                    bundle.putString("title", "热卖");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // {fix_price={aa=fix_price, is_show=1, p_id=3,
                    // icon=, isChecked=1, e_name=, _id=20, sequence=20,
                    // attr_name=实惠}}
                }
                if (jumpFrom.equals("liullanzhonggaoduan")) { // 浏览中高端几分钟
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    // int fenzhong = 1;
                    // try {
                    // fenzhong =
                    // Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                    // long doValue = fenzhong * 60 * 1000;

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, Object> mapRequest = new HashMap<String, Object>();
                    map.put("aa", "fix_price");
                    map.put("is_show", "1");
                    map.put("p_id", "3");
                    map.put("icon", "");
                    map.put("isChecked", "1");
                    map.put("e_name", "");
                    map.put("_id", "22");
                    map.put("sequence", "22");
                    map.put("attr_name", "轻奢");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "流行趋势");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// 默认筛选热卖
                    bundle.putString("title", "热卖");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // map={fix_price={aa=fix_price, is_show=1, p_id=3,
                    // icon=, isChecked=1, e_name=, _id=22, sequence=22,
                    // attr_name=轻奢}}
                }
                if (jumpFrom.equals("liulantianmeikeai")) { // 浏览甜美可爱几分钟
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    // int fenzhong = 1;
                    // try {
                    // fenzhong =
                    // Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                    // long doValue = fenzhong * 60 * 1000;

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, Object> mapRequest = new HashMap<String, Object>();
                    map.put("aa", "style");
                    map.put("is_show", "1");
                    map.put("p_id", "101");
                    map.put("icon", "");
                    map.put("isChecked", "1");
                    map.put("e_name", "");
                    map.put("_id", "105");
                    map.put("sequence", "3");
                    map.put("attr_name", "甜美可爱");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "萌系可爱风");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// 默认筛选热卖
                    bundle.putString("title", "热卖");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // map={style={aa=style, is_show=1, p_id=101, icon=,
                    // isChecked=1, e_name=, _id=105, sequence=3,
                    // attr_name=甜美可爱}}
                }
                if (jumpFrom.equals("liulantongqingmingyuan")) { // 浏览通勤名媛几分钟
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    // int fenzhong = 1;
                    // try {
                    // fenzhong =
                    // Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                    // long doValue = fenzhong * 60 * 1000;

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, Object> mapRequest = new HashMap<String, Object>();
                    map.put("aa", "style");
                    map.put("is_show", "1");
                    map.put("p_id", "101");
                    map.put("icon", "");
                    map.put("isChecked", "1");
                    map.put("e_name", "");
                    map.put("_id", "103");
                    map.put("sequence", "1");
                    map.put("attr_name", "通勤名媛");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "简约通勤");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// 默认筛选热卖
                    bundle.putString("title", "热卖");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // map={style={aa=style, is_show=1, p_id=101, icon=,
                    // isChecked=1, e_name=, _id=103, sequence=1,
                    // attr_name=通勤名媛}}
                }
                if (jumpFrom.equals("liulanyundongxiuxian")) { // 浏览运动休闲几分钟
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    // int fenzhong = 1;
                    // try {
                    // fenzhong =
                    // Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                    // long doValue = fenzhong * 60 * 1000;

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, Object> mapRequest = new HashMap<String, Object>();
                    map.put("aa", "style");
                    map.put("is_show", "1");
                    map.put("p_id", "101");
                    map.put("icon", "");
                    map.put("isChecked", "1");
                    map.put("e_name", "");
                    map.put("_id", "112");
                    map.put("sequence", "4");
                    map.put("attr_name", "运动休闲");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "运动休闲");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// 默认筛选热卖
                    bundle.putString("title", "热卖");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // map={style={aa=style, is_show=1, p_id=101, icon=,
                    // isChecked=1, e_name=, _id=112, sequence=4,
                    // attr_name=运动休闲}}
                }
                if (jumpFrom.equals("liulanjianyuebaida")) { // 浏览简约百搭几分钟
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    // int fenzhong = 1;
                    // try {
                    // fenzhong =
                    // Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                    // long doValue = fenzhong * 60 * 1000;

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, Object> mapRequest = new HashMap<String, Object>();
                    map.put("aa", "style");
                    map.put("is_show", "1");
                    map.put("p_id", "101");
                    map.put("icon", "");
                    map.put("isChecked", "1");
                    map.put("e_name", "");
                    map.put("_id", "750");
                    map.put("sequence", "5");
                    map.put("attr_name", "简约百搭");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "经典百搭");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// 默认筛选热卖
                    bundle.putString("title", "热卖");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // map={style={aa=style, is_show=1, p_id=101, icon=,
                    // isChecked=1, e_name=, _id=750, sequence=5,
                    // attr_name=简约百搭}}

                }
                if (jumpFrom.equals("liulanwenyifugu")) { // 浏览文艺复古几分钟
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    // int fenzhong = 1;
                    // try {
                    // fenzhong =
                    // Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                    // long doValue = fenzhong * 60 * 1000;
                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, Object> mapRequest = new HashMap<String, Object>();
                    map.put("aa", "style");
                    map.put("is_show", "1");
                    map.put("p_id", "101");
                    map.put("icon", "");
                    map.put("isChecked", "1");
                    map.put("e_name", "");
                    map.put("_id", "102");
                    map.put("sequence", "0");
                    map.put("attr_name", "文艺复古");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "文艺复古");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// 默认筛选热卖
                    bundle.putString("title", "热卖");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // map={style={aa=style, is_show=1, p_id=101, icon=,
                    // isChecked=1, e_name=, _id=102, sequence=0,
                    // attr_name=文艺复古}}
                }
                if (jumpFrom.equals("liulanqingtongzhuang")) { // 浏览通勤装几分钟
                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    doValuefenzhongOver = doValuefenzhong;
                    if (overtimer != null) {
                        overtimer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    overtimer = new Timer();
                    overtimer.schedule(overtask, 0, 1000); // timeTask

                    // int fenzhong = 1;
                    // try {
                    // fenzhong =
                    // Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                    // long doValue = fenzhong * 60 * 1000;
                    if (timer != null) {
                        timer.cancel();
                        SignListAdapter.isForceLookTimeOut = false;
                    }
                    timer = new Timer();
                    // 签到计时
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, Object> mapRequest = new HashMap<String, Object>();
                    map.put("aa", "occasion");
                    map.put("is_show", "1");
                    map.put("p_id", "4");
                    map.put("icon", "");
                    map.put("isChecked", "1");
                    map.put("e_name", "");
                    map.put("_id", "24");
                    map.put("sequence", "24");
                    map.put("attr_name", "通勤装");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "上班族必备");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// 默认筛选热卖
                    bundle.putString("title", "热卖");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // map={occasion={aa=occasion, is_show=1, p_id=4,
                    // icon=, isChecked=1, e_name=, _id=24, sequence=24,
                    // attr_name=通勤装}}
                }

                dismiss();
                break;
            case R.id.gobuy2:

                // if (jumpFrom.equals("jingxiwanchengtishikuang")) {


                if (jumpFrom.equals("liulan_sign_finish") || jumpFrom.equals("liulan_sign_chuanda_finish") || jumpFrom.equals(Pref.LIULAN_SIGN_UPPER_LIMIT)) {


                    if (ForceLookMatchActivity.instance != null) {
                        ForceLookMatchActivity.instance.finish();
                    }
                    if (SignActiveShopActivity.instance != null) {
                        SignActiveShopActivity.instance.finish();
                    }
                    if (ForceLookActivity.instance != null) {
                        ForceLookActivity.instance.finish();
                    }

                    if (null != ShopDetailsActivity.instance) {
                        ShopDetailsActivity.instance.finish();
                    }

                    if (CommonActivity.instance != null) {
                        CommonActivity.instance.finish();
                    }


                    // 跳至赚钱
                    SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                    Intent intent = new Intent(mContext, CommonActivity.class);
                    intent.putExtra("isTastComplete", true);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                } else {
                    CommonUtils.finishActivity(MainMenuActivity.instances);

                    Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                    intent2.putExtra("toYf", "toYf");
                    mContext.startActivity(intent2);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                }


                // }
                // if (jumpFrom.equals("share_sign_fanbei_finish")) {// 查看余额 余额翻倍
                // Intent intent = new Intent(context, MyWalletActivity.class);
                // ((FragmentActivity) context).startActivity(intent);
                // ((Activity)
                // context).overridePendingTransition(R.anim.slide_left_in,
                // R.anim.slide_match);
                // }
                // if (jumpFrom.equals("share_sign_jinbi_finish")) {// 升级金币 查看金币
                // Intent intentd = new Intent(context,
                // GoldCoinDetailActivity.class);
                // ((FragmentActivity) context).startActivity(intentd);
                // ((Activity)
                // context).overridePendingTransition(R.anim.slide_left_in,
                // R.anim.slide_match);
                // }
                // if (jumpFrom.equals("share_sign_jinquan_finish")) {// 升级金券 查看金券
                // Intent intent = new Intent(context, MyCouponsActivity.class);
                // context.startActivity(intent);
                // ((Activity)
                // context).overridePendingTransition(R.anim.slide_left_in,
                // R.anim.slide_match);
                // }
                dismiss();
                break;
            case R.id.liebiao:

                if (jumpFrom.equals("share_sign_fanbei_finish") || jumpFrom.equals("share_sign_jinbi_finish")
                        || jumpFrom.equals("share_sign_jinquan_finish")) {
                    Intent intent3 = new Intent((Activity) mContext, MainMenuActivity.class);
                    intent3.putExtra("toYf", "toYf");
                    mContext.startActivity(intent3);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                }
                if (jumpFrom.equals("liulan_sign_finish") || jumpFrom.equals(Pref.LIULAN_SIGN_UPPER_LIMIT) || jumpFrom.equals("liulan_sign_chuanda_finish")) {// 浏览个数任务完成 点击任务列表
                    // Intent intent = new Intent(getContext(),
                    // MainMenuActivity.class);
                    // intent.putExtra("Exit30", true);
                    // context.startActivity(intent);

//                    if (ForceLookMatchActivity.instance != null) {
//                        ForceLookMatchActivity.instance.finish();
//                    }
//                    if (SignActiveShopActivity.instance != null) {
//                        SignActiveShopActivity.instance.finish();
//                    }
//                    if (ForceLookActivity.instance != null) {
//                        ForceLookActivity.instance.finish();
//                    }
//                    if (CommonActivity.instance != null) {
//                        CommonActivity.instance.finish();
//                    }
//
//
//                    // 跳至赚钱
//                    SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
//                    Intent intent = new Intent(mContext, CommonActivity.class);
//                    intent.putExtra("isTastComplete", true);
//                    mContext.startActivity(intent);
//                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                }

                // if(ForceLookMatchActivity.instance != null){
                // ForceLookMatchActivity.instance.finish();
                // }
                // if(SignActiveShopActivity.instance != null){
                // SignActiveShopActivity.instance.finish();
                // }
                // if(ForceLookActivity.instance != null){
                // ForceLookActivity.instance.finish();
                // }
                //
                //
//                if (!jumpFrom.equals("goumairenwuwancheng")) {
//                    ((Activity) mContext).finish();
//
//                }
                dismiss();


                break;
            case R.id.icon_close:
                dismiss();
                break;

            default:
                break;
        }
    }

//    TimerTask task = new TimerTask() {
//        @Override
//        public void run() {
//            ((Activity) mContext).runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//
//                    // 分钟的话奖励是一次发齐的
//                    // ToastUtil.showShortText(context, "浏览时间到，调签到接口");
//
//                    if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
//                        SignListAdapter.isForceLookTimeOut = true;
//
//                        // 签到
//                        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mContext, 0) {
//
//                            @Override
//                            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//                                    throws Exception {
//
//                                // 如果不是余额翻倍任务就不用管
//
//                                return ComModel2.getSignIn(context, false, false,
//                                        SignListAdapter.indexMap.get(YConstance.SCAN_SHOP_TIME),
//                                        SignListAdapter.classMap.get(YConstance.SCAN_SHOP_TIME));
//
//                            }
//
//                            protected boolean isHandleException() {
//                                return true;
//                            }
//
//                            ;
//
//                            @Override
//                            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result,
//                                                         Exception e) {
//                                super.onPostExecute(context, result, e);
//                                if (e == null && result != null) {
//                                    // SharedPreferencesUtil.saveBooleanData(context,
//                                    // "isqitashow", true);
//                                    SignListAdapter.minuteMap.clear();
//                                    new SignFinishDialogNew(YJApplication.diolgContext, R.style.DialogQuheijiao, "bankuailiulanwancheng")
//                                            .show();
//                                    refreshData.timeOut();
//
//                                } else {
//
//                                }
//
//                            }
//
//                        }.execute();
//                    }
//
//                }
//            });
//
//        }
//3
//    };

    SignRefreshDataListener refreshData;

    public interface SignRefreshDataListener {
        public void timeOut();
    }

}
