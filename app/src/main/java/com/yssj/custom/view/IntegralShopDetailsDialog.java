package com.yssj.custom.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.data.YDBHelper;
import com.yssj.entity.IntegralShop;
import com.yssj.entity.StockType;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;

/****
 * 商品详情页 立即购买对话框
 * 
 * @author Administrator
 * 
 */
public class IntegralShopDetailsDialog extends Dialog implements
		OnClickListener, OnItemClickListener {
	private Context context;
	private int height;
	private MyGridView gridviewColor, gridviewSzie;
	private GridViewAdpater adpaterColor, adpaterSzie;
	private ImageView img_cancle, img_toux, img_add, img_reduce;
	private TextView tv_name, tv_price, tv_ok, tv_clothes_number, tv_stock;
	private IntegralShop shop;
	private List<StockType> listsTypes;
	private YDBHelper helper;
	private String color, size;
	private int colorId = -1, sizeId = -1;
	private List<String> listColor, listSize;
	private List<String> colorIds = new ArrayList<String>(), sizeIds = new ArrayList<String>();
	private int stock, buyStock, buy_shopcart;

//	private DisplayImageOptions options;
//	private ImageLoadingListener animateFirstListener;
//	public ImageLoader imageLoader;
	
	private int  stock_type_id ;
	private String pic ; 
	private StockType sType;

	public interface OnCallBackShopCart {
		void callBackChoose(int type, String size, String color, int shop_num,
				double price,int stock_type_id, int stock,String pic, View v);
	}

	public OnCallBackShopCart callBackShopCart;

//	public IntegralShopDetailsDialog(Context context, int style, int height,
//			IntegralShop shop, DisplayImageOptions options,
//			ImageLoadingListener animateFirstListener, int buy_shopcart) {
//		super(context, style);
//		setCanceledOnTouchOutside(true);
//		this.height = height;
//		this.context = context;
//		this.shop = shop;
//		this.options = options;
//		this.animateFirstListener = animateFirstListener;
//		this.buy_shopcart = buy_shopcart;// 0购买 ， 1 加入购物车
//	}
	public IntegralShopDetailsDialog(Context context, int style, int height,
			IntegralShop shop, 
			 int buy_shopcart) {
		super(context, style);
		setCanceledOnTouchOutside(true);
		this.height = height;
		this.context = context;
		this.shop = shop;
		this.buy_shopcart = buy_shopcart;// 0购买 ， 1 加入购物车
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_shop_details);
		RelativeLayout dlg_lay = (RelativeLayout) findViewById(R.id.dlg_lay);
		LayoutParams rp = dlg_lay.getLayoutParams();
		rp.height = height * 2 / 3;
		dlg_lay.setLayoutParams(rp);
		img_cancle = (ImageView) findViewById(R.id.img_cancle);
		img_cancle.setOnClickListener(this);
		img_toux = (ImageView) findViewById(R.id.img_toux);
		//imageLoader = ImageLoader.getInstance();
//		YJApplication.getLoader().displayImage(
//				YUrl.imgurl + shop.getDef_pic(), img_toux, options,
//				animateFirstListener);

		
		PicassoUtils.initImage(context, shop.getDef_pic(), img_toux);
		
		
		tv_name = (TextView) findViewById(R.id.tv_name);

		String name = shop.getShop_name();
		if (!TextUtils.isEmpty(name)) {
			tv_name.setText(name);
		}

		tv_price = (TextView) findViewById(R.id.tv_price);
		String price = "" + shop.getShop_se_price();
		if (!TextUtils.isEmpty(price)) {
			tv_price.setText("¥" + price);
		}
		tv_stock = (TextView) findViewById(R.id.tv_stock);
		tv_ok = (TextView) findViewById(R.id.tv_ok);
		tv_ok.setOnClickListener(this);
		img_add = (ImageView) findViewById(R.id.img_add);
		img_add.setOnClickListener(this);
		img_reduce = (ImageView) findViewById(R.id.img_reduce);
		img_reduce.setOnClickListener(this);
		tv_clothes_number = (TextView) findViewById(R.id.tv_clothes_number);
		tv_clothes_number.setText("" + 0);
		listsTypes = shop.getList_stock_type();
		helper = new YDBHelper(context);
		listColor = new ArrayList<String>();
		listSize = new ArrayList<String>();
		updatesStock_type();

		// queryColor();
		// listSzie = querySzie();

		gridviewColor = (MyGridView) findViewById(R.id.gridview_shop_color);
		gridviewSzie = (MyGridView) findViewById(R.id.gridview_shop_size);

		if (listsTypes != null && listsTypes.size() > 0) {

			adpaterColor = new GridViewAdpater(listColor);
			adpaterSzie = new GridViewAdpater(listSize);
			gridviewColor.setAdapter(adpaterColor);
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// int colorId = -1, sizeId = -1;
		if (arg0 == gridviewColor) {// 颜色
			color = (String) listColor.get(arg2);
			colorId = Integer.parseInt( colorIds.get(arg2));
			adpaterColor.setPosition(arg2);
			adpaterColor.notifyDataSetChanged();
			showStock(colorId, sizeId);
		} else {// 尺码
			size = (String) listSize.get(arg2);
			sizeId = Integer.parseInt(sizeIds.get(arg2));
			adpaterSzie.setPosition(arg2);
			adpaterSzie.notifyDataSetChanged();
			showStock(colorId, sizeId);
		}

	}

	class GridViewAdpater extends BaseAdapter {

		private List<String> list;
		private int p = -1;

		public GridViewAdpater(List<String> list) {

			this.list = list;

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
				convertView = LayoutInflater.from(context).inflate(
						R.layout.gridview_shop_details, null);
			}
			// ImageView img_clothes_property = (ImageView)
			// convertView.findViewById(R.id.img_clothes_property);
			TextView tv_clothes_property = (TextView) convertView
					.findViewById(R.id.tv_clothes_property);
			tv_clothes_property.setText((CharSequence) list.get(position));
			if (position == p) {
				tv_clothes_property
						.setBackgroundResource(R.drawable.selector_tv_ok);
				tv_clothes_property.setTextColor(context.getResources()
						.getColor(R.color.white));
			} else {
				tv_clothes_property
						.setBackgroundResource(R.drawable.grid_bg);
				tv_clothes_property.setTextColor(context.getResources()
						.getColor(R.color.tv_shop_tcbn));
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
			String size = helper.queryAttr_name(str[1]);

			if (!listColor.contains(color)) {
				listColor.add(color);
				colorIds.add(str[0]);
			}
			if (!listSize.contains(size)) {
				listSize.add(size);
				sizeIds.add(str[1]);
			}

		}

	}

	private void showStock(int colorId, int sizeId) {
		LogYiFu.e("", colorId + "_" + sizeId);
		if (colorId != -1 && sizeId != -1) {
			// StockType stockType = helper.queryStock(color, size);
			for (int i = 0; i < listsTypes.size(); i++) {
				String color_size = colorId + ":" + sizeId;
				StockType sType = listsTypes.get(i);
				if (sType.getColor_size().equals(color_size)) {
					this.sType = sType;
					tv_price.setText("¥" + sType.getPrice());
					stock = sType.getStock();
					tv_stock.setText("库存" + stock + "件");// 库存总件
					stock_type_id = sType.getId();
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
			Toast.makeText(context, "请选择颜色", Toast.LENGTH_SHORT).show();
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
			tv_clothes_number.setText(String.valueOf(buyStock));

		} else if (buyStock > 1) {
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
		if (buyStock <= 0) {
			Toast.makeText(context, "请选择购买数量", Toast.LENGTH_SHORT).show();
			return;
		}
		if (buy_shopcart == 0) {// 购买
			if (callBackShopCart != null) {
				callBackShopCart.callBackChoose(1, size, color, buyStock,
						sType.getPrice(),stock_type_id,stock,pic, v);
			}
		} else {// 加入购物车
			if (callBackShopCart != null) {
				callBackShopCart.callBackChoose(0, size, color, buyStock,
						sType.getPrice(), stock_type_id,stock,pic,v);
			}
		}
	}

}
