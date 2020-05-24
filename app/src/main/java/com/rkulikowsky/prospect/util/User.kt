package com.rkulikowsky.prospect.util

import android.content.Context

object User {
    operator fun invoke(context: Context):String?{
        val shared= context.getSharedPreferences("prospect",Context.MODE_PRIVATE)
        return shared.getString("user", "na")
    }

}