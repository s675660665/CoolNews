package com.min.coolnews.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.min.coolnews.R;
import com.min.coolnews.model.News;
import com.min.coolnews.util.HttpCallBackListener;
import com.min.coolnews.util.HttpUtil;
import com.min.coolnews.util.NewsAdapter;
import com.min.coolnews.util.Utility;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    final List<News> list= (List<News>) msg.obj;
                    final NewsAdapter adapter=new NewsAdapter(list,MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.invalidate();
                    adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent=new Intent(MainActivity.this,NewsContentActivity.class);
                            intent.putExtra("link",list.get(position).getLink());
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                                    view,"share").toBundle());
                        }
                    });
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView= (RecyclerView) findViewById(R.id.news_list);

        final SwipeRefreshLayout refreshLayout= (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFromServer("5572a108b3cdc86cf39001cd");
                refreshLayout.setRefreshing(false);
            }
        });
        loadFromServer("5572a108b3cdc86cf39001cd");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.news_inland){
            loadFromServer("5572a108b3cdc86cf39001cd");
        } else if (id == R.id.news_abort) {
            loadFromServer("5572a108b3cdc86cf39001ce");
        } else if (id == R.id.news_finance) {
            loadFromServer("5572a108b3cdc86cf39001d0");
        } else if (id == R.id.news_car) {
            loadFromServer("5572a108b3cdc86cf39001d0");
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 从服务器读取新闻数据
     * @param type
     */
    private void loadFromServer(String type){
        HttpUtil.sendHttpRequest(type, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                final List<News> list=Utility.handleNewsResponse(response);
                Message message=new Message();
                message.what=1;
                message.obj=list;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,"获取数据失败了哦",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
