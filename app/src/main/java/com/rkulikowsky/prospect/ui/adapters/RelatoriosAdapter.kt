package com.rkulikowsky.prospect.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.rkulikowsky.prospect.R
import com.rkulikowsky.prospect.data.entity.RelatorioRoom
import com.rkulikowsky.prospect.util.DateStringFormat
import com.rkulikowsky.prospect.util.TipoCharFormat

class RelatoriosAdapter(private val context: Context): BaseAdapter() {
    private var relatorioList = emptyList<RelatorioRoom>()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: View.inflate(context, R.layout.relatorio_item_extended, null)
        val llPop = view.findViewById<LinearLayout>(R.id.llPop)
        val llVisible = view.findViewById<LinearLayout>(R.id.llVisible)
        val txtTipo = view.findViewById<TextView>(R.id.txtRelatorioTipo)
        val txtObs = view.findViewById<TextView>(R.id.txtRelatObs)
        val txtContato = view.findViewById<TextView>(R.id.txtRelatContato)
        val txtCargo = view.findViewById<TextView>(R.id.txtRelatCargo)
        val txtData = view.findViewById<TextView>(R.id.txtRelatCreated)
        val txtNaoAtende = view.findViewById<TextView>(R.id.txtRelatNaoAtende)
        val txtCadastro = view.findViewById<TextView>(R.id.txtRelatCadastro)
        val txtPresencial = view.findViewById<TextView>(R.id.txtRelatPresencial)
        val txtRelatReagendar = view.findViewById<TextView>(R.id.txtRelatReagendar)
        val txtRelatRestricao = view.findViewById<TextView>(R.id.txtRelatRestricao)
        val txtSemInteresse = view.findViewById<TextView>(R.id.txtRelatSemInteresse)
        val txtVisita = view.findViewById<TextView>(R.id.txtRelatVisita)

        txtTipo.text = TipoCharFormat(relatorioList[position].tipo)
        txtObs.text = relatorioList[position].obs
        txtContato.text=relatorioList[position].contato
        txtCargo.text = relatorioList[position].cargoContato
        txtData.text = DateStringFormat(relatorioList[position].created)

        if (relatorioList[position].naoAtende==true) txtNaoAtende.visibility=View.VISIBLE
        if (relatorioList[position].cadastro==true) txtCadastro.visibility=View.VISIBLE
        if (relatorioList[position].presencial==true) txtPresencial.visibility=View.VISIBLE
        if (relatorioList[position].reagendar==true) txtRelatReagendar.visibility=View.VISIBLE
        if (relatorioList[position].restricao==true) txtRelatRestricao.visibility=View.VISIBLE
        if (relatorioList[position].semInteresse==true) txtSemInteresse.visibility=View.VISIBLE
        txtVisita.text = DateStringFormat(relatorioList[position].proximaVisita)

        llVisible.setOnClickListener {
            llPop.let {
                it.visibility = if(it.visibility==View.GONE) View.VISIBLE else View.GONE
            }

        }
        return view
    }



    override fun getItem(position: Int): Any {
        return relatorioList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return relatorioList.size
    }

    internal fun setRelatorios(relatorioRoomList: List<RelatorioRoom>) {
        this.relatorioList = relatorioRoomList
        notifyDataSetChanged()
    }



}