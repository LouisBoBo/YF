package com.yssj.ui.activity.infos;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.funddetail.RebateFragment;
import com.yssj.ui.fragment.funddetail.RefundFragment;
import com.yssj.ui.fragment.funddetail.TradeFragment;
//import com.yssj.ui.fragment.funddetail.WithdrawFragment;
import com.yssj.ui.fragment.funddetail.WithdrawFragmentNew;
import com.yssj.utils.DialogUtils;

public class FundDetailsActivity extends BasicActivity implements OnCheckedChangeListener {

    private RadioGroup rg_group;

    private TextView tvTitle_base;
    private LinearLayout img_back;

    private TradeFragment tFragment;//交易
    private WithdrawFragmentNew wFragment;//提现
    private RefundFragment fFragment;//退款
    private RebateFragment rFragment;//返现

    private FragmentManager fm;
    private FragmentTransaction ft;
    private LinearLayout mx;

    private RadioButton rb_trade, rb_withdraw, rb_refund, rb_rebate;

    private boolean fromFriendJL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        fm = this.getSupportFragmentManager();
        int index = getIntent().getIntExtra("index", 0);    // 从提现成功跳转过来看查看提现记录
        fromFriendJL = getIntent().getBooleanExtra("fromFriendJL", false);
        initView(index);
    }

    private void initView(int index) {
        // TODO Auto-generated method stub
        setContentView(R.layout.fund_details);
        rg_group = (RadioGroup) findViewById(R.id.rg_group);
        rg_group.setOnCheckedChangeListener(this);
        mx = (LinearLayout) findViewById(R.id.mx);
        mx.setBackgroundColor(Color.WHITE);
        tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("账户明细");
        img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        rb_trade = (RadioButton) findViewById(R.id.rb_trade);
        rb_withdraw = (RadioButton) findViewById(R.id.rb_withdraw);
        rb_refund = (RadioButton) findViewById(R.id.rb_refund);
        rb_rebate = (RadioButton) findViewById(R.id.rb_rebate);
//		rg_group.check(index);
        if (getIntent().getBooleanExtra("isShow", false)) {
            Toast.makeText(this, "佣金将在确认收货后到账!", Toast.LENGTH_SHORT).show();
        }

        if (fromFriendJL) {
//        	DialogUtils.showWithDrawalHGdialog(this);
            fromFriendJL = false;
        }

        switch (index) {
            case 0://交易
                rb_trade.setChecked(true);
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment, new TradeFragment());
                ft.commitAllowingStateLoss();
                break;
            case 1://提现
                rb_withdraw.setChecked(true);
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment, new WithdrawFragmentNew());
                ft.commitAllowingStateLoss();
                break;
            case 2://售后
                rb_refund.setChecked(true);
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment, new RefundFragment());
                ft.commitAllowingStateLoss();
                break;
            case 3://余额
                rb_rebate.setChecked(true);
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment, new RebateFragment());
                ft.commitAllowingStateLoss();
                break;

        }

//		tFragment = new TradeFragment();
//		ft.replace(R.id.fragment, tFragment);
//		
//		ft.commitAllowingStateLoss();

    }

    @Override
    public void onCheckedChanged(RadioGroup arg0, int arg1) {
        ft = fm.beginTransaction();
        switch (arg1) {
            case R.id.rb_trade://交易
                ft.replace(R.id.fragment, new TradeFragment());
                ft.commitAllowingStateLoss();
                break;
            case R.id.rb_withdraw://提现
                ft.replace(R.id.fragment, new WithdrawFragmentNew());
                ft.commitAllowingStateLoss();
                break;
            case R.id.rb_refund://退款
                ft.replace(R.id.fragment, new RefundFragment());
                ft.commitAllowingStateLoss();
                break;
            case R.id.rb_rebate://回佣（余额）
                ft.replace(R.id.fragment, new RebateFragment());
                ft.commitAllowingStateLoss();
                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;

            default:
                break;
        }
    }

}
