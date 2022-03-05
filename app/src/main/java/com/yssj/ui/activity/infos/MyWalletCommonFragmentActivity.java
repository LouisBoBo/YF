package com.yssj.ui.activity.infos;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yssj.activity.R;
import com.yssj.entity.FundDetail;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.funddetail.ReFundDetailsFragment;
import com.yssj.ui.fragment.funddetail.WithDrawaDetailsFragment;
import com.yssj.ui.fragment.mywallet.MyBankCardFragment;
import com.yssj.ui.fragment.mywallet.WithDrawalFragment;

import java.util.HashMap;

public class MyWalletCommonFragmentActivity extends BasicActivity {

    private String flag;
    private String alliance;
    private String allMoney = "";
    private String SucMoney = "";
    private String WXFlag = "";
    private boolean isWithdrawGuid = false;//新用户提现引导

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_circle_common);
        flag = getIntent().getStringExtra("flag");
        alliance = getIntent().getStringExtra("alliance");
        allMoney = getIntent().getStringExtra("allMoney");
        SucMoney = getIntent().getStringExtra("SucMoney");
        WXFlag = getIntent().getStringExtra("WXFlag");
        isWithdrawGuid = getIntent().getBooleanExtra("jumpFromSign", false);
        initFragment();
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);

    }*/
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//		MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//		YJApplication.getLoader().stop();
//		MobclickAgent.onPause(this);
    }

    private void initFragment() {
        Fragment mFragment;
        Bundle bundle;
        if ("myBankCardFragment".equals(flag)) {
            String balance = getIntent().getStringExtra("balance");
            bundle = new Bundle();
            bundle.putString("alliance", alliance);
            bundle.putString("balance", balance);
            mFragment = new MyBankCardFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
        }

        if ("withDrawalFragment".equals(flag)) {
            String balance = getIntent().getStringExtra("balance");
            mFragment = new WithDrawalFragment();
            bundle = new Bundle();
            if ("WXFlag".equals(WXFlag)) {
                bundle.putString("allMoney", allMoney);
                bundle.putString("SucMoney", SucMoney);
                bundle.putString("WXFlag", WXFlag);
            }
            bundle.putString("balance", balance);    //balance
            bundle.putString("alliance", alliance);
            bundle.putBoolean("jumpFromSign", isWithdrawGuid);
            mFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
        }

        if ("reFundDetailsFragment".equals(flag)) {
            FundDetail detail = (FundDetail) getIntent().getSerializableExtra("item");
            mFragment = new ReFundDetailsFragment();
            bundle = new Bundle();
            bundle.putSerializable("detail", detail);
            mFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
        }

        if ("withDrawaDetailsFragment".equals(flag)) {
            HashMap<String, Object> map = (HashMap<String, Object>) getIntent().getSerializableExtra("item");
            String business_code = getIntent().getStringExtra("business_code");
            mFragment = new WithDrawaDetailsFragment();
            bundle = new Bundle();
            bundle.putSerializable("map", map);
            if (null != business_code) {
                bundle.putString("business_code", business_code);
            }
            mFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
        }

    }


}
