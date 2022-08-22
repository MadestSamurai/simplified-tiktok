package com.qxy.siegelions.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.qxy.siegelions.R;
import com.qxy.siegelions.base.BaseRvAdapter;
import com.qxy.siegelions.base.BaseRvViewHolder;
import com.qxy.siegelions.entity.CommentBean;
import com.qxy.siegelions.util.NumUtils;
import com.qxy.siegelions.view.CircleImageView;

import java.util.List;

import butterknife.BindView;

/**
 * create by libo
 * create on 2020-05-24
 * description
 */
public class CommentAdapter extends BaseRvAdapter<CommentBean, CommentAdapter.CommentViewHolder> {

    public CommentAdapter(Context context, List<CommentBean> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(CommentViewHolder holder, CommentBean commentBean, int position) {
        holder.ivHead.setImageResource(commentBean.getUser().getHead());
        holder.tvNickname.setText(commentBean.getUser().getNickName());
        holder.tvContent.setText(commentBean.getContent());
        holder.tvLikecount.setText(NumUtils.numberFilter(commentBean.getLikeCount()));
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    public class CommentViewHolder extends BaseRvViewHolder {
        CircleImageView ivHead;
        TextView tvNickname;
        TextView tvContent;
        TextView tvLikecount;

        public CommentViewHolder(View itemView) {
            super(itemView);

            ivHead = itemView.findViewById(R.id.iv_head);
            tvNickname = itemView.findViewById(R.id.tv_nickname);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvLikecount = itemView.findViewById(R.id.tv_likecount);
        }
    }
}
