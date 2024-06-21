package com.abc.customerconnect

import Owner
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class CustomerMainActivity : AppCompatActivity() {
    private lateinit var customerList: RecyclerView
    private lateinit var ownerListAdapter: OwnerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_main)

        customerList = findViewById(R.id.customer_list)
        ownerListAdapter = OwnerListAdapter(emptyList()) { owner ->
            // Handle item click here, e.g., open chat view
            openChatView(owner)
        }

        customerList.apply {
            layoutManager = LinearLayoutManager(this@CustomerMainActivity)
            adapter = ownerListAdapter
        }

        retrieveOwnerList()
    }

    private fun retrieveOwnerList() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("owners")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val owners = mutableListOf<Owner>()
                for (ownerSnapshot in dataSnapshot.children) {
                    val ownerId = ownerSnapshot.key ?: ""
                    val ownerName = ownerSnapshot.child("name").value?.toString() ?: ""
                    val ownerEmail = ownerSnapshot.child("email").value?.toString() ?: ""
                    val ownerShopNo = ownerSnapshot.child("shopno").value?.toString() ?: ""
                    val owner = Owner(ownerId, ownerName, ownerEmail)
                    owners.add(owner)
                }
                displayOwnerList(owners)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })
    }

    private fun displayOwnerList(owners: List<Owner>) {
        ownerListAdapter = OwnerListAdapter(owners) { owner ->
            openChatView(owner)
        }
        customerList.adapter = ownerListAdapter
    }

    private fun openChatView(owner: Owner) {

        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("ownerId", owner.ownerId)
        intent.putExtra("ownerName", owner.ownerName)
        intent.putExtra("ownerEmail", owner.ownerEmail)
        startActivity(intent)
        // Example function to handle opening chat view with the selected owner
        // Replace with your actual implementation to open chat view
        // You can use intents, fragments, or any other method based on your app's architecture
    }
}
