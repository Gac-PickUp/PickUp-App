package com.example.pickup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class PasswordResetActivity : AppCompatActivity() {
    private lateinit var resetEmail: EditText
    private lateinit var buttonReset: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var strEmail: String
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_password_reset)
        resetEmail = findViewById<EditText>(R.id.resetEmail)
        buttonReset = findViewById<Button>(R.id.buttonReset)
        backButton = findViewById<Button>(R.id.back_btn)
        auth = FirebaseAuth.getInstance()

        // Back button listener
        backButton.setOnClickListener {
            startActivity(Intent(this, LoginPage::class.java))
        }


        // Reset Button Listener
        buttonReset.setOnClickListener {
            strEmail = resetEmail.text.toString().trim()
            if (strEmail.isNotEmpty()) {
                resetPassword()

            } else {
                resetEmail.error = "Email field can't be empty"
            }
        }

    }

    private fun resetPassword() {
        // progressBar.visibility = View.VISIBLE
        buttonReset.visibility = View.INVISIBLE


        auth.sendPasswordResetEmail(strEmail)
            .addOnSuccessListener {
                Toast.makeText(this@PasswordResetActivity, "Reset Password link has been sent to your registered Email", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@PasswordResetActivity, LoginPage::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@PasswordResetActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                //progressBar.visibility = View.INVISIBLE
                buttonReset.visibility = View.VISIBLE
            }
    }

}


