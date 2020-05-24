package com.rkulikowsky.prospect.util

import org.joda.time.DateTime
import java.util.*

class DateStringFormat {
    companion object {
        operator fun invoke(date:Date?): String {
            val data = DateTime(date)
            val mes:String = if (data.monthOfYear>9) data.monthOfYear.toString() else "0${data.monthOfYear}"
            val dia:String = if (data.dayOfMonth>9) data.dayOfMonth.toString() else "0${data.dayOfMonth}"
            return "$dia/$mes/${data.year}"
        }
    }
}