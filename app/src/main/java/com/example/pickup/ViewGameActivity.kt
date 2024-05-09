package com.example.pickup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ViewGameActivity : AppCompatActivity(), GameAdapter.OnGameItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_game)

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

                val adapter = GameAdapter(gamesList, this)
                recyclerView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to fetch games: ${exception.message}", Toast.LENGTH_SHORT).show()
                Log.e("ViewGameActivity", "Failed to fetch games", exception)
            }

    }
    override fun onGameItemClick(game: Map<String, Any>) {
        // Create an intent to start the ClickGameItem activity
        val intent = Intent(this, ClickGameItem::class.java)

        // Pass the details of the clicked game as extras
        intent.putExtra("sport", game["sport"].toString())
        intent.putExtra("location", game["location"].toString())
        intent.putExtra("minPlayers", game["minPlayers"].toString())
        intent.putExtra("maxPlayers", game["maxPlayers"].toString())
        intent.putExtra("date", game["date"].toString())
        intent.putExtra("time", game["time"].toString())
        intent.putExtra("team", game["team"].toString())

        // Start the ClickGameItem activity
        startActivity(intent)
    }

}