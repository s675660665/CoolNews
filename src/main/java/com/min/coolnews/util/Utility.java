package com.min.coolnews.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.min.coolnews.model.News;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 定义有关的数据操作的方法
 * 完成服务器返回数据的解析
 * 定义通过url完成图片获取的方法
 * Created by Administrator on 2016/2/2.
 */
public class Utility {

    private static ImageView content = null;

    /**
     * 解析JSON数据
     */
    public synchronized static List<News> handleNewsResponse(String response) {
        List<News> newsList = new ArrayList<News>();
        try {
            JSONObject all = new JSONObject(response);
            JSONObject responseBody = all.getJSONObject("showapi_res_body");
            JSONObject pageBean = responseBody.getJSONObject("pagebean");
            JSONArray jsonArray = pageBean.getJSONArray("contentlist");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String desc = jsonObject.getString("desc");
                String source = jsonObject.getString("source");
                String pubDate = jsonObject.getString("pubDate");
                String link = jsonObject.getString("link");
                JSONArray image = jsonObject.getJSONArray("imageurls");
                List<String> urlList = new ArrayList<>();
                for (int j = 0; j < image.length(); j++) {
                    JSONObject imageurl = image.getJSONObject(j);
                    String url = imageurl.getString("url");
                    urlList.add(url);
                }
                News news = new News();
                news.setTitle(title);
                news.setDesc(desc);
                news.setSource(source);
                news.setPubDate(pubDate);
                news.setLink(link);
                news.setImageUrls(urlList);
                newsList.add(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }

    /**
     * 通过url从网络中获取图片的方法
     */
    public static void loadImageFromUrl(final String url, final ImageView imageView) {
        content = imageView;
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        Bitmap bitmap= (Bitmap) msg.obj;
                        imageView.setImageBitmap(bitmap);
                        break;
                    default:
                        break;
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                try {
                    URL address = new URL(url);
                    connection = (HttpURLConnection) address.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    if (connection.getResponseCode() == 200) {
                        InputStream inputStream = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Log.d("bitmap", bitmap.toString());
                        Message message = new Message();
                        message.what = 1;
                        message.obj = bitmap;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

