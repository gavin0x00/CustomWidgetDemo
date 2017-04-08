package com.newtrekwang.customwidgetdemo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDataBaseHelper extends SQLiteOpenHelper {
    public final static String CREATE_TABLE_SQL="create table dict (_id integer primary key autoincrement,word text,detail text)";

    public MyDataBaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//        第一次使用数据库是自动建表
        db.execSQL(CREATE_TABLE_SQL);
    }
/**
 * 升级数据是调用
 */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
