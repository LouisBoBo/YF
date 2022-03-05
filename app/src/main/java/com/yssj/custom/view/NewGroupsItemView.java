package com.yssj.custom.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yssj.activity.R;
import com.yssj.entity.NewPTdetailShop;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.utils.GlideUtils;
import com.yssj.utils.PicassoUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class NewGroupsItemView extends LinearLayout {
    private Context mContext;
    private TextView groupsTv;//发起人还是参与者
    private ImageView headIv;
    private ImageView headTzIcon;
    private TextView groupsNicknameTv, groupsDateTv, groupsDateTimesTv, actualPayTv;
    private ImageView imgOneTv, imgTwoTv;
    private TextView shopNumOneTv, shopNumTwoTv, shopNameOneTv, shopNameTwoTv,
            shopColorOneTv, shopColorTwoTv, shopSizeOneTv, shopSizeTwoTv,
            shopPriceOneTv, shopPriceTwoTv, shopElidePriceOneTv, shopElidePriceTwoTv, tv_nopay;
    private View mItemOne, mItemTwo;
    private SimpleDateFormat df;
    private boolean isOneBuy;
    private boolean isMeal;

    public void setOnebuy() {
        isOneBuy = true;
        mItemTwo.setVisibility(GONE);
    }

    public void setMeal(boolean isMeal) {
        isOneBuy = true;
        this.isMeal = isMeal;
    }


    public NewGroupsItemView(Context context) {
        super(context);
        mContext = context;
        initView(context);
    }

    public NewGroupsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
    }

    public NewGroupsItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(
                R.layout.groups_item_view, this, true);
        groupsTv = (TextView) findViewById(R.id.groups_tv);
        headIv = (ImageView) findViewById(R.id.groups_head_iv);
        headTzIcon = (ImageView) findViewById(R.id.groups_head_tz_icon);
        headTzIcon.setVisibility(View.GONE);
        groupsNicknameTv = (TextView) findViewById(R.id.groups_nickname);
        groupsDateTv = (TextView) findViewById(R.id.groups_date);
        groupsDateTimesTv = (TextView) findViewById(R.id.groups_date_times);
        actualPayTv = (TextView) findViewById(R.id.tv_actual_pay);

        shopNumOneTv = (TextView) findViewById(R.id.groups_shop_num_one);
        shopNameOneTv = (TextView) findViewById(R.id.groups_shop_name_one);
        shopColorOneTv = (TextView) findViewById(R.id.groups_shop_color_one);
        shopSizeOneTv = (TextView) findViewById(R.id.groups_shop_size_one);
        shopPriceOneTv = (TextView) findViewById(R.id.groups_shop_price_one);
        shopElidePriceOneTv = (TextView) findViewById(R.id.groups_shop_ori_price_one);

        shopNumTwoTv = (TextView) findViewById(R.id.groups_shop_num_two);
        shopNameTwoTv = (TextView) findViewById(R.id.groups_shop_name_two);
        shopColorTwoTv = (TextView) findViewById(R.id.groups_shop_color_two);
        shopSizeTwoTv = (TextView) findViewById(R.id.groups_shop_size_two);
        shopPriceTwoTv = (TextView) findViewById(R.id.groups_shop_price_two);
        shopElidePriceTwoTv = (TextView) findViewById(R.id.groups_shop_ori_price_two);
        tv_nopay = (TextView) findViewById(R.id.tv_nopay);


        imgOneTv = (ImageView) findViewById(R.id.groups_shop_img_one);
        imgTwoTv = (ImageView) findViewById(R.id.groups_shop_img_two);

        mItemOne = findViewById(R.id.groups_shop_one);
        mItemTwo = findViewById(R.id.groups_shop_two);
        df = new SimpleDateFormat("yyyy-MM-dd^HH:mm:ss");
    }

    public void setItemData(NewPTdetailShop.DataBean itemData) {
        String shop_code = itemData.getShop_code();
        final String[] shopCodes = shop_code.split(",");
        String shop_url = itemData.getShop_url();
        String[] shopUrls = shop_url.split(",");
        String shopPic1 = shopCodes[0].substring(1, 4) + "/" + shopCodes[0] + "/" + shopUrls[0];
        String shopPic2;
        try {
            shopPic2 = shopCodes[1].substring(1, 4) + "/" + shopCodes[1] + "/" + shopUrls[1];
        } catch (Exception e) {
            shopPic2 = "";
        }
        PicassoUtils.initImage(mContext, shopPic1, imgOneTv);
        PicassoUtils.initImage(mContext, shopPic2, imgTwoTv);

//        PicassoUtils.initImage(mContext, itemData.get("user_portrait"), headIv);
        GlideUtils.initRoundImage(Glide.with(mContext), mContext, itemData.getUser_portrait(), headIv);

//        if ("1".equals(itemData.getType())) {
//            groupsTv.setText("拼团发起人：");
////            headTzIcon.setVisibility(View.VISIBLE);
//        } else if ("2".equals(itemData.getType())) {
//            groupsTv.setText("拼团参与人：");
//            headTzIcon.setVisibility(View.GONE);
//        }
        if (itemData.getType() == 1) {
            groupsTv.setText("拼团发起人：");
//            headTzIcon.setVisibility(View.VISIBLE);
        } else{
            groupsTv.setText("拼团参与人：");
            headTzIcon.setVisibility(View.GONE);
        }

        groupsNicknameTv.setText(itemData.getUser_name());
        String addTimes = itemData.getAdd_time()+"";
        if (!"0".equals(addTimes)) {
            String add_time = df.format(new Date(Long.parseLong(addTimes)));
            groupsDateTv.setText(add_time.split("\\^")[0]);
            groupsDateTimesTv.setText(add_time.split("\\^")[1]);
        }

//        String shop_roll = "金额：¥" + itemData.get("shop_roll");
        String shop_roll = "实付：¥0";

        SpannableString textSpan = new SpannableString(shop_roll);
        textSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3F8B")), 3, shop_roll.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actualPayTv.setText(textSpan);

        String shop_name = itemData.getShop_name();
        String[] shopNames = shop_name.split(",");
        try {
            shopNameOneTv.setText(shopNames[0]);
        } catch (Exception e) {
            shopNameOneTv.setText("");
        }
        try {
            shopNameTwoTv.setText(shopNames[1]);
        } catch (Exception e) {
            shopNameTwoTv.setText("");
        }

        if (isMeal) {
            shopColorOneTv.setVisibility(GONE);
            shopColorTwoTv.setVisibility(GONE);
            shopSizeOneTv.setVisibility(GONE);
            shopSizeTwoTv.setVisibility(GONE);
        } else {
            String color = itemData.getColor();
            String[] colors = color.split("\\^");
            try {
                shopColorOneTv.setText("颜色：" + colors[0]);
            } catch (Exception e) {
                shopColorOneTv.setText("颜色：");
            }
            try {
                shopColorTwoTv.setText("颜色：" + colors[1]);
            } catch (Exception e) {
                shopColorTwoTv.setText("颜色：");
            }

            String size = itemData.getSize();
            String[] sizes = size.split("\\^");
            try {
                shopSizeOneTv.setText("尺码：" + sizes[0]);
            } catch (Exception e) {
                shopSizeOneTv.setText("尺码：");
            }
            try {
                shopSizeTwoTv.setText("尺码：" + sizes[1]);
            } catch (Exception e) {
                shopSizeTwoTv.setText("尺码：");
            }
        }


//        String p_price = itemData.get("p_price");
//        String[] prices = p_price.split("\\^");
//        try {
//            shopPriceOneTv.setText("¥" + prices[0]);
//        } catch (Exception e) {
//            shopPriceOneTv.setText("¥0.0");
//        }
//        try {
//            shopPriceTwoTv.setText("¥" + prices[1]);
//        } catch (Exception e) {
//            shopPriceTwoTv.setText("¥0.0");
//        }



        String shop_price = itemData.getShop_price()+"";



        String[] shop_prices = shop_price.split("\\^");
        try {


            shopElidePriceOneTv.setText("¥" + new java.text.DecimalFormat("#0.0").format(Double.parseDouble(shop_prices[0])));

        } catch (Exception e) {
            shopElidePriceOneTv.setText("¥0.0");
        }
        try {

            shopElidePriceTwoTv.setText("¥" + new java.text.DecimalFormat("#0.0").format(Double.parseDouble(shop_prices[1])));

        } catch (Exception e) {
            shopElidePriceTwoTv.setText("¥0.0");
        }



//        try {
//
//            shopElidePriceOneTv.setVisibility(GONE);
//        } catch (Exception e) {
//
//            shopElidePriceOneTv.setVisibility(GONE);
//
//        }
//        try {
//
//            shopElidePriceTwoTv.setVisibility(GONE);
//
//
//        } catch (Exception e) {
//
//
//            shopElidePriceTwoTv.setVisibility(GONE);
//
//
//        }


        mItemOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ShopDetailsActivity.class);
                intent.putExtra("code", shopCodes[0]);
                if (!isOneBuy) {
//                    intent.putExtra("isSignActiveShop", true);
                }
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

            }
        });
        mItemTwo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ShopDetailsActivity.class);
                intent.putExtra("code", shopCodes[1]);
//                intent.putExtra("isSignActiveShop", true);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
            }
        });

        if (isOneBuy) {
            if (itemData.getNoPay() == 1) {//已支付
                tv_nopay.setVisibility(GONE);
            } else {
                tv_nopay.setVisibility(GONE);
            }

        } else {
            tv_nopay.setVisibility(GONE);
        }
    }

}
