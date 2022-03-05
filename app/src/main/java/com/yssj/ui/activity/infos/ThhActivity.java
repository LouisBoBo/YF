package com.yssj.ui.activity.infos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.Order;
import com.yssj.entity.OrderShop;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.base.BasicActivity;
import com.yssj.upyun.UpYunException;
import com.yssj.upyun.UpYunUtils;
import com.yssj.upyun.Uploader;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ToastUtil;

/***
 * 退换货
 * 
 * @author Administrator
 * 
 */
public class ThhActivity extends BasicActivity {

	private Spinner sp_apply_service, sp_apply_reason;// 申请服务，申请原因
	private EditText et_apply_content;
	int[] applyServices = { 1, 2, 3 };
	String[] applyServiceStr = { "换货", "退货", "退款" };
	int applyService = 1;// 申请服务

	String[] applyCauses = { "材质/面料与商品不符", "大小尺寸与商品不符", "做工瑕疵(胶水印/两只鞋不对称等)",
			"质量问题(断底/断面/脱胶等)", "颜色/款式/图案与描述不符", "认为是假货", "卖家发错货",
			"尺码拍错/不喜欢/效果不好" };
	String applyCause;// 申请原因
	String applyContent;// 申请说明

	private Order order;

	private static int RESULT_LOAD_IMAGE = 3;
	private static int RESULT_LOAD_PICTURE = 4;

	private String fileImageName;
	private LinearLayout container;
	private int screenWidth, screenHeight;

	private List<Bitmap> listBitmap = new ArrayList<Bitmap>();// 上传的图片集合
	private List<String> listPicPath = new ArrayList<String>();// 上传的图片地址集合
	private List<Boolean> isUploads = new ArrayList<Boolean>();// 是否上传的标识集合
	private List<String> listUploadedUrl = new ArrayList<String>();// 上传图片之后返回的URL

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		order = (Order) getIntent().getSerializableExtra("order");
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
		screenHeight = wm.getDefaultDisplay().getHeight();
		initView();
	}

	private void initView() {
		setContentView(R.layout.thh);
		sp_apply_service = (Spinner) findViewById(R.id.sp_apply_service);
		sp_apply_reason = (Spinner) findViewById(R.id.sp_apply_reason);
		et_apply_content = (EditText) findViewById(R.id.et_apply_content);
		container = (LinearLayout) findViewById(R.id.container);
		findViewById(R.id.choose_pic).setOnClickListener(this);
		findViewById(R.id.submit).setOnClickListener(this);
		initServiceSp();
		initReasonSp();
	}

	/***
	 * 申请服务下拉
	 */
	private void initServiceSp() {
		ArrayAdapter<String> simpleAdapter = new ArrayAdapter<String>(this,
				R.layout.simple_adapter, applyServiceStr);
		sp_apply_service.setAdapter(simpleAdapter);
		sp_apply_service.setPrompt("申请服务");

		sp_apply_service.setSelection(0);
		sp_apply_service
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						applyService = applyServices[arg2];
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
	}

	/***
	 * 申请原因下拉
	 */
	private void initReasonSp() {

		ArrayAdapter<String> simpleAdapter = new ArrayAdapter<String>(this,
				R.layout.simple_adapter, applyCauses);
		sp_apply_reason.setAdapter(simpleAdapter);
		sp_apply_reason.setPrompt("申请原因");
		sp_apply_reason.setSelection(0);
		sp_apply_reason.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				applyCause = applyCauses[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.choose_pic:
			doPickPhotoAction();
			break;
		case R.id.submit:
			submit(v);
			break;
		default:
			break;
		}
	}

	/***
	 * 提交图片信息，提交文字信息
	 */
	private void submit(final View v) {

		applyContent = et_apply_content.getText().toString().trim();

		for (int k = 0; k < listPicPath.size(); k++) {
			boolean isUpload = false;
			isUploads.add(isUpload);
			new SAsyncTask<String, Void, String>(this, v) {

				private int aa = 0;

				@Override
				protected String doInBackground(FragmentActivity context,
						String... params) throws Exception {
					String string = null;

					try {
						// 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
						String SAVE_KEY = File.separator + "returnShop"
								+ File.separator + System.currentTimeMillis()
								+ ".jpg";

						// 取得base64编码后的policy
						String policy = UpYunUtils.makePolicy(SAVE_KEY,
								Uploader.EXPIRATION, Uploader.BUCKET);

						// 根据表单api签名密钥对policy进行签名
						// 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
						String signature = UpYunUtils.signature(policy + "&"
								+ Uploader.TEST_API_KEY);

						// 上传文件到对应的bucket中去。
						string = Uploader.upload(policy, signature, Uploader.BUCKET,
								params[0]);
						aa = Integer.valueOf(params[1]);

					} catch (UpYunException e) {
						e.printStackTrace();
					}

					return string;
				}

				@Override
				protected void onPostExecute(FragmentActivity context,
						String result) {
					// TODO Auto-generated method stub
					super.onPostExecute(context, result);
					if (result != null) {
						LogYiFu.e("result", result);
						listUploadedUrl.add(result);
						isUploads.set(aa, true);
						boolean isUploaded = false;
						for (int i = 0; i < isUploads.size(); i++) {
							isUploaded = false;
							if (isUploads.get(i)) {
								isUploaded = true;
							}
						}

						if (isUploaded) {
							// 上传退款请求
							uploadThRequest(v);
						}
					}

				}

			}.execute(listPicPath.get(k), k + "");
		}
	}

	/***
	 * 上传文字请求
	 */
	private void uploadThRequest(View v) {
		// ToastUtil.showShortText(this, "继续上传文字操作");
		new SAsyncTask<Void, Void, ReturnInfo>(this, v, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				StringBuffer sbPic = new StringBuffer();
				for (int i = 0; i < listUploadedUrl.size(); i++) {
					sbPic.append(listUploadedUrl.get(i).split("/")[2]);
					if(i != listUploadedUrl.size() -1){
						sbPic.append(",");
					}
				}
				return ComModel2.applyThh(context, order.getOrder_code(), applyContent, sbPic.toString(), applyService, applyCause);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {
				// TODO Auto-generated method stub
				if(null == e){
				ToastUtil.showShortText(context, result.getMessage());
				}
				super.onPostExecute(context, result, e);
			}

		}.execute();
	}

	/**
	 * 获取相片信息
	 */
	private void doPickPhotoAction() {
		final Context dialogContext = new ContextThemeWrapper(this,
				android.R.style.Theme_Light);
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
								showToast("无内存卡");
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

			fileImageName = System.currentTimeMillis()+"";

			mCurrentPhotoFile = new File(mCurrentPhotoFile, fileImageName);
			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
			startActivityForResult(intent, RESULT_LOAD_PICTURE);
		} catch (ActivityNotFoundException e) {
			showToast("photoPickerNotFoundText");
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
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(screenWidth / 3,
					LayoutParams.WRAP_CONTENT));
			imageView.setAdjustViewBounds(true);
			imageView.setImageBitmap(listBitmap.get(i));
			imageView.setScaleType(ScaleType.FIT_XY);
			container.addView(imageView);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
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
				// new_parklot_pic_path = Pic_location.PHOTO_DIR +
				// fileImageName;
				// add_parklot_pic.setImageBitmap(bm);
				// add_parklot_pic.setScaleType(ScaleType.FIT_XY);
				//
				// newParkLotPicSize = new File(new_parklot_pic_path).length();
				// bm.recycle();
			}
		}
	}

	private String getRealPathFromURI(Uri originalUri) {
		String[] proj = { MediaStore.Images.Media.DATA};
		Cursor cursor = getContentResolver().query(originalUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
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

}
