package com.example.pickup

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class GameDetailsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        // Retrieve game details from intent
        val jsonGame = intent.getStringExtra("game")
        val game: Map<String, Any> = Gson().fromJson(jsonGame, object : TypeToken<Map<String, Any>>() {}.type)


        // Set game details to views
        val sportTextView: TextView = findViewById(R.id.sportTextView)
        val locationTextView: TextView = findViewById(R.id.locationTextView)
        val minPlayersTextView: TextView = findViewById(R.id.minPlayersTextView)
         val maxPlayersTextView: TextView = findViewById(R.id.maxPlayersTextView)
        val dateTextView: TextView = findViewById(R.id.dateTextView)
         val timeTextView: TextView = findViewById(R.id.timeTextView)
         val teamTextView: TextView = findViewById(R.id.teamTextView)


        sportTextView.text = "Sport: ${game["sport"]}"
        locationTextView.text = "Location: ${game["location"]}"
        minPlayersTextView.text = "Min Players: ${game["minPlayers"]}"
        maxPlayersTextView.text = "Max Players: ${game["maxPlayers"]}"
        dateTextView.text = "Date: ${game["date"]}"
        timeTextView.text = "Time: ${game["time"]}"
        teamTextView.text = "Team: ${game["team"]}"


}
}
