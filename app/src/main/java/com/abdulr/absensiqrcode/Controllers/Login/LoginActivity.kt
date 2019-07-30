package com.abdulr.absensiqrcode.Controllers.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdulr.absensiqrcode.Controllers.MainActivity
import com.abdulr.absensiqrcode.R
import com.abdulr.absensiqrcode.Utils.API
import com.abdulr.absensiqrcode.Utils.Custom
import com.android.volley.Response
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var custom: Custom
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        custom = Custom(this)

        initView()
    }

    private fun initView() {
        btnMasuk.setOnClickListener {
            val nim: String = etNim.text.toString()
            val password: String = etPassword.text.toString()

            doLogin(nim, password)
        }
    }

    private fun doLogin(nim: String, password: String) {
        custom.loading(true)
        val headers = mutableMapOf<String, String>()
        val params = mutableMapOf<String, String>()

        headers["Content-Type"] = "application/json"
        headers["apikey"] = API.apikey

        params["nim"] = nim
        params["password"] = password

        custom.post(API.login, headers, params, Response.Listener {
            custom.log("info","response :$it")

            if(it.getInt("code") != 200) {
                custom.alert(it.getString("message"), "error")
            } else {
                custom.getSession().login(it.getJSONObject("data").getString("id_mahasiswa"), it.getJSONObject("data").getString("nama"), nim,
                    it.getJSONObject("data").getString("api_token"), it.getJSONObject("data").getString("token_firebase"))
                startActivity(custom.go(MainActivity::class.java))
                finish()
            }

            custom.loading(false)
        }, Response.ErrorListener {
            custom.log("error", "error :$it")
            custom.loading(false)
        })
    }
}
