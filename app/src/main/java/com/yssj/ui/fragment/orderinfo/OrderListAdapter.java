package com.yssj.ui.fragment.orderinfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.PayPasswordCustomDialog;
import com.yssj.custom.view.PayPasswordCustomDialog.InputDialogListener;
import com.yssj.entity.CheckPwdInfo;
import com.yssj.entity.Choujiang20Data;
import com.yssj.entity.Order;
import com.yssj.entity.OrderShop;
import com.yssj.entity.RemainShipInfo;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.OneBuyChouJiangActivity;
import com.yssj.ui.activity.OneBuyGroupsDetailsActivity;
import com.yssj.ui.activity.SignDrawalLimitActivity;
import com.yssj.ui.activity.VipShareGroupsDetailsActivity;
import com.yssj.ui.activity.infos.AppendEvaluateOrderNewActivity;
import com.yssj.ui.activity.infos.EvaluateOrderNewActivity;
import com.yssj.ui.activity.infos.LogisticsInfoActivity;
import com.yssj.ui.activity.infos.SetMyPayPassActivity;
import com.yssj.ui.activity.shopdetails.OrderPaymentActivity;
import com.yssj.ui.activity.shopdetails.ShaiDanActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.PayErrorDialog;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.GetJinBiJinQuanUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.SingleChoicePopupWindow;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YCache;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 */
public class OrderListAdapter extends BaseAdapter {
    private List<Order> listData;
    private Context context;
    private View parentView;
    private LayoutInflater inflater;
    protected AlertDialog dialog;
    protected final int DIALOG_CANCLE = 1;// 取消订单
    protected final int DIALOG_CONFIRM = 2;// 确认收货
    protected final int DIALOG_EXTEND = 3;// 延长收货
    protected final int DIALOG_DELETE = 4;// 延长收货
    private int index = 0;

    private OrderInfoFragment mFragment;

    private List<String> cancleOrderDatas = new ArrayList<String>(); // 取消订单
    // 原因数据
    private List<String> payBackDatas = new ArrayList<String>(); // 退款数据
    private int status;

    public static boolean sqfhClickEd = false;

    private void getCancleOrderDatas() {
        cancleOrderDatas.add("卖家缺货");
        cancleOrderDatas.add("信息填写错误重新拍");
        cancleOrderDatas.add("我不想买了");
        cancleOrderDatas.add("同城见面交易");
        cancleOrderDatas.add("拍错了");
        cancleOrderDatas.add("其他原因");
    }

    private void getpayBackDatas() {
        payBackDatas.add("卖家缺货");
        payBackDatas.add("信息填写错误");
        payBackDatas.add("我不想买了");
        payBackDatas.add("同城见面交易");
        payBackDatas.add("拍错了");
        payBackDatas.add("其他原因");
    }

    public OrderListAdapter(Context context, List<Order> listData, OrderInfoFragment mFragment, View parentView) {
        this.context = context;
        this.listData = listData;
        this.inflater = LayoutInflater.from(context);
        this.parentView = parentView;
        this.mFragment = mFragment;
    }

    public List<Order> getData() {
        return listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(context, R.layout.order_item, null);

            holder = new ViewHolder();
            holder.tv_shop_name = convertView.findViewById(R.id.tv_shop_name);
            holder.tv_status = convertView.findViewById(R.id.tv_status);
            holder.tv_sum = convertView.findViewById(R.id.tv_sum);
            holder.tv_zprice = convertView.findViewById(R.id.tv_zprice);
            holder.tv_one_buy_tuan = convertView.findViewById(R.id.tv_one_buy_tuan);
            holder.container_item = convertView.findViewById(R.id.container_item);// 加载子布局listview

            holder.lay1 = convertView.findViewById(R.id.lay1);// 待付款
            holder.btn_Contact_seller = convertView.findViewById(R.id.btn_contact_seller);
            holder.btn_Cancel_Order = convertView.findViewById(R.id.btn_cancel_order);
            holder.btn_Payment = convertView.findViewById(R.id.btn_payment);
            holder.tv_re_time = convertView.findViewById(R.id.tv_re_time);

            holder.lay2 = convertView.findViewById(R.id.lay2);// 待发货
            holder.btn_Remind_shipments = convertView.findViewById(R.id.btn_remind_shipments);
            holder.btn_payback = convertView.findViewById(R.id.btn_payback);

            holder.lay3 = convertView.findViewById(R.id.lay3);// 待收货
            holder.btn_Extend_Receipt = convertView.findViewById(R.id.btn_extend_receipt);
            holder.btn_See_Logistics = convertView.findViewById(R.id.btn_see_logistics);
            holder.btn_Confirm_receipt = convertView.findViewById(R.id.btn_confirm_receipt);

            holder.btn_mpk = convertView.findViewById(R.id.btn_mpk);
            holder.btn_delete_order = convertView.findViewById(R.id.btn_delete_order);
            holder.lay_mpt_btn = convertView.findViewById(R.id.lay_mpt_btn);

            holder.lay4 = convertView.findViewById(R.id.lay4);// 待评价
            holder.btn_Delete_Orders = convertView.findViewById(R.id.btn_delete_orders);
            holder.btn_Evaluation_Order = convertView.findViewById(R.id.btn_evaluation_order);

            holder.lay5 = convertView.findViewById(R.id.lay5);

            holder.btn_append_evaluate = convertView.findViewById(R.id.btn_append_evaluate); // 追加评论
            holder.btn_delete_orders5 = convertView.findViewById(R.id.btn_delete_orders5); // 删除订单

            holder.lay6 = convertView.findViewById(R.id.lay6);
            holder.btn_deleteorders = convertView.findViewById(R.id.btn_deleteorders);

            holder.lay7 = convertView.findViewById(R.id.lay7);
            holder.btn_confirm_receipt7 = convertView.findViewById(R.id.btn_confirm_receipt7);
            holder.btn_see_logistics7 = convertView.findViewById(R.id.btn_see_logistics7);
            holder.lay9 = convertView.findViewById(R.id.lay9);
            holder.vLine = convertView.findViewById(R.id.view_line);

            holder.lay10 = convertView.findViewById(R.id.lay10); //中奖订单24小时后专用
            holder.tv_close_order_24 = convertView.findViewById(R.id.tv_close_order_24);
            holder.tv_govip_24 = convertView.findViewById(R.id.tv_govip_kefu_24);
            holder.tv_sqfh_kefu_24 = convertView.findViewById(R.id.tv_sqfh_kefu_24);
            holder.tv_lianxi_kefu_24 = convertView.findViewById(R.id.tv_lianxi_kefu_24);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        final Order order = listData.get(position);
        if (position != 0) {
            holder.vLine.setVisibility(View.VISIBLE);
        } else {
            holder.vLine.setVisibility(View.GONE);
        }

        holder.tv_shop_name.setText(order.getOrder_code());// 店铺名称
        status = Integer.parseInt(order.getStatus().toString()); // 订单状态:1待付款2代发货3待收货4待评价6已完结7延长收货9取消订单

        // /1待付款2代发货3待收货4待评价5退款中6已完结7延长收货9取消订单
        setVisibility(holder, status, order, position);
        if (order.getShop_from() == 4 || order.getShop_from() == 6) {
//			holder.tv_sum.setText("共1件商品");
            int num = order.getShop_num();
            holder.tv_sum.setText("共" + num + "件商品");
        } else {
            int num = order.getShop_num();
            holder.tv_sum.setText("共" + num + "件商品");
        }

        List<OrderShop> list = order.getList();

        holder.container_item.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(context);

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.order_item_list, null);
        if (list != null) {

            // OrderListSonAdapter adapter = new OrderListSonAdapter(context,
            // list, false);
            // listView.setAdapter(adapter);
            // listView.setOnItemClickListener(new OnItemClickListener() {
            // @Override
            // public void onItemClick(AdapterView<?> arg0, View arg1,
            // int arg2, long arg3) {
            // if (order != null) {
            // Intent intent = new Intent(context,
            // OrderDetailsActivity.class);
            // Bundle bundle = new Bundle();
            // bundle.putSerializable("order", order);
            // intent.putExtras(bundle);
            // context.startActivity(intent);
            // }
            //
            // }
            // });
            double itemAccount = addView(list, holder.container_item, inflater, order);

            // holder.container_item.addView(layout);
            String totalAccount = new java.text.DecimalFormat("#0.0").format(itemAccount);
//			if (order.getShop_from() == 1) {
//				holder.tv_zprice.setText("实付 : ¥" + order.getOrder_price() + "(含运费¥" + order.getPostage() + ")");
//			} else {
//				holder.tv_zprice.setText("实付 : ¥" + order.getOrder_price());
//			}

            if (order.getShop_from() == 1) {
                holder.tv_zprice.setText("实付 : ¥" + new DecimalFormat("#0.00").format(Double.parseDouble(order.getOrder_price() + "")) + "(含运费¥" + order.getPostage() + ")");
            } else {
                holder.tv_zprice.setText("实付 : ¥" + new DecimalFormat("#0.00").format(Double.parseDouble(order.getPay_money() + "")));
            }


            // holder.tv_zprice.setText("实付 :¥" + order.getOrder_price());

        }

        return convertView;
    }

    private double addView(List<OrderShop> list, LinearLayout container, LayoutInflater inflater, final Order order) {
        container.removeAllViews();
        double itemAccount = 0;
        LogYiFu.e("判断是否什么值", (order.getShop_from() == 1) + "");

        Boolean b = 1 == order.getShop_from();
        Boolean b2 = 4 == order.getShop_from();


//        if(10 == order.getShop_from()){ //1元购订单状态固定是：订单已过期，按钮只保留：删除订单
//            status = 10;
//        }

        if (1 == order.getShop_from()) {
            View view = inflater.inflate(R.layout.listview_orderlist_son, null);
            ImageView img_product = view.findViewById(R.id.img_product1);
            TextView tv_product_name = view.findViewById(R.id.tv_product_name);
            TextView tv_shop_num = view.findViewById(R.id.tv_shop_num);
            TextView tv_price = view.findViewById(R.id.tv_price);
            TextView tvColor = view.findViewById(R.id.tv_product_color);
            TextView tvSize = view.findViewById(R.id.tv_product_size);
            TextView tvStatus = view.findViewById(R.id.tv_status);
            TextView meal = view.findViewById(R.id.meal);
            TextView tv_text_yuanjia = view.findViewById(R.id.tv_text_yuanjia);

            meal.setVisibility(View.VISIBLE);
            tvColor.setVisibility(View.GONE);
            tvSize.setVisibility(View.GONE);
            String pic = order.getOrder_pic();
            img_product.setTag(pic);

            if (list.size() == 1) {
                meal.setText("超值单品");
            } else {
                meal.setText("超值套餐");
            }
            if (!TextUtils.isEmpty(pic)) {
//				SetImageLoader.initImageLoader(context, img_product, pic, "");
                PicassoUtils.initImage(context, pic, img_product);

            }

            String shop_name = order.getOrder_name();
//			System.out.println("这里的名字="+shop_name);


            if (!TextUtils.isEmpty(shop_name)) {
                tv_product_name.setText(shop_name);
            }
            String price = new java.text.DecimalFormat("#0.0").format(order.getOrder_price() - order.getPostage());

            tv_price.setText("¥" + price);
            int num = order.getShop_num();
            tv_shop_num.setText("x" + "1");

            int orderShopStatus = list.get(0).getStatus();
            int orderShopChange = list.get(0).getChange();
            if (orderShopStatus == 0) {
                switch (status) {
                    case 1:
                        if (order.getLast_time().before(new Date())) {
                            tvStatus.setText("已过期");
                        } else {
                            if (order.getPay_status() != 1) {
                                tvStatus.setText("待付款");
                            } else {
                                tvStatus.setText("支付中");
                            }
                        }
                        break;
                    case 2:

                        tvStatus.setText("待发货");

                        break;
                    case 3:
                        tvStatus.setText("待收货");
                        break;
                    case 4:
                        tvStatus.setText("待评价");
                        break;
                    case 5:
                        tvStatus.setText("已评价");
                        break;
                    case 6:
                        tvStatus.setText("交易成功");
                        break;
                    case 7:
                        tvStatus.setText("延长收货");
                        break;
                    case 9:

                        if (order.getShop_from() == 13) {
                            tvStatus.setText("订单关闭");
                        } else {
                            tvStatus.setText("取消订单");
                        }

                        break;
                    case 10:
                        tvStatus.setText("订单已过期");
                        break;

                    default:
                        break;
                }
            } else if (orderShopStatus == 4) {
                tvStatus.setText("交易成功");
            } else {
                StringBuffer sb = new StringBuffer();
                switch (orderShopChange) {
                    case 1:
                        sb.append("换货");
                        break;
                    case 2:
                        sb.append("退货");
                        break;
                    case 3:
                        sb.append("退款");
                        break;
                }
                switch (orderShopStatus) {
                    case 1:
                        sb.append("处理中");
                        break;
                    case 2:
                        sb.append("被拒绝");
                        break;
                    case 3:
                        sb.append("已成功");
                        break;
                    case 4:
                        sb.append("已取消");
                        break;

                    default:
                        break;
                }

                tvStatus.setText(sb.toString());// 显示换货状态，通过商品的status
                // 和change字段来判断s
            }
            if (list.get(0).getOriginal_price() >= 0) {
                tv_text_yuanjia.setVisibility(View.VISIBLE);
                tv_price.setText("¥" + list.get(0).getOriginal_price());
            }


            container.addView(view);

        } else if (4 == order.getShop_from() || 6 == order.getShop_from()) {// 夺宝订单
            View view = inflater.inflate(R.layout.listview_orderlist_son, null);
            ImageView img_product = view.findViewById(R.id.img_product1);
            TextView tv_product_name = view.findViewById(R.id.tv_product_name);
            TextView tv_shop_num = view.findViewById(R.id.tv_shop_num);
            TextView tv_price = view.findViewById(R.id.tv_price);
            TextView tvColor = view.findViewById(R.id.tv_product_color);
            TextView tvSize = view.findViewById(R.id.tv_product_size);
            TextView tvStatus = view.findViewById(R.id.tv_status);
            TextView meal = view.findViewById(R.id.meal);
            meal.setVisibility(View.GONE);
            tvColor.setVisibility(View.GONE);
            tvSize.setVisibility(View.GONE);
            // String pic = order.getOrder_pic();

            String pic = order.getBak().substring(1, 4) + "/" + order.getBak() + "/" + order.getOrder_pic();
            img_product.setTag(pic);

            // if (list.size() == 1) {
            // meal.setText("超值单品");
            // } else {
            // meal.setText("超值套餐");
            // }
            if (!TextUtils.isEmpty(pic)) {
//				SetImageLoader.initImageLoader(context, img_product, pic, "");
                PicassoUtils.initImage(context, pic, img_product);

            }
            String shop_name = order.getOrder_name();

            if (!TextUtils.isEmpty(shop_name)) {
                tv_product_name.setText(shop_name);
            }
            String price = new java.text.DecimalFormat("#0.0").format(order.getOrder_price() - order.getPostage());

            tv_price.setText("¥" + price);
            int num = 1;
            try {
                num = order.getShop_num();
            } catch (Exception e) {
            }
//			tv_shop_num.setText("x" + "1");
            tv_shop_num.setText("x" + num);

            // int orderShopStatus = list.get(0).getStatus();
            // int orderShopChange = list.get(0).getChange();
            int orderShopStatus = Integer.parseInt(order.getIssue_status());

            switch (orderShopStatus) {
                case 0:

                    tvStatus.setText("参与中");
                    break;
                case 1:

                    tvStatus.setText("退款");
                    break;
                case 2:

                    tvStatus.setText("退款");
                    break;
                case 3:

                    tvStatus.setText("中奖");
                    break;
                case 4:

                    tvStatus.setText("未中奖");
                    break;
                default:
                    break;
            }

            // int orderShopChange = order.getChange();
            // if (orderShopStatus == 0) {
            // switch (status) {
            // case 1:
            // if (order.getLast_time().before(new Date())) {
            // tvStatus.setText("已过期");
            // } else {
            // if (order.getPay_status() != 1) {
            // tvStatus.setText("待付款");
            // } else {
            // tvStatus.setText("支付中");
            // }
            // }
            // break;
            // case 2:
            //
            // tvStatus.setText("待发货");
            //
            // break;
            // case 3:
            // tvStatus.setText("待收货");
            // break;
            // case 4:
            // tvStatus.setText("待评价");
            // break;
            // case 5:
            // tvStatus.setText("已评价");
            // break;
            // case 6:
            // tvStatus.setText("交易成功");
            // break;
            // case 7:
            // tvStatus.setText("延长收货");
            // break;
            // case 9:
            // tvStatus.setText("取消订单");
            // break;
            //
            // default:
            // break;
            // }
            // } else if (orderShopStatus == 4) {
            // tvStatus.setText("交易成功");
            // } else {
            // StringBuffer sb = new StringBuffer();
            // switch (orderShopChange) {
            // case 1:
            // sb.append("换货");
            // break;
            // case 2:
            // sb.append("退货");
            // break;
            // case 3:
            // sb.append("退款");
            // break;
            // }
            // switch (orderShopStatus) {
            // case 1:
            // sb.append("处理中");
            // break;
            // case 2:
            // sb.append("被拒绝");
            // break;
            // case 3:
            // sb.append("已成功");
            // break;
            // case 4:
            // sb.append("已取消");
            // break;
            //
            // default:
            // break;
            // }
            //
            // tvStatus.setText(sb.toString());// 显示换货状态，通过商品的status
            // // 和change字段来判断s

            container.addView(view);
        } else {
            for (OrderShop orderShop : list) {
                View view = inflater.inflate(R.layout.listview_orderlist_son, null);
                ImageView iv_group_symbol = view.findViewById(R.id.iv_group_symbol);
                if (order.getShop_from() == 7) {//拼团商品
                    iv_group_symbol.setVisibility(View.VISIBLE);
                } else {
                    iv_group_symbol.setVisibility(View.GONE);
                }
                ImageView img_product = view.findViewById(R.id.img_product1);
                TextView tv_product_name = view.findViewById(R.id.tv_product_name);
                TextView tv_shop_num = view.findViewById(R.id.tv_shop_num);
                TextView tv_price = view.findViewById(R.id.tv_price);
                TextView tvColor = view.findViewById(R.id.tv_product_color);
                TextView tvSize = view.findViewById(R.id.tv_product_size);
                TextView tvStatus = view.findViewById(R.id.tv_status);
                TextView tv_text_yuanjia = view.findViewById(R.id.tv_text_yuanjia);
                TextView tv_zhongjiang_str = view.findViewById(R.id.tv_zhongjiang_str);

                TextView tv_yufahuo = view.findViewById(R.id.tv_yufahuo);


                if (order.getAdvance_sale_days() > 0) {
                    tv_yufahuo.setText("发货时间：付款后" + order.getAdvance_sale_days() + "天内");
                    tv_yufahuo.setVisibility(View.VISIBLE);
                } else {
                    tv_yufahuo.setVisibility(View.GONE);
                }


                // TextView tvOriginalPrice = (TextView) view
                // .findViewById(R.id.tv_original_price);
                TextView meal = view.findViewById(R.id.meal);
                meal.setVisibility(View.GONE);
                String pic = orderShop.getShop_code().substring(1, 4) + "/" + orderShop.getShop_code() + "/"
                        + orderShop.getShop_pic();
                img_product.setTag(pic);
                if (!TextUtils.isEmpty(pic)) {
//					SetImageLoader.initImageLoader(context, img_product, pic, "");
                    PicassoUtils.initImage(context, pic, img_product);
                }
                String shop_name = orderShop.getShop_name(0);
//				String shop_name = order.getOrder_name();  //这个名称不对  别挖坑可好？
//				System.out.println("估计就这了="+shop_name);
                if (!TextUtils.isEmpty(shop_name)) {
                    tv_product_name.setText(shop_name);
                }
                tvColor.setVisibility(View.VISIBLE);
                tvSize.setVisibility(View.VISIBLE);


                if (null == orderShop.getColor()) {
                    tvColor.setVisibility(View.GONE);
                }

                if (null == orderShop.getSize()) {
                    tvSize.setVisibility(View.GONE);
                }

                tvColor.setText("颜色 : " + orderShop.getColor());
                tvSize.setText("尺寸 : " + orderShop.getSize());


                String price = new java.text.DecimalFormat("#0.0").format(orderShop.getShop_price());
                tv_price.setText("¥" + price);
                int num = orderShop.getShop_num();
                tv_shop_num.setText("x" + num);


                TextView tv_dikou = view.findViewById(R.id.tv_dikou);//抵扣说明（返还说明）
                final ImageView iv_wenhao = view.findViewById(R.id.iv_wenhao); //问号

                if (order.getShop_from() == 11) {

//                    if (status == 13 || status == 14) {
//                        if (order.getPay_status() != 0) { //已经付款了的才能显示,（疯抢已经不需要付款了）
//                            tv_dikou.setText("拼团疯抢费已返还");
//                            tv_dikou.setVisibility(View.VISIBLE);
//                            iv_wenhao.setVisibility(View.VISIBLE);
//                        }
//
//                    }

                } else if (order.getShop_from() == 10 && order.getWhether_prize() == 1) {//1元购订单
                    if (order.getPay_status() != 0 && status != 12) { //已经付款了的才能显示
                        tv_dikou.setText("拼团疯抢费已返还");
                        tv_dikou.setVisibility(View.VISIBLE);
                        iv_wenhao.setVisibility(View.VISIBLE);
                    }

                } else {//其他订单先看抵扣金额是否大于0，大于0就显示

                    if (order.getOne_deductible() > 0) {

                        tv_dikou.setText("已抵扣" + order.getOne_deductible() + "元");

                        tv_dikou.setVisibility(View.VISIBLE);
                        iv_wenhao.setVisibility(View.VISIBLE);
                    } else {
                        tv_dikou.setVisibility(View.GONE);
                        iv_wenhao.setVisibility(View.GONE);
                    }


                }
                tv_dikou.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iv_wenhao.performClick();
                    }
                });

                iv_wenhao.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (order.getShop_from() == 11 || order.getShop_from() == 10) {
                            DialogUtils.getDiKouDialogNew(context, "疯抢返还说明", false, false);
                        } else {
                            DialogUtils.getDiKouDialogNewOrder(context);//按商品详情显示

                        }

                    }
                });


                itemAccount += orderShop.getShop_price() * orderShop.getShop_num();

                int orderShopStatus = orderShop.getStatus();
                int orderShopChange = orderShop.getChange();

                if (status == 10) { //1元购订单统一显示“订单已过期”
                    orderShopStatus = 0;
                }

                if (orderShopStatus == 0) {
                    switch (status) {
                        case 1:
                            if (order.getLast_time().before(new Date())) {
                                tvStatus.setText("已过期");
                            } else {
                                if (order.getPay_status() == 1) {
                                    tvStatus.setText("支付中");
                                } else {
                                    tvStatus.setText("待付款");
                                }

                            }

                            if (order.getShop_from() == 11) {
                                tvStatus.setText("拼团中");

                            }


                            break;
                        case 2:
                            tvStatus.setText("待发货");


                            if (order.getWhether_prize() == 2 || order.getNew_free() == 1) {
                                tvStatus.setTextColor(Color.parseColor("#FF3F8B"));
                                tvStatus.setText("申请发货中");
                            }

                            if (order.getWhether_prize() == 3) {
                                tvStatus.setText("待发货");
                            }
                            if (order.getShop_from() == 11 && order.getIs_roll() != 3) {
                                tvStatus.setText("拼团中");

                            }
                            break;
                        case 3:
                            tvStatus.setText("待收货");
                            break;
                        case 4:
                            tvStatus.setText("待评价");
                            break;
                        case 5:
                            tvStatus.setText("已评价");
                            break;
                        case 6:
                            tvStatus.setText("交易成功");
                            break;
                        case 7:
                            tvStatus.setText("延长收货");
                            break;
                        case 9:
                            if (order.getShop_from() == 13) {
                                tvStatus.setText("订单关闭");
                            } else {
                                tvStatus.setText("取消订单");
                            }
                            break;
                        case 10:
                            tvStatus.setText("订单已过期");
                            break;

                        // 11-14拼团一元购相关

                        case 11:
                            tvStatus.setText("拼团中");

                            break;

                        case 12:
                            tvStatus.setText("待免费领");

                            break;

                        case 13:
                            tvStatus.setText("拼团失败");

                            break;

                        case 14:

                            tvStatus.setText("免费领未点中");

                            break;
                        case 15://申请关闭拼团-已过96小时

                            tvStatus.setText("拼团中");

                            break;

                        case 17:
                            tvStatus.setText("申请发货中");

                            break;

                        case 21://会员分享免费领

                            tvStatus.setText("分享中");

                            break;

                        default:
                            break;
                    }
                } else if (orderShopStatus == 4) {
                    tvStatus.setText("交易成功");
                } else {
                    StringBuffer sb = new StringBuffer();
                    switch (orderShopChange) {
                        case 1:
                            sb.append("换货");
                            break;
                        case 2:
                            sb.append("退货");
                            break;
                        case 3:
                            sb.append("退款");
                            break;
                    }
                    switch (orderShopStatus) {
                        case 1:
                            sb.append("处理中");
                            break;
                        case 2:
                            sb.append("被拒绝");
                            break;
                        case 3:
                            sb.append("已成功");
                            break;
                        case 4:
                            sb.append("已取消");
                            break;

                        default:
                            break;
                    }

                    tvStatus.setText(sb.toString());// 显示换货状态，通过商品的status
                    // 和change字段来判断s
                }
                if (orderShop.getOriginal_price() >= 0) {
                    tv_text_yuanjia.setVisibility(View.VISIBLE);
                    tv_price.setText("¥" + orderShop.getOriginal_price());
                }


                if (order.getNew_free() == 13 || order.getNew_free() == 3 || order.getNew_free() == 14|| order.getNew_free() == 1302) {
                    tv_zhongjiang_str.setText(order.getPay_money() + "元已全额返还至钱包的购物余额，本单免费发货");
                    tv_zhongjiang_str.setVisibility(View.VISIBLE);
                } else {
                    if (order.getStatus().equals("2") && order.getWhether_prize() == 2 && order.getNew_free() == 1) {
                        tv_zhongjiang_str.setText("会员免费领订单，请点下方按钮申请发货。");
                        tv_zhongjiang_str.setVisibility(View.VISIBLE);
                    }
                }


                //新用户预中奖首单 24小后
//                if (order.getStatus().equals("17") && order.getWhether_prize() == 2 && order.getNew_free() == 1) {
//                    tv_zhongjiang_str.setText("会员免费领活动订单，请接通客服申请发货。");
//                    tv_zhongjiang_str.setVisibility(View.VISIBLE);
//
//                }


                if (order.getShop_from() == 0 && order.getWhether_prize() == 9) {
                    if (order.getNew_free() == 11) {
                        tv_zhongjiang_str.setText("使用3张免拼卡");
                        tv_zhongjiang_str.setVisibility(View.VISIBLE);
                    }

                    if (order.getNew_free() == 12) {
                        tv_zhongjiang_str.setText("使用3张发货卡");
                        tv_zhongjiang_str.setVisibility(View.VISIBLE);
                    }

                }


//                String vipName = "钻石卡";
//
//                try {
//                    vipName = MineIfoFragment.vipData.getVip_name();
//                } catch (Exception e) {
//
//                }

                //会员预中奖
//                   if ((order.getStatus().equals("17") || order.getStatus().equals("2")) && order.getWhether_prize() == 2 && order.getNew_free() != 1) {
//                    tv_zhongjiang_str.setText("商品超出" + vipName + "会员可免费领的价格区间，请联系客服申请发货。");
//                    tv_zhongjiang_str.setVisibility(View.VISIBLE);
//
//                }


                container.addView(view);


            }
        }
        return itemAccount;
    }

    class ViewHolder {
        TextView tv_close_order_24;
        TextView tv_govip_24;
        TextView tv_sqfh_kefu_24;
        TextView tv_lianxi_kefu_24;


        TextView tv_shop_name, tv_status, tv_sum, tv_zprice, tv_one_buy_tuan, tv_re_time;
        Button btn_Contact_seller, btn_Cancel_Order, btn_Payment;// 待付款状态
        Button btn_Remind_shipments;// 提醒发货
        Button btn_payback; // 退款
        Button btn_Extend_Receipt, btn_See_Logistics, btn_Confirm_receipt, btn_delete_order, btn_mpk;// 待收货状态
        Button btn_Delete_Orders, btn_Evaluation_Order;
        LinearLayout container_item, lay1, lay2, lay3, lay4, lay5, lay6, lay7, lay9, lay10, lay_mpt_btn;
        // Button btn_Confirm_receipt1, btnExtend_Receipt;
        Button btn_see_logistics7, btn_confirm_receipt7;
        Button btn_append_evaluate, btn_delete_orders5; // 追加评论、删除订单
        Button btn_deleteorders; // 删除订单
        View vLine;
    }

    /**
     * 自定义对话框 1，取消订单 2，确认收货，3 延长收货
     *
     * @param position
     */
    private void customDialog(int type, final String order_code, final int position) {
        AlertDialog.Builder builder = new Builder(context);
        // 自定义一个布局文件
        View view = View.inflate(context, R.layout.payback_esc_apply_dialog, null);
        TextView tv_des = view.findViewById(R.id.tv_des);

        tv_des.setText("是否取消申请换货，确认之后您将不能重新发起售后申请");

        Button ok = view.findViewById(R.id.ok);
        ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
        Button cancel = view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 把这个对话框取消掉
                dialog.dismiss();

            }
        });
        switch (type) { // 1，取消订单
            /*
             * case 1: tv_des.setText("是否确定取消订单"); ok.setOnClickListener(new
             * OnClickListener() {
             *
             * @Override public void onClick(View v) { escOrder(order_code);
             * dialog.dismiss(); freshAdapter(position); } }); break;
             */
            case 2: // 确认收货

                break;
            case 3: // 延长收货
                tv_des.setText("  确认延长收货时间吗？\n每笔订单只能延长一次哦");
                ok.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        extensionOrdershop(order_code);
                        dialog.dismiss();

                        // freshAdapter(position);

                    }
                });
                break;
            case 4: // 删除订单
                tv_des.setText("是否确定删除订单");
                ok.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        returnShop(order_code, position);
                        dialog.dismiss();
                        // freshAdapter(position);
                    }
                });
                break;

            default:
                break;
        }

        dialog = builder.create();
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }

    protected void cancle() {
        // TODO Auto-generated method stub

    }

    // /1待付款2待发货3待收货4待评价
    // 5已评论（可追加评论）6已完结7延长收货9取消订单
    private void setVisibility(final ViewHolder holder, int status, final Order order, final int position) {


//        if(10 == order.getShop_from()){ //1元购订单状态固定是：订单已过期，按钮只保留：删除订单
//            status = 10;
//        }


        holder.lay_mpt_btn.setVisibility(View.GONE);
        holder.tv_re_time.setVisibility(View.GONE);


        switch (status) {
            case 1:


                if (order.getShop_from() == 11) {

                    holder.lay1.setVisibility(View.GONE);
                    holder.lay2.setVisibility(View.GONE);
                    holder.lay3.setVisibility(View.GONE);
                    holder.lay4.setVisibility(View.GONE);

                    holder.lay5.setVisibility(View.GONE);
                    holder.lay6.setVisibility(View.GONE);
                    holder.lay7.setVisibility(View.GONE);
                    holder.lay9.setVisibility(View.GONE);

                    holder.tv_one_buy_tuan.setText("去付款");

                    return;
                }


                if (order.getPay_status() == 1) {
                    holder.tv_status.setText("支付中");

                    holder.lay1.setVisibility(View.GONE);
                    holder.lay2.setVisibility(View.VISIBLE);
                    holder.lay3.setVisibility(View.GONE);
                    holder.lay4.setVisibility(View.GONE);
                    holder.lay5.setVisibility(View.GONE);
                    holder.lay6.setVisibility(View.GONE);
                    holder.lay7.setVisibility(View.GONE);
                    holder.lay9.setVisibility(View.GONE);

                    holder.btn_Remind_shipments.setOnClickListener(new View.OnClickListener() {// 提醒发货(聊天系统)

                        @Override
                        public void onClick(View v) {
                            // 跳到聊天界面
                            // context.startActivity(new Intent(context,
                            // ChatActivity.class).putExtra("userId",
                            // order.getSupp_id() + ""));
                            Toast.makeText(context, "支付处理中", Toast.LENGTH_SHORT).show();
                        }
                    });
                    holder.btn_payback.setOnClickListener(new View.OnClickListener() { // 退款

                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "支付处理中", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    holder.tv_status.setText("待付款");
                    if (order.getLast_time().before(new Date())) {
                        holder.lay1.setVisibility(View.GONE);
                        holder.lay2.setVisibility(View.GONE);
                        holder.lay3.setVisibility(View.GONE);
                        holder.lay4.setVisibility(View.GONE);

                        holder.lay5.setVisibility(View.GONE);
                        holder.lay6.setVisibility(View.VISIBLE);
                        holder.lay7.setVisibility(View.GONE);
                        holder.lay9.setVisibility(View.GONE);
                    } else {

                        holder.lay1.setVisibility(View.VISIBLE);
                        holder.lay2.setVisibility(View.GONE);
                        holder.lay3.setVisibility(View.GONE);
                        holder.lay4.setVisibility(View.GONE);

                        holder.lay5.setVisibility(View.GONE);
                        holder.lay6.setVisibility(View.GONE);
                        holder.lay7.setVisibility(View.GONE);
                        holder.lay9.setVisibility(View.GONE);
                    }


                    holder.btn_deleteorders.setOnClickListener(new OnClickListener() { // 已过期订单，删除订单事件

                        @Override
                        public void onClick(View v) {
                            String order_code = order.getOrder_code();
                            customDialog(DIALOG_DELETE, order_code, position);

                        }
                    });

                    holder.btn_Contact_seller.setOnClickListener(new OnClickListener() { // 联系卖家

                        @Override
                        public void onClick(View v) {
                            if (order.getLast_time().before(new Date())) {
                                ToastUtil.showShortText(context, "订单已过期");
                                return;
                            }
//                            Intent intent = new Intent(context, ChatActivity.class);
//                            intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
//                            context.startActivity(intent);

                            WXminiAppUtil.jumpToWXmini(context);

                        }
                    });

                    holder.btn_Cancel_Order.setOnClickListener(new View.OnClickListener() {// 取消订单

                        @Override
                        public void onClick(View v) {
                            if (order.getLast_time().before(new Date())) {
                                ToastUtil.showShortText(context, "订单已过期");
                                return;
                            }

                            final String order_code = order.getOrder_code();
                            // customDialog(DIALOG_CANCLE, order_code,
                            // position);
                            //
                            cancleOrderDatas.clear();
                            getCancleOrderDatas();
                            // 自定义的弹出框-----------取消订单和退款使用
                            final SingleChoicePopupWindow menuWindow;
                            menuWindow = new SingleChoicePopupWindow(context, cancleOrderDatas);
                            menuWindow.setTitle("取消订单");

                            // 显示窗口
                            menuWindow.showAtLocation(parentView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

                            menuWindow.setOnOKButtonListener(new OnClickListener() {

                                public void onClick(View v) {
                                    menuWindow.dismiss();
                                    String selItem = menuWindow.getSelectItem();
                                    if (order.getShop_from() == 4 || 6 == order.getShop_from()) {
                                        escOrderDuo(order_code, selItem, position);
                                    } else
                                        escOrder(order_code, selItem, position);

                                }
                            });

                        }
                    });

                    holder.btn_Payment.setOnClickListener(new View.OnClickListener() {// 付款
                        // 通过我的钱包付款
                        @Override
                        public void onClick(View v) {

                            if (order.getLast_time().before(new Date())) {
                                ToastUtil.showShortText(context, "订单已过期");
                                return;
                            }
                            if (order.getPay_status() == 1) {
                                Toast.makeText(context, "支付处理中", Toast.LENGTH_SHORT).show();
                                return;
                            }

//                            long nowTimes = new Date().getTime();
//                            long issueEndTime = order.getIssue_endtime();
//                            if ((order.getShop_from() == 4 || 6 == order.getShop_from()) && issueEndTime != 0 && nowTimes >= issueEndTime) {
//                                ToastUtil.showShortText(context, "中奖号码已经揭晓！");
//                                return;
//                            }
                            final String order_code = order.getOrder_code();
                            goToPay(order);
                            // freshAdapter(position);
                        }
                    });
                }


                break;
            case 2:


                if (order.getShop_from() == 11 && order.getIs_roll() != 3) {

                    holder.lay1.setVisibility(View.GONE);
                    holder.lay2.setVisibility(View.GONE);
                    holder.lay3.setVisibility(View.GONE);
                    holder.lay4.setVisibility(View.GONE);

                    holder.lay5.setVisibility(View.GONE);
                    holder.lay6.setVisibility(View.GONE);
                    holder.lay7.setVisibility(View.GONE);
                    holder.lay9.setVisibility(View.GONE);

                    holder.tv_one_buy_tuan.setText("已付款");

                    return;
                }


                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.VISIBLE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);
                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);

                holder.tv_status.setText("待发货");
                holder.btn_Remind_shipments.setOnClickListener(new View.OnClickListener() {// 提醒发货(聊天系统)

                    @Override
                    public void onClick(View v) {
                        // 跳到聊天界面
                        // context.startActivity(new Intent(context,
                        // ChatActivity.class).putExtra("userId",
                        // order.getSupp_id() + ""));
                        remainOrdershop(order.getOrder_code());
                    }
                });
                holder.btn_payback.setOnClickListener(new View.OnClickListener() { // 退款

                    @Override
                    public void onClick(View v) {

                        payBackDatas.clear();

                        getpayBackDatas();
                        // 自定义的弹出框-----------取消订单和退款使用
                        final SingleChoicePopupWindow menuWindow;
                        menuWindow = new SingleChoicePopupWindow(context, payBackDatas);
                        menuWindow.setTitle("退款原因");

                        // 显示窗口
                        menuWindow.showAtLocation(parentView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

                        menuWindow.setOnOKButtonListener(new OnClickListener() {

                            public void onClick(View v) {
                                menuWindow.dismiss();
                                String selItem = menuWindow.getSelectItem();
                                returnShopPayBack(order.getOrder_code(), "3", selItem, position);

                            }
                        });

                    }
                });

                //中奖订单处理
                if ((order.getShop_from() == 10 || order.getShop_from() == 11 || order.getShop_from() == 13)
                        && (order.getWhether_prize() == 0 || order.getWhether_prize() == 2)) {

                    holder.lay1.setVisibility(View.GONE);
                    holder.lay2.setVisibility(View.GONE);
                    holder.lay3.setVisibility(View.GONE);
                    holder.lay4.setVisibility(View.GONE);

                    holder.lay5.setVisibility(View.GONE);
                    holder.lay6.setVisibility(View.GONE);
                    holder.lay7.setVisibility(View.GONE);
                    holder.lay9.setVisibility(View.GONE);
                    holder.tv_one_buy_tuan.setText("申请发货");

                    holder.tv_one_buy_tuan.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            ToastUtil.showShortText2("中奖订单申请发货！");
                            sqfhClickEd = true;
                            DialogUtils.showNewUserFirstOrderDialog(context, order, OrderInfoFragment.send_num, OrderInfoFragment.isVip);


                        }
                    });
                }


//                if (order.getNew_free() == 1) {
//                    holder.lay1.setVisibility(View.GONE);
//                    holder.lay2.setVisibility(View.GONE);
//                    holder.lay3.setVisibility(View.GONE);
//                    holder.lay4.setVisibility(View.GONE);
//
//                    holder.lay5.setVisibility(View.GONE);
//                    holder.lay6.setVisibility(View.GONE);
//                    holder.lay7.setVisibility(View.GONE);
//                    holder.lay9.setVisibility(View.GONE);
//                    holder.tv_one_buy_tuan.setText("申请发货");
//                    holder.tv_one_buy_tuan.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
////                            Intent shareIntent = new Intent(context, ShareGroupChatActivity.class);
////                            shareIntent.putExtra("isMeal", "1".equals(order.getIsTM()));
////                            Bundle bundle = new Bundle();
////                            bundle.putSerializable("order", order);
////                            shareIntent.putExtras(bundle);
////                            context.startActivity(shareIntent);
////                            ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//
//                            context.startActivity(new Intent(context, KeFuActivity.class));
//                            ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//
//                        }
//                    });
//                }

                if (order.getShop_from() == 13
                        && !StringUtils.isEmpty(OrderInfoFragment.current_date)
                        && order.getIs_freeDelivery() != 1
                        && OrderInfoFragment.dayEndTime - OrderInfoFragment.now > 1000
                        ) {

                    holder.tv_re_time.setText(OrderInfoFragment.freeLingTimeStr);


//                    final long[] reTime = {OrderInfoFragment.dayEndTime - OrderInfoFragment.now};
//                    holder.tv_re_time.setText("剩余" + DateUtil.FormatMilliseondToEndTime2(reTime[0]));
//
//                    new Timer().schedule(
//
//                            new TimerTask() {
//                                @Override
//                                public void run() {
//                                    ((Activity) context).runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            if (reTime[0] <= 0) {
//                                                holder.tv_re_time.setText("订单已过期");
//                                            } else {
//                                                reTime[0] -= 1000;
//                                                holder.tv_re_time.setText("剩余A " + DateUtil.FormatMilliseondToEndTime2(reTime[0]));
//                                            }
//
//                                        }
//                                    });
//
//                                }
//                            },
//
//                            0, 1000
//                    );
                    holder.tv_re_time.setVisibility(View.VISIBLE);
                }


                break;
            case 3:
                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.VISIBLE);
                holder.lay4.setVisibility(View.GONE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);
                holder.tv_status.setText("待收货");
                holder.btn_Extend_Receipt.setOnClickListener(new View.OnClickListener() {// 延长收货

                    @Override
                    public void onClick(View v) {
                        String order_code = order.getOrder_code();
                        customDialog(DIALOG_EXTEND, order_code, position);
                    }
                });

                holder.btn_See_Logistics.setOnClickListener(new View.OnClickListener() {// 查看物流(调用物流公司的接口)

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, LogisticsInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("order", order);
                        intent.putExtras(bundle);
                        OrderInfoFragment.isAdd = false;
                        context.startActivity(intent);
                    }
                });
                holder.btn_Confirm_receipt.setOnClickListener(new View.OnClickListener() {// 确认收货
                    // 根据订单

                    @Override
                    public void onClick(View v) {
                        final String order_code = order.getOrder_code();

                        checkMyWalletPayPass(v, order_code, position);

                    }
                });
                break;
            case 4:
                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.VISIBLE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);
                holder.btn_Evaluation_Order.setVisibility(View.VISIBLE);

                holder.tv_status.setText("待评价");// 交易成功
                holder.btn_Delete_Orders.setOnClickListener(new OnClickListener() {// 删除订单

                    @Override
                    public void onClick(View v) {
                        String order_code = order.getOrder_code();
                        customDialog(DIALOG_DELETE, order_code, position);

                    }
                });

                holder.btn_Evaluation_Order.setOnClickListener(new View.OnClickListener() {// 评价订单（按订单评价）

                    @Override
                    public void onClick(View v) {
                        boolean isIn = false;
                        if (order.getShop_from() == 4 || 6 == order.getShop_from()) {

                            Intent intent = new Intent(context, ShaiDanActivity.class);
                            intent.putExtra("in_code", order.getParticipation_code());
                            intent.putExtra("shop_code", order.getBak());
                            intent.putExtra("shop_name", order.getOrder_name());
                            intent.putExtra("issue_code", order.getIssue_code());
                            context.startActivity(intent);
                            return;
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

                            Intent intent = new Intent(context, EvaluateOrderNewActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("order", order);
                            intent.putExtras(bundle);
                            OrderInfoFragment.isAdd = false;
                            context.startActivity(intent);
                            // mFragment.finish();
                        }

                    }
                });
                break;
            case 5:// 已评论（可追加评论）
                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);

                holder.lay5.setVisibility(View.VISIBLE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);
                holder.tv_status.setText("已评价");

                if (order.getShop_from() == 4 || 6 == order.getShop_from()) {
                    holder.btn_append_evaluate.setVisibility(View.GONE);
                }

                /** 追加评论 */
                holder.btn_append_evaluate.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        boolean isIn = false;
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
                            ToastUtil.showShortText(context, "商品售后处理中，不能追评");
                            return;
                        }

                        Intent intent = new Intent(context, AppendEvaluateOrderNewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("order", order);
                        intent.putExtras(bundle);
                        OrderInfoFragment.isAdd = false;
                        context.startActivity(intent);

                        // mFragment.finish();
                    }
                });

                /** 删除订单 */
                holder.btn_delete_orders5.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String order_code = order.getOrder_code();
                        customDialog(DIALOG_DELETE, order_code, position);

                    }
                });

                break;
            case 9:// 已取消
                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.VISIBLE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);

                if (order.getShop_from() == 13) {
                    holder.tv_status.setText("订单关闭");
                } else {
                    holder.tv_status.setText("取消订单");
                }


                holder.btn_deleteorders.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String order_code = order.getOrder_code();
                        customDialog(DIALOG_DELETE, order_code, position);

                    }
                });

                break;
            case 10:// 已取消
                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.VISIBLE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);
                holder.tv_status.setText("订单已过期");

                holder.btn_deleteorders.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String order_code = order.getOrder_code();
                        customDialog(DIALOG_DELETE, order_code, position);

                    }
                });

                break;
            case 6:// 已完结
                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.VISIBLE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);
                holder.tv_status.setText("交易成功");

                holder.btn_deleteorders.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String order_code = order.getOrder_code();
                        customDialog(DIALOG_DELETE, order_code, position);

                    }
                });

                break;
            case 7:// 延长收货
                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.VISIBLE);
                holder.lay9.setVisibility(View.GONE);
                holder.tv_status.setText("延长收货");
                // holder.btnExtend_Receipt
                // .setOnClickListener(new View.OnClickListener() {// 延长收货
                //
                // @Override
                // public void onClick(View v) {
                // String order_code = order.getOrder_code();
                // extensionOrdershop(order_code);
                // }
                // });
                holder.btn_see_logistics7.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent(context, LogisticsInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("order", order);
                        intent.putExtras(bundle);
                        OrderInfoFragment.isAdd = false;
                        context.startActivity(intent);
                    }
                });
                holder.btn_confirm_receipt7.setOnClickListener(new View.OnClickListener() {// 确认收货

                    @Override
                    public void onClick(View v) {
                        final String order_code = order.getOrder_code();

                        checkMyWalletPayPass(v, order_code, position);

                    }
                });
                break;
            //11 -14 一元拼团
            case 11:// -拼团中

                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);
                holder.tv_one_buy_tuan.setText("邀请好友拼团");

                break;

            case 12://待疯抢
                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);
                holder.tv_one_buy_tuan.setText("立即去免费领");


                holder.tv_one_buy_tuan.setBackgroundResource(R.drawable.back_pop_in);
                holder.tv_one_buy_tuan.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent OneBuyintent = new Intent(context, OneBuyChouJiangActivity.class);
                        OneBuyintent.putExtra("isMeal", "1".equals(order.getIsTM()));

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("order", order);
                        OneBuyintent.putExtras(bundle);
                        context.startActivity(OneBuyintent);
                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    }
                });

                break;

            case 13://拼团失败

                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.VISIBLE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);
                holder.btn_Evaluation_Order.setVisibility(View.GONE);

                holder.btn_Delete_Orders.setOnClickListener(new OnClickListener() {// 删除订单

                    @Override
                    public void onClick(View v) {
                        String order_code = order.getOrder_code();
                        customDialog(DIALOG_DELETE, order_code, position);

                    }
                });


                if (order.getIs_FightShow() == 1) {

                    holder.lay4.setVisibility(View.GONE);
                    holder.lay_mpt_btn.setVisibility(View.VISIBLE);

                    holder.btn_delete_order.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String order_code = order.getOrder_code();
                            customDialog(DIALOG_DELETE, order_code, position);

                        }
                    });


                    holder.btn_mpk.setText("使用免拼卡直接发货");


                    holder.btn_mpk.setBackgroundResource(R.drawable.back_pop_in);
                    holder.btn_mpk.setClickable(true);
                    holder.btn_mpk.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            HashMap<String, String> map = new HashMap<>();
                            YConn.httpPost(context, YUrl.QUERY_TIQIAN_TXCJ, map, new HttpListener<Choujiang20Data>() {
                                @Override
                                public void onSuccess(Choujiang20Data result) {
                                    if ("1".equals(result.getStatus())) {
                                        if (result.getData().getIs_finish() == 1) {
                                            if (null != SignDrawalLimitActivity.instance) {
                                                SignDrawalLimitActivity.instance.finish();
                                            }

                                            Intent txcjIntent = new Intent(context, SignDrawalLimitActivity.class);
                                            txcjIntent.putExtra("type", 1)
                                                    .putExtra("fromPT", true);

                                            context.startActivity(txcjIntent);
                                            ((FragmentActivity) context).overridePendingTransition(
                                                    R.anim.slide_left_in, R.anim.slide_match);
                                        } else {
//                                            context. startActivity(new Intent(context, MyVipListActivity.class)
//                                                    .putExtra("isGuideMPK", true));


                                            Intent OneBuyintent = new Intent(context, OneBuyGroupsDetailsActivity.class);
                                            OneBuyintent.putExtra("isMeal", "1".equals(order.getIsTM()));

                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("order", order);
                                            OneBuyintent.putExtra("roll_code", order.getRoll_code());

                                            OneBuyintent.putExtras(bundle);
                                            context.startActivity(OneBuyintent);
                                            ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                                        }
                                    }
                                }

                                @Override
                                public void onError() {

                                }
                            });


//                            context.startActivity(new Intent(context, MyVipListActivity.class)
//                                    .putExtra("isGuideMPK", true)
//
//                            );
                        }
                    });

                }
                break;

            case 14://疯抢未抽中

                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.VISIBLE);


                holder.btn_Evaluation_Order.setVisibility(View.GONE);
                holder.btn_Delete_Orders.setVisibility(View.VISIBLE);

                holder.btn_Delete_Orders.setOnClickListener(new OnClickListener() {// 删除订单

                    @Override
                    public void onClick(View v) {
                        String order_code = order.getOrder_code();
                        customDialog(DIALOG_DELETE, order_code, position);

                    }
                });


                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);
                break;

            case 15:////申请关闭拼团-已过96小时


                holder.tv_one_buy_tuan.setTextColor(Color.WHITE);
                holder.tv_one_buy_tuan.setText("申请关闭拼团");
                holder.tv_one_buy_tuan.setBackgroundColor(Color.BLACK);

                holder.tv_one_buy_tuan.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogUtils.callJiQiTiShi(context, order, notifydatas);
                    }
                });


                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);
                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);


                break;

            case 17://中奖订单24小时之后

                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);
                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);


                if (order.getWhether_prize() == 2) {
                    if (order.getNew_free() == 1) {
                        holder.tv_close_order_24.setVisibility(View.VISIBLE);
                        holder.tv_govip_24.setVisibility(View.VISIBLE);
                        holder.tv_lianxi_kefu_24.setVisibility(View.VISIBLE);
                        holder.tv_sqfh_kefu_24.setVisibility(View.GONE);

                    } else {
                        holder.tv_close_order_24.setVisibility(View.VISIBLE);
                        holder.tv_govip_24.setVisibility(View.GONE);
                        holder.tv_lianxi_kefu_24.setVisibility(View.GONE);
                        holder.tv_sqfh_kefu_24.setVisibility(View.VISIBLE);
                    }

                } else {
                    holder.tv_close_order_24.setVisibility(View.VISIBLE);
                    holder.tv_govip_24.setVisibility(View.GONE);
                    holder.tv_lianxi_kefu_24.setVisibility(View.GONE);
                    holder.tv_sqfh_kefu_24.setVisibility(View.GONE);


                }
                holder.tv_sqfh_kefu_24.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        Intent shareIntent = new Intent(context, ShareGroupChatActivity.class);
//                        shareIntent.putExtra("isMeal", "1".equals(order.getIsTM()));
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("order", order);
//                        shareIntent.putExtras(bundle);
//                        context.startActivity(shareIntent);
//                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

//                        context.startActivity(new Intent(context, KeFuActivity.class));
//                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                        WXminiAppUtil.jumpToWXmini(context);


                    }
                });
                holder.tv_close_order_24.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String order_code = order.getOrder_code();
                        customDialog(DIALOG_DELETE, order_code, position);
                    }
                });
                holder.tv_govip_24.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        ToastUtil.showShortText2("中奖订单24小时后"+ "成为会员");


                        BasicActivity.goToGuideVipOrToMyVipList(context, 0);


                    }
                });
                holder.tv_lianxi_kefu_24.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent shareIntent = new Intent(context, ShareGroupChatActivity.class);
//                        shareIntent.putExtra("isMeal", "1".equals(order.getIsTM()));
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("order", order);
//                        shareIntent.putExtras(bundle);
//                        context.startActivity(shareIntent);
//                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

//                        context.startActivity(new Intent(context, KeFuActivity.class));
//                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                        WXminiAppUtil.jumpToWXmini(context);


                    }
                });


                holder.lay10.setVisibility(View.VISIBLE);


                break;


            case 21:

                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);
                holder.tv_one_buy_tuan.setText("分享好友免费领");
                holder.tv_one_buy_tuan.setVisibility(View.VISIBLE);


                holder.tv_one_buy_tuan.setBackgroundResource(R.drawable.back_pop_in);
                holder.tv_one_buy_tuan.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {


//                        Intent OneBuyintent = new Intent(context, OneBuyChouJiangActivity.class);
//                        OneBuyintent.putExtra("isMeal", "1".equals(order.getIsTM()));
//
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("order", order);
//                        OneBuyintent.putExtras(bundle);
//                        context.startActivity(OneBuyintent);
//                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                        Intent OneBuyintent = new Intent(context, VipShareGroupsDetailsActivity.class);
                        OneBuyintent.putExtra("isMeal", "1".equals(order.getIsTM()));

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("order", order);
                        OneBuyintent.putExtra("isKaituan", true);

                        OneBuyintent.putExtras(bundle);
                        context.startActivity(OneBuyintent);
                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                    }
                });


                break;
            /*
             * case 9:// 取消订单 holder.lay1.setVisibility(View.GONE);
             * holder.lay2.setVisibility(View.GONE);
             * holder.lay3.setVisibility(View.GONE);
             * holder.lay4.setVisibility(View.GONE);
             *
             * holder.lay5.setVisibility(View.GONE);.
             * holder.lay6.setVisibility(View.GONE);
             * holder.lay7.setVisibility(View.GONE);
             * holder.lay9.setVisibility(View.GONE);
             * holder.tv_status.setText("取消订单");
             *
             * break;
             */

            default:
                break;
        }

//        if (order.getShop_from() == 11 && (status != 13 || status != 14)) {
//            holder.tv_one_buy_tuan.setVisibility(View.VISIBLE);
//        } else {
//            holder.tv_one_buy_tuan.setVisibility(View.GONE);
//
//        }

        if (status == 11 || status == 12 || status == 15 || status == 21) {
            holder.tv_one_buy_tuan.setVisibility(View.VISIBLE);
        } else {
            holder.tv_one_buy_tuan.setVisibility(View.GONE);

        }


        //处理自动提现

//        if (status == 13 || status == 14) {
//
//
//            holder.tv_one_buy_tuan.setText("退款成功");
//            holder.tv_one_buy_tuan.setTextColor(Color.WHITE);
//            holder.tv_one_buy_tuan.setBackgroundColor(Color.BLACK);
//            holder.tv_one_buy_tuan.setVisibility(View.VISIBLE);
//
//            holder.tv_one_buy_tuan.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    context.startActivity(new Intent(context, FundDetailsActivity.class)
//                            .putExtra("index", 3));
//                }
//            });
//        }
        if (status == 2) {
            if ((order.getShop_from() == 10 || order.getShop_from() == 11 || order.getShop_from() == 13) //中奖处理
                    && (order.getWhether_prize() == 0 || order.getWhether_prize() == 2)) {
                holder.tv_one_buy_tuan.setVisibility(View.VISIBLE);
            }
        }

        if (status == 17) {
            holder.tv_one_buy_tuan.setVisibility(View.GONE);
        }


//        if (order.getCheck() >= 0) {
//            if (status == 13 || status == 14) {
//                String zdtyText;
//                if (order.getCheck() == 3) {
//                    zdtyText = "退款成功";
//                } else if (order.getCheck() == 11 || order.getCheck() == 2) {
//                    zdtyText = "退款失败";
//                } else {
//                    zdtyText = "退款中";
//                    holder.lay4.setVisibility(View.GONE);
//                    holder.lay5.setVisibility(View.GONE);
//                    holder.lay6.setVisibility(View.GONE);
//
//                }
//                holder.tv_one_buy_tuan.setText(zdtyText);
//                holder.tv_one_buy_tuan.setTextColor(Color.WHITE);
//                holder.tv_one_buy_tuan.setBackgroundColor(Color.BLACK);
//                holder.tv_one_buy_tuan.setVisibility(View.VISIBLE);
//
//                holder.tv_one_buy_tuan.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//
//                        if (order.getCheck() == 3) {//退款成功
//                            //到提现详情
//                            context.startActivity(new Intent(context, MyWalletCommonFragmentActivity.class)
//                                    .putExtra("flag", "withDrawaDetailsFragment")
//                                    .putExtra("business_code", order.getBusiness_code())
//                            );
//
//                        } else if (order.getCheck() == 11 || order.getCheck() == 2) {//退款失败
//                            DialogUtils.tuiKaunFailDialog(context);
//                        } else { //退款中
//                            //到提现详情
//                            context.startActivity(new Intent(context, MyWalletCommonFragmentActivity.class)
//                                    .putExtra("flag", "withDrawaDetailsFragment")
//                                    .putExtra("business_code", order.getBusiness_code())
//
//                            );
//                        }
//
//                    }
//                });
//
//            }
//
//        }


    }

    private AlertDialog dialog1;

    private void customDialog() {
        AlertDialog.Builder builder = new Builder(context);
        // 自定义一个布局文件
        View view = View.inflate(context, R.layout.payback_esc_apply_dialog, null);
        TextView tv_des = view.findViewById(R.id.tv_des);
        tv_des.setText("您还没有设置支付密码，立即去设置？");

        Button ok = view.findViewById(R.id.ok);
        ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
        Button cancel = view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 把这个对话框取消掉
                dialog1.dismiss();
            }
        });

        ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SetMyPayPassActivity.class);
                context.startActivity(intent);
                dialog1.dismiss();
            }
        });

        dialog1 = builder.create();
        dialog1.setView(view, 0, 0, 0, 0);
        dialog1.show();
    }

    private PayPasswordCustomDialog customDialog;
    private InputDialogListener inputDialogListener;

    private void checkMyWalletPayPass(View v, final String order_code, final int position) {
        // TODO Auto-generated method stub
        new SAsyncTask<Void, Void, CheckPwdInfo>((FragmentActivity) context, v, R.string.wait) {

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
                        customDialog();
                    } else if (result != null && "1".equals(result.getStatus()) && "2".equals(result.getFlag())) {
                        PayPasswordCustomDialog customDialog = new PayPasswordCustomDialog(context, R.style.mystyle,
                                R.layout.pay_customdialog, "请输入支付密码", null);

                        InputDialogListener inputDialogListener = new InputDialogListener() {

                            @Override
                            public void onOK(String pwd) {
                                // 给密码文本框设置密码
                                // ToastUtil.showLongText(context, "你的密码是："
                                // + text);
                                affirmOrder(order_code, pwd, position);
                                // freshAdapter(position);
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
    }

    /**
     * 去支付
     *
     * @param order
     */
    protected void goToPay(Order order) {

        UserInfo user = YCache.getCacheUser(context);
        if (user.getGender() == 1) {
            ToastUtil.showShortText2("系统维护中，暂不支持支付");
            return;
        }


        /**
         * List<OrderShop> list = order.getList(); if (list.size() == 1) {
         * ShareUtil.getPicPath(list.get(0).getShop_code(), null,
         * (FragmentActivity) context); } ArrayList<Order> listOrder = new
         * ArrayList<Order>(); ArrayList<ShopCart> shopcats = new ArrayList
         * <ShopCart>(); for (int i = 0; i < list.size(); i++) { OrderShop
         * orderShop = list.get(i); ShopCart shopCart = new ShopCart();
         * shopCart.setSize(orderShop.getSize());
         * shopCart.setColor(orderShop.getColor());
         * shopCart.setShop_code(orderShop.getShop_code());
         * shopCart.setShop_name(orderShop.getShop_name(0));
         * shopCart.setShop_price(orderShop.getShop_price());
         * shopCart.setShop_num(orderShop.getShop_num());
         * shopCart.setShop_se_price(order.getOrder_price());
         * shopCart.setSupp_id(order.getSupp_id()); MyLogYiFu.e("TAG", "库存表id=" +
         * orderShop.getStocktypeid() + ",价格=" + orderShop.getShop_price());
         * shopCart.setStock_type_id(orderShop.getStocktypeid());
         * shopcats.add(shopCart); } Intent intent = new Intent(context,
         * OrderPaymentActivity.class); Bundle bundle = new Bundle();
         * HashMap<String, Object> hashMap = new HashMap<String, Object>();
         * hashMap.put("order_code", order.getOrder_code());
         * bundle.putSerializable("result", (Serializable) hashMap);
         * bundle.putDouble("totlaAccount", order.getOrder_price());
         * listOrder.add(order); bundle.putSerializable("listOrder",
         * (Serializable) listOrder); bundle.putSerializable("listGoods",
         * (Serializable) shopcats); intent.putExtras(bundle);
         * intent.putExtra("isMulti", order.getList().size() > 1); ((Activity)
         * context).startActivityForResult(intent, 102);
         */
        List<Order> orders = new ArrayList<Order>();
        orders.add(order);
        Intent intent = new Intent(context, OrderPaymentActivity.class);
        if (order.getShop_from() == 0) {
            intent.putExtra("order_common", "order_common");
        } else if (order.getShop_from() == 1) {
            intent.putExtra("order_special", "order_special");
        } else if (order.getShop_from() == 3) {
            intent.putExtra("signShopDetail", "SignShopDetail");
            intent.putExtra("signType", this.getSignType(order));
        } else if (order.getShop_from() == 4 || 6 == order.getShop_from()) {
            intent.putExtra("isDuobao", true);
            intent.putExtra("signType", this.getSignType(order));
        }
        intent.putExtra("single", "single");
        intent.putExtra("order_code", order.getOrder_code());
        intent.putExtra("totlaAccount", order.getRemain_money());
        intent.putExtra("isMulti", false);
        Bundle bundle = new Bundle();
        bundle.putSerializable("listOrder", (Serializable) orders);
        intent.putExtras(bundle);

        ((Activity) context).startActivityForResult(intent, 101);
    }

    private int getSignType(Order order) {
        if (order.getShop_from() == 4 || order.getShop_from() == 3 || 6 == order.getShop_from()) {
            String[] str = (String.valueOf(order.getOrder_price())).split("\\.");
            String type = str[0];
            return Integer.parseInt(type);
        } else {
            return 0;
        }
    }

    /**
     * 刷新数据
     *
     * @param position
     */
    protected void freshAdapter(int position) {
        listData.remove(position);
        this.notifyDataSetChanged();
    }

    protected void freshAdapter() {
        // listData.remove(position);
        this.notifyDataSetChanged();
    }

    /****
     * 取消订单
     *
     * @param order_code
     */

    private void escOrder(String order_code, final String reason, final int position) {

        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) context, null, R.string.wait) {
            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                return ComModel.escOrder(context, YCache.getCacheToken(context), params[0], reason);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo r, Exception e) {

                if (e != null) {// 查询异常
                    LogYiFu.e("异常 -----", context.getString(R.string.ss));
                    Toast.makeText(context, "取消订单失败", Toast.LENGTH_SHORT).show();
                } else {// 查询商品详情成功，刷新界面
                    freshAdapter(position);
                    if (listData.size() == 0) { // 当前没有数据，显示无数据页面
                        mFragment.showNoDataPager();
                    }
                    if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
                        // 获取金币金券相关数据
                        GetJinBiJinQuanUtils.getJinBi(context);
                        GetJinBiJinQuanUtils.getJinQuan(context);
                    }
                    SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
                    Toast.makeText(context, "取消订单成功", Toast.LENGTH_SHORT).show();
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

    /****
     * 夺宝 取消订单
     *
     * @param order_code
     */

    private void escOrderDuo(String order_code, final String reason, final int position) {

        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) context, null, R.string.wait) {
            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                return ComModel.escOrderDuo(context, YCache.getCacheToken(context), params[0], reason);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo r, Exception e) {

                if (e != null) {// 查询异常
                    LogYiFu.e("异常 -----", context.getString(R.string.ss));
                    Toast.makeText(context, "取消订单失败", Toast.LENGTH_SHORT).show();
                } else {// 查询商品详情成功，刷新界面
                    freshAdapter(position);
                    if (listData.size() == 0) { // 当前没有数据，显示无数据页面
                        mFragment.showNoDataPager();
                    }
                    SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
                    Toast.makeText(context, "取消订单成功", Toast.LENGTH_SHORT).show();
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

    /****
     * 根据订单编号确定收货
     *
     * @param order_code
     * @param pwd
     */
    private void affirmOrder(String order_code, String pwd, final int position) {
        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) context, null, R.string.wait) {
            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                return ComModel.affirmOrder(context, YCache.getCacheToken(context), params[0], params[1]);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo r, Exception e) {

                if (e != null) {// 查询异常
                    LogYiFu.e("异常 -----", context.getString(R.string.ss));
                    // Toast.makeText(context, "提交失败确定", Toast.LENGTH_SHORT)
                    // .show();
                } else {

                    int pwdflag = r.getPwdflag();

                    if (pwdflag == 0) {
                        // ToastUtil.showShortText(context,
                        // "支付成功");

                        if (r.getStatus().equals("1")) {
                            Toast.makeText(context, r.getMessage(), Toast.LENGTH_SHORT).show();
                            freshAdapter(position);
                            if (listData.size() == 0) { // 当前没有数据，显示无数据页面
                                mFragment.showNoDataPager();
                            }
                        } else {
                            ToastUtil.showShortText(context, r.getMessage());
                        }

                        // MealSubmitOrderActivity.instance.finish();
                        // MealPaymentActivity.this.finish();
                        // paySuccessTo();
                        // timeCount.start();
                        // rel_pay_success.setVisibility(View.VISIBLE);
                        // rel_nomal.setVisibility(View.GONE);
                    } else if (pwdflag == 1 || pwdflag == 2 || pwdflag == 3) { // 支付密码错误
                        String message = r.getMessage();
                        // customDialog.dismiss();
                        PayErrorDialog dialog = new PayErrorDialog(context, R.style.DialogStyle1, pwdflag, message);
                        dialog.show();

                    }

                    /*
                     * if (r.getStatus().equals("1")) { //
                     * Toast.makeText(context, r.getMessage(), //
                     * Toast.LENGTH_SHORT).show(); freshAdapter(position); if
                     * (listData.size() == 0) { // 当前没有数据，显示无数据页面
                     * mFragment.showNoDataPager(); } } else { //
                     * ToastUtil.showShortText(context, r.getMessage()); }
                     */
                }

            }

            ;

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;
        }.execute(order_code, pwd);

    }

    /****
     * 删除订单
     *
     * @param order_code
     */
    private void returnShop(String order_code, final int position) {
        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) context, null, R.string.wait) {
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
                    freshAdapter(position);
                    if (listData.size() == 0) { // 当前没有数据，显示无数据页面
                        mFragment.showNoDataPager();
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

    public interface notifyDatas {
        void refresh();

    }

    private notifyDatas notifydatas;

    public void setNotifyDatas(Fragment f) {
        this.notifydatas = (notifyDatas) f;
    }

    /******
     * 延长收货
     *
     * @param order_code
     */
    private void extensionOrdershop(String order_code) {
        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) context, null, R.string.wait) {
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
                    notifydatas.refresh();
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

    /****
     * 通过我的钱包来付款
     *
     * @param order_code
     * @param pwd
     */
    private void walletPayOrder(String order_code, String pwd) {
        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) context, null, R.string.wait) {
            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                return ComModel.walletPayOrder(context, params[0], params[1]);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo r, Exception e) {

                if (e != null) {// 查询异常
                    LogYiFu.e("异常 -----", context.getString(R.string.ss));
                    Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
                } else {// 查询商品详情成功，刷新界面
                    Toast.makeText(context, "付款成功", Toast.LENGTH_SHORT).show();
                }

            }

            ;

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;
        }.execute(order_code, pwd);

    }

    /**
     * 退款
     *
     * @param return_type
     * @param return_reason
     * @param position
     */
    private void returnShopPayBack(final String order_code, final String return_type, final String return_reason,
                                   final int position) {
        new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context, R.string.wait) {
            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel.returnShop(context, return_reason, "android.jpg", return_type, order_code,
                        return_reason);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result != null && "1".equals(result.getStatus())) {
                        freshAdapter(position);

                        if (listData.size() == 0) { // 当前没有数据，显示无数据页面
                            mFragment.showNoDataPager();
                        }

                        ToastUtil.showShortText(context, result.getMessage());
                    } else {
                        ToastUtil.showShortText(context, "糟糕，出错了~~~");
                    }
                }
            }

        }.execute();
    }

    /******
     * 提醒发货
     *
     * @param order_code
     */
    private void remainOrdershop(String order_code) {
        new SAsyncTask<String, Void, RemainShipInfo>((FragmentActivity) context, null, R.string.wait) {
            @Override
            protected RemainShipInfo doInBackground(FragmentActivity context, String... params) throws Exception {
                return ComModel.urgesuppShipments(context, params[0]);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, RemainShipInfo info, Exception e) {

                if (e != null) {// 查询异常
                    LogYiFu.e("异常 -----", context.getString(R.string.ss));
                    // Toast.makeText(context, R.string.fh, Toast.LENGTH_SHORT)
                    // .show();
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

}
