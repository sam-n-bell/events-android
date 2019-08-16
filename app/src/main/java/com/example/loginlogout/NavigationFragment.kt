package com.example.loginlogout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.navigation_fragment.view.*
import org.jetbrains.anko.doAsync

class NavigationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.navigation_fragment, container, false)


        //Here we wan't to use a Custom Adapter that is tied to a custom Data Model
        doAsync {

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

}