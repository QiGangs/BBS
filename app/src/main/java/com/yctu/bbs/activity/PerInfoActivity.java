package com.yctu.bbs.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PerInfoActivity extends AppCompatActivity {
    private TextView account,username,aword,resetu,reseta;
    private ImageView headpic;
    private CUser cUser = new CUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perinfo);

        initview();
        MyApplication myApplication = (MyApplication) getApplication();
        cUser.GetUser(myApplication.getAccount(),callback);
    }

    public void initview(){
        account = (TextView)findViewById(R.id.account);
        username = (TextView)findViewById(R.id.username);
        aword = (TextView)findViewById(R.id.aword);
        resetu = (TextView)findViewById(R.id.reset_u);
        reseta = (TextView)findViewById(R.id.reset_a);
        headpic = (ImageView)findViewById(R.id.headpic);
        resetu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetu.setEnabled(false);
                MyApplication myApplication = (MyApplication) getApplication();
                //Toast.makeText(PerInfoActivity.this,"test"+username.getText().toString()+"test"+myApplication.getAccount(),Toast.LENGTH_SHORT).show();
                cUser.updateu(username.getText().toString().trim(),myApplication.getAccount(),callbackx);
            }
        });
        reseta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reseta.setEnabled(false);
                MyApplication myApplication = (MyApplication) getApplication();
                cUser.updatea(aword.getText().toString().trim(),myApplication.getAccount(),callbackx);
            }
        });
    }

    private Callback callbackx = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(0);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Message message = new Message();
            message.obj = response.body().string().toString().trim();
            message.what = 2;
            handler.sendMessage(message);
        }
    };

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
                account.setText(user.getAccount());
                username.setText(user.getUsername());
                aword.setText(user.getAword());
                Picasso.with(PerInfoActivity.this).load(Serverutil.url + "headpic/" + user.getHeadpic() + ".jpg").into(headpic);
            }else if(msg.what == 2){
                Toast.makeText(PerInfoActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
            }
        }
    };
}
