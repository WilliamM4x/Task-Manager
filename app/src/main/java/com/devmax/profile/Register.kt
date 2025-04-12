package com.devmax.profile

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.devmax.Untils.Navigator
import com.devmax.Untils.PassWord
import com.devmax.fragments.PassDifficult
import com.devmax.profile.R.id.passInput
import com.devmax.profile.R.id.singinPassword
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Register : AppCompatActivity() {
    lateinit var fireBaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        supportFragmentManager.beginTransaction().replace(R.id.singinPassword, PassDifficult()).commitNow()
        val singPassword =supportFragmentManager.findFragmentById(R.id.singinPassword) as? PassDifficult

        fireBaseAuth = Firebase.auth

        val btnSingIn = findViewById<Button>(R.id.btnSingIn)
        val singinEmail = findViewById<EditText>(R.id.singinEmail)


        val agreeSingPassword = findViewById<EditText>(R.id.agreeSingPassword)
        val btnLogout = findViewById<ImageView>(R.id.btnLogout)

        val passInput = singPassword?.view?.findViewById<EditText>(R.id.passInput)

        btnLogout.setOnClickListener {
            fireBaseAuth.signOut()
            Navigator.goTo(this, Lgin::class.java)
            finish()
        }

//        passInput?.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//            override fun afterTextChanged(s: Editable?) { }
//        })

        btnSingIn.setOnClickListener {
            //val pass = singPassword?.passInput?.text.toString()
            val pass = passInput?.text.toString()
            val pass2 = agreeSingPassword.text.toString()
            val email = singinEmail.text.toString()
            if (validateFilds(email, pass, pass2)) {
                if (pass == pass2) {
                    CoroutineScope(Dispatchers.IO).launch {
                        createAccount(email = email, password = pass)
                        delay(timeMillis = 2000)
                        Navigator.goTo(this@Register, Lgin::class.java)
                    }
                    Navigator.goTo(this, Lgin::class.java)
                } else {
                    Toast.makeText(this@Register, getString(R.string.noMatchPass), Toast.LENGTH_LONG).show()
                    singPassword?.passInput?.error = getString(R.string.noMatchPass)
                    agreeSingPassword.error = getString(R.string.noMatchPass)
                }
            }
        }

    }

    private fun validateFilds(email: String, password: String, aPassword: String): Boolean {
        val singPassword =
            supportFragmentManager.findFragmentById(R.id.singinPassword) as PassDifficult
        var isValid = true

        if (email.isEmpty()) {
            findViewById<TextView>(R.id.singinEmail).error = getString(R.string.empty_email)
            isValid = false
        }
        if (password.isEmpty()) {
            singPassword.passInput.error = getString(R.string.empty_pass)
            isValid = false
        }
        if (aPassword.isEmpty()) {
            findViewById<TextView>(R.id.agreeSingPassword).error = getString(R.string.empty_pass)
            isValid = false
        }
        return isValid
    }

    fun createAccount(email: String, password: String) {
        fireBaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = fireBaseAuth.currentUser
                    if (user != null) {
                      Toast.makeText(this@Register, getString(R.string.subsSucess), Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@Register, getString(R.string.nOsubsSucess), Toast.LENGTH_LONG) .show()
                    }
                }
            }

    }
}