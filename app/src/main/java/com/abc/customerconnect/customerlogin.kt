package com.abc.customerconnect

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class customerlogin : AppCompatActivity() {
    private lateinit var signuptxtbtn: TextView
    private lateinit var loginbtn: Button
    private lateinit var Email: EditText
    private lateinit var Password: EditText
    private lateinit var mAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_customerlogin)

        mAuth = FirebaseAuth.getInstance()

        Email = findViewById(R.id.etLoginEmail)
        Password = findViewById(R.id.etLoginPassword)
        loginbtn = findViewById(R.id.btnLogin)
        signuptxtbtn = findViewById(R.id.signuptxtbtn)

        signuptxtbtn.setOnClickListener {
            startActivity(Intent(this, customersignup::class.java))
        }

        loginbtn.setOnClickListener {
            val email = Email.text.toString().trim()
            val password = Password.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, userr::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login failed. Enter correct credentials.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
