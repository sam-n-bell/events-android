package com.example.loginlogout

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
import kotlinx.android.synthetic.main.my_events_listing_fragment.view.*
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.ArrayList

class MyEventsListingFragment : Fragment() {
    private val jsoncode = 1

    private var response: String? = null
    private var userlist: ListView? = null
    private var eventlist: ListView? = null
    private var userArrayList: ArrayList<String>? = null
    private var userModelArrayList: ArrayList<User_Model>? = null
    private var eventsModelArrayList: ArrayList<My_Event_Model>? = null
    private var customAdapter: CustomAdapter? = null
    private var myEventsAdapter: MyEventsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.my_events_listing_fragment, container, false)

        eventlist = view.eventlist

        //Here we wan't to use a Custom Adapter that is tied to a custom Data Model
        doAsync {
            //getting events i have created or joined
            try {
                eventsModelArrayList = getMyEvents()
                // Create a Custom Adapter that gives us a way to "view" each user in the ArrayList
                myEventsAdapter = MyEventsAdapter(view.context, eventsModelArrayList!!)
                val handler = Handler(Looper.getMainLooper());
                handler.post({
                    //put inside try catch so app doesn't crash if no events are returned
                    try {
                        eventlist!!.adapter = myEventsAdapter
                    } catch (e: Exception){
                        // :D
                    }

                })
            } catch (e: Exception) {
                println("error in doasync" + e.toString())
            } finally {
            }

        }

        view.back_button.setOnClickListener({
            (activity as NavigationHost).navigateTo(NavigationFragment(), false) //no back  button functionality
        })
        return view;
    }

    //calling events in flask to get events based on token
    private fun getMyEvents(): ArrayList<My_Event_Model> {
        val eventModelArrayList = ArrayList<My_Event_Model>()

        val url = "https://flaskappmysql.appspot.com/my-events"
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
                val eventModel = My_Event_Model()
                val dataobj = dataArray.getJSONObject(i)
                var sdf = SimpleDateFormat("H:mm:ss")
                var timeObj = sdf.parse(dataobj.getString("start_time"))
                val sdf_12hr = SimpleDateFormat("K:mm a").format(timeObj)
                eventModel.setCreatedBys(dataobj.getInt("created_by"))
                eventModel.setEventDays(dataobj.getString("event_day"))
                eventModel.setEventIds(dataobj.getInt("event_id"))
                eventModel.setNames(dataobj.getString("name"))
                eventModel.setStartTimes(sdf_12hr.toString())
                eventModel.setVenueNames(dataobj.getString("venue_name"))
                eventModelArrayList.add(eventModel)
            }
            return eventModelArrayList
        } catch (e: Exception){
            println("Failed"+e.toString())
            return ArrayList()
        }
    }


}
