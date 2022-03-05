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
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
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
//import com.yssj.custom.view.MyGridView;
//import com.yssj.data.YDBHelper;
//import com.yssj.entity.Shop;
//import com.yssj.entity.ShopCart;
//import com.yssj.entity.StockType;
//import com.yssj.ui.activity.MainMenuActivity.GetSlidingMenu;
////import com.yssj.ui.activity.infos.DailySignActivity;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ToastUtil;
//
///****
// *	语音红包匹配度
// * 
// * @author Administrator
// * 
// */
//public class RedPackgeMatchDialog extends Dialog implements OnClickListener {
//	private Context context;
//	TextView tv10, tv20, tv30, tv40, tv50, tv60, tv70, tv80, tv90, tv100;
//	
//	public String redpackageMatch ;
//	
//	
//	private RedpackageMatchListener redpackageMatchListener;
//	
//	public interface RedpackageMatchListener{
//		
//		 public void callBack(String match);
//		 
//		
//	}
//	public void setRedpackageListener(Activity activity){
//		this.redpackageMatchListener = (RedpackageMatchListener) activity;
//	}
//	
//
//	public RedPackgeMatchDialog(Context context,int styl) {
//		super(context,styl);
//		
//		setCanceledOnTouchOutside(true);
//		this.context = context;
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.dialog_redpackage_match);
//		initView();
//	}
//
//	private void initView() {
//
//		tv10 = (TextView) findViewById(R.id.tv10);
//		tv10.setOnClickListener(this);
//		tv20 = (TextView) findViewById(R.id.tv20);
//		tv20.setOnClickListener(this);
//		tv30 = (TextView) findViewById(R.id.tv30);
//		tv30.setOnClickListener(this);
//		tv40 = (TextView) findViewById(R.id.tv40);
//		tv40.setOnClickListener(this);
//		tv50 = (TextView) findViewById(R.id.tv50);
//		tv50.setOnClickListener(this);
//		tv60 = (TextView) findViewById(R.id.tv60);
//		tv60.setOnClickListener(this);
//		tv70 = (TextView) findViewById(R.id.tv70);
//		tv70.setOnClickListener(this);
//		tv80 = (TextView) findViewById(R.id.tv80);
//		tv80.setOnClickListener(this);
//		tv90 = (TextView) findViewById(R.id.tv90);
//		tv90.setOnClickListener(this);
//		tv100 = (TextView) findViewById(R.id.tv100);
//		tv100.setOnClickListener(this);
//
//	}
//
//	@Override
//	public void onClick(View v) {
//		
//		
//		
//		
//		switch (v.getId()) {
//		case R.id.tv10:
//			
//			// 在onclick事件中添加
//			//tv10.setTextColor(Color.RED);// 点击修改颜色
//			tv10.setBackgroundColor(Color.parseColor("#00A2E8"));
//			// 还原成原来的黑色
//			new Handler().postDelayed(new Runnable() {
//
//				@Override
//				public void run() {
//					tv10.setBackgroundColor(Color.WHITE);
//					dismiss();
//				}
//			}, 200);
//			redpackageMatchListener.callBack("10");
//			
//			break;
//
//		case R.id.tv20:
//			tv20.setBackgroundColor(Color.parseColor("#00A2E8"));
//			new Handler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					tv20.setBackgroundColor(Color.WHITE);
//					dismiss();
//				}
//			}, 200);
//			redpackageMatchListener.callBack("20");
//			break;
//		case R.id.tv30:
//			tv30.setBackgroundColor(Color.parseColor("#00A2E8"));
//			new Handler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					tv30.setBackgroundColor(Color.WHITE);
//					dismiss();
//				}
//			}, 200);
//			redpackageMatchListener.callBack("30");
//			break;
//		case R.id.tv40:
//			tv40.setBackgroundColor(Color.parseColor("#00A2E8"));
//			new Handler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					tv40.setBackgroundColor(Color.WHITE);
//					dismiss();
//				}
//			}, 200);
//			redpackageMatchListener.callBack("40");
//			break;
//		case R.id.tv50:
//			tv50.setBackgroundColor(Color.parseColor("#00A2E8"));
//			new Handler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					tv50.setBackgroundColor(Color.WHITE);
//					dismiss();
//				}
//			}, 200);
//			redpackageMatchListener.callBack("50");
//			break;
//		case R.id.tv60:
//			tv60.setBackgroundColor(Color.parseColor("#00A2E8"));
//			new Handler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					tv60.setBackgroundColor(Color.WHITE);
//					dismiss();
//				}
//			}, 200);
//			redpackageMatchListener.callBack("60");
//			break;
//		case R.id.tv70:
//			tv70.setBackgroundColor(Color.parseColor("#00A2E8"));
//			new Handler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					tv70.setBackgroundColor(Color.WHITE);
//					dismiss();
//				}
//			}, 200);
//			redpackageMatchListener.callBack("70");
//			break;
//		case R.id.tv80:
//			tv80.setBackgroundColor(Color.parseColor("#00A2E8"));
//			new Handler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					tv80.setBackgroundColor(Color.WHITE);
//					dismiss();
//				}
//			}, 200);
//			redpackageMatchListener.callBack("80");
//			break;
//		case R.id.tv90:
//			tv90.setBackgroundColor(Color.parseColor("#00A2E8"));
//			new Handler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					tv90.setBackgroundColor(Color.WHITE);
//					dismiss();
//				}
//			}, 200);
//			redpackageMatchListener.callBack("90");
//			break;
//		case R.id.tv100:
//			tv100.setBackgroundColor(Color.parseColor("#00A2E8"));
//			new Handler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					tv100.setBackgroundColor(Color.WHITE);
//					dismiss();
//				}
//			}, 200);
//			redpackageMatchListener.callBack("100");
//			break;
//
//		default:
//			break;
//		}
//
//	}
//	
//	
//
//}