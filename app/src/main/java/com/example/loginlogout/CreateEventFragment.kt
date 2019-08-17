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
    var timeslot_value: String? = null
//    lateinit var timeslots: ArrayList<TimeSlot_Model>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.create_event_fragment, container, false)
        lateinit var venues: ArrayList<Venue_Model>
        lateinit var timeslots: ArrayList<TimeSlot_Model>

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        view.event_day_text.setText(sdf.format(c.time))

        //getting venues list and time slots
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
                println("error in seconddoasync" + e.toString())
            }
        }

        view.venue_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                venue_id = venues[pos].getVenueIds()
                println("venueid is " + venue_id)
                doAsync {

                    val timeSlotsArrayList = getVenueAvailability()
                    timeslots = timeSlotsArrayList

                    val arrayAdapter2 = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, timeSlotsArrayList)

                    arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                    val handler2 = Handler(Looper.getMainLooper())
                    handler2.post({
                        try {
                            timeslot_spinner.setAdapter(arrayAdapter2)
                            timeslot_spinner!!.adapter = arrayAdapter2
                        } catch (e: Exception){
                            println("problem in getTimeSlots " + e.toString())
                        }
                    })
                }
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {

            }

        }

        view.timeslot_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                timeslot_value = timeslots[pos].getValues()
                println("timeslot vlaue is " + timeslot_value)
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {

            }

        }

        view.event_day_button.setOnClickListener({
            val dpd = DatePickerDialog(view.context, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val cal = Calendar.getInstance()
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, mMonth)
                cal.set(Calendar.DAY_OF_MONTH, mDay)
                event_day_text.setText(sdf.format(cal.time))
                doAsync {
                    if (venue_id !== null) {
                        doAsync {

                            val timeSlotsArrayList = getVenueAvailability()
                            timeslots = timeSlotsArrayList

                            val arrayAdapter2 = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, timeSlotsArrayList)

                            arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                            val handler2 = Handler(Looper.getMainLooper())
                            handler2.post({
                                try {
                                    timeslot_spinner.setAdapter(arrayAdapter2)
                                    timeslot_spinner!!.adapter = arrayAdapter2
                                } catch (e: Exception){
                                    println("problem in getTimeSlots " + e.toString())
                                }
                            })
                        }
                    }
                }
            }, year, month, day)
            dpd.show()
        })

        return view
    }


    private fun getVenueAvailability(): ArrayList<TimeSlot_Model> {
        val timeslotModelArrayList = ArrayList<TimeSlot_Model>()

        val url = """https://flaskappmysql.appspot.com/${venue_id}/availability?day=${event_day_text.text!!}""".trimIndent()
        println("getting time slots " + url)
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
                val timeslotModel = TimeSlot_Model()
                val timeslotobj = dataArray.getJSONObject(i)
                timeslotModel.setLabels(timeslotobj.getString("label"))
                timeslotModel.setValues(timeslotobj.getString("value"))
                timeslotModelArrayList.add(timeslotModel)
            }
            return timeslotModelArrayList
        } catch (e: Exception){
            println("Failed"+e.toString())
            return timeslotModelArrayList
        }
        return timeslotModelArrayList
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
        }
    }

}