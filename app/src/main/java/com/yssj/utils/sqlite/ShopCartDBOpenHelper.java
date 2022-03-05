package com.yssj.utils.sqlite;

import com.yssj.data.YDBConstance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShopCartDBOpenHelper extends SQLiteOpenHelper {

	public ShopCartDBOpenHelper(Context context) {
		super(context, ShopCartDB.NAME, null, ShopCartDB.VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// 创建表
		db.execSQL(ShopCartDB.TableBlack.CREATE_TABLE_SQL);

		db.execSQL(ShopCartDB.TableBlack.CREATE_TABLE_SQL_INVALID);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		for (int i = oldVersion + 1; i < newVersion + 1; i++) {
			switch (i) {
			case 2:
				db.execSQL(ShopCartDB.TableBlack.CREATE_TABLE_SQL_INVALID);
				break;
			case 3:
				db.execSQL(ShopCartDB.TableBlack.UPGRADE_1_SHOPCARTS);
				break;
			default:
				break;
			}
		}

	}

}
