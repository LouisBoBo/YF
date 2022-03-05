package com.yssj.custom.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.Shop;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;

public class CustomMatchTuijianItem extends LinearLayout {
    private Context mContext;
    private ImageView iv, ivZhezhao;
    private TextView tvTitle;
    private TextView tvPrice;
    private TextView tvSupple;
    private TextView tv_one_price;
    private View image_manfaccture_fl;

    private LinearLayout ll_one;

    public CustomMatchTuijianItem(Context context) {
        super(context);
        mContext = context;
        initView(context);
    }

    public CustomMatchTuijianItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
    }

    public CustomMatchTuijianItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(
                R.layout.match_tuijian_item_group, this, true);
        iv = (ImageView) this.findViewById(R.id.image_zuhe_iv);
        tvTitle = (TextView) this.findViewById(R.id.image_title_tv);
        tvPrice = (TextView) this.findViewById(R.id.image_price_tv);
        tvSupple = (TextView) this.findViewById(R.id.image_manfaccture_tv);
        image_manfaccture_fl = this.findViewById(R.id.image_manfaccture_fl);
        ivZhezhao = (ImageView) this.findViewById(R.id.image_manfaccture_zhezhao);
        ll_one = (LinearLayout) this.findViewById(R.id.ll_one);
        tv_one_price = (TextView) this.findViewById(R.id.tv_one_price);
    }

    public void setImageView(String url) {
//		SetImageLoader.initImageLoader(mContext, iv,
//				url, "!280");

        PicassoUtils.initImage(mContext, url + "!280", iv);


    }

    public void setTextView(String name, String price, String suppleName) {
        tvTitle.setText(Shop.getShopNameStrNew(name));
        tvPrice.setText(price);
        if (!TextUtils.isEmpty(suppleName)) {
            String suppleNameText = suppleName + "制造商出品";
            tvSupple.setText(suppleNameText);
            image_manfaccture_fl.setVisibility(View.VISIBLE);
            tvSupple.setVisibility(View.VISIBLE);
            ivZhezhao.setVisibility(View.VISIBLE);
        } else {
            tvSupple.setText("");
            image_manfaccture_fl.setVisibility(View.GONE);
            tvSupple.setVisibility(View.GONE);
            ivZhezhao.setVisibility(View.GONE);
        }
    }

    public void setTextView(String name, String price) {
        tvTitle.setText(Shop.getShopNameStrNew(name));
        tvPrice.setText(price);
//		if(!TextUtils.isEmpty(suppleName)){
//			String suppleNameText  = suppleName+"制造商出品";
//			tvSupple.setText(suppleNameText);	
//		}else{
//			tvSupple.setText("");	
//		}
        image_manfaccture_fl.setVisibility(View.GONE);
        tvSupple.setVisibility(View.GONE);
        ivZhezhao.setVisibility(View.GONE);
    }

    public void setTextViewOne(String name) {
        tvTitle.setText(Shop.getShopNameStrNew(name));


        ll_one.setVisibility(VISIBLE);
        String onPrice = GuideActivity.oneShopPrice;

//        onPrice = new java.text.DecimalFormat("#0")
//                .format(Double.parseDouble(onPrice));

        tv_one_price.setText(onPrice + "元");


        tvPrice.setVisibility(GONE);
        tvSupple.setVisibility(GONE);
        image_manfaccture_fl.setVisibility(View.GONE);
        tvSupple.setVisibility(View.GONE);
        ivZhezhao.setVisibility(View.GONE);
    }


}
