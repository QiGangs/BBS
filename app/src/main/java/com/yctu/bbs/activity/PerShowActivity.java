package com.yctu.bbs.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.yctu.bbs.R;
import com.yctu.bbs.model.User;
import com.yctu.bbs.service.CUser;
import com.yctu.bbs.util.MyApplication;
import com.yctu.bbs.util.Serverutil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PerShowActivity extends AppCompatActivity {
    private TextView account,username,aword;
    private String focusedid;
    private ImageView headpic;
    private String accountString;
    private Button bt_pershow;
    private CUser cUser = new CUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_show);

        setTitle("个人信息展示");

        accountString = getIntent().getStringExtra("account");
        initview();

        cUser.GetUser(accountString,callback);
    }


    public void initview(){
        account = (TextView)findViewById(R.id.account);
        username = (TextView)findViewById(R.id.username);
        aword = (TextView)findViewById(R.id.aword);
        headpic = (ImageView)findViewById(R.id.headpic);
        bt_pershow = (Button)findViewById(R.id.bt_pershow);
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
                User user =  gson.fromJson(msg.obj.toString(), User.class);
                focusedid = user.getId()+"";
                account.setText("账号："+user.getAccount());
                username.setText("昵称："+user.getUsername());
                aword.setText(user.getAword());
                Picasso.with(PerShowActivity.this).load(Serverutil.url + "headpic/" + user.getHeadpic() + ".jpg").into(headpic);
            }
        }
    };


    public void foucs(View v){
        cUser.SetFocus(((MyApplication)getApplication()).getAccount(),focusedid,callbackx);
    }


    private Callback callbackx = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            Toast.makeText(PerShowActivity.this,"error",Toast.LENGTH_LONG).show();
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
                if(msg.obj.toString().equals("exited")){
                    Toast.makeText(PerShowActivity.this,"以关注",Toast.LENGTH_LONG).show();

                }else if(msg.obj.toString().equals("success")){
                    bt_pershow.setEnabled(false);
                    Toast.makeText(PerShowActivity.this,"关注成功",Toast.LENGTH_LONG).show();
                }
            }
        }
    };
}
