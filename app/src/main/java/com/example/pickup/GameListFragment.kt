package com.example.pickup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class GameListFragment : AppCompatActivity() {
    private lateinit var createGameButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ViewGameAdapter
    private lateinit var gamesList: MutableList<Map<String, Any>>
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_game)
        createGameButton = findViewById(R.id.createGameButton)
        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        gamesList = mutableListOf()
        adapter = ViewGameAdapter(gamesList)
        recyclerView.adapter = adapter

        val db = FirebaseFirestore.getInstance()
        db.collection("games")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val game = document.data
                    gamesList.add(game)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to fetch games: ${exception.message}", Toast.LENGTH_SHORT).show()
                Log.e("ViewGameActivity", "Failed to fetch games", exception)
            }

        createGameButton.setOnClickListener {
            startActivity(Intent(this, CreateGameActivity::class.java))
        }

        // Implement search functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }
}
