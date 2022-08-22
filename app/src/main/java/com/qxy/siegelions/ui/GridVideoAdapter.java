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
import com.qxy.siegelions.view.IconFontTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create by libo
 * create on 2020-05-20
 * description
 */
public class GridVideoAdapter extends BaseRvAdapter<Video, GridVideoAdapter.GridVideoViewHolder> {

    public GridVideoAdapter(Context context, List<Video> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(GridVideoViewHolder holder, Video video, int position) {
        holder.ivCover.setBackgroundResource(video.getCoverRes());
        holder.tvContent.setText(video.getContent());
        holder.tvDistance.setText(video.getDistance() + " km");
        holder.ivHead.setImageResource(video.getUser().getHead());

        holder.itemView.setOnClickListener(v -> {
            PlayListActivity.initPos = position;
            getContext().startActivity(new Intent(getContext(), PlayListActivity.class));
        });
    }

    @NonNull
    @Override
    public GridVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_gridvideo, parent, false);
        return new GridVideoViewHolder(view);
    }

    public class GridVideoViewHolder extends BaseRvViewHolder {
        ImageView ivCover;
        TextView tvContent;
        IconFontTextView tvDistance;
        ImageView ivHead;

        public GridVideoViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvDistance = itemView.findViewById(R.id.tv_distance);
            ivHead = itemView.findViewById(R.id.iv_head);
        }
    }
}
