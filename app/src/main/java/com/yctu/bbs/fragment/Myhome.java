package com.yctu.bbs.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.yctu.bbs.R;
import com.yctu.bbs.activity.BlogsListActivity;
import com.yctu.bbs.activity.PerInfoActivity;
import com.yctu.bbs.model.User;
import com.yctu.bbs.service.CUser;
import com.yctu.bbs.util.MyApplication;
import com.yctu.bbs.util.Serverutil;
import com.yctu.bbs.utilactivity.BBXXActivity;
import com.yctu.bbs.utilactivity.KYXKActivity;
import com.yctu.bbs.utilactivity.YHXYActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.System.in;
import static java.lang.System.load;

/**
 * Created by qigang on 2017/1/14.
 */

public class Myhome extends Fragment {
    private LinearLayout linearLayout;
    private ImageView imageView;
    private TextView textView,myblog,mycollect;
    private TextView kyxk,bbxx,myhome_logout,yhxy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myhone_fragment, container, false);

        linearLayout = (LinearLayout)view.findViewById(R.id.myhome_head);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PerInfoActivity.class);
                startActivity(intent);
            }
        });


        imageView = (ImageView)view.findViewById(R.id.myhome_headpic);
        textView = (TextView)view.findViewById(R.id.myhome_headname);
        myblog = (TextView)view.findViewById(R.id.myblog);
        mycollect = (TextView)view.findViewById(R.id.mycollect);

        kyxk = (TextView)view.findViewById(R.id.kyxk);
        kyxk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), KYXKActivity.class);
                startActivity(intent);
            }
        });
        bbxx = (TextView)view.findViewById(R.id.bbxx);
        bbxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BBXXActivity.class);
                startActivity(intent);
            }
        });
        yhxy = (TextView)view.findViewById(R.id.yhxy);
        yhxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), YHXYActivity.class);
                startActivity(intent);
            }
        });
        myhome_logout = (TextView)view.findViewById(R.id.myhome_logout);
        myhome_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("qibbs",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("account","1");
                editor.commit();
                getActivity().onBackPressed();
            }
        });


        MyApplication myApplication = (MyApplication)getActivity().getApplication();
        CUser cUser = new CUser();
        cUser.GetUser(myApplication.getAccount(),callback);

        myblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BlogsListActivity.class);
                intent.putExtra("account",((MyApplication)getActivity().getApplication()).getAccount());
                startActivity(intent);
            }
        });

        mycollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BlogsListActivity.class);
                intent.putExtra("type","collect");
                intent.putExtra("account",((MyApplication)getActivity().getApplication()).getAccount());
                startActivity(intent);
            }
        });


        return view;
    }

    private Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(0);
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            android.os.Message message = new android.os.Message();
            message.obj = response.body().string().toString().trim();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            if(msg.what == 1){
                Gson gson = new Gson();
                User user =  gson.fromJson(msg.obj.toString(), User.class);
                textView.setText(user.getUsername());
                Picasso.with(getActivity()).load(Serverutil.url + "headpic/" + user.getHeadpic() + ".jpg").into(imageView);
            }
        }
    };
}
