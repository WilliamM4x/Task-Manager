package com.devmax.profile

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.devmax.Untils.Navigator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class ForgotPass : AppCompatActivity() {

    var firebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_pass)

        val btnRecovery = findViewById<Button>(R.id.btnRecovery)
        val recoveryEmail = findViewById<TextView>(R.id.recoveryEmail)
        val btnLogout = findViewById<ImageView>(R.id.btnLogout)


        btnRecovery.setOnClickListener {
           val recoEmail= recoveryEmail.text.toString()
           if(recoEmail.isEmpty()){recoveryEmail.error = getString(R.string.empty_email)}
           else{
                recoveryPass(recoEmail)
           }
        }

        btnLogout.setOnClickListener {
            Navigator.goTo(this, Lgin::class.java)
              finish()
        }
    }

    fun recoveryPass(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,getString(R.string.linkRecovery),Toast.LENGTH_SHORT).show()
                    Navigator.goTo(this, Lgin::class.java)
                }else{

                }
            }
    }

}
