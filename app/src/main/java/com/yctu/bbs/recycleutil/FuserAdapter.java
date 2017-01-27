package com.yctu.bbs.recycleutil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.yctu.bbs.R;
import com.yctu.bbs.model.User;
import com.yctu.bbs.modelutil.Blogx;
import com.yctu.bbs.util.OnRecyclerViewListener;
import com.yctu.bbs.util.Serverutil;

import java.util.ArrayList;

/**
 * Created by qigang on 2017/1/19.
 */

public class FuserAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<User> list;
    private OnRecyclerViewListener onRecyclerViewListener;
    public FuserAdapter(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_focususer_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(31,32,32,1);
        view.setLayoutParams(lp);
        return new FuserHolder(view,onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        FuserHolder holder = (FuserHolder) viewHolder;
        holder.position = position;
        User user = list.get(position);
        holder.fuser_account.setText(user.getAccount());
        holder.fuser_aword.setText(user.getAword());
        holder.fuser_username.setText(user.getUsername());
        Picasso.with(context).load(Serverutil.url + "headpic/" + user.getHeadpic() + ".jpg").into(holder.fuser_headpic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnRecyclerViewListener onRecyclerViewListener){
        this.onRecyclerViewListener = onRecyclerViewListener;
    }
}
