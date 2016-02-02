package com.min.coolnews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.min.coolnews.model.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public class CoolNewsDB {
    /**
     * 定义当前的数据库名
     */
    public static final String DB_NAME="cool_news";
    /**
     * 定义数据库的版本
     */
    public static final int DB_VERSION=1;
    private CoolNewsDB coolNewsDB;
    private SQLiteDatabase database;
    /**
     * 将数据库的构造方法私有化，提供实例方法获取数据库获取
     */
    private  CoolNewsDB(Context context){
        CoolNewsSQLiteOpenHelper helper=new CoolNewsSQLiteOpenHelper(context,DB_NAME,null,DB_VERSION);
        database=helper.getWritableDatabase();
    }
    /**
     * 实例化数据库操作类的方法
     */
    public synchronized CoolNewsDB getInstance(Context context){
        if(coolNewsDB==null){
            coolNewsDB=new CoolNewsDB(context);
        }
        return coolNewsDB;
    }
    /**
     * 存储新闻实例到数据库的方法
     */
    private void saveNews(News news){
        if(news!=null){
            ContentValues values=new ContentValues();
            values.put("news_title",news.getTitle());
            values.put("news_content",news.getContent());
            values.put("news_desc",news.getDesc());
            values.put("news_link",news.getLink());
            values.put("news_source",news.getSource());
            database.insert("News",null,values);
        }
    }
    /**
     * 从数据库中读取新闻数据的方法
     */
    private List<News> loadNews(){
        List<News> newsList=new ArrayList<News>();
        Cursor cursor=database.query("News",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                News news=new News();
                news.setTitle(cursor.getString(cursor.getColumnIndex("news_title")));
                news.setContent(cursor.getString(cursor.getColumnIndex("news_content")));
                news.setDesc(cursor.getString(cursor.getColumnIndex("news_desc")));
                news.setLink(cursor.getString(cursor.getColumnIndex("news_link")));
                news.setSource(cursor.getString(cursor.getColumnIndex("news_source")));
                newsList.add(news);
            }while(cursor.moveToNext());
        }
        return newsList;
    }
}
