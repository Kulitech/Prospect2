package com.rkulikowsky.prospect.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.rkulikowsky.prospect.data.dao.ClienteDao
import com.rkulikowsky.prospect.data.dao.RelatorioDao
import com.rkulikowsky.prospect.data.dao.TarefaDao
import com.rkulikowsky.prospect.data.entity.ClienteRoom
import com.rkulikowsky.prospect.data.entity.RelatorioRoom
import com.rkulikowsky.prospect.data.entity.TarefaRoom

class Repository(
    private val clienteDao: ClienteDao,
    private val tarefaDao: TarefaDao,
    private val relatorioDao: RelatorioDao) {


    val clientes: LiveData<List<ClienteRoom>> = clienteDao.getAllClients()
    val tarefas: LiveData<List<TarefaRoom>> = tarefaDao.getAllTarefas()
    val relatorios: LiveData<List<RelatorioRoom>> = relatorioDao.getAllRelatorios()



    @WorkerThread
    suspend fun insertListClientes(clienteRooms: List<ClienteRoom>) {
        clienteDao.insertListClients(clienteRooms)
    }

    @WorkerThread
    suspend fun insertOneClient(clienteRoom: ClienteRoom) {
        clienteDao.insertOneClient(clienteRoom)
    }

    @WorkerThread
    suspend fun insertOneTask(tarefaRoom: TarefaRoom) {
        tarefaDao.insertOneTask(tarefaRoom)
    }

    @WorkerThread
    suspend fun insertOneRelat(relatorioRoom: RelatorioRoom) {
        relatorioDao.insertOneRelatorio(relatorioRoom)
    }

    @WorkerThread
    suspend fun deleteSingleTask(tarefaRoom: TarefaRoom){
        tarefaDao.deleteSingleTarefa(tarefaRoom)
    }

    @WorkerThread
    suspend fun deleteSingleCliente(clienteRoom: ClienteRoom){
        clienteDao.deleteSingleCliente(clienteRoom)
    }
}