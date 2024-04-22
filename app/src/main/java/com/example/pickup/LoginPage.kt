package com.example.pickup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var forgotPassword: TextView
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var progressBar: ProgressBar

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            startActivity(Intent(this, CreateGameActivity::class.java))
//            finish()
//        }
//    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        loginButton = findViewById(R.id.login_btn)
        signUpButton = findViewById(R.id.signup_btn)
        forgotPassword = findViewById(R.id.forgot_password)
        progressBar = findViewById(R.id.progressBar)


        forgotPassword.setOnClickListener {
            // Launch the passwordResetActivity activity
            startActivity(Intent(this, PasswordResetActivity::class.java))
        }


        loginButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)

            } else {
                progressBar.visibility = View.GONE
               // Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()

                val dialogView = layoutInflater.inflate(R.layout.fill_login_dialog, null)

                // Create a dialog and set its content view
                val builder = AlertDialog.Builder(this)
                builder.setView(dialogView)
                val dialog = builder.create()

                // Show the dialog
                dialog.show()
            }
        }

        signUpButton.setOnClickListener {
            // Launch the signup activity
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }




    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   progressBar.visibility = View.GONE
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser

                    startActivity(Intent(this, CreateGameActivity::class.java))
                    finish()
                } else {
                    progressBar.visibility = View.GONE
                    // If sign in fails, display a message to the user.
                   // Toast.makeText(baseContext, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                    val dialogView = layoutInflater.inflate(R.layout.incorrect_login_dialog, null)

                    // Create a dialog and set its content view
                    val builder = AlertDialog.Builder(this)
                    builder.setView(dialogView)
                    val dialog = builder.create()

                    // Show the dialog
                    dialog.show()
                }
            }
    }
}
