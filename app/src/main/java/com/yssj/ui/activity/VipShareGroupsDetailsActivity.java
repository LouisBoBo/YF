package com.yssj.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.BaseData;
import com.yssj.entity.Order;
import com.yssj.entity.RondomShop;
import com.yssj.model.ComModel2;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/***
 */
public class VipShareGroupsDetailsActivity extends BasicActivity implements OnClickListener {


    @Bind(R.id.img_shop_pic)
    ImageView imgShopPic;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.tv_product_color)
    TextView tvProductColor;
    @Bind(R.id.tv_product_size)
    TextView tvProductSize;
    @Bind(R.id.tv_price)
    TextView tvPrice;
//    @Bind(R.id.tv_shifu_price)
//    TextView tvShifuPrice;
    @Bind(R.id.tv_share_count)
    TextView tvShareCount;
    private Order order; // 订单

    private boolean isSubmit;

    private int needShareCount = 5;

    private Activity mActivity;

    private boolean shareClick = false;

    private String order_code;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_details_onebuy);
        context = this;
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        Bundle bundle = getIntent().getExtras();
        isSubmit = getIntent().getBooleanExtra("isSubmit", false);
        mActivity = this;
        needShareCount = Integer.parseInt(SharedPreferencesUtil.getStringData(this, "VIP_SHARE_NEED_COUNT", "5"));
        tvShareCount.setText(needShareCount + "");


        if (isSubmit) {
            order_code = getIntent().getStringExtra("order_code");
            getOrder();

        } else {
            order = (Order) bundle.getSerializable("order");
            initData();


        }


    }

    private void getOrder() {


        new SAsyncTask<Void, Void, Order>(this, R.string.wait) {

            @Override
            protected Order doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.getMyOrderPaysuccss(context, order_code);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(final FragmentActivity context, final Order result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    order = result;
                    initData();


                }
            }

        }.execute();


    }

    private void initData() {
        String picUrl = order.getList().get(0).getShop_code().substring(1, 4) + "/" + order.getList().get(0).getShop_code() + "/"
                + order.getList().get(0).getShop_pic();

        tvShopName.setText(order.getOrder_name());
        PicassoUtils.initImage(this, picUrl, imgShopPic);
        tvPrice.setText("￥" + order.getList().get(0).getOriginal_price());
//        tvShifuPrice.setText("￥" + order.getOrder_price() + "");


        if (null == order.getList().get(0).getColor()) {
            tvProductColor.setVisibility(View.GONE);
        } else {
            tvProductColor.setText("颜色：" + order.getList().get(0).getColor());

        }

        if (null == order.getList().get(0).getSize()) {
            tvProductSize.setVisibility(View.GONE);
        } else {
            tvProductSize.setText("尺寸：" + order.getList().get(0).getSize());

        }


    }


    @OnClick({R.id.img_back, R.id.ll_order, R.id.tv_share1, R.id.tv_share2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_order:
                startActivity(new Intent(this, ShopDetailsActivity.class).putExtra("code",order.getList().get(0).getShop_code()));
                break;
            case R.id.tv_share1:
                share();

                break;
            case R.id.tv_share2:
                share();

                break;
        }
    }

    private void share() {

        shareWaitDialog.show();

        HashMap<String, String> pairsMap = new HashMap<>();
        pairsMap.put("getShop", "true");

        YConn.httpPost(this, YUrl.GET_SHARE_SHOP_LINK_HOBBY, pairsMap, new HttpListener<RondomShop>() {
            @Override
            public void onSuccess(RondomShop rondomShop) {

                String shop_code = rondomShop.getShop().getShop_code();
                String four_pic = rondomShop.getShop().getFour_pic();
                String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + shop_code +
                        "&isShareFlag=true&user_id=" + YCache.getCacheUser(mActivity).getUser_id();

                String shareMIniAPPimgPic = YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + rondomShop.getShop().getDef_pic() + "!280";
                try {
                    shareMIniAPPimgPic = YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + four_pic.split(",")[2] + "!280";
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //分享到微信统一分享小程序
                WXminiAPPShareUtil.shareShopToWXminiAPP(mActivity, rondomShop.getShop().getShop_name(),
                        rondomShop.getShop().getApp_shop_group_price() + "", shareMIniAPPimgPic, wxMiniPathdUO, false);
                shareClick = true;

                WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
                    @Override
                    public void wxMiniShareSuccess() {

                    }
                });


            }

            @Override
            public void onError() {
                shareWaitDialog.dismiss();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (shareClick) {
            if (needShareCount == 1) { //已经全部分享完毕---去抽奖



//                ToastUtil.showShortText2("去抽奖");

                goToLuckPan();


                SharedPreferencesUtil.saveStringData(this, "VIP_SHARE_NEED_COUNT", 5 + "");
                tvShareCount.setText(needShareCount + "");
                shareClick = false;
                return;
            }
            needShareCount--;
            SharedPreferencesUtil.saveStringData(this, "VIP_SHARE_NEED_COUNT", needShareCount + "");
            tvShareCount.setText(needShareCount + "");
            shareClick = false;

        }
    }

    private void goToLuckPan() {
        shareWaitDialog.show();
        HashMap<String, String> pairsMap = new HashMap<>();
        pairsMap.put("order_code", order.getOrder_code());

        YConn.httpPost(this, YUrl.UPDATE_ORDER_FRIENDS_SHARE, pairsMap, new HttpListener<BaseData>() {
            @Override
            public void onSuccess(BaseData baseData) {

                //去转盘
                Intent OneBuyintent = new Intent(context, OneBuyChouJiangActivity.class);
                OneBuyintent.putExtra("isMeal", "1".equals(order.getIsTM()));

                Bundle bundle = new Bundle();
                bundle.putSerializable("order", order);
                OneBuyintent.putExtras(bundle);
                context.startActivity(OneBuyintent);
                ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                shareWaitDialog.dismiss();
                finish();
            }

            @Override
            public void onError() {
                shareWaitDialog.dismiss();
            }
        });

    }
}
