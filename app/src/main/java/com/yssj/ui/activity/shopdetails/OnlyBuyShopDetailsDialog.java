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
import android.widget.LinearLayout;
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
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


/****
 *单独购买商品属性选择
 *
 * @author Administrator
 *
 */
public class OnlyBuyShopDetailsDialog extends Dialog implements OnClickListener, OnItemClickListener {
    private Context context;
    private int height;
    private MyGridView gridviewColor, gridviewSzie;
    private GridViewAdpater adpaterPic, adpaterSzie;
    private ImageView img_cancle, img_toux, img_add, img_reduce;
    private TextView tv_name, tv_price, tv_ok, tv_clothes_number, tv_stock, tv_yufahuo;
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
    private String mColor = "";
    private String mSize = "";
    private String mCount = "1";// 初始数量编辑框里的值
    private int colorPosition = 0;
    private int sizePosition = 0;
    private double vipPayOnlyBuyPrice;

    private LinearLayout container;
    private String def;


    public interface OnCallBackShopCartNewMeal {
        void callBackChooseNewMeal(String color_size, String shop_code, int type, String size, String color, double price, int shop_num, int stock_type_id,
                                   int stock, String pic, int supp_id, double kickback, int original_price, View v);
    }

    public OnCallBackShopCartNewMeal callBackShopCartNewMeal;


    public interface OnCallBackShopCart {
        void callBackChoose(int type, String size, String color, double price, int shop_num, int stock_type_id,
                            int stock, String pic, int supp_id, double kickback, int original_price, View v);
    }

    public OnCallBackShopCart callBackShopCart;

    public OnlyBuyShopDetailsDialog(Context context, int style, int width, int height, Shop shop
            , int buy_shopcart, String color, String size,
                                    String count, double vipPayOnlyBuyPrice) {
        super(context, style);
        setCanceledOnTouchOutside(true);
        this.height = height;
        this.context = context;
        this.width = width;
        this.shop = shop;
        this.vipPayOnlyBuyPrice = vipPayOnlyBuyPrice;


        this.buy_shopcart = buy_shopcart;// 0购买 ， 1 加入购物车,2购物车里修改颜色和尺码
        this.mColor = color;
        this.mSize = size;
        this.mCount = count;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ShopDetailsActivity.isNewMeal) {
            setContentView(R.layout.new_meal_dialog_shop_details);
            listPic = new ArrayList<String>();
            listSize = new ArrayList<String>();
            listColor = new ArrayList<String>();
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


            container = findViewById(R.id.container);
            tv_yufahuo = findViewById(R.id.tv_yufahuo);


            img_cancle = (ImageView) findViewById(R.id.img_cancle);
            img_cancle.setOnClickListener(this);
            img_toux = (ImageView) findViewById(R.id.img_toux);
            // imageLoader = ImageLoader.getInstance();
            def = YUrl.imgurl + shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/"
                    + shop.getDef_pic();

            PicassoUtils.initImage(context, def, img_toux);


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
//            if (!TextUtils.isEmpty(price)) {
//                tv_price.setText("¥" + new java.text.DecimalFormat("#0.0").format(shop.getShop_se_price() - MealShopDetailsActivity.OnBuyDikouprice));
//            }
            tv_price.setText("¥" + new DecimalFormat("#0.0").format(vipPayOnlyBuyPrice));


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
            helper = new YDBHelper(context);


            addViewNewMeal(container, shop);
        } else {
            setContentView(R.layout.dialog_shop_details);
            listPic = new ArrayList<String>();
            listSize = new ArrayList<String>();
            listColor = new ArrayList<String>();
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
            tv_yufahuo = findViewById(R.id.tv_yufahuo);

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


        if(shop.getAdvance_sale_days() > 0){
            tv_yufahuo.setText("发货时间：付款后"+shop.getAdvance_sale_days() +"天内");

            tv_yufahuo.setVisibility(View.VISIBLE);


        }else{
            tv_yufahuo.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_cancle:
                dismiss();
                break;
            case R.id.img_add:
//                if (ShopDetailsActivity.isNewMeal) {
//                    buyNumersClothesNewMeal(0);
//
//                } else {
//                    buyNumersClothes(0);
//
//                }


                break;
            case R.id.img_reduce:

                if (ShopDetailsActivity.isNewMeal) {
                    buyNumersClothesNewMeal(1);

                } else {
                    buyNumersClothes(1);

                }
                break;
            case R.id.tv_ok:


                if (ShopDetailsActivity.isNewMeal) {
                    setOkNewMeal(tv_ok);


                } else {
                    setOk(tv_ok);


                }
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

                    tv_price.setText("¥" + new DecimalFormat("#0.0").format(vipPayOnlyBuyPrice));

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

    private String buyColor_size = "";
    private String buyPrice = "";

    private void showStockNewMeal(String[] strs, List<StockType> listsTypes, TextView tv_stock, int currentPos) {

        for (int i = 0; i < strs.length; i++) {
            if (strs[i] == null) {
                return;
            }
        }

        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < strs.length; j++) {
            sb.append(strs[j]);
            if (j != strs.length - 1) {
                sb.append(":");
            }
        }

        for (int i = 0; i < listsTypes.size(); i++) {

            if (listsTypes.get(i).getColor_size().equals(sb.toString())) {
                String colorPic;
                if (null == listsTypes.get(i).getAttr_pic() || StringUtils.isEmpty(listsTypes.get(i).getAttr_pic())) {
                    colorPic = def;
                } else {
                    colorPic = YUrl.imgurl + shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/"
                            + listsTypes.get(i).getAttr_pic();


                }

                newPic = colorPic;


                PicassoUtils.initImage(context, colorPic, img_toux);


                Double mPrice = listsTypes.get(i).getPrice();
                int supp_id = listsTypes.get(i).getSupp_id();
                StockBean bean = new StockBean();
                bean.setStock_type_id(listsTypes.get(i).getId());
                bean.setShopCode(listsTypes.get(i).getShop_code());
                bean.setSupp_id(listsTypes.get(i).getSupp_id());
                bean.setColor(helper.queryAttr_name(listsTypes.get(i).getColor_size()));
                // mList.add(bean);
                stock = listsTypes.get(i).getStock();
                tv_stock.setText("库存" + stock + "件");// 库存总件

                stock_type_id = listsTypes.get(i).getId();
                stockIds[currentPos] = stock_type_id;
                prices[currentPos] = listsTypes.get(i).getPrice();
                mLists[currentPos] = bean;
//                tv_price.setText("¥" + prices[currentPos] + "");


//                tv_price.setText("¥" + new java.text.DecimalFormat("#0.0").format(mPrice));


                double mmPrice = shop.getShop_se_price() - MealShopDetailsActivity.OnBuyDikouprice;
                if (mmPrice <= 0) {
                    mmPrice = 0;
                }


//                tv_price.setText("¥" + (new DecimalFormat("#0.0").format(mmPrice)));

                tv_price.setText("¥" + new DecimalFormat("#0.0").format(vipPayOnlyBuyPrice));


                buyColor_size = listsTypes.get(i).getColor_size();
                buyPrice = new DecimalFormat("#0.0").format(shop.getShop_se_price());


                i = listsTypes.size();// 跳出循环
                if (stock == 0) {// 库存总件为零
                    stockIds[currentPos] = null;
                }
            } else {
                tv_stock.setText("库存" + 0 + "件");// 库存总件
                stockIds[currentPos] = null;
                prices[currentPos] = null;
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

    private void buyNumersClothesNewMeal(int type) {
//        if (TextUtils.isEmpty(color)) {
//            Toast.makeText(context, "请选择颜色图片", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(size)) {
//            Toast.makeText(context, "请选择尺码", Toast.LENGTH_SHORT).show();
//            return;
//        }
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
        if (callBackShopCart != null) {
            callBackShopCart.callBackChoose(1, size, color, sType.getPrice(), buyStock, stock_type_id, stock,
                    newPic, sType.getSupp_id(), kickback, Integer.parseInt(sType.getCore()), v);
        }


    }


    /***
     * 提交
     */
    private void setOkNewMeal(View v) {
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

        ToastUtil.showShortText2("规格：" + buyColor_size);


        if (callBackShopCartNewMeal != null) {
            callBackShopCartNewMeal.callBackChooseNewMeal(buyColor_size, sType.getShop_code(), 1, size, color, Double.parseDouble(buyPrice), buyStock, stock_type_id, stock,
                    newPic, sType.getSupp_id(), kickback, Integer.parseInt(sType.getCore()), v);
        }

    }

    private Integer[] stockIds;
    private Double[] prices;// 售价
    private StockBean[] mLists;

    private void addViewNewMeal(LinearLayout container, Shop shop) {


        container.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(context);

        stockIds = new Integer[1];
        prices = new Double[1];
        mLists = new StockBean[1];


        View view = inflater.inflate(R.layout.meal_container_item, null);
        LinearLayout item_container = (LinearLayout) view.findViewById(R.id.item_container);
        final TextView tv_stock = (TextView) view.findViewById(R.id.tv_stock);

        final List<StockType> listsTypes = shop.getList_stock_type();
        /***
         * 保存Attr数据
         **/
        final HashMap<Integer, List<String>> listMapAttrName = new HashMap<Integer, List<String>>();
        final HashMap<Integer, List<String>> listMapAttrIds = new HashMap<Integer, List<String>>();
        for (int k = 0; k < listsTypes.size(); k++) {
            sType = listsTypes.get(k);
            String[] st = sType.getColor_size().split(":");

            for (int j = 0; j < st.length; j++) {
                String attrName = helper.queryAttr_name(st[j]);

                List<String> listAttrNames = listMapAttrName.get(j);

                if (listAttrNames != null) {
                    if (!listAttrNames.contains(attrName)) {
                        listAttrNames.add(attrName);
                    }
                } else {
                    List<String> lista = new ArrayList<String>();
                    lista.add(attrName);
                    listMapAttrName.put(j, lista);
                }

                List<String> listAttrIds = listMapAttrIds.get(j);
                if (listAttrIds != null) {
                    if (!listAttrIds.contains(st[j])) {
                        listAttrIds.add(st[j]);
                    }
                } else {
                    List<String> lista = new ArrayList<String>();
                    lista.add(st[j]);
                    listMapAttrIds.put(j, lista);
                }

            }

        }
        String[] st = listsTypes.get(0).getColor_size().split(":");// 判断当前页面几个属性
        final String[] itemClicked = new String[st.length];
        final String[] itemClickedName = new String[st.length];
        for (int j = 0; j < st.length; j++) {
            View itemView = inflater.inflate(R.layout.meal_attrs_container, null);
            TextView tv_shop_attr = (TextView) itemView.findViewById(R.id.tv_shop_attr);
            final MyGridView gridview_shop_attr = (MyGridView) itemView.findViewById(R.id.gridview_shop_attr);
            tv_shop_attr.setText(helper.queryParentAttr_name(st[j]));// 查询出商品属性的父类
            // 显示
            itemClickedName[j] = helper.queryParentAttr_name(st[j]);


            final NewMealGridViewAdpater mAdapter = new NewMealGridViewAdpater(listMapAttrName.get(j), 0, 0, j, itemClicked,
                    listMapAttrIds, listsTypes, tv_stock);


            gridview_shop_attr.setAdapter(mAdapter);
            item_container.addView(itemView);

            final int currentPos = j;

            gridview_shop_attr.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    mAdapter.setPosition(arg2);
                    mAdapter.notifyDataSetChanged();
                    itemClicked[currentPos] = listMapAttrIds.get(currentPos).get(arg2);
//						LogYiFu.e("hhhhhhh" + itemClicked[currentPos]);
                    showStockNewMeal(itemClicked, listsTypes, tv_stock, 0);
                }
            });

        }

//        mapChoose.put(i, itemClicked);
//        mapChoosedName.put(i, itemClickedName);

        container.addView(view);

    }

    class NewMealGridViewAdpater extends BaseAdapter {

        private List<String> list;
        private int p = -1;
        private int type = -1;
        private int i;
        private int j;
        private String[] itemClicked;
        private HashMap<Integer, List<String>> listMapAttrIds;
        private List<StockType> listsTypes;
        private TextView tv_stock;

        public NewMealGridViewAdpater(List<String> list, int type, int i, int j, String[] itemClicked,
                                      HashMap<Integer, List<String>> listMapAttrIds, List<StockType> listsTypes, TextView tv_stock) {

            this.list = list;
            this.type = type;
            this.i = i;
            this.j = j;
            this.itemClicked = itemClicked;
            this.listMapAttrIds = listMapAttrIds;
            this.listsTypes = listsTypes;
            this.tv_stock = tv_stock;
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
            if (type == 1) {

                String pic = list.get(position);
                if (!TextUtils.isEmpty(pic)) {
                    img_clothes_property.setTag(pic);
//					SetImageLoader.initImageLoader(context, img_clothes_property, pic, "");
                    PicassoUtils.initImage(context, pic, img_clothes_property);
                    // ImageLoader.getInstance()
                    // .displayImage(pic, img_clothes_property, options,
                    // animateFirstListener);
                    img_clothes_property.setVisibility(View.VISIBLE);
                    tv_clothes_property.setVisibility(View.GONE);
                    if (position == p || list.size() == 1) {
                        itemClicked[j] = listMapAttrIds.get(j).get(position);
//						Log.e("hhhhhhh" + itemClicked[j]);
                        showStockNewMeal(itemClicked, listsTypes, tv_stock, 0);
                        img_clothes_property.setBackgroundResource(R.drawable.grid_checked);
                    } else {
                        if (position == 0 && p == -1) {
                            itemClicked[j] = listMapAttrIds.get(j).get(0);
//							Log.e("hhhhhhh" + itemClicked[j]);


                            showStockNewMeal(itemClicked, listsTypes, tv_stock, 0);
                            img_clothes_property.setBackgroundResource(R.drawable.grid_checked);
                        } else {
                            img_clothes_property.setBackgroundResource(R.drawable.grid_bg);
                        }
                    }
                }

            } else {

                String str = list.get(position);
                tv_clothes_property.setText(str);
                if (position == p || list.size() == 1) {
                    itemClicked[j] = listMapAttrIds.get(j).get(position);
//					Log.e("hhhhhhh" + itemClicked[j]);
                    showStockNewMeal(itemClicked, listsTypes, tv_stock, 0);
                    tv_clothes_property.setBackgroundResource(R.drawable.grid_checked);
                    tv_clothes_property.setTextColor(context.getResources().getColor(R.color.white));
                } else {
                    if (position == 0 && p == -1) {
                        itemClicked[j] = listMapAttrIds.get(j).get(0);
//						Log.e("hhhhhhh" + itemClicked[j]);
                        showStockNewMeal(itemClicked, listsTypes, tv_stock, 0);
                        tv_clothes_property.setBackgroundResource(R.drawable.grid_checked);
                        tv_clothes_property.setTextColor(context.getResources().getColor(R.color.white));
                    } else {
                        tv_clothes_property.setBackgroundResource(R.drawable.grid_bg);
                        tv_clothes_property.setTextColor(context.getResources().getColor(R.color.tv_shop_tcbn));
                    }
                }
                img_clothes_property.setVisibility(View.GONE);
                tv_clothes_property.setVisibility(View.VISIBLE);
            }

            return convertView;
        }
    }

}
