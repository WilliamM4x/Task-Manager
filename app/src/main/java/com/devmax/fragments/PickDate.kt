package com.devmax.fragments


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.devmax.profile.R
import com.devmax.profile.Task_edit
import java.util.Calendar

class PickDate : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pick_date, container, false)

        val btnDate = view.findViewById<ImageView>(R.id.btn_Date)
        val btnTime = view.findViewById<ImageView>(R.id.btn_Time)
        val txtTime = view.findViewById<EditText>(R.id.time)
        val txtdate = view.findViewById<EditText>(R.id.date)

        val calendarInstanceCurrent = Calendar.getInstance()
        val day = calendarInstanceCurrent.get(Calendar.DAY_OF_MONTH)
        val month = calendarInstanceCurrent.get(Calendar.MONTH)
        val year = calendarInstanceCurrent.get(Calendar.YEAR)
        val hour = calendarInstanceCurrent.get(Calendar.HOUR)
        val minute = calendarInstanceCurrent.get(Calendar.MINUTE)


        btnDate.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(view.context, { _, yearOfYear, monthOfYaer, dayOfMonth ->
                    txtdate.setText(String.format("%2d/%02d/%4d", dayOfMonth, monthOfYaer + 1, yearOfYear)                    )
                }, year, month, day)
            datePickerDialog.show()
        }
        btnTime.setOnClickListener {
            val timePickerDialog = TimePickerDialog(view.context, { _, hourOfDay, minuteOfHour ->
                    txtTime.setText(String.format("%2d:%02d", hourOfDay, minuteOfHour))
                }, hour, minute, true)
            timePickerDialog.show()

            }


            return view

        }

}

