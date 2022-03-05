package com.yssj.ui.activity.shopdetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
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
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.custom.view.MyGridView;
import com.yssj.data.YDBHelper;
import com.yssj.entity.ShopCart;
import com.yssj.entity.StockType;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.utils.PicassoUtils;

/****
 * 购物车窗口
 * @author Administrator
 *
 */
public class ShopCartDialog extends Dialog implements OnClickListener , OnItemClickListener{
	private Context context;
	private int height;	
	private ShopCart cart;
	private List<StockType> listsTypes;
	private YDBHelper helper;
	private String color, size ; 
	private List<String> listPic = new ArrayList<String>(), listSize = new ArrayList<String>();
	private List<String> listColor = new ArrayList<String>();
	private List<String> colorIds = new ArrayList<String>(), sizeIds = new ArrayList<String>();
	private int colorId = -1, sizeId = -1;
	private int stock , buyStock ;
	private double price ; 
	
	private MyGridView gridviewPic, gridviewSzie;
	private GridViewAdpater adpaterPic,adpaterSzie;
	private ImageView img_cancle , img_toux ,img_add ,img_reduce;
	private TextView tv_name ,tv_price ,tv_ok ,tv_clothes_number ,tv_stock;
	
//	public ImageLoader imageLoader;
	private String def_pic ; 
	private String stock_type_id  ; 

	 
	
	public interface ShopCartDialogOncallBack{
		//void updateOncallBack(double price , String color ,String size ,int number , String def_pic , View v);
		
		void updateOncallBack(double shop_se_price, String color,  String size , int shop_num ,
				String def_pic,String stock_type_id, View v);
		
		
	}
	
	public ShopCartDialogOncallBack cartDialogOncallBack;

	public ShopCartDialog(Context context,int style , int height , int position 
			,List<ShopCart> listShopCarts,List<StockType> list) {
		super(context,style);
		this.context = context;
		this.height = height;	
		this.cart = listShopCarts.get(position);
		this.listsTypes = list;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_shop_details);
		RelativeLayout dlg_lay = (RelativeLayout) findViewById(R.id.dlg_lay);
		LayoutParams rp = dlg_lay.getLayoutParams();
		rp.height = height*2/3;
		dlg_lay.setLayoutParams(rp);
		img_cancle = (ImageView) findViewById(R.id.img_cancle);
		img_cancle.setOnClickListener(this);
		img_toux = (ImageView) findViewById(R.id.img_toux);
//		imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.ic_stub)
//				.showImageForEmptyUri(R.drawable.ic_empty)
//				.cacheInMemory(true)
//				.cacheOnDisk(true).considerExifParams(true)
//				.displayer(new FadeInBitmapDisplayer(35)).build();
//		YJApplication.getLoader().displayImage(YUrl.imgurl + cart.getDef_pic(),
//				img_toux, options, animateFirstListener);
		
		
		PicassoUtils.initImage(context, cart.getDef_pic(), img_toux);
		
		
		tv_name = (TextView) findViewById(R.id.tv_name);
		
		String name = cart.getShop_name();
		if (!TextUtils.isEmpty(name)) {
			tv_name.setText(name);
		}
		
		
		tv_price = (TextView) findViewById(R.id.tv_price);
		String price = "¥"+cart.getShop_se_price();
		if (!TextUtils.isEmpty(price)) {
			tv_price.setText(price);
		}
		tv_stock = (TextView) findViewById(R.id.tv_stock);
		stock = listsTypes.get(0).getStock();
		if (!TextUtils.isEmpty( ""+stock)) {
			tv_stock.setText("库存"+ stock + "件");//库存总件
		}
		tv_ok = (TextView) findViewById(R.id.tv_ok);
		tv_ok.setOnClickListener(this);
		img_add = (ImageView) findViewById(R.id.img_add);
		img_add.setOnClickListener(this);
		img_reduce = (ImageView) findViewById(R.id.img_reduce);
		img_reduce.setOnClickListener(this);
		tv_clothes_number = (TextView) findViewById(R.id.tv_clothes_number);
		buyStock = cart.getShop_num();
		String colthes_number = ""+buyStock;
		tv_clothes_number.setText(colthes_number);
		
		helper = new YDBHelper(context);
		updatesStock_type();
		
		color = cart.getColor();
		size = cart.getSize();
		gridviewPic = (MyGridView) findViewById(R.id.gridview_shop_color);
		gridviewSzie = (MyGridView) findViewById(R.id.gridview_shop_size);
		
		if (listsTypes != null && listsTypes.size()>0) {
			
			adpaterPic = new GridViewAdpater(listPic ,1);
			adpaterSzie = new GridViewAdpater(listSize ,0);
			gridviewPic.setAdapter(adpaterPic);
			gridviewSzie.setAdapter(adpaterSzie);
			gridviewPic.setOnItemClickListener(this);
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
			
			commitShopCart(tv_ok);
			break;

		default:
			break;
		}
		
	}
	
	
	private void commitShopCart(View v) {
		buyStock = Integer.parseInt(tv_clothes_number.getText().toString());
		if (TextUtils.isEmpty(def_pic)) {
			Toast.makeText(context, "请选择颜色", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(size)) {
			Toast.makeText(context, "请选择尺码", Toast.LENGTH_SHORT).show();
			return;
		}
		if (buyStock<=0) {
			Toast.makeText(context, "请选择购买数量", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (cartDialogOncallBack != null) {
				cartDialogOncallBack.updateOncallBack(price, color, size, buyStock, def_pic, stock_type_id ,v);
			}
		
		
	}

	/***
	 * 设置要买的数量
	 * 
	 * @param type
	 */
	private void buyNumersClothes(int type) {
		if (TextUtils.isEmpty(def_pic)) {
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
	private void updatesStock_type(){
		
		
		for (int i = 0; i < listsTypes.size(); i++) {
			
			StockType sType = listsTypes.get(i);
			String color_size = sType.getColor_size();
			
			String[] str = color_size.split(":");
			String color = helper.queryAttr_name(str[0]);
			String size = helper.queryAttr_name(str[1]);
			if (!listColor.contains(color)) {
				listColor.add(color);
				colorIds.add(str[0]);
				listPic.add(sType.getPic());//加图片
			}
			if (!listSize.contains(size)) {
				listSize.add(size);
				sizeIds.add(str[1]);
			}
		

		}
	
		
	}
	
//	private static class AnimateFirstDisplayListener extends
//			SimpleImageLoadingListener {
//
//		static final List<String> displayedImages = Collections
//				.synchronizedList(new LinkedList<String>());
//
//		@Override
//		public void onLoadingComplete(String imageUri, View view,
//				Bitmap loadedImage) {
//			if (loadedImage != null) {
//				ImageView imageView = (ImageView) view;
//				boolean firstDisplay = !displayedImages.contains(imageUri);
//				if (firstDisplay) {
//					FadeInBitmapDisplayer.animate(imageView, 500);
//					displayedImages.add(imageUri);
//				}
//			}
//		}
//}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg0 == gridviewPic) {//颜色
			
			def_pic = listPic.get(arg2); 
			if (def_pic != null) {
//				YJApplication.getLoader().displayImage(YUrl.imgurl + def_pic,
//						img_toux, options, animateFirstListener);
				PicassoUtils.initImage(context, def_pic, img_toux);
			}
			
			colorId = Integer.parseInt( colorIds.get(arg2));
			color = listColor.get(arg2);
			showStock(colorId,sizeId);
		}else {//尺码
			adpaterSzie.setFlag(true);
			size =  listSize.get(arg2);
			adpaterSzie.setPosition(arg2);
			adpaterSzie.notifyDataSetChanged();
			sizeId = Integer.parseInt(sizeIds.get(arg2));
			showStock(colorId,sizeId);
		}
		
	}
	private void showStock(int colorId, int sizeId){
		if (colorId != -1 && sizeId != -1) {
			// StockType stockType = helper.queryStock(color, size);
			for (int i = 0; i < listsTypes.size(); i++) {
				String color_size = colorId + ":" + sizeId;
				StockType sType = listsTypes.get(i);
				if (sType.getColor_size().equals(color_size)) {
					tv_price.setText("¥" + sType.getPrice());
					stock = sType.getStock();
					tv_stock.setText("库存" + stock + "件");// 库存总件
					color = helper.queryAttr_name(sType.getColor_size().split(":")[0]);
					stock_type_id = sType.getId()+"";
					i = listsTypes.size();//跳出循环
				}

			}
		}
	}
	class GridViewAdpater extends BaseAdapter{

		private List<String> list;
		private int p  = -1; 
		private int mtype = -1;
		private boolean flag = false;
		public void setFlag(boolean flag){
			
			this.flag = flag;
		}
		public GridViewAdpater( List<String> list , int type) {

			this.list = list;
			this.mtype = type ; 
		}
		public void setPosition(int p ){
			this. p = p ;
			
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
			if (mtype == 1) {
				String pic = list.get(position);
				if (!TextUtils.isEmpty(pic)) {
					pic = YUrl.imgurl + list.get(position);	
//					YJApplication.getLoader().displayImage(pic,
//							img_clothes_property, options, animateFirstListener);
					PicassoUtils.initImage(context, pic, img_clothes_property);
				}
				img_clothes_property.setVisibility(View.VISIBLE);
				tv_clothes_property.setVisibility(View.GONE);
			}else if (mtype == 0) {
				String str = list.get(position);
				tv_clothes_property.setText(str);
				if (position == p) {
					tv_clothes_property.setBackgroundResource(R.drawable.selector_tv_ok);
					tv_clothes_property.setTextColor(context.getResources().getColor(R.color.white));
				}else {
					tv_clothes_property.setBackgroundResource(R.drawable.grid_bg);
					tv_clothes_property.setTextColor(context.getResources().getColor(R.color.tv_shop_tcbn));
				}
				img_clothes_property.setVisibility(View.GONE);
				tv_clothes_property.setVisibility(View.VISIBLE);
			}
			return convertView;
		}
	}
}
