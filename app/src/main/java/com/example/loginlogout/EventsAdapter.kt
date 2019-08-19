package com.example.loginlogout

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.event.view.*
import org.jetbrains.anko.doAsync
import org.w3c.dom.Text
import java.util.ArrayList

class EventsAdapter(private val context: Context, private val EventsModelArrayList: ArrayList<Event_Model>) :
    BaseAdapter() {

    override fun getViewTypeCount(): Int {
        return count
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

    override fun getCount(): Int {
        return EventsModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return EventsModelArrayList[position]
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
            convertView = inflater.inflate(R.layout.event, null, true)

            holder.name = convertView!!.findViewById(R.id.name) as TextView
            holder.venue_name = convertView.findViewById(R.id.venue_name) as TextView
            holder.event_day = convertView.findViewById(R.id.event_day) as TextView
            holder.start_time = convertView.findViewById(R.id.start_time) as TextView
            holder.event_id = EventsModelArrayList[position].getEventIds()
            holder.created_by = EventsModelArrayList[position].getCreatedBys()
            holder.error_text = convertView.findViewById(R.id.join_event_error_text) as TextView


            convertView.tag = holder

            if (holder.created_by == Global.getUserId()) {
                convertView.join_button.visibility = View.INVISIBLE
            }
            convertView.join_button.setOnClickListener({
                doAsync {
                    var response = joinEvent(holder.event_id)
                    val handler = Handler(Looper.getMainLooper());
                    if (response.contains("ERROR")) {
                        handler.post({
                            holder.error_text!!.text = "Problem joining"
                        })
                    } else if (response.contains("Already in game")) {
                        handler.post({
                            holder.error_text!!.text = "Already in event"
                            convertView.join_button.visibility = View.INVISIBLE
                        })
                    } else if (response.contains("added")) {
                        handler.post({
                            holder.error_text!!.text = "Added to event"
                            convertView.join_button.visibility = View.INVISIBLE
                        })
                    }
                }

            })
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }

        holder.name!!.text = "Name: " + EventsModelArrayList[position].getNames()
        holder.venue_name!!.text = "Venue Name: " + EventsModelArrayList[position].getVenueNames()
        holder.event_day!!.text = "Date: " + EventsModelArrayList[position].getEventDays()
        holder.start_time!!.text = "Time: " + EventsModelArrayList[position].getStartTimes()


        return convertView
    }

    private fun joinEvent(event_id: Int?): String {
        return HttpUtilities.posturl(Global.getFlaskUrl() + "/events/${event_id}/join", """
                        {
                        "user_id": ${Global.getUserId()},
                        "num_guests": 0,
                        "participant_comment": "I'd like to play. Joined from mobile"
                        }
                    """)
    }

    private inner class ViewHolder {

        var name: TextView? = null
        var venue_name: TextView? = null
        var event_day: TextView? = null
        var start_time: TextView? = null
        var event_id: Int? = null
        var created_by: Int? = null
        var error_text: TextView? = null
    }

}