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
import com.qxy.siegelions.entity.Comment;
import com.qxy.siegelions.util.NumUtils;
import com.qxy.siegelions.view.CircleImageView;

import java.util.List;

/**
 * 评论数据适配类
 * @author MadSamurai
 */
public class CommentAdapter extends BaseRvAdapter<Comment, CommentAdapter.CommentViewHolder> {

    public CommentAdapter(Context context, List<Comment> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(CommentViewHolder holder, Comment comment, int position) {
        holder.ivHead.setImageResource(comment.getUser().getHead());
        holder.tvNickname.setText(comment.getUser().getNickName());
        holder.tvContent.setText(comment.getContent());
        holder.tvLikecount.setText(NumUtils.numberFilter(comment.getLikeCount()));
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
