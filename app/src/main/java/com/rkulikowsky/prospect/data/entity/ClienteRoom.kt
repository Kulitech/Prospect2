package com.rkulikowsky.prospect.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class ClienteRoom (
    var email: String?=null,
    var telefone1: String?=null,
    @PrimaryKey @ColumnInfo(name = "id") var objectId: String="",
    var porte: Int?=null,
    var consultor: String? = null,
    var created: java.util.Date? = null,
    var telefone3: String? = null,
    var duplicata: Boolean? = null,
    var dinheiro: Boolean? = null,
    var cartao: Boolean? = null,
    var indicacao: String? = null,
    var endereco: String? = null,
    var cidade: String? = null,
    var cheque: Boolean? = null,
    var bairro: String? = null,
    @ColumnInfo(name = "cargo") var indicacaoCargo: String? = null,
    var updated: java.util.Date? = null,
    var ramo: String? = null,
    var telefone2: String? = null,
    var nome: String? = null)



