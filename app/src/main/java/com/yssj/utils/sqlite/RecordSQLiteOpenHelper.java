package com.yssj.utils.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 搜索记录的 数据库
* @author Administrator
* @date 2016年12月29日下午3:06:20
 */
public class RecordSQLiteOpenHelper extends SQLiteOpenHelper {
 
    private final static String DB_NAME = "RecordTemp.db";
    private final static int DB_VERSION = 1;
 
    public RecordSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlStr = "CREATE TABLE IF NOT EXISTS records (_id INTEGER PRIMARY KEY AUTOINCREMENT, class_name TEXT,class_id TEXT,user_id TEXT);";
        db.execSQL(sqlStr);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
    }
}