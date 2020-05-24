package com.rkulikowsky.prospect.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rkulikowsky.prospect.data.entity.TarefaRoom

@Dao
interface TarefaDao {
    @Query("SELECT * from tarefas ORDER BY data ASC")
    fun getAllTarefas(): LiveData<List<TarefaRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListTarefas(listTarefaRooms: List<TarefaRoom>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneTask(tarefaRoom: TarefaRoom)

    @Query("DELETE FROM tarefas")
    fun deleteAll()

    @Delete
    suspend fun deleteSingleTarefa(tarefaRoom: TarefaRoom)
}