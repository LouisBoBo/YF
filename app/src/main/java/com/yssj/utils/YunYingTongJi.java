
/** 
* @author 作者 E-mail: 
* @version 创建时间：2016年8月12日 下午6:49:08 
* 类说明 
*/ 
package com.yssj.utils;

import java.util.List;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ShopCart;
import com.yssj.model.ComModel;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

/**
 * 
 * 运营数据统计工具类
* @author lifeng
* @date 2016年8月12日下午6:49:08
*/
public class YunYingTongJi {
	
	
	private static Context context;
	
	
	public static  void yunYingTongJi(final Context context ,final int type) {
		
		
//
//
//		if(YJApplication.instance.isLoginSucess()){
//
//			new SAsyncTask<String, Void, List<List<ShopCart>>>((FragmentActivity) context, R.string.wait) {
//
//				@Override
//				protected List<List<ShopCart>> doInBackground(FragmentActivity mContext, String... params)
//						throws Exception {
//					List<List<ShopCart>> list = ComModel.yunYingshujuTongji(context,type);
//
//					return list;
//				}
//
//				@Override
//				protected void onPostExecute(FragmentActivity mContext, List<List<ShopCart>> list, Exception e) {
//
//					if (e != null) {// 查询异常
//
//					}
//					LogYiFu.e("yunYingTongJi", type+"");
//				};
//
//				@Override
//				protected boolean isHandleException() {
//					return true;
//				};
//			}.execute();
//		}
//
//

	}

	

}
