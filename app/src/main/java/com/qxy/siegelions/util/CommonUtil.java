package com.qxy.siegelions.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * SharedPreference存取类
 * @author MadSamurai
 */
public class CommonUtil {
    /**
     * 将字符串数据保存到本地
     *
     * @param context 上下文
     * @param filename 生成XML的文件名
     * @param dataMap map<生成XML中每条数据名,需要保存的数据>
     */
    public static void saveSettingNote(Context context, String filename , Map<String, String> dataMap) {
        SharedPreferences.Editor note = context.getSharedPreferences(filename, Context.MODE_PRIVATE).edit();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            note.putString(entry.getKey(), entry.getValue());
        }
        note.apply();
    }

    /**
     * 从本地取出要保存的数据
     * @param context 上下文
     * @param filename 文件名
     * @param dataName 生成XML中每条数据名
     * @return 对应的数据(找不到为null)
     */
    public static String getSettingNote(Context context,String filename ,String dataName) {
        SharedPreferences read = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return read.getString(dataName, null);
    }
}
