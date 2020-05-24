package com.rkulikowsky.prospect.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.rkulikowsky.prospect.R
import com.rkulikowsky.prospect.data.entity.Cliente
import com.rkulikowsky.prospect.data.viewmodel.ProspectViewModel
import com.rkulikowsky.prospect.util.BackToRoom
import com.rkulikowsky.prospect.util.IsOnline
import com.rkulikowsky.prospect.util.User
import kotlinx.android.synthetic.main.fragment_add_client.*
import java.util.*


class AddClientFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(activity!!).get(ProspectViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_client, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnClientSave.setOnClickListener {
            if (!IsOnline(context!!.applicationContext)){
                Toast.makeText(context!!,"Conecte-se à internet",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(etClientNome.text.isEmpty()){
                Toast.makeText(context!!,"O campo 'nome' é  obrigatório",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val porteInt = when(myRadioGroup.checkedRadioButtonId){
                R.id.rbClientPeq -> 1
                R.id.rbClientMed -> 2
                R.id.rbClientGrande -> 3
                else -> null
            }

            val cliente = Cliente(etClientEmail.text.toString(),etClientTel1.text.toString(),null,porteInt, User(context!!),null,etClientTel3.text.toString(),
                cbClientDuplicata.isChecked,cbClientDinheiro.isChecked,cbClientCartao.isChecked,etClientIndicacao.text.toString().toUpperCase(
                    Locale.getDefault()),
                etClientEndereco.text.toString().toUpperCase(Locale.getDefault()),etClientCidade.text.toString().toUpperCase(Locale.getDefault()),cbClientCheque.isChecked,
                etClientBairro.text.toString().toUpperCase(Locale.getDefault()), etClientCargo.text.toString().toUpperCase(Locale.getDefault()).trim(),null,
                etClientRamo.text.toString().toUpperCase(Locale.getDefault()),etClientTel2.text.toString(),etClientNome.text.toString().toUpperCase(Locale.getDefault()).trim()
            )

            Backendless.Data.of(Cliente::class.java).save(cliente, object : AsyncCallback<Cliente> {
                override fun handleFault(fault: BackendlessFault?) {
                    Toast.makeText(context!!,"Erro: Cliente não cadastrado, ${fault?.message}",Toast.LENGTH_SHORT).show()

                }

                override fun handleResponse(response: Cliente?) {

                    viewModel.insertOneClient(BackToRoom.singleCliente(response!!))
                    Toast.makeText(context!!,"Cliente cadastrado",Toast.LENGTH_SHORT).show()
                    NavHostFragment.findNavController(this@AddClientFragment).navigateUp()
                }
            })
        }
    }
}
