package com.yssj.custom.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.MealShopList;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.Shop;
import com.yssj.entity.VipDikouData;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.shopdetails.MealShopDetailsActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.StringUtils;

import java.text.DecimalFormat;

public class NewMealItemView extends LinearLayout {

    private Context context;
    private TextView name;
    private TextView price, tv_save;
    private ImageView image;
    private int width;
    private  double vipPrice;


    public NewMealItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.itemview_new_meal, this);
        name = (TextView) findViewById(R.id.news_title);
        price = (TextView) findViewById(R.id.tv_price);
        image = (ImageView) findViewById(R.id.news_pic);
        tv_save = (TextView) findViewById(R.id.tv_save);
        width = context.getResources().getDisplayMetrics().widthPixels;
    }


    public void initViewVip(final MealShopList.PListBean.ShopListBean map, final VipDikouData vipDikouData, boolean isVip) {
        String shopCode = map.getShop_code();
        String defPic = map.getFour_pic();

        image.setTag(shopCode.toString().substring(1, 4) + "/" + shopCode.toString() + "/" + (String) defPic);
        if (width > 720) {
            PicassoUtils.initImage(context,  defPic + "!382", image);
        } else {
            PicassoUtils.initImage(context,  defPic + "!280", image);

        }
        double prices;
        name.setText(Shop.getShopNameStrNew(map.getShop_name()));
        String a = map.getShop_se_price();


        if(null == a || StringUtils.isEmpty(a)) {
            prices = (double) 0;
        }else {
            prices = Double.parseDouble(map.getShop_price());

        }


        price.setText("原价¥" + new DecimalFormat("#0.0").format(prices) + "元");
        double shop_price = Double.parseDouble(map.getShop_price());//原价
        if (shop_price > 0) {
            String no_discount_price = "专柜价¥" + new DecimalFormat("#0.0").format(shop_price);
            tv_save.setText(no_discount_price);
            tv_save.setVisibility(View.VISIBLE);
        } else {
            tv_save.setVisibility(View.GONE);
        }
        tv_save.setTextSize(10);
        tv_save.setTextColor(Color.parseColor("#A8A8A8"));


        tv_save.setVisibility(VISIBLE);
        String onPrice = map.getApp_shop_group_price();
        onPrice = new DecimalFormat("#0.0")
                .format(Double.parseDouble(onPrice));
        price.setText(onPrice + "元");
//        if (vipDikouData != null && vipDikouData.getIsVip().equals("1")) { //会员的处理
//
//            if(YJApplication.instance.isLoginSucess()){
//
//                double shopSePrice = Double.parseDouble(map.getShop_se_price());
//                double sePrice = shopSePrice * 0.95;
//                double dikou = shopSePrice * 0.9;
//
//                if (vipDikouData.getOne_not_use_price() >= dikou) {
//                    if(vipDikouData.getMaxType() == 6){
//                        vipPrice =  sePrice - dikou;
//                    }else{
//                        vipPrice =  prices - dikou;
//
//                    }
//                }else{
//
//                    if(vipDikouData.getMaxType() == 6){
//                        vipPrice =  sePrice - vipDikouData.getOne_not_use_price();
//                    }else{
//                        vipPrice =  shopSePrice - vipDikouData.getOne_not_use_price();
//
//                    }
//
//                }
//
//                price.setText("¥" +new java.text.DecimalFormat("#0.0").format(vipPrice));
//            }else{
//
//
//
//                price.setText("¥" +map.getShop_se_price()+"元");
//
//
//            }



        if (vipDikouData != null && vipDikouData.getIsVip() > 0) { //会员的处理
            price.setText("会员价元");

            double shopSePrice = Double.parseDouble(map.getShop_se_price());

            double sePrice = shopSePrice * 0.95;
            double dikou = shopSePrice * 0.9;

            if (vipDikouData.getOne_not_use_price() >= dikou) {
                if(vipDikouData.getMaxType() == 6){
                    vipPrice =  sePrice - dikou;
                }else{
                    vipPrice =  shopSePrice - dikou;

                }
            }else{

                if(vipDikouData.getMaxType() == 6){
                    vipPrice =  sePrice - vipDikouData.getOne_not_use_price();
                }else{
                    vipPrice =  shopSePrice - vipDikouData.getOne_not_use_price();

                }

            }

            price.setText("¥" +new java.text.DecimalFormat("#0.0").format(vipPrice));

        }





//        }


//        tv_save.setText("原价¥" + new java.text.DecimalFormat("#0.0").format(prices) + "元");

        tv_save.setText("月销" + map.getMealVirtual_sales()+ "件");


//        if (!GuideActivity.show1yuan) { //非1元购处理  --特卖只有单独购买
//            price.setText("¥" + new DecimalFormat("#0.0").format(prices));
//            String no_discount_price = "原价¥" + new DecimalFormat("#0.0").format(shop_price);
//            tv_save.setText(no_discount_price);
//        }

        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ForceLookActivity forceLookActivity = new ForceLookActivity();
                forceLookActivity.num_tongji = forceLookActivity.num_tongji + 1;
                if (NewMealItemView.this.getVisibility() != View.VISIBLE) {
                    return;
                }
                addScanDataTo(map.getShop_code());
                Intent intent = new Intent(context, ShopDetailsActivity.class);
                intent.putExtra("isNewMeal", true);
                intent.putExtra("code", map.getShop_code());
                FragmentActivity activity = (FragmentActivity) context;
                activity.startActivity(intent);
                ((Activity) context).

                        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

            }
        });
    }


    /*
     * 把浏览过的数据添加进数据库
     */
    private void addScanDataTo(final String shop_code) {
        new SAsyncTask<Void, Void, ReturnInfo>(((FragmentActivity) context)) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context,
                                                Void... params) throws Exception {
                return ComModel.addMySteps(context, shop_code);
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         ReturnInfo result) {
                super.onPostExecute(context, result);
            }

        }.execute();
    }

}
