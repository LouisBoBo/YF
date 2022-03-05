package com.yssj.ui.activity.setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.yssj.Constants;
import com.yssj.YConstance;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.huanxin.activity.BaseActivity;
import com.yssj.model.ComModel2;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.ToastUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class InviteFriendActivity extends BaseActivity implements OnClickListener {
	private Exception ee;
	private Bitmap heard_bit_pic;
	private String pic ="";

	private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);
	private Intent wXinShareIntent; /*
									 * = ShareUtil
									 * .shareMultiplePictureToTimeLine(ShareUtil
									 * .getInviteImage());
									 */ // 分享到微信
	private Intent qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage()); // 分享到QQ空间

	HashMap<String, Object> hashmap;

	private Bitmap bmNew;
	private ImageButton img_save;
	private ImageButton img_qq_space;
	private ImageView img_shop;
	// private String picPath = "userinfo/head_pic/default.jpg";
	private RoundImageButton img_user_heard;
	private String userheard;
	private RelativeLayout rl_farther;
	private ImageView img_erweima;
	private ImageButton img_back;
	private ImageButton img_wx_friend_circle;
	private String link ="";

	private FileOutputStream bigOutputStream2;
	private Bitmap bmg;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.invite_friend);

		userheard = getIntent().getStringExtra("userheard");
		initView();
		initDta();

	}

	private void initView() {
		
		img_shop = (ImageView) findViewById(R.id.img_shop);
		// img_shop.setOnClickListener(this);
		img_save = (ImageButton) findViewById(R.id.img_save); // 保存
//		img_save.setOnClickListener(this);
		img_qq_space = (ImageButton) findViewById(R.id.img_qq_space); // QQ空间
//		img_qq_space.setOnClickListener(this);
		img_user_heard = (RoundImageButton) findViewById(R.id.img_user_heard);
		rl_farther = (RelativeLayout) findViewById(R.id.rl_farther);
		rl_farther.setBackgroundColor(getResources().getColor(R.color.white_white));
		img_erweima = (ImageView) findViewById(R.id.img_erweima);
		img_back = (ImageButton) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		img_wx_friend_circle = (ImageButton) findViewById(R.id.img_wx_friend_circle); // 朋友圈
//		img_wx_friend_circle.setOnClickListener(this);

		// 为适配乐视手机
//		WindowManager wm = this.getWindowManager();
//		int screenWidth = wm.getDefaultDisplay().getWidth();
//		int screenHeidth = wm.getDefaultDisplay().getHeight();
//		ViewGroup.LayoutParams lp = img_shop.getLayoutParams();
//		lp.width = screenWidth;
//		lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//		img_shop.setLayoutParams(lp);
//		img_shop.setMaxWidth(screenWidth);
//		img_shop.setMaxHeight(screenHeidth); // ？？
//		// img_shop.setMaxHeight(img_shop.getHeight());
//		System.out.println("图片宽度宽度=" + screenWidth);
//		System.out.println("图片高度=" + screenHeidth);
	}

	private void initDta() {
		new SAsyncTask<Void, Void, HashMap<String, Object>>(this, 0) {

			@Override
			protected void onPostExecute(final FragmentActivity context, final HashMap<String, Object> result,
					Exception e) {
				super.onPostExecute(context, result, e);

				ee = e;
				if (e == null ) {

//					SetImageLoader.initImageLoader(context, img_user_heard, (String) result.get("pic"), "");
					PicassoUtils.initImage(context, (String) result.get("pic"), img_user_heard);
					pic = result.get("pic").toString();
					
					new Thread(new Runnable() {

						private String path;

						public void run() {
//							String path = YUrl.imgurl + pic;
							
							try {
								if (pic.contains("http")) {
									path = pic;
								}else {
									 path = YUrl.imgurl + pic;
								}
								
								
								heard_bit_pic = getBitmap(path);
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}).start();
					
					link = result.get("link").toString();

					Bitmap bmQr = QRCreateUtil.createQrImage(link, 180, 180);// 得到二维码图片

					img_erweima.setImageBitmap(bmQr);

//					Drawable img_invite_friend = getResources().getDrawable(R.drawable.invite_friend);
//					BitmapDrawable bd = (BitmapDrawable) img_invite_friend;
//					bmg = bd.getBitmap();
					img_wx_friend_circle.setOnClickListener(InviteFriendActivity.this);
					img_save.setOnClickListener(InviteFriendActivity.this);
					img_qq_space.setOnClickListener(InviteFriendActivity.this);
				}else {
					img_wx_friend_circle.setOnClickListener(InviteFriendActivity.this);
					img_save.setOnClickListener(InviteFriendActivity.this);
					img_qq_space.setOnClickListener(InviteFriendActivity.this);
				}
				
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
					throws Exception {

				return ComModel2.getInviteFriend(context);
			}

		}.execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_save:
			if (ee == null) {
				Toast.makeText(InviteFriendActivity.this, "已保存", Toast.LENGTH_SHORT).show();
				save();
			} else {
				Toast.makeText(InviteFriendActivity.this, "保存失败，检查一下你的网络", Toast.LENGTH_SHORT).show();
			}
			// Toast.makeText(InviteFriend.this, "已保存",
			// Toast.LENGTH_SHORT).show();
			// save();
			break;

		case R.id.img_back:
			finish();
			break;

		case R.id.img_wx_friend_circle: // 朋友圈分享
			if (ee == null) {
				ShareUtil.addWXPlatform(InviteFriendActivity.this);
				weixin_save();
			} else {
				Toast.makeText(InviteFriendActivity.this, "分享失败，检查一下你的网络", Toast.LENGTH_SHORT).show();
			}
			// ShareUtil.addWXPlatform(InviteFriend.this);
			// weixin_save();

			break;

		case R.id.img_qq_space: // QQ空间分享
			if (ee == null) {
				ShareUtil.addQQQZonePlatform(InviteFriendActivity.this);
				ToastUtil.showShortText(InviteFriendActivity.this, "分享加载中，稍等哟~");

				performShare(SHARE_MEDIA.QZONE, qqShareIntent);
			} else {
				Toast.makeText(InviteFriendActivity.this, "分享失败，检查一下你的网络", Toast.LENGTH_SHORT).show();
			}

			break;
		default:
			break;
		}

	}

	private void onceShare3(Intent intent, String perform) {
		if (ShareUtil.intentIsAvailable(InviteFriendActivity.this, intent)) {
			InviteFriendActivity.this.startActivity(intent);
		}
	}

	public void performShare(SHARE_MEDIA platform, final Intent intent) {
		UMImage umImage;

		umImage = new UMImage(InviteFriendActivity.this, bmg);

		ShareUtil.setShareContent(InviteFriendActivity.this, umImage, "能赚钱的女装特卖神器", link);

		mController.postShare(InviteFriendActivity.this, platform, new SnsPostListener() {

			@Override
			public void onStart() {
				LogYiFu.e("showText", "asdsafdsf");
				// chooseDialog();
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
				String showText = platform.toString();
				LogYiFu.e("showText", showText);

				if (eCode == StatusCode.ST_CODE_SUCCESSED) {

					ToastUtil.showShortText(InviteFriendActivity.this, "QQ空间分享成功");

				} else {

				}

			}
		});
	}

	// 图片合并
	public Bitmap toConformBitmap(Bitmap background, Bitmap foreground) {
		if (background == null) {
			return null;
		}
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();// 屏幕宽度
		int height = wm.getDefaultDisplay().getHeight();

		int bgWidth = background.getWidth(); // 大图的宽高
		int bgHeight = background.getHeight();

//		bgWidth = width;
		// bgHeight=height/4*3; //感觉这里要出毛病

		int fgWidth = foreground.getWidth(); // 小图的宽高
		int fgHeight = foreground.getHeight();
		// create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
		// Bitmap newbmp = Bitmap.createBitmap(bgWidth+fgWidth, bgHeight,
		// Config.ARGB_8888);

		// 创一个屏幕宽的图
		 Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight,
		 Config.ARGB_8888);

		//下面这一段又是为适配乐视手机
//		float scaleWight = ((float) width) / bgWidth;
//		float scaleHeight = ((float) height) / bgHeight;
//		Drawable img_invite = getResources().getDrawable(R.drawable.invite_friend);
//		 BitmapDrawable bd = (BitmapDrawable) img_invite;
//		 Bitmap bitmap = bd.getBitmap();
//		 Matrix matrix =  new Matrix();  
//		matrix.postScale(scaleWight, scaleWight);            
//		Bitmap newbmp = Bitmap.createBitmap(background, 0, 0, background.getWidth()-1, background.getHeight(), matrix, true);
//		System.out.println("这个不能空吧="+newbmp);
//		System.out.println("比值="+scaleWight);
//		System.out.println("屏幕宽="+width);
//		System.out.println("图片图片宽="+bgWidth);
//		System.out.println("二维码宽="+fgWidth);
//		System.out.println("矩阵="+matrix);
		
//		Bitmap newbmp = Bitmap.createBitmap(width, bgHeight, Config.ARGB_8888);
		Canvas cv = new Canvas(newbmp);
		// draw bg into
		cv.drawBitmap(background, 0, 0, null);// 在 0，0坐标开始画入bg
		// draw fg into
		cv.drawBitmap(foreground, width / 2 - fgWidth / 2, bgHeight - fgHeight, null);//居中
//		cv.drawBitmap(foreground, width / 2 - fgWidth / 5 * 3, bgHeight - fgHeight, null);// 在两手之间
																							// 0，0坐标开始画入fg
																							// ，可以从任意位置画入
		// save all clip
		cv.save();// 保存
		// store
		cv.restore();// 存储
		return newbmp;
	}

	// 头像和进二维码
	public Bitmap toConformBitmapHeart(Bitmap background, Bitmap foreground) {
		if (background == null) {
			return null;
		}
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();// 屏幕宽度
		int height = wm.getDefaultDisplay().getHeight();

		int bgWidth = background.getWidth(); // 大图的宽高
		int bgHeight = background.getHeight();

		int fgWidth = foreground.getWidth(); // 小图的宽高
		int fgHeight = foreground.getHeight();
		// create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
		// Bitmap newbmp = Bitmap.createBitmap(bgWidth+fgWidth, bgHeight,
		// Config.ARGB_8888);

		// 创一个屏幕宽的图
		Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight, Config.ARGB_8888);
		Canvas cv = new Canvas(newbmp);
		// draw bg into
		cv.drawBitmap(background, 0, 0, null);// 在 0，0坐标开始画入bg
		// draw fg into
		cv.drawBitmap(foreground, bgWidth / 2 - fgWidth / 2, bgHeight / 2 - fgHeight / 2, null);// 在
																								// 0，0坐标开始画入fg
																								// ，可以从任意位置画入
		// save all clip
		cv.save();// 保存
		// store
		cv.restore();// 存储
		return newbmp;
	}

	// 得到图片对象
	public static Bitmap getBitmap(String path) throws IOException {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			return bitmap;
		}
		return null;
	}

	// 将bitmap转成圆形
	public Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		
		Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(outBitmap);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPX = bitmap.getWidth() / 2;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return outBitmap;
	}

	// 将bitmap缩小
	private Bitmap small(Bitmap bitmap, Bitmap bit) {
		Matrix matrix = new Matrix();

		int width2 = bitmap.getWidth();
		int height2 = bitmap.getHeight();
		//
		//
		int width = bit.getWidth();// 二维码宽高
		int height = bit.getHeight();

		// width2=2*width/5;
		// height2=2*height/5;
		
		if (width2 <= 150) {
			matrix.postScale(0.3f, 0.3f);
			// Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0,
			// bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		} else if(width2 <= 300){
			matrix.postScale(0.2f, 0.2f); // 长和宽放大缩小的比例
			// Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0,
			// bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		}else {
			matrix.postScale(0.12f, 0.12f);
		}

		// matrix.postScale(0.3f, 0.3f); // 长和宽放大缩小的比例
//		System.out.println("宽=" + width2 + "    高=" + height2);
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		// Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0,
		// bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizeBmp;
	}

	private void save() {
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();// 屏幕宽度
		int height = wm.getDefaultDisplay().getHeight();

		Bitmap bmQr = QRCreateUtil.createQrImage(link, width / 3, width / 3); // 得到二维码

		Bitmap roundedCornerBitmap = getRoundedCornerBitmap(heard_bit_pic); // 变成圆形

		Bitmap small = small(roundedCornerBitmap, bmQr);// 图片缩小

		Bitmap conformBitmap1 = toConformBitmapHeart(bmQr, small);// 头像合进二维码

		/// ***
		Bitmap conformBitmap = toConformBitmap(bmg, conformBitmap1); // 带头像的二维码合成进大图片
		// img_shop.setImageBitmap(conformBitmap);

		File fileDirec = new File(YConstance.saveInvitePath);
		if (!fileDirec.exists()) {
			fileDirec.mkdir();
		}
		File[] listFiles = new File(YConstance.saveInvitePath).listFiles();
		if (listFiles.length != 0) {
			LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
			for (File file : listFiles) {
				file.delete();
			}
		}

		// YConstance.savePicPath; "/sdcard/share_pic.png" "/sdcard/yssjaa/";
		String file = YConstance.saveInvitePath + MD5Tools.md5(String.valueOf(9)) + ".png"; // 图片

		FileOutputStream bigOutputStream = null;
		try {
			bigOutputStream = new FileOutputStream(file);
			conformBitmap.compress(Bitmap.CompressFormat.PNG, 100, bigOutputStream);// 把数据写入文件
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				bigOutputStream.flush();
				bigOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	///
	private void weixin_save() {
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();// 屏幕宽度
		int height = wm.getDefaultDisplay().getHeight();

		Bitmap bmQr = QRCreateUtil.createQrImage(link, width / 3, width / 3); // 得到二维码

		////

		////

		Bitmap roundedCornerBitmap = getRoundedCornerBitmap(heard_bit_pic); // 变成圆形

		Bitmap small = small(roundedCornerBitmap, bmQr);// 图片缩小

		Bitmap conformBitmap1 = toConformBitmapHeart(bmQr, small);// 头像合进二维码

		/// ***
		Bitmap conformBitmap = toConformBitmap(bmg, conformBitmap1); // 带头像的二维码合成进大图片
		// img_shop.setImageBitmap(conformBitmap);

		File fileDirec = new File(YConstance.saveInviteWinPath);
		if (!fileDirec.exists()) {
			fileDirec.mkdir();
		}
		File[] listFiles = new File(YConstance.saveInviteWinPath).listFiles();
		if (listFiles.length != 0) {
			LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
			for (File file : listFiles) {
				file.delete();
			}
		}

		String file = YConstance.saveInviteWinPath + MD5Tools.md5(String.valueOf(9)) + ".png"; // 图片

		FileOutputStream bigOutputStream = null;

		try {
			bigOutputStream = new FileOutputStream(file);
			conformBitmap.compress(Bitmap.CompressFormat.PNG, 100, bigOutputStream);// 把数据写入文件

			ToastUtil.showShortText(InviteFriendActivity.this, "分享加载中，稍等哟~");
			wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getInviteImage());

			onceShare3(wXinShareIntent, "微信");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				bigOutputStream.flush();
				bigOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
