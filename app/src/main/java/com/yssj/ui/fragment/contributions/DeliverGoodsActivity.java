package com.yssj.ui.fragment.contributions;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.custom.view.ContributionsDialog;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.DeliverGoodsDialog;

public class DeliverGoodsActivity extends BasicActivity implements View.OnClickListener{

    private View img_back;
    private TextView tv_submit;
    private Activity mcontent;
    private DeliverGoodsDialog dialog;
    private TextView deliver_address;
    private TextView deliver_num;
    private TextView deliver_com;

    private View deliver_head;
    private View deliver_content;
    private TextView deliver_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_deliver_goods);

        mcontent = this;
        initview();
    }

    public void initview(){
        dialog = new DeliverGoodsDialog(mcontent);
        dialog.show();

        deliver_address = findViewById(R.id.deliver_head_title);
        deliver_num = findViewById(R.id.deliver_content_num);
        deliver_com = findViewById(R.id.deliver_content_com);

        deliver_head = findViewById(R.id.deliver_head);
        deliver_content = findViewById(R.id.deliver_content);
        deliver_submit = findViewById(R.id.submit);

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tv_submit = findViewById(R.id.submit);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        dialog.setOnItemClick(new DeliverGoodsDialog.OnItemClick() {
            @Override
            public void click(String address, String num, String com) {
                deliver_head.setVisibility(View.VISIBLE);
                deliver_content.setVisibility(View.VISIBLE);
                deliver_submit.setVisibility(View.VISIBLE);

                deliver_address.setText(address);
                deliver_num.setText(num);
                deliver_com.setText(com);
            }
        });

    }
}
