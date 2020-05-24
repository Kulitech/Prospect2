package com.rkulikowsky.prospect.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rkulikowsky.prospect.data.entity.RelatorioRoom

@Dao
interface RelatorioDao {
    @Query("SELECT * from relatorios ORDER BY created ASC")
    fun getAllRelatorios(): LiveData<List<RelatorioRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListRelatorios(relatorioRooms: List<RelatorioRoom>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneRelatorio(relatorioRoom: RelatorioRoom)

    @Query("DELETE FROM relatorios")
    fun deleteAll()
}