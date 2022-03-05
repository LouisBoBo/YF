package com.yssj.ui.activity.infos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.entity.YJMXtopData;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.funddetail.RebateFragment;
import com.yssj.utils.DialogUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YJdetailActivity extends BasicActivity {
    @Bind(R.id.tv_my_yj)
    TextView tvMyYj;//我的佣金
    @Bind(R.id.tv_qingling_yj)
    TextView tvQinglingYj;//清零佣金
    @Bind(R.id.tv_yi_ti_xian)
    TextView tvYiTiXian;//已提现
    private TextView tvTitle_base;
    private FragmentTransaction ft;
    private FragmentManager fm;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yj_detail);
        ButterKnife.bind(this);
        mContext = this;
        tvTitle_base = findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("佣金明细");
        findViewById(R.id.imgbtn_left_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fm = this.getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment, new RebateFragment());
        ft.commitAllowingStateLoss();

        getData();


    }

    private void getData() {


        YConn.httpPost(mContext, YUrl.YJMX_HEADER_DATA, new HashMap<String, String>(), new HttpListener<YJMXtopData>() {
            @Override
            public void onSuccess(YJMXtopData result) {
                tvMyYj.setText("¥" + result.getData().getCount5Money());

                double cleanMoney;
                cleanMoney = result.getData().getCleanMoney();
                cleanMoney = cleanMoney < 0 ? 0.0 : cleanMoney;
                tvQinglingYj.setText("¥" + cleanMoney);
                Spanned sd = Html.fromHtml("已提现<font color='#FF3F8B'>¥" + result.getData().getWithdrawMoney() + "</font>");

                tvYiTiXian.setText(sd);


            }

            @Override
            public void onError() {

            }
        });


    }

    @OnClick({R.id.ll_qingling, R.id.bt_tixian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_qingling:
                DialogUtils.showWithDrawalHGdialog(this);
                break;
            case R.id.bt_tixian:
                Intent intent = new Intent(mContext, MyWalletCommonFragmentActivity.class);
                intent.putExtra("flag", "withDrawalFragment");
                intent.putExtra("alliance", "wallet");
                startActivity(intent);
                break;
        }
    }
}
