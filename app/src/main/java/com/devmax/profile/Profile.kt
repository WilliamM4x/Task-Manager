package com.devmax.profile

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.devmax.Untils.Navigator

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        val logout = findViewById<ImageView>(R.id.btnLogout)
        val home = findViewById<ImageView>(R.id.btnHome)

        logout.setOnClickListener {
            Navigator.goTo(this, Lgin::class.java)
            finish()
        }

        home.setOnClickListener{
            Navigator.goTo(this, Tasks_main::class.java)
            finish()
        }

    }
}
