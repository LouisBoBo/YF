package com.yssj.custom.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.socialize.utils.DeviceConfig;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.app.SAsyncTask;
import com.yssj.data.YDBHelper;
import com.yssj.entity.Shop;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongjiShareCount;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

import java.util.HashMap;

import static com.yssj.ui.activity.shopdetails.MealShopDetailsActivity.shareWaitDialog;

/**
 * 新特卖商品分享popuwindow
 */
public class NewMealOneSharePopupwindow extends PopupWindow implements View.OnClickListener {

    private Activity mActivity;
    private TextView tv;
    private ImageView colse;
    private LinearLayout iv_wxin_share;


    public static ImageView iv_img;

    private Shop mShop;
    private YDBHelper dbHelp;



    public NewMealOneSharePopupwindow(Activity activity, Shop shop, String needONEprice) {
        super(activity);
        this.mActivity = activity;
        this.mShop = shop;
        initView(activity);
    }


    private boolean mWxInstallFlag = true;// true代表安装了微信


    @SuppressLint("ResourceAsColor")
    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.share_popupwindow_onebuy, null);

        tv = (TextView) rootView.findViewById(R.id.tv_tv);

        String onPrice = mShop.getApp_shop_group_price();
        onPrice =  new java.text.DecimalFormat("#0.0")
                .format(Double.parseDouble(onPrice));

//        OneBuyChouJiangActivity.isNewMeal = true;

//        if(ShopDetailsActivity.isNewUser){
//            tv.setText("分享到任意微信群后即可"+0+"元购买");
//
//        }else{
            tv.setText("分享到任意微信群后即可"+onPrice+"元购买");

//        }
        colse = (ImageView) rootView.findViewById(R.id.iv_close);
        iv_wxin_share = (LinearLayout) rootView.findViewById(R.id.iv_wxin_share);

        colse.setOnClickListener(this);
        iv_wxin_share.setOnClickListener(this);



        try {
            // // 是否安装了微信
            if (WXcheckUtil.isWeChatAppInstalled(context)) {
                mWxInstallFlag = true;
            } else {
                mWxInstallFlag = false;
            }
        } catch (Exception e) {
        }

        setContentView(rootView);

        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.style.mypopwindow_anim_style);
        setFocusable(true);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable(R.color.white));


//        dbHelp = new YDBHelper(mActivity);
//
//        String sql = "select * from sort_info where is_show = 1 order by _id";
//        List<HashMap<String, String>> sed = dbHelp.query(sql);
//        if(sed.size() > 0){
//            for (int i = 0; i< sed.size();i++){
//
//                HashMap map = sed.get(i);
//
//                for (int )
//
//            }
//        }



    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.iv_wxin_share:


                if(!mWxInstallFlag){
                    ToastUtil.showShortText(mActivity,"您尚未安装微信哦~");
                }


                shareWaitDialog.show();
                new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mActivity, R.string.wait) {

                    @Override
                    protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                            throws Exception {

                        return ComModel2.getShopLink(params[0], context, "true");
                    }

                    @Override
                    protected boolean isHandleException() {
                        return true;
                    }

                    @Override
                    protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                        super.onPostExecute(context, result, e);


                        if (null == e) {

                            if (result.get("status").equals("1")) {
//                                getTyepe2SuppLabel(result.get("four_pic").toString());




                                TongjiShareCount.tongjifenxiangCount();
                                TongjiShareCount.tongjifenxiangwho(mShop.getShop_code());


                                String shareMIniAPPimgPic = YUrl.imgurl + mShop.getShop_code().substring(1, 4) + "/" + mShop.getShop_code() + "/" + mShop.getDef_pic() + "!280";
                                String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + mShop.getShop_code() +
                                        "&isShareFlag=true&user_id=" + YCache.getCacheUser(mActivity).getUser_id();


                                String onPrice = GuideActivity.oneShopPrice;
//                    onPrice = new java.text.DecimalFormat("#0")
//                            .format(Double.parseDouble(onPrice));

//                                OneBuyChouJiangActivity.mealShopName = mShop.getShop_name()+"";
//                                String shareText = "快来" + onPrice + "元抢【" + mShop.getSupp_label() +"正品" + result.get("type2")+ "】，专柜价" + mShop.getShop_se_price() + "元!";
                                String shareText = "快来"+onPrice+"元拼【"+mShop.getShop_name()+"】，原价"+mShop.getShop_se_price()+"元！";

                                //分享到微信统一分享小程序
                                WXminiAPPShareUtil.shareToWXminiAPP(mActivity, shareMIniAPPimgPic, shareText, wxMiniPathdUO, false);

                                WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
                                    @Override
                                    public void wxMiniShareSuccess() {
                                        ToastUtil.showShortText(mActivity,"分享成功");
                                        mOneBuyShareCompleteListener.oneBuyShareComplete();

                                    }
                                });


                                if (null != shareWaitDialog) {
                                    shareWaitDialog.dismiss();
                                }
                                dismiss();


                            }
                        } else {
                            if (null != shareWaitDialog) {
                                shareWaitDialog.dismiss();
                            }
                        }
                    }

                }.execute(mShop.getShop_code());

                break;

        }

    }



    public static OneBuyShareCompleteListener mOneBuyShareCompleteListener;
    public static void setBuyShareCompleteListener(OneBuyShareCompleteListener oneBuyShareCompleteListener){
        mOneBuyShareCompleteListener = oneBuyShareCompleteListener;
    }

    public interface OneBuyShareCompleteListener{
        void oneBuyShareComplete();
    }

}