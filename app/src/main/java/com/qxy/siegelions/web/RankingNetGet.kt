package com.qxy.siegelions.web

import android.content.Context
import android.util.Log
import com.qxy.siegelions.util.CommonUtil.getSettingNote
import com.qxy.siegelions.util.CommonUtil.saveSettingNote
import okhttp3.OkHttpClient
import org.json.JSONObject
import org.json.JSONException
import com.qxy.siegelions.entity.RankingEntry
import com.google.gson.GsonBuilder
import com.qxy.siegelions.config.DateDeserializer
import com.qxy.siegelions.entity.RankingEntryReq
import com.qxy.siegelions.entity.RankingVersion
import com.qxy.siegelions.entity.RankingVersionReq
import com.qxy.siegelions.util.CLIENT_KEY
import com.qxy.siegelions.util.CLIENT_SECRET
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 榜单接口数据获取及JSON反序列化类
 *
 * @author MadSamurai
 */
class RankingNetGet(private val context: Context) {
    private fun clientTokenRequest(forceRequest: Boolean): String? {
        var clientToken = getSettingNote(context, "client_info", "client_token")
        val clientTokenTimeString = getSettingNote(context, "client_info", "client_token_time")
        val clientTokenTime: Long = clientTokenTimeString?.toLong() ?: 0
        Log.d("siegeLions", "client_token$clientToken")
        Log.d(
            "siegeLions",
            "client_token_time" + clientTokenTime + " " + System.currentTimeMillis()
        )
        if (forceRequest
            || System.currentTimeMillis() - clientTokenTime > 7200000L || clientToken == null || clientToken == ""
        ) {
            try {
                val client = OkHttpClient() //创建OkHttpClient对象
                val request = Request.Builder()
                    .url(
                        "https://open.douyin.com/oauth/client_token/?client_key=" + CLIENT_KEY
                                + "&client_secret=" + CLIENT_SECRET + "&grant_type=client_credential"
                    )
                    .build()
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    Log.d("siegeLions", "response.code()==" + response.code())
                    Log.d("siegeLions", "response.message()==" + response.message())
                    assert(response.body() != null)
                    val accessJson = response.body()!!.string()
                    Log.d("siegeLions", "res==$accessJson")
                    val jsonObject = JSONObject(accessJson)
                    clientToken = jsonObject.getJSONObject("data").getString("access_token")
                    val dataMap = HashMap<String?, String?>()
                    dataMap["client_token"] = clientToken
                    dataMap["client_token_time"] = System.currentTimeMillis().toString() + ""
                    saveSettingNote(context, "client_info", dataMap)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return clientToken
    }

    /**
     * 从抖音接口获取排名信息
     *
     * @param type    榜单类型
     * @param version 榜单的版本号（可为空，即查询当前榜单）
     */
    private fun getRanking(type: Int, version: String): RankingEntryReq? {
        val accessToken = clientTokenRequest(false)
        var rankingJson: String? = null
        try {
            val client = OkHttpClient()
            var request = accessToken?.let {
                Request.Builder()
                    .header("Content-Type", "application/json")
                    .header("access-token", it)
                    .url("https://open.douyin.com/discovery/ent/rank/item/?type=$type&version=$version")
                    .build()
            }
            var response: Response
            response = request?.let { client.newCall(it).execute() }!! //得到Response 对象
            if (response.isSuccessful) {
                Log.d("siegeLions", "response.code()==" + response.code())
                Log.d("siegeLions", "response.message()==" + response.message())
                assert(response.body() != null)
                rankingJson = response.body()!!.string()
                Log.d("siegeLions", "res==$rankingJson")
            } else {
                clientTokenRequest(true)
                request = accessToken?.let {
                    Request.Builder()
                        .header("Content-Type", "application/json")
                        .header("access-token", it)
                        .url("https://open.douyin.com/discovery/ent/rank/item/?type=$type&version=$version")
                        .build()
                }
                response = request?.let { client.newCall(it).execute() }!! //得到Response 对象
                if (response.isSuccessful) {
                    Log.d("siegeLions", "response.code()==" + response.code())
                    Log.d("siegeLions", "response.message()==" + response.message())
                    assert(response.body() != null)
                    rankingJson = response.body()!!.string()
                    Log.d("siegeLions", "res==$rankingJson")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var listJson: String? = null
        var activeTime = Date()
        try {
            assert(rankingJson != null)
            val dataJson = rankingJson?.let { JSONObject(it) }?.get("data") as JSONObject
            listJson = dataJson["list"].toString()
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            activeTime = simpleDateFormat.parse(dataJson["active_time"].toString()) as Date

            Log.d("get listJson", "list: $listJson")
            Log.d("get activeTime", "activeTime: $activeTime")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Date::class.java, DateDeserializer())
        val gson = gsonBuilder
            .setDateFormat("yyyy-MM-dd")
            .create()
        val rankingEntries = gson.fromJson(listJson, Array<RankingEntry>::class.java)
            ?: return null
        for (i in rankingEntries.indices) {
            rankingEntries[i].rank = i + 1
        }
        return RankingEntryReq(
            activeTime,
            rankingEntries
        )
    }

    /**
     * 从抖音接口获取当前排名信息
     *
     * @param type 榜单类型
     */
    fun getRanking(type: Int): RankingEntryReq? {
        return this.getRanking(type, "")
    }

    /**
     * 从抖音接口获取榜单对应编号信息
     *
     * @param cursor 获取起点
     * @param count  获取个数，不建议太大
     * @param type   榜单类型
     */
    fun getRankingVersion(cursor: Long, count: Int, type: Int): RankingVersionReq {
        val accessToken = clientTokenRequest(false)
        var rankingJson: String? = null
        try {
            val client = OkHttpClient()
            var request = accessToken?.let {
                Request.Builder()
                    .header("Content-Type", "application/json")
                    .header("access-token", it)
                    .url("https://open.douyin.com/discovery/ent/rank/version/?cursor=$cursor&count=$count&type=$type")
                    .build()
            }
            var response: Response
            response = request?.let { client.newCall(it).execute() }!!
            if (response.isSuccessful) {
                Log.d("siegeLions", "response.code()==" + response.code())
                Log.d("siegeLions", "response.message()==" + response.message())
                assert(response.body() != null)
                rankingJson = response.body()!!.string()
                Log.d("siegeLions", "res==$rankingJson")
            } else {
                clientTokenRequest(true)
                request = accessToken?.let {
                    Request.Builder()
                        .header("Content-Type", "application/json")
                        .header("access-token", it)
                        .url("https://open.douyin.com/discovery/ent/rank/version/?cursor=$cursor&count=$count&type=$type")
                        .build()
                }
                response = request?.let { client.newCall(it).execute() }!!
                if (response.isSuccessful) {
                    Log.d("siegeLions", "response.code()==" + response.code())
                    Log.d("siegeLions", "response.message()==" + response.message())
                    assert(response.body() != null)
                    rankingJson = response.body()!!.string()
                    Log.d("siegeLions", "res==$rankingJson")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var listJson: String? = null
        var cursorGet = 0
        var hasMore: Boolean? = null
        try {
            assert(rankingJson != null)
            val dataJson = rankingJson?.let { JSONObject(it) }?.get("data") as JSONObject
            listJson = dataJson["list"].toString()
            Log.d("siegeLions", "list$listJson")
            cursorGet = dataJson["cursor"] as Int
            hasMore = dataJson["has_more"] as Boolean
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Date::class.java, DateDeserializer())
        val gson = gsonBuilder
            .setDateFormat("yyyy-MM-dd")
            .create()
        return RankingVersionReq(
            cursorGet,
            hasMore,
            gson.fromJson(listJson, Array<RankingVersion>::class.java)
        )
    }
}