package com.example.pickup

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ClickGameItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Retrieve game details from intent extras
        val sport = intent.getStringExtra("sport") ?: ""
        val location = intent.getStringExtra("location") ?: ""
        val minPlayers = intent.getIntExtra("minPlayers", 0)
        val maxPlayers = intent.getIntExtra("maxPlayers", 0)
        val date = intent.getStringExtra("date") ?: ""
        val time = intent.getStringExtra("time") ?: ""
        val team = intent.getStringExtra("team") ?: ""

        // Find TextViews in your layout
        val sportTextView: TextView = findViewById(R.id.sportTextView)
        val locationTextView: TextView = findViewById(R.id.locationTextView)
        val minPlayersTextView: TextView = findViewById(R.id.minPlayersTextView)
        val maxPlayersTextView: TextView = findViewById(R.id.maxPlayersTextView)
        val dateTextView: TextView = findViewById(R.id.dateTextView)
        val timeTextView: TextView = findViewById(R.id.timeTextView)
        val teamTextView: TextView = findViewById(R.id.teamTextView)

        // Populate TextViews with game details
        sportTextView.text = sport
        locationTextView.text = location
        minPlayersTextView.text = minPlayers.toString()
        maxPlayersTextView.text = maxPlayers.toString()
        dateTextView.text = date
        timeTextView.text = time
        teamTextView.text = team
    }
}
