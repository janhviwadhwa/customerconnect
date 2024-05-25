package com.abc.customerconnect

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ownersignup : AppCompatActivity() {
    private lateinit var logintxtbtn: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ownersignup)
        logintxtbtn = findViewById(R.id.logintxtbtn)
        logintxtbtn.setOnClickListener {
            val intent = Intent(this, ownerlogin::class.java)
            startActivity(intent)
        }
    }
}