package com.lp.double_b.view.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/5/24.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
   private static final String DB_NAME="novel.db";
    public static final String TABLE_NAME="novel";
    private static final int DB_VERSION=1;
    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
         }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String netBookSQL= "create table " + TABLE_NAME +" (Id integer primary key, title text, bookId text, author text, cat text, image text, shortIntro text )";
        db.execSQL(netBookSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS novel" + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

}
