//<<<<<<< .mine
////package com.yssj.ui.activity.circles;
////
////import java.io.File;
////import java.io.Serializable;
////import java.util.ArrayList;
////import java.util.HashMap;
////import java.util.List;
////import java.util.Map;
////
////import android.app.AlertDialog;
////import android.content.ActivityNotFoundException;
////import android.content.Context;
////import android.content.DialogInterface;
////import android.content.Intent;
////import android.database.Cursor;
////import android.graphics.Bitmap;
////import android.graphics.BitmapFactory;
////import android.net.Uri;
////import android.os.Bundle;
////import android.os.Environment;
////import android.provider.MediaStore;
////import android.support.v4.app.FragmentActivity;
////import android.text.TextUtils;
////import android.util.Log;
////import android.view.ContextThemeWrapper;
////import android.view.KeyEvent;
////import android.view.View;
////import android.view.View.OnClickListener;
////import android.view.WindowManager;
////import android.widget.ArrayAdapter;
////import android.widget.Button;
////import android.widget.EditText;
////import android.widget.ImageButton;
////import android.widget.LinearLayout;
////import android.widget.ListAdapter;
////import android.widget.RelativeLayout;
////import android.widget.TextView;
////
////import com.yssj.YConstance;
////import com.yssj.activity.R;
////import com.yssj.app.SAsyncTask;
////import com.yssj.custom.view.LoadingDialog;
////import com.yssj.custom.view.PubImage;
////import com.yssj.custom.view.PubImage.onDeteleImgLintener;
////import com.yssj.entity.ReturnInfo;
////import com.yssj.model.ComModel2;
////import com.yssj.ui.activity.logins.RegisterFragment;
////import com.yssj.ui.base.BasicActivity;
////import com.yssj.upyun.UpYunException;
////import com.yssj.upyun.UpYunUtils;
////import com.yssj.upyun.Uploader;
////import com.yssj.utils.StringUtils;
////import com.yssj.utils.ToastUtil;
////
/////**
//// * @author Administrator
//// * 
//// */
////public class PublishTopicActivity extends BasicActivity implements
////		OnClickListener,onDeteleImgLintener {
////	private LinearLayout img_back;
////	private TextView tvTitle_base, tv_tag;
////	private EditText et_title, et_content;
////	private RelativeLayout rel_choice_tag;
////	private Button btn_publish;
////	private String circle_id;
////	private String tags;
////	private String[] tag;
////	private String tagId;
////
////	private ImageButton imgbtn_capture;
////
////	private static int RESULT_LOAD_IMAGE = 3;
////	private static int RESULT_LOAD_PICTURE = 4;
////
////	private String fileImageName;
////	private LinearLayout container;
////
////	private List<Bitmap> listBitmap = new ArrayList<Bitmap>();// 上传的图片集合
////	private List<String> listPicPath = new ArrayList<String>();// 上传的图片地址集合
////	private List<Boolean> isUploads = new ArrayList<Boolean>();// 是否上传的标识集合
////	private List<String> listUploadedUrl = new ArrayList<String>();// 上传图片之后返回的URL
////	private String tagName;
////
////	private List<Map<String, Object>> listData; // 标签数据
////	private String tag_data = null;
////	
////
////	@Override
////	protected void onCreate(Bundle savaIntanceState) {
////		super.onCreate(savaIntanceState);
////		getActionBar().hide();
//////		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//////		screenWidth = wm.getDefaultDisplay().getWidth();
//////		screenHeight = wm.getDefaultDisplay().getHeight();
////		setContentView(R.layout.activity_circle_publish_topic);
////		circle_id = getIntent().getStringExtra("circle_id");
////		tags = getIntent().getStringExtra("tags");
////		tag = tags.split(",");
////		initView();
////		initTagData(); // 初始化标签数据
////	}
////
////	private void initView() {
////		img_back = (LinearLayout) findViewById(R.id.img_back);
////		img_back.setOnClickListener(this);
////		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
////		tvTitle_base.setText("发表话题");
////		tv_tag = (TextView) findViewById(R.id.tv_tag);
////		et_title = (EditText) findViewById(R.id.et_title);
////		et_content = (EditText) findViewById(R.id.et_content);
////		rel_choice_tag = (RelativeLayout) findViewById(R.id.rel_choice_tag);
////		rel_choice_tag.setOnClickListener(this);
////		btn_publish = (Button) findViewById(R.id.btn_publish);
////		btn_publish.setOnClickListener(this);
////
////		imgbtn_capture = (ImageButton) findViewById(R.id.imgbtn_capture);
////		imgbtn_capture.setOnClickListener(this);
////
////		container = (LinearLayout) findViewById(R.id.container);
////		
////
////		tv_tag = (TextView) findViewById(R.id.tv_tag);
////	}
////
////	private void publishTopic(final String title, final String content) {
////
////		new SAsyncTask<String, Void, ReturnInfo>(PublishTopicActivity.this,
////				0) {
////
////			String pic_list = null;
////
////			@Override
////			protected ReturnInfo doInBackground(FragmentActivity context,
////					String... params) throws Exception {
////				StringBuffer sbPic = new StringBuffer();
////				for (int i = 0; i < listUploadedUrl.size(); i++) {
////					sbPic.append(listUploadedUrl.get(i));
////					if (i != listUploadedUrl.size() - 1) {
////						sbPic.append(",");
////					}
////				}
////
////				pic_list = sbPic.toString();
////				isUpdate = false;
////				// MyLogYiFu.e("新制的连接",pic_list);
////				if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
////					return ComModel2.getCirclePublishTopic(context, circle_id,
////							title, content, pic_list, tagId);
////				} else {
////					return null;
////				}
////			}
////
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					ReturnInfo result) {
////				super.onPostExecute(context, result);
////				LoadingDialog.hide(context);
////				if (result != null) {
////					if ("1".equals(result.getStatus())) {
////						Map<String, Object> map = new HashMap<String, Object>();
////						map.put("circle_id", circle_id);
////						ToastUtil.showShortText(PublishTopicActivity.this,
////								result.getMessage());
////						Intent intent = new Intent(context,
////								CircleCommonFragmentActivity.class);
////						intent.putExtra("item", (Serializable) map);
////						intent.putExtra("flag", "allPager");
////						startActivity(intent);
////						finish();
////					} else if ("100".equals(result.getStatus())) {
////
////						ToastUtil.showShortText(context, result.getMessage());
////					}
////				} else {
////					ToastUtil.showShortText(context, "标题、内容或标签都不能为空");
////				}
////				
////			}
////
////		}.execute(circle_id);
////	}
////
////	@Override
////	public boolean onKeyDown(int keyCode, KeyEvent event) {
////		// TODO Auto-generated method stub
////		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
////			Map<String, Object> map = new HashMap<String, Object>();
////			map.put("circle_id", circle_id);
////			Intent intent = new Intent(context,
////					CircleCommonFragmentActivity.class);
////			intent.putExtra("item", (Serializable) map);
////			intent.putExtra("flag", "allPager");
////			startActivity(intent);
////			this.overridePendingTransition(R.anim.activity_from_right_publish,
////					R.anim.activity_from_right_publish_close);
////			finish();
////		}
////		return super.onKeyDown(keyCode, event);
////	}
////
////	@Override
////	public void onClick(View v) {
////		Intent intent;
////		switch (v.getId()) {
////		case R.id.img_back:
////			Map<String, Object> map = new HashMap<String, Object>();
////			map.put("circle_id", circle_id);
////			intent = new Intent(context, CircleCommonFragmentActivity.class);
////			intent.putExtra("item", (Serializable) map);
////			intent.putExtra("flag", "allPager");
////			startActivity(intent);
////			this.overridePendingTransition(R.anim.activity_from_right_publish,
////					R.anim.activity_from_right_publish_close);
////			finish();
////
////			break;
////		case R.id.rel_choice_tag:
////			intent = new Intent(getApplication(), ChooseTagActivity.class);
////			intent.putExtra("tagValue", (Serializable) listData);
////			startActivityForResult(intent, 101);
////			break;
////		case R.id.btn_publish:
////			// publishTopic();
////			submit(v);
////			break;
////		case R.id.imgbtn_capture:
////			doPickPhotoAction();
////			break;
////		default :
////			break;
////		}
////	}
////
////	private boolean isUpdate = false;// 是否正在上传
////
////	private String title, content;
////
////	/***
////	 * 提交图片信息，提交文字信息
////	 */
////	private void submit(final View v) {
////
////		title = et_title.getText().toString();
////		content = et_content.getText().toString();
////
////		if (RegisterFragment.getWordCount(title)< 8 ) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "标题不得小于8个字符！");
////			return;
////		}
////		if(RegisterFragment.getWordCount(content) < 6){
////			ToastUtil.showShortText(PublishTopicActivity.this, "内容不得小于6个字符！");
////			return;
////		}
////
////		if (RegisterFragment.getWordCount(title) > 30 ) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "标题不得大于30个字符！");
////			return;
////		}
////		if ( RegisterFragment.getWordCount(content) > 5000) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "内容不得大于5000个字符！");
////			return;
////		}
////		if (TextUtils.isEmpty(title) ) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "标题不能为空！");
////			return;
////		}
////		if (TextUtils.isEmpty(content)) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "内容不能为空！");
////			return;
////		}
////		if (StringUtils.containsEmoji(title)
////				) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "标题不能输入特殊字符");
////			return;
////		}
////		if (StringUtils.containsEmoji(title)
////				) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "内容不能输入特殊字符");
////			return;
////		}
////		if(TextUtils.isEmpty(tagId)){
////			ToastUtil.showShortText(PublishTopicActivity.this, "请选择标签");
////			return;
////		}
////		if (isUpdate) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "帖子发表中……");
////			return;
////		}
////		isUpdate = true;
////		LoadingDialog.show((FragmentActivity)context);
////		if (listPicPath == null || listPicPath.isEmpty()
////				|| listPicPath.size() == 0) {
////			publishTopic(title, content);
////			return;
////		}
////		isUploads.clear();
////		listUploadedUrl.clear();
////		for (int k = 0; k < listPicPath.size(); k++) {
////			boolean isUpload = false;
////			isUploads.add(isUpload);
////			
////			new SAsyncTask<String, Void, String>(this, 0) {
////
////				private int aa = 0;
////
////				@Override
////				protected String doInBackground(FragmentActivity context,
////						String... params) throws Exception {
////					String string = null;
////
////					try {
////						// 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
////						String SAVE_KEY = File.separator + "circle_news"
////								+ File.separator + System.currentTimeMillis()
////								+ ".jpg";
////
////						// 取得base64编码后的policy
////						String policy = UpYunUtils.makePolicy(SAVE_KEY,
////								Uploader.EXPIRATION, Uploader.BUCKET);
////
////						// 根据表单api签名密钥对policy进行签名
////						// 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
////						String signature = UpYunUtils.signature(policy + "&"
////								+ Uploader.TEST_API_KEY);
////
////						// 上传文件到对应的bucket中去。
////						string = Uploader.upload(policy, signature,
////								Uploader.BUCKET, params[0]);
////						aa = Integer.valueOf(params[1]);
////
////						BitmapFactory.Options opt = new BitmapFactory.Options();
////						opt.inJustDecodeBounds = true;
////						BitmapFactory.decodeFile(params[0], opt);
////						float m = ((float) opt.outWidth)
////								/ ((float) opt.outHeight);
////						string = string + ":" + m;// 宽高比
////
////					} catch (UpYunException e) {
////						e.printStackTrace();
////					}
////
////					return string;
////				}
////
////				@Override
////				protected void onPostExecute(FragmentActivity context,
////						String result) {
////					super.onPostExecute(context, result);
////
////					if (result != null) {
////						MyLogYiFu.e("result", result);
////						listUploadedUrl.add(result);
////						isUploads.set(aa, true);
////						boolean isUploaded = false;
////						for (int i = 0; i < isUploads.size(); i++) {
////							isUploaded = false;
////							if (isUploads.get(i)) {
////								isUploaded = true;
////							}
////						}
////
////						if (isUploaded) {
////							// 上传发帖请求
////							publishTopic(title, content);
////						}
////					}
////
////				}
////
////			}.execute(listPicPath.get(k), k + "");
////		}
////	}
////
////	/**
////	 * 获取相片信息
////	 */
////	private void doPickPhotoAction() {
////		final Context dialogContext = new ContextThemeWrapper(this,
////				android.R.style.Theme_Light); // R.style.Dialog
////		String[] choices;
////		choices = new String[2];
////		choices[0] = "拍照"; // 拍照
////		choices[1] = "从相册中选择"; // 从相册中选择
////		final ListAdapter adapter = new ArrayAdapter<String>(dialogContext,
////				android.R.layout.simple_list_item_1, choices);
////		final AlertDialog.Builder builder = new AlertDialog.Builder(
////				dialogContext);
////		builder.setTitle("");
////		builder.setSingleChoiceItems(adapter, -1,
////				new DialogInterface.OnClickListener() {
////					@Override
////					public void onClick(DialogInterface dialog, int which) {
////						dialog.dismiss();
////						switch (which) {
////						case 0: {
////							String status = Environment
////									.getExternalStorageState();
////							if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
////								doTakePhoto();
////							} else {
////								showToast("无内存卡");
////							}
////							break;
////						}
////						case 1:
////							doPickPhotoFromGallery();
////							break;
////						}
////					}
////				});
////		builder.create().show();
////	}
////
////	/**
////	 * 拍照获取图片
////	 * 
////	 */
////	protected void doTakePhoto() {
////		try {
////			File mCurrentPhotoFile = new File(YConstance.saveUploadPicPath);
////			if (!mCurrentPhotoFile.exists()) {
////				boolean iscreat = mCurrentPhotoFile.mkdirs();// 创建照片的存储目录
////			}
////
////			fileImageName = System.currentTimeMillis() + "";
////
////			mCurrentPhotoFile = new File(mCurrentPhotoFile, fileImageName);
////			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
////			startActivityForResult(intent, RESULT_LOAD_PICTURE);
////		} catch (ActivityNotFoundException e) {
////			showToast("photoPickerNotFoundText");
////		}
////	}
////
////	public static Intent getTakePickIntent(File f) {
////		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
////		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
////		return intent;
////	}
////
////	private void addImageView(List<Bitmap> listBitmap) {
////		container.removeAllViews();
////		for (int i = 0; i < listBitmap.size(); i++) {
////			PubImage img=new PubImage(context,this);
////			img.setTag(i);
////			img.setBitmap(listBitmap.get(i));
////			container.addView(img);
////		}
////		if(listBitmap.size()==10){
////			imgbtn_capture.setVisibility(View.GONE);
////		}else{
////			imgbtn_capture.setVisibility(View.VISIBLE);
////		}
////	}
////
////	@Override
////	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////		super.onActivityResult(requestCode, resultCode, data);
////		if (resultCode == RESULT_OK) {
////			if (requestCode == RESULT_LOAD_IMAGE) {
////				// BitmapFactory bf = BitmapFactory.decodeFile(pathName)
////				// add_qx_pic.set
////
////				final Uri originalUri = data.getData(); // 获得图片的uri
////				String path;
////				if (originalUri.getScheme().equals("content")) {
////					path = getRealPathFromURI(originalUri);
////				} else {
////					path = originalUri.getPath();
////				}
////				BitmapFactory.Options options = new BitmapFactory.Options();
////				options.inSampleSize = 2;
////				Bitmap bm = BitmapFactory.decodeFile(path, options);
////				listBitmap.add(bm);
////				listPicPath.add(path);
////				addImageView(listBitmap);
////				// new_parklot_pic_path = path;
////				// add_parklot_pic.setImageBitmap(bm);
////				// add_parklot_pic.setScaleType(ScaleType.FIT_XY);
////				// newParkLotPicSize = new File(new_parklot_pic_path).length();
////
////			} else if (requestCode == RESULT_LOAD_PICTURE) {
////				BitmapFactory.Options options = new BitmapFactory.Options();
////				options.inSampleSize = 2;
////				String path = YConstance.saveUploadPicPath + fileImageName;
////				Bitmap bm = BitmapFactory.decodeFile(path, options);
////				listBitmap.add(bm);
////				addImageView(listBitmap);
////				listPicPath.add(path);
////				// new_parklot_pic_path = Pic_location.PHOTO_DIR +
////				// fileImageName;
////				// add_parklot_pic.setImageBitmap(bm);
////				// add_parklot_pic.setScaleType(ScaleType.FIT_XY);
////				//
////				// newParkLotPicSize = new File(new_parklot_pic_path).length();
////				// bm.recycle();
////			}
////		} else if (requestCode == 101 && data != null) {
////			tagId = data.getStringExtra("id");
////			tagName = data.getStringExtra("name");
////			tv_tag.setText(tagName);
////		}
////	}
////
////	private String getRealPathFromURI(Uri originalUri) {
////		String[] proj = { MediaStore.Images.Media.DATA };
////		Cursor cursor = getContentResolver().query(originalUri, proj, null,
////				null, null);
////		int column_index = cursor
////				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
////		cursor.moveToFirst();
////		String path = cursor.getString(cursor
////				.getColumnIndex(MediaStore.Images.Media.DATA));
////		cursor.close();
////		return path;
////	}
////
////	protected void doPickPhotoFromGallery() {
////		try {
////			Intent intent = getPhotoPickIntent();
////			startActivityForResult(intent, RESULT_LOAD_IMAGE);
////		} catch (ActivityNotFoundException e) {
////			e.printStackTrace();
////		}
////	}
////
////	public static Intent getPhotoPickIntent() {
////		Intent intent = new Intent(Intent.ACTION_PICK);
////		intent.setType("image/*");
////		return intent;
////	}
////
////	private void initTagData() {
////		new SAsyncTask<Void, Void, List<Map<String, Object>>>(this) {
////
////			@Override
////			protected List<Map<String, Object>> doInBackground(
////					FragmentActivity context, Void... params) throws Exception {
////				return ComModel2.getCircleTags(context, tag_data);
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					List<Map<String, Object>> result, Exception e) {
////				super.onPostExecute(context, result, e);
////				if (null == e) {
////					if (result != null) {
////						List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
////
////						for (int i = 0; i < tag.length; i++) {
////							for (int j = 0; j < result.size(); j++) {
////								if (result.get(j).get("id").equals(tag[i])) {
////									list.add(result.get(j));
////								}
////							}
////						}
////						for (int i = 0; i < list.size(); i++) {
////							list.get(i).put("isChecked", "0");
////						}
////						listData = list;
////					}
////				}
////
////			}
////
////		}.execute();
////	}
////
////	@Override
////	public void deleteImg(int index) {
////		listBitmap.remove(index);
////		listPicPath.remove(index);
////		addImageView(listBitmap);
////	}
////}
//=======
//package com.yssj.ui.activity.circles;
//
//import java.io.File;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.app.AlertDialog;
//import android.content.ActivityNotFoundException;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.ContextThemeWrapper;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.WindowManager;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.ListAdapter;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.yssj.YConstance;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.LoadingDialog;
//import com.yssj.custom.view.PubImage;
//import com.yssj.custom.view.PubImage.onDeteleImgLintener;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.logins.RegisterFragment;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.upyun.UpYunException;
//import com.yssj.upyun.UpYunUtils;
//import com.yssj.upyun.Uploader;
//import com.yssj.utils.StringUtils;
//import com.yssj.utils.ToastUtil;
//
///**
// * @author Administrator
// * 
// */
//public class PublishTopicActivity {
//	private LinearLayout img_back;
//	private TextView tvTitle_base, tv_tag;
//	private EditText et_title, et_content;
//	private RelativeLayout rel_choice_tag;
//	private Button btn_publish;
//	private String circle_id;
//	private String tags;
//	private String[] tag;
//	private String tagId;
//
//	private ImageButton imgbtn_capture;
//
//	private static int RESULT_LOAD_IMAGE = 3;
//	private static int RESULT_LOAD_PICTURE = 4;
//
//	private String fileImageName;
//	private LinearLayout container;
//
//	private List<Bitmap> listBitmap = new ArrayList<Bitmap>();// 上传的图片集合
//	private List<String> listPicPath = new ArrayList<String>();// 上传的图片地址集合
//	private List<Boolean> isUploads = new ArrayList<Boolean>();// 是否上传的标识集合
//	private List<String> listUploadedUrl = new ArrayList<String>();// 上传图片之后返回的URL
//	private String tagName;
//
//	private List<Map<String, Object>> listData; // 标签数据
//	private String tag_data = null;
//	
//
////	@Override
////	protected void onCreate(Bundle savaIntanceState) {
////		super.onCreate(savaIntanceState);
////		getActionBar().hide();
//////		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//////		screenWidth = wm.getDefaultDisplay().getWidth();
//////		screenHeight = wm.getDefaultDisplay().getHeight();
////		setContentView(R.layout.activity_circle_publish_topic);
////		circle_id = getIntent().getStringExtra("circle_id");
////		tags = getIntent().getStringExtra("tags");
////		tag = tags.split(",");
////		initView();
////		initTagData(); // 初始化标签数据
//	}
//
////	private void initView() {
////		img_back = (LinearLayout) findViewById(R.id.img_back);
////		img_back.setOnClickListener(this);
////		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
////		tvTitle_base.setText("发表话题");
////		tv_tag = (TextView) findViewById(R.id.tv_tag);
////		et_title = (EditText) findViewById(R.id.et_title);
////		et_content = (EditText) findViewById(R.id.et_content);
////		rel_choice_tag = (RelativeLayout) findViewById(R.id.rel_choice_tag);
////		rel_choice_tag.setOnClickListener(this);
////		btn_publish = (Button) findViewById(R.id.btn_publish);
////		btn_publish.setOnClickListener(this);
////
////		imgbtn_capture = (ImageButton) findViewById(R.id.imgbtn_capture);
////		imgbtn_capture.setOnClickListener(this);
////
////		container = (LinearLayout) findViewById(R.id.container);
////		
////
////		tv_tag = (TextView) findViewById(R.id.tv_tag);
////	}
////
////	private void publishTopic(final String title, final String content) {
////
////		new SAsyncTask<String, Void, ReturnInfo>(PublishTopicActivity.this,
////				0) {
////
////			String pic_list = null;
////
////			@Override
////			protected ReturnInfo doInBackground(FragmentActivity context,
////					String... params) throws Exception {
////				StringBuffer sbPic = new StringBuffer();
////				for (int i = 0; i < listUploadedUrl.size(); i++) {
////					sbPic.append(listUploadedUrl.get(i));
////					if (i != listUploadedUrl.size() - 1) {
////						sbPic.append(",");
////					}
////				}
////
////				pic_list = sbPic.toString();
////				isUpdate = false;
////				// MyLogYiFu.e("新制的连接",pic_list);
////				if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
////					return ComModel2.getCirclePublishTopic(context, circle_id,
////							title, content, pic_list, tagId);
////				} else {
////					return null;
////				}
////			}
////
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					ReturnInfo result) {
////				super.onPostExecute(context, result);
////				LoadingDialog.hide(context);
////				if (result != null) {
////					if ("1".equals(result.getStatus())) {
////						Map<String, Object> map = new HashMap<String, Object>();
////						map.put("circle_id", circle_id);
////						ToastUtil.showShortText(PublishTopicActivity.this,
////								result.getMessage());
////						Intent intent = new Intent(context,
////								CircleCommonFragmentActivity.class);
////						intent.putExtra("item", (Serializable) map);
////						intent.putExtra("flag", "allPager");
////						startActivity(intent);
////						finish();
////					} else if ("100".equals(result.getStatus())) {
////
////						ToastUtil.showShortText(context, result.getMessage());
////					}
////				} else {
////					ToastUtil.showShortText(context, "标题、内容或标签都不能为空");
////				}
////				
////			}
////
////		}.execute(circle_id);
////	}
////
////	@Override
////	public boolean onKeyDown(int keyCode, KeyEvent event) {
////		// TODO Auto-generated method stub
////		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
////			Map<String, Object> map = new HashMap<String, Object>();
////			map.put("circle_id", circle_id);
////			Intent intent = new Intent(context,
////					CircleCommonFragmentActivity.class);
////			intent.putExtra("item", (Serializable) map);
////			intent.putExtra("flag", "allPager");
////			startActivity(intent);
////			this.overridePendingTransition(R.anim.activity_from_right_publish,
////					R.anim.activity_from_right_publish_close);
////			finish();
////		}
////		return super.onKeyDown(keyCode, event);
////	}
////
////	@Override
////	public void onClick(View v) {
////		Intent intent;
////		switch (v.getId()) {
////		case R.id.img_back:
////			Map<String, Object> map = new HashMap<String, Object>();
////			map.put("circle_id", circle_id);
////			intent = new Intent(context, CircleCommonFragmentActivity.class);
////			intent.putExtra("item", (Serializable) map);
////			intent.putExtra("flag", "allPager");
////			startActivity(intent);
////			this.overridePendingTransition(R.anim.activity_from_right_publish,
////					R.anim.activity_from_right_publish_close);
////			finish();
////
////			break;
////		case R.id.rel_choice_tag:
////			intent = new Intent(getApplication(), ChooseTagActivity.class);
////			intent.putExtra("tagValue", (Serializable) listData);
////			startActivityForResult(intent, 101);
////			break;
////		case R.id.btn_publish:
////			// publishTopic();
////			submit(v);
////			break;
////		case R.id.imgbtn_capture:
////			doPickPhotoAction();
////			break;
////		default :
////			break;
////		}
////	}
////
////	private boolean isUpdate = false;// 是否正在上传
////
////	private String title, content;
////
////	/***
////	 * 提交图片信息，提交文字信息
////	 */
////	private void submit(final View v) {
////
////		title = et_title.getText().toString();
////		content = et_content.getText().toString();
////
////		if (RegisterFragment.getWordCount(title)< 8 ) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "标题不得小于8个字符！");
////			return;
////		}
////		if(RegisterFragment.getWordCount(content) < 6){
////			ToastUtil.showShortText(PublishTopicActivity.this, "内容不得小于6个字符！");
////			return;
////		}
////
////		if (RegisterFragment.getWordCount(title) > 30 ) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "标题不得大于30个字符！");
////			return;
////		}
////		if ( RegisterFragment.getWordCount(content) > 5000) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "内容不得大于5000个字符！");
////			return;
////		}
////		if (TextUtils.isEmpty(title) ) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "标题不能为空！");
////			return;
////		}
////		if (TextUtils.isEmpty(content)) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "内容不能为空！");
////			return;
////		}
////		if (StringUtils.containsEmoji(title)
////				) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "标题不能输入特殊字符");
////			return;
////		}
////		if (StringUtils.containsEmoji(title)
////				) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "内容不能输入特殊字符");
////			return;
////		}
////		if(TextUtils.isEmpty(tagId)){
////			ToastUtil.showShortText(PublishTopicActivity.this, "请选择标签");
////			return;
////		}
////		if (isUpdate) {
////			ToastUtil.showShortText(PublishTopicActivity.this, "帖子发表中……");
////			return;
////		}
////		isUpdate = true;
////		LoadingDialog.show((FragmentActivity)context);
////		if (listPicPath == null || listPicPath.isEmpty()
////				|| listPicPath.size() == 0) {
////			publishTopic(title, content);
////			return;
////		}
////		isUploads.clear();
////		listUploadedUrl.clear();
////		for (int k = 0; k < listPicPath.size(); k++) {
////			boolean isUpload = false;
////			isUploads.add(isUpload);
////			
////			new SAsyncTask<String, Void, String>(this, 0) {
////
////				private int aa = 0;
////
////				@Override
////				protected String doInBackground(FragmentActivity context,
////						String... params) throws Exception {
////					String string = null;
////
////					try {
////						// 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
////						String SAVE_KEY = File.separator + "circle_news"
////								+ File.separator + System.currentTimeMillis()
////								+ ".jpg";
////
////						// 取得base64编码后的policy
////						String policy = UpYunUtils.makePolicy(SAVE_KEY,
////								Uploader.EXPIRATION, Uploader.BUCKET);
////
////						// 根据表单api签名密钥对policy进行签名
////						// 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
////						String signature = UpYunUtils.signature(policy + "&"
////								+ Uploader.TEST_API_KEY);
////
////						// 上传文件到对应的bucket中去。
////						string = Uploader.upload(policy, signature,
////								Uploader.BUCKET, params[0]);
////						aa = Integer.valueOf(params[1]);
////
////						BitmapFactory.Options opt = new BitmapFactory.Options();
////						opt.inJustDecodeBounds = true;
////						BitmapFactory.decodeFile(params[0], opt);
////						float m = ((float) opt.outWidth)
////								/ ((float) opt.outHeight);
////						string = string + ":" + m;// 宽高比
////
////					} catch (UpYunException e) {
////						e.printStackTrace();
////					}
////
////					return string;
////				}
////
////				@Override
////				protected void onPostExecute(FragmentActivity context,
////						String result) {
////					super.onPostExecute(context, result);
////
////					if (result != null) {
////						MyLogYiFu.e("result", result);
////						listUploadedUrl.add(result);
////						isUploads.set(aa, true);
////						boolean isUploaded = false;
////						for (int i = 0; i < isUploads.size(); i++) {
////							isUploaded = false;
////							if (isUploads.get(i)) {
////								isUploaded = true;
////							}
////						}
////
////						if (isUploaded) {
////							// 上传发帖请求
////							publishTopic(title, content);
////						}
////					}
////
////				}
////
////			}.execute(listPicPath.get(k), k + "");
////		}
////	}
////
////	/**
////	 * 获取相片信息
////	 */
////	private void doPickPhotoAction() {
////		final Context dialogContext = new ContextThemeWrapper(this,
////				android.R.style.Theme_Light); // R.style.Dialog
////		String[] choices;
////		choices = new String[2];
////		choices[0] = "拍照"; // 拍照
////		choices[1] = "从相册中选择"; // 从相册中选择
////		final ListAdapter adapter = new ArrayAdapter<String>(dialogContext,
////				android.R.layout.simple_list_item_1, choices);
////		final AlertDialog.Builder builder = new AlertDialog.Builder(
////				dialogContext);
////		builder.setTitle("");
////		builder.setSingleChoiceItems(adapter, -1,
////				new DialogInterface.OnClickListener() {
////					@Override
////					public void onClick(DialogInterface dialog, int which) {
////						dialog.dismiss();
////						switch (which) {
////						case 0: {
////							String status = Environment
////									.getExternalStorageState();
////							if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
////								doTakePhoto();
////							} else {
////								showToast("无内存卡");
////							}
////							break;
////						}
////						case 1:
////							doPickPhotoFromGallery();
////							break;
////						}
////					}
////				});
////		builder.create().show();
////	}
////
////	/**
////	 * 拍照获取图片
////	 * 
////	 */
////	protected void doTakePhoto() {
////		try {
////			File mCurrentPhotoFile = new File(YConstance.saveUploadPicPath);
////			if (!mCurrentPhotoFile.exists()) {
////				boolean iscreat = mCurrentPhotoFile.mkdirs();// 创建照片的存储目录
////			}
////
////			fileImageName = System.currentTimeMillis() + "";
////
////			mCurrentPhotoFile = new File(mCurrentPhotoFile, fileImageName);
////			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
////			startActivityForResult(intent, RESULT_LOAD_PICTURE);
////		} catch (ActivityNotFoundException e) {
////			showToast("photoPickerNotFoundText");
////		}
////	}
////
////	public static Intent getTakePickIntent(File f) {
////		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
////		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
////		return intent;
////	}
////
////	private void addImageView(List<Bitmap> listBitmap) {
////		container.removeAllViews();
////		for (int i = 0; i < listBitmap.size(); i++) {
////			PubImage img=new PubImage(context,this);
////			img.setTag(i);
////			img.setBitmap(listBitmap.get(i));
////			container.addView(img);
////		}
////		if(listBitmap.size()==10){
////			imgbtn_capture.setVisibility(View.GONE);
////		}else{
////			imgbtn_capture.setVisibility(View.VISIBLE);
////		}
////	}
////
////	@Override
////	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////		super.onActivityResult(requestCode, resultCode, data);
////		if (resultCode == RESULT_OK) {
////			if (requestCode == RESULT_LOAD_IMAGE) {
////				// BitmapFactory bf = BitmapFactory.decodeFile(pathName)
////				// add_qx_pic.set
////
////				final Uri originalUri = data.getData(); // 获得图片的uri
////				String path;
////				if (originalUri.getScheme().equals("content")) {
////					path = getRealPathFromURI(originalUri);
////				} else {
////					path = originalUri.getPath();
////				}
////				BitmapFactory.Options options = new BitmapFactory.Options();
////				options.inSampleSize = 2;
////				Bitmap bm = BitmapFactory.decodeFile(path, options);
////				listBitmap.add(bm);
////				listPicPath.add(path);
////				addImageView(listBitmap);
////				// new_parklot_pic_path = path;
////				// add_parklot_pic.setImageBitmap(bm);
////				// add_parklot_pic.setScaleType(ScaleType.FIT_XY);
////				// newParkLotPicSize = new File(new_parklot_pic_path).length();
////
////			} else if (requestCode == RESULT_LOAD_PICTURE) {
////				BitmapFactory.Options options = new BitmapFactory.Options();
////				options.inSampleSize = 2;
////				String path = YConstance.saveUploadPicPath + fileImageName;
////				Bitmap bm = BitmapFactory.decodeFile(path, options);
////				listBitmap.add(bm);
////				addImageView(listBitmap);
////				listPicPath.add(path);
////				// new_parklot_pic_path = Pic_location.PHOTO_DIR +
////				// fileImageName;
////				// add_parklot_pic.setImageBitmap(bm);
////				// add_parklot_pic.setScaleType(ScaleType.FIT_XY);
////				//
////				// newParkLotPicSize = new File(new_parklot_pic_path).length();
////				// bm.recycle();
////			}
////		} else if (requestCode == 101 && data != null) {
////			tagId = data.getStringExtra("id");
////			tagName = data.getStringExtra("name");
////			tv_tag.setText(tagName);
////		}
////	}
////
////	private String getRealPathFromURI(Uri originalUri) {
////		String[] proj = { MediaStore.Images.Media.DATA };
////		Cursor cursor = getContentResolver().query(originalUri, proj, null,
////				null, null);
////		int column_index = cursor
////				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
////		cursor.moveToFirst();
////		String path = cursor.getString(cursor
////				.getColumnIndex(MediaStore.Images.Media.DATA));
////		cursor.close();
////		return path;
////	}
////
////	protected void doPickPhotoFromGallery() {
////		try {
////			Intent intent = getPhotoPickIntent();
////			startActivityForResult(intent, RESULT_LOAD_IMAGE);
////		} catch (ActivityNotFoundException e) {
////			e.printStackTrace();
////		}
////	}
////
////	public static Intent getPhotoPickIntent() {
////		Intent intent = new Intent(Intent.ACTION_PICK);
////		intent.setType("image/*");
////		return intent;
////	}
////
////	private void initTagData() {
////		new SAsyncTask<Void, Void, List<Map<String, Object>>>(this) {
////
////			@Override
////			protected List<Map<String, Object>> doInBackground(
////					FragmentActivity context, Void... params) throws Exception {
////				return ComModel2.getCircleTags(context, tag_data);
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					List<Map<String, Object>> result, Exception e) {
////				super.onPostExecute(context, result, e);
////				if (null == e) {
////					if (result != null) {
////						List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
////
////						for (int i = 0; i < tag.length; i++) {
////							for (int j = 0; j < result.size(); j++) {
////								if (result.get(j).get("id").equals(tag[i])) {
////									list.add(result.get(j));
////								}
////							}
////						}
////						for (int i = 0; i < list.size(); i++) {
////							list.get(i).put("isChecked", "0");
////						}
////						listData = list;
////					}
////				}
////
////			}
////
////		}.execute();
////	}
////
////	@Override
////	public void deleteImg(int index) {
////		listBitmap.remove(index);
////		listPicPath.remove(index);
////		addImageView(listBitmap);
////	}
////}
//>>>>>>> .r26813
