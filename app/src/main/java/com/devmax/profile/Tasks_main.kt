package com.devmax.profile

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Tasks_main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.tasks_main)


        val add_task = findViewById<FloatingActionButton>(R.id.add_task)
        val logout = findViewById<ImageView>(R.id.btnLogout)
        val profile = findViewById<ImageView>(R.id.btnProfile)

        profile.setOnClickListener {
            Navigator.goTo(this, Profile::class.java)
            finish()
        }

        add_task.setOnClickListener {
            Navigator.goTo(this, Task_edit::class.java)
            finish()
        }

        logout.setOnClickListener {
            Navigator.goTo(this, Lgin::class.java)
            finish()
        }

        }
    }
