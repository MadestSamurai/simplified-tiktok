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
import com.qxy.siegelions.entity.ShareBean;

import java.util.List;

import butterknife.BindView;

/**
 * create by libo
 * create on 2020-05-25
 * description
 */
public class ShareAdapter extends BaseRvAdapter<ShareBean, ShareAdapter.ShareViewHolder> {

    public ShareAdapter(Context context, List<ShareBean> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(ShareViewHolder holder, ShareBean shareBean, int position) {
        holder.tvIcon.setText(shareBean.getIconRes());
        holder.tvText.setText(shareBean.getText());
        holder.viewBg.setBackgroundResource(shareBean.getBgRes());
    }

    @NonNull
    @Override
    public ShareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_share, parent, false);
        return new ShareViewHolder(view);
    }

    public class ShareViewHolder extends BaseRvViewHolder {
        TextView tvIcon;
        TextView tvText;
        View viewBg;

        public ShareViewHolder(View itemView) {
            super(itemView);

            tvIcon = itemView.findViewById(R.id.tv_icon);
            tvText = itemView.findViewById(R.id.tv_text);
            viewBg = itemView.findViewById(R.id.view_bg);
        }
    }
}
