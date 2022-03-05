package com.yssj.custom.view;

import java.io.File;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ShopOption;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.main.FilterResultActivity;
import com.yssj.ui.activity.main.SearchResultActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.fragment.MatchFragment;
import com.yssj.ui.fragment.MyShopFragment;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.YunYingTongJi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustImageGalleryMatch extends LinearLayout {

	private LinearLayout mGroup;

	private Context context;
	private CustomMatchTuijianItem customImages;
	private java.text.DecimalFormat pFormate;

	public CustImageGalleryMatch(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.my_horizontal_view, this,true);
		mGroup = (LinearLayout) findViewById(R.id.hor_group);
	}

	public void setData(List<HashMap<String, Object>> list,String type) {
		pFormate=new DecimalFormat("#0.0");
		mGroup.removeAllViews();
		for (int i = 0; i < list.size(); i++) {
			customImages= new CustomMatchTuijianItem(context);
			int dp = DP2SPUtil.dp2px(context, 5);
			
			customImages.setLayoutParams(getLayoutParams());
			customImages.setPadding(0, 0, dp, 0);
			
			final String shop_code = list.get(i).get("shop_code").toString();
			String url = shop_code.substring(1, 4)+File.separator+shop_code
					+File.separator+list.get(i).get("def_pic").toString();
			customImages.setImageView(url);
			double sPrice = Double.valueOf(list.get(i).get("shop_se_price").toString());
			double sKickback = Double.valueOf(list.get(i).get("kickback").toString());
			String suppLabel = list.get(i).get("supp_label").toString();
			if("1".equals(type)){//搭配
				customImages.setTextView(list.get(i).get("shop_name").toString(),"¥"+pFormate.format(sPrice*0.9),suppLabel);//显示9折价格
			}else{//专题
				customImages.setTextView(list.get(i).get("shop_name").toString(),"¥"+pFormate.format(sPrice*0.9));//显示9折价格
			}

			if(GuideActivity.show1yuan){
				customImages.setTextViewOne(list.get(i).get("shop_name").toString());
			}

//			final ShopOption shop = list.get(i);
			customImages.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					YunYingTongJi.yunYingTongJi(context, 58);//首页搭配推荐商品图片
//					if (shop.getShop_code().contains("type2")) {
//						Intent intent = new Intent(context,
//								SearchResultActivity.class);
//						intent.putExtra("id", shop.getShop_code().split("=")[1]);
//						context.startActivity(intent);
//					} else {
//						Intent intent = new Intent(context,
//								FilterResultActivity.class);
//						intent.putExtra("isTuijian", true);
//						intent.putExtra("title", shop.getShop_code());
//						context.startActivity(intent);
//					}
					if(!TextUtils.isEmpty(shop_code)){
						addScanDataTo(shop_code);
						Intent intent = new Intent(context,
								ShopDetailsActivity.class);
						intent.putExtra("code", shop_code);
						context.startActivity(intent);
						((FragmentActivity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
					}
					
				}
			});
			mGroup.addView(customImages);
		}
	}

	public HorizontalScrollView getScroll() {

		return (HorizontalScrollView) findViewById(R.id.my_h_scroll);

	}
	/*
	 * 把浏览过的数据添加进数据库
	 */
	private void addScanDataTo(final String shop_code) {
		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity)context) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
				return ComModel.addMySteps(context, shop_code);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
				super.onPostExecute(context, result, e);
			}

		}.execute();
	}
}
