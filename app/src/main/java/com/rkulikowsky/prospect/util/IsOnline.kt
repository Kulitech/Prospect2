package com.rkulikowsky.prospect.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object IsOnline {
    operator fun invoke(context: Context):Boolean{
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val ni: NetworkInfo? = cm.activeNetworkInfo
            return ni?.isConnected ?: false

    }
}