package com.yctu.bbs.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.yctu.bbs.R;
import com.yctu.bbs.activity.BlogDetailActivity;
import com.yctu.bbs.activity.BlogsListActivity;
import com.yctu.bbs.model.User;
import com.yctu.bbs.modelutil.Blogx;
import com.yctu.bbs.recycleutil.BlogAdapter;
import com.yctu.bbs.recycleutil.FuserAdapter;
import com.yctu.bbs.service.CUser;
import com.yctu.bbs.util.MyApplication;
import com.yctu.bbs.util.OnRecyclerViewListener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by qigang on 2017/1/14.
 */

public class Message extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private ArrayList infolist = null;
    private CUser cUser = new CUser();
    private SwipyRefreshLayout swipyRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.message_fragment, container, false);

        swipyRefreshLayout = (SwipyRefreshLayout)view.findViewById(R.id.blog_srl);
        swipyRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if(direction == SwipyRefreshLayoutDirection.BOTTOM){
                    Toast.makeText(getActivity(),"没有更多了",Toast.LENGTH_SHORT).show();
                    swipyRefreshLayout.setRefreshing(false);
                }else if(direction == SwipyRefreshLayoutDirection.TOP){
                    Toast.makeText(getActivity(),"已刷新",Toast.LENGTH_SHORT).show();
                    swipyRefreshLayout.setRefreshing(false);
                }
            }
        });
        swipyRefreshLayout.setRefreshing(true);

        recyclerView = (RecyclerView)view.findViewById(R.id.message_rlv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        cUser.GetFocusUser(((MyApplication)getActivity().getApplication()).getAccount(),callback);

        return view;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);

            if(msg.what == 0){
                Toast.makeText(getActivity(),"网络异常",Toast.LENGTH_LONG).show();
                return;
            }
            if(msg.obj.toString().equals("[]")){
                Toast.makeText(getActivity(),"没有数据",Toast.LENGTH_LONG).show();
                return;
            }
            infolist = cUser.GetUserList(msg.obj.toString());
            FuserAdapter adapter = new FuserAdapter(infolist,getActivity().getBaseContext());           //调用infoadapter来完成adapter的设置
            adapter.setOnItemClickListener(new OnRecyclerViewListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(getActivity(), BlogsListActivity.class);
                    intent.putExtra("account",((User)infolist.get(position)).getAccount());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
            swipyRefreshLayout.setRefreshing(false);
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
            android.os.Message message = new android.os.Message();
            message.what = 1;
            message.obj = response.body().string().toString().trim();
            handler.sendMessage(message);
        }
    };
}
