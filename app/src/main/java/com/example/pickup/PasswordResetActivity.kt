package com.example.pickup

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class PasswordResetActivity : AppCompatActivity() {
    private lateinit var editTextEmail: EditText
    private lateinit var buttonReset: Button
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)
        editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        buttonReset = findViewById<Button>(R.id.buttonReset)
        firebaseAuth = FirebaseAuth.getInstance()
        buttonReset.setOnClickListener(View.OnClickListener {
            val email = editTextEmail.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Enter your email", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Password reset email sent",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish() // Close the activity after sending reset email
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Failed to send reset email",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        })
    }
}

