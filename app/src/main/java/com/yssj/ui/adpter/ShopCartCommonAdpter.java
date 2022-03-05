package com.yssj.ui.adpter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.yssj.Constants;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.FootItemView;
import com.yssj.entity.ShopCart;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.ShopCartCommonFragment;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.LimitDoubleClicked;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.sqlite.ShopCartDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/***
 * 正价商品购物车item适配器
 * 
 * @author Administrator
 * 
 */
public class ShopCartCommonAdpter extends ArrayAdapter<ShopCart> {
	// 新
	public static interface Type {
		int TYPE_SHOPCART = 0;// 购物车列表
		int TYPE_LOVE = 1;// 我的喜爱列表
		int TYPE_OTHER = 2;// 其余的小布局
	}

	private int count = 0;// 用来记录是否存在第一条内部去逛逛
	private LinkedList<HashMap<String, Object>> mInfos = new LinkedList<HashMap<String, Object>>();;
	// 以前

	private Context mContext;
	private ArrayList<Integer> spImg = new ArrayList<Integer>();
	private int resource;
	private List<ShopCart> mListValidNew = new ArrayList<ShopCart>();// 有效
	private List<ShopCart> mListInValidNew = new ArrayList<ShopCart>();// 失效
	private List<ShopCart> mListAllNew = new ArrayList<ShopCart>();// 所有的

	// private DisplayImageOptions options;
	// private ImageLoadingListener animateFirstListener = new
	// AnimateFirstDisplayListener();

	private List<ShopCart> editList;

	String mShop_code = "";
	private String TAG = "ShopCartCommonAdpter";
	private String flag_activity = "0";

	// private TextView mEditButton;
	// private boolean mEditFlag;
	public interface ShopCartAdpterInterface {
		// 默认全选，已不能点击
		// void selectOnCallBack(int position);

		// 已取消掉，默认全选，不能选择了
		void selectImgOnCallBack(int position);

		void addOnCallBack(int position, int shop_number, List<ShopCart> list);

		// 取消掉，点击加减时不再调用接口
		// void reduceOnCallBack(int position, int shop_number);
		// 现在没使用，留着待用
		void updatetOnCallBack(int position);

		void deleteOnCallBack(int position);

		void deleteAllInvalid();

		void modifyColorSize(int postion);

		// void newJoinOnCallBack(int position, int mIposition);// 重新加入的回调

		// 被代替
		// void onItemClickLintener(int position);

	}

	public ShopCartAdpterInterface cartadpterInterface;

	public ShopCartCommonAdpter(Context context, int resource, List<ShopCart> listAll, ArrayList<Integer> sp,
			ArrayList<Integer> spImg, int mSign, List<ShopCart> listValid, List<ShopCart> listInValid,
			String flag_activity) {
		super(context, resource);
		this.mListValidNew = listValid;
		this.mListInValidNew = listInValid;
		this.mListAllNew = listAll;
		this.spImg = spImg;
		this.mContext = context;
		this.resource = resource;
		this.flag_activity = flag_activity;
		editList = new ArrayList<ShopCart>();
	}

	public void setCartOncallback(ShopCartCommonFragment f) {
		this.cartadpterInterface = f;
	}

	public void deleteSucess(ShopCart cart) {
		editList.remove(cart);
	}

	public class Holder {
		private LinearLayout item_ll_all;// 整体条目
		private RelativeLayout item_rl_right_all, item_rl_edit_show_all;// 图片右侧全部,编辑时图片右侧全部
		private TextView shopcart_shixiao, tv_goods_name, goods_count, tv_color, tv_size, tv_item_price,item_tv_zero_kickback,
				tv_item_old_price, tv_item_supply;
		// 图片上失效二字,商品名字,数量,颜色,尺寸,价格,原价,供应商,
		private TextView tv_goods_num, tv_color_edit, tv_size_edit, tv_edit_delete;// 编辑时显示的数量,尺寸显示,编辑按钮,删除
		private LinearLayout tv_edit_color_size;
		private TextView item_delete_all_invalid, new_delete_cart;// 清空所有失效,下架删除按钮
		View new_lines_valid, new_lines_invalid;// 有效分割线,无效分割线
		private ImageView img_choose, img_goods, img_goods_alpha;// 选择按钮,商品图片,图片透明度
		private Button in_btn_to_shop;// 去逛逛按钮,
		private ImageView btn_reduce, btn_add;// 商品数量-,商品数量+
		// 其他
		private LinearLayout in_ll_guangguang, item_ll_mylove;
		// 我的喜欢
		FootItemView left;
		FootItemView right;
	}

	@Override
	public int getCount() {
		if (mListValidNew.size() == 0) {// 内部逛逛
			count = 1;// 条目多一个
		} else {
			count = 0;
		}
		return count + 1 + mListAllNew.size() + (mInfos.size() % 2 == 0 ? mInfos.size() / 2 : mInfos.size() / 2 + 1);
	}

	@Override
	public int getItemViewType(int position) {

		if (count == 1 && position == 0) {
			return Type.TYPE_OTHER;
		} else if (position == mListAllNew.size() + count) {// 我的喜爱文字
			return Type.TYPE_OTHER;
		} else if (position > mListAllNew.size() + count) {// 推荐列表
			return Type.TYPE_LOVE;
		} else {// 购物车列表
			return Type.TYPE_SHOPCART;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;
		int itemType = getItemViewType(position);
		if (convertView == null) {
			convertView = createView(itemType);
			holder = buildHolder(convertView, itemType);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (itemType == Type.TYPE_LOVE) {
			int rightShow = (position - mListAllNew.size() - count - 1) * 2;// -count
																			// 是内部逛逛，-1是我的喜爱文字
			holder.left.initView(false, mInfos.get(rightShow), true);

			if (mInfos.size() > rightShow + 1) {
				holder.right.setVisibility(View.VISIBLE);
				holder.right.initView(false, mInfos.get(rightShow + 1), true);
			} else {
				holder.right.setVisibility(View.INVISIBLE);
			}
		} else if (itemType == Type.TYPE_SHOPCART) {
			setClick(holder, position - count);
			bindViewData(mListAllNew.get(position - count), holder, position);

		} else if (itemType == Type.TYPE_OTHER) {
			otherSetClick(holder);
			if (position == 0 && mListValidNew.size() == 0) {
				holder.in_ll_guangguang.setVisibility(View.VISIBLE);
				holder.item_ll_mylove.setVisibility(View.GONE);
			} else {
				holder.in_ll_guangguang.setVisibility(View.GONE);
				if (mInfos.size() == 0) {
					holder.item_ll_mylove.setVisibility(View.GONE);
				} else {
					holder.item_ll_mylove.setVisibility(View.VISIBLE);
				}
			}
		}

		return convertView;
	}

	private void otherSetClick(Holder holder) {
		// 内部去逛逛
		holder.in_btn_to_shop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonUtils.finishActivity(MainMenuActivity.instances);

				Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
				intent2.putExtra("toYf", "toYf");
				((Activity) mContext).startActivity(intent2);
				if ("1".equals(flag_activity)) {
					((Activity) mContext).finish();
				}
			}
		});
	}

	private void bindViewData(ShopCart shopCart, Holder holder, int position) {
		if (spImg.size() > 0 && position < mListValidNew.size() + count && position >= count) {
			final int pImg = spImg.get(position - count);
			if (pImg == 1) {
				holder.img_choose.setImageResource(R.drawable.icon_dapeigou_normal);

			} else if (pImg == 2) {
				holder.img_choose.setImageResource(R.drawable.icon_dapeigou_celect);
			}
		}
		// 设置上下架的颜色
		// int a = 0;
		// if (mListValidNew.size() == 0) {
		// a = 1;
		// }
		if (position >= mListValidNew.size() + count) {// 下架部分
			holder.img_goods_alpha.setVisibility(View.VISIBLE);
			holder.shopcart_shixiao.setVisibility(View.VISIBLE);
			holder.tv_goods_name.setTextColor(0xffc5c5c5);
			holder.tv_color.setTextColor(0xffc5c5c5);
			holder.tv_size.setTextColor(0xffc5c5c5);
			holder.tv_item_price.setTextColor(0xffc5c5c5);
			holder.item_tv_zero_kickback.setTextColor(0xffc5c5c5);
			holder.tv_item_old_price.setTextColor(0xffc5c5c5);
			holder.img_choose.setClickable(false);
			holder.img_choose.setVisibility(View.GONE);
			holder.goods_count.setTextColor(0xffc5c5c5);
			holder.new_lines_valid.setVisibility(View.GONE);
			holder.new_lines_invalid.setVisibility(View.VISIBLE);
			holder.new_delete_cart.setVisibility(View.VISIBLE);// 下架时的删除按钮
			holder.goods_count.setVisibility(View.GONE);
			holder.tv_item_supply.setVisibility(View.GONE);
			holder.tv_edit_color_size.setVisibility(View.GONE);// 颜色尺寸编辑按钮
		} else if (position >= count) {// 正常部分
			holder.img_goods_alpha.setVisibility(View.GONE);
			holder.shopcart_shixiao.setVisibility(View.GONE);
			holder.tv_goods_name.setTextColor(0xff3e3e3e);
			holder.tv_color.setTextColor(0xffc5c5c5);
			holder.tv_size.setTextColor(0xffc5c5c5);
			holder.tv_item_price.setTextColor(0xff3e3e3e);
			holder.item_tv_zero_kickback.setTextColor(0xff3e3e3e);
			holder.tv_item_old_price.setTextColor(0xffa8a8a8);
			holder.img_choose.setClickable(true);
			holder.img_choose.setVisibility(View.VISIBLE);
			holder.goods_count.setTextColor(0xff3e3e3e);
			holder.new_lines_valid.setVisibility(View.VISIBLE);
			holder.new_lines_invalid.setVisibility(View.GONE);
			holder.new_delete_cart.setVisibility(View.GONE);
			holder.goods_count.setVisibility(View.VISIBLE);
			holder.tv_item_supply.setVisibility(View.VISIBLE);
			holder.tv_edit_color_size.setVisibility(View.VISIBLE);
		}
		// if (mListValidNew.size() == 0 && position == 0) {// 展示内部去逛逛页面
		// holder.in_ll_guangguang.setVisibility(View.VISIBLE);
		// } else {
		// holder.in_ll_guangguang.setVisibility(View.GONE);
		// }
		if (mListInValidNew.size() > 0 && position == mListAllNew.size() - 1 + count) {// 清空所有下架商品按钮的展示
			holder.item_delete_all_invalid.setVisibility(View.VISIBLE);
		} else {
			holder.item_delete_all_invalid.setVisibility(View.GONE);
		}

		if (ShopCartCommonFragment.mEditFlag) {
			if (count <= position && position < mListValidNew.size() + count) {// 有效部分可编辑
				holder.item_rl_right_all.setVisibility(View.GONE);
				holder.item_rl_edit_show_all.setVisibility(View.VISIBLE);
				if (spImg.get(position - count) == 1) {// 设置删除按钮颜色
					holder.tv_edit_delete.setBackgroundColor(0xffc5c5c5);
				} else {
					holder.tv_edit_delete.setBackgroundColor(0xfffb3b3b);
				}
			} else {
				holder.item_rl_right_all.setVisibility(View.VISIBLE);
				holder.item_rl_edit_show_all.setVisibility(View.GONE);
			}
		} else {
			holder.item_rl_right_all.setVisibility(View.VISIBLE);
			holder.item_rl_edit_show_all.setVisibility(View.GONE);
		}
		String def_pic = YUrl.imgurl + shopCart.getShop_code().substring(1, 4) + "/" + shopCart.getShop_code() + "/"
				+ shopCart.getDef_pic();
		// img_goods.setTag(def_pic);
		// options = new
		// DisplayImageOptions.Builder().showImageOnLoading(R.drawable.image_default)
		// .showImageForEmptyUri(R.drawable.ic_empty).cacheInMemory(false).bitmapConfig(Bitmap.Config.RGB_565)
		// .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).considerExifParams(true).build();
		if (!TextUtils.isEmpty(def_pic)) {
			// YJApplication.getLoader();
			// ImageLoader.getInstance().displayImage(def_pic, holder.img_goods,
			// options, animateFirstListener);
			PicassoUtils.initImage(mContext, def_pic, holder.img_goods);
		}

		String shop_name = shopCart.getShop_name();
		if (!TextUtils.isEmpty(shop_name)) {
			holder.tv_goods_name.setText(shop_name);
		}
		String supp_label = shopCart.getSupp_label();
		if (!TextUtils.isEmpty(supp_label)) {
			holder.tv_item_supply.setText(supp_label+"制造商");
		}
		String color = shopCart.getColor();

		if (!TextUtils.isEmpty(color)) {
			holder.tv_color.setText("颜色：" + color);
			holder.tv_color_edit.setText("颜色：" + color);
		}
		String size = shopCart.getSize();
		if (!TextUtils.isEmpty(size)) {
			holder.tv_size.setText("尺码：" + size);
			holder.tv_size_edit.setText("尺码：" + size);
		}
		// String item_price = "" + shopCart.getShop_se_price();
		if (shopCart.getShop_se_price() != null) {
			holder.tv_item_price.setText("¥" + new java.text.DecimalFormat("#0.0").format(shopCart.getShop_se_price()*0.9));
			holder.item_tv_zero_kickback.setText("返"+new java.text.DecimalFormat("#0.0").format(shopCart.getShop_se_price()*0.9)+"元");
		}

		if (null != shopCart.getShop_price()) {
			holder.tv_item_old_price
					.setText("¥" + new java.text.DecimalFormat("#0.0").format(shopCart.getShop_price()));
			holder.tv_item_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		}
		String str_number = "" + shopCart.getShop_num();
		if (shopCart.getShop_num() != null) {
			holder.goods_count.setText("X" + str_number);
			holder.tv_goods_num.setText("" + str_number);
		}
		int count = shopCart.getShop_num();
		if (count == 1) {
			holder.btn_reduce.setImageResource(R.drawable.icon_jian_disable);
			holder.btn_add.setImageResource(R.drawable.icon_jia);
		} else if (count == 2) {
			holder.btn_reduce.setImageResource(R.drawable.icon_jian);
			holder.btn_add.setImageResource(R.drawable.icon_jia_disable);
		}
		// 编辑时 tv_goods_num,tv_color_edit, tv_size_edit,
		// if (shopCart.getValid() == 1 || (shopCart.getIs_del() != null ?
		// shopCart.getIs_del() == 1 : false)) {// 商品下架的展示
		// holder.img_goods_alpha.setVisibility(View.VISIBLE);
		// holder.shopcart_shixiao.setVisibility(View.VISIBLE);
		// holder.tv_goods_name.setTextColor(0xffc5c5c5);
		// holder.tv_color.setTextColor(0xffc5c5c5);
		// holder.tv_size.setTextColor(0xffc5c5c5);
		// holder.tv_item_price.setTextColor(0xffc5c5c5);
		// holder.tv_item_old_price.setTextColor(0xffc5c5c5);
		// holder.img_choose.setClickable(false);
		// holder.goods_count.setTextColor(0xffc5c5c5);
		//
		// } else {// 正常的展示
		// holder.img_goods_alpha.setVisibility(View.GONE);
		// holder.shopcart_shixiao.setVisibility(View.GONE);
		// holder.tv_goods_name.setTextColor(0xff3e3e3e);
		// holder.tv_color.setTextColor(0xffc5c5c5);
		// holder.tv_size.setTextColor(0xffc5c5c5);
		// holder.tv_item_price.setTextColor(0xff3e3e3e);
		// holder.tv_item_old_price.setTextColor(0xffa8a8a8);
		// holder.img_choose.setClickable(true);
		// holder.goods_count.setTextColor(0xff3e3e3e);
		// }

	}

	private Holder buildHolder(View view, int itemType) {
		Holder holder = new Holder();
		switch (itemType) {
		case Type.TYPE_SHOPCART:
			// 整体条目
			holder.item_ll_all = (LinearLayout) view.findViewById(R.id.item_ll_all);// 整体条目
			holder.img_choose = (ImageView) view.findViewById(R.id.img_choose);
			holder.img_goods = (ImageView) view.findViewById(R.id.img_goods);
			holder.img_goods_alpha = (ImageView) view.findViewById(R.id.img_goods_alpha);
			holder.shopcart_shixiao = (TextView) view.findViewById(R.id.shopcart_shixiao);
			// 正常时的选项
			holder.item_rl_right_all = (RelativeLayout) view.findViewById(R.id.item_rl_right_all);
			holder.tv_goods_name = (TextView) view.findViewById(R.id.tv_goods_name);
			holder.goods_count = (TextView) view.findViewById(R.id.goods_count);
			holder.tv_color = (TextView) view.findViewById(R.id.tv_color);
			holder.tv_size = (TextView) view.findViewById(R.id.tv_size);
			holder.tv_item_price = (TextView) view.findViewById(R.id.tv_item_price);
			holder.item_tv_zero_kickback= (TextView) view.findViewById(R.id.item_tv_zero_kickback);
			holder.tv_item_old_price = (TextView) view.findViewById(R.id.tv_item_old_price);
			holder.tv_item_supply = (TextView) view.findViewById(R.id.tv_item_supply);
			// 编辑时的选项
			holder.item_rl_edit_show_all = (RelativeLayout) view.findViewById(R.id.item_rl_edit_show_all);
			holder.btn_reduce = (ImageView) view.findViewById(R.id.btn_reduce);
			holder.tv_goods_num = (TextView) view.findViewById(R.id.tv_goods_num);// 编辑时的数量
			holder.btn_add = (ImageView) view.findViewById(R.id.btn_add);
			holder.tv_color_edit = (TextView) view.findViewById(R.id.tv_color_edit);
			holder.tv_size_edit = (TextView) view.findViewById(R.id.tv_size_edit);
			holder.tv_edit_color_size = (LinearLayout) view.findViewById(R.id.tv_edit_color_size);// 编辑按钮
			holder.tv_edit_delete = (TextView) view.findViewById(R.id.tv_edit_delete);// 编辑时的删除按钮
			// 下架删除按钮
			holder.new_delete_cart = (TextView) view.findViewById(R.id.new_delete_cart);
			// 底部
			holder.item_delete_all_invalid = (TextView) view.findViewById(R.id.item_delete_all_invalid);// 清空所有失效
			holder.new_lines_valid = (View) view.findViewById(R.id.new_lines_valid);// 有效分割线
			holder.new_lines_invalid = (View) view.findViewById(R.id.new_lines_invalid);// 无效分割线
			break;
		case Type.TYPE_LOVE:
			holder.left = (FootItemView) view.findViewById(R.id.left);
			holder.right = (FootItemView) view.findViewById(R.id.right);
			break;
		case Type.TYPE_OTHER:
			holder.in_ll_guangguang = (LinearLayout) view.findViewById(R.id.in_ll_guangguangs);// 内部逛逛
			holder.item_ll_mylove = (LinearLayout) view.findViewById(R.id.item_ll_mylove);
			holder.in_btn_to_shop = (Button) view.findViewById(R.id.in_btn_to_shop);// 去逛逛按钮
		default:
			break;
		}

		return holder;
	}

	private void setClick(final Holder holder, final int position) {
		holder.item_ll_all.setOnClickListener(new OnClickListener() {//

			@Override
			public void onClick(View v) {
				if (position >= mListValidNew.size()) {// 下架
					return;
				} else {// 上架
					Intent intent = new Intent(mContext, ShopDetailsActivity.class);
					intent.putExtra("code", mListValidNew.get(position).getShop_code());
					((Activity) mContext).startActivityForResult(intent, 100);
					((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
				}
			}
		});
		// 左边选择按钮
		holder.img_choose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cartadpterInterface != null) {
					if (spImg.get(position) == 1) {
						holder.img_choose.setImageResource(R.drawable.icon_dapeigou_celect);
						holder.tv_edit_delete.setBackgroundColor(0xfffb3b3b);
						holder.tv_edit_delete.setClickable(true);
					} else {
						holder.img_choose.setImageResource(R.drawable.icon_dapeigou_normal);
						holder.tv_edit_delete.setBackgroundColor(0xffc5c5c5);
						holder.tv_edit_delete.setClickable(false);
					}

					cartadpterInterface.selectImgOnCallBack(position);
				}
			}
		});
		// 增加数量
		holder.btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ShopCartDao dao = new ShopCartDao(mContext);
				// int count2 = dao.queryCartCommonCount(mContext);
				// if (count2 >= 20) {
				// ToastUtil.showShortText(mContext, "购物车最多允许加入20件有效商品");
				// return;
				// }
				ShopCart shopCart = mListAllNew.get(position);
				if (shopCart.getShop_num() >= 2) {
					ToastUtil.showShortText(mContext, "抱歉，数量有限，最多只能购买2件噢！");
					return;
				}
				if (cartadpterInterface != null) {
					int shop_number = Integer.parseInt(TextUtils.isEmpty(holder.tv_goods_num.getText().toString()) ? "0"
							: holder.tv_goods_num.getText().toString());
					if (shop_number >= 2) {
						ToastUtil.showShortText(mContext, "抱歉，数量有限，最多只能购买2件噢！");
						return;
					} else {
						shop_number++;
					}
					// holder.tv_goods_num.setText(shop_number + "");
					// holder.goods_count.setText("x"+shop_number);
					// mListAllNew.get(position).setShop_num(shop_number);
					// dao.modify("" +
					// mListAllNew.get(position).getStock_type_id(),
					// shop_number);
					cartadpterInterface.addOnCallBack(position, shop_number, mListAllNew);
				}

			}
		});
		// 减少数量
		holder.btn_reduce.setOnClickListener(new View.OnClickListener() {// 减

			@Override
			public void onClick(View v) {
				if (cartadpterInterface != null) {
					ShopCartDao dao = new ShopCartDao(mContext);
					ShopCart shopCart = mListAllNew.get(position);
					int shop_number = Integer.parseInt(TextUtils.isEmpty(holder.tv_goods_num.getText().toString()) ? "1"
							: holder.tv_goods_num.getText().toString());
					if (shop_number > 1) {
						shop_number--;
						// holder.tv_goods_num.setText(shop_number + "");
						// holder.goods_count.setText("x"+shop_number);
					} else {
						return;
					}
					// mListAllNew.get(position).setShop_num(shop_number);
					// dao.modify("" +
					// mListAllNew.get(position).getStock_type_id(),
					// shop_number);
					cartadpterInterface.addOnCallBack(position, shop_number, mListAllNew);
					// holder.goods.setText("X"+num);
				}
			}
		});
		// 编辑时的删除
		holder.tv_edit_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cartadpterInterface != null) {
					if (LimitDoubleClicked.isFastDoubleClick()) {
						return;
					}
					cartadpterInterface.deleteOnCallBack(position);
				}
			}
		});
		// 下架时的删除
		holder.new_delete_cart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cartadpterInterface != null) {
					if (LimitDoubleClicked.isFastDoubleClick()) {
						return;
					}
					cartadpterInterface.deleteOnCallBack(position);
				}
			}
		});
		// 清空失效商品
		holder.item_delete_all_invalid.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cartadpterInterface != null) {
					if (LimitDoubleClicked.isFastDoubleClick()) {
						return;
					}
					cartadpterInterface.deleteAllInvalid();
					;
				}
			}
		});
		// 编辑颜色 尺寸
		holder.tv_edit_color_size.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cartadpterInterface != null) {
					if (LimitDoubleClicked.isFastDoubleClick()) {
						return;
					}
					cartadpterInterface.modifyColorSize(position);
					;
				}
			}
		});
	}

	private View createView(int itemType) {
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		View view;
		switch (itemType) {
		case Type.TYPE_LOVE:
			view = mInflater.inflate(R.layout.item_my_love, null);
			return view;
		case Type.TYPE_OTHER:
			view = mInflater.inflate(R.layout.item_shopcart_guangguang, null);
			return view;
		default:
			return mInflater.inflate(R.layout.item_list_shopcart, null);
		}

	}

	public void addItemTop(List<HashMap<String, Object>> datas) {
		mInfos.clear();
		for (HashMap<String, Object> info : datas) {
			// mInfos.addFirst(info);
			mInfos.addLast(info);
		}

		this.notifyDataSetChanged();
	}

	public void addItemLast(List<HashMap<String, Object>> datas) {

		// for (HashMap<String, Object> data : datas) {
		// mInfos.addFirst(data);
		// }
		mInfos.addAll(datas);
		this.notifyDataSetChanged();
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
				// System.out.println("调用成功");
			}

		}.execute();
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

	public UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);

}
