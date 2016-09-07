package com.feicui.contacts.android8_30.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.feicui.contacts.android8_30.entity.TypeEntry;

public class MyOpenHelder extends SQLiteOpenHelper {
    //数据库版本
    public static final int DATABASE_VERSION = 1;
    //数据库名称
    public  static final String DATABASE_NAME = "phone.db";

    public MyOpenHelder(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TypeEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TypeEntry.SQL_DELETE_TABLE);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
