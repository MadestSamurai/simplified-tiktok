package com.qxy.siegelions.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qxy.siegelions.R;
import com.qxy.siegelions.entity.RankingEntry;
import com.qxy.siegelions.util.ImageCacheUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

/**
 * 影视综条目适配类
 * @author akkcdb110
 */
public class EntryAdapter extends BaseAdapter {
    private RankingEntry[] mRankingEntry;
    private Context mContext;

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
            viewHolder.info_en_title = (TextView) convertView.findViewById(R.id.info_en_title);
            viewHolder.info_director = (TextView) convertView.findViewById(R.id.info_director);
            viewHolder.poster = (ImageView) convertView.findViewById(R.id.poster);
            viewHolder.info_date = (TextView) convertView.findViewById(R.id.info_date);
            viewHolder.info_hot = (TextView) convertView.findViewById(R.id.info_hot);
            viewHolder.tags = (TextView) convertView.findViewById(R.id.info_tags);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RankingEntry rankingEntry = mRankingEntry[position];

        StringBuilder directors = new StringBuilder("");
        if (rankingEntry.getDirectors() == null)
            directors = new StringBuilder("未知");
        else
            for (String director : Objects.requireNonNull(rankingEntry.getDirectors())) {
                if (directors.length()+director.length() > 11) {
                    directors.append(director).append("等");
                    break;
                }
                directors.append(director).append(" ");
            }
        viewHolder.info_item_title.setText(rankingEntry.getNameCN());
        setTextMarquee(viewHolder.info_item_title);

        viewHolder.info_en_title.setText(rankingEntry.getNameEN());
        setTextMarquee(viewHolder.info_en_title);

        viewHolder.info_director.setText("导演：" + directors);

        String date = String.format("%tF", rankingEntry.getReleaseDate());
        viewHolder.info_date.setText((date.equals("null")) ? "上映日期未知" : date + " 上映");
        viewHolder.info_hot.setText("热度：" + String.format("%.1f", rankingEntry.getHot() / 10000.00) + "万");

        viewHolder.poster.setTag(rankingEntry.getPoster());
        viewHolder.poster.setImageResource(R.drawable.ic_launcher_foreground);

        asyncLoadImage(viewHolder.poster, rankingEntry.getPoster());
        Log.d("movie: ", rankingEntry.getNameCN()+rankingEntry.getPoster());

        StringBuilder tags = new StringBuilder("");
        if (rankingEntry.getTags() == null)
            tags.append("未知");
        else {
            for (String tag : Objects.requireNonNull(rankingEntry.getTags())) {
                if (tags.length()+tag.length() > 12) {
                    tags.append(tag).append("等");
                    break;
                }
                tags.append(tag).append("/");
            }
            viewHolder.tags.setText(tags.substring(0, tags.length() - 1));
        }

        return convertView;
    }

    private class ViewHolder {
        TextView info_item_title;
        TextView info_en_title;
        TextView info_director;
        ImageView poster;
        TextView info_date;
        TextView info_hot;
        ImageView rank_image;
        TextView tags;
    }

    public static void setTextMarquee(TextView textView) {
        if (textView != null) {
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setSingleLine(true);
            textView.setSelected(true);
            textView.setFocusable(true);
            textView.setFocusableInTouchMode(true);
        }
    }



    /**
     * @param url 　本地或网络的url
     * @return url的bitmap
     */
    public Bitmap getLocalOrNetBitmap(String url) {
        Bitmap bitmap = BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.ic_launcher_foreground);
        if (url != null) {
            InputStream in = null;
            BufferedOutputStream out = null;
            try {
                in = new BufferedInputStream(new URL(url).openStream(), 2 * 1024);
                final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                out = new BufferedOutputStream(dataStream, 2 * 1024);
                byte[] b = new byte[1024];
                int read;
                while ((read = in.read(b)) != -1) {
                    out.write(b, 0, read);
                }
                out.flush();
                byte[] data = dataStream.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                data = null;
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return bitmap;
            }
        }
        return bitmap;
    }

    private void asyncLoadImage(final ImageView imageView, final String uri) {
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Bitmap bitmap = (Bitmap) msg.obj;
                    if (imageView.getTag() != null && imageView.getTag().equals(uri)) {
                        imageView.setImageBitmap(bitmap);
                    }

                }
            }
        };
        // 子线程，开启子线程去下载或者去缓存目录找图片，并且返回图片在缓存目录的地址
        Runnable runnable = () -> {
            try {
                //这个URI是图片下载到本地后的缓存目录中的URI
                ImageCacheUtil imageCacheUtil = new ImageCacheUtil(mContext);
                if (uri != null) {
                    Bitmap bitmap = imageCacheUtil.getBitmap(uri);
                    if (bitmap == null) {
                        bitmap = getLocalOrNetBitmap(uri);
                        imageCacheUtil.putBitmap(uri, bitmap);
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = bitmap;
                    mHandler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(runnable).start();
    }

}
