package com.yctu.bbs.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yctu.bbs.MainActivity;
import com.yctu.bbs.R;
import com.yctu.bbs.model.User;
import com.yctu.bbs.service.CRegister;
import com.yctu.bbs.util.MyApplication;
import com.yctu.bbs.util.Serverutil;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText account,password,repassword,idcode,username,aword;
    private Button registbt,getidcodebt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initview();

        try {
            SMSSDK.initSDK(this, Serverutil.APPKEY, Serverutil.APPSECRET, true);
            EventHandler eh = new EventHandler() {
                @Override
                public void afterEvent(int event, int result, Object data) {
                    Message msg = new Message();
                    msg.arg1 = event;
                    msg.arg2 = result;
                    msg.obj = data;
                    mHandler.sendMessage(msg);
                }
            };
            SMSSDK.registerEventHandler(eh);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initview(){
        account = (EditText)findViewById(R.id.account);
        password = (EditText)findViewById(R.id.password);
        repassword = (EditText)findViewById(R.id.repassword);
        idcode = (EditText)findViewById(R.id.idcode);
        username = (EditText)findViewById(R.id.username);
        aword = (EditText)findViewById(R.id.aword);
        registbt = (Button)findViewById(R.id.registbt);
        getidcodebt = (Button)findViewById(R.id.getidcode);
    }

    public void registbt(View v){
        SMSSDK.submitVerificationCode("86", account.getText().toString(), idcode.getText().toString());   //对验证码判断
    }
    public void getidocdebt(View v){
        SMSSDK.getVerificationCode("86",account.getText().toString());  //获取验证码
    }

    public void register(){
        CRegister cRegister = new CRegister();
        User user = new User();
        user.setAccount(account.getText().toString());
        user.setPassword(password.getText().toString());
        user.setUsername(username.getText().toString());
        user.setAword(aword.getText().toString());
        cRegister.register(user,callback);
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
                onBackPressed();
            }else if (result.equals("failed")){
                password.setText("");
                repassword.setText("");
                Toast.makeText(getApplicationContext(), "状态异常！\n请重试！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event="+event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                System.out.println("--------result"+event);
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    Toast.makeText(getApplicationContext(), "验证码验证成功", Toast.LENGTH_SHORT).show();
                    register();   //调用方法将数据传到服务器
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //已经验证
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();

                }

            } else {
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;

                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Toast.makeText(RegisterActivity.this, des, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }
        };
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
