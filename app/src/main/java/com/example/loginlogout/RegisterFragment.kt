package com.example.loginlogout

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.okhttp.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import java.io.IOException
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.register_fragment.email_edit_text
import kotlinx.android.synthetic.main.register_fragment.email_text_input
import kotlinx.android.synthetic.main.register_fragment.password_edit_text
import kotlinx.android.synthetic.main.register_fragment.password_text_input
import kotlinx.android.synthetic.main.register_fragment.*
import kotlinx.android.synthetic.main.register_fragment.view.*
import kotlinx.android.synthetic.main.register_fragment.view.name_edit_text
import kotlinx.android.synthetic.main.user.*


/**
 * Fragment representing the login screen for Shrine.
 */
class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.register_fragment, container, false)
        // Set an error if the password is less than 8 characters.
        view.submit_button.setOnClickListener({
            var name = name_edit_text.text!!
            var email = email_edit_text.text!!
            var password = password_edit_text.text!!

            if (email.length == 0 || email == null) {
                email_text_input.error = "Please enter an email"
            }
            else if (password.length == 0 || password == null) {
                password_text_input.error = "Please enter a password"
            }
            else if (name.length ==0 || name == null) {
                name_text_input.error = "Please enter a name"
            }
            else {
//                Thread.sleep(10000)
                doAsync {
                    println("calling register")
                    var response = register(email, password, name)
                    println("got data back" + response)
                    (activity as NavigationHost).navigateTo(LoginFragment(), false) //no back  button functionality
                    try {
//                        val gson = GsonBuilder().create()
//                        val token = gson.fromJson(response, auth::class.java)
                    } catch (e: Exception) {
                        println("error in the try" + e.toString())
                    }
                }
            }

        })
        view.cancel1_button.setOnClickListener({
            (activity as NavigationHost).navigateTo(LoginFragment(), false)
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

        view.name_edit_text.setOnKeyListener({ _, _, _ ->
            if (name_edit_text.text!!.length > 0) {
                name_text_input.error = null
            }
            false
        })


        return view
    }



    private fun register(email: Editable?, password: Editable?, name: Editable?): String {
        val response = ""
        println("name: " + name.toString() +"email: " + email.toString() + "\npassword: " + password.toString())

        //https://stackoverflow.com/questions/48395067/okhttp3-requestbody-in-kotlin
        val json = """
            { 
                "name":"${name}",
                "email":"${email}",
                "password":"${password}"
            }
            """.trimIndent()
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)


        val url = "https://flaskappmysql.appspot.com/register"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        try {
            println("whatever")
            val response = client.newCall(request).execute() //GETS URL. If this line freezes, check network & restart virtual device
            val bodystr =  response.body().string() // this can be consumed only once
            return bodystr
        } catch (e: Exception){
            println("Failed"+e.toString())
            return "fail"
        }
    }

}

