package com.yssj.ui.adpter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.yssj.Constants;
import com.yssj.YConstance;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.MyPopupwindow;
import com.yssj.custom.view.MyPopupwindow.ShopCartGetShare;
import com.yssj.entity.ShopCart;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.ShopCartActivity;
import com.yssj.ui.activity.ShopCartCommonPager;
//import com.yssj.ui.activity.ShopCartSpecialFragment;
//import com.yssj.ui.activity.ShopCartSpecialPager;
import com.yssj.ui.activity.shopdetails.NoShareActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.activity.shopdetails.StockBean;
import com.yssj.ui.dialog.PublicToastDialog;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 购物车item适配器
 * 
 * @author Administrator
 * 
 */
public class ShopCartAdpter extends ArrayAdapter<ShopCart> implements ShopCartGetShare {

	private Context mContext;
	private ArrayList<Integer> spImg;
	private int resource;
	private List<ShopCart> list;
	// public ImageLoader imageLoader;
//	private DisplayImageOptions options;
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private int mSign;
	private MyPopupwindow myPopupwindow;
	// private boolean isEdit=false;//编辑状态
	private int mInvalidPosition = 0;
	private List<ShopCart> editList;

	private String code;
	private String p_code;
	// private ShopCart shopCart;
	private Intent qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
	private Intent wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
private String flag_activity="0";
	public interface ShopCartAdpterInterface {

		void selectOnCallBack(int position);

		void selectImgOnCallBack(int position);

		void addOnCallBack(int position, int shop_number);

		void reduceOnCallBack(int position, int shop_number);

		void updatetOnCallBack(int position);

		void deleteOnCallBack(int position);

		void onItemClickLintener(int position);

		void newJoinOnCallBack(int position);
	}

	public ShopCartAdpterInterface cartadpterInterface;

	public ShopCartAdpter(Context context, int resource, List<ShopCart> list, ArrayList<Integer> sp,
			ArrayList<Integer> spImg, int mSign, int mIvalidPosition,String flag_activity) {
		super(context, resource, list);
		this.list = list;
		this.spImg = spImg;
		this.mContext = context;
		this.resource = resource;
		this.mSign = mSign;
		this.mInvalidPosition = mIvalidPosition;
		this.flag_activity=flag_activity;
		// imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub)
//				.showImageForEmptyUri(R.drawable.ic_empty).cacheInMemory(true).cacheOnDisk(true)
//				.considerExifParams(true).displayer(new FadeInBitmapDisplayer(35)).build();
		editList = new ArrayList<ShopCart>();
	}

	// public void setCartOncallback(ShopCartCommonFragment f) {
	// this.cartadpterInterface = f;
	// }

//	public void setCartOncallback(ShopCartSpecialFragment f) {
//		this.cartadpterInterface = f;
//	}

	public void setCartOncallback(ShopCartActivity shopCartActivity) {
		this.cartadpterInterface = shopCartActivity;
	}

	public void setCartOncallback(ShopCartCommonPager shopCartCommonPager) {
		this.cartadpterInterface = shopCartCommonPager;
	}

//	public void setCartOncallback(ShopCartSpecialPager shopCartSpecialPager) {
//		this.cartadpterInterface = shopCartSpecialPager;
//	}

	public void deleteSucess(ShopCart cart) {
		editList.remove(cart);
	}

	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		} else {
			return list.size();
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ShopCart shopCart = list.get(position);
		// list.get(1).setValid(1);
		boolean isAlready = false;
		View view = convertView;
		final Holder holder;
		// if (view == null) {
		holder = new Holder();
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		view = mInflater.inflate(resource, null);
		if (shopCart.getValid() == 1) {
			if (mInvalidPosition > 0 && position < mInvalidPosition) {
				spImg.set(position, 2);
			}
		}

		if (mSign == 1) {
			holder.img_choose = (ImageView) view.findViewById(R.id.img_choose);
			holder.img_goods = (ImageView) view.findViewById(R.id.img_goods);
			holder.tv_goods_name = (TextView) view.findViewById(R.id.tv_goods_name);
			holder.tv_color = (TextView) view.findViewById(R.id.tv_color);
			holder.tv_size = (TextView) view.findViewById(R.id.tv_size);
			holder.tv_item_price = (TextView) view // 价格
					.findViewById(R.id.tv_item_price);
			holder.tv_item_nprice = (TextView) view// 打折前的价格
					.findViewById(R.id.tv_item_nprice);
			holder.editGoods = (TextView) view.findViewById(R.id.edit_cart);
			holder.tv_shop_price = (TextView) view.findViewById(R.id.tv_shop_price);
			holder.tv_shop_se_price = (TextView) view.findViewById(R.id.tv_shop_se_price);
			holder.price_ly = view.findViewById(R.id.price_lay);
			holder.edit_ly = view.findViewById(R.id.ed_count);

			holder.tv_goods_num = (EditText) view.findViewById(R.id.tv_goods_num);
			holder.goods = (TextView) view.findViewById(R.id.goods_count);
			holder.tv_goods_num.setFocusable(true);
			holder.tv_goods_num.setFocusableInTouchMode(true);

			holder.btnReduce = (Button) view.findViewById(R.id.btn_reduce);// 按钮减
			holder.btnAdd = (Button) view.findViewById(R.id.btn_add);// 按钮加
			holder.tv_return_money = (TextView) view.findViewById(R.id.tv_return_money);// 返回佣金
			holder.tv_discount_price = (TextView) view.findViewById(R.id.tv_discount_price);// 折后价
			holder.btn_share = (Button) view.findViewById(R.id.btn_share);

			holder.mealText = (TextView) view.findViewById(R.id.meal);
			holder.sizeColor = view.findViewById(R.id.size_color);
			holder.mValid = (TextView) view.findViewById(R.id.shopcart_shixiao);
			holder.mDiscount = (TextView) view.findViewById(R.id.discount_price);
			holder.img_alpha = (ImageView) view.findViewById(R.id.img_goods_alpha);
			if (shopCart.getValid() == 1) {

				// final int pImg = spImg.get(position);
				holder.img_alpha.setVisibility(View.VISIBLE);
				holder.mValid.setVisibility(View.VISIBLE);
				holder.tv_item_price.setTextColor(0xffc5c5c5);
				holder.mDiscount.setTextColor(0xffc5c5c5);
				holder.btn_share.setBackgroundColor(0xffc5c5c5);
				// holder.btn_share.setClickable(false);
			} else {
				holder.img_alpha.setVisibility(View.GONE);
				holder.img_choose.setClickable(true);
				holder.mValid.setVisibility(View.GONE);
				holder.btnReduce.setClickable(true);
				holder.btnAdd.setClickable(true);
				holder.tv_item_price.setTextColor(0xff222222);
				holder.mDiscount.setTextColor(0xff222222);
				holder.btn_share.setBackgroundColor(0xffff3f8b);
				// holder.btn_share.setClickable(true);
			}

			// view.setTag(holder);

		} else {
			// LinearLayout
			// in_ll_guangguang,shopcart_common_ll_invalid;//内部去逛逛,重新加入提示语
			// TextView special_delete_cart;//重新加入删除按钮
			// Button special_new_join;//重新加入按钮
			// RelativeLayout speical_rl_bottom;//底部提示
			holder.in_ll_guangguang = (LinearLayout) view.findViewById(R.id.in_ll_guangguang);
			holder.mLayClick=(LinearLayout) view.findViewById(R.id.lay);
			holder.shopcart_common_ll_invalid = (LinearLayout) view.findViewById(R.id.shopcart_common_ll_invalid);
			holder.special_delete_cart = (TextView) view.findViewById(R.id.special_delete_cart);
			holder.special_new_join = (Button) view.findViewById(R.id.special_new_join);
			holder.new_lines = view.findViewById(R.id.new_lines);
			holder.in_btn_to_shop = (Button) view.findViewById(R.id.in_btn_to_shop);
			holder.speical_rl_bottom = (RelativeLayout) view.findViewById(R.id.speical_rl_bottom);
			holder.goods = (TextView) view.findViewById(R.id.goods_count);
			holder.edit_ly = view.findViewById(R.id.ed_count);
			holder.mLine1 = (LinearLayout) view.findViewById(R.id.shopcart_line1);
			holder.mLine2 = (LinearLayout) view.findViewById(R.id.shopcart_line2);
			holder.mLine3 = (LinearLayout) view.findViewById(R.id.shopcart_line3);
			holder.tvColor1 = (TextView) view.findViewById(R.id.shopcart_tv1);
			holder.tvColor2 = (TextView) view.findViewById(R.id.shopcart_tv2);
			holder.tvColor3 = (TextView) view.findViewById(R.id.shopcart_tv3);
			holder.ordersLine = (LinearLayout) view.findViewById(R.id.shopcart_line);
			holder.total = (TextView) view.findViewById(R.id.tv_discount_price);
			holder.tv_shop_price = (TextView) view.findViewById(R.id.tv_shop_price);
			holder.tv_shop_se_price = (TextView) view.findViewById(R.id.tv_shop_se_price);
			holder.tv_return_money = (TextView) view.findViewById(R.id.tv_return_money);
			holder.tv_goods_num = (EditText) view.findViewById(R.id.tv_goods_num);
			holder.tv_goods_num.setFocusable(true);
			holder.tv_goods_num.setFocusableInTouchMode(true);
			holder.btn_share = (Button) view.findViewById(R.id.btn_share);
			holder.editGoods = (TextView) view.findViewById(R.id.edit_cart);
			holder.img_choose = (ImageView) view.findViewById(R.id.img_choose);
			holder.img_goods = (ImageView) view.findViewById(R.id.img_goods);
			holder.tv_goods_name = (TextView) view.findViewById(R.id.tv_goods_name);

			holder.btnReduce = (Button) view.findViewById(R.id.btn_reduce);// 按钮减
			holder.btnAdd = (Button) view.findViewById(R.id.btn_add);// 按钮加
			holder.mValid = (TextView) view.findViewById(R.id.shopcart_shixiao);
			holder.mDiscount = (TextView) view.findViewById(R.id.discount_price);
			holder.img_alpha = (ImageView) view.findViewById(R.id.img_goods_alpha);
			if (shopCart.getValid() == 1) {
				holder.img_alpha.setVisibility(View.VISIBLE);
				holder.mValid.setVisibility(View.VISIBLE);
				holder.mDiscount.setTextColor(0xffc5c5c5);
				holder.btn_share.setBackgroundColor(0xffc5c5c5);
				holder.total.setTextColor(0xffc5c5c5);
				// holder.btn_share.setClickable(false);
			} else {
				holder.img_alpha.setVisibility(View.GONE);
				holder.mValid.setVisibility(View.GONE);
				holder.mDiscount.setTextColor(0xff222222);
				holder.btn_share.setBackgroundColor(0xffff3f8b);
				holder.total.setTextColor(0xffFF3F8C);
				// holder.btn_share.setClickable(true);
			}

			// view.setTag(holder);
		}

		// LinearLayout
		// in_ll_guangguang,shopcart_common_ll_invalid;//内部去逛逛,重新加入提示语
		// TextView special_delete_cart;//重新加入删除按钮
		// Button special_new_join;//重新加入按钮
		// RelativeLayout speical_rl_bottom;//底部提示
		if (mInvalidPosition == -1) {// 没有无效的
			holder.in_ll_guangguang.setVisibility(View.GONE);
			holder.shopcart_common_ll_invalid.setVisibility(View.GONE);
			holder.special_delete_cart.setVisibility(View.VISIBLE);
			holder.editGoods.setVisibility(View.GONE);
			holder.special_new_join.setVisibility(View.GONE);
			holder.new_lines.setVisibility(View.VISIBLE);
			holder.btn_share.setVisibility(View.GONE);
			holder.speical_rl_bottom.setVisibility(View.VISIBLE);
			holder.img_choose.setVisibility(View.GONE);
		} else {
			if (position < mInvalidPosition) {// 有效
				holder.in_ll_guangguang.setVisibility(View.GONE);
				holder.shopcart_common_ll_invalid.setVisibility(View.GONE);
				holder.special_delete_cart.setVisibility(View.VISIBLE);
				holder.editGoods.setVisibility(View.GONE);
				holder.special_new_join.setVisibility(View.GONE);
				holder.new_lines.setVisibility(View.VISIBLE);
				holder.btn_share.setVisibility(View.GONE);
				holder.speical_rl_bottom.setVisibility(View.VISIBLE);
				holder.img_choose.setVisibility(View.GONE);
			} else if (position >= mInvalidPosition) {
				if (position == 0 && mInvalidPosition == 0) {// 有效列表为空
					holder.in_ll_guangguang.setVisibility(View.VISIBLE);//
					holder.shopcart_common_ll_invalid.setVisibility(View.VISIBLE);
					holder.special_delete_cart.setVisibility(View.VISIBLE);
					holder.editGoods.setVisibility(View.GONE);
					holder.special_new_join.setVisibility(View.VISIBLE);
					holder.new_lines.setVisibility(View.GONE);
					holder.btn_share.setVisibility(View.GONE);
					holder.speical_rl_bottom.setVisibility(View.GONE);
					holder.img_choose.setVisibility(View.GONE);
				} else {// 无效

					holder.img_choose.setVisibility(View.GONE);
					holder.in_ll_guangguang.setVisibility(View.GONE);
					if (position == mInvalidPosition && !isAlready) {
						holder.shopcart_common_ll_invalid.setVisibility(View.VISIBLE);
						isAlready = true;
					} else {
						holder.shopcart_common_ll_invalid.setVisibility(View.GONE);
					}
					holder.special_delete_cart.setVisibility(View.VISIBLE);
					holder.editGoods.setVisibility(View.GONE);
					holder.special_new_join.setVisibility(View.VISIBLE);
					holder.new_lines.setVisibility(View.GONE);
					holder.btn_share.setVisibility(View.GONE);
					holder.speical_rl_bottom.setVisibility(View.GONE);
				}
			}
		}
		/*
		 * else if(position<3){ // HashMap<String, List> map; // for (int i = 0;
		 * i < map.size(); i++) { // // }
		 * holder.in_ll_guangguang.setVisibility(View.GONE);
		 * holder.shopcart_common_ll_invalid.setVisibility(View.GONE);
		 * holder.special_delete_cart.setVisibility(View.VISIBLE);
		 * holder.editGoods.setVisibility(View.GONE);
		 * holder.special_new_join.setVisibility(View.GONE);
		 * holder.btn_share.setVisibility(View.GONE);
		 * holder.speical_rl_bottom.setVisibility(View.VISIBLE);
		 * holder.img_choose.setVisibility(View.GONE); }else{
		 * holder.img_choose.setVisibility(View.GONE);
		 * holder.in_ll_guangguang.setVisibility(View.GONE);
		 * if(position==3&&!isAlready){
		 * holder.shopcart_common_ll_invalid.setVisibility(View.VISIBLE);
		 * isAlready=true;} else{
		 * holder.shopcart_common_ll_invalid.setVisibility(View.GONE); }
		 * holder.special_delete_cart.setVisibility(View.VISIBLE);
		 * holder.editGoods.setVisibility(View.GONE);
		 * holder.special_new_join.setVisibility(View.VISIBLE);
		 * holder.btn_share.setVisibility(View.GONE);
		 * holder.speical_rl_bottom.setVisibility(View.GONE); }
		 */
		// final ShopCart shopCart = list.get(position);

		// TODO:NEW
		if (shopCart.getValid() == 1) {
			holder.special_new_join.setClickable(false);
			holder.special_new_join.setTextColor(Color.parseColor("#c5c5c5"));
			holder.special_new_join.setBackgroundResource(R.drawable.shape_corners_public_gray);
		} else {
			holder.special_new_join.setClickable(true);
			holder.special_new_join.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (cartadpterInterface != null) {
						cartadpterInterface.newJoinOnCallBack(position);
					}

				}
			});
		}
		holder.special_delete_cart.setOnClickListener(new OnClickListener() {// 重新加入删除按钮

			@Override
			public void onClick(View arg0) {
				if (cartadpterInterface != null) {
					cartadpterInterface.deleteOnCallBack(position);
				}

			}
		});
		holder.in_btn_to_shop.setOnClickListener(new OnClickListener() {// 内部去逛逛

			@Override
			public void onClick(View arg0) {
				Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
				intent2.putExtra("toYf", "toYf");
				intent2.putExtra("specialCart", "specialCart");
				((Activity) mContext).startActivity(intent2);
				if("1".equals(flag_activity)){
				((Activity) mContext).finish();
				}

			}
		});
		if (shopCart.getValid() == 0) {
			holder.mLayClick.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (cartadpterInterface != null) {
						LogYiFu.e("yumen", shopCart.getP_code());
						cartadpterInterface.onItemClickLintener(position);
					}
				}
			});
		}
		if (mSign == 1) {
			if (TextUtils.isEmpty(shopCart.getP_code())) {
				holder.mealText.setVisibility(View.GONE);
				holder.sizeColor.setVisibility(View.VISIBLE);
			} else {

				holder.mealText.setVisibility(View.VISIBLE);
				holder.sizeColor.setVisibility(View.GONE);
				holder.tv_return_money.setText("共三件商品");
			}

		} else {
			if (shopCart.getShop_list() != null) {
				holder.tv_return_money.setText("共" + shopCart.getShop_list().size() + "件商品");
			}
			if (shopCart.getShop_list() != null) {

				// TODO:
				if (shopCart != null) {
					if (shopCart.getValid() == 1) {
						holder.tv_return_money.setTextColor(0xffc5c5c5);
						holder.tvColor1.setTextColor(0xffc5c5c5);
						holder.tvColor2.setTextColor(0xffc5c5c5);
						holder.tvColor3.setTextColor(0xffc5c5c5);
					} else {
						holder.tv_return_money.setTextColor(0xff222222);
						holder.tvColor1.setTextColor(0xffa5a5a5);
						holder.tvColor2.setTextColor(0xffa5a5a5);
						holder.tvColor3.setTextColor(0xffa5a5a5);
					}
				}
				if (shopCart.getShop_list().size() == 1) {

					StockBean bean = shopCart.getShop_list().get(0);
					if (bean.getColor() != null) {
						holder.tvColor1.setText("商品1:" + bean.getColor());
					}
					holder.mLine2.setVisibility(View.GONE);
					holder.mLine3.setVisibility(View.GONE);
				} else if (shopCart.getShop_list().size() == 2) {
					if (shopCart.getShop_list().get(0).getColor() != null) {
						holder.tvColor1.setText("商品1:" + shopCart.getShop_list().get(0).getColor());
					}
					if (shopCart.getShop_list().get(1).getColor() != null) {
						holder.tvColor2.setText("商品2:" + shopCart.getShop_list().get(1).getColor());
					}
					holder.mLine2.setVisibility(View.VISIBLE);
					holder.mLine3.setVisibility(View.GONE);
				} else {
					if (shopCart.getShop_list().get(0).getColor() != null) {
						holder.tvColor1.setText("商品1:" + shopCart.getShop_list().get(0).getColor());
					}
					if (shopCart.getShop_list().get(1).getColor() != null) {
						holder.tvColor2.setText("商品2:" + shopCart.getShop_list().get(1).getColor());
					}
					if (shopCart.getShop_list().get(2).getColor() != null) {
						holder.tvColor3.setText("商品3:" + shopCart.getShop_list().get(2).getColor());
					}
					holder.mLine2.setVisibility(View.VISIBLE);
					holder.mLine3.setVisibility(View.VISIBLE);
				}
				// TODO:
			}
			if (shopCart.getShop_se_price() != null) {
				holder.total.setText("¥" + shopCart.getShop_se_price());
				holder.tv_shop_price
						.setText("¥" + shopCart.getShop_se_price());
			}
			if (null != shopCart.getShop_price()) {
				holder.tv_shop_se_price
						.setText("¥" +new java.text.DecimalFormat("#0.0").format(shopCart.getShop_price()) );
				holder.tv_shop_se_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			}
		}

		if (editList.contains(shopCart)) {// 编辑状态 //
			holder.btn_share.setBackgroundColor(0xffff3f8b);
			// TODO:
			// if (shopCart.getValid() == 1) {
			// holder.editGoods.setTextColor(0xffc5c5c5);
			// holder.btn_share.setTextColor(0xffc5c5c5);
			//
			// } else {
			// holder.editGoods.setTextColor(0xffffffff);
			// holder.btn_share.setTextColor(0xffffffff);
			//
			// }
			holder.editGoods.setText("| 完成");
			holder.btn_share.setText("删除");

			if (mSign == 1) {
				holder.edit_ly.setVisibility(View.VISIBLE);
				holder.price_ly.setVisibility(View.GONE);
			} else {
				// holder.edit_ly.setVisibility(View.VISIBLE);
				// holder.ordersLine.setVisibility(View.GONE);
			}
		} else {
			if (shopCart.getValid() == 1) {
				holder.btn_share.setBackgroundColor(0xffc5c5c5);
			} else {
				holder.btn_share.setBackgroundColor(0xffff3f8b);
			}
			holder.editGoods.setText("| 编辑");
			holder.btn_share.setText("智能\n分享");

			if (mSign == 1) {
				holder.edit_ly.setVisibility(View.GONE);
				holder.price_ly.setVisibility(View.VISIBLE);
			} else {
				// holder.edit_ly.setVisibility(View.GONE);
				// holder.ordersLine.setVisibility(View.VISIBLE);
			}
		}
		holder.editGoods.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (editList.contains(shopCart)) {
					if (shopCart.getValid() == 1) {
						holder.btn_share.setBackgroundColor(0xffc5c5c5);
					} else {
						holder.btn_share.setBackgroundColor(0xffff3f8b);
					}

					editList.remove(shopCart);
					holder.btn_share.setText("智能\n分享");
					if (shopCart.getValid() == 0) {
						if (mSign == 1) {
							holder.edit_ly.setVisibility(View.GONE);
							holder.price_ly.setVisibility(View.VISIBLE);
						} else {
							// holder.edit_ly.setVisibility(View.GONE);
							// holder.ordersLine.setVisibility(View.VISIBLE);
						}
					} else {
						if (mSign == 1) {
							holder.edit_ly.setVisibility(View.GONE);
							holder.price_ly.setVisibility(View.VISIBLE);
						} else {
							// holder.edit_ly.setVisibility(View.GONE);
							// holder.ordersLine.setVisibility(View.VISIBLE);
						}
					}
					holder.editGoods.setText("| 编辑");

					int num = Integer.parseInt(TextUtils.isEmpty(holder.tv_goods_num.getText().toString()) ? "1"
							: holder.tv_goods_num.getText().toString());
					if (num == 0) {
						ToastUtil.showShortText(mContext, "商品数量不能为0！");
						num = 1;
					}
					int count = shopCart.getShop_num();
					if (num != count) {
						if (mSign == 1) {
							if ("1".equals(shopCart.getP_type())) {
								return;
							}
						}
						cartadpterInterface.addOnCallBack(position, num);
						// holder.goods.setText("X"+num);
					}

				} else {
					holder.btn_share.setBackgroundColor(0xffff3f8b);
					editList.add(shopCart);
					holder.btn_share.setText("删除");
					if (shopCart.getValid() == 0) {
						if (mSign == 1) {
							holder.edit_ly.setVisibility(View.VISIBLE);
							holder.price_ly.setVisibility(View.GONE);
							holder.editGoods.setText("| 完成");
						} else {
							// holder.edit_ly.setVisibility(View.VISIBLE);
							// holder.ordersLine.setVisibility(View.GONE);
							holder.editGoods.setText("| 完成");
						}

					}
					holder.tv_goods_num.setText("" + shopCart.getShop_num());
				}
			}
		});

		// TODO:
		if (mSign == 1) {
			if (shopCart.getValid() == 1) {

				holder.tv_color.setTextColor(0xffc5c5c5);
				holder.tv_size.setTextColor(0xffc5c5c5);
				holder.tv_return_money.setTextColor(0xffc5c5c5);
				holder.tv_item_nprice.setTextColor(0xffc5c5c5);
				holder.tv_discount_price.setTextColor(0xffc5c5c5);
				holder.tv_shop_price.setTextColor(0xffc5c5c5);
				holder.tv_goods_num.setTextColor(0xffc5c5c5);
				holder.goods.setTextColor(0xffc5c5c5);
				holder.tv_goods_name.setTextColor(0xffc5c5c5);
			}

			else {

				holder.tv_color.setTextColor(0xff9a9a9a);
				holder.tv_size.setTextColor(0xff9a9a9a);
				holder.tv_return_money.setTextColor(0xFF222222);
				holder.tv_item_nprice.setTextColor(0xff222222);
				holder.tv_discount_price.setTextColor(0xff9a9a9a);
				holder.tv_shop_price.setTextColor(0xff222222);
				holder.tv_goods_num.setTextColor(0xff9a9a9a);
				holder.goods.setTextColor(0xffa5a5a5);
				holder.tv_goods_name.setTextColor(0xff222222);
			}

			String def_pic = shopCart.getShop_code().substring(1, 4) + "/" + shopCart.getShop_code() + "/"
					+ shopCart.getDef_pic();
			holder.img_goods.setTag(def_pic);
			if (!TextUtils.isEmpty(def_pic)) {
//				SetImageLoader.initImageLoader(null, holder.img_goods, def_pic, "");
				
				PicassoUtils.initImage(mContext, def_pic, holder.img_goods);
			}
			String shop_name = shopCart.getShop_name();
			if (!TextUtils.isEmpty(shop_name)) {
				holder.tv_goods_name.setText(shop_name);
			}
			String color = shopCart.getColor();

			if (!TextUtils.isEmpty(color)) {
				holder.tv_color.setText("颜色：" + color);
			}
			String size = shopCart.getSize();
			if (!TextUtils.isEmpty(size)) {
				holder.tv_size.setText("尺码：" + size);

			}

			if (shopCart.getKickback() != null && shopCart.getKickback() != 0) {
				holder.tv_return_money
						.setText("购买返佣现金" + new java.text.DecimalFormat("#0.0").format(shopCart.getKickback()) + "元");

			}
			String item_price = "" + shopCart.getShop_se_price();
			if (shopCart.getShop_se_price() != null) {
				holder.tv_item_price
						.setText("¥" + new java.text.DecimalFormat("#0.0").format(shopCart.getShop_se_price()));
				holder.tv_discount_price
						.setText("¥" + new java.text.DecimalFormat("#0.0").format(shopCart.getShop_se_price()));
				holder.tv_shop_price
						.setText("¥" + new java.text.DecimalFormat("#0.0").format(shopCart.getShop_se_price()));
			}
			// 原价
			// String item_nprice = "" + shopCart.getShop_price();

			if (null != shopCart.getShop_price()) {
				holder.tv_shop_se_price
						.setText("¥" + new java.text.DecimalFormat("#0.0").format(shopCart.getShop_price()));
				holder.tv_item_nprice
						.setText("¥" + new java.text.DecimalFormat("#0.0").format(shopCart.getShop_price()));
				holder.tv_item_nprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
				String str_number = "" + shopCart.getShop_num();
				if (shopCart.getShop_num() != null) {
					holder.tv_goods_num.setText(str_number);
					holder.goods.setText("X" + str_number);
				}
			}

		} else {
			//

			// TODO:
			if (shopCart.getValid() == 1) {
				holder.tv_goods_num.setTextColor(0xffc5c5c5);
				holder.goods.setTextColor(0xffc5c5c5);
				holder.tv_goods_name.setTextColor(0xffc5c5c5);
			}

			else {
				holder.tv_goods_num.setTextColor(0xff9a9a9a);
				holder.goods.setTextColor(0xffa5a5a5);
				holder.tv_goods_name.setTextColor(0xff222222);
			}

			if (!TextUtils.isEmpty(shopCart.getDef_pic())) {
//				SetImageLoader.initImageLoader(null, holder.img_goods, shopCart.getDef_pic(), "");
				PicassoUtils.initImage(mContext, shopCart.getDef_pic(), holder.img_goods);
				
				
			}
			if (!TextUtils.isEmpty(shopCart.getP_name())) {
				holder.tv_goods_name.setText(shopCart.getP_name());
			}
			String str_number = "" + shopCart.getShop_num();
			if (shopCart.getShop_num() != null) {
				holder.tv_goods_num.setText(str_number);
				holder.goods.setText("X" + str_number);
			}
		}

		// 选择 勾选
		if (mInvalidPosition >= 0 && position < mInvalidPosition) {
			final int pImg = spImg.get(position);
			if (pImg == 1) {
				holder.img_choose.setImageResource(R.drawable.icon_dapeigou_normal);

			} else if (pImg == 2) {
				holder.img_choose.setImageResource(R.drawable.icon_dapeigou_celect);
			}
		}
		if (shopCart.getValid() == 0) {
			holder.img_choose.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					if (cartadpterInterface != null) {
						if (mInvalidPosition > 0 && position < mInvalidPosition) {
							if (spImg.get(position) == 1) {
								holder.img_choose.setImageResource(R.drawable.icon_dapeigou_celect);
							} else {
								holder.img_choose.setImageResource(R.drawable.icon_dapeigou_normal);
							}

							cartadpterInterface.selectImgOnCallBack(position);
						}
					}

				}
			});
		} else {
			holder.img_choose.setImageResource(R.drawable.icon_xuanze_disabled);
		}
		//
		if (shopCart.getValid() == 0) {
			holder.btnAdd.setOnClickListener(new View.OnClickListener() {// 加

				@Override
				public void onClick(View v) {
					if (cartadpterInterface != null) {
						int shop_number = Integer.parseInt(TextUtils.isEmpty(holder.tv_goods_num.getText().toString())
								? "0" : holder.tv_goods_num.getText().toString());
						shop_number++;
						holder.tv_goods_num.setText(shop_number + "");
						// cartadpterInterface.addOnCallBack(position,
						// shop_number);
					}

				}
			});

			holder.btnReduce.setOnClickListener(new View.OnClickListener() {// 减

				@Override
				public void onClick(View v) {

					if (cartadpterInterface != null) {
						int shop_number = Integer.parseInt(TextUtils.isEmpty(holder.tv_goods_num.getText().toString())
								? "0" : holder.tv_goods_num.getText().toString());
						if (shop_number > 1) {
							shop_number--;
							holder.tv_goods_num.setText(shop_number + "");
						}

						// cartadpterInterface.reduceOnCallBack(position,
						// shop_number);
					}
				}
			});
		}
		holder.btn_share.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (editList.contains(shopCart)) {
					if (cartadpterInterface != null) {
						cartadpterInterface.deleteOnCallBack(position);
					}
				} else {
					if (shopCart.getValid() == 0) {
						if (mSign != 1) {
							p_code = shopCart.getP_code();
							// getShareShop(shopCart.getP_code());
							// getPshareShop();
							showMyPopuWindow(shopCart);
						} else {
							// getShopLink(shopCart);
							showMyPopuWindow(shopCart, (FragmentActivity) mContext);
						}
					}
				}
			}
		});

		/**
		 * holder.tv_goods_num .setOnEditorActionListener(new
		 * TextView.OnEditorActionListener() {
		 * 
		 * @Override public boolean onEditorAction(TextView v, int actionId,
		 *           KeyEvent event) {
		 * 
		 *           if (actionId == EditorInfo.IME_ACTION_DONE) { int shop_num
		 *           = Integer.valueOf(holder.tv_goods_num
		 *           .getText().toString().trim());
		 *           cartadpterInterface.reduceOnCallBack(position, shop_num);
		 *           return true; } return false; }
		 * 
		 *           });
		 */

		return view;
	}

	public class Holder {
		public LinearLayout ordersLine, mLine1, mLine2, mLine3;

		TextView tv_item_price, tv_goods_name, tv_color, tv_size,

				tv_item_nprice, tv_return_money, tv_discount_price, total, tvColor1, tvColor2, tvColor3;

		TextView editGoods, goods, mealText, mValid, mDiscount;
		TextView tv_shop_price, tv_shop_se_price;
		Button btnReduce, btnAdd, btn_share;
		ImageView img_goods;
		// img_share;
		ImageView img_choose, img_alpha;

		EditText tv_goods_num;

		View price_ly, edit_ly, sizeColor;
		// TODO:new
		LinearLayout in_ll_guangguang, shopcart_common_ll_invalid,mLayClick;// 内部去逛逛,重新加入提示语,用来条目的点击
		TextView special_delete_cart;// 重新加入删除按钮
		Button special_new_join, in_btn_to_shop;// 重新加入按钮,内部去逛逛
		RelativeLayout speical_rl_bottom;// 底部提示
		View new_lines;
	}

	/*
	 * //特卖分享 private void getPshareShop(final ShopCart sc) { new
	 * SAsyncTask<String, Void, HashMap<String,
	 * Object>>((FragmentActivity)mContext, R.string.wait) {
	 * 
	 * @Override protected HashMap<String, Object> doInBackground(
	 * FragmentActivity context, String... params) throws Exception { // TODO
	 * Auto-generated method stub return ComModel2.getPshopLink(params[0],
	 * context, "true"); }
	 * 
	 * @Override protected boolean isHandleException() { return true; }
	 * 
	 * @Override protected void onPostExecute(FragmentActivity context,
	 * HashMap<String, Object> result, Exception e) { // TODO Auto-generated
	 * method stub super.onPostExecute(context, result, e); if (null == e) { if
	 * (result.get("status").equals("1")) { // MyLogYiFu.e("pic", (String)
	 * result.get("shop_pic")); String[] picList = ((String)
	 * result.get("shop_pic")) .split(","); String link = (String)
	 * result.get("link"); download(null, sc.getShop_code(), result, link); }
	 * else if (result.get("status").equals("1050")) {// 表明 Intent intent = new
	 * Intent(context, NoShareActivity.class); intent.putExtra("isNomal", true);
	 * context.startActivity(intent); // 分享已经超过了
	 * 
	 * } } }
	 * 
	 * }.execute(sc.getShop_code()); }
	 */

	private PublicToastDialog shareWaitDialog = null;

	// 得到商品链接
	public void getShopLink(final ShopCart sc) {
		shareWaitDialog = new PublicToastDialog(mContext, R.style.DialogStyle1,"");

		new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
					throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getShopLink(params[0], context, "true");
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null) {
					if (result.get("status").equals("1")) {
						shareWaitDialog.show();
						LogYiFu.e("pic", result.get("shop_pic"));
						String[] picList = result.get("shop_pic").split(",");
						String link = result.get("link");
						download(null, picList, sc.getShop_code(), result, sc, link);
					} else if (result.get("status").equals("1050")) {// 表明
						Intent intent = new Intent(context, NoShareActivity.class);
						intent.putExtra("isNomal", true);
						context.startActivity(intent); // 分享已经超过了
					}

				}
			}

		}.execute(sc.getShop_code());
	}

	private void download(View v, final String[] picList, final String shop_code,
			final HashMap<String, String> mapInfos, final ShopCart sc, final String link) {
		new SAsyncTask<Void, Void, Void>((FragmentActivity) mContext, v, R.string.wait) {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				File fileDirec = new File(YConstance.savePicPath);
				if (!fileDirec.exists()) {
					fileDirec.mkdir();
				}

				File[] listFiles = new File(YConstance.savePicPath).listFiles();
				if (listFiles.length != 0) {
					LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
					for (File file : listFiles) {
						file.delete();
					}
				}
				// LogYiFu.i("TAG", "piclist=" + picList.length);
				List<String> pics = new ArrayList<String>();
				for (int j = 0; j < picList.length; j++) {
					if (!picList[j].contains("reveal_") && !picList[j].contains("detail_")
							&& !picList[j].contains("real_")) {
						pics.add(picList[j]);
					}
				}
				int j = pics.size() + 1;
				if (pics.size() > 8) {
					j = 9;
				}
				for (int i = 0; i < j; i++) {
					if (i == j - 1) {
						/*
						 * ComModel2.saveQRCode(PaymentSuccessActivity.this,
						 * shop_code);
						 */
						Bitmap bm = QRCreateUtil.createImage(mapInfos.get("QrLink"), 500, 700,
								mapInfos.get("shop_se_price"), mContext);// 得到二维码图片
						QRCreateUtil.saveBitmap(bm, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
						// downloadPic(mapInfos.get("qr_pic"), 9);
						break;
					}
					downloadPic(shop_code.substring(1, 4) + "/" + shop_code + "/" + pics.get(i) + "!450", i);
				}
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result) {
				// TODO Auto-generated method stub
				// showShareDialog();
				LogYiFu.e("TAG", "宝贝内容=" + sc.getShop_name() + ",宝贝链接=" + result);
				ShareUtil.configPlatforms(context);
				UMImage umImage = new UMImage(context, R.drawable.ic_launcher);
				ShareUtil.setShareContent(context, umImage, sc.getShop_name(), link);
				// ShareUtil.share(NewMealShopDetailsActivity.this);
				// showMyPopuWindow(sc, context);
				shareTo(shop_code);
				super.onPostExecute(context, result);
			}
		}.execute();
	}

	/**
	 * 
	 * @param sc
	 * @param context
	 */
	private void showMyPopuWindow(ShopCart sc, FragmentActivity context) {

		myPopupwindow = new MyPopupwindow(context, sc.getKickback(), this, sc, mSign, "ShopCart");
		myPopupwindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

	}

	private void downloadPic(String picPath, int i) {
		try {
			URL url = new URL(YUrl.imgurl + picPath);
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			int contentLength = con.getContentLength();
			// System.out.println("长度 :" + contentLength);
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[8192];
			// 读取到的数据长度
			int len;
			// 输出的文件流 /sdcard/yssj/
			File file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(i)) + ".jpg");
			if (file.exists()) {
				file.delete();
			}
			LogYiFu.e("TAG", "多分享选择下载的图片。。。。");
			OutputStream os = new FileOutputStream(file);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);

			}
			LogYiFu.i("TAG", "下载完毕。。。file=" + file.toString());
			// 完毕，关闭所有链接
			os.close();
			is.close();
		} catch (Exception e) {
			LogYiFu.e("TAG", "下载失败");
			e.printStackTrace();
		}
	}

	private void share(final ShopCart shopCart) {

		new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
					throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getShopLink(params[0], context, "true");
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if (null == e) {
					if (result.get("status").equals("1")) {

						LogYiFu.e("TAG", "宝贝内容=" + shopCart.getShop_name() + ",宝贝链接=" + result);
						ShareUtil.configPlatforms(context);
						UMImage umImage = new UMImage(context, R.drawable.ic_launcher);
						ShareUtil.setShareContent(context, umImage, shopCart.getShop_name(), result.get("link"));
						// ShareUtil.share(NewMealShopDetailsActivity.this);
						myPopupwindow = new MyPopupwindow(context, shopCart.getKickback(), false);
						myPopupwindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

					} else if (result.get("status").equals("1050")) {// 表明
																		// 分享已经超过了
						// ToastUtil.showShortText(context, "您已达到了分享次数上限"); //
						// 4次限制
						Intent intent = new Intent(context, NoShareActivity.class);
						intent.putExtra("isNomal", true);
						context.startActivity(intent); // 分享已经超过了
					}
				}
			}

		}.execute(shopCart.getShop_code());
	}

//	private class MyImageLoadingListener extends SimpleImageLoadingListener {
//		private ImageView iView;
//		private String url;
//
//		public MyImageLoadingListener(ImageView iView, String def_pic) {
//			super();
//			this.iView = iView;
//			this.url = def_pic;
//		}
//
//		@Override
//		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//			super.onLoadingComplete(imageUri, view, loadedImage);
//			if (url.equals(iView.getTag())) {
//				iView.setImageBitmap(loadedImage);
//			}
//		}
//
//		@Override
//		public void onLoadingStarted(String imageUri, View view) {
//			// TODO Auto-generated method stub
//			super.onLoadingStarted(imageUri, view);
//			iView.setImageResource(R.drawable.image_default);
//		}
//	}

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

	/**
	 * 得到分享的商品
	 **/
	// private void getShareShop(final String packageCode) {
	// new SAsyncTask<Void, Void, HashMap<String, Object>>(
	// (FragmentActivity) mContext, R.string.wait) {
	//
	// @Override
	// protected boolean isHandleException() {
	// // TODO Auto-generated method stub
	// return true;
	// }
	//
	// @Override
	// protected HashMap<String, Object> doInBackground(
	// FragmentActivity context, Void... params) throws Exception {
	// // TODO Auto-generated method stub
	// return ComModel2.getSharePShopInfo(context, packageCode, true);
	// }
	//
	// @Override
	// protected void onPostExecute(FragmentActivity context,
	// HashMap<String, Object> result, Exception e) {
	// super.onPostExecute(context, result, e);
	// if (null == e) {
	// if ((Integer) result.get("status") == 1) {
	// // 大图是900 X 900 二维码
	//
	// createSharePic((String) result.get("link"),
	// (String) result.get("def_pic"),
	// (String) result.get("price"),
	// (String) result.get("shop_code"));
	// // download(null, code, result, (String)
	// // result.get("link"));
	// }
	// // submitZeroOrder(v);
	// }
	// }
	//
	// }.execute();
	// }

	private void createSharePic(final String link, final String picPath, final String price, final String shop_code) {
		new SAsyncTask<Void, Void, Void>((FragmentActivity) mContext, R.string.wait) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected Void doInBackground(FragmentActivity context, Void... params) throws Exception {
				// TODO Auto-generated method stub
				Bitmap bmQr = QRCreateUtil.createQrImage(link, 160, 160);// 得到二维码图片
				String pic = shop_code.substring(1, 4) + "/" + shop_code + "/" + picPath;
				Bitmap bmBg = downloadPic(pic);

				Bitmap bmNew = QRCreateUtil.drawNewBitmap1(context, bmBg, bmQr, price, "");

				QRCreateUtil.saveBitmap(bmNew, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
				return super.doInBackground(context, params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if (null == e) {
					/*
					 * File file = new File(YConstance.savePicPath,
					 * MD5Tools.md5(String.valueOf(9)) + ".jpg"); share(file,
					 * v);
					 */
					File file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");
					share(file, null);
				}
			}

		}.execute();
	}

	private Bitmap downloadPic(String picPath) {
		try {
			URL url = new URL(YUrl.imgurl + picPath);
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			int contentLength = con.getContentLength();
			// System.out.println("长度 :" + contentLength);
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[8192];
			// 读取到的数据长度
			int len;
			BitmapDrawable bmpDraw = new BitmapDrawable(is);

			// 完毕，关闭所有链接
			is.close();
			return bmpDraw.getBitmap();
		} catch (Exception e) {
			LogYiFu.e("TAG", "下载失败");

			e.printStackTrace();
			return null;
		}
	}

	public UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);

	private void share(File file, final View v) {

		if (file == null) {
			Toast.makeText(mContext, "您的网络状态不太好哦~~", Toast.LENGTH_SHORT).show();
			return;
		}

		UMImage umImage = new UMImage(mContext, file);
		ShareUtil.configPlatforms(mContext);
		ShareUtil.shareShop(mContext, umImage);

		mController.postShare(mContext, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
				String showText = platform.toString();
				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
				} else {
					/*
					 * showText += "平台分享失败";
					 * Toast.makeText(MealSubmitOrderActivity.this, showText,
					 * Toast.LENGTH_SHORT).show();
					 */
				}
			}
		});

	}

	/**
	 * 分享套餐
	 */
	public void getPshareShop() {
		shareWaitDialog = new PublicToastDialog(mContext, R.style.DialogStyle1,"");

		// isPause = 1;
		new SAsyncTask<String, Void, HashMap<String, Object>>((FragmentActivity) mContext, R.string.wait) {

			@Override
			protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
					throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getPshopLink(p_code, context, "true");
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				// isPause = 0;
				// if (instance == null) {
				// return;
				// }
				if (null == e) {
					if (result.get("status").equals("1")) {
						shareWaitDialog.show();
						tongjifenxiangCount(); // 统计分享次数
						// tongjifenxiang(code);//统计谁分享了

						// MyLogYiFu.e("pic", (String) result.get("shop_pic"));
						String[] picList = ((String) result.get("shop_pic")).split(",");
						String link = (String) result.get("link");
						download(null, p_code, result, link);
					} else if (result.get("status").equals("1050")) {// 表明
						Intent intent = new Intent(context, NoShareActivity.class);
						intent.putExtra("isNomal", true);
						context.startActivity(intent); // 分享已经超过了

					}
				}
			}

		}.execute();
	}

	private void tongjifenxiang(final String shopCode) {

		new SAsyncTask<Integer, Void, String>((FragmentActivity) mContext, R.string.wait) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected String doInBackground(FragmentActivity context, Integer... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.tongjifenxiang(context, shopCode);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, String result, Exception e) {
				super.onPostExecute(context, result, e);

				if (e == null) {
					LogYiFu.e("谁分享了", result + "");
				}

			}

		}.execute();

	}

	private void tongjifenxiangCount() {

		new SAsyncTask<Integer, Void, String>((FragmentActivity) getContext(), R.string.wait) {

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected String doInBackground(FragmentActivity context, Integer... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.tongjifenxiangshu(context);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, String result, Exception e) {
				super.onPostExecute(context, result, e);

				if (e == null) {
					LogYiFu.e("统计用户分享次数", result + "");
				}

			}

		}.execute();
	}

	/** 下载分享的图片 */
	private void download(View v, final String shop_code, final HashMap<String, Object> mapInfos, final String link) {

		shareWaitDialog.show();

		new SAsyncTask<Void, Void, Void>((FragmentActivity) mContext, v, R.string.wait) {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				List<String> shopCodes = (List<String>) mapInfos.get("shopCodes");
				List<HashMap<String, String>> shopPics = (List<HashMap<String, String>>) mapInfos.get("pics");

				File fileDirec = new File(YConstance.savePicPath);
				if (!fileDirec.exists()) {
					fileDirec.mkdir();
				}
				File[] listFiles = new File(YConstance.savePicPath).listFiles();
				if (listFiles.length != 0) {
					LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
					for (File file : listFiles) {
						file.delete();
					}
				}
				// LogYiFu.i("TAG", "piclist=" + picList.length);
				List<String> pics = new ArrayList<String>();
				for (int j = 0; j < shopCodes.size(); j++) {
					String shop_code = shopCodes.get(j);
					HashMap<String, String> map = shopPics.get(j);
					String pic = map.get(shop_code);
					String[] picStrs = pic.split(",");
					for (int i = 0; i < picStrs.length; i++) {
						if (!picStrs[i].contains("reveal_") && !picStrs[i].contains("detail_")
								&& !picStrs[i].contains("real_")) {
							pics.add(shop_code.substring(1, 4) + "/" + shop_code + "/" + picStrs[i]);
						}
					}
				}

				/*
				 * for (int j = 0; j < picList.length; j++) { if
				 * (!picList[j].contains("reveal_") &&
				 * !picList[j].contains("detail_") &&
				 * !picList[j].contains("real_")) { pics.add(picList[j]); } }
				 */
				int j = pics.size() + 1;
				if (pics.size() > 8) {
					j = 9;
				}
				int nP = j>5?4:j-1;
				for (int i = 0; i < j; i++) {
					if (i == nP) {
						/*
						 * ComModel2.saveQRCode(PaymentSuccessActivity.this,
						 * shop_code);
						 */
						// if (isMeal) {
						// Bitmap bm = QRCreateUtil.createZeroImage(link, 500,
						// 700, (String) mapInfos.get("shop_se_price"),
						// mContext);// 得到二维码图片
						// 九宫图二维码新样式
						Bitmap bmQr = QRCreateUtil.createQrImage(link, 250, 250);
						Bitmap bm = QRCreateUtil.drawNewBitmapNine(mContext, bmQr,
								(String) mapInfos.get("shop_se_price"), true);
						QRCreateUtil.saveBitmap(bm, YConstance.savePicPath, MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
						// } else {
						// Bitmap bm = QRCreateUtil.createImage(link, 500,
						// 700,
						// (String) mapInfos.get("shop_se_price"),
						// NewMealShopDetailsActivity.this);// 得到二维码图片
						// QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
						// MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
						// }
						// downloadPic(mapInfos.get("qr_pic"), 9);
						continue;
					}
					int m  = i>4?i-1:i;
					downloadPic(pics.get(m) + "!450", i);
					bmBg = downloadPic(mapInfos.get("four_pic") + "!450");
					LogYiFu.e("热卖分享", mapInfos.get("four_pic") + "!450");
				}
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result) {
				// if (instance == null) {
				// return;
				// }
				if (null != context && null != context.getWindow().getDecorView()) {

					ShareUtil.configPlatforms(context);

					UMImage umImage = new UMImage(context, bmBg);
					// ShareUtil.setShareContent(context, umImage,
					// "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
					ShareUtil.setShareContent(context, umImage, "买了肯定不后悔，数量不多，快来抢购吧~", link);
					myPopupwindow.setUmImage(umImage);
					myPopupwindow.setLink(link);
					// showMyPopuWindow(link, context, umImage);
					// }
					shareTo(shop_code);
					super.onPostExecute(context, result);
				}

			}

		}.execute();
	}

	private void showMyPopuWindow(ShopCart sc) {
		myPopupwindow = new MyPopupwindow((FragmentActivity) mContext, 0, this, sc, mSign, "ShopCart");
		myPopupwindow.setGou(true);
		// if (NewMealShopDetailsActivity.instance != null) {
		myPopupwindow.showAtLocation(((FragmentActivity) mContext).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

	}

	/**
	 * 
	 */
	private void shareTo(String shop_code) {

		shareWaitDialog.dismiss();

		switch (myPopupwindow.getShareId()) {
		case R.id.iv_qq_share:
			if (myPopupwindow.isSecondShare()) {
				myPopupwindow.onceShare(qqShareIntent, "qq空间");
				yunYunTongJi(shop_code, 104, 2);
			}
			break;
		case R.id.iv_wxin_share:
			if (myPopupwindow.isSecondShare()) {
				myPopupwindow.onceShare(wXinShareIntent, "微信");
				yunYunTongJi(shop_code, 1, 2);
			} else {
				myPopupwindow.performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
			}

			break;
		case R.id.iv_wxin_circle_share:
			myPopupwindow.shareToWxin();
			yunYunTongJi(shop_code, 106, 2);
			break;
		default:
			break;
		}
	}

	private void yunYunTongJi(final String shop_code, final int type, final int tab_type) {
		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
					throws Exception {

				return ComModel2.getOperator(context, shop_code, type, tab_type);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				// System.out.println("特卖调用成功");
			}

		}.execute();
	}

	private Bitmap bmBg;

}
