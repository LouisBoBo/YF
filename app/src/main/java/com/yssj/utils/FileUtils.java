package com.yssj.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

public class FileUtils {
	
	/**
	 * <pre>
     * close
     * </pre>
	 * @param closeable
	 */
	public static void close(Closeable closeable)
	{
		if (closeable == null)
		{
			return;
		}
		try
		{
			closeable.close();
		}
		catch (IOException e)
		{
		}
	}
	/** 将图片转成String */
	public static String getImageString(String imgFilePath) {
		String imageString = null;
		Bitmap mBitmap = BitmapFactory.decodeFile(imgFilePath);
		if (mBitmap != null) {
//			Matrix matrix = new Matrix();
//			int mWidth = mBitmap.getWidth();
//			int mHeight = mBitmap.getHeight();
//			float scaleWidth = (float) 150 / mWidth;
//			float scaleHeight = (float) 150 / mHeight;
//			LogYiFu.i("scale", scaleWidth + "++++++++++++" + scaleHeight);
//			matrix.postScale(scaleWidth, scaleHeight);
//			Bitmap newBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
//					mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			mBitmap.compress(CompressFormat.JPEG, 100, out);
			byte[] bytes = out.toByteArray();
			imageString = Base64.encodeToString(bytes, Base64.DEFAULT);
//			System.out.println(imageString);
		}
		return imageString;
	}

	/** 格式化文件大�? */
	public static String formatSize(long size) {
		final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };

		int i = 0;
		for (; i < units.length; i++) {
			if (size < Math.pow(1024, i + 1))
				break;
		}
		return ((long) (size / Math.pow(1024, i) * 100) / 100.0) + units[i];
	}

	public static String getFileExtension(File file) {
		String fileName = file.getName();
		int i = fileName.lastIndexOf('.');
		String extension = "";
		if (i > 0) {
			extension = fileName.substring(i + 1);
		}
		return extension;
	}

	public static String getEnvironmentPath()
	{
		File dir = new File(Environment.getExternalStorageDirectory(), "lcapp");
		if (!dir.exists())
		{
			dir.mkdir();
//			setNoMediaFlag(dir);
		}
		return dir.getPath();
	}
	
	public static File getAppFile()
	{
		File dir = new File(Environment.getExternalStorageDirectory(), "yssjaa");
		if (!dir.exists())
		{
			dir.mkdir();
//			setNoMediaFlag(dir);
		}
		return dir;
	}
	
	/** 对象序列�? */
	public static void ObjectSerialization(File file, Object object)
			throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				fileOutputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.flush();
		objectOutputStream.close();
		objectOutputStream = null;
	}

	/** 对象反序列化 */
	public static Object ObjectDeserialization(File file)
			throws StreamCorruptedException, IOException,
			ClassNotFoundException {
		FileInputStream fileInputStream = new FileInputStream(file);
		ObjectInputStream objectInpuStream = new ObjectInputStream(
				fileInputStream);
		Object object = objectInpuStream.readObject();
		objectInpuStream.close();
		objectInpuStream = null;
		return object;
	}

	/** 复制文件 */
	public static void copyFile(File sourceFile, File destFile)
			throws IOException {
		if (!destFile.exists()) {
			File pf = destFile.getParentFile();
			if (!pf.exists()) {
				pf.mkdirs();
			}
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;

		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}
	}

	public static void copyFdToFile(FileDescriptor src, File dst)
			throws IOException {
		if (!dst.exists()) {
			File pf = dst.getParentFile();
			if (!pf.exists()) {
				pf.mkdirs();
			}
			dst.createNewFile();
		}
		FileChannel inChannel = new FileInputStream(src).getChannel();
		FileChannel outChannel = new FileOutputStream(dst).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} finally {
			if (inChannel != null)
				inChannel.close();
			if (outChannel != null)
				outChannel.close();
		}
	}

	public static boolean copyAssetsToSdCard(Context context, String name,
			String path) {
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			is = context.getAssets().open(name);

			File file = new File(path);
			File pf = file.getParentFile();
			if (!pf.exists()) {
				pf.mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();

			fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
					fos = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/** 删除文件�? */
	public static void deleteAllFilesOfDir(File path, boolean isDeleteSelf) {
		if (path == null || !path.exists()) {
			return;
		}
		// 如果是文件就直接删除�?
		if (path.isFile()) {
			path.delete();
			return;
		}
		
		try {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteAllFilesOfDir(files[i], isDeleteSelf);
			}
			if (isDeleteSelf) {
				path.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/** 创建 .nomeadia */
	public static void createNoMediaFile(File parentFile) {
		try {
			// if (!parentFile.exists()) {
			// parentFile.mkdirs();
			// }
			File file = new File(parentFile, ".nomedia");
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压 zip
	 * 
	 * @param file
	 *            �?要解压的文件
	 */
	public static boolean unpackZip(File file) {
		InputStream is = null;
		;
		ZipInputStream zis = null;
		try {
			is = new FileInputStream(file);
			zis = new ZipInputStream(new BufferedInputStream(is));
			ZipEntry ze;
			byte[] buffer = new byte[1024];

			File parFile = file.getParentFile();
			String filename;
			while ((ze = zis.getNextEntry()) != null) {
				// zapis do souboru
				filename = ze.getName();
				// filename = filename.substring(filename.lastIndexOf("/") + 1);
//				System.out.println(filename);

				// Need to create directories if not exists, or
				// it will generate an Exception...
				File fmd = new File(parFile, filename);
				if (ze.isDirectory()) {
					fmd.mkdirs();
					continue;
				}

				FileOutputStream fout = new FileOutputStream(fmd);

				// cteni zipu a zapis
				int count;
				while ((count = zis.read(buffer)) != -1) {
					fout.write(buffer, 0, count);
				}
				zis.closeEntry();
				fout.close();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (zis != null) {
					zis.close();
					zis = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * 压缩 zip
	 */
	public static void compress() {

	}

	/**
	 * 压缩目录
	 */
	public static void compressDir(File file) {

	}

	/**
	 * 压缩 文件
	 */
	public static void compressFile(File dir) {

	}

	public final static String INVALID_CHAR = "/\\:*?<>|\"";

	// Android获取�?个用于打�?APK文件的intent
	public static Intent getAllIntent(String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "*/*");
		return intent;
	}

	// Android获取�?个用于打�?APK文件的intent
	public static Intent getApkFileIntent(String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		return intent;
	}

	// Android获取�?个用于打�?VIDEO文件的intent
	public static Intent getVideoFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	// Android获取�?个用于打�?AUDIO文件的intent
	public static Intent getAudioFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	// Android获取�?个用于打�?Html文件的intent
	public static Intent getHtmlFileIntent(String param) {

		Uri uri = Uri.parse(param).buildUpon()
				.encodedAuthority("com.android.htmlfileprovider")
				.scheme("content").encodedPath(param).build();
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		return intent;
	}

	// Android获取�?个用于打�?图片文件的intent
	public static Intent getImageFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "image/*");
		return intent;
	}

	// Android获取�?个用于打�?PPT文件的intent
	public static Intent getPptFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	// Android获取�?个用于打�?Excel文件的intent
	public static Intent getExcelFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;
	}

	// Android获取�?个用于打�?Word文件的intent
	public static Intent getWordFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	// Android获取�?个用于打�?CHM文件的intent
	public static Intent getChmFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/x-chm");
		return intent;
	}

	// Android获取�?个用于打�?文本文件的intent
	public static Intent getTextFileIntent(String param, boolean paramBoolean) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (paramBoolean) {
			Uri uri1 = Uri.parse(param);
			intent.setDataAndType(uri1, "text/plain");
		} else {
			Uri uri2 = Uri.fromFile(new File(param));
			intent.setDataAndType(uri2, "text/plain");
		}
		return intent;
	}

	// Android获取�?个用于打�?PDF文件的intent
	public static Intent getPdfFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}

	public static boolean fileExist(String filePath) {
		return new File(filePath).exists() ? true : false;
	}

	/**
	 * 复制文件
	 * 
	 * @param filePath
	 * @param newPath
	 */
	public static void copyFile(String filePath, String newPath) {
		if (!StringUtils.isEmpty(filePath) && !StringUtils.isEmpty(newPath)) {
			BufferedOutputStream bops = null;
			BufferedInputStream bips = null;
			FileInputStream ins = null;
			File outFile = new File(newPath);
			try {
				if (!outFile.getParentFile().isDirectory()) {
					outFile.getParentFile().mkdirs();
				} else {
					if (!outFile.exists()) {
						outFile.createNewFile();
					}
				}
				ins = new FileInputStream(filePath);
				bips = new BufferedInputStream(ins);
				bops = new BufferedOutputStream(new FileOutputStream(outFile));
				byte[] boxes = new byte[512];
				int byts = 0;
				while ((byts = bips.read(boxes)) != -1) {
					bops.write(boxes, 0, byts);
				}
				bops.flush();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("CREATE FILE TO [" + newPath
						+ "] ERROR:", e);
			} finally {
				CloseUtils.closeInputStream(ins);
				CloseUtils.closeInputStream(bips);
				CloseUtils.closeOutputStream(bops);
			}
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param btyes
	 * @param filePath
	 * @throws PlatformException
	 */
	public static void createFile(InputStream ins, String filePath) {
		if (ins != null && !StringUtils.isEmpty(filePath)) {
			BufferedOutputStream bops = null;
			BufferedInputStream bips = null;
			try {
				bips = new BufferedInputStream(ins);
				bops = new BufferedOutputStream(new FileOutputStream(filePath));
				int byts = 0;
				while ((byts = bips.read()) != -1) {
					bops.write(byts);
				}
				bops.flush();
			} catch (Exception e) {
				e.printStackTrace();
				// throw new PlatformException("创建文件到路径["+filePath+"]出错:",e);
			} finally {
				CloseUtils.closeInputStream(bips);
				CloseUtils.closeOutputStream(bops);

			}
		}
	}

	/**
	 * @param filePath
	 * @return
	 */
	public static void deleteFiles(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	public static void deleteFile(String matchStr, String unMatchStr,
			String dirPath) {
		File dir = new File(dirPath);
		File[] fileList = dir.listFiles();
		for (File file : fileList) {
			String tempFileName = file.getName();
			if (tempFileName.contains(matchStr)
					&& !tempFileName.contains(unMatchStr)) {
				file.delete();
			}
		}
	}

	/**
	 * 验证文件名的合法�?
	 * 
	 * @param fileName
	 */
	public static boolean validFileName(String fileName) {
		String regex = "[^/\\:*?<>|\"]+";
		if (fileName.matches(regex)) {
			return true;
		}
		return false;

	}

	/**
	 * 把字符串存储到txt文件当中
	 * 
	 * @param savePath
	 *            保存路径
	 * @param name
	 *            存储文件�?
	 * @param content
	 *            字符�?
	 * @return
	 */
	public static boolean createFileByContent(String savePath, String name,
			String content) {
		return createFileByContent(savePath, name, content, false);
	}

	/**
	 * 把字符串存储到txt文件当中
	 * 
	 * @param savePath
	 *            保存路径
	 * @param name
	 *            存储文件�?
	 * @param content
	 *            字符�?
	 * @param append
	 *            是否追加
	 * @return
	 */
	public static boolean createFileByContent(String savePath, String name,
			String content, boolean append) {
		try {
			FileOutputStream fOut = null;
			File dir = new File(savePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(savePath + File.separator + name);
			fOut = new FileOutputStream(file, append);
			if (content == null) {
				content = "";
			}
			fOut.write(content.getBytes());
			fOut.flush();
			fOut.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将bitmap存储成文�?
	 * 
	 * @param savePath
	 * @param name
	 * @param bitmap
	 * @return
	 */
	public static boolean saveBitmap(String savePath, String name, Bitmap bitmap) {
		FileOutputStream fOut = null;
		try {
			File dir = new File(savePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(savePath + File.separator + name);
			fOut = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			CloseUtils.closeOutputStream(fOut);
			return false;
		}
	}

}
