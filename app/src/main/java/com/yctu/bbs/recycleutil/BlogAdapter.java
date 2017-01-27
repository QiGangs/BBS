package com.yctu.bbs.recycleutil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.yctu.bbs.R;
import com.yctu.bbs.activity.PerInfoActivity;
import com.yctu.bbs.modelutil.Blogx;
import com.yctu.bbs.util.OnRecyclerViewListener;
import com.yctu.bbs.util.Serverutil;

import java.util.ArrayList;

/**
 * Created by qigang on 2017/1/16.
 */

public class BlogAdapter extends RecyclerView.Adapter{
    private Context context;
    private ArrayList<Blogx> list;
    private OnRecyclerViewListener onRecyclerViewListener;
    public BlogAdapter(ArrayList<Blogx> list,Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_blog_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(31,32,32,1);
        view.setLayoutParams(lp);
        return new BlogHolder(view,onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        BlogHolder holder = (BlogHolder) viewHolder;
        holder.position = position;
        Blogx blogx = list.get(position);
        holder.blog_username.setText(blogx.getUsername());
        holder.blog_title.setText(blogx.getTitle());
        holder.blog_date.setText(blogx.getDate());
        Picasso.with(context).load(Serverutil.url + "headpic/" + blogx.getHeadpic() + ".jpg").into(holder.blog_headpic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnRecyclerViewListener onRecyclerViewListener){
        this.onRecyclerViewListener = onRecyclerViewListener;
    }
}
