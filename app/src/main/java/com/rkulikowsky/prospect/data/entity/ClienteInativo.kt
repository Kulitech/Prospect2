package com.rkulikowsky.prospect.data.entity

data class ClienteInativo (
    var email: String?=null,
    var tel1: String?=null,
    var objectId: String?=null,
    var porte: Int?=null,
    var consultor: String? = null,
    var created: java.util.Date? = null,
    var tel3: String? = null,
    var duplicata: Boolean? = null,
    var dinheiro: Boolean? = null,
    var cartao: Boolean? = null,
    var indicacao: String? = null,
    var endereco: String? = null,
    var cidade: String? = null,
    var cheque: Boolean? = null,
    var bairro: String? = null,
    var indicacaoCargo: String? = null,
    var updated: java.util.Date? = null,
    var ramo: String? = null,
    var tel2: String? = null,
    var nome: String?=null)