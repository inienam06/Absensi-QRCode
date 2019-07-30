package com.abdulr.absensiqrcode.Controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdulr.absensiqrcode.R
import com.abdulr.absensiqrcode.Utils.Custom
import com.abdulr.absensiqrcode.Utils.Global
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var custom: Custom
    private lateinit var listener: BottomNavigationView.OnNavigationItemSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        custom = Custom(this)

        initView()
    }

    private fun initView() {
        Global.setFragment(supportFragmentManager, R.id.content, BerandaFragment(this), "Beranda")

        listener = BottomNavigationView.OnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_beranda -> {
                    Global.setFragment(supportFragmentManager, R.id.content, BerandaFragment(this), "Beranda")
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_riwayat -> {
                    Global.setFragment(supportFragmentManager, R.id.content, RiwayatFragment(this), "Riwayat")
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_profil -> {
                    Global.setFragment(supportFragmentManager, R.id.content, ProfilFragment(this), "Profil")
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
        nav_container.setOnNavigationItemSelectedListener(listener)
    }
}
