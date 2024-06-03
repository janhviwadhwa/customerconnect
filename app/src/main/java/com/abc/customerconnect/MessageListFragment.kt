//// File: MessageListFragment.kt
//package com.abc.customerconnect
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.abc.customerconnect.databinding.MessageListBinding
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.ListenerRegistration
//import com.google.firebase.firestore.Query
//
//class MessageListFragment : Fragment() {
//
//    private val db = FirebaseFirestore.getInstance()
//    private lateinit var messageAdapter: MessageAdapter
//    private var listenerRegistration: ListenerRegistration? = null
//    private var _binding: MessageListBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = MessageListBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        messageAdapter = MessageAdapter()
//        binding.recyclerViewMessages.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = messageAdapter
//        }
//
//        listenerRegistration = db.collection("messages")
//            .orderBy("timestamp", Query.Direction.ASCENDING)
//            .addSnapshotListener { snapshots, e ->
//                if (e != null) {
//                    return@addSnapshotListener
//                }
//
//                val messages = snapshots?.toObjects(Message::class.java)
//                messages?.let {
//                    messageAdapter.submitList(messages)
//                }
//            }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        listenerRegistration?.remove()
//        _binding = null
//    }
//}
