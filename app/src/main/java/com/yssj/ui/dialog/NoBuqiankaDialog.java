//package com.yssj.ui.dialog;
//
//import java.io.File;
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
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.bean.SocializeEntity;
//import com.umeng.socialize.bean.StatusCode;
//import com.umeng.socialize.controller.UMServiceFactory;
//import com.umeng.socialize.controller.UMSocialService;
//import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
//import com.umeng.socialize.media.UMImage;
//import com.umeng.socialize.weixin.controller.UMWXHandler;
//import com.umeng.socialize.weixin.media.CircleShareContent;
//import com.umeng.socialize.weixin.media.WeiXinShareContent;
//import com.yssj.Constants;
//import com.yssj.YJApplication;
//import com.yssj.YUrl;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.MyGridView;
//import com.yssj.data.YDBHelper;
//import com.yssj.entity.Shop;
//import com.yssj.entity.ShopCart;
//import com.yssj.entity.StockType;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.MainMenuActivity.GetSlidingMenu;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ShareUtil;
//import com.yssj.utils.ToastUtil;
//import com.yssj.wxpay.WxPayUtil;
//
///****
// * 商品详情页 套餐对话框
// * 
// * @author Administrator
// * 
// */
//public class NoBuqiankaDialog
//
////extends Dialog implements OnClickListener 
//{
////	private ImageView icon_close;
////	private Context context;
////	private TextView tv_jifen;
////	public NoBuqiankaDialog(Context context, int style) {
////		super(context, style);
////		setCanceledOnTouchOutside(true);
////		this.context = context;
////		
////
////	}
////	
//	
////	@Override
////	protected void onCreate(Bundle savedInstanceState) {
////		super.onCreate(savedInstanceState);
////		setContentView(R.layout.dialog_no_buqianka);
//////		setContentView(R.layout.dialog_20y_yhq);
////		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
////		icon_close = (ImageView) findViewById(R.id.icon_close);
//////		icon_close.setOnClickListener(this);
////		icon_close.setOnClickListener(this);
////		findViewById(R.id.qushouji).setOnClickListener(this);
////	}
////	@Override
////	public void onClick(View v) {
////		switch (v.getId()) {
////		case R.id.icon_close:
////			this.dismiss();
////			break;
////		case R.id.qushouji:
////			ShareUtil.shareGetSignBuQianKa(context);
////			this.dismiss();
////			break;
////		default:
////			break;
////		}
////
////	}
////	
//}