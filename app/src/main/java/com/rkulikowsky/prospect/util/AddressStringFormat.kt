package com.rkulikowsky.prospect.util

import com.rkulikowsky.prospect.data.entity.ClienteRoom
import java.util.*

class AddressStringFormat {
    companion object {
        operator fun invoke(clienteRoom: ClienteRoom?): String {

            return "${clienteRoom?.endereco?.toLowerCase(Locale.getDefault())?.capitalize()?:""}\n${clienteRoom?.bairro?:""}\n${clienteRoom?.cidade?:""}"
        }
    }
}