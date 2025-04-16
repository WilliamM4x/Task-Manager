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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Task_edit : AppCompatActivity() {

    val firebaseAuth = FirebaseAuth.getInstance()
    val uid = firebaseAuth.currentUser?.uid
    val dataBaseRef = FirebaseDatabase.getInstance().getReference("users/${uid}/tasks")
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

        //loadTask(taskTitle, descriptonTask)

        btnSave.setOnClickListener {
            val title = taskTitle.text.toString()
            val description = descriptonTask.text.toString()
            val time ="30/02/2025"
            val date = "11:00"

//            val time = taskFragment.txtTime.toString()
//            val date = taskFragment.txtDate.toString()

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

    fun loadTask(title: EditText, description: EditText) {
        this.task_id = intent.getStringExtra("selectedTask").toString()
        if(task_id=="")return
        val BaseRef = FirebaseDatabase.getInstance().getReference("users/$uid/tasks/${task_id}")
        
        BaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists())return

                title.setText(snapshot.child("title").value.toString())
                description.setText(snapshot.child("description").value.toString())

                //fazer isso lá no pickdate
//                date.setText(snapshot.child("date").value.toString())
//                time.setText(snapshot.child("time").value.toString())
            }



            override fun onCancelled(error: DatabaseError) {
               Toast.makeText(this@Task_edit, R.string.loadTaskFail, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun saveTask(title: String, description: String, date: String, time: String) {

        if (task_id == "") {
            val taskMap = hashMapOf(
                "title" to title,
                "description" to description,
//                "date" to date,
//                "time" to time

            )
            dataBaseRef.push().setValue(taskMap)
            Handler(Looper.getMainLooper()).postDelayed({
                Navigator.goTo(this, Tasks_main::class.java)
                finish()
            }, 1500)
        } else {
            val BaseRef = FirebaseDatabase.getInstance().getReference("users/$uid/tasks/${task_id}")
            Toast.makeText(this, "Tarefa não salva EDITAR", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 1500)
        }
    }

}



