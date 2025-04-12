package com.devmax.profile

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.devmax.Untils.Navigator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class Profile : AppCompatActivity() {

    lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        firebaseAuth = Firebase.auth

        val logout = findViewById<ImageView>(R.id.btnLogout)
        val home = findViewById<ImageView>(R.id.btnHome)

        logout.setOnClickListener {
            firebaseAuth.signOut()
            Navigator.goTo(this, Lgin::class.java)
            finish()
        }

        home.setOnClickListener{
            Navigator.goTo(this, Tasks_main::class.java)
            finish()
        }

    }
}
