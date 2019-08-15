package com.example.loginlogout

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import kotlinx.android.synthetic.main.day_events_listing_fragment.*
import kotlinx.android.synthetic.main.day_events_listing_fragment.view.*
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.view.*
import org.jetbrains.anko.doAsync
import java.util.*

class DayEventsFragment  : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.day_events_listing_fragment, container, false)
        // Set an error if the password is less than 8 characters.
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        view.pick_date_button.setOnClickListener({
            val dpd = DatePickerDialog(view.context, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                date_display.setText("" + mDay + "/" + mMonth + "/" + mYear)
            }, year, month, day)
            dpd.show()
        })

        return view
    }





    private fun getEvents(day: Editable?): String {
        return ""
    }

}