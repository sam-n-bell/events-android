package com.example.loginlogout

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import kotlinx.android.synthetic.main.listing_fragment.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList

class ListingFragment : Fragment() {
    private val jsoncode = 1
    // Uncomment below if response is hardcoded instead of coming from a file asset
/*    private val response = """
    [
     {
      "name": "James",
      "email": "james@ut"
     },
     {
      "name": "Jean",
      "email": "jean@gmail"
     }
     ]
     """ */
    private var response: String? = null
    private var userlist: ListView? = null
    private var userArrayList: ArrayList<String>? = null
    private var userModelArrayList: ArrayList<User_Model>? = null
//    private var eventsModelArrayList: ArrayList<Events_Model>? = null
    private var customAdapter: CustomAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.listing_fragment, container, false)

        userlist = view.userlist //grabbing listing_fragment from view

//        userModelArrayList = getInfo(response)  // uncomment this and comment the next line if response is above
        response = loadJSONFromAssets(); // returns json string with array of json objects


        //Here we wan't to use a Custom Adapter that is tied to a custom Data Model
        // Call getInfo to parse the JSON Array and return as a UserModel ArrayList
        userModelArrayList = getInfo(response!!)
        // Create a Custom Adapter that gives us a way to "view" each user in the ArrayList
        customAdapter = CustomAdapter(view.context, userModelArrayList!!)
        // set the custom adapter for the userlist viewing
        userlist!!.adapter = customAdapter

        return view;
    }


    private fun getMyEvents(): String {

        val url = "https://flaskappmysql.appspot.com/my-events"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZG1pbmlzdHJhdG9yIjoxLCJ1c2VyX2lkIjoxLCJuYW1lIjoic2FtIGJlbGwiLCJlbWFpbCI6InNhbS5iZWxsQHV0ZXhhcy5lZHUifQ.ymdk1CuwjEJQ5Z124cKaq7UYLMeg0rzI7cFPYmLcLZ8")
            .build()
        try {
            val response = client.newCall(request).execute() //GETS URL. If this line freezes, check network & restart virtual device
            val bodystr =  response.body().string() // this can be consumed only once
            return bodystr
        } catch (e: Exception){
            println("Failed"+e.toString())
            return "fail"
        }
    }


    fun getInfo(response: String): ArrayList<User_Model> {
        val userModelArrayList = ArrayList<User_Model>()
        try {
                val dataArray = JSONArray(response) //makes array of JSON objects from a string

                //loops and turns JSON object array into arraylist of User Model
                for (i in 0 until dataArray.length()) {
                    val usersModel = User_Model()
                    val dataobj = dataArray.getJSONObject(i)
                    usersModel.setNames(dataobj.getString("name"))
                    usersModel.setEmails(dataobj.getString("email"))
                    userModelArrayList.add(usersModel)
                }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return userModelArrayList
    }

    fun loadJSONFromAssets(): String? {
        var json: String? = null
        try {
            val inputStream = this.context?.assets?.open("users.json")
            val size = inputStream?.available()
            val buffer = ByteArray(size!!)
            inputStream.read(buffer)
            inputStream.close()

            json = String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return json
    }

}
