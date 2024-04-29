
package com.example.pickup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ViewGameActivity : AppCompatActivity() {
    private lateinit var createGameButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_game)
        createGameButton = findViewById(R.id.createGameButton)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = FirebaseFirestore.getInstance()
        db.collection("games")
            .get()
            .addOnSuccessListener { documents ->
                val gamesList = mutableListOf<Map<String, Any>>()
                for (document in documents) {
                    val game = document.data
                    gamesList.add(game)
                }
                val adapter = GameAdapter(gamesList)
                recyclerView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to fetch games: ${exception.message}", Toast.LENGTH_SHORT).show()
                Log.e("ViewGameActivity", "Failed to fetch games", exception)
            }

        createGameButton.setOnClickListener {
            // Launch the passwordResetActivity activity
            startActivity(Intent(this, CreateGameActivity::class.java))
        }

    }
}
