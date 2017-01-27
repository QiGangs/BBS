package com.yctu.bbs.recycleutil;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yctu.bbs.R;
import com.yctu.bbs.util.OnRecyclerViewListener;

/**
 * Created by qigang on 2017/1/19.
 */

public class ReplyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private OnRecyclerViewListener onRecyclerViewListener;
    public int position;
    public View rootv;
    public TextView reply_content,reply_date,reply_account;


    public ReplyHolder(View itemView,OnRecyclerViewListener onRecyclerViewListener){
        super(itemView);
        this.onRecyclerViewListener = onRecyclerViewListener;
        rootv = itemView.findViewById(R.id.reply_root);
        reply_content = (TextView)itemView.findViewById(R.id.reply_content);
        reply_date = (TextView)itemView.findViewById(R.id.reply_date);
        reply_account = (TextView)itemView.findViewById(R.id.reply_account);
        rootv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (null != onRecyclerViewListener) {
            onRecyclerViewListener.onItemClick(v,position);
        }
    }
}
