package com.yctu.bbs.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yctu.bbs.R;
import com.yctu.bbs.model.Reply;
import com.yctu.bbs.modelutil.Blogx;
import com.yctu.bbs.recycleutil.BlogAdapter;
import com.yctu.bbs.recycleutil.ReplyAdapter;
import com.yctu.bbs.service.CBlog;
import com.yctu.bbs.service.CSendMessage;
import com.yctu.bbs.util.OnRecyclerViewListener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BlogReplyActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList infolist = null;
    private CBlog cBlog;
    private String title;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_reply);
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");

        setTitle(title);

        recyclerView = (RecyclerView)findViewById(R.id.blog_reply_rlv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BlogReplyActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        cBlog = new CBlog();
        cBlog.GetreplyById(id,callback);

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                Toast.makeText(BlogReplyActivity.this,"网络异常",Toast.LENGTH_LONG).show();
                return;
            }
            if(msg.obj.toString().equals("[]")){
                Toast.makeText(BlogReplyActivity.this,"没有数据",Toast.LENGTH_LONG).show();
                return;
            }
            infolist = cBlog.GetReplyList(msg.obj.toString());
            ReplyAdapter adapter = new ReplyAdapter(infolist);           //调用infoadapter来完成adapter的设置
            adapter.setOnItemClickListener(new OnRecyclerViewListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(BlogReplyActivity.this, PerShowActivity.class);
                    intent.putExtra("account",((Reply)infolist.get(position)).getAccount());
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
