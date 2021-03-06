package com.yssj.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.eventbus.MessageEvent;
import com.yssj.pubu.PubuFragment;
import com.yssj.spl.DoublePuBuCommmonFragment;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.BackHandledFragment;
import com.yssj.ui.fragment.BackHandledInterface;
import com.yssj.ui.fragment.ClassficationFragment;
import com.yssj.ui.fragment.FriendsListCommnonFragment;
import com.yssj.ui.fragment.ZeroShopFragment;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.ui.fragment.contributions.ContributionStatusFragment;
import com.yssj.ui.fragment.contributions.ContributionsFragment;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CommonActivity extends BasicActivity implements BackHandledInterface {

    private LinearLayout ll_head;
    private TextView tvTitle_base;
    private Button right_btn;
    private ImageButton imgbtn_left_icon;
    private String tag;
    private String tagName;
    private String theme_id = "";
    public static CommonActivity instance;
    private String commonactivityfrom;

    private BackHandledFragment mBackHandedFragment;
    private boolean hadIntercept;


    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_common);
        EventBus.getDefault().register(this);
        instance = this;
        ll_head = (LinearLayout) findViewById(R.id.ll_head);
        tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        imgbtn_left_icon = (ImageButton) findViewById(R.id.imgbtn_left_icon);
        right_btn = findViewById(R.id.btn_right);

        tag = getIntent().getStringExtra("tag");
        tagName = getIntent().getStringExtra("tagName");
        theme_id = getIntent().getStringExtra("theme_id");
        imgbtn_left_icon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        commonactivityfrom = SharedPreferencesUtil.getStringData(this, "commonactivityfrom", "");

        if (tag != null) {// ??????
            tvTitle_base.setText(tagName);
            FriendsListCommnonFragment fragment = FriendsListCommnonFragment.newInstances("", instance, "1", "1", tag);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        } else if (commonactivityfrom.equals("sign")) {// ??????
            if (!GuideActivity.hasSign) {
                CommonUtils.finishActivity(MainMenuActivity.instances);

                Intent intent2 = new Intent((Activity) instance, MainMenuActivity.class);
                intent2.putExtra("toHome", "toHome");
                instance.startActivity(intent2);
                instance.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
            } else {
                SignFragment fragment = SignFragment.newInstance(this);
                ll_head.setVisibility(View.GONE);
                String signShopDetail = getIntent().getStringExtra("signShopDetail");
                String signType = getIntent().getStringExtra("signType");
                boolean isDuobao = getIntent().getBooleanExtra("isDuobao", false);
                String CanYunumber = getIntent().getStringExtra("CanYunumber");
                int now_type_id = getIntent().getIntExtra("now_type_id", 0);
                int now_type_id_value = getIntent().getIntExtra("now_type_id_value", 0);
                int next_type_id = getIntent().getIntExtra("next_type_id", 0);
                int next_type_id_value = getIntent().getIntExtra("next_type_id_value", 0);
                if (isDuobao) {
                    Bundle bundle = new Bundle();
                    bundle.putString("signShopDetail", signShopDetail);
                    bundle.putString("signType", signType);
                    bundle.putBoolean("isDuobao", isDuobao);
                    bundle.putString("CanYunumber", CanYunumber);
                    bundle.putInt("now_type_id", now_type_id);
                    bundle.putInt("now_type_id_value", now_type_id_value);
                    bundle.putInt("next_type_id", next_type_id);
                    bundle.putInt("next_type_id_value", next_type_id_value);
                    fragment.setArguments(bundle);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            }


        } else if (commonactivityfrom.equals("choucangliebiao")) {
            tvTitle_base.setText("????????????");
            FriendsListCommnonFragment fragment = FriendsListCommnonFragment.newInstances("", instance, "1", "5", "");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        } else if (commonactivityfrom.equals("chuandaliebiao")) {
            tvTitle_base.setText("????????????");

            FriendsListCommnonFragment fragment = FriendsListCommnonFragment.newInstances("", instance, "1", "3", "");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        } else if (commonactivityfrom.equals("xiangguantuijian")) {
            tvTitle_base.setText("????????????");

            //??????????????????them_id
            SharedPreferencesUtil.saveStringData(instance, "xiangtuijianhthemid", theme_id);

            DoublePuBuCommmonFragment fragment = DoublePuBuCommmonFragment.newInstances(instance, "xiangguantuijian");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

            // ???????????????????????????theme_id???title?????????
//			FriendsListCommnonFragment fragment = FriendsListCommnonFragment.newInstances(theme_id, context, "1", "4",
//					"");
//			getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        } else if (commonactivityfrom.equals("pubuliu_sign")) { //??????????????????????????????
            tvTitle_base.setText("????????????");
            PubuFragment fragment = PubuFragment.newInstances(instance, "pubuliu_sign");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        } else if (commonactivityfrom.equals("gouwuye")) { //?????????
            tvTitle_base.setText("??????");
//            PubuFragment fragment = PubuFragment.newInstances(context, "pubuliu_sign");
            ClassficationFragment fragment = ClassficationFragment.newInstance("tab1", instance);// ??????---?????????
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        } else if (commonactivityfrom.equals("pubuliu_sign_tixian")) {
            tvTitle_base.setText("????????????");
            PubuFragment fragment = PubuFragment.newInstances(instance, "pubuliu_sign_tixian");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        } else if (commonactivityfrom.equals("zero")) {

            ll_head.setVisibility(View.GONE);
            ZeroShopFragment zeroShopFragment = ZeroShopFragment.newInstance("??????", this);// ??????

            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, zeroShopFragment).commit();


        } else if(commonactivityfrom.equals("contributions")){

            tvTitle_base.setText("????????????");

            ContributionsFragment fragment = ContributionsFragment.newInstances(instance, "contributions");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        } else if(commonactivityfrom.equals("contributionstatus")){

            tvTitle_base.setText("??????????????????");
            ContributionStatusFragment fragment = ContributionStatusFragment.newInstances(instance, "contributionstatus");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        }


    }

//    @Override
//    public void onBackPressed() {
//        // setResult(14342);
//        super.onBackPressed();
//    }


    @Override
    public void onBackPressed() {


        if (null != commonactivityfrom) {
            if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    super.onBackPressed();
                } else {
                    getSupportFragmentManager().popBackStack();
                }
            }
            return;
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onGetMessage(MessageEvent message) {
//        if(message.getMessage().equals("????????????")){
//            fileList();
//        }
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageEvent message){
        if(message.getMessage().equals("????????????")){
            super.onBackPressed();
        }
    }
}