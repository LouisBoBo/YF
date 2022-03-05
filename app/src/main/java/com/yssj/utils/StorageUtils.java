//package com.yssj.utils;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Environment;
//
///**
// * 保存图片，和录音
// * @author hosolo
// *
// */
//@SuppressLint("NewApi") 
//public class StorageUtils {
//	private static final String PICTURE = "Picture";
//	public static File MAIN_FILE = Environment.getExternalStoragePublicDirectory("Yeamo");
//	public static File PICTURE_DIR = new File(MAIN_FILE, PICTURE);
//	
//	public static boolean isExternalStorageWritable() {
//		/* Checks if external storage is available for read and write */
//	    String state = Environment.getExternalStorageState();
//	    if (Environment.MEDIA_MOUNTED.equals(state)) {
//	        return true;
//	    }
//	    return false;
//	}
//	
//	public static Uri saveBitmap(Context context, Uri photoUri) {
//		try {
//			if (photoUri == null) {
//				return null;
//			}
//			if (StorageUtils.isExternalStorageWritable()) {
//				InputStream input = context.getContentResolver().openInputStream(photoUri);
//				BitmapFactory.Options options = new BitmapFactory.Options();
//			    options.inJustDecodeBounds = false;
//			    Bitmap bmp = BitmapFactory.decodeStream(input, null, options);
//			    input.close();
//				
//				// Save the bitmap
//				File dir = StorageUtils.getPictureDir();
//				FileUtils.createNoMediaFile(dir);
//				SimpleDateFormat formater = 
//						new SimpleDateFormat("yyyyMMdd_hhmmss", Locale.getDefault());
//				String name = formater.format(new Date()) + ".jpg";
//				
//				File file = new File(dir, name);
//				ImageUtils.saveBitmap(context, file, bmp);
//				bmp.recycle();
//				
//				Uri pUri = Uri.fromFile(file);
//				return pUri;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	public static File getPictureDir() {
//		if (!PICTURE_DIR.exists()) {
//			PICTURE_DIR.mkdirs();
//		}
//		return PICTURE_DIR;
//	}
//}
