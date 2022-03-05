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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ContributionClassActivity extends BasicActivity implements View.OnClickListener{
    private Activity mContent;
    private View img_back;
    private List<String> titles = new ArrayList<>();
    private List<String> types = new ArrayList<>();
    private RecyclerView myrecycleview;
    private ContributionsClassAdapter adapter;
    private SelectType selectType = new SelectType();
    private TextView bar_title;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SelectType message){
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = this;
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_contribution_class);

        initview();
    }

    public void initview(){
        EventBus.getDefault().register(this);

        Bundle bundle = this.getIntent().getExtras();
        String type = bundle.getString("type");
        selectType.type = Integer.parseInt(type);

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        bar_title = findViewById(R.id.bar_title);
        bar_title.setText(selectType.type == 1?"分类":"尺码");

        if(selectType.type == 1){
            titles.add("上衣");
            titles.add("裤子");
            titles.add("裙子");
            titles.add("套装");
        }else {
            titles.add("2XS及以下");
            titles.add("XS");
            titles.add("S");
            titles.add("M");
            titles.add("L");
            titles.add("XL");
            titles.add("XXL");
            titles.add("XXXL");
            titles.add("4XL及以上");
            titles.add("均码");
        }


        myrecycleview = findViewById(R.id.myrecyclerview);
        GridLayoutManager device_manager = new GridLayoutManager(mContent,1);
        myrecycleview.setLayoutManager(device_manager);
        adapter = new ContributionsClassAdapter();
        myrecycleview.setAdapter(adapter);
        adapter.setmDatas(titles,selectType.type);

        adapter.setOnItemClick(new ContributionsClassAdapter.OnItemClick() {
            @Override
            public void click(int index) {
                if(selectType.type == 1){
                    Intent intent2 = new Intent(mContent, ContributionClassTwoActivity.class);
                    Bundle bundleSimple = new Bundle();
                    bundleSimple.putString("name", titles.get(index));
                    intent2.putExtras(bundleSimple);
                    startActivity(intent2);
                }else {
                    selectType.name = titles.get(index);
                    EventBus.getDefault().post(selectType);
                    onBackPressed();
                }

            }
        });
    }
}
