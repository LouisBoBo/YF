package com.yssj.ui.fragment.contributions;

import android.os.Bundle;
import android.view.View;

import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.ui.base.BasicActivity;

public class ScanEeamplesActivity extends BasicActivity implements View.OnClickListener {

    private View img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_examples);

        initview();
    }

    public void initview(){
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
