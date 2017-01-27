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

public class FuserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private OnRecyclerViewListener onRecyclerViewListener;
    public int position;
    public View rootv;
    public ImageView fuser_headpic;
    public TextView fuser_account,fuser_username,fuser_aword;


    public FuserHolder(View itemView,OnRecyclerViewListener onRecyclerViewListener){
        super(itemView);
        this.onRecyclerViewListener = onRecyclerViewListener;
        rootv = itemView.findViewById(R.id.root_fuser);
        fuser_account = (TextView)itemView.findViewById(R.id.fuser_account);
        fuser_username = (TextView)itemView.findViewById(R.id.fuser_username);
        fuser_aword = (TextView)itemView.findViewById(R.id.fuser_aword);
        fuser_headpic = (ImageView) itemView.findViewById(R.id.fuser_headpic);
        rootv.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (null != onRecyclerViewListener) {
            onRecyclerViewListener.onItemClick(v,position);
        }
    }
}
