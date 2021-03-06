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

    public static long doValuefenzhong = 1 * 60 * 1000; // ???????????????
    public long overTime = 10000L; // ???????????????

    private LinearLayout ll_dojishi;

    public static long minute; // ????????????
    public static long second; // ????????????

    public static long doValuefenzhongOver = 1 * 60 * 1000; // ???????????????

    // ?????????
    TimerTask overtask = new TimerTask() {
        @Override
        public void run() {

            ((Activity) mContext).runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {

                    // doValuefenzhongOver = doValuefenzhong;

                    doValuefenzhongOver -= 1000;

                    // ??????????????????
                    // minute = doValuefenzhong / 60000;
                    // second = (doValuefenzhong % 60000) / 1000;
                    // minute???????????? second?????????
                    // ToastUtil.showShortText(context, minute+"??????"+
                    // second+"???");

                    if (doValuefenzhong <= 0) {
                        // timer.cancel();
                        // 1????????????
                    }
                }
            });
        }
    };
    // ?????????---????????????????????????
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

                    // ??????????????????
                    minute = doValuefenzhongOver / 60000;
                    second = (doValuefenzhongOver % 60000) / 1000;
                    // minute???????????? second?????????
                    // ToastUtil.showShortText(context, minute+"??????"+
                    // second+"???");

                    if (SignListAdapter.minuteMap.size() != 0) {
                        // title.setText("?????????????????????...");
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

        //????????????????????????
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

        gobuy1 = (Button) findViewById(R.id.gobuy1); // ????????????????????????
        gobuy2 = (Button) findViewById(R.id.gobuy2);
        liebiao = (Button) findViewById(R.id.liebiao);
        icon_close = (ImageView) findViewById(R.id.icon_close);

        rl_twobt = (RelativeLayout) findViewById(R.id.rl_twobt); // ????????????

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


        if (SignListAdapter.minuteMap.size() != 0) { // ????????????

            if (null != overtimershow) {
                overtimershow.cancel();
            }
            overtimershow = new Timer();
            overtimershow.schedule(overtaskShow, 0, 1000); // timeTask
            doValuefenzhong = doValuefenzhongOver; // ???????????????????????????

        }

        initData();

    }

    private void initData() {

        try {
            doValue = SignListAdapter.doValue.split(",")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (jumpFrom.equals("jingxiwanchengtishikuang")) {// ??????????????????
            title.setText("???????????????");
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText("????????????~");
            tv2.setVisibility(View.VISIBLE);
            tv2.setText("?????????????????????????????????????????????????????????????????????????????????~");
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.VISIBLE);
            tv4.setText("??????????????????????????????/?????????????????????????????????????????????????????????");
            gobuy1.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.VISIBLE);
            gobuy2.setText("?????????");
            liebiao.setText("?????????");

        }

        if (jumpFrom.equals("goumairenwuwancheng")) {// ??????????????????
            title.setText("???????????????");
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText("????????????~");
            tv2.setVisibility(View.VISIBLE);
            tv2.setText("???????????????????????????????????????????????????~???????????????????????????");
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.VISIBLE);
            tv4.setText("???????????????????????????????????????????????????????????????????????????????????????");
            gobuy1.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.VISIBLE);
            gobuy2.setText("?????????");
            liebiao.setText("?????????");

        }

        if (jumpFrom.equals("liulanhuodongjishitishi")) {

            // title.setText("????????????");
            // tv2.setVisibility(View.GONE);
            // tv3.setVisibility(View.GONE);
            // tv4.setVisibility(View.GONE);
            // rl_twobt.setVisibility(View.GONE);
            //
            // tv1.setText("??????????????????" + doValue + "??????????????????????????????~");
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
            // gobuy1.setText("???????????????");
            //

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "????????????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }

        }
        if (jumpFrom.equals("liulanfenzhongtishi")) { // ??????????????????????????????????????????????????????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "??????" + app_name + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }

        }

        if (jumpFrom.equals("duobaowanchen")) { // ????????????

            title.setText("???????????????");
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText("???????????????");
            tv1.setTextSize(16);

            tv2.setText("??????????????????" + duobaoNumber);
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
            tv4.setText("???????????????????????????????????????~???????????????~");
            rl_twobt.setVisibility(View.GONE);
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("?????????");
        }

        if (jumpFrom.equals("liulandapeitishi")) { // ?????????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "??????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }

        }
        if (jumpFrom.equals("liulanzhuantitishi")) { // ??????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }

        }

        if (jumpFrom.equals("liulangouwuyemian")) { // ???????????????X??????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = app_name + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");

            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }

            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }

        }


        if (jumpFrom.equals("shequshouye")) { // ??????????????????X??????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = app_name + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");

            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }

            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }

        }

        if (jumpFrom.equals("liulanwaitaotishi")) { // ??????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "?????????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }

        }
        if (jumpFrom.equals("liulanxiaowaitao")) { // ???????????????
            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "??????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }
        if (jumpFrom.equals("liulanlianyiqun")) { // ?????????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "??????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }
        if (jumpFrom.equals("liulanhanxi")) { // ??????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "??????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }
        if (jumpFrom.equals("liulanoouxi")) { // ??????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "??????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }
        if (jumpFrom.equals("liulanjianyuebaida")) { // ????????????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "????????????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }
        if (jumpFrom.equals("liulanwenyifugu")) { // ????????????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "????????????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }
        if (jumpFrom.equals("liulanqingtongzhuang")) { // ?????????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "?????????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }
        if (jumpFrom.equals("liulanshihui")) { // ??????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "??????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }
        if (jumpFrom.equals("liullanzhonggaoduan")) { // ????????????????????? ????????????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "??????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }
        if (jumpFrom.equals("liulantianmeikeai")) { // ?????????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "?????????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }
        if (jumpFrom.equals("liulantongqingmingyuan")) { // ????????????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "??????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }
        if (jumpFrom.equals("liulanyundongxiuxian")) { // ????????????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "????????????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }

        if (jumpFrom.equals("liulanshangyitishi")) { // ??????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "?????????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }

        if (jumpFrom.equals("liulanqunzitishi")) { // ??????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "?????????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }

        if (jumpFrom.equals("liulankuzitishi")) { // ??????????????????
            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "?????????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }

        if (jumpFrom.equals("liulanremaitishi")) { // ??????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "?????????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }

        if (jumpFrom.equals("liulanshangxintishi")) { // ????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "??????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }

        if (jumpFrom.equals("liulantaozhuangtishi")) { // ??????????????????

            title.setText("????????????");
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            ll_dojishi.setVisibility(View.VISIBLE);

            String textortherjifen = "?????????????????????" + doValue + "?????????????????????bling bling????????????????????????~";
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
            gobuy1.setText("???????????????");
            if (Integer.parseInt(doValue) >= 10) {
                tv_fenzhong.setText("" + doValue);
            } else {
                tv_fenzhong.setText("0" + doValue);
            }
            tv_miao.setText("00");
            if (SignListAdapter.minuteMap.size() != 0) {
                title.setText("?????????????????????...");
            }
        }

        if (jumpFrom.equals("liulan_sign_finish") || jumpFrom.equals("share_sign_finish")
                || jumpFrom.equals("liulan_sign_chuanda_finish")) { // ????????????????????????
            // ???
            // ??????????????????

            title.setText("???????????????");

            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            if (jumpFrom.equals("liulan_sign_finish")) {
                finishScanMode(0);
            } else if (jumpFrom.equals("share_sign_finish")) {
                tv1.setText("????????????~");
            } else if (jumpFrom.equals("liulan_sign_chuanda_finish")) {
                tv1.setText("??????????????????~");
            }
            tv1.setTextSize(16);
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            tv1.setVisibility(View.VISIBLE);

            String textAward = awards + "????????????????????????????????????????????????~";
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

                gobuy2.setText("????????????");
                liebiao.setText("?????????????????????");
            } else if (jumpFrom.equals("share_sign_finish")) {
                rl_twobt.setVisibility(View.GONE);
                gobuy1.setVisibility(View.VISIBLE);
                gobuy1.setText("?????????~");
            } else if (jumpFrom.equals("liulan_sign_chuanda_finish")) {
                rl_twobt.setVisibility(View.VISIBLE);
                gobuy1.setVisibility(View.GONE);

                gobuy2.setTextSize(14);
                liebiao.setTextSize(14);


                gobuy2.setText("????????????");
                liebiao.setText("?????????????????????");
            }

            //??????????????????
            rl_twobt.setVisibility(View.VISIBLE);
            gobuy1.setVisibility(View.GONE);

            gobuy2.setTextSize(14);
            liebiao.setTextSize(14);


            gobuy2.setText("?????????????????????");
            liebiao.setText("????????????");

        }

        if (jumpFrom.equals(Pref.LIULAN_SIGN_UPPER_LIMIT)) { // ???????????? ???????????? ????????????
            // ???
            // ??????????????????

            title.setText("???????????????");

            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rl_twobt.setVisibility(View.GONE);
            tv1.setText("??????????????????~");
            tv1.setTextSize(16);
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            tv1.setVisibility(View.VISIBLE);

            tv2.setText("?????????????????????????????????????????????????????????");
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

//            gobuy2.setText("????????????");
//            liebiao.setText("?????????????????????");

            gobuy2.setText("?????????????????????");
            liebiao.setText("????????????");
        }

        if (jumpFrom.contains("addshopcarttishi")) { // ??????X??????????????????????????????????????????

            title.setText("????????????");

            tv2.setVisibility(View.GONE);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText("????????????????????????????????????????????????????????????");
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
            gobuy1.setText("???????????????~~");
        }

        if (jumpFrom.equals("share_sign_fanbei_finish")) { // ????????????

            title.setText("????????????");
            tv1.setTextSize(16);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText("??????????????????????????????~");
            tv2.setVisibility(View.VISIBLE);
            tv2.setText("???????????????????????????????????????");
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.VISIBLE);
            tv4.setText("???????????????????????????24??????????????????????????????~");

            rl_twobt.setVisibility(View.VISIBLE);
            gobuy1.setVisibility(View.GONE);
            gobuy2.setText("????????????");
            liebiao.setText("?????????");
        }
        if (jumpFrom.equals("share_sign_jinbi_finish")) { // ????????????

            title.setText("???????????????");
            tv1.setTextSize(16);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText("?????????????????????????????????~");
            tv2.setVisibility(View.VISIBLE);
            tv2.setText("?????????????????????????????????");
            tv3.setVisibility(View.VISIBLE);
            tv3.setText("?????????500????????????????????????5.01??????????????????");
            tv4.setVisibility(View.VISIBLE);
            tv4.setText("?????????????????????24??????????????????????????????~");

            rl_twobt.setVisibility(View.VISIBLE);
            gobuy1.setVisibility(View.GONE);
            gobuy2.setText("????????????");
            liebiao.setText("?????????");
        }
        if (jumpFrom.equals("share_sign_jinquan_finish")) { // ????????????

            title.setText("???????????????");
            tv1.setTextSize(16);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText("????????????????????????????????????~");
            tv2.setVisibility(View.VISIBLE);
            tv2.setText("?????????????????????????????????");
            tv3.setVisibility(View.VISIBLE);
            tv3.setText("?????????5???????????????????????????5.01??????????????????");
            tv4.setVisibility(View.VISIBLE);
            tv4.setText("?????????????????????24??????????????????????????????~");

            rl_twobt.setVisibility(View.VISIBLE);
            gobuy1.setVisibility(View.GONE);
            gobuy2.setText("????????????");
            liebiao.setText("?????????");
        }

        if (jumpFrom.equals("jingxirenwushuoming")) { // ??????????????????

            title.setText("??????????????????");

            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.GONE);
            tv1.setText("????????????????????????????????????????????????????????????????????????????????????????????????");
            tv1.setTextColor(Color.parseColor("#7D7D7D"));
            tv1.setTextSize(14);
            tv3.setVisibility(View.VISIBLE);
            tv3.setText("?????????????????????????????????600?????????????????????????????????");
            tv4.setVisibility(View.GONE);

            rl_twobt.setVisibility(View.GONE);
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("?????????~");
        }
        if (jumpFrom.equals("goumaishuoming")) { // ??????????????????

            title.setText("??????????????????");

            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.GONE);
            String miaoshu = "";

            String where = "type_name=??????&notType=true";
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

            // if (where.equals("collection=shopping_page")) {// ??????
            // miaoshu = "???????????????" + buyCount + "?????????";
            //
            // } else if (where.equals("collection=collocation_shop")) {// ??????
            // miaoshu = "???????????????" + buyCount + "?????????";
            //
            // } else if (where.equals("collection=shop_activity")) {// ??????
            // miaoshu = "???????????????" + buyCount + "?????????";
            //
            // } else if (where.equals("collection=csss_shop")) {// ??????
            // miaoshu = "???????????????" + buyCount + "?????????";
            //
            // }
            //
            // else if (where.equals("type_name=??????&notType=true")) {// ??????
            // miaoshu = "???????????????" + buyCount + "?????????";
            //
            // }
            //
            // else if (where.equals("collection=shop_home")) {// ??????
            // miaoshu = "????????????" + buyCount + "?????????";
            //
            // }
            //
            // else { // ??????????????????????????????
            // miaoshu = "???????????????" + buyCount + "?????????";
            //
            // }

            String namne = "??????";
            try {
                namne = app_name.split("-")[1];
            } catch (Exception e) {
            }

            miaoshu = "??????" + buyCount + "???" + namne + "??????";

            tv1.setText("??????" + miaoshu + "???????????????????????????????????????????????????????????????????????????????????????????????????????????????~???????????????????????????????????????????????????????????????????????????");
            tv1.setTextColor(Color.parseColor("#7D7D7D"));
            tv1.setTextSize(14);
            tv3.setVisibility(View.GONE);
            // tv3.setText("?????????????????????????????????600?????????????????????????????????");
            tv4.setVisibility(View.GONE);

            rl_twobt.setVisibility(View.GONE);
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("?????????~");
        }

    }

    /**
     * ???????????? mode = 1 ????????? mode = 0 ?????? --- ??????????????????
     */
    private void finishScanMode(int mode) {

        String bankuai = SignListAdapter.doValue.split(",")[0];
        if (bankuai.equals("collection=collocation_shop")) {
            tv1.setText("??????????????????????????????~");
        } else if (bankuai.equals("collection=browse_shop")) {
            tv1.setText("?????????????????????????????????~");
        } else if (bankuai.equals("collection=shop_activity")) {
            tv1.setText("????????????????????????????????????~");
        } else if (bankuai.equals("type2=11")) {
            tv1.setText("??????????????????????????????~");
        } else if (bankuai.equals("type2=23")) {
            tv1.setText("??????????????????????????????~");
        } else if (bankuai.equals("favorite=29")) {
            tv1.setText("??????????????????????????????~");
        } else if (bankuai.equals("favorite=30")) {
            tv1.setText("??????????????????????????????~");
        } else if (bankuai.equals("fix_price=20")) {
            tv1.setText("??????????????????????????????~");
        } else if (bankuai.equals("fix_price=22")) {
            tv1.setText("??????????????????????????????~");
        } else if (bankuai.equals("style=105")) {
            tv1.setText("?????????????????????????????????~");
        } else if (bankuai.equals("style=103")) {
            tv1.setText("??????????????????????????????~");
        } else if (bankuai.equals("style=112")) {
            tv1.setText("??????????????????????????????~");
        } else if (bankuai.equals("style=750")) {
            tv1.setText("??????????????????????????????~");
        } else if (bankuai.equals("style=102")) {
            tv1.setText("???????????????????????? ??????~");
        } else if (bankuai.equals("occasion=24")) {
            tv1.setText("?????????????????????????????????~");
        } else {
            tv1.setText("??????????????????~");
        }

    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.gobuy1: // ?????????

                if (jumpFrom.equals("duobaowanchen")) { // ???????????????????????????
                    CommonUtils.finishActivity(MainMenuActivity.instances);

                    Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                    intent2.putExtra("toYf", "toYf");
                    mContext.startActivity(intent2);
                }

                if (jumpFrom.equals("jingxitishi")) { // ???????????????????????????
                    CommonUtils.finishActivity(MainMenuActivity.instances);

                    Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                    intent2.putExtra("toYf", "toYf");
                    mContext.startActivity(intent2);
                }
//			if (jumpFrom.equals("jingxirenwushuoming")) { // ???????????????????????????
//
//				Intent intent = new Intent(mContext, ForceLookActivity.class);
//				intent.putExtra("id", "6");
//				intent.putExtra("title", "??????");
//				mContext.startActivity(intent);
//				((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//				// Intent intent2 = new Intent((Activity) context,
//				// MainMenuActivity.class);
//				// intent2.putExtra("toYf", "toYf");
//				// context.startActivity(intent2);
//			}

                if (jumpFrom.equals("goumaishuoming") || jumpFrom.equals("jingxirenwushuoming")) { // ??????????????????





                    String where = "type_name=??????&notType=true";
                    try {
                        where = app_name.split("-")[0].split(",")[0];
                    } catch (Exception e) {
                    }

                    if (where.equals("collection=shopping_page")) {// ??????
                        // ????????????
                        CommonUtils.finishActivity(MainMenuActivity.instances);

                        Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                        intent2.putExtra("toShop", "toShop");
                        mContext.startActivity(intent2);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    } else if (where.equals("collection=collocation_shop")) {// ??????

                        Intent intent = new Intent(mContext, ForceLookMatchActivity.class);
                        intent.putExtra("type", "1");


                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("isGaoMai", true);


                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    } else if (where.equals("collection=shop_activity")) {// ??????

                        // ?????????????????????

                        Intent intent = new Intent(mContext, SignActiveShopActivity.class);

                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("isGaoMai", true);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    } else if (where.equals("collection=csss_shop")) {// ??????
                        Intent intent = new Intent(mContext, ForceLookMatchActivity.class);

                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("isGaoMai", true);
                        intent.putExtra("type", "2");
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    } else if (where.equals("type_name=??????&notType=true")) {// ??????

                        Intent intent = new Intent(mContext, ForceLookActivity.class);
                        intent.putExtra("id", "6");
                        intent.putExtra("title", "??????");

                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("isGaoMai", true);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    } else if (where.equals("collection=shop_home")) {// ??????

                        // ????????????
                        CommonUtils.finishActivity(MainMenuActivity.instances);

                        Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                        intent2.putExtra("toHome", "toHome");
                        mContext.startActivity(intent2);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    } else { // ??????????????????????????????

                        Intent intent = new Intent(mContext, ForceLookActivity.class);
                        intent.putExtra("id", "6");
                        intent.putExtra("title", "??????");

                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("isGaoMai", true);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    }
                }

                if (jumpFrom.equals("liulandapeitishi")) { // ??????????????????
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
                    // ????????????
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask
                    // timer.schedule(task, 5*1000); // timeTask

                }


                if (jumpFrom.equals("addshopcarttishi_dapei")) { // ????????????---????????????


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


                if (jumpFrom.equals("liulanzhuantitishi")) { // ??????????????????
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
                    // ????????????
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask
                    // timer.schedule(task, 5*1000); // timeTask

                }


                if (jumpFrom.equals("addshopcarttishi_zhuanti")) { // ??????--????????????


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


                if (jumpFrom.equals("addshopcarttishi_huodong")) { // ??????--????????????


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



                if (jumpFrom.equals("addshopcarttishi_qitajihe")) { // ????????????--????????????


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
                    intent.putExtra("title", "??????");
                    intent.putExtra("pinJievalue", leiTX);

                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isCrazy", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);


                }



                if (jumpFrom.equals("liulangouwuyemian")) { // ????????????X??????

                    // ????????????
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
                    // ????????????
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask
                    // timer.schedule(task, 5*1000); // timeTask

                }


                if (jumpFrom.equals("addshopcarttishi_gowuye")) { // ?????????-----????????????



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




                if (jumpFrom.equals("shequshouye")) { // ??????????????????X??????

                    // ????????????
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
                    // ????????????
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask
                    // timer.schedule(task, 5*1000); // timeTask

                }



                if (jumpFrom.equals("liulanwaitaotishi")) { // ??????????????????

                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("title", "???????????????");
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
                    // ????????????
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }

                if (jumpFrom.equals("liulanfenzhongtishi")) { // ??????????????????????????????????????????????????????????????????

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
                    // ????????????
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }

                if (jumpFrom.equals("liulanshangyitishi")) { // ???????????????
                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("title", "???????????????");
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
                    // ????????????
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }

                if (jumpFrom.equals("liulanqunzitishi")) { // ??????????????????
                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("title", "???????????????");
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
                    // ????????????
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }

                if (jumpFrom.equals("liulankuzitishi")) { // ??????????????????
                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("title", "???????????????");
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
                    // ????????????
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }

                if (jumpFrom.equals("liulanremaitishi")) { // ???????????????
                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("title", "???????????????");
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
                    // ????????????
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }
                if (jumpFrom.equals("liulanshangxintishi")) { // ????????????
                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("title", "????????????");
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
                    // ????????????
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }
                if (jumpFrom.equals("liulantaozhuangtishi")) { // ??????????????????
                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("title", "???????????????");
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
                    // ????????????
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                }

                if (jumpFrom.equals("share_sign_finish") || jumpFrom.equals("liulan_sign_chuanda_finish")) {// ???????????????????????????
                    // ????????????Dialog
                    CommonUtils.finishActivity(MainMenuActivity.instances);

                    Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                    intent2.putExtra("toYf", "toYf");
                    mContext.startActivity(intent2);

                }

                if (jumpFrom.equals("addshopcarttishi")) { // ?????????????????????

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

                if (jumpFrom.equals("liulanxiaowaitao")) { // ????????????????????????

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
                    // ????????????
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                    SignListAdapter.minuteMap.put("jumpFrom", jumpFrom);
                    SignListAdapter.minuteMap.put("liulanfeizhong",
                            Integer.parseInt(SignListAdapter.doValueLiulan.split(",")[1]) + "");

                    // ??????????????????????????????

                    HashMap<String, String> item = new HashMap<String, String>();

                    item.put("s_show", "1");
                    item.put("p_id", "1");
                    item.put("icon", "shop/type/xiaowaitao.png");
                    item.put("_id", "11");
                    item.put("group_flag", "");
                    item.put("sequence", "2");
                    item.put("sort_name", "????????????");

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
                if (jumpFrom.equals("liulanhuodongjishitishi")) { // ???????????????????????????
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
                        // ????????????
                        timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask
                    } else {
                        ToastUtil.showShortText(mContext, "?????????????????????");
                        return;
                    }

                    // ?????????????????????

                    mContext.startActivity(new Intent(mContext, SignActiveShopActivity.class));
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                }

                if (jumpFrom.equals("liulanlianyiqun")) { // ????????????????????????
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

                    // ????????????
                    timer.schedule(YJApplication.signFenzhongTask(refreshData), doValuefenzhong); // timeTask

                    // ??????????????????????????????
                    HashMap<String, String> item = new HashMap<String, String>();
                    item.put("s_show", "1");
                    item.put("p_id", "1");
                    item.put("icon", "shop/type/lianyiqun.png");
                    item.put("_id", "23");
                    item.put("group_flag", "");
                    item.put("sequence", "1");
                    item.put("sort_name", "????????????");

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
                if (jumpFrom.equals("liulanhanxi")) { // ?????????????????????
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
                    // ????????????
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
                    map.put("attr_name", "??????");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isSign", true);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "????????????");
                    intent.putExtra("isWhere", "");// ?????????????????? ?????????????????????
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// ??????????????????
                    bundle.putString("title", "??????");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // {favorite={aa=favorite, is_show=1, p_id=5, icon=,
                    // isChecked=1, e_name=, _id=29, sequence=29,
                    // attr_name=??????}}
                }
                if (jumpFrom.equals("liulanoouxi")) { // ?????????????????????
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
                    // ????????????
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
                    map.put("attr_name", "??????");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "????????????");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// ?????????????????? ?????????????????????
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// ??????????????????
                    bundle.putString("title", "??????");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // {favorite={aa=favorite, is_show=1, p_id=5, icon=,
                    // isChecked=1, e_name=, _id=30, sequence=30,
                    // attr_name=??????}}
                }
                if (jumpFrom.equals("liulanshihui")) { // ?????????????????????
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
                    // ????????????
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
                    map.put("attr_name", "??????");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "????????????");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// ?????????????????? ?????????????????????
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// ??????????????????
                    bundle.putString("title", "??????");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // {fix_price={aa=fix_price, is_show=1, p_id=3,
                    // icon=, isChecked=1, e_name=, _id=20, sequence=20,
                    // attr_name=??????}}
                }
                if (jumpFrom.equals("liullanzhonggaoduan")) { // ????????????????????????
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
                    // ????????????
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
                    map.put("attr_name", "??????");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "????????????");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// ?????????????????? ?????????????????????
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// ??????????????????
                    bundle.putString("title", "??????");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // map={fix_price={aa=fix_price, is_show=1, p_id=3,
                    // icon=, isChecked=1, e_name=, _id=22, sequence=22,
                    // attr_name=??????}}
                }
                if (jumpFrom.equals("liulantianmeikeai")) { // ???????????????????????????
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
                    // ????????????
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
                    map.put("attr_name", "????????????");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "???????????????");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// ?????????????????? ?????????????????????
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// ??????????????????
                    bundle.putString("title", "??????");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // map={style={aa=style, is_show=1, p_id=101, icon=,
                    // isChecked=1, e_name=, _id=105, sequence=3,
                    // attr_name=????????????}}
                }
                if (jumpFrom.equals("liulantongqingmingyuan")) { // ???????????????????????????
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
                    // ????????????
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
                    map.put("attr_name", "????????????");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "????????????");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// ?????????????????? ?????????????????????
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// ??????????????????
                    bundle.putString("title", "??????");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // map={style={aa=style, is_show=1, p_id=101, icon=,
                    // isChecked=1, e_name=, _id=103, sequence=1,
                    // attr_name=????????????}}
                }
                if (jumpFrom.equals("liulanyundongxiuxian")) { // ???????????????????????????
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
                    // ????????????
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
                    map.put("attr_name", "????????????");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "????????????");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// ?????????????????? ?????????????????????
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// ??????????????????
                    bundle.putString("title", "??????");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // map={style={aa=style, is_show=1, p_id=101, icon=,
                    // isChecked=1, e_name=, _id=112, sequence=4,
                    // attr_name=????????????}}
                }
                if (jumpFrom.equals("liulanjianyuebaida")) { // ???????????????????????????
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
                    // ????????????
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
                    map.put("attr_name", "????????????");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "????????????");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// ?????????????????? ?????????????????????
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// ??????????????????
                    bundle.putString("title", "??????");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // map={style={aa=style, is_show=1, p_id=101, icon=,
                    // isChecked=1, e_name=, _id=750, sequence=5,
                    // attr_name=????????????}}

                }
                if (jumpFrom.equals("liulanwenyifugu")) { // ???????????????????????????
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
                    // ????????????
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
                    map.put("attr_name", "????????????");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "????????????");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// ?????????????????? ?????????????????????
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// ??????????????????
                    bundle.putString("title", "??????");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // map={style={aa=style, is_show=1, p_id=101, icon=,
                    // isChecked=1, e_name=, _id=102, sequence=0,
                    // attr_name=????????????}}
                }
                if (jumpFrom.equals("liulanqingtongzhuang")) { // ????????????????????????
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
                    // ????????????
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
                    map.put("attr_name", "?????????");
                    mapRequest.put(map.get("aa"), map);
                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("shop_name", "???????????????");
                    intent.putExtra("isSign", true);
                    intent.putExtra("isWhere", "");// ?????????????????? ?????????????????????
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", mapRequest);
                    bundle.putString("id", 6 + "");// ??????????????????
                    bundle.putString("title", "??????");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    // map={occasion={aa=occasion, is_show=1, p_id=4,
                    // icon=, isChecked=1, e_name=, _id=24, sequence=24,
                    // attr_name=?????????}}
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


                    // ????????????
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
                // if (jumpFrom.equals("share_sign_fanbei_finish")) {// ???????????? ????????????
                // Intent intent = new Intent(context, MyWalletActivity.class);
                // ((FragmentActivity) context).startActivity(intent);
                // ((Activity)
                // context).overridePendingTransition(R.anim.slide_left_in,
                // R.anim.slide_match);
                // }
                // if (jumpFrom.equals("share_sign_jinbi_finish")) {// ???????????? ????????????
                // Intent intentd = new Intent(context,
                // GoldCoinDetailActivity.class);
                // ((FragmentActivity) context).startActivity(intentd);
                // ((Activity)
                // context).overridePendingTransition(R.anim.slide_left_in,
                // R.anim.slide_match);
                // }
                // if (jumpFrom.equals("share_sign_jinquan_finish")) {// ???????????? ????????????
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
                if (jumpFrom.equals("liulan_sign_finish") || jumpFrom.equals(Pref.LIULAN_SIGN_UPPER_LIMIT) || jumpFrom.equals("liulan_sign_chuanda_finish")) {// ???????????????????????? ??????????????????
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
//                    // ????????????
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
//                    // ????????????????????????????????????
//                    // ToastUtil.showShortText(context, "?????????????????????????????????");
//
//                    if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
//                        SignListAdapter.isForceLookTimeOut = true;
//
//                        // ??????
//                        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mContext, 0) {
//
//                            @Override
//                            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//                                    throws Exception {
//
//                                // ??????????????????????????????????????????
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
