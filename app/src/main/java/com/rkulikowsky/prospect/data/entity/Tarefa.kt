package com.rkulikowsky.prospect.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Tarefa (
    var updated: Date? = null,
    var autorizado: Boolean? = null,
    var cliente: String? = null,
    var ultimoBordero: Date? = null,
    var obs: String? = null,
    var consultor: String? = null,
    var objectId: String? = null,
    var tipo: Int? = null,
    var visita: Boolean? = null,
    var created: Date? = null,
    var data: Date? = null) : Parcelable


