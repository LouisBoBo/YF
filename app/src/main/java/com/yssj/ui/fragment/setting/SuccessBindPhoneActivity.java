package com.yssj.ui.fragment.setting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.activity.setting.AccountSecureActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.PinTuanDuoBaoUtil;

public class SuccessBindPhoneActivity extends BasicActivity implements OnClickListener {

    private TextView tvTitle_base;
    private LinearLayout img_back;

    private Button btn_close;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_success_bind_phone);


        tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("绑定手机");
        img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
            case R.id.btn_close:
                if (this.getIntent().getStringExtra("buy0") == null) {
                    startActivity(new Intent(this, AccountSecureActivity.class));
                }

                //检查是否有拼团夺宝H5引导
//                PinTuanDuoBaoUtil.getDuobaoH5(super.context);


                finish();
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //检查是否有拼团夺宝H5引导
//        PinTuanDuoBaoUtil.getDuobaoH5(this);
        finish();



    }
}
