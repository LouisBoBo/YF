package com.yssj.utils.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.YJApplication;
import com.yssj.utils.YCache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * 分类页面搜索记录
* @author Administrator
* @date 2016年12月29日下午3:41:31
 */
public class RecordsDao {
	private RecordSQLiteOpenHelper recordHelper;
 
	private  SQLiteDatabase recordsDb;
    private Context context;
    public RecordsDao(Context context) {
        recordHelper = new RecordSQLiteOpenHelper(context);
        this.context = context;
    }
 
    //添加搜索记录 
    public void addRecords(HashMap<String, String> record) {
 
        if (isHasRecord(record.get("class_name"))) {
        	//如果存在先删除 再存入新的数据
        	deleteRecord(record.get("class_name"));
        }
        recordsDb = recordHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("class_name", record.get("class_name"));
        values.put("class_id", record.get("class_id"));
        values.put("user_id", record.get("user_id"));
        //添加
        recordsDb.insert("records", null, values);
        //关闭
        recordsDb.close();
    }
 
    //判断是否含有该搜索记录
    public boolean isHasRecord(String record) {
        boolean isHasRecord = false;
        recordsDb = recordHelper.getReadableDatabase();
        //区分用户 未登录时候默认id ：-1
        Cursor cursor = recordsDb.query("records", null, "user_id = ?", new String []{YJApplication.instance.isLoginSucess()?YCache.getCacheUserSafe(context).getUser_id()+"":"-1"}, null, null, null);
        while (cursor.moveToNext()) {
            if (record.equals(cursor.getString(cursor.getColumnIndexOrThrow("class_name")))) {
                isHasRecord = true;
            }
        }
        //关闭数据库
        recordsDb.close();
        return isHasRecord;
    }
 
    //获取全部搜索记录
    public List<HashMap<String, String>> getRecordsList() {
        List<HashMap<String, String>> recordsList = new ArrayList<HashMap<String, String>>();
        recordsDb = recordHelper.getReadableDatabase();
        //区分用户 未登录时候默认id ：-1
        Cursor cursor = recordsDb.query("records", null, "user_id = ?", new String []{YJApplication.instance.isLoginSucess()?YCache.getCacheUserSafe(context).getUser_id()+"":"-1"}, null, null, null);
        while (cursor.moveToNext()) {
            String class_name = cursor.getString(cursor.getColumnIndexOrThrow("class_name"));
            String class_id = cursor.getString(cursor.getColumnIndexOrThrow("class_id"));
            String user_id = cursor.getString(cursor.getColumnIndexOrThrow("user_id"));
            HashMap<String, String> recordMap = new HashMap<String, String>();
            recordMap.put("class_name", class_name);
            recordMap.put("class_id", class_id);
            recordMap.put("user_id", user_id);
            recordsList.add(0,recordMap);
        }
        //关闭数据库
        recordsDb.close();
        return recordsList;
    }
 
//    //模糊查询
//    public List<String> querySimlarRecord(String record){
//        String queryStr = "select * from records where name like '%" + record + "%' order by name ";
//        List<String> similarRecords = new ArrayList<String>();
//        Cursor cursor= recordHelper.getReadableDatabase().rawQuery(queryStr,null);
// 
//        while (cursor.moveToNext()) {
//            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
//            similarRecords.add(name);
//        }
//        return similarRecords;
//    }
    
    //删除某一条数据
    public void deleteRecord(String record){
    	recordsDb = recordHelper.getWritableDatabase();
//    	String queryStr = "delete from records where name =" + record ;
//    	recordsDb.execSQL(queryStr);
    	//区分用户 未登录时候默认id ：-1
    	recordsDb.delete("records", "class_name = ? and user_id = ?", new String[]{record,YJApplication.instance.isLoginSucess()?YCache.getCacheUserSafe(context).getUser_id()+"":"-1"});
    	recordsDb.close();
    }
 
    //清空搜索记录
    public void deleteAllRecords() {
        recordsDb = recordHelper.getWritableDatabase();
        //区分用户 未登录时候默认id ：-1
        recordsDb.execSQL("delete from records where user_id ="+(YJApplication.instance.isLoginSucess()?YCache.getCacheUserSafe(context).getUser_id()+"":"-1"));
 
        recordsDb.close();
    }
 
}