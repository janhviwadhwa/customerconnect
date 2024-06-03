package com.abc.customerconnect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OwnerMainActivity : AppCompatActivity() {
    private lateinit var customerList: RecyclerView
    private lateinit var customerListAdapter: CustomerListAdapter
    private lateinit var ownerId: String // Initialize this with the logged-in owner's UID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_main)

        // Get ownerId from intent extras
        ownerId = intent.getStringExtra("ownerId") ?: ""

        // Initialize RecyclerView and adapter
        customerList = findViewById(R.id.customer_list)
        customerListAdapter = CustomerListAdapter()
        customerList.apply {
            layoutManager = LinearLayoutManager(this@OwnerMainActivity)
            adapter = customerListAdapter
        }

        // Retrieve customer messages
        retrieveCustomerMessages()
    }

    private fun retrieveCustomerMessages() {
        val databaseReference =
            FirebaseDatabase.getInstance().getReference("messages").child(ownerId)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val customerMessages = mutableListOf<CustomerMessage>()
                for (messageSnapshot in dataSnapshot.children) {
                    val customerMessage = messageSnapshot.getValue(CustomerMessage::class.java)
                    customerMessage?.let {
                        customerMessages.add(it)
                    }
                }
                displayCustomerList(customerMessages)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })
    }

    private fun displayCustomerList(customerMessages: List<CustomerMessage>) {
        // Convert customer messages to customer details and display in RecyclerView
        val customerList = mutableListOf<Customer>()
        for (message in customerMessages) {
            val customer = Customer(message.senderName, message.senderEmail, message.message)
            customerList.add(customer)
        }
        customerListAdapter.submitList(customerList)
    }
}
