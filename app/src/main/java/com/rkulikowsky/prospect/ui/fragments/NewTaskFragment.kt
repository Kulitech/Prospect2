package com.rkulikowsky.prospect.ui.fragments


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.rkulikowsky.prospect.R
import com.rkulikowsky.prospect.data.entity.ClienteRoom
import com.rkulikowsky.prospect.data.entity.Tarefa
import com.rkulikowsky.prospect.data.viewmodel.ProspectViewModel
import com.rkulikowsky.prospect.util.*
import kotlinx.android.synthetic.main.fragment_new_task.*
import org.joda.time.DateTime
import java.util.*


class NewTaskFragment : Fragment() {
    private var taskDate = Date()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedViewModel = ViewModelProvider(activity!!).get(ProspectViewModel::class.java)
        txtData.text = DateStringFormat(Date())
        txtData.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dp = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, ye, mon, dayOfMonth ->

                txtData.text = DateStringFormat(DateTime(ye, mon + 1, dayOfMonth, 0, 0).toDate())
                taskDate = DateTime(ye, mon + 1, dayOfMonth, 0, 0).toDate()
            }, year, month, day)
            dp.datePicker.minDate = c.timeInMillis
            dp.show()
        }
        txtTaskCliente.setOnClickListener {
            val searchList = mutableListOf<String>()


                sharedViewModel.clientes.observe(viewLifecycleOwner, Observer<List<ClienteRoom>> { lista ->

                    val names = lista.map { nom -> nom.nome!! }
                    searchList.addAll(names)
                    val pickClientView = View.inflate(context, R.layout.pick_client, null)
                    val etSearch = pickClientView.findViewById<EditText>(R.id.etSearch)
                    val lvClients = pickClientView.findViewById<ListView>(R.id.lvClients)
                    val arrayAdapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, searchList)
                    lvClients.adapter = arrayAdapter
                    lvClients.choiceMode = ListView.CHOICE_MODE_SINGLE
                    val alert = AlertDialog.Builder(context)
                        .setView(pickClientView)
                        .setCancelable(true)

                        .create()

                    alert.show()
                    lvClients.setOnItemClickListener { _, _, position, _ ->
                        txtTaskCliente.text = searchList[position]
                        alert.dismiss()
                    }

                    etSearch.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {
                            searchList.clear()
                            val word = s?.toString()
                            names.forEach { no ->
                                if (no.toLowerCase(Locale.getDefault()).contains(word?.toLowerCase(Locale.getDefault()).toString())) searchList.add(
                                    no
                                )
                            }
                            arrayAdapter.notifyDataSetChanged()
                        }

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    })
                })


            }



        btnAddTask.setOnClickListener {
            if (txtTaskCliente.text.isEmpty()) {
                Toast.makeText(context, "Escolha um cliente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!IsOnline(context!!.applicationContext)) {
                Toast.makeText(context, "Conecte-se à internet", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val tipoTarefa = when (rgTask.checkedRadioButtonId) {
                R.id.rbTaskCobranca -> 3
                R.id.rbTaskInat -> 2
                else -> 1

            }
            val tarefa = Tarefa(
                null, cbConfirmadoTarefa.isChecked, txtTaskCliente.text.toString(), null, etObsTarefa.text.toString(),
                User(context!!.applicationContext), null, tipoTarefa, cbAgendadoTarefa.isChecked, null, taskDate
            )

            Backendless.Data.of(Tarefa::class.java).save(tarefa, object : AsyncCallback<Tarefa> {
                override fun handleFault(fault: BackendlessFault?) {
                    Toast.makeText(context, fault?.message, Toast.LENGTH_LONG).show()
                }

                override fun handleResponse(res: Tarefa?) {


                        sharedViewModel.insertOneTask(BackToRoom.singleTarefa(res!!))
                        Toast.makeText(context, "Tarefa adicionada!", Toast.LENGTH_SHORT).show()
                    NavHostFragment.findNavController(this@NewTaskFragment).popBackStack()

                }

            })


        }


    }

}
