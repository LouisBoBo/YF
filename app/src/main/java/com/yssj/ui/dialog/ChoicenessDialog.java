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
 * ????????????
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

	private String tieZiID; //?????????ID

	public static ArrayList<HashMap<String, String>> arrayListForEveryGridView2 = new ArrayList<HashMap<String, String>>();

	// private ArrayList<HashMap<String, String>> subList;
	private String messageSub;

	// ????????????????????????????????????
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
			// ???????????????QQ
			if (!DeviceConfig.isAppInstalled("com.tencent.mobileqq", context)) {
				qq.setVisibility(View.GONE);
			}
		} catch (Exception e) {
		}

		try {

		} catch (Exception e) {
			// // ?????????????????????
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
			bt_sendmessage.setText("?????????SHOW??????");
		} else {
			bt_sendmessage.setText("??????????????????");
			bt_exit.setVisibility(View.INVISIBLE);
			bt_exit.setClickable(false);

		}
		int number = new Random().nextInt(13) + 1;
		switch (number) {
		case 1:
			message = "????????????????????????????????????????????????~";
			break;
		case 2:
			message = "?????????????????????????????????????????????????????????~";
			break;
		case 3:
			message = "???????????????????????????????????????????????????????????????~";
			break;
		case 4:
			message = "?????????????????????~????????????????????????";
			break;
		case 5:
			message = "????????????????????????~?????????????????????????????????";
			break;
		case 6:
			message = "????????????????????????????????????????????????~???????????????????????????~";
			break;
		case 7:
			message = "???????????????????????????????????????????????????";
			break;
		case 8:
			message = "???????????????in?????????????????????????????????";
			break;
		case 9:
			message = "??????????????????????????????????????????";
			break;
		case 10:
			message = "???????????????????????????????????????????????????????????????????????????~";
			break;
		case 11:
			message = "???????????????????????????????????????????????????~";
			break;
		case 12:
			message = "??????????????????????????????????????????????????????????????????~";
			break;
		case 13:
			message = "????????????????????????????????????????????????~ ";
			break;

		default:
			break;
		}
		edit_message.setHint(message);

		// ?????????????????????
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
						// ??????????????????9????????????9???
						if (result.size() > 9) {
							for (int i = 0; i < 8; i++) {
								list.add(result.get(i));
							}
							result = list;
						}
						// ??????????????????????????????--?????????
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
		case R.id.bt_exit:// ????????????
			this.dismiss();
			// HomePageFragment.cardRootView.setVisibility(View.GONE);
			HomePageFragment.hideCardView();
			break;
		case R.id.bt_sendmessage:
			messageSub = edit_message.getText() + "";
			messageSub = (edit_message.getText() + "").length() == 0 ? message : edit_message.getText() + "";

			if (arrayListForEveryGridView2.size() == 1) {
				ToastUtil.showShortText(context, "?????????????????????????????????");
				return;
			}

			// ???????????????????????????
			shareToMyFrends();

			// // ?????????QQ
			// if (qq.isChecked()) {
			// ShareUtil.addQQQZonePlatform(context);
			// createSharePic("https://www.baidu.com", "", 1);
			// }
			// // ????????????????????????
			// if (weixin.isChecked()) {
			// ShareUtil.addWXPlatform(context);
			// createSharePic("https://www.baidu.com", "", 2);
			// }
			//
			// // ???????????????
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

				// subList.remove(0);// ???????????????
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

				// ??????
				// * title ??????
				// * content ??????
				// * pics ??????
				// * tags ??????
				// * location ??????
				// * theme_type ?????? 1 ???????????????2 ?????????3 ????????????
				// * shop_codes ????????????
				// * ???theme_type=1???shop_codes ,
				// * ???theme_type=2???type1,type2,supp_label_id,tag_info
				// *customTags ??????????????????????????????
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

						// ?????????QQ
						if (qq.isChecked()) {
							ShareUtil.addQQQZonePlatform(context);
							createSharePic(h5Link, "", 1);
						}
						// ????????????????????????---??????????????????
						if (weixin.isChecked()) {
							ShareUtil.addWXPlatform(context);
							createSharePic(h5Link, "", 2);
						}

						// ???????????????
						if (weibo.isChecked()) {
							createSharePic(h5Link, "", 3);
						}

						ToastUtil.showShortText(context, "?????????????????????");
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
	// i: 1-QQ 2-?????? 3-??????

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
				QRCreateUtil.saveBitmap(bmBg, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// ????????????
				return super.doInBackground(context, params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if (null == e) {
					file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");
					// share(file); ????????????????????????????????????

					share(file, link, i);

				}
			}

		}.execute();
	}

	private Bitmap downloadPic(String picPath) {
		try {
			URL url = new URL(YUrl.imgurl + picPath);
			// https://yssj-real-test.b0.upaiyun.com/collocationShop/2016-07-13/Pg3holtx.jpg
			// ????????????
			URLConnection con = url.openConnection();
			// ?????????????????????
			int contentLength = con.getContentLength();
			// System.out.println("?????? :" + contentLength);
			// ?????????
			InputStream is = con.getInputStream();
			// 1K???????????????
			byte[] bs = new byte[8192];
			// ????????????????????????
			int len;
			BitmapDrawable bmpDraw = new BitmapDrawable(is);

			// ???????????????????????????
			is.close();
			return bmpDraw.getBitmap();
		} catch (Exception e) {
			LogYiFu.e("TAG", "????????????");

			e.printStackTrace();
			return null;
		}

	}

	private void share(File file, String link, int i) {
		UMImage umImage;
		switch (i) {
		case 1:// qq??????
			qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
			if (file == null) {
				ToastUtil.showShortText(context, "??????????????????????????????~~");
				return;
			}
			umImage = new UMImage(context, file);
			ShareUtil.setShareContentFriend(context, umImage, messageSub, link, "");
			performShare(SHARE_MEDIA.QZONE, qqShareIntent);

			// ShareUtil.setShareContent(mActivity, umImage,
			// "????????????????????????????????????????????????????????????????????????~", link);
			// performShare(SHARE_MEDIA.QZONE, qqShareIntent);

			break;
		case 2:// ???????????????
				// ????????????????????????
			wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
			SharedPreferencesUtil.saveStringData(context, "messageSubSub", messageSub);
			if (file == null) {
				ToastUtil.showShortText(context, "??????????????????????????????~~");
				return;
			}


			//?????????????????????
			String wxMiniPathdUO = "/pages/shouye/detail/sweetFriendsDetail/friendsDetail?theme_id=" + tieZiID +
					"&isShareFlag=true&user_id=" + YCache.getCacheUser(context).getUser_id();
			//????????????????????????????????????
			WXminiAPPShareUtil.shareToWXminiAPP(context, downLoadPic+"!280", messageSub, wxMiniPathdUO, false);






//			umImage = new UMImage(context, file);
//			ShareUtil.setShareContent(context, umImage, messageSub, link);
//			performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
			break;
		case 3:// ??????
				// mController.getConfig().setSsoHandler(new SinaSsoHandler());
				// weiBoShareIntent =
				// ShareUtil.shareMultiplePictureToSina(ShareUtil.getImage());
				// ShareUtil.setShareContent(context, new UMImage(context,
				// R.drawable.wodexihao_fengge_rixi), messageSub,
				// "http://www.cnblogs.com/wt616/archive/2011/06/20/2085368.html");
				//
				// performShare(SHARE_MEDIA.SINA, weiBoShareIntent);

			// ???????????????????????????
			SinaShareContent sinaShareContent = new SinaShareContent();
			sinaShareContent.setShareContent(messageSub + "\t" + h5Link);
			sinaShareContent.setShareImage(new UMImage(context, R.drawable.wodexihao_fengge_rixi));
			mController.setShareMedia(sinaShareContent);
			performShare(SHARE_MEDIA.SINA, weiBoShareIntent);

			// ????????????????????????????????????---????????????

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