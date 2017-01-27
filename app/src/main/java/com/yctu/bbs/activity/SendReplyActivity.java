package com.yctu.bbs.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.yctu.bbs.R;
import com.yctu.bbs.model.Blog;
import com.yctu.bbs.model.Reply;
import com.yctu.bbs.service.CSendMessage;
import com.yctu.bbs.util.MyApplication;

import java.io.IOException;

import jp.wasabeef.richeditor.RichEditor;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SendReplyActivity extends AppCompatActivity {
    private RichEditor replycontent;
    private FloatingActionButton sendbt;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendreply);

        setTitle("回复");

        id = getIntent().getStringExtra("id");
        initview();
    }

    public void initview(){
        replycontent = (RichEditor) findViewById(R.id.replycontent);
        replycontent.setEditorHeight(200);
        replycontent.setEditorFontSize(22);
        replycontent.setEditorFontColor(Color.red(000000));
        replycontent.setEditorBackgroundColor(Color.WHITE);
        replycontent.setPadding(10, 10, 10, 10);
        replycontent.setPlaceholder("Insert text here...");
        sendbt = (FloatingActionButton) findViewById(R.id.sendreply);
        sendbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reply reply = new Reply();
                reply.setAccount(((MyApplication) getApplication()).getAccount());
                reply.setBlogid(id);
                reply.setContent(replycontent.getHtml());
                CSendMessage sendMessage = new CSendMessage();
                sendMessage.sendreply(reply,callback);
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
}
