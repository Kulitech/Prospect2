package com.rkulikowsky.prospect.util

import com.rkulikowsky.prospect.data.entity.*


class BackToRoom {
    companion object {
        fun tarefasList(tarefas:List<Tarefa>): List<TarefaRoom> {
            val tempRoom = mutableListOf<TarefaRoom>()
            tarefas.forEach {
                tempRoom.add(TarefaRoom(it.updated,it.autorizado,it.cliente,it.ultimoBordero,it.obs,it.consultor,it.objectId?:"na",it.tipo,it.visita,it.created,it.data))
            }
            return tempRoom
            }

        fun clientesList(clientes:List<Cliente>): List<ClienteRoom> {
            val tempRoom = mutableListOf<ClienteRoom>()
            clientes.forEach {
                tempRoom.add(
                    ClienteRoom(it.email,it.telefone1,it.objectId?:"na",it.porte,it.consultor,it.created,it.telefone3,it.duplicata,it.dinheiro,it.cartao,
                        it.indicacao,it.endereco,it.cidade,it.cheque,it.bairro,it.indicacaoCargo,it.updated,it.ramo,it.telefone2,it.nome)
                )
            }
            return tempRoom
        }

        fun relatoriosList(relatorios:List<Relatorio>): List<RelatorioRoom> {
            val tempRoom = mutableListOf<RelatorioRoom>()
            relatorios.forEach {
                tempRoom.add(
                    RelatorioRoom(it.contato,it.restricao,it.objectId?:"na",it.semInteresse,it.presencial,it.obs,it.updated,
                        it.naoAtende,it.tipo,it.consultor,it.cargoContato,it.cadastro,it.created,it.proximaVisita,it.reagendar,it.cliente)
                )
            }
            return tempRoom
        }

        fun singleTarefa(it:Tarefa): TarefaRoom {
            return TarefaRoom(it.updated,it.autorizado,it.cliente,it.ultimoBordero,it.obs,it.consultor,it.objectId?:"na",it.tipo,it.visita,it.created,it.data)
        }

        fun singleCliente(it:Cliente): ClienteRoom {
            return ClienteRoom(it.email,it.telefone1,it.objectId?:"na",it.porte,it.consultor,it.created,it.telefone3,it.duplicata,it.dinheiro,it.cartao,
                it.indicacao,it.endereco,it.cidade,it.cheque,it.bairro,it.indicacaoCargo,it.updated,it.ramo,it.telefone2,it.nome)
        }

        fun singleRelatorio(it:Relatorio): RelatorioRoom {
            return RelatorioRoom(it.contato,it.restricao,it.objectId?:"na",it.semInteresse,it.presencial,it.obs,it.updated,
                it.naoAtende,it.tipo,it.consultor,it.cargoContato,it.cadastro,it.created,it.proximaVisita,it.reagendar,it.cliente)
        }

        fun singleTarefaReverse(it:TarefaRoom):Tarefa{
            return Tarefa(it.updated,it.autorizado,it.cliente,it.ultimoBordero,it.obs,it.consultor,it.objectId,it.tipo,it.visita,it.created,it.data)
        }

        /*
        fun singleClienteInativoReverse(it:ClienteRoom):ClienteInativo{
            return ClienteInativo(it.email,it.telefone1,null,it.porte,it.consultor,it.created,it.telefone3,it.duplicata,it.dinheiro,it.cartao,
                it.indicacao,it.endereco,it.cidade,it.cheque,it.bairro,it.indicacaoCargo,it.updated,it.ramo,it.telefone2,it.nome)
        }

        fun singleClienteReverse(it:ClienteRoom):Cliente{
            return Cliente(it.email,it.telefone1,it.objectId,it.porte,it.consultor,it.created,it.telefone3,it.duplicata,it.dinheiro,it.cartao,
                it.indicacao,it.endereco,it.cidade,it.cheque,it.bairro,it.indicacaoCargo,it.updated,it.ramo,it.telefone2,it.nome)
        }

         */
    }
}