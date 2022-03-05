package com.yssj.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.DeviceConfig;
import com.yssj.Constants;
import com.yssj.YConstance;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.HorizontalListView;
import com.yssj.data.DBService;
import com.yssj.entity.MyToggleButton;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.adpter.ChoicenessAdapter;
import com.yssj.ui.fragment.HomePageFragment;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

/****
 * 精选分享
 * 
 * @author Administrator
 * 
 */
public class ChoicenessDialog extends Dialog implements OnClickListener {
	private ImageView icon_close;
	private Context context;
	private HorizontalListView listView;
	private EditText edit_message;

	private String message = "";

	private Button bt_sendmessage;
	private Button bt_exit;

	private MyToggleButton weixin;
	private MyToggleButton weibo;
	private MyToggleButton qq;
	private String location;

	private String tieZiID; //帖子的ID

	public static ArrayList<HashMap<String, String>> arrayListForEveryGridView2 = new ArrayList<HashMap<String, String>>();

	// private ArrayList<HashMap<String, String>> subList;
	private String messageSub;

	// 实现对微信朋友圈的分享：
	private Intent wXinShareIntent;

	private Intent qqShareIntent;
	private Intent weiBoShareIntent;
	private int i;
	protected String h5Link = "";
	private String downLoadPic;

	public ChoicenessDialog(Context context, int style, int i) {
		super(context, style);
		setCanceledOnTouchOutside(true);
		this.context = context;
		this.i = i;
	}
	@Override
	public void show() {
		super.show();
		TongJiUtils.TongJi(context, 14+"");
		LogYiFu.e("TongJiNew", 14+"");
	}
	@Override
	public void dismiss() {
		super.dismiss();
		TongJiUtils.TongJi(context, 114+"");
		LogYiFu.e("TongJiNew", 114+"");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_choiceness);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);

		icon_close = (ImageView) findViewById(R.id.icon_close);
		listView = (HorizontalListView) findViewById(R.id.no_gv);
		edit_message = (EditText) findViewById(R.id.edit_message);
		bt_sendmessage = (Button) findViewById(R.id.bt_sendmessage);
		bt_exit = (Button) findViewById(R.id.bt_exit);
		weixin = (MyToggleButton) findViewById(R.id.weixin);
		qq = (MyToggleButton) findViewById(R.id.qq);
		weibo = (MyToggleButton) findViewById(R.id.weibo);
		setCancelable(false);

		try {
			// 是否安装了QQ
			if (!DeviceConfig.isAppInstalled("com.tencent.mobileqq", context)) {
				qq.setVisibility(View.GONE);
			}
		} catch (Exception e) {
		}

		try {

		} catch (Exception e) {
			// // 是否安装了微信
			if (!WXcheckUtil.isWeChatAppInstalled(context)) {
				weixin.setVisibility(View.GONE);
			}

		}
		bt_sendmessage.setOnClickListener(this);
		edit_message.setOnClickListener(this);
		icon_close.setOnClickListener(this);
		weixin.setOnClickListener(this);
		qq.setOnClickListener(this);
		weibo.setOnClickListener(this);
		bt_exit.setOnClickListener(this);
		if (i == 1) {
			bt_sendmessage.setText("分享到SHOW社区");
		} else {
			bt_sendmessage.setText("发布到朋友圈");
			bt_exit.setVisibility(View.INVISIBLE);
			bt_exit.setClickable(false);

		}
		int number = new Random().nextInt(13) + 1;
		switch (number) {
		case 1:
			message = "这是我喜欢的风格，你们觉得好看咩~";
			break;
		case 2:
			message = "今天挑选的心爱款式，觉得好看就点个赞吧~";
			break;
		case 3:
			message = "这几件我觉得挺不错的，大家说应该搭什么好呢~";
			break;
		case 4:
			message = "看起来好时尚哦~好想试穿怎么办？";
			break;
		case 5:
			message = "很用心挑出来的呢~来看看我今日的分享吧！";
			break;
		case 6:
			message = "适合小公举们的漂亮美衣，太好看了~自带仙气，送给你们~";
			break;
		case 7:
			message = "今天的上新美衣哦，上身效果一级棒！";
			break;
		case 8:
			message = "推荐几款最in新品，哪款是你的菜呢？";
			break;
		case 9:
			message = "这几款气质美衣，上身超显瘦！";
			break;
		case 10:
			message = "分享几款流行的穿搭，让你变身街拍达人！赶紧学起来吧~";
			break;
		case 11:
			message = "搭配指南，从此告别早上不知道穿什么~";
			break;
		case 12:
			message = "推荐几款流行单品给你，超低价格让你穿出时尚哦~";
			break;
		case 13:
			message = "大爱这些单品！希望你们也一样喜欢~ ";
			break;

		default:
			break;
		}
		edit_message.setHint(message);

		// 查询城市和生日
		new SAsyncTask<Void, Void, UserInfo>((FragmentActivity) context, R.string.wait) {

			@Override
			protected UserInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
				return ComModel.queryUserInfo(context);
				// return YCache.getCacheUser(context);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, UserInfo result) {
				super.onPostExecute(context, result);
				try {
					location = DBService.getIntance().queryAreaNameById(Integer.parseInt(result.getProvince()))
							+ DBService.getIntance().queryAreaNameById(Integer.parseInt(result.getCity()));
				} catch (NumberFormatException e) {

				}

			}

		}.execute();

		queryData();

	}

	public static ChoicenessAdapter adapter;

	private void queryData() {
		new SAsyncTask<Void, Void, ArrayList<HashMap<String, String>>>((FragmentActivity) context, 0) {

			@Override
			protected ArrayList<HashMap<String, String>> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				return ModQingfeng.getJinPintuijianList(context);
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context, ArrayList<HashMap<String, String>> result,
					Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null && result != null) {
					if (result.size() != 0) {

						HashMap<String, String> myChoice = new HashMap<String, String>();
						ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
						// 如果商品多余9个就取前9个
						if (result.size() > 9) {
							for (int i = 0; i < 8; i++) {
								list.add(result.get(i));
							}
							result = list;
						}
						// 占用第一个位置用来放--自己选
						myChoice.put("show_pic", "0");
						result.add(0, myChoice);
						arrayListForEveryGridView2 = result;
						adapter = new ChoicenessAdapter(context);
						listView.setAdapter(adapter);

					}

				}

			}

		}.execute();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.icon_close:
			arrayListForEveryGridView2.clear();
			this.dismiss();
			// HomePageFragment.cardRootView.setVisibility(View.GONE);
			HomePageFragment.hideCardView();
			break;
		case R.id.edit_message:
			edit_message.setText(message);
			edit_message.setSelection(edit_message.getText().length());
			break;
		case R.id.bt_exit:// 退出浏览
			this.dismiss();
			// HomePageFragment.cardRootView.setVisibility(View.GONE);
			HomePageFragment.hideCardView();
			break;
		case R.id.bt_sendmessage:
			messageSub = edit_message.getText() + "";
			messageSub = (edit_message.getText() + "").length() == 0 ? message : edit_message.getText() + "";

			if (arrayListForEveryGridView2.size() == 1) {
				ToastUtil.showShortText(context, "亲，你没有选择商品喔！");
				return;
			}

			// 调接口发布到密友圈
			shareToMyFrends();

			// // 分享到QQ
			// if (qq.isChecked()) {
			// ShareUtil.addQQQZonePlatform(context);
			// createSharePic("https://www.baidu.com", "", 1);
			// }
			// // 分享的微信朋友圈
			// if (weixin.isChecked()) {
			// ShareUtil.addWXPlatform(context);
			// createSharePic("https://www.baidu.com", "", 2);
			// }
			//
			// // 分享到微博
			// if (weibo.isChecked()) {
			// createSharePic("https://www.baidu.com", "", 3);
			// }

			break;

		default:
			break;
		}

	}

	private void shareToMyFrends() {

		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) context, 0) {
			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
					throws Exception {

				// subList = arrayListForEveryGridView2;

				// subList.remove(0);// 去掉自己选
				StringBuffer sb = new StringBuffer();
				for (int i = 1; i < arrayListForEveryGridView2.size(); i++) {
					HashMap<String, String> map = arrayListForEveryGridView2.get(i);
					sb.append(map.get("shop_code"));
					if (i != arrayListForEveryGridView2.size() - 1) {
						sb.append(",");
					}
				}
				String shopcodes = sb.toString();
				LogYiFu.e("arrayListForEveryGridView2", arrayListForEveryGridView2 + "");

				// 发帖
				// * title 标题
				// * content 文本
				// * pics 图片
				// * tags 标签
				// * location 位置
				// * theme_type 类型 1 精选推荐，2 穿搭，3 普通话题
				// * shop_codes 商品编号
				// * 当theme_type=1时shop_codes ,
				// * 当theme_type=2时type1,type2,supp_label_id,tag_info
				// *customTags 自定义标签，逗号隔开
				return ComModel2.createFaTieJingxuan(context, "", messageSub, "", "", location, "1", shopcodes);
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null && result != null) {
					if (result.size() != 0) {

						h5Link = YUrl.YSS_URL_ANDROID_H5 + "/views/topic/detail.html?theme_id="
								+ result.get("theme_id")+"&realm="+YCache.getCacheUser(context).getUser_id();

						tieZiID = result.get("theme_id");

						// 分享到QQ
						if (qq.isChecked()) {
							ShareUtil.addQQQZonePlatform(context);
							createSharePic(h5Link, "", 1);
						}
						// 分享的微信朋友圈---分享到小程序
						if (weixin.isChecked()) {
							ShareUtil.addWXPlatform(context);
							createSharePic(h5Link, "", 2);
						}

						// 分享到微博
						if (weibo.isChecked()) {
							createSharePic(h5Link, "", 3);
						}

						ToastUtil.showShortText(context, "蜜友圈发布成功");
						CommonUtils.finishActivity(MainMenuActivity.instances);

						Intent intent2 = new Intent((Activity) context, MainMenuActivity.class);
						intent2.putExtra("toFriends", "toFriends");
						context.startActivity(intent2);
						// HomePageFragment.cardRootView.setVisibility(View.GONE);
						HomePageFragment.hideCardView();
						dismiss();

					}

				}

			}

		}.execute();

	}

	private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);

	public void performShare(SHARE_MEDIA platform, final Intent intent) {
		mController.postShare(context, platform, new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
				String showText = platform.toString();
				if (eCode == StatusCode.ST_CODE_SUCCESSED) {

				} else {

				}
			}

		});
	}

	private Bitmap bmBg;
	private File file;
	// i: 1-QQ 2-微信 3-微博

	private void createSharePic(final String link, final String picPath, final int i) {
		new SAsyncTask<Void, Void, Void>((FragmentActivity) context, R.string.wait) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected Void doInBackground(FragmentActivity context, Void... params) throws Exception {
				// TODO Auto-generated method stub

				// String downLoadPic = subList.get(0).get("show_pic")+"";
				 downLoadPic = arrayListForEveryGridView2.get(1).get("shop_code").substring(1, 4) + "/"
						+ arrayListForEveryGridView2.get(1).get("shop_code").toString() + "/"
						+ arrayListForEveryGridView2.get(1).get("show_pic") + "";
				bmBg = downloadPic(downLoadPic);
				QRCreateUtil.saveBitmap(bmBg, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存图片
				return super.doInBackground(context, params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if (null == e) {
					file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");
					// share(file); 创建图片完毕后分享到微信

					share(file, link, i);

				}
			}

		}.execute();
	}

	private Bitmap downloadPic(String picPath) {
		try {
			URL url = new URL(YUrl.imgurl + picPath);
			// https://yssj-real-test.b0.upaiyun.com/collocationShop/2016-07-13/Pg3holtx.jpg
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			int contentLength = con.getContentLength();
			// System.out.println("长度 :" + contentLength);
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[8192];
			// 读取到的数据长度
			int len;
			BitmapDrawable bmpDraw = new BitmapDrawable(is);

			// 完毕，关闭所有链接
			is.close();
			return bmpDraw.getBitmap();
		} catch (Exception e) {
			LogYiFu.e("TAG", "下载失败");

			e.printStackTrace();
			return null;
		}

	}

	private void share(File file, String link, int i) {
		UMImage umImage;
		switch (i) {
		case 1:// qq空间
			qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
			if (file == null) {
				ToastUtil.showShortText(context, "您的网络状态不太好哦~~");
				return;
			}
			umImage = new UMImage(context, file);
			ShareUtil.setShareContentFriend(context, umImage, messageSub, link, "");
			performShare(SHARE_MEDIA.QZONE, qqShareIntent);

			// ShareUtil.setShareContent(mActivity, umImage,
			// "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
			// performShare(SHARE_MEDIA.QZONE, qqShareIntent);

			break;
		case 2:// 微信朋友圈
				// 分享到微信朋友圈
			wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
			SharedPreferencesUtil.saveStringData(context, "messageSubSub", messageSub);
			if (file == null) {
				ToastUtil.showShortText(context, "您的网络状态不太好哦~~");
				return;
			}


			//改为分享小程序
			String wxMiniPathdUO = "/pages/shouye/detail/sweetFriendsDetail/friendsDetail?theme_id=" + tieZiID +
					"&isShareFlag=true&user_id=" + YCache.getCacheUser(context).getUser_id();
			//分享到微信统一分享小程序
			WXminiAPPShareUtil.shareToWXminiAPP(context, downLoadPic+"!280", messageSub, wxMiniPathdUO, false);






//			umImage = new UMImage(context, file);
//			ShareUtil.setShareContent(context, umImage, messageSub, link);
//			performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
			break;
		case 3:// 微博
				// mController.getConfig().setSsoHandler(new SinaSsoHandler());
				// weiBoShareIntent =
				// ShareUtil.shareMultiplePictureToSina(ShareUtil.getImage());
				// ShareUtil.setShareContent(context, new UMImage(context,
				// R.drawable.wodexihao_fengge_rixi), messageSub,
				// "http://www.cnblogs.com/wt616/archive/2011/06/20/2085368.html");
				//
				// performShare(SHARE_MEDIA.SINA, weiBoShareIntent);

			// 通过网页分享到微博
			SinaShareContent sinaShareContent = new SinaShareContent();
			sinaShareContent.setShareContent(messageSub + "\t" + h5Link);
			sinaShareContent.setShareImage(new UMImage(context, R.drawable.wodexihao_fengge_rixi));
			mController.setShareMedia(sinaShareContent);
			performShare(SHARE_MEDIA.SINA, weiBoShareIntent);

			// 直接跳转到微博客户端分享---还没弄好

			// weiBoShareIntent =
			// ShareUtil.shareMultiplePictureToSina(ShareUtil.getImage());
			// ShareUtil.setSinaShareContent(mController, context, new
			// UMImage(context, R.drawable.wodexihao_fengge_rixi),
			// messageSub,
			// "http://www.cnblogs.com/wt616/archive/2011/06/20/2085368.html",
			// true);
			// context.startActivity(weiBoShareIntent);
			break;

		default:
			break;
		}

	}

}