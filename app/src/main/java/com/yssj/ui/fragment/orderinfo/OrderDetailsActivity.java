package com.yssj.ui.fragment.orderinfo;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.MyListView;
import com.yssj.custom.view.PayPasswordCustomDialog;
import com.yssj.custom.view.PayPasswordCustomDialog.InputDialogListener;
import com.yssj.entity.CheckPwdInfo;
import com.yssj.entity.DJKdetail;
import com.yssj.entity.Order;
import com.yssj.entity.OrderShop;
import com.yssj.entity.RemainShipInfo;
import com.yssj.entity.ReturnInfo;
import com.yssj.eventbus.MessageEvent;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.infos.AppendEvaluateOrderNewActivity;
import com.yssj.ui.activity.infos.EvaluateOrderNewActivity;
import com.yssj.ui.activity.infos.LogisticsInfoActivity;
import com.yssj.ui.activity.infos.SetMyPayPassActivity;
import com.yssj.ui.activity.payback.PaybackCommonFragmentActivity;
import com.yssj.ui.activity.shopdetails.OrderPaymentActivity;
import com.yssj.ui.activity.shopdetails.ShaiDanActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DateUtil;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.SingleChoicePopupWindow;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/*****
 * 订单详情界面
 *
 * @author Administrator
 *
 */
public class OrderDetailsActivity extends BasicActivity implements OnClickListener {
    private MyListView listview;

    private Order order; // 订单
    private OrderListSonAdapter adapter;

    private TextView tvTitle_base;

    private TextView tv_order_status, tv_order_amount, tv_order_no, tv_order_time, tv_receiver, tv_phone,
            tv_detail_address, tv_money;

    private TextView tv_logistic_info, tv_add_time, tv_postage;
    private View logistic_divider;
    private RelativeLayout rel_logistic, main;

    private RelativeLayout rel_prepay, rel_prereceive, rel_prejudge;// 待付款，待收货，待评价显示的按钮布局

    private RelativeLayout rel_pre_diliver, rel_pre_receive_btns, rel_finish;// 中间待发货，待收货，已完结
    // 按钮的隐藏和显示
    private LinearLayout rel_appendjudge;

    private RelativeLayout rel_dateout; // 删除过时订单

    private RelativeLayout rel_extends_receive;// 延长收货状态
    private List<HashMap<String, Object>> mapLogistic;

    private PayPasswordCustomDialog customDialog;

    private InputDialogListener inputDialogListener;

    private Button btn_payback;
    private ImageView img_right_icon;
    protected AlertDialog dialog;
    private List<String> cancleOrderDatas = new ArrayList<String>(); // 取消订单

    public static OrderDetailsActivity instance;
    private String shop_code;// 活动商品商品详情编号
    private String sign_huodong;
    private boolean paySuccess;
    private Context context;

    private void getCancleOrderDatas() {
        cancleOrderDatas.add("卖家缺货");
        cancleOrderDatas.add("信息填写错误重新拍");
        cancleOrderDatas.add("我不想买了");
        cancleOrderDatas.add("同城见面交易");
        cancleOrderDatas.add("拍错了");
        cancleOrderDatas.add("其他原因");
    }

    private Date date;
    private long timeout;
    private Long now;
    private TextView mRemainningTime;
    private long recLen;
    Timer timer = new Timer();
    private String TAG = "OrderDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detalis);
        context = this;
        EventBus.getDefault().register(this);//注册

//		aBar.hide();
        Bundle b = getIntent().getExtras();
        order = (Order) b.getSerializable("order");
        sign_huodong = getIntent().getStringExtra("sign_huodong");
        shop_code = getIntent().getStringExtra("shop_code");
        date = order.getLast_time();
        paySuccess = getIntent().getBooleanExtra("paySuccess", false);
        timeout = date.getTime();
        now = getIntent().getLongExtra("nowTime", System.currentTimeMillis());
        LogYiFu.e(TAG, now + "iiiiiiiii");
        // now = order.getNowss();
        // now = System.currentTimeMillis();
        // System.out.println("//___" + null == order.getNowss().toString());
        // recLen = timeout - Long.parseLong(order.getNowss().toString());
        recLen = timeout - now;
        listChecked.add(order);

        instance = this;

        // System.out.println("order:" + order);
        // if (order == null || order.getList() == null) {
        // Toast.makeText(this, "该订单不存在，请重新确认", Toast.LENGTH_SHORT).show();
        // finish();
        // return;
        // }
//        if (paySuccess) {
//            DialogUtils.paySuccessDialog(this, order.getList().get(0).getShop_price());
//
//        }

        if (order == null) {
            Toast.makeText(this, "该订单不存在，请重新确认", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        initView();
        if (order.getShop_from() == 3 || order.getShop_from() == 4 || order.getShop_from() == 6) {
            listview.setSelector(R.drawable.sign_order_details_selector);
        }
        timer.schedule(task, 0, 1000); // timeTask

        //处理发货卡
        initFHK();
    }

    private void initFHK() {
        if(OrderListAdapter.sqfhClickEd){
            return;
        }

        if (order.getShop_from() == 13//有发货卡
                && order.getWhether_prize() == 2
                && order.getNew_free() == 1
                && OrderInfoFragment.send_num > 0
                && OrderInfoFragment.send_num < 3
                && !order.getStatus().equals("9")) {
            DialogUtils.showNewUserFirstOrderDialog(instance, order, OrderInfoFragment.send_num, 0);//已经买了发货卡就不需要会员的判断了


        }


    }

    // 倒计时
    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen -= 1000;
                    String days;
                    String hours;
                    String minutes;
                    String seconds;
                    long minute = recLen / 60000;
                    long second = (recLen % 60000) / 1000;
                    if (minute >= 60) {
                        long hour = minute / 60;
                        minute = minute % 60;
                        if (hour >= 24) {
                            long day = hour / 24;
                            hour = hour % 24;
                            if (day < 10) {
                                days = "0" + day;
                            } else {
                                days = "" + day;
                            }
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
                            mRemainningTime.setText("" + days + "天" + hours + "时" + minutes + "分" + seconds + "秒");
                        } else {
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
                            mRemainningTime.setText("" + hours + "时" + minutes + "分" + seconds + "秒");
                        }
                    } else if (minute >= 10 && second >= 10) {
                        mRemainningTime.setText("" + minute + "分" + second + "秒");
                    } else if (minute >= 10 && second < 10) {
                        mRemainningTime.setText("" + minute + "分0" + second + "秒");
                    } else if (minute < 10 && second >= 10) {
                        mRemainningTime.setText("0" + minute + "分" + second + "秒");
                    } else {
                        mRemainningTime.setText("0" + minute + "分0" + second + "秒");
                    }
                    // mRemainningTime.setText("" + recLen);
                    if (recLen < 0) {
                        timer.cancel();
                        mRemainningTime.setText("此订单已过期");
                        // mRemainningTime.setVisibility(View.GONE);
                    }
                }
            });
        }
    };

    private void initView() {

        findViewById(R.id.img_back).setOnClickListener(this);
        tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("订单详情");

        /*
         * 右上角点点点
         */
        main = (RelativeLayout) findViewById(R.id.main);
        main.setBackgroundColor(Color.WHITE);
        img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
        img_right_icon.setVisibility(View.VISIBLE);
        img_right_icon.setImageResource(R.drawable.mine_message_center);
        img_right_icon.setOnClickListener(this);
        img_right_icon.setVisibility(View.GONE);
        findViewById(R.id.lin_contact_seller).setOnClickListener(this);// 联系卖家
        findViewById(R.id.lin_call).setOnClickListener(this);// 拨打电话

        tv_order_status = (TextView) findViewById(R.id.tv_order_status);// 订单状态
        tv_order_amount = (TextView) findViewById(R.id.tv_order_amount);// 订单总金额
        tv_order_no = (TextView) findViewById(R.id.tv_order_no);// 订单编号
        tv_order_time = (TextView) findViewById(R.id.tv_order_time);// 添加订单时间
        tv_receiver = (TextView) findViewById(R.id.tv_receiver);// 收货人
        tv_phone = (TextView) findViewById(R.id.tv_phone);// 电话号码
        tv_detail_address = (TextView) findViewById(R.id.tv_detail_address);// 收货地址

        tv_money = (TextView) findViewById(R.id.tv_money);// 订单金额

        rel_logistic = (RelativeLayout) findViewById(R.id.rel_logistic);
        rel_logistic.setOnClickListener(this);

        tv_logistic_info = (TextView) findViewById(R.id.tv_logistic_info);
        tv_add_time = (TextView) findViewById(R.id.tv_add_time);

        logistic_divider = findViewById(R.id.logistic_divider);
        rel_prepay = (RelativeLayout) findViewById(R.id.rel_prepay);
        findViewById(R.id.btn_cancel_order).setOnClickListener(this);// 取消订单
        findViewById(R.id.btn_pay).setOnClickListener(this);// 付款
        mRemainningTime = (TextView) findViewById(R.id.remainning_time);// 倒计时
        rel_prereceive = (RelativeLayout) findViewById(R.id.rel_prereceive);
        findViewById(R.id.btn_ck_logistic).setOnClickListener(this);// 查看物流
        findViewById(R.id.btn_yc_sh).setOnClickListener(this);// 延长收货
        findViewById(R.id.btn_qrsh).setOnClickListener(this);// 确认收货
        rel_prejudge = (RelativeLayout) findViewById(R.id.rel_prejudge);
        findViewById(R.id.btn_judge).setOnClickListener(this);// 评价订单

        findViewById(R.id.btn_prejudge_delete).setOnClickListener(this);// 删除订单

        rel_appendjudge = (LinearLayout) findViewById(R.id.rel_appendjudge); // 已完结的订单

        Button btn_appendjudge = (Button) findViewById(R.id.btn_appendjudge);
        findViewById(R.id.btn_appendjudge).setOnClickListener(this);// 追加评价
        findViewById(R.id.btn_appendjudge_delete).setOnClickListener(this);// 删除订单

        rel_dateout = (RelativeLayout) findViewById(R.id.rel_dateout);
        findViewById(R.id.btn_delete_dateout).setOnClickListener(this); // 删除过时订单

        rel_pre_diliver = (RelativeLayout) findViewById(R.id.rel_pre_diliver);
        findViewById(R.id.btn_diliver_remind).setOnClickListener(this);// 提醒发货

        rel_pre_receive_btns = (RelativeLayout) findViewById(R.id.rel_pre_receive_btns);
        btn_payback = (Button) findViewById(R.id.btn_payback);
        btn_payback.setOnClickListener(this);// 退款
        btn_payback.setVisibility(View.GONE);

        rel_extends_receive = (RelativeLayout) findViewById(R.id.rel_extends_receive);
        findViewById(R.id.btn_ck_logistic7).setOnClickListener(this);
        findViewById(R.id.btn_qrsh7).setOnClickListener(this);

        rel_finish = (RelativeLayout) findViewById(R.id.rel_finish);
        findViewById(R.id.btn_service).setOnClickListener(this);// 申请售后

        listview = (MyListView) findViewById(R.id.listview);// 商品列表
        if ("sign_huodong".equals(sign_huodong)) {
            adapter = new OrderListSonAdapter(this, order, false, true, "" + shop_code);
        } else {
            adapter = new OrderListSonAdapter(this, order, false, false, "");
        }

        listview.setAdapter(adapter);

        tv_postage = (TextView) findViewById(R.id.tv_postage);

        if (order.getShop_from() == 4 || order.getShop_from() == 6) {
            btn_appendjudge.setVisibility(View.GONE);
        }

        refreshView();
    }

    /****
     * 刷新界面
     */
    private void refreshView() {
        adapter.notifyDataSetChanged();
        int status = Integer.parseInt(order.getStatus().toString());
        switch (status) {
            case 1:
                if (order.getLast_time().before(new Date(now))) {
                    tv_order_status.setText("已过期");
                    rel_logistic.setVisibility(View.GONE);
                    rel_dateout.setVisibility(View.VISIBLE);
                } else {
                    if (order.getPay_status() == 1) {
                        tv_order_status.setText("支付中");
                    } else {
                        tv_order_status.setText("待付款");
                    }
                    logistic_divider.setVisibility(View.GONE);
                    rel_logistic.setVisibility(View.GONE);
                    rel_prepay.setVisibility(View.VISIBLE);
                }

                break;
            case 2:


                if (order.getWhether_prize() == 2 || order.getNew_free() == 1) {
                    tv_order_status.setText("申请发货中");
                } else if (order.getWhether_prize() == 3) {
                    tv_order_status.setText("待发货");
                } else {
                    tv_order_status.setText("待发货");

                }


                logistic_divider.setVisibility(View.GONE);
                rel_logistic.setVisibility(View.GONE);
                rel_pre_diliver.setVisibility(View.VISIBLE);
                break;
            case 3:
                getLogistics(order);
                tv_order_status.setText("待收货");
                rel_prereceive.setVisibility(View.VISIBLE);
                // rel_pre_receive_btns.setVisibility(View.VISIBLE);
                break;
            case 4:
                getLogistics(order);
                tv_order_status.setText("待评价");
                rel_prejudge.setVisibility(View.VISIBLE);
                break;
            case 5:
                getLogistics(order);
                tv_order_status.setText("已评价");
                rel_appendjudge.setVisibility(View.VISIBLE);
                break;
            case 6:
                getLogistics(order);
                tv_order_status.setText("已完结");
                // rel_finish.setVisibility(View.VISIBLE);
                break;

            case 7:
                getLogistics(order);
                tv_order_status.setText("延长收货");
                rel_extends_receive.setVisibility(View.VISIBLE);
                rel_pre_receive_btns.setVisibility(View.VISIBLE);
                break;
            case 9:
                // getLogistics(order);
                tv_order_status.setText("取消订单");
                rel_logistic.setVisibility(View.GONE);
                break;
            case 14:

                // getLogistics(order);
                tv_order_status.setText("疯抢未抢到");
                rel_logistic.setVisibility(View.GONE);
                break;
            case 17:

                // getLogistics(order);
                tv_order_status.setText("申请发货中");
                rel_logistic.setVisibility(View.GONE);
                break;
            case 21:

                // getLogistics(order);
                tv_order_status.setText("分享中");
                rel_logistic.setVisibility(View.GONE);
                break;

        }
        String addTime = DateUtil.FormatMillisecond(order.getAdd_time());
        if (!TextUtils.isEmpty(addTime)) {
            tv_order_time.setText("下单时间：" + addTime);
        }
        String order_price = "" + order.getOrder_price();
//        if (!TextUtils.isEmpty(order_price)) {
//
//            // 订单金额
//            tv_order_amount.setText("订单金额(包邮): " + new java.text.DecimalFormat("#0.00").format(order.getOrder_price()));
//
//            // 实付
//            tv_money.setText("¥" + new java.text.DecimalFormat("#0.00").format(order.getPay_money()));
//        }

        tv_money.setText("¥" + new java.text.DecimalFormat("#0.00").format(order.getPay_money()));
        tv_order_amount.setText("订单金额(包邮): ¥" + new java.text.DecimalFormat("#0.00").format(order.getPay_money()));

        String consignee = order.getConsignee();
        if (!TextUtils.isEmpty(consignee)) {
            tv_receiver.setText("收货人： " + consignee);
        }
        String phone = order.getPhone();
        if (!TextUtils.isEmpty(phone)) {
            tv_phone.setText("电话： " + phone);
        }

        String address = order.getAddress();
        if (!TextUtils.isEmpty(address)) {
            tv_detail_address.setText("收货人地址： " + address);
        }
        String order_code = order.getOrder_code();
        if (!TextUtils.isEmpty(order_code)) {
            tv_order_no.setText("订单号 ： " + order_code);
        }
        if (null != order.getPostage())
            tv_postage.setText("¥" + new java.text.DecimalFormat("#0.00").format(order.getPostage()));
    }

    // 去快递100中得到数据
    private void getLogistics(final Order order) {

        if (order.getShop_from() == 10) {
            rel_logistic.setVisibility(View.GONE);
            return;
        }

        new SAsyncTask<Void, Void, List<HashMap<String, Object>>>(this, R.string.wait) {

            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getLogistics(context, order.getLogi_code());
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {

                    if (null != result && 0 != result.size()) {
                        tv_logistic_info.setText((CharSequence) result.get(0).get("context"));
                        tv_add_time.setText((CharSequence) result.get(0).get("time"));
                        logistic_divider.setVisibility(View.VISIBLE);
                        rel_logistic.setVisibility(View.VISIBLE);
                    } else {
                        rel_logistic.setVisibility(View.GONE);
                    }
                    OrderDetailsActivity.this.mapLogistic = result;
                }
            }

        }.execute();
    }

    private void callPhone(View v) {
        new SAsyncTask<Integer, Void, String>(OrderDetailsActivity.this, v, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected String doInBackground(FragmentActivity context, Integer... params) throws Exception {
                // TODO Auto-generated method stub
                return ComModel2.getSuppPhone(context, params[0]);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, String result, Exception e) {
                super.onPostExecute(context, result, e);
                // if (null == e) {
                // if (null != result && !"null".equals(result)) {
                // // 用intent启动拨打电话
                // Intent intent = new Intent(Intent.ACTION_DIAL,
                // Uri.parse("tel:" + result));
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // startActivity(intent);
                // } else {
                String number = "4008884224"; // 客服电话
                // 用intent启动拨打电话
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            // }
            // }

        }.execute(order.getSupp_id());
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        Bundle bundle = null;
        switch (v.getId()) {
            case R.id.btn_cancel_order:// 取消订单
                if (order.getPay_status() == 1) {
                    Toast.makeText(context, "支付处理中", Toast.LENGTH_SHORT).show();
                    return;
                }
                cancleOrder();
                break;
            case R.id.img_back:// 返回按钮

                // MealPaymentActivity.instance.finish();
                OrderDetailsActivity.instance.finish();

                break;
            case R.id.img_right_icon:// 消息盒子
                WXminiAppUtil.jumpToWXmini(this);

                break;
            case R.id.btn_pay:// 付款
                // goToPay(order);
//			long nowTimes = new Date().getTime();
//			long issueEndTime = order.getIssue_endtime();
//			if ((order.getShop_from() == 4||order.getShop_from() == 6) && issueEndTime != 0 && nowTimes >= issueEndTime) {
//				ToastUtil.showShortText(context, "中奖号码已经揭晓！");
//				return;
//			}
                if (order.getPay_status() == 1) {
                    Toast.makeText(context, "支付处理中", Toast.LENGTH_SHORT).show();
                    return;
                }
                intentTOPayment(listChecked.get(0).getOrder_code(), listChecked.get(0).getOrder_price(), false);
                break;
            case R.id.lin_call:// 拨打电话
                callPhone(v);
                break;
            case R.id.btn_ck_logistic7:// 延长收货-查看物流
            case R.id.rel_logistic:// 查看物流
            case R.id.btn_ck_logistic:// 待收货-查看物流
                intent = new Intent(this, LogisticsInfoActivity.class);
                bundle = new Bundle();
                bundle.putSerializable("result", (Serializable) mapLogistic);
                intent.putExtras(bundle);
                bundle = new Bundle();
                bundle.putSerializable("order", order);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.btn_yc_sh:// 待收货-延长收货
                customDialog(1, 0);
                break;
            case R.id.btn_qrsh7:
            case R.id.btn_qrsh:// 待收货-确认收货
                affirmOrder(v, order);
                break;
            case R.id.btn_judge:// 待评价-评价订单
                boolean isIn = false;

                if (order.getShop_from() == 4 || order.getShop_from() == 6) {

                    Intent intent2 = new Intent(context, ShaiDanActivity.class);
                    intent2.putExtra("in_code", order.getParticipation_code());
                    intent2.putExtra("shop_code", order.getBak());
                    intent2.putExtra("shop_name", order.getOrder_name());
                    intent2.putExtra("issue_code", order.getIssue_code());
                    context.startActivity(intent2);
                } else {

                    for (int i = 0; i < order.getList().size(); i++) {
                        OrderShop os = order.getList().get(i);
                        if (os.getChange() == 1) {
                            if (os.getStatus() == 1) {// 换货处理中
                                continue;
                            }
                        }
                        if (os.getChange() == 2) {
                            if (os.getStatus() == 1 || os.getStatus() == 3) {// 退货处理中，已成功
                                continue;
                            }
                        }
                        if (os.getChange() == 3) {
                            if (os.getStatus() == 1 || os.getStatus() == 3) {// 退款处理中，已成功
                                continue;
                            }
                        }
                        isIn = true;
                    }
                    if (!isIn) {
                        ToastUtil.showShortText(context, "商品售后处理中，不能评价");
                        return;
                    }
                    intent = new Intent(this, EvaluateOrderNewActivity.class);
                    bundle = new Bundle();
                    bundle.putSerializable("order", order);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    // finish();
                }
                break;
            case R.id.btn_prejudge_delete:// 待评价-删除订单
                // escOrder(order.getOrder_code());
                customDialog(2, 1);
                break;
            case R.id.btn_service:// 申请售后
                /*
                 * intent = new Intent(this, ThhActivity.class); bundle = new
                 * Bundle(); bundle.putSerializable("order", order);
                 * intent.putExtras(bundle); startActivity(intent);
                 */
                intent = new Intent(context, PaybackCommonFragmentActivity.class);
                intent.putExtra("order_code", order.getOrder_code());
                intent.putExtra("flag", "payBackChoiceServiceFragment");
                intent.putExtra("order_price", order.getOrder_price() + "");
                if ("4".equals(order.getStatus()) || "5".equals(order.getStatus()) || "6".equals(order.getStatus())) {
                    if (order.getIs_kick() == 1) {
                        intent.putExtra("isHH", true);
                    }

                }
                startActivity(intent);

                break;
            case R.id.btn_payback:// 退款
                /*
                 * intent = new Intent(this, ThhActivity.class); bundle = new
                 * Bundle(); bundle.putSerializable("order", order);
                 * intent.putExtras(bundle); startActivity(intent);
                 */

                intent = new Intent(context, PaybackCommonFragmentActivity.class);
                intent.putExtra("order_code", order.getOrder_code());
                intent.putExtra("flag", "tKFragment");
                intent.putExtra("order_price", order.getOrder_price() + "");
                startActivity(intent);

                break;
            case R.id.lin_contact_seller:// 联系卖家
//                intent = new Intent(OrderDetailsActivity.this, ChatActivity.class);
//                intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
//                intent.putExtra("sell", "buyyer");
//                startActivity(intent);
                WXminiAppUtil.jumpToWXmini(this);

                break;
            case R.id.btn_diliver_remind:// 提醒发货
                // intent = new Intent(OrderDetailsActivity.this,
                // ChatActivity.class);
                // intent.putExtra("userId", String.valueOf(order.getSupp_id()));
                // startActivity(intent);

                remainOrdershop(order.getOrder_code());

                break;

            case R.id.btn_appendjudge: // 追加评价
                intent = new Intent(OrderDetailsActivity.this, AppendEvaluateOrderNewActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("order", order);
                intent.putExtras(bundle2);
                startActivity(intent);
                break;
            case R.id.btn_appendjudge_delete: // 删除订单
                customDialog(2, 2);
                break;
            case R.id.btn_delete_dateout: // 删除过时订单
                customDialog(2, 2);
                break;
        }
    }

    /**
     * 底部弹出选择框，选择取消订单原因
     */
    private void cancleOrder() {
        cancleOrderDatas.clear();
        getCancleOrderDatas();
        // 自定义的弹出框-----------取消订单和退款使用
        final SingleChoicePopupWindow menuWindow;
        menuWindow = new SingleChoicePopupWindow(context, cancleOrderDatas);
        menuWindow.setTitle("取消订单");

        // 显示窗口
        menuWindow.showAtLocation(OrderDetailsActivity.this.findViewById(R.id.main),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

        menuWindow.setOnOKButtonListener(new OnClickListener() {

            public void onClick(View v) {
                menuWindow.dismiss();
                String selItem = menuWindow.getSelectItem();
                escOrder(order.getOrder_code(), selItem);

            }
        });
    }

    // 延长收货和删除订单提示框
    private void customDialog(int type, final int flag) {
        AlertDialog.Builder builder = new Builder(OrderDetailsActivity.this);
        // 自定义一个布局文件
        View view = View.inflate(OrderDetailsActivity.this, R.layout.payback_esc_apply_dialog, null);
        TextView tv_des = (TextView) view.findViewById(R.id.tv_des);

        Button ok = (Button) view.findViewById(R.id.ok);
        ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
        Button cancel = (Button) view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 把这个对话框取消掉
                dialog.dismiss();
            }
        });

        switch (type) {
            case 1:
                tv_des.setText("  确认延长收货时间吗？\n每笔订单只能延长一次哦");
                ok.setOnClickListener(new OnClickListener() { // 延长收货

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        extensionOrdershop(order.getOrder_code());
                    }
                });
                break;
            case 2:
                tv_des.setText("是否确定删除订单");
                ok.setOnClickListener(new OnClickListener() { // 删除订单

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        returnShop(order.getOrder_code(), flag);
                    }
                });
                break;

        }

        dialog = builder.create();
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }

    /******
     * 延长收货
     *
     * @param order_code
     */
    private void extensionOrdershop(String order_code) {
        new SAsyncTask<String, Void, ReturnInfo>(this, null, R.string.wait) {
            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                return ComModel.extensionOrdershop(context, YCache.getCacheToken(context), params[0]);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo r, Exception e) {

                if (e != null) {// 查询异常
                    LogYiFu.e("异常 -----", context.getString(R.string.ss));
                    Toast.makeText(context, R.string.ee, Toast.LENGTH_SHORT).show();
                } else {// 查询商品详情成功，刷新界面
                    Toast.makeText(context, "延长收货提交成功", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }

            }

            ;

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;
        }.execute(order_code);

    }

    /******
     * 提醒发货
     *
     * @param order_code
     */
    private void remainOrdershop(String order_code) {
        new SAsyncTask<String, Void, RemainShipInfo>(this, null, R.string.wait) {
            @Override
            protected RemainShipInfo doInBackground(FragmentActivity context, String... params) throws Exception {
                return ComModel.urgesuppShipments(context, params[0]);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, RemainShipInfo info, Exception e) {

                if (e != null) {// 查询异常
                    LogYiFu.e("异常 -----", context.getString(R.string.ss));
                    Toast.makeText(context, R.string.fh, Toast.LENGTH_SHORT).show();
                } else {// 提醒发货成功
                    if (info != null && "1".equals(info.getStatus()) && 1 == info.getFalg()) {
                        Toast.makeText(context, "提醒发货成功", Toast.LENGTH_SHORT).show();
                    } else if (info != null && "1".equals(info.getStatus()) && 2 == info.getFalg()) {
                        Toast.makeText(context, "亲，提醒太频繁了，不要那么着急嘛", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            ;

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;
        }.execute(order_code);

    }

    // 弹出支付密码对话框
    private void affirmOrder(View v, final Order order) {
        // WalletDialog dlgDialog = new WalletDialog(this, R.style.DialogStyle);
        // dlgDialog.show();
        // dlgDialog.listener = new WalletDialog.OnCallBackPayListener() {
        //
        // @Override
        // public void selectPwd(String pwd) {
        // affirmOrder(order, pwd);
        //
        // }
        // };

        // 检查是否设置了支付密码

        new SAsyncTask<Void, Void, CheckPwdInfo>(this, v, R.string.wait) {

            @Override
            protected CheckPwdInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel.checkPWD(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, CheckPwdInfo result, Exception e) {
                super.onPostExecute(context, result, e);

                if (null == e) {
                    if (result != null && "1".equals(result.getStatus()) && "1".equals(result.getFlag())) {

                        // 未设置支付密码
                        customDialog();

                    } else if (result != null && "1".equals(result.getStatus()) && "2".equals(result.getFlag())) {
                        // 已设置了支付密码

                        customDialog = new PayPasswordCustomDialog(context, R.style.mystyle, R.layout.pay_customdialog,
                                "确认收货", null);

                        inputDialogListener = new InputDialogListener() {

                            @Override
                            public void onOK(String pwd) {
                                // 给密码文本框设置密码
                                // ToastUtil.showLongText(context, "你的密码是：" +
                                // text);
                                affirmOrder(order, pwd);
                            }

                            @Override
                            public void onCancel() {
                                // TODO Auto-generated method stub

                            }

                        };
                        customDialog.setListener(inputDialogListener);
                        customDialog.show();

                    } else {
                        ToastUtil.showLongText(context, "糟糕，出错了~~~");
                    }
                }
            }

        }.execute((Void[]) null);

        // TextView tvAccount = (TextView) customDialog
        // .findViewById(R.id.tv_account);
        // tvAccount.setText(totalAccount + "元");
    }

    // 确认收货
    private void affirmOrder(final Order order, final String pwd) {
        new SAsyncTask<Void, Void, ReturnInfo>(this, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                // TODO Auto-generated method stub
                return ComModel2.affirmOrder(context, order.getOrder_code(), pwd, 0);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result != null && "1".equals(result.getStatus())) {
                        ToastUtil.showShortText(context, result.getMessage());
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        ToastUtil.showShortText(context, "糟糕，出错了~~~");
                    }
                }
            }

        }.execute();
    }

    /****
     * 取消订单
     *
     * @param order_code
     */

    private void escOrder(String order_code, final String reason) {

        new SAsyncTask<String, Void, ReturnInfo>(OrderDetailsActivity.this, null, R.string.wait) {
            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                return ComModel.escOrder(context, YCache.getCacheToken(context), params[0], reason);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo r, Exception e) {

                if (e != null) {// 查询异常
                    LogYiFu.e("异常 -----", context.getString(R.string.ss));//
                    Toast.makeText(context, "取消订单失败", Toast.LENGTH_SHORT).show();
                } else {// 查询商品详情成功，刷新界面
                    Toast.makeText(context, "取消订单成功", Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
                    setResult(RESULT_OK);
//                    if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
//                        // 获取金币金券相关数据
//                        GetJinBiJinQuanUtils.getJinBi(context);
//                        GetJinBiJinQuanUtils.getJinQuan(context);
//                    }
                    finish();
                }

            }

            ;

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;
        }.execute(order_code);
    }

    private List<Order> listChecked = new ArrayList<Order>();

    private void intentTOPayment(String order_code, double price, boolean isMulti) {
        Intent intent = new Intent(this, OrderPaymentActivity.class);
        if (order.getShop_from() == 3) {
            intent.putExtra("signShopDetail", "SignShopDetail");
            intent.putExtra("signType", order.getSignType());
        } else if (order.getShop_from() == 4 || order.getShop_from() == 6) {
            intent.putExtra("isDuobao", true);
            intent.putExtra("signType", order.getSignType());
        }
        intent.putExtra("single", "single");
        intent.putExtra("order_code", order_code);
        intent.putExtra("totlaAccount", price);
        intent.putExtra("isMulti", isMulti);
        Bundle bundle = new Bundle();
        bundle.putSerializable("listOrder", (Serializable) listChecked);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /****
     * 删除订单
     *
     * @param order_code
     */
    private void returnShop(String order_code, final int type) {
        new SAsyncTask<String, Void, ReturnInfo>(this, null, R.string.wait) {
            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                return ComModel.returnShop(context, YCache.getCacheToken(context), params[0]);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo r, Exception e) {

                if (e != null) {// 查询异常
                    LogYiFu.e("异常 -----", context.getString(R.string.ss));
                    Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
                } else {// 查询商品详情成功，刷新界面
                    Toast.makeText(context, "删除订单成功", Toast.LENGTH_SHORT).show();

                    Intent intent = getIntent();
                    if (type == 1) {
                        intent.putExtra("index", 4);
                    } else if (type == 2) {
                        intent.putExtra("index", 0);
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }

            ;

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;
        }.execute(order_code);

    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == 1001) { // 订单详情
            setResult(1);
        } else if (arg0 == 1002 && arg1 == 102) { // 跳转到购物车碎片中去
            CommonUtils.finishActivity(MainMenuActivity.instances);

            Intent intent = new Intent(this, MainMenuActivity.class);
            intent.putExtra("index", 3);
            startActivity(intent);
            finish();
        }
    }

    private void customDialog() {
        AlertDialog.Builder builder = new Builder(this);
        // 自定义一个布局文件
        View view = View.inflate(this, R.layout.payback_esc_apply_dialog, null);
        TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
        tv_des.setText("您还没有设置支付密码，立即去设置？");

        Button ok = (Button) view.findViewById(R.id.ok);
        ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
        Button cancel = (Button) view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 把这个对话框取消掉
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailsActivity.this, SetMyPayPassActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BuyVipSucEvent(MessageEvent messageEvent) {
        if (messageEvent.getEventBuyVipSucVipType() == 8) {

            HashMap<String, String> map = new HashMap<>();
            map.put("order_code", order.getOrder_code());
            YConn.httpPost(context, YUrl.QUERY_DJK_DETAIL, map, new HttpListener<DJKdetail>() {
                @Override
                public void onSuccess(DJKdetail djKDetail) {
                    if (djKDetail.getIsDeliver() == 1) {//此判断订单已经发货

                        tv_order_status.setText("待发货");
                        rel_pre_diliver.setVisibility(View.VISIBLE);

                    } else {
                        DialogUtils.showNewUserFirstOrderDialog(instance, order, OrderInfoFragment.send_num, 0);//已经买了发货卡就不需要会员的判断了

                    }

                }

                @Override
                public void onError() {

                }
            });


        }
    }


}
