package com.qxy.siegelions.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qxy.siegelions.config.DateDeserializer;
import com.qxy.siegelions.entity.RankingEntry;
import com.qxy.siegelions.entity.RankingVersion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 榜单接口数据获取及JSON反序列化类
 * @author MadSamurai
 */
public class RankingGetUtil {
    private final String CLIENT_KEY = "awobitbo8w4mf83r";
    private final String CLIENT_SECRET = "9108c9df11a7c5649c28e3ed426a0363";
    private Context context;

    public RankingGetUtil(Context context) {
        this.context = context;
    }

    private String clientTokenRequest(boolean forceRequest) {
        String clientToken = CommonUtil
                .getSettingNote(context, "client_info", "client_token");
        String clientTokenTimeString = CommonUtil
                .getSettingNote(context, "client_info", "client_token_time");
        long clientTokenTime;
        if (clientTokenTimeString != null)
            clientTokenTime = Long.parseLong(clientTokenTimeString);
        else clientTokenTime = 0;
        Log.d("siegeLions", "client_token" + clientToken);
        Log.d("siegeLions", "client_token_time" + clientTokenTime + " " + System.currentTimeMillis());
        if (forceRequest
                || (System.currentTimeMillis() - clientTokenTime) > 7200000L
                || clientToken == null || clientToken.equals("")) {
            try {
                OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                Request request = new Request.Builder()
                        .url("https://open.douyin.com/oauth/client_token/?client_key=" + CLIENT_KEY
                                + "&client_secret=" + CLIENT_SECRET + "&grant_type=client_credential")
                        .build();
                Response response;
                response = client.newCall(request).execute();//得到Response 对象
                if (response.isSuccessful()) {
                    Log.d("siegeLions", "response.code()==" + response.code());
                    Log.d("siegeLions", "response.message()==" + response.message());
                    assert response.body() != null;
                    String accessJson = response.body().string();
                    Log.d("siegeLions", "res==" + accessJson);

                    JSONObject jsonObject = new JSONObject(accessJson);
                    clientToken = jsonObject.getJSONObject("data").getString("access_token");
                    HashMap<String, String> dataMap = new HashMap<>();
                    dataMap.put("client_token", clientToken);
                    dataMap.put("client_token_time", System.currentTimeMillis() + "");
                    CommonUtil.saveSettingNote(context, "client_info", dataMap);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return clientToken;
    }

    /**
     * 从抖音接口获取排名信息
     *
     * @param type    榜单类型
     * @param version 榜单的版本号（可为空，即查询当前榜单）
     */
    public RankingEntry[] getRanking(int type, String version) {
        String accessToken = clientTokenRequest(false);
        String rankingJson = null;
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .header("Content-Type", "application/json")
                    .header("access-token", accessToken)
                    .url("https://open.douyin.com/discovery/ent/rank/item/?type=" + type + "&version=" + version)
                    .build();
            Response response;
            response = client.newCall(request).execute();//得到Response 对象
            if (response.isSuccessful()) {
                Log.d("siegeLions", "response.code()==" + response.code());
                Log.d("siegeLions", "response.message()==" + response.message());
                assert response.body() != null;
                rankingJson = response.body().string();
                Log.d("siegeLions", "res==" + rankingJson);
            } else {
                clientTokenRequest(true);
                request = new Request.Builder()
                        .header("Content-Type", "application/json")
                        .header("access-token", accessToken)
                        .url("https://open.douyin.com/discovery/ent/rank/item/?type=" + type + "&version=" + version)
                        .build();
                response = client.newCall(request).execute();//得到Response 对象
                if (response.isSuccessful()) {
                    Log.d("siegeLions", "response.code()==" + response.code());
                    Log.d("siegeLions", "response.message()==" + response.message());
                    assert response.body() != null;
                    rankingJson = response.body().string();
                    Log.d("siegeLions", "res==" + rankingJson);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String listJson = null;
        try {
            assert rankingJson != null;
            JSONObject dataJson = new JSONObject(rankingJson);
            listJson = ((JSONObject) dataJson.get("data")).get("list").toString();
            Log.d("siegeLions", "list" + listJson);
            Gson gson = new Gson();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());

        Gson gson = gsonBuilder
                .setDateFormat("yyyy-MM-dd")
                .create();
        return gson.fromJson(listJson, RankingEntry[].class);
    }

    /**
     * 从抖音接口获取当前排名信息
     *
     * @param type 榜单类型
     */
    public RankingEntry[] getRanking(int type) {
        return this.getRanking(type, "");
    }

    /**
     * 从抖音接口获取榜单对应编号信息
     *
     * @param cursor 获取起点
     * @param count  获取个数，不建议太大
     * @param type   榜单类型
     */
    public RankingVersion[] getRankingVersion(long cursor, int count, int type) {
        String accessToken = clientTokenRequest(false);
        String rankingJson = null;
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .header("Content-Type", "application/json")
                    .header("access-token", accessToken)
                    .url("https://open.douyin.com/discovery/ent/rank/version/?cursor=" + cursor + "&count=" + count + "&type=" + type)
                    .build();
            Response response;
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Log.d("siegeLions", "response.code()==" + response.code());
                Log.d("siegeLions", "response.message()==" + response.message());
                assert response.body() != null;
                rankingJson = response.body().string();
                Log.d("siegeLions", "res==" + rankingJson);
            } else {
                request = new Request.Builder()
                        .header("Content-Type", "application/json")
                        .header("access-token", accessToken)
                        .url("https://open.douyin.com/discovery/ent/rank/version/?cursor=" + cursor + "&count=" + count + "&type=" + type)
                        .build();
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    Log.d("siegeLions", "response.code()==" + response.code());
                    Log.d("siegeLions", "response.message()==" + response.message());
                    assert response.body() != null;
                    rankingJson = response.body().string();
                    Log.d("siegeLions", "res==" + rankingJson);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String listJson = null;
        try {
            assert rankingJson != null;
            JSONObject dataJson = new JSONObject(rankingJson);
            listJson = ((JSONObject) dataJson.get("data")).get("list").toString();
            Log.d("siegeLions", "list" + listJson);
            Gson gson = new Gson();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());

        Gson gson = gsonBuilder
                .setDateFormat("yyyy-MM-dd")
                .create();
        return gson.fromJson(listJson, RankingVersion[].class);
    }
}
