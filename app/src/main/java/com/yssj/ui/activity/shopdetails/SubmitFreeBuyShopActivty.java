package com.yssj.ui.activity.shopdetails;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.data.DBService;
import com.yssj.entity.DeliveryAddress;
import com.yssj.entity.Order;
import com.yssj.entity.ShopCart;
import com.yssj.entity.UserInfo;
import com.yssj.entity.VipInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.HomePageThreeActivity;
import com.yssj.ui.activity.OneBuyChouJiangActivity;
import com.yssj.ui.activity.infos.StatusInfoActivity;
import com.yssj.ui.activity.infos.UsefulCouponsActivity;
import com.yssj.ui.activity.setting.ManMyDeliverAddr;
import com.yssj.ui.activity.setting.SetDeliverAddressActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.PublicToastDialog;
import com.yssj.ui.dialog.WaitDialog;
import com.yssj.ui.fragment.orderinfo.OrderDetailsActivity;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;
import com.yssj.utils.YunYingTongJi;
import com.yssj.wxpay.WxPayUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import butterknife.Bind;
import butterknife.ButterKnife;

//import static com.yssj.ui.activity.shopdetails.ShopDetailsActivity.freeBuyType;


/**
 * 免费领提交订单
 */
@SuppressLint("StringFormatMatches")
public class SubmitFreeBuyShopActivty extends BasicActivity {
    @Bind(R.id.tv_free_buy_youhui)
    TextView tvFreeBuyYouhui;
    @Bind(R.id.tv_youohui_show_text)
    TextView tvYouohuiShowText;
    private TextView tv_name, tv_phone, tv_receiver_addr;

    private LinearLayout lin_receiver_addr;
    int m = 0;
    private DBService db = new DBService(this);
    private RelativeLayout mTouBg;
    private TextView total_account;

    private TextView tvTitle_base;
    private LinearLayout img_back;

    private DeliveryAddress dAddress;
    private HashMap<String, String> addResult;
    Map<String, String> resultunifiedorder;

    IWXAPI msgApi;// 微信api

    private int addressId = 0;

    private LinearLayout lin_set_addr;

    private List<ShopCart> listGoods;

    private LinearLayout container;
    private List<ShopCart> mListShopCart = new ArrayList<ShopCart>();// 用来存储使用抵用券的商品集合
    private RelativeLayout rel_show_share;
    private TextView mSubmitTotal;// 总价
    private int mTenUse = 0, mFiveUse = 0, mTwoUse = 0, mOneUse = 0;// 每个商品使用的优惠券数量
    private HashMap<String, Integer> mMapTen = new HashMap<String, Integer>();
    private HashMap<String, Integer> mMapFive = new HashMap<String, Integer>();
    private HashMap<String, Integer> mMapTwo = new HashMap<String, Integer>();
    private HashMap<String, Integer> mMapOne = new HashMap<String, Integer>();

    double sum = 0.00;
    private HashMap<Integer, List<ShopCart>> mapListGood = new HashMap<Integer, List<ShopCart>>();

    private HashMap<Integer, String> mapMsg = new HashMap<Integer, String>();

    private HashMap<Integer, EditText> mapEdit = new HashMap<Integer, EditText>();


    private HashMap<Integer, HashMap<String, Object>> mapCoups = new HashMap<Integer, HashMap<String, Object>>();// 提交的优惠券


    private LinearLayout btn_pay;


    private RelativeLayout rel_name_phone;
    private String onePrice = "1";

    public static int page3;

    int shopNum = 0;


    public static SubmitFreeBuyShopActivty instance;

    private List<ShopCart> shopCartMeal = new ArrayList<ShopCart>();


    private LinearLayout mShareBack;
    private double mPriceCount = 0.0;// 本单价格之和
    boolean flag = false;

    private String shopPrice = "";

    private VipInfo mVipInfo;

    private String shop_pic;
    private String color_size;
    private int freeOrderPage;
    private int freeBuyType;

    private WaitDialog waitDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != aBar) {
            aBar.hide();
        }
        setContentView(R.layout.submit_freebuy_goods);
        ButterKnife.bind(this);

        instance = this;
        flag = false;
        waitDialog = new WaitDialog(this, R.style.DialogStyle1);


        if (ShopDetailsActivity.isNewMeal) {
            shop_pic = getIntent().getStringExtra("shop_pic");
            color_size = getIntent().getStringExtra("color_size");
        }


        page3 = getIntent().getIntExtra("page3", 0);
        freeOrderPage = getIntent().getIntExtra("freeOrderPage", 0);
        freeBuyType = getIntent().getIntExtra("freeBuyType", 0);

        double vipPayOnlyBuyPrice = getIntent().getDoubleExtra("vipPayOnlyBuyPrice", 0.0);
        String youhuiDikou = getIntent().getStringExtra("youhuiDikou");
        onePrice = getIntent().getStringExtra("onePrice");


        img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);


        mShareBack = (LinearLayout) findViewById(R.id.img_back_share);
        mShareBack.setOnClickListener(this);


        tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("确认订单");


        listGoods = (List<ShopCart>) getIntent().getExtras().getSerializable("listGoods");


        //确定特卖的color_size
        if (ShopDetailsActivity.isNewMeal) {
            initShopAttrNewMeal();

        }


        mVipInfo = (VipInfo) getIntent().getExtras().getSerializable("vipInfo");

        if (mVipInfo.getIsVip() > 0) {  //此时是会员开团
            tvYouohuiShowText.setText("会员优惠");
        }

        btn_pay = (LinearLayout) findViewById(R.id.btn_pay);// 支付按钮

        btn_pay.setOnClickListener(this);


        for (ShopCart shopCart : listGoods) {
            if (TextUtils.isEmpty(shopCart.getP_code())) {
                List<ShopCart> list = mapListGood.get(shopCart.getSupp_id());
                if (list != null) {
                    list.add(shopCart);
                } else {
                    List<ShopCart> lista = new ArrayList<ShopCart>();
                    lista.add(shopCart);
                    mapListGood.put(shopCart.getSupp_id(), lista);
                }
            } else {
                shopCartMeal.add(shopCart);
            }


            sum += shopCart.getShop_se_price() * shopCart.getShop_num();// 计算显示的总金额
            mPriceCount += shopCart.getShop_se_price() * shopCart.getShop_num();// 商品总价

        }
        if (null != YCache.getCacheUserSafe(instance)) {
            if (YCache.getCacheUserSafe(instance).getIs_member().equals("2")) {// 判断是否是会员
                sum = sum * 0.95;// 当是会员的时候 打95折
            }
        }


        msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp(WxPayUtil.APP_ID);
        initData(0);


    }

    String shopAttrNewMeal = "";

    private void initShopAttrNewMeal() {

        //去掉库存属性中无效的数据
        for (int j = 0; j < MealShopDetailsActivity.mealAttrList.size(); j++) {
            if (null == MealShopDetailsActivity.mealAttrList.get(j).getId()) {
                MealShopDetailsActivity.mealAttrList.remove(j);
                j--;
            }

        }

        HashMap<String, String> tempMap = new HashMap<>();

        LogYiFu.e("shopAttr-color-size", color_size);
        String[] attr = color_size.split(":");
        if (attr.length > 0) {

            for (int i = 0; i < attr.length; i++) {

                int attrTemp = Integer.parseInt(attr[i]);

                for (int j = 0; j < MealShopDetailsActivity.mealAttrList.size(); j++) {
                    if (attrTemp == MealShopDetailsActivity.mealAttrList.get(j).getId()) { //属性名对上了;
                        LogYiFu.e("Testmeal", attr[i]);

                        String tempName = MealShopDetailsActivity.mealAttrList.get(j).getAttr_name();
                        int pid = MealShopDetailsActivity.mealAttrList.get(j).getParent_id();
                        //查询属性名
                        for (int x = 0; x < MealShopDetailsActivity.mealAttrList.size(); x++) {
                            if (pid == MealShopDetailsActivity.mealAttrList.get(x).getId()) {
                                String attrName = MealShopDetailsActivity.mealAttrList.get(x).getAttr_name();
                                tempMap.put(tempName, attrName);
                            }
                        }
                    }
                }
            }
        }

        Iterator iter = tempMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            String tempStr = val + ":" + key + " ";
            shopAttrNewMeal += tempStr;
        }

        LogYiFu.e("shopAttr", shopAttrNewMeal);

    }

    @Override
    protected void onResume() {
        super.onResume();
        isClick = false;
//        HomeWatcherReceiver.registerHomeKeyReceiver(this);
        SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1053");
//        TongJiUtils.TongJi(this, 11 + "");
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//        HomeWatcherReceiver.unregisterHomeKeyReceiver(this);
//        TongJiUtils.TongJi(this, 111 + "");

    }

    private void initData(final int requestCode) {
        new SAsyncTask<Void, Void, HashMap<String, String>>(instance, R.string.wait) {// 获取地址

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getDefaultDeliverAddr(instance);
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (requestCode == 1002) {// 设置地址
                    SubmitFreeBuyShopActivty.this.addResult = result;
                    // 设置地址
                    setDeliverAddress(result, null);
                    return;
                } else {
                    initView(result);
                }

            }

        }.execute();


    }

    private void initView(HashMap<String, String> result) {
        // TODO:
        mTouBg = (RelativeLayout) findViewById(R.id.submit_bg);
        mTouBg.setBackgroundColor(Color.WHITE);

        rel_show_share = (RelativeLayout) findViewById(R.id.rel_show_share);
        mSubmitTotal = (TextView) findViewById(R.id.submit_total);

        container = (LinearLayout) findViewById(R.id.container);
        tv_name = (TextView) findViewById(R.id.tv_name);// 收件人
        tv_phone = (TextView) findViewById(R.id.tv_phone);// 收件人电话
        tv_receiver_addr = (TextView) findViewById(R.id.tv_receiver_addr);// 收件地址
        lin_receiver_addr = (LinearLayout) findViewById(R.id.lin_receiver_addr);
        lin_receiver_addr.setOnClickListener(this);
        lin_set_addr = (LinearLayout) findViewById(R.id.lin_set_addr);
        lin_set_addr.setOnClickListener(this);
        this.addResult = result;

        total_account = (TextView) findViewById(R.id.total_account);// 合计多少货物多少钱

        rel_name_phone = (RelativeLayout) findViewById(R.id.rel_name_phone);

        setDeliverAddress(result, null);


        initOther();

    }


    private void initOther() {
        for (int i = 0; i < listGoods.size(); i++) {
            ShopCart cart = listGoods.get(i);
            shopNum = shopNum + cart.getShop_num();
        }


        if (ShopDetailsActivity.isNewMeal) {
            addViewNewMeal(mapListGood, container, null);

        } else {
            addView(mapListGood, container, null);

        }
    }


    private void addViewNewMeal(HashMap<Integer, List<ShopCart>> applyList, LinearLayout container,
                                HashMap<String, Object> mapCoupon) {


        container.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        Iterator<Entry<Integer, List<ShopCart>>> iterator = applyList.entrySet().iterator();
        int position = 0;


        while (iterator.hasNext()) {
            Entry<Integer, List<ShopCart>> entry = iterator.next();
            final List<ShopCart> shopCarts = (List<ShopCart>) entry.getValue();

            View view = inflater.inflate(R.layout.goods_item, null);
            view.setBackgroundColor(Color.WHITE);


            // 设置分割线 第一条的隐藏
            position++;
            View line = view.findViewById(R.id.v_bottom_line);
            if (position == 1) {
                line.setVisibility(View.GONE);
            }

            LinearLayout good_container = (LinearLayout) view.findViewById(R.id.good_container);
            EditText edit_message = (EditText) view.findViewById(R.id.edit_message);
            mapEdit.put(entry.getKey(), edit_message);
            double sumAccount = 0.0;

            LogYiFu.e("TAG", "同一家商品的数量---" + shopCarts.size());
            int userful = 0;//
            double specilaPriceCount = 0;//专柜价总和
            for (int i = 0; i < shopCarts.size(); i++) {
                final ShopCart good = shopCarts.get(i);
                specilaPriceCount = specilaPriceCount + (good.getShop_se_price() / good.getShop_price()) * 10;
                View v = inflater.inflate(R.layout.good_item, null);
                ImageView img_pro_pic = (ImageView) v.findViewById(R.id.img_pro_pic);
                TextView tv_sum = (TextView) v.findViewById(R.id.tv_sum);
                TextView tv_pro_name = (TextView) v.findViewById(R.id.tv_pro_name);
                TextView tv_pro_descri = (TextView) v.findViewById(R.id.tv_pro_descri);
                TextView tv_discout = (TextView) v.findViewById(R.id.tv_pro_discount);
                TextView tv_zero_kickback = (TextView) v.findViewById(R.id.item_tv_zero_kickback);

                tv_zero_kickback.setVisibility(View.VISIBLE);
                // 没有打折 钱的价格
                TextView tvProPrice = (TextView) v.findViewById(R.id.tv_pro_price);
                TextView tv_item_supply = (TextView) v.findViewById(R.id.tv_item_supply);


//                PicassoUtils.initImage(this,
//                        good.getShop_code().substring(1, 4) + "/" + good.getShop_code() + "/" + good.getDef_pic(),
//                        img_pro_pic);


                PicassoUtils.initImage(this, shop_pic, img_pro_pic);


                LogYiFu.e("TAG", "==good=" + good.getColor() + ",size=" + good.getSize());
                tv_sum.setText("x" + good.getShop_num());
                tv_pro_name.setText(good.getShop_name());
                tv_discout.setText("¥" + new DecimalFormat("#0.00").format(good.getShop_se_price()));

                tv_pro_descri.setText(shopAttrNewMeal);


                tv_zero_kickback.setText("返" + new DecimalFormat("#0.00").format(good.getShop_se_price()) + "元=0元购");
                tv_zero_kickback.setVisibility(View.GONE); //有1元购后去掉0元购


                // 设置没有打折的价格
                tvProPrice.setText("¥" + new DecimalFormat("#0.00").format(good.getShop_price()));

                shopPrice = new DecimalFormat("#0.00").format(good.getShop_price());
                tvProPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                String supp_label = good.getSupp_label();
                if (!TextUtils.isEmpty(supp_label) && !supp_label.equals("null")) {
                    tv_item_supply.setText(supp_label + "");

                }

                sumAccount += good.getShop_se_price() * good.getShop_num();

                mMapTen.put("" + good.getStock_type_id(), mTenUse);
                mMapFive.put("" + good.getStock_type_id(), mFiveUse);
                mMapTwo.put("" + good.getStock_type_id(), mTwoUse);
                mMapOne.put("" + good.getStock_type_id(), mOneUse);
                good_container.addView(v);
                mListShopCart.add(good);
                v.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        Intent intent = new Intent(instance, ShopDetailsActivity.class);
                        intent.putExtra("code", good.getShop_code());
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                    }
                });
                tvProPrice.setVisibility(View.GONE);
                tv_discout.setText("原价¥" + new DecimalFormat("#0.00").format(good.getShop_price()));


            }


            container.addView(view);
        }

        total_account.setText(Html.fromHtml(

                getString(R.string.total_account, shopNum, new DecimalFormat("#0.00").

                        format(sum))));

        mSubmitTotal.setText("¥" + shopPrice);
        tvFreeBuyYouhui.setText("-¥" + shopPrice);


    }


    /***
     *
     *            :需要付款的商品
     * @param container
     *            :显示商品的容器
     */
    private void addView(HashMap<Integer, List<ShopCart>> applyList, LinearLayout container,
                         HashMap<String, Object> mapCoupon) {
        container.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        Iterator<Entry<Integer, List<ShopCart>>> iterator = applyList.entrySet().iterator();
        int position = 0;


        while (iterator.hasNext()) {
            Entry<Integer, List<ShopCart>> entry = iterator.next();
            final List<ShopCart> shopCarts = (List<ShopCart>) entry.getValue();

            View view = inflater.inflate(R.layout.goods_item_free_buy, null);
            view.setBackgroundColor(Color.WHITE);


            // 设置分割线 第一条的隐藏
            position++;
            View line = view.findViewById(R.id.v_bottom_line);
            if (position == 1) {
                line.setVisibility(View.GONE);
            }

            LinearLayout good_container = (LinearLayout) view.findViewById(R.id.good_container);
            EditText edit_message = (EditText) view.findViewById(R.id.edit_message);
            mapEdit.put(entry.getKey(), edit_message);
            double sumAccount = 0.0;

            LogYiFu.e("TAG", "同一家商品的数量---" + shopCarts.size());
            int userful = 0;//
            double specilaPriceCount = 0;//专柜价总和
            for (int i = 0; i < shopCarts.size(); i++) {
                final ShopCart good = shopCarts.get(i);
                specilaPriceCount = specilaPriceCount + (good.getShop_se_price() / good.getShop_price()) * 10;
                View v = inflater.inflate(R.layout.good_item, null);
                ImageView img_pro_pic = (ImageView) v.findViewById(R.id.img_pro_pic);
                TextView tv_sum = (TextView) v.findViewById(R.id.tv_sum);
                TextView tv_pro_name = (TextView) v.findViewById(R.id.tv_pro_name);
                TextView tv_pro_descri = (TextView) v.findViewById(R.id.tv_pro_descri);
                TextView tv_discout = (TextView) v.findViewById(R.id.tv_pro_discount);
                TextView tv_zero_kickback = (TextView) v.findViewById(R.id.item_tv_zero_kickback);

                tv_zero_kickback.setVisibility(View.VISIBLE);
                // 没有打折 钱的价格
                TextView tvProPrice = (TextView) v.findViewById(R.id.tv_pro_price);
                TextView tv_item_supply = (TextView) v.findViewById(R.id.tv_item_supply);


                PicassoUtils.initImage(this,
                        good.getShop_code().substring(1, 4) + "/" + good.getShop_code() + "/" + good.getDef_pic(),
                        img_pro_pic);

                LogYiFu.e("TAG", "==good=" + good.getColor() + ",size=" + good.getSize());
                tv_sum.setText("x" + good.getShop_num());
                tv_pro_name.setText(good.getShop_name());
                tv_pro_descri.setText("颜色-" + good.getColor() + "    尺寸:" + good.getSize());
                tv_discout.setText("¥" + new DecimalFormat("#0.00").format(good.getShop_se_price()));


                tv_zero_kickback.setText("返" + new DecimalFormat("#0.00").format(good.getShop_se_price()) + "元=0元购");
                tv_zero_kickback.setVisibility(View.GONE); //有1元购后去掉0元购


                // 设置没有打折的价格
                tvProPrice.setText("¥" + new DecimalFormat("#0.00").format(good.getShop_price()));

                shopPrice = new DecimalFormat("#0.00").format(good.getShop_price());
                tvProPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                String supp_label = good.getSupp_label();
                if (!TextUtils.isEmpty(supp_label)) {
                    tv_item_supply.setText(supp_label + "");

                }

                tvProPrice.setVisibility(View.GONE);
                tv_discout.setText("原价¥" + new DecimalFormat("#0.00").format(good.getShop_price()));

                sumAccount += good.getShop_se_price() * good.getShop_num();

                mMapTen.put("" + good.getStock_type_id(), mTenUse);
                mMapFive.put("" + good.getStock_type_id(), mFiveUse);
                mMapTwo.put("" + good.getStock_type_id(), mTwoUse);
                mMapOne.put("" + good.getStock_type_id(), mOneUse);
                good_container.addView(v);
                mListShopCart.add(good);
                v.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        Intent intent = new Intent(instance, ShopDetailsActivity.class);
                        intent.putExtra("code", good.getShop_code());
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                    }
                });
            }


            container.addView(view);
        }

        total_account.setText(Html.fromHtml(

                getString(R.string.total_account, shopNum, new DecimalFormat("#0.00").

                        format(sum))));

        mSubmitTotal.setText("¥" + shopPrice);
        tvFreeBuyYouhui.setText("-¥" + shopPrice);

    }

    // 设置地址
    private void setDeliverAddress(HashMap<String, String> mapRet, DeliveryAddress dAddress) {
        if (null == mapRet && dAddress != null) {
            tv_name.setText("收件人：" + dAddress.getConsignee());
            tv_phone.setText(dAddress.getPhone());


            String province = db.queryAreaNameById(dAddress.getProvince()) != null && "0".equals(db.queryAreaNameById(dAddress.getProvince()))
                    ? db.queryAreaNameById(dAddress.getProvince()) : "";


            String city = db.queryAreaNameById(dAddress.getCity()) != null && "0".equals(db.queryAreaNameById(dAddress.getCity()))
                    ? db.queryAreaNameById(dAddress.getCity()) : "";


            String county = dAddress.getArea() != null && 0 != dAddress.getArea()
                    ? db.queryAreaNameById(dAddress.getArea()) : "";
            String street = "";


            if (null != dAddress.getStreet() && 0 != dAddress.getStreet()) {
                street = db.queryAreaNameById(dAddress.getStreet());
            }

            tv_receiver_addr.setText("收货地址：" + province + city + county + street + dAddress.getAddress());// 收货地址
            lin_receiver_addr.setVisibility(View.VISIBLE);
            rel_name_phone.setVisibility(View.VISIBLE);
            lin_set_addr.setVisibility(View.GONE);
        } else if (null != mapRet && dAddress == null) {
            lin_receiver_addr.setVisibility(View.VISIBLE);
            rel_name_phone.setVisibility(View.VISIBLE);
            lin_set_addr.setVisibility(View.GONE);
            tv_name.setText("收件人：" + mapRet.get("consignee"));
            tv_phone.setText(mapRet.get("phone"));
            tv_receiver_addr.setText("收货地址：" + mapRet.get("address"));// 收货地址
            lin_receiver_addr.setVisibility(View.VISIBLE);
            rel_name_phone.setVisibility(View.VISIBLE);
            lin_set_addr.setVisibility(View.GONE);
        } else if (null == mapRet && null == dAddress) {
            lin_receiver_addr.setVisibility(View.GONE);
            rel_name_phone.setVisibility(View.GONE);
            lin_set_addr.setVisibility(View.VISIBLE);
        }

    }


    /**
     * 提交订单
     */
    private void submitOrder(final View v) {

        UserInfo user = YCache.getCacheUser(instance);
        if (user.getGender() == 1) {
            ToastUtil.showShortText2("系统维护中，暂不支持支付");
            return;
        }


        Iterator<Entry<Integer, EditText>> iterator = mapEdit.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Integer, EditText> entry = iterator.next();
            String s = entry.getValue().getText().toString().trim();

            mapMsg.put(entry.getKey(), s);
        }


        waitDialog.show();
        new SAsyncTask<Void, Void, HashMap<String, Object>>(this, v, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {


                return ComModel2.submitShopcartOrdersFreeBuy(mVipInfo, instance, mapMsg, listGoods, 0, addressId,
                        111, mMapTen, mMapFive, mMapTwo, mMapOne, freeOrderPage, freeBuyType);


            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                waitDialog.dismiss();
                if (null == e) {

                    //  jumpDrawPage;//0 跳转抽奖  1跳订单列表


                    String jumpDrawPage = result.get("jumpDrawPage") + "";

                    if (jumpDrawPage.equals("1")) {

                        SubmitFreeBuyShopActivty.instance.startActivity(
                                new Intent(SubmitFreeBuyShopActivty.instance, StatusInfoActivity.class)
                                        .putExtra("index", 0)
                                        .putExtra("isUserFirstOrder", true));

                        if (null != ShopDetailsActivity.instance) {
                            ShopDetailsActivity.instance.finish();
                        }
                        if (null != HomePageThreeActivity.instance) {
                            HomePageThreeActivity.instance.finish();
                        }

                        SubmitFreeBuyShopActivty.this.finish();

                    } else {
                        goToLuckPan(result.get("order_code") + "");

                    }


                    /**
                     * freeBuyType 免费领类型
                     * 1：下单后分享免费领
                     * 2：会员下单后直接去抽奖
                     * 3: 有预存并且价格区间是对的，下单后去订单详情
                     * 4: 新用户免费领
                     *
                     * 下单第1种用order/addOrderFriendsShare,其他的都用order/addOrderVipFreeBuy
                     */

//                    switch (freeBuyType) {
//                        case 1: //不存在了
//                            SubmitFreeBuyShopActivty.instance.startActivity(
//                                    new Intent(SubmitFreeBuyShopActivty.instance, VipShareGroupsDetailsActivity.class)
//                                            .putExtra("isSubmit", true)
//                                            .putExtra("order_code", result.get("order_code") + ""));
//                            SubmitFreeBuyShopActivty.this.finish();
//                            break;
//                        case 2: //已经不存在了
//
//
//                            goToLuckPan(result.get("order_code") + "");
//
//                            break;
//                        case 3:
//                            goToOrderDetail(result.get("order_code") + "");
//
//                            break;
//                        case 4:
//                            SubmitFreeBuyShopActivty.instance.startActivity(
//                                    new Intent(SubmitFreeBuyShopActivty.instance, StatusInfoActivity.class)
//                                            .putExtra("index", 0)
//                                            .putExtra("isUserFirstOrder",true));
//
//                            SubmitFreeBuyShopActivty.this.finish();
//
//                            break;
//
//
//
//
//                    }


                }
            }

        }.execute((Void[]) null);
    }

    private void goToLuckPan(final String order_code) {

        new SAsyncTask<Void, Void, Order>(this, R.string.wait) {

            @Override
            protected Order doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.getMyOrderPaysuccss(instance, order_code);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(final FragmentActivity context, final Order result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    //去转盘
                    Intent OneBuyintent = new Intent(instance, OneBuyChouJiangActivity.class);
                    OneBuyintent.putExtra("isMeal", "1".equals(result.getIsTM()));

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order", result);
                    OneBuyintent.putExtras(bundle);
                    context.startActivity(OneBuyintent);
                    instance.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    SubmitFreeBuyShopActivty.this.finish();
                }
            }

        }.execute();


    }

//    private void goToOrderDetail(final String order_code) {
//
//        new SAsyncTask<Void, Void, Order>(this, R.string.wait) {
//
//            @Override
//            protected Order doInBackground(FragmentActivity context, Void... params)
//                    throws Exception {
//
//                return ComModel2.getMyOrderPaysuccss(context, order_code);
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(final FragmentActivity context, final Order result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (null == e) {
//
//
//                    Intent intent = new Intent(context,
//                            OrderDetailsActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("order", result);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                    SubmitFreeBuyShopActivty.this.finish();
//
//
//                }
//            }
//
//        }.execute();
//
//
//    }

    boolean isClick = false;

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_pay:
                YunYingTongJi.yunYingTongJi(SubmitFreeBuyShopActivty.this, 111);
                // mList
                // payMoney(v);
                if (addResult == null && dAddress == null) {
                    ToastUtil.showShortText(this, "请设置收货地址");
                    return;
                }

                if (CommonUtils.isNotFastClick()) {
                    submitOrder(null);

                }

//                if (!isClick) {

//                    if (mListShopCart.size() > 0) {
//
//
//                        submitOrder(null);
//                    } else {
//                        submitOrder(null);
//                    }
//                    isClick = true;
//                }

                break;
            case R.id.lin_receiver_addr:
                intent = new Intent(this, ManMyDeliverAddr.class);
                intent.putExtra("flag", "submitmultishop");
                startActivityForResult(intent, 1001);
                break;
            case R.id.lin_set_addr:
                intent = new Intent(this, SetDeliverAddressActivity.class);
                startActivityForResult(intent, 1002);
                break;
            case R.id.img_back:// 返回上一级
                customDialog();
                break;

            case R.id.img_back_share:
                customDialog();
                break;
            default:
                break;
        }
        super.onClick(v);
    }


    @Override
    public void onBackPressed() {

        rel_show_share.setVisibility(View.GONE);
    }


    private void customDialog() {
        final Dialog dialog = new Dialog(instance, R.style.invate_dialog_style);
        View view = View.inflate(instance, R.layout.dialog_order_back, null);
        TextView btn_cancel = (TextView) view.findViewById(R.id.btn_cancel);

        ((TextView) view.findViewById(R.id.balance_dialog_tv1)).setText("这么好的宝贝，确定不要了吗？");


        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        TextView btn_ok = (TextView) view.findViewById(R.id.btn_ok);
        btn_ok.setText("不要了");
        btn_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                dialog.dismiss();


                finish();
            }
        });

        // 创建自定义样式dialog
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1001) { // 修改地址
            if (null != intent) {
                dAddress = (DeliveryAddress) intent.getSerializableExtra("item");
                addressId = dAddress.getId();
                setDeliverAddress(null, dAddress);
            } else {
                initData(1002);
            }
        } else if (requestCode == 1002) { // 设置地址
            initData(requestCode);
        } else if (requestCode == 1003 && resultCode == 1) {
            SubmitFreeBuyShopActivty.this.setResult(1); // 1，代表支付成功；
            finish();
        } else if (requestCode == 1005 && resultCode == 2001) {

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 这里重写返回键
            customDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
