package com.rkulikowsky.prospect.ui.fragments


import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.backendless.Backendless
import com.backendless.exceptions.BackendlessException
import com.rkulikowsky.prospect.R
import com.rkulikowsky.prospect.data.entity.Relatorio
import com.rkulikowsky.prospect.data.entity.Tarefa
import com.rkulikowsky.prospect.data.viewmodel.ProspectViewModel
import com.rkulikowsky.prospect.util.BackToRoom
import com.rkulikowsky.prospect.util.DateStringFormat
import com.rkulikowsky.prospect.util.IsOnline
import com.rkulikowsky.prospect.util.TipoCharFormat
import kotlinx.android.synthetic.main.fragment_make_relatorio.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import java.util.*


class MakeRelatorioFragment : Fragment() {
    private val task by lazy { TarefaFragmentArgs.fromBundle(arguments!!).task }
    private val sharedViewModel by lazy { ViewModelProvider(activity!!).get(ProspectViewModel::class.java)}
    private var proximaVisita:Date?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {return inflater.inflate(R.layout.fragment_make_relatorio, container, false)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtRelatorioCliente.text = task.cliente
        txtRelatorioTipo.text = TipoCharFormat(task.tipo)

        txtRelatorioReagendar.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dp = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, ye, mon, dayOfMonth ->

                txtRelatorioReagendar.text = DateStringFormat(DateTime(ye, mon + 1, dayOfMonth, 0, 0).toDate())
                proximaVisita = DateTime(ye, mon + 1, dayOfMonth, 0, 0).toDate()
            }, year, month, day)
            dp.datePicker.minDate = c.timeInMillis
            dp.show()
        }

        btnAddRelatorio.setOnClickListener {
            if (!IsOnline(context!!.applicationContext)) {
                Toast.makeText(context, "Conecte-se à internet", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            it.isClickable = false
            val relatorio = Relatorio(etRelatorioContato?.text.toString().toUpperCase(Locale.getDefault()), cbRelatRestricao.isChecked, null,
                cbRelatSemInteresse.isChecked, cbRelatPresencial.isChecked, etRelatorioObs.text.toString().toUpperCase(Locale.getDefault()), null,
                cbRelatNaoAtende.isChecked, task.tipo, task.consultor, etRelatorioCargo.text.toString().toUpperCase(Locale.getDefault()), cbRelatCadastro.isChecked,
                null, proximaVisita, cbRelatReagendar.isChecked, task.cliente
            )

            GlobalScope.launch(Dispatchers.IO) {
                try {


                    val obj = Backendless.Data.of(Relatorio::class.java).save(relatorio)
                    Log.e("BACKBACK", "Enviou relatório")
                    sharedViewModel.insertOneRelatorio(BackToRoom.singleRelatorio(obj))
                    Log.e("BACKBACK", "Colocou no Room")
                    if (proximaVisita != null) {
                        val newTarefa = Tarefa(null,task.autorizado,task.cliente,task.ultimoBordero,"REAGENDAMENTO",task.consultor,
                            null,task.tipo,task.visita,null,proximaVisita)
                        val taskTemp = Backendless.Data.of(Tarefa::class.java).save(newTarefa)
                        sharedViewModel.insertOneTask(BackToRoom.singleTarefa(taskTemp))
                    }
                    Backendless.Data.of(Tarefa::class.java).remove(BackToRoom.singleTarefaReverse(task))
                    Log.e("BACKBACK", "apagou tarefa")
                    sharedViewModel.deleteSingleTask(task)
                    Log.e("BACKBACK", "Apagou o Room")
                }catch (error: BackendlessException){
                    Toast.makeText(context,"Erro: ${error.message}/${error.detail}",Toast.LENGTH_LONG).show()
                }finally {
                    NavHostFragment.findNavController(this@MakeRelatorioFragment).popBackStack()
                }

            }



            }
        }
    }

