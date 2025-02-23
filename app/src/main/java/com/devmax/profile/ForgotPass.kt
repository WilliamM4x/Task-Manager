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
        val backLogin = findViewById<TextView>(R.id.backToLogin)
        val btnLogout = findViewById<ImageView>(R.id.btnLogout)

        val recoEmail= recoveryEmail.text.toString()

        btnRecovery.setOnClickListener {
           if(recoEmail.isNotEmpty()){Toast.makeText(this,"E-mail enviado",Toast.LENGTH_SHORT).show()}
           else{recoveryEmail.error = "Preencha o campo"}
        }

        backLogin.setOnClickListener {Navigator.goTo(this, Lgin::class.java)}
        btnLogout.setOnClickListener {Navigator.goTo(this, Lgin::class.java)}
    }
}
