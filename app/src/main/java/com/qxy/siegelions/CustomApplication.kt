package com.qxy.siegelions

import android.app.Application
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.DouYinOpenConfig
import com.qxy.siegelions.util.CLIENT_KEY

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val clientkey = CLIENT_KEY // 需要到开发者网站申请并替换
        DouYinOpenApiFactory.init(DouYinOpenConfig(clientkey))
    }
}