package com.yssj.ui.activity;//package com.yssj.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.activity.R.id;
import com.yssj.custom.view.SnatchScrollPagerList;
import com.yssj.custom.view.SnatchScrollPagerList.ZeroOnRefreshLintener;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.PinTuanDuoItemFragment;
import com.yssj.ui.fragment.PinTuanDuoShuaiDanFragment;
import com.yssj.ui.fragment.WqjxPinTuanDuoBao;
import com.yssj.ui.fragment.WqjxShaiDan.onWqjxShanDanRefreshListener;
import com.yssj.ui.fragment.WqjxShaiDanPinTuan;
import com.yssj.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 夺宝记录----拼团夺宝
 */
public class PinTuanSnatchActivity extends BasicActivity
        implements OnClickListener, ZeroOnRefreshLintener, onWqjxShanDanRefreshListener {
    private static Context mContext;
    @Bind(id.t_tile)
    TextView tTile;
    private LinearLayout duobaojilu, ll_title_two, ll_title_right, ll_title_one, ll_title;

    private SnatchScrollPagerList mView;
    private int indexFlag = 0;
    private ViewPager mViewPager;
    // private PagerAdapter mAdapter;
    private TextView tv_zero_shop_item_meal, tv_title;

    private View v_shai, v_can;

    private int currentPosition = 0;
    private TextView tV_title_price_one;
    private TextView tV_title_price_two;

    private int choice; // 夺宝记录=0 往期揭晓=1
    private int my_choice; // 参与记录=0 晒单=1
    private LinearLayout snatc_title;
    private PagerAdapter mAdapter;
    private PagerAdapter wqjx_adapter;

    private int index;

    public PinTuanSnatchActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle arg0) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(arg0);
        setContentView(R.layout.snatch_fragment);
        ButterKnife.bind(this);
        choice = 0;
        my_choice = 0;

        index = getIntent().getIntExtra("index", 0);

        SharedPreferencesUtil.saveStringData(mContext, "choice", "0");
        SharedPreferencesUtil.saveStringData(mContext, "my_choice", "0");


        initView();
    }

    private void initView() {
        duobaojilu = (LinearLayout) findViewById(id.duobaojilu);
        duobaojilu.setBackgroundColor(Color.WHITE);
        findViewById(id.iv_back).setOnClickListener(this);
        mView = (SnatchScrollPagerList) findViewById(id.zeroView);
        mView.setZeroOnRefreshLintener(this);
        mViewPager = (ViewPager) findViewById(id.zero_shop_content_viewpager);

        snatc_title = (LinearLayout) findViewById(id.snatc_title);

        // 两条红线
        v_shai = (View) findViewById(id.v_shai);
        v_can = (View) findViewById(id.v_can);

        ll_title_one = (LinearLayout) findViewById(id.ll_title_one);// 夺宝记录框框
        ll_title_two = (LinearLayout) findViewById(id.ll_title_two);// 往期揭晓框框
        tV_title_price_one = (TextView) findViewById(id.TV_title_price_one);
        tV_title_price_two = (TextView) findViewById(id.TV_title_price_two);

        ll_title_one.setOnClickListener(this);
        ll_title_two.setOnClickListener(this);

        tv_zero_shop_item_meal = (TextView) findViewById(id.tv_zero_shop_item_meal);
        tv_title = (TextView) findViewById(id.tv_title);

        ll_title = (LinearLayout) findViewById(id.ll_title);
        ll_title_right = (LinearLayout) findViewById(id.ll_title_right);
        initTextView();

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new PinTuanDuoItemFragment()); // 夺宝记录 我的参与记录
        fragments.add(new PinTuanDuoShuaiDanFragment()); // 夺宝记录 我的晒单记录
        mAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

                // indexFlag = arg0;
                currentPosition = arg0;
                if (currentPosition == 0) {
                    my_choice = 0;
                    SharedPreferencesUtil.saveStringData(mContext, "my_choice", "0");
                    tv_zero_shop_item_meal.setTextColor(getResources().getColor(R.color.zero_shop_choice));
                    tv_title.setTextColor(getResources().getColor(R.color.dark));
                    v_can.setVisibility(View.VISIBLE);
                    v_shai.setVisibility(View.INVISIBLE);

                }
                if (currentPosition == 1) {
                    my_choice = 1;
                    SharedPreferencesUtil.saveStringData(mContext, "my_choice", "1");
                    tv_zero_shop_item_meal.setTextColor(getResources().getColor(R.color.dark));
                    tv_title.setTextColor(getResources().getColor(R.color.zero_shop_choice));
                    v_can.setVisibility(View.INVISIBLE);
                    v_shai.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

        });


        switch (index) {
            case 0:
                tTile.setText("抽奖记录");
                choice = 0;
                SharedPreferencesUtil.saveStringData(mContext, "choice", "0");
                ll_title_one.setBackgroundResource(R.drawable.jx_left_red);
                ll_title_two.setBackgroundResource(R.drawable.jx_right_white);
                tV_title_price_one.setTextColor(getResources().getColor(R.color.white));
                tV_title_price_two.setTextColor(getResources().getColor(R.color.zero_shop_choice));

                tv_zero_shop_item_meal.setText("我的参与记录");
                tv_title.setText("我的晒单纪录");


                mViewPager.removeAllViews();
                List<Fragment> fragmentss = new ArrayList<Fragment>();
                fragmentss.add(new PinTuanDuoItemFragment()); // 夺宝记录 我的参与
                fragmentss.add(new PinTuanDuoShuaiDanFragment()); // 夺宝记录 我的晒单记录
                mAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentss);
                mViewPager.setAdapter(mAdapter);

                if (currentPosition == 1) {
                    mViewPager.setCurrentItem(1);
                }

                // select();
                snatc_title.getChildAt(indexFlag).performClick();

                break;
            case 1:
                tTile.setText("往期揭晓");
                choice = 1;
                SharedPreferencesUtil.saveStringData(mContext, "choice", "1");
                ll_title_one.setBackgroundResource(R.drawable.jx_left_white);
                ll_title_two.setBackgroundResource(R.drawable.jx_right_red);
                tV_title_price_one.setTextColor(getResources().getColor(R.color.zero_shop_choice));
                tV_title_price_two.setTextColor(getResources().getColor(R.color.white));

                tv_zero_shop_item_meal.setText("往期揭晓");
                tv_title.setText("晒单分享");


                snatc_title.getChildAt(indexFlag).performClick();

                mViewPager.removeAllViews();
                List<Fragment> wqjx_fragment = new ArrayList<Fragment>();
                wqjx_fragment.add(new WqjxPinTuanDuoBao()); // 往期揭晓 往期揭晓(左边)
                wqjx_fragment.add(new WqjxShaiDanPinTuan()); // 往期揭晓 晒单分享(右边)
                wqjx_adapter = new PagerAdapter(getSupportFragmentManager(), wqjx_fragment);
                mViewPager.setAdapter(wqjx_adapter);

                if (currentPosition == 1) {
                    mViewPager.setCurrentItem(1);
                }

                break;
            default:
                break;
        }


    }

	/*
     * protected void select() { mAdapter = new
	 * PagerAdapter(getSupportFragmentManager());
	 * mViewPager.setAdapter(mAdapter);
	 *
	 * }
	 */

    private void initTextView() {
        ll_title.setOnClickListener(new MyOnClickListener(0));
        ll_title_right.setOnClickListener(new MyOnClickListener(1));

    }

    /* 标题点击监听 */
    private class MyOnClickListener implements OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            // indexFlag=index;
            index = i;
        }

        public void onClick(View v) {
            indexFlag = index;
            mViewPager.setCurrentItem(index);

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case id.ll_title_one:
                choice = 0;
                SharedPreferencesUtil.saveStringData(mContext, "choice", "0");
                ll_title_one.setBackgroundResource(R.drawable.jx_left_red);
                ll_title_two.setBackgroundResource(R.drawable.jx_right_white);
                tV_title_price_one.setTextColor(getResources().getColor(R.color.white));
                tV_title_price_two.setTextColor(getResources().getColor(R.color.zero_shop_choice));

                tv_zero_shop_item_meal.setText("我的参与记录");
                tv_title.setText("我的晒单纪录");

//			System.out.println("******left****=" + choice);

                mViewPager.removeAllViews();
                List<Fragment> fragments = new ArrayList<Fragment>();
                fragments.add(new PinTuanDuoItemFragment()); // 夺宝记录 我的参与
                fragments.add(new PinTuanDuoShuaiDanFragment()); // 夺宝记录 我的晒单分享
                mAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
                mViewPager.setAdapter(mAdapter);

                if (currentPosition == 1) {
                    mViewPager.setCurrentItem(1);
                }

                // select();
                snatc_title.getChildAt(indexFlag).performClick();
                break;

            case id.ll_title_two:
                choice = 1;
                SharedPreferencesUtil.saveStringData(mContext, "choice", "1");
                ll_title_one.setBackgroundResource(R.drawable.jx_left_white);
                ll_title_two.setBackgroundResource(R.drawable.jx_right_red);
                tV_title_price_one.setTextColor(getResources().getColor(R.color.zero_shop_choice));
                tV_title_price_two.setTextColor(getResources().getColor(R.color.white));

                tv_zero_shop_item_meal.setText("往期揭晓");
                tv_title.setText("晒单分享");
//			System.out.println("******right****=" + choice);

                snatc_title.getChildAt(indexFlag).performClick();

                mViewPager.removeAllViews();
                List<Fragment> wqjx_fragment = new ArrayList<Fragment>();
                wqjx_fragment.add(new WqjxPinTuanDuoBao()); // 往期揭晓 往期揭晓(左边)
                wqjx_fragment.add(new WqjxShaiDanPinTuan()); // 往期揭晓 晒单分享(右边)
                wqjx_adapter = new PagerAdapter(getSupportFragmentManager(), wqjx_fragment);
                mViewPager.setAdapter(wqjx_adapter);

                if (currentPosition == 1) {
                    mViewPager.setCurrentItem(1);
                }

                break;
            case id.iv_back:
                onBackPressed();
                break;

            default:
                break;
        }
    }

    // @Override
    // public void onRefreshlintener() {
    // DuoItemFragment fragment = (DuoItemFragment) mAdapter
    // .getItem(currentPosition);
    // fragment.refresh();
    // }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;

        public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return mFragments.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mFragments.size();
        }

    }

    @Override
    public void onWqjxShanDanRefresh() {
        // TODO Auto-generated method stub
        mView.refreshDone();
    }

    @Override
    public void onRefreshlintener() {

        String choice = SharedPreferencesUtil.getStringData(this, "choice", "0");
        String my_choice = SharedPreferencesUtil.getStringData(this, "my_choice", "0");
        if (choice.equals("0")) {
            if (my_choice.equals("0")) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mView.refreshDone();
                    }
                }, 500);
                PinTuanDuoItemFragment fragment = (PinTuanDuoItemFragment) mAdapter.getItem(0);
                fragment.refresh();
            }

            if (my_choice.equals("1")) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mView.refreshDone();
                    }
                }, 500);
                PinTuanDuoShuaiDanFragment fragment = (PinTuanDuoShuaiDanFragment) mAdapter.getItem(1);
                fragment.refresh();
            }
        }

        if (choice.equals("1")) {
            if (my_choice.equals("0")) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mView.refreshDone();
                    }
                }, 500);
                WqjxPinTuanDuoBao fragment = (WqjxPinTuanDuoBao) wqjx_adapter.getItem(0);
                fragment.refresh();
            }

            if (my_choice.equals("1")) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mView.refreshDone();
                    }
                }, 500);
                WqjxShaiDanPinTuan fragment = (WqjxShaiDanPinTuan) wqjx_adapter.getItem(1);
                fragment.refresh();
            }
        }

//		DuoShuaiDanFragment fragment = (DuoShuaiDanFragment) mAdapter.getItem(1);
//		fragment.refresh();
        // String where = SharedPreferencesUtil.getStringData(mContext, "where",
        // "1");
        // System.out.println("保存的是什么？where="+where);
        //
        // DuoItemFragment duoItemFragment = new DuoItemFragment();
        // //夺宝记录——我的参与记录 1
        // DuoShuaiDanFragment duoShuaiDanFragment = new DuoShuaiDanFragment();
        // //夺宝记录——我的晒单记录 2
        // WqjxDuoBao wqjxDuoBao = new WqjxDuoBao(); //往期揭晓——往期揭晓 3
//		WqjxShaiDan wqjxShaiDan = new WqjxShaiDan(); // 往期揭晓——晒单分享 4
        //
        // duoItemFragment.refresh();
        // if (where.equals("1")) {
        // duoItemFragment.refresh();
        // }
        // if (where.equals("2")) {
        // duoShuaiDanFragment.refresh();
        // }
        // if (where.equals("3")) {
        // wqjxDuoBao.refresh();
        // }
        // if (where.equals("4")) {
        // wqjxShaiDan.refresh();
//		mView.refreshDone();
        // }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
    }

}
