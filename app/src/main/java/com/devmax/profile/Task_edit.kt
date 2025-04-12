package com.devmax.profile

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.devmax.Untils.Navigator
import com.devmax.fragments.PickDate
import com.devmax.fragments.Task_list
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Task_edit : AppCompatActivity() {

    val firebaseAuth = FirebaseAuth.getInstance()
    val uid = firebaseAuth.currentUser?.uid
    val dataBaseRef = FirebaseDatabase.getInstance().getReference("users/$uid/tasks")
    var task_id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task_edit)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_Pick, PickDate()).commit()

        val taskFragment = supportFragmentManager.findFragmentById(R.id.fragment_Pick) as PickDate

        val taskTitle = findViewById<EditText>(R.id.taskTitle)
        val descriptonTask = findViewById<EditText>(R.id.descriptonTask)


        val btnSave = findViewById<Button>(R.id.btnSave)
        val backTask = findViewById<ImageView>(R.id.btnHome)
        val btnLogout = findViewById<ImageView>(R.id.btnLogout)


        btnSave.setOnClickListener {
            val title = taskTitle.text.toString()
            val description = descriptonTask.text.toString()
            val time = taskFragment?.txtTime!!?.text.toString()
            val date = taskFragment?.txtDate!!?.text.toString()

            saveTask(title, description, date, time)
        }


        backTask.setOnClickListener {
            Navigator.goTo(this, Tasks_main::class.java)
            finish()
        }
        btnLogout.setOnClickListener {
            Navigator.goTo(this, Lgin::class.java)
            finish()
        }
    }

    fun loadTask() {

    }

    fun saveTask(title: String, description: String, date: String, time: String) {

        if (task_id=="") {
            val taskMap = hashMapOf(
                "title" to title,
                "description" to description,
                "date" to date,
                "time" to time

            )
            dataBaseRef.push().setValue(taskMap)
            Handler(Looper.getMainLooper()).postDelayed({
             Navigator.goTo(this, Tasks_main::class.java)
                finish()
            },1500)
        }else{
            Toast.makeText(this, "Tarefa não salva EDITAR", Toast.LENGTH_SHORT).show()
        }

    }
}


