package com.yssj.ui.activity.view;//package com.yssj.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.SnatchScrollPagerList;
import com.yssj.custom.view.SnatchScrollPagerList.ZeroOnRefreshLintener;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.activity.infos.IntergralDetailActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.MyCanyuFragment;
import com.yssj.ui.fragment.WqjxShaiDan.onWqjxShanDanRefreshListener;
import com.yssj.ui.fragment.setting.ZhongjiangJiLuFragment;
import com.yssj.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 夺宝记录
 */
public class YiYuanJoinRecordActivity extends BasicActivity
        implements OnClickListener, ZeroOnRefreshLintener, onWqjxShanDanRefreshListener {
    private static Context mContext;
    @Bind(id.t_tile)
    TextView tTile;
    @Bind(id.tv_total_count)
    TextView tvTotalCount;//中奖总金额
    private LinearLayout duobaojilu, ll_title_right, ll_title;

    private SnatchScrollPagerList mView;
    private int indexFlag = 0;
    private ViewPager mViewPager;
    // private PagerAdapter mAdapter;
    private TextView tv_zero_shop_item_meal, tv_title;

    private View v_shai, v_can;

    private int currentPosition = 0;


    private LinearLayout snatc_title;
    private PagerAdapter mAdapter;

    private int index;

    public YiYuanJoinRecordActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle arg0) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(arg0);
        setContentView(R.layout.yiyuan_canyujilu_activity);

        mContext = this;

        ButterKnife.bind(this);
        index = getIntent().getIntExtra("index", 0);
        SharedPreferencesUtil.saveStringData(mContext, "my_choice", "0");
        initView();
        initData();

    }

    private void initView() {
        duobaojilu = (LinearLayout) findViewById(id.duobaojilu);
        duobaojilu.setBackgroundColor(Color.WHITE);
        findViewById(id.iv_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mView = (SnatchScrollPagerList) findViewById(id.zeroView);
        mView.setZeroOnRefreshLintener(this);
        mViewPager = (ViewPager) findViewById(id.zero_shop_content_viewpager);

        snatc_title = (LinearLayout) findViewById(id.snatc_title);

        // 两条红线
        v_shai = (View) findViewById(id.v_shai);
        v_can = (View) findViewById(id.v_can);


        tv_zero_shop_item_meal = (TextView) findViewById(id.tv_zero_shop_item_meal);
        tv_title = (TextView) findViewById(id.tv_title);

        ll_title = (LinearLayout) findViewById(id.ll_title);
        ll_title_right = (LinearLayout) findViewById(id.ll_title_right);
        initTextView();


    }

    private void initData() {
        mViewPager.removeAllViews();
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new MyCanyuFragment()); // 我的参与
        fragments.add(new ZhongjiangJiLuFragment()); //中奖记录
        mAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

                // indexFlag = arg0;
                currentPosition = arg0;
                if (currentPosition == 0) {
                    SharedPreferencesUtil.saveStringData(mContext, "my_choice", "0");
                    tv_zero_shop_item_meal.setTextColor(getResources().getColor(R.color.zero_shop_choice));
                    tv_title.setTextColor(getResources().getColor(R.color.dark));
                    v_can.setVisibility(View.VISIBLE);
                    v_shai.setVisibility(View.INVISIBLE);

                }
                if (currentPosition == 1) {
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


        tTile.setText("我的参与");
        SharedPreferencesUtil.saveStringData(mContext, "choice", "0");

        tv_zero_shop_item_meal.setText("我的参与");
        tv_title.setText("中奖记录");
        if (currentPosition == 1) {
            mViewPager.setCurrentItem(1);
        }
        snatc_title.getChildAt(indexFlag).performClick();
        //获取中奖总金额
        getZhongjiangCount();


    }


    private void getZhongjiangCount() {

        new SAsyncTask<String, Void, String>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected String doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ModQingfeng.getZhongJiangCount(mContext);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, String result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (null != result && result.length() > 0) {
                        try {
                            tvTotalCount.setText("¥" +  new java.text.DecimalFormat("#0.00").format(Float.parseFloat(result)));
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        tvTotalCount.setText("¥" + 0.00);
                    }
                }
            }

        }.execute();

    }


    private void initTextView() {
        ll_title.setOnClickListener(new MyOnClickListener(0));
        ll_title_right.setOnClickListener(new MyOnClickListener(1));

    }

    @OnClick(id.tv_total_count)
    public void onViewClicked() {

        //跳至提现额度列表
        Intent limit_det_ll_intent = new Intent(this, IntergralDetailActivity.class);
        limit_det_ll_intent.putExtra("page", 0);
        limit_det_ll_intent.putExtra("isTiXianMingXi", true);
        startActivity(limit_det_ll_intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


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

        String my_choice = SharedPreferencesUtil.getStringData(this, "my_choice", "0");
        if (my_choice.equals("0")) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    mView.refreshDone();
                }
            }, 500);
            MyCanyuFragment fragment = (MyCanyuFragment) mAdapter.getItem(0);
            fragment.refresh();
        }

        if (my_choice.equals("1")) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    mView.refreshDone();
                }
            }, 500);
            ZhongjiangJiLuFragment fragment = (ZhongjiangJiLuFragment) mAdapter.getItem(1);
            fragment.refresh();
        }

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
    }

}
