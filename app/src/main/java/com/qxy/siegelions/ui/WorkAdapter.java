package com.qxy.siegelions.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.qxy.siegelions.R;
import com.qxy.siegelions.PlayListActivity;
import com.qxy.siegelions.base.BaseRvAdapter;
import com.qxy.siegelions.base.BaseRvViewHolder;
import com.qxy.siegelions.entity.Video;
import com.qxy.siegelions.util.NumUtils;

import java.util.List;


/**
 * 工作适配类
 * @author MadSamurai
 */
public class WorkAdapter extends BaseRvAdapter<Video, WorkAdapter.WorkViewHolder> {

    public WorkAdapter(Context context, List<Video> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(WorkViewHolder holder, Video videoBean, int position) {
        holder.ivCover.setImageResource(videoBean.getCoverRes());
        holder.tvLikeCount.setText(NumUtils.numberFilter(videoBean.getLikeCount()));

        holder.itemView.setOnClickListener(v -> {
            PlayListActivity.initPos = position;
            getContext().startActivity(new Intent(getContext(), PlayListActivity.class));
        });
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rooView = LayoutInflater.from(getContext()).inflate(R.layout.item_work, parent, false);
        return new WorkViewHolder(rooView);
    }

    public class WorkViewHolder extends BaseRvViewHolder {
        ImageView ivCover;
        TextView tvLikeCount;

        public WorkViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvLikeCount = itemView.findViewById(R.id.tv_likecount);
        }
    }

}
