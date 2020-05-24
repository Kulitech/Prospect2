package com.rkulikowsky.prospect.ui.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.Navigation
import com.rkulikowsky.prospect.R
import com.rkulikowsky.prospect.data.entity.TarefaRoom
import com.rkulikowsky.prospect.ui.fragments.CalendarFragmentDirections
import com.rkulikowsky.prospect.util.TipoCharFormat

class TarefasAdapter(private val context: Context): BaseAdapter() {
    private var tarefasList = emptyList<TarefaRoom>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: View.inflate(context, R.layout.tarefa_item, null)
        val textTask = view.findViewById<TextView>(R.id.txtTaskItem)
        val ibRelatorio = view.findViewById<ImageButton>(R.id.ibRelatorio)
        val tipo = view.findViewById<TextView>(R.id.txtTaskTipo)

        tipo.text = TipoCharFormat(tarefasList[position].tipo)
        textTask.text = tarefasList[position].cliente

        if(tarefasList[position].visita==true) textTask.setTextColor(Color.RED) else textTask.setTextColor(Color.WHITE)
        textTask.setOnClickListener {
            val action = CalendarFragmentDirections.nextAction(tarefasList[position])
            Navigation.findNavController(it).navigate(action)

        }

        ibRelatorio.setOnClickListener {
            val action = CalendarFragmentDirections.makeRelatorio(tarefasList[position])
            Navigation.findNavController(it).navigate(action)
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return tarefasList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return tarefasList.size
    }

    internal fun setTarefas(tarefasList: List<TarefaRoom>) {
        this.tarefasList = tarefasList
        notifyDataSetChanged()
    }


}