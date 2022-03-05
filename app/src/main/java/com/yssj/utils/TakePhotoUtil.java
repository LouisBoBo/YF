package com.yssj.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import com.yssj.YConstance;
import com.yssj.ui.base.BasicActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.ContextThemeWrapper;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class TakePhotoUtil {
	
	public static int RESULT_LOAD_IMAGE = 3;
	public static int RESULT_LOAD_PICTURE = 4;
	public static int RESULT_LOAD_FINAL = 5;
	public static int REQUEST_TAKE_HEAD_PIC=6;
	public static String fileImageName;
	
	/**
	 * 获取相片信息
	 */
	public static void doPickPhotoAction(final Activity activity) {
		final Context dialogContext = new ContextThemeWrapper(activity,
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
								doTakePhoto(activity);
							} else {
								ToastUtil.showShortText(activity, "无内存卡");
							}
							break;
						}
						case 1:
							doPickPhotoFromGallery(activity);
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
	public static void doTakePhoto(Activity activity) {
		try {
			File mCurrentPhotoFile = new File(YConstance.saveUploadPicPath);
			if (!mCurrentPhotoFile.exists()) {
				mCurrentPhotoFile.mkdirs();// 创建照片的存储目录
			}

			fileImageName = System.currentTimeMillis()+"";

			mCurrentPhotoFile = new File(mCurrentPhotoFile, fileImageName);
			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
			activity.startActivityForResult(intent, RESULT_LOAD_PICTURE);
		} catch (ActivityNotFoundException e) {
			ToastUtil.showShortText(activity, "photoPickerNotFoundText");
			
		}
	}

	public static Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}
	
	public static void doPickPhotoFromGallery(Activity activity) {
		try {
			Intent intent = getPhotoPickIntent();
			activity.startActivityForResult(intent, RESULT_LOAD_IMAGE);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Intent getPhotoPickIntent() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		return intent;
	}
	
	public static String getRealPathFromURI(Uri originalUri, Activity activity) {
		String[] proj = { MediaStore.Images.Media.DATA};
		Cursor cursor = activity.getContentResolver().query(originalUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
		cursor.close();
		return path;
	}

	/**图片压缩**/
	public final static Bitmap compressBitmap(Bitmap bitmap, int reqsW,
			int reqsH) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.PNG, 100, baos);
			byte[] bts = baos.toByteArray();
			Bitmap res = compressBitmap(bts, reqsW, reqsH);
			baos.close();
			return res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return bitmap;
		}
	}

	public final static Bitmap compressBitmap(byte[] bts, int reqsW, int reqsH) {
		final Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(bts, 0, bts.length, options);
		options.inSampleSize = caculateInSampleSize(options, reqsW, reqsH);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(bts, 0, bts.length, options);
	}

	/**
	 * caculate the bitmap sampleSize
	 * 
	 * @return
	 */
	public final static int caculateInSampleSize(BitmapFactory.Options options,
			int rqsW, int rqsH) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (rqsW == 0 || rqsH == 0)
			return 1;
		if (height > rqsH || width > rqsW) {
			final int heightRatio = Math.round((float) height / (float) rqsH);
			final int widthRatio = Math.round((float) width / (float) rqsW);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
	/**
	 * 获取头像裁剪
	 * @param context
	 */
	
	public static void doUserPic(final Context context){
		
		Intent intent = new Intent(Intent.ACTION_PICK);
		
		
		intent.setType("image/*");

//		intent.putExtra("crop", "true");
//		
//
//		intent.putExtra("aspectX", 1);
//
//		intent.putExtra("aspectY", 1);
//
//		intent.putExtra("outputX", 300);
//
//		intent.putExtra("outputY", 300);
//
//		intent.putExtra("scale", true);

//		intent.putExtra("return-data", false);

//		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse("file:///sdcard/temp1.jpg"));

//		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

//		intent.putExtra("noFaceDetection", true); // no face detection

		((Activity)context).startActivityForResult(intent, 10086);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		final Context dialogContext = new ContextThemeWrapper(context,
//				android.R.style.Theme_Light);
//		String[] choices;
//		choices = new String[2];
//		choices[0] = "拍照"; // 拍照
//		choices[1] = "从相册中选择"; // 从相册中选择
//		final ListAdapter adapter = new ArrayAdapter<String>(dialogContext,
//				android.R.layout.simple_list_item_1, choices);
//		final AlertDialog.Builder builder = new AlertDialog.Builder(
//				dialogContext);
//		builder.setTitle("");
//		builder.setSingleChoiceItems(adapter, -1,
//				new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//						
//						String status = Environment
//								.getExternalStorageState();
//						
//						if (!status.equals(Environment.MEDIA_MOUNTED)) {
//							ToastUtil.showShortText(context, "无内存卡");
//							return ;
//						}
//						
//						switch (which) {
//						case 0: {
//							
//							// 判断是否有SD卡
//								//doTakePhoto(activity);
//							    
//							    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
//
//							    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse("file:///sdcard/temp1.jpg"));
//
//							    ((Activity)context).startActivityForResult(intent, 10086);
//							    
//							break;
//						}
//						case 1:
////							doPickPhotoFromGallery(activity);
//							Intent intent = new Intent(Intent.ACTION_PICK);
//							
//							
//							intent.setType("image/*");
//
////							intent.putExtra("crop", "true");
////							
////
////							intent.putExtra("aspectX", 1);
////
////							intent.putExtra("aspectY", 1);
////
////							intent.putExtra("outputX", 300);
////
////							intent.putExtra("outputY", 300);
////
////							intent.putExtra("scale", true);
//
////							intent.putExtra("return-data", false);
//
////							intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse("file:///sdcard/temp1.jpg"));
//
////							intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//
////							intent.putExtra("noFaceDetection", true); // no face detection
//
//							((Activity)context).startActivityForResult(intent, 10086);
//							break;
//						}
//					}
//				});
//		builder.create().show();
	}
	
	public static void cropImageUri(Activity activity,Uri uri){

	    Intent intent = new Intent("com.android.camera.action.CROP");
	    
	    if(uri==null){
	    	 intent.setDataAndType(Uri.parse("file:///sdcard/temp1.jpg"), "image/*");
	    }else{
	    	 intent.setDataAndType(uri, "image/*");
	    }
	    

	    intent.putExtra("crop", "true");

		intent.putExtra("aspectX", 1);

		intent.putExtra("aspectY", 1);

		intent.putExtra("outputX", 300);

		intent.putExtra("outputY", 300);

		intent.putExtra("scale", true);

		intent.putExtra("return-data", false);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse("file:///sdcard/temp.jpg"));

		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

		intent.putExtra("noFaceDetection", true); // no face detection

	    activity.startActivityForResult(intent, RESULT_LOAD_PICTURE);

	 }
}
