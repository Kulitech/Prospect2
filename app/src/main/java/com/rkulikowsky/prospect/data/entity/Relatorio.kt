package com.rkulikowsky.prospect.data.entity

import java.util.*

data class Relatorio (
    var contato: String? = null,
    var restricao: Boolean? = null,
    var objectId: String? = null,
    var semInteresse: Boolean? = null,
    var presencial: Boolean? = null,
    var obs: String? = null,
    var updated: Date? = null,
    var naoAtende: Boolean? = null,
    var tipo: Int? = null,
    var consultor: String? = null,
    var cargoContato: String? = null,
    var cadastro: Boolean? = null,
    var created: Date? = null,
    var proximaVisita: Date? = null,
    var reagendar: Boolean? = null,
    var cliente: String? = null)
