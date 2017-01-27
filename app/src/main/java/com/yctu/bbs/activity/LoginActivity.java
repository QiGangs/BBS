package com.yctu.bbs.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import com.yctu.bbs.MainActivity;
import com.yctu.bbs.R;
import com.yctu.bbs.service.CLogin;
import com.yctu.bbs.util.MyApplication;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    private EditText account,password;
    private Button login,register ;
    private String acc,pas;
    private CLogin cLogin = new CLogin();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(autologin()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_login);

        initview();
    }

    public void initview(){
        account = (EditText)findViewById(R.id.account);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.loginbt);
        register = (Button)findViewById(R.id.registbt);
    }

    public void onClickLogin(View v){
        login.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        login.setText("正 在 登 陆");
        login.setEnabled(false);
        acc = account.getText().toString();
        pas = password.getText().toString();
        cLogin.login(acc,pas,callback);
    }

    public void onClickRegist(View v){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
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
            handler.sendMessage(message);
        }
    };

    private Handler handler = new Handler(){
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj.toString();
            if(result.equals("success")) {
                //记住登陆信息
                //下次跳过登陆
                SharedPreferences preferences = getSharedPreferences("qibbs",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("account",acc);
                editor.commit();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                MyApplication myApplication = (MyApplication) getApplication();
                myApplication.setAccount(acc);
                startActivity(intent);
                finish();
            }else if (result.equals("failed")){
                login.setBackgroundColor(getResources().getColor(R.color.loginbt1));
                login.setText("登       录");
                login.setEnabled(true);
                password.setText("");
                Toast.makeText(LoginActivity.this,"账号或密码错误！\n请重新输入！",Toast.LENGTH_SHORT).show();
            }
        }
    };


    public boolean autologin(){
        SharedPreferences preferences = getSharedPreferences("qibbs", MODE_PRIVATE);
        acc = preferences.getString("account","1");
        if(!acc.equals("1")){
            MyApplication myApplication = (MyApplication) getApplication();
            myApplication.setAccount(acc);
            return true;
        }
        return false;
    }
}

