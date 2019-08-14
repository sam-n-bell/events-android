package com.example.loginlogout

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
//                Thread.sleep(10000)
                doAsync {
                    println("calling authenticate")
//                    Thread.sleep(10000)
//                    println("woke up")
                    var response = authenticate(email, password)
                }
            }

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
        val response = ""
        println("email: " + email.toString() + "\npassword: " + password.toString())

        //https://stackoverflow.com/questions/48395067/okhttp3-requestbody-in-kotlin
        val json = """
            {
                "email":"${email}",
                "password":"${password}"
            }
            """.trimIndent()
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)


        val url = "https://flaskappmysql.appspot.com/login"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        try {
            val response = client.newCall(request).execute() //GETS URL. If this line freezes, check network & restart virtual device
            val bodystr =  response.body().string() // this can be consumed only once
            println("response\n" + bodystr)
            return bodystr
        } catch (e: Exception){
            println("Failed"+e.toString())
            return "fail"
        }
    }

}
