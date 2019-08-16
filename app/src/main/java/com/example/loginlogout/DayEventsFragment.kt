package com.example.loginlogout

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import kotlinx.android.synthetic.main.day_events_listing_fragment.*
import kotlinx.android.synthetic.main.day_events_listing_fragment.view.*
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*


class DayEventsFragment  : Fragment() {

    private var eventlist: ListView? = null
    private var eventsModelArrayList: ArrayList<Event_Model>? = null
    private var eventsAdapter: EventsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.day_events_listing_fragment, container, false)
        // Set an error if the password is less than 8 characters.
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        view.date_display.setText(sdf.format(c.time))

        eventlist = view.eventlist

        doAsync {
            try {
                eventsModelArrayList = getEvents(view.date_display.text!!.toString())
                // Create a Custom Adapter that gives us a way to "view" each user in the ArrayList
                eventsAdapter = EventsAdapter(view.context, eventsModelArrayList!!)
                // set the custom adapter for the userlist viewing
                val handler = Handler(Looper.getMainLooper());
                handler.post({
                    try {
                        eventlist!!.adapter = eventsAdapter
                    } catch (e: Exception){
                        // :D
                    }
                })
            } catch (e: Exception) {
                println("error in doasync" + e.toString())
            } finally {
            }
        }


        view.pick_date_button.setOnClickListener({
            val dpd = DatePickerDialog(view.context, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val cal = Calendar.getInstance()
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, mMonth)
                cal.set(Calendar.DAY_OF_MONTH, mDay)
                date_display.setText(sdf.format(cal.time))
                println("closing")
                doAsync {
                    try {
                        eventsModelArrayList = getEvents(date_display.text!!.toString())
                        // Create a Custom Adapter that gives us a way to "view" each user in the ArrayList
                        eventsAdapter = EventsAdapter(view.context, eventsModelArrayList!!)
                        // set the custom adapter for the userlist viewing
                        val handler = Handler(Looper.getMainLooper());
                        handler.post({
                            try {
                                eventlist!!.adapter = eventsAdapter
                            } catch (e: Exception){
                                println("Problem is the listen doasync" + e.toString())
                            }
                        })
                    } catch (e: Exception) {
                        println("error in doasync" + e.toString())
                    } finally {
                    }
                }
            }, year, month, day)
            dpd.show()
        })

        view.back_button.setOnClickListener({
            (activity as NavigationHost).navigateTo(NavigationFragment(), false) //no back  button functionality
        })

        return view
    }


    private fun getEvents(day: String): ArrayList<Event_Model> {
        val eventModelArrayList = ArrayList<Event_Model>()

        val url = "https://flaskappmysql.appspot.com/events?date=" + day
        println("url is " + url)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer " + Global.getToken())
            .build()
        try {
            val response = client.newCall(request).execute() //GETS URL. If this line freezes, check network & restart virtual device
            val bodystr =  response.body().string() // this can be consumed only once
            val dataArray = JSONArray(bodystr)
            //loops and turns JSON object array into arraylist of User Model
            for (i in 0 until dataArray.length()) {
                val eventModel = Event_Model()
                val dataobj = dataArray.getJSONObject(i)
                eventModel.setCreatedBys(dataobj.getInt("created_by"))
                eventModel.setEventDays(dataobj.getString("event_day"))
                eventModel.setEventIds(dataobj.getInt("event_id"))
                eventModel.setNames(dataobj.getString("name"))
                eventModel.setStartTimes(dataobj.getString("start_time"))
                eventModel.setVenueNames(dataobj.getString("venue_name"))
                eventModelArrayList.add(eventModel)
            }
            return eventModelArrayList
        } catch (e: Exception){
            println("Failed"+e.toString())
            return eventModelArrayList
        }    }

}