package com.devmax.profile

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Lgin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lgin)

        val BTNentry = findViewById<Button>(R.id.btn_entry)
        val forgotPass = findViewById<TextView>(R.id.ForgotPass)
        val notRegister = findViewById<TextView>(R.id.Register)
        val logintext = findViewById<TextView>(R.id.login)
        val passtext = findViewById<TextView>(R.id.Password)

        BTNentry.setOnClickListener {
           val pass = passtext.text.toString()
           val login = logintext.text.toString()

            if(pass.isNotEmpty() && login.isNotEmpty()){
                Navigator.goTo(this,Tasks_main::class.java)
                finish()
            }else{
                if(pass.isEmpty()) passtext.error = "Preencha o campo."
                if(login.isEmpty()) logintext.error = "Preencha o campo."
            }

        }

        forgotPass.setOnClickListener {
            Navigator.goTo(this,ForgotPass::class.java)
            finish()
        }

        notRegister.setOnClickListener {
            Navigator.goTo(this,Register::class.java)
            finish()
        }


    }
}