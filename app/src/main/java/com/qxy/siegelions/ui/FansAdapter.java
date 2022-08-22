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
import com.qxy.siegelions.entity.Video;
import com.qxy.siegelions.view.CircleImageView;

import java.util.List;

import butterknife.BindView;

/**
 * 粉丝适配类
 * @author MadSamurai
 */
public class FansAdapter extends BaseRvAdapter<Video.User, FansAdapter.FansViewHolder> {

    public FansAdapter(Context context, List<Video.User> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(FansViewHolder holder, Video.User userBean, int position) {
        holder.ivHead.setImageResource(userBean.getHead());
        holder.tvNickname.setText(userBean.getNickName());
        holder.tvFocus.setText(userBean.isFocused() ? "已关注" : "关注");

        holder.tvFocus.setOnClickListener(v -> {
            if (!userBean.isFocused()) {
                holder.tvFocus.setText("已关注");
                holder.tvFocus.setBackgroundResource(R.drawable.shape_round_halfwhite);
            } else {
                holder.tvFocus.setText("关注");
                holder.tvFocus.setBackgroundResource(R.drawable.shape_round_red);
            }

            userBean.setFocused(!userBean.isFocused());
        });
    }

    @NonNull
    @Override
    public FansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_fans, parent, false);
        return new FansViewHolder(view);
    }

    public class FansViewHolder extends BaseRvViewHolder {
        CircleImageView ivHead;
        TextView tvNickname;
        TextView tvFocus;

        public FansViewHolder(View itemView) {
            super(itemView);
            ivHead = itemView.findViewById(R.id.iv_head);
            tvNickname = itemView.findViewById(R.id.tv_nickname);
            tvFocus = itemView.findViewById(R.id.tv_focus);
        }
    }
}
