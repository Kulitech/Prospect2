package com.rkulikowsky.prospect.ui.fragments


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rkulikowsky.prospect.R
import com.rkulikowsky.prospect.data.entity.RelatorioRoom
import com.rkulikowsky.prospect.data.entity.TarefaRoom
import com.rkulikowsky.prospect.data.viewmodel.ProspectViewModel
import com.rkulikowsky.prospect.ui.adapters.RelatoriosAdapter
import com.rkulikowsky.prospect.util.AddressStringFormat
import com.rkulikowsky.prospect.util.DateStringFormat
import com.rkulikowsky.prospect.util.TipoCharFormat
import kotlinx.android.synthetic.main.fragment_tarefa.*
import java.net.URLEncoder


class TarefaFragment : Fragment() {
    private val task by lazy { TarefaFragmentArgs.fromBundle(arguments!!).task }
    private var relatoriosList = emptyList<RelatorioRoom>()
    private val relatoriosAdapter by lazy { RelatoriosAdapter(this.context!!) }
    private val sharedViewModel by lazy { ViewModelProvider(activity!!).get(ProspectViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tarefa, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lvOneTask.adapter = relatoriosAdapter

        sharedViewModel.clientes.observe(viewLifecycleOwner, Observer {

            val cliente = it.find { cliente ->
                cliente.nome.equals(task.cliente)
            }
            txtEmailTarefa.text = cliente?.email ?:""
            txtTel1Tarefa.text = cliente?.telefone1 ?: ""
            txtTel2Tarefa.text = cliente?.telefone2 ?: ""
            txtTel3Tarefa.text = cliente?.telefone3 ?: ""
            txtBorderoTarefa.text = DateStringFormat(task.ultimoBordero)
            txtEnderecoTarefa.text = AddressStringFormat(cliente)

            txtEnderecoTarefa.setOnClickListener {
                if (cliente?.endereco == null || cliente.cidade == null) return@setOnClickListener
                val urlString = "${cliente.endereco}, ${cliente.cidade}"
                val query = URLEncoder.encode(urlString, "utf-8")
                try {
                    // Launch Waze to look for Hawaii:
                    val url = "https://waze.com/ul?q=$query"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    this.startActivity(intent)
                } catch (ex: ActivityNotFoundException) {
                    // If Waze is not installed, open it in Google Play:
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"))
                    this.startActivity(intent)
                }
            }
        })

        sharedViewModel.relatorios.observe(viewLifecycleOwner, Observer {
            relatoriosList = it

            val myList = relatoriosList.filter { relatorio ->
                relatorio.cliente.equals(task.cliente)
            }
            relatoriosAdapter.setRelatorios(myList)

        })

        fillTask(task)
    }


    private fun fillTask(task: TarefaRoom) {
        txtTaskTipo.text = TipoCharFormat(task.tipo)
        txtTaskCliente.text = task.cliente
        txtTaskData.text = DateStringFormat(task.data)
        val obs = "OBS: ${task.obs}"
        txtObsTarefa.text = obs
        if (task.autorizado == false) txtConfirmadoTarefa.visibility = View.GONE
        if (task.visita == false) txtAgendadoTarefa.visibility = View.GONE



    }
}
