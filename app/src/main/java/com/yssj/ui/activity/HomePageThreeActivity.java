package com.yssj.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.entity.BaseData;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.HomePage3Fragment;
import com.yssj.utils.ToastUtil;

import java.util.HashMap;

public class HomePageThreeActivity extends BasicActivity {

    private LinearLayout ll_head;
    private TextView tvTitle_base;
    private ImageButton imgbtn_left_icon;
    private String tag;
    private String tagName;
    private String theme_id = "";
    public static HomePageThreeActivity instance;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_common);
        instance = this;
        ll_head = (LinearLayout) findViewById(R.id.ll_head);
        tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        imgbtn_left_icon = (ImageButton) findViewById(R.id.imgbtn_left_icon);
        tag = getIntent().getStringExtra("tag");
        tagName = getIntent().getStringExtra("tagName");
        theme_id = getIntent().getStringExtra("theme_id");
        imgbtn_left_icon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvTitle_base.setText("会员钜惠");
        HomePage3Fragment fragment = HomePage3Fragment.newInstances("tab2", this);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

    }




    @Override
    public void onBackPressed() {

        HashMap<String, String> pairsMap = new HashMap<>();
        YConn.httpPost(this, YUrl.NEED_JUM_FREE_LING, pairsMap
                , new HttpListener<BaseData>() {
                    @Override
                    public void onSuccess(BaseData baseData) {
                        if (baseData.getIsJumpPage() == 1) {
                            final Dialog mDialog;
                            LayoutInflater mInflater = LayoutInflater.from(HomePageThreeActivity.this);
                            mDialog = new Dialog(HomePageThreeActivity.this, R.style.invate_dialog_style);
                            View view = mInflater.inflate(R.layout.dialog_first_diamond_mfl, null);
                            TextView tv_center = view.findViewById(R.id.tv_center);
                            tv_center.setText("您有件" + baseData.getFreeMoney() + "元美衣可以免费领走，请速速领取！");

                            view.findViewById(R.id.bt1).setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mDialog.dismiss();
                                }
                            });
                            view.findViewById(R.id.iv_close).setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mDialog.dismiss();
                                }
                            });

                            mDialog.setCanceledOnTouchOutside(false);
                            mDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT));
                            ToastUtil.showDialog(mDialog);
                        } else {
                            finish();
                        }

                    }

                    @Override
                    public void onError() {

                    }
                });


    }


}