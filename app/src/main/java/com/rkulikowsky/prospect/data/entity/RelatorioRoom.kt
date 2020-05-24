package com.rkulikowsky.prospect.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "relatorios")
data class RelatorioRoom (
    var contato: String? = null,
    var restricao: Boolean? = null,
    @PrimaryKey @ColumnInfo(name = "id") var objectId: String = "",
    @ColumnInfo(name = "sem_interesse") var semInteresse: Boolean? = null,
    var presencial: Boolean? = null,
    var obs: String? = null,
    var updated: Date? = null,
    @ColumnInfo(name = "n_atende") var naoAtende: Boolean? = null,
    var tipo: Int? = null,
    var consultor: String? = null,
    @ColumnInfo(name = "c_consultor") var cargoContato: String? = null,
    var cadastro: Boolean? = null,
    var created: Date? = null,
    @ColumnInfo(name = "p_visita") var proximaVisita: Date? = null,
    var reagendar: Boolean? = null,
    var cliente: String? = null)
