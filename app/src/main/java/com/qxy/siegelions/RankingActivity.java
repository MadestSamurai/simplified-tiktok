package com.qxy.siegelions;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RankingActivity extends AppCompatActivity {
    private final String CLIENT_KEY = "awobitbo8w4mf83r";
    private final String CLIENT_SECRET = "9108c9df11a7c5649c28e3ed426a0363";

    private final int TYPE_MOVIE = 1;
    private final int TYPE_TELEPLAY = 2;
    private final int TYPE_VARIETY_SHOW = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        findViewById(R.id.get_ranking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        getRanking(clientTokenRequest(), TYPE_MOVIE);
                    }
                }.start();
            }
        });
        findViewById(R.id.get_ranking_version).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        getRankingVersion(clientTokenRequest(), 0, 15, TYPE_MOVIE);
                    }
                }.start();
            }
        });
    }

    private String clientTokenRequest() {
        try {
            OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
            Request request = new Request.Builder()
                    .url("https://open.douyin.com/oauth/client_token/?client_key="+ CLIENT_KEY
                            + "&client_secret="+ CLIENT_SECRET +"&grant_type=client_credential")
                    .build();
            Response response;
            response = client.newCall(request).execute();//得到Response 对象
            if (response.isSuccessful()) {
                Log.d("siegeLions", "response.code()==" + response.code());
                Log.d("siegeLions", "response.message()==" + response.message());
                assert response.body() != null;
                String accessJson = response.body().string();
                Log.d("siegeLions", "res==" + accessJson);
                //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                JSONObject jsonObject = new JSONObject(accessJson);
                return jsonObject.getJSONObject("data").getString("access_token");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getRanking(String accessToken, int type) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .header("Content-Type", "application/json")
                    .header("access-token", accessToken)
                    .url("https://open.douyin.com/discovery/ent/rank/item/?type=" + type + "&version=")
                    .build();
            Response response;
            response = client.newCall(request).execute();//得到Response 对象
            if (response.isSuccessful()) {
                Log.d("siegeLions", "response.code()==" + response.code());
                Log.d("siegeLions", "response.message()==" + response.message());
                assert response.body() != null;
                String rankingJson = response.body().string();
                Log.d("siegeLions", "res==" + rankingJson);
                Looper.prepare();
                Toast.makeText(this, "获取成功：" + rankingJson,
                        Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getRankingVersion(String accessToken, long cursor, int count, int type) {
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
                String rankingJson = response.body().string();
                Log.d("siegeLions", "res==" + rankingJson);
                Looper.prepare();
                Toast.makeText(this, "获取成功：" + rankingJson,
                        Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
