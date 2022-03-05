package com.yssj.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.custom.view.MyMatchTitleView;
import com.yssj.data.YDBHelper;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.QingItemFragment;

import java.util.HashMap;

import android.support.v4.app.Fragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lifeng on 2017/7/3.
 */

public class NewShopActivity extends BasicActivity implements MyMatchTitleView.OnCheckTitleLentener {


    @Bind(R.id.imgbtn_left_icon)
    ImageButton imgbtnLeftIcon;
    @Bind(R.id.title)
    MyMatchTitleView title;
    @Bind(R.id.content_viewpager)
    ViewPager mViewPager;
    @Bind(R.id.tvTitle_base)
    TextView tvTitleBase;
    private List<HashMap<String, String>> listTitle;
    private Context mContext;
    private PagerAdapter mAdapter;

    private QingItemFragment[] fragments;


    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_shop);
        ButterKnife.bind(this);
        mContext = this;


        tvTitleBase.setText("新品专区");
        YDBHelper helper = new YDBHelper(mContext);
        String sql = "select * from sort_info where p_id = 0 and is_show = 1 order by sequence";
        listTitle = helper.query(sql);


        title.setData(listTitle);

        title.setCheckLintener(this);

        mAdapter = new PagerAdapter(getSupportFragmentManager());

        fragments = new QingItemFragment[listTitle.size()];
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                title.setPosition(arg0);
                currentPosition = arg0;
//                if (null != ((QingItemFragment) mAdapter.getItem(arg0)).getmList()
//                        && ((QingItemFragment) mAdapter.getItem(arg0)).getmList()
//                        .getCount() != 0)
//                    ((QingItemFragment) mAdapter.getItem(arg0)).getmList()
//                            .setSelection(0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });



    }


    @OnClick({R.id.imgbtn_left_icon, R.id.title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_left_icon:
                onBackPressed();
                break;
            case R.id.title:
                break;
        }
    }

    @Override
    public void checkTitle(View v) {
        mViewPager.setCurrentItem(v.getId());
        currentPosition = v.getId();
    }


    /**
     * 此处应当继承FragmentStatePagerAdapter
     * 在处理数据量较大的页面应当使用FragmentStatePagerAdapter，而不是FragmentPagerAdapter
     */
    public class PagerAdapter extends FragmentStatePagerAdapter {


        public PagerAdapter(FragmentManager fm) {
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
            QingItemFragment fragment = fragments[position];
            if (fragment == null) {
                fragment = QingItemFragment.newInstances(position,
                        listTitle.get(position).get("sort_name"), listTitle
                                .get(position).get("_id"), mContext);
                fragments[position] = fragment;
            }
            return fragment;
        }

    }
}