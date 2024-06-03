package com.abc.customerconnect

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

class ownerlogin : AppCompatActivity() {
    private lateinit var signuptxtbtn: TextView
    private lateinit var loginbtn: Button
    private lateinit var Email: EditText
    private lateinit var Password: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ownerlogin)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()

        Email = findViewById(R.id.Email)
        Password = findViewById(R.id.Password)
        loginbtn = findViewById(R.id.btnoLogin)
        signuptxtbtn = findViewById(R.id.signupotxtbtn)

        signuptxtbtn.setOnClickListener {
            startActivity(Intent(this, ownersignup::class.java))
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
                val ownerId = currentUser?.uid
                ownerId?.let {
                    mDatabase.child("Customers").child(ownerId)
                        .addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    // User is a customer
                                    startActivity(
                                        Intent(
                                            this@ownerlogin,
                                            CustomerMainActivity::class.java
                                        )
                                    )
                                    finish()
                                } else {
                                    // User is not a customer, check if they are an owner
                                    mDatabase.child("owners").child(ownerId)
                                        .addListenerForSingleValueEvent(object :
                                            ValueEventListener {
                                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    // User is an owner
                                                    startActivity(
                                                        Intent(
                                                            this@ownerlogin,
                                                            OwnerMainActivity::class.java
                                                        ).putExtra("ownerId", ownerId)
                                                    )
                                                    finish()
                                                } else {
                                                    // User is neither a customer nor an owner
                                                    Toast.makeText(
                                                        this@ownerlogin,
                                                        "Invalid user type",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }

                                            override fun onCancelled(databaseError: DatabaseError) {
                                                // Error handling
                                                Toast.makeText(
                                                    this@ownerlogin,
                                                    "Database error",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        })
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // Error handling
                                Toast.makeText(
                                    this@ownerlogin,
                                    "Database error",
                                    Toast.LENGTH_SHORT
                                ).show()
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
