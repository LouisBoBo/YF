package com.yssj.ui.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.entity.ShopCart;
import com.yssj.model.ComModel2;
import com.yssj.ui.adpter.IndianaListAdapter;
import com.yssj.ui.adpter.IndianaListOneAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 夺宝专区
 */
public class IndianaListActivity extends BasicActivity implements OnClickListener {
    private PullToRefreshListView r_list_view;
    private IndianaListAdapter mAdapter;
    private IndianaListOneAdapter mOneAdapter;
    private View img_back;
    private TextView titleTv;
    private int mType = 1;// 1：初始化数据；2：加载更多数据
    private int index = 1;
    public static List<ShopCart> listClick = new ArrayList<ShopCart>();
    public static IndianaListActivity instance;
    private int indianaType;//夺宝类型 2：普通夺宝 ，21：1元夺宝
    private String ShopTypeEnum; //普通夺宝 "indiana"; 1元夺宝"indiana_one";
    private boolean indianaGroups;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        instance = this;
        listClick.clear();
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_indiana_list);
        initView();
    }

    private void initView() {
        titleTv = (TextView) findViewById(R.id.tv_title);
        indianaType = SignListAdapter.doType;
        if(indianaType==2){
            ShopTypeEnum = "indiana";//普通夺宝（可一分钱参与多宝）
        }else if(indianaType==21){
            ShopTypeEnum = "indiana_one";//一元夺宝（夺宝奖励提现额度）
        }else if(indianaType==22){
            titleTv.setText("拼团抽奖专区");
            ShopTypeEnum = "indiana_group";//拼团夺宝
            indianaGroups = true;
        }else {
            ShopTypeEnum = "indiana";//默认普通夺宝
        }
        instance = this;
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        r_list_view = (PullToRefreshListView) findViewById(R.id.r_list_view);
        r_list_view.setMode(Mode.BOTH);

        if(indianaType==21){
            mOneAdapter = new IndianaListOneAdapter(this);
            r_list_view.setAdapter(mOneAdapter);
        }else{
            mAdapter = new IndianaListAdapter(this,indianaGroups);
            r_list_view.setAdapter(mAdapter);
        }

        setListViewRefresh();
        initData(index + "");

    }

    private void setListViewRefresh() {
        r_list_view.setMode(Mode.BOTH);
        r_list_view.setOnRefreshListener(new OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                listClick.clear();
                index = 1;
                mType = 1;
                initData(index + "");
//				r_list_view.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//				r_list_view.onRefreshComplete();
                index++;
                mType = 2;
                initData(index + "");
            }

        });

    }


    private void initData(final String index) {
        final int pageSize = 10;
        new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {
            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                LoadingDialog.show((FragmentActivity) context);
            }

            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
                    throws Exception {

                return ComModel2.getIndianaShopList(context, index, pageSize + "",ShopTypeEnum);
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
                    if(indianaType == 21){
                        //一元夺宝布局 不同 使用单独Adapter
                        if (mType == 1) {
                            if (result == null || result.size() == 0) {
                                r_list_view.setVisibility(View.GONE);
                            } else {
                                r_list_view.setVisibility(View.VISIBLE);
                            }

                            mOneAdapter.setData(result);

                        } else if (mType == 2) {
                            if (result == null || result.size() == 0) {
                                ToastUtil.showShortText(context, "已没有更多了哦~");
                            }else{
                                mOneAdapter.addItemLast(result);
                                r_list_view.getRefreshableView().smoothScrollBy(100, 20);
                            }
                        }
                    }else{
                        if (mType == 1) {
                            if (result == null || result.size() == 0) {
                                r_list_view.setVisibility(View.GONE);
                            } else {
                                r_list_view.setVisibility(View.VISIBLE);
                            }

                            mAdapter.setData(result);

                        } else if (mType == 2) {
                            if (result == null || result.size() == 0) {
                                ToastUtil.showShortText(context, "已没有更多商品了哦~");
                            }else{
                                mAdapter.addItemLast(result);
                                r_list_view.getRefreshableView().smoothScrollBy(100, 20);
                            }
                        }
                    }
                }
                r_list_view.onRefreshComplete();
            }

        }.execute();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();

                break;
//            case R.id.task_explain_tv:
//                new SignActiveShopExplainDialog(this).show();
//                break;
//            case R.id.fight_groups_icon://拼团详情
//                Intent intent = new Intent(context, GroupsDetailsActivity.class);
//                context.startActivity(intent);
//                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
    }


}
