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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class customersignup : AppCompatActivity() {
    private lateinit var loginbtn: TextView
    private lateinit var Name: EditText
    private lateinit var Email: EditText
    private lateinit var Phonenumber: EditText
    private lateinit var Confirmpassword: EditText
    private lateinit var Password: EditText
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_customersignup)
        loginbtn = findViewById(R.id.logintxtbtn)
        loginbtn.setOnClickListener {
            startActivity(Intent(this, customerlogin::class.java))
        }

        Name = findViewById(R.id.Name)
        Email = findViewById(R.id.Email)
        Phonenumber = findViewById(R.id.Phonenumber)
        Password = findViewById(R.id.Password)
        btnSignUp = findViewById(R.id.btnSignup)
        mAuth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            val name = Name.text.toString()
            val email = Email.text.toString()
            val phoneNumber = Phonenumber.text.toString()
            val password = Password.text.toString()
            signUp(name, email, phoneNumber, password)
        }
    }

    private fun signUp(name: String, email: String, phoneNumber: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val userId = mAuth.currentUser?.uid!!
                val customer = Customer(name, email, phoneNumber)
                addUserToDatabase(userId, customer)
                val intent = Intent(this@customersignup, CustomerMainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this@customersignup,
                    "Sign up failed: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun addUserToDatabase(userId: String, customer: Customer) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("Customers").child(userId).setValue(customer)
    }
}
