package com.yssj.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.utils.LogYiFu;

public class DBService extends SQLiteOpenHelper {
	private static final String tag = DBService.class.getName();

	private Context context;

	public static DBService instance;
	public DBService(Context _context) {
		super(_context, "area_data.db", null, 1);
		this.context = _context;
	}
	
	
	public static DBService getIntance(){
		if(instance==null){
			instance=new DBService(YJApplication.mContext);
		}
		return instance;
	}
	
	// 创建或打开数据库
	public SQLiteDatabase openDatabase() {
		try {
			// /data/data/com.usky.wojingtong.activity/databases/wojt.sqlite
			File dbFile = context.getDatabasePath(Environment
					.getExternalStorageDirectory() + "/yssj/" + "area_data.db");
			if (!dbFile.exists()) { // 如果磁盘中没有数据库，那么从raw文件中复制或者创建

				boolean ret = false;
				File dir = dbFile.getParentFile();
				if (!dir.exists()) {
					ret = dir.mkdirs();
				}

				boolean ret2 = dbFile.createNewFile();
				// if (ret2 == true) {
				// initDbObject();
				// }

				InputStream is = context.getResources().openRawResource(
						R.raw.area_data);
				FileOutputStream fos = new FileOutputStream(dbFile.getPath());
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) >= 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			String dbPath = dbFile.getPath();
			return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// /data/data/com.usky.wojingtong.activity/databases/wojt.sqlite
				File dbFile = context.getDatabasePath("area_data.db");
				if (!dbFile.exists()) { // 如果磁盘中没有数据库，那么从raw文件中复制或者创建

					boolean ret = false;
					File dir = dbFile.getParentFile();
					if (!dir.exists()) {
						ret = dir.mkdirs();
					}

					boolean ret2 = dbFile.createNewFile();
					// if (ret2 == true) {
					// initDbObject();
					// }
 
					InputStream is = context.getResources().openRawResource(
							R.raw.area_data);
					FileOutputStream fos = new FileOutputStream(
							dbFile.getPath());
					byte[] buffer = new byte[8192];
					int count = 0;
					while ((count = is.read(buffer)) >= 0) {
						fos.write(buffer, 0, count);
					}
					fos.close();
					is.close();

				}
				String dbPath = dbFile.getPath();
				return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
			} catch (Exception ex) {
				// ex.printStackTrace();
				LogYiFu.e(tag, ex.toString());
			}
			LogYiFu.e(tag, e.toString());
		}
		return null;
	}

	
	
	
	// 创建或打开数据库(购物车)
		public SQLiteDatabase openDatabaseShopCart() {
			try {
				// /data/data/com.usky.wojingtong.activity/databases/wojt.sqlite
				File dbFile = context.getDatabasePath(Environment
						.getExternalStorageDirectory() + "/yssj/" + "shopCart_data.db");
//				if (!dbFile.exists()) { // 如果磁盘中没有数据库，那么从raw文件中复制或者创建
//
//					boolean ret = false;
//					File dir = dbFile.getParentFile();
//					if (!dir.exists()) {
//						ret = dir.mkdirs();
//					}
//
//					boolean ret2 = dbFile.createNewFile();
//					// if (ret2 == true) {
//					// initDbObject();
//					// }
//
//					InputStream is = context.getResources().openRawResource(
//							R.raw.area_data);
//					FileOutputStream fos = new FileOutputStream(dbFile.getPath());
//					byte[] buffer = new byte[8192];
//					int count = 0;
//					while ((count = is.read(buffer)) >= 0) {
//						fos.write(buffer, 0, count);
//					}
//					fos.close();
//					is.close();
//				}
				String dbPath = dbFile.getPath();
				return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
			} catch (Exception e) {
				// e.printStackTrace();
				try {
					// /data/data/com.usky.wojingtong.activity/databases/wojt.sqlite
					File dbFile = context.getDatabasePath("shopCart_data.db");
//					if (!dbFile.exists()) { // 如果磁盘中没有数据库，那么从raw文件中复制或者创建
//
//						boolean ret = false;
//						File dir = dbFile.getParentFile();
//						if (!dir.exists()) {
//							ret = dir.mkdirs();
//						}
//
//						boolean ret2 = dbFile.createNewFile();
//						// if (ret2 == true) {
//						// initDbObject();
//						// }
//	 
//						InputStream is = context.getResources().openRawResource(
//								R.raw.area_data);
//						FileOutputStream fos = new FileOutputStream(
//								dbFile.getPath());
//						byte[] buffer = new byte[8192];
//						int count = 0;
//						while ((count = is.read(buffer)) >= 0) {
//							fos.write(buffer, 0, count);
//						}
//						fos.close();
//						is.close();
//
//					}
					String dbPath = dbFile.getPath();
					return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
				} catch (Exception ex) {
					// ex.printStackTrace();
					LogYiFu.e(tag, ex.toString());
				}
				LogYiFu.e(tag, e.toString());
			}
			return null;
		}
	
	
	
	
	public void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
//					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
//			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}

	}

	public String queryAreaNameById(int id) {
		String areaName = null;
		String sql = "select * from areatbl where id = ?";
		String[] strArgs = { id+"" };
		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			db = openDatabase();
			cursor = db.rawQuery(sql, strArgs);
			while (cursor.moveToNext()) {
				areaName = cursor.getString(cursor.getColumnIndex("AreaName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return areaName;
	}

	// 更新数据
	public void update(String sql) {
		SQLiteDatabase db = null;
		try {
			db = openDatabase();
			db.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
			if (db != null) {
				db.close();
			}
		} finally {
			if (db != null) {
				db.close();
			}
		}

	}

	// 删除数据
	public void delete(String sql) {
		SQLiteDatabase db = null;
		try {
			db = openDatabase();
			db.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
			if (db != null) {
				db.close();
			}
		} finally {
			if (db != null) {
				db.close();
			}
		}

	}

	/*
	 * // 更新数据 public void update(String[] sql) { SQLiteDatabase db = null; try
	 * { db = openDatabase(); for (String s : sql) { db.execSQL(s); } } catch
	 * (Exception e) { e.printStackTrace(); if (db != null) { db.close(); } }
	 * finally { if (db != null) { db.close(); } } }
	 */

	// 更新数据
	public void update(String[] sql) {
		SQLiteDatabase db = null;
		try {
			db = openDatabase();
			db.beginTransaction();
			for (String s : sql) {
				if (null != sql && !"".equals(sql) && !"null".equals(sql))
					db.execSQL(s);
			}
			// 设置事务标志为成功，当结束事务时就会提交事务
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			if (db != null) {
				db.close();
			}
		} finally {
			// 结束事务
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}
	}

	// 更新数据
	public void update(List<String> sqls) {
		SQLiteDatabase db = null;
		try {
			db = openDatabase();
			for (String s : sqls) {
				db.execSQL(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (db != null) {
				db.close();
			}
		} finally {
			if (db != null) {
				db.close();
			}
		}

	}

	// 查询数据
	public Cursor query(String sql, String[] args) {
		SQLiteDatabase db = null;
		Cursor cur = null;
		try {
			db = openDatabase();
			db.beginTransaction();
			cur = db.rawQuery(sql, args);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			if (db != null) {
				db.endTransaction();
				db.close();
			}
		} finally {
			if (db != null) {
				db.endTransaction();
				db.close();
			}
		}
		return cur;
	}

	// 查询数据,返回List<HashMap>
	public List<HashMap<String, String>> query(String sql) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(
				0);
		SQLiteDatabase db = null;
		Cursor cur = null;
		try {
			db = openDatabase();
			cur = db.rawQuery(sql, null);
			cur.moveToFirst();
			String[] columnNames = cur.getColumnNames();
			while (!cur.isAfterLast()) {
				HashMap<String, String> map = new HashMap<String, String>();
				for (String columnName : columnNames) {
					map.put(columnName,
							cur.getString(cur.getColumnIndex(columnName)));
				}
				list.add(map);
				cur.moveToNext();
			}

		} catch (Exception e) {
			LogYiFu.e(sql, e.toString());
			if (db != null) {
				db.close();
			}
			if (cur != null) {
				cur.close();
			}
		} finally {
			if (db != null) {
				db.close();
			}
			if (cur != null) {
				cur.close();
			}
		}
		return list;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			File dbFile = context.getDatabasePath("area_data.db");
			Log.i(tag, "dbFile: " + dbFile);
		} catch (Exception e) {
			LogYiFu.e(tag, e.toString());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
