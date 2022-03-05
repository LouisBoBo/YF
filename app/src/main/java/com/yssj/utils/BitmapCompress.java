package com.yssj.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BitmapCompress {
	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			// final int heightRatio = Math.round((float) height
			// / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			// inSampleSize = heightRatio < widthRatio ? heightRatio :
			// widthRatio;
			inSampleSize = widthRatio;
			LogYiFu.e("widthRatio", widthRatio + " width:" + width + " height"
					+ height);
		}

		return inSampleSize;
	}

	/**
	 * 根据路径获得突破并压缩返回String图片路径
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static String getSmallBitmapPath(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		// options.inSampleSize = calculateInSampleSize(options, 480, 800);
		options.inSampleSize = calculateInSampleSize(options, 900);
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
//		bitmap = compressImage(bitmap);
		File file = new File(filePath);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}

	// 进行质量压缩
	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于200kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			int baosLength = baos.toByteArray().length;
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	// public static String bitmapToString(String filePath) {
	//
	// Bitmap bm = getSmallBitmap(filePath);
	//
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
	// byte[] b = baos.toByteArray();
	//
	// return Base64.encodeToString(b, Base64.DEFAULT);
	//
	// }

}
