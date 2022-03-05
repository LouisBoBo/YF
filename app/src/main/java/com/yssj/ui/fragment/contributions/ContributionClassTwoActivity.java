package com.yssj.ui.fragment.contributions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.eventbus.MessageEvent;
import com.yssj.ui.base.BasicActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ContributionClassTwoActivity extends BasicActivity implements View.OnClickListener{
    private Activity mContent;
    private View zhedie_base;
    private View img_back;
    private TextView tv_title;
    private List<String> titles = new ArrayList<>();
    private List<String> types = new ArrayList<>();
    private RecyclerView myrecycleview;
    private ContributionsClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = this;
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_contribution_classtwo);

        initview();
    }

    public void initview(){
        tv_title = findViewById(R.id.tv_title);
        zhedie_base = findViewById(R.id.zhedie_base);
        zhedie_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zhedie_base.setSelected(!zhedie_base.isSelected());
                myrecycleview.setVisibility(zhedie_base.isSelected()?View.INVISIBLE:View.VISIBLE);
            }
        });
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Bundle bundle = this.getIntent().getExtras();
        String name = bundle.getString("name");
        tv_title.setText(name);

        titles.add("半身群");
        titles.add("连衣裙");
        titles.add("吊带群");

        myrecycleview = findViewById(R.id.myrecyclerview);
        GridLayoutManager device_manager = new GridLayoutManager(mContent,1);
        myrecycleview.setLayoutManager(device_manager);
        adapter = new ContributionsClassAdapter();
        myrecycleview.setAdapter(adapter);
        adapter.setmDatas(titles,2);

        adapter.setOnItemClick(new ContributionsClassAdapter.OnItemClick() {
            @Override
            public void click(int index) {

                SelectType selectType = new SelectType();
                selectType.name = titles.get(index);
                selectType.type = 1;
                EventBus.getDefault().post(selectType);
                onBackPressed();
            }
        });
    }
}
