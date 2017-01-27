package com.yctu.bbs.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yctu.bbs.model.Reply;
import com.yctu.bbs.model.User;
import com.yctu.bbs.util.Serverutil;

import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by qigang on 2017/1/16.
 */

public class CUser {
    public void GetFocusUser(String account, Callback callback){

        RequestBody requestBody = new FormBody.Builder()
                .add("account", account)
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"getfocususer")
                .post(requestBody)
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }


    public ArrayList<User> GetUserList(String obj){
        ArrayList<User> array = new ArrayList<User>();

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray Jarray = parser.parse(obj).getAsJsonArray();

        for(JsonElement object : Jarray ){
            User cse = gson.fromJson( object , User.class);
            array.add(cse);
        }
        //Collections.reverse(array);
        return array;
    }



    public void SetFocus(String userid,String focusedid, Callback callback){

        RequestBody requestBody = new FormBody.Builder()
                .add("userid", userid)
                .add("focusedid", focusedid)
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"setfocus")
                .post(requestBody)
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }



    public void GetUser(String account, Callback callback){

        RequestBody requestBody = new FormBody.Builder()
                .add("account", account)
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"getUserByAccount")
                .post(requestBody)
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }


    public void updateu(String username,String account,Callback callback){
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("type","1")
                .add("account",account)
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"resetUser")
                .post(requestBody)
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }

    public void updatea(String aword,String account,Callback callback){
        RequestBody requestBody = new FormBody.Builder()
                .add("aword", aword)
                .add("type","2")
                .add("account",account)
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"resetUser")
                .post(requestBody)
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }
}
