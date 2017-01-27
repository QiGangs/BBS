package com.yctu.bbs.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.yctu.bbs.R;
import com.yctu.bbs.modelutil.Blogx;
import com.yctu.bbs.recycleutil.BlogAdapter;
import com.yctu.bbs.service.CBlog;
import com.yctu.bbs.util.OnRecyclerViewListener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BlogsListActivity extends AppCompatActivity {
    private String account;
    private String type;

    private RecyclerView recyclerView;
    private ArrayList infolist = null;
    private CBlog cBlog = new CBlog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs_list);
        account = getIntent().getStringExtra("account");
        type = getIntent().getStringExtra("type");

        setTitle("帖子展示");

        recyclerView = (RecyclerView)findViewById(R.id.bloglist_rlv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BlogsListActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        if("collect".equals(type)){
            cBlog.GetCollectBlogByAccount(account,callback);
        }else{
            cBlog.GetAllBlogByAccount(account,callback);
        }

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                Toast.makeText(BlogsListActivity.this,"网络异常",Toast.LENGTH_LONG).show();
                return;
            }
            if(msg.obj.toString().equals("[]")){
                Toast.makeText(BlogsListActivity.this,"没有数据",Toast.LENGTH_LONG).show();
                return;
            }
            infolist = cBlog.GetBlogxList(msg.obj.toString());
            BlogAdapter adapter = new BlogAdapter(infolist,BlogsListActivity.this);           //调用infoadapter来完成adapter的设置
            adapter.setOnItemClickListener(new OnRecyclerViewListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(BlogsListActivity.this, BlogDetailActivity.class);
                    intent.putExtra("id",((Blogx)infolist.get(position)).getId());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    };

    private Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(0);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            android.os.Message message = new android.os.Message();
            message.what = 1;
            message.obj = response.body().string().toString().trim();
            handler.sendMessage(message);
        }
    };
}
