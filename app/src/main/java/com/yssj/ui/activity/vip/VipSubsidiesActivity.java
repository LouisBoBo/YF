package com.yssj.ui.activity.vip;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.entity.AddVipResult;
import com.yssj.entity.VipPriceData;
import com.yssj.eventbus.MessageEvent;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.shopdetails.PaymentActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@TargetApi(Build.VERSION_CODES.N)
public class VipSubsidiesActivity extends BasicActivity {


    @Bind(R.id.tvTitle_base)
    TextView tvTitleBase;
    @Bind(R.id.tv_jiangli_title)
    TextView tvJiangliTitle;
    @Bind(R.id.tv_shuoming1)
    TextView tvShuoming1;
    @Bind(R.id.tv_shuoming2)
    TextView tvShuoming2;
    @Bind(R.id.tv_shuoming3)
    TextView tvShuoming3;
    @Bind(R.id.tv_shuoming4)
    TextView tvShuoming4;

    @Bind(R.id.tv_jiangli2)
    TextView tvJiangli2;
    @Bind(R.id.tv_jiangli3)
    TextView tvJiangli3;
    @Bind(R.id.tv_price1)
    TextView tvPrice1;
    @Bind(R.id.tv_price2)
    TextView tvPrice2;
    @Bind(R.id.tv_price3)
    TextView tvPrice3;
    @Bind(R.id.tv_jiangli_day)
    TextView tvJiangliDay;


    @Bind(R.id.tv_total_pay_money_shifu_hg)
    TextView tvTotalPayMoneyShifuHg;
    @Bind(R.id.tv_go_pay_hg)
    TextView tvGoPayHg;
    @Bind(R.id.rl_bot_to_pay_hg)
    RelativeLayout rlBotToPayHg;
    @Bind(R.id.tv_total_pay_money_shifu_hg_bot)
    TextView tvTotalPayMoneyShifuHgBot;
    @Bind(R.id.tv_buy_yuanjia_bot)
    TextView tvBuyYuanjiaBot;


    private VipListBean.ViplistBean currentVip;
    private VipPriceData mVipPriceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_subsidies);
        ButterKnife.bind(this);
        currentVip = (VipListBean.ViplistBean) getIntent().getSerializableExtra("currentVip");
        mVipPriceData = (VipPriceData) getIntent().getSerializableExtra("mVipPriceData");
        tvTitleBase.setText("会员奖励");
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initData();


    }

    private void initData() {


        tvJiangliTitle.setText("完成打卡返" + currentVip.getReturn_money() + "元奖金|" + currentVip.getPunch_days() + "天。");
        tvShuoming1.setText("预存" + mVipPriceData.getOne_price() + "元成为衣蝠" + currentVip.getVip_name() + "会员。");


        tvShuoming2.setText("每天来衣蝠完成赚钱小任务，即完成当日打卡。");


        tvShuoming3.setText("打卡满" + currentVip.getPunch_days() + "天，可领取" + currentVip.getReturn_money() + "元可提现奖金。");
        tvShuoming4.setText("成为" + currentVip.getVip_name() + "会员还可免费发货一件" + currentVip.getPrice_section() + "元美衣哦。");

        tvJiangliDay.setText(currentVip.getPunch_days() + "天后你将收获");

        tvJiangli2.setText(currentVip.getReturn_money() + "元奖金");
        tvJiangli3.setText(currentVip.getPrice_section() + "元美衣");

        tvPrice1.setText("¥" + MyVipListActivity.shifuPirce + "/");
        tvPrice2.setText(currentVip.getPunch_days() + "天全勤分享");
        tvPrice3.setText("返¥" + currentVip.getReturn_money());
        tvTotalPayMoneyShifuHg.setText(mVipPriceData.getContent1() + "");
        tvBuyYuanjiaBot.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvBuyYuanjiaBot.setText("原价¥" + mVipPriceData.getOriginal_price());


    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonUtils.disableScreenshots(this);
    }

    @OnClick({R.id.tv_pay, R.id.tv_go_pay_hg})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.tv_pay:
            case R.id.tv_go_pay_hg:
                goToPay();
                break;
        }


    }

    private void goToPay() {


        final HashMap<String, String> pairsMap = new HashMap<>();
        pairsMap.put("vip_type", currentVip.getVip_type() + "");
        pairsMap.put("vip_count", MyVipListActivity.buyVipCount + "");


        YConn.httpPost(this, YUrl.VIP_PAY_URL, pairsMap, new HttpListener<AddVipResult>() {


            @Override
            public void onSuccess(AddVipResult result) {
                if (result.getActual_price() <= 0) {
                    setResult(MyVipListActivity.PAY_SUCCESS);
                    finish();
                    return;
                }

                MyVipListActivity.showBuySucMessage = result.getShowBuySucMessage();

                // 跳转到收银台界面
                Intent intent = new Intent(VipSubsidiesActivity.this, PaymentActivity.class);
                intent.putExtra("order_code", result.getV_code());
                intent.putExtra("isMulti", true);

                intent.putExtra("isVIPpay", true);
                intent.putExtra("totlaAccount", Double.parseDouble(MyVipListActivity.shifuPirce + ""));
                intent.putExtra("isMulti", true);
                startActivityForResult(intent, 1009);

            }

            @Override
            public void onError() {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1009) { //支付成功
            if (resultCode == MyVipListActivity.PAY_SUCCESS) {
                setResult(MyVipListActivity.PAY_SUCCESS);
                finish();
            } else {
                ToastUtil.showShortText2("支付失败");

            }

        }


    }

}
