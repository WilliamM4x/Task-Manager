package com.devmax.profile

import android.content.ContentValues.TAG
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.devmax.Untils.Navigator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Lgin : AppCompatActivity() {

    val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()

//    public override fun onStart() {
//        super.onStart()
//        val currentUser = firebaseAuth.currentUser
//        if (currentUser != null) { Navigator.goTo(this, Tasks_main::class.java)
//            finish()
//        } else {
//            Log.d(TAG, "onStart: User is signed out")
//        }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lgin)


        val bTNentry = findViewById<Button>(R.id.btn_entry)
        val forgotPass = findViewById<TextView>(R.id.ForgotPass)
        val notRegister = findViewById<TextView>(R.id.Register)
        val logintext = findViewById<TextView>(R.id.login)
        val passtext = findViewById<TextView>(R.id.Password)


        bTNentry.setOnClickListener {
           val pass = passtext.text.toString()
           val login = logintext.text.toString()

            if(pass.isNotEmpty() && login.isNotEmpty()){

                CoroutineScope(Dispatchers.IO).launch {
                Login(login,pass)
                }
            }else{
                if(pass.isEmpty()) passtext.error = (resources.getString(R.string.empty_pass))
                if(login.isEmpty()) logintext.error = (resources.getString(R.string.empty_email))
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

    fun Login(email: String, password: String) {
    firebaseAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithEmail:success")
                val user = firebaseAuth.currentUser
                if (user != null) {
                    Toast.makeText(this@Lgin, "${getString(R.string.Welcome)} ${user.email}", Toast.LENGTH_SHORT).show()
                    Navigator.goTo(this@Lgin, Tasks_main::class.java)
                }else{
                    Toast.makeText(baseContext, "Authentication failed.",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this@Lgin, getString(R.string.noUser), Toast.LENGTH_SHORT).show()
            }
        }
    }

}