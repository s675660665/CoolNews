package com.min.coolnews.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.Window;

import com.min.coolnews.R;

/**
 * 新闻的内容页面,包含一个的图片显示的imageview，一个文字显示的textview
 * Created by Administrator on 2016/2/2.
 */
public class NewsContentActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
    }
}
