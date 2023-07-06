package com.example.mychatapp.views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mychatapp.MainActivity
import com.example.mychatapp.R
import com.example.mychatapp.adapter.UserAdapter
import com.example.mychatapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.MainScope

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userRecyclerView = findViewById<RecyclerView>(R.id.userRecycleView)
        val userList = ArrayList<User>()

        val userAdapter = UserAdapter(this, userList)

        val dbRef = FirebaseDatabase.getInstance().reference

        val auth = FirebaseAuth.getInstance()


        userRecyclerView.layoutManager = LinearLayoutManager(this)

        userRecyclerView.adapter = userAdapter

        dbRef.child("user").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for(user in snapshot.children) {
                    val curr = user.getValue(User::class.java)

                    if(curr?.uid != auth.currentUser?.uid)
                        userList.add(curr!!)
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val auth = FirebaseAuth.getInstance()
        val id = item.itemId
        if (id == R.id.log_out){
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}