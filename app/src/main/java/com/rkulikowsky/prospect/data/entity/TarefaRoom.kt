package com.rkulikowsky.prospect.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "tarefas")
data class  TarefaRoom (
    var updated: Date? = null,
    var autorizado: Boolean? = null,
    var cliente: String? = null,
    @ColumnInfo(name = "bordero") var ultimoBordero: Date? = null,
    var obs: String? = null,
    var consultor: String? = null,
    @PrimaryKey @ColumnInfo(name = "id") var objectId: String = "",
    var tipo: Int? = null,
    var visita: Boolean? = null,
    var created: Date? = null,
    var data: Date? = null):Parcelable


