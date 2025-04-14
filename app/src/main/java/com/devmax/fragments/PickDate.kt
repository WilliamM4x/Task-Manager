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
import android.widget.Toast
import com.devmax.profile.R
import com.devmax.profile.Task_edit
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar

class PickDate : Fragment() {

    lateinit var txtTime: EditText
    lateinit var txtDate: EditText
    var task_id: String = ""

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

          //  loadTaskDT(txtTime.text.toString(), txtdate.text.toString())

            return view

        }

//    fun loadTaskDT(title: EditText, description: EditText) {
//        this.task_id = intent.getStringExtra("selectedTask").toString()
//        if(task_id=="")return
//        val BaseRef = FirebaseDatabase.getInstance().getReference("users/$uid/tasks/${task_id}")
//
//        BaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(!snapshot.exists())return
//
//                title.setText(snapshot.child("title").value.toString())
//                description.setText(snapshot.child("description").value.toString())
//
//                //fazer isso lá no pickdate
////                date.setText(snapshot.child("date").value.toString())
////                time.setText(snapshot.child("time").value.toString())
//            }
//
//
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@Task_edit, R.string.loadTaskFail, Toast.LENGTH_SHORT).show()
//            }
//        })
    }



