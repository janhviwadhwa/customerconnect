package com.abc.customerconnect

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ownersignup : AppCompatActivity() {
    private lateinit var logintxtbtn: TextView
    private lateinit var Name: TextView
    private lateinit var Email: TextView
    private lateinit var Password: TextView
    private lateinit var Shopno: TextView
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ownersignup)
        logintxtbtn = findViewById(R.id.logintxtbtn)
        Name = findViewById(R.id.Name)
        btnSignUp = findViewById(R.id.btnSignup)
        Email = findViewById(R.id.Email)
        Password = findViewById(R.id.Password)
        Shopno = findViewById(R.id.ShopNo)
        mAuth = FirebaseAuth.getInstance()

        logintxtbtn.setOnClickListener {
            val intent = Intent(this, ownerlogin::class.java)
            startActivity(intent)
        }
        btnSignUp.setOnClickListener {
            val name = Name.text.toString()
            val email = Email.text.toString()
            val shopno = Shopno.text.toString()
            val password = Password.text.toString()
            signUp(name, email, shopno, password)
        }
    }

    private fun signUp(name: String, email: String, shopno: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val userId = mAuth.currentUser?.uid!!
                val owners = owners(name, email, shopno)
                addUserToDatabase(userId, owners)
                val intent = Intent(this@ownersignup, userr::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this@ownersignup,
                    "Sign up failed: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun addUserToDatabase(userId: String, owners: owners) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("owners").child(userId).setValue(owners)
    }
}