package com.rkulikowsky.prospect.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rkulikowsky.prospect.R
import com.rkulikowsky.prospect.data.viewmodel.ProspectViewModel
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import com.rkulikowsky.prospect.data.entity.TarefaRoom
import com.rkulikowsky.prospect.ui.adapters.TarefasAdapter
import org.joda.time.DateTime

class CalendarFragment : Fragment() {
    private lateinit var adapter: TarefasAdapter
    private lateinit var prospectViewModel: ProspectViewModel
    private val dateFormatMonth = SimpleDateFormat("MMMM - yyyy", Locale.getDefault())
    var dateSelected = Date()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCalendarProperties()
        adapter = TarefasAdapter(this.context!!)
        lvTasks.adapter = adapter

        prospectViewModel = ViewModelProvider(activity!!).get(ProspectViewModel::class.java)
        prospectViewModel.tarefas.observe(viewLifecycleOwner, Observer {
            loadTaskList(DateTime(dateSelected), it)
            compactCalendar.setListener(object : CompactCalendarView.CompactCalendarViewListener {
                override fun onDayClick(dateClicked: Date) {
                    dateSelected = dateClicked
                    val date1 = DateTime(dateClicked)
                    loadTaskList(date1, it)

                }

                override fun onMonthScroll(firstDayOfNewMonth: Date) {
                    txtcalendarTitle.text = dateFormatMonth.format(firstDayOfNewMonth).capitalize()
                }
            })
            createEvents(it)
        })

        fab.setOnClickListener {

            val action = CalendarFragmentDirections.actionCalendarFragmentToNewTaskFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun setCalendarProperties() {
        txtcalendarTitle.text = dateFormatMonth.format(Date()).capitalize()
        compactCalendar.setLocale(TimeZone.getDefault(), Locale.getDefault())
        compactCalendar.shouldDrawIndicatorsBelowSelectedDays(true)
        compactCalendar.setUseThreeLetterAbbreviation(true)
        compactCalendar.setCurrentDate(dateSelected)

    }

    private fun createEvents(tarefaRooms: List<TarefaRoom>) {
        compactCalendar.removeAllEvents()
        val listEvents = ArrayList<Event>()
        for (item in tarefaRooms) {
            listEvents.add(Event(R.color.colorAccent, DateTime(item.data).millis))
        }
        compactCalendar.addEvents(listEvents)
    }

    private fun loadTaskList(dateTime: DateTime, tarefaRooms: List<TarefaRoom>) {
        adapter.setTarefas(tarefaRooms.filter { tarefa ->
            DateTime(tarefa.data).dayOfMonth == dateTime.dayOfMonth
                    && DateTime(tarefa.data).monthOfYear == dateTime.monthOfYear
                    && DateTime(tarefa.data).year == dateTime.year
        })
    }
}
