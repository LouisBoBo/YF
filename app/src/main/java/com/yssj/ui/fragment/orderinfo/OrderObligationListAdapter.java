package com.yssj.ui.fragment.orderinfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.CheckBox;
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
import com.yssj.entity.Order;
import com.yssj.entity.OrderShop;
import com.yssj.entity.RemainShipInfo;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.infos.EvaluateOrderNewActivity;
import com.yssj.ui.activity.shopdetails.OrderPaymentActivity;
import com.yssj.ui.dialog.PayErrorDialog;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.GetJinBiJinQuanUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.SingleChoicePopupWindow;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YCache;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderObligationListAdapter extends BaseAdapter {
    private List<Order> listData;
    private Context context;
    private LayoutInflater inflater;
    private Map<Integer, Boolean> isChecked = new HashMap<Integer, Boolean>();
    private OrderObligationFragment mFragment;
    private View parentView;

    private List<String> cancleOrderDatas = new ArrayList<String>(); // ????????????
    // ????????????

    private void getCancleOrderDatas() {
        cancleOrderDatas.add("????????????");
        cancleOrderDatas.add("???????????????????????????");
        cancleOrderDatas.add("???????????????");
        cancleOrderDatas.add("??????????????????");
        cancleOrderDatas.add("?????????");
        cancleOrderDatas.add("????????????");
    }

    public OrderObligationListAdapter(Context context, List<Order> listData, OrderObligationFragment mFragment,
                                      View parentView) {
        this.context = context;
        this.listData = listData;
        this.inflater = LayoutInflater.from(context);
        this.mFragment = mFragment;
        this.parentView = parentView;
    }

    public interface onCheckedCallback {
        void checked(int position, boolean isChecked);
    }

    public onCheckedCallback onCallback;
    protected AlertDialog dialog;
    private boolean isSelectedAll;

    public void setCartOncallback(Fragment f) {
        this.onCallback = (onCheckedCallback) f;
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

        final ViewHolder holder;

        if (null == convertView) {
            convertView = View.inflate(context, R.layout.obligation_order_item, null);

            holder = new ViewHolder();
            holder.tv_shop_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tv_sum = (TextView) convertView.findViewById(R.id.tv_sum);
            holder.tv_zprice = (TextView) convertView.findViewById(R.id.tv_zprice);
            holder.container_item = (LinearLayout) convertView.findViewById(R.id.container_item);// ???????????????listview

            holder.lay1 = (LinearLayout) convertView.findViewById(R.id.lay1);// ?????????
            holder.btn_Contact_seller = (Button) convertView.findViewById(R.id.btn_Contact_seller);
            holder.btn_Cancel_Order = (Button) convertView.findViewById(R.id.btn_Cancel_Order);
            holder.btn_Payment = (Button) convertView.findViewById(R.id.btn_Payment);

            holder.lay2 = (LinearLayout) convertView.findViewById(R.id.lay2);// ?????????
            holder.btn_Remind_shipments = (Button) convertView.findViewById(R.id.btn_Remind_shipments);

            holder.lay3 = (LinearLayout) convertView.findViewById(R.id.lay3);// ?????????
            holder.btn_Extend_Receipt = (Button) convertView.findViewById(R.id.btn_Extend_Receipt);
            holder.btn_See_Logistics = (Button) convertView.findViewById(R.id.btn_See_Logistics);
            holder.btn_Confirm_receipt = (Button) convertView.findViewById(R.id.btn_Confirm_receipt);

            holder.lay4 = (LinearLayout) convertView.findViewById(R.id.lay4);// ?????????
            holder.btn_Delete_Orders = (Button) convertView.findViewById(R.id.btn_Delete_Orders);
            holder.btn_Evaluation_Order = (Button) convertView.findViewById(R.id.btn_Evaluation_Order);

            holder.lay5 = (LinearLayout) convertView.findViewById(R.id.lay5);
            holder.lay6 = (LinearLayout) convertView.findViewById(R.id.lay6);
            holder.lay7 = (LinearLayout) convertView.findViewById(R.id.lay7);
            holder.btn_Confirm_receipt1 = (Button) convertView.findViewById(R.id.btn_Confirm_receipt1);
            holder.btnExtend_Receipt = (Button) convertView.findViewById(R.id.btnExtend_Receipt);
            holder.lay9 = (LinearLayout) convertView.findViewById(R.id.lay9);
            holder.cb_check = (CheckBox) convertView.findViewById(R.id.cb_check);
            holder.cb_check.setChecked(false);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Order order = listData.get(position);

        holder.tv_shop_name.setText(order.getOrder_code());// ????????????
        int status = Integer.parseInt(order.getStatus().toString());
        // /1?????????2?????????3?????????4?????????5?????????6?????????7????????????9????????????
        setVisibility(holder, status, order, position);

        int num = order.getShop_num();
        holder.tv_sum.setText("???" + num + "?????????");

        List<OrderShop> list = order.getList();

        holder.container_item.removeAllViews();

        if (list != null) {
            addView(list, holder.container_item, inflater, order);
//			if (order.getShop_from() == 1) {
//				holder.tv_zprice.setText("?????? : ??" + order.getOrder_price() + "(????????????" + order.getPostage() + ")");
//			} else {
//				holder.tv_zprice.setText("?????? : ??" + order.getOrder_price());
//			}

            if (order.getShop_from() == 1) {
                holder.tv_zprice.setText("?????? : ??" + new DecimalFormat("#0.00").format(Double.parseDouble(order.getOrder_price() + "")) + "(????????????" + order.getPostage() + ")");
            } else {
                holder.tv_zprice.setText("?????? : ??" + new DecimalFormat("#0.00").format(Double.parseDouble(order.getPay_money() + "")));
            }


            holder.cb_check.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    if (holder.cb_check.isChecked()) {
                        onCallback.checked(position, true);
                        isChecked.put(position, true);
                    } else {
                        onCallback.checked(position, false);
                        // isChecked.remove(position);
                        isChecked.put(position, false);
                    }
                }
            });

            if (isChecked.get(position) == null) {
                holder.cb_check.setChecked(false);
            } else {
                holder.cb_check.setChecked(isChecked.get(position));
            }
        }

        return convertView;
    }

    // ??????checkbox??????
    public void setSelected(boolean isSelectedAll) {
        if (isSelectedAll) {
            for (int i = 0; i < listData.size(); i++) {
                isChecked.put(i, true);
            }
        } else {
            for (int i = 0; i < listData.size(); i++) {
                isChecked.put(i, false);
            }
        }
    }

    private void addView(List<OrderShop> list, LinearLayout container, LayoutInflater inflater, final Order order) {
        LogYiFu.e("TAG", "????????????");
        // double itemAccount = 0;
        container.removeAllViews();

        if (order.getShop_from() == 1 || order.getShop_from() == 4 || order.getShop_from() == 6) {
            View view = inflater.inflate(R.layout.listview_orderlist_son, null);
            ImageView img_product = (ImageView) view.findViewById(R.id.img_product1);
            TextView tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            TextView tv_shop_num = (TextView) view.findViewById(R.id.tv_shop_num);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);

            ((TextView) view.findViewById(R.id.tv_status)).setText("?????????");

            tv_product_name.setText(order.getOrder_name());
            if (order.getShop_from() == 1) {
                tv_shop_num.setText("x1");
            } else {
                int num = 1;
                try {
                    num = order.getShop_num();
                } catch (Exception e) {
                }
                tv_shop_num.setText("x" + num);
            }

            tv_price.setText(
                    "??" + new java.text.DecimalFormat("#0.#").format((order.getOrder_price() - order.getPostage())));

            String pic;
            if (order.getShop_from() == 1) {
                pic = order.getOrder_pic();
            } else {
                pic = YUrl.imgurl + order.getBak().substring(1, 4) + "/" + order.getBak() + "/"
                        + (String) order.getOrder_pic();
            }

            // MyLogYiFu.e("TAG", "pic=" + orderShop.getShop_pic() + ",");

            TextView tvColor = (TextView) view.findViewById(R.id.tv_product_color);
            TextView tvSize = (TextView) view.findViewById(R.id.tv_product_size);
            tvColor.setVisibility(View.GONE);
            tvSize.setVisibility(View.GONE);
            TextView meal = (TextView) view.findViewById(R.id.meal);
            meal.setVisibility(View.VISIBLE);
            if (list.size() == 1) {
                meal.setText("????????????");
            }
            img_product.setTag(pic);
            if (!TextUtils.isEmpty(pic)) {
//				SetImageLoader.initImageLoader(context, img_product, pic, "");
                PicassoUtils.initImage(context, pic, img_product);
            }


            container.addView(view);
        } else {
            for (OrderShop orderShop : list) {
                View view = inflater.inflate(R.layout.listview_orderlist_son, null);
                ImageView img_product = (ImageView) view.findViewById(R.id.img_product1);
                TextView tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
                TextView tv_shop_num = (TextView) view.findViewById(R.id.tv_shop_num);
                TextView tv_price = (TextView) view.findViewById(R.id.tv_price);

                TextView tv_dikou = (TextView) view.findViewById(R.id.tv_dikou);//??????????????????????????????
                final ImageView iv_wenhao = (ImageView) view.findViewById(R.id.iv_wenhao); //??????



                TextView tv_yufahuo =view.findViewById(R.id.tv_yufahuo);


                if (order.getAdvance_sale_days() > 0) {
                    tv_yufahuo.setText("????????????????????????" + order.getAdvance_sale_days() + "??????");
                    tv_yufahuo.setVisibility(View.VISIBLE);
                } else {
                    tv_yufahuo.setVisibility(View.GONE);
                }



                if (order.getShop_from() == 10) {//1????????????

                    if(order.getPay_status() != 0 && order.getWhether_prize() == 1){ //??????????????????????????????
                        tv_dikou.setText("????????????????????????");
                        tv_dikou.setVisibility(View.VISIBLE);
                        iv_wenhao.setVisibility(View.VISIBLE);
                    }


                } else {//??????????????????????????????????????????0?????????0?????????

                    if (order.getOne_deductible() > 0) {

                        tv_dikou.setText("?????????" + order.getOne_deductible() + "???");

                        tv_dikou.setVisibility(View.VISIBLE);
                        iv_wenhao.setVisibility(View.VISIBLE);
                    } else {
                        tv_dikou.setVisibility(View.GONE);
                        iv_wenhao.setVisibility(View.GONE);
                    }


                }

                final int status = Integer.parseInt(order.getStatus().toString()); // ????????????:1?????????2?????????3?????????4?????????6?????????7????????????9????????????

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
                            DialogUtils.getDiKouDialogNew(context, "??????????????????", false, false);
                        } else {
                            DialogUtils.getDiKouDialogNew(context, "??????????????????", false, true);//?????????????????????

                        }
                    }
                });


                ((TextView) view.findViewById(R.id.tv_status)).setText("?????????");

                String pic = orderShop.getShop_code().substring(1, 4) + "/" + orderShop.getShop_code() + "/"
                        + orderShop.getShop_pic();
                LogYiFu.e("TAG", "pic=" + orderShop.getShop_pic() + ",");

                TextView tvColor = (TextView) view.findViewById(R.id.tv_product_color);
                TextView tvSize = (TextView) view.findViewById(R.id.tv_product_size);
                view.findViewById(R.id.meal).setVisibility(View.GONE);
                img_product.setTag(pic);
                if (!TextUtils.isEmpty(pic)) {
//					SetImageLoader.initImageLoader(context, img_product, pic, "");
                    PicassoUtils.initImage(context, pic, img_product);
                }
                String shop_name = orderShop.getShop_name(0);
                if (!TextUtils.isEmpty(shop_name)) {
                    tv_product_name.setText(shop_name);
                }

                if (null == orderShop.getColor()) {
                    tvColor.setVisibility(View.GONE);
                }
                if (null == orderShop.getSize()) {
                    tvSize.setVisibility(View.GONE);
                }
                tvColor.setText("??????-" + orderShop.getColor());
                tvSize.setText("??????-" + orderShop.getSize());
                String price = new java.text.DecimalFormat("#0.#").format(orderShop.getShop_price());
                tv_price.setText("??" + price);
                int num = orderShop.getShop_num();
                tv_shop_num.setText("x" + num);
                // 1?????????2?????????3?????????4?????????5?????????6?????????7????????????9????????????
                // itemAccount += num * orderShop.getShop_price();
                container.addView(view);
            }
        }
        // return itemAccount;
    }

    class ViewHolder {
        TextView tv_shop_name, tv_status, tv_sum, tv_zprice;
        Button btn_Contact_seller, btn_Cancel_Order, btn_Payment;// ???????????????
        Button btn_Remind_shipments;// ????????????
        Button btn_Extend_Receipt, btn_See_Logistics, btn_Confirm_receipt;// ???????????????
        Button btn_Delete_Orders, btn_Evaluation_Order;
        LinearLayout container_item, lay1, lay2, lay3, lay4, lay5, lay6, lay7, lay9;
        Button btn_Confirm_receipt1, btnExtend_Receipt;
        CheckBox cb_check;
        View vLine;
    }

    // ?????????????????????
    private void customDialog(final String order_code, final int position) {
        AlertDialog.Builder builder = new Builder(context);
        // ???????????????????????????
        View view = View.inflate(context, R.layout.payback_esc_apply_dialog, null);
        TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
        tv_des.setText("????????????????????????");

        Button ok = (Button) view.findViewById(R.id.ok);
        ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
        Button cancel = (Button) view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // ???????????????????????????
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // escOrder(order_code,position);
                // dialog.dismiss();

            }
        });

        dialog = builder.create();
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }

    // ????????????
    protected void freshAdapter(int position) {
        listData.remove(position);
        this.notifyDataSetChanged();
    }

    // /1?????????2?????????3?????????4?????????
    // 5?????????6?????????7????????????9????????????
    private void setVisibility(ViewHolder holder, int status, final Order order, final int position) {
        switch (status) {
            case 1:
                holder.lay1.setVisibility(View.VISIBLE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);

                holder.tv_status.setText("?????????");

                holder.btn_Contact_seller.setOnClickListener(new OnClickListener() { // ????????????

                    @Override
                    public void onClick(View v) {

//                        Intent intent = new Intent(context, ChatActivity.class);
//                        intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
//                        context.startActivity(intent);
                        WXminiAppUtil.jumpToWXmini(context);

                    }
                });

                holder.btn_Cancel_Order.setOnClickListener(new View.OnClickListener() {// ????????????

                    @Override
                    public void onClick(View v) {
                        final String order_code = order.getOrder_code();
                        //
                        cancleOrderDatas.clear();
                        getCancleOrderDatas();
                        // ?????????????????????-----------???????????????????????????
                        final SingleChoicePopupWindow menuWindow;
                        menuWindow = new SingleChoicePopupWindow(context, cancleOrderDatas);
                        menuWindow.setTitle("????????????");

                        // ????????????
                        menuWindow.showAtLocation(parentView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // ??????layout???PopupWindow??????????????????

                        menuWindow.setOnOKButtonListener(new OnClickListener() {

                            public void onClick(View v) {
                                menuWindow.dismiss();
                                String selItem = menuWindow.getSelectItem();
                                if (order.getShop_from() == 4 || order.getShop_from() == 6) {
                                    escOrderDuo(order_code, selItem, position);
                                } else
                                    escOrder(order_code, selItem, position);

                            }
                        });

                    }
                });

                holder.btn_Payment.setOnClickListener(new View.OnClickListener() {// ??????
                    // ????????????????????????

                    @Override
                    public void onClick(View v) {
//                        long nowTimes = new Date().getTime();
//                        long issueEndTime = order.getIssue_endtime();
//                        if ((order.getShop_from() == 4 || order.getShop_from() == 6) && issueEndTime != 0 && nowTimes >= issueEndTime) {
//                            ToastUtil.showShortText(context, "???????????????????????????");
//                            return;
//                        }
                        goToPay(order);
                        // freshAdapter(position);

                        // final String order_code = order.getOrder_code();
                        // WalletDialog dlgDialog = new
                        // WalletDialog(context,
                        // R.style.DialogStyle);
                        // dlgDialog.show();
                        // dlgDialog.listener = new
                        // WalletDialog.OnCallBackPayListener() {
                        //
                        // @Override
                        // public void selectPwd(String pwd) {
                        // walletPayOrder(order_code, pwd);
                        //
                        // }
                        // };
                    }
                });

                break;
            case 2:
                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.VISIBLE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);
                holder.tv_status.setText("?????????");
                holder.btn_Remind_shipments.setOnClickListener(new View.OnClickListener() {// ????????????(????????????)

                    @Override
                    public void onClick(View v) {
                        remainOrdershop(order.getOrder_code());
                    }
                });
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
                holder.tv_status.setText("?????????");
                holder.btn_Extend_Receipt.setOnClickListener(new View.OnClickListener() {// ????????????

                    @Override
                    public void onClick(View v) {
                        String order_code = order.getOrder_code();
                        extensionOrdershop(order_code);
                    }
                });

                holder.btn_See_Logistics.setOnClickListener(new View.OnClickListener() {// ????????????(???????????????????????????)

                    @Override
                    public void onClick(View v) {

                    }
                });
                holder.btn_Confirm_receipt.setOnClickListener(new View.OnClickListener() {// ????????????
                    // ????????????

                    @Override
                    public void onClick(View v) {
                        final String order_code = order.getOrder_code();
                        // WalletDialog dlgDialog = new
                        // WalletDialog(context,
                        // R.style.DialogStyle);
                        // dlgDialog.show();
                        // dlgDialog.listener = new
                        // WalletDialog.OnCallBackPayListener() {
                        //
                        // @Override
                        // public void selectPwd(String pwd) {
                        // affirmOrder(order_code, pwd);
                        //
                        // }
                        // };
                        PayPasswordCustomDialog customDialog = new PayPasswordCustomDialog(context, R.style.mystyle,
                                R.layout.pay_customdialog, "????????????", null);

                        InputDialogListener inputDialogListener = new InputDialogListener() {

                            @Override
                            public void onOK(String pwd) {
                                // ??????????????????????????????
                                // ToastUtil.showLongText(context, "??????????????????"
                                // + text);
                                affirmOrder(order_code, pwd);
                            }

                            @Override
                            public void onCancel() {
                                // TODO Auto-generated method stub

                            }

                        };
                        customDialog.setListener(inputDialogListener);
                        customDialog.show();

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
                holder.tv_status.setText("?????????");// ????????????
                holder.btn_Delete_Orders.setOnClickListener(new OnClickListener() {// ????????????

                    @Override
                    public void onClick(View v) {
                        String order_code = order.getOrder_code();
                        returnShop(order_code);
                    }
                });

                holder.btn_Evaluation_Order.setOnClickListener(new View.OnClickListener() {// ?????????????????????????????????

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, EvaluateOrderNewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("order", order);
                        intent.putExtras(bundle);
                        context.startActivity(intent);

                    }
                });
                break;
            case 5:// ?????????
                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.VISIBLE);

                holder.lay5.setVisibility(View.VISIBLE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);
                holder.tv_status.setText("?????????");
                break;
            case 6:// ?????????
                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.VISIBLE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.GONE);
                holder.tv_status.setText("????????????");
                break;
            case 7:// ????????????
                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.VISIBLE);
                holder.lay9.setVisibility(View.GONE);
                holder.tv_status.setText("????????????");
                // holder.btnExtend_Receipt
                // .setOnClickListener(new View.OnClickListener() {// ????????????
                //
                // @Override
                // public void onClick(View v) {
                // String order_code = order.getOrder_code();
                // extensionOrdershop(order_code);
                // }
                // });
                holder.btn_Confirm_receipt1.setOnClickListener(new View.OnClickListener() {// ????????????

                    @Override
                    public void onClick(View v) {
                        final String order_code = order.getOrder_code();
                        PayPasswordCustomDialog customDialog = new PayPasswordCustomDialog(context, R.style.mystyle,
                                R.layout.pay_customdialog, "????????????", null);

                        InputDialogListener inputDialogListener = new InputDialogListener() {

                            @Override
                            public void onOK(String pwd) {
                                // ??????????????????????????????
                                // ToastUtil.showLongText(context, "??????????????????"
                                // + text);
                                affirmOrder(order_code, pwd);
                                freshAdapter(position);
                            }

                            @Override
                            public void onCancel() {
                                // TODO Auto-generated method stub

                            }

                        };
                        customDialog.setListener(inputDialogListener);
                        customDialog.show();

                    }
                });
                break;
            case 9:// ????????????
                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.VISIBLE);
                holder.tv_status.setText("????????????");

                break;
            case 10:// ???????????????
                holder.lay1.setVisibility(View.GONE);
                holder.lay2.setVisibility(View.GONE);
                holder.lay3.setVisibility(View.GONE);
                holder.lay4.setVisibility(View.GONE);

                holder.lay5.setVisibility(View.GONE);
                holder.lay6.setVisibility(View.GONE);
                holder.lay7.setVisibility(View.GONE);
                holder.lay9.setVisibility(View.VISIBLE);
                holder.tv_status.setText("???????????????");

                break;


            default:
                break;
        }

    }

    // ?????????
    public void goToPay(Order order) {

		/*
         * List<OrderShop> list = order.getList(); if (list.size() == 1) {
		 * ShareUtil.getPicPath(list.get(0).getShop_code(), null,
		 * (FragmentActivity) context); } ArrayList<Order> listOrder = new
		 * ArrayList<Order>(); ArrayList<ShopCart> shopcats = new
		 * ArrayList<ShopCart>(); for (int i = 0; i < list.size(); i++) {
		 * OrderShop orderShop = list.get(i); ShopCart shopCart = new
		 * ShopCart(); shopCart.setSize(orderShop.getSize());
		 * shopCart.setColor(orderShop.getColor());
		 * shopCart.setShop_code(orderShop.getShop_code());
		 * shopCart.setShop_name(orderShop.getShop_name(0));
		 * shopCart.setShop_price(orderShop.getShop_price());
		 * shopCart.setShop_num(orderShop.getShop_num());
		 * shopCart.setShop_se_price(order.getOrder_price());
		 * shopCart.setSupp_id(order.getSupp_id()); MyLogYiFu.e("TAG", "?????????id=" +
		 * orderShop.getStocktypeid() + ",??????=" + orderShop.getShop_price());
		 * shopCart.setStock_type_id(orderShop.getStocktypeid());
		 * shopcats.add(shopCart); } Intent intent = new Intent(context,
		 * OrderPaymentActivity.class); Bundle bundle = new Bundle();
		 * HashMap<String, Object> hashMap = new HashMap<String, Object>();
		 * hashMap.put("order_code", order.getOrder_code());
		 * bundle.putSerializable("result", (Serializable) hashMap);
		 * bundle.putDouble("totlaAccount", order.getOrder_price());
		 * bundle.putSerializable("listGoods", (Serializable) shopcats);
		 * listOrder.add(order); bundle.putSerializable("listOrder",
		 * (Serializable) listOrder); intent.putExtras(bundle);
		 * intent.putExtra("isMulti", false);
		 */
        List<Order> orders = new ArrayList<Order>();
        orders.add(order);
        Intent intent = new Intent(context, OrderPaymentActivity.class);
        if (order.getShop_from() == 0) {//??????
            intent.putExtra("order_common", "order_common");
        } else if (order.getShop_from() == 1) {//??????
            intent.putExtra("order_special", "order_special");
        } else if (order.getShop_from() == 3) {
            intent.putExtra("signShopDetail", "SignShopDetail");
            intent.putExtra("signType", this.getSignType(order));
        } else if (order.getShop_from() == 4 || order.getShop_from() == 6) {
            intent.putExtra("isDuobao", true);
            intent.putExtra("signType", this.getSignType(order));
        }
        intent.putExtra("order_code", order.getOrder_code());
        intent.putExtra("totlaAccount", order.getRemain_money());
        intent.putExtra("isMulti", false);
        intent.putExtra("single", "single");
        Bundle bundle = new Bundle();
        bundle.putSerializable("listOrder", (Serializable) orders);
        intent.putExtras(bundle);

        ((Activity) context).startActivityForResult(intent, 101);
    }

    private int getSignType(Order order) {
        if (order.getShop_from() == 4 || order.getShop_from() == 3 || order.getShop_from() == 6) {
            String[] str = (String.valueOf(order.getOrder_price())).split("\\.");
            String type = str[0];
            return Integer.parseInt(type);
        } else {
            return 0;
        }
    }

    /****
     * ????????????
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

                if (e != null) {// ????????????
                    LogYiFu.e("?????? -----", context.getString(R.string.ss));
                    Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
                } else {// ???????????????????????????????????????
                    freshAdapter(position);

                    if (listData.size() == 0) { // ??????????????????????????????????????????
                        mFragment.showNoDataPage();
                    }
                    if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
                        // ??????????????????????????????
                        GetJinBiJinQuanUtils.getJinBi(context);
                        GetJinBiJinQuanUtils.getJinQuan(context);
                    }
                    SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
                    Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
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
     * ?????? ????????????
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

                if (e != null) {// ????????????
                    LogYiFu.e("?????? -----", context.getString(R.string.ss));
                    Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
                } else {// ???????????????????????????????????????
                    freshAdapter(position);

                    if (listData.size() == 0) { // ??????????????????????????????????????????
                        mFragment.showNoDataPage();
                    }
                    SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
                    Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
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
     * ??????????????????????????????
     *
     * @param order_code
     * @param pwd
     */
    private void affirmOrder(String order_code, String pwd) {
        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) context, null, R.string.wait) {
            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                return ComModel.affirmOrder(context, YCache.getCacheToken(context), params[0], params[1]);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo r, Exception e) {

                if (e != null) {// ????????????
                    LogYiFu.e("?????? -----", context.getString(R.string.ss));
                    Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
                } else {// ???????????????????????????????????????

                    int pwdflag = r.getPwdflag();
                    if (pwdflag == 0) {
                        Toast.makeText(context, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                    } else if (pwdflag == 1 || pwdflag == 2 || pwdflag == 3) {

                        String message = r.getMessage();
                        // customDialog.dismiss();
                        PayErrorDialog dialog = new PayErrorDialog(context, R.style.DialogStyle1, pwdflag, message);
                        dialog.show();
                    }

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
     * ????????????
     *
     * @param order_code
     */
    private void returnShop(String order_code) {
        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) context, null, R.string.wait) {
            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                return ComModel.returnShop(context, YCache.getCacheToken(context), params[0]);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo r, Exception e) {

                if (e != null) {// ????????????
                    LogYiFu.e("?????? -----", context.getString(R.string.ss));
                    Toast.makeText(context, "????????????", Toast.LENGTH_SHORT).show();
                } else {// ???????????????????????????????????????
                    Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
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
     * ????????????
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

                if (e != null) {// ????????????
                    LogYiFu.e("?????? -----", context.getString(R.string.ss));
                    Toast.makeText(context, R.string.ee, Toast.LENGTH_SHORT).show();
                } else {// ???????????????????????????????????????
                    Toast.makeText(context, "????????????????????????", Toast.LENGTH_SHORT).show();
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
     * ???????????????????????????
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

                if (e != null) {// ????????????
                    LogYiFu.e("?????? -----", context.getString(R.string.ss));
                    Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
                } else {// ???????????????????????????????????????
                    Toast.makeText(context, "????????????", Toast.LENGTH_SHORT).show();
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

    /******
     * ????????????
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

                if (e != null) {// ????????????
                    LogYiFu.e("?????? -----", context.getString(R.string.ss));
                    Toast.makeText(context, R.string.fh, Toast.LENGTH_SHORT).show();
                } else {// ??????????????????
                    if (info != null && "1".equals(info.getStatus()) && 1 == info.getFalg()) {
                        Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
                    } else if (info != null && "1".equals(info.getStatus()) && 2 == info.getFalg()) {
                        Toast.makeText(context, "????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
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


    /**
     * ??????????????????NEW
     */
    public void getDiKouDialogNew() {
        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_dikou_explian_new, null);
        ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });
        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(deleteDialog);
//        deleteDialog.show();

    }

}
