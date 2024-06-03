package com.abc.customerconnect

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class customerlogin : AppCompatActivity() {
    private lateinit var signuptxtbtn: TextView
    private lateinit var loginbtn: Button
    private lateinit var Email: EditText
    private lateinit var Password: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customerlogin)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()

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
                val currentUser = mAuth.currentUser
                val userId = currentUser?.uid
                userId?.let {
                    mDatabase.child("owners").child(userId).addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // User is an owner, not allowed to login as customer
                                Toast.makeText(
                                    this@customerlogin,
                                    "Login failed. You are registered as an owner.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // Sign out the user
                                mAuth.signOut()
                            } else {
                                // User is a customer
                                val intent =
                                    Intent(this@customerlogin, CustomerMainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Error handling
                            Toast.makeText(this@customerlogin, "Database error", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
                }
            } else {
                Toast.makeText(this, "Login failed. Enter correct credentials.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
