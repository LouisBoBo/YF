package com.yssj.custom.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.Shop;
import com.yssj.entity.VipDikouData;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.YunYingTongJi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultItemView extends LinearLayout {
    private int fg_new = -2;

    private Context context;

    private TextView name;

    private CheckBox box;

    private TextView price, tv_save, tv_no_discount_price;

    private TextView returnMoney;

    private ImageView image;

    private int width;

    private ImageView ivShopCart;

    private ImageView ivLike;

    private String where = "";
    private TextView tv_supply_name;
    private ImageView iv_selector;
    private boolean isInviteHot = false;
    private HashMap<String, Object> mHashMap;

    private TextView new_sub;//一元购品牌名


    public void setInviteHot(boolean isInviteHot) {
        this.isInviteHot = isInviteHot;
    }

    public ResultItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.myfave_infos_list_old, this);
        tv_supply_name = (TextView) findViewById(R.id.tv_supply_name);
        name = (TextView) findViewById(R.id.news_title);

        price = (TextView) findViewById(R.id.tv_price);
        // iv_selector=(ImageView) findViewById(R.id.iv_selector);
        // if(isInviteHot){
        // iv_selector.setVisibility(View.VISIBLE);
        // }else{
        // iv_selector.setVisibility(View.GONE);
        // }
        box = (CheckBox) findViewById(R.id.cb_favor);

        returnMoney = (TextView) findViewById(R.id.nickback);

        image = (ImageView) findViewById(R.id.news_pic);

        new_sub = (TextView) findViewById(R.id.new_sub);


        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_no_discount_price = (TextView) findViewById(R.id.tv_no_discount_price);

        // ivShopCart = (ImageView) findViewById(R.id.iv_shopcar);
        // ivLike = (ImageView) findViewById(R.id.iv_love_star);

        width = context.getResources().getDisplayMetrics().widthPixels;
    }

    public void initView(final HashMap<String, Object> map, final String id, String isWhere, final boolean mIsFabu) {
        /** 这里赋值 */
        mHashMap = map;
        where = isWhere + "";

        image.getLayoutParams().height = width / 2 * 900 / 600;

        image.setTag(map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/"
                + (String) map.get("def_pic"));
        if (width > 720) {

            // SetImageLoader.initImageLoader(null, image,
            // map.get("shop_code").toString().substring(1, 4) + "/"
            // + map.get("shop_code").toString() + "/" + (String)
            // map.get("def_pic"), "!382");

            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/"
                    + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!382", image);

        } else {
            // SetImageLoader.initImageLoader(null, image,
            // map.get("shop_code").toString().substring(1, 4) + "/"
            // + map.get("shop_code").toString() + "/" + (String)
            // map.get("def_pic"), "!280");

            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/"
                    + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!280", image);

        }
        if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
            tv_supply_name.setVisibility(View.VISIBLE);
            tv_supply_name.setText("" + map.get("supp_label") + "制造商出品");
        } else {
            tv_supply_name.setVisibility(View.GONE);
        }
        name.setText(Shop.getShopNameStrNew((String) map.get("shop_name")));
        double prices = Double.parseDouble((String) map.get("shop_se_price"));
        double kickBack = Double.parseDouble((String) map.get("kickback"));
        price.setText("¥" + new java.text.DecimalFormat("#0.0").format(prices));
        returnMoney.setText("" + new java.text.DecimalFormat("#0").format(kickBack) + "元");

        double shop_price = Double.parseDouble((String) map.get("shop_price"));// 原价
//		double save = shop_price - prices;
//		if (save > 0) {
//			tv_save.setText("立省" + new java.text.DecimalFormat("#0.0").format(save) + "元");
//			tv_save.setVisibility(View.VISIBLE);
//		} else {
//			tv_save.setVisibility(View.GONE);
//		}
//		if (shop_price > 0) {
//			String no_discount_price = "专柜价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
//			// ToastUtil.addStrikeSpan(tv_no_discount_price,no_discount_price);
//			tv_save.setText(no_discount_price);
//			tv_save.setVisibility(View.VISIBLE);
//		} else {
//			tv_save.setVisibility(View.GONE);
//		}

        tv_save.setText("原价¥" + new java.text.DecimalFormat("#0.0").format(prices));


        tv_save.setTextSize(13);
        tv_save.setTextColor(Color.parseColor("#A8A8A8"));

        tv_no_discount_price.setText("返" + new java.text.DecimalFormat("#0.0").format(prices) + "元=0元");
        tv_no_discount_price.setVisibility(View.VISIBLE);
        tv_no_discount_price.setTextSize(11);
        tv_no_discount_price.setTextColor(Color.parseColor("#FB3B3B"));
        // returnMoney.setText(Html.fromHtml(context.getString(R.string.tv_kick_back,new
        // java.text.DecimalFormat("#0.00").format(kickBack))));
        // if ("0".equals((map.get("isLike").toString()))) {
        // ivLike.setImageResource(R.drawable.img_love_star_default);
        // } else {
        // ivLike.setImageResource(R.drawable.img_love_star_selected);
        //
        // }
        // if ("0".equals((map.get("isCart").toString()))) {
        // ivShopCart.setImageResource(R.drawable.img_shopcar_default);
        // } else {
        // ivShopCart.setImageResource(R.drawable.img_shopcar_selected);
        // }

        box.setVisibility(View.GONE);


        tv_no_discount_price.setVisibility(GONE);
        tv_save.setVisibility(VISIBLE);
        returnMoney.setVisibility(GONE);

        tv_supply_name.setVisibility(GONE);

        if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
            new_sub.setVisibility(View.VISIBLE);
            new_sub.setText("" + map.get("supp_label"));
        } else {
            new_sub.setVisibility(View.INVISIBLE);
        }

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

//			price.setText("¥"+GuideActivity.oneShopPrice);

        String onPrice = map.get("app_shop_group_price") + "";
        onPrice = new java.text.DecimalFormat("#0.0")
                .format(Double.parseDouble(onPrice));
        price.setText("¥" +onPrice);


//        tv_save.setText("原价" + new java.text.DecimalFormat("#0.0").format(prices) + "元");


        if (!GuideActivity.show1yuan) { //非1元购处理
            price.setText("¥" + new java.text.DecimalFormat("#0.0").format(prices));
            String no_discount_price = "原价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
            tv_save.setText(no_discount_price);
        }


        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher("" + id);

                if (where.equals("isFilter")) {
                    YunYingTongJi.yunYingTongJi(context, 65);
                }

                if (where.equals("isCustImageGallery")) {
                    int fg = CustImageGallery.fg;
                    fg_new = fg;

                    if (fg_new == 1) {
                        YunYingTongJi.yunYingTongJi(context, 67);
                    }
                    if (fg_new == 2) {
                        YunYingTongJi.yunYingTongJi(context, 68);
                    }
                    if (fg_new == 3) {
                        YunYingTongJi.yunYingTongJi(context, 69);
                    }
                    if (fg_new == 4) {
                        YunYingTongJi.yunYingTongJi(context, 70);
                    }
                    if (fg_new == 5) {
                        YunYingTongJi.yunYingTongJi(context, 71);
                    }
                    if (fg_new == 6) {
                        YunYingTongJi.yunYingTongJi(context, 72);
                    }
                    if (fg_new == 7) {
                        YunYingTongJi.yunYingTongJi(context, 73);
                    }
                    if (fg_new == 8) {
                        YunYingTongJi.yunYingTongJi(context, 74);
                    }
                    if (fg_new == 9) {
                        YunYingTongJi.yunYingTongJi(context, 75);
                    }
                    if (fg_new == 10) {
                        YunYingTongJi.yunYingTongJi(context, 117);
                    }
                } else if (where.equals("WordSearchResultActivity")) {
                    YunYingTongJi.yunYingTongJi(context, 76);
                }

                // ForceLookActivity forceLookActivity = new
                // ForceLookActivity();
                // forceLookActivity.click_num=forceLookActivity.click_num+1;

                else if (m.matches()) {

                    if (id != null && !"".equals(id) && !"null".equals(id)) {
                        switch (Integer.parseInt(id)) {
                            case 15:
                                YunYingTongJi.yunYingTongJi(context, 77);

                                break;
                            case 16:
                                YunYingTongJi.yunYingTongJi(context, 78);

                                break;
                            case 17:
                                YunYingTongJi.yunYingTongJi(context, 79);

                                break;
                            case 18:
                                YunYingTongJi.yunYingTongJi(context, 80);

                                break;

                            case 19:
                                YunYingTongJi.yunYingTongJi(context, 81);

                                break;
                            case 20:
                                YunYingTongJi.yunYingTongJi(context, 82);
                                break;

                            case 21:
                                YunYingTongJi.yunYingTongJi(context, 83);
                                break;

                            case 22:
                                YunYingTongJi.yunYingTongJi(context, 84);

                                break;
                            case 29:
                                YunYingTongJi.yunYingTongJi(context, 85);

                                break;
                            case 23:
                                YunYingTongJi.yunYingTongJi(context, 86);

                                break;

                            case 24:
                                YunYingTongJi.yunYingTongJi(context, 87);

                                break;

                            case 25:
                                YunYingTongJi.yunYingTongJi(context, 88);
                                break;

                            case 26:
                                YunYingTongJi.yunYingTongJi(context, 89);
                                break;

                            case 27:
                                YunYingTongJi.yunYingTongJi(context, 90);

                                break;
                            case 28:
                                YunYingTongJi.yunYingTongJi(context, 91);
                                break;

                            case 10:
                                YunYingTongJi.yunYingTongJi(context, 92);
                                break;

                            case 30:
                                YunYingTongJi.yunYingTongJi(context, 93);

                                break;
                            case 31:
                                YunYingTongJi.yunYingTongJi(context, 94);

                                break;
                            case 32:
                                YunYingTongJi.yunYingTongJi(context, 95);

                                break;

                            case 33:
                                YunYingTongJi.yunYingTongJi(context, 96);

                                break;

                            case 37:
                                YunYingTongJi.yunYingTongJi(context, 97);

                                break;
                            case 38:
                                YunYingTongJi.yunYingTongJi(context, 98);

                                break;

                            case 36:
                                YunYingTongJi.yunYingTongJi(context, 99);

                                break;

                            case 11:
                                YunYingTongJi.yunYingTongJi(context, 100);

                                break;

                            case 12:
                                YunYingTongJi.yunYingTongJi(context, 101);

                                break;

                            case 13:
                                YunYingTongJi.yunYingTongJi(context, 102);

                                break;

                            case 14:
                                YunYingTongJi.yunYingTongJi(context, 103);

                                break;

                            case 34:
                                YunYingTongJi.yunYingTongJi(context, 104);

                                break;

                            case 35:
                                YunYingTongJi.yunYingTongJi(context, 105);

                                break;

                            default:
                                break;
                        }
                    }
                }
                if (ResultItemView.this.getVisibility() != View.VISIBLE) {
                    return;
                }

                FragmentActivity activity = (FragmentActivity) context;

                if (mIsFabu) {

                    Intent intent = new Intent();

                    intent.putExtra("shop_code", (String) map.get("shop_code"));

                    if (((String) map.get("supp_label")) == null || ((String) map.get("supp_label")).equals("")) {
                        intent.putExtra("label_name", "衣蝠精选");
                    } else {
                        intent.putExtra("label_name", (String) map.get("supp_label"));
                    }

                    try {
                        intent.putExtra("supp_label_id", (String) map.get("supp_label_id"));
                    } catch (Exception e) {
                    }

                    activity.setResult(10000, intent);
                    activity.finish();
                    activity.overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);

                } else {

                    addScanDataTo((String) map.get("shop_code"));
                    Intent intent = new Intent(context, ShopDetailsActivity.class);
                    intent.putExtra("code", (String) map.get("shop_code"));
                    intent.putExtra("shopCarFragment", "shopCarFragment");

                    // intent.putExtra("isforcelook", true);
                    // intent.putExtra("click_num",
                    // forceLookActivity.click_num);//把点击次数传过去 判断是否点击了足够次数
                    // 再决定是否弹框
                    // intent.putExtra("nextID", forceLookActivity.next_id);
                    // intent.putExtra("sign_id", forceLookActivity.sign_id);

                    // FragmentActivity activity = (FragmentActivity) context;
                    activity.startActivityForResult(intent, 101);
                    activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                }
            }
        });

    }


    public void initViewVip(final HashMap<String, Object> map, final String id, String isWhere,
                            final boolean mIsFabu,VipDikouData vipDikouData) {
        /** 这里赋值 */
        mHashMap = map;
        where = isWhere + "";

        image.getLayoutParams().height = width / 2 * 900 / 600;

        image.setTag(map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/"
                + (String) map.get("def_pic"));
        if (width > 720) {

            // SetImageLoader.initImageLoader(null, image,
            // map.get("shop_code").toString().substring(1, 4) + "/"
            // + map.get("shop_code").toString() + "/" + (String)
            // map.get("def_pic"), "!382");

            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/"
                    + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!382", image);

        } else {
            // SetImageLoader.initImageLoader(null, image,
            // map.get("shop_code").toString().substring(1, 4) + "/"
            // + map.get("shop_code").toString() + "/" + (String)
            // map.get("def_pic"), "!280");

            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/"
                    + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!280", image);

        }
        if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
            tv_supply_name.setVisibility(View.VISIBLE);
            tv_supply_name.setText("" + map.get("supp_label") + "制造商出品");
        } else {
            tv_supply_name.setVisibility(View.GONE);
        }
        name.setText(Shop.getShopNameStrNew((String) map.get("shop_name")));
        double prices = Double.parseDouble((String) map.get("shop_se_price"));
        double kickBack = Double.parseDouble((String) map.get("kickback"));
        price.setText("¥" + new java.text.DecimalFormat("#0.0").format(prices));
        returnMoney.setText("" + new java.text.DecimalFormat("#0").format(kickBack) + "元");

        double shop_price = Double.parseDouble((String) map.get("shop_price"));// 原价
//		double save = shop_price - prices;
//		if (save > 0) {
//			tv_save.setText("立省" + new java.text.DecimalFormat("#0.0").format(save) + "元");
//			tv_save.setVisibility(View.VISIBLE);
//		} else {
//			tv_save.setVisibility(View.GONE);
//		}
//		if (shop_price > 0) {
//			String no_discount_price = "专柜价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
//			// ToastUtil.addStrikeSpan(tv_no_discount_price,no_discount_price);
//			tv_save.setText(no_discount_price);
//			tv_save.setVisibility(View.VISIBLE);
//		} else {
//			tv_save.setVisibility(View.GONE);
//		}

        tv_save.setText("原价¥" + new java.text.DecimalFormat("#0.0").format(prices));


        tv_save.setTextSize(13);
        tv_save.setTextColor(Color.parseColor("#A8A8A8"));

        tv_no_discount_price.setText("返" + new java.text.DecimalFormat("#0.0").format(prices) + "元=0元");
        tv_no_discount_price.setVisibility(View.VISIBLE);
        tv_no_discount_price.setTextSize(11);
        tv_no_discount_price.setTextColor(Color.parseColor("#FB3B3B"));
        // returnMoney.setText(Html.fromHtml(context.getString(R.string.tv_kick_back,new
        // java.text.DecimalFormat("#0.00").format(kickBack))));
        // if ("0".equals((map.get("isLike").toString()))) {
        // ivLike.setImageResource(R.drawable.img_love_star_default);
        // } else {
        // ivLike.setImageResource(R.drawable.img_love_star_selected);
        //
        // }
        // if ("0".equals((map.get("isCart").toString()))) {
        // ivShopCart.setImageResource(R.drawable.img_shopcar_default);
        // } else {
        // ivShopCart.setImageResource(R.drawable.img_shopcar_selected);
        // }

        box.setVisibility(View.GONE);


        tv_no_discount_price.setVisibility(GONE);
        tv_save.setVisibility(VISIBLE);
        returnMoney.setVisibility(GONE);

        tv_supply_name.setVisibility(GONE);

        if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
            new_sub.setVisibility(View.VISIBLE);
            new_sub.setText("" + map.get("supp_label"));
        } else {
            new_sub.setVisibility(View.INVISIBLE);
        }

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

//			price.setText("¥"+GuideActivity.oneShopPrice);

        String onPrice = map.get("app_shop_group_price") + "";
        onPrice = new java.text.DecimalFormat("#0.0")
                .format(Double.parseDouble(onPrice));



//        price.setText(onPrice + "CC元");

//        if (vipDikouData != null && vipDikouData.getIsVip() > 0) { //会员的处理
            price.setText("会员价元");

            double vipPrice;

            double sePrice = prices * 0.95;
            double dikou = prices * 0.9;

            if (vipDikouData.getOne_not_use_price() >= dikou) {
                if(vipDikouData.getMaxType() == 6){
                    vipPrice =  sePrice - dikou;
                }else{
                    vipPrice =  prices - dikou;

                }
            }else{

                if(vipDikouData.getMaxType() == 6){
                    vipPrice =  sePrice - vipDikouData.getOne_not_use_price();
                }else{
                    vipPrice =  prices - vipDikouData.getOne_not_use_price();

                }

//            }

            price.setText("¥" +new java.text.DecimalFormat("#0.0").format(vipPrice));

        }



        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher("" + id);

                if (where.equals("isFilter")) {
                    YunYingTongJi.yunYingTongJi(context, 65);
                }

                if (where.equals("isCustImageGallery")) {
                    int fg = CustImageGallery.fg;
                    fg_new = fg;

                    if (fg_new == 1) {
                        YunYingTongJi.yunYingTongJi(context, 67);
                    }
                    if (fg_new == 2) {
                        YunYingTongJi.yunYingTongJi(context, 68);
                    }
                    if (fg_new == 3) {
                        YunYingTongJi.yunYingTongJi(context, 69);
                    }
                    if (fg_new == 4) {
                        YunYingTongJi.yunYingTongJi(context, 70);
                    }
                    if (fg_new == 5) {
                        YunYingTongJi.yunYingTongJi(context, 71);
                    }
                    if (fg_new == 6) {
                        YunYingTongJi.yunYingTongJi(context, 72);
                    }
                    if (fg_new == 7) {
                        YunYingTongJi.yunYingTongJi(context, 73);
                    }
                    if (fg_new == 8) {
                        YunYingTongJi.yunYingTongJi(context, 74);
                    }
                    if (fg_new == 9) {
                        YunYingTongJi.yunYingTongJi(context, 75);
                    }
                    if (fg_new == 10) {
                        YunYingTongJi.yunYingTongJi(context, 117);
                    }
                } else if (where.equals("WordSearchResultActivity")) {
                    YunYingTongJi.yunYingTongJi(context, 76);
                }

                // ForceLookActivity forceLookActivity = new
                // ForceLookActivity();
                // forceLookActivity.click_num=forceLookActivity.click_num+1;

                else if (m.matches()) {

                    if (id != null && !"".equals(id) && !"null".equals(id)) {
                        switch (Integer.parseInt(id)) {
                            case 15:
                                YunYingTongJi.yunYingTongJi(context, 77);

                                break;
                            case 16:
                                YunYingTongJi.yunYingTongJi(context, 78);

                                break;
                            case 17:
                                YunYingTongJi.yunYingTongJi(context, 79);

                                break;
                            case 18:
                                YunYingTongJi.yunYingTongJi(context, 80);

                                break;

                            case 19:
                                YunYingTongJi.yunYingTongJi(context, 81);

                                break;
                            case 20:
                                YunYingTongJi.yunYingTongJi(context, 82);
                                break;

                            case 21:
                                YunYingTongJi.yunYingTongJi(context, 83);
                                break;

                            case 22:
                                YunYingTongJi.yunYingTongJi(context, 84);

                                break;
                            case 29:
                                YunYingTongJi.yunYingTongJi(context, 85);

                                break;
                            case 23:
                                YunYingTongJi.yunYingTongJi(context, 86);

                                break;

                            case 24:
                                YunYingTongJi.yunYingTongJi(context, 87);

                                break;

                            case 25:
                                YunYingTongJi.yunYingTongJi(context, 88);
                                break;

                            case 26:
                                YunYingTongJi.yunYingTongJi(context, 89);
                                break;

                            case 27:
                                YunYingTongJi.yunYingTongJi(context, 90);

                                break;
                            case 28:
                                YunYingTongJi.yunYingTongJi(context, 91);
                                break;

                            case 10:
                                YunYingTongJi.yunYingTongJi(context, 92);
                                break;

                            case 30:
                                YunYingTongJi.yunYingTongJi(context, 93);

                                break;
                            case 31:
                                YunYingTongJi.yunYingTongJi(context, 94);

                                break;
                            case 32:
                                YunYingTongJi.yunYingTongJi(context, 95);

                                break;

                            case 33:
                                YunYingTongJi.yunYingTongJi(context, 96);

                                break;

                            case 37:
                                YunYingTongJi.yunYingTongJi(context, 97);

                                break;
                            case 38:
                                YunYingTongJi.yunYingTongJi(context, 98);

                                break;

                            case 36:
                                YunYingTongJi.yunYingTongJi(context, 99);

                                break;

                            case 11:
                                YunYingTongJi.yunYingTongJi(context, 100);

                                break;

                            case 12:
                                YunYingTongJi.yunYingTongJi(context, 101);

                                break;

                            case 13:
                                YunYingTongJi.yunYingTongJi(context, 102);

                                break;

                            case 14:
                                YunYingTongJi.yunYingTongJi(context, 103);

                                break;

                            case 34:
                                YunYingTongJi.yunYingTongJi(context, 104);

                                break;

                            case 35:
                                YunYingTongJi.yunYingTongJi(context, 105);

                                break;

                            default:
                                break;
                        }
                    }
                }
                if (ResultItemView.this.getVisibility() != View.VISIBLE) {
                    return;
                }

                FragmentActivity activity = (FragmentActivity) context;

                if (mIsFabu) {

                    Intent intent = new Intent();

                    intent.putExtra("shop_code", (String) map.get("shop_code"));

                    if (((String) map.get("supp_label")) == null || ((String) map.get("supp_label")).equals("")) {
                        intent.putExtra("label_name", "衣蝠精选");
                    } else {
                        intent.putExtra("label_name", (String) map.get("supp_label"));
                    }

                    try {
                        intent.putExtra("supp_label_id", (String) map.get("supp_label_id"));
                    } catch (Exception e) {
                    }

                    activity.setResult(10000, intent);
                    activity.finish();
                    activity.overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);

                } else {

                    addScanDataTo((String) map.get("shop_code"));
                    Intent intent = new Intent(context, ShopDetailsActivity.class);
                    intent.putExtra("code", (String) map.get("shop_code"));
                    intent.putExtra("shopCarFragment", "shopCarFragment");

                    // intent.putExtra("isforcelook", true);
                    // intent.putExtra("click_num",
                    // forceLookActivity.click_num);//把点击次数传过去 判断是否点击了足够次数
                    // 再决定是否弹框
                    // intent.putExtra("nextID", forceLookActivity.next_id);
                    // intent.putExtra("sign_id", forceLookActivity.sign_id);

                    // FragmentActivity activity = (FragmentActivity) context;
                    activity.startActivityForResult(intent, 101);
                    activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                }
            }
        });

    }



    /*
     * 把浏览过的数据添加进数据库
     */
    private void addScanDataTo(final String shop_code) {
        new SAsyncTask<Void, Void, ReturnInfo>(((FragmentActivity) context)) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel.addMySteps(context, shop_code);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result) {
                super.onPostExecute(context, result);
            }

        }.execute();
    }

}
