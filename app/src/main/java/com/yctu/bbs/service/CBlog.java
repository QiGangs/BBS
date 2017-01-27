package com.yctu.bbs.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yctu.bbs.model.Reply;
import com.yctu.bbs.modelutil.Blogx;
import com.yctu.bbs.util.Serverutil;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by qigang on 2017/1/16.
 */

public class CBlog {
    public void GetAllBlogByAccount(String account, Callback callback){

        RequestBody requestBody = new FormBody.Builder()
                .add("account",account)
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"getblogsbyaccount")
                .post(requestBody)
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }


    public void GetCollectBlogByAccount(String account, Callback callback){

        RequestBody requestBody = new FormBody.Builder()
                .add("account",account)
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"getcollect")
                .post(requestBody)
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }


    public void report(String blogid,String account,Callback callback){
        RequestBody requestBody = new FormBody.Builder()
                .add("blogid",blogid)
                .add("account",account)
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"report")
                .post(requestBody)
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }

    public void coolect(String blogid,String account,Callback callback){
        RequestBody requestBody = new FormBody.Builder()
                .add("blogid",blogid)
                .add("account",account)
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"collect")
                .post(requestBody)
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }



    public void GetAllBlog( Callback callback){

        RequestBody requestBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"getAllBlog")
                .post(requestBody)
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }


    public void GetBlogById( String id,Callback callback){

        RequestBody requestBody = new FormBody.Builder()
                .add("id",id)
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"getblogbyid")
                .post(requestBody)
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }

    public void GetreplyById(String id,Callback callback){
        RequestBody requestBody = new FormBody.Builder()
                .add("id",id)
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"getReply")
                .post(requestBody)
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }


    public ArrayList<Reply> GetReplyList(String obj){      //用来解析请假信息
        ArrayList<Reply> array = new ArrayList<Reply>();

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray Jarray = parser.parse(obj).getAsJsonArray();

        for(JsonElement object : Jarray ){
            Reply cse = gson.fromJson( object , Reply.class);
            array.add(cse);
        }
        //Collections.reverse(array);
        return array;
    }


    public ArrayList<Blogx> GetBlogxList(String obj){      //用来解析请假信息
        ArrayList<Blogx> array = new ArrayList<Blogx>();

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray Jarray = parser.parse(obj).getAsJsonArray();

        for(JsonElement object : Jarray ){
            Blogx cse = gson.fromJson( object , Blogx.class);
            array.add(cse);
        }
        //Collections.reverse(array);
        return array;
    }
}
