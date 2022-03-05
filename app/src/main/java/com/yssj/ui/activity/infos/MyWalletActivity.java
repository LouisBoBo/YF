package com.yssj.ui.activity.infos;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.YConstance.Pref;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.CheckPwdInfo;
import com.yssj.entity.MyWalletData;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.model.ComModel;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.MyYJactivity;
import com.yssj.ui.activity.SignDrawalLimitActivity;
import com.yssj.ui.activity.WithdrawalLimitActivity;
import com.yssj.ui.activity.setting.SettingCommonFragmentActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.NoTixianEduDialog;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LimitInputTextWatcher;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YCache;

import java.text.DecimalFormat;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/***
 * @author Administrator 我的钱包
 */
public class MyWalletActivity extends BasicActivity {

    @Bind(R.id.tv_yue)
    TextView tvYue;
    @Bind(R.id.tv_my_shouyi)
    TextView tvMyShouyi; //已经改成购物余额
    private TextView tv_keyixian_shouyi;//已经改成可提现余额
    private TextView tvTitle_base;
    private TextView tv_my_coupons_counts, rel_withdrawal;
    private RelativeLayout rel_fina_detail, rel_my_coupons, rel_my_card;
    private LinearLayout img_back, ll_yj;
    private ImageView img_right_icon;

    public static Activity instans;

    private String balance = "0.00";
    private Button btn_right;
    private Context mContext;
    private int isVip;

    private String extract;//可提现收益

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		getActionBar().hide();
        setContentView(R.layout.my_wallet_activity);
        ButterKnife.bind(this);
        instans = this;
        mContext = this;
        isVip = getIntent().getIntExtra("isVip", 0);
        initView();
        initData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonUtils.disableScreenshots(this);

    }


    private void initView() {
        tv_keyixian_shouyi = (TextView) findViewById(R.id.tv_keyixian_shouyi);
        btn_right = (Button) findViewById(R.id.btn_right);
        btn_right.setOnClickListener(MyWalletActivity.this);
        btn_right.setVisibility(View.VISIBLE);
        btn_right.setTextSize(14);
        btn_right.setText("增加提现额度");
        btn_right.setVisibility(View.GONE);
        // btn_right.setOnClickListener(this);
        // mybg = (ScrollView) findViewById(R.id.mybg);
        // mybg.setBackgroundColor(Color.WHITE);
        rel_withdrawal = (TextView) findViewById(R.id.rel_withdrawal);
        ll_yj = findViewById(R.id.ll_yj);
        ll_yj.setOnClickListener(this);
        findViewById(R.id.tv_yjtx).setOnClickListener(this);


        rel_fina_detail = (RelativeLayout) findViewById(R.id.rel_fina_detail);
        rel_fina_detail.setOnClickListener(this);
        rel_my_coupons = (RelativeLayout) findViewById(R.id.rel_my_coupons);
        rel_my_coupons.setOnClickListener(this);
        rel_my_card = (RelativeLayout) findViewById(R.id.rel_my_card);
        rel_my_card.setOnClickListener(this);

        findViewById(R.id.rel_pay_pass).setOnClickListener(this);

        tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("我的钱包");

        /*
         * 右上角点点点
         */
        img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
        img_right_icon.setVisibility(View.GONE);
        img_right_icon.setImageResource(R.drawable.mine_message_center);
        img_right_icon.setOnClickListener(this);
        img_right_icon.setVisibility(View.GONE);

        tv_my_coupons_counts = (TextView) findViewById(R.id.tv_my_coupons_counts); // 优惠券张数

        img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        findViewById(R.id.rel_man_sale).setOnClickListener(this);
        findViewById(R.id.rel_man_sale).setVisibility(View.GONE);
    }

    private void initData() {


        YConn.httpPost(mContext, YUrl.MY_WALLET_INFO, new HashMap<String, String>(), new HttpListener<MyWalletData>() {
            @Override
            public void onSuccess(MyWalletData myWalletData) {


                rel_withdrawal.setOnClickListener(MyWalletActivity.this);
                balance = myWalletData.getBalance() + "";
                extract = myWalletData.getExtract() + "";
                tv_my_coupons_counts.setText(myWalletData.getConponCount() + "张");
                tvYue.setText("¥ " + new DecimalFormat("#0.00").format(myWalletData.getRaffleMoney()));//上
                tvMyShouyi.setText(new DecimalFormat("#0.00").format(myWalletData.getExtract()));//左
                tv_keyixian_shouyi.setText(new DecimalFormat("#0.00").format(myWalletData.getExt_money()));//右


            }

            @Override
            public void onError() {

            }
        });

    }


    private void checkPwd() {

        new SAsyncTask<Void, Void, CheckPwdInfo>(MyWalletActivity.this) {

            @Override
            protected CheckPwdInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel.checkPWD(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, CheckPwdInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    Intent intent;
                    if (result != null && "1".equals(result.getStatus()) && "1".equals(result.getFlag())) {
                        intent = new Intent(MyWalletActivity.this, SetMyPayPassActivity.class);
                        startActivity(intent);
                    } else if (result != null && "1".equals(result.getStatus()) && "2".equals(result.getFlag())) {
                        // ToastUtil.showLongText(context,
                        // "请到【设置】-【账户与安全】修改密码");
                        intent = new Intent(MyWalletActivity.this, SettingCommonFragmentActivity.class);
                        intent.putExtra("flag", "payPasswordFragment");
                        intent.putExtra("wallet", "wallet");
                        startActivity(intent);
                    } else {
                        ToastUtil.showLongText(context, "糟糕，出错了~~~");
                    }
                }
            }

        }.execute((Void[]) null);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.rel_man_sale:// 销售管理
                // intent = new Intent(this, ManSaleActivity.class);
//			intent = new Intent(this, ManSaleDetailsActivity.class);
//			startActivity(intent);
                break;
            case R.id.rel_fina_detail:// 账户明细
                intent = new Intent(this, FundDetailsActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_right://抽奖---更改为提现余额不足弹窗

//                ToastUtil.showDialog(new NoTixianEduDialog(mContext));

//                intent = new Intent(this, WithdrawalLimitActivity.class);
//                startActivity(intent);


                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                startActivity(new Intent(mContext, CommonActivity.class));
                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                break;

            case R.id.rel_my_coupons:// 我的卡券
                intent = new Intent(this, MyCouponsActivity.class);
                startActivity(intent);
                break;

            case R.id.rel_my_card:// 我的银行卡
                intent = new Intent(this, MyWalletCommonFragmentActivity.class);
                intent.putExtra("flag", "myBankCardFragment");
                startActivity(intent);
                break;

            case R.id.rel_pay_pass:// 支付密码
                checkPwd();
                break;

            case R.id.img_back:// 返回
                finish();
                break;
            case R.id.img_right_icon:// 消息盒子
                WXminiAppUtil.jumpToWXmini(this);

                break;


            case R.id.ll_yj:
            case R.id.tv_yjtx:
                startActivity(new Intent(this, MyYJactivity.class));
                break;


            case R.id.rel_withdrawal:

                // 提现

                //会员才能提现（非会员就去抽奖）


//                if (isVip == 0 || isVip == 3) {//非会员


                CommonUtils.getVip(mContext, new CommonUtils.GetVipListener() {
                    @Override
                    public void callBack(boolean isVip, int maxType) {
                        if (isVip) {
                            startActivity(new Intent(mContext, NewWalletActivity.class));
                        } else {
                            if (null != SignDrawalLimitActivity.instance) {
                                SignDrawalLimitActivity.instance.finish();
                            }
                            Intent intent = new Intent(mContext, SignDrawalLimitActivity.class);
//                            intent.putExtra("isFromWallet", true);
                            mContext.startActivity(intent);
                            ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                        }
                    }
                });


//                    return;
//                }
//
//
//                intent = new Intent(this, MyWalletCommonFragmentActivity.class);
//                intent.putExtra("flag", "withDrawalFragment");
//                intent.putExtra("balance", balance);
//                intent.putExtra("alliance", "wallet");
//                intent.putExtra("extract", extract);
//
//                startActivity(intent);


                break;
        }

    }


    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }


    private boolean wxIsNotBind;//1是绑定了，0是未绑定

    /**
     * 获取用户是否绑定提现微信
     */
//    private void getWxIsBind() {
//        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) context, 0) {
//            @Override
//            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
//                    throws Exception {
//
//                return ComModelL.getWxIsBind(mContext);
//            }
//
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (e == null && result != null && "1".equals(result.get("status"))) {
//                    if ("1".equals(result.get("data"))) {
//                        autoLimitDialog();
//                        wxIsNotBind = false;
//                    } else if ("0".equals(result.get("data"))) {
//                        wxIsNotBind = true;
//                        Calendar c = Calendar.getInstance();
//                        int day = c.get(Calendar.DAY_OF_MONTH);
//                        if (!("" + YCache.getCacheToken(mContext) + day).equals(SharedPreferencesUtil.getStringData(mContext, YConstance.Pref.AUTO_WITHDRAW, ""))) {
//                            SharedPreferencesUtil.saveStringData(mContext, YConstance.Pref.AUTO_WITHDRAW, "" + YCache.getCacheToken(mContext) + day);
//                            showAutoWithdrawaDialog();
//                        } else {
//                            autoLimitDialog();
//                        }
//                    }
//                } else {
//                    autoLimitDialog();
//                }
//
//            }
//        }.execute();
//
//    }

}
