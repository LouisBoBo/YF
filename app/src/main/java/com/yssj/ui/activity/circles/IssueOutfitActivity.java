package com.yssj.ui.activity.circles;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
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
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.FlowLayout;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.MyGridView;
import com.yssj.data.DBService;
import com.yssj.data.YDBHelper;
import com.yssj.entity.MyToggleButton;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.picselect.ImagePublishAdapter;
import com.yssj.ui.activity.picselect.PicSelectActivity;
//import com.yssj.ui.activity.picture.ImageBucketChooseActivity;
//import com.yssj.ui.activity.picture.ImageItem;
//import com.yssj.ui.activity.picture.ImagePublishAdapter;
import com.yssj.ui.activity.shopdetails.ShaiDanActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.upyun.UpYunException;
import com.yssj.upyun.UpYunUtils;
import com.yssj.upyun.Uploader;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.YCache;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class IssueOutfitActivity extends BasicActivity implements OnClickListener, TextWatcher {
	private Context mContext;
	private EditText mEtTitle;// 标题
	private MyGridView mGridView;
	private ImagePublishAdapter mAdapter;
	public List<String> mDataList = new ArrayList<String>();
	private LinearLayout mBack;
	private TextView mTvIssue;
	private EditText mEtContent;// 内容
	private String mPicString;
	private String mBrandString = "";
	private String mStyleString = "";
	private FlowLayout mBrand;// 品牌
	private FlowLayout mStytle;// 风格
	private RelativeLayout mRlSelectClass;// 选择分类
	private String mFristClass = "";
	private String mSecondClass = "";
	private TextView mTvClass;
	private int mBrandPosition = 0;// 品牌点击的位置
	private int mStylePosition = 0;// 风格点击的位置
	private boolean clickFlag = false;// 用来防止连续点击发布按钮
	private String type1;// 一级分类
	private String type2;// 二级分类

	private MyToggleButton weixin;
	private MyToggleButton weibo;
	private MyToggleButton qq;

	private String link = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_issue_outfit);
		mContext = this;
		initView();
		initData();
	}

	private void initView() {
		mBrand = (FlowLayout) findViewById(R.id.issue_outfit_brand);
		mStytle = (FlowLayout) findViewById(R.id.issue_outfit_style);
		mTvClass = (TextView) findViewById(R.id.issue_outfit_tv_class);
		mRlSelectClass = (RelativeLayout) findViewById(R.id.issue_outfit_select_class);
		mRlSelectClass.setOnClickListener(this);
		mEtTitle = (EditText) findViewById(R.id.issue_outfit_et_title);
		mEtContent = (EditText) findViewById(R.id.issue_outfit_et_content);
		mTvIssue = (TextView) findViewById(R.id.issue_outfit_tv_issue);
		mTvIssue.setOnClickListener(this);
		mBack = (LinearLayout) findViewById(R.id.img_back);
		mBack.setOnClickListener(this);
		mGridView = (MyGridView) findViewById(R.id.issue_gridview);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mAdapter = new ImagePublishAdapter(this, mDataList);
		mGridView.setAdapter(mAdapter);

		weixin = (MyToggleButton) findViewById(R.id.weixin);
		qq = (MyToggleButton) findViewById(R.id.qq);
		weibo = (MyToggleButton) findViewById(R.id.weibo);

		try {
			// 是否安装了QQ
			if (!DeviceConfig.isAppInstalled("com.tencent.mobileqq", this)) {
				qq.setVisibility(View.GONE);
			}
		} catch (Exception e) {
		}

		try {
			// // 是否安装了微信
			if (!WXcheckUtil.isWeChatAppInstalled(this)) {
				weixin.setVisibility(View.GONE);
			}
		} catch (Exception e) {
		}

		mGridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == getDataSize()) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(mEtContent.getWindowToken(), 0); // 强制隐藏键盘
					Intent intent = new Intent(mContext, PicSelectActivity.class);
					intent.putExtra("can_add_image_size", getAvailableSize());
					startActivityForResult(intent, 10000);
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 10000:
			if (resultCode == 30001) {
				List<String> incomingDataList = (List<String>) data.getSerializableExtra("image_listss");
				if (incomingDataList != null) {
					mDataList.addAll(incomingDataList);
				}
				mAdapter.notifyDataSetChanged();
			}
			break;
		case 10001:
			if (resultCode == 30003) {
				// "上衣", "裤子", "裙子", "套装"

				String type = data.getStringExtra("type");
				type1 = "" + type;
				type2 = data.getStringExtra("type2");
				mFristClass = data.getStringExtra("type1_name");
				// if (type == 2) {
				// mFristClass = "上衣";
				// } else if (type == 4) {
				// mFristClass = "裤子";
				// } else if (type == 3) {
				// mFristClass = "裙子";
				// } else if (type == 7) {
				// mFristClass = "套装";
				// }
				mSecondClass = data.getStringExtra("class_name");
				mTvClass.setText(mFristClass + "-" + mSecondClass);
			}
			break;
		default:
			break;
		}
	}

	private int getDataSize() {
		return mDataList == null ? 0 : mDataList.size();
	}

	private void initData() {
		// getTempFromPref();
		// List<ImageItem> incomingDataList = (List<ImageItem>)
		// getIntent().getSerializableExtra("image_list");
		// if (incomingDataList != null) {
		// mDataList.addAll(incomingDataList);
		// }

		YDBHelper dbHelp = new YDBHelper(this);
		String sql = "select * from supp_label order by _id";
		final List<HashMap<String, String>> listSLevel = dbHelp.query(sql);
		if (listSLevel != null) {
			// HashMap<String, String> map = new HashMap<String, String>();
			// map.put("name", "其他");
			// listSLevel.add(map);
			for (int i = 0; i < listSLevel.size(); i++) {
				final TextView textView = new TextView(this);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
						DP2SPUtil.dp2px(mContext, 26));
				params.setMargins(DP2SPUtil.dp2px(mContext, 10), DP2SPUtil.dp2px(mContext, 10), 0, 0);
				textView.setTextSize(14);
				final String str = listSLevel.get(i).get("name");
				textView.setText(str);
				textView.setPadding(DP2SPUtil.dp2px(mContext, 6), 0, DP2SPUtil.dp2px(mContext, 6), 0);
				textView.setGravity(Gravity.CENTER);
				textView.setTextColor(Color.parseColor("#c5c5c5"));
				textView.setBackgroundResource(R.drawable.shape_gray_brand);
				mBrand.addView(textView, params);
				final int position = i;
				textView.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						TextView childAt = (TextView) mBrand.getChildAt(mBrandPosition);
						childAt.setTextColor(Color.parseColor("#c5c5c5"));
						childAt.setBackgroundResource(R.drawable.shape_gray_brand);
						mBrandPosition = position;
						textView.setBackgroundResource(R.drawable.shape_red_brand);
						textView.setTextColor(Color.parseColor("#ffffff"));
						mBrandString = listSLevel.get(position).get("_id");
					}
				});
			}
		}
		// 查询风格数据
		final List<HashMap<String, String>> listStyle = dbHelp
				.query("select * from tag_info where p_id=2 and is_show=1 order by sequence");
		if (listStyle != null) {

			for (int i = 0; i < listStyle.size(); i++) {
				final TextView textView = new TextView(this);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
						DP2SPUtil.dp2px(mContext, 26));
				params.setMargins(DP2SPUtil.dp2px(mContext, 10), DP2SPUtil.dp2px(mContext, 10), 0, 0);
				textView.setTextSize(14);
				final String str = listStyle.get(i).get("attr_name");
				textView.setText(str);
				textView.setPadding(DP2SPUtil.dp2px(mContext, 6), 0, DP2SPUtil.dp2px(mContext, 6), 0);
				textView.setGravity(Gravity.CENTER);
				textView.setTextColor(Color.parseColor("#c5c5c5"));
				textView.setBackgroundResource(R.drawable.shape_gray_brand);
				mStytle.addView(textView, params);
				final int position = i;
				textView.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						TextView childAt = (TextView) mStytle.getChildAt(mStylePosition);
						childAt.setTextColor(Color.parseColor("#c5c5c5"));
						childAt.setBackgroundResource(R.drawable.shape_gray_brand);
						mStylePosition = position;
						textView.setBackgroundResource(R.drawable.shape_red_brand);
						textView.setTextColor(Color.parseColor("#ffffff"));
						mStyleString = listStyle.get(position).get("_id");
					}
				});
			}
		}
	}

	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.issue_outfit_tv_issue:
			if (clickFlag) {
				return;
			}
			if ("".equals(mEtTitle.getText().toString().trim())) {
				ToastUtil.showShortText(mContext, "还没有输入标题哦~");
				return;
			}
			if ("".equals(mEtContent.getText().toString().trim())) {
				ToastUtil.showShortText(mContext, "还没有输入描述哦~");
				return;
			}
			if (mEtContent.getText().toString().length() > 10000) {
				ToastUtil.showShortText(mContext, "最多只能输入10000个字哦~");
				return;
			}
			if (mDataList.size() == 0) {
				ToastUtil.showShortText(mContext, "还没有选择图片哦~");
				return;
			}
			if ("".equals(mFristClass)) {
				ToastUtil.showShortText(mContext, "还没有选择穿搭分类哦~");
				return;
			}
			if ("".equals(mStyleString)) {
				ToastUtil.showShortText(mContext, "还没有选择穿搭风格哦~");
				return;
			}
			clickFlag = true;
			issue();
			break;
		case R.id.issue_outfit_select_class:
			Intent intent = new Intent(mContext, FristClassActivity.class);
			startActivityForResult(intent, 10001);
			break;
		default:
			break;
		}

	}

	private void issue() {
		new SAsyncTask<Void, Void, Void>((FragmentActivity) mContext, R.string.wait) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				LoadingDialog.show((FragmentActivity) mContext);
			}

			@Override
			protected Void doInBackground(FragmentActivity context, Void... params) throws Exception {
				StringBuffer sb = new StringBuffer();
				long time = System.currentTimeMillis();
				for (int i = 0; i < mDataList.size(); i++) {
					try {
						Bitmap bitmap = BitmapFactory.decodeFile(mDataList.get(i));
						int width = bitmap.getWidth();
						double widths = Double.parseDouble("" + width);
						int height = bitmap.getHeight();
						double heights = Double.parseDouble("" + height);
						double a = (double) (widths / heights);
						double ratio = (double) (Math.round(a * 10) / 10.0);
						// 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
						// String SAVE_KEY = File.separator + "returnShop"
						// + File.separator + System.currentTimeMillis()
						// + ".jpg";
						// "shareOrder"+File.separator+
						String SAVE_KEY = File.separator + "myq/theme/" + YCache.getCacheUser(mContext).getUser_id()
								+ File.separator + time + "_" + (i);

						if (i == 0) {
							shareLink = File.separator + "myq/theme/" + YCache.getCacheUser(mContext).getUser_id()
									+ File.separator + time + "_" + (i);
						}

						// 取得base64编码后的policy
						String policy = UpYunUtils.makePolicy(SAVE_KEY, Uploader.EXPIRATION, Uploader.BUCKET);

						// 根据表单api签名密钥对policy进行签名
						// 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
						String signature = UpYunUtils.signature(policy + "&" + Uploader.TEST_API_KEY);

						// 上传文件到对应的bucket中去。
						String string = Uploader.upload(policy, signature, Uploader.BUCKET, mDataList.get(i));
						// aa = Integer.valueOf(params[1]);

						sb.append(time + "_" + (i) + ":" + ratio);
						if (i != mDataList.size() - 1) {
							sb.append(",");
						}
					} catch (UpYunException e) {
						clickFlag = false;
						e.printStackTrace();
					}
				}
				mPicString = sb.toString();
				// System.out.println("___//___" + mPicString);
				return super.doInBackground(context, params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result, Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null) {
					getCity();
				} else {
					clickFlag = false;
					LoadingDialog.hide(mContext);
				}
			}

		}.execute();

	}

	public void getCity() {
		// 查询城市和生日
		new SAsyncTask<Void, Void, UserInfo>(this, R.string.wait) {

			@Override
			protected UserInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
				return ComModel.queryUserInfo(context);
				// return YCache.getCacheUser(context);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, UserInfo result) {
				super.onPostExecute(context, result);
				String city = "";
				try {
					city = DBService.getIntance().queryAreaNameById(Integer.parseInt(result.getProvince()))
							+ DBService.getIntance().queryAreaNameById(Integer.parseInt(result.getCity()));
				} catch (NumberFormatException e) {

				}
				commentRequest(city);
			}

		}.execute();
	}

	private void commentRequest(final String location) {
		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mContext, 0) {
			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
					throws Exception {

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
				UserInfo queryUserInfo = ComModel.queryUserInfo(context);

				return ComModel2.createFaTie(mContext, mEtTitle.getText().toString().trim(),
						mEtContent.getText().toString().trim(), mPicString, "", location, "2", "", "", type1, type2,
						mStyleString, mBrandString, "");
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result, e);
				clickFlag = false;
				LoadingDialog.hide(mContext);
				if (e == null && result != null) {
					if (result.size() != 0 && "1".equals(result.get("status"))) {
						ToastUtil.showShortText(mContext, "发布成功");
						CommonUtils.finishActivity(MainMenuActivity.instances);

						Intent intent2 = new Intent((Activity) context, MainMenuActivity.class);
						intent2.putExtra("toFriends", "toFriends");
						context.startActivity(intent2);

						link = YUrl.YSS_URL_ANDROID_H5 + "/views/topic/detail.html?theme_id=" + result.get("theme_id")
								+ "&realm=" + YCache.getCacheUser(mContext).getUser_id();

						// 分享到QQ
						if (qq.isChecked()) {
							ShareUtil.addQQQZonePlatform(mContext);
							if (shareLink.equals("")) {
								// 用户没有上传图片就直接实用APP图标作为分享的图标
								qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
								ShareUtil.setShareContentFriend(mContext,
										new UMImage(mContext, R.drawable.gerenzhongxin_morentouxiang_bg),
										mEtContent.getText().toString(), link, "");
								performShare(SHARE_MEDIA.QZONE, qqShareIntent);
							} else {
								createSharePic(link, "", 1);
							}

						}
						// 分享的微信朋友圈
						if (weixin.isChecked()) {
							ShareUtil.addWXPlatform(mContext);
							if (shareLink.equals("")) {
								// 用户没有上传图片就直接实用APP图标作为分享的图标

								wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
								SharedPreferencesUtil.saveStringData(mContext, "messageSubSub",
										mEtContent.getText().toString());
								ShareUtil.setShareContent(mContext,
										new UMImage(mContext, R.drawable.gerenzhongxin_morentouxiang_bg),
										mEtContent.getText().toString(), link);
								performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);

							} else {
								createSharePic(link, "", 2);
							}
						}

						// 分享到微博
						if (weibo.isChecked()) {
							if (shareLink.equals("")) {
								SinaShareContent sinaShareContent = new SinaShareContent();
								sinaShareContent.setShareContent(mEtContent.getText().toString() + "\t" + link);
								sinaShareContent.setShareImage(
										new UMImage(mContext, R.drawable.gerenzhongxin_morentouxiang_bg));
								mController.setShareMedia(sinaShareContent);
								performShare(SHARE_MEDIA.SINA, weiBoShareIntent);
							} else {
								createSharePic(link, "", 3);
							}
						}

					}

				}

			}

		}.execute();
	}

	private int getAvailableSize() {
		int availSize = 9 - mDataList.size();
		if (availSize >= 0) {
			return availSize;
		}
		return 0;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	private String shareLink = "";

	private Bitmap bmBg;
	private File file;
	// i: 1-QQ 2-微信 3-微博

	private void createSharePic(final String link, final String picPath, final int i) {
		new SAsyncTask<Void, Void, Void>((FragmentActivity) this, R.string.wait) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected Void doInBackground(FragmentActivity context, Void... params) throws Exception {
				// TODO Auto-generated method stub

				// String downLoadPic = subList.get(0).get("show_pic")+"";
				bmBg = downloadPic(shareLink);
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

	private Intent wXinShareIntent;

	private Intent qqShareIntent;
	private Intent weiBoShareIntent;

	private void share(File file, String link, int i) {
		UMImage umImage;
		switch (i) {
		case 1:// qq空间
			qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
			if (file == null) {
				ToastUtil.showShortText(this, "您的网络状态不太好哦~~");
				return;
			}
			umImage = new UMImage(this, file);
			ShareUtil.setShareContentFriend(this, umImage, mEtContent.getText().toString(), link, "");
			performShare(SHARE_MEDIA.QZONE, qqShareIntent);

			// ShareUtil.setShareContent(mActivity, umImage,
			// "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
			// performShare(SHARE_MEDIA.QZONE, qqShareIntent);

			break;
		case 2:// 微信朋友圈
				// 分享到微信朋友圈
			wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
			SharedPreferencesUtil.saveStringData(this, "messageSubSub", mEtContent.getText().toString());
			if (file == null) {
				ToastUtil.showShortText(this, "您的网络状态不太好哦~~");
				return;
			}
			umImage = new UMImage(this, file);
			ShareUtil.setShareContent(this, umImage, mEtContent.getText().toString(), link);
			performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
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
			sinaShareContent.setShareContent(mEtContent.getText().toString() + "\t" + link);
			sinaShareContent.setShareImage(new UMImage(this, file));
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

	private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);

	public void performShare(SHARE_MEDIA platform, final Intent intent) {
		mController.postShare(this, platform, new SnsPostListener() {

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
}
