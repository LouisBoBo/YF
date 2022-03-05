//package com.yssj.ui.dialog;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.Html;
//import android.text.TextUtils;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
////import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.yssj.YJApplication;
//import com.yssj.YUrl;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.MyGridView;
//import com.yssj.data.YDBHelper;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.entity.Shop;
//import com.yssj.entity.ShopCart;
//import com.yssj.entity.StockType;
//import com.yssj.model.ComModel;
//import com.yssj.ui.activity.MainMenuActivity.GetSlidingMenu;
//import com.yssj.ui.activity.infos.BalanceDoubleSuccessActivity;
//import com.yssj.ui.activity.infos.MyWalletActivity;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YCache;
//
///****
// * 
// * 
// * @author Administrator
// * 
// */
//public class DialogFanbeiKaiqi extends Dialog implements OnClickListener {
//	private ImageView icon_close;
//	private Context context;
//	public DialogFanbeiKaiqi(Context context, int style) {
//		super(context, style);
//		setCanceledOnTouchOutside(true);
//		this.context = context;
//
//	}
//	
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.dialog_2yuan_fanbei);
////		setContentView(R.layout.dialog_20y_yhq);
//		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
////		icon_close = (ImageView) findViewById(R.id.icon_close);
////		icon_close.setOnClickListener(this);
//		findViewById(R.id.bt).setOnClickListener(this);
//		findViewById(R.id.getYue).setOnClickListener(this);
//	}
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.bt:
//			this.dismiss();
//			break;
//		case R.id.getYue:  //开启余额翻倍特权
//		
//			
//				new SAsyncTask<Void, Void, HashMap<String, Object> >((FragmentActivity) context,
//						R.string.wait) {
//
//					@Override
//					protected HashMap<String, Object>  doInBackground(FragmentActivity context,
//							Void... params) throws Exception {
//						HashMap<String, Object>  returnInfo = new HashMap<String, Object> ();
//						returnInfo = ComModel.startDoubleBalance(context,
//								YCache.getCacheToken(context),1);
//						return returnInfo;
//					}
//
//					@Override
//					protected boolean isHandleException() {
//						return true;
//					}
//
//					@Override
//					protected void onPostExecute(FragmentActivity context,
//							HashMap<String, Object>  result, Exception e) {
//						super.onPostExecute(context, result, e);
//						if (e == null && result != null
//								) {
//							
//							//跳到开启成功界面
//							Intent intent = new Intent(getContext(), BalanceDoubleSuccessActivity.class);
//							intent.putExtra("kaiqi", 2);
//							getContext().startActivity(intent);
//							
//						} else {
//							ToastUtil.showShortText(context, "开启失败");
//						}
//
//					}
//
//				}.execute();
//			
//			dismiss();
//			break;
//		default:
//			break;
//		}
//
//	}
//
//}