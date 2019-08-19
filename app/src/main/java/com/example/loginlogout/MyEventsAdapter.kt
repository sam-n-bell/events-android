package com.example.loginlogout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import java.util.ArrayList

/**
 * Orignial author: Parsania Hardik on 03-Jan-17.
 * Modified by Ramesh Yerraballi on 8/12/19
 */
class MyEventsAdapter(private val context: Context, private val myEventsModelArrayList: ArrayList<My_Event_Model>) :
    BaseAdapter() {

    override fun getViewTypeCount(): Int {
        return count
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

    override fun getCount(): Int {
        return myEventsModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return myEventsModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.myevent, null, true)

            holder.name = convertView!!.findViewById(R.id.name) as TextView
            holder.venue_name = convertView.findViewById(R.id.venue_name) as TextView
            holder.event_day = convertView.findViewById(R.id.event_day) as TextView
            holder.start_time = convertView.findViewById(R.id.start_time) as TextView


            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }
        //shows event information in list for each event
        holder.name!!.text = "Name: " + myEventsModelArrayList[position].getNames()
        holder.venue_name!!.text = "Venue Name: " + myEventsModelArrayList[position].getVenueNames()
        holder.event_day!!.text = "Date: " + myEventsModelArrayList[position].getEventDays()
        holder.start_time!!.text = "Time: " + myEventsModelArrayList[position].getStartTimes()

        return convertView
    }

    private inner class ViewHolder {

        var name: TextView? = null
        var venue_name: TextView? = null
        var event_day: TextView? = null
        var start_time: TextView? = null
    }

}