package com.qxy.siegelions.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qxy.siegelions.R;
import com.qxy.siegelions.entity.RankingEntry;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EntryAdapter extends BaseAdapter {
    private RankingEntry[] mRankingEntry;
    private Context mContext;

    Poster poster = new Poster();

    private class Poster{
        String id;
        Bitmap bitmap;
    }

    private class PosterByte{
        String id;
        byte[] bitmap;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                //通过message，拿到字节数组
                PosterByte posterByte = (PosterByte) msg.obj;
                byte[] Picture = posterByte.bitmap;
                //使用BitmapFactory工厂，把字节数组转化为bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                poster.bitmap = bitmap;
                poster.id = posterByte.id;
            }
        }
    };

    public EntryAdapter(RankingEntry[] mRankingEntry, Context mContext) {
        this.mRankingEntry = mRankingEntry;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mRankingEntry.length;
    }

    @Override
    public Object getItem(int position) {
        return mRankingEntry[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.info_item_title = (TextView) convertView.findViewById(R.id.info_item_title);
            viewHolder.info_director = (TextView) convertView.findViewById(R.id.info_director);
            viewHolder.poster = (ImageView) convertView.findViewById(R.id.poster);
            viewHolder.info_date = (TextView) convertView.findViewById(R.id.info_date);
            viewHolder.info_hot = (TextView) convertView.findViewById(R.id.info_hot);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        StringBuilder directories = new StringBuilder("");
        int count = 0;
        if (mRankingEntry[position].getDirectors() == null)
            directories = new StringBuilder("未知");
        else
            for (String director : Objects.requireNonNull(mRankingEntry[position].getDirectors())) {
                count++;
                if (count == 4) {
                    directories.append(director).append("等");
                    break;
                }
                directories.append(director).append(" ");
            }
        viewHolder.info_item_title.setText(mRankingEntry[position].getNameCN());
        viewHolder.info_director.setText("导演：" + directories);

        String date = String.format("%tF", mRankingEntry[position].getReleaseDate());
        viewHolder.info_date.setText((date.equals("null")) ? "上映日期未知" : date + " 上映");
        viewHolder.info_hot.setText("热度：" + String.format("%.1f", mRankingEntry[position].getHot() / 10000.00) + "万");
        Bitmap[] bitmap = new Bitmap[30];

        try {
            count = 0;
            for(RankingEntry rankingEntry : mRankingEntry) {
                getPoster(mRankingEntry[count].getPoster(), mRankingEntry[count].getId());
                if (poster != null && rankingEntry.getId().equals(poster.id)) {
                    bitmap[count] = poster.bitmap;
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        viewHolder.poster.setImageBitmap(bitmap[position]);
        // TODO 图片获取混乱，待处理，考虑使用本地缓存解决问题。

        return convertView;
    }

    private class ViewHolder {
        TextView info_item_title;
        TextView info_director;
        ImageView poster;
        TextView info_date;
        TextView info_hot;
    }

    public void getPoster(String path, String id) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(path)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {

            }

            public void onResponse(Call call, Response response) throws IOException {
                byte[] Picture_bt = response.body().bytes();
                //通过handler更新UI
                PosterByte posterByte = new PosterByte();
                posterByte.bitmap = Picture_bt;
                posterByte.id = id;
                Message message = handler.obtainMessage();
                message.obj = posterByte;
                message.what = 1;
                handler.sendMessage(message);
            }
        });
    }
}
