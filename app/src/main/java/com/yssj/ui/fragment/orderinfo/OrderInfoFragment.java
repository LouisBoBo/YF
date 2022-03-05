package com.yssj.ui.fragment.orderinfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.entity.Order;
import com.yssj.model.ComModel2;
import com.yssj.network.YConn;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.OneBuyChouJiangActivity;
import com.yssj.ui.activity.OneBuyGroupsDetailsActivity;
import com.yssj.ui.activity.infos.StatusInfoActivity;
import com.yssj.ui.fragment.orderinfo.OrderListAdapter.notifyDatas;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DateUtil;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.LogYiFu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/****
 * 买单列表界面
 * @author Administrator
 *
 */
@SuppressLint("NewApi")
public class OrderInfoFragment extends Fragment implements OnItemClickListener, OnClickListener, notifyDatas {

    public static boolean mBuyFHKsuc = false;

    private int status;

    private PullToRefreshListView listView;
    private OrderListAdapter mAdapter;

    private int index = 1;

    public static boolean isAdd = false;

    private List<Order> orders = new ArrayList<Order>();

    private LinearLayout layout_nodata;
    private Button btn_to_shop;

    protected int PAGESIZE = 10;
    public static boolean isComplete = false;
    private Context mContext;

    public static int send_num;//发货张数
    public static int free_num;//免拼卡张数
    public static long dayEndTime;//免费领订单刷新时间
    public static long now;//当前时间
    public static String current_date;
    public static int isVip;
    public static int maxType;


    public static String freeLingTimeStr = "";


    private View v;

    @SuppressLint("ValidFragment")
    public OrderInfoFragment(int status) {
        this.status = status;
    }

    public OrderInfoFragment() {
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        getData(index);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        LogYiFu.e("nearbyFragment onCreate", "nearbyFragment onCreate");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.trade_fragment, container,
                false);


        initView(v);
        return v;
    }

    private void initView(View v) {

        layout_nodata = (LinearLayout) v.findViewById(R.id.layout_nodata);

        btn_to_shop = (Button) v.findViewById(R.id.btn_to_shop);
        btn_to_shop.setOnClickListener(this);

        listView = (PullToRefreshListView) v.findViewById(R.id.trade_listview);
        mAdapter = new OrderListAdapter(getActivity(), orders, this, v.findViewById(R.id.main));
        listView.setAdapter(mAdapter);
        mAdapter.setNotifyDatas(this);
        listView.setOnItemClickListener(this);
        listView.setMode(Mode.BOTH);
        listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                index = 1;
                isAdd = false;
                getData(index);
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                isAdd = true;
                if (isComplete) {
                    Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_LONG).show();
                    listView.onRefreshComplete();
                } else {
                    getData(++index);
                }
            }
        });


    }

    private Timer timer;

    private void initFreeLingTime() {
        if (null != timer) {
            timer.cancel();
            timer = null;
        }

        if (dayEndTime - now <= 1000) {
            freeLingTimeStr = "";
            return;
        }

        final long[] reTime = {dayEndTime - now};
        freeLingTimeStr = "剩余" + DateUtil.FormatMilliseondToEndTime2(reTime[0]);


        timer = new Timer();


        timer.schedule(

                new TimerTask() {
                    @Override
                    public void run() {


                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (reTime[0] <= 0) {
                                    freeLingTimeStr = "订单已过期";


                                    index = 1;
                                    isAdd = false;
                                    getData(index);

                                    timer.cancel();
                                } else {
                                    reTime[0] -= 1000;
                                    freeLingTimeStr = "剩余 " + DateUtil.FormatMilliseondToEndTime2(reTime[0]);
                                }
                                mAdapter.notifyDataSetChanged();

                            }
                        });


                    }
                },

                0, 1000
        );


    }

    public void showNoDataPager() {
        layout_nodata.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        listView.onRefreshComplete();
    }

    private void getData(int index) {


        new SAsyncTask<Integer, Void, List<Order>>(getActivity(), null, 0) {

            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                LoadingDialog.show((FragmentActivity) mContext);
            }

            @Override
            protected List<Order> doInBackground(FragmentActivity context,
                                                 Integer... params) throws Exception {
                // TODO Auto-generated method stub
                return ComModel2.getOrderList(context, params[0], status, "");
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         List<Order> result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result);
                LoadingDialog.hide(mContext);
                if (e != null) {// 查询异常
                    layout_nodata.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    listView.onRefreshComplete();
                } else {// 查询商品详情成功，刷新界面

                    if (mBuyFHKsuc) {
                        mBuyFHKsuc = false;
                        DialogUtils.showNewUserFirstOrderDialog(mContext, orders.get(0), send_num, isVip);
                    }

                    if (isAdd) {
//						orders.clear();
                        orders.addAll(result);
                    } else {
                        if (result != null && result.size() == 0) {
                            layout_nodata.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                        }
                        orders.clear();
                        orders.addAll(result);
                        if (StatusInfoActivity.isNewUserFirstOrder) {
                            StatusInfoActivity.isNewUserFirstOrder = false;
                            DialogUtils.showNewUserFirstOrderDialog(mContext, orders.get(0), send_num, isVip);
                        }

                        //处理免费领到倒计时
                        initFreeLingTime();


                    }
                    if (result.size() < PAGESIZE) {
                        isComplete = true;
                    } else {
                        isComplete = false;
                    }
                    mAdapter.notifyDataSetChanged();
                    listView.onRefreshComplete();
                }

            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;

        }.execute(index);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

//        if (orders.get(arg2).getShop_from() == 11) {//1元购拼团单独处理

//            Intent OneBuyintent = new Intent(mContext, OneBuyGroupsDetailsActivity.class);
//            OneBuyintent.putExtra("isMeal", "1".equals(orders.get(arg2).getIsTM()));
//
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("order", orders.get(arg2));
//            OneBuyintent.putExtras(bundle);
//            startActivity(OneBuyintent);
//            ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

        if (orders.get(arg2).getStatus().equals("2") && orders.get(arg2).getShop_from() == 11 && orders.get(arg2).getIs_roll() != 3) {//拼团中--去拼团详情已付款


            Intent OneBuyintent = new Intent(mContext, OneBuyGroupsDetailsActivity.class);
            OneBuyintent.putExtra("isMeal", "1".equals(orders.get(arg2).getIsTM()));

            Bundle bundle = new Bundle();
            bundle.putSerializable("order", orders.get(arg2));
            OneBuyintent.putExtra("roll_code", orders.get(arg2).getRoll_code());

            OneBuyintent.putExtras(bundle);
            startActivity(OneBuyintent);
            ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


        } else if (orders.get(arg2).getStatus().equals("1") && orders.get(arg2).getShop_from() == 11) {//拼团中--去拼团详情


            Intent OneBuyintent = new Intent(mContext, OneBuyGroupsDetailsActivity.class);
            OneBuyintent.putExtra("isMeal", "1".equals(orders.get(arg2).getIsTM()));

            Bundle bundle = new Bundle();
            bundle.putSerializable("order", orders.get(arg2));
            OneBuyintent.putExtra("roll_code", orders.get(arg2).getRoll_code());

            OneBuyintent.putExtras(bundle);
            startActivity(OneBuyintent);
            ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


        } else if (orders.get(arg2).getStatus().equals("11") || orders.get(arg2).getStatus().equals("15")) {//拼团中--去拼团详情


            Intent OneBuyintent = new Intent(mContext, OneBuyGroupsDetailsActivity.class);
            OneBuyintent.putExtra("isMeal", "1".equals(orders.get(arg2).getIsTM()));

            Bundle bundle = new Bundle();
            bundle.putSerializable("order", orders.get(arg2));
            OneBuyintent.putExtra("roll_code", orders.get(arg2).getRoll_code());

            OneBuyintent.putExtras(bundle);
            startActivity(OneBuyintent);
            ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


        } else if (orders.get(arg2).getStatus().equals("12")) {//待疯抢


            Intent OneBuyintent = new Intent(mContext, OneBuyChouJiangActivity.class);
            OneBuyintent.putExtra("isMeal", "1".equals(orders.get(arg2).getIsTM()));

            Bundle bundle = new Bundle();
            bundle.putSerializable("order", orders.get(arg2));
            OneBuyintent.putExtras(bundle);

            startActivity(OneBuyintent);
            ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


        } else if (orders.get(arg2).getStatus().equals("13")) {//拼团失败

            Intent OneBuyintent = new Intent(mContext, OneBuyGroupsDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("order", orders.get(arg2));
            OneBuyintent.putExtras(bundle);
            OneBuyintent.putExtra("isMeal", "1".equals(orders.get(arg2).getIsTM()));
            OneBuyintent.putExtra("ptFail", true);
            OneBuyintent.putExtra("roll_code", orders.get(arg2).getRoll_code());

            startActivity(OneBuyintent);
            ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

        } else if (orders.get(arg2).getStatus().equals("14")) {//抽奖未抽中
            Intent intent = new Intent(getActivity(),
                    OrderDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("order", orders.get(arg2));
            intent.putExtras(bundle);
            isAdd = false;
            startActivityForResult(intent, 104);
        } else {//其他
            Intent intent = new Intent(getActivity(),
                    OrderDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("order", orders.get(arg2));
            intent.putExtras(bundle);
            isAdd = false;
            startActivityForResult(intent, 104);
        }


//        } else {
//            Intent intent = new Intent(getActivity(),
//                    OrderDetailsActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("order", orders.get(arg2));
//            intent.putExtras(bundle);
//            isAdd = false;
//            startActivityForResult(intent, 104);
//        }


    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        Intent intent = null;
        switch (arg0.getId()) {
            case R.id.btn_to_shop:
//			intent = new Intent();
//			getActivity().setResult(10001,intent);
//			getActivity().finish();
//			111

                CommonUtils.finishActivity(MainMenuActivity.instances);

                Intent intent2 = new Intent(getActivity(), MainMenuActivity.class);
                intent2.putExtra("toYf", "toYf");
                (getActivity()).startActivity(intent2);
                (getActivity()).finish();
                break;

            default:
                break;
        }
    }

    /**
     * 跳转到评价的时候需要关闭当前Fragment
     */
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
        index = 1;
        isAdd = false;
        getData(index);
    }

}
