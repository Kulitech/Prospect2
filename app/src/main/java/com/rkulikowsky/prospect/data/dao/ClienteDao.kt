package com.rkulikowsky.prospect.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rkulikowsky.prospect.data.entity.ClienteRoom

@Dao
interface ClienteDao {

        @Query("SELECT * from clientes ORDER BY nome ASC")
        fun getAllClients(): LiveData<List<ClienteRoom>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertListClients (listClienteRoom: List<ClienteRoom>)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertOneClient(clienteRoom: ClienteRoom)

        @Query("DELETE FROM clientes")
        fun deleteAll()

        @Delete
        suspend fun deleteSingleCliente(clienteRoom: ClienteRoom)
    }
