package com.example.pickup

import android.content.Intent


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var signUpButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var backButton: MaterialTextView

    private var  db = Firebase.firestore

    private var user = Firebase.auth.currentUser

    val uid = user?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        firstNameInput = findViewById(R.id.first_name_input)
        lastNameInput = findViewById(R.id.last_name_input)
        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        confirmPasswordInput = findViewById(R.id.confirm_password_input)
        signUpButton = findViewById(R.id.sign_up_btn)
        progressBar = findViewById(R.id.progressBar)
        backButton = findViewById(R.id.back_btn)


        backButton.setOnClickListener {
            startActivity(Intent(this, LoginPage::class.java))
        }


        /*
        TODO

        When signing up user add them to the "players" collection in the database
        Can use their email as a uid
        have properties like name etc.
        have subcollection of games that they are signed up for called "gamesIn"
            This subcollection will be empty and be populated when a user either creates a game or joins a game
            It will be populated by gameIDs
            When viewing a user's profile and clicking "My Games" show this list

         */

        signUpButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val firstName = firstNameInput.text.toString()
            val lastName = lastNameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    signUpUser(firstName, lastName, email, password)
                } else {
                    //Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    val dialogView = layoutInflater.inflate(R.layout.unmatch_password_dialog, null)

                    // Create a dialog and set its content view
                    val builder = AlertDialog.Builder(this)
                    builder.setView(dialogView)
                    val dialog = builder.create()

                    // Show the dialog
                    dialog.show()
                    progressBar.visibility = View.GONE
                }
            } else {
                //Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                val dialogView = layoutInflater.inflate(R.layout.fill_signup_dialog, null)

                // Create a dialog and set its content view
                val builder = AlertDialog.Builder(this)
                builder.setView(dialogView)
                val dialog = builder.create()

                // Show the dialog
                dialog.show()
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun signUpUser(firstName: String, lastName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progressBar.visibility = View.GONE
                    // Sign up success, update UI with the signed-up user's information


                    startActivity(Intent(this, CreateGameActivity::class.java))


                    if (uid != null) {
                        val newUserRef = db.collection("players").document(uid)

                        val playerInfo = hashMapOf(
                            "email" to user?.email.toString(),
                            "firstName" to firstName,
                            "lastName" to lastName
                        )

                        newUserRef
                            .set(playerInfo)
                            .addOnSuccessListener { documentReference ->
                                Toast.makeText(
                                    this,
                                    "Player Created Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                newUserRef.collection("gamesIn")

                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Failed to create player", Toast.LENGTH_SHORT)
                                    .show()
                            }

                    }


                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    progressBar.visibility = View.GONE
                    // If sign up fails, display a message to the user.
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        Toast.makeText(baseContext, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(baseContext, "Invalid email format", Toast.LENGTH_SHORT).show()
                    } catch (e: FirebaseAuthUserCollisionException) {
                        Toast.makeText(baseContext, "Email is already registered", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
