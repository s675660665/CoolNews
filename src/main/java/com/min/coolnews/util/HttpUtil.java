package com.min.coolnews.util;

import android.os.Looper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/2/2.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String type,final HttpCallBackListener httpCallBackListener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                HttpURLConnection connection = null;
                try {
                    URL url=new URL("http://apis.baidu.com/showapi_open_bus/channel_news/search_news?"+type);
                    connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setRequestProperty("apikey","e1ecbda44ba6d6c646d20a316bdd2dd4");
                    InputStream in=connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    if(httpCallBackListener!=null){
                        httpCallBackListener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (httpCallBackListener!=null){
                        httpCallBackListener.onError(e);
                    }
                }finally {
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
