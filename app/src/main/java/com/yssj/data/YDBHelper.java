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
import android.util.Log;

import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.entity.StockType;
import com.yssj.utils.LogYiFu;

public class YDBHelper {

	private static final String TAG = YDBHelper.class.getSimpleName();

	// 应该保存到xml里面，硬编码会导致同步开发出现更新问
	// 这是升级的数据库版本
	public static final int DB_UPDATEVERSION = 3;
	private  String DB_VERSION ="DataBaseVersion";

	private Context mContext;
	

	public YDBHelper(Context context) {
//		super(context, "yssj_data", null, 6);
		this.mContext = context;
		if(this.mContext==null){
//			this.mContext=YJApplication.instance;
		}
		SQLiteDatabase db=openDatabase();
		int version=this.mContext.getSharedPreferences(DB_VERSION, Context.MODE_PRIVATE).getInt(DB_VERSION, 0);

		//TODO:_MODIFY_start_由于项目里src/res/raw/yssjdata.db有问题，不能解析出来，增加了创建表
		//同时放了个临时的yssjdata.db
		db.execSQL(YDBConstance.TABLE_MESSAGE_CREATE_1);
		db.execSQL(YDBConstance.TABLE_CREATE_SORT);
		db.execSQL(YDBConstance.TABLE_CREATE_ATTR);
		db.execSQL(YDBConstance.TABLE_CREATE_TAG);
		db.execSQL(YDBConstance.TABLE_CREATE_BUS_TAG);
		db.execSQL(YDBConstance.TABLE_CREATE_TYPE_TAG);
		db.execSQL(YDBConstance.TABLE_CREATE_SUPP_LABEL);
		db.execSQL(YDBConstance.TABLE_CREATE_FRIEND_CIRCLE_TAG);
		db.execSQL(YDBConstance.TABLE_CREATE_SHOP_GROUP_LIST);
		db.execSQL(YDBConstance.TABLE_CREATE_STOCK_TYPE);
		db.execSQL(YDBConstance.TABLE_CREATE_FOOT_PRINT);
//		TODO:_MODIFY_end

		onUpgrade(db, version, 10);
	}
	
	// 创建或打开数据库
	public SQLiteDatabase openDatabase() {
		try {
			// /data/data/com.usky.wojingtong.activity/databases/wojt.sqlite
//			File dbFile = mContext.getDatabasePath(Environment
//					.getExternalStorageDirectory() + "/yssj/" + "yssj_data.db");
			File dbFile=mContext.getDatabasePath("/data/data/com.yssj.activity/databases/yssjdata.db");
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

				InputStream is = mContext.getResources().openRawResource(
						R.raw.yssjdata);
				FileOutputStream fos = new FileOutputStream(dbFile.getPath());
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) >= 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
				mContext.getSharedPreferences(DB_VERSION, Context.MODE_PRIVATE).edit().putInt(DB_VERSION, 6).commit();
			}
			String dbPath = dbFile.getPath();
			return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				File dbFile = mContext.getDatabasePath("/data/data/com.yssj.activity/databases/yssjdata.db");
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
 
					InputStream is = mContext.getResources().openRawResource(
							R.raw.yssjdata);
					FileOutputStream fos = new FileOutputStream(
							dbFile.getPath());
					byte[] buffer = new byte[8192];
					int count = 0;
					while ((count = is.read(buffer)) >= 0) {
						fos.write(buffer, 0, count);
					}
					fos.close();
					is.close();
					mContext.getSharedPreferences(DB_VERSION, Context.MODE_PRIVATE).edit().putInt(DB_VERSION, 5).commit();
				}
				String dbPath = dbFile.getPath();
				return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
			} catch (Exception ex) {
				// ex.printStackTrace();
			}
		}
		return null;
	}

	
	// 此方法只有第一次创建数据库的时候执行以后就不会再执行了
	/**@Override
	public void onCreate(SQLiteDatabase db) { 
		MyLogYiFu.e(TAG, "Version" + db.getVersion());
		// String createTable = YDBConstance
		// .getCreateMessageTableComman(DB_VERSION);
		// db.execSQL(createTable);
//
//		String createSortTable = YDBConstance
//				.getCreateSortInfoTableComman(DB_VERSION);
//		db.execSQL(createSortTable);
//		String createAttrTable = YDBConstance
//				.getCreateAttrInfoTableComman(DB_VERSION);
//		db.execSQL(createAttrTable);
//		String createTagTable = YDBConstance
//				.getCreateTagInfoTableComman(DB_VERSION);
//		db.execSQL(createTagTable);
//
//		db.execSQL(YDBConstance.TABLE_CREATE_BUS_TAG);
//		
//		String createStocketTable = YDBConstance.TABLE_CREATE_STOCK_TYPE;
//		db.execSQL(createStocketTable);
//
//		String createFootPrintTable = YDBConstance.TABLE_CREATE_FOOT_PRINT;
//		db.execSQL(createFootPrintTable);
//		openDatabase()

		MyLogYiFu.e(TAG, "OnCreate数据库添加表完成>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}*/

	// 第二次及以后调用此方法
	//@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		if(db==null){
			return;
		}
		
		if(oldVersion==newVersion){
			db.close();
			return ;
		}
		LogYiFu.e(TAG, "升级数据库，旧版本:" + oldVersion + " 新版本:" + newVersion);
		for (int i = oldVersion+1; i < newVersion+1; i++) {
			switch (i) {
			case 3:
				db.execSQL(YDBConstance.UPGRADE_SORT_INFO);
				// db.execSQL(YDBConstance.UPGRADE_BOX_2);
				LogYiFu.e(TAG, "升级数据库，旧版本:3 " + oldVersion + " 新版本:" + newVersion);

				mContext.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE)
						.edit().clear().commit();
				mContext.getSharedPreferences(Pref.sync_attr_date,
						Context.MODE_PRIVATE).edit().clear().commit();
				mContext.getSharedPreferences(Pref.sync_tag_date,
						Context.MODE_PRIVATE).edit().clear().commit();
				break;
			case 4:
				LogYiFu.e(TAG, "升级数据库，旧版本:4 " + oldVersion + " 新版本:" + newVersion);
				db.execSQL(YDBConstance.UPGRADE_TAG_INFO);
				mContext.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE)
						.edit().clear().commit();
				mContext.getSharedPreferences(Pref.sync_attr_date,
						Context.MODE_PRIVATE).edit().clear().commit();
				mContext.getSharedPreferences(Pref.sync_tag_date,
						Context.MODE_PRIVATE).edit().clear().commit();
				break;
			case 5:
				LogYiFu.e(TAG, "升级数据库，旧版本:5 " + oldVersion + " 新版本:" + newVersion);
				db.execSQL(YDBConstance.UPGRADE_2_SORT_INFO);
				mContext.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE)
						.edit().clear().commit();
				mContext.getSharedPreferences(Pref.sync_attr_date,
						Context.MODE_PRIVATE).edit().clear().commit();
				mContext.getSharedPreferences(Pref.sync_tag_date,
						Context.MODE_PRIVATE).edit().clear().commit();
				break;
			case 6:
				db.execSQL(YDBConstance.TABLE_CREATE_BUS_TAG);
				mContext.getSharedPreferences(Pref.sync,
						Context.MODE_PRIVATE).edit().putString(Pref.sync_bus_tag_date, "").commit();
				mContext.getSharedPreferences(DB_VERSION, Context.MODE_PRIVATE).edit().putInt(DB_VERSION, 6).commit();
				break;
			case 7:
//				db.execSQL(YDBConstance.UPGRADE_TAG_INFO2);
//				mContext.getSharedPreferences(DB_VERSION, Context.MODE_PRIVATE).edit().putInt(DB_VERSION, 7).commit();
//				mContext.getSharedPreferences(Pref.sync_tag_date,
//						Context.MODE_PRIVATE).edit().clear().commit();
				break;
			case 8:
//				db.execSQL(YDBConstance.TABLE_CREATE_TYPE_TAG);
//				db.execSQL(YDBConstance.TABLE_CREATE_SUPP_LABEL);
//				db.execSQL(YDBConstance.TABLE_CREATE_FRIEND_CIRCLE_TAG);
//				mContext.getSharedPreferences(Pref.sync,
//						Context.MODE_PRIVATE).edit().putString(Pref.sync_type_tag_data, "").commit();
//				mContext.getSharedPreferences(Pref.sync,
//						Context.MODE_PRIVATE).edit().putString(Pref.sync_supp_label_data, "").commit();
//				mContext.getSharedPreferences(Pref.sync,
//						Context.MODE_PRIVATE).edit().putString(Pref.sync_friend_circle_tag_data, "").commit();
//				mContext.getSharedPreferences(DB_VERSION, Context.MODE_PRIVATE).edit().putInt(DB_VERSION, 8).commit();
				break;
			case 9:
//				db.execSQL(YDBConstance.UPGRADE_SUPP_LABEL);
//				mContext.getSharedPreferences(Pref.sync,
//						Context.MODE_PRIVATE).edit().putString(Pref.sync_supp_label_data, "").commit();
//				mContext.getSharedPreferences(DB_VERSION, Context.MODE_PRIVATE).edit().putInt(DB_VERSION, 9).commit();
				break;
			case 10:
				db.execSQL(YDBConstance.TABLE_CREATE_SHOP_GROUP_LIST);
				mContext.getSharedPreferences(Pref.sync,
						Context.MODE_PRIVATE).edit().putString(Pref.sync_group_shop_list_data, "").commit();
				mContext.getSharedPreferences(DB_VERSION, Context.MODE_PRIVATE).edit().putInt(DB_VERSION, 10).commit();
				break;
			default:
				break;
			}
		}
		db.close();
	}

	// 查询数据,返回List<String>
	public List<String> queryToSimpleList(String sql) {
		List<String> list = new ArrayList<String>();
		SQLiteDatabase db = null;
		Cursor cur = null;
		try {
			db = openDatabase();
			cur = db.rawQuery(sql, null);
			cur.moveToFirst();
			while (!cur.isAfterLast()) {
				String str = cur.getString(0);
				list.add(str);
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

	// 查询数据,返回List<HashMap>
	public List<HashMap<String, String>> query(String sql) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(
				);
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

	// 查询数据,返回List<HashMap<String, Object>>
	public List<HashMap<String, Object>> queryToObject(String sql) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>(
				);
		SQLiteDatabase db = null;
		Cursor cur = null;
		try {
			db = openDatabase();
			cur = db.rawQuery(sql, null);
			// cur.moveToFirst();
			String[] columnNames = cur.getColumnNames();
//			System.out.println("数据总数：" + cur.getCount());
			while (cur != null && cur.moveToNext() && cur.getCount() > 0) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				for (String columnName : columnNames) {
					map.put(columnName,
							cur.getString(cur.getColumnIndex(columnName)));
				}
				list.add(map);
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
				try {
					db.execSQL(s);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
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

	/***
	 * 查询父类属性名称
	 * 
	 * @param pid
	 * @return
	 */
	public String queryParentAttr_name(String pid) {
		String attr_name = null;
		String sql = "select * from attr_info where _id = (select p_id from attr_info where _id=?)";
		String[] strArgs = { pid };
		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			db = openDatabase();
			cursor = db.rawQuery(sql, strArgs);
			while (cursor.moveToNext()) {
				attr_name = cursor
						.getString(cursor.getColumnIndex("attr_name"));

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (cursor != null) {
				cursor.close();
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return attr_name;

	}
	
	/***
	 * 查询排序ID
	 * 
	 * @param pid
	 * @return
	 */
	public String queryAttr_id(String attrName) {
		String attr_name = null;
		String sql = "select * from sort_info where sort_name = ?";
		String[] strArgs = { attrName };
		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			db = openDatabase();
			cursor = db.rawQuery(sql, strArgs);
			while (cursor.moveToNext()) {
				attr_name = cursor
						.getString(cursor.getColumnIndex("_id"));

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (cursor != null) {
				cursor.close();
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return attr_name;

	}
	
	/***
	 * 查询属性名称
	 * 
	 * @param pid
	 * @return
	 */
	public String querySort_name(String pid) {
		String attr_name = null;
		String sql = "select * from sort_info where _id = ?";
		String[] strArgs = { pid };
		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			db = openDatabase();
			cursor = db.rawQuery(sql, strArgs);
			while (cursor.moveToNext()) {
				attr_name = cursor
						.getString(cursor.getColumnIndex("sort_name"));

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (cursor != null) {
				cursor.close();
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return attr_name;

	}
	
	/***
	 * 查询属性名称
	 * 
	 * @param pid
	 * @return
	 */
	public String queryAttr_name(String pid) {
		String attr_name = null;
		String sql = "select * from attr_info where _id = ?";
		String[] strArgs = { pid };
		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			db = openDatabase();
			cursor = db.rawQuery(sql, strArgs);
			while (cursor.moveToNext()) {
				attr_name = cursor
						.getString(cursor.getColumnIndex("attr_name"));

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (cursor != null) {
				cursor.close();
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return attr_name;

	}

	/***
	 * 查询爱好名称
	 * 
	 * @param pid
	 * @return
	 */
	public String queryHobbyName(String pid) {
		String attr_name = "";
		String sql = "select * from tag_info where _id = ?";
		String[] strArgs = { pid };
		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			db = openDatabase();
			cursor = db.rawQuery(sql, strArgs);
			while (cursor.moveToNext()) {
				attr_name = cursor
						.getString(cursor.getColumnIndex("attr_name"));

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (cursor != null) {
				cursor.close();
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return attr_name;

	}

	public List<String> queryStock_Color_Szie(String sql, String args) {
		List<String> list = new ArrayList<String>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = openDatabase();
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				String color_size = cursor.getString(cursor
						.getColumnIndex(args));
				list.add(color_size);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
		}
		return list;

	}
	
	public StockType queryStock(String color, String size) {
		StockType stockType = null;
		String sql = "select * from stock_type where pic = ? and size = ?";
		SQLiteDatabase db = null;
		Cursor cursor = null;
		String[] strArgs = { color, size };
		try {
			db = openDatabase();
			cursor = db.rawQuery(sql, strArgs);
			while (cursor.moveToNext()) {
				stockType = new StockType();
				stockType.setPrice(cursor.getDouble(cursor
						.getColumnIndex("price")));
				stockType
						.setStock(cursor.getInt(cursor.getColumnIndex("stock")));
				stockType.setId(cursor.getInt(cursor.getColumnIndex("_id")));
				stockType.setColor_size(cursor.getString(cursor
						.getColumnIndex("color")));

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
		}
		return stockType;

	}
	
	
}
