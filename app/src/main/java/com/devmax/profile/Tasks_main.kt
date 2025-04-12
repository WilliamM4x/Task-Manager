package com.devmax.profile

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.devmax.Untils.Navigator
import com.devmax.fragments.Task_list
import com.devmax.fragments.WeatherFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class Tasks_main : AppCompatActivity() {
    lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.tasks_main)

        firebaseAuth = Firebase.auth

        val add_task = findViewById<FloatingActionButton>(R.id.add_task)
        val logout = findViewById<ImageView>(R.id.btnLogout)
        val profile = findViewById<ImageView>(R.id.btnProfile)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentWeather, WeatherFragment()).commitNow()
        val weatherFragment = supportFragmentManager.findFragmentById(R.id.fragmentWeather) as WeatherFragment

        supportFragmentManager.beginTransaction().replace(R.id.frag_list, Task_list()).commitNow()
        val taskFragment = supportFragmentManager.findFragmentById(R.id.frag_list) as Task_list


        profile.setOnClickListener {
            Navigator.goTo(this, Profile::class.java)
            finish()
        }

        add_task.setOnClickListener {
            Navigator.goTo(this, Task_edit::class.java)
            finish()
        }

        logout.setOnClickListener {
            firebaseAuth.signOut()
            Navigator.goTo(this, Lgin::class.java)
            finish()
        }

    }
}
