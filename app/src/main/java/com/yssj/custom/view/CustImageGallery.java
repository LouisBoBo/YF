package com.yssj.custom.view;

import java.util.List;

import com.yssj.YJApplication;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ShopOption;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.main.FilterResultActivity;
import com.yssj.ui.activity.main.SearchResultActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.fragment.MyShopFragment;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.YunYingTongJi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustImageGallery extends LinearLayout {
//private int j;
	private String isWhere="";
	public static int fg=-1;
	private LinearLayout mGroup;

	private Context context;
	private SharedPreferences sp;

	private int[] images = new int[] { R.drawable.img1, R.drawable.img2,
			R.drawable.img3 };

	private String[] codes = new String[] {
			"/shop_option/2015-11-24/20_06_41.jpg",
			"/shop_option/2015-11-24/20_06_18.jpg",
			"/shop_option/2015-11-24/20_05_57.jpg" };

	public CustImageGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.my_horizontal_view, this);
		mGroup = (LinearLayout) findViewById(R.id.hor_group);
		sp = context.getSharedPreferences("YSSJ_yf", 0);
	}

	public void setData(List<ShopOption> list) {
		mGroup.removeAllViews();
		for (int i = 0; i < list.size(); i++) {
			final int j=i;
			ImageView imageView = new ImageView(context);
			int dp = DP2SPUtil.dp2px(context, 9);
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//					LinearLayout.LayoutParams.WRAP_CONTENT,
//					LinearLayout.LayoutParams.WRAP_CONTENT, 1); // , 1是可选写的
//			lp.setMargins(0, dp, dp, dp);
//			imageView.setLayoutParams(lp);
			 imageView.setLayoutParams(new
			 LayoutParams(MyShopFragment.width*1/4-DP2SPUtil.dp2px(context, 2), MyShopFragment.width*1/4-DP2SPUtil.dp2px(context, 2)));

			 imageView.setPadding(0, 0, dp, 0);
			imageView.setAdjustViewBounds(true);

			if (i < 3) {
				if (((ShopOption) list.get(i)).getUrl().equals(codes[i])) {
					imageView.setImageResource(images[i]);
					// SetImageLoader.initImageLoader(null, imageView,
					// "drawable://"+images[i], "");
				} else {
//					SetImageLoader.initImageLoader(
//							context,
//							imageView,
//							((ShopOption) list.get(i)).getUrl().substring(1,
//									list.get(i).getUrl().length()), "!280");
					
					
					
					PicassoUtils.initImage(context, ((ShopOption) list.get(i)).getUrl().substring(1,
									list.get(i).getUrl().length())+"!280", imageView);
					
					
					
				}
			} else {
//				SetImageLoader.initImageLoader(
//						context,
//						imageView,
//						((ShopOption) list.get(i)).getUrl().substring(1,
//								list.get(i).getUrl().length()), "!280");
				
				PicassoUtils.initImage(context, ((ShopOption) list.get(i)).getUrl().substring(1,
								list.get(i).getUrl().length())+"!280", imageView);
			}

			final ShopOption shop = list.get(i);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					isWhere="isCustImageGallery";
					fg=j+1;
					if (j==0) {
						YunYingTongJi.yunYingTongJi(context, 4);
					}
					if (j==1) {
						
						YunYingTongJi.yunYingTongJi(context, 5);
//						SharedPreferencesUtil.saveStringData(context, Pref.TONGJI_TYPE, "1003");
//						SharedPreferencesUtil.saveStringData(context, Pref.TONGJI_PAGE, "风格2");
					}
					if (j==2) {
						YunYingTongJi.yunYingTongJi(context, 6);
					}
					if (j==3) {
						YunYingTongJi.yunYingTongJi(context, 7);
					}
					if (j==4) {
						YunYingTongJi.yunYingTongJi(context, 8);
					}
					if (j==5) {
						YunYingTongJi.yunYingTongJi(context, 9);
					}
					if (j==6) {
						YunYingTongJi.yunYingTongJi(context, 10);
					}
					if (j==7) {
						YunYingTongJi.yunYingTongJi(context, 115);
					}
					if (j==8) {
						YunYingTongJi.yunYingTongJi(context, 11);
					}
					if (j==9) {
						YunYingTongJi.yunYingTongJi(context, 116);
					}
					

					/**
					 * sp.edit().putBoolean("isGoDetail", true).commit();
					 * addScanDataTo(shop.getShop_code()); Intent intent = new
					 * Intent(context, ShopDetailsActivity.class);
					 * intent.putExtra("code", shop.getShop_code());
					 * ((FragmentActivity)
					 * context).startActivityForResult(intent, 102);
					 */
					
					if (shop.getShop_code().contains("type2")) {
						Intent intent = new Intent(context,
								SearchResultActivity.class);
						intent.putExtra("id", shop.getShop_code().split("=")[1]);
						intent.putExtra("isWhere", isWhere);
//						intent.putExtra("shop_name", shop.getShop_name());
						context.startActivity(intent);
					} else {
						Intent intent = new Intent(context,
								FilterResultActivity.class);
						intent.putExtra("isWhere", isWhere);
						
						intent.putExtra("isTuijian", true);
						intent.putExtra("title", shop.getShop_code());
//						intent.putExtra("id", shop.getShop_code().split("=")[1]);
						intent.putExtra("shop_name", shop.getShop_name());
						context.startActivity(intent);
					}
				}
			});
			mGroup.addView(imageView);
		}
	}

	public HorizontalScrollView getScroll() {

		return (HorizontalScrollView) findViewById(R.id.my_h_scroll);

	}

	/*
	 * 把浏览过的数据添加进数据库
	 */
	private void addScanDataTo(final String shop_code) {
		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.addMySteps(context, shop_code);
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result) {
				super.onPostExecute(context, result);
			}

		}.execute();
	}
}
