package com.example.scheduler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.scheduler.model.ScheduleData

class ScheduleAdapter(
    context: Context,
    private val itemList: List<ScheduleData>
) : BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return itemList.count()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view = convertView
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false)
        }

        val date = view!!.findViewById<TextView>(R.id.list_date)
        val title = view.findViewById<TextView>(R.id.list_title)

        val item = itemList[position]
        date.text = item.date
        title.text = item.title

        return view
    }

}