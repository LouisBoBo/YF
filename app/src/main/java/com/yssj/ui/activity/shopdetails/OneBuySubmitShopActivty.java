package com.yssj.ui.activity.shopdetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
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
import com.yssj.entity.ShopCart;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.activity.OneBuyGroupsDetailsActivity;
import com.yssj.ui.activity.setting.ManMyDeliverAddr;
import com.yssj.ui.activity.setting.SetDeliverAddressActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.YCache;
import com.yssj.utils.YunYingTongJi;
import com.yssj.utils.sqlite.ShopCartDao;
import com.yssj.wxpay.WxPayUtil;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 1元购提交订单
 */
@SuppressLint("StringFormatMatches")
public class OneBuySubmitShopActivty extends BasicActivity {
    @Bind(R.id.submit_total2)
    TextView submitTotal2;
    private TextView tv_name, tv_phone, tv_receiver_addr;

    private LinearLayout lin_receiver_addr;
    int m = 0;
    private DBService db = new DBService(this);
    private RelativeLayout mTouBg;
    private TextView tv_settle_account;//实付款

    private TextView tvTitle_base;
    private LinearLayout img_back;

    private int addressId = 0;

    private DeliveryAddress dAddress;
    private HashMap<String, String> addResult;
    private String orderNo;

    IWXAPI msgApi;// 微信api

    private LinearLayout lin_set_addr;

    private List<ShopCart> listGoods;

    private LinearLayout container;
    private List<ShopCart> mListShopCart = new ArrayList<ShopCart>();// 用来存储使用抵用券的商品集合

    private int mTenUse = 0, mFiveUse = 0, mTwoUse = 0, mOneUse = 0;// 每个商品使用的优惠券数量
    private HashMap<String, Integer> mMapTen = new HashMap<String, Integer>();
    private HashMap<String, Integer> mMapFive = new HashMap<String, Integer>();
    private HashMap<String, Integer> mMapTwo = new HashMap<String, Integer>();
    private HashMap<String, Integer> mMapOne = new HashMap<String, Integer>();

    double sum = 0.00;
    private HashMap<Integer, List<ShopCart>> mapListGood = new HashMap<Integer, List<ShopCart>>();

    private HashMap<Integer, String> mapMsg = new HashMap<Integer, String>();

    private HashMap<Integer, EditText> mapEdit = new HashMap<Integer, EditText>();


    private RelativeLayout rel_name_phone;


    int shopNum = 0;


    public static OneBuySubmitShopActivty instance;

    private List<ShopCart> shopCartMeal = new ArrayList<ShopCart>();


    boolean flag = false;


    private LinearLayout btn_pay;

    private TextView submit_total;//商品金额

    private String onePrice = "1";
    private String showPTprice = "0.0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        aBar.hide();
        instance = this;
        flag = false;
        setContentView(R.layout.onebuy_submit_multi_goods);
        ButterKnife.bind(this);

        submit_total = (TextView) findViewById(R.id.submit_total);
        img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        onePrice = getIntent().getStringExtra("onePrice");

        btn_pay = (LinearLayout) findViewById(R.id.btn_pay);// 支付按钮

        btn_pay.setOnClickListener(this);


        tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("确认订单");


        listGoods = (List<ShopCart>) getIntent().getExtras().getSerializable("listGoods"); //bundle传过来的


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

        }
        msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp(WxPayUtil.APP_ID);


        double groupPrice = 0.0;
        try {
            groupPrice = Double.parseDouble(onePrice);
        } catch (Exception e) {
            e.printStackTrace();
        }

        showPTprice = new DecimalFormat("#0.0").format(groupPrice);


        initData(0);


    }

    @Override
    protected void onResume() {
        super.onResume();
        isClick = false;
        HomeWatcherReceiver.registerHomeKeyReceiver(this);
        SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1053");
        TongJiUtils.TongJi(this, 11 + "");
        LogYiFu.e("TongJiNew", 11 + "");//到达提交订单界面
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        HomeWatcherReceiver.unregisterHomeKeyReceiver(this);
        TongJiUtils.TongJi(this, 111 + "");
        LogYiFu.e("TongJiNew", 111 + "");//跳出提交订单界面

    }

    private void initData(final int requestCode) {
        LogYiFu.i("TAG", "多订单");
        new SAsyncTask<Void, Void, HashMap<String, String>>(this, R.string.wait) {// 获取地址

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getDefaultDeliverAddr(context);
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (requestCode == 1002) {// 设置地址
                    OneBuySubmitShopActivty.this.addResult = result;
                    // 设置地址
                    setDeliverAddress(result, null);
                    return;
                } else {
                    initView(result);
                }

            }

        }.execute();

        // 对要付款的物品进行分类 同一家发货，不同家发货
        // listGoods = (List<ShopCart>) getIntent().getSerializableExtra(
        // "listGoods");

    }

    private void initView(HashMap<String, String> result) {
        // TODO:
        mTouBg = (RelativeLayout) findViewById(R.id.submit_bg);
        mTouBg.setBackgroundColor(Color.WHITE);

        container = (LinearLayout) findViewById(R.id.container);
        tv_name = (TextView) findViewById(R.id.tv_name);// 收件人
        tv_phone = (TextView) findViewById(R.id.tv_phone);// 收件人电话
        tv_receiver_addr = (TextView) findViewById(R.id.tv_receiver_addr);// 收件地址
        lin_receiver_addr = (LinearLayout) findViewById(R.id.lin_receiver_addr);
        lin_receiver_addr.setOnClickListener(this);
        lin_set_addr = (LinearLayout) findViewById(R.id.lin_set_addr);
        lin_set_addr.setOnClickListener(this);
        this.addResult = result;


        tv_settle_account = (TextView) findViewById(R.id.tv_settle_account);// 合计多少钱

        rel_name_phone = (RelativeLayout) findViewById(R.id.rel_name_phone);


        // mTgbs.setChecked(true);
        // 设置地址
        setDeliverAddress(result, null);
        initOther();

    }


    private void initOther() {

        submitTotal2.setText("-¥" + showPTprice);
        submit_total.setText("¥" + showPTprice);
        tv_settle_account.setText("实付款¥" + 0.0);


        for (int i = 0; i < listGoods.size(); i++) {
            ShopCart cart = listGoods.get(i);
            shopNum = shopNum + cart.getShop_num();
            // sum = sum + (cart.getShop_num() * cart.getShop_se_price());
        }

        addView(mapListGood, container, null);
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


            // double original_price = 0;
            // 获取同一商家的购物车
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
                TextView tvProPrice = (TextView) v.findViewById(R.id.tv_pro_price);
                TextView tv_item_supply = (TextView) v.findViewById(R.id.tv_item_supply);


                PicassoUtils.initImage(this,
                        good.getShop_code().substring(1, 4) + "/" + good.getShop_code() + "/" + good.getDef_pic(),
                        img_pro_pic);

                LogYiFu.e("TAG", "==good=" + good.getColor() + ",size=" + good.getSize());
                tv_sum.setText("x" + good.getShop_num());
                tv_pro_name.setText(good.getShop_name());
                tv_pro_descri.setText("颜色-" + good.getColor() + "    尺寸:" + good.getSize());


//                if (ShopDetailsActivity.isNewUser) {
//                    tv_discout.setText("¥0.0");
//                } else {
//                    tv_discout.setText("¥"+ GuideActivity.oneShopPrice);


                tv_discout.setText("¥" + showPTprice);


//                }

//                tv_discout.setText("¥" + new DecimalFormat("#0.00").format(good.getShop_se_price()));


                tv_zero_kickback.setVisibility(View.GONE);

                // 设置没有打折的价格
                tvProPrice.setText("¥" + new DecimalFormat("#0.00").format(good.getShop_price()));
                tvProPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                String supp_label = good.getSupp_label();
                if (!TextUtils.isEmpty(supp_label)) {
                    tv_item_supply.setText(supp_label + "");
                }


                mMapTen.put("" + good.getStock_type_id(), mTenUse);
                mMapFive.put("" + good.getStock_type_id(), mFiveUse);
                mMapTwo.put("" + good.getStock_type_id(), mTwoUse);
                mMapOne.put("" + good.getStock_type_id(), mOneUse);

                good_container.addView(v);
                LogYiFu.e("TAG", "添加空间（同一家）");
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


            if (userful <= 0) {


                container.addView(view);
            }


        }
    }

    // 设置地址
    private void setDeliverAddress(HashMap<String, String> mapRet, DeliveryAddress dAddress) {
        if (null == mapRet && dAddress != null) {
            tv_name.setText("收件人：" + dAddress.getConsignee());
            tv_phone.setText(dAddress.getPhone());


            String province = db.queryAreaNameById(dAddress.getProvince()) != null && "0".equals(db.queryAreaNameById(dAddress.getProvince()))
                    ? db.queryAreaNameById(dAddress.getProvince()) : "";


//            String city = db.queryAreaNameById(dAddress.getCity());


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
        if(user.getGender() == 1){
            ToastUtil.showShortText2("系统维护中，暂不支持支付");
            return;
        }

//		Log.e("hello", "000000");
        Iterator<Entry<Integer, EditText>> iterator = mapEdit.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Integer, EditText> entry = iterator.next();
            String s = entry.getValue().getText().toString().trim();
//            if (!TextUtils.isEmpty(s)) {
//                if (StringUtils.containsEmoji(s)) {
//                    Toast.makeText(context, "不得含有特殊字符", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (RegisterFragment.getWordCount(s) < 5) {
//                    Toast.makeText(context, "订单说明不得少于五个字符", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (RegisterFragment.getWordCount(s) > 500) {
//                    Toast.makeText(context, "订单说明不得多于五百个字符", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
            mapMsg.put(entry.getKey(), s);
        }


        final StringBuffer sb = new StringBuffer();

        new SAsyncTask<Void, Void, HashMap<String, Object>>(this, v, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

//                return ComModel2.submitShopOneYuan(context, mapMsg, listGoods, 0, addressId,
//                        0, mMapTen, mMapFive, mMapTwo, mMapOne, 0);


                return ComModel2.submitOneBuyTuan(context, addressId, listGoods, mapMsg, -1);

//                return ComModel2.submitShopOneYuan(context, mapMsg, listGoods, 0, addressId,
//                        0, mMapTen, mMapFive, mMapTwo, mMapOne, 0);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {


                    orderNo = (String) result.get("order_code");
                    SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);
                    Intent OneBuyintent = new Intent(OneBuySubmitShopActivty.this, OneBuyGroupsDetailsActivity.class);
                    OneBuyintent.putExtra("order_code", orderNo);
                    OneBuyintent.putExtra("roll_code", result.get("roll_code") + "");
                    OneBuyintent.putExtra("isMeal", false);
                    startActivity(OneBuyintent);


                    OneBuySubmitShopActivty.this.finish();


                }
            }

        }.execute((Void[]) null);
    }

    boolean isClick = false;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_pay:
                YunYingTongJi.yunYingTongJi(OneBuySubmitShopActivty.this, 111);
                if (addResult == null && dAddress == null) {
                    ToastUtil.showShortText(this, "请设置收货地址");
                    return;
                }


                if (!isClick) {
                    submitOrder(null);
                    isClick = true;
                }

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


                finish();
                break;

            default:
                break;
        }
        super.onClick(v);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
            OneBuySubmitShopActivty.this.setResult(1); // 1，代表支付成功；
            LogYiFu.e("TAG", "提交订单，刷新购物车。。");
            finish();
        } else if (requestCode == 1005 && resultCode == 2001) {


            setMoney(sum, true);
        }
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    public void setMoney(double sum, boolean flag) {

    }


}
