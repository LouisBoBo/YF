package com.yssj.custom.view;

import android.app.Activity;
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
import com.yssj.data.YDBHelper;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.Shop;
import com.yssj.entity.VipDikouData;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.fragment.HomePage3Fragment;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ForceLookItemView extends LinearLayout {

    private TextView new_sub;//一元购品牌名
    private Context context;

    private TextView name;

    private CheckBox box;

    private TextView price, tv_save, tv_no_discount_price,tv_price_homepage3;

    private TextView returnMoney;

    private ImageView image;

    private int width;

    private ImageView ivShopCart;

    private double vipPrice;


    private ImageView ivLike;
    private TextView tv_supply_name;
    private YDBHelper dbHelp;
    private boolean isMoreShop;//密友圈帖子详情更多商品推荐

    public ForceLookItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.myfave_infos_list_old, this);
        tv_supply_name = findViewById(R.id.tv_supply_name);
        name = findViewById(R.id.news_title);

        price = findViewById(R.id.tv_price);
        tv_price_homepage3 = findViewById(R.id.tv_price_homepage3);

        box = findViewById(R.id.cb_favor);

        returnMoney = findViewById(R.id.nickback);
        new_sub = findViewById(R.id.new_sub);

        image = findViewById(R.id.news_pic);

        tv_save = findViewById(R.id.tv_save);
        tv_no_discount_price = findViewById(R.id.tv_no_discount_price);

//		ivShopCart = (ImageView) findViewById(R.id.iv_shopcar);
//		ivLike = (ImageView) findViewById(R.id.iv_love_star);

        width = context.getResources().getDisplayMetrics().widthPixels;
        dbHelp = new YDBHelper(context);
    }

    public boolean isMoreShop() {
        return isMoreShop;
    }


    private boolean isNewPT;//是否是新拼团
    public  void setNewPT(boolean mIsNewPT){
        this.isNewPT = mIsNewPT;
    }

    public void setMoreShop(boolean moreShop) {
        isMoreShop = moreShop;
    }

    public void initView(final HashMap<String, Object> map) {
        /**这里赋值*/

//        image.getLayoutParams().height = width / 2 * 900 / 600;

        image.setTag(map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic"));
        if (width > 720) {
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!382", image);
//			SetImageLoader.initImageLoader(null, image, map.get("shop_code").toString().substring(1, 4)+"/"+map.get("shop_code").toString()+"/"+(String) map.get("def_pic"),"!382");
        } else {
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!280", image);

//			SetImageLoader.initImageLoader(null, image, map.get("shop_code").toString().substring(1, 4)+"/"+map.get("shop_code").toString()+"/"+(String) map.get("def_pic"),"!280");
        }
        Double prices = (double) 0;

        name.setText(Shop.getShopNameStrNew((String) map.get("shop_name")));
//		Double prices=Double.parseDouble((String) map.get("shop_se_price"));
        if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
            tv_supply_name.setVisibility(View.VISIBLE);
            tv_supply_name.setText("" + map.get("supp_label") + "制造商出品");
        } else {
            if (null != map.get("supp_label_id") && !"".equals(map.get("supp_label_id"))) {
                List<HashMap<String, String>> listDataSupple = new ArrayList<HashMap<String, String>>();
                String sql = "select * from supp_label  where _id = " + map.get("supp_label_id");
                listDataSupple = dbHelp.query(sql);
                if (listDataSupple.size() > 0) {
                    tv_supply_name.setVisibility(View.VISIBLE);
                    tv_supply_name.setText(listDataSupple.get(0).get("name") + "制造商出品");
                } else {
                    tv_supply_name.setVisibility(View.GONE);
                }
            } else {
                tv_supply_name.setVisibility(View.GONE);
            }
        }
        String a = (String) map.get("shop_se_price");
        if (a.equals("null")) {
            prices = (double) 0;
        } else {


//			DecimalFormat pFormate = new DecimalFormat("#0.0");
            prices = Double.parseDouble((String) map.get("shop_se_price"));


//            prices = Double.parseDouble((String) map.get("shop_se_price"));


        }

        double kickBack = Double.parseDouble((String) map.get("kickback"));
//		Double prices=(double) 1;
//        price.setText("¥" + new java.text.DecimalFormat("#0.0").format(prices - (int) kickBack));
//        price.setText("原价¥" + new java.text.DecimalFormat("#0.0").format(prices) + "元");
        price.setText("月销" + map.get("virtual_sales") + "件");


        returnMoney.setText("" + new java.text.DecimalFormat("#0").format(kickBack) + "元");

        double shop_price = Double.parseDouble((String) map.get("shop_price"));//原价
//        double save = shop_price - prices;
//        if (save > 0) {
//            tv_save.setText("立省" + new java.text.DecimalFormat("#0.0").format(save) + "元");
//            tv_save.setVisibility(View.VISIBLE);
//        } else {
//            tv_save.setVisibility(View.GONE);
//        }
        if (shop_price > 0) {
            String no_discount_price = "专柜价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
//			ToastUtil.addStrikeSpan(tv_no_discount_price,no_discount_price);
            tv_save.setText(no_discount_price);
            tv_save.setVisibility(View.VISIBLE);
        } else {
            tv_save.setVisibility(View.GONE);
        }
        tv_save.setTextSize(13);
        tv_save.setTextColor(Color.parseColor("#A8A8A8"));

        tv_no_discount_price.setText("返" + new java.text.DecimalFormat("#0.0").format(prices) + "元=0元");
        tv_no_discount_price.setVisibility(View.VISIBLE);
        tv_no_discount_price.setTextSize(11);
        tv_no_discount_price.setTextColor(Color.parseColor("#FB3B3B"));
//		returnMoney.setText(Html.fromHtml(context.getString(R.string.tv_kick_back,new java.text.DecimalFormat("#0.00").format(kickBack))));
//		if ("0".equals((map.get("isLike").toString()))) {
//			ivLike.setImageResource(R.drawable.img_love_star_default);
//		} else {
//			ivLike.setImageResource(R.drawable.img_love_star_selected);
//
//		}
//		if ("0".equals((map.get("isCart").toString()))) {
//			ivShopCart.setImageResource(R.drawable.img_shopcar_default);
//		} else {
//			ivShopCart.setImageResource(R.drawable.img_shopcar_selected);
//		}


        box.setVisibility(View.GONE);


        tv_supply_name.setVisibility(GONE);
        if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
            new_sub.setVisibility(View.VISIBLE);
            new_sub.setText("" + map.get("supp_label"));
        } else {
            if (null != map.get("supp_label_id") && !"".equals(map.get("supp_label_id"))) {
                List<HashMap<String, String>> listDataSupple = new ArrayList<HashMap<String, String>>();
                String sql = "select * from supp_label  where _id = " + map.get("supp_label_id");
                listDataSupple = dbHelp.query(sql);
                if (listDataSupple.size() > 0) {
                    new_sub.setVisibility(View.VISIBLE);
                    new_sub.setText(listDataSupple.get(0).get("name"));
                } else {
                    new_sub.setVisibility(View.INVISIBLE);
                }
            } else {
                new_sub.setVisibility(View.INVISIBLE);
            }
        }


        tv_no_discount_price.setVisibility(GONE);
        tv_save.setVisibility(VISIBLE);


//            if (shop_price > 0) {
//                String no_discount_price = "原价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
////			ToastUtil.addStrikeSpan(tv_no_discount_price,no_discount_price);
//                tv_save.setText(no_discount_price);
//                tv_save.setVisibility(View.VISIBLE);
//            } else {
//                tv_save.setVisibility(View.GONE);
//            }

//            price.setText("¥"+GuideActivity.oneShopPrice);


        String onPrice = map.get("assmble_price") + "";
        try {
            onPrice = new java.text.DecimalFormat("#0.0")
                    .format(Double.parseDouble(onPrice));
        } catch (Exception e) {
            e.printStackTrace();
        }

        price.setText("¥" + onPrice + "元");

//        if(isNewPT){
//            String ptPrice = map.get("assmble_price") + "";
//            try {
//                ptPrice = new java.text.DecimalFormat("#0.0")
//                        .format(Double.parseDouble(ptPrice));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            price.setText("¥" + ptPrice + "元");
//
//
//        }








//        tv_save.setText("原价¥" + new java.text.DecimalFormat("#0.0").format(prices) + "元");

        tv_save.setText("月销" + map.get("virtual_sales") + "件");
        if (!GuideActivity.show1yuan) { //非1元购处理
            price.setText("¥" + new java.text.DecimalFormat("#0.0").format(prices));
            String no_discount_price = "原价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
            tv_save.setText(no_discount_price);
        }


        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ForceLookActivity forceLookActivity = new ForceLookActivity();
//					forceLookActivity.click_num=forceLookActivity.click_num+1;
                forceLookActivity.num_tongji = forceLookActivity.num_tongji + 1;

                if (ForceLookItemView.this.getVisibility() != View.VISIBLE) {
                    return;
                }
                FragmentActivity activity = (FragmentActivity) context;

                addScanDataTo((String) map.get("shop_code"));
                Intent intent = new Intent(context, ShopDetailsActivity.class);
                intent.putExtra("code", (String) map.get("shop_code"));
                intent.putExtra("shopCarFragment", "shopCarFragment");
                intent.putExtra("freeBuyType",activity.getIntent().getIntExtra("freeBuyType",0));


                if(isNewPT){
                    intent.putExtra("fromKTtask",true);
                }

                if (SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete && !isMoreShop) {//强制浏览个数 并且浏览任务没有完成的
                    intent.putExtra("isforcelook", true);
                }
                if (SignListAdapter.doType == 19 && !SignListAdapter.isSignComplete && !isMoreShop) {//新增强制浏览个数 奖励额度 并且浏览任务没有完成的
                    intent.putExtra(YConstance.Pref.ISFORCELOOKLIMIT, true);
                }
//					intent.putExtra("click_num", forceLookActivity.click_num);//把点击次数传过去 判断是否点击了足够次数 再决定是否弹框
//					intent.putExtra("now_type_id_value", forceLookActivity.now_type_id_value);  
//					intent.putExtra("now_type_id", forceLookActivity.now_type_id);  
//					intent.putExtra("next_type_id", forceLookActivity.next_type_id);  
//					intent.putExtra("next_type_id_value", forceLookActivity.next_type_id_value);  


                activity.startActivityForResult(intent, 101);
                activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
            }
        });
    }


    public void initViewVip(final HashMap<String, Object> map, final VipDikouData vipDikouData) {


        image.setTag(map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic"));
        if (width > 720) {
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!382", image);
        } else {
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!280", image);

        }
        Double prices = (double) 0;

        name.setText(Shop.getShopNameStrNew((String) map.get("shop_name")));
        if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
            tv_supply_name.setVisibility(View.VISIBLE);
            tv_supply_name.setText("" + map.get("supp_label") + "制造商出品");
        } else {
            if (null != map.get("supp_label_id") && !"".equals(map.get("supp_label_id"))) {
                List<HashMap<String, String>> listDataSupple = new ArrayList<HashMap<String, String>>();
                String sql = "select * from supp_label  where _id = " + map.get("supp_label_id");
                listDataSupple = dbHelp.query(sql);
                if (listDataSupple.size() > 0) {
                    tv_supply_name.setVisibility(View.VISIBLE);
                    tv_supply_name.setText(listDataSupple.get(0).get("name") + "制造商出品");
                } else {
                    tv_supply_name.setVisibility(View.GONE);
                }
            } else {
                tv_supply_name.setVisibility(View.GONE);
            }
        }
        String a = (String) map.get("shop_se_price");
        if (a.equals("null")) {
            prices = (double) 0;
        } else {

            prices = Double.parseDouble((String) map.get("shop_se_price"));

        }

        double kickBack = Double.parseDouble((String) map.get("kickback"));
        price.setText("月销" + map.get("virtual_sales") + "件");


        returnMoney.setText("" + new java.text.DecimalFormat("#0").format(kickBack) + "元");

        double shop_price = Double.parseDouble((String) map.get("shop_price"));//原价
//        double save = shop_price - prices;
//        if (save > 0) {
//            tv_save.setText("立省" + new java.text.DecimalFormat("#0.0").format(save) + "元");
//            tv_save.setVisibility(View.VISIBLE);
//        } else {
//            tv_save.setVisibility(View.GONE);
//        }
        if (shop_price > 0) {
            String no_discount_price = "专柜价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
//			ToastUtil.addStrikeSpan(tv_no_discount_price,no_discount_price);
            tv_save.setText(no_discount_price);
            tv_save.setVisibility(View.VISIBLE);
        } else {
            tv_save.setVisibility(View.GONE);
        }
        tv_save.setTextSize(13);
        tv_save.setTextColor(Color.parseColor("#A8A8A8"));

        tv_no_discount_price.setText("返" + new java.text.DecimalFormat("#0.0").format(prices) + "元=0元");
        tv_no_discount_price.setVisibility(View.VISIBLE);
        tv_no_discount_price.setTextSize(11);
        tv_no_discount_price.setTextColor(Color.parseColor("#FB3B3B"));
//		returnMoney.setText(Html.fromHtml(context.getString(R.string.tv_kick_back,new java.text.DecimalFormat("#0.00").format(kickBack))));
//		if ("0".equals((map.get("isLike").toString()))) {
//			ivLike.setImageResource(R.drawable.img_love_star_default);
//		} else {
//			ivLike.setImageResource(R.drawable.img_love_star_selected);
//
//		}
//		if ("0".equals((map.get("isCart").toString()))) {
//			ivShopCart.setImageResource(R.drawable.img_shopcar_default);
//		} else {
//			ivShopCart.setImageResource(R.drawable.img_shopcar_selected);
//		}


        box.setVisibility(View.GONE);


        tv_supply_name.setVisibility(GONE);
        if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
            new_sub.setVisibility(View.VISIBLE);
            new_sub.setText("" + map.get("supp_label"));
        } else {
            if (null != map.get("supp_label_id") && !"".equals(map.get("supp_label_id"))) {
                List<HashMap<String, String>> listDataSupple = new ArrayList<HashMap<String, String>>();
                String sql = "select * from supp_label  where _id = " + map.get("supp_label_id");
                listDataSupple = dbHelp.query(sql);
                if (listDataSupple.size() > 0) {
                    new_sub.setVisibility(View.VISIBLE);
                    new_sub.setText(listDataSupple.get(0).get("name"));
                } else {
                    new_sub.setVisibility(View.INVISIBLE);
                }
            } else {
                new_sub.setVisibility(View.INVISIBLE);
            }
        }


        tv_no_discount_price.setVisibility(GONE);
        tv_save.setVisibility(VISIBLE);


//            if (shop_price > 0) {
//                String no_discount_price = "原价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
////			ToastUtil.addStrikeSpan(tv_no_discount_price,no_discount_price);
//                tv_save.setText(no_discount_price);
//                tv_save.setVisibility(View.VISIBLE);
//            } else {
//                tv_save.setVisibility(View.GONE);
//            }

//            price.setText("¥"+GuideActivity.oneShopPrice);


        String onPrice = map.get("app_shop_group_price") + "";
        try {
            onPrice = new java.text.DecimalFormat("#0.0")
                    .format(Double.parseDouble(onPrice));
        } catch (Exception e) {
            e.printStackTrace();
        }


        price.setText("¥" + onPrice + "元");

        if (vipDikouData != null && vipDikouData.getIsVip() > 0) { //会员的处理
            price.setText("会员价元");


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

            price.setText("¥" + new java.text.DecimalFormat("#0.0").format(vipPrice));

        }


//        tv_save.setText("原价¥" + new java.text.DecimalFormat("#0.0").format(prices) + "元");

        tv_save.setText("月销" + map.get("virtual_sales") + "件");
        if (!GuideActivity.show1yuan) { //非1元购处理
            price.setText("¥" + new java.text.DecimalFormat("#0.0").format(prices));
            String no_discount_price = "原价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
            tv_save.setText(no_discount_price);
        }


        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ForceLookActivity forceLookActivity = new ForceLookActivity();
//					forceLookActivity.click_num=forceLookActivity.click_num+1;
                forceLookActivity.num_tongji = forceLookActivity.num_tongji + 1;

                if (ForceLookItemView.this.getVisibility() != View.VISIBLE) {
                    return;
                }
                addScanDataTo((String) map.get("shop_code"));
                Intent intent = new Intent(context, ShopDetailsActivity.class);
                intent.putExtra("code", (String) map.get("shop_code"));
                intent.putExtra("shopCarFragment", "shopCarFragment");
                if(isNewPT){
                    intent.putExtra("fromKTtask",true);
                }
                if (vipDikouData != null && vipDikouData.getIsVip() > 0) { //会员的处理

                    intent.putExtra("vipPrice", vipPrice + "");

                }
                FragmentActivity activity = (FragmentActivity) context;


                intent.putExtra("freeBuyType",activity.getIntent().getIntExtra("freeBuyType",0));


                if (SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete && !isMoreShop) {//强制浏览个数 并且浏览任务没有完成的
                    intent.putExtra("isforcelook", true);
                }
                if (SignListAdapter.doType == 19 && !SignListAdapter.isSignComplete && !isMoreShop) {//新增强制浏览个数 奖励额度 并且浏览任务没有完成的
                    intent.putExtra(YConstance.Pref.ISFORCELOOKLIMIT, true);
                }
//					intent.putExtra("click_num", forceLookActivity.click_num);//把点击次数传过去 判断是否点击了足够次数 再决定是否弹框
//					intent.putExtra("now_type_id_value", forceLookActivity.now_type_id_value);
//					intent.putExtra("now_type_id", forceLookActivity.now_type_id);
//					intent.putExtra("next_type_id", forceLookActivity.next_type_id);
//					intent.putExtra("next_type_id_value", forceLookActivity.next_type_id_value);


                activity.startActivityForResult(intent, 101);
                activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                ;
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


    public void initViewHomePage3(final HashMap<String, Object> map) {
        /**这里赋值*/

//        image.getLayoutParams().height = width / 2 * 900 / 600;

        image.setTag(map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic"));
        if (width > 720) {
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!382", image);
//			SetImageLoader.initImageLoader(null, image, map.get("shop_code").toString().substring(1, 4)+"/"+map.get("shop_code").toString()+"/"+(String) map.get("def_pic"),"!382");
        } else {
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!280", image);

//			SetImageLoader.initImageLoader(null, image, map.get("shop_code").toString().substring(1, 4)+"/"+map.get("shop_code").toString()+"/"+(String) map.get("def_pic"),"!280");
        }
        Double prices = (double) 0;

        name.setText(Shop.getShopNameStrNew((String) map.get("shop_name")));
//		Double prices=Double.parseDouble((String) map.get("shop_se_price"));
        if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
            tv_supply_name.setVisibility(View.VISIBLE);
            tv_supply_name.setText("" + map.get("supp_label") + "制造商出品");
        } else {
            if (null != map.get("supp_label_id") && !"".equals(map.get("supp_label_id"))) {
                List<HashMap<String, String>> listDataSupple = new ArrayList<HashMap<String, String>>();
                String sql = "select * from supp_label  where _id = " + map.get("supp_label_id");
                listDataSupple = dbHelp.query(sql);
                if (listDataSupple.size() > 0) {
                    tv_supply_name.setVisibility(View.VISIBLE);
                    tv_supply_name.setText(listDataSupple.get(0).get("name") + "制造商出品");
                } else {
                    tv_supply_name.setVisibility(View.GONE);
                }
            } else {
                tv_supply_name.setVisibility(View.GONE);
            }
        }
        String a = (String) map.get("shop_se_price");
        if (a.equals("null")) {
            prices = (double) 0;
        } else {


//			DecimalFormat pFormate = new DecimalFormat("#0.0");
            prices = Double.parseDouble((String) map.get("shop_se_price"));


//            prices = Double.parseDouble((String) map.get("shop_se_price"));


        }

        double kickBack = Double.parseDouble((String) map.get("kickback"));
//		Double prices=(double) 1;
//        price.setText("¥" + new java.text.DecimalFormat("#0.0").format(prices - (int) kickBack));
//        price.setText("原价¥" + new java.text.DecimalFormat("#0.0").format(prices) + "元");
        price.setText("月销" + map.get("virtual_sales") + "件");


        returnMoney.setText("" + new java.text.DecimalFormat("#0").format(kickBack) + "元");

        double shop_price = Double.parseDouble((String) map.get("shop_price"));//原价
//        double save = shop_price - prices;
//        if (save > 0) {
//            tv_save.setText("立省" + new java.text.DecimalFormat("#0.0").format(save) + "元");
//            tv_save.setVisibility(View.VISIBLE);
//        } else {
//            tv_save.setVisibility(View.GONE);
//        }
        if (shop_price > 0) {
            String no_discount_price = "专柜价¥" + new java.text.DecimalFormat("#0.0").format(shop_price);
//			ToastUtil.addStrikeSpan(tv_no_discount_price,no_discount_price);
            tv_save.setText(no_discount_price);
            tv_save.setVisibility(View.VISIBLE);
        } else {
            tv_save.setVisibility(View.GONE);
        }
        tv_save.setTextSize(13);
        tv_save.setTextColor(Color.parseColor("#A8A8A8"));

        tv_no_discount_price.setText("返" + new java.text.DecimalFormat("#0.0").format(prices) + "元=0元");
        tv_no_discount_price.setVisibility(View.VISIBLE);
        tv_no_discount_price.setTextSize(11);
        tv_no_discount_price.setTextColor(Color.parseColor("#FB3B3B"));

        box.setVisibility(View.GONE);


        tv_supply_name.setVisibility(GONE);
        if (!"".equals(map.get("supp_label")) && null != map.get("supp_label")) {
            new_sub.setVisibility(View.VISIBLE);
            new_sub.setText("" + map.get("supp_label"));
        } else {
            if (null != map.get("supp_label_id") && !"".equals(map.get("supp_label_id"))) {
                List<HashMap<String, String>> listDataSupple = new ArrayList<HashMap<String, String>>();
                String sql = "select * from supp_label  where _id = " + map.get("supp_label_id");
                listDataSupple = dbHelp.query(sql);
                if (listDataSupple.size() > 0) {
                    new_sub.setVisibility(View.VISIBLE);
                    new_sub.setText(listDataSupple.get(0).get("name"));
                } else {
                    new_sub.setVisibility(View.INVISIBLE);
                }
            } else {
                new_sub.setVisibility(View.INVISIBLE);
            }
        }


        tv_no_discount_price.setVisibility(GONE);
        tv_save.setVisibility(VISIBLE);
        price.setVisibility(GONE);

        tv_price_homepage3.setText("免费领>");
        tv_price_homepage3.setVisibility(VISIBLE);
        tv_save.setText("已领" + map.get("virtual_sales") + "件");


        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, ShopDetailsActivity.class);

                intent.putExtra("freeOrderPage", HomePage3Fragment.freeOrderPage);

                FragmentActivity activity = (FragmentActivity) context;


                intent.putExtra("freeBuyType",activity.getIntent().getIntExtra("freeBuyType",0));


                intent.putExtra("code", (String) map.get("shop_code"));
                intent.putExtra("fromShouye3",true);
                context.startActivity(intent);
                if(isNewPT){
                    intent.putExtra("fromKTtask",true);
                }
                ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
            }
        });
    }


}
