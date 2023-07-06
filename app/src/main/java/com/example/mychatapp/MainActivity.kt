package com.example.mychatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mychatapp.views.Home
import com.example.mychatapp.views.SingIn
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val sing_button = findViewById<Button>(R.id.Sing)
        val login_button = findViewById<Button>(R.id.login)
        val email = findViewById<EditText>(R.id.email)
        val senha = findViewById<EditText>(R.id.senha)

        sing_button.setOnClickListener {
            val intent = Intent(this, SingIn::class.java)
            startActivity(intent)
        }
        login_button.setOnClickListener {
            login(email.text.toString(), senha.text.toString())
        }
    }

    private fun login(email: String, senha: String) {
        var auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "senha ou email errados", Toast.LENGTH_SHORT).show()
                }
            }
    }
}