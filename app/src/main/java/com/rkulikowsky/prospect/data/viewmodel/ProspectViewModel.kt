package com.rkulikowsky.prospect.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rkulikowsky.prospect.data.entity.ClienteRoom
import com.rkulikowsky.prospect.data.entity.RelatorioRoom
import com.rkulikowsky.prospect.data.entity.TarefaRoom
import com.rkulikowsky.prospect.data.repository.Repository
import com.rkulikowsky.prospect.data.room.ProspectRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProspectViewModel(application: Application):AndroidViewModel(application) {

    private val repository: Repository
    val relatorios: LiveData<List<RelatorioRoom>>
    val tarefas: LiveData<List<TarefaRoom>>
    val clientes: LiveData<List<ClienteRoom>>

    init {
        val relatorioDao = ProspectRoom(application,viewModelScope).relatorioDao()
        val tarefaDao = ProspectRoom(application,viewModelScope).tarefaDao()
        val clienteDao = ProspectRoom(application,viewModelScope).clienteDao()

        repository = Repository(clienteDao, tarefaDao, relatorioDao)
        relatorios = repository.relatorios
        tarefas = repository.tarefas
        clientes = repository.clientes


    }


/*
    fun insertListClients(clienteRooms: List<ClienteRoom>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertListClientes(clienteRooms)
    }*/

    fun insertOneClient(clienteRoom: ClienteRoom) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertOneClient(clienteRoom)
    }

    fun insertOneTask(tarefaRoom: TarefaRoom) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertOneTask(tarefaRoom)
    }

    fun insertOneRelatorio(relatorioRoom: RelatorioRoom) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertOneRelat(relatorioRoom)
    }

    fun deleteSingleTask(tarefaRoom: TarefaRoom)= viewModelScope.launch(Dispatchers.IO) {
        repository.deleteSingleTask(tarefaRoom)
    }

    fun deleteSingleCliente(clienteRoom: ClienteRoom)= viewModelScope.launch(Dispatchers.IO) {
        repository.deleteSingleCliente(clienteRoom)
    }
}