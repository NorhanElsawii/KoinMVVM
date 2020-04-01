package com.example.koinmvvm.data.local

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


class ConnectivityUtils constructor(private val context: Context) {

    /**
     * Get the network info
     */
    private fun getNetworkInfo(context: Context): NetworkInfo? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

    /**
     * Check if there is any connectivity
     */
    fun isConnected(): Boolean {
        val info = getNetworkInfo(context)

        return info != null && info.isConnected
    }

    /**
     * Check if there is any connectivity to a Wifi network
     */
    fun isConnectedWifi(): Boolean {
        val info = getNetworkInfo(context)
        return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * Check if there is any connectivity to a mobile network
     */
    fun isConnectedMobile(): Boolean {
        val info = getNetworkInfo(context)
        return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_MOBILE
    }


//    private fun getConnectionType(): Int {
//        var result = NONE // Returns connection type. 0: none; 1: mobile data; 2: wifi
//        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            cm?.run {
//                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
//                    if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
//                        result = WIFI
//                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
//                        result = CELLULAR
//                    }
//                }
//            }
//        } else {
//            cm?.run {
//                cm.activeNetworkInfo?.run {
//                    if (type == ConnectivityManager.TYPE_WIFI) {
//                        result = WIFI
//                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
//                        result = CELLULAR
//                    }
//                }
//            }
//        }
//        return result
//    }
//
//    fun isConnectedToNetwork(): Boolean {
//        return getConnectionType() != NONE
//    }
//
//    companion object {
//        const val NONE = 0
//        const val CELLULAR = 1
//        const val WIFI = 2
//    }
//

}
