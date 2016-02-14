package com.min.coolnews.util;

/**
 * Created by Administrator on 2016/2/2.
 */
public interface HttpCallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}
