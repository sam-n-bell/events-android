package com.example.loginlogout

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.logout_fragment.*
import kotlinx.android.synthetic.main.logout_fragment.view.*


class LogoutFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.logout_fragment, container, false)

        view.logout_button.setOnClickListener({
            // Navigate to the login Fragment.
            (activity as NavigationHost).navigateTo(LoginFragment(), false) //no back  button functionality
        })

        return view
    }


}