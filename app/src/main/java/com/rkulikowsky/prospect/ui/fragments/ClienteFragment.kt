package com.rkulikowsky.prospect.ui.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rkulikowsky.prospect.R
import com.rkulikowsky.prospect.data.entity.ClienteRoom
import com.rkulikowsky.prospect.data.viewmodel.ProspectViewModel
import com.rkulikowsky.prospect.ui.adapters.ClientesAdapter
import kotlinx.android.synthetic.main.fragment_cliente.*
import java.util.*

class ClienteFragment : Fragment() {
    private val adapter by lazy { ClientesAdapter(this.context!!,activity!!) }
    private val viewModelProviders by lazy { ViewModelProvider(activity!!).get(ProspectViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cliente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lvClients.adapter = adapter
        viewModelProviders.clientes.observe(viewLifecycleOwner,
            Observer<List<ClienteRoom>> { t ->
                var filteredList = if(etSearch.text.isNotEmpty()) t.filter { it.nome.toString().toLowerCase(Locale.getDefault()).contains(etSearch.text.toString().toLowerCase(Locale.getDefault())) } else t
                adapter.setClientes(filteredList)

                etSearch.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        filteredList = t.filter { it.nome.toString().toLowerCase(Locale.getDefault()).contains(etSearch.text.toString().toLowerCase(Locale.getDefault())) }
                        adapter.setClientes(filteredList)

                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }
                })

            })


    }
}
