package com.qxy.siegelions.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.qxy.siegelions.R;
import com.qxy.siegelions.base.BaseRvAdapter;
import com.qxy.siegelions.base.BaseRvViewHolder;
import com.qxy.siegelions.entity.Video;
import com.qxy.siegelions.view.ControllerView;
import com.qxy.siegelions.view.LikeView;

import java.util.List;


/**
 * 视频适配类
 * @author MadSamurai
 */
public class VideoAdapter extends BaseRvAdapter<Video, VideoAdapter.VideoViewHolder> {

    public VideoAdapter(Context context, List<Video> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(VideoViewHolder holder, Video video, int position) {
        holder.controllerView.setVideoData(video);

        holder.ivCover.setImageResource(video.getCoverRes());

        holder.likeView.setOnLikeListener(() -> {
            if (!video.isLiked()) {  //未点赞，会有点赞效果，否则无
                holder.controllerView.like();
            }

        });
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    public class VideoViewHolder extends BaseRvViewHolder {
        LikeView likeView;
        ControllerView controllerView;
        ImageView ivCover;

        public VideoViewHolder(View itemView) {
            super(itemView);
            likeView = itemView.findViewById(R.id.likeview);
            controllerView = itemView.findViewById(R.id.controller);
            ivCover = itemView.findViewById(R.id.iv_cover);
        }
    }
}
