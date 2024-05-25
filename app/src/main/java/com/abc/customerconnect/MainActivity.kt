package com.abc.customerconnect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var customerbtn: Button
    private lateinit var shopownerbtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        customerbtn = findViewById(R.id.customerbtn)
        shopownerbtn = findViewById(R.id.shopownerbtn)

        // Set click listeners
        customerbtn.setOnClickListener {
            startActivity(Intent(this, customersignup::class.java))
        }
        shopownerbtn.setOnClickListener {
            startActivity(Intent(this, ownersignup::class.java))
        }
    }
}