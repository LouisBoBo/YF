package com.yssj.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.MYshopTitleView;
import com.yssj.custom.view.MYshopTitleView.OnCheckTitleLentener;
import com.yssj.custom.view.NewPDialog;
import com.yssj.custom.view.ScrollPagerList;
import com.yssj.custom.view.ScrollPagerList.MyOnRefreshLintener;
import com.yssj.custom.view.ToLoginDialog;
import com.yssj.data.YDBHelper;
import com.yssj.entity.ShopOption;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.H5Activity2;
import com.yssj.ui.activity.MessageCenterActivity;
import com.yssj.ui.activity.NewShopActivity;
import com.yssj.ui.activity.classfication.ManufactureActivity;
import com.yssj.ui.activity.setting.FeedBackActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.YCache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

//import com.yssj.custom.view.MyTitleView;
//import com.yssj.custom.view.MyTitleView.OnCheckTitleLentener;

/**
 * ??????-----??????
 *
 * @author lbp
 */
@SuppressWarnings("WrongConstant")
public class MyShopFragment extends Fragment implements OnClickListener, MyOnRefreshLintener, OnCheckTitleLentener {
    // ??????
    public static String select = "no_one";
    private static Context mContext;
    private List<HashMap<String, String>> listTitle = new ArrayList<HashMap<String, String>>();
    private ViewPager mViewPager;

    private MYshopTitleView title;

    //    private ItemFragment[] fragments;
    private HomePageJXfragment matchFragment;// ??????
    private LifeSaleShopFragment lifeSaleShopFragment;// ??????

    public static int width;

    public static int height;

    // private SlideShowView imageViewPager;

    private List<ShopOption> gallList;

    private List<ShopOption> pagerList;

    private static onSearchListener searchListener;

    public static ScrollPagerList mView;

    public Timer timer;

    private int currentPosition = 0;

    private PagerAdapterMy mAdapter;
    private Boolean isShow = false;

    private NewPDialog mDialog2;

    public static String liulan = "";

    // private List<Integer> imgList = new ArrayList<Integer>();

    private String havetan = "1";

    public interface onSearchListener {
        void onSearch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferencesUtil.saveStringData(mContext, Pref.GOUWUPAGE_CURRENT_PAGER, "gouwu");
        return inflater.inflate(R.layout.my_offshop_fragment, container, false);

    }

    public static void setOnSearchListener(Activity mActivity) {
        searchListener = (onSearchListener) mActivity;
    }

    private LinearLayout dot_layout;
    private ViewPager viewPager;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        // diyongquan();

        getView().setBackgroundColor(Color.WHITE);

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);

        width = dm.widthPixels;
        height = dm.heightPixels;

        mView = (ScrollPagerList) getView().findViewById(R.id.myView);
        mView.setOnRefreshLintener(this);

        YDBHelper helper = new YDBHelper(mContext);
        String sql = "select * from sort_info where p_id = 0 and is_show = 1 order by sequence";

//        if (GuideActivity.currentChannel == 13) {
//
//
//            HashMap<String, String> subjectHashMap = new HashMap<String, String>();
//            subjectHashMap.put("is_show", "1");
//            subjectHashMap.put("sort_name", "??????");
//            listTitle.add(0, subjectHashMap);
//
//            HashMap<String, String> matchHashMap = new HashMap<String, String>();
//            matchHashMap.put("is_show", "1");
//            matchHashMap.put("sort_name", "??????");
//            listTitle.add(1, matchHashMap);
//
//
//        } else {
            HashMap<String, String> matchHashMap = new HashMap<String, String>();
            matchHashMap.put("is_show", "1");
            matchHashMap.put("sort_name", "????????????");
            listTitle.add(0, matchHashMap);

//            HashMap<String, String> subjectHashMap = new HashMap<String, String>();
//            subjectHashMap.put("is_show", "1");
//            subjectHashMap.put("sort_name", "??????");
//            listTitle.add(1, subjectHashMap);

//        }


        mViewPager = (ViewPager) getView().findViewById(R.id.content_viewpager);
        title = (com.yssj.custom.view.MYshopTitleView) getView().findViewById(R.id.title);

        dot_layout = (LinearLayout) getView().findViewById(R.id.dot_layout);
        viewPager = (ViewPager) getView().findViewById(R.id.viewPager);


        getTuijianData(false);

        title.setMatchTitle(true);
        title.setData(listTitle);
        title.setCheckLintener(this);

        matchFragment = new HomePageJXfragment();//??????

        lifeSaleShopFragment = new LifeSaleShopFragment();


        mAdapter = new PagerAdapterMy(getChildFragmentManager());

        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

//                String id = listTitle.get(arg0).get("_id");
//                String sort_name = listTitle.get(arg0).get("sort_name");
//
//                title.setPosition(arg0);
//                currentPosition = arg0;
//                if (currentPosition == 0) {
//                    SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_MATCH, "104");
//                    TongJiUtils.TongJi(mContext, 4 + "");
//                    LogYiFu.e("TongJiNew", 4 + "");//??????????????????
//                    TongJiUtils.TongJi(mContext, 105 + "");
//                    LogYiFu.e("TongJiNew", 105 + "");//??????????????????
//                } else {
//                    SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_MATCH, "105");
//                    TongJiUtils.TongJi(mContext, 5 + "");
//                    LogYiFu.e("TongJiNew", 5 + "");//??????????????????
//                    TongJiUtils.TongJi(mContext, 104 + "");
//                    LogYiFu.e("TongJiNew", 104 + "");//??????????????????
//                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    private Handler handler2 = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // ???ViewPager???????????????
            if (options.size() > 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                handler2.sendEmptyMessageDelayed(0, 6000);
            }

//			LogYiFu.e("tag", "sendEmptyMessageDelayed");
        }

        ;
    };

    /**
     * ??????????????????
     */
    protected void updateTextAndDot(boolean isRefresh) {
        int currentItem = viewPager.getCurrentItem() % options.size();// ?????????????????????page

        // ???????????????currentItem????????????????????????????????????????????????????????????
        for (int i = 0; i < dot_layout.getChildCount(); i++) {
            View childView = dot_layout.getChildAt(i);
            childView.setBackgroundResource(
                    currentItem == i ? R.drawable.img_round_checked : R.drawable.img_round_default);

        }
    }

    /**
     * ??????????????????
     */
    private void initDotLayout(boolean isRefresh) {

        if (isRefresh) {// ????????????
            dot_layout.removeAllViews();
        }

        for (int i = 0; i < options.size(); i++) {
            View dotView = new View(mContext);
            LayoutParams params = new LayoutParams(15, 15);
            if (i > 0) {
                params.leftMargin = 15;
            }
            dotView.setLayoutParams(params);// ??????????????????

            // ???????????????dot_layout???
            dot_layout.addView(dotView);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private MyAdapter adapter;
    private List<ShopOption> options = null;

    /**
     * ??????????????? ??????
     */
    private void getTuijianData(final boolean isRefresh) {
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mContext, R.string.wait) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                LoadingDialog.show(getActivity());
            }

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                // TODO Auto-generated method stub
                return ComModel2.getMainTuijianData(context, null);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                if (null == e && result != null) {
                    pagerList = (List<ShopOption>) result.get("topShops");
                    gallList = (List<ShopOption>) result.get("centShops");
                    options = pagerList;
                    mView.refreshDone();

                    if (options.size() > 0) {
                        adapter = new MyAdapter(options);

                        // ?????????????????????
                        initDotLayout(isRefresh);
                        // 3.???ViewPager????????????
                        viewPager.setAdapter(adapter);
                        // 4.??????ViewPager???page??????????????????
                        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
                            /**
                             * ???page??????????????????????????????????????????
                             */
                            @Override
                            public void onPageSelected(int position) {
                                // Log.e("tag", "position: "+position);
                                updateTextAndDot(isRefresh);
                            }

                            /**
                             * ??????????????????page???????????????
                             */
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                // Log.e("tag", "onPageScrolled: ");
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {
                            }
                        });

                        adapter.notifyDataSetChanged();
                        // ???????????????????????????OnPageChangeListener???onPageSelected????????????????????????????????????
                        updateTextAndDot(isRefresh);
                        viewPager.setCurrentItem(options.size() * 100000);// ??????ViewPager?????????????????????

                        if (isRefresh) {
                            handler2.removeCallbacksAndMessages(null);
                            handler2 = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    if (options.size() > 1) {
                                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                                        handler2.sendEmptyMessageDelayed(0, 6000);
                                    }

                                }

                            };

                        }
                        // ??????????????????
                        handler2.sendEmptyMessageDelayed(0, 6000);
                    }

                }
                super.onPostExecute(context, result, e);
            }

        }.execute();
    }

    public static MyShopFragment newInstances(String title, Context context) {
        MyShopFragment fragment = new MyShopFragment();
        Bundle args = new Bundle();
        args.putString("tag", title);
        mContext = context;
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * ??????????????????FragmentStatePagerAdapter
     * ?????????????????????????????????????????????FragmentStatePagerAdapter????????????FragmentPagerAdapter
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


            if (position == 0) {

                // ??????
                if (matchFragment == null) {
                    matchFragment = new HomePageJXfragment();
                }
                return matchFragment;


            } else {


                // ??????
                if (lifeSaleShopFragment == null) {
                    lifeSaleShopFragment = new LifeSaleShopFragment();
                }
                return lifeSaleShopFragment;

//                // ??????
//                if (matchFragment == null) {
////                    matchFragment = MatchFragment.newInstances(listTitle.get(0).get("sort_name"), mContext, "1");
//                    matchFragment =  new HomePageJXfragment();
//                }
//                return matchFragment;

            }

        }

    }

    class MyAdapter extends PagerAdapter {
        List<ShopOption> options;

        public MyAdapter(List<ShopOption> options) {
            this.options = options;
        }

        /**
         * ??????????????????
         */
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
            // return list.size();
        }

        /**
         * ????????????instantiateItem???????????????Object?????????View??????
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * ?????????BaseAdapter???getView?????????????????????View????????????VIew????????????
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            // 1.??????View??????
            View view = View.inflate(mContext, R.layout.adapter_list, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            // 2.????????????
            String url = options.get(position % options.size()).getUrl().substring(1,
                    options.get(position % options.size()).getUrl().length())+"";

            PicassoUtils.initImage(mContext, url, imageView);
            container.addView(view);

            imageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    int type = options.get(position % options.size()).getOption_type();

                    if (type == 5) {
                        // ??????H5?????????
                        Intent intent = new Intent(mContext, H5Activity2.class);
                        intent.putExtra("h5_code", options.get(position % options.size()).getShop_code());
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    } else if (type == 4) {
                        // ????????????
//						((MainFragment) MainMenuActivity.instances.getSupportFragmentManager().findFragmentByTag("tag"))
//								.setIndex(2);


                        if (GuideActivity.hasSign) {
                            // ????????????
                            SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                            mContext.startActivity(new Intent(mContext, CommonActivity.class));
                            ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                        } else {


                        }


                    } else if (type == 2) {
                        // ???????????????
                        if (YJApplication.instance.isLoginSucess() == false) {
                            ToLoginDialog dialog = new ToLoginDialog(mContext);
                            dialog.show();
                            return;
                        }
                        // Intent intent = new Intent(context,
                        // InviteCodeActivity.class);
                        // ((FragmentActivity) context).startActivity(intent);
                    } else if (type == 3) {
                        // ??????????????????
                        if (YJApplication.instance.isLoginSucess() == false) {
                            ToLoginDialog dialog = new ToLoginDialog(mContext);
                            dialog.show();
                            return;
                        }
                        Intent intent = new Intent(mContext, MessageCenterActivity.class);
                        ((FragmentActivity) mContext).startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                R.anim.slide_left_out);
                    } else if (type == 1) {
                        // ??????????????????
                        mContext.getSharedPreferences("YSSJ_yf", mContext.MODE_PRIVATE).edit()
                                .putBoolean("isGoDetail", true).commit();
                        Intent intent = new Intent(mContext, ShopDetailsActivity.class);
                        intent.putExtra("code", options.get(position % options.size()).getShop_code());
                        ((FragmentActivity) mContext).startActivityForResult(intent, 102);
                        ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                R.anim.slide_left_out);
                    } else if (type == 7) {//??????????????????
                        Intent intent = new Intent(mContext, ManufactureActivity.class);
                        intent.putExtra("supple_id", options.get(position % options.size()).getShop_code());
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(R.anim.activity_from_right,
                                R.anim.activity_search_close);
                    } else if (type == 6) {//?????????????????????
                        //????????????
                        Intent intent = new Intent(mContext, NewShopActivity.class);
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    }

                }
            });

            return view;
        }

        /**
         * ????????????page???????????????????????????????????????view???ViewPager?????????
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }

    @Override
    public void checkTitle(View v) {
        mViewPager.setCurrentItem(v.getId());
        currentPosition = v.getId();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler2.removeCallbacksAndMessages(null);
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
        getTuijianData(true);
//
//        if (GuideActivity.currentChannel == 13) {
//            if (currentPosition == 0) {//??????
//                LifeSaleShopFragment lifeSaleShopFragment = (LifeSaleShopFragment) mAdapter.getItem(0);
//                lifeSaleShopFragment.refresh();
//
//            } else {//??????
//                HomePageJXfragment homePageJXfragment = (HomePageJXfragment) mAdapter.getItem(1);
//                homePageJXfragment.refresh();
//            }
//        } else {
            if (currentPosition == 0) {//??????
                HomePageJXfragment homePageJXfragment = (HomePageJXfragment) mAdapter.getItem(0);
                homePageJXfragment.refresh();
            } else {//??????
                LifeSaleShopFragment lifeSaleShopFragment = (LifeSaleShopFragment) mAdapter.getItem(1);
                lifeSaleShopFragment.refresh();
            }
//        }


    }


    public String getIdNew() {
        // ??????????????? ?????????????????????????????? // ???????????? ??????????????????????????????
        int position = currentPosition == 0 ? 2 : currentPosition;
        return listTitle.get(position).get("_id");
    }

    public String getSortNameNew() {

        return listTitle.get(currentPosition).get("sort_name");
    }

}
