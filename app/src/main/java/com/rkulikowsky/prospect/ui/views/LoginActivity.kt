package com.rkulikowsky.prospect.ui.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.rkulikowsky.prospect.R
import com.rkulikowsky.prospect.util.Defaults
import com.rkulikowsky.prospect.util.IsOnline
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Backendless.initApp(applicationContext, Defaults.APPLICATION_ID, Defaults.API_KEY)
        setPointer()
    }

    private fun setPointer() {
        val sharedPreferences = getSharedPreferences("prospect", Context.MODE_PRIVATE)
        // se o app já rodou antes
        if (sharedPreferences.contains("user")) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        } else {
            etNome.visibility = View.VISIBLE
            btnRegistrar.visibility = View.VISIBLE

            btnRegistrar.setOnClickListener {
                if (!IsOnline(applicationContext)){
                    Toast.makeText(this@LoginActivity, "Conecte-se à internet", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                pbWait.visibility = View.VISIBLE
                val user = etNome.text.toString().trim().toUpperCase(Locale.getDefault())
                val backendlessUser = BackendlessUser()

                backendlessUser.apply {
                    setProperty("name", user)
                    password = Defaults.PASSWORD

                }

                Backendless.UserService.register(backendlessUser, object : AsyncCallback<BackendlessUser> {
                    override fun handleFault(fault: BackendlessFault?) {

                        //se o usuário já está cadastrado no Backendless, mas é a primeira vez que o app roda
                        if (fault?.code.equals("3033")) {

                            Backendless.UserService.login(user, Defaults.PASSWORD,
                                object : AsyncCallback<BackendlessUser> {
                                    override fun handleFault(fault: BackendlessFault?) {
                                        Toast.makeText(this@LoginActivity, fault?.message, Toast.LENGTH_LONG).show()
                                    }

                                    override fun handleResponse(response: BackendlessUser?) {
                                        sharedPreferences.edit().putString("user", response?.getProperty("name") as String).apply()
                                        pbWait.visibility = View.GONE
                                        startActivity(Intent(this@LoginActivity,
                                            MainActivity::class.java))
                                        finish()

                                    }
                                },true)
                        }
                    }

                    override fun handleResponse(response: BackendlessUser?) {
                        sharedPreferences.edit().putString("user", response?.getProperty("name") as String).apply()
                        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                        finish()
                    }

                })

            }


        }
    }

}
