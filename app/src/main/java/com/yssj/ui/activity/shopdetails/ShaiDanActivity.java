package com.yssj.ui.activity.shopdetails;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.PubImageIndiana;
import com.yssj.custom.view.PubImageIndiana.onDeteleImgLintener;
import com.yssj.entity.ReturnInfo;
import com.yssj.huanxin.activity.BaseActivity;
import com.yssj.model.ComModel2;
import com.yssj.ui.base.BasicActivity;
import com.yssj.upyun.UpYunException;
import com.yssj.upyun.UpYunUtils;
import com.yssj.upyun.Uploader;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ToastUtil;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ShaiDanActivity extends BasicActivity implements
		onDeteleImgLintener, OnClickListener {
	private TextView mTitle;
	private EditText et_content;// 获奖感言
	private LinearLayout container;// 照片容器
	private ImageView mAddPicture;// 添加照片按钮
	private TextView mSubmit;// 提交评价
	private TextView mNotice;
	
	private LinearLayout img_back,root;
	private static final int RESULT_OK = -1;
	private static int RESULT_LOAD_IMAGE = 3;
	private static int RESULT_LOAD_PICTURE = 4;
	private String fileImageName;
	private String mPicString;
	private String content = "";

	private int screenWidth, screenHeight;

	private List<Bitmap> listBitmap = new ArrayList<Bitmap>();// 上传的图片集合
	private ArrayList<String> listPicPath = new ArrayList<String>();// 上传的图片地址集合
	private List<String> listContent = new ArrayList<String>();// 描述的内容
	private HashMap<String, Object> map = new HashMap<String, Object>();
	private String shop_code;
	private String shop_name;
	private String issue_code;
	private String in_code;
	private String TAG="ShaiDanActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_indiana_shaidan);
		LinearLayout root =(LinearLayout) findViewById(R.id.root);
		root.setBackgroundColor(Color.WHITE);
		
		WindowManager wm = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
		screenHeight = wm.getDefaultDisplay().getHeight();
		initView();
		shop_code=getIntent().getStringExtra("shop_code");
		shop_name=getIntent().getStringExtra("shop_name");
		issue_code=getIntent().getStringExtra("issue_code");
		in_code=getIntent().getStringExtra("in_code");
	}

	private void initView() {
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		mTitle = (TextView) findViewById(R.id.tvTitle_base);
		mTitle.setText("评价订单");
		container = (LinearLayout) findViewById(R.id.indiana_ll_container);
		et_content = (EditText) findViewById(R.id.indiana_et_content);
		mAddPicture = (ImageView) findViewById(R.id.indiana_add_picture);
		mAddPicture.getLayoutParams().height = (screenWidth - DP2SPUtil.dp2px(
				ShaiDanActivity.this, 50)) / 4;
		mAddPicture.getLayoutParams().width = (screenWidth - DP2SPUtil.dp2px(
				ShaiDanActivity.this, 50)) / 4;
		mAddPicture.setOnClickListener(this);
		mSubmit = (TextView) findViewById(R.id.indiana_tv_submit);
		mSubmit.setOnClickListener(this);
		mNotice = (TextView) findViewById(R.id.indiana_tv_notice);
	}

	// TODO:
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back: // 返回
			this.finish();
			break;
		case R.id.indiana_add_picture: // 选择图片
			if (listPicPath.size() == 3) {
				ToastUtil.showShortText(this, "亲，最多只能选择三张图片哦。");
				return;
			}
			doPickPhotoAction();
			// Intent intent=new
			// Intent(getActivity(),ApplyPlatformActivity.class);
			// intent.putStringArrayListExtra("listpicPath", listPicPath);
			break;
		case R.id.indiana_tv_submit:
			submit(v);
			break;
		default:
			break;
		}
	}

//	public HashMap<String, Object> getData() {
//		content = et_content.getText().toString();
//		map.put("content", content);
//		map.put("listPicPath", listPicPath);
//		return map;
//	}

	/**
	 * 获取相片信息
	 */
	private void doPickPhotoAction() {
		final Context dialogContext = new ContextThemeWrapper(this,
				android.R.style.Theme_Light); // R.style.Dialog
		String[] choices;
		choices = new String[2];
		choices[0] = "拍照"; // 拍照
		choices[1] = "从相册中选择"; // 从相册中选择
		final ListAdapter adapter = new ArrayAdapter<String>(dialogContext,
				android.R.layout.simple_list_item_1, choices);
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				dialogContext);
		builder.setTitle("");
		builder.setSingleChoiceItems(adapter, -1,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switch (which) {
						case 0: {
							String status = Environment
									.getExternalStorageState();
							if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
								doTakePhoto();
							} else {
								ToastUtil.showLongText(ShaiDanActivity.this,
										"无内存卡");
							}
							break;
						}
						case 1:
							doPickPhotoFromGallery();
							break;
						}
					}
				});
		builder.create().show();
	}

	/**
	 * 拍照获取图片
	 * 
	 */
	protected void doTakePhoto() {
		try {
			File mCurrentPhotoFile = new File(YConstance.saveUploadPicPath);
			if (!mCurrentPhotoFile.exists()) {
				boolean iscreat = mCurrentPhotoFile.mkdirs();// 创建照片的存储目录
			}

			fileImageName = System.currentTimeMillis() + "";

			mCurrentPhotoFile = new File(mCurrentPhotoFile, fileImageName);
			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
			startActivityForResult(intent, RESULT_LOAD_PICTURE);
		} catch (ActivityNotFoundException e) {
			ToastUtil.showLongText(ShaiDanActivity.this,
					"photoPickerNotFoundText");
		}
	}

	public static Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	private void addImageView(List<Bitmap> listBitmap) {
		container.removeAllViews();
		for (int i = 0; i < listBitmap.size(); i++) {
			PubImageIndiana img = new PubImageIndiana(
					ShaiDanActivity.this,
					(com.yssj.custom.view.PubImageIndiana.onDeteleImgLintener) this);
			img.setTag(i);
			img.setBitmap(listBitmap.get(i));
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT); // , 1是可选写的
			lp.setMargins(DP2SPUtil.dp2px(ShaiDanActivity.this, 10), 0, 0, 0);
			img.setLayoutParams(lp);
			container.addView(img);
			// ImageView imageView = new ImageView(context);
			// imageView.setLayoutParams(new LayoutParams(screenWidth / 3,
			// LayoutParams.WRAP_CONTENT));
			// imageView.setAdjustViewBounds(true);
			// imageView.setImageBitmap(listBitmap.get(i));
			// imageView.setScaleType(ScaleType.FIT_XY);
			// container.addView(imageView);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			mNotice.setVisibility(View.GONE);
			if (requestCode == RESULT_LOAD_IMAGE) {

				// BitmapFactory bf = BitmapFactory.decodeFile(pathName)
				// add_qx_pic.set

				final Uri originalUri = data.getData(); // 获得图片的uri
				String path;
				if (originalUri.getScheme().equals("content")) {
					path = getRealPathFromURI(originalUri);
				} else {
					path = originalUri.getPath();
				}
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap bm = BitmapFactory.decodeFile(path, options);
				listBitmap.add(bm);
				listPicPath.add(path);
				// Intent intent=new
				// Intent(getActivity(),ApplyPlatformActivity.class);
				// intent.putStringArrayListExtra("listpicPath", listPicPath);
//				System.out
//						.println("1***********************************************"
//								+ path);

				addImageView(listBitmap);
				// new_parklot_pic_path = path;
				// add_parklot_pic.setImageBitmap(bm);
				// add_parklot_pic.setScaleType(ScaleType.FIT_XY);
				// newParkLotPicSize = new File(new_parklot_pic_path).length();

			} else if (requestCode == RESULT_LOAD_PICTURE) {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				String path = YConstance.saveUploadPicPath + fileImageName;
				Bitmap bm = BitmapFactory.decodeFile(path, options);
				listBitmap.add(bm);
				addImageView(listBitmap);
				listPicPath.add(path);
				// Intent intent=new
				// Intent(getActivity(),ApplyPlatformActivity.class);
				// intent.putStringArrayListExtra("listpicPath", listPicPath);
//				System.out
//						.println("2***********************************************"
//								+ path);
			}
		}
	}

	private String getRealPathFromURI(Uri originalUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = this.getContentResolver().query(originalUri, proj,
				null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(cursor
				.getColumnIndex(MediaStore.Images.Media.DATA));
		cursor.close();
		return path;
	}

	protected void doPickPhotoFromGallery() {
		try {
			Intent intent = getPhotoPickIntent();
			startActivityForResult(intent, RESULT_LOAD_IMAGE);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Intent getPhotoPickIntent() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		return intent;
	}

	@Override
	public void deleteImg(int index) {
		listBitmap.remove(index);
		addImageView(listBitmap);
		listPicPath.remove(index);
		if (listBitmap != null && listBitmap.size() == 0) {
			mNotice.setVisibility(View.VISIBLE);
		}
	}

	/***
	 * 提交图片信息，提交文字信息
	 */
	private void submit(final View v) {
		content = et_content.getText().toString();
		// listPicPaths.clear();
		// map = f.getData();
		// content = (String) map.get("content");
		// listPicPath = (List<String>) map.get("listPicPath");
		if (TextUtils.isEmpty(content) || content.equals("")) {
			ToastUtil.showShortText(this, "获奖感言不能为空");
			return;
		}
//		if (content.length() > 300) {
//			ToastUtil.showShortText(this, "获奖感言不能超过三百个字");
//			return;
//		}

		if (listPicPath == null || listPicPath.isEmpty()
				|| listPicPath.size() == 0) {
//			 commentRequest(v);
			ToastUtil.showShortText(this, "上传照片不能为空");

			return;
		}

		new SAsyncTask<Void, Void, Void>(ShaiDanActivity.this, v, R.string.wait) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected Void doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < listPicPath.size(); i++) {
					try {
						// 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
//						String SAVE_KEY = File.separator + "returnShop"
//								+ File.separator + System.currentTimeMillis()
//								+ ".jpg";

						String SAVE_KEY ="shareOrder"+File.separator+issue_code+"-"+(i+1)+".jpg";
						LogYiFu.e(TAG, "SAVE_KEY"+SAVE_KEY);
						// 取得base64编码后的policy
						String policy = UpYunUtils.makePolicy(SAVE_KEY,
								Uploader.EXPIRATION, Uploader.BUCKET);

						// 根据表单api签名密钥对policy进行签名
						// 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
						String signature = UpYunUtils.signature(policy + "&"
								+ Uploader.TEST_API_KEY);

						// 上传文件到对应的bucket中去。
						String string = Uploader.upload(policy, signature,
								Uploader.BUCKET, listPicPath.get(i));
						// aa = Integer.valueOf(params[1]);
						sb.append(File.separator+issue_code+"-"+(i+1)+".jpg");
						if (i != listPicPath.size()-1) {
							sb.append(",");
						}
					} catch (UpYunException e) {
						e.printStackTrace();
					}
				}
				mPicString = sb.toString();
//				System.out.println("___//___" + mPicString);
				return super.doInBackground(context, params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result,
					Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null) {
					commentRequest(v);
				}
			}

		}.execute();

	}

	private void commentRequest(View v) {
		new SAsyncTask<Void, Void, ReturnInfo>(this, v, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				// method stub
				/*
				 * StringBuffer sbPic = new StringBuffer(); for (int i = 0; i <
				 * listUploadedUrl.size(); i++) {
				 * sbPic.append(listUploadedUrl.get(i).split("/")[2]); if (i !=
				 * listUploadedUrl.size() - 1) { sbPic.append(","); } }
				 */
				// TODO:returnShop.id=1
				return ComModel2.addIndianaShaidan(context, content,
						mPicString, shop_code, shop_name,in_code,issue_code);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e) {
					if (result != null && "1".equals(result.getStatus())) {
						ToastUtil.showShortText(context, result.getMessage());
						// Intent intent = new Intent(ShaiDanActivity.this,
						// ApplyPlatformFinishActivity.class);
						// startActivity(intent);
						// if (null != OrderDetailsActivity.instance) {
						// OrderDetailsActivity.instance.finish();
						// }
						finish();
					} else {
						ToastUtil.showShortText(context, result.getMessage());
					}
				}
			}

		}.execute();
	}
}
