package com.yssj.custom.view;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.serializer.ListSerializer;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.utils.DP2SPUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class CustImageGalleryIntimate extends LinearLayout {

    private LinearLayout mGroup;

    private Context context;
    private CustomIntimateItem customImages;
    private java.text.DecimalFormat pFormate;
    private LayoutInflater mInflater;

    public CustImageGalleryIntimate(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.my_horizontal_view, this, true);
        mGroup = (LinearLayout) findViewById(R.id.hor_group);
    }

    public void setData(List<HashMap<String, Object>> list) {
        pFormate = new DecimalFormat("#0.0");
        mGroup.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            customImages = new CustomIntimateItem(context);
            int dp = DP2SPUtil.dp2px(context, 10);

//			customImages.setLayoutParams(getLayoutParams());
            customImages.setPadding(0, 0, dp, 0);

            final String shop_code = list.get(i).get("shop_code").toString();
            String url = shop_code.substring(1, 4) + File.separator + shop_code
                    + File.separator + list.get(i).get("def_pic").toString();
            customImages.setImageView(url);
            String supp_label = (String) list.get(i).get("supp_label");
//			String sPrice = (String) list.get(i).get("shop_se_price");
            double sPrice = Double.parseDouble(list.get(i).get("shop_se_price").toString());

            if (GuideActivity.show1yuan) {
                customImages.setTextViewOne(supp_label,list.get(i).get("app_shop_group_price")+"");
            } else {

                customImages.setTextView(supp_label, "¥" + pFormate.format(sPrice * 0.9));//显示九折价格

            }


            customImages.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (!TextUtils.isEmpty(shop_code)) {
                        addScanDataTo(shop_code);
                        Intent intent = new Intent(context,
                                ShopDetailsActivity.class);
                        intent.putExtra("code", shop_code);
                        context.startActivity(intent);
                        ((FragmentActivity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                    }

                }
            });
            mGroup.addView(customImages);
        }
    }

    /**
     * 话题详情的商品推荐 可以查看更更多
     *
     * @param list    商品列表
     * @param themeId 帖子ID
     */
//	public void setData(List<HashMap<String, Object>> list,final String themeId) {
//		pFormate=new DecimalFormat("#0.0");
//		mGroup.removeAllViews();
//		int length = list.size()<20?list.size():20;
//		for (int i = 0; i < length; i++) {
//			customImages= new CustomIntimateItem(context);
//			int dp = DP2SPUtil.dp2px(context, 5);
//
//			customImages.setLayoutParams(getLayoutParams());
//			customImages.setPadding(0, 0, dp, 0);
//
//			final String shop_code = list.get(i).get("shop_code").toString();
//			String url = shop_code.substring(1, 4)+File.separator+shop_code
//					+File.separator+list.get(i).get("def_pic").toString();
//			customImages.setImageView(url);
//			String supp_label = (String) list.get(i).get("supp_label");
////			String sPrice = (String) list.get(i).get("shop_se_price");
////			customImages.setTextView(supp_label,"¥"+sPrice);
//			double sPrice = Double.parseDouble(list.get(i).get("shop_se_price").toString());
//			customImages.setTextView(supp_label,"¥"+pFormate.format(sPrice*0.9));//显示九折价格
//
//
//			customImages.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					if(!TextUtils.isEmpty(shop_code)){
//						addScanDataTo(shop_code);
//						Intent intent = new Intent(context,
//								ShopDetailsActivity.class);
//						intent.putExtra("code", shop_code);
//						context.startActivity(intent);
//						((FragmentActivity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
//					}
//
//				}
//			});
//			mGroup.addView(customImages);
//		}
//		if(length>=20){
//			customImages= new CustomIntimateItem(context);
//			customImages.findViewById(R.id.image_zuhe_iv).setVisibility(View.GONE);
//			customImages.findViewById(R.id.more_tv).setVisibility(View.VISIBLE);
//			customImages.findViewById(R.id.image_manfaccture_fl).setVisibility(View.GONE);
//			customImages.findViewById(R.id.image_price_tv).setVisibility(View.INVISIBLE);
//			int dp = DP2SPUtil.dp2px(context, 5);
//			customImages.setLayoutParams(getLayoutParams());
//			customImages.setPadding(0, 0, dp, 0);
//			customImages.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					Intent intent2 = new Intent(context,ForceLookActivity.class);
//					intent2.putExtra("title","更多推荐");
//					intent2.putExtra("isMoreShop", true);
//					intent2.putExtra("themeId", themeId);
//					context.startActivity(intent2);
//					((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//				}
//			});
//			mGroup.addView(customImages);
//		}
//	}
    public void setDataSweetDet(List<HashMap<String, Object>> list, final String themeId, final boolean isSweetDet) {
        pFormate = new DecimalFormat("#0.0");
        mGroup.removeAllViews();
        int length = list.size() < 20 ? list.size() : 20;
        for (int i = 0; i < length; i++) {
            customImages = new CustomIntimateItem(context, isSweetDet);
            int dp = DP2SPUtil.dp2px(context, 5);

            customImages.setLayoutParams(getLayoutParams());
            customImages.setPadding(0, 0, dp, 0);

            final String shop_code = list.get(i).get("shop_code").toString();
            String url = shop_code.substring(1, 4) + File.separator + shop_code
                    + File.separator + list.get(i).get("def_pic").toString();
            customImages.setImageView(url);
            String supp_label = (String) list.get(i).get("supp_label");
//			String sPrice = (String) list.get(i).get("shop_se_price");
//			customImages.setTextView(supp_label,"¥"+sPrice);
            double sPrice = Double.parseDouble(list.get(i).get("shop_se_price").toString());
            customImages.setTextView(supp_label, "¥" + pFormate.format(sPrice * 0.9));//显示九折价格

            if (GuideActivity.show1yuan) {
                customImages.setTextViewOne(supp_label,list.get(i).get("app_shop_group_price")+"");
            } else {

                customImages.setTextView(supp_label, "¥" + pFormate.format(sPrice * 0.9));//显示九折价格

            }



            customImages.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (!TextUtils.isEmpty(shop_code)) {
                        addScanDataTo(shop_code);
                        Intent intent = new Intent(context,
                                ShopDetailsActivity.class);
                        intent.putExtra("code", shop_code);
                        context.startActivity(intent);
                        ((FragmentActivity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                    }

                }
            });
            mGroup.addView(customImages);
        }
        if (length >= 20) {
//			customImages= new CustomIntimateItem(context);

            customImages = new CustomIntimateItem(context, isSweetDet);

            customImages.findViewById(R.id.image_zuhe_iv).setVisibility(View.GONE);
            customImages.findViewById(R.id.more_tv).setVisibility(View.VISIBLE);
            customImages.findViewById(R.id.image_manfaccture_fl).setVisibility(View.GONE);
            customImages.findViewById(R.id.image_price_tv).setVisibility(View.INVISIBLE);
            int dp = DP2SPUtil.dp2px(context, 5);
            customImages.setLayoutParams(getLayoutParams());
            customImages.setPadding(0, 0, dp, 0);
            customImages.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(context, ForceLookActivity.class);
                    intent2.putExtra("title", "更多推荐");
                    intent2.putExtra("isMoreShop", true);
                    intent2.putExtra("themeId", themeId);
                    context.startActivity(intent2);
                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                }
            });
            mGroup.addView(customImages);
        }
    }

    public HorizontalScrollView getScroll() {

        return (HorizontalScrollView) findViewById(R.id.my_h_scroll);

    }

    /*
     * 把浏览过的数据添加进数据库
     */
    private void addScanDataTo(final String shop_code) {
        new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel.addMySteps(context, shop_code);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
            }

        }.execute();
    }
}
