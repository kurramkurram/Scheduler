package com.example.scheduler

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.scheduler.model.ScheduleData
import com.example.scheduler.model.ScheduleTable

class TabFragment : Fragment() {

    private var listener: OnListItemClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val category = arguments!!.getString(TAB_CATEGORY)
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)
        val listView = view.findViewById<ListView>(R.id.list_view)

        val itemList: List<ScheduleData> = ScheduleTable.doSelectAll(context!!, category!!)

        listView.setOnItemClickListener { parent, _, position, _ ->
            val data: ScheduleData = parent.getItemAtPosition(position) as ScheduleData
            onListTapped(data)
        }
        listView.adapter = ScheduleAdapter(context!!, itemList)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListItemClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    internal interface OnListItemClickListener {
        fun onListItemClicked(data: ScheduleData)
    }

    private fun onListTapped(data: ScheduleData) {
        listener?.onListItemClicked(data)
    }

    companion object {
        private const val TAB_CATEGORY = "tab_category"

        fun newInstance(category: String): TabFragment {
            val pageFragment = TabFragment()
            val bundle = Bundle()
            bundle.putString(TAB_CATEGORY, category)
            pageFragment.arguments = bundle
            return pageFragment
        }
    }
}