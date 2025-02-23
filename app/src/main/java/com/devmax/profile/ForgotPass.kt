package com.devmax.profile

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ForgotPass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_pass)

        val btnRecovery = findViewById<Button>(R.id.btnRecovery)
        val recoveryEmail = findViewById<TextView>(R.id.recoveryEmail)
        val btnLogout = findViewById<ImageView>(R.id.btnLogout)


        btnRecovery.setOnClickListener {
           val recoEmail= recoveryEmail.text.toString()
           if(recoEmail.isEmpty()){recoveryEmail.error = "Preencha o campo."}
           else{Toast.makeText(this,"E-mail enviado.",Toast.LENGTH_SHORT).show()}
        }

        btnLogout.setOnClickListener {Navigator.goTo(this, Lgin::class.java)}
    }
}
