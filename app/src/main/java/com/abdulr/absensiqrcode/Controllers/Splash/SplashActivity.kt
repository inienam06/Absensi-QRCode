package com.abdulr.absensiqrcode.Controllers.Splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.abdulr.absensiqrcode.Controllers.Login.LoginActivity
import com.abdulr.absensiqrcode.Controllers.MainActivity
import com.abdulr.absensiqrcode.R
import com.abdulr.absensiqrcode.Utils.Custom

class SplashActivity : AppCompatActivity() {
    private lateinit var custom: Custom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        custom = Custom(this)

        getData()
    }

    private fun getData() {
        Handler().postDelayed({
            if(custom.getSession().getIsLogin()) {
                startActivity(custom.go(MainActivity::class.java))
            } else {
                startActivity(custom.go(LoginActivity::class.java))
            }
            finish()
        }, 1500)
    }
}
