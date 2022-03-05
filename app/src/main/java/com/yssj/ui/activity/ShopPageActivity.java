package com.yssj.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.LeaveDialog;
import com.yssj.ui.fragment.ClassficationFragment;
import com.yssj.ui.fragment.FriendsFragment;
import com.yssj.ui.fragment.HomePageFragment;
import com.yssj.ui.fragment.MyShopFragment;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.CommonUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

//购物页
public class ShopPageActivity extends BasicActivity {

    @Bind(R.id.vvv)
    View vvv;
    private TextView tvTitle_base;
    private ImageButton imgbtn_left_icon;
    private boolean isMiyouquan;
    private boolean isAddShopcart;//是否是加入购物车的任务
//    private MyShopFragment mCommonFragment;

    private boolean isHomePage;
    private Context context;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_common);
        ButterKnife.bind(this);
        tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);

        context = this;
        isHomePage = false;

        imgbtn_left_icon = (ImageButton) findViewById(R.id.imgbtn_left_icon);


        isAddShopcart = getIntent().getBooleanExtra("isAddShopcart", false);

        vvv.setVisibility(View.VISIBLE);

        imgbtn_left_icon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        isMiyouquan = getIntent().getBooleanExtra("isMiyouquan", false);

        if (isHomePage) {
            tvTitle_base.setText("首页");
//            mCommonFragment = MyShopFragment.newInstances("tab2", this);


            HomePageFragment fragment = HomePageFragment.newInstances("tab2", this);//测试首页
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        } else {
            if (isMiyouquan) {
                tvTitle_base.setText("蜜友圈");
                FriendsFragment fragment = FriendsFragment.newInstance(context, true);//密友圈
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            } else {

                tvTitle_base.setText("购物");
                ClassficationFragment fragment = ClassficationFragment.newInstance("tab1", context, true);// 购物---老分类
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

            }
        }


    }

    @Override
    public void onBackPressed() {


        if (SignListAdapter.isForceLookTimeOut || SignListAdapter.isSignComplete || isAddShopcart) {//分钟数时间到了或者是加入购物车的任务
            super.onBackPressed();

        } else {
            final LeaveDialog leaveDialog = new LeaveDialog(this);
            leaveDialog.show();
            leaveDialog.setContentText("你正在进行浏览商品任务，浏览时长还未完成，你可以选择再逛逛当前页面，或者去浏览其它商品，浏览时长达到任务要求即可完成任务喔~");
            leaveDialog.setButtonText("不了，谢谢", "其他商品");
            View btn_left = leaveDialog.findViewById(R.id.btn_left);
            btn_left.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (leaveDialog != null) {
                        leaveDialog.dismiss();
                    }
                    finish();
                    overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
                }
            });
            View btn_right = leaveDialog.findViewById(R.id.btn_right);
            btn_right.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtils.finishActivity(MainMenuActivity.instances);

                    Intent intent = new Intent((Activity) context, MainMenuActivity.class);
                    intent.putExtra("toYf", "toYf");
                    context.startActivity(intent);
                }
            });

            return;
        }

    }


}