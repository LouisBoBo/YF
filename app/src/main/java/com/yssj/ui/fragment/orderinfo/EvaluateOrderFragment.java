package com.yssj.ui.fragment.orderinfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.custom.view.PubImage;
import com.yssj.custom.view.PubImage.onDeteleImgLintener;
import com.yssj.custom.view.RatingBarView;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.TakePhotoUtil;
import com.yssj.utils.ToastUtil;

public class EvaluateOrderFragment extends BaseFragment implements onDeteleImgLintener{

	private static final int RESULT_OK = -1;
	private static int RESULT_LOAD_IMAGE = 3;
	private static int RESULT_LOAD_PICTURE = 4;
	
	private String fileImageName;

	private RadioGroup rg_no_color, rg_bx_beautify, rg_zg_ok, rg_xjb_good;

	private RatingBarView starView; // 星级评论
	private EditText et_content; // 评论内容

	private LinearLayout container;

	private String content = "";

	private int screenWidth, screenHeight;

	private List<Bitmap> listBitmap = new ArrayList<Bitmap>();// 上传的图片集合
	private List<String> listPicPath = new ArrayList<String>();// 上传的图片地址集合

	private int star = 5;

	private List<Boolean> isUploads = new ArrayList<Boolean>();// 是否上传的标识集合
	private List<String> listUploadedUrl = new ArrayList<String>();// 上传图片之后返回的URL
	
	private int color = 1, work = 1,cost = 1,type = 1; 
	
	private HashMap<String, Object> map = new HashMap<String, Object>();
	
	public EvaluateOrderFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		WindowManager wm = (WindowManager)context
				.getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
		screenHeight = wm.getDefaultDisplay().getHeight();
		
		
	}

	@Override
	public View initView() {

		view = View.inflate(context, R.layout.evaluate_order_content, null);
		view.setBackgroundColor(Color.WHITE);
		starView = (RatingBarView) view.findViewById(R.id.starView);
		starView.setmClickable(true);
		starView.setBindObject(5);
		starView.setStar(5);
		starView.setOnRatingListener(new RatingBarView.OnRatingListener() {
			@Override
			public void onRating(Object bindObject, int RatingScore) {
//				ToastUtil.showShortText(context, "bindObject : " + RatingScore);
				star = RatingScore;
			}
		});
		view.findViewById(R.id.choose_pic).setOnClickListener(this);
		container = (LinearLayout) view.findViewById(R.id.container);
		et_content = (EditText) view.findViewById(R.id.et_content);
		
		rg_no_color = (RadioGroup) view.findViewById(R.id.rg_no_color);
		rg_bx_beautify = (RadioGroup) view.findViewById(R.id.rg_bx_beautify);
		rg_zg_ok = (RadioGroup) view.findViewById(R.id.rg_zg_ok);
		rg_xjb_good = (RadioGroup) view.findViewById(R.id.rg_xjb_good);

		return view;
	}

	@Override
	public void initData() {
		rg_no_color.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.rb_no_color_yes){
					color = 1 ;
					
				}else{
					color = 0 ;
				}
			}
		});
		rg_bx_beautify.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.rb_bx_beautify_yes){
					type = 1 ;
				}else{
					type = 0 ;
				}
				
			}
		});
		rg_zg_ok.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.rb_zg_ok_yes){
					work = 1;
				}else{
					work = 0;
				}
				
			}
		});
		rg_xjb_good.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.rg_xjb_good){
					cost = 1;
				}else{
					cost = 0;
				}
				
			}
		});
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back: // 返回
			getActivity().finish();
			break;
		case R.id.choose_pic: // 选择图片
			if(listPicPath.size() == 3){
				ToastUtil.showShortText(context, "亲，最多只能选择三张图片哦。");
				return;
			}
			doPickPhotoAction();
			break;
		default:
			break;
		}
	}
	

	public HashMap<String, Object> getData(){
		content = et_content.getText().toString();
		map.put("content", content);
		
		map.put("star", star);	
		map.put("color", color);
		map.put("type", type);
		map.put("work", work);
		map.put("cost", cost);
		map.put("listPicPath", listPicPath);
		return map;
	}
	
	 
	/**
	 * 获取相片信息
	 */
	private void doPickPhotoAction() {
		final Context dialogContext = new ContextThemeWrapper(context,
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
								ToastUtil.showLongText(context, "无内存卡");
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
			ToastUtil.showLongText(context, "photoPickerNotFoundText");
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
			PubImage img=new PubImage(getActivity(),this);
			img.setTag(i);
			img.setBitmap(listBitmap.get(i));
			container.addView(img);
//			ImageView imageView = new ImageView(context);
//			imageView.setLayoutParams(new LayoutParams(screenWidth / 3,
//					LayoutParams.WRAP_CONTENT));
//			imageView.setAdjustViewBounds(true);
//			imageView.setImageBitmap(listBitmap.get(i));
//			imageView.setScaleType(ScaleType.FIT_XY);
//			container.addView(imageView);
		}
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
			}
		}
	}

	private String getRealPathFromURI(Uri originalUri) {
		String[] proj = { MediaStore.Images.Media.DATA};
		Cursor cursor = context.getContentResolver().query(originalUri, proj, null, null, null);
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

	@Override
	public void deleteImg(int index) {
		// TODO Auto-generated method stub
		listBitmap.remove(index);
		addImageView(listBitmap);
		listPicPath.remove(index);
	}

	

}
