package com.yctu.bbs.service;

import com.yctu.bbs.util.Serverutil;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by qigang on 2016/10/10.
 */

public class CLogin {

    public void login(String account, String password, Callback callback){

        RequestBody requestBody = new FormBody.Builder()
                .add("user.account", account)
                .add("user.password", password)
                .build();
        Request request = new Request.Builder()
                .url(Serverutil.url+"login")
                .post(requestBody)
                //.addHeader("token", "helloworldhelloworldhelloworld")
                .build();
        Serverutil.GetOkHttpClient().newCall(request).enqueue(callback);
    }

}
