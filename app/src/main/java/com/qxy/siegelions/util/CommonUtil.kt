package com.qxy.siegelions.util

import android.content.Context

/**
 * SharedPreference存取类
 * @author MadSamurai
 */
object CommonUtil {
    /**
     * 将字符串数据保存到本地
     *
     * @param context 上下文
     * @param filename 生成XML的文件名
     * @param dataMap map<生成XML中每条数据名></生成XML中每条数据名>,需要保存的数据>
     */
    @JvmStatic
    fun saveSettingNote(context: Context, filename: String?, dataMap: Map<String?, String?>) {
        val note = context.getSharedPreferences(filename, Context.MODE_PRIVATE).edit()
        for ((key, value) in dataMap) {
            note.putString(key, value)
        }
        note.apply()
    }

    /**
     * 从本地取出要保存的数据
     * @param context 上下文
     * @param filename 文件名
     * @param dataName 生成XML中每条数据名
     * @return 对应的数据(找不到为null)
     */
    @JvmStatic
    fun getSettingNote(context: Context, filename: String?, dataName: String?): String? {
        val read = context.getSharedPreferences(filename, Context.MODE_PRIVATE)
        return read.getString(dataName, null)
    }
}