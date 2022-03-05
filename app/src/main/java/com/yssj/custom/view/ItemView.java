package com.yssj.custom.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.Shop;
import com.yssj.entity.VipDikouData;
import com.yssj.utils.PicassoUtils;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * 列表添加数据
 *
 * @author lbp
 */
public class ItemView extends LinearLayout {

    private Context context;

    private java.text.DecimalFormat pFormate;
    private java.text.DecimalFormat pFormate_new;

    private ImageView img_title;

    private TextView tv_pro_name, tv_supply_name;

    private TextView tv_price, tv_save, tv_no_discount_price;

    private TextView returnback, returnback_icon;

    private ImageView iv_love_star;

    private ImageView iv_star;

    private ImageView iv_shopcar;
    private TextView new_sub;//一元购品牌名


    private Drawable star_selected, star_unselected, shop_selected, shop_unselected;

    private TextView tv_discount, tv_sold;
    private boolean isHot;
    private boolean isManufacture;

    public boolean isManufacture() {
        return isManufacture;
    }

    public void setManufacture(boolean isManufacture) {
        this.isManufacture = isManufacture;
    }


    private int width;
    private TextView mYuanPrice;

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setIsHotShop(boolean isHot) {
        this.isHot = isHot;
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.pro_item, this);
        this.context = context;
        pFormate_new = new DecimalFormat("#0.0");
        pFormate = new DecimalFormat("#0");
        img_title = (ImageView) findViewById(R.id.img_title);
        tv_pro_name = (TextView) findViewById(R.id.tv_pro_name);
        tv_supply_name = (TextView) findViewById(R.id.tv_supply_name);
        new_sub = (TextView) findViewById(R.id.new_sub);
        tv_price = (TextView) findViewById(R.id.tv_price);
        returnback = (TextView) findViewById(R.id.returnback);
        returnback_icon = (TextView) findViewById(R.id.returnback_icon);
//		 iv_star = (ImageView) findViewById(R.id.iv_star);
//		 iv_love_star = (ImageView) 
//				findViewById(R.id.iv_love_star);
        mYuanPrice = (TextView) findViewById(R.id.tv_price_paint);
//		 iv_shopcar = (ImageView) findViewById(R.id.iv_shopcar);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_no_discount_price = (TextView) findViewById(R.id.tv_no_discount_price);
        setPadding(0, 0, 0, 3);
        star_selected = context.getResources().getDrawable(R.drawable.img_love_star_selected);
        star_unselected = context.getResources().getDrawable(R.drawable.img_love_star_default);
        shop_selected = context.getResources().getDrawable(R.drawable.img_shopcar_selected);
        shop_unselected = context.getResources().getDrawable(R.drawable.img_shopcar_default);
        width = context.getResources().getDisplayMetrics().widthPixels;

        tv_discount = (TextView) findViewById(R.id.tv_discount);
        tv_sold = (TextView) findViewById(R.id.tv_sold);
        tv_discount.setVisibility(View.GONE);
        tv_sold.setVisibility(View.GONE);
    }

    public ItemView(Context context) {
        super(context);
        initView(context);
    }

    public void setHeight(int height) {
//        img_title.getLayoutParams().height = height;
    }


    public void iniView(HashMap<String, Object> map) {
        if (width > 720) {
//			SetImageLoader.initImageLoader(context, img_title,
//					map.get("shop_code").toString().substring(1, 4)+"/"+map.get("shop_code").toString()+"/"+(String) map.get("def_pic"),"!382");
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!382", img_title);
        } else {
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!280", img_title);
//
//			SetImageLoader.initImageLoader(context, img_title,
//					map.get("shop_code").toString().substring(1, 4)+"/"+map.get("shop_code").toString()+"/"+(String) map.get("def_pic"),"!280");
        }
        double shop_price = Double.parseDouble((String) map.get("shop_price"));//原价
        double price = Double.parseDouble((String) map.get("shop_se_price"));//售价


//        if (isHot || ShopDetailsActivity.titleCheck == 0) {//当前是热卖 就显示折扣和已售
//            tv_discount.setVisibility(View.VISIBLE);
//            tv_sold.setVisibility(View.VISIBLE);
//            tv_discount.setText(new java.text.DecimalFormat("#0.0").format((price / shop_price) * 10) + "折");
//            String virtual_sales = (String) (map.get("virtual_sales"));
//            tv_sold.setText("已售" + virtual_sales + "件");
//        } else {
        tv_discount.setVisibility(View.GONE);
        tv_sold.setVisibility(View.GONE);
//        }


        if (isManufacture) {
            returnback.setVisibility(View.GONE);
            returnback_icon.setVisibility(View.GONE);
        }

        tv_pro_name.setText(Shop.getShopNameStrNew((String) map.get("shop_name")));
        if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
            tv_supply_name.setVisibility(View.VISIBLE);
            tv_supply_name.setText("" + map.get("supp_label") + "制造商出品");
        } else {
            tv_supply_name.setVisibility(View.GONE);
        }

//		tv_price.setText("¥" + pFormate_new.format(price));
//		double yuanPrice =Double.parseDouble((String) map.get("shop_price"));
//		mYuanPrice.setText("¥" + pFormate.format(yuanPrice));
        mYuanPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        double kickBack = 0;
        if (!TextUtils.isEmpty((String) map.get("kickback"))) {
            kickBack = Double.parseDouble((String) map.get("kickback"));
        }

//		returnback.setText(pFormate.format(price*kickBack) + "\n返佣");
//		returnback.setText(Html.fromHtml(context.getString(R.string.tv_kick_back,pFormate.format(kickBack))));

        tv_price.setText("¥" + pFormate_new.format(price));
        returnback.setText((int) kickBack + "元");

//		double save = shop_price - price;
//		if(save>0){
//			tv_save.setText("立省" + pFormate_new.format(save)+"元");
//			tv_save.setVisibility(View.VISIBLE);
//		}else{
//			tv_save.setVisibility(View.GONE);
//		}
        if (shop_price > 0) {
            String no_discount_price = "专柜价¥" + pFormate_new.format(shop_price);
//			ToastUtil.addStrikeSpan(tv_no_discount_price,no_discount_price);
            tv_save.setText(no_discount_price);
            tv_save.setVisibility(View.VISIBLE);
        } else {
            tv_save.setVisibility(View.GONE);
        }
        tv_save.setTextSize(13);
        tv_save.setTextColor(Color.parseColor("#A8A8A8"));

        tv_no_discount_price.setText("返" + pFormate_new.format(price) + "元=0元");
        tv_no_discount_price.setVisibility(View.VISIBLE);
        tv_no_discount_price.setTextSize(11);
        tv_no_discount_price.setTextColor(Color.parseColor("#FB3B3B"));


        tv_supply_name.setVisibility(GONE);


        if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
            new_sub.setVisibility(View.VISIBLE);
            new_sub.setText("" + map.get("supp_label"));
        } else {
            new_sub.setVisibility(View.INVISIBLE);
        }


        tv_no_discount_price.setVisibility(GONE);
        tv_save.setVisibility(VISIBLE);
        returnback.setVisibility(GONE);


//            if (shop_price > 0) {
//                String no_discount_price = "原价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
////			ToastUtil.addStrikeSpan(tv_no_discount_price,no_discount_price);
//                tv_save.setText(no_discount_price);
//                tv_save.setVisibility(View.VISIBLE);
//            } else {
//                tv_save.setVisibility(View.GONE);
//            }


//        tv_save.setText("原价¥" + new java.text.DecimalFormat("#0.0").format(price));


        int virtual_sales = 0;

        try {
            virtual_sales = Integer.parseInt((String) map.get("virtual_sales"));// 销量

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (virtual_sales > 0) {
            String no_discount_price = "月销" + virtual_sales + "件";


//			ToastUtil.addStrikeSpan(tv_no_discount_price,no_discount_price);
            tv_save.setText(no_discount_price);
            tv_save.setVisibility(View.VISIBLE);
        } else {
            tv_save.setVisibility(View.GONE);
        }


//			tv_price.setText("¥"+GuideActivity.oneShopPrice);


        String onPrice = map.get("app_shop_group_price") + "";
        onPrice = new java.text.DecimalFormat("#0.0")
                .format(Double.parseDouble(onPrice));
        tv_price.setText("¥"+onPrice );


//		int isLike = (Integer) map.get("isLike");
//		if (isLike == 0) {
//			iv_love_star.setImageDrawable(star_unselected);
//		}else{
//			iv_love_star.setImageDrawable(star_selected);
//		}
//		int isCart = (Integer) map.get("isCart");
//		if (isCart > 0) {
//			iv_shopcar.setImageDrawable(shop_selected);
//		}else{
//			iv_shopcar.setImageDrawable(shop_unselected);
//		}
    }

    public void iniViewVip(HashMap<String, Object> map, VipDikouData vipDikouData) {
        if (width > 720) {
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!382", img_title);
        } else {
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!280", img_title);
        }
        double shop_price = Double.parseDouble((String) map.get("shop_price"));//原价
        double price = Double.parseDouble((String) map.get("shop_se_price"));//售价


        tv_discount.setVisibility(View.GONE);
        tv_sold.setVisibility(View.GONE);


        if (isManufacture) {
            returnback.setVisibility(View.GONE);
            returnback_icon.setVisibility(View.GONE);
        }

        tv_pro_name.setText(Shop.getShopNameStrNew((String) map.get("shop_name")));
        if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
            tv_supply_name.setVisibility(View.VISIBLE);
            tv_supply_name.setText("" + map.get("supp_label") + "制造商出品");
        } else {
            tv_supply_name.setVisibility(View.GONE);
        }


        mYuanPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        double kickBack = 0;
        if (!TextUtils.isEmpty((String) map.get("kickback"))) {
            kickBack = Double.parseDouble((String) map.get("kickback"));
        }

        tv_price.setText("¥" + pFormate_new.format(price));
        returnback.setText((int) kickBack + "元");


        if (shop_price > 0) {
            String no_discount_price = "专柜价¥" + pFormate_new.format(shop_price);
            tv_save.setText(no_discount_price);
            tv_save.setVisibility(View.VISIBLE);
        } else {
            tv_save.setVisibility(View.GONE);
        }
        tv_save.setTextSize(13);
        tv_save.setTextColor(Color.parseColor("#A8A8A8"));

        tv_no_discount_price.setText("返" + pFormate_new.format(price) + "元=0元");
        tv_no_discount_price.setVisibility(View.VISIBLE);
        tv_no_discount_price.setTextSize(11);
        tv_no_discount_price.setTextColor(Color.parseColor("#FB3B3B"));


        tv_supply_name.setVisibility(GONE);


        if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
            new_sub.setVisibility(View.VISIBLE);
            new_sub.setText("" + map.get("supp_label"));
        } else {
            new_sub.setVisibility(View.INVISIBLE);
        }


        tv_no_discount_price.setVisibility(GONE);
        tv_save.setVisibility(VISIBLE);
        returnback.setVisibility(GONE);


        int virtual_sales = 0;

        try {
            virtual_sales = Integer.parseInt((String) map.get("virtual_sales"));// 销量

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (virtual_sales > 0) {
            String no_discount_price = "月销" + virtual_sales + "件";


            tv_save.setText(no_discount_price);
            tv_save.setVisibility(View.VISIBLE);
        } else {
            tv_save.setVisibility(View.GONE);
        }


        tv_price.setText("会员价元");
        double prices = (double) 0;
        double vipPrice;//实际显示的会员价
        String a = (String) map.get("shop_se_price");
        if (a.equals("null")) {
            prices = (double) 0;
        } else {

            prices = Double.parseDouble((String) map.get("shop_se_price"));

        }
        double sePrice = prices * 0.95;
        double dikou = prices * 0.9;
        if (vipDikouData.getOne_not_use_price() >= dikou) {
            if (vipDikouData.getMaxType() == 6) {
                vipPrice = sePrice - dikou;
            } else {
                vipPrice = prices - dikou;

            }
        } else {

            if (vipDikouData.getMaxType() == 6) {
                vipPrice = sePrice - vipDikouData.getOne_not_use_price();
            } else {
                vipPrice = prices - vipDikouData.getOne_not_use_price();

            }

        }

        tv_price.setText("¥" + new java.text.DecimalFormat("#0.0").format(vipPrice));


//        String onPrice = map.get("app_shop_group_price")+"";
//        onPrice = new java.text.DecimalFormat("#0.0")
//                .format(Double.parseDouble(onPrice));
//        tv_price.setText(onPrice + "元");


    }

}
