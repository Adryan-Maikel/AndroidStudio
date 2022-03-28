package com.characters.leagueoflegends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.characters.leagueoflegends.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Welcome"
        auth = Firebase.auth

        binding.btnEnter.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.edtEmail.text) -> {
                    binding.edtEmail.error = "Enter your valid email."
                }
                TextUtils.isEmpty(binding.edtPassword.text) -> {
                    binding.edtPassword.error = "Enter your valid password."
                }
                else -> {
                    login(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.edtEmail.text) -> {
                    binding.edtEmail.error = "Enter your valid email."
                }
                TextUtils.isEmpty(binding.edtPassword.text) -> {
                    binding.edtPassword.error = "Enter your valid password."
                }
                else -> {
                    register(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
                }
            }
        }


    }

    private fun clearFields() {
        binding.edtEmail.text.clear()
        binding.edtPassword.text.clear()
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                openChampionsActivity()
            } else {
                Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(baseContext, "User registered success.", Toast.LENGTH_LONG).show()
                clearFields()
                openChampionsActivity()
            } else {
                Toast.makeText(baseContext, "User not registered.", Toast.LENGTH_LONG).show()
                clearFields()
            }
        }
    }

    private fun openChampionsActivity() {
        clearFields()
        val intent = Intent(this, ChampionsActivity::class.java)
        startActivity(intent)
        finish()
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            if (currentUser.email?.isNotEmpty() == true) {
                Toast.makeText(
                    baseContext,
                    "User " + currentUser.email + " logged.",
                    Toast.LENGTH_SHORT
                ).show()
                openChampionsActivity()
            }
        }
    }
}