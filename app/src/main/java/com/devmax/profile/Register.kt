package com.devmax.profile

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        val btnSingIn = findViewById<Button>(R.id.btnSingIn)

        val singinEmail = findViewById<TextView>(R.id.singinEmail)
        val singinPassword = findViewById<TextView>(R.id.singinPassword)
        val agreeSingPassword = findViewById<TextView>(R.id.agreeSingPassword)
        val btnLogout = findViewById<ImageView>(R.id.btnLogout)


        btnLogout.setOnClickListener {Navigator.goTo(this, Lgin::class.java)
        finish()}

        btnSingIn.setOnClickListener {
            val pass = singinPassword.text.toString()
            val pass2 = agreeSingPassword.text.toString()
            val email = singinEmail.text.toString()

             if(validateFilds(email, pass, pass2)){
                if(pass == pass2){
                    Toast.makeText(this,"Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()

                }else{Toast.makeText(this,"As senhas não conferem.", Toast.LENGTH_SHORT).show()
                    singinPassword.error = "Senhas não conferem"
                    agreeSingPassword.error = "Senhas não conferem"}
             }
        }

    }
    fun validateFilds( email:String, password:String, aPassword:String):Boolean{

        var isValid = true

        if (email.isEmpty()) {
            findViewById<TextView>(R.id.singinEmail).error = "Preencha o campo"
            isValid = false
        }
        if (password.isEmpty()) {
            findViewById<TextView>(R.id.singinPassword).error = "Preencha o campo"
            isValid = false
        }
        if (aPassword.isEmpty()) {
            findViewById<TextView>(R.id.agreeSingPassword).error = "Preencha o campo"
            isValid = false
        }
        return isValid
    }
}
