package com.abc.customerconnect

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CustomerMainActivity : AppCompatActivity() {
    private lateinit var ownerList: RecyclerView
    private lateinit var ownerListAdapter: OwnerListAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_main)

        auth = FirebaseAuth.getInstance()

        ownerList = findViewById(R.id.owner_list)
        ownerListAdapter = OwnerListAdapter { owner -> startChatWithOwner(owner) }
        ownerList.apply {
            layoutManager = LinearLayoutManager(this@CustomerMainActivity)
            adapter = ownerListAdapter
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("owners")
        retrieveOwnerList()
    }

    private fun retrieveOwnerList() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val owners = mutableListOf<Owner>()
                for (data in snapshot.children) {
                    val name = data.child("name").value.toString()
                    val email = data.child("email").value.toString()
                    val owner = Owner(name, email)
                    owners.add(owner)
                }
                ownerListAdapter.submitList(owners)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }

    private fun startChatWithOwner(owner: Owner) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("ownerName", owner.name)
        intent.putExtra("ownerEmail", owner.email)
        startActivity(intent)
    }
}
