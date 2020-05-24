package com.rkulikowsky.prospect.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.rkulikowsky.prospect.R
import com.rkulikowsky.prospect.util.Defaults
import com.rkulikowsky.prospect.util.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    /*
    private val viewModel by lazy { ViewModelProvider(this).get(ProspectViewModel::class.java) }
    private val clientesRT by lazy { Backendless.Data.of(Cliente::class.java).rt() }
    private val tarefasRT by lazy { Backendless.Data.of(Tarefa::class.java).rt()}

     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation()
        isValidLogin()
    }

    private fun isValidLogin() {
        Backendless.UserService.isValidLogin(object : AsyncCallback<Boolean> {
            override fun handleFault(fault: BackendlessFault?) {

            }

            override fun handleResponse(response: Boolean) {
                if(!response) backendlessLogin()
            }

        })
    }

    private fun backendlessLogin() {
        Backendless.UserService.login(User(this), Defaults.PASSWORD,
            object : AsyncCallback<BackendlessUser> {
                override fun handleFault(fault: BackendlessFault?) {
                    Log.e("Kulikowsky","${fault?.code}, ${fault?.message}")
                }

                override fun handleResponse(response: BackendlessUser?) {
                    Log.e("Kulikowsky","funcionou")
                    //listeners()
                }
            },true)
    }

    private fun bottomNavigation() {
        val navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        bottom_nav.let {NavigationUI.setupWithNavController(it,navController)}
    }

/*
    private fun listeners() {




        tarefasRT.addCreateListener("consultor = '$user'", object : AsyncCallback<Tarefa> {
            override fun handleFault(fault: BackendlessFault?) {
            }

            override fun handleResponse(response: Tarefa?) {
                CoroutineScope(Dispatchers.IO).launch { viewModel.insertOneTask(BackToRoom.singleTarefa(response!!)) }
                Log.e("BACKENDLESS", "recebeu")


            }
        })


        clientesRT.addCreateListener("consultor = '$user'", object : AsyncCallback<Cliente> {
            override fun handleFault(fault: BackendlessFault?) {}
            override fun handleResponse(response: Cliente?) {
                CoroutineScope(Dispatchers.IO).launch { viewModel.insertOneClient(BackToRoom.singleCliente(response!!)) }
                Log.e("BACKENDLESS", "recebeu")
            }
        })
    }

 */
/*
    override fun onDestroy() {
        clientesRT.removeCreateListeners()
        tarefasRT.removeCreateListeners()
        super.onDestroy()
    }
*/


}
