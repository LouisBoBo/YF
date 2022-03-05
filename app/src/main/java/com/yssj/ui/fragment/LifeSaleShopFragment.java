package com.yssj.ui.fragment;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.XListViewMatch;
import com.yssj.entity.MealShopList;
import com.yssj.entity.VipDikouData;
import com.yssj.eventbus.MessageEvent;
import com.yssj.model.ComModel2;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.adpter.NewMealListAdapter;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 首页-生活
 */
public class LifeSaleShopFragment extends BaseFragment {

    private XListViewMatch dataList;

    private NewMealListAdapter mAdapter;

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
        view = View.inflate(mActivity, R.layout.fragment_home_jx, null);
        dataList = (XListViewMatch) view.findViewById(R.id.dataList);


        dataList.setPullLoadEnable(true);


        mAdapter = new NewMealListAdapter();
        dataList.setAdapter(mAdapter);
        setListViewRefresh();
        return view;
    }

    @Override
    public void initData() {

//        initData(index + "");




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
                    initData(index + "");

                }

                @Override
                public void onError() {

                }
            });


        } else {
            initData(index + "");

        }





    }

    public void refresh() {
        mType = 1;
        index = 1;
        initData(index + "");

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
                initData(index + "");
            }
        });


//
//        r_list_view.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                // TODO Auto-generated method stub
//                mType = 1;
//                index = 1;
//                initData(index + "", is_new, order_by_price);
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                // TODO Auto-generated method stub
//                index++;
//                mType = 2;
//                initData(index + "", is_new, order_by_price);
//            }
//        });
//
//


    }

    private void initData(final String index) {

        new SAsyncTask<String, Void, MealShopList>((FragmentActivity) mActivity, 0) {
            @Override
            protected MealShopList doInBackground(FragmentActivity context, String... params)
                    throws Exception {

                return ComModel2.getNewMealShopList(context, index);
            }

            @Override
            protected boolean isHandleException() {

                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, MealShopList result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);

                if (e == null) {
                    if (mType == 1) {
                        if (result == null) {
                            dataList.setVisibility(View.GONE);
                        } else {
                            dataList.setVisibility(View.VISIBLE);
                        }

                        //只取每个shop_list中的数据
                        List<MealShopList.PListBean.ShopListBean> mShopList = new ArrayList<>();
                        if (result.getPList().size() > 0) {
                            for (int i = 0; i < result.getPList().size(); i++) {
                                if (result.getPList().get(i).getShop_list().size() > 0) {

                                    List<MealShopList.PListBean.ShopListBean> tempShopList = result.getPList().get(i).getShop_list();
                                    //设置all_shop_code
                                    for (int y = 0; y < tempShopList.size(); y++) {
                                        tempShopList.get(y).setAll_shop_code(result.getPList().get(i).getCode());
                                    }

                                    //设置单件商品的一元购价格
                                    for (int y = 0; y < tempShopList.size(); y++) {
                                        tempShopList.get(y).setApp_shop_group_price(result.getPList().get(i).getApp_shop_group_price());
                                    }
                                    //重新设置shop_se_price
                                    for (int y = 0; y < tempShopList.size(); y++) {
                                        if(null != result.getPList().get(i).getShop_se_price()){
                                            tempShopList.get(y).setShop_se_price(result.getPList().get(i).getShop_se_price());
                                        }
                                    }

                                    //重新设置four_pic
                                    for (int y = 0; y < tempShopList.size(); y++) {
                                        tempShopList.get(y).setFour_pic(result.getPList().get(i).getDef_pic());
                                    }



                                    mShopList.addAll(tempShopList);
                                }
                            }
                        }


                        if (null != vipDikouData && vipDikouData.getIsVip() >0) {
                            mAdapter.setDataVip(mShopList, true, vipDikouData);

                        } else {
//                            mAdapter.setData(result);

                            mAdapter.setDataVip(mShopList, false, vipDikouData);

                        }



                        } else if (mType == 2) {
                        if (result == null || result.getPList().size() == 0 ) {
                            ToastUtil.showShortText(context, "已没有更多商品了哦~");
                        } else {

                            //只取每个shop_list中的数据
                            List<MealShopList.PListBean.ShopListBean> mShopList = new ArrayList<>();
                            if (result.getPList().size() > 0) {
                                for (int i = 0; i < result.getPList().size(); i++) {
                                    if (result.getPList().get(i).getShop_list().size() > 0) {

                                        List<MealShopList.PListBean.ShopListBean> tempShopList = result.getPList().get(i).getShop_list();
                                        //设置all_shop_code
                                        for (int y = 0; y < tempShopList.size(); y++) {
                                            tempShopList.get(y).setAll_shop_code(result.getPList().get(i).getCode());
                                        }
                                        //设置单件商品的一元购价格
                                        for (int y = 0; y < tempShopList.size(); y++) {
                                            tempShopList.get(y).setApp_shop_group_price(result.getPList().get(i).getApp_shop_group_price());
                                        }


                                        //重新设置shop_se_price
                                        for (int y = 0; y < tempShopList.size(); y++) {
                                            tempShopList.get(y).setShop_se_price(result.getPList().get(i).getShop_se_price());
                                        }

                                        //重新设置four_pic
                                        for (int y = 0; y < tempShopList.size(); y++) {
                                            tempShopList.get(y).setFour_pic(result.getPList().get(i).getDef_pic());
                                        }



                                        mShopList.addAll(tempShopList);

                                    }
                                }
                            }


                            mAdapter.addItemLast(mShopList);
                        }
                    }
                }
                dataList.stopLoadMore();
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

