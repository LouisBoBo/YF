package com.yssj.custom.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.utils.PicassoUtils;

public class CustomIntimateItem extends LinearLayout {
    private Context mContext;
    private ImageView iv;
    private TextView tvManfaccture;
    private TextView tvPrice;
    boolean isSweetDet;
    private  LinearLayout ll_one;
    private TextView tv_one_price;


    public CustomIntimateItem(Context context) {
        super(context);
        mContext = context;
        initView(context);
    }

    public CustomIntimateItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
    }


    public CustomIntimateItem(Context context, boolean isSweetDet) {
        super(context);
        mContext = context;
        this.isSweetDet = isSweetDet;
        initView(context);
    }

    public CustomIntimateItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {


        if (isSweetDet) {
            LayoutInflater.from(context).inflate(
                    R.layout.intimate_item_group_sweet_det, this, true);
        } else {
            LayoutInflater.from(context).inflate(
                    R.layout.intimate_item_group, this, true);
        }

        ll_one = (LinearLayout) this.findViewById(R.id.ll_one);
        tv_one_price = (TextView) this.findViewById(R.id.tv_one_price);

        iv = (ImageView) this.findViewById(R.id.image_zuhe_iv);
        tvManfaccture = (TextView) this.findViewById(R.id.image_manfaccture_tv);
        tvPrice = (TextView) this.findViewById(R.id.image_price_tv);
    }

    public void setImageView(String url) {
//		SetImageLoader.initImageLoader(mContext, iv,
//				url, "!280");

        PicassoUtils.initImage(mContext, url + "!280", iv);


    }

    public void setTextView(String manfaccture, String price) {
        if (!TextUtils.isEmpty(manfaccture)) {
            String manfacctureText = manfaccture + "";
//            if (manfacctureText.length() > 7) {
//                manfacctureText = manfacctureText.substring(0, 7) + "...";
//            }
            tvManfaccture.setText(manfacctureText);
        } else {
            tvManfaccture.setText("");
        }
        tvPrice.setText(price);
    }
    public void setTextViewOne(String manfaccture ,String app_shop_group_price) {
        tvPrice.setVisibility(GONE);
        if (!TextUtils.isEmpty(manfaccture)) {
            String manfacctureText = manfaccture;
            tvManfaccture.setText(manfacctureText);
        } else {
            tvManfaccture.setText("");
        }

        if (!TextUtils.isEmpty(manfaccture)) {
            String manfacctureText = manfaccture;
            tvManfaccture.setText(manfacctureText);
        } else {
            tvManfaccture.setText("");
        }



        ll_one.setVisibility(VISIBLE);



//        onPrice =  new java.text.DecimalFormat("#0")
//                .format(Double.parseDouble(onPrice));

        tv_one_price.setText(app_shop_group_price+"元");

        tvPrice.setVisibility(GONE);

    }

}
