package com.yssj.ui.activity.shopdetails;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.List;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.yssj.Constants;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.Order;
import com.yssj.entity.ShopCart;
import com.yssj.model.ComModel2;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ShareUtil;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ShowShareActivity extends BasicActivity implements OnClickListener {

	private Intent intent;
	private boolean isMulti = false, isSuccess = false;

	private List<ShopCart> listGoods;

	private AlertDialog dialog2 = null;
	private RadioGroup rgShare;
	private TimeCount timeCount;
	private YJApplication yjApp;
	private TextView tvAutoShare;
	private String shopLink;
	private String content;
	// private String four_pic;
	private UMSocialService mController;

	private ImageView img_countdown;
//	private int[] countDownBg = { R.drawable.count_down1,
//			R.drawable.count_down2, R.drawable.count_down3,
//			R.drawable.count_down3 };
	
	private int[] countDownBg = { R.drawable.count_down1,
			R.drawable.count_down2, R.drawable.count_down3,
			R.drawable.count_down3 };

	private TextView tv_kickback;
	private RadioButton rbQQShare, rbWxinShare;

	private List<Order> listOrder;

	private double kickBack;

	private String order_code;

	private boolean isGcode = false;

	private int requestCode = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogYiFu.e("TAG", "??????????????????");
//		aBar.hide();
		yjApp = (YJApplication) getApplication();

		mController = UMServiceFactory
				.getUMSocialService(Constants.DESCRIPTOR_SHARE);

		order_code = getIntent().getStringExtra("order_code");
		isMulti = getIntent().getBooleanExtra("isMulti", false);
		isSuccess = getIntent().getBooleanExtra("isSuccess", false);
		isGcode = getIntent().getBooleanExtra("is_g_code", false);
		listGoods = (List<ShopCart>) getIntent().getSerializableExtra(
				"listGoods");
		if (null != listGoods && listGoods.size() != 0) {
			for (int i = 0; i < listGoods.size(); i++) {
				kickBack = kickBack + listGoods.get(i).getKickback();
			}
		}
		listOrder = (List<Order>) getIntent().getSerializableExtra("listOrder");
		if (null != listOrder && listOrder.size() != 0) {
			for (int i = 0; i < listOrder.size(); i++) {
				kickBack += listOrder.get(i).getKickBack();
			}

		}
		shopLink = getIntent().getStringExtra("shop_link");
		content = getIntent().getStringExtra("content");
		/*
		 * four_pic = getIntent().getStringExtra("four_pic"); four_pic =
		 * four_pic.split(",")[2];
		 */
		initView();
	}

	private void initView() {
		View view = LayoutInflater.from(this)
				.inflate(R.layout.share_item, null);
		setContentView(view);
		// LinearLayout container = (LinearLayout) findViewById(R.id.container);
		// addView(container, listGoods);
		rbQQShare = (RadioButton) findViewById(R.id.rb_qq);
		// rbQQShare.setOnClickListener(this);
		rbWxinShare = (RadioButton) findViewById(R.id.rb_wx);
		// rbWxinShare.setOnClickListener(this);

		img_countdown = (ImageView) findViewById(R.id.img_countdown);
		img_countdown.setBackgroundResource(countDownBg[2]);

		tv_kickback = (TextView) findViewById(R.id.tv_kickback);
		tv_kickback.setText(Html.fromHtml(getString(R.string.share_kickback,
				new DecimalFormat("#.##").format(kickBack))));
		isShareWxin = ShareUtil.intentIsAvailable(this,
				ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage()));
		isShareQQZone = ShareUtil.intentIsAvailable(this,
				ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage()));
		isShareSina = ShareUtil.intentIsAvailable(this,
				ShareUtil.shareMultiplePictureToSina(ShareUtil.getImage()));
		/*
		 * if (isShareWxin) { rbWxinShare.setOnClickListener(this); } else {
		 * rbWxinShare.setVisibility(View.GONE); } if (isShareQQZone) {
		 * rbQQShare.setOnClickListener(this); } else {
		 * rbQQShare.setVisibility(View.GONE); } if (isShareSina) {
		 * rbSinaShare.setOnClickListener(this); } else {
		 * rbSinaShare.setVisibility(View.GONE); }
		 */
		tvAutoShare = (TextView) findViewById(R.id.tv_auto_share);
		timeCount = new TimeCount(4000, 1000, tvAutoShare);
		timeCount.start();
	}

	private boolean isShareWxin = false, isShareQQZone = false,
			isShareSina = false;

	@Override
	public void onClick(View view) {
		super.onClick(view);
		ShareUtil.configPlatforms(ShowShareActivity.this);
		// UMImage umImage = new UMImage(ShowShareActivity.this, YUrl.imgurl
		// + four_pic);
		// MyLogYiFu.e("pic", YUrl.imgurl + four_pic);
		/*
		 * ShareUtil.setShareContent(ShowShareActivity.this, umImage, content,
		 * shopLink);
		 */
		switch (view.getId()) {
		case R.id.rb_qq:// qq??????
			timeCount.cancel();
			ShareUtil.setQQShareContent(mController, this, null, content,
					shopLink, isShareQQZone);
			intent = ShareUtil
					.shareMultiplePictureToQZone(ShareUtil.getImage());

			if (ShareUtil.intentIsAvailable(ShowShareActivity.this, intent)) {
				onceShare(intent, "qq??????");
			} else {
				performShare(SHARE_MEDIA.QZONE, intent);
			}

			break;
		case R.id.rb_wx:// ????????????
			if (!rbWxinShare.isChecked()) {
				timeCount.cancel();
				ShareUtil.setWxShareContent(mController, yjApp, null, content,
						shopLink, isShareWxin);
				intent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil
						.getImage());
				// performShare(SHARE_MEDIA.WEIXIN_CIRCLE, intent);
				onceShare(intent, "??????");
			}
			break;
		case R.id.rb_sina_share:// ??????????????????
			timeCount.cancel();
			ShareUtil.setSinaShareContent(mController, yjApp, null, content,
					shopLink, isShareSina);
			// if (checkInstallation("com.sina.weibo")) {
			intent = ShareUtil.shareMultiplePictureToSina(ShareUtil.getImage());
			// startActivity(intent);
			// yjApp.savePayCount();
			// finish();
			// } else {
			// Toast.makeText(this, "???????????????????????????????????????", Toast.LENGTH_LONG).show();
			// }
			// performShare(SHARE_MEDIA.SINA, intent);
			onceShare(intent, "????????????");

			break;
		case R.id.img_back:
			timeCount.cancel();

			// intent = new Intent(this, StatusInfoActivity.class);
			// intent.putExtra("index", 1);
			// startActivityForResult(intent, 10002);

			finish();
			break;

		default:
			break;
		}
	}

	private void onceShare(Intent intent, String perform) {
		getKickBack();// ?????????????????????
		Intent showPigIntent = new Intent(ShowShareActivity.this,
				ShowPigActivity.class);
		if (ShareUtil.intentIsAvailable(ShowShareActivity.this, intent)) {
			ShowShareActivity.this.startActivityForResult(intent, requestCode);
		} else {
			Toast.makeText(ShowShareActivity.this, "????????????" + perform + "???????????????",
					Toast.LENGTH_SHORT).show();
			showPigIntent.putExtra("needSecond", false);
		}

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == requestCode) {
			Intent showPigIntent = new Intent(ShowShareActivity.this,
					ShowPigActivity.class);
			showPigIntent.putExtra("needSecond", false);
			ShowShareActivity.this.startActivity(showPigIntent);
			ShowShareActivity.this.finish();
		}
	}

	private void getKickBack() {// ???????????????
		new SAsyncTask<String, Void, Void>(ShowShareActivity.this) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected Void doInBackground(FragmentActivity context,
					String... params) throws Exception {
				// TODO Auto-generated method stub
				ComModel2.getKickback(context, params[0], isGcode);
				return super.doInBackground(context, params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result,
					Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
			}

		}.execute(order_code);
	}

	private void performShare(SHARE_MEDIA platform, final Intent intent) {
		mController.postShare(this, platform, new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,
					SocializeEntity entity) {
				String showText = platform.toString();
				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
					showText += "??????????????????";
					Toast.makeText(ShowShareActivity.this, showText,
							Toast.LENGTH_SHORT).show();
					getKickBack();// ?????????????????????
					if (ShareUtil.intentIsAvailable(ShowShareActivity.this,
							intent)) {
						// ShowShareActivity.this.startActivity(intent);
						// yjApp.savePayCount();
						Intent showPigIntent = new Intent(
								ShowShareActivity.this, ShowPigActivity.class);
						ShowShareActivity.this.startActivity(showPigIntent);
						ShowShareActivity.this.finish();
					} else {
						Toast.makeText(ShowShareActivity.this,
								"????????????" + showText + "?????????", Toast.LENGTH_SHORT)
								.show();
						Intent showPigIntent = new Intent(
								ShowShareActivity.this, ShowPigActivity.class);
						showPigIntent.putExtra("needSecond", false);
						ShowShareActivity.this.startActivity(showPigIntent);
						ShowShareActivity.this.finish();
					}

				} else {
					showText += "??????????????????";
					Toast.makeText(ShowShareActivity.this, showText,
							Toast.LENGTH_SHORT).show();
					ShowShareActivity.this.finish();
				}
			}
		});
	}

	// ????????????????????????
	public boolean checkInstallation(String packageName) {

		try {

			getPackageManager().getPackageInfo(packageName,
					PackageManager.GET_ACTIVITIES);

			return true;

		} catch (NameNotFoundException e) {

			return false;

		}

	}

	// ???????????????????????? ??????????????????
	private void showShareDialog() {
		/** ????????????????????? */
		// AlertDialog.Builder builder = new Builder(SubmitOrderActivity.this);
		// builder.setMessage("??????????????????");
		// builder.setTitle("??????");
		// builder.setPositiveButton("??????", new OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// dialog.dismiss();
		// SubmitOrderActivity.this.finish();
		// }
		// });
		// builder.setNegativeButton("??????", new OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// dialog.dismiss();
		// }
		// });
		// builder.create().show();
		/** ???????????????????????? */
		AlertDialog.Builder builder = null;
		View view = LayoutInflater.from(this).inflate(
				R.layout.choose_share_platform, null);
		RadioGroup rg = (RadioGroup) view.findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				switch (arg1) {
				case R.id.rb_share_timeline:
					intent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil
							.getImage());
					break;
				case R.id.rb_share_qqzone:
					intent = ShareUtil.shareMultiplePictureToQZone(ShareUtil
							.getImage());
					break;
				case R.id.rb_share_sina:
					intent = ShareUtil.shareMultiplePictureToSina(ShareUtil
							.getImage());
					break;
				}
			}

		});

		Button btn_change = (Button) view.findViewById(R.id.btn_change);
		final TimeCount timeCount = new TimeCount(10000, 1000, btn_change);
		timeCount.start();
		btn_change.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog2.dismiss();
				timeCount.cancel();
				startActivityForResult(intent, 101);

			}
		});

		builder = new AlertDialog.Builder(this);
		builder.setTitle("??????????????????");
		builder.setView(view);
		builder.setCancelable(false);
		dialog2 = builder.create();

		dialog2.show();
	}

	private void downloadPic(String picPath, int i) {
		try {
			URL url = new URL(picPath);
			// ????????????
			URLConnection con = url.openConnection();
			// ?????????????????????
			int contentLength = con.getContentLength();
			System.out.println("?????? :" + contentLength);
			// ?????????
			InputStream is = con.getInputStream();
			// 1K???????????????
			byte[] bs = new byte[1024];
			// ????????????????????????
			int len;
			// ??????????????????
			OutputStream os = new FileOutputStream("/sdcard/yssj/" + i + ".jpg");
			// ????????????
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// ???????????????????????????
			os.close();
			is.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		timeCount.cancel();
		finish();
	}

	// ?????????
	class TimeCount extends CountDownTimer {
		private TextView text = null;

		public TimeCount(long millisInFuture, long countDownInterval,
				TextView text) {
			super(millisInFuture, countDownInterval);// ????????????????????????,????????????????????????
			this.text = text;

		}
		

		@Override
		public void onFinish() {// ?????????????????????
			try {

				intent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil
						.getImage());
				ShareUtil.configPlatforms(ShowShareActivity.this);
				/*
				 * UMImage umImage = new UMImage(ShowShareActivity.this,
				 * YUrl.imgurl + four_pic);
				 */
				ShareUtil.setWxShareContent(mController, yjApp, null, content,
						shopLink, isShareWxin);
				// performShare(SHARE_MEDIA.WEIXIN_CIRCLE, intent);

				onceShare(intent, "??????");
				// btn.setBackgroundResource(R.color.actionbar_bn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		@Override
		public void onTick(long millisUntilFinished) {// ??????????????????
			try {
				// btn.setEnabled(false);
				// btn.setBackgroundResource(R.color.time_count);
				// text.setText(millisUntilFinished / 1000 + "???????????????????????????");
				int i = Integer.valueOf(millisUntilFinished / 1000 + "") - 1;
				img_countdown.setBackgroundResource(countDownBg[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
