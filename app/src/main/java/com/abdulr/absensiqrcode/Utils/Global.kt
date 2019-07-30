package com.abdulr.absensiqrcode.Utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.text.DecimalFormat
import java.text.NumberFormat

class Global {
    companion object {
        fun convertPhoneNumber(value: String): String {
            return value.replace("[^0-9\\+]".toRegex(), "")
        }

        fun number_format(value: Int): String {
            val formatter = NumberFormat.getInstance() as DecimalFormat
            val symbols = formatter.decimalFormatSymbols
            symbols.currencySymbol = "" // Don't use null.
            formatter.decimalFormatSymbols = symbols
            return formatter.format(value.toLong())
        }

        fun listPermission(code: Int): Array<String> {
            val PERMISSION_REQUEST: Array<String>
            val defaultPermission = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

            PERMISSION_REQUEST = when (code) {
                1 -> arrayOf(defaultPermission[0])

                2 -> arrayOf(defaultPermission[1], defaultPermission[2])

                3 -> arrayOf(defaultPermission[3], defaultPermission[4])

                else -> defaultPermission
            }

            return PERMISSION_REQUEST
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun isConnected(context: Context): Boolean {
            val connected: Boolean
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val wifi = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork).hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            val mobile = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork).hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            connected =
                wifi || mobile
            return connected
        }

        fun setFragment(fragmentManager: FragmentManager, id: Int, fragment: Fragment, TAG: String, bundle: Bundle?, anims: Array<Int>?) {
            try {
                val transaction = fragmentManager.beginTransaction()

                if (bundle != null) {
                    fragment.arguments = bundle
                }

                if (anims != null && anims.size > 1) {
                    transaction.setCustomAnimations(anims[0], anims[1])
                }

                transaction.replace(id, fragment, TAG).commit()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }

        }

        fun setFragment(fragmentManager: FragmentManager, id: Int, fragment: Fragment, TAG: String, bundle: Bundle) {
            try {
                val transaction = fragmentManager.beginTransaction()
                fragment.arguments = bundle

                transaction.replace(id, fragment, TAG).commit()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }

        }

        fun setFragment(fragmentManager: FragmentManager, id: Int, fragment: Fragment, TAG: String, anims: Array<Int>) {
            try {
                val transaction = fragmentManager.beginTransaction()

                if (anims.size > 1) {
                    transaction.setCustomAnimations(anims[0], anims[1])
                }

                transaction.replace(id, fragment, TAG).commit()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }

        fun setFragment(fragmentManager: FragmentManager, id: Int, fragment: Fragment, TAG: String) {
            try {
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(id, fragment, TAG).commit()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }
}