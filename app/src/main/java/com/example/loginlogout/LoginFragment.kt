package com.example.loginlogout

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.okhttp.*
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.view.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import java.io.IOException
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.login_fragment.email_edit_text
import kotlinx.android.synthetic.main.login_fragment.email_text_input
import kotlinx.android.synthetic.main.login_fragment.password_edit_text
import kotlinx.android.synthetic.main.login_fragment.password_text_input
import kotlinx.android.synthetic.main.register_fragment.*
import org.json.JSONObject

/**
 * Fragment representing the login screen for Shrine.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.login_fragment, container, false)
        // Set an error if the password is less than 8 characters.
        view.next_button.setOnClickListener({
            var email = email_edit_text.text!!
            var password = password_edit_text.text!!

            if (email.length == 0 || email == null) {
                email_text_input.error = "Please enter an email"
            }
            else if (password.length == 0 || password == null) {
                password_text_input.error = "Please enter a password"
            }
            else {
                doAsync {
                    var response = authenticate(email, password)
                    if (response.contains("error")) {
                        val handler = Handler(Looper.getMainLooper());
                        handler.post({
                            error_textview.text = "Can't login with this information"
                        })
                    } else {
                        try {
                            val gson = GsonBuilder().create()
                            val token = gson.fromJson(response, token::class.java)
                            Global.setToken(token.token)
                            (activity as NavigationHost).navigateTo(NavigationFragment(), false) //no back  button functionality
                        } catch (e: Exception) {
                            println("error in the try" + e.toString())
                        }
                    }

                }
            }

        })

        view.register_button.setOnClickListener({
            (activity as NavigationHost).navigateTo(RegisterFragment(), false)
        })

        view.email_edit_text.setOnKeyListener({ _, _, _ ->
            if (email_edit_text.text!!.length > 0) {
                email_text_input.error = null
            }
            false
        })

        view.password_edit_text.setOnKeyListener({ _, _, _ ->
            if (password_edit_text.text!!.length > 0) {
                password_text_input.error = null
            }
            false
        })


        return view
    }



    private fun authenticate(email: Editable?, password: Editable?): String {
        return HttpUtilities.posturl("https://flaskappmysql.appspot.com/login", """
            {
                "email":"${email}",
                "password":"${password}"
            }
            """)
    }

}

class token(val token: String)
