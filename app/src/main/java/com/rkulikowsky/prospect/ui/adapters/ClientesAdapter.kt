package com.rkulikowsky.prospect.ui.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.rkulikowsky.prospect.R
import com.rkulikowsky.prospect.data.entity.ClienteRoom
import com.rkulikowsky.prospect.data.viewmodel.ProspectViewModel
import com.rkulikowsky.prospect.util.IsOnline

class ClientesAdapter(private val context: Context, activity: FragmentActivity) : BaseAdapter() {
    private var clientesList = emptyList<ClienteRoom>()
    private val viewModel = ViewModelProvider(activity).get(ProspectViewModel::class.java)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: View.inflate(context, R.layout.cliente_item_extended, null)
        val txtNome = view.findViewById<TextView>(R.id.txtClientNome)
        val ibDelete = view.findViewById<ImageButton>(R.id.ibRemove)
        //val ibEdit = view.findViewById<ImageButton>(R.id.ibEdit)
        val llHide = view.findViewById<LinearLayout>(R.id.llHide)
        val llPop = view.findViewById<LinearLayout>(R.id.llPop)
        val txtRamo = view.findViewById<TextView>(R.id.txtClientRamo)
        val txtEmail = view.findViewById<TextView>(R.id.txtClientEmail)
        val txtTel1 = view.findViewById<TextView>(R.id.txtClientTel1)
        val txtTel2 = view.findViewById<TextView>(R.id.txtClientTel2)
        val txtTel3 = view.findViewById<TextView>(R.id.txtClientTel3)
        val txtEndereco = view.findViewById<TextView>(R.id.txtClientEndereco)
        val txtBairro = view.findViewById<TextView>(R.id.txtClientBairro)
        val txtCidade = view.findViewById<TextView>(R.id.txtClientCidade)
        val txtIndicacao = view.findViewById<TextView>(R.id.txtClientIndicacao)
        val txtCargo = view.findViewById<TextView>(R.id.txtClientCargo)
        val cbDinheiro = view.findViewById<CheckBox>(R.id.cbClientDinheiro)
        val cbCheque = view.findViewById<CheckBox>(R.id.cbClientCheque)
        val cbDuplicata = view.findViewById<CheckBox>(R.id.cbClientDuplicata)
        val cbCartao = view.findViewById<CheckBox>(R.id.cbClientCartao)
        val rgPorte = view.findViewById<RadioGroup>(R.id.myRadioGroup)


        txtNome.text = clientesList[position].nome
        txtRamo.text = clientesList[position].ramo
        txtEmail.text = clientesList[position].email
        txtTel1.text = clientesList[position].telefone1
        txtTel2.text = clientesList[position].telefone2
        txtTel3.text = clientesList[position].telefone3
        txtEndereco.text = clientesList[position].endereco
        txtBairro.text = clientesList[position].bairro
        txtCidade.text = clientesList[position].cidade
        txtIndicacao.text = clientesList[position].indicacao
        txtCargo.text = clientesList[position].indicacaoCargo
        cbDinheiro.isChecked = clientesList[position].dinheiro == true
        cbDuplicata.isChecked = clientesList[position].duplicata == true
        cbCartao.isChecked = clientesList[position].cartao == true
        cbCheque.isChecked = clientesList[position].cheque == true
        val porte = when (clientesList[position].porte) {
            1 -> R.id.rbClientPeq
            2 -> R.id.rbClientMed
            3 -> R.id.rbClientGrande
            else -> 0
        }
        rgPorte.check(porte)

        llPop.setOnClickListener {
            llHide.visibility = if (llHide.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        ibDelete.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Aviso!")
                .setMessage("Excluir cliente?")
                .setIcon(R.drawable.ic_warning)
                .setPositiveButton("Sim") { dialog, _ ->
                    if (!IsOnline(context.applicationContext)) {
                        Toast.makeText(context, "Conecte-se à internet", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    viewModel.deleteSingleCliente(clientesList[position])
                    dialog.dismiss()

                }.setNegativeButton("Não") { dialog, _ -> dialog.dismiss() }.show()
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return clientesList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return clientesList.size
    }

    internal fun setClientes(clientesList: List<ClienteRoom>) {
        this.clientesList = clientesList
        notifyDataSetChanged()
    }


}