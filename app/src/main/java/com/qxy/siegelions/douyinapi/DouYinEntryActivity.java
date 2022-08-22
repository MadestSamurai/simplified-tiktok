package com.qxy.siegelions.douyinapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bytedance.sdk.open.aweme.CommonConstants;
import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler;
import com.bytedance.sdk.open.aweme.common.model.BaseReq;
import com.bytedance.sdk.open.aweme.common.model.BaseResp;
import com.bytedance.sdk.open.aweme.share.Share;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.qxy.siegelions.R;
import com.qxy.siegelions.TiktokActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class DouYinEntryActivity extends Activity implements IApiEventHandler {
    DouYinOpenApi douYinOpenApi;

    static String CLIENT_KEY = "awglr552ejo1dspq";
    static String CLIENT_SECRET = "68db8db7a2689167ceaff01f96a0d087";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_home);
        douYinOpenApi = DouYinOpenApiFactory.create(this);
        douYinOpenApi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == CommonConstants.ModeType.SHARE_CONTENT_TO_TT_RESP) {
            Share.Response response = (Share.Response) resp;
            Toast.makeText(this, " code：" + response.errorCode + " 文案：" + response.errorMsg, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, TiktokActivity.class);
            startActivity(intent);
        } else if (resp.getType() == CommonConstants.ModeType.SEND_AUTH_RESPONSE) {
            Authorization.Response response = (Authorization.Response) resp;
            Intent intent = null;
            if (resp.isSuccess()) {

                Toast.makeText(this, "授权成功", Toast.LENGTH_LONG).show();

                new Thread() {
                    @Override
                    public void run() {
                        networkRequest(response.authCode);
                    }
                }.start();

            }
        }
    }


    private void networkRequest(String authCode) {
        try {
            OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
            Request request = new Request.Builder()
                    .url("https://open.douyin.com/oauth/access_token/?client_key=" + CLIENT_KEY +
                            "&client_secret="+ CLIENT_SECRET +"&code=" + authCode + "&grant_type=authorization_code")
                    .build();//创建Request 对象
            Response response;
            response = client.newCall(request).execute();//得到Response 对象
            if (response.isSuccessful()) {
                Log.d("siegeLions", "response.code()==" + response.code());
                Log.d("siegeLions", "response.message()==" + response.message());
                assert response.body() != null;
                String accessJson = response.body().string();
                Log.d("siegeLions", "res==" + accessJson);
                //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                getUserInfo(accessJson);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getUserInfo(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String accessToken = jsonObject.getJSONObject("data").getString("access_token");
            String openId = jsonObject.getJSONObject("data").getString("open_id");

            OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
            RequestBody formBody = new FormBody.Builder()
                    .add("access_token", accessToken)
                    .add("open_id", openId)
                    .build();

            Request request = new Request.Builder()
                    .url("https://open.douyin.com/oauth/userinfo/")
                    .header("Content-Type", "application/json")
                    .header("access-token", accessToken)
                    .post(formBody)
                    .build();//创建Request 对象

            Response response;
            response = client.newCall(request).execute();//得到Response 对象
            if (response.isSuccessful()) {
                Log.d("siegeLions", "response.code()==" + response.code());
                Log.d("siegeLions", "response.message()==" + response.message());
                assert response.body() != null;
                String userInfoJson = response.body().string();
                Log.d("siegeLions", "res==" + userInfoJson);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorIntent(Intent intent) {
        // 错误数据
        Toast.makeText(this, "Intent出错", Toast.LENGTH_LONG).show();
    }
}
