package com.yssj.ui.fragment.mywallet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.entity.HotShop;
import com.yssj.entity.WithDrawPageData;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.shopdetails.FreeBuyShareDialog;
import com.yssj.ui.activity.vip.MyVipListActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.dialog.NoTixianEduDialog;
import com.yssj.ui.dialog.PublicToastDialog;
import com.yssj.ui.dialog.TixianYinDaoDialog.WithDrawListener;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;
import com.yssj.wxpay.WxPayUtil;

import java.util.HashMap;

public class WithDrawalFragment extends BaseFragment implements OnClickListener {
    private TextView mIconRight;
    private TextView tvTitle_base;
    private LinearLayout img_back;
    private TextView tv_fouce;
    private TextView tv_sum, tv_sum_withdraw, tv_sum_ice, my_money_ice, tv_ketixian_shouyi;//my_money_ice：可提现现金
    private EditText mEtSum;
    private Button btn_next_step;

    private View tixian;
    private double mBalanceNew = 0;
    private RadioGroup mGroupSelect;

    private TextView tv_minimum;

    private double minimum = 5.0;//最低提现额度

    private double TXMoney;

    private TextView mTvLastBank;
    private Context mContext;
    private String WXFlag = "";
    private String allMoney = "";
    private String SucMoney = "";
    private boolean requestFlg = false;// 用来表示接口是否请求完毕
    private double mLimitMoney;
    private int isCanTX;//是否可以提现  1为可提现


    public PublicToastDialog shareWaitDialog = null;


    @Override
    public View initView() {
        mContext = getActivity();
        Bundle bundle = getArguments();
        WXFlag = bundle.getString("WXFlag");
        allMoney = bundle.getString("allMoney");
        SucMoney = bundle.getString("SucMoney");
        view = View.inflate(context, R.layout.activity_mywallet_withdrawal, null);
        mIconRight = (TextView) view.findViewById(R.id.tv_explain);
        mIconRight.setTextSize(14);
        mIconRight.setText("增加提现额度");
        mIconRight.setVisibility(View.VISIBLE);
        mIconRight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ToastUtil.showDialog(new NoTixianEduDialog(mContext));

            }
        });
        mTvLastBank = (TextView) view.findViewById(R.id.tv_last_bank_card);

        mGroupSelect = (RadioGroup) view.findViewById(R.id.rg_select);
        tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
        tv_fouce = (TextView) view.findViewById(R.id.tv_fouce);
        tv_minimum = (TextView) view.findViewById(R.id.tv_minimum);
        tv_ketixian_shouyi = (TextView) view.findViewById(R.id.tv_ketixian_shouyi);

        tvTitle_base.setText("提现");
        img_back = (LinearLayout) view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        tv_sum = (TextView) view.findViewById(R.id.tv_sum);
        my_money_ice = (TextView) view.findViewById(R.id.my_money_ice);
        tv_sum_ice = (TextView) view.findViewById(R.id.tv_sum_ice);
        tv_sum_withdraw = (TextView) view.findViewById(R.id.tv_sum_withdraw);
        // tv_sum.setText(new
        // DecimalFormat("#0.00").format(Double.parseDouble(mMyMoney)));
        mEtSum = (EditText) view.findViewById(R.id.et_with_drawal_sum);
//		et_with_drawal_sum.addTextChangedListener(this);
        setPricePoint(mEtSum);// 限制输入框内数字只能到小数点后两位

        btn_next_step = (Button) view.findViewById(R.id.btn_next_step);
        btn_next_step.setOnClickListener(this);
        tixian = view.findViewById(R.id.tixian);
        tixian.setBackgroundColor(Color.WHITE);
        listener = new WithDrawListener() {

            @Override
            public void close() {
                initData();

            }
        };


        shareWaitDialog = new PublicToastDialog(mContext, R.style.DialogStyle1, "");


        return view;
    }

    private WithDrawListener listener;

    @Override
    public void initData() {
        getWithDrawAgo();
    }


    // TODO：未开启探弹窗

    private void nextStep() {
        String etMoney = mEtSum.getText().toString();
        if (StringUtils.isEmpty(etMoney)) {
            ToastUtil.showShortText2("请输入提现金额");
            return;
        }


        if (isCanTX != 1) {
            showTXerrorDialog(1);
            return;
        }


        TXMoney = Double.parseDouble(etMoney);
        String money = TXMoney + "";

        if (TXMoney == 0) {
            return;
        } else if (mLimitMoney < TXMoney) {
//			ToastUtil.showShortText(context, "你的提现额度不足~");

//            ToastUtil.showDialog(new NoTixianEduDialog(mContext));
            showTXerrorDialog(2);


            return;
        }


        if (TXMoney < minimum) {
//            ToastUtil.showShortText2("单次提现金额不低于" + minimum + "元哦");

//            DialogUtils.showOnePicDialog(mContext, 1);


            showTXerrorDialog(1);


            return;
        }
        withdrawWX();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.btn_next_step:


                if (requestFlg) {
                    nextStep();
                } else {
                    ToastUtil.showShortText2("正在初始化，请稍后");

                }
                break;

        }
    }

    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

    }

    /**
     * 获取提现前信息
     */
    public void getWithDrawAgo() {

        YConn.httpPost(mContext, YUrl.GET_IS_DIALOG, new HashMap<String, String>(), new HttpListener<WithDrawPageData>() {
            @Override
            public void onSuccess(WithDrawPageData result) {
                requestFlg = true;
                isCanTX = result.getIsCanTX();
                minimum = result.getMinicill();
                tv_minimum.setText("单次提现金额不低于" + minimum + "元");
                mLimitMoney = result.getExtract();
                tv_ketixian_shouyi.setText("¥ " + mLimitMoney + "");//可提现现金
            }

            @Override
            public void onError() {

            }
        });

    }

    /**
     * 微信提现后的操作
     */
    private void withdrawWX() {
        String money = TXMoney + "";

        Intent intent = new Intent(WithDrawalFragment.this.context, WithDrawWeiXinExplainActivity.class);
        intent.putExtra("money", "" + money);
        startActivityForResult(intent, 1002);
        ((Activity) mContext).finish();
    }


    @Override
    public void onResume() {
        super.onResume();
        CommonUtils.disableScreenshots(getActivity());
    }


    public void showTXerrorDialog(int type) {


        LayoutInflater mInflater = LayoutInflater.from(mContext);
        final Dialog dialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_common_three_button, null);

        TextView tv_text1 = view.findViewById(R.id.tv_text1);

        if (type == 2) {
            tv_text1.setText("您的佣金不足，您可以按以下方式赚钱更多佣金。");
        }


        view.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//预存会员返佣金并免费领美衣
                mContext.startActivity(new Intent(mContext, MyVipListActivity.class)
                        .putExtra("guide_vipType", 4)
                );
            }
        });

        view.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//申请小助理，自动赚佣金

                String appId51 = WxPayUtil.APP_ID; // 填应用AppId
                if (StringUtils.isEmpty(appId51)) {
                    appId51 = YUrl.APP_ID;
                }
                IWXAPI api51 = WXAPIFactory.createWXAPI(mContext, appId51);
                WXLaunchMiniProgram.Req req51 = new WXLaunchMiniProgram.Req();
                req51.userName = YUrl.WX_MINIAPP_ORIGINAL_ID; // 填小程序原始id
                req51.path = "/pages/mine/AppMessage/AppMessage?fromApp=1&isSQXZL=1"; ////拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
                // 可选打开 开发版，体验版和正式版
                if (YUrl.wxMiniDedug) {
                    req51.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST;
                } else {
                    req51.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
                }
                api51.sendReq(req51);

            }
        });

        view.findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//分享商品给好友或群赚佣金
                shareShop();
            }
        });


        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(dialog);

    }

    private void shareShop() {

        try {
            shareWaitDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //分享热卖随机商品
        HashMap<String, String> map = new HashMap<>();
        map.put("getShop", "true");
        YConn.httpPost(mContext, YUrl.GET_SHARE_SHOP_LINK_HOBBY, map, new HttpListener<HotShop>() {
            @Override
            public void onSuccess(HotShop shop) {
                String sharePath = "/pages/mine/toexamine_test/toexamine_test?shouYePage=ThreePage" + "&isShareFlag=true" + "&user_id=" + YCache.getCacheUser(mContext).getUser_id();
                String shareMIniAPPimgPic = YUrl.imgurl + shop.getShop().getShop_code().substring(1, 4) + "/" + shop.getShop().getShop_code() + "/" + shop.getShop().getFour_pic().split(",")[2] + "!280";
                WXminiAPPShareUtil.shareShopToWXminiAPP(mContext,
                        shop.getShop().getShop_name(),
                        shop.getShop().getAssmble_price() + "",

                        shareMIniAPPimgPic, sharePath, false);

                WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
                    @Override
                    public void wxMiniShareSuccess() {
                        ToastUtil.showShortText(mContext, "分享成功");

                    }
                });

                if (null != shareWaitDialog) {
                    shareWaitDialog.dismiss();
                }
            }

            @Override
            public void onError() {
                if (null != shareWaitDialog) {
                    shareWaitDialog.dismiss();
                }
            }
        });


    }

}
