package com.yssj.ui.activity.shopdetails;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.entity.HotShop;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/3/26 0026.
 */


public class FreeBuyShareDialog extends Dialog {


    public static int needShareCount = 2;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.ll_share)
    LinearLayout llShare;
    private Context mContext;

    public FreeBuyShareDialog(@NonNull Context context) {
        super(context, R.style.DialogStyle);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_shopdetail_free_buy_share);
        ButterKnife.bind(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);


        Window window = getWindow();
        window.setWindowAnimations(R.style.dlg_down_to_top); // 添加动画

        if(needShareCount < 2){
            tvTitle.setText(Html.fromHtml("再分享<font color='#ff3f8b'><strong><big>" + needShareCount + "</big></strong></font>件美衣到微信群，即可免费领本商品"));

        }else{
            tvTitle.setText(Html.fromHtml("分享<font color='#ff3f8b'><strong><big>" + needShareCount + "</big></strong></font>件美衣到微信群，即可免费领本商品"));

        }



    }

    @OnClick({R.id.iv_close, R.id.ll_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.ll_share:

                dismiss();

                ShopDetailsActivity.shareWaitDialog.show();

                //分享热卖随机商品
                HashMap<String, String> map = new HashMap<>();
                map.put("getShop", "true");

                YConn.httpPost(mContext, YUrl.GET_SHARE_SHOP_LINK_HOBBY, map, new HttpListener<HotShop>() {
                    @Override
                    public void onSuccess(HotShop shop) {
                        String shopCode = shop.getShop().getShop_code();

                        String shareTitle = "点击购买👆【" + shop.getShop().getShop_name() + "】今日特价" + shop.getShop().getAssmble_price() + "元！";
//                        String sharePath = "/pages/shouye/redHongBao?isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();


//                                YUrl.imgurl + shop.getShop().getShop_code().substring(1, 4) + "/" +
//                                shop.getShop().getShop_code() + "/" + shop.getShop().getFour_pic().split(",")[2] + "!280";
//
                        String sharePath = "/pages/mine/toexamine_test/toexamine_test?shouYePage=ThreePage"+ "&isShareFlag=true" + "&user_id=" + YCache.getCacheUser(mContext).getUser_id();

                        String shareCode = shopCode.substring(1, 4);
                        String shareTempPic = shop.getShop().getFour_pic().split(",")[2];


//                        String sharePic = YUrl.imgurl + shareCode + "/" + shopCode + "/" + shareTempPic +  "!382";

                        String shareMIniAPPimgPic = YUrl.imgurl + shop.getShop().getShop_code().substring(1, 4) + "/" + shop.getShop().getShop_code() + "/" + shop.getShop().getFour_pic().split(",")[2] + "!280";


                        //分享到微信统一分享小程序
//                        WXminiAPPShareUtil.shareShopToWXminiAPP(mContext, sharePic, shareTitle, sharePath, false);
                        if(null != ShopDetailsActivity.shareWaitDialog){
                            ShopDetailsActivity.shareWaitDialog.dismiss();
                        }

                        WXminiAPPShareUtil.shareShopToWXminiAPP(mContext,
                                shop.getShop().getShop_name(),
                                shop.getShop().getAssmble_price()+"",

                                shareMIniAPPimgPic, sharePath, false);

//                    public static void shareShopToWXminiAPP(final Context mContext, String shop_name, final String shop_group_price, String imgUrl, final String wxMiniPath, final boolean isShareSign) {


                        WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
                            @Override
                            public void wxMiniShareSuccess() {

                                ToastUtil.showShortText2("分享成功~");

                                if (needShareCount == 1) {
                                    needShareCount--;
                                    freeBuyShareFinishListener.shareSuccess();

                                } else {
                                    needShareCount--;


                                    FreeBuyShareDialog dialog = new FreeBuyShareDialog(mContext);
//                            Window window = dialog.getWindow();
//                            window.setGravity(Gravity.BOTTOM);
//                            window.setWindowAnimations(R.style.dlg_down_to_top);
                                    dialog.show();


                                }

                            }
                        });


                    }

                    @Override
                    public void onError() {
                        if(null != ShopDetailsActivity.shareWaitDialog){
                            ShopDetailsActivity.shareWaitDialog.dismiss();
                        }
                    }
                });


//                String shareText = "月销3万件，券后仅售9.9元的超仙韩版连衣裙，快来抢";
//                String wxMiniPath = "/pages/shouye/redHongBao?isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();
//                String shareMIniAPPimgPic = YUrl.imgurl + "wxcx/share_links/redpackets.jpg";

//                //分享到微信统一分享小程序
//                WXminiAPPShareUtil.shareToWXminiAPP(mContext, shareMIniAPPimgPic, shareText, wxMiniPath, false);
//                WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
//                    @Override
//                    public void wxMiniShareSuccess() {
//                        if (needShareCount == 1) {
//                            needShareCount--;
//                            freeBuyShareFinishListener.shareSuccess();
//
//                        } else {
//                            needShareCount--;
//
//
//                            FreeBuyShareDialog dialog = new FreeBuyShareDialog(mContext);
////                            Window window = dialog.getWindow();
////                            window.setGravity(Gravity.BOTTOM);
////                            window.setWindowAnimations(R.style.dlg_down_to_top);
//                            dialog.show();
//
//
//                        }
//
//                    }
//                });


                break;
        }
    }


    public static FreeBuyShareFinishListener freeBuyShareFinishListener;

    public interface FreeBuyShareFinishListener {
        void shareSuccess();
    }


}
