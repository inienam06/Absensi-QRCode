package com.abdulr.absensiqrcode.Controllers.Pengaturan

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdulr.absensiqrcode.Controllers.Login.LoginActivity
import com.abdulr.absensiqrcode.R
import com.abdulr.absensiqrcode.Utils.Custom
import kotlinx.android.synthetic.main.activity_pengaturan.*

class PengaturanActivity : AppCompatActivity() {
    private lateinit var custom: Custom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaturan)
        custom = Custom(this)
        initView()
    }

    private fun initView() {
        custom.defaultToolbar(supportActionBar, "Pengaturan", true)

        tvPengaturanKeluar.setOnClickListener {
            custom.message(1, "Anda yakin akan keluar dari akun anda ?", 3, DialogInterface.OnClickListener { _, _ ->
                custom.getSession().logout()
                startActivity(custom.go(LoginActivity::class.java))
                finishAffinity()
            }, DialogInterface.OnClickListener { _, _ ->  })
        }
    }
}
