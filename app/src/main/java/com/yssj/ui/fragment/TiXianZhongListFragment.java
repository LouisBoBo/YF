package com.yssj.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.ui.activity.WithdrawalLimitActivity;
import com.yssj.ui.activity.infos.MyWalletCommonFragmentActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.base.BasePager;
import com.yssj.ui.pager.ShenHeZhongPage;
import com.yssj.ui.pager.TiXianZhongPager;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class TiXianZhongListFragment extends BaseFragment implements OnClickListener {
    private ViewPager content_pager;
    private LinearLayout ll, ll_v3, ll_dongjieshuoming;
    private List<BasePager> pageLists; // 总余额--------可提现-----冻结额度
    private TextView textView1, textView2, textView3, intergralTv, tv_allyue, tv_ketixian, tv_view, tv_dongjieedu;
    private int currIndex;// 当前页卡编号

    private TextView tvTitle_base;
    private LinearLayout img_back;
    private LinearLayout ll_top_jifen;
    private Button img_right_icon, bt_edushuoming;
    public static boolean isTiXianMingXi; // true：提现额度明细 false:积分明细
    private RelativeLayout rl_edu;
    private Button bt_tixian;

    private View v1, v2, v3;

    @SuppressLint("ValidFragment")
    public TiXianZhongListFragment(int currIndex, boolean isTiXianMingXi) {
        this.currIndex = currIndex;
        this.isTiXianMingXi = isTiXianMingXi;
    }

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.my_intergral_list_new, null);
        view.setBackgroundColor(Color.WHITE);
        tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);

        img_back = (LinearLayout) view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        ll_v3 = (LinearLayout) view.findViewById(R.id.ll_v3);
        ll_top_jifen = (LinearLayout) view.findViewById(R.id.ll_top_jifen);
        rl_edu = (RelativeLayout) view.findViewById(R.id.rl_edu);
        bt_tixian = (Button) view.findViewById(R.id.bt_tixian);
        bt_tixian.setOnClickListener(this);
        bt_edushuoming = (Button) view.findViewById(R.id.bt_edushuoming);
        bt_edushuoming.setOnClickListener(this);
        ll_dongjieshuoming = (LinearLayout) view.findViewById(R.id.ll_dongjieshuoming);
        // img_right_icon.setImageResource(R.drawable.mine_message_center);
        // img_right_icon.setOnClickListener(this);

        ll = (LinearLayout) view.findViewById(R.id.ll);
        content_pager = (ViewPager) view.findViewById(R.id.content_pager);
        textView1 = (TextView) view.findViewById(R.id.textView1);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        tv_allyue = (TextView) view.findViewById(R.id.tv_allyue);
        tv_ketixian = (TextView) view.findViewById(R.id.tv_ketixian);
        tv_dongjieedu = (TextView) view.findViewById(R.id.tv_dongjieedu);
        tv_view = (TextView) view.findViewById(R.id.tv_view);

        intergralTv = (TextView) view.findViewById(R.id.integral_count_tv);

        // 红线
        v1 = (View) view.findViewById(R.id.v1); // 左边
        v2 = (View) view.findViewById(R.id.v2); // 中间边
        v3 = (View) view.findViewById(R.id.v3); // 右边

        ll_dongjieshuoming.setVisibility(View.GONE);
        ll_v3.setVisibility(View.GONE);
        rl_edu.setVisibility(View.GONE);
        textView3.setVisibility(View.GONE);
        tv_view.setVisibility(View.GONE);
        bt_edushuoming.setVisibility(View.GONE);
        ll_top_jifen.setVisibility(View.GONE);
        tvTitle_base.setText("账户明细");


        textView1.setText("提现中");
        textView2.setText("审核中");

        return view;
    }

    @Override
    public void initData() {
        initViewPager(currIndex);
        initTextView();

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager(int index) {
        pageLists = new ArrayList<BasePager>();
        pageLists.add(new TiXianZhongPager(getActivity())); // 提现中


        pageLists.add(new ShenHeZhongPage(getActivity()));// 审核中

        pageLists.get(index).initData(); // 第一次进来时加载数据

        content_pager.setOffscreenPageLimit(1); // 设置预加载页面
        content_pager.setAdapter(new MyPagerAdapter(pageLists));

        content_pager.setCurrentItem(index);

        content_pager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    private void initTextView() {

        // 红色红线的显示
        if (currIndex == 0) {
            textView1.setTextColor(getResources().getColor(R.color.pink_color));
            v1.setVisibility(View.VISIBLE);
            v2.setVisibility(View.INVISIBLE);
            v3.setVisibility(View.INVISIBLE);
            ll_dongjieshuoming.setVisibility(View.GONE);

        } else if (currIndex == 1) {
            ll_dongjieshuoming.setVisibility(View.GONE);
            textView2.setTextColor(getResources().getColor(R.color.pink_color));
            v1.setVisibility(View.INVISIBLE);
            v2.setVisibility(View.VISIBLE);
            v3.setVisibility(View.INVISIBLE);
        } else {
            ll_dongjieshuoming.setVisibility(View.VISIBLE);
            textView3.setTextColor(getResources().getColor(R.color.pink_color));
            v1.setVisibility(View.INVISIBLE);
            v2.setVisibility(View.INVISIBLE);
            v3.setVisibility(View.VISIBLE);

        }
        textView1.setOnClickListener(new MyOnClickListener(0));
        textView2.setOnClickListener(new MyOnClickListener(1));
        textView3.setOnClickListener(new MyOnClickListener(2));
    }

    /* 页卡切换监听 */
    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            pageLists.get(arg0).initData();
            setTextTitleSelectedColor(arg0);

            if (arg0 == 0) {
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.INVISIBLE);
                v3.setVisibility(View.INVISIBLE);
                ll_dongjieshuoming.setVisibility(View.GONE);
            }

            if (arg0 == 1) {
                v1.setVisibility(View.INVISIBLE);
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.INVISIBLE);
                ll_dongjieshuoming.setVisibility(View.GONE);
            }
            if (arg0 == 2) {
                v1.setVisibility(View.INVISIBLE);
                v2.setVisibility(View.INVISIBLE);
                v3.setVisibility(View.VISIBLE);
                ll_dongjieshuoming.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    /**
     * 设置标题文本的颜色
     **/
    private void setTextTitleSelectedColor(int arg0) {
        for (int i = 0; i < 3; i++) {
            TextView tv = (TextView) ll.getChildAt(i);
            if (!isTiXianMingXi) {
                if (((arg0 == 0) && (i == 0)) || ((arg0 == 1) && (i == 2))) {
                    tv.setTextColor(getResources().getColor(R.color.pink_color));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                }
            } else {

                if (arg0 == 0) {
                    textView1.setTextColor(getResources().getColor(R.color.pink_color));
                    textView2.setTextColor(getResources().getColor(R.color.black));
                    textView3.setTextColor(getResources().getColor(R.color.black));
                } else if (arg0 == 1) {
                    textView1.setTextColor(getResources().getColor(R.color.black));
                    textView2.setTextColor(getResources().getColor(R.color.pink_color));
                    textView3.setTextColor(getResources().getColor(R.color.black));
                } else {
                    textView1.setTextColor(getResources().getColor(R.color.black));
                    textView2.setTextColor(getResources().getColor(R.color.black));
                    textView3.setTextColor(getResources().getColor(R.color.pink_color));
                }

            }

        }
    }

    /* 标题点击监听 */
    private class MyOnClickListener implements OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            content_pager.setCurrentItem(index);
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        private List<BasePager> pageLists;

        public MyPagerAdapter(List<BasePager> pageLists) {
            this.pageLists = pageLists;
        }

        @Override
        public int getCount() {
            return pageLists.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageLists.get(position).getRootView());
            return pageLists.get(position).getRootView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);

                break;
            case R.id.bt_tixian: // 提现


//                if (!SharedPreferencesUtil.getBooleanData(context, "isYindaoToast", false)) {
//                    TixianYinDaoDialog.WithDrawListener listener = new TixianYinDaoDialog.WithDrawListener() {
//                        @Override
//                        public void close() {
//                            // TODO Auto-generated method stub
//                        }
//                    };
//                    // 新手1元提现引导
//                    TixianYinDaoDialog dialog = new TixianYinDaoDialog(context, R.style.DialogQuheijiao, 1, listener);
//                    dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//                    dialog.show();
//                    return;
//                }


                int mIsOpen = Integer.parseInt(SharedPreferencesUtil.getStringData(context, Pref.IS_OPEN, -1 + ""));
                if (mIsOpen == 1) { // 已开启余额翻倍
                    ToastUtil.showShortText(context, "余额翻倍期间暂时不可以提现喔~");
                    return;
                }
                Intent intent = new Intent(context, MyWalletCommonFragmentActivity.class);
                intent.putExtra("flag", "withDrawalFragment");
                // intent.putExtra("balance", balance);
                intent.putExtra("alliance", "wallet");
                startActivity(intent);
                break;
            case R.id.bt_edushuoming:
                final Dialog dialog = new Dialog(context, R.style.invate_dialog_style);
                View view = View.inflate(context, R.layout.dialog_tixianedushuoming, null);
                dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
                // 知道了的点击
                view.findViewById(R.id.liebiao).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });
                // 抽取提现额度点击
                view.findViewById(R.id.gobuy2).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, WithdrawalLimitActivity.class);
                        startActivity(intent);
                        ((FragmentActivity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                        getActivity().finish();

                    }
                });
                // XX按钮
                view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                // // 创建自定义样式dialog
                dialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(context, 270),
                        LinearLayout.LayoutParams.MATCH_PARENT));
                dialog.show();

                break;

        }
    }

}
