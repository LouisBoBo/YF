package com.yssj.ui.fragment;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.XListViewMatch;
import com.yssj.entity.VipDikouData;
import com.yssj.eventbus.MessageEvent;
import com.yssj.model.ComModel2;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.adpter.ForceLookAdapter;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

/**
 * 首页-时尚
 */
public class HomePageJXfragment extends BaseFragment {

    private XListViewMatch dataList;

    private ForceLookAdapter mAdapter;

    private int index = 1;


    private int mType = 1;// 1：初始化数据；2：加载更多数据


    private String pinJievalue = "";


    // private String checkId;


    //	private String is_new = null, order_by_price = null;
    private String is_hot = null, is_new = "is_new", order_by_price = null;


    private Activity mActivity;

    private View view;
    private VipDikouData vipDikouData;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        mType = 1;
        vipDikouData = null;
        refresh();

    }


    @Override
    public View initView() {

        mActivity = getActivity();

        view = View.inflate(mActivity, R.layout.fragment_home_page_three, null);
        dataList = view.findViewById(R.id.dataList);


        dataList.setPullLoadEnable(true);


        mAdapter = new ForceLookAdapter(mActivity, false);
        dataList.setAdapter(mAdapter);
        setListViewRefresh();
        return view;
    }

    @Override
    public void initData() {

        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }

        if (YJApplication.instance.isLoginSucess()) {
            //查询抵扣
            HashMap<String, String> pairsMap = new HashMap<>();
            YConn.httpPost(mActivity, YUrl.GETALLDIKOU, pairsMap, new HttpListener<VipDikouData>() {
                @Override
                public void onSuccess(VipDikouData result) {

                    vipDikouData = result;
                    initData(index + "", is_new, order_by_price);

                }

                @Override
                public void onError() {

                }
            });


        } else {
            initData(index + "", is_new, order_by_price);

        }


    }

    public void refresh() {


        if (YJApplication.instance.isLoginSucess()) {
            //查询抵扣
            HashMap<String, String> pairsMap = new HashMap<>();
            YConn.httpPost(mActivity, YUrl.GETALLDIKOU, pairsMap, new HttpListener<VipDikouData>() {
                @Override
                public void onSuccess(VipDikouData result) {

                    vipDikouData = result;
                    index = 1;
                    initData(index + "", is_new, order_by_price);
                }

                @Override
                public void onError() {

                }
            });


        } else {
            index = 1;
            initData(index + "", is_new, order_by_price);
        }

    }


    private void setListViewRefresh() {


        dataList.setXListViewListener(new XListViewMatch.IXListViewListener() {

            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {

                mType = 2;
                index++;
                initData(index + "", is_new, order_by_price);
            }
        });


    }

    private void initData(final String index, final String is_new,
                          final String order_by_price) {

        new SAsyncTask<String, Void, List<HashMap<String, Object>>>((FragmentActivity) mActivity, 0) {
            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
                    throws Exception {

                //热卖推荐商品
                return ComModel2.getSignShop(context, index, pinJievalue, is_new, is_hot, order_by_price);
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
                            dataList.setVisibility(View.GONE);
                        } else {
                            dataList.setVisibility(View.VISIBLE);
                        }


                        if (null != vipDikouData && vipDikouData.getIsVip() > 0) {
                            mAdapter.setDataVip(result, true, vipDikouData);

                        } else {
//                            mAdapter.setData(result);
                            mAdapter.setDataVip(result, false, vipDikouData);


                        }


                    } else if (mType == 2) {
                        if (result == null || result.size() == 0) {
                            ToastUtil.showShortText(context, "已没有更多商品了哦~");
                        } else {
                            mAdapter.addItemLast(result);
                        }
                    }
                }

                dataList.stopLoadMore();
                if (null != HomePage3Fragment.mView) {
                    HomePage3Fragment.mView.refreshDone();
                }
            }

        }.execute(index);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}

