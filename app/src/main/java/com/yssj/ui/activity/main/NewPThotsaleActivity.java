package com.yssj.ui.activity.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.ui.adpter.ForceLookAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;

public class NewPThotsaleActivity extends BasicActivity {


    private PullToRefreshListView r_list_view;

    private ForceLookAdapter mAdapter;
    public static NewPThotsaleActivity instance;

    private int index = 1;


    private int mType = 1;// 1：初始化数据；2：加载更多数据


    private View headView;


    private LinearLayout llNodata;

    private String is_hot = null, is_new = "is_new", order_by_price = null;
    private TextView mTitle;
    private TextView tvNew, tvHot, tvDesc, tvAsc;

    // isComplete为true
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);

        instance = this;

        setContentView(R.layout.common_shop_list);

        r_list_view = (PullToRefreshListView) findViewById(R.id.r_list_view);
        headView = LayoutInflater.from(this).inflate(R.layout.view_scan_header, null);
        tvNew = (TextView) headView.findViewById(R.id.create_is_new);
        tvHot = (TextView) headView.findViewById(R.id.create_is_hot);
        tvDesc = (TextView) headView.findViewById(R.id.create_price_desc);
        tvAsc = (TextView) headView.findViewById(R.id.create_price_asc);
        tvHot.setOnClickListener(this);
        tvNew.setOnClickListener(this);
        tvDesc.setOnClickListener(this);
        tvAsc.setOnClickListener(this);

        r_list_view.getRefreshableView().addHeaderView(headView);



        mTitle = (TextView) findViewById(R.id.tv_forcelook_title);


        findViewById(R.id.img_back).setOnClickListener(this);
        mTitle.setText("热卖");


        llNodata = (LinearLayout) findViewById(R.id.ll_nodata);
        findViewById(R.id.btn_view_allcircle).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.tv_no_join)).setText("暂无数据");
        r_list_view.setMode(Mode.BOTH);


        mAdapter = new ForceLookAdapter(this, false);
        mAdapter.setNewPT(true);

        r_list_view.setAdapter(mAdapter);


        setListViewRefresh();


        initData(index + "", is_new, order_by_price);


    }

    private void setListViewRefresh() {
        r_list_view.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mType = 1;
                index = 1;
                initData(index + "", is_new, order_by_price);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                index++;
                mType = 2;

                initData(index + "", is_new, order_by_price);

            }
        });

    }


    private void initData(final String index, final String is_new,
                          final String order_by_price) {
        // 下面是真数据
        new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {
            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
                    throws Exception {

                //热卖推荐商品
                return ComModel2.getSignShop(context, index, "", is_new, is_hot, order_by_price);

            }

            @Override
            protected boolean isHandleException() {

                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);

                if (e == null) {
                    if (mType == 1) {
                        if (result == null || result.size() == 0) {
                            llNodata.setVisibility(View.VISIBLE);
                            r_list_view.setVisibility(View.GONE);
                        } else {
                            llNodata.setVisibility(View.GONE);
                            r_list_view.setVisibility(View.VISIBLE);
                        }
                        mAdapter.setData(result, false);

                    } else if (mType == 2) {
                        if (result == null || result.size() == 0) {
                            ToastUtil.showShortText(context, "已没有更多商品了哦~");
                        } else {
                            mAdapter.addItemLast(result);
                            r_list_view.getRefreshableView().smoothScrollBy(100, 20);
                        }
                    }
                }
                r_list_view.onRefreshComplete();
            }

        }.execute(index);
    }


    @Override
    public void onClick(View arg0) {
        super.onClick(arg0);
        switch (arg0.getId()) {
            case R.id.img_back: // 返回
                onBackPressed();
                break;


            case R.id.create_is_new:// 上新
                tvNew.setTextColor(Color.parseColor("#FF3F8B"));
                tvHot.setTextColor(Color.parseColor("#3E3E3E"));
                tvDesc.setTextColor(Color.parseColor("#3E3E3E"));
                tvAsc.setTextColor(Color.parseColor("#3E3E3E"));
                is_new = "is_new";
                is_hot = null;
                order_by_price = null;
                mType = 1;
                index = 1;
                initData(index + "", is_new, order_by_price);
                break;
            case R.id.create_is_hot:// 热销
                tvHot.setTextColor(Color.parseColor("#FF3F8B"));
                tvNew.setTextColor(Color.parseColor("#3E3E3E"));
                tvDesc.setTextColor(Color.parseColor("#3E3E3E"));
                tvAsc.setTextColor(Color.parseColor("#3E3E3E"));
                is_hot = "is_hot";
                is_new = null;
                order_by_price = null;
                mType = 1;
                index = 1;
                initData(index + "", is_new, order_by_price);
                break;
            case R.id.create_price_asc:// 价格
                tvAsc.setTextColor(Color.parseColor("#FF3F8B"));
                tvNew.setTextColor(Color.parseColor("#3E3E3E"));
                tvHot.setTextColor(Color.parseColor("#3E3E3E"));
                tvDesc.setTextColor(Color.parseColor("#3E3E3E"));
                is_new = null;
                is_hot = null;
                order_by_price = "asc";
                mType = 1;
                index = 1;
                initData(index + "", is_new, order_by_price);
                break;
            case R.id.create_price_desc:// 价格
                tvDesc.setTextColor(Color.parseColor("#FF3F8B"));
                tvNew.setTextColor(Color.parseColor("#3E3E3E"));
                tvHot.setTextColor(Color.parseColor("#3E3E3E"));
                tvAsc.setTextColor(Color.parseColor("#3E3E3E"));
                is_new = null;
                is_hot = null;
                order_by_price = "desc";
                mType = 1;
                index = 1;
                initData(index + "", is_new, order_by_price);
                break;
            default:
                break;
        }
    }


}
