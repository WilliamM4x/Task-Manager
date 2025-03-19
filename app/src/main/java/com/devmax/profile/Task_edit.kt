package com.devmax.profile

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.devmax.Untils.Navigator

class Task_edit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task_edit)

        val backTask = findViewById<ImageView>(R.id.btnHome)
        val btnLogout = findViewById<ImageView>(R.id.btnLogout)

        backTask.setOnClickListener { Navigator.goTo(this, Tasks_main::class.java)}
        btnLogout.setOnClickListener { Navigator.goTo(this, Lgin::class.java)}
    }
}