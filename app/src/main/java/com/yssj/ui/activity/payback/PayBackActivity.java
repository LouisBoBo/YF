package com.yssj.ui.activity.payback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.Order;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainFragment;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.adpter.PayBackAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.MineIfoFragment;
import com.yssj.utils.CommonUtils;

public class PayBackActivity extends BasicActivity implements OnItemClickListener {

    private PullToRefreshListView lv_payback;
    private LinearLayout img_back;
    private PayBackAdapter mAdapter;
    private int currentPage = 1;

    private boolean flag;        // 判断上拉、下拉的标志

    private List<Order> orders = new ArrayList<Order>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		getActionBar().hide();
        initView();
        initData();
    }

    private void initData() {
        new SAsyncTask<Void, Void, List<Order>>(this,
                R.string.wait) {

            @Override
            protected List<Order> doInBackground(
                    FragmentActivity context, Void... params) throws Exception {
                return ComModel2.getPaybackList(context, currentPage, -1);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         List<Order> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (flag) {
                        orders.clear();
                        orders.addAll(result);
                    } else {
                        orders.addAll(result);
                    }

                    mAdapter.notifyDataSetChanged();
                    lv_payback.onRefreshComplete();
                }
            }

        }.execute();
    }

    private void initView() {
        setContentView(R.layout.payback_activity);
        img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        lv_payback = (PullToRefreshListView) findViewById(R.id.lv_payback);

        mAdapter = new PayBackAdapter(this, orders);
        lv_payback.setAdapter(mAdapter);

        initIndicator();

        lv_payback.setMode(Mode.BOTH);

        lv_payback.setOnRefreshListener(new OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                flag = true;

                currentPage = 1;
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                flag = false;
                currentPage++;
                initData();

            }
        });

    }

    private void initIndicator() {
        ILoadingLayout startLabels = lv_payback.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("释放刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = lv_payback.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("加载更多");
        endLabels.setRefreshingLabel("正在加载...");
        endLabels.setReleaseLabel("释放加载");
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img_back:
                CommonUtils.finishActivity(MainMenuActivity.instances);
                intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(this, PayBackDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", orders.get(arg2));
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
