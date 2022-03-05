//package com.yssj.ui.dialog;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.TextView;
//import com.yssj.activity.R;
//import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.ui.activity.shopdetails.ShareDetailsActivity;
//
///****
// * 分享额外奖励 ---签到弹出
// * 
// * @author Administrator
// * 
// */
//public class ShareOrtherJiangliDialog extends Dialog implements OnClickListener {
//	private ImageView icon_close;
//	private Context context;
//	private TextView tv_jifen,tv_ewaijiangli;
//	private String ewaijiangli;
//
//	public ShareOrtherJiangliDialog(Context context, int style,String ewaijiangli) {
//		super(context, style);
//		setCanceledOnTouchOutside(true);
//		this.context = context;
//		this.ewaijiangli = ewaijiangli;
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.dialog_share_ewaijiangli);
//		// setContentView(R.layout.dialog_20y_yhq);
//		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//		// icon_close = (ImageView) findViewById(R.id.icon_close);
//		// icon_close.setOnClickListener(this);
//		findViewById(R.id.bt).setOnClickListener(this);
//		findViewById(R.id.zhuanjifen).setOnClickListener(this);
//		tv_ewaijiangli = (TextView) findViewById(R.id.tv_ewaijiangli);
//		tv_ewaijiangli.setText(ewaijiangli +"元额外奖励");
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.bt: // 买买买
//
//			Intent intent2 = new Intent((Activity) context, MainMenuActivity.class);
//			intent2.putExtra("toYf", "toYf");
//			context.startActivity(intent2);
//			dismiss();
//			break;
//		case R.id.zhuanjifen: // 查看奖励
//			context.startActivity(new Intent(context, ShareDetailsActivity.class));
//			dismiss();
//			break;
//		default:
//			break;
//		}
//
//	}
//
//}