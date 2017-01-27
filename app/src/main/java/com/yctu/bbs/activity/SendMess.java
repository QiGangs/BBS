package com.yctu.bbs.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yctu.bbs.R;
import com.yctu.bbs.model.Blog;
import com.yctu.bbs.service.CSendMessage;
import com.yctu.bbs.util.MyApplication;

import java.io.IOException;

import jp.wasabeef.richeditor.RichEditor;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SendMess extends AppCompatActivity {
    private RichEditor mEditor;
    private EditText title;
    private String resString;
    private String resUrl;
    private FloatingActionButton sendbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmess);

        setTitle("信息编写");

        initview();
    }
    public void initview(){
        title = (EditText)findViewById(R.id.send_title) ;
        mEditor = (RichEditor) findViewById(R.id.editor);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.red(000000));
        mEditor.setEditorBackgroundColor(Color.WHITE);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder("正文部分...");
        sendbt = (FloatingActionButton) findViewById(R.id.sendnow);
        sendbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Blog blog = new Blog();
                blog.setTitle(title.getText().toString().trim());
                blog.setContent(mEditor.getHtml());
                CSendMessage sendMessage = new CSendMessage();
                sendMessage.sendmess(blog,((MyApplication) getApplication()).getAccount(),callback);
            }
        });
    }

    private Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            nethandler.sendEmptyMessage(0);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
                nethandler.sendEmptyMessage(1);

        }
    };


    Handler nethandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
                onBackPressed();

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sendmessmeun, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.alertpic){
            SetPicUrl();
            return true;
        }else if(id == R.id.setbold){
            mEditor.setBold();
            return true;
        }else if(id == R.id.setitalic){
            mEditor.setItalic();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void SetPicUrl(){
        final String[] res = {null};
        final View view = LayoutInflater.from(SendMess.this).inflate(R.layout.alert_setpicurl, null);//这里必须是final的
        final EditText edit=(EditText)view.findViewById(R.id.editText);//获得输入框对象

        new AlertDialog.Builder(SendMess.this)
                .setTitle("插入图片的网络地址")//提示框标题
                .setView(view)
                .setPositiveButton("确定",//提示框的两个按钮
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Message message = new Message();
                                message.obj = edit.getText().toString().trim();
                                handler.sendMessage(message);
                            }
                        }).setNegativeButton("取消", null).create().show();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mEditor.insertImage(msg.obj.toString(),"image");
        }
    };
}
