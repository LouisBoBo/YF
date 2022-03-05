//package com.yssj.ui.activity.integral;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.util.DisplayMetrics;
//import android.view.Gravity;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
//import com.umeng.socialize.utils.Log;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.IntegralShopDetailsDialog;
//import com.yssj.custom.view.StickyScrollView.ScrollViewListener;
//import com.yssj.entity.IntegralShop;
//import com.yssj.entity.StockType;
//import com.yssj.model.ComModel;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.ui.fragment.integral.IntegralDetailsFragment;
//import com.yssj.ui.fragment.integral.IntegralEvaluateFragment;
//import com.yssj.ui.fragment.integral.IntegralSizeFragment;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.SetImageLoader;
//
//public class AddIntegralProdActivity extends BasicActivity implements
//		OnCheckedChangeListener, ScrollViewListener {
//
//	private HashMap<String, String> map;
//
//	private ImageView img_pic;
//	private TextView tv_describe;
//	private RadioGroup rg;
//	private RadioButton rb_details, rb_size, rb_evaluate;
//	private Button btn_exchange;
//
//	private FragmentManager fm;
//	private FragmentTransaction ft;
//
//	private HashMap<String, Object> mapReturn;
//	private IntegralDetailsFragment detailFragment;// 详情Fragment
//	private IntegralSizeFragment sizeFragment;// 尺寸Fragment
//	private IntegralEvaluateFragment evaluateFragment;// 评价Fragment
//
//	private IntegralShop shop;
//	private int height;
//
//	private DisplayImageOptions options;
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		map = (HashMap<String, String>) getIntent()
//				.getSerializableExtra("item");
//		fm = this.getSupportFragmentManager();
//		ft = fm.beginTransaction();
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		height = dm.heightPixels;
//		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.ic_stub)
//				.showImageForEmptyUri(R.drawable.ic_empty)
//				.cacheInMemory(true)
//				.cacheOnDisk(true).considerExifParams(true)
//				.displayer(new FadeInBitmapDisplayer(35)).build();
//		initData();
//	}
//
//	private void initData() {
//		new SAsyncTask<String, Void, HashMap<String, Object>>(this,
//				R.string.wait) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.getIntegralGoodById(context, params[0]);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, Object> result) {
//				// TODO Auto-generated method stub
//				if (null != result) {
//					mapReturn = result;
//					shop = (IntegralShop) mapReturn.get("shop");
//					initView();
//				}
//				super.onPostExecute(context, result);
//			}
//
//		}.execute(map.get("shop_code"));
//	}
//
//	private void initView() {
//		setContentView(R.layout.add_integral_prod);
//		img_pic = (ImageView) findViewById(R.id.img_pic);
//		tv_describe = (TextView) findViewById(R.id.tv_describe);
//		rg = (RadioGroup) findViewById(R.id.rg);
//		rg.setOnCheckedChangeListener(this);
//		rb_details = (RadioButton) findViewById(R.id.rb_details);
//		rb_size = (RadioButton) findViewById(R.id.rb_size);
//		rb_evaluate = (RadioButton) findViewById(R.id.rb_evaluate);
//
//		btn_exchange = (Button) findViewById(R.id.btn_exchange);
//		btn_exchange.setOnClickListener(this);
//
//		SetImageLoader.initImageLoader(this, img_pic, map.get("def_pic"),"");
//		initFragment();
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		super.onClick(v);
//		switch (v.getId()) {
//		case R.id.btn_exchange:
//			queryShopQueryAttr();
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	/***
//	 * 查找商品属性
//	 */
//	private void queryShopQueryAttr() {
//		if (shop != null) {
//			List<StockType> list = shop.getList_stock_type();
//			if (list != null && list.size() > 0) {
//				showPopWindow(1);
//			} else {
//				new SAsyncTask<String, Void, IntegralShop>(this, null,
//						R.string.wait) {
//					@Override
//					protected IntegralShop doInBackground(
//							FragmentActivity context, String... params)
//							throws Exception {
//						ComModel.queryInteShopQueryAttr(
//								AddIntegralProdActivity.this, shop, params[0]);
//						return shop;
//					}
//
//					@Override
//					protected void onPostExecute(FragmentActivity context,
//							IntegralShop shop, Exception e) {
//
//						if (e != null) {// 查询异常
//							Toast.makeText(AddIntegralProdActivity.this,
//									"连接超时，请重试", Toast.LENGTH_LONG).show();
//
//						} else {// 查询商品详情成功，刷新界面
//							if (shop != null) {
//								AddIntegralProdActivity.this.shop = shop;
//								showPopWindow(1);
//							}
//						}
//
//					};
//
//					@Override
//					protected boolean isHandleException() {
//						return true;
//					};
//				}.execute("false");
//			}
//
//		}
//	}
//
//	/****
//	 * 弹出底部对话框
//	 * 
//	 * @param i
//	 */
//	private void showPopWindow(int i) {
//		if (shop != null) {
//			final IntegralShopDetailsDialog dlg = new IntegralShopDetailsDialog(
//					this, R.style.DialogStyle, height, shop, options,
//					animateFirstListener, 1);
//			Window window = dlg.getWindow();
//			window.setGravity(Gravity.BOTTOM);
//			window.setWindowAnimations(R.style.dlg_down_to_top);
//			dlg.show();
//
//			dlg.callBackShopCart = new IntegralShopDetailsDialog.OnCallBackShopCart() {
//
//				@Override
//				public void callBackChoose(int type, String size, String color,
//						int shop_num, double price,int stock_type_id, int stock,String pic, View v) {
//					dlg.dismiss();
//					if (type == 1) {// 购买
//
//						Intent intent = new Intent(
//								AddIntegralProdActivity.this,
//								SubmitOrderActivity.class);
//						Bundle bundle = new Bundle();
//						bundle.putSerializable("shop", shop);
//						intent.putExtras(bundle);
//						intent.putExtra("size", size);
//						intent.putExtra("color", color);
//						intent.putExtra("shop_num", shop_num);
//						intent.putExtra("stock_type_id", stock_type_id);
//						LogYiFu.e("stock", stock+"");
//						intent.putExtra("stock", stock);
//						intent.putExtra("price", price);
//						startActivity(intent);
//
//					} else {// 加入购物车
//						// joinShopCart(size, color, shop_num, v);
//						Intent intent = new Intent(
//								AddIntegralProdActivity.this,
//								SubmitOrderActivity.class);
//						Bundle bundle = new Bundle();
//						bundle.putSerializable("shop", shop);
//						intent.putExtras(bundle);
//						intent.putExtra("size", size);
//						intent.putExtra("color", color);
//						intent.putExtra("shop_num", shop_num);
//						intent.putExtra("stock_type_id", stock_type_id);
//						LogYiFu.e("stock", stock+"");
//						intent.putExtra("stock", stock);
//						intent.putExtra("price", price);
//						startActivity(intent);
//					}
//				}
//			};
//		}
//	}
//
//	private void initFragment() {
//		detailFragment = new IntegralDetailsFragment(
//				(IntegralShop) mapReturn.get("shop"));
//		sizeFragment = new IntegralSizeFragment(
//				(IntegralShop) mapReturn.get("shop"));
//		evaluateFragment = new IntegralEvaluateFragment(mapReturn);
//		ft.add(R.id.container, detailFragment);
//		ft.show(detailFragment);
//		ft.commitAllowingStateLoss();
//	}
//
//	@Override
//	public void onCheckedChanged(RadioGroup arg0, int arg1) {
//		// TODO Auto-generated method stubs
//		ft = fm.beginTransaction();
//		if (arg1 == rb_details.getId()) {
//			if (!detailFragment.isAdded()) {
//				ft.add(R.id.container, detailFragment);
//			}
//			ft.show(detailFragment);
//			if (sizeFragment.isAdded()) {
//				ft.hide(sizeFragment);
//			}
//			if (evaluateFragment.isAdded()) {
//				ft.hide(evaluateFragment);
//			}
//			ft.commit();
//		} else if (arg1 == rb_size.getId()) {
//			if (!sizeFragment.isAdded()) {
//				ft.add(R.id.container, sizeFragment);
//			}
//			ft.show(sizeFragment);
//			if (detailFragment.isAdded()) {
//				ft.hide(detailFragment);
//			}
//			if (evaluateFragment.isAdded()) {
//				ft.hide(evaluateFragment);
//			}
//			ft.commit();
//
//		} else if (arg1 == rb_evaluate.getId()) {
//			if (!evaluateFragment.isAdded()) {
//				ft.add(R.id.container, evaluateFragment);
//			}
//			ft.show(evaluateFragment);
//			if (sizeFragment.isAdded()) {
//				ft.hide(sizeFragment);
//			}
//			if (detailFragment.isAdded()) {
//				ft.hide(detailFragment);
//			}
//			ft.commit();
//
//		}
//
//	}
//
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
//	}
//
//	@Override
//	public void onScrollChanged(int x, int y, int oldx, int oldy) {
//		// TODO Auto-generated method stub
//
//	}
//
//}
