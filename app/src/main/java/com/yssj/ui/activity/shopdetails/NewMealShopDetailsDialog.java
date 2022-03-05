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
import com.yssj.ui.activity.GuideActivity;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/****
 * 新特卖商品属性选择(单独购买和1元购共用)
 *
 * @author Administrator
 *
 */
public class NewMealShopDetailsDialog extends Dialog implements OnClickListener {
    private Context context;
    private int height;
    //    private MyGridView gridviewColor, gridviewSzie;
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
    private String mColor = "";
    private String mSize = "";
    private String mCount = "1";// 初始数量编辑框里的值
    private int colorPosition = 0;
    private int sizePosition = 0;
    private boolean isOnbuy;

    private LinearLayout container;

    String def;


    public interface OnCallBackShopCart {
        void callBackChoose(boolean isOneBuy, String color_size, String shop_code, int type, String size, String color, double price, int shop_num, int stock_type_id,
                            int stock, String pic, int supp_id, double kickback, int original_price, View v);
    }

    public OnCallBackShopCart callBackShopCart;

    public NewMealShopDetailsDialog(Context context, boolean isOnbuy, int style, int width, int height, Shop shop
            , boolean actvityFlag, String color, String size,
                                    String count) {
        super(context, style);
        setCanceledOnTouchOutside(true);
        this.height = height;
        this.context = context;
        this.width = width;
        this.shop = shop;
        this.isOnbuy = isOnbuy;


        this.buy_shopcart = buy_shopcart;// 0购买 ， 1 加入购物车,2购物车里修改颜色和尺码
        this.mColor = color;
        this.mSize = size;
        this.mCount = count;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_meal_dialog_shop_details);
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


        container = (LinearLayout) findViewById(R.id.container);


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
        if (!TextUtils.isEmpty(price)) {
            tv_price.setText("¥" + new java.text.DecimalFormat("#0.0").format(shop.getShop_se_price() - MealShopDetailsActivity.OnBuyDikouprice));
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
        helper = new YDBHelper(context);


        addView(container, shop);


    }


    private void addView(LinearLayout container, Shop shop) {


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
                    showStock(itemClicked, listsTypes, tv_stock, 0);
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
                        showStock(itemClicked, listsTypes, tv_stock, 0);
                        img_clothes_property.setBackgroundResource(R.drawable.grid_checked);
                    } else {
                        if (position == 0 && p == -1) {
                            itemClicked[j] = listMapAttrIds.get(j).get(0);
//							Log.e("hhhhhhh" + itemClicked[j]);


                            showStock(itemClicked, listsTypes, tv_stock, 0);
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
                    showStock(itemClicked, listsTypes, tv_stock, 0);
                    tv_clothes_property.setBackgroundResource(R.drawable.grid_checked);
                    tv_clothes_property.setTextColor(context.getResources().getColor(R.color.white));
                } else {
                    if (position == 0 && p == -1) {
                        itemClicked[j] = listMapAttrIds.get(j).get(0);
//						Log.e("hhhhhhh" + itemClicked[j]);
                        showStock(itemClicked, listsTypes, tv_stock, 0);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_cancle:
                dismiss();
                break;
            case R.id.img_add:

                if (isOnbuy) {
                    ToastUtil.showShortText(context, "1元购单次只能选择1件哦~");
                    return;
                }

                buyNumersClothes(0);
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


    private Integer[] stockIds;
    private Double[] prices;// 售价
    private StockBean[] mLists;

    private String allStockString;

    private void showStock(String[] strs, List<StockType> listsTypes, TextView tv_stock, int currentPos) {

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


                tv_price.setText("¥" + (new DecimalFormat("#0.0").format(mmPrice)));


                buyColor_size = listsTypes.get(i).getColor_size();
                buyPrice = new DecimalFormat("#0.0").format(shop.getShop_se_price());


                if (isOnbuy) {


                    String onPrice = shop.getApp_shop_group_price();
                    onPrice = new java.text.DecimalFormat("#0.0")
                            .format(Double.parseDouble(onPrice));
                    tv_price.setText(onPrice + "元");

                }

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

    private String buyColor_size = "";
    private String buyPrice = "";

    /***
     * 设置要买的数量
     *
     * @param type
     */
    private void buyNumersClothes(int type) {
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
//        if (TextUtils.isEmpty(color)) {
//            Toast.makeText(context, "请选择颜色", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(size)) {
//            Toast.makeText(context, "请选择尺码", Toast.LENGTH_SHORT).show();
//            return;
//        }
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
        ToastUtil.showShortText2("规格："+buyColor_size);

        if (callBackShopCart != null) {
            callBackShopCart.callBackChoose(isOnbuy, buyColor_size, sType.getShop_code(), 1, size, color, Double.parseDouble(buyPrice), buyStock, stock_type_id, stock,
                    newPic, sType.getSupp_id(), kickback, Integer.parseInt(sType.getCore()), v);
        }

    }

}
