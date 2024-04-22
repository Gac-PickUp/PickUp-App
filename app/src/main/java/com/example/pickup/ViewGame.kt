package com.example.pickup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ViewGameActivity : AppCompatActivity() {

    private lateinit var sportTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var minPlayersTextView: TextView
    private lateinit var maxPlayersTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var teamTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_game)

        // Initialize views
        sportTextView = findViewById(R.id.sportTextView)
        locationTextView = findViewById(R.id.locationTextView)
        minPlayersTextView = findViewById(R.id.minPlayersTextView)
        maxPlayersTextView = findViewById(R.id.maxPlayersTextView)
        dateTextView = findViewById(R.id.dateTextView)
        timeTextView = findViewById(R.id.timeTextView)
        teamTextView = findViewById(R.id.teamTextView)

        // Get the game ID from intent
        //val gameId = intent.getStringExtra("gameId")

        // Fetch game details from Firebase Firestore
        val db = FirebaseFirestore.getInstance()
        db.collection("games")
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
           // if (document != null && document.exists()) {
                // Display game details
                val game = document.data
                sportTextView.text = game["sport"].toString()
                locationTextView.text = game["location"].toString()
                minPlayersTextView.text = game["minPlayers"].toString()
                maxPlayersTextView.text = game["maxPlayers"].toString()
                dateTextView.text = game["date"].toString()
                timeTextView.text = game["time"].toString()
                teamTextView.text = game["team"].toString()
                Toast.makeText(this, "Game Displayed Successfully", Toast.LENGTH_SHORT).show()
                    //for((key, value) in game){
                        Log.d("MyTag", "Game data: $game")
                    //}

            }
           // }
        }

            .addOnFailureListener { exception ->
                // Handle failures
                // You can show a toast or log the error here

                // For example, showing a toast with the error message
                Toast.makeText(this, "Failed to create game: ${exception.message}", Toast.LENGTH_SHORT).show()

                // Or logging the error
                Log.e("CreateGameActivity", "Failed to create game", exception)
            }

    }
}
