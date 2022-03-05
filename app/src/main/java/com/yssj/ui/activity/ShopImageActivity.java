package com.yssj.ui.activity;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.IndianaPayDialog;
import com.yssj.custom.view.IndianaPayPopupWinDow;
import com.yssj.custom.view.ToLoginDialog;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.Shop;
import com.yssj.entity.ShopCart;
import com.yssj.entity.StockType;
import com.yssj.entity.Store;
import com.yssj.entity.UserInfo;
import com.yssj.huanxin.widget.photoview.PhotoView;
import com.yssj.huanxin.widget.photoview.PhotoViewAttacher.OnPhotoTapListener;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.shopdetails.MealPaymentActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsDialog;
import com.yssj.ui.activity.shopdetails.StockBean;
import com.yssj.ui.activity.shopdetails.SubmitDuobaoOrderActivity;
import com.yssj.ui.activity.shopdetails.SubmitMultiShopActivty;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.SignCompleteDialogUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.YCache;
import com.yssj.utils.sqlite.ShopCartDao;

//import static com.yssj.ui.activity.shopdetails.ShopDetailsIndianaActivity.firstJoin;
//import static com.yssj.ui.activity.shopdetails.ShopDetailsIndianaActivity.my_num;
//import static com.yssj.ui.activity.shopdetails.ShopDetailsIndianaActivity.needCount;

/**
 * 商品详情点击放大图
 *
 * @author lbp
 */
public class ShopImageActivity extends BasicActivity implements IndianaPayDialog.IndianaDialogInterface, IndianaPayPopupWinDow.IndianaInterFace {

    private Shop shop;

    private boolean isMeal = false;
    private boolean isMembers = false;
    private String signShopDetail;
    private int signType;
    private String signValue;
    // private String signValue;
    private HashMap<String, Object> mealMap;
    private String code;
    private boolean isIndiana = false;
    private int mOstatus;
    private boolean isSignActiveShop;
    private String mSupp_label = "";// 供应商名字
    private int needPeopleCount = 0;//夺宝还需要的人数
    public boolean firstJoin;//是否是第一次参与夺宝
    public int my_num;// 我的参与次数
    public int needCount;//开奖剩余人数
    private boolean moneyIndianaFlag = false;//true代表提现额度夺宝
    private boolean isGroupIndianaFlag = false;//true代表拼团夺宝
    private int mGroupPeopleCount = 0;//拼团夺宝需要成团人数
    private String issue_code = "";

    private Context context;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_image_activity);
        context = this;
        firstJoin = getIntent().getBooleanExtra("firstJoin", false);
        my_num = getIntent().getIntExtra("my_num", 0);
        needCount = getIntent().getIntExtra("needCount", 0);

        number_sold_single = getIntent().getStringExtra("number_sold");
        shopCart = getIntent().getBooleanExtra("ShopCart", false);
        isSignActiveShop = getIntent().getBooleanExtra("isSignActiveShop", false);
        PhotoView p = (PhotoView) findViewById(R.id.img);
        String url = getIntent().getStringExtra("url");
        mSupp_label = getIntent().getStringExtra("supp_label");
        isMeal = getIntent().getBooleanExtra("isMeal", false);
        isMembers = getIntent().getBooleanExtra("isMembers", false);
        isIndiana = getIntent().getBooleanExtra("isIndiana", false);
        signShopDetail = getIntent().getStringExtra("signShopDetail");
        signType = getIntent().getIntExtra("signType", 0);
        signValue = getIntent().getStringExtra("signValue");
        code = getIntent().getStringExtra("code");
        moneyIndianaFlag = getIntent().getBooleanExtra("moneyIndianaFlag", false);
        isGroupIndianaFlag = getIntent().getBooleanExtra("groupIndiana", false);
        mGroupPeopleCount = getIntent().getIntExtra("group_number", 0);
        issue_code = getIntent().getStringExtra("issue_code");
        if (isMeal) {
            mealMap = (HashMap<String, Object>) getIntent().getSerializableExtra("mealMap");
            list = (List<Shop>) mealMap.get("shopList");
            findViewById(R.id.nmeal).setVisibility(View.GONE);
        } else if ("SignShopDetail".equals(signShopDetail)) {
            mealMap = (HashMap<String, Object>) getIntent().getSerializableExtra("mealMap");
            list = (List<Shop>) mealMap.get("shopList");
            findViewById(R.id.nmeal).setVisibility(View.GONE);
            findViewById(R.id.bt_meal_order).setVisibility(View.GONE);
            findViewById(R.id.sign_buy).setVisibility(View.VISIBLE);
        } else if (isIndiana) {
            mOstatus = getIntent().getIntExtra("ostatus", 0);
            int my_num = getIntent().getIntExtra("my_num", 0);
            needPeopleCount = getIntent().getIntExtra("need_people", 0);
            TextView tv = (TextView) findViewById(R.id.indiana_bt_meal_order);
            if (mOstatus == 0 || mOstatus == 4) {
                if (my_num <= 0) {


                    if (firstJoin) {
                        tv.setText("一分钱抽奖");
                    } else {
                        tv.setText("立即参与");
                    }

//                    //剩余参与次数未0，且未参与
//                    if(needCount == 0){
//                        tv.setText("正在开奖");
//                    }
//


                    tv.setBackgroundColor(Color.parseColor("#ff3f8b"));
                    tv.setClickable(true);
                    tv.setOnClickListener(this);
                } else {
                    tv.setText("再次参与");
//                    tv.setBackgroundColor(Color.parseColor("#c5c5c5"));
                    tv.setBackgroundColor(Color.parseColor("#ff3f8b"));
                    tv.setClickable(true);
                    tv.setOnClickListener(this);
                }


                //剩余参与次数为0，且未结束
                if (needCount == 0) {
                    tv.setText("正在开奖");
                }


            } else if (mOstatus == 2) {
                tv.setText("已结束");
                tv.setBackgroundColor(Color.parseColor("#c5c5c5"));
                tv.setClickable(false);
            } else if (mOstatus == 3) {
                tv.setText("已结束");
                tv.setBackgroundColor(Color.parseColor("#c5c5c5"));
                tv.setClickable(false);
            }
            findViewById(R.id.bt_meal_order).setVisibility(View.GONE);
            findViewById(R.id.indiana_bt_meal_order).setVisibility(View.VISIBLE);

            shop = (Shop) getIntent().getSerializableExtra("shop");

            findViewById(R.id.nmeal).setVisibility(View.GONE);
        } else {
            shop = (Shop) getIntent().getSerializableExtra("shop");
            findViewById(R.id.nmeal).setVisibility(View.GONE);
            findViewById(R.id.meal).setVisibility(View.GONE);
        }

//		SetImageLoader.initImageLoader(null, p, url, "");
        PicassoUtils.initImage(context, url, p);

        p.setOnPhotoTapListener(new OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View view, float x, float y) {
                onBackPressed();
            }
        });
        if (isMembers) {
            findViewById(R.id.bt_cart).setVisibility(View.GONE);
        }
        findViewById(R.id.bt_cart).setOnClickListener(this);
        findViewById(R.id.bt_order).setOnClickListener(this);
        findViewById(R.id.bt_order).setVisibility(View.GONE);

        findViewById(R.id.bt_meal_order).setOnClickListener(this);
        findViewById(R.id.sign_buy).setOnClickListener(this);

//		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.image_default)
//				.showImageForEmptyUri(R.drawable.ic_empty).cacheInMemory(false).bitmapConfig(Bitmap.Config.RGB_565)
//				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).considerExifParams(true)
//				// .displayer(new FadeInBitmapDisplayer(35))
//				.build();

        width = getResources().getDisplayMetrics().widthPixels;
        height = getResources().getDisplayMetrics().heightPixels;

        Button bt_meal_order = (Button) findViewById(R.id.bt_meal_order);

        // System.out.println("***////=" + number_sold_single);

        if (shopCart == false) {
            if (isMeal) {
                if (null != number_sold_single && number_sold_single.equals("none")) {
                    bt_meal_order
                            .setBackground(getResources().getDrawable(R.drawable.bg_square_choice_btn_checked_new));
                } else {
                    bt_meal_order.setBackground(getResources().getDrawable(R.drawable.bg_square_choice_btn_checked));
                }
            }
        }
        // if (isMeal) {
        // if (number_sold_single.equals("none")) {8
        // bt_meal_order.setBackground(getResources().getDrawable(
        // R.drawable.bg_square_choice_btn_checked_new));
        // } else {
        // bt_meal_order.setBackground(getResources().getDrawable(
        // R.drawable.bg_square_choice_btn_checked));
        // }
        // }
        if (isSignActiveShop) {
            findViewById(R.id.bt_cart).setVisibility(View.GONE);
            Button btn = (Button) findViewById(R.id.bt_order);
            btn.setText("立即购买");
            btn.setVisibility(View.VISIBLE);
        }
    }

    /***
     * 查找商品属性
     */
    private void queryShopQueryAttr(final int i) {
        if (shop != null) {
            List<StockType> list = shop.getList_stock_type();
            if (list != null && list.size() > 0) {
                showPopWindow(i);
            } else {
                new SAsyncTask<String, Void, Shop>(this, null, R.string.wait) {
                    @Override
                    protected Shop doInBackground(FragmentActivity context, String... params) throws Exception {
                        return ComModel.queryShopQueryAttr(ShopImageActivity.this, shop, params[0]);
                    }

                    @Override
                    protected void onPostExecute(FragmentActivity context, Shop shop, Exception e) {

                        if (e != null) {// 查询异常
                            Toast.makeText(ShopImageActivity.this, "连接超时，请重试", Toast.LENGTH_LONG).show();

                        } else {// 查询商品详情成功，刷新界面
                            if (shop != null) {
                                ShopImageActivity.this.shop = shop;
                                showPopWindow(i);
                            }
                        }

                    }

                    ;

                    @Override
                    protected boolean isHandleException() {
                        return true;
                    }

                    ;
                }.execute("false");
            }

        }
    }

//	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
//
//		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
//
//		@Override
//		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//			if (loadedImage != null) {
//				ImageView imageView = (ImageView) view;
//				boolean firstDisplay = !displayedImages.contains(imageUri);
//				if (firstDisplay) {
//					FadeInBitmapDisplayer.animate(imageView, 500);
//					displayedImages.add(imageUri);
//				}
//			}
//		}
//	}

//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private int width, height;

//	private DisplayImageOptions options;

    /****
     * 弹出底部对话框
     *
     * @param i
     */
    private void showPopWindow(int i) {
        if (shop != null && !this.isFinishing()) {
            final ShopDetailsDialog dlg = new ShopDetailsDialog(this, R.style.DialogStyle, width, height, shop,
                    i, false, "-1", "-1", "1");
            Window window = dlg.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.dlg_down_to_top);
            dlg.show();

            dlg.callBackShopCart = new ShopDetailsDialog.OnCallBackShopCart() {

                @Override
                public void callBackChoose(int type, String size, String color, double price, int shop_num,
                                           int stock_type_id, int stock, String pic, int supp_id, double kickback, int original_price,
                                           View v) {
                    dlg.dismiss();
                    if (type == 1) {// 购买 活动商品直接购买
                        // Intent intent = new Intent(ShopImageActivity.this,
                        // SubmitOrderActivity.class);
                        Intent intent = new Intent(ShopImageActivity.this, SubmitMultiShopActivty.class);
                        Bundle bundle = new Bundle();
                        // bundle.putSerializable("shop", shop);
                        // intent.putExtras(bundle);
                        // intent.putExtra("size", size);
                        // intent.putExtra("color", color);
                        // intent.putExtra("shop_num", shop_num);
                        // intent.putExtra("stock_type_id", stock_type_id);
                        // intent.putExtra("stock", stock);
                        // intent.putExtra("price", price);
                        // intent.putExtra("original_price", original_price);
                        // intent.putExtra("pic", pic);
                        List<ShopCart> listGoods = new ArrayList<ShopCart>();
                        ShopCart shopCart = new ShopCart();
                        shopCart.setShop_code(shop.getShop_code());
                        shopCart.setShop_num(shop_num);
                        shopCart.setSize(size);
                        shopCart.setColor(color);
                        shopCart.setShop_price(shop.getShop_price());
                        shopCart.setShop_se_price(shop.getShop_se_price());
                        shopCart.setOriginal_price(Double.valueOf(original_price));
                        shopCart.setDef_pic(pic);
                        shopCart.setStock_type_id(stock_type_id);
                        shopCart.setSupp_id(shop.getSupp_id());
                        shopCart.setShop_name(shop.getShop_name());
                        shopCart.setCore("0");
                        shopCart.setKickback(0.0);
                        shopCart.setUser_id(YCache.getCacheUser(context).getUser_id());
                        shopCart.setStore_code(YCache.getCacheStore(context).getS_code());
                        listGoods.add(shopCart);

                        bundle.putSerializable("listGoods", (Serializable) listGoods);
                        bundle.putBoolean("isSignActiveShop", isSignActiveShop);
                        bundle.putString("rollCode", "0");
                        intent.putExtras(bundle);

                        startActivity(intent);
                    } else {// 加入购物车

                        if (TextUtils.isEmpty(pic)) {
                            pic = shop.getDef_pic();
                        }
                        // setAnim(null);
                        joinShopCart(size, color, shop_num, stock_type_id, pic, price, supp_id, kickback,
                                original_price, v);
                    }
                }
            };
        }
    }

    /***
     * 加入购物车
     *
     * @param size
     * @param color
     * @param shop_num
     * @param v
     */
    // private Drawable d;
    int id_dao;

    @Override
    protected void onResume() {
        super.onResume();

        if (IndianaPayDialog.wxClick) {

            if (!IndianaPayDialog.isCommit) {
                IndianaPayDialog.isCommit = true;
                IndianaPayDialog.submitIndianaShareRecord();
            }


        }


    }

    private void joinShopCart(final String size, final String color, final int shop_num, final int stock_type_id,
                              final String pic, final double realPrice, final int supplyId, final double kickback,
                              final int original_price, final View v) {
        final ShopCartDao dao = new ShopCartDao(context);
//		if (dao.queryCartCommonCount(context) + shop_num > 20) {
//			ToastUtil.showShortText(context, "购物车最多允许加入20件有效商品");
//			return;
//		}
        // clickFlag = false;
        // addAnimLayout();// 添加动画布局 小圆点

        List<ShopCart> list = dao.findAll();
        // List<ShopCart> list_invalid = dao.findAll_invalid();
        boolean id_flag = false;
        // boolean id_flag_invalid = false;// 失效列表有
        int shop_num_old = 0;
        // int shop_num_old_invalid = 0;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (("" + stock_type_id).equals("" + list.get(i).getStock_type_id())) {
                    id_flag = true;
                    id_dao = list.get(i).getId();
                    shop_num_old = list.get(i).getShop_num();
                    break;
                }
            }
        }
        // if (list_invalid != null) {
        // for (int i = 0; i < list_invalid.size(); i++) {
        // if (("" + stock_type_id).equals("" +
        // list_invalid.get(i).getStock_type_id())) {
        // id_flag_invalid = true;
        // id_dao = list_invalid.get(i).getId();
        // shop_num_old_invalid = list_invalid.get(i).getShop_num();
        // break;
        // }
        // }
        // }
        if (!id_flag) {// 需要获取id
            if (shop_num > 2) {
                // clickFlag = true;
                ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
            } else {
                new SAsyncTask<String, Void, HashMap<String, Object>>(this, v, R.string.wait) {

                    @Override
                    protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
                            throws Exception {

                        return ComModel2.getShopCartData(ShopImageActivity.this, 1);

                    }

                    @Override
                    protected boolean isHandleException() {
                        return true;
                    }

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    ;

                    @Override
                    protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result,
                                                 Exception e) {

                        if (null == e) {
                            int id = Integer.parseInt((String) result.get("id"));
                            addCart(size, color, shop_num, stock_type_id, pic, realPrice, supplyId, kickback,
                                    original_price, v, id);
                            UserInfo user = YCache.getCacheUser(ShopImageActivity.this);
                            Store store = YCache.getCacheStore(ShopImageActivity.this);
                            boolean hh = dao.add(shop.getShop_code(), size, color,
                                    Integer.parseInt(String.valueOf(shop_num)),
                                    Integer.parseInt(String.valueOf(stock_type_id)), pic, user.getUser_id(),
                                    shop.getShop_name(), store.getS_code(), "" + shop.getShop_price(),
                                    "" + shop.getShop_se_price(), supplyId + "", "" + kickback, "" + original_price, 0,
                                    null, null, null, null, null, id, null, 0, mSupp_label);
                            // 普通商品过期时间
                            // SharedPreferencesUtil.saveStringData(context,
                            // Pref.SHOPCART_COMMON_TIME,
                            // new Date().getTime() + 30 * 1000 * 60 + "");

                            // setAnim();

                        }
                        // else {
                        // clickFlag = true;
                        // rootView.removeView(pointRoot);
                        // }

                        super.onPostExecute(context, result, e);
                    }

                }.execute();
            }

        } else {// 不需要获取购物车id
            if (id_flag) {// 只有有效的
                if (shop_num + shop_num_old > 2) {
                    // clickFlag = true;
                    ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
                } else {
                    addCart(size, color, shop_num, stock_type_id, pic, realPrice, supplyId, kickback, original_price, v,
                            id_dao);
                    dao.modify("" + stock_type_id, shop_num + shop_num_old);
                }
            }
            // else if (!id_flag && id_flag_invalid) {// 只有失效的
            // if (shop_num + shop_num_old_invalid > 2) {
            //// clickFlag = true;
            // ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
            // } else {
            // addCart(size, color, shop_num, stock_type_id, pic, realPrice,
            // supplyId, kickback, original_price, v,
            // id_dao);
            // UserInfo user = YCache.getCacheUser(ShopImageActivity.this);
            // Store store = YCache.getCacheStore(ShopImageActivity.this);
            // boolean hh = dao.add(shop.getShop_code(), size, color,
            // Integer.parseInt(String.valueOf(shop_num)),
            // Integer.parseInt(String.valueOf(stock_type_id)), pic,
            // user.getUser_id(),
            // shop.getShop_name(), store.getS_code(), "" +
            // shop.getShop_price(),
            // "" + shop.getShop_se_price(), supplyId + "", "" + kickback, "" +
            // original_price, 0, null,
            // null, null, null, null, id_dao, null,0,mSupp_label);
            // // 普通商品过期时间
            // SharedPreferencesUtil.saveStringData(context,
            // Pref.SHOPCART_COMMON_TIME,
            // new Date().getTime() + 30 * 1000 * 60 + "");
            // }
            // } else if (id_flag && id_flag_invalid) {
            //// clickFlag = true;
            // ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
            // }
        }
    }

    /**
     * @param size
     * @param color
     * @param shop_num
     * @param stock_type_id
     * @param pic
     * @param realPrice
     * @param supplyId
     * @param kickback
     * @param original_price
     * @param v
     */
    private void addCart(String size, String color, final int shop_num, int stock_type_id, String pic,
                         final double realPrice, final int supplyId, final double kickback, final int original_price, View v,
                         final int id) {
        new SAsyncTask<String, Void, ReturnInfo>(this, v, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {
                UserInfo user = YCache.getCacheUser(ShopImageActivity.this);
                Store store = YCache.getCacheStore(ShopImageActivity.this);

                return ComModel.joinShopCart(ShopImageActivity.this, params[0], params[1], params[2], params[3],
                        params[4], "" + user.getUser_id(), YCache.getCacheToken(ShopImageActivity.this), realPrice,
                        shop, store.getS_code(), supplyId + "", kickback, original_price, id, mSupp_label);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {

                if (null == e) {
                    if (null != result && result.getStatus().equals("1")) {
                        Toast.makeText(ShopImageActivity.this, "加入购物车成功", Toast.LENGTH_SHORT).show();
                        SharedPreferencesUtil.saveBooleanData(context, "undo_view4", true);// 加入购物车时候
                        // 主界面的购物车数量设置为重新显示

                        int needValue = 1;
                        try {
                            needValue = Integer.parseInt(SignListAdapter.doValueShopCart);
                        } catch (Exception e2) {
                        }
                        int signNumber = 1;// 1，代表一次性奖励，大于1代表多次奖励
                        try {
                            signNumber = SignListAdapter.doNumShopCart;
                        } catch (Exception e2) {
                        }
                        double jiangliValue = 5.0;
                        try {
                            jiangliValue = Double.parseDouble(SignListAdapter.jiangliValueShopCart);
                        } catch (Exception e2) {
                        }
                        String mNotice = "元";
                        int jiangliType = 5;
                        try {
                            jiangliType = SignListAdapter.jiangliIDShopCart;
                        } catch (Exception e2) {
                        }
                        switch (jiangliType) {
                            case 3:
                                mNotice = "元优惠券";
                                break;
                            case 4:
                                mNotice = "积分";
                                break;
                            case 5:
                                mNotice = "元";
                                break;
                            case 11:
                                mNotice = "个衣豆";
                                break;
                            default:
                                break;
                        }
                        String qiandao_time = SharedPreferencesUtil.getStringData(ShopImageActivity.this,
                                "qiandao_time" + YCache.getCacheUser(ShopImageActivity.this).getUser_id(), "");
                        String qiandao_num = SharedPreferencesUtil.getStringData(ShopImageActivity.this,
                                "qiandao_num" + YCache.getCacheUser(ShopImageActivity.this).getUser_id(), "0");
                        // ShopCartDao dao=new
                        // ShopCartDao(ShopDetailsActivity.this);
                        int now_num = Integer.parseInt(qiandao_num) + shop_num;
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String now_time = sdf.format(date);
                        if (qiandao_time.equals(now_time)) {
                            SharedPreferencesUtil.saveStringData(ShopImageActivity.this,
                                    "qiandao_num" + YCache.getCacheUser(ShopImageActivity.this).getUser_id(),
                                    "" + now_num);
                            if (now_num >= needValue) {
                                // if(!SignListAdapter.isSignComplete){
                                sign(true, 0);
                                SharedPreferencesUtil.saveStringData(ShopImageActivity.this,
                                        "qiandao_time" + YCache.getCacheUser(ShopImageActivity.this).getUser_id(),
                                        "-1");
                                // }
                            } else {
                                // if(signNumber>1||jiangliType==3){
                                // if(!SignListAdapter.isSignComplete){
                                ToastUtil.showShortText(context, "再加" + (needValue - now_num) + "件商品可完成任务喔~");
                                // }
                                // }else{
                                // sign(false,needValue-now_num);
                                // }
                            }
                        }
                        int cartCount = 0;
                        if (result.getIsCart() == 0) {
                            cartCount = shop.getCart_count() + 1;
                        } else {
                            cartCount = shop.getCart_count();
                        }
                        shop.setCart_count(cartCount);
                    } else {
                        Toast.makeText(ShopImageActivity.this, "加入购物车失败", Toast.LENGTH_SHORT).show();
                    }
                }
                super.onPostExecute(context, result, e);
            }

        }.execute(size, color, String.valueOf(shop_num), String.valueOf(stock_type_id), pic);
    }

    private ToLoginDialog loginDialog;// 不是你的包袱

    @Override
    public void onClick(View arg0) {
        super.onClick(arg0);
        if (YJApplication.instance.isLoginSucess() == false) {
            if (loginDialog == null) {
                loginDialog = new ToLoginDialog(context);
            }
            loginDialog.show();
            return;
        }

        switch (arg0.getId()) {
            case R.id.bt_cart: {
                queryShopQueryAttr(1);
            }
            break;
            case R.id.bt_order: {
                if (isMembers && YCache.getCacheUser(context).getIs_member().equals("2")) {
                    ToastUtil.showShortText(context, "您已是至尊会员，无需重复购买 ");
                    return;
                }
                queryShopQueryAttr(0);
            }
            break;
            /**
             * 参与夺宝
             */
            case R.id.indiana_bt_meal_order:
                TongJiUtils.yunYunTongJi("duobao", 1002, 10, ShopImageActivity.this);


                if (needCount <= 0) { //剩余参与人数为0
                    ToastUtil.showShortText(ShopImageActivity.this, "当期活动已结束，正在开奖，请稍后再来。");
                    return;
                }


                if (mOstatus == 4) {
                    ToastUtil.showShortText(ShopImageActivity.this, "当期活动已结束，请留意下期哦。");
                } else {
                    if (moneyIndianaFlag) {//提现额度夺宝
                        showMoneyPayDialog();

                    } else if (isGroupIndianaFlag) {
                        Intent intent = new Intent(ShopImageActivity.this, IndianaGroupsDetActivity.class);
                        intent.putExtra("shop_code", shop.getShop_code());
                        intent.putExtra("issue_code", issue_code);
                        intent.putExtra("group_number", mGroupPeopleCount);
                        intent.putExtra("banner", shop.getBanner());
                        intent.putExtra("shop_name", shop.getShop_name());
                        intent.putExtra("shop", shop);
                        intent.putExtra("is_ago", mOstatus);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    } else {
                        showPayDialog();
                    }
                }
//			Intent intent = new Intent(ShopImageActivity.this, SubmitDuobaoOrderActivity.class);
//			String pic = YUrl.imgurl + shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/"
//					+ (String) shop.getDef_pic();
//			intent.putExtra("pic", pic);// 图片
//			intent.putExtra("code", shop.getShop_code());
//			intent.putExtra("valueDuo", code);
//			intent.putExtra("shop_price", shop.getShop_price());// 现价
//			intent.putExtra("shop_orinal_price", shop.getShop_se_price());// 原价
//			intent.putExtra("name", (String) shop.getShop_name());// 商品名
//			intent.putExtra("signType", signType);
//
//			intent.putExtra("name", (String) shop.getShop_name());// 商品名
//			// list = (List<Shop>) getIntent().getSerializableExtra("list");
//
//			startActivity(intent);
                break;
            case R.id.bt_meal_order:
            case R.id.sign_buy: {
                if (!"SignShopDetail".equals(signShopDetail)) {
                    // 非签到商品
                    if (number_sold_single == null) {
                        queryMealShopAttrs(1, arg0);
                    } else if (number_sold_single.equals("none")) {
                        return;
                    } else {
                        // queryMealShopAttrs(0, arg0);
                        queryMealShopAttrs(1, arg0);
                    }
                } else {// 签到商品
                    queryMealShopAttrs(0, arg0);// 0购买 1 加入购物车
                }
                // System.out.println("走到了没？");
                // queryShopQueryAttr(1);
                // System.out.println("上面的已经走过了");
            }
            break;

            default:
                break;
        }

    }

    private List<Shop> list;

    private String number_sold_single = "";

    private boolean shopCart;

    private void queryMealShopAttrs(final int i, View v) {
        final StringBuffer sb = new StringBuffer();
        for (int j = 0; j < list.size(); j++) {
            sb.append(list.get(j).getShop_code());
            if (j != list.size() - 1) {
                sb.append(",");
            }
        }
        new SAsyncTask<Void, Void, List<StockType>>(this, v, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected List<StockType> doInBackground(FragmentActivity context, Void... params) throws Exception {
                // TODO Auto-generated method stub
                return ComModel2.getShopListAttrs(context, sb.toString());
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<StockType> result, Exception e) {
                // TODO Auto-generated method stub
                if (null == e) {
                    for (int s = 0; s < list.size(); s++) {
                        List<StockType> listStock = new ArrayList<StockType>();
                        for (int k = 0; k < result.size(); k++) {
                            if (list.get(s).getShop_code().equals(result.get(k).getShop_code())) {
                                listStock.add(result.get(k));
                                if ("SignShopDetail".equals(signShopDetail)) {
                                    // 签到特卖商品 任何商品类型 价格固定为几元包邮
                                    listStock.get(k).setPrice(list.get(0).getShop_se_price());
                                }
                            }

                        }
                        list.get(s).setList_stock_type(listStock);
                    }

//                    showMealPopWindow(i, list);
                }
                super.onPostExecute(context, result, e);
            }

        }.execute();
    }


    @Override
    public void onBackPressed() {

        if (!isMeal && !"SignShopDetail".equals(signShopDetail)) {
            if (shop.getCart_count() > 0) {
                Intent intent = new Intent();
                intent.putExtra("count", shop.getCart_count());
                setResult(RESULT_OK, intent);
            }
        }
        // if (isMeal ) {
        // if (shop.getCart_count() > 0) {
        // Intent intent = new Intent();
        // intent.putExtra("count", shop.getCart_count());
        // setResult(RESULT_OK, intent);
        // }
        // }

        finish();
    }

    int p_id_dao;

    /**
     * 套餐加入购物车
     */
    private void mealJoinShopCart(final String p_code, final int shop_num, final String p_seq,
                                  final List<StockBean> catJson, final String p_type, final String postage, final double shop_price,
                                  final double shop_se_price, final int supp_id, final String def_pic) {
        final ShopCartDao dao = new ShopCartDao(context);
//		if (dao.queryCartSpecialCount(context) + shop_num > 20) {
//			ToastUtil.showShortText(context, "购物车最多允许加入20件有效商品");
//			return;
//		}

        final List<ShopCart> list = dao.findAll();
        final List<ShopCart> list_invalid = dao.findAll_invalid();
        boolean p_flag_dao = false;
        boolean p_flag_dao_invalid = false;
        int p_dao_num = 0;
        int p_dao_num_invalid = 0;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (p_seq.equals(list.get(i).getP_s_t_id())) {
                    p_flag_dao = true;
                    p_dao_num = list.get(i).getShop_num();
                    p_id_dao = list.get(i).getId();
                    break;
                }
            }
        }
        if (list_invalid != null) {
            for (int i = 0; i < list_invalid.size(); i++) {
                if (p_seq.equals(list_invalid.get(i).getP_s_t_id())) {
                    p_flag_dao_invalid = true;
                    p_dao_num_invalid = list_invalid.get(i).getShop_num();
                    p_id_dao = list_invalid.get(i).getId();
                    break;
                }
            }
        }
        if (!p_flag_dao && !p_flag_dao_invalid) {// 获取id
            new SAsyncTask<String, Void, HashMap<String, Object>>(this, R.string.wait) {

                @Override
                protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
                        throws Exception {

                    return ComModel2.getShopCartData(ShopImageActivity.this, 1);

                }

                @Override
                protected boolean isHandleException() {
                    return true;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                ;

                @Override
                protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {

                    if (null == e) {

                        int id = Integer.parseInt((String) result.get("id"));
                        addMealCart(p_code, shop_num, p_seq, catJson, p_type, postage, shop_price, shop_se_price,
                                supp_id, def_pic, id);
                        StringBuffer mShop_code_p = new StringBuffer();
                        StringBuffer mColor_p = new StringBuffer();
                        for (int i = 0; i < catJson.size(); i++) {
                            StockBean bean = catJson.get(i);
                            if (i < catJson.size() - 1) {
                                mShop_code_p.append(bean.getShop_code()).append(",");
                                mColor_p.append(bean.getColor()).append(",");
                            } else {
                                mShop_code_p.append(bean.getShop_code());
                                mColor_p.append(bean.getColor());
                            }
                        }

                        boolean hh = dao.add(null, null, null, Integer.parseInt(String.valueOf(shop_num)), 0,
                                (String) mealMap.get("def_pic"), 0, null, null, "" + shop_price, "" + shop_se_price,
                                supp_id + "", "" + 0, "" + 0, 1, p_code, postage, p_seq, mShop_code_p.toString(),
                                mColor_p.toString(), id, p_seq.split(",").length > 1 ? "超值套餐" : "超值单品", 0, mSupp_label);
                        // 特卖过期时间
                        SharedPreferencesUtil.saveStringData(context, Pref.SHOPCART_MEAL_TIME,
                                new Date().getTime() + 30 * 1000 * 60 + "");

                        // setAnim();

                    }

                    super.onPostExecute(context, result, e);
                }

            }.execute();
        } else {// 不需要获取id
            if (p_flag_dao && !p_flag_dao_invalid) {// 只有有效的
                if (p_dao_num + shop_num > 2) {
                    ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
                } else {
                    addMealCart(p_code, shop_num, p_seq, catJson, p_type, postage, shop_price, shop_se_price, supp_id,
                            def_pic, p_id_dao);
                    dao.p_modify(p_seq, p_dao_num + shop_num);
                }
            } else if (!p_flag_dao && p_flag_dao_invalid) {// shixiao
                if (p_dao_num_invalid + shop_num > 2) {
                    ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
                } else {
                    addMealCart(p_code, shop_num, p_seq, catJson, p_type, postage, shop_price, shop_se_price, supp_id,
                            def_pic, p_id_dao);
                    StringBuffer mShop_code_p = new StringBuffer();
                    StringBuffer mColor_p = new StringBuffer();
                    for (int i = 0; i < catJson.size(); i++) {
                        StockBean bean = catJson.get(i);
                        if (i < catJson.size() - 1) {
                            mShop_code_p.append(bean.getShop_code()).append(",");
                            mColor_p.append(bean.getColor()).append(",");
                        } else {
                            mShop_code_p.append(bean.getShop_code());
                            mColor_p.append(bean.getColor());
                        }
                    }
                    boolean hh = dao.add(null, null, null, Integer.parseInt(String.valueOf(shop_num)), 0,
                            (String) mealMap.get("def_pic"), 0, null, null, "" + shop_price, "" + shop_se_price,
                            supp_id + "", "" + 0, "" + 0, 1, p_code, postage, p_seq, mShop_code_p.toString(),
                            mColor_p.toString(), p_id_dao, p_seq.split(",").length > 1 ? "超值套餐" : "超值单品", 0,
                            mSupp_label);
                    SharedPreferencesUtil.saveStringData(context, Pref.SHOPCART_MEAL_TIME,
                            new Date().getTime() + 30 * 1000 * 60 + "");
                }

            } else {
                ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
            }
            // setAnim();
        }
    }

    /**
     * @param p_code
     * @param shop_num
     * @param p_seq
     * @param catJson
     * @param p_type
     * @param postage
     * @param shop_price
     * @param shop_se_price
     * @param supp_id
     * @param def_pic
     */
    private void addMealCart(final String p_code, final int shop_num, final String p_seq, final List<StockBean> catJson,
                             final String p_type, final String postage, final double shop_price, final double shop_se_price,
                             final int supp_id, final String def_pic, final int id) {
        new SAsyncTask<Void, Void, ReturnInfo>(this, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                try {
                    URL url = new URL(YUrl.imgurl
                            // + p_code.substring(1, 4) + "/"
                            // + p_code+ "/"
                            + def_pic + "!180");
                    URLConnection conn = url.openConnection();
                    InputStream is = conn.getInputStream();
                    // d = BitmapDrawable.createFromStream(is, null);
                } catch (Exception e) {
                }

                return ComModel2.mealJoinShopCart(context, p_code, shop_num, p_seq, catJson, p_type, postage,
                        shop_price, shop_se_price, supp_id, id);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // if (img == null) {
                // img = new ImageView(context);
                // img.setLayoutParams(new LayoutParams(100, 100));
                // }
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                if (null == e) {
                    ToastUtil.showShortText(context, result.getMessage());
                    int cartCount = 0;
                    if (result.getIsCart() == 0) {
                        cartCount = Integer.parseInt(mealMap.get("cart_count").toString()) + 1;
                    } else {
                        cartCount = Integer.parseInt(mealMap.get("cart_count").toString());
                    }
                    mealMap.put("cart_count", cartCount);
                    // img.setImageDrawable(d);
                    // setAnim(null);

                }
                super.onPostExecute(context, result, e);
            }

        }.execute();
    }

    public void getSignJoinShopCartDialog(Double jiangliValue) {
        final Dialog signDialog = new Dialog(ShopImageActivity.this, R.style.invate_dialog_style);
        View view = View.inflate(ShopImageActivity.this, R.layout.dialog_sign_join_shopcart, null);
        TextView mTvJiangli = (TextView) view.findViewById(R.id.dialog_sign_jiangli);
        String mNotice = "元";
        int jiangliType = 5;
        try {
            jiangliType = SignListAdapter.jiangliIDShopCart;
        } catch (Exception e2) {
        }
        switch (jiangliType) {
            case 3:
                mNotice = "元优惠券";
                break;
            case 4:
                mNotice = "积分";
                break;
            case 5:
                mNotice = "元";
                break;

            default:
                break;
        }
        String s = "";
        String str = "";
        if (jiangliType == 11) {
            str = new java.text.DecimalFormat("#0").format(jiangliValue);
            s = new java.text.DecimalFormat("#0").format(jiangliValue) + mNotice + "奖励已存入你的账户，如果喜欢这件美衣就带它回家吧~";
        } else {
            str = "" + jiangliValue;
            s = jiangliValue + mNotice + "奖励已存入你的账户，如果喜欢这件美衣就带它回家吧~";
        }
        String l = str + mNotice;
        // String l=jiangliValue+mNotice;
        mTvJiangli.setText(s);
        SpannableStringBuilder builder = new SpannableStringBuilder(mTvJiangli.getText().toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#ff3f8b"));
        builder.setSpan(redSpan, 0, l.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvJiangli.setText(builder);
        TextView mClose = (TextView) view.findViewById(R.id.tv_close);
        mClose.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                signDialog.dismiss();
            }
        });
        Button liebiao = (Button) view.findViewById(R.id.dialog_liebiao);
        liebiao.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				Intent intent = new Intent(ShopImageActivity.this, MainMenuActivity.class);
//				intent.putExtra("Exit30", true);
//				startActivity(intent);


                // 跳至赚钱
                SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
                context.startActivity(new Intent(context, CommonActivity.class));


            }
        });
        Button gobuy = (Button) view.findViewById(R.id.gobuy2);
        gobuy.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                signDialog.dismiss();
                Intent intent = new Intent(ShopImageActivity.this, ShopCartNewNewActivity.class);
                startActivity(intent);
            }
        });
        signDialog.setContentView(view);
        signDialog.setCancelable(true);
        signDialog.setCanceledOnTouchOutside(false);
        signDialog.show();
    }

    private void sign(final boolean flag, final int num) {
        // 签到
        new SAsyncTask<Void, Void, HashMap<String, Object>>(ShopImageActivity.this, 0) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.getSignIn(ShopImageActivity.this, false, false,
                        SharedPreferencesUtil.getStringData(context, YConstance.ADD_TO_SHOPCART, ""),
                        SignListAdapter.doClass);

            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {


                    if (Integer.valueOf(result.get("isNewbie01") + "") == 1) {
                        SignCompleteDialogUtil.firstClickInGoToZP(ShopImageActivity.this);
                        return;
                    }


                    String mNotice = "元";
                    int jiangliType = 5;
                    try {
                        jiangliType = SignListAdapter.jiangliIDShopCart;
                    } catch (Exception e2) {
                    }
                    switch (jiangliType) {
                        case 3:
                            mNotice = "元优惠券";
                            break;
                        case 4:
                            mNotice = "积分";
                            break;
                        case 5:
                            mNotice = "元";
                            break;
                        case 11:
                            mNotice = "个衣豆";
                            break;
                        default:
                            break;
                    }
                    int needValue = 1;
                    try {
                        needValue = Integer.parseInt(SignListAdapter.doValueShopCart);
                    } catch (Exception e2) {
                    }
                    int signNumber = 1;// 1，代表一次性奖励，大于1代表多次奖励
                    try {
                        signNumber = SignListAdapter.doNumShopCart;
                    } catch (Exception e2) {
                    }
                    double jiangliValue = 0.0;
                    try {
                        jiangliValue = Double.parseDouble(SignListAdapter.jiangliValueShopCart);
                    } catch (Exception e2) {
                    }
                    if (flag) {
                        getSignJoinShopCartDialog(signNumber * jiangliValue);
                        SharedPreferencesUtil.saveStringData(ShopImageActivity.this,
                                "qiandao_time" + YCache.getCacheUser(ShopImageActivity.this).getUser_id(), "-1");
                    } else {
                        ToastUtil.showShortText(context, "加入成功奖励" + jiangliValue + mNotice + "，还有" + num + "次机会喔~");
                    }
                } else {
                    // ToastUtil.showLongText(mContext, "未知错误");
                }

            }

        }.execute();
    }

    private IndianaPayDialog indianaPayDialog;

    // 夺宝正常夺宝选择支付弹窗
    private void showPayDialog() {
        String shopPicUrl = shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + shop.getDef_pic()
                + "!180";
        indianaPayDialog = new IndianaPayDialog(ShopImageActivity.this, R.style.invate_dialog_style, "" + shopPicUrl, "" + shop.getShop_code(), shop.getShop_se_price(), needPeopleCount, shop.getShop_name(), shop.getDef_pic());
        indianaPayDialog.show();
    }

    //    夺宝提现额度支付弹窗
    private void showMoneyPayDialog() {
        IndianaPayPopupWinDow payWinDow = new IndianaPayPopupWinDow(ShopImageActivity.this, 0, shop.getShop_se_price(), needPeopleCount, shop.getActive_people_num(), true);
        payWinDow.show();
    }

    @Override
    public void intentToConfirm(int takeCount, double countMoney, int flag) {
        Intent intent = new Intent(ShopImageActivity.this, SubmitDuobaoOrderActivity.class);
        String pic = YUrl.imgurl + shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/"
                + shop.getDef_pic();
        intent.putExtra("pic", pic);// 图片
        intent.putExtra("signType", signType);
        intent.putExtra("code", shop.getShop_code());
        intent.putExtra("valueDuo", code);
        intent.putExtra("shop_se_price", shop.getShop_se_price());// 现价
        intent.putExtra("shop_price", shop.getShop_price());// 原价
        intent.putExtra("name", (String) shop.getShop_name());// 商品名

        if (flag == 1) {//1分钱夺宝
            intent.putExtra("flag", "1");// 现价
        } else {
            intent.putExtra("flag", "0");// 现价
        }
        intent.putExtra("shop_num", takeCount);
        if (indianaPayDialog != null) {
            indianaPayDialog.dismiss();
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
    }

    @Override
    public void confirm(int takeCount, double payCountMoney, int flag) {

        submitZeroOrder(null, takeCount);
    }

    /**
     * 夺宝商品提交订单
     */
    private void submitZeroOrder(final View v, final int takeCount) {

        new SAsyncTask<Void, Void, HashMap<String, Object>>(this, null, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.submitDuobaoOrder(context, "", 0, "" + takeCount, "" + 0, "" + shop.getShop_code());
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
//                isClick = false;
                if (null == e) {
                    String orderNo = (String) result.get("order_code");

                    SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);

                    // 跳转到收银台界面
                    Intent intent = new Intent(ShopImageActivity.this, MealPaymentActivity.class);
                    LogYiFu.e("TAG", "点击提交订单");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("result", result);
                    intent.putExtra("isDuobao", true);
                    intent.putExtras(bundle);
                    intent.putExtra("isMulti", false);
                    intent.putExtra("totlaAccount", shop.getShop_se_price() * takeCount);
                    intent.putExtra("order_code", orderNo);
//                    intent.putExtra("pos", pos);
                    intent.putExtra("signType", signType);
                    intent.putExtra("moneyIndiana", true);
                    startActivity(intent);
                    finish();

                }
            }

        }.execute((Void[]) null);
    }
}
