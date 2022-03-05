package com.yssj.custom.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.Shop;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.main.SignGroupShopActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.utils.PicassoUtils;

import java.util.HashMap;

public class SignGroupShopItemView extends LinearLayout {

    private Context context;

    private TextView name;

    //	private CheckBox box;
    private TextView price;

    private TextView returnMoney;

    private ImageView image;
    public ImageView group_iv_select;

    private int width;
    private static int index;//点了第几个条目

    private TextView tv_discount, tv_sold, tv_manufacturers;
    private TextView tv_shoppe_price;

    public SignGroupShopItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.sign_group_shop_list, this);

        name = (TextView) findViewById(R.id.news_title);

        price = (TextView) findViewById(R.id.tv_price);
        tv_shoppe_price = (TextView) findViewById(R.id.tv_shoppe_price);

        tv_manufacturers = (TextView) findViewById(R.id.tv_manufacturers);

        returnMoney = (TextView) findViewById(R.id.nickback);

        image = (ImageView) findViewById(R.id.news_pic);
        tv_discount = (TextView) findViewById(R.id.tv_discount);
        tv_sold = (TextView) findViewById(R.id.tv_sold);
        group_iv_select = (ImageView) findViewById(R.id.group_iv_select);

//		ivShopCart = (ImageView) findViewById(R.id.iv_shopcar);
//		ivLike = (ImageView) findViewById(R.id.iv_love_star);

        width = context.getResources().getDisplayMetrics().widthPixels;
    }

    public void initView(final HashMap<String, Object> map, final int index) {
        /**这里赋值*/
        this.index = index;
        image.getLayoutParams().height = width / 2 * 900 / 600;

        image.setTag(map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic"));
        if (width > 720) {
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!382", image);
        } else {
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!280", image);

        }
        Double prices = (double) 0;

        name.setText(Shop.getShopNameStrNew((String) map.get("shop_name")));
        String a = (String) map.get("shop_se_price");
        if (a.equals("null")) {
            prices = (double) 0;
        } else {
            prices = Double.parseDouble((String) map.get("shop_se_price"));
        }

        price.setText("¥" + new java.text.DecimalFormat("#0.0").format(prices));
        Double savePrices = Double.parseDouble((String) map.get("shop_price"));
        tv_shoppe_price.setText("¥" + new java.text.DecimalFormat("#0.0").format(savePrices));
        returnMoney.setText("拼团价¥" +  map.get("roll_price"));
        tv_discount.setText(new java.text.DecimalFormat("#0.0").format((prices / savePrices) * 10) + "折");
        if (map.get("virtual_sales") != null) {
            tv_sold.setText("已售" + map.get("virtual_sales") + "件");
        }
        String manufacturers = (String) map.get("supp_label");
        if (!TextUtils.isEmpty(manufacturers)) {
            tv_manufacturers.setText(manufacturers + "制造商出品");
            tv_manufacturers.setVisibility(View.VISIBLE);
        } else {
            tv_manufacturers.setVisibility(View.GONE);
        }
        if ("1".equals(map.get("is_click"))) {
//            group_iv_select.setImageResource(R.drawable.icon_celect);
            group_iv_select.setVisibility(View.VISIBLE);
        } else {
            group_iv_select.setVisibility(View.GONE);
//            group_iv_select.setImageResource(R.drawable.icon_xuanze_disabled);
        }
        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (SignGroupShopItemView.this.getVisibility() != View.VISIBLE) {
                    return;
                }
                if ("1".equals(map.get("is_click"))) {
                    SignGroupShopActivity.listClick.clear();
                    map.put("is_click", "0");
                    SignGroupShopActivity.seleckFlag = -1;
//                    group_iv_select.setImageResource(R.drawable.icon_xuanze_disabled);
                    group_iv_select.setVisibility(View.GONE);
                    return;
                }
                SignGroupShopActivity.seleckFlag = index;
                addScanDataTo((String) map.get("shop_code"));
                Intent intent = new Intent(context, ShopDetailsActivity.class);
                intent.putExtra("code", (String) map.get("shop_code"));
//                intent.putExtra("isSignActiveShop", true);
                intent.putExtra("group_click_flag", true);
//					if(SignFragment.doType==4&&!SignFragment.isSignComplete){//强制浏览个数并且任务是没有完成的
//						intent.putExtra("isSignActiveShopScan", true);
//					}

                FragmentActivity activity = (FragmentActivity) context;
                activity.startActivityForResult(intent, SignGroupShopActivity.REQUEST_DETAILS);
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


}
