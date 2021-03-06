package com.yssj.ui.fragment.orderinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.Order;
import com.yssj.entity.OrderShop;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.infos.LogisticsInfoActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderListSonAdapter extends BaseAdapter {
    private Context context;
    private List<OrderShop> list;
//	private DisplayImageOptions options;

    private boolean flag;
    private FragmentActivity activity;

    private static String picUrl;
    private int shop_from;

    private Order order;
    private List<Order> orderlist;
    private OrderShop orderShop;

    private double price;
    private String shop_code;
    private boolean huodongFlag = false;

    public OrderListSonAdapter(Context context, Order order, boolean flag, boolean huodongFlag, String shop_code) {
        this.context = context;
        this.order = order;
        this.list = order.getList();
        this.orderlist = order.getOrderlist();
        this.huodongFlag = huodongFlag;
        this.shop_code = shop_code;
        if (1 == order.getShop_from()) {
            this.list = new ArrayList<OrderShop>();
            this.list.add(order.getList().get(0));

        }

        if (4 == order.getShop_from() || 6 == order.getShop_from()) {
            this.orderlist = new ArrayList<Order>();
            // this.orderlist.add(order.getOrderlist().get(0));

        }

        this.flag = flag;// ???????????????????????????????????????
        this.activity = (BasicActivity) context;

    }

    @Override
    public int getCount() {
        int a = order.getShop_from();

        if (4 == order.getShop_from() || 6 == order.getShop_from()) {
            return 1;
        } else {
            return list.size();
        }

    }

    @Override
    public Object getItem(int position) {
        if (4 == order.getShop_from() || 6 == order.getShop_from()) {
            return orderlist.get(position);
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_orderlist_son, null);

            holder.img_product = convertView.findViewById(R.id.img_product1);
            holder.tv_product_name = convertView.findViewById(R.id.tv_product_name);
            holder.tv_shop_num = convertView.findViewById(R.id.tv_shop_num);
            holder.tv_price = convertView.findViewById(R.id.tv_price);
            holder.tv_product_color = convertView.findViewById(R.id.tv_product_color);
            holder.tv_product_size = convertView.findViewById(R.id.tv_product_size);
            holder.meal = convertView.findViewById(R.id.meal);
            holder.tv_text_yuanjia = convertView.findViewById(R.id.tv_text_yuanjia);
            holder.tv_yufahuo = convertView.findViewById(R.id.tv_yufahuo);

            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }
        // final OrderShop orderShop = null;

        if (list == null) {

        } else {
            if (list.size() != 0) {
                orderShop = list.get(position);
            }
        }


        if (order.getAdvance_sale_days() > 0) {
            holder.tv_yufahuo.setText("????????????????????????" + order.getAdvance_sale_days() + "??????");
            holder.tv_yufahuo.setVisibility(View.VISIBLE);
        } else {
            holder.tv_yufahuo.setVisibility(View.GONE);
        }

        // int status = orderShop.getStatus();
        // 1?????????2?????????3?????????4?????????5?????????6?????????7????????????9????????????
        int a = order.getShop_from();
        LogYiFu.e("iiiiiii", a + "");
        if (1 == order.getShop_from()) {

            String picUrl = order.getOrder_pic();

            if (!TextUtils.isEmpty(picUrl)) {
//				SetImageLoader.initImageLoader(context, holder.img_product, picUrl, "");
                PicassoUtils.initImage(context, picUrl, holder.img_product);
            }
            String shop_name = order.getOrder_name();

            if (!TextUtils.isEmpty(shop_name)) {
                holder.tv_product_name.setText(shop_name);
            }
            // double price = orderShop.getShop_price();
            double price = 0.00;
            if (null != order.getPostage())
                price = order.getOrder_price() - order.getPostage();
            holder.tv_price.setText("??" + new java.text.DecimalFormat("#0.00").format(price));
            int num = orderShop.getShop_num();
            holder.tv_shop_num.setText("x" + num);
            holder.meal.setVisibility(View.VISIBLE);
            holder.tv_product_color.setVisibility(View.GONE);
            holder.tv_product_size.setVisibility(View.GONE);
            if (order.getList().size() == 1) {
                holder.meal.setText("????????????");
            } else {
                holder.meal.setText("????????????");
            }
        } else if (4 == order.getShop_from() || 6 == order.getShop_from()) {
            // String picUrl = order.getOrder_pic();
            String picUrl = order.getBak().substring(1, 4) + "/" + order.getBak() + "/" + order.getOrder_pic();
            if (!TextUtils.isEmpty(picUrl)) {
//				SetImageLoader.initImageLoader(context, holder.img_product, picUrl, "");
                PicassoUtils.initImage(context, picUrl, holder.img_product);
            }
            String shop_name = order.getOrder_name();

            if (!TextUtils.isEmpty(shop_name)) {
                holder.tv_product_name.setText(shop_name);
            }
            // double price = orderShop.getShop_price();
            double price = 0.00;
            if (null != order.getPostage())
                price = order.getOrder_price() - order.getPostage();
            holder.tv_price.setText("??" + new java.text.DecimalFormat("#0.00").format(price));
            int num = 1;
            try {
                num = order.getShop_num();
            } catch (Exception e) {

            }
//			holder.tv_shop_num.setText("x1");
            holder.tv_shop_num.setText("x" + num);
            holder.meal.setVisibility(View.GONE);
            holder.tv_product_color.setVisibility(View.GONE);
            holder.tv_product_size.setVisibility(View.GONE);
            // if(order.getList().size() == 1){
            // holder.meal.setText("????????????");
            // }else{
            // holder.meal.setText("????????????");
            // }

        } else {
            if (orderShop != null) {
                String picUrl = orderShop.getShop_code().substring(1, 4) + "/" + orderShop.getShop_code() + "/"
                        + orderShop.getShop_pic();

                if (!TextUtils.isEmpty(picUrl)) {
//					SetImageLoader.initImageLoader(context, holder.img_product, picUrl, "");
                    PicassoUtils.initImage(context, picUrl, holder.img_product);
                }
                String shop_name = orderShop.getShop_name(0);

                if (!TextUtils.isEmpty(shop_name)) {
                    holder.tv_product_name.setText(shop_name);
                }
                double price = orderShop.getShop_price();

                holder.tv_price.setText("??" + new java.text.DecimalFormat("#0.00").format(price));
                int num = orderShop.getShop_num();
                holder.tv_shop_num.setText("x" + num);


                if (null == orderShop.getColor()) {
                    holder.tv_product_color.setVisibility(View.GONE);
                }

                if (null == orderShop.getSize()) {
                    holder.tv_product_size.setVisibility(View.GONE);
                }

                holder.tv_product_color.setText("?????????" + orderShop.getColor());
                holder.tv_product_size.setText("?????????" + orderShop.getSize());
                // 1?????????2?????????3?????????4?????????5?????????6?????????7????????????9????????????

            }
        }

        if (orderShop.getOriginal_price() >= 0) {
            holder.tv_text_yuanjia.setVisibility(View.VISIBLE);
            holder.tv_price.setText("???" + list.get(0).getOriginal_price());
        }

        if (order.getShop_from() == 4 || 6 == order.getShop_from()) {
            convertView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
//					ToastUtil.showShortText(context, "???????????????????????????????????????????????????");
                }
            });
        }
        if (order.getShop_from() == 3) {
            holder.img_product.setClickable(false);
            convertView.setClickable(false);
            return convertView;
        }
        // holder.img_product.setOnClickListener(new View.OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // Intent intent = new Intent(context, ShopDetailsActivity.class);
        // intent.putExtra("code", orderShop.getShop_code());
        // if(1 == order.getShop_from()){
        // intent.putExtra("code", order.getBak());
        // intent.putExtra("isMeal", true);
        // }
        // ((Activity) context).startActivityForResult(intent, 1002);
        // ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
        // R.anim.slide_left_out);;
        // // Toast.makeText(context, "" + position,
        // // Toast.LENGTH_SHORT).show();
        //
        // }
        // });
        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(order.getNew_free() == 13){
                    return;
                }

                if (huodongFlag) {//?????????????????????????????????????????????
                    Intent intent = new Intent(context, ShopDetailsActivity.class);
                    intent.putExtra("code", "" + shop_code);
//                    intent.putExtra("isSignActiveShop", true);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                } else if (order.getShop_from() == 5 || order.getShop_from() == 7) {//????????????
                    Intent intent = new Intent(context, ShopDetailsActivity.class);
                    intent.putExtra("code", order.getOrderShops().get(position).get("shop_code").toString());
//                    intent.putExtra("isSignActiveShop", true);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                } else if (order.getShop_from() == 1) {
                    ToastUtil.showShortText(context, "???????????????");
                } else {
                    if (order.getShop_from() == 4 || 6 == order.getShop_from()) {
//						ToastUtil.showLongText(context, "???????????????????????????????????????????????????");
                        return;
                    }

                    Intent intent = new Intent(context, ShopDetailsActivity.class);

                    String shop_code = order.getOrderShops().get(position).get("shop_code").toString();
                    intent.putExtra("code", shop_code); // ???????????????????????????

                    if (1 == order.getShop_from()) {
                        intent.putExtra("code", order.getBak());
                        intent.putExtra("isMeal", true);
                    }
                    ((Activity) context).startActivityForResult(intent, 1002);
                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                    ;
                }
            }
        });

        return convertView;
    }

    // ?????????????????? ????????????????????????????????????
    private void chakanLogistics(View v, final OrderShop orderShop) {
        new SAsyncTask<Void, Void, HashMap<String, Object>>(activity, v, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                // TODO Auto-generated method stub
                return ComModel2.getLogisticsInfo(context, orderShop.getOrder_code(), orderShop.getShop_code());
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                // TODO Auto-generated method stub
                if (null == e) {
                    if (null != result) {

                        Intent intent = new Intent(activity, LogisticsInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("result", result);
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    } else
                        ToastUtil.showShortText(context, "????????????");
                }
                super.onPostExecute(context, result, e);
            }

        }.execute();
    }

    // ???????????????????????????
    private void affirmOrder(View v, final OrderShop orderShop) {
        WalletDialog dlgDialog = new WalletDialog(context, R.style.DialogStyle);
        dlgDialog.show();
        dlgDialog.listener = new WalletDialog.OnCallBackPayListener() {

            @Override
            public void selectPwd(String pwd) {
                affirmOrder(orderShop, pwd);

            }
        };
    }

    // ????????????
    private void affirmOrder(final OrderShop orderShop, final String pwd) {
        new SAsyncTask<Void, Void, ReturnInfo>(activity, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                // TODO Auto-generated method stub
                return ComModel2.affirmOrder(context, orderShop.getOrder_code(), pwd, orderShop.getId());
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                // TODO Auto-generated method stub
                if (null == e) {
                    ToastUtil.showShortText(context, result.getMessage());
                }
                super.onPostExecute(context, result, e);
            }

        }.execute();
    }

    class Holder {
        TextView tv_text_yuanjia, tv_yufahuo;
        ImageView img_product; // ????????????
        TextView tv_product_name, tv_price, tv_shop_num; // ??????????????????????????????????????????

        TextView tv_product_color, tv_product_size;
        TextView meal;

    }

}
