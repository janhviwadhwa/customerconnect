package com.abc.customerconnect

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ownerlogin : AppCompatActivity() {
    private lateinit var signuptxtbtn: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ownerlogin)
        signuptxtbtn = findViewById(R.id.signuptxtbtn)
        signuptxtbtn.setOnClickListener {
            startActivity(Intent(this, ownersignup::class.java))
        }
    }
}