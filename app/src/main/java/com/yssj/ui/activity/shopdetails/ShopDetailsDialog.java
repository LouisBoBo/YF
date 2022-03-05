package com.yssj.ui.activity.shopdetails;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.custom.view.MyGridView;
import com.yssj.data.YDBHelper;
import com.yssj.entity.Shop;
import com.yssj.entity.StockType;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
////import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/****
 * 商品详情页 立即购买对话框
 *
 * @author Administrator
 *
 */
public class ShopDetailsDialog extends Dialog implements OnClickListener, OnItemClickListener {
    private Context context;
    private int height;
    private MyGridView gridviewColor, gridviewSzie;
    private GridViewAdpater adpaterPic, adpaterSzie;
    private ImageView img_cancle, img_toux, img_add, img_reduce;
    private TextView tv_name, tv_price, tv_ok, tv_clothes_number, tv_stock;
    private Shop shop;
    private List<StockType> listsTypes;
    private YDBHelper helper;
    private String color, size;
    private List<String> listPic, listSize;
    private List<String> colorIds = new ArrayList<String>(), sizeIds = new ArrayList<String>();
    private List<String> listColor = new ArrayList<String>();
    private int colorId = -1, sizeId = -1;

    private int stock, buyStock, buy_shopcart;
    private int stock_type_id;
    private String pic;

    private double kickback;

//	private DisplayImageOptions options;
//	private ImageLoadingListener animateFirstListener;
    // public ImageLoader imageLoader;

    private StockType sType;
    private int width;
    private String newPic;
    private List<String> listNewPic;
    private boolean actvityFlag = false;
    private String mColor = "";
    private String mSize = "";
    private String mCount = "1";// 初始数量编辑框里的值
    private int colorPosition = 0;
    private int sizePosition = 0;

    public interface OnCallBackShopCart {
        void callBackChoose(int type, String size, String color, double price, int shop_num, int stock_type_id,
                            int stock, String pic, int supp_id, double kickback, int original_price, View v);
    }

    public OnCallBackShopCart callBackShopCart;

    public ShopDetailsDialog(Context context, int style, int width, int height, Shop shop
            , int buy_shopcart, boolean actvityFlag, String color, String size,
                             String count) {
        super(context, style);
        setCanceledOnTouchOutside(true);
        this.height = height;
        this.context = context;
        this.width = width;
        this.shop = shop;


        this.buy_shopcart = buy_shopcart;// 0购买 ， 1 加入购物车,2购物车里修改颜色和尺码
        this.actvityFlag = actvityFlag;
        this.mColor = color;
        this.mSize = size;
        this.mCount = count;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_shop_details);
        listPic = new ArrayList<String>();
        listSize = new ArrayList<String>();
        listColor = new ArrayList<String>();
        listNewPic = new ArrayList<String>();
        /*
         * android.view.WindowManager.LayoutParams
         * ly=getWindow().getAttributes(); ly.height=height * 2 / 3;
         * ly.width=width; getWindow().setAttributes(ly);
         */
        RelativeLayout dlg_lay = (RelativeLayout) findViewById(R.id.dlg_lay);
        LayoutParams rp = dlg_lay.getLayoutParams();
        rp.width = width;
        rp.height = height * 2 / 3;
        dlg_lay.setLayoutParams(rp);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);
        img_cancle = (ImageView) findViewById(R.id.img_cancle);
        img_cancle.setOnClickListener(this);
        img_toux = (ImageView) findViewById(R.id.img_toux);
        // imageLoader = ImageLoader.getInstance();
        String def = YUrl.imgurl + shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/"
                + shop.getDef_pic();
//		YJApplication.getLoader();
//		ImageLoader.getInstance().displayImage(def, img_toux, options, animateFirstListener);
        // id = shop.getId();
        tv_name = (TextView) findViewById(R.id.tv_name);

        String name = shop.getShop_name();
        if (!TextUtils.isEmpty(name)) {
            tv_name.setText(name);
        }

        tv_price = (TextView) findViewById(R.id.tv_price);
        String price = "" + shop.getShop_se_price();
        String group_price = "" + shop.getShop_group_price();
        if (!actvityFlag) {
            if (!TextUtils.isEmpty(price)) {
                double mPrice = shop.getShop_se_price() - ShopDetailsActivity.OnBuyDikouprice;
                if (mPrice <= 0) {
                    mPrice = 0;
                }


                tv_price.setText("¥" + new java.text.DecimalFormat("#0.0").format(mPrice));
            }
        } else {
            if (!TextUtils.isEmpty(group_price)) {
                tv_price.setText("¥" + new java.text.DecimalFormat("#0.0").format(shop.getShop_group_price()));
            }
        }
        tv_stock = (TextView) findViewById(R.id.tv_stock);// 库存
        // String invertory_num = shop.getInvertory_num() + "";
        // if (!TextUtils.isEmpty(invertory_num)) {
        // tv_stock.setText("库存" + invertory_num + "件");// 库存总件
        // }
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(this);
        img_add = (ImageView) findViewById(R.id.img_add);
        img_add.setOnClickListener(this);
        img_reduce = (ImageView) findViewById(R.id.img_reduce);
        img_reduce.setOnClickListener(this);
        tv_clothes_number = (TextView) findViewById(R.id.tv_clothes_number);
        if (buy_shopcart == 2) {
            tv_clothes_number.setText("" + mCount);
        } else {
            tv_clothes_number.setText("" + 1);
        }
        listsTypes = shop.getList_stock_type();
        LogYiFu.e("Shop", shop.toString());
        helper = new YDBHelper(context);
        updatesStock_type();

        gridviewColor = (MyGridView) findViewById(R.id.gridview_shop_color);
        gridviewSzie = (MyGridView) findViewById(R.id.gridview_shop_size);

        if (listsTypes != null && listsTypes.size() > 0) {

            // adpaterPic:颜色的adapter：颜色图片，颜色文字
            // List<String> list, int type, int a

            // 使用颜色图片
            // if (!listPic.contains("") && !listPic.contains(null)) {
            // adpaterPic = new GridViewAdpater(listPic, 1, 1); //填充颜色图片
            // } else {
            // adpaterPic = new GridViewAdpater(listColor, 0, 1);//就是颜色文字
            // }

            // 只保留颜色文字
            if (listColor.size() != 0) {
                adpaterPic = new GridViewAdpater(listColor, 0, 1);// 就是颜色文字

            }
            if (buy_shopcart == 2) {// 模仿手动点击了颜色
                String pic = shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/"
                        + listPic.get(colorPosition);
                if (pic != null) {
//					YJApplication.getLoader().displayImage(YUrl.imgurl + pic, img_toux, options, animateFirstListener);
                    PicassoUtils.initImage(context, pic, img_toux);
                }
                newPic = listPic.get(colorPosition);
                colorId = Integer.parseInt(colorIds.get(colorPosition));
                adpaterPic.setPosition(colorPosition);
                adpaterPic.notifyDataSetChanged();
                color = " ";
                showStock(colorId, sizeId);
            }
            // adpaterSzie：尺码的adapter
            adpaterSzie = new GridViewAdpater(listSize, 0, 0);// 尺码
            if (buy_shopcart == 2) {// 模仿手动点击了尺寸
                size = listSize.get(sizePosition);
                // if (listSize.size() == 1) {
                // size = "均码";
                // }

                adpaterSzie.setPosition(sizePosition);
                adpaterSzie.notifyDataSetChanged();
                sizeId = Integer.parseInt(sizeIds.get(sizePosition));
                showStock(colorId, sizeId);
            }
            gridviewColor.setAdapter(adpaterPic);
            gridviewSzie.setAdapter(adpaterSzie);
            gridviewColor.setOnItemClickListener(this);
            gridviewSzie.setOnItemClickListener(this);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_cancle:
                dismiss();
                break;
            case R.id.img_add:
                if (actvityFlag) {
                    ToastUtil.showShortText(context, "拼团商品只能选择1件哦~");
                } else {
                    buyNumersClothes(0);
                }
                break;
            case R.id.img_reduce:
                buyNumersClothes(1);
                break;
            case R.id.tv_ok:

                setOk(tv_ok);
                break;

            default:
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        if (arg0 == gridviewColor) {// 颜色

            // 提交图片统统 提交默认图
            String pic = shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + listPic.get(arg2);
            if (pic != null) {
//				YJApplication.getLoader().displayImage(YUrl.imgurl + pic, img_toux, options, animateFirstListener);
                PicassoUtils.initImage(context, pic, img_toux);
            }
            newPic = listPic.get(arg2);
            colorId = Integer.parseInt(colorIds.get(arg2));
            adpaterPic.setPosition(arg2);
            adpaterPic.notifyDataSetChanged();
            color = " ";
            showStock(colorId, sizeId);
        } else {// 尺码
            size = listSize.get(arg2);
            // if (listSize.size() == 1) {
            // size = "均码";
            // }

            adpaterSzie.setPosition(arg2);
            adpaterSzie.notifyDataSetChanged();
            sizeId = Integer.parseInt(sizeIds.get(arg2));
            showStock(colorId, sizeId);
        }

    }

    class GridViewAdpater extends BaseAdapter {

        private List<String> list;
        private int p = -1;
        private int type = -1;
        private int a = -1;// 0尺寸，1颜色

        public GridViewAdpater(List<String> list, int type, int a) {

            this.list = list;
            this.type = type;
            this.a = a;

            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    if (null != list.get(i)) {
                        if (a == 1) {// 颜色
                            if (mColor.trim().equals(list.get(i).trim())) {
                                colorPosition = i;
                                break;
                            }
                        } else {
                            if (mSize.trim().equals(list.get(i).trim())) {
                                sizePosition = i;
                                break;
                            }
                        }
                    }

                }
            }

        }

        public void setPosition(int p) {
            this.p = p;

        }

        @Override
        public int getCount() {

            return list.size();
        }

        @Override
        public Object getItem(int position) {

            return list.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.gridview_shop_details, null);
            }
            ImageView img_clothes_property = (ImageView) convertView.findViewById(R.id.img_clothes_property);
            TextView tv_clothes_property = (TextView) convertView.findViewById(R.id.tv_clothes_property);
            if (type == 1) { // 普通商品

                // list只存放图片---需要添加颜色

                String pic = list.get(position);

                LogYiFu.e("商品属性", "list---" + list);
                LogYiFu.e("商品属性", "shop---" + shop);

                if (!TextUtils.isEmpty(pic)) {
                    // 用shop_code拼接图片下载链接
                    pic = shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + list.get(position);
                    img_clothes_property.setTag(pic);
//					SetImageLoader.initImageLoader(context, img_clothes_property, pic, "");
                    PicassoUtils.initImage(context, pic, img_clothes_property);
                    // ImageLoader.getInstance()
                    // .displayImage(pic, img_clothes_property, options,
                    // animateFirstListener);
                    img_clothes_property.setVisibility(View.VISIBLE);
                    tv_clothes_property.setVisibility(View.GONE);
                    if (position == p) {
                        img_clothes_property.setBackgroundResource(R.drawable.grid_checked);
                    } else {
                        img_clothes_property.setBackgroundResource(R.drawable.grid_bg);
                    }

                }

            } else { // type是0 --------颜色文字

                String str = list.get(position);
                tv_clothes_property.setText(str);
                // if(buy_shopcart==2&&p==-1){
                // if(mColor.equals(str)||mSize.equals(str)){
                // tv_clothes_property.setBackgroundResource(R.drawable.grid_checked);
                // tv_clothes_property.setTextColor(context.getResources().getColor(R.color.white));
                // }else{
                // tv_clothes_property.setBackgroundResource(R.drawable.grid_bg);
                // tv_clothes_property.setTextColor(context.getResources().getColor(R.color.tv_shop_tcbn));
                // }
                // }
                if (position == p) {
                    tv_clothes_property.setBackgroundResource(R.drawable.grid_checked);
                    tv_clothes_property.setTextColor(context.getResources().getColor(R.color.white));
                } else {
                    tv_clothes_property.setBackgroundResource(R.drawable.grid_bg);
                    tv_clothes_property.setTextColor(context.getResources().getColor(R.color.tv_shop_tcbn));
                }
                img_clothes_property.setVisibility(View.GONE);
                tv_clothes_property.setVisibility(View.VISIBLE);
            }

            // if (list.size() == 1) {
            // tv_clothes_property.setText("均码");
            // }

            if (buy_shopcart != 2 && position == 0 && p == -1) {
                if (a == 1) {// 颜色

                    // 提交图片统统 提交默认图
                    String pic = shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/"
                            + listPic.get(position);
                    if (pic != null) {
//						YJApplication.getLoader().displayImage(YUrl.imgurl + pic, img_toux, options,
//								animateFirstListener);
                        PicassoUtils.initImage(context, pic, img_toux);
                    }
                    newPic = listPic.get(position);
                    colorId = Integer.parseInt(colorIds.get(position));
                    adpaterPic.setPosition(position);
                    // adpaterPic.notifyDataSetChanged();
                    color = " ";
                    showStock(colorId, sizeId);
                } else {// 尺码
                    size = listSize.get(position);
                    // if (listSize.size() == 1) {
                    // size = "均码";
                    // }

                    adpaterSzie.setPosition(position);
                    // adpaterSzie.notifyDataSetChanged();
                    sizeId = Integer.parseInt(sizeIds.get(position));
                    showStock(colorId, sizeId);
                }
            }
            return convertView;
        }
    }

    private void updatesStock_type() {

        for (int i = 0; i < listsTypes.size(); i++) {
            StockType sType = listsTypes.get(i);
            String color_size = sType.getColor_size();
            String[] str = color_size.split(":");
            String color = helper.queryAttr_name(str[0]);
            // String size = helper.queryAttr_name(str[1]);

            if (!listColor.contains(color)) {
                listColor.add(color);
                colorIds.add(str[0]);
                listPic.add(sType.getPic());// 加图片
            }
            if (!sizeIds.contains(str[1])) {
                // listSize.add(size);
                sizeIds.add(str[1]);
            }

        }

        // Collections.sort(sizeIds);
        Collections.sort(sizeIds, new Comparator<String>() {

            @Override
            public int compare(String lhs, String rhs) {
                // TODO Auto-generated method stub
                return Integer.valueOf(lhs).compareTo(Integer.valueOf(rhs));
            }
        });

        for (int i = 0; i < sizeIds.size(); i++) {
            String size = helper.queryAttr_name(sizeIds.get(i));
            listSize.add(size);
        }

    }

    private void showStock(int colorId, int sizeId) {

        if (colorId != -1 && sizeId != -1) {
            // StockType stockType = helper.queryStock(color, size);
            for (int i = 0; i < listsTypes.size(); i++) {
                String color_size = colorId + ":" + sizeId;
                StockType sType = listsTypes.get(i);
                if (sType.getColor_size().equals(color_size)) {
                    this.sType = sType;
                    if (actvityFlag) {
                        try {
                            tv_price.setText("¥" + new java.text.DecimalFormat("#0.0").format(sType.getGroup_price()));
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    } else {

                        double mPrice = sType.getPrice() - ShopDetailsActivity.OnBuyDikouprice;
                        if (mPrice <= 0) {
                            mPrice = 0;
                        }


                        tv_price.setText("¥" + new java.text.DecimalFormat("#0.0").format(mPrice));


//                        tv_price.setText("¥" + new java.text.DecimalFormat("#0.0").format(sType.getPrice() - ShopDetailsActivity.OnBuyDikouprice));
                    }
                    stock = sType.getStock();
                    tv_stock.setText("库存" + stock + "件");// 库存总件
                    color = helper.queryAttr_name(sType.getColor_size().split(":")[0]);
                    kickback = sType.getKickback();
                    stock_type_id = sType.getId();
                    i = listsTypes.size();// 跳出循环

                }

            }
        }
    }

    /***
     * 设置要买的数量
     *
     * @param type
     */
    private void buyNumersClothes(int type) {
        if (TextUtils.isEmpty(color)) {
            Toast.makeText(context, "请选择颜色图片", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(size)) {
            Toast.makeText(context, "请选择尺码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (stock <= 0) {

            return;
        }
        buyStock = Integer.parseInt(tv_clothes_number.getText().toString());
        if (type == 0 && buyStock < stock) {
            buyStock++;
            if (buyStock > 2) {
                buyStock = 2;
                ToastUtil.showLongText(context, "抱歉,数量有限,最多只能购买两件噢!");
            }
            tv_clothes_number.setText(String.valueOf(buyStock));

        } else if (type == 1 && buyStock > 1) {
            buyStock--;
            tv_clothes_number.setText(String.valueOf(buyStock));
        }

    }

    /***
     * 提交
     */
    private void setOk(View v) {
        if (TextUtils.isEmpty(color)) {
            Toast.makeText(context, "请选择颜色", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(size)) {
            Toast.makeText(context, "请选择尺码", Toast.LENGTH_SHORT).show();
            return;
        }
        buyStock = Integer.parseInt(tv_clothes_number.getText().toString());
        if (buyStock <= 0) {
            Toast.makeText(context, "请选择购买数量", Toast.LENGTH_SHORT).show();
            return;
        }
        if (stock <= 0) {
            ToastUtil.showShortText(context, "库存不足");
            return;
        }
        if (null == pic) {
            pic = shop.getDef_pic();
        }
        if (buy_shopcart == 0) {// 购买
            if (callBackShopCart != null) {
                // if (listSize.size() == 1) {
                // size = "均码";
                // }
                // double a = sType.getOriginal_price();
                // int original_prices = (int) a;
                callBackShopCart.callBackChoose(1, size, color, sType.getPrice(), buyStock, stock_type_id, stock,
                        newPic, sType.getSupp_id(), kickback, Integer.parseInt(sType.getCore()), v);
            }
        } else if (buy_shopcart == 1) {// 加入购物车
            // if (listSize.size() == 1) {
            // size = "均码";
            // }
            // TODO:
            if (callBackShopCart != null && stock != 0) {
                // double a = sType.getCore();
                // int original_prices = (int) a;

                String s = "0";

                if (sType.getCore() == null || sType.getCore().equals("null")) {

                } else {
                    s = sType.getCore();
                }

                callBackShopCart.callBackChoose(0, size, color, sType.getPrice(), buyStock, stock_type_id, stock,
                        newPic, sType.getSupp_id(), kickback, Integer.parseInt(s), v);
                LogYiFu.e("zlj", "oo" + Integer.parseInt(s));
            }
            // 加个判断统计抢购浏览加入购物车数量
        } else if (buy_shopcart == 2) {//购物车里修改颜色和尺寸
            if (callBackShopCart != null) {
                callBackShopCart.callBackChoose(1, size, color, sType.getPrice(), buyStock, stock_type_id, stock,
                        newPic, sType.getSupp_id(), kickback, Integer.parseInt(sType.getCore()), v);
            }
        }
    }

}
