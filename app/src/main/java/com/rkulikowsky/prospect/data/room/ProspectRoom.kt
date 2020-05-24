package com.rkulikowsky.prospect.data.room
    import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.backendless.Backendless
import com.backendless.exceptions.BackendlessException
import com.backendless.persistence.DataQueryBuilder
import com.rkulikowsky.prospect.data.dao.ClienteDao
import com.rkulikowsky.prospect.data.dao.RelatorioDao
import com.rkulikowsky.prospect.data.dao.TarefaDao
import com.rkulikowsky.prospect.data.entity.*
import com.rkulikowsky.prospect.util.BackToRoom
import com.rkulikowsky.prospect.util.IsOnline
import com.rkulikowsky.prospect.util.User
import kotlinx.coroutines.*
import org.joda.time.DateTime


@TypeConverters(Converters::class)
@Database(
    entities = [ClienteRoom::class, TarefaRoom::class, RelatorioRoom::class],
    version = 4,
    exportSchema = false
)
abstract class ProspectRoom : RoomDatabase() {

    abstract fun clienteDao(): ClienteDao
    abstract fun tarefaDao(): TarefaDao
    abstract fun relatorioDao(): RelatorioDao

    companion object {
        @Volatile
        private var INSTANCE: ProspectRoom? = null

        operator fun invoke(context: Context, scope: CoroutineScope): ProspectRoom {
            val tempInstance = this.INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,ProspectRoom::class.java,"prospect_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(PeopleDatabaseCallback(context, scope)).build()
                this.INSTANCE = instance
                return instance
            }
        }
    }

    private class PeopleDatabaseCallback(
        private val context: Context,
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(
                        database.relatorioDao(),
                        database.clienteDao(),
                        database.tarefaDao()
                    )
                }
            }
        }

        suspend fun populateDatabase(relatorioDao: RelatorioDao,clienteDao: ClienteDao,tarefaDao: TarefaDao) {
            val sharedPreferences = context.applicationContext.getSharedPreferences("prospect", Context.MODE_PRIVATE)
            val user = User(context)
            val whereClause = "consultor = '$user'"

            //se é necessário baixar tudo:
            if (!sharedPreferences.contains("lastUpdate")) {
                Log.e("BACKENDLESS", "ENTENDEU QUE DEVE FAZER DOWNLOAD DE TUDO")

                val clientsQueryBuilder = DataQueryBuilder.create().setWhereClause(whereClause).setPageSize(100)
                val tasksQueryBuilder = DataQueryBuilder.create().setWhereClause(whereClause).setPageSize(100)
                val reportsQueryBuilder = DataQueryBuilder.create().setWhereClause(whereClause).setPageSize(100)

                val clientsCount = Backendless.Data.of(Cliente::class.java).getObjectCount(clientsQueryBuilder)
                val tasksCount = Backendless.Data.of(Tarefa::class.java).getObjectCount(tasksQueryBuilder)
                val reportCount = Backendless.Data.of(Relatorio::class.java).getObjectCount(reportsQueryBuilder)

                Log.e("BACKENDLESS", "clientes: $clientsCount tasks: $tasksCount report: $reportCount")

                try {
                    for (i in clientsCount downTo 0 step 100) {
                        val list = Backendless.Data.of(Cliente::class.java).find(clientsQueryBuilder)
                        clienteDao.insertListClients(BackToRoom.clientesList(list))
                        if (i > 100) clientsQueryBuilder.prepareNextPage()
                    }

                } catch (ex: BackendlessException) {
                    Log.e("BACKENDLESS", "${ex.code}, ${ex.detail}")
                }

                try {
                    for (i in tasksCount downTo 0 step 100) {
                        val list = Backendless.Data.of(Tarefa::class.java).find(tasksQueryBuilder)
                        tarefaDao.insertListTarefas(BackToRoom.tarefasList(list))
                        if (i > 100) tasksQueryBuilder.prepareNextPage()
                    }

                } catch (ex: BackendlessException) {
                    Log.e("BACKENDLESS", "${ex.code}, ${ex.detail}")
                }

                try {
                    for (i in reportCount downTo 0 step 100) {
                        val list = Backendless.Data.of(Relatorio::class.java).find(reportsQueryBuilder)
                        relatorioDao.insertListRelatorios(BackToRoom.relatoriosList(list))
                        if (i > 100) reportsQueryBuilder.prepareNextPage()
                    }

                } catch (ex: BackendlessException) {
                    Log.e("BACKENDLESS", "${ex.code}, ${ex.detail}")
                }


                //se o usuário já baixou as tabelas
            } else {
                val lastUpdated = sharedPreferences.getLong("lastUpdate",0)
                if (!IsOnline(context.applicationContext)) return
                val queryBuilder = DataQueryBuilder.create()
                queryBuilder.setPageSize(100).whereClause = "created > $lastUpdated and consultor = '$user'"
                try {

                    val clientes =
                        scope.async(Dispatchers.IO) {
                            Backendless.Data.of(Cliente::class.java).find(queryBuilder)
                        }
                    val tarefas =
                        scope.async(Dispatchers.IO) {
                            Backendless.Data.of(Tarefa::class.java).find(queryBuilder)
                        }



                    Log.e(
                        "BACKENDLESS","TAREFAS = ${tarefas.await().size} - CLIENTES = ${clientes.await().size}"
                    )


                    clienteDao.insertListClients(BackToRoom.clientesList(clientes.await()))
                    tarefaDao.insertListTarefas(BackToRoom.tarefasList(tarefas.await()))


                } catch (backendlessException: BackendlessException) {
                    Log.e(
                        "BACKENDLESS",
                        "${backendlessException.message}, ${backendlessException.detail}, ${backendlessException.code}"
                    )
                }
            }
            sharedPreferences.edit().putLong("lastUpdate", DateTime.now().millis).apply()
            Log.e("BACKENDLESS", "MUDOU SHARED PREFERENCES")

        }


    }

}


