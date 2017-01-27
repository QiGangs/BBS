package com.yctu.bbs.recycleutil;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yctu.bbs.R;
import com.yctu.bbs.util.OnRecyclerViewListener;

/**
 * Created by qigang on 2017/1/16.
 */

public class BlogHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private OnRecyclerViewListener onRecyclerViewListener;
    public int position;
    public View rootv;
    public ImageView blog_headpic;
    public TextView blog_username,blog_title,blog_date;


    public BlogHolder(View itemView,OnRecyclerViewListener onRecyclerViewListener){
        super(itemView);
        this.onRecyclerViewListener = onRecyclerViewListener;
        rootv = itemView.findViewById(R.id.blog_root);
        blog_headpic = (ImageView) itemView.findViewById(R.id.blog_headpic);
        blog_username = (TextView)itemView.findViewById(R.id.blog_username);
        blog_title = (TextView)itemView.findViewById(R.id.blog_title);
        blog_date = (TextView)itemView.findViewById(R.id.blog_date);
        rootv.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (null != onRecyclerViewListener) {
            onRecyclerViewListener.onItemClick(v,position);
        }
    }
}
