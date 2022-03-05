package com.yssj.ui.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ShopCart;
import com.yssj.entity.StockType;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.shopdetails.ShopCartDialog;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.activity.shopdetails.SubmitMultiShopActivty;
import com.yssj.ui.adpter.ShopCartAdpter;
import com.yssj.ui.adpter.ShopCartAdpter.ShopCartAdpterInterface;
import com.yssj.ui.base.BasePager;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.YCache;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopCartCommonPager extends BasePager implements OnClickListener,
		ShopCartAdpterInterface {

	private LinearLayout lay_cartpay;
	private TextView tv_allchoose, tv_price, tv_no_freight, tv_pay;
	private boolean mFLagCommom = false;
	private static List<ShopCart> mListCommon2;
	private int height;
	public List<ShopCart> listShopCarts;
	private ArrayList<Integer> sp;
	private ArrayList<Integer> spImg;

	private ShopCartAdpter adpter;
	private PullToRefreshListView listView;
	private int statusBarHeight;

	private TextView tv_edit_cart;

	private double cPrice;// 结算商品的总价格

	private boolean refeshFlag = true, allFlag = false;

	private LinearLayout layout_nodata_shopcar;
	private Button btn_to_shop;

	private LinearLayout llBottom;

	public ShopCartCommonPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		mListCommon2 = new ArrayList<ShopCart>();
		sp = new ArrayList<Integer>();
		spImg = new ArrayList<Integer>();
		view = View.inflate(context, R.layout.activity_shopcart_common, null);
		listView = (PullToRefreshListView) view
				.findViewById(R.id.listview_shopcart);
		// flush();
		lay_cartpay = (LinearLayout) view.findViewById(R.id.lay_cartpay);
		// lay_cartremove = (LinearLayout) v.findViewById(R.id.lay_cartremove);
		tv_price = (TextView) view.findViewById(R.id.tv_price);
		tv_no_freight = (TextView) view.findViewById(R.id.tv_no_freight);
		tv_pay = (TextView) view.findViewById(R.id.tv_pay);
		tv_pay.setOnClickListener(this);
		// tv_remove = (TextView) v.findViewById(R.id.tv_remove);
		// tv_remove.setOnClickListener(this);
		// tv_delete_all = (TextView) v.findViewById(R.id.tv_delete_all);
		// tv_delete_all.setOnClickListener(this);
		tv_allchoose = (TextView) view.findViewById(R.id.tv_allchoose);
		tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(context
				.getResources().getDrawable(R.drawable.tvchooseno_normal),
				null, null, null);
		tv_allchoose.setOnClickListener(this);

		// int resource = R.layout.listview_shop_cart;
		listShopCarts = new ArrayList<ShopCart>();
		// queryListShopCart(1);
		// adpter = new ShopCartAdpter(context.getApplicationContext(),
		// resource,
		// listShopCarts, sp, spImg);
		// adpter.setCartOncallback(this);
		//
		// listView.setAdapter(adpter);
		// listView.setRefreshing();
		layout_nodata_shopcar = (LinearLayout) view
				.findViewById(R.id.layout_nodata_shopcar);
		llBottom = (LinearLayout) view.findViewById(R.id.lay_bottom);
		// if (listShopCarts!=null&&listShopCarts.size() > 0) {
		// llBottom.setVisibility(View.VISIBLE);
		// }else{
		// layout_nodata_shopcar.setVisibility(View.VISIBLE);
		// listView.setVisibility(View.GONE);
		// }

		btn_to_shop = (Button) view.findViewById(R.id.btn_to_shop);
		btn_to_shop.setOnClickListener(this);
		view.findViewById(R.id.img_back).setOnClickListener(this);

		return view;
	}

	/**
	 * 编辑全部
	 */
	private void editorAllGoods(TextView tv_edit_cart) {
		String strTitle = tv_edit_cart.getText().toString();
		if (strTitle.equals("编辑全部")) {
			tv_edit_cart.setText("完成");
			lay_cartpay.setVisibility(View.GONE);
			// lay_cartremove.setVisibility(View.VISIBLE);
			refeshFlag = false;
			for (int i = 0; i < sp.size(); i++) {
				sp.set(i, 3);
			}

		} else {
			tv_edit_cart.setText("编辑全部");
			lay_cartpay.setVisibility(View.VISIBLE);
			// lay_cartremove.setVisibility(View.GONE);
			refeshFlag = true;
			for (int i = 0; i < sp.size(); i++) {
				sp.set(i, 1);
			}
		}
		adpter.notifyDataSetChanged();

	}

	private int curPage = 1;
	private int pageSize = 10;
	private AlertDialog dialog;

	/****
	 * 查询购物车列表信息
	 */
	public void queryListShopCart(final int type) {
		new SAsyncTask<String, Void, List<ShopCart>>(
				(FragmentActivity) context, R.string.wait) {
			@Override
			protected List<ShopCart> doInBackground(FragmentActivity context,
					String... params) throws Exception {
				List<ShopCart> list = ComModel.queryShopCarts(context,
						YCache.getCacheToken(context), params[0], params[1]);
				return list;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					List<ShopCart> list, Exception e) {

				if (e != null) {// 查询异常
					LogYiFu.e("异常 -----", "异常");
					layout_nodata_shopcar.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
					llBottom.setVisibility(View.GONE);
					listView.onRefreshComplete();
				} else {// 查询商品详情成功，刷新界面

					if (list != null) {
						mFLagCommom = true;
						mListCommon2.clear();
						mListCommon2.addAll(list);
						if (type == 1) { // 下拉刷新
							if (list != null && list.size() == 0) {
								LogYiFu.e("TAG", "没有购物车。。");
								layout_nodata_shopcar
										.setVisibility(View.VISIBLE);
								listView.setVisibility(View.GONE);
								llBottom.setVisibility(View.GONE);
							} else {
								layout_nodata_shopcar.setVisibility(View.GONE);
								listView.setVisibility(View.VISIBLE);
								llBottom.setVisibility(View.VISIBLE);
							}
							listShopCarts.clear();
							listShopCarts.addAll(list);
							sp.clear();
							spImg.clear();
						} else if (type == 2 || type == 0) { // 上拉加载
							listShopCarts.addAll(list);
						}
						for (int i = 0; i < list.size(); i++) {
							sp.add(1);
						}
						for (int i = 0; i < list.size(); i++) {
							spImg.add(1);
						}

					}
				}
				if (type == 1 || type == 2) {
					listView.onRefreshComplete();
				}
				// setPrice();
				tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
						R.drawable.tvchooseno_normal, 0, 0, 0);
				tv_price.setText("合计 " + 0.0 + "元");
				tv_pay.setText("去结算(" + 0 + ")");
				tv_no_freight.setText("为您节省¥" + 0.00);
				allFlag = false;
				adpter.notifyDataSetChanged();
			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute(String.valueOf(curPage), String.valueOf(pageSize));
	}

	/****
	 * 单个删除购物车信息
	 */
	private void deleteShopCart(final int position) {
		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) context,
				null, R.string.wait) {
			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				ReturnInfo r = ComModel.deleteShopCart(context,
						YCache.getCacheToken(context), params[0], params[1]);
				return r;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo r, Exception e) {

				if (e != null) {
					LogYiFu.e("异常 -----", "异常");
				} else {
					adpter.deleteSucess(listShopCarts.get(position));
					listShopCarts.remove(position);
					sp.remove(position);
					spImg.remove(position);
					adpter.notifyDataSetChanged();
					setPrice();
					Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
					if (listShopCarts.size() == 0) {
						layout_nodata_shopcar.setVisibility(View.VISIBLE);
						listView.setVisibility(View.GONE);
						llBottom.setVisibility(View.GONE);
					}
				}
			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute(listShopCarts.get(position).getId() + "",
				listShopCarts.get(position).getP_code());

	}

	/*
	 * @Override public void onPause() { // TODO Auto-generated method stub
	 * super.onPause(); MobclickAgent.onPageEnd("ShopCartActivity");
	 * MobclickAgent.onPause(context); }
	 */
	// TODO:
	public void flush() {
		// MobclickAgent.onPageStart("ShopCartActivity");
		// MobclickAgent.onResume(context);

		// UserInfo userInfo = YCache.getCacheUserSafe(ShopCartActivity.this);
		curPage = 1;
		// queryListShopCart(1);
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				if (refeshFlag) {
					curPage = 1;
					// MyLogYiFu.e("下拉刷新curpage == ", ""+ curPage);
					queryListShopCart(1);
				} else {
					Toast.makeText(context, "编辑状态下不能刷新", Toast.LENGTH_SHORT)
							.show();
				}
				listView.onRefreshComplete();
				setAllSelect(false);
				allFlag = false;

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				if (refeshFlag) {
					curPage++;
					// MyLogYiFu.e("上拉分页curpage == ", ""+ curPage);
					queryListShopCart(2);
				} else {
					Toast.makeText(context, "编辑状态下不能刷新", Toast.LENGTH_SHORT)
							.show();
				}
				listView.onRefreshComplete();
				setAllSelect(false);
				allFlag = false;
			}

		});

		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {

				LogYiFu.e("End of List! == ", "End of List!");
			}
		});

		// listView.setOnItemClickListener(this);
	}

	/****
	 * 批量删除购物车信息
	 */
	private void deleteShopCartList() {
		if (listShopCarts != null && listShopCarts.size() > 0) {
			new SAsyncTask<String, Void, ReturnInfo>(
					(FragmentActivity) context, null, R.string.wait) {
				@Override
				protected ReturnInfo doInBackground(FragmentActivity context,
						String... params) throws Exception {
					String ids = "";
					StringBuffer buffer = new StringBuffer();
					int length = spImg.size() - 1;
					for (int i = 0; i < length + 1; i++) {
						int p = spImg.get(i);

						if (p == 2) {
							String id = listShopCarts.get(i).getId() + "";
							buffer.append(id + ",");
						}
						ids = buffer.toString();
						ids = ids.substring(0, ids.length() - 1);

					}
					ReturnInfo r = ComModel.deleteShopCartList(context,
							YCache.getCacheToken(context), ids);
					return r;
				}

				@Override
				protected void onPostExecute(FragmentActivity context,
						ReturnInfo r, Exception e) {

					if (e != null) {
						LogYiFu.e("异常 -----", "异常");
					} else {
						for (int i = spImg.size() - 1; i >= 0; i--) {
							if (spImg.get(i) == 2) {
								listShopCarts.remove(i);
								spImg.remove(i);
								sp.remove(i);

							}
						}

						adpter.notifyDataSetChanged();
						// setPrice();
						Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT)
								.show();
					}
				};

				@Override
				protected boolean isHandleException() {
					return true;
				};
			}.execute();
		}

	}

	/***
	 * 修改购物车信息
	 */
	private void updateShopCart(final int position, final int shop_number) {

		final ShopCart cart = listShopCarts.get(position);
		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) context,
				null, R.string.wait) {
			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				ReturnInfo r = ComModel.updateShopCart(context,
						YCache.getCacheToken(context), "" + shop_number,
						cart.getId() + "", cart.getStock_type_id() + "",
						cart.getP_code());
				return r;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo r, Exception e) {

				if (e != null) {
					LogYiFu.e("异常 -----", "异常");
				} else {
					listShopCarts.get(position).setShop_num(shop_number);
					setPrice();
					adpter.notifyDataSetChanged();
				}
			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute();

	}

	/***
	 * 查找商品属性
	 */
	private void queryShopQueryAttr(final int position) {
		if (listShopCarts != null && listShopCarts.size() > 0) {
			ShopCart cart = listShopCarts.get(position);
			new SAsyncTask<String, Void, List<StockType>>(
					(FragmentActivity) context, null, R.string.wait) {
				@Override
				protected List<StockType> doInBackground(
						FragmentActivity context, String... params)
						throws Exception {

					List<StockType> list = ComModel.queryShop_Stokect(context,
							params[0]);
					return list;
				}

				@Override
				protected void onPostExecute(FragmentActivity context,
						List<StockType> list, Exception e) {

					if (e != null) {// 查询异常
						Toast.makeText(context, "连接超时，请重试", Toast.LENGTH_LONG)
								.show();

					} else {// 查询商品详情成功，刷新界面
						if (list != null && list.size() > 0) {
							showPopWindow(position, list);
						}
					}

				};

				@Override
				protected boolean isHandleException() {
					return true;
				};
			}.execute(cart.getShop_code());

		}

	}

	/****
	 * 弹出底部对话框
	 * 
	 * @param i
	 */
	private void showPopWindow(final int position, List<StockType> list) {
		if (listShopCarts != null && listShopCarts.size() > 0) {
			final ShopCartDialog dlg = new ShopCartDialog(context,
					R.style.DialogStyle, height, position, listShopCarts, list);
			Window window = dlg.getWindow();
			window.setGravity(Gravity.BOTTOM);
			window.setWindowAnimations(R.style.dlg_down_to_top);
			dlg.show();

			dlg.cartDialogOncallBack = new ShopCartDialog.ShopCartDialogOncallBack() {

				@Override
				public void updateOncallBack(double shop_se_price,
						String color, String size, int shop_num,
						String def_pic, String stock_type_id, View v) {
					dlg.dismiss();
					updateShopCartType(shop_se_price + "", color, size,
							shop_num, def_pic, stock_type_id, position, v);

				}
			};

		}

	}

	/**
	 * 修改购物车的商品数量,属性，尺码等
	 */
	private void updateShopCartType(final String shop_se_price,
			final String color, final String size, final int shop_num,
			String def_pic, String stock_type_id, int p, final View v) {

		final ShopCart cart = listShopCarts.get(p);
		if (cart == null)
			return;
		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) context,
				null, R.string.wait) {
			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				ReturnInfo r = ComModel.updateShopCartType(context,
						YCache.getCacheToken(context), params[0], params[1],
						params[2], params[3], params[4], params[5], params[6]);
				return r;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo r, Exception e) {

				if (e != null) {
					LogYiFu.e("异常 -----", "异常" + e);

				} else {// 修改成功
					cart.setShop_num(shop_num);
					cart.setShop_se_price(Double.parseDouble(shop_se_price));
					cart.setShop_price(cart.getShop_price());
					cart.setColor(color);
					cart.setSize(size);
					adpter.notifyDataSetChanged();
					setPrice();

				}
			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute(cart.getId() + "", shop_se_price, color, size, shop_num + "",
				def_pic, stock_type_id);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.tv_edit_cart:// 编辑全部
			editorAllGoods(tv_edit_cart);
			break;
		case R.id.btn_to_shop:// 购物车没商品时随便逛逛
			/*
			 * if(MainMenuActivity.instances!=null){
			 * MainMenuActivity.instances.finish(); } intent=new Intent(this,
			 * MainMenuActivity.class);
			 * 
			 * startActivity(intent);
			 */

			intent = new Intent();
			((Activity) context).setResult(10001);
			((Activity) context).finish();
			break;
		case R.id.tv_allchoose:// 全选
			allFlag = !allFlag;
			setAllSelect(allFlag);
			break;
		case R.id.tv_pay:// 结算
			getOrderNo();
			break;
		// case R.id.tv_remove:// 移到收藏夹
		// addShopLike(tv_remove);
		// break;
		// case R.id.tv_delete_all:// 批量删除
		// deleteShopCartList();
		// break;
		default:
			break;
		}

	}

	// 设置全选
	private void setAllSelect(boolean isSelectedAll) {
		if (isSelectedAll) { // 全选 选中状态
			cPrice = 0;
			int goodsNum = 0;
			int saveMoney = 0;
			for (int i = 0; i < spImg.size(); i++) {
				spImg.set(i, 2);
				tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
						context.getResources().getDrawable(
								R.drawable.tvchooseno_selected), null, null,
						null);
				ShopCart shopCart = listShopCarts.get(i);
				cPrice = cPrice + shopCart.getShop_se_price()
						* shopCart.getShop_num();
				goodsNum += shopCart.getShop_num();
				saveMoney += shopCart.getShop_num()
						* (shopCart.getShop_price() - shopCart
								.getShop_se_price());
			}
			String price = new java.text.DecimalFormat("#0.0").format(cPrice);
			String savemoneyStr = new java.text.DecimalFormat("#0.0")
					.format(saveMoney);
			tv_price.setText("合计 " + price + "元");
			tv_pay.setText("去结算(" + goodsNum + ")");
			tv_no_freight.setText("为您节省¥" + savemoneyStr);

		} else {
			for (int i = 0; i < spImg.size(); i++) {
				spImg.set(i, 1);

				tv_allchoose
						.setCompoundDrawablesWithIntrinsicBounds(
								context.getResources().getDrawable(
										R.drawable.tvchooseno_normal), null,
								null, null);
			}
			cPrice = 0;
			tv_price.setText("合计 " + cPrice + "元");
			tv_pay.setText("去结算(0)");
			tv_no_freight.setText("为您节省0.00元");
		}
		adpter.notifyDataSetChanged();
	}

	private void getOrderNo() {
		List<ShopCart> listGoods = new ArrayList<ShopCart>();

		for (int i = 0; i < spImg.size(); i++) {
			int f = spImg.get(i);
			if (f == 2) {
				ShopCart shopCart = listShopCarts.get(i);
				if (shopCart != null) {
					listGoods.add(shopCart);
				}

			}
		}

		// 如果数量为0 不跳转
		if (listGoods.size() == 0) {
			Toast.makeText(context, "没有选中物品", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent(context, SubmitMultiShopActivty.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("listGoods", (Serializable) listGoods);
		intent.putExtras(bundle);
		((Activity) context).startActivityForResult(intent, 101);
	}

	@Override
	public void selectOnCallBack(int position) {
		int p = sp.get(position);
		if (p == 1) {
			sp.set(position, 2);
		} else if (p == 2) {
			sp.set(position, 1);
		}
		setPrice();
		// adpter.notifyDataSetChanged();

	}

	@Override
	public void addOnCallBack(int position, int shop_number) {
		updateShopCart(position, shop_number);

	}

	@Override
	public void reduceOnCallBack(int position, int shop_number) {
		updateShopCart(position, shop_number);

	}

	@Override
	public void updatetOnCallBack(int position) {
		queryShopQueryAttr(position);

	}

	@Override
	public void deleteOnCallBack(final int position) {
		// if (spImg.get(position) == 2) {
		AlertDialog.Builder builder = new Builder(context);
		// 自定义一个布局文件
		View view = View.inflate(context, R.layout.payback_esc_apply_dialog,
				null);
		TextView tv_des = (TextView) view.findViewById(R.id.tv_des);

		tv_des.setText("是否确定删除");

		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
		Button cancel = (Button) view.findViewById(R.id.cancel);

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 把这个对话框取消掉
				dialog.dismiss();
			}
		});
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				deleteShopCart(position);
				dialog.dismiss();
			}
		});
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

		// } else {
		// Toast.makeText(context, "你未选中商品，不能删除", Toast.LENGTH_SHORT)
		// .show();
		// }

	}

	// 左边按钮被勾选
	@Override
	public void selectImgOnCallBack(int position) {
		int p = spImg.get(position);
		if (p == 1) {
			spImg.set(position, 2);
		} else {
			spImg.set(position, 1);
		}
		setPrice();
		// 设置全选状态
		setSelectAllStatus();
	}

	// 如果有勾选没有勾选 全选状态取消
	private void setSelectAllStatus() {
		if (spImg.size() == 0) {
			tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.tvchooseno_normal, 0, 0, 0);
		}
		for (int i = 0; i < spImg.size(); i++) {
			int p = spImg.get(i);
			if (p == 1) {
				tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
						R.drawable.tvchooseno_normal, 0, 0, 0);
				return;
			}
		}
		tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
				R.drawable.tvchooseno_selected, 0, 0, 0);
	}

	// 合计多少钱
	private void setPrice() {
		if (spImg != null && listShopCarts != null) {
			cPrice = 0;
			int chooseNo = 0;
			double saveMoney = 0;
			for (int i = 0; i < spImg.size(); i++) {
				int f = spImg.get(i);
				if (f == 2) {
					ShopCart shopCart = listShopCarts.get(i);
					if (shopCart != null) {
						int number = shopCart.getShop_num();
						double se_price = shopCart.getShop_se_price();
						cPrice = cPrice + number * se_price;
						chooseNo = chooseNo + number;
						saveMoney += shopCart.getShop_num()
								* (shopCart.getShop_price() - se_price);
					}

				}
			}
			tv_pay.setText("去结算(" + chooseNo + ")");
			// new java.text.DecimalFormat("#0.0").format(saveMoney)
			tv_price.setText("合计 "
					+ new java.text.DecimalFormat("#0.0").format(cPrice) + "元");
			tv_no_freight.setText("为您节省了¥"
					+ new java.text.DecimalFormat("#0.0").format(saveMoney));// 为您节省了

		}

	}

	/***
	 * 添加我的喜欢
	 * 
	 * @param v
	 */
	private void addShopLike(View v) {
		if (listShopCarts != null && listShopCarts.size() > 0) {
			StringBuffer bfAdd = new StringBuffer();
			for (int i = 0; i < spImg.size(); i++) {
				int f = spImg.get(i);
				if (f == 2) {
					ShopCart cart = listShopCarts.get(i);
					String like_id = cart.getLike_id();
					if (like_id.equals("-1")) {
						String shop_code = cart.getShop_code();
						bfAdd.append(shop_code + ",");
					}
				}
			}

			String shop_code = bfAdd.toString();
			if (!TextUtils.isEmpty(shop_code)) {
				shop_code = shop_code.substring(0, shop_code.length() - 1);// 去掉最后一个逗号
				new SAsyncTask<String, Void, ReturnInfo>(
						(FragmentActivity) context, v, R.string.wait) {

					@Override
					protected ReturnInfo doInBackground(
							FragmentActivity context, String... params)
							throws Exception {

						return ComModel.addLikeShop(context,
								YCache.getCacheToken(context), params[0]);
					}

					@Override
					protected boolean isHandleException() {
						return true;
					}

					@Override
					protected void onPostExecute(FragmentActivity context,
							ReturnInfo result, Exception e) {

						if (null == e) {
							if (null != result) {
								Toast.makeText(context, "添加成功",
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(context, "添加失败",
										Toast.LENGTH_SHORT).show();
							}
						}
						super.onPostExecute(context, result, e);
					}

				}.execute(shop_code);
			}

		}

	}

	@Override
	public void onItemClickLintener(int position) {
		Intent intent = new Intent(context, ShopDetailsActivity.class);
		intent.putExtra("height", statusBarHeight);
		String p_code = listShopCarts.get(position).getP_code();
		if (TextUtils.isEmpty(p_code)) {
			intent.putExtra("code", listShopCarts.get(position).getShop_code());
		} else {
			intent.putExtra("code", p_code);
			intent.putExtra("isMeal", true);
		}

		// startActivity(intent);
		((Activity) context).startActivityForResult(intent, 100);
		((Activity) context).overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);

	}

	@Override
	public void initData() {
		int resource = R.layout.listview_shop_cart;
		if (mFLagCommom) {
			adpter = new ShopCartAdpter((FragmentActivity) context, resource,
					mListCommon2, sp, spImg, 1,0,"0");
		} else {
			queryListShopCart(1);
			adpter = new ShopCartAdpter((FragmentActivity) context, resource,
					listShopCarts, sp, spImg, 1,0,"0");
			adpter.setCartOncallback(this);
		}
		listView.setAdapter(adpter);
		flush();

	}

	@Override
	public void newJoinOnCallBack(int position) {
		// TODO Auto-generated method stub
		
	}

}
