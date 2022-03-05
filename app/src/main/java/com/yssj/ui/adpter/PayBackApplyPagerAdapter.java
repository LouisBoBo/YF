package com.yssj.ui.adpter;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YConstance;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.Order;
import com.yssj.entity.OrderShop;
import com.yssj.ui.activity.payback.PaybackCommonFragmentActivity;
import com.yssj.ui.base.BaseObjectAdapter;
import com.yssj.ui.fragment.payback.PayBackChoiceServiceFragment;
import com.yssj.ui.fragment.payback.hh.HHFragment;
import com.yssj.ui.fragment.payback.hh.HHMemberShopFragment;
import com.yssj.ui.fragment.payback.tk.TKFragment;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

public class PayBackApplyPagerAdapter extends BaseObjectAdapter {

    private LayoutInflater inflater;

    private int status;

    public PayBackApplyPagerAdapter(Context context, String flag) {
        super(context);
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = View.inflate(context,
                    R.layout.apply_payback_list_item, null);
            // holder.iv_goods_icon = (ImageView)
            // convertView.findViewById(R.id.iv_goods_icon);
            // holder.tv_goods_name = (TextView)
            // convertView.findViewById(R.id.tv_goods_name);
            // holder.tv_payback_status = (TextView)
            // convertView.findViewById(R.id.tv_payback_status);
            // holder.tv_color = (TextView)
            // convertView.findViewById(R.id.tv_color);
            // holder.tv_size = (TextView)
            // convertView.findViewById(R.id.tv_size);
            // holder.tv_count = (TextView)
            // convertView.findViewById(R.id.tv_count);
            // holder.tv_money = (TextView)
            // convertView.findViewById(R.id.tv_money);
            holder.tv_payback_money = (TextView) convertView
                    .findViewById(R.id.tv_payback_money);
            holder.tv_count_goods = (TextView) convertView
                    .findViewById(R.id.tv_count_goods);
            holder.continer = (LinearLayout) convertView
                    .findViewById(R.id.container);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Order order = (Order) getItem(position);
        status = Integer.parseInt(order.getStatus().toString());
        if (order != null) {

//			if (order.getList() != null && order.getList().size() > 0) {
//				double itemAccount = addView(order.getList(), holder.continer,
//						order);
//
//				// holder.container_item.addView(layout);
//				String totalAccount = new java.text.DecimalFormat("#0.00")
//						.format(itemAccount);
//				holder.tv_payback_money.setText("实付 :￥"
//						+ order.getOrder_price());
//				holder.tv_count_goods.setText("共" + order.getList().size()
//						+ "件商品");
//			}

//			
            double itemAccount = addView(order.getList(), holder.continer,
                    order);

            // holder.container_item.addView(layout);
            String totalAccount = new java.text.DecimalFormat("#0.00")
                    .format(itemAccount);
            holder.tv_payback_money.setText("实付 :¥"
                    + order.getPay_money());
            if (order.getShop_from() == 4) {
                holder.tv_count_goods.setText("共1" +
                        "件商品");
            } else {
                holder.tv_count_goods.setText("共" + order.getList().size()
                        + "件商品");
            }


        }

        return convertView;
    }

    private double addView(final List<OrderShop> list, LinearLayout container,
                           final Order order) {
        container.removeAllViews();
        double itemAccount = 0;
        if (1 == order.getShop_from()) {
            View view = inflater.inflate(R.layout.listview_orderlist_son, null);
            ImageView img_product = (ImageView) view
                    .findViewById(R.id.img_product1);
            TextView tv_product_name = (TextView) view
                    .findViewById(R.id.tv_product_name);
            TextView tv_shop_num = (TextView) view
                    .findViewById(R.id.tv_shop_num);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
            TextView tvColor = (TextView) view
                    .findViewById(R.id.tv_product_color);
            TextView tvSize = (TextView) view
                    .findViewById(R.id.tv_product_size);
            TextView tvStatus = (TextView) view.findViewById(R.id.tv_status);
            TextView meal = (TextView) view.findViewById(R.id.meal);
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
            if (!TextUtils.isEmpty(shop_name)) {
                tv_product_name.setText(shop_name);
            }
            String price = new java.text.DecimalFormat("#0.00").format(order
                    .getOrder_price() - order.getPostage());

            tv_price.setText("￥" + price);
            int num = order.getShop_num();
            tv_shop_num.setText("x" + "1");

            final int orderShopStatus = list.get(0).getStatus();
            int orderShopChange = list.get(0).getChange();
            if (orderShopStatus == 0) {
                switch (status) {
                    case 1:
                        if (order.getLast_time().before(new Date())) {
                            tvStatus.setText("已过期");
                        } else {
                            tvStatus.setText("待付款");
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
                        tvStatus.setText("取消订单");
                        break;
                    case 10:
                        tvStatus.setText("订单已过期");
                        break;

                    default:
                        break;
                }
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

            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedPreferencesUtil.saveBooleanData(context,
                            "daishouhuo", false);
                    // TODO Auto-generated method stub
//					if (orderShopStatus != 0) {
//						ToastUtil.showShortText(context, "该商品已申请过售后");
//						return;
//					}
                    Calendar c = Calendar.getInstance();
                    int day = c.get(Calendar.DAY_OF_MONTH);
//					if (!("" + YCache.getCacheToken(context) + day).equals(SharedPreferencesUtil.getStringData(context, YConstance.Pref.APPLY_CLICK, ""))) {
//						SharedPreferencesUtil.saveStringData(context, YConstance.Pref.APPLY_CLICK, "" + YCache.getCacheToken(context) + day);
//						noticeDialog();
//						return;
//					}

                    if (orderShopStatus != 0) {
                        ToastUtil.showShortText(context, "该商品已申请过售后");
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("order_code", order.getOrder_code());
                    // bundle.putString("order_price",orderShop.getOrder_price()
                    // + "");
                    // bundle.putInt("orderShopId", order.getId());
                    bundle.putSerializable("orderShop", order);
                    double price;
                    if ("2".equals(order.getStatus() + "")) {
                        price = order.getPay_money();
                    } else {
                        price = order.getPay_money() - order.getPostage();
                    }
                    bundle.putString("order_price", price + "");
                    bundle.putString("isMeal", "isMeal");
                    bundle.putInt("issue_status", list.get(0).getIssue_status());

                    if (Integer.parseInt(order.getStatus().toString()) != 2) {

                        if ("4".equals(order.getStatus().toString())
                                || "5".equals(order.getStatus().toString())
                                || "6".equals(order.getStatus().toString())) {
                            if (order.getIs_kick() == 1) {
                                bundle.putBoolean("isHH", true);
                            }

                        }
                        // if("3".equals(order.getStatus().toString())){
                        // PayBackChoiceServiceFragment mFragment = new
                        // PayBackChoiceServiceFragment();
                        // mFragment.setArguments(bundle);
                        // SharedPreferencesUtil.saveBooleanData(context,
                        // "daishouhuo", true);
                        // ((FragmentActivity) context)
                        // .getSupportFragmentManager().beginTransaction()
                        // .replace(R.id.fl_content, mFragment)
                        // .addToBackStack(null).commit();
                        //
                        // }else{
                        //
                        // PayBackChoiceServiceFragment mFragment = new
                        // PayBackChoiceServiceFragment();
                        // mFragment.setArguments(bundle);
                        // ((FragmentActivity) context)
                        // .getSupportFragmentManager().beginTransaction()
                        // .replace(R.id.fl_content, mFragment)
                        // .addToBackStack(null).commit();
                        if ("3".equals(order.getStatus().toString())
                                || "4".equals(order.getStatus().toString())
                                || "5".equals(order.getStatus().toString())
                                || "6".equals(order.getStatus().toString())) {
                            PayBackChoiceServiceFragment mFragment = new PayBackChoiceServiceFragment();
                            mFragment.setArguments(bundle);
                            SharedPreferencesUtil.saveBooleanData(context,
                                    "daishouhuo", true);
                            ((FragmentActivity) context)
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fl_content, mFragment)
                                    .addToBackStack(null).commit();
                        }

                    } else {
                        TKFragment mFragment = new TKFragment();
                        mFragment.setArguments(bundle);
                        ((FragmentActivity) context)
                                .getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fl_content, mFragment)
                                .addToBackStack(null).commit();
                    }

                }
            });

            container.addView(view);

        } else if (4 == order.getShop_from() || 6 == order.getShop_from()) {
            final int orderShopStatus = Integer.parseInt(order.getIssue_status());

            View view = inflater.inflate(R.layout.listview_orderlist_son, null);
            ImageView img_product = (ImageView) view
                    .findViewById(R.id.img_product1);
            TextView tv_product_name = (TextView) view
                    .findViewById(R.id.tv_product_name);
            TextView tv_shop_num = (TextView) view
                    .findViewById(R.id.tv_shop_num);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
            TextView tvColor = (TextView) view
                    .findViewById(R.id.tv_product_color);
            TextView tvSize = (TextView) view
                    .findViewById(R.id.tv_product_size);
            TextView tvStatus = (TextView) view.findViewById(R.id.tv_status);
            TextView meal = (TextView) view.findViewById(R.id.meal);
            meal.setVisibility(View.GONE);
            tvColor.setVisibility(View.GONE);
            tvSize.setVisibility(View.GONE);
//			String pic = order.getOrder_pic();
//			img_product.setTag(pic);

//			if (list.size() == 1) {
//				meal.setText("超值单品");
//			} else {
//				meal.setText("超值套餐");
//			}

            String pic = YUrl.imgurl + order.getBak().substring(1, 4)
                    + "/" + order.getBak() + "/"
                    + (String) order.getOrder_pic();
            img_product.setTag(pic);

            if (!TextUtils.isEmpty(pic)) {
//				SetImageLoader.initImageLoader(context, img_product, pic, "");
                PicassoUtils.initImage(context, pic, img_product);

            }
            String shop_name = order.getOrder_name();
            if (!TextUtils.isEmpty(shop_name)) {
                tv_product_name.setText(shop_name);
            }
            String price = new java.text.DecimalFormat("#0.00").format(order
                    .getOrder_price() - order.getPostage());

            tv_price.setText("¥" + price);
            int num = order.getShop_num();
            tv_shop_num.setText("x" + "1");


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


            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedPreferencesUtil.saveBooleanData(context,
                            "daishouhuo", false);
                    // TODO Auto-generated method stub
//					if (orderShopStatus != 0) {
//						ToastUtil.showShortText(context, "该商品已申请过售后");
//						return;
//					}
//					 if (orderShopStatus != 0) {
//							ToastUtil.showShortText(context, "该商品已申请过售后");
//							return;
//						}
                    if (orderShopStatus == 0 || orderShopStatus == 4 || orderShopStatus == 1 || orderShopStatus == 2) {
                        return;
                    }
                    Calendar c = Calendar.getInstance();
                    int day = c.get(Calendar.DAY_OF_MONTH);
//					if (!("" + YCache.getCacheToken(context) + day).equals(SharedPreferencesUtil.getStringData(context, YConstance.Pref.APPLY_CLICK, ""))) {
//						SharedPreferencesUtil.saveStringData(context, YConstance.Pref.APPLY_CLICK, "" + YCache.getCacheToken(context) + day);
//						noticeDialog();
//						return;
//					}
                    Bundle bundle = new Bundle();
                    bundle.putString("order_code", order.getOrder_code());

                    // bundle.putString("order_price",orderShop.getOrder_price()
                    // + "");
                    // bundle.putInt("orderShopId", order.getId());
                    bundle.putSerializable("orderShop", order);
                    double price;
                    if ("2".equals(order.getStatus() + "")) {      // 订单状态1待付款2代发货3待收货4待评价5退款中6已完结7延长收货
                        price = order.getPay_money();
                    } else {
                        price = order.getPay_money() - order.getPostage();
                    }
                    bundle.putString("order_price", price + "");
                    bundle.putString("isMeal", "isMeal");
                    bundle.putString("isDuobao", "isDuobao");

                    bundle.putInt("issue_status", list.get(0).getIssue_status());

                    PayBackChoiceServiceFragment mFragment = new PayBackChoiceServiceFragment();
                    mFragment.setArguments(bundle);
                    ((FragmentActivity) context)
                            .getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fl_content, mFragment)
                            .addToBackStack(null).commit();


                }
            });

            container.addView(view);


        } else {
            for (final OrderShop orderShop : list) {

                final int orderShopStatus = orderShop.getStatus();
                int orderShopChange = orderShop.getChange();

                View view = inflater.inflate(R.layout.listview_orderlist_son,
                        null);
                ImageView img_product = (ImageView) view
                        .findViewById(R.id.img_product1);
                TextView tv_product_name = (TextView) view
                        .findViewById(R.id.tv_product_name);
                TextView tv_shop_num = (TextView) view
                        .findViewById(R.id.tv_shop_num);
                TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
                TextView tvColor = (TextView) view
                        .findViewById(R.id.tv_product_color);
                TextView tvSize = (TextView) view
                        .findViewById(R.id.tv_product_size);
                TextView tvStatus = (TextView) view
                        .findViewById(R.id.tv_status);
                // TextView tvOriginalPrice = (TextView) view
                // .findViewById(R.id.tv_original_price);

                String pic = orderShop.getShop_code().substring(1, 4) + "/"
                        + orderShop.getShop_code() + "/"
                        + orderShop.getShop_pic();
                img_product.setTag(pic);
                if (!TextUtils.isEmpty(pic)) {
//					SetImageLoader.initImageLoader(context, img_product, pic,
//							"");
//					
                    PicassoUtils.initImage(context, pic, img_product);
                }
                String shop_name = orderShop.getShop_name(0);
                if (!TextUtils.isEmpty(shop_name)) {
                    tv_product_name.setText(shop_name);
                }
                tvColor.setText("颜色 : " + orderShop.getColor());
                tvSize.setText("尺寸 : " + orderShop.getSize());
                final String price = new java.text.DecimalFormat("#0.00")
                        .format(orderShop.getShop_price());
                tv_price.setText("¥" + price);
                int num = orderShop.getShop_num();
                tv_shop_num.setText("x" + num);
                itemAccount += orderShop.getShop_price()
                        * orderShop.getShop_num();

                if (orderShopStatus == 0) {
                    switch (Integer.parseInt(order.getStatus().toString())) {
                        case 1:
                            if (order.getLast_time().before(new Date())) {
                                tvStatus.setText("已过期");
                            } else {
                                tvStatus.setText("待付款");
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
                            tvStatus.setText("取消订单");
                            break;

                        default:
                            break;
                    }
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
                view.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        int day = c.get(Calendar.DAY_OF_MONTH);
//						if (!("" + YCache.getCacheToken(context) + day).equals(SharedPreferencesUtil.getStringData(context, YConstance.Pref.APPLY_CLICK, ""))) {
//							SharedPreferencesUtil.saveStringData(context, YConstance.Pref.APPLY_CLICK, "" + YCache.getCacheToken(context) + day);
//							noticeDialog();
//							return;
//						}
                        SharedPreferencesUtil.saveBooleanData(context,
                                "daishouhuo", false);
                        // TODO Auto-generated method stub
                        // ToastUtil.showShortText(context,
                        // ""+orderShop.getOrder_code());
                        if (orderShopStatus != 0) {
                            ToastUtil.showShortText(context, "该商品已申请过售后");
                            return;
                        }
                        //第一张钻石卡的免费领点击先弹窗
                        if (order.getNew_free() == 13) {
                            final Dialog dialog = new Dialog(context, R.style.invate_dialog_style);
                            View view = View.inflate(context, R.layout.dialog_apply_click, null);
                            TextView btn_cancel = (TextView) view.findViewById(R.id.btn_cancel);
                            TextView balance_dialog_tv2 = (TextView) view.findViewById(R.id.balance_dialog_tv2);

                            Spanned tv2Str = Html.fromHtml("该订单申请退换货，需要扣除<font color='#FF3F8B'><strong>36元</strong></font>往返双程物流费用哦。请谨慎申请。");

                            balance_dialog_tv2.setText(tv2Str);


                            btn_cancel.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    // 把这个对话框取消掉
                                    dialog.dismiss();

                                    Bundle bundle = new Bundle();
                                    bundle.putString("order_code",
                                            orderShop.getOrder_code());
                                    // bundle.putString("order_price",orderShop.getOrder_price()
                                    // + "");
                                    bundle.putInt("orderShopId", orderShop.getId());
                                    bundle.putSerializable("orderShop", orderShop);
                                    bundle.putInt("issue_status", list.get(0).getIssue_status());

                                    if ("4".equals(order.getStatus().toString())
                                            || "5".equals(order.getStatus().toString())
                                            || "6".equals(order.getStatus().toString())) {
                                        if (order.getIs_kick() == 1) {
                                            bundle.putBoolean("isHH", true);
                                        }
                                    }
                                    if ("3".equals(order.getStatus().toString())
                                            || "4".equals(order.getStatus().toString())
                                            || "5".equals(order.getStatus().toString())
                                            || "6".equals(order.getStatus().toString())) {
                                        PayBackChoiceServiceFragment mFragment = new PayBackChoiceServiceFragment();
                                        mFragment.setArguments(bundle);
                                        SharedPreferencesUtil.saveBooleanData(context,
                                                "daishouhuo", true);
                                        ((FragmentActivity) context)
                                                .getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.fl_content, mFragment)
                                                .addToBackStack(null).commit();

                                    } else {

                                        PayBackChoiceServiceFragment mFragment = new PayBackChoiceServiceFragment();
                                        mFragment.setArguments(bundle);
                                        ((FragmentActivity) context)
                                                .getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.fl_content, mFragment)
                                                .addToBackStack(null).commit();
                                    }







                                }
                            });
                            TextView btn_ok = (TextView) view.findViewById(R.id.btn_ok);
                            btn_ok.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    dialog.dismiss();
//                                    ((Activity) context).finish();

                                }
                            });

                            // 创建自定义样式dialog
                            dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                                    LinearLayout.LayoutParams.FILL_PARENT));
                            dialog.setCancelable(false);
                            dialog.show();



                            return;
                        }

                        if (2 == order.getShop_from()
                                && (!order.getStatus().toString().equals("0"))
                                && (!order.getStatus().toString().equals("1"))
                                && (!order.getStatus().toString().equals("2"))) {
                            HHMemberShopFragment mFragment = new HHMemberShopFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("order_code",
                                    orderShop.getOrder_code());
                            // bundle.putString("order_price",orderShop.getOrder_price()
                            // + "");
                            bundle.putInt("orderShopId", orderShop.getId());
                            bundle.putSerializable("orderShop", orderShop);
                            mFragment.setArguments(bundle);
                            ((FragmentActivity) context)
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fl_content, mFragment)
                                    .addToBackStack(null).commit();
                            return;
                        }
                        if (2 == order.getShop_from()
                                && (order.getStatus().toString().equals("2"))) {
                            ToastUtil.showShortText(context,
                                    "会员商品待发货状态下不能申请售后！");
                            return;
                        }
                        // TODO:
                        if (order.getIs_kick() == 0) {
                            if (orderShop.getLast_timess() != null) {
                                if (Long.parseLong(orderShop.getLast_timess()
                                        .toString()) < Long.parseLong(order
                                        .getNowss().toString())) {
                                    ToastUtil.showShortText(context, "申请已过期");
                                }
                                return;
                            }
                        }
                        if (Integer.parseInt(order.getStatus().toString()) != 2) {
                            Bundle bundle = new Bundle();
                            bundle.putString("order_code",
                                    orderShop.getOrder_code());
                            // bundle.putString("order_price",orderShop.getOrder_price()
                            // + "");
                            bundle.putInt("orderShopId", orderShop.getId());
                            bundle.putSerializable("orderShop", orderShop);
                            bundle.putInt("issue_status", list.get(0).getIssue_status());

                            if ("4".equals(order.getStatus().toString())
                                    || "5".equals(order.getStatus().toString())
                                    || "6".equals(order.getStatus().toString())) {
                                if (order.getIs_kick() == 1) {
                                    bundle.putBoolean("isHH", true);
                                }
                            }
                            if ("3".equals(order.getStatus().toString())
                                    || "4".equals(order.getStatus().toString())
                                    || "5".equals(order.getStatus().toString())
                                    || "6".equals(order.getStatus().toString())) {
                                PayBackChoiceServiceFragment mFragment = new PayBackChoiceServiceFragment();
                                mFragment.setArguments(bundle);
                                SharedPreferencesUtil.saveBooleanData(context,
                                        "daishouhuo", true);
                                ((FragmentActivity) context)
                                        .getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fl_content, mFragment)
                                        .addToBackStack(null).commit();

                            } else {

                                PayBackChoiceServiceFragment mFragment = new PayBackChoiceServiceFragment();
                                mFragment.setArguments(bundle);
                                ((FragmentActivity) context)
                                        .getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fl_content, mFragment)
                                        .addToBackStack(null).commit();
                            }
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putString("order_code",
                                    orderShop.getOrder_code());
                            // bundle.putString("order_price",orderShop.getOrder_price()
                            // + "");
                            bundle.putInt("orderShopId", orderShop.getId());
                            bundle.putString("price", price);
                            bundle.putSerializable("orderShop", orderShop);
                            TKFragment mFragment = new TKFragment();
                            mFragment.setArguments(bundle);
                            ((FragmentActivity) context)
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fl_content, mFragment)
                                    .addToBackStack(null).commit();
                        }
                    }
                });
                container.addView(view);
            }
        }
        return itemAccount;
    }

    class ViewHolder {
        ImageView iv_goods_icon;
        TextView tv_goods_name, tv_payback_status, tv_color, tv_size, tv_count,
                tv_money, tv_payback_money, tv_count_goods;
        LinearLayout continer;
    }


    private void freeBuy79noticeDialog() {

        final Dialog dialog = new Dialog(context, R.style.invate_dialog_style);
        View view = View.inflate(context, R.layout.dialog_apply_click, null);
        TextView btn_cancel = (TextView) view.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 把这个对话框取消掉
                dialog.dismiss();
            }
        });
        TextView btn_ok = (TextView) view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
                ((Activity) context).finish();

            }
        });

        // 创建自定义样式dialog
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        dialog.setCancelable(false);
        dialog.show();
    }
}
