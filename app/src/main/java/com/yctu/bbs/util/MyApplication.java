package com.yctu.bbs.util;

import android.app.Application;

/**
 * Created by qigang on 16/9/19.
 */

public class MyApplication extends Application {
    private String account;

    @Override
    public void onCreate() {
        super.onCreate();
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


}
