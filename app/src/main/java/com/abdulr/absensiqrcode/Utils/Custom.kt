package com.abdulr.absensiqrcode.Utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.abdulr.absensiqrcode.R
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import org.slf4j.LoggerFactory

class Custom(private val activity: Activity) {
    private val progress: Dialog = Dialog(activity)
    private var builder: AlertDialog.Builder = AlertDialog.Builder(activity)
    private var dialog: Dialog = Dialog(activity)
    private var queue: RequestQueue = Volley.newRequestQueue(activity)
    private var session: Session = Session(activity)

    fun getSession(): Session {
        return session
    }

    fun go(tujuan: Class<*>): Intent {
        return Intent(activity, tujuan)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    fun defaultToolbar(actionBar: ActionBar?, titleActiobar: String, showBackBotton: Boolean) {
        actionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setCustomView(R.layout.toolbar)

        val toolbar = actionBar.customView.parent as Toolbar
        val title = toolbar.findViewById(R.id.title) as TextView
        val back = toolbar.findViewById(R.id.btn_back) as ImageButton

        toolbar.setContentInsetsAbsolute(0, 0)
        toolbar.contentInsetEnd
        toolbar.setPadding(0, 0, 0, 0)

        title.text = titleActiobar
        if (!showBackBotton) {
            back.visibility = View.GONE
        }

        back.setOnClickListener { activity.onBackPressed() }
    }

    fun customToolbar(actionBar: ActionBar?, layout: Int?): Toolbar {
        actionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setCustomView(layout!!)
        actionBar.elevation = 0f
        val toolbar = actionBar.customView.parent as Toolbar

        toolbar.setContentInsetsAbsolute(0, 0)
        toolbar.contentInsetEnd
        toolbar.setPadding(0, 0, 0, 0)

        return toolbar
    }

    fun message(
        types: Int, message: String, code: Int, positive: DialogInterface.OnClickListener?, negative: DialogInterface.OnClickListener?) {
        val type = arrayOfNulls<String>(2)

        when (types) {
            1 -> {
                type[0] = "Ya"
                type[1] = "Tidak"
            }

            2 -> {
                type[0] = "Ok"
                type[1] = "Batal"
            }
        }

        builder.setMessage(message)
        builder.setCancelable(false)
        when (code) {
            1 -> builder.setPositiveButton(type[0], positive)

            2 -> builder.setNegativeButton(type[1], negative)

            3 -> {
                builder.setPositiveButton(type[0], positive)
                builder.setNegativeButton(type[1], negative)
            }

            else -> {
            }
        }

        builder.create()
        builder.show()
    }

    fun allowPermission(code: Int) {
        if ((checkPermission(code))) {
            ActivityCompat.requestPermissions(activity, Global.listPermission(code), 1)
        }
    }

    fun checkPermission(code: Int): Boolean {
        for (persn in Global.listPermission(code)) {
            if (ActivityCompat.checkSelfPermission(activity, persn) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun loading(show: Boolean) {
        if (show) {
            progress.setContentView(R.layout.dialog_loading)
            progress.setCancelable(false)
            progress.setCanceledOnTouchOutside(false)
            progress.show()
        } else {
            if (progress.isShowing) {
                progress.dismiss()
            }
        }
    }

    fun get(url: String, headers: Map<String, String>?, response: Response.Listener<JSONObject>, error: Response.ErrorListener) {
        if (Global.isConnected(activity)) {
            val jsonObjectRequest = object : JsonObjectRequest(Method.GET, url, null, response, error) {
                override fun getHeaders(): Map<String, String>? {
                    return headers
                }
            }

            jsonObjectRequest.retryPolicy = DefaultRetryPolicy(60000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
            queue.add(jsonObjectRequest)
        } else {
            alert("No Internet Connected", "error")
            loading(false)
        }
    }

    fun post(url: String, headers: Map<String, String>?, params: Map<*, *>, response: Response.Listener<JSONObject>, error: Response.ErrorListener) {
        if (Global.isConnected(activity)) {
            log("info", "headers : " + headers!!)
            log("info", "param : $params")
            val obj = JSONObject(params)

            val jsonObjectRequest = object : JsonObjectRequest(Method.POST, url, obj, response, error) {
                override fun getHeaders(): Map<String, String>? {
                    return headers
                }
            }

            jsonObjectRequest.retryPolicy = DefaultRetryPolicy(60000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
            queue.add(jsonObjectRequest)
        } else {
            alert("No Internet Connected", "error")
            loading(false)
        }
    }

    fun alert(message: String, tipe: String) {
        val _toast: Int = when (tipe) {
            "default" -> FancyToast.DEFAULT

            "success" -> FancyToast.SUCCESS

            "info" -> FancyToast.INFO

            "warning" -> FancyToast.WARNING

            "error" -> FancyToast.ERROR

            "retry" -> FancyToast.CONFUSING

            else -> FancyToast.DEFAULT
        }

        FancyToast.makeText(activity, message, FancyToast.LENGTH_LONG, _toast, false).show()
    }

    fun log(tipe: String, pesan: String) {
        val log = LoggerFactory.getLogger(activity.localClassName)

        when (tipe) {
            "debug" -> log.debug(pesan)

            "info" -> log.info(pesan)

            "error" -> log.error(pesan)

            "trace" -> log.trace(pesan)

            "warning" -> log.warn(pesan)
        }
    }

    fun hideKeyboard() {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}