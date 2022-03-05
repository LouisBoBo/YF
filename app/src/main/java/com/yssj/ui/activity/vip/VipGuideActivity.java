package com.yssj.ui.activity.vip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VipGuideActivity extends BasicActivity {


    @Bind(R.id.tvTitle_base)
    TextView tvTitleBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_guide);
        ButterKnife.bind(this);
        tvTitleBase.setText("选择会员卡类型");

    }


    @OnClick({R.id.img_back, R.id.tv_goVipList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.tv_goVipList:
                startActivity(new Intent(this,MyVipListActivity.class));
                break;
        }
    }
}
