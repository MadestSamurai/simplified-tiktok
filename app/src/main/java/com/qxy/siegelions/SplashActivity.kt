package com.qxy.siegelions

import android.content.Intent
import android.os.CountDownTimer
import com.qxy.siegelions.base.BaseActivity

/**
 * App启动页面
 * @author MadSamurai
 */
class SplashActivity : BaseActivity() {

    override fun setLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun init() {
        setFullScreen()
        object : CountDownTimer(500, 500) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }.start()
    }
}