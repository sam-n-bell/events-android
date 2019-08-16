package com.example.loginlogout

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.create_event_fragment.*
import kotlinx.android.synthetic.main.create_event_fragment.view.*


class CreateEventFragment  : Fragment() {

//    private var venues: ArrayList<Venue_Model>? = null
    var spinner: Spinner? = null
    var venue_id: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.create_event_fragment, container, false)
        lateinit var venues: ArrayList<Venue_Model>

        doAsync {
            try {
                val venueModelArrayList = getVenues()
                venues = venueModelArrayList

                val arrayAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, venueModelArrayList)

                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                val handler = Handler(Looper.getMainLooper())
                handler.post({
                    try {
                        venue_spinner.setAdapter(arrayAdapter)
                        venue_spinner!!.adapter = arrayAdapter
                    } catch (e: Exception){
                        println("problem in createvent " + e.toString())
                    }
                })
            } catch (e: Exception) {
                println("error in doasync" + e.toString())
            } finally {
            }
        }

        view.venue_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                venue_id = venues[pos].getVenueIds()
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {

            }

        }



//        view.pick_date_button.setOnClickListener({
//            val dpd = DatePickerDialog(view.context, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
//                val cal = Calendar.getInstance()
//                cal.set(Calendar.YEAR, year)
//                cal.set(Calendar.MONTH, mMonth)
//                cal.set(Calendar.DAY_OF_MONTH, mDay)
//                date_display.setText(sdf.format(cal.time))
//                println("closing")
//                doAsync {
//                    try {
//                        eventsModelArrayList = getEvents(date_display.text!!.toString())
//                        // Create a Custom Adapter that gives us a way to "view" each user in the ArrayList
//                        eventsAdapter = EventsAdapter(view.context, eventsModelArrayList!!)
//                        // set the custom adapter for the userlist viewing
//                        val handler = Handler(Looper.getMainLooper());
//                        handler.post({
//                            try {
//                                eventlist!!.adapter = eventsAdapter
//                            } catch (e: Exception){
//                            }
//                        })
//                    } catch (e: Exception) {
//                        println("error in doasync" + e.toString())
//                    } finally {
//                    }
//                }
//            }, year, month, day)
//            dpd.show()
//        })

//        view.back_button.setOnClickListener({
//            (activity as NavigationHost).navigateTo(NavigationFragment(), false) //no back  button functionality
//        })

        return view
    }



    private fun getVenues(): ArrayList<Venue_Model> {
        val venueModelArrayList = ArrayList<Venue_Model>()

        val url = "https://flaskappmysql.appspot.com/venues"
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
                val venueModel = Venue_Model()
                val dataobj = dataArray.getJSONObject(i)
                venueModel.setVenueIds(dataobj.getInt("venue_id"))
                venueModel.setVenueNames(dataobj.getString("name"))
                venueModelArrayList.add(venueModel)
            }
            return venueModelArrayList
        } catch (e: Exception){
            println("Failed"+e.toString())
            return venueModelArrayList
        }    }



}