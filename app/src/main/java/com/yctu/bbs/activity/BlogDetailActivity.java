package com.yctu.bbs.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import jp.wasabeef.richeditor.RichEditor;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.yctu.bbs.R;
import com.yctu.bbs.model.Reply;
import com.yctu.bbs.model.User;
import com.yctu.bbs.modelutil.Blogx;
import com.yctu.bbs.modelutil.Blogz;
import com.yctu.bbs.service.CBlog;
import com.yctu.bbs.util.MyApplication;
import com.yctu.bbs.util.Serverutil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BlogDetailActivity extends AppCompatActivity {
    private String id;
    private TextView title_bd,date_bd,account_bd,username_bd;
    private RichEditor content_bd;
    private ImageView headpic_bd;
    private Button reply_bd;
    private CBlog cBlog  = new CBlog();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogdetail);

        setTitle("信息详情");

        id = getIntent().getStringExtra("id");
        initview();
        cBlog.GetBlogById(id,callback);
    }

    public void initview(){
        title_bd = (TextView)findViewById(R.id.title_bd);
        content_bd = (RichEditor)findViewById(R.id.content_bd);
        date_bd = (TextView)findViewById(R.id.date_bd);
        account_bd = (TextView)findViewById(R.id.account_bd);
        username_bd = (TextView)findViewById(R.id.username_bd);
        headpic_bd = (ImageView) findViewById(R.id.headpic_bd);
        reply_bd = (Button) findViewById(R.id.reply_bd);
    }

    private Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(0);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Message message = new Message();
            message.obj = response.body().string().toString().trim();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                Gson gson = new Gson();
                Blogz blogz =  gson.fromJson(msg.obj.toString(), Blogz.class);
                title_bd.setText(blogz.getTitle());
                content_bd.setHtml(blogz.getContent());
                date_bd.setText(blogz.getDate());
                account_bd.setText(blogz.getAccount());
                username_bd.setText(blogz.getUsername());
                Picasso.with(BlogDetailActivity.this).load(Serverutil.url + "headpic/" + blogz.getHeadpic() + ".jpg").into(headpic_bd);
            }
        }
    };


    public void replybt(View v){
        Intent intent = new Intent(BlogDetailActivity.this, SendReplyActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);

    }

    public void display_reply(View v){
        Intent intent = new Intent(BlogDetailActivity.this, BlogReplyActivity.class);
        intent.putExtra("title",title_bd.getText().toString());
        intent.putExtra("id",id);
        startActivity(intent);
    }


    public void showperinfo(View v){
        Intent intent = new Intent(BlogDetailActivity.this, PerShowActivity.class);
        intent.putExtra("account",account_bd.getText().toString().trim());
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.blogdetailmeun, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.collect){
            collect();
            return true;
        }else if(id == R.id.report){
            isreport();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void isreport(){
        new  AlertDialog.Builder(this)
                .setTitle("举报确认" )
                .setMessage("确定要举报么？" )
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cBlog.report(id,((MyApplication)getApplication()).getAccount(),callbackx);
                    }
                })
                .setNegativeButton("否", null)
                .show();
    }

    public void collect(){
        cBlog.coolect(id,((MyApplication)getApplication()).getAccount(),callbackx);
    }


    private Callback callbackx = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            handlerx.sendEmptyMessage(0);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Message message = new Message();
            message.obj = response.body().string().toString().trim();
            message.what = 1;
            handlerx.sendMessage(message);
        }
    };

    Handler handlerx = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                Toast.makeText(BlogDetailActivity.this,"操作成功",Toast.LENGTH_SHORT).show();
            }
        }
    };

}
