package com.qxy.siegelions.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.security.MessageDigest;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * 图像三级缓存工具类
 * @author MadSamurai
 */
public class ImageCacheUtil implements ImageCache {

    /**
     * PYX LRU缓存区
     */
    private LruCache<String, Bitmap> lruCache;

    /**
     * 软引用缓存
     */
    private HashMap<String, SoftReference<Bitmap>> softCache;

    /**
     * Context 用于获取当前应用程序的存储卡缓存文件目录，这个必须设置
     */
    private Context context;

    /**
     * 创建图片缓存区实例 Context 这个字段必须设置，因为需要通过这个变量来获取缓冲区目录
     */
    public ImageCacheUtil(Context ct) {
        // 5MB 内存大小
        lruCache = new LruCache<String, Bitmap>(5 * 1024 * 1024);
        // 软引用
        softCache = new HashMap<String, SoftReference<Bitmap>>();
        context = ct;
    }

    /**
     * 从缓存加载，如果返回null，那么就会自动从网络加载
     *
     * @param url 链接
     * @return
     */
    @Override
    public Bitmap getBitmap(String url) {
        Bitmap ret = null;
        if (url != null) {

            // 第一步，从LRU缓存获取图片
            Bitmap bitmap = lruCache.get(url);

            if (bitmap != null) { // 从LRUCache中找到数据
                ret = bitmap; // 找到图片直接返回
            } else {
                // 没有在LRUCache中找到，就要检查软引用的数据是否有

                SoftReference<Bitmap> reference = softCache.get(url);
                if (reference != null) {
                    bitmap = reference.get();
                }

                if (bitmap != null) { // 从软引用中找到实际的数据，则返回
                    ret = bitmap;
                    lruCache.put(url, ret); // 更新LRU缓存
                } else {
                    // LruCache 和SoftReference中都没有，那么就要检查SD卡中是否存在

                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        // 存在SD卡，进行读取

                        // 所有在此目录中存储的文件，在应用程序卸载的时候，被Android系统清空
                        // 只有自身程序可以访问，此目录，其他程序无法访问
                        File cacheDir = context.getExternalCacheDir();

                        File imageCacheFolder = new File(cacheDir, "images");

                        boolean bok = true;

                        if (!imageCacheFolder.exists()) {
                            bok = imageCacheFolder.mkdirs();
                        }

                        if (bok) {
                            try {
                                MessageDigest digest = MessageDigest
                                        .getInstance("MD5");
                                digest.update(url.getBytes());
                                byte[] bytes = digest.digest();
                                String hex = hex(bytes);

                                File targetFile = new File(imageCacheFolder,
                                        hex);

                                if (targetFile.exists()) {
                                    // 读取文件，并生成Bitmap返回，并且，加入到LruCache
                                    // 并且加入到 SoftReference
                                    try {
                                        FileInputStream fin = new FileInputStream(
                                                targetFile);
                                        bitmap = BitmapFactory
                                                .decodeStream(fin);
                                        fin.close();
                                        ret = bitmap;

                                        lruCache.put(url, bitmap);
                                        softCache.put(url,
                                                new SoftReference<Bitmap>(
                                                        bitmap));

                                    } catch (IOException ioe) {
                                        ioe.printStackTrace();
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                }

            }
        }
        return ret;
    }

    /**
     * @param url 链接
     * @param bitmap 图片
     */
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        lruCache.put(url, bitmap);
        softCache.put(url, new SoftReference<Bitmap>(bitmap));

        // 所有在此目录中存储的文件，在应用程序卸载的时候，被android系统清空
        // 只有自身程序可以访问，此目录，其他程序无法访问
        File cacheDir = context.getExternalCacheDir();

        File imageCacheFolder = new File(cacheDir, "images");

        boolean bok = true;

        if (!imageCacheFolder.exists()) {
            bok = imageCacheFolder.mkdirs();
        }

        if (bok) {
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(url.getBytes());
                byte[] bytes = digest.digest();
                String hex = hex(bytes);

                File targetFile = new File(imageCacheFolder, hex);

                try {
                    if (!targetFile.exists()) {
                        bok = targetFile.createNewFile();
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

                if (bok) {
                    // TODO 写入文件
                    try {

                        FileOutputStream fout = new FileOutputStream(targetFile);

                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);

                        fout.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private static String hex(byte[] bytes) {
        String ret = null;
        if (bytes != null) {
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                int h = (aByte >> 4) & 0x0F;
                int l = (aByte & 0x0F);
                char ch = 0, cl = 0;
                if (h > 9) {
                    ch = (char) ('A' + h - 10);
                } else {
                    ch = (char) (h + '0');
                }
                if (l > 9) {
                    cl = (char) ('A' + l - 10);
                } else {
                    cl = (char) (l + '0');
                }

                sb.append(ch).append(cl);
            }
            ret = sb.toString();
            sb = null;
        }
        return ret;
    }

}