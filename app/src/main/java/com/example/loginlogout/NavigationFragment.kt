package com.example.loginlogout

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.navigation_fragment.view.*
import org.jetbrains.anko.doAsync

class NavigationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.navigation_fragment, container, false)


        //Here we wan't to use a Custom Adapter that is tied to a custom Data Model
        doAsync {
            if (Global.getUserId() == null) {
               // println("getting user id")
                var response = authenticate(Global.getToken())
                if (response.contains("error")) {
                    (activity as NavigationHost).navigateTo(LoginFragment(), false) //no back  button functionality

                } else {
                    //mapping response to user class and then stores user id in global object
                    try {
                        val gson = GsonBuilder().create()
                        val user = gson.fromJson(response, user::class.java)
                        Global.setUserId(user.user_id)
                    } catch (e: Exception) {
                        println("error in the try" + e.toString())
                    }
                }
            }
        }

        view.my_events_button.setOnClickListener({
            (activity as NavigationHost).navigateTo(MyEventsListingFragment(), false) //no back  button functionality
        })

        view.all_events_button.setOnClickListener({
            (activity as NavigationHost).navigateTo(DayEventsFragment(), false) //no back  button functionality
        })

        view.logout_button.setOnClickListener({
            Global.setToken("")
            (activity as NavigationHost).navigateTo(LoginFragment(), false) //no back  button functionality
        })

        view.create_event_button.setOnClickListener({
            (activity as NavigationHost).navigateTo(CreateEventFragment(), false) //no back  button functionality
        })

        return view;
    }
    //gets userid from flask
    private fun authenticate(token: String): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://flaskappmysql.appspot.com/authenticate")
            .header("Authorization", "Bearer " + Global.getToken())
            .build()
        val response = client.newCall(request).execute()
        val body = response.body().string()
        return body
    }


}

class user(val user_id: Int)

