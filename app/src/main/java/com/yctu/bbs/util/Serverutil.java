package com.yctu.bbs.util;

import okhttp3.OkHttpClient;

/**
 * Created by qigang on 16/9/19.
 */

public class Serverutil {
    public static String APPKEY = "f3d1189823d0";
    public static String APPSECRET = "9d60291f4f7cfb9e1b3d4b007021bd5d";

    static public String url = "http://192.168.0.100:8080/BBS/";

    private static OkHttpClient okHttpClient = null;

    public static OkHttpClient GetOkHttpClient(){
        if(okHttpClient == null){
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }
}
