package com.yctu.bbs.recycleutil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.yctu.bbs.R;
import com.yctu.bbs.model.Reply;
import com.yctu.bbs.modelutil.Blogx;
import com.yctu.bbs.util.OnRecyclerViewListener;
import com.yctu.bbs.util.Serverutil;

import java.util.ArrayList;

/**
 * Created by qigang on 2017/1/19.
 */

public class ReplyAdapter  extends RecyclerView.Adapter{
    private ArrayList<Reply> list;
    private OnRecyclerViewListener onRecyclerViewListener;
    public ReplyAdapter(ArrayList<Reply> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_reply_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(31,32,32,1);
        view.setLayoutParams(lp);
        return new ReplyHolder(view,onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ReplyHolder holder = (ReplyHolder) viewHolder;
        holder.position = position;
        Reply reply = list.get(position);
        holder.reply_account.setText(reply.getAccount());
        holder.reply_content.setText(reply.getContent());
        holder.reply_date.setText(reply.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnRecyclerViewListener onRecyclerViewListener){
        this.onRecyclerViewListener = onRecyclerViewListener;
    }
}
