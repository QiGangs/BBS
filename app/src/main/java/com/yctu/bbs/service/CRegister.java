package com.yctu.bbs.service;

import com.yctu.bbs.model.User;
import com.yctu.bbs.util.Serverutil;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by qigang on 2017/1/14.
 */

public class CRegister {
    public void register(User user, Callback callback){
        RequestBody requestBody = new FormBody.Builder()
                .add("user.account", user.getAccount())
                .add("user.password", user.getPassword())
                .add("user.username", user.getUsername())
                .add("user.aword", user.getAword())
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"register")
                .post(requestBody)
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }
}
