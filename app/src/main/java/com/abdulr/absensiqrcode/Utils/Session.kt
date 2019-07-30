package com.abdulr.absensiqrcode.Utils

import android.content.Context
import android.content.SharedPreferences
import com.abdulr.absensiqrcode.R

class Session(context: Context) {
    private var pref: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = pref.edit()
    private var id: String = "id"
    private var nama: String = "nama"
    private var nim: String = "nim"
    private var token: String = "token"
    private var firebase: String = "firebase"
    private var isLogin: String = "is_login";

    //TODO: SETTER
    fun setId(value: String) {
        editor.putString(id, value).apply()
    }

    fun setNama(value: String) {
        editor.putString(nama, value).apply()
    }

    fun setNim(value: String) {
        editor.putString(nim, value).apply()
    }

    fun setToken(value: String) {
        editor.putString(token, value).apply()
    }

    fun setFirebase(value: String) {
        editor.putString(firebase, value).apply()
    }

    fun setIsLogin(value: Boolean) {
        editor.putBoolean(isLogin, value).apply()
    }

    //TODO: GETTER
    fun getId(): String {
        return pref.getString(id, "")
    }

    fun getNama(): String {
        return pref.getString(nama, "")
    }

    fun getNim(): String {
        return pref.getString(nim, "")
    }

    fun getToken(): String {
        return pref.getString(token, "")
    }

    fun getFirebase(): String {
        return pref.getString(firebase, "")
    }

    fun getIsLogin(): Boolean {
        return pref.getBoolean(isLogin, false)
    }

    //TODO: ADDITIONAL
    fun login(id: String, nama: String, nim: String, token: String, firebase: String) {
        editor.putString(this.id, id)
            .putString(this.nama, nama)
            .putString(this.nim, nim)
            .putString(this.token, token)
            .putString(this.firebase, firebase)
            .putBoolean(this.isLogin, true)
            .apply()
    }

    fun logout() {
        editor.clear().commit()
    }
}