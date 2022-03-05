package com.yssj.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.MyYJfragment;

public class MyYJactivity extends BasicActivity {

    private LinearLayout ll_head;
    private TextView tvTitle_base;
    private ImageButton imgbtn_left_icon;
    private String tag;
    private String tagName;
    private String theme_id = "";
    public static MyYJactivity instance;


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


        tvTitle_base.setText("我的佣金");
        MyYJfragment fragment = MyYJfragment.newInstances(instance);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

    }


}