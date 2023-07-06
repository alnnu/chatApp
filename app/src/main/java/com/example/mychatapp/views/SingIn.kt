package com.example.mychatapp.views

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mychatapp.MainActivity
import com.example.mychatapp.R
import com.example.mychatapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SingIn : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in)

        supportActionBar?.hide()

        val sing_button = findViewById<Button>(R.id.Sing)
        val email = findViewById<EditText>(R.id.email)
        val senha = findViewById<EditText>(R.id.senha)
        val nome = findViewById<EditText>(R.id.nome)

        sing_button.setOnClickListener {
            singIn(nome.text.toString(),email.text.toString(), senha.text.toString())
        }
    }

    private fun singIn(nome: String, email: String, senha: String) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUser(nome,email, auth.currentUser?.uid!!)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "ERRO!!!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUser(nome: String, email: String, uid: String) {
        val dbRef = FirebaseDatabase.getInstance().reference

        dbRef.child("user").child(uid).setValue(User(nome, email, uid))
    }

}