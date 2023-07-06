package com.example.mychatapp.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mychatapp.R
import com.example.mychatapp.adapter.MessageAdapter
import com.example.mychatapp.models.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Chat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val auth = FirebaseAuth.getInstance()

        val dbRef = FirebaseDatabase.getInstance().reference

        val nome = intent.getStringExtra("nome")
        val receiverId = intent.getStringExtra("uid")
        val senderId = auth.currentUser?.uid

        supportActionBar?.title = nome

        val receiverRoon = receiverId + senderId
        val senderRoon =  senderId + receiverId



        val messagesRecycler = findViewById<RecyclerView>(R.id.messages_recycler)
        val messagesInput = findViewById<EditText>(R.id.message_input)
        val messagesButton = findViewById<ImageButton>(R.id.send_button)

        val messageList = ArrayList<Message>()

        val adapter = MessageAdapter(this, messageList, senderId)

        messagesRecycler.layoutManager = LinearLayoutManager(this)

        messagesRecycler.adapter = adapter


        dbRef.child("chats").child(senderRoon).child("message").addValueEventListener(object: ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for(message in snapshot.children) {
                    val curr = message.getValue(Message::class.java)

                    messageList.add(curr!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}

        })

        messagesButton.setOnClickListener {
            val message = Message(messagesInput.text.toString(), senderId)


            dbRef.child("chats").child(senderRoon).child("message").push().setValue(message)
                .addOnSuccessListener {
                    dbRef.child("chats").child(receiverRoon).child("message").push().setValue(message)
                }

            messagesInput.setText("")
        }

        actionBar?.setDisplayHomeAsUpEnabled(true)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == android.R.id.home){
            //Tratamos o clique no botão de voltar (<--)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}