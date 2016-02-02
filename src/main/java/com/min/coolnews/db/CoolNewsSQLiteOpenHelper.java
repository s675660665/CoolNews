package com.min.coolnews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/1/13.
 */
public class CoolNewsSQLiteOpenHelper extends SQLiteOpenHelper{

    public static final String CREATE_NEWS="create table News(" +
            "id integer primary key autoincrement, " +
            "news_title text, " +
            "news_content text, " +
            "news_desc text, " +
            "news_link text, " +
            "news_source text)";

    public CoolNewsSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
