package com.yctu.bbs.service;

import com.yctu.bbs.model.Blog;
import com.yctu.bbs.model.Reply;
import com.yctu.bbs.model.User;
import com.yctu.bbs.util.MyApplication;
import com.yctu.bbs.util.Serverutil;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by qigang on 2017/1/15.
 */

public class CSendMessage {
    public void sendmess(Blog blog,String account ,Callback callback){
        RequestBody requestBody = new FormBody.Builder()
                .add("user.account",account)
                .add("blog.title",blog.getTitle())
                .add("blog.content",blog.getContent())
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"saveblog")
                .post(requestBody)
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }

    public void sendreply(Reply reply,Callback callback){
        RequestBody requestBody = new FormBody.Builder()
                .add("account",reply.getAccount())
                .add("content",reply.getContent())
                .add("blogid",reply.getBlogid())
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"saveReply")
                .post(requestBody)
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }
}
