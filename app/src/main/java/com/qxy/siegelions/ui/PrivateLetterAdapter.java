package com.qxy.siegelions.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.qxy.siegelions.R;
import com.qxy.siegelions.base.BaseRvAdapter;
import com.qxy.siegelions.base.BaseRvViewHolder;
import com.qxy.siegelions.entity.Video;

import java.util.List;

import butterknife.BindView;

/**
 * create by libo
 * create on 2020-05-25
 * description
 */
public class PrivateLetterAdapter extends BaseRvAdapter<Video.User, PrivateLetterAdapter.PrivateLetterViewHolder> {

    public PrivateLetterAdapter(Context context, List<Video.User> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(PrivateLetterViewHolder holder, Video.User userBean, int position) {
        holder.ivHead.setImageResource(userBean.getHead());
        holder.tvName.setText(userBean.getNickName());
    }

    @NonNull
    @Override
    public PrivateLetterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_private_letter,parent, false);
        return new PrivateLetterViewHolder(view);
    }

    public class PrivateLetterViewHolder extends BaseRvViewHolder {
        ImageView ivHead;
        TextView tvName;

        public PrivateLetterViewHolder(View itemView) {
            super(itemView);
            ivHead = itemView.findViewById(R.id.iv_head);
            tvName = itemView.findViewById(R.id.tv_nickname);
        }
    }
}
