package com.yssj.utils;

import java.util.List;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ShopCart;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
/**
 * 	在登录成功和APP启动（已登录）的时候后获取购物车数据
* @author lifeng
* @date 2016年8月10日下午5:36:12
 */
public class GetShopCart {
	
	private static Context context;
	
	
	public static  void querShopCart(final Context context,final int a) {//a=0第一次进入app,a=1切换永华
		new SAsyncTask<String, Void, List<List<ShopCart>>>((FragmentActivity) context, R.string.wait) {

			@Override
			protected List<List<ShopCart>> doInBackground(FragmentActivity mContext, String... params)
					throws Exception {
				List<List<ShopCart>> list = ComModel.queryShopCartsAll(context,a);

				return list;
			}

			@Override
			protected void onPostExecute(FragmentActivity mContext, List<List<ShopCart>> list, Exception e) {

				if (e != null) {// 查询异常

				}

			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute();

	}


}
