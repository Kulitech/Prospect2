package com.rkulikowsky.prospect.util


class TipoCharFormat {
    companion object {
        operator fun invoke(tipo: Int?): String {
            return when(tipo) {
                Defaults.PROSPECCAO -> "P"
                Defaults.INATIVIDADE -> "I"
                Defaults.COBRANCA -> "C"
                else -> "??? - "
            }
        }
    }
}