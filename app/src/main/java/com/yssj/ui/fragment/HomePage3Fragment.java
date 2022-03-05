package com.yssj.ui.fragment;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.custom.view.MYshopTitleView;
import com.yssj.custom.view.MYshopTitleView.OnCheckTitleLentener;
import com.yssj.custom.view.ScrollPagerListShouYe3;
import com.yssj.utils.CheckVersionUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 首页-----首页3
 *
 * @author lbp
 */
@SuppressWarnings("WrongConstant")
public class HomePage3Fragment extends Fragment implements OnClickListener, ScrollPagerListShouYe3.MyOnRefreshLintener, OnCheckTitleLentener {
    // 首页3
    public static String select = "no_one";
    private static Context mContext;
    @Bind(R.id.tv_time)
    TextView tvTime;


    private List<HashMap<String, String>> listTitle = new ArrayList<HashMap<String, String>>();
    private ViewPager mViewPager;

    private MYshopTitleView title;

    private HomePageThreeFragment threeFragment;// 时尚

    public static int width;

    public static int height;


    public static ScrollPagerListShouYe3 mView;

    private PagerAdapterMy mAdapter;


    private long recLen = 30 * 60 * 1000;// 剩余时间
    private Timer timer;

    public static int freeOrderPage;

    private TextView tv_tv;

    private String freeMoney;
    private boolean fromSign;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferencesUtil.saveStringData(mContext, Pref.GOUWUPAGE_CURRENT_PAGER, "gouwu");
        View view = inflater.inflate(R.layout.homepage_three_fragment, container, false);
        tv_tv = view.findViewById(R.id.tv_tv);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mView = null;
        if (timer != null) {
            timer.cancel();
        }

        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        freeOrderPage = ((FragmentActivity) mContext).getIntent().getIntExtra("freeOrderPage", 0);
        freeMoney = ((FragmentActivity) mContext).getIntent().getStringExtra("freeMoney");
        fromSign = ((FragmentActivity) mContext).getIntent().getBooleanExtra("fromSign",false);


        if (!StringUtils.isEmpty(freeMoney)) {
            tv_tv.setText(freeMoney + "元购物券");
        }
        getView().setBackgroundColor(Color.WHITE);

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);

        width = dm.widthPixels;
        height = dm.heightPixels;

        mView = (ScrollPagerListShouYe3) getView().findViewById(R.id.myView);
        mView.setHomepage3(true);
        mView.setOnRefreshLintener(this);

        HashMap<String, String> matchHashMap = new HashMap<String, String>();
        matchHashMap.put("is_show", "1");
        matchHashMap.put("sort_name", "新人钜惠");
        listTitle.add(0, matchHashMap);


        mViewPager = (ViewPager) getView().findViewById(R.id.content_viewpager);
        title = (MYshopTitleView) getView().findViewById(R.id.title);


        title.setMatchTitle(true);
        title.setData(listTitle);
        title.setCheckLintener(this);


        threeFragment = new HomePageThreeFragment();//时尚
        mAdapter = new PagerAdapterMy(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);


        if (timer != null) {
            timer.cancel();
        }

        if (null == tvTime) {
            return;
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                ((Activity) mContext).runOnUiThread(new Runnable() { // UI thread
                    @Override
                    public void run() {
                        recLen -= 1000;
                        String hours = "00";
                        String minutes = "00";
                        String seconds = "00";
                        long minute = recLen / 60000;
                        long second = (recLen % 60000) / 1000;
                        if (minute >= 60) {
                            long hour = minute / 60;
                            minute = minute % 60;

                            if (hour < 10) {
                                hours = "0" + hour;
                            } else {
                                hours = "" + hour;
                            }
                            if (minute < 10) {
                                minutes = "0" + minute;
                            } else {
                                minutes = "" + minute;
                            }
                            if (second < 10) {
                                seconds = "0" + second;
                            } else {
                                seconds = "" + second;
                            }


                        } else if (minute >= 10 && second >= 10) {
//
                            hours = "00";
                            minutes = minute + "";
                            seconds = second + "";

                        } else if (minute >= 10 && second < 10) {


                            hours = "00";
                            minutes = minute + "";
                            seconds = "0" + second;

                        } else if (minute < 10 && second >= 10) {


                            hours = "00";
                            minutes = "0" + minute;
                            seconds = second + "";


                        } else if (minute < 10 && second < 10) {


                            hours = "00";
                            minutes = "0" + minute;
                            seconds = "0" + second;

                        }

                        if (recLen <= 0) {
                            timer.cancel();


                            hours = "00";
                            minutes = "00";
                            seconds = "00";
                        }


                        tvTime.setText(hours + ":" + minutes + ":" + seconds + "分后过期");

                    }
                });


            }
        }, 0, 1000);


        if (!StringUtils.isEmpty(freeMoney) && !fromSign) {

            final Dialog mDialog;
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            mDialog = new Dialog(mContext, R.style.invate_dialog_style);
            View view = mInflater.inflate(R.layout.dialog_first_diamond_mfl, null);

            TextView tv_center = view.findViewById(R.id.tv_center);
            tv_center.setText("您有件" + freeMoney + "元美衣可以免费领走，请速速领取！");

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

        }

        CheckVersionUtils.checkVersion(mContext);


    }

    AnimatorSet animatorSet = new AnimatorSet();

    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            animatorSet.start();

            handler.postDelayed(this, 2600);
        }
    };


    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);


    }


    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();


    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }


    public static HomePage3Fragment newInstances(String title, Context context) {
        HomePage3Fragment fragment = new HomePage3Fragment();
        Bundle args = new Bundle();
        args.putString("tag", title);
        mContext = context;
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * 此处应当继承FragmentStatePagerAdapter
     * 在处理数据量较大的页面应当使用FragmentStatePagerAdapter，而不是FragmentPagerAdapter
     */
    public class PagerAdapterMy extends FragmentStatePagerAdapter {
        public PagerAdapterMy(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listTitle.get(position).get("sort_name");
        }

        @Override
        public int getCount() {
            return listTitle.size();
        }

        @Override
        public Fragment getItem(int position) {


            // 精选
            if (threeFragment == null) {
                threeFragment = new HomePageThreeFragment();
            }
            return threeFragment;


        }

    }


    @Override
    public void checkTitle(View v) {
        mViewPager.setCurrentItem(v.getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {

            default:
                break;
        }
    }

    @Override
    public void onRefreshlintener() {


        HomePageThreeFragment threeFragment = (HomePageThreeFragment) mAdapter.getItem(1);
        threeFragment.refresh();

    }


}
