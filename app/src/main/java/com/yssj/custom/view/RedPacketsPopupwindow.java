//package com.yssj.custom.view;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.ColorDrawable;
//import android.support.v4.app.FragmentActivity;
//import android.text.format.DateFormat;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.view.WindowManager;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.bean.SocializeEntity;
//import com.umeng.socialize.bean.StatusCode;
//import com.umeng.socialize.controller.UMServiceFactory;
//import com.umeng.socialize.controller.UMSocialService;
//import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
//import com.yssj.Constants;
//import com.yssj.YConstance;
//import com.yssj.YUrl;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.league.LeagueBusinessHomeActivity;
//import com.yssj.utils.MD5Tools;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.QRCreateUtil;
//import com.yssj.utils.ShareUtil;
//
//public class RedPacketsPopupwindow extends PopupWindow implements OnClickListener {
//	private Activity mActivity;
//
//	String shareSrc;String name;String word;String wordCircle;
//	private double feedBack;
//	private HashMap<String, String> mapInfos;
//	
//	private TextView tv_title;
//	
//	private int isNoFeed = 0;
//	private Bitmap bm;
//
//	public RedPacketsPopupwindow(Activity activity, double feedBack,String link,HashMap<String, String> mapInfos,String four_pic) {//
//		super(activity);
//		this.feedBack = feedBack;
//		this.mActivity = activity;
//		this.mapInfos = mapInfos;
//		initView(activity);
//	}
//	
//	public RedPacketsPopupwindow(Activity activity, String shareSrc,String name,String word,String wordCircle) {//
//		super(activity);
//		this.shareSrc = shareSrc;
//		this.name = name;
//		this.word = word;
//		this.wordCircle = wordCircle;
//		this.mActivity = activity;
//		initView(activity);
//	}
//	
//	public RedPacketsPopupwindow(Activity activity, double feedBack, int isNoFeed){//此处是判断 任务分享的时候 无回佣，显示不同的文字 isNoFeed = 1
//		super(activity);
//		this.feedBack = feedBack;
//		this.mActivity = activity;
//		this.isNoFeed = isNoFeed;
//		initView(activity);
//	}
//
//	
//	
//
//
//	public RedPacketsPopupwindow(Activity activity, double feedBack,
//			boolean isSecondShare) {
//		this.feedBack = feedBack;
//		this.mActivity = activity;
//		initView(activity);
//	}
//
//	@SuppressWarnings("deprecation")
//	private void initView(Context context) {
//		View rootView = LayoutInflater.from(context).inflate(
//				R.layout.share_redpackets_popupwindow, null);
//		rootView.findViewById(R.id.iv_qq_share).setOnClickListener(this);
//		rootView.findViewById(R.id.iv_wxin_share).setOnClickListener(this);
//		rootView.findViewById(R.id.iv_sina_share).setOnClickListener(this);
//		rootView.findViewById(R.id.iv_wxin_circle_share).setOnClickListener(this);
//		// rootView.findViewById(R.id.btn_cancle).setOnClickListener(this);
//		tv_title = (TextView) rootView.findViewById(R.id.tv_title);
//		
//		TextView tv_kickback = (TextView) rootView.findViewById(R.id.kick_back);
////		if(feedBack==0){
////			tv_kickback.setVisibility(View.GONE);
////			if(isNoFeed == 0)
////				tv_title.setText("亲，福利手慢则无，快分享给其他姐妹吧");
////			else
////				tv_title.setText("美是用来分享哒~分享最爱的美衣给好友");
////		}else{
////			tv_kickback.setVisibility(View.VISIBLE);
////			tv_title.setText("美是用来分享哒~分享最爱的美衣给好友");
////		}
////		tv_kickback.setText(context
////				.getString(R.string.kick_back, feedBack + ""));
//		setContentView(rootView);
//		setWidth(LayoutParams.MATCH_PARENT);
//		setHeight(LayoutParams.WRAP_CONTENT);
//		setAnimationStyle(R.style.mypopwindow_anim_style);
//		setFocusable(true);
//		setTouchable(true);
//		setBackgroundDrawable(new ColorDrawable(R.color.white));
//		setOnDismissListener(new OnDismissListener() {
//
//			@Override
//			public void onDismiss() {
//			}
//		});
//	}
//
//	@Override
//	public void onClick(View v) {
//		int id = v.getId();
//		switch (id) {
//		case R.id.iv_wxin_share:
//			ShareUtil.shareRedPackets(mActivity, shareSrc,name,word);
//			LogYiFu.e("djiejfijife",word);
//			dismiss();
//			break;
//		case R.id.iv_wxin_circle_share:
//			ShareUtil.shareRedPacketsCircle(mActivity, shareSrc,name,wordCircle);
//			dismiss();
//			
//		default:
//			break;
//		}
//	}
//
//	private void onceShare(Intent intent, String perform) {
//		if (ShareUtil.intentIsAvailable(mActivity, intent)) {
//			mActivity.startActivity(intent);
//		} else {
//			Toast.makeText(mActivity, "没有安装" + perform + "客户端",
//					Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	// 供后台记录分享次数
//
//	
//	
//	
//	
//
//}
